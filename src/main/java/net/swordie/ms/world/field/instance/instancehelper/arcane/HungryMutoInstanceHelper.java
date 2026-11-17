package net.swordie.ms.world.field.instance.instancehelper.arcane;

import java.util.stream.IntStream;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.connection.packet.field.HungryMutoFieldPacket;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.fieldeffect.FieldEffectType;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hungry Muto (Chu Chu Island, 921170050): hunt the field's monsters for ingredients and bring each order's
 * ingredients to Muto (the goal area) before the order's timer runs out. Muto's fullness (the score) starts at 500,
 * drops 10 every 10 seconds and rises with each finished dish; 1000 clears the run (10 minutes). Orders alternate
 * between the two phases' recipes, and each phase brings its own monsters.
 * <p>
 * GMS 271.4: HUNGRY_MUTO_INFO types 1 (timer ms), 3 (new order), 4 (order progress), 5 (carried
 * ingredients), 2 (clear); FIELD_VALUE phase/foodType/score/time; SESSION_VALUE rType/rCount at the start and
 * clear/score/ptime at the clear; the client sends its position from the goal area to deliver. Recipes are the
 * Chu Chu Food Truck's (Etc.wz ChewChewFoodTruck), scaled as GMS's orders.
 */
public class HungryMutoInstanceHelper extends InstanceHelper {
    public static final int FIELD = 921170050;
    public static final int EXIT = 450002024;
    /** Etc.wz MapleScheduler: the weekly clears; also holds score/clear as GMS does */
    public static final int WEEKLY_QR = 34222;
    public static final int TIME_LIMIT_SECONDS = 600;
    public static final int GOAL_SCORE = 1000;
    private static final int START_SCORE = 500;
    private static final int DECAY = 10;
    private static final int DECAY_MS = 10_000;
    private static final int DISH_SCORE = 340;
    private static final int[] ORDER_MS = {130_000, 140_000};
    private static final int FIRST_INGREDIENT = 2435856; // Sweet Hoof; the food truck's material n is this + n
    private static final int FIRST_DISH = 4034959; // Fried Treat; food n is this + n
    private static final Rect GOAL = new Rect(1265, -40, 1565, 160);
    private static final int PER_MOB = 6;
    private static final int RESPAWN_MS = 3000;
    private static final int SLURPY_TREE = 9833046;
    /** each phase's monsters (Bighorn Pinedeer etc. replace the first ones) and the material each drops */
    private static final List<DataTable.Row> MOB_ROWS = DataTable.read("hungry-muto-mobs").rows();
    private static final int[][] PHASE_MOBS = IntStream.range(0, 2).mapToObj(p -> MOB_ROWS.stream()
            .filter(r -> r.i("phase") == p).mapToInt(r -> r.i("mob")).toArray()).toArray(int[][]::new);
    private static final Map<Integer, Integer> MOB_MATERIAL = materials();
    /** food truck recipes: {material, count} pairs per food (Etc.wz ChewChewFoodTruck/food) */
    private static final int[][][] RECIPES = {
            {{0, 2}, {4, 1}}, {{2, 1}, {6, 2}}, {{0, 1}, {4, 1}, {8, 2}}, {{2, 1}, {6, 1}, {10, 2}},
            {{4, 1}, {8, 1}, {12, 2}}, {{8, 1}, {10, 1}, {14, 2}}, {{2, 2}, {6, 2}, {12, 4}, {16, 1}},
            {{4, 2}, {10, 2}, {16, 4}, {14, 1}}, {{5, 1}, {1, 2}}, {{3, 1}, {7, 2}}, {{5, 1}, {1, 1}, {9, 2}},
            {{3, 1}, {7, 1}, {11, 2}}, {{5, 1}, {9, 1}, {13, 2}}, {{9, 1}, {11, 1}, {15, 2}},
            {{3, 2}, {7, 2}, {16, 4}, {13, 1}}, {{5, 2}, {11, 2}, {16, 4}, {15, 1}}};
    /** the foods each phase orders (phase 1 uses the first monsters' materials, phase 2 the second's) */
    private static final int[][] PHASE_FOODS = {{2, 3, 4, 5, 6, 7}, {10, 11, 12, 13, 14, 15}};

    private final Map<Integer, Integer> carried = new HashMap<>();
    private int phase; // 0 or 1
    private int food;
    private int[][] order; // {item, need, have}
    private int orderMsLeft;
    private int score = START_SCORE;
    private int dishes;
    private long startedAt;
    private boolean over;

    private static Map<Integer, Integer> materials() {
        Map<Integer, Integer> m = new HashMap<>();
        MOB_ROWS.forEach(r -> m.put(r.i("mob"), r.i("material")));
        m.put(SLURPY_TREE, 16);
        return Map.copyOf(m);
    }

    public static boolean isField(int fieldId) {
        return fieldId == FIELD;
    }

    private static boolean isIngredient(int itemId) {
        return itemId >= FIRST_INGREDIENT && itemId <= FIRST_INGREDIENT + 16;
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        startedAt = System.currentTimeMillis();
        Field field = getField();
        spawnPhase(field, 0);
        field.spawnMob(SLURPY_TREE, randomPos(field).getX(), randomPos(field).getY(), false, 0);
        for (Char chr : instance.getChars()) {
            var sm = chr.getScriptManager();
            sm.setQRValue(WEEKLY_QR, "inGame", "1");
            sm.setQRValue(WEEKLY_QR, "clear", "0");
            sm.setQRValue(WEEKLY_QR, "dif", "normal");
            chr.write(HungryMutoFieldPacket.timer(TIME_LIMIT_SECONDS * 1000));
            chr.write(FieldPacket.sessionValue("rType", "0"));
            chr.write(FieldPacket.sessionValue("rCount", "0"));
        }
        newOrder();
        broadcast(FieldPacket.fieldValue("score", String.valueOf(score)));
        topScreen("Map/Effect3.img/hungryMutoMsg/msg1");
        addTimer(EventManager.addFixedRateEvent(this::tickSecond, 1000, 1000));
        addTimer(EventManager.addFixedRateEvent(this::decay, DECAY_MS, DECAY_MS));
    }

    @Override
    protected void startStage(int stageNumber) {
        // one stage, set up in setup()
    }

    private Char player() {
        var chars = getInstance().getChars();
        return chars.isEmpty() ? null : chars.get(0);
    }

    private void broadcast(OutPacket out) {
        for (Char chr : getInstance().getChars()) {
            chr.write(out);
        }
    }

    private static Position randomPos(Field field) {
        Position p = field.getRandomPosOnWalkableFoothold(30, false);
        return p != null ? p : new Position(1426, -170);
    }

    private void spawnPhase(Field field, int newPhase) {
        for (Mob m : List.copyOf(field.getMobs())) {
            Integer mat = MOB_MATERIAL.get(m.getTemplateId());
            if (mat != null && m.getTemplateId() != SLURPY_TREE) {
                field.removeLife(m.getObjectId(), true);
            }
        }
        for (int template : PHASE_MOBS[newPhase]) {
            for (int i = 0; i < PER_MOB; i++) {
                Position p = randomPos(field);
                field.spawnMob(template, p.getX(), p.getY(), false, 0);
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------ packets

    private OutPacket orderPacket(boolean isNew) {
        // GMS sends 45,000-54,000 as the unknown int; its use isn't known
        return HungryMutoFieldPacket.order(isNew, FIRST_DISH + food, food, phase + 1, ORDER_MS[phase], 45_000, order);
    }

    private static OutPacket carriedPacket(Char chr, int itemId, int count) {
        return HungryMutoFieldPacket.carried(chr.getId(), itemId, count);
    }

    private void topScreen(String path) {
        FieldEffect fe = new FieldEffect(FieldEffectType.TopScreen);
        fe.setString(path);
        broadcast(FieldPacket.fieldEffect(fe));
    }

    // ------------------------------------------------------------------------------------------------------- orders

    /** Orders scale the food truck's counts: 1 -> 5, 2 -> 10; four-ingredient dishes need 10 of the single one,
     * 5 of the pairs and 1 Slurpy Fruit (the 4) */
    private static int scaled(int[][] recipe, int count) {
        if (recipe.length < 4) {
            return count * 5;
        }
        return count == 1 ? 10 : count == 2 ? 5 : 1;
    }

    private synchronized void newOrder() {
        int[] foods = PHASE_FOODS[phase];
        food = foods[Util.getRandom(foods.length - 1)];
        int[][] recipe = RECIPES[food];
        order = new int[recipe.length][];
        for (int i = 0; i < recipe.length; i++) {
            order[i] = new int[]{FIRST_INGREDIENT + recipe[i][0], scaled(recipe, recipe[i][1]), 0};
        }
        orderMsLeft = ORDER_MS[phase];
        broadcast(FieldPacket.fieldValue("phase", String.valueOf(phase + 1)));
        broadcast(FieldPacket.fieldValue("foodType", String.valueOf(food)));
        broadcast(orderPacket(true));
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        Integer mat = MOB_MATERIAL.get(mob.getTemplateId());
        if (over || mat == null) {
            return;
        }
        Item item = ItemData.getItemDeepCopy(FIRST_INGREDIENT + mat);
        if (item != null) {
            field.drop(new Drop(-1, item), mob.getPosition(), true);
        }
        int template = mob.getTemplateId();
        addTimer(EventManager.addEvent(() -> {
            boolean current = template == SLURPY_TREE;
            for (int t : PHASE_MOBS[phase]) {
                current |= t == template;
            }
            if (!over && current) {
                Position p = randomPos(field);
                field.spawnMob(template, p.getX(), p.getY(), false, 0);
            }
        }, RESPAWN_MS));
    }

    @Override
    public synchronized void onItemLooted(Char chr, Item item, int quantity) {
        if (!over && isIngredient(item.getItemId())) {
            int n = carried.merge(item.getItemId(), Math.max(1, quantity), Integer::sum);
            chr.write(carriedPacket(chr, item.getItemId(), n));
        }
        super.onItemLooted(chr, item, quantity);
    }

    /** The client's delivery request: its position, sent while the player stands in the goal area */
    public synchronized void deliver(Char chr, int x, int y) {
        if (over || order == null || carried.isEmpty() || !GOAL.hasPositionInside(new Position(x, y))) {
            return;
        }
        Position pos = chr.getPosition();
        if (pos != null && Math.abs(pos.getX() - x) > 300) {
            return; // the position has to be the player's
        }
        boolean added = false;
        for (int[] line : order) {
            int have = carried.getOrDefault(line[0], 0);
            int add = Math.min(have, line[1] - line[2]);
            if (add > 0) {
                line[2] += add;
                added = true;
            }
        }
        carried.clear();
        chr.write(orderPacket(false));
        chr.write(carriedPacket(chr, 0, 0));
        if (!added) {
            return;
        }
        boolean done = true;
        for (int[] line : order) {
            done &= line[2] >= line[1];
        }
        if (!done) {
            topScreen("Map/Effect3.img/hungryMutoMsg/msg4");
            return;
        }
        dishes++;
        topScreen("Map/Effect3.img/hungryMuto/perfect");
        topScreen("Map/Effect3.img/hungryMutoMsg/msg5");
        setScore(score + DISH_SCORE);
        if (score >= GOAL_SCORE) {
            clear(chr);
            return;
        }
        phase = dishes % 2;
        spawnPhase(chr.getField(), phase);
        newOrder();
    }

    private synchronized void setScore(int value) {
        score = Math.max(0, Math.min(GOAL_SCORE, value));
        broadcast(FieldPacket.fieldValue("score", String.valueOf(score)));
    }

    private synchronized void tickSecond() {
        if (over || order == null) {
            return;
        }
        orderMsLeft -= 1000;
        broadcast(FieldPacket.fieldValue("time", String.valueOf(Math.max(0, orderMsLeft))));
        if (orderMsLeft <= 0) {
            topScreen("Map/Effect3.img/hungryMutoMsg/msg3"); // the map's fail effect
            carried.clear();
            Char chr = player();
            if (chr != null) {
                chr.write(carriedPacket(chr, 0, 0));
            }
            newOrder();
        }
    }

    private synchronized void decay() {
        if (over) {
            return;
        }
        setScore(score - DECAY);
        if (score <= 0) {
            fail();
        }
    }

    private void endRun(Char chr) {
        over = true;
        stopTimers();
        if (chr != null) {
            chr.getScriptManager().setQRValue(WEEKLY_QR, "inGame", "0");
            addTimer(EventManager.addEvent(() -> {
                if (chr.getInstance() == getInstance()) {
                    getInstance().removeChar(chr, EXIT, 0, true, false);
                }
            }, 3000));
        }
    }

    private void fail() {
        Char chr = player();
        topScreen("Map/Effect3.img/hungryMutoMsg/msg3");
        if (chr != null) {
            chr.getScriptManager().progressMessageFont("Muto got too hungry... Try again another time.");
        }
        endRun(chr);
    }

    private void clear(Char chr) {
        int ptime = (int) Math.max(0, TIME_LIMIT_SECONDS * 1000L - (System.currentTimeMillis() - startedAt));
        var sm = chr.getScriptManager();
        chr.write(FieldPacket.sessionValue("clear", "1"));
        chr.write(FieldPacket.sessionValue("score", String.valueOf(score)));
        chr.write(FieldPacket.sessionValue("ptime", String.valueOf(ptime)));
        sm.setQRValue(WEEKLY_QR, "clear", "1");
        sm.setQRValue(WEEKLY_QR, "score", String.valueOf(score));
        sm.setQRValue(WEEKLY_QR, "ptime", String.valueOf(ptime));
        ArcaneWeekly.addClear(chr, WEEKLY_QR);
        chr.write(HungryMutoFieldPacket.clear());
        sm.progressMessageFont("Muto is full! Talk to Simia outside for your reward.");
        endRun(chr);
    }
}

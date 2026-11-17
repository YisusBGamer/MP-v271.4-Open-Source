package net.swordie.ms.world.field.instance.instancehelper.arcane;

import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ReactorData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Spirit Savior (Arcana, 921172300; GMS since the Savior update): free the Bound Rock Spirits, take up to 5 along
 * (harvest key on the freed spirit) and bring them to the start, the goal area, before the Toxic Spirit catches up.
 * 400/800/1200/1800/2500 points for 1-5 spirits brought at once; 10,000 points clear the run (10 minutes). The
 * Toxic Spirit appears with the first freed spirit and grows with each one (5 stages, Map.wz spiritSavior/chaser);
 * when it touches a player escorting spirits, they're lost and the Toxic Spirit is gone.
 * Map data: Map.wz 921172300 spiritSavior (goalArea, prison spawn points, chaser stages).
 */
public class SpiritSaviorInstanceHelper extends InstanceHelper {
    public static final int FIELD = 921172300;
    public static final int EXIT = 921172400;
    /** the record the score is kept in (point, carry, chase) and the clear waits in (reward=1) */
    public static final int SCORE_QR = 16215;
    /** Etc.wz MapleScheduler: Spirit Savior's weekly clears */
    public static final int WEEKLY_QR = 16214;
    public static final int TIME_LIMIT_SECONDS = 600;
    public static final int GOAL_POINTS = 10000;
    private static final int[] POINTS = {0, 400, 800, 1200, 1800, 2500};
    private static final int MAX_CARRIED = 5;
    private static final int FOLLOW_SKILL = 80002310;
    public static final int FREED_REACTOR = 3600001;
    private static final int FREED_STAYS_MS = 8000;
    private static final int BOUND_AT_ONCE = 8;
    private static final int CHASER_BASE = 8644301;
    private static final int CHASER_STAGES = 5;
    private static final Rect GOAL = new Rect(-405, -840, 45, -440);
    // Map.wz spiritSavior/prison: spawn point -> Bound Rock Spirit template
    private static final int[][] PRISONS = DataTable.read("spirit-savior-prisons").rows().stream()
            .map(r -> new int[]{r.i("x"), r.i("y"), r.i("mob")}).toArray(int[][]::new);
    private static final String[] CHASER_MSG = {
            "It seems the Toxic Spirit has noticed you! Watch out!",
            "The Toxic Spirit is getting stronger by the minute...!",
            "The Toxic Spirit is getting stronger by the minute...!",
            "The Toxic Spirit is getting stronger by the minute...!",
            "The Toxic Spirit has achieved its strongest form! Be careful!"};

    private final List<Summon> carried = new ArrayList<>();
    private int points;
    private int freed;
    private Mob chaser;
    private boolean cleared;

    public static boolean isField(int fieldId) {
        return fieldId == FIELD;
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        Field field = getField();
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < PRISONS.length; i++) {
            order.add(i);
        }
        Collections.shuffle(order);
        for (int i = 0; i < BOUND_AT_ONCE; i++) {
            spawnBound(field, order.get(i));
        }
        for (Char chr : instance.getChars()) {
            chr.getScriptManager().setQRValue(SCORE_QR, "point", "0");
            chr.getScriptManager().setQRValue(SCORE_QR, "carry", "0");
            chr.getScriptManager().setQRValue(SCORE_QR, "chase", "0");
            chr.getScriptManager().showEffect("Map/Effect.img/killing/first/start");
        }
        addTimer(EventManager.addFixedRateEvent(this::tick, 500, 500));
    }

    @Override
    protected void startStage(int stageNumber) {
        // one stage, set up in setup()
    }

    private Char player() {
        var chars = getInstance().getChars();
        return chars.isEmpty() ? null : chars.get(0);
    }

    private void spawnBound(Field field, int prison) {
        int[] p = PRISONS[prison];
        field.spawnMob(p[2], p[0], p[1], false, 0);
    }

    /** A spawn point no Bound Rock Spirit stands on right now */
    private int freePrison(Field field) {
        List<Integer> free = new ArrayList<>();
        for (int i = 0; i < PRISONS.length; i++) {
            int template = PRISONS[i][2];
            if (field.getMobs().stream().noneMatch(m -> m.getTemplateId() == template)) {
                free.add(i);
            }
        }
        return free.isEmpty() ? Util.getRandom(PRISONS.length - 1) : free.get(Util.getRandom(free.size() - 1));
    }

    private static boolean isBound(int template) {
        return template >= PRISONS[0][2] && template <= PRISONS[PRISONS.length - 1][2];
    }

    private static boolean isChaser(int template) {
        return template >= CHASER_BASE && template < CHASER_BASE + CHASER_STAGES;
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        if (cleared || !isBound(mob.getTemplateId())) {
            return;
        }
        // the freed spirit waits a few seconds to be taken along
        Reactor freedSpirit = ReactorData.getReactorByID(FREED_REACTOR);
        if (freedSpirit != null) {
            freedSpirit.setPosition(mob.getPosition().deepCopy());
            field.spawnLife(freedSpirit, null);
            addTimer(EventManager.addEvent(() -> {
                if (field.getLifeByObjectID(freedSpirit.getObjectId()) != null) {
                    field.removeLife(freedSpirit.getObjectId(), true);
                }
            }, FREED_STAYS_MS));
        }
        addTimer(EventManager.addEvent(() -> {
            if (!cleared) {
                spawnBound(field, freePrison(field));
            }
        }, 3000));
        growChaser(field, mob.getPosition());
    }

    /** Each freed spirit makes the Toxic Spirit stronger (the first one calls it) */
    private synchronized void growChaser(Field field, Position near) {
        freed++;
        int stage = Math.min(freed, CHASER_STAGES) - 1;
        Position at = chaser != null && field.getLifeByObjectID(chaser.getObjectId()) != null
                ? chaser.getPosition().deepCopy() : new Position(near.getX(), near.getY() - 200);
        if (chaser != null && chaser.getTemplateId() == CHASER_BASE + stage
                && field.getLifeByObjectID(chaser.getObjectId()) != null) {
            return;
        }
        if (chaser != null && field.getLifeByObjectID(chaser.getObjectId()) != null) {
            field.removeLife(chaser.getObjectId(), true);
        }
        chaser = field.spawnMob(CHASER_BASE + stage, at.getX(), at.getY(), false, 0);
        Char chr = player();
        if (chr != null) {
            chr.getScriptManager().setQRValue(SCORE_QR, "chase", String.valueOf(stage + 1));
            chr.getScriptManager().progressMessageFont(CHASER_MSG[stage]);
        }
    }

    /** reactor/arcana_SavingSpirit: the player takes a freed spirit along (harvest key) */
    public synchronized void rescue(Char chr, Reactor reactor) {
        Field field = chr.getField();
        if (cleared || field.getLifeByObjectID(reactor.getObjectId()) == null) {
            return;
        }
        if (carried.size() >= MAX_CARRIED) {
            chr.chatMessage("You can't lead more than 5 spirits at once. Bring them back to the start first.");
            return;
        }
        field.removeLife(reactor.getObjectId(), true);
        Summon spirit = Summon.getSummonByNoCTS(chr, FOLLOW_SKILL, 1);
        spirit.setMoveAbility(MoveAbility.WalkSmart);
        spirit.setSummonTerm(TIME_LIMIT_SECONDS);
        field.spawnSummon(spirit);
        carried.add(spirit);
        chr.getScriptManager().setQRValue(SCORE_QR, "carry", String.valueOf(carried.size()));
        sendSavingCount(chr);
    }

    /** The escorted-spirits counter (UI/UIWindow6.img/spiritSavior/savingCount/%d) */
    private void sendSavingCount(Char chr) {
        var out = new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.SPIRIT_SAVIOR_SAVING_COUNT);
        out.encodeInt(carried.size());
        chr.write(out);
    }

    private synchronized void dropCarried(Field field) {
        for (Summon s : carried) {
            field.removeSummon(s);
        }
        carried.clear();
        Char chr = player();
        if (chr != null) {
            chr.getScriptManager().setQRValue(SCORE_QR, "carry", "0");
            sendSavingCount(chr);
        }
    }

    @Override
    public void onChrHitByMob(Char chr, int mobTemplateId, int dmgAmount) {
        if (cleared || !isChaser(mobTemplateId) || carried.isEmpty()) {
            return;
        }
        Field field = chr.getField();
        dropCarried(field);
        if (chaser != null && field.getLifeByObjectID(chaser.getObjectId()) != null) {
            field.removeLife(chaser.getObjectId(), true);
        }
        chaser = null;
        freed = 0;
        chr.getScriptManager().setQRValue(SCORE_QR, "chase", "0");
        chr.getScriptManager().progressMessageFont("Your friends have all been defeated by the Toxic Spirit!");
    }

    private synchronized void tick() {
        Char chr = player();
        if (chr == null || cleared || chr.getFieldID() != FIELD || carried.isEmpty()) {
            return;
        }
        Position pos = chr.getPosition();
        if (pos == null || !GOAL.hasPositionInside(pos)) {
            return;
        }
        int n = carried.size();
        dropCarried(chr.getField());
        points += POINTS[n];
        var sm = chr.getScriptManager();
        sm.setQRValue(SCORE_QR, "point", String.valueOf(points));
        sm.showEffect("Map/Effect3.img/savingSpirit/" + n);
        sm.playSound("Sound/MiniGame.img/Result_Yut");
        sm.progressMessageFont(n + (n == 1 ? " spirit" : " spirits") + " rescued: +" + POINTS[n] + " points ("
                + points + " / " + GOAL_POINTS + ")");
        if (points >= GOAL_POINTS) {
            clear(chr);
        }
    }

    private void clear(Char chr) {
        cleared = true;
        var sm = chr.getScriptManager();
        sm.setQRValue(SCORE_QR, "reward", "1");
        ArcaneWeekly.addClear(chr, WEEKLY_QR);
        sm.showEffect("Map/Effect.img/killing/clear");
        sm.progressMessageFont("You rescued enough spirits! Talk to the Escaped Rock Spirit outside for your reward.");
        Field field = chr.getField();
        if (chaser != null && field.getLifeByObjectID(chaser.getObjectId()) != null) {
            field.removeLife(chaser.getObjectId(), true);
        }
        addTimer(EventManager.addEvent(() -> {
            if (chr.getInstance() == getInstance()) {
                getInstance().removeChar(chr, EXIT, 0, true, false);
            }
        }, 4000));
    }
}

package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * Forest of Poison Haze (Cross World party quest, Ellin), as on live servers
 * (maplestorywiki.net/w/Forest_of_Poison_Haze). Purify the forest Kirston poisoned:
 * <ul>
 *     <li>Mouth of the Forest: defeat all 50 Poisoned Lord Trees.</li>
 *     <li>Deteriorated Forest: Poisoned Stone Bugs keep coming and drop Concentrated Poison. Drop it into the water
 *     hole to dilute it, then drop 4 Diluted Poison on the Thorny Vines to clear them.</li>
 *     <li>Forest of Haze: a maze of random portals (new every run). Anyone who reaches Ellin at the bottom right
 *     clears it for the party.</li>
 *     <li>Poisoned Forest: defeat Poisoned Sprights for 20 Monster Marbles; the leader gives them to Ellin.</li>
 *     <li>Forest's Empty Lot: one of the 9 boxes at the top holds the Purple Stone of Magic. Its finder shows it to
 *     the Sprite.</li>
 *     <li>Forest of Poison: drop the stone on the altar to summon the Poison Golem (3 phases) and defeat it.</li>
 * </ul>
 * Level 70+, a party of 1-4, 5 entries a day, 20 minutes. Rewards: the Cross World completion EXP (times the
 * server's EXP rate), split over the stages, +6 Willpower and +3 Insight EXP and 10 Party Points.
 */
public class PoisonHazeInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "poison_haze";
    public static final int MIN_LEVEL = 70;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 4;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int PRE_ENTRANCE = 930000000;
    public static final int FOREST_ENTRANCE = 930000010;
    public static final int STAGE_1 = 930000100;
    public static final int STAGE_2 = 930000200;
    public static final int STAGE_3 = 930000300;
    public static final int STAGE_4 = 930000400;
    public static final int STAGE_5 = 930000500;
    public static final int STAGE_6 = 930000600;
    public static final int ELLINS_FOREST = 930000700;
    public static final int FOREST_EXIT = 930000800;

    public static final int CONCENTRATED_POISON = 4001161;
    public static final int DILUTED_POISON = 4001162;
    public static final int PURPLE_STONE = 4001163;
    public static final int MONSTER_MARBLE = 4001169;
    public static final int MARBLES_NEEDED = 20;
    public static final int DILUTED_NEEDED = 4;

    private static final int STONE_BUG = 9300173;
    private static final int SPRIGHT = 9300175;
    private static final int LORD_TREE = 9300172;
    private static final int GOLEM = 9300180;
    private static final int GOLEM_2 = 9300181;
    private static final int GOLEM_3 = 9300182;
    private static final int WATER_HOLE = 3002000;
    private static final int THORNY_VINES = 3009000;
    private static final int ALTAR = 3001000;
    private static final int MAX_BUGS = 10;
    private static final int[] STAGE_PERCENT = {0, 10, 15, 10, 15, 10, 40};

    // the maze (Forest of Haze): rooms 1-3, 5-7 and 9-11 of a 3x3 grid; Ellin waits in room 11
    private static final int GOAL_ROOM = 11;
    private static final int START_ROOM = 1;
    private static final int[] ROOMS = {1, 2, 3, 5, 6, 7, 9, 10};
    private static final String[] EXITS = {"001E", "001S", "002W", "002E", "002S", "003W", "003S", "005N", "005E",
            "005S", "006W", "006N", "006E", "006S", "007W", "007N", "007S", "009N", "009E", "010W", "010N", "010E"};
    private final Map<String, Integer> maze = new HashMap<>();

    private int dilutedOnVines;
    private int boxWithStone;
    private boolean golemSummoned;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(FOREST_EXIT);
        instance.setTimeout(TIME_LIMIT_SECONDS);
        makeMaze();
        boxWithStone = Util.getRandom(1, 10); // an1-an9
        addTimer(EventManager.addFixedRateEvent(this::topUpBugs, 5000, 5000));
    }

    @Override
    protected void startStage(int stageNumber) {
    }

    // ---------------------------------------------------------------------------------------------------------- entry

    public static String getEntryBlocker(Char leader) {
        var party = leader.getParty();
        if (party == null) {
            return "Please make a party first (you can go alone in a party of 1).";
        }
        if (party.getPartyLeaderID() != leader.getId()) {
            return "Please have your party leader talk to me.";
        }
        var members = party.getPartyMembersInField(leader);
        if (members.size() < MIN_PARTY || members.size() > MAX_PARTY) {
            return "You need " + MIN_PARTY + " to " + MAX_PARTY + " party members here.";
        }
        for (Char c : members) {
            if (c.getLevel() < MIN_LEVEL) {
                return c.getName() + " must be at least level " + MIN_LEVEL + " to enter.";
            }
            if (PartyQuestRecords.getTodayEntries(c, RECORD_KEY) >= DAILY_ENTRIES) {
                return c.getName() + " has already entered " + DAILY_ENTRIES + " times today. Come back tomorrow!";
            }
        }
        return null;
    }

    public static void recordEntries(Char leader) {
        for (Char c : leader.getParty().getPartyMembersInField(leader)) {
            PartyQuestRecords.addEntry(c, RECORD_KEY);
        }
    }

    public static int getRemainingEntries(Char chr) {
        return Math.max(0, DAILY_ENTRIES - PartyQuestRecords.getTodayEntries(chr, RECORD_KEY));
    }

    // ---------------------------------------------------------------------------------------------------------- maps

    private static int stageOf(int fid) {
        switch (fid) {
            case STAGE_1:
                return 1;
            case STAGE_2:
                return 2;
            case STAGE_3:
                return 3;
            case STAGE_4:
                return 4;
            case STAGE_5:
                return 5;
            case STAGE_6:
                return 6;
        }
        return 0;
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        int s = stageOf(field.getId());
        return (s == 2 && !isStageCleared(2)) || (s == 4 && !isStageCleared(4));
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        int s = stageOf(field.getId());
        if (s >= 1 && s <= 2 && "east00".equals(portal.getName()) && !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The way on is still poisoned. Clear this area first.");
            return false;
        }
        return true;
    }

    /** party6_stage: on to the next area (once it's cleared) */
    public void next(Char chr) {
        int fid = chr.getField().getId();
        int s = stageOf(fid);
        if (s >= 1 && !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The way on is still poisoned. Clear this area first.");
            return;
        }
        switch (fid) {
            case PRE_ENTRANCE:
                chr.warp(FOREST_ENTRANCE);
                break;
            case FOREST_ENTRANCE:
                chr.warp(STAGE_1);
                break;
            case STAGE_1:
                chr.warp(STAGE_2);
                break;
            case STAGE_2:
                chr.warp(STAGE_3);
                break;
        }
    }

    // ---------------------------------------------------------------------------------------------------------- stage 2

    private void topUpBugs() {
        if (isStageCleared(2) || getInstance() == null) {
            return;
        }
        Field field = getInstance().getField(STAGE_2);
        if (field == null || field.getChars().isEmpty()) {
            return;
        }
        long bugs = field.getMobs().stream().filter(m -> m.getTemplateId() == STONE_BUG).count();
        for (long i = bugs; i < MAX_BUGS; i++) {
            Position pos = field.getRandomPosOnWalkableFoothold(50, false);
            if (pos != null) {
                field.spawnMob(STONE_BUG, pos.getX(), pos.getY(), false, -1);
            }
        }
    }

    private static Reactor findReactor(Field field, int templateId) {
        for (Reactor r : field.getReactors()) {
            if (r.getTemplateId() == templateId) {
                return r;
            }
        }
        return null;
    }

    private static boolean isNear(Reactor r, Position pos) {
        return r != null && pos != null && Math.abs(r.getPosition().getX() - pos.getX()) <= 150
                && Math.abs(r.getPosition().getY() - pos.getY()) <= 150;
    }

    /**
     * A quest item dropped onto the floor under a reactor. The water hole's anchor (1057, 188) lies under the floor it
     * sits on (y 150), with no foothold below it, so the drop starts higher up.
     */
    private static void dropOnFloor(Field field, int itemId, Position anchor, Position floor) {
        Position from = floor != null ? new Position(anchor.getX(), floor.getY() - 20) : anchor;
        for (int lift = 0; lift <= 300; lift += 100) {
            Position p = new Position(anchor.getX(), anchor.getY() - lift);
            if (field.findFootHoldBelow(p) != null) {
                from = p;
                break;
            }
        }
        field.drop(new Drop(-1, ItemData.getItemDeepCopy(itemId)), from, true); // a quest item: it must land to be picked up
    }

    @Override
    public synchronized void onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {
        super.onItemDropped(chr, item, quantity, position, drop);
        if (item == null || drop == null) {
            return;
        }
        Field field = chr.getField();
        int fid = field.getId();
        int id = item.getItemId();
        if (fid == STAGE_2 && id == CONCENTRATED_POISON) {
            Reactor hole = findReactor(field, WATER_HOLE);
            if (isNear(hole, position)) {
                drop.setBlockFromPickup(true);
                field.removeDrop(drop.getObjectId(), 0, false, 0);
                for (int i = 0; i < Math.max(1, quantity); i++) {
                    dropOnFloor(field, DILUTED_POISON, hole.getPosition(), position);
                }
                chr.chatMessage(ChatType.SystemNotice, "The water hole dilutes the poison.");
            }
        } else if (fid == STAGE_2 && id == DILUTED_POISON && !isStageCleared(2)) {
            Reactor vines = findReactor(field, THORNY_VINES);
            if (isNear(vines, position)) {
                drop.setBlockFromPickup(true);
                field.removeDrop(drop.getObjectId(), 0, false, 0);
                dilutedOnVines += Math.max(1, quantity);
                if (dilutedOnVines >= DILUTED_NEEDED) {
                    field.removeLife(vines.getObjectId(), false);
                    for (Mob m : new ArrayList<>(field.getMobs())) {
                        m.die(false);
                    }
                    if (clearStage(2, field, STAGE_PERCENT[2])) {
                        notice(field, "The Thorny Vines wither away! Go on through the portal.");
                    }
                } else {
                    vines.increaseState();
                    vines.broadcastChangeState();
                    notice(field, "The Thorny Vines shrink... (" + dilutedOnVines + "/" + DILUTED_NEEDED + ")");
                }
            }
        } else if (fid == STAGE_6 && id == PURPLE_STONE && !golemSummoned) {
            Reactor altar = findReactor(field, ALTAR);
            // the altar's anchor is high on its sprite: a stone dropped at its foot lands ~220 px lower
            if (altar != null && position != null && Math.abs(altar.getPosition().getX() - position.getX()) <= 150
                    && Math.abs(altar.getPosition().getY() - position.getY()) <= 300) {
                golemSummoned = true;
                drop.setBlockFromPickup(true);
                field.removeDrop(drop.getObjectId(), 0, false, 0);
                field.spawnMob(GOLEM, altar.getPosition().getX(), altar.getPosition().getY(), false, -1);
                notice(field, "The altar glows... the Poison Golem awakens!");
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------- stage 3

    private void makeMaze() {
        Map<String, Integer> m = new HashMap<>();
        for (int tries = 0; tries < 500; tries++) {
            m.clear();
            for (String exit : EXITS) {
                int from = Integer.parseInt(exit.substring(0, 3));
                int to;
                do {
                    to = ROOMS[Util.getRandom(ROOMS.length - 1)];
                } while (to == from);
                m.put(exit, to);
            }
            // one or two exits (never from the start room) lead to Ellin
            int ways = Util.getRandom(1, 3);
            for (int w = 0; w < ways; w++) {
                String exit;
                do {
                    exit = EXITS[Util.getRandom(EXITS.length - 1)];
                } while (exit.startsWith("001"));
                m.put(exit, GOAL_ROOM);
            }
            int dist = distanceToGoal(m);
            if (dist >= 3 && dist <= 6) {
                break;
            }
        }
        maze.putAll(m);
    }

    private static int distanceToGoal(Map<String, Integer> m) {
        Map<Integer, Integer> dist = new HashMap<>();
        Deque<Integer> queue = new ArrayDeque<>();
        dist.put(START_ROOM, 0);
        queue.add(START_ROOM);
        while (!queue.isEmpty()) {
            int room = queue.poll();
            for (var e : m.entrySet()) {
                if (Integer.parseInt(e.getKey().substring(0, 3)) == room && !dist.containsKey(e.getValue())) {
                    dist.put(e.getValue(), dist.get(room) + 1);
                    if (e.getValue() == GOAL_ROOM) {
                        return dist.get(e.getValue());
                    }
                    queue.add(e.getValue());
                }
            }
        }
        return -1;
    }

    /**
     * party6_stage5NN: a maze portal.
     *
     * @return the id of the portal to teleport to, or -1
     */
    public int mazeMove(Char chr, int portalId) {
        Field field = chr.getField();
        Portal portal = field.getInfo().getPortalByID(portalId);
        if (portal == null || !maze.containsKey(portal.getName())) {
            return -1;
        }
        int to = maze.get(portal.getName());
        Portal target = field.getInfo().getPortalByName(String.format("%02dst", to));
        return target == null ? -1 : target.getId();
    }

    /** Ellin in the Forest of Haze: whoever reaches her clears the maze for the party. */
    public String reachEllin(Char chr) {
        Field field = chr.getField();
        if (field.getId() != STAGE_3) {
            return null;
        }
        if (clearStage(3, field, STAGE_PERCENT[3])) {
            getInstance().warp(STAGE_4);
            return null;
        }
        return "You made it through the haze!";
    }

    // ---------------------------------------------------------------------------------------------------------- stage 4

    /** The leader brings Ellin 20 Monster Marbles in the Poisoned Forest. */
    public String giveMarbles(Char chr) {
        if (isStageCleared(4)) {
            return "The Sprights are free! Hurry on to the Forest's Empty Lot.";
        }
        if (!isLeader(chr)) {
            return "Gather #b" + MARBLES_NEEDED + " #t" + MONSTER_MARBLE + "#s#k from the Poisoned Sprights and "
                    + "have your party leader bring them to me.";
        }
        if (!chr.hasItemCount(MONSTER_MARBLE, MARBLES_NEEDED)) {
            return "Defeat the Poisoned Sprights and bring me #b" + MARBLES_NEEDED + " #t" + MONSTER_MARBLE
                    + "#s#k. You have " + chr.getInventories().stream().mapToInt(inv -> inv.getQuantity(MONSTER_MARBLE)).sum()
                    + ".";
        }
        chr.consumeItem(MONSTER_MARBLE, MARBLES_NEEDED);
        Field field = chr.getField();
        for (Mob m : new ArrayList<>(field.getMobs())) {
            m.die(false);
        }
        clearStage(4, field, STAGE_PERCENT[4]);
        getInstance().warp(STAGE_5);
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------- stage 5

    /** pFItem1: one of the 9 boxes in the Forest's Empty Lot (an1-an9) holds the Purple Stone of Magic. */
    public synchronized void openBox(Char chr, Reactor box) {
        if (box == null) {
            return;
        }
        Field field = chr.getField();
        String name = box.getName() == null ? "" : box.getName();
        int index = name.startsWith("an") && Util.isNumber(name.substring(2)) ? Integer.parseInt(name.substring(2)) : 0;
        field.removeLife(box.getObjectId(), false);
        if (index == boxWithStone) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(PURPLE_STONE)), box.getPosition(), true); // a quest item: it must land to be picked up
            notice(field, "You found the Purple Stone of Magic! Show it to the Sprite below.");
        } else {
            chr.chatMessage(ChatType.SystemNotice, "The box is empty.");
        }
    }

    /** The Sprite in the Forest's Empty Lot: the stone's finder takes the party to the Forest of Poison. */
    public String showStone(Char chr) {
        if (!chr.hasItemCount(PURPLE_STONE, 1)) {
            return "One of the boxes up top holds the #bPurple Stone of Magic#k. Bring it to me, and I'll open the "
                    + "way to the Forest of Poison.";
        }
        clearStage(5, chr.getField(), STAGE_PERCENT[5]);
        getInstance().warp(STAGE_6);
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------- monsters

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        int t = mob.getTemplateId();
        if (field.getId() == STAGE_6 && (t == GOLEM_2 || t == GOLEM_3)) {
            // phases 2 and 3 call deeply poisoned forest monsters
            int[] minions = {LORD_TREE, STONE_BUG, SPRIGHT};
            int count = t == GOLEM_2 ? 3 : 5;
            for (int i = 0; i < count; i++) {
                Position pos = field.getRandomPosOnWalkableFoothold(50, false);
                if (pos != null) {
                    int minion = minions[i % minions.length];
                    addTimer(EventManager.addEvent(() -> {
                        if (!finished) {
                            field.spawnMob(minion, pos.getX(), pos.getY(), false, -1);
                        }
                    }, 500));
                }
            }
        }
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int s = stageOf(field.getId());
        int t = mob.getTemplateId();
        if (s == 1 && field.getMobs().isEmpty()) {
            if (clearStage(1, field, STAGE_PERCENT[1])) {
                notice(field, "The Lord Trees are purified! Go on through the portal.");
            }
        } else if (s == 2 && t == STONE_BUG && !isStageCleared(2) && Util.succeedProp(60)) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(CONCENTRATED_POISON)), mob.getPosition(), true); // a quest item: it must land to be picked up
        } else if (s == 4 && t == SPRIGHT && !isStageCleared(4)) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(MONSTER_MARBLE)), mob.getPosition(), true); // a quest item: it must land to be picked up
        } else if (s == 6 && t == GOLEM_3 && !finished) {
            finished = true;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(6, field, STAGE_PERCENT[6]);
            giveTraitExp(Stat.willEXP, 6);
            giveTraitExp(Stat.insightEXP, 3);
            givePartyPoints(10);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
            }
            notice(field, "The Poison Golem is defeated and the forest is purified! Off to Ellin's Forest...");
            addTimer(EventManager.addEvent(() -> getInstance().warp(ELLINS_FOREST), 5000));
        }
        super.onMobDeath(field, mob);
    }

    public boolean isFinished() {
        return finished;
    }

    private static void notice(Field field, String msg) {
        if (field != null) {
            for (Char c : field.getChars()) {
                c.chatMessage(ChatType.SystemNotice, msg);
            }
        }
    }

    @Override
    public long getCompletionExp(Char chr) {
        return getCrossWorldExp(chr);
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Arrays.asList(CONCENTRATED_POISON, DILUTED_POISON, PURPLE_STONE, MONSTER_MARBLE);
    }
}

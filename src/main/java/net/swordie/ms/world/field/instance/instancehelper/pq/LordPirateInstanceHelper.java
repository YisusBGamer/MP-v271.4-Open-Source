package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.MobGen;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * Lord Pirate (Herb Town party quest), as on live servers (maplestorywiki.net/w/Lord_Pirate):
 * <ul>
 *     <li>Level 120+ (recommended 130-149), a party of 1-6, 5 entries a day, 20 minutes.</li>
 *     <li>Stage 1: defeat every monster and break all 7 chests (they hold more Ginseng Jars).</li>
 *     <li>Stage 2: 10 Rookie Pirate Marks from the Enraged Mr. Allis, then 10 Rising Pirate Marks from the Krus that
 *     replace them, then 10 Veteran Pirate Marks from the Captains, each handed to Guon.</li>
 *     <li>Stage 3: pirates pour out of four doors; Old Metal Keys close them. Close all four and defeat every pirate.</li>
 *     <li>Stage 4: defeat Lord Pirate (he calls Krus and Captains), then talk to Guon.</li>
 *     <li>Wu Yang gives Lord Pirate Hat Fragments by the monsters each member defeated (up to 30).</li>
 * </ul>
 * Completion EXP: 3,507,000 * 1.025^(level - 120) up to level 149 (times the server's EXP rate), split over the
 * stages; a clear also gives +18 Willpower and +11 Ambition EXP. Monsters are scaled to the party's level.
 */
public class LordPirateInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "lord_pirate";
    public static final int MIN_LEVEL = 120;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int ENTRANCE = 251010404;
    public static final int STAGE_1 = 925100000;
    public static final int STAGE_2 = 925100100;
    public static final int STAGE_3 = 925100400;
    public static final int STAGE_BOSS = 925100500;
    public static final int REWARD_MAP = 925100600;
    public static final int OUT_MAP = 925100700;

    public static final int OLD_METAL_KEY = 4001117;
    public static final int ROOKIE_MARK = 4001120;
    public static final int RISING_MARK = 4001121;
    public static final int VETERAN_MARK = 4001122;
    public static final int MARKS_NEEDED = 10;
    public static final int HAT_FRAGMENT = 4001455;
    public static final int MAX_FRAGMENTS = 30;
    public static final int COMMON_HAT = 1003856;
    public static final int RATTY_HAT = 1003857;
    public static final int GREEDY_HAT = 1003858;

    /** Stage 1 chests (davyMob0) and the stage 3 doors (davyScript0-3). */
    public static final int CHEST = 2511000;
    public static final int[] DOOR_REACTORS = {2519000, 2519001, 2519002, 2519003};

    private static final int CHESTS = 7;
    private static final int DOORS = 4;
    private static final int[] TIER_MOBS = {9300114, 9300115, 9300116}; // Enraged Mr. Alli, Kru, Captain
    private static final int[] TIER_MARKS = {ROOKIE_MARK, RISING_MARK, VETERAN_MARK};
    private static final int TIER_MOB_COUNT = 15;
    /** How close to a door a dropped Old Metal Key must land to close it. */
    private static final int DOOR_REACH_X = 100, DOOR_REACH_Y = 100;
    /** Time left after the boss, at least, so the party can still see Wu Yang. */
    private static final int REWARD_SECONDS = 120;

    public static final int LORD_PIRATE = 9300119;
    private static final Position LORD_PIRATE_POS = new Position(566, 238);
    private static final int LORD_PIRATE_KRU = 9300124;
    private static final int LORD_PIRATE_CAPTAIN = 9300125;

    private static final int[] STAGE_PERCENT = {0, 15, 20, 25, 40};

    private final Set<Integer> chestsBroken = new HashSet<>();
    private int tier; // 0-2: which mark Guon wants, 3 = done
    private final Set<Integer> doorsClosed = new HashSet<>();
    private boolean bossSpawned;
    private final Set<Integer> bossCalls = new HashSet<>();
    private boolean finished;
    private final Map<Integer, Integer> kills = new HashMap<>();
    private final Set<Integer> rewarded = new HashSet<>();

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(OUT_MAP);
        instance.setTimeout(TIME_LIMIT_SECONDS);
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
            return "Please have your party leader speak to me.";
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

    // ---------------------------------------------------------------------------------------------------------- maps

    @Override
    public boolean canSpawnMapMobs(Field field) {
        return field.getId() != STAGE_2; // stage 2's pirates come one kind at a time
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        // stage 3's doors keep sending pirates until all four are closed; elsewhere every monster is defeated once
        return field.getId() == STAGE_3 && doorsClosed.size() < DOORS;
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        int stage = stageOf(field.getId());
        if (stage > 0 && portal.getName() != null && portal.getName().startsWith("next") && !isStageCleared(stage)) {
            chr.chatMessage(ChatType.SystemNotice, "The portal is not open yet. Clear this stage first.");
            return false;
        }
        return true;
    }

    private static int stageOf(int fieldId) {
        switch (fieldId) {
            case STAGE_1:
                return 1;
            case STAGE_2:
                return 2;
            case STAGE_3:
                return 3;
            case STAGE_BOSS:
                return 4;
        }
        return 0;
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null) {
            if (newField.getId() == STAGE_2) {
                synchronized (this) {
                    if (tier == 0 && newField.getMobs().isEmpty()) {
                        spawnTier(newField);
                    }
                }
            } else if (newField.getId() == STAGE_BOSS) {
                synchronized (this) {
                    if (!bossSpawned) {
                        bossSpawned = true;
                        newField.spawnMob(LORD_PIRATE, LORD_PIRATE_POS.getX(), LORD_PIRATE_POS.getY(), false, -1);
                    }
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    // ---------------------------------------------------------------------------------------------------------- monsters

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        Char killer = mob.getMostDamageChar();
        if (killer != null) {
            kills.merge(killer.getId(), 1, Integer::sum);
        }
        int fid = field.getId();
        if (fid == STAGE_1) {
            checkStage1(field);
        } else if (fid == STAGE_2 && tier < 3 && mob.getTemplateId() == TIER_MOBS[tier]) {
            if (Util.succeedProp(GameConstants.LORD_PIRATE_TIER_MARK_CHANCE)) {
                field.drop(new Drop(-1, ItemData.getItemDeepCopy(TIER_MARKS[tier])), mob.getPosition(), true); // a quest item: it must land to be picked up
            }
            spawnOne(field, TIER_MOBS[tier]); // keep the current kind coming until Guon is satisfied
        } else if (fid == STAGE_3) {
            checkStage3(field);
        } else if (fid == STAGE_BOSS && mob.getTemplateId() == LORD_PIRATE && !finished) {
            finished = true;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(4, field, STAGE_PERCENT[4]);
            giveTraitExp(Stat.willEXP, 18);
            giveTraitExp(Stat.charismaEXP, 11);
            for (Char c : new ArrayList<>(getInstance().getChars())) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
            }
            // a clear in the last seconds still leaves time to see Wu Yang
            if (getInstance().getRemainingTime() < REWARD_SECONDS * 1000L) {
                getInstance().setTimeout(REWARD_SECONDS);
            }
            notice(field, "Lord Pirate is defeated! Talk to Guon to see Wu Yang.");
        }
        super.onMobDeath(field, mob);
    }

    @Override
    public synchronized void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        if (mob.getTemplateId() == LORD_PIRATE && mob.getMaxHp() > 0 && !finished) {
            long pct = newHp * 100 / mob.getMaxHp();
            for (int at : new int[]{75, 50, 25}) {
                if (pct <= at && bossCalls.add(at)) {
                    var pos = mob.getPosition();
                    for (int i = 0; i < 3; i++) {
                        mob.getField().spawnMob(i % 2 == 0 ? LORD_PIRATE_KRU : LORD_PIRATE_CAPTAIN,
                                pos.getX() - 150 + i * 150, pos.getY(), false, -1);
                    }
                }
            }
        }
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
    }

    private void spawnTier(Field field) {
        for (int i = 0; i < TIER_MOB_COUNT; i++) {
            spawnOne(field, TIER_MOBS[tier]);
        }
    }

    /**
     * One pirate of this kind at one of the map's own spawn points for it. A random foothold could be the decks the
     * NPCs stand on high above the ship, where nobody reaches the pirate or the mark it drops.
     */
    private void spawnOne(Field field, int template) {
        List<Position> spots = new ArrayList<>();
        for (MobGen gen : field.getMobGens()) {
            if (gen.getMob() != null && gen.getMob().getTemplateId() == template && gen.getPosition() != null) {
                spots.add(gen.getPosition());
            }
        }
        Position pos = spots.isEmpty() ? field.getRandomPosOnWalkableFoothold(50, false)
                : Util.getRandomFromCollection(spots);
        if (pos != null) {
            field.spawnMob(template, pos.getX(), pos.getY(), false, -1);
        }
    }

    // ---------------------------------------------------------------------------------------------------------- stage 1

    /** A stage 1 chest was broken (reactor davyMob0); each chest counts once, however many hits reach it. */
    public synchronized void chestBroken(Field field, int reactorObjectId) {
        if (chestsBroken.add(reactorObjectId)) {
            checkStage1(field);
        }
    }

    /** Older davyMob0 scripts that don't pass the chest. */
    public synchronized void chestBroken(Field field) {
        chestBroken(field, -1 - chestsBroken.size());
    }

    public int getChestsBroken() {
        return chestsBroken.size();
    }

    private void checkStage1(Field field) {
        if (chestsBroken.size() >= CHESTS && field.getMobs().isEmpty()) {
            if (clearStage(1, field, STAGE_PERCENT[1])) {
                notice(field, "Stage clear! Head through the portal.");
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------- stage 2

    /** @return the mark Guon wants now, 0 when stage 2 is done */
    public int getWantedMark() {
        return tier < 3 ? TIER_MARKS[tier] : 0;
    }

    /**
     * Guon takes 10 of the wanted marks (from anyone of the party in the map).
     *
     * @return what Guon says
     */
    public synchronized String handInMarks(Char chr) {
        if (tier >= 3) {
            return "You have all the marks! Please proceed to the next stage.";
        }
        var field = chr.getField();
        int mark = TIER_MARKS[tier];
        int have = 0;
        for (Char c : field.getChars()) {
            have += c.getInventories().stream().mapToInt(inv -> inv.getQuantity(mark)).sum();
        }
        if (have < MARKS_NEEDED) {
            return "Please bring me " + MARKS_NEEDED + " #v" + mark + "##t" + mark + "#. Your party has " + have + ".";
        }
        int left = MARKS_NEEDED;
        for (Char c : new ArrayList<>(field.getChars())) {
            int q = c.getInventories().stream().mapToInt(inv -> inv.getQuantity(mark)).sum();
            int take = Math.min(q, left);
            if (take > 0) {
                c.consumeItem(mark, take);
                left -= take;
            }
            if (left <= 0) {
                break;
            }
        }
        // the current pirates leave; the next kind comes
        int old = TIER_MOBS[tier];
        for (Mob m : new ArrayList<>(field.getMobs())) {
            if (m.getTemplateId() == old) {
                field.removeLife(m.getObjectId());
                field.broadcastPacket(net.swordie.ms.connection.packet.MobPool.leaveField(m.getObjectId(),
                        net.swordie.ms.life.DeathType.ANIMATION_DEATH));
            }
        }
        tier++;
        if (tier >= 3) {
            clearStage(2, field, STAGE_PERCENT[2]);
            return "That's all the marks! The way forward is open.";
        }
        spawnTier(field);
        int next = TIER_MARKS[tier];
        return "Great job! Now I need " + MARKS_NEEDED + " #v" + next + "##t" + next + "#.";
    }

    // ---------------------------------------------------------------------------------------------------------- stage 3

    /**
     * A door was hit or clicked (reactors davyScript0-3) by someone holding an Old Metal Key.
     *
     * @return whether it closed (the script then removes it)
     */
    public synchronized boolean closeDoor(Char chr, int reactorObjectId) {
        if (doorsClosed.contains(reactorObjectId)) {
            return true; // a second hit of the same swing: already shut
        }
        if (!chr.hasItem(OLD_METAL_KEY)) {
            chr.chatMessage(ChatType.SystemNotice, "You need an Old Metal Key to close this door.");
            return false;
        }
        chr.consumeItem(OLD_METAL_KEY, 1);
        shut(chr.getField(), reactorObjectId);
        return true;
    }

    /** Older davyScript0-3 scripts that don't pass the door. */
    public synchronized boolean closeDoor(Char chr) {
        return closeDoor(chr, -1 - doorsClosed.size());
    }

    /** An Old Metal Key dropped at an open door closes it too (the door's Reactor.wz event asks for the key). */
    @Override
    public synchronized void onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {
        super.onItemDropped(chr, item, quantity, position, drop);
        if (item == null || drop == null || position == null || item.getItemId() != OLD_METAL_KEY) {
            return;
        }
        Field field = chr.getField();
        if (field == null || field.getId() != STAGE_3) {
            return;
        }
        Reactor door = null;
        for (Reactor re : field.getReactors()) {
            if (isDoor(re.getTemplateId()) && re.getPosition() != null && !doorsClosed.contains(re.getObjectId())
                    && Math.abs(re.getPosition().getX() - position.getX()) <= DOOR_REACH_X
                    && Math.abs(re.getPosition().getY() - position.getY()) <= DOOR_REACH_Y) {
                door = re;
                break;
            }
        }
        if (door == null) {
            return;
        }
        drop.setBlockFromPickup(true);
        field.removeDrop(drop.getObjectId(), 0, false, 0);
        int left = Math.max(1, quantity) - 1;
        if (left > 0) {
            chr.addItemToInventory(OLD_METAL_KEY, left); // one key per door: the rest go back
        }
        field.removeLife(door.getObjectId(), false);
        shut(field, door.getObjectId());
    }

    public static boolean isDoor(int reactorTemplate) {
        for (int d : DOOR_REACTORS) {
            if (d == reactorTemplate) {
                return true;
            }
        }
        return false;
    }

    private void shut(Field field, int reactorObjectId) {
        doorsClosed.add(reactorObjectId);
        int n = doorsClosed.size();
        notice(field, n >= DOORS ? "All the doors are closed! Defeat the remaining pirates."
                : "A door is closed. Doors left: " + (DOORS - n));
        checkStage3(field);
    }

    public int getDoorsClosed() {
        return doorsClosed.size();
    }

    private void checkStage3(Field field) {
        if (doorsClosed.size() >= DOORS && field.getMobs().isEmpty()) {
            if (clearStage(3, field, STAGE_PERCENT[3])) {
                notice(field, "Stage clear! Head through the portal to face Lord Pirate.");
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------- end

    public boolean isFinished() {
        return finished;
    }

    /** Guon after the boss: the party goes to Wu Yang. */
    public void goToReward() {
        for (Char c : new ArrayList<>(getInstance().getChars())) {
            c.warp(REWARD_MAP);
        }
    }

    /**
     * Wu Yang: Hat Fragments by the monsters this character defeated (once per run).
     *
     * @return the fragments given, -1 if already given, -2 if there was no room
     */
    public synchronized int giveFragments(Char chr) {
        if (!finished || rewarded.contains(chr.getId())) {
            return -1;
        }
        int amount = Math.max(1, Math.min(MAX_FRAGMENTS, kills.getOrDefault(chr.getId(), 0) / 3));
        if (!chr.canHold(HAT_FRAGMENT, amount)) {
            return -2;
        }
        chr.addItemToInventory(HAT_FRAGMENT, amount);
        rewarded.add(chr.getId());
        return amount;
    }

    private static void notice(Field field, String msg) {
        if (field != null) {
            for (Char c : new ArrayList<>(field.getChars())) {
                c.chatMessage(ChatType.SystemNotice, msg);
            }
        }
    }

    @Override
    protected long getBaseCompletionExp() {
        return 3_507_000L;
    }

    @Override
    protected int getExpMinLevel() {
        return 120;
    }

    @Override
    protected int getExpMaxLevel() {
        return 149;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Arrays.asList(OLD_METAL_KEY, ROOKIE_MARK, RISING_MARK, VETERAN_MARK);
    }
}

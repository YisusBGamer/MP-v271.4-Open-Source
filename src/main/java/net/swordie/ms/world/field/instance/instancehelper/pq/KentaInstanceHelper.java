package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.enums.Stat;
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
 * Kenta in Danger (Aqua Road party quest), as on live servers (maplestorywiki.net/w/Kenta_in_Danger):
 * <ul>
 *     <li>Level 120+ (recommended 160-179), a party of 1-6, 5 entries a day, 20 minutes.</li>
 *     <li>Dangerous Sea 1: break the shipwreck to free Kenta, collect 10 Air Bubbles from the Sharks and bring them to
 *     Kenta.</li>
 *     <li>Dangerous Sea 2: seal the caves the monsters come from with 12 rocks (3 per cave).</li>
 *     <li>Dangerous Cave 1: clear the Cave Bat swarm at the top of the flooding cave.</li>
 *     <li>Dangerous Cave 2: defeat both Pianus. Each drops a Pianus Scale; the Dolphin trades 10 for Kenta's New
 *     Goggles or 5 for a Kenta Pet Equip Scroll.</li>
 * </ul>
 * Completion EXP: 6,579,300 * 1.025^(level - 120) up to level 179 (times the server's EXP rate), split over the
 * stages, +30% for keeping Kenta safe; a clear also gives +44 Willpower and +26 Charm EXP.
 */
public class KentaInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "kenta";
    public static final int MIN_LEVEL = 120;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int ENTRANCE = 923040000;
    public static final int STAGE_1 = 923040100;
    public static final int STAGE_2 = 923040200;
    public static final int STAGE_3 = 923040300;
    public static final int STAGE_4 = 923040400;

    public static final int AIR_BUBBLE = 2430364;
    public static final int BUBBLES_NEEDED = 10;
    public static final int PIANUS_SCALE = 4001535;
    public static final int KENTAS_NEW_GOGGLES = 1022175;
    public static final int[] PET_SCROLLS = {2048024, 2048025, 2048028, 2048029, 2048030, 2048031};

    private static final int SHARK = 9300446;
    private static final int COLD_SHARK = 9300447;
    private static final int CAVE_BAT = 9300889;
    private static final int PIANUS_LEFT = 9300468;
    private static final int PIANUS_RIGHT = 9300461; // Pianus 8510000's copy, on the right in its own map
    private static final int ROCKS_NEEDED = 12;
    private static final int[] STAGE_PERCENT = {0, 20, 20, 20, 40};
    private static final int KENTA_BONUS_PERCENT = 30;

    private boolean wreckBroken;
    private int rocks;
    private boolean batsSpawned;
    private boolean bossesSpawned;
    private int pianusLeft = 2;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(ENTRANCE);
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

    /** The Dolphin's trade: 10 scales for the goggles (choice 0) or 5 for a random pet scroll (choice 1). */
    public static String tradeScales(Char chr, int choice) {
        int need = choice == 0 ? 10 : 5;
        int item = choice == 0 ? KENTAS_NEW_GOGGLES : PET_SCROLLS[Util.getRandom(PET_SCROLLS.length - 1)];
        if (!chr.hasItemCount(PIANUS_SCALE, need)) {
            return "You need " + need + " #t" + PIANUS_SCALE + "#s for that.";
        }
        if (!chr.canHold(item, 1)) {
            return "Please make room in your inventory first.";
        }
        chr.consumeItem(PIANUS_SCALE, need);
        chr.addItemToInventory(item, 1);
        return "Here you go: #i" + item + "# #b#t" + item + "##k!";
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
        }
        return 0;
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        // the caves keep sending monsters until they're sealed; the sharks keep coming for bubbles
        return (field.getId() == STAGE_2 && rocks < ROCKS_NEEDED) || field.getId() == STAGE_1;
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        int s = stageOf(field.getId());
        if ("next00".equals(portal.getName()) && s > 0 && !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "Kenta can't go on yet. Clear this area first.");
            return false;
        }
        return true;
    }

    /** kenta_nextStage */
    public void next(Char chr) {
        int s = stageOf(chr.getField().getId());
        if (s == 0 || !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "Kenta can't go on yet. Clear this area first.");
            return;
        }
        int to = s == 1 ? STAGE_2 : s == 2 ? STAGE_3 : s == 3 ? STAGE_4 : 0;
        if (to != 0) {
            chr.warp(to);
        }
    }

    /** The stretch of the cave's bottom floor the party stands on. */
    private static final int BAT_MIN_X = -175, BAT_MAX_X = 806;

    private synchronized void herdBats(Field cave) {
        if (isStageCleared(3)) {
            return;
        }
        for (Mob m : new ArrayList<>(cave.getMobs())) {
            if (m.getTemplateId() == CAVE_BAT && m.getHp() > 0 && m.getPosition() != null
                    && (m.getPosition().getX() < BAT_MIN_X - 60 || m.getPosition().getX() > BAT_MAX_X + 60)) {
                m.disappear();
                cave.spawnMob(CAVE_BAT, Util.getRandom(BAT_MIN_X, BAT_MAX_X), 168, false, -1);
            }
        }
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null) {
            if (newField.getId() == STAGE_3) {
                synchronized (this) {
                    if (!batsSpawned) {
                        batsSpawned = true;
                        // the upper floors are reached on the rising water, which isn't sent: all on the bottom floor
                        for (int i = 0; i < 15; i++) {
                            newField.spawnMob(CAVE_BAT, Util.getRandom(BAT_MIN_X, BAT_MAX_X), 168, false, -1);
                        }
                        // bats fly off along the floor into stretches walled off from it: brought back every 10 s
                        Field cave = newField;
                        addTimer(EventManager.addFixedRateEvent(() -> herdBats(cave), 10_000, 10_000));
                    }
                }
            } else if (newField.getId() == STAGE_4) {
                synchronized (this) {
                    if (!bossesSpawned) {
                        bossesSpawned = true;
                        // one on each side, clear of the start point (both hit on contact)
                        newField.spawnMob(PIANUS_LEFT, -1110, 168, false, -1);
                        newField.spawnMob(PIANUS_RIGHT, 500, 168, false, -1);
                    }
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    // ---------------------------------------------------------------------------------------------------------- stage 1

    /** The shipwreck (reactor kenta_woodenboard) was broken. */
    public synchronized void wreckBroken(Field field) {
        if (!wreckBroken) {
            wreckBroken = true;
            notice(field, "Kenta is free! Collect " + BUBBLES_NEEDED + " Air Bubbles from the Sharks so he can breathe.");
        }
    }

    /**
     * Talking to Kenta in Dangerous Sea 1: takes the 10 bubbles.
     */
    public synchronized String giveBubbles(Char chr) {
        var field = chr.getField();
        if (field.getId() != STAGE_1) {
            return isStageCleared(stageOf(field.getId())) ? "Let's keep going!" : "Please help me get through here!";
        }
        if (isStageCleared(1)) {
            return "I can breathe again! Let's go through the portal at the top.";
        }
        if (!wreckBroken) {
            return "Help! I'm stuck under the shipwreck! Break it with normal attacks!";
        }
        int have = 0;
        for (Char c : field.getChars()) {
            have += c.getInventories().stream().mapToInt(inv -> inv.getQuantity(AIR_BUBBLE)).sum();
        }
        if (have < BUBBLES_NEEDED) {
            return "I... can't... breathe... Please bring me " + BUBBLES_NEEDED + " #t" + AIR_BUBBLE + "#s! (Your "
                    + "party has " + have + ".)";
        }
        int left = BUBBLES_NEEDED;
        for (Char c : field.getChars()) {
            int q = c.getInventories().stream().mapToInt(inv -> inv.getQuantity(AIR_BUBBLE)).sum();
            int take = Math.min(q, left);
            if (take > 0) {
                c.consumeItem(AIR_BUBBLE, take);
                left -= take;
            }
        }
        clearStage(1, field, STAGE_PERCENT[1]);
        return "Phew, I can breathe! Thank you. Let's head up to the portal!";
    }

    // ---------------------------------------------------------------------------------------------------------- stage 2

    /** A rock (reactor kenta_stone1) was put into a cave. */
    public synchronized void rockPlaced(Field field) {
        if (isStageCleared(2)) {
            return;
        }
        rocks++;
        if (rocks >= ROCKS_NEEDED) {
            notice(field, "All the caves are sealed!");
            checkStage2(field);
        } else {
            notice(field, "Rocks placed: " + rocks + " / " + ROCKS_NEEDED);
        }
    }

    private void checkStage2(Field field) {
        if (rocks >= ROCKS_NEEDED && field.getMobs().isEmpty()) {
            if (clearStage(2, field, STAGE_PERCENT[2])) {
                notice(field, "The sea is safe! Kenta swims to the portal.");
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------- monsters

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int fid = field.getId();
        int t = mob.getTemplateId();
        if (fid == STAGE_1 && (t == SHARK || t == COLD_SHARK) && !isStageCleared(1) && Util.succeedProp(50)) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(AIR_BUBBLE)), mob.getPosition(), true); // a quest item: it must land to be picked up
        } else if (fid == STAGE_2) {
            checkStage2(field);
        } else if (fid == STAGE_3 && field.getMobs().isEmpty()) {
            if (clearStage(3, field, STAGE_PERCENT[3])) {
                // the water that lifts the party to the top isn't sent: put everyone by the portal up there
                Position top = new Position(543, -1574);
                for (Char c : field.getChars()) {
                    c.setPosition(top.deepCopy());
                    c.write(FieldPacket.teleport(top, c));
                }
                notice(field, "The bats are gone! The water carries you up to the portal at the top.");
            }
        } else if (fid == STAGE_4 && (t == PIANUS_LEFT || t == PIANUS_RIGHT)) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(PIANUS_SCALE)), mob.getPosition(), true); // a quest item: it must land to be picked up
            pianusLeft--;
            if (pianusLeft <= 0 && !finished) {
                finished = true;
                clearStage(4, field, STAGE_PERCENT[4] + KENTA_BONUS_PERCENT);
                giveTraitExp(Stat.willEXP, 44);
                giveTraitExp(Stat.charmEXP, 26);
                for (Char c : getInstance().getChars()) {
                    PartyQuestRecords.addClear(c, RECORD_KEY);
                }
                notice(field, "Both Pianus are defeated and Kenta is safe! Pick up the Pianus Scales: you'll be "
                        + "taken back to the Dolphin in 30 seconds.");
                addTimer(net.swordie.ms.handlers.executors.EventManager.addEvent(() -> getInstance().clear(), 30_000));
            }
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
    protected long getBaseCompletionExp() {
        return 6_579_300L;
    }

    @Override
    protected int getExpMinLevel() {
        return 120;
    }

    @Override
    protected int getExpMaxLevel() {
        return 179;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.singletonList(AIR_BUBBLE);
    }
}

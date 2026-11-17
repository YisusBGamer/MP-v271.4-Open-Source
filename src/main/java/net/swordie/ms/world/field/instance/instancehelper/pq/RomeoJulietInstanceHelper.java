package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * Romeo and Juliet (Magatia party quest), as on live servers (maplestorywiki.net/w/Romeo_and_Juliet). The party
 * helps Romeo (maps 9261000xx, reward: Zenumist Marble) or Juliet (9261100xx, reward: Alcadno Marble):
 * <ol>
 *     <li>Mysterious Lab: search the light spots for the switch that opens the door (other spots hold a little EXP or
 *     mesos).</li>
 *     <li>Laboratory Tunnel: defeat every Homun and Saitie.</li>
 *     <li>Unpleasant Lab: Homunculi drop Suspicious Liquid; fill the 3 cracked beakers.</li>
 *     <li>Special Lab: Neo Huroids drop Card Keys that open the two side doors; break the crates in the dark labs for
 *     the Alcadno and Zenumist Experiment Files and give both to Romeo/Juliet.</li>
 *     <li>Yulete's Office: defeat the Mutae Yulete calls and find the switch.</li>
 *     <li>Lab Tunnel: each door leads up 5 floors of 4 platforms; one platform per floor leads on. One member reaching
 *     the top takes the party on.</li>
 *     <li>Center Lab: defeat Frankenroid. If the captured lover falls, the quest fails.</li>
 * </ol>
 * Level 70+, a party of 1-4, 5 entries a day, 20 minutes. Completion EXP: 179,400 * 1.037^(level - 70) up to level 99
 * (times the server's EXP rate), split over the stages; a clear also gives +29 Willpower and +17 Empathy EXP and the
 * marble. 2 marbles make a pendant; Romeo's and Juliet's pendants make the Symbol of Eternal Love.
 */
public class RomeoJulietInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "romeo_juliet";
    public static final int MIN_LEVEL = 70;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 4;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int ROMEO_BASE = 926100000;
    public static final int JULIET_BASE = 926110000;
    public static final int ROMEO_ENTRANCE = 261000011;
    public static final int JULIET_ENTRANCE = 261000021;

    public static final int SUSPICIOUS_LIQUID = 4001132;
    public static final int CARD_KEY = 4001133;
    public static final int ALCADNO_FILES = 4001134;
    public static final int ZENUMIST_FILES = 4001135;
    public static final int ZENUMIST_MARBLE = 4001159;
    public static final int ALCADNO_MARBLE = 4001160;

    private static final int HOMUNCULUS = 9300147;
    private static final int NEO_HUROID = 9300148;
    private static final int IRON_MUTAE = 9300143;
    private static final int MITHRIL_MUTAE = 9300144;
    private static final int FRANKENROID = 9300139;
    private static final int JULIET_MOB = 9300137;
    private static final int ROMEO_MOB = 9300138;

    private static final int[] STAGE_PERCENT = {0, 10, 10, 15, 15, 15, 10, 25};
    private static final int FIRST_UNIT_PLATFORM = 15; // portals pt00-pt43 of the units: ids 15-34
    private static final int UNIT_FLOORS = 5;
    private static final int UNIT_SUCCESS_PORTAL = 1;
    private static final int UNIT_FAIL_PORTAL = 2;

    private int base;
    private int stage1Switch;
    /** Stage 1 spots already searched: each gives its find once. */
    private final Set<Integer> searched = new HashSet<>();
    private int stage5Switch;
    private int beakers;
    private final Set<Integer> doorsOpen = new HashSet<>();
    private boolean mutaeSpawned;
    private boolean bossSpawned;
    private final int[][] unitCode = new int[4][UNIT_FLOORS];
    private boolean finished;
    private boolean failed;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        base = instance.getEnterFieldId() >= JULIET_BASE ? JULIET_BASE : ROMEO_BASE;
        stage1Switch = Util.getRandom(2, 28); // portals click01-click26 (the top is left out)
        stage5Switch = Util.getRandom(4, 9); // portals click01-click05 of Yulete's office
        for (int[] unit : unitCode) {
            for (int f = 0; f < UNIT_FLOORS; f++) {
                unit[f] = Util.getRandom(0, 4);
            }
        }
        instance.setForcedReturn(exitMap());
        instance.setTimeout(TIME_LIMIT_SECONDS);
    }

    @Override
    protected void startStage(int stageNumber) {
    }

    private boolean isJulietSide() {
        return base == JULIET_BASE;
    }

    public int stage1() { return base; }
    public int stage2() { return base + 1; }
    public int stage3() { return base + 100; }
    public int stage4() { return base + 200; }
    public int darkLab1() { return base + 201; }
    public int darkLab2() { return base + 202; }
    public int stage5() { return base + 203; }
    public int stage6() { return base + 300; }
    public int centerLabEntrance() { return base + 400; }
    public int bossMap() { return base + 401; }
    public int exitMap() { return base + 700; }

    public int entrance() {
        return isJulietSide() ? JULIET_ENTRANCE : ROMEO_ENTRANCE;
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

    // ---------------------------------------------------------------------------------------------------------- maps

    private int stageOf(int fid) {
        if (fid == stage1()) return 1;
        if (fid == stage2()) return 2;
        if (fid == stage3()) return 3;
        if (fid == stage4() || fid == darkLab1() || fid == darkLab2()) return 4;
        if (fid == stage5()) return 5;
        if (fid >= stage6() && fid <= stage6() + 4) return 6;
        if (fid == bossMap()) return 7;
        return 0;
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        int s = stageOf(field.getId());
        return s == 3 || s == 4; // Homunculi and Neo Huroids keep coming (liquids, card keys); the rest is once
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        // a unit's exit at the top of the Lab Tunnel only opens once someone has made it up
        if (stageOf(field.getId()) == 6 && field.getId() != stage6() && portal.getName() != null
                && portal.getName().equals("out00") && !isStageCleared(6)) {
            return false;
        }
        return true;
    }

    /**
     * A stage's way forward (portal scripts rnj*_out, rnj1_pt00, rnj12_in and the Juliet versions).
     */
    public void next(Char chr) {
        int fid = chr.getField().getId();
        int s = stageOf(fid);
        if (s == 0 || !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The way forward is still closed.");
            return;
        }
        int to = 0;
        if (fid == stage1()) to = stage2();
        else if (fid == stage2()) to = stage3();
        else if (fid == stage3()) to = stage4();
        else if (fid == stage4()) to = stage5();
        else if (fid == stage5()) to = stage6();
        if (to != 0) {
            chr.warp(to);
        }
    }

    /** Center Lab entrance -> Frankenroid. */
    public void enterBoss(Char chr) {
        if (isStageCleared(6)) {
            chr.warp(bossMap());
        }
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null) {
            int fid = newField.getId();
            if (fid == stage5()) {
                synchronized (this) {
                    if (!mutaeSpawned) {
                        mutaeSpawned = true;
                        for (int i = 0; i < 6; i++) {
                            var pos = newField.getRandomPosOnWalkableFoothold(50, false);
                            if (pos != null) {
                                newField.spawnMob(i % 2 == 0 ? IRON_MUTAE : MITHRIL_MUTAE, pos.getX(), pos.getY(),
                                        false, -1);
                            }
                        }
                        notice(newField, "Yulete: \"You won't get any further!\" Defeat the Mutae and find the switch.");
                    }
                }
            } else if (fid == bossMap()) {
                synchronized (this) {
                    if (!bossSpawned) {
                        bossSpawned = true;
                        newField.spawnMob(FRANKENROID, -150, -120, false, -1);
                        notice(newField, "Yulete summoned Frankenroid! Defeat it, and don't let "
                                + (isJulietSide() ? "Romeo" : "Juliet") + " fall!");
                    }
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    // ---------------------------------------------------------------------------------------------------------- clicks

    /**
     * A light spot / switch (portal script party5_click) was searched.
     */
    public synchronized void click(Char chr, int portalId) {
        var field = chr.getField();
        int s = stageOf(field.getId());
        if (s == 1 && !isStageCleared(1)) {
            if (portalId == stage1Switch) {
                clearStage(1, field, STAGE_PERCENT[1]);
                notice(field, chr.getName() + " found the switch! The door is open.");
            } else if (searched.add(portalId)) {
                findSomething(chr);
            } else {
                chr.chatMessage(ChatType.SystemNotice, "Nothing more here.");
            }
        } else if (s == 5 && !isStageCleared(5)) {
            if (!field.getMobs().isEmpty()) {
                chr.chatMessage(ChatType.SystemNotice, "Defeat Yulete's Mutae first.");
            } else if (portalId == stage5Switch) {
                clearStage(5, field, STAGE_PERCENT[5]);
                notice(field, chr.getName() + " found the switch! The way to the Lab Tunnel is open.");
            } else {
                chr.chatMessage(ChatType.SystemNotice, "Nothing here.");
            }
        }
    }

    private void findSomething(Char chr) {
        int roll = Util.getRandom(0, 9);
        if (roll < 3) {
            long exp = Math.max(1, getCompletionExp(chr) / 100);
            chr.addExpNoMsg(exp);
            chr.chatMessage(ChatType.SystemNotice, "You found some notes on alchemy. (+" + exp + " EXP)");
        } else if (roll < 5) {
            int mesos = 1000 * Util.getRandom(1, 5);
            chr.addMoney(mesos);
            chr.chatMessage(ChatType.SystemNotice, "You found " + mesos + " mesos.");
        } else {
            chr.chatMessage(ChatType.SystemNotice, "Nothing here.");
        }
    }

    // ---------------------------------------------------------------------------------------------------------- stage 3

    /**
     * A cracked beaker (reactor 2618000) was given a Suspicious Liquid.
     */
    public synchronized boolean fillBeaker(Char chr) {
        if (!chr.hasItem(SUSPICIOUS_LIQUID)) {
            chr.chatMessage(ChatType.SystemNotice, "Fill the beaker with a Suspicious Liquid from the Homunculi.");
            return false;
        }
        chr.consumeItem(SUSPICIOUS_LIQUID, 1);
        beakers++;
        var field = chr.getField();
        if (beakers >= 3) {
            clearStage(3, field, STAGE_PERCENT[3]);
            notice(field, "All 3 beakers are full! The way forward is open.");
        } else {
            notice(field, "A beaker is filled. Beakers left: " + (3 - beakers));
        }
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------- stage 4

    /**
     * A locked side door of the Special Lab (reactors 2618001 / 2618002) was given a Card Key.
     */
    public synchronized boolean openDoor(Char chr, int door) {
        if (doorsOpen.contains(door)) {
            return true;
        }
        if (!chr.hasItem(CARD_KEY)) {
            chr.chatMessage(ChatType.SystemNotice, "This door needs a Card Key. The Neo Huroids carry them.");
            return false;
        }
        chr.consumeItem(CARD_KEY, 1);
        doorsOpen.add(door);
        notice(chr.getField(), "A Dark Lab door is open!");
        return true;
    }

    /** The side door portals (rnj3_in0 / rnj3_in1). */
    public void enterDarkLab(Char chr, int door) {
        if (!doorsOpen.contains(door)) {
            chr.chatMessage(ChatType.SystemNotice, "The door is locked. Open it with a Card Key.");
            return;
        }
        chr.warp(door == 0 ? darkLab1() : darkLab2());
    }

    /**
     * Romeo/Juliet in the Special Lab: takes both experiment files.
     *
     * @return what they say
     */
    public synchronized String handInFiles(Char chr) {
        if (isStageCleared(4)) {
            return "Thank you! Now we can prove who the real thief is. Hurry on to Yulete's office!";
        }
        if (!chr.hasItem(ALCADNO_FILES) || !chr.hasItem(ZENUMIST_FILES)) {
            return "Please bring me both the #b#t" + ALCADNO_FILES + "##k and the #b#t" + ZENUMIST_FILES + "##k. "
                    + "They're hidden in crates in the two Dark Labs behind the locked doors.";
        }
        chr.consumeItem(ALCADNO_FILES, 1);
        chr.consumeItem(ZENUMIST_FILES, 1);
        clearStage(4, chr.getField(), STAGE_PERCENT[4]);
        return "These are the files! With these, we can prove it! The way to Yulete's office is open.";
    }

    // ---------------------------------------------------------------------------------------------------------- stage 6

    /** A door of the Lab Tunnel (rnj4_r1-4): into its unit. */
    public void enterUnit(Char chr, int unit) {
        chr.warp(stage6() + unit);
    }

    /**
     * A platform of a unit (portal script rnj5_rp).
     *
     * @return the portal to move to
     */
    public synchronized int platform(Char chr, int portalId) {
        var field = chr.getField();
        int unit = field.getId() - stage6() - 1;
        int idx = portalId - FIRST_UNIT_PLATFORM;
        if (unit < 0 || unit >= unitCode.length || idx < 0 || idx >= UNIT_FLOORS * 4) {
            return UNIT_FAIL_PORTAL;
        }
        int floor = idx / 4;
        if (idx % 4 != unitCode[unit][floor]) {
            return UNIT_FAIL_PORTAL;
        }
        if (floor < UNIT_FLOORS - 1) {
            return FIRST_UNIT_PLATFORM + (floor + 1) * 4;
        }
        if (clearStage(6, field, STAGE_PERCENT[6])) {
            getInstance().chatMessage(chr.getName() + " reached the top of the Lab Tunnel!");
            for (Char c : new ArrayList<>(getInstance().getChars())) {
                int f = c.getField() == null ? 0 : c.getField().getId();
                if (c != chr && f >= stage6() && f <= stage6() + 4) {
                    c.warp(centerLabEntrance());
                }
            }
        }
        return UNIT_SUCCESS_PORTAL;
    }

    // ---------------------------------------------------------------------------------------------------------- monsters

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int s = stageOf(field.getId());
        int t = mob.getTemplateId();
        if (s == 2 && field.getMobs().isEmpty()) {
            if (clearStage(2, field, STAGE_PERCENT[2])) {
                notice(field, "Every monster in the tunnel is down! The way forward is open.");
            }
        } else if (s == 3 && t == HOMUNCULUS && beakers < 3 && Util.succeedProp(40)) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(SUSPICIOUS_LIQUID)), mob.getPosition(), true); // a party quest item: it must land to be picked up
        } else if (s == 4 && t == NEO_HUROID && doorsOpen.size() < 2 && Util.succeedProp(25)) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(CARD_KEY)), mob.getPosition(), true); // a party quest item: it must land to be picked up
        } else if (s == 7) {
            if ((t == JULIET_MOB || t == ROMEO_MOB) && !finished) {
                failed = true;
                getInstance().chatMessage((t == JULIET_MOB ? "Juliet" : "Romeo")
                        + " has fallen... The quest has failed.");
                getInstance().setForcedReturn(entrance());
                addTimer(net.swordie.ms.handlers.executors.EventManager.addEvent(() -> getInstance().clear(), 5000));
            } else if (t == FRANKENROID && !failed && !finished) {
                finished = true;
                for (Mob m : new ArrayList<>(field.getMobs())) {
                    if (m.getTemplateId() != JULIET_MOB && m.getTemplateId() != ROMEO_MOB) {
                        m.die(false);
                    }
                }
                clearStage(7, field, STAGE_PERCENT[7]);
                giveTraitExp(Stat.willEXP, 29);
                giveTraitExp(Stat.senseEXP, 17);
                int marble = isJulietSide() ? ALCADNO_MARBLE : ZENUMIST_MARBLE;
                for (Char c : getInstance().getChars()) {
                    PartyQuestRecords.addClear(c, RECORD_KEY);
                    if (c.canHold(marble, 1)) {
                        c.addItemToInventory(marble, 1);
                    }
                }
                getInstance().chatMessage("Frankenroid is down and Magatia is saved! You received a marble. "
                        + "You'll be taken out in a moment.");
                getInstance().setForcedReturn(exitMap());
                addTimer(net.swordie.ms.handlers.executors.EventManager.addEvent(() -> getInstance().clear(), 8000));
            }
        }
        super.onMobDeath(field, mob);
    }

    private static void notice(Field field, String msg) {
        if (field != null) {
            for (Char c : field.getChars()) {
                c.chatMessage(ChatType.SystemNotice, msg);
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    protected long getBaseCompletionExp() {
        return 179_400L;
    }

    @Override
    protected double getExpGrowth() {
        return 1.037;
    }

    @Override
    protected int getExpMinLevel() {
        return 70;
    }

    @Override
    protected int getExpMaxLevel() {
        return 99;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Arrays.asList(SUSPICIOUS_LIQUID, CARD_KEY, ALCADNO_FILES, ZENUMIST_FILES);
    }
}

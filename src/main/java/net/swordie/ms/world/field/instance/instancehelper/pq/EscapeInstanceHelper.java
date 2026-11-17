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
 * Escape (Lion King's Castle party quest), as on live servers (maplestorywiki.net/w/Escape):
 * <ul>
 *     <li>Level 120+ (recommended 140-159), a party of 1-6, 5 entries a day, 20 minutes.</li>
 *     <li>Stage 1: Hidden Tower jump quest. One member reaching the top takes the whole party on.</li>
 *     <li>Stage 2: defeat every Grey Vulture and Castle Golem.</li>
 *     <li>Stage 3: the Dark Tower maze. One portal of each tower leads on (new every run), the others back to Dark
 *     Tower 1; Dark Tower 6 is behind a portal of Dark Tower 5. Whoever finds it takes the party on.</li>
 *     <li>Stage 4: defeat every Grey Vulture and Prison Guard Boar.</li>
 *     <li>Stage 5: the Last Trap jump quest.</li>
 *     <li>Stage 6: open the 4 prison gates with Prison Keys (from Rhinos and some boxes) and defeat every guard.</li>
 *     <li>Stage 7: defeat Prison Guard Ani. Everyone gets a Prison Guard Key; Jenn trades 5 for a Von Leon War Belt.</li>
 * </ul>
 * Completion EXP: 4,620,000 * 1.025^(level - 120) up to level 159 (times the server's EXP rate), split over the
 * stages; a clear also gives +70 Willpower EXP. Monsters are scaled to the party's level.
 */
public class EscapeInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "escape";
    public static final int MIN_LEVEL = 120;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int ENTRANCE = 921160000;
    public static final int STAGE_1 = 921160100;
    public static final int STAGE_2 = 921160200;
    public static final int DARK_TOWER_1 = 921160300;
    public static final int DARK_TOWER_6 = 921160350;
    public static final int STAGE_4 = 921160400;
    public static final int STAGE_5 = 921160500;
    public static final int STAGE_6 = 921160600;
    public static final int STAGE_BOSS = 921160700;

    public static final int PRISON_KEY = 4001528;
    public static final int PRISON_GUARD_KEY = 4001534;
    public static final int KEYS_PER_BELT = 5;
    public static final int[] BELTS = {1132094, 1132095, 1132096, 1132097, 1132098};

    private static final int PRISON_GUARD_BOAR = 9300452;
    private static final int PRISON_GUARD_RHINO = 9300453;
    public static final int PRISON_GUARD_ANI = 9300454;
    private static final int GATES = 4;

    private static final int[] STAGE_PERCENT = {0, 10, 15, 15, 15, 10, 15, 20};

    private final int[] mazeExit = new int[5]; // the portal (miro0-3) of each Dark Tower 1-5 that leads on
    private int gatesOpen;
    private boolean bossSpawned;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        for (int i = 0; i < mazeExit.length; i++) {
            mazeExit[i] = Util.getRandom(3);
        }
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

    /** Jenn's trade: 5 Prison Guard Keys for the chosen belt. */
    public static boolean tradeKeysForBelt(Char chr, int beltIndex) {
        if (beltIndex < 0 || beltIndex >= BELTS.length || !chr.hasItemCount(PRISON_GUARD_KEY, KEYS_PER_BELT)
                || !chr.canHold(BELTS[beltIndex], 1)) {
            return false;
        }
        chr.consumeItem(PRISON_GUARD_KEY, KEYS_PER_BELT);
        chr.addItemToInventory(BELTS[beltIndex], 1);
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------- maps

    private static int stageOf(int fieldId) {
        if (fieldId >= DARK_TOWER_1 && fieldId <= DARK_TOWER_6) {
            return 3;
        }
        switch (fieldId) {
            case STAGE_1:
                return 1;
            case STAGE_2:
                return 2;
            case STAGE_4:
                return 4;
            case STAGE_5:
                return 5;
            case STAGE_6:
                return 6;
            case STAGE_BOSS:
                return 7;
        }
        return 0;
    }

    private static int nextMapOf(int stage) {
        switch (stage) {
            case 1:
                return STAGE_2;
            case 2:
                return DARK_TOWER_1;
            case 3:
                return STAGE_4;
            case 4:
                return STAGE_5;
            case 5:
                return STAGE_6;
            case 6:
                return STAGE_BOSS;
        }
        return 0;
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        int stage = stageOf(field.getId());
        return stage == 3; // the Blood Reindeer of the maze are only in the way; the other stages are cleared once
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        int stage = stageOf(field.getId());
        if ("next00".equals(portal.getName()) && stage > 0 && !isStageCleared(stage)) {
            chr.chatMessage(ChatType.SystemNotice, "The way forward is not open yet.");
            return false;
        }
        return true;
    }

    /**
     * The next00 portal of a stage (prisonBreak_next): on to the next stage once this one is cleared.
     */
    public void next(Char chr) {
        var field = chr.getField();
        int stage = stageOf(field.getId());
        if (stage == 0 || !isStageCleared(stage)) {
            chr.chatMessage(ChatType.SystemNotice, "The portal is not open yet.");
            return;
        }
        if (stage == 3) {
            // Dark Tower 6: the finder brings the whole party out of the maze
            for (Char c : new ArrayList<>(getInstance().getChars())) {
                int f = c.getField() == null ? 0 : c.getField().getId();
                if (f >= DARK_TOWER_1 && f <= DARK_TOWER_6) {
                    c.warp(STAGE_4);
                }
            }
            return;
        }
        int next = nextMapOf(stage);
        if (next != 0) {
            chr.warp(next);
        }
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null) {
            int fid = newField.getId();
            if (fid == DARK_TOWER_6 && clearStage(3, newField, STAGE_PERCENT[3])) {
                getInstance().chatMessage(chr.getName() + " found Dark Tower 6! Take the portal at the bottom right.");
            } else if (fid == STAGE_BOSS) {
                synchronized (this) {
                    if (!bossSpawned) {
                        bossSpawned = true;
                        newField.spawnMob(PRISON_GUARD_ANI, -1281, -181, false, -1);
                    }
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    // ---------------------------------------------------------------------------------------------------------- jump quests

    /**
     * The goal of a jump quest (prison_1stage / prison_5stage): the stage is cleared and the party moves on.
     */
    public void jumpGoal(Char chr) {
        var field = chr.getField();
        int stage = stageOf(field.getId());
        if (stage != 1 && stage != 5) {
            return;
        }
        clearStage(stage, field, STAGE_PERCENT[stage]);
        int next = nextMapOf(stage);
        for (Char c : new ArrayList<>(field.getChars())) {
            c.warp(next);
        }
    }

    // ---------------------------------------------------------------------------------------------------------- maze

    /**
     * A maze portal (prisonBreak_miro, portal miro0-3) of a Dark Tower.
     *
     * @return the map it leads to
     */
    public int mazePortal(Char chr, String portalName) {
        int fid = chr.getField().getId();
        int tower = (fid - DARK_TOWER_1) / 10; // 0-5
        int idx = -1;
        if (portalName != null && portalName.startsWith("miro")) {
            try {
                idx = Integer.parseInt(portalName.substring(4));
            } catch (NumberFormatException ignored) {
            }
        }
        if (tower < 0 || tower >= mazeExit.length) {
            return DARK_TOWER_1;
        }
        if (idx == mazeExit[tower]) {
            return DARK_TOWER_1 + (tower + 1) * 10;
        }
        return DARK_TOWER_1;
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
        if (fid == STAGE_2 || fid == STAGE_4) {
            checkKillStage(field);
        } else if (fid == STAGE_6) {
            if (mob.getTemplateId() == PRISON_GUARD_RHINO) {
                field.drop(new Drop(-1, ItemData.getItemDeepCopy(PRISON_KEY)), mob.getPosition(), true); // a quest item: it must land to be picked up
            }
            checkStage6(field);
        } else if (fid == STAGE_BOSS && mob.getTemplateId() == PRISON_GUARD_ANI && !finished) {
            finished = true;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(7, field, STAGE_PERCENT[7]);
            giveTraitExp(Stat.willEXP, 70);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
                if (c.canHold(PRISON_GUARD_KEY, 1)) {
                    c.addItemToInventory(PRISON_GUARD_KEY, 1);
                }
            }
            getInstance().chatMessage("The prisoners are free! You got a Prison Guard Key. Leave through the "
                    + "portal and trade 5 keys with Jenn for a Von Leon War Belt.");
        }
        super.onMobDeath(field, mob);
    }

    private void checkKillStage(Field field) {
        if (field.getMobs().isEmpty() && field.getChars().size() > 0) {
            int stage = stageOf(field.getId());
            if (clearStage(stage, field, STAGE_PERCENT[stage])) {
                for (Char c : field.getChars()) {
                    c.chatMessage(ChatType.SystemNotice, "All the guards are down! The portal is open.");
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------- stage 6

    /**
     * A prison gate was opened with a Prison Key (reactor 9218000).
     */
    public synchronized boolean openGate(Char chr) {
        if (!chr.hasItem(PRISON_KEY)) {
            chr.chatMessage(ChatType.SystemNotice, "You need a Prison Key in order to open the prison.");
            return false;
        }
        chr.consumeItem(PRISON_KEY, 1);
        gatesOpen++;
        var field = chr.getField();
        for (Char c : field.getChars()) {
            c.chatMessage(ChatType.SystemNotice, gatesOpen >= GATES ? "All the prison gates are open!"
                    : "A prison gate is open. Gates left: " + (GATES - gatesOpen));
        }
        checkStage6(field);
        return true;
    }

    private void checkStage6(Field field) {
        if (gatesOpen >= GATES && field.getMobs().isEmpty()) {
            if (clearStage(6, field, STAGE_PERCENT[6])) {
                for (Char c : field.getChars()) {
                    c.chatMessage(ChatType.SystemNotice, "The prisoners are free! On to the Prison Guard.");
                }
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    protected long getBaseCompletionExp() {
        return 4_620_000L;
    }

    @Override
    protected int getExpMinLevel() {
        return 120;
    }

    @Override
    protected int getExpMaxLevel() {
        return 159;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.singletonList(PRISON_KEY);
    }
}

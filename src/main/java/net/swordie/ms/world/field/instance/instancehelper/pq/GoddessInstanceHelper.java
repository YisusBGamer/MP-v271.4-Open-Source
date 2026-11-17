package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * Remnant of the Goddess (Orbis party quest), as on live servers (maplestorywiki.net/w/Remnant_of_the_Goddess).
 * From the Center Tower, Chamberlain Eak sends the party into four rooms, each holding a Goddess Statue Piece:
 * <ul>
 *     <li>Lounge: play the right LP on the phonograph. Today's music depends on the weekday (Sunday Operatic, Monday
 *     Cute, Tuesday Scary, Wednesday Fun, Thursday Sad, Friday Cold, Saturday Tight).</li>
 *     <li>Storage: leave exactly the number of Cellions, Grupins and Lioners alive that Eak asks for (the cats can be
 *     reset up to 10 times).</li>
 *     <li>Walkway: defeat all 30 pixies.</li>
 *     <li>On the Way Up: one platform per floor leads up; reach the top.</li>
 * </ul>
 * With all four pieces the leader restores the statue; the Garden opens. Defeat the Nependeaths, then Papa Pixie, and
 * Minerva is freed.
 * <p>
 * Level 70+, a party of 1-4, 5 entries a day, 30 minutes. Completion EXP follows the live formula (times the server's
 * EXP rate), split over the stages; a clear also gives +6 Willpower and +3 Insight EXP.
 */
public class GoddessInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "goddess";
    public static final int MIN_LEVEL = 70;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 4;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 30 * 60;

    public static final int ENTRANCE = 920010000;
    /** The Cross World waiting room (its exit leads back to the hub); the tower entrance has no way out. */
    public static final int WAITING_ROOM = 933030000;
    public static final int CENTER = 920010100;
    public static final int WALKWAY = 920010200;
    public static final int STORAGE = 920010300;
    public static final int LOUNGE = 920010400;
    public static final int WAY_UP = 920010700;
    public static final int GARDEN = 920010800;
    public static final int BLESSING = 920011300;

    public static final int[] PIECES = {4001818, 4001819, 4001820, 4001821};
    public static final int[] LPS = {4001056, 4001057, 4001058, 4001059, 4001060, 4001061, 4001062};
    public static final String[] LP_NAMES = {"Operatic", "Cute", "Scary", "Fun", "Sad", "Cold", "Tight"};

    private static final int CELLION = 9300041;
    private static final int GRUPIN = 9300042;
    private static final int LIONER = 9300043;
    private static final int[] CATS = {CELLION, GRUPIN, LIONER};
    private static final String[] CAT_NAMES = {"Cellion", "Grupin", "Lioner"};
    private static final int CATS_EACH = 5;
    private static final int MAX_RESETS = 10;
    private static final int PAPA_PIXIE = 9300039;

    private static final int WAY_UP_FIRST = 24; // portals rp011-rp084: ids 24-55, 4 per floor
    private static final int WAY_UP_FLOORS = 8;
    private static final int WAY_UP_FAIL = 5;
    private static final int WAY_UP_TOP = 2;

    // stages: 1-4 the rooms, 5 the statue, 6 the garden
    private static final int[] STAGE_PERCENT = {0, 10, 10, 15, 15, 5, 45};

    private final int[] catTargets = new int[3];
    private int resets;
    private boolean catsSpawned;
    private final int[] wayUpCode = new int[WAY_UP_FLOORS];
    private boolean papaSpawned;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        for (int i = 0; i < catTargets.length; i++) {
            catTargets[i] = Util.getRandom(1, 4);
        }
        for (int i = 0; i < WAY_UP_FLOORS; i++) {
            wayUpCode[i] = Util.getRandom(3);
        }
        instance.setForcedReturn(WAITING_ROOM);
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

    // ---------------------------------------------------------------------------------------------------------- rooms

    /** party3_room*: Eak's doors from the Center Tower. */
    public void enterRoom(Char chr, int room) {
        int to = 0;
        switch (room) {
            case 1:
                to = LOUNGE;
                break;
            case 2:
                to = STORAGE;
                break;
            case 3:
                to = WALKWAY;
                break;
            case 6:
                to = WAY_UP;
                break;
            case 8:
                if (!isStageCleared(5)) {
                    chr.chatMessage(ChatType.SystemNotice, "The Garden is sealed until the statue is restored.");
                    return;
                }
                to = GARDEN;
                break;
            default:
                chr.chatMessage(ChatType.SystemNotice, "This room is locked.");
                return;
        }
        chr.warp(to);
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        return false;
    }

    @Override
    public boolean canSpawnMapMobs(Field field) {
        return field.getId() != STORAGE; // the cats are counted out by Eak
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null && newField.getId() == STORAGE) {
            synchronized (this) {
                if (!catsSpawned) {
                    catsSpawned = true;
                    spawnCats(newField);
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    private void spawnCats(Field field) {
        for (int cat : CATS) {
            for (int i = 0; i < CATS_EACH; i++) {
                Position pos = field.getRandomPosOnWalkableFoothold(50, false);
                if (pos != null) {
                    field.spawnMob(cat, pos.getX(), pos.getY(), false, -1);
                }
            }
        }
    }

    private void givePiece(Field field, int piece) {
        Char to = null;
        for (Char c : getInstance().getChars()) {
            if (isLeader(c)) {
                to = c;
            }
        }
        if (to == null && !getInstance().getChars().isEmpty()) {
            to = getInstance().getChars().get(0);
        }
        if (to != null && to.canHold(PIECES[piece - 1], 1)) {
            to.addItemToInventory(PIECES[piece - 1], 1);
        }
        clearStage(piece, field, STAGE_PERCENT[piece]);
        getInstance().chatMessage("The " + ordinal(piece) + " Goddess Statue Piece was found"
                + (to == null ? "!" : " and given to " + to.getName() + "!"));
    }

    private static String ordinal(int n) {
        return n == 1 ? "1st" : n == 2 ? "2nd" : n == 3 ? "3rd" : n + "th";
    }

    // ---------------------------------------------------------------------------------------------------------- lounge

    /** @return the LP that fits today's music (index into LPS) */
    public static int todaysLp() {
        DayOfWeek d = net.swordie.ms.util.ResetClock.today().getDayOfWeek();
        return d.getValue() % 7; // Sunday = 0
    }

    /** Eak in the Lounge: the phonograph. */
    public synchronized String playLp(Char chr, int lp) {
        if (isStageCleared(1)) {
            return "The music already played. Go on to the next room!";
        }
        if (lp == todaysLp()) {
            givePiece(chr.getField(), 1);
            return "What lovely music! The 1st Goddess Statue Piece fell out of the phonograph!";
        }
        return "Hmm, that music doesn't suit today at all...";
    }

    // ---------------------------------------------------------------------------------------------------------- storage

    public String getCatOrder() {
        return "Leave exactly #b" + catTargets[0] + " " + CAT_NAMES[0] + "#k, #b" + catTargets[1] + " " + CAT_NAMES[1]
                + "#k and #b" + catTargets[2] + " " + CAT_NAMES[2] + "#k alive.";
    }

    /** Eak in the Storage: checks the cats. */
    public synchronized String checkCats(Char chr) {
        if (isStageCleared(2)) {
            return "The cats are behaving. Go on to the next room!";
        }
        var field = chr.getField();
        int[] alive = new int[3];
        for (Mob m : field.getMobs()) {
            for (int i = 0; i < CATS.length; i++) {
                if (m.getTemplateId() == CATS[i]) {
                    alive[i]++;
                }
            }
        }
        if (Arrays.equals(alive, catTargets)) {
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            givePiece(field, 2);
            return "Perfect! The 2nd Goddess Statue Piece was hidden under the cats.";
        }
        boolean tooFew = false;
        for (int i = 0; i < 3; i++) {
            tooFew |= alive[i] < catTargets[i];
        }
        if (tooFew) {
            if (resets >= MAX_RESETS) {
                return "Too many cats were hurt... I can't bring any more back.";
            }
            resets++;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            spawnCats(field);
            return "Too many cats were defeated! I've brought them back (" + (MAX_RESETS - resets) + " resets left). "
                    + getCatOrder();
        }
        return "Not yet. " + getCatOrder() + " (Now: " + alive[0] + " " + CAT_NAMES[0] + ", " + alive[1] + " "
                + CAT_NAMES[1] + ", " + alive[2] + " " + CAT_NAMES[2] + ")";
    }

    // ---------------------------------------------------------------------------------------------------------- way up

    /** party3_r6pt: a platform of On the Way Up. @return the portal to move to */
    public synchronized int wayUp(Char chr, int portalId) {
        int idx = portalId - WAY_UP_FIRST;
        if (idx < 0 || idx >= WAY_UP_FLOORS * 4) {
            return WAY_UP_FAIL;
        }
        int floor = idx / 4;
        if (idx % 4 != wayUpCode[floor]) {
            return WAY_UP_FAIL;
        }
        if (floor < WAY_UP_FLOORS - 1) {
            return WAY_UP_FIRST + (floor + 1) * 4;
        }
        if (!isStageCleared(4)) {
            givePiece(chr.getField(), 4);
        }
        return WAY_UP_TOP;
    }

    // ---------------------------------------------------------------------------------------------------------- statue

    /** Eak in the Center Tower: the leader places the 4 pieces. */
    public synchronized String restoreStatue(Char chr) {
        if (isStageCleared(5)) {
            return finished ? "Minerva is free! Thank you!" : "The statue is restored! The Garden is open: defeat Papa "
                    + "Pixie!";
        }
        // counted by the stages: a piece the leader couldn't hold, or that left with a member, still counts
        int found = 0;
        for (int i = 1; i <= 4; i++) {
            found += isStageCleared(i) ? 1 : 0;
        }
        if (found < 4) {
            return "Bring me all 4 Goddess Statue Pieces (" + found + " found). The Lounge, the Storage, the "
                    + "Walkway and On the Way Up each hide one.";
        }
        for (int p : PIECES) {
            if (chr.hasItem(p)) {
                chr.consumeItem(p, 1);
            }
        }
        clearStage(5, chr.getField(), STAGE_PERCENT[5]);
        return "The statue is restored! The way to the #bGarden#k is open. Papa Pixie is hiding there!";
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
        if (fid == WALKWAY && field.getMobs().isEmpty() && !isStageCleared(3)) {
            givePiece(field, 3);
        } else if (fid == GARDEN) {
            if (mob.getTemplateId() == PAPA_PIXIE && !finished) {
                finished = true;
                clearStage(6, field, STAGE_PERCENT[6]);
                giveTraitExp(Stat.willEXP, 6);
                giveTraitExp(Stat.insightEXP, 3);
                givePartyPoints(10); // a Cross World party quest on live servers
                for (Char c : getInstance().getChars()) {
                    PartyQuestRecords.addClear(c, RECORD_KEY);
                }
                getInstance().chatMessage("Papa Pixie is defeated and Minerva is free! You'll be taken to the "
                        + "Goddess' Blessing.");
                getInstance().setForcedReturn(BLESSING);
                addTimer(EventManager.addEvent(() -> getInstance().clear(), 8000));
            } else if (field.getMobs().isEmpty() && !papaSpawned) {
                papaSpawned = true;
                Position pos = field.getRandomPosOnWalkableFoothold(100, false);
                if (pos != null) {
                    field.spawnMob(PAPA_PIXIE, pos.getX(), pos.getY(), false, -1);
                }
                for (Char c : field.getChars()) {
                    c.chatMessage(ChatType.SystemNotice, "Papa Pixie appeared!");
                }
            }
        }
        super.onMobDeath(field, mob);
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public long getCompletionExp(Char chr) {
        int lv = Math.max(70, chr.getLevel());
        double exp;
        if (lv < 100) {
            exp = Math.round(1.1 * (450 + lv)) * 345 * Math.pow(1.03, lv - 70);
        } else if (lv < 110) {
            exp = Math.round(1.1 * (480 + lv)) * 345 * Math.pow(1.03, lv - 70);
        } else if (lv < 120) {
            exp = (lv - 65) * 7590 * Math.pow(1.03, lv - 70);
        } else if (lv < 130) {
            exp = lv * 23100.0 * Math.pow(1.02, lv - 120);
        } else if (lv < 140) {
            exp = (lv + 20) * 23100.0 * Math.pow(1.02, lv - 120);
        } else if (lv < 150) {
            exp = (2 * lv - 70) * 23100.0 * Math.pow(1.02, lv - 120);
        } else if (lv < 170) {
            exp = (2 * lv - 10) * 23100.0 * Math.pow(1.02, lv - 120);
        } else {
            exp = (lv + 230) * 23100.0 * Math.pow(1.02, Math.min(lv, 249) - 120);
        }
        return (long) (exp * Math.max(1, ServerRates.getExpRate()));
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        List<Integer> items = new ArrayList<>();
        for (int p : PIECES) {
            items.add(p);
        }
        return items;
    }
}

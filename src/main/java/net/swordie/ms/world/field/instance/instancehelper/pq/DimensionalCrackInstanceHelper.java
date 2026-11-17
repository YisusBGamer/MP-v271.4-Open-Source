package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * Dimensional Crack (Ludibrium party quest), as on live servers
 * (https://strategywiki.org/wiki/MapleStory/Dimensional_Crack):
 * <ul>
 *     <li>Level 120+, a party of 3-6 in the entrance map, 5 entries a day, 20 minutes.</li>
 *     <li>Stage 1: defeat the Ratz and collect the 20 Dimensional Passes they drop.</li>
 *     <li>Stage 2: defeat the 14 Shadow/Dark Eyes in the five dark rooms.</li>
 *     <li>Stage 3: find the right box of each row (the code is new every run) and reach the top; the whole party is
 *     brought up when one member gets there.</li>
 *     <li>Stage 4: defeat the 4 Rombots, then talk to the Sky-Blue Balloon.</li>
 *     <li>Stage 5: the leader gets a sum from the Blue Balloon; three members stand on the boxes of its digits.</li>
 *     <li>Stage 6: defeat Alishar (calls Chronos at 75%, 50% and 25% HP), then talk to the Violet Balloon.</li>
 * </ul>
 * Each stage gives its share of the live completion EXP (2,562,000 * 1.025^(level-120) up to level 139, times the
 * server's EXP rate; 10/15/10/15/15/35%), and a clear +33 Willpower and +20 Insight EXP. Monsters are scaled to the
 * party's level.
 * Broken Glasses come from the Red Sign every 5 clears.
 */
public class DimensionalCrackInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "lpq";
    public static final int MIN_LEVEL = 120;
    public static final int MIN_PARTY = 3;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;
    public static final int CLEARS_PER_GLASSES = 5;
    public static final int BROKEN_GLASSES = 1022073;

    public static final int ENTRANCE = 221023300;
    public static final int END_OF_JOURNEY = 922010000;
    public static final int STAGE_1 = 922010100;
    public static final int STAGE_2 = 922010400;
    public static final int STAGE_2_ROOM_FIRST = 922010401;
    public static final int STAGE_2_ROOM_LAST = 922010405;
    public static final int STAGE_3 = 922010600;
    public static final int STAGE_4 = 922010700;
    public static final int STAGE_5 = 922010800;
    public static final int STAGE_BOSS = 922010900;
    public static final int EXIT_MAP = 922011100;

    public static final int DIMENSIONAL_PASS = 2430115;
    public static final int PASSES_NEEDED = 20;

    public static final int ALISHAR = 2600622;
    private static final Position ALISHAR_POS = new Position(979, 184);
    private static final int RATZ = 9300005; // stage 1: each drops a Dimensional Pass
    private static final int BLACK_RATZ = 9300006;
    private static final int CHRONOS = 2600610;
    private static final int PLATOON_CHRONOS = 2600611;
    private static final int MASTER_CHRONOS = 2600612;

    private static final int[] STAGE_PERCENT = {0, 10, 15, 10, 15, 15, 35};

    // stage 3: for each box portal (ids 15-44, 3 per row), its row and the number on it
    private static final int FIRST_BOX_PORTAL = 15;
    private static final int[][] BOX_NUMBERS = {
            {3, 2, 1}, {1, 2, 3}, {3, 2, 1}, {3, 2, 1}, {1, 2, 3},
            {1, 2, 3}, {3, 1, 2}, {3, 2, 1}, {1, 3, 2}, {1, 2, 3}
    };
    private static final int STAGE_3_START_PORTAL = 0;
    private static final int STAGE_3_TOP_PORTAL = 14; // next00, at the top

    // stage 5: the nine boxes (Map.wz 922010800 area 0-8 = boxes 1-9)
    private static final Rect[] PLATFORMS = {
            new Rect(-27, -575, 37, -535), new Rect(72, -575, 134, -535),
            new Rect(-129, -360, -66, -318), new Rect(-37, -360, 24, -318), new Rect(51, -360, 117, -318),
            new Rect(-245, -160, -186, -124), new Rect(-165, -160, -104, -124), new Rect(-86, -160, -24, -124),
            new Rect(3, -160, 69, -124)
    };

    private final int[] boxCode = new int[BOX_NUMBERS.length];
    private int passesCollected;
    private final Set<Integer> clearedRooms = new HashSet<>();
    private boolean bossSpawned;
    private final Set<Integer> alisharCalls = new HashSet<>();
    private int sumA;
    private int sumB;
    private Set<Integer> answerDigits;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        for (int i = 0; i < boxCode.length; i++) {
            boxCode[i] = Util.getRandom(1, 4); // 1-3
        }
        instance.setForcedReturn(END_OF_JOURNEY);
        instance.setTimeout(TIME_LIMIT_SECONDS);
    }

    @Override
    protected void startStage(int stageNumber) {
        // the stages are driven by the players (monsters, NPCs and portals), not by timers
    }

    // ---------------------------------------------------------------------------------------------------------- entry

    /**
     * @return why the party can't enter (shown by the Red Sign), or null if it can
     */
    public static String getEntryBlocker(Char leader) {
        var party = leader.getParty();
        if (party == null) {
            return "Please create a party of " + MIN_PARTY + " to " + MAX_PARTY + " members before entering.";
        }
        if (party.getPartyLeaderID() != leader.getId()) {
            return "Please have your party leader speak to me.";
        }
        var members = party.getPartyMembersInField(leader);
        if (members.size() < MIN_PARTY || members.size() > MAX_PARTY) {
            return "You need " + MIN_PARTY + " to " + MAX_PARTY + " party members here with you to enter. "
                    + "(There are " + members.size() + " here now.)";
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

    /**
     * Counts today's entry for everyone going in (called by the Red Sign right before entering).
     */
    public static void recordEntries(Char leader) {
        for (Char c : leader.getParty().getPartyMembersInField(leader)) {
            PartyQuestRecords.addEntry(c, RECORD_KEY);
        }
    }

    /** @return Broken Glasses the character can still claim (one per 5 clears) */
    public static int getClaimableGlasses(Char chr) {
        int earned = PartyQuestRecords.getClears(chr, RECORD_KEY) / CLEARS_PER_GLASSES;
        return Math.max(0, earned - PartyQuestRecords.getRewardsGiven(chr, RECORD_KEY));
    }

    /** @return clears needed for the next pair of Broken Glasses */
    public static int getClearsUntilGlasses(Char chr) {
        int clears = PartyQuestRecords.getClears(chr, RECORD_KEY);
        return CLEARS_PER_GLASSES - clears % CLEARS_PER_GLASSES;
    }

    public static boolean claimGlasses(Char chr) {
        if (getClaimableGlasses(chr) <= 0 || !chr.canHold(BROKEN_GLASSES, 1)) {
            return false;
        }
        chr.addItemToInventory(BROKEN_GLASSES, 1);
        PartyQuestRecords.addRewardGiven(chr, RECORD_KEY);
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------- portals

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        if (!"next00".equals(portal.getName())) {
            return true;
        }
        int stage = getStageOfField(field.getId());
        if (stage > 0 && !isStageCleared(stage)) {
            chr.chatMessage(ChatType.SystemNotice, "The way forward is still sealed. Clear this stage first.");
            return false;
        }
        return true;
    }

    private static int getStageOfField(int fieldId) {
        switch (fieldId) {
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
            case STAGE_BOSS:
                return 6;
        }
        return 0;
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        return field.getId() == STAGE_1; // the Ratz keep coming until the passes are in; everything else is once
    }

    // ---------------------------------------------------------------------------------------------------------- monsters

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        int t = mob.getTemplateId();
        if (t != ALISHAR && t != CHRONOS && t != PLATOON_CHRONOS && t != MASTER_CHRONOS) {
            scaleMobToParty(mob);
        }
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onItemLooted(Char chr, Item item, int quantity) {
        if (item.getItemId() == DIMENSIONAL_PASS && !isStageCleared(1)) {
            passesCollected += Math.max(1, quantity);
            var field = getInstance().getField(STAGE_1);
            if (passesCollected >= PASSES_NEEDED) {
                for (Char c : getInstance().getChars()) {
                    int have = c.getInventories().stream().mapToInt(inv -> inv.getQuantity(DIMENSIONAL_PASS)).sum();
                    if (have > 0) {
                        c.consumeItem(DIMENSIONAL_PASS, have);
                    }
                }
                clearStage(1, field, STAGE_PERCENT[1]);
                notice(field, "All 20 passes collected! Head through the portal by the Red Balloon.");
            } else {
                notice(field, String.format("Dimensional Passes: %d / %d", passesCollected, PASSES_NEEDED));
            }
        }
        super.onItemLooted(chr, item, quantity);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int fieldId = field.getId();
        if (fieldId == STAGE_1 && !isStageCleared(1) && field.getMobs().isEmpty()) {
            // the map's Ratz come once (mobTime -1); a pass that vanished or couldn't be looted needs new ones
            int missing = Math.max(1, PASSES_NEEDED - passesCollected);
            for (int i = 0; i < missing; i++) {
                Position pos = field.getRandomPosOnWalkableFoothold(50, false);
                if (pos != null) {
                    field.spawnMob(i % 2 == 0 ? RATZ : BLACK_RATZ, pos.getX(), pos.getY(), false, -1);
                }
            }
            notice(field, String.format("More Ratz come out of the crack! Dimensional Passes: %d / %d",
                    passesCollected, PASSES_NEEDED));
        } else if (fieldId >= STAGE_2_ROOM_FIRST && fieldId <= STAGE_2_ROOM_LAST && field.getMobs().isEmpty()) {
            clearedRooms.add(fieldId);
            int left = (STAGE_2_ROOM_LAST - STAGE_2_ROOM_FIRST + 1) - clearedRooms.size();
            if (left <= 0) {
                clearStage(2, getInstance().getField(STAGE_2), STAGE_PERCENT[2]);
                notice(field, "Every eye in the darkness is gone! Head through the portal by the Lime Balloon.");
            } else {
                notice(field, "This room is clear. Dark rooms left: " + left);
            }
        } else if (fieldId == STAGE_4 && field.getMobs().isEmpty()) {
            notice(field, "All the Rombots are down! Talk to the Sky-Blue Balloon.");
        } else if (mob.getTemplateId() == ALISHAR && !finished) {
            finished = true;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(6, field, STAGE_PERCENT[6]);
            giveTraitExp(net.swordie.ms.enums.Stat.willEXP, 33);
            giveTraitExp(net.swordie.ms.enums.Stat.insightEXP, 20);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
                if (getClaimableGlasses(c) > 0) {
                    c.chatMessage(ChatType.SystemNotice, "You can now get Broken Glasses from the Red Sign!");
                }
            }
            notice(field, "Alishar is defeated! Talk to the Violet Balloon to leave.");
        }
        super.onMobDeath(field, mob);
    }

    @Override
    public synchronized void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        if (mob.getTemplateId() == ALISHAR && mob.getMaxHp() > 0) {
            long pct = newHp * 100 / mob.getMaxHp();
            var field = mob.getField();
            var pos = mob.getPosition();
            if (pct <= 75 && alisharCalls.add(75)) {
                summon(field, pos, CHRONOS, CHRONOS);
            }
            if (pct <= 50 && alisharCalls.add(50)) {
                summon(field, pos, CHRONOS, PLATOON_CHRONOS, PLATOON_CHRONOS);
            }
            if (pct <= 25 && alisharCalls.add(25)) {
                summon(field, pos, MASTER_CHRONOS, PLATOON_CHRONOS, CHRONOS);
            }
        }
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
    }

    private void summon(Field field, Position pos, int... templates) {
        if (field == null || pos == null || bossGone(field)) {
            return;
        }
        int offset = -120;
        for (int t : templates) {
            field.spawnMob(t, pos.getX() + offset, pos.getY(), false, -1);
            offset += 120;
        }
    }

    private boolean bossGone(Field field) {
        return finished || field.getMobs().stream().noneMatch(m -> m.getTemplateId() == ALISHAR);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null && newField.getId() == STAGE_BOSS) {
            synchronized (this) {
                if (!bossSpawned) {
                    bossSpawned = true;
                    newField.spawnMob(ALISHAR, ALISHAR_POS.getX(), ALISHAR_POS.getY(), false, -1);
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    // ---------------------------------------------------------------------------------------------------------- stage 3

    /**
     * A box of stage 3 was entered (portal script party2_rp).
     *
     * @return the portal id the character is moved to: the next row on the right box, the bottom on a wrong one
     */
    public synchronized int enterBox(Char chr, int portalId) {
        int idx = portalId - FIRST_BOX_PORTAL;
        if (idx < 0 || idx >= BOX_NUMBERS.length * 3 || isStageCleared(3)) {
            return STAGE_3_TOP_PORTAL;
        }
        int row = idx / 3;
        int col = idx % 3;
        var field = chr.getField();
        if (BOX_NUMBERS[row][col] != boxCode[row]) {
            return STAGE_3_START_PORTAL;
        }
        if (field != null) {
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromObject("an" + row + col)));
        }
        if (row < BOX_NUMBERS.length - 1) {
            return FIRST_BOX_PORTAL + (row + 1) * 3; // stand at the start of the next row
        }
        // the top: everyone in the stage comes up
        clearStage(3, field, STAGE_PERCENT[3]);
        if (field != null) {
            for (Char c : field.getChars()) {
                if (c != chr) {
                    c.getScriptManager().teleportToPortal(STAGE_3_TOP_PORTAL);
                }
            }
        }
        return STAGE_3_TOP_PORTAL;
    }

    // ---------------------------------------------------------------------------------------------------------- stage 4

    /**
     * The Sky-Blue Balloon: clears stage 4 once all Rombots are down.
     *
     * @return whether stage 4 is (now) cleared
     */
    public boolean checkStage4(Char chr) {
        var field = chr.getField();
        if (isStageCleared(4)) {
            return true;
        }
        if (field == null || field.getId() != STAGE_4 || !field.getMobs().isEmpty()) {
            return false;
        }
        clearStage(4, field, STAGE_PERCENT[4]);
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------- stage 5

    /**
     * @return the stage 5 question, made the first time it is asked: a sum whose answer has three different digits
     * from 1 to 9
     */
    public synchronized String getStage5Question() {
        if (answerDigits == null) {
            int answer;
            do {
                answer = Util.getRandom(123, 987);
            } while (!validAnswer(answer));
            sumA = Util.getRandom(answer / 4, answer * 3 / 4);
            sumB = answer - sumA;
            answerDigits = digits(answer);
        }
        return sumA + " + " + sumB + " = ?";
    }

    private static boolean validAnswer(int n) {
        int a = n / 100, b = n / 10 % 10, c = n % 10;
        return a > 0 && b > 0 && c > 0 && a != b && b != c && a != c;
    }

    private static Set<Integer> digits(int n) {
        return new HashSet<>(Arrays.asList(n / 100, n / 10 % 10, n % 10));
    }

    /**
     * @return the box (1-9) the character stands on, 0 if none
     */
    private static int platformOf(Char chr) {
        Position p = chr.getPosition();
        if (p == null) {
            return 0;
        }
        for (int i = 0; i < PLATFORMS.length; i++) {
            Rect r = PLATFORMS[i];
            if (p.getX() >= r.getLeft() && p.getX() <= r.getRight() && p.getY() >= r.getTop() && p.getY() <= r.getBottom()) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * The Blue Balloon's check: exactly three characters must stand on the boxes of the answer's digits.
     *
     * @return null when cleared, or what is wrong
     */
    public synchronized String checkStage5(Char chr) {
        if (isStageCleared(5)) {
            return null;
        }
        if (answerDigits == null) {
            return "Ask me for the question first.";
        }
        var field = chr.getField();
        if (field == null || field.getId() != STAGE_5) {
            return "You're not in the right place.";
        }
        Set<Integer> occupied = new HashSet<>();
        int onBoxes = 0;
        for (Char c : field.getChars()) {
            int box = platformOf(c);
            if (box > 0) {
                onBoxes++;
                occupied.add(box);
            }
        }
        if (onBoxes != 3) {
            return "Exactly 3 members must stand on the boxes. Right now there " + (onBoxes == 1 ? "is " : "are ")
                    + onBoxes + ".";
        }
        if (!occupied.equals(answerDigits)) {
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz("quest/party/wrong_kor", 0)));
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.playSound("Party1/Failed", 100)));
            return "That's not the right answer. Try again!";
        }
        clearStage(5, field, STAGE_PERCENT[5]);
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------- end

    /** @return the passes counted so far (what stage 1's notice shows) */
    public int getPassesCollected() {
        return passesCollected;
    }

    public boolean isFinished() {
        return finished;
    }

    /**
     * The Violet Balloon after Alishar: everyone leaves to the exit map, where Arturo sends them home.
     */
    public void leave() {
        getInstance().setForcedReturn(EXIT_MAP);
        getInstance().setForcedReturnPortalId(0);
        getInstance().clear();
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
        return 2_562_000L; // live: 2,562,000 * 1.025^(level - 120), up to level 139
    }

    @Override
    protected int getExpMinLevel() {
        return 120;
    }

    @Override
    protected int getExpMaxLevel() {
        return 139;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.singletonList(DIMENSIONAL_PASS);
    }
}

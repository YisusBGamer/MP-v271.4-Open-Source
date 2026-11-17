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
 * First Time Together (Cross World party quest), as on live servers (maplestorywiki.net/w/First_Time_Together):
 * <ol>
 *     <li>Coupons: Cloto asks each member other than the leader for 3-8 Coupons (dropped by Ligators). Bring the exact
 *     number; once everyone is done, the leader talks to Cloto.</li>
 *     <li>Vines: 3 of the 4 vines are right. Members hang on different vines while the leader asks Cloto.</li>
 *     <li>Platforms: 3 of the 5 platforms (1-5 cats, numbered from the left) are right; each try says how many are.
 *     Nella hands out Platform Puppets when fewer than 3 members can stand.</li>
 *     <li>Math: reach Cloto's number. Curse Eyes drop Numbers 1-4; drop one on a sign (+, -, x, /) to apply it to the
 *     current number. The same sign can't be used twice in a row.</li>
 *     <li>Defeat King Slime, then leave from the Returning Path.</li>
 * </ol>
 * Level 70+, a party of 4, 5 entries a day, 20 minutes. Rewards: the Cross World completion EXP (times the server's
 * EXP rate), split over the stages, +6 Willpower and +3 Insight EXP and 10 Party Points.
 */
public class FirstTimeTogetherInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "first_time_together";
    public static final int MIN_LEVEL = 70;
    public static final int PARTY_SIZE = 4;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int WAITING_ROOM = 933010000;
    public static final int STAGE_1 = 933011000;
    public static final int STAGE_2 = 933012000;
    public static final int STAGE_3 = 933013000;
    public static final int STAGE_4 = 933014000;
    public static final int STAGE_5 = 933015000;
    public static final int RETURNING_PATH = 933019000;

    public static final int COUPON = 4001007;
    public static final int[] NUMBERS = {4034330, 4034331, 4034332, 4034333}; // Number 1-4
    private static final int LIGATOR = 9300910;
    private static final int CURSE_EYE = 9300911;
    private static final int KING_SLIME = 9300912;
    private static final int[] STAGE_PERCENT = {0, 15, 15, 15, 15, 40};

    // vines (stage 2), from the left: x, top y, bottom y
    private static final int[][] VINES = {{-956, -133, 89}, {-785, -142, 89}, {-646, -138, 87}, {-506, -145, 79}};
    // platforms (stage 3), from the left: x from, x to, top y, bottom y
    private static final int[][] PLATFORMS = {{597, 753, -135, -120}, {687, 843, -195, -180}, {777, 933, -75, -60},
            {867, 1023, -195, -180}, {957, 1113, -135, -120}};
    private static final String[] SIGNS = {"+", "-", "x", "/"};

    // stage 1
    private final Map<Integer, Integer> couponsAsked = new HashMap<>();
    private final Set<Integer> couponsDone = new HashSet<>();
    // stage 2 / 3
    private final Set<Integer> rightVines = new HashSet<>();
    private final Set<Integer> rightPlatforms = new HashSet<>();
    private final Set<Integer> puppets = new HashSet<>();
    // stage 4
    private int target;
    private int current;
    private int lastSign = -1;
    // stage 5
    private boolean slimeSpawned;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(WAITING_ROOM);
        instance.setTimeout(TIME_LIMIT_SECONDS);
        List<Integer> v = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        Collections.shuffle(v);
        rightVines.addAll(v.subList(0, 3));
        List<Integer> p = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collections.shuffle(p);
        rightPlatforms.addAll(p.subList(0, 3));
        target = Util.getRandom(15, 45);
    }

    @Override
    protected void startStage(int stageNumber) {
    }

    // ---------------------------------------------------------------------------------------------------------- entry

    public static String getEntryBlocker(Char leader) {
        var party = leader.getParty();
        if (party == null) {
            return "Please make a party of " + PARTY_SIZE + " first.";
        }
        if (party.getPartyLeaderID() != leader.getId()) {
            return "Please have your party leader talk to me.";
        }
        var members = party.getPartyMembersInField(leader);
        if (members.size() != PARTY_SIZE) {
            return "You need a party of exactly " + PARTY_SIZE + " here.";
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
        }
        return 0;
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        int s = stageOf(field.getId());
        return (s == 1 || s == 4) && !isStageCleared(s);
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        int s = stageOf(field.getId());
        if (s >= 1 && s <= 4 && portal.getName() != null && portal.getName().startsWith("next") && !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The portal isn't open yet. Finish Cloto's task first.");
            return false;
        }
        return true;
    }

    /** WUparty_portal */
    public void next(Char chr) {
        int s = stageOf(chr.getField().getId());
        if (s < 1 || s > 4 || !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The portal isn't open yet. Finish Cloto's task first.");
            return;
        }
        chr.warp(new int[]{0, STAGE_2, STAGE_3, STAGE_4, STAGE_5}[s]);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null && newField.getId() == STAGE_5) {
            synchronized (this) {
                if (!slimeSpawned) {
                    slimeSpawned = true;
                    final Field f = newField;
                    addTimer(EventManager.addEvent(() -> {
                        Position pos = f.getRandomPosOnWalkableFoothold(100, false);
                        if (pos != null) {
                            f.spawnMob(KING_SLIME, pos.getX(), pos.getY(), false, -1);
                        }
                    }, 2000));
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    /** Cloto: each stage's task */
    public synchronized String talkToCloto(Char chr) {
        int s = stageOf(chr.getField().getId());
        if (s >= 1 && s <= 4 && isStageCleared(s)) {
            return "Well done! The portal is open. On to the next stage!";
        }
        switch (s) {
            case 1:
                return stage1(chr);
            case 2:
                return checkVines(chr);
            case 3:
                return checkPlatforms(chr);
            case 4:
                return "Reach #b" + target + "#k! Your number is #b" + current + "#k."
                        + (lastSign >= 0 ? " The last sign used was #b" + SIGNS[lastSign] + "#k; use a different one "
                        + "next." : "") + "\r\n\r\nDefeat the Curse Eyes for #bNumbers#k and drop one on a sign "
                        + "(+, -, x, /) to apply it.";
            case 5:
                return "Defeat King Slime!";
        }
        return null;
    }

    private List<Char> membersHere(Field field) {
        List<Char> list = new ArrayList<>();
        for (Char c : getInstance().getChars()) {
            if (c.getField() == field) {
                list.add(c);
            }
        }
        return list;
    }

    // ---------------------------------------------------------------------------------------------------------- stage 1

    private String stage1(Char chr) {
        Field field = chr.getField();
        List<Char> members = membersHere(field);
        boolean solo = members.size() <= 1;
        if (!isLeader(chr) || solo) {
            if (couponsDone.contains(chr.getId())) {
                if (!solo) {
                    return "Thank you! Wait for your party leader to talk to me once everyone is done.";
                }
            } else {
                Integer asked = couponsAsked.get(chr.getId());
                if (asked == null) {
                    asked = Util.getRandom(3, 8);
                    couponsAsked.put(chr.getId(), asked);
                    return "Bring me exactly #b" + asked + " #t" + COUPON + "#s#k from the Ligators. Not one more, not "
                            + "one less!";
                }
                int have = chr.getInventories().stream().mapToInt(inv -> inv.getQuantity(COUPON)).sum();
                if (have != asked) {
                    return "I asked for exactly #b" + asked + "#k Coupons. You have " + have + ". (Drop any extra.)";
                }
                chr.consumeItem(COUPON, asked);
                couponsDone.add(chr.getId());
                if (!solo) {
                    return "That's exactly right! Wait for the others to finish.";
                }
            }
        }
        for (Char c : members) {
            if ((!isLeader(c) || solo) && !couponsDone.contains(c.getId())) {
                return "Not everyone has brought me their Coupons yet.";
            }
        }
        for (Mob m : new ArrayList<>(field.getMobs())) {
            m.die(false);
        }
        clearStage(1, field, STAGE_PERCENT[1]);
        return "Everyone brought exactly the right number! The portal is open.";
    }

    // ---------------------------------------------------------------------------------------------------------- stage 2

    private static int vineOf(Char c) {
        Position pos = c.getPosition();
        if (pos == null) {
            return 0;
        }
        for (int i = 0; i < VINES.length; i++) {
            if (Math.abs(pos.getX() - VINES[i][0]) <= 20 && pos.getY() >= VINES[i][1] - 10
                    && pos.getY() <= VINES[i][2] - 15) {
                return i + 1;
            }
        }
        return 0;
    }

    private String checkVines(Char chr) {
        if (!isLeader(chr)) {
            return "Three of the four vines are the right ones. Hang on different vines and have your leader talk "
                    + "to me.";
        }
        Field field = chr.getField();
        Set<Integer> used = new HashSet<>();
        for (Char c : membersHere(field)) {
            int v = vineOf(c);
            if (v > 0) {
                used.add(v);
            }
        }
        if (used.size() < Math.min(3, Math.max(1, membersHere(field).size() - 1))) {
            return "Hang on three different vines first (one person per vine).";
        }
        if (used.equals(rightVines) || (membersHere(field).size() < 4 && rightVines.containsAll(used))) {
            clearStage(2, field, STAGE_PERCENT[2]);
            return "That's the right combination! The portal is open.";
        }
        return "That's not the right combination. Try again!";
    }

    // ---------------------------------------------------------------------------------------------------------- stage 3

    private static int platformOf(Char c) {
        Position pos = c.getPosition();
        if (pos == null) {
            return 0;
        }
        for (int i = 0; i < PLATFORMS.length; i++) {
            int[] p = PLATFORMS[i];
            if (pos.getX() >= p[0] && pos.getX() <= p[1] && pos.getY() >= p[2] - 15 && pos.getY() <= p[3] + 5) {
                return i + 1;
            }
        }
        return 0;
    }

    private String checkPlatforms(Char chr) {
        if (!isLeader(chr)) {
            return "Three of the five platforms (1 to 5 cats, from the left) are right. Stand on different platforms "
                    + "and have your leader talk to me.";
        }
        Field field = chr.getField();
        Set<Integer> used = new HashSet<>(puppets);
        for (Char c : membersHere(field)) {
            int p = platformOf(c);
            if (p > 0) {
                used.add(p);
            }
        }
        if (used.size() != 3) {
            return "Exactly three platforms must be taken (by members or Platform Puppets). Right now " + used.size()
                    + " are.";
        }
        int right = 0;
        for (int p : used) {
            if (rightPlatforms.contains(p)) {
                right++;
            }
        }
        if (right == 3) {
            puppets.clear();
            clearStage(3, field, STAGE_PERCENT[3]);
            return "All three are right! The portal is open.";
        }
        return right + " of the platforms " + (right == 1 ? "is" : "are") + " right. Try again!";
    }

    /** Nella in stage 3: places (or removes) a Platform Puppet on a platform (1-5). */
    public synchronized String togglePuppet(int platform) {
        if (platform < 1 || platform > 5) {
            return null;
        }
        if (puppets.remove(platform)) {
            return "I took the Platform Puppet off platform " + platform + ".";
        }
        if (puppets.size() >= 2) {
            return "You can only have 2 Platform Puppets out at once.";
        }
        puppets.add(platform);
        return "I put a Platform Puppet on platform " + platform + ".";
    }

    public Set<Integer> getPuppets() {
        return puppets;
    }

    // ---------------------------------------------------------------------------------------------------------- stage 4

    private static int numberValue(int itemId) {
        for (int i = 0; i < NUMBERS.length; i++) {
            if (NUMBERS[i] == itemId) {
                return i + 1;
            }
        }
        return 0;
    }

    /** WUkerningCal0-3: clicking a sign shows the state */
    public String describeSign(int sign) {
        return "Sign: " + SIGNS[Math.max(0, Math.min(3, sign))] + ". Your number is " + current + "; reach " + target
                + ".";
    }

    @Override
    public synchronized void onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {
        super.onItemDropped(chr, item, quantity, position, drop);
        if (item == null || drop == null || chr.getField().getId() != STAGE_4 || isStageCleared(4)) {
            return;
        }
        int value = numberValue(item.getItemId());
        if (value == 0) {
            return;
        }
        Field field = chr.getField();
        int sign = -1;
        for (Reactor r : field.getReactors()) {
            String name = r.getName();
            if (name != null && name.startsWith("calc") && Math.abs(r.getPosition().getX() - position.getX()) <= 90
                    && Math.abs(r.getPosition().getY() - position.getY()) <= 120) {
                sign = Util.isNumber(name.substring(4)) ? Integer.parseInt(name.substring(4)) : -1;
            }
        }
        if (sign < 0 || sign > 3) {
            return;
        }
        if (sign == lastSign) {
            notice(field, "You can't use the " + SIGNS[sign] + " sign twice in a row!");
            return;
        }
        int result;
        switch (sign) {
            case 0:
                result = current + value;
                break;
            case 1:
                result = current - value;
                break;
            case 2:
                result = current * value;
                break;
            default:
                result = current % value == 0 ? current / value : -1;
                break;
        }
        if (result < 0) {
            notice(field, "That doesn't work out evenly. Try another number or sign.");
            return;
        }
        drop.setBlockFromPickup(true);
        field.removeDrop(drop.getObjectId(), 0, false, 0);
        notice(field, current + " " + SIGNS[sign] + " " + value + " = " + result + " (target: " + target + ")");
        current = result;
        lastSign = sign;
        if (current == target) {
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(4, field, STAGE_PERCENT[4]);
            notice(field, "You reached " + target + "! The portal is open.");
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
        int s = stageOf(field.getId());
        int t = mob.getTemplateId();
        if (s == 1 && t == LIGATOR && !isStageCleared(1) && Util.succeedProp(70)) {
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(COUPON)), mob.getPosition(), true); // a quest item: it must land to be picked up
        } else if (s == 4 && t == CURSE_EYE && !isStageCleared(4)) {
            int n = NUMBERS[Util.getRandom(NUMBERS.length - 1)];
            field.drop(new Drop(-1, ItemData.getItemDeepCopy(n)), mob.getPosition(), true); // a quest item: it must land to be picked up
        } else if (s == 5 && t == KING_SLIME && !finished) {
            finished = true;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(5, field, STAGE_PERCENT[5]);
            giveTraitExp(Stat.willEXP, 6);
            giveTraitExp(Stat.insightEXP, 3);
            givePartyPoints(10);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
            }
            notice(field, "King Slime is defeated! Off to the Returning Path...");
            addTimer(EventManager.addEvent(() -> getInstance().warp(RETURNING_PATH), 5000));
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
        return Arrays.asList(COUPON, NUMBERS[0], NUMBERS[1], NUMBERS[2], NUMBERS[3]);
    }

    // read-only, for test reports: what a player reads in Cloto's answers

    public int getCouponsAsked(int charId) {
        return couponsAsked.getOrDefault(charId, 0);
    }

    public int getTarget() {
        return target;
    }

    public int getCurrent() {
        return current;
    }

    public int getLastSign() {
        return lastSign;
    }

    /** The stage being played: the first one not cleared (6 once all five are). */
    public int getCurrentStage() {
        for (int s = 1; s <= 5; s++) {
            if (!isStageCleared(s)) {
                return s;
            }
        }
        return 6;
    }
}

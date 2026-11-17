package net.swordie.ms.client.character;

import java.util.ArrayList;
import java.util.List;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.FirstEnterRewardType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

/**
 * Contributed by Arixal (from GitLab).
 *
 * Burning characters, as GMS Hyper Burning MAX plus Burning Beyond. Up to {@link #MAX_BURNING_PER_ACCOUNT} at once per
 * account, first life only.
 * <ul>
 * <li>Hyper Burning MAX: each level up from 10 (Zero 100) gives 4 more levels, up to 260. Started on a new character
 * (up to {@link #NEW_CHAR_MAX_LEVEL}) or an existing one at 200-258, which burns from its current level.</li>
 * <li>Burning Beyond: each level up from 260 gives 1 more, up to 270. A Hyper Burning MAX character goes on into it;
 * another character can start it at 260-268.</li>
 * </ul>
 * Started from the client's own Burning dialog (the character select Burning button; v271 enters the game right after
 * creating a character, without the dialog), the in-game offer to a new character ({@link #askNewCharacter}, both
 * play styles), @burning / @menu, or {@link #startBurning(Char, int)}. A rebirth ends it for good; the player can end
 * it early ({@link #endBurning}), which also frees the account's slot.
 */
public class BurningModule {

    private static final Logger log = LogManager.getLogger(BurningModule.class);

    public static final int TYPE_NONE = 0;
    public static final int TYPE_HYPER_MAX = 1;
    public static final int TYPE_BEYOND = 2;
    /** for {@link #startBurning}: Burning Beyond from 260, Hyper Burning MAX below */
    public static final int TYPE_AUTO = -1;

    public static final int HYPER_MIN_LEVEL = 10;
    public static final int HYPER_ZERO_MIN_LEVEL = 100;
    public static final int HYPER_MAX_LEVEL = 260;
    public static final int HYPER_EXTRA_LEVELS = 4;
    public static final int BEYOND_MIN_LEVEL = 260;
    public static final int BEYOND_MAX_LEVEL = 270;
    public static final int BEYOND_EXTRA_LEVELS = 1;

    /** highest level that still counts as a new character (tutorials pass level 10) */
    public static final int NEW_CHAR_MAX_LEVEL = 30;
    public static final int NEW_ZERO_MAX_LEVEL = 110;
    public static final int EXISTING_MIN_LEVEL = 200;
    public static final int EXISTING_MAX_LEVEL = 258;
    public static final int BEYOND_START_MAX_LEVEL = 268;

    // the client's burning type ids (dialog answer, stat block byte)
    private static final int CLIENT_TYPE_HYPER = 5;
    private static final int CLIENT_TYPE_BEYOND = 10;

    // Etc.wz EventCharacterBurning LimitedJobHyper: Pink Bean, Yeti, and 12005/12100
    private static final Set<Integer> EXCLUDED_JOBS = Set.of(13000, 13100, 13500, 12005, 12100);

    public static final int HYPER_BURNING_MAX_MEDAL = 1143407;
    public static final int BURNING_BEYOND_TITLE = 3700807;

    private static final int POWER_ELIXIR = 2000005;
    private static final int EXP_COUPON = ItemConstants.EXP_COUPON_2X_1_HOUR;
    private static final int ETERNAL_FLAME_TITLE_COUPON = 2634979;
    private static final int ETERNAL_FLAME_RING_COUPON = 2632789;
    private static final int ABSOLAB_WEAPON_BOX = 2633912;
    private static final int ABSOLAB_ARMOR_BOX = 2633913;
    private static final int ARCANE_UMBRA_WEAPON_BOX = 2633914;
    private static final int ARCANE_UMBRA_ARMOR_BOX = 2633915;
    private static final int ARCANE_SYMBOL_SELECTOR = 2630437;
    private static final int BURNING_FLAME_WINGS_COUPON = 2639895;

    /** Burninator items (Item.wz 0253 info/burning): item, min level, max level */
    private static final int[][] BURNINATORS = {
            {2534000, 10, 150}, {2534001, 10, 150}, {2534004, 10, 150}, // hardcode-ok
            {2534002, 10, 200}, {2534005, 10, 200}, {2534006, 10, 200}, // hardcode-ok
            {2534007, 11, 260}, {2534009, 10, 260}, {2534010, 10, 260}, // hardcode-ok
    };

    /** Hyper Burning MAX gift quests (Quest.wz, display only here): quest, level */
    private static final int GIFT_QUEST_SUPPORT = 101243;
    private static final int[][] GIFT_QUESTS = {
            {101244, 200}, {101245, 210}, {101251, 215}, {101246, 220}, {101247, 225}, {101248, 230},
            {101249, 235}, {101262, 245}, {101250, 250}, {101526, 255}, {101527, 260},
    };

    // ------------------------------------------------------------------------------------------------- state

    public static boolean isExcludedJob(int job) {
        return EXCLUDED_JOBS.contains(job) || JobConstants.isPinkBean(job) || JobConstants.isGmJob(job);
    }

    /** @return whether the character burns at its level (rebirth not checked: that ends it on the next level up) */
    public static boolean isBurningNow(CharacterStat cs) {
        return cs != null && cs.getBurningType() != TYPE_NONE && !cs.isBurningEnded()
                && cs.getLevel() < BEYOND_MAX_LEVEL && !isExcludedJob(cs.getJob());
    }

    /** @return the account's burning character (active or done), or null */
    public static Char getAccountBurningChar(Account acc) {
        if (acc == null) {
            return null;
        }
        for (Char c : acc.getCharacters()) {
            if (c.getAvatarData() != null && c.getAvatarData().getCharacterStat().getBurningType() != TYPE_NONE) {
                return c;
            }
        }
        return null;
    }

    /** Burning characters an account may have at once (finished or reborn ones don't count). */
    public static final int MAX_BURNING_PER_ACCOUNT = 5;

    /** @return the account's characters that are still burning, besides the one with id exceptId */
    public static List<Char> getActiveBurningChars(Account acc, int exceptId) {
        List<Char> res = new ArrayList<>();
        if (acc == null) {
            return res;
        }
        for (Char c : acc.getCharacters()) {
            if (c.getId() == exceptId || c.getAvatarData() == null) {
                continue;
            }
            CharacterStat cs = c.getAvatarData().getCharacterStat();
            if (cs.getBurningType() != TYPE_NONE && !cs.isBurningEnded() && cs.getLevel() < BEYOND_MAX_LEVEL) {
                res.add(c);
            }
        }
        return res;
    }

    private static int hyperMinLevel(CharacterStat cs) {
        int min = JobConstants.isZero(cs.getJob()) ? HYPER_ZERO_MIN_LEVEL : HYPER_MIN_LEVEL;
        return Math.max(min, cs.getBurningStartLevel());
    }

    /**
     * @return {min level, max level, extra levels, client type} for the stat block, or null when not burning
     */
    public static int[] getDisplay(CharacterStat cs) {
        if (!isBurningNow(cs)) {
            return null;
        }
        if (cs.getBurningType() == TYPE_HYPER_MAX && cs.getLevel() < HYPER_MAX_LEVEL) {
            return new int[]{hyperMinLevel(cs), HYPER_MAX_LEVEL, HYPER_EXTRA_LEVELS, CLIENT_TYPE_HYPER};
        }
        return new int[]{Math.max(BEYOND_MIN_LEVEL, cs.getBurningStartLevel()), BEYOND_MAX_LEVEL,
                BEYOND_EXTRA_LEVELS, CLIENT_TYPE_BEYOND};
    }

    /** Ends a burning that a rebirth should have ended (characters reborn before burning was first life only). */
    private static boolean endIfReborn(Char chr, CharacterStat cs) {
        if (cs.getBurningType() != TYPE_NONE && !cs.isBurningEnded() && chr.getQuestManager() != null
                && RebirthModule.isReborn(chr)) {
            cs.setBurningEnded(true);
            return true;
        }
        return false;
    }

    /**
     * Extra levels for a level up, called once for every level gained.
     *
     * @param reached the level just reached (before the extra levels)
     */
    public static int getExtraLevels(Char chr, int reached) {
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        if (cs.getBurningType() == TYPE_NONE || cs.isBurningEnded() || isExcludedJob(chr.getJob())
                || endIfReborn(chr, cs)) {
            return 0;
        }
        int prev = reached - 1;
        int extra = 0;
        if (cs.getBurningType() == TYPE_HYPER_MAX && prev < HYPER_MAX_LEVEL) {
            if (prev >= hyperMinLevel(cs)) {
                extra = Math.min(HYPER_EXTRA_LEVELS, HYPER_MAX_LEVEL - reached);
            }
        } else if (prev >= BEYOND_MIN_LEVEL && prev >= cs.getBurningStartLevel()) {
            extra = Math.min(BEYOND_EXTRA_LEVELS, BEYOND_MAX_LEVEL - reached);
        }
        return Math.max(0, Math.min(extra, GameConstants.MAX_CHAR_LEVEL - reached));
    }

    // ------------------------------------------------------------------------------------------------- starting

    /** @return why the character can't start this burning type, or null if it can */
    public static String getBlocker(Char chr, int type) {
        return getBlocker(chr, type, false);
    }

    /** @param ignoreCap leave out the per-account limit */
    private static String getBlocker(Char chr, int type, boolean ignoreCap) {
        if (chr == null || chr.getAvatarData() == null) {
            return "Character not found.";
        }
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        if (cs.getBurningType() != TYPE_NONE) {
            if (cs.isBurningEnded()) {
                boolean reborn = chr.getQuestManager() != null && RebirthModule.isReborn(chr);
                return chr.getName() + (hasFlag(chr, FLAG_ENDED) || !reborn ? " ended its Burning early." :
                        "'s Burning ended with its rebirth.");
            }
            if (cs.getLevel() >= BEYOND_MAX_LEVEL) {
                return chr.getName() + " has finished Burning.";
            }
            return String.format("%s is a Burning character: %s", chr.getName(), describe(cs));
        }
        if (!ServerFeatures.on(ServerFeatures.Feature.BURNING)) {
            return "Burning characters are turned off on this server.";
        }
        List<Char> others = getActiveBurningChars(chr.getAccount(), chr.getId());
        if (!ignoreCap && others.size() >= MAX_BURNING_PER_ACCOUNT) {
            return String.format("Your account already has %d Burning characters (%s); that's the most at once.",
                    others.size(), others.stream().map(Char::getName).collect(java.util.stream.Collectors.joining(", ")));
        }
        if (chr.getQuestManager() != null && RebirthModule.isReborn(chr)) {
            return "Only a character that hasn't been reborn can burn.";
        }
        if (isExcludedJob(chr.getJob())) {
            return "This class can't burn.";
        }
        int level = chr.getLevel();
        if (resolveType(chr, type) == TYPE_BEYOND) {
            if (level < BEYOND_MIN_LEVEL || level > BEYOND_START_MAX_LEVEL) {
                return String.format("Burning Beyond starts at level %d-%d.", BEYOND_MIN_LEVEL, BEYOND_START_MAX_LEVEL);
            }
            return null;
        }
        int newMax = JobConstants.isZero(chr.getJob()) ? NEW_ZERO_MAX_LEVEL : NEW_CHAR_MAX_LEVEL;
        if (level > newMax && (level < EXISTING_MIN_LEVEL || level > EXISTING_MAX_LEVEL)) {
            return String.format("Hyper Burning MAX is for a new character (up to level %d) or one at level %d-%d.",
                    newMax, EXISTING_MIN_LEVEL, EXISTING_MAX_LEVEL);
        }
        return null;
    }

    private static int resolveType(Char chr, int type) {
        if (type == TYPE_AUTO) {
            return chr.getLevel() >= BEYOND_MIN_LEVEL ? TYPE_BEYOND : TYPE_HYPER_MAX;
        }
        return type == TYPE_BEYOND ? TYPE_BEYOND : TYPE_HYPER_MAX;
    }

    /**
     * Makes the character a Burning character. Works online, at character select and for bots.
     *
     * @param type {@link #TYPE_HYPER_MAX}, {@link #TYPE_BEYOND} or {@link #TYPE_AUTO}
     * @return null if it now burns, otherwise why not
     */
    public static synchronized String startBurning(Char chr, int type) {
        String blocker = getBlocker(chr, type);
        if (blocker != null) {
            return blocker;
        }
        type = resolveType(chr, type);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        int level = chr.getLevel();
        int start;
        if (type == TYPE_BEYOND) {
            start = level;
        } else if (level >= EXISTING_MIN_LEVEL) {
            start = level;
        } else {
            start = JobConstants.isZero(chr.getJob()) ? HYPER_ZERO_MIN_LEVEL : HYPER_MIN_LEVEL;
        }
        cs.setBurningType(type);
        cs.setBurningStartLevel(start);
        cs.setBurningEnded(false);
        if (type == TYPE_HYPER_MAX) {
            completeGiftQuest(chr, GIFT_QUEST_SUPPORT);
        }
        log.info(String.format("Burning: %s (%d) started %s at level %d.", chr.getName(), chr.getId(),
                type == TYPE_BEYOND ? "Burning Beyond" : "Hyper Burning MAX", level));
        if (isOnline(chr)) {
            chr.chatMessage(ChatType.Notice2, String.format("%s is now a Burning character: %s", chr.getName(),
                    describe(cs)));
            chr.chatMessage(ChatType.Notice2, "The flame shows after you change channel or log in again.");
        }
        return null;
    }

    public static boolean isBurninator(int itemId) {
        for (int[] row : BURNINATORS) {
            if (row[0] == itemId) {
                return true;
            }
        }
        return false;
    }

    /**
     * A Burninator item: starts the character's burning if its level is in the item's range.
     * Every Burninator starts Hyper Burning MAX (the only burning here); the +2 ones keep their level range.
     *
     * @return null if burning started (the caller consumes the item), otherwise why not
     */
    public static String useBurninator(Char chr, int itemId) {
        for (int[] row : BURNINATORS) {
            if (row[0] != itemId) {
                continue;
            }
            int level = chr.getLevel();
            if (level < row[1] || level >= row[2]) {
                return String.format("This Burninator works for levels %d-%d.", row[1], row[2] - 1);
            }
            return startBurning(chr, TYPE_HYPER_MAX);
        }
        return "This isn't a Burninator.";
    }

    /** What the character's burning does now, for messages. */
    public static String describe(CharacterStat cs) {
        if (cs.getBurningType() == TYPE_HYPER_MAX && cs.getLevel() < HYPER_MAX_LEVEL) {
            return String.format("Hyper Burning MAX, each level up from level %d gives %d more levels until level %d, "
                    + "then Burning Beyond (+%d) until %d.", hyperMinLevel(cs), HYPER_EXTRA_LEVELS, HYPER_MAX_LEVEL,
                    BEYOND_EXTRA_LEVELS, BEYOND_MAX_LEVEL);
        }
        return String.format("Burning Beyond, each level up gives %d more level until level %d.", BEYOND_EXTRA_LEVELS,
                BEYOND_MAX_LEVEL);
    }

    /** The rules, for the question before it's started. */
    public static String getRules() {
        return String.format("#bHyper Burning MAX#k: each level up from level %d (Zero %d) gives %d more levels until "
                        + "level %d, then #bBurning Beyond#k gives %d more per level up until %d. For a new character "
                        + "(up to level %d) or one at level %d-%d; at %d-%d Burning Beyond alone. "
                        + "Up to %d characters at once per account, first life only (a rebirth ends it), and it can't be moved.",
                HYPER_MIN_LEVEL, HYPER_ZERO_MIN_LEVEL, HYPER_EXTRA_LEVELS, HYPER_MAX_LEVEL, BEYOND_EXTRA_LEVELS,
                BEYOND_MAX_LEVEL, NEW_CHAR_MAX_LEVEL, EXISTING_MIN_LEVEL, EXISTING_MAX_LEVEL, BEYOND_MIN_LEVEL,
                BEYOND_START_MAX_LEVEL, MAX_BURNING_PER_ACCOUNT);
    }

    // ------------------------------------------------------------------------------------------------- offer, end

    /** Server-only record: flags separated by ';' ("offered": the new character was asked, "ended": ended early). */
    private static final int BURNING_QR = 89999963;
    private static final String FLAG_OFFERED = "offered";
    private static final String FLAG_ENDED = "ended";
    public static final String OFFER_SCRIPT = "burningOffer";

    private static boolean hasFlag(Char chr, String flag) {
        if (chr.getQuestManager() == null) {
            return false;
        }
        String rec = chr.getQuestManager().getHiddenRecord(BURNING_QR);
        return rec != null && java.util.Arrays.asList(rec.split(";")).contains(flag);
    }

    private static void addFlag(Char chr, String flag) {
        if (chr.getQuestManager() == null || hasFlag(chr, flag)) {
            return;
        }
        String rec = chr.getQuestManager().getHiddenRecord(BURNING_QR);
        chr.getQuestManager().setHiddenRecord(BURNING_QR, rec == null || rec.isEmpty() ? flag : rec + ";" + flag);
    }

    /** @return whether a new character should still be asked about Hyper Burning MAX (once, setting burning) */
    public static boolean needsOffer(Char chr) {
        if (chr == null || chr.getAvatarData() == null || chr.getQuestManager() == null
                || !ServerFeatures.on(ServerFeatures.Feature.BURNING) || hasFlag(chr, FLAG_OFFERED)) {
            return false;
        }
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        int newMax = JobConstants.isZero(chr.getJob()) ? NEW_ZERO_MAX_LEVEL : NEW_CHAR_MAX_LEVEL;
        return cs.getBurningType() == TYPE_NONE && chr.getLevel() <= newMax
                && getBlocker(chr, TYPE_HYPER_MAX) == null;
    }

    /** The in-game question for a new character (script burningOffer), for both play styles. Asked once. */
    public static void askNewCharacter(net.swordie.ms.scripts.ScriptManagerImpl sm, Char chr) {
        if (!needsOffer(chr)) {
            return;
        }
        addFlag(chr, FLAG_OFFERED);
        sm.setSpeakerID(PlayStyleModule.SPEAKER);
        boolean classic = PlayStyleModule.isClassic(chr);
        String intro = "#e#bHyper Burning MAX#k#n\r\n" + getRules() + "\r\n\r\n"
                + (classic ? "You keep every quest: race ahead and come back to the story whenever you like. "
                : "Your Express skips work the same while burning. ")
                + "You can end Burning early in @menu > My character.\r\n\r\n";
        if (!sm.sendAskYesNo(intro + "Make #b" + chr.getName() + "#k a Burning character?")) {
            sm.sendSayOkay(declined(chr));
            return;
        }
        String blocker = startBurning(chr, TYPE_HYPER_MAX);
        sm.sendSayOkay(blocker == null ? chr.getName() + " is now a Burning character. "
                + describe(chr.getAvatarData().getCharacterStat()) : blocker);
    }

    private static String declined(Char chr) {
        int newMax = JobConstants.isZero(chr.getJob()) ? NEW_ZERO_MAX_LEVEL : NEW_CHAR_MAX_LEVEL;
        return chr.getLevel() < newMax ? "No Burning for now. Until level " + newMax
                + " you can still start it from @menu > My character." : "No Burning for now.";
    }

    /** @return why this character can't end its Burning, or null if it can */
    public static String getEndBlocker(Char chr) {
        if (chr == null || chr.getAvatarData() == null) {
            return "Character not found.";
        }
        return isBurningNow(chr.getAvatarData().getCharacterStat()) ? null : chr.getName() + " isn't burning.";
    }

    /**
     * Ends the character's Burning for good (GMS lets players end Hyper Burning): no more extra levels, and the
     * account's slot is free again. Levels already gained stay.
     *
     * @return null when ended, otherwise why not
     */
    public static synchronized String endBurning(Char chr) {
        String blocker = getEndBlocker(chr);
        if (blocker != null) {
            return blocker;
        }
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        cs.setBurningEnded(true);
        addFlag(chr, FLAG_ENDED);
        log.info(String.format("Burning: %s (%d) ended it early at level %d.", chr.getName(), chr.getId(),
                chr.getLevel()));
        if (isOnline(chr)) {
            chr.chatMessage(ChatType.Notice2, chr.getName() + "'s Burning has ended. The flame goes after you change "
                    + "channel or log in again.");
        }
        return null;
    }

    // ------------------------------------------------------------------------------------------------- hooks

    /** After a level up (Char#addExp): client gift quests marked, the hand-over to Burning Beyond. */
    public static void onLevelUp(Char chr, int oldLevel, int newLevel) {
        try {
            CharacterStat cs = chr.getAvatarData().getCharacterStat();
            if (cs.getBurningType() == TYPE_NONE || cs.isBurningEnded() || newLevel <= oldLevel) {
                return;
            }
            if (cs.getBurningType() == TYPE_HYPER_MAX) {
                for (int[] q : GIFT_QUESTS) {
                    if (q[1] > oldLevel && q[1] <= newLevel) {
                        completeGiftQuest(chr, q[0]);
                    }
                }
            }
            if (!isOnline(chr)) {
                return;
            }
            if (cs.getBurningType() == TYPE_HYPER_MAX && oldLevel < HYPER_MAX_LEVEL && newLevel >= HYPER_MAX_LEVEL) {
                chr.chatMessage(ChatType.Notice2, String.format("Hyper Burning MAX complete! Burning Beyond goes on: "
                        + "each level up gives %d more level until level %d.", BEYOND_EXTRA_LEVELS, BEYOND_MAX_LEVEL));
            }
            if (oldLevel < BEYOND_MAX_LEVEL && newLevel >= BEYOND_MAX_LEVEL) {
                chr.chatMessage(ChatType.Notice2, "Burning complete!");
            }
        } catch (Exception e) {
            log.error("Burning: level up of " + chr.getName(), e);
        }
    }

    /**
     * Marks a client gift quest done when its level is reached. The quests have no rewards in Quest.wz; the gifts
     * come through the gift box (giveRewards), so nothing is given here.
     */
    private static void completeGiftQuest(Char chr, int questId) {
        try {
            QuestManager qm = chr.getQuestManager();
            if (qm == null || qm.hasQuestCompleted(questId)) {
                return;
            }
            Quest quest = qm.getQuestById(questId);
            if (quest == null) {
                quest = QuestData.createQuestFromId(questId);
            }
            quest.setCompletedTime(FileTime.currentTime());
            quest.setStatus(QuestStatus.Completed);
            qm.addQuest(quest);
            if (isOnline(chr)) {
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
            }
        } catch (Exception e) {
            log.error("Burning: gift quest " + questId + " of " + chr.getName(), e);
        }
    }

    /** At a rebirth: burning ends for good. */
    public static void onRebirth(Char chr) {
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        if (cs.getBurningType() != TYPE_NONE && !cs.isBurningEnded()) {
            cs.setBurningEnded(true);
            chr.chatMessage(ChatType.Notice2, "Your Burning ended with the rebirth (Burning is for the first life only).");
        }
    }

    // ------------------------------------------------------------------------------------------------- login

    /**
     * The client offers Burning (the dialog after creating a character, the character select Burning button) only
     * while its clock is inside an Etc.wz EventCharacterBurning window, and the v265 windows have all ended
     * (EventHyper and EventHyper2: 2025-11-12 to 2026-02-03). The login screen takes its clock from SELECT_WORLD_RESULT
     * and the game resets it on SET_FIELD, so this date only lives on the login screen.
     */
    private static final LocalDateTime OFFER_CLOCK = LocalDateTime.of(2026, 1, 15, 12, 0);

    /** @return the clock for SELECT_WORLD_RESULT: inside the Hyper Burning window while the account can pick one */
    public static FileTime getLoginClock(Account acc) {
        try {
            if (acc != null && ServerFeatures.on(ServerFeatures.Feature.BURNING)
                    && getActiveBurningChars(acc, 0).size() < MAX_BURNING_PER_ACCOUNT) {
                return FileTime.fromDate(OFFER_CLOCK);
            }
        } catch (Exception e) {
            log.error("Burning: login clock", e);
        }
        return FileTime.currentTime();
    }

    /**
     * In-op 184 (v265 183; was read as a world status request): the answer of the client's "make this character Burning?"
     * dialog, after creating a character or from the character select Burning button.
     * byte 0, int (unused), int charId, byte burning type (the dialog's pick: 1 Mega, 3 Tera, 5 Hyper, 6 Hyper for
     * 200+, 7/8 Remaster, 10 Beyond; all but 10 start Hyper Burning MAX here), int job.
     * Answered with CHARACTER_BURNING 461 (v265 447): byte 1, int charId, the stat extra block (the character list entry gets
     * the flame), or byte 0 after a pop-up with the reason.
     */
    public static void handleLoginRequest(Client c, InPacket inPacket) {
        inPacket.decodeByte();
        inPacket.decodeInt();
        int charId = inPacket.decodeInt();
        int clientType = inPacket.decodeByte();
        inPacket.decodeInt(); // job
        Account acc = c.getAccount();
        Char chr = acc == null ? null : acc.getCharById(charId);
        String blocker = chr == null ? "Character not found." :
                startBurning(chr, clientType == CLIENT_TYPE_BEYOND ? TYPE_BEYOND : TYPE_HYPER_MAX);
        if (blocker != null) {
            log.info(String.format("Burning: request for character %d refused: %s", charId, blocker));
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(blocker)));
            c.write(characterBurning(0, charId, null));
            return;
        }
        try {
            ((CharDao) SworDaoFactory.getByClass(Char.class)).saveOrUpdate(chr);
        } catch (Exception e) {
            log.error("Burning: could not save " + chr.getName(), e);
        }
        c.write(characterBurning(1, charId, chr.getAvatarData().getCharacterStat()));
    }

    /** CHARACTER_BURNING 447 (login): byte result, then for 1 the character id and its stat extra block. */
    public static OutPacket characterBurning(int result, int charId, CharacterStat cs) {
        OutPacket outPacket = new OutPacket(OutHeader.CHARACTER_BURNING);
        outPacket.encodeByte(result);
        if (result == 1) {
            outPacket.encodeInt(charId);
            cs.encodeStatExtra(outPacket);
        }
        return outPacket;
    }

    private static boolean isOnline(Char chr) {
        return chr.isOnline() && chr.getClient() != null && chr.getField() != null;
    }
}

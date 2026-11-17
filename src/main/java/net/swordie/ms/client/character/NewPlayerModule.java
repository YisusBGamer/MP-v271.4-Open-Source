package net.swordie.ms.client.character;

import net.swordie.ms.client.character.info.ExpIncreaseInfo;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Swordie Plus help for new players, shown in @menu:
 * <ul>
 * <li><b>What should I do next?</b> ({@link #getNextSteps}): the next useful thing for this character - gifts, the
 * onboarding checklist, ability points, a job advancement, where to train, empty equipment slots,
 * a boss it can beat, Arcane River and Grandis (symbols, dailies), HEXA/V Matrix/Hyper Stats, link skills - each
 * with a button to it;</li>
 * <li><b>Onboarding checklist</b>: open the menu, find a hunting ground, open storage, visit the Free Market, check the
 * gear helpers;</li>
 * <li><b>Sprouts</b>: characters below level 100 whose account has no level 200 character. Party members who hunt
 * in the same map as a Sprout get bonus EXP (shown as party bonus EXP), and so does the Sprout;</li>
 * <li><b>Rested EXP</b>: time spent logged out builds a pool of bonus EXP (doubles hunting EXP until it's used),
 * up to one level's worth after a day away;</li>
 * <li><b>Link skills and Legion</b> ({@link #getLinkHelperText}): every class's link skill and Legion effect, and which
 * ones this account already has.</li>
 * </ul>
 * Per-character progress is a server-only record ({@link #NEW_PLAYER_QR}, {@link #RESTED_QR}).
 * <p>
 * Each helper has its own setting (ServerFeatures, category New players). Switched off, a helper only stops giving:
 * saved progress (rested EXP pool) is kept for when it's switched on again.
 */
public final class NewPlayerModule {

    private static final Logger log = LogManager.getLogger(NewPlayerModule.class);

    /** Server-only record: checklist steps done and milestones announced ("step:menu,lv200,..."). */
    public static final int NEW_PLAYER_QR = 89999983;
    /** Server-only record: rested EXP pool and the last logout ("pool=123;out=1700000000000"). */
    public static final int RESTED_QR = 89999984;

    public static final int SPROUT_MAX_LEVEL = 99;
    /** An account with a character at this level or higher is not new. */
    public static final int VETERAN_LEVEL = 200;
    /** Bonus EXP % for each Sprout hunting in the same map (party members), and for the Sprout itself. */
    public static final int SPROUT_BONUS_PER = 10;
    public static final int SPROUT_BONUS_MAX = 30;

    /** Rested EXP: a full level's worth after this many hours logged out; nothing under {@link #RESTED_MIN_MINUTES}. */
    public static final int RESTED_FULL_HOURS = 24;
    public static final int RESTED_MIN_MINUTES = 60;

    private NewPlayerModule() {
    }

    // =================================================================================================================
    // Per-character progress record
    // =================================================================================================================

    private static Set<String> getFlags(Char chr) {
        Set<String> flags = new LinkedHashSet<>();
        if (chr == null || chr.getQuestManager() == null) {
            return flags;
        }
        String rec = chr.getQuestManager().getHiddenRecord(NEW_PLAYER_QR);
        if (rec != null && !rec.isEmpty()) {
            flags.addAll(Arrays.asList(rec.split(",")));
        }
        return flags;
    }

    private static boolean hasFlag(Char chr, String flag) {
        return getFlags(chr).contains(flag);
    }

    private static boolean addFlag(Char chr, String flag) {
        Set<String> flags = getFlags(chr);
        if (!flags.add(flag)) {
            return false;
        }
        chr.getQuestManager().setHiddenRecord(NEW_PLAYER_QR, String.join(",", flags));
        return true;
    }

    private static boolean isOnline(Char chr) {
        return chr != null && chr.isOnline() && chr.getClient() != null && chr.getField() != null
                && chr.getQuestManager() != null;
    }

    // =================================================================================================================
    // Hooks
    // =================================================================================================================

    /** Called on login (Char#login): rested EXP for the time spent logged out. */
    public static void onLogin(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return;
        }
        try {
            if (ServerFeatures.on(ServerFeatures.Feature.RESTED_EXP)) {
                accrueRested(chr);
            }
        } catch (Exception e) {
            log.error("Rested EXP: could not update " + chr.getName(), e);
        }
        sproutCache.remove(accountKey(chr));
        EventManager.addEvent(() -> {
            try {
                if (!isOnline(chr)) {
                    return;
                }
                long pool = ServerFeatures.on(ServerFeatures.Feature.RESTED_EXP) ? getRestedPool(chr) : 0;
                if (pool > 0) {
                    chr.chatMessage(ChatType.Notice2, String.format("Welcome back! You rested while you were away: "
                            + "your hunting EXP is doubled until you've earned %s bonus EXP (%s of a level).",
                            Util.formatNumber(String.valueOf(pool)), restedPercentText(chr, pool)));
                }
            } catch (Exception e) {
                log.error("Rested EXP: could not check " + chr.getName(), e);
            }
        }, 7000);
    }

    /** Called on logout (Char#logout): remembers when, for rested EXP. */
    public static void onLogout(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return;
        }
        Map<String, String> rec = readRested(chr);
        rec.put("out", String.valueOf(System.currentTimeMillis()));
        writeRested(chr, rec);
    }

    /** Called after a level up (Char#addExp): sprout cache and level milestones. */
    public static void onLevelUp(Char chr, int oldLevel, int newLevel) {
        if (chr == null || chr.getQuestManager() == null || newLevel <= oldLevel) {
            return;
        }
        if (oldLevel <= SPROUT_MAX_LEVEL && newLevel > SPROUT_MAX_LEVEL) {
            sproutCache.remove(accountKey(chr));
        }
        announceMilestones(chr, oldLevel, newLevel);
    }

    /** The levels the whole world hears about, once per character (also after a rebirth). */
    public static final int[] MILESTONE_LEVELS = {200, 250, 275, 300};

    private static void announceMilestones(Char chr, int oldLevel, int newLevel) {
        try {
            if (chr.isHidden() || chr.getClient() == null || chr.getClient().getWorld() == null) {
                return;
            }
            for (int level : MILESTONE_LEVELS) {
                if (oldLevel < level && newLevel >= level && addFlag(chr, "lv" + level)) {
                    net.swordie.ms.world.WorldNews.publish(chr.getClient().getWorld().getWorldId().getVal(),
                            net.swordie.ms.world.WorldNews.Type.LEVEL_MILESTONE,
                            String.format("[Milestone] %s reached level %d!", chr.getName(), level),
                            List.of(chr.getId()), List.of(chr.getName()), Map.of("level", String.valueOf(level)));
                }
            }
        } catch (Exception e) {
            log.error("Could not announce the level milestone of " + chr.getName(), e);
        }
    }

    /** Called when a character changes maps (Events#onWarp): the checklist's Free Market step. */
    public static void onWarp(Char chr, Field newField) {
        if (chr != null && newField != null && chr.getQuestManager() != null
                && (newField.getId() == MenuModule.FREE_MARKET || GameConstants.isFreeMarketField(newField.getId()))) {
            markStep(chr, "fm");
        }
    }

    private static void say(Char chr, String text) {
        if (isOnline(chr)) {
            chr.chatMessage(ChatType.Notice2, text);
        }
    }

    // =================================================================================================================
    // 3. Onboarding checklist
    // =================================================================================================================

    /** {key, what to do, where} */
    public static final String[][] STEPS = {
            {"menu", "Open the menu", "@menu or the Maple Administrator"},
            {"hunt", "Find a place to train", "@hunt, or @menu > Travel > Hunting Grounds"},
            {"storage", "Open your storage", "@storage, or @menu > Storage & inventory"},
            {"fm", "Visit the Free Market", "@menu > Travel > Free Market"},
            {"gear", "Check your gear", "@menu > Gear > Upgrades / Gear guide"},
    };

    public static int getStepCount() {
        return STEPS.length;
    }

    public static boolean isStepDone(Char chr, int i) {
        return hasFlag(chr, "step:" + STEPS[i][0]);
    }

    public static int getStepsDone(Char chr) {
        int n = 0;
        for (int i = 0; i < STEPS.length; i++) {
            if (isStepDone(chr, i)) {
                n++;
            }
        }
        return n;
    }

    /** The checklist is for characters below this level. */
    public static final int CHECKLIST_MAX_LEVEL = 69;

    /** Marks a checklist step done (from the menu, or on entering the Free Market). */
    public static void markStep(Char chr, String key) {
        if (chr == null || chr.getQuestManager() == null || chr.getLevel() > CHECKLIST_MAX_LEVEL) {
            return;
        }
        boolean known = Arrays.stream(STEPS).anyMatch(s -> s[0].equals(key));
        if (!known || !addFlag(chr, "step:" + key)) {
            return;
        }
        int done = getStepsDone(chr);
        if (done >= STEPS.length) {
            say(chr, "[Checklist] All done!");
        } else {
            say(chr, String.format("[Checklist] %d/%d done. Next: %s (%s).", done, STEPS.length, nextStepName(chr),
                    nextStepWhere(chr)));
        }
    }

    private static int nextStep(Char chr) {
        for (int i = 0; i < STEPS.length; i++) {
            if (!isStepDone(chr, i)) {
                return i;
            }
        }
        return -1;
    }

    public static String nextStepName(Char chr) {
        int i = nextStep(chr);
        return i < 0 ? "" : STEPS[i][1];
    }

    public static String nextStepWhere(Char chr) {
        int i = nextStep(chr);
        return i < 0 ? "" : STEPS[i][2];
    }

    /** @return the checklist as NPC chat text */
    public static String getChecklistText(Char chr) {
        StringBuilder sb = new StringBuilder("#e#bOnboarding checklist#k#n  #d@checklist#k\r\n\r\n");
        for (int i = 0; i < STEPS.length; i++) {
            boolean done = isStepDone(chr, i);
            if (done) {
                sb.append("#g[done] ").append(STEPS[i][1]).append("#k");
            } else {
                sb.append("#b").append(STEPS[i][1]).append("#k\r\n    #d").append(STEPS[i][2].replace("@menu > ", ""))
                        .append("#k");
            }
            sb.append("\r\n");
        }
        if (chr.getLevel() > CHECKLIST_MAX_LEVEL) {
            sb.append("\r\n#dFor characters below Lv. ").append(CHECKLIST_MAX_LEVEL + 1).append(".#k");
        }
        return sb.toString();
    }

    // =================================================================================================================
    // 6. Sprouts (new players) and their party EXP bonus
    // =================================================================================================================

    private static final Map<Integer, Boolean> sproutCache = new ConcurrentHashMap<>();

    private static int accountKey(Char chr) {
        return chr.getAccount() == null ? -chr.getId() : chr.getAccount().getId();
    }

    /** @return whether the account has no character at {@link #VETERAN_LEVEL} or higher (cached per account) */
    private static boolean isNewAccount(Char chr) {
        if (chr.getAccount() == null) {
            return false;
        }
        return sproutCache.computeIfAbsent(accountKey(chr), k -> {
            for (Char c : chr.getAccount().getCharacters()) {
                if (c != null && c.getLevel() >= VETERAN_LEVEL) {
                    return false;
                }
            }
            return true;
        });
    }

    /** A Sprout: below level 100, on an account with no level 200 character. */
    public static boolean isSprout(Char chr) {
        return chr != null && chr.getLevel() <= SPROUT_MAX_LEVEL && isNewAccount(chr);
    }

    /** @return the Sprout party bonus (%) for a kill: see the class description */
    public static int getSproutBonusPercent(Char chr) {
        Party party = chr.getParty();
        if (party == null) {
            return 0;
        }
        Set<Char> near = party.getPartyMembersInSameField(chr);
        if (near.isEmpty()) {
            return 0;
        }
        int sprouts = 0;
        boolean helper = false;
        for (Char other : near) {
            if (isSprout(other)) {
                sprouts++;
            } else {
                helper = true;
            }
        }
        int bonus = sprouts * SPROUT_BONUS_PER;
        if (isSprout(chr) && helper) {
            bonus += SPROUT_BONUS_PER;
        }
        return Math.min(bonus, SPROUT_BONUS_MAX);
    }

    // =================================================================================================================
    // 9. Rested EXP
    // =================================================================================================================

    private static Map<String, String> readRested(Char chr) {
        Map<String, String> map = new HashMap<>();
        String rec = chr.getQuestManager().getHiddenRecord(RESTED_QR);
        if (rec != null) {
            for (String part : rec.split(";")) {
                int eq = part.indexOf('=');
                if (eq > 0) {
                    map.put(part.substring(0, eq), part.substring(eq + 1));
                }
            }
        }
        return map;
    }

    private static void writeRested(Char chr, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (var e : map.entrySet()) {
            sb.append(e.getKey()).append('=').append(e.getValue()).append(';');
        }
        chr.getQuestManager().setHiddenRecord(RESTED_QR, sb.toString());
    }

    private static long parseLong(String s) {
        try {
            return s == null ? 0 : Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** @return the EXP for the character's current level (0 at the highest level) */
    private static long levelExp(Char chr) {
        int level = chr.getLevel();
        return level >= GameConstants.MAX_CHAR_LEVEL || level >= GameConstants.charExp.length ? 0
                : GameConstants.charExp[level];
    }

    private static void accrueRested(Char chr) {
        Map<String, String> rec = readRested(chr);
        long out = parseLong(rec.remove("out"));
        long pool = parseLong(rec.get("pool"));
        long cap = levelExp(chr);
        if (out > 0 && cap > 0) {
            long minutes = (System.currentTimeMillis() - out) / 60_000;
            if (minutes >= RESTED_MIN_MINUTES) {
                long gained = (long) (cap * Math.min(1D, minutes / (RESTED_FULL_HOURS * 60D)));
                pool = Math.min(cap, pool + gained);
            }
        }
        rec.put("pool", String.valueOf(Math.max(0, pool)));
        writeRested(chr, rec);
    }

    public static long getRestedPool(Char chr) {
        return chr == null || chr.getQuestManager() == null ? 0 : parseLong(readRested(chr).get("pool"));
    }

    /** @return e.g. "35%" (the pool as a part of the current level's EXP) */
    public static String restedPercentText(Char chr, long pool) {
        long cap = levelExp(chr);
        return cap <= 0 ? "0%" : Math.min(100, Math.round(pool * 100D / cap)) + "%";
    }

    private static long useRested(Char chr, long exp) {
        Map<String, String> rec = readRested(chr);
        long pool = parseLong(rec.get("pool"));
        if (pool <= 0 || exp <= 0) {
            return 0;
        }
        long used = Math.min(pool, exp);
        rec.put("pool", String.valueOf(pool - used));
        writeRested(chr, rec);
        if (pool - used <= 0) {
            say(chr, "Your rested EXP is used up. Log out for a while to rest again.");
        }
        return used;
    }

    // =================================================================================================================
    // Kill EXP bonuses (Mob#distributeExp, PartyDamageInfo#distributeExp)
    // =================================================================================================================

    /**
     * @return the bonus EXP for a monster kill worth {@code exp} to the character (Sprout party bonus and rested
     * EXP), also put in the EXP message
     */
    public static long getKillBonus(Char chr, long exp, ExpIncreaseInfo eei) {
        if (chr == null || exp <= 0 || chr.getQuestManager() == null) {
            return 0;
        }
        long bonus = 0;
        try {
            int sprout = ServerFeatures.on(ServerFeatures.Feature.SPROUT_BONUS) ? getSproutBonusPercent(chr) : 0;
            if (sprout > 0) {
                long add = exp * sprout / 100;
                bonus += add;
                if (eei != null) {
                    eei.setPartyBonusExp(Util.maxInt((long) eei.getPartyBonusExp() + add));
                }
            }
            long rested = ServerFeatures.on(ServerFeatures.Feature.RESTED_EXP) ? useRested(chr, exp) : 0;
            if (rested > 0) {
                bonus += rested;
                if (eei != null) {
                    eei.setRelaxBonusExp(Util.maxInt((long) eei.getRelaxBonusExp() + rested));
                }
            }
        } catch (Exception e) {
            log.error("Kill EXP bonus failed for " + chr.getName(), e);
        }
        return bonus;
    }

    // =================================================================================================================
    // 10. Link skills and Legion
    // =================================================================================================================

    private static boolean sameClass(int job, int classJob) {
        Integer a = EtcData.getUnionMemberSkillIDByJob(job);
        Integer b = EtcData.getUnionMemberSkillIDByJob(classJob);
        if (a != null && b != null && a.intValue() == b.intValue()) {
            return true;
        }
        int la = SkillConstants.getLinkSkillByJob((short) job);
        return la != 0 && la == SkillConstants.getLinkSkillByJob((short) classJob);
    }

    private static final int LINK_CLASSES_PER_PAGE = 12;

    /** One class line of the link skill page: "#bName#k - your character / Link: ... Legion: ..." */
    private static List<String[]> linkLines(Char chr) {
        List<Char> mine = new ArrayList<>();
        if (chr.getAccount() != null) {
            for (Char c : chr.getAccount().getCharacters()) {
                if (c != null) {
                    mine.add(c);
                }
            }
        }
        List<String[]> lines = new ArrayList<>(); // {"have"/"missing", text}
        for (Tuple<String, Integer> t : JobConstants.CLASS_RANKING_MAP) {
            int classJob = t.getRight();
            Char best = null;
            for (Char c : mine) {
                if (!JobConstants.isBeginnerJob(c.getJob()) && sameClass(c.getJob(), classJob)
                        && (best == null || c.getLevel() > best.getLevel())) {
                    best = c;
                }
            }
            int link = SkillConstants.getLinkSkillByJob((short) classJob);
            Integer legion = EtcData.getUnionMemberSkillIDByJob(classJob);
            StringBuilder line = new StringBuilder("#b").append(t.getLeft()).append("#k");
            if (best != null) {
                int linkLv = SkillConstants.getLinkSkillLevelByCharLevel(best.getJob(), best.getLevel());
                line.append(" - ").append(best.getName()).append(" Lv. ").append(best.getLevel());
                line.append(linkLv > 0 ? " #g(link Lv. " + linkLv + ")#k" : " #r(link at Lv. "
                        + (JobConstants.isZero(classJob) ? SkillConstants.ZERO_LINK_SKILL_1_LEVEL
                        : SkillConstants.LINK_SKILL_1_LEVEL) + ")#k");
            }
            line.append("\r\n    ");
            if (link != 0) {
                line.append("Link: #q").append(link).append("#");
            }
            if (legion != null && legion != 0) {
                line.append(link != 0 ? "   " : "").append("Legion: #q").append(legion).append("#");
            }
            lines.add(new String[]{best != null ? "have" : "missing", line.toString()});
        }
        return lines;
    }

    /** @return how many pages the link skill helper has (page 0: the explanation and your classes) */
    public static int getLinkHelperPageCount(Char chr) {
        long missing = linkLines(chr).stream().filter(l -> l[0].equals("missing")).count();
        return 1 + (int) ((missing + LINK_CLASSES_PER_PAGE - 1) / LINK_CLASSES_PER_PAGE);
    }

    /** @return a page of the link skill and Legion helper as NPC chat text */
    public static String getLinkHelperText(Char chr, int page) {
        List<String[]> lines = linkLines(chr);
        List<String> have = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        for (String[] l : lines) {
            (l[0].equals("have") ? have : missing).add(l[1]);
        }
        StringBuilder sb = new StringBuilder("#e#bLink skills & Legion#k#n");
        if (page <= 0) {
            int legionLevel = chr.getUnion() == null ? 0 : chr.getUnion().getUnionLevel();
            sb.append("\r\n#dEach class gives a link skill (Lv. ").append(SkillConstants.LINK_SKILL_1_LEVEL).append("/")
                    .append(SkillConstants.LINK_SKILL_2_LEVEL).append("/").append(SkillConstants.LINK_SKILL_3_LEVEL)
                    .append(") and a Legion effect (Lv. 60+).#k\r\nLegion level: #b").append(legionLevel)
                    .append("#k").append(legionLevel < LevelUnlockModule.LEGION_LEVEL ? " #d(opens at "
                            + LevelUnlockModule.LEGION_LEVEL + ")#k" : "").append("\r\n\r\n");
            sb.append("#eYour classes (").append(have.size()).append(")#n\r\n");
            sb.append(have.isEmpty() ? "None yet.\r\n" : String.join("\r\n", have) + "\r\n");
        } else {
            int from = (page - 1) * LINK_CLASSES_PER_PAGE;
            int to = Math.min(missing.size(), from + LINK_CLASSES_PER_PAGE);
            sb.append(": classes you don't have (").append(missing.size()).append(")\r\n\r\n");
            if (from < to) {
                sb.append(String.join("\r\n", missing.subList(from, to))).append("\r\n");
            }
        }
        return sb.toString();
    }

    // =================================================================================================================
    // 1. What should I do next?
    // =================================================================================================================

    /**
     * @return up to 8 suggestions as {text, action}; the menu turns each action into a button ("" = text only)
     */
    public static List<String[]> getNextSteps(Char chr) {
        List<String[]> steps = new ArrayList<>();
        if (!isNextHelperOn()) {
            return steps;
        }
        int level = chr.getLevel();
        try {
            int gifts = chr.getFirstEnterRewards().size();
            if (gifts > 0) {
                steps.add(new String[]{"#b" + gifts + " gift(s)#k to claim", "gifts"});
            }
            if (level <= CHECKLIST_MAX_LEVEL && getStepsDone(chr) < STEPS.length) {
                steps.add(new String[]{"#bChecklist#k " + getStepsDone(chr) + "/" + STEPS.length + ": "
                        + nextStepName(chr), "checklist"});
            }
            int ap = chr.getStat(Stat.ap);
            if (ap >= 5) {
                steps.add(new String[]{"#b" + ap + " ability points#k to spend", "ap"});
            }
            String job = getJobAdvice(chr);
            if (job != null) {
                // the 5th job quests start at the Memory Keeper: the button goes to the Temple of Time
                steps.add(new String[]{job, job.contains("Temple of Time") ? "hunt:" + TEMPLE_OF_TIME : "job"});
            }
            String system = getSystemAdvice(chr);
            if (system != null) {
                steps.add(new String[]{system, "guide"});
            }
            String train = getTrainingAdvice(chr);
            if (train != null) {
                // "hunt:<map>": the menu goes straight to the map it names
                steps.add(new String[]{train, "hunt:" + getTrainingMap(chr)});
            }
            int empty = getEmptyMainSlots(chr);
            if (empty > 0 && level >= 10) {
                steps.add(new String[]{"#b" + empty + " empty gear slot(s)#k", "gear"});
            }
            String boss = getBossAdvice(chr);
            if (boss != null) {
                steps.add(new String[]{boss, "bosses"});
            }
            String[] daily = getSymbolDaily(chr);
            if (daily != null) {
                steps.add(daily);
            }
            String[] region = getNextRegion(chr);
            if (region != null) {
                // the progression goal goes first: where the character's story goes on from here
                // the first region starts with the Arcane River prologue in Nameless Town, not at its hunting map
                boolean first = String.valueOf(SymbolAreas.steps().get(0).town()).equals(region[2]);
                steps.add(0, new String[]{"Next: #b" + region[0] + "#k (from level " + region[1] + ", "
                        + region[3] + ")", first ? "arcane" : "hunt:" + region[2]});
            }
            String[] story = StoryWayModule.getNextStep(chr);
            if (story != null) {
                steps.add(0, story); // a story map no other map leads to
            }
            if (level >= 70 && countLegionChars(chr) < LevelUnlockModule.LEGION_CHARACTERS) {
                steps.add(new String[]{"Make more characters for #blink skills & Legion#k", "links"});
            }
            int groups = LfgModule.countOpen(chr);
            if (groups > 0) {
                steps.add(new String[]{"#b" + groups + " group(s)#k looking for members", "lfg"});
            }
            long rested = ServerFeatures.on(ServerFeatures.Feature.RESTED_EXP) ? getRestedPool(chr) : 0;
            if (rested > 0) {
                steps.add(new String[]{"#dRested EXP: " + restedPercentText(chr, rested) + " of a level (2x hunting "
                        + "EXP)#k", ""});
            }
            if (ServerFeatures.on(ServerFeatures.Feature.SPROUT_BONUS) && isSprout(chr)) {
                steps.add(new String[]{"#gSprout#k: party up for up to " + SPROUT_BONUS_MAX + "% bonus EXP", "lfg"});
            }
        } catch (Exception e) {
            log.error("Next steps failed for " + chr.getName(), e);
        }
        if (steps.size() > 8) {
            return new ArrayList<>(steps.subList(0, 8));
        }
        return steps;
    }

    private static String getJobAdvice(Char chr) {
        int job = chr.getJob();
        int level = chr.getLevel();
        if (JobConstants.isZero(job)) {
            return null;
        }
        // Default mode: no play style, so it goes by whether the character chose a job path
        boolean quest = PlayStyleModule.mode() == PlayStyleModule.Mode.DEFAULT ? !JobPathModule.isManaged(chr)
                : PlayStyleModule.isClassic(chr);
        // no @job to suggest when job paths are off for this character
        quest |= !JobPathModule.isManaged(chr) && !JobPathModule.canChooseNewPath(chr);
        String how = quest ? " (job quest)" : " (@job)";
        if (JobConstants.isBeginnerJob(job)) {
            return level >= 10 ? "#b1st job advancement#k is ready" + how : null;
        }
        int have = JobConstants.getJobLevel(job);
        int need = level >= 100 ? 4 : level >= 60 ? 3 : level >= 30 ? 2 : 1;
        if (have >= 1 && have < need && have <= 4) {
            return "#b" + ordinal(have + 1) + " job advancement#k is ready" + how;
        }
        var qm = chr.getQuestManager();
        if (qm != null && level >= LevelUnlockModule.FIFTH_JOB_LEVEL
                && !qm.hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            // a job path gets it by itself at 200; everyone else does the Memory Keeper's quests
            return quest ? "#b5th job#k is ready: Memory Keeper, Temple of Time" : "#b5th job#k is ready" + how;
        }
        return null;
    }

    private static String ordinal(int n) {
        return switch (n) {
            case 1 -> "1st";
            case 2 -> "2nd";
            case 3 -> "3rd";
            default -> n + "th";
        };
    }

    /** The hunting map {@link #getTrainingAdvice} names, or 0. */
    public static int getTrainingMap(Char chr) {
        int level = chr.getLevel();
        int bracket = QuickMoveModule.getBracketForLevel(level);
        if (bracket < 0 || bracket >= QuickMoveModule.getBracketCount()) {
            return 0;
        }
        // the first map for the level whose Arcane/Authentic Force the character has, else the first for the level
        int first = 0;
        for (int map : QuickMoveModule.getHuntingMaps(bracket)) {
            if (QuickMoveModule.getHuntingMinLevel(map) <= level && level <= QuickMoveModule.getHuntingMaxLevel(map)) {
                if (getForceShort(chr, map) == null) {
                    return map;
                }
                if (first == 0) {
                    first = map;
                }
            }
        }
        return first;
    }

    /** "130 Authentic Force (you: 90)" when the character's force is below what the map asks for, else null. */
    private static String getForceShort(Char chr, int map) {
        int arc = QuickMoveModule.getHuntingArcaneForce(map);
        if (arc > 0 && chr.getTotalStat(BaseStat.arc) < arc) {
            return arc + " Arcane Force (you: " + chr.getTotalStat(BaseStat.arc) + ")";
        }
        int aut = QuickMoveModule.getHuntingAuthenticForce(map);
        if (aut > 0 && chr.getTotalStat(BaseStat.authForce) < aut) {
            return aut + " Authentic Force (you: " + chr.getTotalStat(BaseStat.authForce) + ")";
        }
        return null;
    }

    private static String getTrainingAdvice(Char chr) {
        int map = getTrainingMap(chr);
        if (map == 0 || map == chr.getFieldID()) {
            return null; // nothing listed, or already training in a good spot
        }
        String force = getForceShort(chr, map);
        return "Train at #b#m" + map + "##k (Lv. " + QuickMoveModule.getHuntingMinLevel(map) + "-"
                + QuickMoveModule.getHuntingMaxLevel(map) + ")" + (force == null ? "" : ", needs " + force);
    }

    private static final GearGuideModule.Category[] MAIN_SLOTS = {
            GearGuideModule.Category.WEAPON, GearGuideModule.Category.HAT, GearGuideModule.Category.TOP,
            GearGuideModule.Category.BOTTOM, GearGuideModule.Category.SHOES, GearGuideModule.Category.GLOVES,
            GearGuideModule.Category.CAPE,
    };

    private static int getEmptyMainSlots(Char chr) {
        int empty = 0;
        for (GearGuideModule.Category cat : MAIN_SLOTS) {
            if (GearGuideModule.countEquipped(chr, cat) == 0) {
                empty++;
            }
        }
        // an overall fills the bottom slot too
        if (GearGuideModule.countEquipped(chr, GearGuideModule.Category.BOTTOM) == 0
                && hasOverall(chr)) {
            empty--;
        }
        return Math.max(0, empty);
    }

    private static boolean hasOverall(Char chr) {
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (!item.isCash() && item.getBagIndex() < 100 && net.swordie.ms.constants.ItemConstants.isOverall(item.getItemId())) {
                return true;
            }
        }
        return false;
    }

    private static String getBossAdvice(Char chr) {
        int level = chr.getLevel();
        if (level < 50) {
            return null;
        }
        BossReadinessModule.Boss best = null;
        double baseDps = BossReadinessModule.getBaseDps(chr); // once: it reads the whole stat window
        for (BossReadinessModule.Boss boss : BossReadinessModule.getBosses()) {
            if (boss.entryLevel <= level && BossReadinessModule.getVerdict(chr, boss, baseDps) >= 2
                    && (best == null || boss.entryLevel >= best.entryLevel)) {
                best = boss;
            }
        }
        return best == null ? null : "You can beat #b" + best.name + " (" + best.difficulty + ")#k";
    }

    private static final int TEMPLE_OF_TIME = 270000100;

    private static boolean isArcaneRegion(SymbolAreas.Area r) {
        return ItemConstants.isArcaneSymbol(r.symbol());
    }

    private static Equip wornSymbol(Char chr, int symbol) {
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (item instanceof Equip e && item.getItemId() == symbol) {
                return e;
            }
        }
        return null;
    }

    /**
     * The first region the character is old enough for and has no symbol of yet (worn or in the bag): the next step of
     * its progression (Arcane River after the 5th job). @return {name, level, town, force} or null
     */
    private static String[] getNextRegion(Char chr) {
        int level = chr.getLevel();
        var qm = chr.getQuestManager();
        boolean fifth = qm != null && qm.hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST);
        for (SymbolAreas.Area r : SymbolAreas.steps()) {
            int need = r.level();
            int symbol = r.symbol();
            if (level < need || !fifth) {
                return null;
            }
            if (!chr.hasItem(symbol) && wornSymbol(chr, symbol) == null) {
                String force = r.force() + (isArcaneRegion(r) ? " Arcane Force" : " Authentic Force");
                return new String[]{r.name(), String.valueOf(need), String.valueOf(r.town()), force};
            }
        }
        return null;
    }

    /**
     * The symbol dailies of the newest region whose symbol the character wears and hasn't maxed: {text, action} or
     * null.
     */
    private static String[] getSymbolDaily(Char chr) {
        int level = chr.getLevel();
        List<SymbolAreas.Area> steps = SymbolAreas.steps();
        for (int i = steps.size() - 1; i >= 0; i--) {
            SymbolAreas.Area r = steps.get(i);
            if (level < r.level()) {
                continue;
            }
            Equip e = wornSymbol(chr, r.symbol());
            int max = isArcaneRegion(r) ? ItemConstants.MAX_ARCANE_SYMBOL_LEVEL : ItemConstants.MAX_AUTH_SYMBOL_LEVEL;
            if (e != null && e.getSymbolLevel() < max) {
                return new String[]{"Symbol dailies: #b" + r.name() + "#k (#p" + r.dailyNpc() + "#)",
                        "hunt:" + r.dailyMap()};
            }
        }
        return null;
    }

    /** The newest system the character has something to do in: HEXA, V Matrix, Hyper Stats, Familiars. */
    private static String getSystemAdvice(Char chr) {
        int level = chr.getLevel();
        var qm = chr.getQuestManager();
        if (qm == null) {
            return null;
        }
        if (net.swordie.ms.client.character.hexa.HexaModule.hasSixthJob(chr)) {
            int solErda = net.swordie.ms.client.character.hexa.HexaModule.getSolErda(chr);
            if (solErda > 0) {
                return "#bHEXA Matrix#k: " + solErda + " Sol Erda to spend";
            }
        }
        if (qm.hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST) && chr.getAvailableMatrixPoints() > 0) {
            return "#bV Matrix#k: " + chr.getAvailableMatrixPoints() + " Matrix points to spend";
        }
        if (level >= 140) {
            int hyper = net.swordie.ms.client.character.hyperstats.HyperStatsModule.getTotalHyperStatSp(chr)
                    - chr.getSpentHyperSp();
            if (hyper > 0) {
                return "#b" + hyper + " Hyper Stat points#k to spend";
            }
        }
        if (level >= LevelUnlockModule.INNER_ABILITY_LEVEL && level < LevelUnlockModule.INNER_ABILITY_LEVEL + 5) {
            return "#bInner Ability#k is open (Stats > Ability)";
        }
        var fcm = chr.getFamiliarCodexManager();
        if (fcm != null && !fcm.getFamiliars().isEmpty() && !fcm.isFamiliarsSummoned()) {
            return "#bFamiliars#k aren't summoned";
        }
        return null;
    }

    private static int countLegionChars(Char chr) {
        if (chr.getUnion() == null || chr.getAccount() == null) {
            return 0;
        }
        int n = 0;
        for (Char c : chr.getAccount().getCharacters()) {
            if (c != null && chr.getUnion().isEligibleUnionChar(c)) {
                n++;
            }
        }
        return n;
    }

    /** @return " (Sprout)" for the "Players in this map" list (while the Sprout bonus is on) */
    public static String sproutTag(Char chr) {
        return ServerFeatures.on(ServerFeatures.Feature.SPROUT_BONUS) && isSprout(chr) ? " #g(Sprout)#k" : "";
    }

    /** @return whether "What should I do next?" (@next, the top of @menu, the Maple Administrator) is on */
    public static boolean isNextHelperOn() {
        return ServerFeatures.on(ServerFeatures.Feature.NEXT_HELPER);
    }
}

package net.swordie.ms.client.character;

import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.jobs.adventurer.archer.Pathfinder;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.jobs.shine.ErelLight;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.loaders.SkillData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Job paths (@job): from level {@link GameConstants#JOB_PATH_MIN_LEVEL} a beginner can pick any class's job path. The
 * character then advances along that path automatically at the job advancement levels (10/30/60/100, Dual Blade
 * 10/20/30/45/60/100), getting the SP and AP a job advancement gives. Picking the path at a higher level advances it
 * straight to the job that fits its level, with the SP for every level it has gained.
 * <p>
 * A character that already has a job can use @job too: it keeps its class, and @job advances it whenever it is overdue
 * and from then on automatically.
 * <p>
 * The chosen path is kept in a server-only quest record ({@link GameConstants#JOB_PATH_QR}): "100,110,111,112".
 */
public class JobPathModule {

    private static final Logger log = LogManager.getLogger(JobPathModule.class);

    public static final class ClassChoice {
        private final int finalJob;
        private final String name;
        private final String group;

        ClassChoice(int finalJob, String name, String group) {
            this.finalJob = finalJob;
            this.name = name;
            this.group = group;
        }

        public int getFinalJob() {
            return finalJob;
        }

        public String getName() {
            return name;
        }

        public String getGroup() {
            return group;
        }
    }

    public static final String[] GROUPS = {"Explorers", "Cygnus Knights", "Heroes", "Resistance", "Nova", "Flora",
            "Anima", "Other"};

    // Zero and Pink Bean aren't here: they don't advance like the others (Zero also starts at level 100)
    private static final List<ClassChoice> ALL_CHOICES = List.of(
            new ClassChoice(112, "Hero", "Explorers"),
            new ClassChoice(122, "Paladin", "Explorers"),
            new ClassChoice(132, "Dark Knight", "Explorers"),
            new ClassChoice(212, "Arch Mage (Fire, Poison)", "Explorers"),
            new ClassChoice(222, "Arch Mage (Ice, Lightning)", "Explorers"),
            new ClassChoice(232, "Bishop", "Explorers"),
            new ClassChoice(312, "Bowmaster", "Explorers"),
            new ClassChoice(322, "Marksman", "Explorers"),
            new ClassChoice(332, "Pathfinder", "Explorers"),
            new ClassChoice(412, "Night Lord", "Explorers"),
            new ClassChoice(422, "Shadower", "Explorers"),
            new ClassChoice(434, "Dual Blade", "Explorers"),
            new ClassChoice(512, "Buccaneer", "Explorers"),
            new ClassChoice(522, "Corsair", "Explorers"),
            new ClassChoice(532, "Cannoneer", "Explorers"),
            new ClassChoice(1112, "Dawn Warrior", "Cygnus Knights"),
            new ClassChoice(1212, "Blaze Wizard", "Cygnus Knights"),
            new ClassChoice(1312, "Wind Archer", "Cygnus Knights"),
            new ClassChoice(1412, "Night Walker", "Cygnus Knights"),
            new ClassChoice(1512, "Thunder Breaker", "Cygnus Knights"),
            new ClassChoice(5112, "Mihile", "Cygnus Knights"),
            new ClassChoice(2112, "Aran", "Heroes"),
            new ClassChoice(2217, "Evan", "Heroes"),
            new ClassChoice(2312, "Mercedes", "Heroes"),
            new ClassChoice(2412, "Phantom", "Heroes"),
            new ClassChoice(2512, "Shade", "Heroes"),
            new ClassChoice(2712, "Luminous", "Heroes"),
            new ClassChoice(3112, "Demon Slayer", "Resistance"),
            new ClassChoice(3122, "Demon Avenger", "Resistance"),
            new ClassChoice(3212, "Battle Mage", "Resistance"),
            new ClassChoice(3312, "Wild Hunter", "Resistance"),
            new ClassChoice(3512, "Mechanic", "Resistance"),
            new ClassChoice(3612, "Xenon", "Resistance"),
            new ClassChoice(3712, "Blaster", "Resistance"),
            new ClassChoice(6112, "Kaiser", "Nova"),
            new ClassChoice(6312, "Kain", "Nova"),
            new ClassChoice(6412, "Cadena", "Nova"),
            new ClassChoice(6512, "Angelic Buster", "Nova"),
            new ClassChoice(15112, "Adele", "Flora"),
            new ClassChoice(15212, "Illium", "Flora"),
            new ClassChoice(15512, "Ark", "Flora"),
            new ClassChoice(15412, "Khali", "Flora"),
            new ClassChoice(16412, "Hoyoung", "Anima"),
            new ClassChoice(16212, "Lara", "Anima"),
            new ClassChoice(16112, "Ren", "Anima"),
            new ClassChoice(17512, "Mo Xuan", "Jianghu"),
            new ClassChoice(17212, "Lynn", "Jianghu"),
            new ClassChoice(18212, "Sia Astelle", "Shine"),
            new ClassChoice(18112, "Erel Light", "Shine"),
            new ClassChoice(4112, "Hayato", "Other"),
            new ClassChoice(4212, "Kanna", "Other"),
            new ClassChoice(14212, "Kinesis", "Other")
    );

    /** The classes the creation screen offers; the rest stay out of the choices. */
    private static List<ClassChoice> choices() {
        return ALL_CHOICES.stream().filter(c -> CreatableClasses.isFinalJob(c.getFinalJob())).collect(Collectors.toList());
    }

    // first job of the classes whose 1st job id isn't a multiple of 100, by their 2nd job branch (job / 10 * 10)
    private static final Map<Integer, Integer> SPECIAL_FIRST_JOBS = Map.of(
            330, 301,   // Pathfinder
            530, 501,   // Cannoneer
            570, 508,   // Jett
            3120, 3101  // Demon Avenger
    );
    // Evan's jobs in v232 (the skills are on these jobs)
    private static final int[] EVAN_PATH = {2200, 2211, 2214, 2217};
    private static final int[] ADVANCE_LEVELS = {10, 30, 60, 100};
    private static final int[] DUAL_BLADE_ADVANCE_LEVELS = {10, 20, 30, 45, 60, 100};
    private static final String[] TIER_NAMES = {"1st", "2nd", "3rd", "4th"};
    private static final String[] DUAL_BLADE_TIER_NAMES = {"1st", "1.5th", "2nd", "2.5th", "3rd", "4th"};

    // ------------------------------------------------------------------------------------------------- job paths

    /**
     * The jobs a class goes through from its 1st job up to (and including) the given job, e.g. 112 -> [100, 110,
     * 111, 112]. Beginner jobs give an empty list.
     */
    public static List<Integer> getJobPath(int job) {
        List<Integer> path = new ArrayList<>();
        if (JobConstants.isBeginnerJob(job)) {
            return path;
        }
        if (JobConstants.isEvan(job)) {
            int tier = JobConstants.getJobLevel(job); // also knows the older Evan job ids (2210, 2212)
            for (int i = 0; i < tier && i < EVAN_PATH.length; i++) {
                path.add(EVAN_PATH[i]);
            }
            return path;
        }
        if (JobConstants.isDualBlade(job)) {
            path.add(400);
            for (int j = 430; j <= job; j++) {
                path.add(j);
            }
            return path;
        }
        int branch = job / 10 * 10;
        int first = SPECIAL_FIRST_JOBS.getOrDefault(branch, job / 100 * 100);
        if (SPECIAL_FIRST_JOBS.containsValue(job) || job % 100 == 0) {
            path.add(job);
            return path;
        }
        path.add(first);
        for (int j = branch; j <= job; j++) {
            if (JobConstants.JobEnum.getJobById(j) != null) {
                path.add(j);
            }
        }
        return path;
    }

    public static boolean isDualBladePath(List<Integer> path) {
        return path.size() > 1 && path.get(0) == 400 && path.get(1) == 430;
    }

    public static int[] getAdvanceLevels(List<Integer> path) {
        return isDualBladePath(path) ? DUAL_BLADE_ADVANCE_LEVELS : ADVANCE_LEVELS;
    }

    /**
     * @return the index in the path of the job a Char of the given level should have (-1: still a beginner)
     */
    private static int getTargetIndex(List<Integer> path, int level) {
        int[] levels = getAdvanceLevels(path);
        int target = -1;
        for (int i = 0; i < path.size() && i < levels.length; i++) {
            if (level >= levels[i]) {
                target = i;
            }
        }
        return target;
    }

    /** @return every class with a job path, whatever the level */
    public static List<ClassChoice> getAllChoices() {
        return choices();
    }

    public static ClassChoice getChoice(int finalJob) {
        return choices().stream().filter(c -> c.getFinalJob() == finalJob).findFirst().orElse(null);
    }

    /**
     * @return the classes whose path goes through the given job (for a Char that has a job but no path yet), whatever
     * its level
     */
    static List<ClassChoice> getClassesContaining(int job, int subJob) {
        List<Integer> done = getJobPath(job);
        if (done.isEmpty()) {
            return new ArrayList<>();
        }
        return choices().stream()
                .filter(c -> isPrefix(done, getJobPath(c.getFinalJob())))
                // a Thief (400) is a Dual Blade if its sub job is 1, an Explorer thief otherwise
                .filter(c -> job != 400 || isDualBladePath(getJobPath(c.getFinalJob())) == (subJob == 1))
                .collect(Collectors.toList());
    }

    /**
     * @return the name of the class a job belongs to ("Hero", "Night Walker", ...), the job group's name if the job can
     * still lead to more than one class (e.g. "Warrior" for job 100), or "Beginner"
     */
    public static String getClassName(int job, int subJob) {
        if (JobConstants.isZero(job)) {
            return "Zero";
        }
        if (JobConstants.isPinkBean(job)) {
            return "Pink Bean";
        }
        if (JobConstants.isYeti(job)) {
            return "Yeti";
        }
        if (JobConstants.isGmJob(job)) {
            return "GM";
        }
        if (JobConstants.isBeginnerJob(job)) {
            return "Beginner";
        }
        List<ClassChoice> classes = getClassesContaining(job, subJob);
        if (classes.size() == 1) {
            return classes.get(0).getName();
        }
        switch (job / 100) {
            case 1:
                return "Warrior";
            case 2:
                return "Magician";
            case 3:
                return "Bowman";
            case 4:
                return "Thief";
            case 5:
                return "Pirate";
        }
        return classes.isEmpty() ? "Unknown" : classes.get(0).getGroup();
    }

    /**
     * @return the class group of a job ("Explorers", "Cygnus Knights", ...), or "Other"
     */
    public static String getClassGroup(int job, int subJob) {
        if (JobConstants.isZero(job)) {
            return "Other";
        }
        List<ClassChoice> classes = getClassesContaining(job, subJob);
        return classes.isEmpty() ? "Other" : classes.get(0).getGroup();
    }

    private static boolean isPrefix(List<Integer> prefix, List<Integer> path) {
        return prefix.size() <= path.size() && path.subList(0, prefix.size()).equals(prefix);
    }

    /**
     * @return e.g. "Hero (2nd job)"
     */
    public static String getJobName(List<Integer> path, int index) {
        ClassChoice choice = getChoice(path.get(path.size() - 1));
        String name = choice == null ? "your class" : choice.getName();
        String[] tiers = isDualBladePath(path) ? DUAL_BLADE_TIER_NAMES : TIER_NAMES;
        return index >= 0 && index < tiers.length ? name + " (" + tiers[index] + " job)" : name;
    }

    static boolean isUnsupportedClass(int job) {
        return JobConstants.isZero(job) || JobConstants.isPinkBean(job) || JobConstants.isYeti(job)
                || JobConstants.isGmJob(job) || job == 800
                || job == 9000;
    }

    // ------------------------------------------------------------------------------------------------- record

    /**
     * @return the path this Char advances along automatically, or an empty list if it hasn't got one
     */
    public static List<Integer> getActivePath(Char chr) {
        String value = chr.getQuestManager().getHiddenRecord(GameConstants.JOB_PATH_QR);
        if (value == null) {
            // characters reborn before @job existed kept their path in the rebirth record
            List<Integer> old = RebirthModule.getPreviousPath(chr);
            return RebirthModule.isReborn(chr) && old.contains((int) chr.getJob()) ? old : new ArrayList<>();
        }
        return parsePath(value);
    }

    static List<Integer> parsePath(String value) {
        List<Integer> path = new ArrayList<>();
        if (value != null && !value.isEmpty()) {
            for (String s : value.split(",")) {
                try {
                    path.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return path;
    }

    static String pathToString(List<Integer> path) {
        return path.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    static void setActivePath(Char chr, List<Integer> path) {
        chr.getQuestManager().setHiddenRecord(GameConstants.JOB_PATH_QR, pathToString(path));
    }

    /**
     * @return whether this Char's job advancements are done by this module (it chose a path with @job, or it is a
     * reborn character that still has to choose one)
     */
    public static boolean isManaged(Char chr) {
        return !getActivePath(chr).isEmpty()
                || (JobConstants.isBeginnerJob(chr.getJob()) && RebirthModule.isReborn(chr));
    }

    /**
     * For the class job handlers that advance their characters themselves on level up (Kain, Shade, Evan, ...): they
     * should only do so if this module doesn't.
     */
    public static boolean classHandlesAdvancement(Char chr) {
        // with job_advancement_by_quest_only, characters without a path advance through their class's quests instead
        return !isManaged(chr) && !ServerFeatures.on(ServerFeatures.Feature.JOB_ADVANCEMENT_BY_QUEST_ONLY);
    }

    // ------------------------------------------------------------------------------------------------- choosing

    /**
     * @return the classes this Char can choose from right now (all of them for a beginner; for a Char that has a job
     * but no path yet, the classes its job leads to)
     */
    public static List<ClassChoice> getChoices(Char chr) {
        if (chr.getLevel() < GameConstants.JOB_PATH_MIN_LEVEL || isUnsupportedClass(chr.getJob())
                || !getActivePath(chr).isEmpty()) {
            return new ArrayList<>();
        }
        if (JobConstants.isBeginnerJob(chr.getJob())) {
            return new ArrayList<>(choices());
        }
        return getClassesContaining(chr.getJob(), chr.getSubJob());
    }

    /**
     * @return the class this Char had before its last rebirth, if any (offered first when it chooses again)
     */
    public static ClassChoice getPreviousClass(Char chr) {
        List<Integer> prev = RebirthModule.getPreviousPath(chr);
        return prev.isEmpty() ? null : getChoice(prev.get(prev.size() - 1));
    }

    /**
     * @return whether this Char may choose a new job path: always while the setting job_paths is on; while it's off
     * only reborn characters (a rebirth needs a new path) and bots. A path already chosen keeps working either way.
     */
    public static boolean canChooseNewPath(Char chr) {
        return ServerFeatures.on(ServerFeatures.Feature.JOB_PATHS) || RebirthModule.isReborn(chr)
                || ServerHooks.isAutomated(chr);
    }

    private static String getBlocker(Char chr) {
        if (getActivePath(chr).isEmpty() && !canChooseNewPath(chr)) {
            return "Job paths are turned off on this server: your job advances through your class's job "
                    + "advancement quests.";
        }
        if (chr.getLevel() < GameConstants.JOB_PATH_MIN_LEVEL) {
            return String.format("You can choose your job path from level %d.", GameConstants.JOB_PATH_MIN_LEVEL);
        }
        if (isUnsupportedClass(chr.getJob())) {
            return "Your class advances on its own and can't use @job.";
        }
        if (PlayStyleModule.blocksJobPath(chr)) {
            return "This server plays Classic: your job advances through your class's job advancement quests.";
        }
        if (chr.getInstance() != null || chr.getHP() <= 0 || chr.getMiniRoom() != null || chr.getShop() != null
                || chr.isInTrunk()) {
            return "You can't change your job right now. Leave any instance, trade or shop first.";
        }
        return null;
    }

    /**
     * What @job does: shows the status of a Char that already follows a path (advancing it if it's overdue), adopts
     * the path of a Char whose job only leads to one class, or returns null if the Char has to choose (the script
     * "jobPath" is opened for that).
     */
    public static String handleCommand(Char chr) {
        String blocker = getBlocker(chr);
        if (blocker != null) {
            return blocker;
        }
        List<Integer> path = getActivePath(chr);
        if (!path.isEmpty()) {
            checkAdvancement(chr);
            return getStatus(chr);
        }
        List<ClassChoice> choices = getChoices(chr);
        if (choices.isEmpty()) {
            return "There is no job path for your job.";
        }
        return null; // the "jobPath" script asks first (and warns that @job skips the class questline)
    }

    public static String getStatus(Char chr) {
        List<Integer> path = getActivePath(chr);
        if (path.isEmpty()) {
            return "You haven't chosen a job path yet.";
        }
        int current = getJobPath(chr.getJob()).size() - 1;
        StringBuilder sb = new StringBuilder("Job path: ").append(getJobName(path, current)).append(".");
        int[] levels = getAdvanceLevels(path);
        if (current + 1 < path.size() && current + 1 < levels.length) {
            sb.append(" Next job advancement at level ").append(levels[current + 1]).append(".");
        } else {
            sb.append(" You have reached your final job.");
        }
        return sb.toString();
    }

    /**
     * Makes the Char follow the path of the given class: a beginner becomes that class and advances to the job that
     * fits its level (with the SP for its level); a Char that has a job keeps it and advances if it's overdue.
     *
     * @return a message for the player (what happened, or why it didn't)
     */
    public static synchronized String choosePath(Char chr, int finalJob) {
        String blocker = getBlocker(chr);
        if (blocker != null) {
            return blocker;
        }
        if (!getActivePath(chr).isEmpty()) {
            return "You have already chosen your job path. " + getStatus(chr);
        }
        ClassChoice choice = getChoice(finalJob);
        if (choice == null || getChoices(chr).stream().noneMatch(c -> c.getFinalJob() == finalJob)) {
            return "You can't choose that job path.";
        }
        List<Integer> path = getJobPath(finalJob);
        boolean beginner = JobConstants.isBeginnerJob(chr.getJob());
        int advancements = beginner ? getTargetIndex(path, chr.getLevel()) + 1 : 0;
        if (beginner) {
            int ap = chr.getStat(Stat.ap);
            int advancementAp = advancements * 5;
            if (ap < 0) {
                return "Your AP is outside the safe range. Please contact staff before choosing a job path.";
            }
            if (ap > Short.MAX_VALUE - advancementAp) {
                int needed = ap + advancementAp - Short.MAX_VALUE;
                return String.format("Spend at least %d AP before choosing a job path so the advancement points fit "
                        + "safely.", needed);
            }
        }
        if (beginner && !JobStarterKit.canHold(chr, finalJob)) {
            return "You need " + JobStarterKit.getSpaceNeeded(chr, finalJob) + " for your starter kit. Make room in your "
                    + "inventory and use @job again.";
        }
        setActivePath(chr, path);
        PlayStyleModule.onJobPathChosen(chr); // a job path chosen with @job is the Express play style
        if (beginner) {
            var cs = chr.getAvatarData().getCharacterStat();
            if (isDualBladePath(path)) {
                cs.setSubJob(1);
            } else if (cs.getSubJob() == 1) {
                cs.setSubJob(0); // was a Dual Blade beginner
            }
            assignSpForLevel(chr, path, advancements);
            for (int i = 0; i < advancements; i++) {
                chr.setJob(path.get(i));
                chr.addStatAndSendPacket(Stat.ap, 5); // what a normal job advancement gives
                giveClassAdvancementExtras(chr, path, i);
            }
            int refunded = net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.JOB_PATH_REFUND_AP) ? refundAbilityPoints(chr) : 0;
            sendJobAndSp(chr);
            if (refunded != 0 || RebirthModule.isReborn(chr)) {
                chr.initBaseStats();
            }
            // the first job choice (as a beginner) comes with a starter kit: a weapon and the class's secondary
            List<String> kit = JobStarterKit.give(chr, finalJob);
            log.info(String.format("Char %d (%s) chose the job path %s (%s) at level %d.", chr.getId(), chr.getName(),
                    choice.getName(), pathToString(path), chr.getLevel()));
            return "You are now a " + getJobName(path, advancements - 1) + "! You will advance automatically at "
                    + "the job advancement levels. Your SP has been set for your level."
                    + (refunded > 0 ? " Your STR, DEX, INT and LUK were reset to " + MIN_ABILITY_STAT + " and the "
                    + refunded + " points in them were returned as ability points (AP) to spend on your new class."
                    : refunded < 0 ? " Your stats were left as they are because a full AP refund would exceed the "
                    + "AP limit. No points were lost."
                    : "")
                    + (kit.isEmpty() ? "" : "\r\n\r\nYour starter kit is in your inventory: #b"
                    + String.join(", ", kit) + "#k.");
        }
        log.info(String.format("Char %d (%s) now follows the job path %s.", chr.getId(), chr.getName(), choice.getName()));
        checkAdvancement(chr);
        return "You will now advance automatically as a " + choice.getName() + ". " + getStatus(chr);
    }

    /**
     * Gives a beginner that's becoming a class the SP that class would have at its level: what every level from the
     * 1st job on gives (by the class's job levels, which differ for Dual Blades), plus what every job advancement gives
     * on live servers ({@link JobConstants#getJobAdvancementSp(int)}).
     * SP that was already there is kept if it's more.
     */
    private static void assignSpForLevel(Char chr, List<Integer> path, int advancements) {
        var cs = chr.getAvatarData().getCharacterStat();
        int level = chr.getLevel();
        int finalJob = path.get(path.size() - 1);
        boolean extend = JobConstants.isExtendSpJob(finalJob);
        boolean wasExtend = JobConstants.isExtendSpJob(chr.getJob());

        int currentTotal = 0;
        ExtendSP esp = cs.getExtendSP();
        if (wasExtend && esp != null) {
            for (SPSet set : esp.getSpSet()) {
                currentTotal += Math.max(0, set.getSp());
            }
        } else {
            currentTotal += Math.max(0, cs.getSp());
        }

        if (extend) {
            Map<Integer, Integer> expected = getExpectedSp(path, level, advancements);
            int expectedTotal = expected.values().stream().mapToInt(Integer::intValue).sum();
            if (currentTotal > expectedTotal) {
                int firstSlot = JobConstants.getJobLevel(path.get(0));
                expected.merge(firstSlot, currentTotal - expectedTotal, Integer::sum);
            }
            for (SPSet set : esp.getSpSet()) {
                set.setSp(expected.getOrDefault((int) set.getJobLevel(), 0));
            }
            if (!wasExtend) {
                cs.setSp(0);
            }
        } else {
            // Beast Tamer: one SP pool. Every level gives SP, and the class gives 40 more every 10 levels.
            int expectedTotal = 0;
            for (int i = 0; i < advancements && i < path.size(); i++) {
                expectedTotal += JobConstants.getJobAdvancementSp(path.get(i));
            }
            for (int lv = 2; lv <= level; lv++) {
                expectedTotal += getLevelSp(lv);
                if (lv % 10 == 0) {
                    expectedTotal += lv == 140 ? 52 : 40;
                }
            }
            cs.setSp(Math.max(expectedTotal, currentTotal));
            if (esp != null) {
                for (SPSet set : esp.getSpSet()) {
                    set.setSp(0);
                }
            }
        }
    }

    /**
     * @return SP per job level (ExtendSP slot) that a Char of this path and level has had in total
     */
    static Map<Integer, Integer> getExpectedSp(List<Integer> path, int level, int advancements) {
        Map<Integer, Integer> res = new HashMap<>();
        int finalJob = path.get(path.size() - 1);
        int subJob = isDualBladePath(path) ? 1 : 0;
        for (int lv = GameConstants.JOB_PATH_MIN_LEVEL + 1; lv <= level; lv++) {
            int slot = JobConstants.getJobLevelByCharLevel(finalJob, subJob, lv);
            if (slot > 0) {
                res.merge(slot, getLevelSp(lv), Integer::sum);
            }
        }
        for (int i = 0; i < advancements && i < path.size(); i++) {
            int sp = JobConstants.getJobAdvancementSp(path.get(i));
            if (sp > 0) {
                res.merge(JobConstants.getJobLevel(path.get(i)), sp, Integer::sum);
            }
        }
        return res;
    }

    /**
     * @return the SP a level up to the given level gives (same as Job.handleLevelUp)
     */
    static int getLevelSp(int level) {
        int sp = SkillConstants.getBaseSpByLevel((short) level);
        if ((level % 10) % 3 == 0 && level > 100) {
            sp *= 2;
        }
        return sp;
    }

    private static final int MIN_ABILITY_STAT = 4;

    /** AP is serialized as a signed short; keep refunds within its positive range. */
    private static final int MAX_ABILITY_POINTS = Short.MAX_VALUE;

    /**
     * Puts STR, DEX, INT and LUK back to {@link #MIN_ABILITY_STAT} and returns the points above that as AP: a
     * beginner's points went into STR automatically, which the new class may not use.
     *
     * @return the number of AP returned, or -1 if the full refund would exceed the AP limit
     */
    private static int refundAbilityPoints(Char chr) {
        int ap = chr.getStat(Stat.ap);
        if (ap < 0 || ap > MAX_ABILITY_POINTS) {
            return -1;
        }
        Stat[] stats = {Stat.str, Stat.dex, Stat.inte, Stat.luk};
        int refunded = 0;
        for (Stat stat : stats) {
            refunded += Math.max(0, chr.getStat(stat) - MIN_ABILITY_STAT);
        }
        if (refunded > MAX_ABILITY_POINTS - ap) {
            return -1;
        }

        Map<Stat, Object> changed = new HashMap<>();
        for (Stat stat : stats) {
            int value = chr.getStat(stat);
            if (value > MIN_ABILITY_STAT) {
                chr.setStat(stat, MIN_ABILITY_STAT);
                changed.put(stat, (short) MIN_ABILITY_STAT);
            }
        }
        if (refunded > 0) {
            chr.setStat(Stat.ap, ap + refunded);
            changed.put(Stat.ap, (short) chr.getStat(Stat.ap));
            chr.write(WvsContext.statChanged(changed));
        }
        return refunded;
    }

    static void sendJobAndSp(Char chr) {
        chr.trimUnusableSp(); // a reborn character's already-maxed skills don't get SP again
        var cs = chr.getAvatarData().getCharacterStat();
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.job, chr.getJob());
        stats.put(Stat.sp, JobConstants.isExtendSpJob(chr.getJob()) ? cs.getExtendSP() : (short) cs.getSp());
        stats.put(Stat.ap, (short) chr.getStat(Stat.ap));
        chr.write(WvsContext.statChanged(stats, chr.getSubJob()));
    }

    /**
     * Once per character: gives a job path character the skills of its jobs that it lacks, and the SP of its current
     * job's slot that was trimmed away while those skills were missing (a class made at its 1st job, like Angelic
     * Buster, had its 1st job SP removed at the first level up or login). Past slots are topped up by
     * {@link Char#topUpPastJobSp()}.
     */
    public static void restoreMissingSp(Char chr) {
        var qm = chr.getQuestManager();
        List<Integer> path = getActivePath(chr);
        int index = path.indexOf((int) chr.getJob());
        if (index < 0 || qm.getHiddenRecord(GameConstants.FIRST_JOB_SP_QR) != null || !JobConstants.isExtendSpJob(chr.getJob())) {
            return;
        }
        qm.setHiddenRecord(GameConstants.FIRST_JOB_SP_QR, "1");
        chr.giveMissingJobSkills();
        int slot = JobConstants.getJobLevel(chr.getJob());
        var esp = chr.getAvatarData().getCharacterStat().getExtendSP();
        int unspent = Math.max(0, esp.getSpByJobLevel((byte) slot));
        int spent = chr.getSkillsByJobLevel((byte) slot).stream()
                .filter(s -> !SkillData.isSpLocked(s.getSkillId()))
                .mapToInt(s -> Math.max(0, s.getCurrentLevel())).sum();
        int missing = Math.min(getExpectedSp(path, chr.getLevel(), index + 1).getOrDefault(slot, 0) - spent - unspent,
                chr.getUsableSpForJobLevel(slot) - unspent);
        if (missing <= 0) {
            return;
        }
        esp.setSpToJobLevel(slot, unspent + missing);
        chr.write(WvsContext.statChanged(Map.<Stat, Object>of(Stat.sp, esp)));
        chr.chatMessage(ChatType.SystemNotice, String.format("You received %d job SP your class was missing.", missing));
        log.info(String.format("[SP] %s got %d missing job SP", chr.getName(), missing));
    }

    // ------------------------------------------------------------------------------------------------- advancing

    /**
     * Advances a Char that follows a path as far as its level allows, giving the SP of each advancement (as on live
     * servers, {@link Char#advanceJob(int)}) and 5 AP.
     *
     * @return whether it advanced
     */
    public static boolean checkAdvancement(Char chr) {
        List<Integer> path = getActivePath(chr);
        if (path.isEmpty()) {
            return false;
        }
        List<Integer> done = getJobPath(chr.getJob());
        if (done.isEmpty() || !isPrefix(done, path)) {
            return false; // still a beginner, or changed class some other way: leave it alone
        }
        int current = done.size() - 1;
        int target = getTargetIndex(path, chr.getLevel());
        if (target <= current) {
            return false;
        }
        for (int i = current + 1; i <= target; i++) {
            int job = path.get(i);
            if (chr.advanceJob(job) < 0) {
                continue; // already that job
            }
            chr.addStatAndSendPacket(Stat.ap, 5);
            giveClassAdvancementExtras(chr, path, i);
        }
        sendJobAndSp(chr);
        if (RebirthModule.isReborn(chr)) {
            chr.initBaseStats();
        }
        chr.chatMessage(ChatType.Notice2, "You advanced to " + getJobName(path, target) + "!");
        return true;
    }

    /**
     * The things some classes' own job advancements give besides the job, SP and AP (their handlers skip them for
     * characters this module advances). Skills a reborn character already has keep their levels.
     */
    static void giveClassAdvancementExtras(Char chr, List<Integer> path, int index) {
        int job = path.get(index);
        if (job == 2500) { // Shade: Spirit Bond I
            if (chr.getSkill(Shade.SPIRIT_BOND_I) == null) {
                chr.addSkill(Shade.SPIRIT_BOND_I, 0, 10);
            }
        } else if (job == 301 || job == 330 || job == 331 || job == 332) { // Pathfinder: Ancient Curse levels with the job
            int lv = job == 301 ? 1 : job - 328; // 1, 2, 3, 4
            Skill curse = chr.getSkill(Pathfinder.ANCIENT_CURSE);
            if (curse == null || curse.getCurrentLevel() < lv) {
                chr.addSkill(Pathfinder.ANCIENT_CURSE, lv, 4);
            }
        } else if (JobConstants.isErelLight(job)) { // Erel Light: the job's Keir and medal
            ErelLight.giveAdvancementExtras(chr, job);
        } else if (JobConstants.isIllium(job)) { // Illium: +350 HP / +250 MP per job advancement
            chr.addStatAndSendPacket(Stat.mhp, 350);
            chr.addStatAndSendPacket(Stat.mmp, 250);
        }
    }

    /**
     * Called on every level up (from Job.handleLevelUp).
     */
    public static void onLevelUp(Char chr) {
        checkAdvancement(chr);
        int level = chr.getLevel();
        if (level >= GameConstants.JOB_PATH_MIN_LEVEL && JobConstants.isBeginnerJob(chr.getJob())
                && !isUnsupportedClass(chr.getJob()) && !PlayStyleModule.isClassic(chr) && canChooseNewPath(chr)
                && (level == GameConstants.JOB_PATH_MIN_LEVEL || level % 10 == 0)) {
            // after the level up is done, as some classes advance themselves on level up
            EventManager.addEvent(() -> {
                if (JobConstants.isBeginnerJob(chr.getJob()) && chr.getClient() != null) {
                    chr.chatMessage(ChatType.Notice2, "You can choose your job path: type @job to pick any class. "
                            + GameConstants.JOB_PATH_WARNING);
                }
            }, 1000);
        }
    }
}

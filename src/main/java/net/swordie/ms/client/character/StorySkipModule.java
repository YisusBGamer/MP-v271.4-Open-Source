package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.quest.QuietQuests;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.scripts.ScriptType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The job story choice a new character gets first (UI.wz StoryUI.img/tutoskipUI, AskStoryUI):
 * <ul>
 * <li>screen 0: SKIP (button 1000) or PLAY (1001). PLAY plays the class tutorial.</li>
 * <li>screen 1 after SKIP: skip entirely (1000), story summary (1001), back (2000).</li>
 * <li>SKIP runs the tutorial's own skip, then (on landing) picks the class where the beginner job leads to several
 * (screens 2-10: Explorer class and job, Cygnus, Resistance, Demon), advances to the 1st job, levels to 30, advances
 * to the 2nd job and completes the class's Story &gt; Job quests up to Lv. 30 (resources/story-skip.tsv). The summary
 * shows the completed texts of some of those quests (resources/story-summary.tsv). Zero: screens 12/13, Lv. 120.</li>
 * </ul>
 * Asked from the tutorial's first script (sm.askTutorialSkip, {@link PlayStyleModule#askInTutorial}) instead of the
 * play style question; the play style is asked afterwards, outside the tutorial. Bots never see it.
 */
public final class StorySkipModule {

    private static final Logger log = LogManager.getLogger(StorySkipModule.class);

    /** Server-only record: "" not asked, "play", "skip;&lt;summary 0/1&gt;" (to finish), "done". */
    private static final int STORY_QR = 89999943;
    private static final String SCRIPT = "storySkip";

    private static final String UI = "UI/StoryUI.img/tutoskipUI/";
    private static final int SKIP = 1000;
    private static final int PLAY = 1001;
    private static final int SUMMARY = 1001;
    private static final int BACK = 2000;
    private static final int FIRST_CARD = 1000; // a card screen's buttons count up from here

    static final int TARGET_LEVEL = 30;
    static final int ZERO_TARGET_LEVEL = 120;
    private static final int DUAL_BLADE_SUB_JOB = 1;
    private static final int CANNONEER_SUB_JOB = 2;
    /** one a second: a welcome or Burning window can stay open a few minutes */
    private static final int MAX_START_TRIES = 300;

    /** Explorer class card (screen 2, buttons 1000-1004) -> its job screen and finals in button order. */
    private static final int[][] EXPLORER_JOBS = {
            {3, 112, 122, 132}, {4, 212, 222, 232}, {5, 322, 312}, {6, 422, 412}, {7, 512, 522}};
    /** Beginner job -> its job screen and finals in button order. */
    private static final Map<Integer, int[]> BEGINNER_SCREENS = Map.of(
            1000, new int[]{8, 1112, 1212, 1312, 1412, 1512},
            3000, new int[]{9, 3712, 3212, 3312, 3512},
            3001, new int[]{10, 3112, 3122});

    private static final Set<Integer> scheduled = ConcurrentHashMap.newKeySet();

    private StorySkipModule() {
    }

    // ------------------------------------------------------------------------------------------------- asking

    /**
     * @return whether the choice is shown to this character (and, once made, answered from the record)
     */
    private static boolean applies(Char chr) {
        return chr != null && chr.getQuestManager() != null && !ServerHooks.isAutomated(chr)
                && hasSkip(chr.getJob());
    }

    /** @return whether characters of this job get the skip (Beast Tamer, Pink Bean and Yeti have no job path) */
    static boolean hasSkip(int job) {
        return JobConstants.isZero(job) || !JobConstants.isBeastTamer(job) && !JobPathModule.isUnsupportedClass(job);
    }

    /**
     * The tutorial's first question.
     *
     * @return true to skip, false to play, null when this character isn't asked here (the play style question goes on)
     */
    static Boolean askInTutorial(ScriptManagerImpl sm, Char chr, boolean newCharacter) {
        if (!applies(chr)) {
            return null;
        }
        String rec = record(chr);
        if ("play".equals(rec)) {
            return false;
        }
        if (rec.startsWith("skip")) {
            return true; // the tutorial's script ran again before the skip finished
        }
        if (!rec.isEmpty() || !newCharacter) {
            return null;
        }
        boolean zero = JobConstants.isZero(chr.getJob());
        int first = zero ? 12 : 0;
        int second = zero ? 13 : 1;
        while (true) {
            int a = sm.sendAskStoryUI(UI + first, "", (byte) 0);
            if (a == PLAY) {
                setRecord(chr, "play");
                log.info(chr.getName() + " chose to play the job story");
                return false;
            }
            if (a != SKIP) {
                continue; // closed: ask again
            }
            int b = sm.sendAskStoryUI(UI + second, "", (byte) 0);
            if (b == BACK || (b != SKIP && b != SUMMARY)) {
                continue;
            }
            setRecord(chr, "skip;" + (b == SUMMARY ? 1 : 0));
            log.info(chr.getName() + " chose to skip the job story" + (b == SUMMARY ? " (with the summary)" : ""));
            return true;
        }
    }

    private static String record(Char chr) {
        String rec = chr.getQuestManager().getHiddenRecord(STORY_QR);
        return rec == null ? "" : rec;
    }

    private static void setRecord(Char chr, String value) {
        chr.getQuestManager().setHiddenRecord(STORY_QR, value);
    }

    /** @return whether a SKIP still has to be finished */
    public static boolean isPending(Char chr) {
        return chr != null && chr.getQuestManager() != null && record(chr).startsWith("skip");
    }

    /**
     * Luminous's Light or Dark choice in its tutorial's skip branch: screen 11 (Light 1000, Dark 1001) during a SKIP.
     *
     * @return 0 Light, 1 Dark, or -1 when no SKIP is under way (the tutorial asks with its own menu)
     */
    public static int askLuminousPath(ScriptManagerImpl sm, Char chr) {
        if (!isPending(chr)) {
            return -1;
        }
        while (true) {
            int a = sm.sendAskStoryUI(UI + 11, "", (byte) 0);
            if (a == SKIP || a == SKIP + 1) {
                return a - SKIP;
            }
        }
    }

    /**
     * Express starts every class at Lv. 30 (Zero 120) with its job advancements, however Express was picked (the play
     * style question, @menu, @job, the server's mode): queues the same finish as SKIP. It keeps
     * the tutorial's own skip (Lv. 10).
     *
     * @return whether a finish is queued (it runs on the next {@link #schedule} out of the tutorial)
     */
    static boolean queueExpress(Char chr) {
        if (!applies(chr)) {
            return false;
        }
        String rec = record(chr);
        if (rec.startsWith("skip")) {
            return true;
        }
        int target = JobConstants.isZero(chr.getJob()) ? ZERO_TARGET_LEVEL : TARGET_LEVEL;
        if ("done".equals(rec) || chr.getLevel() >= target) {
            return false;
        }
        setRecord(chr, "skip;0");
        log.info(chr.getName() + " is Express: the job story skip to Lv. " + target + " is queued");
        return true;
    }

    // ------------------------------------------------------------------------------------------------- finishing

    /** Called when the character left its tutorial (or logged in outside it) with a SKIP to finish. */
    static void schedule(Char chr) {
        if (!isPending(chr) || !scheduled.add(chr.getId())) {
            return;
        }
        tryStart(chr, 0);
    }

    private static void tryStart(Char chr, int tries) {
        EventManager.addEvent(() -> {
            try {
                if (!chr.isOnline() || chr.getClient() == null || chr.getField() == null || !isPending(chr)) {
                    scheduled.remove(chr.getId());
                    return;
                }
                var sm = chr.getScriptManager();
                boolean busy = sm.isActive(ScriptType.Npc) || sm.isActive(ScriptType.Quest)
                        || sm.isActive(ScriptType.Field) || sm.isActive(ScriptType.FirstEnterField)
                        || sm.isWaitingForAnswer() || sm.isUiLocked();
                if (busy) {
                    if (tries < MAX_START_TRIES) {
                        tryStart(chr, tries + 1);
                    } else {
                        scheduled.remove(chr.getId()); // tried again on the next map change or login
                    }
                    return;
                }
                scheduled.remove(chr.getId());
                sm.startScript(PlayStyleModule.SPEAKER, SCRIPT, ScriptType.Npc);
            } catch (Exception e) {
                scheduled.remove(chr.getId());
                log.error("Could not finish the story skip of " + chr.getName(), e);
            }
        }, tries == 0 ? 1500 : 1000);
    }

    /** scripts/npc/storySkip.py: the class choice where needed, Lv. 30 and the 2nd job, the quests, the summary. */
    public static void run(ScriptManagerImpl sm, Char chr) {
        if (!isPending(chr)) {
            return;
        }
        boolean summary = record(chr).endsWith(";1");
        boolean zero = JobConstants.isZero(chr.getJob());
        int target = zero ? ZERO_TARGET_LEVEL : TARGET_LEVEL;
        int startLevel = chr.getLevel();
        int done;
        QuietQuests.begin(chr); // the quests reach the client with a map reload, not as a notice each
        try {
            if (!zero) {
                Integer finalJob = pickFinalJob(sm, chr);
                if (finalJob == null) {
                    sm.setSpeakerID(PlayStyleModule.SPEAKER);
                    sm.sendSayOkay("Your job story couldn't be skipped from here. Carry on with your class's quests.");
                    setRecord(chr, "done");
                    log.warn("Story skip: no class path for " + chr.getName() + " (job " + chr.getJob() + ")");
                    PlayStyleModule.askSoon(chr);
                    return;
                }
                boolean beginner = JobConstants.isBeginnerJob(chr.getJob());
                List<Integer> path = JobPathModule.getJobPath(finalJob);
                if (beginner && JobStarterKit.canHold(chr, finalJob)) {
                    JobStarterKit.give(chr, finalJob);
                }
                advanceUpTo(chr, path, chr.getLevel());
                if (chr.getLevel() < target) {
                    sm.addLevel(target - chr.getLevel());
                }
                advanceUpTo(chr, path, chr.getLevel());
            } else {
                if (chr.getLevel() < target) {
                    sm.addLevel(target - chr.getLevel());
                }
                // the weapon window and the later chapters, as Express Zeros get them
                for (int q : new int[]{net.swordie.ms.constants.QuestConstants.LAPIS_LAZULI,
                        net.swordie.ms.constants.QuestConstants.ZERO_LAST_QUEST}) {
                    if (!chr.getQuestManager().hasQuestCompleted(q)) {
                        chr.getQuestManager().completeQuest(q, false);
                    }
                }
            }
            done = completeStoryQuests(chr);
            BurningModule.onLevelUp(chr, startLevel, chr.getLevel());
            AutoAbilityModule.onLevelUp(chr); // AP saved up as a beginner and from the advancements
            ClassQuestSetupModule.onLogin(chr);
        } finally {
            QuietQuests.end(chr, true);
        }
        setRecord(chr, "done");
        log.info(String.format("%s skipped the job story: level %d, job %d, %d quests completed", chr.getName(),
                chr.getLevel(), chr.getJob(), done));
        sm.setSpeakerID(PlayStyleModule.SPEAKER);
        sm.removeEscapeButton();
        if (summary) {
            showSummary(sm, chr);
        }
        sm.sendSayOkay(String.format("Your job story up to #bLv. %d#k is complete. You can carry on from here as "
                + "#b%s#k.", target, JobPathModule.getClassName(chr.getJob(), chr.getSubJob())));
        PlayStyleModule.askSoon(chr);
    }

    /** @return the class's last job, asking with the class/job cards where the job still leads to several */
    private static Integer pickFinalJob(ScriptManagerImpl sm, Char chr) {
        List<Integer> active = JobPathModule.getActivePath(chr);
        if (!active.isEmpty()) {
            return active.get(active.size() - 1); // @job was used before the skip finished
        }
        int job = chr.getJob();
        int subJob = chr.getSubJob();
        if (isExplorerBeginner(job, subJob)) {
            while (true) { // the class card, then its job card
                int a = sm.sendAskStoryUI(UI + 2, "", (byte) 0);
                if (a >= FIRST_CARD && a < FIRST_CARD + EXPLORER_JOBS.length) {
                    return askCards(sm, EXPLORER_JOBS[a - FIRST_CARD]);
                }
            }
        }
        int[] cards = cardsFor(job, subJob);
        if (cards != null) {
            return askCards(sm, cards);
        }
        List<Integer> finals = finalJobsFor(job, subJob);
        return finals.size() == 1 ? finals.get(0) : null;
    }

    private static boolean isExplorerBeginner(int job, int subJob) {
        return job == JobEnum.BEGINNER.getJobId() && subJob != DUAL_BLADE_SUB_JOB && subJob != CANNONEER_SUB_JOB;
    }

    /** @return the job cards asked for this job (screen, then the finals in button order), or null when none are */
    static int[] cardsFor(int job, int subJob) {
        if (JobConstants.isBeginnerJob(job)) {
            return BEGINNER_SCREENS.get(job);
        }
        if (finalJobsFor(job, subJob).size() > 1) { // an Explorer 1st job
            for (int[] e : EXPLORER_JOBS) {
                if (JobPathModule.getJobPath(e[1]).get(0) == job) {
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * Level-independent: a tutorial may finish below Lv. 10, where {@link JobPathModule#getChoices} has nothing yet.
     *
     * @return the last jobs of the classes a character of this job can still become
     */
    static List<Integer> finalJobsFor(int job, int subJob) {
        List<Integer> res = new ArrayList<>();
        if (!JobConstants.isBeginnerJob(job)) {
            JobPathModule.getClassesContaining(job, subJob).forEach(c -> res.add(c.getFinalJob()));
            return res;
        }
        if (job == JobEnum.BEGINNER.getJobId()) {
            if (subJob == DUAL_BLADE_SUB_JOB) {
                res.add(JobEnum.BLADE_MASTER.getJobId());
            } else if (subJob == CANNONEER_SUB_JOB) {
                res.add(JobEnum.CANNON_MASTER.getJobId());
            } else {
                for (int[] e : EXPLORER_JOBS) {
                    for (int i = 1; i < e.length; i++) {
                        res.add(e[i]);
                    }
                }
            }
            return res;
        }
        int[] screen = BEGINNER_SCREENS.get(job);
        if (screen != null) {
            for (int i = 1; i < screen.length; i++) {
                res.add(screen[i]);
            }
            return res;
        }
        for (JobPathModule.ClassChoice c : JobPathModule.getAllChoices()) {
            List<Integer> path = JobPathModule.getJobPath(c.getFinalJob());
            JobEnum first = path.isEmpty() ? null : JobEnum.getJobById(path.get(0));
            if (first != null && first.getBeginnerJobId() == job) {
                res.add(c.getFinalJob());
            }
        }
        return res;
    }

    /** @param screen the screen number, then the finals in button order (1000, 1001, ...) */
    private static int askCards(ScriptManagerImpl sm, int[] screen) {
        while (true) {
            int a = sm.sendAskStoryUI(UI + screen[0], "", (byte) 0);
            int i = a - FIRST_CARD + 1;
            if (i >= 1 && i < screen.length) {
                return screen[i];
            }
        }
    }

    /** Advances along the path through every job whose advancement level the character has reached. */
    private static void advanceUpTo(Char chr, List<Integer> path, int level) {
        int[] levels = JobPathModule.getAdvanceLevels(path);
        boolean changed = false;
        for (int i = 0; i < path.size() && i < levels.length; i++) {
            int job = path.get(i);
            if (levels[i] > level
                    || JobConstants.getAdvancementTier(chr.getJob()) >= JobConstants.getAdvancementTier(job)) {
                continue;
            }
            if (chr.advanceJob(job) < 0) {
                continue;
            }
            chr.addStatAndSendPacket(Stat.ap, 5);
            JobPathModule.giveClassAdvancementExtras(chr, path, i);
            changed = true;
        }
        if (changed) {
            JobPathModule.sendJobAndSp(chr);
        }
    }

    // ------------------------------------------------------------------------------------------------- data

    private record StoryRow(Set<Integer> jobs, int level, List<Integer> quests) {
    }

    private record SummaryLine(String title, String text) {
    }

    private static volatile List<StoryRow> rows;
    private static volatile Map<Integer, List<SummaryLine>> summaries;

    private static void load() {
        if (rows != null) {
            return;
        }
        List<StoryRow> r = new ArrayList<>();
        Map<Integer, List<SummaryLine>> s = new HashMap<>();
        Map<String, Integer> keyIndex = new HashMap<>();
        try {
            for (String line : Files.readAllLines(Path.of(ServerConstants.RESOURCES_DIR, "story-skip.tsv"),
                    StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] c = line.split("\t", -1);
                if (c.length < 4) {
                    continue;
                }
                Set<Integer> jobs = new HashSet<>();
                for (String j : c[1].split(",")) {
                    jobs.add(Integer.parseInt(j.trim()));
                }
                List<Integer> quests = new ArrayList<>();
                for (String q : c[3].split(",")) {
                    if (!q.isBlank()) {
                        quests.add(Integer.parseInt(q.trim()));
                    }
                }
                keyIndex.put(c[0], r.size());
                r.add(new StoryRow(jobs, Integer.parseInt(c[2].trim()), quests));
            }
            for (String line : Files.readAllLines(Path.of(ServerConstants.RESOURCES_DIR, "story-summary.tsv"),
                    StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] c = line.split("\t", -1);
                Integer idx = c.length < 4 ? null : keyIndex.get(c[0]);
                if (idx != null) {
                    s.computeIfAbsent(idx, k -> new ArrayList<>())
                            .add(new SummaryLine(c[2], c[3].replace("\\n", "\r\n")));
                }
            }
        } catch (Exception e) {
            log.error("Could not read the story skip data", e);
        }
        summaries = s;
        rows = r;
    }

    private static int rowOf(Char chr) {
        return rowOf(chr.getJob());
    }

    /** @return the story-skip.tsv row of this job, or -1 */
    static int rowOf(int job) {
        load();
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).jobs().contains(job)) {
                return i;
            }
        }
        return -1;
    }

    private static int completeStoryQuests(Char chr) {
        int row = rowOf(chr);
        if (row < 0) {
            return 0;
        }
        QuestManager qm = chr.getQuestManager();
        int n = 0;
        for (int q : rows.get(row).quests()) {
            if (!qm.hasQuestCompleted(q) && qm.completeQuestSilently(q)) {
                n++;
            }
        }
        return n;
    }

    private static void showSummary(ScriptManagerImpl sm, Char chr) {
        int row = rowOf(chr);
        List<SummaryLine> lines = row < 0 ? null : summaries.get(row);
        if (lines == null || lines.isEmpty()) {
            sm.sendNext("#e[Story summary]#n\r\n\r\nThere's no summary of your class's story yet.");
            return;
        }
        for (int i = 0; i < lines.size(); i += 2) {
            StringBuilder sb = new StringBuilder("#e[Story summary]#n");
            for (int j = i; j < Math.min(i + 2, lines.size()); j++) {
                sb.append("\r\n\r\n#b").append(lines.get(j).title()).append("#k\r\n").append(lines.get(j).text());
            }
            sm.sendNext(sb.toString());
        }
    }
}

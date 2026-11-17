package net.swordie.ms.client.character;

import net.swordie.ms.client.jobs.legend.Luminous;
import net.swordie.ms.client.jobs.legend.Phantom;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.constants.JobConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Makes the official job advancement quests reachable for characters that didn't play (all of) their class's
 * tutorial: classes this server creates past their tutorial (Kain, Adele, Lara, Hoyoung: level 10 with their 1st job)
 * and tutorials or skips that don't complete the quests the next job advancement quest requires (Kanna, whose own
 * tutorial is missing; Evan's 1st job quest; Pathfinder). Story quests a character can still play (Hayato's
 * Zipangu story before his 2nd job, Illium's) are left to be played.
 * <p>
 * Each job advancement quest in Quest.wz requires the class's earlier story quests (e.g. Kain's "Second Malice" needs
 * "First Malice", which the Kain tutorial completes). Once a character has its class's 1st job, those earlier quests
 * are completed here (no rewards: what the tutorial gave was given at character creation or by the skip). Called on
 * every login; it only does something the first time it's needed.
 */
public final class ClassQuestSetupModule {
    private static final int KINGS_SEAT_FIELD = 101050010;

    private static final Logger log = LogManager.getLogger(ClassQuestSetupModule.class);

    private ClassQuestSetupModule() {
    }

    /** @return the quests the class's job advancement quests need done once the character has its 1st job */
    private static int[] getRequiredQuests(Char chr) {
        int job = chr.getJob();
        if (JobConstants.isKain(job) && job >= JobConstants.JobEnum.KAIN_1.getJobId()) {
            return new int[]{39401, 39402}; // Just Match the Headcount, First Malice -> Second Malice (39451)
        }
        if (JobConstants.isAdele(job) && job >= JobConstants.JobEnum.ADELE_1.getJobId()) {
            if (chr.getQuestManager().hasQuestCompleted(39672)) {
                return new int[]{39600, 39651}; // tutorial played (Ristonia 2): Escaping the Mansion (39627) is played
            }
            return new int[]{39600, 39627, 39651}; // ... First Sword's Calling -> Second Sword's Calling (39652)
        }
        if (JobConstants.isLara(job) && job >= JobConstants.JobEnum.LARA_1.getJobId()) {
            return new int[]{36201, 36202}; // Hero for Hire, Closer to the Mountain Kids -> Second Sentiment (36256)
        }
        if (JobConstants.isHoYoung(job) && job >= JobConstants.JobEnum.HO_YOUNG_1.getJobId()) {
            return new int[]{39551}; // The Path of the Sage I -> II (39552)
        }
        if (JobConstants.isEvan(job) && job != JobConstants.JobEnum.EVAN_NOOB.getJobId()) {
            return new int[]{22130}; // Dragon Master 1st Job Advancement -> 2nd (22131)
        }
        if (JobConstants.isPathFinder(job)) {
            // Find Altar Key 1-3, Power of the Relic -> Research and Rescue (35904), Relic's Guidance - Partem (35939)
            return new int[]{35900, 35901, 35902, 35903};
        }
        if (JobConstants.isKanna(job) && job >= JobConstants.JobEnum.KANNA_1.getJobId()) {
            // Kanna's own tutorial doesn't exist here (she plays Hayato's), so her story quest is never completed
            return new int[]{57439}; // -> Maple Adjustment Period (57458)
        }
        if (JobConstants.isCadena(job) && job >= JobConstants.JobEnum.CADENA_1.getJobId()) {
            // The Nightmare to Seeking Treatment -> Beginner's Luck (34604); First Fighting Tactic -> Second (34657)
            return new int[]{34600, 34601, 34602, 34603, 34656};
        }
        if (JobConstants.isArk(job) && job >= JobConstants.JobEnum.ARK_1.getJobId()) {
            return new int[]{34901}; // Creeping Abyss -> Crumbling Abyss (34902)
        }
        return new int[0];
    }

    /**
     * Job advancements that happen when a quest is handed in to its NPC (the client sends no script request for these,
     * so it's done here): {quest, from job, to job}.
     */
    private static final int[][] ADVANCE_ON_COMPLETE = {
            {34834, 15210, 15211}, // Illium: Sanctuary Discovered 1 (3rd job)
            {34842, 15211, 15212}, // Illium: Discovery of the Sanctuary 2 (4th job)
            {25101, 2400, 2410}, // Phantom: That's So Raven (2nd job, Orbis vault)
            {25111, 2410, 2411}, // Phantom: The Lowdown (3rd job, Ariant vault)
            {25122, 2411, 2412}, // Phantom: The Empress and the Thief (4th job, Leafre vault)
    };

    /**
     * Skills a class gets with a job advancement that aren't skills of the job itself (so {@link Char#setJob(int)}
     * doesn't add them): {from job, to job (inclusive), skill}. Phantom's Judgment Draw comes from his vault journals.
     */
    private static final int[][] JOB_EXTRA_SKILLS = {
            {2410, 2412, Phantom.JUDGMENT_DRAW_1}, // Judgment Draw (2nd job)
            {2410, 2412, Phantom.JUDGMENT_DRAW_AUTO_MANUAL}, // Judgment Draw Auto/Manual
            {2412, 2412, Phantom.JUDGMENT_DRAW_2}, // Judgment Draw, 4th job version
    };

    /** Called when the job changed (Char#setJob) and on login: gives {@link #JOB_EXTRA_SKILLS} that are missing. */
    public static void onJobChanged(Char chr) {
        int job = chr.getJob();
        for (int[] e : JOB_EXTRA_SKILLS) {
            if (job >= e[0] && job <= e[1] && chr.getSkill(e[2]) == null) {
                chr.addSkill(e[2], 1, 1);
            }
        }
    }

    /** Called after a quest was completed (QuestManager#completeQuest). */
    public static void onQuestCompleted(Char chr, int questId) {
        for (int[] a : ADVANCE_ON_COMPLETE) {
            if (a[0] == questId && chr.getJob() == a[1]) {
                chr.getScriptManager().jobAdvance((short) a[2]); // + the advancement's SP and AP
                if (chr.getField() != null) {
                    chr.getField().broadcastPacket(net.swordie.ms.connection.packet.UserRemote.effect(chr.getId(),
                            net.swordie.ms.connection.packet.Effect.changeJobEffect(chr)));
                }
                log.info(String.format("%s advanced to job %d (quest %d)", chr.getName(), a[2], questId));
            }
        }
        runDoneScript(chr, questId);
    }

    /** Called when a quest without a start script is accepted at an NPC (QuestHandler). */
    public static void onQuestAccepted(Char chr, int questId) {
        runHookScript(chr, questId, "start_");
    }

    /**
     * Called when an in-progress quest's requirements become met (QuestManager#notifyIfReady, e.g. the last kill):
     * scripts/quest/ready_&lt;quest id&gt;.py, for mission quests that have no NPC to report to.
     */
    public static void onQuestReady(Char chr, int questId) {
        runHookScript(chr, questId, "ready_");
    }

    /**
     * Story scenes for quests that have no start/end script of their own (the client starts or completes those without
     * asking the server for a script): scripts/quest/start_&lt;quest id&gt;.py runs after such a quest is accepted, and
     * scripts/quest/done_&lt;quest id&gt;.py after it is completed, half a second later, as a quest script
     * (parentID = the quest).
     */
    private static void runDoneScript(Char chr, int questId) {
        runHookScript(chr, questId, "done_");
    }

    private static void runHookScript(Char chr, int questId, String prefix) {
        String name = prefix + questId;
        if (!new java.io.File(net.swordie.ms.ServerConstants.SCRIPT_DIR + "/quest/" + name + ".py").exists()) {
            return;
        }
        net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
            if (chr.getField() != null && chr.isOnline()) {
                chr.getScriptManager().startScript(questId, name, net.swordie.ms.scripts.ScriptType.Quest,
                        java.util.Map.<String, Object>of("startQuest", false, "resignQuest", false, "questNpc", 0));
            }
        }, 500);
    }

    /**
     * Quest records a class tutorial sets at its end and the class's next story quest needs (Quest.wz infoNumber),
     * which older tutorial skips didn't set: Mercedes' Awakening (24040) needs 24009 = 1, the Demon's The Resistance
     * and You (23230) needs 23209 = 1, Luminous' Sojourn's End (25530) / Containing Darkness (25560) need 25505
     * route=0 (Light) / route=1 (Dark).
     */
    private static void setStoryRecords(Char chr) {
        int job = chr.getJob();
        QuestManager qm = chr.getQuestManager();
        var sm = chr.getScriptManager();
        if (job / 100 == 23 && qm.hasQuestCompleted(24005) && notStarted(qm, 24040)
                && chr.getFieldID() != KINGS_SEAT_FIELD /* the King's Seat: leaving it plays the scene that sets it */
                && !"1".equals(sm.getQRValue(24009))) {
            sm.createQuestWithQRValue(24009, "1", false);
        }
        if (job / 100 == 31 && notStarted(qm, 23230) && !"1".equals(sm.getQRValue(23209))) {
            sm.createQuestWithQRValue(23209, "1", false);
        }
        String route = sm.getQRValue(25505);
        if (job / 100 == 27 && notStarted(qm, 25530) && notStarted(qm, 25560)
                && (route == null || !route.contains("route="))) {
            // the path chosen at the end of the tutorial: its first attack skill
            boolean dark = chr.hasSkill(Luminous.ABYSSAL_DROP) && !chr.hasSkill(Luminous.FLASH_SHOWER); // Abyssal Drop, Flash Shower
            sm.createQuestWithQRValue(25505, dark ? "route=1" : "route=0", true);
        }
    }

    private static boolean notStarted(QuestManager qm, int questId) {
        return !qm.hasQuestInProgress(questId) && !qm.hasQuestCompleted(questId);
    }

    /** Called on login (Char#login). */
    public static void onLogin(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return;
        }
        try {
            onJobChanged(chr);
            QuestManager qm = chr.getQuestManager();
            for (int q : getRequiredQuests(chr)) {
                if (!qm.hasQuestCompleted(q)) {
                    qm.completeQuestSilently(q);
                    log.debug(String.format("%s: completed quest %d for the job advancement quests", chr.getName(), q));
                }
            }
            setStoryRecords(chr);
        } catch (Exception e) {
            log.error("Could not set up the job advancement quests of " + chr.getName(), e);
        }
    }
}

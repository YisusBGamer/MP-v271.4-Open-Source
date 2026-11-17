package net.swordie.ms.client.character;

import net.swordie.ms.client.character.achievement.MapleAchievements;
import net.swordie.ms.client.character.potential.CharacterPotentialMan;
import net.swordie.ms.client.character.union.ChampionModule;
import net.swordie.ms.client.character.union.UnionArtifactModule;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.SkillData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Systems that live servers unlock through auto-start quests, which are easy to miss in a long quest list, are
 * unlocked here as soon as the character qualifies (checked on login and on every level-up):
 * <ul>
 *     <li>Inner Ability (Hidden Abilities): all three lines at level 50 (quests 12394-12396).</li>
 *     <li>5th Job (V Matrix, its skills and Arcane Symbols): at level 200, for characters that chose a job path with
 *     &#64;job (quests 1460-1466). Everyone else does the official 5th job questline
 *     (setting job_advancement_by_quest_only; off: everyone gets it here).</li>
 *     <li>6th Job (HEXA Matrix, the class's Origin node): at level 260 once the 5th job is done (quest 1488; there
 *     is no scripted questline for it).</li>
 *     <li>Legion: as soon as the account qualifies (Legion level 500 with 3 eligible characters, quest 16013). The
 *     introduction (16059) at level 60; once the Legion is founded, the Legion Artifact (503149) and the Legion
 *     Champions (504775), whose windows the client keeps locked until those quests are done.</li>
 *     <li>Maple Achievements: at level 33 (quest 16370). The client opens the window only once the account's
 *     achievement 1 "Begin Maple Achievements" is complete, which the quest's missing script would do.</li>
 *     <li>Link Skills: the "Teaching Link Skill" quests (6615 at 70, Zero's 6616 at 118); the link skill itself
 *     comes with the level-up.</li>
 *     <li>V Matrix tutorial (1478) for characters that did the official 5th job questline.</li>
 * </ul>
 */
public final class LevelUnlockModule {

    private static final Logger log = LogManager.getLogger(LevelUnlockModule.class);

    public static final int INNER_ABILITY_LEVEL = 50;
    private static final int[] INNER_ABILITY_QUESTS = {12394, 12395, 12396};
    public static final int FIFTH_JOB_LEVEL = 200;
    // 1478 (the V Matrix) too: the Temple of Time's way into Arcane River (talkToPresent) checks it, and nothing
    // else completes it for a character whose 5th job came this way
    private static final int[] FIFTH_JOB_QUESTS = {1460, 1461, 1462, 1463, 1464, QuestConstants.FIFTH_JOB_QUEST, 1466,
            1478};
    public static final int LEGION_LEVEL = 500;
    public static final int LEGION_CHARACTERS = 3;
    public static final int LEGION_INTRO_LEVEL = 60;
    private static final int LEGION_INTRO_QUEST = 16059;
    private static final int LEGION_ARTIFACT_QUEST = 503149;
    public static final int ACHIEVEMENTS_LEVEL = 33;
    public static final int ACHIEVEMENTS_QUEST = 16370;
    /** World-shared record GMS sets with quest 16370: the menu button's gate. */
    public static final int ACHIEVEMENTS_WSR = 131;
    public static final String ACHIEVEMENTS_WSR_VALUE = "uiOpen=1";
    /** Etc/Achievement/AchievementData/1 "Begin Maple Achievements": the client's gate for the window */
    private static final int ACHIEVEMENTS_OPEN = 1;
    public static final int LINK_SKILL_LEVEL = 70;
    private static final int LINK_SKILL_QUEST = 6615;
    private static final int ZERO_LINK_SKILL_LEVEL = 118;
    private static final int ZERO_LINK_SKILL_QUEST = 6616;
    private static final int ZERO_LINK_SKILL_PREQ = 40914;
    private static final int V_MATRIX_QUEST = 1478;

    private LevelUnlockModule() {
    }

    /** Unlocks everything the character qualifies for. Safe to call any number of times. */
    public static void check(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return;
        }
        try {
            unlockInnerAbility(chr);
        } catch (Exception e) {
            log.error("Could not unlock the Inner Ability of " + chr.getName(), e);
        }
        try {
            unlockFifthJob(chr);
        } catch (Exception e) {
            log.error("Could not unlock the 5th job of " + chr.getName(), e);
        }
        try {
            giveMissingVSkills(chr);
        } catch (Exception e) {
            log.error("Could not give the starting V skills of " + chr.getName(), e);
        }
        try {
            ArcaneRiverSkipModule.giveBasicSymbolIfMissing(chr); // a path's 5th job completes 1466 without it
            ArcaneRiverSkipModule.upgradeBasicSymbols(chr); // the basic one can't be equipped
        } catch (Exception e) {
            log.error("Could not check the basic Arcane Symbol of " + chr.getName(), e);
        }
        try {
            if (net.swordie.ms.client.character.hexa.SixthJobQuest.isOn(chr)
                    && net.swordie.ms.client.character.hexa.SixthJobQuest.classHasQuestline(chr)
                    && !net.swordie.ms.client.character.hexa.HexaModule.hasSixthJob(chr)) {
                net.swordie.ms.client.character.hexa.SixthJobQuest.offer(chr); // earned through the questline
            } else {
                net.swordie.ms.client.character.hexa.HexaModule.unlockSixthJob(chr); // 6th job (HEXA Matrix) at 260
            }
        } catch (Exception e) {
            log.error("Could not unlock the 6th job of " + chr.getName(), e);
        }
        try {
            unlockLegion(chr);
        } catch (Exception e) {
            log.error("Could not unlock the Legion of " + chr.getName(), e);
        }
        try {
            rankUpLegion(chr);
        } catch (Exception e) {
            log.error("Could not rank up the Legion of " + chr.getName(), e);
        }
        try {
            unlockLegionSystems(chr);
        } catch (Exception e) {
            log.error("Could not unlock the Legion Artifact and Champions of " + chr.getName(), e);
        }
        try {
            unlockAchievements(chr);
        } catch (Exception e) {
            log.error("Could not unlock the Maple Achievements of " + chr.getName(), e);
        }
        try {
            completeTutorialQuests(chr);
        } catch (Exception e) {
            log.error("Could not complete the system tutorial quests of " + chr.getName(), e);
        }
        try {
            if (chr.getFamiliarCodexManager() != null) {
                chr.getFamiliarCodexManager().ensureSummonSkill(); // existing characters with familiars
            }
        } catch (Exception e) {
            log.error("Could not give the Summon Familiars skill to " + chr.getName(), e);
        }
        try {
            CerniumStoryModule.check(chr); // the Cernium story at 260, and its Sacred Symbol
        } catch (Exception e) {
            log.error("Could not check the Cernium story of " + chr.getName(), e);
        }
        try {
            chr.topUpPastJobSp(); // job tiers already passed can max their skills
        } catch (Exception e) {
            log.error("Could not check the past job SP of " + chr.getName(), e);
        }
        try {
            JobStarterKit.giveMissingSecondary(chr); // Magic Arrows and the like, if the class has none
        } catch (Exception e) {
            log.error("Could not check the secondary weapon of " + chr.getName(), e);
        }
        try {
            if (chr.getJob() == JobConstants.JobEnum.PINK_BEAN_0.getJobId()) {
                chr.advanceJob(JobConstants.JobEnum.PINK_BEAN_1.getJobId()); // made before Pink Bean started at 13100
            }
        } catch (Exception e) {
            log.error("Could not move the Pink Bean " + chr.getName() + " to job 13100", e);
        }
        try {
            givePinkBeanSkills(chr);
        } catch (Exception e) {
            log.error("Could not give the Pink Bean skills of " + chr.getName(), e);
        }
        try {
            net.swordie.ms.client.jobs.adventurer.Yeti.giveLevelSkills(chr);
        } catch (Exception e) {
            log.error("Could not give the Yeti skills of " + chr.getName(), e);
        }
        try {
            JobPathModule.restoreMissingSp(chr); // before the trim: SP with no skill to take it is removed
            trimSp(chr); // SP saved before a fix to what the skills can take
            topUpThirdJobSp(chr);
        } catch (Exception e) {
            log.error("Could not check the 3rd job SP of " + chr.getName(), e);
        }
    }

    /**
     * Pink Bean's skills and the level each unlocks at (Skill.wz 13100 reqLev); Pink Bean has no SP to spend. Its
     * Beginner job 13000 is skipped at creation, so that job's two shown skills are given here too.
     */
    private static final Supplier<int[][]> PINK_BEAN_SKILLS = DataTable.cached("pink-bean-skills",
            t -> t.rows().stream().filter(r -> r.has("level"))
                    .map(r -> new int[]{r.i("skill"), r.i("level")}).toArray(int[][]::new));
    /** The hidden halves of Pink Bean's attacks: their level is the character's level (max 300). */
    private static final Supplier<int[]> PINK_BEAN_SCALING_SKILLS = DataTable.cached("pink-bean-skills",
            t -> t.rows().stream().filter(r -> !r.has("level")).mapToInt(r -> r.i("skill")).toArray());

    /** Pink Bean learns its skills by level instead of SP: the unlocked ones at level 1, the hidden halves at its level. */
    private static void givePinkBeanSkills(Char chr) {
        if (chr.getJob() != JobConstants.JobEnum.PINK_BEAN_1.getJobId()) {
            return;
        }
        int level = chr.getLevel();
        for (int[] s : PINK_BEAN_SKILLS.get()) {
            if (level >= s[1] && chr.getSkillLevel(s[0]) <= 0) {
                chr.addSkill(s[0], 1, 1);
            }
        }
        int slv = Math.min(300, level);
        for (int id : PINK_BEAN_SCALING_SKILLS.get()) {
            if (chr.getSkillLevel(id) != slv) {
                chr.addSkill(id, slv, 300);
            }
        }
    }

    /**
     * 3rd job gives the same SP to every class, but some classes' 3rd job skills need a point or two more to max
     * (Kanna, Hero). Past level 100 the missing points are added to the 3rd job slot. Skills that take no SP
     * (fixed level, or level shared with another skill) don't count.
     */
    private static void topUpThirdJobSp(Char chr) {
        int job = chr.getJob();
        if (chr.getLevel() <= 100 || !JobConstants.isExtendSpJob(job) || JobConstants.getJobLevel(job) != 4
                || job % 10 != 2 || JobConstants.isDualBlade(job) || JobConstants.isEvan(job)
                || JobConstants.isZero(job)) {
            return;
        }
        int need = 0;
        int have = 0;
        for (Skill skill : SkillData.getSkillsByJob((short) (job - 1))) {
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            if (si == null || si.getHyper() > 0 || si.getHyperStat() > 0 || si.getFixLevel() > 0
                    || SkillData.isSpLocked(si.getSkillId())) {
                continue;
            }
            need += si.getMaxLevel();
            Skill learned = chr.getSkill(skill.getSkillId());
            if (learned != null) {
                have += Math.max(0, learned.getCurrentLevel());
            }
        }
        int given = JobConstants.getJobAdvancementSpByTier(job, 3);
        for (int lv = 61; lv <= 100; lv++) {
            given += JobPathModule.getLevelSp(lv);
        }
        var esp = chr.getAvatarData().getCharacterStat().getExtendSP();
        int unspent = Math.max(0, esp.getSpByJobLevel((byte) 3));
        int missing = Math.min(need - have - unspent, need - given);
        if (missing <= 0) {
            return;
        }
        esp.setSpToJobLevel(3, unspent + missing);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.sp, esp);
        chr.write(WvsContext.statChanged(stats));
        chr.chatMessage(ChatType.SystemNotice, String.format("You received %d 3rd job SP your class was missing.",
                missing));
        log.info(String.format("[SP] %s got %d missing 3rd job SP", chr.getName(), missing));
    }

    private static void trimSp(Char chr) {
        if (!chr.trimUnusableSp()) {
            return;
        }
        var cs = chr.getAvatarData().getCharacterStat();
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.sp, JobConstants.isExtendSpJob(chr.getJob()) ? cs.getExtendSP() : (short) cs.getSp());
        chr.write(WvsContext.statChanged(stats));
        log.info(String.format("[SP] %s: SP no skill could take was removed", chr.getName()));
    }

    private static void completeQuietly(Char chr, int questId) {
        var qm = chr.getQuestManager();
        if (!qm.hasQuestCompleted(questId)) {
            qm.completeQuest(questId, false);
        }
    }

    private static void unlockInnerAbility(Char chr) {
        if (chr.getLevel() < INNER_ABILITY_LEVEL) {
            return;
        }
        CharacterPotentialMan cpm = chr.getPotentialMan();
        boolean added = false;
        for (byte key = 1; key <= 3; key++) {
            if (cpm.getPotentialByKey(key) == null) {
                cpm.addPotential(cpm.generateRandomPotential(key));
                added = true;
            }
        }
        for (int q : INNER_ABILITY_QUESTS) {
            completeQuietly(chr, q);
        }
        if (added) {
            chr.chatMessage(ChatType.SystemNotice, "Your Inner Ability (Hidden Abilities) is unlocked! Open the "
                    + "Ability window (Stats > Ability) to see it and reset it with Honor EXP.");
        }
    }

    /**
     * A character with the 5th job but none of its job's starting V skills gets them (e.g. one that reached level 200
     * before its 4th job, when the list was looked up for the wrong job).
     */
    private static void giveMissingVSkills(Char chr) {
        if (chr.getLevel() < FIFTH_JOB_LEVEL || chr.getJobHandler() == null
                || !chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            return;
        }
        var start = JobConstants.GetVSkillsToGiveUponReachingV(chr.getJob());
        if (start == null || start.isEmpty()) {
            return;
        }
        boolean hasAny = chr.getMatrixRecords().stream()
                .anyMatch(mr -> start.contains(mr.getSkillID1()));
        if (!hasAny) {
            log.info("Giving the starting V skills of job " + chr.getJob() + " to " + chr.getName());
            chr.getJobHandler().GiveVSkills(chr);
        }
    }

    private static void unlockFifthJob(Char chr) {
        int job = chr.getJob();
        if (chr.getLevel() < FIFTH_JOB_LEVEL || JobConstants.isBeginnerJob(job)
                || chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            return;
        }
        if (ServerFeatures.on(ServerFeatures.Feature.JOB_ADVANCEMENT_BY_QUEST_ONLY) && !JobPathModule.isManaged(chr)) {
            return; // the official 5th job questline (quests 1460-1466); only @job paths get it automatically
        }
        for (int q : FIFTH_JOB_QUESTS) {
            completeQuietly(chr, q);
        }
        if (chr.getJobHandler() != null) {
            chr.getJobHandler().GiveVSkills(chr);
        }
        chr.chatMessage(ChatType.SystemNotice, "You've reached the 5th Job! The V Matrix and Arcane Symbols are "
                + "unlocked, and your first 5th job skills are in the V Matrix.");
    }

    private static void unlockLegion(Char chr) {
        var qm = chr.getQuestManager();
        var union = chr.getUnion();
        if (union == null || qm.hasQuestCompleted(QuestConstants.UNION_START_QUEST)) {
            return;
        }
        if (union.getUnionLevel() < LEGION_LEVEL || union.getEligibleUnionChars().size() < LEGION_CHARACTERS) {
            return;
        }
        if (union.getUnionRank() == 0) {
            union.setUnionRank(101);
        }
        qm.initUnion(); // the Legion's quest records, and completes the Legion start quest
        qm.checkAndAddCompletedAccountQuest(QuestConstants.UNION_START_QUEST);
        union.updateUnionBonuses(chr);
        chr.chatMessage(ChatType.SystemNotice, "Your Legion is founded! Open the Legion window to place your "
                + "characters on the board.");
    }

    /** v271 ranks the Legion up by itself once the Legion level reaches the next rank (mapleUnion rankUpMessage). */
    private static void rankUpLegion(Char chr) {
        var union = chr.getUnion();
        if (union == null || union.getUnionRank() <= 0
                || !chr.getQuestManager().hasQuestCompleted(QuestConstants.UNION_START_QUEST)) {
            return;
        }
        int level = union.getUnionLevel();
        int before = union.getUnionRank();
        for (int i = 0; i < 25 && !union.isMaxRank(); i++) {
            int req = union.getRankUpLevelReq();
            if (req <= 0 || level < req || union.getUnionCoin() < union.getRankUpCoinReq()) {
                break;
            }
            if (union.getRankUpCoinReq() > 0) {
                union.addUnionCoin(-union.getRankUpCoinReq());
            }
            chr.incrementUnionRank();
        }
        if (union.getUnionRank() != before) {
            chr.chatMessage(ChatType.SystemNotice, String.format("Your Legion grows! It is now %s (max Legion "
                    + "members %d).", union.getUnionRankName(), union.getAttackerCount()));
        }
    }

    /** @return whether the quest was completed now (no rewards, effects or NPC talk) */
    private static boolean completeSilently(Char chr, int questId) {
        var qm = chr.getQuestManager();
        return !qm.hasQuestCompleted(questId) && qm.completeQuestSilently(questId);
    }

    private static void unlockLegionSystems(Char chr) {
        if (chr.getLevel() >= LEGION_INTRO_LEVEL) {
            completeSilently(chr, LEGION_INTRO_QUEST);
        }
        if (!chr.getQuestManager().hasQuestCompleted(QuestConstants.UNION_START_QUEST)) {
            return;
        }
        if (completeSilently(chr, LEGION_ARTIFACT_QUEST)) {
            UnionArtifactModule.onLogin(chr);
            chr.chatMessage(ChatType.SystemNotice, "The Legion Artifact is unlocked! Open it from the Legion "
                    + "window; its missions power up the Artifact's crystals.");
        }
        if (completeSilently(chr, ChampionModule.PATH_QUEST)) {
            ChampionModule.sendRecords(chr);
            chr.chatMessage(ChatType.SystemNotice, "Legion Champions are unlocked! Register your Champions in the "
                    + "Champion tab of the Legion window; Sir Hamselot explains the Champion Trials.");
        }
    }

    private static void unlockAchievements(Char chr) {
        if (chr.getLevel() < ACHIEVEMENTS_LEVEL || chr.getAccount() == null
                || (ServerHooks.isAutomated(chr) && !ServerHooks.isTestCharacter(chr))) {
            return;
        }
        completeSilently(chr, ACHIEVEMENTS_QUEST);
        if (MapleAchievements.isComplete(chr, ACHIEVEMENTS_OPEN)) {
            return;
        }
        // what opening the window does: completes "Begin Maple Achievements" and sends the records
        MapleAchievements.handleRequest(chr, MapleAchievements.REQ_OPEN, null);
        if (MapleAchievements.isComplete(chr, ACHIEVEMENTS_OPEN)) {
            chr.write(WvsContext.message(net.swordie.ms.connection.packet.model.MessagePacket.worldShareRecordMessage(
                    ACHIEVEMENTS_WSR, ACHIEVEMENTS_WSR_VALUE)));
            chr.chatMessage(ChatType.SystemNotice, "Maple Achievements are unlocked! Open them from the Menu.");
        }
    }

    /** Tutorial quests of systems that already work without them, so they don't sit in the quest list. */
    private static void completeTutorialQuests(Char chr) {
        var qm = chr.getQuestManager();
        int job = chr.getJob();
        int level = chr.getLevel();
        if (JobConstants.isZero(job)) {
            if (level >= ZERO_LINK_SKILL_LEVEL && qm.hasQuestCompleted(ZERO_LINK_SKILL_PREQ)
                    && completeSilently(chr, ZERO_LINK_SKILL_QUEST)) {
                chr.giveLinkSkillToAccount();
            }
        } else if (level >= LINK_SKILL_LEVEL && !JobConstants.isBeginnerJob(job)
                && completeSilently(chr, LINK_SKILL_QUEST)) {
            chr.giveLinkSkillToAccount();
        }
        if (level >= FIFTH_JOB_LEVEL && qm.hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            completeSilently(chr, V_MATRIX_QUEST);
        }
    }
}

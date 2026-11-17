package net.swordie.ms.client.character.hyperstats;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.loaders.SkillData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 01/02/2022.
 *
 * @author Asura
 */
public class HyperStatsModule {

    private static final Logger log = LogManager.getLogger();

    /** Raises a Hyper Stat one level at a time up to targetLevel (the window's Max button asks for several). */
    public static void increaseHyperStatSkill(Char chr, int presetIndex, int skillId, int targetLevel) {
        var skill = chr.getSkill(skillId, true);
        int steps = skill == null ? 1 : Math.max(1, Math.min(targetLevel, skill.getMaxLevel()) - skill.getCurrentLevel());
        for (int i = 0; i < steps; i++) {
            if (!increaseHyperStatSkill(chr, presetIndex, skillId)) {
                return;
            }
        }
    }

    public static boolean increaseHyperStatSkill(Char chr, int presetIndex, int skillId) {
        var si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            log.error(String.format("Character %d attempted assigning hyper SP to a skill with null skillinfo (%d).", chr.getId(), skillId));
            chr.dispose();
            return false;
        }
        if (!SkillConstants.isHyperstatSkill(skillId)) {
            log.error(String.format("Character %d attempted assigning hyper stat SP to a wrong skill (skill id %d, player job %d)", chr.getId(), skillId, chr.getJob()));
            chr.dispose();
            return false;
        }
        if (si.getHyperStat() == 0) {
            chr.getOffenseManager().addOffense(
                    String.format("Character %d attempted assigning hyper stat to an improper skill. (%d, job %d)",
                            chr.getId(), skillId, chr.getJob()));
            chr.dispose();
            return false;
        }
        if (skillId == Job.HYPER_STAT_ARCANE_FORCE && !chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            chr.getOffenseManager().addOffense("Tried adding to AF hyper stat without having the proper quest completed.");
            chr.dispose();
            return false;
        }

        var hsm = chr.getHyperStatsManager();
        var skill = chr.getSkill(skillId, true);
        var level = chr.getLevel();

        var totalHyperSp = getTotalHyperStatSp(chr); // includes the points from earlier lives (rebirth)
        var spentSp = chr.getSpentHyperSp();
        var availableSp = totalHyperSp - spentSp;

        var neededSp = SkillConstants.getNeededSpForHyperStatSkill(skill.getCurrentLevel() + 1);

        if (skill.getCurrentLevel() >= skill.getMaxLevel() || availableSp < neededSp) {
            chr.getOffenseManager().addOffense(
                    String.format("Character %d attempted assigning too many hyper stat levels. Available SP %d, " +
                                    "needed %d, current %d (%d, job %d)", chr.getId(), availableSp, neededSp,
                            skill.getCurrentLevel(), skillId, chr.getJob()));
            chr.dispose();
            return false;
        }
        var newSkill = SkillData.getSkillDeepCopy(skill);
        newSkill.setCurrentLevel(skill.getCurrentLevel() + 1);
        chr.addSkill(newSkill);
        addHyperStatToPreset(chr, hsm, newSkill, presetIndex);
        chr.write(WvsContext.changeSkillRecordResult(newSkill));

        if (skillId == Job.HYPER_STAT_ARCANE_FORCE) {
            chr.getJobHandler().giveHyperAfBuff();
        }
        return true;
    }

    /**
     * All the Hyper Stat points a character has: this life's from Lv. 140, plus (from Lv. 140) the ones a reborn
     * character brought from its earlier lives (RebirthModule#getCarriedHyperStatSp).
     */
    public static int getTotalHyperStatSp(Char chr) {
        int level = chr.getLevel();
        int total = SkillConstants.getTotalHyperStatSpByLevel((short) level);
        long withCarry = level >= 140
                ? (long) total + net.swordie.ms.client.character.RebirthModule.getCarriedHyperStatSp(chr) : total;
        return (int) Math.min(Integer.MAX_VALUE, withCarry);
    }

    /**
     * The Hyper Stat points the client is told a level gives ("incHyperStat"; the Hyper Stat window adds them up from
     * Lv. 140). A reborn character's points from earlier lives come with Lv. 140's.
     */
    public static int getHyperStatSpForLevel(Char chr, int level) {
        if (level < 140) {
            return 0;
        }
        int sp = SkillConstants.getHyperStatSpByLv((short) level);
        if (level == 140) {
            long withCarry = (long) sp + net.swordie.ms.client.character.RebirthModule.getCarriedHyperStatSp(chr);
            sp = (int) Math.min(Integer.MAX_VALUE, withCarry);
        }
        return sp;
    }

    /** Replace the point table cached by the client. Preset data is sent by reapplyCurrentPreset when it changes. */
    public static void refreshPointDisplay(Char chr) {
        chr.write(WvsContext.resultInstanceTable("incHyperStat", 140, 0, true,
                getHyperStatSpForLevel(chr, 140)));
    }

    private static void addHyperStatToPreset(Char chr, HyperStatsManager hsm, Skill hyperStatSkill, int presetIndex) {
        var info = HyperStatInfo.fromSkill(hyperStatSkill, presetIndex);
        hsm.addHyperStatInfoToPreset(info);
        chr.write(WvsContext.hyperStatSkillUpdateResult(hsm, true));
    }


    public static void switchPreset(Char chr, int presentIndex) {
        var hsm = chr.getHyperStatsManager();

        resetHyperStats(chr);

        hsm.setCurrentPreset(presentIndex);
        chr.write(WvsContext.hyperStatSkillUpdateResult(hsm, true));
        giveHyperStatsByPreset(chr, presentIndex);
    }

    /** Reapplies the saved active preset after rebirth or login so its skill levels and stat cache stay in sync. */
    public static void reapplyCurrentPreset(Char chr) {
        var hsm = chr.getHyperStatsManager();
        if (hsm == null) {
            return;
        }
        int presetIndex = hsm.getCurrentPreset();
        if (presetIndex < 0 || presetIndex >= net.swordie.ms.constants.GameConstants.HYPER_STATS_MAX_PRESETS
                || hsm.getHyperStatSkillsByPreset(presetIndex).isEmpty()) {
            return;
        }

        resetHyperStats(chr);
        giveHyperStatsByPreset(chr, presetIndex);
        chr.write(WvsContext.hyperStatSkillUpdateResult(hsm, true));
    }

    /**
     * Raises any Hyper Stat below its level in the active preset back to it; nothing is lowered or removed.
     *
     * @return how many were restored
     */
    public static int restoreFromCurrentPreset(Char chr) {
        var hsm = chr.getHyperStatsManager();
        if (hsm == null) {
            return 0;
        }
        int presetIndex = hsm.getCurrentPreset();
        if (presetIndex < 0 || presetIndex >= net.swordie.ms.constants.GameConstants.HYPER_STATS_MAX_PRESETS) {
            return 0;
        }
        var skills = new ArrayList<Skill>();
        for (var info : hsm.getHyperStatSkillsByPreset(presetIndex)) {
            if (!SkillConstants.isHyperstatSkill(info.getSkillId()) || info.getLevel() <= 0) {
                continue;
            }
            Skill current = chr.getSkill(info.getSkillId());
            if (current != null && current.getCurrentLevel() >= info.getLevel()) {
                continue;
            }
            Skill skill = chr.getSkill(info.getSkillId(), true);
            if (skill == null) {
                continue;
            }
            skill.setCurrentLevel(info.getLevel());
            chr.addSkill(skill);
            skills.add(skill);
        }
        if (!skills.isEmpty()) {
            chr.write(WvsContext.changeSkillRecordResult(skills, true, false, false, false));
            log.info(String.format("[HyperStats] %s: restored %d Hyper Stat(s) from preset %d", chr.getName(),
                    skills.size(), presetIndex));
        }
        return skills.size();
    }

    public static void resetPreset(Char chr, int presetIndex) {
        var hsm = chr.getHyperStatsManager();

        hsm.resetPreset(presetIndex);
        resetHyperStats(chr);
    }

    private static void resetHyperStats(Char chr) {
        List<Skill> skills = new ArrayList<>();
        for (int skillId : SkillConstants.HYPER_STAT_SKILLS) {
            Skill skill = chr.getSkill(skillId);
            if (skill != null) {
                chr.removeSkill(skillId);
                skill.setCurrentLevel(0);
                skills.add(skill);
            }
        }
        chr.write(WvsContext.changeSkillRecordResult(skills, true, false, false, false));
    }

    private static void giveHyperStatsByPreset(Char chr, int presetIndex) {
        var hsm = chr.getHyperStatsManager();
        var infos = hsm.getHyperStatSkillsByPreset(presetIndex);
        var skills = new ArrayList<Skill>();
        for (var info : infos) {
            var skill = chr.getSkill(info.getSkillId(), true);
            skill.setCurrentLevel(info.getLevel());
            chr.addSkill(skill);

            skills.add(skill);
        }
        if (skills.size() > 0) {
            chr.write(WvsContext.changeSkillRecordResult(skills, true, false, false, false));
        }
    }
}

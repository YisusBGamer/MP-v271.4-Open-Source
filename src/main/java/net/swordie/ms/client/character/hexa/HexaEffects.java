package net.swordie.ms.client.character.hexa;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.HexaConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.loaders.HexaCoreData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * What the HEXA Matrix gives besides its skills: the HEXA Stat bonuses in the stat calculation, the boost cores' extra
 * damage on 5th job skills, and Sol Erda energy from Grandis dailies and monsters.
 */
public final class HexaEffects {

    // HEXA Stat types, as the data and the client number them
    private static final int CRIT_DAMAGE = 0;
    private static final int BOSS_DAMAGE = 1;
    private static final int IGNORE_DEFENSE = 2;
    private static final int DAMAGE = 3;
    private static final int ATT = 4;
    private static final int MATT = 5;
    private static final int MAIN_STAT = 6;

    /** indieStat is in hundreds of main stat (lv 10 main = +2000) */
    private static final int MAIN_STAT_UNIT = 100;
    /** Xenon gets 48% of it on each of STR, DEX and LUK, Demon Avenger 21 HP per point, as with Arcane Symbols */
    private static final double XENON_SHARE = 0.48;
    private static final int DEMON_AVENGER_HP = 21;

    /** Sol Erda energy per completed Grandis daily tier */
    public static final int GRANDIS_DAILY_ENERGY = 50;
    /** defaults of the settings, per 10,000 before the drop rate: Faint Sol Erda Energy and Sol Erda Fragments */
    public static final int FAINT_ENERGY_DROP_CHANCE = 500; // hardcode-ok
    public static final int FRAGMENT_DROP_CHANCE = 30; // hardcode-ok
    public static final int FAINT_ENERGY_ITEM = 2636420;
    public static final int FRAGMENT_ITEM = 4009547;

    private HexaEffects() {
    }

    private static boolean hasStats(Char chr) {
        // below 260 nothing can be active, and the nodes aren't even read from the database
        return chr.getLevel() >= HexaConstants.SIXTH_JOB_LEVEL && !chr.getHexaStats().isEmpty();
    }

    /** @return the sum of one HEXA Stat type over all nodes, in the data's units */
    private static int sum(Char chr, int type) {
        int total = 0;
        for (HexaStat stat : chr.getHexaStats()) {
            for (int i = 0; i < 3; i++) {
                if (stat.getType(i) == type) {
                    total += HexaCoreData.getStatValue(type, stat.getLevel(i), i == 0);
                }
            }
        }
        return total;
    }

    /** HEXA Stat bonuses that ATT % and the like raise: crit damage, boss damage, damage, ATT and Magic ATT */
    public static double getStat(Char chr, BaseStat baseStat) {
        if (!hasStats(chr)) {
            return 0;
        }
        return switch (baseStat) {
            case crDmg -> sum(chr, CRIT_DAMAGE) / 100D;
            case bd -> sum(chr, BOSS_DAMAGE) / 100D;
            case damR -> sum(chr, DAMAGE) / 100D;
            case pad -> sum(chr, ATT);
            case mad -> sum(chr, MATT);
            default -> 0;
        };
    }

    /** The HEXA main stat: flat, not raised by % (like Arcane Symbols) */
    public static double getMainStat(Char chr, BaseStat baseStat) {
        if (!hasStats(chr) || !(baseStat == BaseStat.str || baseStat == BaseStat.dex || baseStat == BaseStat.inte
                || baseStat == BaseStat.luk || baseStat == BaseStat.mhp)) {
            return 0;
        }
        int job = chr.getJob();
        if (JobConstants.isXenon(job)) {
            return baseStat == BaseStat.str || baseStat == BaseStat.dex || baseStat == BaseStat.luk
                    ? Math.floor(sum(chr, MAIN_STAT) * MAIN_STAT_UNIT * XENON_SHARE) : 0;
        }
        if (JobConstants.isDemonAvenger(job)) {
            return baseStat == BaseStat.mhp ? (double) sum(chr, MAIN_STAT) * MAIN_STAT_UNIT * DEMON_AVENGER_HP : 0;
        }
        return baseStat == JobConstants.getMainStatByJob(job) ? sum(chr, MAIN_STAT) * MAIN_STAT_UNIT : 0;
    }

    /** HEXA ignore defense, one entry per stat line (IED stacks multiplicatively) */
    public static List<Integer> getIgnoreDefense(Char chr) {
        List<Integer> res = new ArrayList<>();
        if (!hasStats(chr)) {
            return res;
        }
        for (HexaStat stat : chr.getHexaStats()) {
            for (int i = 0; i < 3; i++) {
                if (stat.getType(i) == IGNORE_DEFENSE && stat.getLevel(i) > 0) {
                    res.add(HexaCoreData.getStatValue(IGNORE_DEFENSE, stat.getLevel(i), i == 0) / 100);
                }
            }
        }
        return res;
    }

    // ------------------------------------------------------------------------------------------------- boost cores

    /**
     * @return the multiplier the current job's boost cores put on a 5th job skill's damage (1 if none), the same way
     * V Matrix Boost Nodes raise 4th job skills
     */
    public static double getBoostMultiplier(Char chr, int skillId) {
        if (skillId <= 0 || chr.getLevel() < HexaConstants.SIXTH_JOB_LEVEL) {
            return 1;
        }
        double mult = net.swordie.ms.client.character.erdalink.ErdaLinkEffects.getBoostMultiplier(chr, skillId);
        int job = chr.getJob();
        for (HexaSkill hs : chr.getHexaSkills()) {
            if (hs.getLevel() <= 0 || hs.getCoreId() / 10_000_000 != HexaCoreData.BOOST_CORE
                    || !HexaCoreData.isJobCore(job, hs.getCoreId())) {
                continue;
            }
            HexaCoreData.SkillCore core = HexaCoreData.getSkillCore(hs.getCoreId());
            if (core == null) {
                continue;
            }
            for (int boostSkill : core.connectSkills) {
                Set<Integer> targets = HexaCoreData.getBoostTargets(boostSkill);
                if (targets.contains(skillId)) {
                    mult *= 1 + HexaCoreData.getBoostDamR(boostSkill, Math.min(hs.getLevel(), core.maxLevel)) / 100D;
                }
            }
        }
        return mult;
    }

    // ------------------------------------------------------------------------------------------------- Sol Erda

    private static double getRate() {
        return ServerRates.getSolErdaRate();
    }

    /** A Grandis daily tier (not the region's start quest) handed in at the 6th job gives Sol Erda energy */
    public static void onQuestCompleted(Char chr, int questId) {
        if (questId < QuestConstants.GRANDIS_DAILY_QUEST_FIRST || questId > QuestConstants.GRANDIS_DAILY_QUEST_LAST
                || (questId - QuestConstants.GRANDIS_DAILY_QUEST_FIRST) % 4 == 0 || !HexaModule.hasSixthJob(chr)) {
            return;
        }
        int energy = (int) Math.round(GRANDIS_DAILY_ENERGY * getRate());
        if (energy > 0) {
            HexaModule.addSolErdaEnergy(chr, energy);
        }
    }

    private static boolean isGrandisField(int fieldId) {
        return fieldId / 1_000_000 == 410;
    }

    /**
     * Lv. 260+ monsters in Grandis drop Faint Sol Erda Energy (picked up straight into the gauge) and now and then a
     * Sol Erda Fragment, for a killer who has the 6th job. Both go through the drop rate; the energy also through the
     * Sol Erda rate.
     */
    public static void addMobDrops(Char killer, int fieldId, int mobLevel, boolean boss, Set<DropInfo> drops) {
        if (killer == null || boss || mobLevel < HexaConstants.SIXTH_JOB_LEVEL || !isGrandisField(fieldId)
                || killer.getLevel() < HexaConstants.SIXTH_JOB_LEVEL || !HexaModule.hasSixthJob(killer)) {
            return;
        }
        int energyChance = (int) Math.round(ServerFeatures.getInt(ServerFeatures.Feature.SOL_ERDA_ENERGY_CHANCE)
                * getRate());
        if (energyChance > 0) {
            drops.add(new DropInfo(FAINT_ENERGY_ITEM, energyChance));
        }
        int fragmentChance = ServerFeatures.getInt(ServerFeatures.Feature.SOL_ERDA_FRAGMENT_CHANCE);
        if (fragmentChance > 0) {
            drops.add(new DropInfo(FRAGMENT_ITEM, (int) Math.max(1, Math.round(fragmentChance * getRate()))));
        }
    }
}

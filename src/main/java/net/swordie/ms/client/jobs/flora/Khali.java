package net.swordie.ms.client.jobs.flora;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.SkillBuffs;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.life.Summon;
import net.swordie.ms.util.Util;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;

import static net.swordie.ms.client.character.skills.SkillStat.cooltime;
import static net.swordie.ms.client.character.skills.SkillStat.time;
import static net.swordie.ms.client.character.skills.SkillStat.u;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.DarkSight;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.SummonChakri;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ShadowAcceleration;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndieNotDamaged;
import net.swordie.ms.client.Client;

/**
 * Khali (Flora thief, Chakram). Job ids 15003, 15400-15412.
 * Attacks use the generic handling in {@link Job}; the buffs below follow Skill.wz (the v265.3 reference tree has no
 * class mechanics for it).
 */
public class Khali extends Job {

    public static final int CHAKRAM_BOOSTER = 154101005;
    public static final int DESERT_VEIL = 154121004; // invincible for time
    public static final int FLORAN_HEROS_WILL = 154121006;
    public static final int DIVINE_WRATH = 154121042; // indieDamR
    public static final int HEX_SANDSTORM = 154141500;
    public static final int HEX_SANDSTORM_BUFF = 154141502; // indiePMdR after Sandstorm
    public static final int DEATH_BLOSSOM = 154121041; // rose summon
    public static final int HEXA_DEATH_BLOSSOM = 154141012;
    public static final int OBLIVION = 154121043; // resets Void Rush/Blitz, Hex cooldowns -u% for time
    public static final int ARTS_CROSS_CUT = 154001000;
    public static final int ARTS_DUAL_EDGE = 154101000;
    public static final int ARTS_CRESCENTUM = 154101001;
    public static final int ARTS_CRESCENTUM_2 = 154101002;
    public static final int ARTS_TRIPLE_BASH = 154111002;
    public static final int VOID_ENHANCE = 154111007;
    public static final int ARTS_FLURRY = 154121000;
    public static final int HEX_CHAKRAM_SPLIT = 154121001;
    public static final int VOID_BLITZ = 154121011;
    public static final int VOID_RUSH = 154121012;
    public static final int HEXA_ARTS_FLURRY = 154141000;
    public static final int HEXA_ARTS_CRESCENTUM = 154141001;
    public static final int HEXA_ARTS_CRESCENTUM_2 = 154141002;
    public static final int HEXA_VOID_RUSH = 154141003;
    public static final int HEXA_VOID_RUSH_2 = 154141004;
    public static final int HEXA_VOID_BLITZ = 154141007;
    public static final int HEXA_VOID_BLITZ_2 = 154141008;
    public static final int HEXA_HEX_CHAKRAM_SPLIT = 154141009;

    /** Hex skills of the 4th job or below whose cooldown Oblivion cuts (its skillList2 without HEXA). */
    private static final java.util.Set<Integer> OBLIVION_HEX = java.util.Set.of(154111006, 154121001, 154121002);
    /** Void Rush and Void Blitz (Oblivion's skillList). */
    private static final int[] OBLIVION_RESET = {154001003, 154101004, 154111004, 154111011, 154121003, 154121009,
            VOID_BLITZ, VOID_RUSH, HEXA_VOID_RUSH, HEXA_VOID_RUSH_2, HEXA_VOID_BLITZ, HEXA_VOID_BLITZ_2};

    private long oblivionEnd;

    /** Arts skills whose hits cut the Void Rush/Void Blitz cooldown by u sec; value = the id that holds u. */
    private static final java.util.Map<Integer, Integer> ARTS_CD_CUT = java.util.Map.of(
            ARTS_CROSS_CUT, ARTS_CROSS_CUT, ARTS_DUAL_EDGE, ARTS_DUAL_EDGE, ARTS_CRESCENTUM, ARTS_CRESCENTUM, ARTS_CRESCENTUM_2, ARTS_CRESCENTUM,
            ARTS_TRIPLE_BASH, ARTS_TRIPLE_BASH, ARTS_FLURRY, ARTS_FLURRY, HEXA_ARTS_FLURRY, HEXA_ARTS_FLURRY, HEXA_ARTS_CRESCENTUM, HEXA_ARTS_CRESCENTUM,
            HEXA_ARTS_CRESCENTUM_2, HEXA_ARTS_CRESCENTUM);
    public static final int VOID_BURST_FINISH = 400041086;
    /** Void Burst's invincibility after the finishing attack (3.5 sec in the skill text). */
    private static final int VOID_BURST_FINISH_GUARD_MS = 3500;

    /** Void Rush's Dark Sight (invisible, time 2) */
    public static final int VOID_RUSH_DARK_SIGHT = 154101009;
    public static final int SUMMON_CHAKRI = 154111000; // on/off toggle
    public static final int INSANITY_I = 154100003; // dashes; Dark Sight for time after
    public static final int INSANITY_II = 154110003;
    public static final int INSANITY_III = 154120008;
    public static final int DECEIVING_BLADE = 154110005;
    public static final int HEXA_DECEIVING_BLADE = 154141014;

    // V skills
    public static final int HEX_PANDEMONIUM = 400041082;
    public static final int VOID_BURST = 400041084;
    public static final int ARTS_ASTRA = 400041087;
    public static final int RESONATE_ULTIMATUM = 400041089;

    public Khali(Char chr) {
        super(chr);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isKhali(id);
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        switch (skillId) {
            case CHAKRAM_BOOSTER -> SkillBuffs.giveBooster(chr, skillId, slv);
            case DIVINE_WRATH -> SkillBuffs.giveIndieBuff(chr, skillId, slv);
            case HEX_SANDSTORM -> SkillBuffs.giveIndieBuff(chr, HEX_SANDSTORM_BUFF, slv);
            case FLORAN_HEROS_WILL -> tsm.removeAllDebuffs();
            case SUMMON_CHAKRI -> {
                // on/off: the client places the chakri itself (its attack code builds them while the stat is on)
                if (tsm.hasStat(SummonChakri)) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    tsm.putCharacterStatValue(SummonChakri, new Option(1, skillId, 0));
                    tsm.sendSetStatPacket();
                }
            }
            case VOID_ENHANCE -> { // Void Enhance: on/off, longer Void Rush (the client reads ShadowAcceleration's n)
                if (tsm.hasStat(ShadowAcceleration)) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    tsm.putCharacterStatValue(ShadowAcceleration, new Option(1, skillId, 0));
                    tsm.sendSetStatPacket();
                }
            }
            case DEATH_BLOSSOM, HEXA_DEATH_BLOSSOM -> {
                Summon rose = Summon.getSummonBy(chr, skillId, slv);
                rose.setMoveAbility(MoveAbility.Stop);
                chr.getField().spawnSummonAndRemoveOld(rose);
            }
            case OBLIVION -> {
                for (int id : OBLIVION_RESET) {
                    if (chr.hasSkill(id)) {
                        chr.resetSkillCoolTime(id);
                    }
                }
                oblivionEnd = Util.getCurrentTimeLong() + si.getValue(time, slv) * 1000L;
            }
            case RESONATE_ULTIMATUM -> {
                // the client makes the Awakened Chakri and Resonates while the stat is on
                tsm.putCharacterStatValue(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.KhaliUltimatum,
                        new Option(1, skillId, si.getValue(time, slv)));
                tsm.sendSetStatPacket();
            }
            case VOID_BURST -> voidBurstGuard(si.getValue(time, slv)); // Wind of Vengeance: invincible (time in ms)
            case DESERT_VEIL -> {
                if (tsm.hasAOptByCTSAndSkill(IndieNotDamaged, skillId)) {
                    tsm.removeStatsBySkill(skillId); // used again: the veil is cancelled
                } else {
                    tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, skillId, si.getValue(time, slv)), true);
                    tsm.sendSetStatPacket();
                }
            }
        }
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        // Void Rush / Void Blitz: hidden (Dark Sight) for a moment after the dash
        int id = attackInfo.skillId;
        boolean insanity = id == INSANITY_I || id == INSANITY_II || id == INSANITY_III;
        if (insanity || java.util.Arrays.stream(OBLIVION_RESET).anyMatch(v -> v == id)) {
            SkillInfo dsi = SkillData.getSkillInfoById(VOID_RUSH_DARK_SIGHT);
            int dur = dsi == null ? 0 : dsi.getValue(time, 1);
            if (dur > 0) {
                TemporaryStatManager tsm = chr.getTemporaryStatManager();
                tsm.putCharacterStatValue(DarkSight, new Option(1, VOID_RUSH_DARK_SIGHT, dur));
                tsm.sendSetStatPacket();
            }
            // Deceiving Blade: a Void-type hit starts the buff whose extra hits the client makes
            int blade = chr.hasSkill(HEXA_DECEIVING_BLADE) ? HEXA_DECEIVING_BLADE : DECEIVING_BLADE;
            if (!attackInfo.mobAttackInfo.isEmpty() && chr.hasSkill(blade)) {
                SkillInfo bsi = SkillData.getSkillInfoById(blade);
                TemporaryStatManager tsm = chr.getTemporaryStatManager();
                tsm.putCharacterStatValue(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.DeceivingBlade,
                        new Option(1, blade, bsi.getValue(time, chr.getSkillLevel(blade))));
                tsm.sendSetStatPacket();
            }
        }
        // Arts: each hit cuts the Void Rush/Void Blitz cooldown by u sec
        Integer uFrom = ARTS_CD_CUT.get(id);
        if (uFrom != null && !attackInfo.mobAttackInfo.isEmpty()) {
            SkillInfo asi = SkillData.getSkillInfoById(uFrom);
            int lv = chr.getSkillLevel(uFrom);
            int cut = asi == null || lv <= 0 ? 0 : asi.getValue(u, lv);
            if (cut > 0) {
                java.util.List<Integer> onCd = java.util.Arrays.stream(OBLIVION_RESET)
                        .filter(v -> chr.getRemainingCoolTime(v) > 0).boxed().toList();
                if (!onCd.isEmpty()) {
                    chr.reduceSkillCoolTime(onCd, cut * 1000);
                }
            }
        }
        if (id == VOID_BURST && !chr.getTemporaryStatManager().hasAOptByCTSAndSkill(IndieNotDamaged, VOID_BURST)) {
            SkillInfo vsi = SkillData.getSkillInfoById(VOID_BURST);
            int lv = chr.getSkillLevel(VOID_BURST);
            if (vsi != null && lv > 0) {
                voidBurstGuard(vsi.getValue(time, lv));
            }
        } else if (id == VOID_BURST_FINISH) {
            voidBurstGuard(VOID_BURST_FINISH_GUARD_MS);
        }
        super.handleAttack(c, attackInfo);
    }

    private void voidBurstGuard(int ms) {
        if (ms <= 0) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option(1, VOID_BURST, ms);
        o.setInMillis(true);
        tsm.putCharacterStatValue(IndieNotDamaged, o, true);
        tsm.sendSetStatPacket();
    }

    @Override
    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        if (OBLIVION_HEX.contains(skillId) && Util.getCurrentTimeLong() < oblivionEnd) {
            SkillInfo si = SkillData.getSkillInfoById(skillId);
            SkillInfo oblivion = SkillData.getSkillInfoById(OBLIVION);
            if (si != null && oblivion != null) {
                int cd = si.getValue(cooltime, slv) * 1000;
                return cd * (100 - oblivion.getValue(u, chr.getSkillLevel(OBLIVION))) / 100;
            }
        }
        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        setFirstJobCreationStats(chr, JobEnum.KHALI_1.getJobId(), 4, 4, 4, 25);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        advanceByLevel(JobEnum.KHALI_1.getJobId(), JobEnum.KHALI_2.getJobId(), JobEnum.KHALI_3.getJobId(),
                JobEnum.KHALI_4.getJobId());
    }
}

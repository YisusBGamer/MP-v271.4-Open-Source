package net.swordie.ms.client.jobs.jianghu;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;

import java.util.concurrent.ScheduledFuture;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Mo Xuan (Jianghu pirate, Martial Brace). Job ids 17000, 17500-17512.
 * Mechanics from the v265.3 reference tree's Mo Xuan: the Tian/Di/Divine gauge and Heir of the Divine, Swirling Tide,
 * Arrow Flight charges, Qi Shield, Qi Projection, the invincibility windows and Awaken's regeneration.
 */
public class MoXuan extends Job {

    public static final int XUANSHAN_SPIRIT = 170000001;
    public static final int WILL_OF_THE_ALLIANCE = 170000190;
    public static final int RETURN_TO_XUANSHAN = 170001000;

    public static final int AWAKEN = 175000006; // restores x% of max HP every w seconds
    public static final int XUANSHAN_ARTS_TIAN = 175001000;
    public static final int XUANSHAN_STRIKE_TIAN = 175001002;
    public static final int DIVINE_ART_ERUPTING_FLAME = 175001003;

    public static final int XUANSHAN_STRIKE_DI = 175101001;
    public static final int XUANSHAN_ARTS_DI = 175101003;
    public static final int DIVINE_ART_TEARING_WIND = 175101004;
    public static final int DIVINE_ART_BURNING_IRON = 175101005;
    public static final int SECRET_ART_ARROW_FLIGHT = 175101007;

    public static final int THE_HOLY_MOUNTAIN = 175110010;
    public static final int DIVINE_ART_RIGHTEOUS_THUNDER = 175111001;
    public static final int DIVINE_ART_SWIRLING_TIDE = 175111002;
    public static final int DIVINE_ART_SWIRLING_TIDE_2 = 175111003;
    public static final int SECRET_ART_QI_PROJECTION = 175111004;

    public static final int HEIR_OF_THE_DIVINE = 175120016;
    public static final int XUANSHAN_CROSS_TIAN = 175121001;
    public static final int XUANSHAN_CROSS_DI = 175121002;
    public static final int DIVINE_ART_HOWLING_STORM = 175121003;
    public static final int SECRET_ART_QI_DISRUPTION = 175121005;
    public static final int SECRET_ART_QI_SHIELD = 175121009;
    public static final int SOUL_ART_BLACK_WIND = 175121040;
    public static final int SECRET_ART_STRENGTH_WITHIN = 175121041;
    public static final int AURA_OF_DESTINY = 175121042;

    // V skills
    public static final int SOUL_ART_BENEATH_HEAVEN = 400051084;
    public static final int SOUL_ART_THE_CONQUERED_SELF = 400051086;
    public static final int SOUL_ART_THE_CONQUERED_SELF_ATOM = 400051087;
    public static final int SOUL_ART_THE_OPENED_GATE = 400051088;
    public static final int DIVINE_ART_CRASHING_EARTH = 400051089;

    /** Skills every Mo Xuan has once the job that owns them is reached (no SP is spent on them). */
    private static final int[] addedSkills = new int[]{
            XUANSHAN_SPIRIT, RETURN_TO_XUANSHAN, WILL_OF_THE_ALLIANCE, DIVINE_ART_ERUPTING_FLAME, AWAKEN,
            THE_HOLY_MOUNTAIN, HEIR_OF_THE_DIVINE,
    };

    /** The reference tree's value for Qi Projection's periodical activation. */
    private static final int QI_PROJECTION_PERIOD = 132160;
    public static final int HEXA_DIVINE_ART_SWIRLING_TIDE = 175141012; // HEXA Divine Art: Swirling Tide
    public static final int HEXA_SOUL_ART_BLACK_WIND = 175141014; // HEXA Soul Art: Black Wind
    public static final int HEXA_DIVINE_ART_HOWLING_STORM = 175141006; // HEXA Divine Art: Howling Storm
    public static final int HEXA_DIVINE_ART_ERUPTING_FLAME = 175141010;
    public static final int HEXA_DIVINE_ART_RIGHTEOUS_THUNDER = 175141011;
    public static final int HEXA_DIVINE_ART_TEARING_WIND = 175141015;
    public static final int MO_XUAN_HEROS_WILL = 175121012;
    public static final int HEXA_SECRET_ART_QI_PROJECTION = 175141016; // HEXA Secret Art: Qi Projection
    private static final long GAUGE_RESET_MS = 5000;
    private static final int SWIRLING_TIDE_STATE = 10; // hardcode-ok: client state values, no stat
    private static final int SWIRLING_TIDE_2_STATE = 2010; // hardcode-ok
    private static final int SWIRLING_TIDE_SEC = 5; // hardcode-ok: data updatableTime=5000 on the first hit only
    private static final int HOWLING_STORM_DAM_REDUCE = 50; // hardcode-ok: data s5=50, no SkillStat for it
    private static final int HOWLING_STORM_SEC = 4; // hardcode-ok: hold time q2 plus the finishing blow
    public static final int HEXA_XUANSHAN_ARTS_TIAN = 175141000;
    public static final int HEXA_XUANSHAN_ARTS_TIAN_2 = 175141001;
    public static final int HEXA_XUANSHAN_ARTS_TIAN_3 = 175141002;
    public static final int HEXA_XUANSHAN_ARTS_DI = 175141003;
    public static final int HEXA_XUANSHAN_ARTS_DI_2 = 175141004;
    public static final int HEXA_XUANSHAN_ARTS_DI_3 = 175141005;
    public static final int HEXA_XUANSHAN_ARTS_TIAN_4 = 175141008;
    public static final int HEXA_XUANSHAN_ARTS_DI_4 = 175141009;

    private int powerType;
    private int godPower;
    private ScheduledFuture gaugeResetTimer;
    private ScheduledFuture awakenTimer;

    public MoXuan(Char chr) {
        super(chr);
        grantAddedSkills();
    }

    private void grantAddedSkills() {
        if (chr.getId() == 0 || !isHandlerOfJob(chr.getJob())) {
            return;
        }
        for (int id : addedSkills) {
            if (chr.getJob() < id / 10000 || chr.hasSkill(id)) {
                continue;
            }
            Skill skill = SkillData.getSkillDeepCopyById(id);
            if (skill != null) {
                skill.setCurrentLevel(skill.getMasterLevel());
                chr.addSkill(skill);
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isMoXuan(id);
    }

    @Override
    public void onWarp(net.swordie.ms.world.field.Field oldField, net.swordie.ms.world.field.Field newField) {
        super.onWarp(oldField, newField);
        chr.write(WvsContext.extraSystemInit()); // GMS sends it with every field change
    }

    // region Gauge ----------------------------------------------------------------------------------------------------

    /**
     * Tian and Di arts alternate their stage in gauge slot 0; Divine arts set the Divine power (slot 2) and feed Heir
     * of the Divine. The client keeps slots 0-2 apart (UIMukHyun), so an art stage must never go to slot 2.
     */
    private void updateGauge(int skillId) {
        boolean divine = false;
        switch (skillId) {
            case XUANSHAN_ARTS_TIAN, XUANSHAN_STRIKE_TIAN, XUANSHAN_CROSS_TIAN,
                 HEXA_XUANSHAN_ARTS_TIAN, HEXA_XUANSHAN_ARTS_TIAN_2, HEXA_XUANSHAN_ARTS_TIAN_3, HEXA_XUANSHAN_ARTS_TIAN_4 -> { // + HEXA Xuanshan Arts [Tian]
                setGauge(0, godPower == 110 ? 111 : 110); // GMS: Tian stages 110/111
            }
            case XUANSHAN_ARTS_DI, XUANSHAN_STRIKE_DI, XUANSHAN_CROSS_DI,
                 HEXA_XUANSHAN_ARTS_DI, HEXA_XUANSHAN_ARTS_DI_2, HEXA_XUANSHAN_ARTS_DI_3, HEXA_XUANSHAN_ARTS_DI_4 -> { // + HEXA Xuanshan Arts [Di]
                setGauge(0, godPower == 210 ? 211 : 210); // GMS: Di stages 210/211
            }
            case DIVINE_ART_RIGHTEOUS_THUNDER, DIVINE_ART_ERUPTING_FLAME, HEXA_DIVINE_ART_RIGHTEOUS_THUNDER,
                 HEXA_DIVINE_ART_ERUPTING_FLAME -> {
                heirEffect(1, 1, 0);
                setGauge(2, 1);
                divine = true;
            }
            case DIVINE_ART_TEARING_WIND, HEXA_DIVINE_ART_TEARING_WIND -> {
                heirEffect(2, 1, 0);
                setGauge(2, 2);
                divine = true;
            }
            case DIVINE_ART_SWIRLING_TIDE, HEXA_DIVINE_ART_SWIRLING_TIDE -> {
                heirEffect(2, 1, 1);
                setGauge(2, 0);
                divine = true;
            }
            case DIVINE_ART_SWIRLING_TIDE_2 -> {
                setGauge(1000, 0);
                divine = true;
            }
            case DIVINE_ART_HOWLING_STORM, HEXA_DIVINE_ART_HOWLING_STORM -> {
                heirEffect(1, 2, Util.getRandom(0, 1));
                setGauge(2, 1);
                divine = true;
            }
            case SECRET_ART_QI_SHIELD -> chr.write(UserPacket.effect(Effect.moXuanEffect(SECRET_ART_QI_SHIELD, 100, 0, 0)));
            default -> {
            }
        }
        if (divine && chr.hasSkill(HEIR_OF_THE_DIVINE)) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o = new Option();
            o.nOption = tsm.hasStat(MukHyunDivine) ? Math.min(tsm.getOption(MukHyunDivine).nOption + 1, chr.getSkillStatValue(y, HEIR_OF_THE_DIVINE)) : 1;
            o.rOption = HEIR_OF_THE_DIVINE;
            o.tOption = chr.getSkillStatValue(time, HEIR_OF_THE_DIVINE);
            tsm.putCharacterStatValue(MukHyunDivine, o);
            tsm.sendSetStatPacket();
        }
    }

    private void heirEffect(int a, int b, int c) {
        if (chr.hasSkill(HEIR_OF_THE_DIVINE)) {
            chr.write(UserPacket.effect(Effect.moXuanEffect(HEIR_OF_THE_DIVINE, a, b, c)));
        }
    }

    private void setGauge(int type, int power) {
        powerType = type;
        godPower = power;
        sendGauge();
    }

    /** Sends the gauge; it empties again after a few seconds without a gauge skill. */
    private void sendGauge() {
        chr.write(WvsContext.moXuanGauge(powerType, godPower));
        EventManager.stopTimer(gaugeResetTimer);
        gaugeResetTimer = EventManager.addEvent(this::resetGauge, GAUGE_RESET_MS);
    }

    private void resetGauge() {
        powerType = 0;
        godPower = 0;
        if (chr.getClient() == null || chr.getField() == null) {
            return;
        }
        for (int type = 0; type < 3; type++) {
            chr.write(WvsContext.moXuanGaugeClear(type));
        }
    }

    // endregion

    // region Attack ---------------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int skillId = attackInfo.skillId;
        int slv = chr.getSkillLevel(skillId);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        ensureAwakenTimer();
        updateGauge(skillId);
        switch (skillId) {
            case DIVINE_ART_SWIRLING_TIDE:
            case HEXA_DIVINE_ART_SWIRLING_TIDE:
                tsm.putCharacterStatValue(MukHyun_SEON_PUNG_GAG, new Option(SWIRLING_TIDE_STATE, skillId, SWIRLING_TIDE_SEC));
                tsm.sendSetStatPacket();
                break;
            case DIVINE_ART_SWIRLING_TIDE_2:
                tsm.putCharacterStatValue(MukHyun_SEON_PUNG_GAG, new Option(SWIRLING_TIDE_2_STATE, skillId, SWIRLING_TIDE_SEC));
                tsm.sendSetStatPacket();
                break;
            case SECRET_ART_QI_DISRUPTION:
            case SOUL_ART_BLACK_WIND:
            case HEXA_SOUL_ART_BLACK_WIND:
            case SOUL_ART_BENEATH_HEAVEN:
                if (si != null) {
                    Option o = new Option(1, skillId, si.getValue(ndTime, slv));
                    o.setInMillis(true);
                    tsm.putCharacterStatValue(IndieNotDamaged, o, true);
                    tsm.sendSetStatPacket();
                }
                break;
            case DIVINE_ART_RIGHTEOUS_THUNDER:
            case HEXA_DIVINE_ART_RIGHTEOUS_THUNDER:
                // incapacitates the enemies hit for time sec
                if (si != null && attackInfo.mobAttackInfo.size() > 0) {
                    int stunLv = Math.max(1, attackInfo.slv > 0 ? attackInfo.slv : slv);
                    net.swordie.ms.client.character.skills.debuff.DebuffUtil.applyDebuffOnMobs(chr,
                            net.swordie.ms.life.mob.MobStat.Stun, new Option(1, skillId, si.getValue(time, stunLv)),
                            attackInfo.getMobIds());
                }
                break;
            case DIVINE_ART_HOWLING_STORM:
            case HEXA_DIVINE_ART_HOWLING_STORM:
                tsm.putCharacterStatValue(IndieDamReduceR, new Option(HOWLING_STORM_DAM_REDUCE, skillId, HOWLING_STORM_SEC), true);
                tsm.putCharacterStatValue(IndieAntiMagicShell, new Option(1, skillId, HOWLING_STORM_SEC), true);
                tsm.sendSetStatPacket();
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    // endregion

    // region Skills ---------------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        ensureAwakenTimer();
        updateGauge(skillId);
        Option o1 = new Option();
        switch (skillId) {
            case MO_XUAN_HEROS_WILL:
                tsm.removeAllDebuffs();
                break;
            case SECRET_ART_ARROW_FLIGHT:
                // the first use loads all charges, each later use spends one
                setArrowFlight(tsm.hasStat(StackOverTimeSkill)
                        ? Math.max(tsm.getOption(StackOverTimeSkill).nOption - 1, 0) : si.getValue(y, slv));
                break;
            case SECRET_ART_QI_SHIELD:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(MukHyun_HO_SIN_GANG_GI, o1);
                break;
            case SECRET_ART_QI_PROJECTION:
            case HEXA_SECRET_ART_QI_PROJECTION:
            case SOUL_ART_THE_CONQUERED_SELF:
                if (tsm.hasStatBySkillId(skillId)) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    tsm.removeStatsBySkill(skillId == SECRET_ART_QI_PROJECTION
                            ? SOUL_ART_THE_CONQUERED_SELF : SECRET_ART_QI_PROJECTION);
                    o1.nOption = QI_PROJECTION_PERIOD;
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(IndiePeriodicalSkillActivation, o1);
                }
                break;
            case SECRET_ART_STRENGTH_WITHIN:
                setGauge(2, 1);
                break;
            case AURA_OF_DESTINY:
                o1.nOption = si.getValue(indieDamR, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case SOUL_ART_THE_OPENED_GATE:
                // every attack critical, more critical damage
                o1.nOption = si.getValue(indieCr, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieCr, o1);
                Option o2 = new Option(si.getValue(indieCD, slv), skillId, si.getValue(time, slv));
                tsm.putCharacterStatValue(IndieCrDmg, o2);
                break;
        }
        tsm.sendSetStatPacket();
    }

    /** Secret Art: Arrow Flight gains a charge when the client asks (up to y). */
    public void increaseArrowFlight() {
        if (!chr.hasSkill(SECRET_ART_ARROW_FLIGHT)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int max = chr.getSkillStatValue(y, SECRET_ART_ARROW_FLIGHT);
        int count = tsm.hasStat(StackOverTimeSkill) ? tsm.getOption(StackOverTimeSkill).nOption : 0;
        setArrowFlight(Math.min(max, count + 1));
    }

    private void setArrowFlight(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = count;
        o.rOption = SECRET_ART_ARROW_FLIGHT;
        tsm.putCharacterStatValue(StackOverTimeSkill, o);
        tsm.sendSetStatPacket();
        chr.write(WvsContext.stackSkillRequestResult(-2, 1));
    }

    // endregion

    /** Awaken: x% of max HP every w seconds, even in combat. */
    private void ensureAwakenTimer() {
        if (awakenTimer != null || !chr.hasSkill(AWAKEN)) {
            return;
        }
        int period = Math.max(1, chr.getSkillStatValue(w, AWAKEN));
        awakenTimer = EventManager.addFixedRateEvent(this::awaken, period * 1000L, period * 1000L);
    }

    private void awaken() {
        if (chr.getClient() == null || chr.getField() == null || chr.isDead() || chr.getHP() >= chr.getMaxHP()) {
            return;
        }
        int percent = chr.getSkillStatValue(x, AWAKEN);
        if (percent > 0) {
            chr.heal((int) ((long) chr.getMaxHP() * percent / 100));
        }
    }

    @Override
    public void stopTimers() {
        EventManager.stopTimer(gaugeResetTimer);
        EventManager.stopTimer(awakenTimer);
        gaugeResetTimer = null;
        awakenTimer = null;
        super.stopTimers();
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        setFirstJobCreationStats(chr, JobEnum.MO_XUAN_1.getJobId(), 4, 25, 4, 4);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        advanceByLevel(JobEnum.MO_XUAN_1.getJobId(), JobEnum.MO_XUAN_2.getJobId(), JobEnum.MO_XUAN_3.getJobId(),
                JobEnum.MO_XUAN_4.getJobId());
        grantAddedSkills();
    }

    /** Arts whose cast (a skill request) starts the cooldown and whose hits then come in as attacks of the same id. */
    private static final java.util.Set<Integer> CAST_THEN_HIT_ARTS = java.util.Set.of(
            DIVINE_ART_ERUPTING_FLAME, DIVINE_ART_TEARING_WIND, DIVINE_ART_BURNING_IRON, SECRET_ART_ARROW_FLIGHT,
            DIVINE_ART_RIGHTEOUS_THUNDER, DIVINE_ART_SWIRLING_TIDE, DIVINE_ART_SWIRLING_TIDE_2,
            SECRET_ART_QI_PROJECTION, DIVINE_ART_HOWLING_STORM, SECRET_ART_QI_DISRUPTION, SECRET_ART_QI_SHIELD,
            SOUL_ART_BLACK_WIND, SECRET_ART_STRENGTH_WITHIN, SOUL_ART_BENEATH_HEAVEN, SOUL_ART_THE_CONQUERED_SELF,
            SOUL_ART_THE_CONQUERED_SELF_ATOM, SOUL_ART_THE_OPENED_GATE, DIVINE_ART_CRASHING_EARTH,
            HEXA_DIVINE_ART_SWIRLING_TIDE, HEXA_SOUL_ART_BLACK_WIND, HEXA_DIVINE_ART_HOWLING_STORM,
            HEXA_DIVINE_ART_ERUPTING_FLAME, HEXA_DIVINE_ART_RIGHTEOUS_THUNDER, HEXA_DIVINE_ART_TEARING_WIND,
            HEXA_SECRET_ART_QI_PROJECTION, AURA_OF_DESTINY);

    @Override
    public net.swordie.ms.client.character.skills.BypassCooldownCheckType canBypassCooldownCheck(int skillId,
            AttackInfo attackInfo, SkillUseInfo sui, net.swordie.ms.client.character.skills.SkillUseSource source) {
        // the hits of an Art that was just cast arrive while the cooldown the cast set is running
        if (source.isAttackRequest() && CAST_THEN_HIT_ARTS.contains(skillId) && chr.hasSkillOnCooldown(skillId)) {
            return net.swordie.ms.client.character.skills.BypassCooldownCheckType.BypassCheckAndCooldown;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }
}

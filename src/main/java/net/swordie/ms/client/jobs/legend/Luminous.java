package net.swordie.ms.client.jobs.legend;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.*;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.fieldeffect.GreyFieldType;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Luminous extends Job {
    public static final int LIGHT_SPEED_BOOST = 27111008; // on/off: longer teleport
    public static final int SUNFIRE = 20040216; //
    public static final int ECLIPSE = 20040217; //
    public static final int EQUILIBRIUM = 20040219; //
    public static final int EQUILIBRIUM2 = 20040220;
    public static final int EQUILIBRIUM_LIBERATION = 27101009; // starts Equilibrium once the gauge is full (v265 27111009)
    public static final int LIGHTS_UMBRA = 27111009; // v271: invincible for time sec
    public static final int INNER_LIGHT = 20040221;
    public static final int FLASH_BLINK = 20041222;
    public static final int CHANGE_LIGHT_DARK = 20041239;

    public static final int MAGIC_BOOSTER = 27101004; //Buff
    public static final int BLACK_BLESSING = 27100008; // passive
    public static final int PRESSURE_VOID = 27101202;

    public static final int SHADOW_SHELL = 27111004; //Buff
    public static final int RAY_OF_REDEMPTION = 27111101; // Attack + heals party members
    public static final int DUSK_GUARD = 27111005; //Buff
    public static final int PHOTIC_MEDITATION = 27111006; //Buff
    public static final int LUNAR_TIDE = 27110007;
    public static final int DEATH_SCYTHE = 27111303;
    public static final int MOONLIGHT_SPEAR = 27111202;
    public static final int SPECTRAL_LIGHT = 27111100;

    public static final int DARK_CRESCENDO = 27120005;
    public static final int ARCANE_PITCH = 27121006; //Buff
    public static final int MAPLE_WARRIOR_LUMI = 27121009; //Buff
    public static final int ENDER = 27121303;
    public static final int DARKNESS_MASTERY = 27120008;
    public static final int HEROS_WILL_LUMI = 27121010;
    public static final int APOCALYPSE = 27121202;
    public static final int APOCALYPSE_RECHARGE = 27120047;

    public static final int EQUALIZE = 27121054;
    public static final int HEROIC_MEMORIES_LUMI = 27121053;
    public static final int ARMAGEDDON = 27121052; //Stun debuff

    // V
    public static final int DOOR_OF_LIGHT = 400021005;
    public static final int AETHER_CONDUIT_L = 400021041;
    public static final int AETHER_CONDUIT_D = 400021049;
    public static final int AETHER_CONDUIT_EQ = 400021050;
    public static final int TWILIGHT_NOVA_L = 27121113;
    public static final int TWILIGHT_NOVA_D = 27121214;
    public static final int TWILIGHT_NOVA_EQ = 27121315;
    public static final int HEXA_TWILIGHT_NOVA_L = 27141007;
    public static final int HEXA_TWILIGHT_NOVA_D = 27141008;
    public static final int HEXA_TWILIGHT_NOVA_EQ = 27141009;
    private static final long NOVA_CAST_WINDOW_MS = 3000;
    private static final int LIFE_TIDAL_CRIT = 2; // hardcode-ok: client mode (1 = damage)
    private static final int ARMAGEDDON_INVINCIBLE_SEC = 2; // hardcode-ok
    private static final int LIBERATION_ORB_PASSIVE = 2; // hardcode-ok: client state value
    private long novaCastAt;
    public static final int BAPTISM_OF_LIGHT_AND_DARKNESS = 400021071;
    public static final int LIBERATION_ORB = 400021105;
    public static final int LIBERATION_ORB_IMBALANCE_ATOM = 400021106; // Active Imbalance ForceAtom
    public static final int LIBERATION_ORB_LIGHT_ZONE = 400021107; // Passive Light
    public static final int LIBERATION_ORB_DARK_BULLET = 400021108; // Passive Dark
    public static final int LIBERATION_ORB_IMBALANCE_ATTACK = 400021109;
    public static final int LIBERATION_ORB_BALANCE_ATTACK = 400021110;
    public static final int HEXA_ENDER = 27141000; // HEXA Ender
    public static final int INFILTRATE = 20041001;
    public static final int FLASH_SHOWER = 27001100;
    public static final int ABYSSAL_DROP = 27001201;
    public static final int HEXA_APOCALYPSE = 27141004;
    public static final int DESTROYED_TEMPLE_OF_TIME_ENTRANCE_FIELD = 927020000;

    private static final int[] addedSkills = new int[]{
            EQUILIBRIUM,
    };

    private long lastBonusChain = 0;
    private String curChainEffectResolution = "";
    private ScheduledFuture changeModeTimer;

    public Luminous(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }

            if (chr.getTemporaryStatManager().getLarknessManager() == null) {
                chr.getTemporaryStatManager().setLarknessManager(new LarknessManager(chr));
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isLuminous(id);
    }


    private void giveLunarTideBuff() {
        if (chr.hasSkill(LUNAR_TIDE)) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            tsm.removeStatsBySkill(LUNAR_TIDE);
            Option o1 = new Option();
            Option o2 = new Option();
            SkillInfo lti = SkillData.getSkillInfoById(LUNAR_TIDE);
            Skill skill = chr.getSkill(LUNAR_TIDE);
            int slv = skill.getCurrentLevel();
            int maxMP = chr.getStat(Stat.mmp);
            int curMP = chr.getStat(Stat.mp);
            int maxHP = chr.getStat(Stat.mhp);
            int curHP = chr.getStat(Stat.hp);
            double ratioHP = ((double) curHP / maxHP);
            double ratioMP = ((double) curMP) / maxMP;

            //if (ratioHP > ratioMP) {
            if (ratioHP > ratioMP) {
                //Crit Rate      HP is Greater than MP
                o1.nOption = LIFE_TIDAL_CRIT;
                o1.rOption = LUNAR_TIDE;
                tsm.putCharacterStatValue(LifeTidal, o1);
                o2.nOption = lti.getValue(prop, slv);     //only gives 10% for w/e reason but the SkillStat is correct
                o2.rOption = LUNAR_TIDE;
                tsm.putCharacterStatValue(CriticalBuff, o2);
            } else {
                //Damage         MP is Greater than HP
                o1.nOption = 1;
                o1.rOption = LUNAR_TIDE;
                tsm.putCharacterStatValue(LifeTidal, o1);
                o2.nOption = lti.getValue(x, slv);
                o2.rOption = LUNAR_TIDE;
                tsm.putCharacterStatValue(DamR, o2);
            }
            tsm.sendSetStatPacket();
        }
    }

    public static final int HARMONIC_PARADOX = 27141500; // 6th job Origin
    private long paradoxAt;

    // Harmonic Paradox: an active Equilibrium lasts x sec longer (once per cast)
    private void extendEquilibrium(TemporaryStatManager tsm) {
        long now = System.currentTimeMillis();
        int lv = chr.getSkillLevel(HARMONIC_PARADOX);
        Option eq = tsm.hasStat(Larkness) ? tsm.getOption(Larkness) : null;
        if (lv <= 0 || eq == null || eq.nOption != 2 || eq.tOption <= 0 || now - paradoxAt < 30_000) {
            return;
        }
        paradoxAt = now;
        Option longer = eq.deepCopy();
        longer.setInMillis(false);
        longer.tOption = (int) (Math.max(0, eq.getRemainingTime()) / 1000)
                + SkillData.getSkillInfoById(HARMONIC_PARADOX).getValue(x, lv);
        tsm.putCharacterStatValue(Larkness, longer, true);
        tsm.sendSetStatPacket();
    }

    public int getMoreEquilibriumTime() {
        int eqTime = 0;
        SkillInfo eqi = SkillData.getSkillInfoById(DARKNESS_MASTERY);
        if (chr.hasSkill(DARKNESS_MASTERY)) {
            eqTime += eqi.getValue(time, chr.getSkillLevel(DARKNESS_MASTERY));
        }
        return eqTime;
    }


    // HEXA skills (2714xxxx) don't follow the id ranges the 4th job ones use
    private static final java.util.Set<Integer> HEXA_LIGHT = java.util.Set.of(27141001, 27141006, 27141007);
    private static final java.util.Set<Integer> HEXA_DARK = java.util.Set.of(27141003, 27141004, 27141008);
    private static final java.util.Set<Integer> HEXA_EQUILIBRIUM = java.util.Set.of(27141000, 27141009);

    private static boolean isLightSkill(int skillId) {
        return SkillConstants.isLarknessLightSkill(skillId) || HEXA_LIGHT.contains(skillId);
    }

    private static boolean isDarkSkill(int skillId) {
        return SkillConstants.isLarknessDarkSkill(skillId) || HEXA_DARK.contains(skillId);
    }

    private static boolean isEquilibriumMagic(int skillId) {
        return SkillConstants.isEquilibriumSkill(skillId) || HEXA_EQUILIBRIUM.contains(skillId);
    }

    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        LarknessManager lm = tsm.getLarknessManager();
        if (attackInfo.skillId == HARMONIC_PARADOX) {
            extendEquilibrium(tsm);
        }
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = (byte) skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        if (
                chr.getJob() != 2004
                        && tsm.hasStat(Larkness)
                        && ((lm.isDark() && isDarkSkill(attackInfo.skillId))
                        || (!lm.isDark() && isLightSkill(attackInfo.skillId)))
                        && (tsm.getOption(Larkness).rOption != EQUILIBRIUM2)
        ) {

            changeLarknessGauge(attackInfo, si, slv);
        }

        if (hasHitMobs) {
            if (!tsm.hasStat(Larkness)) {
                lm.changeMode();
            }

            // Dark Crescendo
            int crescendoProp = getCrescendoProp();
            if (skill != null && Util.succeedProp(crescendoProp)) {
                incrementDarkCrescendo(tsm);
            }

            // Liberation Orb Passive Attributes
            if (chr.hasSkill(LIBERATION_ORB)) {
                if (!hasLiberationOrb()) {
                    if (isDarkSkill(attackInfo.skillId)) {
                        procDarkBullet();
                    } else if (isLightSkill(attackInfo.skillId)) {
                        procLightZone();
                    }
                } else if (hasLiberationOrb() &&
                        (isLightSkill(attackInfo.skillId)
                                || isDarkSkill(attackInfo.skillId)
                                || isEquilibriumMagic(attackInfo.skillId)
                        )) {
                    procLiberationOrb();
                }
            }
        }

        if (SkillChangeConstants.PRE_PORT_CUSTOMS && tsm.getLarknessManager().isEquilibrium() && chr.hasSkill(MOONLIGHT_SPEAR) && Util.getCurrentTimeLong() > lastBonusChain + swGetMoonlightSpearCooldown(tsm)) {
            chr.write(UserLocal.userBonusAttackRequest(MOONLIGHT_SPEAR));
            lastBonusChain = Util.getCurrentTimeLong();
        }

        giveLunarTideBuff();
        giveEquilibriumHPDrain(attackInfo.skillId);
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case ARMAGEDDON:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                }
                o2.nOption = 1;
                o2.rOption = skill.getSkillId();
                o2.tOption = ARMAGEDDON_INVINCIBLE_SEC;
                tsm.putCharacterStatValue(IndieNotDamaged, o2, true);
                tsm.sendSetStatPacket();
                break;
            case RAY_OF_REDEMPTION:
                chr.heal(chr.getMaxHP()); // 800% Recovery
                break;
            case ENDER:
            case HEXA_ENDER:
                if (chr.hasSkill(BAPTISM_OF_LIGHT_AND_DARKNESS)) {
                    incrementSwordsOfConsciousness();
                }
                break;
        }

        super.handleAttack(c, attackInfo);
    }

    // [Luminous] Custom Skill Change
    private int swGetMoonlightSpearCooldown(TemporaryStatManager tsm) {
        return tsm.getOptByCTSAndSkill(IndieBooster, OVERCOMING_TERROR) != null ? SkillChangeConstants.TERROR_MOONLIGHT_SPEAR_CD : SkillChangeConstants.NORMAL_MOONLIGHT_SPEAR_CD;
    }

    // [Luminous] Custom Skill Change
    private void swGiveTerrorBuff(TemporaryStatManager tsm) {
        Effect effect = Effect.effectFromWZ("Effect/Direction15.img/effect/BM_Weapon/powerUp/0", false, 0, 0, 0);
        chr.write(UserPacket.effect(effect));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), effect), chr);
        Option o1 = new Option(SkillChangeConstants.TERROR_BOOSTER, OVERCOMING_TERROR, SkillChangeConstants.TERROR_DURATION);
        tsm.putCharacterStatValue(IndieBooster, o1, true);
        tsm.sendSetStatPacket();
    }

    private void changeLarknessGauge(AttackInfo attackInfo, SkillInfo si, int slv) {
        LarknessManager lm = chr.getTemporaryStatManager().getLarknessManager();
        if (si != null && si.getValue(gauge, slv) > 0) {
            int gaugeInc = si.getValue(gauge, slv);
            if ((attackInfo.skillId == APOCALYPSE || attackInfo.skillId == HEXA_APOCALYPSE) && chr.hasSkill(APOCALYPSE_RECHARGE)) { // Recharge HyperSkill
                gaugeInc += chr.getSkillStatValue(gauge, APOCALYPSE_RECHARGE);
            }
            lm.changeGauge(gaugeInc);
        }
    }

    private void incrementSwordsOfConsciousness() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int curStack = tsm.hasStat(SwordsOfConsciousness) ? tsm.getOption(SwordsOfConsciousness).nOption : 0;
        changeSwordsOfConsciousness(curStack + 1);
    }

    private void changeSwordsOfConsciousness(int swords) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int skillId = BAPTISM_OF_LIGHT_AND_DARKNESS;
        var cap = 12;
        Option o = new Option();
        if (swords < cap) {
            o.nOption = swords;
            o.rOption = skillId;
            tsm.putCharacterStatValue(SwordsOfConsciousness, o);
            tsm.sendSetStatPacket();
        } else {
            tsm.removeStatsBySkill(skillId);
            chr.resetSkillCoolTime(skillId);

            // [Luminous] Custom Skill Change
            if (SkillChangeConstants.PRE_PORT_CUSTOMS) {
                swGiveTerrorBuff(tsm);
            }
        }
    }

    private void giveEquilibriumHPDrain(int skillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        if (tsm.getLarknessManager().isEquilibrium() && isLightSkill(skillId) && chr.hasSkill(EQUILIBRIUM)) {
            Skill skill = chr.getSkill(EQUILIBRIUM);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            chr.heal(chr.getHPPerc(si.getValue(x, 1)));
        }
    }

    private void incrementDarkCrescendo(TemporaryStatManager tsm) {
        Option o = new Option();
        Option o1 = new Option();
        SkillInfo crescendoInfo = SkillData.getSkillInfoById(DARK_CRESCENDO);
        Skill skill = chr.getSkill(DARK_CRESCENDO);
        int slv = skill.getCurrentLevel();
        int amount = 1;
        if (tsm.hasStat(StackBuff)) {
            amount = tsm.getOption(StackBuff).mOption;
            if (amount < getMaxDarkCrescendoStack()) {
                amount++;
            }
        }
        o.setInMillis(true);
        o.nOption = (amount * crescendoInfo.getValue(damR, slv));
        o.rOption = DARK_CRESCENDO;
        o.tOption = crescendoInfo.getValue(time, slv) * 1000; // in millis (setInMillis(true) above)
        o.mOption = amount;
        o.setInMillis(true);
        tsm.putCharacterStatValue(StackBuff, o, true);
        tsm.sendSetStatPacket();
    }

    private int getCrescendoProp() {
        Skill skill = null;
        if (chr.hasSkill(DARK_CRESCENDO)) {
            skill = chr.getSkill(DARK_CRESCENDO);
        }
        return skill == null ? 0 : SkillData.getSkillInfoById(DARK_CRESCENDO).getValue(prop, skill.getCurrentLevel());
    }

    private int getMaxDarkCrescendoStack() {
        Skill skill = null;
        if (chr.hasSkill(DARK_CRESCENDO)) {
            skill = chr.getSkill(DARK_CRESCENDO);
        }
        return skill == null ? 0 : SkillData.getSkillInfoById(skill.getSkillId()).getValue(x, skill.getCurrentLevel());
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        LarknessManager lm = tsm.getLarknessManager();
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillId) {
            case EQUILIBRIUM_LIBERATION:
                if (lm != null && !lm.isEquilibrium()) {
                    lm.goEquilibriumMode(false);
                }
                break;
            case LIGHTS_UMBRA:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true); // GMS: the indie stat
                break;
            case MAGIC_BOOSTER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case SHADOW_SHELL:
                o1.nOption = si.getValue(u, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                // no bOption for Luminous' AntiMagicShell
                tsm.putCharacterStatValue(AntiMagicShell, o1);
                break;
            case DUSK_GUARD:
                o2.nOption = si.getValue(indiePdd, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDEF, o2);
                break;
            case PHOTIC_MEDITATION:
                o1.nOption = si.getValue(emad, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EMAD, o1);
                break;
            case DARK_CRESCENDO:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                o1.mOption = 1;
                tsm.putCharacterStatValue(StackBuff, o1);
                break;
            case ARCANE_PITCH:
                o1.nOption = si.getValue(y, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ElementalReset, o1);
                break;
            case EQUALIZE:
                // a time buff now (Torrent of Fate on direct hits); Equilibrium comes from Equilibrium Liberation
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(LuminousMemorize, o1);
                break;
            case HEROIC_MEMORIES_LUMI:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case DOOR_OF_LIGHT:
                Summon summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case AETHER_CONDUIT_L:
            case AETHER_CONDUIT_D:
            case AETHER_CONDUIT_EQ:
                slv = chr.getSkillLevel(AETHER_CONDUIT_L);
                // ends with the key-down cancel, a recast (replaces it) or the cap below
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillId, slv);
                aa.setDuration(AETHER_CONDUIT_MAX_MS);
                field.spawnAffectedAreaAndRemoveOld(aa);
                break;
            case BAPTISM_OF_LIGHT_AND_DARKNESS:
                summon = Summon.getSummonByNoCTS(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.SequenceAttack);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case LIBERATION_ORB:
                if (canFreeLiberationOrb()) {
                    var nOpt = liberationOrbIsBalanced() ? 2 : 1;
                    tsm.removeStatsBySkill(LIBERATION_ORB);

                    o1.nOption = nOpt; // 1 = Unbalanced  ||  2 = Balanced
                    o1.rOption = skillId;
                    o1.tOption = si.getValue(time, slv);
                    tsm.putCharacterStatValue(LiberationOrb, o1);
                }
                break;
        }
        tsm.sendSetStatPacket();
    }

    private void procLiberationOrb() {
        if (!hasLiberationOrb() || chr.hasSkillOnCooldown(LIBERATION_ORB_BALANCE_ATTACK) || chr.hasSkillOnCooldown(LIBERATION_ORB_IMBALANCE_ATTACK)) {
            return;
        }
        var skillId = liberationOrbIsBalanced() ? LIBERATION_ORB_BALANCE_ATTACK : LIBERATION_ORB_IMBALANCE_ATTACK;

        chr.write(FieldPacket.registerExtraSkill(LIBERATION_ORB, new ExtraSkillInfo(chr, skillId)));
        chr.addSkillCoolTime(skillId, chr.getSkillStatValue(w, LIBERATION_ORB));
    }

    private boolean hasLiberationOrbPassiveOnCD() {
        return chr.hasSkillOnCooldown(LIBERATION_ORB_LIGHT_ZONE) || chr.hasSkillOnCooldown(LIBERATION_ORB_DARK_BULLET);
    }

    private void procLightZone() {
        if (hasLiberationOrbPassiveOnCD()) {
            return;
        }

        var skillId = LIBERATION_ORB_LIGHT_ZONE;
        chr.write(FieldPacket.registerExtraSkill(LIBERATION_ORB, new ExtraSkillInfo(skillId, chr.getPosition(), chr.isLeft(), Util.getRandom(150, 750), 0)));
        incLightAttribute();
        chr.addSkillCoolTime(skillId, chr.getSkillStatValue(x, LIBERATION_ORB) * 1000);
    }

    private void procDarkBullet() {
        if (hasLiberationOrbPassiveOnCD()) {
            return;
        }

        var skillId = LIBERATION_ORB_DARK_BULLET;
        chr.write(FieldPacket.registerExtraSkill(LIBERATION_ORB, new ExtraSkillInfo(chr, skillId)));
        incDarkAttribute();
        chr.addSkillCoolTime(skillId, chr.getSkillStatValue(x, LIBERATION_ORB) * 1000);
    }

    private void incDarkAttribute() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        var lightAttr = 0;
        var darkAttr = 0;
        if (tsm.hasStat(LiberationOrbPassive)) {
            Option o = tsm.getOption(LiberationOrbPassive);
            lightAttr = o.xOption;
            darkAttr = o.yOption;
        }
        if (darkAttr < 4) {
            darkAttr++;
        }

        updateLiberationOrbPassive(lightAttr, darkAttr);
    }

    private void incLightAttribute() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        var lightAttr = 0;
        var darkAttr = 0;
        if (tsm.hasStat(LiberationOrbPassive)) {
            Option o = tsm.getOption(LiberationOrbPassive);
            lightAttr = o.xOption;
            darkAttr = o.yOption;
        }
        if (lightAttr < 4) {
            lightAttr++;
        }

        updateLiberationOrbPassive(lightAttr, darkAttr);
    }

    private void updateLiberationOrbPassive(int lightAttr, int darkAttr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = LIBERATION_ORB_PASSIVE;
        o.rOption = LIBERATION_ORB;
        o.xOption = lightAttr;
        o.yOption = darkAttr;
        tsm.putCharacterStatValue(LiberationOrbPassive, o);
        tsm.sendSetStatPacket();
    }

    private boolean liberationOrbIsBalanced() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(LiberationOrbPassive)) {
            return false;
        }
        Option o = tsm.getOption(LiberationOrbPassive);
        return o.yOption == o.xOption;
    }

    private boolean hasLiberationOrb() {
        return chr.getTemporaryStatManager().hasStat(LiberationOrb);
    }

    private boolean canFreeLiberationOrb() {
        return chr.getTemporaryStatManager().hasStat(LiberationOrbPassive);
    }

    @Override
    public BypassCooldownCheckType canBypassCooldownCheck(int skillId, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        // dark/equilibrium Twilight Nova: the cooltime goes on the light id (the one in the skill window).
        // One cast sends several packets: those right after the cast pass, a recast during the cooltime is refused.
        int used = attackInfo != null && attackInfo.skillId != 0 ? attackInfo.skillId : sui != null ? sui.skillID : 0;
        boolean nova = (skillId == TWILIGHT_NOVA_L && (used == TWILIGHT_NOVA_D || used == TWILIGHT_NOVA_EQ))
                || (skillId == HEXA_TWILIGHT_NOVA_L && (used == HEXA_TWILIGHT_NOVA_D || used == HEXA_TWILIGHT_NOVA_EQ));
        if (nova) {
            long now = System.currentTimeMillis();
            if (chr.hasSkillOnCooldown(skillId)) {
                return now - novaCastAt < NOVA_CAST_WINDOW_MS
                        ? BypassCooldownCheckType.BypassCheckAndCooldown : BypassCooldownCheckType.Check;
            }
            novaCastAt = now;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        switch (skillId) {
            case ENDER:
            case HEXA_ENDER:
            case DEATH_SCYTHE:
                if (tsm.getLarknessManager().isEquilibrium()) {
                    return 0;
                }
        }
        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }

    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        if (cts == Larkness && o.rOption == EQUILIBRIUM2) {
            EventManager.stopTimer(changeModeTimer);
            changeModeTimer = EventManager.addEvent(this::getOutOfEquilibrium, 30, TimeUnit.MILLISECONDS);
        }

        super.handleRemoveCTS(cts, o);
    }

    public void getOutOfEquilibrium() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        LarknessManager lm = tsm.getLarknessManager();
        lm.changeMode();
        lm.changeGauge(50);
        chr.deactivateCustomEffect();
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        // Black Blessing: damage taken -x%; a client's reported hit has it taken off already
        if (chr.hasSkill(BLACK_BLESSING) && hitInfo.hpDamage > 0 && hitInfo.clientHpDamage < 0) {
            SkillInfo si = SkillData.getSkillInfoById(BLACK_BLESSING);
            int dmgAbsorbed = si.getValue(x, chr.getSkillLevel(BLACK_BLESSING));
            hitInfo.hpDamage = (int) (hitInfo.hpDamage * ((100 - dmgAbsorbed) / 100D));
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    @Override
    public void handleMobDebuffSkill(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(AntiMagicShell)) {
            tsm.removeAllDebuffs();
            deductShadowShell();
        }

    }

    private void deductShadowShell() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(SHADOW_SHELL)) {
            return;
        }
        Skill skill = chr.getSkill(SHADOW_SHELL);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        Option o = new Option();
        if (tsm.hasStat(AntiMagicShell)) {
            int shadowShellCount = tsm.getOption(AntiMagicShell).nOption;

            if (shadowShellCount > 0) {
                shadowShellCount--;
            }

            if (shadowShellCount <= 0) {
                tsm.removeStatsBySkill(skill.getSkillId());
                tsm.sendResetStatPacket();
            } else {
                o.nOption = shadowShellCount;
                o.rOption = skill.getSkillId();
                o.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(AntiMagicShell, o);
                tsm.sendSetStatPacket();
            }
            chr.write(UserPacket.effect(Effect.skillSpecial(skill.getSkillId())));
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillSpecial(skill.getSkillId())));
        }
    }

    @Override
    public int alterMpCon(int mpCon, int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (isDarkSkill(skillId)
                && tsm.hasStat(Larkness)
                && tsm.getOption(Larkness).rOption == EQUILIBRIUM2) {
            // Luminous Dark Skills do not consumpe MP if in Equilibrium Mode.
            return 0;
        }

        return super.alterMpCon(mpCon, skillId, slv, attackInfo, sui, source);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        chr.getAvatarData().getCharacterStat().setPosMap(DESTROYED_TEMPLE_OF_TIME_ENTRANCE_FIELD);
    }

    // [Luminous] Custom Skill Changes
    public void activateCustomEffect(String res, int fadeDuration) {
        FieldEffect colourEff = FieldEffect.setFieldColor(GreyFieldType.Field, (short) 100, (short) 50, (short) 60, fadeDuration);
        chr.write(FieldPacket.fieldEffect(colourEff));

        FieldEffect chainsEff = FieldEffect.onOffLayer(0, fadeDuration, res,
                0, 0, 12, String.format("Effect/FieldGimmickEff.img/FearEventFrame/%s", res), 0, true,
                -1, true);
        chainsEff.setArg10(120);
        chr.write(FieldPacket.fieldEffect(chainsEff));
        resolution = res;
    }

    // [Luminous] Custom Skill Changes
    public void deactivateCustomEffect() {
        FieldEffect colourEff = FieldEffect.setFieldColor(GreyFieldType.Field, (short) 255, (short) 255, (short) 255, 700);
        FieldEffect chainsEff = FieldEffect.onOffLayer(2, 400, resolution,
                0, 0, 12, String.format("Effect/FieldGimmickEff.img/FearEventFrame/%s", resolution), 0, true,
                -1, true);
        chainsEff.setArg8(1);
        chr.write(FieldPacket.fieldEffect(colourEff));
        chr.write(FieldPacket.fieldEffect(chainsEff));
        resolution = "";
    }

    private static final int AETHER_CONDUIT_MAX_MS = 30_000; // safety cap; the client ends it by cancel or recast

    private boolean removeAetherConduit(Field field) {
        boolean had = false;
        for (int id : new int[]{AETHER_CONDUIT_L, AETHER_CONDUIT_D, AETHER_CONDUIT_EQ}) {
            if (field.getAffectedAreas().stream().anyMatch(a -> a.getSkillID() == id && a.getOwner() == chr)) {
                had = true;
            }
            field.removeAffectedAreas(id, chr.getId());
        }
        return had;
    }

    @Override
    public void handleCancelKeyDownSkill(Char chr, int skillID) {
        if (skillID == AETHER_CONDUIT_L || skillID == AETHER_CONDUIT_D || skillID == AETHER_CONDUIT_EQ) {
            removeAetherConduit(chr.getField());
        }
        super.handleCancelKeyDownSkill(chr, skillID);
    }
}

package net.swordie.ms.client.jobs.cygnus;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.ExtraSkillInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Mihile extends Job {
    public static final int IMPERIAL_RECALL = 50001245;

    public static final int ROYAL_GUARD_STACK_BUFF = 51001005;
    public static final int ROYAL_GUARD = 51001006; //Special Buff/Attack
    public static final int ROYAL_GUARD_2 = 51001007;
    public static final int ROYAL_GUARD_3 = 51001008;
    public static final int ROYAL_GUARD_4 = 51001009;
    public static final int ROYAL_GUARD_5 = 51001010;

    public static final int SWORD_BOOSTER = 51101003; //Buff
    public static final int RALLY = 51101004; //Buff

    public static final int ENDURING_SPIRIT = 51111004; //Buff
    public static final int SOUL_LINK = 51110008; // v271 passive (v265 toggle 51111008)
    public static final int MAGIC_CRASH = 51111005; //Special Skill (Debuff Mobs)
    public static final int ADVANCED_ROYAL_GUARD = 51110009; //Upgrade on Royal Guard
    public static final int SELF_RECOVERY = 51110000;

    public static final int ROILING_SOUL = 51121006; //Buff (ON/OFF)
    public static final int FOUR_POINT_ASSAULT = 51101005; // Radiant Soul skill (blind)
    public static final int SHINING_PIERCE = 51001004;
    public static final int SOUL_RELEASE = 51111006;
    public static final int RADIANT_CROSS_ASSAULT = 51121017;
    public static final int HEXA_RADIANT_CROSS_ASSAULT = 51141001;
    public static final int INSTALL_SHIELD = 51121016;
    public static final int HEXA_INSTALL_SHIELD = 51141015;
    public static final int INSTALL_SHIELD_PERSIST = 51120051;
    public static final int SOUL_MAJESTY = 51121059;
    public static final int OFFENSIVE_DEFENSE = 51110014;
    public static final int OFFENSIVE_DEFENSE_WAVE = 51111015;
    public static final int HEXA_OFFENSIVE_DEFENSE = 51141016;
    public static final int HEXA_OFFENSIVE_DEFENSE_WAVE = 51141017;
    public static final int HEXA_SOUL_MAJESTY = 51141019;
    private static final int SOUL_MAJESTY_WAVE_COOLDOWN_MS = 2000;
    // no Skill.wz stat for these (Sword of Light's -30% accuracy is in its tooltip text only)
    private static final int SHIELD_OF_LIGHT_AURA_SEC = 2; // hardcode-ok
    private static final int SHIELD_OF_LIGHT_LAYERS = 3; // hardcode-ok
    private static final int SWORD_OF_LIGHT_ACC = -30; // hardcode-ok
    public static final int RADIANT_CROSS = 51121009; //Special Attack (Accuracy Debuff)    Creates an Area of Effect
    public static final int RADIANT_CROSS_SPREAD = 51120057; //Area of Effect,  After Radiant Cross
    public static final int CALL_OF_CYGNUS_MIHILE = 51121005; //Buff
    public static final int SOUL_ASYLUM = 51120003;
    public static final int ENDURING_SPIRIT_STEEL_SKIN = 51120044;
    public static final int ENDURING_SPIRIT_PREPARATION = 51120045;
    public static final int ENDURING_SPIRIT_PERSIST = 51120043;
    public static final int FOUR_POINT_ASSAULT_OPPORTUNITY = 51120050;

    //Final Attack
    public static final int FINAL_ATTACK_MIHILE = 51100002;
    public static final int ADVANCED_FINAL_ATTACK_MIHILE = 51120002;
    public static final int HEXA_FINAL_ATTACK_MIHILE = 51140018;


    public static final int CHARGING_LIGHT = 51121052;
    public static final int QUEEN_OF_TOMORROW = 51121053;
    public static final int SACRED_CUBE = 51121054;

    public static final int SHIELD_OF_LIGHT = 400011011;
    public static final int HEXA_SHIELD_OF_LIGHT = 500061008;
    public static final int SWORD_OF_LIGHT = 400011032;
    public static final int SWORD_OF_LIGHT_1 = 400011033;
    public static final int SWORD_OF_LIGHT_2 = 400011034;
    public static final int SWORD_OF_LIGHT_3 = 400011035;
    public static final int SWORD_OF_LIGHT_4 = 400011036;
    public static final int SWORD_OF_LIGHT_5 = 400011037;
    public static final int SWORD_OF_LIGHT_PASSIVE = 400011067;
    public static final int RADIANT_SOUL = 400011083;

    public static final int LIGHT_OF_COURAGE_ATTACK = 400011050; // Periodic Attack
    public static final int LIGHT_OF_COURAGE_MOB_STAT = 400011086; // MobStat
    public static final int LIGHT_OF_COURAGE_MOB_ATTACK = 400011087; // End of Buff attack on Mobs with Debuff
    public static final int LIGHT_OF_COURAGE_BUFF = 400011127; // buff
    public static final int LIGHT_OF_COURAGE_SI = 400011128; // Buff Info
    public static final int HEXA_LIGHT_OF_COURAGE = 500061010;
    public static final int HEXA_LIGHT_OF_COURAGE_SI = 500061011;
    public static final int HEXA_RADIANT_CROSS = 51141000; // HEXA Radiant Cross
    public static final int HEXA_CHARGING_LIGHT = 51141013; // HEXA Charging Light
    public static final int DURENDAL = 51141500;
    public static final int ROYAL_GUARD_6 = 51001013;
    public static final int ROYAL_GUARD_7 = 51111011;
    public static final int ROYAL_GUARD_8 = 51111012;
    public static final int HEXA_ROYAL_GUARD_3 = 51141002;
    public static final int HEXA_ROYAL_GUARD = 51141003;
    public static final int HEXA_ROYAL_GUARD_4 = 51141004;
    public static final int HEXA_ROYAL_GUARD_5 = 51141005;
    public static final int HEXA_ROYAL_GUARD_6 = 51141006;
    public static final int HEXA_ROYAL_GUARD_7 = 51141007;
    public static final int HEXA_ROYAL_GUARD_2 = 51141012;
    public static final int DURENDAL_2 = 51141501;
    public static final int DURENDAL_3 = 51141502;
    public static final int LIMBERTS_GENERAL_STORE_FIELD = 913070000;

    private static final int[] addedSkills = new int[]{
    };

    private ScheduledFuture selfRecoveryTimer;
    private ScheduledFuture shielfOfLightTimer;
    private ScheduledFuture soulLinkTimer;
    private long durendalCastTime;

    public Mihile(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id) || chr.getSkill(id).getCurrentLevel() == 0) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            removeSkillBeforeFourthJob(CALL_OF_CYGNUS_MIHILE);
            startSelfRecovery();
            soulLinkTimer = EventManager.addFixedRateEvent(this::refreshSoulLink, 4, 4, TimeUnit.SECONDS);
        }
    }

    // v271 Soul Link is passive: damage +indieDamR% per party member in the map (alone counts as 1)
    private void refreshSoulLink() {
        if (chr.getField() == null || !chr.hasSkill(SOUL_LINK)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SOUL_LINK);
        if (si == null) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int members = chr.getParty() == null ? 1 : Math.max(1, chr.getParty().getPartyMembersInField(chr).size());
        int dmg = si.getValue(indieDamR, chr.getSkillLevel(SOUL_LINK)) * members;
        Option cur = tsm.getOptByCTSAndSkill(IndieDamR, SOUL_LINK);
        boolean changed = false;
        if (cur == null || cur.nOption != dmg) {
            tsm.putCharacterStatValue(IndieDamR, new Option(dmg, SOUL_LINK, 0));
            changed = true;
        }
        if (tsm.getOptByCTSAndSkill(MichaelSoulLink, SOUL_LINK) == null) {
            Option link = new Option(1, SOUL_LINK, 0);
            link.cOption = chr.getId();
            tsm.putCharacterStatValue(MichaelSoulLink, link);
            changed = true;
        }
        if (changed) {
            tsm.sendSetStatPacket();
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return id == JobConstants.JobEnum.NAMELESS_WARDEN.getJobId() || JobConstants.isMihile(id);
    }


    public void giveShieldOfLightBuff() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(RhoAias) || !chr.hasSkill(SHIELD_OF_LIGHT) || chr.getParty() == null) {
            shielfOfLightTimer.cancel(true);
            return;
        }
        Skill skill = chr.getSkill(getShieldOfLightId());
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();

        Rect rect = chr.getRectAround(si.getFirstRect());

        Set<Char> chrList = chr.getParty().getPartyMembersInSameField(chr);
        for (Char pChr : chrList) {
            TemporaryStatManager pTSM = pChr.getTemporaryStatManager();
            Option o = new Option();

            if (!rect.hasPositionInside(pChr.getPosition())) {
                pTSM.removeStatsBySkill(skill.getSkillId());
                continue;
            }
            o.nOption = si.getValue(x, slv); // dmg reduction
            o.rOption = skill.getSkillId();
            o.tOption = SHIELD_OF_LIGHT_AURA_SEC;
            o.xOption = chr.getId(); // Mihile Chr Id
            pTSM.putCharacterStatValue(RhoAias, o);
            pTSM.sendSetStatPacket();
        }

    }

    public void hitShieldOfLight() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option prevOption = tsm.getOption(RhoAias);
        Option o1 = new Option();

        o1.nOption = prevOption.nOption; // dmg reduction
        o1.rOption = prevOption.rOption;
        o1.tOption = (int) tsm.getRemainingTime(RhoAias, getShieldOfLightId());
        o1.xOption = chr.getId(); // Mihile Chr Id
        o1.cOption = ++prevOption.cOption; // total Hits absorbed
        o1.bOption = --prevOption.bOption; // Shield Hits Left
        o1.wOption = prevOption.wOption; // Shield Stage
        if (o1.bOption <= 0) {
            o1.wOption = prevOption.wOption + 1; // Shield Stage
            o1.bOption = getShieldOfLightHitCount(o1.wOption);
        }
        if (o1.wOption > SHIELD_OF_LIGHT_LAYERS) {
            tsm.removeStatsBySkill(getShieldOfLightId());
            return;
        }
        o1.setInMillis(true);
        tsm.putCharacterStatValue(RhoAias, o1, true);
        tsm.sendSetStatPacket();
    }

    // the HEXA common core upgrades Shield of Light; its buff runs under the HEXA id
    private int getShieldOfLightId() {
        return chr.hasSkill(HEXA_SHIELD_OF_LIGHT) ? HEXA_SHIELD_OF_LIGHT : SHIELD_OF_LIGHT;
    }

    // hits each layer blocks: y, w, z
    private int getShieldOfLightHitCount(int stage) {
        SkillInfo si = SkillData.getSkillInfoById(getShieldOfLightId());
        int slv = chr.getSkillLevel(getShieldOfLightId());
        switch (stage) {
            case 1:
                return si.getValue(y, slv);
            case 2:
                return si.getValue(w, slv);
            case 3:
                return si.getValue(z, slv);

            default:
                return 0;
        }
    }

    private void giveEndShieldBuff(int totalHitsAbsorbed) {
        if (!chr.hasSkill(SHIELD_OF_LIGHT)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        SkillInfo si = SkillData.getSkillInfoById(getShieldOfLightId());
        int slv = chr.getSkillLevel(getShieldOfLightId());
        // v265: a flat u% Final Damage for q2 sec (older data: w2 + the hits the shield took)
        int fdGiven = si.getValue(u, slv) > 0 ? si.getValue(u, slv) : si.getValue(w2, slv) + totalHitsAbsorbed;

        Option o = new Option();
        o.nOption = fdGiven;
        o.rOption = getShieldOfLightId();
        o.tOption = si.getValue(q2, slv);
        tsm.putCharacterStatValue(IndiePMdR, o);
        tsm.sendSetStatPacket();
    }

    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        switch (cts) {
            case RhoAias:
                if (tsm.getOption(cts).xOption == chr.getId()) {
                    int totalHitsAbsorbed = tsm.hasStat(RhoAias) ? tsm.getOption(RhoAias).cOption : 0;
                    EventManager.addEvent(() -> giveEndShieldBuff(totalHitsAbsorbed), 40, TimeUnit.MILLISECONDS);
                }
                break;
            case RoyalGuardState:
                var stack = SkillConstants.getStackByCTS(cts, o);
                var newStack = stack - 1;
                if (newStack > 0) {
                    giveRoyalGaurdStackBuff(newStack);
                }
                o.nOption = newStack;
                break;
            case IndieDamR:
                if (o.rOption == LIGHT_OF_COURAGE_BUFF || o.rOption == HEXA_LIGHT_OF_COURAGE) {
                    lightOfCourageFinisher();
                }
                break;
        }

        super.handleRemoveCTS(cts, o);
    }

    private void increaseRoyalGuardStackBuffDuration() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        SkillInfo si = SkillData.getSkillInfoById(SWORD_OF_LIGHT);
        int slv = chr.getSkillLevel(SWORD_OF_LIGHT);
        if (tsm.hasStat(RoyalGuardState)) {
            Option prevOption = tsm.getOption(RoyalGuardState);
            Option o1 = new Option();

            o1.nOption = prevOption.nOption;
            o1.xOption = prevOption.xOption;
            o1.bOption = prevOption.bOption;
            o1.rOption = prevOption.rOption;
            o1.tOption = (int) tsm.getRemainingTime(RoyalGuardState, o1.rOption) + (si.getValue(q, slv) * 1000);
            o1.tOption = Math.min(o1.tOption, royalGuardBuffSec() * 1000);
            o1.setInMillis(true);
            tsm.removeStatsBySkill(ROYAL_GUARD_STACK_BUFF);

            tsm.putCharacterStatValue(RoyalGuardState, o1, true);
            tsm.sendSetStatPacket();
        }
    }

    private void doRoyalGuard() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();

        o1.nOption = 1;
        o1.rOption = ROYAL_GUARD;
        o1.tOption = calcRoyalGuardTime();
        o1.setInMillis(true);
        tsm.putCharacterStatValue(RoyalGuardPrepare, o1, true);
        tsm.sendSetStatPacket();
    }

    private int calcRoyalGuardTime() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int royalGuardTime = 1450;
        int curStack = (tsm.hasStat(RoyalGuardState) ? tsm.getOption(RoyalGuardState).xOption : 0);
        royalGuardTime -= (curStack * 200);
        if (tsm.hasStatBySkillId(SACRED_CUBE)) {
            royalGuardTime += 500;
        }
        return royalGuardTime;
    }

    private void successfulGuard() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.removeStat(RoyalGuardPrepare);
        giveRoyalGuardBuff();
        giveSoulAsylumBuff();
        giveRoyalGuardStackedBuff();
        doRoyalGuardAttack();
        if (chr.hasSkillOnCooldown(SWORD_OF_LIGHT) && chr.hasSkillOnCooldown(SWORD_OF_LIGHT) && !chr.hasSkillOnCooldown(SWORD_OF_LIGHT_PASSIVE)) {
            chr.write(UserLocal.userBonusAttackRequest(SWORD_OF_LIGHT_PASSIVE));
        }
    }

    private void giveRoyalGuardBuff() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = 1;
        o.rOption = ROYAL_GUARD;
        o.tOption = SkillData.getSkillInfoById(ROYAL_GUARD_STACK_BUFF).getValue(dot, 1);
        tsm.putCharacterStatValue(NotDamaged, o);
        tsm.sendSetStatPacket();
    }

    private void giveSoulAsylumBuff() {
        if (chr.hasSkill(SOUL_ASYLUM)) {
            Skill skill = chr.getSkill(SOUL_ASYLUM);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();

            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            // y% less damage taken for time sec; Soul Majesty adds its w% while it lasts
            int reduce = si.getValue(y, slv);
            for (int majesty : new int[]{SOUL_MAJESTY, HEXA_SOUL_MAJESTY}) {
                if (tsm.hasStatBySkillId(majesty)) {
                    SkillInfo msi = SkillData.getSkillInfoById(majesty);
                    reduce += msi == null ? 0 : msi.getValue(w, Math.max(1, chr.getSkillLevel(majesty)));
                    break;
                }
            }
            tsm.removeStatsBySkill(skill.getSkillId());
            tsm.putCharacterStatValue(IndieDamReduceR, new Option(reduce, skill.getSkillId(), si.getValue(time, slv)));
            tsm.sendSetStatPacket();
        }
    }

    private void giveRoyalGuardStackedBuff() { //TempStat  Shield Attack is Effect
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int amount = 1;
        if (tsm.hasStat(RoyalGuardState)) {
            amount = tsm.getOption(RoyalGuardState).xOption;
            if (amount < royalGuardMaxCounter()) {
                amount++;
            }
        }
        giveRoyalGaurdStackBuff(amount);
    }

    private void giveRoyalGaurdStackBuff(int amount) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        Option o1 = new Option();

        var nOpt = Math.max(0, Math.min(royalGuardMaxCounter(), amount));
        if (nOpt > 0) {
            o.nOption = nOpt;
            o.xOption = nOpt;
            o.bOption = 4;
            o.rOption = ROYAL_GUARD_STACK_BUFF;
            o.tOption = royalGuardBuffSec();
            tsm.putCharacterStatValue(RoyalGuardState, o, true);
            o1.nOption = getRoyalGuardAttPower();
            o1.rOption = ROYAL_GUARD_STACK_BUFF;
            o1.tOption = o.tOption;
            tsm.putCharacterStatValue(PAD, o1, true);
            tsm.sendSetStatPacket();
        }
    }

    // Attack Power buff per counterattack: Royal Guard x sec
    private int royalGuardBuffSec() {
        return SkillData.getSkillInfoById(ROYAL_GUARD_STACK_BUFF).getValue(x, 1);
    }

    private int royalGuardMaxCounter() {
        int num = 3;
        if (chr.hasSkill(ROYAL_GUARD)) {
            num = 3;
        }
        if (chr.hasSkill(ADVANCED_ROYAL_GUARD)) {
            num = 5;
        }
        return num;
    }

    private int getRoyalGuardAttPower() {
        int pad = 0;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        // 1st-3rd time from Royal Guard, 4th-5th from Advanced Royal Guard
        SkillInfo rg = SkillData.getSkillInfoById(ROYAL_GUARD_STACK_BUFF);
        SkillInfo adv = SkillData.getSkillInfoById(ADVANCED_ROYAL_GUARD);
        int advLv = Math.max(1, chr.getSkillLevel(ADVANCED_ROYAL_GUARD));
        switch (tsm.getOption(RoyalGuardState).xOption) {
            case 1 -> pad = rg.getValue(dotTime, 1);
            case 2 -> pad = rg.getValue(dotInterval, 1);
            case 3 -> pad = rg.getValue(range, 1);
            case 4 -> pad = adv.getValue(w2, advLv);
            case 5 -> pad = adv.getValue(u, advLv);
        }
        return pad;
    }

    // Attack related methods ------------------------------------------------------------------------------------------

    // Radiant Soul skills: prop% (u% while Roiling Soul is on) to blind, accuracy -x% for time
    private void radiantSoulBlind(AttackInfo attackInfo, SkillInfo si, int slv) {
        if (si == null) {
            si = SkillData.getSkillInfoById(attackInfo.skillId);
            slv = Math.max(1, chr.getSkillLevel(attackInfo.skillId));
        }
        if (si == null) {
            return;
        }
        int chance = si.getValue(prop, slv);
        if (chr.getTemporaryStatManager().getOptByCTSAndSkill(Enrage, ROILING_SOUL) != null) {
            SkillInfo rsi = SkillData.getSkillInfoById(ROILING_SOUL);
            chance = Math.max(chance, rsi == null ? 100 : rsi.getValue(u, Math.max(1, chr.getSkillLevel(ROILING_SOUL))));
        }
        Option o = new Option(-si.getValue(x, slv), attackInfo.skillId, si.getValue(time, slv));
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob != null && Util.succeedProp(chance)) {
                mob.getTemporaryStat().addStatOptionsAndBroadcast(chr, MobStat.ACC, o.deepCopy());
            }
        }
    }

    @Override
    public net.swordie.ms.client.character.skills.BypassCooldownCheckType canBypassCooldownCheck(int skillId,
            AttackInfo attackInfo, SkillUseInfo sui, net.swordie.ms.client.character.skills.SkillUseSource source) {
        // Durendal's opening hits come in as attacks after the cast already started its cooldown
        if (skillId == DURENDAL && source.isAttackRequest()
                && System.currentTimeMillis() - durendalCastTime < 10000) {
            return net.swordie.ms.client.character.skills.BypassCooldownCheckType.BypassCheckAndCooldown;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }

        if (hasHitMobs) {
            chr.write(FieldPacket.registerExtraSkill(LIGHT_OF_COURAGE_BUFF, new ExtraSkillInfo(chr, LIGHT_OF_COURAGE_MOB_ATTACK)));
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case SHINING_PIERCE:
            case FOUR_POINT_ASSAULT:
            case SOUL_RELEASE:
            case RADIANT_CROSS_ASSAULT:
            case HEXA_RADIANT_CROSS_ASSAULT:
                radiantSoulBlind(attackInfo, si, slv);
                break;
            case DURENDAL: // Durendal and its follow-ups: blind at prop% for time
            case DURENDAL_2:
            case DURENDAL_3:
                radiantSoulBlind(attackInfo, null, 0);
                break;
            case RADIANT_CROSS:
            case HEXA_RADIANT_CROSS:
                radiantSoulBlind(attackInfo, si, slv);
                if (chr.hasSkill(RADIANT_CROSS_SPREAD)) {
                    Field field = chr.getField();
                    SkillInfo rca = SkillData.getSkillInfoById(RADIANT_CROSS_SPREAD);
                    AffectedArea aa = AffectedArea.getAffectedArea(chr, attackInfo);
                    aa.setSkillID(RADIANT_CROSS_SPREAD);
                    aa.setPosition(attackInfo.chrPos);
                    Rect rect = aa.getPosition().getRectAround(si.getRects().get(0));
                    if (!attackInfo.left) {
                        rect = rect.horizontalFlipAround(attackInfo.chrPos.getX());
                    }
                    aa.setRect(rect);
                    aa.setFlip(!attackInfo.left);
                    aa.setDelay((short) 7); //spawn delay
                    field.spawnAffectedAreaAndRemoveOld(aa);
                }
                break;
            case RADIANT_CROSS_SPREAD:
                int dur = si.getValue(time, slv);
                o1.nOption = -si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = dur;
                o2.nOption = -si.getValue(x, slv);
                o2.rOption = skillID;
                o2.tOption = dur / 2;
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.ACC, mob.isBoss() ? o2 : o1);
                }
                break;
            case CHARGING_LIGHT:
            case HEXA_CHARGING_LIGHT:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.FinalDmgReceived, o1);
                }
                break;
            case SWORD_OF_LIGHT_1:
            case SWORD_OF_LIGHT_2:
            case SWORD_OF_LIGHT_3:
            case SWORD_OF_LIGHT_4:
            case SWORD_OF_LIGHT_5:
                si = SkillData.getSkillInfoById(SWORD_OF_LIGHT);
                slv = chr.getSkillLevel(SWORD_OF_LIGHT);
                int duration = si.getValue(time, slv);
                o1.nOption = SWORD_OF_LIGHT_ACC;
                o1.rOption = skillID;
                o1.tOption = duration;
                o2.nOption = SWORD_OF_LIGHT_ACC;
                o2.rOption = skillID;
                o2.tOption = duration / 2;
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.ACC, mob.isBoss() ? o2 : o1);
                }
                increaseRoyalGuardStackBuffDuration();
                chr.addSkillCoolTime(SWORD_OF_LIGHT_PASSIVE, chr.getSkillStatValue(cooltime, SWORD_OF_LIGHT) * 1000);
                break;
            case SWORD_OF_LIGHT_PASSIVE:
                si = SkillData.getSkillInfoById(SWORD_OF_LIGHT);
                slv = chr.getSkillLevel(SWORD_OF_LIGHT);
                o1.nOption = SWORD_OF_LIGHT_ACC;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv) / 2;
                o2.nOption = SWORD_OF_LIGHT_ACC;
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.ACC, mob.isBoss() ? o1 : o2);
                }
                chr.addSkillCoolTime(SWORD_OF_LIGHT_PASSIVE, chr.getSkillStatValue(cooltime, SWORD_OF_LIGHT) * 1000);
                break;
            case LIGHT_OF_COURAGE_ATTACK:

                break;
        }
        if (hasHitMobs) {
            offensiveDefense(attackInfo);
        }

        super.handleAttack(c, attackInfo);
    }

    // Offensive Defense: Royal Guard's counter marks enemies for time; a Radiant Soul hit on a marked enemy sends the
    // light shockwave there (cooldown) and clears the mark
    private final java.util.Map<Integer, Long> offensiveDefenseMarks = new java.util.HashMap<>();

    private static boolean isRoyalGuardCounter(int skillId) {
        return (skillId >= ROYAL_GUARD && skillId <= ROYAL_GUARD_6) || skillId == ROYAL_GUARD_7 || skillId == ROYAL_GUARD_8
                || (skillId >= HEXA_ROYAL_GUARD && skillId <= HEXA_ROYAL_GUARD_2);
    }

    private static boolean isRadiantSoulSkill(int skillId) {
        return skillId == SHINING_PIERCE || skillId == FOUR_POINT_ASSAULT || skillId == SOUL_RELEASE
                || skillId == RADIANT_CROSS || skillId == HEXA_RADIANT_CROSS || skillId == RADIANT_CROSS_ASSAULT
                || skillId == HEXA_RADIANT_CROSS_ASSAULT || skillId == CHARGING_LIGHT || skillId == HEXA_CHARGING_LIGHT;
    }

    private void offensiveDefense(AttackInfo attackInfo) {
        boolean hexa = chr.hasSkill(HEXA_OFFENSIVE_DEFENSE);
        int passive = hexa ? HEXA_OFFENSIVE_DEFENSE : OFFENSIVE_DEFENSE;
        if (!chr.hasSkill(passive)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(passive);
        int slv = chr.getSkillLevel(passive);
        long now = Util.getCurrentTimeLong();
        offensiveDefenseMarks.values().removeIf(t -> t <= now);
        if (isRoyalGuardCounter(attackInfo.skillId)) {
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                offensiveDefenseMarks.put(mai.mobId, now + si.getValue(time, slv) * 1000L);
            }
            return;
        }
        int wave = hexa ? HEXA_OFFENSIVE_DEFENSE_WAVE : OFFENSIVE_DEFENSE_WAVE;
        if (!isRadiantSoulSkill(attackInfo.skillId) || chr.hasSkillOnCooldown(wave)) {
            return;
        }
        java.util.List<Integer> targets = new java.util.ArrayList<>();
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            if (offensiveDefenseMarks.remove(mai.mobId) != null) {
                targets.add(mai.mobId);
            }
        }
        if (!targets.isEmpty()) {
            chr.write(UserLocal.userBonusAttackRequest(wave, targets));
            // Soul Majesty: Light Shockwave every 2 s while it lasts
            boolean majesty = chr.getTemporaryStatManager().hasStatBySkillId(SOUL_MAJESTY)
                    || chr.getTemporaryStatManager().hasStatBySkillId(HEXA_SOUL_MAJESTY);
            chr.addSkillCoolTime(wave, majesty ? SOUL_MAJESTY_WAVE_COOLDOWN_MS
                    : Math.max(1, si.getValue(cooltime, slv)) * 1000);
        }
    }

    private void doRoyalGuardAttack() {
        chr.write(UserLocal.royalGuardAttack(true));
    }

    @Override
    public int getFinalAttackSkill() {
        Skill skill = getFinalAtkSkill();
        if (skill != null) {
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            int proc = si.getValue(prop, slv);
            if (Util.succeedProp(proc)) {
                return skill.getSkillId();
            }
        }
        return super.getFinalAttackSkill();
    }

    private Skill getFinalAtkSkill() {
        Skill skill = null;
        if (chr.hasSkill(FINAL_ATTACK_MIHILE)) {
            skill = chr.getSkill(FINAL_ATTACK_MIHILE);
        }
        if (chr.hasSkill(ADVANCED_FINAL_ATTACK_MIHILE)) {
            skill = chr.getSkill(ADVANCED_FINAL_ATTACK_MIHILE);
        }
        if (chr.hasSkill(HEXA_FINAL_ATTACK_MIHILE)) {
            skill = chr.getSkill(HEXA_FINAL_ATTACK_MIHILE);
        }
        return skill;
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Field field = chr.getField();
        switch (skillId) {
            case DURENDAL:
                durendalCastTime = System.currentTimeMillis();
                break;
            case MAGIC_CRASH:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                Rect rect = chr.getPosition().getRectAround(si.getRects().get(0));
                if (!chr.isLeft()) {
                    rect = rect.horizontalFlipAround(chr.getPosition().getX());
                }
                for (Life life : chr.getField().getLifesInRect(rect)) {
                    if (life instanceof Mob && ((Mob) life).getHp() > 0) {
                        Mob mob = (Mob) life;
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        if (Util.succeedProp(si.getValue(prop, slv))) {
                            mts.removeBuffs();
                            mts.addStatOptionsAndBroadcast(chr, MobStat.MagicCrash, o1);
                        }
                    }
                }
                break;

            case ROYAL_GUARD:   //BuffStat 'ShieldAttack'  has something to do with this skill
            case ROYAL_GUARD_2:
            case ROYAL_GUARD_3:
            case ROYAL_GUARD_4:
            case ROYAL_GUARD_5: // lasts 1450 millisec
            case HEXA_ROYAL_GUARD_3: // HEXA Royal Guard and its stacked forms
            case HEXA_ROYAL_GUARD:
            case HEXA_ROYAL_GUARD_4:
            case HEXA_ROYAL_GUARD_5:
            case HEXA_ROYAL_GUARD_6:
            case HEXA_ROYAL_GUARD_7:
                // 51001006  skill Id
                doRoyalGuard();
                chr.addSkillCoolTime(ROYAL_GUARD_STACK_BUFF, 6000);

                // TODO CTS ShieldAttack gives invincibility if nOpt > 0
                break;
            case SWORD_BOOSTER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case RALLY:
                o1.nOption = si.getValue(indiePad, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, o1);
                break;
            case ENDURING_SPIRIT:
                o1.nOption = si.getValue(x, slv) + (chr.hasSkill(ENDURING_SPIRIT_STEEL_SKIN) ? 20 : 0);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv) + (chr.hasSkill(ENDURING_SPIRIT_PERSIST) ? 20 : 0);
                tsm.putCharacterStatValue(IndiePDDR, o1);
                o2.nOption = si.getValue(y, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv) + (chr.hasSkill(ENDURING_SPIRIT_PERSIST) ? 20 : 0);
                tsm.putCharacterStatValue(IndieAsrR, o2);
                o3.nOption = si.getValue(z, slv) + (chr.hasSkill(ENDURING_SPIRIT_PREPARATION) ? 10 : 0);
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv) + (chr.hasSkill(ENDURING_SPIRIT_PERSIST) ? 20 : 0);
                tsm.putCharacterStatValue(IndieTerR, o3);
                break;
            case SOUL_LINK: // (ON/OFF)
                // damage +indieDamR% per party member in the map (alone counts as 1); the party shield is not done
                if (tsm.hasStatBySkillId(SOUL_LINK)) {
                    tsm.removeStatsBySkill(SOUL_LINK);
                    break;
                }
                int members = chr.getParty() == null ? 1 : Math.max(1, chr.getParty().getPartyMembersInField(chr).size());
                o1.nOption = si.getValue(indieDamR, slv) * members;
                o1.rOption = skillId;
                tsm.putCharacterStatValue(IndieDamR, o1);
                // the link itself (the client checks it for the toggle); c = the Mihile who owns it
                Option link = new Option(1, skillId, 0);
                link.cOption = chr.getId();
                tsm.putCharacterStatValue(MichaelSoulLink, link);
/*                Aura aura = field.getAurasByChr(chr).stream().filter(a -> a.getSkillID() == skillId).findFirst().orElse(null);
                if (aura != null) {
                    field.removeAura(aura);
                } else {
                    aura = Aura.getAuraBy(chr, skillId, slv);
                    aura.activateTimer(4000, 4000);
                    field.spawnAura(aura);
                }*/

                //o1.nOption = 1;
                //o1.rOption = skillID;
                //o1.cOption = getOwner().getId();

                // yOption = dmg Reduce
                //tsm.putCharacterStatValue(MichaelSoulLink, o1);
                break;
            case ROILING_SOUL:
                // v265: on/off, raises Radiant Soul blind chance to u% (its final/crit damage is passive now)
                if (tsm.getOptByCTSAndSkill(Enrage, ROILING_SOUL) != null) {
                    tsm.removeStatsBySkill(skillId);
                    break;
                }
                o1.nOption = 1;
                o1.rOption = skillId;
                tsm.putCharacterStatValue(Enrage, o1);
                break;
            case SOUL_MAJESTY:
            case HEXA_SOUL_MAJESTY:
                // resets Offensive Defense's Light Shockwave on use
                chr.resetSkillCoolTime(OFFENSIVE_DEFENSE_WAVE);
                chr.resetSkillCoolTime(HEXA_OFFENSIVE_DEFENSE_WAVE);
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case INSTALL_SHIELD:
            case HEXA_INSTALL_SHIELD: {
                net.swordie.ms.life.Summon shield = net.swordie.ms.life.Summon.getSummonBy(chr, skillId, slv);
                shield.setMoveAbility(net.swordie.ms.enums.MoveAbility.Stop);
                shield.setFlyMob(true);
                int extra = chr.getSkillStatValue(time, INSTALL_SHIELD_PERSIST);
                if (extra > 0) {
                    shield.setSummonTerm(shield.getSummonTerm() / 1000 + extra); // the getter is in ms, the setter takes seconds
                }
                chr.getField().spawnSummonAndRemoveOld(shield);
                break;
            }
            case QUEEN_OF_TOMORROW:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case SACRED_CUBE:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                o2.rOption = skillId;
                o2.nOption = si.getValue(indieMhpR, slv);
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMHPR, o2);
                o3.nOption = si.getValue(x, slv);
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DamAbsorbShield, o3);
                break;
            case SHIELD_OF_LIGHT:
            case HEXA_SHIELD_OF_LIGHT:
                if (tsm.hasStatBySkillId(skillId)) {
                    tsm.removeStatsBySkill(skillId);
                    return;
                }
                o1.nOption = si.getValue(x, slv); // dmg reduction
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                o1.xOption = chr.getId(); // Mihile Chr Id
                o1.cOption = 0; // total Hits absorbed
                o1.wOption = 1; // Shield Stage
                o1.bOption = si.getValue(y, slv); // Shield Hits Left
                tsm.putCharacterStatValue(RhoAias, o1);

                EventManager.stopTimer(shielfOfLightTimer);
                shielfOfLightTimer = EventManager.addFixedRateEvent(this::giveShieldOfLightBuff, 500, 500);
                break;
            case RADIANT_SOUL:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(SwordOfLight, o1);
                o2.rOption = skillId;
                o2.nOption = si.getValue(indieCr, slv);
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieCr, o2);
                o3.rOption = skillId;
                o3.nOption = si.getValue(indiePMdR, slv); // data has indiePMdR, no indiePadR
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o3);
                o4.rOption = skillId;
                o4.nOption = si.getValue(indieIgnoreMobpdpR, slv);
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieIgnoreMobpdpR, o4);
                break;
            case LIGHT_OF_COURAGE_BUFF:
            case HEXA_LIGHT_OF_COURAGE:
                si = SkillData.getSkillInfoById(skillId == HEXA_LIGHT_OF_COURAGE ? HEXA_LIGHT_OF_COURAGE_SI : LIGHT_OF_COURAGE_SI);
                o1.nOption = SkillData.getSkillInfoById(skillId).getValue(z, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
        }
        tsm.sendSetStatPacket();
    }

    private boolean hasLightOfCourageBuff() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        return tsm.hasAOptByCTSAndSkill(IndieDamR, LIGHT_OF_COURAGE_BUFF) || tsm.hasAOptByCTSAndSkill(IndieDamR, HEXA_LIGHT_OF_COURAGE);
    }

    private void lightOfCourageFinisher() {
        chr.write(UserLocal.userBonusAttackRequest(LIGHT_OF_COURAGE_MOB_ATTACK));
    }

    private void startSelfRecovery() {
        if (!chr.hasSkill(SELF_RECOVERY)) {
            return;
        }
        int interval = 5;
        EventManager.stopTimer(selfRecoveryTimer);
        if (interval > 0) {
            selfRecoveryTimer = EventManager.addFixedRateEvent(this::selfRecovery, interval, interval, TimeUnit.SECONDS);
        }
    }

    private void selfRecovery() {
        if (chr != null && chr.getField() != null && chr.hasSkill(SELF_RECOVERY)) {
            Skill skill = chr.getSkill(SELF_RECOVERY);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            int healHP = si.getValue(hp, slv) + (int) ((long) chr.getMaxHP() * si.getValue(x, slv) / 100);
            int healMP = si.getValue(mp, slv) + (int) ((long) chr.getMaxMP() * si.getValue(y, slv) / 100);
            chr.heal(healHP, true, false);
            chr.healMP(healMP);
        } else if (selfRecoveryTimer != null && !selfRecoveryTimer.isDone()) {
            selfRecoveryTimer.cancel(true);
        }
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(RoyalGuardPrepare)) {
            successfulGuard();
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    // Character creation related methods ---------------------------------------------------------------------------------------------
    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        chr.getAvatarData().getCharacterStat().setPosMap(LIMBERTS_GENERAL_STORE_FIELD);
    }

    @Override
    public void stopTimers() {
        if (selfRecoveryTimer != null) {
            selfRecoveryTimer.cancel(true);
        }
        if (shielfOfLightTimer != null) {
            shielfOfLightTimer.cancel(true);
        }
        EventManager.stopTimer(soulLinkTimer);
        super.stopTimers();
    }

    @Override
    public void updateTimerSkill() {
        EventManager.stopTimer(selfRecoveryTimer);
        startSelfRecovery();
        super.updateTimerSkill();
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        if (!net.swordie.ms.client.character.JobPathModule.classHandlesAdvancement(chr)) {
            return; // job path chosen with @job, or a reborn character: JobPathModule advances it
        }
        short level = chr.getLevel();
        if (level == 30) {
            chr.advanceJob(JobConstants.JobEnum.MIHILE_2.getJobId()); // + its advancement SP (once, also if a quest did it)
        } else if (level == 60) {
            chr.advanceJob(JobConstants.JobEnum.MIHILE_3.getJobId()); // + its advancement SP (once, also if a quest did it)
        } else if (level == 100) {
            chr.advanceJob(JobConstants.JobEnum.MIHILE_4.getJobId()); // + its advancement SP (once, also if a quest did it)
        }
    }
}

package net.swordie.ms.client.jobs.adventurer.pirate;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.skills.ExtraSkillInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.PartyBooster;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.debuff.DebuffInfo;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.shootobject.ShootObjectSkillInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatBase;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class Buccaneer extends Pirate {
    public static final int TORNADO_UPPERCUT = 5101012; //Special Attack
    public static final int CORKSCREW_BLOW = 5101004;
    public static final int KNUCKLE_BOOSTER = 5101006; //Buff
    public static final int PERSEVERANCE = 5100013;
    public static final int SEA_SERPENT = 5100017; // v271 passive (v265 toggle 5101017)
    public static final int SEA_SERPENT_BURST = 5101019;

    public static final int ROLL_OF_THE_DICE_BUCC = 5111007; //Buff
    public static final int TURNING_KICK = 5111002; //Special Attack
    public static final int STATIC_THUMPER = 5111012; //Special Attack
    public static final int STUN_MASTERY = 5110000; // Groggy Mastery
    public static final int SPIRAL_ASSAULT = 5111009;
    public static final int GREATER_SEA_SERPENT_I = 5110016;
    public static final int GREATER_SEA_SERPENT_I_BURST = 5111021;
    public static final int SERPENT_SCALE = 5111017;
    public static final int HEXA_SERPENT_SCALE = 5141011;
    public static final int SERPENT_ASSAULT = 5110018;
    public static final int SERPENT_ASSAULT_BURST = 5111019;


    public static final int OCTOPUNCH = 5121007; //Special Attack
    public static final int NAUTILUS_STRIKE_BUCC = 5121013; //Special Attack
    public static final int NAUTILUS_STRIKE_BUCC_FA = 5120021; // Final Attack
    public static final int HEXA_NAUTILUS_STRIKE = 5141009;
    public static final int HEXA_NAUTILUS_STRIKE_FA = 5140010;
    public static final int BUCCANEER_BLAST = 5121016; // Hook Bomber
    public static final int MAPLE_WARRIOR_BUCC = 5121000; //Buff
    public static final int PIRATE_REVENGE_BUCC = 5120011; // Defensive Stance
    public static final int GREATER_SEA_SERPENT_II = 5120022;
    public static final int SEA_SERPENT_BURST_II = 5121023;
    public static final int SEA_SERPENT_RAGE = 5121025;
    public static final int SERPENT_ASSAULT_RAGE = 5121027;
    public static final int AGGRESSIVE_STANCE = 5120028;
    public static final int ROLL_OF_THE_DICE_BUCC_ADDITION = 5120044;
    public static final int ROLL_OF_THE_DICE_BUCC_SAVING_GRACE = 5120043;
    public static final int ROLL_OF_THE_DICE_BUCC_ENHANCE = 5120045;
    public static final int HEROS_WILL_BUCC = 5121008;
    public static final int EPIC_ADVENTURER_BUCC = 5121053;
    public static final int POWER_UNITY = 5121052; // Serpent Spirit (v271: a passive, critical damage)
    public static final int POWER_UNITY_SKILL_REQUEST = 5121055; // v265 only
    public static final int STIMULATING_CONVERSATION = 5121054;
    public static final int ROLL_OF_THE_DICE_BUCC_DD = 5120012;
    public static final int TIME_LEAP = 5121010; //Special Move / Buff
    public static final int SPEED_INFUSION = 5121009; //Buff
    public static final int CROSSBONES = 5121015; //Buff


    // V skills
    public static final int MELTDOWN = 400051002; // Lightning Form
    public static final int MELTDOWN_ENERGY_ORB = 400051003;
    public static final int LORD_OF_THE_DEEP = 400051015;
    public static final int SERPENT_VORTEX = 400051042;
    public static final int HOWLING_FIST = 400051070;
    public static final int HOWLING_FIST_LEVIATHAN_ATTACK = 400051071;
    public static final int HEXA_OCTOPUNCH = 5141000;
    public static final int SUPER_OCTOPUNCH = 5141002;
    public static final int HEXA_SEA_SERPENTS_RAGE = 5141006;
    public static final int HEXA_SEA_SERPENT_BURST = 5141008;
    public static final int HEXA_SERPENT_ASSAULT = 5141013;
    public static final int HEXA_RAGING_SERPENT_ASSAULT = 5141014;
    public static final int HEXA_HOOK_BOMBER = 5141015;


    // Sea Serpent triggers: skillList (burst) and skillList2 (rage) of 5101017
    private static final Set<Integer> SEA_SERPENT_BURST_TRIGGERS = Set.of(5101012, 5111002, 5121016, 5141015);
    private static final Set<Integer> SEA_SERPENT_RAGE_TRIGGERS = Set.of(5121007, 5141000, 5141002);
    private static final Set<Integer> AGGRESSIVE_STANCE_TRIGGERS = Set.of(5101012, 5111002, 5111009, 5121007,
            NAUTILUS_STRIKE_BUCC, BUCCANEER_BLAST, NAUTILUS_STRIKE_BUCC_FA, HEXA_OCTOPUNCH, SUPER_OCTOPUNCH, HEXA_HOOK_BOMBER);
    private static final Set<Integer> DEFENSIVE_STANCE_TRIGGERS = Set.of(5101019, 5111021, 5121023, 5121025,
            SERPENT_ASSAULT_BURST, SERPENT_ASSAULT_RAGE, HEXA_SEA_SERPENT_BURST, HEXA_SEA_SERPENTS_RAGE, HEXA_SERPENT_ASSAULT, HEXA_RAGING_SERPENT_ASSAULT);

    private static final int GROGGY_MASTERY_STUN_SEC = 3; // hardcode-ok
    private static final int LEVIATHAN_INVINCIBLE_SEC = 3; // hardcode-ok: 400051071 q=3000 matches but isn't labelled
    private static final int MELTDOWN_INVINCIBLE_MS = 1500; // hardcode-ok

    private ScheduledFuture perseveranceTimer;
    private long nextSeaSerpentTime;
    private int lordOfTheDeepBossHits;

    public Buccaneer(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            EventManager.stopTimer(perseveranceTimer);
            perseveranceTimer = EventManager.addFixedRateEvent(this::applyPerseveranceTimer, 5, 5, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isBuccaneer(id);
    }

    private void applyPerseveranceTimer() {
        Skill skill = chr.getSkill(PERSEVERANCE);
        if (skill == null) {
            return;
        }
        chr.heal(chr.getHPPerc(chr.getSkillStatValue(x, PERSEVERANCE)), true, false);
        chr.healMP(chr.getMPPerc(chr.getSkillStatValue(x, PERSEVERANCE)));
    }

    public void increaseSerpentVortex() { // Serpent Vortex  Counter uses same CTS as StackOverTimeSkill.
        if (!chr.hasSkill(SERPENT_VORTEX)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int count = 1;
        if (tsm.hasStat(StackOverTimeSkill)) {
            count = tsm.getOption(StackOverTimeSkill).nOption;
            if (count < 6) {
                count++;
            }
        }
        updateSerpentVortex(count);
    }

    private void updateSerpentVortex(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = count;
        o.rOption = 0;
        tsm.putCharacterStatValue(StackOverTimeSkill, o);
        tsm.sendSetStatPacket();
    }


    // v attacks; q boss hits count as one
    private void lordOfTheDeepAttack(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(SerpentScrew)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(LORD_OF_THE_DEEP);
        int slv = chr.getSkillLevel(LORD_OF_THE_DEEP);
        boolean hitBoss = attackInfo.mobAttackInfo.stream().anyMatch(mai -> mai.mob != null && mai.mob.isBoss());
        if (hitBoss && ++lordOfTheDeepBossHits < Math.max(1, si.getValue(q, slv))) {
            return;
        }
        lordOfTheDeepBossHits = 0;
        int left = tsm.getOption(SerpentScrew).nOption - 1;
        if (left <= 0) {
            tsm.removeStatsBySkill(LORD_OF_THE_DEEP);
            return;
        }
        tsm.putCharacterStatValue(SerpentScrew, new Option(left, LORD_OF_THE_DEEP, 0), true);
        tsm.sendSetStatPacket();
    }

    private boolean isSkillLocked(int skillId) {
        Quest quest = chr.getQuestManager().getQuestById(QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2);
        return quest != null && quest.hasProperty(String.valueOf(skillId));
    }

    private void registerExtraSkill(int mainSkillId, int extraSkillId, int delay) {
        chr.write(FieldPacket.registerExtraSkill(mainSkillId,
                new ExtraSkillInfo(extraSkillId, chr.getPosition(), chr.isLeft(), delay, 1)));
    }

    // highest learned burst, or Sea Serpent's Rage (Greater Sea Serpent II) for Octopunch-type skills
    private void seaSerpent(int skillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        long now = Util.getCurrentTimeLong();
        if ((!tsm.hasStat(SeaSerpent) && !chr.hasSkill(SEA_SERPENT)) || now < nextSeaSerpentTime) {
            return;
        }
        int extraSkillId;
        if (SEA_SERPENT_RAGE_TRIGGERS.contains(skillId)) {
            if (!chr.hasSkill(GREATER_SEA_SERPENT_II)) {
                return;
            }
            extraSkillId = SEA_SERPENT_RAGE;
        } else if (SEA_SERPENT_BURST_TRIGGERS.contains(skillId)) {
            extraSkillId = chr.hasSkill(GREATER_SEA_SERPENT_II) ? SEA_SERPENT_BURST_II
                    : chr.hasSkill(GREATER_SEA_SERPENT_I) ? GREATER_SEA_SERPENT_I_BURST : SEA_SERPENT_BURST;
        } else {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(extraSkillId);
        int cooltimeMs = si == null ? 3000 : si.getValue(cooltime, Math.max(1, chr.getSkillLevel(SEA_SERPENT))) * 1000;
        if (tsm.hasStat(Stimulate)) {
            SkillInfo scSi = SkillData.getSkillInfoById(STIMULATING_CONVERSATION);
            cooltimeMs = cooltimeMs * (100 - scSi.getValue(y, 1)) / 100;
        }
        nextSeaSerpentTime = now + cooltimeMs;
        registerExtraSkill(SEA_SERPENT, extraSkillId, extraSkillId == SEA_SERPENT_RAGE ? 0 : 240);
        addSerpentStone(skillId);
    }

    private void addSerpentStone(int skillId) {
        if (!chr.hasSkill(SERPENT_SCALE)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        SkillInfo si = SkillData.getSkillInfoById(SERPENT_SCALE);
        int max = si.getValue(u, chr.getSkillLevel(SERPENT_SCALE));
        int stones = Math.min(max, (tsm.hasStat(SerpentStone) ? tsm.getOption(SerpentStone).nOption : 0) + 1);
        if (stones >= max && isSkillLocked(SERPENT_SCALE)) {
            // Auto Mode: fire the assault as soon as the stones are full
            serpentAssault(SEA_SERPENT_RAGE_TRIGGERS.contains(skillId));
            stones = 0;
        }
        tsm.putCharacterStatValue(SerpentStone, new Option(stones, SERPENT_SCALE, 0), true);
        tsm.sendSetStatPacket();
    }

    private void serpentAssault(boolean rage) {
        registerExtraSkill(SERPENT_ASSAULT, rage ? SERPENT_ASSAULT_RAGE : SERPENT_ASSAULT_BURST, 240);
    }

    private void stanceProcs(int skillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (chr.hasSkill(AGGRESSIVE_STANCE) && AGGRESSIVE_STANCE_TRIGGERS.contains(skillId)) {
            SkillInfo si = SkillData.getSkillInfoById(AGGRESSIVE_STANCE);
            int slv = chr.getSkillLevel(AGGRESSIVE_STANCE);
            tsm.putCharacterStatValue(IndieDamR, new Option(si.getValue(indieDamR, slv), AGGRESSIVE_STANCE, si.getValue(time, slv)));
        }
        if (chr.hasSkill(PIRATE_REVENGE_BUCC) && DEFENSIVE_STANCE_TRIGGERS.contains(skillId)
                && !chr.hasSkillOnCooldown(PIRATE_REVENGE_BUCC)) {
            SkillInfo si = SkillData.getSkillInfoById(PIRATE_REVENGE_BUCC);
            int slv = chr.getSkillLevel(PIRATE_REVENGE_BUCC);
            tsm.putCharacterStatValue(IndieHitDamageInclHPR, new Option(-si.getValue(y, slv), PIRATE_REVENGE_BUCC, si.getValue(x, slv)));
            chr.addSkillCoolTime(PIRATE_REVENGE_BUCC, si.getValue(cooltime, slv) * 1000);
        }
        tsm.sendSetStatPacket();
    }

    private void powerUnity() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        SkillInfo si = SkillData.getSkillInfoById(POWER_UNITY_SKILL_REQUEST);
        if (si == null || !chr.hasSkill(POWER_UNITY)) {
            return;
        }
        int amount = tsm.hasStat(UnityOfPower) ? Math.min(si.getValue(u, 1), tsm.getOption(UnityOfPower).nOption + 1) : 1;
        tsm.putCharacterStatValue(UnityOfPower, new Option(amount, POWER_UNITY_SKILL_REQUEST, si.getValue(time, 1)));
        tsm.sendSetStatPacket();
    }

    private void applyStunMasteryOnMob(AttackInfo attackInfo) {
        if (!chr.hasSkill(STUN_MASTERY)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(STUN_MASTERY);
        int slv = chr.getSkillLevel(STUN_MASTERY);
        Option o1 = new Option();
        o1.nOption = 1;
        o1.rOption = STUN_MASTERY;
        o1.tOption = GROGGY_MASTERY_STUN_SEC;

        DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds(), new DebuffInfo(true, si.getValue(subProp, slv)));
    }

    // Attack related methods ------------------------------------------------------------------------------------------

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

        // Stun Mastery
        applyStunMasteryOnMob(attackInfo);
        if (hasHitMobs && attackInfo.bySummonedID == 0) {
            int id = attackInfo.skillId;
            if ((tsm.getOptByCTSAndSkill(IndieBuffIcon, SERPENT_SCALE) != null || tsm.getOptByCTSAndSkill(IndieBuffIcon, HEXA_SERPENT_SCALE) != null)
                    && (SEA_SERPENT_BURST_TRIGGERS.contains(id) || SEA_SERPENT_RAGE_TRIGGERS.contains(id))) {
                serpentAssault(SEA_SERPENT_RAGE_TRIGGERS.contains(id));
                tsm.removeStatsBySkill(SERPENT_SCALE);
                tsm.removeStatsBySkill(HEXA_SERPENT_SCALE);
            }
            seaSerpent(id);
            stanceProcs(id);
            if (id == SEA_SERPENT_RAGE && tsm.hasStat(UnityOfPower)) {
                powerUnity();
            }
            if (id == LORD_OF_THE_DEEP) {
                lordOfTheDeepAttack(attackInfo);
            }
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (attackInfo.skillId) {
            case TURNING_KICK:
                si = SkillData.getSkillInfoById(TURNING_KICK);
                slv = chr.getSkillLevel(TURNING_KICK);
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null || mob.isBoss()) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                    }
                }
                break;
            case SERPENT_VORTEX:
                updateSerpentVortex(tsm.hasStat(StackOverTimeSkill) ? (tsm.getOption(StackOverTimeSkill).nOption - 1) : 0);
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        switch (skillId) {
            case HOWLING_FIST:
                var tsm = chr.getTemporaryStatManager();
                if (!tsm.hasStatBySkillId(HOWLING_FIST_LEVIATHAN_ATTACK)) {
                    var o = new Option(1, HOWLING_FIST_LEVIATHAN_ATTACK, LEVIATHAN_INVINCIBLE_SEC);
                    tsm.putCharacterStatValue(IndieNotDamaged, o, true);
                    tsm.sendSetStatPacket();
                }
                break;
        }
        super.handleSkillPrepareStop(chr, skillId, sui);
    }

    @Override
    public int getFinalAttackSkill() {
        if (chr.hasSkill(HEXA_NAUTILUS_STRIKE)) {
            SkillInfo hsi = SkillData.getSkillInfoById(HEXA_NAUTILUS_STRIKE);
            return Util.succeedProp(hsi.getValue(x, chr.getSkillLevel(HEXA_NAUTILUS_STRIKE))) ? HEXA_NAUTILUS_STRIKE_FA : 0;
        }
        if (chr.hasSkill(NAUTILUS_STRIKE_BUCC) && chr.hasSkillOnCooldown(NAUTILUS_STRIKE_BUCC)) {
            return NAUTILUS_STRIKE_BUCC_FA;
        }
        return super.getFinalAttackSkill();
    }


    public void handleShootObj(Char chr, ShootObjectSkillInfo sosi) {
        var skillId = sosi.getSkillId();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        switch (skillId) {
            case MELTDOWN_ENERGY_ORB:
                Option prevOpt = tsm.getOption(MeltDown);
                Option o = new Option();
                Option o1 = new Option();

                o.tOption = (int) tsm.getRemainingTime(MeltDown, MELTDOWN);
                o.nOption = prevOpt.nOption - 1;
                o.rOption = MELTDOWN;
                if (o.nOption == 0) {
                    o.nOption = -1;
                }
                o.setInMillis(true);

                tsm.putCharacterStatValue(MeltDown, o, true);
                tsm.sendSetStatPacket();

                o1.nOption = 1;
                o1.rOption = MELTDOWN;
                o1.tOption = MELTDOWN_INVINCIBLE_MS;
                o1.setInMillis(true);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                tsm.sendSetStatPacket();
                break;
        }
        super.handleShootObj(chr, sosi);
    }

    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillID, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillID, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillID);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillID);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillID) {
            case SEA_SERPENT:
                if (tsm.hasStat(SeaSerpent)) {
                    tsm.removeStatsBySkill(skillID);
                } else {
                    o1.nOption = 1;
                    o1.rOption = skillID;
                    tsm.putCharacterStatValue(SeaSerpent, o1);
                }
                break;
            case SERPENT_SCALE:
            case HEXA_SERPENT_SCALE:
                tsm.putCharacterStatValue(SerpentStone, new Option(0, SERPENT_SCALE, 0), true);
                o2.nOption = 1;
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBuffIcon, o2);
                break;
            case POWER_UNITY:
                if (!SkillData.getSkillInfoById(POWER_UNITY).isPsd()) {
                    powerUnity();
                }
                break;
            case TIME_LEAP:
                List<Char> chrs = new ArrayList<>() {{
                    add(chr);
                }};
                Party party = chr.getParty();
                if (party != null) {
                    chrs = party.getPartyMembersInField(chr);
                }
                for (Char pChr : chrs) {
                    if (!pChr.hasSkillOnCooldown(-TIME_LEAP)) {
                        for (var skillId : new ArrayList<>(pChr.getSkillCoolTimes().keySet())) {
                            if (!SkillConstants.isNoCooltimeResetSkill(skillId)) {
                                pChr.resetSkillCoolTime(skillId);
                            }
                        }
                        pChr.addSkillCoolTime(-TIME_LEAP, si.getValue(time, slv) * 1000);
                    }
                }
                // GMS: ViperTimeLeap n 1 for time sec (the cooldown-reset lockout)
                tsm.putCharacterStatValue(ViperTimeLeap, new Option(1, TIME_LEAP, si.getValue(time, slv)));
                break;
            case SPEED_INFUSION:
                net.swordie.ms.client.character.skills.PartyBooster pb = (PartyBooster) tsm.getTSBByTSIndex(TSIndex.PartyBooster);
                pb.setNOption(-2);
                pb.setROption(skillID);
                pb.setDynamicTermSet(true);
                pb.setHasPartyBooster(tsm.hasStat(PartyBooster));
                pb.setExpireTerm((int) (si.getValue(time, slv) * (chr.getTotalStat(BaseStat.buffTimeR) / 100D)));
                tsm.putCharacterStatValue(PartyBooster, pb.getOption());
                break;
            case CROSSBONES:
                o2.rOption = skillID;
                o2.nOption = si.getValue(indiePadR, slv);
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePADR, o2);
                break;
            case POWER_UNITY_SKILL_REQUEST:
                si = SkillData.getSkillInfoById(skillID);
                if (si == null) {
                    break;
                }
                slv = chr.getSkillLevel(POWER_UNITY);
                int amount = 1;
                if (tsm.hasStat(UnityOfPower)) {
                    amount = tsm.getOption(UnityOfPower).nOption;
                    if (amount < 4) {
                        amount++;
                    }
                }
                o1.nOption = amount;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(UnityOfPower, o1);
                break;
            case STIMULATING_CONVERSATION:
                var t = si.getValue(time, slv);
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = t;
                tsm.putCharacterStatValue(Stimulate, o1);
                o2.nOption = si.getValue(indieDamR, slv);
                o2.rOption = skillID;
                o2.tOption = t;
                tsm.putCharacterStatValue(IndieDamR, o2);
                break;
            case LORD_OF_THE_DEEP:
                lordOfTheDeepBossHits = 0;
                o1.nOption = si.getValue(v, slv);
                o1.rOption = skillID;
                tsm.putCharacterStatValue(SerpentScrew, o1, true);
                break;
            case MELTDOWN:
                o1.nOption = si.getValue(w, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(MeltDown, o1);
                o2.nOption = si.getValue(indiePMdR, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o2);
                break;
            case ROLL_OF_THE_DICE_BUCC_DD:
                int upbound = 6;
                if ((chr.hasSkill(ROLL_OF_THE_DICE_BUCC_DD) && chr.hasSkill(ROLL_OF_THE_DICE_BUCC_ADDITION)) ||
                        (chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_DD) && chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_ADDITION))) {
                    upbound = 7;
                }
                int diceThrow1 = new Random().nextInt(upbound) + 1;
                int diceThrow2 = new Random().nextInt(upbound) + 1;

                if ((chr.hasSkill(ROLL_OF_THE_DICE_BUCC_ENHANCE) || chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_ENHANCE)) && Util.succeedProp(DICE_ENHANCE_PROP)) {
                    diceThrow1 = new Random().nextInt(4) + 4;
                    diceThrow2 = new Random().nextInt(4) + 4;
                }

                int diceThrow3 = 1;
                if (chr.hasSkill(Job.LOADED_DICE)) {
                    if (chr.getQuestManager().getQuestById(GameConstants.LOADED_DICE_SELECTION) == null) {
                        chr.getScriptManager().createQuestWithQRValue(GameConstants.LOADED_DICE_SELECTION, "1");
                    }
                    diceThrow3 = Integer.parseInt(chr.getScriptManager().getQRValue(GameConstants.LOADED_DICE_SELECTION));

                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), -1, 1, false)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), diceThrow3, -1, false)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), diceThrow1, 0, true)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), diceThrow2, -1, true)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), -1, 2, false)));

                    o1.nOption = (diceThrow1 * 100) + (diceThrow2 * 10) + diceThrow3; // if rolled: 3, 5, and 6 the LoadedDice nOption = 356
                    o1.dOption = tsm.throwDice(diceThrow1, diceThrow2, diceThrow3);
                } else {
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillID, slv, diceThrow1, false)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillID, slv, diceThrow2, true)));
                    chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillID, slv, diceThrow1, false)));
                    chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillID, slv, diceThrow2, true)));

                    o1.nOption = (diceThrow1 * 10) + diceThrow2; // if rolled: 3 and 5, the DoubleDown nOption = 35
                    o1.dOption = tsm.throwDice(diceThrow1, diceThrow2);
                }
                if (diceThrow1 == 1 || diceThrow2 == 1 || diceThrow3 == 1) {
                    chr.reduceSkillCoolTime(skillID, (1000 * si.getValue(cooltime, slv)) / 2);
                }
                // Saving Grace Hyper Passive Skill
                if ((chr.hasSkill(ROLL_OF_THE_DICE_BUCC_SAVING_GRACE) || (chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_SAVING_GRACE))) && diceThrow1 != diceThrow2) {
                    if (Util.succeedProp(savingGraceProp())) {
                        chr.resetSkillCoolTime(skillID);
                    }
                }
                if (diceThrow1 == 1 && diceThrow2 == 1 && diceThrow3 == 1) {
                    return;
                }
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Dice, o1);
                tsm.sendSetStatPacket();
                break;
        }
        tsm.sendSetStatPacket();
    }

    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(skillId);
        if (skill != null) {
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());

            if (skillId == SPIRAL_ASSAULT && SkillChangeConstants.SPIRAL_ASSAULT_NO_CD) {
                return 0;
            }
        }
        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }


    // Hit related methods ---------------------------------------------------------------------------------------------


    @Override
    public void stopTimers() {
        EventManager.stopTimer(perseveranceTimer);
        super.stopTimers();
    }
}

package net.swordie.ms.client.jobs.resistance;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.ForceAtomInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatBase;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserPool;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.OpenGate;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.*;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.Mechanic;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;
import static net.swordie.ms.enums.ForceAtomEnum.*;

/**
 * Created on 12/14/2017.
 */
public class Mechanic extends Citizen {

    public static final int MECH_VEHICLE = 1932016;

    public static final int BOOSTER_ONLINE = 30001080;
    public static final int SECRET_ASSEMBLY = 30001281;
    public static final int MECHANIC_DASH = 30001068;
    public static final int HIDDEN_PEACE = 30000227;

    public static final int HUMANOID_MECH = 35001002; //Mech Suit
    public static final int ROCKET_BOOSTER = 35001006;
    public static final int TANK_MECH = 35111003; //Tank Mech Suit

    public static final int MECHANIC_RAGE = 35101006; //Buff
    public static final int PERFECT_ARMOR = 35101007; //Buff (ON/OFF)
    public static final int OPEN_PORTAL_GX9 = 35101005; //Special Skill
    public static final int ROBO_LAUNCHER_RM7 = 35101012; //Summon
    public static final int HOMING_BEACON = 35101002;

    public static final int ROCK_N_SHOCK = 35111002; //Special Summon
    public static final int ROLL_OF_THE_DICE = 35111013; //Special Buff
    public static final int SUPPORT_UNIT_HEX = 35111008; //Summon
    public static final int ADV_HOMING_BEACON = 35110017;

    public static final int ROBOT_MASTERY = 35120001;
    public static final int BOTS_N_TOTS = 35121009; //Special Summon
    public static final int BOTS_N_TOTS_SUB_SUMMON = 35121011; // Summon that spawn from the main BotsNtots
    public static final int MAPLE_WARRIOR_MECH = 35121007; //Buff
    public static final int ENHANCED_SUPPORT_UNIT = 35120002;
    public static final int SUPPORT_UNIT_HEX_PARTY_REINFORCE = 35120047;
    public static final int SUPPORT_UNIT_HEX_PERSIST = 35120048;
    public static final int HEROS_WILL_MECH = 35121008;
    public static final int HOMING_BEACON_RESEARCH = 35120017;
    public static final int ROLL_OF_THE_DICE_DD = 35120014; //Special Buff
    public static final int GIANT_ROBOT_SG_88 = 35121003;
    public static final int HEAVY_SALVO_PLUS = 35121015;
    public static final int AP_SALVO_PLUS = 35121016;
    public static final int AP_SALVO_PLUS_FA = 35121019;

    public static final int FOR_LIBERTY_MECH = 35121053;
    public static final int FULL_SPREAD = 35121055;
    public static final int DISTORTION_BOMB = 35121052;

    public static final int DOOMSDAY_DEVICE = 400051009;
    public static final int MOBILE_MISSILE_BATTERY = 400051017;
    public static final int FULL_METAL_BARRAGE = 400051041;
    public static final int MECHA_CARRIER = 400051068; // Summon
    public static final int MECHA_CARRIER_2 = 400051069; // SecondAtom
    public static final int HEXA_DISTORTION_BOMB = 35141004; // HEXA Distortion Bomb
    public static final int HEXA_ROBO_LAUNCHER_RM7 = 35141006; // HEXA Robo Launcher RM7
    public static final int HEXA_ROCK_N_SHOCK = 35141007; // HEXA Rock 'n Shock
    public static final int HEXA_BOTS_N_TOTS = 35141008; // HEXA Bots 'n Tots
    public static final int HEXA_BOTS_N_TOTS_SUB_SUMMON = 35141009;
    public static final int ROBO_CONVERSION_CB_P1 = 35141011; // toggle: RobotTransition r = this id
    public static final int CB_P1_NODEBOT = 35141012; // the nodebot summon; attacks with 35141013-15 by its level

    // no clear Skill.wz stat for these (Full Metal Barrage's hold limit w is 8)
    private static final int SUPPORT_UNIT_DEBUFF_SEC = 7; // hardcode-ok
    private static final int FULL_METAL_BARRAGE_SEC = 10; // hardcode-ok
    public static final int HEXA_AP_SALVO_PLUS = 35141002;
    public static final int GROUND_ZERO = 35141501;

    private static final int[] addedSkills = new int[]{
            SECRET_ASSEMBLY,
            MECHANIC_DASH,
            HIDDEN_PEACE,
    };

    private static final int[] homingBeacon = new int[]{
            HOMING_BEACON,
            ADV_HOMING_BEACON,
            HOMING_BEACON_RESEARCH,
    };

    public Mechanic(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isMechanic(id);
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
            slv = (byte) skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillID) {
            case DISTORTION_BOMB:
            case HEXA_DISTORTION_BOMB:
                if (attackInfo.attackHeader == OutHeader.REMOTE_MELEE_ATTACK) {
                    AffectedArea aa = AffectedArea.getAffectedArea(chr, attackInfo);
                    aa.setPosition(chr.getPosition());
                    Rect rect = aa.getPosition().getRectAround(si.getRects().get(0));
                    if (!attackInfo.left) {
                        rect = rect.horizontalFlipAround(aa.getPosition().getX());
                    }
                    aa.setRect(rect);
                    aa.setFlip(!attackInfo.left);
                    aa.setDelay((short) 6);
                    chr.getField().spawnAffectedArea(aa);
                }
                break;
        }

        super.handleAttack(c, attackInfo);
    }

    private void createMicroMissileForceAtoms(int skillId, int slv) {
        if (!chr.hasSkill(skillId)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        Field field = chr.getField();
        ForceAtomEnum fae = INVISIBLE_ATOM;
        List<Integer> targetList = new ArrayList<>();
        List<ForceAtomInfo> faiList = new ArrayList<>();
        int totalMobCount = si.getValue(mobCount, slv);
        for (int i = 0; i < totalMobCount; i++) {
            Mob mob = Util.getRandomFromCollection(field.getMobs());
            if (mob == null) {
                targetList.add(0);
                continue;
            }
            targetList.add(Util.getRandomFromCollection(field.getMobs()).getObjectId());
        }
        int totalMissileCount = si.getValue(bulletCount, slv);
        for (int i = 0; i < totalMissileCount; i++) {
            int fImpact = new Random().nextInt(36) + 15;
            int sImpact = new Random().nextInt(3) + 5;
            int delay = new Random().nextInt(500) + 2000;
            ForceAtomInfo fai = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), fImpact, sImpact,
                    new Random().nextInt(360), delay, Util.getCurrentTime(), 0, 0,
                    new Position());
            faiList.add(fai);
        }
        ForceAtom fa = new ForceAtom(chr.getId(), fae, targetList, skillId, faiList);
        fa.setPosition(new Position());
        chr.createForceAtom(fa);
    }

    private void createHumanoidMechRocketForceAtom(List<Integer> targetList) { // Humanoid Rockets are spread around
        Field field = chr.getField();
        SkillInfo si = SkillData.getSkillInfoById((chr.hasSkill(ADV_HOMING_BEACON) ? ADV_HOMING_BEACON : HOMING_BEACON));
        ForceAtomEnum fae = getHomingBeaconForceAtomEnum();
        var bulletCount = getHomingBeaconBulletCount();
        for (var i = 0; i < bulletCount; i++) {
            var target = 0;
            if (i < targetList.size()) {
                target = targetList.get(i);
            }
            Mob mob = (Mob) field.getLifeByObjectID(target);
            var mobId = mob == null ? 0 : mob.getObjectId();
            Position position = mob == null ? new Position() : mob.getPosition();
            ForceAtomInfo forceAtomInfo = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), Util.getRandom(33, 52), Util.getRandom(10, 18),
                    Util.getRandom(0, 10), Util.getRandom(150, 500), Util.getCurrentTime(), 1, 0,
                    new Position());
            chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae,
                    true, mobId, si.getSkillId(), forceAtomInfo, new Rect(), 90, 30,
                    position, 0, position, 0));
        }

    }

    private ForceAtomEnum getHomingBeaconForceAtomEnum() {
        switch (getHomingBeaconSkill().getSkillId()) {
            case ADV_HOMING_BEACON:
                return MECH_MEGA_ROCKET_1;
            case HOMING_BEACON_RESEARCH:
                return MECH_MEGA_ROCKET_2;
            case HOMING_BEACON:
            default:
                return MECH_ROCKET;
        }
    }

    private Skill getHomingBeaconSkill() {
        if (chr.hasSkill(HOMING_BEACON_RESEARCH)) {
            return chr.getSkill(HOMING_BEACON_RESEARCH);
        } else if (chr.hasSkill(ADV_HOMING_BEACON)) {
            return chr.getSkill(ADV_HOMING_BEACON);
        } else if (chr.hasSkill(HOMING_BEACON)) {
            return chr.getSkill(HOMING_BEACON);
        }
        return null;
    }

    private int getHomingBeaconBulletCount() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int forceAtomCount = 0;
        for (int skillId : homingBeacon) {
            if (skillId == ADV_HOMING_BEACON) {
                continue;
            }

            if (chr.hasSkill(skillId)) {
                Skill skill = chr.getSkill(skillId);
                SkillInfo si = SkillData.getSkillInfoById(skillId);
                int slv = skill.getCurrentLevel();
                forceAtomCount += si.getValue(bulletCount, slv);
            }
        }
        if (tsm.getOptByCTSAndSkill(BombTime, FULL_SPREAD) != null) {
            forceAtomCount += chr.hasSkill(FULL_SPREAD) ? SkillData.getSkillInfoById(FULL_SPREAD).getValue(x, chr.getSkill(FULL_SPREAD).getCurrentLevel()) : 0;
        }
        return forceAtomCount;
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
        TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.RideVehicle);
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        Option o6 = new Option();
        switch (skillId) {
            case GROUND_ZERO: { // Ground Zero's Massive Bombardment: the client asks for it after the Origin
                net.swordie.ms.life.AffectedArea zone = net.swordie.ms.life.AffectedArea.getPassiveAA(chr, skillId, Math.max(1, slv));
                chr.getField().spawnAffectedArea(zone);
                break;
            }
            case BOOSTER_ONLINE:
                int boosterSec = SkillData.getSkillInfoById(BOOSTER_ONLINE).getValue(time, 1) / 1000;
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = boosterSec;
                tsm.putCharacterStatValue(NewFlying, o1);
                o2.nOption = 1;
                o2.rOption = skillId;
                o2.tOption = boosterSec;
                tsm.putCharacterStatValue(IndieFloating, o2);
                break;
            case OPEN_PORTAL_GX9:
                Position position = inPacket.decodePosition();

                OpenGate openGate;
                if (getOpenGateById(0) == null) {
                    openGate = OpenGate.getOpenGateS(chr, position);
                } else if (getOpenGateById(1) == null) {
                    openGate = OpenGate.getOpenGateR(chr, position, getOpenGateById(0));
                } else {
                    openGate = field.getOpenGates().stream().min(Comparator.comparing(OpenGate::getTimeSpawned)).orElse(null);
                    if (openGate != null) {
                        field.removeLife(openGate.getObjectId(), true);
                        if (openGate.getOpenGateId() == 0) {
                            openGate = OpenGate.getOpenGateS(chr, position);
                            openGate.setRespectiveOpenGate(getOpenGateById(1));
                        } else if (openGate.getOpenGateId() == 1) {
                            openGate = OpenGate.getOpenGateR(chr, position, getOpenGateById(0));
                        }
                    }
                }

                if (openGate == null) {
                    break;
                }
                field.spawnLifeForTime(openGate, si.getValue(time, slv) * 1000);
                break;
            case HOMING_BEACON: //4
            case ADV_HOMING_BEACON: // 4thJob upgrade +5 -> 9
                byte size = inPacket.decodeByte();
                List<Integer> targetList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    targetList.add(inPacket.decodeInt());
                }
                if (targetList.size() > 0) {
                    createHumanoidMechRocketForceAtom(targetList);
                }
                break;
            case HUMANOID_MECH:
                tsm.removeStatsBySkill(TANK_MECH);
                o1.nOption = si.getValue(epad, slv);
                o1.rOption = skillId;
                tsm.putCharacterStatValue(EPAD, o1);
                o2.nOption = si.getValue(epdd, slv);
                o2.rOption = skillId;
                tsm.putCharacterStatValue(EPDD, o2);
                o3.nOption = slv;
                o3.rOption = skillId;
                tsm.putCharacterStatValue(Mechanic, o3);

                o4.nOption = si.getValue(indieSpeed, slv);
                o4.rOption = skillId;
                tsm.putCharacterStatValue(IndieSpeed, o4);
                o5.nOption = -1;
                o5.rOption = skillId;
                tsm.putCharacterStatValue(IndieBooster, o5);
                tsb.setNOption(MECH_VEHICLE);
                tsb.setROption(skillId);
                tsm.putCharacterStatValue(RideVehicle, tsb.getOption());
                break;
            case TANK_MECH:
                tsm.removeStatsBySkill(HUMANOID_MECH);
                o1.nOption = si.getValue(epad, slv);
                o1.rOption = skillId;
                tsm.putCharacterStatValue(EPAD, o1);
                o2.nOption = si.getValue(epdd, slv);
                o2.rOption = skillId;
                tsm.putCharacterStatValue(EPDD, o2);
                o3.nOption = 1;
                o3.rOption = skillId;
                tsm.putCharacterStatValue(Mechanic, o3);
                o4.nOption = si.getValue(cr, slv);
                o4.rOption = skillId;
                tsm.putCharacterStatValue(CriticalBuff, o4);
                tsb.setNOption(MECH_VEHICLE);
                tsb.setROption(skillId);
                tsm.putCharacterStatValue(RideVehicle, tsb.getOption());
                break;
            case MECHANIC_RAGE:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case PERFECT_ARMOR:
                if (tsm.hasStatBySkillId(skillId)) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    o1.nOption = si.getValue(x, slv);
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(Guard, o1);
                }
                break;
            case ROLL_OF_THE_DICE:
                int diceThrow1 = new Random().nextInt(6) + 1;

                chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));

                if (diceThrow1 < 2) {
                    chr.reduceSkillCoolTime(skillId, (1000 * si.getValue(cooltime, slv)) / 2);
                    return;
                }

                o1.nOption = diceThrow1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);

                o1.dOption = tsm.throwDice(diceThrow1);
                tsm.putCharacterStatValue(Dice, o1);
                break;
            case ROLL_OF_THE_DICE_DD:
                diceThrow1 = new Random().nextInt(6) + 1;
                int diceThrow2 = new Random().nextInt(6) + 1;
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
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillId, slv, diceThrow2, true)));
                    chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));
                    chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillId, slv, diceThrow2, true)));

                    o1.nOption = (diceThrow1 * 10) + diceThrow2; // if rolled: 3 and 5, the DoubleDown nOption = 35
                    o1.dOption = tsm.throwDice(diceThrow1, diceThrow2);
                }
                if (diceThrow1 == 1 || diceThrow2 == 1 || diceThrow3 == 1) {
                    chr.reduceSkillCoolTime(skillId, (1000 * si.getValue(cooltime, slv)) / 2);
                }
                if (diceThrow1 == 1 && diceThrow2 == 1 && diceThrow3 == 1) {
                    return;
                }
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Dice, o1);
                break;
            case ENHANCED_SUPPORT_UNIT:
            case SUPPORT_UNIT_HEX:
                Summon summon = Summon.getSummonByNoCTS(chr, skillId, slv);
                summon.setFlyMob(false);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.None);
                summon.setAttackActive(false);
                summon.setSummonTerm(si.getValue(SkillStat.time, slv) + chr.getSkillStatValue(time, SUPPORT_UNIT_HEX_PERSIST));
                field.spawnSummonAndRemoveOld(summon);
                break;
            case ROBO_CONVERSION_CB_P1:
                if (tsm.getOptByCTSAndSkill(RobotTransition, skillId) != null) {
                    tsm.removeStatsBySkill(skillId);
                    break;
                }
                summon = Summon.getSummonByNoCTS(chr, CB_P1_NODEBOT, slv);
                summon.setMoveAbility(MoveAbility.Walk);
                summon.setAssistType(AssistType.Attack);
                summon.setSummonTerm(0);
                field.spawnSummonAndRemoveOld(summon);
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = 0;
                o1.summon = summon;
                tsm.putCharacterStatValue(RobotTransition, o1);
                break;
            case ROBO_LAUNCHER_RM7:
            case HEXA_ROBO_LAUNCHER_RM7:
                if (skillId == HEXA_ROBO_LAUNCHER_RM7 && attachToNodebot(RobotTransitionRoboLuncherRM7, skillId, slv, si.getValue(time, slv))) {
                    break;
                }
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setFlyMob(false);
                summon.setMoveAbility(MoveAbility.Stop);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case ROCK_N_SHOCK:
            case HEXA_ROCK_N_SHOCK:
                // on the nodebot the coils last as long as a full set of three (y sec)
                if (skillId == HEXA_ROCK_N_SHOCK && attachToNodebot(RobotTransitionMagneticField, skillId, slv, si.getValue(y, slv))) {
                    break;
                }
                var opt = tsm.getOptByCTSAndSkill(IndieSummon, ROCK_N_SHOCK);
                inPacket.decodeInt();
                int rockNshockSize = inPacket.decodeByte();
                Summon rockNshock1 = null;
                Summon rockNshock2 = null;
                if (rockNshockSize == 2) {
                    rockNshock1 = (Summon) field.getLifeByObjectID(inPacket.decodeInt());
                    rockNshock2 = (Summon) field.getLifeByObjectID(inPacket.decodeInt());
                }
                summon = Summon.getSummonByNoCTS(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.None);
                var tOpt = summon.getSummonTerm() / 1000;
                summon.setSummonTerm(0);
                field.spawnSummon(summon);

                if (opt == null) {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    o1.tOption = tOpt;
                    o1.summons.add(summon);
                    tsm.putCharacterStatValue(IndieSummon, o1);
                } else {
                    opt.summons.add(summon);
                }

                if (rockNshockSize == 2) {
                    field.broadcastPacket(UserPool.teslaTriangle(chr.getId(), Arrays.asList(summon, rockNshock2, rockNshock1)));
                    chr.addSkillCoolTime(skillId, si.getValue(cooltime, slv) * 1000);
                }
                break;
            case GIANT_ROBOT_SG_88:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.AttackCounter);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case BOTS_N_TOTS:
            case HEXA_BOTS_N_TOTS:
                if (skillId == HEXA_BOTS_N_TOTS && attachToNodebot(RobotTransitionRoboFactory, skillId, slv, si.getValue(time, slv))) {
                    break;
                }
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.Summon);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case FOR_LIBERTY_MECH:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case FULL_SPREAD:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(BombTime, o1);
                break;
            case DOOMSDAY_DEVICE:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Walk);
                summon.setAssistType(AssistType.MultiSkills);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case MOBILE_MISSILE_BATTERY:
                summon = Summon.getSummonByNoCTS(chr, skillId, slv);
                summon.setFlyMob(false);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.None);
                field.spawnSummonAndRemoveOld(summon);
                createMicroMissileForceAtoms(skillId, slv);
                break;
            case MECHA_CARRIER:
                var pos = inPacket.decodePosition();
                summon = Summon.getSummonBy(chr, skillId, slv);
                // no stand action: the client falls back to fly only for fly move abilities (3, 4, 11, 15)
                summon.setMoveAbility(MoveAbility.MoveAbility15);
                summon.setAssistType(AssistType.SequenceAttack);
                summon.setPosition(pos);
                summon.setCount(si.getValue(x, slv));
                summon.setFlip(!chr.isLeft());
                field.spawnSummonAndRemoveOld(summon);
                break;
        }
        tsm.sendSetStatPacket();
    }

    /**
     * While CB-P1 is on, HEXA RM7 / Rock 'n Shock / Bots 'n Tots attach to the nodebot instead of being summoned:
     * their part stat (n 1) for the summon's time. The client draws each part on the nodebot and levels its attack
     * (35141013-15) by how many parts are on.
     */
    private boolean attachToNodebot(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat part, int skillId, int slv, int sec) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(RobotTransition) || sec <= 0) {
            return false;
        }
        long ms = sec * 1000L;
        if (!net.swordie.ms.constants.SkillConstants.isNoBuffDurationAppliedSkill(skillId)) {
            ms = ms * chr.getTotalStat(net.swordie.ms.enums.BaseStat.summonTimeR) / 100;
        }
        Option o = new Option(1, skillId, (int) ms);
        o.setInMillis(true);
        tsm.putCharacterStatValue(part, o, true);
        tsm.sendSetStatPacket();
        return true;
    }

    @Override
    public void handleRemoveCTS(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat cts, Option option) {
        if (cts == RobotTransition) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            for (var part : new net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat[]{
                    RobotTransitionRoboLuncherRM7, RobotTransitionMagneticField, RobotTransitionRoboFactory}) {
                if (tsm.hasStat(part)) {
                    tsm.removeStat(part);
                }
            }
        }
        super.handleRemoveCTS(cts, option);
    }

    public void doSupportUnitSkill(Summon summon) {
        var owner = summon.getChr();
        List<Char> partyChrs = new ArrayList<>() {{ add(owner); }};
        if (owner.getParty() != null) {
            partyChrs = owner.getParty().getPartyMembersInField(owner);
        }

        var skill = owner.getSkill(summon.getSkillID());
        var si = SkillData.getSkillInfoById(summon.getSkillID());
        var rect = summon.getRectAround(si.getFirstRect());

        if (skill == null) {
            return;
        }

        // Loop over party chrs
        for (var partyChr : partyChrs) {
            if (rect.hasPositionInside(partyChr.getPosition())) {
                buffsFromSupportUnit(owner, partyChr, summon, skill, si);
            }
        }

        // Loop over mobs
        debuffMobsBySupportUnit(summon, skill, si);
    }

    private void debuffMobsBySupportUnit(Summon summon, Skill skill, SkillInfo si) {
        var skillId = summon.getSkillID();
        if (skill != null) {
            var slv = skill.getCurrentLevel();

            // Mob Debuff
            var o = new Option();
            o.nOption = si.getValue(y, slv);
            o.rOption = skillId;
            o.tOption = SUPPORT_UNIT_DEBUFF_SEC;
            Field field = summon.getField();
            for (Mob mob : field.getMobs()) {
                MobTemporaryStat mts = mob.getTemporaryStat();
                mts.addStatOptionsAndBroadcast(chr, MobStat.PDR, o);
                mts.addStatOptionsAndBroadcast(chr, MobStat.MDR, o);
            }
        }
    }

    private void buffsFromSupportUnit(Char owner, Char affected, Summon summon, Skill skill, SkillInfo si) {
        var skillId = summon.getSkillID();
        if (skill != null) {
            var slv = skill.getCurrentLevel();

            // Healing
            int healrate = si.getValue(hp, slv);
            affected.heal((int) (affected.getMaxHP() * ((double) healrate / 100)));

            // Chr Buff (if ENHANCED_SUPPORT_HEX)
            if (skillId == ENHANCED_SUPPORT_UNIT) {
                TemporaryStatManager tsm = affected.getTemporaryStatManager();
                var nOpt = si.getValue(z, slv) + owner.getSkillStatValue(x, SUPPORT_UNIT_HEX_PARTY_REINFORCE);
                var o1 = new Option(nOpt, skillId, 6);
                tsm.putCharacterStatValue(IndiePMdR, o1);
                tsm.sendSetStatPacket();
            }
        }
    }

    public void createMechaCarrierSummons(Summon summon) {
        if (chr.hasSkillOnCooldown(MECHA_CARRIER_2)) {
            return;
        }

        var si = SkillData.getSkillInfoById(summon.getSkillID());
        var slv = chr.getSkillLevel(summon.getSkillID());
        var maxCount = si.getValue(z, slv);
        var count = Math.max(summon.getCount(), maxCount);

        List<SecondAtom> saList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var saSI = SkillData.getSkillInfoById(MECHA_CARRIER_2).getSecondAtomInfos().get(0);
            SecondAtom sa = SecondAtom.createFromInfo(chr, MECHA_CARRIER_2, saSI);
            sa.setPosition(summon.getPosition());
            sa.setFirstAngleRange(0);
            sa.setFirstAngleStart(1);
            sa.setCustomRotation(250);
            saList.add(sa);
        }
        chr.spawnSecondAtoms(saList);
        summon.setCount(summon.getCount() + si.getValue(y, slv));
        chr.addSkillCoolTime(MECHA_CARRIER_2, 2000);
    }

    @Override
    public void handleKeyDownSkill(Char chr, int skillID, InPacket inPacket) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        switch (skillID) {
            case FULL_METAL_BARRAGE:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = FULL_METAL_BARRAGE_SEC;
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                o2.nOption = 100; // hardcode-ok: ignores all reflect
                o2.rOption = skillID;
                o2.tOption = FULL_METAL_BARRAGE_SEC;
                tsm.putCharacterStatValue(IgnorePCounter, o2);
                tsm.sendSetStatPacket();
                break;
        }
        super.handleKeyDownSkill(chr, skillID, inPacket);
    }

    @Override
    public void handleCancelKeyDownSkill(Char chr, int skillID) {
        if (skillID == FULL_METAL_BARRAGE) {
            chr.addSkillCoolTime(HOMING_BEACON, 4000);
        }
        super.handleCancelKeyDownSkill(chr, skillID);
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        switch (skillId) {
            case BOOSTER_ONLINE:
                var tsm = chr.getTemporaryStatManager();
                tsm.removeStatsBySkill(skillId);
                break;
        }
        super.handleSkillPrepareStop(chr, skillId, sui);
    }

    public void summonBotsNTotsDogs(Summon parent) {
        for (int i = 0; i < 3; i++) {
            int subId = parent.getSkillID() == HEXA_BOTS_N_TOTS ? HEXA_BOTS_N_TOTS_SUB_SUMMON : BOTS_N_TOTS_SUB_SUMMON;
            Summon summon = Summon.getSummonByNoCTS(chr, subId, parent.getSlv());
            summon.setPosition(parent.getPosition());
            summon.setCurFoothold(parent.getCurFoothold());
            summon.setMoveAbility(MoveAbility.WalkRandom);
            summon.setAssistType(AssistType.Attack);
            summon.setTemplateId(0);
            summon.setFlip(parent.isFlip());
            parent.getField().spawnSummon(summon);
        }
    }

    public OpenGate getOpenGateById(int openGateId) {
        return chr.getField().getOpenGates().stream().filter(og -> og.getOwner() == chr && og.getOpenGateId() == openGateId).findFirst().orElse(null);
    }

    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        if (skillId == ROCK_N_SHOCK) {
            return 0;
        }

        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

}

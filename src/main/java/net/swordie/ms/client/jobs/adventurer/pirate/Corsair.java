package net.swordie.ms.client.jobs.adventurer.pirate;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.GuidedBullet;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.*;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class Corsair extends Pirate {
    public static final int THE_DREADNOUGHT = 5241500;
    public static final int THE_DREADNOUGHT_SHIP = 5241502;
    public static final int INFINITY_BLAST = 5200016; //Passive
    public static final int SCURVY_SUMMONS_MURAT = 5201012; // Summon & Skill Use Request
    public static final int SCURVY_SUMMONS_ATOM = 5201017; // Second atom fired by the crew


    public static final int ALL_ABOARD_MURAT = 5210015; // Dual pistol crewman, upgrades Scurvy Summons
    public static final int ALL_ABOARD_SHARPSHOOTER = 5211019; // Summon
    public static final int ALL_ABOARD_SHOOT = 5211020; // Sharpshooter attack
    public static final int ROLL_OF_THE_DICE_SAIR = 5211007; //Buff
    public static final int OCTO_CANNON = 5211014; //Summon


    public static final int PARROTARGETTING = 5221015; //Special Attack
    public static final int NAUTILUS_STRIKE_SAIR = 5221013; //Special Attack
    public static final int MAPLE_WARRIOR_SAIR = 5221000; //Buff
    public static final int PIRATE_REVENGE_SAIR = 5220012;
    public static final int ROLL_OF_THE_DICE_SAIR_DD = 5220014;
    public static final int ROLL_OF_THE_DICE_SAIR_ADDITION = 5220044;
    public static final int ROLL_OF_THE_DICE_SAIR_SAVING_GRACE = 5220043;
    public static final int ROLL_OF_THE_DICE_SAIR_ENHANCE = 5220045;
    public static final int HEROS_WILL_SAIR = 5221010;
    public static final int MAJESTIC_PRESENCE = 5220020;
    public static final int AHOY_MATEYS = 5220019;
    public static final int BROADSIDE_BLACK_BARK = 5221022; // Cast
    public static final int BROADSIDE_SUMMON = 5221027; // Summon, up to 2
    public static final int BROADSIDE_2 = 5221028; // Broadside
    public static final int FIRING_ORDERS = 5221029; // Summon

    public static final int JOLLY_ROGER = 5221018; //Buff
    public static final int QUICKDRAW = 5220055; //Passive
    public static final int RAPID_FIRE = 5221004;
    public static final int BRAIN_SCRAMBLER = 5221016;

    public static final int EPIC_ADVENTURER_SAIR = 5221053;
    public static final int WHALERS_POTION = 5221054;
    public static final int UGLY_BOMB = 5221052;


    // V skills
    public static final int BULLET_BARRAGE = 400051006;
    public static final int TARGET_LOCK = 400051021;
    private static final int TARGET_LOCK_INVINCIBLE_SEC = 2; // hardcode-ok: no Skill.wz stat
    public static final int NAUTILUS_ASSAULT = 400051040;
    public static final int NAUTILUS_ASSAULT_2 = 400051049;
    public static final int NAUTILUS_ASSAULT_3 = 400051050;
    public static final int DEATH_TRIGGER = 400051073;
    public static final int DEATH_TRIGGER_2 = 400051081;
    public static final int HEXA_NAUTILUS_STRIKE_SAIR = 5241018; // HEXA Nautilus Strike
    public static final int HEXA_OCTO_CANNON = 5241015; // HEXA Siege Bomber
    public static final int HEXA_OCTO_CANNON_2 = 5241016; // HEXA Siege Bomber
    public static final int HEXA_BROADSIDE_BLACK_BARK = 5241002; // HEXA Broadside
    public static final int HEXA_BROADSIDE_SUMMON = 5241003;
    public static final int HEXA_SCURVY_SUMMONS = 5241010;
    public static final int HEXA_FIRING_ORDERS = 5241009; // HEXA Firing Orders

    private int curDurationIncCount = 0;
    private ScheduledFuture targetLockTimer;
    private boolean canRegisterExtraSkillDeathTrigger = false;

    private static final List<Integer> scurvySummons = List.of(SCURVY_SUMMONS_MURAT, ALL_ABOARD_MURAT, ALL_ABOARD_SHARPSHOOTER);

    // skill ids the crew attacks with
    private static final List<Integer> allAboardSummons = List.of(SCURVY_SUMMONS_MURAT, SCURVY_SUMMONS_ATOM,
            ALL_ABOARD_MURAT, ALL_ABOARD_SHARPSHOOTER, ALL_ABOARD_SHOOT);

    private static final List<Integer> broadsideSummons = List.of(BROADSIDE_BLACK_BARK, BROADSIDE_SUMMON,
            HEXA_BROADSIDE_BLACK_BARK, HEXA_BROADSIDE_SUMMON);

    private static final List<Integer> corsairSummons = List.of(SCURVY_SUMMONS_MURAT, ALL_ABOARD_MURAT,
            ALL_ABOARD_SHARPSHOOTER, OCTO_CANNON, BROADSIDE_SUMMON, HEXA_BROADSIDE_SUMMON, FIRING_ORDERS);

    // attacks that use up a Quickdraw proc
    private static final List<Integer> quickdrawSkills = List.of(BRAIN_SCRAMBLER, UGLY_BOMB, NAUTILUS_STRIKE_SAIR, TARGET_LOCK);

    public Corsair(Char chr) {
        super(chr);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isCorsair(id);
    }

    // Scurvy Summons calls one crewman; with All Aboard learned it calls the sharpshooter and the dual pistol crewman
    private void scurvySummons(int slv) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Field field = chr.getField();
        scurvySummons.forEach(tsm::removeStatsBySkill);
        tsm.removeStatsBySkill(AHOY_MATEYS);
        List<Integer> crew = List.of(SCURVY_SUMMONS_MURAT);
        int crewSlv = slv;
        if (chr.hasSkill(ALL_ABOARD_MURAT)) {
            crew = List.of(ALL_ABOARD_SHARPSHOOTER, ALL_ABOARD_MURAT);
            crewSlv = chr.getSkillLevel(ALL_ABOARD_MURAT);
        }
        scurvySummons.stream().filter(id -> field.getSummonByChrAndSkillId(chr, id) != null)
                .forEach(id -> field.removeSummon(id, chr.getId()));
        // Ahoy Mateys buffs last as long as the crew
        int crewSec = SkillData.getSkillInfoById(crew.get(0)).getValue(time, crewSlv);
        SkillInfo ahoyInfo = chr.hasSkill(AHOY_MATEYS) ? SkillData.getSkillInfoById(AHOY_MATEYS) : null;
        int ahoySlv = chr.getSkillLevel(AHOY_MATEYS);
        for (int summonSkillID : crew) {
            Summon summon = Summon.getSummonBy(chr, summonSkillID, crewSlv);
            summon.setFlyMob(false);
            summon.setMoveAction((byte) 4);
            summon.setMoveAbility(MoveAbility.WalkSmart);
            summon.setAssistType(summonSkillID == ALL_ABOARD_SHARPSHOOTER ? AssistType.CreateB2BodyRequests : AssistType.SequenceAttack);
            summon.setAttackActive(true);
            field.spawnSummon(summon);

            Option link = new Option();
            link.nOption = summonSkillID;
            link.rOption = AHOY_MATEYS;
            link.tOption = summon.getSummonTerm();
            link.setInMillis(true);
            tsm.putCharacterStatValue(SpiritLink, link);

            if (ahoyInfo != null) {
                Option o1 = new Option();
                Option o2 = new Option();
                Option o3 = new Option();
                Option o4 = new Option();

                o1.nOption = ahoyInfo.getValue(z, ahoySlv);
                o1.rOption = summonSkillID;
                o1.tOption = crewSec;
                tsm.putCharacterStatValue(EnrageCrDamMin, o1);

                o2.nOption = ahoyInfo.getValue(s, ahoySlv);
                o2.rOption = summonSkillID;
                o2.tOption = crewSec;
                tsm.putCharacterStatValue(IndieCr, o2);

                o3.nOption = ahoyInfo.getValue(u, ahoySlv);
                o3.rOption = summonSkillID;
                o3.tOption = crewSec;
                tsm.putCharacterStatValue(IndieSpeed, o3);

                o4.nOption = ahoyInfo.getValue(y, ahoySlv);
                o4.rOption = summonSkillID;
                o4.tOption = crewSec;
                tsm.putCharacterStatValue(DamageReduce, o4);
            }
        }
        if (ahoyInfo != null) {
            Option o5 = new Option();
            o5.nOption = ahoyInfo.getValue(w, ahoySlv);
            o5.rOption = AHOY_MATEYS;
            o5.tOption = crewSec;
            tsm.putCharacterStatValue(IndiePAD, o5);
        }
        tsm.sendSetStatPacket();
    }

    // the dual pistol crewman's volley, asked for by the summon
    public void fireCrewAtoms(Summon summon) {
        SkillInfo si = SkillData.getSkillInfoById(SCURVY_SUMMONS_ATOM);
        if (si == null || si.getSecondAtomInfos().isEmpty() || si.getFirstRect() == null) {
            return;
        }
        int slv = Math.max(1, chr.getSkillLevel(SCURVY_SUMMONS_MURAT));
        Rect rect = chr.getRectAround(si.getFirstRect());
        if (!chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        Mob mob = Util.getRandomFromCollection(chr.getField().getMobsInRect(rect));
        int bulletCount = Math.max(1, si.getValue(SkillStat.bulletCount, slv));
        List<SecondAtom> atoms = new ArrayList<>();
        for (int i = 0; i < bulletCount; i++) {
            var sai = si.getSecondAtomInfos().get(Math.min(i, si.getSecondAtomInfos().size() - 1));
            SecondAtom atom = SecondAtom.createFromInfo(chr, SCURVY_SUMMONS_ATOM, sai);
            atom.setKey(i);
            atom.setTargetId(mob != null ? mob.getObjectId() : 0);
            Position pos = summon.getPosition().deepCopy();
            if (sai.getPosition() != null) {
                pos = pos.add(sai.getPosition());
            }
            atom.setPosition(pos);
            atoms.add(atom);
        }
        chr.spawnSecondAtoms(atoms);
    }

    public void extendBulletBarrageDuration() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(BulletParty)) {
            return;
        }
        Skill skill = chr.getSkill(BULLET_BARRAGE);
        if (skill == null) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        Option o1 = new Option();

        int maxDurationIncCount = si.getValue(y, slv);
        if (curDurationIncCount < maxDurationIncCount) {
            var remainingTime = tsm.getRemainingTime(BulletParty, skill.getSkillId());
            var tOpt = remainingTime + 1000;

            o1.nOption = 1;
            o1.rOption = skill.getSkillId();
            o1.tOption = (int) tOpt;
            o1.setInMillis(true);
            tsm.putCharacterStatValue(BulletParty, o1, true);
            tsm.sendSetStatPacket();
            curDurationIncCount++;
        }
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    public static final int HEXA_RAPID_FIRE = 5241000;
    public static final int HEXA_RAPID_FIRE_SHOOTOUT = 5241001;

    // HEXA Rapid Fire: every z hits reload a clip; at y clips (n = z * y) Shootout Mode (x 1), which spends 2 per hit
    private void rapidFireClips(int skillId) {
        if (skillId != HEXA_RAPID_FIRE && skillId != HEXA_RAPID_FIRE_SHOOTOUT) {
            return;
        }
        int slv = chr.getSkillLevel(HEXA_RAPID_FIRE);
        SkillInfo si = SkillData.getSkillInfoById(HEXA_RAPID_FIRE);
        if (slv <= 0 || si == null) {
            return;
        }
        var tsm = chr.getTemporaryStatManager();
        Option cur = tsm.hasStat(RapidFire) ? tsm.getOption(RapidFire) : null;
        int n = cur == null ? 0 : cur.nOption;
        int full = Math.max(1, si.getValue(z, slv) * si.getValue(y, slv));
        boolean shootout = cur != null && cur.xOption == 1;
        if (skillId == HEXA_RAPID_FIRE && !shootout) {
            n = Math.min(full, n + 1);
            shootout = n >= full;
        } else if (skillId == HEXA_RAPID_FIRE_SHOOTOUT) {
            n = Math.max(0, n - 2);
            shootout = n > 0;
        } else {
            return;
        }
        Option o = new Option(Math.max(n, 0), HEXA_RAPID_FIRE, 0);
        o.xOption = shootout ? 1 : 0;
        tsm.putCharacterStatValue(RapidFire, o);
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        if (!attackInfo.mobAttackInfo.isEmpty()) {
            rapidFireClips(attackInfo.skillId);
        }
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

        // Quickdraw
        if (hasHitMobs) {
            activateQuickdraw(attackInfo.skillId);
        }


        // [Corsair] Custom Skill Change
        if (hasHitMobs) {
            allAboardProcNautilusStrike(attackInfo);
            broadsideProcUglyBomb(attackInfo);
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (attackInfo.skillId) {
            case PARROTARGETTING:
                if (attackInfo.mobCount > 0) {
                    net.swordie.ms.client.character.skills.GuidedBullet gb = (GuidedBullet) tsm.getTSBByTSIndex(TSIndex.GuidedBullet);
                    var mobId = attackInfo.mobAttackInfo.get(0).mobId;
                    if (tsm.hasStat(GuidedBullet)) {
                        if (gb.getMobID() == mobId) {
                            break;
                        }
                        tsm.removeStat(GuidedBullet);
                    }

                    gb.setNOption(1);
                    gb.setROption(skillID);
                    gb.setDynamicTermSet(true);
                    gb.setMobID(mobId);
                    tsm.putCharacterStatValue(GuidedBullet, gb.getOption());
                    tsm.sendSetStatPacket();
                }
                break;
            case NAUTILUS_STRIKE_SAIR:
            case HEXA_NAUTILUS_STRIKE_SAIR:
                chr.reduceSkillCoolTime(SCURVY_SUMMONS_MURAT, (int) (chr.getRemainingCoolTime(SCURVY_SUMMONS_MURAT) * 0.5F));
                chr.reduceSkillCoolTime(ALL_ABOARD_MURAT, (int) (chr.getRemainingCoolTime(ALL_ABOARD_MURAT) * 0.5F));
                chr.reduceSkillCoolTime(BROADSIDE_BLACK_BARK, (int) (chr.getRemainingCoolTime(BROADSIDE_BLACK_BARK) * 0.5F));
                chr.reduceSkillCoolTime(ROLL_OF_THE_DICE_SAIR, (int) (chr.getRemainingCoolTime(ROLL_OF_THE_DICE_SAIR) * 0.5F));

                if (chr.getRemainingCoolTime(NAUTILUS_ASSAULT) < 8000) {
                    chr.addSkillCoolTime(NAUTILUS_ASSAULT, 8000);
                }
                break;
            case TARGET_LOCK:
                chr.addSkillCoolTime(attackInfo.skillId, si.getValue(cooltime, slv) * 1000);
                if (targetLockTimer != null && !targetLockTimer.isDone()) {
                    EventManager.stopTimer(targetLockTimer);
                }
                targetLockTimer = EventManager.addEvent(() -> chr.write(UserLocal.skillCooltimeSetM(TARGET_LOCK, 0)), si.getValue(cooltime, slv), TimeUnit.SECONDS);

                tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, TARGET_LOCK, TARGET_LOCK_INVINCIBLE_SEC));
                tsm.sendSetStatPacket();
                break;
            case DEATH_TRIGGER:
            case DEATH_TRIGGER_2:
                if (canRegisterExtraSkillDeathTrigger) {
                    ExtraSkillInfo esi = new ExtraSkillInfo(DEATH_TRIGGER_2, attackInfo.sui.position, attackInfo.left, 0, 1);
                    chr.write(FieldPacket.registerExtraSkill(skillID, esi));
                }
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    // [Corsair] Custom Skill Change
    private void allAboardProcNautilusStrike(AttackInfo attackInfo) {
        var skillId = attackInfo.skillId;
        for (var mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob == null) {
                continue;
            }

            if (mob.hasDamageReflect()) {
                return;
            }
        }
        if (SkillChangeConstants.SCURVY_SUMMON_PROC_NAUTILUS_STRIKE && !chr.hasSkillOnCooldown(NAUTILUS_STRIKE_SAIR) && allAboardSummons.stream().anyMatch(s -> s == skillId) && chr.hasSkill(NAUTILUS_STRIKE_SAIR)) {
            chr.write(UserLocal.userBonusAttackRequest(NAUTILUS_STRIKE_SAIR));
        }
    }

    // [Corsair] Custom Skill Change
    private void broadsideProcUglyBomb(AttackInfo attackInfo) {
        var skillId = attackInfo.skillId;
        for (var mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob == null) {
                continue;
            }

            if (mob.hasDamageReflect()) {
                return;
            }
        }
        if (SkillChangeConstants.BROADSIDE_PROC_UGLY_BOMB && !chr.hasSkillOnCooldown(UGLY_BOMB) && broadsideSummons.stream().anyMatch(s -> s == skillId) && chr.hasSkill(UGLY_BOMB)) {
            chr.write(UserLocal.userBonusAttackRequest(UGLY_BOMB));
        }
    }

    // a proc arms the next Brain Scrambler / Ugly Bomb / Nautilus Strike / Target Lock, which uses it up
    private void activateQuickdraw(int attackSkillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(QUICKDRAW)) {
            return;
        }
        if (tsm.hasStat(QuickDraw)) {
            if (quickdrawSkills.contains(attackSkillId)) {
                tsm.removeStatsBySkill(QUICKDRAW);
            }
            return;
        }
        SkillInfo quickdrawInfo = SkillData.getSkillInfoById(QUICKDRAW);
        int slv = chr.getSkillLevel(QUICKDRAW);
        if (quickdrawInfo != null && Util.succeedProp(quickdrawInfo.getValue(prop, slv))) {
            Option o = new Option();
            o.nOption = 1;
            o.rOption = QUICKDRAW;
            tsm.putCharacterStatValue(QuickDraw, o);
            tsm.sendSetStatPacket();
        }
    }

    @Override
    public int getFinalAttackSkill() {
        if (chr.hasSkill(MAJESTIC_PRESENCE)) {
            return MAJESTIC_PRESENCE;
        }
        return super.getFinalAttackSkill();
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
        Summon summon;
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        switch (skillId) {
            case THE_DREADNOUGHT_SHIP: // Origin follow-up summon, when the client uses its id
                if (chr.getSkillLevel(THE_DREADNOUGHT) > 0) {
                    Summon originSummon = Summon.getSummonBy(chr, THE_DREADNOUGHT_SHIP, chr.getSkillLevel(THE_DREADNOUGHT));
                    originSummon.setFlyMob(false);
                    originSummon.setMoveAbility(net.swordie.ms.enums.MoveAbility.Stop);
                    originSummon.setAssistType(net.swordie.ms.enums.AssistType.Attack);
                    chr.getField().spawnSummonAndRemoveOld(originSummon);
                }
                break;
            case JOLLY_ROGER:
                o2.rOption = skillId;
                o2.nOption = si.getValue(indiePadR, slv);
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePADR, o2);
                // v271 data: indieEva (GMS IndieEVA), no indieStance
                o3.rOption = skillId;
                o3.nOption = si.getValue(indieEva, slv);
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieEVA, o3);
                o4.rOption = skillId;
                o4.nOption = si.getValue(indieAsrR, slv);
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieAsrR, o4);
                o5.rOption = skillId;
                o5.nOption = si.getValue(indieTerR, slv);
                o5.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieTerR, o5);
                break;
            case WHALERS_POTION:
                o2.nOption = si.getValue(w, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DamageReduce, o2);
                chr.heal(chr.getHPPerc(si.getValue(z, slv)));
                break;
            case OCTO_CANNON: //Stationary, Attacks
            case HEXA_OCTO_CANNON:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 0);
                summon.setMoveAbility(MoveAbility.Stop);
                if (skillId == Jett.TURRET_DEPLOYMENT) {
                    field.spawnSummon(summon);
                } else {
                    field.spawnSummonAndRemoveOld(summon);
                }
                break;
            case ALL_ABOARD_MURAT:
            case SCURVY_SUMMONS_MURAT:
            case HEXA_SCURVY_SUMMONS: // HEXA crew ids aren't mapped yet: the 4th job crew comes
                scurvySummons(skillId == HEXA_SCURVY_SUMMONS ? Math.max(1, chr.getSkillLevel(SCURVY_SUMMONS_MURAT)) : slv);
                break;
            case BROADSIDE_BLACK_BARK:
            case HEXA_BROADSIDE_BLACK_BARK:
                var broadsideList = field.getSummons().stream()
                        .filter(sum -> sum.getChr() == chr && (sum.getSkillID() == BROADSIDE_SUMMON
                                || sum.getSkillID() == HEXA_BROADSIDE_SUMMON))
                        .sorted(Comparator.comparingInt(Summon::getObjectId))
                        .collect(Collectors.toList());

                removeBroadsideSummons(broadsideList); // at the cap of 2, the oldest ship leaves

                summon = Summon.getSummonBy(chr, skillId == HEXA_BROADSIDE_BLACK_BARK ? HEXA_BROADSIDE_SUMMON : BROADSIDE_SUMMON, slv);
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 5);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.CreateB2BodyRequests);
                summon.setAttackActive(true);
                if (inPacket != null && inPacket.getUnreadAmount() >= 5) {
                    Position position = inPacket.decodePosition();
                    boolean isLeft = inPacket.decodeByte() != 0;
                    if (Math.abs(position.getX() - chr.getPosition().getX()) < 1000
                            && Math.abs(position.getY() - chr.getPosition().getY()) < 1000) {
                        summon.setPosition(position);
                    }
                    summon.setFlip(!isLeft);
                }
                field.spawnSummon(summon);
                break;
            case FIRING_ORDERS:
            case HEXA_FIRING_ORDERS:
                // v271 Tempest Bombardment (same ids): a type-33 toggle without a summon node, no summon to spawn
                if (!SkillConstants.isSummon(skillId)) {
                    break;
                }
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 4);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.None);
                summon.setAttackActive(true);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case BULLET_BARRAGE:
                var addedDuration = 0;
                if (SkillChangeConstants.BULLET_PARTY_SACRIFICE_SUMMONS) {
                    var sumList = new ArrayList<>(field.getSummons());
                    for (var sum : sumList) {
                        if (sum != null && sum.getChr() == chr && corsairSummons.stream().anyMatch(s -> s == sum.getSkillID())) {
                            addedDuration += SkillChangeConstants.BULLET_PARTY_DURATION_GAIN_PER_SUMMON;
                            tsm.removeStatsBySkill(sum.getSkillID());
                            field.removeSummon(sum);
                        }
                    }

                    chr.resetSkillCoolTime(SCURVY_SUMMONS_MURAT);
                    chr.resetSkillCoolTime(ALL_ABOARD_MURAT);
                    chr.resetSkillCoolTime(OCTO_CANNON);
                    chr.resetSkillCoolTime(BROADSIDE_BLACK_BARK);
                }
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv) + addedDuration;
                tsm.putCharacterStatValue(BulletParty, o1);
                curDurationIncCount = 0;
                break;
        }
        tsm.sendSetStatPacket();
    }

    private void removeBroadsideSummons(List<Summon> broadsideList) {
        var field = chr.getField();

        if (broadsideList.size() >= 2) {
            var removedSummon = broadsideList.get(0);
            if (removedSummon != null) {
                var tsm = chr.getTemporaryStatManager();
                tsm.removeStatsBySkill(removedSummon.getSkillID());
                field.removeSummon(removedSummon);
            }
        }
    }

    @Override
    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        switch (skillId) {
            case DEATH_TRIGGER:
                if (!SkillChangeConstants.DEATH_TRIGGER_COOLDOWN_DECREASE) {
                    return -1;
                }
                var reducedCd = 0;
                Field field = chr.getField();
                for (var sum : field.getSummons()) {
                    if (sum != null && sum.getChr() == chr && corsairSummons.stream().anyMatch(s -> s == sum.getSkillID())) {
                        reducedCd += SkillChangeConstants.DEATH_TRIGGER_COOLDOWN_DECREASE_PER_SUMMON;
                    }
                }

                var cd = SkillData.getSkillInfoById(skillId).getValue(cooltime, slv) - reducedCd;
                return cd * 1000;
        }

        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }

    @Override
    public void handleSkillPrepareStart(Char chr, int skillId, SkillUseInfo sui) {
        if (skillId == NAUTILUS_ASSAULT) {
            if (chr.getRemainingCoolTime(NAUTILUS_STRIKE_SAIR) < 8000) {
                chr.addSkillCoolTime(NAUTILUS_STRIKE_SAIR, 8000);
            }
            chr.setSkillCooldown(skillId, sui.slv); // hardcode-ok
            var tsm = chr.getTemporaryStatManager();
            // invincible while casting: time (ms)
            var opt = new Option(1, skillId, SkillData.getSkillInfoById(skillId).getValue(time, sui.slv) / 1000);
            tsm.putCharacterStatValue(IndieNotDamaged, opt, true);
            tsm.sendSetStatPacket();
        }

        if (skillId == DEATH_TRIGGER) {
            chr.applyMpCon(skillId, sui.slv, new AttackInfo(), sui, SkillUseSource.SkillUseRequest);
            canRegisterExtraSkillDeathTrigger = true;
            chr.setSkillCooldown(skillId, sui.slv); // hardcode-ok
        }

        super.handleSkillPrepareStart(chr, skillId, sui);
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        if (skillId == DEATH_TRIGGER) {
            canRegisterExtraSkillDeathTrigger = false;
        }

        super.handleSkillPrepareStop(chr, skillId, sui);
    }

    @Override
    public int alterMpCon(int mpCon, int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        if (skillId == BULLET_BARRAGE) {
            return -1;
        }

        return super.alterMpCon(mpCon, skillId, slv, attackInfo, sui, source);
    }

    // Hit related methods ---------------------------------------------------------------------------------------------


    @Override
    public void stopTimers() {
        EventManager.stopTimer(targetLockTimer);
        super.stopTimers();
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        super.onWarp(oldField, newField);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(GuidedBullet)) {
            tsm.removeStat(GuidedBullet);
        }
        if (chr.hasSkill(Corsair.TARGET_LOCK) && !chr.hasSkillOnCooldown(Corsair.TARGET_LOCK)) {
            chr.write(UserLocal.skillCooltimeSetM(Corsair.TARGET_LOCK, 0)); // needs to be sent, to reset the passive effect
        }
    }
}

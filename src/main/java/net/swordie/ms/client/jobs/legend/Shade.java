package net.swordie.ms.client.jobs.legend;

import net.swordie.ms.client.jobs.SkillBuffs;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.debuff.DebuffInfo;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.info.*;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.MobData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Shade extends Job {
    public static final int FOX_GODS_FAVOR = 25001007; // booster
    public static final int LIVER = 2434851;

    public static final int SPIRIT_BOND_I = 20050285;
    public static final int FOX_TROT = 20051284;

    public static final int FOX_SPIRITS = 25101009; //Buff (ON/OFF)
    public static final int HEXA_FOX_SPIRITS = 25141001; // Buff (ON/OFF), replaces Fox Spirits on the quickslot
    public static final int FOX_SPIRITS_INIT = 25100009;
    public static final int FOX_SPIRITS_ATOM = 25100010;
    public static final int FOX_SPIRITS_ATOM_2 = 25120115; //Upgrade
    public static final int GROUND_POUND_FIRST = 25101000; //Special Attack (Slow Debuff)
    public static final int GROUND_POUND_SECOND = 25100001; //Special Attack (Slow Debuff)
    public static final int GROUND_POUND_MOB_DEBUFF = 25100002;
    public static final int BLADE_IMP_FORWARD = 25101004;
    public static final int BLADE_IMP_DOWNWARD = 25101003;
    public static final int BLADE_IMP_MOB_DEBUFF = 25100011;

    public static final int SUMMON_OTHER_SPIRIT = 25111209; //Passive Buff (Icon)
    public static final int SPIRIT_TRAP = 25111206; //Tile
    public static final int WEAKEN = 25110210; //Passive Debuff
    public static final int SPIRIT_FRENZY = 25111005; // summon (a wall of spirits)
    public static final int HEXA_SPIRIT_FRENZY = 25141007;
    public static final int SPIRIT_FRENZY_AA = 25111012; // spirit flow aa
    public static final int BLADE_IMP_SPIN_SLASH = 25111004;

    public static final int SPIRIT_WARD = 25121209; //Special Buff
    public static final int SPIRIT_WARD_V271 = 25120017; // what the v271 client sends for Spirit Ward
    public static final int SPIRIT_FRENZY_V271 = 25120018; // the v271 Spirit Frenzy (GMS summons with this id)
    public static final int MAPLE_WARRIOR_SH = 25121108; //Buff
    public static final int BOMB_PUNCH = 25121000;
    public static final int BOMB_PUNCH_FINAL = 25120003; //Special Attack (Stun Debuff)
    public static final int DEATH_MARK = 25121006; //Special Attack (Mark Debuff)
    public static final int SOUL_SPLITTER = 25121007; //Special Attack (Split)
    public static final int FIRE_FOX_SPIRIT_MASTERY = 25120110;
    public static final int FIRE_FOX_SPIRIT_SUMMON_CHANCE = 25120154;
    public static final int FIRE_FOX_SPIRIT_REPEAT_ATTACK = 25120153;
    public static final int HEROS_WILL_SH = 25121211;

    public static final int HEROIC_MEMORIES_SH = 25121132;
    public static final int SPIRIT_BOND_MAX = 25121131; // Fox Marble Fusion
    public static final int SPIRIT_INCARNATION = 25121030;
    public static final int SPIRIT_INCARNATION_AA = 25121055;

    public static final int SPIRIT_FLOW = 400051010;
    public static final int SPIRITGATE_SUMMONER = 400051022;
    public static final int SPIRITGATE_SUMMONS = 400051023;
    private static final int SPIRITGATE_ATOMS_PER_TRIGGER = 2; // guess: no GMS sample, the old summon count
    public static final int TRUE_SPIRIT_CLAW = 400051043;
    public static final int SMASHING_MULTIPUNCH_KEYDOWN = 400051078;
    public static final int SMASHING_MULTIPUNCH_FINALE = 400051079;
    public static final int HEXA_DEATH_MARK = 25141006; // HEXA Death Mark
    public static final int SPIRIT_REDEMPTION = 25111211;
    public static final int BLUE_FOX_MARBLE = 1353100;
    public static final int TEMPLE_OF_TIME_FIELD = 927030050;

    private static final int[] addedSkills = new int[]{
    };

    private long spiritWardTimer;
    private long lastSpiritFlowAttack;

    public Shade(Char chr) {
        super(chr);
        if (chr.getId() != 0 && isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            if (chr.getLevel() >= 200) {
                QuestManager qm = chr.getQuestManager();
                qm.completeQuest(QuestConstants.SPIRIT_BOND_MAX_QUEST_ID);
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isShade(id);
    }


    public void summonSpiritgateSummons(Position position) {
        if (!chr.hasSkill(SPIRITGATE_SUMMONER)) {
            return;
        }
        Field field = chr.getField();
        Skill skill = chr.getSkill(SPIRITGATE_SUMMONER);
        int slv = skill.getCurrentLevel();

        // v271: the gate's spirits are second atoms (400051023 has SecondAtom data and no summon node; spawning
        // summons for it crashed the client)
        SkillInfo si = SkillData.getSkillInfoById(SPIRITGATE_SUMMONS);
        if (field.getSummonByChrAndSkillId(chr, SPIRITGATE_SUMMONER) == null || si == null
                || si.getSecondAtomInfos().isEmpty()) {
            return;
        }
        Mob target = field.getMobs().stream()
                .min(Comparator.comparingDouble(m -> m.getPosition().distanceTo(position)))
                .orElse(null);
        List<SecondAtom> atoms = new ArrayList<>();
        for (int i = 0; i < SPIRITGATE_ATOMS_PER_TRIGGER; i++) {
            SecondAtom atom = SecondAtom.createFromInfo(chr, SPIRITGATE_SUMMONS, si.getSecondAtomInfos().get(0));
            atom.setKey(i);
            atom.setTargetId(target != null ? target.getObjectId() : 0);
            atom.setPosition(position.deepCopy());
            atoms.add(atom);
        }
        chr.spawnSecondAtoms(atoms);
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
        if (hasHitMobs) {
            if (attackInfo.skillId != FOX_SPIRITS_ATOM
                    && attackInfo.skillId != FOX_SPIRITS_ATOM_2
                    && Util.succeedProp(getFoxSpiritProc())) {
                createFoxSpiritForceAtom();
            }
            if (attackInfo.skillId != FOX_SPIRITS_ATOM && attackInfo.skillId != FOX_SPIRITS_ATOM_2) {
                foxMarbleOrbs(attackInfo);
            }
            applyWeakenOnMob(attackInfo, chr.getSkillLevel(WEAKEN));
            deathMarkDoTHeal(attackInfo);
        }
        if (attackInfo.skillId != FOX_SPIRITS_ATOM
                && attackInfo.skillId != FOX_SPIRITS_ATOM_2
                && attackInfo.skillId != SPIRITGATE_SUMMONS
                && attackInfo.skillId != SPIRIT_TRAP
                && Util.getCurrentTimeLong() > 2000 + lastSpiritFlowAttack) {
            doSpiritFlowBonusAttack();
        }

        if (isFoxMarbleFusionActive()) {
            dropLiver(attackInfo, skillID == SPIRIT_FRENZY || skillID == SPIRIT_FRENZY_AA);
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Field field = chr.getField();
        switch (attackInfo.skillId) {
            case BLADE_IMP_FORWARD:
            case BLADE_IMP_DOWNWARD:
            case BLADE_IMP_SPIN_SLASH:
                if (SkillData.getSkillInfoById(BLADE_IMP_MOB_DEBUFF) == null) {
                    break; // v271: no Blade Imp - Trap mark
                }
                o1.nOption = 1;
                o1.rOption = BLADE_IMP_MOB_DEBUFF;
                o1.tOption = si.getValue(time, slv);

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.FinalDmgReceived_Shade, o1, attackInfo.getMobIds());
                break;
            case GROUND_POUND_FIRST:
            case GROUND_POUND_SECOND:
                si = SkillData.getSkillInfoById(GROUND_POUND_MOB_DEBUFF);
                if (si == null) {
                    break; // the v265 data has no debuff skill for it
                }
                slv = (byte) chr.getSkillLevel(GROUND_POUND_FIRST);
                o1.nOption = -si.getValue(y, slv);
                o1.rOption = GROUND_POUND_MOB_DEBUFF;
                o1.tOption = si.getValue(time, slv);

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Speed, o1, attackInfo.getMobIds());
                break;
            case BOMB_PUNCH_FINAL:
                SkillInfo bpi = SkillData.getSkillInfoById(BOMB_PUNCH);
                byte bombPunchslv = (byte) chr.getSkill(BOMB_PUNCH).getCurrentLevel();
                o1.nOption = 1;
                o1.rOption = BOMB_PUNCH_FINAL;
                o1.tOption = bpi.getValue(time, bombPunchslv);

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds(), new DebuffInfo(true, bpi.getValue(prop, bombPunchslv)));
                break;
            case DEATH_MARK:
            case HEXA_DEATH_MARK:
                int healrate = si.getValue(x, slv);
                chr.heal((int) (chr.getMaxHP() / ((double) 100 / healrate)));
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(dotTime, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = mai.mob;
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.DebuffHealing, o1);
                    mts.createAndAddBurnedInfo(chr, skill);
                }
                break;
            case SOUL_SPLITTER:
                o1.nOption = 50;
                o1.tOption = si.getValue(time, slv);
                o1.wOption = 70;
                o1.uOption = SOUL_SPLITTER;
                o1.chr = chr;

                o2.nOption = 50;
                o2.tOption = si.getValue(time, slv);
                o2.wOption = 70;
                o2.uOption = SOUL_SPLITTER;
                o2.chr = chr;
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob origin = mai.mob;
                    if (origin == null
                            || origin.isSplit()
                            || (origin.getTemporaryStat().hasCurrentMobStat(MobStat.SeperateSoulC) || origin.getTemporaryStat().hasCurrentMobStat(MobStat.SeperateSoulP))
                            || (origin.getCopyMob() != null || origin.getOriginMob() != null)
                            || mai.mobDies) {
                        continue;
                    }
                    Mob copy = MobData.getMobDeepCopyById(origin.getTemplateId());
                    copy.setPosition(origin.getPosition());
                    copy.setMaxHp(origin.getMaxHp());
                    copy.setMaxMp(origin.getMaxMp());
                    copy.setHp(origin.getHp());
                    copy.setMp(origin.getMp());
                    copy.setNotRespawnable(true);
                    copy.setSplit(true);
                    copy.setField(field);
                    field.spawnLife(copy, null);

                    MobTemporaryStat mtsCopy = copy.getTemporaryStat();
                    o1.rOption = origin.getObjectId();
                    mtsCopy.addStatOptionsAndBroadcast(chr, MobStat.SeperateSoulC, o1);

                    MobTemporaryStat mts = origin.getTemporaryStat();
                    o2.rOption = copy.getObjectId();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.SeperateSoulP, o2);
                }
                break;
            case SPIRIT_INCARNATION:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(NotDamaged, o1);
                tsm.sendSetStatPacket();
                break;
            case SPIRITGATE_SUMMONS:
                si = SkillData.getSkillInfoById(SPIRITGATE_SUMMONER);
                slv = (byte) chr.getSkillLevel(SPIRITGATE_SUMMONER);
                o1.rOption = SPIRITGATE_SUMMONS;
                o1.tOption = si.getValue(s2, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    int stack = 0;
                    if (mts.hasCurrentMobStat(MobStat.SpiritGate)) {
                        stack = mts.getCurrentOptionsByMobStat(MobStat.SpiritGate).nOption;
                    }
                    if (stack < si.getValue(q2, slv)) {
                        stack++;
                    }
                    o1.nOption = stack * si.getValue(v2, slv);
                    o1.xOption = stack * si.getValue(q, slv);
                    mts.addStatOptionsAndBroadcast(chr, MobStat.SpiritGate, o1);
                }
                break;
            case SMASHING_MULTIPUNCH_KEYDOWN:
                // Spawn for spirit per attack
                var targetId = Util.getRandomFromCollection(attackInfo.mobAttackInfo).mobId;
                createFoxSpiritForceAtom(targetId);
                break;
            case SMASHING_MULTIPUNCH_FINALE:
                if (attackInfo.sui.multiAttackInfoSequenceCounter == 1 && hasHitMobs) { // Last Attack Register from Finale
                    // Spawn 15 Fox Spirits
                    si = SkillData.getSkillInfoById(SMASHING_MULTIPUNCH_KEYDOWN);
                    slv = chr.getSkillLevel(SMASHING_MULTIPUNCH_KEYDOWN);
                    var foxSpiritsSummoned = si.getValue(v2, slv);
                    for (int i = 0; i < foxSpiritsSummoned; i++) {
                        targetId = Util.getRandomFromCollection(attackInfo.mobAttackInfo).mobId;
                        createFoxSpiritForceAtom(targetId);
                    }
                }
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    @Override
    public void handleRemoveMobStat(MobStat mobStat, Mob mob, Option option) {
        if (mobStat == MobStat.SeperateSoulC && mob != null) {
            mob.getField().broadcastPacket(MobPool.leaveField(mob.getObjectId(), DeathType.ANIMATION_DEATH));
            mob.getField().removeLife(mob.getObjectId());
        }
        super.handleRemoveMobStat(mobStat, mob, option);
    }

    private void doSpiritFlowBonusAttack() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(SPIRIT_FLOW) || !tsm.hasStatBySkillId(SPIRIT_FLOW)) {
            return;
        }
        Skill skill = chr.getSkill(SPIRIT_FLOW);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        Map<Integer, Integer> randomSkillMap = Util.getRandomFromCollection(si.getRandomSkills());
        if (randomSkillMap == null) {
            return; // no random skill list in this version's data
        }
        List<Tuple<Integer, Integer>> randomSkillTuples = new ArrayList<>();
        randomSkillMap.forEach((k, v) -> randomSkillTuples.add(new Tuple<>(k, v)));
        // v271 has no delay-list packet (1410 is something else): one delayed bonus attack each
        randomSkillTuples.forEach(t -> chr.write(UserLocal.delayedBonusAttackRequest(t.getLeft(), t.getRight())));
        lastSpiritFlowAttack = Util.getCurrentTimeLong();
    }

    private void createFoxSpiritForceAtom() {
        createFoxSpiritForceAtom(0);
    }

    private void createFoxSpiritForceAtom(int targetId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        boolean hasSpiritBondMaxEnhanced = isFoxMarbleFusionActive(); // [Shade] Custom Skill Change
        var skillID = getFoxSpiritSkill();

        if (skillID <= 0 || !tsm.hasStat(HiddenPossession)) {
            return;
        }

        int recreationCount = 3;
        int atomid = FOX_SPIRITS_ATOM;
        ForceAtomEnum fae = ForceAtomEnum.RABBIT_ORB;

        if (skillID == FIRE_FOX_SPIRIT_MASTERY) {
            atomid = FOX_SPIRITS_ATOM_2;
            fae = ForceAtomEnum.FLAMING_RABBIT_ORB;
        }
        if (chr.hasSkill(FIRE_FOX_SPIRIT_REPEAT_ATTACK)) {
            recreationCount += 1;
        }

        // [Shade] Custom Skill Change
        if (hasSpiritBondMaxEnhanced && SkillChangeConstants.PRE_PORT_CUSTOMS) {
            recreationCount += SkillChangeConstants.SBM_RECREATION_COUNT_INCREASE;
        }

        ForceAtomInfo forceAtomInfo = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 18, 6,
                Util.getRandom(290, 320), Util.getRandom(250, 850), Util.getCurrentTime(), 1, 0,
                new Position(chr.isLeft() ? 0 : -50, -50));
        ForceAtom fa = new ForceAtom(false, 0, chr.getId(), fae,
                true, targetId, atomid, forceAtomInfo, new Rect(), 0, 300,
                new Position(), atomid, new Position(), 0);
        fa.setMaxRecreationCount(recreationCount);
        chr.createForceAtom(fa);
    }

    public void applyWeakenOnMob(AttackInfo attackInfo, int slv) {
        if (chr.hasSkill(WEAKEN)) {
            Option o1 = new Option();
            Option o2 = new Option();
            Option o3 = new Option();
            SkillInfo si = SkillData.getSkillInfoById(WEAKEN);
            o1.nOption = si.getValue(x, slv);
            o1.rOption = WEAKEN;
            o1.tOption = si.getValue(time, slv);
            o2.nOption = si.getValue(y, slv);
            o2.rOption = WEAKEN;
            o2.tOption = si.getValue(time, slv);
            o3.nOption = si.getValue(z, slv);
            o3.rOption = WEAKEN;
            o3.tOption = si.getValue(time, slv);

            var msMap = Map.of(
                    MobStat.Weakness, o1,
                    MobStat.ACC, o2,
                    MobStat.EVA, o3
            );
            DebuffUtil.applyDebuffOnMobs(chr, msMap, attackInfo.getMobIds(), new DebuffInfo(si.getValue(prop, slv)));
        }
    }

    public void deathMarkDoTHeal(AttackInfo attackInfo) {
        Skill skill = chr.getSkill(DEATH_MARK);
        if (skill != null) {
            int slv = skill.getCurrentLevel();
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int healrate = si.getValue(x, slv);
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                MobTemporaryStat mts = mob.getTemporaryStat();
                if (mts.hasBurnFromSkillAndOwner(DEATH_MARK, chr.getId())) {
                    chr.heal((int) (chr.getMaxHP() / ((double) 100 / healrate)));
                }
            }
        }
    }

    // newer data has no fire fox atom or separate passive: Fox Spirits itself holds the proc chance
    private int getFoxSpiritSkill() {
        if (chr.hasSkill(FIRE_FOX_SPIRIT_MASTERY) && SkillData.getSkillInfoById(FOX_SPIRITS_ATOM_2) != null) {
            return FIRE_FOX_SPIRIT_MASTERY;
        } else if (chr.hasSkill(FOX_SPIRITS_INIT)) {
            return FOX_SPIRITS_INIT;
        } else if (chr.hasSkill(HEXA_FOX_SPIRITS)) {
            return HEXA_FOX_SPIRITS;
        } else if (chr.hasSkill(FOX_SPIRITS)) {
            return FOX_SPIRITS;
        }
        return 0;
    }

    private int getFoxSpiritProc() {
        int proc = 0;
        if (getFoxSpiritSkill() == 0) {
            return proc;
        }
        proc = chr.getSkillStatValue(prop, getFoxSpiritSkill());
        if (chr.hasSkill(FIRE_FOX_SPIRIT_SUMMON_CHANCE)) {
            proc += chr.getSkillStatValue(prop, FIRE_FOX_SPIRIT_SUMMON_CHANCE);
        }
        return Math.min(100, proc);
    }

    private void dropLiver(AttackInfo attackInfo, boolean applyCooltime) {
        if (SkillChangeConstants.SBM_TIME_INSTEAD_LIVER || attackInfo.mobCount <= 0 || chr.hasSkillOnCooldown(LIVER) || !Util.succeedProp(10)) {
            return;
        }
        Item liver = ItemData.getItemDeepCopy(LIVER);
        Field field = chr.getField();
        Mob mob = Util.getRandomFromCollection(attackInfo.mobAttackInfo).mob;
        if (mob == null) {
            return;
        }
        Drop drop = new Drop(-1, liver, true);
        drop.setOwnerID(chr.getId());
        drop.setCanBePickedUpByPet(true);
        field.drop(drop, mob.getPosition(), mob.getPosition(), true);
        if (applyCooltime) {
            chr.addSkillCoolTime(LIVER, 1200);
        }
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
        Option o4 = new Option();
        Option o5 = new Option();
        Option o6 = new Option();
        Option o7 = new Option();
        switch (skillID) {
            case FOX_GODS_FAVOR:
                SkillBuffs.giveBooster(chr, skillID, slv);
                break;
            case SPIRIT_TRAP:
                SkillInfo fci = SkillData.getSkillInfoById(skillID);
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillID, slv);
                aa.setPosition(chr.getPosition());
                aa.setRect(aa.getPosition().getRectAround(fci.getRects().get(0)));
                aa.setDelay((short) 4);
                chr.getField().spawnAffectedArea(aa);
                break;
            case FOX_SPIRITS:
            case HEXA_FOX_SPIRITS:
                if (tsm.hasStat(HiddenPossession)) {
                    tsm.removeStatsBySkill(skillID);
                } else {
                    o1.nOption = 1;
                    o1.rOption = skillID;
                    tsm.putCharacterStatValue(HiddenPossession, o1);
                }
                break;
            case SUMMON_OTHER_SPIRIT:
                // on/off (the cooldown starts at the revive)
                if (tsm.getOptByCTSAndSkill(ReviveOnce, SUMMON_OTHER_SPIRIT) != null) {
                    tsm.removeStatsBySkill(SUMMON_OTHER_SPIRIT);
                    break;
                }
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ReviveOnce, o1, true);
                break;
            case SPIRIT_WARD:
            case SPIRIT_WARD_V271:
                if (skillID == SPIRIT_WARD_V271) {
                    // the v271 client casts 25120017; GMS's SpiritGuard still carries 25121209 and its time
                    skillID = SPIRIT_WARD;
                    si = SkillData.getSkillInfoById(SPIRIT_WARD);
                    slv = Math.max(slv, chr.getSkillLevel(SPIRIT_WARD));
                }
                if (slv <= 0 || si == null) {
                    return;
                }
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(SpiritGuard, o1);
                spiritWardTimer = System.currentTimeMillis() + (si.getValue(time, slv) * 1000);
                break;
            case SPIRIT_FRENZY:
            case SPIRIT_FRENZY_V271:
            case HEXA_SPIRIT_FRENZY: {
                Summon frenzy = Summon.getSummonBy(chr, skillID, slv);
                frenzy.setFlyMob(false);
                frenzy.setMoveAbility(MoveAbility.Stop);
                chr.getField().spawnSummonAndRemoveOld(frenzy);
                break;
            }
            case SPIRIT_FRENZY_AA:
            case SPIRIT_INCARNATION_AA:
                si = SkillData.getSkillInfoById(skillID);
                slv = skillID == SPIRIT_FRENZY_AA ? chr.getSkillLevel(SPIRIT_FRENZY) : chr.getSkillLevel(SPIRIT_INCARNATION);
                aa = AffectedArea.getPassiveAA(chr, skillID, slv);
                aa.setRect(aa.getRectAround(si.getFirstRect()));
                aa.setDelay((short) 4);
                chr.getField().spawnAffectedArea(aa);
                break;
            case HEROIC_MEMORIES_SH:
                o1.rOption = skillID;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case SPIRIT_BOND_MAX: // Fox Marble Fusion
                var newTime = SkillChangeConstants.SBM_TIME_INSTEAD_LIVER
                        ? SkillChangeConstants.SBM_TIME
                        : si.getValue(time, slv);
                if (SkillChangeConstants.PRE_PORT_CUSTOMS) { // v265 data has no attack speed on it
                    o4.rOption = skillID;
                    o4.nOption = SkillChangeConstants.SBM_BOOSTER;
                    o4.tOption = newTime;
                    tsm.putCharacterStatValue(IndieBooster, o4);
                }
                o6.nOption = 1;
                o6.rOption = skillID;
                o6.tOption = newTime;
                tsm.putCharacterStatValue(HiddenHyperLinkMaximization, o6);
                renewSpiritWard();
                break;
            case SPIRIT_FLOW:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ExtraSkillCTS, o1);
                o2.rOption = skillID;
                o2.nOption = si.getValue(indiePMdR, slv);
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o2);
                for (var skillId : chr.getSkillCoolTimes().keySet()) {
                    if (!SkillConstants.isNoCooltimeResetSkill(skillId)) {
                        chr.resetSkillCoolTime(skillId);
                    }
                }
                break;
            case SPIRITGATE_SUMMONER:
                Summon summon = Summon.getSummonBy(chr, skillID, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.Summon);
                chr.getField().spawnSummonAndRemoveOld(summon);
                break;
        }
        tsm.sendSetStatPacket();
    }

    public void extendSpiritBondMax() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!isFoxMarbleFusionActive()) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SPIRIT_BOND_MAX);
        int slv = Math.max(1, chr.getSkillLevel(SPIRIT_BOND_MAX));
        var extensionTime = 4000; // ms
        int remainingTime = (int) tsm.getRemainingTime(HiddenHyperLinkMaximization, SPIRIT_BOND_MAX);
        int tOpt = Math.max(50, Math.min((remainingTime + extensionTime), (si.getValue(time, slv) * 1000 * 2)));

        Option o4 = new Option();
        o4.rOption = SPIRIT_BOND_MAX;
        o4.nOption = SkillChangeConstants.SBM_BOOSTER; // [Shade] Custom Skill Change
        o4.tOption = tOpt;
        o4.setInMillis(true);
        if (SkillChangeConstants.PRE_PORT_CUSTOMS) {
            tsm.putCharacterStatValue(IndieBooster, o4, true);
        }
        Option o6 = new Option();
        o6.nOption = 1;
        o6.rOption = SPIRIT_BOND_MAX;
        o6.tOption = tOpt;
        o6.setInMillis(true);
        tsm.putCharacterStatValue(HiddenHyperLinkMaximization, o6, true);
        tsm.sendSetStatPacket();
    }

    private boolean isFoxMarbleFusionActive() {
        return chr.getTemporaryStatManager().getOptByCTSAndSkill(HiddenHyperLinkMaximization, SPIRIT_BOND_MAX) != null;
    }

    // Fox Marble Fusion tops Spirit Ward back up once when guardian spirits are missing
    private void renewSpiritWard() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int slv = chr.getSkillLevel(SPIRIT_WARD);
        if (slv <= 0) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SPIRIT_WARD);
        int spirits = si.getValue(x, slv);
        if (tsm.hasStat(SpiritGuard) && tsm.getOption(SpiritGuard).nOption >= spirits) {
            return;
        }
        Option o = new Option();
        o.nOption = spirits;
        o.rOption = SPIRIT_WARD;
        o.tOption = si.getValue(time, slv);
        tsm.putCharacterStatValue(SpiritGuard, o);
        spiritWardTimer = System.currentTimeMillis() + (si.getValue(time, slv) * 1000L);
    }

    // during Fox Marble Fusion, direct attacks can call extra empowered Fox Spirits
    private void foxMarbleOrbs(AttackInfo attackInfo) {
        if (!isFoxMarbleFusionActive()) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SPIRIT_BOND_MAX);
        int slv = Math.max(1, chr.getSkillLevel(SPIRIT_BOND_MAX));
        int chance = si.getValue(prop, slv);
        if (attackInfo.skillId == BOMB_PUNCH && attackInfo.mobAttackInfo.stream().anyMatch(m -> m.mob != null && m.mob.isBoss())) {
            chance = Math.max(chance, si.getValue(u2, slv));
        }
        if (!Util.succeedProp(chance)) {
            return;
        }
        int count = Math.max(1, si.getValue(bulletCount, slv));
        for (int i = 0; i < count; i++) {
            createFoxSpiritForceAtom();
        }
    }

    @Override
    public void handleKeyDownSkill(Char chr, int skillID, InPacket inPacket) {
        switch (skillID) {
            case SMASHING_MULTIPUNCH_KEYDOWN:
                TemporaryStatManager tsm = chr.getTemporaryStatManager();
                Option o = new Option();
                o.nOption = chr.getSkillStatValue(w, skillID);
                o.rOption = skillID;
                o.tOption = 3;
                tsm.putCharacterStatValue(IndieDamReduceR, o);
                tsm.sendSetStatPacket();
                break;
        }

        super.handleKeyDownSkill(chr, skillID, inPacket);
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        if (skillId == SMASHING_MULTIPUNCH_KEYDOWN) {
            chr.getJobHandler().handleSkillRemove(chr, skillId, new HashMap<>());
            return;
        }
        super.handleSkillPrepareStop(chr, skillId, sui);
    }

    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(SpiritGuard) && hitInfo.hpDamage > 0) {
            deductSpiritWard();
            hitInfo.hpDamage = 0;
            hitInfo.mpDamage = 0;
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    private void deductSpiritWard() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(SPIRIT_WARD)) {
            return;
        }
        Skill skill = chr.getSkill(SPIRIT_WARD);
        Option o = new Option();
        if (tsm.hasStat(SpiritGuard)) {
            int spiritWardCount = tsm.getOption(SpiritGuard).nOption;

            if (spiritWardCount > 0) {
                spiritWardCount--;
            }

            if (spiritWardCount <= 0) {
                tsm.removeStatsBySkill(skill.getSkillId());
                tsm.sendResetStatPacket();
            } else {
                o.setInMillis(true);
                o.nOption = spiritWardCount;
                o.rOption = skill.getSkillId();
                o.tOption = (int) (spiritWardTimer - System.currentTimeMillis());
                tsm.putCharacterStatValue(SpiritGuard, o);
                tsm.sendSetStatPacket();
            }
        }
    }

    @Override
    public void handleMobDebuffSkill(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(SpiritGuard)) {
            tsm.removeAllDebuffs();
            deductSpiritWard();
        }
    }

    @Override
    public net.swordie.ms.client.character.skills.BypassCooldownCheckType canBypassCooldownCheck(int skillId,
            AttackInfo attackInfo, SkillUseInfo sui, net.swordie.ms.client.character.skills.SkillUseSource source) {
        // the cooldown starts at the revive, not at the toggle
        if (skillId == SUMMON_OTHER_SPIRIT && !chr.hasSkillOnCooldown(SUMMON_OTHER_SPIRIT)) {
            return net.swordie.ms.client.character.skills.BypassCooldownCheckType.BypassCheckAndCooldown;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    public void reviveBySummonOtherSpirit() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        chr.heal(chr.getMaxHP(), false, true);
        tsm.removeStatsBySkill(SUMMON_OTHER_SPIRIT);
        tsm.sendResetStatPacket();
        chr.chatMessage("You have been revived by Summon Other Spirit.");
        // invincible for y sec (the client checks NotDamaged with this skill)
        int lv = Math.max(1, chr.getSkillLevel(SUMMON_OTHER_SPIRIT));
        chr.setSkillCooldown(SUMMON_OTHER_SPIRIT, lv); // hardcode-ok
        tsm.putCharacterStatValue(NotDamaged, new Option(1, SUMMON_OTHER_SPIRIT,
                Math.max(1, SkillData.getSkillInfoById(SUMMON_OTHER_SPIRIT).getValue(y, lv))));
        tsm.sendSetStatPacket();
        chr.write(UserPacket.effect(Effect.skillSpecial(SUMMON_OTHER_SPIRIT)));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillSpecial(SUMMON_OTHER_SPIRIT)));

        chr.write(UserPacket.effect(Effect.skillUse(SPIRIT_REDEMPTION, chr.getLevel(), (byte) 1, 0)));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillUse(SPIRIT_REDEMPTION, chr.getLevel(), (byte) 1, 0)));
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        chr.getAvatarData().getCharacterStat().setPosMap(TEMPLE_OF_TIME_FIELD);
        Item item = ItemData.getItemDeepCopy(BLUE_FOX_MARBLE); // Secondary
        chr.addItemToInventory(item);
    }
    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        var level = chr.getLevel();
        boolean classAdv = net.swordie.ms.client.character.JobPathModule.classHandlesAdvancement(chr);
        if (classAdv && level == 10) {
            chr.advanceJob(JobConstants.JobEnum.SHADE_1.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);

            chr.addSkill(SPIRIT_BOND_I, 0, 10);
        } else if (!classAdv && level == 30 && chr.getJob() == JobConstants.JobEnum.SHADE_1.getJobId()
                && !net.swordie.ms.client.character.JobPathModule.isManaged(chr)
                && chr.getQuestManager().hasQuestCompleted(38030)) {
            // official (v232): the 2nd job comes with "The Return" (38030, back in Maple World) at level 30; Shade
            // has no 2nd job quest of its own. If 38030 is done at 30+, its script advances instead.
            chr.advanceJob(JobConstants.JobEnum.SHADE_2.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);
        } else if (classAdv && level == 30) {
            chr.advanceJob(JobConstants.JobEnum.SHADE_2.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);
            chr.getQuestManager().completeQuest(38030); // JobAdv quest
        } else if (classAdv && level == 60) {
            chr.advanceJob(JobConstants.JobEnum.SHADE_3.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);
            chr.getQuestManager().completeQuest(38074); // JobAdv Quest
            chr.getQuestManager().completeQuest(38075); // JobAdv Quest
            chr.getQuestManager().completeQuest(38076); // JobAdv Quest
        } else if (classAdv && level == 100) {
            chr.advanceJob(JobConstants.JobEnum.SHADE_4.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);
            chr.getQuestManager().completeQuest(38073); // JobAdv Quest
        } else if (level == 200) {
            QuestManager qm = chr.getQuestManager();
            qm.completeQuest(QuestConstants.SPIRIT_BOND_MAX_QUEST_ID);
        }
    }
}

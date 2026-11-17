package net.swordie.ms.client.jobs.resistance;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.debuff.DebuffInfo;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.*;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Xenon extends Job {
    public static final int SUPPLY_SURPLUS = 30020232;
    public static final int MULTILATERAL_I = 30020234;
    public static final int MODAL_SHIFT = 30021236;
    public static final int LIBERTY_BOOSTERS = 30021237;
    public static final int MIMIC_PROTOCOL = 30020240;
    public static final int PROMESSA_ESCAPE = 30021235;

    public static final int MULTILATERAL_II = 36000004;
    public static final int CIRCUIT_SURGE = 36001002; //Buff
    public static final int PINPOINT_SALVO = 36001005; //Special Attack

    public static final int MULTILATERAL_III = 36100007;
    public static final int XENON_BOOSTER = 36101004; //Buff
    public static final int EFFICIENCY_STREAMLINE = 36101003; //Buff
    public static final int ION_THRUST = 36101001; //Special Attack
    public static final int PINPOINT_SALVO_REDESIGN_A = 36100010; //Special Attack Upgrade  (Passive Upgrade)

    public static final int MULTILATERAL_IV = 36110007;
    public static final int GRAVITY_PILLAR = 36111002;
    public static final int COMBAT_SWITCH_FISSION = 36111010;
    public static final int HYBRID_DEFENSES = 36111003; //Buff
    public static final int AEGIS_SYSTEM = 36111004; //Special Buff (ON/OFF)
    public static final int AEGIS_SYSTEM_ATOM = 36110004; //Special Buff (ON/OFF)
    public static final int MANIFEST_PROJECTOR = 36111006; //Special Buff (Special Duration)
    public static final int EMERGENCY_RESUPPLY = 36111008; //Special Skill
    public static final int PINPOINT_SALVO_REDESIGN_B = 36110012; //Special Attack Upgrade  (Passive Upgrade)
    public static final int TRIANGULATION = 36110005;

    public static final int BEAM_DANCE = 36121000;
    public static final int MULTILATERAL_V = 36120010;
    public static final int MULTILATERAL_VI = 36120016;
    public static final int HYPOGRAM_FIELD_FORCE_FIELD = 36121013;
    public static final int HYPOGRAM_FIELD_PENETRATE = 36121002; // v265 only
    public static final int HYPOGRAM_FIELD_SUPPORT = 36121014;
    public static final int HYPOGRAM_FIELD_PERSIST = 36120051; // hyper passive
    public static final int TEMPORAL_POD = 36121007;
    public static final int OOPARTS_CODE = 36121003; //Buff
    public static final int MAPLE_WARRIOR_XENON = 36121008; //Buff
    public static final int PINPOINT_SALVO_PERFECT_DESIGN = 36120015; //Sp. Attack Upgrade  (Passive Upgrade)
    public static final int HEROS_WILL_XENON = 36121009;

    public static final int ORBITAL_CATACLYSM = 36121052;
    public static final int AMARANTH_GENERATOR = 36121054;
    public static final int ENTANGLISH_LASH = 36121053;

    public static final int OMEGA_BLASTER = 400041007;
    public static final int CORE_OVERLOAD_BUFF = 400041029;
    public static final int CORE_OVERLOAD_ATTACK = 400041031;
    public static final int CORE_OVERLOAD_BURST = 400041093; // v271

    public static final int HYPOGRAM_FIELD_FUSION = 400041044;
    public static final int PHOTON_RAY = 400041057;
    public static final int PHOTON_RAY_SA = 400041058; // SecondAtom
    public static final int HEXA_ORBITAL_CATACLYSM = 36141012; // HEXA Orbital Cataclysm
    public static final int HEXA_HYPOGRAM_FIELD_PENETRATE = 36141004; // HEXA Hypogram Field: Penetrate
    public static final int HEXA_HYPOGRAM_FIELD_FORCE_FIELD = 36141005;
    public static final int HEXA_HYPOGRAM_FIELD_SUPPORT = 36141006;
    public static final int ARTIFICIAL_EVOLUTION = 36141500;
    public static final int ARTIFICIAL_EVOLUTION_BUFF = 36141503;
    public static final int HEXA_SALVO = 36141010;
    public static final int HEXA_SALVO_SYSTEM = 36141009; // on/off, replaces Salvo System on the quickslot
    public static final int HEXA_SALVO_AEGIS = 36141011;
    public static final int HEXA_TRIANGULATION = 36141007;
    public static final int HEXA_TRIANGULATION_2 = 36141008;
    public static final int HEXA_BEAM_DANCE = 36141014;
    public static final int HEXA_MEMORY_DANCE = 36140015;
    public static final int LIBERTY_BOOSTERS_ENHANCED = 36141502;

    private static final int GRAVITY_PILLAR_LIFT = 150; // hardcode-ok: data has y/time=300, subTime=1200, meaning unclear
    private static final int GRAVITY_PILLAR_LIFT_SEC = 2; // hardcode-ok
    private static final int ORBITAL_CATACLYSM_INVINCIBLE_SEC = 4; // hardcode-ok: tooltip says "during casting", no stat
    private static final int TRIANGULATION_MARK_SEC = 120; // hardcode-ok: data time=8 is not in the tooltip
    private static final int OVERLOAD_MODE_X = 91; // hardcode-ok: client value, no stat
    public static final int INFILTRATE = 30021001;
    public static final int DUAL_CORE_CONTROLLER = 1353000;
    public static final int EDELSTEIN_OUTSKIRTS_1_FIELD = 931050900;

    private int supply;
    private int supplyProp;
    private ScheduledFuture overloadTimer;
    private ScheduledFuture supplyTimer;

    private static final int[] addedSkills = new int[]{
            SUPPLY_SURPLUS,
            MULTILATERAL_I,
            MODAL_SHIFT,
            LIBERTY_BOOSTERS,
            MIMIC_PROTOCOL,
            PROMESSA_ESCAPE,
    };


    public Xenon(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            supplyProp = SkillData.getSkillInfoById(SUPPLY_SURPLUS).getValue(prop, 1);
        }
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        super.onWarp(oldField, newField);
        // Supply Surplus: one power unit every z seconds, started once the character is in a map
        if (supplyTimer == null && isHandlerOfJob(chr.getJob())) {
            int interval = SkillData.getSkillInfoById(SUPPLY_SURPLUS).getValue(z, 1);
            if (interval > 0) {
                supplyTimer = EventManager.addFixedRateEvent(this::supplyTick, interval * 1000L, interval * 1000L);
            }
        }
    }

    private void supplyTick() {
        if (chr == null || chr.getField() == null || chr.getTemporaryStatManager() == null) {
            return;
        }
        if (chr.getTemporaryStatManager().hasStat(OverloadMode)) {
            return; // Core Overload charges on its own faster timer
        }
        incrementSupply(1);
    }

    // Core Overload: the v265 client sends no per-second request, so the server drains MP and charges energy every u sec
    private void overloadTick() {
        if (chr == null || chr.getField() == null || chr.getTemporaryStatManager() == null) {
            return;
        }
        if (!chr.getTemporaryStatManager().hasStat(OverloadMode)) {
            handleSkillRemove(chr, CORE_OVERLOAD_BUFF, new HashMap<>());
            return;
        }
        if (coreOverloadManaConsumption()) {
            incrementSupply(1);
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isXenon(id);
    }


    public void useOmegaBlasterAttack() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.getOptByCTSAndSkill(OmegaBlaster, OMEGA_BLASTER) != null && tsm.getOptByCTSAndSkill(OmegaBlaster, OMEGA_BLASTER).nOption == -1) {
            Skill skill = chr.getSkill(OMEGA_BLASTER);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            Option o1 = new Option();
            Option o2 = new Option();

            long secPerXSec = si.getValue(y, slv) * 1000;
            long cap = si.getValue(z, slv) * 1000;
            if (secPerXSec <= 0) {
                // v271: a key-down beam, the client deploys it; no charged buff after
                tsm.removeStatsBySkill(OMEGA_BLASTER);
                tsm.sendResetStatPacket();
                return;
            }

            long chargingDuration = Util.getCurrentTimeLong() - tsm.getOptByCTSAndSkill(OmegaBlaster, OMEGA_BLASTER).tStartLong;
            long addedDuration = (chargingDuration / secPerXSec) * 1000;
            long initDuration = si.getValue(time, slv) * 1000;
            long fullDuration = initDuration + (Math.min(addedDuration, cap));

            o1.nOption = 1;
            o1.rOption = skill.getSkillId();
            o1.tOption = (int) fullDuration;
            o1.setInMillis(true);
            tsm.putCharacterStatValue(OmegaBlaster, o1);
            o2.nOption = 1;
            o2.rOption = skill.getSkillId();
            o2.tOption = (int) fullDuration;
            o2.setInMillis(true);
            tsm.putCharacterStatValue(IndieNotDamaged, o2, true); // invincibility
        } else {
            tsm.removeStatsBySkill(OMEGA_BLASTER);
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleKeyDownSkill(Char chr, int skillID, InPacket inPacket) {
        if (JobConstants.isXenon(chr.getJob()) && skillID == Xenon.ION_THRUST) {
            ((Xenon) chr.getJobHandler()).applySupplyCost(skillID, chr.getSkillLevel(skillID), SkillData.getSkillInfoById(skillID));
            return;
        }

        super.handleKeyDownSkill(chr, skillID, inPacket);
    }

    private void applySupplyCost(int skillID, int slv, SkillInfo si) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (skillID == PINPOINT_SALVO || skillID == PINPOINT_SALVO_REDESIGN_A || skillID == PINPOINT_SALVO_REDESIGN_B || skillID == PINPOINT_SALVO_PERFECT_DESIGN) {
            return;
        }
        if (tsm.hasStat(AmaranthGenerator) || tsm.hasStat(OverloadMode)) {
            return;
        }
        if (si == null) {
            return;
        }
        if (si.getValue(powerCon, slv) > 0) {
            supply -= si.getValue(powerCon, slv);
            supply = Math.max(0, supply);
        }
        updateSupply();
    }

    public void incrementSupply() {
        incrementSupply(1);
    }

    public void incrementSupply(int amount) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int maxSupply = getMaxSupply();
        if (tsm.hasStat(OverloadMode)) {
            maxSupply = 40;
        }
        if (tsm.hasStat(SurplusSupply)) {
            supply = tsm.getOption(SurplusSupply).nOption;
        }
        if (supply < maxSupply) {
            supply += amount;
            supply = Math.min(maxSupply, supply);
            updateSupply();
        }
    }

    public int getMaxSupply() {
        if (JobConstants.JobEnum.XENON_1.getJobId() == chr.getJob()) {
            return 5;
        } else if (JobConstants.JobEnum.XENON_2.getJobId() == chr.getJob()) {
            return 10;
        } else if (JobConstants.JobEnum.XENON_3.getJobId() == chr.getJob()) {
            return 15;
        } else if (JobConstants.JobEnum.XENON_4.getJobId() == chr.getJob()) {
            return 20;
        }
        return 0;
    }

    private void updateSupply() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = supply;
        tsm.putCharacterStatValue(SurplusSupply, o);

        if (tsm.hasStat(OverloadMode)) {
            Option o1 = new Option();
            o1.rOption = CORE_OVERLOAD_BUFF + 100;
            o1.nOption = supply;
            tsm.putCharacterStatValue(IndieDamR, o1);
        }

        tsm.sendSetStatPacket();
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
        if (hasHitMobs && attackInfo.skillId != 0 && attackInfo.skillId != TRIANGULATION
                && attackInfo.skillId != HEXA_TRIANGULATION && attackInfo.skillId != HEXA_TRIANGULATION_2) {
            // Triangulation
            applyTriangulationOnMob(attackInfo); // [Xenon] Custom Skill Change
            // Triangulation can be procced from Pinpoint salvo

            if (attackInfo.skillId != PINPOINT_SALVO
                    && attackInfo.skillId != PINPOINT_SALVO_REDESIGN_A
                    && attackInfo.skillId != PINPOINT_SALVO_REDESIGN_B
                    && attackInfo.skillId != PINPOINT_SALVO_PERFECT_DESIGN
                    && attackInfo.skillId != HEXA_SALVO
                    && attackInfo.skillId != HEXA_SALVO_AEGIS) {

                if (attackInfo.skillId != BEAM_DANCE
                        && attackInfo.skillId != HEXA_BEAM_DANCE
                        && attackInfo.skillId != HEXA_MEMORY_DANCE
                        && attackInfo.skillId != AEGIS_SYSTEM_ATOM
                        && attackInfo.skillId != CORE_OVERLOAD_ATTACK
                        && attackInfo.skillId != HYPOGRAM_FIELD_FORCE_FIELD
                        && attackInfo.skillId != HYPOGRAM_FIELD_SUPPORT
                        && attackInfo.skillId != HYPOGRAM_FIELD_PENETRATE
                        && attackInfo.skillId != HYPOGRAM_FIELD_FUSION) {

                    coreOverloadSkillManaConsumption();
                }

                if (!chr.hasSkillOnCooldown(PINPOINT_SALVO)) {
                    createPinPointSalvoForceAtom();
                }
            }
        }
        if (attackInfo.skillId != ION_THRUST) {
            applySupplyCost(skillID, (byte) slv, si);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case GRAVITY_PILLAR:
                o1.nOption = GRAVITY_PILLAR_LIFT;
                o1.rOption = skillID;
                o1.tOption = GRAVITY_PILLAR_LIFT_SEC;

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Lifting, o1, attackInfo.getMobIds(), new DebuffInfo(true));
                break;
            case ENTANGLISH_LASH:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.removeBuffs();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                    mts.addStatOptionsAndBroadcast(chr, MobStat.MagicCrash, o1);
                }
                break;
            case ORBITAL_CATACLYSM:
            case HEXA_ORBITAL_CATACLYSM:
                o1.nOption = -si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);

                if (o1.nOption != 0) { // v271: no DEF shred and no damage buff after
                    DebuffUtil.applyDebuffOnMobs(chr, Map.of(MobStat.PDR, o1, MobStat.MDR, o1), attackInfo.getMobIds());
                }

                o3.nOption = 1;
                o3.rOption = skillID;
                o3.tOption = ORBITAL_CATACLYSM_INVINCIBLE_SEC;
                tsm.putCharacterStatValue(IndieNotDamaged, o3, true);
                tsm.sendSetStatPacket();

                if (si.getValue(w, slv) > 0) {
                    o2.nOption = si.getValue(w, slv);
                    o2.rOption = skillID;
                    o2.tOption = si.getValue(y, slv);
                    tsm.putCharacterStatValue(IndieDamR, o2);
                    tsm.sendSetStatPacket();
                }
                break;
        }

        super.handleAttack(c, attackInfo);
    }

    public void doOverloadModeAttack() {
        chr.write(UserLocal.userBonusAttackRequest(CORE_OVERLOAD_ATTACK));
    }

    public void applyTriangulationOnMob(AttackInfo attackInfo) {
        if (!chr.hasSkill(TRIANGULATION)) {
            return;
        }
        Skill skill = chr.getSkill(TRIANGULATION);
        int slv = skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(TRIANGULATION);
        int proc = si.getValue(prop, slv);
        Option o1 = new Option();
        o1.rOption = TRIANGULATION;
        o1.wOption = chr.getId();
        o1.tOption = TRIANGULATION_MARK_SEC;
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob == null) {
                continue;
            }
            MobTemporaryStat mts = mob.getTemporaryStat();

            if (Util.succeedProp(proc)) {
                int amount = 1; // per mob, up to 3 airframes
                if (mts.hasCurrentMobStat(MobStat.Explosion)) {
                    amount = mts.getCurrentOptionsByMobStat(MobStat.Explosion).nOption;
                    if (amount < 3) {
                        amount++;
                    }

                    mts.removeMobStat(MobStat.Explosion);
                }

                o1.nOption = amount;
                mts.addStatOptionsAndBroadcast(chr, MobStat.Explosion, o1);
            }
        }
    }

    private void createPinPointSalvoForceAtom() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(PinPointSalvo)) {
            return;
        }
        Field field = chr.getField();
        SkillInfo si = SkillData.getSkillInfoById(PINPOINT_SALVO);
        int slv = chr.getSkillLevel(PINPOINT_SALVO);
        Rect rect = chr.getPosition().getRectAround(si.getFirstRect());
        if (!chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        List<Integer> targetList = new ArrayList<>();
        List<ForceAtomInfo> faiList = new ArrayList<>();
        ForceAtomEnum fae = ForceAtomEnum.XENON_ROCKET_3;
        List<Mob> possibleTargetMobs = field.getMobsInRect(rect).stream().filter(m -> !MobConstants.isFriendlyMob(m.getTemplateId())).collect(Collectors.toList());
        for (int i = 0; i < si.getValue(bulletCount, slv); i++) {
            Mob mob = Util.getRandomFromCollection(possibleTargetMobs);
            int angle = new Random().nextInt(90) + 45;
            int fImpact = new Random().nextInt(3) + 18;
            int sImpact = new Random().nextInt(3) + 28;
            int delay = new Random().nextInt(200) + 50;
            ForceAtomInfo forceAtomInfo = new ForceAtomInfo(1, fae.getInc(), fImpact, sImpact,
                    angle, delay, Util.getCurrentTime(), 1, 0,
                    new Position());
            faiList.add(forceAtomInfo);
            targetList.add(mob != null ? mob.getObjectId() : 0);
        }
        chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae,
                true, targetList, getPinPointSkill(), faiList, rect, 0, 300,
                new Position(), 0, new Position(), 0));
        var customCD = swGetCustomCDOnRockets(tsm, PINPOINT_SALVO);
        chr.addSkillCoolTime(PINPOINT_SALVO, customCD != -1 ? customCD : si.getValue(x, slv) * 1000);
    }

    // [Xenon] Custom Skill Change
    private int swGetCustomCDOnRockets(TemporaryStatManager tsm, int skillId) {
        if (!tsm.hasStat(AmaranthGenerator) || !SkillChangeConstants.AMARANTH_LOWER_ROCKETS_CD) {
            return -1;
        }
        if (skillId == PINPOINT_SALVO) {
            return SkillChangeConstants.PINPOINT_SALVO_CD_WHILST_AMARANTH;
        } else if (skillId == AEGIS_SYSTEM) {
            return SkillChangeConstants.AEGIS_SYSTEM_CD_WHILST_AMARANTH;
        }
        return -1;
    }

    public int getPinPointSkill() {
        int skill = PINPOINT_SALVO;
        if (chr.hasSkill(PINPOINT_SALVO_REDESIGN_A)) {
            skill = PINPOINT_SALVO_REDESIGN_A;
        }
        if (chr.hasSkill(PINPOINT_SALVO_REDESIGN_B)) {
            skill = PINPOINT_SALVO_REDESIGN_B;
        }
        if (chr.hasSkill(PINPOINT_SALVO_PERFECT_DESIGN)) {
            skill = PINPOINT_SALVO_PERFECT_DESIGN;
        }
        return skill;
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (skill != null) {
            coreOverloadSkillManaConsumption();
        }
        applySupplyCost(skillId, slv, si);
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillId) {
            case ARTIFICIAL_EVOLUTION_BUFF: {
                int aeSlv = chr.getSkillLevel(ARTIFICIAL_EVOLUTION);
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = SkillData.getSkillInfoById(ARTIFICIAL_EVOLUTION_BUFF).getValue(time, aeSlv > 0 ? aeSlv : Math.max(1, slv));
                tsm.putCharacterStatValue(ArtificialEvolution, o1);
                tsm.sendSetStatPacket();
                break;
            }
            case LIBERTY_BOOSTERS:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(x, slv);
                tsm.putCharacterStatValue(NewFlying, o1, true);
                break;
            case LIBERTY_BOOSTERS_ENHANCED: { // Artificial Evolution's flight, time in ms
                SkillInfo lsi = SkillData.getSkillInfoById(skillId);
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = lsi.getValue(time, Math.max(1, slv));
                o1.setInMillis(true);
                tsm.putCharacterStatValue(NewFlying, o1, true);
                break;
            }
            case EMERGENCY_RESUPPLY:
                int rechargedSupply = si.getValue(x, slv);
                if (rechargedSupply + supply > 20) {
                    rechargedSupply = 20;
                }
                incrementSupply(rechargedSupply);
                break;
            case CIRCUIT_SURGE:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indiePad, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, o1);
                break;
            case XENON_BOOSTER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case EFFICIENCY_STREAMLINE:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieMhpR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMHPR, o1);
                tsm.putCharacterStatValue(IndieMMPR, o1);
                break;
            case HYBRID_DEFENSES:
                o1.nOption = si.getValue(prop, slv);
                o1.rOption = skillId;
                tsm.putCharacterStatValue(IndieEVAR, o1);
                o2.nOption = 1;
                o2.rOption = skillId;
                o2.mOption = si.getValue(x, slv);
                tsm.putCharacterStatValue(StackBuff, o2);
                o3.nOption = si.getValue(z, slv);
                o3.rOption = skillId;
                tsm.putCharacterStatValue(DamageReduce, o3);
                break;
            case AEGIS_SYSTEM:
                if (tsm.hasStat(XenonAegisSystem)) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(XenonAegisSystem, o1);
                }
                break;
            case PINPOINT_SALVO:
            case HEXA_SALVO_SYSTEM:
                if (tsm.hasStat(PinPointSalvo)) {
                    tsm.removeStat(PinPointSalvo);
                    tsm.sendResetStatPacket();
                } else {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(PinPointSalvo, o1);
                }
                break;
            case MANIFEST_PROJECTOR:
                o1.nOption = si.getValue(y, slv);
                o1.rOption = skillId;
                tsm.putCharacterStatValue(ShadowPartner, o1);
                break;
            case OOPARTS_CODE:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indiePMdR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o1);
                o2.rOption = skillId;
                o2.nOption = si.getValue(indieBDR, slv);
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBDR, o2);
                break;
            case AMARANTH_GENERATOR:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(AmaranthGenerator, o1);
                if (!tsm.hasStat(OverloadMode)) {
                    incrementSupply(20);
                }
                break;
            case HYPOGRAM_FIELD_FUSION:
            case HYPOGRAM_FIELD_FORCE_FIELD:
            case HYPOGRAM_FIELD_PENETRATE:
            case HEXA_HYPOGRAM_FIELD_PENETRATE:
            case HEXA_HYPOGRAM_FIELD_FORCE_FIELD:
            case HEXA_HYPOGRAM_FIELD_SUPPORT:
            case HYPOGRAM_FIELD_SUPPORT:
                if (skillId != HYPOGRAM_FIELD_FUSION) {
                    // the fields run on the learned Force Field (v271: Penetrate is gone)
                    int fieldSkill = skillId >= HEXA_HYPOGRAM_FIELD_PENETRATE && skillId <= HEXA_HYPOGRAM_FIELD_SUPPORT
                            ? HEXA_HYPOGRAM_FIELD_FORCE_FIELD : HYPOGRAM_FIELD_FORCE_FIELD;
                    if (chr.getSkillLevel(fieldSkill) > 0) {
                        si = SkillData.getSkillInfoById(fieldSkill);
                        slv = chr.getSkillLevel(fieldSkill);
                    }
                }
                AssistType at = AssistType.Attack;
                if (skillId == HYPOGRAM_FIELD_PENETRATE || skillId == HEXA_HYPOGRAM_FIELD_PENETRATE
                        || skillId == HYPOGRAM_FIELD_FUSION) {
                    at = AssistType.AttackEx;
                } else if (skillId == HYPOGRAM_FIELD_SUPPORT || skillId == HEXA_HYPOGRAM_FIELD_SUPPORT) {
                    at = AssistType.Heal;
                }
                Position position = inPacket.decodePosition();
                Summon summon = Summon.getSummonByNoCTS(chr, skillId, slv);
                summon.setPosition(position);
                summon.setCurFoothold((short) field.findFootHoldIdBelow(summon.getPosition()));
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 4);
                summon.setAssistType(at);
                summon.setAttackActive(true);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setSummonTerm(si.getValue(time, slv) + (chr.hasSkill(HYPOGRAM_FIELD_PERSIST) ? 10 : 0));
                field.spawnSummonAndRemoveOld(summon);
                if (skillId == HYPOGRAM_FIELD_FUSION) {
                    AffectedArea aa = AffectedArea.getPassiveAA(chr, skillId, slv);
                    aa.setDelay((short) 2);
                    aa.setPosition(chr.getPosition());
                    aa.setRect(position.getRectAround(si.getFirstRect()));
                    aa.setDuration((si.getValue(time, slv) + (chr.hasSkill(HYPOGRAM_FIELD_PERSIST) ? 10 : 0)) * 1000);
                    field.spawnAffectedArea(aa);
                }
                if (skillId != HYPOGRAM_FIELD_FUSION) {
                    chr.setSkillCooldown(HYPOGRAM_FIELD_FORCE_FIELD, slv); // hardcode-ok
                    chr.setSkillCooldown(HYPOGRAM_FIELD_SUPPORT, slv); // hardcode-ok
                }
                break;
            case TEMPORAL_POD:
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillId, slv);
                aa.setPosition(chr.getPosition());
                aa.setField(chr.getField());
                aa.setRect(chr.getPosition().getRectAround(si.getFirstRect()));
                aa.setDelay((short) 7);
                field.spawnAffectedArea(aa);
                aa.activateTimer(1000, 1000);
                break;
            case CORE_OVERLOAD_BUFF:
                o1.nOption = si.getValue(w, slv);
                o1.rOption = skillId;
                o1.xOption = OVERLOAD_MODE_X;
                o1.tOption = si.getValue(u2, slv) > 0 ? si.getValue(u2, slv) : si.getValue(time, slv); // v271: time
                tsm.putCharacterStatValue(OverloadMode, o1);
                SkillInfo burstSi = SkillData.getSkillInfoById(CORE_OVERLOAD_BURST);
                if (burstSi != null) {
                    // v271: damage and ignore defense for the first seconds
                    int bt = burstSi.getValue(time, slv);
                    tsm.putCharacterStatValue(IndieDamR, new Option(burstSi.getValue(indieDamR, slv), CORE_OVERLOAD_BURST, bt), true);
                    tsm.putCharacterStatValue(IndieIgnoreMobpdpR, new Option(burstSi.getValue(indieIgnoreMobpdpR, slv), CORE_OVERLOAD_BURST, bt), true);
                }
                updateSupply();
                if (overloadTimer != null && !overloadTimer.isDone()) {
                    overloadTimer.cancel(false);
                }
                long chargeMs = Math.max(1, si.getValue(u, slv)) * 1000L;
                overloadTimer = EventManager.addFixedRateEvent(this::overloadTick, chargeMs, chargeMs);
                break;
            case OMEGA_BLASTER: // Charging
                o1.nOption = -1;
                o1.rOption = skillId;
                o1.xOption = skillId;
                tsm.putCharacterStatValue(OmegaBlaster, o1);
                break;
            case PHOTON_RAY:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(PhotonRay, o1);
                break;
            case PHOTON_RAY_SA:
                inPacket.decodeInt();
                Position pos = inPacket.decodePositionInt();
                var i = 0;
                if (si != null && si.getSecondAtomInfos().size() > 0) {

                    var sai = si.getSecondAtomInfos().get(0);

                    List<SecondAtom> saList = new ArrayList<>();
                    for (var entry : skillUseInfo.targetMap.entrySet()) {
                        var target = entry.getKey();
                        var count = entry.getValue();

                        for (int ii = 0; ii < count; ii++) {
                            var sa = SecondAtom.createFromInfo(chr, skillId, sai);
                            sa.setTargetId(target);
                            if (i >= 30) {
                                continue;
                            }
                            sa.setCustomRotation(sai.getCustomList().get(i));
                            sa.setPosition(pos.add(sai.getExtraPosList().get(i)));
                            sa.setCreateDelay(Util.getRandom(50, 250));
                            saList.add(sa);
                            i++;
                        }
                    }

                    if (saList.size() > 0) {
                        chr.spawnSecondAtoms(saList);
                        tsm.removeStatsBySkill(PHOTON_RAY);
                    }
                }
                break;
        }
        tsm.sendSetStatPacket();
    }

    public void healHypogramField(Summon summon) {
        var si = SkillData.getSkillInfoById(summon.getSkillID());
        var slv = summon.getSlv();
        Rect rect = summon.getRectAround(si.getFirstRect());
        List<Char> affectedChrs = new ArrayList<>() {{ add(chr); }};
        if (chr.getParty() != null) {
            affectedChrs = chr.getParty().getPartyMembersInField(chr);
        }
        for (var pChr : affectedChrs.stream().filter(pChr -> rect.hasPositionInside(pChr.getPosition())).collect(Collectors.toList())) {
            var opt = new Option(si.getValue(indieMhpR, slv), summon.getSkillID(), 8);
            opt.chr = chr;
            pChr.getTemporaryStatManager().putCharacterStatValue(IndieMHPR, opt);
            pChr.getTemporaryStatManager().sendSetStatPacket();
        }
    }

    public void coreOverloadSkillManaConsumption() {
        // v265 Core Overload costs q% Max MP + y per second only (overloadTick), nothing per skill
    }

    /** One second of Core Overload: q% Max MP + y. Ends the overload and returns false when MP runs short. */
    public boolean coreOverloadManaConsumption() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(CORE_OVERLOAD_BUFF);
        if (!tsm.hasStat(OverloadMode) || skill == null || !chr.hasSkill(CORE_OVERLOAD_BUFF)) {
            return false;
        }
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        long cost = (long) chr.getMaxMP() * si.getValue(q, slv) / 100 + si.getValue(y, slv);
        if (chr.getMP() < cost) {
            handleSkillRemove(chr, CORE_OVERLOAD_BUFF, new HashMap<>());
            return false;
        }
        chr.healMP((int) -cost);
        return true;
    }

    @Override
    public int alterHealMP(int oldMP, int amount, int sourceId) {
        if (chr.getTemporaryStatManager().hasStat(OverloadMode) && amount > 0
                && chr.getSkillStatValue(q, CORE_OVERLOAD_BUFF) > 0) {
            // Cannot recover MP whilst in OverloadMode (v265; v271 has no per-second MP drain)
            return 0;
        }
        return super.alterHealMP(oldMP, amount, sourceId);
    }

    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        if (chr.hasSkill(HYBRID_DEFENSES) && tsm.hasAOptByCTSAndSkill(IndieEVAR, HYBRID_DEFENSES) && tsm.hasAOptByCTSAndSkill(StackBuff, HYBRID_DEFENSES)) {
            activateHybridDefenses();
        }

        if (tsm.hasStat(XenonAegisSystem) && chr.hasSkill(AEGIS_SYSTEM) && !chr.hasSkillOnCooldown(AEGIS_SYSTEM_ATOM)) {
            activateAegisSystem(hitInfo);
        }

        if (Util.succeedProp(supplyProp)) {
            incrementSupply(1);
        }

        super.handleHit(c, inPacket, hitInfo);
    }

    private void activateHybridDefenses() {
        Skill skill = chr.getSkill(HYBRID_DEFENSES);
        int slv = skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        Option o = new Option();
        Option o1 = new Option();
        Option o2 = new Option();

        var curEvaR = tsm.getOptByCTSAndSkill(IndieEVAR, skill.getSkillId()).nOption;
        var newEvaR = curEvaR - si.getValue(y, slv);

        var curCount = tsm.getOptByCTSAndSkill(StackBuff, skill.getSkillId()).mOption;
        var newCount = curCount - 1;

        if (newCount <= 0) {
            tsm.removeStatsBySkill(skill.getSkillId());
        } else {
            o.nOption = newEvaR;
            o.rOption = skill.getSkillId();
            o.tOption = si.getValue(time, slv);
            tsm.putCharacterStatValue(IndieEVAR, o);
            o1.nOption = 1;
            o1.rOption = skill.getSkillId();
            o1.mOption = newCount;
            tsm.putCharacterStatValue(StackBuff, o1);
            o2.nOption = si.getValue(z, slv);
            o2.rOption = skill.getSkillId();
            tsm.putCharacterStatValue(DamageReduce, o2);
        }
        tsm.sendSetStatPacket();
    }

    private void activateAegisSystem(HitInfo hitInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill aegis = chr.getSkill(AEGIS_SYSTEM);
        SkillInfo si = SkillData.getSkillInfoById(AEGIS_SYSTEM);
        int slv = aegis.getCurrentLevel();
        if (Util.succeedProp(si.getValue(prop, slv))) {
            int mobID = hitInfo.mobID;
            ForceAtomEnum fae = ForceAtomEnum.XENON_ROCKET_1;
            int curTime = Util.getCurrentTime();
            List<ForceAtomInfo> faiList = new ArrayList<>();
            List<Integer> mobList = new ArrayList<>();
            int extraAtomFromFusion = 0;
            if (chr.hasSkill(HYPOGRAM_FIELD_FUSION) && tsm.getOptByCTSAndSkill(IndieDamR, HYPOGRAM_FIELD_FUSION) != null) {
                SkillInfo fsi = SkillData.getSkillInfoById(HYPOGRAM_FIELD_FUSION);
                byte fslv = (byte) chr.getSkillLevel(HYPOGRAM_FIELD_FUSION);
                extraAtomFromFusion = fsi.getValue(x, fslv);
            }
            int atomCount = si.getValue(x, slv) + extraAtomFromFusion;
            Random random = new Random();
            for (int i = 0; i < atomCount; i++) {
                int firstImpact = 5 + random.nextInt(6);
                int secondImpact = 5 + random.nextInt(6);
                int angle = random.nextInt(180);
                ForceAtomInfo fai = new ForceAtomInfo(1, fae.getInc(), firstImpact, secondImpact,
                        angle, 0, curTime, 0, AEGIS_SYSTEM_ATOM, new Position(0, 0));
                faiList.add(fai);
                mobList.add(mobID);
            }
            chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae, true,
                    mobList, AEGIS_SYSTEM_ATOM, faiList, null, 0, 0,
                    null, 0, null, 0));
            var customCD = swGetCustomCDOnRockets(tsm, AEGIS_SYSTEM);
            chr.addSkillCoolTime(AEGIS_SYSTEM_ATOM, customCD != -1 ? customCD : si.getValue(y, slv));
        }
    }

    @Override
    public void stopTimers() {
        if (overloadTimer != null) {
            overloadTimer.cancel(true);
        }
        overloadTimer = null;
        if (supplyTimer != null) {
            supplyTimer.cancel(true);
        }
        supplyTimer = null;
        super.stopTimers();
    }

    @Override
    public void handleSkillRemove(Char chr, int skillID, Map<CharacterTemporaryStat, Option> removedOptions) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        switch (skillID) {
            case CORE_OVERLOAD_BUFF:
                if (overloadTimer != null) {
                    overloadTimer.cancel(false); // may run from the overload tick itself
                }
                overloadTimer = null;

                tsm.removeStatsBySkill(CORE_OVERLOAD_ATTACK + 100);
                tsm.removeStatsBySkill(CORE_OVERLOAD_BUFF + 100); // the per-energy damage bonus from updateSupply
                tsm.removeStatsBySkill(CORE_OVERLOAD_BURST);
                tsm.removeStatsBySkill(skillID);
                supply = supply > 20 ? 20 : supply;
                updateSupply();
                break;
        }

        super.handleSkillRemove(chr, skillID, removedOptions);
    }

    @Override
    public int alterMpCon(int mpCon, int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(OverloadMode) && skillId == BEAM_DANCE) {
            return 0;
        }

        return super.alterMpCon(mpCon, skillId, slv, attackInfo, sui, source);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        Item item = ItemData.getItemDeepCopy(DUAL_CORE_CONTROLLER); // Secondary
        item.setBagIndex(BodyPart.Shield.getVal());
        chr.getEquippedInventory().addItem(item);
        cs.setPosMap(EDELSTEIN_OUTSKIRTS_1_FIELD); // the tutorial (xn_tuto_0_0), which ends in the Edelstein hideout (310010000)
        cs.setLevel(10);
        //cs.setJob(3600);
        cs.setStr(4);
        cs.setInt(4);
        cs.setDex(4);
        cs.setLuk(4);
        cs.setAp(4 + chr.getLevel() * 5);
        cs.setMaxHp(cs.getMaxMp() + 50);
        // made at level 10: the HP and MP its nine level-ups would have given
        int[] perLevel = net.swordie.ms.constants.GameConstants.getHpMpPerLevel((short) JobConstants.JobEnum.XENON_1.getJobId());
        cs.setMaxHp(cs.getMaxHp() + 9 * perLevel[0]);
        cs.setHp(cs.getMaxHp());
        cs.setMaxMp(cs.getMaxMp() + 9 * perLevel[1]);
        cs.setMp(cs.getMaxMp());
        byte jobLevel = (byte) JobConstants.getJobLevel(chr.getJob());
        int currentSP = cs.getExtendSP().getSpByJobLevel(jobLevel);
        cs.getExtendSP().setSpToJobLevel(jobLevel, currentSP + 5);
        chr.setJob(JobConstants.JobEnum.XENON_1.getJobId());
    }
}


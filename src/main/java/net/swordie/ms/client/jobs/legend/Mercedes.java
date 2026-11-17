package net.swordie.ms.client.jobs.legend;

import net.swordie.ms.client.jobs.adventurer.archer.Marksman;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.avatar.EarStyle;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatBase;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Mercedes extends Job {
    public static final int UNFADING_GLORY = 23141500;
    public static final int UNFADING_GLORY_SPIRIT = 23141501;
    //Link Skill = return skill

    public static final int ELVEN_GRACE = 20020112;
    public static final int UPDRAFT = 20020111;
    public static final int ELVEN_HEALING = 20020109;

    public static final int DUAL_BOWGUN_BOOSTER = 23101002; //Buff
    public static final int RISING_RUSH_1 = 23101001;
    public static final int RISING_RUSH_2 = 23101007; // Lifting debuff

    public static final int STUNNING_STRIKES = 23111000; //Special Attack
    public static final int UNICORN_SPIKE = 23111002; //Special Attack
    public static final int IGNIS_ROAR = 23110004; //Buff
    public static final int WATER_SHIELD = 23111005; //Buff
    public static final int ELEMENTAL_KNIGHTS_BLUE = 23111008; //Summon
    public static final int ELEMENTAL_KNIGHTS_RED = 23111009; //Summon
    public static final int ELEMENTAL_KNIGHTS_PURPLE = 23111010; //Summon
    public static final int LEAP_TORNADO = 23111001;

    public static final int SPIKES_ROYALE = 23121002;  //Special Attack
    public static final int STAGGERING_STRIKES = 23120013; //Special Attack
    public static final int ANCIENT_WARDING = 23121004; //Buff
    public static final int MAPLE_WARRIOR_MERC = 23121005; //Buff
    public static final int LIGHTNING_EDGE = 23121003; //Debuff mobs
    public static final int HEROS_WILL_MERC = 23121008;
    public static final int ISHTAR_RING = 23121000;
    public static final int ROLLING_MOONSAULT = 23121011;
    public static final int SPIRIT_NIMBLE_FLIGHT_H = 23121014;
    public static final int SPIRIT_NIMBLE_FLIGHT_V = 23121015;

    public static final int HEROIC_MEMORIES_MERC = 23121053;
    public static final int ELVISH_BLESSING = 23121054;
    public static final int WRATH_OF_ENLIL = 23121052;

    //Final Attack
    public static final int FINAL_ATTACK_DBG = 23100006;
    public static final int ADVANCED_FINAL_ATTACK = 23120012;
    public static final int HEXA_FINAL_ATTACK = 23140017;

    public static final int SPIRIT_OF_ELLUEL = 400031007; // ExtraSkillCTS
    public static final int SYLVIDIAS_FLIGHT = 400031017;
    public static final int BREATH_OF_IRKALLA = 400031011;
    public static final int IRKILLAS_WRATH = 400031024;
    public static final int ROYAL_KNIGHTS = 400031044;
    public static final int ROYAL_KNIGHTS_SA = 400031045;
    public static final int HEXA_ISHTAR_RING = 23141000; // HEXA Ishtar's Ring
    public static final int HEXA_STUNNING_STRIKES = 23141010; // HEXA Stunning Strikes
    public static final int HEXA_UNICORN_SPIKE = 23141011; // HEXA Unicorn Spike
    public static final int HEXA_SPIKES_ROYALE = 23141005; // HEXA Spikes Royale
    public static final int HEXA_ELEMENTAL_KNIGHTS_BLUE = 23141013; // HEXA Elemental Knights
    public static final int INFILTRATE = 20021001;
    public static final int HEXA_ELEMENTAL_KNIGHTS = 23141014;
    public static final int HEXA_ELEMENTAL_KNIGHTS_2 = 23141015;
    public static final int HEXA_ELEMENTAL_KNIGHTS_3 = 23141016;
    public static final int SPIRIT_OF_ELLUEL_2 = 400031009;

    private static final int[] addedSkills = new int[]{
            ELVEN_GRACE,
            UPDRAFT,
            ELVEN_HEALING,
    };

    private static final int[] summonAttacks = new int[]{
            ELEMENTAL_KNIGHTS_BLUE,
            ELEMENTAL_KNIGHTS_RED,
            ELEMENTAL_KNIGHTS_PURPLE,
            HEXA_ELEMENTAL_KNIGHTS, HEXA_ELEMENTAL_KNIGHTS_2, HEXA_ELEMENTAL_KNIGHTS_3, // HEXA Elemental Knights
    };


    private static List<Integer> eleKnights = Arrays.asList(
            ELEMENTAL_KNIGHTS_BLUE,
            ELEMENTAL_KNIGHTS_RED,
            ELEMENTAL_KNIGHTS_PURPLE
    );
    private static final int IRKALLA_PROP = 50; // hardcode-ok
    private static final int RISING_RUSH_SEC = 4; // hardcode-ok
    // data x=2600 / y=1600 may be these (unlabelled)
    private static final int ROYAL_KNIGHTS_CAST_INVINCIBLE_SEC = 3; // hardcode-ok
    private static final int ROYAL_KNIGHTS_END_INVINCIBLE_SEC = 2; // hardcode-ok
    public static final int MAGIC_ARROWS = 1352000;
    public static final int AFTER_THE_BATTLE_FIELD = 910150000;

    private int lastAttackSkill = 0;
    private ScheduledFuture elvenHealingTimer;
    private Map<Integer, Long> swCustomSpiritOfElluelMap = new HashMap<>();

    public Mercedes(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            startElvenHealing();
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isMercedes(id);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        chr.getAvatarData().getAvatarLook().setDrawElfEar(true);
        Item item = ItemData.getItemDeepCopy(MAGIC_ARROWS); // Secondary
        item.setBagIndex(BodyPart.Shield.getVal());
        chr.getEquippedInventory().addItem(item);
        chr.getAvatarData().getCharacterStat().setPosMap(AFTER_THE_BATTLE_FIELD);

        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        cs.setLevel(10);
        cs.setDex(70);
        cs.setMaxHp(300);
        cs.setMaxMp(200);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.mhp, chr.getStat(Stat.mhp));
        stats.put(Stat.mmp, chr.getStat(Stat.mmp));
        chr.write(WvsContext.statChanged(stats));

        chr.getAvatarData().getAvatarLook().setEarStyle(EarStyle.Elf);
    }


    private void summonEleKnights() {
        int randSkillId = Util.getRandomFromCollection(eleKnights);
        int knightLv = chr.getSkillLevel(ELEMENTAL_KNIGHTS_BLUE);
        if (chr.hasSkill(HEXA_ELEMENTAL_KNIGHTS_BLUE)) {
            // HEXA knights 23141014-16 in the same blue/red/purple order
            randSkillId = HEXA_ELEMENTAL_KNIGHTS_BLUE + 1 + eleKnights.indexOf(randSkillId);
            knightLv = chr.getSkillLevel(HEXA_ELEMENTAL_KNIGHTS_BLUE);
        }
        Summon summon = Summon.getSummonBy(chr, randSkillId, knightLv);
        summon.setMoveAbility(MoveAbility.Fly);
        chr.getField().spawnSummonAndRemoveOld(summon);
        // GMS: ElementalKnight n 1 on the cast (its icon); kept for the knight's time
        var ksi = SkillData.getSkillInfoById(randSkillId);
        int dur = ksi == null ? 0 : ksi.getValue(time, knightLv);
        if (dur > 0) {
            var tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValue(ElementalKnight, new Option(1, ELEMENTAL_KNIGHTS_BLUE, dur));
            tsm.sendSetStatPacket();
        }
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
        incrementIgnisRoarStackCount(tsm, attackInfo);
        if (!chr.hasSkillOnCooldown(BREATH_OF_IRKALLA) && tsm.getOptByCTSAndSkill(IndieSummon, SPIRIT_OF_ELLUEL) != null && Util.succeedProp(IRKALLA_PROP)) {
            chr.write(UserLocal.userBonusAttackRequest(BREATH_OF_IRKALLA));
        }

        if (hasHitMobs && canProcRoyalKnights() && attackInfo.skillId != ROYAL_KNIGHTS_SA) {
            procRoyalKnights(attackInfo);
        }

        // [Mercedes] Custom Skill Change
        swAddedDamageToSpiritOfElluel(attackInfo);


        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case ISHTAR_RING:
            case HEXA_ISHTAR_RING:
                if (hasHitMobs && tsm.hasStat(AddAttackCount) && chr.hasSkill(IGNIS_ROAR)) {
                    int extensionMS = si.getValue(y, slv);
                    o1.nOption = tsm.getOption(AddAttackCount).nOption;
                    o1.rOption = tsm.getOption(AddAttackCount).rOption;
                    o1.tOption = (int) tsm.getRemainingTime(AddAttackCount, o1.rOption) + extensionMS;
                    o1.setInMillis(true);
                    tsm.putCharacterStatValue(AddAttackCount, o1, true);
                    tsm.sendSetStatPacket();
                }
                break;
            case RISING_RUSH_2:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = RISING_RUSH_SEC;
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null || mob.isBoss()) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.Lifting, o1);
                }
                break;
            case STUNNING_STRIKES:
            case HEXA_STUNNING_STRIKES:
                SkillInfo stunningStrikes = SkillData.getSkillInfoById(STUNNING_STRIKES);
                int proc = stunningStrikes.getValue(prop, slv);
                o1.nOption = 1;
                o1.rOption = STUNNING_STRIKES;
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(proc)) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null || mob.isBoss()) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                    }
                }
                break;
            case STAGGERING_STRIKES:
                SkillInfo staggeringStrikes = SkillData.getSkillInfoById(STAGGERING_STRIKES);
                int procc = staggeringStrikes.getValue(prop, slv);
                o1.nOption = 1;
                o1.rOption = STAGGERING_STRIKES;
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(procc)) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null || mob.isBoss()) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                    }
                }
                break;
            case UNICORN_SPIKE:
            case HEXA_UNICORN_SPIKE:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.FinalDmgReceived, o1);
                    }
                }
                break;
            case SPIKES_ROYALE:
            case HEXA_SPIKES_ROYALE:
                o1.nOption = si.getValue(x, slv); // x is already negative: -(slv+10) == -#y
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.PDR, o1);
                    mts.addStatOptionsAndBroadcast(chr, MobStat.MDR, o1);
                }
                break;
            case LIGHTNING_EDGE:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.AddDamSkill, o1);
                }
                break;
            case ELEMENTAL_KNIGHTS_BLUE:
            case HEXA_ELEMENTAL_KNIGHTS_BLUE:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(subTime, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null || mob.isBoss()) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Freeze, o1);
                    }
                }
                break;
            case ELEMENTAL_KNIGHTS_RED:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.createAndAddBurnedInfo(chr, attackInfo.skillId, chr.getSkillLevel(ELEMENTAL_KNIGHTS_BLUE));
                }
                break;
            case BREATH_OF_IRKALLA:
                chr.addSkillCoolTime(BREATH_OF_IRKALLA, 10 * 1000);
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    private void swAddedDamageToSpiritOfElluel(AttackInfo attackInfo) {
        if (!SkillChangeConstants.SPIRIT_OF_ELLUEL_FINISHER) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        if (attackInfo.bySummonedID == 0 || !tsm.hasStatBySkillId(SPIRIT_OF_ELLUEL)) {
            return;
        }

        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            var mobId = mai.mobId;
            Mob mob = mai.mob;
            if (mob == null) {
                continue;
            }

            long totalDmg = mai.totalDamage;
            if (this.swCustomSpiritOfElluelMap.containsKey(mobId)) {
                if (!mai.mobDies) {
                    long curDmg = this.swCustomSpiritOfElluelMap.get(mobId);
                    this.swCustomSpiritOfElluelMap.put(mobId, totalDmg + curDmg);
                } else {
                    this.swCustomSpiritOfElluelMap.remove(mobId);
                }
            } else {
                if (!mai.mobDies) {
                    this.swCustomSpiritOfElluelMap.put(mobId, totalDmg);
                }
            }
        }
    }

    private void swSpiritOfElluelFinisher() {
        if (!SkillChangeConstants.SPIRIT_OF_ELLUEL_FINISHER) {
            return;
        }
        Field field = chr.getField();
        var slv = chr.getSkillLevel(SPIRIT_OF_ELLUEL);
        var hits = SkillChangeConstants.SPIRIT_OF_ELLUEL_HITS;
        for (Map.Entry<Integer, Long> entry : this.swCustomSpiritOfElluelMap.entrySet()) {
            int mobId = entry.getKey();
            long dmg = entry.getValue();

            if (dmg <= 0) {
                continue;
            }

            Life l = field.getLifeByObjectID(mobId);
            if (!(l instanceof Mob)) {
                continue;
            }
            Mob mob = (Mob) l;

            mob.damageBySkill(chr, Marksman.PERFECT_SHOT_HIT, slv, hits, Math.min(GameConstants.DAMAGE_CAP * hits, dmg));
        }
        clearCustomSpiritOfElluelMap();
    }

    public void clearCustomSpiritOfElluelMap() {
        this.swCustomSpiritOfElluelMap.clear();
    }

    @Override
    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        if (cts.equals(IndieSummon) && (o.rOption >= SPIRIT_OF_ELLUEL && o.rOption <= SPIRIT_OF_ELLUEL_2)) {
            swSpiritOfElluelFinisher();
        }
        if (cts.equals(RoyalKnights)) {
            var tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, ROYAL_KNIGHTS, ROYAL_KNIGHTS_END_INVINCIBLE_SEC), true);
            tsm.sendSetStatPacket();
        }
        super.handleRemoveCTS(cts, o);
    }

    private void giveMaxIgnisRoarStacks(TemporaryStatManager tsm) {
        Skill ignis = chr.getSkill(IGNIS_ROAR);
        if (ignis == null) {
            return;
        }
        SkillInfo ignisInfo = SkillData.getSkillInfoById(IGNIS_ROAR);
        int islv = ignis.getCurrentLevel();
        tsm.putCharacterStatValue(AddAttackCount,
                new Option(ignisInfo.getValue(y, islv), IGNIS_ROAR, ignisInfo.getValue(subTime, islv)));
    }

    private void incrementIgnisRoarStackCount(TemporaryStatManager tsm, AttackInfo attackInfo) {
        if (Util.makeSet(summonAttacks).contains(attackInfo.skillId)
                || ((getFinalAtkSkill() != null) && attackInfo.skillId == getFinalAtkSkill().getSkillId())
                || attackInfo.skillId == lastAttackSkill) {
            return;
        }
        Option o = new Option();
        Skill skill = chr.getSkill(IGNIS_ROAR);
        if (skill == null) {
            return;
        }
        boolean specialLink = (lastAttackSkill == RISING_RUSH_1 && attackInfo.skillId == RISING_RUSH_2)
                || (lastAttackSkill == ROLLING_MOONSAULT && attackInfo.skillId == LEAP_TORNADO)
                || (lastAttackSkill == SPIKES_ROYALE && attackInfo.skillId == LEAP_TORNADO);

        SkillInfo ignisRoarInfo = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(lastAttackSkill);
        lastAttackSkill = attackInfo.skillId;
        if (!specialLink && (si == null || si.getAddAttackSkills().stream().noneMatch(aas -> aas == attackInfo.skillId))) {
            return;
        }

        if (attackInfo.skillId != SPIKES_ROYALE) {
            chr.reduceSkillCoolTime(SPIKES_ROYALE, 1000);
        }
        if (attackInfo.skillId != UNICORN_SPIKE) {
            chr.reduceSkillCoolTime(UNICORN_SPIKE, 1000);
        }
        if (attackInfo.skillId != WRATH_OF_ENLIL) {
            chr.reduceSkillCoolTime(WRATH_OF_ENLIL, 1000);
        }
        chr.reduceSkillCoolTime(BREATH_OF_IRKALLA, 1000);

        // [Mercedes] Custom Skill Change
        chr.reduceSkillCoolTime(IRKILLAS_WRATH, 1000);

        int amount = 1;
        if (tsm.hasStat(AddAttackCount)) {
            amount = tsm.getOption(AddAttackCount).nOption;
            if (amount < ignisRoarInfo.getValue(y, slv)) {
                amount++;
            }
        }
        if (tsm.hasStatBySkillId(SYLVIDIAS_FLIGHT)) {
            amount = ignisRoarInfo.getValue(y, slv);
        }
        o.nOption = amount;
        o.rOption = IGNIS_ROAR;
        o.tOption = ignisRoarInfo.getValue(subTime, slv);
        tsm.putCharacterStatValue(AddAttackCount, o);
        tsm.sendSetStatPacket();
    }

    @Override
    public int getFinalAttackSkill() {
        Skill faSkill = getFinalAtkSkill();
        if (faSkill != null) {
            SkillInfo si = SkillData.getSkillInfoById(faSkill.getSkillId());
            int slv = faSkill.getCurrentLevel();
            int proc = si.getValue(prop, slv);
            if (Util.succeedProp(proc)) {
                return faSkill.getSkillId();
            }
        }
        return super.getFinalAttackSkill();
    }

    private Skill getFinalAtkSkill() {
        Skill skill = null;
        if (chr.hasSkill(FINAL_ATTACK_DBG)) {
            skill = chr.getSkill(FINAL_ATTACK_DBG);
        }
        if (chr.hasSkill(ADVANCED_FINAL_ATTACK)) {
            skill = chr.getSkill(ADVANCED_FINAL_ATTACK);
        }
        if (chr.hasSkill(HEXA_FINAL_ATTACK)) {
            skill = chr.getSkill(HEXA_FINAL_ATTACK);
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
        Summon summon;
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (skillId) {
            case UNFADING_GLORY_SPIRIT: // Origin follow-up summon, when the client uses its id
                if (chr.getSkillLevel(UNFADING_GLORY) > 0) {
                    Summon originSummon = Summon.getSummonBy(chr, UNFADING_GLORY_SPIRIT, chr.getSkillLevel(UNFADING_GLORY));
                    originSummon.setFlyMob(false);
                    originSummon.setMoveAbility(net.swordie.ms.enums.MoveAbility.Stop);
                    originSummon.setAssistType(net.swordie.ms.enums.AssistType.Attack);
                    chr.getField().spawnSummonAndRemoveOld(originSummon);
                }
                break;
            case DUAL_BOWGUN_BOOSTER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case IGNIS_ROAR:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IgnisRore, o1);
                o2.nOption = si.getValue(indiePad, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, o2);
                break;
            case WATER_SHIELD:
                // v265: a barrier that blocks all attacks for time sec; its resistances are passive now
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                break;
            case ANCIENT_WARDING:
                o1.nOption = si.getValue(emhp, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EMHP, o1);
                o2.nOption = si.getValue(indiePadR, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePADR, o2);
                break;
            case HEROIC_MEMORIES_MERC:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case ELVISH_BLESSING:
                o1.nOption = si.getValue(indiePad, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, o1);
                break; // v271: Attack Power only (no stance x in the data)
            case ELEMENTAL_KNIGHTS_BLUE:
            case HEXA_ELEMENTAL_KNIGHTS_BLUE:
                summonEleKnights();
                break;

            case SPIRIT_OF_ELLUEL:
                var tOpt = 0;
                for (int i = skillId; i < skillId + 3; i++) {
                    summon = Summon.getSummonByNoCTS(chr, i, slv);
                    tOpt = summon.getSummonTerm() / 1000;
                    summon.setFlyMob(false);
                    summon.setAvatarLook(chr.getAvatarData().getAvatarLook());
                    summon.setMoveAbility(MoveAbility.WalkClone);
                    summon.setActionDelay((i - (skillId - 1)) * 400);
                    summon.setMovementDelay((i - (skillId - 1)) * 30);
                    field.spawnSummonAndRemoveOld(summon);
                    o1.summons.add(summon);
                }
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = tOpt;
                tsm.putCharacterStatValue(IndieSummon, o1);
                break;
            case SYLVIDIAS_FLIGHT:
                if (si.getVehicleId() <= 0) {
                    // v265: a charge attack, no mount; it still fills Ignis Roar
                    giveMaxIgnisRoarStacks(tsm);
                    break;
                }
                TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.RideVehicle);
                if (tsm.hasStat(RideVehicle)) {
                    tsm.removeStat(RideVehicle);
                }
                tsb.setNOption(si.getVehicleId());
                tsb.setROption(skillId);
                tsb.setExpireTerm(si.getValue(time, slv));
                tsb.setDynamicTermSet(false);
                tsm.putCharacterStatValue(RideVehicle, tsb.getOption());
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieStance, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieStance, o1);
                o2.rOption = skillId;
                o2.nOption = si.getValue(indiePadR, slv);
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePADR, o2);
                o3.rOption = skillId;
                o3.nOption = si.getValue(indieDamReduceR, slv);
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamReduceR, o3);
                tsm.sendSetStatPacket();

                Skill skillIgnisRoar = chr.getSkill(IGNIS_ROAR);
                if (skillIgnisRoar != null) {
                    SkillInfo ignisRoarInfo = SkillData.getSkillInfoById(skillIgnisRoar.getSkillId());
                    int ignisRoadSlv = skillIgnisRoar.getCurrentLevel();
                    o4.nOption = ignisRoarInfo.getValue(y, ignisRoadSlv);
                    o4.rOption = IGNIS_ROAR;
                    o4.tOption = ignisRoarInfo.getValue(subTime, ignisRoadSlv);
                    tsm.putCharacterStatValue(AddAttackCount, o4);
                }
                break;
            case ROYAL_KNIGHTS:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(RoyalKnights, o1);
                o2.nOption = 1;
                o2.rOption = skillId;
                o2.tOption = ROYAL_KNIGHTS_CAST_INVINCIBLE_SEC;
                tsm.putCharacterStatValue(IndieNotDamaged, o2, true);
                break;
        }
        tsm.sendSetStatPacket();
    }

    private boolean canProcRoyalKnights() {
        return chr.hasSkill(ROYAL_KNIGHTS) && chr.getTemporaryStatManager().hasStat(RoyalKnights) && !chr.hasSkillOnCooldown(ROYAL_KNIGHTS_SA);
    }

    private void procRoyalKnights(AttackInfo attackInfo) {
        Skill skill = chr.getSkill(ROYAL_KNIGHTS);
        if (skill == null) {
            return;
        }
        var si = SkillData.getSkillInfoById(ROYAL_KNIGHTS_SA);
        var slv = skill.getCurrentLevel();
        List<SecondAtom> saList = new ArrayList<>();
        var bulletCount = si.getValue(SkillStat.bulletCount, slv);
        var sai = si.getSecondAtomInfos().get(0);
        for (var i = 0; i < bulletCount; i++) {
            var sa = SecondAtom.createFromInfo(chr, si.getSkillId(), sai);
            Mob mob = (Mob) chr.getField().getLifeByObjectID(Util.getRandomFromCollection(attackInfo.mobAttackInfo).mobId);
            if (mob == null) {
                continue;
            }
            sa.setTargetId(mob.getObjectId());

            saList.add(sa);
        }

        if (saList.size() > 0) {
            chr.spawnSecondAtoms(saList);
            chr.addSkillCoolTime(ROYAL_KNIGHTS_SA, si.getValue(cooltimeMS, slv));
        }
    }

    private void startElvenHealing() {
        if (!chr.hasSkill(ELVEN_HEALING)) {
            return;
        }

        int interval = 4;
        EventManager.stopTimer(elvenHealingTimer);
        if (interval > 0) {
            elvenHealingTimer = EventManager.addFixedRateEvent(this::healByElvenHealing, interval, interval, TimeUnit.SECONDS);
        }
    }

    private void healByElvenHealing() {
        if (chr != null && chr.getField() != null && chr.hasSkill(ELVEN_HEALING)) {
            Skill skill = chr.getSkill(ELVEN_HEALING);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            int healHP = (int) ((double) (chr.getMaxHP() * si.getValue(x, slv)) / 100F);
            int healMP = (int) ((double) (chr.getMaxMP() * si.getValue(x, slv)) / 100F);

            chr.heal(healHP, true, false);
            chr.healMP(healMP);
        } else if (elvenHealingTimer != null) {
            elvenHealingTimer.cancel(true);
        }
    }


    // Hit related methods ---------------------------------------------------------------------------------------------


    @Override
    public void stopTimers() {
        clearCustomSpiritOfElluelMap();
        if (elvenHealingTimer != null) {
            elvenHealingTimer.cancel(true);
        }
        super.stopTimers();
    }

    @Override
    public void updateTimerSkill() {
        EventManager.stopTimer(elvenHealingTimer);
        startElvenHealing();
        super.updateTimerSkill();
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        super.onWarp(oldField, newField);
        clearCustomSpiritOfElluelMap();
    }
}

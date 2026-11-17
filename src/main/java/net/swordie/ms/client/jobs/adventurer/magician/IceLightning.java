package net.swordie.ms.client.jobs.adventurer.magician;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.debuff.DebuffInfo;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Foothold;

import java.util.*;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class IceLightning extends Magician {
    public static final int TELEPORT_BOOST = 2211017; // on/off: longer teleport
    public static final int COLD_BEAM = 2201008;
    public static final int FREEZING_CRUSH = 2200011;
    public static final int FROST_CLUTCH = 2220015;
    public static final int MP_EATER_IL = 2200000;
    public static final int MAGIC_BOOSTER_IL = 2201010;
    public static final int CHILLING_STEP = -2201009; // v265 only: v271 gives 2201009 to Absolute Zero Aura
    public static final int MEDITATION_IL = 2201001;


    public static final int ICE_STRIKE = 2211002;
    public static final int GLACIER_CHAIN = 2211010;
    public static final int TELEPORT_MASTERY_IL = 2211007;
    public static final int ELEMENTAL_DECREASE_IL = 2211008;


    public static final int CHAIN_LIGHTNING = 2221006;
    public static final int FREEZING_BREATH = 2221011;
    public static final int BLIZZARD = 2221007;
    public static final int BLIZZARD_FA = 2220014;
    public static final int HEXA_BLIZZARD = 2241003;
    public static final int HEXA_BLIZZARD_FA = 2241004;
    public static final int FROZEN_ORB = 2221012;
    public static final int INFINITY_IL = 2220004; // v271 passive (v265 2221004)
    public static final int ELQUINES = 2221005;
    public static final int ARCANE_AIM_IL = 2220010;
    public static final int MAPLE_WARRIOR_IL = 2221000;
    public static final int HEROS_WILL_IL = 2221008;
    public static final int ELEMENTAL_ADAPTATION_IL = 2211012;
    public static final int THUNDER_STORM = 2211011;
    public static final int TELEPORT_MASTERY_RANGE_IL = 2221045;
    public static final int EPIC_ADVENTURE_IL = 2221053;
    public static final int ABSOLUTE_ZERO_AURA = 2201009; // v271 2nd job (v265 hyper 2221054)
    public static final int GLACIAL_FURY = 2221054; // v271 hyper buff (stat GlacialFury)
    public static final int LIGHTNING_ORB = 2221052;


    // V skills
    public static final int ICE_AGE = 400021002;
    public static final int ICE_AGE_TILE = 400020002;
    public static final int BOLT_BARRAGE = 400021030;
    public static final int BOLT_BARRAGE_TILE = 400021040;
    public static final int BOLT_BARRAGE_TILE_2 = 400021031;
    public static final int SPIRIT_OF_SNOW = 400021067;
    public static final int JUPITER_THUNDER = 400021094;
    public static final int JUPITER_THUNDER_2 = 400021112;
    public static final int HEXA_ELQUINES = 2241009; // HEXA Elquines
    public static final int HEXA_CHAIN_LIGHTNING = 2241000; // HEXA Chain Lightning
    public static final int HEXA_CHAIN_LIGHTNING_TILE = 2241001; // HEXA Chain Lightning
    public static final int FROZEN_LIGHTNING = 2241503;
    public static final int FROZEN_LIGHTNING_BUFF = 2241504;
    public static final int HEXA_THUNDER_STORM = 2241007; // HEXA Thunder Sphere
    public static final int THUNDER_SPHERE_STAY = 2211015; // Thunder Sphere left in place
    public static final int HEXA_THUNDER_SPHERE_STAY = 2241008;
    public static final int CRYO_SHOCK = 2240006;
    // no clear Skill.wz stat for these (Elquines' subTime=8 isn't labelled)
    private static final int ELQUINES_FREEZE = 5; // hardcode-ok
    private static final int ELQUINES_FREEZE_SEC = 5; // hardcode-ok
    private static final int BOLT_BARRAGE_TILE_MS = 1000; // hardcode-ok
    private static final int CHILLING_STEP_BOSSING = 2; // hardcode-ok: client mode (1 = normal)



    public IceLightning(Char chr) {
        super(chr);
    }


    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isIceLightning(id);
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
        //Freezing Crush / Frozen Clutch
        applyFreezingCrushOnMob(attackInfo, attackInfo.skillId);

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        if (attackInfo.skillId == HEXA_CHAIN_LIGHTNING && hasHitMobs) {
            hexaChainLightningTiles(attackInfo);
        } else if (attackInfo.skillId == FROZEN_LIGHTNING && !tsm.hasStat(SixthFrozenLightning)) {
            o1.nOption = 1;
            o1.rOption = FROZEN_LIGHTNING_BUFF;
            o1.tOption = SkillData.getSkillInfoById(FROZEN_LIGHTNING_BUFF).getValue(time, 1);
            tsm.putCharacterStatValue(SixthFrozenLightning, o1);
            tsm.sendSetStatPacket();
            o1 = new Option();
        }
        switch (attackInfo.skillId) {
            case ELQUINES:
            case HEXA_ELQUINES:
                o1.nOption = ELQUINES_FREEZE;
                o1.rOption = skillID;
                o1.tOption = ELQUINES_FREEZE_SEC;
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);

                    // [Ice Lightning] Custom Skill Change
                    if (mob != null && chr.hasSkill(ICE_AGE) && SkillChangeConstants.ELQUINES_PROC_ICE_AGE_TILE
                            && SkillData.getSkillInfoById(ICE_AGE_TILE) != null) {
                        AffectedArea aa = AffectedArea.getPassiveAA(chr, ICE_AGE_TILE, chr.getSkillLevel(ICE_AGE));
                        aa.setPosition(mob.getPosition());
                        aa.setRect(aa.getPosition().getRectAround(new Rect(-100, -100, 100, 50)));
                        aa.setCurFoothold();
                        aa.setDuration(SkillChangeConstants.ELQUINES_ICE_AGE_TILE_DURATION);
                        aa.setDelay((short) 11);
                        chr.getField().spawnAffectedArea(aa);
                    }

                    if (mob == null || mob.isBoss()) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.Freeze, o1);
                }
                break;
            case TELEPORT_MASTERY_IL:
            case CHAIN_LIGHTNING:
            case HEXA_CHAIN_LIGHTNING:
            case Bishop.SHINING_RAY:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds(), new DebuffInfo(true,
                        si.getValue(attackInfo.skillId == TELEPORT_MASTERY_IL ? subProp : prop, slv)));
                break;
            case FREEZING_BREATH:
                Rect rect = chr.getPosition().getRectAround(si.getFirstRect());
                if (!chr.isLeft()) {
                    rect = rect.horizontalFlipAround(chr.getPosition().getX());
                }
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);

                o2.nOption = si.getValue(x, slv); // already negative (= -v, Weapon DEF)
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);

                o3.nOption = si.getValue(y, slv); // already negative (= -w, Magic DEF)
                o3.rOption = skillID;
                o3.tOption = si.getValue(time, slv);
                for (Life life : chr.getField().getLifesInRect(rect)) {
                    if (life instanceof Mob && ((Mob) life).getHp() > 0) {
                        Mob mob = (Mob) life;
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Freeze, o1);
                        mts.addStatOptionsAndBroadcast(chr, MobStat.PDR, o2);
                        mts.addStatOptionsAndBroadcast(chr, MobStat.MDR, o3);
                    }
                }
                if (tsm.getOptByCTSAndSkill(NotDamaged, skillID) == null) {
                    o1.nOption = 1;
                    o1.rOption = skillID;
                    tsm.putCharacterStatValue(NotDamaged, o1);
                    tsm.sendSetStatPacket();
                }
                break;
            case ICE_AGE:
                rect = chr.getRectAround(si.getFirstRect());
                if (!chr.isLeft()) {
                    rect = rect.horizontalFlipAround(chr.getPosition().getX());
                }
                var si2 = SkillData.getSkillInfoById(ICE_AGE_TILE);
                if (si2 == null) { // v271 Frost Ark has no ice tiles (400020002 is gone)
                    break;
                }
                for (Foothold fh : chr.getField().getInfo().getFootholdsInRect(rect)) {
                    AffectedArea aa = AffectedArea.getAffectedArea(chr, attackInfo);
                    aa.setSkillID(ICE_AGE_TILE);
                    aa.setPosition(fh.getRandomPosition());
                    aa.setDelay((short) 8);
                    aa.setRect(aa.getPosition().getRectAround(si2.getFirstRect()));
                    aa.setFh(fh.getId());
                    chr.getField().spawnAffectedArea(aa);
                }
                break;
            case SPIRIT_OF_SNOW:
                applyFreezingCrushOnMob(attackInfo, attackInfo.skillId);
                applyFreezingCrushOnMob(attackInfo, attackInfo.skillId);
                break;
            case BOLT_BARRAGE_TILE:
            case BOLT_BARRAGE_TILE_2:
                Life life = chr.getField().getLifeByObjectID(attackInfo.affectedAreaObjId);
                if (!(life instanceof AffectedArea)) {
                    return;
                }
                chr.getField().removeLife(life);
                break;
            case THUNDER_STORM:
            case HEXA_THUNDER_STORM:
                // [IceLightning] Custom Skill Change
                if (attackInfo.mobCount == 1 && chr.hasSkill(BOLT_BARRAGE) && SkillChangeConstants.BOLT_BARRAGE_PROC_BY_THUNDERSTORM) {
                    Field field = chr.getField();
                    for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                        Mob mob = mai.mob;
                        if (mob == null) {
                            continue;
                        }

                        AffectedArea aa = AffectedArea.getPassiveAA(chr, BOLT_BARRAGE_TILE, chr.getSkillLevel(BOLT_BARRAGE));
                        aa.setPosition(mob.getPosition().add(new Position(Util.getRandom(-100, 100), 0)));
                        aa.setRect(aa.getRectAround(SkillData.getSkillInfoById(BOLT_BARRAGE_TILE).getFirstRect()));
                        aa.setDuration(BOLT_BARRAGE_TILE_MS);
                        aa.setDelay((short) 7);
                        field.spawnAffectedArea(aa);
                    }
                }
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    // HEXA Chain Lightning: a hit may leave electric tiles under the struck monsters (7 s apart)
    private void hexaChainLightningTiles(AttackInfo attackInfo) {
        SkillInfo si = SkillData.getSkillInfoById(HEXA_CHAIN_LIGHTNING_TILE);
        int slv = chr.getSkillLevel(HEXA_CHAIN_LIGHTNING);
        if (si == null || chr.hasSkillOnCooldown(HEXA_CHAIN_LIGHTNING_TILE) || !Util.succeedProp(si.getValue(prop, slv))) {
            return;
        }
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = mai.mob;
            if (mob == null || mob.getHp() <= 0) {
                continue;
            }
            AffectedArea aa = AffectedArea.getPassiveAA(chr, HEXA_CHAIN_LIGHTNING_TILE, slv);
            aa.setPosition(mob.getPosition());
            aa.setRect(aa.getRectAround(si.getFirstRect()));
            aa.setDelay((short) 3);
            chr.getField().spawnAffectedArea(aa);
        }
        chr.addSkillCoolTime(HEXA_CHAIN_LIGHTNING_TILE, 7000);
    }

    private void applyFreezingCrushOnMob(AttackInfo attackInfo, int skillID) {
        if (!chr.hasSkill(FREEZING_CRUSH)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        int slv = chr.getSkillLevel(skillID);
        if (si != null && (si.getElemAttr().contains("i") || si.getElemAttr().contains("l"))) {
            Option o1 = new Option();
            o1.rOption = COLD_BEAM;
            o1.tOption = si.getValue(time, slv);
            boolean isIceSkill = si.getElemAttr().contains("i");
            List<Integer> cryoShockTargets = new ArrayList<>();
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                MobTemporaryStat mts = mob.getTemporaryStat();

                int counter = 1;
                if (mts.hasCurrentMobStat(MobStat.Speed)) {
                    counter = mts.getCurrentOptionsByMobStat(MobStat.Speed).mOption;
                    if (counter >= 5 && !isIceSkill && chr.hasSkill(CRYO_SHOCK)) {
                        // Cryo Shock: a lightning hit on a fully frozen enemy shatters the stacks
                        mts.removeMobStat(MobStat.Speed);
                        cryoShockTargets.add(mob.getObjectId());
                        continue;
                    }
                    if (counter < 5 && isIceSkill) {
                        counter++;
                    } else if (counter > 0 && !isIceSkill) {
                        counter--;
                    }
                }
                o1.nOption = -15 * counter;
                o1.mOption = counter;
                mts.addStatOptionsAndBroadcast(chr, MobStat.Speed, o1);
            }
            if (!cryoShockTargets.isEmpty() && skillID != CRYO_SHOCK) {
                chr.write(UserLocal.userBonusAttackRequest(CRYO_SHOCK, cryoShockTargets));
            }
        }
    }

    @Override
    public int getFinalAttackSkill() {
        if (chr.hasSkill(HEXA_BLIZZARD)) {
            SkillInfo hsi = SkillData.getSkillInfoById(HEXA_BLIZZARD_FA);
            return hsi != null && Util.succeedProp(hsi.getValue(prop, chr.getSkillLevel(HEXA_BLIZZARD))) ? HEXA_BLIZZARD_FA : 0;
        }
        SkillInfo si = SkillData.getSkillInfoById(BLIZZARD_FA);
        if (chr.getSkill(BLIZZARD) != null && chr.hasSkillOnCooldown(BLIZZARD)) {
            int slv = chr.getSkill(BLIZZARD).getCurrentLevel();

            // [IceLightning] Custom Skill Change
            if (SkillChangeConstants.BYPASS_PROC_ON_BLIZZARD_FA || Util.succeedProp(si.getValue(prop, slv))) {
                return BLIZZARD_FA;
            }
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
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (skillId) {
            case TELEPORT:
                // [Ice Lightning] Custom Skill Change
                if (SkillChangeConstants.CHILLING_STEP_BOSSING_MODE) {
                    if (tsm.hasStat(ChillingStep) && chr.hasSkill(IceLightning.CHILLING_STEP)) {
                        if (tsm.getOption(ChillingStep).nOption == 1) {
                            createChillStepAA();
                        } else if (tsm.getOption(ChillingStep).nOption == 2) {
                            createCustomChillStepAA();
                        }
                    }
                } else {
                    if (tsm.hasStat(ChillingStep) && chr.hasSkill(IceLightning.CHILLING_STEP)) {
                        createChillStepAA();
                    }
                }
                break;
            case GLACIAL_FURY:
                if (si != null) {
                    // max Freeze stacks x, Ice Magic ATT y per stack while it lasts (the client stat GlacialFury)
                    tsm.putCharacterStatValue(GlacialFury, new Option(si.getValue(x, slv), skillId,
                            si.getValue(time, slv)));
                }
                break;
            case ELEMENTAL_ADAPTATION_IL:
                o1.nOption = 1;
                o1.rOption = skillId;
                tsm.putCharacterStatValue(AntiMagicShell, o1);
                break;
            case THUNDER_STORM:
            case HEXA_THUNDER_STORM:
            case THUNDER_SPHERE_STAY:
            case HEXA_THUNDER_SPHERE_STAY: {
                // the moving sphere and the one left in place replace each other
                boolean hexa = skillId == HEXA_THUNDER_STORM || skillId == HEXA_THUNDER_SPHERE_STAY;
                boolean stay = skillId == THUNDER_SPHERE_STAY || skillId == HEXA_THUNDER_SPHERE_STAY;
                int other = hexa ? (stay ? HEXA_THUNDER_STORM : HEXA_THUNDER_SPHERE_STAY) : (stay ? THUNDER_STORM : THUNDER_SPHERE_STAY);
                tsm.removeStatsBySkill(other);
                field.removeSummon(other, chr.getId());
                Summon sphere = Summon.getSummonBy(chr, skillId, slv);
                sphere.setFlyMob(true);
                if (stay) {
                    sphere.setMoveAbility(MoveAbility.Stop);
                }
                field.spawnSummonAndRemoveOld(sphere);
                break;
            }
            case CHILLING_STEP:

                var nOpt = tsm.hasStat(ChillingStep) ? tsm.getOption(ChillingStep).nOption : 0;

                switch (nOpt) {
                    case 0: // Normal ChillStep
                        o1.nOption = 1;
                        o1.rOption = skillId;
                        tsm.putCharacterStatValue(ChillingStep, o1);
                        break;
                    case 1: // Custom ChillStep
                        o1.nOption = CHILLING_STEP_BOSSING;
                        o1.rOption = skillId;
                        tsm.putCharacterStatValue(ChillingStep, o1);
                        break;
                    case 2: // Remove ChillStep
                        tsm.removeStatsBySkill(skillId);
                        break;
                }
                break;
            case ABSOLUTE_ZERO_AURA:
                if (tsm.hasStat(IceAura) && tsm.getOption(IceAura).xOption == tsm.getChr().getId()) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    o1.nOption = slv;
                    o1.rOption = skillId;
                    o1.xOption = chr.getId();
                    o1.bOption = 0; // isPartyMember
                    tsm.putCharacterStatValue(IceAura, o1);

                    if (si.getValue(SkillStat.x, slv) > 0) {
                        o2.nOption = si.getValue(SkillStat.x, slv);
                        o2.rOption = skillId;
                        tsm.putCharacterStatValue(IndieStance, o2);
                    }
                    o3.nOption = si.getValue(SkillStat.y, slv);
                    o3.rOption = skillId;
                    tsm.putCharacterStatValue(DamageReduce, o3);
                    o4.nOption = si.getValue(z, slv);
                    o4.rOption = skillId;
                    tsm.putCharacterStatValue(IndieAsrR, o4);
                    // v271: elemental resistance is w (v265 used z for both)
                    Option o5 = new Option();
                    o5.nOption = si.getValue(SkillStat.w, slv) > 0 ? si.getValue(SkillStat.w, slv) : o4.nOption;
                    o5.rOption = skillId;
                    tsm.putCharacterStatValue(IndieTerR, o5);
                }
                break;
            case SPIRIT_OF_SNOW:
                Summon summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case BOLT_BARRAGE:
                Position chrPos = inPacket.decodePosition();
                boolean isLeft = inPacket.decodeByte() != 0;
                Position[] positions = new Position[inPacket.decodeInt()]; // amount of Positions
                for (int i = 0; i < positions.length; i++) {
                    positions[i] = inPacket.decodePositionInt();
                }
                Position bossPos = null;

                if (SkillChangeConstants.BOLT_BARRAGE_STICK_TO_BOSS) { // [Ice Lightning] Custom Skill Change
                    Position lastPos = positions[positions.length - 1];
                    Rect rect;
                    if (isLeft) {
                        Position lt = new Position(lastPos.getX(), lastPos.getY() - 500);
                        Position rb = chrPos;
                        rect = new Rect(lt, rb);
                    } else {
                        Position lt = new Position(chrPos.getX(), chrPos.getY() - 500);
                        Position rb = lastPos;
                        rect = new Rect(lt, rb);
                    }


                    Mob boss = field.getMobs().stream().filter(m -> m.isBoss() && rect.hasPositionInside(m.getPosition())).max(Comparator.comparingLong(Mob::getHp)).orElse(null);
                    if (boss != null) {
                        bossPos = boss.getPosition();
                    }
                }

                int duration = 3000; // ms
                int delay = 2;
                int distancePerBolt = 200;
                int i = 0;
                for (Position position : positions) {
                    AffectedArea aa = AffectedArea.getPassiveAA(chr, BOLT_BARRAGE_TILE, slv);
                    if ((bossPos != null) && ((!isLeft && position.getX() > bossPos.getX()) || (isLeft && bossPos.getX() > position.getX()))) {
                        position = new Position(bossPos.getX(), position.getY());
                    }
                    aa.setPosition(position);
                    aa.setRect(aa.getRectAround(SkillData.getSkillInfoById(BOLT_BARRAGE_TILE).getFirstRect()));
                    aa.setDelay((short) (delay * i));
                    aa.setDuration(duration + (i * distancePerBolt));
                    field.spawnAffectedArea(aa);
                    i++;
                }
                break;
        }
        tsm.sendSetStatPacket();
    }

    public void handleRemoveJupiterThunder(int shocksRemaining) {
        var finalCdR = shocksRemaining * (chr.getSkillStatValueD(t, JUPITER_THUNDER) * 1000);
        chr.reduceSkillCoolTime(JUPITER_THUNDER, (int) finalCdR);
    }

    public void handleSkillRemove(Char chr, int skillID, Map<CharacterTemporaryStat, Option> removedOptions) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (skillID == CHILLING_STEP) {
            if (SkillChangeConstants.CHILLING_STEP_BOSSING_MODE) {
                var nOpt = tsm.hasStat(ChillingStep)
                        ? tsm.getOption(ChillingStep).nOption
                        : removedOptions.containsKey(ChillingStep)
                            ? removedOptions.get(ChillingStep).nOption
                            : 0;
                if (nOpt == 1) {
                    Option o1 = new Option(CHILLING_STEP_BOSSING, skillID, 0);
                    tsm.putCharacterStatValue(ChillingStep, o1);
                    tsm.sendSetStatPacket();
                } else if (nOpt == 2) {
                    tsm.removeStatsBySkill(skillID);
                }
            } else {
                tsm.removeStatsBySkill(skillID);
            }
        }
    }

    public void doIceAura() {
        if (chr.hasSkillOnCooldown(-ABSOLUTE_ZERO_AURA) || !chr.getTemporaryStatManager().hasStat(IceAura)) {
            return;
        }

        var si = SkillData.getSkillInfoById(ABSOLUTE_ZERO_AURA);
        var slv = chr.getSkillLevel(ABSOLUTE_ZERO_AURA);
        Rect rect = chr.getRectAround(si.getFirstRect());
        for (Mob mob : chr.getField().getMobsInRect(rect).stream().limit(15).collect(Collectors.toList())) {
            applyIceAuraOnMob(mob, si, slv);
        }
        chr.addSkillCoolTime(-ABSOLUTE_ZERO_AURA, 3000);
    }

    public void applyIceAuraOnMob(Mob mob, SkillInfo si, int slv) {
        Option o = new Option();
        var mts = mob.getTemporaryStat();

        if (!mob.isBoss()) {
            o.nOption = 1;
            o.rOption = IceLightning.ICE_STRIKE; // for Blue effect
            o.tOption = si.getValue(time, slv);
            mts.addStatOptionsAndBroadcast(chr, MobStat.Freeze, o);
        }
    }

    private void createChillStepAA() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        SkillInfo chillingStepInfo = SkillData.getSkillInfoById(IceLightning.CHILLING_STEP);
        int slv = chr.getSkill(IceLightning.CHILLING_STEP).getCurrentLevel();
        int range = 168 + (tsm.hasStatBySkillId(TELEPORT_MASTERY_RANGE_IL) ? 100 : 0);
        int count = range / 3;
        if (Util.succeedProp(chillingStepInfo.getValue(prop, slv))) {
            for (int i = 0; i < range; i += count) {
                AffectedArea aa = AffectedArea.getPassiveAA(chr, IceLightning.CHILLING_STEP, (byte) slv);
                int x = chr.isLeft() ? chr.getPosition().getX() - i : chr.getPosition().getX() + i;
                int y = chr.getPosition().getY();
                aa.setPosition(new Position(x, y));
                aa.setRect(aa.getPosition().getRectAround(chillingStepInfo.getRects().get(0)));
                aa.setCurFoothold();
                aa.setDelay((short) 4);
                aa.setSkillID(IceLightning.CHILLING_STEP);
                chr.getField().spawnAffectedArea(aa);
            }
        }
    }

    // [Ice Lightning] Custom Skill Change
    private void createCustomChillStepAA() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int range = 168 + (tsm.hasStatBySkillId(TELEPORT_MASTERY_RANGE_IL) ? 100 : 0);
        int count = range / SkillChangeConstants.CHILLING_STEP_ICE_AGE_TILE_COUNT;
        if (chr.hasSkill(ICE_AGE) && Util.succeedProp(SkillChangeConstants.CHILLING_STEP_BOSS_MODE_PROC_CHANCE)) {
            for (int i = 0; i < range; i += count) {
                AffectedArea aa = AffectedArea.getPassiveAA(chr, ICE_AGE_TILE, (byte) chr.getSkillLevel(ICE_AGE));
                int x = chr.isLeft() ? chr.getPosition().getX() - i : chr.getPosition().getX() + i;
                int y = chr.getPosition().getY();
                aa.setPosition(new Position(x, y));
                aa.setRect(aa.getPosition().getRectAround(new Rect(-30, -40, 50, 20)));
                aa.setCurFoothold();
                aa.setDuration(SkillChangeConstants.CHILLING_STEP_ICE_AGE_TILE_DURATION);
                aa.setDelay((short) 4);
                chr.getField().spawnAffectedArea(aa);
            }
        }
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        if (skillId == CHILLING_STEP) {
            chr.getJobHandler().handleSkillRemove(chr, skillId, new HashMap<>());
            return;
        }
        super.handleSkillPrepareStop(chr, skillId, sui);
    }


    // Hit related methods ---------------------------------------------------------------------------------------------
}

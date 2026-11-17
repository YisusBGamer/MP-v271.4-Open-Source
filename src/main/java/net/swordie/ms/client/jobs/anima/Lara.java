package net.swordie.ms.client.jobs.anima;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.skills.*;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.info.AttackInfo;
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
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 26/11/2021.
 *
 * @author Asura
 */
public class Lara extends Job {
    public static final int WIND_SWING_FLY_SKILLID = 80003059;

    // Link Skill
    public static final int NATURE_FRIEND_ORIGIN = 160010001; // Link Skill
    public static final int NATURE_FRIEND_LINKED = 80003058; // Link Skill
    public static final int NATURE_FRIEND_LINKED_BUFF = 80003070; // Link Skill Buff

    // 0th Job
    public static final int SPIRIT_AFFINITY = 160010000;

    // 1st Job
        // Passive
    public static final int MOUNTAIN_KID_PASSIVE = 162000003;
    public static final int RESPONSE = 162000006;
        // Active
    public static final int ESSENCE_SPRINKLE = 162001000;
    public static final int JUMP_FOR_JOY = 162001001; // Flash Jump
    public static final int LEAP_UP = 162001002; // Up Jump
    public static final int MOUNTAIN_KID_ACTIVE = 162001004;
    public static final int PEERLESS_MOUNTAIN = 162000005; // v271: always on (v265 162001005 was a toggle)


    // 2nd Job
        // Passive
    public static final int ERUPTION_HEAVING_RIVER_PASSIVE = 162100002;
    public static final int ERUPTION_WHIRLWIND_PASSIVE = 162100005;
    public static final int ERUPTION_SUNRISE_WELL_PASSIVE = 162100008;
    public static final int WAND_MASTERY = 162100014;
    public static final int FORTUNE_FITNESS = 162100015;
        // Active
    public static final int DRAGON_VEIN_READING = 162101000; // (CTS Val: 684)
    public static final int DRAGON_VEIN_ERUPTION = 162101001;
    public static final int ERUPTION_HEAVING_RIVER_ACTIVE_1 = 162101003; // Summon
    public static final int ERUPTION_HEAVING_RIVER_ACTIVE_2 = 162101004;
    public static final int ERUPTION_WHIRLWIND_ACTIVE_1 = 162101006; // Summon
    public static final int ERUPTION_WHIRLWIND_ACTIVE_2 = 162101007;
    public static final int ERUPTION_SUNRISE_WELL_ACTIVE_1 = 162101009; // Normal Attack
    public static final int ERUPTION_SUNRISE_WELL_ACTIVE_2 = 162101010; // Affected Area (creates second Atoms)
    public static final int ERUPTION_SUNRISE_WELL_ACTIVE_3 = 162101011; // SecondAtom
    public static final int MOUNTAIN_SEEDS = 162101012; // Summon
    public static final int WAND_BOOSTER = 162101013;


    // 3rd Job
        // Passive
    public static final int DRAGON_VEIN_ECHO = 162110007;
    public static final int IMMACULATE_THOUGHT = 162110008;
    public static final int GEOMANCY = 162110009;
        // Active
    public static final int MANIFESTATION_WIND_SWING_ACTIVE_1 = 162111000; // Affected Area
    public static final int MANIFESTATION_WIND_SWING_ACTIVE_2 = 162111001; // Buff Info
    public static final int MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1 = 162111002; // SecondAtom
    public static final int MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_2 = 162111010; // Contains Counter Number
    public static final int MANIFESTATION_SUNLIGHT_FILLED_GROUND_ACTIVE_1 = 162111003; // Affected Area
    public static final int MANIFESTATION_SUNLIGHT_FILLED_GROUND_ACTIVE_2 = 162111004; // Buff Info
    public static final int WAKEUP_CALL = 162111005;
    public static final int DRAGON_VEIN_TRACES = 162111006;


    // 4th Job
        // Passive
    public static final int ABSORPTION_RIVER_PUDDLE_DOUSE_PASSIVE = 162120002;
    public static final int ABSORPTION_FIERCE_WIND_PASSIVE = 162120005;
    public static final int ABSORPTION_SUNLIT_GRAIN_PASSIVE = 162120008;
    public static final int NATURE_MASTER = 162120011;
    public static final int BEST_FRIEND = 162120020;
    public static final int ADVANCED_WAND_MASTERY = 162120025;
    public static final int IN_DEPTH_GEOMANCY = 162120026;
    public static final int INSIGHT = 162120027;
    public static final int SOB = 162120028;
        // Active
    public static final int DRAGON_VEIN_ABSORPTION = 162121000;
    public static final int DRAGON_VEIN_CONVERSION = 162121001;
    public static final int ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_1 = 162121003;
    public static final int ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_2 = 162121004;
    public static final int ABSORPTION_FIERCE_WIND_ACTIVE_1 = 162121006;
    public static final int ABSORPTION_FIERCE_WIND_ACTIVE_2 = 162121007;
    public static final int ABSORPTION_SUNLIT_GRAIN_ACTIVE_1 = 162121009;
    public static final int ABSORPTION_SUNLIT_GRAIN_ACTIVE_2 = 162121010; // Second Atom & Called through extraSkillinfo
    public static final int ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_1 = 162121012;
    public static final int ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_2 = 162121013;
    public static final int ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_3 = 162121014;
    public static final int ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_1 = 162121015;
    public static final int ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_2 = 162121016;
    public static final int ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_1 = 162121017;
    public static final int ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_2 = 162121018;
    public static final int ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_3 = 162121019; // SecondAtom
    public static final int ESSENCE_SPRINKLE_UPGRADE_4 = 162121021;
    public static final int MOUNTAIN_EMBRACE = 162121022;
    public static final int ANIMA_WARRIOR = 162121023;
    public static final int ANIMA_HERO_WILL = 162121024;


    // Hypers
        // Passive
    public static final int ERUPTION_ABSORPTION_GUARD_BREAK = 162120031;
    public static final int ERUPTION_ABSORPTION_GUARD_REINFORCE = 162120032;
    public static final int ERUPTION_ABSORPTION_GUARD_BOSS_RUSH = 162120033;
    public static final int ESSENCE_SPRINKLE_BOSS_RUSH = 162120034;
    public static final int WAKEUP_CALL_COOLDOWN_CUTTER = 162120035; // Should automatically be handled
    public static final int MOUNTAIN_SEEDS_REINFORCE = 162120036;
    public static final int DRAGON_VEIN_ECHO_ENHANCE = 162120037;
    public static final int MOUNTAIN_EMBRACE_EXTRA_SHIELD = 162120038;
    public static final int DRAGON_VEIN_ENHANCE = 162120039;
        // Active
    public static final int VINE_COIL = 162121041; // 140
    public static final int BRIGHT_HEART = 162121042; // 160
    public static final int UNCONSTRAINED_DRAGON_VEIN = -162121042; // v265 only; v271 reuses its id for Bright Heart
    public static final int ARBOR_AWAY_1 = 162121043; // 190
    public static final int ARBOR_AWAY_2 = 162121044; // 190

    // V skills
    public static final int BIG_STRETCH = 400021122;
    public static final int LANDS_CONNECTION = 400021123;
    public static final int LANDS_CONNECTION_PASSIVE_1 = 400021124;
    public static final int LANDS_CONNECTION_PASSIVE_2 = 400021125;
    public static final int LANDS_CONNECTION_PASSIVE_3 = 400021126;
    public static final int LANDS_CONNECTION_PASSIVE_4 = 400021127;
    public static final int LANDS_CONNECTION_PASSIVE_5 = 400021128;
    public static final int SURGING_ESSENCE = 400021129;
    public static final int WINDING_MOUNTAIN_RIDGE_1 = 400021130;
    public static final int WINDING_MOUNTAIN_RIDGE_2 = 400021131;
    public static final int HEXA_WAKEUP_CALL = 162141020; // HEXA Wakeup Call
    public static final int HEXA_ERUPTION_HEAVING_RIVER = 162141002;
    public static final int HEXA_ERUPTION_WHIRLWIND = 162141005;
    public static final int HEXA_ERUPTION_SUNRISE_WELL = 162141007;
    public static final int HEXA_ERUPTION_SUNRISE_WELL_LAVA = 162141008; // Affected Area (creates second atoms)
    public static final int HEXA_ERUPTION_SUNRISE_WELL_ATOM = 162141009;
    public static final int HEXA_ABSORPTION_RIVER_PUDDLE_DOUSE = 162141012;
    public static final int HEXA_ABSORPTION_FIERCE_WIND = 162141015;
    public static final int HEXA_ABSORPTION_SUNLIT_GRAIN = 162141018;
    public static final int INFILTRATE = 160011001;
    public static final int HEXA_DRAGON_VEIN_ERUPTION = 162141001;
    public static final int SIMPLE_FOUR_JADE_ORNAMENT = 1354020;


    private static final int[] addedSkills = new int[]{
            SPIRIT_AFFINITY
    };

    public Lara(Char chr) {
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

    private int lastVeinInteractionSkill = -1;

    private ScheduledFuture mountainSeedTimer;
    private ScheduledFuture dragonVeinTraceTimer;
    private ScheduledFuture unconstrainedDragonVeinTimer;

    private ScheduledFuture mountainEmbraceTimer;
    private ScheduledFuture dragonVeinEchoTimer;

    private static final int[] skillUseReqBeforeCommand = new int[]{
            DRAGON_VEIN_ERUPTION,

            ERUPTION_WHIRLWIND_ACTIVE_1,
            ERUPTION_SUNRISE_WELL_ACTIVE_1,
            ERUPTION_HEAVING_RIVER_ACTIVE_1,

            ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_1,
            ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_1,
            ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_1,

            HEXA_ERUPTION_HEAVING_RIVER,
            HEXA_ERUPTION_WHIRLWIND,
            HEXA_ERUPTION_SUNRISE_WELL,
    };

    private boolean isSkillUseReqBeforeCommand(int skillId) {
        return Arrays.stream(skillUseReqBeforeCommand).anyMatch(s -> s == skillId);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isLara(id);
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
        // v271 Dragon Vein Echo: a direct attack that hits heals u% and gives the Final Damage, at most every cooltime
        // seconds (there are no vein objects to absorb any more)
        if (hasHitMobs && attackInfo.summon == null && chr.hasSkill(DRAGON_VEIN_ECHO)
                && !chr.hasSkillOnCooldown(-DRAGON_VEIN_ECHO)) {
            var echo = SkillData.getSkillInfoById(DRAGON_VEIN_ECHO);
            int cd = echo == null ? 5 : Math.max(1, echo.getValue(cooltime, chr.getSkillLevel(DRAGON_VEIN_ECHO)));
            chr.addSkillCoolTime(-DRAGON_VEIN_ECHO, cd * 1000);
            dragonVeinEcho();
        }

        //
        // Mountain Kid
        // Passive Register Extra Skill Proc
        //
        mountainKidPassive(attackInfo);

        //
        // Absorption Skill Procs
        //
        manageAbsorptionProc(attackInfo);

        switch (attackInfo.skillId) {

            case VINE_COIL:
                for (var mai : attackInfo.mobAttackInfo) {
                    if (mai.mob == null) {
                        return;
                    }

                    var stunDuration = SkillConstants.getBindDuration(mai.mob.getMaxHp(), mai.totalDamage, si.getValue(time, slv));

                    if (mai.mob != null) {
                        mai.mob.getTemporaryStat()
                                .addStatOptionsAndBroadcast(chr, MobStat.Stun, new Option(1, skillID, stunDuration));
                    }
                }
                break;
        }
        super.handleAttack(c, attackInfo);
    }

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

        if (isSkillUseReqBeforeCommand(skillId)) {
            lastVeinInteractionSkill = skillId;
        }

        SecondAtom vein = null;
        if (skillUseInfo.targetPosition != null) {
            vein = chr.getSecondAtoms().values().stream().filter(sa -> sa.getDataIndex() == 20 && skillUseInfo.targetPosition.getRectAround(new Rect(-20, -20, 20, 20)).hasPositionInside(sa.getPosition())).findFirst().orElse(null);
        }

        var field = chr.getField();
        switch (skillId) {

            // region 1st Job Skills Handling
            case PEERLESS_MOUNTAIN:
                if (tsm.hasStat(PeerlessMountain)) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    o1.nOption = si.getValue(x, slv);
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(PeerlessMountain, o1);
                }
                break;
            // endregion

            // region 2nd Job Skills Handling
            case DRAGON_VEIN_READING:
                if (tsm.hasStat(DragonVeinReading)) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(DragonVeinReading, o1);
                }
                break;

            case WAND_BOOSTER:
                tsm.putCharacterStatValue(IndieBooster, new Option(si.getValue(x, slv), skillId, si.getValue(time, slv)));
                break;

            case MOUNTAIN_SEEDS: // v271: the seed spot comes in the skill-use info (the old raw tail is gone)
                var position = eruptionPosition(skillUseInfo);
                spawnMountainSeed(position);
                break;

            // v271 has no vein objects: the client sends the Eruption skill itself, with where it erupts
            case ERUPTION_HEAVING_RIVER_ACTIVE_1:
            case ERUPTION_WHIRLWIND_ACTIVE_1:
            case ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_1:
            case ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_1:
            case HEXA_ERUPTION_HEAVING_RIVER:
            case HEXA_ERUPTION_WHIRLWIND:
                eruptSummon(skillId, eruptionPosition(skillUseInfo));
                lastVeinInteractionSkill = 0;
                consumeDragonVein();
                break;

            case ERUPTION_SUNRISE_WELL_ACTIVE_3: // volcanic bombs the lava pool asks for
            case ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_3:
            case HEXA_ERUPTION_SUNRISE_WELL_ATOM:
                spawnVolcanicBombs(skillId, eruptionPosition(skillUseInfo));
                break;

            case ERUPTION_SUNRISE_WELL_ACTIVE_1:
                tsm.removeStat(AbsorptionSun);

                var tileSI = SkillData.getSkillInfoById(ERUPTION_SUNRISE_WELL_ACTIVE_2);
                var aa = AffectedArea.getPassiveAA(chr, ERUPTION_SUNRISE_WELL_ACTIVE_2, chr.getSkillLevel(ERUPTION_SUNRISE_WELL_PASSIVE));
                aa.setPosition(eruptionPosition(skillUseInfo));
                aa.setRect(aa.getPosition().getRectAround(tileSI.getFirstRect()));
                aa.activateTimer(1000, 3000);
                field.spawnAffectedAreaAndRemoveOld(aa);
                lastVeinInteractionSkill = 0;
                consumeDragonVein();
                break;
            // endregion

            // region 3rd Job Skills Handling
            case MANIFESTATION_SUNLIGHT_FILLED_GROUND_ACTIVE_1:
                if (vein != null) {
                    manifestSunVein(vein);
                }
                break;

            case MANIFESTATION_WIND_SWING_ACTIVE_1:
                if (vein != null) {
                    manifestWindVein(vein);
                }
                break;

            case MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1:
                var opt = tsm.getOptByCTSAndSkill(IndieSummon, MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1);
                if (opt == null || opt.nOption == 2) {
                    if (vein != null) {
                        // remove previous courses
                        chr.removeSecondAtom(chr.getSecondAtoms().values().stream().filter(sa -> sa.getDataIndex() == 31 || sa.getDataIndex() == 32).map(sa -> sa.getObjectId()).collect(Collectors.toList()));
                        manifestWhereTheRiverCoursesFirst(vein);
                    }
                } else if (opt.nOption == 1) {
                    var firstVein = chr.getSecondAtoms().values().stream().filter(sa -> sa.getDataIndex() == 31).findFirst().orElse(null);
                    if (firstVein != null) {
                        position = skillUseInfo.targetPosition;
                        manifestWhereTheRiverCoursesSecond(firstVein.getObjectId(), position);
                    }
                }
                break;

            case WAKEUP_CALL:
            case HEXA_WAKEUP_CALL:
                if (vein != null) {
                    wakeupCall(si, slv, vein, false);
                }
                break;

            case DRAGON_VEIN_TRACES:
                var currentStack = tsm.getOption(DragonVeinTrace).nOption;
                tsm.putCharacterStatValue(DragonVeinTrace, new Option(currentStack - 1, skillId, 0), true);
                break;
            // endregion

            // region 4th Job Skill Handling
            case DRAGON_VEIN_ABSORPTION:
                break;

            // HEXA Absorption: same buffs at the HEXA skill's own level
            case HEXA_ABSORPTION_RIVER_PUDDLE_DOUSE:
            case HEXA_ABSORPTION_FIERCE_WIND:
            case HEXA_ABSORPTION_SUNLIT_GRAIN: {
                SkillInfo hsi = SkillData.getSkillInfoById(skillId);
                int hlv = Math.max(1, chr.getSkillLevel(skillId) > 0 ? chr.getSkillLevel(skillId) : slv);
                var hcts = skillId == HEXA_ABSORPTION_RIVER_PUDDLE_DOUSE ? AbsorptionWater
                        : skillId == HEXA_ABSORPTION_FIERCE_WIND ? AbsorptionWind : AbsorptionSun;
                tsm.putCharacterStatValue(hcts, new Option(1, skillId, hsi.getValue(time, hlv)), true);
                spawnAbsorptionSpirit(skillId - 1, hlv);
                if (hcts == AbsorptionWater) {
                    field.removeSummon(ERUPTION_HEAVING_RIVER_ACTIVE_1, chr.getId());
                    field.removeSummon(ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_1, chr.getId());
                } else if (hcts == AbsorptionWind) {
                    field.removeSummon(ERUPTION_WHIRLWIND_ACTIVE_1, chr.getId());
                    field.removeSummon(ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_1, chr.getId());
                } else {
                    field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_ACTIVE_1, chr.getId());
                    field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_ACTIVE_2, chr.getId());
                    field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_1, chr.getId());
                    field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_2, chr.getId());
                }
                break;
            }
            case ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_1:
                si = SkillData.getSkillInfoById(skillId);
                slv = chr.getSkillLevel(ABSORPTION_RIVER_PUDDLE_DOUSE_PASSIVE);

                tsm.putCharacterStatValue(AbsorptionWater, new Option(1, skillId, si.getValue(time, slv)), true);
                spawnAbsorptionSpirit(skillId, slv);

                // Cancel eruption
                field.removeSummon(ERUPTION_HEAVING_RIVER_ACTIVE_1, chr.getId());
                field.removeSummon(ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_1, chr.getId());
                break;
            case ABSORPTION_FIERCE_WIND_ACTIVE_1:
                si = SkillData.getSkillInfoById(skillId);
                slv = chr.getSkillLevel(ABSORPTION_FIERCE_WIND_PASSIVE);

                tsm.putCharacterStatValue(AbsorptionWind, new Option(1, skillId, si.getValue(time, slv)), true);
                spawnAbsorptionSpirit(skillId, slv);

                // Cancel eruption
                field.removeSummon(ERUPTION_WHIRLWIND_ACTIVE_1, chr.getId());
                field.removeSummon(ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_1, chr.getId());
                break;
            case ABSORPTION_SUNLIT_GRAIN_ACTIVE_1:
                si = SkillData.getSkillInfoById(skillId);
                slv = chr.getSkillLevel(ABSORPTION_SUNLIT_GRAIN_PASSIVE);

                tsm.putCharacterStatValue(AbsorptionSun, new Option(1, skillId, si.getValue(time, slv)), true);
                spawnAbsorptionSpirit(skillId, slv);

                // Cancel eruption
                field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_ACTIVE_1, chr.getId());
                field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_ACTIVE_2, chr.getId());
                field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_1, chr.getId());
                field.removeAffectedAreas(ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_2, chr.getId());
                break;
            case ABSORPTION_SUNLIT_GRAIN_ACTIVE_2:
                var targetList = new ArrayList<Integer>();
                var size = inPacket.decodeByte();
                for (int i = 0; i < size; i++) {
                    targetList.add(inPacket.decodeInt());
                }
                si = SkillData.getSkillInfoById(skillId);
                slv = chr.getSkillLevel(ABSORPTION_SUNLIT_GRAIN_PASSIVE);
                var createDelay = si.getValue(w, slv); // create delay
                var createDelayIncrement = si.getValue(u, slv);

                var saList = new ArrayList<SecondAtom>();
                for (var sai : si.getSecondAtomInfos()) {
                    var sa = SecondAtom.createFromInfo(chr, skillId, sai);
                    var target = Util.getRandomFromCollection(targetList);
                    sa.setTargetId(targetList.size() <= 0 ? 0 : target);
                    position = sai.getPosition();
                    if (skillUseInfo.isLeft) {
                        position.setX(-position.getX());
                    }
                    sa.setPosition(skillUseInfo.targetPosition.add(position));
                    sa.setCreateDelay(createDelay);
                    sa.setEnableDelay(createDelay + sai.getEnableDelay());
                    sa.setCustomList(new ArrayList<>() {{ add(skillUseInfo.targetPosition.getX()); add(skillUseInfo.targetPosition.getY()); }});
                    saList.add(sa);

                    createDelay += createDelayIncrement;
                }
                if (saList.size() > 0) {
                    chr.spawnSecondAtoms(saList);
                }
                var eff = Effect.skillUse(skillId, chr.getLevel(), slv, 0);
                eff.setArg5(skillUseInfo.isLeft ? 1 : 0);
                eff.setArg6(skillUseInfo.targetPosition.getX());
                eff.setArg7(skillUseInfo.targetPosition.getY());
                chr.write(UserPacket.effect(eff));
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), eff), chr);
                chr.addSkillCoolTime(ABSORPTION_SUNLIT_GRAIN_ACTIVE_2, SkillData.getSkillInfoById(ABSORPTION_SUNLIT_GRAIN_ACTIVE_1).getValue(u, chr.getSkillLevel(ABSORPTION_SUNLIT_GRAIN_PASSIVE)));
                break;

            case HEXA_ERUPTION_SUNRISE_WELL: // HEXA lava pool at the vein, at the HEXA level
                if (skillUseInfo.targetPosition != null) {
                    tsm.removeStat(AbsorptionSun);
                    lastVeinInteractionSkill = 0;
                    consumeDragonVein();
                    tileSI = SkillData.getSkillInfoById(HEXA_ERUPTION_SUNRISE_WELL_LAVA);
                    aa = AffectedArea.getPassiveAA(chr, HEXA_ERUPTION_SUNRISE_WELL_LAVA,
                            Math.max(1, chr.getSkillLevel(HEXA_ERUPTION_SUNRISE_WELL)));
                    aa.setPosition(skillUseInfo.targetPosition);
                    aa.setRect(aa.getPosition().getRectAround(tileSI.getFirstRect()));
                    aa.activateTimer(1000, 2000);
                    field.spawnAffectedAreaAndRemoveOld(aa);
                }
                break;
            case ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_1:
                tsm.removeStat(AbsorptionSun);
                lastVeinInteractionSkill = 0;
                consumeDragonVein();
                tileSI = SkillData.getSkillInfoById(ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_2);
                aa = AffectedArea.getPassiveAA(chr, ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_2, chr.getSkillLevel(NATURE_MASTER));
                aa.setPosition(eruptionPosition(skillUseInfo));
                aa.setRect(aa.getPosition().getRectAround(tileSI.getFirstRect()));
                aa.activateTimer(1000, 2000);
                field.spawnAffectedAreaAndRemoveOld(aa);
                break;
            // endregion

            // region Hyper Skill Handling
            case UNCONSTRAINED_DRAGON_VEIN:
                createDragonVein(skillUseInfo.lifeObjectId, VeinType.DRAGON_VEIN.getVal(), skillUseInfo.targetPosition, skillUseInfo.unk);

                currentStack = tsm.getOption(UnconstrainedDragonVein).nOption;
                tsm.putCharacterStatValue(UnconstrainedDragonVein, new Option(currentStack - 1, skillId, 0), true);
                break;
            case BRIGHT_HEART:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o1);
                break;
            case MOUNTAIN_EMBRACE: // v271: a timed buff (v265 a key-down)
                applyMountainEmbrace(skillId, slv, si.getValue(time, slv), false);
                break;
            case ARBOR_AWAY_1:
                aa = AffectedArea.getPassiveAA(chr, skillId, slv);
                aa.activateTimer(2000, 2000);
                field.spawnAffectedAreaAndRemoveOld(aa);
                break;
            // endregion

            // region V Skill Handling
            case BIG_STRETCH:
                if (vein != null) {
                    wakeupCall(si, slv, vein, true);
                }
                break;

            case LANDS_CONNECTION:
                var pos = skillUseInfo.targetPosition;
                var left = skillUseInfo.isLeft;

                var esiList = new ArrayList<ExtraSkillInfo>();
                for (var esiMap : si.getExtraSkillInfo()) {
                    for (var entry : esiMap.entrySet()) {
                        var extraSkillId = entry.getKey();
                        var delay = entry.getValue();
                        esiList.add(new ExtraSkillInfo(extraSkillId, pos, left, delay, 0));
                    }
                }

                chr.write(FieldPacket.registerExtraSkill(skillId, esiList));
                break;
            // endregion
        }

        tsm.sendSetStatPacket();
    }

    private void spawnMountainSeed(Position position) {
        var tsm = chr.getTemporaryStatManager();
        var field = chr.getField();

        var skillId = MOUNTAIN_SEEDS;
        var slv = chr.getSkillLevel(MOUNTAIN_SEEDS);

        var currentStack = tsm.getOption(MountainSeeds).nOption;

        if (currentStack <= 0) {
            return;
        }

        var seedList = field.getSummons().stream().filter(s -> s.getChr() == chr && s.getSkillID() == skillId).sorted(Comparator.comparingInt(Life::getObjectId)).collect(Collectors.toList());
        if (seedList.size() == 4) {
            var removedSeed = seedList.get(0);
            field.removeSummon(removedSeed);
        }

        var summon = Summon.getSummonByNoCTS(chr, skillId, slv);
        summon.setMoveAbility(MoveAbility.WalkRandom);
        summon.setPosition(position);
        field.spawnSummon(summon);

        tsm.putCharacterStatValue(MountainSeeds, new Option(currentStack - 1, skillId, 0), true);
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleKeyDownSkill(Char chr, int skillID, InPacket inPacket) {
        switch (skillID) {
            case MOUNTAIN_EMBRACE:
                applyMountainEmbrace(skillID, chr.getSkillLevel(skillID), 8, true);
                break;
        }
        super.handleKeyDownSkill(chr, skillID, inPacket);
    }

    private void applyMountainEmbrace(int skillID, int slv, int dur, boolean keyDown) {
        var si = SkillData.getSkillInfoById(skillID);
        var tsm = chr.getTemporaryStatManager();
        var o1 = new Option();
        var o2 = new Option();
        var o3 = new Option();
        var o4 = new Option();
        var o5 = new Option();
        var o6 = new Option();

        o1.nOption = 1;
        o1.rOption = skillID;
        o1.tOption = dur;
        o1.xOption = dur * 1000;
        tsm.putCharacterStatValue(AntiMagicShell, o1, true);

        if (keyDown) {
            o2.nOption = 1;
            o2.rOption = skillID;
            o2.tOption = dur;
            tsm.putCharacterStatValue(KeyDownEnable, o2, true); // the client checks KeyDownEnable with r Mountain Embrace
        }

        o3.nOption = -si.getValue(x, slv);
        o3.rOption = skillID;
        o3.tOption = dur;
        tsm.putCharacterStatValue(IndieHitDamageInclHPR, o3, true);

        if (keyDown) {
            o4.nOption = 1;
            o4.rOption = skillID;
            o4.tOption = dur;
            tsm.putCharacterStatValue(IndieFloating, o4, true);
        }

        if (chr.hasSkill(MOUNTAIN_EMBRACE_EXTRA_SHIELD)) {
            var shieldSI = SkillData.getSkillInfoById(MOUNTAIN_EMBRACE_EXTRA_SHIELD);
            var shieldHPR = shieldSI.getValue(x, 1);
            o5.nOption = 1;
            o5.rOption = MOUNTAIN_EMBRACE_EXTRA_SHIELD;
            o5.tOption = dur;
            tsm.putCharacterStatValue(IndieFloating, o5, true);

            var val = chr.getHPPerc(shieldHPR);
            o6.nOption = val;
            o6.rOption = MOUNTAIN_EMBRACE_EXTRA_SHIELD;
            o6.tOption = dur + 1;
            tsm.putCharacterStatValue(IndieProtectiveShield, o6, true);
            EventManager.stopTimer(mountainEmbraceTimer);
            mountainEmbraceTimer = EventManager.addFixedRateEvent(() -> increaseMountainEmbraceShield(val), 1, 1, TimeUnit.SECONDS);
        }
        tsm.sendSetStatPacket();
    }

    private void spawnAbsorptionSpirit(int summonId, int slv) {
        // v271: the Absorption spirits are summons (v265 only had the buff)
        SkillInfo ssi = SkillData.getSkillInfoById(summonId);
        if (ssi == null || !ssi.isSummon() || chr.getField() == null) {
            return;
        }
        Summon spirit = Summon.getSummonBy(chr, summonId, Math.max(1, slv));
        chr.getField().spawnSummonAndRemoveOld(spirit);
    }

    private void increaseMountainEmbraceShield(int shieldInc) {
        var tsm = chr.getTemporaryStatManager();
        var opt = tsm.getOptByCTSAndSkill(IndieProtectiveShield, MOUNTAIN_EMBRACE_EXTRA_SHIELD);

        if (opt == null) {
            EventManager.stopTimer(mountainEmbraceTimer);
            return;
        }

        opt.nOption += shieldInc;
        tsm.updateBuff(IndieProtectiveShield, opt);
    }

    @Override
    public void handleCancelKeyDownSkill(Char chr, int skillID) {
        var tsm = chr.getTemporaryStatManager();
        switch (skillID) {
            case MOUNTAIN_EMBRACE:
                EventManager.stopTimer(mountainEmbraceTimer);

                int remainingDuration = (int) (tsm.getRemainingTime(IndieHitDamageInclHPR, MOUNTAIN_EMBRACE) / 1000);
                if (remainingDuration > 0) {
                    chr.reduceSkillCoolTime(skillID, remainingDuration * 3_500); // 3.5 seconds reduction to CD per second remaining
                }
                tsm.removeStatsBySkill(MOUNTAIN_EMBRACE);

                var opt = tsm.getOptByCTSAndSkill(IndieProtectiveShield, MOUNTAIN_EMBRACE_EXTRA_SHIELD);
                if (opt != null) {
                    opt.tOption = 1000 * chr.getSkillStatValue(u, MOUNTAIN_EMBRACE_EXTRA_SHIELD);
                    opt.setInMillis(true);
                    tsm.updateBuffExtendTime(IndieProtectiveShield, opt);
                }
                break;
        }

        super.handleCancelKeyDownSkill(chr, skillID);
    }

    @Override
    public void handleRemoveCTS(CharacterTemporaryStat cts, Option option) {

        if (cts == IndieSummon && option.rOption == MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1) {
            var manifestations = chr.getSecondAtoms().values().stream().filter(sa -> sa.getDataIndex() == 30 || sa.getDataIndex() == 31).map(sa -> sa.getObjectId()).collect(Collectors.toList());
            chr.removeSecondAtom(manifestations);
        }

        super.handleRemoveCTS(cts, option);
    }

    @Override
    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        switch (skillId) {
            case BIG_STRETCH:
                if (sui.unk > 0) {
                    var si = SkillData.getSkillInfoById(skillId);
                    var maxVeins = si.getValue(u, slv);
                    var cdReduction = si.getValue(w, slv) * 1000;
                    var diff = Math.max(0, Math.min(maxVeins - sui.unk, maxVeins));
                    var totalCdReduction = diff * cdReduction;

                    var skillCooltime = si.getValue(cooltime, slv) * 1000;
                    return skillCooltime - totalCdReduction;
                }
                break;
        }
        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }

    @Override
    public BypassCooldownCheckType canBypassCooldownCheck(int skillId, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        switch (skillId) {
            case MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1:
                var tsm = chr.getTemporaryStatManager();
                var opt = tsm.getOptByCTSAndSkill(IndieSummon, MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1);
                if (opt != null && opt.nOption == 1) {
                    return BypassCooldownCheckType.BypassCheckAndCooldown;
                }
                break;

            case BIG_STRETCH:
                if (sui.unk <= 0) {
                    return BypassCooldownCheckType.BypassCheckAndCooldown;
                }
                break;
        }

        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    @Override
    public boolean handleSecondAtomRemoveRequest(int objectId) {
        var waterManifestationVein = chr.getSecondAtomById(objectId);
        if (waterManifestationVein != null && waterManifestationVein.getDataIndex() == 31 && chr.getSecondAtoms().values().stream().anyMatch(sa -> sa.getDataIndex() == 32)) {
            return false;
        }
        if (waterManifestationVein != null && waterManifestationVein.getDataIndex() == 32) {
            var waterManifestationVeinFirst = chr.getSecondAtoms().values().stream().filter(sa -> sa.getDataIndex() == 31).findFirst().orElse(null);
            if (waterManifestationVeinFirst != null) {
                chr.removeSecondAtom(Collections.singletonList(waterManifestationVeinFirst.getObjectId()));
            }
            return true;
        }
        return super.handleSecondAtomRemoveRequest(objectId);
    }

    // region Mountain Kid
    public void mountainKidPassive(AttackInfo attackInfo) {
        if (attackInfo.skillId != ESSENCE_SPRINKLE_UPGRADE_4 && attackInfo.skillId != ESSENCE_SPRINKLE) {
            return;
        }
        var skill = chr.getSkill(MOUNTAIN_KID_PASSIVE);
        if (skill == null || attackInfo.mobCount <= 0) {
            return;
        }

        var si = SkillData.getSkillInfoById(MOUNTAIN_KID_PASSIVE);
        var slv = skill.getCurrentLevel();

        var prop = si.getValue(SkillStat.prop, slv);

        if (Util.succeedProp(prop)) {
            doMountainKidProc(attackInfo.skillId, attackInfo.mobAttackInfo.get(0).pos1);
        }
    }

    public void doMountainKidProc(int essenceSprinkle, Position position) {
        var esi = new ExtraSkillInfo(MOUNTAIN_KID_ACTIVE, position, Util.randBoolean(), 0, 1);
        chr.write(FieldPacket.registerExtraSkill(essenceSprinkle, esi));
    }
    // endregion

    // region Dragon Vein Reading
    public void createDragonVein(int veinIncrement, int veinType, Position position, int unk) {
        var sa = new SecondAtom();

        // SecondAtom Owner Info
        sa.setObjectId(chr.getNewSecondAtomKey());
        sa.setChrId(chr.getId());
        sa.setKey(1);

        // SecondAtom Info
        sa.setSource(DRAGON_VEIN_READING);
        sa.setDataIndex(20);
        sa.setPosition(position);
        sa.setUnk(unk);
        sa.setCustomList(new ArrayList<>() {{ add(veinType); add(veinIncrement); }});

        // Broadcast Info
        sa.setLocalOnly(true);

        chr.spawnSecondAtoms(Collections.singletonList(sa));
    }

    public VeinType getVeinTypeByVein(SecondAtom vein) {
        if (vein == null) {
            return null;
        }
        if (vein.getCustomList() == null || vein.getCustomList().size() <= 0) {
            return null;
        }
        return VeinType.getByVal(vein.getCustomList().get(0));
    }

    public void consumeDragonVein() {
        // Dragon Vein Echo
        dragonVeinEcho();
    }
    // endregion

    public void secondAtomCommandRequest(SecondAtom vein) {
        var tsm = chr.getTemporaryStatManager();
        if (lastVeinInteractionSkill == 0) {
            return;
        }

        if (vein.getCustomList() == null || vein.getCustomList().size() <= 0) {
            return;
        }

        switch (lastVeinInteractionSkill) {
            case ERUPTION_HEAVING_RIVER_ACTIVE_1:
            case ERUPTION_WHIRLWIND_ACTIVE_1:
            case ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_1:
            case ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_1:
            case HEXA_ERUPTION_HEAVING_RIVER:
            case HEXA_ERUPTION_WHIRLWIND:
                eruptSummon(lastVeinInteractionSkill, vein.getPosition());
                break;

            case ERUPTION_SUNRISE_WELL_ACTIVE_1:
            case ERUPTION_SUNRISE_WELL_UPGRADE_4_ACTIVE_1:
            case HEXA_ERUPTION_SUNRISE_WELL:
                tsm.removeStat(AbsorptionSun); // the lava pool comes with the skill use
                break;
        }

        consumeDragonVein();

        lastVeinInteractionSkill = 0;
    }

    // region Eruption methods
    /** Where an Eruption lands: the client's target, else its cast position, else Lara's position. */
    private Position eruptionPosition(SkillUseInfo sui) {
        if (sui != null && sui.targetPosition != null) {
            return sui.targetPosition;
        }
        if (sui != null && sui.castPosition != null) {
            return sui.castPosition;
        }
        return chr.getPosition();
    }

    /** Heaving River / Whirlwind spirits; v271 runs their surges from the skill data (no sequence packet). */
    private void eruptSummon(int summonId, Position position) {
        var field = chr.getField();
        var tsm = chr.getTemporaryStatManager();
        if (field == null || position == null) {
            return;
        }
        int slv;
        boolean river;
        switch (summonId) {
            case ERUPTION_HEAVING_RIVER_ACTIVE_1 -> { slv = chr.getSkillLevel(ERUPTION_HEAVING_RIVER_PASSIVE); river = true; }
            case ERUPTION_WHIRLWIND_ACTIVE_1 -> { slv = chr.getSkillLevel(ERUPTION_WHIRLWIND_PASSIVE); river = false; }
            case ERUPTION_HEAVING_RIVER_UPGRADE_4_ACTIVE_1 -> { slv = chr.getSkillLevel(NATURE_MASTER); river = true; }
            case ERUPTION_WHIRLWIND_UPGRADE_4_ACTIVE_1 -> { slv = chr.getSkillLevel(NATURE_MASTER); river = false; }
            case HEXA_ERUPTION_HEAVING_RIVER, HEXA_ERUPTION_WHIRLWIND -> {
                slv = chr.getSkillLevel(summonId) > 0 ? chr.getSkillLevel(summonId) : chr.getSkillLevel(HEXA_DRAGON_VEIN_ERUPTION);
                river = summonId == HEXA_ERUPTION_HEAVING_RIVER;
            }
            default -> {
                return;
            }
        }
        var summon = Summon.getSummonBy(chr, summonId, Math.max(1, slv));
        summon.setMoveAbility(MoveAbility.Stop);
        summon.setAssistType(AssistType.SequenceAttack);
        summon.setPosition(position);
        summon.setFlip(river);
        summon.setCurFoothold((short) 0);
        field.spawnSummonAndRemoveOld(summon);
        tsm.removeStat(river ? AbsorptionWater : AbsorptionWind);
    }

    /** Sunrise Well volcanic bombs: one second atom per atom node, spread over the lava pool. */
    private void spawnVolcanicBombs(int skillId, Position position) {
        var si = SkillData.getSkillInfoById(skillId);
        if (si == null || position == null || si.getSecondAtomInfos().isEmpty() || chr.getField() == null) {
            return;
        }
        var rect = position.getRectAround(new Rect(-150, -100, 150, 0));
        var saList = new ArrayList<SecondAtom>();
        for (var sai : si.getSecondAtomInfos()) {
            var sa = SecondAtom.createFromInfo(chr, skillId, sai);
            sa.setPosition(rect.getRandomPositionInside());
            saList.add(sa);
        }
        chr.spawnSecondAtoms(saList);
    }
    // endregion

    // region Manifestation methods
    private void manifestSunVein(SecondAtom vein) {
        var field = chr.getField();
        var si = SkillData.getSkillInfoById(MANIFESTATION_SUNLIGHT_FILLED_GROUND_ACTIVE_1);
        var slv = chr.getSkillLevel(MANIFESTATION_SUNLIGHT_FILLED_GROUND_ACTIVE_1);
        var aa = AffectedArea.getPassiveAA(chr, MANIFESTATION_SUNLIGHT_FILLED_GROUND_ACTIVE_1, slv);
        aa.setPosition(vein.getPosition());
        aa.setRect(aa.getPosition().getRectAround(si.getFirstRect()));

        // spawn aa
        field.spawnAffectedAreaAndRemoveOld(aa);

        // set Healing Timer
        aa.activateTimer(2000, 2000);

        consumeDragonVein();
    }

    private void manifestWindVein(SecondAtom vein) {
        var field = chr.getField();
        var si = SkillData.getSkillInfoById(MANIFESTATION_WIND_SWING_ACTIVE_1);
        var slv = chr.getSkillLevel(MANIFESTATION_WIND_SWING_ACTIVE_1);
        var aa = AffectedArea.getPassiveAA(chr, MANIFESTATION_WIND_SWING_ACTIVE_1, slv);
        aa.setPosition(vein.getPosition());
        aa.setRect(aa.getPosition().getRectAround(si.getFirstRect()));
        aa.setRect2(aa.getPosition().getRectAround(si.getRects().get(2))); // get third rect

        // spawn aa
        field.spawnAffectedAreaAndRemoveOld(aa); // TODO Flying gets insta-cancelled

        consumeDragonVein();
    }

    private void manifestWhereTheRiverCoursesFirst(SecondAtom vein) {
        var tsm = chr.getTemporaryStatManager();
        var si = SkillData.getSkillInfoById(MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1);
        int slv = chr.getSkillLevel(MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1);
        tsm.putCharacterStatValue(IndieSummon, new Option(1, MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1, si.getValue(s, slv)));

        var sai = si.getSecondAtomInfos().get(0);
        var sa = SecondAtom.createFromInfo(chr, MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1, sai);
        sa.setPosition(vein.getPosition());
        sa.setFirstAngleStart(20);
        sa.setExpire(0);
        sa.setAttackableCount(1);

        sa.setCustomList(new ArrayList<>() {{ add(0); }});
        chr.spawnSecondAtoms(Collections.singletonList(sa));

        consumeDragonVein();
    }

    private void manifestWhereTheRiverCoursesSecond(int otherVeinObjId, Position position) {
        var tsm = chr.getTemporaryStatManager();
        var si = SkillData.getSkillInfoById(MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1);
        int slv = chr.getSkillLevel(MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1);
        tsm.putCharacterStatValue(IndieSummon, new Option(2, MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1, si.getValue(s2, slv)));

        var sai = si.getSecondAtomInfos().get(1);
        var sa = SecondAtom.createFromInfo(chr, MANIFESTATION_WHERE_THE_RIVER_COURSES_ACTIVE_1, sai);
        sa.setPosition(position);
        sa.setFirstAngleStart(20);
        sa.setExpire(0);
        sa.setAttackableCount(1);
        sa.setCustomList(new ArrayList<>() {{ add(otherVeinObjId); }});
        chr.spawnSecondAtoms(Collections.singletonList(sa));
    }
    // endregion

    // region Absorption Methods
    private void manageAbsorptionProc(AttackInfo attackInfo) {
        if (attackInfo.skillId != ESSENCE_SPRINKLE_UPGRADE_4 && attackInfo.skillId != ESSENCE_SPRINKLE) {
            return;
        }

        var tsm = chr.getTemporaryStatManager();
        var activeAbsorptions = new ArrayList<Integer>();
        if (tsm.hasStat(AbsorptionWater)) {
            activeAbsorptions.add(ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_2);
        }
        if (tsm.hasStat(AbsorptionWind)) {
            activeAbsorptions.add(ABSORPTION_FIERCE_WIND_ACTIVE_2);
        }
        if (tsm.hasStat(AbsorptionSun)) {
            activeAbsorptions.add(ABSORPTION_SUNLIT_GRAIN_ACTIVE_2);
        }

        // Has no absorptions
        if (activeAbsorptions.size() <= 0) {
            return;
        }

        var activeAndAvailableAbsorptions = activeAbsorptions.stream().filter(s -> !chr.hasSkillOnCooldown(s)).collect(Collectors.toList());

        // Has no available absorptions
        if (activeAndAvailableAbsorptions.size() <= 0) {
            return;
        }

        var randomAbsorption = Util.getRandomFromCollection(activeAndAvailableAbsorptions);
        switch (randomAbsorption) {
            case ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_2:
                procRiverPuddleDouse(attackInfo);
                break;
            case ABSORPTION_FIERCE_WIND_ACTIVE_2:
                procFierceWind(attackInfo);
                break;
            case ABSORPTION_SUNLIT_GRAIN_ACTIVE_2:
                procSunlitGrain(attackInfo);
                break;
        }

        consumeDragonVein();
    }

    private void procSunlitGrain(AttackInfo attackInfo) {
        var esi = new ExtraSkillInfo(ABSORPTION_SUNLIT_GRAIN_ACTIVE_2, chr.getPosition(), attackInfo.left, 0, 5);
        chr.write(FieldPacket.registerExtraSkill(ABSORPTION_SUNLIT_GRAIN_ACTIVE_2, esi));
    }

    private void procRiverPuddleDouse(AttackInfo attackInfo) {
        var esi = new ExtraSkillInfo(ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_2, chr.getPosition(), attackInfo.left, 0, 1);
        chr.write(FieldPacket.registerExtraSkill(ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_2, esi));
        chr.addSkillCoolTime(ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_2, 2500);
    }

    private void procFierceWind(AttackInfo attackInfo) {
        var esi = new ExtraSkillInfo(ABSORPTION_FIERCE_WIND_ACTIVE_2, chr.getPosition(), attackInfo.left, 0, 7);
        chr.write(FieldPacket.registerExtraSkill(ABSORPTION_FIERCE_WIND_ACTIVE_2, esi));
        chr.addSkillCoolTime(ABSORPTION_FIERCE_WIND_ACTIVE_2, 2500);
    }
    // endregion

    // region wakeup call
    private void wakeupCall(SkillInfo si, int slv, SecondAtom vein, boolean byBigStretch) {
        var count = si.getValue(bulletCount, slv);
        var sai = si.getSecondAtomInfos().get(0);
        var veinType = getVeinTypeByVein(vein);
        if (veinType == null) {
            return;
        }
        var dataIndex = 0;
        switch (veinType) {
            case WATER_VEIN:
                dataIndex = byBigStretch ? 24 : 22; // 22
                break;
            case WIND_VEIN:
                dataIndex = byBigStretch ? 28 : 25; // 25
                break;
            case SUN_VEIN:
                dataIndex = byBigStretch ? 29 : 26; // 26
                break;
            case DRAGON_VEIN:
                dataIndex = byBigStretch ? 30 : 27; // 27
                break;
        }
        var saList = new ArrayList<SecondAtom>();
        var rect = vein.getPosition().getRectAround(new Rect(-200, -400, 200, 0));
        for (var i = 0; i < count; i++) {
            var sa = SecondAtom.createFromInfo(chr, byBigStretch ? BIG_STRETCH : WAKEUP_CALL, sai);
            sa.setDataIndex(dataIndex);
            sa.setPosition(rect.getRandomPositionInside());

            saList.add(sa);
        }

        chr.spawnSecondAtoms(saList);

        consumeDragonVein();
    }
    // endregion

    // region Dragon Vein Echo
    private void dragonVeinEcho() {
        var skill = chr.getSkill(DRAGON_VEIN_ECHO);

        if (skill == null) {
            return;
        }

        var si = SkillData.getSkillInfoById(skill.getSkillId());
        var slv = skill.getCurrentLevel();

        // Heal HP & MP
        var percentageHealed = si.getValue(u, slv);
        chr.heal(chr.getHPPerc(percentageHealed));
        chr.healMP(chr.getMPPerc(percentageHealed));

        var tOpt = si.getValue(time, slv);

        // FinalDmg Buff
        var tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(IndiePMdR, new Option(si.getValue(x, slv), skill.getSkillId(), tOpt), true);

        if (chr.hasSkill(DRAGON_VEIN_ECHO_ENHANCE)) {
            var siUpgrade = SkillData.getSkillInfoById(DRAGON_VEIN_ECHO_ENHANCE);
            var slvUpgrade = 1;
            tsm.putCharacterStatValue(IndieSpeed, new Option(siUpgrade.getValue(x, slvUpgrade), skill.getSkillId(), tOpt), true);
            tsm.putCharacterStatValue(IndieJump, new Option(siUpgrade.getValue(y, slvUpgrade), skill.getSkillId(), tOpt), true);

            EventManager.stopTimer(dragonVeinEchoTimer);
            var healedAmount = chr.getHPPerc(siUpgrade.getValue(w, slvUpgrade));
            var executes = tOpt / 5;
            dragonVeinEchoTimer = EventManager.addFixedRateEvent(() -> healByDragonVeinEchoEnhance(healedAmount), 5000, 5000, executes);
        }
    }

    private void healByDragonVeinEchoEnhance(int amount) {
        var tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStatBySkillId(DRAGON_VEIN_ECHO)) {
            EventManager.stopTimer(dragonVeinEchoTimer);
            return;
        }
        chr.heal(amount);
    }
    // endregion

    /** The seed and trace stacks fill over time from login (nothing started them, so Mountain Seeds never had a seed). */
    @Override
    public void onWarp(net.swordie.ms.world.field.Field oldField, net.swordie.ms.world.field.Field newField) {
        super.onWarp(oldField, newField);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : new int[]{MOUNTAIN_SEEDS, DRAGON_VEIN_TRACES}) {
                if (chr.hasSkill(id)) {
                    stackSkillManager(id);
                }
            }
        }
    }

    // region Stacking Skills
    public void stackSkillManager(int skillId) {
        if (chr.hasSkillOnCooldown(-skillId)) {
            //return;
        }

        var si = SkillData.getSkillInfoById(skillId);
        var slv = chr.getSkillLevel(skillId);

        switch (skillId) {
            case MOUNTAIN_SEEDS:
                // Timer is already started
                if (mountainSeedTimer != null) {
                    return;
                }
                var maxStack = si.getValue(w2, slv);
                var delay = si.getValue(w, slv);
                EventManager.stopTimer(mountainSeedTimer);
                mountainSeedTimer = EventManager.addFixedRateEvent(() -> stackMountainSeeds(maxStack), 0, delay);
                break;

            case DRAGON_VEIN_TRACES:
                // Timer is already started
                if (dragonVeinTraceTimer != null) {
                    return;
                }
                delay = si.getValue(s, slv);
                maxStack = si.getValue(v, slv);
                EventManager.stopTimer(dragonVeinTraceTimer);
                dragonVeinTraceTimer = EventManager.addFixedRateEvent(() -> stackDragonVeinTrace(maxStack), 0, delay);
                break;

            case UNCONSTRAINED_DRAGON_VEIN:
                if (unconstrainedDragonVeinTimer != null) {
                    return;
                }
                delay = si.getValue(w, slv);
                maxStack = si.getValue(u, slv);
                EventManager.stopTimer(unconstrainedDragonVeinTimer);
                unconstrainedDragonVeinTimer = EventManager.addFixedRateEvent(() -> stackUnstronstrainedDragonVein(maxStack), 0, delay, TimeUnit.SECONDS);
        }
    }

    private void stackMountainSeeds(int maxStack) {
        var tsm = chr.getTemporaryStatManager();
        var currentStack = tsm.getOption(MountainSeeds).nOption;
        var opt = new Option(Math.max(0, Math.min(currentStack + 1, maxStack)), MOUNTAIN_SEEDS, 0);
        tsm.putCharacterStatValue(MountainSeeds, opt, true);
        tsm.sendSetStatPacket();
    }

    private void stackDragonVeinTrace(int maxStack) {
        var tsm = chr.getTemporaryStatManager();
        var currentStack = tsm.getOption(DragonVeinTrace).nOption;
        var opt = new Option(Math.max(0, Math.min(currentStack + 1, maxStack)), DRAGON_VEIN_TRACES, 0);
        tsm.putCharacterStatValue(DragonVeinTrace, opt, true);
        tsm.sendSetStatPacket();
    }

    private void stackUnstronstrainedDragonVein(int maxStack) {
        var tsm = chr.getTemporaryStatManager();
        var currentStack = tsm.getOption(UnconstrainedDragonVein).nOption;
        var opt = new Option(Math.max(0, Math.min(currentStack + 1, maxStack)), UNCONSTRAINED_DRAGON_VEIN, 0);
        tsm.putCharacterStatValue(UnconstrainedDragonVein, opt, true);
        tsm.sendSetStatPacket();
    }
    // endregion

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);

        var cs = chr.getAvatarData().getCharacterStat();
        chr.setJob(JobConstants.JobEnum.LARA_1.getJobId());
        chr.addSpToJobByCurrentJob(5);
        cs.setLevel(10);
        cs.setStr(5);
        cs.setDex(5);
        cs.setInt(5);
        cs.setLuk(5);
        cs.setAp(4 + cs.getLevel() * 5);
        cs.setHp(450);
        cs.setMaxHp(450);
        cs.setMp(300);
        cs.setMaxMp(300);

        var secondary = ItemData.getItemDeepCopy(SIMPLE_FOUR_JADE_ORNAMENT);
        secondary.setBagIndex(BodyPart.Shield.getVal());
        chr.getAvatarData().getAvatarLook().getHairEquips().add(secondary.getItemId());
        chr.getEquippedInventory().addItem(secondary);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        if (!net.swordie.ms.client.character.JobPathModule.classHandlesAdvancement(chr)) {
            return; // job path chosen with @job, or a reborn character: JobPathModule advances it
        }
        var level = chr.getLevel();
        if (level == 10) {
            chr.advanceJob(JobConstants.JobEnum.LARA_1.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);
        } else if (level == 30) {
            chr.advanceJob(JobConstants.JobEnum.LARA_2.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 4);
        } else if (level == 60) {
            chr.advanceJob(JobConstants.JobEnum.LARA_3.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);
        } else if (level == 100) {
            chr.advanceJob(JobConstants.JobEnum.LARA_4.getJobId()); // + its advancement SP
            chr.addStatAndSendPacket(Stat.ap, 5);
        }

    }

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        var tsm = chr.getTemporaryStatManager();
        var field = chr.getField();
        if (chr.hasSkill(PEERLESS_MOUNTAIN) && chr.getMP() >= chr.getSkillStatValue(u, PEERLESS_MOUNTAIN)) {
            var reduction = chr.getSkillStatValue(x, PEERLESS_MOUNTAIN);
            var mobObjId = hitInfo.mobID;
            var mob = field.getLifeByObjectID(Mob.class, mobObjId);
            if (mob == null || !mob.isBoss()) {
                if (hitInfo.clientHpDamage < 0) { // a client's reported hit has it taken off already
                    hitInfo.hpDamage = (int) (((100 - reduction) * hitInfo.hpDamage) / 100D);
                }
                var eff = Effect.skillSpecial(PEERLESS_MOUNTAIN);
                chr.write(UserPacket.effect(eff));
                chr.healMP(-chr.getSkillStatValue(u, PEERLESS_MOUNTAIN));
            }
        }

        super.handleHit(c, inPacket, hitInfo);
    }

    @Override
    public void stopTimers() {
        EventManager.stopTimer(mountainSeedTimer);
        EventManager.stopTimer(dragonVeinTraceTimer);
        EventManager.stopTimer(unconstrainedDragonVeinTimer);
        EventManager.stopTimer(mountainEmbraceTimer);
        EventManager.stopTimer(dragonVeinEchoTimer);

        super.stopTimers();
    }

    public enum VeinType {
        WATER_VEIN(1),
        WIND_VEIN(2),
        DRAGON_VEIN(8),
        SUN_VEIN(4),
        ;

        private int val;

        VeinType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static VeinType getByVal(int val) {
            return Arrays.stream(values()).filter(vt -> vt.getVal() == val).findFirst().orElse(null);
        }
    }
}


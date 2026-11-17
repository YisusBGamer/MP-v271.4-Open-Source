package net.swordie.ms.constants;

import net.swordie.ms.client.jobs.common.HexaSkillHandler;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.runestones.RuneStone;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.Zero;
import net.swordie.ms.client.jobs.adventurer.BeastTamer;
import net.swordie.ms.client.jobs.adventurer.Kinesis;
import net.swordie.ms.client.jobs.adventurer.PinkBean;
import net.swordie.ms.client.jobs.adventurer.archer.Archer;
import net.swordie.ms.client.jobs.adventurer.archer.BowMaster;
import net.swordie.ms.client.jobs.adventurer.archer.Marksman;
import net.swordie.ms.client.jobs.adventurer.archer.Pathfinder;
import net.swordie.ms.client.jobs.adventurer.magician.Bishop;
import net.swordie.ms.client.jobs.adventurer.magician.FirePoison;
import net.swordie.ms.client.jobs.adventurer.magician.IceLightning;
import net.swordie.ms.client.jobs.adventurer.pirate.Buccaneer;
import net.swordie.ms.client.jobs.adventurer.pirate.Cannoneer;
import net.swordie.ms.client.jobs.adventurer.pirate.Corsair;
import net.swordie.ms.client.jobs.adventurer.pirate.Jett;
import net.swordie.ms.client.jobs.adventurer.thief.BladeMaster;
import net.swordie.ms.client.jobs.adventurer.thief.NightLord;
import net.swordie.ms.client.jobs.adventurer.thief.Shadower;
import net.swordie.ms.client.jobs.adventurer.thief.Thief;
import net.swordie.ms.client.jobs.adventurer.warrior.DarkKnight;
import net.swordie.ms.client.jobs.adventurer.warrior.Hero;
import net.swordie.ms.client.jobs.adventurer.warrior.Paladin;
import net.swordie.ms.client.jobs.adventurer.warrior.Warrior;
import net.swordie.ms.client.jobs.anima.HoYoung;
import net.swordie.ms.client.jobs.anima.Lara;
import net.swordie.ms.client.jobs.anima.Ren;
import net.swordie.ms.client.jobs.common.OzSkillHandler;
import net.swordie.ms.client.jobs.common.SoulSkillHandler;
import net.swordie.ms.client.jobs.cygnus.*;
import net.swordie.ms.client.jobs.flora.Adele;
import net.swordie.ms.client.jobs.flora.Ark;
import net.swordie.ms.client.jobs.flora.Illium;
import net.swordie.ms.client.jobs.jianghu.MoXuan;
import net.swordie.ms.client.jobs.legend.*;
import net.swordie.ms.client.jobs.nova.AngelicBuster;
import net.swordie.ms.client.jobs.nova.Cadena;
import net.swordie.ms.client.jobs.nova.Kain;
import net.swordie.ms.client.jobs.nova.Kaiser;
import net.swordie.ms.client.jobs.resistance.*;
import net.swordie.ms.client.jobs.resistance.demon.Demon;
import net.swordie.ms.client.jobs.resistance.demon.DemonAvenger;
import net.swordie.ms.client.jobs.resistance.demon.DemonSlayer;
import net.swordie.ms.client.jobs.sengoku.Hayato;
import net.swordie.ms.client.jobs.sengoku.Kanna;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Summon;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static net.swordie.ms.client.character.skills.SkillStat.coolTimeR;
import static net.swordie.ms.client.jobs.adventurer.magician.Magician.GUIDED_ARROW;
import static net.swordie.ms.client.jobs.anima.HoYoung.ABSORBING_VORTEX;
import static net.swordie.ms.client.jobs.cygnus.WindArcher.SPIRALING_VORTEX_EXPLOSION;
import static net.swordie.ms.client.jobs.flora.Ark.DECENT_HYPER_BODY_V;
import static net.swordie.ms.client.jobs.flora.Ark.DECENT_MYSTIC_DOOR_V;
import static net.swordie.ms.client.jobs.flora.Ark.DECENT_SHARP_EYES_V;
import static net.swordie.ms.client.jobs.flora.Ark.DECENT_SPEED_INFUSION_V;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_AB;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_ADELE;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_ARK;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_CADENA;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_HOYOUNG;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_ILLIUM;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_KAISER;
import static net.swordie.ms.client.jobs.flora.Ark.EXCLUSIVE_SPELL_LARA;
import static net.swordie.ms.client.jobs.flora.Ark.GRANDIS_BLESSING;
import static net.swordie.ms.client.jobs.flora.Ark.RESISTANCE_INFANTRY_1;
import static net.swordie.ms.client.jobs.flora.Ark.RESISTANCE_INFANTRY_2;
import static net.swordie.ms.client.jobs.flora.Ark.ROPE_LIFT;
import static net.swordie.ms.client.jobs.flora.Ark.*;
import static net.swordie.ms.client.jobs.flora.Illium.TEMPLAR_KNIGHT;
import static net.swordie.ms.client.jobs.legend.Aran.*;
import static net.swordie.ms.client.jobs.legend.Mercedes.STAGGERING_STRIKES;
import static net.swordie.ms.client.jobs.legend.Mercedes.STUNNING_STRIKES;
import static net.swordie.ms.client.jobs.legend.Phantom.ACE_IN_THE_HOLE;
import static net.swordie.ms.client.jobs.legend.Phantom.ACE_IN_THE_HOLE_ATOM;
import static net.swordie.ms.client.jobs.legend.Shade.*;
import static net.swordie.ms.client.jobs.nova.AngelicBuster.*;
import static net.swordie.ms.client.jobs.nova.Kaiser.*;
import static net.swordie.ms.client.jobs.resistance.Blaster.*;
import static net.swordie.ms.client.jobs.resistance.demon.DemonAvenger.NETHER_SHIELD;
import static net.swordie.ms.client.jobs.resistance.demon.DemonAvenger.NETHER_SHIELD_ATOM;
import static net.swordie.ms.client.jobs.sengoku.Kanna.*;

/**
 * Created on 12/18/2017.
 */
public class SkillConstants {
    public static final short PASSIVE_HYPER_MIN_LEVEL = 140;
    public static final short ACTIVE_HYPER_SKILL_1 = 140;
    public static final short ACTIVE_HYPER_SKILL_2 = 160;
    public static final short ACTIVE_HYPER_SKILL_3 = 190;

    public static final List<Short> ACTIVE_HYPER_LEVELS = Arrays.asList(
            ACTIVE_HYPER_SKILL_1, // Lv140
            ACTIVE_HYPER_SKILL_2, // Lv170
            ACTIVE_HYPER_SKILL_3  // Lv190
    );

    public static final int HYPER_STAT_STR = 80000400, HYPER_STAT_DEX = 80000401, HYPER_STAT_INT = 80000402,
            HYPER_STAT_LUK = 80000403, HYPER_STAT_DF = 80000406;
    public static final Set<Integer> HYPER_STAT_SKILLS = Util.makeSet(new int[]{
            80000400,
            80000401,
            80000402,
            80000403,
            80000404,
            80000405,
            80000406,
            80000407,
            80000408,
            80000409,
            80000410,
            80000411,
            80000412,
            80000413,
            80000414,
            80000415,
            80000416,
            80000417,
            80000419,
            80000420,
            80000421,
            80000422,
    });

    public static final Set<Integer> NO_RATE_HYPER_STAT_SKILLS = Util.makeSet(new int[]{
            80000400,
            80000401,
            80000402,
            80000403,
    });

    public static final Set<Integer> INVISIBLE_SKILLS_NOT_IN_WZ = Util.makeSet(new int[]{
            // HoYoung
            164111005, // Stone Tremor (Clone/True)

            // Kain
            63120140, // [Possess/Execute]
            63121140,
            63121141, // ~
            63001100, // [Possess] Strike Arrow
            63101100, // [Possess] Strike Arrow
            63101104, // [Possess] Scattering Shot

            // Hayato
            41120013, // Akatsuki Trace

            // Kinesis
            142120002, // Ultimate - Psychic Shot
    });

    private static final Logger log = LogManager.getLogger();

    public static final int LEVEL_UP_DAMAGE_SKILL_ID = 80001770;
    // shared skills whose attack packets carry extra fields
    public static final int SOUL_SHEAR = 80001835;
    public static final int BOMB_PUNCH = 80001913;
    public static final int SPIKES_ROYALE = 80001914;
    public static final int BOMB_PUNCH_2 = 80001915;
    public static final int SMASH_SWING = 80001925;
    public static final int BOMB_PUNCH_3 = 80001926;
    public static final int BOMB_PUNCH_4 = 80001927;
    public static final int SMASH_SWING_2 = 80001936;
    public static final int SMASH_SWING_3 = 80001937;
    public static final int SMASH_SWING_4 = 80001938;
    public static final int AURORA_DOLPHIN_MOUNT = 80002448;
    public static final int ERDA_EXPLOSION = 80002682; // Erda Spectrum
    public static final int NEROTAS_POWER_PROTECTION = 80002819;
    public static final int SURGING_BLADE = 80011045; // named in older data
    public static final int ETHER_PULSE = 80011046; // named in older data

    public static final short LINK_SKILL_1_LEVEL = 70;
    public static final short LINK_SKILL_2_LEVEL = 120;
    public static final short LINK_SKILL_3_LEVEL = 210;

    public static final short ZERO_LINK_SKILL_1_LEVEL = 115;
    public static final short ZERO_LINK_SKILL_2_LEVEL = 140;
    public static final short ZERO_LINK_SKILL_3_LEVEL = 165;
    public static final short ZERO_LINK_SKILL_4_LEVEL = 190;
    public static final short ZERO_LINK_SKILL_5_LEVEL = 215;

    public static final int HERBALISM_SKILL = 92000000;
    public static final int MINING_SKILL = 92010000;
    public static final int SMITHING_CRAFT_SKILL = 92020000;
    public static final int ACCESSORY_CRAFT_SKILL = 92030000;
    public static final int ALCHEMY_CRAFT_SKILL = 92040000;

    public static final int MAKING_SKILL_EXPERT_LEVEL = 10;
    public static final int MAKING_SKILL_MASTER_LEVEL = 11;
    public static final int MAKING_SKILL_MEISTER_LEVEL = 12;

    // used for char specific Xenon pod cooldown.
    public static final int XENON_POD_FOR_COOLDOWN = 899999999;
    public static final int VAMPIRIC_TOUCH_COOLDOWN = 899999998;
    public static final int TIME_LEAP_COOLDOWN = 899999997;
    public static final int CARDINAL_TORRENT_COOLDOWN = Pathfinder.CARDINAL_TORRENT + 200;
    public static final int GUILD_SKILL_BASE = 91000000;
    public static final int GUILD_SKILL_END = 91000044; // includes the Reboot tree's 91000041-44
    public static final int GUILD_NOBLESSE_BASE = 91001022;
    public static final int GUILD_NOBLESSE_END = 91001025;
    public static final int GUILD_SKILL_BASE_2 = 91001016;
    public static final int GUILD_SKILL_END_2 = 91001021;
    public static final int STARLIGHT_EXPLOSION = 80000257;
    public static final int PETITE_AVATAR = 80000260;
    public static final int HYPER_STAT_HP = 80000404;
    public static final int HYPER_STAT_CRITICAL_RATE = 80000409;
    public static final int LIBERATE_THE_RUNE_OF_THUNDER = 80001756;
    public static final int LIBERATE_THE_RUNE_OF_THUNDER_2 = 80001762;
    public static final int SHIKIGAMI_HAUNTING_3RD_HIT = 80001850;
    public static final int M_FORCE_GO = 80002206;
    public static final int SILVER_CREST = 80002325;
    public static final int GOLD_CREST = 80002326;
    public static final int PLATINUM_CREST = 80002327;
    public static final int DIAMOND_CREST = 80002328;
    public static final int MASTER_CREST = 80002329;
    public static final int MOONLIGHT_SPATIAL_TRANSFER = 80002393;
    public static final int MOONLIGHT_HP_RECOVERY = 80002394;
    public static final int MOONLIGHT_WEB_SWEEP = 80002395;
    public static final int MOONLIGHT_INVINCIBILITY = 80002421;
    public static final int FRUITFUL_BOUNTY = 80002452;
    public static final int REMEMBERING_THE_JOURNEY = 80002599;
    public static final int ALLIED_ENCOURAGEMENT = 80002601;
    public static final int PIXEL_WARRIORS = 80002725;
    public static final int MONSTER_BUSTERS = 80002734;
    public static final int SUMMON_SILVERBRISTLE = 80002747;
    public static final int PAPYRUS_OF_LUCK = 80002753;
    public static final int RIFTBRINGER = 80002779;
    public static final int NEROTA_RELIC_HUNT = 80002801;
    public static final int EXPLODING_ROBOT_KABOOM_BOMBER = 80002813;
    public static final int GLORY_GUARD_MEDAL = 80002838;
    public static final int REWIRE = 80003669;
    public static final int ANGLER_PROTO_SUIT = 80003686;
    public static final int PROTO_SUIT_CANNON = 80003689;
    public static final int MATTER_ANTIMATTER_SHIELD = 80003757;
    public static final int BIFROST = 80003850;
    public static final int ENDURING_WILL = 80003894;
    public static final int NATURES_ROAR = 80011787;
    public static final int VESTIGE_OF_DIVINITY = 400001098;
    public static final int HEXA_SHADOW_WALKER = 500001031;
    public static final int HEXA_SHADOW_WALKER_2 = 500001033;
    public static final int HEXA_SHADOW_WALKER_3 = 500001035;


    public static int getPrefix(int skillID) {
        int prefix = skillID / 10000;
        if (prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix;
    }


    public static boolean isSkillNeedMasterLevel(int skillId) {
        if (isIgnoreMasterLevel(skillId)
                || (skillId / 1000000 == 92 && (skillId % 10000 == 0))
                || isMakingSkillRecipe(skillId)
                || isCommonSkill(skillId)
                || isNoviceSkill(skillId)
                || isFieldAttackObjSkill(skillId)) {
            return false;
        }
        if (isPreFourthNeedMasterLevelSkill(skillId)) {
            return true;
        }
        int job = getSkillRootFromSkill(skillId);
        return isPreFourthNeedMasterLevelSkill(skillId) || !JobConstants.isBeastTamer((short) job)
                && (isAddedSpDualAndZeroSkill(skillId) || (JobConstants.getJobLevelForMasterSkillCheck((short) job) == 4 && !JobConstants.isZero((short) job)));
    }

    private static boolean isPreFourthNeedMasterLevelSkill(int skillId) {
        return skillId == 42120024;
    }

    public static boolean isAddedSpDualAndZeroSkill(int skillId) {
        if (skillId > 101100101) {
            if (skillId > 101110203) {
                if (skillId == 101120104)
                    return true;
                return skillId == 101120204;
            } else {
                if (skillId == 101110203 || skillId == 101100201 || skillId == 101110102)
                    return true;
                return skillId == 101110200;
            }
        } else {
            if (skillId == 101100101)
                return true;
            if (skillId > 4331002) {
                if (skillId == 4340007 || skillId == 4341004)
                    return true;
                return skillId == 101000101;
            } else {
                if (skillId == 4331002 || skillId == 4311003 || skillId == 4321006)
                    return true;
                return skillId == 4330009;
            }
        }
    }

    public static int getSkillRootFromSkill(int skillId) {
        int prefix = skillId / 10000;
        if (prefix == 8000 || prefix == 8001) {
            prefix = skillId / 100;
        }
        return prefix;
    }

    public static boolean isFieldAttackObjSkill(int skillId) {
        if (skillId <= 0) {
            return false;
        }
        int prefix = skillId / 10000;
        if (prefix == 8000 || prefix == 8001) {
            prefix = skillId / 100;
        }
        return prefix == 9500;
    }

    private static boolean isNoviceSkill(int skillId) {
        int prefix = skillId / 10000;
        if (skillId / 10000 == 8000) {
            prefix = skillId / 100;
        }
        return JobConstants.isBeginnerJob((short) prefix);
    }

    private static boolean isCommonSkill(int skillId) {
        int prefix = skillId / 10000;
        if (skillId / 10000 == 8000 || skillId / 10000 == 8001) {
            prefix = skillId / 100;
        }
        return (prefix >= 800000 && prefix < 800100);
    }

    public static boolean isMakingSkillRecipe(int recipeId) {
        return recipeId > 92000000 && recipeId < 93000000 && ((recipeId % 10000) != 0);
    }

    public static boolean isUnionSkill(int skillId) {
        return skillId / 10000 == 7100;
    }

    public static boolean isUnionMemberBonusSkill(int skillId) {
        return skillId / 1000 == 71000;
    }

    public static boolean isUnionGridBonusSkill(int skillId) {
        return skillId / 1000 == 71004;
    }

    public static boolean isIgnoreMasterLevel(int skillId) { // updated to v200.1
        switch (skillId) {
            case 1120012:
            case 1320011:
            case 2121009:
            case 2221009:
            case 2321010:
            case 3210015:
            case 4110012:
            case 4210012:
            case 4340010:
            case 4340012:
            case 5120011:
            case 5120012:
            case 5220012:
            case 5220014:
            case 5221022:
            case 5320007:
            case 5321004:
            case 5321006:
            case 21120011:
            case 21120014:
            case 21120020:
            case 21120021:
            case 21121008:
            case 22171069:
            case 23120013:
            case 23121008:
            case 23121011:
            case 33120010:
            case 35120014:
            case 51120000:
            case 80001913:
            case 152120003:
            case 152120012:
            case 152120013:
            case 152121006:
            case 152121010:
                return true;
            default:
                return false;
        }
    }

    /** v271 skills with prepare/keydown data that the client's key-down list (0x140f3ea50) holds. */
    private static final java.util.Set<Integer> V271_KEYDOWN = java.util.Set.of(3201012, 3341010, 3341011, 3341013,
            3341015, 4341052, 4361001, 12001020, 12100020, 12110020, 12120006, 12120018, 12141000, 12141003, 27141500,
            63121040, 63121140, 63141004, 63141026, 63141126, 80002685, 80003075, 80003077, 80003087, 80003302,
            80003370, 80003616, 80003668, 80003669, 80003689, 80004202, 80011362, 80011366, 80011371, 80011381,
            80011382, 80011387, 122001003, 122001007, 131001020, 131001021, 154121002, 154141010, 154141500,
            155141029, 161001003, 164141500, 175121003, 175141006, 400011153, 400011176, 400021041, 400021049,
            400041007, 400041059, 400051006, 400051046, 400051070);
    /** Listed below for v265 but without prepare/keydown data in v271 (or gone from it). */
    private static final java.util.Set<Integer> V271_NOT_KEYDOWN = java.util.Set.of(1311011, 12121054, 13111020,
            20031205, 21121030, 33121114, 33121214, 37121003, 162121022, 400011038, 400031025, 400031046, 400051024);

    public static boolean isKeyDownSkill(int skillId) {
        if (V271_NOT_KEYDOWN.contains(skillId)) {
            return false;
        }
        if (V271_KEYDOWN.contains(skillId)) {
            return true;
        }
        switch (skillId) {
            // Common
            case 80001836: // Vanquisher's Charm
            case 80001887: // Mille Aiguilles
            case 80001880: // Liberate the Rune of Barrage
            case 80001629: // Airship Attack
            case 80001587: // Airship Lv. 1
            case 95001001: // Flying Battle Chair Mount

            // HEXA (6th job)
            case 2241005: // HEXA Lightning Orb
            case 3141000: // HEXA Hurricane
            case 3141001: // HEXA Hurricane: Shootout Mode
            case 3141004: // HEXA Arrow Blaster
            case 5241000: // HEXA Rapid Fire
            case 5241001: // HEXA Rapid Fire
            case 13141000: // HEXA Song of Heaven
            case 21141004: // HEXA Finisher - Hunter's Prey
            case 23141000: // HEXA Ishtar's Ring
            case 24141000: // HEXA Tempest
            case 24141001: // HEXA Mille Aiguilles
            case 33141000: // HEXA Wild Arrow Blast: Apex (v271: channeled)
            case 35141003: // HEXA Homing Beacon
            case 36141014: // HEXA Beam Dance
            case 101141015: // HEXA Wheel Wind

            // Hero

            // Paladin
            case Paladin.GRAND_GUARDIAN:

            // Dark Knight
            case DarkKnight.LA_MANCHA_SPEAR:
            case DarkKnight.CALAMITOUS_CYCLONE:

            // Fire Poison

            // Ice Lightning
            case IceLightning.FREEZING_BREATH:
            case IceLightning.LIGHTNING_ORB:

            // Bishop
            case Bishop.DIVINE_PUNIHSMENT:

            // Bow Master
            case BowMaster.HURRICANE:
            case BowMaster.COVERING_FIRE:
            case BowMaster.ARROW_PLATTER:

            // Marksman
            case Marksman.SURGE_BOLT:

            // Pathfinder
            case Pathfinder.ANCIENT_ASTRA_DELUGE:
            case Pathfinder.ANCIENT_ASTRA_NONE:
            case Pathfinder.ANCIENT_ASTRA_TORRENT:
            case Pathfinder.ANCIENT_ASTRA_BURST_HOLD:

            // Night Lord

            // Shadower
            case Shadower.SONIC_BLOW:

            // Blade Master
            case BladeMaster.FINAL_CUT:
            case BladeMaster.BLADE_TEMPEST:

            // Buccaneer

            // Corsair
            case Corsair.RAPID_FIRE:

            // Cannoneer
            case Cannoneer.MONKEY_WAVE:
            case Cannoneer.NUCLEAR_OPTION:

            // Jett
            case 5700010: // Volt Barrage (Removed)
            case Jett.BLASTER_BARRAGE:
            case Jett.STARFORCE_SALVO:
            case Jett.BACKUP_BEATDOWN:

            // Blaze Wizard
            case BlazeWizard.DRAGON_BLAZE:
            case BlazeWizard.INFERNO_SPHERE:

            // Wind Archer
            case WindArcher.SENTIENT_ARROW:
            case WindArcher.SONG_OF_HEAVEN:

            // Night Walker
            case 14111006: // Poison Bomb (Removed)
            case NightWalker.SHADOW_STITCH:

            // Aran
            case Aran.FINISHER_HUNTER_PREY:
            case Aran.FINISHER_HUNTER_PREY_ATTACK:

            // Evan
            case Evan.DRAGON_MASTER_2:

            // Mercedes
            case Mercedes.ISHTAR_RING:
            case Mercedes.IRKILLAS_WRATH:

            // Phantom
            case Phantom.LUCK_OF_THE_DRAW:
            case Phantom.SHROUD_WALK:
            case Phantom.MILLE_AIGUILLES:
            case Phantom.TEMPEST:

            // Shade
            case Shade.SPIRIT_INCARNATION:
            case Shade.SMASHING_MULTIPUNCH_KEYDOWN:

            // Luminous
            case 20041226: // Spectral Light (Tutorial Skill)
            case Luminous.SPECTRAL_LIGHT:
            case Luminous.PRESSURE_VOID:

            // Demon Slayer
            case DemonSlayer.CARRION_BREATH:
            case DemonSlayer.GRIM_SCYTHE:
            case DemonSlayer.SOUL_EATER:
            case DemonSlayer.DEMON_BANE:
            case DemonSlayer.DEMON_BANE_2:

            // Demon Avenger
            case DemonAvenger.VITALITY_VEIL:
            case DemonAvenger.DEMONIC_BLAST_HOLDDOWN:

            // Battle Mage
            case 32121003: // Twister Spin (Removed Skill)

            // Wild Hunter
            case 33121009: // Old Wild Arrow Blast (Removed Skill)
            case WildHunter.WILD_ARROW_BLAST:
            case WildHunter.WILD_ARROW_BLAST_JAGUAR:
            case WildHunter.WILD_ARROW_BLAST_TYPE_X:
            case 33121000: // Wild Arrow Blast: Apex (v271: channeled)
            case WildHunter.WILD_ARROW_BLAST_OVERDRIVE: // v271 (client 0x140f3ea50)

            // Mechanic
            case Mechanic.FULL_METAL_BARRAGE:

            // Xenon
            case 30021238: // Beam Dance (Tutorial Skill)
            case Xenon.BEAM_DANCE:
            case Xenon.ION_THRUST:

            // Blaster
            case Blaster.HYPER_MAGNUM_PUNCH:
            case Blaster.BALLISTIC_HURRICANE:
            case Blaster.GATLING_PUNCH:

            // Hayato, Kanna: no keydown skills in v271 (v265 Shinsoku / Vanquisher's Charm / Orochi Unbound ids
            // are other skills now)

            // Mihile

            // Kaiser

            // Kain
            case Kain.DRAGON_BURST:
            case Kain.FATAL_BLITZ:
            case Kain.POISON_NEEDLE:

            // Cadena
            case Cadena.CHAIN_ART_REIGN_OF_CHAINS:

            // Angelic Buster
            case AngelicBuster.SOUL_BUSTER:
            case AngelicBuster.SOUL_RESONANCE:
            case Blaster.BULLET_BLAST:

            // Zero
            case Zero.SPIN_DRIVER:
            case Zero.WHEEL_WIND:
            case Zero.ADV_WHEEL_WIND:

            // Beast Tamer
            case BeastTamer.FISHY_SLAP:
            case BeastTamer.FORMATION_ATTACK:
            case BeastTamer.TORNADO_FLIGHT:

            // Pink Bean
            case 131001008: // Sky Jump
            case PinkBean.LETS_ROLL:

            // Kinesis
            case Kinesis.KINETIC_JAUNT:

            // Adele
            case Adele.AETHER_GUARD:

            // Illium
            case Illium.CRYSTAL_IGNITION:

            // Ark
            case Ark.ENDLESS_AGONY:
            case Ark.ABYSSAL_RECALL:
            case Ark.CREEPING_TERROR_HELD_DOWN:

            // HoYoung
            case HoYoung.DREAM_GARDEN:
                return true;

            case Lara.MOUNTAIN_EMBRACE:
                return true;

            default:
                return false;
        }
    }

    public static boolean isLunaSkill(int skillId) {
        return skillId == 11001126
                || skillId == 11100128
                || skillId == 11110128
                || skillId == 11111130
                || skillId == 11120117
                || skillId == 11121157
                || skillId == 11141100;
    }

    public static boolean isSolarSkill(int skillId) {
        return skillId == 11001226
                || skillId == 11100228
                || skillId == 11110228
                || skillId == 11111230
                || skillId == 11120217
                || skillId == 11121257
                || skillId == 11141200;
    }

    public static boolean isKeyDownAttackForRemote(int skillId) {
        return skillId == IceLightning.LIGHTNING_ORB || skillId == 12121054;
    }

    public static boolean isCooltimeOnCancelSkill(int skillId) {
        return skillId == FirePoison.ELEMENTAL_ADAPTATION_FP
                || skillId == IceLightning.ELEMENTAL_ADAPTATION_IL
                || skillId == Bishop.DIVINE_PROTECTION
                || skillId == NightWalker.DARKNESS_ASCENDING
                || skillId == Phantom.FINAL_FEINT
                || skillId == SUMMON_OTHER_SPIRIT
                || skillId == Luminous.SHADOW_SHELL
                || skillId == BlazeWizard.INFERNO_SPHERE
                || skillId == DarkKnight.CALAMITOUS_CYCLONE
                || skillId == Kanna.OROCHI_UNBOUND
                || skillId == SOUL_RESONANCE
                || skillId == SPIRIT_FRENZY
                || skillId == SPIRIT_INCARNATION
                || skillId == Jett.BACKUP_BEATDOWN
                || skillId == Blaster.BALLISTIC_HURRICANE
                || skillId == DemonAvenger.VITALITY_VEIL
                || skillId == Ark.ENDLESS_AGONY
                || skillId == Phantom.SHROUD_WALK
                || skillId == Phantom.TEMPEST
                ;
    }

    public static boolean isCooltimeOnStartSkill(int skillId) {
        return skillId == Hero.SWORD_ILLUSION || skillId == DemonSlayer.DEMON_BANE
                || skillId == Illium.CRYSTAL_IGNITION || skillId == Paladin.GRAND_GUARDIAN
                || skillId == Adele.AETHER_GUARD || skillId == Marksman.SURGE_BOLT
                || skillId == Shadower.SONIC_BLOW || skillId == DarkKnight.CALAMITOUS_CYCLONE
                || skillId == Lara.MOUNTAIN_EMBRACE;
    }

    public static int getKeydownMPConInterval(int skillId) {
        var si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return 0;
        }

        switch (skillId) {
            case Corsair.BULLET_BARRAGE:
                return 1000;
            case Phantom.PHANTOMS_MARK:
                return 5000;
            case DemonSlayer.DEMON_BANE:
                return 4000;
            case WildHunter.WILD_ARROW_BLAST_TYPE_X:
                return 7200;
        }

        return si.getKeyDownConsumptionInterval();
    }

    public static boolean isEvanForceSkill(int skillId) {
        switch (skillId) {
            case Evan.DRAGON_DIVE_2:
            case Evan.WIND_CIRCLE:
            case Evan.DRAGON_FLASH:
            case Evan.DRAGON_FLASH_2:
            case Evan.DRAGON_FLASH_3:
            case 22111017:
            case 80001894:
            case Evan.EARTH_CIRCLE:
            case Evan.DRAGON_BREATH:
            case Evan.THUNDER_CIRCLE:
            case Evan.DRAGON_DIVE:
            case Evan.DRAGON_SLAM:
            case Evan.WYRMKINGS_BREATH:
            case 400021012:
            case 22201002: // HEXA fusion variants carry the same extra byte
            case 22201003:
            case 22201006:
            case 22201007:
            case 22201010:
            case 22201011:
                return true;
        }
        return false;
    }

    public static boolean isDragonAction(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        switch (skillId) {
            case Evan.WIND_FLASH:
            case Evan.WIND_FLASH_2:
            case Evan.WIND_FLASH_3:
            case Evan.DRAGON_FLASH:
            case Evan.DRAGON_FLASH_2:
            case Evan.DRAGON_FLASH_3:
            case Evan.DRAGON_DIVE:
            case Evan.DRAGON_DIVE_2:
            case Evan.THUNDER_DIVE:
            case Evan.THUNDER_DIVE_2:
            case Evan.THUNDER_FLASH:
            case Evan.THUNDER_FLASH_2:
            case Evan.EARTH_BREATH:
            case Evan.DRAGON_BREATH:
            case Evan.WIND_BREATH:
            case Evan.EARTH_DIVE:
            case Evan.DRAGON_SLAM:
            case Evan.WYRMKINGS_BREATH:
                return true;
        }
        if (si == null) {
            return false;
        }
        return si.isDragonAction();
    }

    public static int getOriginalDragonSkill(int skillId) {
        switch (skillId) {
            case Evan.WIND_FLASH:
            case Evan.WIND_FLASH_2:
            case Evan.WIND_FLASH_3:
                return Evan.WIND_FLASH;
            case Evan.DRAGON_FLASH:
            case Evan.DRAGON_FLASH_2:
            case Evan.DRAGON_FLASH_3:
                return Evan.DRAGON_FLASH;
            case Evan.DRAGON_DIVE:
            case Evan.DRAGON_DIVE_2:
                return Evan.DRAGON_DIVE;
            case Evan.THUNDER_DIVE:
            case Evan.THUNDER_DIVE_2:
                return Evan.THUNDER_DIVE;
            case Evan.THUNDER_FLASH:
            case Evan.THUNDER_FLASH_2:
                return Evan.THUNDER_FLASH;
        }
        return skillId;
    }

    public static boolean isSuperNovaSkill(int skillID) {
        return skillID == 4221052 || skillID == 65121052
                || skillID == 4241013 // HEXA Shadow Veil
                || skillID == 65141005; // HEXA Supreme Supernova
    }

    public static boolean isRushBombSkill(int skillID) {
        // the client's is_rushbomb_skill (v265 0x1410443e0, v271 0x140ff2670): melee/magic attacks of these carry the grenade id
        switch (skillID) {
            case 2221012: // Frozen Orb
            case 2241002: // HEXA Frozen Orb
            case 5301001: // Barrel Bomb
            case 11101029: // Cosmic Matter
            case 11101030: // Cosmic Matter
            case 12121001: // Blazing Extinction
            case 12141007: // HEXA Blazing Extinction
            case 14101028: // Shadow Spark
            case 14101029: // Shadow Spark
            case 15101028: // Sea Wave
            case 15141009: // HEXA Sea Wave
            case 22140015: // Thunder Dive
            case 22140024: // Thunder Dive
            case 22200009: // HEXA Thunder Dive
            case 31201001: // Bat Swarm
            // v271 (0x140ff25a0): no Kanna ids (42141008 / 42141010 are Oni attacks / orders now)
            case 61111100: // Wing Beat
            case 61111218: // Wing Beat
            case 61141015: // HEXA Wing Beat
            case 61141017: // HEXA Wing Beat
            case 64101002: // Summon Shuriken
            case 64141032: // HEXA Summon Shuriken
            case 80002247: // Maple Rumble
            case 80002300: // Stellar Staff
            case 80011380: // Hydro-drone Blitz
            case 80011386: // Hydro-drone Blitz
            case 80011999: // 
            case 101120200: // Wind Cutter
            case 101120203: // Storm Break
            case 101120205: // Severe Storm Break
            case 101141007: // HEXA Wind Cutter
            case 101141010: // HEXA Storm Break
            case 122001013: // Hellstorm
            case 400001018: // Phalanx Charge
            case 400021131: // Winding Mountain Ridge
            case 400031003: // Howling Gale
            case 400031004: // Howling Gale
            case 400031036: // Raven Tempest
            case 400031067: // Fury of the Wild Tempest
            case 400031068: // Howling Gale
            case 500001125: // HEXA Phalanx Charge
            case 500001126:
            case 500001127:
            case 500001128:
            case 500001129:
            case 500001130:
                return true;
        }
        return false;
    }

    /**
     * HEXA-boosted 5th job skills (500061xxx) that no job handler knows yet: the cast is handled as the 5th job skill
     * it replaces on the quickslot. Returns the skill itself when there is no such base.
     */
    public static int getHexaBoostBaseSkill(int skillID) {
        switch (skillID) {
            case 500061012: return 400021068; // Crystalline Spirit
            case 500061013: return 400051036; // Infinity Spell
            case 500061014: return 400041048; // Sage: Clone Rampage
            case 500061016: return 400031053; // Silhouette Mirage
            case 500061033: return 400031030; // Gale Barrier
            case 500061036: return 400051007; // Lightning Cascade
            case 500061046: return 400031007; // Spirit of Elluel
            case 500061054: return 400011112; // Revenant
            case 500061059: return 400041029; // Core Overload
            case 500061062: return 400011015; // Chrono Break
            case 500061065: return 400011109; // Legacy Restoration
            case 500061066: return 400021099; // Crystal Gate
            case 500061069: return 400021054; // Spirit's Domain
            case 500061070: return 400051088; // Soul Art: The Opened Gate
            case 500061071: return 400021138; // [Focus] Awaken
            case 500061072: return 400021139; // Nature's Grace
            case 500061074: return 400021142; // Shine
            case 500061075: return 400021149; // Savior's Circle
            default: return skillID;
        }
    }

    /** First id and span of the HEXA-boosted skills (5000610xx): their skill info only exists for characters that learned them */
    private static final int HEXA_BOOST_SKILL_BASE = 500061000;
    private static final int HEXA_BOOST_SKILL_SPAN = 1000;

    public static boolean isHexaBoostSkill(int skillID) {
        return skillID >= HEXA_BOOST_SKILL_BASE && skillID < HEXA_BOOST_SKILL_BASE + HEXA_BOOST_SKILL_SPAN;
    }

    /** Attacks that carry the key-down int after the skill-use info (client 0x140f96f10, plus the special melee writer's ids) */
    public static boolean isAttackKeyDownSkill(int skillID) {
        switch (skillID) {
            case 2221011: // Freezing Breath
            case 2221052: // Lightning Orb
            case 2241005: // HEXA Lightning Orb
            case 3101008: // Covering Fire
            case 3111013: // Arrow Blaster
            case 3121020: // Hurricane
            case 3141000: // HEXA Hurricane
            case 3141001: // HEXA Hurricane: Shootout Mode
            case 3141004: // HEXA Arrow Blaster
            case 3201012: // Covering Fire
            case 4341002: // Final Cut
            case 5221004: // Rapid Fire
            case 5241000: // HEXA Rapid Fire
            case 5241001: // HEXA Rapid Fire
            // v271 (0x140f3d820) adds the Orbital Flames, Dream Darts and the new Kinetic Jaunt; no Jett, Beast Tamer,
            // old Kinetic Jaunt, Wild Arrow Blast Type X or Nuclear Option
            case 12001020: // Orbital Flame
            case 12100020: // Greater Orbital Flame
            case 12110020: // Grand Orbital Flame
            case 12120006: // Final Orbital Flame
            case 12120018: // Final Orbital Flame
            case 12141000: // HEXA Orbital Flame
            case 12141003: // HEXA Orbital Flame
            case 13121001: // Song of Heaven
            case 13141000: // HEXA Song of Heaven
            case 14121004: // Shadow Stitch
            case 20041226: // Spectral Light
            case 21121029: // Finisher - Hunter's Prey
            case 21141004: // HEXA Finisher - Hunter's Prey
            case 22171083: // Dragon Master
            case 23121000: // Ishtar's Ring
            case 23141000: // HEXA Ishtar's Ring
            case 24121000: // Mille Aiguilles
            case 24121005: // Tempest
            case 24141000: // HEXA Tempest
            case 24141001: // HEXA Mille Aiguilles
            case 25121030: // Spirit Incarnation
            case 27101202: // Pressure Void
            case 27111100: // Spectral Light
            case 30021238: // Beam Dance
            case 31001000: // Grim Scythe
            case 31101000: // Soul Eater
            case 31111005: // Carrion Breath
            case 31211001: // Vitality Veil
            // v271 (0x140f3d820): no Wild Hunter or Kanna ids
            case 35101002: // Homing Beacon
            case 35110017: // Advanced Homing Beacon
            case 35141003: // HEXA Homing Beacon
            case 36101001: // Ion Thrust
            case 36121000: // Beam Dance
            case 36141014: // HEXA Beam Dance
            case 37121052: // Hyper Magnum Punch
            case 60011216: // Soul Buster
            case 64001000: // Chain Arts: Pursuit
            case 64001007: // Chain Arts: Pursuit
            case 64001008: // Chain Arts: Pursuit
            case 64121002: // Chain Arts: Reign of Chains
            case 80001389: // 
            case 80001390: // 
            case 80001391: // 
            case 80001392: // 
            case 80001587: // Airship Lv. 1
            case 80001629: // Airship Attack
            case 80001836: // Vanquisher's Charm
            case 80001887: // Mille Aiguilles
            case 80002458: // 
            case 80002685: // Erda Spectrum: Erda Release
            case 80002780: // 
            case 80002785: // 
            case 80003075: // Hammer Slam!
            case 80003076: // Hammer Slam!
            case 80003077: // Hammer Slam!
            case 80003087: // Kick
            case 80003302: // Hurricane
            case 80003370: // Erda Release
            case 80003616: // Banana Kick
            case 80003668: // Rewire
            case 80003669: // Rewire
            case 80003689: // Proto-Suit Cannon
            case 80011362: // Basic Hydroblaster
            case 80011366: // Bubble Blaster
            case 80011371: // Bubble Blaster
            case 80011381: // Water Tank
            case 80011382: // Water Cannon
            case 80011387: // Water Cannon
            case 80004202: // Dream Darts
            case 95001001: // Flying Battle Chair Mount
            case 101110101: // Wheel Wind
            case 101110102: // Advanced Wheel Wind
            case 101141015: // HEXA Wheel Wind
            case 131001004: // Let's Roll!
            case 131001008: // Sky Jump
            case 131001020: // Pink Bean's Tutu
            case 131001021: // Pink Bean's Scooter
            case 142111005: // Kinetic Jaunt
            case 161001003: // Lilting Petal
            case 400011028: // Gatling Punch
            case 400011072: // Grand Guardian
            case 400011091: // Bullet Blast
            case 400021061: // Crystal Ignition
            case 400041006: // Blade Storm
            case 400041009: // Luck of the Draw
            // special melee writer 0x144e19fb0
            case 3011004: // Cardinal Deluge
            case 3300002: // Cardinal Deluge Amplification
            case 3321003: // Cardinal Deluge
            case 3341004: // HEXA Cardinal Deluge
            case 3341005: // HEXA Bountiful Deluge
            case 14110034: // Shadow Bat (enhanced)
            case 14110035: // Shadow Bat (enhanced)
            case 14141009: // HEXA Shadow Bat (enhanced)
            case 14141010: // HEXA Shadow Bat (enhanced)
            case 31241001: // HEXA Nether Shield
            case 61141006: // HEXA Tempest Blades
            case 61141007: // HEXA Tempest Blades
            case 152141000: // HEXA Radiant Javelin
            case 152141001: // HEXA Radiant Javelin
            // processtype / additional_process has 11 (0x140f3d820 -> 0x1410990e0(id, 11))
            case 3321035: // Ancient Astra
            case 3321036: // Ancient Astra (Deluge)
            case 3321038: // Ancient Astra (Burst)
            case 3321040: // Ancient Astra (Torrent)
            case 3341010: // HEXA Ancient Astra
            case 3341011: // HEXA Ancient Astra (Deluge)
            case 3341013: // HEXA Ancient Astra (Burst)
            case 3341015: // HEXA Ancient Astra (Torrent)
            case 27141500: // Harmonic Paradox
            case 33121000: // Wild Arrow Blast: Apex
            case 33141000: // HEXA Wild Arrow Blast: Apex
            case 63121006: // [Execute] Poison Needle
            case 63121008: // Dragon Scale
            case 63121040: // Unseen Sniper
            case 63121140: // [Possess/Execute] Unseen Sniper
            case 63141004: // [Execute] HEXA Poison Needle
            case 63141026: // HEXA Unseen Sniper
            case 63141126: // HEXA [Possess/Execute] Unseen Sniper
            case 65121003: // Soul Resonance
            case 80003362: // Harpoon Barrage
            case 80003966: // Incineration Cannon
            case 80012875: // Multistrike
            case 80012880: // Multistrike Crystal - Range
            case 122001003: // Consecutive Normal Punches
            case 122001007: // Killer Move: Serious Series - Serious Sideways Jumps
            case 151121004: // Aether Guard
            case 154121002: // Hex: Chakram Fury
            case 154141010: // HEXA Hex: Chakram Fury
            case 154141500: // Hex: Sandstorm
            case 155111306: // Creeping Terror
            case 155121341: // Endless Agony
            case 155141029: // HEXA Endless Agony
            case 161121000: // Plum Blossom Sword: Storm
            case 161141000: // HEXA Plum Blossom Sword: Storm
            case 164121042: // Sage: Dream of Shangri-La
            case 164141500: // Sage: Apotheosis
            case 164141502: // Sage: Apotheosis
            case 172121004: // Whirlwind
            case 175121003: // Divine Art: Howling Storm
            case 175141006: // HEXA Divine Art: Howling Storm
            case 400011068: // Calamitous Cyclone
            case 400011110: // Demon Bane
            case 400011111: // Demon Bane
            case 400011153: // Final Plum Blossom Sword: Dancing Annihilation
            case 400011176: // Destruction of Roan
            case 400021041: // Aether Conduit
            case 400021049: // Aether Conduit
            case 400021072: // Inferno Sphere
            case 400021086: // Divine Punishment
            case 400031024: // Irkalla's Wrath
            case 400031061: // [Possess] Dragon Burst
            case 400031065: // [Execute] Fatal Blitz
            case 400031072: // Wild Arrow Blast: Overdrive
            case 400041007: // Omega Blaster
            case 400041039: // Sonic Blow
            case 400041059: // Rapid Throw
            case 400051041: // Full Metal Barrage
            case 400051046: // Mighty Mascot
            case 400051070: // Howling Fist
            case 400051078: // Smashing Multipunch
            case 400051334: // Abyssal Recall
                return true;
        }
        return false;
    }

    public static boolean isChangeFieldWithOwnerSummon(Summon summon) {
        switch (summon.getSkillID()) {
            case BattleMage.GRIM_HARVEST:
            case Ren.THOUSAND_BLOSSOM_FLURRY: // stationary, yet GMS carries them over
            case Ren.HEARTBOUND_VERSE_FALLING_FLOWER:
                return true;

            case Hayato.IAIJUTSU_PHANTOM_BLADE:
            case DawnWarrior.SOUL_ECLIPSE:
                return false;

        }

        return summon.getMoveAbility().changeFieldWithOwner();
    }

    public static boolean isZeroSkill(int skillID) {
        int prefix = getPrefix(skillID);
        // as the client (0x141063440): every 101xx book counts, HEXA 10114 included
        return prefix == 10000 || (prefix >= 10100 && prefix < 10200);
    }

    public static boolean isPhantomSkill(int skillID) {
        int prefix = getPrefix(skillID);
        return prefix == 2400 || prefix == 2410 || prefix == 2411 || prefix == 2412;
    }

    public static boolean isUsercloneSummonedAbleSkill(int skillID) {
        // the client's list (0x140577850, used by every attack writer): these attacks carry an extra int
        switch (skillID) {
            case 11111130: // Flicker
            case 11111230: // Bluster
            case 14001020: // Lucky Seven
            case 14101020: // Triple Throw
            case 14101021: // Triple Throw
            case 14101028: // Shadow Spark
            case 14101029: // Shadow Spark
            case 14111020: // Quad Star
            case 14111021: // Quad Star
            case 14120045: // Quintuple Star - Critical Chance
            case 14121001: // Quintuple Star
            case 14121002: // Quintuple Star
            case 14141000: // HEXA Quintuple Star
            case 14141001: // HEXA Quintuple Star
            case 14141002: // HEXA Quintuple Star
            case 14141003: // HEXA Quintuple Star
            case 23001000: // Swift Dual Shot
            case 23100004: // Parting Shot
            case 23101000: // Piercing Storm
            case 23101001: // Rising Rush
            case 23101007: // Rising Rush
            case 23110006: // Aerial Barrage
            case 23111000: // Stunning Strikes
            case 23111001: // Leaf Tornado
            case 23111002: // Unicorn Spike
            case 23111003: // Gust Dive
            case 23120013: // Staggering Strikes
            case 23121000: // Ishtar's Ring
            case 23121002: // Spikes Royale
            case 23121003: // Lightning Edge
            case 23121011: // Rolling Moonsault
            case 23121052: // Wrath of Enlil
            case 23141000: // HEXA Ishtar's Ring
            case 23141003: // HEXA Wrath of Enlil
            case 23141004: // HEXA Wrath of Enlil: Spirit Enchant
            case 23141005: // HEXA Spikes Royale
            case 23141006: // HEXA Spikes Royale: Spirit Enchant
            case 23141007: // HEXA Leaf Tornado
            case 23141008: // HEXA Leaf Tornado: Spirit Enchant
            case 23141009: // HEXA Leaf Tornado: Spirit Enchant
            case 23141010: // HEXA Stunning Strikes
            case 23141011: // HEXA Unicorn Spike
            case 23141012: // HEXA Gust Dive
            case 101000100: // Air Raid
            case 101000101: // Air Riot
            case 101000200: // Piercing Thrust
            case 101000201: // Shadow Strike
            case 101001100: // Rising Slash
            case 101001200: // Moon Strike
            case 101100100: // Throwing Weapon
            case 101100101: // Advanced Throwing Weapon
            case 101100200: // Blade Ring
            case 101100201: // Advanced Blade Ring
            case 101101100: // Flash Cut
            case 101101200: // Flash Assault
            case 101110101: // Wheel Wind
            case 101110102: // Advanced Wheel Wind
            case 101110104: // Advanced Wheel Wind
            case 101110200: // Grand Rolling Cross
            case 101110202: // Rolling Assault
            case 101110203: // Advanced Rolling Assault
            case 101111100: // Spin Driver
            case 101111200: // Rolling Cross
            case 101120100: // Falling Star
            case 101120102: // Groundbreaker
            case 101120104: // Mega Groundbreaker
            case 101120200: // Wind Cutter
            case 101120201: // Wind Striker
            case 101120202: // Storm Break
            case 101120204: // Advanced Storm Break
            case 101121100: // Giga Crash
            case 101121200: // Wind Cutter
            case 101141000: // HEXA Giga Crash
            case 101141001: // HEXA Falling Star
            case 101141003: // HEXA Groundbreaker
            case 101141006: // HEXA Wind Cutter
            case 101141007: // HEXA Wind Cutter
            case 101141008: // HEXA Wind Striker
            case 101141009: // HEXA Storm Break
            case 101141014: // HEXA Spin Driver
            case 101141015: // HEXA Wheel Wind
            case 101141016: // HEXA Wheel Wind
            case 101141017: // HEXA Rolling Cross
            case 101141019: // HEXA Rolling Assault
            case 101141021: // HEXA Flash Cut
            case 101141022: // HEXA Throwing Weapon
            case 101141023: // HEXA Flash Assault
            case 101141024: // HEXA Blade Ring
            case 101141026: // HEXA Rising Slash
            case 101141027: // HEXA Air Raid
            case 101141029: // HEXA Moon Strike
            case 101141030: // HEXA Piercing Thrust
            case 101141031: // HEXA Shadow Strike
            case 131001000: // Pink Powerhouse
            case 131001001: // Pink Powerhouse
            case 131001002: // Pink Powerhouse
            case 131001003: // Pink Powerhouse
            case 131001004: // Let's Roll!
            case 131001005: // Umbrella
            case 131001008: // Sky Jump
            case 131001010: // Blazing Yo-yo
            case 131001011: // Blazing Yo-yo
            case 131001012: // Pink Pulverizer
            case 131001013: // Let's Rock!
            case 131001101: // Pink Powerhouse
            case 131001102: // Pink Powerhouse
            case 131001103: // Pink Powerhouse
            case 131001104: // Let's Roll!
            case 131001108: // Mid-air Sky Jump
            case 131001113: // Electric Guitar
            case 131001208: // Sky Jump Grounder
            case 131001213: // Whistle
            case 131001313: // Megaphone
            case 131002010: // Blazing Yo-yo
            case 400031024: // Irkalla's Wrath
            case 400041059: // Rapid Throw
            case 400041060: // Rapid Throw
                return true;
        }
        return false;
    }

    public static boolean isNoconsumeUsebulletMeleeAttack(int skillID) {
        return skillID == 14121052 || skillID == 14121003 || skillID == 14000028 || skillID == 14000029;
    }

    public static boolean isScreenCenterAttackSkill(int skillID) {
        switch (skillID) {
            case 80001431:
            case 80011562:
            case 100001283:
            case 21121057:
            case 13121052:
            case 14121052:
            case 15121052:
            case 13141005: // HEXA Monsoon
            case 14141018: // HEXA Dominion
            case 15141007: // HEXA Deep Rising
            case 101141033: // HEXA Shadow Rain
                return true;
        }
        return false;
    }

    public static int getSkillIdByAtomSkillId(int skillID) {
        switch (skillID) {
            case SOUL_SEEKER_ATOM:
                return SOUL_SEEKER;
            case FirePoison.MEGIDDO_FLAME_ATOM:
                return FirePoison.MEGIDDO_FLAME;
            case Shadower.MESO_EXPLOSION_ATOM:
                return Shadower.MESO_EXPLOSION;
            case NightLord.ASSASSIN_MARK_ATOM:
                return NightLord.ASSASSINS_MARK;
            case NightLord.NIGHTLORD_MARK_ATOM:
                return NightLord.NIGHT_LORD_MARK;
            case FOX_SPIRITS_ATOM:
                return FOX_SPIRITS_INIT;
            case FOX_SPIRITS_ATOM_2:
                return FIRE_FOX_SPIRIT_MASTERY;
            case NETHER_SHIELD_ATOM:
                return NETHER_SHIELD;
            case ACE_IN_THE_HOLE_ATOM:
                return ACE_IN_THE_HOLE;
            default:
                return skillID;
        }
    }

    public static boolean isForceAtomSkill(int skillID) {
        switch (skillID) {

            // Adventurer
            // Paladin
            case Paladin.MIGHTY_MJOLNIR:

            // Fire Poison
            case FirePoison.MEGIDDO_FLAME_ATOM:
            case FirePoison.DOT_PUNISHER:

                // Archer
            case Archer.GUIDED_ARROW:

                // BowMaster
            case BowMaster.QUIVER_CARTRIDGE_ATOM:
            case BowMaster.ENCHANTED_QUIVER_ATOM:
            case BowMaster.HEXA_QUIVER_CARTRIDGE_ATOM:
            case BowMaster.INHUMAN_SPEED_2:
            case BowMaster.QUIVER_BARRAGE_ATOM:
            case BowMaster.SILHOUETTE_MIRAGE_ATTACK:

                // Pathfinder
            case Pathfinder.CARDINAL_DELUGE:
            case Pathfinder.CARDINAL_DELUGE_AMPLIFICATION:
            case Pathfinder.CARDINAL_DELUGE_ADVANCED:
            case Pathfinder.BOUNTIFUL_DELUGE:
            case Pathfinder.SWARM_SHOT_ATOM:
            case Pathfinder.BOUNTIFUL_BURST:
            case Pathfinder.ANCIENT_ASTRA_DELUGE_ATOM:

                // Night Lord
            case NightLord.ASSASSIN_MARK_ATOM:
            case NightLord.NIGHTLORD_MARK_ATOM:
            case NightLord.DARK_LORDS_OMEN:
            case 4141004: // HEXA Assassin's Mark

                // Shadower
            case Shadower.MESO_EXPLOSION_ATOM:


                // Cygnus
                // Blaze Wizard
            case BlazeWizard.ORBITAL_FLAME_ATOM:
            case BlazeWizard.GREATER_ORBITAL_FLAME_ATOM:
            case BlazeWizard.GRAND_ORBITAL_FLAME_ATOM:
            case BlazeWizard.FINAL_ORBITAL_FLAME_ATOM:
            case BlazeWizard.SAVAGE_FLAME_FOX_ATOM:

                // Wind Archer
            case WindArcher.TRIFLING_WIND_I:
            case WindArcher.TRIFLING_WIND_II:
            case WindArcher.TRIFLING_WIND_III:
            case WindArcher.TRIFLING_WIND_ATOM_ENHANCED:
            case WindArcher.TRIFLING_WIND_II_ATOM:
            case WindArcher.TRIFLING_WIND_III_ENHANCED:
            case WindArcher.STORM_BRINGER:
            case WindArcher.GALE_BARRIER_ATOM:
            case WindArcher.MERCILESS_WINDS:
            case 500061034: // HEXA Gale Barrier

                // Night Walker
            case NightWalker.SHADOW_BAT_ATOM:
            case NightWalker.SHADOW_BAT_FROM_MOB_ATOM:
            case NightWalker.HEXA_SHADOW_BAT_ATOM:
            case NightWalker.HEXA_SHADOW_BAT_FROM_MOB_ATOM:


                // Legend
                // Evan
            case Evan.MAGIC_DEBRIS:
            case Evan.ENHANCED_MAGIC_DEBRIS:

                // Phantom
            case Phantom.CARTE_NOIR:
            case Phantom.HEXA_CARTE_NOIR:
            case Phantom.CARTE_BLANCHE:
            case Phantom.LUCK_OF_THE_DRAW_ATOM:
            case Phantom.ACE_IN_THE_HOLE_ATOM:

                // Shade
            case Shade.FOX_SPIRITS_ATOM:
            case Shade.FOX_SPIRITS_ATOM_2:


                // Resistance
                // Demon Avenger
            case DemonAvenger.NETHER_SHIELD_ATOM:

                // Battle Mage
            case BattleMage.GRIM_HARVEST:

                // Mechanic
            case Mechanic.MOBILE_MISSILE_BATTERY:
            case Mechanic.HOMING_BEACON_RESEARCH:
            case Mechanic.ADV_HOMING_BEACON:
            case Mechanic.HOMING_BEACON:

            case Xenon.AEGIS_SYSTEM_ATOM:
            case Xenon.PINPOINT_SALVO:
            case Xenon.PINPOINT_SALVO_REDESIGN_A:
            case Xenon.PINPOINT_SALVO_REDESIGN_B:
            case Xenon.PINPOINT_SALVO_PERFECT_DESIGN:
                // Sengoku
                // Kanna
                // TODO Kanna's new ForceAtom


                // Nova
                // Kaiser
            case Kaiser.BLADEFALL_ATTACK:
            case Kaiser.BLADEFALL_ATTACK_FF:
            case Kaiser.TEMPEST_BLADES_THREE:
            case Kaiser.TEMPEST_BLADES_THREE_FF:
            case Kaiser.TEMPEST_BLADES_FIVE:
            case Kaiser.TEMPEST_BLADES_FIVE_FF:

                // Angelic Buster
            case AngelicBuster.SOUL_SEEKER_ATOM:
            case AngelicBuster.SOUL_SEEKER_EXPERT_ATOM:
            // case AngelicBuster.SPARKLE_BURST:


                // Flora
                // Illium
            case Illium.RADIANT_JAVELIN:
            case Illium.RADIANT_JAVELIN_II:
            case Illium.RADIANT_JAVELIN_ENHANCED:
            case Illium.RADIANT_JAVELIN_II_SPLIT:
            case Illium.RESONANT_WINGS_ATOM:

                // Ark
            case Ark.IMPENDING_DEATH_ATOM:
            case Ark.VENGEFUL_HATE:
            case Ark.BASIC_CHARGE_DRIVE_ATOM:
            case Ark.SCARLET_CHARGE_DRIVE_ATOM:
            case Ark.GUST_CHARGE_DRIVE_ATOM:
            case Ark.ABYSSAL_CHARGE_DRIVE_ATOM:


                // HoYoung
            case HoYoung.CLONE_SEAL_ATTACK:
            case HoYoung.BUTTERFLY_DREAM_ATT:

                // Others
                return true;
            default:
                return false;
        }
    }

    public static boolean isAranFallingStopSkill(int skillID) {
        switch (skillID) {
            case 21110028:
            case 21120025:
            case 21110026:
            case 21001010:
            case 21000006:
            case 21000007:
            case 21110022:
            case 21110023:
            case 80001925:
            case 80001926:
            case 80001927:
            case 80001936:
            case 80001937:
            case 80001938:
                return true;
            default:
                return false;
        }
    }

    public static boolean isWindSwing(int skillId) {
        return skillId == Lara.MANIFESTATION_WIND_SWING_ACTIVE_1;
    }

    /** Areas whose packet carries the flip byte: exactly the client's list (0x140f9b860). */
    public static boolean isFlipAffectAreaSkill(int skillID) {
        if (isSomeAreaAffectSkill(skillID)) {
            return true;
        }
        switch (skillID) {
            case 4121015:
            case 4221006: // Smokescreen
            case 131001107:
            case 131001207:
            case 135001012: // Homemade Snowman
            case 152121041:
            case 152141015: // HEXA Longinus Zone
            case 400001017:
            case 400021039:
            case 400041041:
                return true;
        }
        return false;
    }

    /** The client's inner list (0x140f9ae80). */
    private static boolean isSomeAreaAffectSkill(int skillID) {
        switch (skillID) {
            case 33111013:
            case 33121012:
            case 33121016:
            case 33141023: // HEXA Hunting Assistant Unit
            case 33141024: // HEXA Drill Salvo
            case 35121052:
            case 35141004: // HEXA Distortion Bomb
            case 35141005:
            case 400020046:
            case 400020051:
                return true;
        }
        return false;
    }

    public static boolean isShootSkillNotConsumingBullets(int skillID) {
        int job = skillID / 10000;
        if (skillID / 10000 == 8000) {
            job = skillID / 100;
        }
        switch (skillID) {
            case 80001279:
            case 80001914:
            case 80001915:
            case 80001880:
            case 80001629:
            case 33121052:
            case 33101002:
            case 14101006:
            case 13101020:
            case 1078:
                return true;
            default:
                return getDummyBulletItemIDForJob(job, 0, 0) > 0
                        || isShootSkillNotUsingShootingWeapon(skillID, false)
                        || isFieldAttackObjSkill(skillID);

        }
    }

    private static boolean isShootSkillNotUsingShootingWeapon(int skillID, boolean bySteal) {
        if (bySteal || (skillID >= 80001848 && skillID <= 80001850)) {
            return true;
        }
        switch (skillID) {
            case 80001863:
            case 80001880:
            case 80001914:
            case 80001915:
            case 80001939:
            case 101110204:
            case 101110201:
            case 101000202:
            case 101100202:
            case 80001858:
            case 80001629:
            case 80001829:
            case 80001838:
            case 80001856:
            case 80001587:
            case 80001418:
            case 80001387:
            case 61111215:
            case 80001279:
            case 61001101:
            case 51121008:
            case 51111007:
            case 51001004:
            case 36111010:
            case 36101009:
            case 31111005:
            case 31111006: // ? was 26803624, guessing it's just a +1
            case 31101000:
            case 22110024:
            case 22110014:
            case 21120006:
            case 21100007:
            case 21110027:
            case 21001009:
            case 21000004:
            case 5121013:
            case 1078:
            case 1079:
                return true;
            default:
                return false;

        }
    }

    private static int getDummyBulletItemIDForJob(int job, int subJob, int skillID) {
        if (job / 100 == 35)
            return 2333000;
        if (job / 10 == 53 || job == 501 || (job / 1000) == 0 && subJob == 2)
            return 2333001;
        if (JobConstants.isMercedes((short) job))
            return 2061010;
        if (JobConstants.isAngelicBuster(job))
            return 2333001;
        // TODO:
//        if ( !JobConstants.isPhantom((short) job)
//                || !is_useable_stealedskill(skillID)
//                || (result = get_vari_dummy_bullet_by_cane(skillID), result <= 0) )
//        {
//            result = 0;
//        }
        return 0;
    }

    public static boolean isKeydownSkillRectMoveXY(int skillID) {
        return skillID == 13111020 || skillID == 112111016;
    }

    public static boolean isKeydownAreaMovingSkill(int skillID) {
        return skillID == 27101202; // Pressure Void
    }

    public static int getLinkedSkillOfOriginal(int skillID) {
        switch (skillID) {
            case 1214: // Core Aura
                return 80001151; // Core Aura
            case 10000255: // Cygnus Blessing (Warrior)
                return 80000066; // Cygnus Blessing (Warrior)
            case 10000256: // Cygnus Blessing (Magician)
                return 80000067; // Cygnus Blessing (Magician)
            case 10000257: // Cygnus Blessing (Bowman)
                return 80000068; // Cygnus Blessing (Bowman)
            case 10000258: // Cygnus Blessing (Thief)
                return 80000069; // Cygnus Blessing (Thief)
            case 10000259: // Cygnus Blessing (Pirate)
                return 80000070; // Cygnus Blessing (Pirate)
            case 20000297: // Combo Kill Blessing
                return 80000370; // Combo Kill Blessing
            case 20010294: // Rune Persistence
                return 80000369; // Rune Persistence
            case 20021110: // Elven Blessing
                return 80001040; // Elven Blessing
            case 20030204: // Phantom Instinct
                return 80000002; // Phantom Instinct
            case 20040218: // Light Wash
                return 80000005; // Light Wash
            case 20050286: // Close Call
                return 80000169; // Close Call
            case 30000074: // Spirit of Freedom (Magician)
                return 80000333; // Spirit of Freedom (Magician)
            case 30000075: // Spirit of Freedom (Bowman)
                return 80000334; // Spirit of Freedom (Bowman)
            case 30000076: // Spirit of Freedom (Pirate)
                return 80000335; // Spirit of Freedom (Pirate)
            case 30000077: // Spirit of Freedom (Warrior)
                return 80000378; // Spirit of Freedom (Warrior)
            case 30010112: // Fury Unleashed
                return 80000001; // Fury Unleashed
            case 30010241: // Wild Rage
                return 80000050; // Wild Rage
            case 30020233: // Hybrid Logic
                return 80000047; // Hybrid Logic
            case 40010001: // Keen Edge
                return 80000003; // Keen Edge
            case 40020002: // Elementalism (Link Skill)
                return 80000004; // Elementalism
            case 50001214: // Knight's Watch
                return 80001140; // Knight's Watch
            case 60000222: // Iron Will
                return 80000006; // Iron Will
            case 60011219: // Terms and Conditions
                return 80001155; // Terms and Conditions
            case 60020218: // Unfair Advantage
                return 80000261; // Unfair Advantage
            case 100000271: // Rhinne's Blessing
                return 80000110; // Rhinne's Blessing
            case 110000800: // Focus Spirit
                return 80010006; // Focus Spirit
            case 140000292: // Judgment
                return 80000188; // Judgment
            case 150000017: // Tide of Battle
                return 80000268; // Tide of Battle
            case 150010241: // Solus
                return 80000514; // Solus
            case 160000001: // Bravado
                return 80000609; // Bravado
            case Adele.NOBLE_FIRE_ORIGIN: // Noble Fire
                return Adele.NOBLE_FIRE_LINKED; // Noble Fire
            case Lara.NATURE_FRIEND_ORIGIN:
                return Lara.NATURE_FRIEND_ORIGIN;
            case Kain.PRIOR_PREPARATION_ORIGIN: // Time to Prepare
                return Kain.PRIOR_PREPARATION_LINKED; // Time to Prepare

            case 252: // Invincible Belief (Hero)
                return 80002759; // Invincible Belief (Hero)
            case 253: // Invincible Belief (Paladin)
                return 80002760; // Invincible Belief (Paladin)
            case 254: // Invincible Belief (Dark Knight)
                return 80002761; // Invincible Belief (Dark Knight)
            case 255: // Empirical Knowledge (Fire Poison)
                return 80002763; // Empirical Knowledge (Fire Poison)
            case 256: // Empirical Knowledge (Ice Lightning)
                return 80002764; // Empirical Knowledge (Ice Lightning)
            case 257: // Empirical Knowledge (Bishop)
                return 80002765; // Empirical Knowledge (Bishop)
            case 258: // Adventurer's Curiosity (BowMaster)
                return 80002767; // Adventurer's Curiosity (BowMaster)
            case 259: // Adventurer's Curiosity (Marksman)
                return 80002768; // Adventurer's Curiosity (Marksman)
            case 260: // Adventurer's Curiosity (PathFinder)
                return 80002769; // Adventurer's Curiosity (PathFinder)
            case 261: // Thief's Cunning (Night Lord)
                return 80002771; // Thief's Cunning (Night Lord)
            case 262: // Thief's Cunning (Shadower)
                return 80002772; // Thief's Cunning (Shadower)
            case 263: // Thief's Cunning (Dual Blade)
                return 80002773; // Thief's Cunning (Dual Blade)
            case 264: // Pirate Blessing (Buccaneer)
                return 80002775; // Pirate Blessing (Buccaneer)
            case 265: // Pirate Blessing (Corsair)
                return 80002776; // Pirate Blessing (Corsair)
            case 110: // Pirate Blessing (Cannoneer)
                return 80000000; // Pirate Blessing (Cannoneer)
        }
        return 0;
    }

    public static int getOriginalOfLinkedSkill(int skillID) {
        switch (skillID) {
            case 80000001: // Fury Unleashed
                return 30010112; // Fury Unleashed
            case 80000002: // Phantom Instinct
                return 20030204; // Phantom Instinct
            case 80000003: // Keen Edge
                return 40010001; // Keen Edge
            case 80000004: // Elementalism
                return 40020002; // Elementalism (Link Skill)
            case 80000005: // Light Wash
                return 20040218; // Light Wash
            case 80000006: // Iron Will
                return 60000222; // Iron Will
            case 80000047: // Hybrid Logic
                return 30020233; // Hybrid Logic
            case 80000050: // Wild Rage
                return 30010241; // Wild Rage
            case 80000066: // Cygnus Blessing (Warrior)
                return 10000255; // Cygnus Blessing (Warrior)
            case 80000067: // Cygnus Blessing (Magician)
                return 10000256; // Cygnus Blessing (Magician)
            case 80000068: // Cygnus Blessing (Bowman)
                return 10000257; // Cygnus Blessing (Bowman)
            case 80000069: // Cygnus Blessing (Thief)
                return 10000258; // Cygnus Blessing (Thief)
            case 80000070: // Cygnus Blessing (Pirate)
                return 10000259; // Cygnus Blessing (Pirate)
            case 80000110: // Rhinne's Blessing
                return 100000271; // Rhinne's Blessing
            case 80000169: // Close Call
                return 20050286; // Close Call
            case 80000188: // Judgment
                return 140000292; // Judgment
            case 80000261: // Unfair Advantage
                return 60020218; // Unfair Advantage
            case 80000268: // Tide of Battle
                return 150000017; // Tide of Battle
            case 80000333: // Spirit of Freedom (Magician)
                return 30000074; // Spirit of Freedom (Magician)
            case 80000334: // Spirit of Freedom (Bowman)
                return 30000075; // Spirit of Freedom (Bowman)
            case 80000335: // Spirit of Freedom (Pirate)
                return 30000076; // Spirit of Freedom (Pirate)
            case 80000369: // Rune Persistence
                return 20010294; // Rune Persistence
            case 80000370: // Combo Kill Blessing
                return 20000297; // Combo Kill Blessing
            case 80000378: // Spirit of Freedom (Warrior)
                return 30000077; // Spirit of Freedom (Warrior)
            case 80000514: // Solus
                return 150010241; // Solus
            case 80001040: // Elven Blessing
                return 20021110; // Elven Blessing
            case 80001140: // Knight's Watch
                return 50001214; // Knight's Watch
            case 80001151: // Core Aura
                return 1214; // Core Aura
            case 80001155: // Terms and Conditions
                return 60011219; // Terms and Conditions
            case 80010006: // Focus Spirit
                return 110000800; // Focus Spirit
            case 80000609: // Bravado
                return 160000001; // Bravado
            case 80010343: // Guiding Stars
                return 180000001;
            case 80010473: // Guiding Stars
                return 180010001;
            case Adele.NOBLE_FIRE_LINKED:
                return Adele.NOBLE_FIRE_ORIGIN;

            case 80002759: // Invincible Belief (Hero)
                return 252; // Invincible Belief (Hero)
            case 80002760: // Invincible Belief (Paladin)
                return 253; // Invincible Belief (Paladin)
            case 80002761: // Invincible Belief (Dark Knight)
                return 254; // Invincible Belief (Dark Knight)
            case 80002763: // Empirical Knowledge (Fire Poison)
                return 255; // Empirical Knowledge (Fire Poison)
            case 80002764: // Empirical Knowledge (Ice Lightning)
                return 256; // Empirical Knowledge (Ice Lightning)
            case 80002765: // Empirical Knowledge (Bishop)
                return 257; // Empirical Knowledge (Bishop)
            case 80002767: // Adventurer's Curiosity (BowMaster)
                return 258; // Adventurer's Curiosity (BowMaster)
            case 80002768: // Adventurer's Curiosity (Marksman)
                return 259; // Adventurer's Curiosity (Marksman)
            case 80002769: // Adventurer's Curiosity (PathFinder)
                return 260; // Adventurer's Curiosity (PathFinder)
            case 80002771: // Thief's Cunning (Night Lord)
                return 261; // Thief's Cunning (Night Lord)
            case 80002772: // Thief's Cunning (Shadower)
                return 262; // Thief's Cunning (Shadower)
            case 80002773: // Thief's Cunning (Dual Blade)
                return 263; // Thief's Cunning (Dual Blade)
            case 80002775: // Pirate Blessing (Buccaneer)
                return 264; // Pirate Blessing (Buccaneer)
            case 80002776: // Pirate Blessing (Corsair)
                return 265; // Pirate Blessing (Corsair)
            case 80000000: // Pirate Blessing (Cannoneer)
                return 110; // Pirate Blessing (Cannoneer)

            case Lara.NATURE_FRIEND_LINKED:
                return Lara.NATURE_FRIEND_ORIGIN;
            case Kain.PRIOR_PREPARATION_LINKED: // Time to Prepare (Kain)
                return Kain.PRIOR_PREPARATION_ORIGIN; // Time to Prepare (Kain)

            default:
                return 0;
        }
    }

    public static int getLinkSkillByJob(short job) {
        if (JobConstants.isAngelicBuster(job)) { // Terms and Conditions
            return 80001155;
        } else if (JobConstants.isAran(job)) { // Combo Kill Blessing
            return 80000370;
        } else if (JobConstants.isArk(job)) { // Solus
            return 80000514;
        } else if (JobConstants.isBeastTamer(job)) { // Focus Spirit
            return 80010006;
        } else if (JobConstants.isCadena(job)) { // Unfair Advantage
            return 80000261;
        } else if (JobConstants.isDemonAvenger(job)) { // Wild Rage
            return 80000050;
        } else if (JobConstants.isDemonSlayer(job)) { // Fury Unleashed
            return 80000001;
        } else if (JobConstants.isShade(job)) { // Close Call
            return 80000169;
        } else if (JobConstants.isEvan(job)) { // Rune Persistence
            return 80000369;
        } else if (JobConstants.isHayato(job)) { // Keen Edge
            return 80000003;
        } else if (JobConstants.isIllium(job)) { // Tide of Battle
            return 80000268;
        } else if (JobConstants.isJett(job)) { // Core Aura
            return 80001151;
        } else if (JobConstants.isKaiser(job)) { // Iron Will
            return 80000006;
        } else if (JobConstants.isKanna(job)) { // Elementalism
            return 80000004;
        } else if (JobConstants.isKinesis(job)) { // Judgment
            return 80000188;
        } else if (JobConstants.isLuminous(job)) { // Light Wash
            return 80000005;
        } else if (JobConstants.isMercedes(job)) { // Elven Blessing
            return 80001040;
        } else if (JobConstants.isMihile(job)) { // Knight's Watch
            return 80001140;
        } else if (JobConstants.isPhantom(job)) { // Phantom Instinct
            return 80000002;
        } else if (JobConstants.isXenon(job)) { // Hybrid Logic
            return 80000047;
        } else if (JobConstants.isZero(job)) { // Rhinne's blessing
            return 80000110;
        } else if (JobConstants.isHoYoung(job)) { // Bravado
            return 80000609;
        } else if (JobConstants.isAdele(job)) { // Noble Fire
            return Adele.NOBLE_FIRE_LINKED;
        } else if (JobConstants.isLara(job)) {
            return Lara.NATURE_FRIEND_LINKED;
        } else if (JobConstants.isKain(job)) { // Time to Prepare
            return Kain.PRIOR_PREPARATION_LINKED;
        } else if (JobConstants.isKhali(job)) { // Innate Gift
            return 80003224;
        } else if (JobConstants.isMoXuan(job)) { // Qi Cultivation
            return 80011964;
        } else if (JobConstants.isLynn(job)) { // Focus Spirit
            return 80010006;
        } else if (JobConstants.isSiaAstelle(job)) { // Guiding Stars
            return 80010343;
        } else if (JobConstants.isErelLight(job)) { // Guiding Stars
            return 80010473;
        }

        // KoC (Cygnus Blessing)
        else if (JobConstants.isDawnWarrior(job)) {
            return 80000066;
        } else if (JobConstants.isBlazeWizard(job)) {
            return 80000067;
        } else if (JobConstants.isWindArcher(job)) {
            return 80000068;
        } else if (JobConstants.isNightWalker(job)) {
            return 80000069;
        } else if (JobConstants.isThunderBreaker(job)) {
            return 80000070;
        }

        // Resistance jobs (Spirit of Freedom)
        else if (JobConstants.isBlaster(job)) {
            return 80000378;
        } else if (JobConstants.isBattleMage(job)) {
            return 80000333;
        } else if (JobConstants.isWildHunter(job)) {
            return 80000334;
        } else if (JobConstants.isMechanic(job)) {
            return 80000335;
        }

        // Explorer
        else if (JobConstants.isHero(job)) {
            return 80002759;
        } else if (JobConstants.isPaladin(job)) {
            return 80002760;
        } else if (JobConstants.isDarkKnight(job)) {
            return 80002761;
        } else if (JobConstants.isFirePoison(job)) {
            return 80002763;
        } else if (JobConstants.isIceLightning(job)) {
            return 80002764;
        } else if (JobConstants.isBishop(job)) {
            return 80002765;
        } else if (JobConstants.isBowMaster(job)) {
            return 80002767;
        } else if (JobConstants.isMarksman(job)) {
            return 80002768;
        } else if (JobConstants.isPathFinder(job)) {
            return 80002769;
        } else if (JobConstants.isNightLord(job)) {
            return 80002771;
        } else if (JobConstants.isShadower(job)) {
            return 80002772;
        } else if (JobConstants.isDualBlade(job)) {
            return 80002773;
        } else if (JobConstants.isBuccaneer(job)) {
            return 80002775;
        } else if (JobConstants.isCorsair(job)) {
            return 80002776;
        } else if (JobConstants.isCannonShooter(job)) {
            return 80000000;
        }
        return 0;
    }

    public static boolean isStackingLinkSkill(int skillID) {
        return getStackLinkSkill(skillID) != 0;
    }

    public static int getMasterLevelForStackingLinkSkill(int skillID) {
        switch (getStackLinkSkill(skillID)) {
            case Job.INVINCIBLE_BELIEF: // Invincible Belief
            case Job.EMPIRICAL_KNOWLEDGE: // Empirical Knowledge
            case Job.ADVENTURERS_CURIOSITY: // Adventurer's Curiosity
            case Job.THIEFS_CUNNING: // Thiefs Cunning
            case Job.PIRATE_BLESSING: // Pirate Blessing
            case 80000055: // Cygnus Blessing
            case 80000329: // Spirit of Freedom
            case 80010486: // Guiding Stars (Shine)
                return 3; // v265 Skill.wz: each part goes to 3 (stacks 9 / 15 / 12)
        }
        return 0;
    }

    public static int getMasterLevelForStackingLinkSkillUsingStackId(int skillID) {
        switch (skillID) {
            case Job.INVINCIBLE_BELIEF: // Invincible Belief
            case Job.EMPIRICAL_KNOWLEDGE: // Empirical Knowledge
            case Job.ADVENTURERS_CURIOSITY: // Adventurer's Curiosity
            case Job.THIEFS_CUNNING: // Thiefs Cunning
            case Job.PIRATE_BLESSING: // Pirate Blessing
            case 80000055: // Cygnus Blessing
            case 80000329: // Spirit of Freedom
            case 80010486: // Guiding Stars (Shine)
                return 3;
        }
        return 0;
    }

    public static int getStackLinkSkillMaxLv(int skillId) {
        var size = getIndividualsByStackLink(skillId).length;
        if (size > 0) {
            return size * getMasterLevelForStackingLinkSkillUsingStackId(skillId);
        }
        return 3; // just some default val
    }

    public static int getStackLinkSkill(int skillID) {
        switch (skillID) {
            // Invincible Belief
            case 80002759:
            case 80002760:
            case 80002761:
                return Job.INVINCIBLE_BELIEF;

            // Empirical Knowledge
            case 80002763:
            case 80002764:
            case 80002765:
                return Job.EMPIRICAL_KNOWLEDGE;

            // Adventurer's Curiosity
            case 80002767:
            case 80002768:
            case 80002769:
                return Job.ADVENTURERS_CURIOSITY;

            // Thiefs Cunning
            case 80002771:
            case 80002772:
            case 80002773:
                return Job.THIEFS_CUNNING;

            // Pirate Blessing
            case 80000000:
            case 80002775:
            case 80002776:
                return Job.PIRATE_BLESSING;

            // Cygnus Blessing
            case 80000066:
            case 80000067:
            case 80000068:
            case 80000069:
            case 80000070:
                return 80000055;

            // Spirit of Freedom
            case 80000378:
            case 80000333:
            case 80000334:
            case 80000335:
                return 80000329;

            // Guiding Stars: Sia Astelle and Erel Light
            case 80010343:
            case 80010473:
                return 80010486;
        }
        return 0;
    }

    public static boolean isGlobalStackingLinkSkill(int skillID) {
        return getIndividualsByStackLink(skillID).length > 0;
    }

    public static int[] getIndividualsByStackLink(int skillID) {
        switch (skillID) {
            case Job.INVINCIBLE_BELIEF:
                return new int[]{80002759, 80002760, 80002761};
            case Job.EMPIRICAL_KNOWLEDGE:
                return new int[]{80002763, 80002764, 80002765};
            case Job.ADVENTURERS_CURIOSITY:
                return new int[]{80002767, 80002768, 80002769};
            case Job.THIEFS_CUNNING:
                return new int[]{80002771, 80002772, 80002773};
            case Job.PIRATE_BLESSING:
                return new int[]{80000000, 80002775, 80002776};
            case 80000055:
                return new int[]{80000066, 80000067, 80000068, 80000069, 80000070};
            case 80000329:
                return new int[]{80000378, 80000333, 80000334, 80000335};
            case 80010486:
                return new int[]{80010343, 80010473};
        }
        return new int[]{};
    }

    public static List<Integer> getOriginAndLinkByStackingLink(int skillID) {
        List<Integer> skills = new ArrayList<>();
        for (int skill : getIndividualsByStackLink(skillID)) {
            skills.add(skill);

            if (getOriginalOfLinkedSkill(skill) > 0) {
                skills.add(getOriginalOfLinkedSkill(skill));
            }
        }

        return skills;
    }

    public static int getLinkSkillLevelByCharLevel(short job, int level) {
        // Zero Custom Link Skill Change
        if (JobConstants.isZero(job)) {
            if (level >= ZERO_LINK_SKILL_5_LEVEL) {
                return 5;
            } else if (level >= ZERO_LINK_SKILL_4_LEVEL) {
                return 4;
            } else if (level >= ZERO_LINK_SKILL_3_LEVEL) {
                return 3;
            } else if (level >= ZERO_LINK_SKILL_2_LEVEL) {
                return 2;
            } else if (level >= ZERO_LINK_SKILL_1_LEVEL) {
                return 1;
            } else {
                return 0;
            }
        }

        int res = 0;
        if (level >= LINK_SKILL_3_LEVEL) {
            res = 3;
        } else if (level >= LINK_SKILL_2_LEVEL) {
            res = 2;
        } else if (level >= LINK_SKILL_1_LEVEL) {
            res = 1;
        }
        return res;
    }

    public static boolean isZeroAlphaSkill(int skillID) {
        return isZeroSkill(skillID) && skillID % 1000 / 100 == 2;
    }

    public static boolean isZeroBetaSkill(int skillID) {
        return isZeroSkill(skillID) && skillID % 1000 / 100 == 1;
    }

    public static boolean isLightmageSkill(int skillID) {
        int prefix = skillID / 10000;
        if (prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix / 100 == 27 || prefix == 2004;
    }

    public static boolean isLarknessDarkSkill(int skillID) {
        return skillID != 20041222 && isLightmageSkill(skillID) && (skillID % 1000 >= 200 && skillID % 1000 < 300);
    }

    public static boolean isLarknessLightSkill(int skillID) {
        return skillID == 20041226 // Spectral Light
                || skillID != 20041222 && isLightmageSkill(skillID) && (skillID % 1000 >= 100 && skillID % 1000 < 200);
    }

    public static boolean isEquilibriumSkill(int skillID) {
        return (skillID >= 20040219 && skillID <= 20040220) || (skillID % 1000 >= 300 && skillID % 1000 < 400);
    }

    public static int getAdvancedCountHyperSkill(int skillId) {
        switch (skillId) {
            case 4121013:
            case 4141000: // HEXA Quad Star
                return 4120051;
            case 5321012:
                return 5320051;
            default:
                return 0;
        }
    }

    public static int getAdvancedAttackCountHyperSkill(int skillId) {
        switch (skillId) {
            case 1120017: // Raging Blow
                return 1120051; // Raging Blow - Extra Strike
            case 1121008: // Raging Blow
                return 1120051; // Raging Blow - Extra Strike
            case 1221009: // Blast
                return 1220048; // Blast - Extra Strike
            case 1221011: // Heaven's Hammer
                return 1220050; // Heaven's Hammer - Extra Strike
            case 2121006: // Paralyze
                return 2120048; // Paralyze - Extra Strike
            case 2221006: // Chain Lightning
                return 2220048; // Chain Lightning - Extra Strike
            case 3121015: // Arrow Stream
                return 3120048; // Arrow Stream - Extra Strike
            case 3121020: // Hurricane
                return 3120051; // Hurricane - Split Attack
            case 3221017: // Piercing Arrow
                return 3220048; // Piercing Arrow - Extra Strike
            case 4221007: // Boomerang Stab
                return 4220048; // Boomerang Stab - Extra Strike
            case 4331000: // Bloody Storm
                return 4340045; // Bloody Storm - Extra Strike
            case 4341009: // Phantom Blow
                return 4340048; // Phantom Blow - Extra Strike
            case 5121007: // Octopunch
                return 5120048; // Octopunch - Extra Strike
            case 5121016: // Buccaneer Blast
                return 5120051; // Buccaneer Blast - Extra Strike
            case 5121017: // Double Blast
                return 5120051; // Buccaneer Blast - Extra Strike
            case 5121020: // Octopunch
                return 5120048; // Octopunch - Extra Strike
            case 5221016: // Brain Scrambler
                return 5220047; // Brain Scrambler - Extra Strike
            case 5321000: // Cannon Bazooka
                return 5320048; // Cannon Bazooka - Extra Strike
            case 5321004: // Support Monkey
                return 5320045; // Support Monkey - Extra Strike
            case 5721061: // Backup Beatdown
                return 5720045; // Backup Beatdown - Extra Strike
            case 5721064: // Planet Buster
                return 5720048; // Planet Buster - Extra Strike
            case 11121103: // Crescent Divide
                return 11120048; // Divide and Pierce - Extra Strike
            case 11121203: // Solar Pierce
                return 11120048; // Divide and Pierce - Extra Strike
            case 12000026: // Orbital Flame
                return 12120045; // Orbital Flame - Split Attack
            case 12100028: // Greater Orbital Flame
                return 12120045; // Orbital Flame - Split Attack
            case 12110028: // Grand Orbital Flame
                return 12120045; // Orbital Flame - Split Attack
            case 12120010: // Final Orbital Flame
                return 12120045; // Orbital Flame - Split Attack
            case 12120011: // Blazing Extinction
                return 12120046; // Blazing Extinction - Add Attack
            case 13121002: // Spiraling Vortex
                return 13120048; // Spiraling Vortex - Extra Strike
            case 14121002: // Quintuple Star
                return 14120045; // Quintuple Star - Critical Chance
            case 15111022: // Gale
                return 15120045; // Gale - Extra Strike
            case 15120003: // Typhoon
                return 15120045; // Gale - Extra Strike
            case 15121002: // Thunderbolt
                return 15120048; // Thunderbolt - Extra Strike
            case 21110020: // Final Blow
                return 21120047; // Final Blow - Bonus
            case 21111021: // Final Blow
                return 21120047; // Final Blow - Bonus
            case 21120006: // Combo Tempest
                return 21120049; // Combo Tempest - Bonus
            case 21120022: // Beyond Blade
                return 21120066; // Beyond Blade Barrage
            case 21121016: // Beyond Blade
                return 21120066; // Beyond Blade Barrage
            case 21121017: // Beyond Blade
                return 21120066; // Beyond Blade Barrage
            case 22140023: // Thunder Flash
                return 22170086; // Rolling Thunder
            case 25121005: // Spirit Claw
                return 25120148; // Spirit Claw - Extra Strike
            case 31121001: // Demon Impact
                return 31120050; // Demon Impact - Extra Strike
            case 35121016: // AP Salvo Plus
                return 35120051; // AP Salvo Plus - Extra Strike
            case 37110002: // Hammer Smash
                return 37120045; // Aftershock Punch
            case 37120001: // Shotgun Punch
                return 37120045; // Aftershock Punch
            case 41121001: // Shinsoku
                return 41120044; // Shinsoku - Extra Strike
            case 41121002: // Hitokiri Strike
                return 41120050; // Hitokiri Strike - Extra Strike
            case 41121018: // Sudden Strike
                return 41120048; // Sudden Strike - Extra Strike
            case 41121021: // Sudden Strike
                return 41120048; // Sudden Strike - Extra Strike
            case 42121000: // Vanquisher's Charm
                return 42120043; // Vanquisher's Charm - Extra Strike
            case 51120057: // Radiant Cross - Spread
                return 51120058; // Radiant Cross - Extra Strike
            case 51121007: // Four-Point Assault
                return 51120051; // Four-Point Assault - Extra Strike
            case 51121008: // Radiant Blast
                return 51120048; // Radiant Blast - Extra Strike
            case 51121009: // Radiant Cross
                return 51120058; // Radiant Cross - Extra Strike
            case 61121100: // Gigas Wave
                return 61120045; // Gigas Wave - Bonus Attack
            case 61121201: // Gigas Wave
                return 61120045; // Gigas Wave - Bonus Attack
            case 65121007: // Trinity
                return 65120051; // Trinity-Extra Strike
            case 65121008: // Trinity
                return 65120051; // Trinity-Extra Strike
            case 65121101: // Trinity
                return 65120051; // Trinity-Extra Strike
            case 112000003: // Furious Strikes
                return 112120044; // Furious Strikes - Extra Strike
            case 112101009: // Three-Point Pounce
                return 112120048; // Three-Point Pounce - Extra Strike
            case 112111004: // Baby Bombers
                return 112120050; // Formation Attack - Spread
            case 112120000: // Friend Launcher
                return 112120053; // Friend Launcher - Spread
            case 112120001: // Friend Launcher 2
                return 112120053; // Friend Launcher - Spread
            case 112120002: // Friend Launcher 3
                return 112120053; // Friend Launcher - Spread
            case 112120003: // Friend Launcher 4
                return 112120053; // Friend Launcher - Spread
            case 152001001: // Radiant Javelin
                return 152120032; // Javelin - Extra Strike
            case 152110004: // Winged Javelin
                return 152120032; // Javelin - Extra Strike
            case 152120001: // Radiant Javelin II
                return 152120032; // Javelin - Extra Strike
            case 152121004: // Longinus Spear
                return 152120035; // Longinus - Extra Strike
            case 152121005: // Crystal Skill - Deus
                return 152120038; // Deus - Extra Strike
            case 152121006: // Crystal Skill - Deus
                return 152120038; // Deus - Extra Strike
            case 400010070: // Fenrir Crash
                return 21120066; // Beyond Blade Barrage
            case 400011079: // Draco Surge
                return 61120045; // Gigas Wave - Bonus Attack
            case 400011080: // Draco Wave
                return 61120045; // Gigas Wave - Bonus Attack
            case 400011081: // Draco Wave
                return 61120045; // Gigas Wave - Bonus Attack
            case 400011082: // Draco Wave
                return 61120045; // Gigas Wave - Bonus Attack
            case 400051043: // True Spirit Claw
                return 25120148; // Spirit Claw - Extra Strike

            // HEXA (6th job)
            case 1141000: // HEXA Raging Blow
            case 1141001: // HEXA Raging Blow
                return 1120051; // Raging Blow - Extra Strike
            case 1241000: // HEXA Blast
                return 1220048; // Blast - Extra Strike
            case 1241007: // HEXA Heaven's Hammer
            case 1241010: // HEXA Heaven's Hammer
                return 1220050; // Heaven's Hammer - Extra Strike
            case 1241003: // HEXA Divine Charge
                return 1220058; // Divine Charge - Extra Strike
            case 2141000: // HEXA Flame Sweep
            case 2141001: // HEXA Flame Sweep
            case 2141002: // HEXA Flame Sweep
                return 2120048; // Flame Sweep - Extra Strike
            case 2241000: // HEXA Chain Lightning
            case 2241001: // HEXA Chain Lightning
                return 2220048; // Chain Lightning - Extra Strike
            case 3141002: // HEXA Arrow Stream
            case 3141003: // HEXA Arrow Stream
                return 3120048; // Arrow Stream - Extra Strike
            case 3141000: // HEXA Hurricane
            case 3141001: // HEXA Hurricane: Shootout Mode
                return 3120051; // Hurricane - Split Attack
            case 3241005: // HEXA Piercing Arrow
            case 3241006: // HEXA Empowered Piercing Arrow
            case 3241007: // HEXA Ultimate Piercing Arrow
            case 3241008: // HEXA Empowered Piercing Arrow
            case 3241009: // HEXA Ultimate Piercing Arrow
            case 3241010: // HEXA Piercing Arrow
            case 3241011: // HEXA Piercing Arrow
                return 3220048; // Piercing Arrow - Extra Strike
            case 3241000: // HEXA Snipe
            case 3241001: // HEXA Empowered Snipe
            case 3241002: // HEXA Empowered Snipe
            case 3241003: // Ultimate Snipe
            case 3241004: // Ultimate Snipe
                return 3220051; // Snipe - Extra Strike
            case 4141001: // HEXA Quad Star
                return 4120051; // Quad Star - Extra Strike
            case 4241011: // HEXA Cruel Stab
                return 4220048; // Cruel Stab - Extra Strike
            case 5141000: // HEXA Octopunch
            case 5141002: // Super Octopunch
            case 5141003: // Super Octopunch
                return 5120048; // Octopunch - Extra Strike
            case 5141015: // HEXA Hook Bomber
                return 5120051; // Hook Bomber - Extra Strike
            case 5241005: // HEXA Brain Scrambler
            case 5241006: // Condemnation
                return 5220047; // Brain Scrambler - Extra Strike
            case 5341009: // HEXA Support Monkey
                return 5320045; // Support Monkey - Extra Strike
            case 5341001: // HEXA Cannon Bazooka
                return 5320048; // Cannon Bazooka - Extra Strike
            case 5341000: // HEXA Cannon Barrage
                return 5320051; // Cannon Barrage - Extra Strike
            case 12141000: // HEXA Orbital Flame
            case 12141001: // HEXA Orbital Flame
            case 12141002: // HEXA Orbital Flame
            case 12141003: // HEXA Orbital Flame
            case 12141004: // HEXA Orbital Flame
            case 12141005: // HEXA Orbital Flame
            case 12141012: // HEXA Phoenix Drive
            case 12141014: // HEXA Phoenix Drive
                return 12120045; // Orbital Flame - Split Attack
            case 12141008: // HEXA Blazing Extinction
            case 12141009: // HEXA Blazing Extinction
                return 12120046; // Blazing Extinction - Add Attack
            case 13141004: // HEXA Fairy Spiral
                return 13120048; // Fairy Spiral - Extra Strike
            case 15141006: // HEXA Typhoon
                return 15120045; // Gale - Extra Strike
            case 15141003: // HEXA Thunderbolt
            case 15141004: // HEXA Thunderbolt
                return 15120048; // Thunderbolt - Extra Strike
            case 21141000: // HEXA Beyond Blade
            case 21141001: // HEXA Beyond Blade
            case 21141002: // HEXA Beyond Blade
                return 21120066; // Beyond Blade Barrage
            case 25141000: // HEXA Spirit Claw
                return 25120148; // Spirit Claw - Extra Strike
            case 31141000: // HEXA Demon Impact
            case 31141001: // HEXA Demon Impact: Demon Chain
                return 31120050; // Demon Impact - Extra Strike
            case 35141001: // HEXA AP Salvo Plus
                return 35120051; // AP Salvo Plus - Extra Strike
            case 37141009: // HEXA Hammer Smash
            case 37141010: // HEXA Hammer Smash
            case 37141011: // HEXA Shotgun Punch
                return 37120045; // Punching Extra Strike
            case 41141004: // HEXA Shinsoku
                return 41120044; // Shinsoku - Extra Strike
            case 41141007: // HEXA Hitokiri Strike
                return 41120050; // Hitokiri Strike - Extra Strike
            case 42141004: // HEXA Vanquisher's Charm
                return 42120043; // Vanquisher's Charm - Extra Strike
            case 51141000: // HEXA Radiant Cross
            case 51141001: // HEXA Radiant Cross - Assault
                return 51120058; // Radiant Cross - Extra Strike
            case 61141000: // HEXA Gigas Wave
            case 61141001: // HEXA Gigas Wave
                return 61120045; // Gigas Wave - Bonus Attack
            case 65141000: // HEXA Trinity
                return 65120051; // Trinity-Extra Strike
            case 142140007: // HEXA Kinetic Combo
                return 142120041; // Kinetic Combo - Extra Strike
            case 152141000: // HEXA Radiant Javelin
            case 152141002: // HEXA Radiant Enchanted Javelin
            case 152141004: // HEXA Winged Javelin
            case 152141005: // HEXA Winged Enchanted Javelin
                return 152120032; // Javelin - Extra Strike
            case 152141014: // HEXA Longinus Spear
                return 152120035; // Longinus - Extra Strike
            case 152141012: // HEXA Crystal Skill - Deus
            case 152141013: // HEXA Crystal Skill - Deus
                return 152120038; // Deus - Extra Strike
            case 154141001: // HEXA Arts: Crescentum
            case 154141002: // HEXA Arts: Crescentum
                return 154120039; // Arts - Extra Strike
            case 172141000: // HEXA Strike
                return 172120033; // Strike - Extra Strike
            case 172141002: // HEXA Sneak Attack
                return 172120038; // Sneak Attack - Extra Strike
            case 182141000: // SHINE Ray
                return 182120032; // Marking - Extra Strike

            default:
                return isDelugeSkill(skillId)
                        || isBurstAttackingSkill(skillId)
                        || isTorrentSkill(skillId)
                        || isBurstSkill(skillId) ? 3320030 : 0;
        }
    }

    public static boolean isKinesisPsychicLockSkill(int skillId) {
        // v271 (Kinesis Redux) has no psychic-lock attack writer: no attack carries the per-mob lock ints
        return false;
    }

    public static boolean isKinesisPsychicAreaSkill(int skillId) {
        // v271 has no psychic-area attack writer either
        return false;
    }

    public static boolean isKinesisPsychicLockSkillExtraEncode(int skillId) {
        return false;
    }

    public static int getActualSkillIDfromSkillID(int skillID) {
        switch (skillID) {
            case 101120206: //Zero - Severe Storm Break (Tile)
                return 101120204; //Zero - Adv Storm Break

            case 4221016: //Shadower - Assassinate 2
                return 4221014; //Shadower - Assassinate 1

            case 41121020: //Hayato - Tornado Blade-Battoujutsu Link
                return 41121017; //Tornado Blade

            case 41121021: //Hayato - Sudden Strike-Battoujutsu Link
                return 41121018; //Sudden Strike

            case 5121017: //Bucc - Double Blast
                return 5121016; //Bucc - Buccaneer Blast

            case 5101014: //Bucc - Energy Vortex
                return 5101012; //Bucc - Tornado Uppercut

            case 5121020: //Bucc - Octopunch (Max Charge)
                return 5121007; //Bucc - Octopunch

            case 5111013: //Bucc - Hedgehog Buster
                return 5111002; //Bucc - Energy Burst

            case 5111015: //Bucc - Static Thumper
                return 5111012; //Bucc - Static Thumper

            case 31011004: //DA - Exceed Double Slash 2
            case 31011005: //DA - Exceed Double Slash 3
            case 31011006: //DA - Exceed Double Slash 4
            case 31011007: //DA - Exceed Double Slash Purple
                return 31011000; //DA - Exceed Double Slash 1

            case 31201007: //DA - Exceed Demon Strike 2
            case 31201008: //DA - Exceed Demon Strike 3
            case 31201009: //DA - Exceed Demon Strike 4
            case 31201010: //DA - Exceed Demon Strike Purple
                return 31201000; //DA - Exceed Demon Strike 1

            case 31211007: //DA - Exceed Lunar Slash 2
            case 31211008: //DA - Exceed Lunar Slash 3
            case 31211009: //DA - Exceed Lunar Slash 4
            case 31211010: //DA - Exceed Lunar Slash Purple
                return 31211000; //DA - Exceed Lunar Slash 1

            case 31221009: //DA - Exceed Execution 2
            case 31221010: //DA - Exceed Execution 3
            case 31221011: //DA - Exceed Execution 4
            case 31221012: //DA - Exceed Execution Purple
                return 31221000; //DA - Exceed Execution 1

            case 61120219: //Kaiser - Dragon Slash (Final Form)
                return 61001000; //Kaiser - Dragon Slash 1

            case 61111215: //Kaiser - Flame Surge (Final Form)
                return 61001101; //Kaiser - Flame Surge

            case 61111216: //Kaiser - Impact Wave (Final Form)
                return 61101100; //Kaiser - Impact Wave

            case 61111217: //Kaiser - Piercing Blaze (Final Form)
                return 61101101; //Kaiser - Piercing Blaze

            case 61111111: //Kaiser - Wing Beat (Final Form)
                return 61111100; //Kaiser - Wing Beat

            case 61111219: //Kaiser - Pressure Chain (Final Form)
                return 61111101; //Kaiser - Pressure Chain

            case 61121201: //Kaiser - Gigas Wave (Final Form)
                return 61121100; //Kaiser - Gigas Wave

            case 61121222: //Kaiser - Inferno Breath (Final Form)
                return 61121105; //Kaiser - Inferno Breath

            case 61121203: //Kaiser - Dragon Barrage (Final Form)
                return 61121102; //Kaiser - Dragon Barrage

            case 61121221: //Kaiser - Blade Burst (Final Form)
                return 61121104; //Kaiser - Blade Burst

            case 14101021: //NW - Quint. Throw Finisher
                return 14101020; //NW - Quint. Throw

            case 14111021: //NW - Quad Throw Finisher
                return 14111020; //NW - Quad Throw

            case 14121002: //NW - Triple Throw Finisher
                return 14121001; //NW - Triple Throw

            case STAGGERING_STRIKES:
                return STUNNING_STRIKES;

            case SMASH_WAVE_COMBO:
                return SMASH_WAVE;

            case FINAL_BLOW_COMBO:
            case FINAL_BLOW_SMASH_SWING_COMBO:
                return FINAL_BLOW;

            case SOUL_SEEKER_ATOM:
                return SOUL_SEEKER;

            case 65101006: //AB - Lovely Sting Explosion
                return LOVELY_STING;

            case 65121007:
            case 65121008:
                return TRINITY;

            case WindArcher.HOWLING_GALE_2:
                return WindArcher.HOWLING_GALE_1;

            default:
                return skillID;
        }
    }

    public static int getKaiserGaugeIncrementBySkill(int skillID) {
        HashMap<Integer, Integer> hashMapIncrement = new HashMap<>();
        hashMapIncrement.put(DRAGON_SLASH_1, 1);
        hashMapIncrement.put(DRAGON_SLASH_2, 3);
        hashMapIncrement.put(DRAGON_SLASH_3, 4);
        hashMapIncrement.put(DRAGON_SLASH_1_FINAL_FORM, 1);

        hashMapIncrement.put(Kaiser.FLAME_SURGE, 2);
        hashMapIncrement.put(FLAME_SURGE_FINAL_FORM, 2);

        hashMapIncrement.put(IMPACT_WAVE, 5);
        hashMapIncrement.put(IMPACT_WAVE_FINAL_FORM, 0);

        hashMapIncrement.put(PIERCING_BLAZE, 5);
        hashMapIncrement.put(PIERCING_BLAZE_FINAL_FORM, 0);

        hashMapIncrement.put(WING_BEAT, 2);
        hashMapIncrement.put(WING_BEAT_FINAL_FORM, 1);

        hashMapIncrement.put(PRESSURE_CHAIN, 8);
        hashMapIncrement.put(PRESSURE_CHAIN_FINAL_FORM, 0);

        hashMapIncrement.put(GIGA_WAVE, 8);
        hashMapIncrement.put(61141000, 8); // HEXA Gigas Wave
        hashMapIncrement.put(GIGA_WAVE_FINAL_FORM, 0);

        hashMapIncrement.put(INFERNO_BREATH, 14);
        hashMapIncrement.put(61141009, 14); // HEXA Inferno Breath
        hashMapIncrement.put(61141011, 14); // HEXA Enhanced Inferno Breath
        hashMapIncrement.put(INFERNO_BREATH_FINAL_FORM, 0);

        hashMapIncrement.put(DRAGON_BARRAGE, 6);
        hashMapIncrement.put(DRAGON_BARRAGE_FINAL_FORM, 0);

        hashMapIncrement.put(BLADE_BURST, 6);
        hashMapIncrement.put(61141002, 6); // HEXA Blade Burst
        hashMapIncrement.put(BLADE_BURST_FINAL_FORM, 0);

        hashMapIncrement.put(TEMPEST_BLADES_FIVE, 15);
        hashMapIncrement.put(TEMPEST_BLADES_FIVE_FF, 0);

        hashMapIncrement.put(TEMPEST_BLADES_THREE, 15);
        hashMapIncrement.put(TEMPEST_BLADES_THREE_FF, 0);

        return hashMapIncrement.getOrDefault(skillID, 0);
    }

    public static boolean isEvanFusionSkill(int skillID) {
        switch (skillID) {
            case Evan.WIND_FLASH:
            case Evan.WIND_FLASH_2:
            case Evan.WIND_FLASH_3:
            case Evan.THUNDER_FLASH:
            case 22140015:
            case 22140024:
            case Evan.THUNDER_FLASH_2:
            case 22170065:
            case 22170066:
            case 22170067:
            case 22170094:
            case 22201004: // HEXA Thunder Flash
            case 22201005: // HEXA Wind Flash
            case 22200008: // HEXA Earth Dive
            case 22200009: // HEXA Thunder Dive
            case 22200012: // HEXA Earth Breath
            case 22200013: // HEXA Earth Breath
            case 22200014: // HEXA Wind Breath
                return true;
            default:
                return false;
        }
    }

    public static boolean isShikigamiHauntingSkill(int skillID) {
        switch (skillID) {
            case 80001850:
            case 42001000:
            case 42001005:
            case 42001006:
            case 40021185:
            case 80011067:
                return true;
            default:
                return false;
        }
    }

    public static boolean isStealableSkill(int skillID) {
        // TODO
        return false;
    }

    public static int getLinkedSkill(int skillID) {
        switch (skillID) {
            case Zero.STORM_BREAK_INIT:
                return Zero.STORM_BREAK;
            case Zero.ADV_STORM_BREAK_SHOCK_INIT:
                return Zero.ADV_STORM_BREAK;
        }
        return skillID;
    }

    public static boolean isPassiveSkill_NoPsdSkillsCheck(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        return si != null && si.isPsd() || SkillConstants.isPsd(skillId);
    }

    private static boolean isPsd(int skillId) {
        // for (mostly old) skills that aren't specified as passives in wz
        return skillId == 3000001 || SkillConstants.isMultilateral(skillId) ||
                skillId == FirePoison.BURNING_MAGIC ||
                skillId == 20010294 || skillId == 80000369 || // Evan Link Skills
                isBasicStatUpSkill(skillId);
    }

    /** v265 Maple Warrior and its class copies: a passive "AP-invested stats +basicStatUp%", the cast is only a visual */
    public static boolean isBasicStatUpSkill(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        return si != null && si.getValue(SkillStat.basicStatUp, 1) > 0;
    }

    public static boolean isPassiveSkill(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);

        switch (skillId) {
            case 80000299:  // Crisis H Ring
            case 80000300:  // Crisis M Ring
            case 80000301:  // Crisis HM Ring
            case 80000302:  // Clean Stance Ring
            case 80000303:  // Clean Defense Ring
            case 80000304:  // Tower Boost Ring

            case Mechanic.ROBOT_MASTERY:
                return true;
        }

        return SkillConstants.isPsd(skillId) || (si != null && si.isPsd() && si.getPsdSkills().size() == 0);
    }

    public static boolean isHyperstatSkill(int skillID) {
        return HYPER_STAT_SKILLS.contains(skillID);
    }

    public static int getTotalHyperStatSpByLevel(short curLevel) {
        int sp = 0;
        for (short level = 140; level <= curLevel; level++) {
            sp += getHyperStatSpByLv(level);
        }
        return sp;
    }

    public static int getHyperStatSpByLv(short level) {
        return 3 + ((level - 140) / 10);
    }

    public static int getNeededSpForHyperStatSkill(int lv) {
        switch (lv) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 8;
            case 5:
                return 10;
            case 6:
                return 15;
            case 7:
                return 20;
            case 8:
                return 25;
            case 9:
                return 30;
            case 10:
                return 35;
            case 11:
                return 50;
            case 12:
                return 65;
            case 13:
                return 80;
            case 14:
                return 95;
            case 15:
                return 110;
            default:
                return 0;
        }
    }

    public static int getTotalNeededSpForHyperStatSkill(int lv) {
        switch (lv) {
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 7;
            case 4:
                return 15;
            case 5:
                return 25;
            case 6:
                return 40;
            case 7:
                return 60;
            case 8:
                return 85;
            case 9:
                return 115;
            case 10:
                return 150;
            case 11:
                return 200;
            case 12:
                return 265;
            case 13:
                return 345;
            case 14:
                return 440;
            case 15:
                return 550;
            default:
                return 0;
        }
    }

    public static boolean isUnregisteredSkill(int skillID) {
        int prefix = skillID / 10000;
        if (prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix != 9500 && skillID / 10000000 == 9;
    }

    public static boolean isHomeTeleportSkill(int skillId) {
        switch (skillId) {
            // Adventurers
            case Warrior.MAPLE_RETURN: // All Adventurers
            case Pathfinder.RETURN_TO_PARTEM: // Pathfinder
            case BladeMaster.RETURN: // BladeMaster

                // Cygnus Knights
            case DawnWarrior.IMPERIAL_RECALL: // All Cygnus Knights
            case Mihile.IMPERIAL_RECALL:

                // Legends
            case Aran.RETURN_TO_RIEN:
            case Evan.BACK_TO_NATURE:
            case Phantom.TO_THE_SKIES:

                // Resistance
            case Demon.SECRET_ASSEMBLY:  // All Resistance
            case Xenon.PROMESSA_ESCAPE:

                // Sengoku
            case Hayato.RETURN_OF_THE_FIVE_PLANETS:
            case Kanna.RETURN_OF_THE_FIVE_PLANETS:

                // Nova
            case Kaiser.GUARDIANS_RETURN:
            case AngelicBuster.DAY_DREAMER:
            case Cadena.BACK_TO_HQ:

                // Lef
            case Illium.SHELTER_RETURN:
            case Adele.ARTISTIC_RECALL:

                // Others
            case BeastTamer.HOMEWARD_BOUND:
            case Kinesis.RETURN_KINESIS:
            case Zero.TEMPLE_RECALL:

                // Anima
            case HoYoung.BLUE_CLOUD_VALLEY_RETURN:
            case Ren.RETURN_TO_SERPENT_HEARTWATERS:

                // Jianghu
            case MoXuan.RETURN_TO_XUANSHAN:

                // Elven Blessing (Link Skill)
            case 80001040:
                return true;
            default:
                return false;
        }
    }

    public static boolean isArmorPiercingSkill(int skillId) {
        switch (skillId) {
            case 3120017:
            case 95001000:
            case 3120008:
            case 3100001:
            case 3100010:
                return false;

            default:
                return true;
        }
    }

    public static int getBaseSpByLevel(short level) {
        return level > 140 ? 0
                : level > 130 ? 6
                : level > 120 ? 5
                : level > 110 ? 4
                : 3;
    }

    /**
     * The levels that give a passive Hyper Skill point: 9, one for each of a class's 9 passive
     * hyper skills (Skill.wz: required levels 140 to 190), every 15 levels from 140 and the last at 255. All are
     * multiples of 5, as the client's Hyper Skill window asks per 5 levels ({@link #getHyperPassiveSkillSpByLv(int)}).
     * Points are per class: the class's learned hyper skills use them, so a rebirth into the same class gets none.
     */
    public static final short[] PASSIVE_HYPER_SP_LEVELS = {140, 155, 170, 185, 200, 215, 230, 245, 255};

    public static int getTotalPassiveHyperSpByLevel(short level) {
        int sp = 0;
        for (short l : PASSIVE_HYPER_SP_LEVELS) {
            if (level >= l) {
                sp++;
            }
        }
        return sp;
    }

    public static int getTotalActiveHyperSpByLevel(short level) {
        return level >= ACTIVE_HYPER_SKILL_3 ? 3 :
                level >= ACTIVE_HYPER_SKILL_2 ? 2 :
                level >= ACTIVE_HYPER_SKILL_1 ? 1 :
                    0;
    }

    public static boolean isGuildSkill(int skillID) {
        int prefix = skillID / 10000;
        if (prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix == 9100;
    }

    public static boolean isGuildContentSkill(int skillID) {
        return (skillID >= GUILD_SKILL_BASE && skillID <= GUILD_SKILL_END) || (skillID >= GUILD_SKILL_BASE_2 && skillID <= GUILD_SKILL_END_2);
    }

    public static boolean isGuildNoblesseSkill(int skillID) {
        return skillID >= GUILD_NOBLESSE_BASE && skillID <= GUILD_NOBLESSE_END;
    }

    public static boolean isNoDisposeSkill(int skillID) {
        switch (skillID) {
            case Zero.CHRONO_BREAK:

            case MAGNUM_PUNCH:
            case DOUBLE_BLAST:
            case REVOLVING_BLAST:
            case REVOLVING_CANNON_MASTERY:
            case REVOLVING_CANNON_1:
            case REVOLVING_CANNON:
            case BOBBING:
            case WEAVING:
            case HAMMER_SMASH:
                return true;
        }

        return false;
    }

    /** v271 right-click toggles sent as SKILL_ACTION_TOGGLE (client list 0x140eccae0). */
    private static final java.util.Set<Integer> ACTION_TOGGLE_SKILLS = java.util.Set.of(
            1100013, 1210016, 2121052, 2141010, 2211011, 2241007, 2311001, 2341002, 3001007, 3011005, 3111013, 3141000,
            3141001, 3141002, 3141003, 3141004, 3301003, 4121054, 4141500, 4221018, 4341054, 4361002, 4361003, 5341014,
            11001025, 11100031, 14001026, 14121052, 14141018, 15100027, 15101021, 15140008, 15141003, 15141004, 20040217,
            20040219, 21111030, 23141007, 24141500, 31120046, 32001016, 33111002, 35101002, 35101005, 35141003, 35141004,
            35141005, 36141000, 36141500, 42001003, 42111001, 42111005, 42121003, 64121001, 64141013, 65121054, 65141500,
            151100006, 162111000, 172111008, 400001018, 400001036, 400001064, 400011001, 400011002, 400011015, 400011055,
            400011098, 400011100, 400011116, 400011118, 400011121, 400011131, 400021000, 400031003, 400041032, 400041035,
            400051008, 400051018, 400051042, 500001008, 500001068, 500001070);
    /** The id the client keys a toggle under (0x1453753f0): upgraded versions share their base's toggle. */
    private static final java.util.Map<Integer, Integer> ACTION_TOGGLE_BASE = java.util.Map.ofEntries(
            java.util.Map.entry(2121052, 2141010), java.util.Map.entry(2241007, 2211011),
            java.util.Map.entry(3141001, 3141000), java.util.Map.entry(3141003, 3141002),
            java.util.Map.entry(3141004, 3111013), java.util.Map.entry(4361002, 4341054),
            java.util.Map.entry(14141018, 14121052), java.util.Map.entry(15141004, 15141003),
            java.util.Map.entry(35141003, 35101002), java.util.Map.entry(35141005, 35141004),
            java.util.Map.entry(64141013, 64121001), java.util.Map.entry(400001064, 400001036),
            java.util.Map.entry(400011002, 400011001), java.util.Map.entry(400011100, 400011098));

    public static boolean isActionToggleSkill(int skillId) {
        return ACTION_TOGGLE_SKILLS.contains(skillId);
    }

    public static int getActionToggleBase(int skillId) {
        return ACTION_TOGGLE_BASE.getOrDefault(skillId, skillId);
    }

    public static boolean isNoExtraSkill(int skillID) {
        return skillID == HYPER_MAGNUM_PUNCH || skillID == RuneStone.LIBERATE_THE_RUNE_OF_IGNITION || skillID == Cadena.MUSCLE_MEMORY_FINALE
                || skillID == HoYoung.THREE_PATHS_APPARITION_ATTACK_1 || skillID == HoYoung.THREE_PATHS_APPARITION_ATTACK_2
                || skillID == HoYoung.THREE_PATHS_APPARITION_ATTACK_3;
    }

    public static boolean isUserCloneSummon(int skillID) {
        switch (skillID) {
            case 14111024:
            case 14121054:
            case 14121055:
            case 14121056:
            case 131001017:
            case 131002017:
            case 131003017:
            case 400011005:
            case 400031007:
            case 400031008:
            case 400031009:
            case 400041095:
            case 500061004:
                return true;
        }
        return false;
    }

    public static boolean isStateUsingSummon(int skillId) {
        switch (skillId) {
            case Illium.DEPLOY_CRYSTAL:
            case NightWalker.SHADOW_SLIDE:
            case NightWalker.HEXA_SHADOW_SLIDE:
            case MIGHTY_MASCOT:
            case ABSORBING_VORTEX:

                return true;
            default:
                return false;
        }
    }

    public static boolean isDivineEchoMimicSkills(int skillId) {
        switch (skillId) {
            case Paladin.DIVINE_CHARGE:
            case Paladin.DIVINE_MARK:
            case Paladin.BLAST:
            case Paladin.HEAVENS_HAMMER:
            case Paladin.SMITE_SHIELD:

            case Paladin.NOBLE_DEMAND:
            case Paladin.MAGIC_CRASH_PALLY:

            case Paladin.WEAPON_BOOSTER_PAGE:
            case Paladin.ELEMENTAL_FORCE:
            case Paladin.SACROSANCTITY:
            case Paladin.HP_RECOVERY:
                return true;

            default:
                return false;
        }
    }

    public static boolean isSuborbitalStrike(int skillID) {
        return skillID >= 400051028 && skillID <= 400051032;
    }

    public static boolean isSummonJaguarSkill(int skillID) {
        return skillID >= 33001007 && skillID <= 33001015;
    }

    public static boolean isShadowAssault(int skillID) {
        return skillID >= 400041002 && skillID <= 400041005
                || skillID == 500061025 || skillID == 500061026; // HEXA Shadow Assault
    }

    public static boolean isMatching(int rootId, int job) {
        boolean matchingStart = job / 100 == rootId / 100;
        boolean matching = matchingStart;
        if (matchingStart && rootId % 100 != 0) {
            // job path must match
            if (rootId == 301) {
                matching = JobConstants.isPathFinder((short) job);
            } else {
                int path = (rootId % 100) / 10;
                matching = path == (job % 100) / 10 || path == 0;
            }
        }
        return matching;
    }

    // Used for Skills that are leveled through a different skill
    public static int[] getAutoLevelSkillsBySkillID(int skillID) {
        switch (skillID) {
            case Illium.RADIANT_JAVELIN:
                return new int[]{Illium.RADIANT_ORB};
            case Illium.RADIANT_JAVELIN_II:
                return new int[]{Illium.RADIANT_ORB_II};

            case Evan.DRAGON_FLASH:
                return new int[]{Evan.WIND_FLASH, Evan.RETURN_FLASH};
            case Evan.DRAGON_DIVE:
                return new int[]{Evan.THUNDER_CIRCLE, Evan.RETURN_DIVE};

            case HoYoung.EARTH_BREAK_REAL:
                return new int[]{HoYoung.EARTH_BREAK_FAKE};

            case Kain.SCATTERING_SHOT:
                return new int[]{Kain.SCATTERING_SHOT_POSSESS};
            case Kain.STRIKE_ARROW_2_PASSIVE:
                return new int[]{Kain.STRIKE_ARROW_2_POSSESS};
            case Kain.SHAFT_BREAK:
                return new int[]{Kain.SHAFT_BREAK_PASSIVE};
            case Kain.FALLING_DUST:
                return new int[]{Kain.FALLING_DUST_PASSIVE};

            case Aran.ADRENALINE_RUSH:
                return new int[]{Aran.ADRENALINE_RUSH_ACTIVATE};

            default:
                return new int[]{};
        }
    }

    // is_skill_from_item(signed int nSkillID)
    public static boolean isSkillFromItem(int skillID) {
        switch (skillID) {
            case 80011123: // New Destiny
            case 80011247: // Dawn Shield
            case 80011248: // Dawn Shield
            case 80011249: // Divine Guardian
            case 80011250: // Divine Shield
            case 80011251: // Divine Brilliance
            case 80011261: // Monolith
            case 80011295: // Scouter
            case 80011346: // Ribbit Ring
            case 80011347: // Krrr Ring
            case 80011348: // Rawr Ring
            case 80011349: // Pew Pew Ring
            case 80011475: // Elunarium Power (ATT & M. ATT)
            case 80011476: // Elunarium Power (Skill EXP)
            case 80011477: // Elunarium Power (Boss Damage)
            case 80011478: // Elunarium Power (Ignore Enemy DEF)
            case 80011479: // Elunarium Power (Crit Rate)
            case 80011480: // Elunarium Power (Crit Damage)
            case 80011481: // Elunarium Power (Status Resistance)
            case 80011482: // Elunarium Power (All Stats)
            case 80011492: // Firestarter Ring
            case 80001768: // Rope Lift
            case 80001705: // Rope Lift
            case 80001941: // Scouter
            case 80010040: // Altered Fate
                return true;
        }
        // Tower of Oz skill rings
        return (skillID >= 80001455 && skillID <= 80001479);
    }

    /**
     * Passive Hyper Skill points a level gives, for the client's Hyper Skill window (it adds these up itself through
     * the instance table request): the same levels the server counts ({@link #PASSIVE_HYPER_SP_LEVELS}).
     */
    public static int getHyperPassiveSkillSpByLv(int level) {
        for (short l : PASSIVE_HYPER_SP_LEVELS) {
            if (level == l) {
                return 1;
            }
        }
        return 0;
    }

    public static int getHyperActiveSkillSpByLv(int level) {
        return level == 140 || level == 160 || level == 190 ? 1 : 0;
    }

    public static boolean isSomeShootObjectSkillsForRemote(int skillId) {
        switch (skillId) {
            case 80002691:	// Ancient Hero's Orb
            case 80002832:	// Aquastallion Belly Missile
            case 80002834:	// Aquastallion Net Missile
            case 80012123:  // Ride Aquastallion
            case 112111004: // Baby Bombers
            case 152001002:	// Radiant Orb
            case 152120003:	// Radiant Orb II
            case 152121004:	// Longinus Spear
            case 400011004:	// Spear of Darkness
            case 400021004:	// Orbital Inferno
            case 400021009:	// Psychic Tornado
            case 400021010:	// Psychic Tornado
            case 400021011:	// Psychic Tornado
            case 400021028:	// Poison Nova
            case 400021047:	// Altar of Annihilation
            case 400021048:	// Ultimate - Mind Over Matter
            case 400021064:	// Templar Knight
            case 400021065:	// Templar Knight
            case 400021078:	// Liberated Spirit Circle
            case 400021080:	// Liberated Spirit Circle
            case 400031048: // Relic Unbound (Deluge)
            case 400041016:	// Lucky Seven (Spread Throw)
            case 400041017:	// Triple Throw (Spread Throw)
            case 400041018:	// Quad Star (Spread Throw)
            case 400041020:	// Shurrikane
            case 400041034:	// Apocalypse Cannon
            case 400051003:	// Meltdown
            case 400051008:	// Cannon of Mass Destruction
            case 400051016:	// Shark Torpedo

                //
            case DRACO_SURGE_SHOOTOBJ:
            case DRACO_SURGE_SHOOTOBJ_FF:
            case Bishop.PEACEMAKER_EXPLOSION:
            case Bishop.PEACEMAKER_TRAVEL:
            case BladeMaster.BLADE_TORNADO_SHOOT_OBJECT:
            case BladeMaster.HAUNTED_EDGE_ASURA_WIND:

                //
            case Luminous.LIBERATION_ORB_LIGHT_ZONE:

                return true;
        }
        return false;
    }

    public static boolean isArkSkill1(int skillID) {
        return JobConstants.isArk((short) (skillID / 10000));
    }

    public static boolean isArkSkill2(int skillID) {
        switch (skillID) {
            case 155001100:
            case 155120000:
                return true;
            default:
                return false;
        }
    }

    public static boolean isArkForceAtomAttack(int skillID) {
        switch (skillID) {
            case Ark.BASIC_CHARGE_DRIVE_ATOM:
            case Ark.SCARLET_CHARGE_DRIVE_ATOM:
            case Ark.GUST_CHARGE_DRIVE_ATOM:
            case ABYSSAL_CHARGE_DRIVE_ATOM:
            case IMPENDING_DEATH_ATOM:
            case VENGEFUL_HATE:
                return true;
            default:
                return false;
        }
    }

    public static int getNoviceSkillRoot(short job) {
        if (job / 100 == 22 || job == 2001) {
            return JobConstants.JobEnum.EVAN_NOOB.getJobId();
        }
        if (job / 100 == 23 || job == 2002) {
            return JobConstants.JobEnum.MERCEDES.getJobId();
        }
        if (job / 100 == 24 || job == 2003) {
            return JobConstants.JobEnum.PHANTOM.getJobId();
        }
        if (JobConstants.isDemon(job)) {
            return JobConstants.JobEnum.DEMON.getJobId();
        }
        if (JobConstants.isMihile(job)) {
            return JobConstants.JobEnum.NAMELESS_WARDEN.getJobId();
        }
        if (JobConstants.isLuminous(job)) {
            return JobConstants.JobEnum.LUMINOUS.getJobId();
        }
        if (JobConstants.isAngelicBuster(job)) {
            return JobConstants.JobEnum.ANGELIC_BUSTER.getJobId();
        }
        if (JobConstants.isXenon(job)) {
            return JobConstants.JobEnum.XENON.getJobId();
        }
        if (JobConstants.isShade(job)) {
            return JobConstants.JobEnum.SHADE.getJobId();
        }
        if (JobConstants.isKinesis(job)) {
            return JobConstants.JobEnum.KINESIS_0.getJobId();
        }
        if (JobConstants.isBlaster(job)) {
            return JobConstants.JobEnum.CITIZEN.getJobId();
        }
        if (JobConstants.isHayato(job)) {
            return JobConstants.JobEnum.HAYATO.getJobId();
        }
        if (JobConstants.isKanna(job)) {
            return JobConstants.JobEnum.KANNA.getJobId();
        }
        return 1000 * (job / 1000);
    }

    public static int getNoviceSkillFromRace(int skillID) {
        if (skillID == 50001215 || skillID == 10001215) {
            return 1005;
        }
        if (isCommonSkill(skillID) || (skillID >= 110001500 && skillID <= 110001504)) {
            return skillID;
        }
        if (isNoviceSkill(skillID)) {
            return skillID % 10000;
        }
        return 0;
    }

    public static int getBuffSkillItem(int buffSkillID) {
        int novice = getNoviceSkillFromRace(buffSkillID);
        switch (novice) {
            // Angelic Blessing
            case 86:
                return 2022746;
            // Dark Angelic Blessing
            case 88:
                return 2022747;
            // Angelic Blessing
            case 91:
                return 2022764;
            // White Angelic Blessing
            case 180:
                return 2022823;
            // Lightning God's Blessing
            case 80000086:
                return 2023189;
            // White Angelic Blessing
            case 80000155:
                return 2022823;
            // Lightning God's Blessing
            case 80010065:
                return 2023189;
            // Goddess' Guard
            case 80011150:
                return 1112932;
        }
        return 0;
    }

    public static String getMakingSkillName(int skillID) {
        switch (skillID) {
            case 92000000:
                return "Herbalism";
            case 92010000:
                return "Mining";
            case 92020000:
                return "Smithing";
            case 92030000:
                return "Accessory Crafting";
            case 92040000:
                return "Alchemy";
        }
        return null;
    }

    public static int recipeCodeToMakingSkillCode(int skillID) {
        return 10000 * (skillID / 10000);
    }

    public static int getNeededProficiency(int level) {
        if (level > 0 && level < MAKING_SKILL_EXPERT_LEVEL) {
            return ((100 * level * level) + (level * 400)) / 2;
        } else if (level == MAKING_SKILL_EXPERT_LEVEL) {
            return 45000;
        } else if (level == MAKING_SKILL_MASTER_LEVEL) {
            return 160000;
        }
        return 0;
    }

    public static boolean isSynthesizeRecipe(int recipeID) {
        return isMakingSkillRecipe(recipeID) && recipeID % 10000 == 9001;
    }

    public static boolean isDecompositionRecipeScroll(int recipeID) {
        return isMakingSkillRecipe(recipeID)
                && recipeCodeToMakingSkillCode(recipeID) == 92040000
                && recipeID - 92040000 >= 9003
                && recipeID - 92040000 <= 9006;
    }

    public static boolean isDecompositionRecipeCube(int recipeID) {
        return isMakingSkillRecipe(recipeID) && recipeCodeToMakingSkillCode(recipeID) == 92040000 && recipeID == 92049002;
    }

    public static boolean isDecompositionRecipe(int recipeID) {
        if (isMakingSkillRecipe(recipeID) && recipeCodeToMakingSkillCode(recipeID) == 92040000 && recipeID == 92049000
                || isDecompositionRecipeScroll(recipeID)
                || isDecompositionRecipeScroll(recipeID)) {
            return true;
        }
        return false;
    }

    public static int getFairyBlessingByJob(short job) {
        short beginJob = JobConstants.JobEnum.getJobById(job).getBeginnerJobId();
        // xxxx0012, where xxxx is the "0th" job
        return beginJob * 10000 + 12;
    }

    public static int getEmpressBlessingByJob(short job) {
        short beginJob = JobConstants.JobEnum.getJobById(job).getBeginnerJobId();
        // xxxx0073, where xxxx is the "0th" job
        return beginJob * 10000 + 73;
    }

    public static boolean isBlessingSkill(int skillId) {
        // the blessings are exactly <beginner job>0012 and <beginner job>0073, never the shared 8000xxxx/8001xxxx
        // skills; a plain "ends in 12/73" check also matches e.g. the Ignore Defense Hyper Stat (80000412), Elven
        // Grace (20020112) and Fury Unleashed (30010112)
        int root = skillId / 10000;
        return root != 8000 && root != 8001 && JobConstants.isBeginnerJob((short) root)
                && (skillId % 10000 == 12 || skillId % 10000 == 73);
    }

    public static boolean isBeginnerSpAddableSkill(int skillID) {
        return skillID == 1000 || skillID == 1001 || skillID == 1002 || skillID == 140000291 || skillID == 30001000
                || skillID == 30001001 || skillID == 30001002;
    }

    public static boolean isWingedJavelinOrAbyssalCast(int skillID) {
        switch (skillID) {
            case 152110004:
            case 152120016:
            case 155121003:
                return true;
        }
        return false;
    }

    public static boolean isRandomAttackSkill(int skillID) {
        switch (skillID) {
            case 80001762:	// Liberate the Rune of Thunder
            case 80002208:	// Mesotron-X
            case 80002459:	// No Name
            case 80011549:	// No Name
                return true;
        }
        return false;
    }

    public static boolean isSentientArrowOrTornadoFlight(int skillID) {
        return skillID == 13111020 || skillID == 112111016;
    }

    public static boolean isSomeAA(int skillID) {
        // the client's 0x141023790 (melee writer 0x145644f30: int, 2 shorts, byte); v271 drops the Kinesis tornado /
        // Mind Over Matter and Baby Bombers ids and adds the Wild Arrow Blast hits
        switch (skillID) {
            case 3221024: // Empowered Piercing Arrow II
            case 3241008: // HEXA Empowered Piercing Arrow
            case 3241009:
            case 3301004:
            case 3311011:
            case 3311013:
            case 3321005:
            case 3321039:
            case 3341001:
            case 3341014:
            case 4101014: // Shuriken Burst
            case 5211021: // All Aboard
            case 5241014:
            case 33101001: // Wild Arrow Blast: Detonation
            case 33111007: // Wild Arrow Blast: Shattershot
            case 33121003: // Wild Arrow Blast: Apex
            case 33141002: // HEXA Wild Arrow Blast: Apex
            case 64111012:
            case 64141029:
            case 80003387:
            case 400021029:
            case 400031035:
                return true;
        }
        return false;
    }

    public static boolean isLuckOfTheDrawSkill(int skillID) {
        int num = skillID - 400041011;
        return num >= 0 && num <= 4;
    }

    public static boolean isSomeBlasterSkill(int skillID) {
        switch (skillID) {
            case WEAVING_ATTACK:
            case WEAVING:
            case HAMMER_SMASH:
            case HAMMER_SMASH_CHARGE:
            case BOBBING:
            case BOBBING_ATTACK:
            case REVOLVING_CANNON_RELOAD:
            case REVOLVING_CANNON:
                return true;
        }
        return false;
    }

    public static boolean isSummon(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null || skillId == GUIDED_ARROW) {
            return false;
        }
        return si.isSummon();
    }

    public static boolean isShootObj(int skillId) {

        switch (skillId) {
            case Bishop.PEACEMAKER_EXPLOSION:
            case HoYoung.MASTER_CLONE_TRANSFORMATION_MOB_DEBUFF:
                return true;
        }

        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return false;
        }
        return si.isShootObject();
    }

    public static boolean isAffectedArea(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return false;
        }
        return si.isAffectedArea();
    }

    public static boolean isNoRemoveAA(int skillID) {
        return skillID == IceLightning.CHILLING_STEP;
    }

    public static boolean isSelfDestructSummon(int skillId) {
        switch (skillId) {
            case Mechanic.ROBO_LAUNCHER_RM7:
            case Mechanic.ROCK_N_SHOCK:
            case Mechanic.SUPPORT_UNIT_HEX:
            case Mechanic.ENHANCED_SUPPORT_UNIT:
            case Mechanic.BOTS_N_TOTS:
            case Mechanic.BOTS_N_TOTS_SUB_SUMMON:
            case Mechanic.HEXA_BOTS_N_TOTS:
            case Mechanic.HEXA_BOTS_N_TOTS_SUB_SUMMON:
                return true;
        }
        return false;
    }

    public static boolean isExplodeOnDeathSummon(int skillId) {
        switch (skillId) {
            case Cadena.APOCALYPSE_CANNON_SUMMON:
            case Hayato.BATTOUJUTSU_ULTIMATE_WILL:
            case DawnWarrior.RIFT_OF_DAMNATION_SUMMON:
            case NightLord.DARK_LORDS_OMEN:
            case DemonSlayer.SPIRIT_OF_RAGE:
            case Jett.GRAVITY_CRUSH:
            case SPARKLE_BURST:
            case BeastTamer.CUB_CAVALRY_SUMMON_1:
            case BeastTamer.CUB_CAVALRY_SUMMON_2:
            case BeastTamer.CUB_CAVALRY_SUMMON_3:
            case BeastTamer.CUB_CAVALRY_SUMMON_4:
            case GHOST_YAKSHA_TRAINEE:
            case GHOST_YAKSHA_BROTHER:
            case GHOST_YAKSHA_LIEUTENANT:
            case GHOST_YAKSHA_BOSS:
                return true;
        }
        return false;
    }

    public static boolean isSpecialEffectSkill(int skillID) {
        return isExplosionSkill(skillID) || skillID == BattleMage.DARK_SHOCK || skillID == 80002206 ||
                skillID == 80000257 || skillID == 80000260 || skillID == 80002599;
    }

    public static boolean isExplosionSkill(int skillID) {
        return skillID == Xenon.TRIANGULATION
                || skillID == 36141008 // HEXA Triangulation
                || skillID == LOVELY_STING_EXPLOSION
                || skillID == SPIRALING_VORTEX_EXPLOSION
                || skillID == DawnWarrior.IMPALING_RAYS_EXPLOSION
                || skillID == BlazeWizard.IGNITION_EXPLOSION;
    }

    public static boolean isCardinalForceSkill(int skillID) {
        return isDelugeSkill(skillID) || isBurstAttackingSkill(skillID) || isTorrentSkill(skillID);
    }

    public static boolean isAncientForceSkill(int skillID) {
        switch (skillID) {
            case Pathfinder.SWARM_SHOT:
            case Pathfinder.TRIPLE_IMPACT:
            case Pathfinder.GLYPH_OF_IMPALEMENT:
            case Pathfinder.NOVA_BLAST:
            case Pathfinder.RAVEN_TEMPEST:
                return true;
            default:
                return false;
        }
    }

    public static boolean isEnchantForceSkill(int skillID) {
        switch (skillID) {
            case Pathfinder.COMBO_ASSAULT_NONE:
            case Pathfinder.COMBO_ASSAULT_DELUGE:
            case Pathfinder.COMBO_ASSAULT_BURST:
            case Pathfinder.COMBO_ASSAULT_TORRENT:

            case Pathfinder.ANCIENT_ASTRA_NONE:
            case Pathfinder.ANCIENT_ASTRA_DELUGE:
            case Pathfinder.ANCIENT_ASTRA_BURST_HOLD:
            case Pathfinder.ANCIENT_ASTRA_BURST:
            case Pathfinder.ANCIENT_ASTRA_TORRENT:

            case Pathfinder.OBSIDIAN_BARRIER_NONE:
            case Pathfinder.OBSIDIAN_BARRIER_TORRENT:
            case Pathfinder.OBSIDIAN_BARRIER_BURST:
            case Pathfinder.OBSIDIAN_BARRIER_DELUGE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isDelugeSkill(int skillID) {
        switch (skillID) {
            case Pathfinder.CARDINAL_DELUGE:
            case Pathfinder.CARDINAL_DELUGE_AMPLIFICATION:
            case Pathfinder.CARDINAL_DELUGE_ADVANCED:
            case 3341004: // HEXA Cardinal Deluge
                return true;
            default:
                return false;
        }
    }

    public static boolean isBurstAttackingSkill(int skillID) {
        switch (skillID) {
            case Pathfinder.CARDINAL_BURST:
            case Pathfinder.CARDINAL_BURST_AMPLIFICATION:
            case Pathfinder.CARDINAL_BURST_ADVANCED:
            case 3341001: // HEXA Cardinal Burst
                return true;
            default:
                return false;
        }
    }

    public static boolean isTorrentSkill(int skillID) {
        switch (skillID) {
            case Pathfinder.CARDINAL_TORRENT:
            case Pathfinder.CARDINAL_TORRENT_SWEEP:
            case Pathfinder.CARDINAL_TORRENT_ADVANCED:
            case Pathfinder.CARDINAL_TORRENT_SWEEP_ADVANCED:
            case 3341008: // HEXA Cardinal Torrent
                return true;
            default:
                return false;
        }
    }

    public static boolean isSomePathfinderSkill(int skillID) {
        switch (skillID) {
            case Pathfinder.CARDINAL_BURST:
            case 3311011:
            case Pathfinder.CARDINAL_BURST_AMPLIFICATION:
            case Pathfinder.CARDINAL_BURST_ADVANCED:
            case Pathfinder.ANCIENT_ASTRA_BURST:
            case Pathfinder.SWARM_SHOT:
            case 400031035:
            case 3341001: // HEXA Cardinal Burst
            case 3341014: // HEXA Ancient Astra (Burst)
            case 5241014: // HEXA Scurvy Summons: Sharpshooter Crew

            case BeastTamer.BABY_BOMBERS_ATTACK:
                return true;
        }
        return false;
    }

    public static boolean isBurstSkill(int skillID) {
        return skillID == 3301004 || skillID == 3311013 || skillID == 3321005 || skillID == 3341001;
    }

    public static int getCorrectMpConSkillID(int skillID) {
        switch (skillID) {
            case Hero.SWORD_ILLUSION_2:
            case Hero.SWORD_ILLUSION_3:
                return Hero.SWORD_ILLUSION;

            default:
                return skillID;
        }
    }

    public static int getCorrectCooltimeSkillID(int skillID) {
        if (SkillConstants.isChampCharge(skillID)) {
            return BeastTamer.CHAMP_CHARGE;
        }

        switch (skillID) {
            // Erda Fountain is Erda Shower with its toggle on: one cooldown, on the Shower key the client checks
            case Job.ERDA_FOUNTAIN:
                return Job.ERDA_SHOWER;
            // Sol Janus: every Dawn shares the Dawn icon's cooldown
            case HexaSkillHandler.SOL_JANUS_DAWN_2:
            case HexaSkillHandler.SOL_JANUS_DAWN_3:
                return HexaSkillHandler.SOL_JANUS_DAWN;

            // Sia Astelle: a fused Stellar shares its base skill's cooldown
            case 182001002: // Antares
            case 182101002: // Algol
            case 182111002: // Alchiba
            case 182121042: // Canopus
            case 182121044: // Capella
            case 400021148: // Sadalsuud
            case 182141002: // Shine Antares
                return skillID - 1;
            case 182121005: // Vega
            case 182121009: // Sadalmelik
                return skillID - 2;
            case 400021145: // Sirius
                return 400021143;

            // Ren: the learned Burrowing Earth has no cooltime; the hidden one holds it (see getCooltimeLevelSkillID)
            case Ren.BURROWING_EARTH:
                return Ren.BURROWING_EARTH_EFF;

            // Night Walker: the weakened Dark Omen (cast while Shadow Bat is on) has no cooltime of its own
            case NightWalker.DARK_OMEN_WEAKENED:
                return NightWalker.DARK_OMEN;
            case NightWalker.HEXA_DARK_OMEN_WEAKENED:
                return NightWalker.HEXA_DARK_OMEN;
            // the v271 cast ids have no cooltime either; the client keys them to the same cooldown
            case NightWalker.DARK_OMEN_V271:
                return NightWalker.DARK_OMEN;
            case NightWalker.HEXA_DARK_OMEN_V271:
                return NightWalker.HEXA_DARK_OMEN;

            // Hero
            case Hero.SWORD_ILLUSION_2:
            case Hero.SWORD_ILLUSION_3:
                return Hero.SWORD_ILLUSION;

            case Aran.SMASH_WAVE_COMBO:
                return Aran.SMASH_WAVE;
            case Aran.GATHERING_HOOK_COMBO:
                return Aran.GATHERING_HOOK;
            case Aran.MAHAS_CARNAGE_COMBO:
                return Aran.MAHAS_CARNAGE;

            case WildHunter.WILD_ARROW_BLAST_JAGUAR:
                return WildHunter.WILD_ARROW_BLAST;
            case WildHunter.JAGUAR_RAMPAGE:
                return WildHunter.EXPLODING_ARROWS;

            case Pathfinder.ANCIENT_ASTRA_DELUGE:
            case Pathfinder.ANCIENT_ASTRA_BURST_HOLD:
            case Pathfinder.ANCIENT_ASTRA_BURST:
            case Pathfinder.ANCIENT_ASTRA_TORRENT:
                return Pathfinder.ANCIENT_ASTRA_NONE;

            case Luminous.AETHER_CONDUIT_EQ:
            case Luminous.AETHER_CONDUIT_D:
                return Luminous.AETHER_CONDUIT_L;
            case Luminous.TWILIGHT_NOVA_D:
            case Luminous.TWILIGHT_NOVA_EQ:
                return Luminous.TWILIGHT_NOVA_L;
            case Luminous.HEXA_TWILIGHT_NOVA_D:
            case Luminous.HEXA_TWILIGHT_NOVA_EQ:
                return Luminous.HEXA_TWILIGHT_NOVA_L;

            // Sword of Light attacks come in as its unlearned variants
            case Mihile.SWORD_OF_LIGHT_1:
            case Mihile.SWORD_OF_LIGHT_2:
            case Mihile.SWORD_OF_LIGHT_3:
            case Mihile.SWORD_OF_LIGHT_4:
            case Mihile.SWORD_OF_LIGHT_5:
                return Mihile.SWORD_OF_LIGHT;

            case Cadena.SUMMON_SCIMITAR_AIR:
                return Cadena.SUMMON_SCIMITAR;

            case Corsair.NAUTILUS_ASSAULT_2:
            case Corsair.NAUTILUS_ASSAULT_3:
                return Corsair.NAUTILUS_ASSAULT;

            case Hayato.HITOKIRI_HUNDRED_STRIKE:
                return Hayato.SUMMER_RAIN;

            case BeastTamer.AERIAL_RELIEF + 1:
            case BeastTamer.AERIAL_RELIEF + 2:
            case BeastTamer.AERIAL_RELIEF + 3:
                return BeastTamer.AERIAL_RELIEF;
                
            // V skill  Blessing
            case Job.GRANDIS_BLESSING_NOVA:
            case Job.GRANDIS_BLESSING_FLORA:
            case Job.GRANDIS_BLESSING_ANIMA:
                return GRANDIS_BLESSING;

            case Kinesis.PSYCHIC_TORNADO_1:
            case Kinesis.PSYCHIC_TORNADO_2:
            case Kinesis.PSYCHIC_TORNADO_3:
                return Kinesis.PSYCHIC_TORNADO;


            // Ark
            case Ark.TENACIOUS_INSTINCT:
            case Ark.TENACIOUS_INSTINCT_COMBO:
            case Ark.UNSTOPPABLE_IMPULSE_COMBO:
                return Ark.UNSTOPPABLE_IMPULSE;
            case SCARLET_CHARGE_DRIVE_AIR:
            case SCARLET_CHARGE_DRIVE_ATTACK_COMBO_1:
                return Ark.SCARLET_CHARGE_DRIVE_ATTACK_1;
            case Ark.GRIEVOUS_WOUND_AIR:
            case Ark.GRIEVOUS_WOUND_COMBO:
                return Ark.GRIEVOUS_WOUND;
            case Ark.GUST_CHARGE_DRIVE_ATTACK_COMBO:
                return Ark.GUST_CHARGE_DRIVE_ATTACK;
            case Ark.INSATIABLE_HUNGER_FINISHER:
            case Ark.INSATIABLE_HUNGER_COMBO:
                return Ark.INSATIABLE_HUNGER;
            case Ark.UNBRIDLED_CHAOS_COMBO:
                return Ark.UNBRIDLED_CHAOS;

            case Mercedes.SPIRIT_NIMBLE_FLIGHT_V:
                return Mercedes.SPIRIT_NIMBLE_FLIGHT_H;


            // Resistance
            case RESISTANCE_INFANTRY_2:
                return RESISTANCE_INFANTRY_1;


            // Kaiser
            case INFERNO_BREATH_FINAL_FORM:
                return INFERNO_BREATH;
            case TEMPEST_BLADES_FIVE_FF:
                return TEMPEST_BLADES_FIVE;
            case TEMPEST_BLADES_THREE_FF:
                return TEMPEST_BLADES_THREE;
            case DRACO_SURGE_ATTACK_FF:
                return DRACO_SURGE_ATTACK;
            case DRAGON_BARRAGE_FINAL_FORM:
                return DRAGON_BARRAGE;

            // Beast Tamer
            case BeastTamer.ADV_THUNDER_DASH:
                return BeastTamer.THUNDER_DASH;
            case BeastTamer.MEOW_CARD_GOLD_SKILL:
                return BeastTamer.MEOW_CARD;

            // Dual Blade
            case BladeMaster.HAUNTED_EDGE_ASURA:
            case BladeMaster.HAUNTED_EDGE_YAKSA:
                return BladeMaster.HAUNTED_EDGE;

            // Phantom
            case Phantom.RIFT_BREAK_ALL_ATTACKS_IN_ONE:
                return Phantom.RIFT_BREAK_TELEPORT;

            // Pathfinder
            case Pathfinder.RELIC_UNBOUND_DELUGE_1:
            case Pathfinder.RELIC_UNBOUND_BURST_1:
            case Pathfinder.RELIC_UNBOUND_TORRENT:
                return Pathfinder.RELIC_UNBOUND;

            // Adele
            case Adele.RUIN + 1:
                return Adele.RUIN;

            // Kain
            case Kain.SHADOW_STEP_VERTICAL_DOWN:
            case Kain.SHADOW_STEP_VERTICAL_UP:
                return Kain.SHADOW_STEP_HORIZONTAL;

            case Jett.ALLIED_FURY + 1:
            case Jett.ALLIED_FURY + 2:
            case Jett.ALLIED_FURY + 3:
                return Jett.ALLIED_FURY;

            case Shadower.TRICKBLADE_FINISHER:
            case Shadower.TRICKBLADE_MOB_ATTACK:
                return Shadower.TRICKBLADE;

            case Lara.WINDING_MOUNTAIN_RIDGE_2:
                return Lara.WINDING_MOUNTAIN_RIDGE_1;

            // HEXA skills share the cooldown of the skill they replace
            case 3141015: // HEXA Gritty Gust
                return 3121052; // Gritty Gust
            case 3241013: // HEXA Bolt Burst
                return 3211018; // Bolt Burst
            case 3241015: // HEXA High Speed Shot
                return 3221052; // High Speed Shot
            case 2341009: // HEXA Genesis
                return 2321008; // Genesis
            case 2241003: // HEXA Blizzard
                return 2221007; // Blizzard
            case 2241010: // HEXA Lightning Orb
                return 2221052; // Lightning Orb
            case 5140004: // HEXA Sea Serpent
                return 5100017; // Sea Serpent
            case 5141009: // HEXA Nautilus Strike
                return 5121013; // Nautilus Strike
            case 5341006: // HEXA Nautilus Strike
                return 5321001; // Nautilus Strike
            case 500061029: // Poolmaker
                return 400051074; // Poolmaker
            case 5241002: // HEXA Broadside
                return 5221022; // Broadside
            case 5241018: // HEXA Nautilus Strike
                return 5221013; // Nautilus Strike
            case 5241017: // HEXA Ugly Bomb
                return 5221052; // Ugly Bomb
            case 5241010: // HEXA Scurvy Summons
                return 5201012; // Scurvy Summons
            case 4361005: // HEXA Sudden Raid
                return 4341011; // Sudden Raid
            case 4361001: // HEXA Asura's Anger
                return 4341052; // Asura's Anger
            case 4141007: // HEXA Dark Flare
                return 4111007; // Dark Flare
            case 4141008: // HEXA Sudden Raid
                return 4121016; // Sudden Raid
            case 4141009: // HEXA Death Star
                return 4121052; // Death Star
            case 4241012: // HEXA Dark Flare
                return 4211007; // Dark Flare
            case 500061025: // Shadow Assault
                return 400041002; // Shadow Assault
            case 4241013: // HEXA Shadow Veil
                return 4221052; // Shadow Veil
            case 4241015: // HEXA Sudden Raid
                return 4221010; // Sudden Raid
            case 1341003: // HEXA Nightshade Explosion
                return 1321052; // Nightshade Explosion
            case 1341009: // HEXA Evil Eye Shock
                return 1301014; // Evil Eye Shock
            case 1141002: // HEXA Rising Rage
                return 1121052; // Rising Rage
            case 1141006: // HEXA Cry Valhalla
                return 1121054; // Cry Valhalla
            case 1241004: // HEXA Divine Mark
                return 1221019; // Divine Mark
            case 1241007: // HEXA Heaven's Hammer
                return 1221011; // Heaven's Hammer
            case 12141007: // HEXA Blazing Extinction
                return 12121001; // Blazing Extinction
            case 12141010: // HEXA Phoenix Drive
                return 12121054; // Phoenix Drive
            case 12141017: // HEXA Towering Inferno
                return 12121002; // Towering Inferno
            case 12141018: // HEXA Towering Inferno
                return 12120023; // Towering Inferno
            case 11141004: // HEXA Cosmic Burst
                return 11121018; // Cosmic Burst
            case 11141005: // HEXA Equinox Slash
                return 11121014; // Equinox Slash
            case 13141003: // HEXA Storm Bringer
                return 13121017; // Storm Bringer
            case 13141005: // HEXA Monsoon
                return 13121052; // Monsoon
            case 13141006: // HEXA Storm Whim
                return 13121055; // Storm Whim
            case 151140003: // HEXA Aetherial Arms (v271 ids)
                return 151100013; // Aetherial Arms
            case 151141016: // HEXA Aether Bloom
                return 151121003; // Aether Bloom
            case 151141015: // HEXA Reign of Destruction
                return 151111001; // Reign of Destruction
            case 151141017: // HEXA Shardbreaker
                return 151121041; // Shardbreaker
            case 36141012: // HEXA Orbital Cataclysm
                return 36121052; // Orbital Cataclysm
            case 24141006: // HEXA Carte Rose Finale
                return 24121052; // Carte Rose Finale
            case 24141000: // HEXA Tempest
                return 24121005; // Tempest
            case 41141007: // HEXA Hitokiri Strike
                return 41121002; // Hitokiri Strike
            case 41141008: // HEXA Hitokiri Strike
                return 41121022; // Hitokiri Strike

            default:
                return skillID;
        }
    }

    private static boolean isChampCharge(int skillID) {
        return skillID == BeastTamer.CHAMP_CHARGE_1
                || skillID == BeastTamer.CHAMP_CHARGE_2
                || skillID == BeastTamer.CHAMP_CHARGE_3
                || skillID == BeastTamer.CHAMP_CHARGE_4
                || skillID == BeastTamer.CHAMP_CHARGE_5
                || skillID == BeastTamer.CHAMP_CHARGE_6
                || skillID == BeastTamer.CHAMP_CHARGE_7
                || skillID == BeastTamer.CHAMP_CHARGE_8
                || skillID == BeastTamer.CHAMP_CHARGE_9
                || skillID == BeastTamer.CHAMP_CHARGE_10
                || skillID == BeastTamer.CHAMP_CHARGE_11
                || skillID == BeastTamer.CHAMP_CHARGE_12
                || skillID == BeastTamer.CHAMP_CHARGE_13
                || skillID == BeastTamer.CHAMP_CHARGE_14;
    }

    public static boolean isAntiRepeatBuff(int skillID) {
        switch (skillID) {
            case 1000003:      // Iron Body
            case 1121000:      // Maple Warrior
            case 1121016:      // Magic Crash
            case 1121053:      // Epic Adventure
            case 1121054:      // Cry Valhalla
            case 1211010:      // HP Recovery
            case 1211013:      // Threaten
            case 1221000:      // Maple Warrior
            case 1221014:      // Magic Crash
            case 1221052:      // Smite Shield
            case 1221053:      // Epic Adventure
            case 1301006:      // Iron Will
            case 1301007:      // Hyper Body
            case 1311015:      // Cross Surge
            case 1321000:      // Maple Warrior
            case 1321014:      // Magic Crash
            case 1321015:      // Sacrifice
            case 1321053:      // Epic Adventure
            case 2101001:      // Meditation
            case 2101010:      // Ignite
            case 2121000:      // Maple Warrior
            case 2121053:      // Epic Adventure
            case 2121054:      // Inferno Aura
            case 2201001:      // Meditation
            case 2221000:      // Maple Warrior
            case 2221053:      // Epic Adventure
            case 2301004:      // Bless
            case 2311001:      // Dispel
            case 2311003:      // Holy Symbol
            case 2321000:      // Maple Warrior
            case 2321005:      // Advanced Blessing
            case 2321053:      // Epic Adventure
            case 3111011:      // Reckless Hunt: Bow
            case 3121000:      // Maple Warrior
            case 3121002:      // Sharp Eyes
            case 3121053:      // Epic Adventure
            case 3211012:      // Reckless Hunt: Crossbow
            case 3221000:      // Maple Warrior
            case 3221053:      // Epic Adventure
            case 3321022:      // Sharp Eyes
            case 3321023:      // Maple Warrior
            case 3321041:      // Epic Adventure
            case 4001003:      // Dark Sight
            case 4001005:      // Haste
            case 4101011:      // Assassin's Mark
            case 4121000:      // Maple Warrior
            case 4121053:      // Epic Adventure
            case 4221000:      // Maple Warrior
            case 4221053:      // Epic Adventure
            case 4301003:      // Self Haste
            case 4341000:      // Maple Warrior
            case 4341053:      // Epic Adventure
            case 5111007:      // Roll of the Dice
            case 5120012:      // Double Down
            case 5121000:      // Maple Warrior
            case 5121009:      // Speed Infusion
            case 5121015:      // Crossbones
            case 5121053:      // Epic Adventure
            case 5121054:      // Stimulating Conversation
            case 5211007:      // Roll of the Dice
            case 5220014:      // Double Down
            case 5221000:      // Maple Warrior
            case 5221018:      // Jolly Roger
            case 5221053:      // Epic Adventure
            case 5221054:      // Whaler's Potion
            case 5301003:      // Monkey Magic
            case 5311004:      // Barrel Roulette
            case 5311005:      // Roll of the Dice
            case 5320007:      // Double Down
            case 5320008:      // Mega Monkey Magic
            case 5321005:      // Maple Warrior
            case 5321053:      // Epic Adventure
            case 5701013:      // Bounty Chaser
            case 5711024:      // Slipstream Suit
            case 5721000:      // Maple Warrior
            case 5721053:      // Rising Cosmos
            case 5721054:      // Bionic Resilience
            case 11001022:      // Soul Element
            case 11001024:      // Falling Moon
            case 11001025:      // Rising Sun
            case 11111023:      // True Sight
            case 11121000:      // Call of Cygnus
            case 11101031:      // Equinox Cycle
            case 11121053:      // Glory of the Guardians
            case 11121054:      // Soul Forge
            case 12101000:      // Meditation
            case 12101001:      // Slow
            case 12121000:      // Call of Cygnus
            case 12121053:      // Glory of the Guardians
            case 13001022:      // Storm Elemental
            case 13101024:      // Sylvan Aid
            case 13121000:      // Call of Cygnus
            case 13121005:      // Sharp Eyes
            case 13121053:      // Glory of the Guardians
            case 14001003:      // Dark Sight
            case 14001007:      // Haste
            case 14001022:      // Haste
            case 14001023:      // Dark Sight
            case 14001027:      // Shadow Bat
            case 14121000:      // Call of Cygnus
            case 14121053:      // Glory of the Guardians
            case 15001022:      // Lightning Elemental
            case 15121000:      // Call of Cygnus
            case 15121005:      // Speed Infusion
            case 15121053:      // Glory of the Guardians
            case 20031209:      // Judgment Draw
            case 20031210:      // Judgment Draw
            case 21111012:      // Maha Blessing
            case 21121000:      // Maple Warrior
            case 21121053:      // Heroic Memories
            case 21121054:      // Hyper Finisher - Last Stand (an attack now; never sent as a skill use)
            case 22171068:      // Maple Warrior
            case 22171073:      // Blessing of the Onyx
            case 22171082:      // Heroic Memories
            case 23121004:      // Ancient Warding
            case 23121005:      // Maple Warrior
            case 23121053:      // Heroic Memories
            case 23121054:      // Elvish Blessing
            case 24111003:      // Bad Luck Ward
            case 24121004:      // Priere D'Aria
            case 24121007:      // Vol D'Ame
            case 24121008:      // Maple Warrior
            case 24121053:      // Heroic Memories
            case 25101009:      // Fox Spirits
            case 25121108:      // Maple Warrior
            case 25121132:      // Heroic Memories
            case 27111005:      // Dusk Guard
            case 27111006:      // Photic Meditation
            case 27121006:      // Arcane Pitch
            case 27121009:      // Maple Warrior
            case 27121053:      // Heroic Memories
            case 27121054:      // Equalize
            case 31011001:      // Overload Release
            case 31121003:      // Demon Cry
            case 31121004:      // Maple Warrior
            case 31121005:      // Dark Metamorphosis
            case 31121053:      // Demonic Fortitude
            case 31211003:      // Ward Evil
            case 31211004:      // Diabolic Recovery
            case 31221001:      // Nether Shield
            case 31221008:      // Maple Warrior
            case 31221053:      // Demonic Fortitude
            case 31221054:      // Forbidden Contract
            case 32121007:      // Maple Warrior
            case 32121053:      // For Liberty
            case 33121007:      // Maple Warrior
            case 33121053:      // For Liberty
            case 35111013:      // Roll of the Dice
            case 35120014:      // Double Down
            case 35121053:      // For Liberty
            case 36111004:      // Aegis System
            case 36121008:      // Maple Warrior
            case 36121053:      // Entangling Lash
            case 36121054:      // Amaranth Generator
            case 37121053:      // For Liberty
            case 41001010:      // Battoujutsu Advance
            case 41101003:      // Military Might
            case 41121005:      // Akatsuki Warrior
            case 41121053:      // Princess's Vow
            case 41121054:      // God of Blades
            case 42121006:      // Akatsuki Warrior
            case 42121053:      // Princess's Vow
            case 42121054:      // Circle of Suppression
            case 51101004:      // Rally
            case 51111004:      // Enduring Spirit
            case 51111005:      // Magic Crash
            case 51121005:      // Call of Cygnus
            case 51121053:      // Queen of Tomorrow
            case 51121054:      // Sacred Cube
            case 61101002:      // Tempest Blades
            case 61110211:      // Tempest Blades
            case 61110212:      // Dragon Slash
            case 61111008:      // Final Form
            case 61120007:      // Advanced Tempest Blades
            case 61121014:      // Nova Warrior
            case 61121217:      // Advanced Tempest Blades
            case 64121004:      // Nova Warrior
            case 64121011:      // Summon Spiked Bat
            case 64121054:      // Shadowdealer's Elixir
            case 65111100:      // Soul Seeker
            case 65121004:      // Star Gazer
            case 65121009:      // Nova Warrior
            case 65121011:      // Soul Seeker Expert
            case 65121053:      // Final Contract
            case 65121054:      // Pretty Exaltation
            case 80000365:      // Battoujutsu Soul
            case 80001816:      // Military Might
            case 80011032:      // Military Might
            case 100001268:      // Rhinne's Protection
            case 100001271:      // Time Dispel
            case 110001511:      // Maple Guardian
            case 112121006:      // Meow Card
            case 112121010:      // Meow Cure
            case 112121056:      // Team Roar
            case 131001018:      // Pink Warrior
            case 142121016:      // President's Orders
            case 400001020:      // Decent Holy Symbol
            case 400011010:      // Demonic Frenzy
            case 400011066:      // Impenetrable Skin
            case 400011102:      // Dimensional Sword
            case 400021024:      // Champ Charge
            case 400021035:      // Champ Charge
            case 400031002:      // Storm of Arrows
            case 400041008:      // Shadow Spear
            case 400051001:      // Roll of the Dice
            case 400051015:      // Lord of the Deep
            case 400051033:      // Overdrive
                return true;
            default:
                return false;
        }
    }

    // unknown func name
    // v205-1 idb -> sub_BC1DE0
    public static boolean someUpgradeSkillCheck(int skillID) {
        switch (skillID) {
            case 3011004:       // Cardinal Deluge
            case 3300002:       // Cardinal Deluge Amplification
            case 3301003:       // Cardinal Burst
            case 3310001:       // Cardinal Burst Amplification
            case 3321003:       // Cardinal Deluge
            case 3321004:       // Cardinal Burst
            case 152001001:     // Radiant Javelin
            case 152120001:     // Radiant Javelin II
            case 400031054:     // Mirage Silhoutte
                return true;
            default:
                return false;
        }
    }

    public static boolean isNoProjectileConsumptionSkill(int skillID) {
        switch (skillID) {
            case 4111009:       // Shadow Stars
            case 14111007:      // Shadow Stars
            case 5201008:       // Infinity Blast
            case 14111025:      // Spirit Projection
                return true;
            default:
                return false;
        }
    }

    public static boolean isMassSpellSkill(int skillID) {
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        if (si == null) {
            return false;
        }

        switch (skillID) {
            case SoulSkillHandler.A_QUEENLY_FRAGRANCE:
                return true;
        }

        switch (skillID) {
            case Paladin.DIVINE_ECHO:

            case FirePoison.INFERNO_AURA:
            case Kanna.CIRCLE_OF_SUPRESSION:
            case Bishop.BENEDICTION:

            case TEMPEST_BLADES_THREE:
            case TEMPEST_BLADES_FIVE:
            case TEMPEST_BLADES_THREE_FF:
            case TEMPEST_BLADES_FIVE_FF:

            case BeastTamer.MEOW_CARD:
            case BeastTamer.MEOW_CARD_GOLD_SKILL:

            case Mechanic.HUMANOID_MECH:
            case Mechanic.TANK_MECH:

            case BattleMage.WEAKENING_AURA:
            case BattleMage.AURA_SCYTHE:
                return false;
        }
        return si.isMassSpell() || (si.getInfoType() == 10 && si.getFirstRect() != null);
    }

    public static boolean isSpiritWalkerSkill(int skillID) {
        switch (skillID) {
            case SHIKIGAMI_HAUNTING_1_3:
            case SHIKIGAMI_HAUNTING_2_3:
            case SHIKIGAMI_HAUNTING_3_3:
            case SHIKIGAMI_HAUNTING_4_3:
            case SHIKIGAMI_CHARM:
            case EXORCIST_CHARM:
            case FALLING_SAKURA:
                return true;

            default:
                return false;
        }
    }

    public static void putMissingBaseStatsBySkill(Map<BaseStat, Integer> stats, SkillInfo si, int slv) {
        int skillId = si.getSkillId();
        int basicStatUpR = si.getValue(SkillStat.basicStatUp, slv);
        if (basicStatUpR > 0) {
            // all stats +%, as Maple Warrior's buff stat counts it (strR..lukR)
            stats.merge(BaseStat.strR, basicStatUpR, Integer::sum);
            stats.merge(BaseStat.dexR, basicStatUpR, Integer::sum);
            stats.merge(BaseStat.intR, basicStatUpR, Integer::sum);
            stats.merge(BaseStat.lukR, basicStatUpR, Integer::sum);
        }
        // pad/mad not in wz
        if (SkillConstants.isBlessingSkill(skillId)) {
            stats.put(BaseStat.pad, si.getValue(SkillStat.x, slv));
            // the skill says "Attack Power: +x, Magic ATT: +y"
            int blessingY = si.getValue(SkillStat.y, slv);
            stats.put(BaseStat.mad, blessingY > 0 ? blessingY : si.getValue(SkillStat.x, slv));
        }
        switch (skillId) {
            // mostly old skills that have no info in wz
            case 12120009: // Pure Magic
            case 12100027: // Spell Control
            case 22110018: // Spell Mastery
            case 22170071: // Magic Mastery
            case 27120007: // Magic Mastery
            case 32120016: // Staff Expert
            case 32100006: // Staff Mastery
                stats.put(BaseStat.mad, si.getValue(SkillStat.x, slv));
                break;
            case 13120006: // Bow Expert
            case 3120005: // Bow Expert
            case 3220004: // Crossbow Expert
            case 33120000: // Crossbow Expert
            case 14120005: // Throwing Expert
            case 4120012: // Claw Expert
            case 4220012: // Dagger Expert
            case 4340013: // Katara Expert
            case 11120007: // Student of the Blade
            case 15120006: // Knuckle Expert
            case 21120001: // High Mastery
            case 23120009: // Dual Bowgun Expert
            case 3320010: // Ancient Bow Expertise
                stats.put(BaseStat.pad, si.getValue(SkillStat.x, slv));
                break;
            case 3000001: // Critical Shot
            case 14100001: // Critical Throw
            case 14100024: // Critical Throw
            case 4100001: // Critical Throw
            case 22140018: // Critical Magic
                stats.put(BaseStat.cr, si.getValue(SkillStat.prop, slv));
                break;
            case 14110027: // Alchemic Adrenaline
            case 4110014:
                stats.put(BaseStat.recoveryUp, si.getValue(SkillStat.x, slv));
                break;
            case 13110025: // Featherweight
            case BLESSING_OF_THE_FIVE_ELEMENTS: // Blessing of the Five Elements
                stats.put(BaseStat.dmgReduce, si.getValue(SkillStat.x, slv));
                break;
            case 21120004: // High Defense
                stats.put(BaseStat.dmgReduce, si.getValue(SkillStat.t, slv));
                break;
            case Paladin.ACHILLES:
                stats.put(BaseStat.dmgReduce, si.getValue(SkillStat.y, slv));
                break;
            case 80000066: // Cygnus Blessing (Warrior)
            case 80000067: // Cygnus Blessing (Magician)
            case 80000068: // Cygnus Blessing (Bowman)
            case 80000069: // Cygnus Blessing (Thief)
            case 80000070: // Cygnus Blessing (Pirate)
                stats.put(BaseStat.asr, si.getValue(SkillStat.x, slv));
                stats.put(BaseStat.ter, si.getValue(SkillStat.y, slv));
                break;
            case FirePoison.ELEMENTAL_ADAPTATION_FP:
            case IceLightning.ELEMENTAL_ADAPTATION_IL:
            case Bishop.DIVINE_PROTECTION:
                stats.put(BaseStat.ter, si.getValue(SkillStat.asrR, slv));
                break;
            case 61000003: // Scale Skin
            case 112000010: // Dumb Luck
                stats.put(BaseStat.stance, si.getValue(SkillStat.prop, slv));
                break;
            case 112000012: // Defense Ignorance
                stats.put(BaseStat.booster, si.getValue(SkillStat.q, slv));
                break;
            case BowMaster.ILLUSION_STEP_BOW:
            case Marksman.ILLUSION_STEP_XBOW:
                stats.remove(BaseStat.dex);
                break;
            case Pathfinder.ILLUSION_STEP_ABOW: // Illusion Step
                stats.put(BaseStat.evaR, si.getValue(SkillStat.x, slv));
                break;
            case Pathfinder.ANCIENT_CURSE:
                stats.clear();
                switch (si.getCurrentLevel()) {
                    case 1:
                        stats.put(BaseStat.cr, -20);
                        // Fall through intended
                    case 2:
                        stats.put(BaseStat.pddR, -20);
                        break;
                    case 3:
                        stats.put(BaseStat.asr, -20);
                        break;
                }
                break;
            case Noblesse.ELEMENTAL_EXPERT:
            case 50000250: // Elemental Expert
                stats.put(BaseStat.madR, si.getValue(SkillStat.padR, slv));
                break;
            case DawnWarrior.WILL_OF_STEEL:
                stats.put(BaseStat.dmgReduce, si.getValue(SkillStat.x, slv));
                break;
            case 12000024: // Fire Repulsion
            case 27000003: // Standard Magic Guard
                stats.put(BaseStat.magicGuard, si.getValue(SkillStat.x, slv));
                break;
            case 12110025: // Liberated magic
                stats.put(BaseStat.fd, si.getValue(SkillStat.z, slv));
                stats.put(BaseStat.costmpR, si.getValue(SkillStat.x, slv));
                break;
            case WindArcher.SECOND_WIND:
                stats.remove(BaseStat.pad); // part of the buff
                break;
            case WindArcher.TOUCH_OF_THE_WIND:
            case Mercedes.IGNIS_ROAR:
                stats.put(BaseStat.evaR, si.getValue(SkillStat.prop, slv));
                break;
            case Evan.PARTNERS:
                stats.remove(BaseStat.damR); // part of the buff
                break;
            case DarkKnight.SACRIFICE:
                stats.remove(BaseStat.bd); // part of the buff
                break;
            case NightWalker.HASTE:
            case BladeMaster.SELF_HASTE:
            case Thief.HASTE:
            case BeastTamer.HAWK_FLOCK:
                stats.remove(BaseStat.speed); // part of the buff
                stats.remove(BaseStat.jump); // part of the buff
                break;
            case 15110009: // Precision Strikes
            case 5110011: // Precision Strikes
                stats.put(BaseStat.addCrOnBoss, si.getValue(SkillStat.prop, slv));
                break;
            case TRUE_HEART_INHERITANCE:
                stats.put(BaseStat.mastery, 10); // gj nexon, please hardcode more.
                break;
            case 5210012: // Outlaw's Code
                stats.put(BaseStat.mhp, si.getValue(SkillStat.x, slv));
                stats.put(BaseStat.mmp, si.getValue(SkillStat.x, slv));
                break;
            case Corsair.PIRATE_REVENGE_SAIR:
            case Buccaneer.STIMULATING_CONVERSATION:
                stats.remove(BaseStat.damR); // part of the buff
                break;
            case Buccaneer.CROSSBONES:
                stats.remove(BaseStat.padR);
                break;
            case Mihile.RALLY:
                stats.clear();
                stats.put(BaseStat.pad, si.getValue(SkillStat.padX, slv));
                break;
            case Jett.BOUNTY_CHASER:
                stats.remove(BaseStat.cr); // part of the buff
                stats.remove(BaseStat.damR); // part of the buff
                break;
            case Jett.HIGH_GRAVITY:
                stats.remove(BaseStat.allStat); // part of the buff
                stats.remove(BaseStat.cr); // part of the buff
                stats.remove(BaseStat.stance); // part of the buff
                break;
            case MELODY_CROSS:
                stats.remove(BaseStat.booster); // part of the buff
                break;
            case BattleMage.HASTY_AURA:
                stats.clear();
                stats.put(BaseStat.booster, si.getValue(SkillStat.actionSpeed, slv));
                stats.put(BaseStat.speed, si.getValue(SkillStat.psdSpeed, slv));
                stats.put(BaseStat.jump, si.getValue(SkillStat.psdJump, slv));
                stats.put(BaseStat.evaR, si.getValue(SkillStat.er, slv));
                break;
            case BattleMage.BLUE_AURA:
                stats.clear();
                stats.put(BaseStat.asr, si.getValue(SkillStat.asrR, slv));
                stats.put(BaseStat.ter, si.getValue(SkillStat.terR, slv));
                stats.put(BaseStat.pddR, si.getValue(SkillStat.pddR, slv));
                stats.put(BaseStat.dmgReduce, si.getValue(SkillStat.ignoreMobDamR, slv));
                break;
            case BattleMage.DARK_AURA:
                stats.remove(BaseStat.damR); // part of the buff
                break;
            case Mechanic.SUPPORT_UNIT_HEX:
                stats.remove(BaseStat.hpRecovery);
                break;
            case Xenon.EFFICIENCY_STREAMLINE: // Efficiency Streamline
                stats.put(BaseStat.mmp, si.getValue(SkillStat.mhpX, slv));
                break;
            case FirePoison.BURNING_MAGIC:
                stats.put(BaseStat.dotBuffTimeR, si.getValue(SkillStat.x, slv));
                break;
            case Illium.LUCENT_BRAND:
            case 152110011: // Might of the Flora
                stats.put(BaseStat.mhpR, si.getValue(SkillStat.mmpR, slv));
                break;
            case 80011558: // Konyanyachiwa
                stats.put(BaseStat.mhp, si.getValue(SkillStat.mhpX, slv));
                break;
            case 20000297: // Combo Kill Blessing
                stats.put(BaseStat.comboKillOrbExpR, si.getValue(SkillStat.x, slv));
                break;
            case BladeMaster.SHADOW_MELD:
                stats.remove(BaseStat.pad);
                break;
            case 80011545: // Lunar New Year Knight
            case 80011570: // MapleStory X Cardcaptor Sakura
                stats.remove(BaseStat.inte);
                stats.put(BaseStat.allStat, si.getValue(SkillStat.intX, slv));
                stats.put(BaseStat.mmp, si.getValue(SkillStat.mhpX, slv));
                break;
            case 80010108: // Exclusive V Title
            case 80002525: // Alliance basics I
            case 80002526: // Alliance basics II
            case 80002527: // Alliance basics III
            case 80000109: // King of Neckbraces
            case 80000003: // Keen Edge
            case 40010001: // Keen Edge
            case 20010194: // Inherited Will
            case 33120013: // Extended Magazine
            case 36100005: // Structural Integrity
            case Xenon.HYBRID_DEFENSES:
            case 70000015: // All Stats Increase
            case ROPE_LIFT:
            case 400001007: // Blink
            case DECENT_MYSTIC_DOOR_V:
            case DECENT_SHARP_EYES_V:
            case DECENT_HYPER_BODY_V:
            case DECENT_SPEED_INFUSION_V:
            case 11120006: // Soul Pledge
                stats.remove(BaseStat.str);
                stats.put(BaseStat.allStat, si.getValue(SkillStat.strX, slv));
                break;
            case 70000044: // All Stat Increase
                stats.remove(BaseStat.str);
                stats.put(BaseStat.allStat, si.getValue(SkillStat.strFX, slv));
                break;
            case 80000047: // Hybrid Logic
            case 30020233: // Hybrid Logic
                stats.remove(BaseStat.str);
                stats.put(BaseStat.allStatR, si.getValue(SkillStat.strR, slv));
                break;
            case 80011224: // [Beefy's Kitchen] Maker's Buff
            case 80000080: // Sweet White Day
            case 80000085: // Maple Windrider
            case 80000051: // 2013 Summer Heat
            case 80000046: // Gauntlet Hero
            case 80000043: // Miwok Cheers
            case 80000032: // Post Season Supporter
                stats.remove(BaseStat.dex);
                stats.put(BaseStat.allStat, si.getValue(SkillStat.dexX, slv));
                break;
            case 80002532: // Alliance's Strength: Attack Power/Magic ATT
            case 80000565: // Challenge: Attack Power & Magic ATT
            case 80000419: // Attack Power & Magic ATT
            case 71009002: // Lab Server Legion Block
            case 71009003: // Enhanced Lab Server Legion Block
                stats.put(BaseStat.mad, si.getValue(SkillStat.padX, slv));
                break;
            case 80000329: // Spirit of Freedom
                stats.put(BaseStat.invincibleAfterRevive, si.getValue(SkillStat.x, slv));
                break;
            case 80000333: // Spirit of Freedom (Magician)
            case 80000334: // Spirit of Freedom (Bowman)
            case 80000335: // Spirit of Freedom (Pirate)
            case 80000378: // Spirit of Freedom (Warrior)
                stats.put(BaseStat.invincibleAfterRevive, si.getValue(SkillStat.u, slv));
                break;
            case 80000582: // Adventurer Stats
                stats.remove(BaseStat.str);
                stats.put(BaseStat.allStat, si.getValue(SkillStat.strX, slv));
                stats.put(BaseStat.mad, si.getValue(SkillStat.padX, slv));
                break;
            case 80000133: // Apprentice Hunter
            case 80000134: // Capable Hunter
            case 80000135: // Veteran Hunter
            case 80000136: // Superior Hunter
                stats.remove(BaseStat.str);
                stats.put(BaseStat.allStat, si.getValue(SkillStat.strX, slv));
                stats.put(BaseStat.mmp, si.getValue(SkillStat.mhpX, slv));
                break;
            case 80002407: // Summon Parrot
            case 80002408: // Summon Parrot
            case 80002409: // Summon Parrot
            case 80002410: // Summon Parrot
            case 80002411: // Summon Parrot
            case 80002412: // Summon Parrot
            case 80002413: // Summon Parrot
            case 80002414: // Summon Parrot
            case 80002415: // Summon Parrot
            case 80002416: // Summon Parrot
                stats.put(BaseStat.pad, si.getValue(SkillStat.y, slv));
                stats.put(BaseStat.mad, si.getValue(SkillStat.y, slv));
                stats.put(BaseStat.nbd, si.getValue(SkillStat.y, slv));
                stats.put(BaseStat.ied, si.getValue(SkillStat.y, slv));
                stats.put(BaseStat.bd, si.getValue(SkillStat.y, slv));
                stats.put(BaseStat.cr, si.getValue(SkillStat.y, slv));
                break;
            case 80000251: // Rune Love power!
                stats.put(BaseStat.runeBuffTimerR, 100); // great..
                break;
            case 80000369: // Evan Link
            case 20010294: // Evan Link
                stats.put(BaseStat.runeBuffTimerR, si.getValue(SkillStat.x, slv));
                break;
            case 80000370: // Combo kill Blessing
                stats.put(BaseStat.comboKillOrbExpR, si.getValue(SkillStat.x, slv));
                break;
            case OzSkillHandler.TOWER_BOOST_RING:
                stats.put(BaseStat.ozBoostR, si.getValue(SkillStat.x, slv));
                break;
            case REALIGN_ATTACKER_MODE:
            case REALIGN_ATTACKER_MODE_I:
            case REALIGN_ATTACKER_MODE_II:
            case REALIGN_ATTACKER_MODE_III:
            case REALIGN_DEFENDER_MODE:
            case REALIGN_DEFENDER_MODE_I:
            case REALIGN_DEFENDER_MODE_II:
            case REALIGN_DEFENDER_MODE_III:
            case Kinesis.MENTAL_TEMPEST:
            case Xenon.SUPPLY_SURPLUS:
            case 22171073: // Blessing of the Onyx
            case 5921007: // Battleship Cannon
            case 5821004: // Demolition
            case 155120014: // Battle Frenzy
            case 21110000: // Advanced Combo Ability
            case WildHunter.SILENT_RAMPAGE:
            case NightLord.EXPERT_THROWING_STAR_HANDLING:
            case 50001214: // Knight's Watch
            case 42101002: // Haku Reborn
                stats.clear();
                break;

            case Mechanic.ROBOT_MASTERY:
                stats.put(BaseStat.summonTimeR, si.getValue(SkillStat.y, slv));
                break;

            case Paladin.SHIELD_MASTERY:
            case Shadower.SHIELD_MASTERY:
                stats.clear(); // stats are removed here, and given in the JobHandler'getJobBaseStat
                break;

            // TODO Haku Reborn(42101002)
            // TODO Blessing of the Five Elements(40020000)
            // TODO Deadly Fangs(112100010)     insta-kill chance
            // TODO Weapon Mastery(64100005)    more complex passive
            // TODO Critical Insight(25120214)  more complex passive
            // TODO  Magic Conversation (150000079  150010079)
            case DarkKnight.FINAL_PACT_INFO: // TODO  more complex passive
            case 142120011: // TODO  Critical Rush(142120011)
            case 33110014: // Jaguar Link   // TODO
            case 112120014: // Purr-Powered // TODO
                stats.clear();
                break;
        }
    }

    public static boolean isGrenadeSkill(int skillID) {
        return SkillConstants.isRushBombSkill(skillID)
                || skillID == 5300007
                || skillID == 27120211
                || skillID == 14111023
                || skillID == 400031003
                || skillID == 400031004
                || skillID == 64101008
                || skillID == 80011369
                || skillID == 80011370
                || skillID == Lara.WINDING_MOUNTAIN_RIDGE_1
                || skillID == Lara.WINDING_MOUNTAIN_RIDGE_2;

/*                || skillID == 80011389
                || skillID == 80011390 || skillID == 400031036 || skillID == 42120000
                || skillID == Cadena.SUMMON_SHURIKEN
                || skillID == IceLightning.FROZEN_ORB;*/
    }

    public static boolean isLinkSkill(int skillId) {
        return getOriginalOfLinkedSkill(skillId) != 0;
    }

    public static boolean isSummonedBox2DAttack(int skillID) {
        switch (skillID) {
            case MIGHTY_MASCOT:
                return true;

            default:
                return false;
        }
    }

    public static boolean isSummonedTeslaCoilAttack(int skillID) {
        return skillID == Mechanic.ROCK_N_SHOCK;
    }

    public static boolean isSummonedVariableRectAttack(int skillID) {
        return skillID == Illium.REACTION_DESTRUCTION_II;
    }


    public static boolean noEncodeAttack(int skillID) { // TEMPORARY : Just to prevent people from being able to purposely 38 others
        switch (skillID) {
            case Cadena.VETERAN_SHADOW_DEALER_FA:

            case 400031025: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK (SURGE_BOLT
            case 400031026: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK
            case 400031027: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK
            case 400031050: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK (RELIC_UNBOUND_BURST_2)
            case 400031059: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK
            case 63111104: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK (SHAFT_BREAK_VACUUM_POSSESS)
            case 63111004: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK (SHAFT_BREAK_VACUUM_POSSESS)
            case 63111105: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK (SHAFT_BREAK_VACUUM_POSSESS)
            case 63111106: // Temporary fix for Remote 38 upon REMOTE_MELEE_ATTACK (SHAFT_BREAK_VACUUM_POSSESS)
                return true;
            default:
                return false;
        }
    }

    public static boolean isNoEncodeSkillEffect(int skillID) {
        switch (skillID) {
            case Shadower.SHADOW_ASSAULT:
            case Shadower.SHADOW_ASSAULT_2:
            case Shadower.SHADOW_ASSAULT_3:
            case Shadower.SHADOW_ASSAULT_4:
            case DawnWarrior.EQUINOX_SLASH:
            case Zero.CHRONO_BREAK:
                return true;
            default:
                return false;
        }
    }

    public static boolean isValidSkillForJobAndJobLevel(int toSkillId, int chrJob, int jobLevel) {
        int rootId = getSkillRootFromSkill(toSkillId);

        int jobLevelOfSkill = JobConstants.getJobLevel((short) rootId);
        if (JobConstants.isZero(SkillConstants.getSkillRootFromSkill(toSkillId))) {
            jobLevelOfSkill = JobConstants.getJobLevelByZeroSkillID(toSkillId);
        }

        return jobLevelOfSkill == jobLevel && isMatching(rootId, chrJob);
    }

    public static boolean isShootObjectSummon(int skillID) {
        return skillID == BattleMage.ALTAR_OF_ANNIHILATION ||  // Altar of Annihilation
                skillID == Cadena.APOCALYPSE_CANNON_SUMMON || // Apocalypse Cannon
                skillID == TEMPLAR_KNIGHT ||  // Templar Knight
                skillID == 400031047 || // Relic Unbound (Deluge)
                skillID == 400031049; // Relic Unbound (Burst)
    }

    public static boolean isIgnoreDRSkill(int skillID) {
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        return ((si != null && si.isIgnoreCounter())
                || (si != null && si.isFinalAttack())
                || isSummon(skillID)
                || isShootObj(skillID)
                || isAffectedArea(skillID)
                || isForceAtomSkill(skillID)
                || skillID == BowMaster.ARROW_PLATTER_TURRET
                || skillID == DemonSlayer.DARK_METAMORPHOSIS
        );
    }

    public static boolean isSoulSkill(int skillID) {
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        if (si == null) {
            return false;
        }
        return si.getValue(SkillStat.soulmpCon, 1) > 0;
    }

    public static int getDecentSkillByItemOption(int itemOption, int jobId) {
        JobConstants.JobEnum je = JobConstants.JobEnum.getJobById(jobId);
        if (je == null) {
            return 0;
        }
        int beginnerJobId = je.getBeginnerJobId() * 10000;
        switch (itemOption) {
            case 31001: // Haste
                return beginnerJobId + 8000;
            case 31002: // Mystic Door
                return beginnerJobId + 8001;
            case 31003: // Sharp Eyes
                return beginnerJobId + 8002;
            case 31004: // Hyper Body
                return beginnerJobId + 8003;

            case 41005: // Combat Orders
                return beginnerJobId + 8004;
            case 41006: // Advanced Blessing
                return beginnerJobId + 8005;
            case 41007: // Speed Infusion
                return beginnerJobId + 8006;
        }
        return 0;
    }

    public static int getDecentSkillBySocketOption(int socketOption, int jobId) {
        JobConstants.JobEnum je = JobConstants.JobEnum.getJobById(jobId);
        if (je == null) {
            return 0;
        }
        int beginnerJobId = je.getBeginnerJobId() * 10000;
        switch (socketOption) {
            case 3370: // Haste
                return beginnerJobId + 8000;
            case 3380: // Mystic Door
                return beginnerJobId + 8001;
            case 3390: // Sharp Eyes
                return beginnerJobId + 8002;
            case 3400: // Hyper Body
                return beginnerJobId + 8003;

            case 4470: // Combat Orders
                return beginnerJobId + 8004;
            case 4480: // Advanced Blessing
                return beginnerJobId + 8005;
            case 4490: // Speed Infusion
                return beginnerJobId + 8006;
        }
        return 0;
    }

    public static boolean isNotIncreasedByRate(int skillId) {
        return NO_RATE_HYPER_STAT_SKILLS.contains(skillId);
    }

    public static int getKeyModeByBtSkill(int skillId) {
        switch (skillId / 10000) {
            case 11200:
                return 1; // Bear
            case 11210:
                return 2; // Leopard
            case 11211:
                return 3; // Bird
            case 11212:
                return 4; // Cat
            default:
                return 0; // None
        }
    }

    /**
     * @return whether the cooldown is a server-side timer rather than a real skill's cooldown (negative ids, the
     * Xenon pod, the rune power, Adele's Order): resetting every cooldown must leave these alone
     */
    public static boolean isInternalCooltime(int skillId) {
        if (skillId <= 0) {
            return true;
        }
        switch (skillId) {
            case SkillConstants.XENON_POD_FOR_COOLDOWN:
            case RuneStone.SEALED_RUNE_POWER:
            case Adele.ORDER:
                return true;
        }
        return false;
    }

    public static boolean isNoCooltimeResetSkill(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return true;
        }

        if (isInternalCooltime(skillId) || skillId == SOUL_RESONANCE) {
            return true;
        }

        // the skill data marks the skills whose cooldown can't be reset (notCooltimeReset): 12 of the 150 hyper skills
        // with a cooldown, and 245 of the 249 5th job skills with one
        return si.isNotCooltimeReset() || si.getvSkill() != 0;
    }

    public static boolean isNoBuffDurationAppliedSkill(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return false;
        }

        if (isOzRingSkill(skillId)) {
            return true;
        }

        return si.isNotIncBuffDuration() || si.getHyper() != 0 || si.getvSkill() != 0 || skillId < 0; // Item Buff
    }

    public static boolean isNoCooltimeReductionAppliedSkill(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return true;
        }

        if (isOzRingSkill(skillId) || si.isNotCooltimeReduce()) { // the data's notCooltimeReduce flag
            return true;
        }

        switch (skillId) {
            case ABYSSAL_RECALL_2:
                return true;
        }

        // the skill data marks the skills cooldown reductions don't apply to (notCooltimeReduce, 4 hyper skills)
        return false;
    }

    public static int getReduceCooltimeRforSkillId(Char chr, int skillId) {
        int cooldownReductionR = 0;
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return cooldownReductionR;
        }

        for (int psdSkillOrigin : si.getPsdSkillsOrigin()) {
            SkillInfo psdSi = SkillData.getSkillInfoById(psdSkillOrigin);
            if (!chr.hasSkill(psdSkillOrigin) || psdSi == null) {
                continue;
            }
            int psdSlv = chr.getSkillLevel(psdSkillOrigin);
            if (psdSi.isPsd() && psdSi.getValue(coolTimeR, psdSlv) > 0) {
                cooldownReductionR += psdSi.getValue(coolTimeR, psdSlv);
            }
        }
        return cooldownReductionR;
    }

    public static Stat getIncStatByMakingSkill(int makingSkillId) {
        switch (makingSkillId) {
            case HERBALISM_SKILL:
                return Stat.senseEXP;
            case MINING_SKILL:
                return Stat.willEXP;
            default:
                return Stat.craftEXP;
        }
    }

    public static int getMapleWarriorSkillByJob(int job) {
        var jobEnum = JobConstants.JobEnum.getJobById(job);
        if (jobEnum == null) {
            return 0;
        }
        switch (jobEnum) {
            case HERO:
                return Hero.MAPLE_WARRIOR_HERO;
            case PALADIN:
                return Paladin.MAPLE_WARRIOR_PALADIN;
            case DARK_KNIGHT:
                return DarkKnight.MAPLE_WARRIOR_DARK_KNIGHT;
            case FP_ARCHMAGE:
                return FirePoison.MAPLE_WARRIOR_FP;
            case IL_ARCHMAGE:
                return IceLightning.MAPLE_WARRIOR_IL;
            case BISHOP:
                return Bishop.MAPLE_WARRIOR_BISH;
            case BOWMASTER:
                return BowMaster.MAPLE_WARRIOR_BOW;
            case MARKSMAN:
                return Marksman.MAPLE_WARRIOR_XBOW;
            case PATHFINDER_4:
                return Pathfinder.MAPLE_WARRIOR_PF;
            case NIGHT_LORD:
                return NightLord.MAPLE_WARRIOR_NL;
            case SHADOWER:
                return Shadower.MAPLE_WARRIOR_SHAD;
            case BLADE_MASTER:
                return BladeMaster.MAPLE_WARRIOR_DB;
            case BUCCANEER:
                return Buccaneer.MAPLE_WARRIOR_BUCC;
            case CORSAIR:
                return Corsair.MAPLE_WARRIOR_SAIR;
            case CANNON_MASTER:
                return Cannoneer.MAPLE_WARRIOR_CANNON;
            case JETT_4:
                return Jett.MAPLE_WARRIOR_JETT;
            case DAWN_WARRIOR_4:
                return DawnWarrior.CALL_OF_CYGNUS_DW;
            case BLAZE_WIZARD_4:
                return BlazeWizard.CALL_OF_CYGNUS_BW;
            case WIND_ARCHER_4:
                return WindArcher.CALL_OF_CYGNUS_WA;
            case NIGHT_WALKER_4:
                return NightWalker.CALL_OF_CYGNUS_NW;
            case THUNDER_BREAKER_4:
                return ThunderBreaker.CALL_OF_CYGNUS_TB;
            case ARAN_4:
                return Aran.MAPLE_WARRIOR_ARAN;
            case EVAN_4:
                return Evan.MAPLE_WARRIOR_EVAN;
            case MERCEDES_4:
                return Mercedes.MAPLE_WARRIOR_MERC;
            case PHANTOM_4:
                return Phantom.MAPLE_WARRIOR_PH;
            case SHADE_4:
                return MAPLE_WARRIOR_SH;
            case LUMINOUS_4:
                return Luminous.MAPLE_WARRIOR_LUMI;
            case DEMON_SLAYER_4:
                return DemonSlayer.MAPLE_WARRIOR_DS;
            case DEMON_AVENGER_4:
                return DemonAvenger.MAPLE_WARRIOR_DA;
            case BATTLE_MAGE_4:
                return BattleMage.MAPLE_WARRIOR_BAM;
            case WILD_HUNTER_4:
                return WildHunter.MAPLE_WARRIOR_WH;
            case MECHANIC_4:
                return Mechanic.MAPLE_WARRIOR_MECH;
            case BLASTER_4:
                return MAPLE_WARRIOR_BLASTER;
            case XENON_4:
                return Xenon.MAPLE_WARRIOR_XENON;
            case HAYATO_4:
                return Hayato.AKATSUKI_HERO_HAYATO;
            case KANNA_4:
                return AKATSUKI_HERO_KANNA;
            case MIHILE_4:
                return Mihile.CALL_OF_CYGNUS_MIHILE;
            case KAISER_4:
                return NOVA_WARRIOR_KAISER;
            case CADENA_4:
                return Cadena.NOVA_WARRIOR_CADENA;
            case KAIN_4:
                return Kain.NOVA_WARRIOR;
            case ADELE_4:
                return Adele.LEF_WARRIOR;
            case ANGELIC_BUSTER_4:
                return AngelicBuster.NOVA_WARRIOR_AB;
            case ZERO_4:
                return Zero.RHINNES_PROTECTION;
            case BEAST_TAMER_4:
                return BeastTamer.MAPLE_GUARDIAN;
            case KINESIS_4:
                return Kinesis.PRESIDENTS_ORDERS;
            case ILLIUM_4:
                return Illium.HERO_OF_THE_FLORA;
            case ARK_4:
                return Ark.HERO_OF_THE_FLORA;
            case HO_YOUNG_4:
                return HoYoung.ANIMA_WARRIOR;
            case LARA_4:
                return Lara.ANIMA_WARRIOR;
        }
        return 0;
    }

    public static boolean isJobHandledVehicleSkill(int skillId) {
        switch (skillId) {
            case Mercedes.SYLVIDIAS_FLIGHT:
            case Evan.DRAGON_MASTER:
            case Evan.DRAGON_MASTER_2:
            case Jett.ANTI_GRAVITY_CYCLE_SUMMON:
                return true;
        }
        return false;
    }

    public static boolean isMultilateral(int skillId) {
        return     skillId == 30020234
                || skillId == 36000004
                || skillId == 36100007
                || skillId == 36110007
                || skillId == 36120010
                || skillId == 36120016;
    }

    public static boolean isLowerStackCTS(CharacterTemporaryStat cts) {
        return getStackByCTS(cts, new Option()) != -1337;
    }

    public static int getStackByCTS(CharacterTemporaryStat cts, Option o) {
        switch (cts) {
            case RoyalGuardState:
                return o.nOption;
        }

        return -1337;
    }

    public static boolean isStackingSummon(int skillID) {
        return NightWalker.isShadowBatSummon(skillID) ||
                skillID == Adele.ETHER_CRYSTAL;
    }

    // Skills that require a server-sided timer.
    public static boolean isTimerSkill(int skillId) {
        return skillId == Mihile.SELF_RECOVERY || skillId == Kaiser.SELF_RECOVERY
                || skillId == Adele.ATTRIBUTE || skillId == Hero.SELF_RECOVERY
                || skillId == DemonSlayer.MAX_FURY || skillId == DawnWarrior.WILL_OF_STEEL
                || skillId == Mercedes.ELVEN_HEALING || skillId == Cadena.MUSCLE_MEMORY_FINALE
                || skillId == 2120004 || skillId == 2220004 || skillId == 2320004; // Infinity (passive)
    }

    public static boolean isBroadcastSkillUseRequest(int skillID) {
        return skillID == Cadena.CHAIN_ARTS_VOID_STRIKE_ATTACK;
    }

    public static boolean isDecentSpeedInfusion(int skillId) {
        switch (skillId) {
            case 40028006: // Decent Speed Infusion
            case 30008006: // Decent Speed Infusion
            case 30018006: // Decent Speed Infusion
            case 140008006: // Decent Speed Infusion
            case 150018006: // Decent Speed Infusion
            case 150028006: // Decent Speed Infusion
            case 30028006: // Decent Speed Infusion
            case 40018006: // Decent Speed Infusion
            case 150008006: // Decent Speed Infusion
            case 50008006: // Decent Speed Infusion
            case 8006: // Decent Speed Infusion
            case 20038006: // Decent Speed Infusion
            case 110008006: // Decent Speed Infusion
            case 20028006: // Decent Speed Infusion
            case 10008006: // Decent Speed Infusion
            case 20018006: // Decent Speed Infusion
            case 20008006: // Decent Speed Infusion
            case 130008006: // Decent Speed Infusion
            case 20058006: // Decent Speed Infusion
            case 100008006: // Decent Speed Infusion
            case 20048006: // Decent Speed Infusion
            case 60018006: // Decent Speed Infusion
            case 160008006: // Decent Speed Infusion
            case 60008006: // Decent Speed Infusion
            case 60028006: // Decent Speed Infusion
            case 400000006: // Decent Speed Infusion
            case 160018006: // Decent Speed Infusion
                return true;
        }
        return false;
    }

    public static boolean isDecentHolySymbol(int skillId) {
        switch (skillId) {
            case 400001020:
        }
        return false;
    }

    public static boolean isDecentSharpEyes(int skillId) {
        switch (skillId) {
            case 40028002: // Decent Sharp Eyes
            case 30008002: // Decent Sharp Eyes
            case 30018002: // Decent Sharp Eyes
            case 140008002: // Decent Sharp Eyes
            case 150018002: // Decent Sharp Eyes
            case 150028002: // Decent Sharp Eyes
            case 30028002: // Decent Sharp Eyes
            case 40018002: // Decent Sharp Eyes
            case 150008002: // Decent Sharp Eyes
            case 50008002: // Decent Sharp Eyes
            case 8002: // Decent Sharp Eyes
            case 20038002: // Decent Sharp Eyes
            case 110008002: // Decent Sharp Eyes
            case 20028002: // Decent Sharp Eyes
            case 10008002: // Decent Sharp Eyes
            case 20018002: // Decent Sharp Eyes
            case 20008002: // Decent Sharp Eyes
            case 130008002: // Decent Sharp Eyes
            case 20058002: // Decent Sharp Eyes
            case 20048002: // Decent Sharp Eyes
            case 100008002: // Decent Sharp Eyes
            case 60018002: // Decent Sharp Eyes
            case 160008002: // Decent Sharp Eyes
            case 60008002: // Decent Sharp Eyes
            case 60028002: // Decent Sharp Eyes
            case 400001002: //Decent Sharp Eyes
            case 160018002: //Decent Sharp Eyes
                return true;
        }
        return false;
    }

    public static boolean isDecentMysticDoor(int skillId) {
        switch (skillId) {
            case 40028001: // Decent Mystic Door
            case 30008001: // Decent Mystic Door
            case 140008001: // Decent Mystic Door
            case 30018001: // Decent Mystic Door
            case 150018001: // Decent Mystic Door
            case 150028001: // Decent Mystic Door
            case 30028001: // Decent Mystic Door
            case 150008001: // Decent Mystic Door
            case 40018001: // Decent Mystic Door
            case 50008001: // Decent Mystic Door
            case 20038001: // Decent Mystic Door
            case 8001: // Decent Mystic Door
            case 110008001: // Decent Mystic Door
            case 20028001: // Decent Mystic Door
            case 10008001: // Decent Mystic Door
            case 20018001: // Decent Mystic Door
            case 20008001: // Decent Mystic Door
            case 130008001: // Decent Mystic Door
            case 20058001: // Decent Mystic Door
            case 20048001: // Decent Mystic Door
            case 100008001: // Decent Mystic Door
            case 60018001: // Decent Mystic Door
            case 160008001: // Decent Mystic Door
            case 60008001: // Decent Mystic Door
            case 60028001: // Decent Mystic Door
            case 400001001: // Decent Mystic Door
            case 160018001: // Decent Mystic Door
                return true;
        }
        return false;
    }

    public static boolean isDecentHyperBody(int skillId) {
        switch (skillId) {
            case 40028003: // Decent Hyper Body
            case 30008003: // Decent Hyper Body
            case 30018003: // Decent Hyper Body
            case 140008003: // Decent Hyper Body
            case 150018003: // Decent Hyper Body
            case 150028003: // Decent Hyper Body
            case 30028003: // Decent Hyper Body
            case 40018003: // Decent Hyper Body
            case 150008003: // Decent Hyper Body
            case 50008003: // Decent Hyper Body
            case 20038003: // Decent Hyper Body
            case 8003: // Decent Hyper Body
            case 110008003: // Decent Hyper Body
            case 20028003: // Decent Hyper Body
            case 10008003: // Decent Hyper Body
            case 20018003: // Decent Hyper Body
            case 20008003: // Decent Hyper Body
            case 130008003: // Decent Hyper Body
            case 20058003: // Decent Hyper Body
            case 20048003: // Decent Hyper Body
            case 100008003: // Decent Hyper Body
            case 60018003: // Decent Hyper Body
            case 160008003: // Decent Hyper Body
            case 60008003: // Decent Hyper Body
            case 60028003: // Decent Hyper Body
            case 400001003: // Decent Hyper Body
            case 160018003: // Decent Hyper Body
                return true;
        }
        return false;
    }

    public static boolean isDecentHaste(int skillId) {
        switch (skillId) {
            case 40028000: // Decent Haste
            case 30008000: // Decent Haste
            case 140008000: // Decent Haste
            case 30018000: // Decent Haste
            case 150018000: // Decent Haste
            case 30028000: // Decent Haste
            case 150008000: // Decent Haste
            case 40018000: // Decent Haste
            case 50008000: // Decent Haste
            case 8000: // Decent Haste
            case 20038000: // Decent Haste
            case 110008000: // Decent Haste
            case 20028000: // Decent Haste
            case 10008000: // Decent Haste
            case 20018000: // Decent Haste
            case 20008000: // Decent Haste
            case 130008000: // Decent Haste
            case 20058000: // Decent Haste
            case 20048000: // Decent Haste
            case 100008000: // Decent Haste
            case 60018000: // Decent Haste
            case 160008000: // Decent Haste
            case 150028000: // Decent Haste
            case 60008000: // Decent Haste
            case 60028000: // Decent Haste
            case 160018000: // Decent Haste
                return true;
        }
        return false;
    }

    public static boolean isDecentCombatOrders(int skillId) {
        switch (skillId) {
            case 40028004: // Decent Combat Orders
            case 30008004: // Decent Combat Orders
            case 140008004: // Decent Combat Orders
            case 30018004: // Decent Combat Orders
            case 150018004: // Decent Combat Orders
            case 30028004: // Decent Combat Orders
            case 150008004: // Decent Combat Orders
            case 40018004: // Decent Combat Orders
            case 50008004: // Decent Combat Orders
            case 8004: // Decent Combat Orders
            case 20038004: // Decent Combat Orders
            case 110008004: // Decent Combat Orders
            case 20028004: // Decent Combat Orders
            case 10008004: // Decent Combat Orders
            case 20018004: // Decent Combat Orders
            case 20008004: // Decent Combat Orders
            case 130008004: // Decent Combat Orders
            case 20058004: // Decent Combat Orders
            case 100008004: // Decent Combat Orders
            case 20048004: // Decent Combat Orders
            case 60018004: // Decent Combat Orders
            case 160008004: // Decent Combat Orders
            case 60008004: // Decent Combat Orders
            case 60028004: // Decent Combat Orders
            case 150028004: // Decent Combat Orders
            case 400001004: // Decent Combat Orders
            case 160018004: // Decent Combat Orders
                return true;
        }
        return false;
    }

    public static boolean isDecentAdvBlessing(int skillId) {
        switch (skillId) {
            case 40028005: // Decent Advanced Blessing
            case 30008005: // Decent Advanced Blessing
            case 140008005: // Decent Advanced Blessing
            case 30018005: // Decent Advanced Blessing
            case 150018005: // Decent Advanced Blessing
            case 30028005: // Decent Advanced Blessing
            case 150008005: // Decent Advanced Blessing
            case 40018005: // Decent Advanced Blessing
            case 50008005: // Decent Advanced Blessing
            case 20038005: // Decent Advanced Blessing
            case 8005: // Decent Advanced Blessing
            case 110008005: // Decent Advanced Blessing
            case 20028005: // Decent Advanced Blessing
            case 10008005: // Decent Advanced Blessing
            case 20018005: // Decent Advanced Blessing
            case 20008005: // Decent Advanced Blessing
            case 130008005: // Decent Advanced Blessing
            case 20058005: // Decent Advanced Blessing
            case 100008005: // Decent Advanced Blessing
            case 20048005: // Decent Advanced Blessing
            case 60018005: // Decent Advanced Blessing
            case 160008005: // Decent Advanced Blessing
            case 60008005: // Decent Advanced Blessing
            case 60028005: // Decent Advanced Blessing
            case 150028005: // Decent Advanced Blessing
            case 400000005: // Decent Advanced Blessing
            case 160018005: // Decent Advanced Blessing
                return true;
        }
        return false;
    }

    public static boolean isExclusiveSpellSkill(int skillId) {
        switch (skillId) {
            case EXCLUSIVE_SPELL_KAISER:
            case EXCLUSIVE_SPELL_AB:
            case EXCLUSIVE_SPELL_CADENA:
            case EXCLUSIVE_SPELL_ILLIUM:
            case EXCLUSIVE_SPELL_ARK:
            case EXCLUSIVE_SPELL_ADELE:
            case EXCLUSIVE_SPELL_HOYOUNG:
            case EXCLUSIVE_SPELL_LARA:
            case Ren.EXCLUSIVE_SPELL:
                return true;
        }
        return false;
    }

    public static int getExclusiveSpellSkillByJob(int job) {
        JobConstants.JobEnum jobEnum = JobConstants.JobEnum.getJobById(job);
        if (jobEnum == null) {
            return 0;
        }
        var skillId = jobEnum.getBeginnerJobId() * 10000 + 1005;
        if (!isExclusiveSpellSkill(skillId)) {
            return 0;
        }
        return skillId;
    }

    public static boolean isSeparateIncapacitateSkillId(int skillId) { // Skills that have their own specific stun cooldown.
        return skillId == Zero.CRITICAL_BIND;
    }

    public static boolean isBodyAttack(int skillId) {
        switch (skillId) {
            case DemonSlayer.DARK_METAMORPHOSIS:
            case Buccaneer.LORD_OF_THE_DEEP:
            case DemonAvenger.DIMENSIONAL_SWORD_ATTACK:
            case Adele.STORM:

            case Paladin.HAMMERS_OF_THE_RIGHTEOUS:
            case Paladin.HAMMERS_OF_THE_RIGHTEOUS_2:

            case DarkKnight.DARKNESS_AURA:
            case DarkKnight.DARKNESS_AURA_2:

            case Kinesis.PSYCHIC_TORNADO:

            case Xenon.OMEGA_BLASTER:

            case Kaiser.DRAGON_BLAZE:
                return true;
        }
        return false;
    }

    public static boolean isSecondAtom(int skillId) {
        var si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return false;
        }

        return si.getSecondAtomInfos().size() > 0;
    }

    public static boolean isMultiAttackSkill(int skillId) {
        switch (skillId) {
            case BladeMaster.BLADES_OF_DESTINY:
            case IceLightning.JUPITER_THUNDER:
            case DawnWarrior.FLARE_SLASH_MOON:
            case DawnWarrior.FLARE_SLASH_SUN:
            case Shadower.SHADOW_ASSAULT:
            case Shadower.TRICKBLADE_FINISHER:
            case Corsair.DEATH_TRIGGER:
            case Aran.MAHAS_CARNAGE:
            case Aran.MAHAS_CARNAGE_COMBO:
            case BeastTamer.AERIAL_RELIEF:
            case BeastTamer.AERIAL_RELIEF_2:
            case BeastTamer.AERIAL_RELIEF_3:
            case Kain.CHASING_SHOT:
            case Corsair.NAUTILUS_ASSAULT:

            case Lara.LANDS_CONNECTION_PASSIVE_1:
            case Lara.LANDS_CONNECTION_PASSIVE_2:
            case Lara.LANDS_CONNECTION_PASSIVE_3:
            case Lara.LANDS_CONNECTION_PASSIVE_4:
            case Lara.LANDS_CONNECTION_PASSIVE_5:
            case Lara.SURGING_ESSENCE:
                return true;
        }
        return false;
    }

    // Skills where we only ignore the cooldown if they come from an Attack Request
    public static boolean isIgnoreCooldownAttack(int skillId) {
        switch (skillId) {

            // Common
            case RuneStone.LIBERATE_THE_RUNE_OF_IGNITION:
            case Job.CONVERSION_OVERDRIVE:
            case Job.CONVERSION_OVERDRIVE_ATTACK:

            // Bishop
            case Bishop.HEAL:

            // Bow Master
            case BowMaster.ARROW_PLATTER_TURRET:

            // Shadower
            case Shadower.SHADOW_VEIL:

            // Blade Master
            case BladeMaster.FLASHBANG:
            case BladeMaster.CHAINS_OF_HELL:

            // Corsair
            case Corsair.BROADSIDE_BLACK_BARK:
            case Corsair.BROADSIDE_SUMMON:
            case Corsair.HEXA_BROADSIDE_SUMMON:

            // Cannoneer
            case Cannoneer.MONKEY_FURY:

            // Dawn Warrior
            case DawnWarrior.CELESTIAL_DANCE_MOON:
            case DawnWarrior.CELESTIAL_DANCE_SUN:
            case DawnWarrior.EQUINOX_SLASH:

            // Luminous
            case Luminous.LIBERATION_ORB_DARK_BULLET:
            case Luminous.LIBERATION_ORB_LIGHT_ZONE:
            case Luminous.LIBERATION_ORB_BALANCE_ATTACK:
            case Luminous.LIBERATION_ORB_IMBALANCE_ATTACK:

            // Cadena
            case Cadena.MUSCLE_MEMORY_I_ATTACK:
            case Cadena.MUSCLE_MEMORY_II_ATTACK:
            case Cadena.MUSCLE_MEMORY_III_ATTACK:
            case Cadena.CHAIN_ARTS_VOID_STRIKE_ATTACK:

            // Angelic Buster
            case AngelicBuster.SUPREME_SUPERNOVA:
            case AngelicBuster.SPARKLE_BURST:
            case AngelicBuster.TRINITY_FUSION:
            case AngelicBuster.SUPER_STAR_SPOTLIGHT:

            // Kinesis
            case Kinesis.ULTIMATE_BPM:

            // Adele
            case Adele.GATHERING:
            case Adele.BLOSSOM:
            case Adele.LEGACY_RESTORATION:

            // Ark
            case ENDLESSLY_STARVING_BEAST:

            // Lara
            case Lara.ABSORPTION_RIVER_PUDDLE_DOUSE_ACTIVE_2:
            case Lara.ABSORPTION_FIERCE_WIND_ACTIVE_2:

            // Illium
            case Illium.CRYSTAL_GATE_PORTAL_ATTACK:
                return true;
        }
        return false;
    }

    // Skills that have another effect whilst in cooldown. Thus, cannot be ignored if on cooldown.
    public static boolean isUseWhileCooldownSkill(int skillId) {
        switch (skillId) {
            case Zero.CHRONO_BREAK:
            case Phantom.SHROUD_WALK:
            case DemonAvenger.DEMONIC_BLAST_HOLDDOWN:
            case Illium.CRYSTAL_GATE:
                return true;
        }
        return false;
    }

    public static boolean isPutCooldownButBypassCheckSkill(int skillId) {
        switch (skillId) {
            case Job.LAST_RESORT:
            case Mihile.SHIELD_OF_LIGHT:
            case Hero.BURNING_SOUL_BLADE:
            case Zero.CHRONO_BREAK:
            case Adele.ETHER_CRYSTAL:
                return true;
        }
        return false;
    }

    public static SkillStat getDamageSkillStat(int skillId, OutHeader attackHeader) {
        switch (skillId) {
            case Mechanic.GIANT_ROBOT_SG_88:
                if (attackHeader == OutHeader.REMOTE_BODY_ATTACK) {
                    return SkillStat.y;
                }
                break;
        }

        return SkillStat.damage;
    }

    public static boolean isOrbitalFlameSkill(int skillId) {
        return skillId == BlazeWizard.ORBITAL_FLAME_ATOM
                || skillId == BlazeWizard.GREATER_ORBITAL_FLAME_ATOM
                || skillId == BlazeWizard.GRAND_ORBITAL_FLAME_ATOM
                || skillId == BlazeWizard.FINAL_ORBITAL_FLAME_ATOM;
    }

    public static boolean isShowCooltimeInBuffBarSkill(int skillId) {
        switch (skillId) {
            case NightLord.THROW_BLASTING_CD:

            case Buccaneer.TIME_LEAP:

            case RuneStone.SEALED_RUNE_POWER:

            case Bishop.HOLY_MAGIC_SHELL_COOLTIME:
            case Bishop.HEAVENS_DOOR_SKILL_USE:
                return true;
        }
        return false;
    }

    public static boolean isKainShadowStep(int skillID) {
        return skillID == 63001005 || skillID == 63001002 || skillID == 63001003;
    }

    public static boolean isOzRingSkill(int skillId) {
        switch (skillId) {
            case OzSkillHandler.RING_OF_RESTRAINT:
            case OzSkillHandler.ULTIMATUM_RING:
            case OzSkillHandler.LIMIT_RING:
            case OzSkillHandler.HEALTH_CUT_RING:
            case OzSkillHandler.MANA_CUT_RING:
            case OzSkillHandler.DURABILITY_RING:
            case OzSkillHandler.CRITICAL_DAMAGE_RING:
            case OzSkillHandler.CRITICAL_DEFENSE_RING:
            case OzSkillHandler.CRITICAL_SHIFT_RING:
            case OzSkillHandler.STANCE_SHIFT_RING:
            case OzSkillHandler.TOTTALING_RING:
            case OzSkillHandler.LEVEL_JUMP_S_RING:
            case OzSkillHandler.LEVEL_JUMP_D_RING:
            case OzSkillHandler.LEVEL_JUMP_I_RING:
            case OzSkillHandler.LEVEL_JUMP_L_RING:
            case OzSkillHandler.WEAPON_JUMP_S_RING:
            case OzSkillHandler.WEAPON_JUMP_D_RING:
            case OzSkillHandler.WEAPON_JUMP_I_RING:
            case OzSkillHandler.WEAPON_JUMP_L_RING:
            case OzSkillHandler.SWIFT_RING:
            case OzSkillHandler.OVERDRIVE_RING:
            case OzSkillHandler.BERSERKER_RING:
            case OzSkillHandler.REFLECTIVE_RING:
            case OzSkillHandler.CLEANSING_RING:
            case OzSkillHandler.RISK_TAKER_RING:
            case OzSkillHandler.CRISIS_H_RING:
            case OzSkillHandler.CRISIS_M_RING:
            case OzSkillHandler.CRISIS_HM_RING:
            case OzSkillHandler.CLEAN_STANCE_RING:
            case OzSkillHandler.CLEAN_DEFENCE_RING:
            case OzSkillHandler.TOWER_BOOST_RING:
                return true;
        }
        return false;
    }

    public static int getMaxHpFromStarForceConversion(int chuc) {
        if (chuc <= 0) {
            return 0;
        }

        if (chuc <= 15) {
            return chuc * 35; // 0~15 => 35 per star
        } else if (chuc <= 35) {
            return chuc * 60; // 16~35 => 60 per star
        } else if (chuc <= 60) {
            return chuc * 80; // 36~60 => 80 per star
        } else if (chuc <= 100) {
            return chuc * 100; // 61~100 => 100 per star
        } else if (chuc <= 140) {
            return chuc * 120; // 101~140 => 120 per star
        } else if (chuc <= 150) {
            return chuc * 135; // 141~150 => 135 per star
        } else if (chuc <= 175) {
            return chuc * 138; // 151~175 => 138 per star
        } else if (chuc <= 200) {
            return chuc * 140; // 176~200 => 140 per star
        } else if (chuc <= 220) {
            return chuc * 142; // 201~220 => 142 per star
        } else if (chuc <= 250) {
            return chuc * 144; // 221~250 => 144 per star
        } else if (chuc <= 270) {
            return chuc * 146; // 251~270 => 146 per star
        } else if (chuc <= 290) {
            return chuc * 148; // 271~290 => 148 per star
        } else if (chuc <= 310) {
            return chuc * 150; // 291~310 => 150 per star
        } else if (chuc <= 320) {
            return chuc * 152; // 311~320 => 152 per star
        } else if (chuc <= 330) {
            return chuc * 154; // 321~330 => 154 per star
        } else if (chuc <= 340) {
            return chuc * 156; // 331~340 => 156 per star
        } else if (chuc <= 350) {
            return chuc * 158; // 341~350 => 158 per star
        } else if (chuc <= 360) {
            return chuc * 160; // 351~360 => 160 per star
        } else if (chuc <= 370) {
            return chuc * 162; // 361~370 => 162 per star
        } else if (chuc <= 380) {
            return chuc * 164; // 371~380 => 164 per star
        } else if (chuc <= 390) {
            return chuc * 166; // 381~390 => 166 per star
        } else {
            return chuc * 168;
        }
    }

    public static boolean isBeastTamerBearSkill(int skillId) {
        return getSkillRootFromSkill(skillId) == JobConstants.JobEnum.BEAST_TAMER_1.getJobId();
    }

    public static boolean isBeastTamerLeopardSkill(int skillId) {
        return getSkillRootFromSkill(skillId) == JobConstants.JobEnum.BEAST_TAMER_2.getJobId();
    }

    public static boolean isBeastTamerHawkSkill(int skillId) {
        return getSkillRootFromSkill(skillId) == JobConstants.JobEnum.BEAST_TAMER_3.getJobId();
    }

    public static boolean isBeastTamerCatSkill(int skillId) {
        return getSkillRootFromSkill(skillId) == JobConstants.JobEnum.BEAST_TAMER_4.getJobId();
    }

    public static int getCooldownBySkillId(int skillId) {
        var si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return 0;
        }

        // Don't forget SkillConstants::getCooltimeSkillStat
        if (skillId == DemonAvenger.DEMONIC_FRENZY) {
            return si.getValue(SkillStat.z, 1);
        }
        if (skillId == Hayato.BATTOUJUTSU_ADVANCE) {
            return si.getValue(SkillStat.y, 1);
        }

        return 0;
    }

    public static boolean isFlySkill(int skillId) {
        return skillId == Evan.DRAGON_MASTER || skillId == HoYoung.NIMBUS_CLOUD ||
                skillId == BeastTamer.FLY || skillId == Jett.ANTI_GRAVITY_CYCLE_SUMMON ||
                skillId == Mechanic.BOOSTER_ONLINE || skillId == Kinesis.KINETIC_JAUNT ||
                skillId == Xenon.LIBERTY_BOOSTERS || skillId == Ark.FLOAT ||
                skillId == Illium.CRYSTALLINE_WINGS_FLY || skillId == Illium.WINGS_OF_GLORY;
    }

    public static int getNotWzLinkedAttackCountSkill(int skillId) {
        switch (skillId) {
            case IceLightning.CHAIN_LIGHTNING:
                return 2220048;
            case Mihile.RADIANT_CROSS: // Radiant Cross - Extra Strike
                return 51120058;
        }

        return 0;
    }

    /**
     * Contributed by Arixal (from GitLab).
     *
     * The skill a character must have learned for this cooltime skill to go on cooldown, for a cooltime that's on a
     * hidden skill nobody learns.
     */
    public static int getCooltimeLevelSkillID(int cooltimeSkillId) {
        switch (cooltimeSkillId) {
            case Ren.BURROWING_EARTH_EFF:
                return Ren.BURROWING_EARTH;
            // Enhanced forms: only these go on cooldown, the normal form they come from stays free to use
            case Bishop.HEXA_BIG_BANG_HOLY_EXPLOSION:
                return Bishop.HEXA_BIG_BANG;
            case BowMaster.HEXA_ARROW_STREAM_ENHANCED:
                return BowMaster.HEXA_ARROW_STREAM;
            case NightWalker.HEXA_QUINTUPLE_STAR_JET_BLACK:
                return NightWalker.HEXA_QUINTUPLE_STAR;
            case ThunderBreaker.HEXA_THUNDERBOLT_THUNDER_SLASH:
                return ThunderBreaker.HEXA_THUNDERBOLT;
            case Adele.HEXA_CLEAVE_2:
                return Adele.HEXA_CLEAVE;
        }
        return cooltimeSkillId;
    }

    public static int getOriginalSkillForSlv(int skillId) {
        switch (skillId) {
            case FINAL_BLOW_COMBO:
            case FINAL_BLOW_SMASH_SWING_COMBO:
                return FINAL_BLOW;
            case 21121055: // Last Stand 2nd and 3rd taps
            case 21121056:
                return 21121054;
            case 21141010: // HEXA Last Stand 2nd and 3rd taps
            case 21141011:
                return 21141009;
            case Job.ERDA_FOUNTAIN:
                return Job.ERDA_SHOWER;
            case HexaSkillHandler.SOL_JANUS_DAWN:
            case HexaSkillHandler.SOL_JANUS_DAWN_2:
            case HexaSkillHandler.SOL_JANUS_DAWN_3:
                return HexaSkillHandler.SOL_JANUS;
            // Skill.wz VsubSkill: Time Binder comes with Sadalsuud and uses its level, as the client does
            case net.swordie.ms.client.jobs.shine.SiaAstelle.STELLAR_XII_SADALSUUD_FUSION:
            case net.swordie.ms.client.jobs.shine.SiaAstelle.SADALSUUD_BURST:
            case net.swordie.ms.client.jobs.shine.SiaAstelle.TIME_BINDER:
                return net.swordie.ms.client.jobs.shine.SiaAstelle.STELLAR_XII_SADALSUUD;
        }

        return skillId;
    }

    public static boolean isDecentBuff(int skillID) {
        return isDecentAdvBlessing(skillID) || isDecentHaste(skillID) || isDecentHyperBody(skillID) || isDecentMysticDoor(skillID)
                || isDecentCombatOrders(skillID) || isDecentSharpEyes(skillID) || isDecentSpeedInfusion(skillID) || isDecentHolySymbol(skillID);
    }

    public static boolean isMapleWarriorSkill(int skillId) {
        switch (skillId) {
            // region adventurers
            case Hero.MAPLE_WARRIOR_HERO:
            case Paladin.MAPLE_WARRIOR_PALADIN:
            case DarkKnight.MAPLE_WARRIOR_DARK_KNIGHT:

            case FirePoison.MAPLE_WARRIOR_FP:
            case IceLightning.MAPLE_WARRIOR_IL:
            case Bishop.MAPLE_WARRIOR_BISH:

            case BowMaster.MAPLE_WARRIOR_BOW:
            case Marksman.MAPLE_WARRIOR_XBOW:
            case Pathfinder.MAPLE_WARRIOR_PF:

            case NightLord.MAPLE_WARRIOR_NL:
            case Shadower.MAPLE_WARRIOR_SHAD:
            case BladeMaster.MAPLE_WARRIOR_DB:

            case Buccaneer.MAPLE_WARRIOR_BUCC:
            case Cannoneer.MAPLE_WARRIOR_CANNON:
            case Jett.MAPLE_WARRIOR_JETT:
            case Corsair.MAPLE_WARRIOR_SAIR:
            // endregion

            // region cygnus
            case BlazeWizard.CALL_OF_CYGNUS_BW:
            case DawnWarrior.CALL_OF_CYGNUS_DW:
            case Mihile.CALL_OF_CYGNUS_MIHILE:
            case NightWalker.CALL_OF_CYGNUS_NW:
            case ThunderBreaker.CALL_OF_CYGNUS_TB:
            case WindArcher.CALL_OF_CYGNUS_WA:
            // endregion

            // region heroes
            case Aran.MAPLE_WARRIOR_ARAN:
            case Evan.MAPLE_WARRIOR_EVAN:
            case Luminous.MAPLE_WARRIOR_LUMI:
            case Mercedes.MAPLE_WARRIOR_MERC:
            case Phantom.MAPLE_WARRIOR_PH:
            case Shade.MAPLE_WARRIOR_SH:
            // endregion

            // region resistance
            case BattleMage.MAPLE_WARRIOR_BAM:
            case Blaster.MAPLE_WARRIOR_BLASTER:
            case Mechanic.MAPLE_WARRIOR_MECH:
            case WildHunter.MAPLE_WARRIOR_WH:
            case Xenon.MAPLE_WARRIOR_XENON:
            // endregion

            // region demon
            case DemonSlayer.MAPLE_WARRIOR_DS:
            case DemonAvenger.MAPLE_WARRIOR_DA:
            // endregion

            // region sengoku
            case Hayato.AKATSUKI_HERO_HAYATO:
            case Kanna.AKATSUKI_HERO_KANNA:
            // endregion

            // region nova
            case Kaiser.NOVA_WARRIOR_KAISER:
            case Kain.NOVA_WARRIOR:
            case Cadena.NOVA_WARRIOR_CADENA:
            case AngelicBuster.NOVA_WARRIOR_AB:
            // endregion

            // region flora
            case Ark.HERO_OF_THE_FLORA:
            case Illium.HERO_OF_THE_FLORA:
            case Adele.LEF_WARRIOR:
            // endregion

            // region anima
            case Lara.ANIMA_WARRIOR:
            case HoYoung.ANIMA_WARRIOR:
            // endregion

            // region other
            case Zero.RHINNES_PROTECTION:
            case BeastTamer.MAPLE_GUARDIAN:
            case Kinesis.PRESIDENTS_ORDERS:
            // endregion

                return true;
        }

        return false;
    }

    /** GMS skill text: "Immune to most abnormal status effects for 3 sec. after use" (Skill.wz time says 1). */
    public static final int HERO_WILL_IMMUNE_SECONDS = 3;

    public static boolean isHeroWillSkill(int skillId) {
        switch (skillId) {
                // region adventurers
            case Hero.HEROS_WILL_HERO:
            case Paladin.HEROS_WILL_PALA:
            case DarkKnight.HEROS_WILL_DRK:

            case FirePoison.HEROS_WILL_FP:
            case IceLightning.HEROS_WILL_IL:
            case Bishop.HEROS_WILL_BISH:

            case BowMaster.HEROS_WILL_BM:
            case Marksman.HEROS_WILL_MM:
            case Pathfinder.HEROS_WILL_PF:

            case NightLord.HEROS_WILL_NL:
            case Shadower.HEROS_WILL_SHAD:
            case BladeMaster.HEROS_WILL_DB:

            case Buccaneer.HEROS_WILL_BUCC:
            case Cannoneer.HEROS_WILL_CANNON:
            case Jett.HEROS_WILL_JETT:
            case Corsair.HEROS_WILL_SAIR:
                // endregion

                // region cygnus
            case 11121015: // Cygnus Knights' Will
            case 12121015:
            case 13121015:
            case 14121015:
            case 15121015:
            case 51121015:
                // endregion

                // region heroes
            case Aran.HEROS_WILL_ARAN:
            case Evan.HEROS_WILL_EVAN:
            case Luminous.HEROS_WILL_LUMI:
            case Mercedes.HEROS_WILL_MERC:
            case Phantom.HEROS_WILL_PH:
            case Shade.HEROS_WILL_SH:
                // endregion

                // region resistance
            case BattleMage.HEROS_WILL_BAM:
            case Blaster.HEROS_WILL_BLASTER:
            case Mechanic.HEROS_WILL_MECH:
            case WildHunter.HEROS_WILL_WH:
            case Xenon.HEROS_WILL_XENON:
                // endregion

                // region demon
                // endregion

                // region sengoku
            case Hayato.AKATSUKI_BLOSSOMS:
            case Kanna.BLOSSOMING_DAWN:
                // endregion

                // region nova
            case Kaiser.NOVA_TEMPERANCE_KAISER:
            case 61121220: // Nova Temperance (Final Form)
            case Kain.NOVA_HERO_WILL:
            case Cadena.NOVA_TEMPERANCE:
            case AngelicBuster.NOVA_TEMPERANCE_AB:
                // endregion

                // region flora
            case Ark.FLORAN_HEROS_WILL:
            case Illium.FLORAN_HERO_WILL:
            case Adele.LEF_HERO_WILL:
                // endregion

                // region anima
            case Lara.ANIMA_HERO_WILL:
            case HoYoung.ANIMA_HERO_WILL:
                // endregion

                // region other
            case BeastTamer.BEASTLY_RESOLVE:
            case Kinesis.CLEAR_MIND:
                // endregion

                return true;
        }

        return false;
    }

    public static int getBindDuration(long mobMaxHp, long totalDamage, int minBindDuration) {
        // https://strategywiki.org/wiki/MapleStory/Formulas#Bind_Skill_Duration
        return Math.min(minBindDuration * 2, (int) (minBindDuration + (((double) minBindDuration) * (totalDamage / ((double)mobMaxHp)) * 1000D)));
    }

    public static boolean isInvisible(SkillInfo si) {
        return si.isInvisible() || INVISIBLE_SKILLS_NOT_IN_WZ.contains(si.getSkillId());
    }

    public static SkillStat getCooltimeSkillStat(int skillId) {
        // Don't forget SkillConstants::getCooldownBySkillId
        if (skillId == Hayato.BATTOUJUTSU_ADVANCE) {
            return SkillStat.y;
        }

        return SkillStat.cooltime;
    }
}

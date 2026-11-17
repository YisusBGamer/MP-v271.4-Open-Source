package net.swordie.ms.constants;

import net.swordie.ms.util.Position;

/**
 * Created on 2-8-2018.
 */
public class BossConstants {

    //  Zakum --------------------------------------------------------------------------------------------------------------
    public static final int EASY_ZAKUM_FIELD = 280030200;
    public static final int NORMAL_ZAKUM_FIELD = 280030100;
    public static final int HARD_ZAKUM_FIELD = 280030000;

    public static final int EASY_ZAKUM_BODY = 8800022;
    public static final int NORMAL_ZAKUM_BODY = 8800002;
    public static final int HARD_ZAKUM_BODY = 8800102;

//  Lotus --------------------------------------------------------------------------------------------------------------

    //      General
    public static final int LOTUS_TIMER = 30 * 60;  // Minutes
    public static final int ENTRY_COOLDOWN = 30;

    //      Normal Lotus
    public static final int LOTUS_P3 = 8950002;
    public static final long LOTUS_NORMAL_HP_1 = 472_500_000_000L; // maplestorywiki.net (GMS)
    public static final long LOTUS_NORMAL_HP_2 = 472_500_000_000L; // maplestorywiki.net (GMS)
    public static final long LOTUS_NORMAL_HP_3 = 630_000_000_000L; // wiki (GMS, 2022)
    public static final int LOTUS_NORMAL_DEATHCOUNT = 5;

    //      Hard Lotus
    public static final long LOTUS_HARD_HP_1 = 9_985_500_000_000L; // maplestorywiki.net (GMS)
    public static final long LOTUS_HARD_HP_2 = 9_985_500_000_000L; // maplestorywiki.net (GMS)
    public static final long LOTUS_HARD_HP_3 = 13_314_000_000_000L; // maplestorywiki.net (GMS)
    public static final int LOTUS_HARD_P3 = 8950102;
    public static final int LOTUS_HARD_DEATHCOUNT = 5;

    // The v265 Lotus (Mob.wz patternSys, maps fieldType 368/369/370). Index 0 Normal, 1 Hard, 2 Extreme.
    // Phase maps base + 100 / + 300 / + 500, treasure + 600; monsters base + 0 / + 1 / + 2, reward core + 3.
    public static final String[] LOTUS_V265_MODE_NAMES = {"Normal", "Hard", "Extreme"};
    public static final int[] LOTUS_V265_FIELD_BASE = {350066000, 350067000, 350068000};
    public static final int[] LOTUS_V265_MOB_BASE = {8881100, 8881150, 8881200};
    public static final long[][] LOTUS_V265_HP = { // maplestorywiki.net (GMS)
            {472_500_000_000L, 472_500_000_000L, 630_000_000_000L},
            {9_985_500_000_000L, 9_985_500_000_000L, 13_314_000_000_000L},
            {543_300_000_000_000L, 543_300_000_000_000L, 724_400_000_000_000L}};

    // phase 1's body and its pattern monsters (base + 0 / + 4 / + 10 / + 11), every mode
    private static final int[] LOTUS_V265_PHASE_ONE_OFFSETS = {0, 4, 10, 11};

    /** @return whether the monster is phase 1 of a v265 Lotus mode (their init carries an extra block, as GMS sends) */
    public static boolean isLotusV265PhaseOneMob(int templateId) {
        for (int base : LOTUS_V265_MOB_BASE) {
            for (int off : LOTUS_V265_PHASE_ONE_OFFSETS) {
                if (templateId == base + off) {
                    return true;
                }
            }
        }
        return false;
    }

    /** @return the v265 Lotus mode whose maps hold this field, or -1 */
    public static int getLotusV265Mode(int fieldId) {
        for (int m = 0; m < LOTUS_V265_FIELD_BASE.length; m++) {
            if (fieldId >= LOTUS_V265_FIELD_BASE[m] && fieldId <= LOTUS_V265_FIELD_BASE[m] + 600) {
                return m;
            }
        }
        return -1;
    }

    // Black Mage (Limina, fieldType 208..211 for phases 1..4). Index 0 Hard, 1 Extreme.
    public static final String[] BLACK_MAGE_MODE_NAMES = {"Hard", "Extreme"};
    public static final int[][] BLACK_MAGE_FIELDS = { // phase 1..4, then Beyond Existence (treasure)
            {450013100, 450013300, 450013500, 450013700, 450013750},
            {450013910, 450013930, 450013950, 450013970, 450013980}};
    public static final String[] BLACK_MAGE_MODE_KEYS = {"hard", "extreme"}; // resources/data/blackmage-fight.tsv
    public static final int BLACK_MAGE_P4 = 8880504, BLACK_MAGE_GENESIS_CRUX = 8880518; // phase 4 body (pay table), reward box
    /** Mob skill 249 (the attacks' disease): level 1 is the Curse of Creation, the other the Curse of Destruction. */
    public static final int BLACK_MAGE_CURSE_SKILL = 249, BLACK_MAGE_CURSE_CREATION_LEVEL = 1;
    /** Both curses at once (GMS): the hit effect and the Seal the life loss brings, as mob skill {id, level}. */
    public static final int[] BLACK_MAGE_LIFE_LOSS_HIT = {176, 45}, BLACK_MAGE_LIFE_LOSS_SEAL = {120, 39};
    /** GMS sends the curses for half their MobSkill.img time (3 s for time 6). */
    public static final int BLACK_MAGE_CURSE_TIME_PERCENT = 50;
    /** The Black Mage's death count window (OPEN_UI on every phase map, GMS) and how long a phase 4 mark stays. */
    public static final int BLACK_MAGE_DEATH_COUNT_UI = 1204, BLACK_MAGE_MARK_SECONDS = 30;
    public static final long[][] BLACK_MAGE_HP = { // knights (one shared bar), phase 2, 3, 4
            {63_000_000_000_000L, 115_500_000_000_000L, 157_500_000_000_000L, 136_500_000_000_000L}, // Hard (maplestorywiki)
            {1_180_000_000_000_000L, 1_191_000_000_000_000L, 1_285_000_000_000_000L, 1_155_000_000_000_000L}}; // Extreme (maplestorywiki estimates; Mob.wz hides maxHP)

    /** @return the Black Mage mode whose maps hold this field, or -1 */
    public static int getBlackMageMode(int fieldId) {
        for (int m = 0; m < BLACK_MAGE_FIELDS.length; m++) {
            for (int f : BLACK_MAGE_FIELDS[m]) {
                if (f == fieldId) {
                    return m;
                }
            }
        }
        return -1;
    }

    public static int lotusV265Core(int mode) {
        return LOTUS_V265_MOB_BASE[mode] + 3;
    }

    //      Falling Foothold
    public static final int LOTUS_FALLING_FOOTHOLD_CHANCE = 60;  // 60% chance of spawning when MobSkill Used
    public static final int LOTUS_FALLING_FOOTHOLD_WIDTH = 90; // value used for both  left distance & right distance
    public static final int LOTUS_FALLING_FOOTHOLD_DURATION = 10; // 10 seconds
    public static final int LOTUS_FALLING_FOOTHOLD_SHOCK_TERRAIN_DELAY = 2500; // ms  after falling footholds land, will the shock terrain start

    //      Toss Skill
    public static final int LOTUS_AMOUNT_OF_BEAMS = 3;

    //      Obstacle Atoms
    public static final int LOTUS_OBSTACLE_ATOM_VELOCITY = 9; // Velocity at which the Obstacle Atoms fall down.

    public static final int LOTUS_BLUE_ATOM_EXECUTION_DELAY = 1500; // in ms. Delay between method executions
    public static final int LOTUS_BLUE_ATOM_AMOUNT = 3; // max amount of Atoms spawning attempts in 1 call
    public static final int LOTUS_BLUE_ATOM_PROP = 25; // % chance of actually spawning in
    public static final int LOTUS_BLUE_ATOM_DAMAGE = 25; // % of Max HP

    public static final int LOTUS_YELLOW_ATOM_EXECUTION_DELAY = 1750; // in ms. Delay between method executions
    public static final int LOTUS_YELLOW_ATOM_AMOUNT = 3; // max amount of Atoms spawning attempts in 1 call
    public static final int LOTUS_YELLOW_ATOM_PROP = 20; // % chance of actually spawning in
    public static final int LOTUS_YELLOW_ATOM_DAMAGE = 45; // % of Max HP

    public static final int LOTUS_PURPLE_ATOM_EXECUTION_DELAY = 2000; // in ms. Delay between method executions
    public static final int LOTUS_PURPLE_ATOM_AMOUNT = 3; // max amount of Atoms spawning attempts in 1 call
    public static final int LOTUS_PURPLE_ATOM_PROP = 15; // % chance of actually spawning in
    public static final int LOTUS_PURPLE_ATOM_DAMAGE = 75; // % of Max HP
    //      Hard Lotus
    public static final int LOTUS_ROBOT_ATOM_EXECUTION_DELAY = 3000; // in ms. Delay between method executions
    public static final int LOTUS_ROBOT_ATOM_AMOUNT = 2; // max amount of Atoms spawning attempts in 1 call
    public static final int LOTUS_ROBOT_ATOM_PROP = 10; // % chance of actually spawning in
    public static final int LOTUS_ROBOT_ATOM_DAMAGE = 90; // % of Max HP
    //      Stage 3
    public static final int LOTUS_CRUSHER_ATOM_EXECUTION_DELAY = 10000; // in ms. Delay between method executions
    public static final int LOTUS_CRUSHER_ATOM_AMOUNT = 2; // max amount of Atoms spawning attempts in 1 call
    public static final int LOTUS_CRUSHER_ATOM_PROP = 50; // % chance of actually spawning in
    public static final int LOTUS_CRUSHER_ATOM_DAMAGE = 100; // % of Max HP


//  Root Abyss ---------------------------------------------------------------------------------------------------------

    public static final int NORMAL_CRIMSON_QUEEN_SIMMERING = 8920100;
    public static final int NORMAL_CRIMSON_QUEEN_JOYOUS = 8920101;
    public static final int NORMAL_CRIMSON_QUEEN_WRATHFUL = 8920102;
    public static final int NORMAL_CRIMSON_QUEEN_SORROWFUL = 8920103;

    public static final int CHAOS_CRIMSON_QUEEN_SIMMERING = 8920000;
    public static final int CHAOS_CRIMSON_QUEEN_JOYOUS = 8920001;
    public static final int CHAOS_CRIMSON_QUEEN_WRATHFUL = 8920002;
    public static final int CHAOS_CRIMSON_QUEEN_SORROWFUL = 8920003;




    public static final int CRIMSON_QUEEN_NORMAL_BOX = 8920106;
    public static final int PIERRE_NORMAL_BOX = 8900103;
    public static final int VELLUM_NORMAL = 8930100;

    public static final int CRIMSON_QUEEN_CHAOS_BOX = 8920006;
    public static final int PIERRE_CHAOS_BOX = 8900003;
    public static final int QUEEN_CHAOS = 8920002;
    public static final int VELLUM_CHAOS = 8930000;
    public static final long VON_BON_CHAOS_HP = 800_000_000_000L;
    public static final long VELLUM_CHAOS_HP = 1_200_000_000_000L;
    public static final long QUEEN_CHAOS_HP = 900_000_000_000L;
    public static final long PIERRE_CHAOS_HP = 600_000_000_000L;

    //Pierre-----------------------------------------------------------------------------------------------------------
    public static final int PIERRE_CHAOS_FIELDID = 105200610;
    public static final int PIERRE_NORMAL_FIELDID = 105200210;

    public static final int PIERRE_CHAOS_PURPLE = 8900000;
    public static final int PIERRE_CHAOS_RED = 8900001;
    public static final int PIERRE_CHAOS_BLUE = 8900002;

    public static final int PIERRE_NORMAL_PURPLE = 8900100;
    public static final int PIERRE_NORMAL_RED = 8900101;
    public static final int PIERRE_NORMAL_BLUE = 8900102;

    //Von Bon ---------------------------------------------------------------------------------------------------------
    //      Obstacle Atoms
    public static final int VON_BON_ATOM_MIN_VELOCITY = 3; // Min Velocity at which the Obstacle Atoms fall down.
    public static final int VON_BON_ATOM_MAX_VELOCITY = 8; // Max Velocity at which the Obstacle Atoms fall down.


    public static final int VON_BON_GREEN_ATOM_EXECUTION_DELAY = 750; // in ms. Delay between method executions
    public static final int VON_BON_GREEN_ATOM_AMOUNT = 4; // max amount of Atoms spawning attempts in 1 call
    public static final int VON_BON_GREEN_ATOM_PROP = 30; // % chance of actually spawning in
    public static final int VON_BON_GREEN_ATOM_DAMAGE = 30; // % of Max HP

    public static final int VON_BON_BLUE_ATOM_EXECUTION_DELAY = 1000; // in ms. Delay between method executions
    public static final int VON_BON_BLUE_ATOM_AMOUNT = 3; // max amount of Atoms spawning attempts in 1 call
    public static final int VON_BON_BLUE_ATOM_PROP = 20; // % chance of actually spawning in
    public static final int VON_BON_BLUE_ATOM_DAMAGE = 50; // % of Max HP

    public static final int VON_BON_PURPLE_ATOM_EXECUTION_DELAY = 2000; // in ms. Delay between method executions
    public static final int VON_BON_PURPLE_ATOM_AMOUNT = 2; // max amount of Atoms spawning attempts in 1 call
    public static final int VON_BON_PURPLE_ATOM_PROP = 10; // % chance of actually spawning in
    public static final int VON_BON_PURPLE_ATOM_DAMAGE = 100; // % of Max HP


    public static final int VON_BON_TIME_SHIFT = 7; //s
    public static final int VON_BON_TIME = 600; //seconds


    public static final int VON_BON_NORMAL = 8910100;
    public static final int VON_BON_NORMAL_FIELDID = 105200110;
    public static final int VON_BON_NORMAL_REALM_FIELDID = 105200520;
    public static final int VON_BON_NORMAL_DISEMBODIED = 8910101;


    public static final int VON_BON_CHAOS = 8910000;
    public static final int VON_BON_CHAOS_FIELDID = 105200510;
    public static final int VON_BON_CHAOS_REALM_FIELDID = 105200520;
    public static final int VON_BON_CHAOS_DISEMBODIED = 8910001;


//  Gollux -------------------------------------------------------------------------------------------------------------

    //      General
    public static final int GOLLUX_TIME = 30 * 60; // 30 minutes
    public static final int GOLLUX_DEATH_COUNT = 5; // starting amount of Death Count
    public static final int MAX_GOLLUX_STACKS = 5;
    public static final String GOLLUX_SPAWN_KEY = "Spawned";
    public static final String GOLLUX_FINISHED_KEY = "Killed";

    public static final long GOLLUX_ABDOMEN_HP = 990000000;     // 990mil HP
    public static final long GOLLUX_L_SHOULDER_HP = 990000000;  // 990mil HP
    public static final long GOLLUX_R_SHOULDER_HP = 990000000;  // 990mil HP

    // Gollux's head by phase (jaw, eyes, jewel): GMS values from maplestorywiki.net
    public static final long EASY_GOLLUX_HEAD_JAW = 50_000_000L;
    public static final long EASY_GOLLUX_HEAD_EYES = 50_000_000L;
    public static final long EASY_GOLLUX_HEAD_JEWEL = 10_000_000L;

    public static final long NORMAL_GOLLUX_HEAD_JAW = 3_000_000_000L;
    public static final long NORMAL_GOLLUX_HEAD_EYES = 3_000_000_000L;
    public static final long NORMAL_GOLLUX_HEAD_JEWEL = 600_000_000L;

    public static final long HARD_GOLLUX_HEAD_JAW = 75_000_000_000L;
    public static final long HARD_GOLLUX_HEAD_EYES = 75_000_000_000L;
    public static final long HARD_GOLLUX_HEAD_JEWEL = 15_000_000_000L;

    public static final long HELL_GOLLUX_HEAD_JAW = 350_000_000_000L; // maplestorywiki.net (GMS): head phase 1
    public static final long HELL_GOLLUX_HEAD_EYES = 350_000_000_000L; // head phase 2
    public static final long HELL_GOLLUX_HEAD_JEWEL = 70_000_000_000L; // head phase 3

    public static final int GOLLUX_REWARD_EASY = 9601459;
    public static final int GOLLUX_REWARD_NORMAL = 9601460;
    public static final int GOLLUX_REWARD_HARD = 9601461;
    public static final int GOLLUX_REWARD_HELL = 9601462;

//  Magnus -------------------------------------------------------------------------------------------------------------

    //      General
    public static final int HARD_MAGNUS_TEMPLATE = 8880000;
    public static final int NORMAL_MAGNUS_TEMPLATE = 8880002;
    public static final int EASY_MAGNUS_TEMPLATE = 8880010;
    public static final int EASY_MAGNUS_MAPID = 401060300;
    public static final int NORMAL_MAGNUS_MAPID = 401060200;
    public static final int HARD_MAGNUS_MAPID = 401060100;

    public static final int MAGNUS_TIME = 30 * 60;
    public static final int MAGNUS_DEATHCOUNT = 15; // 15 death count

    public static final int V_MAGNUS_TEMPLATE = 9001059;
    public static final long V_QUEST_MAGNUS_HP = 25_000_000_000L;

    //      Hekaton
    public static final int HEKATON_OBSTACLE_ATOM_VELOCITY = 5; // Velocity at which the Obstacle Atoms fall down.
    public static final int HEKATON_RED_ATOM_EXECUTION_DELAY = 1500; // in ms. Delay between method executions
    public static final int HEKATON_RED_ATOM_AMOUNT = 3; // max amount of Atoms spawning attempts in 1 call
    public static final int HEKATON_RED_ATOM_PROP = 30; // % chance of actually spawning in
    public static final int HEKATON_RED_ATOM_DAMAGE = 10; // % of Max HP

    //      Obstacle Atoms
    public static final int MAGNUS_OBSTACLE_ATOM_VELOCITY = 5; // Velocity at which the Obstacle Atoms fall down.

    public static final int MAGNUS_GREEN_ATOM_EXECUTION_DELAY = 1000; // in ms. Delay between method executions
    public static final int MAGNUS_GREEN_ATOM_AMOUNT = 4; // max amount of Atoms spawning attempts in 1 call
    public static final int MAGNUS_GREEN_ATOM_PROP = 35; // % chance of actually spawning in
    public static final int MAGNUS_GREEN_ATOM_DAMAGE = 25; // % of Max HP

    public static final int MAGNUS_BLUE_ATOM_EXECUTION_DELAY = 750; // in ms. Delay between method executions
    public static final int MAGNUS_BLUE_ATOM_AMOUNT = 4; // max amount of Atoms spawning attempts in 1 call
    public static final int MAGNUS_BLUE_ATOM_PROP = 30; // % chance of actually spawning in
    public static final int MAGNUS_BLUE_ATOM_DAMAGE = 50; // % of Max HP

    public static final int MAGNUS_PURPLE_ATOM_EXECUTION_DELAY = 2000; // in ms. Delay between method executions
    public static final int MAGNUS_PURPLE_ATOM_AMOUNT = 3; // max amount of Atoms spawning attempts in 1 call
    public static final int MAGNUS_PURPLE_ATOM_PROP = 25; // % chance of actually spawning in
    public static final int MAGNUS_PURPLE_ATOM_DAMAGE = 100; // % of Max HP
    // meteor damage (% max HP) by mode: green / blue / purple
    public static final int EASY_MAGNUS_GREEN_ATOM_DAMAGE = 10;
    public static final int EASY_MAGNUS_BLUE_ATOM_DAMAGE = 30;
    public static final int EASY_MAGNUS_PURPLE_ATOM_DAMAGE = 50;
    public static final int NORMAL_MAGNUS_GREEN_ATOM_DAMAGE = 50;
    public static final int NORMAL_MAGNUS_BLUE_ATOM_DAMAGE = 60;
    public static final int NORMAL_MAGNUS_PURPLE_ATOM_DAMAGE = 80;
    public static final int HARD_MAGNUS_GREEN_ATOM_DAMAGE = 50;
    public static final int HARD_MAGNUS_BLUE_ATOM_DAMAGE = 80;
    public static final int HARD_MAGNUS_PURPLE_ATOM_DAMAGE = 100;


//  Horntail -------------------------------------------------------------------------------------------------------

    //      General
    public static final int HORNTAIL_DEATH_COUNT = 5;
    public static final String HORNTAIL_PROGRESS = "HorntailProgress";
    public static final int DEAD_HORNTAIL_HEAD_A = 8810010;
    public static final int DEAD_HORNTAIL_HEAD_B = 8810011;
    public static final int DEAD_HORNTAIL_HEAD_C = 8810012;
    public static final int DEAD_HORNTAIL_LEFT_HAND = 8810013;
    public static final int DEAD_HORNTAIL_RIGHT_HAND = 8810014;
    public static final int DEAD_HORNTAIL_WINGS = 8810015;
    public static final int DEAD_HORNTAIL_LEGS = 8810016;
    public static final int DEAD_HORNTAIL_TAILS = 8810017;


    // Easy
    public static final int HORNTAIL_EASY = 8810214;
    public static final int EASY_HORNTAIL_TIME = 75 * 60; // 1 hr, 15 min timer

    public static final int EASY_HORNTAIL_HEAD_1 = 8810200;
    public static final int EASY_HORNTAIL_HEAD_2 = 8810201;

    public static final int EASY_HORNTAIL_HEAD_A = 8810202;
    public static final int EASY_HORNTAIL_HEAD_B = 8810203;
    public static final int EASY_HORNTAIL_HEAD_C = 8810204;
    public static final int EASY_HORNTAIL_LEFT_HAND = 8810205;
    public static final int EASY_HORNTAIL_RIGHT_HAND = 8810206;
    public static final int EASY_HORNTAIL_WINGS = 8810207;
    public static final int EASY_HORNTAIL_LEGS = 8810208;
    public static final int EASY_HORNTAIL_TAIL = 8810209;

    public static final int EASY_HORNTAIL_FIELDID_1 = 240_060_002;
    public static final int EASY_HORNTAIL_FIELDID_2 = 240_060_102;
    public static final int EASY_HORNTAIL_FIELDID_3 = 240_060_300;

    // Normal
    public static final int HORNTAIL_NORMAL = 8810018;

    public static final int NORMAL_HORNTAIL_HEAD_1 = 8810000;
    public static final int NORMAL_HORNTAIL_HEAD_2 = 8810001;

    public static final int NORMAL_HORNTAIL_HEAD_A = 8810002;
    public static final int NORMAL_HORNTAIL_HEAD_B = 8810003;
    public static final int NORMAL_HORNTAIL_HEAD_C = 8810004;
    public static final int NORMAL_HORNTAIL_LEFT_HAND = 8810005;
    public static final int NORMAL_HORNTAIL_RIGHT_HAND = 8810006;
    public static final int NORMAL_HORNTAIL_WINGS = 8810007;
    public static final int NORMAL_HORNTAIL_LEGS = 8810008;
    public static final int NORMAL_HORNTAIL_TAIL = 8810009;

    public static final int NORMAL_HORNTAIL_FIELDID_1 = 240_060_000;
    public static final int NORMAL_HORNTAIL_FIELDID_2 = 240_060_100;
    public static final int NORMAL_HORNTAIL_FIELDID_3 = 240_060_200;

    // Chaos
    public static final int HORNTAIL_HARD = 8810122;
    public static final long HORNTAIL_CHAOS_MULTIPLIER = 4;
    public static final long HORNTAIL_CHAOS_HP = 10_000_000_000L * HORNTAIL_CHAOS_MULTIPLIER; // 40bil
    public static final int CHAOS_HORNTAIL_TIME = 150 * 60; // 2 hrs, 30 min timer

    public static final int CHAOS_HORNTAIL_HEAD_1 = 8810100;
    public static final int CHAOS_HORNTAIL_HEAD_2 = 8810101;

    public static final int CHAOS_HORNTAIL_HEAD_A = 8810102;
    public static final int CHAOS_HORNTAIL_HEAD_B = 8810103;
    public static final int CHAOS_HORNTAIL_HEAD_C = 8810104;
    public static final int CHAOS_HORNTAIL_LEFT_HAND = 8810105;
    public static final int CHAOS_HORNTAIL_RIGHT_HAND = 8810106;
    public static final int CHAOS_HORNTAIL_WINGS = 8810107;
    public static final int CHAOS_HORNTAIL_LEGS = 8810108;
    public static final int CHAOS_HORNTAIL_TAIL = 8810109;
    public static final int CHAOS_HORNTAIL = 8810118;

    public static final int CHAOS_HORNTAIL_FIELDID_1 = 240_060_001;
    public static final int CHAOS_HORNTAIL_FIELDID_2 = 240_060_101;
    public static final int CHAOS_HORNTAIL_FIELDID_3 = 240_060_201;


//  Hilla --------------------------------------------------------------------------------------------------------------

    //      General
    public static final int HILLA_NORMAL = 8870000;
    public static final int HILLA_HARD = 8870100;
    public static final long HILLA_HARD_HP = 16_800_000_000L; // wiki (GMS, 2022)
    public static final int EASY_HILLA_TIME = 30 * 60; // 1 hr, 15 min timer
    public static final int NORMAL_HILLA_TIME = 150 * 60; // 2 hrs, 30 min timer


    //  Von Leon -----------------------------------------------------------------------------------------------------------
    public static final int VON_LEON_SUMMON_NPC = 2161000;

    public static final int VON_LEON_TIME = 30 * 60; // 30 min timer

    public static final int VON_LEON_DEATHCOUNT = 5;
    public static final int VON_LEON_EASY_FIELDID = 211070300;
    public static final int VON_LEON_NORMAL_FIELDID = 211070400;
    public static final int VON_LEON_HARD_FIELDID = 211070500;
    public static final int VON_LEON_PRISON_EASY_FIELDID = 211070101;
    public static final int VON_LEON_PRISON_NORMAL_FIELDID = 211070103;
    public static final int VON_LEON_PRISON_HARD_FIELDID = 211070105;
    public static final int VON_LEON_EASY = 8840007; // old 8840007
    public static final int VON_LEON_NORMAL = 8840000; // old 8840000
    public static final int VON_LEON_HARD = 8840014;
    public static final long VON_LEON_HARD_HP = 10_500_000_000L; // wiki (GMS, 2022)

    //  Cygnus -------------------------------------------------------------------------------------------------------------
    public static final int CYGNUS_TIME = 30 * 60; // 30 min timer
    public static final int CYGNUS_EASY = 8850111;
    public static final int SHINSOO_CYGNUS_EASY = 8850110;
    public static final int CYGNUS_NORMAL = 8850011;
    public static final int CYGNUS_FLAMES_DELAY = 2500; //ms
    public static final int CYGNUS_EASY_MAP = 271041100;
    public static final int CYGNUS_NORMAL_MAP = 271040100;


    //  Arkarium -----------------------------------------------------------------------------------------------------------
    public static final int ARKARIUM_TIME = 30 * 60; // 30 min timer
    public static final int ARKARIUM_EASY = 8860005;
    public static final long ARKARIUM_EASY_HP = 2_100_000_000L; // wiki (GMS, 2022)
    public static final long ARKARIUM_EASY_FIELD = 272_020_210;
    public static final int ARKARIUM_EASY_BANMAP_FIELDID = 272020300;
    public static final int ARKARIUM_NORMAL = 8860000;
    public static final long ARKARIUM_NORMAL_HP = 12_600_000_000L; // wiki (GMS, 2022)
    public static final long ARKARIUM_NORMAL_FIELD = 272_020_200;
    public static final int ARKARIUM_NORMAL_BANMAP_FIELDID = 272020500; // GMS v271
    public static final int TWISTED_DARKNESS_DUPLICATE = 8860003;

    //  Pink Bean ----------------------------------------------------------------------------------------------------------
    public static final int ARIEL = 8820002;
    public static final long ARIEL_HP = 1_580_000_000L; // wiki (GMS, 2022)
    public static final int ARIEL_STATUE = 8820019;
    public static final int SOLOMON = 8820003;
    public static final long SOLOMON_HP = 800_000_000L; // wiki (GMS, 2022)
    public static final int SOLOMON_STATUE = 8820020;
    public static final int REX = 8820004;
    public static final long REX_HP = 800_000_000L; // wiki (GMS, 2022)
    public static final int REX_STATUE = 8820021;
    public static final int HUGIN = 8820005;
    public static final long HUGIN_HP = 1_185_000_000L; // wiki (GMS, 2022)
    public static final int HUGIN_STATUE = 8820022;
    public static final int MUNIN = 8820006;
    public static final long MUNIN_HP = 1_185_000_000L; // wiki (GMS, 2022)
    public static final int MUNIN_STATUE = 8820023;
    public static final int MUNIN_BANMAP_FIELDID = 270050200;

    public static final int CHAOS_ARIEL = 8820102;
    public static final long CHAOS_ARIEL_HP = 37_800_000_000L;
    public static final int CHAOS_SOLOMON = 8820103;
    public static final long CHAOS_SOLOMON_HP = 21_000_000_000L;
    public static final int CHAOS_REX = 8820104;
    public static final long CHAOS_REX_HP = 21_000_000_000L;
    public static final int CHAOS_HUGIN = 8820105;
    public static final long CHAOS_HUGIN_HP = 25_200_000_000L;
    public static final int CHAOS_MUNIN = 8820106;
    public static final long CHAOS_MUNIN_HP = 25_200_000_000L;
    public static final int CHAOS_MUNIN_BANMAP_FIELDID = 270051200;

    public static final int PINK_BEAN_TIME = 30 * 60; // 30 min
    public static final int PINK_BEAN = 8820000;
    public static final int PINK_BEAN_P2 = 8820001;
    public static final long PINK_BEAN_HP_P1 = ARIEL_HP + SOLOMON_HP + REX_HP + HUGIN_HP + MUNIN_HP;
    public static final long PINK_BEAN_HP_P2 = 2_100_000_000L; // wiki (GMS, 2022)
    public static final int PINK_BEAN_FIELD = 270050100;
    public static final int CHAOS_PINK_BEAN_P1 = 8820100;
    public static final int CHAOS_PINK_BEAN_P2 = 8820212;
    public static final long CHAOS_PINK_BEAN_HP_P1 = CHAOS_ARIEL_HP + CHAOS_SOLOMON_HP + CHAOS_REX_HP + CHAOS_HUGIN_HP + CHAOS_MUNIN_HP;
    public static final long CHAOS_PINK_BEAN_HP_P2 = 69_300_000_000L; // current GMS total for its three bodies
    public static final int CHAOS_PINK_BEAN_FIELD = 270051100;
    public static final String PINK_BEAN_CTR = "bossesKilled";

    //  Papulatus ----------------------------------------------------------------------------------------------------------
    public static final int PAPULATUS_ALARM_CLOCK_COOLTIME_MILLIS = 1000 * 60 * 2; // 2 minutes
    public static final int PAPULATUS_ALARM_CLOCK_MILLIS = 1000 * 30; // 30 seconds
    // lasers (GMS Chaos): the first pair ~10 s in, on 10-25 s, then a 2 s fade and 8-12 s off
    public static final int PAPULATUS_LASER_INIT_COOLTIME_MILLIS = 1000 * 10;
    public static final int PAPULATUS_LASER_ON_MIN_MILLIS = 1000 * 10;
    public static final int PAPULATUS_LASER_ON_MAX_MILLIS = 1000 * 25;
    public static final int PAPULATUS_LASER_OFF_MIN_MILLIS = 1000 * 8;
    public static final int PAPULATUS_LASER_OFF_MAX_MILLIS = 1000 * 12;
    public static final int PAPULATUS_TWEEZERS = 6;
    public static final int PAPULATUS_TIME = 30 * 60; // 30 minutes
    public static final int PAPULATUS_SOLO_TIMER_MOB_COUNT = 3; // 30 minutes

    public static final int PAPULATUS_EASY_P0 = 8500000;
    public static final int PAPULATUS_EASY_P1 = 8500001;
    public static final int PAPULATUS_EASY_P2 = 8500002;
    public static final int PAPULATUS_EASY_DC = 50;
    public static final int PAPULATUS_NORMAL_P0 = 8500010;
    public static final int PAPULATUS_NORMAL_P1 = 8500011;
    public static final int PAPULATUS_NORMAL_P2 = 8500012;
    public static final int PAPULATUS_NORMAL_DC = 10;
    public static final int PAPULATUS_CHAOS_P0 = 8500020;
    public static final int PAPULATUS_CHAOS_P1 = 8500021;
    public static final int PAPULATUS_CHAOS_P2 = 8500022;
    public static final int PAPULATUS_CHAOS_DC = 5;




    //  Demian -------------------------------------------------------------------------------------------------------------
    public static final int DEMIAN_TIME = 30 * 60; // 30 min
    public static final int BRAND_OF_SACRIFICE = 80001974; // Skill ID

    // Hard 8880100/8880101, Normal 8880110/8880111 (map boss tags of 350160100/350160200, Hard Damien quests 16993/36116)
    public static final int DEMIAN_NORMAL_TEMPLATE_ID_1 = 8880110;
    public static final int DEMIAN_NORMAL_TEMPLATE_ID_2 = 8880111;
    public static final int DEMIAN_HARD_TEMPLATE_ID_1 = 8880100;
    public static final int DEMIAN_HARD_TEMPLATE_ID_2 = 8880101;
    public static final int DEMIAN_SHADOW_ZONE = 8880102; // phase 2 add
    public static final int DEMIAN_FALLEN = 8880103; // what phase 2 revives into; invincible

    public static final long DEMIAN_HP_NORMAL_1 = 840_000_000_000L; // wiki (GMS, 2022)
    public static final long DEMIAN_HP_NORMAL_2 = 360_000_000_000L; // wiki (GMS, 2022)
    public static final long DEMIAN_HP_HARD_1 = 25_200_000_000_000L; // wiki (GMS, 2022)
    public static final long DEMIAN_HP_HARD_2 = 10_800_000_000_000L; // wiki (GMS, 2022)

    // Sword
    public static final int DEMIAN_SWORD_VELOCITY = 30; // default velocity
    public static final int DEMIAN_SWORD_TARGETING_VELOCITY = 60; // default velocity when targeting

    // Stigma
    public static final int DEMIAN_MAX_STIGMA = 7; // max stigma
    public static final int DEMIAN_PASSIVE_STIGMA_TIME = 30 * 1000; // Every 30 seconds, users are hit with +1 stigma
    public static final int DEMIAN_STIGMA_INCINERATE_OBJECT_RESPAWN_TIME = 20 * 1000; // Stigma Pillar spawns every 20seconds
    public static final int DEMIAN_STIGMA_INCINERATE_OBJECT_DURATION_TIME = 10 * 1000; // Stigma Pillar lasts 10 seconds


    //  Ursus --------------------------------------------------------------------------------------------------------------
    public static final int URSUS_TEMPLATE_ID = 8881000;


    //  Gollux -------------------------------------------------------------------------------------------------------------
    public static final int[][] GOLLUX_HP_MULTIPLIERS = {{1, 60, 300, 500}, {1, 10, 150, 3000}, {1, 10, 300, 30000}};
    public static final int GOLLUX_FIRST_MAP = 863010100;
    public static final int GOLLUX_RIGHT_SHOULDER = 863010330;
    public static final int GOLLUX_LEFT_SHOULDER = 863010430;
    public static final int GOLLUX_HEAD_MAP = 863010600;

    /** @return whether this is one of the maps of the Gollux fight (Road to Gollux up to the Head) */
    /**
     * Gollux himself: the head's three phases (9390600-9390602), the shoulders and the abdomen (9390610-9390612)
     * and the reward monsters. Everything else in his maps is trash, even the monsters Mob.wz flags as bosses (the
     * cores and guards of the leg, torso and arm maps, 9390630-9390640).
     */
    public static boolean isGolluxPart(int templateId) {
        return (templateId >= 9390600 && templateId <= 9390602) || (templateId >= 9390610 && templateId <= 9390612)
                || (templateId >= GOLLUX_REWARD_EASY && templateId <= GOLLUX_REWARD_HELL);
    }

    public static boolean isGolluxFightField(int fieldId) {
        return fieldId >= 863010100 && fieldId <= GOLLUX_HEAD_MAP;
    }

    /**
     * @return whether this is one of Gollux's leg, torso and arm maps: their monsters spawn once and don't come back, so
     * the map can be cleared (its portals open when its core monsters are gone)
     */
    public static boolean isGolluxNoRespawnField(int fieldId) {
        return fieldId >= 863010200 && fieldId <= GOLLUX_LEFT_SHOULDER;
    }
    public static final int GOLLUX_ABDOMEN = 863010240;
    public static final int[] GOLLUX_RIGHT_HAND_SKILLS = new int[]{3, 5, 6, 8, 10};
    public static final int[] GOLLUX_LEFT_HAND_SKILLS = new int[]{2, 4, 7, 9, 11};
    public static final int GOLLUX_BREATH_ATTACK = 1;

    //  Princess No --------------------------------------------------------------------------------------------------------
    public static final int PRINCESS_NO = 9450022;

    //  Akechi  ------------------------------------------------------------------------------------------------------------
    // 9601623 is the boss version in the v232 data (boss reward explosion, final max HP 120b); 9601622 is the story one.
    // [Maple Rewards] Defeat Normal Akechi Mitsuhide (66446) counts 9601623.
    public static final int AKECHI = 9601623;
    public static final long AKECHI_HP = 120_000_000_000L; // v232 game data (finalmaxHP); the wiki only estimates 350b
    public static final int AKECHI_ENTRANCE = 874000005; // Fort Asura Entrance
    // GMS v271 flow: cut 874004500 -> phase 1 874004550 (9601622) -> cut 874004600 -> phase 2 874004650 (9601623)
    public static final int AKECHI_FIELD = 874004500;
    public static final int AKECHI_P1_FIELD = 874004550;
    public static final int AKECHI_CUT_2 = 874004600;
    public static final int AKECHI_FIELD_2 = 874004650;
    public static final int AKECHI_P1 = 9601622;

    //  Chosen Seren ------------------------------------------------------------------------------------------------------
    public static final int SEREN_ENTRANCE = 410000670; // Cernium: Palace Main Hall
    // Index 0 Normal, 1 Hard, 2 Extreme. Each mode has its own maps (base + 20 phase 1, + 60 phase 2, + 80 treasure
    // room) and its own monsters (base + offset below), as in the v265 data.
    public static final String[] SEREN_MODE_NAMES = {"Normal", "Hard", "Extreme"};
    public static final int[] SEREN_FIELD_BASE = {410002100, 410002000, 410002200};
    public static final int[] SEREN_MOB_BASE = {8880630, 8880600, 8880650};
    public static final int SEREN_P1 = 0;
    public static final int SEREN_DAWN = 3;
    public static final int SEREN_AVATAR_OF_NEROTA = 5;
    public static final int SEREN_NEROTAS_POWER = 6;
    public static final int SEREN_MIDDAY = 7;
    public static final int SEREN_DUSK = 9;
    public static final int SEREN_TWILIGHT = 12;
    public static final int SEREN_TREASURE_CHEST = 14;
    // maplestorywiki.net (GMS): phase 1, phase 2 (one bar over the four suns)
    public static final long[][] SEREN_HP = {
            {52_500_000_000_000L, 155_400_000_000_000L},
            {126_000_000_000_000L, 357_000_000_000_000L},
            {1_320_000_000_000_000L, 5_160_000_000_000_000L}};
    public static final long[] SEREN_AVATAR_HP = {800_000_000_000L, 2_000_000_000_000L, 20_000_000_000_000L};

    /** @return the Seren mode (0 Normal, 1 Hard, 2 Extreme) whose maps hold this field, or -1 */
    public static int getSerenMode(int fieldId) {
        for (int m = 0; m < SEREN_FIELD_BASE.length; m++) {
            if (fieldId >= SEREN_FIELD_BASE[m] && fieldId <= SEREN_FIELD_BASE[m] + 80) {
                return m;
            }
        }
        return -1;
    }

    public static int serenMob(int mode, int offset) {
        return SEREN_MOB_BASE[mode] + offset;
    }

    public static int serenP1Field(int mode) {
        return SEREN_FIELD_BASE[mode] + 20;
    }

    public static int serenP2Field(int mode) {
        return SEREN_FIELD_BASE[mode] + 60;
    }

    public static int serenRewardField(int mode) {
        return SEREN_FIELD_BASE[mode] + 80;
    }

//  RanMaru ------------------------------------------------------------------------------------------------------------

    // General
    public static final int RANMARU_TIMER = 60 * 60; // 60 min (GMS)
    public static final int RANMARU_DEATHCOUNT = 10;
    public static final String RANMARU_SPAWN_KEY = "Spawned";
    public static final Position RANMARU_LEFT_WALKER_NORMAL_POSITION = new Position(-781, -169);
    public static final Position RANMARU_RIGHT_WALKER_NORMAL_POSITION = new Position(-69, -135);
    public static final Position RANMARU_TOP_WALKER_NORMAL_POSITION = new Position(-329, -298);

    // Normal
    public static final long RANMARU_NORMAL_HP = 1_000_000_000L; // 1 bil HP
    public static final int RANMARU_NORMAL_BODY = 9421581;
    public static final int RANMARU_NORMAL_DEAD_BODY = 9421582;
    public static final int RANMARU_NORMAL_MAP = 807300110;
    public static final int RANMARU_RESTRAINED_SPIRIT_WALKER_NORMAL = 9421575;
    public static final int RANMARU_HEALING_SPIRIT_WALKER_NORMAL = 9421576;

    // Hard
    public static final long RANMARU_HARD_HP = 10_500_000_000L; // 10.5 bil HP
    public static final int RANMARU_HARD_BODY_1 = 9421583;
    public static final int RANMARU_HARD_BODY_2 = 9421586;
    public static final int RANMARU_HARD_BODY_3 = 9421587;
    public static final int RANMARU_HARD_BODY_4 = 9421588;
    public static final int RANMARU_HARD_BODY_5 = 9421589;
    public static final int RANMARU_HARD_DEAD_BODY = 9421584;
    public static final int RANMARU_HARD_MAP = 807300210;
    public static final int RANMARU_RESTRAINED_SPIRIT_WALKER_HARD = 9421577;
    public static final int RANMARU_INFERNO_SPIRIT_WALKER_HARD = 9421579;
    public static final int RANMARU_HEALING_SPIRIT_WALKER_HARD = 9421578;
    public static final int RANMARU_WALL = 9421585;
    public static final Position RANMARU_LEFT_WALL_START_POSITION = new Position(-1500, 123);
    public static final Position RANMARU_RIGHT_WALL_START_POSITION = new Position(550, 123);
    public static final int RANMARU_DISTANCE_BETWEEN_WALLS = 100;
    public static final int RANMARU_WALL_MAX_COUNT = 10;


//  Dorothy ------------------------------------------------------------------------------------------------------------


    public static final long DOROTHY_HP = 85_500_000_000_000L; // 85t

//  Dorothy ------------------------------------------------------------------------------------------------------------


    public static final int GUARDIAN_ANGEL_SLIME_NORMAL = 9210052;
    public static final int GUARDIAN_ANGEL_SLIME_CHAOS = 9210051;
    // the fight itself (Ramuramu Valley): Normal level 220, Chaos level 250 (maplestorywiki.net, GMS)
    public static final int GUARDIAN_ANGEL_SLIME_ALTAR = 160000000;
    public static final int GUARDIAN_ANGEL_SLIME_NORMAL_FIELD = 160060000;
    public static final int GUARDIAN_ANGEL_SLIME_CHAOS_FIELD = 160050000;
    public static final int GUARDIAN_ANGEL_SLIME_NORMAL_MOB = 8880711;
    public static final int GUARDIAN_ANGEL_SLIME_CHAOS_MOB = 8880700;
    // Slime Treasure Chests: they appear where Guardian Angel Slime falls and hold the rewards; the Maple Rewards
    // quests (26613 Normal, 26622 Chaos) and Ramuramu's Crown for the Cutest (36016) count these.
    public static final int GUARDIAN_ANGEL_SLIME_NORMAL_CHEST = 8880726;
    public static final int GUARDIAN_ANGEL_SLIME_CHAOS_CHEST = 8880725;
    public static final long GUARDIAN_ANGEL_SLIME_CHEST_HP = 100_000L; // v271 game data: 100,000 HP, 1,000 fixed damage per hit
    public static final long GUARDIAN_ANGEL_SLIME_NORMAL_HP = 5_000_000_000_000L;
    public static final long GUARDIAN_ANGEL_SLIME_CHAOS_HP = 90_000_000_000_000L;

//  Lucid --------------------------------------------------------------------------------------------------------------

    public static final int LUCID_TIMER = 30 * 60; // 30 min timer
    public static final int LUCID_DEATHCOUNT = 10;
    public static final int LUCID_WELCOME_BARRAGE_COOLTIME = 120 * 1000; // 120 seconds

    public static final int LUCID_TEMPLATE_ID_1 = 8880140;
    public static final int LUCID_TEMPLATE_ID_2 = 8880150;

    public static final int LUCID_REWARD_BOX_EASY = 8880156;
    public static final int LUCID_REWARD_BOX_NORMAL = 8880167;
    public static final int LUCID_REWARD_BOX_HARD = 8880177;

    public static final long LUCID_HP_1 = 12_000_000_000_000L; // wiki (GMS, 2022)
    public static final long LUCID_HP_2 = 12_000_000_000_000L; // wiki (GMS, 2022)

    // Hard Lucid (Swordie Plus): its own templates, three phases (wiki, GMS 2022: 50.8t / 50.8t / 12t, 45 s phase 3)
    public static final int LUCID_HARD_TEMPLATE_ID_1 = 8880141;
    public static final int LUCID_HARD_TEMPLATE_ID_2 = 8880151;
    public static final int LUCID_HARD_TEMPLATE_ID_3 = 8880153;
    public static final long LUCID_HARD_HP_1 = 50_800_000_000_000L;
    public static final long LUCID_HARD_HP_2 = 54_000_000_000_000L; // wiki: 54t
    public static final long LUCID_HARD_HP_3 = 12_800_000_000_000L; // wiki: 10 lives of 1.28t, as one bar here
    public static final int LUCID_HARD_PHASE_3_TIME = 45; // seconds
    public static final int LUCID_FIELD = 450004100; // Normal: 450004100-450004300
    public static final int LUCID_HARD_FIELD = 450004400; // Hard: 450004400-450004600

    // Easy Lucid: two phases at half of Normal's health (maplestorywiki.net, GMS: 6t / 6t, Lv. 220+, 30 min,
    // 10 deaths), in GMS v271's map set 450003800-450003960 (entry, P1 +40, collapse +80, P2 +120, reward +160; the
    // other sets step by 50), with its own Final Music Box (8880156).
    public static final int LUCID_EASY_TEMPLATE_ID_1 = 8880142;
    public static final int LUCID_EASY_TEMPLATE_ID_2 = 8880155;
    public static final long LUCID_EASY_HP_1 = 6_000_000_000_000L;
    public static final long LUCID_EASY_HP_2 = 6_000_000_000_000L;
    public static final int LUCID_EASY_FIELD = 450003800; // Easy: 450003800-450003960

    public static boolean isLucidEasyField(int fieldId) {
        return fieldId >= 450003800 && fieldId <= 450003999;
    }

    /** From one Lucid map to the next in its difficulty's set (entry, phase 1, collapse, phase 2, reward). */
    public static int lucidStep(int fieldId) {
        return isLucidEasyField(fieldId) ? 40 : 50;
    }

    /** Lucid's first phase (the Dreaming Forest), in every difficulty's map set. */
    public static boolean isLucidPhase1Field(int fieldId) {
        return fieldId == 450004150 || fieldId == 450004450 || fieldId == 450003840;
    }

    public static boolean isLucidHardField(int fieldId) {
        return fieldId >= 450004400 && fieldId <= 450004699;
    }

    //  Will -----------------------------------------------------------------------------------------------------------

    public static final long WILL_P1_HP = 4_200_000_000_000L;
    public static final long WILL_P2_HP = 6_300_000_000_000L;
    public static final long WILL_P3_HP = 10_500_000_000_000L;

    public static final long HARD_WILL_P1_HP = 21_000_000_000_000L;
    public static final long HARD_WILL_P2_HP = 31_500_000_000_000L;
    public static final long HARD_WILL_P3_HP = 52_500_000_000_000L;

    public static final int REAL_WILL_P1 = 8880340;
    public static final int BLUE_WILL_P1 = 8880343;
    public static final int PURPLE_WILL_P1 = 8880344;
    public static final int WILL_P1_DUMMY_1 = 8880351;
    public static final int WILL_P1_DUMMY_2 = 8880355;

    public static final int WILL_P2 = 8880341;
    public static final int WILL_P2_DUMMY_1 = 8880353;
    public static final int WILL_P2_DUMMY_2 = 8880357;

    public static final int WILL_P3 = 8880342;
    public static final int WILL_P3_DUMMY_1 = 8880354;
    public static final int WILL_P3_DUMMY_2 = 8880358;

    public static final int HARD_REAL_WILL_P1 = 8880300;
    public static final int HARD_BLUE_WILL_P1 = 8880303;
    public static final int HARD_PURPLE_WILL_P1 = 8880304;
    public static final int HARD_WILL_P1_DUMMY_1 = 8880321;
    public static final int HARD_WILL_P1_DUMMY_2 = 8880325;

    public static final int HARD_WILL_P2 = 8880301;
    public static final int HARD_WILL_P2_DUMMY_1 = 8880323;
    public static final int HARD_WILL_P2_DUMMY_2 = 8880327;

    public static final int HARD_WILL_P3 = 8880302;
    public static final int HARD_WILL_P3_DUMMY_1 = 8880324;
    public static final int HARD_WILL_P3_DUMMY_2 = 8880328;

    // Easy Will (v265 data: maps with mode 3, monsters 8880360+). maplestorywiki.net HP: 5.6t over phase 1's two
    // bars, 4.2t, 7t.
    /** Will's reward: the Lost Light Mirror in Fading Light (P3 map + 30), Easy / Normal / Hard; it drops the loot. */
    public static final int[] WILL_MIRROR = {8950113, 8950114, 8950115};
    public static final int EASY_REAL_WILL_P1 = 8880360;
    public static final int EASY_WILL_P2 = 8880361;
    public static final int EASY_WILL_P3 = 8880362;
    public static final int EASY_BLUE_WILL_P1 = 8880363;
    public static final int EASY_PURPLE_WILL_P1 = 8880364;
    public static final int EASY_WILL_P1_DUMMY_1 = 8880372;
    public static final int EASY_WILL_P1_DUMMY_2 = 8880376;
    public static final int EASY_WILL_P2_DUMMY_1 = 8880374;
    public static final int EASY_WILL_P2_DUMMY_2 = 8880378;
    public static final int EASY_WILL_P3_DUMMY_1 = 8880375;
    public static final int EASY_WILL_P3_DUMMY_2 = 8880379;
    public static final long EASY_WILL_P1_HP = 2_800_000_000_000L;
    public static final long EASY_WILL_P2_HP = 4_200_000_000_000L;
    public static final long EASY_WILL_P3_HP = 7_000_000_000_000L;
    public static final int WILL_EASY_FIELD_P1 = 450007850;
    public static final int WILL_EASY_FIELD_P2 = 450007950;
    public static final int WILL_EASY_FIELD_P3 = 450008050;

    /** Index 0 Easy, 1 Normal, 2 Hard; then phase 1-3 (index 0-2). */
    public static final int[][] WILL_FIELDS = {
            {WILL_EASY_FIELD_P1, WILL_EASY_FIELD_P2, WILL_EASY_FIELD_P3},
            {450008750, 450008850, 450008950},
            {450008150, 450008250, 450008350}};
    public static final String[] WILL_MODE_NAMES = {"Easy", "Normal", "Hard"};

    /** @return 0 Easy, 1 Normal, 2 Hard for a Will fight map, else -1 */
    public static int getWillMode(int fieldId) {
        for (int m = 0; m < WILL_FIELDS.length; m++) {
            for (int f : WILL_FIELDS[m]) {
                if (f == fieldId) {
                    return m;
                }
            }
        }
        return -1;
    }

    /** @return 1-3 for a Will fight map, else 0 */
    public static int getWillPhase(int fieldId) {
        for (int[] fields : WILL_FIELDS) {
            for (int p = 0; p < fields.length; p++) {
                if (fields[p] == fieldId) {
                    return p + 1;
                }
            }
        }
        return 0;
    }

    /** Will's monsters by mode (0 Easy, 1 Normal, 2 Hard). */
    public static int willP1(int mode) {
        return new int[]{EASY_REAL_WILL_P1, REAL_WILL_P1, HARD_REAL_WILL_P1}[mode];
    }

    public static int willBlueP1(int mode) {
        return new int[]{EASY_BLUE_WILL_P1, BLUE_WILL_P1, HARD_BLUE_WILL_P1}[mode];
    }

    public static int willPurpleP1(int mode) {
        return new int[]{EASY_PURPLE_WILL_P1, PURPLE_WILL_P1, HARD_PURPLE_WILL_P1}[mode];
    }

    public static int willP2(int mode) {
        return new int[]{EASY_WILL_P2, WILL_P2, HARD_WILL_P2}[mode];
    }

    public static int willP3(int mode) {
        return new int[]{EASY_WILL_P3, WILL_P3, HARD_WILL_P3}[mode];
    }

    /** The invisible helper monsters of a phase (1-3): dummy 1 (attacks from the sky) and dummy 2 (bullets). */
    public static int willDummy(int mode, int phase, int which) {
        int[][][] d = {
                {{EASY_WILL_P1_DUMMY_1, EASY_WILL_P1_DUMMY_2}, {EASY_WILL_P2_DUMMY_1, EASY_WILL_P2_DUMMY_2}, {EASY_WILL_P3_DUMMY_1, EASY_WILL_P3_DUMMY_2}},
                {{WILL_P1_DUMMY_1, WILL_P1_DUMMY_2}, {WILL_P2_DUMMY_1, WILL_P2_DUMMY_2}, {WILL_P3_DUMMY_1, WILL_P3_DUMMY_2}},
                {{HARD_WILL_P1_DUMMY_1, HARD_WILL_P1_DUMMY_2}, {HARD_WILL_P2_DUMMY_1, HARD_WILL_P2_DUMMY_2}, {HARD_WILL_P3_DUMMY_1, HARD_WILL_P3_DUMMY_2}}};
        return d[mode][phase - 1][which - 1];
    }

    public static long willHp(int mode, int phase) {
        long[][] hp = {{EASY_WILL_P1_HP, EASY_WILL_P2_HP, EASY_WILL_P3_HP},
                {WILL_P1_HP, WILL_P2_HP, WILL_P3_HP},
                {HARD_WILL_P1_HP, HARD_WILL_P2_HP, HARD_WILL_P3_HP}};
        return hp[mode][phase - 1];
    }

    public static final int BEHOLDERS_EYE_BLUE = 8880305;
    public static final int BEHOLDERS_EYE_PURPLE = 8880307;

    public static final long WILL_P2_MOONLIGHT_TIME = 7000; // moonlight lifts the phase 2 healing block for 7 s

    public static final int WILL_FIELD_P1 = 450008750;
    public static final int WILL_FIELD_P2 = 450008850;
    public static final int WILL_FIELD_P3 = 450008950;

    public static final int WILL_HARD_FIELD_P1 = 450008150;
    public static final int WILL_HARD_FIELD_P2 = 450008250;
    public static final int WILL_HARD_FIELD_P3 = 450008350;

    public static final String WILL_FIELD_SAFE_KEY = "safe";
    public static final String WILL_FIELD_FAIL_KEY = "fail";
    public static final String WILL_FIELD_P1_HIT_OA_KEY = "oaCd";
    public static final String WILL_FIELD_P2_REFLECTED = "reflected";

    public static final int WILL_INFECTION_DAMAGE_CHECK_TIME = 7; // seconds
    public static final int WILL_INFECTION_INITIAL_DELAY = 2000; // millis
    public static final int WILL_INFECTION_DELAY = 1000; // millis
    public static final int WILL_INFECTION_TIME = 15; // seconds

    // Verus Hilla -----------------------------------------------------------------------------------------------------
    public static final int VERUS_HILLA = 8880450; // Normal (BossParty.img clear mob); 8880405 is the old Normal set
    public static final long VERUS_HILLA_HP = 89_250_000_000_000L; // Normal, maplestorywiki.net (GMS)
    public static final int VERUS_HILLA_HARD = 8880410;
    public static final long VERUS_HILLA_HARD_HP = 178_500_000_000_000L; // maplestorywiki.net (GMS)
    /** Instance property set by the entry portal for the Hard fight (Normal and Hard share the map). */
    public static final String HARD_MODE_KEY = "hardMode";

    //  Kalos the Guardian (Karote: Summit) -------------------------------------------------------------------------
    // Index 0 Easy, 1 Normal, 2 Chaos, 3 Extreme. Level 265, 30 minutes, death count 8 (maplestorywiki.net, GMS).
    public static final String KALOS_MODE_KEY = "kalosMode";
    public static final int KALOS_LOBBY = 410005005;
    public static final String[] KALOS_MODE_NAMES = {"Easy", "Normal", "Chaos", "Extreme"};
    public static final int[] KALOS_P1_FIELDS = {410006220, 410006020, 410006320, 410006420};
    public static final int[] KALOS_P2_FIELDS = {410006260, 410006060, 410006360, 410006460};
    public static final int[] KALOS_REWARD_FIELDS = {410006280, 410006080, 410006380, 410006480};
    public static final int[] KALOS_P1 = {8881010, 8880800, 8881030, 8881050};
    public static final int[] KALOS_P1_REVIVE = {8881020, 8880811, 8881040, 8881060};
    // phase 2: the body holds the HP (and the bar); its four forms (wings, armguard) take the hits for it
    public static final int[] KALOS_P2_BODY = {8881012, 8880802, 8881032, 8881052};
    public static final int[] KALOS_P2_FIRST_FORM = {8881013, 8880803, 8881033, 8881053}; // then +1, +2, +3
    public static final int[] KALOS_CHEST = {8881018, 8880808, 8881038, 8881058};
    public static final long[] KALOS_P1_HP = {94_500_000_000_000L, 336_000_000_000_000L, 1_066_000_000_000_000L,
            5_798_000_000_000_000L};
    public static final long[] KALOS_P2_HP = {262_500_000_000_000L, 720_000_000_000_000L, 4_060_000_000_000_000L,
            15_498_000_000_000_000L};
    public static final int KALOS_REQ_LEVEL = 265;

    //  Kaling (Shangri-La: Four Seasons Pavilion) -----------------------------------------------------------------
    // Index 0 Easy, 1 Normal, 2 Hard, 3 Extreme. Level 275, 30 minutes, Willpower 1000 (maplestorywiki.net, GMS).
    public static final String KALING_MODE_KEY = "kalingMode";
    public static final int KALING_LOBBY = 410007025;
    public static final String[] KALING_MODE_NAMES = {"Easy", "Normal", "Hard", "Extreme"};
    // stages: 0 Qiongqi (Spring), 1 Taowu (Summer), 2 Hundun (Autumn), 3 Kaling, 4 Frenzied Kaling, 5 treasure
    public static final int[][] KALING_FIELDS = {
            {410008040, 410008080, 410008120, 410008160, 410008200, 410008220},
            {410007140, 410007180, 410007220, 410007260, 410007300, 410007320},
            {410008340, 410008380, 410008420, 410008460, 410008500, 410008520},
            {410008640, 410008680, 410008720, 410008760, 410008800, 410008820}};
    // first mob id of each difficulty: Qiongqi +0, Taowu +1, Hundun +2, Kaling +7, Death-Gripped Perils +9..+11,
    // Frenzied Kaling +12, Peace Blooms Again (treasure) +15
    public static final int[] KALING_BASE = {8880900, 8880830, 8880930, 8880960};
    public static final int KALING_P2 = 7, KALING_DEATH_GRIPPED = 9, KALING_FRENZIED = 12, KALING_CHEST = 15;
    public static final long[] KALING_PERIL_HP = {96_600_000_000_000L, 399_000_000_000_000L, 920_000_000_000_000L,
            6_018_000_000_000_000L};
    public static final long[] KALING_P2_HP = {105_000_000_000_000L, 468_000_000_000_000L, 1_404_000_000_000_000L,
            6_930_000_000_000_000L};
    public static final long[] KALING_P3_HP = {73_750_000_000_000L, 313_200_000_000_000L, 1_179_000_000_000_000L,
            4_404_600_000_000_000L};
    public static final long[] KALING_DEATH_GRIPPED_HP = {63_200_000_000_000L, 270_700_000_000_000L,
            1_360_000_000_000_000L, 3_523_900_000_000_000L};
    public static final int KALING_REQ_LEVEL = 275;

    //  First Adversary (Fate's Battleground: From Whence Will Flows) ---------------------------------------------
    // Index 0 Easy, 1 Normal, 2 Hard, 3 Extreme. Level 270, 30 minutes, 1-3 players, Adversarial Will 1000 per
    // player, 200 a death (maplestorywiki.net, GMS; HP marked as estimates there).
    public static final String ADVERSARY_MODE_KEY = "adversaryMode";
    public static final int ADVERSARY_LOBBY = 410007019;
    public static final String[] ADVERSARY_MODE_NAMES = {"Easy", "Normal", "Hard", "Extreme"};
    public static final int[] ADVERSARY_FIELD_BASE = {410020000, 410021000, 410022000, 410023000}; // +100/+300/+500, +600
    // Adversary's Valor +0, Adversary's Conviction +5, First Adversary +10, World Heart (treasure) +15
    public static final int[] ADVERSARY_BASE = {8881700, 8881730, 8881760, 8881790};
    public static final long[][] ADVERSARY_HP = {
            {171_000_000_000_000L, 171_000_000_000_000L, 228_000_000_000_000L},
            {495_000_000_000_000L, 495_000_000_000_000L, 660_000_000_000_000L},
            {3_135_000_000_000_000L, 3_135_000_000_000_000L, 4_180_000_000_000_000L},
            {9_655_000_000_000_000L, 9_655_000_000_000_000L, 12_870_000_000_000_000L}};
    public static final int ADVERSARY_REQ_LEVEL = 270;

    //  Limbo (Carcion: Temple of Tears) -----------------------------------------------------------------------------
    // Index 0 Normal, 1 Hard. Level 285, 30 minutes, 1-3 players, no death count (Erosion) (maplestorywiki.net, GMS).
    public static final String LIMBO_MODE_KEY = "limboMode";
    public static final int LIMBO_LOBBY = 410007601;
    public static final String[] LIMBO_MODE_NAMES = {"Normal", "Hard"};
    public static final int[] LIMBO_FIELD_BASE = {410009000, 410011000}; // +100, +300, +500, +700, treasure +800
    // Specter A +0, B +2, Test Subject AB +6 (phase 1 HP), Specter C +13, D +15, Limbo Enlightened by Dark Truth +20,
    // by Light Truth +25, by Truth +32 (phase 3 HP), Paradoxical Truth (treasure) +34, Test Subject CD +35 (phase 2 HP)
    public static final int[] LIMBO_BASE = {8881300, 8881350};
    public static final long[] LIMBO_P1_HP = {1_944_000_000_000_000L, 3_774_000_000_000_000L};
    public static final long[] LIMBO_P2_HALF_HP = {972_000_000_000_000L, 1_887_000_000_000_000L};
    public static final long[] LIMBO_P3_HP = {2_592_000_000_000_000L, 4_884_000_000_000_000L};
    public static final int LIMBO_REQ_LEVEL = 285;

    //  Baldrix (Tallahart: Phantasmal Night Sanctum) ----------------------------------------------------------------
    // Index 0 Normal, 1 Hard. Level 290, 30 minutes, 1-3 players, shared Magic gauge (a death adds 200/100/70 by
    // party size, 1000 fails) (maplestorywiki.net, GMS).
    public static final String BALDRIX_MODE_KEY = "baldrixMode";
    public static final int BALDRIX_LOBBY = 410013608;
    public static final String[] BALDRIX_MODE_NAMES = {"Normal", "Hard"};
    // phase 1, phase 2, phase 3, treasure
    public static final int[][] BALDRIX_FIELDS = {{410014100, 410014500, 410014900, 410015000},
            {410015200, 410015600, 410016000, 410016100}};
    // Baldrix +1, Magic-assimilating Baldrix +14, Godslayer Baldrix +21, Concentrated Magical Essence (treasure) +24
    public static final int[] BALDRIX_BASE = {8881600, 8881650};
    public static final long[][] BALDRIX_HP = {
            {2_379_000_000_000_000L, 2_531_000_000_000_000L, 4_145_000_000_000_000L},
            {5_340_000_000_000_000L, 5_688_000_000_000_000L, 9_072_000_000_000_000L}};
    public static final int BALDRIX_REQ_LEVEL = 290;

    //  Malefic Star (Dark Sea: Temple of the Radiant Star) ---------------------------------------------------------
    // Index 0 Normal, 1 Hard (BossParty.img 37). Level 280, 30 minutes, 1-3 players, death count 5, one clear a week
    // (maplestorywiki.net, GMS). One HP for three phases (80% / 40%); the party moves between the Banquet Hall
    // (reality) and the Unglamoured Ruins (illusion) every 220 seconds.
    public static final String MALEFIC_MODE_KEY = "maleficMode";
    public static final int MALEFIC_LOBBY = 410007670;
    public static final String[] MALEFIC_MODE_NAMES = {"Normal", "Hard"};
    // reality, illusion, reward (Illusion's Rest)
    public static final int[][] MALEFIC_FIELDS = {{410025100, 410025200, 410025300}, {410026100, 410026200, 410026300}};
    // Malefic Star +0, Crooked Scale (reward) +4
    public static final int[] MALEFIC_BASE = {8881900, 8881950};
    public static final int MALEFIC_CHEST = 4;
    public static final long[] MALEFIC_HP = {3_288_000_000_000_000L, 14_740_000_000_000_000L};
    public static final int MALEFIC_REQ_LEVEL = 280;

    //  Jupiter (Geardock: Forge Entrance) --------------------------------------------------------------------------
    // Index 0 Normal, 1 Hard (BossParty.img 38). Level 295, 30 minutes, 1-3 players, one clear a week
    // (maplestorywiki.net, GMS). One map with three areas (Jupiter above, the Alchemical Entity below, the merged
    // forms in the middle); one HP pool (holder +0), phases at 79.9% and 49.9%.
    public static final String JUPITER_MODE_KEY = "jupiterMode";
    public static final int JUPITER_LOBBY = 410013709;
    public static final String[] JUPITER_MODE_NAMES = {"Normal", "Hard"};
    // fight, reward (Silent Forge)
    public static final int[][] JUPITER_FIELDS = {{410027100, 410027200}, {410028100, 410028200}};
    // HP holder +0, Jupiter +1, Alchemical Entity +4, Auspicious Jupiter +6, Balanced Jupiter +9, Ruinous Jupiter +11,
    // Alchemical Remnant (reward) +13
    public static final int[] JUPITER_BASE = {8882000, 8882050};
    public static final int JUPITER_CHEST = 13;
    // total 10,266 / 49,400 trillion, held by one mob (the three parts are only summed)
    public static final long[][] JUPITER_HP = {
            {3_079_800_000_000_000L, 3_079_800_000_000_000L, 4_106_400_000_000_000L},
            {14_820_000_000_000_000L, 14_820_000_000_000_000L, 19_760_000_000_000_000L}};
    public static final int JUPITER_REQ_LEVEL = 295;

    //  Kai (Nexus Tower Delta 3: Elevator No. 14) ------------------------------------------------------------------
    // Index 0 Normal, 1 Hard (BossParty.img 36, Season Boss). Level 270 / 280, solo only, 20 minutes, death count
    // 5, one clear a week (maplestorywiki.net, GMS; HP marked as estimates there).
    public static final String KAI_MODE_KEY = "kaiMode";
    public static final int KAI_LOBBY = 993277350;
    public static final String[] KAI_MODE_NAMES = {"Normal", "Hard"};
    public static final int[] KAI_REQ_LEVEL = {270, 280};
    // fight (Fl. 88 Private Hall), reward (Top Floor Terrace)
    public static final int[][] KAI_FIELDS = {{993277600, 993277800}, {993278200, 993278400}};
    // Kai +0, Transcended Kai +4, Chrono Module Type-F (reward) +8
    public static final int[] KAI_BASE = {8881860, 8881870};
    public static final int KAI_CHEST = 8;
    public static final long[] KAI_HP = {63_000_000_000_000L, 241_500_000_000_000L}; // each phase
    // BossParty.img seasonBossReward: Golden Meso Pouch x30 / x60 and 10,000,000 mesos
    public static final int KAI_REWARD_ITEM = 2024309;
    public static final int[] KAI_REWARD_COUNT = {30, 60};
    public static final long KAI_REWARD_MESO = 10_000_000L;

    public static int[] chestsOf(int[] bases, int offset) {
        int[] res = new int[bases.length];
        for (int i = 0; i < bases.length; i++) {
            res[i] = bases[i] + offset;
        }
        return res;
    }

    /** The difficulty of a fight whose maps all lie in base .. base + 999, or -1. */
    public static int modeByFieldBase(int[] bases, int fieldId) {
        for (int i = 0; i < bases.length; i++) {
            if (fieldId >= bases[i] && fieldId < bases[i] + 1000) {
                return i;
            }
        }
        return -1;
    }

    /** {difficulty, stage} of a Kaling field, or null. */
    public static int[] getKalingStage(int fieldId) {
        for (int m = 0; m < 4; m++) {
            for (int s = 0; s < 6; s++) {
                if (KALING_FIELDS[m][s] == fieldId) {
                    return new int[]{m, s};
                }
            }
        }
        return null;
    }

    public static int[] getKalingChests() {
        int[] res = new int[4];
        for (int m = 0; m < 4; m++) {
            res[m] = KALING_BASE[m] + KALING_CHEST;
        }
        return res;
    }

    /** The Kalos difficulty (0-3) of a Kalos field, or -1. */
    public static int getKalosMode(int fieldId) {
        for (int i = 0; i < 4; i++) {
            if (KALOS_P1_FIELDS[i] == fieldId || KALOS_P2_FIELDS[i] == fieldId || KALOS_REWARD_FIELDS[i] == fieldId) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isP2Will(int templateId) {
        return templateId == WILL_P2 || templateId == HARD_WILL_P2;
    }

    // Gloom -----------------------------------------------------------------------------------------------------------
    public static final int GLOOM = 8644650;
    public static final int GLOOM_FIELD = 450009400;
    public static final long GLOOM_HP = 25_410_000_000_000L; // maplestorywiki.net (GMS)

    public static final int GLOOM_HARD = 8644655; // Chaos Gloom (its illusions are 8644656/8644657)
    public static final int GLOOM_HARD_FIELD = 450009450;
    public static final long GLOOM_HARD_HP = 127_050_000_000_000L; // maplestorywiki.net (GMS)
    public static final int GLOOM_TENTACLE = 8644658; // Needs to be spawned otherwise tentacle attack won't do
    //Not sure if this is right ID
    public static final int GLOOM_CHAOS = 9210046;

    // Darknell --------------------------------------------------------------------------------------------------------
    public static final int DARKNELL_FIELD = 450012210; // GMS v271 Normal (Hard: 450012600)
    public static final int DARKNELL_NORMAL = 8645009;
    public static final long DARKNELL_HP = 26_250_000_000_000L; // maplestorywiki.net (GMS)

    public static final int DARKNELL_HARD_FIELD = 450012600;
    public static final long DARKNELL_HARD_HP = 157_500_000_000_000L; // maplestorywiki.net (GMS)
    public static final int DARKNELL_HARD = 8645066;
    public static final int DARKNELL_BEAM = 8645068;

    // Black Mage ------------------------------------------------------------------------------------------------------
    public static final int GENESIS_CRUX = 8880518;

    // Seren ------------------------------------------------------------------------------------------------------
    public static final int CHOSEN_SEREN_TREASURE_CHEST = 8880615;

    // Gigatoad ------------------------------------------------------------------------------------------------------
    public static final int GIGATOAD = 9601327;
    public static final int FRENZIED_GIGATOAD = 9601328;

    // OMNI_CLN ------------------------------------------------------------------------------------------------------
    public static final int OMNI_CLN = 8880200;

    // Julieta ------------------------------------------------------------------------------------------------------
    public static final int JULIETA_BOX = 9402282;

    // Official HP ----------------------------------------------------------------------------------------------------
    /**
     * Boss parts whose HP in this client's data differs from the official servers (maplestorywiki.net and
     * strategywiki.org, GMS). Applied when the mob is created (MobData#getMobDeepCopyById), so every way it spawns
     * (scripts, reactors, the map itself) gets it; a script that passes its own HP still overrides it.
     */
    private static final java.util.Map<Integer, Long> OFFICIAL_MAX_HP = new java.util.HashMap<>();

    static {
        // Chaos Zakum: body 84b, each arm 10.5b (the data has 2.1b)
        OFFICIAL_MAX_HP.put(8800102, 84_000_000_000L);
        for (int arm = 8800103; arm <= 8800110; arm++) {
            OFFICIAL_MAX_HP.put(arm, 10_500_000_000L);
        }
        // Chaos Horntail: every part has twice the HP in the data (heads 3.3b, middle head 3.9b, hands 2.3b,
        // wings 2.7b, legs 1.3b, tail 900m), the preliminary heads too; Horntail itself holds the parts' total (20b)
        OFFICIAL_MAX_HP.put(8810100, 3_300_000_000L);
        OFFICIAL_MAX_HP.put(8810101, 3_300_000_000L);
        OFFICIAL_MAX_HP.put(8810102, 3_300_000_000L);
        OFFICIAL_MAX_HP.put(8810103, 3_900_000_000L);
        OFFICIAL_MAX_HP.put(8810104, 3_300_000_000L);
        OFFICIAL_MAX_HP.put(8810105, 2_300_000_000L);
        OFFICIAL_MAX_HP.put(8810106, 2_300_000_000L);
        OFFICIAL_MAX_HP.put(8810107, 2_700_000_000L);
        OFFICIAL_MAX_HP.put(8810108, 1_300_000_000L);
        OFFICIAL_MAX_HP.put(8810109, 900_000_000L);
        OFFICIAL_MAX_HP.put(8810118, 20_000_000_000L);
        OFFICIAL_MAX_HP.put(8810122, 20_000_000_000L);
        // Papulatus Normal (clock 12.6b, Papulatus 4.2b) and Chaos (378b, 126b); the data has 2.1b and 504b
        OFFICIAL_MAX_HP.put(8500011, 12_600_000_000L);
        OFFICIAL_MAX_HP.put(8500012, 4_200_000_000L);
        OFFICIAL_MAX_HP.put(8500021, 378_000_000_000L);
        OFFICIAL_MAX_HP.put(8500022, 126_000_000_000L);
        // Von Leon Normal: 6.3b (the data has 3.15b)
        OFFICIAL_MAX_HP.put(8840000, 6_300_000_000L);
        OFFICIAL_MAX_HP.put(8840014, VON_LEON_HARD_HP); // Hard Von Leon (the WZ value is 2.1b)
        // Princess No (Normal): 500b (the data has 200b)
        OFFICIAL_MAX_HP.put(9450022, 500_000_000_000L);
        // Normal Arkarium: 12.6b (the data has 2.1b, the same as Easy); set on the mob itself so every way it spawns gets it, not only the altar's script
        OFFICIAL_MAX_HP.put(ARKARIUM_NORMAL, ARKARIUM_NORMAL_HP);
    }

    /**
     * @return the official max HP of a boss part whose client data differs, or 0 to keep the data's
     */
    public static long getOfficialMaxHp(int templateId) {
        return OFFICIAL_MAX_HP.getOrDefault(templateId, 0L);
    }

    // content rates moved here from their helpers (this server's values)
    /** Pierre: % chance a hat spawns in his pattern */
    public static final int PIERRE_HAT_SPAWN_CHANCE = 75;
}

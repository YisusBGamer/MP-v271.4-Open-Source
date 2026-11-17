package net.swordie.ms.constants;

import net.swordie.ms.client.character.quest.Quest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sjonnie
 * Created on 8/19/2018.
 */
public class QuestConstants {
    public static final int DEATH_INFO = 100716; // decDropR=X;decExpR=Y;

    public static final int WILD_HUNTER_JAGUAR_STORAGE_ID = 23008;
    public static final int WILD_HUNTER_JAGUAR_CHOSEN_ID = 23009;
    public static final int FIFTH_JOB_QUEST = 1465;
    public static final int A_DIVINE_POWER = 1484; // for auth symbols
    public static final int NODESHARD_COUNT = 1477;
    public static final int DIMENSION_LIBRARY = 32600;
    public static final int SKILL_COMMAND_LOCK_QUEST_ID_2 = 1544;
    public static final int SKILL_COMMAND_LOCK_QUEST_ID = 21770;
    public static final int HO_YOUNG_SHAPESHIFT_QUEST_ID = 7786;
    public static final int DAMAGE_SKIN = 7291;
    public static final int TOWER_CHAIR = 7266;
    // game settings window (USER_QUICK_SLOT_MODIFIED), quest-ex records as in the reference server
    public static final int GAME_SETTING_1 = 368;
    public static final int GAME_SETTING_2 = 369;
    public static final int GAME_SETTING_3 = 370;
    public static final int OVERAL_SETTING = 101563;
    public static final String OVERAL_SETTING_DEFAULT = "acpHP=0;alFol=1;chAl=1;alAl=1;alPa=1;alExch=1;alHome=1;soErUI=0;alMe=1;bufFav=0;chFr=1;alFr=1;bufAual=1;alMapT=1;chGu=1;frOnNot=1;flHP=19;alGu=1;bufMin=0;soulUI=1;alWh=1;flMP=19;debufMin=0";
    public static final int QUICK_SLOT_SETTING = 101790;
    public static final String QUICK_SLOT_SETTING_DEFAULT = "qsLock=0;qsLeft=1;petHP=50;qsCol=16;petMP=50;qsMin=0;qmMin=0";
    public static final int SOUL_EFFECT = 26535; // effect=X  |  X = 0 or 1
    public static final int TITLE_MEDAL_SHOW = 101149; // 1007=medal on/off; 1009=title on/off

    // [Custom] Screen Effects (Like Kaiser Flame effect or Luminous Chain effect)
    public static final int SW_CUSTOM_EFFECT = 999_999; // NoEffect=1 -> disabled | anything else -> enabled
    public static final int SW_PHANTOM_LOADOUT_PRESET = 999_998; // NoEffect=1 -> disabled | anything else -> enabled
    public static final int SW_DEMON_SLAYER_DF_ORBS = 999_997; // NoEffect=1 -> disabled | anything else -> enabled

    // Dojo
    public static final int DOJO_FLOOR_REACHED = 100466; // Floor=X

    //Daily Quests
    public static final int VANISHING_JOURNEY_WEEKLY_AMOUNT = 8;
    public static final int VANISHING_JOURNEY_COUNT_QUEST = 34127; //count=x
    public static final int VANISHING_JOURNEY_DAILY_QUEST = 34129;

    public static final int CHU_CHU_WEEKLY_AMOUNT = 8;
    public static final int CHU_CHU_COUNT_QUEST = 39016; //count=x
    public static final int CHU_CHU_DAILY_QUEST = 39014;

    public static final int LACHELEIN_WEEKLY_AMOUNT = 8;
    public static final int LACHELEIN_COUNT_QUEST = 34380; //count=x
    public static final int LACHELEIN_DAILY_QUEST = 34378;

    public static final int ARCANA_WEEKLY_AMOUNT = 8;
    public static final int ARCANA_COUNT_QUEST = 39037; //count=x
    public static final int ARCANA_DAILY_QUEST = 39035;

    public static final int MORASS_WEEKLY_AMOUNT = 8;
    public static final int MORASS_COUNT_QUEST = 34298; //count=x
    public static final int MORASS_DAILY_QUEST = 34275;

    public static final int ESFERA_WEEKLY_AMOUNT = 8;
    public static final int ESFERA_COUNT_QUEST = 34775; //count=x
    public static final int ESFERA_DAILY_QUEST = 34773;

    // v265 region dailies (Vanishing Journey 38450 .. Limina 38476): picked up again every day
    public static final int REGION_DAILY_QUEST_FIRST = 38450;
    public static final int REGION_DAILY_QUEST_LAST = 38476;
    // Grandis dailies (Cernium 19020 .. Geardock 19048 in v271, each a start quest and three tiers)
    public static final int GRANDIS_DAILY_QUEST_FIRST = 19020;
    public static final int GRANDIS_DAILY_QUEST_LAST = 19051;

    public static final int CERNIUM_WEEKLY_AMOUNT = 8;
    public static final int CERNIUM_DAILY_QUEST = 39819;

    public static final int HOTEL_ARCUS_WEEKLY_AMOUNT = 6;
    public static final int HOTEL_ARCUS_DAILY_QUEST = 38150;




    // Shade
    public static final int SPIRIT_BOND_MAX_QUEST_ID = 1542;

    // Mechanic
    public static final int MECH_SUIT_COLOUR = 7825; // hue=X

    // Kaiser
    public static final int KAISER_MORPH_COLOUR = 12860; // inner=X;extern=Y;premium=Z;

    // Zero
    public static final int ZERO_LAST_QUEST = 40914; // required to be able to start other quests
    public static final int ZERO_WEAPON_GRADE = 41901; // grade=X;broken=Y  |  Y = 0 or 1
    public static final int LAPIS_LAZULI = 40905; // Allows for opening the ZeroWeaponUI
    public static final int EGO_EQUIP_TALK_REQUEST = 40902; // number=X

    // Union
    public static final int UNION_START_QUEST = 16013;
    public static final int UNION_MAP = 1601400; // qrKey 16014
    public static final int UNION_COIN = 500629; // lastTime=220422040404;point=x
    public static final int UNION_RANK = 18771; // rank=XXX (101 is default, check Etc.wz/mapleUnion/unionRank. 1st digit is top node, 3rd is child node, 2nd digit always seems to be empty.
    public static final int UNION_RAID_COIN = 18098; // lastTime=YYMMDDHHMMSS;coin=x (unclaimed raid coins; client 0x1444a3fe0, 0x142e504b0)
    public static final int UNION_RAID = 18790; // mod=x;lastTime=YYMMDDHHMMSS;damage=x;coin=x idk what mob is
    public static final int UNION_RAID_BOSS = 18792; // mobDefenceHP=X;mobType=Y;mobNormalHP=Z;
    public static final int UNION_QUEST = 18793; // q0=0;q1=0;pq=0;q2=0;q1Date=YY/MM/DD;pqDate=YY/MM/DD;q2Date=YY/MM/DD
    public static final int UNION_SYNERGY_BOARD = 18791; // X=Y; repeated 8 times, 0 <= x & y <= 7
    public static final int UNION_PRESET = 500630; // presetNo=n, 0~4
    public static final int WHIP_THE_WHELPS = 16011;
    public static final int DEFEAT_GOLDEN_WYVERN = 16012;

    // Dailies
    public static final int DAILIES_QUEST = 9_999_998; // DailyTypeVal=LastUpdate

    //Power Crystal
    public static final int POWER_CRYSTAL_QUEST = 66449; // probably 501619 questID for v234 count=x;time=x;max=x;type=x (count=179;time=20220725;max=180;type=2)
    public static final int POWER_CRYSTAL_WEEKLY_COUNT = 180;



    // Mini-Games
    public static final int CASTLE_COLUMN_QUEST = 16209; // Point=X;Score=Y


    // Silent Crusade
    public static final int SILENT_CRUSADE_WANTED_TAB_1 = 1648;
    public static final int SILENT_CRUSADE_WANTED_TAB_2 = 1649;
    public static final int SILENT_CRUSADE_WANTED_TAB_3 = 1650;
    public static final int SILENT_CRUSADE_WANTED_TAB_4 = 1651;

    public static final int MEDAL_REISSUE_QUEST = 29949;
    public static final int CASH_SHOP_TUTORIAL = 61646;

    // Voyage
    public static final int VOYAGE_PRE_QUEST = 17614;
    public static final int VOYAGES = 17018; // 0=X;1=Y;2=Z; | where key is routeId
    public static final int VOYAGES_TOTAL = 17932; // Near=X | where X is the total amount of voyages done.

    // Familiar
    public static final int FAMILIAR_POINTS = 65427; // point=x
    public static final int FAMILIAR_SLOT1 = 65456; // slotX=1  |  famimax=1
    public static final int FAMILIAR_SLOT2 = 64996;

    // Tower Of Oz
    public static final int TOWER_OF_OZ_PILL_SLOT = 42000; // slot1=1;slot2=0;slot3=-1  | 1=filled, -1=locked 0=empty
    public static final int TOWER_OF_OZ_DAILY_CLEARS = 42001; // tc=TODAY_CLEARS;c=MAX_CLEARS(200);d=DATE(YY/MM/DD);
    public static final int TOWER_OF_OZ_REWARD = 42002; // reward=1;Scr=0;f=0;t=99999999
    public static final int OZ_POINTS = 42003; // point=X
    public static final int TOWER_OF_OZ_TIMER = 42011; // time=MS;type=1
    public static final int TOWER_OF_OZ_TIMER_START = 42012; // timeAtStart=SECONDS
    public static final int TOWER_OF_OZ_CLEAR_INFO_1 = 42017; // 1=0 ~ 10=0 | Floors that are cleared. 0=not, 1=cleared
    public static final int TOWER_OF_OZ_CLEAR_INFO_2 = 42018; // 11=0 ~ 20=0 | Floors that are cleared. 0=not, 1=cleared
    public static final int TOWER_OF_OZ_CLEAR_INFO_3 = 42019; // 21=0 ~ 30=0 | Floors that are cleared. 0=not, 1=cleared
    public static final int TOWER_OF_OZ_CLEAR_INFO_4 = 42020; // 40=0;31=0;32=0;33=0;34=0;35=0;36=0;37=0;38=0;39=0;24_Q1=0;24_Q2=0;24_Q3=0;24_Q4=0
    public static final int TOWER_OF_OZ_CLEAR_INFO_5 = 42021; // 41=0 ~ 50=0 | Floors that are cleared. 0=not, 1=cleared

    public static final int TOWER_OF_OZ_TIME_INFO = 42109; // ht=120;rt=720

    // World Map Bookmark
    public static final int WORLD_MAP_BOOKMARK_1_14 = 7697; // idx=fieldId {0~14}
    public static final int WORLD_MAP_BOOKMARK_15_29 = 7698; // idx=fieldId {15-29}

    // Surprise Mission
    public static final int SURPRISE_MISSION_QUEST_ID = 49000; // count=0;Quest=49013;day=153795;QET=20220129091858;state=1

    // Custom Title
    public static final int ACTIVE_TITLE_QUEST_ID = 9_999_999; // i=
    public static final int CUSTOM_TITLE_QUEST_ID = 65503; // Look=3700625;expire=2021/10/14 11:29:00:000;Stat=3700625;date=2021/10/14 02:39:17:855;Title=MVP Red

    // Boss prequests
    // TODO: Lotus/Damien quests
    public static final int PREQ_URSUS = 33565;
    public static final int PREQ_EASY_MAGNUS = 31851;
    public static final int PREQ_MAGNUS = 31833;
    public static final int PREQ_MAGNUS_HELISEUM = 38858; // [Heliseum] The Final Battle, v265's Magnus prequest
    public static final int PREQ_OMNI_CLN = 3496;
    public static final int PREQ_PAPULATUS = 3470;
    public static final int PREQ_CRA = 30007;
    public static final int PREQ_VON_LON_1 = 1562; // [Lion King's Castle] A Frozen Heart
    public static final int PREQ_VON_LON_2 = 3170;
    public static final int PREQ_HORNTAIL = 7313;
    public static final int PREQ_ARKARIUM = 31179;
    public static final int PREQ_PINK_BEAN = 3521;
    public static final int PREQ_CYGNUS = 31152;
    public static final int PREQ_LUCID = 34330; //Don't add this to account quests since it's part of lachelein questline
    public static final int PREQ_GOLLUX = 17523;
    public static final int PREQ_GLOOM = 35632;
    public static final int PREQ_VERUS_HILLA = 36772;
    public static final int PREQ_DARKNELL = 35815;
    public static final int PREQ_BLACK_MAGE = 35815;
    public static final int PREQ_RANMARU = 58913;
    public static final int PREQ_WILL = 37871; //Don't add this to account quests since it's part of esfera questline
    public static final int PREQ_JULIETA_1 = 64142;
    public static final int PREQ_JULIETA_2 = 64208;

    public static final Set<Integer> ACCOUNT_WIDE_QUESTS = new HashSet<>(Arrays.asList(
            // TODO: Lotus/Damien quests
            PREQ_URSUS,
            PREQ_EASY_MAGNUS,
            PREQ_MAGNUS,
            PREQ_MAGNUS_HELISEUM,
            PREQ_OMNI_CLN,
            PREQ_PAPULATUS,
            PREQ_CRA,
            PREQ_VON_LON_1,
            PREQ_VON_LON_2,
            PREQ_HORNTAIL,
            PREQ_ARKARIUM,
            PREQ_PINK_BEAN,
            PREQ_CYGNUS,
            PREQ_LUCID,
            PREQ_GOLLUX,
            PREQ_GLOOM,
            PREQ_VERUS_HILLA,
            PREQ_BLACK_MAGE, //  PREQ_DARKNELL same id
            PREQ_RANMARU,
            PREQ_JULIETA_1,
            PREQ_JULIETA_2,
            UNION_START_QUEST,
            UNION_COIN,
            UNION_RAID,
            UNION_RAID_COIN,
            UNION_RAID_BOSS,
            WHIP_THE_WHELPS,
            DEFEAT_GOLDEN_WYVERN,
            POWER_CRYSTAL_QUEST,
            VANISHING_JOURNEY_DAILY_QUEST,
            CHU_CHU_DAILY_QUEST,
            LACHELEIN_DAILY_QUEST,
            ARCANA_DAILY_QUEST,
            MORASS_DAILY_QUEST,
            ESFERA_DAILY_QUEST,
            CERNIUM_DAILY_QUEST,
            HOTEL_ARCUS_DAILY_QUEST
    ));

    public static final Set<Integer> VANISHING_JOURNEY_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.VANISHING_JOURNEY_DAILY_QUEST,
            34130,
            34131,
            34132,
            34133,
            34134,
            34135,
            34136,
            34137,
            34138,
            34139,
            34140,
            34141,
            34142,
            34143,
            34144,
            34145,
            34146,
            34147,
            34148,
            34149,
            34150,
            // also given by Rona (Quest.wz area 207), not in the parent quest's pool
            39055,
            39056,
            39057,
            39058,
            39059,
            39060,
            39061,
            39062,
            39063
            ));

    public static final Set<Integer> CHUCHU_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.CHU_CHU_DAILY_QUEST,
            39017,
            39018,
            39019,
            39020,
            39021,
            39022,
            39023,
            39024,
            39025,
            39026,
            39027,
            39028,
            39029,
            39030,
            39031,
            39032,
            // also given by Lyck (area 208), not in the parent quest's pool
            39033,
            39064,
            39065,
            39066,
            39067,
            39068,
            39069,
            39070
    ));

    public static final Set<Integer> LACHELEIN_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.LACHELEIN_DAILY_QUEST,
            34381,
            34382,
            34383,
            34384,
            34385,
            34386,
            34387,
            34388,
            34389,
            34390,
            34391,
            34392,
            34393,
            // also given in Lachelein (area 210), not in the parent quest's pool
            34394
    ));

    public static final Set<Integer> ARCANA_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.ARCANA_DAILY_QUEST,
            39038,
            39039,
            39040,
            39041,
            39042,
            39043,
            39044,
            39045,
            39046,
            39047,
            39048,
            // also given in Arcana (area 213), not in the parent quest's pool
            39049,
            39050
            ));

    public static final Set<Integer> MORASS_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.MORASS_DAILY_QUEST,
            34276,
            34277,
            34278,
            34279,
            34280,
            34281,
            34282,
            34283,
            34284,
            34285,
            34286,
            34287,
            34288,
            34289,
            34290,
            34291,
            34292,
            34293,
            39073,
            // Morass (area 217) quests outside the parent quest's pool; 34246/39074 are steps of 34296/34295
            34246,
            34294,
            34295,
            34296,
            39074
    ));

    public static final Set<Integer> ESFERA_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.ESFERA_DAILY_QUEST,
            34780,
            34781,
            34782,
            34783,
            34784,
            34785,
            34786,
            34787,
            34788,
            34789,
            34790,
            34791,
            34792,
            34793,
            34794,
            34795,
            34796,
            34797,
            34798,
            34799
    ));

    public static final Set<Integer> CERNIUM_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.CERNIUM_DAILY_QUEST,
            39820,
            39821,
            39822,
            39823,
            39824,
            39825,
            39923,
            39924,
            39925,
            39926,
            39927,
            39928
    ));

    public static final Set<Integer> HOTEL_ARCUS_QUESTS = new HashSet<>(Arrays.asList(
            QuestConstants.HOTEL_ARCUS_DAILY_QUEST,
            38151,
            38152,
            38153,
            38154,
            38155,
            38156
    ));

    //This list contains all quests that use m0=x where x is count for mob count tracking instead of the default qr
    public static final Set<Integer> M0_QR_QUESTS = new HashSet<>(Arrays.asList(
            39820,
            39821,
            39822,
            39823,
            39824,
            39825,
            39923,
            39924,
            38151,
            38152
    ));

    /**
     * Day on which "weeklyRepeat" quests become available again (at midnight in the reset time zone, ResetClock).
     */
    public static final java.time.DayOfWeek QUEST_WEEKLY_RESET_DAY = java.time.DayOfWeek.MONDAY;
    /**
     * When true, quest requests sent by the client are checked against all Quest.wz requirements
     * (quest chains, jobs, quest records, skills, equips, world, time windows, repeat timers), like the official
     * server does. Quests started from scripts (sm.startQuest) keep the old, lenient checks so existing scripts
     * keep working.
     */
    public static final boolean STRICT_QUEST_REQUIREMENTS = true;

    public static final Set<Integer> REPEATABLE_QUESTS = new HashSet<>(Arrays.asList(
            23947, //Purchasing a disguise | Black wings hat quest
            3901 //Tigun the Guard at the Palace
            ));


    /**
     * Quests the client asks to accept on its own (hidden auto-start markers) that the reference server never starts
     * and never answers.
     */
    public static final Set<Integer> IGNORED_ACCEPT_QUESTS = new HashSet<>(Arrays.asList(
            10732, 10734, 10736, 10738, 10740, 10742, 10744, 10746, 10748, 10750, 10752, 10754, 10762, 10764,
            10766, 10768, 10770, 10772, 10774, 10776, 10778, 10780, 10782, 10784, 14431, 15148, 15528, 15530,
            15532, 15534, 15536, 15538, 15540, 15542, 15544, 15546, 15548, 15550, 16019, 16021, 16023, 16025,
            16027, 16029, 16450, 16451, 16452, 16453, 16454, 16455, 16486, 16487, 16488, 16751, 16752, 16753,
            16754, 16874, 16875, 16876, 18938, 20767, 20768, 26024, 29015, 29906, 29907, 33189, 33190, 33243,
            33265, 33267, 36100, 36101, 36103, 36104, 36105, 36106, 36107, 36108, 36109, 36110, 36111, 36112,
            36113, 36114, 36115, 36116, 36117, 39300, 39301, 39303, 39305, 39306, 39307, 39309, 39318, 39319,
            39320, 39321, 39326, 39327, 39332, 39333, 39339, 39340, 39361, 39362, 39363, 39370, 39371, 39372,
            39373, 39374, 62154, 100528
    ));

    public static final int GROWTH_SPURT_SLV = 59340;
    public static final int GROWTH_SPURT_EXP = 59341;
    /** Lucky Item (set scroll) quest: its QR value is the set id that was applied. As in v265.3. */
    public static final int ZERO_SET_QUEST = 41907;

    public static String getWhStorageQuestValByTemplateID(int templateId) {
        if (templateId >= 9304000 && templateId <= 9304008) {
            return String.valueOf((templateId % 10) + 1);
        }
        return null;
    }

    public static boolean isSpamQuest(String scriptName) {
        return scriptName.contains("63360") || scriptName.contains("7707") || scriptName.contains("36102");
    }

    public static boolean isSurpriseMissionQuest(int questId) {
        return questId > 49_000 && questId < 50_000;
    }

    public static List<Integer> getSurpriseMissionQuestIds() {
        return Arrays.asList(
                49001, 49002, 49003, 49004, 49005,
                49006, 49007, 49008, 49009, 49010,
                49011, 49012, 49013, 49014, 49015,
                49016, 49017, 49018
        );
    }

    public static boolean is2QrQuest(int questId) {
        return questId == SURPRISE_MISSION_QUEST_ID || isSurpriseMissionQuest(questId)
                || questId == 3463 || questId == 3875 || questId == 3881 || questId == 31063 || questId == 25973;
    }
}

package net.swordie.ms.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * Quests v265 blocks in its data and replaces with a newer questline, and quests the v265
 * data can no longer run at all ({@link #isUnreachable}). Neither can be started; scripts that force-complete one
 * still can. Other blocked quests are not listed and stay as they were (hidden from the quest log, still startable).
 */
public class RetiredQuests {

    /** {first, last} id ranges, by questline. */
    private static final int[][] RANGES = {
            // Explorer start, story and 2nd/3rd job choice, Dual Blade start -> Maple Island 36301-36307, Explorer
            // story 36308-36337; 1401-1405 follow 36307 (no 1400); 2nd job 1410/1414/1418/1421/1424/1427 and 3rd job
            // 1430/1434/1438/1441/1444 are single quests (the per-branch 1411-1448 are gone); Dual Blade starts at
            // 2604 after 36307
            {1411, 1413}, {1415, 1417}, {1419, 1420}, {1422, 1423}, {1425, 1426}, {1428, 1428}, {1431, 1433},
            {1435, 1437}, {1439, 1440}, {1442, 1443}, {1445, 1448}, {2618, 2620}, {2624, 2626}, {2630, 2631},
            {2637, 2637}, {2646, 2646}, {2680, 2683}, {32240, 32323}, {32325, 32359}, {35940, 35940},
            // 5th Job: Preparing for Power -> 1460 -> 1461 directly
            {1481, 1481},
            // Orbis / El Nath old side quests -> Orbis remade: 3270-3284, Aqua Road 3285-3292
            {1531, 1534}, {3038, 3038}, {3043, 3043}, {3045, 3046}, {3049, 3049}, {3059, 3059}, {3079, 3079},
            {3097, 3098}, {3100, 3100}, {3158, 3158},
            // Shade start and story -> 38161-38263 (Curse Your Human Legs 38161 ... Forgotten Hero 38263)
            {1542, 1542}, {38030, 38058}, {38060, 38077}, {38080, 38081}, {38901, 38902}, {38904, 38904},
            {38996, 38998},
            // Silent Crusade -> [Silent Crusade] 20964-36658
            {1607, 1608}, {1619, 1639}, {1653, 1675},
            // Silent Crusade orientation -> [Silent Crusade] 20964-36658
            {1618, 1618},
            // Orbis in Danger -> 3277-3280
            {3086, 3088},
            // Lion King's Castle -> [Lion King's Castle] 1550-1568 (boss window now asks 1562 or 3170)
            {3138, 3141}, {3143, 3145}, {3147, 3147}, {3165, 3178},
            // Lion King's Castle -> 1550-1568
            {3157, 3157}, {3181, 3182},
            // Single quests with a same-name replacement -> same name, new id
            {15149, 15149},
            // Legion dailies -> [Legion] Weekly Dragon Extermination 503281 (16015 PC Cafe kept)
            {16011, 16012},
            // Mihile start / Cygnus side -> Mihile 36601-36646
            {20321, 20321}, {20410, 20410}, {20412, 20412}, {20802, 20807}, {20809, 20809},
            // Alliance / Empress's Council -> same names 1525, 36516-36523, 23194
            {20735, 20742}, {23146, 23146},
            // Cygnus Knights start and story -> 36451-36467 (Ereve start), 36468-36537 [Cygnus Knights], job trials
            // 36506-36511/36523
            {20751, 20758}, {20880, 20882}, {20890, 20893}, {20900, 20907}, {20912, 20914}, {20920, 20945},
            {20950, 20953}, {20955, 20956},
            // Mihile: training -> Mihile 36601-36646 (36604-36606 same names)
            {20770, 20777},
            // Mihile: Chromile -> Mihile 36601-36646
            {20778, 20794},
            // Mihile: new ally -> Mihile 36601-36646
            {20795, 20801},
            // Cygnus missions -> [Cygnus Knights] 36468-36537
            {20946, 20947},
            // Aran start and story -> 36901-36966 (Rien start 36901-36908, Victoria 36910+, job advancements
            // 36961-36966)
            {21200, 21202}, {21300, 21303}, {21400, 21401}, {21700, 21700}, {21705, 21758}, {21766, 21767},
            // [Alliance] -> 1524/1525, 23194
            {23147, 23147}, {38078, 38078},
            // Edelstein (Resistance) side -> 23180-23198 (same names)
            {23925, 23927}, {23938, 23938}, {23942, 23943},
            // Edelstein side -> 23186-23198 (same names)
            {23947, 23948}, {23950, 23950}, {23953, 23953},
            // [Verne Mine] -> 23187/23188 and [Verne Mine] 2433-23195
            {23949, 23949}, {23951, 23952},
            // Kaiser story -> 25790, 36750-36753
            {25737, 25738}, {25743, 25743}, {25746, 25748}, {25756, 25756}, {25763, 25765}, {25779, 25781},
            // Angelic Buster start and story -> 25920, 36721-36754
            {25825, 25829}, {25835, 25837}, {25839, 25880}, {25882, 25886}, {25891, 25892}, {25895, 25898},
            {25902, 25903},
            // Hella is Missing -> 3270-3281 (same names)
            {31026, 31034},
            // Ellin Forest -> [Ellin Forest] 36341-36367
            {31200, 31229},
            // Heliseum / Pantheon story -> [Heliseum] 36690-38860 (boss window: Magnus asks 38858 [Heliseum] The Final
            // Battle, or the old set)
            {31400, 31445}, {31447, 31447}, {31450, 31457}, {31459, 31515}, {31517, 31521}, {31523, 31526},
            {31528, 31538}, {31580, 31584}, {31600, 31601}, {31603, 31617}, {31619, 31621}, {31623, 31630},
            {31637, 31637}, {31643, 31643}, {31645, 31651}, {31653, 31653}, {31655, 31662}, {31664, 31668},
            {31670, 31674}, {31677, 31677}, {31679, 31683}, {31686, 31686}, {31800, 31802}, {31804, 31806},
            {31808, 31816}, {31818, 31821}, {31823, 31830}, {31832, 31833}, {31852, 31852}, {34703, 34736},
            {36253, 36253}, {39441, 39441}, {39686, 39686},
            // Vanishing Journey dailies -> 38450-38453
            {34128, 34150}, {39055, 39063},
            // Vanishing Journey: Investigation Findings 3/4, Reverse City request -> 34173-34178, 34344, 34345 (the
            // Erda thread between regions)
            {34166, 34167}, {39071, 39071},
            // Morass: Tana side story -> none as such: Esfera 37851 now follows Morass 34272 directly
            {34230, 34244},
            // Morass dailies -> 38466-38469
            {34275, 34296},
            // Lachelein: Dream Defender Ranking Gift -> none needed (Dream Defender ranking reworked)
            {34336, 34336},
            // Lachelein dailies -> 38458-38461
            {34377, 34378}, {34381, 34394},
            // Esfera story -> 37851-37871
            {34561, 34586},
            // Esfera: Ollie's Message -> 37851-37871
            {34772, 34772},
            // Esfera dailies -> 38470-38473
            {34773, 34773}, {34780, 34799},
            // Heliseum -> [Heliseum] 36690-38860
            {34967, 34967}, {34971, 34971},
            // Tenebris (Moonbridge/Labyrinth/Limina) dailies -> 38474-38476
            {35550, 35551}, {35560, 35565}, {35570, 35583}, {35590, 35594},
            // Moonbridge story (steps cut) -> the remaining Moonbridge quests
            // 35600/35601/35605/35607-35613/35615/35620/35628/35631/35632 relink
            {35602, 35604}, {35606, 35606}, {35614, 35614}, {35616, 35619}, {35621, 35627}, {35629, 35630},
            // Labyrinth of Suffering story -> 36760-36772
            {35700, 35710}, {35712, 35731}, {35756, 35756},
            // Limina story -> 35841-35851
            {35800, 35814}, {35817, 35817},
            // [The First] boss first-clear -> same names, new ids
            {36100, 36118},
            // Hotel Arcus dailies -> 19024-19027
            {38150, 38156},
            // Chu Chu dailies -> 38454-38457
            {39013, 39014}, {39017, 39033}, {39064, 39070},
            // Arcana dailies -> 38462-38465
            {39034, 39035}, {39038, 39050},
            // Chu Chu: Master Lyck's Request -> 38454 Chu Chu daily
            {39072, 39072},
            // Cernium (Before) steps cut -> remaining Cernium chain
            {39802, 39802}, {39812, 39812},
            // Cernium dailies -> 19020-19023
            {39819, 39825},
            // Cernium (After) steps cut -> remaining Cernium chain
            {39905, 39905}, {39909, 39909}, {39915, 39916},
            // Burning Cernium dailies -> 19020-19023 (Cernium)
            {39922, 39928},
    };

    /**
     * {first, last} id ranges of quests v265 can't run at all, blocked or not: a start or end NPC, a required map
     * or monster is gone from the data, or the only quest leading to them is. Built from the v265 export.
     */
    private static final int[][] UNREACHABLE_RANGES = {
            // Arboren / Stump Town (Beast Tamer story): its NPCs stood only on maps v265 removed
            {59003, 59009}, {59011, 59011}, {59013, 59013}, {59015, 59020}, {59036, 59052}, {59054, 59056},
            {59061, 59061}, {59063, 59070}, {59072, 59079}, {59081, 59081}, {59100, 59120}, {59123, 59153},
            {59170, 59179}, {59190, 59237}, {59241, 59262}, {59268, 59270},
            // Abrup Basin / MONAD: its NPCs stood only on maps v265 removed
            {64010, 64015}, {64017, 64031}, {64033, 64033}, {64035, 64041}, {64043, 64049}, {64051, 64053},
            {64060, 64065}, {64067, 64068}, {64070, 64074}, {64076, 64081}, {64083, 64092}, {64100, 64105},
            {64107, 64114}, {64116, 64116}, {64118, 64123}, {64125, 64136}, {64138, 64140}, {64142, 64145},
            {64149, 64152}, {64191, 64192}, {64208, 64208}, {64211, 64212}, {64859, 64860}, {500487, 500493},
            // NPCs or maps missing from the data (Star Planet, Blackgate, the Gray aliens, Epic Dungeon and a few
            // others)
            {8142, 8142}, {27113, 27115}, {28750, 28753}, {28755, 28756}, {28768, 28768}, {28779, 28780},
            {43701, 43701}, {43704, 43705}, {43721, 43722}, {43750, 43750}, {43755, 43765}, {43770, 43771},
            {43774, 43774}, {43776, 43778}, {48000, 48008}, {48010, 48010}, {59517, 59517}, {61133, 61142},
            {62035, 62035},
            // Nautilus: Bartol and Serryl stood only on the hallways v265 removed
            {2207, 2207}, {2900, 2934}, {2936, 2938}, {2946, 2947},
            // Princess No: her chamber 811000599 is gone, so she can't be fought
            {5869, 5869}, {68094, 68094},
            // Alien Dimension: all its maps are gone; Dr. Bing stood only there
            {17206, 17206},
    };

    private static final Set<Integer> IDS = new HashSet<>();
    private static final Set<Integer> UNREACHABLE = new HashSet<>();

    static {
        for (int[] r : RANGES) {
            for (int id = r[0]; id <= r[1]; id++) {
                IDS.add(id);
            }
        }
        for (int[] r : UNREACHABLE_RANGES) {
            for (int id = r[0]; id <= r[1]; id++) {
                UNREACHABLE.add(id);
            }
        }
    }

    public static boolean contains(int questId) {
        return IDS.contains(questId);
    }

    /** @return whether the v265 data can no longer run this quest (blocked or not) */
    public static boolean isUnreachable(int questId) {
        return UNREACHABLE.contains(questId);
    }

    public static int size() {
        return IDS.size();
    }
}

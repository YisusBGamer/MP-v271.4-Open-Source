package net.swordie.ms.constants;

import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.util.container.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 29/12/2021.
 *
 * @author Asura
 */
public class BossLootConstants {
    // v232 "of the Wolf" weapons: the wiki's newer "Lupine" names refer to the same set.
    // Only weapons present in this client version are included (no later-job weapons).
    private static final int[] AKECHI_WOLF_WEAPONS = {
            1212134, 1213038, 1214034, 1222127, 1232127, 1242150, 1242151,
            1252115, 1262057, 1272047, 1282047, 1292042, 1302364, 1312219,
            1322271, 1332294, 1342112, 1362154, 1372242, 1382279, 1402274,
            1412194, 1422204, 1432232, 1442290, 1452273, 1462257, 1472280,
            1482237, 1492251, 1522157, 1532163, 1542136, 1254044, 1582050, 1592041
    };
    /**
     * Chaos Horntail's loot is listed under 8810122 (the id GMS spawns and the boss list uses); 8810118, its old
     * spawn id, still maps there.
     */
    public static int lootId(int templateId) {
        return templateId == 8810118 ? BossConstants.HORNTAIL_HARD : templateId;
    }

    public static List<DropInfo> getLootPoolByTemplateId(int templateId) {
        if (templateId == BossConstants.AKECHI) {
            // The v232 data has no Mitsuhide's Sword reward mob; award its rewards on Akechi instead.
            // DropInfo's quantity bounds are inclusive, so this produces 1 to 4 marks.
            List<DropInfo> loot = new ArrayList<>();
            loot.add(new DropInfo(4310295, 10000, 1, 4));
            for (int weaponId : AKECHI_WOLF_WEAPONS) {
                // No drop odds are known; use the Ranmaru weapon base chance. The normal class filter, drop rate and boss
                // equipment boost still apply in Mob.
                loot.add(new DropInfo(weaponId, 300));
            }
            return loot;
        }
        // bosses drop their own table (mob_drops) and crystals only; this adds the few drops kept in code
        List<DropInfo> pool = new ArrayList<>();
        DropInfo hammer = net.swordie.ms.client.character.ExceptionalEnchantModule.hammerDrop(templateId);
        if (hammer != null) {
            pool.add(hammer);
        }
        return pool;
    }

    /**
     * {@link #getLootPoolByTemplateId(int)} plus what depends on the mode's map: both Black Mage modes end on the same
     * Genesis Crux, so Extreme's extras (belt hammer, Dense Sol Erda Energy) go by the field.
     */
    public static List<DropInfo> getLootPoolByTemplateId(int templateId, int fieldId) {
        List<DropInfo> pool = getLootPoolByTemplateId(templateId);
        if (templateId == BossConstants.BLACK_MAGE_GENESIS_CRUX && BossConstants.getBlackMageMode(fieldId) == 1) {
            pool = new ArrayList<>(pool);
            pool.add(new DropInfo(net.swordie.ms.client.character.ExceptionalEnchantModule.BELT_HAMMER,
                    net.swordie.ms.client.character.ExceptionalEnchantModule.HAMMER_DROP_CHANCE));
            pool.add(new DropInfo(2636422, 10000)); // Dense Sol Erda Energy
        }
        return pool;
    }

    //MOB ID, <UI NAME, UI ICON>
    private static final Map<Integer, Tuple<Integer, Integer>> PowerCrystalTemplateIdOverrideForUI = Map.ofEntries(
            Map.entry(BossConstants.CRIMSON_QUEEN_NORMAL_BOX, new Tuple<>(BossConstants.NORMAL_CRIMSON_QUEEN_SIMMERING, BossConstants.NORMAL_CRIMSON_QUEEN_SIMMERING)),
            Map.entry(BossConstants.PIERRE_NORMAL_BOX, new Tuple<>(BossConstants.PIERRE_NORMAL_PURPLE, BossConstants.PIERRE_NORMAL_PURPLE)),
            Map.entry(BossConstants.CRIMSON_QUEEN_CHAOS_BOX, new Tuple<>(BossConstants.CHAOS_CRIMSON_QUEEN_SIMMERING, BossConstants.CHAOS_CRIMSON_QUEEN_SIMMERING)),
            Map.entry(BossConstants.PIERRE_CHAOS_BOX, new Tuple<>(BossConstants.PIERRE_CHAOS_PURPLE, BossConstants.PIERRE_CHAOS_PURPLE)),
            Map.entry(BossConstants.JULIETA_BOX, new Tuple<>(9402270, 9402270)),
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL, new Tuple<>(9210052, 8880700)),
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_CHEST, new Tuple<>(9210052, 8880700)),
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS, new Tuple<>(9210051, 8880711)),
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_CHEST, new Tuple<>(9210051, 8880711)),
            Map.entry(BossConstants.CHOSEN_SEREN_TREASURE_CHEST, new Tuple<>(9210050, 8880600)),
            Map.entry(BossConstants.serenMob(0, BossConstants.SEREN_TREASURE_CHEST), new Tuple<>(9210054, 8880630)),
            Map.entry(BossConstants.serenMob(1, BossConstants.SEREN_TREASURE_CHEST), new Tuple<>(9210050, 8880600)),
            Map.entry(BossConstants.serenMob(2, BossConstants.SEREN_TREASURE_CHEST), new Tuple<>(9210058, 8880650)),
            Map.entry(BossConstants.KALOS_CHEST[0], new Tuple<>(9210060, BossConstants.KALOS_P1[0])), // Easy Kalos
            Map.entry(BossConstants.KALOS_CHEST[1], new Tuple<>(9210056, BossConstants.KALOS_P1[1])), // Normal Kalos
            Map.entry(BossConstants.KALOS_CHEST[2], new Tuple<>(9210061, BossConstants.KALOS_P1[2])), // Chaos Kalos
            Map.entry(BossConstants.KALOS_CHEST[3], new Tuple<>(9210062, BossConstants.KALOS_P1[3])), // Extreme Kalos
            Map.entry(BossConstants.KALING_BASE[0] + BossConstants.KALING_CHEST, new Tuple<>(9210063, 8880907)), // Easy Kaling
            Map.entry(BossConstants.KALING_BASE[1] + BossConstants.KALING_CHEST, new Tuple<>(9210059, 8880837)),
            Map.entry(BossConstants.KALING_BASE[2] + BossConstants.KALING_CHEST, new Tuple<>(9210064, 8880937)),
            Map.entry(BossConstants.KALING_BASE[3] + BossConstants.KALING_CHEST, new Tuple<>(9210065, 8880967)),
            Map.entry(BossConstants.ADVERSARY_BASE[0] + 15, new Tuple<>(9210071, BossConstants.ADVERSARY_BASE[0] + 10)),
            Map.entry(BossConstants.ADVERSARY_BASE[1] + 15, new Tuple<>(9210072, BossConstants.ADVERSARY_BASE[1] + 10)),
            Map.entry(BossConstants.ADVERSARY_BASE[2] + 15, new Tuple<>(9210073, BossConstants.ADVERSARY_BASE[2] + 10)),
            Map.entry(BossConstants.ADVERSARY_BASE[3] + 15, new Tuple<>(9210074, BossConstants.ADVERSARY_BASE[3] + 10)),
            Map.entry(BossConstants.LIMBO_BASE[0] + 34, new Tuple<>(9210067, BossConstants.LIMBO_BASE[0] + 32)),
            Map.entry(BossConstants.LIMBO_BASE[1] + 34, new Tuple<>(9210068, BossConstants.LIMBO_BASE[1] + 32)),
            Map.entry(BossConstants.BALDRIX_BASE[0] + 24, new Tuple<>(9210069, BossConstants.BALDRIX_BASE[0] + 21)),
            Map.entry(BossConstants.BALDRIX_BASE[1] + 24, new Tuple<>(9210070, BossConstants.BALDRIX_BASE[1] + 21)),
            Map.entry(BossConstants.LUCID_REWARD_BOX_EASY, new Tuple<>(9210048, 8880142)),
            Map.entry(BossConstants.WILL_MIRROR[0], new Tuple<>(9210053, BossConstants.EASY_REAL_WILL_P1)),
            Map.entry(BossConstants.WILL_MIRROR[1], new Tuple<>(9210038, BossConstants.REAL_WILL_P1)),
            Map.entry(BossConstants.DARKNELL_HARD, new Tuple<>(9210047, 8645066)),
            Map.entry(BossConstants.GLOOM_CHAOS, new Tuple<>(9210046, 8644655)),
            Map.entry(BossConstants.DARKNELL_NORMAL, new Tuple<>(9210043, 8645009)),
            Map.entry(BossConstants.GLOOM, new Tuple<>(9210042, 8644650)),
            Map.entry(BossConstants.GENESIS_CRUX, new Tuple<>(9210041, 8880502)),
            Map.entry(BossConstants.VERUS_HILLA, new Tuple<>(9210040, 8880410)),
            Map.entry(BossConstants.VERUS_HILLA_HARD, new Tuple<>(9210040, 8880410)),
            Map.entry(BossConstants.EASY_ZAKUM_BODY, new Tuple<>(9210000, 8800022)),
            Map.entry(BossConstants.NORMAL_ZAKUM_BODY, new Tuple<>(9210001, 8800002)),
            Map.entry(BossConstants.EASY_MAGNUS_TEMPLATE, new Tuple<>(9210003, 8880010)),
            Map.entry(BossConstants.NORMAL_MAGNUS_TEMPLATE, new Tuple<>(9210004, 8880002)),
            Map.entry(BossConstants.HARD_MAGNUS_TEMPLATE, new Tuple<>(9210005, 8880000)),
            Map.entry(BossConstants.HILLA_NORMAL, new Tuple<>(9210006, 8870000)),
            Map.entry(BossConstants.HILLA_HARD, new Tuple<>(9210007, 8870100)),
            Map.entry(BossConstants.VON_LEON_EASY, new Tuple<>(9210017, 8840007)),
            Map.entry(BossConstants.VON_LEON_NORMAL, new Tuple<>(9210018, 8840000)),
            Map.entry(BossConstants.VON_LEON_HARD, new Tuple<>(9210019, 8840014)),
            Map.entry(BossConstants.HORNTAIL_NORMAL, new Tuple<>(9210021, 8810018)),
            Map.entry(BossConstants.ARKARIUM_EASY, new Tuple<>(9210023, 8860005)),
            Map.entry(BossConstants.ARKARIUM_NORMAL, new Tuple<>(9210024, 8860000)),
            Map.entry(BossConstants.CYGNUS_EASY, new Tuple<>(9210027, 8850111)),
            Map.entry(BossConstants.CYGNUS_NORMAL, new Tuple<>(9210028, 8850011)),
            Map.entry(BossConstants.LOTUS_HARD_P3, new Tuple<>(9210030, 8950001)),
            Map.entry(BossConstants.lotusV265Core(0), new Tuple<>(9210029, 8950101)),
            Map.entry(BossConstants.lotusV265Core(1), new Tuple<>(9210030, 8950001)),
            Map.entry(BossConstants.lotusV265Core(2), new Tuple<>(9210066, 8881200)),
            // Damien's reward (and the clear) is the Restrained Sword of Destruction in the clear room (Normal 8950111,
            // Hard 8950112), not his phase 2; its crystal shows as Damien's.
            Map.entry(8950111, new Tuple<>(9210031, 8880110)),
            Map.entry(8950112, new Tuple<>(9210032, 8880100)),
            Map.entry(BossConstants.LUCID_REWARD_BOX_NORMAL, new Tuple<>(9210033, 8880140)),
            Map.entry(BossConstants.LUCID_REWARD_BOX_HARD, new Tuple<>(9210034, 8880141)),
            Map.entry(BossConstants.PAPULATUS_EASY_P2, new Tuple<>(9210035, 8500002)),
            Map.entry(BossConstants.PAPULATUS_NORMAL_P2, new Tuple<>(9210036, 8500012)),
            Map.entry(BossConstants.PAPULATUS_CHAOS_P2, new Tuple<>(9210037, 8500022)),
            Map.entry(BossConstants.WILL_MIRROR[2], new Tuple<>(9210039, 8880300))

    );

    //MOB ID, MESO AMOUNT
    private static final Map<Integer, Long> PowerCrystalMesoByTemplateId = Map.ofEntries(
            Map.entry(BossConstants.EASY_ZAKUM_BODY, 200_000L),
            Map.entry(BossConstants.NORMAL_ZAKUM_BODY, 612_500L),
            Map.entry(BossConstants.PAPULATUS_EASY_P2, 684_500L),
            Map.entry(BossConstants.EASY_MAGNUS_TEMPLATE, 722_000L),
            Map.entry(9400300, 722_000L), //YAKUZA boss
            Map.entry(BossConstants.HILLA_NORMAL, 800_000L),
            Map.entry(BossConstants.RANMARU_NORMAL_BODY, 840_500L),
            Map.entry(BossConstants.HORNTAIL_EASY, 882_000L),
            Map.entry(BossConstants.GIGATOAD, 882_000L),
            Map.entry(BossConstants.PIERRE_NORMAL_BOX, 968_000L),
            Map.entry(BossConstants.VON_BON_NORMAL, 968_000L),
            Map.entry(BossConstants.CRIMSON_QUEEN_NORMAL_BOX, 968_000L),
            Map.entry(BossConstants.VELLUM_NORMAL, 968_000L),
            Map.entry(BossConstants.HORNTAIL_NORMAL, 1_012_500L),
            Map.entry(BossConstants.VON_LEON_EASY, 1_058_000L),
            Map.entry(BossConstants.ARKARIUM_EASY, 1_152_000L),
            Map.entry(BossConstants.FRENZIED_GIGATOAD, 1_153_000L),
            Map.entry(BossConstants.JULIETA_BOX, 1_200_000L),
            Map.entry(BossConstants.OMNI_CLN, 1_250_000L),
            Map.entry(BossConstants.HORNTAIL_HARD, 1_352_000L),
            Map.entry(BossConstants.PINK_BEAN_P2, 1_404_500L),
            Map.entry(BossConstants.VON_LEON_NORMAL, 1_458_000L),
            Map.entry(BossConstants.VON_LEON_HARD, 2_450_000L),
            Map.entry(BossConstants.ARKARIUM_NORMAL, 2_520_500L),
            Map.entry(BossConstants.NORMAL_MAGNUS_TEMPLATE, 2_592_000L),
            Map.entry(BossConstants.PAPULATUS_NORMAL_P2, 2_664_500L),
            Map.entry(BossConstants.RANMARU_HARD_BODY_5, 2_664_500L),
            Map.entry(BossConstants.CYGNUS_EASY, 9_112_500L),
            Map.entry(BossConstants.HILLA_HARD, 11_250_000L),
            Map.entry(BossConstants.CHAOS_PINK_BEAN_P2, 12_800_000L),
            Map.entry(BossConstants.CYGNUS_NORMAL, 14_450_000L),
            Map.entry(BossConstants.HARD_ZAKUM_BODY, 16_200_000L),
            Map.entry(BossConstants.PIERRE_CHAOS_BOX, 16_200_000L),
            Map.entry(BossConstants.VON_BON_CHAOS, 16_200_000L),
            Map.entry(BossConstants.CRIMSON_QUEEN_CHAOS_BOX, 16_200_000L),
            Map.entry(BossConstants.PRINCESS_NO, 16_200_000L),
            Map.entry(BossConstants.HARD_MAGNUS_TEMPLATE, 19_012_500L),
            Map.entry(BossConstants.VELLUM_CHAOS, 21_012_500L),
            Map.entry(BossConstants.PAPULATUS_CHAOS_P2, 16_200_000L),
            Map.entry(BossConstants.AKECHI, 28_800_000L),
            Map.entry(BossConstants.LOTUS_P3, 32_512_500L),
            Map.entry(BossConstants.lotusV265Core(0), 32_512_500L),
            Map.entry(8950111, 33_800_000L), // Restrained Sword of Destruction (Normal Damien clear room)
            Map.entry(8950112, 70_312_500L), // Restrained Sword of Destruction (Hard Damien clear room)
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL, 34_322_000L),
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_CHEST, 34_322_000L),
            Map.entry(BossConstants.LUCID_REWARD_BOX_EASY, 35_112_500L),
            Map.entry(BossConstants.WILL_MIRROR[0], 38_255_000L),
            // Normal and Hard Lucid drop crystals (PowerCrystalTemplateIdOverrideForUI)
            Map.entry(BossConstants.LUCID_REWARD_BOX_NORMAL, 40_612_500L),
            Map.entry(BossConstants.LUCID_REWARD_BOX_HARD, 80_000_000L),
            Map.entry(BossConstants.WILL_MIRROR[1], 46_512_500L),
            Map.entry(BossConstants.GLOOM, 49_612_500L),
            Map.entry(BossConstants.DARKNELL_NORMAL, 52_812_500L),
            Map.entry(BossConstants.LOTUS_HARD_P3, 74_112_500L),
            Map.entry(BossConstants.lotusV265Core(1), 74_112_500L),
            Map.entry(BossConstants.lotusV265Core(2), 279_500_000L),
            Map.entry(BossConstants.WILL_MIRROR[2], 88_200_000L),
            Map.entry(BossConstants.VERUS_HILLA, 89_520_000L),
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS, 90_312_500L),
            Map.entry(BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_CHEST, 90_312_500L),
            Map.entry(BossConstants.GLOOM_CHAOS, 92_450_000L),
            Map.entry(BossConstants.DARKNELL_HARD, 98_000_000L),
            Map.entry(BossConstants.VERUS_HILLA_HARD, 110_450_000L),
            Map.entry(BossConstants.CHOSEN_SEREN_TREASURE_CHEST, 151_250_000L),
            // Seren: crystal ids 9210054 / 9210050 / 9210058 (Normal / Hard / Extreme)
            Map.entry(BossConstants.serenMob(0, BossConstants.SEREN_TREASURE_CHEST), 133_687_500L),
            Map.entry(BossConstants.serenMob(1, BossConstants.SEREN_TREASURE_CHEST), 151_250_000L),
            Map.entry(BossConstants.serenMob(2, BossConstants.SEREN_TREASURE_CHEST), 847_000_000L),
            // Kalos the Guardian: Intense Power Crystal, 1 party member (maplestorywiki.net, GMS v270)
            Map.entry(BossConstants.KALOS_CHEST[0], 187_500_000L),
            Map.entry(BossConstants.KALOS_CHEST[1], 260_000_000L),
            Map.entry(BossConstants.KALOS_CHEST[2], 520_000_000L),
            Map.entry(BossConstants.KALOS_CHEST[3], 1_040_000_000L),
            Map.entry(BossConstants.KALING_BASE[0] + BossConstants.KALING_CHEST, 206_250_000L),
            Map.entry(BossConstants.KALING_BASE[1] + BossConstants.KALING_CHEST, 301_300_000L),
            Map.entry(BossConstants.KALING_BASE[2] + BossConstants.KALING_CHEST, 598_000_000L),
            Map.entry(BossConstants.KALING_BASE[3] + BossConstants.KALING_CHEST, 1_205_200_000L),
            // First Adversary, Limbo, Baldrix: Intense Power Crystal, 1 party member (maplestorywiki.net, GMS v270)
            Map.entry(BossConstants.ADVERSARY_BASE[0] + 15, 197_000_000L),
            Map.entry(BossConstants.ADVERSARY_BASE[1] + 15, 273_000_000L),
            Map.entry(BossConstants.ADVERSARY_BASE[2] + 15, 588_000_000L),
            Map.entry(BossConstants.ADVERSARY_BASE[3] + 15, 1_176_000_000L),
            Map.entry(BossConstants.LIMBO_BASE[0] + 34, 420_000_000L),
            Map.entry(BossConstants.LIMBO_BASE[1] + 34, 749_000_000L),
            Map.entry(BossConstants.BALDRIX_BASE[0] + 24, 560_000_000L),
            Map.entry(BossConstants.BALDRIX_BASE[1] + 24, 840_000_000L),
            // Malefic Star, Jupiter: no published price yet; above Baldrix by entry level, below the top (Extreme Kaling)
            Map.entry(BossConstants.MALEFIC_BASE[0] + BossConstants.MALEFIC_CHEST, 600_000_000L),
            Map.entry(BossConstants.MALEFIC_BASE[1] + BossConstants.MALEFIC_CHEST, 950_000_000L),
            Map.entry(BossConstants.JUPITER_BASE[0] + BossConstants.JUPITER_CHEST, 760_000_000L),
            Map.entry(BossConstants.JUPITER_BASE[1] + BossConstants.JUPITER_CHEST, 1_200_000_000L),
            // Kai: no published price; between First Adversary and Limbo by entry level
            Map.entry(BossConstants.KAI_BASE[0] + BossConstants.KAI_CHEST, 300_000_000L),
            Map.entry(BossConstants.KAI_BASE[1] + BossConstants.KAI_CHEST, 640_000_000L),
            Map.entry(BossConstants.GENESIS_CRUX, 500_000_000L)
    );

    /**
     * The price of a crystal by the boss id it carries (its UI id, e.g. 9210033 Normal Lucid), for crystals that were
     * made with a price of 0. 0 if unknown.
     */
    public static long getPowerCrystalMesoByCrystalBossId(int crystalBossId) {
        for (var e : V271_CRYSTALS.entrySet()) { // GMS v271 names first
            if (e.getValue()[0] == crystalBossId) {
                return e.getValue()[2];
            }
        }
        for (var e : PowerCrystalTemplateIdOverrideForUI.entrySet()) {
            if (e.getValue().getLeft() == crystalBossId) {
                return PowerCrystalMesoByTemplateId.getOrDefault(e.getKey(), 0L);
            }
        }
        return PowerCrystalMesoByTemplateId.getOrDefault(lootId(crystalBossId), 0L);
    }

    /** Default of the top price setting: the most valuable crystal; the rest follow a square-root curve below it. */
    public static final long POWER_CRYSTAL_TOP_PRICE = 50_000_000_000L;
    private static final long POWER_CRYSTAL_TOP_BASE = PowerCrystalMesoByTemplateId.values().stream()
            .mapToLong(Long::longValue).max().orElse(1L);

    /** The sell price of a crystal with this base price: top price * sqrt(base / highest base). */
    public static long getPowerCrystalSellPrice(long basePrice) {
        if (basePrice <= 0) {
            return 0;
        }
        long top = ServerFeatures.getInt(ServerFeatures.Feature.CRYSTAL_TOP_PRICE) * 1_000_000L;
        if (top <= 0) {
            return basePrice; // the table's own price
        }
        double price = top * Math.sqrt((double) basePrice / POWER_CRYSTAL_TOP_BASE);
        return Math.min(top, Math.round(price));
    }

    // GMS v271 crystal shop (SHOP_COLLECTOR_INIT,
    // current period from 2024-12-05): crystal name id, icon and price where the tables above differ (prices raised in the
    // 2024-12 rebalance; GMS names every crystal by its own 92100xx/96011xx id). Keys are the dropping monsters.
    private static final Map<Integer, long[]> V271_CRYSTALS = Map.ofEntries(
            crystal(8500022, 9210037, 8500022, 26_450_000L),
            crystal(8644650, 9210042, 8644650, 59_535_000L),
            crystal(8645009, 9210043, 8645009, 63_375_000L),
            crystal(8645066, 9210047, 8645066, 133_584_000L),
            crystal(8880156, 9210048, 8880142, 47_401_875L),
            crystal(8880167, 9210033, 8880140, 50_765_625L),
            crystal(8880177, 9210034, 8880141, 100_800_000L),
            crystal(8880450, 9210055, 8880450, 116_376_000L),
            crystal(8880410, 9210040, 8880410, 152_421_000L),
            crystal(8880518, 9210041, 8880502, 900_000_000L),
            crystal(8880614, 9210050, 8880600, 219_312_500L),
            crystal(8880615, 9210050, 8880600, 219_312_500L),
            crystal(8880644, 9210054, 8880630, 177_804_375L),
            crystal(8880725, 9210051, 8880711, 120_115_625L),
            crystal(8880726, 9210052, 8880700, 46_334_700L),
            crystal(9210051, 9210051, 8880711, 120_115_625L),
            crystal(9210052, 9210052, 8880700, 46_334_700L),
            crystal(8881153, 9210030, 8881150, 88_935_000L),
            crystal(8950102, 9210030, 8881150, 88_935_000L),
            crystal(8950112, 9210032, 8880100, 84_375_000L),
            crystal(8950113, 9210053, 8880360, 49_348_950L),
            crystal(8950114, 9210038, 8880340, 55_815_000L),
            crystal(8950115, 9210039, 8880300, 124_362_000L),
            crystal(9210046, 9210046, 8644655, 112_789_000L),
            crystal(8881904, 9210075, 8881900, 290_400_000L),
            crystal(8881954, 9210076, 8881900, 798_000_000L),
            crystal(8882013, 9210077, 8882000, 593_000_000L),
            crystal(8882063, 9210078, 8882000, 1_190_600_000L),
            crystal(9421581, 9601188, 9421589, 840_500L),
            crystal(9421589, 9601189, 9421589, 2_664_500L),
            crystal(8800102, 9210002, 8800102, 16_200_000L),
            crystal(8810122, 9210022, 8810122, 1_352_000L),
            crystal(8810214, 9210020, 8810214, 882_000L),
            crystal(8820001, 9210025, 8820001, 1_404_500L),
            crystal(8820212, 9210026, 8820212, 12_800_000L),
            crystal(8880200, 9210008, 8880200, 1_250_000L),
            crystal(8900003, 9210010, 8900000, 16_200_000L),
            crystal(8900103, 9210009, 8900100, 968_000L),
            crystal(8910000, 9210012, 8910000, 16_200_000L),
            crystal(8910100, 9210011, 8910100, 968_000L),
            crystal(8920006, 9210014, 8920000, 16_200_000L),
            crystal(8920106, 9210013, 8920100, 968_000L),
            crystal(8930000, 9210016, 8930000, 21_012_500L),
            crystal(8930100, 9210015, 8930100, 968_000L),
            crystal(9450022, 9601190, 9450022, 16_200_000L),
            crystal(9601623, 9601635, 9601623, 28_800_000L),
            crystal(8881103, 9210029, 8881100, 32_512_500L), // GMS shop icon
            crystal(8880915, 9210063, 8880845, 206_250_000L), // GMS shop icon
            crystal(8881624, 9210069, 8881601, 560_000_000L), // GMS shop icon
            crystal(8881805, 9210074, 8881710, 1_176_000_000L), // GMS shop icon
            crystal(8881058, 9210062, 8880802, 1_040_000_000L), // GMS shop icon
            crystal(8881775, 9210073, 8881710, 588_000_000L), // GMS shop icon
            crystal(8881038, 9210061, 8880802, 520_000_000L), // GMS shop icon
            crystal(8881384, 9210068, 8881320, 749_000_000L), // GMS shop icon
            crystal(8881745, 9210072, 8881710, 273_000_000L), // GMS shop icon
            crystal(8880845, 9210059, 8880845, 301_300_000L), // GMS shop icon
            crystal(8881018, 9210060, 8880802, 187_500_000L), // GMS shop icon
            crystal(8880808, 9210056, 8880802, 260_000_000L), // GMS shop icon
            crystal(8881334, 9210067, 8881320, 420_000_000L), // GMS shop icon
            crystal(8880975, 9210065, 8880845, 1_205_200_000L), // GMS shop icon
            crystal(8881674, 9210070, 8881601, 840_000_000L), // GMS shop icon
            crystal(8880945, 9210064, 8880845, 598_000_000L), // GMS shop icon
            crystal(8880532, 9210057, 8880532, 3_600_000_000L) // Extreme Black Mage: shop list only (crystalFor gives the drop)
    );

    /** Extreme Black Mage (the Genesis Crux of the Extreme map): GMS's own crystal. */
    public static final int BLACK_MAGE_EXTREME_CRYSTAL = 9210057;
    public static final int BLACK_MAGE_EXTREME_ICON = 8880532;
    public static final long BLACK_MAGE_EXTREME_PRICE = 3_600_000_000L;

    private static Map.Entry<Integer, long[]> crystal(int key, int name, int icon, long price) {
        return Map.entry(key, new long[]{name, icon, price});
    }

    // weekly-content crystal droppers that aren't a BossCooldown's own monster (reward boxes, chests, Damien's swords, ...)
    private static final java.util.Set<Integer> WEEKLY_CRYSTAL_EXTRA = java.util.Set.of(8880518, 8880615, 8950111, 8950112,
            9210046, 9210051, 9210052, 9450022);

    /**
     * GMS v271: a weekly (or monthly) boss drops the weekly Intense Power Crystal (4001928), a daily one the daily crystal
     * (4001886), as on GMS.
     */
    public static boolean isWeeklyCrystal(int templateId) {
        int id = lootId(templateId);
        if (WEEKLY_CRYSTAL_EXTRA.contains(id)) {
            return true;
        }
        var bc = net.swordie.ms.enums.BossCooldown.byTemplateId(id);
        return bc != null && bc.getLimit() != net.swordie.ms.enums.BossCooldown.Limit.DAILY;
    }

    /** The crystal's name id and price for a monster on a map (Extreme Black Mage has its own). */
    public static long[] crystalFor(int templateId, int fieldId) {
        if (templateId == BossConstants.GENESIS_CRUX && BossConstants.getBlackMageMode(fieldId) == 1) {
            return new long[]{BLACK_MAGE_EXTREME_CRYSTAL, BLACK_MAGE_EXTREME_ICON, BLACK_MAGE_EXTREME_PRICE};
        }
        var ui = getPowerCrystalTemplateIdOverrideForUI(templateId);
        return new long[]{ui == null ? templateId : ui.getLeft(), ui == null ? templateId : ui.getRight(),
                getPowerCrystalMesoByTemplateId(templateId)};
    }

    public static long getPowerCrystalMesoByTemplateId(int templateId) {
        long[] v271 = V271_CRYSTALS.get(lootId(templateId));
        if (v271 != null) {
            return v271[2];
        }
        return PowerCrystalMesoByTemplateId.getOrDefault(lootId(templateId), 0L);
    }

    public static Tuple<Integer, Integer> getPowerCrystalTemplateIdOverrideForUI(int mapEntry) {
        long[] v271 = V271_CRYSTALS.get(mapEntry);
        if (v271 != null) {
            return new Tuple<>((int) v271[0], (int) v271[1]);
        }
        return PowerCrystalTemplateIdOverrideForUI.getOrDefault(mapEntry, null);
    }

    public static List<Map.Entry<Integer, Long>> getPowerCrystalMesos() {
        List<Map.Entry<Integer, Long>> list = new ArrayList<>();
        for (var e : PowerCrystalMesoByTemplateId.entrySet()) {
            list.add(Map.entry(e.getKey(), getPowerCrystalMesoByTemplateId(e.getKey())));
        }
        list.add(Map.entry(BLACK_MAGE_EXTREME_ICON, BLACK_MAGE_EXTREME_PRICE)); // GMS lists it as its own crystal
        return list;
    }

    public static boolean isDropPowerCrystal(int templateId) {
        return PowerCrystalMesoByTemplateId.containsKey(lootId(templateId));
    }
}

package net.swordie.ms.constants;

import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.util.Util;

import java.util.*;

/**
 * The official Soul Weapon values.
 * <p>
 * Normal souls: the bonus potential each soul gives, as written in its own description (String.wz/Consume.img, e.g.
 * "Clever Crimson Queen Soul: Bonus Potential: INT +24"). The server used Item.wz ItemOption levels from the weapon's
 * level instead, which gave other numbers (and some souls a random line).
 * <p>
 * Magnificent souls: one of seven lines, chosen when the soul is applied, with values by the boss's tier (StrategyWiki,
 * MapleStory/Soul_Weapon); the tier comes from the boss's normal souls (STR +10 B, +12 B+, +15 A, +18/+20 S, +20 with
 * ATT +5 SS, +24 SSS).
 * <p>
 * Generated from the v232 WZ data.
 */
public final class SoulWeaponData {

    private SoulWeaponData() {
    }

    // ------------------------------------------------------------------------------------------- normal souls

    private static final Map<Integer, Map<BaseStat, Double>> NORMAL = new HashMap<>();

    private static void n(int soulItemId, int value, BaseStat... stats) {
        Map<BaseStat, Double> map = new EnumMap<>(BaseStat.class);
        for (BaseStat s : stats) {
            map.put(s, (double) value);
        }
        NORMAL.put(soulItemId, Collections.unmodifiableMap(map));
    }

    static {
        n(2591010, 3, BaseStat.str);
        n(2591011, 3, BaseStat.dex);
        n(2591012, 3, BaseStat.inte);
        n(2591013, 3, BaseStat.luk);
        n(2591014, 150, BaseStat.mhp);
        n(2591015, 150, BaseStat.mmp);
        n(2591016, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591017, 3, BaseStat.str);
        n(2591018, 3, BaseStat.dex);
        n(2591019, 3, BaseStat.inte);
        n(2591020, 3, BaseStat.luk);
        n(2591021, 150, BaseStat.mhp);
        n(2591022, 150, BaseStat.mmp);
        n(2591023, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591024, 5, BaseStat.str);
        n(2591025, 5, BaseStat.dex);
        n(2591026, 5, BaseStat.inte);
        n(2591027, 5, BaseStat.luk);
        n(2591028, 200, BaseStat.mhp);
        n(2591029, 200, BaseStat.mmp);
        n(2591030, 3, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591031, 5, BaseStat.str);
        n(2591032, 5, BaseStat.dex);
        n(2591033, 5, BaseStat.inte);
        n(2591034, 5, BaseStat.luk);
        n(2591035, 200, BaseStat.mhp);
        n(2591036, 200, BaseStat.mmp);
        n(2591037, 3, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591038, 7, BaseStat.str);
        n(2591039, 7, BaseStat.dex);
        n(2591040, 7, BaseStat.inte);
        n(2591041, 7, BaseStat.luk);
        n(2591042, 300, BaseStat.mhp);
        n(2591043, 300, BaseStat.mmp);
        n(2591044, 5, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591045, 10, BaseStat.str);
        n(2591046, 10, BaseStat.dex);
        n(2591047, 10, BaseStat.inte);
        n(2591048, 10, BaseStat.luk);
        n(2591049, 7, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591050, 3, BaseStat.pad);
        n(2591051, 3, BaseStat.mad);
        n(2591052, 400, BaseStat.mhp);
        n(2591053, 5, BaseStat.ied);
        n(2591054, 5, BaseStat.bd);
        n(2591055, 20, BaseStat.str);
        n(2591056, 20, BaseStat.dex);
        n(2591057, 20, BaseStat.inte);
        n(2591058, 20, BaseStat.luk);
        n(2591059, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591060, 5, BaseStat.pad);
        n(2591061, 5, BaseStat.mad);
        n(2591062, 800, BaseStat.mhp);
        n(2591063, 5, BaseStat.ied);
        n(2591064, 5, BaseStat.bd);
        n(2591065, 15, BaseStat.str);
        n(2591066, 15, BaseStat.dex);
        n(2591067, 15, BaseStat.inte);
        n(2591068, 15, BaseStat.luk);
        n(2591069, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591070, 4, BaseStat.pad);
        n(2591071, 4, BaseStat.mad);
        n(2591072, 600, BaseStat.mhp);
        n(2591073, 5, BaseStat.ied);
        n(2591074, 5, BaseStat.bd);
        n(2591075, 24, BaseStat.str);
        n(2591076, 24, BaseStat.dex);
        n(2591077, 24, BaseStat.inte);
        n(2591078, 24, BaseStat.luk);
        n(2591079, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591080, 6, BaseStat.pad);
        n(2591081, 6, BaseStat.mad);
        n(2591082, 960, BaseStat.mhp);
        n(2591089, 3, BaseStat.str);
        n(2591090, 3, BaseStat.dex);
        n(2591091, 3, BaseStat.inte);
        n(2591092, 3, BaseStat.luk);
        n(2591093, 150, BaseStat.mhp);
        n(2591094, 150, BaseStat.mmp);
        n(2591095, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591096, 3, BaseStat.str);
        n(2591097, 3, BaseStat.dex);
        n(2591098, 3, BaseStat.inte);
        n(2591099, 3, BaseStat.luk);
        n(2591100, 150, BaseStat.mhp);
        n(2591101, 150, BaseStat.mmp);
        n(2591102, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591103, 5, BaseStat.str);
        n(2591104, 5, BaseStat.dex);
        n(2591105, 5, BaseStat.inte);
        n(2591106, 5, BaseStat.luk);
        n(2591107, 200, BaseStat.mhp);
        n(2591108, 200, BaseStat.mmp);
        n(2591109, 3, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591110, 5, BaseStat.str);
        n(2591111, 5, BaseStat.dex);
        n(2591112, 5, BaseStat.inte);
        n(2591113, 5, BaseStat.luk);
        n(2591114, 200, BaseStat.mhp);
        n(2591115, 200, BaseStat.mmp);
        n(2591116, 3, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591117, 7, BaseStat.str);
        n(2591118, 7, BaseStat.dex);
        n(2591119, 7, BaseStat.inte);
        n(2591120, 7, BaseStat.luk);
        n(2591121, 300, BaseStat.mhp);
        n(2591122, 300, BaseStat.mmp);
        n(2591123, 5, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591124, 10, BaseStat.str);
        n(2591125, 10, BaseStat.dex);
        n(2591126, 10, BaseStat.inte);
        n(2591127, 10, BaseStat.luk);
        n(2591128, 7, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591129, 3, BaseStat.pad);
        n(2591130, 3, BaseStat.mad);
        n(2591131, 400, BaseStat.mhp);
        n(2591132, 15, BaseStat.str);
        n(2591133, 15, BaseStat.dex);
        n(2591134, 15, BaseStat.inte);
        n(2591135, 15, BaseStat.luk);
        n(2591136, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591137, 4, BaseStat.pad);
        n(2591138, 4, BaseStat.mad);
        n(2591139, 600, BaseStat.mhp);
        n(2591140, 20, BaseStat.str);
        n(2591141, 20, BaseStat.dex);
        n(2591142, 20, BaseStat.inte);
        n(2591143, 20, BaseStat.luk);
        n(2591144, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591145, 5, BaseStat.pad);
        n(2591146, 5, BaseStat.mad);
        n(2591147, 800, BaseStat.mhp);
        n(2591148, 3, BaseStat.str);
        n(2591149, 3, BaseStat.dex);
        n(2591150, 3, BaseStat.inte);
        n(2591151, 3, BaseStat.luk);
        n(2591152, 150, BaseStat.mhp);
        n(2591153, 150, BaseStat.mmp);
        n(2591154, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591155, 12, BaseStat.str);
        n(2591156, 12, BaseStat.dex);
        n(2591157, 12, BaseStat.inte);
        n(2591158, 12, BaseStat.luk);
        n(2591159, 8, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591160, 3, BaseStat.pad);
        n(2591161, 3, BaseStat.mad);
        n(2591162, 500, BaseStat.mhp);
        n(2591164, 3, BaseStat.str);
        n(2591165, 3, BaseStat.dex);
        n(2591166, 3, BaseStat.inte);
        n(2591167, 3, BaseStat.luk);
        n(2591168, 150, BaseStat.mhp);
        n(2591169, 150, BaseStat.mmp);
        n(2591170, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591171, 12, BaseStat.str);
        n(2591172, 12, BaseStat.dex);
        n(2591173, 12, BaseStat.inte);
        n(2591174, 12, BaseStat.luk);
        n(2591175, 8, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591176, 3, BaseStat.pad);
        n(2591177, 3, BaseStat.mad);
        n(2591178, 500, BaseStat.mhp);
        n(2591179, 24, BaseStat.str);
        n(2591180, 24, BaseStat.dex);
        n(2591181, 24, BaseStat.inte);
        n(2591182, 24, BaseStat.luk);
        n(2591183, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591184, 6, BaseStat.pad);
        n(2591185, 6, BaseStat.mad);
        n(2591186, 960, BaseStat.mhp);
        n(2591187, 4, BaseStat.str);
        n(2591188, 4, BaseStat.dex);
        n(2591189, 4, BaseStat.inte);
        n(2591190, 4, BaseStat.luk);
        n(2591191, 180, BaseStat.mhp);
        n(2591192, 180, BaseStat.mmp);
        n(2591193, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591194, 18, BaseStat.str);
        n(2591195, 18, BaseStat.dex);
        n(2591196, 18, BaseStat.inte);
        n(2591197, 18, BaseStat.luk);
        n(2591198, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591199, 4, BaseStat.pad);
        n(2591200, 4, BaseStat.mad);
        n(2591201, 700, BaseStat.mhp);
        n(2591203, 4, BaseStat.str);
        n(2591204, 4, BaseStat.dex);
        n(2591205, 4, BaseStat.inte);
        n(2591206, 4, BaseStat.luk);
        n(2591207, 180, BaseStat.mhp);
        n(2591208, 180, BaseStat.mmp);
        n(2591209, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591210, 18, BaseStat.str);
        n(2591211, 18, BaseStat.dex);
        n(2591212, 18, BaseStat.inte);
        n(2591213, 18, BaseStat.luk);
        n(2591214, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591215, 4, BaseStat.pad);
        n(2591216, 4, BaseStat.mad);
        n(2591217, 700, BaseStat.mhp);
        n(2591218, 7, BaseStat.str);
        n(2591219, 7, BaseStat.dex);
        n(2591220, 7, BaseStat.inte);
        n(2591221, 7, BaseStat.dex);
        n(2591222, 300, BaseStat.mhp);
        n(2591223, 300, BaseStat.mmp);
        n(2591224, 5, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591225, 15, BaseStat.str);
        n(2591226, 15, BaseStat.dex);
        n(2591227, 15, BaseStat.inte);
        n(2591228, 15, BaseStat.luk);
        n(2591229, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591230, 4, BaseStat.pad);
        n(2591231, 4, BaseStat.mad);
        n(2591232, 600, BaseStat.mhp);
        n(2591234, 7, BaseStat.str);
        n(2591235, 7, BaseStat.dex);
        n(2591236, 7, BaseStat.inte);
        n(2591237, 7, BaseStat.dex);
        n(2591238, 300, BaseStat.mhp);
        n(2591239, 300, BaseStat.mmp);
        n(2591240, 5, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591241, 15, BaseStat.str);
        n(2591242, 15, BaseStat.dex);
        n(2591243, 15, BaseStat.inte);
        n(2591244, 15, BaseStat.luk);
        n(2591245, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591246, 4, BaseStat.pad);
        n(2591247, 4, BaseStat.mad);
        n(2591248, 600, BaseStat.mhp);
        n(2591249, 3, BaseStat.str);
        n(2591250, 3, BaseStat.dex);
        n(2591251, 3, BaseStat.inte);
        n(2591252, 3, BaseStat.luk);
        n(2591253, 150, BaseStat.mhp);
        n(2591254, 150, BaseStat.mmp);
        n(2591255, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591256, 24, BaseStat.str);
        n(2591257, 24, BaseStat.dex);
        n(2591258, 24, BaseStat.inte);
        n(2591259, 24, BaseStat.luk);
        n(2591260, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591261, 6, BaseStat.pad);
        n(2591262, 6, BaseStat.mad);
        n(2591263, 960, BaseStat.mhp);
        n(2591265, 3, BaseStat.str);
        n(2591266, 3, BaseStat.dex);
        n(2591267, 3, BaseStat.inte);
        n(2591268, 3, BaseStat.luk);
        n(2591269, 150, BaseStat.mhp);
        n(2591270, 150, BaseStat.mmp);
        n(2591271, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591272, 24, BaseStat.str);
        n(2591273, 24, BaseStat.dex);
        n(2591274, 24, BaseStat.inte);
        n(2591275, 24, BaseStat.luk);
        n(2591276, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591277, 6, BaseStat.pad);
        n(2591278, 6, BaseStat.mad);
        n(2591279, 960, BaseStat.mhp);
        n(2591288, 24, BaseStat.str);
        n(2591289, 24, BaseStat.dex);
        n(2591290, 24, BaseStat.inte);
        n(2591291, 24, BaseStat.luk);
        n(2591292, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591293, 6, BaseStat.pad);
        n(2591294, 6, BaseStat.mad);
        n(2591295, 960, BaseStat.mhp);
        n(2591297, 20, BaseStat.str);
        n(2591298, 18, BaseStat.dex);
        n(2591299, 18, BaseStat.inte);
        n(2591300, 18, BaseStat.luk);
        n(2591301, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591302, 4, BaseStat.pad);
        n(2591303, 4, BaseStat.mad);
        n(2591304, 700, BaseStat.mhp);
        n(2591306, 18, BaseStat.str);
        n(2591307, 18, BaseStat.dex);
        n(2591308, 20, BaseStat.inte);
        n(2591309, 18, BaseStat.luk);
        n(2591310, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591311, 4, BaseStat.pad);
        n(2591312, 4, BaseStat.mad);
        n(2591313, 700, BaseStat.mhp);
        n(2591315, 18, BaseStat.str);
        n(2591316, 18, BaseStat.dex);
        n(2591317, 18, BaseStat.inte);
        n(2591318, 20, BaseStat.luk);
        n(2591319, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591320, 4, BaseStat.pad);
        n(2591321, 4, BaseStat.mad);
        n(2591322, 700, BaseStat.mhp);
        n(2591324, 18, BaseStat.str);
        n(2591325, 20, BaseStat.dex);
        n(2591326, 18, BaseStat.inte);
        n(2591327, 18, BaseStat.luk);
        n(2591328, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591329, 4, BaseStat.pad);
        n(2591330, 4, BaseStat.mad);
        n(2591331, 700, BaseStat.mhp);
        n(2591333, 18, BaseStat.str);
        n(2591334, 18, BaseStat.dex);
        n(2591335, 18, BaseStat.inte);
        n(2591336, 18, BaseStat.luk);
        n(2591337, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591338, 4, BaseStat.pad);
        n(2591339, 4, BaseStat.mad);
        n(2591340, 700, BaseStat.mhp);
        n(2591342, 20, BaseStat.str);
        n(2591343, 18, BaseStat.dex);
        n(2591344, 18, BaseStat.inte);
        n(2591345, 18, BaseStat.luk);
        n(2591346, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591347, 4, BaseStat.pad);
        n(2591348, 4, BaseStat.mad);
        n(2591349, 700, BaseStat.mhp);
        n(2591350, 18, BaseStat.str);
        n(2591351, 18, BaseStat.dex);
        n(2591352, 20, BaseStat.inte);
        n(2591353, 18, BaseStat.luk);
        n(2591354, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591355, 4, BaseStat.pad);
        n(2591356, 4, BaseStat.mad);
        n(2591357, 700, BaseStat.mhp);
        n(2591358, 18, BaseStat.str);
        n(2591359, 18, BaseStat.dex);
        n(2591360, 18, BaseStat.inte);
        n(2591361, 20, BaseStat.luk);
        n(2591362, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591363, 4, BaseStat.pad);
        n(2591364, 4, BaseStat.mad);
        n(2591365, 700, BaseStat.mhp);
        n(2591366, 18, BaseStat.str);
        n(2591367, 20, BaseStat.dex);
        n(2591368, 18, BaseStat.inte);
        n(2591369, 18, BaseStat.luk);
        n(2591370, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591371, 4, BaseStat.pad);
        n(2591372, 4, BaseStat.mad);
        n(2591373, 700, BaseStat.mhp);
        n(2591374, 18, BaseStat.str);
        n(2591375, 18, BaseStat.dex);
        n(2591376, 18, BaseStat.inte);
        n(2591377, 18, BaseStat.luk);
        n(2591378, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591379, 4, BaseStat.pad);
        n(2591380, 4, BaseStat.mad);
        n(2591381, 700, BaseStat.mhp);
        n(2591383, 20, BaseStat.str);
        n(2591384, 20, BaseStat.dex);
        n(2591385, 20, BaseStat.inte);
        n(2591386, 20, BaseStat.luk);
        n(2591387, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591388, 5, BaseStat.pad);
        n(2591389, 5, BaseStat.mad);
        n(2591390, 800, BaseStat.mhp);
        n(2591392, 20, BaseStat.str);
        n(2591393, 20, BaseStat.dex);
        n(2591394, 20, BaseStat.inte);
        n(2591395, 20, BaseStat.luk);
        n(2591396, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591397, 5, BaseStat.pad);
        n(2591398, 5, BaseStat.mad);
        n(2591399, 800, BaseStat.mhp);
        n(2591401, 24, BaseStat.str);
        n(2591402, 24, BaseStat.dex);
        n(2591403, 24, BaseStat.inte);
        n(2591404, 24, BaseStat.luk);
        n(2591405, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591406, 6, BaseStat.pad);
        n(2591407, 6, BaseStat.mad);
        n(2591408, 960, BaseStat.mhp);
        n(2591410, 24, BaseStat.str);
        n(2591411, 24, BaseStat.dex);
        n(2591412, 24, BaseStat.inte);
        n(2591413, 24, BaseStat.luk);
        n(2591414, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591415, 6, BaseStat.pad);
        n(2591416, 6, BaseStat.mad);
        n(2591417, 960, BaseStat.mhp);
        n(2591419, 24, BaseStat.str);
        n(2591420, 24, BaseStat.dex);
        n(2591421, 24, BaseStat.inte);
        n(2591422, 24, BaseStat.luk);
        n(2591423, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591424, 6, BaseStat.pad);
        n(2591425, 6, BaseStat.mad);
        n(2591426, 960, BaseStat.mhp);
        n(2591428, 20, BaseStat.str);
        n(2591429, 20, BaseStat.dex);
        n(2591430, 20, BaseStat.inte);
        n(2591431, 20, BaseStat.luk);
        n(2591432, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591433, 5, BaseStat.pad);
        n(2591434, 5, BaseStat.mad);
        n(2591435, 800, BaseStat.mhp);
        n(2591436, 20, BaseStat.str);
        n(2591437, 20, BaseStat.dex);
        n(2591438, 20, BaseStat.inte);
        n(2591439, 20, BaseStat.luk);
        n(2591440, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591441, 5, BaseStat.pad);
        n(2591442, 5, BaseStat.mad);
        n(2591443, 800, BaseStat.mhp);
        n(2591444, 24, BaseStat.str);
        n(2591445, 24, BaseStat.dex);
        n(2591446, 24, BaseStat.inte);
        n(2591447, 24, BaseStat.luk);
        n(2591448, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591449, 6, BaseStat.pad);
        n(2591450, 6, BaseStat.mad);
        n(2591451, 960, BaseStat.mhp);
        n(2591452, 24, BaseStat.str);
        n(2591453, 24, BaseStat.dex);
        n(2591454, 24, BaseStat.inte);
        n(2591455, 24, BaseStat.luk);
        n(2591456, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591457, 6, BaseStat.pad);
        n(2591458, 6, BaseStat.mad);
        n(2591459, 960, BaseStat.mhp);
        n(2591460, 24, BaseStat.str);
        n(2591461, 24, BaseStat.dex);
        n(2591462, 24, BaseStat.inte);
        n(2591463, 24, BaseStat.luk);
        n(2591464, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591465, 6, BaseStat.pad);
        n(2591466, 6, BaseStat.mad);
        n(2591467, 960, BaseStat.mhp);
        n(2591468, 12, BaseStat.str);
        n(2591469, 12, BaseStat.dex);
        n(2591470, 12, BaseStat.inte);
        n(2591471, 12, BaseStat.luk);
        n(2591472, 8, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591473, 3, BaseStat.pad);
        n(2591474, 3, BaseStat.mad);
        n(2591475, 500, BaseStat.mhp);
        n(2591476, 12, BaseStat.str);
        n(2591477, 12, BaseStat.dex);
        n(2591478, 12, BaseStat.inte);
        n(2591479, 12, BaseStat.luk);
        n(2591480, 8, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591481, 3, BaseStat.pad);
        n(2591482, 3, BaseStat.mad);
        n(2591483, 500, BaseStat.mhp);
        n(2591487, 10, BaseStat.str);
        n(2591488, 10, BaseStat.dex);
        n(2591489, 10, BaseStat.inte);
        n(2591490, 10, BaseStat.luk);
        n(2591491, 7, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591492, 3, BaseStat.pad);
        n(2591493, 3, BaseStat.mad);
        n(2591494, 400, BaseStat.mhp);
        n(2591509, 20, BaseStat.str);
        n(2591510, 20, BaseStat.dex);
        n(2591511, 20, BaseStat.inte);
        n(2591512, 20, BaseStat.luk);
        n(2591513, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591514, 5, BaseStat.pad);
        n(2591515, 5, BaseStat.mad);
        n(2591516, 800, BaseStat.mhp);
        n(2591518, 20, BaseStat.str);
        n(2591519, 20, BaseStat.dex);
        n(2591520, 20, BaseStat.inte);
        n(2591521, 20, BaseStat.luk);
        n(2591522, 12, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591523, 5, BaseStat.pad);
        n(2591524, 5, BaseStat.mad);
        n(2591525, 800, BaseStat.mhp);
        n(2591528, 4, BaseStat.str);
        n(2591529, 4, BaseStat.dex);
        n(2591530, 4, BaseStat.inte);
        n(2591531, 4, BaseStat.luk);
        n(2591532, 180, BaseStat.mhp);
        n(2591533, 180, BaseStat.mmp);
        n(2591534, 2, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591544, 10, BaseStat.str);
        n(2591545, 10, BaseStat.dex);
        n(2591546, 10, BaseStat.inte);
        n(2591547, 10, BaseStat.luk);
        n(2591548, 7, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591549, 3, BaseStat.pad);
        n(2591550, 3, BaseStat.mad);
        n(2591551, 400, BaseStat.mhp);
        n(2591553, 10, BaseStat.str);
        n(2591554, 10, BaseStat.dex);
        n(2591555, 10, BaseStat.inte);
        n(2591556, 10, BaseStat.luk);
        n(2591557, 7, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591558, 3, BaseStat.pad);
        n(2591559, 3, BaseStat.mad);
        n(2591560, 400, BaseStat.mhp);
        n(2591562, 6, BaseStat.pddR);
        n(2591563, 3, BaseStat.strR, BaseStat.dexR, BaseStat.intR, BaseStat.lukR);
        n(2591564, 24, BaseStat.str);
        n(2591565, 24, BaseStat.dex);
        n(2591566, 24, BaseStat.inte);
        n(2591567, 24, BaseStat.luk);
        n(2591568, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591569, 6, BaseStat.pad);
        n(2591570, 6, BaseStat.mad);
        n(2591571, 960, BaseStat.mhp);
        n(2591573, 24, BaseStat.str);
        n(2591574, 24, BaseStat.dex);
        n(2591575, 24, BaseStat.inte);
        n(2591576, 24, BaseStat.luk);
        n(2591577, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591578, 6, BaseStat.pad);
        n(2591579, 6, BaseStat.mad);
        n(2591580, 960, BaseStat.mhp);
        n(2591582, 24, BaseStat.str);
        n(2591583, 24, BaseStat.dex);
        n(2591584, 24, BaseStat.inte);
        n(2591585, 24, BaseStat.luk);
        n(2591586, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591587, 6, BaseStat.pad);
        n(2591588, 6, BaseStat.mad);
        n(2591589, 960, BaseStat.mhp);
        n(2591591, 24, BaseStat.str);
        n(2591592, 24, BaseStat.dex);
        n(2591593, 24, BaseStat.inte);
        n(2591594, 24, BaseStat.luk);
        n(2591595, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591596, 6, BaseStat.pad);
        n(2591597, 6, BaseStat.mad);
        n(2591598, 960, BaseStat.mhp);
        n(2591602, 15, BaseStat.str);
        n(2591603, 15, BaseStat.dex);
        n(2591604, 15, BaseStat.inte);
        n(2591605, 15, BaseStat.luk);
        n(2591606, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591607, 4, BaseStat.pad);
        n(2591608, 4, BaseStat.mad);
        n(2591609, 600, BaseStat.mhp);
        n(2591611, 15, BaseStat.str);
        n(2591612, 15, BaseStat.dex);
        n(2591613, 15, BaseStat.inte);
        n(2591614, 15, BaseStat.luk);
        n(2591615, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591616, 4, BaseStat.pad);
        n(2591617, 4, BaseStat.mad);
        n(2591618, 600, BaseStat.mhp);
        n(2591620, 15, BaseStat.str);
        n(2591621, 15, BaseStat.dex);
        n(2591622, 15, BaseStat.inte);
        n(2591623, 15, BaseStat.luk);
        n(2591624, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591625, 4, BaseStat.pad);
        n(2591626, 4, BaseStat.mad);
        n(2591627, 600, BaseStat.mhp);
        n(2591632, 24, BaseStat.str);
        n(2591633, 24, BaseStat.dex);
        n(2591634, 24, BaseStat.inte);
        n(2591635, 24, BaseStat.luk);
        n(2591636, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591637, 6, BaseStat.pad);
        n(2591638, 6, BaseStat.mad);
        n(2591639, 960, BaseStat.mhp);
        n(2591641, 24, BaseStat.str);
        n(2591642, 24, BaseStat.dex);
        n(2591643, 24, BaseStat.inte);
        n(2591644, 24, BaseStat.luk);
        n(2591645, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591646, 6, BaseStat.pad);
        n(2591647, 6, BaseStat.mad);
        n(2591648, 960, BaseStat.mhp);
        n(2591651, 24, BaseStat.str);
        n(2591652, 24, BaseStat.dex);
        n(2591653, 24, BaseStat.inte);
        n(2591654, 24, BaseStat.luk);
        n(2591655, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591656, 6, BaseStat.pad);
        n(2591657, 6, BaseStat.mad);
        n(2591658, 960, BaseStat.mhp);
        n(2591660, 24, BaseStat.str);
        n(2591661, 24, BaseStat.dex);
        n(2591662, 24, BaseStat.inte);
        n(2591663, 24, BaseStat.luk);
        n(2591664, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591665, 6, BaseStat.pad);
        n(2591666, 6, BaseStat.mad);
        n(2591667, 960, BaseStat.mhp);
        n(2591668, 24, BaseStat.str);
        n(2591669, 24, BaseStat.dex);
        n(2591670, 24, BaseStat.inte);
        n(2591671, 24, BaseStat.luk);
        n(2591672, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591673, 6, BaseStat.pad);
        n(2591674, 6, BaseStat.mad);
        n(2591675, 960, BaseStat.mhp);
        n(2591677, 24, BaseStat.str);
        n(2591678, 24, BaseStat.dex);
        n(2591679, 24, BaseStat.inte);
        n(2591680, 24, BaseStat.luk);
        n(2591681, 15, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591682, 6, BaseStat.pad);
        n(2591683, 6, BaseStat.mad);
        n(2591684, 960, BaseStat.mhp);
        n(2591688, 15, BaseStat.str);
        n(2591689, 15, BaseStat.dex);
        n(2591690, 15, BaseStat.inte);
        n(2591691, 15, BaseStat.luk);
        n(2591692, 10, BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk);
        n(2591693, 4, BaseStat.pad);
        n(2591694, 4, BaseStat.mad);
        n(2591695, 600, BaseStat.mhp);
    }

    // ---------------------------------------------------------------------------------------- Magnificent souls

    /** Line codes (Item.wz ItemOption ids, so the client shows the right kind of line). */
    public static final int LINE_HP = 32005;
    public static final int LINE_ALL_STAT = 32086;
    public static final int LINE_ATT = 32011;
    public static final int LINE_ATT_R = 32051;
    public static final int LINE_MATT = 32012;
    public static final int LINE_MATT_R = 32053;
    public static final int LINE_CRIT = 32057;
    public static final int LINE_IED = 32291;
    public static final int LINE_BOSS = 32601;

    private enum Tier {
        // HP, All Stat, ATT/MATT, Crit %, IED %, Boss %; SSS: All Stat and ATT/MATT are %
        B(1000, 10, 5, 5, 3, 3),
        B_PLUS(1100, 12, 6, 6, 3, 3),
        A(1200, 15, 7, 7, 4, 4),
        S(1300, 17, 8, 8, 4, 4),
        SS(1500, 20, 10, 10, 5, 5),
        SSS(2000, 5, 3, 12, 7, 7);

        final int hp, allStat, att, crit, ied, boss;

        Tier(int hp, int allStat, int att, int crit, int ied, int boss) {
            this.hp = hp;
            this.allStat = allStat;
            this.att = att;
            this.crit = crit;
            this.ied = ied;
            this.boss = boss;
        }
    }

    /** S tier souls with one stronger line. */
    private enum Special {
        NONE, VICIOUS_HUNTER, BAD_BRAWLER, BLACK_KNIGHT, MAD_MAGE, RAMPANT_CYBORG
    }

    private static final class MagTier {
        final Tier tier;
        final Special special;

        MagTier(Tier tier, Special special) {
            this.tier = tier;
            this.special = special;
        }
    }

    private static final Map<Integer, MagTier> MAGNIFICENT = new HashMap<>();

    static {
        MAGNIFICENT.put(2591202, new MagTier(Tier.S, Special.NONE)); // Arkarium
        MAGNIFICENT.put(2591341, new MagTier(Tier.S, Special.BAD_BRAWLER)); // Bad Brawler
        MAGNIFICENT.put(2591085, new MagTier(Tier.B, Special.NONE)); // Balrog
        MAGNIFICENT.put(2591305, new MagTier(Tier.S, Special.BLACK_KNIGHT)); // Black Knight
        MAGNIFICENT.put(2591409, new MagTier(Tier.SSS, Special.NONE)); // Crimson Queen
        MAGNIFICENT.put(2591088, new MagTier(Tier.SSS, Special.NONE)); // Cygnus
        MAGNIFICENT.put(2591572, new MagTier(Tier.SSS, Special.NONE)); // Damien
        MAGNIFICENT.put(2591581, new MagTier(Tier.SSS, Special.NONE)); // Damien
        MAGNIFICENT.put(2591676, new MagTier(Tier.SSS, Special.NONE)); // Darknell
        MAGNIFICENT.put(2591697, new MagTier(Tier.SSS, Special.NONE)); // Darknell
        MAGNIFICENT.put(2591610, new MagTier(Tier.A, Special.NONE)); // Elunite Elemental
        MAGNIFICENT.put(2591696, new MagTier(Tier.A, Special.NONE)); // Gigatoad
        MAGNIFICENT.put(2591484, new MagTier(Tier.B_PLUS, Special.NONE)); // Gold Dragon
        MAGNIFICENT.put(2591233, new MagTier(Tier.A, Special.NONE)); // Hilla
        MAGNIFICENT.put(2591427, new MagTier(Tier.SSS, Special.NONE)); // Lotus
        MAGNIFICENT.put(2591590, new MagTier(Tier.SSS, Special.NONE)); // Lucid
        MAGNIFICENT.put(2591314, new MagTier(Tier.S, Special.MAD_MAGE)); // Mad Mage
        MAGNIFICENT.put(2591264, new MagTier(Tier.SSS, Special.NONE)); // Magnus
        MAGNIFICENT.put(2591296, new MagTier(Tier.SSS, Special.NONE)); // Murgoth
        MAGNIFICENT.put(2591561, new MagTier(Tier.B, Special.NONE)); // Nenne
        MAGNIFICENT.put(2591619, new MagTier(Tier.A, Special.NONE)); // Papulatus
        MAGNIFICENT.put(2591391, new MagTier(Tier.SS, Special.NONE)); // Pierre
        MAGNIFICENT.put(2591087, new MagTier(Tier.SS, Special.NONE)); // Pink Bean
        MAGNIFICENT.put(2591486, new MagTier(Tier.SS, Special.NONE)); // Pink Bean
        MAGNIFICENT.put(2591323, new MagTier(Tier.S, Special.RAMPANT_CYBORG)); // Rampant Cyborg
        MAGNIFICENT.put(2591485, new MagTier(Tier.B_PLUS, Special.NONE)); // Red Tiger
        MAGNIFICENT.put(2591552, new MagTier(Tier.B, Special.NONE)); // Tutu
        MAGNIFICENT.put(2591517, new MagTier(Tier.SS, Special.NONE)); // Ursus
        MAGNIFICENT.put(2591418, new MagTier(Tier.SSS, Special.NONE)); // Vellum
        MAGNIFICENT.put(2591659, new MagTier(Tier.SSS, Special.NONE)); // Verus Hilla
        MAGNIFICENT.put(2591698, new MagTier(Tier.SSS, Special.NONE)); // Verus Hilla
        MAGNIFICENT.put(2591332, new MagTier(Tier.S, Special.VICIOUS_HUNTER)); // Vicious Hunter
        MAGNIFICENT.put(2591400, new MagTier(Tier.SS, Special.NONE)); // Von Bon
        MAGNIFICENT.put(2591086, new MagTier(Tier.A, Special.NONE)); // Von Leon
        MAGNIFICENT.put(2591640, new MagTier(Tier.SSS, Special.NONE)); // Will
        MAGNIFICENT.put(2591163, new MagTier(Tier.B_PLUS, Special.NONE)); // Zakum
    }

    public static boolean isMagnificentSoul(int soulItemId) {
        return MAGNIFICENT.containsKey(soulItemId);
    }

    /**
     * @return a random line for a Magnificent soul (one of seven, equal odds), or 0 if it isn't one
     */
    public static int getRandomMagnificentLine(int soulItemId) {
        MagTier mt = MAGNIFICENT.get(soulItemId);
        if (mt == null) {
            return 0;
        }
        boolean sss = mt.tier == Tier.SSS;
        List<Integer> lines = List.of(LINE_HP, LINE_ALL_STAT, sss ? LINE_ATT_R : LINE_ATT, sss ? LINE_MATT_R : LINE_MATT,
                LINE_CRIT, LINE_IED, LINE_BOSS);
        return Util.getRandomFromCollection(lines);
    }

    /**
     * @param soulItemId the soul item (2591xxx) on the weapon
     * @param line       the weapon's soul option (for Magnificent souls: the line chosen when it was applied)
     * @return the soul's stats, or null if this soul isn't known (then the Item.wz option is used)
     */
    public static Map<BaseStat, Double> getSoulStats(int soulItemId, int line) {
        Map<BaseStat, Double> normal = NORMAL.get(soulItemId);
        if (normal != null) {
            return normal;
        }
        MagTier mt = MAGNIFICENT.get(soulItemId);
        if (mt == null) {
            return null;
        }
        Tier t = mt.tier;
        Special e = mt.special;
        Map<BaseStat, Double> res = new EnumMap<>(BaseStat.class);
        switch (line) {
            case LINE_HP:
                res.put(BaseStat.mhp, (double) (e == Special.VICIOUS_HUNTER ? 1500 : t.hp));
                break;
            case LINE_ALL_STAT:
                int as = e == Special.BAD_BRAWLER ? 20 : t.allStat;
                if (t == Tier.SSS) {
                    for (BaseStat s : List.of(BaseStat.strR, BaseStat.dexR, BaseStat.intR, BaseStat.lukR)) {
                        res.put(s, (double) as);
                    }
                } else {
                    for (BaseStat s : List.of(BaseStat.str, BaseStat.dex, BaseStat.inte, BaseStat.luk)) {
                        res.put(s, (double) as);
                    }
                }
                break;
            case LINE_ATT:
            case LINE_ATT_R:
                res.put(t == Tier.SSS ? BaseStat.padR : BaseStat.pad,
                        (double) (e == Special.BLACK_KNIGHT ? 10 : t.att));
                break;
            case LINE_MATT:
            case LINE_MATT_R:
                res.put(t == Tier.SSS ? BaseStat.madR : BaseStat.mad,
                        (double) (e == Special.MAD_MAGE ? 10 : t.att));
                break;
            case LINE_CRIT:
                res.put(BaseStat.cr, (double) (e == Special.RAMPANT_CYBORG ? 10 : t.crit));
                break;
            case LINE_IED:
                res.put(BaseStat.ied, (double) t.ied);
                break;
            case LINE_BOSS:
                res.put(BaseStat.bd, (double) t.boss);
                break;
            default:
                return null; // a line from before this table (the Item.wz option is used)
        }
        return res;
    }

    // ------------------------------------------------------------------------------------------------- gauge

    /**
     * Soul gauge from one kill: 3-5 for a monster near the character's level, 1-3 for one far below it (official).
     */
    public static int getGaugeFromKill(int charLevel, int mobLevel) {
        return mobLevel >= charLevel - 20 ? Util.getRandom(3, 6) : Util.getRandom(1, 4); // upper bound exclusive;
    }

    /**
     * ATT and Magic ATT a Soul Weapon gives by how full its gauge is (official): +10 to +20 with a soul applied,
     * +1 to +10 without one.
     */
    public static int getGaugeAttBonus(int gauge, boolean hasSoul) {
        if (gauge <= 0) {
            return 0;
        }
        int step = Math.min(5, (gauge - 1) / 100); // 1-100: 0, 101-200: 1, ... 501+: 5
        if (hasSoul) {
            return 10 + 2 * step;
        }
        int[] empty = {1, 2, 4, 6, 8, 10};
        return empty[step];
    }

    // ------------------------------------------------------------------------------------------- soul drops

    /** Chance (out of 10000) that a boss clear (the last part or reward box of a boss fight) drops one of its souls. */
    public static final int BOSS_SOUL_CHANCE = 1500;
    /** Chance (out of 10000) that a field boss with souls (Rex, Pianus, Mu Gong, ...) drops one. */
    public static final int AREA_BOSS_SOUL_CHANCE = 500;
    /** Of the souls dropped, this many percent are Magnificent (when the boss has one). */
    public static final int MAGNIFICENT_SOUL_PERCENT = 10;
    /** Mob names that don't contain their boss's name. */
    private static final Map<String, String> SOUL_NAME_ALIASES = Map.of("Final Music Box", "Lucid", "Lost Light Mirror", "Will");

    /** boss name -> {normal souls, Magnificent souls}, from the souls' names ("Beefy Zakum Soul" -> "Zakum") */
    private static volatile Map<String, List<List<Integer>>> soulsByBoss;

    private static Map<String, List<List<Integer>>> getSoulsByBoss() {
        Map<String, List<List<Integer>>> res = soulsByBoss;
        if (res != null) {
            return res;
        }
        res = new HashMap<>();
        Set<Integer> ids = new TreeSet<>(NORMAL.keySet());
        ids.addAll(MAGNIFICENT.keySet());
        for (int id : ids) {
            String name = net.swordie.ms.loaders.StringData.getItemStringById(id);
            if (name == null || !name.endsWith(" Soul") || name.indexOf(' ') < 0) {
                continue;
            }
            String boss = name.substring(name.indexOf(' ') + 1, name.length() - " Soul".length()).trim();
            if (boss.isEmpty()) {
                continue;
            }
            var lists = res.computeIfAbsent(boss, k -> List.of(new ArrayList<>(), new ArrayList<>()));
            lists.get(isMagnificentSoul(id) ? 1 : 0).add(id);
        }
        soulsByBoss = res;
        return res;
    }

    /** @return the souls' boss name this mob counts as (null: none), the longest one its name holds */
    private static String getSoulBoss(int mobTemplateId, boolean exactName) {
        String mobName = net.swordie.ms.loaders.StringData.getMobStringById(mobTemplateId);
        if (mobName == null) {
            return null;
        }
        mobName = SOUL_NAME_ALIASES.getOrDefault(mobName, mobName);
        String best = null;
        for (String boss : getSoulsByBoss().keySet()) {
            boolean match = exactName ? mobName.equalsIgnoreCase(boss) || mobName.equalsIgnoreCase("Chaos " + boss)
                    : mobName.contains(boss);
            if (match && (best == null || boss.length() > best.length())) {
                best = boss;
            }
        }
        return best;
    }

    /**
     * Rolls a soul drop for a dying mob: boss clears ({@code bossClear}) drop the souls of the boss their name holds,
     * other bosses only when their name is a soul's boss name (field bosses like Rex or Pianus; not boss parts such as
     * Zakum's arms).
     *
     * @return the soul item to drop, or 0
     */
    public static int rollSoulDrop(int mobTemplateId, boolean bossClear, boolean boss) {
        if (!bossClear && !boss) {
            return 0;
        }
        int chance = ServerFeatures.getInt(bossClear ? ServerFeatures.Feature.BOSS_SOUL_CHANCE
                : ServerFeatures.Feature.FIELD_BOSS_SOUL_CHANCE) * 100;
        if (chance <= 0 || !Util.succeedProp(chance, 10000)) {
            return 0;
        }
        String name = getSoulBoss(mobTemplateId, !bossClear);
        if (name == null) {
            return 0;
        }
        var lists = getSoulsByBoss().get(name);
        List<Integer> pool = !lists.get(1).isEmpty() && Util.succeedProp(ServerFeatures.getInt(ServerFeatures.Feature.MAGNIFICENT_SOUL_CHANCE)) ? lists.get(1)
                : lists.get(0);
        if (pool.isEmpty()) {
            pool = lists.get(1);
        }
        return pool.isEmpty() ? 0 : Util.getRandomFromCollection(pool);
    }
}

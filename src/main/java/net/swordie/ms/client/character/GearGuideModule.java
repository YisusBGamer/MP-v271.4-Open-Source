package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.EquipDropFilter;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.constants.BossLootConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.BossCooldown;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.loaders.DropData;
import net.swordie.ms.loaders.MobData;
import net.swordie.ms.loaders.StringData;
import net.swordie.orm.dao.SworDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @gear (scripts/npc/gearGuide.py): shows which equipment slots a character still has empty and where to farm items
 * for them: the bosses that drop them (from this server's drop tables and boss loot pools) and the monsters closest
 * to the character's level that drop them. Only items the character's class can use are counted.
 */
public class GearGuideModule {

    private static final Logger log = LogManager.getLogger(GearGuideModule.class);

    public enum Category {
        RING("Rings", 4, 111),
        PENDANT("Pendants", 2, 112),
        EARRINGS("Earrings", 1, 103),
        FACE("Face Accessory", 1, 101),
        EYE("Eye Accessory", 1, 102),
        BELT("Belt", 1, 113),
        SHOULDER("Shoulder", 1, 115),
        POCKET("Pocket Item", 1, 116),
        BADGE("Badge", 1, 118),
        EMBLEM("Emblem", 1, 119),
        MEDAL("Medal", 1, 114),
        HEART("Mechanical Heart", 1, 167),
        ANDROID("Android", 1, 166),
        HAT("Hat", 1, 100),
        TOP("Top / Overall", 1, 104, 105),
        BOTTOM("Bottom", 1, 106),
        SHOES("Shoes", 1, 107),
        GLOVES("Gloves", 1, 108),
        CAPE("Cape", 1, 110),
        SECONDARY("Secondary / Shield", 1, 109, 134, 135),
        WEAPON("Weapon", 1);

        private final String title;
        private final int slots;
        private final int[] prefixes;

        Category(String title, int slots, int... prefixes) {
            this.title = title;
            this.slots = slots;
            this.prefixes = prefixes;
        }

        public String getTitle() {
            return title;
        }

        public int getSlots() {
            return slots;
        }

        public boolean contains(int itemId) {
            if (!ItemConstants.isEquip(itemId)) {
                return false;
            }
            if (this == WEAPON) {
                int prefix = itemId / 10000;
                return ItemConstants.isWeapon(itemId) && prefix != 134 && prefix != 135 && prefix != 170;
            }
            int prefix = itemId / 10000;
            for (int p : prefixes) {
                if (p == prefix) {
                    return true;
                }
            }
            return false;
        }
    }

    /** A boss or monster and the items of a category it drops. */
    public static class Source {
        public final String name;
        public final int level;
        public final List<Integer> items = new ArrayList<>();

        Source(String name, int level) {
            this.name = name;
            this.level = level;
        }
    }

    public static Category[] getCategories() {
        return Category.values();
    }

    public static int countEquipped(Char chr, Category cat) {
        if (cat == Category.BOTTOM) {
            // A bottom only counts as missing for a character wearing a top that isn't an overall
            boolean top = false, bottom = false;
            for (Item item : chr.getEquippedInventory().getItems()) {
                if (item.isCash() || item.getBagIndex() >= 100 || !ItemConstants.isEquip(item.getItemId())) {
                    continue;
                }
                int prefix = item.getItemId() / 10000;
                top |= prefix == 104;
                bottom |= prefix == 106;
            }
            return bottom || !top ? 1 : 0;
        }
        int n = 0;
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (!item.isCash() && item.getBagIndex() < 100 && cat.contains(item.getItemId())) {
                n++;
            }
        }
        return Math.min(n, cat.getSlots());
    }

    // ------------------------------------------------------------------------------------------------- bosses

    private static volatile List<Object[]> bossDrops; // {name, level, Set<Integer> items}

    /** Bosses whose top mode GMS calls Chaos rather than Hard. */
    private static final Set<String> CHAOS_NAMED = Set.of("Zakum", "Horntail", "PinkBean", "Queen", "Pierre", "VonBon",
            "Vellum", "Papulatus", "Gloom");

    private static String bossName(BossCooldown bc) {
        String n = bc.name();
        String difficulty = "Normal";
        for (String suffix : new String[]{"Hard", "Easy", "Chaos", "Extreme"}) {
            if (n.endsWith(suffix) && n.length() > suffix.length()) {
                difficulty = suffix;
                n = n.substring(0, n.length() - suffix.length());
                break;
            }
        }
        if (difficulty.equals("Hard") && CHAOS_NAMED.contains(n)) {
            difficulty = "Chaos";
        }
        n = switch (n) {
            case "Queen" -> "Crimson Queen";
            case "ScarlionTarga" -> "Scarlion & Targa";
            case "OmniCln" -> "OMNI-CLN";
            default -> n.replaceAll("([a-z])([A-Z])", "$1 $2");
        };
        return n + " (" + difficulty + ")";
    }

    private static List<Object[]> getBossDrops() {
        List<Object[]> res = bossDrops;
        if (res != null) {
            return res;
        }
        res = new ArrayList<>();
        for (BossCooldown bc : BossCooldown.values()) {
            Set<Integer> items = new HashSet<>();
            int level = 0;
            for (int mobId : bc.getTemplateIds()) {
                try {
                    for (DropInfo di : DropData.getDropInfoByID(BossLootConstants.lootId(mobId))) {
                        if (!di.isMoney() && ItemConstants.isEquip(di.getItemID())) {
                            items.add(di.getItemID());
                        }
                    }
                    for (DropInfo di : BossLootConstants.getLootPoolByTemplateId(mobId)) {
                        if (!di.isMoney() && ItemConstants.isEquip(di.getItemID())) {
                            items.add(di.getItemID());
                        }
                    }
                    var info = MobData.getMobInfoById(mobId);
                    if (info != null && info.getForcedMobStat() != null) {
                        level = Math.max(level, info.getForcedMobStat().getLevel());
                    }
                } catch (Exception e) {
                    log.debug("Gear guide: could not read the drops of " + mobId, e);
                }
            }
            if (!items.isEmpty()) {
                res.add(new Object[]{bossName(bc), level, items});
            }
        }
        res.sort(Comparator.comparingInt(o -> (int) o[1]));
        bossDrops = res;
        return res;
    }

    /**
     * @return the bosses that drop items of this category the character can use, lowest level first
     */
    @SuppressWarnings("unchecked")
    public static List<Source> getBossSources(Char chr, Category cat) {
        List<Source> res = new ArrayList<>();
        for (Object[] boss : getBossDrops()) {
            Source src = new Source((String) boss[0], (int) boss[1]);
            for (int itemId : (Set<Integer>) boss[2]) {
                if (cat.contains(itemId) && EquipDropFilter.isUsableBy(itemId, chr)) {
                    src.items.add(itemId);
                }
            }
            if (!src.items.isEmpty()) {
                Collections.sort(src.items);
                res.add(src);
            }
        }
        return res;
    }

    // ------------------------------------------------------------------------------------------------- monsters

    private static final SworDao<int[]> DROP_ROW = (rs, alias) -> {
        try {
            return new int[]{rs.getInt(1), rs.getInt(2)};
        } catch (SQLException e) {
            return null;
        }
    };

    /** category -> mob id -> items */
    private static final Map<Category, Map<Integer, Set<Integer>>> monsterDrops = new ConcurrentHashMap<>();

    private static Map<Integer, Set<Integer>> getMonsterDrops(Category cat) {
        return monsterDrops.computeIfAbsent(cat, c -> {
            Map<Integer, Set<Integer>> res = new HashMap<>();
            List<int[]> ranges = new ArrayList<>();
            if (c == Category.WEAPON) {
                ranges.add(new int[]{1210000, 1339999}); // hardcode-ok
                ranges.add(new int[]{1360000, 1599999}); // hardcode-ok
            } else {
                for (int p : c.prefixes) {
                    ranges.add(new int[]{p * 10000, p * 10000 + 9999});
                }
            }
            for (int[] r : ranges) {
                try {
                    for (Object o : DatabaseManager.executeSelect(DROP_ROW,
                            "SELECT mobid, itemid FROM mob_drops WHERE itemid BETWEEN ? AND ? AND mobid > 0", "",
                            r[0], r[1])) {
                        if (o instanceof int[] row && c.contains(row[1])) {
                            res.computeIfAbsent(row[0], k -> new HashSet<>()).add(row[1]);
                        }
                    }
                } catch (Exception e) {
                    log.error("Gear guide: could not read the monster drops", e);
                }
            }
            return res;
        });
    }

    /**
     * @return up to 8 normal monsters (no bosses) that drop items of this category the character can use, closest
     * to the character's level first
     */
    public static List<Source> getMonsterSources(Char chr, Category cat) {
        List<Source> res = new ArrayList<>();
        for (var entry : getMonsterDrops(cat).entrySet()) {
            var info = MobData.getMobInfoById(entry.getKey());
            String name = StringData.getMobStringById(entry.getKey());
            if (info == null || info.isBoss() || name == null || info.getForcedMobStat() == null) {
                continue;
            }
            Source src = new Source(name, info.getForcedMobStat().getLevel());
            for (int itemId : entry.getValue()) {
                if (EquipDropFilter.isUsableBy(itemId, chr)) {
                    src.items.add(itemId);
                }
            }
            if (!src.items.isEmpty()) {
                Collections.sort(src.items);
                res.add(src);
            }
        }
        int lv = chr.getLevel();
        res.sort(Comparator.comparingInt(s -> Math.abs(s.level - lv)));
        return res.size() > 8 ? new ArrayList<>(res.subList(0, 8)) : res;
    }

    /**
     * @return whether monsters near the character's level drop generic equipment of this category (armor and
     * weapons for the character's class, see ItemConstants.getEquipMobDrops)
     */
    public static boolean hasGenericDrops(Category cat) {
        return switch (cat) {
            case HAT, TOP, BOTTOM, SHOES, GLOVES, CAPE, WEAPON -> true;
            default -> false;
        };
    }
}

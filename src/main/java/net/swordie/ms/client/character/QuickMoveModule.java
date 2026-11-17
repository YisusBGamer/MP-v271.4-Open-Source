package net.swordie.ms.client.character;

import net.swordie.ms.life.Life;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.FieldData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.world.field.MobGen;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * The map lists of @qm (scripts/npc/9072302.py): towns by region, hunting maps sorted by monster level, and boss
 * entrances sorted by required level. Maps the game data doesn't have are left out, and each map's monster levels
 * are worked out once from its spawns.
 */
public class QuickMoveModule {

    // the tables: resources/data/quick-move-*.tsv (towns, areas, hunting, brackets, bosses)
    private record Towns(String[] regions, int[][] towns) {
    }

    private static final Supplier<Towns> TOWNS = DataTable.cached("quick-move-towns", t -> {
        Map<String, List<Integer>> byRegion = new LinkedHashMap<>();
        for (DataTable.Row r : t.rows()) {
            byRegion.computeIfAbsent(r.str("region"), k -> new ArrayList<>()).add(r.i("map"));
        }
        int[][] towns = byRegion.values().stream().map(l -> l.stream().mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);
        return new Towns(byRegion.keySet().toArray(new String[0]), towns);
    });
    private static final Supplier<Map<Integer, String>> AREA_NAMES = DataTable.cached("quick-move-areas", t -> {
        Map<Integer, String> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.i("map"), r.str("area"));
        }
        return m;
    });
    /** map, from, to[, Authentic Force when Map.wz has none] */
    private static final Supplier<int[][]> HUNTING_GROUNDS = DataTable.cached("quick-move-hunting", t ->
            t.rows().stream().map(r -> r.has("authentic")
                    ? new int[]{r.i("map"), r.i("from"), r.i("to"), r.i("authentic")}
                    : new int[]{r.i("map"), r.i("from"), r.i("to")}).toArray(int[][]::new));
    /** name, from, to (character level) */
    private static final Supplier<Object[][]> HUNTING_BRACKETS = DataTable.cached("quick-move-brackets", t ->
            t.rows().stream().map(r -> new Object[]{r.str("name"), r.i("from"), r.i("to")}).toArray(Object[][]::new));
    /** entrance map, name, required level */
    private static final Supplier<Object[][]> BOSSES = DataTable.cached("quick-move-bosses", t ->
            t.rows().stream().map(r -> new Object[]{r.i("map"), r.str("name"), r.i("level")}).toArray(Object[][]::new));

    /** The town menu's regions, in menu order (read once; a new region needs a restart). */
    public static final String[] REGIONS = TOWNS.get().regions();

    private static int[][] towns() {
        return TOWNS.get().towns();
    }

    private static Map<Integer, String> areaNames() {
        return AREA_NAMES.get();
    }

    private static int[][] hunting() {
        return HUNTING_GROUNDS.get();
    }

    private static Object[][] brackets() {
        return HUNTING_BRACKETS.get();
    }

    private static Object[][] bosses() {
        return BOSSES.get();
    }

    /** The Arcane River area a town is the town of, or null. */
    public static String getAreaName(int mapId) {
        return areaNames().get(mapId);
    }

    private static final Map<Integer, int[]> levelCache = new ConcurrentHashMap<>();
    private static volatile List<Integer> sortedHuntingMaps;
    private static volatile int[][] sortedFor;

    private static final Map<Integer, Boolean> existsCache = new ConcurrentHashMap<>();

    /**
     * Maps no longer in the game data (some old World Tour towns) are left out of the lists.
     */
    private static boolean exists(int mapId) {
        return existsCache.computeIfAbsent(mapId, id ->
                StringData.getMapStringById(id) != null && FieldData.getFieldInfoById(id) != null);
    }

    public static String getMapName(int mapId) {
        String name = StringData.getMapStringById(mapId);
        return name == null ? String.valueOf(mapId) : name;
    }

    // ------------------------------------------------------------------------------------------------- towns

    public static List<Integer> getTowns(int region) {
        List<Integer> res = new ArrayList<>();
        if (region >= 0 && region < towns().length) {
            for (int mapId : towns()[region]) {
                if (exists(mapId)) {
                    res.add(mapId);
                }
            }
        }
        return res;
    }

    /**
     * @return every town in the lists (all regions)
     */
    public static List<Integer> getAllTowns() {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < towns().length; i++) {
            res.addAll(getTowns(i));
        }
        return res;
    }

    public static boolean isTown(int mapId) {
        for (int[] region : towns()) {
            for (int id : region) {
                if (id == mapId) {
                    return true;
                }
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------------------------------- hunting

    /**
     * @return {lowest, highest} level of the monsters that spawn in the map, or null if none do
     */
    public static int[] getMobLevels(int mapId) {
        return levelCache.computeIfAbsent(mapId, id -> {
            var info = FieldData.getFieldInfoById(id);
            int min = Integer.MAX_VALUE;
            int max = 0;
            if (info != null) {
                for (Life life : info.getMobGens()) {
                    var mob = ((MobGen) life).getMob();
                    if (mob == null || mob.getForcedMobStat() == null || mob.isBoss()) {
                        continue;
                    }
                    int lv = mob.getForcedMobStat().getLevel();
                    if (lv > 0) {
                        min = Math.min(min, lv);
                        max = Math.max(max, lv);
                    }
                }
            }
            return max == 0 ? new int[0] : new int[]{min, max};
        });
    }

    public static String getLevelText(int mapId) {
        int[] lv = getMobLevels(mapId);
        if (lv.length == 0) {
            return "";
        }
        return lv[0] == lv[1] ? "Lv. " + lv[0] : "Lv. " + lv[0] + "-" + lv[1];
    }

    private static int[] huntingEntry(int mapId) {
        for (int[] e : hunting()) {
            if (e[0] == mapId) {
                return e;
            }
        }
        return null;
    }

    /**
     * @return the hunting maps (the ones in this client's data), lowest recommended level first
     */
    public static List<Integer> getHuntingMaps() {
        List<Integer> res = sortedHuntingMaps;
        if (res == null || sortedFor != hunting()) {
            sortedFor = hunting();
            res = new ArrayList<>();
            for (int[] e : hunting()) {
                if (exists(e[0])) {
                    res.add(e[0]);
                }
            }
            res.sort(Comparator.comparingInt(QuickMoveModule::getHuntingMinLevel));
            res = Collections.unmodifiableList(res);
            sortedHuntingMaps = res;
        }
        return res;
    }

    /**
     * @return the hunting maps whose recommended levels overlap the bracket's
     */
    public static List<Integer> getHuntingMaps(int bracket) {
        List<Integer> res = new ArrayList<>();
        if (bracket < 0 || bracket >= brackets().length) {
            return res;
        }
        int from = (int) brackets()[bracket][1];
        int to = (int) brackets()[bracket][2];
        for (int mapId : getHuntingMaps()) {
            if (getHuntingMinLevel(mapId) <= to && getHuntingMaxLevel(mapId) >= from
                    && getHuntingMinLevel(mapId) >= from - 5) {
                res.add(mapId);
            }
        }
        return res;
    }

    public static String getBracketName(int bracket) {
        return (String) brackets()[bracket][0];
    }

    public static int getBracketCount() {
        return brackets().length;
    }

    /**
     * @return the bracket for a character level (the highest one that starts at or below it)
     */
    public static int getBracketForLevel(int level) {
        int res = 0;
        for (int i = 0; i < brackets().length; i++) {
            if ((int) brackets()[i][1] <= level) {
                res = i;
            }
        }
        return res;
    }

    /** Recommended character level range of a hunting map. */
    public static int getHuntingMinLevel(int mapId) {
        int[] e = huntingEntry(mapId);
        return e == null ? 0 : e[1];
    }

    public static int getHuntingMaxLevel(int mapId) {
        int[] e = huntingEntry(mapId);
        return e == null ? 0 : e[2];
    }

    /** Arcane Force a hunting map asks for (0 outside Arcane River). */
    public static int getHuntingArcaneForce(int mapId) {
        var info = FieldData.getFieldInfoById(mapId);
        return info == null ? 0 : info.getBarrierArc();
    }

    /** Authentic Force a Grandis hunting map asks for (Map.wz barrierAut, else the table), or 0. */
    public static int getHuntingAuthenticForce(int mapId) {
        var info = FieldData.getFieldInfoById(mapId);
        if (info != null && info.getBarrierAut() > 0) {
            return info.getBarrierAut();
        }
        int[] e = huntingEntry(mapId);
        return e == null || e.length < 4 ? 0 : e[3];
    }

    public static boolean isHuntingMap(int mapId) {
        return huntingEntry(mapId) != null;
    }

    // ------------------------------------------------------------------------------------------------- bosses

    public static int getBossCount() {
        return bosses().length;
    }

    public static int getBossMap(int i) {
        return (int) bosses()[i][0];
    }

    public static String getBossName(int i) {
        return (String) bosses()[i][1];
    }

    public static int getBossLevel(int i) {
        return (int) bosses()[i][2];
    }
}

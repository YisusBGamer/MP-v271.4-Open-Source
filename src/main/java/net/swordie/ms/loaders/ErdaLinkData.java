package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Erda Link (Sia Astelle's 6th job) from Etc.wz: the board (ErdaLink.img/tree/{job / 100}), its stones
 * (ErdaLink.img/stone, the SHINE stones in ErdaLinkShineStone.img) and the Sol Erda / fragment / meso costs
 * (ErdaLinkSolerda.img). Read on first use.
 */
public class ErdaLinkData {

    private static final Logger log = LogManager.getLogger(ErdaLinkData.class);

    // stone categories, numbered as the client numbers them
    public static final int ORIGIN = 0;
    public static final int RUSH = 1;
    public static final int SKILL = 2;
    public static final int BOOST = 3;
    public static final int ULTIMATE = 4;
    public static final int SHINE = 5;
    private static final String[] CATEGORY_NAMES = {"origin", "rush", "skill", "boost", "ultimate", "SHINE"};

    public static final String DEFAULT_COST = "default";

    public static class PassiveLine {
        public final String stat;
        public final int[] byLevel;

        PassiveLine(String stat, int[] byLevel) {
            this.stat = stat;
            this.byLevel = byLevel;
        }

        public int get(int level) {
            return level <= 0 || byLevel.length == 0 ? 0 : byLevel[Math.min(level, byLevel.length - 1)];
        }
    }

    public static class Stone {
        public final int id;
        public final int category;
        public String name = "";
        public int maxLevel = 1;
        public String costType = DEFAULT_COST;
        public boolean rushEnd;
        public boolean locked;
        public final List<Integer> connectSkills = new ArrayList<>();
        /** 5th job skill -> the level it needs */
        public final Map<Integer, Integer> reqSkills = new LinkedHashMap<>();
        public final List<PassiveLine> passives = new ArrayList<>();
        // SHINE stone conditions (-1 / 0 = none)
        public int condStoneCategory = -1;
        public int condStoneCount;
        public int condLevel;
        public int condStoneLevelCategory = -1;
        public int condStoneLevel;
        public int condEndStones;
        public boolean condAll;
        /** SHINE: {success, fail, downgrade} % going from level i to i + 1 */
        public double[][] enforceProb = new double[0][];
        /** boost stones: special node option (combokill / prob) */
        public String spCondType;
        public int spSkillId;
        // where it sits on the board (-1 if not on it)
        public int nodeIndex = -1;
        public int[] prevAnd = new int[0];
        public int[] prevOr = new int[0];

        Stone(int id, int category) {
            this.id = id;
            this.category = category;
        }

        public boolean isOnBoard() {
            return nodeIndex >= 0;
        }
    }

    public static class Cost {
        public final int solErda;
        public final int fragments;
        public final long meso;

        Cost(int solErda, int fragments, long meso) {
            this.solErda = solErda;
            this.fragments = fragments;
            this.meso = meso;
        }
    }

    // board (job / 100) -> stone id -> stone; the ids repeat across boards
    private static final Map<Integer, Map<Integer, Stone>> stones = new HashMap<>();
    // tree (job / 100) -> node index -> stone id
    private static final Map<Integer, Map<Integer, Integer>> trees = new HashMap<>();
    // category/costType
    private static final Map<String, Cost> activation = new HashMap<>();
    // category/level/costType
    private static final Map<String, Cost> enforcement = new HashMap<>();
    private static final List<Integer> fragmentItems = new ArrayList<>();
    private static boolean loaded;

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz");
            WzObject<?, ?> root = file.getChild("ErdaLink.img");
            if (root == null) {
                log.warn("Etc.wz has no ErdaLink.img: Erda Link is off");
                return;
            }
            readStones(root.getChild("stone"));
            WzObject<?, ?> shine = file.getChild("ErdaLinkShineStone.img");
            if (shine != null) {
                readStones(shine.getChild("stone"));
            }
            readTrees(root.getChild("tree"));
            readCosts(file.getChild("ErdaLinkSolerda.img"));
            log.info(String.format("Loaded %d Erda Link stones on %d boards",
                    stones.values().stream().mapToInt(Map::size).sum(), trees.size()));
        } catch (Exception e) {
            log.error("Could not read the Erda Link data", e);
        }
    }

    /** @return the board a job, or a board named by its job (18212), uses */
    private static int boardOf(int job) {
        return job / 100;
    }

    private static void readStones(WzObject<?, ?> stoneRoot) {
        if (stoneRoot == null) {
            return;
        }
        for (WzObject<?, ?> jobNode : stoneRoot) {
            int job;
            try {
                job = Integer.parseInt(jobNode.getName());
            } catch (NumberFormatException e) {
                continue; // info
            }
            for (WzObject<?, ?> catNode : jobNode) {
                readCategory(boardOf(job), catNode);
            }
        }
    }

    private static void readCategory(int board, WzObject<?, ?> catNode) {
        int category = categoryOf(catNode.getName());
        if (category >= 0) {
            for (WzObject<?, ?> node : catNode) {
                int id;
                try {
                    id = Integer.parseInt(node.getName());
                } catch (NumberFormatException e) {
                    continue;
                }
                Stone stone = new Stone(id, category);
                stone.name = stringOf(node.getChild("name"));
                stone.maxLevel = Math.max(1, intOf(node.getChild("maxLevel"), 1));
                String cost = stringOf(node.getChild("costType"));
                stone.costType = cost.isEmpty() ? DEFAULT_COST : cost;
                stone.rushEnd = intOf(node.getChild("rushEnd"), 0) != 0;
                stone.locked = intOf(node.getChild("locked"), 0) != 0;
                WzObject<?, ?> connect = node.getChild("connectSkill");
                if (connect != null) {
                    for (WzObject<?, ?> skill : connect) {
                        stone.connectSkills.add(intOf(skill, 0));
                    }
                }
                readCondition(stone, node.getChild("condition"));
                readPassives(stone, node.getChild("passive"));
                readEnforceProb(stone, node.getChild("enforceProb"));
                WzObject<?, ?> sp = node.getChild("spCoreOption");
                if (sp != null) {
                    stone.spCondType = stringOf(sp.getChildByPath("cond/type"));
                    stone.spSkillId = intOf(sp.getChildByPath("effect/skill_id"), 0);
                }
                stones.computeIfAbsent(board, b -> new HashMap<>()).put(id, stone);
            }
        }
    }

    private static void readCondition(Stone stone, WzObject<?, ?> cond) {
        if (cond == null) {
            return;
        }
        WzObject<?, ?> req = cond.getChild("reqForActivation");
        if (req != null) {
            for (WzObject<?, ?> skill : req) {
                try {
                    stone.reqSkills.put(Integer.parseInt(skill.getName()), intOf(skill, 0));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        WzObject<?, ?> byStone = cond.getChild("stone");
        if (byStone != null) {
            stone.condStoneCategory = categoryOf(stringOf(byStone.getChild("type")));
            stone.condStoneCount = intOf(byStone.getChild("count"), 1);
        }
        WzObject<?, ?> level = cond.getChild("level");
        if (level != null) {
            stone.condLevel = intOf(level.getChild("level"), 0);
        }
        WzObject<?, ?> stoneLevel = cond.getChild("stonelevel");
        if (stoneLevel != null) {
            stone.condStoneLevelCategory = categoryOf(stringOf(stoneLevel.getChild("type")));
            stone.condStoneLevel = intOf(stoneLevel.getChild("lv"), 0);
        }
        WzObject<?, ?> endStone = cond.getChild("endstone");
        if (endStone != null) {
            stone.condEndStones = intOf(endStone.getChild("count"), 1);
        }
        WzObject<?, ?> all = cond.getChild("all");
        if (all != null) {
            stone.condAll = intOf(all.getChild("all"), 0) != 0;
        }
    }

    /** passive/{line}/{level}/{stat} */
    private static void readPassives(Stone stone, WzObject<?, ?> passive) {
        if (passive == null) {
            return;
        }
        for (WzObject<?, ?> line : passive) {
            String stat = null;
            int maxLv = 0;
            Map<Integer, Integer> values = new HashMap<>();
            for (WzObject<?, ?> level : line) {
                int lv;
                try {
                    lv = Integer.parseInt(level.getName());
                } catch (NumberFormatException e) {
                    continue;
                }
                for (WzObject<?, ?> value : level) {
                    stat = value.getName();
                    values.put(lv, intOf(value, 0));
                }
                maxLv = Math.max(maxLv, lv);
            }
            if (stat == null) {
                continue;
            }
            int[] byLevel = new int[maxLv + 1];
            values.forEach((lv, v) -> byLevel[lv] = v);
            stone.passives.add(new PassiveLine(stat, byLevel));
        }
    }

    private static void readEnforceProb(Stone stone, WzObject<?, ?> probs) {
        if (probs == null) {
            return;
        }
        Map<Integer, double[]> byLevel = new TreeMap<>();
        for (WzObject<?, ?> level : probs) {
            try {
                byLevel.put(Integer.parseInt(level.getName()), new double[]{
                        doubleOf(level.getChild("successRate")), doubleOf(level.getChild("failRate")),
                        doubleOf(level.getChild("downgradeRate"))});
            } catch (NumberFormatException ignored) {
            }
        }
        int size = byLevel.isEmpty() ? 0 : Collections.max(byLevel.keySet()) + 1;
        stone.enforceProb = new double[size][];
        for (int i = 0; i < size; i++) {
            stone.enforceProb[i] = byLevel.getOrDefault(i, new double[]{100, 0, 0});
        }
    }

    private static void readTrees(WzObject<?, ?> treeRoot) {
        if (treeRoot == null) {
            return;
        }
        for (WzObject<?, ?> tree : treeRoot) {
            int treeId;
            try {
                treeId = Integer.parseInt(tree.getName());
            } catch (NumberFormatException e) {
                continue;
            }
            Map<Integer, Integer> nodes = new TreeMap<>();
            for (WzObject<?, ?> section : tree) {
                for (WzObject<?, ?> node : section) {
                    WzObject<?, ?> stoneId = node.getChild("stoneId");
                    if (stoneId == null) {
                        continue;
                    }
                    int idx;
                    try {
                        idx = Integer.parseInt(node.getName());
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    int id = intOf(stoneId, 0);
                    nodes.put(idx, id);
                    Stone stone = stones.getOrDefault(boardOf(treeId), Map.of()).get(id);
                    if (stone == null) {
                        log.warn("Erda Link board " + treeId + " node " + idx + " names stone " + id
                                + ", which the data doesn't have");
                        continue;
                    }
                    stone.nodeIndex = idx;
                    WzObject<?, ?> prev = node.getChild("prevIdx");
                    if (prev != null) {
                        stone.prevAnd = intList(prev.getChild("and"));
                        stone.prevOr = intList(prev.getChild("or"));
                    }
                }
            }
            trees.put(boardOf(treeId), nodes);
        }
    }

    private static void readCosts(WzObject<?, ?> root) {
        if (root == null) {
            return;
        }
        WzObject<?, ?> act = root.getChildByPath("runeStone/activation");
        if (act != null) {
            for (WzObject<?, ?> cat : act) {
                for (WzObject<?, ?> type : cat) {
                    activation.put(cat.getName() + "/" + type.getName(), costOf(type));
                }
            }
        }
        WzObject<?, ?> enf = root.getChildByPath("runeStone/enforcement");
        if (enf != null) {
            for (WzObject<?, ?> cat : enf) {
                for (WzObject<?, ?> level : cat) {
                    for (WzObject<?, ?> type : level) {
                        enforcement.put(cat.getName() + "/" + level.getName() + "/" + type.getName(), costOf(type));
                    }
                }
            }
        }
        WzObject<?, ?> sub = root.getChild("solerdaSub");
        if (sub != null) {
            for (WzObject<?, ?> item : sub) {
                fragmentItems.add(intOf(item, 0));
            }
        }
    }

    private static Cost costOf(WzObject<?, ?> node) {
        WzObject<?, ?> meso = node.getChild("meso");
        return new Cost(intOf(node.getChild("main"), 0), intOf(node.getChild("sub"), 0),
                meso instanceof WzProperty<?> p ? p.getLongValue() : 0);
    }

    private static int[] intList(WzObject<?, ?> node) {
        if (node == null) {
            return new int[0];
        }
        List<Integer> res = new ArrayList<>();
        for (WzObject<?, ?> c : node) {
            res.add(intOf(c, 0));
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int categoryOf(String name) {
        for (int i = 0; i < CATEGORY_NAMES.length; i++) {
            if (CATEGORY_NAMES[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static String categoryName(int category) {
        return category >= 0 && category < CATEGORY_NAMES.length ? CATEGORY_NAMES[category] : "";
    }

    private static int intOf(WzObject<?, ?> node, int def) {
        if (node instanceof WzProperty<?> prop) {
            Object v = prop.getValue();
            if (v instanceof Number n) {
                return n.intValue();
            }
            try {
                return prop.getIntValue();
            } catch (RuntimeException e) {
                return def;
            }
        }
        return def;
    }

    private static double doubleOf(WzObject<?, ?> node) {
        if (node instanceof WzProperty<?> prop) {
            Object v = prop.getValue();
            if (v instanceof Number n) {
                return n.doubleValue();
            }
            if (v != null) {
                try {
                    return Double.parseDouble(v.toString().trim());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return 0;
    }

    private static String stringOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop && prop.getValue() != null ? prop.getValue().toString() : "";
    }

    // ------------------------------------------------------------------------------------------------- lookups

    public static Stone getStone(int job, int id) {
        ensureLoaded();
        return stones.getOrDefault(boardOf(job), Map.of()).get(id);
    }

    /** @return the board of a job (its job id / 100), node index -> stone id; empty if the job has none */
    public static Map<Integer, Integer> getTree(int job) {
        ensureLoaded();
        return trees.getOrDefault(boardOf(job), Map.of());
    }

    public static boolean hasTree(int job) {
        return !getTree(job).isEmpty();
    }

    public static boolean isOnTree(int job, int stoneId) {
        Stone stone = getStone(job, stoneId);
        return stone != null && stone.isOnBoard() && Objects.equals(getTree(job).get(stone.nodeIndex), stoneId);
    }

    /** @return the stones of a job's board */
    public static List<Stone> getTreeStones(int job) {
        List<Stone> res = new ArrayList<>();
        for (int id : getTree(job).values()) {
            Stone stone = getStone(job, id);
            if (stone != null) {
                res.add(stone);
            }
        }
        return res;
    }

    public static Cost getActivationCost(Stone stone) {
        ensureLoaded();
        String cat = categoryName(stone.category);
        Cost cost = activation.get(cat + "/" + stone.costType);
        return cost != null ? cost : activation.get(cat + "/" + DEFAULT_COST);
    }

    /** @return the cost of going from level to level + 1, null if the data has none */
    public static Cost getEnforcementCost(Stone stone, int level) {
        ensureLoaded();
        String key = categoryName(stone.category) + "/" + level + "/";
        Cost cost = enforcement.get(key + stone.costType);
        return cost != null ? cost : enforcement.get(key + DEFAULT_COST);
    }

    /** @return the Sol Erda Fragment items the costs take, in the order they are used up */
    public static List<Integer> getFragmentItems() {
        ensureLoaded();
        return fragmentItems;
    }
}

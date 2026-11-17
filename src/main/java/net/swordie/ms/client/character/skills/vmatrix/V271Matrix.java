package net.swordie.ms.client.character.skills.vmatrix;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The v271 V Matrix: no nodes or slots. Every core of the job's group (Etc/VCore.img/vSkill/coreGroup) has a level
 * bought with V points (quest ex 1477 "vPoint"; Nodestones give points per Etc/VCore.img/coreGemstone). A core's level
 * is the level of its connected skill(s), so characters from the node system keep what their nodes gave.
 * <p>
 * Client: core map 0x140793990 (CharacterData F50, MATRIX_UPDATE 499), result 0x145817710 (MATRIX_RESULT 500),
 * requests 0x1459c79b0..7d40 (MATRIX_UPDATE_REQUEST 668, int type first), level cost 0x140eefb40.
 */
public class V271Matrix {
    private static final Logger log = LogManager.getLogger();

    public static final int POINT_QUEST = 1477;
    public static final String POINT_KEY = "vPoint";
    public static final int MAX_POINTS = 9999;

    public static final int REQ_TRANSFORM = 0; // 4 amount
    public static final int REQ_GEMSTONE = 1; // 4 item, 4 count
    public static final int REQ_LEVEL_UP = 2; // 4 core, 4 target level
    public static final int REQ_3 = 3; // 4
    public static final int REQ_SPECIAL_REGISTER = 4; // 4 core
    public static final int REQ_SPECIAL_EXTEND = 5; // 4 core
    public static final int REQ_RESET = 6; // no body

    private static final class Core {
        int id;
        int type; // 0 skill, 1 enforce, 2 special
        int maxLevel;
        boolean notSelfOpen;
        List<Integer> skills = new ArrayList<>();
    }

    private static final class Gem {
        int vPoint;
        int openCore;
        int reqQuest;
        String questAlert;
    }

    private static volatile boolean loaded;
    private static final Map<Integer, Core> cores = new HashMap<>();
    private static final Map<Integer, List<Integer>> groups = new HashMap<>(); // job -> core ids
    private static final Set<Integer> commonCores = new HashSet<>(); // listed under a group's "common"
    private static final TreeMap<Integer, Integer> skillCost = new TreeMap<>();
    private static final TreeMap<Integer, Integer> enforceCost = new TreeMap<>();
    private static final Map<Integer, Gem> gems = new HashMap<>();
    private static final Map<Integer, int[]> boostFrom265 = new HashMap<>(); // v271 boost skill -> v265 boost skills
    private static long resetMeso = 10_000_000L;

    /** quest ex 1477 key set once the node-system state has been carried over */
    public static final String MIGRATED_KEY = "vMig";

    private static synchronized void load() {
        if (loaded) {
            return;
        }
        try {
            WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz");
            WzObject<?, ?> top = file.getChild("VCore.img");
            WzObject<?, ?> vSkill = top.getChild("vSkill");
            if (vSkill != null) {
                for (WzObject<?, ?> c : kids(vSkill.getChild("CoreData"))) {
                    Core core = new Core();
                    core.id = Integer.parseInt(c.getName());
                    core.type = intOf(c.getChild("type"), core.id / 10_000_000 - 1);
                    core.maxLevel = intOf(c.getChild("maxLevel"), 0);
                    core.notSelfOpen = intOf(c.getChild("notAbleSelfOpen"), 0) != 0;
                    WzObject<?, ?> cs = c.getChild("connectSkill");
                    if (cs != null) {
                        for (WzObject<?, ?> s : kids(cs)) {
                            core.skills.add(intOf(s, 0));
                        }
                    }
                    cores.put(core.id, core);
                }
                for (WzObject<?, ?> g : kids(vSkill.getChild("coreGroup"))) {
                    List<Integer> ids = new ArrayList<>();
                    for (String part : new String[]{"skill", "enforce", "common"}) {
                        WzObject<?, ?> list = g.getChild(part);
                        if (list != null) {
                            for (WzObject<?, ?> id : kids(list)) {
                                ids.add(intOf(id, 0));
                                if (part.equals("common")) {
                                    commonCores.add(intOf(id, 0));
                                }
                            }
                        }
                    }
                    groups.put(Integer.parseInt(g.getName()), ids);
                }
                // Beast Tamer (retired in GMS) has no group in v271, but its own skill cores are still in CoreData:
                // its four skills + the magician common list (Lynn's), no boost cores (none left in the data)
                if (!groups.containsKey(11212) && groups.containsKey(17212)) {
                    List<Integer> bt = new ArrayList<>(List.of(10020010, 10020020, 10020034, 10020045)); // hardcode-ok
                    for (int id : groups.get(17212)) {
                        if (commonCores.contains(id)) {
                            bt.add(id);
                        }
                    }
                    if (bt.stream().limit(4).allMatch(cores::containsKey)) {
                        groups.put(11212, bt);
                    }
                }
            }
            WzObject<?, ?> vPoint = top.getChild("vPoint");
            if (vPoint != null) {
                readCostTable(vPoint.getChild("skill"), skillCost);
                readCostTable(vPoint.getChild("enforce"), enforceCost);
            }
            WzObject<?, ?> gemNode = top.getChild("coreGemstone");
            if (gemNode != null) {
                for (WzObject<?, ?> g : kids(gemNode)) {
                    Gem gem = new Gem();
                    gem.vPoint = intOf(g.getChild("vPoint"), 0);
                    gem.openCore = intOf(g.getChild("openCore"), 0);
                    gem.reqQuest = intOf(g.getChild("reqQuest"), 0);
                    WzObject<?, ?> alert = g.getChild("questAlert");
                    gem.questAlert = alert instanceof WzProperty<?> ap ? ap.toString() : null;
                    WzObject<?, ?> ids = g.getChild("id");
                    if (ids != null) {
                        for (WzObject<?, ?> id : kids(ids)) {
                            gems.put(intOf(id, 0), gem);
                        }
                    }
                }
            }
            WzObject<?, ?> info = top.getChild("info");
            if (info != null && info.getChild("resetMeso") != null) {
                resetMeso = intOf(info.getChild("resetMeso"), 10_000_000);
            }
            log.info(String.format("[V271Matrix] %d cores, %d job groups, %d gemstone items", cores.size(),
                    groups.size(), gems.size()));
        } catch (RuntimeException e) {
            log.error("[V271Matrix] could not read Etc/VCore.img", e);
        }
        loadBoostMap();
        loaded = true;
    }

    /** resources/v271-boost-v265.tsv: v271 boost skill, job, v265 boost skill ids (comma list), name */
    private static void loadBoostMap() {
        java.nio.file.Path file = java.nio.file.Paths.get(ServerConstants.RESOURCES_DIR, "v271-boost-v265.tsv");
        try {
            for (String line : java.nio.file.Files.readAllLines(file)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] f = line.split("\t");
                if (f.length < 3) {
                    continue;
                }
                boostFrom265.put(Integer.parseInt(f[0].trim()),
                        Arrays.stream(f[2].split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray());
            }
        } catch (java.io.IOException | RuntimeException e) {
            log.warn("[V271Matrix] no v265 boost map (" + file + "): " + e);
        }
    }

    private static void readCostTable(WzObject<?, ?> node, TreeMap<Integer, Integer> out) {
        if (node == null) {
            return;
        }
        for (WzObject<?, ?> e : kids(node)) {
            out.put(Integer.parseInt(e.getName()), intOf(e, 0));
        }
    }

    private static List<WzObject<?, ?>> kids(WzObject<?, ?> node) {
        List<WzObject<?, ?>> out = new ArrayList<>();
        if (node != null) {
            for (Object o : node) {
                out.add((WzObject<?, ?>) o);
            }
        }
        return out;
    }

    private static int intOf(WzObject<?, ?> node, int def) {
        if (!(node instanceof WzProperty<?> p)) {
            return def;
        }
        try {
            return p.getIntValue();
        } catch (RuntimeException e) {
            try {
                return Integer.parseInt(p.toString().trim());
            } catch (NumberFormatException e2) {
                return def;
            }
        }
    }

    /** V points for raising a core to this level (the client's table: first key >= level) */
    public static int levelCost(int coreId, int level) {
        load();
        Core core = cores.get(coreId);
        if (core == null || level <= 0) {
            return 0;
        }
        TreeMap<Integer, Integer> table = core.type == 0 ? skillCost : core.type == 1 ? enforceCost : null;
        if (table == null) {
            return 0;
        }
        Map.Entry<Integer, Integer> e = table.ceilingEntry(level);
        return e == null ? 0 : e.getValue();
    }

    public static int costBetween(int coreId, int from, int to) {
        int sum = 0;
        for (int l = from + 1; l <= to; l++) {
            sum += levelCost(coreId, l);
        }
        return sum;
    }

    private static List<Integer> groupOf(Char chr) {
        load();
        return groups.getOrDefault((int) chr.getJob(), List.of());
    }

    public static int coreLevel(Char chr, int coreId) {
        load();
        Core core = cores.get(coreId);
        if (core == null || core.skills.isEmpty()) {
            return 0;
        }
        int level = 0;
        for (int skillId : core.skills) {
            Skill s = chr.getSkill(skillId);
            if (s != null) {
                level = Math.max(level, s.getCurrentLevel());
            }
        }
        return core.maxLevel > 0 ? Math.min(level, core.maxLevel) : level;
    }

    /** Cores with a level, in client order (the map is keyed by core id) */
    private static TreeMap<Integer, Integer> levels(Char chr) {
        TreeMap<Integer, Integer> out = new TreeMap<>();
        if (chr.getId() == 0) {
            return out;
        }
        for (int coreId : groupOf(chr)) {
            int level = coreLevel(chr, coreId);
            if (level > 0) {
                out.put(coreId, level);
            }
        }
        return out;
    }

    /** CharacterData F50 / MATRIX_UPDATE body: B4 n, n x (B4 core, B17: 4 core, 4 level, 1, FT expire), 1 has set */
    public static void encode(Char chr, OutPacket outPacket) {
        TreeMap<Integer, Integer> lv = levels(chr);
        outPacket.encodeInt(lv.size());
        for (Map.Entry<Integer, Integer> e : lv.entrySet()) {
            outPacket.encodeInt(e.getKey());
            outPacket.encodeInt(e.getKey());
            outPacket.encodeInt(e.getValue());
            outPacket.encodeByte(0);
            outPacket.encodeFT(FileTime.fromType(FileTime.Type.MAX_TIME));
        }
        outPacket.encodeByte(0); // no int set: the client keeps its own
    }

    public static OutPacket update(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.MATRIX_UPDATE);
        encode(chr, outPacket);
        outPacket.encodeByte(0); // 1 + int shows notice 0x6dff
        return outPacket;
    }

    /** MATRIX_RESULT 500: B4 type, B4 code; code 0 bodies: 0 (4), 1 (4 item, 4 count, 1 opened, 4 core), 2 (4 core, 4 level) */
    public static OutPacket result(int type, int code, int... body) {
        OutPacket outPacket = new OutPacket(OutHeader.MATRIX_RESULT);
        outPacket.encodeInt(type);
        outPacket.encodeInt(code);
        if (code == 0) {
            switch (type) {
                case REQ_TRANSFORM -> outPacket.encodeInt(body.length > 0 ? body[0] : 0);
                case REQ_GEMSTONE -> {
                    outPacket.encodeInt(body[0]);
                    outPacket.encodeInt(body[1]);
                    outPacket.encodeByte(body[2] != 0);
                    outPacket.encodeInt(body[3]);
                }
                case REQ_LEVEL_UP -> {
                    outPacket.encodeInt(body[0]);
                    outPacket.encodeInt(body[1]);
                }
                default -> {
                }
            }
        }
        return outPacket;
    }

    /** 1477 is also the v265 node shard record ("count"), so the vPoint key may be missing from an existing record */
    private static Quest pointQuest(Char chr) {
        Quest q = chr.getQuestManager().getQuestById(POINT_QUEST);
        if (q == null) {
            q = QuestData.createQuestFromId(POINT_QUEST);
            q.setProperty(POINT_KEY, net.swordie.ms.ServerSettings.getInt("test.v271StartVPoint", 0));
            chr.getQuestManager().addQuest(q);
        }
        if (!q.hasProperty(POINT_KEY)) {
            q.setProperty(POINT_KEY, net.swordie.ms.ServerSettings.getInt("test.v271StartVPoint", 0));
        }
        return q;
    }

    /** Creates the V point record (no packet) so the login's quest ex section carries it */
    public static void ensurePoints(Char chr) {
        if (chr.getId() != 0) {
            pointQuest(chr);
        }
    }

    /**
     * Before the login's skill section: V points, and once per character the node-system state as core levels.
     * Skill cores take the best owned node of that core when it beats the skill's level (owned, not only equipped);
     * boost cores take the highest level among the v265 boost skills their connected skills replace.
     */
    public static void prepare(Char chr) {
        if (chr.getId() == 0) {
            return;
        }
        load();
        Quest q = pointQuest(chr);
        if (q.hasProperty(MIGRATED_KEY)) {
            return;
        }
        int changed = 0;
        for (int coreId : groupOf(chr)) {
            Core core = cores.get(coreId);
            if (core == null || core.skills.isEmpty() || core.type == 2) {
                continue;
            }
            int cur = coreLevel(chr, coreId);
            int old = core.type == 0 ? ownedNodeLevel(chr, core) : oldBoostLevel(chr, core);
            int max = core.maxLevel > 0 ? core.maxLevel : 30;
            old = Math.min(old, max);
            if (old > cur) {
                setCoreLevel(chr, core, old, false);
                changed++;
            }
        }
        q.setProperty(MIGRATED_KEY, 1);
        if (changed > 0) {
            log.info(String.format("[V271Matrix] %s: %d core(s) carried over from the node system", chr.getName(),
                    changed));
        }
    }

    private static int ownedNodeLevel(Char chr, Core core) {
        int level = 0;
        for (MatrixRecord mr : chr.getMatrixRecords()) {
            if (mr.getIconID() == core.id || (mr.getSkillID1() != 0 && mr.getIconID() / 10_000_000 == 1
                    && core.skills.contains(mr.getSkillID1()))) {
                level = Math.max(level, mr.getSlv());
            }
        }
        return level;
    }

    private static int oldBoostLevel(Char chr, Core core) {
        int level = 0;
        for (int skillId : core.skills) {
            for (int old : boostFrom265.getOrDefault(skillId, new int[0])) {
                Skill s = chr.getSkill(old);
                if (s != null) {
                    level = Math.max(level, s.getCurrentLevel());
                }
            }
        }
        return level;
    }

    public static int getPoints(Char chr) {
        return pointQuest(chr).getIntProperty(POINT_KEY);
    }

    public static void setPoints(Char chr, int points) {
        Quest q = pointQuest(chr);
        q.setProperty(POINT_KEY, Math.max(0, points));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    /** !maxv: every core of the character's job at its max level. @return how many cores were raised */
    public static int maxAll(Char chr) {
        load();
        int n = 0;
        for (int coreId : groupOf(chr)) {
            Core core = cores.get(coreId);
            if (core != null && core.maxLevel > 0 && coreLevel(chr, coreId) < core.maxLevel) {
                setCoreLevel(chr, core, core.maxLevel);
                n++;
            }
        }
        chr.write(update(chr));
        return n;
    }

    private static void setCoreLevel(Char chr, Core core, int level) {
        setCoreLevel(chr, core, level, true);
    }

    private static void setCoreLevel(Char chr, Core core, int level, boolean send) {
        for (int skillId : core.skills) {
            Skill skill = chr.getSkill(skillId);
            if (skill == null) {
                skill = SkillData.getSkillDeepCopyById(skillId);
                if (skill == null) {
                    continue;
                }
            }
            skill.setCurrentLevel(level);
            chr.addSkill(skill);
            if (send) {
                chr.write(WvsContext.changeSkillRecordResult(skill));
            }
        }
    }

    /** The 5th job's first skill cores: their connected skills at level 1 (a core's level is its skill's) */
    public static void openSkill(Char chr, int skillId) {
        load();
        List<Integer> group = groupOf(chr);
        for (int coreId : group) {
            Core core = cores.get(coreId);
            if (core != null && core.type == 0 && core.skills.contains(skillId)) {
                if (coreLevel(chr, coreId) == 0) {
                    setCoreLevel(chr, core, 1);
                }
                return;
            }
        }
        // the old first-skill id isn't one of this job's v271 cores: open the group's first skill core
        Core first = group.isEmpty() ? null : cores.get(group.get(0));
        if (first != null && first.type == 0 && coreLevel(chr, first.id) == 0) {
            setCoreLevel(chr, first, 1);
        }
    }

    public static void handleRequest(Char chr, InPacket inPacket) {
        load();
        int type = inPacket.decodeInt();
        int a = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        int b = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        switch (type) {
            case REQ_LEVEL_UP -> levelUp(chr, a, b);
            case REQ_GEMSTONE -> useGemstones(chr, a, b);
            case REQ_RESET -> reset(chr);
            default -> {
                log.info(String.format("[V271Matrix] %s: request type %d (%d, %d) not handled", chr.getName(), type, a, b));
                chr.write(update(chr));
            }
        }
    }

    private static void levelUp(Char chr, int coreId, int target) {
        if (raise(chr, coreId, target)) {
            chr.write(update(chr));
            chr.write(result(REQ_LEVEL_UP, 0, coreId, target));
        } else {
            chr.write(update(chr));
        }
    }

    /** Pays for and sets a core's level; false (nothing changed) when the request isn't allowed */
    private static boolean raise(Char chr, int coreId, int target) {
        Core core = cores.get(coreId);
        if (core == null || core.type == 2 || !groupOf(chr).contains(coreId)) {
            log.info(String.format("[V271Matrix] %s: level-up of core %d refused (not in job %d's group)",
                    chr.getName(), coreId, chr.getJob()));
            return false;
        }
        int cur = coreLevel(chr, coreId);
        int max = core.maxLevel > 0 ? core.maxLevel : 30;
        int cost = costBetween(coreId, cur, target);
        int points = getPoints(chr);
        if (cur == 0 && core.notSelfOpen) {
            log.info(String.format("[V271Matrix] %s: core %d is opened by its Nodestone only", chr.getName(), coreId));
            return false;
        }
        if (target <= cur || target > max || cost > points) {
            log.info(String.format("[V271Matrix] %s: core %d %d -> %d refused (cost %d, points %d)", chr.getName(),
                    coreId, cur, target, cost, points));
            return false;
        }
        setPoints(chr, points - cost);
        setCoreLevel(chr, core, target);
        return true;
    }

    public static boolean isGemstone(int itemId) {
        load();
        return gems.containsKey(itemId);
    }

    /** A Nodestone double-clicked in the Use tab (the item script path): same as the V Matrix's own request */
    public static void useGemstonesFromItem(Char chr, int itemId, int count) {
        load();
        useGemstones(chr, itemId, count);
    }

    private static void useGemstones(Char chr, int itemId, int count) {
        Gem gem = gems.get(itemId);
        int held = chr.getConsumeInventory().getItems().stream().filter(i -> i.getItemId() == itemId)
                .mapToInt(net.swordie.ms.client.character.items.Item::getQuantity).sum();
        count = Math.min(count, held);
        if (gem == null || count <= 0) {
            log.info(String.format("[V271Matrix] %s: gemstone %d x%d refused", chr.getName(), itemId, count));
            chr.write(update(chr));
            return;
        }
        if (!chr.getQuestManager().hasQuestCompleted(net.swordie.ms.constants.QuestConstants.FIFTH_JOB_QUEST)) {
            chr.chatMessage("Can only be used by Lv. 200+ characters who have completed the 5th Job Advancement.");
            chr.write(update(chr));
            return;
        }
        if (gem.reqQuest != 0 && !chr.getQuestManager().hasQuestCompleted(gem.reqQuest)) {
            chr.chatMessage(gem.questAlert != null ? gem.questAlert : "You can't use this item yet.");
            chr.write(update(chr));
            return;
        }
        chr.consumeItem(itemId, count);
        int opened = 0;
        Core open = gem.openCore != 0 ? cores.get(gem.openCore) : null;
        if (open != null && coreLevel(chr, open.id) == 0) {
            setCoreLevel(chr, open, 1);
            opened = open.id;
        }
        // the stone that unlocks its core gives no points; the rest do
        setPoints(chr, Math.min(MAX_POINTS, getPoints(chr) + gem.vPoint * (opened != 0 ? count - 1 : count)));
        chr.write(update(chr));
        chr.write(result(REQ_GEMSTONE, 0, itemId, count, opened != 0 ? 1 : 0, opened));
    }

    private static final SkillStat[] DAMAGE_STATS = {
            SkillStat.damR, SkillStat.indieDamR, SkillStat.bdR, SkillStat.indieBDR, SkillStat.ignoreMobpdpR,
            SkillStat.indieIgnoreMobpdpR, SkillStat.cr, SkillStat.indieCr, SkillStat.criticaldamage,
            SkillStat.criticaldamageMax, SkillStat.indieCD, SkillStat.pad, SkillStat.mad, SkillStat.padX,
            SkillStat.madX, SkillStat.indiePad, SkillStat.indieMad, SkillStat.indiePadR, SkillStat.indieMadR,
            SkillStat.indieAllStat,
    };

    /** Attack worth of the skills a core raises: its own skills, or for a boost core the skills it boosts */
    private static long coreWorth(Char chr, Core core) {
        long worth = 0;
        for (int skillId : core.skills) {
            SkillInfo si = SkillData.getSkillInfoById(skillId);
            if (si == null) {
                continue;
            }
            if (core.type == 1) {
                for (int base : si.getPsdSkills()) {
                    if (chr.getSkillLevel(base) > 0) {
                        worth += BoostNodeModule.attackWorth(SkillData.getSkillInfoById(base));
                    }
                }
            } else {
                worth += BoostNodeModule.attackWorth(si);
            }
        }
        return worth;
    }

    /** A common core worth points: it attacks or raises a damage stat (Blink, Rope Lift, Haste and the like don't) */
    private static boolean helpsDamage(Core core) {
        for (int skillId : core.skills) {
            SkillInfo si = SkillData.getSkillInfoById(skillId);
            if (si == null) {
                continue;
            }
            if (BoostNodeModule.attackWorth(si) > 0) {
                return true;
            }
            int lv = Math.max(1, si.getMaxLevel());
            for (SkillStat st : DAMAGE_STATS) {
                if (si.getValue(st, lv) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String coreName(Core core) {
        for (int skillId : core.skills) {
            var ssi = StringData.getSkillStringById(skillId);
            if (ssi != null && ssi.getName() != null && !ssi.getName().isBlank()) {
                return ssi.getName();
            }
        }
        return "Core " + core.id;
    }

    /** Raises a core as far as the points go, up to cap, through the normal level-up checks */
    private static void raiseAffordable(Char chr, Core core, int cap) {
        int cur = coreLevel(chr, core.id);
        if (core.notSelfOpen && cur == 0) {
            return;
        }
        int points = getPoints(chr);
        int target = cur, cost = 0;
        while (target < cap) {
            int next = levelCost(core.id, target + 1);
            if (cost + next > points) {
                break;
            }
            cost += next;
            target++;
        }
        if (target > cur) {
            raise(chr, core.id, target);
        }
    }

    /** Uses every Nodestone that fits under the point cap, the same way as using them by hand */
    private static void convertGemstones(Char chr) {
        Map<Integer, Integer> held = new TreeMap<>();
        for (Item item : chr.getConsumeInventory().getItems()) {
            if (gems.containsKey(item.getItemId())) {
                held.merge(item.getItemId(), item.getQuantity(), Integer::sum);
            }
        }
        for (Map.Entry<Integer, Integer> e : held.entrySet()) {
            Gem gem = gems.get(e.getKey());
            if (gem.reqQuest != 0 && !chr.getQuestManager().hasQuestCompleted(gem.reqQuest)) {
                continue;
            }
            boolean opens = gem.openCore != 0 && cores.containsKey(gem.openCore) && coreLevel(chr, gem.openCore) == 0;
            int count = gem.vPoint > 0
                    ? Math.min(e.getValue(), (MAX_POINTS - getPoints(chr)) / gem.vPoint + (opens ? 1 : 0))
                    : opens ? 1 : 0;
            if (count > 0) {
                useGemstones(chr, e.getKey(), count);
            }
        }
    }

    private static int heldGemstones(Char chr) {
        int n = 0;
        for (Item item : chr.getConsumeInventory().getItems()) {
            if (gems.containsKey(item.getItemId())) {
                n += item.getQuantity();
            }
        }
        return n;
    }

    /**
     * Turns the character's Nodestones into V points and spends them where they add the most damage per point:
     * boost cores (their cheap levels first), then the job's skill cores, then common cores that attack or raise
     * damage. Utility common cores are left alone.
     *
     * @return a message for the player
     */
    public static String spendPoints(Char chr) {
        load();
        if (!chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            return "The V Matrix opens with the 5th job.";
        }
        List<Core> boosts = new ArrayList<>(), skills = new ArrayList<>(), common = new ArrayList<>();
        // utility shared cores (Blink and the like) last, so damage comes first but every core gets levels
        List<Core> utility = new ArrayList<>();
        for (int coreId : groupOf(chr)) {
            Core core = cores.get(coreId);
            if (core == null || core.type == 2 || core.skills.isEmpty()) {
                continue;
            }
            if (commonCores.contains(coreId)) {
                if (helpsDamage(core)) {
                    common.add(core);
                } else {
                    utility.add(core);
                }
            } else if (core.type == 1) {
                boosts.add(core);
            } else {
                skills.add(core);
            }
        }
        List<List<Core>> order = List.of(boosts, skills, common, utility);
        for (List<Core> list : order) {
            list.sort(Comparator.comparingLong((Core c) -> -coreWorth(chr, c)).thenComparingInt(c -> c.id));
        }
        Map<Integer, Integer> before = new HashMap<>();
        for (int coreId : groupOf(chr)) {
            before.put(coreId, coreLevel(chr, coreId));
        }
        int startPoints = getPoints(chr);
        int startStones = heldGemstones(chr);
        int gained = 0, spent = 0;
        Integer step = enforceCost.isEmpty() ? null : enforceCost.firstKey();
        // Nodestones past the point cap wait until spending makes room
        for (int round = 0; round < 20; round++) {
            int pts = getPoints(chr);
            convertGemstones(chr);
            gained += getPoints(chr) - pts;
            int ready = getPoints(chr);
            if (step != null) {
                for (Core c : boosts) {
                    raiseAffordable(chr, c, Math.min(step, c.maxLevel > 0 ? c.maxLevel : 30));
                }
            }
            for (List<Core> list : order) {
                for (Core c : list) {
                    raiseAffordable(chr, c, c.maxLevel > 0 ? c.maxLevel : 30);
                }
            }
            spent += ready - getPoints(chr);
            if (ready == getPoints(chr) || heldGemstones(chr) == 0) {
                break;
            }
        }
        StringBuilder lines = new StringBuilder();
        int lastCore = 0;
        for (List<Core> list : order) {
            for (Core c : list) {
                int was = before.get(c.id), now = coreLevel(chr, c.id);
                if (now > was) {
                    lines.append(String.format("\r\n#b%s#k Lv. %d -> %d", coreName(c), was, now));
                    lastCore = c.id;
                }
            }
        }
        if (lastCore != 0) {
            chr.write(update(chr));
            chr.write(result(REQ_LEVEL_UP, 0, lastCore, coreLevel(chr, lastCore)));
        }
        StringBuilder msg = new StringBuilder();
        int used = startStones - heldGemstones(chr);
        if (used > 0) {
            msg.append(String.format("%,d Nodestone(s) gave #b%,d#k V points. ", used, gained));
        }
        if (spent == 0) {
            msg.append(String.format("Nothing to raise: you have #b%,d#k V points", getPoints(chr)));
            msg.append(startPoints + gained > 0 ? ", and every core they could buy is already at its cap or out of reach."
                    : ". Nodestones turn into V points.");
            return msg.toString();
        }
        msg.append(String.format("Spent #b%,d#k V points, #b%,d#k left.", spent, getPoints(chr)));
        return msg.append(lines).toString();
    }

    private static void reset(Char chr) {
        if (chr.getMoney() < resetMeso) {
            chr.chatMessage("You don't have enough mesos.");
            chr.write(update(chr));
            return;
        }
        int refund = 0;
        for (Map.Entry<Integer, Integer> e : levels(chr).entrySet()) {
            Core core = cores.get(e.getKey());
            int base = core.notSelfOpen ? 0 : 1;
            if (e.getValue() > base) {
                refund += costBetween(core.id, base, e.getValue());
                setCoreLevel(chr, core, base);
            }
        }
        chr.deductMoney(resetMeso, ProgressionLog.Sink.FEES);
        setPoints(chr, getPoints(chr) + refund);
        chr.write(update(chr));
        chr.write(result(REQ_RESET, 0));
    }
}

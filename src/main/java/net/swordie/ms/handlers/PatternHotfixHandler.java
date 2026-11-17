package net.swordie.ms.handlers;

import net.swordie.ms.client.Client;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * MobPatternSystem hotfix (Out 679): a WZ image whose children are named by version numbers; each child is registered
 * as an overlay like a Data.wz root (children named by full path, e.g. Mob\BossPattern\PatternSystem.img\1088).
 * The client reports its version in In 1204 at field entry and after applying a blob; its version is the number of
 * the last child (a child named 0 = "invalid version", nothing applied).
 */
public class PatternHotfixHandler {
    private static final Logger log = LogManager.getLogger(PatternHotfixHandler.class);
    private static final int CHUNK = 0x10000;

    private static File loadedFile;
    private static long loadedStamp;
    private static byte[] blob;
    private static int blobVersion;
    // version last sent per connection: a blob the client rejects is not sent again on every 1204
    private static final Map<Client, Integer> sent = Collections.synchronizedMap(new WeakHashMap<>());
    // category * 100 + difficulty -> its patterns, from the loaded blob
    private static volatile Map<Integer, List<PatternInfo>> patternIndex = Collections.emptyMap();
    // category * 100 + difficulty -> Common/difficulty/nn with the category's Common/weatherMessage
    private static volatile Map<Integer, AlertGauge> alertGauges = Collections.emptyMap();

    /**
     * One pattern of a PatternSystem set: its global id (category * 100000 + difficulty * 1000 + n), timings, and from
     * the category's Common/patternCommonInfo/n: the number of Action groups and Command entries, and each group's
     * subAction 0 animation name (null when it has none). tool = Common/isCreatedByTool: other categories run from
     * the client's per-boss builders (0x142892800 switch on 0x140c5f7c0), using SkillData only; skillX = SkillData/x,
     * lifeTimeMs = Info/lifeTime, summons = SkillData/summon (mobs the server spawns), skill = SkillData's int values
     * (x, y, z, z2, ...), pointCount = entries in SkillData/point, summonRandomCount = SkillData/summon/randomCount
     * (how many summon entries come out, picked at random; 0 = all).
     */
    public record PatternInfo(int id, int cooltimeMs, int startCooltimeMs, int priority, int actionCount,
                              int commandCount, List<String> actions, boolean tool, int skillX, int lifeTimeMs,
                              List<Summon> summons, Map<String, Integer> skill, int pointCount,
                              int summonRandomCount) {
        public int skill(String key) {
            return skill.getOrDefault(key, 0);
        }
    }

    /**
     * Common/difficulty/nn of a category (Lotus 1000/1002/1004): the alert gauge runs from min to max, rising by
     * increase every tick ms (negative for Lotus 1004, which has decrease instead); once full it falls by alertDecrease
     * every alertTick ms. messages = Common/weatherMessage.
     */
    public record AlertGauge(int min, int max, int tick, int increase, int alertTick, int alertDecrease,
                             List<GaugeMessage> messages) {
    }

    /** Common/weatherMessage/n: a chat line and a weather notice (type, duration). */
    public record GaugeMessage(String system, String weather, int type, int durationMs) {
    }

    /** The alert gauge of this category and difficulty, or null. */
    public static AlertGauge alertGauge(int category, int difficulty) {
        return patterns(category, difficulty).isEmpty() ? null : alertGauges.get(category * 100 + difficulty);
    }

    /**
     * SkillData/summon/n: count mobs of mobId at positions picked from the list (field coordinates). onlyView =
     * onlyViewMob: a prop of the pattern (Lotus purge barricades) that should last only as long as the pattern.
     */
    public record Summon(int mobId, int count, List<int[]> positions, boolean onlyView) {
    }

    /** The loaded pattern with this global id, or null. */
    public static PatternInfo pattern(int id) {
        for (PatternInfo p : patterns(id / 100000, id / 1000 % 100)) {
            if (p.id() == id) {
                return p;
            }
        }
        return null;
    }

    /** Whether a blob with pattern sets is loaded (loads test.patternFile on first use). */
    public static boolean hasPatterns() {
        long now = System.currentTimeMillis();
        if (patternIndex.isEmpty() && now >= nextLoadTry) {
            nextLoadTry = now + 10_000;
            String path = net.swordie.ms.ServerSettings.getString("test.patternFile", "").trim();
            if (path.isEmpty()) {
                return false;
            }
            synchronized (PatternHotfixHandler.class) {
                load(path);
            }
        }
        return !patternIndex.isEmpty();
    }

    private static volatile long nextLoadTry;

    /** The patterns the blob in test.patternFile has for this category and difficulty (empty: none, or no blob). */
    public static List<PatternInfo> patterns(int category, int difficulty) {
        String path = net.swordie.ms.ServerSettings.getString("test.patternFile", "").trim();
        if (path.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (PatternHotfixHandler.class) {
            if (!load(path)) {
                return Collections.emptyList();
            }
        }
        return patternIndex.getOrDefault(category * 100 + difficulty, Collections.emptyList());
    }

    @Handler(op = InHeader.FIELD_ENTER_INT_NOTICE)
    public static void handlePatternVersion(Client c, InPacket inPacket) {
        int clientVersion = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        String path = net.swordie.ms.ServerSettings.getString("test.patternFile", "").trim();
        if (path.isEmpty()) {
            return;
        }
        byte[] data;
        int version;
        synchronized (PatternHotfixHandler.class) {
            if (!load(path)) {
                return;
            }
            data = blob;
            version = blobVersion;
        }
        if (clientVersion == version) {
            return;
        }
        Integer last = sent.put(c, version);
        if (last != null && last == version) {
            log.info(String.format("[Patterns] %s still reports %d after version %d was sent; not sent again",
                    c.getIP(), clientVersion, version));
            return;
        }
        List<OutPacket> packets = patternPackets(data);
        log.info(String.format("[Patterns] %s has %d, sending version %d (%d bytes, %d packets)", c.getIP(),
                clientVersion, version, data.length, packets.size()));
        for (OutPacket p : packets) {
            c.write(p);
        }
    }

    private static boolean load(String path) {
        File f = new File(path);
        if (!f.isFile()) {
            if (!f.equals(loadedFile)) {
                log.warn("[Patterns] test.patternFile not found: " + path);
                loadedFile = f;
                blob = null;
                patternIndex = Collections.emptyMap();
            }
            return false;
        }
        long stamp = f.lastModified() ^ f.length();
        if (f.equals(loadedFile) && stamp == loadedStamp) {
            return blob != null;
        }
        loadedFile = f;
        loadedStamp = stamp;
        blob = null;
        patternIndex = Collections.emptyMap();
        try {
            byte[] data = Files.readAllBytes(f.toPath());
            List<String> names = topNames(data);
            if (names.isEmpty()) {
                log.warn("[Patterns] " + path + " has no version children, not sent");
                return false;
            }
            int version = parseVersion(names.get(names.size() - 1));
            if (version == 0 || names.contains("0")) {
                log.warn("[Patterns] " + path + " has version 0 (client: invalid version), not sent");
                return false;
            }
            blob = data;
            blobVersion = version;
            try {
                patternIndex = buildIndex(data);
            } catch (RuntimeException e) {
                patternIndex = Collections.emptyMap();
                log.warn("[Patterns] " + path + ": pattern list not read (" + e + "), the server can't drive them");
            }
            log.info(String.format("[Patterns] loaded %s: %d bytes, versions %s, %d pattern sets", path,
                    data.length, names, patternIndex.size()));
            return true;
        } catch (IOException | RuntimeException e) {
            log.warn("[Patterns] " + path + " is not a WZ property image, not sent: " + e);
            return false;
        }
    }

    /** The client takes atoi() of the last child's name. */
    private static int parseVersion(String s) {
        int i = 0, v = 0;
        boolean neg = false;
        if (i < s.length() && (s.charAt(i) == '-' || s.charAt(i) == '+')) {
            neg = s.charAt(i++) == '-';
        }
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            v = v * 10 + (s.charAt(i++) - '0');
        }
        return neg ? -v : v;
    }

    /**
     * Out 679 packets. The client's receiver: D4 offset; offset 0 starts a blob (packed size, buffer resized) and sets
     * a remaining count; a packet is skipped if offset >= remaining; while remaining > 0x10000 it reads 0x10000 bytes
     * at buffer+offset and lowers remaining by 0x10000, otherwise it reads `remaining` bytes and parses the buffer.
     * One packet up to 0x10000 bytes. Larger blobs: declared size 2*(tiles-1)*0x10000 + 2 (zero tail), tiles at
     * their offsets, then tile 1 again until 2 bytes remain, then those 2 at offset 1 - the only order that fills
     * every byte before the parse.
     */
    public static List<OutPacket> patternPackets(byte[] data) {
        List<OutPacket> packets = new ArrayList<>();
        if (data.length <= CHUNK) {
            OutPacket p = new OutPacket(OutHeader.MOB_PATTERN_SYSTEM_HOTFIX);
            p.encodeInt(0);
            p.encodeArr(Util.toPackedInt(data.length));
            p.encodeArr(data);
            packets.add(p);
            return packets;
        }
        int tiles = (data.length + CHUNK - 1) / CHUNK;
        int size = 2 * (tiles - 1) * CHUNK + 2;
        byte[] buf = Arrays.copyOf(data, size);
        OutPacket first = new OutPacket(OutHeader.MOB_PATTERN_SYSTEM_HOTFIX);
        first.encodeInt(0);
        first.encodeArr(Util.toPackedInt(size));
        first.encodeArr(Arrays.copyOfRange(buf, 0, CHUNK));
        packets.add(first);
        int remaining = size - CHUNK;
        for (int k = 1; k < tiles; k++) {
            packets.add(piece(buf, k * CHUNK, CHUNK));
            remaining -= CHUNK;
        }
        while (remaining > CHUNK) {
            packets.add(piece(buf, CHUNK, CHUNK));
            remaining -= CHUNK;
        }
        packets.add(piece(buf, 1, remaining));
        return packets;
    }

    private static OutPacket piece(byte[] buf, int offset, int len) {
        OutPacket p = new OutPacket(OutHeader.MOB_PATTERN_SYSTEM_HOTFIX);
        p.encodeInt(offset);
        p.encodeArr(Arrays.copyOfRange(buf, offset, offset + len));
        return p;
    }

    /** A parsed WZ property: its int value (when it has one) and its children. */
    private static final class Node {
        final String name;
        Integer value;
        String str;
        int[] vec;
        final List<Node> children = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }

        Node child(String n) {
            for (Node c : children) {
                if (c.name.equals(n)) {
                    return c;
                }
            }
            return null;
        }

        int intOf(String n, int def) {
            Node c = child(n);
            return c == null || c.value == null ? def : c.value;
        }
    }

    private static final String PATTERN_PREFIX = "Mob\\BossPattern\\PatternSystem.img\\";

    /** Every set (category/difficulty) of PatternSystem.img in the blob; a later version child replaces an earlier one. */
    static Map<Integer, List<PatternInfo>> buildIndex(byte[] data) {
        ByteBuffer b = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        Node root = new Node("");
        readProperty(b, root);
        Map<Integer, List<PatternInfo>> index = new java.util.HashMap<>();
        Map<Integer, AlertGauge> gauges = new java.util.HashMap<>();
        for (Node version : root.children) {
            for (Node entry : version.children) {
                if (!entry.name.startsWith(PATTERN_PREFIX)) {
                    continue;
                }
                int category = parseVersion(entry.name.substring(PATTERN_PREFIX.length()));
                if (category <= 0) {
                    continue;
                }
                readGauges(category, entry.child("Common"), gauges);
                for (Node diff : entry.children) {
                    if (diff.name.length() != 2 || !Character.isDigit(diff.name.charAt(0))) {
                        continue;
                    }
                    int difficulty = parseVersion(diff.name);
                    List<PatternInfo> list = new ArrayList<>();
                    Node common = entry.child("Common");
                    Node commonInfo = common == null ? null : common.child("patternCommonInfo");
                    boolean tool = common != null && common.intOf("isCreatedByTool", 0) != 0;
                    for (Node p : diff.children) {
                        if (p.name.length() != 3 || !Character.isDigit(p.name.charAt(0))) {
                            continue;
                        }
                        Node skill = p.child("SkillData");
                        Node cond = p.child("Condition");
                        Node pinfo = p.child("Info");
                        Node info = commonInfo == null ? null : commonInfo.child(p.name);
                        Node actions = info == null ? null : info.child("Action");
                        Node commands = info == null ? null : info.child("Command");
                        List<String> names = new ArrayList<>();
                        if (actions != null) {
                            for (Node group : actions.children) {
                                Node subs = group.child("subAction");
                                Node first = subs == null ? null : subs.child("0");
                                Node action = first == null ? null : first.child("action");
                                names.add(action == null ? null : action.str);
                            }
                        }
                        list.add(new PatternInfo(category * 100000 + difficulty * 1000 + parseVersion(p.name),
                                skill == null ? 0 : skill.intOf("cooltimeMS", 0),
                                skill == null ? 0 : skill.intOf("startCooltimeMS", 0),
                                cond == null ? 0 : cond.intOf("priority", 0),
                                names.size(), commands == null ? 0 : commands.children.size(),
                                Collections.unmodifiableList(names), tool,
                                skill == null ? 0 : skill.intOf("x", 0),
                                pinfo == null ? 0 : pinfo.intOf("lifeTime", 0), summons(skill), ints(skill),
                                skill == null || skill.child("point") == null ? 0 : skill.child("point").children.size(),
                                skill == null || skill.child("summon") == null ? 0
                                        : skill.child("summon").intOf("randomCount", 0)));
                    }
                    if (!list.isEmpty()) {
                        index.put(category * 100 + difficulty, Collections.unmodifiableList(list));
                    }
                }
            }
        }
        alertGauges = Collections.unmodifiableMap(gauges);
        return index;
    }

    private static void readGauges(int category, Node common, Map<Integer, AlertGauge> out) {
        Node diffs = common == null ? null : common.child("difficulty");
        if (diffs == null) {
            return;
        }
        List<GaugeMessage> messages = new ArrayList<>();
        Node weather = common.child("weatherMessage");
        if (weather != null) {
            for (Node m : weather.children) {
                Node sys = m.child("systemMessage"), text = m.child("weatherEffectMessage");
                messages.add(new GaugeMessage(sys == null ? null : sys.str, text == null ? null : text.str,
                        m.intOf("weatherEffectType", 0), m.intOf("weatherEffectDuration", 2500)));
            }
        }
        for (Node d : diffs.children) {
            if (d.name.length() != 2 || !Character.isDigit(d.name.charAt(0))) {
                continue;
            }
            out.put(category * 100 + parseVersion(d.name), new AlertGauge(d.intOf("min", 0), d.intOf("max", 1000),
                    // the client's loader (0x140c696b4) keeps decrease as a negative increase
                    d.intOf("tick", 1000), d.intOf("increase", 0) - d.intOf("decrease", 0), d.intOf("alertTick", 1000),
                    d.intOf("alertDecrease", 0), Collections.unmodifiableList(messages)));
        }
    }

    private static Map<String, Integer> ints(Node skill) {
        if (skill == null) {
            return Collections.emptyMap();
        }
        Map<String, Integer> out = new java.util.HashMap<>();
        for (Node c : skill.children) {
            if (c.value != null && c.children.isEmpty()) {
                out.put(c.name, c.value);
            }
        }
        return Collections.unmodifiableMap(out);
    }

    private static List<Summon> summons(Node skill) {
        Node list = skill == null ? null : skill.child("summon");
        if (list == null) {
            return Collections.emptyList();
        }
        List<Summon> out = new ArrayList<>();
        for (Node s : list.children) {
            int mob = s.intOf("mobTemplateID", 0);
            if (mob <= 0) {
                continue;
            }
            List<int[]> pos = new ArrayList<>();
            Node at = s.child("position");
            if (at != null) {
                for (Node v : at.children) {
                    if (v.vec != null) {
                        pos.add(v.vec);
                    }
                }
            }
            out.add(new Summon(mob, Math.max(1, s.intOf("count", 1)), Collections.unmodifiableList(pos),
                    s.intOf("onlyViewMob", 0) != 0));
        }
        return Collections.unmodifiableList(out);
    }

    /** A Property body: 2 reserved bytes, a child count, the children. */
    private static void readProperty(ByteBuffer b, Node parent) {
        if (!"Property".equals(strBlock(b))) {
            throw new IllegalArgumentException("no Property header");
        }
        readChildren(b, parent);
    }

    private static void readChildren(ByteBuffer b, Node parent) {
        b.getShort();
        int n = compressedInt(b);
        for (int i = 0; i < n; i++) {
            Node c = new Node(strBlock(b));
            parent.children.add(c);
            int type = b.get() & 0xFF;
            switch (type) {
                case 0:
                    break;
                case 2:
                case 11:
                    c.value = (int) b.getShort();
                    break;
                case 3:
                case 19:
                    c.value = compressedInt(b);
                    break;
                case 20:
                    byte l = b.get();
                    c.value = l == -128 ? (int) b.getLong() : l;
                    break;
                case 4:
                    if ((b.get() & 0xFF) == 0x80) {
                        b.getFloat();
                    }
                    break;
                case 5:
                    b.getDouble();
                    break;
                case 8:
                    c.str = strBlock(b);
                    break;
                case 9:
                    int size = b.getInt();
                    int end = b.position() + size;
                    int save = b.position();
                    String kind = strBlock(b);
                    if ("Property".equals(kind)) {
                        readChildren(b, c);
                    } else if ("Shape2D#Vector2D".equals(kind)) {
                        c.vec = new int[]{compressedInt(b), compressedInt(b)};
                    } else {
                        b.position(save);
                    }
                    b.position(end);
                    break;
                default:
                    throw new IllegalArgumentException("property type " + type);
            }
        }
    }

    /** Names of the top-level children of a WZ property image (zero key, 0xAA mask). */
    static List<String> topNames(byte[] data) {
        ByteBuffer b = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        if (!"Property".equals(strBlock(b))) {
            throw new IllegalArgumentException("no Property header");
        }
        b.getShort();
        int n = compressedInt(b);
        List<String> names = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            names.add(strBlock(b));
            int type = b.get() & 0xFF;
            switch (type) {
                case 0:
                    break;
                case 2:
                case 11:
                    b.getShort();
                    break;
                case 3:
                case 19:
                    compressedInt(b);
                    break;
                case 20:
                    if (b.get() == -128) {
                        b.getLong();
                    }
                    break;
                case 4:
                    if ((b.get() & 0xFF) == 0x80) {
                        b.getFloat();
                    }
                    break;
                case 5:
                    b.getDouble();
                    break;
                case 8:
                    strBlock(b);
                    break;
                case 9:
                    int size = b.getInt();
                    b.position(b.position() + size);
                    break;
                default:
                    throw new IllegalArgumentException("property type " + type);
            }
        }
        return names;
    }

    private static int compressedInt(ByteBuffer b) {
        byte v = b.get();
        return v == -128 ? b.getInt() : v;
    }

    private static String strBlock(ByteBuffer b) {
        int t = b.get() & 0xFF;
        if (t == 0 || t == 0x73) {
            return wzString(b);
        }
        if (t == 1 || t == 0x1b) {
            int off = b.getInt();
            int save = b.position();
            b.position(off);
            String s = wzString(b);
            b.position(save);
            return s;
        }
        throw new IllegalArgumentException("string block " + t);
    }

    private static String wzString(ByteBuffer b) {
        int n = b.get();
        if (n == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (n > 0) {
            if (n == 127) {
                n = b.getInt();
            }
            int m = 0xAAAA;
            for (int i = 0; i < n; i++) {
                sb.append((char) ((b.getShort() ^ m) & 0xFFFF));
                m = (m + 1) & 0xFFFF;
            }
        } else {
            n = n == -128 ? b.getInt() : -n;
            int m = 0xAA;
            for (int i = 0; i < n; i++) {
                sb.append((char) ((b.get() ^ m) & 0xFF));
                m = (m + 1) & 0xFF;
            }
        }
        return sb.toString();
    }
}

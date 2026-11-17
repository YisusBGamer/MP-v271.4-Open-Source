package net.swordie.ms.jwzlib;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.jwzlib.common.WzHeader;
import net.swordie.ms.jwzlib.io.WzBufferInputStream;
import net.swordie.ms.jwzlib.io.WzInputStream;
import net.swordie.ms.jwzlib.io.WzMappedInputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Reads the 64-bit client's Data folder as it is installed, so a copy of it can be the server's wz folder:
 * <ul>
 *     <li>Data\&lt;Cat&gt;\[&lt;sub&gt;\...]&lt;X&gt;.ini + &lt;X&gt;_000.wz, &lt;X&gt;_001.wz, ...: the parts of one
 *     folder, merged into &lt;Cat&gt;.wz\&lt;sub&gt;\... The .ini holds the last part number (LastWzIndex|n); with
 *     -1 the data is in &lt;X&gt;.wz itself. A .wz with no .ini of its own is read whole.</li>
 *     <li>Data\Packs\&lt;Cat&gt;_00000.ms, ...: packed images, placed by their path (Skill/MobSkill/100.img goes to
 *     Skill.wz\MobSkill).</li>
 *     <li>_Canvas folders (bitmaps only) are left out, like the XML export does.</li>
 * </ul>
 * A split name (Mob001.wz, Mob2.wz, Map002.wz) opens the whole category, as with an XML export.
 */
final class WzDataReader {

    private static final Pattern TRAILING_DIGITS = Pattern.compile("^(.*?)\\d*$");
    private static final Pattern PART = Pattern.compile(".+_\\d{3}\\.wz", Pattern.CASE_INSENSITIVE);
    private static final Pattern LAST_INDEX = Pattern.compile("\\s*LastWzIndex\\s*\\|\\s*(-?\\d+)\\s*",
            Pattern.CASE_INSENSITIVE);
    private static final String[] QUEST_PARTS = {"Check", "Act", "QuestInfo", "Say"};
    /** Base and index files without data are tiny; a part this small that doesn't parse is skipped quietly. */
    private static final long SMALL_FILE = 4096;
    private static final String CANVAS = "_Canvas";

    private static volatile int knownVersion;

    private WzDataReader() {
    }

    /**
     * @return the category folder of a Data-style copy for this .wz path (wz/Mob001.wz -> wz/Mob), or null
     */
    static Path findCategory(Path wz) {
        Path parent = wz.toAbsolutePath().getParent();
        if (parent == null) {
            return null;
        }
        String cat = category(wz.getFileName().toString());
        Path dir = parent.resolve(cat);
        if (Files.isDirectory(dir) && (Files.exists(dir.resolve(cat + ".ini")) || Files.exists(dir.resolve(cat + ".wz")))) {
            return dir;
        }
        return null;
    }

    /** Etc.wz -> Etc, Mob001.wz -> Mob, Map002.wz -> Map */
    static String category(String fileName) {
        String name = fileName.toLowerCase(Locale.ROOT).endsWith(".wz") ? fileName.substring(0, fileName.length() - 3) : fileName;
        Matcher m = TRAILING_DIGITS.matcher(name);
        return m.matches() && !m.group(1).isEmpty() ? m.group(1) : name;
    }

    /**
     * @return whether the folder looks like a copy of a 64-bit client's Data folder
     */
    static boolean isDataFolder(Path dir) {
        Path base = dir.resolve("Base");
        return Files.isDirectory(base) && (Files.exists(base.resolve("Base.ini")) || Files.exists(base.resolve("Base.wz")))
                || Files.isDirectory(dir.resolve("Packs")) && Files.isDirectory(dir.resolve("Etc"));
    }

    static WzDirectory readCategory(Path catDir, String name) {
        WzDirectory root = new WzDirectory(name);
        addFolder(root, catDir);
        addPacks(root, catDir);
        sort(root);
        if (catDir.getFileName().toString().equalsIgnoreCase("Quest")) {
            addQuestParts(root);
        }
        return root;
    }

    private static void addFolder(WzDirectory target, Path dir) {
        List<Path> entries = list(dir);
        List<String> withIni = new ArrayList<>();
        for (Path p : entries) {
            String fn = p.getFileName().toString();
            if (!Files.isRegularFile(p) || !fn.toLowerCase(Locale.ROOT).endsWith(".ini")) {
                continue;
            }
            String base = fn.substring(0, fn.length() - 4);
            withIni.add(base.toLowerCase(Locale.ROOT));
            int last = lastIndex(p);
            boolean anyPart = false;
            for (int i = 0; i <= last; i++) {
                Path part = dir.resolve(String.format("%s_%03d.wz", base, i));
                if (Files.isRegularFile(part)) {
                    addWzFile(target, part);
                    anyPart = true;
                }
            }
            Path whole = dir.resolve(base + ".wz");
            if (!anyPart && Files.isRegularFile(whole)) {
                addWzFile(target, whole);
            }
        }
        for (Path p : entries) {
            String fn = p.getFileName().toString();
            if (Files.isRegularFile(p) && fn.toLowerCase(Locale.ROOT).endsWith(".wz") && !PART.matcher(fn).matches()
                    && !withIni.contains(fn.substring(0, fn.length() - 3).toLowerCase(Locale.ROOT))) {
                addWzFile(target, p);
            }
        }
        for (Path p : entries) {
            String fn = p.getFileName().toString();
            if (Files.isDirectory(p) && !fn.equalsIgnoreCase(CANVAS)) {
                addFolder(childDir(target, fn), p);
            }
        }
    }

    private static void addPacks(WzDirectory root, Path catDir) {
        Path packs = catDir.getParent().resolve("Packs");
        if (!Files.isDirectory(packs)) {
            return;
        }
        String prefix = catDir.getFileName().toString().toLowerCase(Locale.ROOT) + "_";
        for (Path p : list(packs)) {
            String fn = p.getFileName().toString().toLowerCase(Locale.ROOT);
            if (!fn.startsWith(prefix) || !fn.endsWith(".ms") || !fn.substring(prefix.length(), fn.length() - 3).matches("\\d+")) {
                continue;
            }
            WzMsPack pack;
            try {
                pack = WzMsPack.open(p);
            } catch (IOException | RuntimeException e) {
                throw new WzDataException("Cannot read " + p + " (" + e.getMessage() + "). " + COPY_AGAIN);
            }
            for (WzMsPack.Entry entry : pack.getEntries()) {
                String[] path = entry.name.split("/");
                WzDirectory dir = root;
                // the first part is the category itself
                for (int i = 1; i < path.length - 1; i++) {
                    dir = childDir(dir, path[i]);
                }
                WzImage image = new WzImage(entry.imageName(), 0, entry.size, 0);
                image.setXmlSource(img -> fillPacked(img, pack.read(entry)));
                dir.putChild(image);
            }
        }
    }

    /** Where a part's root directory starts and how its offsets are hashed. */
    private static final class Layout {
        final WzHeader header;
        /** the encrypted version number before the root directory, or -1 if the file has none */
        final int encVersion;
        final int dirStart;
        /** files without the version number store string offsets one byte further on */
        final int stringShift;

        Layout(WzHeader header, int encVersion) {
            this.header = header;
            this.encVersion = encVersion;
            dirStart = header.FILE_START + (encVersion >= 0 ? 2 : 0);
            stringShift = encVersion >= 0 ? 0 : 1;
        }
    }

    private static final byte[] TEXT_IMAGE = "#Property".getBytes(StandardCharsets.US_ASCII);

    /** A packed image is a binary .img, or (e.g. Skill/Recipe_9200.img) the client's "#Property" text form. */
    private static void fillPacked(WzImage image, byte[] data) {
        if (data.length > 0 && (data[0] == 0x73 || data[0] == 0x01 || data[0] == 0x1B)) {
            image.parse(new WzBufferInputStream(data));
        } else if (Arrays.equals(data, 0, Math.min(data.length, TEXT_IMAGE.length), TEXT_IMAGE, 0, TEXT_IMAGE.length)) {
            readTextImage(image, new String(data, StandardCharsets.UTF_8));
        }
        // other text forms (Root <Property>) hold nothing the server reads
    }

    /** Lines of "name = value" and "name = {" ... "}"; numbers become ints (longs if they don't fit). */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void readTextImage(WzImage image, String text) {
        Deque<WzProperty<?>> open = new ArrayDeque<>();
        for (String raw : text.split("\r?\n")) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            if (line.equals("}")) {
                open.poll();
                continue;
            }
            int eq = line.indexOf(" = ");
            if (eq <= 0) {
                continue;
            }
            String name = line.substring(0, eq).trim();
            String value = line.substring(eq + 3).trim();
            WzProperty prop;
            boolean container = value.equals("{");
            if (container) {
                prop = new WzProperty<>(name, null, WzProperty.Type.SUB_PROPERTY, true);
            } else if (value.matches("-?[0-9]{1,18}")) {
                long v = Long.parseLong(value);
                prop = v == (int) v ? new WzProperty<>(name, (int) v, WzProperty.Type.COMPRESSED_INTEGER)
                        : new WzProperty<>(name, v, WzProperty.Type.COMPRESSED_LONG);
            } else {
                prop = new WzProperty<>(name, value, WzProperty.Type.STRING);
            }
            if (open.isEmpty()) {
                image.addChild(prop);
            } else {
                open.peek().addChild(prop);
            }
            if (container) {
                open.push(prop);
            }
        }
    }

    /** Parses one .wz part and merges its tree into target. */
    private static void addWzFile(WzDirectory target, Path file) {
        long size;
        try {
            size = Files.size(file);
        } catch (IOException e) {
            throw new WzDataException("Cannot read " + file + " (" + e.getMessage() + "). " + COPY_AGAIN);
        }
        try {
            WzMappedInputStream in = new WzMappedInputStream(file);
            Layout layout = readLayout(in, file);
            if (layout == null) {
                return; // no directory in it
            }
            int version = knownVersion;
            // a version number in the file settles the hash; without one, check the known hash still reads it
            boolean known = version != 0 && matches(layout, version)
                    && (layout.encVersion >= 0 || tryParse(in, layout, versionHash(version)));
            if (!known) {
                version = findVersion(in, layout, file);
                if (version == 0) {
                    if (size < SMALL_FILE) {
                        return; // an index file with nothing in it
                    }
                    throw new WzDataException(file + " doesn't read as a v" + ServerConstants.VERSION
                            + " client file. " + WRONG_VERSION);
                }
                knownVersion = version;
            }
            in.setHash(versionHash(version));
            parseDirectory(in, layout, layout.dirStart, target, 0);
        } catch (WzDataException e) {
            throw e;
        } catch (RuntimeException e) {
            if (size < SMALL_FILE) {
                return;
            }
            throw new WzDataException("Cannot read " + file + " (" + e + "). " + COPY_AGAIN);
        }
    }

    /** @return the layout, or null if the file has nothing past its header */
    private static Layout readLayout(WzInputStream in, Path file) {
        WzHeader h = new WzHeader();
        in.seek(0);
        h.IDENT = in.readStringByLen(4);
        if (!"PKG1".equals(h.IDENT)) {
            throw new WzDataException(file + " is not a .wz file. " + COPY_AGAIN);
        }
        h.FILE_SIZE = in.readInteger();
        in.skip(4);
        h.FILE_START = in.readInteger();
        in.setHeader(h);
        if (h.FILE_SIZE < 2) {
            return null;
        }
        in.seek(h.FILE_START);
        int first = in.readShort() & 0xFFFF;
        boolean noVersion = first > 0xFF;
        if (!noVersion && first == 0x80) {
            in.seek(h.FILE_START);
            int v = in.readInteger();
            noVersion = v > 0 && (v & 0xFF) == 0 && v <= 0xFFFF;
        }
        return new Layout(h, noVersion ? -1 : first);
    }

    /**
     * @return the client version whose hash reads this file (0 if none), the server's own first. A file with a
     * version number only takes versions that encrypt to it, which is how another client version shows.
     */
    private static int findVersion(WzInputStream in, Layout layout, Path file) {
        if (layout.encVersion >= 0 && !matches(layout, ServerConstants.VERSION)) {
            throw new WzDataException(file + " is from another client version, not v" + ServerConstants.VERSION
                    + ". " + WRONG_VERSION);
        }
        List<Integer> versions = new ArrayList<>();
        versions.add((int) ServerConstants.VERSION);
        versions.add((int) ServerConstants.VERSION_FOR_WZ);
        for (int v = 770; v < 780; v++) {
            versions.add(v);
        }
        for (int v : versions) {
            if (matches(layout, v) && tryParse(in, layout, versionHash(v))) {
                return v;
            }
        }
        return 0;
    }

    private static boolean matches(Layout layout, int version) {
        return layout.encVersion < 0 || encryptedVersion(version) == layout.encVersion;
    }

    static int versionHash(int version) {
        int hash = 0;
        for (char c : String.valueOf(version).toCharArray()) {
            hash = (hash << 5) + c + 1;
        }
        return hash;
    }

    static int encryptedVersion(int version) {
        int h = versionHash(version);
        return ~((h >>> 24) ^ (h >>> 16) ^ (h >>> 8) ^ h) & 0xFF;
    }

    /** A trial parse: the wrong hash gives offsets that point at noise rather than an image header. */
    private static boolean tryParse(WzInputStream in, Layout layout, int hash) {
        in.setHash(hash);
        WzDirectory probe = new WzDirectory("probe");
        try {
            parseDirectory(in, layout, layout.dirStart, probe, 0);
        } catch (RuntimeException e) {
            return false;
        }
        WzImage first = firstImage(probe);
        if (first == null) {
            return true;
        }
        in.seek(first.getOffset());
        int b = in.read();
        return b == 0x73 || b == 0x01 || b == 0x1B;
    }

    private static WzImage firstImage(WzDirectory dir) {
        for (WzObject<?, ?> img : dir.getImages().values()) {
            return (WzImage) img;
        }
        for (WzObject<?, ?> sub : dir.getDirectories().values()) {
            WzImage img = firstImage((WzDirectory) sub);
            if (img != null) {
                return img;
            }
        }
        return null;
    }

    private static void parseDirectory(WzInputStream in, Layout layout, int offset, WzDirectory target, int depth) {
        if (depth > 64) {
            throw new IllegalStateException("directory nesting too deep");
        }
        WzHeader h = layout.header;
        in.seek(offset);
        int count = in.readCompressedInteger();
        if (count < 0 || count > 100_000) {
            throw new IllegalStateException("bad entry count " + count);
        }
        List<String> dirNames = new ArrayList<>();
        List<Integer> dirOffsets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int type = in.readByte();
            String name;
            int resume;
            if (type == 1) {
                in.readInteger();
                in.readShort();
                in.readInteger();
                continue;
            } else if (type == 2) {
                int strOff = in.readInteger();
                resume = in.getPosition();
                in.seek(h.FILE_START + strOff + layout.stringShift);
                type = in.readByte();
                name = in.readString();
            } else if (type == 3 || type == 4) {
                name = in.readString();
                resume = in.getPosition();
            } else {
                throw new IllegalStateException("unknown directory entry type " + type);
            }
            if (type != 3 && type != 4 || !plain(name)) {
                throw new IllegalStateException("bad directory entry");
            }
            in.seek(resume);
            int size = in.readCompressedInteger();
            int checksum = in.readCompressedInteger();
            int off = in.readOffset();
            if (size < 0 || off < 0 || (long) off + size > (long) h.FILE_SIZE + h.FILE_START) {
                throw new IllegalStateException("bad directory entry offset");
            }
            if (type == 3) {
                dirNames.add(name);
                dirOffsets.add(off);
            } else {
                WzImage img = new WzImage(name, off, size, checksum);
                img.setStoredInput(in);
                target.putChild(img);
            }
        }
        for (int i = 0; i < dirNames.size(); i++) {
            parseDirectory(in, layout, dirOffsets.get(i), childDir(target, dirNames.get(i)), depth + 1);
        }
    }

    private static boolean plain(String s) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 0x20 || c >= 0x7F) {
                return false;
            }
        }
        return true;
    }

    private static WzDirectory childDir(WzDirectory parent, String name) {
        WzObject<?, ?> existing = parent.getDirectory(name);
        if (existing instanceof WzDirectory) {
            return (WzDirectory) existing;
        }
        WzDirectory child = new WzDirectory(name);
        parent.putChild(child);
        return child;
    }

    /** Name order (as an XML export lists them), so the dat files come out the same from either. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void sort(WzDirectory dir) {
        List<WzObject<?, ?>> children = new ArrayList<>();
        children.addAll((java.util.Collection) dir.getImages().values());
        children.addAll((java.util.Collection) dir.getDirectories().values());
        children.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.getName(), b.getName()));
        dir.getImages().clear();
        dir.getDirectories().clear();
        for (WzObject<?, ?> child : children) {
            dir.putChild(child);
            if (child instanceof WzDirectory) {
                sort((WzDirectory) child);
            }
        }
    }

    /** Quest.wz keeps one image per quest in QuestData; the loaders read Check/Act/QuestInfo/Say.img. */
    private static void addQuestParts(WzDirectory root) {
        WzObject<?, ?> questData = root.getDirectory("QuestData");
        if (!(questData instanceof WzDirectory) || root.getImage("Check.img") != null) {
            return;
        }
        WzDirectory qd = (WzDirectory) questData;
        for (String part : QUEST_PARTS) {
            WzImage image = new WzImage(part + ".img", 0, 0, 0);
            image.setXmlSource(img -> fillQuestPart(qd, part, img));
            root.putChild(image);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void fillQuestPart(WzDirectory questData, String part, WzImage target) {
        for (WzObject<WzImage, ?> obj : questData.getImages().values()) {
            WzImage quest = (WzImage) obj;
            String questId = quest.getName().replace(".img", "");
            WzProperty<?> src = quest.getChild(part);
            if (src != null) {
                WzProperty copy = new WzProperty<>(questId, src.getValue(), src.getPropertyType(), src.getChildren() != null);
                if (src.getChildren() != null) {
                    for (WzProperty<?> child : new ArrayList<>(src.getChildren().values())) {
                        copy.addChild(child);
                    }
                }
                target.addChild(copy);
            }
            quest.unparse();
        }
    }

    private static int lastIndex(Path ini) {
        try {
            for (String line : Files.readAllLines(ini)) {
                Matcher m = LAST_INDEX.matcher(line);
                if (m.matches()) {
                    return Integer.parseInt(m.group(1));
                }
            }
        } catch (IOException | NumberFormatException e) {
            // read as no parts
        }
        return 0;
    }

    private static List<Path> list(Path dir) {
        try (Stream<Path> s = Files.list(dir)) {
            return s.sorted().toList();
        } catch (IOException e) {
            throw new WzDataException("Cannot list " + dir + " (" + e.getMessage() + ").");
        }
    }

    static final String COPY_AGAIN = "Copy the client's Data folder into the wz folder again.";
    static final String WRONG_VERSION = "The wz folder needs the Data folder of the v" + ServerConstants.VERSION
            + " client (64-bit, Steam build); copy it again from that client.";

    /**
     * @return what's wrong with the wz folder for this .wz path, in one line, or null if nothing is
     */
    static String problem(Path wzDir, String fileName) {
        String setting = "data.wzDir in server.properties";
        if (!Files.isDirectory(wzDir)) {
            return "The wz folder " + wzDir + " doesn't exist. Copy everything in the client's Data folder into it, "
                    + "or point " + setting + " at the client's Data folder.";
        }
        Path nested = wzDir.resolve("Data");
        if (Files.isRegularFile(wzDir.resolve("MapleStory.exe"))) {
            return wzDir + " is the client folder; the server needs what's in its Data folder. Point " + setting
                    + " at " + nested + ", or copy that folder's contents into the wz folder.";
        }
        if (isDataFolder(nested)) {
            return "The game data in " + wzDir + " is one folder too deep: move everything in " + nested
                    + " up into " + wzDir + " (or point " + setting + " at " + nested + ").";
        }
        if (isDataFolder(wzDir)) {
            return wzDir + " has no " + category(fileName) + " folder. " + COPY_AGAIN;
        }
        if (list(wzDir).isEmpty()) {
            return "The wz folder " + wzDir + " is empty. Copy everything in the client's Data folder (Base, Etc, "
                    + "Map, ..., Packs) into it.";
        }
        return "No game data for " + fileName + " in " + wzDir + ". Copy everything in the client's Data folder "
                + "(Base, Etc, Map, ..., Packs) into it, or use an XML export (one folder per .wz).";
    }
}

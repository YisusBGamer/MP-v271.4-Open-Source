package net.swordie.ms.jwzlib;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Reads an XML export of a .wz file (a folder of X.img.xml or X.xml files) into the same tree the binary reader
 * builds, so the loaders work on either. Images are parsed when first used.
 * <ul>
 *     <li>Both common export formats are read: imgdir/int/float/canvas/vector x="" y="" and
 *     dir/int32/single/png/vector value="x, y".</li>
 *     <li>Numbered data packs (Skill_00000, ...) are merged into their parent folder, as the client does.</li>
 *     <li>A file name like Map002.wz or Mob2.wz with no folder of its own falls back to Map.wz / Mob.wz.</li>
 *     <li>A Quest.wz that only has per-quest QuestData images also offers Check.img, Act.img, QuestInfo.img and
 *     Say.img, built from them.</li>
 * </ul>
 */
final class WzXmlReader {

    private static final Logger log = LogManager.getLogger(WzXmlReader.class);

    interface Source {
        void fill(WzImage image);
    }

    private static final Pattern DATA_PACK = Pattern.compile(".+_\\d{5}");
    private static final Pattern SPLIT_FILE = Pattern.compile("\\d+\\.wz$");
    private static final String[] QUEST_PARTS = {"Check", "Act", "QuestInfo", "Say"};
    private static final XMLInputFactory FACTORY = XMLInputFactory.newFactory();

    static {
        FACTORY.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        FACTORY.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        // exports keep images and sounds as base64 attributes, far past the JDK's default size limits
        for (String limit : new String[]{"jdk.xml.maxGeneralEntitySizeLimit", "jdk.xml.totalEntitySizeLimit"}) {
            try {
                FACTORY.setProperty(limit, 0);
            } catch (IllegalArgumentException e) {
                log.error("XML reader: cannot lift " + limit);
            }
        }
    }

    private WzXmlReader() {
    }

    /**
     * @return the export folder for this .wz path, or null if the path is a binary .wz file (or nothing is there)
     */
    static Path findFolder(Path wz) {
        if (Files.isRegularFile(wz)) {
            return null;
        }
        if (Files.isDirectory(wz)) {
            return wz;
        }
        String name = wz.getFileName().toString();
        String base = SPLIT_FILE.matcher(name).replaceFirst(".wz");
        if (!base.equals(name) && wz.getParent() != null && Files.isDirectory(wz.getParent().resolve(base))) {
            return wz.getParent().resolve(base);
        }
        return null;
    }

    static WzDirectory readFolder(Path folder, String name) {
        WzDirectory root = new WzDirectory(name);
        addEntries(root, folder);
        if (folder.getFileName().toString().equalsIgnoreCase("Quest.wz")) {
            addQuestParts(root, folder.resolve("QuestData"));
        }
        return root;
    }

    private static void addEntries(WzDirectory target, Path folder) {
        List<Path> entries;
        try (Stream<Path> list = Files.list(folder)) {
            entries = list.sorted().toList();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot list " + folder, e);
        }
        for (Path entry : entries) {
            String fileName = entry.getFileName().toString();
            if (Files.isDirectory(entry)) {
                if (DATA_PACK.matcher(fileName).matches()) {
                    addEntries(target, entry);
                } else {
                    WzObject<?, ?> existing = target.getDirectory(fileName);
                    WzDirectory child;
                    if (existing instanceof WzDirectory) {
                        child = (WzDirectory) existing;
                    } else {
                        child = new WzDirectory(fileName);
                        target.putChild(child);
                    }
                    addEntries(child, entry);
                }
            } else if (fileName.endsWith(".xml")) {
                String imageName = fileName.substring(0, fileName.length() - 4);
                if (!imageName.endsWith(".img")) {
                    imageName += ".img";
                }
                WzImage image = new WzImage(imageName, 0, 0, 0);
                image.setXmlSource(img -> readImage(entry, img));
                target.putChild(image);
            }
        }
    }

    private static void addQuestParts(WzDirectory root, Path questData) {
        if (!Files.isDirectory(questData) || root.getImage("Check.img") != null) {
            return;
        }
        for (String part : QUEST_PARTS) {
            WzImage image = new WzImage(part + ".img", 0, 0, 0);
            image.setXmlSource(img -> readQuestPart(questData, part, img));
            root.putChild(image);
        }
    }

    private static void readImage(Path file, WzImage image) {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(file))) {
            XMLStreamReader r = FACTORY.createXMLStreamReader(in);
            try {
                if (!nextStart(r)) {
                    return;
                }
                // children of the root element are the image's properties
                while (r.hasNext()) {
                    int ev = r.next();
                    if (ev == XMLStreamConstants.START_ELEMENT) {
                        WzProperty<?> prop = readProperty(r, null);
                        if (prop != null) {
                            image.addChild(prop);
                        }
                    } else if (ev == XMLStreamConstants.END_ELEMENT) {
                        break;
                    }
                }
            } finally {
                r.close();
            }
        } catch (IOException | XMLStreamException e) {
            log.error(String.format("Cannot read %s: %s%n", file, e.getMessage()));
        }
    }

    /**
     * Fills a Check/Act/QuestInfo/Say image with one child per quest (named after the quest id), taken from
     * QuestData/&lt;id&gt;.img.
     */
    private static void readQuestPart(Path questData, String part, WzImage image) {
        List<Path> files;
        try (Stream<Path> list = Files.list(questData)) {
            files = list.filter(p -> p.getFileName().toString().endsWith(".xml")).sorted().toList();
        } catch (IOException e) {
            log.error(String.format("Cannot list %s: %s%n", questData, e.getMessage()));
            return;
        }
        for (Path file : files) {
            String questId = file.getFileName().toString().replace(".xml", "").replace(".img", "");
            try (InputStream in = new BufferedInputStream(Files.newInputStream(file))) {
                XMLStreamReader r = FACTORY.createXMLStreamReader(in);
                try {
                    if (!nextStart(r)) {
                        continue;
                    }
                    while (r.hasNext()) {
                        int ev = r.next();
                        if (ev == XMLStreamConstants.START_ELEMENT) {
                            if (part.equals(r.getAttributeValue(null, "name"))) {
                                WzProperty<?> prop = readProperty(r, questId);
                                if (prop != null) {
                                    image.addChild(prop);
                                }
                                break;
                            }
                            skipElement(r);
                        } else if (ev == XMLStreamConstants.END_ELEMENT) {
                            break;
                        }
                    }
                } finally {
                    r.close();
                }
            } catch (IOException | XMLStreamException e) {
                log.error(String.format("Cannot read %s: %s%n", file, e.getMessage()));
            }
        }
    }

    private static boolean nextStart(XMLStreamReader r) throws XMLStreamException {
        while (r.hasNext()) {
            if (r.next() == XMLStreamConstants.START_ELEMENT) {
                return true;
            }
        }
        return false;
    }

    /** Reads the element the reader is on (and its children); leaves the reader on its end tag. */
    private static WzProperty<?> readProperty(XMLStreamReader r, String nameOverride) throws XMLStreamException {
        String tag = r.getLocalName();
        String name = nameOverride != null ? nameOverride : r.getAttributeValue(null, "name");
        String value = r.getAttributeValue(null, "value");
        WzProperty<?> prop;
        boolean container = false;
        switch (tag) {
            case "imgdir", "dir" -> {
                prop = new WzProperty<>(name, null, WzProperty.Type.SUB_PROPERTY, true);
                container = true;
            }
            case "canvas", "png" -> {
                prop = new WzProperty<>(name, null, WzProperty.Type.CANVAS, true);
                container = true;
            }
            case "extended", "convex" -> {
                prop = new WzProperty<>(name, null, WzProperty.Type.CONVEX, true);
                container = true;
            }
            case "int", "int32" -> prop = new WzProperty<>(name, (int) parseLong(value), WzProperty.Type.COMPRESSED_INTEGER);
            case "short", "int16" -> prop = new WzProperty<>(name, (short) parseLong(value), WzProperty.Type.UNSIGNED_SHORT);
            case "long", "int64" -> prop = new WzProperty<>(name, parseLong(value), WzProperty.Type.COMPRESSED_LONG);
            case "float", "single" -> prop = new WzProperty<>(name, (float) parseDouble(value), WzProperty.Type.BYTE_FLOAT);
            case "double" -> prop = new WzProperty<>(name, parseDouble(value), WzProperty.Type.DOUBLE);
            case "string" -> prop = new WzProperty<>(name, value == null ? "" : value, WzProperty.Type.STRING);
            case "uol" -> prop = new WzProperty<>(name, value, WzProperty.Type.UOL);
            case "vector" -> prop = new WzProperty<>(name, parseVector(r, value), WzProperty.Type.VECTOR);
            case "sound" -> prop = new WzProperty<>(name, null, WzProperty.Type.SOUND);
            case "null" -> prop = new WzProperty<>(name, null, WzProperty.Type.NULL);
            default -> {
                if (value != null) {
                    prop = new WzProperty<>(name, value, WzProperty.Type.STRING);
                } else {
                    prop = new WzProperty<>(name, null, WzProperty.Type.SUB_PROPERTY, true);
                    container = true;
                }
            }
        }
        while (r.hasNext()) {
            int ev = r.next();
            if (ev == XMLStreamConstants.START_ELEMENT) {
                if (container) {
                    WzProperty<?> child = readProperty(r, null);
                    if (child != null) {
                        prop.addChild(child);
                    }
                } else {
                    skipElement(r);
                }
            } else if (ev == XMLStreamConstants.END_ELEMENT) {
                break;
            }
        }
        return name == null ? null : prop;
    }

    private static void skipElement(XMLStreamReader r) throws XMLStreamException {
        int depth = 1;
        while (depth > 0 && r.hasNext()) {
            int ev = r.next();
            if (ev == XMLStreamConstants.START_ELEMENT) {
                depth++;
            } else if (ev == XMLStreamConstants.END_ELEMENT) {
                depth--;
            }
        }
    }

    private static Point parseVector(XMLStreamReader r, String value) {
        String x = r.getAttributeValue(null, "x");
        String y = r.getAttributeValue(null, "y");
        if ((x == null || y == null) && value != null) {
            String[] split = value.split(",");
            if (split.length == 2) {
                x = split[0];
                y = split[1];
            }
        }
        return new Point((int) parseLong(x), (int) parseLong(y));
    }

    private static long parseLong(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }
        String v = value.trim();
        try {
            return Long.parseLong(v);
        } catch (NumberFormatException e) {
            try {
                return (long) Double.parseDouble(v);
            } catch (NumberFormatException e2) {
                return 0;
            }
        }
    }

    private static double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

package net.swordie.ms.connection.packet;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.ServerSettings;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

/**
 * WZ_CHECK_LIST and FILE_CHECK_LIST: names of the game client's own files (an int count, then one string per file,
 * relative to the client folder). The client handles both in its protected socket code. The lists are made once from
 * the owner's client folder (data.clientDir in server.properties) and kept in the dat folder; without either, the
 * lists go out empty.
 */
public class ClientFileLists {

    private static final Logger log = LogManager.getRootLogger();

    private static final String CLIENT_DIR = ServerSettings.getString("data.clientDir", null);
    private static final Path OUT_DIR = Path.of(ServerConstants.DAT_DIR, "client-files");
    private static final String WZ_LIST = "wz_check_list.txt";
    private static final String FILE_LIST = "file_check_list.txt";

    private static final Set<String> ROOT_EXTENSIONS = Set.of("dll", "exe", "aes", "dat", "bin", "ini");
    private static final Set<String> DATA_EXTENSIONS = Set.of("wz", "ini", "ms");
    // files a launcher adds next to the game; not part of the client
    private static final Set<String> NOT_CLIENT = Set.of("msvn.dll", "wpfanimatedgif.dll");

    private static volatile List<String> wzList;
    private static volatile List<String> fileList;

    public static OutPacket wzCheckList() {
        return encode(OutHeader.WZ_CHECK_LIST, getWzList());
    }

    public static OutPacket fileCheckList() {
        return encode(OutHeader.FILE_CHECK_LIST, getFileList());
    }

    private static OutPacket encode(OutHeader header, List<String> names) {
        OutPacket outPacket = new OutPacket(header);
        outPacket.encodeInt(names.size());
        for (String name : names) {
            outPacket.encodeString(name);
        }
        return outPacket;
    }

    private static List<String> getWzList() {
        if (wzList == null) {
            load();
        }
        return wzList;
    }

    private static List<String> getFileList() {
        if (fileList == null) {
            load();
        }
        return fileList;
    }

    private static synchronized void load() {
        if (wzList != null && fileList != null) {
            return;
        }
        Path wz = OUT_DIR.resolve(WZ_LIST);
        Path file = OUT_DIR.resolve(FILE_LIST);
        try {
            if ((!Files.isRegularFile(wz) || !Files.isRegularFile(file)) && CLIENT_DIR != null) {
                Path client = Path.of(CLIENT_DIR);
                if (Files.isRegularFile(client.resolve("MapleStory.exe"))) {
                    write(client, OUT_DIR);
                    log.info("Client file lists made from " + client + " in " + OUT_DIR);
                } else {
                    log.warn("data.clientDir has no MapleStory.exe: " + client);
                }
            }
            if (Files.isRegularFile(wz) && Files.isRegularFile(file)) {
                fileList = read(file);
                wzList = read(wz);
                return;
            }
            log.info("No client file lists (optional): data.clientDir in server.properties can point at the game folder. "
                    + "Sending empty lists.");
        } catch (IOException e) {
            log.error("Could not make the client file lists", e);
        }
        fileList = List.of();
        wzList = List.of();
    }

    private static List<String> read(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8).stream().filter(s -> !s.isEmpty()).toList();
    }

    /** Writes both lists for the client in {@code client} into {@code outDir}. */
    public static void write(Path client, Path outDir) throws IOException {
        List<String> all = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(client)) {
            walk.filter(Files::isRegularFile)
                    .forEach(p -> all.add(client.relativize(p).toString().replace('/', '\\')));
        }
        Files.createDirectories(outDir);
        Files.write(outDir.resolve(WZ_LIST), wzCheckNames(all), StandardCharsets.UTF_8);
        Files.write(outDir.resolve(FILE_LIST), fileCheckNames(all), StandardCharsets.UTF_8);
    }

    /**
     * Every .wz under Data, a folder's contents before the files that follow it by name (sorted part by part):
     * Data\Map\Map\Map0\... comes before Data\Map\Map.wz.
     */
    static List<String> fileCheckNames(List<String> all) {
        return all.stream()
                .filter(n -> n.startsWith("Data\\") && extension(n).equals("wz"))
                .sorted(ClientFileLists::comparePaths)
                .toList();
    }

    /**
     * The game's program files and data: the files in the client folder itself, the NxOverlay folder, and the .wz,
     * .ini and .ms files under Data except Sound and the _Canvas folders. Sorted as plain strings.
     */
    static List<String> wzCheckNames(List<String> all) {
        return all.stream().filter(ClientFileLists::inWzCheck).sorted().toList();
    }

    private static boolean inWzCheck(String name) {
        String[] parts = name.split("\\\\");
        String ext = extension(name);
        if (parts.length == 1) {
            String lower = name.toLowerCase(Locale.ROOT);
            return (ROOT_EXTENSIONS.contains(ext) || lower.equals("steam_appid.txt"))
                    && !NOT_CLIENT.contains(lower) && !lower.contains("launcher");
        }
        if (parts[0].equals("NxOverlay")) {
            return true;
        }
        if (parts[0].equals("Data")) {
            if (parts[1].equals("Sound")) {
                return false;
            }
            for (String part : parts) {
                if (part.equals("_Canvas")) {
                    return false;
                }
            }
            return DATA_EXTENSIONS.contains(ext);
        }
        return false;
    }

    private static String extension(String name) {
        int dot = name.lastIndexOf('.');
        int slash = name.lastIndexOf('\\');
        return dot > slash ? name.substring(dot + 1).toLowerCase(Locale.ROOT) : "";
    }

    private static int comparePaths(String a, String b) {
        String[] pa = a.split("\\\\");
        String[] pb = b.split("\\\\");
        for (int i = 0; i < Math.min(pa.length, pb.length); i++) {
            int c = pa[i].compareTo(pb[i]);
            if (c != 0) {
                return c;
            }
        }
        return Integer.compare(pa.length, pb.length);
    }

    /** Makes the lists from a client folder: ClientFileLists &lt;client folder&gt; [output folder]. */
    public static void main(String[] args) throws IOException {
        String dir = args.length > 0 ? args[0] : CLIENT_DIR;
        if (dir == null) {
            System.out.println("Usage: ClientFileLists <client folder> [output folder]"); // hardcode-ok
            return;
        }
        Path client = Path.of(dir);
        Path out = args.length > 1 ? Path.of(args[1]) : OUT_DIR;
        write(client, out);
        System.out.println("Wrote " + out.resolve(WZ_LIST) + " and " + out.resolve(FILE_LIST)); // hardcode-ok
    }
}

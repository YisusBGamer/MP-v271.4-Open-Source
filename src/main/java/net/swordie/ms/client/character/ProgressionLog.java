package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.world.BuffedChannels;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Players' level-ups, logins and logouts for the progression dashboard (tools/graph/build_progression.py), written to
 * logs/progression/player-levels.tsv (event: login, logout, level or rebirth). Characters are named only by a salted hash of their id (the salt is made once
 * and kept in logs/progression/salt.txt), never by name or account. Mesos players spend go to
 * logs/progression/meso-sinks.tsv, by sink. Named level-up rows (account, character, minutes online since the last one)
 * go to players-<date>.tsv for the dashboard graph. Bots are left out.
 */
public final class ProgressionLog {
    private static final Logger log = LogManager.getLogger();

    public static final Path DIR = Paths.get(ServerConstants.LOG_DIR, "progression");
    public static final Path LEVELS = DIR.resolve("player-levels.tsv");
    private static final Path SALT = DIR.resolve("salt.txt");
    public static final Path SINKS = DIR.resolve("meso-sinks.tsv");
    private static final String HEADER = "time\tevent\tchar\tlevel\tjob\texpRate\tbuffed\tburning\tmesos\n";
    private static final String SINKS_HEADER = "time\tsink\tchar\tlevel\tmesos\n";

    /** Where spent mesos go. TRANSFER (trades, storage, dropped mesos) isn't spending and isn't logged. */
    public enum Sink {
        STAR_FORCE("starforce"), CUBES("cubes"), HAMMERS("hammers"), SHOPS("shops"), FEES("fees"),
        AUCTION("auction"), OTHER("other"), TRANSFER(null);

        private final String key;

        Sink(String key) {
            this.key = key;
        }
    }

    private static final ThreadLocal<Sink> sink = new ThreadLocal<>();
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final ExecutorService writer = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "ProgressionLog");
        t.setDaemon(true);
        return t;
    });
    private static volatile byte[] salt;

    private ProgressionLog() {
    }

    // per character: [millis of last login/level-up, level then]; online time only
    private static final Map<Integer, long[]> since = new ConcurrentHashMap<>();
    private static final DateTimeFormatter DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String PLAYERS_HEADER = "time\taccount\tcharId\tname\tjob\tlevel\tlevels\tminutes\texpRate\tmap\n";

    public static void onLogin(Char chr) {
        record(chr, "login");
        if (chr != null && !ServerHooks.isAutomated(chr)) {
            since.put(chr.getId(), new long[]{System.currentTimeMillis(), chr.getLevel()});
        }
    }

    public static void onLogout(Char chr) {
        record(chr, "logout");
        if (chr != null) {
            since.remove(chr.getId());
        }
    }

    public static void onRebirth(Char chr) {
        record(chr, "rebirth");
        if (chr != null) {
            since.remove(chr.getId());
        }
    }

    public static void onLevelUp(Char chr) {
        record(chr, "level");
        playerLevel(chr);
    }

    /** One named row per player level-up (players-yyyy-MM-dd.tsv): minutes online since the last one. */
    private static void playerLevel(Char chr) {
        if (chr == null || ServerHooks.isAutomated(chr)) {
            return;
        }
        long now = System.currentTimeMillis();
        int level = chr.getLevel();
        long[] prev = since.put(chr.getId(), new long[]{now, level});
        int gained = prev == null ? 1 : Math.max(1, level - (int) prev[1]);
        String mins = prev == null ? "" : String.format(Locale.ROOT, "%.2f", (now - prev[0]) / 60000.0);
        String account = chr.getUser() == null ? "?" : chr.getUser().getName();
        String name = chr.getName();
        int id = chr.getId();
        int job = chr.getJob();
        int expRate = ServerRates.getExpRate();
        int map = chr.getFieldID();
        LocalDateTime t = LocalDateTime.now();
        writer.execute(() -> write(DIR.resolve("players-" + t.format(DAY) + ".tsv"), PLAYERS_HEADER,
                String.join("\t", t.format(TIME), account, String.valueOf(id), name, String.valueOf(job),
                        String.valueOf(level), String.valueOf(gained), mins, String.valueOf(expRate),
                        String.valueOf(map)) + "\n"));
    }

    /** Runs {@code r} with mesos it removes counted as {@code s}. */
    public static void spending(Sink s, Runnable r) {
        Sink before = sink.get();
        sink.set(s);
        try {
            r.run();
        } finally {
            sink.set(before);
        }
    }

    /** Called by {@link Char#addMoney(long, boolean)} once mesos have been removed. */
    static void onMesosSpent(Char chr, long amount) {
        Sink s = sink.get() == null ? Sink.OTHER : sink.get();
        if (amount > 0 && chr != null) {
            ServerHooks.mesosSpent(chr, amount, s.key == null ? "transfer" : s.key);
        }
        if (amount <= 0 || s.key == null || chr == null || ServerHooks.isAutomated(chr)) {
            return;
        }
        String time = LocalDateTime.now().format(TIME);
        int id = chr.getId();
        int level = chr.getLevel();
        writer.execute(() -> write(SINKS, SINKS_HEADER, String.join("\t", time, s.key, hash(id),
                String.valueOf(level), String.valueOf(amount)) + "\n"));
    }

    private static void record(Char chr, String event) {
        if (chr == null || ServerHooks.isAutomated(chr)) {
            return;
        }
        // read now, on the caller's thread: the character may change before the writer gets to it
        String time = LocalDateTime.now().format(TIME);
        int id = chr.getId();
        int level = chr.getLevel();
        int job = chr.getJob();
        int expRate = ServerRates.getExpRate();
        boolean buffed = BuffedChannels.isBuffed(chr.getChannel());
        boolean burning = chr.getAvatarData() != null && chr.getAvatarData().getCharacterStat().isBurning();
        long mesos = chr.getMoney();
        writer.execute(() -> write(LEVELS, HEADER, String.join("\t", time, event, hash(id), String.valueOf(level),
                String.valueOf(job), String.valueOf(expRate), buffed ? "1" : "0", burning ? "1" : "0",
                String.valueOf(mesos)) + "\n"));
    }

    private static void write(Path file, String header, String line) {
        try {
            Files.createDirectories(DIR);
            if (!Files.exists(file)) {
                Files.writeString(file, header, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            }
            Files.writeString(file, line, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Progression: could not write " + file, e);
        }
    }

    private static String hash(int charId) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt());
            md.update(Integer.toString(charId).getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(md.digest()).substring(0, 16);
        } catch (Exception e) {
            return "?";
        }
    }

    /** Runs on the writer thread only. */
    private static byte[] salt() throws IOException {
        if (salt == null) {
            if (Files.exists(SALT)) {
                salt = HexFormat.of().parseHex(Files.readString(SALT, StandardCharsets.UTF_8).trim());
            } else {
                byte[] s = new byte[32];
                new SecureRandom().nextBytes(s);
                Files.createDirectories(DIR);
                Files.writeString(SALT, HexFormat.of().formatHex(s), StandardCharsets.UTF_8);
                salt = s;
            }
        }
        return salt;
    }
}

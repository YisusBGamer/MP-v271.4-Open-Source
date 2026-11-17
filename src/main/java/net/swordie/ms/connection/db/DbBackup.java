package net.swordie.ms.connection.db;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.ServerSettings;
import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Backups of the game database with mysqldump: on a timer (db.backup.hours, 0 = off), before a start runs new
 * migrations (db.backup.beforeMigration) and on request. Each backup is one zip in db.backup.dir holding a plain .sql
 * dump, named database-yyyyMMdd-HHmmss-kind.zip; the newest db.backup.keep of each kind are kept. The dump is one
 * consistent snapshot (--single-transaction) and never locks tables. Restoring: SETUP.md, "Backups".
 */
public final class DbBackup {

    private static final Logger log = LogManager.getLogger();

    public enum Kind { TIMED, MIGRATION, MANUAL }

    /** The last line mysqldump writes: a dump without it was cut short. */
    static final String DONE_MARK = "-- Dump completed";
    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final Pattern NAME = Pattern.compile("(.+)-(\\d{8}-\\d{6})-([a-z]+)\\.zip");
    /** The first timed backup after a start waits at least this long, so it never lands on the start itself. */
    private static final long MIN_FIRST_DELAY_MINUTES = 10;
    private static final long DUMP_TIMEOUT_MINUTES = 30;
    private static final int DEFAULT_HOURS = 6;
    private static final int DEFAULT_KEEP = 28;
    private static final String DUMP_EXE = File.separatorChar == '\\' ? "mysqldump.exe" : "mysqldump";

    private static final Object dumpLock = new Object();
    private static ScheduledExecutorService timer;

    private DbBackup() {
    }

    /** No mysqldump to run: backups are off until db.backup.mysqldump points at one. */
    public static final class NotAvailableException extends IOException {
        NotAvailableException(String msg) {
            super(msg);
        }
    }

    public static synchronized void start() {
        if (timer != null) {
            return;
        }
        int hours = ServerSettings.getInt("db.backup.hours", DEFAULT_HOURS);
        if (hours <= 0) {
            log.info("[Backup] timer off (db.backup.hours=0)");
            return;
        }
        if (findMysqldump() == null) {
            log.warn("[Backup] timer off: " + notFoundText());
            return;
        }
        long period = hours * 60L;
        long sinceLast = minutesSinceNewest(Kind.TIMED);
        long delay = Math.max(MIN_FIRST_DELAY_MINUTES, period - sinceLast);
        timer = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "DbBackup");
            t.setDaemon(true);
            t.setPriority(Thread.MIN_PRIORITY);
            return t;
        });
        timer.scheduleAtFixedRate(() -> {
            try {
                backup(Kind.TIMED);
            } catch (Throwable t) {
                log.error("[Backup] timed backup failed: " + t.getMessage(), t);
            }
        }, delay, period, TimeUnit.MINUTES);
        log.info("[Backup] every " + hours + " h into " + dir() + ", next in " + delay + " min");
    }

    public static Path dir() {
        return Path.of(ServerConstants.DIR).resolve(ServerSettings.getString("db.backup.dir", "backups/db"));
    }

    /**
     * Dumps the database now and prunes the oldest backups of the same kind.
     *
     * @return the zip it wrote
     * @throws NotAvailableException no mysqldump was found
     * @throws IOException the dump failed; nothing was written and no backup was pruned
     */
    public static Path backup(Kind kind) throws IOException {
        Path exe = findMysqldump();
        if (exe == null) {
            throw new NotAvailableException(notFoundText());
        }
        synchronized (dumpLock) {
            long start = System.currentTimeMillis();
            Db db = Db.fromSettings();
            Path dir = dir();
            Files.createDirectories(dir);
            String base = db.name + "-" + LocalDateTime.now().format(STAMP) + "-" + kind.name().toLowerCase();
            Path zip = dir.resolve(base + ".zip");
            Path part = dir.resolve(base + ".zip.part");
            Path err = dir.resolve(base + ".err");
            Path cnf = Files.createTempFile(dir, "dump", ".cnf");
            try {
                Files.writeString(cnf, db.optionFile(), StandardCharsets.UTF_8);
                List<String> cmd = new ArrayList<>(Arrays.asList(exe.toString(),
                        "--defaults-extra-file=" + cnf.toAbsolutePath(),
                        "--single-transaction", "--quick", "--hex-blob", "--no-tablespaces",
                        "--default-character-set=utf8mb4", db.name));
                Process p = new ProcessBuilder(cmd).redirectError(err.toFile()).start();
                boolean complete;
                try (InputStream in = p.getInputStream();
                     ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(part))) {
                    out.putNextEntry(new ZipEntry(base + ".sql"));
                    complete = copyAndCheckEnd(in, out);
                    out.closeEntry();
                }
                if (!p.waitFor(DUMP_TIMEOUT_MINUTES, TimeUnit.MINUTES)) {
                    p.destroyForcibly();
                    throw new IOException("mysqldump took over " + DUMP_TIMEOUT_MINUTES + " min");
                }
                if (p.exitValue() != 0 || !complete) {
                    String why = Files.exists(err) ? Files.readString(err, StandardCharsets.UTF_8).trim() : "";
                    throw new IOException("mysqldump exit " + p.exitValue() + (complete ? "" : ", dump cut short")
                            + (why.isEmpty() ? "" : ": " + why));
                }
                Files.move(part, zip, StandardCopyOption.ATOMIC_MOVE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("interrupted", e);
            } finally {
                Files.deleteIfExists(cnf);
                Files.deleteIfExists(err);
                Files.deleteIfExists(part);
            }
            log.info(String.format("[Backup] %s: %s (%.1f MB) in %d s", kind.name().toLowerCase(), zip,
                    Files.size(zip) / 1048576.0, (System.currentTimeMillis() - start) / 1000));
            prune(kind);
            return zip;
        }
    }

    /** Copies the dump into the zip and says whether it ended with mysqldump's completion line. */
    private static boolean copyAndCheckEnd(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[1 << 16];
        byte[] tail = new byte[256];
        int tailLen = 0;
        int n;
        while ((n = in.read(buf)) > 0) {
            out.write(buf, 0, n);
            if (n >= tail.length) {
                System.arraycopy(buf, n - tail.length, tail, 0, tail.length);
                tailLen = tail.length;
            } else {
                int keep = Math.min(tailLen, tail.length - n);
                System.arraycopy(tail, tailLen - keep, tail, 0, keep);
                System.arraycopy(buf, 0, tail, keep, n);
                tailLen = keep + n;
            }
        }
        return new String(tail, 0, tailLen, StandardCharsets.UTF_8).contains(DONE_MARK);
    }

    private static void prune(Kind kind) {
        int keep = Math.max(1, ServerSettings.getInt("db.backup.keep", DEFAULT_KEEP));
        List<Path> own = backups(kind);
        for (int i = keep; i < own.size(); i++) {
            try {
                Files.deleteIfExists(own.get(i));
            } catch (IOException e) {
                log.warn("[Backup] couldn't remove old backup " + own.get(i) + ": " + e.getMessage());
            }
        }
    }

    /** This kind's backups in the backup folder, newest first. */
    static List<Path> backups(Kind kind) {
        List<Path> ret = new ArrayList<>();
        Path dir = dir();
        if (!Files.isDirectory(dir)) {
            return ret;
        }
        String want = kind.name().toLowerCase();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*.zip")) {
            for (Path p : ds) {
                Matcher m = NAME.matcher(p.getFileName().toString());
                if (m.matches() && m.group(3).equals(want)) {
                    ret.add(p);
                }
            }
        } catch (IOException e) {
            log.warn("[Backup] couldn't list " + dir + ": " + e.getMessage());
        }
        ret.sort(Comparator.comparing((Path p) -> stampOf(p)).reversed());
        return ret;
    }

    /** The newest backup of any kind, or null. */
    public static Path newest() {
        Path best = null;
        for (Kind k : Kind.values()) {
            List<Path> own = backups(k);
            if (!own.isEmpty() && (best == null || stampOf(own.get(0)).compareTo(stampOf(best)) > 0)) {
                best = own.get(0);
            }
        }
        return best;
    }

    private static String stampOf(Path p) {
        Matcher m = NAME.matcher(p.getFileName().toString());
        return m.matches() ? m.group(2) : "";
    }

    private static long minutesSinceNewest(Kind kind) {
        List<Path> own = backups(kind);
        if (own.isEmpty()) {
            return Long.MAX_VALUE / 2;
        }
        try {
            LocalDateTime when = LocalDateTime.parse(stampOf(own.get(0)), STAMP);
            return Math.max(0, Duration.between(when, LocalDateTime.now()).toMinutes());
        } catch (RuntimeException e) {
            return Long.MAX_VALUE / 2;
        }
    }

    private static String notFoundText() {
        return "no " + DUMP_EXE + " found; put MySQL's bin folder on PATH or set db.backup.mysqldump in server.properties";
    }

    /**
     * db.backup.mysqldump (the program or its folder), else the first one on PATH, else the newest
     * "MySQL Server x.y" install under Program Files.
     */
    static Path findMysqldump() {
        String own = ServerSettings.getString("db.backup.mysqldump", "");
        if (!own.isEmpty()) {
            Path p = Path.of(own);
            Path exe = Files.isDirectory(p) ? p.resolve(DUMP_EXE) : p;
            return Files.isRegularFile(exe) ? exe : null;
        }
        String path = System.getenv("PATH");
        if (path != null) {
            for (String d : path.split(File.pathSeparator)) {
                if (d.isBlank()) {
                    continue;
                }
                try {
                    Path exe = Path.of(d.trim()).resolve(DUMP_EXE);
                    if (Files.isRegularFile(exe)) {
                        return exe;
                    }
                } catch (RuntimeException ignored) {
                    // a PATH entry that isn't a valid path
                }
            }
        }
        String programFiles = System.getenv("ProgramFiles");
        if (programFiles == null) {
            return null;
        }
        Path mysql = Path.of(programFiles, "MySQL");
        if (!Files.isDirectory(mysql)) {
            return null;
        }
        Path best = null;
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(mysql, "MySQL Server *")) {
            for (Path d : ds) {
                Path exe = d.resolve("bin").resolve(DUMP_EXE);
                if (Files.isRegularFile(exe) && (best == null || d.getFileName().toString()
                        .compareTo(best.getParent().getParent().getFileName().toString()) > 0)) {
                    best = exe;
                }
            }
        } catch (IOException e) {
            log.warn("[Backup] couldn't look in " + mysql + ": " + e.getMessage());
        }
        return best;
    }

    /** Where the game database is, from database.properties. */
    record Db(String host, int port, String name, String user, String password) {

        private static final int DEFAULT_PORT = 3306;

        static Db fromSettings() throws IOException {
            Properties p;
            try {
                p = HikariCPDataSource.loadSettings();
            } catch (IllegalStateException e) {
                throw new IOException(e.getMessage(), e);
            }
            String url = p.getProperty("url", "").trim();
            if (!url.startsWith("jdbc:")) {
                throw new IOException("database.properties url isn't a jdbc: url");
            }
            URI uri = URI.create(url.substring("jdbc:".length()));
            String name = uri.getPath() == null ? "" : uri.getPath().replaceFirst("^/", "");
            if (name.isEmpty() || uri.getHost() == null) {
                throw new IOException("database.properties url has no host or database name");
            }
            return new Db(uri.getHost(), uri.getPort() > 0 ? uri.getPort() : DEFAULT_PORT, name,
                    p.getProperty("username", "").trim(), p.getProperty("password", ""));
        }

        /** A [client] option file, so the password never shows on a command line. */
        String optionFile() {
            return "[client]\nuser=" + quote(user) + "\npassword=" + quote(password) + "\nhost=" + host
                    + "\nport=" + port + "\n";
        }

        private static String quote(String v) {
            String esc = v.replace("\\", "\\\\");
            return v.contains("\"") ? "'" + esc + "'" : "\"" + esc + "\"";
        }
    }
}

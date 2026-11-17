package net.swordie.ms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contributed by Arixal (from GitLab).
 *
 * Copies everything the server prints to logs/console/console-yyyyMMdd-HHmm.log, the files the launcher's Logs page and
 * Server status read (LogTail, AdminRoute.controlStatus). The shipped start scripts (Start Server.bat, the launcher's
 * cog) leave the console in a window, so without this nothing writes that folder. log4j's console appender follows
 * System.out (log4j2.properties), so its lines are copied too. Only the newest KEEP console logs are kept. A start
 * script that copies the console itself sets PLUS_CONSOLE_LOG to its file, and then this does nothing.
 */
public final class ConsoleLog {

    private static final Logger log = LogManager.getLogger(ConsoleLog.class);
    static final int KEEP = 20;
    static final String SCRIPT_LOG_ENV = "PLUS_CONSOLE_LOG";
    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
    // a start script's own console logs (another stamp) are never pruned
    private static final Pattern OWN_LOG = Pattern.compile("console-\\d{8}-\\d{4}\\.log");

    private ConsoleLog() {
    }

    /** Sends System.out and System.err to the console and to a new console log as well; the console alone if it fails. */
    public static void start() {
        if (System.getenv(SCRIPT_LOG_ENV) != null) {
            return;
        }
        try {
            Path file = open(Path.of(ServerConstants.LOG_DIR, "console"), LocalDateTime.now());
            OutputStream copy = new BufferedOutputStream(new FileOutputStream(file.toFile(), true));
            System.setOut(new PrintStream(tee(System.out, copy), true, System.out.charset()));
            System.setErr(new PrintStream(tee(System.err, copy), true, System.err.charset()));
        } catch (IOException | RuntimeException e) {
            log.error("Couldn't open the console log, so the launcher's Logs page stays empty", e);
        }
    }

    /** Creates the folder and this start's console log (appended to on a second start in the same minute). */
    static Path open(Path dir, LocalDateTime now) throws IOException {
        Files.createDirectories(dir);
        Path file = dir.resolve("console-" + now.format(STAMP) + ".log");
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
        prune(dir, KEEP);
        return file;
    }

    /** Deletes all but the newest keep of this class's console logs in dir; other files are left alone. */
    static void prune(Path dir, int keep) throws IOException {
        List<Path> logs;
        try (Stream<Path> s = Files.list(dir)) {
            logs = s.filter(p -> OWN_LOG.matcher(p.getFileName().toString()).matches())
                    .sorted(Comparator.comparingLong((Path p) -> p.toFile().lastModified()).reversed())
                    .collect(Collectors.toList());
        }
        for (Path old : logs.subList(Math.min(keep, logs.size()), logs.size())) {
            Files.deleteIfExists(old);
        }
    }

    /** A stream that writes to both, as one: the server prints from many threads. */
    static OutputStream tee(OutputStream first, OutputStream second) {
        return new OutputStream() {
            @Override
            public synchronized void write(int b) throws IOException {
                first.write(b);
                second.write(b);
            }

            @Override
            public synchronized void write(byte[] b, int off, int len) throws IOException {
                first.write(b, off, len);
                second.write(b, off, len);
            }

            @Override
            public synchronized void flush() throws IOException {
                first.flush();
                second.flush();
            }
        };
    }
}

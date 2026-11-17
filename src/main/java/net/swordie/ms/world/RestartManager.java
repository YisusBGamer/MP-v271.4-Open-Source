package net.swordie.ms.world;

import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.ServerSettings;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Contributed by Arixal (from GitLab).
 *
 * Contributed by Arixal (from GitLab).
 *
 * Contributed by Arixal (from GitLab).
 *
 * Restarts asked for by an admin, never instantly while players are on.
 * <ul>
 * <li>whenEmpty: waits until no human is online, then saves and restarts (cancellable until then).</li>
 * <li>timed: notices to every player at 10, 5 and 1 minutes and 30 seconds left, then saves and restarts.</li>
 * </ul>
 * With build, the server is built first: mvn -q -o package (the jar in bin\) for a server started from a jar, mvn -q -o
 * compile (bin\classes) for one started from its classes. A failed build cancels the restart and the server keeps
 * running.
 * <p>
 * The JVM can't restart itself: it writes control/restart.flag and exits. "Start Server.bat" runs the server in a loop
 * that starts it again (fresh copy of the newest jar in bin\) whenever that flag is there, and sets PLUS_RESTART_LOOP so
 * the server knows something will bring it back; without it a restart is refused. A start script that runs bin\classes
 * can set it too.
 */
public final class RestartManager {

    private static final Logger log = LogManager.getLogger(RestartManager.class);
    private static final Path CONTROL = Path.of(ServerConstants.DIR, "control");
    public static final Path FLAG = CONTROL.resolve("restart.flag");
    private static final Path BUILD_LOG = Path.of(ServerConstants.DIR, "logs", "build", "last.txt");
    private static final int[] NOTICE_SECONDS = {600, 300, 60, 30};
    public static final int MAX_MINUTES = 60;

    private static Pending pending;
    private static final Path LAST = CONTROL.resolve("restart-last.txt");
    private static String lastResult = readLast();
    private static volatile boolean building;

    private static final class Pending {
        String mode;
        String by;
        int minutes;
        boolean build;
        LocalDateTime requested = LocalDateTime.now().withNano(0);
        String stage;
        long fireAt; // timed: epoch ms of the restart
        final List<ScheduledFuture<?>> futures = new ArrayList<>();
    }

    /** Thrown for a request that can't go ahead now (the API answers 409). */
    public static final class Refused extends RuntimeException {
        public Refused(String message) {
            super(message);
        }
    }

    private RestartManager() {
    }

    public static int humansOnline() {
        int n = 0;
        for (Char chr : Server.getInstance().getAllOnlineCharacters()) {
            if (!net.swordie.ms.ext.ServerHooks.isAutomated(chr)) {
                n++;
            }
        }
        return n;
    }

    /** The Maven goal a build runs: a jar start never reads bin\classes, a classes start never reads the jar. */
    static String buildGoal() {
        var source = RestartManager.class.getProtectionDomain().getCodeSource();
        String location = source == null || source.getLocation() == null ? "" : source.getLocation().getPath();
        return location.endsWith(".jar") ? "package" : "compile";
    }

    /** True when something outside the JVM starts the server again after it exits for a restart. */
    public static boolean canRestart() {
        return System.getenv("PLUS_RESTART_LOOP") != null;
    }

    public static synchronized Map<String, Object> request(String mode, int minutes, boolean build, String by) {
        if (!"whenEmpty".equals(mode) && !"timed".equals(mode)) {
            throw new IllegalArgumentException("mode is whenEmpty or timed.");
        }
        if (pending != null) {
            throw new Refused("A restart is already pending (" + pending.mode + ", " + pending.stage + "). Cancel it first.");
        }
        if (building) {
            throw new Refused("A build is running. Wait for it to finish.");
        }
        if (!canRestart()) {
            throw new Refused("This server wasn't started by Start Server.bat, so it wouldn't come back. Restart it from this "
                    + "PC instead.");
        }
        minutes = Math.max(0, Math.min(MAX_MINUTES, minutes));
        if ("timed".equals(mode) && minutes == 0 && humansOnline() > 0) {
            throw new Refused(humansOnline() + " player(s) online: give them at least 1 minute of warning, or restart "
                    + "when they're all offline.");
        }
        Pending p = new Pending();
        p.mode = mode;
        p.minutes = minutes;
        p.build = build;
        p.by = by;
        pending = p;
        if (build) {
            p.stage = "building";
            Thread t = new Thread(() -> {
                String failed = compile();
                synchronized (RestartManager.class) {
                    if (pending != p) {
                        return; // cancelled meanwhile
                    }
                    if (failed != null) {
                        pending = null;
                        lastResult = "Build failed, restart cancelled; the server keeps running. " + failed;
                        log.warn("[Restart] " + lastResult);
                        return;
                    }
                    arm(p);
                }
            }, "restart-build");
            t.setDaemon(true);
            t.start();
        } else {
            arm(p);
        }
        log.info("[Restart] " + by + " asked for a restart: " + describe(p));
        return status();
    }

    public static synchronized boolean cancel(String by) {
        Pending p = pending;
        if (p == null || "restarting".equals(p.stage)) {
            return false;
        }
        for (ScheduledFuture<?> f : p.futures) {
            f.cancel(false);
        }
        pending = null;
        lastResult = "Restart cancelled by " + by + ".";
        if ("countdown".equals(p.stage)) {
            broadcast("[Notice] The server restart was cancelled.");
        }
        log.info("[Restart] " + lastResult);
        return true;
    }

    public static synchronized Map<String, Object> status() {
        Map<String, Object> res = new LinkedHashMap<>();
        Pending p = pending;
        res.put("pending", p != null);
        if (p != null) {
            res.put("mode", p.mode);
            res.put("stage", p.stage);
            res.put("minutes", p.minutes);
            res.put("build", p.build);
            res.put("by", p.by);
            res.put("requestedAt", p.requested.toString());
            res.put("secondsLeft", p.fireAt > 0 ? Math.max(0, (p.fireAt - System.currentTimeMillis()) / 1000) : null);
        }
        res.put("humans", humansOnline());
        res.put("canRestart", canRestart());
        res.put("building", building);
        res.put("lastResult", lastResult);
        return res;
    }

    /** For the public overview: what players may see coming. */
    public static synchronized Map<String, Object> publicStatus() {
        Pending p = pending;
        if (p == null || p.fireAt == 0) {
            return null;
        }
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("secondsLeft", Math.max(0, (p.fireAt - System.currentTimeMillis()) / 1000));
        return res;
    }

    private static String describe(Pending p) {
        return ("timed".equals(p.mode) ? "in " + p.minutes + " min" : "when all players are offline")
                + (p.build ? ", after a build" : "");
    }

    private static void arm(Pending p) {
        if ("whenEmpty".equals(p.mode)) {
            p.stage = "waiting";
            p.futures.add(EventManager.addFixedRateEvent(() -> {
                if (humansOnline() == 0) {
                    fire(p);
                }
            }, 0, 10, TimeUnit.SECONDS));
            return;
        }
        p.stage = "countdown";
        long total = p.minutes * 60L;
        p.fireAt = System.currentTimeMillis() + total * 1000;
        if (total > 0) {
            broadcast("[Notice] The server restarts in " + left(total) + ". Please finish what you're doing; "
                    + "everything is saved first.");
        }
        for (int s : NOTICE_SECONDS) {
            if (s < total) {
                p.futures.add(EventManager.addEvent(() -> broadcast("[Notice] The server restarts in " + left(s)
                        + ". Everything is saved first."), (total - s) * 1000, TimeUnit.MILLISECONDS));
            }
        }
        p.futures.add(EventManager.addEvent(() -> fire(p), total * 1000, TimeUnit.MILLISECONDS));
    }

    private static String left(long seconds) {
        if (seconds < 60) {
            return seconds + " seconds";
        }
        long m = seconds / 60;
        return m + (m == 1 ? " minute" : " minutes");
    }

    private static void fire(Pending p) {
        synchronized (RestartManager.class) {
            if (pending != p || "restarting".equals(p.stage)) {
                return;
            }
            p.stage = "restarting";
            for (ScheduledFuture<?> f : p.futures) {
                f.cancel(false);
            }
        }
        new Thread(() -> restartNow(p), "restart").start();
    }

    private static String readLast() {
        try {
            return Files.isRegularFile(LAST) ? Files.readString(LAST).trim() : "";
        } catch (IOException e) {
            return "";
        }
    }

    private static void restartNow(Pending p) {
        broadcast("[Notice] The server is restarting now. Back in a minute or two.");
        AutoSave.Result r = AutoSave.saveAll(0);
        log.warn("[Restart] saved before the restart: " + r.summary());
        if (r.failed > 0) {
            synchronized (RestartManager.class) {
                pending = null;
                lastResult = "Restart stopped: " + r.failed + " character(s) didn't save; the server keeps running.";
            }
            log.error("[Restart] " + lastResult);
            broadcast("[Notice] The server restart was called off.");
            return;
        }
        try {
            Files.createDirectories(CONTROL);
            Files.writeString(FLAG, "by=" + p.by + "\r\nmode=" + p.mode + "\r\nat=" + LocalDateTime.now().withNano(0)
                    + "\r\n");
            // read back by the next process, since lastResult dies with this JVM
            Files.writeString(LAST, "Restarted at " + LocalDateTime.now().withNano(0) + " (" + describe(p)
                    + ", asked by " + p.by + ").");
        } catch (IOException e) {
            synchronized (RestartManager.class) {
                pending = null;
                lastResult = "Restart stopped: couldn't write " + FLAG + ": " + e.getMessage();
            }
            log.error("[Restart] " + lastResult);
            return;
        }
        Server server = Server.getInstance();
        server.setInShutdown(true);
        log.warn("[Restart] stopping for a restart (" + describe(p) + ", asked by " + p.by + ")");
        for (World world : server.getWorlds()) {
            world.shutdown();
        }
        AutoSave.markShutdownSaved();
        System.exit(0);
    }

    private static void broadcast(String msg) {
        for (World world : Server.getInstance().getWorlds()) {
            world.broadcastPacket(UserLocal.chatMsg(ChatType.Notice2, msg));
        }
        log.warn(msg);
    }

    /** Builds for the next start (see {@link #buildGoal()}); the output goes to logs/build/last.txt. */
    public static synchronized Map<String, Object> buildOnly(String by) {
        if (building) {
            throw new Refused("A build is running already.");
        }
        Thread t = new Thread(() -> {
            String failed = compile();
            synchronized (RestartManager.class) {
                lastResult = failed == null ? "Build OK (asked by " + by + "): used at the next restart."
                        : "Build failed. " + failed;
            }
            log.info("[Restart] " + lastResult);
        }, "build");
        t.setDaemon(true);
        t.start();
        return status();
    }

    /** @return null when the build is OK, else a short reason */
    private static String compile() {
        building = true;
        try {
            Files.createDirectories(BUILD_LOG.getParent());
            String mvn = ServerSettings.getString("control.mvn", "mvn");
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", mvn, "-q", "-o", buildGoal())
                    .directory(Path.of(ServerConstants.DIR).toFile()).redirectErrorStream(true);
            long start = System.currentTimeMillis();
            Process proc = pb.start();
            byte[] out = proc.getInputStream().readAllBytes();
            boolean done = proc.waitFor(10, TimeUnit.MINUTES);
            if (!done) {
                proc.destroyForcibly();
            }
            int code = done ? proc.exitValue() : -1;
            String text = new String(out, StandardCharsets.UTF_8);
            boolean ok = code == 0;
            Files.writeString(BUILD_LOG, (ok ? "BUILD OK" : "BUILD FAILED (exit " + code + ")") + " "
                    + LocalDateTime.now().withNano(0) + " in " + (System.currentTimeMillis() - start) / 1000 + " s\r\n"
                    + text, StandardCharsets.UTF_8);
            if (ok) {
                return null;
            }
            String[] lines = text.strip().split("\r?\n");
            return lines.length == 0 || lines[0].isEmpty() ? "mvn exit " + code : lines[Math.max(0, lines.length - 3)];
        } catch (Exception e) {
            return "couldn't run the build: " + e.getMessage();
        } finally {
            building = false;
        }
    }
}

package net.swordie.ms.util;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.handlers.executors.EventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Timing of server work, by kind:
 * <ul>
 * <li>"packet": each packet handler, by packet name;</li>
 * <li>"timer": each server timer task (Server#safely);</li>
 * <li>"field": a single map inside a timer task, when it alone was slow (Channel#forEachSafely);</li>
 * <li>"db": each database statement, by its first words (never the values).</li>
 * </ul>
 * Anything over its threshold is logged as "[Perf] slow ..." (at most once a minute per name). Every
 * {@link #REPORT_MINUTES} minutes the busiest names of each kind go to the server log and logs/perf.log, then the
 * counters reset. Toggled by {@link ServerConstants#PERF_LOG}.
 */
public final class PerfLog {

    private static final Logger log = LogManager.getLogger(PerfLog.class);

    public static final int REPORT_MINUTES = 10;
    private static final int TOP = 8;
    private static final Map<String, Long> SLOW_MS = Map.of("packet", 50L, "timer", 250L, "field", 100L, "db", 300L);

    private static final class Stat {
        final LongAdder count = new LongAdder();
        final LongAdder total = new LongAdder();
        final AtomicLong max = new AtomicLong();
    }

    private static final Map<String, Map<String, Stat>> stats = new ConcurrentHashMap<>();
    private static final Map<String, Long> lastSlowWarning = new ConcurrentHashMap<>();
    private static volatile boolean started = false;

    private PerfLog() {
    }

    /** @return System.nanoTime() when on, 0 when off (cheap to call everywhere) */
    public static long start() {
        return ServerConstants.PERF_LOG ? System.nanoTime() : 0;
    }

    /** Records one run of something that began at {@link #start()} (does nothing when that was 0). */
    public static void end(String kind, String name, long start) {
        end(kind, name, start, null);
    }

    /** As {@link #end(String, String, long)}; detail is added to the slow warning only (e.g. a map id). */
    public static void end(String kind, String name, long start, String detail) {
        if (start == 0) {
            return;
        }
        try {
            long nanos = System.nanoTime() - start;
            if (!started) {
                startReports();
            }
            Stat s = stats.computeIfAbsent(kind, k -> new ConcurrentHashMap<>()).computeIfAbsent(name, n -> new Stat());
            s.count.increment();
            s.total.add(nanos);
            s.max.accumulateAndGet(nanos, Math::max);
            long ms = nanos / 1_000_000;
            long slow = SLOW_MS.getOrDefault(kind, 200L);
            // db statements are not warned one by one (a character creation alone runs a dozen slow statements at
            // once); the report below still lists them, by total time
            if (ms >= slow && !"db".equals(kind)) {
                String key = kind + ":" + name;
                long now = System.currentTimeMillis();
                Long last = lastSlowWarning.get(key);
                if (last == null || now - last > 60_000) {
                    lastSlowWarning.put(key, now);
                    log.warn(String.format("[Perf] slow %s %s: %d ms%s", kind, name, ms,
                            detail == null ? "" : " (" + detail + ")"));
                }
            }
        } catch (Exception ignored) {
            // timing must never break what it times
        }
    }

    /** A short, value-free label for a SQL statement: its first words. */
    public static String sqlLabel(String sql) {
        if (sql == null) {
            return "?";
        }
        String s = sql.replaceAll("\\s+", " ").trim();
        return s.length() > 60 ? s.substring(0, 60) : s;
    }

    private static synchronized void startReports() {
        if (started) {
            return;
        }
        started = true;
        EventManager.addFixedRateEvent(PerfLog::report, REPORT_MINUTES, REPORT_MINUTES, TimeUnit.MINUTES);
    }

    private static void report() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("===== ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .append(" - the last ").append(REPORT_MINUTES).append(" minutes =====\r\n");
            for (String kind : new TreeSet<>(stats.keySet())) {
                Map<String, Stat> byName = stats.remove(kind);
                if (byName == null || byName.isEmpty()) {
                    continue;
                }
                List<Map.Entry<String, Stat>> list = new ArrayList<>(byName.entrySet());
                list.sort((a, b) -> Long.compare(b.getValue().total.sum(), a.getValue().total.sum()));
                long all = list.stream().mapToLong(e -> e.getValue().total.sum()).sum();
                //sb.append(String.format("%s: %,d ms in total%n", kind, all / 1_000_000));
                for (var e : list.subList(0, Math.min(TOP, list.size()))) {
                    Stat s = e.getValue();
                    long n = Math.max(1, s.count.sum());
                    //sb.append(String.format("  %-40s %,8d ms  %,8d times  avg %6.2f ms  max %,6d ms%n", e.getKey(),
                     //       s.total.sum() / 1_000_000, s.count.sum(), s.total.sum() / 1e6 / n, s.max.get() / 1_000_000));
                }
            }
            String text = sb.toString();
            //log.info("[Perf] summary\r\n" + text);
            try (var w = new FileWriter(ServerConstants.LOG_DIR + "/perf.log", true)) {
                w.write(text);
                w.write("\r\n");
            }
        } catch (IOException e) {
            log.warn("[Perf] could not write logs/perf.log: " + e.getMessage());
        } catch (Exception e) {
            log.error("[Perf] report failed", e);
        }
    }
}

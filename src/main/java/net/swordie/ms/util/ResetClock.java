package net.swordie.ms.util;

import net.swordie.ms.constants.ServerFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Properties;

/**
 * The server's day. Every daily, weekly and monthly reset and every day or week key turns at midnight in one time
 * zone: the reset_time_zone setting (read at start). The client's clock runs in the same zone (FileTime), so the day
 * the client shows turns with the server's resets.
 * <p>
 * When the zone changes, the day never goes back and never repeats. Times before the change keep the old zone's date;
 * from the change until the first midnight in the new zone at which the new zone's date has caught up, the day is the
 * later of the two dates. Moving west (to a later midnight) the current day runs on until midnight in the new zone;
 * moving east the next day starts at the restart. The change is remembered in control/reset-clock.txt.
 * <p>
 * Static initialization stays trivial: nothing here can throw while the class loads.
 */
public final class ResetClock {
    private static final Logger log = LogManager.getLogger();

    public static final String DEFAULT_ZONE_ID = "America/Los_Angeles";
    public static final ZoneId DEFAULT_ZONE = ZoneId.of(DEFAULT_ZONE_ID);
    private static final Path STATE_FILE = Path.of("control", "reset-clock.txt");
    private static final DateTimeFormatter YYMMDD = DateTimeFormatter.ofPattern("yy/MM/dd");

    /** zone in effect; previous zone and change time while a change is remembered (previous null otherwise) */
    private record State(ZoneId zone, ZoneId previous, long changedAt, long transitionEnd) {
    }

    private static volatile State state = new State(DEFAULT_ZONE, null, 0, 0);
    /** the time source: the system clock, or a fixed one in tools/ResetSim */
    private static volatile java.time.Clock clock = java.time.Clock.systemUTC();

    /** Offline simulation only (net.swordie.ms.tools.ResetSim): a zone and a fake clock, no state file. */
    public static void useForTest(ZoneId zone, java.time.Clock fake) {
        state = new State(zone, null, 0, 0);
        clock = fake;
    }

    /** @return now in milliseconds by the reset clock's time source */
    public static long millis() {
        return clock.millis();
    }

    private ResetClock() {
    }

    // ------------------------------------------------------------------------------------------------- reading

    /** @return the zone resets turn in (also the client's clock zone) */
    public static ZoneId zone() {
        return state.zone;
    }

    /** @return now as wall time in the reset zone, for hours and weekdays of timed windows */
    public static ZonedDateTime now() {
        return ZonedDateTime.now(clock.withZone(state.zone));
    }

    /** @return the wall time of an instant in the reset zone */
    public static LocalDateTime wallTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), state.zone);
    }

    /** @return the current reset day */
    public static LocalDate today() {
        return dayOf(clock.millis());
    }

    /** @return today as yy/MM/dd, the form quest records keep their dates in */
    public static String todayYYMMDD() {
        return today().format(YYMMDD);
    }

    /** @return today in the given pattern (DateTimeFormatter) */
    public static String today(String pattern) {
        return today().format(DateTimeFormatter.ofPattern(pattern));
    }

    /** @return the reset day an instant belongs to */
    public static LocalDate dayOf(long millis) {
        State s = state;
        Instant t = Instant.ofEpochMilli(millis);
        LocalDate d = LocalDate.ofInstant(t, s.zone);
        if (s.previous == null || millis >= s.transitionEnd) {
            return d;
        }
        LocalDate p = LocalDate.ofInstant(t, s.previous);
        if (millis < s.changedAt) {
            return p;
        }
        return p.isAfter(d) ? p : d;
    }

    /** @return the reset day of a FileTime (null for null) */
    public static LocalDate dayOf(FileTime ft) {
        return ft == null ? null : dayOf(ft.toMillis());
    }

    public static boolean isToday(long millis) {
        return dayOf(millis).equals(today());
    }

    /** @return the first day of the current week, for weeks that start on the given weekday */
    public static LocalDate weekStart(DayOfWeek first) {
        return weekStart(today(), first);
    }

    public static LocalDate weekStart(LocalDate day, DayOfWeek first) {
        return day.with(TemporalAdjusters.previousOrSame(first));
    }

    /** @return the instant a reset day begins (its reset) */
    public static long startOfDay(LocalDate day) {
        State s = state;
        long n = day.atStartOfDay(s.zone).toInstant().toEpochMilli();
        if (s.previous == null) {
            return n;
        }
        // the day changes only at a midnight of either zone, at the change or at the end of the transition
        long best = Long.MAX_VALUE;
        long[] candidates = {day.atStartOfDay(s.previous).toInstant().toEpochMilli(), n, s.changedAt, s.transitionEnd};
        for (long c : candidates) {
            if (c < best && !dayOf(c).isBefore(day)) {
                best = c;
            }
        }
        return best == Long.MAX_VALUE ? n : best;
    }

    /** @return the next daily reset after now */
    public static long nextDailyReset() {
        return startOfDay(today().plusDays(1));
    }

    /** @return the next weekly reset after now, for weeks that start on the given weekday */
    public static long nextWeeklyReset(DayOfWeek first) {
        return startOfDay(today().with(TemporalAdjusters.next(first)));
    }

    /** @return the next monthly reset after now (the 1st) */
    public static long nextMonthlyReset() {
        return startOfDay(today().withDayOfMonth(1).plusMonths(1));
    }

    /**
     * @return the next time the reset zone's UTC offset changes (daylight saving), or 0 if it never does. The
     * client's clock is the zone's wall time, so it has to be sent again at that moment.
     */
    public static long nextOffsetChange() {
        var t = zone().getRules().nextTransition(clock.instant());
        return t == null ? 0 : t.getInstant().toEpochMilli();
    }

    // ------------------------------------------------------------------------------------------------- loading

    /** @return the zone a setting text names, or null if it names none */
    public static ZoneId parseZone(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        String t = text.trim();
        try {
            return ZoneId.of(t);
        } catch (Exception ignored) {
        }
        for (String id : ZoneId.getAvailableZoneIds()) {
            if (id.equalsIgnoreCase(t)) {
                return ZoneId.of(id);
            }
        }
        return null;
    }

    private static ZoneId configuredZone() {
        Object v = ServerFeatures.get(ServerFeatures.Feature.RESET_TIME_ZONE);
        ZoneId z = v instanceof String s ? parseZone(s) : null;
        return z != null ? z : DEFAULT_ZONE;
    }

    /** First midnight in the new zone after the change at which the new zone's date is not behind the old one's. */
    private static long transitionEnd(ZoneId previous, ZoneId zone, long changedAt) {
        LocalDate d = LocalDate.ofInstant(Instant.ofEpochMilli(changedAt), zone).plusDays(1);
        for (int i = 0; i < 3; i++, d = d.plusDays(1)) {
            long m = d.atStartOfDay(zone).toInstant().toEpochMilli();
            if (!d.isBefore(LocalDate.ofInstant(Instant.ofEpochMilli(m), previous))) {
                return m;
            }
        }
        return d.atStartOfDay(zone).toInstant().toEpochMilli();
    }

    /**
     * Puts the configured zone in effect, once while the server starts (after ServerFeatures.load()). A zone that
     * differs from the one the last start used starts a transition (see the class comment). Never throws.
     */
    public static synchronized void init() {
        try {
            ZoneId zone = configuredZone();
            long now = System.currentTimeMillis();
            Properties p = readState();
            ZoneId stored = p == null ? null : parseZone(p.getProperty("zone"));
            ZoneId previous = null;
            long changedAt = 0;
            boolean write = true;
            if (stored != null && !stored.equals(zone)) {
                previous = stored;
                changedAt = now;
            } else if (stored != null) {
                previous = parseZone(p.getProperty("previous"));
                changedAt = parseLong(p.getProperty("changedAt"));
                if (previous == null || changedAt <= 0) {
                    previous = null;
                    changedAt = 0;
                }
                write = false;
            }
            long end = previous == null ? 0 : transitionEnd(previous, zone, changedAt);
            state = new State(zone, previous, changedAt, end);
            if (write) {
                writeState(state);
            }
            if (previous != null && now < end) {
                log.info("[Resets] Daily resets at midnight " + zone.getId() + " (changed from " + previous.getId()
                        + "; today stays " + today() + " until " + wallTime(end) + ").");
            } else {
                log.info("[Resets] Daily resets at midnight " + zone.getId() + "; next at " + wallTime(nextDailyReset()) + ".");
            }
        } catch (Exception e) {
            log.warn("[Resets] Could not set the reset time zone (" + e + "); using " + state.zone.getId() + ".");
        }
    }

    private static long parseLong(String s) {
        try {
            return s == null ? 0 : Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static Properties readState() {
        if (!Files.isRegularFile(STATE_FILE)) {
            return null;
        }
        try (Reader r = Files.newBufferedReader(STATE_FILE, StandardCharsets.UTF_8)) {
            Properties p = new Properties();
            p.load(r);
            return p;
        } catch (Exception e) {
            log.warn("[Resets] Could not read " + STATE_FILE + ": " + e.getMessage());
            return null;
        }
    }

    private static void writeState(State s) {
        try {
            Properties p = new Properties();
            p.setProperty("zone", s.zone.getId());
            if (s.previous != null) {
                p.setProperty("previous", s.previous.getId());
                p.setProperty("changedAt", String.valueOf(s.changedAt));
            }
            Path dir = STATE_FILE.toAbsolutePath().getParent();
            Files.createDirectories(dir);
            Path tmp = Files.createTempFile(dir, "reset-clock", ".tmp");
            try (Writer w = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
                p.store(w, "Reset time zone the last start used (written by the server)");
            }
            Files.move(tmp, STATE_FILE, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception e) {
            log.warn("[Resets] Could not write " + STATE_FILE + ": " + e.getMessage());
        }
    }
}

package net.swordie.ms.world;

import net.swordie.orm.connection.HikariCPDataSource;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ServerRates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Buffed channels. On a buffed channel ordinary monsters (not bosses) spawn with more HP and give more EXP, and look a
 * little bigger.
 * <ul>
 *     <li>{@code buffed} (server_rates table): 0 off, 1 always on, 2 weekends only (Friday 00:00
 *     to Sunday 23:59, Pacific time, like Hot Time).</li>
 *     <li>{@code buffed_channels}: how many channels, counting down from the last one (1 = the last channel).</li>
 *     <li>{@code buffed_hp}, {@code buffed_exp}: the monster HP and EXP multipliers.</li>
 * </ul>
 * Unlike the other rates these are read again every minute, so a change in server_rates takes effect within a
 * minute (for monsters that spawn after it).
 */
public final class BuffedChannels {

    private static final Logger log = LogManager.getLogger();
    private static final long REFRESH_MS = 60_000L;

    public static final int OFF = 0, ALWAYS = 1, WEEKENDS = 2;

    private static volatile long readAt;
    private static volatile int mode = (int) ServerRates.Rate.BUFFED.getDefaultValue();
    private static volatile int count = (int) ServerRates.Rate.BUFFED_CHANNELS.getDefaultValue();
    private static volatile double hpMultiplier = ServerRates.Rate.BUFFED_HP.getDefaultValue();
    private static volatile double expMultiplier = ServerRates.Rate.BUFFED_EXP.getDefaultValue();
    private static volatile boolean warned;

    private BuffedChannels() {
    }

    /** Reads the settings from the rates table (at most once a minute; the last values are kept on errors). */
    private static void refresh() {
        long now = System.currentTimeMillis();
        if (now - readAt < REFRESH_MS) {
            return;
        }
        synchronized (BuffedChannels.class) {
            if (now - readAt < REFRESH_MS) {
                return;
            }
            readAt = now;
            try (Connection con = HikariCPDataSource.getConnection()) {
                Map<ServerRates.Rate, Double> saved = ServerRates.readSaved(con);
                con.rollback();
                mode = (int) value(saved, ServerRates.Rate.BUFFED);
                count = (int) value(saved, ServerRates.Rate.BUFFED_CHANNELS);
                hpMultiplier = value(saved, ServerRates.Rate.BUFFED_HP);
                expMultiplier = value(saved, ServerRates.Rate.BUFFED_EXP);
                warned = false;
            } catch (Exception e) {
                if (!warned) {
                    warned = true;
                    log.warn("Could not read the buffed channel settings, keeping the last ones: " + e);
                }
            }
        }
    }

    private static double value(Map<ServerRates.Rate, Double> saved, ServerRates.Rate rate) {
        Double v = saved.get(rate);
        double res = v == null ? rate.getDefaultValue() : v;
        ServerRates.putActive(rate, res); // reported as in effect: they are
        return res;
    }

    /**
     * The channels a world really has (the server runs fewer than GameConstants.CHANNELS_PER_WORLD), so "the last
     * channel" is one that exists.
     */
    public static int channelCount() {
        try {
            for (World w : net.swordie.ms.Server.getInstance().getWorlds()) {
                if (w.getChannels() != null && !w.getChannels().isEmpty()) {
                    return w.getChannels().size();
                }
            }
        } catch (Exception ignored) {
            // the server is still starting
        }
        return GameConstants.CHANNELS_PER_WORLD;
    }

    public static int getMode() {
        refresh();
        return mode;
    }

    private static ZonedDateTime now() {
        return net.swordie.ms.util.ResetClock.now();
    }

    private static boolean isWeekend(ZonedDateTime t) {
        DayOfWeek d = t.getDayOfWeek();
        return d == DayOfWeek.FRIDAY || d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }

    /** @return whether channels are buffed right now (on, or the weekend with the weekend schedule) */
    public static boolean isActive() {
        int m = getMode();
        return m == ALWAYS || (m == WEEKENDS && isWeekend(now()));
    }

    /** @return the number of channels that are buffed when buffed channels are on (from the last channel down) */
    public static int getCount() {
        refresh();
        return Math.max(1, Math.min(channelCount(), count));
    }

    /** @return whether monsters of this channel spawn buffed right now */
    public static boolean isBuffed(int channelId) {
        return isActive() && channelId > channelCount() - getCount();
    }

    /** @return the channels that are buffed when buffed channels are on, lowest first */
    public static List<Integer> getChannelIds() {
        List<Integer> res = new ArrayList<>();
        for (int ch = channelCount() - getCount() + 1; ch <= channelCount(); ch++) {
            res.add(ch);
        }
        return res;
    }

    public static double getHpMultiplier() {
        refresh();
        return Math.max(1, hpMultiplier);
    }

    public static double getExpMultiplier() {
        refresh();
        return Math.max(1, expMultiplier);
    }

    /** @return e.g. "Channel 3", "Channels 2 and 3", "Every channel" */
    public static String channelsText() {
        List<Integer> ids = getChannelIds();
        if (ids.size() >= channelCount()) {
            return "Every channel";
        }
        if (ids.size() == 1) {
            return "Channel " + ids.get(0);
        }
        StringBuilder sb = new StringBuilder("Channels ");
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) {
                sb.append(i == ids.size() - 1 ? " and " : ", ");
            }
            sb.append(ids.get(i));
        }
        return sb.toString();
    }

    private static String times(double v) {
        return (v == Math.rint(v) ? String.valueOf((long) v) : String.valueOf(v)) + "x";
    }

    /** @return e.g. "2x monster HP and 2x EXP" */
    public static String effectText() {
        double hp = getHpMultiplier(), exp = getExpMultiplier();
        if (hp == exp) {
            return times(hp) + " monster HP and EXP";
        }
        return times(hp) + " monster HP and " + times(exp) + " EXP";
    }

    /** @return when the current buffed weekend ends (Monday 00:00 Pacific), or null when it isn't a buffed weekend */
    public static ZonedDateTime getEnd() {
        if (getMode() != WEEKENDS || !isWeekend(now())) {
            return null;
        }
        LocalDate monday = now().toLocalDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return monday.atStartOfDay(net.swordie.ms.util.ResetClock.zone());
    }

    /** @return when the next buffed weekend starts (Friday 00:00 Pacific), or null when not on the weekend schedule */
    public static ZonedDateTime getNextStart() {
        if (getMode() != WEEKENDS || isWeekend(now())) {
            return null;
        }
        LocalDate friday = now().toLocalDate().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        return friday.atStartOfDay(net.swordie.ms.util.ResetClock.zone());
    }

    /**
     * @return a line for the world select screen's world balloon (the client shows the world's event text there),
     * or "" when no channel is buffed now
     */
    public static String worldNotice() {
        int m = getMode();
        if (m == OFF) {
            return "";
        }
        String ch = getCount() >= channelCount() ? ""
                : getCount() == 1 ? "ch. " + getChannelIds().get(0) : "ch. " + getChannelIds().get(0) + "-"
                + channelCount();
        if (isActive()) {
            return (m == WEEKENDS ? "Weekend buff on now: " : "Buff on: ") + effectText() + ", " + ch;
        }
        // on weekdays the balloon says what the weekend brings
        return "Weekends (Fri-Sun): " + effectText() + ", " + ch;
    }
}

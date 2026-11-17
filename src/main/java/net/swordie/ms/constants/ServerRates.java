package net.swordie.ms.constants;

import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Server rates, read from the server_rates table at startup. New values saved there
 * take effect at the next restart. A rate missing from the table (or a database error) uses its default.
 */
public final class ServerRates {
    private static final Logger log = LogManager.getLogger();

    public static final String TABLE = "server_rates";

    public enum Rate {
        EXP("exp", "EXP rate", "Monster EXP multiplier from level 10 on.", 100, 1, 10_000, false),
        EXP_LOW("exp_low", "EXP rate (levels 1-9)", "Monster EXP multiplier below level 10 (beginner area).", 1, 1, 10_000, false),
        DROP("drop", "Drop rate", "Item drop chance multiplier.", 20, 1, 1_000, false),
        MESO("meso", "Meso rate", "Meso drop amount multiplier.", 2000, 1, 10_000, false),
        NX("nx", "NX rate", "Multiplier of the NX Prepaid that monsters give (0 turns it off). A kill gives at most 143,800 NX whatever the rate.", 1, 0, 100, true),
        SPELL_TRACE("spell_trace", "Spell Trace rate", "Multiplier of the Spell Traces a drop gives, bosses included (1 = the normal amounts). How often they drop follows the drop rate.", 1, 0.1, 100, true),
        SOL_ERDA("sol_erda", "Sol Erda energy rate", "Multiplier of the Sol Erda energy from Grandis dailies and of how often Lv. 260+ Grandis monsters drop Faint Sol Erda Energy (0 turns both off). The drop also follows the drop rate.", 1, 0, 100, true),
        // buffed channels (BuffedChannels). These four take effect within a minute, not at the next start.
        BUFFED("buffed", "Buffed channels", "0 off, 1 always on, 2 weekends only (Friday to Sunday, Pacific time). Takes effect within a minute.", 2, 0, 2, false),
        BUFFED_CHANNELS("buffed_channels", "Buffed channel count", "How many channels are buffed, counting down from the last one (1 = only the last channel; the number of channels or more = every channel). Takes effect within a minute.", 2, 1, 30, false),
        BUFFED_HP("buffed_hp", "Buffed monster HP", "Monster HP multiplier on buffed channels (bosses are never buffed). Takes effect within a minute.", 2, 1, 20, true),
        BUFFED_EXP("buffed_exp", "Buffed monster EXP", "Monster EXP multiplier on buffed channels, on top of the EXP rate. Takes effect within a minute.", 2, 1, 20, true),
        ;

        private final String key;
        private final String label;
        private final String description;
        private final double defaultValue;
        private final double min;
        private final double max;
        private final boolean decimal;

        Rate(String key, String label, String description, double defaultValue, double min, double max, boolean decimal) {
            this.key = key;
            this.label = label;
            this.description = description;
            this.defaultValue = defaultValue;
            this.min = min;
            this.max = max;
            this.decimal = decimal;
        }

        public String getKey() {
            return key;
        }

        public String getLabel() {
            return label;
        }

        public String getDescription() {
            return description;
        }

        public double getDefaultValue() {
            return defaultValue;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        public boolean isDecimal() {
            return decimal;
        }

        public static Rate getByKey(String key) {
            for (Rate r : values()) {
                if (r.key.equalsIgnoreCase(key)) {
                    return r;
                }
            }
            return null;
        }

        /**
         * @return the value made valid for this rate (within min..max, whole numbers for whole-number rates), or
         * NaN if it isn't a number
         */
        public double clean(double value) {
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                return Double.NaN;
            }
            if (!decimal) {
                value = Math.round(value);
            }
            return Math.max(min, Math.min(max, value));
        }
    }

    /** The rates in effect (read when the server started). */
    private static final Map<Rate, Double> active = new EnumMap<>(Rate.class);

    static {
        for (Rate r : Rate.values()) {
            active.put(r, r.getDefaultValue());
        }
    }

    private ServerRates() {
    }

    /**
     * Reads the rates from the database. Called once while the server starts (after the migrations ran).
     */
    public static synchronized void load() {
        Map<Rate, Double> saved;
        try (Connection con = HikariCPDataSource.getConnection()) {
            saved = readSaved(con);
            con.rollback();
        } catch (SQLException e) {
            log.error("Could not read the server rates from the " + TABLE + " table, using the defaults.", e);
            return;
        }
        for (Rate r : Rate.values()) {
            Double v = saved.get(r);
            active.put(r, v == null ? r.getDefaultValue() : v);
        }
    }

    /**
     * @return the rates saved in the database (the ones the next start uses); rates that aren't saved are missing
     */
    public static Map<Rate, Double> readSaved(Connection con) throws SQLException {
        Map<Rate, Double> saved = new EnumMap<>(Rate.class);
        try (PreparedStatement ps = con.prepareStatement("SELECT name, value FROM " + TABLE);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Rate r = Rate.getByKey(rs.getString(1));
                if (r != null) {
                    double v = r.clean(rs.getDouble(2));
                    if (!Double.isNaN(v)) {
                        saved.put(r, v);
                    }
                }
            }
        }
        return saved;
    }

    /**
     * Saves rates for the next start (doesn't change the rates in effect). The caller commits.
     */
    public static void save(Connection con, Map<Rate, Double> values) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO " + TABLE + " (name, value) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE value = VALUES(value)")) {
            for (Map.Entry<Rate, Double> e : values.entrySet()) {
                ps.setString(1, e.getKey().getKey());
                ps.setDouble(2, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    /** A rate that changes at runtime (buffed channel settings) also updates its in-effect value. */
    public static synchronized void putActive(Rate rate, double value) {
        active.put(rate, value);
    }

    public static synchronized double get(Rate rate) {
        return active.get(rate);
    }

    /** Monster EXP multiplier from level 10 on. */
    public static int getExpRate() {
        return (int) get(Rate.EXP);
    }

    /** Monster EXP multiplier below level 10. */
    public static int getLowLevelExpRate() {
        return (int) get(Rate.EXP_LOW);
    }

    public static int getDropRate() {
        return (int) get(Rate.DROP);
    }

    public static int getMesoRate() {
        return (int) get(Rate.MESO);
    }

    public static double getNxRate() {
        return get(Rate.NX);
    }

    public static double getSpellTraceRate() {
        return get(Rate.SPELL_TRACE);
    }

    public static double getSolErdaRate() {
        return get(Rate.SOL_ERDA);
    }
}

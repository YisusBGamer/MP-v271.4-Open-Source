package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Content tables kept out of the Java code: resources/data/&lt;name&gt;.tsv, tab-separated, one header row naming the
 * columns, # starts a comment line; cells are taken as written (a note column is just another column). Read on first use and again after
 * {@link #clear()} (Server.clearCache, the admin "clear cache" action).
 */
public final class DataTable {
    private static final Logger log = LogManager.getLogger(DataTable.class);
    public static final Path DIR = Path.of(ServerConstants.RESOURCES_DIR, "data");
    private static final List<Cached<?>> CACHES = new CopyOnWriteArrayList<>();

    private final String name;
    private final Map<String, Integer> columns;
    private final List<Row> rows;

    private DataTable(String name, Map<String, Integer> columns, List<Row> rows) {
        this.name = name;
        this.columns = columns;
        this.rows = rows;
    }

    /** one row; cells are read by column name */
    public final class Row {
        private final String[] cells;
        private final int line;

        private Row(String[] cells, int line) {
            this.cells = cells;
            this.line = line;
        }

        public String str(String column) {
            Integer i = columns.get(column);
            if (i == null) {
                throw new IllegalArgumentException(name + ".tsv has no column " + column);
            }
            return i < cells.length ? cells[i] : "";
        }

        public boolean has(String column) {
            return !str(column).isBlank();
        }

        public int i(String column) {
            return Integer.parseInt(str(column).trim());
        }

        /** the int, or def when the cell is empty */
        public int i(String column, int def) {
            return has(column) ? i(column) : def;
        }

        public long l(String column) {
            return Long.parseLong(str(column).trim());
        }

        public double d(String column) {
            return Double.parseDouble(str(column).trim());
        }

        public boolean b(String column) {
            String s = str(column).trim();
            return s.equals("1") || s.equalsIgnoreCase("true") || s.equalsIgnoreCase("y");
        }

        /** a comma-separated list of ints (empty cell: none) */
        public int[] ints(String column) {
            String s = str(column).trim();
            if (s.isEmpty()) {
                return new int[0];
            }
            return Arrays.stream(s.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        }

        public int line() {
            return line;
        }
    }

    public String name() {
        return name;
    }

    public List<Row> rows() {
        return rows;
    }

    /** Reads resources/data/&lt;name&gt;.tsv now (no cache); a missing or broken file throws. */
    public static DataTable read(String name) {
        Path file = DIR.resolve(name + ".tsv");
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            Map<String, Integer> cols = null;
            List<Row> rows = new ArrayList<>();
            DataTable t = null;
            for (int n = 0; n < lines.size(); n++) {
                String line = lines.get(n);
                if (line.isBlank() || line.stripLeading().startsWith("#")) {
                    continue;
                }
                String[] cells = line.split("\t", -1);
                if (cols == null) {
                    cols = new LinkedHashMap<>();
                    for (int i = 0; i < cells.length; i++) {
                        cols.put(cells[i].trim(), i);
                    }
                    t = new DataTable(name, Collections.unmodifiableMap(cols), rows);
                    continue;
                }
                rows.add(t.new Row(cells, n + 1));
            }
            if (t == null) {
                throw new IllegalStateException(file + " has no header row");
            }
            return t;
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Could not read " + file, e);
        }
    }

    /**
     * A value built from one table, rebuilt on first use after {@link #clear()}. When the file can't be read the last
     * good value stays (or the build of an empty table on the first read).
     */
    public static <T> Supplier<T> cached(String name, Function<DataTable, T> build) {
        Cached<T> c = new Cached<>(name, build);
        CACHES.add(c);
        return c;
    }

    /** Every cached table is read again on its next use. */
    public static void clear() {
        for (Cached<?> c : CACHES) {
            c.stale = true;
        }
    }

    private static final class Cached<T> implements Supplier<T> {
        private final String name;
        private final Function<DataTable, T> build;
        private volatile T value;
        private volatile boolean stale = true;

        private Cached(String name, Function<DataTable, T> build) {
            this.name = name;
            this.build = build;
        }

        @Override
        public T get() {
            if (stale) {
                synchronized (this) {
                    if (stale) {
                        try {
                            value = build.apply(read(name));
                        } catch (RuntimeException e) {
                            log.error("Could not load data table " + name + (value != null ? "; the old one stays" : ""), e);
                            if (value == null) {
                                value = build.apply(new DataTable(name, Map.of(), List.of()));
                            }
                        }
                        stale = false;
                    }
                }
            }
            return value;
        }
    }
}

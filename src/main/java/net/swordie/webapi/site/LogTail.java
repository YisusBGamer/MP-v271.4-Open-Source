package net.swordie.webapi.site;

import net.swordie.ms.ServerConstants;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** The end of the server's log files for the admin API, with secrets taken out. */
public final class LogTail {

    private static final long MAX_BYTES = 8L << 20;
    private static final long MAX_BYTES_FILTERED = 48L << 20;

    private static final Pattern WEBHOOK = Pattern.compile("https?://(?:\\w+\\.)?discord(?:app)?\\.com/api/webhooks/\\S+",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern JDBC_CREDS = Pattern.compile("(jdbc:[a-z]+://)[^/\\s:@]+:[^/\\s@]+@",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern KEY_VALUE = Pattern.compile(
            "((?:password|passwd|pwd|token|secret|api[_-]?key|authorization|webhook)\\w*[\"']?\\s*[:=]\\s*[\"']?)"
                    + "(?:Bearer\\s+|Bot\\s+)?[^\\s\"',;&}]+", Pattern.CASE_INSENSITIVE);
    private static final Pattern BOT_TOKEN = Pattern.compile("\\b[MNO][A-Za-z\\d_-]{23,27}\\.[\\w-]{6}\\.[\\w-]{27,}\\b");
    private static final Pattern SECRET_KEY = Pattern.compile("(?i)pass|token|secret|webhook|apikey|api_key");
    /** The database login: its user name and URL are kept out of logs too. */
    private static final Pattern DB_KEY = Pattern.compile("(?i)^(user|username|url)$");

    private static List<String> literals = List.of();
    private static long literalsAt;

    private LogTail() {
    }

    public static Path newestConsoleLog() throws IOException {
        Path dir = Path.of(ServerConstants.DIR, "logs", "console");
        if (!Files.isDirectory(dir)) {
            return null;
        }
        try (var s = Files.list(dir)) {
            return s.filter(p -> p.getFileName().toString().startsWith("console-"))
                    .max(Comparator.comparingLong(p -> p.toFile().lastModified())).orElse(null);
        }
    }

    public static Path buildLog() {
        return Path.of(ServerConstants.DIR, "logs", "build", "last.txt");
    }

    /**
     * @param filter null/empty: every line; "errors", "warnings": those levels; anything else: lines containing it
     *               (ignoring case)
     * @return {file, lines, truncated}
     */
    public static Map<String, Object> tail(Path file, int lines, String filter) throws IOException {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("file", file == null ? null : file.getFileName().toString());
        if (file == null || !Files.isRegularFile(file)) {
            res.put("lines", List.of());
            res.put("truncated", false);
            return res;
        }
        boolean filtered = filter != null && !filter.isBlank();
        long max = filtered ? MAX_BYTES_FILTERED : MAX_BYTES;
        List<String> all;
        boolean truncated;
        try (RandomAccessFile f = new RandomAccessFile(file.toFile(), "r")) {
            long size = f.length();
            byte[] head = new byte[(int) Math.min(3, size)];
            f.readFully(head);
            Charset cs = StandardCharsets.UTF_8;
            int bom = 0;
            if (head.length >= 2 && (head[0] & 0xFF) == 0xFF && (head[1] & 0xFF) == 0xFE) {
                cs = StandardCharsets.UTF_16LE;
                bom = 2;
            } else if (head.length >= 2 && (head[0] & 0xFF) == 0xFE && (head[1] & 0xFF) == 0xFF) {
                cs = StandardCharsets.UTF_16BE;
                bom = 2;
            } else if (head.length == 3 && (head[0] & 0xFF) == 0xEF && (head[1] & 0xFF) == 0xBB
                    && (head[2] & 0xFF) == 0xBF) {
                bom = 3;
            }
            long start = Math.max(bom, size - max);
            if (bom == 2 && (start - bom) % 2 != 0) {
                start++; // two-byte units start at even offsets after the mark
            }
            truncated = start > bom;
            byte[] buf = new byte[(int) (size - start)];
            f.seek(start);
            f.readFully(buf);
            all = new ArrayList<>(Arrays.asList(new String(buf, cs).split("\r?\n", -1)));
            if (truncated && !all.isEmpty()) {
                all.remove(0); // partial first line
            }
            if (!all.isEmpty() && all.get(all.size() - 1).isEmpty()) {
                all.remove(all.size() - 1);
            }
        }
        if (filtered) {
            String q = filter.trim().toLowerCase(Locale.ROOT);
            all.removeIf(l -> !matches(l, q));
        }
        List<String> out = all.subList(Math.max(0, all.size() - lines), all.size());
        List<String> clean = new ArrayList<>(out.size());
        for (String l : out) {
            clean.add(scrub(l.length() > 2000 ? l.substring(0, 2000) + " ..." : l));
        }
        res.put("lines", clean);
        res.put("truncated", truncated);
        return res;
    }

    private static boolean matches(String line, String q) {
        return switch (q) {
            case "errors" -> line.contains("[ERROR]") || line.contains("Exception") || line.contains(" ERROR ");
            case "warnings" -> line.contains("[WARN]") || line.contains(" WARN ") || line.contains("[ERROR]")
                    || line.contains("Exception");
            default -> line.toLowerCase(Locale.ROOT).contains(q);
        };
    }

    public static String scrub(String text) {
        for (String s : secretValues()) {
            text = text.replace(s, "***");
        }
        text = WEBHOOK.matcher(text).replaceAll("***webhook***");
        text = JDBC_CREDS.matcher(text).replaceAll("$1***@");
        text = BOT_TOKEN.matcher(text).replaceAll("***");
        Matcher m = KEY_VALUE.matcher(text);
        return m.replaceAll(r -> Matcher.quoteReplacement(r.group(1)) + "***");
    }

    /** Secret values from the server's .properties files (passwords, tokens, webhook URLs, DB login), re-read each minute. */
    private static synchronized List<String> secretValues() {
        if (System.currentTimeMillis() - literalsAt < 60_000) {
            return literals;
        }
        Set<String> found = new TreeSet<>(Comparator.comparingInt(String::length).reversed().thenComparing(s -> s));
        List<Path> files = new ArrayList<>();
        for (Path dir : List.of(Path.of(ServerConstants.DIR), Path.of(ServerConstants.RESOURCES_DIR))) {
            try (var s = Files.list(dir)) {
                s.filter(p -> p.getFileName().toString().endsWith(".properties")).forEach(files::add);
            } catch (IOException ignored) {
                // no such folder
            }
        }
        for (Path p : files) {
            Properties props = new Properties();
            try (var in = Files.newInputStream(p)) {
                props.load(in);
            } catch (Exception e) {
                continue;
            }
            for (String key : props.stringPropertyNames()) {
                String last = key.substring(key.lastIndexOf('.') + 1);
                String v = props.getProperty(key, "").trim();
                boolean db = p.getFileName().toString().startsWith("database") && DB_KEY.matcher(last).find();
                if (v.length() >= 5 && (db || SECRET_KEY.matcher(last).find())) {
                    found.add(v);
                }
            }
        }
        literals = List.copyOf(found);
        literalsAt = System.currentTimeMillis();
        return literals;
    }
}

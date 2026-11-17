package net.swordie.webapi.site;

import net.swordie.ms.ServerConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The launcher's news: resources/news.json, edited by hand or through PUT /api/admin/news.
 * {"news": [{"date": "...", "title": "...", "text": "..."}], "knownIssues": [{"title": "...", "text": "..."}],
 *  "slides": [{"id": "...", "title": "...", "line": "...", "image": "<file>", "link": "https://...", "date": "..."}]}
 * <p>
 * Slides come back newest first. Their images live in resources/news-images and are served at
 * /api/public/news/image/{file}.
 */
public final class News {

    private static final Path FILE = Path.of(ServerConstants.RESOURCES_DIR, "news.json");
    public static final Path IMAGE_DIR = Path.of(ServerConstants.RESOURCES_DIR, "news-images").toAbsolutePath().normalize();
    public static final int MAX_IMAGE_BYTES = 3 * 1024 * 1024;
    public static final int MAX_SLIDES = 12;
    private static final Pattern IMAGE_NAME = Pattern.compile("^[A-Za-z0-9_-]{1,64}\\.(png|jpg|jpeg|webp)$");
    private static final Pattern SLIDE_ID = Pattern.compile("^[A-Za-z0-9_-]{1,40}$");
    private static final Pattern DATE = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final SecureRandom random = new SecureRandom();
    private static Map<String, Object> cached;
    private static long cachedStamp = -1;

    private News() {
    }

    public static synchronized Map<String, Object> get() {
        try {
            long stamp = Files.exists(FILE) ? Files.getLastModifiedTime(FILE).toMillis() : 0;
            if (stamp != cachedStamp) {
                Map<String, Object> m = stamp == 0 ? new LinkedHashMap<>()
                        : new JSONObject(Files.readString(FILE, StandardCharsets.UTF_8)).toMap();
                m.putIfAbsent("news", List.of());
                m.putIfAbsent("knownIssues", List.of());
                m.put("slides", newestFirst(m.get("slides")));
                cached = m;
                cachedStamp = stamp;
            }
        } catch (Exception e) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("news", List.of());
            m.put("knownIssues", List.of());
            m.put("slides", List.of());
            m.put("error", "news.json couldn't be read");
            return m;
        }
        return cached;
    }

    /** Replaces the parts the body has (news, knownIssues, slides); the others stay as they are. */
    public static synchronized void save(Map<String, Object> body) throws IOException {
        Map<String, Object> current = get();
        if (current.containsKey("error") && Files.exists(FILE)) {
            throw new IOException("news.json couldn't be read, so it wasn't overwritten");
        }
        JSONObject o = new JSONObject();
        o.put("news", body.containsKey("news") ? body.get("news") : current.get("news"));
        o.put("knownIssues", body.containsKey("knownIssues") ? body.get("knownIssues") : current.get("knownIssues"));
        o.put("slides", body.containsKey("slides") ? cleanSlides(body.get("slides")) : current.get("slides"));
        Files.writeString(FILE, o.toString(2), StandardCharsets.UTF_8);
        cachedStamp = -1;
    }

    private static List<Object> newestFirst(Object slides) {
        if (!(slides instanceof List<?> list)) {
            return List.of();
        }
        List<Object> out = new ArrayList<>(list);
        out.sort(Comparator.comparing((Object s) -> s instanceof Map<?, ?> m && m.get("date") != null
                ? String.valueOf(m.get("date")) : "").reversed());
        return out;
    }

    private static JSONArray cleanSlides(Object slides) {
        if (!(slides instanceof List<?> list)) {
            throw new IllegalArgumentException("slides must be a list.");
        }
        if (list.size() > MAX_SLIDES) {
            throw new IllegalArgumentException("Up to " + MAX_SLIDES + " slides.");
        }
        JSONArray out = new JSONArray();
        Set<String> ids = new HashSet<>();
        for (Object item : list) {
            if (!(item instanceof Map<?, ?> s)) {
                throw new IllegalArgumentException("Every slide must be an object.");
            }
            String title = text(s.get("title"), 80);
            String line = text(s.get("line"), 200);
            if (title.isEmpty()) {
                throw new IllegalArgumentException("Every slide needs a title.");
            }
            String id = text(s.get("id"), 40);
            if (!SLIDE_ID.matcher(id).matches() || !ids.add(id)) {
                do {
                    id = randomHex(6);
                } while (!ids.add(id));
            }
            String image = text(s.get("image"), 200);
            if (!image.isEmpty()) {
                image = image.substring(image.lastIndexOf('/') + 1);
                if (!isImageName(image)) {
                    throw new IllegalArgumentException("Bad image name: " + image);
                }
            }
            String link = text(s.get("link"), 300);
            if (!link.isEmpty() && !link.matches("(?i)^https?://[^\\s\"'<>]+$")) {
                throw new IllegalArgumentException("A link must start with http:// or https://.");
            }
            String date = text(s.get("date"), 10);
            if (!DATE.matcher(date).matches()) {
                date = LocalDate.now().toString(); // hardcode-ok
            }
            JSONObject slide = new JSONObject();
            slide.put("id", id);
            slide.put("title", title);
            slide.put("line", line);
            slide.put("image", image);
            slide.put("link", link);
            slide.put("date", date);
            out.put(slide);
        }
        return out;
    }

    private static String text(Object v, int max) {
        String s = v == null ? "" : String.valueOf(v).replaceAll("[\\r\\n\\t]+", " ").trim();
        return s.length() > max ? s.substring(0, max) : s;
    }

    private static String randomHex(int bytes) {
        byte[] b = new byte[bytes];
        random.nextBytes(b);
        return HexFormat.of().formatHex(b);
    }

    public static boolean isImageName(String name) {
        return name != null && IMAGE_NAME.matcher(name).matches();
    }

    public static String contentType(String name) {
        String lower = name.toLowerCase(Locale.ROOT);
        if (lower.endsWith(".png")) {
            return "image/png";
        }
        if (lower.endsWith(".webp")) {
            return "image/webp";
        }
        return "image/jpeg";
    }

    /** A slide image's bytes, or null if there is no such image. */
    public static byte[] image(String name) throws IOException {
        if (!isImageName(name)) {
            return null;
        }
        Path file = IMAGE_DIR.resolve(name).normalize();
        if (!file.startsWith(IMAGE_DIR) || !Files.isRegularFile(file) || Files.size(file) > MAX_IMAGE_BYTES) {
            return null;
        }
        return Files.readAllBytes(file);
    }

    /** Stores an uploaded image under a new random name, typed by its contents; returns the file name. */
    public static String saveImage(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("The image is empty.");
        }
        if (data.length > MAX_IMAGE_BYTES) {
            throw new IllegalArgumentException("Images can be up to " + MAX_IMAGE_BYTES / (1024 * 1024) + " MB.");
        }
        String ext = sniff(data);
        if (ext == null) {
            throw new IllegalArgumentException("Only PNG, JPG or WebP images.");
        }
        Files.createDirectories(IMAGE_DIR);
        String name = "slide-" + randomHex(8) + "." + ext;
        Files.write(IMAGE_DIR.resolve(name), data);
        return name;
    }

    private static String sniff(byte[] d) {
        if (d.length > 8 && (d[0] & 0xFF) == 0x89 && d[1] == 'P' && d[2] == 'N' && d[3] == 'G') {
            return "png";
        }
        if (d.length > 3 && (d[0] & 0xFF) == 0xFF && (d[1] & 0xFF) == 0xD8 && (d[2] & 0xFF) == 0xFF) {
            return "jpg";
        }
        if (d.length > 12 && d[0] == 'R' && d[1] == 'I' && d[2] == 'F' && d[3] == 'F'
                && d[8] == 'W' && d[9] == 'E' && d[10] == 'B' && d[11] == 'P') {
            return "webp";
        }
        return null;
    }
}

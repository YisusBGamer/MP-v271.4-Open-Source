package net.swordie.ms.world;

import net.swordie.ms.ServerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Posts server announcements ({@link WorldNews}) to a Discord channel through a webhook.
 * <p>
 * Off until {@link ServerConstants#DISCORD_WEBHOOK_FILE} (discord-webhook.txt in the server folder) has the webhook's
 * address on its first line (Discord: channel settings > Integrations > Webhooks > Copy Webhook URL). An optional line
 * "types=BOSS_FIRST_CLEAR,LEVEL_MILESTONE,HOT_TIME,LFG_POST" picks which announcements go out (default: all but
 * LFG_POST). The file is read again when it changes, so no restart is needed. The address is a secret: it's kept out
 * of the code and the logs.
 * <p>
 * Posts go out one at a time from their own thread, at most one every 2 seconds (Discord allows about 30 a minute per
 * webhook); when Discord asks to slow down, it waits as long as Discord says. At most 100 wait; more are dropped.
 * <p>
 * Without an address in discord-webhook.txt, the announcements use resources/discord.properties:
 * {@code news.webhook=} a channel of their own
 * (e.g. a public one for players), else the staff channel's {@code webhook=} when {@code post.news=true};
 * {@code news.types=} picks the types like "types=" above. That file is also read again when it changes. The same
 * announcements (not the LFG posts) go to the server-link sheet's Log.
 */
public final class DiscordRelay {

    private static final Logger log = LogManager.getLogger(DiscordRelay.class);

    private static final long MIN_GAP_MS = 2000;
    private static final int MAX_QUEUED = 100;
    private static final Set<String> DEFAULT_TYPES = Set.of("BOSS_FIRST_CLEAR", "LEVEL_MILESTONE", "HOT_TIME");

    private static final ExecutorService sender = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "discord-relay");
        t.setDaemon(true);
        return t;
    });
    private static final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

    private static volatile long fileTime = -1, propsTime = -1;
    private static volatile String webhook = null;
    private static volatile Set<String> types = DEFAULT_TYPES;
    private static final java.util.concurrent.atomic.AtomicInteger queued = new java.util.concurrent.atomic.AtomicInteger();
    private static long lastSent = 0;

    private DiscordRelay() {
    }

    private static long modified(Path file) {
        try {
            return Files.exists(file) ? Files.getLastModifiedTime(file).toMillis() : 0;
        } catch (IOException e) {
            return 0;
        }
    }

    /** Reads discord-webhook.txt (and resources/discord.properties) again if they changed. */
    private static synchronized void reload() {
        Path file = Paths.get(ServerConstants.DISCORD_WEBHOOK_FILE);
        Path props = Paths.get(ServerConstants.RESOURCES_DIR, "discord.properties");
        long time = modified(file), ptime = modified(props);
        if (time == fileTime && ptime == propsTime) {
            return;
        }
        fileTime = time;
        propsTime = ptime;
        String url = null;
        Set<String> t = DEFAULT_TYPES;
        if (time > 0) {
            try {
                for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                    line = line.trim();
                    if (line.startsWith("https://") && url == null) {
                        url = line;
                    } else if (line.toLowerCase().startsWith("types=")) {
                        Set<String> chosen = new HashSet<>();
                        for (String s : line.substring(6).split(",")) {
                            if (!s.isBlank()) {
                                chosen.add(s.trim().toUpperCase());
                            }
                        }
                        t = chosen;
                    }
                }
            } catch (IOException e) {
                log.error("Could not read " + file, e);
            }
        }
        String from = file.getFileName().toString();
        if (url == null && ptime > 0) {
            // the server's Discord settings
            Properties p = new Properties();
            try (var r = Files.newBufferedReader(props, StandardCharsets.UTF_8)) {
                p.load(r);
            } catch (IOException e) {
                log.error("Could not read " + props, e);
            }
            boolean on = !"false".equalsIgnoreCase(p.getProperty("enabled", "true").trim());
            String own = p.getProperty("news.webhook", "").trim();
            if (on && !own.isEmpty()) {
                url = own;
            } else if (on && "true".equalsIgnoreCase(p.getProperty("post.news", "false").trim())) {
                url = p.getProperty("webhook", "").trim();
            }
            String nt = p.getProperty("news.types", "").trim();
            if (!nt.isEmpty()) {
                Set<String> chosen = new HashSet<>();
                for (String s : nt.split(",")) {
                    if (!s.isBlank()) {
                        chosen.add(s.trim().toUpperCase());
                    }
                }
                t = chosen;
            }
            from = props.getFileName().toString();
        }
        webhook = url != null && url.matches("https://(?:\\w+\\.)?discord(?:app)?\\.com/api/webhooks/\\S+") ? url : null;
        types = t;
        log.info(webhook != null ? "Discord relay on (types " + types + ", from " + from + ")."
                : "Discord relay off (no webhook address for announcements).");
    }

    /** Queues an announcement for Discord (when the relay is on and its type is picked). Never blocks. */
    public static void post(WorldNews.Announcement a) {
        try {
            if (a.type() != WorldNews.Type.LFG_POST) {
                // the server-link sheet's Log (when it's set up)
                net.swordie.ms.ext.ServerHooks.onWorldNews(a.text());
            }
        } catch (Throwable ignored) {
        }
        try {
            reload();
            String url = webhook;
            if (url == null || !types.contains(a.type().name()) || queued.get() >= MAX_QUEUED) {
                return;
            }
            queued.incrementAndGet();
            sender.submit(() -> {
                try {
                    send(url, a);
                } finally {
                    queued.decrementAndGet();
                }
            });
        } catch (Exception e) {
            log.error("Discord relay failed", e);
        }
    }

    private static void send(String url, WorldNews.Announcement a) {
        String body = "{\"content\":\"" + json(escapeMarkdown(a.text())) + "\",\"allowed_mentions\":{\"parse\":[]}}";
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                long wait = MIN_GAP_MS - (System.currentTimeMillis() - lastSent);
                if (wait > 0) {
                    Thread.sleep(wait);
                }
                lastSent = System.currentTimeMillis();
                HttpResponse<String> res = http.send(HttpRequest.newBuilder(URI.create(url))
                        .timeout(Duration.ofSeconds(15))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build(),
                        HttpResponse.BodyHandlers.ofString());
                if (res.statusCode() == 429) { // too many: Discord says how long to wait
                    double after = 5;
                    var m = java.util.regex.Pattern.compile("\"retry_after\"\\s*:\\s*([0-9.]+)").matcher(res.body());
                    if (m.find()) {
                        after = Double.parseDouble(m.group(1));
                    }
                    Thread.sleep((long) Math.min(60_000, after * 1000) + 250);
                    continue;
                }
                if (res.statusCode() >= 300) {
                    log.warn("Discord relay: Discord answered " + res.statusCode() + " for a " + a.type() + " post.");
                }
                return;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } catch (Exception e) {
                log.warn("Discord relay: could not reach Discord (" + e.getClass().getSimpleName() + ").");
                return;
            }
        }
    }

    /** Character names and notes can hold * _ ~ ` | > which Discord would turn into formatting. */
    private static String escapeMarkdown(String s) {
        return s.replaceAll("([*_~`|>\\\\])", "\\\\$1");
    }

    private static String json(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }
}

package net.swordie.webapi.site;

import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.JobPathModule;
import net.swordie.ms.client.character.RebirthModule;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.constants.MobExpConstants;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.World;
import net.swordie.orm.dao.SworDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * The data behind the server website (see {@link net.swordie.webapi.routes.PublicRoute}): server status, online
 * players and rankings. Database results are cached for {@link SiteConfig#CACHE_SECONDS}, so the website can't
 * put load on the database however often it's refreshed. Everything here is read-only and public: no account data,
 * only what the game itself shows other players.
 */
public class SiteData {

    private static final Logger log = LogManager.getLogger(SiteData.class);

    public static final int MAX_LIMIT = 100;
    private static final int REBIRTH_QR = GameConstants.REBIRTH_QR;

    // ------------------------------------------------------------------------------------------------- caching

    private static final class Cached {
        private final long time;
        private final Object value;

        private Cached(long time, Object value) {
            this.time = time;
            this.value = value;
        }
    }

    private static final Map<String, Cached> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    private static <T> T cached(String key, Supplier<T> loader) {
        long now = System.currentTimeMillis();
        Cached c = cache.get(key);
        if (c != null && now - c.time < SiteConfig.CACHE_SECONDS * 1000L) {
            return (T) c.value;
        }
        T value = loader.get();
        cache.put(key, new Cached(now, value));
        return value;
    }

    /**
     * Where the launcher downloads plus.dll (client.hookUrl / client.hookSha256 in server.properties), or null.
     * The URL goes out XORed with the hash and base64'd so it isn't sitting in plain text in the JSON.
     */
    private static Map<String, Object> hookInfo() {
        String url = net.swordie.ms.ServerSettings.getString("client.hookUrl", "").trim();
        String sha = net.swordie.ms.ServerSettings.getString("client.hookSha256", "").trim().toLowerCase();
        if (!url.startsWith("https://") || !sha.matches("[0-9a-f]{64}")) return null;
        byte[] u = url.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] k = sha.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        for (int i = 0; i < u.length; i++) u[i] ^= k[i % k.length];
        Map<String, Object> hook = new LinkedHashMap<>();
        hook.put("sha256", sha);
        hook.put("src", java.util.Base64.getEncoder().encodeToString(u));
        return hook;
    }

    // ------------------------------------------------------------------------------------------------- database

    /**
     * Reads a row into a map by column label (use "AS" in the queries).
     */
    private static final SworDao<Map<String, Object>> ROW_DAO = (resultSet, alias) -> {
        Map<String, Object> row = new LinkedHashMap<>();
        try {
            var meta = resultSet.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                row.put(meta.getColumnLabel(i), resultSet.getObject(i));
            }
        } catch (SQLException e) {
            log.error("Could not read a row for the website", e);
        }
        return row;
    };

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> query(String sql, Object... args) {
        List<Map<String, Object>> rows = new ArrayList<>();
        try {
            for (Object o : DatabaseManager.executeSelect(ROW_DAO, sql, "", args)) {
                if (o instanceof Map) {
                    rows.add((Map<String, Object>) o);
                }
            }
        } catch (Exception e) {
            log.error("Website query failed: " + sql, e);
        }
        return rows;
    }

    private static long num(Object o) {
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        if (o instanceof String) {
            try {
                return Long.parseLong(((String) o).trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        if (o instanceof Boolean) {
            return (Boolean) o ? 1 : 0;
        }
        return 0;
    }

    private static String str(Object o) {
        return o == null ? null : o.toString();
    }

    /**
     * Joins and filters shared by the character queries: banned accounts never show up, staff only if allowed.
     */
    private static final String CHAR_FROM = "FROM characterstats cs "
            + "INNER JOIN characters c ON c.id = cs.characterid "
            + "INNER JOIN users u ON u.id = c.userid ";

    /**
     * Logins that always count as players on the website regardless of account type. Lowercase login names
     * (users.name).
     */
    private static final java.util.Set<String> ALWAYS_LISTED_USERS = java.util.Set.of("shawnyall");

    /** @return SQL: the login with this alias is a player, or one of {@link #ALWAYS_LISTED_USERS} */
    private static String playerSql(String alias) {
        return "(" + alias + ".accounttype = 0 OR LOWER(" + alias + ".name) IN ("
                + ALWAYS_LISTED_USERS.stream().map(n -> "'" + n.replace("'", "") + "'")
                .collect(java.util.stream.Collectors.joining(",")) + "))";
    }

    /** "AND ..." that leaves out staff accounts (GMs, admins, testers) when WEB_HIDE_STAFF is on, for queries on u. */
    private static String staffFilter() {
        return (ServerConstants.WEB_HIDE_STAFF ? "AND " + playerSql("u") + " " : "")
                + net.swordie.ms.ext.ServerHooks.sqlExcludeUnlisted("u"); // social bots are never listed
    }

    /**
     * @return whether this online character shows on the website (online list and online counts): not a hidden GM,
     * and not a staff account's character when WEB_HIDE_STAFF is on
     */
    private static boolean isListed(Char chr) {
        if (chr == null || chr.isHidden() || net.swordie.ms.ext.ServerHooks.isUnlisted(chr)) {
            return false; // hidden GMs and social bots
        }
        return !ServerConstants.WEB_HIDE_STAFF || chr.getUser() == null || chr.getUser().isPlayer()
                || (chr.getUser().getName() != null
                && ALWAYS_LISTED_USERS.contains(chr.getUser().getName().toLowerCase(java.util.Locale.ROOT)));
    }

    private static int countListed(Channel channel) {
        int n = 0;
        for (Char chr : new ArrayList<>(channel.getChars().values())) {
            if (isListed(chr)) {
                n++;
            }
        }
        return n;
    }

    /** @return the listed online characters of one world, or of all worlds when world is null */
    public static int countListedOnline(World world) {
        int n = 0;
        for (World w : Server.getInstance().getWorlds()) {
            if (world == null || w == world) {
                for (Channel channel : w.getChannels()) {
                    n += countListed(channel);
                }
            }
        }
        return n;
    }

    private static String charWhere() {
        return "WHERE (u.banExpireDate IS NULL OR u.banExpireDate < CURRENT_TIMESTAMP) "
                + staffFilter();
    }

    private static Map<String, Object> characterEntry(Map<String, Object> row) {
        int job = (int) num(row.get("job"));
        int subJob = (int) num(row.get("subjob"));
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("name", str(row.get("name")));
        res.put("level", num(row.get("level")));
        res.put("job", job);
        res.put("className", JobPathModule.getClassName(job, subJob));
        res.put("classGroup", JobPathModule.getClassGroup(job, subJob));
        if (row.containsKey("fame")) {
            res.put("fame", num(row.get("fame")));
        }
        if (row.containsKey("guild")) {
            res.put("guild", str(row.get("guild")));
        }
        return res;
    }

    /**
     * The database only has a character's level and fame as of its last save, so online characters use their live
     * values.
     */
    private static void applyLiveStats(List<Map<String, Object>> entries) {
        Map<String, Char> online = new HashMap<>();
        for (World world : Server.getInstance().getWorlds()) {
            for (Channel channel : world.getChannels()) {
                for (Char chr : new ArrayList<>(channel.getChars().values())) {
                    if (chr != null && chr.getName() != null) {
                        online.put(chr.getName().toLowerCase(), chr);
                    }
                }
            }
        }
        if (online.isEmpty()) {
            return;
        }
        for (var entry : entries) {
            Char chr = online.get(String.valueOf(entry.get("name")).toLowerCase());
            if (chr == null) {
                continue;
            }
            try {
                entry.put("level", (long) chr.getLevel());
                if (entry.containsKey("fame")) {
                    entry.put("fame", (long) chr.getAvatarData().getCharacterStat().getPop());
                }
            } catch (Exception e) {
                log.error("Could not read an online character's live stats for the website", e);
            }
        }
    }

    private static long longOf(Object o) {
        return o instanceof Number ? ((Number) o).longValue() : 0;
    }

    private static void addRanks(List<Map<String, Object>> entries) {
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).put("rank", i + 1);
        }
    }

    private static int clampLimit(int limit) {
        return Math.max(1, Math.min(MAX_LIMIT, limit <= 0 ? 50 : limit));
    }

    // ------------------------------------------------------------------------------------------------- server

    public static Map<String, Object> getOverview() {
        Server server = Server.getInstance();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("name", ServerConstants.SERVER_NAME);
        res.put("online", !server.isInShutdown());
        res.put("version", String.format("%s.%s", ServerConstants.VERSION, ServerConstants.MINOR_VERSION));
        LocalDateTime started = server.getStartedTime();
        res.put("startedAt", started == null ? null : started.toString());
        res.put("uptimeSeconds", started == null ? 0 : Duration.between(started, LocalDateTime.now()).getSeconds());
        res.put("playersOnline", countListedOnline(null));
        res.put("shutdownScheduled", server.isInShutdown());
        res.put("shutdownMinutesRemaining", server.isInShutdown() ? server.getMinutesRemaining() : 0);
        res.put("restart", net.swordie.ms.world.RestartManager.publicStatus());
        // the game client this server matches (Steam build id, optional MapleStory.exe size)
        Map<String, Object> client = new LinkedHashMap<>();
        client.put("version", ServerConstants.VERSION + "." + ServerConstants.MINOR_VERSION);
        client.put("steamBuild", net.swordie.ms.ServerSettings.getString("client.steamBuild", "26141834")); // hardcode-ok
        long exeSize = net.swordie.ms.ServerSettings.getLong("client.exeSize", 0);
        client.put("exeSize", exeSize > 0 ? exeSize : null);
        Map<String, Object> hook = hookInfo();
        if (hook != null) client.put("hook", hook);
        res.put("client", client);

        List<Map<String, Object>> worlds = new ArrayList<>();
        for (World world : server.getWorlds()) {
            Map<String, Object> w = new LinkedHashMap<>();
            w.put("name", world.getName());
            w.put("playersOnline", countListedOnline(world));
            List<Map<String, Object>> channels = new ArrayList<>();
            for (Channel channel : world.getChannels()) {
                Map<String, Object> ch = new LinkedHashMap<>();
                ch.put("channel", channel.getChannelId());
                ch.put("players", countListed(channel));
                ch.put("buffed", net.swordie.ms.world.BuffedChannels.isBuffed(channel.getChannelId()));
                channels.add(ch);
            }
            w.put("channels", channels);
            worlds.add(w);
        }
        res.put("worlds", worlds);

        Map<String, Object> rates = new LinkedHashMap<>();
        rates.put("exp", ServerRates.getExpRate());
        rates.put("drop", ServerRates.getDropRate());
        rates.put("meso", ServerRates.getMesoRate());
        res.put("rates", rates);
        res.put("announcements", getAnnouncements());

        // the feature list and commands never change at runtime, so they're served by getFeatures (loaded once by the
        // website) instead of here (polled every 30 seconds)
        return res;
    }

    private static final java.time.format.DateTimeFormatter DAY_TIME =
            java.time.format.DateTimeFormatter.ofPattern("EEEE h:mm a", java.util.Locale.US);

    /**
     * The homepage's announcement board: buffed channels, active now or coming up. Each entry is
     * {kind, title, text, active}.
     */
    public static List<Map<String, Object>> getAnnouncements() {
        List<Map<String, Object>> list = new ArrayList<>();
        int mode = net.swordie.ms.world.BuffedChannels.getMode();
        if (mode != net.swordie.ms.world.BuffedChannels.OFF) {
            String channels = net.swordie.ms.world.BuffedChannels.channelsText();
            String what = channels + (channels.startsWith("Channels ") ? " have " : " has ")
                    + net.swordie.ms.world.BuffedChannels.effectText() + ". Bosses aren't buffed.";
            if (net.swordie.ms.world.BuffedChannels.isActive()) {
                var end = net.swordie.ms.world.BuffedChannels.getEnd();
                announcement(list, "buffed", mode == net.swordie.ms.world.BuffedChannels.WEEKENDS
                        ? "Buffed channels this weekend" : "Buffed channels", what + (end == null ? ""
                        : " Until the end of Sunday, Pacific time."), true);
            } else {
                var start = net.swordie.ms.world.BuffedChannels.getNextStart();
                announcement(list, "buffed", "Buffed channels on weekends", "Friday to Sunday, Pacific time"
                        + (start == null ? "" : " (next starts " + start.format(DAY_TIME) + ")") + ". " + what, false);
            }
        }
        addScheduledContent(list);
        list.sort((a, b) -> Boolean.compare((Boolean) b.get("active"), (Boolean) a.get("active")));
        return list;
    }

    /** Content that runs every day or week; always on. Daily resets are at midnight UTC, weekly ones on Thursday. */
    private static void addScheduledContent(List<Map<String, Object>> list) {
        announcement(list, "daily", "Daily Gift",
                "Log in and hunt to claim a gift every day of the month. Days 1 and 15 give Swordie Coins.", true);
        announcement(list, "daily", "Monster Park",
                "Free and unlimited, every dungeon from Morass to Odium. Monster Park Extreme is open at level 260 "
                        + "for up to 4 players, also free and unlimited. Sundays give 50% more EXP.", true);
        announcement(list, "daily", "Arcane River and Grandis dailies",
                "Symbol dailies in every area, plus both of Erda's Request hunts every day at level 260.", true);
        announcement(list, "weekly", "Weekly content",
                "Kritias missions, Dark World Tree missions and the weekly bosses reset every Thursday.", true);
        announcement(list, "weekly", "Mu Lung Dojo",
                "All 100 floors are open, up to the Awakened Mu Gong on the Top Floor.", true);
        if (ServerFeatures.on(ServerFeatures.Feature.SWORDIE_COINS)) {
            announcement(list, "weekly", "Swordie Coin Shop",
                    "Earn coins from your first weekly clear of each boss, level milestones, Maple Achievements and "
                            + "rebirths, then spend them in @shop on chairs, mounts, outfits, bags and more.", true);
        }
    }

    private static void announcement(List<Map<String, Object>> list, String kind, String title, String text,
                                     boolean active) {
        Map<String, Object> e = new LinkedHashMap<>();
        e.put("kind", kind);
        e.put("title", title);
        e.put("text", text);
        e.put("active", active);
        list.add(e);
    }

    /** The website's "Commands & features": {qol, commands}. Follows the QoL settings in effect. */
    public static Map<String, Object> getFeatures() {
        return qolCache().features();
    }

    /** The QoL list built for one combination of the settings it mentions (rebuilt when one of them changes). */
    private record QolCache(String settings, List<Map<String, Object>> qol, Map<String, Object> features) {
    }

    private static volatile QolCache qolCache;

    /** The settings the website's lists mention, read once per request. */
    private record QolSettings(Map<ServerFeatures.Feature, Boolean> on, List<String> commandsOn) {
        boolean is(ServerFeatures.Feature f) {
            return Boolean.TRUE.equals(on.get(f));
        }
    }

    private static final ServerFeatures.Feature[] QOL_SETTINGS = {
            ServerFeatures.Feature.FREE_ENHANCE, ServerFeatures.Feature.BOSS_UI_IGNORE_QUESTS,
            ServerFeatures.Feature.SCROLLS_ALWAYS_SUCCEED, ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS,
            ServerFeatures.Feature.NEXT_HELPER,
            ServerFeatures.Feature.SPROUT_BONUS, ServerFeatures.Feature.RESTED_EXP,
            ServerFeatures.Feature.JOB_PATHS, ServerFeatures.Feature.REMOTE_ENHANCE,
    };

    private static QolCache qolCache() {
        // read once: the list and its key come from the same values even if a reload lands in between
        Map<ServerFeatures.Feature, Boolean> on = new java.util.EnumMap<>(ServerFeatures.Feature.class);
        for (ServerFeatures.Feature f : QOL_SETTINGS) {
            on.put(f, ServerFeatures.on(f));
        }
        List<String> commandsOn = new ArrayList<>();
        for (String[] command : net.swordie.ms.client.character.commands.PlayerCommands.getCommandList()) {
            if (net.swordie.ms.client.character.commands.PlayerCommands.isOn(command[0])) {
                commandsOn.add(command[0]);
            }
        }
        QolSettings s = new QolSettings(on, commandsOn);
        String settings = on + "|" + commandsOn;
        QolCache c = qolCache;
        if (c == null || !c.settings().equals(settings)) {
            List<Map<String, Object>> qol = buildQualityOfLife(s);
            c = new QolCache(settings, qol, Map.of("qol", qol, "commands", buildCommands(commandsOn)));
            qolCache = c;
        }
        return c;
    }

    /**
     * The server's quality of life changes for the website, grouped. Built from the settings in GameConstants and
     * ServerFeatures, so a feature that is switched off isn't listed.
     */
    public static List<Map<String, Object>> getQualityOfLife() {
        return qolCache().qol();
    }

    private static List<Map<String, Object>> buildQualityOfLife(QolSettings s) {
        boolean freeEnhance = s.is(ServerFeatures.Feature.FREE_ENHANCE);
        boolean bossBypass = s.is(ServerFeatures.Feature.BOSS_UI_IGNORE_QUESTS);
        // one short line each, with menu paths as they read in game
        List<Map<String, Object>> list = new ArrayList<>();
        // ---- New players
        String fresh = "New players";
        if (s.is(ServerFeatures.Feature.NEXT_HELPER)) {
            qol(list, fresh, "What should I do next?", "Suggestions for your character, each one click away.",
                    "@menu > What should I do next?");
        }
        qol(list, fresh, "Onboarding checklist", "Five quick steps around the server.",
                "@menu > Help > Onboarding checklist");
        List<String> boosts = new ArrayList<>();
        if (s.is(ServerFeatures.Feature.SPROUT_BONUS)) {
            boosts.add(String.format("New accounts' characters below level %d give their party up to +%d%% EXP.",
                    net.swordie.ms.client.character.NewPlayerModule.SPROUT_MAX_LEVEL + 1,
                    net.swordie.ms.client.character.NewPlayerModule.SPROUT_BONUS_MAX));
        }
        if (s.is(ServerFeatures.Feature.RESTED_EXP)) {
            boosts.add("Time away gives rested EXP (2x hunting EXP).");
        }
        if (!boosts.isEmpty()) {
            qol(list, fresh, "EXP boosts", String.join(" ", boosts));
        }
        // ---- Your character
        String character = "Your character";
        qol(list, character, "Classic or Express", "Play the full story and job quests, or skip the tutorial and "
                + "advance automatically. Classic can switch to Express any time.", "@menu > My character");
        if (s.is(ServerFeatures.Feature.JOB_PATHS)) {
            qol(list, character, "Any class, any path", String.format("From level %d, pick any job and path; the "
                    + "advancements, SP and a weapon come with it. ", GameConstants.JOB_PATH_MIN_LEVEL)
                    + GameConstants.JOB_PATH_WARNING, "@job");
        }
        String rebirth = String.format("Rebirth at level %d for +%d AP%s.", GameConstants.REBIRTH_MIN_LEVEL,
                ServerFeatures.getInt(ServerFeatures.Feature.REBIRTH_BONUS_AP),
                ServerFeatures.getInt(ServerFeatures.Feature.REBIRTH_MAX) > 0 ? String.format(" (up to %d times)",
                        ServerFeatures.getInt(ServerFeatures.Feature.REBIRTH_MAX)) : "");
        qol(list, character, "Leveling, stats and rebirth", expCurveText() + " Move AP freely. " + rebirth
                + " Hide others' effects for less lag.", "@menu > My character");
        // ---- Gear and items
        String gear = "Gear and items";
        List<String> enhance = new ArrayList<>();
        if (s.is(ServerFeatures.Feature.REMOTE_ENHANCE)) {
            enhance.add("Scrolls, Star Force, potentials and cubes in one menu"
                    + (freeEnhance ? ", free." : "."));
        }
        if (s.is(ServerFeatures.Feature.SCROLLS_ALWAYS_SUCCEED)) {
            enhance.add("Scrolls never fail.");
        }
        if (s.is(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS)) {
            enhance.add("Star Force never fails.");
        }
        qol(list, gear, "Enhancing", String.join(" ", enhance), "@enhance", "@menu > Gear");
        qol(list, gear, "Gear helpers", "Equip your best gear, see which slots to farm and what raises your damage "
                + "most. Transmog too." + (ServerFeatures.on(ServerFeatures.Feature.AUTO_MERGE_SYMBOLS) ? " Symbols merge and level up by "
                + "themselves." : ""), "@menu > Gear");
        qol(list, gear, "Shops, storage and pets", String.format("Every shop in one list, storage anywhere, bulk "
                        + "selling, and a pet with every skill for %s mesos.",
                java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(GameConstants.PET_PACKAGE_PRICE)),
                "@menu > Shops & trading", "@menu > Storage & inventory");
        // ---- Travel and info
        String travel = "Travel and info";
        qol(list, travel, "Travel and bosses", "Warp to towns, the Free Market, training maps for your level and "
                + "boss entrances" + (bossBypass
                ? " (no prerequisite quests)" : "") + ". See which bosses you can beat.", "@go", "@menu > Travel", "@menu > Bosses");
        qol(list, travel, "Quests and drops", (ServerFeatures.on(ServerFeatures.Feature.QUEST_READY_NOTICE) ? "Quests tell you when they're ready "
                + "to turn in. " : "") + "See what drops here or who drops an item.", "@menu > Info");
        // ---- Together
        String together = "Playing together";
        qol(list, together, "Looking for group", "Post your group, or join one in one click.",
                "@menu > Looking for group");
        qol(list, together, "Boss first clears", "The whole server hears about your first clear of each boss. See "
                + "them under First clears on this page.");
        return Collections.unmodifiableList(list);
    }

    /**
     * The player commands for the website: the same list @help prints (PlayerCommands.getCommandList), without the
     * ones switched off.
     */
    private static List<Map<String, Object>> buildCommands(List<String> commandsOn) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (String[] command : net.swordie.ms.client.character.commands.PlayerCommands.getCommandList()) {
            if (!commandsOn.contains(command[0])) {
                continue;
            }
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("command", command[0]);
            entry.put("text", command[1]);
            list.add(entry);
        }
        return Collections.unmodifiableList(list);
    }

    /** The EXP curve for the website. */
    private static String expCurveText() {
        if (!ServerFeatures.on(ServerFeatures.Feature.CUSTOM_EXP_CURVE)) {
            return "Monster EXP by level: " + net.swordie.ms.constants.MobExpConstants.describe() + ".";
        }
        return String.format("From level %d, each level takes only a little longer than the last.",
                GameConstants.CUSTOM_EXP_FROM_LEVEL);
    }

    private static void qol(List<Map<String, Object>> list, String group, String title, String text,
                            String... commands) {
        Map<String, Object> entry = new LinkedHashMap<>();
        entry.put("group", group);
        entry.put("title", title);
        entry.put("text", text);
        entry.put("commands", List.of(commands));
        list.add(entry);
    }

    public static Map<String, Object> getTotals() {
        return cached("totals", () -> {
            Map<String, Object> res = new LinkedHashMap<>();
            var rows = query("SELECT "
                    // only characters that belong to an account, and only accounts (logins) that have one: no
                    // unused logins (e.g. the admin accounts the database setup creates) or leftover rows
                    // (staff accounts and their characters and guilds aren't counted when WEB_HIDE_STAFF is on)
                    + "(SELECT COUNT(DISTINCT c.userid) FROM characters c INNER JOIN accounts a ON a.id = c.accid "
                    + "INNER JOIN users u ON u.id = c.userid WHERE 1=1 " + staffFilter() + ") AS accounts, "
                    + "(SELECT COUNT(*) FROM characters c INNER JOIN accounts a ON a.id = c.accid "
                    + "INNER JOIN users u ON u.id = c.userid WHERE 1=1 " + staffFilter() + ") AS characters, "
                    + (ServerConstants.WEB_HIDE_STAFF
                    ? "(SELECT COUNT(DISTINCT gm.guildid) FROM guildmembers gm INNER JOIN guilds g ON g.id = gm.guildid "
                    + "INNER JOIN characters c ON c.id = gm.charid INNER JOIN users u ON u.id = c.userid "
                    + "WHERE " + playerSql("u") + " " + net.swordie.ms.ext.ServerHooks.sqlExcludeUnlisted("u")
                    + ") AS guilds, "
                    : "(SELECT COUNT(*) FROM guilds) AS guilds, ")
                    + "(SELECT MAX(cs.level) " + CHAR_FROM + charWhere() + ") AS highestLevel");
            Map<String, Object> row = rows.isEmpty() ? Map.of() : rows.get(0);
            res.put("accounts", num(row.get("accounts")));
            res.put("characters", num(row.get("characters")));
            res.put("rebirths", getTotalRebirths());
            res.put("highestLevel", num(row.get("highestLevel")));
            res.put("guilds", num(row.get("guilds")));
            return res;
        });
    }

    /**
     * @return all rebirths of every character added up (online characters' live counts, saved counts otherwise)
     */
    private static long getTotalRebirths() {
        Map<String, Integer> counts = new HashMap<>();
        var rows = query("SELECT cs.name AS name, q.qrvalue AS record "
                + CHAR_FROM + "INNER JOIN quests q ON q.questmanager_id = c.questmanager "
                + charWhere() + "AND q.qrkey = ?", REBIRTH_QR);
        for (var row : rows) {
            counts.put(String.valueOf(row.get("name")).toLowerCase(), parseRebirthCount(str(row.get("record"))));
        }
        for (World world : Server.getInstance().getWorlds()) {
            for (Channel channel : world.getChannels()) {
                for (Char chr : new ArrayList<>(channel.getChars().values())) {
                    try {
                        if (chr != null && chr.getName() != null) {
                            String key = chr.getName().toLowerCase();
                            int live = RebirthModule.getRebirthCount(chr);
                            if (live > 0 || counts.containsKey(key)) {
                                counts.put(key, live);
                            }
                        }
                    } catch (Exception e) {
                        log.error("Could not read an online character's rebirths for the website", e);
                    }
                }
            }
        }
        long total = 0;
        for (int c : counts.values()) {
            total += Math.max(0, c);
        }
        return total;
    }

    // ------------------------------------------------------------------------------------------------- players

    /**
     * @return the characters online right now (hidden GMs excluded), highest level first
     */
    public static List<Map<String, Object>> getOnlinePlayers() {
        List<Map<String, Object>> res = new ArrayList<>();
        for (World world : Server.getInstance().getWorlds()) {
            for (Channel channel : world.getChannels()) {
                for (Char chr : new ArrayList<>(channel.getChars().values())) {
                    try {
                        if (!isListed(chr)) {
                            continue; // hidden GMs, and staff accounts' characters when WEB_HIDE_STAFF is on
                        }
                        Map<String, Object> p = new LinkedHashMap<>();
                        p.put("name", chr.getName());
                        p.put("level", (int) chr.getLevel());
                        p.put("job", (int) chr.getJob());
                        p.put("className", JobPathModule.getClassName(chr.getJob(), chr.getSubJob()));
                        p.put("world", world.getName());
                        p.put("channel", channel.getChannelId());
                        if (SiteConfig.SHOW_PLAYER_LOCATION) {
                            String map = StringData.getMapStringById(chr.getFieldID());
                            p.put("map", map == null ? null : map);
                        }
                        p.put("guild", chr.getGuild() == null ? null : chr.getGuild().getName());
                        res.add(p);
                    } catch (Exception e) {
                        log.error("Could not list an online character for the website", e);
                    }
                }
            }
        }
        res.sort((a, b) -> Integer.compare((int) b.get("level"), (int) a.get("level")));
        return res;
    }

    private static boolean isOnline(String name) {
        for (World world : Server.getInstance().getWorlds()) {
            for (Char chr : world.getChars()) {
                if (chr != null && !chr.isHidden() && chr.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------------------------------- rankings

    /**
     * @param group a class group ("Explorers", ...) to filter on, or null/"all"
     */
    public static List<Map<String, Object>> getLevelRanking(int limit, String group) {
        int lim = clampLimit(limit);
        String g = canonicalGroup(group);
        if (g == null && group != null && !group.isBlank() && !group.equalsIgnoreCase("all")) {
            return new ArrayList<>(); // unknown group (also keeps arbitrary input out of the cache)
        }
        return cached("level:" + lim + ":" + g, () -> {
            // filtering by class group is done here (the job ids don't map to groups in SQL), so read more rows
            int fetch = g == null ? lim + 50 : MAX_LIMIT * 20; // a few extra rows for online characters' live levels
            var rows = query("SELECT cs.name AS name, cs.level AS level, cs.job AS job, cs.subjob AS subjob, "
                    + "cs.pop AS fame, g.name AS guild "
                    + CHAR_FROM + "LEFT JOIN guilds g ON g.id = c.guild "
                    + charWhere()
                    + "ORDER BY cs.level DESC, CAST(cs.exp AS UNSIGNED) DESC, cs.lastlevelobtainedtime ASC "
                    + "LIMIT ?", fetch);
            List<Map<String, Object>> res = new ArrayList<>();
            for (var row : rows) {
                var entry = characterEntry(row);
                if (g == null || g.equalsIgnoreCase((String) entry.get("classGroup"))) {
                    res.add(entry);
                }
            }
            applyLiveStats(res);
            res.sort((a, b) -> Long.compare(longOf(b.get("level")), longOf(a.get("level")))); // stable: EXP order kept
            res = new ArrayList<>(res.subList(0, Math.min(lim, res.size())));
            addRanks(res);
            return res;
        });
    }

    /**
     * The newest boss first clears (BossFirstClearModule, table boss_first_clears), for the website's "First clears"
     * tab: {name, level, className, boss, time}.
     */
    public static List<Map<String, Object>> getRecentFirstClears(int limit) {
        int lim = clampLimit(limit);
        return cached("firstclears:" + lim, () -> {
            var rows = query("SELECT cs.name AS name, cs.level AS level, cs.job AS job, cs.subjob AS subjob, "
                    + "bfc.boss AS boss, bfc.cleared_at AS clearedAt "
                    + CHAR_FROM + "INNER JOIN boss_first_clears bfc ON bfc.charid = c.id "
                    + charWhere()
                    + "ORDER BY bfc.cleared_at DESC LIMIT ?", lim);
            List<Map<String, Object>> res = new ArrayList<>();
            for (var row : rows) {
                var entry = characterEntry(row);
                entry.put("boss", bossDisplay(String.valueOf(row.get("boss"))));
                entry.put("time", row.get("clearedAt") == null ? null : String.valueOf(row.get("clearedAt")));
                res.add(entry);
            }
            return res;
        });
    }

    /** "Zakum|Chaos" -> "Zakum (Chaos)" (boss_first_clears.boss) */
    public static String bossDisplay(String key) {
        int bar = key.indexOf('|');
        return bar < 0 ? key : key.substring(0, bar) + " (" + key.substring(bar + 1) + ")";
    }

    public static List<Map<String, Object>> getFameRanking(int limit) {
        int lim = clampLimit(limit);
        return cached("fame:" + lim, () -> {
            var rows = query("SELECT cs.name AS name, cs.level AS level, cs.job AS job, cs.subjob AS subjob, "
                    + "cs.pop AS fame, g.name AS guild "
                    + CHAR_FROM + "LEFT JOIN guilds g ON g.id = c.guild "
                    + charWhere()
                    + "ORDER BY cs.pop DESC, cs.level DESC LIMIT ?", lim + 50);
            List<Map<String, Object>> res = new ArrayList<>();
            for (var row : rows) {
                res.add(characterEntry(row));
            }
            applyLiveStats(res);
            res.sort((a, b) -> {
                int c = Long.compare(longOf(b.get("fame")), longOf(a.get("fame")));
                return c != 0 ? c : Long.compare(longOf(b.get("level")), longOf(a.get("level")));
            });
            res = new ArrayList<>(res.subList(0, Math.min(lim, res.size())));
            addRanks(res);
            return res;
        });
    }

    /**
     * Rebirths are kept in a server-only quest record ("count=N;path=..."), see RebirthModule.
     */
    public static List<Map<String, Object>> getRebirthRanking(int limit) {
        int lim = clampLimit(limit);
        return cached("rebirth:" + lim, () -> {
            var rows = query("SELECT cs.name AS name, cs.level AS level, cs.job AS job, cs.subjob AS subjob, "
                    + "q.qrvalue AS record "
                    + CHAR_FROM + "INNER JOIN quests q ON q.questmanager_id = c.questmanager "
                    + charWhere() + "AND q.qrkey = ?", REBIRTH_QR);
            List<Map<String, Object>> res = new ArrayList<>();
            for (var row : rows) {
                int count = parseRebirthCount(str(row.get("record")));
                if (count <= 0) {
                    continue;
                }
                var entry = characterEntry(row);
                entry.put("rebirths", count);
                res.add(entry);
            }
            applyLiveStats(res);
            res.sort((a, b) -> {
                int c = Integer.compare((int) b.get("rebirths"), (int) a.get("rebirths"));
                return c != 0 ? c : Long.compare((long) b.get("level"), (long) a.get("level"));
            });
            List<Map<String, Object>> top = new ArrayList<>(res.subList(0, Math.min(lim, res.size())));
            addRanks(top);
            return top;
        });
    }

    /**
     * @return the known class group with that name (any case), or null
     */
    private static String canonicalGroup(String group) {
        if (group == null) {
            return null;
        }
        for (String g : JobPathModule.GROUPS) {
            if (g.equalsIgnoreCase(group.trim())) {
                return g;
            }
        }
        return "other".equalsIgnoreCase(group.trim()) ? "Other" : null;
    }

    private static int parseRebirthCount(String record) {
        if (record == null) {
            return 0;
        }
        for (String part : record.split(";")) {
            if (part.startsWith("count=")) {
                try {
                    return Integer.parseInt(part.substring(6).trim());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    public static List<Map<String, Object>> getGuildRanking(int limit) {
        int lim = clampLimit(limit);
        return cached("guilds:" + lim, () -> {
            // with WEB_HIDE_STAFF: only player members count, guilds of staff characters only are left out, and a
            // staff character leading a guild isn't named
            var rows = ServerConstants.WEB_HIDE_STAFF
                    ? query("SELECT g.name AS name, g.level AS level, g.ggp AS ggp, pm.n AS members, "
                    + "CASE WHEN " + playerSql("lu") + " THEN ldr.name ELSE NULL END AS leader "
                    + "FROM guilds g "
                    + "INNER JOIN (SELECT gm.guildid AS guildid, COUNT(*) AS n FROM guildmembers gm "
                    + "INNER JOIN characters c ON c.id = gm.charid INNER JOIN users u ON u.id = c.userid "
                    + "WHERE " + playerSql("u") + " " + net.swordie.ms.ext.ServerHooks.sqlExcludeUnlisted("u")
                    + "GROUP BY gm.guildid) pm ON pm.guildid = g.id "
                    + "LEFT JOIN characterstats ldr ON ldr.characterid = g.leaderid "
                    + "LEFT JOIN characters lc ON lc.id = g.leaderid "
                    + "LEFT JOIN users lu ON lu.id = lc.userid "
                    + "ORDER BY g.level DESC, g.ggp DESC, members DESC LIMIT ?", lim)
                    : query("SELECT g.name AS name, g.level AS level, g.ggp AS ggp, "
                    + "COUNT(gm.id) AS members, ldr.name AS leader "
                    + "FROM guilds g "
                    + "LEFT JOIN guildmembers gm ON gm.guildid = g.id "
                    + "LEFT JOIN characterstats ldr ON ldr.characterid = g.leaderid "
                    + "GROUP BY g.id, g.name, g.level, g.ggp, ldr.name "
                    + "ORDER BY g.level DESC, g.ggp DESC, members DESC LIMIT ?", lim);
            List<Map<String, Object>> res = new ArrayList<>();
            for (var row : rows) {
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.put("name", str(row.get("name")));
                entry.put("level", num(row.get("level")));
                entry.put("ggp", num(row.get("ggp")));
                entry.put("members", num(row.get("members")));
                entry.put("leader", str(row.get("leader")));
                res.add(entry);
            }
            addRanks(res);
            return res;
        });
    }

    /**
     * @return how many characters play each class, most played first
     */
    public static List<Map<String, Object>> getClassPopularity() {
        return cached("classes", () -> {
            var rows = query("SELECT cs.job AS job, cs.subjob AS subjob, COUNT(*) AS players "
                    + CHAR_FROM + charWhere() + "GROUP BY cs.job, cs.subjob");
            Map<String, long[]> counts = new HashMap<>();
            Map<String, String> groups = new HashMap<>();
            long total = 0;
            for (var row : rows) {
                int job = (int) num(row.get("job"));
                int subJob = (int) num(row.get("subjob"));
                String name = JobPathModule.getClassName(job, subJob);
                long n = num(row.get("players"));
                counts.computeIfAbsent(name, k -> new long[1])[0] += n;
                groups.putIfAbsent(name, JobPathModule.getClassGroup(job, subJob));
                total += n;
            }
            List<Map<String, Object>> res = new ArrayList<>();
            for (var e : counts.entrySet()) {
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.put("className", e.getKey());
                entry.put("classGroup", groups.get(e.getKey()));
                entry.put("players", e.getValue()[0]);
                entry.put("percent", total == 0 ? 0 : Math.round(e.getValue()[0] * 1000.0 / total) / 10.0);
                res.add(entry);
            }
            res.sort((a, b) -> Long.compare((long) b.get("players"), (long) a.get("players")));
            return res;
        });
    }

    // ------------------------------------------------------------------------------------------------- lookup

    /**
     * @return a character's public profile, or null if there's no such character
     */
    public static Map<String, Object> getCharacter(String name) {
        if (name == null || name.isBlank() || name.length() > 16) {
            return null;
        }
        String key = "char:" + name.trim().toLowerCase();
        if (cache.size() > 2000) {
            cache.keySet().removeIf(k -> k.startsWith("char:")); // bound the cache (names come from the request)
        }
        Map<String, Object> res = cached(key, () -> lookupCharacter(name.trim()));
        if (res == null) {
            return null;
        }
        Map<String, Object> copy = new LinkedHashMap<>(res);
        copy.put("online", isOnline((String) res.get("name"))); // always live
        applyLiveStats(Collections.singletonList(copy)); // level and fame too, while the character is online
        return copy;
    }

    private static Map<String, Object> lookupCharacter(String name) {
        var rows = query("SELECT cs.name AS name, cs.level AS level, cs.job AS job, cs.subjob AS subjob, "
                + "cs.pop AS fame, CAST(cs.exp AS UNSIGNED) AS exp, g.name AS guild, c.questmanager AS qm "
                + CHAR_FROM + "LEFT JOIN guilds g ON g.id = c.guild "
                + charWhere() + "AND cs.name = ? LIMIT 1", name.trim());
        if (rows.isEmpty()) {
            return null;
        }
        var row = rows.get(0);
        var res = characterEntry(row);
        long level = num(row.get("level"));
        long exp = num(row.get("exp"));
        var rankRows = query("SELECT COUNT(*) AS higher " + CHAR_FROM + charWhere()
                + "AND (cs.level > ? OR (cs.level = ? AND CAST(cs.exp AS UNSIGNED) > ?))", level, level, exp);
        res.put("levelRank", rankRows.isEmpty() ? null : num(rankRows.get(0).get("higher")) + 1);
        var rebirthRows = query("SELECT q.qrvalue AS record FROM quests q WHERE q.questmanager_id = ? AND q.qrkey = ? "
                + "LIMIT 1", row.get("qm"), REBIRTH_QR);
        res.put("rebirths", rebirthRows.isEmpty() ? 0 : parseRebirthCount(str(rebirthRows.get(0).get("record"))));
        return res;
    }
}

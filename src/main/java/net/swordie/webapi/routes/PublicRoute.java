package net.swordie.webapi.routes;

import com.sun.net.httpserver.HttpExchange;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import net.swordie.ms.ServerConstants;
import net.swordie.webapi.site.SiteData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Public, read-only data for the server website and launcher. No login needed and no
 * account data: server status, who's online, rankings and character lookup. All database results are cached, see
 * {@link SiteData}.
 * <p>
 * GET /api/public/overview            server status, worlds/channels, rates
 * GET /api/public/features            the website's features and commands (fixed while the server runs)
 * GET /api/public/totals              accounts, characters, guilds, highest level
 * GET /api/public/online              characters online right now
 * GET /api/public/rankings/level      ?limit=50&group=Explorers (a class group, or all)
 * GET /api/public/rankings/fame       ?limit=50
 * GET /api/public/rankings/rebirths   ?limit=50
 * GET /api/public/rankings/guilds     ?limit=50
 * GET /api/public/classes             how many characters play each class
 * GET /api/public/character/{name}    a character's profile
 * GET /api/public/firstclears         the newest boss first clears (?limit=20)
 * GET /api/public/news                the launcher's news, known issues and slides (resources/news.json)
 * GET /api/public/news/image/{file}   a slide image (resources/news-images)
 */
@Path("/public")
public class PublicRoute {

    @GET
    @Path("/overview")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> getOverview() {
        return SiteData.getOverview();
    }

    @GET
    @Path("/news")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> getNews() {
        return net.swordie.webapi.site.News.get();
    }

    @GET
    @Path("/news/image/{file}")
    public jakarta.ws.rs.core.Response getNewsImage(@PathParam("file") String file) {
        byte[] data;
        try {
            data = net.swordie.webapi.site.News.image(file);
        } catch (java.io.IOException e) {
            data = null;
        }
        if (data == null) {
            throw new NotFoundException("No such image.");
        }
        // names are random and never reused, so the file under a name never changes
        return jakarta.ws.rs.core.Response.ok(data, net.swordie.webapi.site.News.contentType(file))
                .header("Cache-Control", "public, max-age=604800, immutable")
                .header("X-Content-Type-Options", "nosniff")
                .build();
    }

    @GET
    @Path("/features")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> getFeatures() {
        return SiteData.getFeatures();
    }

    @GET
    @Path("/totals")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> getTotals() {
        return SiteData.getTotals();
    }

    @GET
    @Path("/online")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> getOnline() {
        List<Map<String, Object>> players = SiteData.getOnlinePlayers();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("count", players.size());
        res.put("players", players);
        return res;
    }

    @GET
    @Path("/rankings/level")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> getLevelRanking(@QueryParam("limit") int limit, @QueryParam("group") String group) {
        return SiteData.getLevelRanking(limit, group);
    }

    @GET
    @Path("/rankings/fame")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> getFameRanking(@QueryParam("limit") int limit) {
        return SiteData.getFameRanking(limit);
    }

    @GET
    @Path("/rankings/rebirths")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> getRebirthRanking(@QueryParam("limit") int limit) {
        return SiteData.getRebirthRanking(limit);
    }

    /** The stats a top-5 character had at its last logout (null when not in the top 5 or none kept yet). */
    @GET
    @Path("/rankings/stats")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> getRankingStats(@QueryParam("name") String name) {
        String json = net.swordie.webapi.site.RankingStats.getJson(name);
        return json == null ? new LinkedHashMap<>() : new org.json.JSONObject(json).toMap();
    }

    @GET
    @Path("/rankings/guilds")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> getGuildRanking(@QueryParam("limit") int limit) {
        return SiteData.getGuildRanking(limit);
    }

    @GET
    @Path("/firstclears")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> getFirstClears(@QueryParam("limit") int limit) {
        return SiteData.getRecentFirstClears(limit);
    }

    @GET
    @Path("/classes")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> getClasses() {
        return SiteData.getClassPopularity();
    }

    @GET
    @Path("/character/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> getCharacter(@PathParam("name") String name, @Context HttpExchange exchange) {
        if (!allowLookup(exchange)) {
            throw new ForbiddenException("Too many character lookups. Wait a minute and try again.");
        }
        var res = SiteData.getCharacter(name);
        if (res == null) {
            throw new NotFoundException("No character with that name.");
        }
        return res;
    }

    // Character lookups per address and minute: each new name runs database queries, and the web API's few threads
    // also serve logins, so one address can't flood it.
    private static final int MAX_LOOKUPS_PER_MINUTE = 60;
    private static final Map<String, long[]> lookups = new ConcurrentHashMap<>(); // address -> {count, minute start}

    private static boolean allowLookup(HttpExchange exchange) {
        return allowLookup(exchange, ServerConstants.WEB_TRUST_FORWARDED_FOR);
    }

    /** @param trustForwarded whose address a request counts as, the same rule as the login limits */
    static boolean allowLookup(HttpExchange exchange, boolean trustForwarded) {
        String address = LoginRoute.clientAddress(exchange, trustForwarded);
        long now = System.currentTimeMillis();
        if (lookups.size() > 10_000) {
            lookups.values().removeIf(v -> now - v[1] >= 60_000);
        }
        long[] count = lookups.compute(address, (k, v) -> v == null || now - v[1] >= 60_000
                ? new long[]{1, now} : new long[]{v[0] + 1, v[1]});
        return count[0] <= MAX_LOOKUPS_PER_MINUTE;
    }
}

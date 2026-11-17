package net.swordie.webapi.routes;

import net.swordie.ms.constants.GameConstants;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.swordie.ms.Server;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.StuckModule;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.world.World;
import net.swordie.webapi.ApiConstants;
import net.swordie.webapi.site.SiteData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The website's "unstuck" (account page): the logged-in player's characters, and moving one of them to Henesys
 * (see {@link StuckModule}; every use is logged in logs/stuck.log).
 * <p>
 * GET  /api/stuck             the characters of the logged-in login, with where they are and their boss
 *                             first clears
 * POST /api/stuck/{charId}    moves that character to Henesys (online: right away; offline: it logs in there)
 */
@Path("/stuck")
public class StuckRoute extends BaseResource {

    /** One unstuck per login per this many seconds. */
    private static final long COOLDOWN_SECONDS = 30;
    private static final Map<Integer, Long> lastUse = new ConcurrentHashMap<>();

    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> characters(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        var user = authorize(token);
        List<Map<String, Object>> res = new ArrayList<>();
        Map<Integer, List<Map<String, Object>>> clears = new java.util.HashMap<>();
        for (var row : SiteData.query("SELECT bfc.charid AS charid, bfc.boss AS boss, bfc.cleared_at AS clearedAt "
                + "FROM boss_first_clears bfc INNER JOIN characters c ON c.id = bfc.charid WHERE c.userid = ? "
                + "ORDER BY bfc.cleared_at", user.getId())) {
            if (row.get("charid") instanceof Number n) {
                Map<String, Object> clear = new LinkedHashMap<>();
                clear.put("boss", SiteData.bossDisplay(String.valueOf(row.get("boss"))));
                clear.put("time", row.get("clearedAt") == null ? null : String.valueOf(row.get("clearedAt")));
                clears.computeIfAbsent(n.intValue(), k -> new ArrayList<>()).add(clear);
            }
        }
        for (var row : SiteData.query("SELECT c.id AS id, cs.name AS name, cs.level AS level, cs.posmap AS map "
                + CHAR_STATS + "WHERE c.userid = ? ORDER BY cs.level DESC, cs.name", user.getId())) {
            int id = ((Number) row.get("id")).intValue();
            Char online = findOnline(id);
            long map = online != null ? (online.getField() != null ? online.getField().getId() : online.getFieldID())
                    : row.get("map") instanceof Number n ? n.longValue() : 0;
            Map<String, Object> c = new LinkedHashMap<>();
            c.put("id", id);
            c.put("name", String.valueOf(row.get("name")));
            c.put("level", row.get("level"));
            c.put("online", online != null);
            c.put("map", map);
            c.put("mapName", placeName(map, online));
            c.put("firstClears", clears.getOrDefault(id, List.of()));
            res.add(c);
        }
        return res;
    }

    @POST
    @Path("/{charId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> unstuck(@PathParam("charId") int charId,
                                       @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        var user = authorize(token);
        var rows = SiteData.query("SELECT c.userid AS userid, cs.name AS name, cs.posmap AS map "
                + CHAR_STATS + "WHERE c.id = ?", charId);
        if (rows.isEmpty() || !(rows.get(0).get("userid") instanceof Number owner)
                || owner.intValue() != user.getId()) {
            throw new ForbiddenException("That character isn't on your account.");
        }
        long now = System.currentTimeMillis();
        Long last = lastUse.get(user.getId());
        if (last != null && now - last < COOLDOWN_SECONDS * 1000) {
            throw new ForbiddenException("Please wait " + (COOLDOWN_SECONDS - (now - last) / 1000)
                    + " seconds before using this again.");
        }
        lastUse.put(user.getId(), now);
        String name = String.valueOf(rows.get(0).get("name"));
        Map<String, Object> res = new LinkedHashMap<>();
        Char online = findOnline(charId);
        if (online != null) {
            StuckModule.unstuck(online, "web");
            res.put("message", name + " was moved to Henesys.");
        } else {
            long map = rows.get(0).get("map") instanceof Number n ? n.longValue() : 0;
            if (!StuckModule.unstuckOffline(charId, user.getName(), name, map, "web")) {
                throw new BadRequestException("Could not move " + name + ". Please try again.");
            }
            res.put("message", name + " will be in Henesys the next time you log in with them.");
        }
        return res;
    }

    /**
     * A character's own stats row is the one its avatar data points at (the same join the game uses to load it);
     * matching characterstats.characterid could pick up a stale row and show an old map.
     */
    private static final String CHAR_STATS = "FROM characters c INNER JOIN avatardata ad ON ad.id = c.avatardata "
            + "INNER JOIN characterstats cs ON cs.id = ad.characterstat ";

    /** "Street : Map" for a map id, or what the character is doing when it isn't in a map. */
    private static String placeName(long map, Char online) {
        if (online != null && online.isInCashShop()) {
            return "Cash Shop";
        }
        if (map <= 0 || map >= GameConstants.NO_MAP_ID) {
            return online != null ? "Changing channel" : "Unknown (logs in at the last town)";
        }
        String name = StringData.getMapStringById((int) map);
        return name == null ? "Map " + map : name.replace(" : ", " - ");
    }

    private static Char findOnline(int charId) {
        for (World world : Server.getInstance().getWorlds()) {
            Char chr = world.getCharByID(charId);
            if (chr != null) {
                return chr;
            }
        }
        return null;
    }
}

package net.swordie.webapi.routes;

import com.sun.net.httpserver.HttpExchange;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.swordie.ms.Server;
import net.swordie.ms.client.User;
import net.swordie.ms.util.LoginAttemptLimiter;
import net.swordie.webapi.ApiConstants;
import net.swordie.webapi.WebApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * POST /api/launch for the launcher: with the session from /api/login (Authorization header) it hands out a one-time
 * numeric game token for "MapleStory.exe WebStart &lt;token&gt;".
 * <p>
 * The token only starts the game (it is not a web API login) and has to be used within
 * {@link Server#LAUNCH_TOKEN_MINUTES} minutes. The game's first use ties it to that address; after that it lasts
 * {@link net.swordie.ms.ServerConstants#TOKEN_EXPIRY_TIME} minutes from its last use (see
 * {@link Server#getUserIdFromAuthToken(String, boolean, String)}).
 * <p>
 * Not registered when another route serves /launch.
 */
@Path("/launch")
@WebApi.FallbackRoute
public class PublicLaunchRoute extends BaseResource {
    private static final Logger log = LogManager.getLogger();

    public static final String LINK_SCHEME = "swordieplus";
    /** One game token per login per this many milliseconds. */
    private static final long COOLDOWN_MS = 5000;
    /** Unknown session tokens allowed per address within LoginAttemptLimiter's window. */
    private static final int MAX_BAD_SESSIONS = 20;
    private static final int TOKEN_MIN = 100_000_000;
    private static final int MAX_TRACKED = 10_000;

    private static final SecureRandom random = new SecureRandom();
    private static final Map<Integer, Long> lastUse = new ConcurrentHashMap<>();

    @POST
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> launch(@HeaderParam(ApiConstants.TOKEN_HEADER) String sessionToken,
                                      @Context HttpExchange exchange) {
        LoginRoute.requireSecure(exchange);
        String address = LoginRoute.clientAddress(exchange);
        String badKey = LoginAttemptLimiter.key(address, "#launch");
        long blocked = LoginAttemptLimiter.minutesBlocked(badKey, MAX_BAD_SESSIONS);
        if (blocked > 0) {
            throw new ForbiddenException("Too many tries. Try again in " + blocked + " minute(s).");
        }
        User user;
        try {
            user = authorize(sessionToken);
        } catch (ForbiddenException e) {
            LoginAttemptLimiter.recordFailure(badKey);
            throw new ForbiddenException("Your session has expired. Log in again.");
        }

        long now = System.currentTimeMillis();
        Long last = lastUse.get(user.getId());
        if (last != null && now - last < COOLDOWN_MS) {
            throw new ForbiddenException("Starting the game already - give it a few seconds.");
        }
        if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired()) {
            throw new ForbiddenException("This account is banned.");
        }
        if (LoginRoute.isPlaying(user)) {
            throw new ClientErrorException("This account is already logged in to the game. Log out of it first "
                    + "(if the game closed without logging out, wait a minute and try again).", Response.Status.CONFLICT);
        }
        if (lastUse.size() > MAX_TRACKED) {
            lastUse.values().removeIf(t -> now - t >= COOLDOWN_MS);
        }
        lastUse.put(user.getId(), now);

        // the v265 hook reads "WebStart <token>" as a decimal int: 9-10 digits
        String token = null;
        for (int i = 0; i < 16 && token == null; i++) {
            String candidate = String.valueOf(TOKEN_MIN + random.nextInt(Integer.MAX_VALUE - TOKEN_MIN));
            if (Server.getInstance().addLaunchToken(candidate, user.getId(), user.isPlayer(),
                    Server.LAUNCH_TOKEN_MINUTES)) {
                token = candidate;
            }
        }
        if (token == null) {
            throw new ClientErrorException("The server is busy. Try again in a moment.", Response.Status.TOO_MANY_REQUESTS);
        }
        log.info(String.format("[Launch] %s (login id %d) got a game token (%s)", user.getName(), user.getId(), address));

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("token", token);
        res.put("url", LINK_SCHEME + "://play/" + token);
        res.put("expiresInSeconds", Server.LAUNCH_TOKEN_MINUTES * 60);
        return res;
    }
}

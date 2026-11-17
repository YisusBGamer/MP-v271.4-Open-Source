package net.swordie.webapi.routes;

import com.sun.net.httpserver.HttpExchange;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.swordie.ms.Server;
import net.swordie.webapi.ApiConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The website's Play now button.
 * <p>
 * POST /api/launch (logged in on the website: Authorization header) -> a one-time game token and the
 * {@code swordieplus://play/<token>} link. The browser opens that link, Windows hands it to the Swordie Plus Launcher
 * (it registers the swordieplus: link type when it runs), and the launcher starts MapleStory.exe WebStart &lt;token&gt;.
 * The token only starts the game (it is not a website login) and must be used within
 * {@link Server#LAUNCH_TOKEN_MINUTES} minutes.
 */
@Path("/launch")
public class LaunchRoute extends BaseResource {
    private static final Logger log = LogManager.getLogger();

    public static final String LINK_SCHEME = "swordieplus";
    private static final int TOKEN_LENGTH = 10;
    /** One Play now per login per this many seconds. */
    private static final long COOLDOWN_MS = 5000;

    private static final SecureRandom random = new SecureRandom();
    private static final Map<Integer, Long> lastUse = new ConcurrentHashMap<>();

    @POST
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> launch(@HeaderParam(ApiConstants.TOKEN_HEADER) String sessionToken,
                                      @Context HttpExchange exchange) {
        LoginRoute.requireSecure(exchange);
        var user = authorize(sessionToken);

        long now = System.currentTimeMillis();
        Long last = lastUse.get(user.getId());
        if (last != null && now - last < COOLDOWN_MS) {
            throw new ForbiddenException("Starting the game already - give it a few seconds.");
        }
        if (LoginRoute.isPlaying(user)) {
            throw new ClientErrorException("This account is already logged in to the game. Log out of it first "
                    + "(if the game closed without logging out, wait a minute and try again).", Response.Status.CONFLICT);
        }
        lastUse.put(user.getId(), now);

        // the v265 hook reads "WebStart <token>" as a decimal int, so the token is a 9-10 digit number
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        token.append(100_000_000 + random.nextInt(Integer.MAX_VALUE - 100_000_000));
        Server.getInstance().addLaunchToken(token.toString(), user.getId(), user.isPlayer());
        log.info(String.format("[Launch] %s (login id %d) pressed Play now on the website (%s)",
                user.getName(), user.getId(), LoginRoute.clientAddress(exchange)));

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("token", token.toString());
        res.put("url", LINK_SCHEME + "://play/" + token);
        res.put("expiresInSeconds", Server.LAUNCH_TOKEN_MINUTES * 60);
        return res;
    }
}

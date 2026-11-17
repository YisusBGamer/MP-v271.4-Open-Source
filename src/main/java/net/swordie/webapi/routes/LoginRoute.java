package net.swordie.webapi.routes;

import com.sun.net.httpserver.HttpExchange;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.User;
import net.swordie.ms.util.LoginAttemptLimiter;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import net.swordie.webapi.ApiConstants;
import net.swordie.webapi.protocol.request.LoginRequest;
import net.swordie.webapi.protocol.result.LoginResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Path("/login")
public class LoginRoute {
    private static final Logger log = LogManager.getLogger();

    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);

    /** The caller's address (see {@link ServerConstants#WEB_TRUST_FORWARDED_FOR}). */
    static String clientAddress(HttpExchange exchange) {
        return clientAddress(exchange, ServerConstants.WEB_TRUST_FORWARDED_FOR);
    }

    /**
     * Contributed by Arixal (from GitLab).
     *
     * @param trustForwarded a proxy on this machine (nginx) forwards the requests: one from this machine counts as the
     *                       last X-Forwarded-For entry, the one that proxy appended (earlier ones are the visitor's own)
     */
    static String clientAddress(HttpExchange exchange, boolean trustForwarded) {
        if (exchange == null || exchange.getRemoteAddress() == null) {
            return "?";
        }
        var address = exchange.getRemoteAddress().getAddress();
        if (trustForwarded && address != null && address.isLoopbackAddress()) {
            String forwarded = exchange.getRequestHeaders().getFirst("X-Forwarded-For");
            if (forwarded != null && !forwarded.isBlank()) {
                String[] hops = forwarded.split(",");
                return hops[hops.length - 1].trim();
            }
        }
        return address == null ? "?" : address.getHostAddress();
    }

    /**
     * With {@link ServerConstants#WEB_REQUIRE_HTTPS}: refuses a request that carries a password unless it came over
     * HTTPS. Requests reach this server over plain HTTP; they count as secure when a proxy on this PC (Cloudflare
     * Tunnel, nginx) says the visitor used HTTPS (X-Forwarded-Proto), when a program on this PC sends them itself, or
     * (with {@link ServerConstants#WEB_ALLOW_HTTP_FROM_LAN}) when they come from the local network.
     */
    static void requireSecure(HttpExchange exchange) {
        if (!ServerConstants.WEB_REQUIRE_HTTPS || exchange == null || exchange.getRemoteAddress() == null) {
            return;
        }
        var address = exchange.getRemoteAddress().getAddress();
        if (address != null && address.isLoopbackAddress()) {
            var headers = exchange.getRequestHeaders();
            String forwardedFor = headers.getFirst("X-Forwarded-For");
            String cfIp = headers.getFirst("CF-Connecting-IP");
            if ((forwardedFor == null || forwardedFor.isBlank()) && (cfIp == null || cfIp.isBlank())) {
                return; // not through a proxy: a program on this PC
            }
            String proto = headers.getFirst("X-Forwarded-Proto");
            if (proto != null && proto.split(",")[0].trim().equalsIgnoreCase("https")) {
                return;
            }
        } else if (address != null && ServerConstants.WEB_ALLOW_HTTP_FROM_LAN
                && (address.isSiteLocalAddress() || address.isLinkLocalAddress())) {
            return;
        }
        throw new ForbiddenException("For your security, use the secure (https://) address of the server to log in "
                + "or make an account.");
    }

    @POST
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public LoginResult login(LoginRequest content, @Context HttpExchange exchange) {
        requireSecure(exchange);
        content.validate();

        String key = LoginAttemptLimiter.key(clientAddress(exchange), content.username);
        long minutes = LoginAttemptLimiter.minutesBlocked(key);
        if (minutes > 0) {
            throw new ForbiddenException("Too many failed logins. Try again in " + minutes + " minute(s).");
        }

        var username = content.username;
        var password = content.password;

        User user = userDao.getByName(username);
        boolean success = false;
        if (user != null) {
            success = user.isCorrectPassword(password);
        }
        if (!success) {
            LoginAttemptLimiter.recordFailure(key);
            throw new ForbiddenException("Invalid username/password combination.");
        }
        LoginAttemptLimiter.recordSuccess(key);

        if (content.game && isPlaying(user)) {
            // checked before starting the game: the game would only say "already connected"
            throw new ClientErrorException("This account is already logged in to the game. Log out of it first "
                    + "(if the game closed without logging out, wait a minute and try again).", Response.Status.CONFLICT);
        }

        // Generate token
        var token = UUID.randomUUID().toString().getBytes();
        Server.getInstance().addAuthToken(token, user.getId(), user.isPlayer());
        LoginResult result = new LoginResult(token, user);
        // sql/init/08_admin_account.sql makes admin / admin
        result.defaultPassword = "admin".equalsIgnoreCase(user.getName()) && "admin".equals(password);
        return result;
    }

    /**
     * @return whether the login is in the game right now (logged in with a client that's still connected; a login
     * whose client dropped is logged out by the game's own login, so that one may start the game)
     */
    static boolean isPlaying(User user) {
        Server server = Server.getInstance();
        if (!server.isUserLoggedIn(user)) {
            return false;
        }
        User live = server.getUserById(user.getId());
        if (live == null) {
            live = server.getUserFromTransfer(user.getId());
        }
        return live != null && !live.isClientDisconnected();
    }

    @DELETE
    @Path("")
    public void logout(@Context HttpExchange httpExhange) {
        var token = httpExhange.getRequestHeaders().getFirst(ApiConstants.TOKEN_HEADER);
        if (token != null) {
            Server.getInstance().removeUserFromAuthToken(token);
        }
    }

}

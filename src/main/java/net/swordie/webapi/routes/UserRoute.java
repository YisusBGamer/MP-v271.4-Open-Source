package net.swordie.webapi.routes;

import com.sun.net.httpserver.HttpExchange;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.MaxSlotsModule;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import net.swordie.webapi.ApiConstants;
import net.swordie.webapi.dto.UserDto;
import net.swordie.webapi.protocol.request.ChangePasswordRequest;
import net.swordie.webapi.protocol.request.CreateAccountRequest;
import net.swordie.webapi.protocol.result.GetUserResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("/users")
public class UserRoute extends BaseResource {

    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);

    public static final long RESET_COOLDOWN_MINUTES = 10; // 10 minutes
    public static final long RESET_KEY_VALID_TIME_MINUTES = 24 * 60; // 24 hours

    @POST
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createAccount(@Context HttpExchange exchange, CreateAccountRequest request) {
        if (!ServerConstants.WEB_REGISTRATION_ENABLED) {
            throw new ForbiddenException("Creating accounts is turned off on this server. Ask the server owner.");
        }
        LoginRoute.requireSecure(exchange);
        String address = LoginRoute.clientAddress(exchange);
        if (accountsCreatedRecently(address) >= ServerConstants.WEB_ACCOUNTS_PER_ADDRESS_PER_HOUR
                && ServerConstants.WEB_ACCOUNTS_PER_ADDRESS_PER_HOUR > 0) {
            throw new ForbiddenException("Too many accounts were made from your connection. Try again in an hour.");
        }

        synchronized (ACCOUNT_CREATION_LOCK) {
            request.validate(); // the name and email checks, under the lock so two sign-ups can't take one name
            User user = new User(request.username);
            user.setPasswordAndHash(request.password);
            user.setEmail(request.email);
            user.setCharacterSlots(MaxSlotsModule.newAccountCharacterSlots());

            user.setRegisterIp(address); // the visitor's address (through a proxy on this PC: the forwarded one)

            userDao.saveOrUpdate(user, null);
        }
        countAccountCreated(address);
    }

    /** Held from the name check to the save by every way of making an account (also the launcher API). */
    public static final Object ACCOUNT_CREATION_LOCK = new Object();

    private static final long ACCOUNT_WINDOW_MS = 60 * 60_000L;
    /** address -> {accounts made, start of its hour}: slows down automated sign-ups */
    private static final Map<String, long[]> accountsByAddress = new ConcurrentHashMap<>();

    /** shared with the launcher API (connection.api) */
    public static long accountsCreatedRecently(String address) {
        long[] v = accountsByAddress.get(address);
        return v == null || System.currentTimeMillis() - v[1] >= ACCOUNT_WINDOW_MS ? 0 : v[0];
    }

    public static void countAccountCreated(String address) {
        long now = System.currentTimeMillis();
        if (accountsByAddress.size() > 10_000) {
            accountsByAddress.values().removeIf(v -> now - v[1] >= ACCOUNT_WINDOW_MS);
        }
        accountsByAddress.compute(address, (k, v) -> v == null || now - v[1] >= ACCOUNT_WINDOW_MS
                ? new long[]{1, now} : new long[]{v[0] + 1, v[1]});
    }

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public GetUserResult createUserGet(@PathParam("userId") int userId, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        return new GetUserResult(UserDto.fromUser(authorize(token, userId)));
    }

    @PUT
    @Path("/{userId}/password")
    @Consumes({MediaType.APPLICATION_JSON})
    public void changePassword(ChangePasswordRequest request, @PathParam("userId") int userId,
                               @HeaderParam(ApiConstants.TOKEN_HEADER) String token, @Context HttpExchange exchange) {
        LoginRoute.requireSecure(exchange);
        request.validate();

        var user = authorize(token, userId);
        var oldPassword = request.currentPassword;
        var newPassword = request.newPassword;

        if (oldPassword == null || !user.isCorrectPassword(oldPassword)) {
            throw new ForbiddenException("Incorrect current password");
        }

        user.setPasswordAndHash(newPassword);
        userDao.saveProperties(user);
    }

}

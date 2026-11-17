package net.swordie.ms.handlers;

import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.MaxSlotsModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.ApiResponse;
import net.swordie.ms.connection.packet.ApiResponse.CharSummary;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.AccountCreateResult;
import net.swordie.ms.enums.ApiTokenResultType;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.util.LoginAttemptLimiter;
import net.swordie.ms.util.UserUtil;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.World;
import net.swordie.orm.connection.HikariCPDataSource;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import net.swordie.webapi.routes.UserRoute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * The launcher's TCP API (D:\Launcher style, v265 "X64" requests). Wire format: a big-endian int length, then a
 * little-endian short opcode and the fields; strings are a short byte count plus UTF-8.
 * <ul>
 * <li>102 login (name, password) -> byte result [+ int token]. The token is a launch token in the same store as the
 * website's Play now ({@link Server#addLaunchToken}), so "MapleStory.exe WebStart &lt;token&gt;" logs in through
 * SPECIAL_LOGIN_AUTH_REQUEST.</li>
 * <li>101 register (name, password, email) -> byte {@link AccountCreateResult}.</li>
 * <li>103 account overview (int token) -> byte result, NX, DP, VP, the top 3 characters by level.</li>
 * <li>106 account settings (int token, current password, new password, email) -> byte result.</li>
 * <li>100 (the v232 launcher's login) -> WrongVersion; 104 forgets a token.</li>
 * </ul>
 * Passwords are never logged.
 *
 * @author Sjonnie
 * Created on 10/5/2018.
 */
public class ApiRequestHandler {

    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);

    private static final Logger log = LogManager.getLogger();

    private static final int MAX_NAME_BYTES = 32;
    private static final int MAX_PASSWORD_BYTES = 72; // what BCrypt uses
    private static final int MAX_EMAIL_BYTES = 128;
    private static final int MAX_TOKEN_BYTES = 64;
    /** Failed logins (any name) or unknown tokens one address may send per {@link LoginAttemptLimiter} window. */
    private static final int MAX_FAILURES_PER_ADDRESS = 30;
    /** One password check per connection per this many ms. */
    private static final long PASSWORD_REQUEST_GAP_MS = 1000;
    private static final int TOKEN_MIN = 100_000_000;
    private static final int TOP_CHARACTERS = 3;

    private static final SecureRandom random = new SecureRandom();
    private static final Map<Client, Long> lastPasswordRequest = Collections.synchronizedMap(new WeakHashMap<>());

    /** A request whose fields don't fit the format: the connection is closed. */
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    private static String decodeString(InPacket inPacket, int maxBytes) {
        if (inPacket.getUnreadAmount() < 2) {
            throw new BadRequestException("truncated string");
        }
        int length = inPacket.decodeShort() & 0xFFFF;
        if (length > maxBytes || length > inPacket.getUnreadAmount()) {
            throw new BadRequestException("string too long");
        }
        return new String(inPacket.decodeArr(length), StandardCharsets.UTF_8);
    }

    private static boolean hasControlChars(String s) {
        return s.chars().anyMatch(Character::isISOControl);
    }

    /** @return false if this connection sent a password request less than a second ago */
    private static boolean passwordRequestAllowed(Client c) {
        long now = System.currentTimeMillis();
        Long last = lastPasswordRequest.put(c, now);
        return last == null || now - last >= PASSWORD_REQUEST_GAP_MS;
    }

    private static String addressKey(Client c) {
        return LoginAttemptLimiter.key(c.getIP(), "*");
    }

    public static void handleOldTokenRequest(Client c, InPacket inPacket) {
        c.write(ApiResponse.tokenRequestResult(ApiTokenResultType.WrongVersion, "", null));
    }

    public static void handleTokenRequest(Client c, InPacket inPacket) {
        String name = decodeString(inPacket, MAX_NAME_BYTES).trim();
        String password = decodeString(inPacket, MAX_PASSWORD_BYTES);
        String address = c.getIP();
        String limitKey = LoginAttemptLimiter.key(address, name);
        String addressKey = addressKey(c);
        if (!passwordRequestAllowed(c) || LoginAttemptLimiter.minutesBlocked(limitKey) > 0
                || LoginAttemptLimiter.minutesBlocked(addressKey, MAX_FAILURES_PER_ADDRESS) > 0) {
            c.write(ApiResponse.tokenRequestResultX64(ApiTokenResultType.TooManyRequest, 0));
            return;
        }
        User user = name.isEmpty() || hasControlChars(name) ? null : userDao.getByName(name);
        if (user == null || !user.isCorrectPassword(password)) {
            LoginAttemptLimiter.recordFailure(limitKey);
            LoginAttemptLimiter.recordFailure(addressKey);
            log.info(String.format("[Launcher] Failed login for \"%s\" from %s", name, address));
            c.write(ApiResponse.tokenRequestResultX64(ApiTokenResultType.InvalidUserPassCombination, 0));
            return;
        }
        LoginAttemptLimiter.recordSuccess(limitKey);
        if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired()) {
            c.write(ApiResponse.tokenRequestResultX64(ApiTokenResultType.Banned, 0));
            return;
        }
        if (isPlaying(user)) {
            c.write(ApiResponse.tokenRequestResultX64(ApiTokenResultType.AlreadyLogin, 0));
            return;
        }
        long minutes = ServerConstants.CLIENT_API_TOKEN_MINUTES > 0
                ? ServerConstants.CLIENT_API_TOKEN_MINUTES : Server.LAUNCH_TOKEN_MINUTES;
        // the hook reads "WebStart <token>" as a decimal int: 9-10 digits, as the website's Play now makes them
        int token = 0;
        for (int i = 0; i < 16 && token == 0; i++) {
            int candidate = TOKEN_MIN + random.nextInt(Integer.MAX_VALUE - TOKEN_MIN);
            if (Server.getInstance().addLaunchToken(String.valueOf(candidate), user.getId(), user.isPlayer(), minutes)) {
                token = candidate;
            }
        }
        if (token == 0) {
            c.write(ApiResponse.tokenRequestResultX64(ApiTokenResultType.TooManyRequest, 0));
            return;
        }
        log.info(String.format("[Launcher] %s (login id %d) logged in from %s", user.getName(), user.getId(), address));
        c.write(ApiResponse.tokenRequestResultX64(ApiTokenResultType.Success, token));
    }

    /** Whether the login is in the game with a client that's still connected (as the website's login checks). */
    private static boolean isPlaying(User user) {
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

    /** Same rules as the website's account creation (UserRoute), answered with the launcher's result codes. */
    public static void handleCreateAccountRequest(Client c, InPacket inPacket) {
        String name = decodeString(inPacket, MAX_NAME_BYTES).trim();
        String password = decodeString(inPacket, MAX_PASSWORD_BYTES);
        String email = UserUtil.optionalEmail(decodeString(inPacket, MAX_EMAIL_BYTES));
        String address = c.getIP();
        if (!passwordRequestAllowed(c)) {
            c.write(ApiResponse.createAccountResult(AccountCreateResult.Unknown));
            return;
        }
        if (!ServerConstants.WEB_REGISTRATION_ENABLED || !ServerConstants.CLIENT_API_REGISTRATION) {
            log.info(String.format("[Launcher] Refused an account creation (\"%s\") from %s: registration is off",
                    name, address));
            c.write(ApiResponse.createAccountResult(AccountCreateResult.Unknown));
            return;
        }
        int perHour = ServerConstants.WEB_ACCOUNTS_PER_ADDRESS_PER_HOUR;
        if (perHour > 0 && UserRoute.accountsCreatedRecently(address) >= perHour) {
            c.write(ApiResponse.createAccountResult(AccountCreateResult.IpAlreadyHasAccount));
            return;
        }
        if (!UserUtil.isValidUsername(name) || !UserUtil.isValidPassword(password)
                || (email != null && !UserUtil.isValidEmail(email))) {
            c.write(ApiResponse.createAccountResult(AccountCreateResult.Unknown));
            return;
        }
        AccountCreateResult result;
        synchronized (UserRoute.ACCOUNT_CREATION_LOCK) {
            if (userDao.getByName(name) != null) {
                result = AccountCreateResult.NameInUse;
            } else if (email != null && userDao.getByEmail(email) != null) {
                result = AccountCreateResult.Unknown;
            } else {
                User user = new User(name);
                user.setPasswordAndHash(password);
                user.setEmail(email);
                user.setCharacterSlots(MaxSlotsModule.newAccountCharacterSlots());
                user.setRegisterIp(address);
                userDao.saveOrUpdate(user, null);
                UserRoute.countAccountCreated(address);
                result = AccountCreateResult.Success;
            }
        }
        if (result == AccountCreateResult.Success) {
            log.info(String.format("[Launcher] Account \"%s\" created from %s", name, address));
        }
        c.write(ApiResponse.createAccountResult(result));
    }

    public static void handleAccountDataRequest(Client c, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 4) {
            throw new BadRequestException("no token");
        }
        String token = Integer.toUnsignedString(inPacket.decodeInt());
        String addressKey = addressKey(c);
        int userId = 0;
        if (LoginAttemptLimiter.minutesBlocked(addressKey, MAX_FAILURES_PER_ADDRESS) == 0) {
            userId = Server.getInstance().peekUserIdFromAuthToken(token);
            if (userId <= 0) {
                LoginAttemptLimiter.recordFailure(addressKey);
            }
        }
        if (userId <= 0) {
            c.write(ApiResponse.accountDataResult(false, 0, 0, 0, List.of(), null));
            return;
        }
        long nx = 0;
        List<CharSummary> chars = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT COALESCE(SUM(nxCredit), 0) + COALESCE(SUM(nxPrepaid), 0) FROM accounts WHERE userid = ?")) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nx = rs.getLong(1);
                    }
                }
            }
            String sql = "SELECT cs.name, cs.level, cs.job, cs.posmap FROM characters c "
                    + "INNER JOIN accounts a ON a.id = c.accid "
                    + "INNER JOIN users u ON u.id = a.userid "
                    + "INNER JOIN characterstats cs ON cs.characterid = c.id "
                    + "WHERE a.userid = ? " + ServerHooks.sqlExcludeAutomated("u") + ServerHooks.sqlExcludeUnlisted("u")
                    + "ORDER BY cs.level DESC, CAST(cs.exp AS UNSIGNED) DESC, c.id ASC LIMIT " + TOP_CHARACTERS;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int job = rs.getInt(3);
                        chars.add(new CharSummary(rs.getString(1), rs.getInt(2), job, jobName(job),
                                parseField(rs.getString(4))));
                    }
                }
            }
            con.rollback();
        } catch (SQLException e) {
            log.error("[Launcher] Account overview failed: " + e.getMessage());
            c.write(ApiResponse.accountDataResult(false, 0, 0, 0, List.of(), null));
            return;
        }
        // an account in the game has its live balance
        User live = Server.getInstance().getUserById(userId);
        if (live != null && live.getAccounts() != null && !live.getAccounts().isEmpty()) {
            nx = 0;
            for (Account account : live.getAccounts()) {
                nx += (long) account.getNxCredit() + account.getNxPrepaid();
            }
        }
        // no donation or vote points on this server
        User owner = userDao.getById(userId);
        String email = owner == null ? null : owner.getEmail();
        c.write(ApiResponse.accountDataResult(true, (int) Math.min(Integer.MAX_VALUE, Math.max(0, nx)), 0, 0, chars,
                email));
    }

    /**
     * Launcher account settings (106): int token, current password, new password, email.
     * A blank new password keeps the current one; the email is always taken as sent (blank clears it).
     * Answers {@link ApiResponse#accountUpdateResult(int)}.
     */
    public static void handleAccountUpdateRequest(Client c, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 4) {
            throw new BadRequestException("no token");
        }
        String token = Integer.toUnsignedString(inPacket.decodeInt());
        String currentPassword = decodeString(inPacket, MAX_PASSWORD_BYTES);
        String newPassword = decodeString(inPacket, MAX_PASSWORD_BYTES);
        String email = UserUtil.optionalEmail(decodeString(inPacket, MAX_EMAIL_BYTES));

        String addressKey = addressKey(c);
        if (LoginAttemptLimiter.minutesBlocked(addressKey, MAX_FAILURES_PER_ADDRESS) > 0) {
            c.write(ApiResponse.accountUpdateResult(5));
            return;
        }

        int userId = Server.getInstance().peekUserIdFromAuthToken(token);
        User user = userId <= 0 ? null : userDao.getById(userId);
        if (user == null) {
            c.write(ApiResponse.accountUpdateResult(1));
            return;
        }

        boolean changePassword = newPassword != null && !newPassword.isBlank();
        if (changePassword) {
            if (!UserUtil.isValidPassword(newPassword)) {
                c.write(ApiResponse.accountUpdateResult(3));
                return;
            }
            if (currentPassword == null || !user.isCorrectPassword(currentPassword)) {
                LoginAttemptLimiter.recordFailure(addressKey);
                c.write(ApiResponse.accountUpdateResult(2));
                return;
            }
        }

        if (email != null && !UserUtil.isValidEmail(email)) {
            c.write(ApiResponse.accountUpdateResult(3));
            return;
        }
        if (email != null && !email.equalsIgnoreCase(user.getEmail())) {
            User holder = userDao.getByEmail(email);
            if (holder != null && holder.getId() != user.getId()) {
                c.write(ApiResponse.accountUpdateResult(4));
                return;
            }
        }

        if (changePassword) {
            user.setPasswordAndHash(newPassword);
        }
        if (email == null ? user.getEmail() != null : !email.equalsIgnoreCase(user.getEmail())) {
            user.setEmail(email);
        }
        userDao.saveProperties(user);
        log.info(String.format("[Launcher] Account %d settings updated from %s%s", userId, c.getIP(),
                changePassword ? " (password)" : ""));
        c.write(ApiResponse.accountUpdateResult(0));
    }

    /** Players in game, per world and channel; used by the launcher's server card. No token needed. */
    public static void handleWorldStatsRequest(Client c, InPacket inPacket) {
        int total = 0;
        List<ApiResponse.WorldChannels> worlds = new ArrayList<>();
        for (World world : Server.getInstance().getWorlds()) {
            List<ApiResponse.ChannelPlayers> channels = new ArrayList<>();
            for (Channel channel : world.getChannels()) {
                channels.add(new ApiResponse.ChannelPlayers(channel.getChannelId(), channel.getChars().size()));
            }
            total += world.getPlayerCount();
            worlds.add(new ApiResponse.WorldChannels(world.getWorldId().getVal(), channels));
        }
        c.write(ApiResponse.worldStatsResult(true, total, worlds));
    }

    public static void handleLogoutRequest(Client c, InPacket inPacket) {
        Server.getInstance().removeUserFromAuthToken(decodeString(inPacket, MAX_TOKEN_BYTES));
    }

    private static int parseField(String posMap) {
        try {
            return posMap == null ? 0 : (int) Long.parseLong(posMap.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** "DUAL_BLADE_4" -> "Dual Blade" */
    private static String jobName(int jobId) {
        JobConstants.JobEnum job = JobConstants.getJobEnumById(jobId);
        if (job == null) {
            return "";
        }
        String[] words = job.name().replaceAll("_\\d+$", "").toLowerCase(Locale.ROOT).split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
        }
        return sb.toString();
    }
}

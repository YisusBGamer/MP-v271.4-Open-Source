package net.swordie.webapi.routes;

import net.swordie.ms.constants.FieldConstants;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.JobPathModule;
import net.swordie.ms.connection.db.DbBackup;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.AccountType;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.UserUtil;
import net.swordie.orm.connection.HikariCPDataSource;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import net.swordie.webapi.ApiConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Admin tools for the launcher's database page. Every call needs the login token of an
 * account with account type Admin, sent in the Authorization header (the website's login gives one).
 * <p>
 * GET    /api/admin/info                       database name/version, latest migration, players online
 * GET    /api/admin/schema                     every table with its columns and (estimated) row count
 * GET    /api/admin/users?q=                   logins (by name, email, id or a character's name), max 200
 * GET    /api/admin/users/{id}                 a login with its world accounts and characters
 * PUT    /api/admin/users/{id}/account-type    {"accountType": "Admin"}
 * PUT    /api/admin/users/{id}/password        {"password": "..."}
 * PUT    /api/admin/users/{id}/character-slots {"characterSlots": 40}
 * PUT    /api/admin/users/{id}/ban             {"days": 7, "reason": "..."} (days 0 = permanent)
 * DELETE /api/admin/users/{id}/ban
 * DELETE /api/admin/users/{id}                 only a login without characters that isn't playing
 * GET    /api/admin/rates                      server rates: in effect now, and saved for the next start
 * PUT    /api/admin/rates                      {"values": {"exp": 150, ...}} saved for the next start
 * POST   /api/admin/sql                        {"sql": "SELECT ...", "write": false}
 * PUT    /api/admin/news                       {"news": [...], "knownIssues": [...], "slides": [...]} (any of them)
 * POST   /api/admin/news/image                 {"data": "<base64 image>"} a slide image, returns {name, url}
 * <p>
 * Changes to a login that is playing are made on the server's copy too, so they aren't overwritten when it saves.
 * Raw SQL isn't: the page warns about that.
 */
@Path("/admin")
public class AdminRoute extends BaseResource {

    private static final Logger log = LogManager.getLogger();
    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);

    /** Most rows a query returns to the page. */
    private static final int MAX_ROWS = 1000;
    private static final int QUERY_TIMEOUT_SECONDS = 30;
    /** Statements the query box runs without "allow changes" (also run in a read-only transaction). */
    private static final Set<String> READ_STATEMENTS = Set.of("SELECT", "SHOW", "DESCRIBE", "DESC", "EXPLAIN", "WITH");

    // ------------------------------------------------------------------------------------------------------ info

    @GET
    @Path("/info")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> info(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("you", admin.getName());
        res.put("serverName", ServerConstants.SERVER_NAME);
        res.put("serverVersion", ServerConstants.VERSION + "." + ServerConstants.MINOR_VERSION);
        // social bots count toward channel population in game, but this page is about real players
        res.put("playersOnline", Math.max(0, Server.getInstance().getPlayerCount()
                - net.swordie.ms.ext.ServerHooks.automatedOnline()));
        // by character: bots are the ones the server plays itself (journey, rebirth, classic, social, probe)
        int humans = 0;
        int bots = 0;
        for (var chr : Server.getInstance().getAllOnlineCharacters()) {
            if (net.swordie.ms.ext.ServerHooks.isAutomated(chr)) {
                bots++;
            } else {
                humans++;
            }
        }
        res.put("humans", humans);
        res.put("bots", bots);
        try (Connection con = HikariCPDataSource.getConnection()) {
            res.put("database", single(con, "SELECT DATABASE()"));
            res.put("mysqlVersion", single(con, "SELECT VERSION()"));
            res.put("latestMigration", single(con,
                    "SELECT CONCAT(version, ' (', script_name, ')') FROM migration_history ORDER BY version DESC LIMIT 1"));
            con.rollback();
        } catch (SQLException e) {
            log.error("Admin page: could not read database info", e);
        }
        List<String> types = new ArrayList<>();
        for (AccountType t : AccountType.values()) {
            types.add(t.name());
        }
        res.put("accountTypes", types);
        res.put("maxRows", MAX_ROWS);
        return res;
    }

    @GET
    @Path("/schema")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> schema(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        Map<String, Map<String, Object>> tables = new TreeMap<>();
        try (Connection con = HikariCPDataSource.getConnection()) {
            for (Map<String, Object> r : rows(con, "SELECT table_name AS t, table_rows AS n FROM information_schema.tables "
                    + "WHERE table_schema = DATABASE()")) {
                Map<String, Object> table = new LinkedHashMap<>();
                table.put("table", r.get("t"));
                table.put("rows", r.get("n"));
                table.put("columns", new ArrayList<Map<String, Object>>());
                tables.put(String.valueOf(r.get("t")), table);
            }
            for (Map<String, Object> r : rows(con, "SELECT table_name AS t, column_name AS c, column_type AS type, "
                    + "column_key AS k FROM information_schema.columns WHERE table_schema = DATABASE() "
                    + "ORDER BY table_name, ordinal_position")) {
                Map<String, Object> table = tables.get(String.valueOf(r.get("t")));
                if (table == null) {
                    continue;
                }
                Map<String, Object> col = new LinkedHashMap<>();
                col.put("name", r.get("c"));
                col.put("type", r.get("type"));
                col.put("key", r.get("k"));
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> cols = (List<Map<String, Object>>) table.get("columns");
                cols.add(col);
            }
            con.rollback();
        } catch (SQLException e) {
            throw new BadRequestException("MySQL: " + e.getMessage());
        }
        return new ArrayList<>(tables.values());
    }

    // ----------------------------------------------------------------------------------------------------- users

    private static final String USER_SELECT = "SELECT u.id, u.name, u.email, u.accounttype, u.characterslots, "
            + "u.maplePoints, u.banExpireDate, u.banReason, u.creationdate, u.registerip, "
            + "(SELECT COUNT(*) FROM characters c INNER JOIN accounts a ON a.id = c.accid WHERE a.userid = u.id) "
            + "AS characters FROM users u ";

    @GET
    @Path("/users")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> users(@QueryParam("q") String q,
                                           @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        String search = q == null ? "" : q.trim();
        try (Connection con = HikariCPDataSource.getConnection()) {
            List<Map<String, Object>> list;
            // social bot logins (sbot_...) are left out of this page
            String noBots = net.swordie.ms.ext.ServerHooks.sqlExcludeAutomated("u");
            if (search.isEmpty()) {
                list = rows(con, USER_SELECT + "WHERE 1 = 1 " + noBots + "ORDER BY u.id LIMIT 200");
            } else {
                String like = "%" + search.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_") + "%";
                int id = -1;
                try {
                    id = Integer.parseInt(search);
                } catch (NumberFormatException ignored) {
                }
                list = rows(con, USER_SELECT + "WHERE (u.id = ? OR u.name LIKE ? OR u.email LIKE ? OR u.id IN "
                        + "(SELECT a.userid FROM characters c INNER JOIN accounts a ON a.id = c.accid "
                        + "INNER JOIN characterstats cs ON cs.characterid = c.id WHERE cs.name LIKE ?)) " + noBots
                        + "ORDER BY u.id LIMIT 200", id, like, like, like);
            }
            con.rollback();
            list.forEach(AdminRoute::describeUser);
            return list;
        } catch (SQLException e) {
            throw new BadRequestException("MySQL: " + e.getMessage());
        }
    }

    @GET
    @Path("/users/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> user(@PathParam("id") int id, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        try (Connection con = HikariCPDataSource.getConnection()) {
            List<Map<String, Object>> found = rows(con, USER_SELECT + "WHERE u.id = ? "
                    + net.swordie.ms.ext.ServerHooks.sqlExcludeAutomated("u"), id);
            if (found.isEmpty()) {
                throw new NotFoundException("There's no login with id " + id + ".");
            }
            Map<String, Object> user = describeUser(found.get(0));
            user.put("accounts", rows(con, "SELECT a.id, a.worldid, a.nxCredit, a.nxPrepaid, "
                    + "(SELECT COUNT(*) FROM characters c WHERE c.accid = a.id) AS characters "
                    + "FROM accounts a WHERE a.userid = ? ORDER BY a.worldid", id));
            List<Map<String, Object>> chars = rows(con, "SELECT c.id, cs.name, cs.level, cs.job, cs.subjob, "
                    + "a.worldid, cs.money AS mesos FROM characters c INNER JOIN accounts a ON a.id = c.accid "
                    + "INNER JOIN characterstats cs ON cs.characterid = c.id WHERE a.userid = ? "
                    + "ORDER BY cs.level DESC, c.id", id);
            for (Map<String, Object> c : chars) {
                try {
                    c.put("className", JobPathModule.getClassName(Integer.parseInt(String.valueOf(c.get("job"))),
                            Integer.parseInt(String.valueOf(c.get("subjob")))));
                } catch (Exception e) {
                    c.put("className", null);
                }
            }
            user.put("characterList", chars);
            con.rollback();
            return user;
        } catch (SQLException e) {
            throw new BadRequestException("MySQL: " + e.getMessage());
        }
    }

    /** Adds readable account type, ban state and whether the login is playing to a row of USER_SELECT. */
    private static Map<String, Object> describeUser(Map<String, Object> row) {
        try {
            int type = Integer.parseInt(String.valueOf(row.get("accounttype")));
            row.put("accountType", type >= 0 && type < AccountType.values().length
                    ? AccountType.values()[type].name() : "Unknown (" + type + ")");
        } catch (NumberFormatException e) {
            row.put("accountType", "Unknown");
        }
        Object ban = row.get("banExpireDate");
        boolean banned = false;
        if (ban != null) {
            try {
                banned = Timestamp.valueOf(String.valueOf(ban)).toLocalDateTime().isAfter(LocalDateTime.now());
            } catch (IllegalArgumentException e) {
                banned = true;
            }
        }
        row.put("banned", banned);
        try {
            row.put("online", Server.getInstance().getUserById(Integer.parseInt(String.valueOf(row.get("id")))) != null);
        } catch (Exception e) {
            row.put("online", false);
        }
        return row;
    }

    /** The server's copy of a playing login, else the one in the database. */
    private static User loadUser(int id) {
        User user = Server.getInstance().getUserById(id);
        if (user == null) {
            user = userDao.getById(id);
        }
        if (user == null) {
            throw new NotFoundException("There's no login with id " + id + ".");
        }
        return user;
    }

    private static Map<String, Object> message(String text) {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("message", text);
        return res;
    }

    private static boolean isOnline(User user) {
        return Server.getInstance().getUserById(user.getId()) != null;
    }

    @PUT
    @Path("/users/{id}/account-type")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> setAccountType(@PathParam("id") int id, Map<String, Object> body,
                                              @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        String name = body == null ? null : String.valueOf(body.get("accountType"));
        AccountType type = null;
        for (AccountType t : AccountType.values()) {
            if (t.name().equalsIgnoreCase(name)) {
                type = t;
            }
        }
        if (type == null) {
            throw new BadRequestException("Unknown account type: " + name);
        }
        if (id == admin.getId() && type != AccountType.Admin) {
            throw new BadRequestException("You can't take admin away from the login you're using here.");
        }
        User user = loadUser(id);
        user.setAccountType(type);
        userDao.saveProperties(user);
        log.info(String.format("[Admin] %s set the account type of %s (id %d) to %s", admin.getName(), user.getName(),
                id, type));
        return message(user.getName() + " is now " + type.name() + "."
                + (isOnline(user) ? " They're playing: relog to see the change in game." : ""));
    }

    @PUT
    @Path("/users/{id}/password")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> setPassword(@PathParam("id") int id, Map<String, Object> body,
                                           @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        String password = body == null || body.get("password") == null ? null : String.valueOf(body.get("password"));
        if (!UserUtil.isValidPassword(password)) {
            throw new BadRequestException("The password must be at least 6 characters.");
        }
        User user = loadUser(id);
        user.setPasswordAndHash(password);
        userDao.saveProperties(user);
        log.info(String.format("[Admin] %s changed the password of %s (id %d)", admin.getName(), user.getName(), id));
        return message("Password of " + user.getName() + " changed.");
    }

    @PUT
    @Path("/users/{id}/character-slots")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> setCharacterSlots(@PathParam("id") int id, Map<String, Object> body,
                                                 @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        int slots;
        try {
            slots = Integer.parseInt(String.valueOf(body == null ? null : body.get("characterSlots")));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Enter a number of character slots.");
        }
        if (slots < 1 || slots > ServerConstants.MAX_CHARACTERS) {
            throw new BadRequestException("Character slots must be 1-" + ServerConstants.MAX_CHARACTERS + ".");
        }
        User user = loadUser(id);
        user.setCharacterSlots(slots);
        userDao.saveProperties(user);
        log.info(String.format("[Admin] %s set the character slots of %s (id %d) to %d", admin.getName(),
                user.getName(), id, slots));
        return message(user.getName() + " now has " + slots + " character slots.");
    }

    @PUT
    @Path("/users/{id}/ban")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> ban(@PathParam("id") int id, Map<String, Object> body,
                                   @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        if (id == admin.getId()) {
            throw new BadRequestException("You can't ban the login you're using here.");
        }
        int days;
        try {
            days = Integer.parseInt(String.valueOf(body == null ? null : body.get("days")));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Enter the number of days (0 = permanent).");
        }
        if (days > 36500) {
            // the date must fit in the database (a bad date makes every later save of this login fail)
            throw new BadRequestException("At most 36500 days (use 0 for permanent).");
        }
        String reason = body.get("reason") == null ? "" : String.valueOf(body.get("reason")).trim();
        User user = loadUser(id);
        user.setBanExpireDate(days <= 0 ? FileTime.fromDate(LocalDateTime.now().plusYears(100))
                : FileTime.fromDate(LocalDateTime.now().plusDays(days)));
        user.setBanReason(reason.isEmpty() ? "Banned by an admin" : reason);
        userDao.saveProperties(user);
        log.info(String.format("[Admin] %s banned %s (id %d) for %s: %s", admin.getName(), user.getName(), id,
                days <= 0 ? "ever" : days + " days", reason));
        return message(user.getName() + " is banned " + (days <= 0 ? "permanently" : "for " + days + " days") + "."
                + (isOnline(user) ? " They're playing right now: the ban stops their next login." : ""));
    }

    @DELETE
    @Path("/users/{id}/ban")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> unban(@PathParam("id") int id, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        User user = loadUser(id);
        user.setBanExpireDate(null);
        user.setBanReason(null);
        userDao.saveProperties(user);
        log.info(String.format("[Admin] %s unbanned %s (id %d)", admin.getName(), user.getName(), id));
        return message(user.getName() + " is no longer banned.");
    }

    @DELETE
    @Path("/users/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> delete(@PathParam("id") int id, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        if (id == admin.getId()) {
            throw new BadRequestException("You can't delete the login you're using here.");
        }
        User user = loadUser(id);
        if (isOnline(user)) {
            throw new BadRequestException(user.getName() + " is playing right now.");
        }
        try (Connection con = HikariCPDataSource.getConnection()) {
            Object chars = single(con, "SELECT COUNT(*) FROM characters c INNER JOIN accounts a ON a.id = c.accid "
                    + "WHERE a.userid = " + id);
            Object chars2 = single(con, "SELECT COUNT(*) FROM characters WHERE userid = " + id);
            if (!"0".equals(String.valueOf(chars)) || !"0".equals(String.valueOf(chars2))) {
                con.rollback();
                throw new BadRequestException(user.getName() + " still has characters. Only logins without "
                        + "characters can be deleted here.");
            }
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            throw new BadRequestException("MySQL: " + e.getMessage());
        }
        log.info(String.format("[Admin] %s deleted the login %s (id %d)", admin.getName(), user.getName(), id));
        return message("Login " + user.getName() + " deleted.");
    }

    // ----------------------------------------------------------------------------------------------------- rates

    @GET
    @Path("/rates")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> rates(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        return ratesResult(null);
    }

    /**
     * Saves rates for the next start: {"values": {"exp": 150, "drop": 20, ...}} (rates left out are kept).
     */
    @PUT
    @Path("/rates")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> setRates(Map<String, Object> body, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        Object rawValues = body == null ? null : body.get("values");
        if (!(rawValues instanceof Map)) {
            throw new BadRequestException("No rates were sent.");
        }
        Map<ServerRates.Rate, Double> values = new EnumMap<>(ServerRates.Rate.class);
        for (Map.Entry<?, ?> e : ((Map<?, ?>) rawValues).entrySet()) {
            ServerRates.Rate rate = ServerRates.Rate.getByKey(String.valueOf(e.getKey()));
            if (rate == null) {
                throw new BadRequestException("Unknown rate: " + e.getKey());
            }
            double value;
            try {
                value = Double.parseDouble(String.valueOf(e.getValue()).trim());
            } catch (NumberFormatException ex) {
                throw new BadRequestException(rate.getLabel() + " must be a number.");
            }
            if (Double.isNaN(value) || value < rate.getMin() || value > rate.getMax()) {
                throw new BadRequestException(String.format("%s must be between %s and %s.", rate.getLabel(),
                        fmtRate(rate.getMin()), fmtRate(rate.getMax())));
            }
            if (!rate.isDecimal() && value != Math.rint(value)) {
                throw new BadRequestException(rate.getLabel() + " must be a whole number.");
            }
            values.put(rate, value);
        }
        if (values.isEmpty()) {
            throw new BadRequestException("No rates were sent.");
        }
        try (Connection con = HikariCPDataSource.getConnection()) {
            try {
                ServerRates.save(con, values);
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            log.error("Admin page: could not save the server rates", e);
            throw new BadRequestException("MySQL: " + e.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<ServerRates.Rate, Double> e : values.entrySet()) {
            sb.append(sb.length() == 0 ? "" : ", ").append(e.getKey().getKey()).append(" x").append(fmtRate(e.getValue()));
        }
        log.info(String.format("[Admin] %s saved the server rates for the next start: %s", admin.getName(), sb));
        return ratesResult("Saved. The new rates take effect the next time the server starts (the buffed channel "
                + "settings within a minute).");
    }

    private Map<String, Object> ratesResult(String message) {
        Map<ServerRates.Rate, Double> saved;
        try (Connection con = HikariCPDataSource.getConnection()) {
            saved = ServerRates.readSaved(con);
            con.rollback();
        } catch (SQLException e) {
            throw new BadRequestException("Could not read the server_rates table (MySQL: " + e.getMessage()
                    + "). It's made by the V88 migration when the server starts.");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (ServerRates.Rate rate : ServerRates.Rate.values()) {
            Map<String, Object> r = new LinkedHashMap<>();
            r.put("key", rate.getKey());
            r.put("label", rate.getLabel());
            r.put("description", rate.getDescription());
            r.put("active", ServerRates.get(rate));
            r.put("saved", saved.getOrDefault(rate, rate.getDefaultValue()));
            r.put("default", rate.getDefaultValue());
            r.put("min", rate.getMin());
            r.put("max", rate.getMax());
            r.put("decimal", rate.isDecimal());
            list.add(r);
        }
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("rates", list);
        if (message != null) {
            res.put("message", message);
        }
        return res;
    }

    private static String fmtRate(double v) {
        return v == Math.rint(v) ? String.valueOf((long) v) : String.valueOf(v);
    }

    // ------------------------------------------------------------------------------------------------------- sql

    @POST
    @Path("/sql")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> sql(Map<String, Object> body, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        String sql = body == null || body.get("sql") == null ? "" : String.valueOf(body.get("sql")).trim();
        while (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1).trim();
        }
        if (sql.isEmpty()) {
            throw new BadRequestException("Enter a query.");
        }
        if (sql.contains(";")) {
            throw new BadRequestException("One statement at a time, please (no ';' inside the query).");
        }
        boolean write = Boolean.TRUE.equals(body.get("write"));
        String first = firstWord(sql);
        if (!write && !READ_STATEMENTS.contains(first)) {
            throw new BadRequestException(first + " changes the database: tick \"Allow changes\" to run it.");
        }
        log.info(String.format("[Admin SQL] %s (%s): %s", admin.getName(), write ? "write" : "read", sql));

        Map<String, Object> res = new LinkedHashMap<>();
        try (Connection con = HikariCPDataSource.getConnection()) {
            con.setReadOnly(!write);
            try (Statement st = con.createStatement()) {
                st.setQueryTimeout(QUERY_TIMEOUT_SECONDS);
                st.setMaxRows(MAX_ROWS + 1);
                long start = System.nanoTime();
                boolean hasResult = st.execute(sql);
                if (hasResult) {
                    try (ResultSet rs = st.getResultSet()) {
                        ResultSetMetaData md = rs.getMetaData();
                        List<String> columns = new ArrayList<>();
                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            columns.add(md.getColumnLabel(i));
                        }
                        List<List<String>> data = new ArrayList<>();
                        boolean truncated = false;
                        while (rs.next()) {
                            if (data.size() >= MAX_ROWS) {
                                truncated = true;
                                break;
                            }
                            List<String> row = new ArrayList<>();
                            for (int i = 1; i <= columns.size(); i++) {
                                row.add(value(rs, i, md.getColumnType(i)));
                            }
                            data.add(row);
                        }
                        res.put("columns", columns);
                        res.put("rows", data);
                        res.put("truncated", truncated);
                    }
                } else {
                    res.put("updated", st.getUpdateCount());
                }
                res.put("ms", (System.nanoTime() - start) / 1_000_000);
                if (write) {
                    con.commit();
                } else {
                    con.rollback();
                }
            } catch (SQLException e) {
                con.rollback();
                throw new BadRequestException("MySQL: " + e.getMessage());
            } finally {
                con.setReadOnly(false);
            }
        } catch (SQLException e) {
            throw new BadRequestException("MySQL: " + e.getMessage());
        }
        return res;
    }

    private static String firstWord(String sql) {
        String s = sql.replaceAll("(?s)^(\\s|/\\*.*?\\*/|--[^\\n]*\\n|#[^\\n]*\\n)*", "").trim();
        int end = 0;
        while (end < s.length() && Character.isLetter(s.charAt(end))) {
            end++;
        }
        return s.substring(0, end).toUpperCase(Locale.ROOT);
    }

    // --------------------------------------------------------------------------------------------------- helpers

    private static String value(ResultSet rs, int i, int sqlType) throws SQLException {
        switch (sqlType) {
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB: {
                byte[] bytes = rs.getBytes(i);
                return bytes == null ? null : "<" + bytes.length + " bytes>";
            }
            default:
                return rs.getString(i);
        }
    }

    private static Object single(Connection con, String sql) {
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getString(1) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    private static List<Map<String, Object>> rows(Connection con, String sql, Object... args) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setQueryTimeout(QUERY_TIMEOUT_SECONDS);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int c = 1; c <= md.getColumnCount(); c++) {
                        row.put(md.getColumnLabel(c), value(rs, c, md.getColumnType(c)));
                    }
                    list.add(row);
                }
            }
        }
        return list;
    }

    // --------------------------------------------------------------------------------------------------- control
    // the server's own control files (control/), from the page

    private static final java.nio.file.Path CONTROL = java.nio.file.Paths.get(ServerConstants.DIR, "control");

    private static String readText(java.nio.file.Path p) {
        try {
            return java.nio.file.Files.exists(p) ? java.nio.file.Files.readString(p) : "";
        } catch (Exception e) {
            return "";
        }
    }

    /** The server's state: status.txt, the last build, recent errors in the console log, and any bot module status. */
    @GET
    @Path("/control/status")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> controlStatus(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("status", readText(CONTROL.resolve("status.txt")));
        res.put("lastBuild", readText(java.nio.file.Paths.get(ServerConstants.DIR, "logs", "build", "last.txt")));
        res.putAll(net.swordie.ms.socialbot.SocialBotHooks.status());
        var backup = DbBackup.newest();
        res.put("lastBackup", backup == null ? "" : backup.getFileName().toString());
        List<String> errors = new ArrayList<>();
        try {
            java.nio.file.Path logs = java.nio.file.Paths.get(ServerConstants.DIR, "logs", "console");
            java.nio.file.Path newest = null;
            try (var s = java.nio.file.Files.list(logs)) {
                newest = s.filter(p -> p.getFileName().toString().startsWith("console-"))
                        .max(Comparator.comparingLong(p -> p.toFile().lastModified())).orElse(null);
            }
            if (newest != null) {
                List<String> lines = java.nio.file.Files.readAllLines(newest);
                for (int i = Math.max(0, lines.size() - 20000); i < lines.size(); i++) {
                    String l = lines.get(i);
                    if ((l.contains("Exception") || l.startsWith("[ERROR]")) && !l.contains("Could not find")) {
                        errors.add(l.length() > 300 ? l.substring(0, 300) : l);
                    }
                }
            }
        } catch (Exception e) {
            errors.add("Couldn't read the console log: " + e);
        }
        res.put("recentErrors", errors.subList(Math.max(0, errors.size() - 15), errors.size()));
        return res;
    }

    /**
     * Server actions: update (build the newest code, then restart as POST /control/restart does), build (only build,
     * used at the next restart), reloadScripts, clearCache (drops, shops, item/mob data from the database
     * and files), unstuck (frees a character: closes its chat, sends it to Henesys), saveAll (saves every online
     * character now and returns the counts; HTTP 500 if any save failed), backup (a database backup now, as the
     * timed ones; 409 without mysqldump, 500 if the dump failed).
     * Body: {"minutes": warning minutes for update, "name": the character for unstuck}.
     */
    @POST
    @Path("/control/{action}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> control(@PathParam("action") String action, Map<String, Object> body,
                                       @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        int minutes = 0;
        if (body != null && body.get("minutes") instanceof Number n) {
            minutes = Math.max(0, Math.min(net.swordie.ms.world.RestartManager.MAX_MINUTES, n.intValue()));
        }
        String msg;
        Map<String, Object> extra = new LinkedHashMap<>();
        try {
            switch (action) {
                case "saveAll" -> {
                    var r = net.swordie.ms.world.AutoSave.saveAll(0);
                    extra.putAll(r.toMap());
                    msg = "Saved " + r.summary() + ".";
                    if (r.failed > 0) {
                        throw new InternalServerErrorException(msg);
                    }
                }
                case "backup" -> {
                    try {
                        var zip = DbBackup.backup(DbBackup.Kind.MANUAL);
                        extra.put("file", zip.getFileName().toString());
                        extra.put("bytes", java.nio.file.Files.size(zip));
                        msg = "Backed up the database to " + zip + ".";
                    } catch (DbBackup.NotAvailableException e) {
                        throw new ClientErrorException(e.getMessage(), 409);
                    } catch (java.io.IOException e) {
                        log.error("[Backup] by hand for " + admin.getName() + " failed: " + e.getMessage());
                        throw new InternalServerErrorException("The backup failed: " + e.getMessage());
                    }
                }
                case "update" -> {
                    // build, then restart with a warning (whenEmpty: {"mode": "whenEmpty"})
                    String mode = body != null && "whenEmpty".equals(body.get("mode")) ? "whenEmpty" : "timed";
                    extra.putAll(requestRestart(mode, minutes, true, admin));
                    msg = "Building the newest code; if the build is OK the server restarts "
                            + (mode.equals("timed") ? "after a " + minutes + " minute warning." : "once every player is offline.");
                }
                case "build" -> {
                    try {
                        extra.putAll(net.swordie.ms.world.RestartManager.buildOnly(admin.getName()));
                    } catch (net.swordie.ms.world.RestartManager.Refused e) {
                        throw new ClientErrorException(e.getMessage(), 409);
                    }
                    msg = "Building the newest code; it's used at the next restart (logs/build/last.txt).";
                }
                case "reloadScripts" -> {
                    net.swordie.ms.scripts.ScriptManagerImpl.clear();
                    msg = "Scripts reloaded: the next run of each script reads its file again.";
                }
                case "reloadDailyGift" -> {
                    msg = net.swordie.ms.client.character.DailyGiftModule.reload()
                            ? "Daily Gift calendar reloaded: players see it at their next login."
                            : "The Daily Gift tables couldn't be read; the calendar in effect was kept.";
                }
                case "clearCache" -> {
                    Server.getInstance().clearCache();
                    msg = "Data cache cleared: drops, shops and game data are read again as they're needed.";
                }
                case "unstuck" -> {
                    String name = body == null || body.get("name") == null ? "" : String.valueOf(body.get("name")).trim();
                    if (name.isEmpty()) {
                        throw new BadRequestException("Type the character's name.");
                    }
                    Char chr = null;
                    for (var w : Server.getInstance().getWorlds()) {
                        chr = chr != null ? chr : w.getCharByName(name);
                    }
                    if (chr == null) {
                        throw new BadRequestException(name + " isn't online.");
                    }
                    chr.getScriptManager().dispose(false);
                    chr.setInstance(null);
                    chr.warp(FieldConstants.HENESYS_FIELD_ID);
                    msg = chr.getName() + " was freed and sent to Henesys.";
                }
                case "command" -> {
                    // runs a GM command ("!spawn 8881100") as an online character, as if typed in chat
                    String name = body == null || body.get("name") == null ? "" : String.valueOf(body.get("name")).trim();
                    String text = body == null || body.get("text") == null ? "" : String.valueOf(body.get("text")).trim();
                    char prefix = net.swordie.ms.client.character.commands.AdminCommand.getPrefix();
                    if (name.isEmpty() || text.isEmpty() || text.charAt(0) != prefix) {
                        throw new BadRequestException("Give the character's name and a command starting with " + prefix + ".");
                    }
                    Char chr = null;
                    for (var w : Server.getInstance().getWorlds()) {
                        chr = chr != null ? chr : w.getCharByName(name);
                    }
                    if (chr == null) {
                        throw new BadRequestException(name + " isn't online.");
                    }
                    net.swordie.ms.handlers.social.ChatHandler.executeAdminCommand(chr.getClient(), chr, text);
                    msg = "Ran " + text + " as " + chr.getName() + ".";
                }
                default -> {
                    msg = net.swordie.ms.socialbot.SocialBotHooks.controlAction(action);
                    if (msg == null) {
                        throw new BadRequestException("Unknown action: " + action);
                    }
                }
            }
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Admin control " + action + " failed", e);
            throw new InternalServerErrorException("That didn't work: " + e.getMessage());
        }
        log.info("[Admin] " + admin.getName() + ": " + action);
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("ok", true);
        res.put("message", msg);
        res.putAll(extra);
        return res;
    }

    // ----------------------------------------------------------------------------------------------- restart

    private static Map<String, Object> requestRestart(String mode, int minutes, boolean build, User admin) {
        try {
            return net.swordie.ms.world.RestartManager.request(mode, minutes, build, admin.getName());
        } catch (net.swordie.ms.world.RestartManager.Refused e) {
            throw new ClientErrorException(e.getMessage(), 409);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /** Live server-side state of an online character, read from the existing getters (for test agents). */
    @GET
    @Path("/chrstate/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> chrState(@PathParam("name") String name, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        Char chr = null;
        for (var w : Server.getInstance().getWorlds()) {
            chr = chr != null ? chr : w.getCharByName(name);
        }
        if (chr == null) {
            throw new BadRequestException(name + " isn't online.");
        }
        long now = System.currentTimeMillis();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("name", chr.getName());
        res.put("fieldId", chr.getFieldID());
        res.put("inCashShop", chr.isInCashShop());
        res.put("exclusiveRequestPending", chr.isTalkingToNpc());
        List<Map<String, Object>> buffs = new ArrayList<>();
        chr.getTemporaryStatManager().getCurrentStats().forEach((cts, opts) -> {
            for (var o : opts) {
                Map<String, Object> b = new LinkedHashMap<>();
                b.put("stat", cts.name());
                b.put("id", o.rOption);
                b.put("n", o.nOption);
                b.put("remainingMs", o.tOption == 0 ? 0 : o.getRemainingTime());
                buffs.add(b);
            }
        });
        res.put("buffs", buffs);
        Map<String, Object> cooldowns = new LinkedHashMap<>();
        chr.getSkillCoolTimes().forEach((id, end) -> {
            if (end > now) {
                cooldowns.put(String.valueOf(id), end - now);
            }
        });
        res.put("cooldownsMs", cooldowns);
        List<Map<String, Object>> summons = new ArrayList<>();
        for (var s : new ArrayList<>(chr.getSummons())) {
            if (s == null) {
                continue; // the list can briefly hold a removed entry
            }
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("skillId", s.getSkillID());
            m.put("objectId", s.getObjectId());
            m.put("termMs", s.getSummonTerm());
            summons.add(m);
        }
        res.put("summons", summons);
        Map<String, Object> gauges = new LinkedHashMap<>();
        gauges.put("killCombo", chr.getComboCounter());
        gauges.put("deathCount", chr.getDeathCount());
        gauges.put("viperEnergyCharge", chr.getTemporaryStatManager().getViperEnergyCharge());
        res.put("gauges", gauges);
        List<Map<String, Object>> scripts = new ArrayList<>();
        chr.getScriptManager().getScripts().forEach((type, info) -> {
            if (info.isActive()) {
                scripts.add(Map.of("type", type.name(), "script", String.valueOf(info.getScriptName()),
                        "parentId", info.getParentID()));
            }
        });
        res.put("scripts", scripts);
        return res;
    }

    /** The pending restart, if any: {pending, mode, stage, minutes, build, by, requestedAt, secondsLeft, humans, ...}. */
    @GET
    @Path("/control/restart")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> restartStatus(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        return net.swordie.ms.world.RestartManager.status();
    }

    /**
     * {"mode": "whenEmpty" | "timed", "minutes": 10, "build": false}. whenEmpty waits until no player is online;
     * timed warns players at 10/5/1 min and 30 s, then saves everyone and restarts. 409 when it can't go ahead (a
     * restart already pending, players online with no warning, or no restart loop around the server).
     */
    @POST
    @Path("/control/restart")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> restart(Map<String, Object> body, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        String mode = body == null || body.get("mode") == null ? "timed" : String.valueOf(body.get("mode"));
        int minutes = body != null && body.get("minutes") instanceof Number n ? n.intValue() : 0;
        boolean build = body != null && Boolean.TRUE.equals(body.get("build"));
        Map<String, Object> res = requestRestart(mode, minutes, build, admin);
        log.info(String.format("[Admin] %s: restart %s%s%s", admin.getName(), mode,
                mode.equals("timed") ? " in " + minutes + " min" : "", build ? " after a build" : ""));
        return res;
    }

    @POST
    @Path("/control/restart/cancel")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> cancelRestart(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        boolean cancelled = net.swordie.ms.world.RestartManager.cancel(admin.getName());
        if (cancelled) {
            log.info("[Admin] " + admin.getName() + ": restart cancelled");
        }
        Map<String, Object> res = new LinkedHashMap<>(net.swordie.ms.world.RestartManager.status());
        res.put("cancelled", cancelled);
        return res;
    }

    // ------------------------------------------------------------------------------------------- logs, people

    /** ?source=console|build&lines=200&filter=errors|warnings|text. Secrets are replaced by ***. */
    @GET
    @Path("/logs")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> logs(@QueryParam("source") String source, @QueryParam("lines") int lines,
                                    @QueryParam("filter") String filter,
                                    @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        String src = source == null || source.isBlank() ? "console" : source;
        int n = lines <= 0 ? 200 : Math.min(5000, lines);
        try {
            java.nio.file.Path file = switch (src) {
                case "console" -> net.swordie.webapi.site.LogTail.newestConsoleLog();
                case "build" -> net.swordie.webapi.site.LogTail.buildLog();
                default -> throw new BadRequestException("source is console or build.");
            };
            Map<String, Object> res = new LinkedHashMap<>();
            res.put("source", src);
            res.putAll(net.swordie.webapi.site.LogTail.tail(file, n, filter));
            return res;
        } catch (java.io.IOException e) {
            throw new InternalServerErrorException("Couldn't read the log: " + e.getMessage());
        }
    }

    /** Every character online, players and bots, hidden GMs included. */
    @GET
    @Path("/online")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> online(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        List<Map<String, Object>> list = new ArrayList<>();
        int humans = 0;
        for (var world : Server.getInstance().getWorlds()) {
            for (var channel : world.getChannels()) {
                for (var chr : new ArrayList<>(channel.getChars().values())) {
                    try {
                        boolean bot = net.swordie.ms.ext.ServerHooks.isAutomated(chr);
                        humans += bot ? 0 : 1;
                        Map<String, Object> m = new LinkedHashMap<>();
                        m.put("name", chr.getName());
                        m.put("level", (int) chr.getLevel());
                        m.put("job", (int) chr.getJob());
                        m.put("className", JobPathModule.getClassName(chr.getJob(), chr.getSubJob()));
                        m.put("map", chr.getFieldID());
                        m.put("mapName", net.swordie.ms.loaders.StringData.getMapStringById(chr.getFieldID()));
                        m.put("world", world.getName());
                        m.put("channel", channel.getChannelId());
                        m.put("login", chr.getUser() == null ? null : chr.getUser().getName());
                        m.put("kind", bot ? "bot" : "human");
                        String botKind = bot ? net.swordie.ms.socialbot.journey.JourneyAdmin.kindOf(chr) : null;
                        m.put("botKind", bot ? (botKind == null ? "other" : botKind) : null);
                        m.put("hidden", chr.isHidden());
                        list.add(m);
                    } catch (Exception e) {
                        log.error("Admin online list: could not describe a character", e);
                    }
                }
            }
        }
        list.sort(Comparator.comparing((Map<String, Object> m) -> (String) m.get("kind")).reversed()
                .thenComparing(m -> -(int) m.get("level")));
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("count", list.size());
        res.put("humans", humans);
        res.put("bots", list.size() - humans);
        res.put("characters", list);
        return res;
    }

    /** Leveling bots: request.txt flags (wanted, pausedAll, stopped) and each bot with its state. */
    @GET
    @Path("/bots")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> bots(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        return net.swordie.ms.socialbot.journey.JourneyAdmin.list();
    }

    /** action: pause, resume, unstuck, retire; name "all" with pause/resume sets pause= in journey/request.txt. */
    @POST
    @Path("/bots/{name}/{action}")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> botAction(@PathParam("name") String name, @PathParam("action") String action,
                                         @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        String msg;
        try {
            if ("all".equalsIgnoreCase(name)) {
                if (!action.equals("pause") && !action.equals("resume")) {
                    throw new BadRequestException("All bots: pause or resume.");
                }
                net.swordie.ms.socialbot.journey.JourneyAdmin.pauseAll(action.equals("pause"));
                msg = action.equals("pause") ? "Every leveling bot pauses within seconds." : "Every leveling bot goes on.";
            } else {
                msg = net.swordie.ms.socialbot.journey.JourneyAdmin.act(name, action, admin.getName());
                if (msg == null) {
                    throw new NotFoundException(name + " isn't an online leveling bot.");
                }
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        } catch (java.io.IOException e) {
            throw new InternalServerErrorException("Couldn't write journey/request.txt: " + e.getMessage());
        }
        log.info("[Admin] " + admin.getName() + ": bot " + action + " " + name);
        return message(msg);
    }

    /**
     * Saves the launcher's news file (the same JSON GET /public/news returns). Only the parts sent are replaced:
     * {"news": [...], "knownIssues": [...]} keeps the slides, {"slides": [...]} keeps the rest.
     */
    @PUT
    @Path("/news")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> saveNews(Map<String, Object> body, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        if (body == null || !(body.containsKey("news") || body.containsKey("knownIssues") || body.containsKey("slides"))) {
            throw new BadRequestException("Send {\"news\": [...], \"knownIssues\": [...]} and/or {\"slides\": [...]}.");
        }
        try {
            net.swordie.webapi.site.News.save(body);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        } catch (java.io.IOException e) {
            throw new InternalServerErrorException("Couldn't save the news: " + e.getMessage());
        }
        log.info("[Admin] " + admin.getName() + ": news saved (" + String.join(", ", body.keySet()) + ")");
        return message("News saved.");
    }

    /**
     * Uploads a slide image: {"data": "<base64 PNG, JPG or WebP>"}. Returns its file name (for a slide's "image")
     * and its public URL.
     */
    @POST
    @Path("/news/image")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> uploadNewsImage(Map<String, Object> body, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        Object data = body == null ? null : body.get("data");
        if (!(data instanceof String b64) || b64.isBlank()) {
            throw new BadRequestException("Send {\"data\": \"<base64 image>\"}.");
        }
        if (b64.length() > net.swordie.webapi.site.News.MAX_IMAGE_BYTES / 3 * 4 + 8) {
            throw new BadRequestException("Images can be up to "
                    + net.swordie.webapi.site.News.MAX_IMAGE_BYTES / (1024 * 1024) + " MB.");
        }
        String name;
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(b64.trim());
            name = net.swordie.webapi.site.News.saveImage(bytes);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage() == null || e.getMessage().startsWith("Illegal base64")
                    ? "The image isn't valid base64." : e.getMessage());
        } catch (java.io.IOException e) {
            throw new InternalServerErrorException("Couldn't save the image: " + e.getMessage());
        }
        log.info("[Admin] " + admin.getName() + ": news image " + name + " uploaded (" + bytes.length / 1024 + " KB)");
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("name", name);
        res.put("url", "/api/public/news/image/" + name);
        return res;
    }
}

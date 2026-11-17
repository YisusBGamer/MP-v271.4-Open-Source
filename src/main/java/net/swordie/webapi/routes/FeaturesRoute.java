package net.swordie.webapi.routes;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.swordie.ms.client.User;
import net.swordie.ms.constants.DefinitionsExport;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.ServerFeatures.Feature;
import net.swordie.orm.connection.HikariCPDataSource;
import net.swordie.webapi.ApiConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Quality-of-life settings for the launcher's Features tab. Needs the login token of an
 * account with account type Admin, like the other admin calls.
 * <p>
 * GET /api/admin/features   every setting (in category order): default, official value ("vanilla", null when there
 *                           is none), saved value and value in effect, plus saved keys nothing uses
 *                           and the presets (ServerFeatures.Preset: name, description, every setting's value)
 * PUT /api/admin/features   {"values": {"auto_loot": "0", ...}}: saves the settings sent (one transaction, each change
 *                           logged in server_feature_log), then puts them in effect (except "needs restart" ones)
 */
@Path("/admin/features")
public class FeaturesRoute extends BaseResource {

    private static final Logger log = LogManager.getLogger();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> features(@HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        requireAdmin(token);
        return list(null);
    }

    /**
     * Saves the settings sent: a value equal to the default removes the setting's row (so it follows the default),
     * any other value is stored. Settings left out are kept. Every value is checked before anything is written.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Object> save(String json, @HeaderParam(ApiConstants.TOKEN_HEADER) String token) {
        User admin = requireAdmin(token);
        // parsed here, so a malformed body is a 400 and not a server error
        Map<String, Object> raw;
        try {
            org.json.JSONObject body = new org.json.JSONObject(json == null ? "" : json);
            org.json.JSONObject values = body.optJSONObject("values");
            raw = values == null ? Map.of() : values.toMap();
        } catch (org.json.JSONException e) {
            throw new BadRequestException("The settings weren't sent as JSON: " + e.getMessage());
        }
        if (raw.isEmpty()) {
            throw new BadRequestException("No settings were sent.");
        }
        // check everything first: one bad value saves nothing
        Map<Feature, String> wanted = new EnumMap<>(Feature.class); // null text = back to the default (no row)
        for (Map.Entry<?, ?> e : raw.entrySet()) {
            Feature f = Feature.getByKey(String.valueOf(e.getKey()));
            if (f == null) {
                throw new BadRequestException("Unknown setting: " + e.getKey());
            }
            if (e.getValue() == null || e.getValue() == org.json.JSONObject.NULL) {
                wanted.put(f, null); // back to the default
                continue;
            }
            Object value = f.parse(String.valueOf(e.getValue()));
            if (value == null) {
                throw new BadRequestException(f.getLabel() + " " + describeAllowed(f) + " (got " + e.getValue() + ").");
            }
            wanted.put(f, value.equals(f.getDefaultValue()) ? null : f.format(value));
        }
        String by = admin.getName();
        List<String> changed = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection()) {
            try {
                Map<String, String> before = lockRows(con, wanted);
                for (Map.Entry<Feature, String> e : wanted.entrySet()) {
                    Feature f = e.getKey();
                    String old = before.get(f.getKey());
                    String text = e.getValue();
                    if (Objects.equals(old, text)) {
                        continue;
                    }
                    if (text == null) {
                        try (PreparedStatement ps = con.prepareStatement(
                                "DELETE FROM " + ServerFeatures.TABLE + " WHERE name = ?")) {
                            ps.setString(1, f.getKey());
                            ps.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement ps = con.prepareStatement("INSERT INTO " + ServerFeatures.TABLE
                                + " (name, value, updated_by) VALUES (?, ?, ?) "
                                + "ON DUPLICATE KEY UPDATE value = VALUES(value), updated_by = VALUES(updated_by)")) {
                            ps.setString(1, f.getKey());
                            ps.setString(2, text);
                            ps.setString(3, by);
                            ps.executeUpdate();
                        }
                    }
                    // new_value NULL = back to the default, as the desktop app logs it
                    try (PreparedStatement ps = con.prepareStatement("INSERT INTO " + ServerFeatures.LOG_TABLE
                            + " (name, old_value, new_value, changed_by) VALUES (?, ?, ?, ?)")) {
                        ps.setString(1, f.getKey());
                        ps.setString(2, old);
                        ps.setString(3, text);
                        ps.setString(4, by);
                        ps.executeUpdate();
                    }
                    changed.add(f.getLabel() + ": " + (text == null
                            ? "default (" + show(f, f.getDefaultValue()) + ")" : show(f, f.parse(text))));
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            log.error("Admin page: could not save the server features", e);
            throw new BadRequestException("MySQL: " + e.getMessage());
        }
        if (changed.isEmpty()) {
            return list("Nothing changed.");
        }
        log.info(String.format("[Admin] %s saved the server features: %s", by, String.join("; ", changed)));
        ServerFeatures.ReloadResult r = ServerFeatures.reload();
        DefinitionsExport.writeActive();
        List<String> later = new ArrayList<>();
        for (Map.Entry<Feature, String> e : wanted.entrySet()) {
            Feature f = e.getKey();
            if (f.isRestartRequired() && !Objects.equals(ServerFeatures.get(f), savedOrDefault(f, e.getValue()))) {
                later.add(f.getLabel());
            }
        }
        String msg = "Saved: " + String.join("; ", changed) + ".";
        if (!r.ok()) {
            msg += " The settings couldn't be read back, so the ones in effect were kept (see the server log).";
        } else if (!later.isEmpty()) {
            msg += " Takes effect at the next start: " + String.join(", ", later) + ".";
        }
        return list(msg);
    }

    /** @return the current rows of the settings about to change (lower-case name -> value), locked until commit */
    private static Map<String, String> lockRows(Connection con, Map<Feature, String> wanted) throws SQLException {
        Map<String, String> res = new LinkedHashMap<>();
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < wanted.size(); i++) {
            in.append(i == 0 ? "?" : ",?");
        }
        try (PreparedStatement ps = con.prepareStatement("SELECT name, value FROM " + ServerFeatures.TABLE
                + " WHERE name IN (" + in + ") FOR UPDATE")) {
            int i = 1;
            for (Feature f : wanted.keySet()) {
                ps.setString(i++, f.getKey());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString(1);
                    if (name != null) {
                        res.put(name.toLowerCase(Locale.ROOT), rs.getString(2));
                    }
                }
            }
        }
        return res;
    }

    private static Object savedOrDefault(Feature f, String text) {
        Object v = text == null ? null : f.parse(text);
        return v != null ? v : f.getDefaultValue();
    }

    private static String show(Feature f, Object value) {
        if (value instanceof Boolean b) {
            return b ? "on" : "off";
        }
        String text = String.valueOf(value);
        return f.getChoiceLabels().getOrDefault(text, text);
    }

    private static String describeAllowed(Feature f) {
        return switch (f.getType()) {
            case BOOL -> "must be on or off";
            case INT -> "must be a whole number from " + f.getMin() + " to " + f.getMax();
            case CHOICE -> "must be one of " + String.join(", ", f.getChoices());
            case ZONE -> "must be a time zone such as America/Los_Angeles or UTC";
        };
    }

    /** Every setting with its default, saved value and value in effect (the GET answer), plus an optional message. */
    private Map<String, Object> list(String message) {
        Map<String, String> raw;
        try (Connection con = HikariCPDataSource.getConnection()) {
            raw = ServerFeatures.readRaw(con);
            con.rollback();
        } catch (SQLException e) {
            log.error("Admin page: could not read the server features", e);
            throw new BadRequestException("Could not read the " + ServerFeatures.TABLE + " table (MySQL: "
                    + e.getMessage() + "). It's made by the V156 migration when the server starts.");
        }
        Map<String, String> unused = new LinkedHashMap<>(raw);
        List<Map<String, Object>> list = new ArrayList<>();
        // grouped in the categories' display order, each group in declaration order
        List<Feature> ordered = new ArrayList<>(List.of(Feature.values()));
        ordered.sort(java.util.Comparator.comparingInt(f -> f.getCategory().ordinal()));
        for (Feature f : ordered) {
            String text = raw.get(f.getKey());
            unused.remove(f.getKey());
            Object saved = text == null ? null : f.parse(text);
            Map<String, Object> r = new LinkedHashMap<>();
            r.put("key", f.getKey());
            r.put("category", f.getCategory().name());
            r.put("categoryLabel", f.getCategory().getLabel());
            r.put("label", f.getLabel());
            r.put("description", f.getDescription());
            r.put("type", f.getType().name());
            if (f.getType() == ServerFeatures.Type.INT) {
                r.put("min", f.getMin());
                r.put("max", f.getMax());
            }
            if (f.getType() == ServerFeatures.Type.CHOICE || f.getType() == ServerFeatures.Type.ZONE) {
                r.put("choices", f.getChoices());
                if (!f.getChoiceLabels().isEmpty()) {
                    r.put("choiceLabels", f.getChoiceLabels());
                }
            }
            r.put("default", f.getDefaultValue());
            r.put("vanilla", f.getVanillaValue()); // the official servers' value; null: no official equivalent
            // what the next start uses: a missing or invalid row means the default
            r.put("saved", saved != null ? saved : f.getDefaultValue());
            r.put("savedRow", text != null);
            r.put("invalid", text != null && saved == null);
            r.put("active", ServerFeatures.get(f));
            r.put("restartRequired", f.isRestartRequired());
            list.add(r);
        }
        List<String> categories = new ArrayList<>();
        for (ServerFeatures.Category c : ServerFeatures.Category.values()) {
            categories.add(c.name());
        }
        Map<String, Object> res = new LinkedHashMap<>();
        if (message != null) {
            res.put("message", message);
        }
        res.put("categories", categories);
        res.put("features", list);
        res.put("unused", unused);
        List<Map<String, Object>> presets = new ArrayList<>();
        for (ServerFeatures.Preset p : ServerFeatures.Preset.values()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("name", p.getName());
            m.put("description", p.getDescription());
            m.put("values", p.getValueTexts()); // as the PUT takes them: "1"/"0", a number or a choice
            presets.add(m);
        }
        res.put("presets", presets);
        return res;
    }
}

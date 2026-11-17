package net.swordie.ms.constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Locale;

/**
 * Writes the setting and rate definitions for the desktop Server Control app, so it can show and validate them
 * while the server is off: control/definitions.json at boot, run/definitions-new.json from a new jar (main), and
 * control/features-active.json with the values in effect after each load or reload.
 * <p>
 * Loading this class must not touch the database: only the enums and their getters are used.
 */
public final class DefinitionsExport {
    private static final Logger log = LogManager.getLogger();

    public static final Path DEFINITIONS = Path.of("control", "definitions.json");
    public static final Path ACTIVE = Path.of("control", "features-active.json");
    public static final Path NEW_BUILD = Path.of("run", "definitions-new.json");

    private DefinitionsExport() {
    }

    /** @return when this jar (or class folder) was built, as an ISO instant, or "" */
    private static String buildStamp() {
        try {
            File where = new File(DefinitionsExport.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return Instant.ofEpochMilli(where.lastModified()).toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static JSONObject definitions() {
        JSONObject root = new JSONObject();
        root.put("version", 1);
        root.put("build", buildStamp());
        root.put("written", Instant.now().toString());
        root.put("actions", new JSONArray().put("reloadScripts").put("clearCache").put("unstuck").put("reloadFeatures").put("reloadDailyGift"));

        JSONArray categories = new JSONArray();
        for (ServerFeatures.Category c : ServerFeatures.Category.values()) {
            categories.put(new JSONObject().put("key", c.name()).put("label", c.getLabel()));
        }
        root.put("categories", categories);

        JSONArray features = new JSONArray();
        for (ServerFeatures.Feature f : ServerFeatures.Feature.values()) {
            JSONObject o = new JSONObject();
            o.put("key", f.getKey());
            o.put("category", f.getCategory().name());
            o.put("label", f.getLabel());
            o.put("description", f.getDescription());
            o.put("type", f.getType().name().toLowerCase(Locale.ROOT));
            o.put("default", f.format(f.getDefaultValue()));
            o.put("vanilla", f.getVanillaText() == null ? JSONObject.NULL : f.getVanillaText());
            o.put("min", f.getMin());
            o.put("max", f.getMax());
            o.put("choices", new JSONArray(f.getChoices()));
            if (!f.getChoiceLabels().isEmpty()) {
                o.put("choiceLabels", new JSONObject(f.getChoiceLabels()));
            }
            o.put("restartRequired", f.isRestartRequired());
            features.put(o);
        }
        root.put("features", features);

        JSONArray rates = new JSONArray();
        for (ServerRates.Rate r : ServerRates.Rate.values()) {
            JSONObject o = new JSONObject();
            o.put("key", r.getKey());
            o.put("label", r.getLabel());
            o.put("description", r.getDescription());
            o.put("default", r.getDefaultValue());
            o.put("min", r.getMin());
            o.put("max", r.getMax());
            o.put("decimal", r.isDecimal());
            // the buffed channel rates are re-read by BuffedChannels every minute; the rest at the next start
            o.put("applies", r.getKey().startsWith("buffed") ? "minute" : "restart");
            rates.put(o);
        }
        root.put("rates", rates);
        // name -> every setting's value as saved text (the desktop app's Game Settings page fills its form from these)
        JSONObject presets = new JSONObject();
        for (ServerFeatures.Preset p : ServerFeatures.Preset.values()) {
            presets.put(p.getName(), new JSONObject(p.getValueTexts()));
        }
        root.put("presets", presets);
        return root;
    }

    /** The values in effect now and at boot, and the rates in effect. */
    public static JSONObject active() {
        JSONObject values = new JSONObject();
        JSONObject atBoot = new JSONObject();
        for (ServerFeatures.Feature f : ServerFeatures.Feature.values()) {
            values.put(f.getKey(), f.format(ServerFeatures.get(f)));
            atBoot.put(f.getKey(), f.format(ServerFeatures.getAtBoot(f)));
        }
        JSONObject rates = new JSONObject();
        for (ServerRates.Rate r : ServerRates.Rate.values()) {
            rates.put(r.getKey(), ServerRates.get(r));
        }
        return new JSONObject().put("written", Instant.now().toString()).put("values", values).put("atBoot", atBoot).put("rates", rates);
    }

    private static void write(Path file, JSONObject json) throws Exception {
        Path dir = file.toAbsolutePath().getParent();
        Files.createDirectories(dir);
        Path tmp = Files.createTempFile(dir, file.getFileName().toString(), ".tmp");
        Files.writeString(tmp, json.toString(2), StandardCharsets.UTF_8);
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    /** Called once while the server starts, right after ServerFeatures.load(). Never throws. */
    public static void writeAtBoot() {
        try {
            write(DEFINITIONS, definitions());
        } catch (Exception e) {
            log.warn("[Features] Could not write " + DEFINITIONS + ": " + e.getMessage());
        }
        writeActive();
    }

    /** Called after every load or reload. Never throws. */
    public static void writeActive() {
        try {
            write(ACTIVE, active());
        } catch (Exception e) {
            log.warn("[Features] Could not write " + ACTIVE + ": " + e.getMessage());
        }
    }

    /** tools/build.ps1: java -cp run\server-new.jar net.swordie.ms.constants.DefinitionsExport run\definitions-new.json */
    public static void main(String[] args) throws Exception {
        Path file = args.length > 0 ? Path.of(args[0]) : NEW_BUILD;
        write(file, definitions());
    }
}

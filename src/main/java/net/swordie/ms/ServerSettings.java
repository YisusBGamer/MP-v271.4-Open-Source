package net.swordie.ms;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Settings from server.properties in the server folder (see server.properties.example), on top of
 * resources/server.defaults.properties: the tested setup every server starts with.
 */
public final class ServerSettings {

    private static final Logger log = LogManager.getLogger(ServerSettings.class);

    private static final Properties DEFAULTS = loadDefaults();
    private static final Properties PROPS = load(DEFAULTS);

    private ServerSettings() {
    }

    private static Properties loadDefaults() {
        Properties defaults = new Properties();
        Path defaultsFile = Path.of(System.getProperty("user.dir"), "resources", "server.defaults.properties");
        if (Files.isRegularFile(defaultsFile)) {
            try (InputStream in = new FileInputStream(defaultsFile.toFile())) {
                defaults.load(in);
            } catch (IOException e) {
                log.error("Could not read " + defaultsFile + ": " + e.getMessage());
            }
        } else {
            log.error("Missing " + defaultsFile + ": the packet filter is off, unchecked packets can crash the game");
        }
        return defaults;
    }

    private static Properties load(Properties defaults) {
        Properties props = new Properties(defaults);
        Path file = Path.of(System.getProperty("user.dir"), "server.properties");
        if (Files.isRegularFile(file)) {
            try (InputStream in = new FileInputStream(file.toFile())) {
                props.load(in);
            } catch (IOException e) {
                log.error("Could not read " + file + ": " + e.getMessage());
            }
        }
        return props;
    }

    public static String getString(String key, String def) {
        String value = PROPS.getProperty(key);
        return value == null || value.isBlank() ? def : value.trim();
    }

    /**
     * A comma-separated list from both files: the defaults' entries plus the ones server.properties adds, instead of
     * the live value replacing the default one.
     */
    public static String getMergedList(String key) {
        String base = DEFAULTS.getProperty(key, "");
        Object own = PROPS.get(key);
        return own == null ? base.trim() : (base + "," + own).trim();
    }

    public static boolean getBoolean(String key, boolean def) {
        String value = PROPS.getProperty(key);
        return value == null || value.isBlank() ? def : Boolean.parseBoolean(value.trim());
    }

    public static int getInt(String key, int def) {
        String value = PROPS.getProperty(key);
        try {
            return value == null || value.isBlank() ? def : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.error("server.properties: " + key + " is not a number: " + value);
            return def;
        }
    }

    public static long getLong(String key, long def) {
        String value = PROPS.getProperty(key);
        try {
            return value == null || value.isBlank() ? def : Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            log.error("server.properties: " + key + " is not a number: " + value);
            return def;
        }
    }
}

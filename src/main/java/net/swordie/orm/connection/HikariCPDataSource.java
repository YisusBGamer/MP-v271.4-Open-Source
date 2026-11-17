package net.swordie.orm.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {


    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    public static void init() {
        Properties db = loadSettings();
        config.setJdbcUrl(require(db, "url"));
        config.setUsername(require(db, "username"));
        config.setPassword(db.getProperty("password", ""));
        config.setLeakDetectionThreshold(1000 * 15);
        config.setMaximumPoolSize(50);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("autoReconnect", "false");
        config.addDataSourceProperty("allowPublicKeyRetrieval", "true");
        config.setAutoCommit(false);
        ds = new HikariDataSource(config);
    }

    /** Connection settings from database.properties in the server folder (see database.properties.example). */
    public static Properties loadSettings() {
        Properties props = new Properties();
        Path file = Path.of(net.swordie.ms.ServerConstants.DIR, "database.properties");
        if (!Files.isRegularFile(file)) {
            throw new IllegalStateException("Missing " + file + ": copy database.properties.example and fill it in");
        }
        try (InputStream in = new FileInputStream(file.toFile())) {
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + file, e);
        }
        return props;
    }

    private static String require(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("database.properties has no " + key);
        }
        return value.trim();
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private HikariCPDataSource(){}
}

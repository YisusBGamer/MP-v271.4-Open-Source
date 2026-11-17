package net.swordie.orm.migration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.swordie.ms.connection.db.DatabaseException;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.db.converters.FileTimeConverter;
import net.swordie.orm.dao.SworDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 02/06/2021.
 *
 * @author Asura
 */
public class MigrationVersionDao implements SworDao<MigrationVersion> {

    private static final Logger log = LogManager.getLogger(MigrationVersionDao.class);

    public void insert(MigrationVersion version) {
        if (version == null) {
            return;
        }

        var query =
                "INSERT INTO " + MigrationManager.MIGRATION_HISTORY_TABLE_NAME + " (version, script_name, executed_at) values (" +
                "?, " +
                "?, " +
                "?" +
                ");";
        DatabaseManager.executeQuery(query,
                version.getVersion(),
                version.getScriptName(),
                FileTimeConverter.toDbColumn(version.getExecutedAt())
        );
    }

    public List<MigrationVersion> getMigrationVersions() {
        List<Object> objs;
        try {
            objs = DatabaseManager.executeSelect(this, "select * from migration_history mh", "mh");
        } catch (DatabaseException e) {
            objs = List.of(); // no history table yet: every migration runs
        }

        List<MigrationVersion> ret = new ArrayList<>();
        for (var obj : objs) {
            if (obj != null) {
                ret.add((MigrationVersion) obj);
            }
        }

        return ret;
    }

    @Override
    public MigrationVersion fromResultSet(ResultSet resultSet, String alias) {
        MigrationVersion migrationVersion = null;

        try {
            migrationVersion = new MigrationVersion();

            migrationVersion.setVersion(resultSet.getInt(alias + ".version"));
            migrationVersion.setScriptName(resultSet.getString(alias + ".script_name"));
            migrationVersion.setExecutedAt(FileTimeConverter.fromDbColumn(resultSet.getTimestamp(alias + ".executed_at")));

        } catch (SQLException e) {
            log.error("MigrationVersionDao.fromResultSet", e);
        }

        return migrationVersion;
    }
}

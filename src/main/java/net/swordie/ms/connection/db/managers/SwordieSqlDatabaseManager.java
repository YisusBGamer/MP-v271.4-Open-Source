package net.swordie.ms.connection.db.managers;

import net.swordie.ms.util.PerfLog;
import net.swordie.ms.connection.db.BatchQueryHolder;
import net.swordie.ms.connection.db.DatabaseException;
import net.swordie.ms.connection.db.QueryHolder;
import net.swordie.orm.connection.HikariCPDataSource;
import net.swordie.orm.dao.SworDao;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.util.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SwordieSqlDatabaseManager implements IDatabaseManager {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void init(Class[] dbClasses) {
        HikariCPDataSource.init();
        SworDaoFactory.init();
    }

    @Override
    public void sendHeartBeat() {

    }

    @Override
    public void saveToDB(Object obj) {
        throw new UnsupportedOperationException("Use daos");
    }

    @Override
    public void deleteFromDB(Object obj) {
        throw new UnsupportedOperationException("Use daos");
    }

    @Override
    public Object getObjFromDB(Class clazz, int id) {
        throw new UnsupportedOperationException("Use daos");
    }

    @Override
    public Object getObjFromDB(Class clazz, String name) {
        throw new UnsupportedOperationException("Use daos");
    }

    @Override
    public Object getObjFromDB(Class clazz, String columnName, Object value) {
        throw new UnsupportedOperationException("Use daos");
    }

    @Override
    public Object getObjListFromDB(Class clazz) {
        throw new UnsupportedOperationException("Use daos");
    }

    @Override
    public Object getObjListFromDB(Class clazz, String columnName, Object value) {
        throw new UnsupportedOperationException("Use daos");
    }

    @Override
    public List<Object> executeSelect(SworDao dao, String query, String alias, Object... args) {
        long perf = PerfLog.start();
        try {
            return executeSelect0(dao, query, alias, args);
        } finally {
            PerfLog.end("db", PerfLog.sqlLabel(query), perf);
        }
    }

    private List<Object> executeSelect0(SworDao dao, String query, String alias, Object... args) {
        List<Object> objs = new ArrayList<>();

        try (Connection con = HikariCPDataSource.getConnection()) {
            try (var stmt = con.prepareStatement(query)) {
                stmt.setQueryTimeout(QUERY_TIMEOUT_SECONDS);

                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]); // sql starts at 1
                }

                var resultSet = stmt.executeQuery();

                while (resultSet.next()) {
                    objs.add(dao.fromResultSet(resultSet, alias));
                }
                con.commit();
            }
        } catch (SQLException e) {
            throw failure("select", query, e);
        }

        return objs;
    }

    @Override
    public long executeQuery(String query, Object... args) {
        long perf = PerfLog.start();
        try {
            return executeQuery0(query, args);
        } finally {
            PerfLog.end("db", PerfLog.sqlLabel(query), perf);
        }
    }

    private long executeQuery0(String query, Object... args) {
        long id = 0;
        try (Connection con = HikariCPDataSource.getConnection()) {
            try (var stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setQueryTimeout(QUERY_TIMEOUT_SECONDS);

                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]); // sql starts at 1
                }
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                    }
                }
                con.commit();
            }
        } catch (SQLException e) {
            throw failure("query", query, e);
        }
        return id;
    }

    @Override
    public int executeUpdate(String query, Object... args) {
        long perf = PerfLog.start();
        try (Connection con = HikariCPDataSource.getConnection()) {
            try (var stmt = con.prepareStatement(query)) {
                stmt.setQueryTimeout(QUERY_TIMEOUT_SECONDS);
                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]); // sql starts at 1
                }
                int rows = stmt.executeUpdate();
                con.commit();
                return rows;
            }
        } catch (SQLException e) {
            throw failure("update", query, e);
        } finally {
            PerfLog.end("db", PerfLog.sqlLabel(query), perf);
        }
    }

    /** Logs a failed statement once and hands the caller an unchecked exception, so a failed write never looks done. */
    private static DatabaseException failure(String kind, String query, SQLException e) {
        String label = PerfLog.sqlLabel(query);
        log.error("[DB] " + kind + " failed: " + label, e);
        return new DatabaseException(kind + " failed: " + label + ": " + e.getMessage(), e);
    }

    @Override
    public boolean executeFile(File file) {
        try (Connection con = HikariCPDataSource.getConnection()) {

            Reader reader = new FileReader(file.getPath());
            ScriptRunner scriptRunner = new ScriptRunner(con, false, false);
            scriptRunner.runScript(reader);
            con.commit();
            return true;

        } catch (SQLException | IOException e) {
            log.error("SwordieSqlDatabaseManager.executeFile", e);
            return false;
        }
    }

    @Override
    public void executeBatchQuery(BatchQueryHolder batchQueryHolder) {
        if (batchQueryHolder.isEmpty()) {
            return;
        }
        long perf = PerfLog.start();
        try {
            executeBatchQuery0(batchQueryHolder);
        } finally {
            PerfLog.end("db", "batch " + PerfLog.sqlLabel(batchQueryHolder.hasInserts()
                    ? batchQueryHolder.getInsertString() : batchQueryHolder.getUpdateString()), perf);
        }
    }

    private void executeBatchQuery0(BatchQueryHolder batchQueryHolder) {

        // Inserts and updates are each committed on their own, and a row only counts as saved (its hash is stored)
        // once its batch went through. A failed batch is rolled back and its rows stay "changed", so the next save
        // writes them again instead of the change being lost; the caller then gets a DatabaseException.
        SQLException error = null;
        try (Connection con = HikariCPDataSource.getConnection()) {
            // insert
            if (batchQueryHolder.hasInserts()) {
                List<QueryHolder> list = batchQueryHolder.getInsertQueryInfo();
                try (var stmt = con.prepareStatement(batchQueryHolder.getInsertString(), Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setQueryTimeout(QUERY_TIMEOUT_SECONDS);

                    for (var queryHolder : list) {
                        int i = 1; // sql starts at 1
                        for (var arg : queryHolder.getArgs()) {
                            stmt.setObject(i++, arg);
                        }
                        stmt.addBatch();
                    }
                    stmt.executeBatch();

                    try (var keys = stmt.getGeneratedKeys()) {
                        for (var queryHolder : list) {
                            if (!queryHolder.needsId()) {
                                continue; // ids assigned by the server (items, familiars, ...): no generated key
                            }
                            if (keys.next()) {
                                queryHolder.assignId(keys.getLong(1));
                            } else {
                                log.error(String.format("No more keys for %s!", queryHolder));
                            }
                        }
                    }
                    con.commit();
                    for (var queryHolder : list) {
                        queryHolder.setLastSavedHash();
                    }
                } catch (SQLException e) {
                    log.error("[INSERT] SQLException, the rows will be written again on the next save: "
                            + batchQueryHolder.getInsertQueryInfo(), e);
                    rollback(con);
                    for (var queryHolder : list) {
                        queryHolder.unassignId();
                        queryHolder.failed();
                    }
                    error = e;
                }
            }

            // update
            if (batchQueryHolder.hasUpdates()) {
                List<QueryHolder> list = batchQueryHolder.getUpdateQueryInfo();
                try (var stmt = con.prepareStatement(batchQueryHolder.getUpdateString())) {
                    stmt.setQueryTimeout(QUERY_TIMEOUT_SECONDS);

                    for (var queryHolder : list) {
                        int i = 1; // sql starts at 1
                        for (var arg : queryHolder.getArgs()) {
                            stmt.setObject(i++, arg);
                        }
                        stmt.addBatch();
                    }
                    stmt.executeBatch();
                    con.commit();
                    for (var queryHolder : list) {
                        queryHolder.setLastSavedHash();
                    }
                } catch (SQLException e) {
                    log.error("[UPDATE] SQLException, the rows will be written again on the next save: "
                            + batchQueryHolder.getUpdateQueryInfo(), e);
                    rollback(con);
                    error = e;
                }
            }
        } catch (SQLException e) {
            log.error("SQLException: " + batchQueryHolder, e);
            // no connection: nothing was written
            for (var queryHolder : (List<QueryHolder>) batchQueryHolder.getInsertQueryInfo()) {
                queryHolder.failed();
            }
            error = e;
        }
        if (error != null) {
            throw new DatabaseException("batch failed: " + PerfLog.sqlLabel(batchQueryHolder.hasInserts()
                    ? batchQueryHolder.getInsertString() : batchQueryHolder.getUpdateString()) + ": "
                    + error.getMessage(), error);
        }
    }

    private static void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            log.error("Rollback failed", e);
        }
    }

    @Override
    public long executeInsert(String query, Object... args) {
        long perf = PerfLog.start();
        try {
            return executeInsert0(query, args);
        } finally {
            PerfLog.end("db", PerfLog.sqlLabel(query), perf);
        }
    }

    private long executeInsert0(String query, Object... args) {
        long res = 0;
        try (Connection con = HikariCPDataSource.getConnection()) {
            try (var stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setQueryTimeout(QUERY_TIMEOUT_SECONDS);

                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]); // sql starts at 1
                }
                stmt.executeUpdate();

                try (var resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        res = resultSet.getLong(1);
                    }
                }
                con.commit();
            }
        } catch (SQLException e) {
            throw failure("insert", query, e);
        }
        return res;
    }
}

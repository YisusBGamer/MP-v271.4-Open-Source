package net.swordie.orm.dao;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerDao implements SworDao<Integer> {

    private static final Logger log = LogManager.getLogger(IntegerDao.class);

    private String columnName;

    public IntegerDao(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public Integer fromResultSet(ResultSet resultSet, String alias) {
        Integer i = null;

        try {
            i = resultSet.getInt(alias + "." + columnName);
        } catch (SQLException e) {
            log.error("IntegerDao.fromResultSet", e);
        }

        return i;
    }
}

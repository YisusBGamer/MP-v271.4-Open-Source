package net.swordie.orm.dao;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerColumnDao implements SworDao<Integer> {

    private static final Logger log = LogManager.getLogger(IntegerColumnDao.class);

    private String columnName;

    public IntegerColumnDao(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public Integer fromResultSet(ResultSet resultSet, String alias) {
        Integer integer = null;

        try {
            integer = resultSet.getInt(alias + "." + columnName);
        } catch (SQLException e) {
            log.error("IntegerColumnDao.fromResultSet", e);
        }

        return integer;
    }
}

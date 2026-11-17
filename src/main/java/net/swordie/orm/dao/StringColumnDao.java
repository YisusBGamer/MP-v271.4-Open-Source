package net.swordie.orm.dao;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 02/06/2021.
 *
 * @author Asura
 */
public class StringColumnDao implements SworDao<String> {

    private static final Logger log = LogManager.getLogger(StringColumnDao.class);

    private String columnName;

    public StringColumnDao(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String fromResultSet(ResultSet resultSet, String alias) {
        String string = null;

        try {
            string = resultSet.getString(alias + "." + columnName);

        } catch (SQLException e) {
            log.error("StringColumnDao.fromResultSet", e);
        }

        return string;
    }
}

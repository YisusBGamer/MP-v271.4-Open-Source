package net.swordie.orm.dao;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.erdalink.ErdaLinkStone;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Erda Link stones of a character (erda_link_stones). */
public class ErdaLinkDao implements SworDao<ErdaLinkStone> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public ErdaLinkStone fromResultSet(ResultSet rs, String alias) {
        try {
            ErdaLinkStone stone = new ErdaLinkStone(rs.getInt(alias + ".stoneid"), rs.getInt(alias + ".level"));
            stone.setId(rs.getLong(alias + ".id"));
            stone.setLastSavedHash();
            return stone;
        } catch (SQLException e) {
            log.error("Could not read an Erda Link row", e);
            return null;
        }
    }

    public List<ErdaLinkStone> byChar(Char chr) {
        List<ErdaLinkStone> res = new ArrayList<>();
        for (Object o : DatabaseManager.executeSelect(this, "SELECT * FROM erda_link_stones el WHERE charid = ?",
                "el", chr.getId())) {
            if (o instanceof ErdaLinkStone stone) {
                res.add(stone);
            }
        }
        return res;
    }

    public void save(Char chr, Collection<ErdaLinkStone> stones) {
        if (chr == null || chr.getId() == 0 || stones == null) {
            return;
        }
        for (ErdaLinkStone stone : Util.snapshotSet(stones)) {
            if (stone.isClean()) {
                continue;
            }
            long id = DatabaseManager.executeInsert("INSERT INTO erda_link_stones (charid, stoneid, level) VALUES "
                    + "(?, ?, ?) ON DUPLICATE KEY UPDATE level = VALUES(level)", chr.getId(), stone.getStoneId(),
                    stone.getLevel());
            if (stone.getId() == 0 && id > 0) {
                stone.setId(id);
            }
            stone.setLastSavedHash();
        }
    }
}

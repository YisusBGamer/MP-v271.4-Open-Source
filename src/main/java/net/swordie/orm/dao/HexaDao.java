package net.swordie.orm.dao;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.hexa.HexaSkill;
import net.swordie.ms.client.character.hexa.HexaStat;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** HEXA Matrix cores (hexa_skills) and HEXA Stat nodes (hexa_stats) of a character. */
public class HexaDao implements SworDao<Object> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public Object fromResultSet(ResultSet rs, String alias) {
        try {
            if ("hs".equals(alias)) {
                HexaSkill skill = new HexaSkill(rs.getInt(alias + ".coreid"), rs.getInt(alias + ".level"));
                skill.setId(rs.getLong(alias + ".id"));
                skill.setEnabled(rs.getBoolean(alias + ".enabled"));
                skill.setDisabled(rs.getBoolean(alias + ".disabled"));
                skill.setLastSavedHash();
                return skill;
            }
            HexaStat stat = new HexaStat(rs.getInt(alias + ".coreid"));
            stat.setId(rs.getLong(alias + ".id"));
            for (int i = 0; i < 3; i++) {
                stat.setType(i, rs.getInt(alias + ".stat" + i));
                stat.setLevel(i, rs.getInt(alias + ".level" + i));
            }
            stat.setLastSavedHash();
            return stat;
        } catch (SQLException e) {
            log.error("Could not read a HEXA row", e);
            return null;
        }
    }

    public List<HexaSkill> skillsByChar(Char chr) {
        List<HexaSkill> res = new ArrayList<>();
        for (Object o : DatabaseManager.executeSelect(this, "SELECT * FROM hexa_skills hs WHERE charid = ?", "hs",
                chr.getId())) {
            if (o instanceof HexaSkill hs) {
                res.add(hs);
            }
        }
        return res;
    }

    public List<HexaStat> statsByChar(Char chr) {
        List<HexaStat> res = new ArrayList<>();
        for (Object o : DatabaseManager.executeSelect(this, "SELECT * FROM hexa_stats hx WHERE charid = ?", "hx",
                chr.getId())) {
            if (o instanceof HexaStat hs) {
                res.add(hs);
            }
        }
        return res;
    }

    public void saveSkills(Char chr, Collection<HexaSkill> skills) {
        if (chr == null || chr.getId() == 0 || skills == null) {
            return;
        }
        for (HexaSkill hs : Util.snapshotSet(skills)) {
            if (hs.isClean()) {
                continue;
            }
            long id = DatabaseManager.executeInsert("INSERT INTO hexa_skills (charid, coreid, level, enabled, "
                            + "disabled) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE level = VALUES(level), "
                            + "enabled = VALUES(enabled), disabled = VALUES(disabled)",
                    chr.getId(), hs.getCoreId(), hs.getLevel(), hs.isEnabled(), hs.isDisabled());
            if (hs.getId() == 0 && id > 0) {
                hs.setId(id);
            }
            hs.setLastSavedHash();
        }
    }

    public void saveStats(Char chr, Collection<HexaStat> stats) {
        if (chr == null || chr.getId() == 0 || stats == null) {
            return;
        }
        for (HexaStat hs : Util.snapshotSet(stats)) {
            if (hs.isClean()) {
                continue;
            }
            long id = DatabaseManager.executeInsert("INSERT INTO hexa_stats (charid, coreid, stat0, level0, stat1, "
                            + "level1, stat2, level2) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                            + "stat0 = VALUES(stat0), level0 = VALUES(level0), stat1 = VALUES(stat1), "
                            + "level1 = VALUES(level1), stat2 = VALUES(stat2), level2 = VALUES(level2)",
                    chr.getId(), hs.getCoreId(), hs.getType(0), hs.getLevel(0), hs.getType(1), hs.getLevel(1),
                    hs.getType(2), hs.getLevel(2));
            if (hs.getId() == 0 && id > 0) {
                hs.setId(id);
            }
            hs.setLastSavedHash();
        }
    }
}

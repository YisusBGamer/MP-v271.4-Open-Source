package net.swordie.orm.dao;

import net.swordie.ms.client.character.union.UnionArtifact;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.db.converters.FileTimeConverter;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/** Union Artifact crystals of an account (union_artifacts). */
public class UnionArtifactDao {

    public Map<Integer, UnionArtifact> byAccount(int accId) {
        Map<Integer, UnionArtifact> res = new TreeMap<>();
        for (var row : DatabaseManager.queryRows("SELECT idx, level, skill1, skill2, skill3, expiration FROM "
                + "union_artifacts WHERE accid = ?", accId)) {
            UnionArtifact ua = new UnionArtifact();
            ua.setIndex(((Number) row.get("idx")).intValue());
            ua.setLevel(((Number) row.get("level")).intValue());
            ua.setSkill(0, ((Number) row.get("skill1")).intValue());
            ua.setSkill(1, ((Number) row.get("skill2")).intValue());
            ua.setSkill(2, ((Number) row.get("skill3")).intValue());
            Object exp = row.get("expiration");
            if (exp instanceof Timestamp ts) {
                ua.setExpiration(FileTimeConverter.fromDbColumn(ts));
            } else if (exp instanceof java.time.LocalDateTime ldt) {
                ua.setExpiration(FileTimeConverter.fromDbColumn(Timestamp.valueOf(ldt)));
            }
            res.put(ua.getIndex(), ua);
        }
        return res;
    }

    public void save(int accId, Collection<UnionArtifact> artifacts) {
        for (UnionArtifact ua : artifacts) {
            DatabaseManager.executeQuery("INSERT INTO union_artifacts (accid, idx, level, skill1, skill2, skill3, "
                            + "expiration) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE level = VALUES(level), "
                            + "skill1 = VALUES(skill1), skill2 = VALUES(skill2), skill3 = VALUES(skill3), "
                            + "expiration = VALUES(expiration)",
                    accId, ua.getIndex(), ua.getLevel(), ua.getSkill(0), ua.getSkill(1), ua.getSkill(2),
                    FileTimeConverter.toDbColumn(ua.getExpiration()));
        }
    }
}

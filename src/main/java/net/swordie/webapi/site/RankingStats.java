package net.swordie.webapi.site;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.BaseStat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * When a character in the level ranking's top 5 logs out, a few of its stats are saved (rankingstats/&lt;name&gt;.json)
 * and the website shows them in a drop-down under its row (GET /api/public/rankings/stats?name=...).
 */
public final class RankingStats {

    private static final Logger log = LogManager.getLogger(RankingStats.class);
    private static final Path DIR = Paths.get(ServerConstants.DIR, "rankingstats");
    public static final int TOP = 5;

    private RankingStats() {
    }

    /** Called on logout: keeps the character's stats if it's in the level ranking's top 5. */
    public static void onLogout(Char chr) {
        try {
            if (chr == null || !inTop(chr.getName())) {
                return;
            }
            Files.createDirectories(DIR);
            Files.writeString(DIR.resolve(fileName(chr.getName())), toJson(snapshot(chr)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("Could not keep the ranking stats of " + (chr == null ? "?" : chr.getName()) + ": " + e);
        }
    }

    /** @return the kept stats of a character of the top 5, or null (not in the top 5, or none kept yet) */
    public static String getJson(String name) {
        if (name == null || name.isBlank() || name.length() > 16 || !inTop(name)) {
            return null;
        }
        try {
            Path p = DIR.resolve(fileName(name));
            return Files.exists(p) ? Files.readString(p, StandardCharsets.UTF_8) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean inTop(String name) {
        List<Map<String, Object>> top = SiteData.getLevelRanking(TOP, null);
        for (var row : top) {
            if (name.equalsIgnoreCase(String.valueOf(row.get("name")))) {
                return true;
            }
        }
        return false;
    }

    private static String fileName(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9_]", "_") + ".json";
    }

    private static Map<String, Object> snapshot(Char chr) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("name", chr.getName());
        m.put("level", chr.getLevel());
        var je = net.swordie.ms.constants.JobConstants.JobEnum.getJobById(chr.getJob());
        m.put("job", je == null ? String.valueOf(chr.getJob()) : je.toPrettyString());
        m.put("maxHp", chr.getMaxHP());
        m.put("maxMp", chr.getMaxMP());
        m.put("str", chr.getTotalStat(BaseStat.str));
        m.put("dex", chr.getTotalStat(BaseStat.dex));
        m.put("int", chr.getTotalStat(BaseStat.inte));
        m.put("luk", chr.getTotalStat(BaseStat.luk));
        m.put("attack", chr.getTotalStat(BaseStat.pad));
        m.put("magicAttack", chr.getTotalStat(BaseStat.mad));
        m.put("critRate", chr.getTotalStat(BaseStat.cr));
        m.put("critDamage", chr.getTotalStat(BaseStat.crDmg));
        m.put("damage", chr.getTotalStat(BaseStat.damR));
        m.put("bossDamage", chr.getTotalStat(BaseStat.bd));
        m.put("ignoreDefence", chr.getTotalStat(BaseStat.ied));
        m.put("arcaneForce", chr.getTotalStat(BaseStat.arc));
        int stars = 0;
        for (Item it : chr.getEquippedInventory().getItems()) {
            if (it instanceof Equip e && !e.isCash() && !ItemConstants.isSymbol(e.getItemId())) {
                stars += e.getChuc();
            }
        }
        m.put("starForce", stars);
        m.put("updated", java.time.LocalDateTime.now().withNano(0).toString());
        return m;
    }

    private static String toJson(Map<String, Object> m) {
        StringBuilder sb = new StringBuilder("{");
        for (var e : m.entrySet()) {
            if (sb.length() > 1) {
                sb.append(',');
            }
            sb.append('"').append(e.getKey()).append("\":");
            Object v = e.getValue();
            if (v instanceof Number) {
                sb.append(v);
            } else {
                sb.append('"').append(String.valueOf(v).replace("\\", "\\\\").replace("\"", "\\\"")).append('"');
            }
        }
        return sb.append('}').toString();
    }
}

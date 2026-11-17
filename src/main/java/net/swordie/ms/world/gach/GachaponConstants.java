package net.swordie.ms.world.gach;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.util.Randomizer;
import net.swordie.ms.world.gach.result.GachaponDlgType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Gachapon rewards. The town gachapon (ticket 5220000) has one pool per town, read from
 * resources/gachapon-pools.tsv: "pool name maps title" rows tie maps to a pool, "item pool id count weight kind hot
 * name" rows fill it. A spin picks by weight from the pool of the map the player stands in (Henesys's otherwise).
 */
public class GachaponConstants {
    private static final Logger log = LogManager.getLogger();
    private static final String FILE = ServerConstants.RESOURCES_DIR + "/gachapon-pools.tsv";
    private static final String DEFAULT_POOL = "henesys";

    private static final Map<GachaponDlgType, GachaponInfo> gachaponInfo = new HashMap<>();
    private static volatile Map<String, GachaponInfo> townPools = new HashMap<>();
    private static volatile Map<Integer, String> poolByMap = new HashMap<>();

    static {
        init();
    }

    public static Map<GachaponDlgType, GachaponInfo> getGachaponInfo() {
        return gachaponInfo;
    }

    public static synchronized void init() {
        loadTownPools();
        GachaponInfo town = townPools.get(DEFAULT_POOL);
        gachaponInfo.put(GachaponDlgType.TOWN, town != null ? town
                : new GachaponInfo(messages("Town"), new ArrayList<>()));
        gachaponInfo.put(GachaponDlgType.NEBULITE, new GachaponInfo(messages("Nebulite"),
                new ArrayList<>(List.of(new GachaponInfo.GachItem(3063231, true)))));
        gachaponInfo.put(GachaponDlgType.CHAIR, new GachaponInfo(messages("Chair"), new ArrayList<>()));
        gachaponInfo.put(GachaponDlgType.MOUNT, new GachaponInfo(messages("Mount"), new ArrayList<>()));
        gachaponInfo.put(GachaponDlgType.SPECIAL, new GachaponInfo(messages("Special"), new ArrayList<>()));
    }

    private static List<String> messages(String title) {
        return Arrays.asList(title + " Gachapon",
                "Equipment, potions and ores, with a chance at chairs and mounts.",
                "Each spin uses one Gachapon Ticket.");
    }

    private static void loadTownPools() {
        Map<String, GachaponInfo> pools = new LinkedHashMap<>();
        Map<String, List<GachaponInfo.GachItem>> poolItems = new HashMap<>();
        Map<String, String> titles = new HashMap<>();
        Map<Integer, String> maps = new HashMap<>();
        try {
            for (String line : Files.readAllLines(Path.of(FILE), StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] c = line.split("\t");
                if (c[0].equals("pool") && c.length >= 4) {
                    titles.put(c[1], c[3]);
                    poolItems.putIfAbsent(c[1], new ArrayList<>());
                    for (String m : c[2].split(",")) {
                        maps.put(Integer.parseInt(m.trim()), c[1]);
                    }
                } else if (c[0].equals("item") && c.length >= 7) {
                    poolItems.computeIfAbsent(c[1], k -> new ArrayList<>()).add(new GachaponInfo.GachItem(
                            Integer.parseInt(c[2]), Short.parseShort(c[3]), Integer.parseInt(c[4]), c[6].equals("1")));
                }
            }
        } catch (IOException | RuntimeException e) {
            log.error("Could not read " + FILE, e);
        }
        poolItems.forEach((name, list) -> pools.put(name,
                new GachaponInfo(messages(titles.getOrDefault(name, "Town")), list)));
        townPools = pools;
        poolByMap = maps;
    }

    /** The town pool for a map: the map's own, else the default one. */
    public static GachaponInfo getTownPool(int fieldId) {
        String name = poolByMap.get(fieldId);
        GachaponInfo info = name != null ? townPools.get(name) : null;
        return info != null ? info : townPools.get(DEFAULT_POOL);
    }

    public static GachaponInfo getInfo(GachaponDlgType dlg, int fieldId) {
        if (dlg == GachaponDlgType.TOWN) {
            GachaponInfo town = getTownPool(fieldId);
            if (town != null) {
                return town;
            }
        }
        return gachaponInfo.get(dlg);
    }

    /** A weighted pick from the dialog's pool (the town's for the town gachapon), or null if it has none. */
    public static GachaponInfo.GachItem getRandomReward(GachaponDlgType dlg, int fieldId) {
        GachaponInfo gachapon = getInfo(dlg, fieldId);
        if (gachapon == null) {
            return null;
        }
        List<GachaponInfo.GachItem> rewards = gachapon.getRewards();
        if (rewards == null || rewards.isEmpty()) {
            return null;
        }
        long total = 0;
        for (GachaponInfo.GachItem i : rewards) {
            total += i.getWeight();
        }
        long roll = (long) (Randomizer.nextDouble() * total);
        for (GachaponInfo.GachItem i : rewards) {
            roll -= i.getWeight();
            if (roll < 0) {
                return i;
            }
        }
        return rewards.get(rewards.size() - 1);
    }

    public static int getRandomItem(GachaponDlgType dlg) {
        GachaponInfo.GachItem item = getRandomReward(dlg, 0);
        return item != null ? item.getItemID() : -1;
    }

    public static GachaponDlgType getDlgByTicket(final int ticketID) {
        switch (ticketID) {
            case 5220000:
                return GachaponDlgType.TOWN;
            case 5220098:
                return GachaponDlgType.NEBULITE;
            case 5220097:
                return GachaponDlgType.CHAIR;
            case 5220099:
                return GachaponDlgType.MOUNT;
            case 5220100:
                return GachaponDlgType.SPECIAL;
            case 5451000:// Remote Gachapon Ticket TODO: handle remote gach
                return null;
        }
        return null;
    }
}

package net.swordie.ms.client.character.items;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.GameConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Growth Potions with no script (resources/growth-potions.tsv, from their String.wz descriptions): between the
 * minimum level and "through" level they give one level; above it, those that say so give one level's worth of EXP
 * (the EXP from the "through" level to the next), the others can't be used.
 */
public class GrowthPotions {
    private static final Logger log = LogManager.getLogger();
    private static final String FILE = ServerConstants.RESOURCES_DIR + "/growth-potions.tsv";

    private record Potion(int min, int through, boolean expAbove, boolean noBurning, int levels) {
    }

    private static volatile Map<Integer, Potion> potions;

    private static Map<Integer, Potion> potions() {
        if (potions == null) {
            synchronized (GrowthPotions.class) {
                if (potions == null) {
                    Map<Integer, Potion> map = new HashMap<>();
                    try {
                        for (String line : Files.readAllLines(Path.of(FILE), StandardCharsets.UTF_8)) {
                            if (line.isBlank() || line.startsWith("#")) {
                                continue;
                            }
                            String[] c = line.split("\t");
                            map.put(Integer.parseInt(c[0]), new Potion(Integer.parseInt(c[1]), Integer.parseInt(c[2]),
                                    "1".equals(c[3]), c.length > 5 && "1".equals(c[4]),
                                    c.length > 6 ? Math.max(1, Integer.parseInt(c[6].trim())) : 1));
                        }
                    } catch (Exception e) {
                        log.error("Could not read " + FILE, e);
                    }
                    potions = map;
                }
            }
        }
        return potions;
    }

    public static boolean isGrowthPotion(int itemId) {
        return potions().containsKey(itemId);
    }

    /** @return null if used, else why not */
    public static String use(Char chr, Item potion) {
        Potion p = potions().get(potion.getItemId());
        if (p == null) {
            return "This item can't be used.";
        }
        if (p.noBurning() && net.swordie.ms.client.character.BurningModule.isBurningNow(
                chr.getAvatarData().getCharacterStat())) {
            return "Burning characters can't use this item.";
        }
        int level = chr.getLevel();
        if (level < p.min()) {
            return String.format("You must be at least level %d to use this.", p.min());
        }
        if (level >= GameConstants.MAX_CHAR_LEVEL) {
            return "Maximum-level characters cannot benefit from this item.";
        }
        if (p.levels() > 1 && level <= p.through()) {
            int n = pickLevels(p, level);
            chr.consumeItem(potion, 1);
            for (int i = 0; i < n && chr.getLevel() < GameConstants.MAX_CHAR_LEVEL; i++) {
                addFixedExp(chr, GameConstants.charExp[chr.getLevel()]);
            }
            return null;
        }
        long exp;
        if (level <= p.through()) {
            exp = GameConstants.charExp[level]; // one whole level: the EXP already in the bar carries over
        } else if (p.expAbove() && p.through() < GameConstants.charExp.length) {
            exp = GameConstants.charExp[p.through()];
        } else {
            return String.format("This item can only be used up to level %d.", p.through());
        }
        chr.consumeItem(potion, 1);
        addFixedExp(chr, exp);
        return null;
    }

    /** addExp adds the EXP-rate bonuses on top; a potion's level is a fixed amount */
    private static void addFixedExp(Char chr, long exp) {
        long rate = Math.max(1, chr.getTotalStat(net.swordie.ms.enums.BaseStat.expR));
        chr.addExp(Math.max(1, (exp * 100 + rate - 1) / rate + 1)); // rounded up: addExp truncates the bonus part
    }

    /**
     * 1 to levels, fewer more likely the higher the character is in the potion's range (weight 1/n^(1+2t), t from 0
     * at the minimum level to 1 at the "through" level). GMS doesn't publish the odds.
     */
    static int pickLevels(Potion p, int level) {
        double t = p.through() > p.min() ? Math.min(1, Math.max(0, (level - p.min()) / (double) (p.through() - p.min()))) : 0;
        double[] w = new double[p.levels()];
        double sum = 0;
        for (int n = 1; n <= w.length; n++) {
            w[n - 1] = 1 / Math.pow(n, 1 + 2 * t);
            sum += w[n - 1];
        }
        double r = Math.random() * sum;
        for (int n = 1; n <= w.length; n++) {
            r -= w[n - 1];
            if (r < 0) {
                return n;
            }
        }
        return 1;
    }
}

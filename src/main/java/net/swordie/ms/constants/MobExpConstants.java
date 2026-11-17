package net.swordie.ms.constants;

import java.util.ArrayList;
import java.util.List;

public class MobExpConstants {
    public record MobExpRate(
        int minLevel,
        int rate
    ) {}

    /**
     * The monster EXP rate of a character level: the low level rate below 10, the server rate from 10, and the
     * gentler EXP-to-level curve from {@link GameConstants#CUSTOM_EXP_FROM_LEVEL} (setting custom_exp_curve, which needs
     * a restart: characters keep their rate until their level changes).
     */
    private static int computeRate(int level) {
        if (level < 10) {
            return ServerRates.getLowLevelExpRate();
        }
        long base = ServerRates.getExpRate();
        int idx = level - GameConstants.CUSTOM_EXP_FROM_LEVEL;
        if (ServerFeatures.on(ServerFeatures.Feature.CUSTOM_EXP_CURVE)
                && idx >= 0 && idx < GameConstants.CUSTOM_EXP_TO_LEVEL.length
                && level < GameConstants.charExp.length && GameConstants.CUSTOM_EXP_TO_LEVEL[idx] > 0) {
            // the custom EXP-to-level curve: EXP scaled by official / custom EXP needed for this level
            double scale = GameConstants.charExp[level]
                    / (GameConstants.CUSTOM_EXP_TO_LEVEL[idx] * GameConstants.CUSTOM_EXP_TIME_FACTOR);
            return (int) Math.min(Integer.MAX_VALUE, Math.max(1, Math.round(base * Math.max(1D, scale))));
        }
        return (int) base;
    }

    /**
     * @return the EXP rates by minimum level (a new entry wherever the rate changes), sorted from the lowest minLevel
     * to the highest. The rates come from the server rates (see {@link ServerRates}: the server_rates table,
     * used from the next start) and the EXP curve in GameConstants.
     */
    public static List<MobExpRate> getExpRatesPerMinLevel() {
        List<MobExpRate> list = new ArrayList<>();
        int last = -1;
        for (int level = 1; level <= GameConstants.MAX_CHAR_LEVEL; level++) {
            int rate = computeRate(level);
            if (rate != last) {
                list.add(new MobExpRate(level, rate));
                last = rate;
            }
        }
        return list;
    }

    /**
     * @return the EXP rates by level in a few words, for the startup banner and the web API, e.g. "x1 (Lv. 1-9), x100
     * (Lv. 10-199), x100-x51,119 by level from Lv. 200 (gentler EXP curve)"
     */
    public static String describe() {
        List<String> parts = new ArrayList<>();
        int low = computeRate(1);
        int base = computeRate(10);
        parts.add(String.format("x%,d (Lv. 1-9)", low));
        if (!ServerFeatures.on(ServerFeatures.Feature.CUSTOM_EXP_CURVE)) {
            parts.add(String.format("x%,d (Lv. 10+)", base));
            return String.join(", ", parts);
        }
        int from = GameConstants.CUSTOM_EXP_FROM_LEVEL;
        int to = Math.min(GameConstants.MAX_CHAR_LEVEL - 1, from + GameConstants.CUSTOM_EXP_TO_LEVEL.length - 1);
        int min = Integer.MAX_VALUE, max = 0;
        for (int level = from; level <= to; level++) {
            min = Math.min(min, computeRate(level));
            max = Math.max(max, computeRate(level));
        }
        parts.add(String.format("x%,d (Lv. 10-%d)", base, from - 1));
        parts.add(String.format("x%,d-x%,d by level from Lv. %d (gentler EXP curve)", min, max, from));
        return String.join(", ", parts);
    }

    /**
     * Searches for the {@link MobExpRate} with the highest minLevel for the given character level
     *
     * @param level the level of the character
     * @return exp rate found for the given level, with a minimum value of 1
     */
    public static int getRateForCharacterLevel(int level) {
        return Math.max(computeRate(level), 1);
    }
}

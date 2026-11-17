package net.swordie.ms.tools;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.ItemGrade;
import net.swordie.ms.handlers.item.ItemHandlerModule;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.ServerConstants;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Offline roll harness (no game client, no database): rolls cubes, flames and Star Force N times through the real
 * server code and prints the measured rates next to the GMS numbers we use. A rate more than 4 standard errors off
 * is marked "<-- MISMATCH".
 * <p>
 * Run from the server folder after a compile (tools/rolltest.sh): java -cp target/classes;deps RollTest [N] [item]
 * <p>
 * Uses the dat files (dat/equips.dat, dat/itemOptions.dat) and reads the database (equip_drops, server_features), so
 * the rates are what this server rolls (luck_boost, prime_line_chance...).
 */
public final class RollTest {
    private static final int ABSOLAB_KNIGHT_HELM = 1004422;

    private static int n = 200_000;
    private static int mismatches;

    // GMS v271 Star Force window, standard mode (slider 1), as GMS 271.3-271.5 sends it (EQUIPMENT_ENCHANT 52):
    // {success, destroyed share of a failure}, out of 1000, for stars 0-18
    private static final int[][] GMS_SF = {
            {950, 0}, {900, 0}, {850, 0}, {850, 0}, {800, 0}, {750, 0}, {700, 0}, {650, 0}, {600, 0}, {550, 0},
            {500, 0}, {450, 0}, {400, 0}, {350, 0}, {300, 0}, {300, 30}, {300, 30}, {150, 80}, {150, 80},
    };

    public static void main(String[] argv) throws Exception {
        boolean server = java.util.Arrays.asList(argv).contains("--server");
        String[] args = java.util.Arrays.stream(argv).filter(a -> !a.equals("--server")).toArray(String[]::new);
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        int itemId = args.length > 1 ? Integer.parseInt(args[1]) : ABSOLAB_KNIGHT_HELM; // AbsoLab Knight Helm, Lv 160
        // read-only: ItemConstants loads equip_drops when its class loads
        net.swordie.ms.connection.db.DatabaseManager.init();
        net.swordie.ms.constants.ServerFeatures.load();
        if (!server) {
            officialOdds();
        }
        System.out.println(server ? "Settings: this server's (luck_boost etc.)" // hardcode-ok
                : "Settings: official odds (luck_boost 100, no prime/third-line overrides); --server for ours");
        ItemData.loadItemOptions(new File(ServerConstants.DAT_DIR + "/itemOptions.dat"), true);
        Equip base = ItemData.getEquipDeepCopy(itemId, false);
        if (base == null) {
            System.out.println("No equip " + itemId + " in " + ServerConstants.DAT_DIR); // hardcode-ok
            return;
        }
        System.out.printf("RollTest: %,d rolls each, item %d (Lv %d, %d slots)%n%n", n, itemId, base.getReqLevel(), // hardcode-ok
                base.getTuc());
        cubes(base);
        flames();
        starForce(base);
        System.out.println(mismatches == 0 ? "\nNo mismatches." : "\n" + mismatches + " mismatch(es)."); // hardcode-ok
        System.exit(0); // the database pool's threads
    }

    /** In memory only (never saved): the settings that change roll odds at their official values. */
    @SuppressWarnings("unchecked")
    private static void officialOdds() throws Exception {
        var cls = net.swordie.ms.constants.ServerFeatures.class;
        var f = cls.getDeclaredField("active");
        f.setAccessible(true);
        var cur = (java.util.Map<net.swordie.ms.constants.ServerFeatures.Feature, Object>) f.get(null);
        var m = new java.util.EnumMap<net.swordie.ms.constants.ServerFeatures.Feature, Object>(
                net.swordie.ms.constants.ServerFeatures.Feature.class);
        if (cur != null) {
            m.putAll(cur);
        }
        m.put(net.swordie.ms.constants.ServerFeatures.Feature.LUCK_BOOST, 100);
        m.put(net.swordie.ms.constants.ServerFeatures.Feature.PRIME_LINE_CHANCE, 0);
        m.put(net.swordie.ms.constants.ServerFeatures.Feature.ALWAYS_THREE_POTENTIAL_LINES, false);
        f.set(null, java.util.Collections.unmodifiableMap(m));
    }

    /** prints one rate line; expected and measured are fractions */
    private static void line(String what, double expected, long hits, long trials) {
        double got = (double) hits / trials;
        double se = Math.sqrt(Math.max(expected * (1 - expected), 1e-9) / trials);
        boolean off = Math.abs(got - expected) > 4 * se + 1e-6;
        if (off) {
            mismatches++;
        }
        System.out.printf("  %-44s expected %8.4f%%  measured %8.4f%%%s%n", what, expected * 100, got * 100, // hardcode-ok
                off ? "  <-- MISMATCH" : "");
    }

    // ------------------------------------------------------------------------------------------------ cubes
    private static final int[][] CUBES = {
            {ItemConstants.RED_CUBE, 0}, {ItemConstants.BLACK_CUBE, 0}, {ItemConstants.BONUS_POT_CUBE, 1},
            {-1, 0}, {-2, 1}, // the Enhance window's meso reset (potential / bonus potential)
    };
    private static final String[] GRADES = {"Rare", "Epic", "Unique", "Legendary"};

    private static void cubes(Equip base) {
        System.out.println("== Cubes (expected = ItemConstants tables, GMS published rates): rank-up per use (ceiling reset every roll), and with " // hardcode-ok
                + "the ceiling kept");
        for (int[] c : CUBES) {
            int cube = c[0];
            boolean bonus = c[1] == 1;
            int[] rates = ItemConstants.getCubeTierUpRates(cube);
            String name = cube == -1 ? "Meso reset (potential)" : cube == -2 ? "Meso reset (bonus)" : String.valueOf(cube);
            for (int g = 1; g <= rates.length && g <= 3; g++) {
                Equip e = base.deepCopy();
                long ups = 0, upsCeiling = 0;
                long[] prime = new long[3];
                long kept = 0;
                for (int i = 0; i < n; i++) {
                    setGrade(e, g, bonus);
                    e.setCubeCeiling(bonus, 0);
                    if (ItemHandlerModule.rollPotentialInPlace(e, cube, bonus)) {
                        ups++;
                    } else {
                        kept++;
                        for (int l = 0; l < 3; l++) {
                            if (optionTier(e.getOption(l, bonus)) == g) {
                                prime[l]++;
                            }
                        }
                    }
                }
                Equip f = base.deepCopy();
                setGrade(f, g, bonus);
                f.setCubeCeiling(bonus, 0);
                for (int i = 0; i < n; i++) {
                    if (ItemHandlerModule.rollPotentialInPlace(f, cube, bonus)) {
                        upsCeiling++;
                        setGrade(f, g, bonus);
                    }
                }
                line(name + " " + GRADES[g - 1] + " -> " + GRADES[g], rates[g - 1] / 1e6, ups, n);
                System.out.printf("  %-44s %33s  measured %8.4f%%%n", "  with the ceiling (effective)", "", // hardcode-ok
                        100.0 * upsCeiling / n);
                if (kept > 0) {
                    for (int l = 1; l < 3; l++) {
                        int p = ItemConstants.getPrimeLineChance(cube, bonus, l, gradeVal(g));
                        line("  line " + (l + 1) + " at " + GRADES[g - 1] + " (prime, by option id)", p / 1e6, prime[l],
                                kept);
                    }
                }
            }
        }
        System.out.println(); // hardcode-ok
    }

    private static int gradeVal(int hidden) {
        return ItemGrade.Rare.getVal() + hidden - 1;
    }

    private static void setGrade(Equip e, int hidden, boolean bonus) {
        if (bonus) {
            e.setHiddenOptionBonus((short) hidden, 100);
        } else {
            e.setHiddenOptionBase((short) hidden, 100);
        }
        e.releaseOptions(bonus);
    }

    /** an option's tier from its id (1xxxx rare .. 4xxxx legendary), 0 if unknown */
    private static int optionTier(int option) {
        int t = (option % 100000) / 10000;
        return t >= 1 && t <= 4 ? t : 0;
    }

    // ------------------------------------------------------------------------------------------------ flames
    private static void flames() throws Exception {
        System.out.println("== Flames: tier odds (boss items; ordinary items are 2 tiers lower). Expected = the server's table, UNVERIFIED"); // hardcode-ok
        Method roll = Equip.class.getDeclaredMethod("rollFlameTier", boolean.class);
        roll.setAccessible(true);
        int[][] gms = {{20, 30, 36, 14}, {29, 45, 25, 1}}; // Powerful 3-6, Eternal 4-7: the server's Equip.FLAME_TIERS_* (unverified: no data source)
        for (int eternal = 0; eternal < 2; eternal++) {
            long[] count = new long[10];
            for (int i = 0; i < n; i++) {
                count[(int) roll.invoke(null, eternal == 1)]++;
            }
            int first = eternal == 1 ? 4 : 3;
            for (int t = 0; t < 4; t++) {
                line((eternal == 1 ? "Eternal" : "Powerful") + " tier " + (first + t), gms[eternal][t] / 100.0,
                        count[first + t], n);
            }
        }
        System.out.println("  (drops call randomizeFlameStats(true), so they roll with the Eternal odds)"); // hardcode-ok
        System.out.println(); // hardcode-ok
    }

    // ------------------------------------------------------------------------------------------------ Star Force
    private static void starForce(Equip base) {
        System.out.println("== Star Force: standard mode, no Safeguard. Window rate vs GMS, then rolls " // hardcode-ok
                + "(success x1.05 since Star Catching was folded in)");
        int max = Math.min(GameConstants.getMaxStars(base), 29);
        for (int star = 0; star < max; star++) {
            Equip e = base.deepCopy();
            e.setChuc((short) star);
            int[] ch = GameConstants.starForceChances(e, 0, false, false);
            if (star < GMS_SF.length && (ch[0] != GMS_SF[star][0] || ch[1] != GMS_SF[star][1])) {
                mismatches++;
                System.out.printf("  %2d stars: sent %d/%d but GMS sends %d/%d  <-- MISMATCH%n", star, ch[0], ch[1], // hardcode-ok
                        GMS_SF[star][0], GMS_SF[star][1]);
            }
            long ok = 0, boom = 0;
            for (int i = 0; i < n; i++) {
                int r = GameConstants.rollStarForce(ch, true);
                if (r == 1) {
                    ok++;
                } else if (r == 2) {
                    boom++;
                }
            }
            double shown = Math.min(1.0, ch[0] * 1.05 / 1000);
            line(String.format("%2d -> %2d success (sent %d)", star, star + 1, ch[0]), shown, ok, n);
            if (ch[1] > 0) {
                line(String.format("%2d -> destroyed (share %d)", star, ch[1]), (1 - shown) * ch[1] / 1000.0, boom, n);
            }
        }
    }
}

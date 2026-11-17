package net.swordie.ms.client.character.erdalink;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.HexaConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.loaders.ErdaLinkData;
import net.swordie.ms.loaders.ErdaLinkData.PassiveLine;
import net.swordie.ms.loaders.ErdaLinkData.Stone;
import net.swordie.ms.loaders.HexaCoreData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * What the Erda Link stones give besides their skills: the Boost, Rush and SHINE passives in the stat calculation,
 * and the skill boost stones' extra damage on 5th job skills (the same boost skills the HEXA Matrix uses).
 * <p>
 * Stat units: the Rush and Boost stones give whole numbers (nbdR 2 = +2%), the SHINE stones' % stats are in 1/100 %
 * (bdR 5000 = +50%, the client shows them with two decimals); flat stats (intFX, madX, autX) are plain.
 */
public final class ErdaLinkEffects {

    private static final Map<Integer, Totals> cache = new ConcurrentHashMap<>();

    private ErdaLinkEffects() {
    }

    private static class Totals {
        final int job;
        final Map<BaseStat, Double> rated = new EnumMap<>(BaseStat.class);
        final Map<BaseStat, Double> flat = new EnumMap<>(BaseStat.class);
        final List<Integer> ied = new ArrayList<>();
        // boost skill -> level (summed over its stones)
        final Map<Integer, Integer> boostSkills = new HashMap<>();

        Totals(int job) {
            this.job = job;
        }

        void add(Map<BaseStat, Double> into, BaseStat stat, double value) {
            into.merge(stat, value, Double::sum);
        }
    }

    public static void invalidate(Char chr) {
        cache.remove(chr.getId());
    }

    private static Totals get(Char chr) {
        if (chr.getLevel() < HexaConstants.SIXTH_JOB_LEVEL || !ErdaLinkModule.usesErdaLink(chr.getJob())) {
            return null;
        }
        Totals t = cache.get(chr.getId());
        if (t == null || t.job != chr.getJob()) {
            t = compute(chr);
            cache.put(chr.getId(), t);
        }
        return t;
    }

    private static Totals compute(Char chr) {
        Totals t = new Totals(chr.getJob());
        for (Map.Entry<Stone, Integer> e : ErdaLinkModule.getActiveStones(chr).entrySet()) {
            Stone stone = e.getKey();
            int level = e.getValue();
            boolean hundredths = stone.category == ErdaLinkData.SHINE;
            for (PassiveLine line : stone.passives) {
                int v = line.get(level);
                if (v != 0) {
                    boolean percent = line.stat.endsWith("R") || line.stat.equals("incCrDam");
                    addStat(t, line.stat, hundredths && percent ? v / 100D : v);
                }
            }
            if (stone.category == ErdaLinkData.SKILL) {
                for (int skillId : stone.connectSkills) {
                    if (!HexaCoreData.getBoostTargets(skillId).isEmpty()) {
                        t.boostSkills.merge(skillId, level, Integer::sum);
                    }
                }
            }
        }
        return t;
    }

    private static void addStat(Totals t, String stat, double v) {
        switch (stat) {
            case "nbdR" -> t.add(t.rated, BaseStat.nbd, v);
            case "bdR" -> t.add(t.rated, BaseStat.bd, v);
            case "damR" -> t.add(t.rated, BaseStat.damR, v);
            case "incCrDam" -> t.add(t.rated, BaseStat.crDmg, v);
            case "expR" -> t.add(t.rated, BaseStat.expR, v);
            case "mesoR" -> t.add(t.rated, BaseStat.mesoR, v);
            case "dropR" -> t.add(t.rated, BaseStat.dropR, v);
            case "bufftimeR" -> t.add(t.rated, BaseStat.buffTimeR, v);
            case "summonTimeR" -> t.add(t.rated, BaseStat.summonTimeR, v);
            case "madX" -> t.add(t.rated, BaseStat.mad, v);
            case "autX" -> t.add(t.rated, BaseStat.authForce, v);
            case "allR" -> {
                t.add(t.rated, BaseStat.strR, v);
                t.add(t.rated, BaseStat.dexR, v);
                t.add(t.rated, BaseStat.intR, v);
                t.add(t.rated, BaseStat.lukR, v);
            }
            case "intFX" -> t.add(t.flat, BaseStat.inte, v);
            case "lukFX" -> t.add(t.flat, BaseStat.luk, v);
            case "ignoreMobpdpR" -> t.ied.add((int) Math.round(v));
            default -> {
                // abnormalDamR (damage to targets with an abnormal status) has no stat here
            }
        }
    }

    /** Stats that their % raises, and the % / rate stats themselves */
    public static double getStat(Char chr, BaseStat baseStat) {
        Totals t = get(chr);
        return t == null ? 0 : t.rated.getOrDefault(baseStat, 0D);
    }

    /** Flat INT / LUK, not raised by % (like the HEXA main stat) */
    public static double getFlatStat(Char chr, BaseStat baseStat) {
        Totals t = get(chr);
        return t == null ? 0 : t.flat.getOrDefault(baseStat, 0D);
    }

    /** Ignore defense, one entry per stone line (stacks multiplicatively) */
    public static List<Integer> getIgnoreDefense(Char chr) {
        Totals t = get(chr);
        return t == null ? List.of() : t.ied;
    }

    /** @return the multiplier the skill boost stones put on a 5th job skill's damage (1 if none) */
    public static double getBoostMultiplier(Char chr, int skillId) {
        Totals t = get(chr);
        if (t == null || t.boostSkills.isEmpty()) {
            return 1;
        }
        double mult = 1;
        for (Map.Entry<Integer, Integer> e : t.boostSkills.entrySet()) {
            if (HexaCoreData.getBoostTargets(e.getKey()).contains(skillId)) {
                mult *= 1 + HexaCoreData.getBoostDamR(e.getKey(), e.getValue()) / 100D;
            }
        }
        return mult;
    }
}

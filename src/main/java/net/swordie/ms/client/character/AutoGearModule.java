package net.swordie.ms.client.character;

import net.swordie.ms.client.character.hyperstats.HyperStatsModule;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.enums.ScrollStat;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.handlers.item.ItemHandlerModule;
import net.swordie.ms.handlers.item.ItemUpgradeHandler;
import net.swordie.ms.loaders.ItemData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Gear and stat upkeep done for you, step by step (@optimize): wear the best gear, scroll and Star Force it, add
 * missing potentials, reroll weak flames, cube toward good lines and spend Hyper Stat points. Each step pays what it
 * would cost by hand (Spell Traces, cubes or NX), except the mesos for Star Force, flames and cube fees, which cost
 * {@link GameConstants#OPTIMIZE_MESO_MARKUP_PERCENT}% more while a step runs, and each run's convenience fee
 * ({@link #convenienceFee}). Bots pay neither.
 */
public final class AutoGearModule {

    /** Eternal Rebirth Flames used on one item per run; the best result is kept. */
    public static final int FLAMES_PER_ITEM = 5;
    /** Cubes per item spent on a better goal than "Strong 2-line" once that one is met. */
    public static final int HIGHER_GOAL_CUBES = 100;
    /** Hyper Stats raised besides the main stat (Arcane Power once the 5th job is done). */
    private static final int[] HYPER_STATS = {80000410, 80000412, 80000413, 80000414, 80000419, 80000409, 80000422};

    /** Depth of @optimize steps running on this thread (their meso prices carry the markup). */
    private static final ThreadLocal<Integer> OPTIMIZING = ThreadLocal.withInitial(() -> 0);

    private AutoGearModule() {
    }

    /** Whether an @optimize step is running on this thread. */
    public static boolean isOptimizing() {
        return OPTIMIZING.get() > 0;
    }

    private static <T> T optimizing(Supplier<T> step) {
        OPTIMIZING.set(OPTIMIZING.get() + 1);
        try {
            return step.get();
        } finally {
            OPTIMIZING.set(OPTIMIZING.get() - 1);
        }
    }

    /** The convenience fee of one @optimize run: a share of the mesos carried now (none for bots). */
    public static long convenienceFee(Char chr) {
        if (ServerHooks.isAutomated(chr)) {
            return 0;
        }
        return chr.getMoney() / 100 * net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.OPTIMIZE_FEE);
    }

    /** Takes the convenience fee at the start of a run. @return the mesos taken */
    public static long chargeConvenienceFee(Char chr) {
        long fee = convenienceFee(chr);
        if (fee > 0) {
            chr.deductMoney(fee, ProgressionLog.Sink.FEES);
        }
        return fee;
    }

    /** One Eternal Rebirth Flame roll as @optimize charges it. */
    public static long flameMesos(Char chr) {
        return optimizing(() -> AutoEnhanceModule.optimizeMesos(chr, AutoEnhanceModule.getFlameMesos(true)));
    }

    /** Puts on the best gear being carried (the same as "Equip all" in @upgrades). */
    public static String equipBest(Char chr) {
        return UpgradeFinder.equipAll(chr);
    }

    /** Scrolls and Star Forces every worn item as @enhance does (normal scrolls), to each item's full stars. */
    public static String enhanceAll(Char chr) {
        return optimizing(() -> enhanceAllNow(chr));
    }

    private static String enhanceAllNow(Char chr) {
        int done = 0, skipped = 0;
        String why = "";
        for (var plan : AutoEnhanceModule.getPlans(chr, AutoEnhanceModule.Mode.NORMAL, true)) {
            String res = AutoEnhanceModule.enhance(chr, List.of(plan));
            if (res != null && res.startsWith("You need")) {
                skipped++;
                why = res;
            } else {
                done++;
            }
        }
        String nStar = forceUpgradeWorn(chr);
        if (done == 0 && skipped == 0) {
            return "Your worn gear is already fully scrolled and starred." + nStar;
        }
        return done + " item(s) scrolled and starred" + (skipped == 0 ? "." : "; " + skipped + " left: " + why) + nStar;
    }

    /**
     * Worn items Star Force can't take but N-star enhancement scrolls can (Astra secondaries, made without upgrade
     * slots): each gets the highest such scroll from the Use tab that raises it.
     * @return a sentence for the result ("" when no such item is worn)
     */
    private static String forceUpgradeWorn(Char chr) {
        int raised = 0, waiting = 0;
        for (Equip e : wornEquips(chr)) {
            int max = GameConstants.getMaxStars(e);
            if (!ItemConstants.isUpgradable(e.getItemId()) || AutoEnhanceModule.canStarForce(e) || e.getChuc() >= max) {
                continue;
            }
            Item best = null;
            int bestStars = 0;
            for (Item it : new ArrayList<>(chr.getConsumeInventory().getItems())) {
                var info = ItemData.getItemInfoByID(it.getItemId());
                int stars = info == null ? 0
                        : info.getScrollStats().getOrDefault(ScrollStat.forceUpgrade, 0);
                if (stars > e.getChuc() && stars <= max && stars > bestStars) {
                    best = it;
                    bestStars = stars;
                }
            }
            if (best != null && ItemUpgradeHandler.forceUpgrade(chr, e, bestStars) == null) {
                chr.consumeItem(best, 1);
                raised++;
            } else {
                waiting++;
            }
        }
        String res = raised > 0 ? " " + raised + " item(s) without Star Force got an N-star enhancement scroll." : "";
        if (waiting > 0) {
            res += " " + waiting + " worn item(s) can't be Star Forced (Astra secondaries): they take N-star"
                    + " enhancement scrolls, and you have none that raise them.";
        }
        return res;
    }

    /** Adds a potential and a bonus potential to every worn item that can have one and has none. */
    public static int addPotentials(Char chr) {
        int added = 0;
        for (Equip e : wornEquips(chr)) {
            if (!ItemConstants.canEquipHavePotential(e)) {
                continue;
            }
            for (boolean bonus : new boolean[]{false, true}) {
                if (e.getOption(0, bonus) == 0 && AutoEnhanceModule.addPotential(chr, e, bonus).startsWith("Added")) {
                    added++;
                }
            }
        }
        return added;
    }

    /** How good an item's flame is for this character: main stat, attack, all stat %, and boss/damage on a weapon. */
    public static int flameScore(Char chr, Equip e) {
        var main = mainStat(chr);
        int stat = switch (main) {
            case str -> e.getfSTR();
            case dex -> e.getfDEX();
            case inte -> e.getfINT();
            case luk -> e.getfLUK();
            default -> e.getfHP() / 50;
        };
        int att = main == BaseStat.inte ? e.getfMATT() : e.getfATT();
        int score = stat + att * 4 + e.getfAllStat() * 10;
        if (ItemConstants.isWeapon(e.getItemId())) {
            score += e.getfBoss() * 2 + e.getfDamage() * 3;
        }
        return score;
    }

    /** Mesos charged on top of the flames for each item @optimize flames, as it may go back to the old flame. */
    public static long flameRevertTax() {
        return 2 * AutoEnhanceModule.getFlameMesos(true);
    }

    /** Mesos of cube fees for one cube on each worn item that can be cubed (potential and bonus potential). */
    public static long cubeFeePerRound(Char chr) {
        return optimizing(() -> cubeFeePerRoundNow(chr));
    }

    private static long cubeFeePerRoundNow(Char chr) {
        long fee = 0;
        for (Equip e : wornEquips(chr)) {
            for (boolean bonus : new boolean[]{false, true}) {
                if (CubingModule.getCandidates(chr, bonus).contains(e)) {
                    fee += CubingModule.getCubeMesoFee(chr, e);
                }
            }
        }
        return fee;
    }

    /** NX charged on top of the cubes for each item @optimize may give its old lines back (cubes paid in NX). */
    public static int cubeRevertTax(boolean bonus) {
        return 2 * CubingModule.getCubeNx(bonus);
    }

    /**
     * The revert tax in mesos when the item's cubes are meso resets: two resets at its current rank; -1 when it pays
     * in NX ({@link #cubeRevertTax}).
     */
    public static long cubeRevertTaxMesos(Char chr, Equip e, boolean bonus) {
        if (CubingModule.getMesoResetCost(chr, e, bonus) < 0) {
            return -1;
        }
        return 2 * ItemHandlerModule.potentialMesoResetCost(e, bonus);
    }

    /**
     * A few Eternal Rebirth Flames on one item, keeping the best result (the old flame if none beats it); the
     * revert tax is paid first. @return whether the flame got better
     */
    public static boolean reflame(Char chr, Equip e, int rolls) {
        return optimizing(() -> reflameNow(chr, e, rolls));
    }

    private static boolean reflameNow(Char chr, Equip e, int rolls) {
        if (!AutoEnhanceModule.canFlame(e)) {
            return false;
        }
        long tax = flameRevertTax();
        if (chr.getMoney() < tax + flameMesos(chr)) {
            return false;
        }
        chr.deductMoney(tax, ProgressionLog.Sink.OTHER);
        int before = flameScore(chr, e);
        Equip best = e.deepCopy();
        int bestScore = before;
        for (int i = 0; i < rolls; i++) {
            if (!AutoEnhanceModule.flame(chr, e, true).startsWith("Rerolled")) {
                break;
            }
            int score = flameScore(chr, e);
            if (score > bestScore) {
                bestScore = score;
                best = e.deepCopy();
            }
        }
        if (flameScore(chr, e) < bestScore) {
            e.copyFlameStatsFrom(best);
            e.updateToChar(chr);
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
        return bestScore > before;
    }

    /** Rerolls the flames of every worn item, keeping the best of a few rolls each. @return items improved */
    public static int reflameAll(Char chr, int rollsPerItem) {
        int improved = 0;
        for (Equip e : wornEquips(chr)) {
            if (reflame(chr, e, rollsPerItem)) {
                improved++;
            }
        }
        return improved;
    }

    /** Flame scores Eternal Flames roll on an item, sampled once per item and main stat: the goal is this share. */
    private static final double FLAME_GOAL_PERCENTILE = 0.8;
    private static final int FLAME_GOAL_SAMPLES = 200;
    private static final java.util.Map<Long, Integer> FLAME_GOALS = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * A good flame for this item and character: better than {@link #FLAME_GOAL_PERCENTILE} of Eternal Flame rolls
     * on it (a few flames usually get there; past it, more flames rarely pay).
     */
    public static int flameGoal(Char chr, Equip e) {
        long key = (long) e.getItemId() * 64 + mainStat(chr).ordinal() * 2L
                + (JobConstants.isMageEquipJob(chr.getJob()) ? 1 : 0);
        return FLAME_GOALS.computeIfAbsent(key, k -> {
            Equip copy = e.deepCopy();
            int[] scores = new int[FLAME_GOAL_SAMPLES];
            for (int i = 0; i < scores.length; i++) {
                copy.randomizeFlameStats(true);
                scores[i] = flameScore(chr, copy);
            }
            java.util.Arrays.sort(scores);
            return scores[(int) (scores.length * FLAME_GOAL_PERCENTILE)];
        });
    }

    /** What a flame run on the items below their flame goal did. */
    public static class FlameRun {
        public int improved, tried, atGoal;
    }

    /**
     * Flames only the worn items whose flame is below {@link #flameGoal} (new gear, a weak roll), keeping the best of
     * a few rolls each; the others are left as they are. At most {@code maxMesos} mesos (flames and revert tax).
     */
    public static FlameRun reflameBelowGoal(Char chr, int rollsPerItem, long maxMesos) {
        FlameRun run = new FlameRun();
        long perItem = rollsPerItem * flameMesos(chr) + flameRevertTax();
        long left = maxMesos;
        for (Equip e : wornEquips(chr)) {
            if (!AutoEnhanceModule.canFlame(e)) {
                continue;
            }
            if (flameScore(chr, e) >= flameGoal(chr, e)) {
                run.atGoal++;
                continue;
            }
            if (left < perItem) {
                break;
            }
            long before = chr.getMoney();
            run.tried++;
            if (reflame(chr, e, rollsPerItem)) {
                run.improved++;
            }
            left -= Math.max(0, before - chr.getMoney());
        }
        return run;
    }

    /** Mesos a flame run can cost at most (every roll on every worn item that can be flamed, plus the revert tax). */
    public static long maxFlameMesos(Char chr, int rollsPerItem) {
        return flameItems(chr) * (rollsPerItem * flameMesos(chr) + flameRevertTax());
    }

    /** The revert tax part of {@link #maxFlameMesos}. */
    public static long maxFlameTax(Char chr) {
        return flameItems(chr) * flameRevertTax();
    }

    private static long flameItems(Char chr) {
        return wornEquips(chr).stream().filter(AutoEnhanceModule::canFlame).count();
    }

    /** What a cube run did. */
    public static class CubeRun {
        public int cubes;
        /** NX spent, the revert tax included */
        public long nx;
        /** NX of revert tax */
        public long taxNx;
        /** mesos of cube fees (the Enhance window's fee per cube) */
        public long mesoFee;
        /** mesos of meso resets (after the cubes ran out) and of revert tax paid in mesos */
        public long resetMesos, taxMesos;
        public int goalsMet;
        /** items already at "Strong 2-line", left as they are without {@code recubeGood} (not in goalsMet) */
        public int leftAlone;
        /** what the better-goal cubes may still spend this run (mesos of fees, NX) */
        long higherMesosLeft = Long.MAX_VALUE, higherNxLeft = Long.MAX_VALUE;
    }

    /**
     * Cubes every worn item toward "Strong 2-line" (main and bonus potential), at most {@code cubesPerItem} each; an
     * item that meets it then gets up to {@code higherCubes} toward a better goal (3 lines, or boss and ignore defense
     * on a weapon), keeping its old lines if that one isn't reached, as with a Black Cube.
     */
    public static CubeRun cubeAll(Char chr, int cubesPerItem, int bonusCubesPerItem, int higherCubes) {
        return cubeAll(chr, cubesPerItem, bonusCubesPerItem, higherCubes, true);
    }

    /** As above; without {@code recubeGood}, a potential that already has 2 good lines is left as it is. */
    public static CubeRun cubeAll(Char chr, int cubesPerItem, int bonusCubesPerItem, int higherCubes,
                                  boolean recubeGood) {
        return optimizing(() -> cubeAllNow(chr, cubesPerItem, bonusCubesPerItem, higherCubes, recubeGood,
                Long.MAX_VALUE, Long.MAX_VALUE));
    }

    /**
     * As above, the better-goal cubes capped at {@code higherMesos} of cube fees and {@code higherNx} NX (revert tax
     * included) for the whole run; the cubes toward "Strong 2-line" aren't capped.
     */
    public static CubeRun cubeAll(Char chr, int cubesPerItem, int bonusCubesPerItem, int higherCubes,
                                  boolean recubeGood, long higherMesos, long higherNx) {
        return optimizing(() -> cubeAllNow(chr, cubesPerItem, bonusCubesPerItem, higherCubes, recubeGood,
                higherMesos, higherNx));
    }

    private static CubeRun cubeAllNow(Char chr, int cubesPerItem, int bonusCubesPerItem, int higherCubes,
                                      boolean recubeGood, long higherMesos, long higherNx) {
        CubeRun run = new CubeRun();
        run.higherMesosLeft = higherMesos;
        run.higherNxLeft = higherNx;
        long nxBefore = chr.getNX();
        for (Equip e : wornEquips(chr)) {
            for (boolean bonus : new boolean[]{false, true}) {
                if (!CubingModule.getCandidates(chr, bonus).contains(e)) {
                    continue;
                }
                var presets = CubingModule.getPresets(chr, e, bonus);
                var goal = presets.stream().filter(g -> g.name.startsWith(bonus ? "Strong bonus 2-line" : "Strong 2-line"))
                        .findFirst().orElse(null);
                if (goal == null) {
                    goal = presets.stream().filter(g -> g.name.equals("Legendary rank")).findFirst().orElse(null);
                }
                if (goal == null) {
                    continue;
                }
                int special = bonus ? 0 : specialLines(chr, e);
                if (special >= 3) {
                    continue; // perfect crit gloves, cooldown hat or boss/IED weapon: never cubed
                }
                if (special == 2) {
                    run.goalsMet++; // worth more than 2 lines of main stat; only a perfect result replaces it
                    if (higherCubes > 0) {
                        run.cubes += cubeHigher(chr, e, false, higherCubes, run);
                    }
                    continue;
                }
                if (!recubeGood && !goal.name.equals("Legendary rank") && CubingModule.meets(e, goal.wanted, bonus)) {
                    run.leftAlone++;
                    continue;
                }
                var r = CubingModule.cube(chr, e, bonus, goal.wanted, bonus ? bonusCubesPerItem : cubesPerItem);
                run.cubes += r.cubesUsed;
                run.mesoFee += r.mesoFee;
                run.resetMesos += r.resetMesos;
                if (r.goalMet) {
                    run.goalsMet++;
                    if (higherCubes > 0) {
                        run.cubes += cubeHigher(chr, e, bonus, higherCubes, run);
                    }
                }
            }
        }
        run.nx = nxBefore - chr.getNX();
        return run;
    }

    /** Cubes toward the best goal an item has, keeping the old lines if it isn't reached. @return cubes used */
    public static int cubeHigher(Char chr, Equip e, boolean bonus, int maxCubes) {
        return optimizing(() -> cubeHigher(chr, e, bonus, maxCubes, null));
    }

    /**
     * As above; the revert tax is paid first (mesos for meso resets, else NX) and added to {@code run}. Can't pay it:
     * the item is left alone.
     */
    private static int cubeHigher(Char chr, Equip e, boolean bonus, int maxCubes, CubeRun run) {
        int special = bonus ? 0 : specialLines(chr, e);
        if (special >= 3) {
            return 0;
        }
        String[] order = bonus ? new String[]{"Perfect bonus 3-line"}
                : special > 0 && ItemConstants.isGlove(e.getItemId()) ? new String[]{"Perfect 3-line Crit Damage"}
                : special > 0 && ItemConstants.isHat(e.getItemId()) ? new String[]{"Perfect cooldown hat"}
                : new String[]{"Perfect boss mix", "Perfect 3-line", "Bossing mix"};
        var presets = CubingModule.getPresets(chr, e, bonus);
        CubingModule.Goal goal = null;
        for (String name : order) {
            goal = presets.stream().filter(g -> g.name.startsWith(name)).findFirst().orElse(null);
            if (goal != null) {
                break;
            }
        }
        if (goal == null || CubingModule.meets(e, goal.wanted, bonus)) {
            return 0;
        }
        long taxMesos = Math.max(0, cubeRevertTaxMesos(chr, e, bonus));
        int tax = taxMesos > 0 ? 0 : cubeRevertTax(bonus);
        if (chr.getNX() < tax || chr.getMoney() < taxMesos) {
            return 0;
        }
        long maxMesos = Long.MAX_VALUE, maxNx = Long.MAX_VALUE;
        if (run != null) {
            // the run's caps, less the tax
            maxMesos = run.higherMesosLeft - taxMesos;
            maxNx = run.higherNxLeft - tax;
            if (maxMesos <= 0 || maxNx < 0 || (tax > 0 && maxNx == 0)) {
                return 0;
            }
        }
        if (tax > 0) {
            chr.deductNX(tax);
        }
        if (taxMesos > 0) {
            chr.deductMoney(taxMesos, ProgressionLog.Sink.CUBES);
        }
        if (run != null) {
            run.taxNx += tax;
            run.taxMesos += taxMesos;
        }
        List<Integer> before = new ArrayList<>(e.getOptions());
        var r = CubingModule.cube(chr, e, bonus, goal.wanted, maxCubes, maxMesos, maxNx);
        if (run != null) {
            run.mesoFee += r.mesoFee;
            run.resetMesos += r.resetMesos;
            run.higherMesosLeft -= taxMesos + r.mesoFee + r.resetMesos;
            run.higherNxLeft -= tax + r.nxSpent;
        }
        if (!r.goalMet) {
            if (!bonus) {
                e.updateDecentSkills(chr, false);
            }
            e.setOptions(before);
            if (!bonus) {
                e.updateDecentSkills(chr, true);
            }
            e.updateToChar(chr);
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
        return r.cubesUsed;
    }

    /**
     * Spends unspent Hyper Stat points (from level 140): the main stat and the damage lines, the cheapest next level
     * first so they rise together. Critical Rate stops once crit reaches 100%. @return levels raised
     */
    public static int spendHyperStats(Char chr) {
        if (chr.getLevel() < 140 || chr.getHyperStatsManager() == null) {
            return 0;
        }
        int preset = chr.getHyperStatsManager().getCurrentPreset();
        List<Integer> wanted = new ArrayList<>();
        wanted.add(switch (mainStat(chr)) {
            case str -> SkillConstants.HYPER_STAT_STR;
            case dex -> SkillConstants.HYPER_STAT_DEX;
            case inte -> SkillConstants.HYPER_STAT_INT;
            case luk -> SkillConstants.HYPER_STAT_LUK;
            default -> SkillConstants.HYPER_STAT_HP;
        });
        for (int id : HYPER_STATS) {
            wanted.add(id);
        }
        if (chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            wanted.add(Job.HYPER_STAT_ARCANE_FORCE);
        }
        int raised = 0;
        while (raised < 500) {
            int available = HyperStatsModule.getTotalHyperStatSp(chr) - chr.getSpentHyperSp();
            int best = 0, bestCost = Integer.MAX_VALUE;
            for (int id : wanted) {
                Skill sk = chr.getSkill(id, true);
                if (sk == null || sk.getCurrentLevel() >= sk.getMaxLevel()) {
                    continue;
                }
                if (id == SkillConstants.HYPER_STAT_CRITICAL_RATE && chr.getTotalStat(BaseStat.cr) >= 100) {
                    continue;
                }
                int cost = SkillConstants.getNeededSpForHyperStatSkill(sk.getCurrentLevel() + 1);
                if (cost < bestCost) {
                    best = id;
                    bestCost = cost;
                }
            }
            if (best == 0 || bestCost > available) {
                break;
            }
            HyperStatsModule.increaseHyperStatSkill(chr, preset, best);
            raised++;
        }
        return raised;
    }

    /**
     * Prime main-potential lines that beat main stat on this item: Crit Damage on gloves, Cooldown Reduction on hats,
     * Boss Damage / Ignore DEF (with ATT) on weapons, secondaries and emblems. 3 = perfect, 0 = none.
     */
    static int specialLines(Char chr, Equip e) {
        int id = e.getItemId();
        if (ItemConstants.isGlove(id)) {
            return primeLines(e, BaseStat.crDmg);
        }
        if (ItemConstants.isHat(id)) {
            return primeLines(e, BaseStat.reduceCooltime);
        }
        if (ItemConstants.isWeapon(id) || ItemConstants.isSecondary(id) || ItemConstants.isEmblem(id)) {
            int bossing = primeLines(e, BaseStat.bd) + primeLines(e, BaseStat.ied);
            if (bossing == 0) {
                return 0;
            }
            BaseStat att = JobConstants.isMageEquipJob(chr.getJob()) ? BaseStat.madR : BaseStat.padR;
            return Math.min(3, bossing + primeLines(e, att));
        }
        return 0;
    }

    /** Lines of this stat at (or near) the best Legendary value; lower-tier lines don't count. */
    private static int primeLines(Equip e, BaseStat stat) {
        int max = CubingModule.maxLegendaryLine(e, false, stat);
        return max <= 0 ? 0 : e.getPotentialStat(stat, false) * 4 / (max * 3);
    }

    /** The job's main stat; STR for jobs that have none set (beginners). */
    private static BaseStat mainStat(Char chr) {
        BaseStat main = JobConstants.getMainStatByJob(chr.getJob());
        return main == null ? BaseStat.str : main;
    }

    private static List<Equip> wornEquips(Char chr) {
        List<Equip> res = new ArrayList<>();
        for (Item it : new ArrayList<>(chr.getEquippedInventory().getItems())) {
            if (it instanceof Equip e && !e.isCash()) {
                res.add(e);
            }
        }
        return res;
    }
}

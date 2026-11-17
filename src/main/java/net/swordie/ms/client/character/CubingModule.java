package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.ItemOption;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.ItemGrade;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.handlers.item.ItemHandlerModule;
import net.swordie.ms.loaders.ItemData;

import java.util.*;

/**
 * Cubing for @enhance (scripts/npc/autoEnhance.py): the potential (Red Cubes) or the bonus potential (Bonus Potential
 * Cubes) of worn items only. Each roll uses one owned cube plus the Enhance window's meso fee for the item (a
 * fee-waiving glass covers it as in the window). With no cube left, players reset with mesos as the v271 window does
 * (the cash shop sells no cubes): the potential cost table's price for the item's rank, no fee. Without the meso
 * reset (setting enhance.heroic.mesoPotentialReset off) and for bots, the cube's old NX price is paid instead.
 * Every roll counts toward the cube ceiling as by hand. Cubing runs on the server in one go: once, or until a goal
 * (a preset or a custom combination) is met or the cube limit is reached.
 */
public final class CubingModule {

    /** NX per cube when the character has none of that cube and can't reset with mesos. */
    public static final int RED_CUBE_NX = 1200;
    public static final int BONUS_CUBE_NX = 2400;
    /** Most cubes one AutoCuber run may use. */
    public static final int MAX_CUBES_PER_RUN = 5000;
    /** Goal key: the potential's rank (1 Rare - 4 Legendary). */
    public static final String RANK_KEY = "Rank";

    /** The stats a custom goal can ask for (BaseStat.getBaseStatByName names). */
    public static final String[] CUSTOM_STATS = {
            "STR %", "DEX %", "INT %", "LUK %", "All Stat %", "HP %",
            "ATT %", "Magic ATT %", "Boss Damage %", "Ignore Defense %", "Total Damage %",
            "Crit Damage %", "Crit Rate %", "Item Drop %", "Meso Drop %", "Cooldown Reduction",
            "Flat STR", "Flat DEX", "Flat INT", "Flat LUK", "Flat All Stat", "Flat ATT", "Flat Magic ATT",
            "STR Per 10 levels", "DEX Per 10 levels", "INT Per 10 levels", "LUK Per 10 levels"
    };

    private CubingModule() {
    }

    public static int getCubeId(boolean bonus) {
        return bonus ? ItemConstants.BONUS_POT_CUBE : ItemConstants.RED_CUBE;
    }

    public static int getCubeNx(boolean bonus) {
        return bonus ? BONUS_CUBE_NX : RED_CUBE_NX;
    }

    /**
     * Mesos per cube on this item, as the Enhance window charges them (bots don't pay it, the budget bot does);
     * @optimize's markup while it runs.
     */
    public static long getCubeMesoFee(Char chr, Equip equip) {
        if (equip == null || !ServerHooks.paysAsPlayer(chr)) {
            return 0;
        }
        return AutoEnhanceModule.optimizeMesos(chr, ItemHandlerModule.cubeMesoFee(chr, equip));
    }

    /**
     * Mesos of one meso reset of this item's (bonus) potential as @enhance charges it (@optimize's markup while it
     * runs), or -1 when out of cubes means NX instead (setting off, bots).
     */
    public static long getMesoResetCost(Char chr, Equip equip, boolean bonus) {
        if (equip == null || ServerHooks.isAutomated(chr)) {
            return -1;
        }
        long cost = ItemHandlerModule.potentialMesoResetCost(equip, bonus);
        return cost < 0 ? -1 : AutoEnhanceModule.optimizeMesos(chr, cost);
    }

    /** @return the worn items whose potential (or bonus potential) can be cubed */
    public static List<Equip> getCandidates(Char chr, boolean bonus) {
        List<Equip> list = new ArrayList<>();
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (item instanceof Equip equip && !equip.isCash() && ItemConstants.canEquipHavePotential(equip)
                    && equip.getOption(0, bonus) > 0 && !isZeroSecondWeapon(chr, equip)) {
                list.add(equip);
            }
        }
        list.sort(Comparator.comparingInt(e -> -e.getBagIndex()));
        return list;
    }

    /** @return 1 Rare, 2 Epic, 3 Unique, 4 Legendary (0 if none) */
    public static int getRank(Equip equip, boolean bonus) {
        int opt = equip.getOption(0, bonus);
        return opt <= 0 ? 0 : Math.max(1, Math.min(4, opt / 10000));
    }

    public static String rankName(int rank) {
        switch (rank) {
            case 1:
                return "#b(Rare)#k";
            case 2:
                return "#d(Epic)#k";
            case 3:
                return "#r(Unique)#k";
            case 4:
                return "#g(Legendary)#k";
        }
        return "(None)";
    }

    /** The rank and lines of the potential (or bonus potential), one line each. */
    public static String describe(Equip equip, boolean bonus) {
        StringBuilder sb = new StringBuilder(rankName(getRank(equip, bonus)));
        for (int i = 0; i < 3; i++) {
            int opt = equip.getOption(i, bonus);
            if (opt <= 0) {
                continue;
            }
            ItemOption io = ItemData.getItemOptionById(opt);
            String line = io == null ? String.valueOf(opt) : io.getString(equip.getReqLevel()).replace("#", "");
            sb.append("\r\n  - ").append(line);
        }
        return sb.toString();
    }

    // Goals ----------------------------------------------------------------------------------------------------------

    /** A named goal: every stat must reach its amount (all stat % counts towards each stat %). */
    public static class Goal {
        public final String name;
        public final Map<String, Integer> wanted;

        public Goal(String name, Map<String, Integer> wanted) {
            this.name = name;
            this.wanted = wanted;
        }
    }

    private static Goal goal(String name, Object... kv) {
        Map<String, Integer> m = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) {
            m.put((String) kv[i], (Integer) kv[i + 1]);
        }
        return new Goal(name, m);
    }

    /** Ready-made goals for this character and item, including the best three prime-line result the v232 data allows. */
    public static List<Goal> getPresets(Char chr, Equip equip, boolean bonus) {
        List<Goal> list = new ArrayList<>();
        int id = equip.getItemId();
        boolean mage = JobConstants.isMageEquipJob(chr.getJob());
        String att = mage ? "Magic ATT %" : "ATT %";
        BaseStat main = JobConstants.getMainStatByJob(chr.getJob());
        String stat;
        BaseStat statType;
        if (JobConstants.isXenon(chr.getJob())) {
            stat = "All Stat %";
            statType = BaseStat.allStatR;
        } else if (JobConstants.isDemonAvenger(chr.getJob())) {
            stat = "HP %";
            statType = BaseStat.mhpR;
        } else {
            stat = main == BaseStat.dex ? "DEX %" : main == BaseStat.inte ? "INT %"
                    : main == BaseStat.luk ? "LUK %" : "STR %";
            statType = main == BaseStat.dex ? BaseStat.dexR : main == BaseStat.inte ? BaseStat.intR
                    : main == BaseStat.luk ? BaseStat.lukR : BaseStat.strR;
        }
        if (getRank(equip, bonus) < 4) {
            list.add(goal("Legendary rank", RANK_KEY, 4));
        }
        boolean attackSlot = ItemConstants.isWeapon(id) || ItemConstants.isSecondary(id) || ItemConstants.isEmblem(id);
        BaseStat attType = mage ? BaseStat.madR : BaseStat.padR;
        int maxAtt = maxLegendaryLine(equip, bonus, attType);
        int maxStat = maxLegendaryLine(equip, bonus, statType);
        if (!bonus) {
            if (attackSlot) {
                if (maxAtt > 0) {
                    list.add(goal("Perfect 3-line " + att + " (" + (maxAtt * 3) + "%+)", att, maxAtt * 3));
                }
                if (!ItemConstants.isEmblem(id)) {
                    int maxBoss = maxLegendaryLine(equip, false, BaseStat.bd);
                    if (maxBoss > 0 && maxAtt > 0) {
                        list.add(goal("Perfect boss mix: " + (maxBoss * 2) + "% Boss + " + maxAtt + "% " + att,
                                att, maxAtt, "Boss Damage %", maxBoss * 2));
                    }
                }
                int maxIed = maxLegendaryLine(equip, false, BaseStat.ied);
                if (maxIed > 0 && maxAtt > 0) {
                    list.add(goal("Bossing mix: 2-line " + att + " + " + maxIed + "% Ignore DEF",
                            att, maxAtt * 2, "Ignore Defense %", maxIed));
                }
                list.add(goal("Strong 2-line " + att, att, Math.max(1, maxAtt * 2 - 3)));
            } else {
                if (ItemConstants.isGlove(id)) {
                    int maxCrit = maxLegendaryLine(equip, false, BaseStat.crDmg);
                    if (maxCrit > 0) {
                        list.add(goal("Perfect 3-line Crit Damage (" + (maxCrit * 3) + "%+)",
                                "Crit Damage %", maxCrit * 3));
                        list.add(goal("2-line Crit Damage (" + (maxCrit * 2) + "%+)",
                                "Crit Damage %", maxCrit * 2));
                    }
                }
                if (ItemConstants.isHat(id)) {
                    int maxCooldown = maxLegendaryLine(equip, false, BaseStat.reduceCooltime);
                    if (maxCooldown > 0) {
                        list.add(goal("Perfect cooldown hat (-" + (maxCooldown * 3) + " sec)",
                                "Cooldown Reduction", maxCooldown * 3));
                        list.add(goal("Cooldown hat (-" + (maxCooldown * 2) + " sec)",
                                "Cooldown Reduction", maxCooldown * 2));
                    }
                }
                if (maxStat > 0) {
                    list.add(goal("Perfect 3-line " + stat + " (" + (maxStat * 3) + "%+)", stat, maxStat * 3));
                    list.add(goal("Strong 2-line " + stat, stat, Math.max(1, maxStat * 2 - 3)));
                }
                if (isFarmingAccessory(id)) {
                    int maxDrop = maxLegendaryLine(equip, false, BaseStat.dropR);
                    int maxMeso = maxLegendaryLine(equip, false, BaseStat.mesoR);
                    if (maxDrop > 0) {
                        list.add(goal("Perfect item-drop gear (" + (maxDrop * 3) + "%+)",
                                "Item Drop %", maxDrop * 3));
                    }
                    if (maxMeso > 0) {
                        list.add(goal("Perfect meso gear (" + (maxMeso * 3) + "%+)",
                                "Meso Drop %", maxMeso * 3));
                    }
                }
            }
        } else {
            if (attackSlot) {
                if (maxAtt > 0) {
                    list.add(goal("Perfect bonus 3-line " + att + " (" + (maxAtt * 3) + "%+)",
                            att, maxAtt * 3));
                    list.add(goal("Strong bonus 2-line " + att, att, Math.max(1, maxAtt * 2 - 3)));
                }
            } else {
                if (ItemConstants.isGlove(id)) {
                    int maxCrit = maxLegendaryLine(equip, true, BaseStat.crDmg);
                    if (maxCrit > 0) {
                        list.add(goal("Perfect bonus Crit Damage (" + (maxCrit * 3) + "%+)",
                                "Crit Damage %", maxCrit * 3));
                    }
                }
                if (maxStat > 0) {
                    list.add(goal("Perfect bonus 3-line " + stat + " (" + (maxStat * 3) + "%+)",
                            stat, maxStat * 3));
                    list.add(goal("Strong bonus 2-line " + stat, stat, Math.max(1, maxStat * 2 - 2)));
                }
            }
        }
        return list;
    }

    /** Highest value one prime Legendary line can roll on this exact item in the server's v232 option data. */
    static int maxLegendaryLine(Equip equip, boolean bonus, BaseStat stat) {
        int level = Math.max(1, (equip.getReqLevel() + equip.getiIncReq()) / 10);
        int max = 0;
        for (ItemOption option : ItemConstants.getOptionsByEquip(equip, bonus, ItemGrade.Legendary)) {
            max = Math.max(max, option.getStatValuesByLevel(level).getOrDefault(stat, 0D).intValue());
        }
        return max;
    }

    /** Accessories whose regular potential pool can roll meso and item-drop lines. */
    private static boolean isFarmingAccessory(int id) {
        return ItemConstants.isRing(id) || ItemConstants.isPendant(id) || ItemConstants.isFaceAccessory(id)
                || ItemConstants.isEyeAccessory(id) || ItemConstants.isEarrings(id);
    }

    /** @return whether the potential (or bonus potential) meets the goal */
    public static boolean meets(Equip equip, Map<String, Integer> wanted, boolean bonus) {
        if (wanted == null || wanted.isEmpty()) {
            return false;
        }
        for (Map.Entry<String, Integer> e : wanted.entrySet()) {
            int want = e.getValue();
            if (RANK_KEY.equals(e.getKey())) {
                if (getRank(equip, bonus) < want) {
                    return false;
                }
                continue;
            }
            BaseStat bs = BaseStat.getBaseStatByName(e.getKey());
            if (bs == null) {
                continue;
            }
            int amount = equip.getPotentialStat(bs, bonus);
            switch (bs) {
                case str, dex, inte, luk -> amount += equip.getPotentialStat(BaseStat.allStat, bonus);
                case strR, dexR, intR, lukR -> amount += equip.getPotentialStat(BaseStat.allStatR, bonus);
                default -> {
                }
            }
            if (amount < want) {
                return false;
            }
        }
        return true;
    }

    // Cubing ---------------------------------------------------------------------------------------------------------

    /** What a cubing run did. */
    public static class Result {
        public int cubesUsed;
        public int ownedCubesUsed;
        public long nxSpent;
        /** the Enhance window's meso fee paid, and the uses a fee-waiving glass covered */
        public long mesoFee;
        public int feesWaived;
        /** meso resets made after the cubes ran out, and their mesos */
        public int resetsUsed;
        public long resetMesos;
        public boolean goalMet;
        public String stopReason;
    }

    /**
     * Cubes a worn item: until the goal is met (or once, without a goal), using owned cubes first, then meso resets (or NX), at most
     * maxCubes times.
     */
    public static Result cube(Char chr, Equip equip, boolean bonus, Map<String, Integer> goal, int maxCubes) {
        return cube(chr, equip, bonus, goal, maxCubes, Long.MAX_VALUE, Long.MAX_VALUE);
    }

    /** As above, spending at most {@code maxMesos} mesos (fees and resets) and {@code maxNx} NX. */
    public static Result cube(Char chr, Equip equip, boolean bonus, Map<String, Integer> goal, int maxCubes,
                              long maxMesos, long maxNx) {
        Result r = new Result();
        int cubeId = getCubeId(bonus);
        int nx = getCubeNx(bonus);
        int limit = goal == null || goal.isEmpty() ? 1 : Math.max(1, Math.min(MAX_CUBES_PER_RUN, maxCubes));
        if (!getCandidates(chr, bonus).contains(equip)) {
            r.stopReason = "That item isn't worn or has nothing to cube.";
            return r;
        }
        if (goal != null && !goal.isEmpty() && meets(equip, goal, bonus)) {
            r.goalMet = true;
            r.stopReason = "The item already meets that goal.";
            return r;
        }
        boolean decent = !bonus; // decent skills come from the (main) potential of worn items
        if (decent) {
            equip.updateDecentSkills(chr, false);
        }
        long fee = getCubeMesoFee(chr, equip);
        try {
            while (r.cubesUsed < limit) {
                long mesosLeft = maxMesos - r.mesoFee - r.resetMesos;
                if (chr.hasItemCount(cubeId, 1)) {
                    Item glass = fee > 0 ? ItemHandlerModule.feeWaiver(chr, equip) : null;
                    if (glass == null && (chr.getMoney() < fee || fee > mesosLeft)) {
                        r.stopReason = "You ran out of mesos for the cube fee (" + AutoEnhanceModule.format(fee)
                                + " mesos a cube).";
                        break;
                    }
                    chr.consumeItem(cubeId, 1);
                    r.ownedCubesUsed++;
                    if (glass != null) {
                        chr.consumeItem(glass, 1);
                        r.feesWaived++;
                    } else if (fee > 0) {
                        chr.deductMoney(fee, ProgressionLog.Sink.CUBES);
                        r.mesoFee += fee;
                    }
                    ItemHandlerModule.rollPotentialInPlace(equip, cubeId, bonus);
                } else {
                    long reset = getMesoResetCost(chr, equip, bonus);
                    if (reset >= 0) {
                        if (chr.getMoney() < reset || reset > mesosLeft) {
                            r.stopReason = "You ran out of cubes and mesos for a meso reset ("
                                    + AutoEnhanceModule.format(reset) + " mesos).";
                            break;
                        }
                        chr.deductMoney(reset, ProgressionLog.Sink.CUBES);
                        r.resetMesos += reset;
                        r.resetsUsed++;
                        ItemHandlerModule.rollPotentialInPlace(equip, bonus ? -2 : -1, bonus);
                    } else {
                        Item glass = fee > 0 ? ItemHandlerModule.feeWaiver(chr, equip) : null;
                        if (glass == null && (chr.getMoney() < fee || fee > mesosLeft)) {
                            r.stopReason = "You ran out of mesos for the cube fee (" + AutoEnhanceModule.format(fee)
                                    + " mesos a cube).";
                            break;
                        }
                        if (chr.getNX() < nx || r.nxSpent + nx > maxNx) {
                            r.stopReason = "You ran out of cubes and NX.";
                            break;
                        }
                        chr.deductNX(nx);
                        r.nxSpent += nx;
                        if (glass != null) {
                            chr.consumeItem(glass, 1);
                            r.feesWaived++;
                        } else if (fee > 0) {
                            chr.deductMoney(fee, ProgressionLog.Sink.CUBES);
                            r.mesoFee += fee;
                        }
                        ItemHandlerModule.rollPotentialInPlace(equip, cubeId, bonus);
                    }
                }
                r.cubesUsed++;
                if (goal != null && !goal.isEmpty() && meets(equip, goal, bonus)) {
                    r.goalMet = true;
                    break;
                }
            }
        } finally {
            equip.updateToChar(chr);
            Equip other = getZeroOtherWeapon(chr, equip);
            if (other != null) {
                other.copyItemOptionsFrom(equip);
                other.updateToChar(chr);
            }
            if (decent) {
                equip.updateDecentSkills(chr, true);
            }
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
        if (r.stopReason == null && !r.goalMet && goal != null && !goal.isEmpty()) {
            r.stopReason = "Reached the limit of " + limit + " cube" + (limit == 1 ? "" : "s") + ".";
        }
        return r;
    }

    private static boolean isZeroSecondWeapon(Char chr, Equip equip) {
        return JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())
                && equip.getBagIndex() == 10;
    }

    private static Equip getZeroOtherWeapon(Char chr, Equip equip) {
        if (!JobConstants.isZero(chr.getJob()) || !ItemConstants.isLongOrBigSword(equip.getItemId())) {
            return null;
        }
        Item other = chr.getEquippedInventory().getItemBySlot(equip.getBagIndex() == 10 ? 11 : 10);
        return other instanceof Equip ? (Equip) other : null;
    }
}

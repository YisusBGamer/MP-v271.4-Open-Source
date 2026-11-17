package net.swordie.ms.constants;

/**
 * HEXA Matrix (6th job) costs and ids. The cost tables are the live server's (they aren't in the client data); the
 * client shows what HEXA_MATRIX_INFORMATION sends, so both sides use these.
 */
public class HexaConstants {

    public static final int SIXTH_JOB_LEVEL = 260;
    /** A 6th job skill's job root is its class's 4th job id plus this (Hero 112 -> 114) */
    public static final int SIXTH_JOB_ROOT_OFFSET = 2;
    /** Completed = 6th job (the client opens the HEXA Matrix with it) */
    public static final int SIXTH_JOB_QUEST = 1488;
    /** Quest record: 0 = Sol Erda held, 0exp = Sol Erda energy (gauge) */
    public static final int SOL_ERDA_QUEST = 1489;
    public static final int HEXA_STAT_SKILL = 500071000;

    public static final int[] SOL_ERDA_FRAGMENTS = {4009548, 4009547, 4009550}; // untradable one first

    /** Sol Erda Conversion: {item, amount per Sol Erda} */
    public static final int[][] ERDA_CONVERSIONS = {
            {2638879, 30}, // Faint Sol Erda Energy
            {2638603, 1}, // HEXA Booster
    };

    /** Node categories as the client numbers them (HexaCoreData category - 1) */
    public static final int[] NODE_TYPES = {0, 1, 2, 3};

    // activation cost by node type (skill, mastery, boost, common)
    private static final int[] ACTIVATE_SOL_ERDA = {5, 3, 4, 7};
    private static final int[] ACTIVATE_FRAGMENTS = {100, 50, 75, 125};

    // upgrade cost from level i + 1 to i + 2, by node type
    private static final int[][] UPGRADE_SOL_ERDA = {
            {1, 1, 1, 2, 2, 2, 3, 3, 10, 3, 3, 4, 4, 4, 4, 4, 4, 5, 15, 5, 5, 5, 5, 5, 6, 6, 6, 7, 20},
            {1, 1, 1, 1, 1, 1, 2, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 4, 10},
            {1, 1, 1, 2, 2, 2, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 4, 12, 4, 4, 4, 4, 4, 5, 5, 5, 6, 15},
            {2, 2, 2, 3, 3, 3, 5, 5, 14, 5, 5, 6, 6, 6, 6, 6, 6, 7, 17, 7, 7, 7, 7, 7, 9, 9, 9, 10, 20},
    };
    private static final int[][] UPGRADE_FRAGMENTS = {
            {30, 35, 40, 45, 50, 55, 60, 65, 200, 80, 90, 100, 110, 120, 130, 140, 150, 160, 350, 170, 180, 190, 200,
                    210, 220, 230, 240, 250, 500},
            {15, 18, 20, 23, 25, 28, 30, 33, 100, 40, 45, 50, 55, 60, 65, 70, 75, 80, 175, 85, 90, 95, 100, 105, 110,
                    115, 120, 125, 250},
            {23, 27, 30, 34, 38, 42, 45, 49, 150, 60, 68, 75, 83, 90, 98, 105, 113, 120, 263, 128, 135, 143, 150, 158,
                    165, 173, 180, 188, 375},
            {38, 44, 50, 57, 63, 69, 75, 82, 300, 110, 124, 138, 152, 165, 179, 193, 207, 220, 525, 234, 248, 262, 275,
                    289, 303, 317, 330, 344, 750},
    };

    /**
     * Node subtypes (HexaCore coreData subType) with their own cost rows; the client hides the node's Enhance panel
     * when its subType has no row. 1 = 5th job common skill boost (Blitz Shield and the like).
     */
    public static final int[] NODE_SUB_TYPES = {1};
    private static final int[] SUB_ACTIVATE_SOL_ERDA = {0, 4};
    private static final int[] SUB_ACTIVATE_FRAGMENTS = {0, 90};
    private static final int[][] SUB_UPGRADE_SOL_ERDA = {
            {},
            {1, 1, 1, 2, 2, 2, 3, 3, 9, 3, 3, 3, 3, 4, 4, 4, 4, 4, 14, 4, 5, 5, 5, 5, 5, 5, 5, 6, 18},
    };
    private static final int[][] SUB_UPGRADE_FRAGMENTS = {
            {},
            {25, 30, 35, 40, 45, 50, 55, 60, 180, 73, 81, 90, 98, 107, 115, 124, 132, 141, 315, 151, 160, 170, 179,
                    189, 198, 208, 217, 227, 450},
    };

    // HEXA Stat: unlock, then one enhancement per try (by the main stat's level)
    public static final int[][] STAT_CORE_UNLOCK = { // {core, Sol Erda, fragments, meso (millions) per change}
            {50000000, 5, 10, 10},
            {50000001, 10, 200, 20},
            {50000002, 15, 350, 35},
    };
    public static final int[] STAT_UPGRADE_SOL_ERDA = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final int[] STAT_UPGRADE_FRAGMENTS = {10, 10, 10, 20, 20, 20, 20, 30, 40, 50, 50};
    /** Chance that an enhancement goes to the main stat, by its level */
    public static final double[] STAT_MAIN_WEIGHT = {0.35, 0.35, 0.35, 0.20, 0.20, 0.20, 0.20, 0.15, 0.10, 0.05};
    public static final int STAT_MAX_LEVEL = 10;
    public static final long STAT_RESET_MESO = 10_000_000L;
    public static final int STAT_TYPES = 7; // crit damage, boss damage, ignore defense, damage, att, m.att, main stat
    // meso the client shows for a HEXA Stat change at levels 0-30 (the 10-30 table is per node, above)
    public static final long STAT_CHANGE_MESO_0_TO_30 = 100_000_000L;

    // Sol Erda energy items: energy per item
    private static final int[] FAINT_ENERGY = {2636420, 2637468, 2638208, 2636953, 2636954, 2638879};
    private static final int[] ENERGY = {2636421, 2637469, 2638391, 2636883, 2636955, 2638456};
    private static final int[] DENSE_ENERGY = {2636422, 2638231, 2638392, 2636884, 2636956};

    public static int getActivateSolErda(int nodeType) {
        return valid(nodeType) ? ACTIVATE_SOL_ERDA[nodeType] : Integer.MAX_VALUE;
    }

    public static int getActivateFragments(int nodeType) {
        return valid(nodeType) ? ACTIVATE_FRAGMENTS[nodeType] : Integer.MAX_VALUE;
    }

    /** @return the Sol Erda to go from level to level + 1 (level 1-29) */
    public static int getUpgradeSolErda(int nodeType, int level) {
        return valid(nodeType) && level >= 1 && level <= UPGRADE_SOL_ERDA[nodeType].length
                ? UPGRADE_SOL_ERDA[nodeType][level - 1] : Integer.MAX_VALUE;
    }

    public static int getUpgradeFragments(int nodeType, int level) {
        return valid(nodeType) && level >= 1 && level <= UPGRADE_FRAGMENTS[nodeType].length
                ? UPGRADE_FRAGMENTS[nodeType][level - 1] : Integer.MAX_VALUE;
    }

    public static int getMaxNodeLevel(int nodeType) {
        return valid(nodeType) ? UPGRADE_FRAGMENTS[nodeType].length + 1 : 0;
    }

    public static int getActivateSolErda(int nodeType, int subType) {
        return subType == 0 ? getActivateSolErda(nodeType)
                : validSub(subType) ? SUB_ACTIVATE_SOL_ERDA[subType] : Integer.MAX_VALUE;
    }

    public static int getActivateFragments(int nodeType, int subType) {
        return subType == 0 ? getActivateFragments(nodeType)
                : validSub(subType) ? SUB_ACTIVATE_FRAGMENTS[subType] : Integer.MAX_VALUE;
    }

    public static int getUpgradeSolErda(int nodeType, int subType, int level) {
        if (subType == 0) {
            return getUpgradeSolErda(nodeType, level);
        }
        return validSub(subType) && level >= 1 && level <= SUB_UPGRADE_SOL_ERDA[subType].length
                ? SUB_UPGRADE_SOL_ERDA[subType][level - 1] : Integer.MAX_VALUE;
    }

    public static int getUpgradeFragments(int nodeType, int subType, int level) {
        if (subType == 0) {
            return getUpgradeFragments(nodeType, level);
        }
        return validSub(subType) && level >= 1 && level <= SUB_UPGRADE_FRAGMENTS[subType].length
                ? SUB_UPGRADE_FRAGMENTS[subType][level - 1] : Integer.MAX_VALUE;
    }

    public static int getSubTypeMaxLevel(int subType) {
        return validSub(subType) ? SUB_UPGRADE_FRAGMENTS[subType].length + 1 : 0;
    }

    private static boolean validSub(int subType) {
        return subType > 0 && subType < SUB_ACTIVATE_SOL_ERDA.length;
    }

    private static boolean valid(int nodeType) {
        return nodeType >= 0 && nodeType < ACTIVATE_SOL_ERDA.length;
    }

    public static int[] getStatCoreUnlock(int coreId) {
        for (int[] unlock : STAT_CORE_UNLOCK) {
            if (unlock[0] == coreId) {
                return unlock;
            }
        }
        return null;
    }

    /** @return the Sol Erda energy one of these items gives, 0 if it isn't one */
    public static int getSolErdaEnergy(int itemId) {
        int fromData = net.swordie.ms.loaders.HexaCoreData.getEnergyItemValue(itemId);
        if (fromData > 0) {
            return fromData;
        }
        if (contains(FAINT_ENERGY, itemId)) {
            return 10;
        } else if (contains(ENERGY, itemId)) {
            return 200;
        } else if (contains(DENSE_ENERGY, itemId)) {
            return 500;
        }
        return 0;
    }

    private static boolean contains(int[] ids, int id) {
        for (int i : ids) {
            if (i == id) {
                return true;
            }
        }
        return false;
    }
}

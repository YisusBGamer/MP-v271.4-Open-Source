package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * Created on 2/15/2020.
 *
 * @author Asura
 */
public enum EgoEquipItemUpgradeType { // Zero
    AddBonusStat(0, 50_000, 500),
    ChangePotential(1, 100_000, 600),
    ;

    private final int val;
    private final int mesoCost;
    private final int wpCost;

    EgoEquipItemUpgradeType(int val, int mesoCost, int wpCost) {
        this.val = val;
        this.mesoCost = mesoCost;
        this.wpCost = wpCost;
    }

    public int getMesoCost() {
        return mesoCost;
    }

    public int getWpCost() {
        return wpCost;
    }

    public int getVal() {
        return val;
    }

    public static EgoEquipItemUpgradeType getByVal(int val) {
        return Arrays.stream(values()).filter(v -> v.getVal() == val).findFirst().orElse(null);
    }
}

package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * In MAPLE_PLANNER_MODIFIED request types.
 */
public enum MaplePlannerRequest {
    /** A row's Start (it completes the mission instead once it's done). */
    Start(0),
    /** A row's Complete. */
    Complete(1),
    /** Start All / Complete All over the scheduled rows. */
    All(3),
    /** Save: the rows the player checked. */
    Save(5);

    private final int val;

    MaplePlannerRequest(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static MaplePlannerRequest getByVal(int val) {
        return Arrays.stream(values()).filter(r -> r.getVal() == val).findAny().orElse(null);
    }
}

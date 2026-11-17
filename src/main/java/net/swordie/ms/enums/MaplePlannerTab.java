package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * The Maple Planner's tabs: the quest-ex record GMS keeps each tab's checked rows in ("scheduled=&lt;flags&gt;;
 * typeOrder=&lt;order&gt;"), the number of flags GMS stores, and the Etc.wz MapleScheduler.img/contents tab with the
 * rows' quests (none for bosses).
 */
public enum MaplePlannerTab {
    Daily(1, 39395, 21, "contents,quest", "daily"),
    Weekly(2, 39396, 22, "contents,quest", "weekly"),
    Boss(3, 39397, 105, "bossWeekly,bossDaily", null);

    private final int val;
    private final int recordId;
    private final int flags;
    private final String typeOrder;
    private final String schedulerTab;

    MaplePlannerTab(int val, int recordId, int flags, String typeOrder, String schedulerTab) {
        this.val = val;
        this.recordId = recordId;
        this.flags = flags;
        this.typeOrder = typeOrder;
        this.schedulerTab = schedulerTab;
    }

    public int getVal() {
        return val;
    }

    public int getRecordId() {
        return recordId;
    }

    public int getFlags() {
        return flags;
    }

    public String getTypeOrder() {
        return typeOrder;
    }

    public String getSchedulerTab() {
        return schedulerTab;
    }

    public static MaplePlannerTab getByVal(int val) {
        return Arrays.stream(values()).filter(t -> t.getVal() == val).findAny().orElse(null);
    }
}

package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * Created on 1/25/2018.
 */
public enum EquipmentEnchantType {
    ScrollUpgradeRequest(0),
    HyperUpgradeResult(1),
    TransmissionResult(2), // Trace Restoration with an identical item: int tick, short material, short trace
    // v271: the Star Force request with 2 more ints before the mode; only a client test command sets them (no Star
    // Catching in v271)
    HyperUpgradeExtra(7),
    StarCoreTraceRestoration(9), // Trace Restoration with a Star Core (tab 10): int tick, short Etc slot, short trace
    ScrollUpgradeDisplay(50),
    ScrollTimerEffective(51),
    HyperUpgradeDisplay(52),
    MiniGameDisplay(53), // v265 only: the v271 client has no 53 and no Star Catching
    HyperUpgradeDisplayRefresh(54), // v271: the display without the trailing int
    // v271 client (0x1459ead40): 50/51/100-102 go to the Scroll tab, 52/54/104/105 to Star Force, 103/108 to either,
    // 107 to tab 5, 110 to tab 9, 111/112 to Trace Restoration (tabs 7/10), 109 is the "You can't do that right now."
    // popup (no payload)
    ShowScrollUpgradeResult(100),
    ShowScrollUseResult(101), // an inventory scroll used from the Scroll tab: plays the result, unlocks the window
    ShowScrollVestigeCompensationResult(102),
    ShowHyperUpgradeResult(104),
    Unk(105),
    ShowUnknownFailResult(109),
    ShowTraceRestorationResult(111); // int position of the restored item; 112 also goes to the trace tabs, which ignore it

    private byte val;

    EquipmentEnchantType(int val) {
        this.val = (byte) val;
    }

    public static EquipmentEnchantType getByVal(byte val) {
        return Arrays.stream(values()).filter(tt -> tt.getVal() == val).findAny().orElse(null);
    }

    public byte getVal() {
        return val;
    }
}

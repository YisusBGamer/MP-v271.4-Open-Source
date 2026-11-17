package net.swordie.ms.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rune types as the v265 client numbers them (Etc/RuneStone.img/runeStone/0). The client only has slots 0-8;
 * any other number reaches a null rune template and crashes it. Swiftness, Recovery and Riches are gone in v265.
 */
public enum RuneType {
    Destruction(0),
    Thunder(1),
    Might(2), // Rune of Giants
    Darkness(3),
    Skill(4),
    Purification(5),
    Contact(6),
    Ignition(7),
    Blessing(8),
    Swiftness(-1),
    Recovery(-1),
    Riches(-1),
    ;

    private byte val;

    RuneType(int val) {this.val = (byte) val;}

    public byte getVal() {return val;}

    public boolean isInClient() {
        return val >= 0;
    }

    /** Types a random rune can be: the ones in the client. */
    public static List<RuneType> spawnable() {
        // Contact is a buff now (ShineRuneAttack); left out until a !testrunestone 6 check in game
        return Arrays.stream(values()).filter(RuneType::isInClient).filter(rt -> rt != Contact)
                .collect(Collectors.toList());
    }

    public static RuneType getByVal(byte val) {
        if (val < 0) {
            return null;
        }
        return Arrays.stream(values()).filter(rt -> rt.getVal() == val).findAny().orElse(null);
    }
}

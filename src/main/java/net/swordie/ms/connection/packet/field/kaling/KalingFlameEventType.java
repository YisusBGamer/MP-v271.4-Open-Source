package net.swordie.ms.connection.packet.field.kaling;

/** KALING_FIELD_EVENT sub-types (CField_Kaling, client 0x14245ed90). */
public enum KalingFlameEventType {
    Flames(0x3e9),
    FlameStates(0x3ea),
    FlameAttack(0x3eb),
    ZoneRules(0x3ec),
    ;

    private final int val;

    KalingFlameEventType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}

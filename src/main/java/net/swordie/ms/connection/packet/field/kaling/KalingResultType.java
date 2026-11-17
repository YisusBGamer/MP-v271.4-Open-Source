package net.swordie.ms.connection.packet.field.kaling;

/** KALING_FIELD_RESULT types (CField_Kaling, client 0x14245ed00). */
public enum KalingResultType {
    Willpower(0),
    Seasons(1),
    ;

    private final int val;

    KalingResultType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}

package net.swordie.ms.connection.packet.field.kaling;

/** KALING_HUNDUN_FIELD types (CField_KalingHundun, client 0x142a08ff0). */
public enum HundunResultType {
    Bar(2),
    Animation(4),
    AmplifyTimer(5),
    BarValue(6),
    Init(7),
    ;

    private final int val;

    HundunResultType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}

package net.swordie.ms.connection.packet.field.kaling;

/** KALING_SPRING_RESULT types (CField_KalingQiongqi, client 0x142a08a20). */
public enum QiongqiResultType {
    DisturbanceTimer(0),
    ;

    private final int val;

    QiongqiResultType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}

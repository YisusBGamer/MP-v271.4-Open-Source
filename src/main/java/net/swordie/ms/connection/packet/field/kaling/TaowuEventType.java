package net.swordie.ms.connection.packet.field.kaling;

/** KALING_ELECTRIC_EVENT types (CField_KalingTaowu, client 0x1424622b0). */
public enum TaowuEventType {
    TeslaCoils(0),
    CoilStates(1),
    Attack(2),
    Current(3),
    InCurrent(4),
    MiniCoilUsed(5),
    MiniCoilReady(6),
    ZoneAck(7),
    ZonesAdd(9),
    ZonesEnd(10),
    Timer(11),
    TimerResult(13),
    ;

    private final int val;

    TaowuEventType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}

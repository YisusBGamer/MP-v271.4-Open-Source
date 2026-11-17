package net.swordie.ms.connection.packet.field.kaling;

import net.swordie.ms.connection.OutPacket;

/** Qiongqi's map. */
public class QiongqiResult {

    private final QiongqiResultType type;
    private int ms;

    private QiongqiResult(QiongqiResultType type) {
        this.type = type;
    }

    /** Ms until the next disturbance (Torn Spring); the tooltip says "under way" at 0. */
    public static QiongqiResult disturbanceTimer(int ms) {
        QiongqiResult r = new QiongqiResult(QiongqiResultType.DisturbanceTimer);
        r.ms = ms;
        return r;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        switch (type) {
            case DisturbanceTimer:
                outPacket.encodeInt(ms);
                break;
        }
    }
}

package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Lotus fields (client 0x142808e30): LOTUS_ALERT_GAUGE, int type then its body. */
public class LotusFieldPacket {

    public enum LotusGaugeType {
        /** makes the window; difficulty 3: no UI */
        Create(0),
        SetGauge(1),
        /** phase 3's gauge, drawn as max - value */
        SetGauge2(2),
        SetMode(3),
        ;

        private final int val;

        LotusGaugeType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    private static OutPacket gauge(LotusGaugeType type) {
        OutPacket out = new OutPacket(OutHeader.LOTUS_ALERT_GAUGE);
        out.encodeInt(type.getVal());
        return out;
    }

    public static OutPacket create(int difficulty, int phase, int alertMode, int gauge, int gauge2) {
        OutPacket out = gauge(LotusGaugeType.Create);
        out.encodeInt(difficulty);
        out.encodeInt(phase);
        out.encodeInt(alertMode);
        out.encodeInt(gauge);
        out.encodeInt(gauge2);
        return out;
    }

    /** reason: the pattern key that changed it, or 0 (the client's "Update" label) */
    public static OutPacket setGauge(boolean second, int value, int reason) {
        OutPacket out = gauge(second ? LotusGaugeType.SetGauge2 : LotusGaugeType.SetGauge);
        out.encodeInt(value);
        out.encodeInt(reason);
        out.encodeByte(0);
        return out;
    }

    public static OutPacket setMode(int mode) {
        OutPacket out = gauge(LotusGaugeType.SetMode);
        out.encodeInt(mode);
        return out;
    }
}

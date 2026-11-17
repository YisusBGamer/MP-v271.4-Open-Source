package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Created on 01/08/2021.
 *
 * @author Asura
 */
public class GloomFieldPacket {
    public static OutPacket gloomGaugeUpdate(boolean terrorisedState, int curGauge, int maxGauge) {
        OutPacket outPacket = new OutPacket(OutHeader.GLOOM_GAUGE_UPDATE);

        outPacket.encodeByte(terrorisedState);
        outPacket.encodeInt(curGauge);
        outPacket.encodeInt(maxGauge);

        return outPacket;
    }

    public static OutPacket gloomUnk(int value) {
        // v265 client reads one int
        OutPacket outPacket = new OutPacket(OutHeader.GLOOM_UNK214_1613);
        outPacket.encodeInt(value);

        return outPacket;
    }

    public static OutPacket gloomUnk2(int value) {
        // v265 client reads one int
        OutPacket outPacket = new OutPacket(OutHeader.GLOOM_UNK214_1614);
        outPacket.encodeInt(value);

        return outPacket;
    }
}

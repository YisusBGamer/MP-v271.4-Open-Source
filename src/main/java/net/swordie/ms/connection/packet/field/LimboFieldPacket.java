package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * CField_Limbo (client 0x14279dc70): LIMBO_FIELD_RESULT. Erosion gauge: 1000 = none taken, 0 = out. Phase: 0 for
 * field type 380, 1 for 381/382/384, 2 for 383; the client ignores a packet for another phase.
 */
public class LimboFieldPacket {

    public enum LimboResultType {
        /** Also makes the gauge window. */
        Erosion(0),
        ErosionChange(1),
        ;

        private final int val;

        LimboResultType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static OutPacket erosion(int phase, int left, int hpPermille) {
        OutPacket outPacket = result(LimboResultType.Erosion, phase, left);
        outPacket.encodeInt(hpPermille);
        return outPacket;
    }

    /** Needs the window from {@link #erosion} first. */
    public static OutPacket erosionChange(int phase, int left, boolean effect) {
        OutPacket outPacket = result(LimboResultType.ErosionChange, phase, left);
        outPacket.encodeByte(effect);
        return outPacket;
    }

    private static OutPacket result(LimboResultType type, int phase, int left) {
        OutPacket outPacket = new OutPacket(OutHeader.LIMBO_FIELD_RESULT);
        outPacket.encodeInt(type.getVal());
        outPacket.encodeInt(phase);
        outPacket.encodeInt(left);
        return outPacket;
    }
}

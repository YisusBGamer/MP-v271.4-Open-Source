package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Black Mage fields: BLACK_MAGE_FIELD_TIMER, int type then its body. */
public class BlackMageFieldPacket {

    public enum BlackMageTimerType {
        PhaseOver(1),
        /** int ms to the next big pattern */
        NextPattern(2),
        /** the big pattern goes off (GMS phase 2, right after the clock restarts) */
        Pattern(3),
        ;

        private final int val;

        BlackMageTimerType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static OutPacket timer(BlackMageTimerType type) {
        OutPacket out = new OutPacket(OutHeader.BLACK_MAGE_FIELD_TIMER);
        out.encodeInt(type.getVal());
        return out;
    }

    public static OutPacket nextPattern(int ms) {
        OutPacket out = timer(BlackMageTimerType.NextPattern);
        out.encodeInt(ms);
        return out;
    }
}

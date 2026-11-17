package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Erda Spectrum fields: ERDA_SPECTRUM_STATE and ERDA_SPECTRUM_VALUES. */
public class ErdaSpectrumFieldPacket {

    public enum ErdaSpectrumState {
        Running(2),
        /** the collector is on, or the run is cleared */
        Cleared(3),
        PhaseEnd(4),
        ;

        private final int val;

        ErdaSpectrumState(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static OutPacket state(ErdaSpectrumState state) {
        OutPacket out = new OutPacket(OutHeader.ERDA_SPECTRUM_STATE);
        out.encodeInt(state.getVal());
        return out;
    }

    /** goal < 0: the phase without a goal (time only) */
    public static OutPacket values(int timeMs, int goal) {
        OutPacket out = new OutPacket(OutHeader.ERDA_SPECTRUM_VALUES);
        out.encodeInt(timeMs);
        if (goal >= 0) {
            out.encodeInt(goal);
        }
        return out;
    }
}

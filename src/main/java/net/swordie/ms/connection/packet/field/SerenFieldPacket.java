package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Chosen Seren's field packets (fieldType 270/271).
 */
public class SerenFieldPacket {

    /** The sun gauge window: current and max. */
    public static OutPacket sunGauge(int cur, int max) {
        OutPacket outPacket = new OutPacket(OutHeader.SEREN_SUN_GAUGE);

        outPacket.encodeInt(cur);
        outPacket.encodeInt(max);

        return outPacket;
    }

    /** Phase 2: the sun's segment (1 noon, 2 dusk, 3 midnight, 4 dawn) for its music and effect. */
    public static OutPacket sunPhase(int phase) {
        OutPacket outPacket = new OutPacket(OutHeader.SEREN_SUN_PHASE);

        outPacket.encodeInt(phase);

        return outPacket;
    }

    /** Ray of Light under way for ms; the client answers SEREN_RAY_OF_LIGHT_END with the key when it is over. */
    public static OutPacket rayOfLight(int key, int ms) {
        OutPacket outPacket = new OutPacket(OutHeader.SEREN_RAY_OF_LIGHT);

        outPacket.encodeInt(key);
        outPacket.encodeInt(ms);

        return outPacket;
    }
}

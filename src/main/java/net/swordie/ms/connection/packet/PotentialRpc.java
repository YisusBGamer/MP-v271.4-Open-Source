package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

import java.util.List;

/**
 * Replies to the Enhance window's cube calls (In POTENTIAL_RPC_REQUEST) on POTENTIAL_RPC_RESULT: 4 call id,
 * 4 status (0 = ok), then the method's reply, a field mask and the fields it names.
 */
public final class PotentialRpc {

    private PotentialRpc() {
    }

    /** A little-endian int of a call's bytes. */
    public static int readIntLE(byte[] b, int o) {
        return (b[o] & 0xFF) | (b[o + 1] & 0xFF) << 8 | (b[o + 2] & 0xFF) << 16 | (b[o + 3] & 0xFF) << 24;
    }

    /** The client's zigzag varint (a vector's length). */
    public static int readVarint(byte[] b, int[] off) {
        int v = 0;
        for (int shift = 0; off[0] < b.length && shift < 35; shift += 7) {
            int x = b[off[0]++] & 0xFF;
            v |= (x & 0x7F) << shift;
            if ((x & 0x80) == 0) {
                break;
            }
        }
        return (v >>> 1) ^ -(v & 1);
    }

    private static OutPacket header(int callId) {
        OutPacket out = new OutPacket(OutHeader.POTENTIAL_RPC_RESULT);
        out.encodeInt(callId);
        out.encodeInt(0);
        return out;
    }

    /** Unicube / Bonus Unicube use (client reader 0x141a58f30): B mask, [1: 4 result (0 = ok)] [2: 4]. */
    public static OutPacket uniCubeResult(int callId, int result) {
        OutPacket out = header(callId);
        out.encodeByte(1);
        out.encodeInt(result);
        return out;
    }

    /**
     * Violet Cube use (client reader 0x142c89650): B mask, [1: 4 result (0 = ok)] [2: 4] [4: varint n, n x 4 option]
     * [8: 4]. The options are the lines to pick from.
     */
    public static OutPacket violetCubeResult(int callId, int result, List<Integer> options) {
        OutPacket out = header(callId);
        boolean withLines = result == 0 && options != null && !options.isEmpty();
        out.encodeByte(withLines ? 1 | 4 : 1);
        out.encodeInt(result);
        if (withLines) {
            out.encodeByte(options.size() * 2); // zigzag varint, one byte up to 63
            for (int option : options) {
                out.encodeInt(option);
            }
        }
        return out;
    }
}

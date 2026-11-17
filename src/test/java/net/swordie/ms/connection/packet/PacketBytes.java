package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import org.junit.Assert;

/** Golden-byte checks: a packet's bytes (opcode first, little endian) against hex text written in the test. */
public final class PacketBytes {

    private PacketBytes() {
    }

    public static String hex(OutPacket packet) {
        StringBuilder sb = new StringBuilder();
        for (byte b : packet.getData()) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(String.format("%02X", b & 0xff));
        }
        return sb.toString();
    }

    /** Spaces and case in the expected text don't matter. */
    public static void assertBytes(String expectedHex, OutPacket packet) {
        String expected = expectedHex.replaceAll("\s+", "").toUpperCase();
        Assert.assertEquals(expected, hex(packet).replace(" ", ""));
    }
}

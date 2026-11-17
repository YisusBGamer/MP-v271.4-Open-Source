package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * SERVER_VALUE and SERVER_ENVIRONMENT: key/value switches the client keeps in two maps and looks up with a default
 * (client 0x1404ded00 for ints, 0x1404dee30 for strings). An entry's condition decides whether the client stores it:
 * "All" is stored, "AllServer" and "Project|X|..." with X other than "Client" are not. Only keys the client reads,
 * with a value other than its own default, are sent; everything else reads the same with or without an entry.
 */
public class ServerValues {

    private static final String ALL = "All";

    /** {key, value} */
    private static final int[][] VALUES = {
            // content switches 1000 + n (n = 0..318), default on; 0 turns that content off
            {1000, 0}, {1001, 0}, {1002, 0}, {1010, 0}, {1022, 0}, {1063, 0}, {1064, 0}, {1181, 0}, {1191, 0},
            {10016, 0},  // familiar block, default 1 (on shows the "can't use" notice instead of the familiar UI)
            {278, 0},    // default 1
            {344, 0},    // default 1
            {438, 1},    // on/off, default off
            {446, 1},    // on/off read by the damage calculation, default off
            {784, 0},    // on/off, default on
            {850, 0},    // on/off, default on
            {10076, 15}, // default 0
    };

    /**
     * Steps by Union level, "level:step|...": the first step whose level is above the Union level applies, the last
     * one is the top step (client 0x144917b00, next to the Union Artifact code). Without it the client skips this.
     */
    private static final int ENV_UNION_STEPS = 1020;
    private static final String UNION_STEPS = "2000:1|3500:2|5000:3|6500:4|8000:5|99999:6";

    public static OutPacket serverValues() {
        OutPacket outPacket = new OutPacket(OutHeader.SERVER_VALUE);
        outPacket.encodeInt(VALUES.length);
        int index = 1;
        for (int[] kv : VALUES) {
            outPacket.encodeInt(index++);
            outPacket.encodeInt(kv[0]);
            outPacket.encodeInt(kv[1]);
            outPacket.encodeString(ALL);
        }
        return outPacket;
    }

    public static OutPacket serverEnvironment() {
        OutPacket outPacket = new OutPacket(OutHeader.SERVER_ENVIRONMENT);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(ENV_UNION_STEPS);
        outPacket.encodeString(UNION_STEPS);
        outPacket.encodeString(ALL);
        return outPacket;
    }
}

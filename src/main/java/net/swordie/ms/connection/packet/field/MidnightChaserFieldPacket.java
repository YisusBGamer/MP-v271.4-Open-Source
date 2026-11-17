package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

import java.util.List;

/** Midnight Chaser fields (client field class 0x142972030): rooms, objects, state, step results. */
public class MidnightChaserFieldPacket {

    public enum MidnightChaserState {
        Start(1),
        Clear(2),
        Fail(3),
        ;

        private final int val;

        MidnightChaserState(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    /** byte n, n x (byte room, byte object of that room) */
    public static OutPacket objects(List<Integer> rooms, int[] objectOfRoom) {
        OutPacket out = new OutPacket(OutHeader.MIDNIGHT_CHASER_OBJECTS);
        out.encodeByte(rooms.size());
        for (int room : rooms) {
            out.encodeByte(room);
            out.encodeByte(objectOfRoom[room]);
        }
        return out;
    }

    public static OutPacket state(MidnightChaserState state) {
        OutPacket out = new OutPacket(OutHeader.MIDNIGHT_CHASER_STATE);
        out.encodeInt(state.getVal());
        return out;
    }

    public static OutPacket stepResult(int step, boolean success) {
        OutPacket out = new OutPacket(OutHeader.MIDNIGHT_CHASER_STEP_RESULT);
        out.encodeByte(step);
        out.encodeByte(success ? 1 : 0);
        return out;
    }

    /** the room (1-based) lights up */
    public static OutPacket room(int room) {
        OutPacket out = new OutPacket(OutHeader.MIDNIGHT_CHASER_ROOM);
        out.encodeByte(room);
        out.encodeByte(1);
        return out;
    }
}

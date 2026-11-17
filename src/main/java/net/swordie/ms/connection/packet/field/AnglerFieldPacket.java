package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Angler Company fields (base 0x14234a950): EPIC_DUNGEON2_OBJECTS, int type then its body. */
public class AnglerFieldPacket {

    public enum AnglerObjectsType {
        Objects(4),
        Reset(6),
        ;

        private final int val;

        AnglerObjectsType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    /**
     * int n, n x (int type, int id, int value, byte on). Type 6 is the Proto-Suit terminal (value = charges needed;
     * the client sends In 1761 with its id).
     */
    public static OutPacket objects(int[][] objects) {
        OutPacket out = new OutPacket(OutHeader.EPIC_DUNGEON2_OBJECTS);
        out.encodeInt(AnglerObjectsType.Objects.getVal());
        out.encodeInt(objects.length);
        for (int[] o : objects) {
            out.encodeInt(o[0]);
            out.encodeInt(o[1]);
            out.encodeInt(o[2]);
            out.encodeByte(o[3]);
        }
        return out;
    }

    /** every object back to its first state */
    public static OutPacket objectsReset() {
        OutPacket out = new OutPacket(OutHeader.EPIC_DUNGEON2_OBJECTS);
        out.encodeInt(AnglerObjectsType.Reset.getVal());
        return out;
    }
}

package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** High Mountain fields (client 0x142349ab0): EPIC_DUNGEON_FIELD, int type then its body. */
public class HighMountainFieldPacket {

    public enum HighMountainType {
        /** the heal altar appears; standing on it fills max steps (In 1488) */
        AltarShow(0),
        /** also lets the client ask again */
        AltarProgress(1),
        /** success or fail animation */
        AltarEnd(2),
        /** a client timer; In 1532 with the id when it runs out */
        FieldTimer(3),
        ;

        private final int val;

        HighMountainType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    private static OutPacket field(HighMountainType type) {
        OutPacket out = new OutPacket(OutHeader.EPIC_DUNGEON_FIELD);
        out.encodeInt(type.getVal());
        return out;
    }

    public static OutPacket altarShow(int x, int y, int max) {
        OutPacket out = field(HighMountainType.AltarShow);
        out.encodeInt(x);
        out.encodeInt(y);
        out.encodeInt(max);
        return out;
    }

    public static OutPacket altarProgress(int value) {
        OutPacket out = field(HighMountainType.AltarProgress);
        out.encodeInt(value);
        return out;
    }

    public static OutPacket altarEnd(boolean success) {
        OutPacket out = field(HighMountainType.AltarEnd);
        out.encodeByte(success ? 1 : 0);
        return out;
    }

    public static OutPacket fieldTimer(int id, int delayMs) {
        OutPacket out = field(HighMountainType.FieldTimer);
        out.encodeInt(id);
        out.encodeInt(delayMs);
        return out;
    }
}

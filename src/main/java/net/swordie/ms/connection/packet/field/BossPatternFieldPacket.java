package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** The pattern-system boss field (Baldrix; client 0x141b6d940): BOSS_PATTERN_FIELD_RESULT. */
public class BossPatternFieldPacket {

    public enum BossPatternResultType {
        DeathCount(7),
        ;

        private final int val;

        BossPatternResultType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    /** The 4-digit death count number; effect plays the change animation. */
    public static OutPacket deathCount(int value, boolean effect) {
        OutPacket outPacket = new OutPacket(OutHeader.BOSS_PATTERN_FIELD_RESULT);
        outPacket.encodeInt(BossPatternResultType.DeathCount.getVal());
        outPacket.encodeByte(effect);
        outPacket.encodeInt(value);
        return outPacket;
    }
}

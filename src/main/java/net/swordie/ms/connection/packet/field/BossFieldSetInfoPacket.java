package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** FIELD_SET_EVENT, read by the client's BossFieldSetInfo (First Adversary). */
public class BossFieldSetInfoPacket {

    public enum FieldSetEventType {
        End(3),
        FieldEffect(4),
        TimerTick(25),
        ;

        private final int val;

        FieldSetEventType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    private static final int PATTERN_CATEGORY = 1069;
    private static final int PATH_TYPE = 2;
    private static final int Z_TYPE = 11;
    private static final int Z = 999;

    private static OutPacket of(FieldSetEventType type) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SET_EVENT);
        outPacket.encodeInt(type.getVal());
        return outPacket;
    }

    /** As GMS sends it at a later phase's start and when a player's Adversarial Will reaches 0 (not decoded). */
    public static OutPacket end() {
        OutPacket outPacket = of(FieldSetEventType.End);
        outPacket.encodeByte(0);
        outPacket.encodeInt(3);
        outPacket.encodeInt(0);
        outPacket.encodeInt(1);
        outPacket.encodeInt(6);
        return outPacket;
    }

    /** A named field-set timer tick ("weather" about every 1.08 s in First Adversary). */
    public static OutPacket timerTick(int ms, String name) {
        OutPacket outPacket = of(FieldSetEventType.TimerTick);
        outPacket.encodeInt(ms);
        outPacket.encodeString(name);
        return outPacket;
    }

    /**
     * A field effect from BossFirstAdversary.img/common/GaugeEffect/&lt;index&gt; (Power of Order up "Levelnc",
     * down "LeveDec").
     */
    public static OutPacket levelEffect(String key, int effectIndex, int charId) {
        OutPacket outPacket = of(FieldSetEventType.FieldEffect);
        outPacket.encodeString(key);
        outPacket.encodeInt(0);
        outPacket.encodeInt(1);
        outPacket.encodeInt(0);
        outPacket.encodeInt(1);
        outPacket.encodeInt(PATTERN_CATEGORY);
        outPacket.encodeInt(0);
        outPacket.encodeInt(charId);
        outPacket.encodeInt(PATH_TYPE);
        outPacket.encodeInt(0);
        outPacket.encodeString("BossFirstAdversary.img/common/GaugeEffect/" + effectIndex);
        outPacket.encodeInt(Z_TYPE);
        outPacket.encodeInt(Z);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(4);
        return outPacket;
    }
}

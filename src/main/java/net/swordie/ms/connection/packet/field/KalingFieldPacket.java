package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.kaling.*;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Kaling's fields. CField_Kaling (client 0x1429ffbc0) handles KALING_FIELD_RESULT and KALING_FIELD_EVENT; its
 * sub-fields Taowu (0x142a03010), Qiongqi (0x142a088a0), Hundun (0x142a08d00) and the Four Seasons hub (0x142a115a0)
 * each handle their own opcode.
 */
public class KalingFieldPacket {

    public static OutPacket fieldResult(KalingResult result) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_RESULT);
        result.encode(outPacket);
        return outPacket;
    }

    public static OutPacket flameEvent(KalingFlameEvent event) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_EVENT);
        event.encode(outPacket);
        return outPacket;
    }

    public static OutPacket taowuEvent(TaowuEvent event) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        event.encode(outPacket);
        return outPacket;
    }

    public static OutPacket qiongqiResult(QiongqiResult result) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_SPRING_RESULT);
        result.encode(outPacket);
        return outPacket;
    }

    public static OutPacket hundunResult(HundunResult result) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_HUNDUN_FIELD);
        result.encode(outPacket);
        return outPacket;
    }

    public static OutPacket seasonPick(SeasonPickResult result) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_SEASON_PICK);
        result.encode(outPacket);
        return outPacket;
    }
}

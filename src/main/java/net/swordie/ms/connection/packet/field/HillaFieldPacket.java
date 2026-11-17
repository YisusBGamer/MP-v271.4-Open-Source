package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

import java.util.List;

/** Hard Hilla's vampire phase (opcodes not mapped for v271 yet). */
public class HillaFieldPacket {

    public static OutPacket vampStart(int hillaObjectId) {
        OutPacket out = new OutPacket(OutHeader.HILLA_VAMP_EFFECT_START);
        out.encodeInt(hillaObjectId);
        return out;
    }

    public static OutPacket vampMobAbsorb(int hillaObjectId, List<Integer> mobObjectIds) {
        OutPacket out = new OutPacket(OutHeader.HILLA_VAMP_EFFECT_MOB_ABSORB);
        out.encodeInt(hillaObjectId);
        out.encodeInt(mobObjectIds.size());
        for (int id : mobObjectIds) {
            out.encodeInt(id);
        }
        return out;
    }

    public static OutPacket vampHillaAbsorb(int hillaObjectId) {
        OutPacket out = new OutPacket(OutHeader.HILLA_VAMP_EFFECT_HILLA_ABSORB);
        out.encodeInt(hillaObjectId);
        return out;
    }
}

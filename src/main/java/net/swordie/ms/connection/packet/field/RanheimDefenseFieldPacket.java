package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Ranheim Defense fields: state, launcher fired, shot hit. */
public class RanheimDefenseFieldPacket {

    /** state (2 running, 3 cleared), fragments destroyed, Light Magic charge 0-1000 */
    public static OutPacket state(int state, int destroyed, int gauge) {
        OutPacket out = new OutPacket(OutHeader.RANHEIM_DEFENSE_877);
        out.encodeInt(state);
        out.encodeInt(destroyed);
        out.encodeInt(gauge);
        return out;
    }

    public static OutPacket launch(int charId, int launcher) {
        OutPacket out = new OutPacket(OutHeader.RANHEIM_DEFENSE_879);
        out.encodeInt(charId);
        out.encodeInt(launcher);
        return out;
    }

    public static OutPacket shotHit(int launcher, int mobObjectId) {
        OutPacket out = new OutPacket(OutHeader.RANHEIM_DEFENSE_880);
        out.encodeInt(launcher);
        out.encodeInt(mobObjectId);
        return out;
    }
}

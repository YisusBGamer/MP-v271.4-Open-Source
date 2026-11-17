package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.model.MiniRoomPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * @author Sjonnie
 * Created on 8/10/2018.
 */
public class MiniRoomPool {

    public static OutPacket miniRoomBaseDlg(MiniRoomPacket miniRoomPacket) {
        OutPacket outPacket = new OutPacket(OutHeader.MINI_ROOM_BASE_DLG);

        miniRoomPacket.encode(outPacket);

        return outPacket;
    }
}

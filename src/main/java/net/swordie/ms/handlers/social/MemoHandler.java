package net.swordie.ms.handlers.social;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Notes (MEMO_REQUEST 508): 2 delete, 3 mark read, 4 load, 5. Nothing sends notes yet, so a load gets an empty
 * inbox (MEMO_RESULT 6: 1 count) and the rest is ignored.
 */
public class MemoHandler {

    private static final int REQ_LOAD = 4;
    private static final int RES_LIST = 6;

    @Handler(op = InHeader.MEMO_REQUEST)
    public static void handleMemoRequest(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 1) {
            return;
        }
        if (inPacket.decodeByte() == REQ_LOAD) {
            OutPacket outPacket = new OutPacket(OutHeader.MEMO_RESULT);
            outPacket.encodeByte(RES_LIST);
            outPacket.encodeByte(0);
            chr.write(outPacket);
        }
    }
}

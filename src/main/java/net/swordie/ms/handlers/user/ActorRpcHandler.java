package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.jobs.jianghu.Lynn;
import net.swordie.ms.client.jobs.sengoku.Kanna;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RPCs the v271 client calls on its actors (sender 0x1466854f0): byte 9, byte, then per call u16 length of the rest,
 * int object, u16 RPC index (the class's bases first: AActor + UObject have 50), arguments. The client only calls them
 * on actors whose NetOwnerID is its own user.
 */
public class ActorRpcHandler {
    private static final Logger log = LogManager.getLogger(ActorRpcHandler.class);

    @Handler(ops = {InHeader.USER_ACTOR_RPC, InHeader.ACTOR_RPC_ALT})
    public static void handleActorRpc(Char chr, InPacket inPacket) {
        if (chr == null || inPacket.getUnreadAmount() < 2) {
            return;
        }
        inPacket.decodeByte();
        inPacket.decodeByte();
        while (inPacket.getUnreadAmount() >= 8) {
            int length = inPacket.decodeShort() & 0xFFFF;
            if (length < 6 || length > inPacket.getUnreadAmount()) {
                break;
            }
            int objectId = inPacket.decodeInt();
            int rpc = inPacket.decodeShort() & 0xFFFF;
            byte[] args = inPacket.decodeArr(length - 6);
            if (chr.getJobHandler() instanceof Lynn lynn && lynn.isControllerId(objectId)) {
                lynn.handleRpc(rpc, args);
            } else if (chr.getJobHandler() instanceof Kanna kanna && kanna.isActorId(objectId)) {
                kanna.handleRpc(objectId, rpc, args);
            } else {
                log.debug(String.format("[ActorRpc] %s: object %d, RPC %d, args %s", chr.getName(), objectId, rpc,
                        Util.readableByteArray(args)));
            }
        }
    }
}

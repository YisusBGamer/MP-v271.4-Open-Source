package net.swordie.ms.handlers.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;
import net.swordie.ms.world.field.instance.instancehelper.boss.KalosInstanceHelper;

/**
 * Kalos the Guardian's field requests: int type, int sub, then the sub's body (see KalosInstanceHelper#onRequest).
 */
public class KalosHandler {

    @Handler(op = InHeader.KALOS_FIELD_REQUEST)
    public static void handleFieldRequest(Char chr, InPacket inPacket) {
        if (chr.getInstance() == null || inPacket.getUnreadAmount() < 8) {
            return;
        }
        if (InstanceHelperFactory.getHelperByInstance(chr.getInstance()) instanceof KalosInstanceHelper kalos) {
            int type = inPacket.decodeInt();
            int sub = inPacket.decodeInt();
            kalos.onRequest(chr, type, sub, inPacket);
        }
    }
}

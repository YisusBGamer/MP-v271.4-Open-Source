package net.swordie.ms.handlers.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;
import net.swordie.ms.world.field.instance.instancehelper.boss.LucidInstanceHelper;

/**
 * Lucid's field requests.
 */
public class LucidHandler {

    /** A player sounds a Trumpet Statue (v265 client 0x14349db30: byte 1). */
    @Handler(op = InHeader.LUCID_STATUE_USE_REQUEST)
    public static void handleStatueUseRequest(Char chr, InPacket inPacket) {
        if (chr.getInstance() == null) {
            return;
        }
        if (InstanceHelperFactory.getHelperByInstance(chr.getInstance()) instanceof LucidInstanceHelper lucid) {
            lucid.onStatueUse(chr);
        }
    }
}

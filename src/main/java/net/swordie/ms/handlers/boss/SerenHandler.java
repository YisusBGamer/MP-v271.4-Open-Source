package net.swordie.ms.handlers.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;
import net.swordie.ms.world.field.instance.instancehelper.boss.SerenInstanceHelper;

/**
 * Chosen Seren's field requests.
 */
public class SerenHandler {

    /** int start tick, int end tick, int key: the client's Ray of Light timer ran out. */
    @Handler(op = InHeader.SEREN_RAY_OF_LIGHT_END)
    public static void handleRayOfLightEnd(Char chr, InPacket inPacket) {
        if (chr.getInstance() == null || inPacket.getUnreadAmount() < 12) {
            return;
        }
        inPacket.decodeInt();
        inPacket.decodeInt();
        int key = inPacket.decodeInt();
        if (InstanceHelperFactory.getHelperByInstance(chr.getInstance()) instanceof SerenInstanceHelper seren) {
            seren.onRayOfLightEnd(chr, key);
        }
    }
}

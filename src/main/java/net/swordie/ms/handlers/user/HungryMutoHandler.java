package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.arcane.HungryMutoInstanceHelper;

/** Hungry Muto's delivery request (layout as GMS v271.4). */
public class HungryMutoHandler {
    @Handler(op = InHeader.HUNGRY_MUTO_DELIVER)
    public static void handleHungryMutoDeliver(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 8) {
            return;
        }
        int x = inPacket.decodeInt();
        int y = inPacket.decodeInt();
        var inst = chr.getInstance();
        if (inst != null && inst.getInstanceHelper() instanceof HungryMutoInstanceHelper hm) {
            hm.deliver(chr, x, y);
        }
    }
}

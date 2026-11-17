package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.arcane.ErdaSpectrumInstanceHelper;

/** Erda Spectrum's own requests (layouts as GMS v271.4). */
public class ErdaSpectrumHandler {

    private static ErdaSpectrumInstanceHelper helper(Char chr) {
        var inst = chr.getInstance();
        return inst != null && inst.getInstanceHelper() instanceof ErdaSpectrumInstanceHelper es ? es : null;
    }

    @Handler(op = InHeader.ERDA_SPECTRUM_COLLECTOR_REQUEST)
    public static void handleCollectorRequest(Char chr, InPacket inPacket) {
        var es = helper(chr);
        if (es != null) {
            es.activateCollector(chr);
        }
        chr.dispose();
    }

    @Handler(op = InHeader.ERDA_SPECTRUM_LASER_REQUEST)
    public static void handleLaserRequest(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 12) {
            return;
        }
        inPacket.decodeInt(); // char id
        boolean on = inPacket.decodeInt() != 0;
        int armaObjectId = inPacket.decodeInt();
        var es = helper(chr);
        if (es != null) {
            es.laser(chr, on, armaObjectId);
        }
    }

    @Handler(op = InHeader.ERDA_SPECTRUM_CLUSTER_REQUEST)
    public static void handleClusterRequest(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 12) {
            return;
        }
        inPacket.decodeInt();
        int ok = inPacket.decodeInt();
        int objectId = inPacket.decodeInt();
        var es = helper(chr);
        if (es != null) {
            es.clusterDelivered(chr, ok, objectId);
        }
    }
}

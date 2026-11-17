package net.swordie.ms.handlers.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;
import net.swordie.ms.world.field.instance.instancehelper.boss.GuardianAngelSlimeInstanceHelper;

/**
 * Guardian Angel Slime's field requests.
 */
public class GuardianAngelSlimeHandler {

    private static GuardianAngelSlimeInstanceHelper helper(Char chr) {
        if (chr.getInstance() != null
                && InstanceHelperFactory.getHelperByInstance(chr.getInstance()) instanceof GuardianAngelSlimeInstanceHelper h) {
            return h;
        }
        return null;
    }

    @Handler(op = InHeader.GUARDIAN_ANGEL_SLIME_REQUEST)
    public static void handleRequest(Char chr, InPacket inPacket) {
        var h = helper(chr);
        if (h != null && inPacket.getUnreadAmount() >= 4) {
            h.onRequest(chr, inPacket.decodeInt(), inPacket);
        }
    }

    @Handler(op = InHeader.GUARDIAN_ANGEL_SLIME_CRYSTAL_DROP)
    public static void handleCrystalDrop(Char chr, InPacket inPacket) {
        var h = helper(chr);
        if (h != null && inPacket.getUnreadAmount() >= 12) {
            h.onCrystalDrop(chr, inPacket.decodeInt(), inPacket.decodeInt(), inPacket.decodeInt());
        }
    }

    @Handler(op = InHeader.GUARDIAN_ANGEL_SLIME_WAVE_RESULT)
    public static void handleWaveResult(Char chr, InPacket inPacket) {
        var h = helper(chr);
        if (h != null && inPacket.getUnreadAmount() >= 8) {
            h.onWaveResult(chr, inPacket.decodeInt(), inPacket.decodeInt());
        }
    }
}

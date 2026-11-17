package net.swordie.ms.handlers.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;
import net.swordie.ms.world.field.instance.instancehelper.boss.VerusHillaInstanceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Verus Hilla's field requests.
 */
public class VerusHillaHandler {

    /** A press of the Interact key while standing on the Soul Altar (v265 client 0x1440ee040: int 9). */
    @Handler(op = InHeader.VERUS_HILLA_SOUL_ALTAR_REQUEST)
    public static void handleSoulAltarRequest(Char chr, InPacket inPacket) {
        if (chr.getInstance() == null) {
            return;
        }
        if (InstanceHelperFactory.getHelperByInstance(chr.getInstance()) instanceof VerusHillaInstanceHelper vh) {
            vh.onAltarTap(chr);
        }
    }

    /**
     * A Red Thread caught the player (v265 client 0x1432dfec0): int mob object id, int (the thread's entry), int character
     * id, short x, short y (where), int count + that many ints (the x of each soul fragment the client scattered), byte
     * (non-zero: a guard or invincibility took it).
     */
    @Handler(op = InHeader.VERUS_HILLA_RED_THREAD_CATCH)
    public static void handleRedThreadCatch(Char chr, InPacket inPacket) {
        int mobObjectId = inPacket.decodeInt();
        inPacket.decodeInt();
        int chrId = inPacket.decodeInt();
        short x = inPacket.decodeShort();
        short y = inPacket.decodeShort();
        int count = inPacket.decodeInt();
        if (count < 0 || count > 32 || inPacket.getUnreadAmount() < count * 4) {
            return;
        }
        List<Integer> fragments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            fragments.add(inPacket.decodeInt());
        }
        boolean guarded = inPacket.getUnreadAmount() > 0 && inPacket.decodeByte() != 0;
        if (chrId != chr.getId() || guarded || chr.getInstance() == null) {
            return;
        }
        if (InstanceHelperFactory.getHelperByInstance(chr.getInstance()) instanceof VerusHillaInstanceHelper vh) {
            vh.onRedThreadCatch(chr, mobObjectId, new Position(x, y), fragments);
        }
    }
}

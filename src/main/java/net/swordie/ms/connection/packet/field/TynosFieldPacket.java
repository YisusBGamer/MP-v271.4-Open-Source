package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Tynos fields (client field class 0x14284abd0, OnPacket 0x1422bca70). */
public class TynosFieldPacket {

    /** a 1.5 s screen flash */
    public static OutPacket flash() {
        return new OutPacket(OutHeader.TYNOS_FLASH);
    }

    /** Mitra's Flare at (x, y) with its rect (relative to x, y); standing in it sends In 1530. Off with on = false. */
    public static OutPacket mitraFlare(boolean on, int x, int y, int l, int t, int r, int b, String sound) {
        OutPacket out = new OutPacket(OutHeader.TYNOS_MITRA_FLARE);
        out.encodeInt(on ? 1 : 0);
        if (on) {
            out.encodeInt(x);
            out.encodeInt(y);
            out.encodeInt(l);
            out.encodeInt(t);
            out.encodeInt(r);
            out.encodeInt(b);
            out.encodeString(sound);
        }
        return out;
    }

    /** a spine animation; In 1531 with the id once delayMs has passed. One animation, no loop. */
    public static OutPacket spine(int delayMs, int id, String spine, String ani) {
        OutPacket out = new OutPacket(OutHeader.TYNOS_SPINE);
        out.encodeInt(delayMs);
        out.encodeInt(id);
        out.encodeString(spine);
        out.encodeInt(0);
        out.encodeInt(1);
        out.encodeString(ani);
        out.encodeInt(0);
        return out;
    }

    /** Effect/ClientDirection.img/highMountain/tynus/phaseStart/(phase) */
    public static OutPacket phase(String phase) {
        OutPacket out = new OutPacket(OutHeader.TYNOS_PHASE);
        out.encodeString(phase);
        return out;
    }
}

package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.boss.verushilla.SkullDeathCountType;
import net.swordie.ms.handlers.header.OutHeader;

import java.util.List;
import java.util.Map;

/**
 * Verus Hilla's field packet (VERUS_HILLA_FIELD_OBJECT_CHANGED, v265 client 0x142bd1e50): an int subtype, then its
 * body. The layouts are the client's reads.
 */
public class VerusHillaFieldPacket {

    private static OutPacket of(int subtype) {
        OutPacket outPacket = new OutPacket(OutHeader.VERUS_HILLA_FIELD_OBJECT_CHANGED);
        outPacket.encodeInt(subtype);
        return outPacket;
    }

    /** 0: the candles (how many there are). */
    public static OutPacket candles(int total) {
        OutPacket outPacket = of(0);
        outPacket.encodeInt(total);
        outPacket.encodeByte(false);
        return outPacket;
    }

    /** 1: how many candles are lit. */
    public static OutPacket litCandles(int lit) {
        OutPacket outPacket = of(1);
        outPacket.encodeInt(lit);
        return outPacket;
    }

    /** 3: this player's Spirit Stones, true = green (usable), false = red (taken by a Red Thread), null = gone for good. */
    public static OutPacket spiritStones(List<Boolean> stones) {
        OutPacket outPacket = of(3);
        outPacket.encodeInt(stones.size());
        for (Boolean green : stones) {
            outPacket.encodeInt(0);
            outPacket.encodeByte(green == null ? SkullDeathCountType.Gone.getVal() : green ? SkullDeathCountType.Green.getVal()
                    : SkullDeathCountType.Red.getVal());
        }
        return outPacket;
    }

    /** 4: the Soul Split hourglass: time until it fills. */
    public static OutPacket hourglass(int millis) {
        OutPacket outPacket = of(4);
        outPacket.encodeInt(millis);
        outPacket.encodeInt(247); // Soul Split (mob skill 247)
        outPacket.encodeInt(1);
        return outPacket;
    }

    /** 6: the Soul Altar appears, needing this many presses of the Interact key. */
    public static OutPacket altarCreate(int x, int y, int taps) {
        OutPacket outPacket = of(6);
        outPacket.encodeInt(x);
        outPacket.encodeInt(y);
        outPacket.encodeInt(taps);
        return outPacket;
    }

    /** 7: presses still needed on the Soul Altar. */
    public static OutPacket altarTapsLeft(int taps) {
        OutPacket outPacket = of(7);
        outPacket.encodeInt(taps);
        return outPacket;
    }

    /** 8: the Soul Altar goes (destroyed = true: the players broke it). */
    public static OutPacket altarRemove(boolean destroyed) {
        OutPacket outPacket = of(8);
        outPacket.encodeByte(destroyed);
        return outPacket;
    }

    /**
     * VERUS_HILLA_BLACK_HAND (mob pool, client 0x1436201c0): the Red Threads of an Evil Hand. Per hand: id, then the
     * skill entry (index, mob skill 246, level, has-object, object type 1: x, y as shorts, delay, two ints, the hit
     * rects).
     */
    public static OutPacket blackHand(int mobObjectId, int slv, List<int[]> hands, List<List<net.swordie.ms.util.Rect>> rects) {
        OutPacket outPacket = new OutPacket(OutHeader.VERUS_HILLA_BLACK_HAND);
        outPacket.encodeInt(mobObjectId);
        outPacket.encodeInt(hands.size());
        for (int i = 0; i < hands.size(); i++) {
            int[] h = hands.get(i); // x, y, delay
            outPacket.encodeInt(1);
            outPacket.encodeInt(i + 1);
            outPacket.encodeInt(246); // TrueHillaRedStringHand
            outPacket.encodeInt(slv);
            outPacket.encodeByte(true);
            outPacket.encodeInt(1);
            outPacket.encodeShort(h[0]);
            outPacket.encodeShort(h[1]);
            outPacket.encodeInt(h[2]);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(rects.get(i).size());
            for (var r : rects.get(i)) {
                outPacket.encodeRectInt(r);
            }
        }
        return outPacket;
    }

    /**
     * VERUS_HILLA_SPIRIT (mob pool, client 0x1436202c0): a caught soul. The client reads: int (kept as the target
     * character), an int list (the x of each soul fragment, as the catching client sent them in
     * VERUS_HILLA_RED_THREAD_CATCH), x and y as shorts (where it happened), then one skill entry as in {@link #blackHand}
     * (without its leading id).
     */
    public static OutPacket spirit(int mobObjectId, int chrId, net.swordie.ms.util.Position pos, int slv,
                                   int delay, List<net.swordie.ms.util.Rect> rects) {
        return spirit(mobObjectId, chrId, pos, slv, delay, rects, List.of(pos.getX()));
    }

    public static OutPacket spirit(int mobObjectId, int chrId, net.swordie.ms.util.Position pos, int slv,
                                   int delay, List<net.swordie.ms.util.Rect> rects, List<Integer> fragments) {
        OutPacket outPacket = new OutPacket(OutHeader.VERUS_HILLA_SPIRIT);
        outPacket.encodeInt(mobObjectId);
        outPacket.encodeInt(chrId);
        outPacket.encodeInt(fragments.size());
        for (int fx : fragments) {
            outPacket.encodeInt(fx);
        }
        outPacket.encodeShort(pos.getX());
        outPacket.encodeShort(pos.getY());
        outPacket.encodeInt(1); // entry index (no per-entry id here, unlike blackHand)
        outPacket.encodeInt(246); // TrueHillaRedStringHand
        outPacket.encodeInt(slv);
        outPacket.encodeByte(true);
        outPacket.encodeInt(1);
        outPacket.encodeShort(pos.getX());
        outPacket.encodeShort(-260);
        outPacket.encodeInt(delay);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(rects.size());
        for (var r : rects) {
            outPacket.encodeRectInt(r);
        }
        return outPacket;
    }

    /** 10: every player's remaining Spirit Stones (character id, count). */
    public static OutPacket deathCounts(Map<Integer, Integer> counts) {
        OutPacket outPacket = of(10);
        outPacket.encodeInt(counts.size());
        counts.forEach((chrId, count) -> {
            outPacket.encodeInt(chrId);
            outPacket.encodeInt(count);
        });
        return outPacket;
    }
}

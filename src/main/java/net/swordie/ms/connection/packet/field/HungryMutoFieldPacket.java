package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Hungry Muto fields: HUNGRY_MUTO_INFO, int type then its body. */
public class HungryMutoFieldPacket {

    public enum HungryMutoType {
        Timer(1),
        Clear(2),
        NewOrder(3),
        OrderProgress(4),
        Carried(5),
        ;

        private final int val;

        HungryMutoType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    private static OutPacket info(HungryMutoType type) {
        OutPacket out = new OutPacket(OutHeader.HUNGRY_MUTO_INFO);
        out.encodeInt(type.getVal());
        return out;
    }

    public static OutPacket timer(int ms) {
        OutPacket out = info(HungryMutoType.Timer);
        out.encodeInt(ms);
        return out;
    }

    public static OutPacket clear() {
        return info(HungryMutoType.Clear);
    }

    /**
     * A new order (with its time and the unknown int GMS sends) or its progress after a delivery; order lines are
     * {item, need, have}.
     */
    public static OutPacket order(boolean isNew, int dish, int food, int phase, int orderMs, int unknown, int[][] order) {
        OutPacket out = info(isNew ? HungryMutoType.NewOrder : HungryMutoType.OrderProgress);
        out.encodeInt(dish);
        out.encodeInt(food);
        out.encodeInt(phase);
        if (isNew) {
            out.encodeInt(orderMs);
            out.encodeInt(unknown);
        }
        out.encodeInt(order.length);
        for (int[] line : order) {
            out.encodeInt(line[0]);
            out.encodeInt(line[1]);
            out.encodeInt(line[2]);
        }
        return out;
    }

    /** the ingredients one character carries (GMS: n = 1, then character id, item, count) */
    public static OutPacket carried(int charId, int itemId, int count) {
        OutPacket out = info(HungryMutoType.Carried);
        out.encodeInt(1);
        out.encodeInt(charId);
        out.encodeInt(itemId);
        out.encodeInt(count);
        return out;
    }
}

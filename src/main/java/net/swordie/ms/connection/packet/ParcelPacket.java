package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.handlers.social.ParcelHandler.Parcel;
import net.swordie.ms.util.FileTime;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * Duey (PARCEL_DLG, client 0x143e2aa10): 1 type + body. v271 moved the types from v265: open 9, messages 11..27
 * (0x143e2b270; the v265 ones +1 up to 19, +2 from 20 on), removed 28, notice 30.
 */
public class ParcelPacket {

    public static final int OPEN = 9;
    public static final int NO_MESOS = 11;
    public static final int BAD_REQUEST = 12;
    public static final int CHECK_NAME = 13;
    public static final int SAME_ACCOUNT = 14;
    public static final int RECEIVER_FULL = 15;
    public static final int ONE_OF_A_KIND = 17;
    public static final int SENT = 22;
    public static final int UNKNOWN_ERROR = 23;
    public static final int NO_SLOTS = 25;
    public static final int MESO_LIMIT = 27;
    public static final int REMOVED = 28;
    public static final int NOTICE = 30;

    private static final int NAME_SIZE = 13;
    private static final int MEMO_SIZE = 200;

    // 1 mode (nonzero: quick delivery tab), 4 n records, 1 m records
    public static OutPacket open(Collection<Parcel> parcels) {
        OutPacket outPacket = new OutPacket(OutHeader.PARCEL_DLG);

        outPacket.encodeByte(OPEN);
        outPacket.encodeByte(0);
        outPacket.encodeInt(parcels.size());
        for (Parcel p : parcels) {
            encode(outPacket, p);
        }
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket result(int type) {
        OutPacket outPacket = new OutPacket(OutHeader.PARCEL_DLG);

        outPacket.encodeByte(type);

        return outPacket;
    }

    // 4 id, 1 (3 deleted, 4 claimed)
    public static OutPacket removed(long id, boolean claimed) {
        OutPacket outPacket = new OutPacket(OutHeader.PARCEL_DLG);

        outPacket.encodeByte(REMOVED);
        outPacket.encodeInt((int) id);
        outPacket.encodeByte(claimed ? 4 : 3);

        return outPacket;
    }

    // S sender, 1 quick
    public static OutPacket notice(String sender, boolean quick) {
        OutPacket outPacket = new OutPacket(OutHeader.PARCEL_DLG);

        outPacket.encodeByte(NOTICE);
        outPacket.encodeString(sender);
        outPacket.encodeByte(quick);

        return outPacket;
    }

    // 0x14078aba0: a 235-byte record (4 id, 13 sender, 4 meso, 8 sent, 4 has memo, 200 memo, 1 quick, 1),
    // then 1 has item + item
    private static void encode(OutPacket outPacket, Parcel p) {
        outPacket.encodeInt((int) p.id);
        fixed(outPacket, p.senderName, NAME_SIZE);
        outPacket.encodeInt((int) Math.min(Integer.MAX_VALUE, p.meso));
        outPacket.encodeFT(FileTime.fromEpochMillis(p.sentTime));
        outPacket.encodeInt(p.memo.isEmpty() ? 0 : 1);
        fixed(outPacket, p.memo, MEMO_SIZE);
        outPacket.encodeByte(p.quick);
        outPacket.encodeByte(0);
        outPacket.encodeByte(p.item != null);
        if (p.item != null) {
            p.item.encode(outPacket);
        }
    }

    private static void fixed(OutPacket outPacket, String s, int size) {
        byte[] b = new byte[size];
        byte[] src = (s == null ? "" : s).getBytes(StandardCharsets.ISO_8859_1);
        System.arraycopy(src, 0, b, 0, Math.min(src.length, size - 1));
        outPacket.encodeArr(b);
    }
}

package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.AndroidEmoteType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.android.Android;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.util.FileTime;

/**
 * @author Sjonnie
 * Created on 2/12/2019.
 */
public class AndroidPacket {

    public static OutPacket created(Android android) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_CREATED);

        outPacket.encodeInt(android.getChrId());
        outPacket.encode(android);

        return outPacket;
    }

    public static OutPacket removed(Android android) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_REMOVED);

        outPacket.encodeInt(android.getChrId());

        return outPacket;
    }

    public static OutPacket move(Android android, MovementInfo mi) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_MOVE);

        outPacket.encodeInt(android.getChrId());
        mi.encodeWithoutKeyPads(outPacket); // GMS sends the path without the key pad part

        return outPacket;
    }

    public static OutPacket actionSet(Android android, int action, int randomKey) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_ACTION_SET);

        outPacket.encodeInt(android.getChrId());
        outPacket.encodeByte(action);
        outPacket.encodeByte(randomKey);

        return outPacket;
    }

    public static OutPacket modified(Android android) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_MODIFIED);

        outPacket.encodeInt(android.getChrId());

        // bits 0-6: the 7 equips that follow; the look block below is only read when the mask is 0 (see modifiedLook)
        outPacket.encodeShort(0xFF); // short mask, 13 bytes per equip (v265: 9)
        for (int itemId : android.getItems()) {
            outPacket.encodeInt(itemId);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
        }
        outPacket.encodeInt(0); // v271: one int after the equips
        outPacket.encodeFT(FileTime.MAX_TIME());
        android.encodeAndroidInfo(outPacket);

        return outPacket;
    }
    /**
     * Skin, hair, face and name update: mask 0 sends no equips, then the 8 bytes and the AndroidInfo the client only
     * reads for mask 0.
     */
    public static OutPacket modifiedLook(Android android) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_MODIFIED);

        outPacket.encodeInt(android.getChrId());
        outPacket.encodeShort(0); // short mask
        outPacket.encodeInt(0); // v271: one int after the (empty) equips
        outPacket.encodeFT(FileTime.MAX_TIME());
        android.encodeAndroidInfo(outPacket);

        return outPacket;
    }

    public static OutPacket androidEmotion(Android android, AndroidEmoteType emotionId, int durationMs) {
        OutPacket out = new OutPacket(OutHeader.ANDROID_EMOTION);

        out.encodeInt(emotionId.getId());
        out.encodeInt(durationMs);

        return out;
    }
    public static OutPacket remoteAndroidEmotion(Android android, AndroidEmoteType emotionId, int durationMs) {
        OutPacket out = new OutPacket(OutHeader.REMOTE_ANDROID_EMOTION);

        out.encodeInt(android.getChrId());
        out.encodeInt(emotionId.getId());
        out.encodeInt(durationMs);

        return out;
    }
}

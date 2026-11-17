package net.swordie.ms.handlers.life;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.AndroidPacket;
import net.swordie.ms.enums.AndroidEmoteType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.life.android.Android;
import net.swordie.ms.life.movement.MovementInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AndroidHandler {

    private static final Logger log = LogManager.getLogger();


    @Handler(op = InHeader.ANDROID_MOVE)
    public static void handleAndroidMove(Char chr, InPacket inPacket) {
        Android android = chr.getAndroid();
        if (android == null) {
            return;
        }
        inPacket.decodeInt(); // idk what, but is != 0 if this[48]
        MovementInfo mi = new MovementInfo(inPacket);
        mi.applyTo(android);
        chr.getField().broadcastPacket(AndroidPacket.move(android, mi), chr);
    }

    /**
     * SHOP button of the Android tab (equipment window): char id, 2, x, y. Officially it opens a general store through a
     * shop-capable android with a Heart; here it opens the Regular Shop of @shop.
     */
    @Handler(op = InHeader.USER_QUICK_MOVE_SCRIPT)
    public static void handleAndroidShopButton(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() == 4) {
            // a quick move button (reference server: one int, the entry's v265 code from SET_QUICK_MOVE_INFO)
            int code = inPacket.decodeInt();
            var qmi = net.swordie.ms.constants.GameConstants.getQuickMoveInfos().stream()
                    .filter(info -> info.getV265Code() == code).findFirst().orElse(null);
            if (qmi == null) {
                chr.dispose();
                return;
            }
            net.swordie.ms.handlers.user.MigrationHandler.useQuickMove(chr, qmi);
            return;
        }
        if (chr.getAndroid() == null || chr.getShop() != null || chr.isTalkingToNpc()) {
            chr.dispose();
            return;
        }
        chr.getScriptManager().openShop(GameConstants.COLLECTOR_NPC);
    }

    /**
     * The Android emotion comes from its own UI, not from chat. The v271 sender at 0x145a67ca0 encodes the emotion id
     * and the duration as two ints.
     */
    @Handler(op = InHeader.ANDROID_EMOTION)
    public static void handleAndroidEmotion(Char chr, InPacket inPacket) {
        int emotion = inPacket.decodeInt();
        int duration = inPacket.decodeInt();
        Android android = chr.getAndroid();
        if (android == null) {
            return;
        }
        AndroidEmoteType type = AndroidEmoteType.fromId(emotion);
        if (type == null) {
            return;
        }
        chr.write(AndroidPacket.androidEmotion(android, type, duration));
        chr.getField().broadcastPacket(AndroidPacket.remoteAndroidEmotion(android, type, duration), chr);
    }

    /**
     * The client plays one of its Android's actions (e.g. an idle animation); relay it so the other players in the map
     * see it too.
     */
    @Handler(op = InHeader.ANDROID_ACTION_SET)
    public static void handleAndroidActionSet(Char chr, InPacket inPacket) {
        Android android = chr.getAndroid();
        if (android == null || chr.getField() == null) {
            return;
        }
        int action = inPacket.decodeByte();
        int randomKey = inPacket.decodeByte();
        chr.getField().broadcastPacket(AndroidPacket.actionSet(android, action, randomKey), chr);
    }
}

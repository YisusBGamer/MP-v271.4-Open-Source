package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Story scene packets: illustrations laid over the screen (FIELD_EFFECT 81/82/83, client 0x1406ddeb0 / 0x1406de0e0 /
 * 0x1434c9b20), the narration text (IN_GAME_DIRECTION_EVENT 31) and avatar face (18) of the direction player, and
 * the walking speed of a scripted walk (FORCED_STAT_SET, cleared by the FORCED_STAT_RESET of the next map entry), the
 * map's floating sway (USER_SET_FIELD_FLOATING, client 0x1454a1b60) and the quest guide arrow (START_NAVIGATION).
 * Layouts match GMS; scripts send them with chr.write.
 */
public final class ScenePacket {

    private static final int ILLUSTRATION_SHOW = 81, ILLUSTRATION_REMOVE = 82, ILLUSTRATION_SCALE = 83;
    private static final int DIRECTION_CLEAR_ACTION = 1, DIRECTION_FACE = 18, DIRECTION_ACTION_HOLD = 19, DIRECTION_NARRATION = 31;
    /** illustration fields GMS sends with the same value every time */
    private static final int ILLUSTRATION_DURATION_UNTIL_REMOVED = -1;
    private static final String ILLUSTRATION_ORIGIN_CENTER = "cc";
    /** forced-stat bit of the walking speed (client 0x1410cdf20 keeps it at +0x84) */
    private static final int FORCED_MOVE_SPEED = 1 << 16;
    /** START_NAVIGATION kind GMS uses for an NPC target */
    private static final int NAVIGATION_TO_NPC = 1;

    private ScenePacket() {
    }

    /**
     * Shows an illustration (an Effect/... image path) under a key; later packets with the same key scale or remove it.
     *
     * @param z       draw layer among the scene's illustrations
     * @param animate plays the image's frames
     * @param repeat  frame loops, -1 for endless
     * @param alpha   0-255
     */
    public static OutPacket illustrationShow(String key, String path, boolean flip, int x, int y, int z, int fadeInMs,
                                             boolean animate, int repeat, int alpha) {
        OutPacket out = new OutPacket(OutHeader.FIELD_EFFECT);
        out.encodeByte(ILLUSTRATION_SHOW);
        out.encodeString(key);
        out.encodeString(path);
        out.encodeByte(flip);
        out.encodeInt(x);
        out.encodeInt(y);
        out.encodeInt(z);
        out.encodeInt(fadeInMs);
        out.encodeInt(ILLUSTRATION_DURATION_UNTIL_REMOVED);
        out.encodeString(ILLUSTRATION_ORIGIN_CENTER);
        out.encodeByte(animate);
        out.encodeInt(0);
        out.encodeInt(repeat);
        out.encodeInt(0);
        out.encodeInt(alpha);
        out.encodeByte(0);
        out.encodeString("");
        return out;
    }

    public static OutPacket illustrationRemove(String key, int fadeOutMs) {
        OutPacket out = new OutPacket(OutHeader.FIELD_EFFECT);
        out.encodeByte(ILLUSTRATION_REMOVE);
        out.encodeString(key);
        out.encodeInt(fadeOutMs);
        return out;
    }

    /** scales an illustration, in percent */
    public static OutPacket illustrationScale(String key, int scaleX, int scaleY) {
        OutPacket out = new OutPacket(OutHeader.FIELD_EFFECT);
        out.encodeByte(ILLUSTRATION_SCALE);
        out.encodeString(key);
        out.encodeInt(scaleX);
        out.encodeInt(scaleY);
        out.encodeInt(0);
        out.encodeInt(0);
        out.encodeString("");
        out.encodeInt(0);
        return out;
    }

    /**
     * Narration text over the scene; an empty text with clear set takes it away.
     *
     * @param offsetY  shift from the screen centre
     * @param align    text alignment (GMS: 2 centred, 0 left)
     * @param backColor ARGB as "0xAARRGGBB"
     */
    public static OutPacket narration(String text, boolean clear, int offsetY, int fadeMs, int align, String backColor) {
        OutPacket out = new OutPacket(OutHeader.IN_GAME_DIRECTION_EVENT);
        out.encodeByte(DIRECTION_NARRATION);
        out.encodeString(text);
        out.encodeByte(clear);
        out.encodeInt(0);
        out.encodeInt(0);
        out.encodeInt(offsetY);
        out.encodeInt(fadeMs);
        out.encodeInt(align);
        out.encodeString(backColor);
        return out;
    }

    /** the avatar's face for a while (-1: until changed), e.g. "blink/1", "hit", "smile" */
    public static OutPacket face(String expression, int durationMs) {
        OutPacket out = new OutPacket(OutHeader.IN_GAME_DIRECTION_EVENT);
        out.encodeByte(DIRECTION_FACE);
        out.encodeString(expression);
        out.encodeInt(durationMs);
        return out;
    }

    /** the avatar's walking speed for a scripted walk (GMS scenes: 80-300) */
    public static OutPacket moveSpeed(int speed) {
        OutPacket out = new OutPacket(OutHeader.FORCED_STAT_SET);
        out.encodeInt(FORCED_MOVE_SPEED);
        out.encodeInt(speed);
        return out;
    }

    /**
     * Sways the view as if the map floats (GMS scenes: 3/5/30 for all three values, on; then 0s, off). The client
     * ignores it unless fieldId is the map it is in.
     */
    public static OutPacket fieldFloating(int fieldId, int swayX, int swayY, int speed, boolean on) {
        OutPacket out = new OutPacket(OutHeader.USER_SET_FIELD_FLOATING);
        out.encodeInt(fieldId);
        out.encodeInt(swayX);
        out.encodeInt(swayY);
        out.encodeInt(speed);
        out.encodeByte(on);
        out.encodeInt(0);
        return out;
    }

    /** the guide arrow to an NPC in a map */
    public static OutPacket navigationToNpc(int fieldId, int npcId) {
        OutPacket out = new OutPacket(OutHeader.START_NAVIGATION);
        out.encodeInt(fieldId);
        out.encodeInt(NAVIGATION_TO_NPC);
        out.encodeString(String.valueOf(npcId));
        out.encodeInt(0);
        return out;
    }

    /** ends the avatar's forced action (client 0x14385dd30 case 1: action -1) */
    public static OutPacket clearForcedAction() {
        OutPacket out = new OutPacket(OutHeader.IN_GAME_DIRECTION_EVENT);
        out.encodeByte(DIRECTION_CLEAR_ACTION);
        return out;
    }

    /** the avatar flag GMS sets on before a held forced action and off after it (client case 19, 0x141eac750) */
    public static OutPacket actionHold(boolean on) {
        OutPacket out = new OutPacket(OutHeader.IN_GAME_DIRECTION_EVENT);
        out.encodeByte(DIRECTION_ACTION_HOLD);
        out.encodeInt(on ? 1 : 0);
        return out;
    }
}

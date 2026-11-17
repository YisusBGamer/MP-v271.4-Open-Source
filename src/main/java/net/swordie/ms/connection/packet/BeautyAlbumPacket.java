package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.BeautyAlbum;
import net.swordie.ms.client.character.items.DressUpInfo;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Beauty Album packets (1215 in, 1557 out). Both start with an int key: the RS hash of the message's MSVC type name
 * ("class GMS::BeautyAlbum::InterClientGame::RefreshResponse" etc.). Bodies are a presence-bit byte followed by the
 * present fields; counts are the client's compact ints.
 */
public final class BeautyAlbumPacket {

    private static final String NS = "class GMS::BeautyAlbum::InterClientGame::";
    public static final int REFRESH_REQUEST = key("RefreshRequest");
    public static final int CHANGE_REQUEST = key("ChangeRequest");
    public static final int DELETE_REQUEST = key("DeleteRequest");
    public static final int SAVE_REQUEST = key("SaveRequest");
    private static final int REFRESH_RESPONSE = key("RefreshResponse"); // -1374040409
    private static final int CHANGE_RESPONSE = key("ChangeResponse");
    private static final int SAVE_RESPONSE = key("SaveResponse");
    private static final int EXTEND_RESPONSE = key("ExtendResponse");

    public static final int OK = 1, FAIL = 0;

    private BeautyAlbumPacket() {
    }

    static int key(String type) {
        String s = NS + type;
        int h = 0, a = 63689;
        for (int i = 0; i < s.length(); i++) {
            h = h * a + (byte) s.charAt(i);
            a *= 378551;
        }
        return h;
    }

    /** Writer 0x140eb85c0: zigzag, 7-bit groups */
    private static void encodeCompact(OutPacket out, int x) {
        int z = (x << 1) ^ (x >> 31);
        while ((z & ~0x7F) != 0) {
            out.encodeByte((z & 0x7F) | 0x80);
            z >>>= 7;
        }
        out.encodeByte(z);
    }

    private static void encodeHair(OutPacket out, int hair) {
        out.encodeInt(hair);
        out.encodeByte(0); // mix base colour
        out.encodeByte(0); // mix add colour
        out.encodeByte(0); // mix percent
    }

    private static void encodeSkin(OutPacket out, int skin) {
        out.encodeInt(skin);
        out.encodeInt(0);
    }

    /** RefreshResponse (0x141b2ce30): bits int result, album (0x140527fc0) */
    public static OutPacket refresh(BeautyAlbum album) {
        OutPacket out = new OutPacket(OutHeader.USER_BEAUTY_DATA_RESULT);
        out.encodeInt(REFRESH_RESPONSE);
        out.encodeByte(0x3);
        out.encodeInt(OK);
        int[] hairs = album.slots(BeautyAlbum.HAIR);
        int[] faces = album.slots(BeautyAlbum.FACE);
        int[] skins = album.slots(BeautyAlbum.SKIN);
        out.encodeByte(0x7F);
        out.encodeInt(100);
        out.encodeInt(hairs.length);
        out.encodeInt(faces.length);
        out.encodeInt(skins.length);
        encodeCompact(out, hairs.length);
        for (int hair : hairs) {
            encodeHair(out, hair);
        }
        encodeCompact(out, faces.length);
        for (int face : faces) {
            out.encodeInt(face);
        }
        encodeCompact(out, skins.length);
        for (int skin : skins) {
            encodeSkin(out, skin);
        }
        return out;
    }

    /** ChangeResponse (0x141b2cba0): bit int result */
    public static OutPacket change(int result) {
        OutPacket out = new OutPacket(OutHeader.USER_BEAUTY_DATA_RESULT);
        out.encodeInt(CHANGE_RESPONSE);
        out.encodeByte(0x1);
        out.encodeInt(result);
        return out;
    }

    /** SaveResponse (0x141b2d070): bits int result, int code, hair entry, int face, skin entry */
    public static OutPacket save(int result, int code, int id) {
        OutPacket out = new OutPacket(OutHeader.USER_BEAUTY_DATA_RESULT);
        out.encodeInt(SAVE_RESPONSE);
        int type = BeautyAlbum.typeOf(code);
        int bit = type == BeautyAlbum.HAIR ? 0x4 : type == BeautyAlbum.FACE ? 0x8 : type == BeautyAlbum.SKIN ? 0x10 : 0;
        out.encodeByte(0x3 | bit);
        out.encodeInt(result);
        out.encodeInt(code);
        if (bit == 0x4) {
            encodeHair(out, id);
        } else if (bit == 0x8) {
            out.encodeInt(id);
        } else if (bit == 0x10) {
            encodeSkin(out, id);
        }
        return out;
    }

    /** DRESS_UP_INFO_MODIFIED (354): the local user's dress-up look, no character id */
    public static OutPacket dressUpInfoModified(DressUpInfo info) {
        OutPacket out = new OutPacket(OutHeader.DRESS_UP_INFO_MODIFIED);
        // v271 reads type 1 + its own record (meaning not mapped): sent as "no record" until it is
        out.encodeInt(1);
        out.encodeByte(0);
        return out;
    }

    /** ExtendResponse (0x141b2ccd0): bits int result, int type (3 hair, 2 face, 4 skin), int slot count */
    public static OutPacket extend(int type, int slots) {
        OutPacket out = new OutPacket(OutHeader.USER_BEAUTY_DATA_RESULT);
        out.encodeInt(EXTEND_RESPONSE);
        out.encodeByte(0x7);
        out.encodeInt(OK);
        out.encodeInt(type);
        out.encodeInt(slots);
        return out;
    }
}

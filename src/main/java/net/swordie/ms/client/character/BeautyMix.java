package net.swordie.ms.client.character;

import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.info.ZeroInfo;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.AndroidPacket;
import net.swordie.ms.connection.packet.BeautyAlbumPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.android.Android;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Mix dye and colourblend lens coupons. The client opens its own mixing window, then sends (after slot and item id):
 * byte target (0 own look, 1 dress-up, 2 Zero's Beta, 'd' android, 'e' both Zero looks), byte, [android: 8 bytes],
 * [custom and half-and-half coupons: int mix code, 'e': a second one for Beta]. Mix code = base colour * 10000 +
 * mix colour * 1000 + mix percent. Mystery coupons send no code: the server picks the mix colour (50:50).
 * Styling is free, so the coupon is kept.
 */
public class BeautyMix {

    private static final Logger log = LogManager.getLogger(BeautyMix.class);

    public static final int TARGET_SELF = 0;
    public static final int TARGET_DRESS_UP = 1;
    public static final int TARGET_BETA = 2;
    public static final int TARGET_ANDROID = 'd';
    public static final int TARGET_ZERO_BOTH = 'e';

    private static final Set<Integer> HAIR_USE_ITEMS = Set.of(2432946, 2437931, 2631717, 2632866, 2633543);
    private static final Set<Integer> HAIR_CASH_ITEMS = Set.of(5151040, 5151041, 5151042, 5680814);
    private static final int MYSTERY_HAIR = 5680814;
    private static final int MYSTERY_EYE = 5152301;

    public static boolean isHairMixItem(int itemId) {
        return HAIR_USE_ITEMS.contains(itemId) || HAIR_CASH_ITEMS.contains(itemId);
    }

    public static boolean isEyeMixItem(int itemId) {
        return itemId / 100 == 51523;
    }

    public static boolean isMixItem(int itemId) {
        return isHairMixItem(itemId) || isEyeMixItem(itemId);
    }

    /** Custom and half-and-half coupons send the picked colours; mystery ones don't */
    private static boolean sendsMixCode(int itemId) {
        return itemId != MYSTERY_HAIR && itemId != MYSTERY_EYE;
    }

    /** Reads the rest of a coupon use (after slot and item id) and applies the blend. */
    public static void handle(Char chr, Item item, InPacket inPacket) {
        int itemId = item.getItemId();
        boolean eye = isEyeMixItem(itemId);
        int target = inPacket.decodeByte() & 0xFF;
        inPacket.decodeByte();
        if (target == TARGET_ANDROID) {
            inPacket.decodeLong();
        }
        int code = -1, code2 = -1;
        if (sendsMixCode(itemId)) {
            code = inPacket.decodeInt();
            if (target == TARGET_ZERO_BOTH) {
                code2 = inPacket.decodeInt();
            }
        }
        boolean ok = switch (target) {
            case TARGET_SELF -> applyOwn(chr, false, eye, code);
            case TARGET_BETA -> applyOwn(chr, true, eye, code);
            case TARGET_ZERO_BOTH -> applyOwn(chr, false, eye, code) & applyOwn(chr, true, eye, code2);
            case TARGET_DRESS_UP -> applyDressUp(chr, eye, code);
            case TARGET_ANDROID -> applyAndroid(chr, eye, code);
            default -> false;
        };
        if (!ok) {
            log.debug(String.format("Mix coupon %d: target %d, code %d/%d not applied (chr %d)", itemId, target,
                    code, code2, chr.getId()));
            chr.chatMessage("That colour blend can't be used.");
        }
        chr.dispose();
    }

    /** The look the studio styles: dress-up when it's on, Zero's Beta when tagged, else the normal look */
    private static int studioTarget(Char chr) {
        if (BeautyModule.isDressedUp(chr)) {
            return TARGET_DRESS_UP;
        }
        return chr.isZeroBeta() ? TARGET_BETA : TARGET_SELF;
    }

    /** Studio: blends the current colour with mixColor (percent = the mix colour's share) */
    public static boolean applyFromStudio(Char chr, boolean eye, int mixColor, int percent) {
        int current = currentId(chr, eye);
        int base = eye ? AvatarLook.faceColor(current) : AvatarLook.hairColor(current);
        int code = base * 10000 + mixColor * 1000 + percent;
        return switch (studioTarget(chr)) {
            case TARGET_DRESS_UP -> applyDressUp(chr, eye, code);
            case TARGET_BETA -> applyOwn(chr, true, eye, code);
            default -> applyOwn(chr, false, eye, code);
        };
    }

    /** The studio's current plain hair or face id */
    public static int currentId(Char chr, boolean eye) {
        int target = studioTarget(chr);
        if (target == TARGET_DRESS_UP) {
            return eye ? BeautyModule.getDressFace(chr) : BeautyModule.getDressHair(chr);
        }
        AvatarLook al = target == TARGET_BETA ? chr.getAvatarData().getZeroAvatarLook()
                : chr.getAvatarData().getAvatarLook();
        return eye ? al.getFace() : al.getHair();
    }

    /** The studio's current blend as {mix colour, percent}, or null */
    public static int[] currentMix(Char chr, boolean eye) {
        int target = studioTarget(chr);
        if (target == TARGET_DRESS_UP) {
            BeautyAlbum album = chr.getBeautyAlbum();
            int id = eye ? album.getDressFace() : album.getDressHair();
            return AvatarLook.isMixedId(id) ? new int[]{AvatarLook.mixColorOf(id), AvatarLook.mixPercentOf(id)} : null;
        }
        AvatarLook al = target == TARGET_BETA ? chr.getAvatarData().getZeroAvatarLook()
                : chr.getAvatarData().getAvatarLook();
        if (eye) {
            return al.isFaceMixed() ? new int[]{al.getMixedFaceColor(), al.getMixFacePercent()} : null;
        }
        return al.isHairMixed() ? new int[]{al.getMixedHairColor(), al.getMixHairPercent()} : null;
    }

    /** Studio: back to the plain base colour */
    public static void clearFromStudio(Char chr, boolean eye) {
        int target = studioTarget(chr);
        if (target == TARGET_DRESS_UP) {
            BeautyAlbum album = chr.getBeautyAlbum();
            if (eye) {
                album.setDressFace(AvatarLook.baseId(album.getDressFace()));
            } else {
                album.setDressHair(AvatarLook.baseId(album.getDressHair()));
            }
            BeautyModule.ALBUM_DAO.save(album);
            chr.write(BeautyAlbumPacket.dressUpInfoModified(BeautyModule.dressUpInfo(chr)));
            chr.broadcastRemoteAvatarModified();
            return;
        }
        boolean beta = target == TARGET_BETA;
        AvatarLook al = beta ? chr.getAvatarData().getZeroAvatarLook() : chr.getAvatarData().getAvatarLook();
        if (eye) {
            al.clearFaceMix();
        } else {
            al.clearHairMix();
        }
        refresh(chr, al, beta, eye);
    }

    private static void refresh(Char chr, AvatarLook al, boolean beta, boolean eye) {
        if (beta) {
            ZeroInfo zi = chr.getZeroInfo();
            if (zi != null) {
                zi.setSubFace(al.getEncodedFace());
                zi.setSubHair(al.getEncodedHair());
                zi.setMixBaseHairColor(AvatarLook.hairColor(al.getHair()));
                zi.setMixAddHairColor(al.getMixedHairColor());
                zi.setMixHairBaseProb(al.getMixHairPercent());
                chr.updateZeroInfo();
            }
            return;
        }
        if (eye) {
            chr.setStatAndSendPacket(Stat.face, al.getFace());
        } else {
            chr.setStatAndSendPacket(Stat.hair, al.getHair());
        }
        chr.broadcastRemoteAvatarModified();
    }

    /** @return {base, mix, percent}, or null if the code isn't a valid blend */
    public static int[] decodeMixCode(int code, boolean eye) {
        int base = code / 10000, mix = code % 10000 / 1000, percent = code % 1000;
        int colors = eye ? AvatarLook.EYE_COLORS : AvatarLook.HAIR_COLORS;
        if (code < 0 || base >= colors || mix >= colors || base == mix || percent < 1 || percent > 99) {
            return null;
        }
        return new int[]{base, mix, percent};
    }

    /** code -1 = mystery: the current colour, a random other one, 50:50 */
    private static int[] resolve(int currentId, boolean eye, int code) {
        if (code >= 0) {
            return decodeMixCode(code, eye);
        }
        int base = eye ? AvatarLook.faceColor(currentId) : AvatarLook.hairColor(currentId);
        int colors = eye ? AvatarLook.EYE_COLORS : AvatarLook.HAIR_COLORS;
        if (base >= colors) {
            base = 0;
        }
        int mix = (base + 1 + Util.getRandom(colors - 2)) % colors;
        return new int[]{base, mix, 50};
    }

    private static boolean applyOwn(Char chr, boolean beta, boolean eye, int code) {
        AvatarLook al = beta ? chr.getAvatarData().getZeroAvatarLook() : chr.getAvatarData().getAvatarLook();
        if (al == null || (beta && !JobConstants.isZero(chr.getJob()))) {
            return false;
        }
        int[] m = resolve(eye ? al.getFace() : al.getHair(), eye, code);
        if (m == null) {
            return false;
        }
        if (eye) {
            al.setFaceMix(m[0], m[1], m[2]);
        } else {
            al.setHairMix(m[0], m[1], m[2]);
        }
        refresh(chr, al, beta, eye);
        appliedMessage(chr, eye, m[2], null);
        return true;
    }

    private static boolean applyDressUp(Char chr, boolean eye, int code) {
        if (!BeautyModule.isAngelicBuster(chr)) {
            return false;
        }
        BeautyAlbum album = chr.getBeautyAlbum();
        int current = eye ? BeautyModule.getDressFace(chr) : BeautyModule.getDressHair(chr);
        int[] m = resolve(current, eye, code);
        if (m == null) {
            return false;
        }
        if (eye) {
            album.setDressFace(AvatarLook.mixedId(AvatarLook.withFaceColor(current, m[0]), m[1], m[2]));
        } else {
            album.setDressHair(AvatarLook.mixedId(AvatarLook.withHairColor(current, m[0]), m[1], m[2]));
        }
        BeautyModule.ALBUM_DAO.save(album);
        chr.write(BeautyAlbumPacket.dressUpInfoModified(BeautyModule.dressUpInfo(chr)));
        chr.broadcastRemoteAvatarModified();
        appliedMessage(chr, eye, m[2], null);
        return true;
    }

    private static boolean applyAndroid(Char chr, boolean eye, int code) {
        Android android = chr.getAndroid();
        if (android == null) {
            return false;
        }
        int current = AvatarLook.baseId(eye ? android.getFace() : android.getHair());
        int[] m = resolve(current, eye, code);
        if (m == null) {
            return false;
        }
        if (eye) {
            android.setFace(AvatarLook.mixedId(AvatarLook.withFaceColor(current, m[0]), m[1], m[2]));
        } else {
            android.setHair(AvatarLook.mixedId(AvatarLook.withHairColor(current, m[0]), m[1], m[2]));
        }
        android.saveLook();
        if (android.getField() != null) {
            android.getField().broadcastPacket(AndroidPacket.modifiedLook(android));
        } else {
            chr.write(AndroidPacket.modifiedLook(android));
        }
        appliedMessage(chr, eye, m[2], "your android");
        return true;
    }

    private static void appliedMessage(Char chr, boolean eye, int percent, String who) {
        chr.chatMessage(String.format("A mixed %s colour was applied%s (base colour %d%% : mix colour %d%%).",
                eye ? "eye" : "hair", who == null ? "" : " to " + who, 100 - percent, percent));
    }
}

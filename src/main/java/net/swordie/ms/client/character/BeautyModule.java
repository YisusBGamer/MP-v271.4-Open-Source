package net.swordie.ms.client.character;

import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.info.ZeroInfo;
import net.swordie.ms.client.character.items.DressUpInfo;
import net.swordie.ms.connection.packet.BeautyAlbumPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.orm.dao.BeautyAlbumDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The hairs, faces and skins of the game data, for the free style picker (@style, Big Headward, beauty coupons).
 * Hair ids end in the colour (0-9); faces have the eye colour as their hundreds digit. Styles are grouped by their
 * thousands block (e.g. 30000-30999), which keeps each list well under the 255 options the preview can show.
 */
public final class BeautyModule {

    public static final String SCRIPT = "beautyStyle";
    public static final int SPEAKER = 1012117; // Big Headward

    private static volatile Map<Integer, TreeSet<Integer>> hairsByStyle;
    private static volatile Map<Integer, TreeSet<Integer>> facesByStyle;

    private BeautyModule() {
    }

    private static void load() {
        if (hairsByStyle != null) {
            return;
        }
        synchronized (BeautyModule.class) {
            if (hairsByStyle != null) {
                return;
            }
            ItemData.getEquipInfoById(0); // loads the equip table
            Set<Integer> ids = new HashSet<>(ItemData.getEquips().keySet());
            if (ItemData.equipOffsets != null) {
                ids.addAll(ItemData.equipOffsets.keySet());
            }
            Map<Integer, TreeSet<Integer>> hairs = new TreeMap<>();
            Map<Integer, TreeSet<Integer>> faces = new TreeMap<>();
            for (int id : ids) {
                if (ItemConstants.isHair(id)) {
                    hairs.computeIfAbsent(hairStyle(id), k -> new TreeSet<>()).add(id);
                } else if (ItemConstants.isFace(id)) {
                    faces.computeIfAbsent(faceStyle(id), k -> new TreeSet<>()).add(id);
                }
            }
            facesByStyle = faces;
            hairsByStyle = hairs;
        }
    }

    private static int hairStyle(int hair) {
        return hair / 10 * 10;
    }

    private static int faceStyle(int face) {
        return face - (face / 100 % 10) * 100;
    }

    private static int[] toArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int[] blocks(Map<Integer, TreeSet<Integer>> styles) {
        return styles.keySet().stream().mapToInt(s -> s / 1000).distinct().toArray();
    }

    /** @return the thousands blocks that have hairs (30 = 30000-30999, ...) */
    public static int[] getHairBlocks() {
        load();
        return blocks(hairsByStyle);
    }

    /** @return the thousands blocks that have faces (20 = 20000-20999, ...) */
    public static int[] getFaceBlocks() {
        load();
        return blocks(facesByStyle);
    }

    /** @return how many styles a block has */
    public static int getHairStyleCount(int block) {
        load();
        return (int) hairsByStyle.keySet().stream().filter(s -> s / 1000 == block).count();
    }

    public static int getFaceStyleCount(int block) {
        load();
        return (int) facesByStyle.keySet().stream().filter(s -> s / 1000 == block).count();
    }

    /**
     * @return every hairstyle of a block, in the given colour where the style has it (else its first colour)
     */
    public static int[] getHairStyles(int block, int colour) {
        load();
        List<Integer> res = new ArrayList<>();
        for (var e : hairsByStyle.entrySet()) {
            if (e.getKey() / 1000 == block) {
                int wanted = e.getKey() + colour;
                res.add(e.getValue().contains(wanted) ? wanted : e.getValue().first());
            }
        }
        return toArray(res);
    }

    /** @return every face of a block, in the given eye colour (0-9) where the face has it (else its first one) */
    public static int[] getFaceStyles(int block, int eyeColour) {
        load();
        List<Integer> res = new ArrayList<>();
        for (var e : facesByStyle.entrySet()) {
            if (e.getKey() / 1000 == block) {
                int wanted = e.getKey() + eyeColour * 100;
                res.add(e.getValue().contains(wanted) ? wanted : e.getValue().first());
            }
        }
        return toArray(res);
    }

    /** @return the colours a hairstyle comes in (full ids) */
    public static int[] getHairColours(int hair) {
        load();
        var colours = hairsByStyle.get(hairStyle(hair));
        return colours == null ? new int[]{hair} : toArray(new ArrayList<>(colours));
    }

    /** @return the eye colours a face comes in (full ids) */
    public static int[] getFaceColours(int face) {
        load();
        var colours = facesByStyle.get(faceStyle(face));
        return colours == null ? new int[]{face} : toArray(new ArrayList<>(colours));
    }

    /** @return every skin tone */
    public static int[] getSkins() {
        return toArray(ItemConstants.getSkins());
    }

    /** @return whether the game data has this hair or face */
    public static boolean exists(int look) {
        load();
        if (ItemConstants.isHair(look)) {
            var s = hairsByStyle.get(hairStyle(look));
            return s != null && s.contains(look);
        }
        if (ItemConstants.isFace(look)) {
            var s = facesByStyle.get(faceStyle(look));
            return s != null && s.contains(look);
        }
        return look < 100 && ItemConstants.isSkin(look);
    }

    // ------------------------------------------------------------------------- second looks (Zero Beta, AB dress-up)

    private static boolean zeroBeta(Char chr) {
        return JobConstants.isZero(chr.getJob()) && chr.getAvatarData().getZeroAvatarLook() != null;
    }

    public static boolean isAngelicBuster(Char chr) {
        return chr != null && JobConstants.isAngelicBuster(chr.getJob());
    }

    /** Angelic Buster in her dress-up look: salons in @style change that look */
    public static boolean isDressedUp(Char chr) {
        return isAngelicBuster(chr) && chr.getBeautyAlbum().isDressOn();
    }

    public static DressUpInfo dressUpInfo(Char chr) {
        DressUpInfo info = new DressUpInfo();
        if (isAngelicBuster(chr)) {
            BeautyAlbum album = chr.getBeautyAlbum();
            info.setFace(album.getDressFace());
            info.setHair(album.getDressHair());
            info.setSkin((byte) album.getDressSkin());
            info.setDressedUp(album.isDressOn());
        }
        return info;
    }

    /** The client's dress-up switch (566): saved, and others see the change */
    public static void setDressedUp(Char chr, boolean on) {
        if (!isAngelicBuster(chr)) {
            return;
        }
        BeautyAlbum album = chr.getBeautyAlbum();
        chr.getAvatarData().getAvatarLook().setDressUp(on);
        if (album.isDressOn() != on) {
            album.setDressOn(on);
            ALBUM_DAO.save(album);
        }
        chr.broadcastRemoteAvatarModified();
    }

    /** A new style in the same colour keeps the old id's blend; a new colour drops it */
    private static int keepBlend(int oldId, int look, boolean face) {
        if (!AvatarLook.isMixedId(oldId)) {
            return look;
        }
        int base = AvatarLook.baseId(oldId);
        boolean sameColor = face ? AvatarLook.faceColor(base) == AvatarLook.faceColor(look)
                : AvatarLook.hairColor(base) == AvatarLook.hairColor(look);
        return sameColor ? AvatarLook.mixedId(look, AvatarLook.mixColorOf(oldId), AvatarLook.mixPercentOf(oldId))
                : look;
    }

    /** Dress-up ids of 0 are the job's default; the normal look stands in for them in menus */
    public static int getDressHair(Char chr) {
        int hair = AvatarLook.baseId(chr.getBeautyAlbum().getDressHair());
        return hair > 0 ? hair : chr.getAvatarData().getAvatarLook().getHair();
    }

    public static int getDressFace(Char chr) {
        int face = AvatarLook.baseId(chr.getBeautyAlbum().getDressFace());
        return face > 0 ? face : chr.getAvatarData().getAvatarLook().getFace();
    }

    public static int getDressSkin(Char chr) {
        int skin = chr.getBeautyAlbum().getDressSkin();
        return skin > 0 ? skin : chr.getAvatarData().getAvatarLook().getSkin();
    }

    /** Changes AB's dress-up hair, face or skin (DRESS_UP_INFO_MODIFIED goes to her only: it has no character id) */
    public static boolean changeDressUpLook(Char chr, int look) {
        if (!isAngelicBuster(chr)) {
            return false;
        }
        BeautyAlbum album = chr.getBeautyAlbum();
        if (look < 100) {
            album.setDressSkin(look);
        } else if (ItemConstants.isFace(look)) {
            album.setDressFace(keepBlend(album.getDressFace(), look, true));
        } else if (ItemConstants.isHair(look)) {
            album.setDressHair(keepBlend(album.getDressHair(), look, false));
        } else {
            return false;
        }
        ALBUM_DAO.save(album);
        chr.write(BeautyAlbumPacket.dressUpInfoModified(dressUpInfo(chr)));
        return true;
    }

    /**
     * Applies a hair, face or skin. main = the normal look; otherwise Zero's Beta or AB's dress-up look.
     * @return false if there's nothing to apply it to
     */
    public static boolean applyLook(Char chr, int look, boolean main) {
        if (!main && isAngelicBuster(chr)) {
            return changeDressUpLook(chr, look);
        }
        boolean beta = !main && zeroBeta(chr);
        if (!main && !beta) {
            return false;
        }
        AvatarLook al = beta ? chr.getAvatarData().getZeroAvatarLook() : chr.getAvatarData().getAvatarLook();
        ZeroInfo zi = chr.getZeroInfo();
        if (look < 100) {
            al.setSkin(look);
            if (!beta) {
                chr.setStatAndSendPacket(Stat.skin, look);
            } else if (zi != null) {
                zi.setSubSkin(look);
            }
        } else if (ItemConstants.isFace(look)) {
            al.setFace(look);
            if (!beta) {
                chr.setStatAndSendPacket(Stat.face, look);
            } else if (zi != null) {
                zi.setSubFace(al.getEncodedFace());
            }
        } else if (ItemConstants.isHair(look)) {
            al.setHair(look);
            if (!beta) {
                chr.setStatAndSendPacket(Stat.hair, look);
            } else if (zi != null) {
                zi.setSubHair(al.getEncodedHair());
            }
        } else {
            return false;
        }
        if (beta && zi != null) {
            chr.updateZeroInfo();
        } else {
            chr.broadcastRemoteAvatarModified();
        }
        return true;
    }

    /** @return the current hair/face/skin (album type) of the normal or second look, or -1 */
    public static int currentLook(Char chr, int type, boolean main) {
        AvatarLook al;
        if (main) {
            al = chr.getAvatarData().getAvatarLook();
        } else if (isAngelicBuster(chr)) {
            BeautyAlbum album = chr.getBeautyAlbum();
            int id = type == BeautyAlbum.HAIR ? AvatarLook.baseId(album.getDressHair()) : type == BeautyAlbum.FACE
                    ? AvatarLook.baseId(album.getDressFace()) : album.getDressSkin();
            return id > 0 || type == BeautyAlbum.SKIN ? id : -1;
        } else if (zeroBeta(chr)) {
            al = chr.getAvatarData().getZeroAvatarLook();
        } else {
            return -1;
        }
        return type == BeautyAlbum.HAIR ? al.getHair() : type == BeautyAlbum.FACE ? al.getFace() : al.getSkin();
    }

    public static final BeautyAlbumDao ALBUM_DAO = new BeautyAlbumDao();
}

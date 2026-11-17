package net.swordie.ms.client.character.avatar;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.db.converters.InlinedIntArrayConverter;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.WeaponType;
import net.swordie.orm.dao.AvatarLookDao;
import net.swordie.orm.dao.SworDaoFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2/18/2017.
 */
@Entity
@Table(name = "avatarlook")
public class AvatarLook {

    private static final AvatarLookDao avatarLookDao = (AvatarLookDao) SworDaoFactory.getByClass(AvatarLook.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int gender;
    private int skin;
    private int face;
    private int hair;
    private int weaponStickerId;

    private int weaponId;
    private int subWeaponId;

    @ElementCollection
    @CollectionTable(name = "hairequips", joinColumns = @JoinColumn(name = "alId"))
    @Column(name = "equipId")
    private List<Integer> hairEquips;

    @ElementCollection
    @CollectionTable(name = "unseenequips", joinColumns = @JoinColumn(name = "alId"))
    @Column(name = "equipId")
    private List<Integer> unseenEquips;

    @Convert(converter = InlinedIntArrayConverter.class)
    private List<Integer> petIDs = Arrays.asList(0, 0, 0);

    private int job;
    private boolean drawElfEar;
    private int demonSlayerDefFaceAcc;
    private int xenonDefFaceAcc;
    private int beastTamerDefFaceAcc;
    private EarStyle earStyle;

    private boolean isZeroBetaLook;
    private int mixedHairColor;
    private int mixHairPercent;
    private int mixedFaceColor;
    private int mixFacePercent;

    @Convert(converter = InlinedIntArrayConverter.class)
    private List<Integer> totems;

    private int ears;
    private int tail;
    @Transient
    private String name = ""; // v265 ends the look with the character's name
    @Transient
    private boolean dressUp; // Angelic Buster in her dress-up look (saved in beauty_albums)
    @Transient
    private int demonWingID;
    @Transient
    private int kaiserWingID;
    @Transient
    private int kaiserTailID;

    public AvatarLook(boolean init) {
        if (init) {
            hairEquips = new ArrayList<>();
            unseenEquips = new ArrayList<>();
            petIDs = Arrays.asList(0, 0, 0);
            totems = new ArrayList<>();
        }
    }

    public AvatarLook deepCopy() {
        AvatarLook res = new AvatarLook(false);
        res.setGender(getGender());
        res.setSkin(getSkin());
        res.setFace(getFace());
        res.setHair(getHair());
        res.setWeaponStickerId(getWeaponStickerId());
        res.setWeaponId(getWeaponId());
        res.setSubWeaponId(getSubWeaponId());
        List<Integer> resHairEquips = new ArrayList<>(getHairEquips());
        res.setHairEquips(resHairEquips);
        List<Integer> resUnseenEquips = new ArrayList<>(getUnseenEquips());
        res.setUnseenEquips(resUnseenEquips);
        List<Integer> resPetIDs = new ArrayList<>(getPetIDs());
        res.setUnseenEquips(resPetIDs);
        res.setJob(getJob());
        res.setDrawElfEar(isDrawElfEar());
        res.setDemonSlayerDefFaceAcc(getDemonSlayerDefFaceAcc());
        res.setXenonDefFaceAcc(getXenonDefFaceAcc());
        res.setBeastTamerDefFaceAcc(getBeastTamerDefFaceAcc());
        res.setZeroBetaLook(isZeroBetaLook());
        res.setDressUp(isDressUp());
        res.setMixedHairColor(getMixedHairColor());
        res.setMixHairPercent(getMixHairPercent());
        res.setMixedFaceColor(getMixedFaceColor());
        res.setMixFacePercent(getMixFacePercent());
        List<Integer> resTotems = new ArrayList<>(getTotems());
        res.setTotems(resTotems);
        res.setEars(getEars());
        res.setTail(getTail());
        res.setEarStyle(getEarStyle());
        return res;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        if (this.face != 0 && faceColor(face) != faceColor(this.face)) {
            clearFaceMix(); // a plain eye colour replaces the blend
        }
        this.face = face;
    }

    public int getWeaponStickerId() {
        return weaponStickerId;
    }

    public void setWeaponStickerId(int weaponStickerId) {
        this.weaponStickerId = weaponStickerId;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }

    public int getSubWeaponId() {
        return subWeaponId;
    }

    public void setSubWeaponId(int subWeaponId) {
        this.subWeaponId = subWeaponId;
    }

    public List<Integer> getHairEquips() {
        if (hairEquips == null) {
            hairEquips = avatarLookDao.getHairEquipsByAvatarLook(this);
        }

        return hairEquips;
    }

    public void setHairEquips(List<Integer> hairEquips) {
        this.hairEquips = hairEquips;
    }

    public List<Integer> getUnseenEquips() {
        if (unseenEquips == null) {
            unseenEquips = avatarLookDao.getUnseenEquipsByAvatarLook(this);
        }

        return unseenEquips;
    }

    public void setUnseenEquips(List<Integer> unseenEquips) {
        this.unseenEquips = unseenEquips;
    }

    public List<Integer> getPetIDs() {
        return petIDs;
    }

    public void setPetIDs(List<Integer> petIDs) {
        this.petIDs = petIDs;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public boolean isDrawElfEar() {
        return drawElfEar;
    }

    public void setDrawElfEar(boolean drawElfEar) {
        this.drawElfEar = drawElfEar;
    }

    public int getDemonSlayerDefFaceAcc() {
        return demonSlayerDefFaceAcc;
    }

    public void setDemonSlayerDefFaceAcc(int demonSlayerDefFaceAcc) {
        this.demonSlayerDefFaceAcc = demonSlayerDefFaceAcc;
    }

    public int getXenonDefFaceAcc() {
        return xenonDefFaceAcc;
    }

    public void setXenonDefFaceAcc(int xenonDefFaceAcc) {
        this.xenonDefFaceAcc = xenonDefFaceAcc;
    }

    public int getBeastTamerDefFaceAcc() {
        return beastTamerDefFaceAcc;
    }

    public void setBeastTamerDefFaceAcc(int beastTamerDefFaceAcc) {
        this.beastTamerDefFaceAcc = beastTamerDefFaceAcc;
    }

    public boolean isZeroBetaLook() {
        return isZeroBetaLook;
    }

    public void setZeroBetaLook(boolean zeroBetaLook) {
        isZeroBetaLook = zeroBetaLook;
    }

    public boolean isDressUp() {
        return dressUp;
    }

    public void setDressUp(boolean dressUp) {
        this.dressUp = dressUp;
    }

    public int getMixedHairColor() {
        return mixedHairColor;
    }

    public void setMixedHairColor(int mixedHairColor) {
        this.mixedHairColor = mixedHairColor;
    }

    public int getMixHairPercent() {
        return mixHairPercent;
    }

    public void setMixHairPercent(int mixHairPercent) {
        this.mixHairPercent = mixHairPercent;
    }

    public int getMixedFaceColor() {
        return mixedFaceColor;
    }

    public void setMixedFaceColor(int mixedFaceColor) {
        this.mixedFaceColor = mixedFaceColor;
    }

    public int getMixFacePercent() {
        return mixFacePercent;
    }

    public void setMixFacePercent(int mixFacePercent) {
        this.mixFacePercent = mixFacePercent;
    }

    // v265 has no separate mix fields: a blended hair or face is sent as one id,
    // id * 1000 + mix colour * 100 + mix percent (anything above 9,999,999 is blended)
    public static final int MIX_MIN = 10_000_000;
    public static final int HAIR_COLORS = 8;
    public static final int EYE_COLORS = 9;

    public static boolean isMixedId(int id) {
        return id % 1_000_000_000 >= MIX_MIN;
    }

    public static int mixedId(int id, int mixColor, int percent) {
        return percent > 0 && percent < 100 ? id * 1000 + mixColor * 100 + percent : id;
    }

    /** The plain hair/face id of a possibly blended id */
    public static int baseId(int id) {
        return isMixedId(id) ? id % 1_000_000_000 / 1000 : id;
    }

    public static int mixColorOf(int id) {
        return isMixedId(id) ? id % 1000 / 100 : -1;
    }

    public static int mixPercentOf(int id) {
        return isMixedId(id) ? id % 100 : 0;
    }

    public static int hairColor(int hair) {
        return hair % 10;
    }

    public static int faceColor(int face) {
        return face / 100 % 10;
    }

    public static int withHairColor(int hair, int color) {
        return hair / 10 * 10 + color;
    }

    public static int withFaceColor(int face, int color) {
        return face / 1000 * 1000 + color * 100 + face % 100;
    }

    public boolean isHairMixed() {
        return mixHairPercent > 0 && mixHairPercent < 100;
    }

    public boolean isFaceMixed() {
        return mixFacePercent > 0 && mixFacePercent < 100;
    }

    /** The hair id the client draws (blended if a mix dye is on) */
    public int getEncodedHair() {
        return isHairMixed() ? mixedId(getHair(), mixedHairColor, mixHairPercent) : getHair();
    }

    public int getEncodedFace() {
        return isFaceMixed() ? mixedId(getFace(), mixedFaceColor, mixFacePercent) : getFace();
    }

    /** hair is the look's own hair: then its blend is added */
    public int encodeHair(int hair) {
        return hair == getHair() ? getEncodedHair() : hair;
    }

    public int encodeFace(int face) {
        return face == getFace() ? getEncodedFace() : face;
    }

    public void setHairMix(int baseColor, int mixColor, int percent) {
        setHair(withHairColor(getHair(), baseColor));
        mixedHairColor = mixColor;
        mixHairPercent = percent;
    }

    public void setFaceMix(int baseColor, int mixColor, int percent) {
        setFace(withFaceColor(getFace(), baseColor));
        mixedFaceColor = mixColor;
        mixFacePercent = percent;
    }

    public void clearHairMix() {
        mixedHairColor = 0;
        mixHairPercent = 0;
    }

    public void clearFaceMix() {
        mixedFaceColor = 0;
        mixFacePercent = 0;
    }

    /** Sets hair from a client id, blended or not */
    public void setEncodedHair(int id) {
        hair = baseId(id);
        mixedHairColor = Math.max(0, mixColorOf(id));
        mixHairPercent = mixPercentOf(id);
    }

    public void setEncodedFace(int id) {
        face = baseId(id);
        mixedFaceColor = Math.max(0, mixColorOf(id));
        mixFacePercent = mixPercentOf(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    /**
     * The secondary worn (shield slot): GMS sends it as the look's sub weapon (Erel Light's 990 after the story choice:
     * weapon 1433001, sub weapon 1352881), and the client checks skills against it; it was never set here, so every
     * look went out with 0. Zero's Lapis rides in the weapon slot and is left out.
     */
    private int wornSubWeaponId() {
        if (getSubWeaponId() != 0) {
            return getSubWeaponId();
        }
        for (int itemId : new ArrayList<>(getHairEquips())) {
            if (!ItemConstants.isLongOrBigSword(itemId)
                    && ItemConstants.getBodyPartFromItem(itemId, getGender()) == BodyPart.Shield.getVal()) {
                return itemId;
            }
        }
        return 0;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getGender());
        outPacket.encodeByte(getSkin());
        outPacket.encodeInt(0);
        outPacket.encodeInt(getEncodedFace());
        outPacket.encodeInt(getJob());
        outPacket.encodeByte(0); // ignored
        outPacket.encodeInt(getEncodedHair());

        // copies: equips can change on another thread while a look is encoded
        for (int itemId : new ArrayList<>(getHairEquips())) {
            int bodyPart = ItemConstants.getBodyPartFromItem(itemId, getGender());
            if (bodyPart != 0) {
                if (ItemConstants.isLongOrBigSword(itemId)) { // Temporary fix.. When it comes to Slots, Lapis should be Shield slot, but AL encodes it as Weapon.
                    outPacket.encodeByte(BodyPart.Weapon.getVal());
                    outPacket.encodeInt(itemId);
                } else {
                    outPacket.encodeByte(bodyPart);
                    outPacket.encodeInt(itemId);
                }
            }
        }
        outPacket.encodeByte(-1);

        for (int itemId : new ArrayList<>(getUnseenEquips())) {
            if (itemId != 0) {
                outPacket.encodeByte(ItemConstants.getBodyPartFromItem(itemId, getGender())); // body part
                outPacket.encodeInt(itemId);
            }
        }
        outPacket.encodeByte(-1);

        // v265 has three lists (equipped, hidden, totems); v232's fourth, always-empty list is gone. Totem slots are
        // 0-3 here (the client adds 5000), not the body part.
        int totemSlot = 0;
        for (int itemId : getTotems()) {
            if (totemSlot > 3) {
                break;
            }
            outPacket.encodeByte(totemSlot++);
            outPacket.encodeInt(itemId);
        }
        outPacket.encodeByte(-1);

        outPacket.encodeInt(getWeaponStickerId());
        outPacket.encodeInt(getWeaponId());
        outPacket.encodeInt(wornSubWeaponId());
        outPacket.encodeInt(getEarStyle().ordinal());
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(isDrawElfEar());
        outPacket.encodeInt(0);
        // v271: 9 part-visibility flags (client 0x14079a210; every client AvatarLook ctor sets all 9 to 1).
        // Flag 3 shows the cape layer, flag 5 the cap layer (0x143875a50); 0 hides that part.
        for (int i = 0; i < 9; i++) {
            outPacket.encodeByte(1);
        }

        for (int i = 0; i < 3; i++) {
            if (getPetIDs() != null && getPetIDs().size() > i) {
                outPacket.encodeInt(getPetIDs().get(i)); // always 3
            } else {
                outPacket.encodeInt(0);
            }
        }
        outPacket.encodeArr(new byte[528]); // v271: 132 ints at client +0x249 (v265 66)

        if (JobConstants.isDemon(getJob())) {
            outPacket.encodeInt(getDemonSlayerDefFaceAcc());
        } else if (JobConstants.isXenon(getJob())) {
            outPacket.encodeInt(getXenonDefFaceAcc());
        } else if (JobConstants.isArk(getJob())) {
            outPacket.encodeInt(0); // face acc?
        } else if (JobConstants.isHoYoung(getJob())) {
            outPacket.encodeInt(0); // face acc?
        } else if (JobConstants.isAngelicBuster(getJob())) {
            outPacket.encodeByte(isDressUp()); // +0x37f
        } else if (JobConstants.isZero(getJob())) {
             outPacket.encodeByte(isZeroBetaLook());
        }
        // v271: the Beast Tamer ears/tail branch is gone, and a new int comes before the name
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeString(getName(), 13);
    }

    public void encodePackedCharacterLook(OutPacket outPacket, Char chr) {
        // flawlessy typed over
        // and let's call it loop unrolling
        int[] hairEquips = new int[11];
        for (int i = 0; i < hairEquips.length; i++) {
             Item item = chr.getEquippedInventory().getItemBySlot(i);
             if (item != null) {
                 hairEquips[i] = item.getItemId();
             } else {
                 hairEquips[i] = -1;
             }
        }
        byte[] data = new byte[24];
        int weaponID = getWeaponStickerId() != 0 ? getWeaponStickerId() : getWeaponId();
        WeaponType weaponType = ItemConstants.getWeaponType(weaponID);
        int wt = weaponType == null ? 0 : weaponType.getVal();
        int faceAcc = 0;
        if (getDemonSlayerDefFaceAcc() != 0) {
            faceAcc = getDemonSlayerDefFaceAcc();
        } else if (getXenonDefFaceAcc() != 0) {
            faceAcc = getXenonDefFaceAcc();
        } else if (getBeastTamerDefFaceAcc() != 0) {
            faceAcc = getBeastTamerDefFaceAcc();
        }
        data[0] |= getGender() & 1;
        data[0] |= (getSkin() & 0xF) << 1;
        data[0] |= ((getFace() % 1000) & 0x3FF) << 5;
        data[1] |= (getFace() / 1000 % 10 & 7) << 7;
        data[2] |= (getFace() / 10000 == 4 ? 1 : 0) << 2;
        data[2] |= ((getFace() % 1000) & 0x3FF) << 3;
        data[3] |= (getFace() / 1000 % 10 & 0xF) << 5;
        data[4] |= ((hairEquips[1] % 1000) & 0x3FF) << 1;
        data[5] |= (hairEquips[1] / 1000 % 10 & 7) << 3;
        data[5] |= ((hairEquips[2] % 1000) & 0x3FF) << 6;
        data[7] |= faceAcc / 1000 % 10 & 3;
        data[7] |= (hairEquips[3] % 1000) & 0x3FF;
        data[8] |= (hairEquips[3] / 1000 % 10 & 3) << 4;
        data[8] |= ((hairEquips[4] % 1000) & 0x3FF) << 6;
        data[10] |= hairEquips[4] / 1000 % 10 & 3;
        data[10] |= (hairEquips[5] / 10000 == 105 ? 1 : 0) << 2;
        data[10] |= ((hairEquips[5] % 1000) & 0x3FF) << 3;
        data[11] |= ((hairEquips[5] / 1000 % 10) & 0xF) << 5;
        data[12] |= ((hairEquips[6] % 1000) & 0x3FF) << 1;
        data[13] |= (hairEquips[6] / 1000 % 10 & 3) << 3;
        data[13] |= ((hairEquips[7] % 1000) & 0x3FF) << 5;
        data[14] |= (hairEquips[7] / 1000 % 10 & 3) << 7;
        data[15] |= ((hairEquips[8] % 1000) & 0x3FF) << 1;
        data[16] |= (hairEquips[8] / 1000 % 10 & 3) << 3;
        data[16] |= ((hairEquips[9] % 1000) & 0x3FF) << 5;
        data[17] |= (hairEquips[9] / 1000 % 10 & 3) << 7;
        int val = hairEquips[10];
        if (val / 10000 == 109) {
            val = 1;
        } else {
            val = (val / 10000 != 134 ? 1 : 0) + 2;
        }
        data[18] |= (val & 3) << 1;
        data[19] |= ((hairEquips[10] % 1000) & 0x3FF) << 3;
        data[19] |= (hairEquips[10] / 1000 % 10 & 0xF) << 5;
        data[20] |= getWeaponStickerId() / 100000 == 17 ? 2 : 0;
        data[20] |= ((weaponID % 1000) & 0x3FF) << 2;
        data[21] |= (weaponID / 1000 % 10 & 3) << 4;
        data[21] |= (wt & 0x1F) << 6;
        data[22] |= (isDrawElfEar() ? 1 : 0) << 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 7; j >= 0; j--) {
                // basically encoding 3 sequential longs in little endian
                outPacket.encodeByte(data[j + i * 8]);
            }
        }
    }

    public void decode(InPacket inPacket) {
        // mirrors encode (v265 layout)
        setGender(inPacket.decodeByte());
        setSkin(inPacket.decodeByte());
        inPacket.decodeInt();
        setEncodedFace(inPacket.decodeInt());
        setJob(inPacket.decodeInt());
        inPacket.decodeByte(); // ignored
        setEncodedHair(inPacket.decodeInt());

        while (inPacket.decodeByte() != -1) { // Body Part
            int itemId = inPacket.decodeInt();
            getHairEquips().add(itemId);
        }
        while (inPacket.decodeByte() != -1) { // Body Part
            int itemId = inPacket.decodeInt();
            getUnseenEquips().add(itemId);
        }
        while (inPacket.decodeByte() != -1) { // Body Part
            int itemId = inPacket.decodeInt();
            getTotems().add(itemId);
        }

        setWeaponStickerId(inPacket.decodeInt());
        setWeaponId(inPacket.decodeInt());
        setSubWeaponId(inPacket.decodeInt());
        inPacket.decodeInt();
        inPacket.decodeInt();
        inPacket.decodeByte();
        setDrawElfEar(inPacket.decodeByte() != 0);
        inPacket.decodeInt();
        inPacket.decodeArr(4);

        for (int i = 0; i < 3; i++) {
            int petId = inPacket.decodeInt();
            //getPetIDs().add(petId);
        }
        inPacket.decodeArr(264);
        if (JobConstants.isDemon((short) getJob())) {
            setDemonSlayerDefFaceAcc(inPacket.decodeInt());
        } else if (JobConstants.isXenon((short) getJob())) {
            setXenonDefFaceAcc(inPacket.decodeInt());
        } else if (JobConstants.isArk((short) getJob()) || JobConstants.isHoYoung((short) getJob())) {
            inPacket.decodeInt(); // face acc?
        } else if (JobConstants.isAngelicBuster((short) getJob())) {
            inPacket.decodeByte();
        } else if (JobConstants.isZero((short) getJob())) {
            setZeroBetaLook(inPacket.decodeByte() != 0);
        } else if (JobConstants.isBeastTamer((short) getJob())) {
            setBeastTamerDefFaceAcc(inPacket.decodeInt());
            inPacket.decodeByte(); // has Ears
            setEars(inPacket.decodeInt());
            inPacket.decodeByte(); // has Tail
            setTail(inPacket.decodeInt());
        }
        inPacket.decodeInt();
        inPacket.decodeArr(13); // name
    }

    public int getHair() {
        return hair;
    }

    public void setHair(int hair) {
        if (this.hair != 0 && hairColor(hair) != hairColor(this.hair)) {
            clearHairMix(); // a plain dye replaces the blend
        }
        this.hair = hair;
    }

    public List<Integer> getTotems() {
        if (totems == null) {
            totems = new ArrayList<>();
        }
        return totems;
    }

    public int getEars() {
        return ears;
    }

    public void setEars(int ears) {
        this.ears = ears;
    }

    public int getTail() {
        return tail;
    }

    public void setTail(short tail) {
        this.tail = tail;
    }

    public void setTotems(List<Integer> totems) {
        this.totems = totems;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDemonWingID() {
        return demonWingID;
    }

    public void setDemonWingID(int demonWingID) {
        this.demonWingID = demonWingID;
    }

    public int getKaiserWingID() {
        return kaiserWingID;
    }

    public void setKaiserWingID(int kaiserWingID) {
        this.kaiserWingID = kaiserWingID;
    }

    public int getKaiserTailID() {
        return kaiserTailID;
    }

    public void setKaiserTailID(int kaiserTailID) {
        this.kaiserTailID = kaiserTailID;
    }

    public void removeItem(int itemID, int anvilID, boolean cash, int overrideId) {
        if (anvilID > 0) { // Fusion Anvil
            itemID = anvilID;
        }

        List<Integer> hairEquips = getHairEquips();
        List<Integer> unseenEquips = getUnseenEquips();
        if (ItemConstants.isWeapon(itemID) && !ItemConstants.isSecondary(itemID) && !ItemConstants.isKatara(itemID) && !ItemConstants.isShield(itemID)) {
            if (cash) {
                setWeaponStickerId(0);
                unseenEquips.remove((Integer) itemID);
            } else {
                setWeaponId(0);
                hairEquips.remove((Integer) itemID);
            }
        } else if (cash) {
            hairEquips.remove((Integer) itemID);
            if (overrideId >= 0) {
                unseenEquips.remove((Integer) overrideId);
                if (!hairEquips.contains(overrideId)) {
                    hairEquips.add(overrideId);
                }
            }
        } else {
            hairEquips.remove((Integer) itemID);
            unseenEquips.remove((Integer) itemID);
        }
    }

    public void addItem(int itemID, int anvilID, boolean isCash, int overrideItemId) {
        if (anvilID > 0) { // Fusion Anvil
            itemID = anvilID;
        }

        if (ItemConstants.isWeapon(itemID) && !ItemConstants.isSecondary(itemID) && !ItemConstants.isKatara(itemID) && !ItemConstants.isShield(itemID)) {
            if (isCash) {
                setWeaponStickerId(itemID);
                if (!unseenEquips.contains(itemID)) {
                    unseenEquips.add(itemID);
                }
            } else {
                setWeaponId(itemID);
                if (!hairEquips.contains(itemID)) {
                    hairEquips.add(itemID);
                }
            }
        } else if (isCash) {
            if (!hairEquips.contains(itemID)) {
                hairEquips.add(itemID);
            }
            if (overrideItemId >= 0) {
                hairEquips.remove((Integer) overrideItemId);
                if (!unseenEquips.contains(overrideItemId)) {
                    unseenEquips.add(overrideItemId);
                }
            }
        } else {
            if (overrideItemId >= 0 && !unseenEquips.contains(itemID)) {
                unseenEquips.add(itemID);
            } else {
                hairEquips.add(itemID);
            }
        }
    }

    public EarStyle getEarStyle() {
        return earStyle;
    }

    public void setEarStyle(EarStyle earStyle) {
        this.earStyle = earStyle;
    }

    /**
     * The 120-byte packed look v265 puts in party member entries (the reference server's layout).
     */
    public byte[] getPackedLook120() {
        int[] equipArr = new int[11];
        List<Integer> hairEquips = new ArrayList<>();
        if (getHairEquips() != null) {
            for (Integer i : getHairEquips()) {
                if (i != null && i > 0) {
                    hairEquips.add(i);
                }
            }
        }
        int n = Math.min(equipArr.length, hairEquips.size());
        for (int i = 0; i < n; i++) {
            equipArr[i] = hairEquips.get(i);
        }
        int visibleWeaponId = getWeaponStickerId() != 0 ? getWeaponStickerId() : getWeaponId();
        int weaponType = 0;
        for (WeaponType type : WeaponType.values()) {
            if (type.getVal() == visibleWeaponId / 1000 % 1000) {
                break;
            }
            if (++weaponType > 36) {
                weaponType = 0;
                break;
            }
        }
        int defFaceAcc = 0;
        if (JobConstants.isDemon(getJob()) || JobConstants.isHoYoung(getJob())) {
            defFaceAcc = getDemonSlayerDefFaceAcc();
        }
        if (JobConstants.isXenon((short) getJob())) {
            defFaceAcc = getXenonDefFaceAcc();
        }
        int gender = getGender(), skin = getSkin(), face = getFace(), hair = getHair();
        byte[] data = new byte[120];
        packAt(data, 0, gender & 1);
        packAt(data, 0, (skin & 0x3FF) << 1);
        packAt(data, 1, ((face > 0 ? face % 1000 : -1) & 0x3FF) << 3);
        packAt(data, 2, (face / 1000 % 10 & 0xF) << 5);
        packAt(data, 3, hair / 10000 == 4 ? 2 : 0);
        packAt(data, 3, ((hair > 0 ? hair % 1000 : -1) & 0x3FF) << 2);
        packAt(data, 4, (hair / 1000 % 10 & 0xF) << 4);
        packAt(data, 5, (equipArr[1] > 0 ? equipArr[1] % 1000 : -1) & 0x3FF);
        packAt(data, 6, (equipArr[1] / 1000 % 10 & 7) << 2);
        packAt(data, 6, ((defFaceAcc > 0 ? defFaceAcc % 1000 : -1) & 0x3FF) << 5);
        packAt(data, 7, (defFaceAcc / 1000 % 10 & 3) << 7);
        packAt(data, 8, ((equipArr[3] > 0 ? equipArr[3] % 1000 : -1) & 0x3FF) << 1);
        packAt(data, 9, (equipArr[3] / 1000 % 10 & 3) << 3);
        packAt(data, 9, ((equipArr[4] > 0 ? equipArr[4] % 1000 : -1) & 0x3FF) << 5);
        packAt(data, 10, (equipArr[4] / 1000 % 10 & 3) << 7);
        packAt(data, 11, equipArr[5] / 10000 == 105 ? 2 : 0);
        packAt(data, 11, ((equipArr[5] > 0 ? equipArr[5] % 1000 : -1) & 0x3FF) << 2);
        packAt(data, 12, (equipArr[5] / 1000 % 10 & 0xF) << 4);
        packAt(data, 13, (equipArr[6] > 0 ? equipArr[6] % 1000 : -1) & 0x3FF);
        packAt(data, 14, (equipArr[6] / 1000 % 10 & 3) << 2);
        packAt(data, 14, ((equipArr[7] > 0 ? equipArr[7] % 1000 : -1) & 0x3FF) << 4);
        packAt(data, 15, (equipArr[7] / 1000 % 10 & 3) << 6);
        packAt(data, 16, (equipArr[8] > 0 ? equipArr[8] % 1000 : -1) & 0x3FF);
        packAt(data, 17, (equipArr[8] / 1000 % 10 & 3) << 2);
        packAt(data, 17, ((equipArr[9] > 0 ? equipArr[9] % 1000 : -1) & 0x3FF) << 4);
        packAt(data, 18, (equipArr[9] / 1000 % 10 & 3) << 6);
        packAt(data, 19, equipArr[10] > 0 ? equipArr[10] / 10000 == 109 ? 1 : 3 - (equipArr[10] / 10000 == 134 ? 1 : 0) : 0);
        packAt(data, 19, ((equipArr[10] > 0 ? equipArr[10] % 1000 : -1) & 0x3FF) << 2);
        packAt(data, 20, (equipArr[10] / 1000 % 10 & 0xF) << 4);
        packAt(data, 21, visibleWeaponId / 10000 == 170 ? 1 : 0);
        packAt(data, 21, ((visibleWeaponId > 0 ? visibleWeaponId % 1000 : -1) & 0x3FF) << 1);
        packAt(data, 22, (visibleWeaponId / 1000 % 10 & 3) << 3);
        packAt(data, 22, weaponType << 5);
        packAt(data, 119, 29);
        return data;
    }

    private static final int[] V271_WEAPON_TYPES = {30, 31, 32, 33, 37, 38, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 39,
            34, 52, 53, 35, 36, 21, 22, 54, 254, 23, 24, 56, 57, 26, 58, 27, 28, 59, 29, 213, 214, 404, 215, 403, 25, 253,
            433};
    private static final java.util.Set<Integer> V271_THREE_DIGIT_TYPES = java.util.Set.of(21, 22, 23, 25, 26, 27, 28,
            29, 30, 31, 32, 33, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 52, 53, 54, 56, 57, 58, 59);

    /** LSB-first bit writer over the 480-byte look, the way the client's packer writes it. */
    private static final class LookBits {
        final byte[] data = new byte[480];
        int pos;

        void put(long value, int bits) {
            for (int i = 0; i < bits; i++, pos++) {
                if ((value >> i & 1) != 0 && pos / 8 < data.length - 1) {
                    data[pos / 8] |= (byte) (1 << (pos % 8));
                }
            }
        }

        /** An item: id % 1000 (1023 for none), then its thousands digit. */
        void item(int id, int digitBits) {
            put(id != 0 ? id % 1000 : 0xFFFF, 10);
            put(id / 1000 % 10, digitBits);
        }
    }

    private static int v271WeaponType(int id) {
        if ((id < 1000000 || id >= 2000000) && (id < 6000000 || id >= 7000000)) { // hardcode-ok
            return 0;
        }
        int type = id / 10000 % 100;
        if (id / 1000 % 10 > 2 && V271_THREE_DIGIT_TYPES.contains(type)) {
            type = type * 10 + id / 1000 % 10;
        }
        if (type == 72) {
            type = 35;
        }
        for (int i = 0; i < V271_WEAPON_TYPES.length; i++) {
            if (V271_WEAPON_TYPES[i] == type) {
                return i + 1;
            }
        }
        return 0;
    }

    private static int v271JobGroup(int job) {
        return switch (job) {
            case 13000, 13100 -> 1;
            case 13001, 13500 -> 2;
            case 12005, 12100 -> 3;
            case 12006, 12200 -> 4;
            default -> 0;
        };
    }

    private static boolean inHundred(int value, int from) {
        return value >= from && value < from + 100;
    }

    /**
     * The v271 480-byte packed look (version 41 in the last byte), as the client packer 0x14064c960 builds it from its
     * AvatarLook (field order and widths from there; checked against a GMS Kaling picker look). Mixed hair and face
     * colours keep their mix bits; dyes, the second face (+0x25) and the job-only fields are left empty.
     */
    public byte[] getPackedLook480() {
        int[] eq = new int[66];
        if (getHairEquips() != null) {
            for (Integer id : getHairEquips()) {
                if (id == null || id <= 0) {
                    continue;
                }
                int bp = ItemConstants.isLongOrBigSword(id) ? 11 : ItemConstants.getBodyPartFromItem(id, getGender());
                if (bp > 0 && bp < eq.length) {
                    eq[bp] = id;
                }
            }
        }
        int job = getJob();
        int sticker = getWeaponStickerId();
        boolean hasSticker = sticker >= 1700000 && sticker < 1710000; // hardcode-ok
        int weapon = eq[11] != 0 ? eq[11] : getWeaponId();
        if (!hasSticker && (job == 15001 || inHundred(job, 15500))) {
            weapon = 0x169df3;
        }
        int faceAcc = eq[2];
        if (faceAcc == 0) {
            if (JobConstants.isDemon(job)) {
                faceAcc = getDemonSlayerDefFaceAcc();
            } else if (JobConstants.isXenon((short) job)) {
                faceAcc = getXenonDefFaceAcc();
            }
        }
        int jobForm = 0;
        if (job == 16000 || inHundred(job, 16400)) {
            jobForm = 1;
        } else if (job == 16001 || inHundred(job, 16200)) {
            jobForm = 2;
        } else if (job == 16002 || inHundred(job, 16100)) {
            jobForm = getGender() != 1 ? 4 : 3;
        }
        int rawFace = getEncodedFace(), rawHair = getEncodedHair();
        boolean faceFlag = rawFace >= 1_000_000_000, hairFlag = rawHair >= 1_000_000_000;
        int faceS = faceFlag ? rawFace % 1_000_000_000 : rawFace, hairS = hairFlag ? rawHair % 1_000_000_000 : rawHair;
        boolean faceMixed = faceS > 9_999_999, hairMixed = hairS > 9_999_999;
        int face = faceMixed ? faceS / 1000 : faceS, hair = hairMixed ? hairS / 1000 : hairS;
        int faceMix = faceMixed ? rawFace % 1000 : 0, hairMix = hairMixed ? rawHair % 1000 : 0;

        LookBits w = new LookBits();
        w.put(getGender() & 1, 1);
        w.put(getSkin() & 0x3FF, 10);
        w.put(face / 10000 & 0xF, 4);
        w.put(face != 0 ? face % 1000 : 0xFFFF, 10);
        w.put(face / 1000 % 10 & 0xF, 4);
        w.put(hair / 10000 & 0xF, 4);
        w.put(hair != 0 ? hair % 1000 : 0xFFFF, 10);
        w.put(hair / 1000 % 10 & 0xF, 4);
        w.item(eq[1], 3);
        w.item(faceAcc, 2);
        w.item(eq[3], 2);
        w.item(eq[4], 2);
        w.put(eq[5] >= 1050000 && eq[5] < 1060000 ? 1 : 0, 1); // hardcode-ok
        w.item(eq[5], 4);
        w.item(eq[6], 2);
        w.item(eq[7], 4);
        w.item(eq[8], 2);
        w.item(eq[9], 4);
        int shieldType = switch (eq[10] / 10000) {
            case 109 -> 1;
            case 134 -> 2;
            case 135 -> 3;
            case 172 -> 4;
            default -> 0;
        };
        w.put(shieldType, 3);
        if (shieldType != 0) {
            w.item(eq[10], 4);
        }
        w.put(0, 1); // body part 62 (+0x131): not in the client set
        w.put(hasSticker ? 1 : 0, 1);
        if (hasSticker) {
            w.item(sticker, 2);
        }
        w.item(weapon, 2);
        w.put(v271WeaponType(weapon) & 0xFF, 8);
        w.put(getEarStyle().ordinal() & 0xF, 4);
        w.put(hairMix / 100 & 0xF, 4);
        w.put(hairMix % 100 & 0xFF, 8);
        w.put(faceMix & 0xFFF, 12);
        w.put(hairFlag ? 1 : 0, 1);
        w.put(faceFlag ? 1 : 0, 1);
        w.put(jobForm & 0xFF, 8);
        w.put(isDrawElfEar() ? 1 : 0, 8);
        w.put(v271JobGroup(job) & 0x3F, 6);
        w.put(0, 2);
        w.put(0, 18); // +0x49a
        w.put(0, 8);
        w.put(0, 4); // +0x473
        w.put(0, 9); // +0x46d
        for (int i = 0; i < 7; i++) {
            w.put(1, 1); // part-visibility flags, all shown
        }
        w.item(0, 2); // +0x487
        w.put(0, 11); // no dyes on equips 1-11
        w.put(0, 1); // +0x25
        for (int bp : new int[]{12, 13, 15, 16}) {
            w.put(eq[bp] != 0 ? eq[bp] % 1000 : 0xFFFF, 10);
            w.put(eq[bp] / 1000 % 10 & 0xF, 4);
        }
        byte[] name = getName() == null ? new byte[0]
                : getName().getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        int len = Math.min(name.length, 13);
        w.put(len, 16);
        for (int i = 0; i < len; i++) {
            w.put(name[i] & 0xFF, 8);
        }
        w.data[479] = 0x29;
        return w.data;
    }

    private static void packAt(byte[] arr, int index, int value) {
        arr[index] |= (byte) (value & 0xFF);
        if (value > 0xFF && index < arr.length - 1) {
            arr[index + 1] |= (byte) (value >>> 8 & 0xFF);
        }
    }
}

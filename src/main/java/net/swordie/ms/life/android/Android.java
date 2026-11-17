package net.swordie.ms.life.android;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Inventory;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.AndroidPacket;
import net.swordie.ms.enums.EnterType;
import net.swordie.ms.life.Life;
import net.swordie.ms.loaders.containerclasses.AndroidInfo;
import net.swordie.ms.util.ErrorLogger;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 4/14/2018.
 */
public class Android extends Life implements Encodable {

    private static final int ANDROID_ITEM_SIZE = 7;
    /**
     * Quest record that remembers each android's look per character (key = android item id, value = skin_hair_face),
     * so the look stays the same between logins and can be changed by the Android Beautician.
     */
    public static final int ANDROID_LOOK_QR = 500780;

    private Char ownerChar;
    private short skin;
    private int hair;
    private int face;
    private String name;
    private int androidItemId;
    private AndroidInfo androidInfo;

    public Android(int templateId) {
        super(templateId);
    }

    public Android(int templateId, Char ownerChar, short skin, short hair, short face, String name) {
        super(templateId);
        this.ownerChar = ownerChar;
        this.skin = skin;
        this.hair = hair;
        this.face = face;
        this.name = name;
    }

    public Android(Char chr, AndroidInfo androidInfo) {
        this(chr, androidInfo, 0);
    }

    /**
     * Creates the Android for the given android item. Its look is the one saved for this item on the character; the
     * first time, a random look from the android's own options (Etc/Android) is picked and saved.
     * Hair and face are full item ids (like the character's own look); skin is the skin number (2000 -> 0).
     */
    public Android(Char chr, AndroidInfo androidInfo, int androidItemId) {
        super(androidInfo.getId());
        this.ownerChar = chr;
        this.androidInfo = androidInfo;
        this.androidItemId = androidItemId;
        this.name = "Android";
        if (!loadSavedLook()) {
            Integer skin = Util.getRandomFromCollection(androidInfo.getSkins());
            Integer hair = Util.getRandomFromCollection(androidInfo.getHairs());
            Integer face = Util.getRandomFromCollection(androidInfo.getFaces());
            this.skin = (short) (skin == null ? 0 : skin % 1000);
            this.hair = hair == null ? 0 : hair;
            this.face = face == null ? 0 : face;
            saveLook();
        }
        Quest names = chr == null || chr.getQuestManager() == null ? null
                : chr.getQuestManager().getQuestById(ANDROID_LOOK_QR);
        String saved = names == null ? null : names.getProperty("n" + getLookKey());
        if (saved != null && !saved.isEmpty()) {
            this.name = saved;
        }
    }

    /** Android Naming Coupon: renames it, saves the name with its look and shows it. */
    public void rename(String newName) {
        setName(newName);
        if (ownerChar != null && ownerChar.getQuestManager() != null) {
            QuestManager qm = ownerChar.getQuestManager();
            Quest quest = qm.getQuestById(ANDROID_LOOK_QR);
            if (quest == null) {
                quest = QuestData.createQuestFromId(ANDROID_LOOK_QR);
                qm.getQuests().put(ANDROID_LOOK_QR, quest);
            }
            quest.setProperty("n" + getLookKey(), newName);
        }
        if (getField() != null) {
            getField().broadcastPacket(AndroidPacket.modifiedLook(this));
        } else if (ownerChar != null) {
            ownerChar.write(AndroidPacket.modifiedLook(this));
        }
    }

    private String getLookKey() {
        return String.valueOf(androidItemId > 0 ? androidItemId : getTemplateId());
    }

    private boolean loadSavedLook() {
        if (ownerChar == null || ownerChar.getQuestManager() == null) {
            return false;
        }
        Quest quest = ownerChar.getQuestManager().getQuestById(ANDROID_LOOK_QR);
        String value = quest == null ? null : quest.getProperty(getLookKey());
        if (value == null) {
            return false;
        }
        String[] parts = value.split("_");
        if (parts.length != 3) {
            return false;
        }
        try {
            this.skin = Short.parseShort(parts[0]);
            this.hair = Integer.parseInt(parts[1]);
            this.face = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        // looks saved before hair/face were full ids (0-999) aren't valid any more
        return this.hair >= 10000 && this.face >= 10000;
    }

    /**
     * Saves this Android's look on its owner (quest record {@link #ANDROID_LOOK_QR}). Doesn't send anything to the
     * client, so it's safe to call while logging in.
     */
    public void saveLook() {
        if (ownerChar == null || ownerChar.getQuestManager() == null) {
            return;
        }
        QuestManager qm = ownerChar.getQuestManager();
        Quest quest = qm.getQuestById(ANDROID_LOOK_QR);
        if (quest == null) {
            quest = QuestData.createQuestFromId(ANDROID_LOOK_QR);
            qm.getQuests().put(ANDROID_LOOK_QR, quest);
        }
        quest.setProperty(getLookKey(), getSkin() + "_" + getHair() + "_" + getFace());
    }

    /**
     * Changes the Android's skin (0-99), hair or face (full ids), saves it and shows it to everyone in the map.
     */
    public void changeLook(int look) {
        if (look < 100) {
            setSkin((short) look);
        } else if (ItemConstants.isFace(look)) {
            setFace(look);
        } else {
            setHair(look);
        }
        saveLook();
        if (getField() != null) {
            getField().broadcastPacket(AndroidPacket.modifiedLook(this));
        } else if (ownerChar != null) {
            ownerChar.write(AndroidPacket.modifiedLook(this));
        }
    }

    public AndroidInfo getAndroidInfo() {
        return androidInfo;
    }

    public int getAndroidItemId() {
        return androidItemId;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getTemplateId()); // v265: int
        outPacket.encodePosition(getPosition());
        outPacket.encodeByte(getMoveAction());
        outPacket.encodeShort(getFh());
        outPacket.encodeInt(0); // new 218
        encodeAndroidInfo(outPacket);

        // 14 equip records of int id, two ints (v265 had one), byte (same record as ANDROID_MODIFIED)
        List<Integer> androidItems = getItems();
        for (int i = 0; i < ANDROID_EQUIP_RECORDS; i++) {
            outPacket.encodeInt(i < androidItems.size() ? androidItems.get(i) : 0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
        }
        outPacket.encodeInt(0); // v271: one int after the records
    }

    private static final int ANDROID_EQUIP_RECORDS = 14;

    public void encodeAndroidInfo(OutPacket outPacket) {
        outPacket.encodeShort(getSkin());
        // v265 takes the full ids (client 0x140640890 keeps them as sent); hairs 40000+ can't be sent as offsets
        outPacket.encodeInt(getHair());
        outPacket.encodeInt(getFace());
        outPacket.encodeString(getName());
        outPacket.encodeInt(0);
        outPacket.encodeFT(FileTime.MAX_TIME());
        outPacket.encodeInt(0); // v265 (reference tree)
    }

    public static void encodeDefaultAndroidInfo(OutPacket outPacket) {
        outPacket.encodeShort(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeString("Android");
        outPacket.encodeInt(0);
        outPacket.encodeFT(FileTime.MAX_TIME());
        outPacket.encodeInt(0); // v265 (reference tree)
    }

    public Char getOwnerChar() {
        return ownerChar;
    }

    public void setOwnerChar(Char ownerChar) {
        this.ownerChar = ownerChar;
    }

    public short getSkin() {
        return skin;
    }

    public void setSkin(short skin) {
        this.skin = skin;
    }

    public int getHair() {
        return hair;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChrId() {
        return ownerChar.getId();
    }

    public List<Integer> getItems() {
        List<Integer> items = new ArrayList<>();
        if (getOwnerChar() == null || getOwnerChar().getEquippedInventory() == null) {
            if (getOwnerChar() == null) {
                ErrorLogger.logAsClientError("OwnerChar is null in Android::getItems.");
            } else {
                ErrorLogger.logAsClientError(String.format("Would get an NPE in Android::getItems. Ownerchar = %s, channel = %d, fieldId = %d", getOwnerChar(), getOwnerChar().getChannel(), getOwnerChar().getFieldID()));
            }
            return items;
        }


        Inventory inv = getOwnerChar().getEquippedInventory();
        for (int i = BodyPart.APBase.getVal(); i < BodyPart.APEnd.getVal(); i++) {
            Item item = inv.getItemBySlot(i);
            items.add(item == null ? 0 : item.getItemId());
        }
        return items;
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar, EnterType enterType) {
        OutPacket outPacket = AndroidPacket.created(this);
        if (onlyChar == null) {
            getField().broadcastPacket(outPacket);
        } else {
            onlyChar.write(outPacket);
        }
    }

    @Override
    public void broadcastLeavePacket() {
        getField().broadcastPacket(AndroidPacket.removed(this));
    }

    public boolean hasObjectId() {
        return getObjectId() != -1;
    }
}

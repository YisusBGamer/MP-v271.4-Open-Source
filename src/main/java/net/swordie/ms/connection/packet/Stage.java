package net.swordie.ms.connection.packet;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharEncoder;
import net.swordie.ms.client.character.damage.DamageCalc;
import net.swordie.ms.client.character.modules.CustomTitleModule;
import net.swordie.ms.client.jobs.jianghu.Lynn;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.DBChar;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.FieldCustom;
import net.swordie.ms.world.shop.cashshop.CashShop;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created on 12/14/2017.
 */
public class Stage {

    public static OutPacket setField(Char chr, Field field, int channelId, boolean dev, int oldDriverID,
                                     boolean characterData, boolean usingBuffProtector, byte portal,
                                     boolean setWhiteFadeInOut, int mobStatAdjustRate, FieldCustom fieldCustom,
                                     boolean canNotifyAnnouncedQuest, int stackEventGauge) {
        // v265 layout from the reference tree (Stage.setField there)
        OutPacket outPacket = new OutPacket(OutHeader.SET_FIELD);

        outPacket.encodeFT(FileTime.currentTime());
        outPacket.encodeByte(false);
        outPacket.encodeInt(channelId - 1);
        outPacket.encodeByte(dev);
        outPacket.encodeInt(oldDriverID);

        outPacket.encodeByte(characterData ? 1 : 2);
        outPacket.encodeInt(0); // field type when not characterData; unused
        outPacket.encodeInt(field.getInfo().getWidth());
        outPacket.encodeInt(Math.abs(field.getInfo().getHeight()));
        outPacket.encodeByte(characterData);
        // v271: CharacterData decoder selector (0 = the full decoder 0x1407c9950; else 0x1407c7880, another grammar)
        outPacket.encodeByte(0);
        short notifierCheck = 0;
        outPacket.encodeShort(notifierCheck);
        if (notifierCheck > 0) {
            outPacket.encodeString(""); // pBlockReasonIter
            for (int i = 0; i < notifierCheck; i++) {
                outPacket.encodeString(""); // sMsg2
            }
        }

        if (characterData) {
            Random random = new SecureRandom();
            int s1 = random.nextInt();
            int s2 = random.nextInt();
            int s3 = random.nextInt();
            chr.setDamageCalc(new DamageCalc(chr, s1, s2, s3));
            outPacket.encodeInt(s1);
            outPacket.encodeInt(s2);
            outPacket.encodeInt(s3);


            // v271: only the sections whose v271 grammar is known (CharEncoder.encodeV271Minimal); the v265 full
            // encoder doesn't match the v271 client (item quantities, pets, equips, new F88, ...)
            CharEncoder.encodeV271Minimal(chr, outPacket);

            // logout event
            outPacket.encodeByte(true);
            outPacket.encodeByte(false);
            outPacket.encodeFT(FileTime.MIN_TIME());
            outPacket.encodeByte(0);
            outPacket.encodeFT(FileTime.MIN_TIME());
            outPacket.encodeFT(FileTime.MIN_TIME());
            outPacket.encodeInt(-1);
        } else {
            outPacket.encodeByte(usingBuffProtector);
            outPacket.encodeInt(field.getId());
            outPacket.encodeByte(portal);
            outPacket.encodeInt(chr.getAvatarData().getCharacterStat().getHp());
            outPacket.encodeInt(0);
            outPacket.encodeByte(false); // has position (int x, int y)
        }

        var bool = false;
        outPacket.encodeByte(bool);
        if (bool) {
            // some kind of portal/positional stuff
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }

        // the reference tree always sends 2, 0 here (v232: white fade-in, slow fade-in); setWhiteFadeInOut is unmapped
        outPacket.encodeByte(2);
        outPacket.encodeByte(false);

        outPacket.encodeInt(mobStatAdjustRate);
        boolean hasFieldCustom = fieldCustom != null;
        outPacket.encodeByte(hasFieldCustom);
        if (hasFieldCustom) {
            fieldCustom.encode(outPacket);
        }

        outPacket.encodeByte(false); // is pvp map, deprecated
        outPacket.encodeInt(0);
        outPacket.encodeByte(canNotifyAnnouncedQuest);

        encodeExtraSystemBuffer(chr, outPacket);

        outPacket.encodeByte(JobConstants.isExtendSpJob(chr.getJob()));
        outPacket.encodeLong(0);
        outPacket.encodeInt(-1);

        stackEventGauge = 0;
        outPacket.encodeByte(stackEventGauge > 0);
        if (stackEventGauge > 0) {
            outPacket.encodeInt(stackEventGauge);
        }

        // start new 199
        if (FieldConstants.isVonbonField(chr.getFieldID())) {
            int size = 0;
            outPacket.encodeByte(size);
            for (int i = 0; i < size; i++) {
                outPacket.encodeString(""); // portals?
            }
        }
        // end new 199

        // CUser::DecodeTextEquipInfo
        int size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeInt(0);
            outPacket.encodeString("");
        }
        // FreezeAndHotEventInfo::Decode
        outPacket.encodeByte(0); // nAccountType
        outPacket.encodeInt(0); // nAccountID
        // CUser::DecodeEventBestFriendInfo
        outPacket.encodeInt(0); // dwEventBestFriendAID

        outPacket.encodeByte(true);
        outPacket.encodeInt(-1);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(GameConstants.NO_MAP_ID);
        outPacket.encodeInt(GameConstants.NO_MAP_ID);
        outPacket.encodeString("");

        outPacket.encodeInt(CharEncoder.activeSkinId(chr.getActiveDamageSkin()));
        outPacket.encodeString("");
        outPacket.encodeString("");

        outPacket.encodeByte(false); // popup to open on login (Sunny Sunday in the reference tree)

        outPacket.encodeInt(0); // nUiOnLogin?
        outPacket.encodeInt(0);
        outPacket.encodeByte(false); // GMS: 0 (1 only on a few boss maps)
        outPacket.encodeInt(0); // v271 reads 4 4 1 4 here, then the int list

        outPacket.encodeInt(0); // int list

        CustomTitleModule.encodeCustomTitle(chr, outPacket, FileTime.currentTime()); // GMS: the current time

        return outPacket;
    }

    private static final int EXTRA_SYSTEM_KEY = 1 + new SecureRandom().nextInt(4095);

    /**
     * A length-prefixed actor object stream for the user's channel (see {@link ActorPacket}): the AUserController
     * from level 200 as the reference tree sends it, and Lynn's ALynnController.
     */
    private static void encodeExtraSystemBuffer(Char chr, OutPacket outPacket) {
        OutPacket buffer = new OutPacket();
        if (!net.swordie.ms.ServerSettings.getBoolean("test.fieldActorStream", false)) {
            // v271 test: an empty stream (only the end mark); the actor classes' v271 layouts are unchecked
            buffer.encodeInt(0);
            byte[] data = buffer.getData();
            outPacket.encodeInt(data.length);
            outPacket.encodeArr(data);
            return;
        }
        if (chr.getLevel() >= 200) {
            ActorPacket.beginObject(buffer, EXTRA_SYSTEM_KEY, ActorPacket.USER_CONTROLLER_HASH);
            ActorPacket.encodeActorBase(buffer, chr.getUserId(), chr.getId());
            ActorPacket.endObject(buffer);
        }
        Lynn.encodeControllerFor(chr, buffer);
        buffer.encodeInt(0);
        byte[] data = buffer.getData();
        outPacket.encodeInt(data.length);
        outPacket.encodeArr(data);
    }

    public static OutPacket setAuctionField(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_AUCTION_HOUSE_FIELD);

        // v271 (0x144120fe0): B8 time, CharacterData (0x1407c9950 mode 0, as SET_FIELD), nothing else
        outPacket.encodeFT(FileTime.fromDate(LocalDateTime.now()));
        CharEncoder.encodeV271Minimal(chr, outPacket);

        return outPacket;
    }

    private static void encodeLogoutEvent(OutPacket outPacket) {
        int idOrSomething = 0;
        outPacket.encodeInt(idOrSomething);
        if (idOrSomething > 0) {
            for (int i = 0; i < 3; i++) {
                // sub_9896B0
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeString("");
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeLong(0);
                outPacket.encodeLong(0);
                outPacket.encodeLong(0);
                outPacket.encodeLong(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeString("");
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeByte(0);
                // if(a3 & 1 != 0) -> encode int + str + buf of size 0x18 (24). a3 is 0 when called from setField
                int size = 0;
                outPacket.encodeInt(size);
                for (int j = 0; j < size; j++) {
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                }
            }
        }
    }

    public static OutPacket setCashShop(Char chr, CashShop cashShop) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_CASH_SHOP.getValue());

        // v271 (handler 0x144120c50): B8 time, CharacterData (0x1407c9950 mode 0, as SET_FIELD), CCashShop body
        outPacket.encodeFT(FileTime.currentTime());
        CharEncoder.encodeV271Minimal(chr, outPacket);
        cashShop.encodeV271(outPacket);

        return outPacket;
    }
}

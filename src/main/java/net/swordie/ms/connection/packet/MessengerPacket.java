package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Maple Messenger (UI_MESSENGER, client 0x1442a0c90): 1 type + body; rooms have 6 slots.
 */
public class MessengerPacket {

    public static final int ENTER = 0;
    public static final int SELF_ENTER = 1;
    public static final int LEAVE = 2;
    public static final int INVITE = 3;
    public static final int INVITE_RESULT = 4;
    public static final int DECLINE = 5;
    public static final int CHAT = 6;
    public static final int CHAT_2 = 7;

    // 1 slot, 4, avatar look, S name, 1 channel, 1, 4
    public static OutPacket enter(int slot, Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.UI_MESSENGER);

        outPacket.encodeByte(ENTER);
        outPacket.encodeByte(slot);
        outPacket.encodeInt(chr.getId());
        chr.getAvatarData().getAvatarLook().encode(outPacket);
        outPacket.encodeString(chr.getName());
        outPacket.encodeByte(Math.max(0, chr.getClient().getChannel() - 1));
        outPacket.encodeByte(0);
        outPacket.encodeInt(chr.getJob());
        outPacket.encodeLong(0); // v271 (0x1448b5a00 case 0)

        return outPacket;
    }

    // slot < 0: "You have been unable to join the invited chat room."
    public static OutPacket selfEnter(int slot) {
        OutPacket outPacket = new OutPacket(OutHeader.UI_MESSENGER);

        outPacket.encodeByte(SELF_ENTER);
        outPacket.encodeByte(slot);

        return outPacket;
    }

    public static OutPacket leave(int slot) {
        OutPacket outPacket = new OutPacket(OutHeader.UI_MESSENGER);

        outPacket.encodeByte(LEAVE);
        outPacket.encodeByte(slot);

        return outPacket;
    }

    // 4 inviter id, S inviter, 4, 1 channel, 4 room id, 1 (0 lets the client decline by itself)
    public static OutPacket invite(Char inviter, int roomId) {
        OutPacket outPacket = new OutPacket(OutHeader.UI_MESSENGER);

        outPacket.encodeByte(INVITE);
        outPacket.encodeInt(inviter.getId());
        outPacket.encodeString(inviter.getName());
        outPacket.encodeInt(0);
        outPacket.encodeByte(Math.max(0, inviter.getClient().getChannel() - 1));
        outPacket.encodeInt(roomId);
        outPacket.encodeByte(1);

        return outPacket;
    }

    // 1 sent, 2 not found
    public static OutPacket inviteResult(String name, boolean sent) {
        OutPacket outPacket = new OutPacket(OutHeader.UI_MESSENGER);

        outPacket.encodeByte(INVITE_RESULT);
        outPacket.encodeString(name);
        outPacket.encodeByte(sent ? 1 : 2);

        return outPacket;
    }

    // 0 "denied the request", else "not accepting chat"
    public static OutPacket declined(String name, boolean blocked) {
        OutPacket outPacket = new OutPacket(OutHeader.UI_MESSENGER);

        outPacket.encodeByte(DECLINE);
        outPacket.encodeString(name);
        outPacket.encodeByte(blocked ? 1 : 0);

        return outPacket;
    }

    // 6 and 7 (one reader, 0x1448b68c0): S, S, chat info, 4, then a set of ints (1 n, n x 4)
    public static OutPacket chat(int type, String name, String text, int value, Char speaker) {
        OutPacket outPacket = new OutPacket(OutHeader.UI_MESSENGER);

        outPacket.encodeByte(type);
        outPacket.encodeString(name);
        outPacket.encodeString(text);
        speaker.encodeChatInfo(outPacket, text);
        outPacket.encodeInt(value);
        outPacket.encodeByte(0);

        return outPacket;
    }
}

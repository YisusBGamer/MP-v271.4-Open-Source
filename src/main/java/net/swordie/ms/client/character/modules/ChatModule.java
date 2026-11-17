package net.swordie.ms.client.character.modules;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.loaders.StringData;

public class ChatModule {

    public enum LinkedMessageType {
        Nothing,
        Item,
        Achievement
    }

    public static void encodeLinkedMessage(OutPacket outPacket, Item item, LinkedMessageType lmt) {
        outPacket.encodeInt(lmt.ordinal());
        if (lmt == LinkedMessageType.Item) {
            // v265: byte has; with an item: typed item, two strings; without: nothing more
            outPacket.encodeByte(item != null);
            if (item != null) {
                outPacket.encode(item);
                String itemName = StringData.getItemStringById(item.getItemId());
                outPacket.encodeString(itemName == null ? "" : itemName);
                outPacket.encodeString("");
            }
        } else if (lmt == LinkedMessageType.Achievement) {
            outPacket.encodeInt(1);
            outPacket.encodeInt(2);
            outPacket.encodeInt(3);
            outPacket.encodeLong(4);
        }
    }

}

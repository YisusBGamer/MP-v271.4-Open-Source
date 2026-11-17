package net.swordie.ms.handlers.item;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ExceptionalEnchantModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

public class ExceptionalEnchantHandler {

    // Enchant window, Exceptional tab: int type (0 enhance, 1 extract), int tick, int, int equip pos,
    // enhance only: int extra hammers, int n, n x (int, int)
    @Handler(op = InHeader.USER_EXCEPTIONAL_ENCHANT_REQUEST)
    public static void handleExceptionalEnchantRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr == null) {
            return;
        }
        int type = inPacket.decodeInt();
        inPacket.decodeInt(); // tick
        inPacket.decodeInt();
        int equipPos = inPacket.decodeInt();
        if (type == 0) {
            int extraHammers = inPacket.decodeInt();
            ExceptionalEnchantModule.enhance(chr, equipPos, extraHammers);
        } else if (type == 1) {
            ExceptionalEnchantModule.extract(chr, equipPos);
        } else {
            chr.write(ExceptionalEnchantModule.result(ExceptionalEnchantModule.ERR_CANNOT, type, false));
            chr.dispose();
        }
    }

    // a hammer or Exceptional Part dragged onto an equip: int tick, byte inv, short pos, byte inv, short pos, byte
    @Handler(op = InHeader.USER_EXCEPTIONAL_ITEM_USE_REQUEST)
    public static void handleExceptionalItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr == null) {
            return;
        }
        inPacket.decodeInt(); // tick
        byte srcInv = inPacket.decodeByte();
        short srcPos = inPacket.decodeShort();
        inPacket.decodeByte(); // equip inventory
        short equipPos = inPacket.decodeShort();
        if (inPacket.getUnreadAmount() > 0) {
            inPacket.decodeByte(); // flag
        }
        ExceptionalEnchantModule.useOnEquip(chr, srcInv, srcPos, equipPos);
    }
}

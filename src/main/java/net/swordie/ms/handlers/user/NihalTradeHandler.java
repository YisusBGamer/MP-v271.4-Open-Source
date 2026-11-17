package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.NihalTradeModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

/** The Nihal Desert trade window (NihalTradeModule). */
public class NihalTradeHandler {

    @Handler(op = InHeader.TRADE_KING_SHOP_REQ)
    public static void handleTradeKingShopReq(Char chr, InPacket inPacket) {
        boolean buy = inPacket.decodeByte() != 0;
        int index = inPacket.decodeInt();
        int count = inPacket.decodeInt();
        NihalTradeModule.handleRequest(chr, buy, index, count);
    }

    @Handler(op = InHeader.TRADE_KING_SHOP_INFO_REQ)
    public static void handleTradeKingShopInfoReq(Char chr, InPacket inPacket) {
        NihalTradeModule.handleInfoRequest(chr);
    }
}

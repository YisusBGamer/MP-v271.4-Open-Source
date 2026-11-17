package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.union.ChampionModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

public class ChampionHandler {

    @Handler(op = InHeader.UNION_CHAMPION_REQUEST)
    public static void handleUnionChampionRequest(Char chr, InPacket inPacket) {
        ChampionModule.handleRequest(chr, inPacket);
    }

    @Handler(op = InHeader.UNION_CHAMPION_RAID_REQUEST)
    public static void handleUnionChampionRaidRequest(Char chr, InPacket inPacket) {
        net.swordie.ms.client.character.union.ChampionRaid.handleRequest(chr, inPacket);
    }
}

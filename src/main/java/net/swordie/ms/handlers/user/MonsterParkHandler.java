package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.MonsterPark;
import net.swordie.ms.client.character.MonsterParkExtreme;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.ContentPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

public class MonsterParkHandler {

    /**
     * Enter button of the Monster Park window: int (1 from this sender), int gate, int index (client 0x142195d90).
     * Answered with MONSTER_PARK_RESULT kind 2 (1 = entered; anything else shows the message).
     */
    @Handler(op = InHeader.MONSTER_PARK_UI_REQUEST)
    public static void handleMonsterParkUiRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt();
        int gate = inPacket.decodeInt();
        int index = inPacket.decodeInt();
        if (chr.getFieldID() != MonsterPark.HUB || chr.getInstance() != null) {
            chr.write(ContentPacket.monsterParkResult(0, "You can only enter a dungeon from Monster Park."));
            return;
        }
        MonsterPark.Dungeon d = MonsterPark.getDungeon(gate, index);
        String block = MonsterPark.getEntryBlock(chr, d);
        if (block != null) {
            chr.write(ContentPacket.monsterParkResult(0, block));
            return;
        }
        chr.write(ContentPacket.monsterParkResult(1, ""));
        MonsterPark.enter(chr, d);
    }

    /** Monster Park Extreme altar held (client 0x142488310): int altar value from MONSTER_PARK_EXTREME_ALTAR. */
    @Handler(op = InHeader.MONSTER_PARK_EXTREME_ALTAR_REQUEST)
    public static void handleMonsterParkExtremeAltarRequest(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 4) {
            return;
        }
        MonsterParkExtreme.onAltar(chr, inPacket.decodeInt());
    }
}

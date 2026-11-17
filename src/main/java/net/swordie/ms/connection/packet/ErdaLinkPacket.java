package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.erdalink.ErdaLinkModule;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Erda Link packets, from the client's readers: ERDA_LINK_INFO (0x145413570, the same state decoder 0x1406525d0 as
 * the CharacterData section) and ERDA_LINK_RESULT (0x145413620 / 0x142118780).
 */
public class ErdaLinkPacket {

    public static final int ACTIVATE = 0;
    public static final int ENHANCE = 1;
    /** clears the client's int set (second list of the state) */
    public static final int CLEAR_SET = 2;

    // result codes the client tells apart; any other code shows "Failed to activate/enhance the stone. Code: %d"
    public static final int OK = 0;
    public static final int SHINE_FAIL = 201;
    public static final int SHINE_DOWNGRADE = 202;

    /** The whole Erda Link state; the client refreshes the open window from it */
    public static OutPacket info(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.ERDA_LINK_INFO);
        ErdaLinkModule.encode(chr, outPacket);
        return outPacket;
    }

    /** Activation result: type 0, code, stone, category */
    public static OutPacket activateResult(int code, int stoneId, int category) {
        OutPacket outPacket = new OutPacket(OutHeader.ERDA_LINK_RESULT);
        outPacket.encodeInt(ACTIVATE);
        outPacket.encodeInt(code);
        outPacket.encodeInt(stoneId);
        outPacket.encodeInt(category);
        return outPacket;
    }

    /** Enhancement result: type 1, code, stone, category, level before, level after */
    public static OutPacket enhanceResult(int code, int stoneId, int category, int before, int after) {
        OutPacket outPacket = new OutPacket(OutHeader.ERDA_LINK_RESULT);
        outPacket.encodeInt(ENHANCE);
        outPacket.encodeInt(code);
        outPacket.encodeInt(stoneId);
        outPacket.encodeInt(category);
        outPacket.encodeInt(before);
        outPacket.encodeInt(after);
        return outPacket;
    }
}

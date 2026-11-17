package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.hexa.HexaModule;
import net.swordie.ms.client.character.union.UnionArtifactModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

/** HEXA Matrix (6th job) and Union Artifact requests. */
public class HexaHandler {

    @Handler(op = InHeader.HEXA_MATRIX_OPERATION)
    public static void handleHexaMatrixOperation(Char chr, InPacket inPacket) {
        HexaModule.handleOperation(chr, inPacket);
    }

    @Handler(op = InHeader.HEXA_ERDA_CONVERSION)
    public static void handleHexaErdaConversion(Char chr, InPacket inPacket) {
        HexaModule.handleErdaConversion(chr, inPacket);
    }

    /**
     * Using several of one item from the client's "how many?" box (Sol Erda energy).
     * Contributed by Dapsy (from the Discord).
     */
    @Handler(op = InHeader.ITEM_USE_COUNT_REQUEST)
    public static void handleItemUseCount(Char chr, InPacket inPacket) {
        int itemId = inPacket.decodeInt();
        int count = inPacket.decodeInt();
        HexaModule.useEnergyItems(chr, itemId, count);
        chr.dispose(); // the client waits for an answer: without one it ignores every later use
    }

    @Handler(op = InHeader.UNION_ARTIFACT_UPDATE_REQUEST)
    public static void handleUnionArtifactUpdateRequest(Char chr, InPacket inPacket) {
        UnionArtifactModule.handleUpdateRequest(chr, inPacket);
    }
}

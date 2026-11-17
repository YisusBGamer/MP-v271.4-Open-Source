package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.jobs.common.HexaSkillHandler;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

/** HEXA skill mode switches: Sol Janus Dusk/Dawn, Sol Hecate: Pactum and Phantom's Fate Shuffle. */
public class HexaSkillRequestHandler {

    @Handler(op = InHeader.HEXA_SOL_JANUS_REQUEST)
    public static void handleSolJanusRequest(Char chr, InPacket inPacket) {
        HexaSkillHandler.handleSolJanusRequest(chr, inPacket);
    }

    @Handler(op = InHeader.HEXA_SOL_HECATE_PACTUM_REQUEST)
    public static void handleSolHecatePactumRequest(Char chr, InPacket inPacket) {
        HexaSkillHandler.handleSolHecatePactumRequest(chr, inPacket);
    }

    @Handler(op = InHeader.HEXA_FATE_SHUFFLE_REQUEST)
    public static void handleFateShuffleRequest(Char chr, InPacket inPacket) {
        HexaSkillHandler.handleFateShuffleRequest(chr, inPacket);
    }
}

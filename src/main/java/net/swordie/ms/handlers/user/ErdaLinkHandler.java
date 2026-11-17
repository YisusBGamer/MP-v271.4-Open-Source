package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.erdalink.ErdaLinkModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

/** Erda Link (Sia Astelle's 6th job) requests. */
public class ErdaLinkHandler {

    @Handler(op = InHeader.ERDA_LINK_REQUEST)
    public static void handleErdaLinkRequest(Char chr, InPacket inPacket) {
        ErdaLinkModule.handleRequest(chr, inPacket);
    }
}

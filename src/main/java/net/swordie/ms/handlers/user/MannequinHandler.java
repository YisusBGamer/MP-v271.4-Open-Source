package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.MannequinModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

public class MannequinHandler {

    @Handler(op = InHeader.USER_MANNEQUIN)
    public static void handleUserMannequin(Char chr, InPacket inPacket) {
        MannequinModule.handle(chr, inPacket);
    }
}

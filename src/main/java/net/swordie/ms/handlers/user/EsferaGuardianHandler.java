package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.arcane.EsferaGuardianInstanceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Esfera Guardian's turret (GMS v271.4): int type (1 = fire), int character id, int (5, or 4 for the last
 * shot of a gauge), byte 1. The shell's hit comes after it as a normal attack with skill 95001015.
 */
public class EsferaGuardianHandler {
    private static final Logger log = LogManager.getLogger(EsferaGuardianHandler.class);

    @Handler(op = InHeader.ESFERA_GUARDIAN_REQUEST)
    public static void handleEsferaGuardianRequest(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 8) {
            return;
        }
        int type = inPacket.decodeInt();
        int chrId = inPacket.decodeInt();
        var inst = chr.getInstance();
        if (chrId != chr.getId() || inst == null
                || !(inst.getInstanceHelper() instanceof EsferaGuardianInstanceHelper eg)) {
            return;
        }
        if (type == 1) {
            eg.fire(chr);
        } else {
            log.debug(String.format("[EsferaGuardian] %s request type %d", chr.getName(), type));
        }
    }
}

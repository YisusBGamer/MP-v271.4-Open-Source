package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.world.field.instance.instancehelper.arcane.MidnightChaserInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.arcane.RanheimDefenseInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.arcane.SpiritSaviorInstanceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Arcane River weekly minigames' own requests (v265 client, re-next): Midnight Chaser's search (1716) and
 * Spirit Savior's mouse-up request (1630), both empty, and Spirit Savior's poll every second (1627: int, int).
 */
public class ArcaneDailyHandler {
    private static final Logger log = LogManager.getLogger(ArcaneDailyHandler.class);
    /** how near a freed spirit has to be for the mouse-up request to take it along */
    private static final int RESCUE_RANGE = 150;

    @Handler(op = InHeader.MIDNIGHT_CHASER_REQUEST)
    public static void handleMidnightChaserRequest(Char chr, InPacket inPacket) {
        var inst = chr.getInstance();
        if (inst != null && inst.getInstanceHelper() instanceof MidnightChaserInstanceHelper mc) {
            mc.searchHere(chr);
        }
        chr.dispose();
    }

    @Handler(op = InHeader.SPIRIT_SAVIOR_REQUEST)
    public static void handleSpiritSaviorRequest(Char chr, InPacket inPacket) {
        var inst = chr.getInstance();
        if (inst != null && inst.getInstanceHelper() instanceof SpiritSaviorInstanceHelper ss && chr.getPosition() != null) {
            Reactor nearest = null;
            double best = RESCUE_RANGE;
            for (Reactor r : chr.getField().getReactors()) {
                if (r.getTemplateId() == SpiritSaviorInstanceHelper.FREED_REACTOR) {
                    double d = r.getPosition().distanceTo(chr.getPosition());
                    if (d <= best) {
                        best = d;
                        nearest = r;
                    }
                }
            }
            if (nearest != null) {
                ss.rescue(chr, nearest);
            }
        }
        chr.dispose();
    }

    @Handler(op = InHeader.SPIRIT_SAVIOR_POLL)
    public static void handleSpiritSaviorPoll(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() >= 8) {
            log.debug(String.format("[SpiritSavior] poll from %s: %d %d", chr.getName(), inPacket.decodeInt(),
                    inPacket.decodeInt()));
        }
    }

    /**
     * Ranheim Defense (GMS): In 1734 int launcher, the harvest key on a Light Magic Launcher with a full
     * charge.
     */
    @Handler(op = InHeader.RANHEIM_DEFENSE_LAUNCH_REQUEST)
    public static void handleRanheimDefenseLaunch(Char chr, InPacket inPacket) {
        int launcher = inPacket.decodeInt();
        var inst = chr.getInstance();
        if (inst != null && inst.getInstanceHelper() instanceof RanheimDefenseInstanceHelper rd) {
            rd.launch(chr, launcher);
        }
    }

    /** Ranheim Defense: In 1735 int launcher, the launcher's shot landed (RANHEIM_DEFENSE_SHOT_REQUEST) */
    @Handler(op = InHeader.RANHEIM_DEFENSE_SHOT_REQUEST)
    public static void handleRanheimDefenseShot(Char chr, InPacket inPacket) {
        int launcher = inPacket.decodeInt();
        var inst = chr.getInstance();
        if (inst != null && inst.getInstanceHelper() instanceof RanheimDefenseInstanceHelper rd) {
            rd.shotLanded(chr, launcher);
        }
    }
}

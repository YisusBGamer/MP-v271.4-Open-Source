package net.swordie.ms.handlers.user;

import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.epic.AnglerCompanyInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.epic.EpicDungeon;
import net.swordie.ms.world.field.instance.instancehelper.epic.HighMountainInstanceHelper;

/**
 * Epic Dungeon field requests (v265 client): the Mitra morph key (1529), standing on the heal altar (1488), the end of
 * a field timer from Out 847 type 3 (1532), and Tynos's field (358/363): Mitra's Flare (1530), the end of a spine
 * animation from Out 2212 (1531) and the summoning altar (1533). Angler Company: the Proto-Suit terminal (1761) and the
 * stage objects' requests (1765, 1766; read and dropped, the server doesn't run those objects).
 */
public class EpicDungeonHandler {

    private static HighMountainInstanceHelper highMountain(Char chr) {
        var inst = chr.getInstance();
        return inst != null && inst.getInstanceHelper() instanceof HighMountainInstanceHelper hm ? hm : null;
    }

    private static AnglerCompanyInstanceHelper angler(Char chr) {
        var inst = chr.getInstance();
        return inst != null && inst.getInstanceHelper() instanceof AnglerCompanyInstanceHelper ac ? ac : null;
    }

    @Handler(op = InHeader.EPIC_DUNGEON_MORPH_REQUEST)
    public static void handleMorphRequest(Char chr, InPacket inPacket) {
        var hm = highMountain(chr);
        if (hm != null) {
            hm.toggleMorph(chr);
        }
        chr.write(UserLocal.epicDungeonMorphResult());
    }

    @Handler(op = InHeader.EPIC_DUNGEON_ALTAR_REQUEST)
    public static void handleAltarRequest(Char chr, InPacket inPacket) {
        var hm = highMountain(chr);
        if (hm != null) {
            hm.onAltarStep(chr);
        }
    }

    @Handler(op = InHeader.EPIC_DUNGEON_TIMER_DONE)
    public static void handleTimerDone(Char chr, InPacket inPacket) {
        int id = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : -1;
        var hm = highMountain(chr);
        if (hm != null) {
            hm.onTimerDone(chr, id);
        }
    }

    @Handler(op = InHeader.TYNOS_FLARE_REQUEST)
    public static void handleFlareRequest(Char chr, InPacket inPacket) {
        var hm = highMountain(chr);
        if (hm != null) {
            hm.onFlare(chr);
        }
    }

    @Handler(op = InHeader.TYNOS_SPINE_DONE)
    public static void handleSpineDone(Char chr, InPacket inPacket) {
        int id = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : -1;
        var hm = highMountain(chr);
        if (hm != null) {
            hm.onSpineDone(chr, id);
        }
    }

    @Handler(op = InHeader.TYNOS_ALTAR_REQUEST)
    public static void handleSummonAltar(Char chr, InPacket inPacket) {
        var hm = highMountain(chr);
        if (hm != null) {
            hm.onSummonAltar(chr);
        }
    }

    @Handler(op = InHeader.EPIC_DUNGEON2_PROTO_SUIT_REQUEST)
    public static void handleProtoSuitRequest(Char chr, InPacket inPacket) {
        int objectId = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : -1;
        var ac = angler(chr);
        if (ac != null) {
            ac.onProtoSuit(chr, objectId);
        }
    }

    @Handler(op = InHeader.EPIC_DUNGEON2_FIELD_REQUEST)
    public static void handleAnglerFieldRequest(Char chr, InPacket inPacket) {
    }

    @Handler(op = InHeader.EPIC_DUNGEON2_OBJECT_REQUEST)
    public static void handleAnglerObjectRequest(Char chr, InPacket inPacket) {
    }
}

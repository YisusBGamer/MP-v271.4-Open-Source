package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;

/**
 * In-map teleport portals (e.g. the Free Market's ledges, Lachelein's alley, Gollux): the client asks the server to
 * teleport it (byte type, portal name, its x/y, the destination x/y) and waits for a TELEPORT packet. This wasn't
 * handled, so those portals did nothing (players tried them again and again).
 */
public class CallingTeleportHandler {

    @Handler(op = InHeader.USER_CALLING_TELEPORT_REQUEST)
    public static void handleUserCallingTeleportRequest(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        // v265 (0x144e7cf30): byte (from the user object), byte type (not 3..5), int id; no portal name or positions.
        // The id is taken as the portal id (unconfirmed).
        if (field == null || field.getInfo() == null || inPacket.getUnreadAmount() < 6) {
            return;
        }
        inPacket.decodeByte();
        inPacket.decodeByte(); // type
        int portalId = inPacket.decodeInt();
        Portal portal = field.getInfo().getPortalByID(portalId);
        if (portal == null) {
            return;
        }
        Position to = null;
        // a portal to another spot in this map: trust the map's data over the client
        if (portal.getTargetMapId() == field.getId() && portal.getTargetPortalName() != null) {
            Portal target = field.getInfo().getPortalByName(portal.getTargetPortalName());
            if (target != null) {
                to = new Position(target.getX(), target.getY());
            }
        }
        if (to == null && inPacket.getUnreadAmount() >= 8) {
            inPacket.decodeShort(); // old x
            inPacket.decodeShort(); // old y
            to = new Position(inPacket.decodeShort(), inPacket.decodeShort());
        }
        if (to != null) {
            chr.setPosition(to);
            OutPacket outPacket = new OutPacket(OutHeader.TELEPORT);
            outPacket.encodeByte(true); // exclRequest: the answer to the client's request
            outPacket.encodeByte(true); // nUserCallingType: to a position
            outPacket.encodeInt(chr.getId());
            outPacket.encodePosition(to);
            outPacket.encodeByte(0); // v271
            outPacket.encodeInt(0);
            chr.write(outPacket);
        }
    }
}

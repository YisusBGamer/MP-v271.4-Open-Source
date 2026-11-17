package net.swordie.ms.handlers.boss;

import net.swordie.ms.client.Client;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.boss.FirstAdversaryInstanceHelper;

/** The First Adversary's client reports (other bosses send them too; they're ignored there). */
public class FirstAdversaryHandler {

    private static FirstAdversaryInstanceHelper helper(Client c) {
        var chr = c.getChr();
        if (chr == null || chr.getInstance() == null) {
            return null;
        }
        return chr.getInstance().getInstanceHelper() instanceof FirstAdversaryInstanceHelper h ? h : null;
    }

    /** v271 0x14545e590: 4 0, 4 pattern key (category * 100000 + pattern), 4 sub attack, 4. */
    @Handler(op = InHeader.UNKNOWN_OPCODE_661_SINK)
    public static void handlePatternAttackHit(Client c, InPacket inPacket) {
        var h = helper(c);
        if (h == null || inPacket.getUnreadAmount() < 12) {
            return;
        }
        inPacket.decodeInt();
        int key = inPacket.decodeInt();
        int sub = inPacket.decodeInt();
        h.onPatternAttackHit(c.getChr(), key, sub);
    }

    /** v271 0x14198a0b0 family: 4 type; type 4 = a field skill used (4 skill, 4 char id). */
    @Handler(op = InHeader.UNKNOWN_OPCODE_1772_SINK)
    public static void handleFieldSetReport(Client c, InPacket inPacket) {
        var h = helper(c);
        if (h == null || inPacket.getUnreadAmount() < 8) {
            return;
        }
        int type = inPacket.decodeInt();
        if (type == 4) {
            h.onGuardUsed(c.getChr(), inPacket.decodeInt());
        }
    }

    /** v271 0x141f3d240: 4 type; type 4 = a boss pattern hit (4 pattern key, 4 mob template, 4 mob id, 1). Lotus' gauge. */
    @Handler(op = InHeader.UNKNOWN_OPCODE_1777_SINK)
    public static void handlePatternHitReport(Client c, InPacket inPacket) {
        var chr = c.getChr();
        if (chr == null || chr.getInstance() == null || inPacket.getUnreadAmount() < 17
                || !(chr.getInstance().getInstanceHelper()
                instanceof net.swordie.ms.world.field.instance.instancehelper.boss.LotusV265InstanceHelper lotus)) {
            return;
        }
        if (inPacket.decodeInt() != 4) {
            return;
        }
        int key = inPacket.decodeInt();
        inPacket.decodeInt();
        inPacket.decodeInt();
        lotus.onPatternHit(key, inPacket.decodeByte());
    }
}

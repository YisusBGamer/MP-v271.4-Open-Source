package net.swordie.ms.handlers.item;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipAttribute;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.EquipBaseStat;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.loaders.StringData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Return Scroll (5064400): after the next successful stat scroll on the equip, the player chooses between the old and
 * the new stats. RETURN_EFFECT_CONFIRM (340, reader 0x1453c1410: 1, B8 equip SN, 1 has + typed item + 4 scroll) shows
 * the old equip, RETURN_EFFECT_MODIFIED (341, reader 0x1453c1aa0: 1 has + typed item + 4 scroll) the new one; the
 * client answers USER_RETURN_EFFECT_RESPONSE (569: 4 tick, 1 return). A choice that never comes keeps the new stats.
 */
public class ReturnScrollModule {

    private record Pending(Equip equip, Equip before, int scrollId) {
    }

    private static final Map<Integer, Pending> pending = new ConcurrentHashMap<>();

    /** Called after a successful scroll on an equip that had the Return Scroll on it (before = its copy from before). */
    public static void offer(Char chr, Equip equip, Equip before, int scrollId) {
        equip.removeAttribute(EquipAttribute.ReturnScroll);
        before.removeAttribute(EquipAttribute.ReturnScroll);
        pending.put(chr.getId(), new Pending(equip, before, scrollId));

        OutPacket confirm = new OutPacket(OutHeader.RETURN_EFFECT_CONFIRM);
        confirm.encodeByte(0);
        confirm.encodeLong(equip.getId());
        confirm.encodeByte(1);
        before.encode(confirm);
        confirm.encodeInt(scrollId);
        chr.write(confirm);

        OutPacket modified = new OutPacket(OutHeader.RETURN_EFFECT_MODIFIED);
        modified.encodeByte(1);
        equip.encode(modified);
        modified.encodeInt(scrollId);
        chr.write(modified);
    }

    /** The player's choice: back to the stats from before the scroll (the upgrade slot stays used), or keep them. */
    public static void respond(Char chr, boolean goBack) {
        Pending p = pending.remove(chr.getId());
        if (p == null) {
            chr.dispose();
            return;
        }
        Equip equip = p.equip();
        if (goBack && equip.getInvType() != null && chr.getInventoryByType(equip.getInvType()).getItems().contains(equip)) {
            long tuc = equip.getBaseStat(EquipBaseStat.tuc), cuc = equip.getBaseStat(EquipBaseStat.cuc);
            for (EquipBaseStat ebs : EquipBaseStat.values()) {
                if (ebs != EquipBaseStat.attribute && ebs != EquipBaseStat.growthEnchant
                        && ebs != EquipBaseStat.psEnchant && ebs != EquipBaseStat.exGradeOption) {
                    equip.setBaseStat(ebs, p.before().getBaseStat(ebs));
                }
            }
            equip.setBaseStat(EquipBaseStat.tuc, tuc);
            equip.setBaseStat(EquipBaseStat.cuc, cuc);
            equip.recalcEnchantmentStats();
            equip.updateToChar(chr);
            String eq = StringData.getItemStringById(equip.getItemId());
            String sc = StringData.getItemStringById(p.scrollId());
            chr.chatMessage(String.format("The power of the Return Scroll has returned the stats on your %s to what "
                    + "they were before using the %s.", eq != null ? eq : "equip", sc != null ? sc : "scroll"));
        } else {
            chr.chatMessage("The protection of the Return Scroll has disappeared.");
        }
        chr.dispose();
    }

    public static void forget(Char chr) {
        pending.remove(chr.getId());
    }
}

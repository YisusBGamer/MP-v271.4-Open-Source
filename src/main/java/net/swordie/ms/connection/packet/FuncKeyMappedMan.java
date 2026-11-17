package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.keys.FuncKeyMap;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.handlers.header.OutHeader;

import java.util.List;

/**
 * Created on 21/03/2020.
 *
 * @author Asura
 */
public class FuncKeyMappedMan {

    private static final int PRESETS = 4;

    /**
     * v271: 4 key presets (v265 3; client 0x143811eb0), each a byte (1 = the client's default keys) or 89 + 10
     * (type, value) entries, then the quick slots (a byte, then 32 ints). We keep one key map (Beast Tamer: one per mode), so every preset
     * gets the first.
     */
    public static OutPacket funcKeyMappedManInit(List<FuncKeyMap> funcKeyMaps, List<Integer> quickslotKeys) {
        OutPacket outPacket = new OutPacket(OutHeader.FUNC_KEY_MAPPED_MAN_INIT);

        FuncKeyMap funcKeyMap = funcKeyMaps == null || funcKeyMaps.isEmpty() ? null : funcKeyMaps.get(0);
        for (int i = 0; i < PRESETS; i++) {
            if (funcKeyMap == null) {
                outPacket.encodeByte(true); // empty: the client's default keys
            } else {
                funcKeyMap.encode(outPacket);
            }
        }
        boolean hasQuickslots = quickslotKeys != null && !quickslotKeys.isEmpty();
        outPacket.encodeByte(hasQuickslots);
        if (hasQuickslots) {
            for (int i = 0; i < GameConstants.QUICKSLOT_LENGTH; i++) {
                outPacket.encodeInt(i < quickslotKeys.size() ? quickslotKeys.get(i) : 0);
            }
        }

        return outPacket;
    }

    /**
     * Sets the HP Slot in Pet Tab (Equip Window)
     *
     * @param hpPotItemId Template Id of the Item to be set as Pet's HP Slot.
     * @return
     */
    public static OutPacket petConsumeHpInit(int hpPotItemId) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_CONSUME_HP_INIT);

        outPacket.encodeInt(hpPotItemId); // template Id

        return outPacket;
    }

    /**
     * Sets the MP Slot in Pet Tab (Equip Window)
     *
     * @param mpPotItemId Template Id of the Item to be set as Pet's MP Slot.
     * @return
     */
    public static OutPacket petConsumeMpInit(int mpPotItemId) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_CONSUME_MP_INIT);

        outPacket.encodeInt(mpPotItemId); // template Id

        return outPacket;
    }

    /**
     * Sets the AC Slot in Pet Tab (Equip Window)
     *
     * @param acPotItemId Template Id of the Item to be set as Pet's All-Cure Slot.
     * @return
     */
    public static OutPacket petConsumeAllCureInit(int acPotItemId) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_CONSUME_AC_INIT);

        outPacket.encodeInt(acPotItemId); // template Id

        return outPacket;
    }
}

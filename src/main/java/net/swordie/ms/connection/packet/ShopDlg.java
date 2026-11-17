package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.BossLootConstants;
import net.swordie.ms.constants.CustomConstants;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.shop.NpcShopDlg;
import net.swordie.ms.world.shop.result.ShopResult;

import java.util.List;
import java.util.Map;

/**
 * Created on 3/28/2018.
 */
public class ShopDlg {

    public static OutPacket openShop(Char chr, int petTemplateID, NpcShopDlg nsd) {
        OutPacket outPacket = new OutPacket(OutHeader.SHOP_OPEN);

        outPacket.encodeInt(nsd.getNpcTemplateID());
        outPacket.encodeByte(petTemplateID != 0);
        if (petTemplateID != 0) {
            outPacket.encodeInt(petTemplateID);
        }
        nsd.encode(outPacket, chr.getBuyBack());

        return outPacket;
    }

    public static OutPacket shopResult(ShopResult shopResult) {
        OutPacket outPacket = new OutPacket(OutHeader.SHOP_RESULT);

        shopResult.encode(outPacket);

        return outPacket;
    }

    public static OutPacket shopCollectorInit() {
        OutPacket outPacket = new OutPacket(OutHeader.SHOP_COLLECTOR_INIT);

        boolean doRefresh = true;
        outPacket.encodeByte(doRefresh);
        if (doRefresh) {
            outPacket.encodeInt(1); // ??
            outPacket.encodeLong(0); // ??
            outPacket.encodeInt(2); // 0 = no show (buffer of 4)
            outPacket.encodeInt(0); // % increase price, 10 being 10% more $$$ (additionally increases meso price after the increase below)
            outPacket.encodeInt(0); // % increase price; the list below already carries the sell prices
            outPacket.encodeByte(0); // v271: new flag
            outPacket.encodeInt(0); // v271: new int

            // v265 (reference server): int 1, then two price periods split at the same date
            long[][] periods = {{94354848000000000L, 133778304000000000L}, {133778304000000000L, 150842304000000000L}};
            outPacket.encodeInt(1);
            for (long[] period : periods) {
                outPacket.encodeLong(period[0]); // start date to display
                outPacket.encodeLong(period[1]); // end date to display


                List<Map.Entry<Integer, Long>> bossPowerCrystalEntries =  BossLootConstants.getPowerCrystalMesos();
                outPacket.encodeInt(bossPowerCrystalEntries.size());
                for (Map.Entry<Integer, Long> bpcEntry : bossPowerCrystalEntries) {
                    Tuple<Integer, Integer> templateID = BossLootConstants.getPowerCrystalTemplateIdOverrideForUI(bpcEntry.getKey());
                    outPacket.encodeInt(templateID == null ? bpcEntry.getKey() : templateID.getLeft()); // mobName
                    outPacket.encodeLong(BossLootConstants.getPowerCrystalSellPrice(bpcEntry.getValue()));
                    outPacket.encodeInt(templateID == null ? bpcEntry.getKey() : templateID.getRight()); // mobIcon
                }
            }
        }

        return outPacket;
    }

}

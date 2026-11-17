package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Epic Dungeon windows: Selazar's window (UI router) and the clear / bonus reward windows (context). */
public class EpicDungeonPacket {

    /** one region of Selazar's window and its NX record */
    public record RegionRecord(int region, int recordKey, String record) {
        public void encode(OutPacket out) {
            out.encodeInt(region);
            out.encodeInt(0);
            out.encodeInt(recordKey);
            out.encodeString(record);
        }
    }

    /** Selazar's window, showing the given region first */
    public static OutPacket uiOpen(int region, RegionRecord... regions) {
        OutPacket out = new OutPacket(OutHeader.EPIC_DUNGEON_UI_OPEN);
        out.encodeInt(region);
        out.encodeInt(regions.length);
        for (RegionRecord r : regions) {
            r.encode(out);
        }
        return out;
    }

    /**
     * ClearRewardUI / BonusRewardUI: int key, long EXP, items (int n, n x (int id, int qty)), [bonus: int NX record
     * key], int tier 1 price, int tier 2 price, Erda tiers (int n, tier 1..n: int, items (int m, m x (int id, int extra
     * multiple))), EXP tiers (int n, tier 1..n: int total multiplier, int m, m x (int, int)). Tier arrays are indexed
     * by tier (index 0 unused).
     */
    public static OutPacket rewardWindow(OutHeader header, int nxRecord, long exp, int[][] items, boolean bonus,
                                         int[] bonusCost, int[] erdaExtra, int[] expMult) {
        OutPacket out = new OutPacket(header);
        out.encodeInt(nxRecord);
        out.encodeLong(exp);
        out.encodeInt(items.length);
        for (int[] item : items) {
            out.encodeInt(item[0]);
            out.encodeInt(item[1]);
        }
        if (bonus) {
            out.encodeInt(nxRecord);
        }
        out.encodeInt(bonusCost[1]);
        out.encodeInt(bonusCost[2]);
        out.encodeInt(2);
        for (int tier = 1; tier <= 2; tier++) {
            out.encodeInt(erdaExtra[tier]);
            out.encodeInt(items.length);
            for (int[] item : items) {
                out.encodeInt(item[0]);
                out.encodeInt(erdaExtra[tier]);
            }
        }
        out.encodeInt(2);
        for (int tier = 1; tier <= 2; tier++) {
            out.encodeInt(expMult[tier]);
            out.encodeInt(0);
        }
        return out;
    }
}

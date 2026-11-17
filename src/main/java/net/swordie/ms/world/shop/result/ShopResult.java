package net.swordie.ms.world.shop.result;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.world.shop.NpcShopDlg;
import net.swordie.ms.world.shop.NpcShopItem;

import java.util.List;

/**
 * Created on 3/29/2018.
 */
public class ShopResult implements Encodable {

    private ShopResultType type;
    private NpcShopDlg shop;
    private int arg1;
    private int arg2;
    private int arg3;
    private int arg4;
    private List<NpcShopItem> buyBack;

    public ShopResult(ShopResultType type) {
        this.type = type;
    }

    /** v271 SHOP_RESULT switch (0x143fbdb30): a new type 4 (string only), so v265 types 4..37 are one higher. */
    public static int v271Type(int v265Type) {
        return v265Type >= 4 ? v265Type + 1 : v265Type;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(v271Type(type.getVal()));
        switch (type) {
            case Buy:
                outPacket.encodeByte(arg1 != 0); // repurchaseItem
                if (arg1 != 0) {
                    outPacket.encodeInt(arg1);
                } else {
                    outPacket.encodeInt(arg2); // item id
                    outPacket.encodeInt(arg3); // remaining stock, -1 = no limit (GMS: 10 bytes in all)
                }
                break;
            case Update:
                shop.encode(outPacket, buyBack);
                break;
            case Success:
                outPacket.encodeInt(arg1); // nItemID
                break;
            default:
                outPacket.encodeInt(arg1);
                break;
        }
    }

    public static ShopResult buy(int repurchaseItem, int someUpdateItem, int remainingStock, int starCoinUpdate) {
        ShopResult sr = new ShopResult(ShopResultType.Buy);

        sr.arg1 = repurchaseItem;
        sr.arg2 = someUpdateItem;
        sr.arg3 = remainingStock;
        sr.arg4 = starCoinUpdate;

        return sr;
    }

    public static ShopResult msg(ShopResultType srt) {
        return new ShopResult(srt);
    }

    public static ShopResult intMsg(ShopResultType srt, int val) {
        ShopResult sr = new ShopResult(srt);

        sr.arg1 = val;

        return sr;
    }

    public static ShopResult sellSuccess(int soldItemId) {
        ShopResult sr = new ShopResult(ShopResultType.Success);

        sr.arg1 = soldItemId;

        return sr;
    }

    public static ShopResult update(Char chr, NpcShopDlg nsd) {
        ShopResult sr = new ShopResult(ShopResultType.Update);

        sr.shop = nsd;
        sr.buyBack = chr.getBuyBack();

        return sr;
    }
}

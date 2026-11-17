package net.swordie.ms.client.character.cashshop;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.packet.CCashShop;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.CashItemType;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.world.shop.cashshop.CashItemInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cash Shop coupon codes (table coupons, one use per account in coupon_uses). A code gives one item (cash items go
 * to the Cash Shop locker, others to the inventory), NX, Maple Points and/or mesos.
 */
public class CouponModule {
    private static final Logger log = LogManager.getLogger();

    // fail reasons the v265 client shows for coupons (0x141c6b190)
    public static final int REASON_WRONG_NUMBER = 14;
    public static final int REASON_CANT_USE_NOW = 16;
    public static final int REASON_EXPIRED = 17;
    public static final int REASON_ALREADY_USED = 18;

    public static synchronized void useCoupon(Char chr, String rawCode) {
        String code = rawCode == null ? "" : rawCode.trim().toUpperCase();
        if (code.isEmpty() || code.length() > 32) {
            chr.write(CCashShop.couponFailed(REASON_WRONG_NUMBER));
            return;
        }
        var rows = DatabaseManager.queryRows("SELECT code AS code, itemid AS itemid, quantity AS quantity, nx AS nx, "
                + "maplepoints AS maplepoints, mesos AS mesos, maxuses AS maxuses, uses AS uses, expires AS expires "
                + "FROM coupons WHERE code = ?", code);
        if (rows.isEmpty()) {
            chr.write(CCashShop.couponFailed(REASON_WRONG_NUMBER));
            return;
        }
        Map<String, Object> row = rows.get(0);
        int itemId = num(row.get("itemid")).intValue();
        int quantity = Math.max(1, num(row.get("quantity")).intValue());
        int nx = num(row.get("nx")).intValue();
        int maplePoints = num(row.get("maplepoints")).intValue();
        long mesos = num(row.get("mesos")).longValue();
        int maxUses = num(row.get("maxuses")).intValue();
        int uses = num(row.get("uses")).intValue();
        long expires = num(row.get("expires")).longValue();
        if (expires > 0 && System.currentTimeMillis() > expires) {
            chr.write(CCashShop.couponFailed(REASON_EXPIRED));
            return;
        }
        if (maxUses > 0 && uses >= maxUses) {
            chr.write(CCashShop.couponFailed(REASON_ALREADY_USED));
            return;
        }
        Account account = chr.getAccount();
        if (!DatabaseManager.queryRows("SELECT 1 AS x FROM coupon_uses WHERE code = ? AND accountid = ?",
                code, account.getId()).isEmpty()) {
            chr.write(CCashShop.couponFailed(REASON_ALREADY_USED));
            return;
        }
        boolean cashItem = itemId > 0 && ItemConstants.isCash(itemId);
        if (itemId > 0 && ItemData.getItemDeepCopy(itemId) == null) {
            log.warn(String.format("[Coupon] %s: item %d of coupon %s does not exist", chr.getName(), itemId, code));
            chr.write(CCashShop.couponFailed(REASON_CANT_USE_NOW));
            return;
        }
        if (cashItem && account.getTrunk().isFullLocker()) {
            chr.write(CCashShop.fullInventoryMsg());
            return;
        }
        if (itemId > 0 && !cashItem && !chr.canHold(itemId, quantity)) {
            chr.write(CCashShop.fullInventoryMsg());
            return;
        }
        if (mesos > 0 && !chr.canAddMoney(mesos)) {
            chr.write(CCashShop.couponFailed(REASON_CANT_USE_NOW));
            return;
        }
        // claim it (useCoupon runs one at a time, so the checks above still hold)
        DatabaseManager.executeQuery("INSERT IGNORE INTO coupon_uses (code, accountid, charid, usedtime) "
                + "VALUES (?, ?, ?, ?)", code, account.getId(), chr.getId(), System.currentTimeMillis());
        DatabaseManager.executeQuery("UPDATE coupons SET uses = uses + 1 WHERE code = ?", code);

        List<CashItemInfo> lockerItems = new ArrayList<>();
        List<int[]> normalItems = new ArrayList<>();
        if (cashItem) {
            CashItemInfo cii = CashItemInfo.fromNewItem(chr, itemId, quantity);
            account.getTrunk().addCashItem(cii);
            lockerItems.add(cii);
        } else if (itemId > 0) {
            Item item = ItemData.getItemDeepCopy(itemId);
            item.setQuantity(quantity);
            chr.addItemToInventory(item);
            normalItems.add(new int[]{itemId, quantity});
        }
        if (nx > 0) {
            account.addNXCredit(nx);
        }
        User user = chr.getUser();
        if (maplePoints > 0 && user != null) {
            user.addMaplePoints(maplePoints);
        }
        if (mesos > 0) {
            chr.addMoney(mesos);
        }
        log.info(String.format("[Coupon] %s (account %d) used %s: item %d x%d, %d NX, %d MP, %d mesos", chr.getName(),
                account.getId(), code, itemId, quantity, nx, maplePoints, mesos));
        chr.write(CCashShop.couponDone(lockerItems, maplePoints, normalItems, mesos));
        chr.write(CCashShop.queryCashResult(chr));
    }

    /** Adds or replaces a code. @return whether it was saved */
    public static synchronized boolean addCoupon(String code, int itemId, int quantity, int nx, int maplePoints, long mesos,
                                    int maxUses, long expires, String note) {
        String c = code.trim().toUpperCase();
        if (c.isEmpty() || c.length() > 32) {
            return false;
        }
        DatabaseManager.executeQuery("REPLACE INTO coupons (code, itemid, quantity, nx, maplepoints, mesos, "
                + "maxuses, uses, expires, note) VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?)", c, itemId, Math.max(1, quantity),
                nx, maplePoints, mesos, maxUses, expires, note == null ? "" : note);
        return exists(c);
    }

    /** @return whether there was such a code */
    public static synchronized boolean deleteCoupon(String code) {
        String c = code.trim().toUpperCase();
        if (!exists(c)) {
            return false;
        }
        DatabaseManager.executeQuery("DELETE FROM coupons WHERE code = ?", c);
        return true;
    }

    private static boolean exists(String code) {
        return !DatabaseManager.queryRows("SELECT 1 AS x FROM coupons WHERE code = ?", code).isEmpty();
    }

    /** One line per code (at most 30). */
    public static List<String> listCoupons() {
        List<String> lines = new ArrayList<>();
        for (Map<String, Object> r : DatabaseManager.queryRows("SELECT code AS code, itemid AS itemid, quantity AS quantity, "
                + "nx AS nx, maplepoints AS maplepoints, mesos AS mesos, maxuses AS maxuses, uses AS uses, "
                + "expires AS expires FROM coupons ORDER BY code LIMIT 30")) {
            long exp = num(r.get("expires")).longValue();
            int max = num(r.get("maxuses")).intValue();
            lines.add(String.format("%s: item %d x%d, %d NX, %d MP, %d mesos, used %d/%s%s", r.get("code"),
                    num(r.get("itemid")).intValue(), num(r.get("quantity")).intValue(), num(r.get("nx")).intValue(),
                    num(r.get("maplepoints")).intValue(), num(r.get("mesos")).longValue(),
                    num(r.get("uses")).intValue(), max == 0 ? "any" : String.valueOf(max),
                    exp == 0 ? "" : ", ends " + new java.util.Date(exp)));
        }
        return lines;
    }

    private static Number num(Object o) {
        return o instanceof Number n ? n : 0;
    }
}

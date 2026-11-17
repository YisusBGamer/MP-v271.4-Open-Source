package net.swordie.ms.handlers;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Cash Inventory's trash button (Req_Rebate) finds the item sn on v271 and v265 clients.
 * Contributed by Dapsy (from the Discord).
 */
public class CashShopRebateTest {

    @Test
    public void v271_snAfterTheString() {
        // seen from the v271 client: 00 00 01 | 01 00 2E | 7E 48 00 00 00 00 00 00
        Assert.assertEquals(6, CashShopHandler.rebateSnOffset(14));
    }

    @Test
    public void v265_snAfterThreeBytes() {
        Assert.assertEquals(3, CashShopHandler.rebateSnOffset(11));
    }

    @Test
    public void tooShort() {
        Assert.assertEquals(-1, CashShopHandler.rebateSnOffset(10));
        Assert.assertEquals(-1, CashShopHandler.rebateSnOffset(0));
    }
}

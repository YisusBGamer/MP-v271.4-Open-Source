-- Town stores and the profession recipe shop carry what they sell on GMS (stock and meso prices, v271).
-- Added: Sprout Potion, throwing stars and bullets (bundle = one stack), Powerful Rebirth Flame (7 days),
-- Customized Soul Enchanter, the Storage Room coupon, bags, Mastery Books, Appearance Reset Coupon, Pet Food.
-- Left out: the inventory tab coupons (every inventory is already at its maximum), the character slot coupon and the Silver Potential Stamp (no use handler yet).
-- Each insert is skipped when the shop already sells the item, so re-running adds nothing.
-- 1011100 Luna
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2048716, 9500000, 0, 0, 0, 0, 10080, 0, 0, 0, 100000, 1, 999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2048716);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2434257, 9900000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2434257);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2650012, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2650012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 4330031, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 4330031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 4330030, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 4330030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 4330032, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 4330032);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2650011, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2650011);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 3080019, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 3080019);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 3080018, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 3080018);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2070001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2070001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2070002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 700, 700 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2070002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2070003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2070003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2070004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2070004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2070005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2070005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2070006, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2070006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2330000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2330001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1200, 1200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2330001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2330002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1600, 1600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2330002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2330003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2200, 2200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2330003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2330004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2600, 2600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2330004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2330005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3000, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2330005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1011100, 2330008, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3200, 3200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1011100 AND itemid = 2330008);
-- 1001100 Mina
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1001100, 2431789, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1001100 AND itemid = 2431789);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1001100, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1001100 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1001100, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1001100 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1001100, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1001100 AND itemid = 2120000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1001100, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1001100 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1001100, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1001100 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1001100, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1001100 AND itemid = 2330000);
-- 3003100 Lento
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2048716, 9500000, 0, 0, 0, 0, 10080, 0, 0, 0, 100000, 1, 999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2048716);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2434257, 9900000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2434257);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2650012, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2650012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 4330031, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 4330031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 4330030, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 4330030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 4330032, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 4330032);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2650011, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2650011);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 3080019, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 3080019);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 3080018, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 3080018);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2070001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2070001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2070002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 700, 700 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2070002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2070003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2070003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2070004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2070004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2070005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2070005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2070006, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2070006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2330000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2330001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1200, 1200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2330001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2330002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1600, 1600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2330002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2330003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2200, 2200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2330003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2330004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2600, 2600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2330004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2330005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3000, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2330005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003100, 2330008, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3200, 3200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003100 AND itemid = 2330008);
-- 3003158 Madam Popo
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2048716, 9500000, 0, 0, 0, 0, 10080, 0, 0, 0, 100000, 1, 999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2048716);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2434257, 9900000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2434257);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2650012, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2650012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 4330031, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 4330031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 4330030, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 4330030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 4330032, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 4330032);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2650011, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2650011);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 3080019, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 3080019);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 3080018, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 3080018);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2070001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2070001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2070002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 700, 700 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2070002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2070003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2070003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2070004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2070004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2070005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2070005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2070006, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2070006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2330000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2330001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1200, 1200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2330001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2330002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1600, 1600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2330002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2330003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2200, 2200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2330003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2330004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2600, 2600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2330004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2330005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3000, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2330005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003158, 2330008, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3200, 3200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003158 AND itemid = 2330008);
-- 3004467 Douglas
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2048716, 9500000, 0, 0, 0, 0, 10080, 0, 0, 0, 100000, 1, 999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2048716);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2434257, 9900000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2434257);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2650012, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2650012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 4330031, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 4330031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 4330030, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 4330030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 4330032, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 4330032);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2650011, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2650011);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 3080019, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 3080019);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 3080018, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 3080018);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2070001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2070001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2070002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 700, 700 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2070002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2070003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2070003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2070004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2070004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2070005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2070005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2070006, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2070006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2330000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2330001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1200, 1200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2330001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2330002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1600, 1600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2330002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2330003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2200, 2200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2330003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2330004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2600, 2600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2330004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2330005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3000, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2330005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3004467, 2330008, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3200, 3200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3004467 AND itemid = 2330008);
-- 2110001 Jerry
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2110001, 2431789, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2110001 AND itemid = 2431789);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2110001, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2110001 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2110001, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2110001 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2110001, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2110001 AND itemid = 2050001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2110001, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2110001 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2110001, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2110001 AND itemid = 2432805);
-- 2161013 Glibber
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2431789, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2431789);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2001000, 3328, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2001000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2001001, 2392, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2001001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2001002, 4160, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2001002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020005, 99, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020001, 68, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020003, 234, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020004, 234, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020006, 275, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2022003, 800, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2022003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2022000, 1201, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2022000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 700, 700 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2330000);
-- 1501005 Renard
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1501005, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1501005 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1501005, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1501005 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1501005, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1501005 AND itemid = 2120000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1501005, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1501005 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1501005, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1501005 AND itemid = 2432805);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1501005, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 640, 640 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1501005 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1501005, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1501005 AND itemid = 2330000);
-- 2142000 Maya
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2431789, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2431789);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2120000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2432805);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2142000, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2142000 AND itemid = 2330000);
-- 1511000 Puddin
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2431789, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2431789);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2120000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2432805);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1511000, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1511000 AND itemid = 2330000);
-- 2140004 Johanna
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2140004, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2140004 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2140004, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2140004 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2140004, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2140004 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2140004, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2140004 AND itemid = 2432805);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2140004, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2140004 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2140004, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2140004 AND itemid = 2330000);
-- 1052254 June
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1052254, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1052254 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1052254, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1052254 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1052254, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1052254 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1052254, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1052254 AND itemid = 2432805);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1052254, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1052254 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1052254, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1052254 AND itemid = 2330000);
-- 2040051 Toly
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2431789, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2431789);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2000059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2120000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2432805);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2040051, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2040051 AND itemid = 2330000);
-- 3006336 Chest of Wisdom
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2434257, 9900000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2434257);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2650012, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2650012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 4330031, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 4330031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 4330030, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 4330030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 4330032, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 4330032);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2650011, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2650011);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 3080019, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 3080019);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 3080018, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 3080018);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2070001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2070001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2070002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 700, 700 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2070002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2070003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2070003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2070004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2070004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2070005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2070005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2070006, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2070006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2330000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2330001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1200, 1200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2330001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2330002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1600, 1600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2330002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2330003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2200, 2200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2330003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2330004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2600, 2600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2330004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2330005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3000, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2330005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2330008, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3200, 3200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2330008);
-- 3005581 Bonnie
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2590008, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2590008);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2434257, 9900000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2434257);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2650012, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2650012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 4330031, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 4330031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 4330030, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 4330030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 4330032, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 4330032);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2650011, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2650011);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 3080019, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 3080019);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 3080018, 50000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 3080018);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2070000, 500, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2070000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2070001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2070001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2070002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 700, 700 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2070002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2070003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 500, 500 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2070003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2070004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2070004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2070005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1000, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2070005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2070006, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2070006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2330000, 600, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 800, 800 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2330000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2330001, 50000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1200, 1200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2330001);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2330002, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 1600, 1600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2330002);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2330003, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2200, 2200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2330003);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2330004, 950000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 2600, 2600 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2330004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2330005, 2100000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3000, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2330005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2330008, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 1000000, 3200, 3200 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2330008);
-- 9031015 Angelica
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512004, 250000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512004);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512005, 300000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512005);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512006, 350000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512006);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512007, 400000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512007);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512012, 250000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512013, 300000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512013);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512014, 350000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512014);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512015, 400000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512015);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512020, 250000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512020);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512021, 300000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512021);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512022, 350000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512022);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512023, 400000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512023);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512028, 250000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512028);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512029, 300000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512029);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512030, 350000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512031, 400000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512035, 400000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512035);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512036, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512036);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512040, 400000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512040);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512041, 500000, 0, 0, 0, 0, 0, 0, 0, 0, 9999999, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512041);
-- Prices that differ from GMS (the shop has one row for the item)
UPDATE shopitems SET price = 49 WHERE shopid = 2161013 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0; -- Orange Potion
UPDATE shopitems SET price = 99 WHERE shopid = 2161013 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0; -- White Potion
UPDATE shopitems SET price = 20 WHERE shopid = 2161013 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0; -- Blue Potion
UPDATE shopitems SET price = 193 WHERE shopid = 2161013 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0; -- Mana Elixir
UPDATE shopitems SET price = 520 WHERE shopid = 2161013 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0; -- Dexterity Potion
UPDATE shopitems SET price = 416 WHERE shopid = 2161013 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0; -- Speed Potion
UPDATE shopitems SET price = 520 WHERE shopid = 2161013 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0; -- Magic Potion
UPDATE shopitems SET price = 520 WHERE shopid = 2161013 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0; -- Warrior Potion
UPDATE shopitems SET price = 520 WHERE shopid = 2161013 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0; -- Sniper Potion
UPDATE shopitems SET price = 96 WHERE shopid = 2161013 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0; -- Lemon
UPDATE shopitems SET price = 4680 WHERE shopid = 2161013 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0; -- Melting Cheese
UPDATE shopitems SET price = 5824 WHERE shopid = 2161013 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0; -- Reindeer Milk
UPDATE shopitems SET price = 8424 WHERE shopid = 2161013 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0; -- Sunrise Dew
UPDATE shopitems SET price = 10608 WHERE shopid = 2161013 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0; -- Sunset Dew
UPDATE shopitems SET price = 125000 WHERE shopid = 9031015 AND itemid = 2512219 AND price = 200000 AND tokenitemid = 0; -- Defense Pill I Recipe
UPDATE shopitems SET price = 150000 WHERE shopid = 9031015 AND itemid = 2512220 AND price = 300000 AND tokenitemid = 0; -- Defense Pill II Recipe
UPDATE shopitems SET price = 175000 WHERE shopid = 9031015 AND itemid = 2512221 AND price = 400000 AND tokenitemid = 0; -- Defense Pill III Recipe
UPDATE shopitems SET price = 200000 WHERE shopid = 9031015 AND itemid = 2512222 AND price = 500000 AND tokenitemid = 0; -- Defense Pill IV Recipe
UPDATE shopitems SET price = 250000 WHERE shopid = 9031015 AND itemid = 2512223 AND price = 600000 AND tokenitemid = 0; -- Defense Pill V Recipe
UPDATE shopitems SET price = 300000 WHERE shopid = 9031015 AND itemid = 2512224 AND price = 700000 AND tokenitemid = 0; -- Defense Pill VI Recipe
UPDATE shopitems SET price = 350000 WHERE shopid = 9031015 AND itemid = 2512225 AND price = 800000 AND tokenitemid = 0; -- Defense Pill VII Recipe
UPDATE shopitems SET price = 400000 WHERE shopid = 9031015 AND itemid = 2512226 AND price = 900000 AND tokenitemid = 0; -- Defense Pill VIII Recipe
UPDATE shopitems SET price = 450000 WHERE shopid = 9031015 AND itemid = 2512227 AND price = 1000000 AND tokenitemid = 0; -- Defense Pill IX Recipe
UPDATE shopitems SET price = 500000 WHERE shopid = 9031015 AND itemid = 2512228 AND price = 1100000 AND tokenitemid = 0; -- Defense Pill X Recipe

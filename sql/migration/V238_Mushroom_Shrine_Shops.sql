-- Mushroom Shrine's shops carry their GMS stock: Nita (armour, snowshoes) and Cliff (Mastery Books, Sprout Potion).
-- Each insert is skipped when the shop already sells the item, so re-running adds nothing.
-- 2230101 Nita
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002028, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002028);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002216, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002216);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002212, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002212);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002207, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002207);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002631, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002631);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002084, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002084);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002243, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002243);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002268, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002268);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002247, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002247);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1002634, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1002634);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040087, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040087);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040088, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040088);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040089, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040089);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040094, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040094);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040095, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040095);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040096, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040096);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040097, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040097);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041087, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041087);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041088, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041088);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041089, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041089);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041077, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041077);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041078, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041078);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041079, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041079);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041080, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041080);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060076, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060076);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060077, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060077);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060078, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060078);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060083, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060083);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060084, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060084);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060085, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060085);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060086, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060086);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061086, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061086);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061087, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061087);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061088, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061088);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061076, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061076);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061077, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061077);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061078, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061078);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061079, 96000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061079);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040090, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040090);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040091, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040091);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040092, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040092);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040093, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040093);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040098, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040098);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040099, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040099);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1040100, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1040100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041091, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041091);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041092, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041092);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041093, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041093);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041094, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041094);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041095, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041095);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1041096, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1041096);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060079, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060079);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060080, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060080);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060081, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060081);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060082, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060082);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060087, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060087);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060088, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060088);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1060089, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1060089);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061090, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061090);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061091, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061091);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061092, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061092);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061093, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061093);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061094, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061094);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1061095, 115000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1061095);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050045, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050045);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050046, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050046);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050047, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050047);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050049, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050049);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050048, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050048);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050051, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050051);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050052, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050052);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051030, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051030);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051031, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051031);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051032, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051032);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051033, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051033);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051034, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051034);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051037, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051037);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051038, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051038);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051039, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051039);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1052116, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1052116);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050053, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050053);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050054, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050054);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050055, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050055);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050056, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050056);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050058, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050058);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050059, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050059);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1050060, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1050060);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051044, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051044);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051045, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051045);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051046, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051046);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051047, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051047);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051041, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051041);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051042, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051042);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1051043, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1051043);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1052119, 173000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1052119);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072135, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072135);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072143, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072143);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072125, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072125);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072129, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072129);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072303, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072303);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072149, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072149);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072139, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072139);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072146, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072146);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072152, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072152);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072306, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072306);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082011, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082011);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082082, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082082);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082085, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082085);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082067, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082067);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082198, 120000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082198);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082061, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082061);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082088, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082088);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082091, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082091);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082094, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082094);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1082201, 144000, 0, 0, 0, 0, 0, 1, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1082201);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072168, 30000, 0, 0, 0, 0, 0, 7, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072168);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072169, 28000, 0, 0, 0, 0, 0, 7, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072169);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072170, 29000, 0, 0, 0, 0, 0, 7, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072170);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072171, 29000, 0, 0, 0, 0, 0, 7, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072171);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230101, 1072338, 29000, 0, 0, 0, 0, 0, 7, 0, 0, 100000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230101 AND itemid = 1072338);
-- 2230100 Cliff
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230100, 2431789, 3000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230100 AND itemid = 2431789);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230100, 2431790, 5000000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230100 AND itemid = 2431790);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2230100, 2000059, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2230100 AND itemid = 2000059);

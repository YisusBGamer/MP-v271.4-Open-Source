-- Puri Puri (Rien) sells Rien's GMS goods: its potions, elixirs, food and Mapleade, at GMS prices.
-- Each insert is skipped when the shop already sells the item, so re-running adds nothing.
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2001501, 160, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2001501);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2001503, 200, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2001503);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2001512, 3200, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2001512);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2001513, 2300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2001513);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2001514, 4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2001514);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002016, 5000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002016);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002017, 5000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002017);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002018, 5000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002018);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002019, 5000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002019);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002020, 2800, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002020);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002021, 2800, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002021);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002022, 2100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002022);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002024, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002024);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002025, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002025);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2002026, 3800, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2002026);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2020012);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2020013);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2020045, 8100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2020045);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2020046, 10200, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2020046);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2022174, 320, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2022174);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2022177, 620, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2022177);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2022189, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2022189);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2022190, 3000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2022190);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2022191, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2022191);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2022192, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2022192);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 2022195, 150000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 2022195);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 3994092, 1100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 3994092);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 4032352, 1650, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 4032352);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 4034403, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 4034403);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 4034405, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 4034405);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 4034406, 310, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 4034406);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, itemperiod, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1200002, 4220124, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1200002 AND itemid = 4220124);
UPDATE shopitems SET price = 106 WHERE shopid = 1200002 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0; -- Meat

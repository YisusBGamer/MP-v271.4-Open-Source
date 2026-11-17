-- Shops the v265 towns were missing.
-- 1) Shops from the reference v265.3 server (InitTables_shops.sql) that our DB lacked; several NPC scripts already
--    opened them (Matilda in Henesys, Cello in Zero's Temple, Murt, Mono, Sagacious Goemon, Legends/Heroes shops).
-- 2) General stores for shopkeepers flagged as shops in v265 Npc.wz with no stock (Lumiere, Kritias, Veritas, Grandis
--    towns, Hejin, Xuanshan, Oort ...): the town general store goods (potions, return scroll, arrows, pet food) at
--    this server's prices, as V104 did for the Arcane/Grandis merchants.
-- Each row is skipped when the shop already has that item at that price, so re-running adds nothing.
-- 9000132 Wee Moon Bunny
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9000132, 1112762, 0, 4310095, 500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9000132 AND itemid = 1112762 AND price = 0 AND tokenitemid = 4310095 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9000132, 2435285, 0, 4310095, 1000, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9000132 AND itemid = 2435285 AND price = 0 AND tokenitemid = 4310095 AND tokenprice = 1000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9000132, 2436114, 0, 4310095, 500, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9000132 AND itemid = 2436114 AND price = 0 AND tokenitemid = 4310095 AND tokenprice = 500);
-- 2020005 Alcaster
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 2050004, 400, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 2050004 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 2050003, 300, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 2050003 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 4006001, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 4006001 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 4006000, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 4006000 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
-- 1055000 Lumi (Weapon/Armor Seller)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002001, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002001 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060023, 2800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060023 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040033, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040033 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061038, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061038 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1442079, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1442079 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1422000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1422000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060033, 8000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060033 AND price = 8000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332006, 7000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332006 AND price = 7000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332009, 42000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332009 AND price = 42000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061037, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061037 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041040, 9000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041040 AND price = 9000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060024, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060024 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060025, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060025 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332013, 15000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332013 AND price = 15000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002097, 7400, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002097 AND price = 7400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061042, 12000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061042 AND price = 12000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041044, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041044 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041060, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041060 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1472000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1472000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1342000, 10000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1342000 AND price = 10000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041050, 45000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041050 AND price = 45000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061053, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061053 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061056, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061056 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002150, 20000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002150 AND price = 20000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1412001, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1412001 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1402001, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1402001 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1432001, 7000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1432001 AND price = 7000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060039, 19000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060039 AND price = 19000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040050, 16000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040050 AND price = 16000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1322009, 20000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1322009 AND price = 20000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332012, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332012 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1342001, 42000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1342001 AND price = 42000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060043, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060043 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040057, 45000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040057 AND price = 45000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072028, 4500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072028 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332008, 10000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332008 AND price = 10000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040058, 45000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040058 AND price = 45000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1302007, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1302007 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040034, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040034 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332002, 8000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332002 AND price = 8000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1432000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1432000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040033, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040033 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041057, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041057 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060023, 2800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060023 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072043, 2000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072043 AND price = 2000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002175, 30000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002175 AND price = 30000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002110, 4000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002110 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041045, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041045 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060044, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060044 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332010, 22000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332010 AND price = 22000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061033, 8000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061033 AND price = 8000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002130, 12000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002130 AND price = 12000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061046, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061046 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072066, 9000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072066 AND price = 9000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072031, 4500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072031 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040044, 9000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040044 AND price = 9000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332000, 4000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332000 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332004, 38000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332004 AND price = 38000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002125, 900, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002125 AND price = 900 AND tokenitemid = 0 AND tokenprice = 0);
-- 9201059 Kyle (Weapon Seller)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1382135, 22500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1382135 AND price = 22500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1302186, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1302186 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1312082, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1312082 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1372111, 40500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1372111 AND price = 40500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1332009, 44500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1332009 AND price = 44500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1432110, 62500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1432110 AND price = 62500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1442001, 62500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1442001 AND price = 62500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1322116, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1322116 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1332162, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1332162 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1422083, 47500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1422083 AND price = 47500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1452141, 152500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1452141 AND price = 152500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1462129, 32500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1462129 AND price = 32500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1302068, 352500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1302068 AND price = 352500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1472150, 22500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1472150 AND price = 22500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1402123, 152500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1402123 AND price = 152500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1412081, 47500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1412081 AND price = 47500 AND tokenitemid = 0 AND tokenprice = 0);
-- 9031015 Angelica (Alchemy Recipe Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512000, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512000 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512001, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512001 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512002, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512002 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512003, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512003 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512008, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512008 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512009, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512009 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512010, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512010 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512011, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512011 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512016, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512016 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512017, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512017 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512018, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512018 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512019, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512019 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512024, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512024 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512025, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512025 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512026, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512026 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512027, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512027 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512032, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512032 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512033, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512033 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512034, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512034 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512037, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512037 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512038, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512038 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512039, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512039 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512069, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512069 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512070, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512070 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512071, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512071 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512072, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512072 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512073, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512073 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512074, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512074 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512075, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512075 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512076, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512076 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512077, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512077 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512078, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512078 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512079, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512079 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512080, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512080 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512081, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512081 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512082, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512082 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512083, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512083 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512084, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512084 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512085, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512085 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512086, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512086 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512087, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512087 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512088, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512088 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512139, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512139 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512140, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512140 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512141, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512141 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512142, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512142 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512143, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512143 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512144, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512144 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512145, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512145 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512146, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512146 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512147, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512147 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512148, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512148 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512159, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512159 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512160, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512160 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512161, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512161 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512162, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512162 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512163, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512163 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512164, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512164 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512165, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512165 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512166, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512166 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512167, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512167 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512168, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512168 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512179, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512179 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512180, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512180 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512181, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512181 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512182, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512182 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512183, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512183 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512184, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512184 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512185, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512185 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512186, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512186 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512187, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512187 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512188, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512188 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512199, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512199 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512200, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512200 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512201, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512201 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512202, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512202 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512203, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512203 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512204, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512204 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512205, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512205 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512206, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512206 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512207, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512207 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512208, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512208 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512219, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512219 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512220, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512220 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512221, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512221 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512222, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512222 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512223, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512223 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512224, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512224 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512225, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512225 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512226, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512226 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512227, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512227 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512228, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512228 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512239, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512239 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512240, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512240 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512241, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512241 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512242, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512242 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512243, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512243 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512249, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512249 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512250, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512250 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512251, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512251 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512252, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512252 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512253, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512253 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512262, 2000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512262 AND price = 2000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512263, 5000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512263 AND price = 5000000 AND tokenitemid = 0 AND tokenprice = 0);
-- 9075110 Vendor-C2 (Coin Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132116, 0, 4310060, 200, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132116 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132117, 0, 4310060, 400, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132117 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132118, 0, 4310060, 600, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132118 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 600);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132119, 0, 4310060, 800, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132119 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 800);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132120, 0, 4310060, 1000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132120 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132121, 0, 4310060, 1500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132121 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132122, 0, 4310060, 2000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132122 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132146, 0, 4310060, 400, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132146 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132147, 0, 4310060, 800, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132147 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 800);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132148, 0, 4310060, 1000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132148 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132149, 0, 4310060, 1500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132149 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132150, 0, 4310060, 2000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132150 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001515, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001515 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001516, 400, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001516 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001517, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001517 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001519, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001519 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001520, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001520 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001000, 3200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001000 AND price = 3200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001001, 2300, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001001 AND price = 2300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001002, 4000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001002 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2020012, 4500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2000036, 5600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2000036 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2020045, 8100, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2020045 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2020046, 10200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2020046 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2030000, 400, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2060000, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2061000, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2060001, 10, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2060001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2061001, 10, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2061001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2720000, 20000000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2720000 AND price = 20000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2070000, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2070000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2330000, 600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2330000 AND price = 600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604000, 0, 4310060, 150, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604000 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 150);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604001, 0, 4310060, 300, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604001 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 300);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604002, 0, 4310060, 50, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604002 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604003, 0, 4310060, 80, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604003 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 80);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604004, 0, 4310060, 100, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604004 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604005, 0, 4310060, 150, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604005 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 150);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602100, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602100 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602101, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602101 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602102, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602102 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602103, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602103 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602104, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602104 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602105, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602105 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602106, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602106 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602107, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602107 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602108, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602108 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602108, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602108 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604011, 0, 4310060, 600, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604011 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 600);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604006, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604006 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604007, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604007 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604009, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604009 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604010, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604010 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600000, 15000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600000 AND price = 15000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600001, 30000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600001 AND price = 30000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600002, 60000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600002 AND price = 60000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600003, 100000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600003 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600004, 150000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600004 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600005, 200000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600005 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600100, 16000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600100 AND price = 16000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600101, 36000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600101 AND price = 36000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600102, 58000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600102 AND price = 58000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600103, 76000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600103 AND price = 76000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600104, 102000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600104 AND price = 102000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600200, 90000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600200 AND price = 90000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600201, 120000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600201 AND price = 120000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600202, 180000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600202 AND price = 180000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600203, 360000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600203 AND price = 360000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602000, 130000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602000 AND price = 130000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602001, 300000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602001 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602002, 560000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602002 AND price = 560000 AND tokenitemid = 0 AND tokenprice = 0);
-- 9010037 Randolf (Legends Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022794, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022794 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022795, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022795 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022796, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022796 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022797, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022797 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022798, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022798 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022799, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022799 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2431232, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2431232 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2431233, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2431233 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021013, 0, 4310098, 3, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021013 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021014, 0, 4310098, 5, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021014 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021015, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021015 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021016, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021016 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2003048, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2003048 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2003049, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2003049 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510171, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510171 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510172, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510172 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510176, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510176 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510240, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510240 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2615000, 0, 4310098, 40, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2615000 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 40);
-- 3003537 Dimo (Arachno Coin Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1542117, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1542117 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1552119, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1552119 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1252098, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1252098 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 2048918, 0, 4310249, 4, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 2048918 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 4310217, 0, 4310249, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 4310217 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1212120, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1212120 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1272017, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1272017 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1282017, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1282017 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1292018, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1292018 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1292018, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1292018 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1213018, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1213018 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1222113, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1222113 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1232113, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1232113 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1242121, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1242121 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1262039, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1262039 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1302343, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1302343 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1312203, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1312203 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1322255, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1322255 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1332279, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1332279 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1342104, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1342104 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1362140, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1362140 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1372228, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1372228 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1382265, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1382265 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1402259, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1402259 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1412181, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1412181 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1422189, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1422189 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1432218, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1432218 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1442274, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1442274 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1452257, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1452257 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1462243, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1462243 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1472265, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1472265 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1482221, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1482221 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1492235, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1492235 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1522143, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1522143 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1532150, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1532150 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1582023, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1582023 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004808, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004808 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004809, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004809 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004810, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004810 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004811, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004811 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004812, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004812 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053063, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053063 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053064, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053064 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053065, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053065 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053066, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053066 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053067, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053067 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152196, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152196 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152197, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152197 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152198, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152198 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152199, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152199 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152200, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152200 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
-- 2160000 Murt
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1252087, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1252087 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1542102, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1542102 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1552103, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1552103 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 2048913, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 2048913 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1212102, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1212102 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1222096, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1222096 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1232096, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1232096 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1242103, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1242103 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1262014, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1262014 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1302316, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1302316 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1312186, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1312186 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1322237, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1322237 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1332261, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1332261 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1362122, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1362122 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1372208, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1372208 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1382246, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1382246 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1402237, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1402237 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1412179, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1412179 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1422186, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1422186 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1432201, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1432201 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1442255, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1442255 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1452239, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1452239 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1462226, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1462226 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1472248, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1472248 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1482203, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1482203 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1492213, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1492213 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1522125, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1522125 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1532131, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1532131 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1582014, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1582014 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004234, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004234 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004235, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004235 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004236, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004236 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004237, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004237 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004238, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004238 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052804, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052804 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052805, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052805 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052806, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052806 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052807, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052807 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052808, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052808 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072972, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072972 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072973, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072973 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072974, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072974 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072975, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072975 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072976, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072976 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082613, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082613 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082614, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082614 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082615, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082615 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082616, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082616 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082617, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082617 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102713, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102713 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102714, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102714 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102715, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102715 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102716, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102716 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102717, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102717 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
-- 2400003 Cello (Quartermaster)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2431935, 0, 4310085, 5, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2431935 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2431936, 0, 4310085, 5, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2431936 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1190900, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1190900 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2550016, 0, 4310085, 30, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2550016 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 30);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1003911, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1003911 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1052632, 0, 4310085, 150, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1052632 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 150);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1082535, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1082535 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1072837, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1072837 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1102594, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1102594 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1132232, 0, 4310085, 70, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1132232 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 70);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1152138, 0, 4310085, 70, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1152138 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 70);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048913, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048913 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048906, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048906 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048903, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048903 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048904, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048904 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2431854, 0, 4310085, 10, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2431854 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4033923, 0, 4310085, 5, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4033923 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510704, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510704 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510705, 0, 4310085, 700, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510705 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 700);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510706, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510706 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510707, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510707 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510708, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510708 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2511178, 0, 4310085, 400, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2511178 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2511179, 0, 4310085, 400, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2511179 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4021040, 0, 4310085, 10, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4021040 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1003419, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1003419 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1022073, 0, 4310085, 200, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1022073 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1032101, 0, 4310085, 300, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1032101 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 300);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1122118, 0, 4310085, 400, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1122118 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1003267, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1003267 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1022123, 0, 4310085, 300, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1022123 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 300);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048908, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048908 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024004, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024004 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024005, 300, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024005 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024006, 500, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024006 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024007, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024007 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025000, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025000 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025001, 5000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025001 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025002, 10000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025002 AND price = 10000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025003, 50000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025003 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4340000, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4340000 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024000, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024000 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024001, 300, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024001 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024002, 500, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024003, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024003 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025004, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025004 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025005, 200, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025005 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025006, 400, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025006 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025007, 800, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025007 AND price = 800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025008, 50, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025008 AND price = 50 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025009, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025009 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025010, 200, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025010 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025011, 400, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025011 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4021017, 5000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4021017 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
-- 3001217 Mono (Weapon Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002022, 2100, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002022 AND price = 2100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002019, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002019 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022190, 3000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022190 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001500, 50, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001500 AND price = 50 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020045, 8100, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020045 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001513, 2300, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001513 AND price = 2300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022192, 600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022192 AND price = 600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002020, 2800, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002020 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001512, 3200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001512 AND price = 3200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020013, 5600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001503, 200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001503 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022191, 1000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022191 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001514, 4000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001514 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002025, 1500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002025 AND price = 1500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002018, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002018 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002016, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002016 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002021, 2800, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002021 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2030000, 400, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2061000, 2, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2061000 AND price = 2 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001501, 160, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001501 AND price = 160 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020012, 4500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022177, 620, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022177 AND price = 620 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002026, 3800, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002026 AND price = 3800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022195, 150000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022195 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002017, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002017 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020046, 10200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020046 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2060000, 2, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2060000 AND price = 2 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2010001, 106, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2010001 AND price = 106 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022189, 1000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022189 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022174, 320, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022174 AND price = 320 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002024, 1500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002024 AND price = 1500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1272000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1272000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1272001, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1272001 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1282000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1282000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1282001, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1282001 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
-- 9001127 Spiegelmann (Heroes Coin Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017006, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017006 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017007, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017007 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017008, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017008 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017009, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017009 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017010, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017010 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017011, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017011 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017012, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017012 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017013, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017013 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017014, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017014 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017015, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017015 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017016, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017016 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000271, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000271 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000272, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000272 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000273, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000273 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000161, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000161 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000162, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000162 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000163, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000163 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000249, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000249 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000250, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000250 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000251, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000251 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 2434891, 0, 4310113, 200, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 2434891 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 2049116, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 2049116 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 2049601, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 2049601 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5064100, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5064100 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5064000, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5064000 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
-- 9330278 Sagacious Goemon (Totem Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1202089, 0, 4310075, 1550, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1202089 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1550);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1202090, 0, 4310075, 1550, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1202090 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1550);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1202091, 0, 4310075, 1550, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1202091 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1550);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1142856, 0, 4310075, 1200, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1142856 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1142783, 0, 4310075, 500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1142783 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1142784, 0, 4310075, 750, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1142784 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 750);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1162033, 0, 4310075, 500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1162033 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1162034, 0, 4310075, 750, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1162034 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 750);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1162035, 0, 4310075, 1250, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1162035 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1250);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 2433209, 0, 4310075, 500, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 2433209 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 2433210, 0, 4310075, 750, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 2433210 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 750);
-- 9001174 Cosmic Vending Machine (13th Anniversary Galaxy Star Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436945, 0, 0, 0, 16180, 50, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436945 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436946, 0, 0, 0, 16180, 250, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436946 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436947, 0, 0, 0, 16180, 250, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436947 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436948, 0, 0, 0, 16180, 250, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436948 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2630756, 0, 0, 0, 16180, 900, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2630756 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2433384, 0, 0, 0, 16180, 100, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2433384 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437020, 0, 0, 0, 16180, 900, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437020 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2432970, 0, 0, 0, 16180, 100, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2432970 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2049705, 0, 0, 0, 16180, 100, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2049705 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2710000, 0, 0, 0, 16180, 5, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2710000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2049500, 0, 0, 0, 16180, 20, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2049500 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436942, 0, 0, 0, 16180, 300, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436942 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436951, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436951 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436952, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436952 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015847, 0, 0, 0, 16180, 1000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015847 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015642, 0, 0, 0, 16180, 1000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015642 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3010964, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3010964 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437021, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437021 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437022, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437022 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437023, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437023 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015603, 0, 0, 0, 16180, 1000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015603 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015157, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015157 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2435719, 0, 0, 0, 16180, 100, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2435719 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350004, 0, 0, 0, 16180, 80, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350004 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350005, 0, 0, 0, 16180, 80, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350005 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350006, 0, 0, 0, 16180, 80, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350006 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436954, 0, 0, 0, 16180, 10, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436954 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2430909, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2430909 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2431789, 0, 0, 0, 16180, 30, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2431789 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2431790, 0, 0, 0, 16180, 30, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2431790 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350000, 0, 0, 0, 16180, 3000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2028349, 0, 0, 0, 16180, 100, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2028349 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 9010038 Lucia (Legends Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 4000313, 0, 0, 0, 14693, 10000, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 4000313 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680047, 0, 0, 0, 14693, 29900, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680047 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680048, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680048 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5062024, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5062024, 0, 0, 0, 14693, 100000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5062024, 0, 0, 0, 14693, 500000, 1, 0, 0, 0, 50, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1672020, 0, 0, 0, 14693, 250000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1672020 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2049610, 0, 0, 0, 14693, 1000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2049610 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2049610, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2049610 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2048212, 0, 0, 0, 14693, 50000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2048212 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2048212, 0, 0, 0, 14693, 500000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2048212 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5220100, 0, 0, 0, 14693, 1000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5220100 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5220100, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5220100 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5220100, 0, 0, 0, 14693, 100000, 1, 0, 0, 0, 100, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5220100 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1122210, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1122210 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1132183, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1132183 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1152101, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1152101 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1112757, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1112757 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2048331, 0, 0, 0, 14693, 150000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2048331 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5530360, 0, 0, 0, 14693, 50000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5530360 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680770, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680770 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680770, 0, 0, 0, 14693, 99000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680770 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680770, 0, 0, 0, 14693, 900000, 1, 0, 0, 0, 100, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680770 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2049506, 0, 0, 0, 14693, 50000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2049506 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5552000, 0, 0, 0, 14693, 30000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5552000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5553000, 0, 0, 0, 14693, 30000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5553000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2432946, 0, 0, 0, 14693, 20000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2432946 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 9010040 Conor (Renegades Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5211121, 0, 0, 0, 14434, 2, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5211121 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5360056, 0, 0, 0, 14434, 2, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5360056 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 1202236, 0, 0, 0, 14434, 3, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 1202236 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5180000, 0, 0, 0, 14434, 25, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5180000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5062024, 0, 0, 0, 14434, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 2431936, 0, 0, 0, 14434, 5, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 2431936 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 2431935, 0, 0, 0, 14434, 2, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 2431935 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 9201450 Matilda (Purveyor of Useful Items)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3010043, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3010043 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3010119, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3010119 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3010431, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3010431 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3015659, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3015659 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3015606, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3015606 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3015581, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3015581 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2431965, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2431965 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2432846, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2432846 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2432640, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2432640 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2432749, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2432749 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433715, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433715 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433775, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433775 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433776, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433776 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433830, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433830 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433831, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433831 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433913, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433913 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433981, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433981 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022730, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022730 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022731, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022731 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022729, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022729 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022728, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022728 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2049152, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2049152 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2049611, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2049611 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 1400002 Mathias (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2161013 Glibber (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2231100 Cliff (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2301001 Nathania (Merchant/Alchemist)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2400022 Oko (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3001341 Sanctuary Merchant (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3002011 Swindle (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3005581 Bonnie (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006336 Chest of Wisdom (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006337 Chest of Wisdom (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006436 Cygnus Supplier (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006549 Kalifa (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006661 Tarrah (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006842 Phantasmal Priestess (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9310599 Xuzhu (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9400469 Fenton (Shopkeep)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9401294 Xiang Xiang (Tavern Keeper)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9401295 Ju Ji (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9401340 Freete (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9402236 Starkeeper Eren (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);

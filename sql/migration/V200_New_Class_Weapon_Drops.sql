-- Level-based monster drops for the Ren, Sia Astelle, Mo Xuan and Khali weapons (the levelled weapons that drop
-- from monsters in GMS: 10-110, Briser 120, Jaihin 130, Utgard 140), the same Briser/Jaihin mob drops the other
-- class weapons have, and the class secondaries Neville sells that his shop was missing.

INSERT IGNORE INTO equip_drops (id, job, level) VALUES
(1215001, 'WARRIOR', 10),
(1215002, 'WARRIOR', 30),
(1215003, 'WARRIOR', 50),
(1215004, 'WARRIOR', 60),
(1215005, 'WARRIOR', 70),
(1215006, 'WARRIOR', 80),
(1215007, 'WARRIOR', 90),
(1215008, 'WARRIOR', 100),
(1215010, 'WARRIOR', 110),
(1215013, 'WARRIOR', 120),
(1215014, 'WARRIOR', 130),
(1215016, 'WARRIOR', 140),
(1253000, 'MAGICIAN', 10),
(1253001, 'MAGICIAN', 30),
(1253002, 'MAGICIAN', 50),
(1253003, 'MAGICIAN', 60),
(1253004, 'MAGICIAN', 70),
(1253005, 'MAGICIAN', 80),
(1253006, 'MAGICIAN', 90),
(1253007, 'MAGICIAN', 100),
(1253008, 'MAGICIAN', 110),
(1253010, 'MAGICIAN', 120),
(1253012, 'MAGICIAN', 130),
(1253014, 'MAGICIAN', 140),
(1403001, 'PIRATE', 30),
(1403002, 'PIRATE', 50),
(1403003, 'PIRATE', 60),
(1403004, 'PIRATE', 70),
(1403005, 'PIRATE', 80),
(1403006, 'PIRATE', 90),
(1403007, 'PIRATE', 100),
(1403008, 'PIRATE', 110),
(1403010, 'PIRATE', 120),
(1403012, 'PIRATE', 130),
(1403014, 'PIRATE', 140),
(1404000, 'THIEF', 10),
(1404001, 'THIEF', 30),
(1404002, 'THIEF', 50),
(1404003, 'THIEF', 60),
(1404004, 'THIEF', 70),
(1404005, 'THIEF', 80),
(1404006, 'THIEF', 90),
(1404007, 'THIEF', 100),
(1404008, 'THIEF', 110),
(1404010, 'THIEF', 120),
(1404012, 'THIEF', 130),
(1404014, 'THIEF', 140);

INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT n.mobid, n.itemid, 40, 1, 1 FROM (
    SELECT 8145101 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1404012 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1404012 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1404012 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1404012 AS itemid
) n
WHERE NOT EXISTS (SELECT 1 FROM mob_drops d WHERE d.mobid = n.mobid AND d.itemid = n.itemid);

INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, questexid,
    questexkey, questexvalue, itemperiod, levellimited, showlevmin, showlevmax, questid, tabindex, worldblock,
    potentialgrade, buylimit, quantity, unitprice, maxperslot, discountperc)
SELECT 9072100, n.itemid, n.price, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 1, 0, 1, 0 FROM (
    SELECT 1352802 AS itemid, 330000 AS price
    UNION ALL SELECT 1352860 AS itemid, 3000 AS price
    UNION ALL SELECT 1352861 AS itemid, 79000 AS price
    UNION ALL SELECT 1352862 AS itemid, 330000 AS price
    UNION ALL SELECT 1352863 AS itemid, 580000 AS price
    UNION ALL SELECT 1352870 AS itemid, 3000 AS price
    UNION ALL SELECT 1352871 AS itemid, 79000 AS price
    UNION ALL SELECT 1352872 AS itemid, 330000 AS price
    UNION ALL SELECT 1352873 AS itemid, 580000 AS price
    UNION ALL SELECT 1354030 AS itemid, 3000 AS price
    UNION ALL SELECT 1354031 AS itemid, 80000 AS price
    UNION ALL SELECT 1354032 AS itemid, 330000 AS price
    UNION ALL SELECT 1354033 AS itemid, 580000 AS price
    UNION ALL SELECT 1354040 AS itemid, 3000 AS price
    UNION ALL SELECT 1354041 AS itemid, 80000 AS price
    UNION ALL SELECT 1354042 AS itemid, 330000 AS price
    UNION ALL SELECT 1354043 AS itemid, 580000 AS price
) n
WHERE NOT EXISTS (SELECT 1 FROM shopitems s WHERE s.shopid = 9072100 AND s.itemid = n.itemid);

-- Verus Hilla (maplestorywiki.net, GMS): both modes drop the Daybreak Pendant; Normal a Red Jade Boss Ring Box, Hard a
-- Black Jade Boss Ring Box. Normal is 8880450, Hard 8880410. Only adds rows that aren't there.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT t.m, t.i, t.c, 1, 1 FROM (SELECT 8880450 AS m, 1122443 AS i, 200 AS c UNION SELECT 8880410, 1122443, 200
    UNION SELECT 8880450, 2028408, 300 UNION SELECT 8880410, 2028409, 300) t
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = t.m AND x.itemid = t.i);

-- Boss rewards (maplestorywiki.net drop lists, v265 monster ids). Copies only into monsters that have no drops yet,
-- so running it again changes nothing.

-- Will: phase 3 Will drops nothing in GMS; the Lost Light Mirror (Easy 8950113, Normal 8950114, Hard 8950115) drops
-- everything. Easy has no equipment. Twilight Mark from Normal and Hard.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8950114, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880342
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8950114) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8950115, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880302
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8950115) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8950113, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880342 AND itemid >= 2000000
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8950113) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT m, 1012757, 100, 1, 1 FROM (SELECT 8950114 AS m UNION SELECT 8950115) t
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = t.m AND x.itemid = 1012757);
DELETE FROM mob_drops WHERE mobid IN (8880342, 8880302, 8880362);

-- Verus Hilla Normal is 8880450 now (8880405 was the old Normal set).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8880450, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880405
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8880450) x);

-- Chosen Seren: Normal's chest is 8880644 and Extreme's 8880664 (8880614 is Hard's). Mitra's Rage (emblems) from all.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8880644, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880614
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8880644) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8880664, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880614
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8880664) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT c.m, e.i, 50, 1, 1 FROM (SELECT 8880644 AS m UNION SELECT 8880614 UNION SELECT 8880664) c
CROSS JOIN (SELECT 1190555 AS i UNION SELECT 1190556 UNION SELECT 1190557 UNION SELECT 1190558 UNION SELECT 1190559) e
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = c.m AND x.itemid = e.i);

-- Chaos Horntail (8810122) had no drops: Normal's, plus the Chaos Horntail Necklace.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8810122, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8810018
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8810122) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8810122, 1122076, 2000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = 8810122 AND x.itemid = 1122076);

-- Chaos Gloom (8644655) had no drops: Normal's, plus Endless Terror.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644655, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8644650
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8644655) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644655, 1113306, 50, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = 8644655 AND x.itemid = 1113306);

-- The v265 Lotus (staff test): its reward cores get the old fight's drops.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8881103, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8950002
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8881103) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8881153, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8950102
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8881153) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8881203, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8950102
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8881203) x);

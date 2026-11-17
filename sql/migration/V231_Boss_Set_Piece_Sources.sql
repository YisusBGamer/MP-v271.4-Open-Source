-- Boss set pieces as the client's Contents Guide lists them per boss and difficulty (Etc.wz ContentsGuide.img
-- boss/*/extendReward). Rates for the added rows follow the other newest boss pieces (50 = 0.5%).

-- Total Control: Extreme Lotus
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8881203, 1672095, 50, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881203 AND itemid = 1672095);

-- Immortal Legacy (Brilliant Boss Set): Hard and Extreme First Adversary
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT m.mobid, 1143471, 50, 1, 1 FROM (SELECT 8881775 AS mobid UNION ALL SELECT 8881805) m
WHERE NOT EXISTS (SELECT 1 FROM mob_drops d WHERE d.mobid = m.mobid AND d.itemid = 1143471);

-- Twilight Mark: Normal and Hard Lucid (Will already drops it), at Will's rate
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT m.mobid, 1012757, 100, 1, 1 FROM (SELECT 8880167 AS mobid UNION ALL SELECT 8880177) m
WHERE NOT EXISTS (SELECT 1 FROM mob_drops d WHERE d.mobid = m.mobid AND d.itemid = 1012757);

-- Mitra's Rage: Hard and Extreme Seren give the selection box; Normal Seren gives none
DELETE FROM mob_drops WHERE itemid BETWEEN 1190555 AND 1190559 AND mobid IN (8880614, 8880615, 8880644, 8880664);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT m.mobid, 2633927, 50, 1, 1 FROM (SELECT 8880614 AS mobid UNION ALL SELECT 8880615 UNION ALL SELECT 8880664) m
WHERE NOT EXISTS (SELECT 1 FROM mob_drops d WHERE d.mobid = m.mobid AND d.itemid = 2633927);

-- Cursed Spellbooks: Hard Will gives the selection box (already in its table), not the books themselves
DELETE FROM mob_drops WHERE mobid = 8950115 AND itemid BETWEEN 1162080 AND 1162083;

-- Damaged Black Heart: Hard and Extreme Lotus only, not Normal
DELETE FROM mob_drops WHERE mobid = 8881103 AND itemid = 4001877;

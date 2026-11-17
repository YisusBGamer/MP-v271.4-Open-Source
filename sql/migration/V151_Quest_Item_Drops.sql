-- Quest items nothing dropped:
-- Stained Soul (4033117) for Mihile's 20758, from Arkarium (both versions); Snake Leather Bag (4033231) for 25444,
-- from the Serpents near Edelstein.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8860000, 4033117, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860000 AND itemid = 4033117);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8860005, 4033117, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860005 AND itemid = 4033117);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1150002, 4033231, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1150002 AND itemid = 4033231);

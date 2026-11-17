-- Kenta's Freezer Leather (4000592) for 22404, from Kenta's Freezer (Sea of Silence 923030000), which dropped nothing.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9001025, 4000592, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001025 AND itemid = 4000592);

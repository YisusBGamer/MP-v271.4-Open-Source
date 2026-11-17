-- Erel Light story quest items, as GMS drops them.
-- Darkness Investigation (61702) - 9602659 | Black Shadow Mound
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9602659, 4037491, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9602659 AND itemid = 4037491);
-- Black Shadow Search (61706) - 9602663 | Black Shadow Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9602663, 4037492, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9602663 AND itemid = 4037492);

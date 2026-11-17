-- Stone Origin Droplet (4001889, "Esferan Erda"): 9 and a Stone Cobweb Droplet (Will) make an Arachno Coin at
-- Kanto in Esfera Base Camp, but no monster dropped it. From the Esfera hunting monsters (450007010-450007230:
-- 8644500-8644509) at the Arcane River Droplet Stone's rate (2%).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644500, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644500 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644501, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644501 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644502, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644502 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644503, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644503 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644504, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644504 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644505, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644505 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644506, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644506 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644507, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644507 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644508, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644508 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644509, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644509 AND itemid = 4001889);

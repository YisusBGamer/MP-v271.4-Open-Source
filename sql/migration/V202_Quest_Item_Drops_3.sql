-- Quest items that no drop gave (same format as V115/V190). Each insert is skipped if the drop already exists.
-- [Aqua Road] The Bottled-up Letter (3072) starts with an SOS Letter in hand: Aqua Road's Bubble Fish, Flower Fish,
-- Krip and Mask Fish around the Aquarium.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230109, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230109 AND itemid = 4031209);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230200, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230200 AND itemid = 4031209);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3000006, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3000006 AND itemid = 4031209);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230104, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230104 AND itemid = 4031209);
-- Evan: Making a Saddle (22404) - Kenta's Seruf in the Sea of Silence (923030000) | Seruf Pearl
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9001026, 4032474, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001026 AND itemid = 4032474);
-- Switching Attributes (6225) - the Elemental Thanatos in the Room of Thanatos (922020100) | Icy Soul Rock
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300086, 4031470, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300086 AND itemid = 4031470);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300100, 4031470, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300100 AND itemid = 4031470);

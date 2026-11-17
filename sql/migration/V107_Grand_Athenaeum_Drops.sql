-- Grand Athenaeum episode quest items that no monster dropped. Quest items only drop for characters that have
-- the quest. Each insert is skipped if the drop already exists.
-- Aqua Road White Pearl (Clutching Pearls 1, 32613)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230019, 4033962, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230019 AND itemid = 4033962);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230020, 4033962, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230020 AND itemid = 4033962);
-- Aqua Road Green Pearl (32614)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230022, 4033963, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230022 AND itemid = 4033963);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230023, 4033963, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230023 AND itemid = 4033963);
-- Aqua Road Black Pearl (32615)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230024, 4033964, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230024 AND itemid = 4033964);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230025, 4033964, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230025 AND itemid = 4033964);
-- Glowing Antler (Medicine Maker 1, 32620)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230027, 4033966, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230027 AND itemid = 4033966);
-- Golden Peach (32621)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230029, 4033967, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230029 AND itemid = 4033967);
-- Ginseng Concentrate (32622)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230028, 4033968, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230028 AND itemid = 4033968);
-- Flower of Fate (Willpower Flower, 32624)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8140000, 4033970, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8140000 AND itemid = 4033970);
-- Omen Piece (32638)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230047, 4033971, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230047 AND itemid = 4033971);
-- Dry Firewood (Winter Bard 1, 32672)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230050, 4036137, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230050 AND itemid = 4036137);
-- Resin (Winter Bard 2, 32673)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230051, 4036138, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230051 AND itemid = 4036138);
-- Mountain Turtle Egg (32685)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230059, 4036139, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230059 AND itemid = 4036139);

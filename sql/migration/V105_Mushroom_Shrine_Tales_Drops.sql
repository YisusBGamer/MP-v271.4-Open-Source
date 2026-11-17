-- Mushroom Shrine Tales (Zipangu) quest items that no monster dropped. Quest items only drop for characters
-- that have the quest. Each insert is skipped if the drop already exists.
-- Lost Specter's Handkerchief (A Deal with the Dead 58743) and The Gospel of Nue (The Bachelor's Request 58745): Lost Specters
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400037, 4034856, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400037 AND itemid = 4034856);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400037, 4034858, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400037 AND itemid = 4034858);
-- Dusk Viper Venom (Unholy Water 58737): Dusk Vipers
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400039, 4034859, 10000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400039 AND itemid = 4034859);
-- Stompin' Rock (The Unusual Technique 58763): Stone Golems at the Rock Pile
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400074, 4034873, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400074 AND itemid = 4034873);
-- Sakura's Treasure (Sakura's Request 58766): the Oni at the hideout
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400031, 4034872, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400031 AND itemid = 4034872);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400032, 4034872, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400032 AND itemid = 4034872);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400033, 4034872, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400033 AND itemid = 4034872);

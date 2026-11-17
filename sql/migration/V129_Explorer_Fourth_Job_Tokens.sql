-- Explorer 4th job (Harmonia's "A True Explorer" quests 1451/1453/1455/1457/1459): the Heroic Pentagon and Heroic Star
-- (and the other classes' 4031511/4031512) dropped only from the "Herald of Heroes" copies of Manon and Griffey
-- (9001043/9001044), which no map spawns, so no Explorer could reach 4th job. As on the official servers, the real
-- Manon (8180000) and Griffey (8180001) of Leafre drop them. They are quest items: they only drop for a character
-- whose quest needs them (Drop.java). Same format as V95-V99.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8180000, 4031343, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8180000 AND itemid = 4031343);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8180000, 4031511, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8180000 AND itemid = 4031511);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8180001, 4031344, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8180001 AND itemid = 4031344);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8180001, 4031512, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8180001 AND itemid = 4031512);

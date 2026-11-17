-- Class story quest items that no monster dropped, so their quests couldn't be finished. The mobs come
-- from each quest's own text in Quest.wz (QuestInfo.img). Same format as V89: quest items only drop for characters
-- that have the quest; each insert is skipped if the drop already exists.
-- Oda Scout | Familiar Seal Case (57110 Counterintelligence)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421511, 4033306, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421511 AND itemid = 4033306);
-- Oda Warrior | Mysterious Foxtail (57118 Samurai Skirmish 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421514, 4033307, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421514 AND itemid = 4033307);
-- Peach Monkey | Oda Clan Seal (57146 Tae Soo and You)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130207, 4033309, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130207 AND itemid = 4033309);
-- Cute Reindeer | Oda Clan Seal (57146 Tae Soo and You)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120505, 4033309, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120505 AND itemid = 4033309);
-- Sage Cat | Volatile Oda Clan Seal (57148 Mu Lung Mojo)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130209, 4033310, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130209 AND itemid = 4033310);
-- Puppeteer | Puppeteer Document (21731 Eliminate the Puppeteer!, Aran: the document he drops while running away)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300344, 4032322, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300344 AND itemid = 4032322);
-- Unidentified Assailant | Cloth Scrap (20785 A Truth So Cruel, Mihile)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9001056, 4033222, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001056 AND itemid = 4033222);

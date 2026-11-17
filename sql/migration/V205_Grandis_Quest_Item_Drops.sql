-- Grandis quest items that no monster dropped (same format as V204).
-- The Button and the Four Treasures of the Study (1) (38408): Broken Fragment (4037167) from the entranced
-- researchers in 410007035 (Harsh Winter Researcher 8645372), as the quest says.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645372, 4037167, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645372 AND itemid = 4037167);
-- For a New Start (38714): Warrior Emblem - Ring of Cycles (4037200) from the Specters of Among Tainted Trees
-- 1-3 (410007643-645: Shadowy Black Panther 8645424, Abyssal Hunter 8645425).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645424, 4037200, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645424 AND itemid = 4037200);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645425, 4037200, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645425 AND itemid = 4037200);

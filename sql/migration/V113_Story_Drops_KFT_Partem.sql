-- Korean Folk Town (Lv. 125) and Partem Ruins quest items that no monster dropped, which blocked their stories.
-- Monsters come from each quest's own text in Quest.wz (QuestInfo.img). Quest items only show up for characters
-- that have the quest. Each insert is skipped if the drop already exists.
-- Rice Flour | Moon Bunny (30418 [Korean Folk Town] No More Gluttony)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5100100, 4034634, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5100100 AND itemid = 4034634);
-- Rose | Dark Axe Stump (30423 [Korean Folk Town] Preparing to Confess)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5100101, 4034639, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5100101 AND itemid = 4034639);
-- Very Thin Bark | Dark Axe Stump (30432 [Korean Folk Town] The Tree Cutter's Habits 1)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5100101, 4034650, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5100101 AND itemid = 4034650);
-- Sentsuma | Frenzied Ruins Sentinel (35972 [Partem Ruins] Faded Lettering)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300266, 4036533, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300266 AND itemid = 4036533);
-- Guardian's Soul Flame | Searing Ruins Guardian (35975 [Partem Ruins] Brazier Blaze)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300267, 4036534, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300267 AND itemid = 4036534);

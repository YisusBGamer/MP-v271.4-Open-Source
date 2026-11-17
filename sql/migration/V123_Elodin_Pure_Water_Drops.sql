-- Elodin, the Shimmer Songbird's Small Bottle quests: "Hunt Spring Sprites in the Twilit Forest for Pure Water"
-- (Quest.wz). No monster dropped Pure Water. Blue Spring Sprite (3501108) and Deep Spring Sprite (3501109) live in the
-- Twilit Forest (101084100-101084300). Pure Water 4036503 is for Thirst Quenching (37169), 4036505 for Singing
-- Necessities 1 (37173), 2630243 for Another Chance! (37176; it fills the bottle when picked up). Each only shows up
-- for characters on its quest. Each insert is skipped if the drop exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3501108, 4036503, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501108 AND itemid = 4036503);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3501109, 4036503, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501109 AND itemid = 4036503);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3501108, 4036505, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501108 AND itemid = 4036505);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3501109, 4036505, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501109 AND itemid = 4036505);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3501108, 2630243, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501108 AND itemid = 2630243);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3501109, 2630243, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501109 AND itemid = 2630243);

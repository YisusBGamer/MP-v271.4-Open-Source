-- [Reverse City] questline items were never added to the drop tables, so the gathering quests could not be finished.
-- Quest items only drop for characters that have the quest. Each insert is skipped if the drop already exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8641051, 4036631, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641051 AND itemid = 4036631); -- Erda Rat | Blue Rat Meat (37604)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8641052, 4036632, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641052 AND itemid = 4036632); -- Monto | Purple Rat Meat (37606)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8641054, 4036633, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641054 AND itemid = 4036633); -- Seeker T-Drone Model B | T-Boy's Research Notes (37610)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8641055, 4036634, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641055 AND itemid = 4036634); -- Combat T-Drone Model A | Drone Part (37612)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8641055, 4036635, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641055 AND itemid = 4036635); -- Combat T-Drone Model A | Drone Part (37615)

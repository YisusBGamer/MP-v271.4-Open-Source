-- Evan's "Fixing the Fence" (22004): "Defeat some of the Stumps nearby and bring back some Thick Branches" (Quest.wz).
-- The farm's Stumps are the tutorial's own copy (9100045, map 100030300), which dropped nothing, so a level 5-7 Evan had
-- to go to Nautilus for Pigs. Quest item: drops only while the quest needs it.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9100045, 4032498, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9100045 AND itemid = 4032498);

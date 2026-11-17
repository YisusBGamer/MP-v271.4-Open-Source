-- Elodin, 'Spring Cleaning' (37164): "If you've used up all the Makeshift Broomsticks, you can make more with Hay from
-- Hay Bundles" (Quest.wz). The Hay that makes a broomstick (2630159, usable only during the quest) dropped from nothing.
-- Hay Bundle (3501103) lives in Ruenna's Home (101082100, 101082200). Each insert is skipped if the drop exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3501103, 2630159, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501103 AND itemid = 2630159);

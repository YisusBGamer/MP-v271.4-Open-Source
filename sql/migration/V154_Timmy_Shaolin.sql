-- Timmy (9310581) stands in no map's data; his quests (62036, 62037) say he is in this Shaolin Temple map.
INSERT INTO `npc` (`npcid`, `mapid`, `x`, `y`, `cy`, `rx0`, `rx1`, `fh`)
SELECT 9310581, 701220350, -150, 137, 137, -200, -100, 4 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `npc` WHERE `npcid` = 9310581 AND `mapid` = 701220350);

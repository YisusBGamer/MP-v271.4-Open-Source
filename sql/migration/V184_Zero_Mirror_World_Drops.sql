-- Zero's Mirror World side quests (41xxx): items the quest asks for from the Mirror World dungeon monsters, which
-- dropped nothing for them (quest audit 2026-10-24; monster picked by the quest's dungeon and the item's name)
--   41054 Cold Desert Nights 2: Lizard's Scarf (4000935)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600108, 4000935, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600108 AND itemid = 4000935);
--   41102 Reminiscing: Purple Clam Shell (4000969)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600216, 4000969, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600216 AND itemid = 4000969);
--   41105 Fresh Eggs: Octopus Ink (4000973)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600220, 4000973, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600220 AND itemid = 4000973);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600221, 4000973, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600221 AND itemid = 4000973);
--   41110 Good Luck Charm: Undead Ticket (4000960)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600206, 4000960, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600206 AND itemid = 4000960);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600207, 4000960, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600207 AND itemid = 4000960);
--   41150 Gathering Training Equipment: Straw Voodoo Doll (4009000)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600300, 4009000, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600300 AND itemid = 4009000);
--   41152 Fashionista Naran: Mr. Alli's Alligator Leather (4009022)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600322, 4009022, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600322 AND itemid = 4009022);
--   41157 Dieting: Oak Tree Acorn (4009006)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600306, 4009006, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600306 AND itemid = 4009006);
--   41200 Broken Patrol Robot: Guard Robot E's Memory Chip (4009032)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600405, 4009032, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600405 AND itemid = 4009032);
--   41216 Allen, Father of Robots: Android Parts (4009051)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600423, 4009051, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600423 AND itemid = 4009051);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600424, 4009051, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600424 AND itemid = 4009051);
--   41303 Reducing Production Costs 2: Robo Battery (4009083)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600605, 4009083, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600605 AND itemid = 4009083);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600606, 4009083, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600606 AND itemid = 4009083);
--   41312 Tools for the Magic Show: Colorful Hat (4009097)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600618, 4009097, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600618 AND itemid = 4009097);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600619, 4009097, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600619 AND itemid = 4009097);
--   41366 Meaning of Oblivion 2: Oblivion Helm (4009121)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600713, 4009121, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600713 AND itemid = 4009121);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600714, 4009121, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600714 AND itemid = 4009121);

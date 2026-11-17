-- Story quest items that no monster dropped (same format as V95-V97).
-- Cygnus Knights: The Lost Permit 3 (20848) - Tiguru | Ereve Investigation Permit pieces
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 100124, 4033671, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 100124 AND itemid = 4033671);
-- Cygnus Knights: The Lost Treasure (20850) - Master of Disguise | the lost treasure
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300743, 4033672, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300743 AND itemid = 4033672);
-- Forest of Peace: Helping Vieren (32640) - Neon Bat | Glowing Substance
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230026, 4033972, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230026 AND itemid = 4033972);
-- Forest of Peace: Helping Mars (32642) - Glowing Primary Rock | ore
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230008, 4033973, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230008 AND itemid = 4033973);
-- Gelimer Research Lab: Big Daddy's Stuff (32653) - Ore Muncher | Big Daddy's photo (one, after about 10)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230036, 4033975, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230036 AND itemid = 4033975);
-- Gelimer Research Lab: Prison Escape (32655) - AF Android | Security Memory Chip
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230037, 4033976, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230037 AND itemid = 4033976);
-- Gelimer Research Lab: Prison Escape (32655) - Broken DF Android | Detour Operator Device
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230038, 4033977, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230038 AND itemid = 4033977);

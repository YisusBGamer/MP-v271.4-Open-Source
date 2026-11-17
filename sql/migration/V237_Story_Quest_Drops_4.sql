-- Story quest items that no monster dropped, part 2 of 11-05 (same format as V236): Silent Crusade's Lv 190
-- quest maps (the Uncanny monsters the quests name), Heliseum's guardian keys and Specter Cores.
-- Silent Crusade: Gathering Medicinal Ingredients 1 (20966) - Uncanny Reindeer | Cute Reindeer Antlers
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645309, 4037308, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645309 AND itemid = 4037308);
-- Silent Crusade: Gathering Medicinal Ingredients 1 (20966) - Uncanny Bellflower | Bellflower Root
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645310, 4037309, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645310 AND itemid = 4037309);
-- Silent Crusade: Gathering Medicinal Ingredients 2 (20967) - Uncanny King Centipede | King Centipede's Mandible
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645324, 4037313, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645324 AND itemid = 4037313);
-- Silent Crusade: Her Request (20974) - Uncanny Tauromacis | Tauromacis Hoof
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645314, 4037310, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645314 AND itemid = 4037310);
-- Silent Crusade: Her Request (20974) - Uncanny Wild Kargo | Wild Kargo Eye
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645311, 4037311, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645311 AND itemid = 4037311);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645312, 4037311, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645312 AND itemid = 4037311);
-- Silent Crusade: Her Request (20974) - Uncanny Taurospear | Taurospear's Hoof
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645313, 4037315, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645313 AND itemid = 4037315);
-- Silent Crusade: Trapped Forever? (20979) - Uncanny Scorpion | Scorpion Stinger
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3645319, 4037312, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645319 AND itemid = 4037312);
-- Heliseum: Mad Scientist Treglow (38849) - Treglow | Treglow's Key
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880008, 4037227, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880008 AND itemid = 4037227);
-- Heliseum: Melancholy Victor (38852) - Victor | Victor's Key
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880006, 4037228, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880006 AND itemid = 4037228);
-- Heliseum: Melancholy Victor (38852) - Victor | Victor's Diary
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880006, 4037229, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880006 AND itemid = 4037229);
-- Heliseum: No Free Trade (38824) - Specters | Specter Core
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400106, 4037224, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4037224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4037224, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4037224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400108, 4037224, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400108 AND itemid = 4037224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400109, 4037224, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4037224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4037224, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4037224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400111, 4037224, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400111 AND itemid = 4037224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 4037224, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 4037224);

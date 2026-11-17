-- Know Your Enemy (31674, Angelic Buster): "Letters can only be obtained from Specter soldiers, not Specter Battle Hounds."
-- Nothing dropped the Specter Corps Letter (2430944). Specter soldiers 2400107-2400115 (Battle Hound 2400106 left out).
-- Each insert is skipped if the drop already exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400108, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400108 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400109, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400111, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400111 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400113, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400113 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400114, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400114 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400115, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400115 AND itemid = 2430944);

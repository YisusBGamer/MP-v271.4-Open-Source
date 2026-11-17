-- Mushroom Castle quest items that no monster dropped (same format as V95-V99).
-- 30061 Prisoners Needed - captured Vikings (the capture rope doesn't work here)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300102, 4034111, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300102 AND itemid = 4034111);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300103, 4034111, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300103 AND itemid = 4034111);
-- 30066 Violetta's Rescue - the keys
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300106, 4034112, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300106 AND itemid = 4034112);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300107, 4034112, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300107 AND itemid = 4034112);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300108, 4034112, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300108 AND itemid = 4034112);
-- 30068 Secret Captain's Quarters - puzzle pieces
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300106, 4034114, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300106 AND itemid = 4034114);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300107, 4034114, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300107 AND itemid = 4034114);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300108, 4034114, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300108 AND itemid = 4034114);

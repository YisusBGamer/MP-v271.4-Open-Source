-- Carcion story items nothing dropped (quest audit 2026-10-24):
--   38692 The Cause of the Infection: Mystery Infectious Agent (4037177) from the Despairing Hunter (8645423)
--   38706: Intact Infectious Agent (4037196) from the Knight and the Priest of the Source (8645426, 8645427)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645423, 4037177, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645423 AND itemid = 4037177);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645426, 4037196, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645426 AND itemid = 4037196);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645427, 4037196, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645427 AND itemid = 4037196);

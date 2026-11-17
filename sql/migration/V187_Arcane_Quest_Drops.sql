-- Arcane River story items nothing a player can reach dropped (quest audit 2026-10-24):
--   35612 [Moonbridge] Foggy Investigation: Wicked Soul Remnants (4036446) only dropped from the Soot Beast/Talon
--   copies of a closed area (8644622/8644623); now also from the Last Horizon deck's Soot Beast (8644614) and Soot Talon
--   (8644615)
--   37858 [Esfera] Collecting Erda 2: Erda Energy (4037360) from the Bellalis (8644503) around the quest's NPC
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644614, 4036446, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644614 AND itemid = 4036446);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644615, 4036446, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644615 AND itemid = 4036446);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644503, 4037360, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644503 AND itemid = 4037360);

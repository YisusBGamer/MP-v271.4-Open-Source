-- Class start quest items that no monster dropped (same format as V95-V99).
-- Cygnus Knights: Combat Technique (36458) - Training Tino | Tino Feather
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645270, 4036882, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645270 AND itemid = 4036882);
-- Lara: Kite Kicker (36205) - 10 kills of 8645225 | Lost Shoe
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645225, 4036781, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645225 AND itemid = 4036781);
-- Sia Astelle: Cosmic Silence (67276) - 9602449 | Cosmic Silence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9602449, 4009586, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9602449 AND itemid = 4009586);

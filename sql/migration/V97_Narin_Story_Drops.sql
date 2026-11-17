-- Lara's Narin story quest items that no monster dropped (same format as V95/V96).
-- Sprout Silkworm | Lost Shoe (36205 Kite Kicker)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645225, 4036781, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645225 AND itemid = 4036781);
-- Flower Field Bunny | Carrot Brooch (36208 Ladders are Chaos: one brooch, after about 15)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645227, 4036778, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645227 AND itemid = 4036778);
-- Flower Wing | Wrapped Toy (36214 A Toy for a Bowl: one toy, after about 20)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645226, 4036779, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645226 AND itemid = 4036779);
-- Flower Field Bunny | Pink Petal, Flower Drift Bunny | Yellow Petal (36221 Incident at the Studio 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645227, 4036782, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645227 AND itemid = 4036782);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645228, 4036783, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645228 AND itemid = 4036783);
-- Sunlight-Seeking Sphere | Worn-out Bottle (36230 Preparing a New Painting)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645221, 4036784, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645221 AND itemid = 4036784);
-- Gluey Leaf Fly | Worn Paper (36232 The Lost Pages 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645223, 4036780, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645223 AND itemid = 4036780);

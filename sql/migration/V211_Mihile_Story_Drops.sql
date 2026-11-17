-- v265 Mihile story quest items (nothing dropped them):
--   36605 The Second Training Session: Wild Pig's Stolen Fruit (4036875) from the Wild Pig (3645301, 993201060)
--   36606 The Third Training Session: White Sparrow Egg (4036876) from the Glutinous Rice Sparrow (3645302, 993201061)
--   36618 The Entrance Trial: Knight's Token (4036872) from the Training Timu (3645303, 993201067)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3645301, 4036875, 6000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645301 AND itemid = 4036875);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3645302, 4036876, 6000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645302 AND itemid = 4036876);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3645303, 4036872, 2500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3645303 AND itemid = 4036872);

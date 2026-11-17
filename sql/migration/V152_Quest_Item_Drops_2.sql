-- Quest items no monster dropped, from the monsters the quests name:
-- Bird Egg (4034600, 22741 Enjoying the Wait 1) from Sparrows; Dinogoth Shoulder Meat (4036160, 34708 Supply Run) from
-- Dinogoths; Decent Wooden Shield (4036154, 34709) from Specter Shieldbearers; Animal Oil (4036155, 34713) from Red
-- Speeyors; Pork (4033009, 2903 Pork Delivery 1) from Blue Ribbon Pigs.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2700310, 4034600, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700310 AND itemid = 4034600);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400103, 4036160, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400103 AND itemid = 4036160);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400109, 4036154, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4036154);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400101, 4036155, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4036155);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1210104, 4033009, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210104 AND itemid = 4033009);

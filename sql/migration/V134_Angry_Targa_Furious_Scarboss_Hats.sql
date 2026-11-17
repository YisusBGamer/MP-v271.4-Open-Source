-- Fantasy Theme World's Crittertorium show (Fantastic Animal Show, after 31382): Angry Targa (8145101/8145102) and
-- Furious Scarboss (8145201/8145202) dropped nothing.
-- Their own hats are the Ludibrium theme park's, level 110: Ludi Targa Hat (1003534) and Ludi Scarlion Hat (1003535)
-- (Targa Hat / Scarlion Boss Hat 1002926/1002927 are Malaysia's level 90 park). 5% each before the drop rate.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145101, 1003534, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145101 AND itemid = 1003534);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145102, 1003534, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145102 AND itemid = 1003534);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145201, 1003535, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145201 AND itemid = 1003535);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145202, 1003535, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145202 AND itemid = 1003535);
-- Per the MapleStory Wiki (Targa#Angry, Scarlion_Boss#Furious), both also drop the Heart Bracelet (1082719,
-- level 105 gloves).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145101, 1082719, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145101 AND itemid = 1082719);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145102, 1082719, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145102 AND itemid = 1082719);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145201, 1082719, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145201 AND itemid = 1082719);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145202, 1082719, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145202 AND itemid = 1082719);

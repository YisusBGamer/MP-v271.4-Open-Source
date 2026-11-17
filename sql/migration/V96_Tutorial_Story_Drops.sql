-- Class tutorial and story quest items that no monster dropped (same format as V95: quest items only drop for
-- characters that have the quest; each insert is skipped if the drop already exists).
-- Kinesis: Shooter/Flyer/Roller Mote | Twisted Dust Ball (22728 Gathering Evidence)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700303, 4034602, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700303 AND itemid = 4034602);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700304, 4034602, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700304 AND itemid = 4034602);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700305, 4034602, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700305 AND itemid = 4034602);
-- Explorer story: Master of Disguise | Gem of Nihal Desert (32294 Cornering the Master of Disguise: he has the jewel)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300804, 4033891, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300804 AND itemid = 4033891);
-- Phantom story: the Golem's Temple golems | Golem Core (25421 Francis Spills the Beans)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130101, 4033124, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130101 AND itemid = 4033124);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130102, 4033124, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130102 AND itemid = 4033124);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150000, 4033124, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150000 AND itemid = 4033124);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150002, 4033124, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150002 AND itemid = 4033124);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150003, 4033124, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150003 AND itemid = 4033124);
-- Phantom story: Ludibrium's Block Golems | Golem Core (25423 An Upset Puppet)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230109, 4033137, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230109 AND itemid = 4033137);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230110, 4033137, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230110 AND itemid = 4033137);
-- Phantom story: Tauromacis | Tauromacis Blood (25435 Don't Turn Around)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 7130100, 4033126, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7130100 AND itemid = 4033126);

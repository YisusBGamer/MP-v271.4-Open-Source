-- Fantasy Theme World (Spiegelmann's theme park, quests 31300-31391, level 120): seven quest items had no monster that
-- dropped them, so their quests couldn't be finished.
-- The source of each is the quest's own text in Quest.wz QuestInfo:
--   31306 A Ticket to Unemployment: 20 Entry Tickets (4033235) "from the Creeper Scarlion" (8145000)
--   31365 The Heart-Pounding Recipe for Love: 50 Creeper Scarlion's Ribbons (4036092) from Creeper Scarlion (8145000)
--   31310 The Lost Grey: Lost Walkie Talkie (4033237) from the Canterbear (8145003)
--   31314 Exchanging Items: 30 Fantastic Combo Sets (4033239) from Colabears (8145005)
--   31320 Adorable Kids: 2 Cotton Candies (4033244) from Bearic the White (8145008)
--   31323 Graffiti Absolutely Forbidden!: 20 Sparkling Cleaners (4033245) from the Jellybus (8145007)
--   31380 Swingboat Maintenance: 10 Tool Bags (4036093) from the Jellybus (8145007)
-- Also 4576 Off to Fantasy Theme World (NPC 9270062): 50 Rodeo Riders (4000471) from Rodeo (9420533), none before.
-- They are quest items: they only drop for a character whose quest needs them (Drop.java). 7500 = the theme park's
-- other quest items (Ratacular Hat, Balloon). Same format as V129.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145000, 4033235, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145000 AND itemid = 4033235);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145000, 4036092, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145000 AND itemid = 4036092);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145003, 4033237, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145003 AND itemid = 4033237);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145005, 4033239, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145005 AND itemid = 4033239);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145008, 4033244, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145008 AND itemid = 4033244);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145007, 4033245, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145007 AND itemid = 4033245);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8145007, 4036093, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145007 AND itemid = 4036093);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9420533, 4000471, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9420533 AND itemid = 4000471);

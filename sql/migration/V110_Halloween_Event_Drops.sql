-- Halloween (the Haunted Mansion, HalloweenEvent). The 2016 event's quest items had no monster drops on this
-- server; the mobs and items come from the quests' own text. Quest items only show up for characters that need them.
-- The Elite Pumpkin Zombie replaces some Elite Bosses during Halloween (2016 patch notes: tickets and badge boxes).
-- Each insert is skipped if the drop already exists.
-- Jack-o-Lantern | Jack-O-Lantern ([Halloween] Episode 2: Proper Decor)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500195, 4034711, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500195 AND itemid = 4034711);
-- Jack-o-Lantern | Soul Flower ([Halloween] Episode 2: Proper Decor)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500195, 4034709, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500195 AND itemid = 4034709);
-- Graveyard Stirge | Black Bow ([Halloween] Episode 2: Proper Decor)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500407, 4034710, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500407 AND itemid = 4034710);
-- Graveyard Stirge | Soul Flower ([Halloween] Episode 2: Proper Decor)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500407, 4034709, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500407 AND itemid = 4034709);
-- Green Phantom | Candle Chest Key ([Halloween] Episode 2: Lighting Repair)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500191, 4009379, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500191 AND itemid = 4009379);
-- Ghost (Weird Room) | Candle Chest Key ([Halloween] Episode 2: Lighting Repair)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500196, 4009379, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500196 AND itemid = 4009379);
-- Destroyed Doll | Tuning Hammer ([Halloween] Episode 2: Piano Tuning)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500188, 4034712, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500188 AND itemid = 4034712);
-- Pumpkin Knight | Pumpkin Knight's Charm ([Halloween] Episode 3: A Charm for Sophelia)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500192, 4034716, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500192 AND itemid = 4034716);
-- Fire Steed | Invisible Cloth Piece ([Halloween] Episode 4: The Truth Revealed)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500193, 4034718, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500193 AND itemid = 4034718);
-- Elite Pumpkin Zombie | Kaleido-Spinner Event Tickets
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9010102, 4001863, 10000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9010102 AND itemid = 4001863);
-- Elite Pumpkin Zombie | Halloween Surprise Badge Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9010102, 2550029, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9010102 AND itemid = 2550029);

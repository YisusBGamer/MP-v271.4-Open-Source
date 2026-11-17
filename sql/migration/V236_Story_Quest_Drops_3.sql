-- Story quest items that no monster dropped (same format as V95-V99). The mobs come from each quest's own text in
-- Quest.wz (QuestInfo.img); where v271 renamed the item ids, by the item's name. Quest items only drop for
-- characters that have the quest; each insert is skipped if the drop already exists.
-- Heliseum: Supply Central (31449) - Dinogoth | Dinogoth Shoulder Meat
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400103, 4033472, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400103 AND itemid = 4033472);
-- Heliseum: Supply Central (31449) - Blue Speeyor | Blue Speeyor Fur Coat
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4033473, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4033473);
-- Heliseum: More Supplies! (31647) - Blue Speeyor | Blue Speeyor Fur Coat
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4033417, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4033417);
-- Heliseum: More Supplies! (31647) - Red Speeyor | Animal Oil
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400101, 4033418, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4033418);
-- Heliseum: Eurenth on the Rocks (31661) - Yellow Speeyor | Yellow Speeyor Fur Coat
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400102, 4033419, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4033419);
-- Heliseum: Lacking Resources (31658) - Dinogoth | Dinogoth Shoulder Meat
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400103, 4033420, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400103 AND itemid = 4033420);
-- Heliseum: Hungry Games (31660) - Dinoram | Dinoram Tenderloin
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400104, 4033421, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4033421);
-- Heliseum: Not so Simple (31633) - Dinodon | Dinodon Sharp Fang
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400105, 4033422, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033422);
-- Heliseum: Setting the Stage (31680) - Specter Engineer | Specter Wrench
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 4033427, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 4033427);
-- Heliseum: Quality Ingredients (31666) - Specters of the central region | Coated Hyssop Flower
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400108, 4033387, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400108 AND itemid = 4033387);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400109, 4033387, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4033387);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4033387, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033387);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400111, 4033387, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400111 AND itemid = 4033387);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 4033387, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 4033387);
-- Heliseum: Eurenth's Favorites 1 (34718) - Yellow Speeyor | Yellow Speeyor Fur Coat
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400102, 4036156, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4036156);
-- Heliseum: Eurenth's Favorites 2 (34719) - Dinodon | Dinodon Ribs
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400105, 4036157, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4036157);
-- Heliseum: Blue Dragon Cannon Maintenance (38829) - Red Speeyor | Pure Oil
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400101, 4037233, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4037233);
-- Morass: Life in Morass (34231) - any monster in Morass | Memory Sliver
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644400, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644400 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644401, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644401 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644402, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644402 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644403, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644403 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644404, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644404 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644405, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644405 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644406, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644406 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644407, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644407 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644408, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644408 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644409, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644409 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644410, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644410 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644411, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644411 AND itemid = 4036337);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644412, 4036337, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644412 AND itemid = 4036337);
-- Morass: Wake Up, Fish! (34241) - any monster in Morass | Memory Sliver
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644400, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644400 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644401, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644401 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644402, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644402 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644403, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644403 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644404, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644404 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644405, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644405 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644406, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644406 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644407, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644407 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644408, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644408 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644409, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644409 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644410, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644410 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644411, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644411 AND itemid = 4036339);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644412, 4036339, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644412 AND itemid = 4036339);
-- Geardock: Undefined Location (36870) - Kronos's Retribution | Card Key
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645486, 4037475, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645486 AND itemid = 4037475);

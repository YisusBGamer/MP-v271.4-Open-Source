-- Boss drop tables. For every boss:
-- 1. repeated rows cut to one per item (the best chance kept), so more kinds of items drop;
-- 2. no Elixirs, Power Elixirs, recipes or Potential/Bonus Potential scrolls from bosses at all;
-- 3. the official GMS rewards each boss was missing (that exist in v232) added, chances set by item value;
-- 4. server-only extras removed, except Hard Magnus's Tyrant gear and Magnus's Rage (the only
--    source of the level-150 Tyrant set: kept for gear progression). Normal Damien is V145.

-- 1. repeats
DELETE d FROM mob_drops d
    JOIN mob_drops k ON k.mobid = d.mobid AND k.itemid = d.itemid
        AND (k.chance > d.chance OR (k.chance = d.chance AND k.id < d.id))
WHERE d.mobid IN (8500002, 8500012, 8500022, 8644650, 8645009, 8645066, 8800002, 8800022, 8800102, 8810018, 8810118, 8810214, 8820001, 8820212, 8840000, 8840007, 8840014, 8850011, 8850111, 8860000, 8860005, 8870000, 8870100, 8880000, 8880002, 8880010, 8880100, 8880101, 8880111, 8880156, 8880167, 8880177, 8880302, 8880342, 8880405, 8880410, 8880614, 8880615, 8880725, 8880726, 8900003, 8900103, 8910000, 8910100, 8920006, 8920106, 8930000, 8930100, 8950002, 8950102, 9421581, 9421589, 9601459, 9601460, 9601461, 9601462, 9601623);

-- 2. potions, recipes and potential scrolls
DELETE m FROM mob_drops m WHERE m.mobid IN (8500002, 8500012, 8500022, 8644650, 8645009, 8645066, 8800002, 8800022, 8800102, 8810018, 8810118, 8810214, 8820001, 8820212, 8840000, 8840007, 8840014, 8850011, 8850111, 8860000, 8860005, 8870000, 8870100, 8880000, 8880002, 8880010, 8880100, 8880101, 8880111, 8880156, 8880167, 8880177, 8880302, 8880342, 8880405, 8880410, 8880614, 8880615, 8880725, 8880726, 8900003, 8900103, 8910000, 8910100, 8920006, 8920106, 8930000, 8930100, 8950002, 8950102, 9421581, 9421589, 9601459, 9601460, 9601461, 9601462, 9601623) AND m.itemid IN (2000004, 2000005, 2022176, 2510262, 2511126, 2511127, 2511128, 2511129, 2511130, 2512062, 2512066, 2512264, 2512291, 2512292);

-- 4. extras removed
-- Arkarium
DELETE FROM mob_drops WHERE mobid = 8860000 AND itemid IN (1612004, 1622004, 1632003, 1642002, 1652004, 1942001, 1952001, 1962001, 1972001, 2020013, 2048702, 4021032, 4021033, 4021034, 4021035, 4310018, 4310029);
-- Darknell
DELETE FROM mob_drops WHERE mobid = 8645009 AND itemid IN (2020013);
-- Gloom
DELETE FROM mob_drops WHERE mobid = 8644650 AND itemid IN (2020013);
-- Gollux
DELETE FROM mob_drops WHERE mobid = 9601460 AND itemid IN (2049129, 2049133);
-- Gollux
DELETE FROM mob_drops WHERE mobid = 9601461 AND itemid IN (2049129, 2049133);
-- Gollux
DELETE FROM mob_drops WHERE mobid = 9601462 AND itemid IN (2049129, 2049133);
-- Hilla
DELETE FROM mob_drops WHERE mobid = 8870000 AND itemid IN (1082598);
-- HillaHard
DELETE FROM mob_drops WHERE mobid = 8870100 AND itemid IN (1082598);
-- HorntailHard
DELETE FROM mob_drops WHERE mobid = 8810118 AND itemid IN (1612004, 1622004, 1632003, 1642002, 1652004, 1942001, 1952001, 1962001, 1972001, 2020013, 2020015, 2048705, 4310018, 4310029);
-- Lucid
DELETE FROM mob_drops WHERE mobid = 8880167 AND itemid IN (2020013);
-- LucidHard
DELETE FROM mob_drops WHERE mobid = 8880177 AND itemid IN (2020013);
-- Magnus
DELETE FROM mob_drops WHERE mobid = 8880002 AND itemid IN (1072732, 1072733, 1072734, 1072735, 1072736, 1102471, 1102472, 1102473, 1102474, 1102475);
-- MagnusHard
DELETE FROM mob_drops WHERE mobid = 8880000 AND itemid IN (1132179, 1142520, 2048703, 2048716, 2048717);
-- Papulatus
DELETE FROM mob_drops WHERE mobid = 8500012 AND itemid IN (1612004, 1622004, 1632003, 1642002, 1652004, 1942001, 1952001, 1962001, 1972001, 2020013, 2048700, 4310018);
-- PapulatusHard
DELETE FROM mob_drops WHERE mobid = 8500022 AND itemid IN (1612004, 1622004, 1632003, 1642002, 1652004, 1942001, 1952001, 1962001, 1972001, 2020013, 2048700, 2048708, 2049129, 2049133);
-- PierreHard
DELETE FROM mob_drops WHERE mobid = 8900003 AND itemid IN (2020013, 2049129, 2049133);
-- QueenHard
DELETE FROM mob_drops WHERE mobid = 8920006 AND itemid IN (2020013);
-- RanmaruHard
DELETE FROM mob_drops WHERE mobid = 9421589 AND itemid IN (1092113, 1113020, 1212080, 1213034, 1214037, 1222075, 1232075, 1242081, 1252066, 1262030, 1272029, 1282029, 1292034, 1302290, 1312166, 1322216, 1332239, 1342086, 1362102, 1372189, 1382223, 1402211, 1412148, 1422153, 1432179, 1442235, 1452217, 1462205, 1472227, 1482180, 1492191, 1522106, 1532110, 1542068, 1552068, 1582022, 1582026, 1592033, 2048708, 2048716, 4021029, 4310018);
-- VellumHard
DELETE FROM mob_drops WHERE mobid = 8930000 AND itemid IN (2020013, 2049129, 2049133);
-- VerusHilla
DELETE FROM mob_drops WHERE mobid = 8880405 AND itemid IN (2020013);
-- VonBonHard
DELETE FROM mob_drops WHERE mobid = 8910000 AND itemid IN (2020013);
-- VonLeon
DELETE FROM mob_drops WHERE mobid = 8840000 AND itemid IN (1004234, 1004235, 1004236, 1004237, 1004238, 1032227, 1052804, 1052805, 1052806, 1052807, 1052808, 1072972, 1072973, 1072974, 1072975, 1072976, 1082613, 1082614, 1082615, 1082616, 1082617, 1102713, 1102714, 1102715, 1102716, 1102717, 1113089, 1122274, 1212102, 1213013, 1222096, 1232096, 1242103, 1252087, 1262014, 1272012, 1282012, 1302316, 1312186, 1322237, 1332261, 1362122, 1372208, 1382246, 1402237, 1412179, 1422186, 1432201, 1442255, 1452239, 1462226, 1472248, 1482203, 1492213, 1522125, 1532131, 1542102, 1552103, 1582014, 1612004, 1622004, 1632003, 1642002, 1652004, 1942001, 1952001, 1962001, 1972001, 2020013, 2048706, 4021020, 4021032, 4021033, 4021034, 4021035, 4310018);
-- VonLeonEasy
DELETE FROM mob_drops WHERE mobid = 8840007 AND itemid IN (4021032, 4021033, 4021034, 4021035);
-- Will
DELETE FROM mob_drops WHERE mobid = 8880342 AND itemid IN (2020013);
-- WillHard
DELETE FROM mob_drops WHERE mobid = 8880302 AND itemid IN (2020013);
-- ZakumHard
DELETE FROM mob_drops WHERE mobid = 8800102 AND itemid IN (1612004, 1622004, 1632003, 1642002, 1652004, 1942001, 1952001, 1962001, 1972001, 2020013, 2048700, 2049133);

-- 3. official rewards added (chance out of 10000, before the server's drop rate)
-- Akechi: Ame-no-Uzume's Shining Rod of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1212134, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1212134);
-- Akechi: Amaterasu Bladecaster of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1213038, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1213038);
-- Akechi: Oyamatsumi's Whispershot of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1214034, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1214034);
-- Akechi: Susano-o's Rainbow Shooter of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1222127, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1222127);
-- Akechi: Amaterasu's Death Bringer of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1232127, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1232127);
-- Akechi: Tsukuyomi's Energy Chain of the Wolf (Thief)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1242150, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1242150);
-- Akechi: Susano-o's Energy Chain of the Wolf (Pirate)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1242151, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1242151);
-- Akechi: Ame-no-Uzume's Spirit Pole of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1252115, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1252115);
-- Akechi: Sengoku Era Psy-limiter of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1262057, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1262057);
-- Akechi: Tsukuyomi's Chain of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1272047, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1272047);
-- Akechi: Ame-no-Uzume's Lucent Gauntlet of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1282047, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1282047);
-- Akechi: Tsukuyomi Ritual Fan of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1292042, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1292042);
-- Akechi: Amaterasu's Nimbus Sword of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1302364, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1302364);
-- Akechi: Amaterasu's Axe of a Thousand Blades of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1312219, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1312219);
-- Akechi: Amaterasu's Golden Slice of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1322271, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1322271);
-- Akechi: Tsukuyomi's Execution Sword of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1332294, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1332294);
-- Akechi: Tsukuyomi's Mournful Blade of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1342112, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1342112);
-- Akechi: Tsukuyomi's Wraith Hand of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1362154, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1362154);
-- Akechi: Ame-no-Uzume's Crooked Soul Staff of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1372242, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1372242);
-- Akechi: Ame-no-Uzume's Sinister Spirit Hand of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1382279, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1382279);
-- Akechi: Amaterasu's Weeping Spirit Sword of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1402274, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1402274);
-- Akechi: Amaterasu's Smoldering Spirit Axe of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1412194, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1412194);
-- Akechi: Amaterasu's Mace of Ruin of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1422204, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1422204);
-- Akechi: Amaterasu's Final Wind of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1432232, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1432232);
-- Akechi: Amaterasu's Pelting Rain of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1442290, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1442290);
-- Akechi: Oyamatsumi's Fire Spirit Bow of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1452273, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1452273);
-- Akechi: Oyamatsumi's Mind Expansion Bow of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1462257, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1462257);
-- Akechi: Tsukuyomi's Fist of Malice of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1472280, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1472280);
-- Akechi: Susano-o's Bloodstained Sword of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1482237, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1482237);
-- Akechi: Susano-o's Baleful Blaster of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1492251, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1492251);
-- Akechi: Oyamatsumi's Dragon Bow of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1522157, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1522157);
-- Akechi: Susano-o's Solar Cannon of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1532163, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1532163);
-- Akechi: Amaterasu's Muramasa of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1542136, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1542136);
-- Akechi: Ame-no-Uzume's Red Flower of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1552139, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1552139);
-- Akechi: Amaterasu Bunker Buster of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1582050, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1582050);
-- Akechi: Oyamatsumi's Ancient Bow of the Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 1592041, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 1592041);
-- Akechi: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 2003586);
-- Akechi: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 2003587);
-- Akechi: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 2003607);
-- Akechi: 50% Bonus EXP Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 2024084, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 2024084);
-- Akechi: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 2633892);
-- Akechi: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 4021020);
-- Akechi: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 4021031);
-- Akechi: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 4001832);
-- Akechi: Mark of the Silver Wolf
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 4310295, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 4310295);
-- Akechi: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 4001886);
-- Akechi: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601623, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601623 AND itemid = 2590006);
-- Arkarium: Proton Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860000, 1072776, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860000 AND itemid = 1072776);
-- Arkarium: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860000, 1050253, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860000 AND itemid = 1050253);
-- Arkarium: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860000, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860000 AND itemid = 2590006);
-- Arkarium: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860000, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860000 AND itemid = 4021022);
-- ArkariumEasy: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860005, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860005 AND itemid = 2590006);
-- ArkariumEasy: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860005, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860005 AND itemid = 2433103);
-- ArkariumEasy: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860005, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860005 AND itemid = 4021022);
-- ArkariumEasy: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860005, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860005 AND itemid = 4001832);
-- ArkariumEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8860005, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860005 AND itemid = 4001886);
-- Cygnus: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 4001832);
-- Cygnus: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 2590006);
-- Cygnus: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1050253, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1050253);
-- Cygnus: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1051309, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1051309);
-- Cygnus: Proton Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1072776, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1072776);
-- Cygnus: Lionheart Battle Shoulder
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1152108, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1152108);
-- Cygnus: Dragon Tail Mage Shoulder
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1152110, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1152110);
-- Cygnus: Falcon Wing Sentinel Shoulder
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1152111, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1152111);
-- Cygnus: Raven Horn Chaser Shoulder
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1152112, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1152112);
-- Cygnus: Shark Tooth Skipper Shoulder
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 1152113, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 1152113);
-- Cygnus: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850011, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850011 AND itemid = 4001886);
-- CygnusEasy: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850111, 4001832, 10000, 140, 140 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850111 AND itemid = 4001832);
-- CygnusEasy: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850111, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850111 AND itemid = 2590006);
-- CygnusEasy: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850111, 1050253, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850111 AND itemid = 1050253);
-- CygnusEasy: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850111, 1051309, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850111 AND itemid = 1051309);
-- CygnusEasy: Proton Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850111, 1072776, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850111 AND itemid = 1072776);
-- CygnusEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850111, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850111 AND itemid = 4001886);
-- CygnusEasy: Cygnus's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8850111, 2431711, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8850111 AND itemid = 2431711);
-- DamienHard: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2003586);
-- DamienHard: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2003587);
-- DamienHard: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2003607);
-- DamienHard: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2633920);
-- DamienHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2590006);
-- DamienHard: Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2633919, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2633919);
-- DamienHard: AbsoLab Weapon Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2633912, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2633912);
-- DamienHard: AbsoLab Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 2633913, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 2633913);
-- DamienHard: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 4021020);
-- DamienHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 4021031);
-- DamienHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880111, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880111 AND itemid = 4001832);
-- Darknell: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2003586);
-- Darknell: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2003587);
-- Darknell: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2003607);
-- Darknell: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2633920);
-- Darknell: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2590006);
-- Darknell: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 4001832);
-- Darknell: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 4001886);
-- DarknellHard: Commanding Force Earring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 1032316, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 1032316);
-- DarknellHard: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2003586);
-- DarknellHard: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2003587);
-- DarknellHard: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2003607);
-- DarknellHard: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633920);
-- DarknellHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2590006);
-- DarknellHard: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633892);
-- DarknellHard: Darknell's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2439568, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2439568);
-- DarknellHard: Premium Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633916, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633916);
-- DarknellHard: Premium Scroll - Pet Equipment Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633917, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633917);
-- DarknellHard: Magical Scroll - Weapon Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633918, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633918);
-- DarknellHard: Arcane Umbra Weapon Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633914, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633914);
-- DarknellHard: Arcane Umbra Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633915, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633915);
-- DarknellHard: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 4021022);
-- DarknellHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 4021031);
-- DarknellHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 4001832);
-- DarknellHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 4001886);
-- Gloom: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2003586);
-- Gloom: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2003587);
-- Gloom: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2003607);
-- Gloom: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2633920);
-- Gloom: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2590006);
-- Gloom: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 4001832);
-- Gloom: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 4001886);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032220);
-- Gollux: Cracked Gollux Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1113072);
-- Gollux: Cracked Gollux Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1113072);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2590006);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2433103);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4021031);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4001832);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032220);
-- Gollux: Cracked Gollux Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1113072);
-- Gollux: Cracked Gollux Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1113072);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032221);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032221);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032221);
-- Gollux: Solid Gollux Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1113073, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1113073);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2590006);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2433103);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4021031);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4001832);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032220);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032221);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032221);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032221);
-- Gollux: Reinforced Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032222, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032222);
-- Gollux: Reinforced Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032222, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032222);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2590006);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2433103);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4021031);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4001832);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032220);
-- Gollux: Cracked Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032220);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032221);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032221);
-- Gollux: Solid Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032221);
-- Gollux: Reinforced Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032222, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032222);
-- Gollux: Reinforced Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032222, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032222);
-- Gollux: Superior Gollux Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032223, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032223);
-- Gollux: Fallen Heart Tree Key
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4033982, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4033982);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2590006);
-- Gollux: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2590006);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 2433103);
-- Gollux: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 2433103);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4021031);
-- Gollux: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4021031);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 4001832);
-- Gollux: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 4001832, 10000, 50, 50 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 4001832);
-- GuardianAngelSlime: Guardian Angel Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 1113313, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 1113313);
-- GuardianAngelSlime: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 2003586);
-- GuardianAngelSlime: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 2003587);
-- GuardianAngelSlime: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 2003607);
-- GuardianAngelSlime: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 2633920);
-- GuardianAngelSlime: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 2633892);
-- GuardianAngelSlime: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 4021020);
-- GuardianAngelSlime: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 4021031);
-- GuardianAngelSlime: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 4001832);
-- GuardianAngelSlime: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 4001886);
-- GuardianAngelSlime: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880726, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880726 AND itemid = 2590006);
-- GuardianAngelSlimeChaos: Guardian Angel Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 1113313, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 1113313);
-- GuardianAngelSlimeChaos: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2003586);
-- GuardianAngelSlimeChaos: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2003587);
-- GuardianAngelSlimeChaos: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2003607);
-- GuardianAngelSlimeChaos: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2633920);
-- GuardianAngelSlimeChaos: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2590006);
-- GuardianAngelSlimeChaos: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2633892);
-- GuardianAngelSlimeChaos: Premium Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2633916, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2633916);
-- GuardianAngelSlimeChaos: Premium Scroll - Pet Equipment Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2633917, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2633917);
-- GuardianAngelSlimeChaos: Magical Scroll - Weapon Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 2633918, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 2633918);
-- GuardianAngelSlimeChaos: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 4021022);
-- GuardianAngelSlimeChaos: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 4021020);
-- GuardianAngelSlimeChaos: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 4021031);
-- GuardianAngelSlimeChaos: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 4001832);
-- GuardianAngelSlimeChaos: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880725, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880725 AND itemid = 4001886);
-- Hilla: Necromancer Whispershot
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 1214011, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 1214011);
-- Hilla: Necro Apparition Ritual Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 1292011, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 1292011);
-- Hilla: Necromancer's Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 1552040, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 1552040);
-- Hilla: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 2590006);
-- Hilla: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 2433103);
-- Hilla: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 4021031);
-- Hilla: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 4001832);
-- Hilla: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870000, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870000 AND itemid = 4001886);
-- HillaHard: Necromancer Whispershot
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 1214011, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 1214011);
-- HillaHard: Necro Apparition Ritual Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 1292011, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 1292011);
-- HillaHard: Necromancer's Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 1552040, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 1552040);
-- HillaHard: Proton Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 1072776, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 1072776);
-- HillaHard: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 1050253, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 1050253);
-- HillaHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 2590006);
-- HillaHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 4021031);
-- HillaHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8870100, 4001832, 10000, 140, 140 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8870100 AND itemid = 4001832);
-- Horntail: Silver Blossom Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810018, 1113149, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810018 AND itemid = 1113149);
-- Horntail: Dea Sidus Earring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810018, 1032241, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810018 AND itemid = 1032241);
-- Horntail: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810018, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810018 AND itemid = 2590006);
-- Horntail: Horntail Monsterbloom
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810018, 2435366, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810018 AND itemid = 2435366);
-- Horntail: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810018, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810018 AND itemid = 4021031);
-- Horntail: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810018, 4001832, 10000, 40, 40 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810018 AND itemid = 4001832);
-- Horntail: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810018, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810018 AND itemid = 4001886);
-- HorntailEasy: Horntail Necklace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810214, 1122000, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810214 AND itemid = 1122000);
-- HorntailEasy: Silver Blossom Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810214, 1113149, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810214 AND itemid = 1113149);
-- HorntailEasy: Dea Sidus Earring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810214, 1032241, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810214 AND itemid = 1032241);
-- HorntailEasy: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810214, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810214 AND itemid = 2590006);
-- HorntailEasy: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810214, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810214 AND itemid = 4021031);
-- HorntailEasy: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810214, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810214 AND itemid = 4001832);
-- HorntailEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810214, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810214 AND itemid = 4001886);
-- HorntailHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810118, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810118 AND itemid = 2590006);
-- HorntailHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810118, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810118 AND itemid = 4021031);
-- HorntailHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810118, 4001832, 10000, 60, 60 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810118 AND itemid = 4001832);
-- HorntailHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8810118, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8810118 AND itemid = 4001886);
-- Lotus: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 2003586);
-- Lotus: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 2003587);
-- Lotus: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 2003607);
-- Lotus: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 2633920);
-- Lotus: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 2590006);
-- Lotus: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 4021020);
-- Lotus: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 4021031);
-- Lotus: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 4001832);
-- Lotus: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950002, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950002 AND itemid = 4001886);
-- LotusHard: Lotusroid
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 1662053, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 1662053);
-- LotusHard: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2003586);
-- LotusHard: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2003587);
-- LotusHard: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2003607);
-- LotusHard: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2633920);
-- LotusHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2590006);
-- LotusHard: Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2633919, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2633919);
-- LotusHard: AbsoLab Weapon Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2633912, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2633912);
-- LotusHard: AbsoLab Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 2633913, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 2633913);
-- LotusHard: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 4021020);
-- LotusHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 4021031);
-- LotusHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 4001832);
-- LotusHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8950102, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8950102 AND itemid = 4001886);
-- Lucid: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 2003586);
-- Lucid: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 2003587);
-- Lucid: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 2003607);
-- Lucid: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 2633920);
-- Lucid: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 2590006);
-- Lucid: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 4021020);
-- Lucid: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 4021031);
-- Lucid: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 4001832);
-- Lucid: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880167, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880167 AND itemid = 4001886);
-- LucidEasy: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 2003586);
-- LucidEasy: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 2003587);
-- LucidEasy: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 2003607);
-- LucidEasy: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 2633920);
-- LucidEasy: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 2590006);
-- LucidEasy: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 2633892);
-- LucidEasy: Lucid's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 2436039, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 2436039);
-- LucidEasy: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 4021020);
-- LucidEasy: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 4021031);
-- LucidEasy: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 4001832);
-- LucidEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880156, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880156 AND itemid = 4001886);
-- LucidHard: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2003586);
-- LucidHard: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2003587);
-- LucidHard: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2003607);
-- LucidHard: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2633920);
-- LucidHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2590006);
-- LucidHard: Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2633919, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2633919);
-- LucidHard: Arcane Umbra Weapon Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2633914, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2633914);
-- LucidHard: Arcane Umbra Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 2633915, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 2633915);
-- LucidHard: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 4021020);
-- LucidHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 4021031);
-- LucidHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 4001832);
-- LucidHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880177, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880177 AND itemid = 4001886);
-- Magnus: Nova Hyades Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1102476, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1102476);
-- Magnus: Nova Hermes Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1102477, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1102477);
-- Magnus: Nova Charon Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1102478, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1102478);
-- Magnus: Nova Lycaon Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1102479, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1102479);
-- Magnus: Nova Altair Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1102480, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1102480);
-- Magnus: Kaiserion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1402205, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1402205);
-- Magnus: Royal Black Metal Shoulder
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1152170, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1152170);
-- Magnus: Crystal Ventus Badge
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 1182087, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 1182087);
-- Magnus: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 2590006);
-- Magnus: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 2433103);
-- Magnus: Magnus's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 2431964, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 2431964);
-- Magnus: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 4021031);
-- Magnus: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 4001832, 10000, 72, 72 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 4001832);
-- Magnus: Shadowdealer Coin
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 4310059, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 4310059);
-- Magnus: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880002, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880002 AND itemid = 4001886);
-- MagnusEasy: Elite Heliseum Warrior Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1132164, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1132164);
-- MagnusEasy: Elite Heliseum Magician Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1132165, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1132165);
-- MagnusEasy: Elite Heliseum Bowman Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1132166, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1132166);
-- MagnusEasy: Elite Heliseum Thief Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1132167, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1132167);
-- MagnusEasy: Elite Heliseum Pirate Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1132168, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1132168);
-- MagnusEasy: Nova Hyades Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1102476, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1102476);
-- MagnusEasy: Nova Hermes Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1102477, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1102477);
-- MagnusEasy: Nova Charon Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1102478, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1102478);
-- MagnusEasy: Nova Lycaon Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1102479, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1102479);
-- MagnusEasy: Nova Altair Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1102480, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1102480);
-- MagnusEasy: Kaiserion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 1402205, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 1402205);
-- MagnusEasy: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 2590006);
-- MagnusEasy: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 2433103);
-- MagnusEasy: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 4021031);
-- MagnusEasy: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 4001832);
-- MagnusEasy: Shadowdealer Coin
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 4310059, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 4310059);
-- MagnusEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880010, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880010 AND itemid = 4001886);
-- MagnusHard: Elite Heliseum Warrior Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132164, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132164);
-- MagnusHard: Elite Heliseum Magician Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132165, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132165);
-- MagnusHard: Elite Heliseum Bowman Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132166, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132166);
-- MagnusHard: Elite Heliseum Thief Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132167, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132167);
-- MagnusHard: Elite Heliseum Pirate Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132168, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132168);
-- MagnusHard: Nova Hyades Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1102476, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1102476);
-- MagnusHard: Nova Hermes Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1102477, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1102477);
-- MagnusHard: Nova Charon Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1102478, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1102478);
-- MagnusHard: Nova Lycaon Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1102479, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1102479);
-- MagnusHard: Nova Altair Cloak
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1102480, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1102480);
-- MagnusHard: Nova Hyades Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1072737, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1072737);
-- MagnusHard: Nova Hermes Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1072738, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1072738);
-- MagnusHard: Nova Charon Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1072739, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1072739);
-- MagnusHard: Nova Lycaon Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1072740, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1072740);
-- MagnusHard: Nova Altair Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1072741, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1072741);
-- MagnusHard: Nova Hyades Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132169, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132169);
-- MagnusHard: Nova Hermes Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132170, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132170);
-- MagnusHard: Nova Charon Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132171, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132171);
-- MagnusHard: Nova Lycaon Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132172, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132172);
-- MagnusHard: Nova Altair Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1132173, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1132173);
-- MagnusHard: Proton Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1072776, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1072776);
-- MagnusHard: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1050253, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1050253);
-- MagnusHard: Royal Black Metal Shoulder
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1152170, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1152170);
-- MagnusHard: Crystal Ventus Badge
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 1182087, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 1182087);
-- MagnusHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 2590006);
-- MagnusHard: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 2433103);
-- MagnusHard: Magnus's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 2431964, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 2431964);
-- MagnusHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 4021031);
-- MagnusHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 4001832);
-- MagnusHard: Shadowdealer Coin
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 4310059, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 4310059);
-- MagnusHard: Magnus Coin
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 4310058, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 4310058);
-- MagnusHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880000, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880000 AND itemid = 4001886);
-- Papulatus: Papulatus Familiar
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500012, 2870377, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500012 AND itemid = 2870377);
-- Papulatus: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500012, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500012 AND itemid = 4021031);
-- Papulatus: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500012, 4001832, 10000, 72, 72 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500012 AND itemid = 4001832);
-- Papulatus: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500012, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500012 AND itemid = 4001886);
-- PapulatusEasy: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500002, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500002 AND itemid = 2433103);
-- PapulatusEasy: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500002, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500002 AND itemid = 4021031);
-- PapulatusEasy: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500002, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500002 AND itemid = 4001832);
-- PapulatusEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500002, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500002 AND itemid = 4001886);
-- PapulatusHard: Papulatus Familiar
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500022, 2870377, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500022 AND itemid = 2870377);
-- PapulatusHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500022, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500022 AND itemid = 4021031);
-- PapulatusHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500022, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500022 AND itemid = 4001832);
-- PapulatusHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8500022, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8500022 AND itemid = 4001886);
-- Pierre: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900103, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900103 AND itemid = 2590006);
-- Pierre: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900103, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900103 AND itemid = 4021031);
-- Pierre: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900103, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900103 AND itemid = 4001832);
-- Pierre: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900103, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900103 AND itemid = 4001886);
-- PierreHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900003, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900003 AND itemid = 4021031);
-- PierreHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900003, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900003 AND itemid = 4001832);
-- PierreHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900003, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900003 AND itemid = 4001886);
-- PierreHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8900003, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8900003 AND itemid = 2590006);
-- PinkBean: Black Bean Mark
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 1022232, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 1022232);
-- PinkBean: Golden Clover Belt
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 1132272, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 1132272);
-- PinkBean: Pink Holy Cup
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 1162025, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 1162025);
-- PinkBean: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 2590006);
-- PinkBean: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 2433103);
-- PinkBean: Pink Bean's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 2431661, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 2431661);
-- PinkBean: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 4021031);
-- PinkBean: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 4001832, 10000, 60, 60 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 4001832);
-- PinkBean: Undine's Cloth
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 4001115, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 4001115);
-- PinkBean: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820001, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820001 AND itemid = 4001886);
-- PinkBeanHard: Proton Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820212, 1050253, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820212 AND itemid = 1050253);
-- PinkBeanHard: Proton Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820212, 1072776, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820212 AND itemid = 1072776);
-- PinkBeanHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820212, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820212 AND itemid = 2590006);
-- PinkBeanHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820212, 4001832, 10000, 140, 140 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820212 AND itemid = 4001832);
-- PinkBeanHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8820212, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8820212 AND itemid = 4001886);
-- Queen: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8920106, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8920106 AND itemid = 4021031);
-- Queen: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8920106, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8920106 AND itemid = 4001832);
-- Queen: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8920106, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8920106 AND itemid = 2590006);
-- QueenHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8920006, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8920006 AND itemid = 2590006);
-- QueenHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8920006, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8920006 AND itemid = 4021031);
-- QueenHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8920006, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8920006 AND itemid = 4001832);
-- QueenHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8920006, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8920006 AND itemid = 4001886);
-- Ranmaru: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421581, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421581 AND itemid = 2433103);
-- Ranmaru: Spirit Stone of the Sixth Heaven
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421581, 4021030, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421581 AND itemid = 4021030);
-- Ranmaru: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421581, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421581 AND itemid = 4001832);
-- Ranmaru: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421581, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421581 AND itemid = 2590006);
-- Ranmaru: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421581, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421581 AND itemid = 4001886);
-- RanmaruHard: EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 2003550, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 2003550);
-- RanmaruHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 2590006);
-- RanmaruHard: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4021020);
-- RanmaruHard: Legendary Weapon Shard (Mouri Clan)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4021023, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4021023);
-- RanmaruHard: Legendary Armor Shard (Mouri Clan)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4021024, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4021024);
-- RanmaruHard: Legendary Weapon Shard (Takeda Clan)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4021025, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4021025);
-- RanmaruHard: Legendary Armor Shard (Takeda Clan)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4021026, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4021026);
-- RanmaruHard: Legendary Weapon Shard (Uesugi Clan)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4021027, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4021027);
-- RanmaruHard: Legendary Armor Shard (Uesugi Clan)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4021028, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4021028);
-- RanmaruHard: Oyamatsumi's Whispershot
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 1214033, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 1214033);
-- RanmaruHard: Tsukuyomi Ritual Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 1292033, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 1292033);
-- RanmaruHard: Amaterasu Bunker Buster
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 1582030, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 1582030);
-- RanmaruHard: Oyamatsumi's Ancient Bow
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 1592034, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 1592034);
-- RanmaruHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421589, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421589 AND itemid = 4001886);
-- Seren: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2003586);
-- Seren: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2003586);
-- Seren: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2003587);
-- Seren: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2003587);
-- Seren: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2003607);
-- Seren: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2003607);
-- Seren: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2633920);
-- Seren: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2633920);
-- Seren: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2590006);
-- Seren: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2590006);
-- Seren: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2633892);
-- Seren: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2633892);
-- Seren: Premium Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2633916, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2633916);
-- Seren: Premium Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2633916, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2633916);
-- Seren: Premium Scroll - Pet Equipment Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2633917, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2633917);
-- Seren: Premium Scroll - Pet Equipment Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2633917, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2633917);
-- Seren: Magical Scroll - Weapon Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2633918, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2633918);
-- Seren: Magical Scroll - Weapon Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2633918, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2633918);
-- Seren: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 4021022);
-- Seren: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 4021022);
-- Seren: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 4021031);
-- Seren: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 4021031);
-- Seren: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 4001832);
-- Seren: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 4001832);
-- Seren: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 4001886);
-- Seren: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 4001886);
-- Vellum: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930100, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930100 AND itemid = 2590006);
-- Vellum: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930100, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930100 AND itemid = 4021031);
-- Vellum: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930100, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930100 AND itemid = 4001832);
-- Vellum: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930100, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930100 AND itemid = 4001886);
-- VellumHard: Root Abyss (Warrior) Lucky Item Scroll
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930000, 2048905, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930000 AND itemid = 2048905);
-- VellumHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930000, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930000 AND itemid = 2590006);
-- VellumHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930000, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930000 AND itemid = 4021031);
-- VellumHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930000, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930000 AND itemid = 4001832);
-- VellumHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8930000, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8930000 AND itemid = 4001886);
-- VerusHilla: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2003586);
-- VerusHilla: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2003587);
-- VerusHilla: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2003607);
-- VerusHilla: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2633920);
-- VerusHilla: Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2633919, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2633919);
-- VerusHilla: Arcane Umbra Weapon Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2633914, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2633914);
-- VerusHilla: Arcane Umbra Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2633915, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2633915);
-- VerusHilla: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 4021022);
-- VerusHilla: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 4021031);
-- VerusHilla: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 4001832);
-- VerusHilla: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 2590006);
-- VerusHilla: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880405, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880405 AND itemid = 4001886);
-- VerusHillaHard: Source of Suffering
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 1122430, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 1122430);
-- VerusHillaHard: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2003586);
-- VerusHillaHard: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2003587);
-- VerusHillaHard: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2003607);
-- VerusHillaHard: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2633920);
-- VerusHillaHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2590006);
-- VerusHillaHard: Large Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2633892);
-- VerusHillaHard: Verus Hilla's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2439567, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2439567);
-- VerusHillaHard: Premium Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2633916, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2633916);
-- VerusHillaHard: Premium Scroll - Pet Equipment Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2633917, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2633917);
-- VerusHillaHard: Magical Scroll - Weapon Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2633918, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2633918);
-- VerusHillaHard: Arcane Umbra Weapon Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2633914, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2633914);
-- VerusHillaHard: Arcane Umbra Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 2633915, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 2633915);
-- VerusHillaHard: Primal Essence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 4021022);
-- VerusHillaHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 4021031);
-- VerusHillaHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 4001832);
-- VerusHillaHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880410, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880410 AND itemid = 4001886);
-- VonBon: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910100, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910100 AND itemid = 2590006);
-- VonBon: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910100, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910100 AND itemid = 4021031);
-- VonBon: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910100, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910100 AND itemid = 4001832);
-- VonBon: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910100, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910100 AND itemid = 4001886);
-- VonBonHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910000, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910000 AND itemid = 4021031);
-- VonBonHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910000, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910000 AND itemid = 4001832);
-- VonBonHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910000, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910000 AND itemid = 2590006);
-- VonBonHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8910000, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8910000 AND itemid = 4001886);
-- VonLeon: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840000, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840000 AND itemid = 2590006);
-- VonLeon: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840000, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840000 AND itemid = 4001886);
-- VonLeonEasy: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840007, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840007 AND itemid = 2590006);
-- VonLeonEasy: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840007, 4001832, 10000, 40, 40 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840007 AND itemid = 4001832);
-- VonLeonEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840007, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840007 AND itemid = 4001886);
-- VonLeonHard: Ifia's Earrings
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1032227, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1032227);
-- VonLeonHard: Ifia's Ring
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1113089, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1113089);
-- VonLeonHard: Ifia's Necklace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1122274, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1122274);
-- VonLeonHard: Royal Von Leon Warrior Helm
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1004234, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1004234);
-- VonLeonHard: Royal Von Leon Magician Helm
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1004235, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1004235);
-- VonLeonHard: Royal Von Leon Sentinel Helm
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1004236, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1004236);
-- VonLeonHard: Royal Von Leon Chaser Helm
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1004237, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1004237);
-- VonLeonHard: Royal Von Leon Skipper Helm
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1004238, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1004238);
-- VonLeonHard: Royal Von Leon Warrior Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1052804, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1052804);
-- VonLeonHard: Royal Von Leon Mage Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1052805, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1052805);
-- VonLeonHard: Royal Von Leon Sentinel Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1052806, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1052806);
-- VonLeonHard: Royal Von Leon Chaser Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1052807, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1052807);
-- VonLeonHard: Royal Von Leon Skipper Suit
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1052808, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1052808);
-- VonLeonHard: Royal Von Leon Warrior Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1072972, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1072972);
-- VonLeonHard: Royal Von Leon Mage Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1072973, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1072973);
-- VonLeonHard: Royal Von Leon Sentinel Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1072974, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1072974);
-- VonLeonHard: Royal Von Leon Chaser Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1072975, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1072975);
-- VonLeonHard: Royal Von Leon Skipper Boots
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1072976, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1072976);
-- VonLeonHard: Royal Von Leon Warrior Hands
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1082613, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1082613);
-- VonLeonHard: Royal Von Leon Mage Hands
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1082614, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1082614);
-- VonLeonHard: Royal Von Leon Sentinel Hands
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1082615, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1082615);
-- VonLeonHard: Royal Von Leon Chaser Hands
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1082616, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1082616);
-- VonLeonHard: Royal Von Leon Skipper Hands
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1082617, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1082617);
-- VonLeonHard: Royal Von Leon Warrior Cape
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1102713, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1102713);
-- VonLeonHard: Royal Von Leon Mage Cape
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1102714, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1102714);
-- VonLeonHard: Royal Von Leon Sentinel Cape
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1102715, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1102715);
-- VonLeonHard: Royal Von Leon Chaser Cape
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1102716, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1102716);
-- VonLeonHard: Royal Von Leon Skipper Cape
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1102717, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1102717);
-- VonLeonHard: Royal Von Leon Glorier
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1212102, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1212102);
-- VonLeonHard: Royal Von Leon Nobility
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1213013, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1213013);
-- VonLeonHard: Royal Von Leon Whispershot
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1214013, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1214013);
-- VonLeonHard: Royal Von Leon White Worm
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1222096, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1222096);
-- VonLeonHard: Royal Von Leon Blood Fury
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1232096, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1232096);
-- VonLeonHard: Royal Von Leon Energy Chain
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1242103, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1242103);
-- VonLeonHard: Royal Von Leon Stick
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1252087, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1252087);
-- VonLeonHard: Royal Von Leon Psy-limiter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1262014, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1262014);
-- VonLeonHard: Royal Von Leon Chain
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1272012, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1272012);
-- VonLeonHard: Royal Von Leon Lucent Gauntlet
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1282012, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1282012);
-- VonLeonHard: Royal Von Leon Lion Ritual Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1292013, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1292013);
-- VonLeonHard: Royal Von Leon Saber
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1302316, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1302316);
-- VonLeonHard: Royal Von Leon Axe
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1312186, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1312186);
-- VonLeonHard: Royal Von Leon Hammer
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1322237, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1322237);
-- VonLeonHard: Royal Von Leon Dagger
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1332261, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1332261);
-- VonLeonHard: Royal Von Leon Cane
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1362122, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1362122);
-- VonLeonHard: Royal Von Leon Wand
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1372208, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1372208);
-- VonLeonHard: Royal Von Leon Staff
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1382246, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1382246);
-- VonLeonHard: Royal Von Leon Sword
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1402237, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1402237);
-- VonLeonHard: Royal Von Leon Two-Handed Axe
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1412179, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1412179);
-- VonLeonHard: Royal Von Leon Two-Handed Hammer
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1422186, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1422186);
-- VonLeonHard: Royal Von Leon Spear
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1432201, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1432201);
-- VonLeonHard: Royal Von Leon Hellslayer
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1442255, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1442255);
-- VonLeonHard: Royal Von Leon Bow
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1452239, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1452239);
-- VonLeonHard: Royal Von Leon Crossbow
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1462226, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1462226);
-- VonLeonHard: Royal Von Leon Guards
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1472248, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1472248);
-- VonLeonHard: Royal Von Leon Claw
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1482203, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1482203);
-- VonLeonHard: Royal Von Leon Pistol
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1492213, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1492213);
-- VonLeonHard: Royal Von Leon Dual Bowguns
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1522125, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1522125);
-- VonLeonHard: Royal Von Leon Siege Gun
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1532131, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1532131);
-- VonLeonHard: Royal Von Leon Katana
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1542102, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1542102);
-- VonLeonHard: Royal Von Leon Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1552103, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1552103);
-- VonLeonHard: Royal Von Leon Ymir
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1582014, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1582014);
-- VonLeonHard: Royal Von Leon Ancient Bow
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 1592013, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 1592013);
-- VonLeonHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 2590006);
-- VonLeonHard: Boss Medal of Honor
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 2433103, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 2433103);
-- VonLeonHard: Von Leon Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 2431662, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 2431662);
-- VonLeonHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 4021031);
-- VonLeonHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 4001832, 10000, 72, 72 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 4001832);
-- VonLeonHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8840014, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8840014 AND itemid = 4001886);
-- Will: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 2003586);
-- Will: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 2003587);
-- Will: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 2003607);
-- Will: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 2633920);
-- Will: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 2590006);
-- Will: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 4021020);
-- Will: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 4021031);
-- Will: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 4001832);
-- Will: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880342, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880342 AND itemid = 4001886);
-- WillHard: Sparkling Blue Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2003586, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2003586);
-- WillHard: Sparkling Red Star Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2003587, 3000, 3, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2003587);
-- WillHard: Small EXP Accumulation Potion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2003607);
-- WillHard: 50% Bonus EXP Coupon Voucher
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2633920);
-- WillHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2590006);
-- WillHard: Arcane Umbra Weapon Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2633914, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2633914);
-- WillHard: Arcane Umbra Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2633915, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2633915);
-- WillHard: Will's Cursed Spellbook Selection Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2633926, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2633926);
-- WillHard: Confusion Fragment
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 4021020);
-- WillHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 4021031);
-- WillHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 4001832, 10000, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 4001832);
-- WillHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 4001886);
-- WillHard: Scroll - Accessory Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880302, 2633919, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880302 AND itemid = 2633919);
-- Zakum: Zakum's Poisonic Whispershot
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800002, 1214009, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800002 AND itemid = 1214009);
-- Zakum: Zakum's Poisonic Ritual Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800002, 1292009, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800002 AND itemid = 1292009);
-- Zakum: Zakum's Poisonic Ancient Bow
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800002, 1592010, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800002 AND itemid = 1592010);
-- Zakum: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800002, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800002 AND itemid = 2590006);
-- Zakum: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800002, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800002 AND itemid = 4021031);
-- Zakum: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800002, 4001832, 10000, 20, 20 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800002 AND itemid = 4001832);
-- Zakum: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800002, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800002 AND itemid = 4001886);
-- ZakumEasy: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800022, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800022 AND itemid = 2590006);
-- ZakumEasy: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800022, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800022 AND itemid = 4001886);
-- ZakumHard: Zakum's Poisonic Whispershot
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800102, 1214009, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800102 AND itemid = 1214009);
-- ZakumHard: Zakum's Poisonic Ritual Fan
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800102, 1292009, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800102 AND itemid = 1292009);
-- ZakumHard: Zakum's Poisonic Ancient Bow
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800102, 1592010, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800102 AND itemid = 1592010);
-- ZakumHard: Production Soul Enchanter
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800102, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800102 AND itemid = 2590006);
-- ZakumHard: Twisted Time
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800102, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800102 AND itemid = 4021031);
-- ZakumHard: Spell Trace
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800102, 4001832, 10000, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800102 AND itemid = 4001832);
-- ZakumHard: Intense Power Crystal
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8800102, 4001886, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8800102 AND itemid = 4001886);

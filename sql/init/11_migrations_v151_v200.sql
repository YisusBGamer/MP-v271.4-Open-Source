-- The migrations V151 to V200 in order, folded in so a new database needs no catch-up at startup.
-- 09_migration_history.sql marks them done. sql/migration keeps the files for servers updating from older versions.

-- ----------------------------------------------------------------------------------------------------
-- V151_Quest_Item_Drops.sql
-- Quest items nothing dropped:
-- Stained Soul (4033117) for Mihile's 20758, from Arkarium (both versions); Snake Leather Bag (4033231) for 25444,
-- from the Serpents near Edelstein.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8860000, 4033117, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860000 AND itemid = 4033117);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8860005, 4033117, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8860005 AND itemid = 4033117);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1150002, 4033231, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1150002 AND itemid = 4033231);

-- ----------------------------------------------------------------------------------------------------
-- V152_Quest_Item_Drops_2.sql
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

-- ----------------------------------------------------------------------------------------------------
-- V153_Kentas_Freezer_Leather.sql
-- Kenta's Freezer Leather (4000592) for 22404, from Kenta's Freezer (Sea of Silence 923030000), which dropped nothing.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9001025, 4000592, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001025 AND itemid = 4000592);

-- ----------------------------------------------------------------------------------------------------
-- V154_Timmy_Shaolin.sql
-- Timmy (9310581) stands in no map's data; his quests (62036, 62037) say he is in this Shaolin Temple map.
INSERT INTO `npc` (`npcid`, `mapid`, `x`, `y`, `cy`, `rx0`, `rx1`, `fh`)
SELECT 9310581, 701220350, -150, 137, 137, -200, -100, 4 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `npc` WHERE `npcid` = 9310581 AND `mapid` = 701220350);

-- ----------------------------------------------------------------------------------------------------
-- V155_Drop_Nonexistent_Item.sql
-- Water Thief Monster (2150000) listed "item" 2150001, which is a monster id (Dust Box), not an item: nothing dropped,
-- and every kill logged an error.
DELETE FROM mob_drops WHERE mobid = 2150000 AND itemid = 2150001;

-- ----------------------------------------------------------------------------------------------------
-- V156_Server_Features.sql
-- Quality-of-life settings (ServerFeatures). No rows: a setting missing here uses its default, which is the
-- server's normal behaviour.
CREATE TABLE IF NOT EXISTS server_features (
    name VARCHAR(64) NOT NULL PRIMARY KEY,
    value VARCHAR(64) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(64) NULL
);

-- Every change saved from the admin page.
CREATE TABLE IF NOT EXISTS server_feature_log (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    old_value VARCHAR(64) NULL,
    new_value VARCHAR(64) NULL,
    changed_by VARCHAR(64) NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_server_feature_log_changed_at (changed_at)
);

-- ----------------------------------------------------------------------------------------------------
-- V157_FriendStory_Love_Letter_Drop.sql
-- FriendStory Hidden Story: Cheese and Corn (33047) needs a Hoodlum's Love Letter (4034349) "from the Mote"; no monster dropped it.
-- Quest item: only drops for characters that have the quest. Each insert is skipped if the drop already exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034349);

-- ----------------------------------------------------------------------------------------------------
-- V158_Specter_Corps_Letter_Drop.sql
-- Know Your Enemy (31674, Angelic Buster): "Letters can only be obtained from Specter soldiers, not Specter Battle Hounds."
-- Nothing dropped the Specter Corps Letter (2430944). Specter soldiers 2400107-2400115 (Battle Hound 2400106 left out).
-- Each insert is skipped if the drop already exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400108, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400108 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400109, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400111, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400111 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400113, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400113 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400114, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400114 AND itemid = 2430944);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400115, 2430944, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400115 AND itemid = 2430944);

-- ----------------------------------------------------------------------------------------------------
-- V159_Lord_Pirate_Mark_Drops.sql
-- Lord Pirate stage 2: each kind of pirate gives only its own mark (Guon asks for Rookie, then Rising, then Veteran).
-- The drop table had every Enraged pirate drop all three marks; LordPirateInstanceHelper drops the right one itself.
DELETE FROM mob_drops WHERE mobid IN (9300114, 9300115, 9300116) AND itemid IN (4001120, 4001121, 4001122);

-- ----------------------------------------------------------------------------------------------------
-- V160_Boss_Piece_Core_Droplet_Drops.sql
-- Chaos Root Abyss and Cygnus "Piece of" boxes: a sure drop of 5 instead of a 35% chance of 1.
UPDATE mob_drops SET chance = 10000, minquant = 5, maxquant = 5
WHERE itemid IN (2434584, 2434585, 2434586, 2434587, 2434588, 2434589)
  AND mobid IN (8900003, 8910000, 8920006, 8930000, 8850011, 8850111);
-- Extraordinary Energy Core (Lotus) and Butterfly Droplet Stone (Lucid): twice as many per drop.
UPDATE mob_drops SET minquant = minquant * 2, maxquant = maxquant * 2
WHERE itemid IN (4001843, 4001879);

-- ----------------------------------------------------------------------------------------------------
-- V161_Spell_Trace_Rate.sql
-- Spell Trace rate 15: drops give 15x the drop tables' Spell Trace amounts, close to what the old level curve gave in
-- Arcane River. Runs once (Flyway); later changes made in the server_rates table are kept.
insert into server_rates (name, value) values ('spell_trace', 15)
    on duplicate key update value = values(value);

-- ----------------------------------------------------------------------------------------------------
-- V162_Hexa_Matrix.sql
-- HEXA Matrix (6th job): the skill, mastery, boost and common cores a character has activated, and its HEXA Stat
-- nodes (one row per node: its three stat types and their levels). Sol Erda and Sol Erda energy are kept in quest
-- record 1489 like the client expects. See HexaModule.
CREATE TABLE IF NOT EXISTS hexa_skills (
    id BIGINT NOT NULL AUTO_INCREMENT,
    charid INT NOT NULL,
    coreid INT NOT NULL,
    level INT NOT NULL DEFAULT 0,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    disabled TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY hexa_skills_char_core (charid, coreid),
    CONSTRAINT hexa_skills_ibfk_1 FOREIGN KEY (charid) REFERENCES characters (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hexa_stats (
    id BIGINT NOT NULL AUTO_INCREMENT,
    charid INT NOT NULL,
    coreid INT NOT NULL,
    stat0 INT NOT NULL DEFAULT -1,
    level0 INT NOT NULL DEFAULT 0,
    stat1 INT NOT NULL DEFAULT -1,
    level1 INT NOT NULL DEFAULT 0,
    stat2 INT NOT NULL DEFAULT -1,
    level2 INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY hexa_stats_char_core (charid, coreid),
    CONSTRAINT hexa_stats_ibfk_1 FOREIGN KEY (charid) REFERENCES characters (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------------------------------
-- V163_Union_Artifacts.sql
-- Legion (Union) Artifact crystals, shared by the account: crystal index 0-8, its level (1-5), its three stat skills
-- and when it expires. Artifact level, EXP, points and mission progress are in quest records
-- (503392 and friends) like the client expects. See UnionArtifactModule.
CREATE TABLE IF NOT EXISTS union_artifacts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    accid INT NOT NULL,
    idx INT NOT NULL,
    level INT NOT NULL DEFAULT 1,
    skill1 INT NOT NULL DEFAULT 0,
    skill2 INT NOT NULL DEFAULT 0,
    skill3 INT NOT NULL DEFAULT 0,
    expiration DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE KEY union_artifacts_acc_idx (accid, idx)
);

-- ----------------------------------------------------------------------------------------------------
-- V164_Removed_Mob_Drops.sql
-- Drops of monsters v265 no longer has: Oura Shogun (Princess No's cut floors) and the Mammoth Moon Bunny.
DELETE FROM mob_drops WHERE mobid IN (9450013, 9450034);

-- ----------------------------------------------------------------------------------------------------
-- V165_Login_Quick_Start.sql
-- Login screen Quick Start: last played and favourite character per login (world, channel index, character id).
CREATE TABLE IF NOT EXISTS user_quick_start (
    userid INT NOT NULL PRIMARY KEY,
    lastworld INT NOT NULL DEFAULT -1,
    lastchannel INT NOT NULL DEFAULT 0,
    lastcharid INT NOT NULL DEFAULT 0,
    favworld INT NOT NULL DEFAULT -1,
    favchannel INT NOT NULL DEFAULT 0,
    favcharid INT NOT NULL DEFAULT 0
);

-- ----------------------------------------------------------------------------------------------------
-- V166_Inner_Ability_Presets.sql
-- Inner Ability presets (v265 has 3): each line belongs to one preset (0-2); existing lines are preset 0.
ALTER TABLE characterpotentials ADD COLUMN preset INT NOT NULL DEFAULT 0;

-- ----------------------------------------------------------------------------------------------------
-- V167_Burning_Types.sql
-- Burning characters: type (0 none, 1 Hyper Burning MAX, 2 Burning Beyond), the level it started from, and whether a
-- rebirth ended it. `burning` stays as "burning now" for outside readers.
ALTER TABLE characterstats
    ADD COLUMN burningType TINYINT NOT NULL DEFAULT 0,
    ADD COLUMN burningStartLevel INT NOT NULL DEFAULT 0,
    ADD COLUMN burningEnded TINYINT NOT NULL DEFAULT 0;

-- @burning characters become Hyper Burning MAX ones from level 10
UPDATE characterstats SET burningType = 1, burningStartLevel = 10 WHERE burning = 1;

-- ----------------------------------------------------------------------------------------------------
-- V168_Beauty_Albums.sql
-- Beauty Album (saved hair, face and skin slots) per character, plus Angelic Buster's dress-up look.
-- Slot lists are comma-separated ids, -1 for an empty slot. Dress-up ids of 0 mean the job's default look.
CREATE TABLE IF NOT EXISTS beauty_albums (
    charid INT NOT NULL,
    haircap INT NOT NULL DEFAULT 0,
    facecap INT NOT NULL DEFAULT 0,
    skincap INT NOT NULL DEFAULT 0,
    hairs VARCHAR(2048) NOT NULL DEFAULT '',
    faces VARCHAR(2048) NOT NULL DEFAULT '',
    skins VARCHAR(1024) NOT NULL DEFAULT '',
    dressface INT NOT NULL DEFAULT 0,
    dresshair INT NOT NULL DEFAULT 0,
    dressskin INT NOT NULL DEFAULT 0,
    dresson TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (charid)
);

-- ----------------------------------------------------------------------------------------------------
-- V169_Arcane_Skip_Toggles.sql
-- Arcane River story skip and its free Arcane Symbols: both on (the defaults); Server Control toggles them.
INSERT IGNORE INTO server_features (name, value, updated_by) VALUES
    ('arcane_skip', '1', 'migration'),
    ('arcane_skip_symbols', '1', 'migration');

-- ----------------------------------------------------------------------------------------------------
-- V170_Swordie_Coin_Shop_Off.sql
-- Swordie Coins are off for now: the Swordie Plus shop (Smithy 9062231, the only shop priced in Swordie Coins,
-- 4310096) and the Inferno Wolf's coin drops (mob 9101078, the Polo/Fritto bounty portal) are removed. Coins
-- players already hold are left alone. The revert SQL that puts every removed row back is kept with the backup.
DELETE FROM shopitems WHERE shopid = 9062231 AND tokenitemid = 4310096;
DELETE FROM mob_drops WHERE mobid = 9101078 AND itemid = 4310096;

-- ----------------------------------------------------------------------------------------------------
-- V171_Parcels.sql
-- Duey delivery packages. The item (if any) is a row in items with no inventory; itemid 0 means mesos only.
CREATE TABLE IF NOT EXISTS parcels (
    id BIGINT NOT NULL AUTO_INCREMENT,
    receiverid INT NOT NULL,
    senderid INT NOT NULL DEFAULT 0,
    sendername VARCHAR(13) NOT NULL DEFAULT '',
    meso BIGINT NOT NULL DEFAULT 0,
    memo VARCHAR(200) NOT NULL DEFAULT '',
    quick TINYINT NOT NULL DEFAULT 0,
    senttime BIGINT NOT NULL DEFAULT 0,
    itemid BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_parcels_receiver (receiverid)
);

-- ----------------------------------------------------------------------------------------------------
-- V172_Equip_Cube_Ceiling.sql
-- cubes used on an item since its last rank-up, for the cube ceiling (Etc/CubeCeiling.img)
alter table items add column cubeceilingmain smallint not null default 0;
alter table items add column cubeceilingbonus smallint not null default 0;

-- ----------------------------------------------------------------------------------------------------
-- V173_Cash_Shop_Coupons.sql
-- Cash Shop coupon codes (entered in the Cash Shop's coupon box). Codes are matched in upper case.
-- itemid 0 = no item; cash items go to the Cash Shop locker, other items to the inventory.
-- maxuses 0 = no overall limit (each account can still use a code once); expires = epoch ms, 0 = never.
CREATE TABLE IF NOT EXISTS coupons (
    code VARCHAR(32) NOT NULL,
    itemid INT NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 1,
    nx INT NOT NULL DEFAULT 0,
    maplepoints INT NOT NULL DEFAULT 0,
    mesos BIGINT NOT NULL DEFAULT 0,
    maxuses INT NOT NULL DEFAULT 0,
    uses INT NOT NULL DEFAULT 0,
    expires BIGINT NOT NULL DEFAULT 0,
    note VARCHAR(100) NOT NULL DEFAULT '',
    PRIMARY KEY (code)
);

CREATE TABLE IF NOT EXISTS coupon_uses (
    code VARCHAR(32) NOT NULL,
    accountid INT NOT NULL,
    charid INT NOT NULL DEFAULT 0,
    usedtime BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (code, accountid)
);

-- ----------------------------------------------------------------------------------------------------
-- V174_Daily_Gift_Edit.sql
-- Daily Gift calendar, editable in Server Control (loaded at start, reloaded on request).
-- daily_gifts: one row per calendar day; expire_days 0 = the item doesn't expire.
-- daily_gift_settings: monster_goal (enemies to defeat a day), min_level (level needed).
CREATE TABLE IF NOT EXISTS daily_gifts (
    day INT NOT NULL,
    itemid INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    expire_days INT NOT NULL DEFAULT 0,
    PRIMARY KEY (day)
);

CREATE TABLE IF NOT EXISTS daily_gift_settings (
    name VARCHAR(32) NOT NULL,
    value VARCHAR(32) NOT NULL,
    PRIMARY KEY (name)
);

INSERT IGNORE INTO daily_gifts (day, itemid, quantity, expire_days) VALUES
    (1, 5040004, 1, 7),
    (2, 2450064, 2, 0),
    (3, 1122017, 1, 3),
    (4, 2710028, 10, 0),
    (5, 4001832, 500, 0),
    (6, 2432970, 1, 0),
    (7, 5680410, 1, 0),
    (8, 2710028, 50, 0),
    (9, 2450064, 2, 0),
    (10, 2710034, 20, 0),
    (11, 2048716, 1, 0),
    (12, 2000005, 100, 0),
    (13, 2350000, 1, 0),
    (14, 2710028, 50, 0),
    (15, 5040004, 1, 7),
    (16, 2450064, 2, 0),
    (17, 5205007, 1, 0),
    (18, 5062800, 1, 0),
    (19, 4001832, 500, 0),
    (20, 2430909, 1, 0),
    (21, 5205011, 1, 0),
    (22, 2023072, 1, 0),
    (23, 1113227, 1, 15),
    (24, 2028333, 5, 0),
    (25, 2710028, 50, 0),
    (26, 2049705, 1, 0),
    (27, 2028333, 20, 0),
    (28, 2730007, 30, 0);

INSERT IGNORE INTO daily_gift_settings (name, value) VALUES
    ('monster_goal', '300'),
    ('min_level', '33');

-- ----------------------------------------------------------------------------------------------------
-- V175_Remove_Old_Gift_Box_Rows.sql
-- Gift box rows from systems that were removed: starter supplies and Sprout medal, Hot Time, onboarding checklist,
-- burning level supplies, Hyper Burning MAX gifts, Swordie Coin overflow. Admin and first-rebirth gifts stay.
-- The rows are copied to first_enter_rewards_removed first; put them back with
-- INSERT INTO first_enter_rewards SELECT * FROM first_enter_rewards_removed;
CREATE TABLE IF NOT EXISTS first_enter_rewards_removed LIKE first_enter_rewards;

INSERT IGNORE INTO first_enter_rewards_removed
SELECT * FROM first_enter_rewards
WHERE descr IN ('Starter supplies', 'Sprout medal', 'Hot Time', 'Onboarding checklist')
   OR descr LIKE 'Level % supplies'
   OR descr LIKE 'Hyper Burning MAX%'
   OR descr LIKE '% Swordie Coins (%';

DELETE FROM first_enter_rewards
WHERE descr IN ('Starter supplies', 'Sprout medal', 'Hot Time', 'Onboarding checklist')
   OR descr LIKE 'Level % supplies'
   OR descr LIKE 'Hyper Burning MAX%'
   OR descr LIKE '% Swordie Coins (%';

-- ----------------------------------------------------------------------------------------------------
-- V176_Equip_Exceptional.sql
-- exceptional enhancement stages applied to an equip
alter table items add column exceptionalcount tinyint not null default 0;

-- ----------------------------------------------------------------------------------------------------
-- V177_Maple_Achievements.sql
-- Maple Achievements, per account (GMS keeps them world-wide). missionid -1 = the whole achievement,
-- -2 = its reward was claimed. status: 1 in progress, 2 complete. unlocktime is a FileTime long.
CREATE TABLE IF NOT EXISTS achievements (
    accid INT NOT NULL,
    infoid INT NOT NULL,
    missionid TINYINT NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    value BIGINT NOT NULL DEFAULT 0,
    unlocktime BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (accid, infoid, missionid)
);

-- ----------------------------------------------------------------------------------------------------
-- V178_Quest_Story_Drops_3.sql
-- Quest items the v265 walk found nothing giving (quest audit 2026-10-24):
--   38876 Deal with the Shadowdealers 3: Specter Ambassador Scythe (4000736) from the Reaper Specter (2400115, Heliseum)
--   38130 How to Brew the Special Drink: Spare Memory Chip (4037171) from the Puxillian Scrounger (8645203, Hotel Arcus)
--   36729 Getting Ingredients: Sleepy Grobbler Stalk (4000704) from the Sleepy Grobbler (2400000, Pantheon)
--   3248 Operation Strategy 1: Timer's Egg (4031991) from Timer (5220003, Ludibrium Clocktower)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400115, 4000736, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400115 AND itemid = 4000736);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645203, 4037171, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645203 AND itemid = 4037171);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400000, 4000704, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400000 AND itemid = 4000704);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5220003, 4031991, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5220003 AND itemid = 4031991);
--   36469 The Black Wings and the Puppeteer (Cygnus) / 36913 Check Out the Orange Mushroom (Aran): the puppets from the
--   Cynical Orange Mushroom (2300102, Suspicious Hill 100010001)
--   23187 An Attempt to Escape (Verne Mine): Broken Guard Robot Baton (4037321) from the Guard Robot (6150000)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2300102, 4036879, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300102 AND itemid = 4036879);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2300102, 4037293, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300102 AND itemid = 4037293);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 6150000, 4037321, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6150000 AND itemid = 4037321);
--   36947 Find the Key (Aran): v265 asks for 5 Wooden Keys 4037302 (the old 4032326 still drops too) from the Primitive
--   Thief Boar (9001024, Dangerous Rocky Mountain 930010001)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9001024, 4037302, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001024 AND itemid = 4037302);
--   38323 Dad's Too Busy 2 (Odium): Angler Robot Energy Source (4036894) from the Angler Bomb, Melee and Electro Testers
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645293, 4036894, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645293 AND itemid = 4036894);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645294, 4036894, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645294 AND itemid = 4036894);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645295, 4036894, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645295 AND itemid = 4036894);
--   38821 [Heliseum] Popo's Tears: Popo's Jewel (4037237) from the Yellow Speeyor (2400102)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400102, 4037237, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4037237);
--   37853 [Esfera] In Search of the Signal Flare: v265 asks for Flare 4037358 (Ahtuin 8644500 dropped the old 4036449)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644500, 4037358, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644500 AND itemid = 4037358);

-- v265 renumbered these quest items; the monsters that drop the old item (same name) now drop the one the quest asks for
--   1567 [Lion King's Castle] Key to the Rose Garden: Key to the Rose Garden (4037314)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8210004, 4037314, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8210004 AND itemid = 4037314);
--   2617 Helping Don Giovanni with His Problem: Toy Plane (4033179)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8145006, 4033179, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145006 AND itemid = 4033179);
--   3272 Spiruna's Black Crystal: Cracked Black Crystal (4037251)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5120001, 4037251, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120001 AND itemid = 4037251);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5120002, 4037251, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120002 AND itemid = 4037251);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5120003, 4037251, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120003 AND itemid = 4037251);
--   3815 Finding the Ingredients for Tae Sang: Ginseng Concentrate (2022131)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8230028, 2022131, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230028 AND itemid = 2022131);
--   8142 Todd's How-to-Hunt: Toy Drum (4000301)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3230400, 4000301, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230400 AND itemid = 4000301);
--   20974 [Silent Crusade] Her Request: Wild Kargo Eye (4037311)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600226, 4037311, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600226 AND itemid = 4037311);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 6230100, 4037311, 1000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6230100 AND itemid = 4037311);
--   20994 [Verne Mine] Gabrielle's Request 1: Guard Robot L's Chip (4037286)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 7150004, 4037286, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7150004 AND itemid = 4037286);
--   31700 [Daily] Supply Delivery - Yellow Spee Hide: Yellow Speeyor Fur Coat (4033556)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400102, 4033556, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4033556);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400105, 4033556, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033556);
--   31701 [Daily] Supply Delivery - Dinor's Tenderloin: Dinoram Tenderloin (4033557)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400104, 4033557, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4033557);
--   31702 [Daily] Supply Delivery - Animal Oil: Animal Oil (4033558)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400101, 4033558, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4033558);
--   31703 [Daily] Supply Delivery - Sharp Fang: Dinodon Sharp Fang (4033559)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400102, 4033559, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4033559);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400105, 4033559, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033559);
--   31716 [Daily] Merchant Crew - Specter Candy: Specter Candy (4033565)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400106, 4033565, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4033565);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400107, 4033565, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033565);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400110, 4033565, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033565);
--   31717 [Daily] Merchant Crew - Tamer's Whip: Tamer's Whip (4033566)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400106, 4033566, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4033566);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400107, 4033566, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033566);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400110, 4033566, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033566);
--   31722 [Daily] Merchant Crew - Specter General Emblem: Specter Commander Badge (4033571)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400109, 4033571, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4033571);
--   32478 [Weekly Quest] The Complete Set: Frozen Energy (4033931)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600900, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600900 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600901, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600901 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600902, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600902 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600903, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600903 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600904, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600904 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600905, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600905 AND itemid = 4033931);
--   33234 Find the Pipe Piece: Pipe Piece (4034271)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8250013, 4034271, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8250013 AND itemid = 4034271);
--   33237 Find the Pipe Piece: Pipe Piece (4034278)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8250013, 4034278, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8250013 AND itemid = 4034278);
--   36463 Who Is Baroq? Part 2: The Lost Treasure (4036863)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9001009, 4036863, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001009 AND itemid = 4036863);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9300743, 4036863, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300743 AND itemid = 4036863);
--   36470 [Cygnus Knights] Checking on Henesys: Berserk Horny Mushroom Puppet (4036880)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1210102, 4036880, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210102 AND itemid = 4036880);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2110200, 4036880, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2110200 AND itemid = 4036880);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9300736, 4036880, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300736 AND itemid = 4036880);
--   36498 [Cygnus Knights] Gathering Intel: Working Rue Battery (4036881)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3150000, 4036881, 1000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3150000 AND itemid = 4036881);
--   36620 The Keepsake: Empress Aria's Keepsake (4036877)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1140100, 4036877, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4036877);
--   36752 Enemy Mastermind: Magnus's Letter (4037236)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400009, 4037236, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400009 AND itemid = 4037236);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400012, 4037236, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400012 AND itemid = 4037236);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400114, 4037236, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400114 AND itemid = 4037236);
--   36914 When Mushrooms Cry: Crying Blue Mushroom Doll (4037294)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2220110, 4037294, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2220110 AND itemid = 4037294);
--   36921 Ghost Stumps: Smirking Ghost Stump Puppet (4037295)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1140100, 4037295, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4037295);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1140130, 4037295, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140130 AND itemid = 4037295);
--   37163 [Elodin] Hay Necessities: Hay (4036501)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600200, 4036501, 2000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600200 AND itemid = 4036501);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3501103, 4036501, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501103 AND itemid = 4036501);
--   41102 Reminiscing : Coconut (4000968)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3401001, 4000968, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401001 AND itemid = 4000968);

-- ----------------------------------------------------------------------------------------------------
-- V179_Union_Champions.sql
-- Legion Champions, per account: up to 6 slots (the client's quest records 504782-504787 hold cid/rank/time).
-- rank: 0 = C ... 5 = SSS.
CREATE TABLE IF NOT EXISTS union_champions (
    accid INT NOT NULL,
    slot TINYINT NOT NULL,
    charid INT NOT NULL,
    `rank` TINYINT NOT NULL DEFAULT 0,
    regtime BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (accid, slot)
);

-- ----------------------------------------------------------------------------------------------------
-- V180_Town_Shops_v265.sql
-- Shops the v265 towns were missing.
-- 1) Shops from the reference v265.3 server (InitTables_shops.sql) that our DB lacked; several NPC scripts already
--    opened them (Matilda in Henesys, Cello in Zero's Temple, Murt, Mono, Sagacious Goemon, Legends/Heroes shops).
-- 2) General stores for shopkeepers flagged as shops in v265 Npc.wz with no stock (Lumiere, Kritias, Veritas, Grandis
--    towns, Hejin, Xuanshan, Oort ...): the town general store goods (potions, return scroll, arrows, pet food) at
--    this server's prices, as V104 did for the Arcane/Grandis merchants.
-- Each row is skipped when the shop already has that item at that price, so re-running adds nothing.
-- 9000132 Wee Moon Bunny
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9000132, 1112762, 0, 4310095, 500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9000132 AND itemid = 1112762 AND price = 0 AND tokenitemid = 4310095 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9000132, 2435285, 0, 4310095, 1000, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9000132 AND itemid = 2435285 AND price = 0 AND tokenitemid = 4310095 AND tokenprice = 1000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9000132, 2436114, 0, 4310095, 500, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9000132 AND itemid = 2436114 AND price = 0 AND tokenitemid = 4310095 AND tokenprice = 500);
-- 2020005 Alcaster
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 2050004, 400, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 2050004 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 2050003, 300, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 2050003 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 4006001, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 4006001 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2020005, 4006000, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2020005 AND itemid = 4006000 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
-- 1055000 Lumi (Weapon/Armor Seller)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002001, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002001 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060023, 2800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060023 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040033, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040033 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061038, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061038 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1442079, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1442079 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1422000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1422000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060033, 8000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060033 AND price = 8000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332006, 7000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332006 AND price = 7000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332009, 42000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332009 AND price = 42000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061037, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061037 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041040, 9000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041040 AND price = 9000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060024, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060024 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060025, 4800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060025 AND price = 4800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332013, 15000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332013 AND price = 15000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002097, 7400, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002097 AND price = 7400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061042, 12000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061042 AND price = 12000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041044, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041044 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041060, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041060 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1472000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1472000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1342000, 10000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1342000 AND price = 10000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041050, 45000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041050 AND price = 45000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061053, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061053 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061056, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061056 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002150, 20000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002150 AND price = 20000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1412001, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1412001 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1402001, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1402001 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1432001, 7000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1432001 AND price = 7000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060039, 19000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060039 AND price = 19000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040050, 16000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040050 AND price = 16000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1322009, 20000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1322009 AND price = 20000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332012, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332012 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1342001, 42000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1342001 AND price = 42000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060043, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060043 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040057, 45000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040057 AND price = 45000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072028, 4500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072028 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332008, 10000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332008 AND price = 10000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040058, 45000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040058 AND price = 45000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1302007, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1302007 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040034, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040034 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332002, 8000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332002 AND price = 8000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1432000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1432000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040033, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040033 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041057, 18000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041057 AND price = 18000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060023, 2800, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060023 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072043, 2000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072043 AND price = 2000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002175, 30000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002175 AND price = 30000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002110, 4000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002110 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1041045, 5000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1041045 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1060044, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1060044 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332010, 22000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332010 AND price = 22000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061033, 8000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061033 AND price = 8000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002130, 12000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002130 AND price = 12000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1061046, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1061046 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072066, 9000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072066 AND price = 9000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1072031, 4500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1072031 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1040044, 9000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1040044 AND price = 9000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332000, 4000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332000 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1332004, 38000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1332004 AND price = 38000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1055000, 1002125, 900, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1055000 AND itemid = 1002125 AND price = 900 AND tokenitemid = 0 AND tokenprice = 0);
-- 9201059 Kyle (Weapon Seller)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1382135, 22500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1382135 AND price = 22500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1302186, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1302186 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1312082, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1312082 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1372111, 40500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1372111 AND price = 40500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1332009, 44500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1332009 AND price = 44500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1432110, 62500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1432110 AND price = 62500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1442001, 62500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1442001 AND price = 62500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1322116, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1322116 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1332162, 42500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1332162 AND price = 42500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1422083, 47500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1422083 AND price = 47500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1452141, 152500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1452141 AND price = 152500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1462129, 32500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1462129 AND price = 32500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1302068, 352500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1302068 AND price = 352500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1472150, 22500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1472150 AND price = 22500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1402123, 152500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1402123 AND price = 152500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201059, 1412081, 47500, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201059 AND itemid = 1412081 AND price = 47500 AND tokenitemid = 0 AND tokenprice = 0);
-- 9031015 Angelica (Alchemy Recipe Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512000, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512000 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512001, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512001 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512002, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512002 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512003, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512003 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512008, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512008 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512009, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512009 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512010, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512010 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512011, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512011 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512016, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512016 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512017, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512017 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512018, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512018 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512019, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512019 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512024, 50000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512024 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512025, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512025 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512026, 150000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512026 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512027, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512027 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512032, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512032 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512033, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512033 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512034, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512034 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512037, 100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512037 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512038, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512038 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512039, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512039 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512069, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512069 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512070, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512070 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512071, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512071 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512072, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512072 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512073, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512073 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512074, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512074 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512075, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512075 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512076, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512076 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512077, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512077 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512078, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512078 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512079, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512079 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512080, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512080 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512081, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512081 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512082, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512082 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512083, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512083 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512084, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512084 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512085, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512085 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512086, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512086 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512087, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512087 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512088, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512088 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512139, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512139 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512140, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512140 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512141, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512141 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512142, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512142 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512143, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512143 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512144, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512144 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512145, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512145 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512146, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512146 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512147, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512147 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512148, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512148 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512159, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512159 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512160, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512160 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512161, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512161 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512162, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512162 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512163, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512163 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512164, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512164 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512165, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512165 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512166, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512166 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512167, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512167 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512168, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512168 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512179, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512179 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512180, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512180 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512181, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512181 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512182, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512182 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512183, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512183 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512184, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512184 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512185, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512185 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512186, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512186 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512187, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512187 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512188, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512188 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512199, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512199 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512200, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512200 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512201, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512201 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512202, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512202 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512203, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512203 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512204, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512204 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512205, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512205 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512206, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512206 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512207, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512207 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512208, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512208 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512219, 200000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512219 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512220, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512220 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512221, 400000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512221 AND price = 400000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512222, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512222 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512223, 600000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512223 AND price = 600000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512224, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512224 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512225, 800000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512225 AND price = 800000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512226, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512226 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512227, 1000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512227 AND price = 1000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512228, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512228 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512239, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512239 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512240, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512240 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512241, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512241 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512242, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512242 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512243, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512243 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512249, 300000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512249 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512250, 500000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512250 AND price = 500000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512251, 700000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512251 AND price = 700000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512252, 900000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512252 AND price = 900000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512253, 1100000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512253 AND price = 1100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512262, 2000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512262 AND price = 2000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9031015, 2512263, 5000000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9031015 AND itemid = 2512263 AND price = 5000000 AND tokenitemid = 0 AND tokenprice = 0);
-- 9075110 Vendor-C2 (Coin Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132116, 0, 4310060, 200, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132116 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132117, 0, 4310060, 400, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132117 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132118, 0, 4310060, 600, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132118 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 600);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132119, 0, 4310060, 800, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132119 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 800);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132120, 0, 4310060, 1000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132120 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132121, 0, 4310060, 1500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132121 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132122, 0, 4310060, 2000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132122 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132146, 0, 4310060, 400, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132146 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132147, 0, 4310060, 800, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132147 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 800);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132148, 0, 4310060, 1000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132148 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132149, 0, 4310060, 1500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132149 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 1500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 1132150, 0, 4310060, 2000, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 1132150 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001515, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001515 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001516, 400, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001516 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001517, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001517 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001519, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001519 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001520, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001520 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001000, 3200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001000 AND price = 3200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001001, 2300, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001001 AND price = 2300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2001002, 4000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2001002 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2020012, 4500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2000036, 5600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2000036 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2020045, 8100, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2020045 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2020046, 10200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2020046 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2030000, 400, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2060000, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2061000, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2060001, 10, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2060001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2061001, 10, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2061001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2720000, 20000000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2720000 AND price = 20000000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2070000, 500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2070000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 2330000, 600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 2330000 AND price = 600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604000, 0, 4310060, 150, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604000 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 150);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604001, 0, 4310060, 300, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604001 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 300);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604002, 0, 4310060, 50, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604002 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604003, 0, 4310060, 80, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604003 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 80);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604004, 0, 4310060, 100, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604004 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604005, 0, 4310060, 150, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604005 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 150);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602100, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602100 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602101, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602101 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602102, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602102 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602103, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602103 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602104, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602104 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602105, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602105 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602106, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602106 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602107, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602107 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602108, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602108 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602108, 0, 4310060, 450, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602108 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 450);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604011, 0, 4310060, 600, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604011 AND price = 0 AND tokenitemid = 4310060 AND tokenprice = 600);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604006, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604006 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604007, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604007 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604009, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604009 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3604010, 32000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3604010 AND price = 32000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600000, 15000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600000 AND price = 15000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600001, 30000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600001 AND price = 30000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600002, 60000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600002 AND price = 60000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600003, 100000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600003 AND price = 100000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600004, 150000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600004 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600005, 200000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600005 AND price = 200000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600100, 16000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600100 AND price = 16000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600101, 36000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600101 AND price = 36000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600102, 58000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600102 AND price = 58000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600103, 76000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600103 AND price = 76000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600104, 102000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600104 AND price = 102000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600200, 90000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600200 AND price = 90000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600201, 120000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600201 AND price = 120000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600202, 180000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600202 AND price = 180000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3600203, 360000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3600203 AND price = 360000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602000, 130000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602000 AND price = 130000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602001, 300000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602001 AND price = 300000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9075110, 3602002, 560000, 0, 0, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9075110 AND itemid = 3602002 AND price = 560000 AND tokenitemid = 0 AND tokenprice = 0);
-- 9010037 Randolf (Legends Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022794, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022794 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022795, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022795 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022796, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022796 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022797, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022797 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022798, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022798 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2022799, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2022799 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2431232, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2431232 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2431233, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2431233 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021013, 0, 4310098, 3, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021013 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021014, 0, 4310098, 5, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021014 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021015, 0, 4310098, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021015 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 4021016, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 4021016 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2003048, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2003048 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2003049, 0, 4310098, 50, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2003049 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510171, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510171 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510172, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510172 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510176, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510176 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2510240, 0, 4310098, 15, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2510240 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010037, 2615000, 0, 4310098, 40, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010037 AND itemid = 2615000 AND price = 0 AND tokenitemid = 4310098 AND tokenprice = 40);
-- 3003537 Dimo (Arachno Coin Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1542117, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1542117 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1552119, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1552119 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1252098, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1252098 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 2048918, 0, 4310249, 4, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 2048918 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 4310217, 0, 4310249, 10, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 4310217 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1212120, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1212120 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1272017, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1272017 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1282017, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1282017 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1292018, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1292018 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1292018, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1292018 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1213018, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1213018 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1222113, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1222113 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1232113, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1232113 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1242121, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1242121 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1262039, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1262039 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1302343, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1302343 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1312203, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1312203 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1322255, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1322255 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1332279, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1332279 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1342104, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1342104 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1362140, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1362140 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1372228, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1372228 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1382265, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1382265 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1402259, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1402259 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1412181, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1412181 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1422189, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1422189 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1432218, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1432218 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1442274, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1442274 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1452257, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1452257 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1462243, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1462243 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1472265, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1472265 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1482221, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1482221 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1492235, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1492235 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1522143, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1522143 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1532150, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1532150 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1582023, 0, 4310249, 15, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1582023 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 15);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004808, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004808 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004809, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004809 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004810, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004810 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004811, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004811 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1004812, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1004812 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053063, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053063 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053064, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053064 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053065, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053065 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053066, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053066 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1053067, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1053067 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152196, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152196 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152197, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152197 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152198, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152198 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152199, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152199 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3003537, 1152200, 0, 4310249, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3003537 AND itemid = 1152200 AND price = 0 AND tokenitemid = 4310249 AND tokenprice = 6);
-- 2160000 Murt
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1252087, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1252087 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1542102, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1542102 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1552103, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1552103 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 2048913, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 2048913 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1212102, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1212102 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1222096, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1222096 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1232096, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1232096 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1242103, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1242103 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1262014, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1262014 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1302316, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1302316 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1312186, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1312186 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1322237, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1322237 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1332261, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1332261 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1362122, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1362122 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1372208, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1372208 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1382246, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1382246 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1402237, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1402237 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1412179, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1412179 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1422186, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1422186 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1432201, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1432201 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1442255, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1442255 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1452239, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1452239 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1462226, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1462226 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1472248, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1472248 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1482203, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1482203 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1492213, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1492213 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1522125, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1522125 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1532131, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1532131 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1582014, 0, 4310010, 6, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1582014 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 6);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004234, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004234 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004235, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004235 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004236, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004236 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004237, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004237 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1004238, 0, 4310010, 3, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1004238 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 3);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052804, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052804 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052805, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052805 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052806, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052806 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052807, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052807 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1052808, 0, 4310010, 4, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1052808 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 4);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072972, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072972 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072973, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072973 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072974, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072974 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072975, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072975 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1072976, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1072976 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082613, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082613 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082614, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082614 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082615, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082615 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082616, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082616 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1082617, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1082617 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102713, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102713 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102714, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102714 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102715, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102715 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102716, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102716 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2160000, 1102717, 0, 4310010, 2, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2160000 AND itemid = 1102717 AND price = 0 AND tokenitemid = 4310010 AND tokenprice = 2);
-- 2400003 Cello (Quartermaster)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2431935, 0, 4310085, 5, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2431935 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2431936, 0, 4310085, 5, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2431936 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1190900, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1190900 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2550016, 0, 4310085, 30, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2550016 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 30);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1003911, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1003911 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1052632, 0, 4310085, 150, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1052632 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 150);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1082535, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1082535 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1072837, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1072837 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1102594, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1102594 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1132232, 0, 4310085, 70, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1132232 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 70);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1152138, 0, 4310085, 70, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1152138 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 70);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048913, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048913 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048906, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048906 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048903, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048903 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048904, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048904 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2431854, 0, 4310085, 10, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2431854 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4033923, 0, 4310085, 5, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4033923 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 5);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510704, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510704 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510705, 0, 4310085, 700, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510705 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 700);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510706, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510706 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510707, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510707 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2510708, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2510708 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2511178, 0, 4310085, 400, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2511178 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2511179, 0, 4310085, 400, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2511179 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4021040, 0, 4310085, 10, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4021040 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 10);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1003419, 0, 4310085, 100, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1003419 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 100);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1022073, 0, 4310085, 200, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1022073 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1032101, 0, 4310085, 300, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1032101 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 300);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1122118, 0, 4310085, 400, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1122118 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 400);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1003267, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1003267 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 1022123, 0, 4310085, 300, 0, 0, 7, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 1022123 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 300);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 2048908, 0, 4310085, 500, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 2048908 AND price = 0 AND tokenitemid = 4310085 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024004, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024004 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024005, 300, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024005 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024006, 500, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024006 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024007, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024007 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025000, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025000 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025001, 5000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025001 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025002, 10000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025002 AND price = 10000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025003, 50000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025003 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4340000, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4340000 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024000, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024000 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024001, 300, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024001 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024002, 500, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4024003, 1000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4024003 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025004, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025004 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025005, 200, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025005 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025006, 400, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025006 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025007, 800, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025007 AND price = 800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025008, 50, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025008 AND price = 50 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025009, 100, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025009 AND price = 100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025010, 200, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025010 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4025011, 400, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4025011 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400003, 4021017, 5000, 0, 0, 0, 0, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400003 AND itemid = 4021017 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
-- 3001217 Mono (Weapon Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002022, 2100, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002022 AND price = 2100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002019, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002019 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022190, 3000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022190 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001500, 50, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001500 AND price = 50 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020045, 8100, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020045 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001513, 2300, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001513 AND price = 2300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022192, 600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022192 AND price = 600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002020, 2800, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002020 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001512, 3200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001512 AND price = 3200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020013, 5600, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001503, 200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001503 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022191, 1000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022191 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001514, 4000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001514 AND price = 4000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002025, 1500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002025 AND price = 1500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002018, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002018 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002016, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002016 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002021, 2800, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002021 AND price = 2800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2030000, 400, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2061000, 2, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2061000 AND price = 2 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2001501, 160, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2001501 AND price = 160 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020012, 4500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022177, 620, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022177 AND price = 620 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002026, 3800, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002026 AND price = 3800 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022195, 150000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022195 AND price = 150000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002017, 5000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002017 AND price = 5000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2020046, 10200, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2020046 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2060000, 2, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2060000 AND price = 2 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2010001, 106, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2010001 AND price = 106 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022189, 1000, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022189 AND price = 1000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2022174, 320, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2022174 AND price = 320 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 2002024, 1500, 0, 0, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 2002024 AND price = 1500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1272000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1272000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1272001, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1272001 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1282000, 3000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1282000 AND price = 3000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001217, 1282001, 40000, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001217 AND itemid = 1282001 AND price = 40000 AND tokenitemid = 0 AND tokenprice = 0);
-- 9001127 Spiegelmann (Heroes Coin Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017006, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017006 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017007, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017007 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017008, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017008 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017009, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017009 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017010, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017010 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017011, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017011 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017012, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017012 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017013, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017013 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017014, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017014 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017015, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017015 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 3017016, 0, 4310113, 2000, 0, 0, 3, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 3017016 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000271, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000271 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000272, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000272 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000273, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000273 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000161, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000161 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000162, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000162 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000163, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000163 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000249, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000249 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000250, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000250 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5000251, 0, 4310113, 2000, 0, 0, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5000251 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 2000);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 2434891, 0, 4310113, 200, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 2434891 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 2049116, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 2049116 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 2049601, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 2049601 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5064100, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5064100 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001127, 5064000, 0, 4310113, 50, 0, 0, 2, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001127 AND itemid = 5064000 AND price = 0 AND tokenitemid = 4310113 AND tokenprice = 50);
-- 9330278 Sagacious Goemon (Totem Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1202089, 0, 4310075, 1550, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1202089 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1550);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1202090, 0, 4310075, 1550, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1202090 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1550);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1202091, 0, 4310075, 1550, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1202091 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1550);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1142856, 0, 4310075, 1200, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1142856 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1200);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1142783, 0, 4310075, 500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1142783 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1142784, 0, 4310075, 750, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1142784 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 750);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1162033, 0, 4310075, 500, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1162033 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1162034, 0, 4310075, 750, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1162034 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 750);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 1162035, 0, 4310075, 1250, 0, 0, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 1162035 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 1250);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 2433209, 0, 4310075, 500, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 2433209 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 500);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9330278, 2433210, 0, 4310075, 750, 0, 0, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9330278 AND itemid = 2433210 AND price = 0 AND tokenitemid = 4310075 AND tokenprice = 750);
-- 9001174 Cosmic Vending Machine (13th Anniversary Galaxy Star Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436945, 0, 0, 0, 16180, 50, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436945 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436946, 0, 0, 0, 16180, 250, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436946 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436947, 0, 0, 0, 16180, 250, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436947 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436948, 0, 0, 0, 16180, 250, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436948 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2630756, 0, 0, 0, 16180, 900, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2630756 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2433384, 0, 0, 0, 16180, 100, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2433384 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437020, 0, 0, 0, 16180, 900, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437020 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2432970, 0, 0, 0, 16180, 100, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2432970 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2049705, 0, 0, 0, 16180, 100, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2049705 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2710000, 0, 0, 0, 16180, 5, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2710000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2049500, 0, 0, 0, 16180, 20, 6, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2049500 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436942, 0, 0, 0, 16180, 300, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436942 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436951, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436951 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436952, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436952 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015847, 0, 0, 0, 16180, 1000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015847 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015642, 0, 0, 0, 16180, 1000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015642 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3010964, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3010964 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437021, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437021 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437022, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437022 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2437023, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2437023 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015603, 0, 0, 0, 16180, 1000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015603 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 3015157, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 3015157 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2435719, 0, 0, 0, 16180, 100, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2435719 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350004, 0, 0, 0, 16180, 80, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350004 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350005, 0, 0, 0, 16180, 80, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350005 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350006, 0, 0, 0, 16180, 80, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350006 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2436954, 0, 0, 0, 16180, 10, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2436954 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2430909, 0, 0, 0, 16180, 500, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2430909 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2431789, 0, 0, 0, 16180, 30, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2431789 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2431790, 0, 0, 0, 16180, 30, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2431790 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2350000, 0, 0, 0, 16180, 3000, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2350000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9001174, 2028349, 0, 0, 0, 16180, 100, 7, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9001174 AND itemid = 2028349 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 9010038 Lucia (Legends Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 4000313, 0, 0, 0, 14693, 10000, 5, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 4000313 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680047, 0, 0, 0, 14693, 29900, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680047 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680048, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680048 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5062024, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5062024, 0, 0, 0, 14693, 100000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5062024, 0, 0, 0, 14693, 500000, 1, 0, 0, 0, 50, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1672020, 0, 0, 0, 14693, 250000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1672020 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2049610, 0, 0, 0, 14693, 1000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2049610 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2049610, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2049610 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2048212, 0, 0, 0, 14693, 50000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2048212 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2048212, 0, 0, 0, 14693, 500000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2048212 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5220100, 0, 0, 0, 14693, 1000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5220100 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5220100, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5220100 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5220100, 0, 0, 0, 14693, 100000, 1, 0, 0, 0, 100, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5220100 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1122210, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1122210 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1132183, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1132183 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1152101, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1152101 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 1112757, 0, 0, 0, 14693, 125000, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 1112757 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2048331, 0, 0, 0, 14693, 150000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2048331 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5530360, 0, 0, 0, 14693, 50000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5530360 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680770, 0, 0, 0, 14693, 10000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680770 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680770, 0, 0, 0, 14693, 99000, 1, 0, 0, 0, 10, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680770 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5680770, 0, 0, 0, 14693, 900000, 1, 0, 0, 0, 100, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5680770 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2049506, 0, 0, 0, 14693, 50000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2049506 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5552000, 0, 0, 0, 14693, 30000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5552000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 5553000, 0, 0, 0, 14693, 30000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 5553000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010038, 2432946, 0, 0, 0, 14693, 20000, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010038 AND itemid = 2432946 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 9010040 Conor (Renegades Shop)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5211121, 0, 0, 0, 14434, 2, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5211121 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5360056, 0, 0, 0, 14434, 2, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5360056 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 1202236, 0, 0, 0, 14434, 3, 1, 0, 0, 0, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 1202236 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5180000, 0, 0, 0, 14434, 25, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5180000 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 5062024, 0, 0, 0, 14434, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 5062024 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 2431936, 0, 0, 0, 14434, 5, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 2431936 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9010040, 2431935, 0, 0, 0, 14434, 2, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9010040 AND itemid = 2431935 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 9201450 Matilda (Purveyor of Useful Items)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3010043, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3010043 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3010119, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3010119 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3010431, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3010431 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3015659, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3015659 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3015606, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3015606 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 3015581, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 3015581 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2431965, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2431965 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2432846, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2432846 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2432640, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2432640 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2432749, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2432749 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433715, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433715 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433775, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433775 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433776, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433776 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433830, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433830 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433831, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433831 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433913, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433913 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2433981, 0, 0, 0, 59520, 20, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2433981 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022730, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022730 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022731, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022731 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022729, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022729 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2022728, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2022728 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2049152, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2049152 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9201450, 2049611, 0, 0, 0, 59520, 10, 1, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9201450 AND itemid = 2049611 AND price = 0 AND tokenitemid = 0 AND tokenprice = 0);
-- 1400002 Mathias (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 1400002, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 1400002 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2161013 Glibber (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2161013, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2161013 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2231100 Cliff (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2231100, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2231100 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2301001 Nathania (Merchant/Alchemist)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2301001, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2301001 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 2400022 Oko (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 2400022, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2400022 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3001341 Sanctuary Merchant (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3001341, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3001341 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3002011 Swindle (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3002011, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3002011 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3005581 Bonnie (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3005581, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3005581 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006336 Chest of Wisdom (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006336, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006336 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006337 Chest of Wisdom (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006337, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006337 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006436 Cygnus Supplier (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006436, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006436 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006549 Kalifa (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006549, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006549 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006661 Tarrah (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006661, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006661 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 3006842 Phantasmal Priestess (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 3006842, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 3006842 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9310599 Xuzhu (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9310599, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9310599 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9400469 Fenton (Shopkeep)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9400469, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9400469 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9401294 Xiang Xiang (Tavern Keeper)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401294, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401294 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9401295 Ju Ji (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401295, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401295 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9401340 Freete (General Store)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9401340, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9401340 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);
-- 9402236 Starkeeper Eren (Merchant)
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000000, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000000 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000001, 48, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000001 AND price = 48 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000002, 96, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000002 AND price = 96 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000003, 19, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000003 AND price = 19 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2000006, 181, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2000006 AND price = 181 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002000, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002000 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002001, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002001 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002002, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002002 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002004, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002004 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2002005, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2002005 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010000, 3, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010000 AND price = 3 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010002, 5, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010002 AND price = 5 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010001, 10, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010001 AND price = 10 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010003, 9, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010003 AND price = 9 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2010004, 91, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 300 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2010004 AND price = 91 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2120000, 30, 0, 0, 0, 0, 0, 0, 0, 1000000000, 1, 9000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2120000 AND price = 30 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2030000, 400, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2030000 AND price = 400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2060000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2060000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2061000, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2061000 AND price = 1 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2060000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2060000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2061000, 1400, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 9999 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2061000 AND price = 1400 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050000, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050000 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050001, 200, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050001 AND price = 200 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050002, 300, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 100 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050002 AND price = 300 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2050003, 500, 0, 0, 0, 0, 0, 0, 0, 100000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2050003 AND price = 500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2432805, 50000, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 1000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2432805 AND price = 50000 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020012, 4500, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020012 AND price = 4500 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020013, 5600, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020013 AND price = 5600 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020014, 8100, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020014 AND price = 8100 AND tokenitemid = 0 AND tokenprice = 0);
INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, tabindex, worldblock, potentialgrade, buylimit, quantity, maxperslot)
SELECT 9402236, 2020015, 10200, 0, 0, 0, 0, 0, 0, 0, 1000000, 1, 3000 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 9402236 AND itemid = 2020015 AND price = 10200 AND tokenitemid = 0 AND tokenprice = 0);

-- ----------------------------------------------------------------------------------------------------
-- V181_Erda_Link.sql
-- Erda Link (Sia Astelle's 6th job): the stones a character has activated on its board and their levels.
-- Sol Erda is the HEXA Matrix's (quest record 1489). See ErdaLinkModule.
CREATE TABLE IF NOT EXISTS erda_link_stones (
    id BIGINT NOT NULL AUTO_INCREMENT,
    charid INT NOT NULL,
    stoneid INT NOT NULL,
    level INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY erda_link_stones_char_stone (charid, stoneid),
    CONSTRAINT erda_link_stones_ibfk_1 FOREIGN KEY (charid) REFERENCES characters (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------------------------------
-- V182_Quest_Story_Drops_4.sql
-- Quest items the v265 walk found nothing giving (quest audit 2026-10-24, part 2):
--   39134 [Weekly Quest] Black Heaven Deck: Collect Steel Drill: Steel Drill (4034293) from the Demolishizer (8250012)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8250012, 4034293, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8250012 AND itemid = 4034293);

-- ----------------------------------------------------------------------------------------------------
-- V183_Quest_Story_Drops_5.sql
-- 37856 [Esfera] Erda Energy (4037359, collected from the enemies around Living Spring 5): from the Bellalion (8644502)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644502, 4037359, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644502 AND itemid = 4037359);

-- ----------------------------------------------------------------------------------------------------
-- V184_Zero_Mirror_World_Drops.sql
-- Zero's Mirror World side quests (41xxx): items the quest asks for from the Mirror World dungeon monsters, which
-- dropped nothing for them (quest audit 2026-10-24; monster picked by the quest's dungeon and the item's name)
--   41054 Cold Desert Nights 2: Lizard's Scarf (4000935)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600108, 4000935, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600108 AND itemid = 4000935);
--   41102 Reminiscing: Purple Clam Shell (4000969)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600216, 4000969, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600216 AND itemid = 4000969);
--   41105 Fresh Eggs: Octopus Ink (4000973)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600220, 4000973, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600220 AND itemid = 4000973);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600221, 4000973, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600221 AND itemid = 4000973);
--   41110 Good Luck Charm: Undead Ticket (4000960)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600206, 4000960, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600206 AND itemid = 4000960);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600207, 4000960, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600207 AND itemid = 4000960);
--   41150 Gathering Training Equipment: Straw Voodoo Doll (4009000)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600300, 4009000, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600300 AND itemid = 4009000);
--   41152 Fashionista Naran: Mr. Alli's Alligator Leather (4009022)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600322, 4009022, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600322 AND itemid = 4009022);
--   41157 Dieting: Oak Tree Acorn (4009006)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600306, 4009006, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600306 AND itemid = 4009006);
--   41200 Broken Patrol Robot: Guard Robot E's Memory Chip (4009032)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600405, 4009032, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600405 AND itemid = 4009032);
--   41216 Allen, Father of Robots: Android Parts (4009051)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600423, 4009051, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600423 AND itemid = 4009051);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600424, 4009051, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600424 AND itemid = 4009051);
--   41303 Reducing Production Costs 2: Robo Battery (4009083)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600605, 4009083, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600605 AND itemid = 4009083);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600606, 4009083, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600606 AND itemid = 4009083);
--   41312 Tools for the Magic Show: Colorful Hat (4009097)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600618, 4009097, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600618 AND itemid = 4009097);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600619, 4009097, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600619 AND itemid = 4009097);
--   41366 Meaning of Oblivion 2: Oblivion Helm (4009121)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600713, 4009121, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600713 AND itemid = 4009121);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600714, 4009121, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600714 AND itemid = 4009121);

-- ----------------------------------------------------------------------------------------------------
-- V185_Regular_Shop_Stock.sql
-- @shop Regular Shop (Collector, 9001212): mid- and late-game basics. Elixir, the status cures (Antidote, Eyedrop,
-- Tonic, Holy Water, All Cure Potion), the 60% HP/MP potions (Honster, Mana Bull), Melting Cheese (4000 HP),
-- Sunset Dew (5000 MP) and higher ammo (Diamond arrows, Hwabi Throwing-Stars, Eternal Bullets). No buff or EXP items.
-- Prices are in mesos; ammo is sold in bundles of 1000. Existing rows are unchanged.
insert into shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, questexid, questexkey, questexvalue, itemperiod, levellimited, showlevmin, showlevmax, questid, sellstart, sellend, tabindex, worldblock, potentialgrade, buylimit, quantity, unitprice, maxperslot, discountperc) values
(9001212, 2000004, 2000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050000, 300, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050001, 300, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050002, 400, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050003, 400, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050004, 800, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2002021, 3000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2002020, 3000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2020012, 3000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2020015, 4000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2060004, 4000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0),
(9001212, 2061004, 4000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0),
(9001212, 2070007, 12000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0),
(9001212, 2330005, 5000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0);

-- ----------------------------------------------------------------------------------------------------
-- V186_Carcion_Quest_Drops.sql
-- Carcion story items nothing dropped (quest audit 2026-10-24):
--   38692 The Cause of the Infection: Mystery Infectious Agent (4037177) from the Despairing Hunter (8645423)
--   38706: Intact Infectious Agent (4037196) from the Knight and the Priest of the Source (8645426, 8645427)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645423, 4037177, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645423 AND itemid = 4037177);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645426, 4037196, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645426 AND itemid = 4037196);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645427, 4037196, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645427 AND itemid = 4037196);

-- ----------------------------------------------------------------------------------------------------
-- V187_Arcane_Quest_Drops.sql
-- Arcane River story items nothing a player can reach dropped (quest audit 2026-10-24):
--   35612 [Moonbridge] Foggy Investigation: Wicked Soul Remnants (4036446) only dropped from the Soot Beast/Talon
--   copies of a closed area (8644622/8644623); now also from the Last Horizon deck's Soot Beast (8644614) and Soot Talon
--   (8644615)
--   37858 [Esfera] Collecting Erda 2: Erda Energy (4037360) from the Bellalis (8644503) around the quest's NPC
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644614, 4036446, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644614 AND itemid = 4036446);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644615, 4036446, 4000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644615 AND itemid = 4036446);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644503, 4037360, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644503 AND itemid = 4037360);

-- ----------------------------------------------------------------------------------------------------
-- V188_Swordie_Coins.sql
-- Swordie Coins: what each account has been paid, and its coin shop purchases.
-- kind: boss (rkey = boss group, period = week), gift (rkey = gift day, period = month), level (rkey = level),
-- achievement (rkey = achievement id), cap (rkey = shop line, period = week or "ever"). amount: coins paid
-- (boss: the tier paid this week) or items bought (cap).
CREATE TABLE IF NOT EXISTS coin_rewards (
    accid INT NOT NULL,
    kind VARCHAR(16) NOT NULL,
    rkey VARCHAR(48) NOT NULL,
    period VARCHAR(16) NOT NULL,
    amount INT NOT NULL DEFAULT 0,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (accid, kind, rkey, period)
);

-- Daily Gift days 1 and 15: 30 Swordie Coins instead of the 7-day Hyper Teleport Rock (left alone if Server Control
-- already put something else there).
UPDATE daily_gifts SET itemid = 4310096, quantity = 30, expire_days = 0
WHERE day IN (1, 15) AND itemid = 5040004;

-- ----------------------------------------------------------------------------------------------------
-- V189__Boss_Reward_Drops_v265.sql
-- Boss rewards (maplestorywiki.net drop lists, v265 monster ids). Copies only into monsters that have no drops yet,
-- so running it again changes nothing.

-- Will: phase 3 Will drops nothing in GMS; the Lost Light Mirror (Easy 8950113, Normal 8950114, Hard 8950115) drops
-- everything. Easy has no equipment. Twilight Mark from Normal and Hard.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8950114, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880342
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8950114) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8950115, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880302
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8950115) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8950113, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880342 AND itemid >= 2000000
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8950113) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT m, 1012757, 100, 1, 1 FROM (SELECT 8950114 AS m UNION SELECT 8950115) t
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = t.m AND x.itemid = 1012757);
DELETE FROM mob_drops WHERE mobid IN (8880342, 8880302, 8880362);

-- Verus Hilla Normal is 8880450 now (8880405 was the old Normal set).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8880450, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880405
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8880450) x);

-- Chosen Seren: Normal's chest is 8880644 and Extreme's 8880664 (8880614 is Hard's). Mitra's Rage (emblems) from all.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8880644, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880614
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8880644) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8880664, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8880614
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8880664) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT c.m, e.i, 50, 1, 1 FROM (SELECT 8880644 AS m UNION SELECT 8880614 UNION SELECT 8880664) c
CROSS JOIN (SELECT 1190555 AS i UNION SELECT 1190556 UNION SELECT 1190557 UNION SELECT 1190558 UNION SELECT 1190559) e
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = c.m AND x.itemid = e.i);

-- Chaos Horntail (8810122) had no drops: Normal's, plus the Chaos Horntail Necklace.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8810122, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8810018
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8810122) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8810122, 1122076, 2000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = 8810122 AND x.itemid = 1122076);

-- Chaos Gloom (8644655) had no drops: Normal's, plus Endless Terror.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644655, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8644650
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8644655) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644655, 1113306, 50, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = 8644655 AND x.itemid = 1113306);

-- The v265 Lotus (staff test): its reward cores get the old fight's drops.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8881103, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8950002
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8881103) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8881153, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8950102
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8881153) x);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8881203, itemid, chance, minquant, maxquant FROM mob_drops WHERE mobid = 8950102
AND NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops WHERE mobid = 8881203) x);

-- ----------------------------------------------------------------------------------------------------
-- V190_Class_Start_Quest_Drops.sql
-- Class start quest items that no monster dropped (same format as V95-V99).
-- Cygnus Knights: Combat Technique (36458) - Training Tino | Tino Feather
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645270, 4036882, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645270 AND itemid = 4036882);
-- Lara: Kite Kicker (36205) - 10 kills of 8645225 | Lost Shoe
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645225, 4036781, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645225 AND itemid = 4036781);
-- Sia Astelle: Cosmic Silence (67276) - 9602449 | Cosmic Silence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9602449, 4009586, 6000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9602449 AND itemid = 4009586);

-- ----------------------------------------------------------------------------------------------------
-- V191_Verus_Hilla_Pendant_Rings.sql
-- Verus Hilla (maplestorywiki.net, GMS): both modes drop the Daybreak Pendant; Normal a Red Jade Boss Ring Box, Hard a
-- Black Jade Boss Ring Box. Normal is 8880450, Hard 8880410. Only adds rows that aren't there.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT t.m, t.i, t.c, 1, 1 FROM (SELECT 8880450 AS m, 1122443 AS i, 200 AS c UNION SELECT 8880410, 1122443, 200
    UNION SELECT 8880450, 2028408, 300 UNION SELECT 8880410, 2028409, 300) t
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) x WHERE x.mobid = t.m AND x.itemid = t.i);

-- ----------------------------------------------------------------------------------------------------
-- V192_Char_Mannequins.sql
-- Salon mannequins per character: type 0 hair, 1 face, 2 skin; slot 0-41; the client record is (byte flag,
-- int style, int extra).
CREATE TABLE IF NOT EXISTS char_mannequins (
    charid INT NOT NULL,
    type TINYINT NOT NULL,
    slot TINYINT NOT NULL,
    flag TINYINT NOT NULL DEFAULT 0,
    styleid INT NOT NULL DEFAULT 0,
    extra INT NOT NULL DEFAULT 0,
    PRIMARY KEY (charid, type, slot)
);

-- ----------------------------------------------------------------------------------------------------
-- V193_Dojo_Ranking.sql
-- Mu Lung Dojo weekly ranking (DojoRanking.java): each character's best run of a ranking week (Thursday 00:30 to
-- Wednesday 23:30 UTC). grp: 0 = Novice (Lv. 105-200), 1 = Master (Lv. 201+). After the week, rank_overall and
-- rank_class are filled in, reward names what So Gong hands out, claimed says it was taken.
CREATE TABLE IF NOT EXISTS dojo_ranking (
    week VARCHAR(10) NOT NULL,
    char_id INT NOT NULL,
    char_name VARCHAR(16) NOT NULL DEFAULT '',
    job INT NOT NULL DEFAULT 0,
    level INT NOT NULL DEFAULT 0,
    grp TINYINT NOT NULL DEFAULT 0,
    floor INT NOT NULL DEFAULT 0,
    time_sec INT NOT NULL DEFAULT 0,
    settled TINYINT NOT NULL DEFAULT 0,
    rank_overall INT NOT NULL DEFAULT 0,
    rank_class INT NOT NULL DEFAULT 0,
    reward VARCHAR(64) DEFAULT NULL,
    claimed TINYINT NOT NULL DEFAULT 0,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (week, char_id),
    KEY dojo_ranking_char (char_id)
);

-- ----------------------------------------------------------------------------------------------------
-- V194_Dojo_Unity_Training_Charms.sql
-- Lao's Mu Lung Dojo Point Shop (2091013): the Unity Training Center Entrance Charms (30 minutes to 24 hours), which
-- So Gong takes to let you into the Unity Training Center (925080000, EXP every 5 seconds). Paid in Dojo Points
-- (record 3887 point). Prices: 600 points an hour of training (the live price list isn't in the client data or on the
-- wiki). Skipped if a row is already there.
INSERT INTO shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) SELECT 2091013, 4001851, 2000000000, 0, 0, 0, 0, 0, 3887, 300, 0, 0, 0, 0, 0, '', 0, 1, 0, 100 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2091013 AND itemid = 4001851);
INSERT INTO shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) SELECT 2091013, 4001852, 2000000000, 0, 0, 0, 0, 0, 3887, 600, 0, 0, 0, 0, 0, '', 0, 1, 0, 100 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2091013 AND itemid = 4001852);
INSERT INTO shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) SELECT 2091013, 4001853, 2000000000, 0, 0, 0, 0, 0, 3887, 1800, 0, 0, 0, 0, 0, '', 0, 1, 0, 100 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2091013 AND itemid = 4001853);
INSERT INTO shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) SELECT 2091013, 4001854, 2000000000, 0, 0, 0, 0, 0, 3887, 3600, 0, 0, 0, 0, 0, '', 0, 1, 0, 100 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2091013 AND itemid = 4001854);
INSERT INTO shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) SELECT 2091013, 4001881, 2000000000, 0, 0, 0, 0, 0, 3887, 5400, 0, 0, 0, 0, 0, '', 0, 1, 0, 100 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2091013 AND itemid = 4001881);
INSERT INTO shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) SELECT 2091013, 4001862, 2000000000, 0, 0, 0, 0, 0, 3887, 7200, 0, 0, 0, 0, 0, '', 0, 1, 0, 100 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2091013 AND itemid = 4001862);
INSERT INTO shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) SELECT 2091013, 4001882, 2000000000, 0, 0, 0, 0, 0, 3887, 14400, 0, 0, 0, 0, 0, '', 0, 1, 0, 100 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM shopitems WHERE shopid = 2091013 AND itemid = 4001882);

-- ----------------------------------------------------------------------------------------------------
-- V195_Prototype_Soul_Enchanter_Rate.sql
-- Prototype Soul Enchanters (2590004) dropped far too often from 255 monsters: 1/25 of the old chance (100 -> 4).
update mob_drops set chance = 4 where itemid = 2590004 and chance = 100;

-- ----------------------------------------------------------------------------------------------------
-- V196_Spell_Trace_Drops.sql
-- Spell Traces filled the Etc tab: back to normal amounts, and monsters drop them far less often.
-- The Spell Trace rate multiplied every drop's amount (15x); 1 = the drop tables' own amounts.
update server_rates set value = 1 where name = 'spell_trace';
-- 72 monsters dropped them every kill (10000); 50 is 0.5%, about 10% a kill at the 20x drop rate.
update mob_drops set chance = 50 where itemid = 4001832 and chance > 50;

-- ----------------------------------------------------------------------------------------------------
-- V197_Equip_Presets.sql
-- Equipment presets per character: the applied preset (0-2) and the ids of the Preset 1 items worn while another
-- preset is applied. The presets' stored items are rows in items (equipped inventory, bag index 3001-3265).
CREATE TABLE IF NOT EXISTS char_equip_presets (
    charid INT NOT NULL PRIMARY KEY,
    applied TINYINT NOT NULL DEFAULT 0,
    carried TEXT
);

-- ----------------------------------------------------------------------------------------------------
-- V198_Golden_Leaf_Sets.sql
-- Leaf Shop gear packages: the equipment sets (Etc.wz SetItemInfo ids) each account has worn whole at least once,
-- which opens that set's package for every character of the account. See GoldenLeafModule.
CREATE TABLE IF NOT EXISTS golden_leaf_sets (
    accountid INT NOT NULL,
    setid INT NOT NULL,
    unlocked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (accountid, setid)
);

-- ----------------------------------------------------------------------------------------------------
-- V199_Link_Skill_Presets.sql
-- Link skill presets per character: the applied preset (0-2) and each preset's link skill ids (comma-separated:
-- the 8000xxxx link, or the stack skill for the Explorer / Cygnus / Resistance links).
CREATE TABLE IF NOT EXISTS char_link_presets (
    charid INT NOT NULL PRIMARY KEY,
    applied TINYINT NOT NULL DEFAULT 0,
    preset1 TEXT,
    preset2 TEXT,
    preset3 TEXT
);

-- ----------------------------------------------------------------------------------------------------
-- V200_New_Class_Weapon_Drops.sql
-- Level-based monster drops for the Ren, Sia Astelle, Mo Xuan and Khali weapons (the levelled weapons that drop
-- from monsters in GMS: 10-110, Briser 120, Jaihin 130, Utgard 140), the same Briser/Jaihin mob drops the other
-- class weapons have, and the class secondaries Neville sells that his shop was missing.

INSERT IGNORE INTO equip_drops (id, job, level) VALUES
(1215001, 'WARRIOR', 10),
(1215002, 'WARRIOR', 30),
(1215003, 'WARRIOR', 50),
(1215004, 'WARRIOR', 60),
(1215005, 'WARRIOR', 70),
(1215006, 'WARRIOR', 80),
(1215007, 'WARRIOR', 90),
(1215008, 'WARRIOR', 100),
(1215010, 'WARRIOR', 110),
(1215013, 'WARRIOR', 120),
(1215014, 'WARRIOR', 130),
(1215016, 'WARRIOR', 140),
(1253000, 'MAGICIAN', 10),
(1253001, 'MAGICIAN', 30),
(1253002, 'MAGICIAN', 50),
(1253003, 'MAGICIAN', 60),
(1253004, 'MAGICIAN', 70),
(1253005, 'MAGICIAN', 80),
(1253006, 'MAGICIAN', 90),
(1253007, 'MAGICIAN', 100),
(1253008, 'MAGICIAN', 110),
(1253010, 'MAGICIAN', 120),
(1253012, 'MAGICIAN', 130),
(1253014, 'MAGICIAN', 140),
(1403001, 'PIRATE', 30),
(1403002, 'PIRATE', 50),
(1403003, 'PIRATE', 60),
(1403004, 'PIRATE', 70),
(1403005, 'PIRATE', 80),
(1403006, 'PIRATE', 90),
(1403007, 'PIRATE', 100),
(1403008, 'PIRATE', 110),
(1403010, 'PIRATE', 120),
(1403012, 'PIRATE', 130),
(1403014, 'PIRATE', 140),
(1404000, 'THIEF', 10),
(1404001, 'THIEF', 30),
(1404002, 'THIEF', 50),
(1404003, 'THIEF', 60),
(1404004, 'THIEF', 70),
(1404005, 'THIEF', 80),
(1404006, 'THIEF', 90),
(1404007, 'THIEF', 100),
(1404008, 'THIEF', 110),
(1404010, 'THIEF', 120),
(1404012, 'THIEF', 130),
(1404014, 'THIEF', 140);

INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT n.mobid, n.itemid, 40, 1, 1 FROM (
    SELECT 8145101 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145101 AS mobid, 1404012 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145102 AS mobid, 1404012 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145201 AS mobid, 1404012 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1215013 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1215014 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1253010 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1253012 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1403010 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1403012 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1404010 AS itemid
    UNION ALL SELECT 8145202 AS mobid, 1404012 AS itemid
) n
WHERE NOT EXISTS (SELECT 1 FROM mob_drops d WHERE d.mobid = n.mobid AND d.itemid = n.itemid);

INSERT INTO shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, questexid,
    questexkey, questexvalue, itemperiod, levellimited, showlevmin, showlevmax, questid, tabindex, worldblock,
    potentialgrade, buylimit, quantity, unitprice, maxperslot, discountperc)
SELECT 9072100, n.itemid, n.price, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 1, 0, 1, 0 FROM (
    SELECT 1352802 AS itemid, 330000 AS price
    UNION ALL SELECT 1352860 AS itemid, 3000 AS price
    UNION ALL SELECT 1352861 AS itemid, 79000 AS price
    UNION ALL SELECT 1352862 AS itemid, 330000 AS price
    UNION ALL SELECT 1352863 AS itemid, 580000 AS price
    UNION ALL SELECT 1352870 AS itemid, 3000 AS price
    UNION ALL SELECT 1352871 AS itemid, 79000 AS price
    UNION ALL SELECT 1352872 AS itemid, 330000 AS price
    UNION ALL SELECT 1352873 AS itemid, 580000 AS price
    UNION ALL SELECT 1354030 AS itemid, 3000 AS price
    UNION ALL SELECT 1354031 AS itemid, 80000 AS price
    UNION ALL SELECT 1354032 AS itemid, 330000 AS price
    UNION ALL SELECT 1354033 AS itemid, 580000 AS price
    UNION ALL SELECT 1354040 AS itemid, 3000 AS price
    UNION ALL SELECT 1354041 AS itemid, 80000 AS price
    UNION ALL SELECT 1354042 AS itemid, 330000 AS price
    UNION ALL SELECT 1354043 AS itemid, 580000 AS price
) n
WHERE NOT EXISTS (SELECT 1 FROM shopitems s WHERE s.shopid = 9072100 AND s.itemid = n.itemid);


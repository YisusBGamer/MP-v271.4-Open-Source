-- The migrations V201 to V220 in order, folded in so a new database needs no catch-up at startup.
-- 09_migration_history.sql marks them done. sql/migration keeps the files for servers updating from older versions.

-- ----------------------------------------------------------------------------------------------------
-- V201_Guild_Week_And_CheckIn.sql
-- Guild members: contribution this week (the guild ranking's weekly Honor EXP list) and the day of the last
-- guild check-in (yyyymmdd, UTC; the check-in window counts members by it).

ALTER TABLE guildmembers
    ADD COLUMN weekCommitment int NOT NULL DEFAULT 0,
    ADD COLUMN weekStart int NOT NULL DEFAULT 0,
    ADD COLUMN checkInDate int NOT NULL DEFAULT 0;


-- ----------------------------------------------------------------------------------------------------
-- V202_Quest_Item_Drops_3.sql
-- Quest items that no drop gave (same format as V115/V190). Each insert is skipped if the drop already exists.
-- [Aqua Road] The Bottled-up Letter (3072) starts with an SOS Letter in hand: Aqua Road's Bubble Fish, Flower Fish,
-- Krip and Mask Fish around the Aquarium.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230109, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230109 AND itemid = 4031209);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230200, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230200 AND itemid = 4031209);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3000006, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3000006 AND itemid = 4031209);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230104, 4031209, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230104 AND itemid = 4031209);
-- Evan: Making a Saddle (22404) - Kenta's Seruf in the Sea of Silence (923030000) | Seruf Pearl
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9001026, 4032474, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001026 AND itemid = 4032474);
-- Switching Attributes (6225) - the Elemental Thanatos in the Room of Thanatos (922020100) | Icy Soul Rock
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300086, 4031470, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300086 AND itemid = 4031470);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300100, 4031470, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300100 AND itemid = 4031470);


-- ----------------------------------------------------------------------------------------------------
-- V203_Guild_Weekly_Mission.sql
-- Guild weekly mission: each member's points for the week (hunting, boss) and kills toward the next hunting point;
-- each guild's last settled week and that week's total (shown as last week's result, rewarded with GP).

ALTER TABLE guildmembers
    ADD COLUMN missionWeek int NOT NULL DEFAULT 0,
    ADD COLUMN missionHunt int NOT NULL DEFAULT 0,
    ADD COLUMN missionBoss int NOT NULL DEFAULT 0,
    ADD COLUMN missionKills int NOT NULL DEFAULT 0;

ALTER TABLE guilds
    ADD COLUMN missionCheckedWeek int NOT NULL DEFAULT 0,
    ADD COLUMN missionLastPoints int NOT NULL DEFAULT 0;


-- ----------------------------------------------------------------------------------------------------
-- V204_Carcion_Water_Drop_Flower.sql
-- Pinjar Specialty (38710): Water Drop Flowers (4037198) from the Specters of the Giant Coral Colony (410007620-622:
-- Despairing Guard 8645420, Monstrous Shadow Bird 8645421), as the quest says; nothing dropped them.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645420, 4037198, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645420 AND itemid = 4037198);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645421, 4037198, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645421 AND itemid = 4037198);


-- ----------------------------------------------------------------------------------------------------
-- V205_Grandis_Quest_Item_Drops.sql
-- Grandis quest items that no monster dropped (same format as V204).
-- The Button and the Four Treasures of the Study (1) (38408): Broken Fragment (4037167) from the entranced
-- researchers in 410007035 (Harsh Winter Researcher 8645372), as the quest says.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645372, 4037167, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645372 AND itemid = 4037167);
-- For a New Start (38714): Warrior Emblem - Ring of Cycles (4037200) from the Specters of Among Tainted Trees
-- 1-3 (410007643-645: Shadowy Black Panther 8645424, Abyssal Hunter 8645425).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645424, 4037200, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645424 AND itemid = 4037200);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645425, 4037200, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645425 AND itemid = 4037200);


-- ----------------------------------------------------------------------------------------------------
-- V206_Monster_Park_Extreme_Clears.sql
-- Monster Park Extreme weekly clears (MonsterParkExtreme.java): one clear per character and two per world (account)
-- each week; the week starts Thursday 00:00 (reset time zone) and is named by that date.
CREATE TABLE IF NOT EXISTS monster_park_extreme_clears (
    week VARCHAR(10) NOT NULL,
    char_id INT NOT NULL,
    account_id INT NOT NULL,
    char_name VARCHAR(16) NOT NULL DEFAULT '',
    cleared_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (week, char_id),
    KEY mpe_clears_account (week, account_id)
);


-- ----------------------------------------------------------------------------------------------------
-- V207_Esfera_Origin_Droplet_Drops.sql
-- Stone Origin Droplet (4001889, "Esferan Erda"): 9 and a Stone Cobweb Droplet (Will) make an Arachno Coin at
-- Kanto in Esfera Base Camp, but no monster dropped it. From the Esfera hunting monsters (450007010-450007230:
-- 8644500-8644509) at the Arcane River Droplet Stone's rate (2%).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644500, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644500 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644501, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644501 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644502, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644502 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644503, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644503 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644504, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644504 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644505, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644505 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644506, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644506 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644507, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644507 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644508, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644508 AND itemid = 4001889);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644509, 4001889, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644509 AND itemid = 4001889);


-- ----------------------------------------------------------------------------------------------------
-- V208_Eternal_Set_Sources.sql
-- Eternal set sources (maplestorywiki.net Eternal_Set and the bosses' /Monster pages, GMS Interactive amounts): the
-- Grandis treasure chests drop the v265 tokens (10 for an Eternal piece; fragments 2 for a token) and armor boxes
-- (one piece). The wiki gives no box rates: 1% on Chaos/Hard, 2% on Extreme. Opened by scripts/item/eternal_source.py.

-- Kalos the Guardian: Normal Fragments, Chaos/Extreme Residual Determinations, Divine Eternal Armor Box (Chaos, Extreme)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 2636606, 10000, 3, 3 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 2636606);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2634472, 10000, 5, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2634472);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2638064, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2638064);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2634472, 10000, 14, 14 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2634472);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2638064, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2638064);

-- Kaling: Easy/Normal Ring Fragments, Hard/Extreme Ferocious Beast Entanglement Rings, Ferocious Beast Eternal Armor Box (Hard, Extreme)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 2636607, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 2636607);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 2636607, 10000, 5, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 2636607);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2635747, 10000, 7, 7 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2635747);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2638065, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2638065);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2635747, 10000, 18, 18 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2635747);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2638065, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2638065);

-- First Adversary (World Heart): Normal Whispers, Hard/Extreme Echoes of Ancient Resolve, Ancient Eternal Armor Box (Hard, Extreme)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 2830305, 10000, 4, 4 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 2830305);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2830304, 10000, 6, 6 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2830304);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2830306, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2830306);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2830304, 10000, 16, 16 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2830304);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2830306, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2830306);

-- Limbo (Paradoxical Truth): Distorted Ambition, Eternal Armor of Desire Box (Hard)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 2638063, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 2638063);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2638063, 10000, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2638063);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2638066, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2638066);

-- Baldrix (Concentrated Magical Essence): Trace of Eternal Loyalty, Eternal Armor of Oaths Box (Hard)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 2639252, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 2639252);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2639252, 10000, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2639252);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2639253, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2639253);


-- ----------------------------------------------------------------------------------------------------
-- V209_Eternal_Box_Rates.sql
-- Eternal Armor Box rates from the Grandis treasure chests: 10% on Chaos/Hard, 20% on Extreme (chance is per 10,000)
UPDATE mob_drops SET chance = 1000 WHERE itemid IN (2638064, 2638065, 2830306, 2638066, 2639253) AND chance = 100;
UPDATE mob_drops SET chance = 2000 WHERE itemid IN (2638064, 2638065, 2830306) AND chance = 200;


-- ----------------------------------------------------------------------------------------------------
-- V210_Auction_House_Trades.sql
-- Auction House: buyer/seller sides of a sale are collected separately, and every trade is logged
ALTER TABLE auction_items
    ADD COLUMN selleruserid int NOT NULL DEFAULT 0,
    ADD COLUMN bidaccountid int NOT NULL DEFAULT 0,
    ADD COLUMN buyeruserid int NOT NULL DEFAULT 0,
    ADD COLUMN sellercollected tinyint(1) NOT NULL DEFAULT 0,
    ADD COLUMN buyercollected tinyint(1) NOT NULL DEFAULT 0,
    ADD COLUMN solddate datetime DEFAULT NULL,
    ADD COLUMN itemid int NOT NULL DEFAULT 0,
    ADD KEY idx_auction_items_state (state),
    ADD KEY idx_auction_items_bidaccountid (bidaccountid);

CREATE TABLE auction_log (
    id bigint NOT NULL AUTO_INCREMENT,
    time datetime NOT NULL,
    action varchar(16) NOT NULL,
    auctionid bigint NOT NULL,
    accountid int NOT NULL,
    charid int NOT NULL,
    otheraccountid int NOT NULL DEFAULT 0,
    itemsn bigint NOT NULL DEFAULT 0,
    itemid int NOT NULL DEFAULT 0,
    quantity int NOT NULL DEFAULT 0,
    mesos bigint NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_auction_log_auctionid (auctionid),
    KEY idx_auction_log_accountid (accountid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ----------------------------------------------------------------------------------------------------
-- V211_Mihile_Story_Drops.sql
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


-- ----------------------------------------------------------------------------------------------------
-- V212_AvatarLook_Mixed_Eye_Color.sql
-- Mixed eye colour (colourblend lens): mix colour and its percent, like mixedhaircolor/mixhairpercent
ALTER TABLE avatarlook ADD COLUMN mixedfacecolor int NOT NULL DEFAULT 0;
ALTER TABLE avatarlook ADD COLUMN mixfacepercent int NOT NULL DEFAULT 0;


-- ----------------------------------------------------------------------------------------------------
-- V213_Champion_Raid.sql
-- Champion Raid (Draco Isle): this month's best score and Champion Coins per account
CREATE TABLE IF NOT EXISTS champion_raid (
    accid int NOT NULL PRIMARY KEY,
    month varchar(7) NOT NULL,
    best int NOT NULL DEFAULT 0,
    coins int NOT NULL DEFAULT 0
);


-- ----------------------------------------------------------------------------------------------------
-- V214_No_Table_Power_Crystals.sql
-- Intense Power Crystals come only from the boss kill itself (Mob.java: one per fighter, as in GMS); the old drop-table
-- rows (30%, 1-2 each, then multiplied by the drop rate) gave extra crystals on top.
DELETE FROM mob_drops WHERE itemid = 4001886;


-- ----------------------------------------------------------------------------------------------------
-- V215_Malefic_Star_Jupiter_Drops.sql
-- Malefic Star and Jupiter reward drops (maplestorywiki.net, GMS v270/v271): the Crooked Scale (8881904 Normal,
-- 8881954 Hard) and the Alchemical Remnant (8882013 Normal, 8882063 Hard). Amounts as published; Sol Erda Energy
-- as the energy items (Faint 10, Energy 200, Dense 500) for the published totals. Soul shards at 30% (V149); no
-- published rates for the androids, Blissful Nightmare, Original Sin of Pride and the armor box (0.5-1%).
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2831409, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2831409); -- Malefic Star's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2000005, 10000, 60, 60 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2000005); -- Power Elixir
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2636421, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2636421); -- Sol Erda Energy (200)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2636420, 10000, 9, 9 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2636420); -- Faint Sol Erda Energy (90)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2539001, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2539001); -- Grindstone of Life
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 1662303, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 1662303); -- Maleficroid
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2831409, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2831409); -- Malefic Star's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2000005, 10000, 60, 60 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2000005); -- Power Elixir
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2636422, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2636422); -- Dense Sol Erda Energy (500)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2636420, 10000, 10, 10 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2636420); -- Faint Sol Erda Energy (100)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2539003, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2539003); -- Grindstone of Faith
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2831435, 10000, 90, 90 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2831435); -- Vestige of Erion Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 1662303, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 1662303); -- Maleficroid
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 1113360, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 1113360); -- Blissful Nightmare
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2831524, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2831524); -- Jupiter's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2831435, 10000, 45, 45 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2831435); -- Vestige of Erion Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2831104, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2831104); -- Lingering Twisted Desire
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 1662304, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 1662304); -- Jupiteroid
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2636421, 10000, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2636421); -- Sol Erda Energy (400)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2636420, 10000, 5, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2636420); -- Faint Sol Erda Energy (50)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2831524, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2831524); -- Jupiter's Soul Shard
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2831435, 10000, 360, 360 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2831435); -- Vestige of Erion Coupon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2831104, 10000, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2831104); -- Lingering Twisted Desire
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2831106, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2831106); -- Eternal Twisted Armor Box
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 1012911, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 1012911); -- Original Sin of Pride
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 1662304, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 1662304); -- Jupiteroid
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2636422, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2636422); -- Dense Sol Erda Energy (500)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2636421, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2636421); -- Sol Erda Energy (200)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2636420, 10000, 5, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2636420); -- Faint Sol Erda Energy (50)


-- ----------------------------------------------------------------------------------------------------
-- V216_Login_Theme.sql
-- Character select theme per login (Etc.wz customLoginTheme.img id; 0 = Beneath the World Tree (Day)).
ALTER TABLE user_quick_start ADD COLUMN logintheme INT NOT NULL DEFAULT 0;


-- ----------------------------------------------------------------------------------------------------
-- V217_Chaos_Root_Abyss_Runes.sql
-- Chaos Root Abyss bosses drop 4 of each Yggdrasil Rune instead of 1 (Normal unchanged)
UPDATE mob_drops SET minquant = 4, maxquant = 4
WHERE mobid IN (8900003, 8910000, 8920003, 8920006, 8930000)
  AND itemid IN (4310064, 4310065)
  AND minquant = 1 AND maxquant = 1;


-- ----------------------------------------------------------------------------------------------------
-- V218_Boss_Drop_Cleanup.sql
-- Boss drop cleanup (maplestorywiki.net boss /Monster pages, GMS v270/v271 Interactive lists; chances per 10,000
-- before the drop rate, in line with the bosses' existing rows). Earlier QoL changes (Chaos Root Abyss runes x4,
-- Piece boxes x5, droplet stones x2, soul shards 30%, Spell Traces 0.5%, Eternal box rates, no Potential scrolls or
-- Power Elixirs from bosses) are left as they are. Every insert checks first, so running it again changes nothing.

-- 1. healing potions out of every boss table (Power Elixir, Reindeer Milk, Sunset Dew, Sparkling Star Potions)
DELETE FROM mob_drops WHERE itemid IN (2000005, 2000036, 2001554, 2003586, 2003587, 2020013)
    AND mobid IN (8500002, 8500012, 8500022, 8644650, 8644655, 8645009, 8645066, 8800002, 8800022, 8800102, 8810018, 8810118, 8810122, 8810214, 8820000, 8820001, 8820100, 8820212, 8840000, 8840007, 8840014, 8850011, 8850012, 8850111, 8860000, 8860001, 8860005, 8870000, 8870100, 8880000, 8880002, 8880010, 8880100, 8880101, 8880110, 8880111, 8880156, 8880167, 8880177, 8880200, 8880302, 8880342, 8880362, 8880410, 8880450, 8880504, 8880518, 8880614, 8880615, 8880644, 8880664, 8880725, 8880726, 8880808, 8880845, 8880915, 8880945, 8880975, 8881018, 8881038, 8881058, 8881103, 8881153, 8881203, 8881334, 8881384, 8881624, 8881674, 8881715, 8881745, 8881775, 8881805, 8881868, 8881878, 8881904, 8881954, 8882013, 8882063, 8900003, 8900103, 8910000, 8910100, 8920006, 8920106, 8930000, 8930100, 8950001, 8950002, 8950102, 8950113, 8950114, 8950115, 9210046, 9210051, 9210052, 9402282, 9421581, 9421589, 9450022, 9601459, 9601460, 9601461, 9601462, 9601623);
-- event scrolls on Hard Will
DELETE FROM mob_drops WHERE mobid = 8950115 AND itemid IN (2046876, 2046995, 2047817, 2047950);
-- level-capped Rebirth Flames (Lv. 110-150) are below these bosses' gear: Lv. 110-140 go, Lv. 150 becomes a
-- Powerful Rebirth Flame where the boss had none
DELETE FROM mob_drops WHERE itemid IN (2048700, 2048701, 2048702, 2048703)
    AND mobid IN (8500002, 8500012, 8500022, 8644650, 8644655, 8645009, 8645066, 8800002, 8800022, 8800102, 8810018, 8810118, 8810122, 8810214, 8820000, 8820001, 8820100, 8820212, 8840000, 8840007, 8840014, 8850011, 8850012, 8850111, 8860000, 8860001, 8860005, 8870000, 8870100, 8880000, 8880002, 8880010, 8880100, 8880101, 8880110, 8880111, 8880156, 8880167, 8880177, 8880200, 8880302, 8880342, 8880362, 8880410, 8880450, 8880504, 8880518, 8880614, 8880615, 8880644, 8880664, 8880725, 8880726, 8880808, 8880845, 8880915, 8880945, 8880975, 8881018, 8881038, 8881058, 8881103, 8881153, 8881203, 8881334, 8881384, 8881624, 8881674, 8881715, 8881745, 8881775, 8881805, 8881868, 8881878, 8881904, 8881954, 8882013, 8882063, 8900003, 8900103, 8910000, 8910100, 8920006, 8920106, 8930000, 8930100, 8950001, 8950002, 8950102, 8950113, 8950114, 8950115, 9210046, 9210051, 9210052, 9402282, 9421581, 9421589, 9450022, 9601459, 9601460, 9601461, 9601462, 9601623);
DELETE d FROM mob_drops d JOIN (SELECT DISTINCT mobid FROM mob_drops WHERE itemid = 2048716) p ON p.mobid = d.mobid
WHERE d.itemid = 2048708 AND d.mobid IN (8500002, 8500012, 8500022, 8644650, 8644655, 8645009, 8645066, 8800002, 8800022, 8800102, 8810018, 8810118, 8810122, 8810214, 8820000, 8820001, 8820100, 8820212, 8840000, 8840007, 8840014, 8850011, 8850012, 8850111, 8860000, 8860001, 8860005, 8870000, 8870100, 8880000, 8880002, 8880010, 8880100, 8880101, 8880110, 8880111, 8880156, 8880167, 8880177, 8880200, 8880302, 8880342, 8880362, 8880410, 8880450, 8880504, 8880518, 8880614, 8880615, 8880644, 8880664, 8880725, 8880726, 8880808, 8880845, 8880915, 8880945, 8880975, 8881018, 8881038, 8881058, 8881103, 8881153, 8881203, 8881334, 8881384, 8881624, 8881674, 8881715, 8881745, 8881775, 8881805, 8881868, 8881878, 8881904, 8881954, 8882013, 8882063, 8900003, 8900103, 8910000, 8910100, 8920006, 8920106, 8930000, 8930100, 8950001, 8950002, 8950102, 8950113, 8950114, 8950115, 9210046, 9210051, 9210052, 9402282, 9421581, 9421589, 9450022, 9601459, 9601460, 9601461, 9601462, 9601623);
UPDATE mob_drops SET itemid = 2048716 WHERE itemid = 2048708 AND mobid IN (8500002, 8500012, 8500022, 8644650, 8644655, 8645009, 8645066, 8800002, 8800022, 8800102, 8810018, 8810118, 8810122, 8810214, 8820000, 8820001, 8820100, 8820212, 8840000, 8840007, 8840014, 8850011, 8850012, 8850111, 8860000, 8860001, 8860005, 8870000, 8870100, 8880000, 8880002, 8880010, 8880100, 8880101, 8880110, 8880111, 8880156, 8880167, 8880177, 8880200, 8880302, 8880342, 8880362, 8880410, 8880450, 8880504, 8880518, 8880614, 8880615, 8880644, 8880664, 8880725, 8880726, 8880808, 8880845, 8880915, 8880945, 8880975, 8881018, 8881038, 8881058, 8881103, 8881153, 8881203, 8881334, 8881384, 8881624, 8881674, 8881715, 8881745, 8881775, 8881805, 8881868, 8881878, 8881904, 8881954, 8882013, 8882063, 8900003, 8900103, 8910000, 8910100, 8920006, 8920106, 8930000, 8930100, 8950001, 8950002, 8950102, 8950113, 8950114, 8950115, 9210046, 9210051, 9210052, 9402282, 9421581, 9421589, 9450022, 9601459, 9601460, 9601461, 9601462, 9601623);

-- 2. Damien: the Hard fight ends on 8880101 and Normal on 8880111 (BossParty, crystals), but the tables were the
-- other way round (Magic Eyepatch on Normal)
SET @swap_damien = (SELECT COUNT(*) FROM mob_drops WHERE mobid = 8880111 AND itemid = 1022278) > 0
    AND (SELECT COUNT(*) FROM mob_drops WHERE mobid = 8880101 AND itemid = 1022278) = 0;
UPDATE mob_drops SET mobid = IF(mobid = 8880101, 8880111, 8880101) WHERE @swap_damien AND mobid IN (8880101, 8880111);


-- 3. Black Mage: Genesis Crux (8880518, both modes) had no table. Hard list; Extreme's belt hammer and Dense Sol Erda are in code
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 1182285, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 1182285);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2633914, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2633914);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2633915, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2633915);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2633916, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2633916);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2633917, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2633917);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2633918, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2633918);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2633920, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2633920);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2003607, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2003607);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2049165, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2049165);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2636420, 10000, 10, 10 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2636420);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2636421, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2636421);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880518, 4001832, 50, 800, 800 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880518 AND itemid = 4001832);

-- 4. Chosen Seren: Daybreak Pendant, ring boxes, Mitra's Nodestone (Hard, Extreme), Vestige coupons (Extreme)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880644, 1122443, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880644 AND itemid = 1122443);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880644, 2028409, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880644 AND itemid = 2028409);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 1122443, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 1122443);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880614, 2632972, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880614 AND itemid = 2632972);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 1122443, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 1122443);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880615, 2632972, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880615 AND itemid = 2632972);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880664, 1122443, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880664 AND itemid = 1122443);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880664, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880664 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880664, 2632972, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880664 AND itemid = 2632972);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880664, 2831435, 10000, 30, 30 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880664 AND itemid = 2831435);

-- 5. Verus Hilla, Darknell, Gloom: Estella Earrings (Gloom, Darknell), ring boxes
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 1032330, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 1032330);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644655, 1032330, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644655 AND itemid = 1032330);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 1032330, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 1032330);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 1032330, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 1032330);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2028407, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2028407);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2028407, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2028407);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644655, 2028409, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644655 AND itemid = 2028409);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2028409, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2028409);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644650, 4001832, 50, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644650 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644655, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644655 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644655, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644655 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644655, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644655 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644655, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644655 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644655, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644655 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645009, 4001832, 50, 160, 160 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645009 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645066, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645066 AND itemid = 4001832);

-- 6. Grandis bosses: soul shards, androids, grindstones, ring boxes, boss accessories, Vestige coupons, the usual materials
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 2634499, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 2634499);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881018, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881018 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 2634499, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 2634499);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 1662206, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 1662206);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 2539001, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 2539001);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2634499, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2634499);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 1662206, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 1662206);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2539001, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2539001);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2634499, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2634499);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 1662206, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 1662206);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2539001, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2539001);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2831435, 10000, 180, 180 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2831435);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 2635761, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 2635761);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 2635761, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 2635761);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 1662198, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 1662198);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2635761, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2635761);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 1662198, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 1662198);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2635761, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2635761);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 1662198, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 1662198);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2539003, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2539003);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881715, 2830318, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881715 AND itemid = 2830318);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881715, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881715 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881715, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881715 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881715, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881715 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881715, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881715 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881715, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881715 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881715, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881715 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 2830318, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 2830318);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 1662267, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 1662267);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 2028410, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 2028410);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2830318, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2830318);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 1662267, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 1662267);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2830318, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2830318);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 1662267, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 1662267);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 1662224, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 1662224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 2539003, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 2539003);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 2638089, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 2638089);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 1662224, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 1662224);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2539003, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2539003);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2638089, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2638089);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 1113341, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 1113341);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2831435, 10000, 60, 60 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2831435);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 1662245, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 1662245);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 2539003, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 2539003);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 2639268, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 2639268);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 4021022, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 4021022);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 1662245, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 1662245);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2539003, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2539003);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2639268, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2639268);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 1122447, 50, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 1122447);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2831435, 10000, 120, 120 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2831435);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881904, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881904 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881954, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881954 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882013, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882013 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8882063, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8882063 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881868, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881868 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881868, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881868 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881868, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881868 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881868, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881868 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881868, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881868 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881868, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881868 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881878, 2028430, 300, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881878 AND itemid = 2028430);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881878, 2590006, 500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881878 AND itemid = 2590006);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881878, 2633892, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881878 AND itemid = 2633892);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881878, 4021031, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881878 AND itemid = 4021031);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881878, 4021020, 3000, 1, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881878 AND itemid = 4021020);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881878, 4001832, 50, 240, 240 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881878 AND itemid = 4001832);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881878, 2831039, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881878 AND itemid = 2831039);

-- 7. Gollux: every mode drops the tiers up to its own in all four slots (Hell had no rings, Hard no Reinforced ring)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1032220);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1113072);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1122264, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1122264);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601459, 1132243, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601459 AND itemid = 1132243);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032220);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1113072);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1122264, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1122264);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1132243, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1132243);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1032221);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1113073, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1113073);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1122265, 600, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1122265);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601460, 1132244, 600, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601460 AND itemid = 1132244);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032220);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1113072);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1122264, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1122264);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1132243, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1132243);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032221);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1113073, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1113073);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1122265, 600, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1122265);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1132244, 600, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1132244);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1032222, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1032222);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1113074, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1113074);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1122266, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1122266);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601461, 1132245, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601461 AND itemid = 1132245);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032220, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032220);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1113072, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1113072);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1122264, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1122264);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1132243, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1132243);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032221, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032221);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1113073, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1113073);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1122265, 600, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1122265);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1132244, 600, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1132244);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032222, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032222);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1113074, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1113074);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1122266, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1122266);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1132245, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1132245);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1032223, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1032223);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1113075, 400, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1113075);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1122267, 150, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1122267);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601462, 1132246, 150, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601462 AND itemid = 1132246);


-- ----------------------------------------------------------------------------------------------------
-- V219_Old_Fans_To_Spirit_Walker_Fans.sql
-- The v271 client has no two-handed fan weapon type (1552xxx; Kanna uses Spirit Walker Fans 1254xxx since the
-- Sengoku revamp): turn every old fan into its Spirit Walker Fan, keeping its stats, stars and potential.
CREATE TABLE tmp_fan_map (old_id INT PRIMARY KEY, new_id INT NOT NULL);
INSERT INTO tmp_fan_map (old_id, new_id) VALUES
(1552000, 1254001),
(1552001, 1254002),
(1552002, 1254003),
(1552003, 1254004),
(1552004, 1254005),
(1552005, 1254006),
(1552006, 1254007),
(1552007, 1254046),
(1552008, 1254009),
(1552009, 1254010),
(1552010, 1254012),
(1552011, 1254014),
(1552012, 1254061),
(1552013, 1254062),
(1552014, 1254065),
(1552015, 1254021),
(1552016, 1254067),
(1552017, 1254003),
(1552018, 1254007),
(1552019, 1254008),
(1552020, 1254009),
(1552021, 1254003),
(1552022, 1254005),
(1552023, 1254031),
(1552024, 1254007),
(1552025, 1254008),
(1552026, 1254003),
(1552027, 1254006),
(1552028, 1254009),
(1552033, 1254063),
(1552034, 1254064),
(1552035, 1254066),
(1552036, 1254003),
(1552037, 1254006),
(1552038, 1254009),
(1552039, 1254068),
(1552040, 1254016),
(1552044, 1254015),
(1552045, 1254043),
(1552046, 1254012),
(1552047, 1254060),
(1552048, 1254001),
(1552049, 1254001),
(1552050, 1254002),
(1552051, 1254003),
(1552052, 1254004),
(1552053, 1254005),
(1552054, 1254006),
(1552055, 1254007),
(1552056, 1254012),
(1552057, 1254061),
(1552058, 1254046),
(1552059, 1254047),
(1552060, 1254069),
(1552061, 1254048),
(1552063, 1254023),
(1552064, 1254008),
(1552065, 1254049),
(1552068, 1254042),
(1552069, 1254052),
(1552070, 1254053),
(1552071, 1254070),
(1552072, 1254040),
(1552073, 1254039),
(1552074, 1254054),
(1552075, 1254022),
(1552076, 1254080),
(1552077, 1254075),
(1552078, 1254070),
(1552079, 1254043),
(1552080, 1254012),
(1552081, 1254068),
(1552083, 1254052),
(1552084, 1254072),
(1552099, 1254013),
(1552100, 1254017),
(1552101, 1254018),
(1552102, 1254020),
(1552103, 1254019),
(1552109, 1254055),
(1552110, 1254025),
(1552111, 1254003),
(1552112, 1254006),
(1552113, 1254010),
(1552114, 1254068),
(1552116, 1254011),
(1552118, 1254056),
(1552119, 1254026),
(1552120, 1254025),
(1552123, 1254003),
(1552124, 1254006),
(1552125, 1254059),
(1552127, 1254023),
(1552128, 1254025),
(1552129, 1254027),
(1552130, 1254028),
(1552132, 1254026),
(1552134, 1254026),
(1552139, 1254044),
(1552141, 1254001),
(1552142, 1254005),
(1552143, 1254014),
(1552144, 1254019),
(1552151, 1254024),
(1552152, 1254029),
(1552153, 1254079);

UPDATE items i JOIN tmp_fan_map m ON i.itemid = m.old_id SET i.itemid = m.new_id;

UPDATE avatarlook a JOIN tmp_fan_map m ON a.weaponid = m.old_id SET a.weaponid = m.new_id;
UPDATE avatarlook a JOIN tmp_fan_map m ON a.weaponstickerid = m.old_id SET a.weaponstickerid = m.new_id;
UPDATE avatarlook a JOIN tmp_fan_map m ON FIND_IN_SET(m.old_id, a.hairequips) > 0
    SET a.hairequips = TRIM(BOTH ',' FROM REPLACE(CONCAT(',', a.hairequips, ','), CONCAT(',', m.old_id, ','), CONCAT(',', m.new_id, ',')));
UPDATE avatarlook a JOIN tmp_fan_map m ON FIND_IN_SET(m.old_id, a.unseenequips) > 0
    SET a.unseenequips = TRIM(BOTH ',' FROM REPLACE(CONCAT(',', a.unseenequips, ','), CONCAT(',', m.old_id, ','), CONCAT(',', m.new_id, ',')));

-- drop and shop rows: keep the existing Spirit Walker row where the mob or shop already has one
DELETE d FROM mob_drops d JOIN tmp_fan_map m ON d.itemid = m.old_id
    JOIN (SELECT mobid, itemid FROM mob_drops) e ON e.mobid = d.mobid AND e.itemid = m.new_id;
DELETE d FROM mob_drops d JOIN tmp_fan_map m ON d.itemid = m.old_id
    JOIN (SELECT id, mobid, itemid FROM mob_drops) e ON e.mobid = d.mobid AND e.id < d.id
    JOIN tmp_fan_map m2 ON e.itemid = m2.old_id AND m2.new_id = m.new_id;
UPDATE mob_drops d JOIN tmp_fan_map m ON d.itemid = m.old_id SET d.itemid = m.new_id;

DELETE s FROM shopitems s JOIN tmp_fan_map m ON s.itemid = m.old_id
    JOIN (SELECT shopid, itemid FROM shopitems) e ON e.shopid = s.shopid AND e.itemid = m.new_id;
DELETE s FROM shopitems s JOIN tmp_fan_map m ON s.itemid = m.old_id
    JOIN (SELECT id, shopid, itemid FROM shopitems) e ON e.shopid = s.shopid AND e.id < s.id
    JOIN tmp_fan_map m2 ON e.itemid = m2.old_id AND m2.new_id = m.new_id;
UPDATE shopitems s JOIN tmp_fan_map m ON s.itemid = m.old_id SET s.itemid = m.new_id;

DELETE d FROM equip_drops d JOIN tmp_fan_map m ON d.id = m.old_id
    JOIN (SELECT id, job FROM equip_drops) e ON e.job = d.job AND e.id = m.new_id;
UPDATE equip_drops d JOIN tmp_fan_map m ON d.id = m.old_id SET d.id = m.new_id;

DROP TABLE tmp_fan_map;


-- ----------------------------------------------------------------------------------------------------
-- V220_Legion_Weekly_Dragon_Record.sql
-- the weekly dragon quest's record holds its kill counts; clear the start date an old script wrote over them
UPDATE quests SET qrvalue = NULL WHERE qrkey = 503281 AND qrvalue LIKE 'startDate=%';



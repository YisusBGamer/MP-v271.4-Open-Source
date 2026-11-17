-- FriendStory (Shinsoo International School) quest items that no monster dropped; the school's motes drop them.
-- Quest items only drop for characters that have the quest. Each insert is skipped if the drop already exists.
-- Bizarre Dust Ball (32736 The Motes)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4030038, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4030038);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4030038, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4030038);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4030038, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4030038);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4030038, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4030038);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4030038, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4030038);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4030038, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4030038);
-- Student Records Page (32749 Student Records 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4030039, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4030039);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4030039, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4030039);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4030039, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4030039);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4030039, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4030039);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4030039, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4030039);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4030039, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4030039);
-- Aluminum Powder (32766 Oz's Hunt 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034165, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034165);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034165, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034165);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034165, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034165);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034165, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034165);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034165, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034165);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034165, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034165);
-- Laxative (32773 The Healing Process 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034172, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034172);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034172, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034172);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034172, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034172);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034172, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034172);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034172, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034172);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034172, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034172);
-- Flu Virus (32807)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034150, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034150);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034150, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034150);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034150, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034150);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034150, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034150);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034150, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034150);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034150, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034150);
-- Chalk (32810)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034151, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034151);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034151, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034151);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034151, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034151);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034151, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034151);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034151, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034151);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034151, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034151);
-- Suspicious Recorder (32854)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034173, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034173);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034173, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034173);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034173, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034173);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034173, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034173);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034173, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034173);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034173, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034173);
-- Big Ketchup Bottle (32860 Following the Story 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034174, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034174);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034174, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034174);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034174, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034174);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034174, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034174);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034174, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034174);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034174, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034174);
-- Practice Music Sheet (33510)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034346, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034346);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034346, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034346);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034346, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034346);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034346, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034346);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034346, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034346);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034346, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034346);
-- Fever Medicine (33517)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034348, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034348);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034348, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034348);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034348, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034348);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034348, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034348);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034348, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034348);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034348, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034348);
-- Pamphlet (33520)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034347, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034347);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034347, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034347);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034347, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034347);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034347, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034347);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034347, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034347);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034347, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034347);

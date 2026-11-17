-- FriendStory Hidden Story: Cheese and Corn (33047) needs a Hoodlum's Love Letter (4034349) "from the Mote"; no monster dropped it.
-- Quest item: only drops for characters that have the quest. Each insert is skipped if the drop already exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700020, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700020 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034349);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034349, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034349);

-- Knight Stronghold, 'Piercing Defenses' (31125): "Defeat the guards defending the entrance of the stronghold and get
-- one of their emblems" (Quest.wz). Nothing dropped the emblem (4032922). Official Knight A (8610022) guards the
-- Stronghold Entrance (271030010). It only shows up for characters on the quest. Skipped if the drop exists.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8610022, 4032922, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8610022 AND itemid = 4032922);

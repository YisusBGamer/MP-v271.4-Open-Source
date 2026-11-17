-- El Nath, 'Taboo Priest' (3191): "To make a Barrier Totem, you must get Lich's Red Contract Orb and Bible of the
-- Corrupt" (Quest.wz). Those drops were only on the old Lich (6090000), which no map has any more; the Lich players
-- meet is the one behind the Mystic Gate in Forest of Dead Trees IV (9300514, map 931050428). Quest items only show
-- up for characters that have the quest. Each insert is skipped if the drop already exists.
-- Red Contract Orb | Lich (3191 Taboo Priest)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300514, 4000633, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300514 AND itemid = 4000633);
-- Bible of the Corrupt | Lich (3191 Taboo Priest)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300514, 4000336, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300514 AND itemid = 4000336);

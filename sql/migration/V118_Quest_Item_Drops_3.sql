-- Quest items missed by V115. Same rules as V115 (official data only; quest items only show
-- up for characters that have the quest; skipped if the drop already exists).
-- Prison Guard Ani | Congealed Evil (3147 Looking Into the Lion King: Final: "Go deep into the Lion King's Castle,
-- defeat the Prison Guard Ani, and bring back the Congealed Evil"). Always drops: Ani is fought once per visit.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8210013, 4033114, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8210013 AND itemid = 4033114);

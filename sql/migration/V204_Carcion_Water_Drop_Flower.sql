-- Pinjar Specialty (38710): Water Drop Flowers (4037198) from the Specters of the Giant Coral Colony (410007620-622:
-- Despairing Guard 8645420, Monstrous Shadow Bird 8645421), as the quest says; nothing dropped them.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645420, 4037198, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645420 AND itemid = 4037198);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8645421, 4037198, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645421 AND itemid = 4037198);

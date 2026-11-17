-- Drop rows pointing at items or monsters the v271 data doesn't have.

-- familiar cards gone from Item.wz: the mob's v271 card (Character.wz Familiar MobID) where it has one
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT mobid, 2880583, chance, minquant, maxquant FROM mob_drops a WHERE a.mobid = 9400590 AND a.itemid = 2870513 AND NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) b WHERE b.mobid = 9400590 AND b.itemid = 2880583);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT mobid, 2880584, chance, minquant, maxquant FROM mob_drops a WHERE a.mobid = 9400591 AND a.itemid = 2870515 AND NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) b WHERE b.mobid = 9400591 AND b.itemid = 2880584);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT mobid, 2880586, chance, minquant, maxquant FROM mob_drops a WHERE a.mobid = 9400593 AND a.itemid = 2870516 AND NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) b WHERE b.mobid = 9400593 AND b.itemid = 2880586);
DELETE FROM mob_drops WHERE itemid IN (2870512, 2870513, 2870515, 2870516, 2870523, 2870996, 2870997, 2870998, 2870999,
    2871026, 2871188, 2871189, 2871190, 2871191, 2871192, 2880554, 2880555, 2880558, 2880560, 2880561, 2880562,
    2880564, 2880664);

-- mob ids written as item ids (King Sage Cat, Melon Bubble Tea)
DELETE FROM mob_drops WHERE itemid IN (2600305, 3400002);
DELETE FROM mob_drops WHERE mobid = 0 AND itemid = 0;

-- Crimson Queen's old final-phase ids: their drops belong to the v271 final body
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT a.mobid + 3, a.itemid, a.chance, a.minquant, a.maxquant FROM mob_drops a WHERE a.mobid IN (8920003, 8920103) AND NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) b WHERE b.mobid = a.mobid + 3 AND b.itemid = a.itemid);

-- monsters missing from Mob.wz
DELETE FROM mob_drops WHERE mobid IN (1000041, 1000044, 4034527, 8220037, 8290099, 8920003, 8920103, 9101054, 9101127,
    9101128, 9270030);

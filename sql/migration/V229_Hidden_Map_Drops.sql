-- Hidden hunting maps without drops: their monsters drop what the same monster of their area's regular maps drops
-- (Arcane Symbols, spell essences, potions); quest items left out.

-- Hidden Cave (450001262): Arma's Follower 8641015 as 8641009
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8641015, a.itemid, a.chance, a.minquant, a.maxquant FROM mob_drops a WHERE a.mobid = 8641009 AND NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) b WHERE b.mobid = 8641015 AND b.itemid = a.itemid);

-- Hidden Plate Street (450003360): Crooked Victory Plate 8643014-8643016 as 8643006
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT m.id, a.itemid, a.chance, a.minquant, a.maxquant FROM mob_drops a JOIN (SELECT 8643014 AS id UNION ALL SELECT 8643015 UNION ALL SELECT 8643016) m WHERE a.mobid = 8643006 AND a.itemid NOT IN (4034982, 4034983, 4034984, 4034985, 4034986, 4034987, 4034988, 4036572) AND NOT EXISTS (SELECT 1 FROM (SELECT mobid, itemid FROM mob_drops) b WHERE b.mobid = m.id AND b.itemid = a.itemid);

-- Hunting-map monsters with no drop rows of their own: the rows of the same monster (same name, within 15
-- levels) that has them.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT m.mobid, a.itemid, a.chance, a.minquant, a.maxquant
FROM mob_drops a JOIN (
    SELECT 2300101 AS mobid, 9300736 AS donor
    UNION ALL SELECT 2300260 AS mobid, 2300271 AS donor
    UNION ALL SELECT 2400311 AS mobid, 2400318 AS donor
    UNION ALL SELECT 4310022 AS mobid, 4310005 AS donor
    UNION ALL SELECT 4310023 AS mobid, 4310007 AS donor
    UNION ALL SELECT 4310024 AS mobid, 4310009 AS donor
    UNION ALL SELECT 4310025 AS mobid, 4310011 AS donor
    UNION ALL SELECT 4310026 AS mobid, 4310014 AS donor
    UNION ALL SELECT 5100103 AS mobid, 5100005 AS donor
    UNION ALL SELECT 8140555 AS mobid, 8230025 AS donor
    UNION ALL SELECT 8147015 AS mobid, 8147007 AS donor
    UNION ALL SELECT 8190001 AS mobid, 8190000 AS donor
    UNION ALL SELECT 8211005 AS mobid, 8211000 AS donor
    UNION ALL SELECT 8211006 AS mobid, 8211001 AS donor
    UNION ALL SELECT 8211007 AS mobid, 8211002 AS donor
    UNION ALL SELECT 8620028 AS mobid, 8620000 AS donor
    UNION ALL SELECT 8620029 AS mobid, 8620003 AS donor
    UNION ALL SELECT 8620030 AS mobid, 8620010 AS donor
    UNION ALL SELECT 8620031 AS mobid, 8620011 AS donor
    UNION ALL SELECT 8643016 AS mobid, 8643011 AS donor
    UNION ALL SELECT 9390843 AS mobid, 9390808 AS donor
    UNION ALL SELECT 9390845 AS mobid, 9390810 AS donor
    UNION ALL SELECT 9390846 AS mobid, 9390811 AS donor
    UNION ALL SELECT 9400015 AS mobid, 9400039 AS donor
    UNION ALL SELECT 9400030 AS mobid, 9400000 AS donor
) m ON a.mobid = m.donor
WHERE NOT EXISTS (SELECT 1 FROM (SELECT mobid FROM mob_drops) b WHERE b.mobid = m.mobid);

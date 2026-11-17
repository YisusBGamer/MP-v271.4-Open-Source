-- The Haven exchange uses 20 Grade A cores and 1 Grade S core for each AbsoLab Coin.
-- Raise Grade A core chance from 75% to 100% on Scrapyard androids. Drop modifiers still apply.
UPDATE mob_drops
SET chance = 10000
WHERE itemid = 4001842
  AND mobid IN (8250028, 8250024, 8250026, 8250020, 8250022, 8250016, 8250018,
                8250013, 8250014, 8250009, 8250008, 8250011, 8250010, 8250005,
                8250004, 8250007, 8250006, 8250001, 8250000, 8250003, 8250002);

-- Add any missing Grade A sources for databases that predate these Scrapyard drops.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT sources.mobid, 4001842, 10000, 1, 1
FROM (
    SELECT 8250028 AS mobid
    UNION ALL SELECT 8250024
    UNION ALL SELECT 8250026
    UNION ALL SELECT 8250020
    UNION ALL SELECT 8250022
    UNION ALL SELECT 8250016
    UNION ALL SELECT 8250018
    UNION ALL SELECT 8250013
    UNION ALL SELECT 8250014
    UNION ALL SELECT 8250009
    UNION ALL SELECT 8250008
    UNION ALL SELECT 8250011
    UNION ALL SELECT 8250010
    UNION ALL SELECT 8250005
    UNION ALL SELECT 8250004
    UNION ALL SELECT 8250007
    UNION ALL SELECT 8250006
    UNION ALL SELECT 8250001
    UNION ALL SELECT 8250000
    UNION ALL SELECT 8250003
    UNION ALL SELECT 8250002
) sources
WHERE NOT EXISTS (
    SELECT 1 FROM mob_drops existing
    WHERE existing.mobid = sources.mobid AND existing.itemid = 4001842
);

-- Lotus supplies the Grade S core required by the same exchange; retain the existing 75% base chance.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT sources.mobid, 4001843, 7500, 1, 1
FROM (
    SELECT 8950001 AS mobid
    UNION ALL SELECT 8950002
    UNION ALL SELECT 8240098
) sources
WHERE NOT EXISTS (
    SELECT 1 FROM mob_drops existing
    WHERE existing.mobid = sources.mobid AND existing.itemid = 4001843
);

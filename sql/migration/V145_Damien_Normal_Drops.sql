-- Normal Damien's drops: one row per item instead of repeats, so more kinds of items come out of a kill, plus
-- the rewards on the official list that exist in v232, alongside the items already here (kept as extras).
-- Official list: the MapleWiki Damien page (Normal: the Restrained Sword of Destruction's rewards); in v232 they come
-- from Damien's phase 2 (8880101).

-- 1. Repeats: keep one row per item, the one with the highest chance (Boss Medal of Honor was on 16 rows at 100%,
--    Crusader Coins on 11 rows, Twisted Stigma Spirit Stone on 4, Black Mage's Token on 3, Elixir on 2).
DELETE d FROM mob_drops d
    JOIN mob_drops k ON k.mobid = d.mobid AND k.itemid = d.itemid
        AND (k.chance > d.chance OR (k.chance = d.chance AND k.id < d.id))
WHERE d.mobid IN (8880100, 8880101);

-- 2. Missing official rewards (chance out of 10000, before the server's drop rate). Potions, recipes and Potential
--    scrolls are skipped (V146 keeps those off every boss). Not in v232 and so left out: Small EXP Accumulation
--    Potion, Green Jade Boss Ring Box (Basic), Bonus Mystical Cube.
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8880101, v.itemid, v.chance, v.minquant, v.maxquant FROM (
-- Sparkling Blue Star Potion
    SELECT 2003586 AS itemid, 3000 AS chance, 3 AS minquant, 5 AS maxquant UNION ALL
-- Sparkling Red Star Potion
    SELECT 2003587, 3000, 3, 5 UNION ALL
-- Production Soul Enchanter (no time limit)
    SELECT 2590006, 500, 1, 1 UNION ALL
-- Large Boss Medal of Honor
    SELECT 2633892, 10000, 1, 1 UNION ALL
-- 50% Bonus EXP Coupon Voucher
    SELECT 2633920, 1000, 1, 1 UNION ALL
-- Confusion Fragment
    SELECT 4021020, 3000, 1, 3 UNION ALL
-- Twisted Time
    SELECT 4021031, 2000, 1, 2 UNION ALL
-- Spell Trace x160
    SELECT 4001832, 10000, 160, 160
) v
WHERE NOT EXISTS (SELECT 1 FROM mob_drops m WHERE m.mobid = 8880101 AND m.itemid = v.itemid);

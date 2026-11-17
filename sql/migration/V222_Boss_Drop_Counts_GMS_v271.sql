-- Boss drop amounts raised to what GMS v271 drops per kill (each drop object holds at least one, so these
-- counts are minimums): Chaos Root Abyss pieces, Cygnus's Pieces of Ruin/Darkness, Hard Magnus's coins.
UPDATE mob_drops SET minquant = 8, maxquant = 8 WHERE mobid = 8900003 AND itemid = 2434585;  -- Chaos Pierre
UPDATE mob_drops SET minquant = 9, maxquant = 9 WHERE mobid = 8910000 AND itemid = 2434584;  -- Chaos Von Bon
UPDATE mob_drops SET minquant = 11, maxquant = 11 WHERE mobid = 8920006 AND itemid = 2434586; -- Chaos Crimson Queen
UPDATE mob_drops SET minquant = 9, maxquant = 10 WHERE mobid = 8930000 AND itemid = 2434587;  -- Chaos Vellum
UPDATE mob_drops SET minquant = 9, maxquant = 9 WHERE mobid = 8850011 AND itemid = 2434588;   -- Cygnus: Piece of Ruin
UPDATE mob_drops SET minquant = 11, maxquant = 11 WHERE mobid = 8850011 AND itemid = 2434589; -- Cygnus: Piece of Darkness
UPDATE mob_drops SET chance = 10000, minquant = 9, maxquant = 9 WHERE mobid = 8880000 AND itemid = 4310058; -- Hard Magnus: Magnus Coin
UPDATE mob_drops SET chance = 10000, minquant = 6, maxquant = 6 WHERE mobid = 8880000 AND itemid = 4310059; -- Hard Magnus: Shadowdealer Coin

-- Chaos Root Abyss and Cygnus "Piece of" boxes: a sure drop of 5 instead of a 35% chance of 1.
UPDATE mob_drops SET chance = 10000, minquant = 5, maxquant = 5
WHERE itemid IN (2434584, 2434585, 2434586, 2434587, 2434588, 2434589)
  AND mobid IN (8900003, 8910000, 8920006, 8930000, 8850011, 8850111);
-- Extraordinary Energy Core (Lotus) and Butterfly Droplet Stone (Lucid): twice as many per drop.
UPDATE mob_drops SET minquant = minquant * 2, maxquant = maxquant * 2
WHERE itemid IN (4001843, 4001879);

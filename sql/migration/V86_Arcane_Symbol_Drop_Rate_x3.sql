-- Arcane Symbols (Vanishing Journey, Chu Chu Island, Lachelein, Arcana, Morass, Esfera) drop three times as often
-- from Arcane River monsters: 30-33 -> 90-99 out of 10000 before the server drop rate (about 6% -> 18-20% per kill
-- at x20 drop rate and no drop-rate gear/buffs).
UPDATE mob_drops SET chance = chance * 3 WHERE itemid BETWEEN 1712001 AND 1712006 AND chance > 0;

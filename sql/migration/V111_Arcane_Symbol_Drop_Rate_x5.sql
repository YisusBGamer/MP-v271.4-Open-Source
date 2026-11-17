-- Arcane Symbols (Vanishing Journey, Chu Chu Island, Lachelein, Arcana, Morass, Esfera) drop five times as often
-- again, on top of V86's x3: 90-99 -> 450-495 out of 10000 before the server drop rate (about 18-20% -> about 90% per
-- kill at x20 drop rate and no drop-rate gear/buffs; any drop-rate boost makes it every kill).
UPDATE mob_drops SET chance = LEAST(chance * 5, 10000) WHERE itemid BETWEEN 1712001 AND 1712006 AND chance > 0;

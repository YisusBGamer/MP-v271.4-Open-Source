-- Nodestones and Arcane/Sacred Symbols drop twice as often.
UPDATE mob_drops SET chance = LEAST(10000, chance * 2)
WHERE itemid IN (2435719, 2435902, 2436078, 2436324, 2439869, 2631128)
   OR itemid BETWEEN 1712000 AND 1713999;

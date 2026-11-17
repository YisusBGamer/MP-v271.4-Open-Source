-- Chaos Root Abyss bosses drop 4 of each Yggdrasil Rune instead of 1 (Normal unchanged)
UPDATE mob_drops SET minquant = 4, maxquant = 4
WHERE mobid IN (8900003, 8910000, 8920003, 8920006, 8930000)
  AND itemid IN (4310064, 4310065)
  AND minquant = 1 AND maxquant = 1;

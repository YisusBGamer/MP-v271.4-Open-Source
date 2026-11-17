-- 37856 [Esfera] Erda Energy (4037359, collected from the enemies around Living Spring 5): from the Bellalion (8644502)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644502, 4037359, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644502 AND itemid = 4037359);

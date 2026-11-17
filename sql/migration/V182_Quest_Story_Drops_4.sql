-- Quest items the v265 walk found nothing giving (quest audit 2026-10-24, part 2):
--   39134 [Weekly Quest] Black Heaven Deck: Collect Steel Drill: Steel Drill (4034293) from the Demolishizer (8250012)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8250012, 4034293, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8250012 AND itemid = 4034293);

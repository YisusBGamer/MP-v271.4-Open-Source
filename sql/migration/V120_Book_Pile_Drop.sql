-- Veritas, the laboratory (923050005): "A Gift Most Manly 1" (23681) says "Go attack the Messy Book Pile laying on
-- the floor of the laboratory and bring back a book" (Quest.wz), but the Messy Book Pile (9300644) dropped nothing.
-- It drops the Book Pile (4033644) now; quest items only show up for characters that have the quest.
-- Book Pile | Messy Book Pile (23681 A Gift Most Manly 1)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300644, 4033644, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300644 AND itemid = 4033644);

-- Korean Folk Town: Hongbu's and Nolbu's gourds. The seed dropped on the roof grows into a Gourd (9500400,
-- reactor scripts fvquest0/fvquest1); what comes out of it is the quest item: the Box of Jewelry for "Opening
-- Hongbu's Gourd" (3608), the Warrant of Attachment for "Opening Nolbu's Gourd" (3607). Quest items only show up for
-- characters that have the quest (the two quests exclude each other). Each insert is skipped if the drop exists.
-- Box of Jewelry | Gourd (3608 Opening Hongbu's Gourd)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500400, 4031223, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500400 AND itemid = 4031223);
-- Warrant of Attachment | Gourd (3607 Opening Nolbu's Gourd)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9500400, 4031224, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9500400 AND itemid = 4031224);

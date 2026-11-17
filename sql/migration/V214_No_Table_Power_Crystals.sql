-- Intense Power Crystals come only from the boss kill itself (Mob.java: one per fighter, as in GMS); the old drop-table
-- rows (30%, 1-2 each, then multiplied by the drop rate) gave extra crystals on top.
DELETE FROM mob_drops WHERE itemid = 4001886;

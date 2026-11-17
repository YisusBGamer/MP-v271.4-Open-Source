-- Crafting is removed from the server: no
-- monster drops the Maker's Forging/Production Stimulators (4130000-4130999) or Forging/Production Manuals
-- (4131000-4131999) any more. They were left on 7 monsters (e.g. 2230103, 2110300).
DELETE FROM mob_drops WHERE itemid BETWEEN 4130000 AND 4131999;

-- Sacred Symbols drop as often as Arcane Symbols:
-- Sacred Symbol: Cernium (1713000) and Arcus (1713001) dropped at 10 (0.1%); the Arcane Symbols drop at 495 (4.95%,
-- Vanishing Journey's 450). Both go to 495.
UPDATE mob_drops SET chance = 495 WHERE itemid IN (1713000, 1713001);

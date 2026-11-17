-- Items that don't work yet come out of the shops until they do (plans/features/pulled-items.tsv says when they return).
-- Violet Cube: no pick-from-six handling.
DELETE FROM shopitems WHERE itemid = 5062024 AND shopid IN (9010038, 9010040);
-- Potential Stamps: retired items whose Power Elixir exchange script doesn't exist yet, so using one does nothing.
DELETE FROM shopitems WHERE itemid = 2049500 AND shopid = 9001174;
DELETE FROM shopitems WHERE itemid = 2049506 AND shopid = 9010038;
DELETE FROM shopitems WHERE itemid = 2049509 AND shopid IN (9010107, 9010111);
DELETE FROM shopitems WHERE itemid = 2048212 AND shopid = 9010038;

-- The Summon Gauge is always full now, so Familiar Essence (2000039, refills the gauge by 50%) has
-- no use: Roro's Familiar shop (9133404) no longer sells it.
DELETE FROM shopitems WHERE shopid = 9133404 AND itemid = 2000039;

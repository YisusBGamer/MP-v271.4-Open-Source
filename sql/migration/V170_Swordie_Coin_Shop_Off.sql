-- Swordie Coins are off for now: the Swordie Plus shop (Smithy 9062231, the only shop priced in Swordie Coins,
-- 4310096) and the Inferno Wolf's coin drops (mob 9101078, the Polo/Fritto bounty portal) are removed. Coins
-- players already hold are left alone. The revert SQL that puts every removed row back is kept with the backup.
DELETE FROM shopitems WHERE shopid = 9062231 AND tokenitemid = 4310096;
DELETE FROM mob_drops WHERE mobid = 9101078 AND itemid = 4310096;

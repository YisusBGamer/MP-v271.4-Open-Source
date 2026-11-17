-- Swordie Plus: these quest items are caught with capture items again, as on official servers
-- (USER_BRIDLE_ITEM_USE_REQUEST handler in ItemHandler), so the drop fallbacks from V99/V100 are removed.
DELETE FROM mob_drops WHERE mobid = 9300795 AND itemid = 4033911; -- Time Cube -> Time Cube in a Bottle (40601)
DELETE FROM mob_drops WHERE mobid = 2600602 AND itemid = 4033865; -- Panda Teddy -> Boxed Teddy Bear (41302)
DELETE FROM mob_drops WHERE mobid = 9300761 AND itemid = 4033816; -- Ariant Scorpion (contest, 40109)
DELETE FROM mob_drops WHERE mobid = 2600315 AND itemid = 4033822; -- Panda (41159)
DELETE FROM mob_drops WHERE itemid = 4034111 AND mobid IN (3300102, 3300103, 3300105); -- Vikings (30061)

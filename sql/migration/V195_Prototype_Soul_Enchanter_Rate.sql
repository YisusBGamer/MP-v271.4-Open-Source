-- Prototype Soul Enchanters (2590004) dropped far too often from 255 monsters: 1/25 of the old chance (100 -> 4).
update mob_drops set chance = 4 where itemid = 2590004 and chance = 100;

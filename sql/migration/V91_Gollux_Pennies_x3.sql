-- Gollux Pennies x3: every Gollux Penny drop of the Gollux boss content (its shoulders and abdomen, the monsters on
-- its body and Gollux's Corrupted Heart) drops three times as many.
UPDATE mob_drops SET minquant = minquant * 3, maxquant = maxquant * 3 WHERE itemid = 4310098 AND mobid IN (9390610, 9390611, 9390612, 9390624, 9390625, 9390634, 9390637, 9390640, 9390643, 9601459, 9601460, 9601461, 9601462);

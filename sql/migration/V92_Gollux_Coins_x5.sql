-- Gollux Coins (4310097) dropped by Gollux five times over: Gollux's parts (9390612) and the Heart's reward
-- (9601459-9601462, by difficulty).
UPDATE mob_drops SET minquant = minquant * 5, maxquant = maxquant * 5 WHERE itemid = 4310097 AND mobid IN (9390612, 9601459, 9601460, 9601461, 9601462);

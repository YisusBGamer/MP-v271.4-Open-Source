-- Every Soul Shard (and Horntail Monsterbloom) drop below
-- 30% goes up to 30% (V146 added most at 1%: about 1,000 clears for the 10 a soul needs). Higher ones stay.
UPDATE mob_drops SET chance = 3000
WHERE chance < 3000 AND itemid IN (2431655, 2431656, 2431657, 2431661, 2431662, 2431709, 2431710, 2431711, 2431752,
    2431753, 2431895, 2431896, 2431964, 2433446, 2433515, 2433591, 2433592, 2433593, 2435366, 2435367, 2435369,
    2436039, 2437478, 2438396, 2439567, 2439568);

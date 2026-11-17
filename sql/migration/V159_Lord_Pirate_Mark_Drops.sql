-- Lord Pirate stage 2: each kind of pirate gives only its own mark (Guon asks for Rookie, then Rising, then Veteran).
-- The drop table had every Enraged pirate drop all three marks; LordPirateInstanceHelper drops the right one itself.
DELETE FROM mob_drops WHERE mobid IN (9300114, 9300115, 9300116) AND itemid IN (4001120, 4001121, 4001122);

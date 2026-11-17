-- The migrations V241 to V260 in order, folded in so a new database needs no catch-up at startup.
-- 09_migration_history.sql marks them done. sql/migration keeps the files for servers updating from older versions.


-- ----------------------------------------------------------------------------------------------------
-- V241_Boss_Faint_Sol_Erda_Energy.sql
-- Faint Sol Erda Energy (2636420) from weekly bosses, as many per clear as GMS v271 kills drop: Hard Lotus 5, Hard Damien 5,
-- Hard Will 5, Chaos Gloom 10, Hard Verus Hilla 12, Chaos Guardian Angel Slime 7, Hard Chosen Seren 2. Each goes on the
-- monsters that already hold that boss's drops here (the reward core, chest or the boss itself).

DELETE FROM mob_drops WHERE itemid = 2636420 AND mobid IN (8881153, 8950102, 8880101, 8950115, 8644655, 8880410, 8880725, 8880614, 8880615);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) VALUES
(8881153, 2636420, 10000, 5, 5),
(8950102, 2636420, 10000, 5, 5),
(8880101, 2636420, 10000, 5, 5),
(8950115, 2636420, 10000, 5, 5),
(8644655, 2636420, 10000, 10, 10),
(8880410, 2636420, 10000, 12, 12),
(8880725, 2636420, 10000, 7, 7),
(8880614, 2636420, 10000, 2, 2),
(8880615, 2636420, 10000, 2, 2);

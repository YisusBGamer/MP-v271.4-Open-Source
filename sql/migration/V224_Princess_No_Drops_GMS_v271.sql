-- Princess No (9450022) drops as GMS v271: no more Jewel Boxes (old event boxes the
-- client won't open); Captivating Fragments, Mystical Cubes, Boss Medals of Honor and Twisted Time instead.
-- Counts are drop objects per kill (each holds at least one).
DELETE FROM mob_drops WHERE mobid = 9450022 AND itemid IN (2432754, 2432755, 2433103, 2630594, 2710033, 4021031);
-- Captivating Fragment, Mystical Cube, Boss Medal of Honor, Twisted Time (the migration runner can't take a comment
-- at the end of a line inside a statement)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) VALUES
    (9450022, 2630594, 10000, 5, 6),
    (9450022, 2710033, 10000, 13, 13),
    (9450022, 2433103, 10000, 15, 15),
    (9450022, 4021031, 10000, 1, 1);

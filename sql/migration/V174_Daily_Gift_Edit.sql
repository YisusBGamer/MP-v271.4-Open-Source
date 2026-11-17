-- Daily Gift calendar, editable in Server Control (loaded at start, reloaded on request).
-- daily_gifts: one row per calendar day; expire_days 0 = the item doesn't expire.
-- daily_gift_settings: monster_goal (enemies to defeat a day), min_level (level needed).
CREATE TABLE IF NOT EXISTS daily_gifts (
    day INT NOT NULL,
    itemid INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    expire_days INT NOT NULL DEFAULT 0,
    PRIMARY KEY (day)
);

CREATE TABLE IF NOT EXISTS daily_gift_settings (
    name VARCHAR(32) NOT NULL,
    value VARCHAR(32) NOT NULL,
    PRIMARY KEY (name)
);

INSERT IGNORE INTO daily_gifts (day, itemid, quantity, expire_days) VALUES
    (1, 5040004, 1, 7),
    (2, 2450064, 2, 0),
    (3, 1122017, 1, 3),
    (4, 2710028, 10, 0),
    (5, 4001832, 500, 0),
    (6, 2432970, 1, 0),
    (7, 5680410, 1, 0),
    (8, 2710028, 50, 0),
    (9, 2450064, 2, 0),
    (10, 2710034, 20, 0),
    (11, 2048716, 1, 0),
    (12, 2000005, 100, 0),
    (13, 2350000, 1, 0),
    (14, 2710028, 50, 0),
    (15, 5040004, 1, 7),
    (16, 2450064, 2, 0),
    (17, 5205007, 1, 0),
    (18, 5062800, 1, 0),
    (19, 4001832, 500, 0),
    (20, 2430909, 1, 0),
    (21, 5205011, 1, 0),
    (22, 2023072, 1, 0),
    (23, 1113227, 1, 15),
    (24, 2028333, 5, 0),
    (25, 2710028, 50, 0),
    (26, 2049705, 1, 0),
    (27, 2028333, 20, 0),
    (28, 2730007, 30, 0);

INSERT IGNORE INTO daily_gift_settings (name, value) VALUES
    ('monster_goal', '300'),
    ('min_level', '33');

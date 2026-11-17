-- Burning characters: type (0 none, 1 Hyper Burning MAX, 2 Burning Beyond), the level it started from, and whether a
-- rebirth ended it. `burning` stays as "burning now" for outside readers.
ALTER TABLE characterstats
    ADD COLUMN burningType TINYINT NOT NULL DEFAULT 0,
    ADD COLUMN burningStartLevel INT NOT NULL DEFAULT 0,
    ADD COLUMN burningEnded TINYINT NOT NULL DEFAULT 0;

-- @burning characters become Hyper Burning MAX ones from level 10
UPDATE characterstats SET burningType = 1, burningStartLevel = 10 WHERE burning = 1;

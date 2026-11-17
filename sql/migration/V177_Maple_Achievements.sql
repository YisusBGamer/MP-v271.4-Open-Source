-- Maple Achievements, per account (GMS keeps them world-wide). missionid -1 = the whole achievement,
-- -2 = its reward was claimed. status: 1 in progress, 2 complete. unlocktime is a FileTime long.
CREATE TABLE IF NOT EXISTS achievements (
    accid INT NOT NULL,
    infoid INT NOT NULL,
    missionid TINYINT NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    value BIGINT NOT NULL DEFAULT 0,
    unlocktime BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (accid, infoid, missionid)
);

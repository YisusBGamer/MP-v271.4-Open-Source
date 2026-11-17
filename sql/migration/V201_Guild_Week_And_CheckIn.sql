-- Guild members: contribution this week (the guild ranking's weekly Honor EXP list) and the day of the last
-- guild check-in (yyyymmdd, UTC; the check-in window counts members by it).

ALTER TABLE guildmembers
    ADD COLUMN weekCommitment int NOT NULL DEFAULT 0,
    ADD COLUMN weekStart int NOT NULL DEFAULT 0,
    ADD COLUMN checkInDate int NOT NULL DEFAULT 0;

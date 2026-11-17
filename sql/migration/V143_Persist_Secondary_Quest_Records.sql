-- Some quests retain independent normal and extended records (for example visit and attack observations).
-- Preserve both across saves and restarts; existing normal records and quest statuses are unchanged.
ALTER TABLE quests ADD COLUMN qrvalueex TEXT NULL;

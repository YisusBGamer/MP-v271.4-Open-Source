-- Guild weekly mission: each member's points for the week (hunting, boss) and kills toward the next hunting point;
-- each guild's last settled week and that week's total (shown as last week's result, rewarded with GP).

ALTER TABLE guildmembers
    ADD COLUMN missionWeek int NOT NULL DEFAULT 0,
    ADD COLUMN missionHunt int NOT NULL DEFAULT 0,
    ADD COLUMN missionBoss int NOT NULL DEFAULT 0,
    ADD COLUMN missionKills int NOT NULL DEFAULT 0;

ALTER TABLE guilds
    ADD COLUMN missionCheckedWeek int NOT NULL DEFAULT 0,
    ADD COLUMN missionLastPoints int NOT NULL DEFAULT 0;

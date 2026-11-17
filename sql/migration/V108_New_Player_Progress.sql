-- New player help: per-login (users.id) daily check-in progress and whether the first-hour checklist reward
-- was already given (to one character of that login). See NewPlayerModule.
CREATE TABLE IF NOT EXISTS new_player_progress (
    userid INT NOT NULL PRIMARY KEY,
    checkin_day VARCHAR(10) NULL,
    checkin_step INT NOT NULL DEFAULT 0,
    checklist_rewarded TINYINT NOT NULL DEFAULT 0
);

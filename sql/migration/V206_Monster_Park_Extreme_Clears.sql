-- Monster Park Extreme weekly clears (MonsterParkExtreme.java): one clear per character and two per world (account)
-- each week; the week starts Thursday 00:00 (reset time zone) and is named by that date.
CREATE TABLE IF NOT EXISTS monster_park_extreme_clears (
    week VARCHAR(10) NOT NULL,
    char_id INT NOT NULL,
    account_id INT NOT NULL,
    char_name VARCHAR(16) NOT NULL DEFAULT '',
    cleared_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (week, char_id),
    KEY mpe_clears_account (week, account_id)
);

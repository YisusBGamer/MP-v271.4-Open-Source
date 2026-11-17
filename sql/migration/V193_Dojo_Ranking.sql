-- Mu Lung Dojo weekly ranking (DojoRanking.java): each character's best run of a ranking week (Thursday 00:30 to
-- Wednesday 23:30 UTC). grp: 0 = Novice (Lv. 105-200), 1 = Master (Lv. 201+). After the week, rank_overall and
-- rank_class are filled in, reward names what So Gong hands out, claimed says it was taken.
CREATE TABLE IF NOT EXISTS dojo_ranking (
    week VARCHAR(10) NOT NULL,
    char_id INT NOT NULL,
    char_name VARCHAR(16) NOT NULL DEFAULT '',
    job INT NOT NULL DEFAULT 0,
    level INT NOT NULL DEFAULT 0,
    grp TINYINT NOT NULL DEFAULT 0,
    floor INT NOT NULL DEFAULT 0,
    time_sec INT NOT NULL DEFAULT 0,
    settled TINYINT NOT NULL DEFAULT 0,
    rank_overall INT NOT NULL DEFAULT 0,
    rank_class INT NOT NULL DEFAULT 0,
    reward VARCHAR(64) DEFAULT NULL,
    claimed TINYINT NOT NULL DEFAULT 0,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (week, char_id),
    KEY dojo_ranking_char (char_id)
);

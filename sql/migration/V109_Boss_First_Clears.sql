-- Boss first clears: the first time each character defeated each boss (and difficulty), which the server
-- announces to everyone. See BossFirstClearModule. boss is e.g. "Zakum|Chaos", or "Black Mage" without a difficulty.
CREATE TABLE IF NOT EXISTS boss_first_clears (
    charid INT NOT NULL,
    boss VARCHAR(48) NOT NULL,
    cleared_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (charid, boss)
);

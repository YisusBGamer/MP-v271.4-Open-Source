-- Swordie Coins: what each account has been paid, and its coin shop purchases.
-- kind: boss (rkey = boss group, period = week), gift (rkey = gift day, period = month), level (rkey = level),
-- achievement (rkey = achievement id), cap (rkey = shop line, period = week or "ever"). amount: coins paid
-- (boss: the tier paid this week) or items bought (cap).
CREATE TABLE IF NOT EXISTS coin_rewards (
    accid INT NOT NULL,
    kind VARCHAR(16) NOT NULL,
    rkey VARCHAR(48) NOT NULL,
    period VARCHAR(16) NOT NULL,
    amount INT NOT NULL DEFAULT 0,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (accid, kind, rkey, period)
);

-- Daily Gift days 1 and 15: 30 Swordie Coins instead of the 7-day Hyper Teleport Rock (left alone if Server Control
-- already put something else there).
UPDATE daily_gifts SET itemid = 4310096, quantity = 30, expire_days = 0
WHERE day IN (1, 15) AND itemid = 5040004;

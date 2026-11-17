-- Legion Champions, per account: up to 6 slots (the client's quest records 504782-504787 hold cid/rank/time).
-- rank: 0 = C ... 5 = SSS.
CREATE TABLE IF NOT EXISTS union_champions (
    accid INT NOT NULL,
    slot TINYINT NOT NULL,
    charid INT NOT NULL,
    `rank` TINYINT NOT NULL DEFAULT 0,
    regtime BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (accid, slot)
);

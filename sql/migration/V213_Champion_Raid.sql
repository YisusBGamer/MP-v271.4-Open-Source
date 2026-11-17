-- Champion Raid (Draco Isle): this month's best score and Champion Coins per account
CREATE TABLE IF NOT EXISTS champion_raid (
    accid int NOT NULL PRIMARY KEY,
    month varchar(7) NOT NULL,
    best int NOT NULL DEFAULT 0,
    coins int NOT NULL DEFAULT 0
);

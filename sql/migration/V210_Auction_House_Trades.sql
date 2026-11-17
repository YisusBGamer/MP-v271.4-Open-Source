-- Auction House: buyer/seller sides of a sale are collected separately, and every trade is logged
ALTER TABLE auction_items
    ADD COLUMN selleruserid int NOT NULL DEFAULT 0,
    ADD COLUMN bidaccountid int NOT NULL DEFAULT 0,
    ADD COLUMN buyeruserid int NOT NULL DEFAULT 0,
    ADD COLUMN sellercollected tinyint(1) NOT NULL DEFAULT 0,
    ADD COLUMN buyercollected tinyint(1) NOT NULL DEFAULT 0,
    ADD COLUMN solddate datetime DEFAULT NULL,
    ADD COLUMN itemid int NOT NULL DEFAULT 0,
    ADD KEY idx_auction_items_state (state),
    ADD KEY idx_auction_items_bidaccountid (bidaccountid);

CREATE TABLE auction_log (
    id bigint NOT NULL AUTO_INCREMENT,
    time datetime NOT NULL,
    action varchar(16) NOT NULL,
    auctionid bigint NOT NULL,
    accountid int NOT NULL,
    charid int NOT NULL,
    otheraccountid int NOT NULL DEFAULT 0,
    itemsn bigint NOT NULL DEFAULT 0,
    itemid int NOT NULL DEFAULT 0,
    quantity int NOT NULL DEFAULT 0,
    mesos bigint NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_auction_log_auctionid (auctionid),
    KEY idx_auction_log_accountid (accountid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Duey delivery packages. The item (if any) is a row in items with no inventory; itemid 0 means mesos only.
CREATE TABLE IF NOT EXISTS parcels (
    id BIGINT NOT NULL AUTO_INCREMENT,
    receiverid INT NOT NULL,
    senderid INT NOT NULL DEFAULT 0,
    sendername VARCHAR(13) NOT NULL DEFAULT '',
    meso BIGINT NOT NULL DEFAULT 0,
    memo VARCHAR(200) NOT NULL DEFAULT '',
    quick TINYINT NOT NULL DEFAULT 0,
    senttime BIGINT NOT NULL DEFAULT 0,
    itemid BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_parcels_receiver (receiverid)
);

-- Cash Shop coupon codes (entered in the Cash Shop's coupon box). Codes are matched in upper case.
-- itemid 0 = no item; cash items go to the Cash Shop locker, other items to the inventory.
-- maxuses 0 = no overall limit (each account can still use a code once); expires = epoch ms, 0 = never.
CREATE TABLE IF NOT EXISTS coupons (
    code VARCHAR(32) NOT NULL,
    itemid INT NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 1,
    nx INT NOT NULL DEFAULT 0,
    maplepoints INT NOT NULL DEFAULT 0,
    mesos BIGINT NOT NULL DEFAULT 0,
    maxuses INT NOT NULL DEFAULT 0,
    uses INT NOT NULL DEFAULT 0,
    expires BIGINT NOT NULL DEFAULT 0,
    note VARCHAR(100) NOT NULL DEFAULT '',
    PRIMARY KEY (code)
);

CREATE TABLE IF NOT EXISTS coupon_uses (
    code VARCHAR(32) NOT NULL,
    accountid INT NOT NULL,
    charid INT NOT NULL DEFAULT 0,
    usedtime BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (code, accountid)
);

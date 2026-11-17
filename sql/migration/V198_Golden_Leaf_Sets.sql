-- Leaf Shop gear packages: the equipment sets (Etc.wz SetItemInfo ids) each account has worn whole at least once,
-- which opens that set's package for every character of the account. See GoldenLeafModule.
CREATE TABLE IF NOT EXISTS golden_leaf_sets (
    accountid INT NOT NULL,
    setid INT NOT NULL,
    unlocked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (accountid, setid)
);

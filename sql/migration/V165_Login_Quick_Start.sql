-- Login screen Quick Start: last played and favourite character per login (world, channel index, character id).
CREATE TABLE IF NOT EXISTS user_quick_start (
    userid INT NOT NULL PRIMARY KEY,
    lastworld INT NOT NULL DEFAULT -1,
    lastchannel INT NOT NULL DEFAULT 0,
    lastcharid INT NOT NULL DEFAULT 0,
    favworld INT NOT NULL DEFAULT -1,
    favchannel INT NOT NULL DEFAULT 0,
    favcharid INT NOT NULL DEFAULT 0
);

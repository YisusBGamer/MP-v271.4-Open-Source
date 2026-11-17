-- Server rates, read when the server starts.
-- The values below are the rates the server had before they could be changed here.
CREATE TABLE IF NOT EXISTS server_rates (
    name VARCHAR(32) NOT NULL PRIMARY KEY,
    value DOUBLE NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT IGNORE INTO server_rates (name, value) VALUES
    ('exp', 100),
    ('exp_low', 1),
    ('drop', 20),
    ('meso', 2000),
    ('nx', 6.25);

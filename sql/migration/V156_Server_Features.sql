-- Quality-of-life settings (ServerFeatures). No rows: a setting missing here uses its default, which is the
-- server's normal behaviour.
CREATE TABLE IF NOT EXISTS server_features (
    name VARCHAR(64) NOT NULL PRIMARY KEY,
    value VARCHAR(64) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(64) NULL
);

-- Every change saved from the admin page.
CREATE TABLE IF NOT EXISTS server_feature_log (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    old_value VARCHAR(64) NULL,
    new_value VARCHAR(64) NULL,
    changed_by VARCHAR(64) NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_server_feature_log_changed_at (changed_at)
);

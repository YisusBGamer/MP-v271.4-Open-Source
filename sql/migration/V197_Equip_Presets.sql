-- Equipment presets per character: the applied preset (0-2) and the ids of the Preset 1 items worn while another
-- preset is applied. The presets' stored items are rows in items (equipped inventory, bag index 3001-3265).
CREATE TABLE IF NOT EXISTS char_equip_presets (
    charid INT NOT NULL PRIMARY KEY,
    applied TINYINT NOT NULL DEFAULT 0,
    carried TEXT
);

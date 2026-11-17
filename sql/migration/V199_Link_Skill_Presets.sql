-- Link skill presets per character: the applied preset (0-2) and each preset's link skill ids (comma-separated:
-- the 8000xxxx link, or the stack skill for the Explorer / Cygnus / Resistance links).
CREATE TABLE IF NOT EXISTS char_link_presets (
    charid INT NOT NULL PRIMARY KEY,
    applied TINYINT NOT NULL DEFAULT 0,
    preset1 TEXT,
    preset2 TEXT,
    preset3 TEXT
);

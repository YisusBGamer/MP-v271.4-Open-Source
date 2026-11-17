-- Beauty Album (saved hair, face and skin slots) per character, plus Angelic Buster's dress-up look.
-- Slot lists are comma-separated ids, -1 for an empty slot. Dress-up ids of 0 mean the job's default look.
CREATE TABLE IF NOT EXISTS beauty_albums (
    charid INT NOT NULL,
    haircap INT NOT NULL DEFAULT 0,
    facecap INT NOT NULL DEFAULT 0,
    skincap INT NOT NULL DEFAULT 0,
    hairs VARCHAR(2048) NOT NULL DEFAULT '',
    faces VARCHAR(2048) NOT NULL DEFAULT '',
    skins VARCHAR(1024) NOT NULL DEFAULT '',
    dressface INT NOT NULL DEFAULT 0,
    dresshair INT NOT NULL DEFAULT 0,
    dressskin INT NOT NULL DEFAULT 0,
    dresson TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (charid)
);

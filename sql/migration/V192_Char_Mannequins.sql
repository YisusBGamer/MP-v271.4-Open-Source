-- Salon mannequins per character: type 0 hair, 1 face, 2 skin; slot 0-41; the client record is (byte flag,
-- int style, int extra).
CREATE TABLE IF NOT EXISTS char_mannequins (
    charid INT NOT NULL,
    type TINYINT NOT NULL,
    slot TINYINT NOT NULL,
    flag TINYINT NOT NULL DEFAULT 0,
    styleid INT NOT NULL DEFAULT 0,
    extra INT NOT NULL DEFAULT 0,
    PRIMARY KEY (charid, type, slot)
);

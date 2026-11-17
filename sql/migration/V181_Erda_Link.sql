-- Erda Link (Sia Astelle's 6th job): the stones a character has activated on its board and their levels.
-- Sol Erda is the HEXA Matrix's (quest record 1489). See ErdaLinkModule.
CREATE TABLE IF NOT EXISTS erda_link_stones (
    id BIGINT NOT NULL AUTO_INCREMENT,
    charid INT NOT NULL,
    stoneid INT NOT NULL,
    level INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY erda_link_stones_char_stone (charid, stoneid),
    CONSTRAINT erda_link_stones_ibfk_1 FOREIGN KEY (charid) REFERENCES characters (id) ON DELETE CASCADE
);

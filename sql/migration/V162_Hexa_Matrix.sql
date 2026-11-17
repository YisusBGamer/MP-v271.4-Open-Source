-- HEXA Matrix (6th job): the skill, mastery, boost and common cores a character has activated, and its HEXA Stat
-- nodes (one row per node: its three stat types and their levels). Sol Erda and Sol Erda energy are kept in quest
-- record 1489 like the client expects. See HexaModule.
CREATE TABLE IF NOT EXISTS hexa_skills (
    id BIGINT NOT NULL AUTO_INCREMENT,
    charid INT NOT NULL,
    coreid INT NOT NULL,
    level INT NOT NULL DEFAULT 0,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    disabled TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY hexa_skills_char_core (charid, coreid),
    CONSTRAINT hexa_skills_ibfk_1 FOREIGN KEY (charid) REFERENCES characters (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hexa_stats (
    id BIGINT NOT NULL AUTO_INCREMENT,
    charid INT NOT NULL,
    coreid INT NOT NULL,
    stat0 INT NOT NULL DEFAULT -1,
    level0 INT NOT NULL DEFAULT 0,
    stat1 INT NOT NULL DEFAULT -1,
    level1 INT NOT NULL DEFAULT 0,
    stat2 INT NOT NULL DEFAULT -1,
    level2 INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY hexa_stats_char_core (charid, coreid),
    CONSTRAINT hexa_stats_ibfk_1 FOREIGN KEY (charid) REFERENCES characters (id) ON DELETE CASCADE
);

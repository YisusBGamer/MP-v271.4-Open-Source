-- Legion (Union) Artifact crystals, shared by the account: crystal index 0-8, its level (1-5), its three stat skills
-- and when it expires. Artifact level, EXP, points and mission progress are in quest records
-- (503392 and friends) like the client expects. See UnionArtifactModule.
CREATE TABLE IF NOT EXISTS union_artifacts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    accid INT NOT NULL,
    idx INT NOT NULL,
    level INT NOT NULL DEFAULT 1,
    skill1 INT NOT NULL DEFAULT 0,
    skill2 INT NOT NULL DEFAULT 0,
    skill3 INT NOT NULL DEFAULT 0,
    expiration DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE KEY union_artifacts_acc_idx (accid, idx)
);

-- Swordie Plus has no voting or donating: drop the vote/donation point columns and the donations table.
-- (The server code no longer reads or writes any of these.)
ALTER TABLE users DROP COLUMN votepoints, DROP COLUMN donationpoints, DROP COLUMN nextPossibleVoteTime;
DROP TABLE IF EXISTS donations;

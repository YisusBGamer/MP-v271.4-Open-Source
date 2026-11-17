-- Removes the unused admin logins the database setup creates (Init_Admin_accounts.sql): admin1, asura and maigal.
-- A login is only removed when it has no characters at all, checked both by the login on each character and by
-- the logins per-world accounts. Its (empty) accounts are removed with it by the on delete cascade foreign key.
DELETE FROM users
WHERE name IN ('admin1', 'asura', 'maigal')
  AND NOT EXISTS (SELECT 1 FROM characters c WHERE c.userid = users.id)
  AND NOT EXISTS (SELECT 1 FROM accounts a INNER JOIN characters c ON c.accid = a.id WHERE a.userid = users.id);

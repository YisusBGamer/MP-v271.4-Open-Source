-- One Fame for every first boss clear and every rebirth, applied retroactively: first clears saved so far
-- (boss_first_clears: one row per character, boss and difficulty, since V109) and rebirths so far (server-only
-- quest record 89999952, "count=N;..."). BossFirstClearModule and SwordieCoinRewards.onRebirth award it from
-- now on.
UPDATE characterstats cs
SET cs.pop = COALESCE(cs.pop, 0)
    + (SELECT COUNT(*) FROM boss_first_clears f WHERE f.charid = cs.characterid)
    + COALESCE((SELECT CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(q.qrvalue, 'count=', -1), ';', 1) AS UNSIGNED)
        FROM quests q JOIN characters c ON c.questmanager = q.questmanager_id
        WHERE c.id = cs.characterid AND q.qrkey = 89999952 AND q.qrvalue LIKE 'count=%' LIMIT 1), 0);

-- Monster Park now allows 100 free clears per account per day (DailyEntry.MonsterPark). Give every account a fresh
-- count right away instead of after the next daily reset: -1 means "not set yet", which becomes 100 on first use.
UPDATE accountdailyentries SET monsterparkentries = -1;

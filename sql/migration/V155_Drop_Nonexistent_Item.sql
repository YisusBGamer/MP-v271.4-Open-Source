-- Water Thief Monster (2150000) listed "item" 2150001, which is a monster id (Dust Box), not an item: nothing dropped,
-- and every kill logged an error.
DELETE FROM mob_drops WHERE mobid = 2150000 AND itemid = 2150001;

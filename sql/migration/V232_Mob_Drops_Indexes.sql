-- Drop lookups go by mob and by item; without these each one read the whole table.
CREATE INDEX mob_drops_mobid ON mob_drops (mobid);
CREATE INDEX mob_drops_itemid ON mob_drops (itemid);

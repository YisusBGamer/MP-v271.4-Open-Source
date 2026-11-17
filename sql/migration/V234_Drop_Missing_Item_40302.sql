-- Item 40302 is not in the v271 item data; its single drop row logged an error on every kill.
DELETE FROM mob_drops WHERE itemid = 40302;

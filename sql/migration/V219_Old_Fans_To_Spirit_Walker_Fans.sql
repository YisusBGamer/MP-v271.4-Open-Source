-- The v271 client has no two-handed fan weapon type (1552xxx; Kanna uses Spirit Walker Fans 1254xxx since the
-- Sengoku revamp): turn every old fan into its Spirit Walker Fan, keeping its stats, stars and potential.
CREATE TABLE tmp_fan_map (old_id INT PRIMARY KEY, new_id INT NOT NULL);
INSERT INTO tmp_fan_map (old_id, new_id) VALUES
(1552000, 1254001),
(1552001, 1254002),
(1552002, 1254003),
(1552003, 1254004),
(1552004, 1254005),
(1552005, 1254006),
(1552006, 1254007),
(1552007, 1254046),
(1552008, 1254009),
(1552009, 1254010),
(1552010, 1254012),
(1552011, 1254014),
(1552012, 1254061),
(1552013, 1254062),
(1552014, 1254065),
(1552015, 1254021),
(1552016, 1254067),
(1552017, 1254003),
(1552018, 1254007),
(1552019, 1254008),
(1552020, 1254009),
(1552021, 1254003),
(1552022, 1254005),
(1552023, 1254031),
(1552024, 1254007),
(1552025, 1254008),
(1552026, 1254003),
(1552027, 1254006),
(1552028, 1254009),
(1552033, 1254063),
(1552034, 1254064),
(1552035, 1254066),
(1552036, 1254003),
(1552037, 1254006),
(1552038, 1254009),
(1552039, 1254068),
(1552040, 1254016),
(1552044, 1254015),
(1552045, 1254043),
(1552046, 1254012),
(1552047, 1254060),
(1552048, 1254001),
(1552049, 1254001),
(1552050, 1254002),
(1552051, 1254003),
(1552052, 1254004),
(1552053, 1254005),
(1552054, 1254006),
(1552055, 1254007),
(1552056, 1254012),
(1552057, 1254061),
(1552058, 1254046),
(1552059, 1254047),
(1552060, 1254069),
(1552061, 1254048),
(1552063, 1254023),
(1552064, 1254008),
(1552065, 1254049),
(1552068, 1254042),
(1552069, 1254052),
(1552070, 1254053),
(1552071, 1254070),
(1552072, 1254040),
(1552073, 1254039),
(1552074, 1254054),
(1552075, 1254022),
(1552076, 1254080),
(1552077, 1254075),
(1552078, 1254070),
(1552079, 1254043),
(1552080, 1254012),
(1552081, 1254068),
(1552083, 1254052),
(1552084, 1254072),
(1552099, 1254013),
(1552100, 1254017),
(1552101, 1254018),
(1552102, 1254020),
(1552103, 1254019),
(1552109, 1254055),
(1552110, 1254025),
(1552111, 1254003),
(1552112, 1254006),
(1552113, 1254010),
(1552114, 1254068),
(1552116, 1254011),
(1552118, 1254056),
(1552119, 1254026),
(1552120, 1254025),
(1552123, 1254003),
(1552124, 1254006),
(1552125, 1254059),
(1552127, 1254023),
(1552128, 1254025),
(1552129, 1254027),
(1552130, 1254028),
(1552132, 1254026),
(1552134, 1254026),
(1552139, 1254044),
(1552141, 1254001),
(1552142, 1254005),
(1552143, 1254014),
(1552144, 1254019),
(1552151, 1254024),
(1552152, 1254029),
(1552153, 1254079);

UPDATE items i JOIN tmp_fan_map m ON i.itemid = m.old_id SET i.itemid = m.new_id;

UPDATE avatarlook a JOIN tmp_fan_map m ON a.weaponid = m.old_id SET a.weaponid = m.new_id;
UPDATE avatarlook a JOIN tmp_fan_map m ON a.weaponstickerid = m.old_id SET a.weaponstickerid = m.new_id;
UPDATE avatarlook a JOIN tmp_fan_map m ON FIND_IN_SET(m.old_id, a.hairequips) > 0
    SET a.hairequips = TRIM(BOTH ',' FROM REPLACE(CONCAT(',', a.hairequips, ','), CONCAT(',', m.old_id, ','), CONCAT(',', m.new_id, ',')));
UPDATE avatarlook a JOIN tmp_fan_map m ON FIND_IN_SET(m.old_id, a.unseenequips) > 0
    SET a.unseenequips = TRIM(BOTH ',' FROM REPLACE(CONCAT(',', a.unseenequips, ','), CONCAT(',', m.old_id, ','), CONCAT(',', m.new_id, ',')));

-- drop and shop rows: keep the existing Spirit Walker row where the mob or shop already has one
DELETE d FROM mob_drops d JOIN tmp_fan_map m ON d.itemid = m.old_id
    JOIN (SELECT mobid, itemid FROM mob_drops) e ON e.mobid = d.mobid AND e.itemid = m.new_id;
DELETE d FROM mob_drops d JOIN tmp_fan_map m ON d.itemid = m.old_id
    JOIN (SELECT id, mobid, itemid FROM mob_drops) e ON e.mobid = d.mobid AND e.id < d.id
    JOIN tmp_fan_map m2 ON e.itemid = m2.old_id AND m2.new_id = m.new_id;
UPDATE mob_drops d JOIN tmp_fan_map m ON d.itemid = m.old_id SET d.itemid = m.new_id;

DELETE s FROM shopitems s JOIN tmp_fan_map m ON s.itemid = m.old_id
    JOIN (SELECT shopid, itemid FROM shopitems) e ON e.shopid = s.shopid AND e.itemid = m.new_id;
DELETE s FROM shopitems s JOIN tmp_fan_map m ON s.itemid = m.old_id
    JOIN (SELECT id, shopid, itemid FROM shopitems) e ON e.shopid = s.shopid AND e.id < s.id
    JOIN tmp_fan_map m2 ON e.itemid = m2.old_id AND m2.new_id = m.new_id;
UPDATE shopitems s JOIN tmp_fan_map m ON s.itemid = m.old_id SET s.itemid = m.new_id;

DELETE d FROM equip_drops d JOIN tmp_fan_map m ON d.id = m.old_id
    JOIN (SELECT id, job FROM equip_drops) e ON e.job = d.job AND e.id = m.new_id;
UPDATE equip_drops d JOIN tmp_fan_map m ON d.id = m.old_id SET d.id = m.new_id;

DROP TABLE tmp_fan_map;

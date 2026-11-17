-- Undoes V129. The Explorer 4th job tokens (Heroic Pentagon/Star) already had a way: Harmonia and the other 4th job
-- instructors send 3rd job characters to the test maps 924000200/924000201, where Manon and Griffey, Heralds of
-- Heroes (9001043/9001044), drop them; the Leafre portal to Griffey's forest does the same. V129's search for those
-- monsters missed the Map9 maps. As on the official servers, the real Manon and Griffey don't drop them.
DELETE FROM mob_drops WHERE mobid = 8180000 AND itemid IN (4031343, 4031511);
DELETE FROM mob_drops WHERE mobid = 8180001 AND itemid IN (4031344, 4031512);

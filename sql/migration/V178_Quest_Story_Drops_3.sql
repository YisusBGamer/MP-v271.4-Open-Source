-- Quest items the v265 walk found nothing giving (quest audit 2026-10-24):
--   38876 Deal with the Shadowdealers 3: Specter Ambassador Scythe (4000736) from the Reaper Specter (2400115, Heliseum)
--   38130 How to Brew the Special Drink: Spare Memory Chip (4037171) from the Puxillian Scrounger (8645203, Hotel Arcus)
--   36729 Getting Ingredients: Sleepy Grobbler Stalk (4000704) from the Sleepy Grobbler (2400000, Pantheon)
--   3248 Operation Strategy 1: Timer's Egg (4031991) from Timer (5220003, Ludibrium Clocktower)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400115, 4000736, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400115 AND itemid = 4000736);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645203, 4037171, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645203 AND itemid = 4037171);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400000, 4000704, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400000 AND itemid = 4000704);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5220003, 4031991, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5220003 AND itemid = 4031991);
--   36469 The Black Wings and the Puppeteer (Cygnus) / 36913 Check Out the Orange Mushroom (Aran): the puppets from the
--   Cynical Orange Mushroom (2300102, Suspicious Hill 100010001)
--   23187 An Attempt to Escape (Verne Mine): Broken Guard Robot Baton (4037321) from the Guard Robot (6150000)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2300102, 4036879, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300102 AND itemid = 4036879);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2300102, 4037293, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300102 AND itemid = 4037293);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 6150000, 4037321, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6150000 AND itemid = 4037321);
--   36947 Find the Key (Aran): v265 asks for 5 Wooden Keys 4037302 (the old 4032326 still drops too) from the Primitive
--   Thief Boar (9001024, Dangerous Rocky Mountain 930010001)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9001024, 4037302, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001024 AND itemid = 4037302);
--   38323 Dad's Too Busy 2 (Odium): Angler Robot Energy Source (4036894) from the Angler Bomb, Melee and Electro Testers
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645293, 4036894, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645293 AND itemid = 4036894);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645294, 4036894, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645294 AND itemid = 4036894);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8645295, 4036894, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8645295 AND itemid = 4036894);
--   38821 [Heliseum] Popo's Tears: Popo's Jewel (4037237) from the Yellow Speeyor (2400102)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400102, 4037237, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4037237);
--   37853 [Esfera] In Search of the Signal Flare: v265 asks for Flare 4037358 (Ahtuin 8644500 dropped the old 4036449)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8644500, 4037358, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644500 AND itemid = 4037358);

-- v265 renumbered these quest items; the monsters that drop the old item (same name) now drop the one the quest asks for
--   1567 [Lion King's Castle] Key to the Rose Garden: Key to the Rose Garden (4037314)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8210004, 4037314, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8210004 AND itemid = 4037314);
--   2617 Helping Don Giovanni with His Problem: Toy Plane (4033179)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8145006, 4033179, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8145006 AND itemid = 4033179);
--   3272 Spiruna's Black Crystal: Cracked Black Crystal (4037251)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5120001, 4037251, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120001 AND itemid = 4037251);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5120002, 4037251, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120002 AND itemid = 4037251);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 5120003, 4037251, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120003 AND itemid = 4037251);
--   3815 Finding the Ingredients for Tae Sang: Ginseng Concentrate (2022131)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8230028, 2022131, 5000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230028 AND itemid = 2022131);
--   8142 Todd's How-to-Hunt: Toy Drum (4000301)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3230400, 4000301, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230400 AND itemid = 4000301);
--   20974 [Silent Crusade] Her Request: Wild Kargo Eye (4037311)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600226, 4037311, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600226 AND itemid = 4037311);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 6230100, 4037311, 1000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6230100 AND itemid = 4037311);
--   20994 [Verne Mine] Gabrielle's Request 1: Guard Robot L's Chip (4037286)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 7150004, 4037286, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7150004 AND itemid = 4037286);
--   31700 [Daily] Supply Delivery - Yellow Spee Hide: Yellow Speeyor Fur Coat (4033556)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400102, 4033556, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4033556);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400105, 4033556, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033556);
--   31701 [Daily] Supply Delivery - Dinor's Tenderloin: Dinoram Tenderloin (4033557)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400104, 4033557, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4033557);
--   31702 [Daily] Supply Delivery - Animal Oil: Animal Oil (4033558)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400101, 4033558, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4033558);
--   31703 [Daily] Supply Delivery - Sharp Fang: Dinodon Sharp Fang (4033559)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400102, 4033559, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4033559);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400105, 4033559, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033559);
--   31716 [Daily] Merchant Crew - Specter Candy: Specter Candy (4033565)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400106, 4033565, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4033565);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400107, 4033565, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033565);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400110, 4033565, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033565);
--   31717 [Daily] Merchant Crew - Tamer's Whip: Tamer's Whip (4033566)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400106, 4033566, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4033566);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400107, 4033566, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033566);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400110, 4033566, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033566);
--   31722 [Daily] Merchant Crew - Specter General Emblem: Specter Commander Badge (4033571)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400109, 4033571, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4033571);
--   32478 [Weekly Quest] The Complete Set: Frozen Energy (4033931)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600900, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600900 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600901, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600901 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600902, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600902 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600903, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600903 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600904, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600904 AND itemid = 4033931);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600905, 4033931, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600905 AND itemid = 4033931);
--   33234 Find the Pipe Piece: Pipe Piece (4034271)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8250013, 4034271, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8250013 AND itemid = 4034271);
--   33237 Find the Pipe Piece: Pipe Piece (4034278)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 8250013, 4034278, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8250013 AND itemid = 4034278);
--   36463 Who Is Baroq? Part 2: The Lost Treasure (4036863)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9001009, 4036863, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9001009 AND itemid = 4036863);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9300743, 4036863, 10000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300743 AND itemid = 4036863);
--   36470 [Cygnus Knights] Checking on Henesys: Berserk Horny Mushroom Puppet (4036880)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1210102, 4036880, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210102 AND itemid = 4036880);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2110200, 4036880, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2110200 AND itemid = 4036880);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 9300736, 4036880, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300736 AND itemid = 4036880);
--   36498 [Cygnus Knights] Gathering Intel: Working Rue Battery (4036881)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3150000, 4036881, 1000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3150000 AND itemid = 4036881);
--   36620 The Keepsake: Empress Aria's Keepsake (4036877)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1140100, 4036877, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4036877);
--   36752 Enemy Mastermind: Magnus's Letter (4037236)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400009, 4037236, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400009 AND itemid = 4037236);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400012, 4037236, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400012 AND itemid = 4037236);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2400114, 4037236, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400114 AND itemid = 4037236);
--   36914 When Mushrooms Cry: Crying Blue Mushroom Doll (4037294)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2220110, 4037294, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2220110 AND itemid = 4037294);
--   36921 Ghost Stumps: Smirking Ghost Stump Puppet (4037295)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1140100, 4037295, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4037295);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 1140130, 4037295, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140130 AND itemid = 4037295);
--   37163 [Elodin] Hay Necessities: Hay (4036501)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 2600200, 4036501, 2000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600200 AND itemid = 4036501);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3501103, 4036501, 3000, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3501103 AND itemid = 4036501);
--   41102 Reminiscing : Coconut (4000968)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant)
SELECT 3401001, 4000968, 7500, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401001 AND itemid = 4000968);

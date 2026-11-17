-- Zero's story and Mirror World side-quest items that no monster dropped (same format as V95-V98).
-- 40057 The Saddest Dragon 2 - music box parts
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600010, 4033798, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600010 AND itemid = 4033798);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600011, 4033798, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600011 AND itemid = 4033798);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600012, 4033798, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600012 AND itemid = 4033798);
-- 40057
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600008, 4033799, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600008 AND itemid = 4033799);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600009, 4033799, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600009 AND itemid = 4033799);
-- 40060 Breaking the Curse
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600013, 4033805, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600013 AND itemid = 4033805);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600014, 4033805, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600014 AND itemid = 4033805);
-- 40060
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600018, 4033806, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600018 AND itemid = 4033806);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600019, 4033806, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600019 AND itemid = 4033806);
-- 40101 Tears of Blue - the gift for the queen
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600104, 4033811, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600104 AND itemid = 4033811);
-- 40103 The Three Sisters - the key (Thief Crow, boss of Outside East Entrance of Ariant)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600111, 4033812, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600111 AND itemid = 4033812);
-- 40109 Respite - the contest scorpions
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300761, 4033816, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300761 AND itemid = 4033816);
-- 40200 Maya's Medicine
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600206, 4033820, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600206 AND itemid = 4033820);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600207, 4033820, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600207 AND itemid = 4033820);
-- 40206 Legacy of Larelle 3
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600220, 4033821, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600220 AND itemid = 4033821);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600221, 4033821, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600221 AND itemid = 4033821);
-- 40301 Path of the Sage 2 - the training dolls
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300766, 4033832, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300766 AND itemid = 4033832);
-- 40309 Wu Gong and Tae Soo 1
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600318, 4033922, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600318 AND itemid = 4033922);
-- 40404 Sensing Danger
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600416, 4033858, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600416 AND itemid = 4033858);
-- 40407 Surl Knows All - the report (Shadow Knights' camp)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300792, 4033859, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300792 AND itemid = 4033859);
-- 40408 Mind Eraser, No Chaser (Poison Gas Generator, boss of Mine Security Area)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600425, 4033860, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600425 AND itemid = 4033860);
-- 40601 Tower of the Past - the town's time flow (Time Cubes)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300795, 4033911, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300795 AND itemid = 4033911);
-- 40701 Memory Lane - Shard of Recollection (Dodo, boss of Memory Lane)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600705, 2431836, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600705 AND itemid = 2431836);
-- 40702 Road of Regrets - Shard of Regret (Lilynouch)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600710, 2431837, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600710 AND itemid = 2431837);
-- 40703 Road to Oblivion - Shard of Oblivion (Lyka)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600715, 2431838, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600715 AND itemid = 2431838);
-- 41004 Sly's Research 2
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600023, 4033795, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600023 AND itemid = 4033795);
-- 41004
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600024, 4033796, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600024 AND itemid = 4033796);
-- 41004
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600025, 4033797, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600025 AND itemid = 4033797);
-- 41005 Sly's Research 1
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600026, 4033803, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600026 AND itemid = 4033803);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600027, 4033803, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600027 AND itemid = 4033803);
-- 41006 Sly's Research 3 (Leviathan, boss of Wyvern Gorge)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600030, 4033793, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600030 AND itemid = 4033793);
-- 41008 Oh, the Aches
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600010, 4033794, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600010 AND itemid = 4033794);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600011, 4033794, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600011 AND itemid = 4033794);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600012, 4033794, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600012 AND itemid = 4033794);
-- 41011 Making Peace - the Hoblin King's signature
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600006, 4033790, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600006 AND itemid = 4033790);
-- 41012 Feeding a Growing Dragon
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600016, 4033787, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600016 AND itemid = 4033787);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600017, 4033787, 4000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600017 AND itemid = 4033787);
-- 41012 (Manon, boss of Twilight Forest)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600022, 4033792, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600022 AND itemid = 4033792);
-- 41056 Schegerazade's Second Riddle
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600113, 4033907, 1000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600113 AND itemid = 4033907);
-- 41059 Aldin Grows Brave
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600106, 4033906, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600106 AND itemid = 4033906);
-- 41106 Fresh Milk - hay
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600200, 4033819, 2000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600200 AND itemid = 4033819);
-- 41113 A Boy's Romance (Jr. Balrog, boss of Under the Sleeping Forest)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600231, 4033786, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600231 AND itemid = 4033786);
-- 41153 Lilishu, the Good Daughter (Gigantic Bellflower Root, boss of Herb Garden)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600321, 4033908, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600321 AND itemid = 4033908);
-- 41159 Kid Sibling Wanted
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600315, 4033822, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600315 AND itemid = 4033822);
-- 41166 Your Daughter's Hand (Lord Pirate, boss of Pirate Hideout)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600326, 4033791, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600326 AND itemid = 4033791);
-- 41213 Non-moving Taxi
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600418, 4033836, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600418 AND itemid = 4033836);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600411, 4033836, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600411 AND itemid = 4033836);
-- 41254 Gathering the Research Material - diary pages
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033841, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033841);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033842, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033842);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033843, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033843);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033844, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033844);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033845, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033845);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033846, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033846);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033847, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033847);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4033848, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4033848);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033841, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033841);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033842, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033842);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033843, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033843);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033844, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033844);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033845, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033845);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033846, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033846);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033847, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033847);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600506, 4033848, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600506 AND itemid = 4033848);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033841, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033841);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033842, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033842);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033843, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033843);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033844, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033844);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033845, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033845);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033846, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033846);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033847, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033847);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4033848, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4033848);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033841, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033841);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033842, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033842);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033843, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033843);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033844, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033844);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033845, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033845);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033846, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033846);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033847, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033847);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600508, 4033848, 800, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600508 AND itemid = 4033848);
-- 41258 Keep this a Secret (Security Camera, boss of Alcadno Laboratory A)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600509, 4033850, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600509 AND itemid = 4033850);
-- 41262 Constructing Neo Huroid 2
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600516, 4033909, 1500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600516 AND itemid = 4033909);
-- 41268 New Heart (Deet and Roi, boss of Alcadno Laboratory B)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600518, 4033910, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600518 AND itemid = 4033910);
-- 41302 Wrapping up the Panda Teddy
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600602, 4033865, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600602 AND itemid = 4033865);
-- 41307 In Search of Lost Time (Timer, boss of Clocktower Interior)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600613, 4033862, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600613 AND itemid = 4033862);
-- 41308 The Girl who lost her Innocence
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600608, 4033863, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600608 AND itemid = 4033863);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600609, 4033863, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600609 AND itemid = 4033863);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600610, 4033863, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600610 AND itemid = 4033863);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600611, 4033863, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600611 AND itemid = 4033863);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600612, 4033863, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600612 AND itemid = 4033863);
-- 41313 Final Choice (Papulatus, boss of Clocktower Bottom Floor)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600631, 4033864, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600631 AND itemid = 4033864);
-- 41356 Selling Memories
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600700, 4033872, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600700 AND itemid = 4033872);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600701, 4033872, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600701 AND itemid = 4033872);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600702, 4033872, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600702 AND itemid = 4033872);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600703, 4033872, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600703 AND itemid = 4033872);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600704, 4033872, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600704 AND itemid = 4033872);
-- 41362 Selling Emotions
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600706, 4033874, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600706 AND itemid = 4033874);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600707, 4033874, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600707 AND itemid = 4033874);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600708, 4033874, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600708 AND itemid = 4033874);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600709, 4033874, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600709 AND itemid = 4033874);
-- 41368 Selling Oblivion
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600711, 4033876, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600711 AND itemid = 4033876);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600712, 4033876, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600712 AND itemid = 4033876);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600713, 4033876, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600713 AND itemid = 4033876);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600714, 4033876, 5000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600714 AND itemid = 4033876);

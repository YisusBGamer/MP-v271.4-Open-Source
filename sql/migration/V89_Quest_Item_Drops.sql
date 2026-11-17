-- Quest items that no monster dropped, so their quests couldn't be finished. The mobs come from each quest's
-- own text in Quest.wz (QuestInfo.img), e.g. [Reverse City] The Rat Snack: Erda Rats drop Blue Rat Meat.
-- V84 tried to add the Reverse City ones, but a comment after each ';' made the script runner skip them all.
-- Quest items only show up for characters that have the quest. Each insert is skipped if the drop already exists.
-- (Comments stay on their own lines: the script runner only ends a statement at a line ending in ';'.)
-- Greatest Oldies Album | Blake's New Album (34402 [Kerning Tower] Kerning Square's Superstar)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310000, 4036027, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310000 AND itemid = 4036027);
-- Latest Hits Album | Blake's New Album (34402 [Kerning Tower] Kerning Square's Superstar)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310001, 4036027, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310001 AND itemid = 4036027);
-- Next-gen Speaker | Audio Cable (34406 [Kerning Tower] In Need of Speaker Cables)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310002, 4036019, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310002 AND itemid = 4036019);
-- Blue Raspberry Jelly Juice | Fruit Jelly Container (34408 [Kerning Tower] Fixings for a Drink)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310004, 4036020, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310004 AND itemid = 4036020);
-- Grape Jelly Juice | Fruit Jelly Container (34408 [Kerning Tower] Fixings for a Drink)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310005, 4036020, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310005 AND itemid = 4036020);
-- Enraged Espresso Machine | Fruit Jelly Container (34408 [Kerning Tower] Fixings for a Drink)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310006, 4036020, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310006 AND itemid = 4036020);
-- Green Notebook | Note Paper (34411 [Kerning Tower] Scattered Notes)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310007, 4036021, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310007 AND itemid = 4036021);
-- Blue Notebook | Note Paper (34411 [Kerning Tower] Scattered Notes)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310008, 4036021, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310008 AND itemid = 4036021);
-- Yellow Headphones | Amy's Recorder (34414 [Kerning Tower] The Lost Recorder)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310009, 4036023, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310009 AND itemid = 4036023);
-- Red Headphones | Amy's Recorder (34414 [Kerning Tower] The Lost Recorder)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310010, 4036023, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310010 AND itemid = 4036023);
-- Killer Cosmetics | Super Expensive Makeup Kit (34421 [Kerning Tower] Hiring a Makeup Artist)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310011, 4036024, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310011 AND itemid = 4036024);
-- Deadly Dressing Table | Super Expensive Makeup Kit (34421 [Kerning Tower] Hiring a Makeup Artist)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310012, 4036024, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310012 AND itemid = 4036024);
-- Harried Hair Roller | High-End Perm Agent (34422 [Kerning Tower] Hiring a Hairstylist)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310013, 4036025, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310013 AND itemid = 4036025);
-- Peeved Perm Machine | High-End Perm Agent (34422 [Kerning Tower] Hiring a Hairstylist)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310014, 4036025, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310014 AND itemid = 4036025);
-- Hedgehog | Cast Iron Plate (34605 Intro to Hideout Repair)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400300, 4036140, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400300 AND itemid = 4036140);
-- Mutant Hedgehog | Big Nail (34606 Intermediate Hideout Repair)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400301, 4036141, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400301 AND itemid = 4036141);
-- Mutant Hedgehog | Mutant Hedgehog Fur (34607 Advanced Hideout Repair)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400301, 4036142, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400301 AND itemid = 4036142);
-- Nutria | Nutria Hind Leg (34609 Bringing Home the Bacon)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400302, 4036143, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400302 AND itemid = 4036143);
-- Ooze Waste | Still Fluid (34617 Cracking the Safe (1))
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400304, 4036144, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400304 AND itemid = 4036144);
-- Flame Waste | Unburning Flame (34618 Cracking the Safe (2))
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400305, 4036145, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400305 AND itemid = 4036145);
-- Scrap Iron Waste | Soft Scrap Iron (34621 That Kid Is Getting on My Nerves)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400306, 4036146, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400306 AND itemid = 4036146);
-- Specter Stray Dog | Wiggling Tail (34626 Investigate the Brawl 'n' Bash Club 1)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400307, 4036147, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400307 AND itemid = 4036147);
-- Mutant Nutria | Mutant Nutria Blood (34634 Mr. Hazard's Objective)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400308, 4036151, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400308 AND itemid = 4036151);
-- Hazard's Mean Crony | Poison Remover (34645 More Dirt on Hazard)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400312, 4036152, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400312 AND itemid = 4036152);
-- Onyx Stonegar | Onyx Stonegar Magic Stone (34652 Beldar's Grudging Approval)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400013, 4036153, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400013 AND itemid = 4036153);
-- Flutterbuzz | Flutterbuzz Powder (34745 [Fox Valley] Operation Powder Puff)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400251, 4036315, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400251 AND itemid = 4036315);
-- Pom Puff Bush | Pom Puff Leaf (34746 [Fox Valley] Master Crafter Maru)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400252, 4036316, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400252 AND itemid = 4036316);
-- Nettled Squirrel | Nettled Squirrel Tree Nut (34753 [Fox Valley] Magical Mulch Ingredients)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400254, 4036317, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400254 AND itemid = 4036317);
-- Vicious Squirrel | Vicious Squirrel Tree Nut (34755 [Fox Valley] Extra Magical Mulch Ingredients)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400255, 4036319, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400255 AND itemid = 4036319);
-- Green Grossular | Grossular Shard (34802 Collect Grossular)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400400, 4036163, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400400 AND itemid = 4036163);
-- Purple Amethyst | Amethyst Shard (34805 Making a Crystal Gate 1)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400401, 4036164, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400401 AND itemid = 4036164);
-- Tainted Practice Robot | Dean's Bag (34807 Dean's Lost Stuff)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400409, 4036165, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400409 AND itemid = 4036165);
-- Tainted Practice Robot | Dean's Notebook (34807 Dean's Lost Stuff)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400409, 4036166, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400409 AND itemid = 4036166);
-- Fire Pyrope | Pyrope Shard (34808 A Divine Festival 1)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400402, 4036167, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400402 AND itemid = 4036167);
-- Dark Obsidian | Obsidian Shard (34811 Making a Crystal Gate 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400403, 4036168, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400403 AND itemid = 4036168);
-- Shining Gold | Gold Shard (34814 A Divine Festival 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400404, 4036169, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400404 AND itemid = 4036169);
-- Shining Vinegold | Vinegold Shard (34815 A Divine Festival 3)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400405, 4036170, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400405 AND itemid = 4036170);
-- Gravi Stonegar | Gravi Stonegar Magic Stone (34822 Interdimensional Portal, Activate)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400014, 4036172, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400014 AND itemid = 4036172);
-- Scrap Iron Waste | Soft Scrap Iron (34941 Separate Ways 1)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400306, 4036350, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400306 AND itemid = 4036350);
-- Mutant Nutria | Wiggling Tail (34942 Separate Ways 2)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400308, 4036351, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400308 AND itemid = 4036351);
-- Soot Beast | Wicked Soul Remnants (35612 ?)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644622, 4036446, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644622 AND itemid = 4036446);
-- Soot Talon | Wicked Soul Remnants (35612 ?)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644623, 4036446, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644623 AND itemid = 4036446);
-- Crushing Glare | Void Fragment (35624 ?)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644626, 4036456, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644626 AND itemid = 4036456);
-- Burst Glare | Void Fragment (35624 ?)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644627, 4036456, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644627 AND itemid = 4036456);
-- Entangled Fragment | Decent Flint (35705 [Labyrinth of Suffering] Signal Flare Necessities)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644700, 4036452, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644700 AND itemid = 4036452);
-- Faith Fragment | Decent Flint (35705 [Labyrinth of Suffering] Signal Flare Necessities)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644701, 4036452, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644701 AND itemid = 4036452);
-- Entangled Fragment | Decent Tree Branch (35705 [Labyrinth of Suffering] Signal Flare Necessities)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644700, 4036453, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644700 AND itemid = 4036453);
-- Faith Fragment | Decent Tree Branch (35705 [Labyrinth of Suffering] Signal Flare Necessities)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644701, 4036453, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644701 AND itemid = 4036453);
-- Despairing Wing | Burning Black Soul (35717 [Labyrinth of Suffering] Traces of Despair)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644704, 4036454, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644704 AND itemid = 4036454);
-- Despairing Blade | Burning Black Soul (35717 [Labyrinth of Suffering] Traces of Despair)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644705, 4036454, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644705 AND itemid = 4036454);
-- Log-a-Rhythmic Bugs | Extra Dry Firewood (35920 Compass Fragment 3)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300205, 4036529, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300205 AND itemid = 4036529);
-- Sparkinstone Bugs | Extra Dry Firewood (35920 Compass Fragment 3)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300206, 4036529, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300206 AND itemid = 4036529);
-- Log-a-Rhythmic Bugs | Smoky Flint (35920 Compass Fragment 3)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300205, 4036530, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300205 AND itemid = 4036530);
-- Sparkinstone Bugs | Smoky Flint (35920 Compass Fragment 3)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300206, 4036530, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300206 AND itemid = 4036530);
-- Ruins Sentinel | Glowpod (35926 Glowpod)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300208, 4036531, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300208 AND itemid = 4036531);
-- Frenzied Ruins Sentinel | Sentsuma (35972 [Partem Ruins] Faded Lettering)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300266, 4036533, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300266 AND itemid = 4036533);
-- Searing Ruins Guardian | Guardian's Soul Flame (35975 [Partem Ruins] Brazier Blaze)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2300267, 4036534, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2300267 AND itemid = 4036534);
-- Erda Rat | Blue Rat Meat (37604 [Reverse City] The Rat Snack)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8641051, 4036631, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641051 AND itemid = 4036631);
-- Monto | Purple Rat Meat (37606 [Reverse City] From a Strange World)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8641052, 4036632, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641052 AND itemid = 4036632);
-- Seeker T-Drone Model B | T-Boy's Research Notes (37610 [Reverse City] The Find)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8641054, 4036633, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641054 AND itemid = 4036633);
-- Combat T-Drone Model A | Drone Part (37612 [Reverse City] Surviving in the Subway)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8641055, 4036634, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641055 AND itemid = 4036634);
-- Combat T-Drone Model A | Drone Part (37615 [Reverse City] Just Parts)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8641055, 4036635, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8641055 AND itemid = 4036635);
-- Thralled Guard | Memory Glass (34240 [Morass] The Path Tana Left)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644431, 4036338, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644431 AND itemid = 4036338);
-- Thralled Warhammer Knight | Memory Glass (34240 [Morass] The Path Tana Left)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644432, 4036338, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644432 AND itemid = 4036338);
-- Thralled Wizard | Memory Glass (34240 [Morass] The Path Tana Left)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644433, 4036338, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644433 AND itemid = 4036338);
-- Thralled Archer | Memory Glass (34240 [Morass] The Path Tana Left)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8644434, 4036338, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8644434 AND itemid = 4036338);
-- Paper Bag Alley Citizen | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643001, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643001 AND itemid = 4036572);
-- Wood Board Alley Citizen | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643002, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643002 AND itemid = 4036572);
-- Gallina | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643003, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643003 AND itemid = 4036572);
-- Gallus | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643004, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643004 AND itemid = 4036572);
-- Angry Victory Plate | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643005, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643005 AND itemid = 4036572);
-- Crooked Victory Plate | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643006, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643006 AND itemid = 4036572);
-- Red Dancing Shoes | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643007, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643007 AND itemid = 4036572);
-- Angry Masquerade Citizen | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643008, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643008 AND itemid = 4036572);
-- Insane Masquerade Citizen | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643009, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643009 AND itemid = 4036572);
-- Blue-eyed Gargoyle | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643010, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643010 AND itemid = 4036572);
-- Red-eyed Gargoyle | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643011, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643011 AND itemid = 4036572);
-- Red Dancing Shoes | Sleep Powder (34394 [Daily Quest] Obtain 50 Sleep Powders)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8643015, 4036572, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8643015 AND itemid = 4036572);

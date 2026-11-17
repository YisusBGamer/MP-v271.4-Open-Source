-- Monster drops (mobid -1 drops from every monster) and the equips each monster can drop.

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (-1, 4001832, 500, 5, 40), # Global | Spell Trace
    (8230009, 4009155, 7500, 1, 1), # Peace Spirit | Spirit Substance
    (8230009, 4021033, 100, 1, 1), # Peace Spirit | Basic Spell Essence
    (8230009, 4021034, 100, 1, 1), # Peace Spirit | Fine Spell Essence
    (8230009, 2001528, 100, 1, 1), # Peace Spirit | Melting Cheese
    (8230009, 2434021, 100, 1, 1), # Peace Spirit | Medal of Honor
    (8230009, 2000037, 100, 1, 1), # Peace Spirit | Sunrise Dew
    (8230009, 4021031, 50, 1, 1), # Peace Spirit | Twisted Time
    (8230009, 2590004, 100, 1, 1), # Peace Spirit | Prototype Soul Enchanter
    (8230009, 4021032, 7500, 1, 1), # Peace Spirit | Mana Crystal
    (8230009, 4021035, 100, 1, 1), # Peace Spirit | Grand Spell Essence
    (8230008, 4021031, 50, 1, 1), # Glowing Primary Rock | Twisted Time
    (8230008, 4021035, 100, 1, 1), # Glowing Primary Rock | Grand Spell Essence
    (8230008, 4021032, 7500, 1, 1), # Glowing Primary Rock | Mana Crystal
    (8230008, 4021034, 100, 1, 1), # Glowing Primary Rock | Fine Spell Essence
    (8230008, 4009154, 7500, 1, 1), # Glowing Primary Rock | Glowing Primary Rock Fragment
    (8230008, 4021033, 100, 1, 1), # Glowing Primary Rock | Basic Spell Essence
    (8230005, 4033978, 7500, 1, 1), # Hidden Grave Robber | Story Fragment
    (8230005, 4021033, 100, 1, 1), # Hidden Grave Robber | Basic Spell Essence
    (8230005, 4021034, 100, 1, 1), # Hidden Grave Robber | Fine Spell Essence
    (8230005, 4009151, 7500, 1, 1), # Hidden Grave Robber | Grave Robber Certificate
    (8230005, 4021032, 7500, 1, 1), # Hidden Grave Robber | Mana Crystal
    (8230005, 4021035, 100, 1, 1), # Hidden Grave Robber | Grand Spell Essence
    (8230004, 4021031, 50, 1, 1), # Poacher's Hawk | Twisted Time
    (8230004, 4021035, 100, 1, 1), # Poacher's Hawk | Grand Spell Essence
    (8230004, 4021033, 100, 1, 1), # Poacher's Hawk | Basic Spell Essence
    (8230004, 4009150, 7500, 1, 1), # Poacher's Hawk | Hawk Claw
    (8230004, 4021032, 7500, 1, 1), # Poacher's Hawk | Mana Crystal
    (8230004, 4021034, 100, 1, 1), # Poacher's Hawk | Fine Spell Essence
    (8230004, 4033978, 7500, 1, 1), # Poacher's Hawk | Story Fragment
    (8230007, 4009153, 7500, 1, 1), # Neon Bat | Neon Bat Wings
    (8230007, 2590004, 100, 1, 1), # Neon Bat | Prototype Soul Enchanter
    (8230007, 4021032, 7500, 1, 1), # Neon Bat | Mana Crystal
    (8230007, 4021033, 100, 1, 1), # Neon Bat | Basic Spell Essence
    (8230007, 2000037, 100, 1, 1), # Neon Bat | Sunrise Dew
    (8230007, 2001528, 100, 1, 1), # Neon Bat | Melting Cheese
    (8230007, 2434021, 100, 1, 1), # Neon Bat | Medal of Honor
    (8230007, 4021031, 50, 1, 1), # Neon Bat | Twisted Time
    (8230007, 4021035, 100, 1, 1), # Neon Bat | Grand Spell Essence
    (8230007, 4021034, 100, 1, 1), # Neon Bat | Fine Spell Essence
    (6230100, 4000032, 7500, 1, 1), # Wild Kargo | Ligator Skin
    (8230006, 4021032, 7500, 1, 1), # Grave Robber's Hunting Dog | Mana Crystal
    (8230006, 4021034, 100, 1, 1), # Grave Robber's Hunting Dog | Fine Spell Essence
    (8230006, 4021031, 50, 1, 1), # Grave Robber's Hunting Dog | Twisted Time
    (8230006, 4021035, 100, 1, 1), # Grave Robber's Hunting Dog | Grand Spell Essence
    (8230006, 4009152, 7500, 1, 1), # Grave Robber's Hunting Dog | Hunting Dog's Fang
    (8230006, 4021033, 100, 1, 1), # Grave Robber's Hunting Dog | Basic Spell Essence
    (8230006, 4033978, 7500, 1, 1), # Grave Robber's Hunting Dog | Story Fragment
    (8145008, 2431174, 100, 1, 1), # Bearic the White | Medal of Honor
    (8145008, 2000037, 100, 1, 1), # Bearic the White | Sunrise Dew
    (8145008, 4021031, 50, 1, 1), # Bearic the White | Twisted Time
    (8145008, 2590004, 100, 1, 1), # Bearic the White | Prototype Soul Enchanter
    (8145008, 4021032, 7500, 1, 1), # Bearic the White | Mana Crystal
    (8145008, 4021035, 100, 1, 1), # Bearic the White | Grand Spell Essence
    (8145008, 4000686, 7500, 1, 1), # Bearic the White | Viking Helmet
    (8145008, 4021033, 100, 1, 1), # Bearic the White | Basic Spell Essence
    (8145008, 4021034, 100, 1, 1), # Bearic the White | Fine Spell Essence
    (8145008, 2001528, 100, 1, 1), # Bearic the White | Melting Cheese
    (8230003, 4021033, 100, 1, 1), # Evil Poacher | Basic Spell Essence
    (8230003, 4021031, 50, 1, 1), # Evil Poacher | Twisted Time
    (8230003, 4033978, 7500, 1, 1), # Evil Poacher | Story Fragment
    (8230003, 4021032, 7500, 1, 1), # Evil Poacher | Mana Crystal
    (8230003, 4009078, 7500, 1, 1), # Evil Poacher | Poacher Certificate
    (8230003, 4021034, 100, 1, 1), # Evil Poacher | Fine Spell Essence
    (8230003, 4021035, 100, 1, 1), # Evil Poacher | Grand Spell Essence
    (8145004, 4021034, 100, 1, 1), # Clowning Scarlion | Fine Spell Essence
    (8145004, 4033238, 7500, 1, 1), # Clowning Scarlion | Balloon
    (8145004, 4021031, 50, 1, 1), # Clowning Scarlion | Twisted Time
    (8145004, 2000037, 100, 1, 1), # Clowning Scarlion | Sunrise Dew
    (8145004, 2001528, 100, 1, 1), # Clowning Scarlion | Melting Cheese
    (8145004, 4021032, 7500, 1, 1), # Clowning Scarlion | Mana Crystal
    (8145004, 4021035, 100, 1, 1), # Clowning Scarlion | Grand Spell Essence
    (8145004, 4021033, 100, 1, 1), # Clowning Scarlion | Basic Spell Essence
    (8145004, 4000682, 7500, 1, 1), # Clowning Scarlion | Tinker Log
    (8145005, 4000683, 7500, 1, 1), # Colabear | Colabear Cap
    (8145005, 4021035, 100, 1, 1), # Colabear | Grand Spell Essence
    (8145005, 4033234, 7500, 1, 1), # Colabear | Treasure Note
    (8145005, 4021034, 100, 1, 1), # Colabear | Fine Spell Essence
    (8145005, 4021032, 7500, 1, 1), # Colabear | Mana Crystal
    (8145005, 4021033, 100, 1, 1), # Colabear | Basic Spell Essence
    (8145005, 4021031, 50, 1, 1), # Colabear | Twisted Time
    (8145006, 4000684, 7500, 1, 1), # Soaring Scarlion | Toy Plane
    (8145006, 4021032, 7500, 1, 1), # Soaring Scarlion | Mana Crystal
    (8145006, 4021034, 100, 1, 1), # Soaring Scarlion | Fine Spell Essence
    (8145006, 4021035, 100, 1, 1), # Soaring Scarlion | Grand Spell Essence
    (8145006, 4033234, 7500, 1, 1), # Soaring Scarlion | Treasure Note
    (8145006, 4021031, 50, 1, 1), # Soaring Scarlion | Twisted Time
    (8145006, 4021033, 100, 1, 1), # Soaring Scarlion | Basic Spell Essence
    (8145007, 4021033, 100, 1, 1), # Jellybus | Basic Spell Essence
    (8145007, 4021035, 100, 1, 1), # Jellybus | Grand Spell Essence
    (8145007, 4000685, 7500, 1, 1), # Jellybus | Jellybus Tire
    (8145007, 2001528, 100, 1, 1), # Jellybus | Melting Cheese
    (8145007, 4021032, 7500, 1, 1), # Jellybus | Mana Crystal
    (8145007, 4021034, 100, 1, 1), # Jellybus | Fine Spell Essence
    (8145007, 4021031, 50, 1, 1), # Jellybus | Twisted Time
    (8145007, 2000037, 100, 1, 1), # Jellybus | Sunrise Dew
    (8145000, 4021034, 100, 1, 1), # Creeper Scarlion | Fine Spell Essence
    (8145000, 4021033, 100, 1, 1), # Creeper Scarlion | Basic Spell Essence
    (8145000, 2001528, 100, 1, 1), # Creeper Scarlion | Melting Cheese
    (8145000, 4021035, 100, 1, 1), # Creeper Scarlion | Grand Spell Essence
    (8145000, 2000037, 100, 1, 1), # Creeper Scarlion | Sunrise Dew
    (8145000, 4000679, 7500, 1, 1), # Creeper Scarlion | Scarlion Muffler
    (8145000, 4021032, 7500, 1, 1), # Creeper Scarlion | Mana Crystal
    (8145000, 4021031, 50, 1, 1), # Creeper Scarlion | Twisted Time
    (9300121, 4001117, 1000, 1, 1), # Lord Pirate's Furious Kru | Old Metal Key
    (8145001, 2001528, 100, 1, 1), # Ratacula | Melting Cheese
    (8145001, 4000680, 7500, 1, 1), # Ratacula | Ratacular Hat
    (8145001, 2000037, 100, 1, 1), # Ratacula | Sunrise Dew
    (8145001, 4021033, 100, 1, 1), # Ratacula | Basic Spell Essence
    (8145001, 4021031, 50, 1, 1), # Ratacula | Twisted Time
    (8145001, 4021035, 100, 1, 1), # Ratacula | Grand Spell Essence
    (8145001, 4021032, 7500, 1, 1), # Ratacula | Mana Crystal
    (8145001, 4021034, 100, 1, 1), # Ratacula | Fine Spell Essence
    (9300120, 4001117, 1000, 1, 1), # Lord Pirate's Furious Mr. Alli | Old Metal Key
    (8145002, 4021035, 100, 1, 1), # Ghost | Grand Spell Essence
    (8145002, 4021033, 100, 1, 1), # Ghost | Basic Spell Essence
    (8145002, 2001528, 100, 1, 1), # Ghost | Melting Cheese
    (8145002, 4021031, 50, 1, 1), # Ghost | Twisted Time
    (8145002, 4021034, 100, 1, 1), # Ghost | Fine Spell Essence
    (8145002, 4021032, 7500, 1, 1), # Ghost | Mana Crystal
    (8145002, 2000037, 100, 1, 1), # Ghost | Sunrise Dew
    (8145003, 4033234, 7500, 1, 1), # Canterbear | Treasure Note
    (8145003, 4000681, 7500, 1, 1), # Canterbear | Toy Horse Tail
    (8145003, 4021033, 100, 1, 1), # Canterbear | Basic Spell Essence
    (8145003, 4021032, 7500, 1, 1), # Canterbear | Mana Crystal
    (8145003, 4021031, 50, 1, 1), # Canterbear | Twisted Time
    (8145003, 4021034, 100, 1, 1), # Canterbear | Fine Spell Essence
    (8145003, 4021035, 100, 1, 1), # Canterbear | Grand Spell Essence
    (8800401, 2001554, 100, 1, 1), # Poison Hornet | Sunset Dew
    (8800401, 2590004, 100, 1, 1), # Poison Hornet | Prototype Soul Enchanter
    (8800401, 4033783, 7500, 1, 1), # Poison Hornet | Large Poison Gland
    (8800401, 4021035, 100, 1, 1), # Poison Hornet | Grand Spell Essence
    (8800401, 4021032, 7500, 1, 1), # Poison Hornet | Mana Crystal
    (8800401, 2434021, 100, 1, 1), # Poison Hornet | Medal of Honor
    (8800401, 2000036, 100, 1, 1), # Poison Hornet | Reindeer Milk
    (8800401, 4021034, 100, 1, 1), # Poison Hornet | Fine Spell Essence
    (8800401, 4000887, 7500, 1, 1), # Poison Hornet | Poison Hornet Stinger
    (8800401, 4021033, 100, 1, 1), # Poison Hornet | Basic Spell Essence
    (8800401, 4021020, 10, 1, 1), # Poison Hornet | Confusion Fragment
    (8800401, 4021031, 50, 1, 1), # Poison Hornet | Twisted Time
    (9300122, 4001117, 1000, 1, 1), # Lord Pirate's Furious Captain | Old Metal Key
    (9300116, 4001121, 1000, 1, 1), # Lord Pirate's Enraged Captain | Mark of the Rising Pirate
    (9300116, 4001122, 1000, 1, 1), # Lord Pirate's Enraged Captain | Mark of the Veteran Pirate
    (9300116, 4001120, 1000, 1, 1), # Lord Pirate's Enraged Captain | Mark of the Rookie Pirate
    (9300115, 4001122, 1000, 1, 1), # Lord Pirate's Enraged Kru | Mark of the Veteran Pirate
    (9300115, 4001120, 1000, 1, 1), # Lord Pirate's Enraged Kru | Mark of the Rookie Pirate
    (9300115, 4001121, 1000, 1, 1), # Lord Pirate's Enraged Kru | Mark of the Rising Pirate
    (9300114, 4001121, 1000, 1, 1), # Lord Pirate's Enraged Mr. Alli | Mark of the Rising Pirate
    (9300114, 4001120, 1000, 1, 1), # Lord Pirate's Enraged Mr. Alli | Mark of the Rookie Pirate
    (9300114, 4001122, 1000, 1, 1), # Lord Pirate's Enraged Mr. Alli | Mark of the Veteran Pirate
    (1150001, 2870017, 100, 1, 1), # Strange Sign | Strange Sign Familiar
    (1150001, 2040900, 100, 1, 1), # Strange Sign | Scroll for Shield for DEF 100%
    (1150001, 2060000, 100, 1, 1), # Strange Sign | Arrow for Bow
    (1150001, 2000001, 100, 1, 1), # Strange Sign | Orange Potion
    (1150001, 2061000, 100, 1, 1), # Strange Sign | Arrow for Crossbow
    (1150001, 2040003, 100, 1, 1), # Strange Sign | Scroll for Helmet for HP 100%
    (1150001, 2000003, 100, 1, 1), # Strange Sign | Blue Potion
    (1150001, 2000000, 100, 1, 1), # Strange Sign | Red Potion
    (1150001, 2000002, 100, 1, 1), # Strange Sign | White Potion
    (1150001, 4000599, 7500, 1, 1), # Strange Sign | Wooden Board
    (1150001, 2040000, 100, 1, 1), # Strange Sign | Scroll for Helmet for DEF 100%
    (4220000, 4000166, 7500, 1, 1), # Seruf | Shrimp Meat
    (4220000, 4000167, 7500, 1, 1), # Seruf | Hard Needle
    (4220000, 4032474, 7500, 1, 1), # Seruf | Seruf Pearl
    (1150000, 2040901, 100, 1, 1), # Patrol Robot | Scroll for Shield for DEF 60%
    (1150000, 2040614, 100, 1, 1), # Patrol Robot | Scroll for Bottomwear for DEX 100%
    (1150000, 2870016, 100, 1, 1), # Patrol Robot | Patrol Robot Familiar
    (1150000, 2000000, 100, 1, 1), # Patrol Robot | Red Potion
    (1150000, 2000003, 100, 1, 1), # Patrol Robot | Blue Potion
    (1150000, 2000002, 100, 1, 1), # Patrol Robot | White Potion
    (1150000, 2060000, 100, 1, 1), # Patrol Robot | Arrow for Bow
    (1150000, 2061000, 100, 1, 1), # Patrol Robot | Arrow for Crossbow
    (1150000, 4000598, 7500, 1, 1), # Patrol Robot | Patrol Robot Memory Chip
    (1150000, 2000001, 100, 1, 1), # Patrol Robot | Orange Potion
    (1150000, 2048000, 100, 1, 1), # Patrol Robot | Scroll for Pet Equip. for Speed 100%
    (4220001, 4000166, 7500, 1, 1), # Seruf | Shrimp Meat
    (4220001, 4032474, 7500, 1, 1), # Seruf | Seruf Pearl
    (4220001, 4000167, 7500, 1, 1), # Seruf | Hard Needle
    (1150002, 2043000, 100, 1, 1), # Serpent | Scroll for One-Handed Sword for ATT 100%
    (1150002, 2040515, 100, 1, 1), # Serpent | Scroll for Overall Armor for LUK 100%
    (1150002, 2870018, 100, 1, 1), # Serpent | Serpent Familiar
    (1150002, 2000002, 100, 1, 1), # Serpent | White Potion
    (1150002, 2000003, 100, 1, 1), # Serpent | Blue Potion
    (1150002, 2000001, 100, 1, 1), # Serpent | Orange Potion
    (1150002, 2000000, 100, 1, 1), # Serpent | Red Potion
    (1150002, 2044400, 100, 1, 1), # Serpent | Scroll for Pole Arm for ATT 100%
    (1150002, 2061000, 100, 1, 1), # Serpent | Arrow for Crossbow
    (1150002, 4000600, 7500, 1, 1), # Serpent | Serpent Tail
    (1150002, 2060000, 100, 1, 1), # Serpent | Arrow for Bow
    (8150100, 2590004, 100, 1, 1), # Shark | Prototype Soul Enchanter
    (8150100, 4031252, 100, 1, 1), # Shark | Ripped Note
    (8150100, 2001528, 100, 1, 1), # Shark | Melting Cheese
    (8150100, 4021034, 100, 1, 1), # Shark | Fine Spell Essence
    (8150100, 4031255, 100, 1, 1), # Shark | Broken Camera
    (8150100, 2870336, 100, 1, 1), # Shark | Shark Familiar
    (8150100, 2000037, 100, 1, 1), # Shark | Sunrise Dew
    (8150100, 4021031, 50, 1, 1), # Shark | Twisted Time
    (8150100, 4021033, 100, 1, 1), # Shark | Basic Spell Essence
    (8150100, 4021032, 7500, 1, 1), # Shark | Mana Crystal
    (8150100, 2434021, 100, 1, 1), # Shark | Medal of Honor
    (8150100, 4000180, 7500, 1, 1), # Shark | Shark Denture
    (8150100, 4021035, 100, 1, 1), # Shark | Grand Spell Essence
    (8150101, 2590004, 100, 1, 1), # Cold Shark | Prototype Soul Enchanter
    (8150101, 2870337, 100, 1, 1), # Cold Shark | Cold Shark Familiar
    (8150101, 2001528, 100, 1, 1), # Cold Shark | Melting Cheese
    (8150101, 4021032, 7500, 1, 1), # Cold Shark | Mana Crystal
    (8150101, 4021033, 100, 1, 1), # Cold Shark | Basic Spell Essence
    (8150101, 4021031, 50, 1, 1), # Cold Shark | Twisted Time
    (8150101, 4021034, 100, 1, 1), # Cold Shark | Fine Spell Essence
    (8150101, 4000181, 7500, 1, 1), # Cold Shark | Frozen Shark's Fin
    (8150101, 2000037, 100, 1, 1), # Cold Shark | Sunrise Dew
    (8150101, 4021035, 100, 1, 1), # Cold Shark | Grand Spell Essence
    (8150101, 4031252, 100, 1, 1), # Cold Shark | Ripped Note
    (8150101, 4031255, 100, 1, 1), # Cold Shark | Broken Camera
    (8150101, 2434021, 100, 1, 1), # Cold Shark | Medal of Honor
    (5120100, 4021033, 100, 1, 1), # MT-09 | Basic Spell Essence
    (5120100, 4021031, 50, 1, 1), # MT-09 | Twisted Time
    (5120100, 2434021, 100, 1, 1), # MT-09 | Medal of Honor
    (5120100, 4021020, 10, 1, 1), # MT-09 | Confusion Fragment
    (5120100, 2590004, 100, 1, 1), # MT-09 | Prototype Soul Enchanter
    (5120100, 4021035, 100, 1, 1), # MT-09 | Grand Spell Essence
    (5120100, 4021034, 100, 1, 1), # MT-09 | Fine Spell Essence
    (5120100, 4021032, 7500, 1, 1), # MT-09 | Mana Crystal
    (5120100, 2001554, 100, 1, 1), # MT-09 | Sunset Dew
    (5120100, 2870195, 100, 1, 1), # MT-09 | MT-09 Familiar
    (5120100, 2000036, 100, 1, 1), # MT-09 | Reindeer Milk
    (9000100, 4031013, 9000, 1, 1), # Skeledog | Dark Marble
    (2700312, 2870226, 100, 1, 1), # Malady | Malady Familiar
    (130101, 2022053, 100, 1, 1), # Red Snail | Jujube
    (130101, 2000003, 100, 1, 1), # Red Snail | Blue Potion
    (130101, 2000000, 100, 1, 1), # Red Snail | Red Potion
    (130101, 2870004, 100, 1, 1), # Red Snail | Red Snail Familiar
    (130101, 2870004, 100, 1, 1), # Red Snail | Red Snail Familiar
    (130101, 4010004, 7500, 1, 1), # Red Snail | Silver Ore
    (130101, 2010009, 100, 1, 1), # Red Snail | Green Apple
    (130101, 4000016, 7500, 1, 1), # Red Snail | Red Snail Shell
    (130101, 2022054, 100, 1, 1), # Red Snail | Pear
    (130101, 2022055, 100, 1, 1), # Red Snail | Persimmon
    (130101, 2061000, 100, 1, 1), # Red Snail | Arrow for Crossbow
    (130101, 2060000, 100, 1, 1), # Red Snail | Arrow for Bow
    (130101, 4020004, 7500, 1, 1), # Red Snail | Opal Ore
    (7130200, 4000053, 7500, 1, 1), # Werewolf | Werewolf Toenail
    (7130200, 4021032, 7500, 1, 1), # Werewolf | Mana Crystal
    (7130200, 2049401, 100, 1, 1), # Werewolf | Potential Scroll
    (7130200, 2060000, 100, 1, 1), # Werewolf | Arrow for Bow
    (7130200, 2001528, 100, 1, 1), # Werewolf | Melting Cheese
    (7130200, 2041058, 100, 1, 1), # Werewolf | Scroll for Cape for Cold Protection 10%
    (7130200, 4021035, 100, 1, 1), # Werewolf | Grand Spell Essence
    (7130200, 2870284, 100, 1, 1), # Werewolf | Werewolf Familiar
    (7130200, 4021031, 50, 1, 1), # Werewolf | Twisted Time
    (7130200, 2001527, 100, 1, 1), # Werewolf | Unagi
    (7130200, 4021033, 100, 1, 1), # Werewolf | Basic Spell Essence
    (7130200, 4006000, 7500, 1, 1), # Werewolf | The Magic Rock
    (7130200, 2330005, 100, 1, 1), # Werewolf | Eternal Bullet
    (7130200, 2590004, 100, 1, 1), # Werewolf | Prototype Soul Enchanter
    (7130200, 2001001, 100, 1, 1), # Werewolf | Ice Cream Pop
    (7130200, 4021034, 100, 1, 1), # Werewolf | Fine Spell Essence
    (7130200, 2434021, 100, 1, 1), # Werewolf | Medal of Honor
    (130100, 2000000, 100, 1, 1), # Stump | Red Potion
    (130100, 2000003, 100, 1, 1), # Stump | Blue Potion
    (130100, 4000003, 7500, 1, 1), # Stump | Tree Branch
    (130100, 2870003, 100, 1, 1), # Stump | Stump Familiar
    (130100, 2010009, 100, 1, 1), # Stump | Green Apple
    (8910000, 4310029, 7500, 1, 1), # Chaos Von Bon | Crusader Coins
    (8910000, 4009096, 7500, 1, 1), # Chaos Von Bon | Piece of Time
    (8147014, 4030017, 7500, 1, 1), # General Hornet | Fresh Honeycomb
    (8147014, 4033784, 7500, 1, 1), # General Hornet | Royal Jelly
    (8147008, 4021020, 10, 1, 1), # Polluted Tree Spirit | Confusion Fragment
    (8147008, 4021034, 100, 1, 1), # Polluted Tree Spirit | Fine Spell Essence
    (8147008, 2001554, 100, 1, 1), # Polluted Tree Spirit | Sunset Dew
    (8147008, 4000891, 7500, 1, 1), # Polluted Tree Spirit | Decaying Branch
    (8147008, 4021033, 100, 1, 1), # Polluted Tree Spirit | Basic Spell Essence
    (8147008, 2434021, 100, 1, 1), # Polluted Tree Spirit | Medal of Honor
    (8147008, 2590004, 100, 1, 1), # Polluted Tree Spirit | Prototype Soul Enchanter
    (8147008, 2000036, 100, 1, 1), # Polluted Tree Spirit | Reindeer Milk
    (8147008, 4021032, 7500, 1, 1), # Polluted Tree Spirit | Mana Crystal
    (8147008, 4021031, 50, 1, 1), # Polluted Tree Spirit | Twisted Time
    (8147008, 4021035, 100, 1, 1), # Polluted Tree Spirit | Grand Spell Essence
    (8147009, 4021035, 100, 1, 1), # Corrupted Tree Spirit | Grand Spell Essence
    (8147009, 4021033, 100, 1, 1), # Corrupted Tree Spirit | Basic Spell Essence
    (8147009, 4021020, 10, 1, 1), # Corrupted Tree Spirit | Confusion Fragment
    (8147009, 4021034, 100, 1, 1), # Corrupted Tree Spirit | Fine Spell Essence
    (8147009, 2434021, 100, 1, 1), # Corrupted Tree Spirit | Medal of Honor
    (8147009, 4000892, 7500, 1, 1), # Corrupted Tree Spirit | Rotting Branch
    (8147009, 2000036, 100, 1, 1), # Corrupted Tree Spirit | Reindeer Milk
    (8147009, 4021032, 7500, 1, 1), # Corrupted Tree Spirit | Mana Crystal
    (8147009, 4021031, 50, 1, 1), # Corrupted Tree Spirit | Twisted Time
    (8147009, 2001554, 100, 1, 1), # Corrupted Tree Spirit | Sunset Dew
    (8147009, 2590004, 100, 1, 1), # Corrupted Tree Spirit | Prototype Soul Enchanter
    (2700311, 2870089, 100, 1, 1), # Lupin | Lupin Familiar
    (2700311, 4000026, 7500, 1, 1), # Lupin | Lupin Doll
    (2700311, 4000029, 7500, 1, 1), # Lupin | Lupin's Banana
    (8147010, 4000891, 7500, 1, 1), # Polluted Rock Spirit | Decaying Branch
    (8147010, 4021035, 100, 1, 1), # Polluted Rock Spirit | Grand Spell Essence
    (8147010, 4021032, 7500, 1, 1), # Polluted Rock Spirit | Mana Crystal
    (8147010, 2000036, 100, 1, 1), # Polluted Rock Spirit | Reindeer Milk
    (8147010, 4021020, 10, 1, 1), # Polluted Rock Spirit | Confusion Fragment
    (8147010, 2434021, 100, 1, 1), # Polluted Rock Spirit | Medal of Honor
    (8147010, 4021034, 100, 1, 1), # Polluted Rock Spirit | Fine Spell Essence
    (8147010, 4021033, 100, 1, 1), # Polluted Rock Spirit | Basic Spell Essence
    (8147010, 4021031, 50, 1, 1), # Polluted Rock Spirit | Twisted Time
    (8147010, 2001554, 100, 1, 1), # Polluted Rock Spirit | Sunset Dew
    (8147010, 2590004, 100, 1, 1), # Polluted Rock Spirit | Prototype Soul Enchanter
    (2700310, 4009221, 7500, 1, 1), # Sparrow | Sparrow Egg
    (8147011, 4021020, 10, 1, 1), # Corrupted Rock Spirit | Confusion Fragment
    (8147011, 2000036, 100, 1, 1), # Corrupted Rock Spirit | Reindeer Milk
    (8147011, 4021031, 50, 1, 1), # Corrupted Rock Spirit | Twisted Time
    (8147011, 4021034, 100, 1, 1), # Corrupted Rock Spirit | Fine Spell Essence
    (8147011, 4021033, 100, 1, 1), # Corrupted Rock Spirit | Basic Spell Essence
    (8147011, 4021032, 7500, 1, 1), # Corrupted Rock Spirit | Mana Crystal
    (8147011, 2001554, 100, 1, 1), # Corrupted Rock Spirit | Sunset Dew
    (8147011, 4000894, 7500, 1, 1), # Corrupted Rock Spirit | Shaky Pebble
    (8147011, 4021035, 100, 1, 1), # Corrupted Rock Spirit | Grand Spell Essence
    (8147011, 2434021, 100, 1, 1), # Corrupted Rock Spirit | Medal of Honor
    (8147011, 2590004, 100, 1, 1), # Corrupted Rock Spirit | Prototype Soul Enchanter
    (8147004, 2590004, 100, 1, 1), # Dark Flora | Prototype Soul Enchanter
    (8147004, 4000883, 7500, 1, 1), # Dark Flora | Dark Flora Petal
    (8147004, 2000036, 100, 1, 1), # Dark Flora | Reindeer Milk
    (8147004, 4021032, 7500, 1, 1), # Dark Flora | Mana Crystal
    (8147004, 4021031, 50, 1, 1), # Dark Flora | Twisted Time
    (8147004, 2001554, 100, 1, 1), # Dark Flora | Sunset Dew
    (8147004, 4021035, 100, 1, 1), # Dark Flora | Grand Spell Essence
    (8147004, 4021034, 100, 1, 1), # Dark Flora | Fine Spell Essence
    (8147004, 2434021, 100, 1, 1), # Dark Flora | Medal of Honor
    (8147004, 4033781, 7500, 1, 1), # Dark Flora | Oily Seed
    (8147004, 4021020, 10, 1, 1), # Dark Flora | Confusion Fragment
    (8147004, 4021033, 100, 1, 1), # Dark Flora | Basic Spell Essence
    (8147005, 4021035, 100, 1, 1), # Hornet | Grand Spell Essence
    (8147005, 2590004, 100, 1, 1), # Hornet | Prototype Soul Enchanter
    (8147005, 4021033, 100, 1, 1), # Hornet | Basic Spell Essence
    (8147005, 4000885, 7500, 1, 1), # Hornet | Hornet Stinger
    (8147005, 4021020, 10, 1, 1), # Hornet | Confusion Fragment
    (8147005, 4021032, 7500, 1, 1), # Hornet | Mana Crystal
    (8147005, 4033782, 7500, 1, 1), # Hornet | Hornet Honey
    (8147005, 2001554, 100, 1, 1), # Hornet | Sunset Dew
    (8147005, 2000036, 100, 1, 1), # Hornet | Reindeer Milk
    (8147005, 2434021, 100, 1, 1), # Hornet | Medal of Honor
    (8147005, 4021034, 100, 1, 1), # Hornet | Fine Spell Essence
    (8147005, 4021031, 50, 1, 1), # Hornet | Twisted Time
    (8147006, 4021031, 50, 1, 1), # Poison Hornet | Twisted Time
    (8147006, 2000036, 100, 1, 1), # Poison Hornet | Reindeer Milk
    (8147006, 2434021, 100, 1, 1), # Poison Hornet | Medal of Honor
    (8147006, 4033783, 7500, 1, 1), # Poison Hornet | Large Poison Gland
    (8147006, 4000887, 7500, 1, 1), # Poison Hornet | Poison Hornet Stinger
    (8147006, 2001554, 100, 1, 1), # Poison Hornet | Sunset Dew
    (8147006, 4021033, 100, 1, 1), # Poison Hornet | Basic Spell Essence
    (8147006, 4021035, 100, 1, 1), # Poison Hornet | Grand Spell Essence
    (8147006, 4021032, 7500, 1, 1), # Poison Hornet | Mana Crystal
    (8147006, 4021034, 100, 1, 1), # Poison Hornet | Fine Spell Essence
    (8147006, 2590004, 100, 1, 1), # Poison Hornet | Prototype Soul Enchanter
    (8147006, 4021020, 10, 1, 1), # Poison Hornet | Confusion Fragment
    (8147007, 4021034, 100, 1, 1), # General Hornet | Fine Spell Essence
    (8147007, 4021031, 50, 1, 1), # General Hornet | Twisted Time
    (8147007, 2001554, 100, 1, 1), # General Hornet | Sunset Dew
    (8147007, 4021020, 10, 1, 1), # General Hornet | Confusion Fragment
    (8147007, 2590004, 100, 1, 1), # General Hornet | Prototype Soul Enchanter
    (8147007, 2434021, 100, 1, 1), # General Hornet | Medal of Honor
    (8147007, 4021035, 100, 1, 1), # General Hornet | Grand Spell Essence
    (8147007, 4021032, 7500, 1, 1), # General Hornet | Mana Crystal
    (8147007, 2000036, 100, 1, 1), # General Hornet | Reindeer Milk
    (8147007, 4021033, 100, 1, 1), # General Hornet | Basic Spell Essence
    (8147007, 4030017, 7500, 1, 1), # General Hornet | Fresh Honeycomb
    (8147007, 4033784, 7500, 1, 1), # General Hornet | Royal Jelly
    (8147007, 4000889, 7500, 1, 1), # General Hornet | General Hornet Stinger
    (1110100, 2022055, 100, 1, 1), # Green Mushroom | Persimmon
    (1110100, 2022054, 100, 1, 1), # Green Mushroom | Pear
    (1110100, 2060000, 100, 1, 1), # Green Mushroom | Arrow for Bow
    (1110100, 2870009, 100, 1, 1), # Green Mushroom | Green Mushroom Familiar
    (1110100, 2043102, 100, 1, 1), # Green Mushroom | Scroll for One-Handed Axe for ATT 10%
    (1110100, 4000012, 7500, 1, 1), # Green Mushroom | Green Mushroom Cap
    (1110100, 4010004, 7500, 1, 1), # Green Mushroom | Silver Ore
    (1110100, 2000000, 100, 1, 1), # Green Mushroom | Red Potion
    (1110100, 4020003, 7500, 1, 1), # Green Mushroom | Emerald Ore
    (1110100, 4020002, 7500, 1, 1), # Green Mushroom | AquaMarine Ore
    (1110100, 2022053, 100, 1, 1), # Green Mushroom | Jujube
    (1110100, 2000003, 100, 1, 1), # Green Mushroom | Blue Potion
    (8147000, 4021032, 7500, 1, 1), # Mantis | Mana Crystal
    (8147000, 4021033, 100, 1, 1), # Mantis | Basic Spell Essence
    (8147000, 2000036, 100, 1, 1), # Mantis | Reindeer Milk
    (8147000, 4021034, 100, 1, 1), # Mantis | Fine Spell Essence
    (8147000, 4021035, 100, 1, 1), # Mantis | Grand Spell Essence
    (8147000, 2434021, 100, 1, 1), # Mantis | Medal of Honor
    (8147000, 4021020, 10, 1, 1), # Mantis | Confusion Fragment
    (8147000, 4000878, 7500, 1, 1), # Mantis | Mantis Wing
    (8147000, 2590004, 100, 1, 1), # Mantis | Prototype Soul Enchanter
    (8147000, 4021031, 50, 1, 1), # Mantis | Twisted Time
    (8147000, 2001554, 100, 1, 1), # Mantis | Sunset Dew
    (1110101, 4010001, 7500, 1, 1), # Dark Stump | Steel Ore
    (1110101, 2040802, 100, 1, 1), # Dark Stump | Scroll for Gloves for DEX 10%
    (1110101, 2061000, 100, 1, 1), # Dark Stump | Arrow for Crossbow
    (1110101, 4020000, 7500, 1, 1), # Dark Stump | Garnet Ore
    (1110101, 2000000, 100, 1, 1), # Dark Stump | Red Potion
    (1110101, 2022054, 100, 1, 1), # Dark Stump | Pear
    (1110101, 2022053, 100, 1, 1), # Dark Stump | Jujube
    (1110101, 4000005, 7500, 1, 1), # Dark Stump | Leaf
    (1110101, 2022055, 100, 1, 1), # Dark Stump | Persimmon
    (1110101, 2870010, 100, 1, 1), # Dark Stump | Dark Stump Familiar
    (8147001, 4021032, 7500, 1, 1), # Blood Mantis | Mana Crystal
    (8147001, 4021034, 100, 1, 1), # Blood Mantis | Fine Spell Essence
    (8147001, 2434021, 100, 1, 1), # Blood Mantis | Medal of Honor
    (8147001, 4021031, 50, 1, 1), # Blood Mantis | Twisted Time
    (8147001, 4000879, 7500, 1, 1), # Blood Mantis | Really Red Horn
    (8147001, 2001554, 100, 1, 1), # Blood Mantis | Sunset Dew
    (8147001, 2000036, 100, 1, 1), # Blood Mantis | Reindeer Milk
    (8147001, 4021033, 100, 1, 1), # Blood Mantis | Basic Spell Essence
    (8147001, 2590004, 100, 1, 1), # Blood Mantis | Prototype Soul Enchanter
    (8147001, 4021035, 100, 1, 1), # Blood Mantis | Grand Spell Essence
    (8147001, 4021020, 10, 1, 1), # Blood Mantis | Confusion Fragment
    (8147002, 2001554, 100, 1, 1), # Poison Mantis | Sunset Dew
    (8147002, 2434021, 100, 1, 1), # Poison Mantis | Medal of Honor
    (8147002, 2000036, 100, 1, 1), # Poison Mantis | Reindeer Milk
    (8147002, 4000880, 7500, 1, 1), # Poison Mantis | Poisoned Rake
    (8147002, 4021020, 10, 1, 1), # Poison Mantis | Confusion Fragment
    (8147002, 4021035, 100, 1, 1), # Poison Mantis | Grand Spell Essence
    (8147002, 2590004, 100, 1, 1), # Poison Mantis | Prototype Soul Enchanter
    (8147002, 4021032, 7500, 1, 1), # Poison Mantis | Mana Crystal
    (8147002, 4021031, 50, 1, 1), # Poison Mantis | Twisted Time
    (8147002, 4021034, 100, 1, 1), # Poison Mantis | Fine Spell Essence
    (8147002, 4021033, 100, 1, 1), # Poison Mantis | Basic Spell Essence
    (8147003, 4021020, 10, 1, 1), # Flora | Confusion Fragment
    (8147003, 2590004, 100, 1, 1), # Flora | Prototype Soul Enchanter
    (8147003, 4021035, 100, 1, 1), # Flora | Grand Spell Essence
    (8147003, 4021034, 100, 1, 1), # Flora | Fine Spell Essence
    (8147003, 4021031, 50, 1, 1), # Flora | Twisted Time
    (8147003, 2000036, 100, 1, 1), # Flora | Reindeer Milk
    (8147003, 4000881, 7500, 1, 1), # Flora | Flora Petal
    (8147003, 4033780, 7500, 1, 1), # Flora | Damp Seed
    (8147003, 4021032, 7500, 1, 1), # Flora | Mana Crystal
    (8147003, 2001554, 100, 1, 1), # Flora | Sunset Dew
    (8147003, 2434021, 100, 1, 1), # Flora | Medal of Honor
    (8147003, 4021033, 100, 1, 1), # Flora | Basic Spell Essence
    (8240181, 4021031, 50, 1, 1), # Polluted Tree Sap | Twisted Time
    (8240181, 4021035, 100, 1, 1), # Polluted Tree Sap | Grand Spell Essence
    (8240181, 4021032, 7500, 1, 1), # Polluted Tree Sap | Mana Crystal
    (8240181, 2001554, 100, 1, 1), # Polluted Tree Sap | Sunset Dew
    (8240181, 4021034, 100, 1, 1), # Polluted Tree Sap | Fine Spell Essence
    (8240181, 2434021, 100, 1, 1), # Polluted Tree Sap | Medal of Honor
    (8240181, 4021020, 10, 1, 1), # Polluted Tree Sap | Confusion Fragment
    (8240181, 2590004, 100, 1, 1), # Polluted Tree Sap | Prototype Soul Enchanter
    (8240181, 2000036, 100, 1, 1), # Polluted Tree Sap | Reindeer Milk
    (8240181, 4021033, 100, 1, 1), # Polluted Tree Sap | Basic Spell Essence
    (8240181, 4001868, 7500, 1, 1), # Polluted Tree Sap | Faint Stigma Spirit Stone
    (8240180, 4021035, 100, 1, 1), # Polluted Tree Sap | Grand Spell Essence
    (8240180, 4021020, 10, 1, 1), # Polluted Tree Sap | Confusion Fragment
    (8240180, 2434021, 100, 1, 1), # Polluted Tree Sap | Medal of Honor
    (8240180, 2590004, 100, 1, 1), # Polluted Tree Sap | Prototype Soul Enchanter
    (8240180, 4021033, 100, 1, 1), # Polluted Tree Sap | Basic Spell Essence
    (8240180, 4001868, 7500, 1, 1), # Polluted Tree Sap | Faint Stigma Spirit Stone
    (8240180, 4021032, 7500, 1, 1), # Polluted Tree Sap | Mana Crystal
    (8240180, 2001554, 100, 1, 1), # Polluted Tree Sap | Sunset Dew
    (8240180, 4021034, 100, 1, 1), # Polluted Tree Sap | Fine Spell Essence
    (8240180, 4021031, 50, 1, 1), # Polluted Tree Sap | Twisted Time
    (8240180, 2000036, 100, 1, 1), # Polluted Tree Sap | Reindeer Milk
    (5120002, 4021032, 7500, 1, 1), # Lioner | Mana Crystal
    (5120002, 4021034, 100, 1, 1), # Lioner | Fine Spell Essence
    (5120002, 4021035, 100, 1, 1), # Lioner | Grand Spell Essence
    (5120002, 4021033, 100, 1, 1), # Lioner | Basic Spell Essence
    (5120002, 4033145, 7500, 1, 1), # Lioner | Cracked Black Crystal
    (5120002, 2001002, 100, 1, 1), # Lioner | Very Special Sundae
    (5120002, 2590004, 100, 1, 1), # Lioner | Prototype Soul Enchanter
    (5120002, 2001001, 100, 1, 1), # Lioner | Ice Cream Pop
    (5120002, 4000071, 7500, 1, 1), # Lioner | Lioner Tail
    (5120002, 2431174, 100, 1, 1), # Lioner | Medal of Honor
    (5120002, 2870193, 100, 1, 1), # Lioner | Lioner Familiar
    (5120003, 4021033, 100, 1, 1), # Grupin | Basic Spell Essence
    (5120003, 4000072, 7500, 1, 1), # Grupin | Grupin Tail
    (5120003, 4033145, 7500, 1, 1), # Grupin | Cracked Black Crystal
    (5120003, 4021034, 100, 1, 1), # Grupin | Fine Spell Essence
    (5120003, 2001001, 100, 1, 1), # Grupin | Ice Cream Pop
    (5120003, 2590004, 100, 1, 1), # Grupin | Prototype Soul Enchanter
    (5120003, 2001002, 100, 1, 1), # Grupin | Very Special Sundae
    (5120003, 2431174, 100, 1, 1), # Grupin | Medal of Honor
    (5120003, 4021032, 7500, 1, 1), # Grupin | Mana Crystal
    (5120003, 2870194, 100, 1, 1), # Grupin | Grupin Familiar
    (5120003, 4021035, 100, 1, 1), # Grupin | Grand Spell Essence
    (5120000, 2431174, 100, 1, 1), # Luster Pixie | Medal of Honor
    (5120000, 2001002, 100, 1, 1), # Luster Pixie | Very Special Sundae
    (5120000, 4021034, 100, 1, 1), # Luster Pixie | Fine Spell Essence
    (5120000, 4021035, 100, 1, 1), # Luster Pixie | Grand Spell Essence
    (5120000, 2001001, 100, 1, 1), # Luster Pixie | Ice Cream Pop
    (5120000, 2870191, 100, 1, 1), # Luster Pixie | Luster Pixie Familiar
    (5120000, 2590004, 100, 1, 1), # Luster Pixie | Prototype Soul Enchanter
    (5120000, 4021032, 7500, 1, 1), # Luster Pixie | Mana Crystal
    (5120000, 4031309, 7500, 1, 1), # Luster Pixie | Cloud Piece
    (5120000, 4000061, 7500, 1, 1), # Luster Pixie | Luster Pixie's Sunpiece
    (5120000, 4021033, 100, 1, 1), # Luster Pixie | Basic Spell Essence
    (5120001, 4000070, 7500, 1, 1), # Cellion | Cellion Tail
    (5120001, 2001001, 100, 1, 1), # Cellion | Ice Cream Pop
    (5120001, 2431174, 100, 1, 1), # Cellion | Medal of Honor
    (5120001, 2590004, 100, 1, 1), # Cellion | Prototype Soul Enchanter
    (5120001, 4021034, 100, 1, 1), # Cellion | Fine Spell Essence
    (5120001, 4021035, 100, 1, 1), # Cellion | Grand Spell Essence
    (5120001, 4033145, 7500, 1, 1), # Cellion | Cracked Black Crystal
    (5120001, 4021033, 100, 1, 1), # Cellion | Basic Spell Essence
    (5120001, 2870192, 100, 1, 1), # Cellion | Cellion Familiar
    (5120001, 2001002, 100, 1, 1), # Cellion | Very Special Sundae
    (5120001, 4021032, 7500, 1, 1), # Cellion | Mana Crystal
    (8148012, 4000905, 7500, 1, 1), # Master Red Nirg | Master Red Nirg Helm
    (8610003, 2512137, 100, 1, 1), # Night | Advanced Magic Attack Potion IV Recipe
    (8610003, 4021031, 50, 1, 1), # Night | Twisted Time
    (8610003, 2001554, 100, 1, 1), # Night | Sunset Dew
    (8610003, 4021033, 100, 1, 1), # Night | Basic Spell Essence
    (8610003, 2512285, 100, 1, 1), # Night | Superior Dexterity Boost Potion Recipe
    (8610003, 2512266, 100, 1, 1), # Night | Superior Hero Potion Recipe
    (8610003, 2512099, 100, 1, 1), # Night | Advanced Dexterity Potion I Recipe
    (8610003, 2512128, 100, 1, 1), # Night | Advanced Luck Potion X Recipe
    (8610003, 2512154, 100, 1, 1), # Night | Advanced Strength Pill VI Recipe
    (8610003, 2512287, 100, 1, 1), # Night | Superior Luck Boost Potion Recipe
    (8610003, 4000663, 7500, 1, 1), # Night | Night Stone
    (8610003, 2512175, 100, 1, 1), # Night | Advanced Dexterity Pill VII Recipe
    (8610003, 4021020, 10, 1, 1), # Night | Confusion Fragment
    (8610003, 2049401, 100, 1, 1), # Night | Potential Scroll
    (8610003, 4021032, 7500, 1, 1), # Night | Mana Crystal
    (8610003, 2512271, 100, 1, 1), # Night | Legendary Blessing Potion Recipe
    (8610003, 4034983, 7500, 1, 1), # Night | Dream Fragment
    (8610003, 4034984, 7500, 1, 1), # Night | Dream Fragment
    (8610003, 4034985, 7500, 1, 1), # Night | Dream Fragment
    (8610003, 4034986, 7500, 1, 1), # Night | Dream Fragment
    (8610003, 4034987, 7500, 1, 1), # Night | Dream Fragment
    (8610003, 4034988, 7500, 1, 1), # Night | Dream Fragment
    (8610003, 2512270, 100, 1, 1), # Night | Exceptional Blessing Potion Recipe
    (8610003, 4021034, 100, 1, 1), # Night | Fine Spell Essence
    (8610003, 2070004, 100, 1, 1), # Night | Tobi Throwing-Stars
    (8610003, 2590004, 100, 1, 1), # Night | Prototype Soul Enchanter
    (8610003, 2512283, 100, 1, 1), # Night | Superior Intelligence Boost Potion Recipe
    (8610003, 4021035, 100, 1, 1), # Night | Grand Spell Essence
    (8610003, 2000036, 100, 1, 1), # Night | Reindeer Milk
    (8610003, 2434021, 100, 1, 1), # Night | Medal of Honor
    (8870103, 4021033, 100, 1, 1), # Bloodfang | Basic Spell Essence
    (8870103, 4021032, 7500, 1, 1), # Bloodfang | Mana Crystal
    (8870103, 4021035, 100, 1, 1), # Bloodfang | Grand Spell Essence
    (8870103, 4021031, 50, 1, 1), # Bloodfang | Twisted Time
    (8870103, 4021034, 100, 1, 1), # Bloodfang | Fine Spell Essence
    (8610002, 4021031, 50, 1, 1), # Wind | Twisted Time
    (8610002, 2512175, 100, 1, 1), # Wind | Advanced Dexterity Pill VII Recipe
    (8610002, 2049401, 100, 1, 1), # Wind | Potential Scroll
    (8610002, 2512271, 100, 1, 1), # Wind | Legendary Blessing Potion Recipe
    (8610002, 4021034, 100, 1, 1), # Wind | Fine Spell Essence
    (8610002, 4034983, 7500, 1, 1), # Wind | Dream Fragment
    (8610002, 4034984, 7500, 1, 1), # Wind | Dream Fragment
    (8610002, 4034985, 7500, 1, 1), # Wind | Dream Fragment
    (8610002, 4034986, 7500, 1, 1), # Wind | Dream Fragment
    (8610002, 4034987, 7500, 1, 1), # Wind | Dream Fragment
    (8610002, 4034988, 7500, 1, 1), # Wind | Dream Fragment
    (8610002, 4021033, 100, 1, 1), # Wind | Basic Spell Essence
    (8610002, 2512099, 100, 1, 1), # Wind | Advanced Dexterity Potion I Recipe
    (8610002, 2001554, 100, 1, 1), # Wind | Sunset Dew
    (8610002, 2434021, 100, 1, 1), # Wind | Medal of Honor
    (8610002, 2512287, 100, 1, 1), # Wind | Superior Luck Boost Potion Recipe
    (8610002, 2048004, 100, 1, 1), # Wind | Scroll for Pet Equip. for Jump 60%
    (8610002, 2000036, 100, 1, 1), # Wind | Reindeer Milk
    (8610002, 2512266, 100, 1, 1), # Wind | Superior Hero Potion Recipe
    (8610002, 4021035, 100, 1, 1), # Wind | Grand Spell Essence
    (8610002, 2590004, 100, 1, 1), # Wind | Prototype Soul Enchanter
    (8610002, 2512137, 100, 1, 1), # Wind | Advanced Magic Attack Potion IV Recipe
    (8610002, 2512283, 100, 1, 1), # Wind | Superior Intelligence Boost Potion Recipe
    (8610002, 2048001, 100, 1, 1), # Wind | Scroll for Pet Equip. for Speed 60%
    (8610002, 4000662, 7500, 1, 1), # Wind | Wind Stone
    (8610002, 4021020, 10, 1, 1), # Wind | Confusion Fragment
    (8610002, 4021032, 7500, 1, 1), # Wind | Mana Crystal
    (8610002, 2512128, 100, 1, 1), # Wind | Advanced Luck Potion X Recipe
    (8610002, 2512154, 100, 1, 1), # Wind | Advanced Strength Pill VI Recipe
    (8610002, 2070003, 100, 1, 1), # Wind | Kumbi Throwing-Stars
    (8610002, 2512285, 100, 1, 1), # Wind | Superior Dexterity Boost Potion Recipe
    (8610002, 2512270, 100, 1, 1), # Wind | Exceptional Blessing Potion Recipe
    (8870102, 4021031, 50, 1, 1), # Elite Pikeman Skeleknight | Twisted Time
    (8870102, 4021032, 7500, 1, 1), # Elite Pikeman Skeleknight | Mana Crystal
    (8870102, 4021035, 100, 1, 1), # Elite Pikeman Skeleknight | Grand Spell Essence
    (8870102, 4021033, 100, 1, 1), # Elite Pikeman Skeleknight | Basic Spell Essence
    (8870102, 4021034, 100, 1, 1), # Elite Pikeman Skeleknight | Fine Spell Essence
    (8610001, 2512154, 100, 1, 1), # Blaze | Advanced Strength Pill VI Recipe
    (8610001, 2434021, 100, 1, 1), # Blaze | Medal of Honor
    (8610001, 2049401, 100, 1, 1), # Blaze | Potential Scroll
    (8610001, 2001554, 100, 1, 1), # Blaze | Sunset Dew
    (8610001, 2512128, 100, 1, 1), # Blaze | Advanced Luck Potion X Recipe
    (8610001, 2048005, 100, 1, 1), # Blaze | Scroll for Pet Equip. for Jump 10%
    (8610001, 2512270, 100, 1, 1), # Blaze | Exceptional Blessing Potion Recipe
    (8610001, 2070005, 100, 1, 1), # Blaze | Steely Throwing-Knives
    (8610001, 2512285, 100, 1, 1), # Blaze | Superior Dexterity Boost Potion Recipe
    (8610001, 4034983, 7500, 1, 1), # Blaze | Dream Fragment
    (8610001, 4034984, 7500, 1, 1), # Blaze | Dream Fragment
    (8610001, 4034985, 7500, 1, 1), # Blaze | Dream Fragment
    (8610001, 4034986, 7500, 1, 1), # Blaze | Dream Fragment
    (8610001, 4034987, 7500, 1, 1), # Blaze | Dream Fragment
    (8610001, 4034988, 7500, 1, 1), # Blaze | Dream Fragment
    (8610001, 2512137, 100, 1, 1), # Blaze | Advanced Magic Attack Potion IV Recipe
    (8610001, 2590004, 100, 1, 1), # Blaze | Prototype Soul Enchanter
    (8610001, 4000661, 7500, 1, 1), # Blaze | Blaze Stone
    (8610001, 2512175, 100, 1, 1), # Blaze | Advanced Dexterity Pill VII Recipe
    (8610001, 2512271, 100, 1, 1), # Blaze | Legendary Blessing Potion Recipe
    (8610001, 2512099, 100, 1, 1), # Blaze | Advanced Dexterity Potion I Recipe
    (8610001, 2000036, 100, 1, 1), # Blaze | Reindeer Milk
    (8610001, 2512266, 100, 1, 1), # Blaze | Superior Hero Potion Recipe
    (8610001, 4021032, 7500, 1, 1), # Blaze | Mana Crystal
    (8610001, 4021031, 50, 1, 1), # Blaze | Twisted Time
    (8610001, 2330005, 100, 1, 1), # Blaze | Eternal Bullet
    (8610001, 4021033, 100, 1, 1), # Blaze | Basic Spell Essence
    (8610001, 2512283, 100, 1, 1), # Blaze | Superior Intelligence Boost Potion Recipe
    (8610001, 4021020, 10, 1, 1), # Blaze | Confusion Fragment
    (8610001, 4021034, 100, 1, 1), # Blaze | Fine Spell Essence
    (8610001, 4021035, 100, 1, 1), # Blaze | Grand Spell Essence
    (8610001, 2512287, 100, 1, 1), # Blaze | Superior Luck Boost Potion Recipe
    (8870101, 4021032, 7500, 1, 1), # Elite Pikeman Skeleknight | Mana Crystal
    (8870101, 4021034, 100, 1, 1), # Elite Pikeman Skeleknight | Fine Spell Essence
    (8870101, 4021035, 100, 1, 1), # Elite Pikeman Skeleknight | Grand Spell Essence
    (8870101, 4021033, 100, 1, 1), # Elite Pikeman Skeleknight | Basic Spell Essence
    (8870101, 4021031, 50, 1, 1), # Elite Pikeman Skeleknight | Twisted Time
    (8610000, 2512137, 100, 1, 1), # Dawn | Advanced Magic Attack Potion IV Recipe
    (8610000, 2510255, 100, 1, 1), # Dawn | Lionheart Battle Cape Recipe
    (8610000, 2512285, 100, 1, 1), # Dawn | Superior Dexterity Boost Potion Recipe
    (8610000, 4021034, 100, 1, 1), # Dawn | Fine Spell Essence
    (8610000, 2512175, 100, 1, 1), # Dawn | Advanced Dexterity Pill VII Recipe
    (8610000, 2510258, 100, 1, 1), # Dawn | Raven Horn Chaser Cape Recipe
    (8610000, 4034983, 7500, 1, 1), # Dawn | Dream Fragment
    (8610000, 4034984, 7500, 1, 1), # Dawn | Dream Fragment
    (8610000, 4034985, 7500, 1, 1), # Dawn | Dream Fragment
    (8610000, 4034986, 7500, 1, 1), # Dawn | Dream Fragment
    (8610000, 4034987, 7500, 1, 1), # Dawn | Dream Fragment
    (8610000, 4034988, 7500, 1, 1), # Dawn | Dream Fragment
    (8610000, 4021020, 10, 1, 1), # Dawn | Confusion Fragment
    (8610000, 2512266, 100, 1, 1), # Dawn | Superior Hero Potion Recipe
    (8610000, 2512283, 100, 1, 1), # Dawn | Superior Intelligence Boost Potion Recipe
    (8610000, 2510256, 100, 1, 1), # Dawn | Dragon Tail Mage Cape Recipe
    (8610000, 2510259, 100, 1, 1), # Dawn | Shark Tooth Skipper Cape Recipe
    (8610000, 2330004, 100, 1, 1), # Dawn | Shiny Bullet
    (8610000, 2512099, 100, 1, 1), # Dawn | Advanced Dexterity Potion I Recipe
    (8610000, 2512154, 100, 1, 1), # Dawn | Advanced Strength Pill VI Recipe
    (8610000, 2000036, 100, 1, 1), # Dawn | Reindeer Milk
    (8610000, 2590004, 100, 1, 1), # Dawn | Prototype Soul Enchanter
    (8610000, 2512270, 100, 1, 1), # Dawn | Exceptional Blessing Potion Recipe
    (8610000, 2512128, 100, 1, 1), # Dawn | Advanced Luck Potion X Recipe
    (8610000, 2434021, 100, 1, 1), # Dawn | Medal of Honor
    (8610000, 4021032, 7500, 1, 1), # Dawn | Mana Crystal
    (8610000, 4021033, 100, 1, 1), # Dawn | Basic Spell Essence
    (8610000, 2512287, 100, 1, 1), # Dawn | Superior Luck Boost Potion Recipe
    (8610000, 2510257, 100, 1, 1), # Dawn | Falcon Wing Sentinel Cape Recipe
    (8610000, 4000660, 7500, 1, 1), # Dawn | Dawn Stone
    (8610000, 4021031, 50, 1, 1), # Dawn | Twisted Time
    (8610000, 2001554, 100, 1, 1), # Dawn | Sunset Dew
    (8610000, 2512271, 100, 1, 1), # Dawn | Legendary Blessing Potion Recipe
    (8610000, 4021035, 100, 1, 1), # Dawn | Grand Spell Essence
    (8148008, 4000901, 7500, 1, 1), # Crimson Stirge | Old Dust
    (8148008, 2000036, 100, 1, 1), # Crimson Stirge | Reindeer Milk
    (8148008, 4021033, 100, 1, 1), # Crimson Stirge | Basic Spell Essence
    (8148008, 4021034, 100, 1, 1), # Crimson Stirge | Fine Spell Essence
    (8148008, 2434021, 100, 1, 1), # Crimson Stirge | Medal of Honor
    (8148008, 4021032, 7500, 1, 1), # Crimson Stirge | Mana Crystal
    (8148008, 2590004, 100, 1, 1), # Crimson Stirge | Prototype Soul Enchanter
    (8148008, 2001554, 100, 1, 1), # Crimson Stirge | Sunset Dew
    (8148008, 4021031, 50, 1, 1), # Crimson Stirge | Twisted Time
    (8148008, 4021035, 100, 1, 1), # Crimson Stirge | Grand Spell Essence
    (8610007, 4021031, 50, 1, 1), # Official Knight C | Twisted Time
    (8610007, 4021032, 7500, 1, 1), # Official Knight C | Mana Crystal
    (8610007, 2870466, 100, 1, 1), # Official Knight C | Official Knight C Familiar
    (8610007, 4021034, 100, 1, 1), # Official Knight C | Fine Spell Essence
    (8610007, 2590004, 100, 1, 1), # Official Knight C | Prototype Soul Enchanter
    (8610007, 2001554, 100, 1, 1), # Official Knight C | Sunset Dew
    (8610007, 4021033, 100, 1, 1), # Official Knight C | Basic Spell Essence
    (8610007, 4032926, 7500, 1, 1), # Official Knight C | Stan's Letter
    (8610007, 4000651, 7500, 1, 1), # Official Knight C | Knight Bandana
    (8610007, 2000036, 100, 1, 1), # Official Knight C | Reindeer Milk
    (8610007, 2070004, 100, 1, 1), # Official Knight C | Tobi Throwing-Stars
    (8610007, 2434021, 100, 1, 1), # Official Knight C | Medal of Honor
    (8610007, 4034983, 7500, 1, 1), # Official Knight C | Dream Fragment
    (8610007, 4034984, 7500, 1, 1), # Official Knight C | Dream Fragment
    (8610007, 4034985, 7500, 1, 1), # Official Knight C | Dream Fragment
    (8610007, 4034986, 7500, 1, 1), # Official Knight C | Dream Fragment
    (8610007, 4034987, 7500, 1, 1), # Official Knight C | Dream Fragment
    (8610007, 4034988, 7500, 1, 1), # Official Knight C | Dream Fragment
    (8610007, 4021035, 100, 1, 1), # Official Knight C | Grand Spell Essence
    (8610007, 4021020, 10, 1, 1), # Official Knight C | Confusion Fragment
    (8148009, 4021034, 100, 1, 1), # Elite Nightshadow | Fine Spell Essence
    (8148009, 2001554, 100, 1, 1), # Elite Nightshadow | Sunset Dew
    (8148009, 4021031, 50, 1, 1), # Elite Nightshadow | Twisted Time
    (8148009, 2000036, 100, 1, 1), # Elite Nightshadow | Reindeer Milk
    (8148009, 4021035, 100, 1, 1), # Elite Nightshadow | Grand Spell Essence
    (8148009, 2590004, 100, 1, 1), # Elite Nightshadow | Prototype Soul Enchanter
    (8148009, 2434021, 100, 1, 1), # Elite Nightshadow | Medal of Honor
    (8148009, 4021033, 100, 1, 1), # Elite Nightshadow | Basic Spell Essence
    (8148009, 4000902, 7500, 1, 1), # Elite Nightshadow | Predator Helm
    (8148009, 4021032, 7500, 1, 1), # Elite Nightshadow | Mana Crystal
    (8610006, 4021033, 100, 1, 1), # Official Knight B | Basic Spell Essence
    (8610006, 4032941, 7500, 1, 1), # Official Knight B | Cheery Book
    (8610006, 4034983, 7500, 1, 1), # Official Knight B | Dream Fragment
    (8610006, 4034984, 7500, 1, 1), # Official Knight B | Dream Fragment
    (8610006, 4034985, 7500, 1, 1), # Official Knight B | Dream Fragment
    (8610006, 4034986, 7500, 1, 1), # Official Knight B | Dream Fragment
    (8610006, 4034987, 7500, 1, 1), # Official Knight B | Dream Fragment
    (8610006, 4034988, 7500, 1, 1), # Official Knight B | Dream Fragment
    (8610006, 2049401, 100, 1, 1), # Official Knight B | Potential Scroll
    (8610006, 2434021, 100, 1, 1), # Official Knight B | Medal of Honor
    (8610006, 2512271, 100, 1, 1), # Official Knight B | Legendary Blessing Potion Recipe
    (8610006, 4021035, 100, 1, 1), # Official Knight B | Grand Spell Essence
    (8610006, 4021032, 7500, 1, 1), # Official Knight B | Mana Crystal
    (8610006, 2070009, 100, 1, 1), # Official Knight B | Wooden Top
    (8610006, 2512287, 100, 1, 1), # Official Knight B | Superior Luck Boost Potion Recipe
    (8610006, 2870465, 100, 1, 1), # Official Knight B | Official Knight B Familiar
    (8610006, 2512128, 100, 1, 1), # Official Knight B | Advanced Luck Potion X Recipe
    (8610006, 4000650, 7500, 1, 1), # Official Knight B | Knight Robe
    (8610006, 4021020, 10, 1, 1), # Official Knight B | Confusion Fragment
    (8610006, 2001554, 100, 1, 1), # Official Knight B | Sunset Dew
    (8610006, 2512175, 100, 1, 1), # Official Knight B | Advanced Dexterity Pill VII Recipe
    (8610006, 2512154, 100, 1, 1), # Official Knight B | Advanced Strength Pill VI Recipe
    (8610006, 2512099, 100, 1, 1), # Official Knight B | Advanced Dexterity Potion I Recipe
    (8610006, 2512270, 100, 1, 1), # Official Knight B | Exceptional Blessing Potion Recipe
    (8610006, 2512285, 100, 1, 1), # Official Knight B | Superior Dexterity Boost Potion Recipe
    (8610006, 2000036, 100, 1, 1), # Official Knight B | Reindeer Milk
    (8610006, 2512266, 100, 1, 1), # Official Knight B | Superior Hero Potion Recipe
    (8610006, 4021031, 50, 1, 1), # Official Knight B | Twisted Time
    (8610006, 2512283, 100, 1, 1), # Official Knight B | Superior Intelligence Boost Potion Recipe
    (8610006, 2590004, 100, 1, 1), # Official Knight B | Prototype Soul Enchanter
    (8610006, 2512137, 100, 1, 1), # Official Knight B | Advanced Magic Attack Potion IV Recipe
    (8610006, 4021034, 100, 1, 1), # Official Knight B | Fine Spell Essence
    (8148010, 4021035, 100, 1, 1), # Elite Scarlet Phoenix | Grand Spell Essence
    (8148010, 4021031, 50, 1, 1), # Elite Scarlet Phoenix | Twisted Time
    (8148010, 4021034, 100, 1, 1), # Elite Scarlet Phoenix | Fine Spell Essence
    (8148010, 2001554, 100, 1, 1), # Elite Scarlet Phoenix | Sunset Dew
    (8148010, 2000036, 100, 1, 1), # Elite Scarlet Phoenix | Reindeer Milk
    (8148010, 2590004, 100, 1, 1), # Elite Scarlet Phoenix | Prototype Soul Enchanter
    (8148010, 4021033, 100, 1, 1), # Elite Scarlet Phoenix | Basic Spell Essence
    (8148010, 2434021, 100, 1, 1), # Elite Scarlet Phoenix | Medal of Honor
    (8148010, 4000903, 7500, 1, 1), # Elite Scarlet Phoenix | Red Guard Helm
    (8148010, 4021032, 7500, 1, 1), # Elite Scarlet Phoenix | Mana Crystal
    (8610005, 2434021, 100, 1, 1), # Official Knight A | Medal of Honor
    (8610005, 4021035, 100, 1, 1), # Official Knight A | Grand Spell Essence
    (8610005, 2512099, 100, 1, 1), # Official Knight A | Advanced Dexterity Potion I Recipe
    (8610005, 2512154, 100, 1, 1), # Official Knight A | Advanced Strength Pill VI Recipe
    (8610005, 4034983, 7500, 1, 1), # Official Knight A | Dream Fragment
    (8610005, 4034984, 7500, 1, 1), # Official Knight A | Dream Fragment
    (8610005, 4034985, 7500, 1, 1), # Official Knight A | Dream Fragment
    (8610005, 4034986, 7500, 1, 1), # Official Knight A | Dream Fragment
    (8610005, 4034987, 7500, 1, 1), # Official Knight A | Dream Fragment
    (8610005, 4034988, 7500, 1, 1), # Official Knight A | Dream Fragment
    (8610005, 2000036, 100, 1, 1), # Official Knight A | Reindeer Milk
    (8610005, 2512128, 100, 1, 1), # Official Knight A | Advanced Luck Potion X Recipe
    (8610005, 2870464, 100, 1, 1), # Official Knight A | Official Knight A Familiar
    (8610005, 2512266, 100, 1, 1), # Official Knight A | Superior Hero Potion Recipe
    (8610005, 4021020, 10, 1, 1), # Official Knight A | Confusion Fragment
    (8610005, 2512175, 100, 1, 1), # Official Knight A | Advanced Dexterity Pill VII Recipe
    (8610005, 2512285, 100, 1, 1), # Official Knight A | Superior Dexterity Boost Potion Recipe
    (8610005, 2512271, 100, 1, 1), # Official Knight A | Legendary Blessing Potion Recipe
    (8610005, 4021034, 100, 1, 1), # Official Knight A | Fine Spell Essence
    (8610005, 2590004, 100, 1, 1), # Official Knight A | Prototype Soul Enchanter
    (8610005, 4021033, 100, 1, 1), # Official Knight A | Basic Spell Essence
    (8610005, 2512137, 100, 1, 1), # Official Knight A | Advanced Magic Attack Potion IV Recipe
    (8610005, 2512287, 100, 1, 1), # Official Knight A | Superior Luck Boost Potion Recipe
    (8610005, 4000649, 7500, 1, 1), # Official Knight A | Knight Mask
    (8610005, 2001554, 100, 1, 1), # Official Knight A | Sunset Dew
    (8610005, 4021031, 50, 1, 1), # Official Knight A | Twisted Time
    (8610005, 2049401, 100, 1, 1), # Official Knight A | Potential Scroll
    (8610005, 2070007, 100, 1, 1), # Official Knight A | Hwabi Throwing-Stars
    (8610005, 2330002, 100, 1, 1), # Official Knight A | Mighty Bullet
    (8610005, 4021032, 7500, 1, 1), # Official Knight A | Mana Crystal
    (8610005, 2330003, 100, 1, 1), # Official Knight A | Vital Bullet
    (8610005, 2512270, 100, 1, 1), # Official Knight A | Exceptional Blessing Potion Recipe
    (8610005, 2048003, 100, 1, 1), # Official Knight A | Scroll for Pet Equip. for Jump 100%
    (8610005, 2512283, 100, 1, 1), # Official Knight A | Superior Intelligence Boost Potion Recipe
    (8148011, 4021034, 100, 1, 1), # Elite Azure Ocelot | Fine Spell Essence
    (8148011, 4021035, 100, 1, 1), # Elite Azure Ocelot | Grand Spell Essence
    (8148011, 2434021, 100, 1, 1), # Elite Azure Ocelot | Medal of Honor
    (8148011, 4021032, 7500, 1, 1), # Elite Azure Ocelot | Mana Crystal
    (8148011, 4021033, 100, 1, 1), # Elite Azure Ocelot | Basic Spell Essence
    (8148011, 4000904, 7500, 1, 1), # Elite Azure Ocelot | Blue Guard Helm
    (8148011, 2590004, 100, 1, 1), # Elite Azure Ocelot | Prototype Soul Enchanter
    (8148011, 4021031, 50, 1, 1), # Elite Azure Ocelot | Twisted Time
    (8148011, 2000036, 100, 1, 1), # Elite Azure Ocelot | Reindeer Milk
    (8148011, 2001554, 100, 1, 1), # Elite Azure Ocelot | Sunset Dew
    (8610004, 2512285, 100, 1, 1), # Thunder | Superior Dexterity Boost Potion Recipe
    (8610004, 2048003, 100, 1, 1), # Thunder | Scroll for Pet Equip. for Jump 100%
    (8610004, 4034983, 7500, 1, 1), # Thunder | Dream Fragment
    (8610004, 4034984, 7500, 1, 1), # Thunder | Dream Fragment
    (8610004, 4034985, 7500, 1, 1), # Thunder | Dream Fragment
    (8610004, 4034986, 7500, 1, 1), # Thunder | Dream Fragment
    (8610004, 4034987, 7500, 1, 1), # Thunder | Dream Fragment
    (8610004, 4034988, 7500, 1, 1), # Thunder | Dream Fragment
    (8610004, 2510169, 100, 1, 1), # Thunder | Flame Throwing Star Recipe
    (8610004, 2000036, 100, 1, 1), # Thunder | Reindeer Milk
    (8610004, 2512271, 100, 1, 1), # Thunder | Legendary Blessing Potion Recipe
    (8610004, 2512128, 100, 1, 1), # Thunder | Advanced Luck Potion X Recipe
    (8610004, 2512283, 100, 1, 1), # Thunder | Superior Intelligence Boost Potion Recipe
    (8610004, 4021020, 10, 1, 1), # Thunder | Confusion Fragment
    (8610004, 4021032, 7500, 1, 1), # Thunder | Mana Crystal
    (8610004, 2590004, 100, 1, 1), # Thunder | Prototype Soul Enchanter
    (8610004, 2512137, 100, 1, 1), # Thunder | Advanced Magic Attack Potion IV Recipe
    (8610004, 2430200, 100, 1, 1), # Thunder | Thunder Stone
    (8610004, 4021035, 100, 1, 1), # Thunder | Grand Spell Essence
    (8610004, 2049401, 100, 1, 1), # Thunder | Potential Scroll
    (8610004, 2512154, 100, 1, 1), # Thunder | Advanced Strength Pill VI Recipe
    (8610004, 4021033, 100, 1, 1), # Thunder | Basic Spell Essence
    (8610004, 2512287, 100, 1, 1), # Thunder | Superior Luck Boost Potion Recipe
    (8610004, 2001554, 100, 1, 1), # Thunder | Sunset Dew
    (8610004, 2512270, 100, 1, 1), # Thunder | Exceptional Blessing Potion Recipe
    (8610004, 2512175, 100, 1, 1), # Thunder | Advanced Dexterity Pill VII Recipe
    (8610004, 2512266, 100, 1, 1), # Thunder | Superior Hero Potion Recipe
    (8610004, 2512099, 100, 1, 1), # Thunder | Advanced Dexterity Potion I Recipe
    (8610004, 4021031, 50, 1, 1), # Thunder | Twisted Time
    (8610004, 4021034, 100, 1, 1), # Thunder | Fine Spell Essence
    (8610004, 2434021, 100, 1, 1), # Thunder | Medal of Honor
    (8610011, 4021020, 10, 1, 1), # Advanced Knight B | Confusion Fragment
    (8610011, 4021035, 100, 1, 1), # Advanced Knight B | Grand Spell Essence
    (8610011, 2590004, 100, 1, 1), # Advanced Knight B | Prototype Soul Enchanter
    (8610011, 4021034, 100, 1, 1), # Advanced Knight B | Fine Spell Essence
    (8610011, 4021032, 7500, 1, 1), # Advanced Knight B | Mana Crystal
    (8610011, 4021031, 50, 1, 1), # Advanced Knight B | Twisted Time
    (8610011, 2434021, 100, 1, 1), # Advanced Knight B | Medal of Honor
    (8610011, 4000655, 7500, 1, 1), # Advanced Knight B | Blaze Pendant
    (8610011, 2001554, 100, 1, 1), # Advanced Knight B | Sunset Dew
    (8610011, 4034983, 7500, 1, 1), # Advanced Knight B | Dream Fragment
    (8610011, 4034984, 7500, 1, 1), # Advanced Knight B | Dream Fragment
    (8610011, 4034985, 7500, 1, 1), # Advanced Knight B | Dream Fragment
    (8610011, 2000036, 100, 1, 1), # Advanced Knight B | Reindeer Milk
    (8610011, 4021033, 100, 1, 1), # Advanced Knight B | Basic Spell Essence
    (8610011, 2870470, 100, 1, 1), # Advanced Knight B | Advanced Knight B Familiar
    (8148005, 4021032, 7500, 1, 1), # Elite Firebrand | Mana Crystal
    (8148005, 4000898, 7500, 1, 1), # Elite Firebrand | Fiery Jewel
    (8148005, 4021033, 100, 1, 1), # Elite Firebrand | Basic Spell Essence
    (8148005, 2000037, 100, 1, 1), # Elite Firebrand | Sunrise Dew
    (8148005, 4021031, 50, 1, 1), # Elite Firebrand | Twisted Time
    (8148005, 4021035, 100, 1, 1), # Elite Firebrand | Grand Spell Essence
    (8148005, 2431174, 100, 1, 1), # Elite Firebrand | Medal of Honor
    (8148005, 2000036, 100, 1, 1), # Elite Firebrand | Reindeer Milk
    (8148005, 4021034, 100, 1, 1), # Elite Firebrand | Fine Spell Essence
    (8148005, 2590004, 100, 1, 1), # Elite Firebrand | Prototype Soul Enchanter
    (8610010, 4021020, 10, 1, 1), # Advanced Knight A | Confusion Fragment
    (8610010, 2870469, 100, 1, 1), # Advanced Knight A | Advanced Knight A Familiar
    (8610010, 4034983, 7500, 1, 1), # Advanced Knight A | Dream Fragment
    (8610010, 4034984, 7500, 1, 1), # Advanced Knight A | Dream Fragment
    (8610010, 4034985, 7500, 1, 1), # Advanced Knight A | Dream Fragment
    (8610010, 4034986, 7500, 1, 1), # Advanced Knight A | Dream Fragment
    (8610010, 4034987, 7500, 1, 1), # Advanced Knight A | Dream Fragment
    (8610010, 4034988, 7500, 1, 1), # Advanced Knight A | Dream Fragment
    (8610010, 4021034, 100, 1, 1), # Advanced Knight A | Fine Spell Essence
    (8610010, 2000036, 100, 1, 1), # Advanced Knight A | Reindeer Milk
    (8610010, 2590004, 100, 1, 1), # Advanced Knight A | Prototype Soul Enchanter
    (8610010, 2434021, 100, 1, 1), # Advanced Knight A | Medal of Honor
    (8610010, 4021033, 100, 1, 1), # Advanced Knight A | Basic Spell Essence
    (8610010, 4021031, 50, 1, 1), # Advanced Knight A | Twisted Time
    (8610010, 4000654, 7500, 1, 1), # Advanced Knight A | Night Pendant
    (8610010, 4021035, 100, 1, 1), # Advanced Knight A | Grand Spell Essence
    (8610010, 2001554, 100, 1, 1), # Advanced Knight A | Sunset Dew
    (8610010, 4021032, 7500, 1, 1), # Advanced Knight A | Mana Crystal
    (8148006, 4021033, 100, 1, 1), # Elderwraith | Basic Spell Essence
    (8148006, 4021035, 100, 1, 1), # Elderwraith | Grand Spell Essence
    (8148006, 4032010, 7500, 1, 1), # Elderwraith | Elder Ashes
    (8148006, 4021031, 50, 1, 1), # Elderwraith | Twisted Time
    (8148006, 4021034, 100, 1, 1), # Elderwraith | Fine Spell Essence
    (8148006, 4021032, 7500, 1, 1), # Elderwraith | Mana Crystal
    (8148006, 4032011, 7500, 1, 1), # Elderwraith | Soiled Rags
    (8610009, 2590004, 100, 1, 1), # Official Knight E | Prototype Soul Enchanter
    (8610009, 4021034, 100, 1, 1), # Official Knight E | Fine Spell Essence
    (8610009, 4021032, 7500, 1, 1), # Official Knight E | Mana Crystal
    (8610009, 2070005, 100, 1, 1), # Official Knight E | Steely Throwing-Knives
    (8610009, 2000036, 100, 1, 1), # Official Knight E | Reindeer Milk
    (8610009, 2434021, 100, 1, 1), # Official Knight E | Medal of Honor
    (8610009, 4034983, 7500, 1, 1), # Official Knight E | Dream Fragment
    (8610009, 4034984, 7500, 1, 1), # Official Knight E | Dream Fragment
    (8610009, 4034985, 7500, 1, 1), # Official Knight E | Dream Fragment
    (8610009, 4034986, 7500, 1, 1), # Official Knight E | Dream Fragment
    (8610009, 4034987, 7500, 1, 1), # Official Knight E | Dream Fragment
    (8610009, 4034988, 7500, 1, 1), # Official Knight E | Dream Fragment
    (8610009, 2870468, 100, 1, 1), # Official Knight E | Official Knight E Familiar
    (8610009, 4021031, 50, 1, 1), # Official Knight E | Twisted Time
    (8610009, 4021033, 100, 1, 1), # Official Knight E | Basic Spell Essence
    (8610009, 4021035, 100, 1, 1), # Official Knight E | Grand Spell Essence
    (8610009, 4000653, 7500, 1, 1), # Official Knight E | Knight Pauldron
    (8610009, 4021020, 10, 1, 1), # Official Knight E | Confusion Fragment
    (8610009, 2001554, 100, 1, 1), # Official Knight E | Sunset Dew
    (8148007, 4021032, 7500, 1, 1), # Elite Windraider | Mana Crystal
    (8148007, 2000036, 100, 1, 1), # Elite Windraider | Reindeer Milk
    (8148007, 2001554, 100, 1, 1), # Elite Windraider | Sunset Dew
    (8148007, 4021035, 100, 1, 1), # Elite Windraider | Grand Spell Essence
    (8148007, 4021033, 100, 1, 1), # Elite Windraider | Basic Spell Essence
    (8148007, 4021031, 50, 1, 1), # Elite Windraider | Twisted Time
    (8148007, 2434021, 100, 1, 1), # Elite Windraider | Medal of Honor
    (8148007, 2590004, 100, 1, 1), # Elite Windraider | Prototype Soul Enchanter
    (8148007, 4000900, 7500, 1, 1), # Elite Windraider | Hunter's Mask
    (8148007, 4021034, 100, 1, 1), # Elite Windraider | Fine Spell Essence
    (8610008, 2000036, 100, 1, 1), # Official Knight D | Reindeer Milk
    (8610008, 4021031, 50, 1, 1), # Official Knight D | Twisted Time
    (8610008, 4021032, 7500, 1, 1), # Official Knight D | Mana Crystal
    (8610008, 4021034, 100, 1, 1), # Official Knight D | Fine Spell Essence
    (8610008, 2434021, 100, 1, 1), # Official Knight D | Medal of Honor
    (8610008, 4021020, 10, 1, 1), # Official Knight D | Confusion Fragment
    (8610008, 2001554, 100, 1, 1), # Official Knight D | Sunset Dew
    (8610008, 2870467, 100, 1, 1), # Official Knight D | Official Knight D Familiar
    (8610008, 2590004, 100, 1, 1), # Official Knight D | Prototype Soul Enchanter
    (8610008, 4000652, 7500, 1, 1), # Official Knight D | Knight Gloves
    (8610008, 4034983, 7500, 1, 1), # Official Knight D | Dream Fragment
    (8610008, 4034984, 7500, 1, 1), # Official Knight D | Dream Fragment
    (8610008, 4034985, 7500, 1, 1), # Official Knight D | Dream Fragment
    (8610008, 4034986, 7500, 1, 1), # Official Knight D | Dream Fragment
    (8610008, 4034987, 7500, 1, 1), # Official Knight D | Dream Fragment
    (8610008, 4034988, 7500, 1, 1), # Official Knight D | Dream Fragment
    (8610008, 4021035, 100, 1, 1), # Official Knight D | Grand Spell Essence
    (8610008, 4021033, 100, 1, 1), # Official Knight D | Basic Spell Essence
    (8148000, 4021031, 50, 1, 1), # Typhon | Twisted Time
    (8148000, 4021034, 100, 1, 1), # Typhon | Fine Spell Essence
    (8148000, 4021032, 7500, 1, 1), # Typhon | Mana Crystal
    (8148000, 4000895, 7500, 1, 1), # Typhon | Typhon Feather
    (8148000, 4021035, 100, 1, 1), # Typhon | Grand Spell Essence
    (8148000, 4021033, 100, 1, 1), # Typhon | Basic Spell Essence
    (8148001, 4021035, 100, 1, 1), # Elite Stormbreaker | Grand Spell Essence
    (8148001, 2431174, 100, 1, 1), # Elite Stormbreaker | Medal of Honor
    (8148001, 2000037, 100, 1, 1), # Elite Stormbreaker | Sunrise Dew
    (8148001, 4021034, 100, 1, 1), # Elite Stormbreaker | Fine Spell Essence
    (8148001, 2590004, 100, 1, 1), # Elite Stormbreaker | Prototype Soul Enchanter
    (8148001, 4000896, 7500, 1, 1), # Elite Stormbreaker | Thunder Spear
    (8148001, 2000036, 100, 1, 1), # Elite Stormbreaker | Reindeer Milk
    (8148001, 4021031, 50, 1, 1), # Elite Stormbreaker | Twisted Time
    (8148001, 4021033, 100, 1, 1), # Elite Stormbreaker | Basic Spell Essence
    (8148001, 4021032, 7500, 1, 1), # Elite Stormbreaker | Mana Crystal
    (8610014, 4021020, 10, 1, 1), # Advanced Knight E | Confusion Fragment
    (8610014, 4021034, 100, 1, 1), # Advanced Knight E | Fine Spell Essence
    (8610014, 2000036, 100, 1, 1), # Advanced Knight E | Reindeer Milk
    (8610014, 4021033, 100, 1, 1), # Advanced Knight E | Basic Spell Essence
    (8610014, 4032927, 7500, 1, 1), # Advanced Knight E | Ornate Sword
    (8610014, 2434021, 100, 1, 1), # Advanced Knight E | Medal of Honor
    (8610014, 4000658, 7500, 1, 1), # Advanced Knight E | Dawn Pendant
    (8610014, 2870473, 100, 1, 1), # Advanced Knight E | Advanced Knight E Familiar
    (8610014, 4034983, 7500, 1, 1), # Advanced Knight E | Dream Fragment
    (8610014, 4034984, 7500, 1, 1), # Advanced Knight E | Dream Fragment
    (8610014, 4034985, 7500, 1, 1), # Advanced Knight E | Dream Fragment
    (8610014, 4034986, 7500, 1, 1), # Advanced Knight E | Dream Fragment
    (8610014, 4034987, 7500, 1, 1), # Advanced Knight E | Dream Fragment
    (8610014, 4034988, 7500, 1, 1), # Advanced Knight E | Dream Fragment
    (8610014, 4021031, 50, 1, 1), # Advanced Knight E | Twisted Time
    (8610014, 4021035, 100, 1, 1), # Advanced Knight E | Grand Spell Essence
    (8610014, 4021032, 7500, 1, 1), # Advanced Knight E | Mana Crystal
    (8610014, 2001554, 100, 1, 1), # Advanced Knight E | Sunset Dew
    (8610014, 2590004, 100, 1, 1), # Advanced Knight E | Prototype Soul Enchanter
    (8610013, 2434021, 100, 1, 1), # Advanced Knight D | Medal of Honor
    (8610013, 4032928, 7500, 1, 1), # Advanced Knight D | Superior Bow
    (8610013, 4021031, 50, 1, 1), # Advanced Knight D | Twisted Time
    (8610013, 2590004, 100, 1, 1), # Advanced Knight D | Prototype Soul Enchanter
    (8610013, 4021020, 10, 1, 1), # Advanced Knight D | Confusion Fragment
    (8610013, 4034983, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4034984, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4034985, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4034986, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4034987, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4034988, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4000657, 7500, 1, 1), # Advanced Knight D | Wind Pendant
    (8610013, 2000036, 100, 1, 1), # Advanced Knight D | Reindeer Milk
    (8610013, 4021035, 100, 1, 1), # Advanced Knight D | Grand Spell Essence
    (8610013, 4021033, 100, 1, 1), # Advanced Knight D | Basic Spell Essence
    (8610013, 4021032, 7500, 1, 1), # Advanced Knight D | Mana Crystal
    (8610013, 2001554, 100, 1, 1), # Advanced Knight D | Sunset Dew
    (8610013, 2870472, 100, 1, 1), # Advanced Knight D | Advanced Knight D Familiar
    (8610013, 4021034, 100, 1, 1), # Advanced Knight D | Fine Spell Essence
    (8610012, 4021031, 50, 1, 1), # Advanced Knight C | Twisted Time
    (8610012, 2000036, 100, 1, 1), # Advanced Knight C | Reindeer Milk
    (8610012, 4021035, 100, 1, 1), # Advanced Knight C | Grand Spell Essence
    (8610012, 2590004, 100, 1, 1), # Advanced Knight C | Prototype Soul Enchanter
    (8610012, 4021020, 10, 1, 1), # Advanced Knight C | Confusion Fragment
    (8610012, 4021034, 100, 1, 1), # Advanced Knight C | Fine Spell Essence
    (8610012, 2434021, 100, 1, 1), # Advanced Knight C | Medal of Honor
    (8610012, 4021033, 100, 1, 1), # Advanced Knight C | Basic Spell Essence
    (8610012, 4000656, 7500, 1, 1), # Advanced Knight C | Thunder Pendant
    (8610012, 4034983, 7500, 1, 1), # Advanced Knight C | Dream Fragment
    (8610012, 4034984, 7500, 1, 1), # Advanced Knight C | Dream Fragment
    (8610012, 4034985, 7500, 1, 1), # Advanced Knight C | Dream Fragment
    (8610013, 4034986, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4034987, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610013, 4034988, 7500, 1, 1), # Advanced Knight D | Dream Fragment
    (8610012, 2001554, 100, 1, 1), # Advanced Knight C | Sunset Dew
    (8610012, 4021032, 7500, 1, 1), # Advanced Knight C | Mana Crystal
    (8610012, 2870471, 100, 1, 1), # Advanced Knight C | Advanced Knight C Familiar
    (8870104, 4021032, 7500, 1, 1), # Bloodfang | Mana Crystal
    (8870104, 4021033, 100, 1, 1), # Bloodfang | Basic Spell Essence
    (8870104, 4021031, 50, 1, 1), # Bloodfang | Twisted Time
    (8870104, 4021034, 100, 1, 1), # Bloodfang | Fine Spell Essence
    (8870104, 4021035, 100, 1, 1), # Bloodfang | Grand Spell Essence
    (2600007, 2512256, 100, 1, 1), # Griffey | Advanced Magic Attack Pill III Recipe
    (2600007, 2061000, 100, 1, 1), # Griffey | Arrow for Crossbow
    (2600007, 2044112, 100, 1, 1), # Griffey | Scroll for Two-Handed Axe for Accuracy 60%
    (2600007, 2040802, 100, 1, 1), # Griffey | Scroll for Gloves for DEX 10%
    (2600007, 2022057, 100, 1, 1), # Griffey | Tofu
    (2600007, 2512274, 100, 1, 1), # Griffey | Exceptional Concentration Potion Recipe
    (2600007, 2510037, 100, 1, 1), # Griffey | Reverse Taragon Recipe
    (2600007, 4000243, 7500, 1, 1), # Griffey | Griffey Horn
    (2600007, 2049100, 100, 1, 1), # Griffey | Chaos Scroll 60%
    (2600007, 2043114, 100, 1, 1), # Griffey | Scroll for One-Handed Axe for Accuracy 10%
    (2600007, 2512040, 100, 1, 1), # Griffey | Magic Attack Potion IV Recipe
    (2600007, 2040516, 100, 1, 1), # Griffey | Scroll for Overall Armor for LUK 60%
    (2600007, 2510427, 100, 1, 1), # Griffey | Reverse Peony Recipe
    (2600007, 2040902, 100, 1, 1), # Griffey | Scroll for Shield for DEF 10%
    (2600007, 2040002, 100, 1, 1), # Griffey | Scroll for Helmet for DEF 10%
    (2600007, 2040621, 100, 1, 1), # Griffey | Scroll for Bottomwear for HP 60%
    (2600007, 2040901, 100, 1, 1), # Griffey | Scroll for Shield for DEF 60%
    (2600007, 2044801, 100, 1, 1), # Griffey | Scroll for Knuckle for Attack 60%
    (2600007, 2512272, 100, 1, 1), # Griffey | Intermediate Concentration Potion Recipe
    (2600007, 2870346, 100, 1, 1), # Griffey | Griffey Familiar
    (2600007, 4004002, 7500, 1, 1), # Griffey | DEX Crystal Ore
    (2600007, 2040532, 100, 1, 1), # Griffey | Scroll for Overall for STR 60%
    (2600007, 2043214, 100, 1, 1), # Griffey | Scroll for One-Handed BW for Accuracy 10%
    (2600007, 2040925, 100, 1, 1), # Griffey | Scroll for Shield for LUK 10%
    (2600007, 2512283, 100, 1, 1), # Griffey | Superior Intelligence Boost Potion Recipe
    (2600007, 2510272, 100, 1, 1), # Griffey | Abyss Taragon Recipe
    (2600007, 2040602, 100, 1, 1), # Griffey | Scroll for Bottomwear for DEF 10%
    (2600007, 2510294, 100, 1, 1); # Griffey | Abyss Aeas Hand Recipe

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (2600007, 4000244, 7500, 1, 1), # Griffey | Dragon Spirit
    (2600007, 2512280, 100, 1, 1), # Griffey | Legendary Insight Potion Recipe
    (2600007, 40302, 100, 1, 1), # Griffey | Orange Smoky Wild Hair
    (2600007, 2510133, 100, 1, 1), # Griffey | Reverse Aeas Hand Recipe
    (2600007, 2040709, 100, 1, 1), # Griffey | Scroll for Shoes for DEX 100%
    (2600007, 4000245, 7500, 1, 1), # Griffey | Dragon Scale
    (2600007, 2001527, 100, 1, 1), # Griffey | Unagi
    (2600007, 4004003, 7500, 1, 1), # Griffey | LUK Crystal Ore
    (2600007, 2040513, 100, 1, 1), # Griffey | Scroll for Overall Armor for INT 60%
    (2600007, 2512271, 100, 1, 1), # Griffey | Legendary Blessing Potion Recipe
    (2600007, 2000037, 100, 1, 1), # Griffey | Sunrise Dew
    (2600007, 2040618, 100, 1, 1), # Griffey | Scroll for Bottomwear for Jump 60%
    (2600007, 2040005, 100, 1, 1), # Griffey | Scroll for Helmet for HP 10%
    (2600007, 2511101, 100, 1, 1), # Griffey | Nimble Hound Earrings Recipe
    (2600007, 2043701, 100, 1, 1), # Griffey | Scroll for Wand for Magic Attack 60%
    (2600007, 2512048, 100, 1, 1), # Griffey | Special Griffey Transform Potion Recipe
    (2600007, 2040505, 100, 1, 1), # Griffey | Scroll for Overall Armor for DEF 10%
    (2600007, 2512287, 100, 1, 1), # Griffey | Superior Luck Boost Potion Recipe
    (2600007, 4004001, 7500, 1, 1), # Griffey | Wisdom Crystal Ore
    (2600007, 2040402, 100, 1, 1), # Griffey | Scroll for Topwear for DEF 10%
    (2600007, 4004000, 7500, 1, 1), # Griffey | Power Crystal Ore
    (2600007, 2512112, 100, 1, 1), # Griffey | Advanced Intelligence Potion IV Recipe
    (2600007, 4001402, 7500, 1, 1), # Griffey | Dragon's Essence
    (2600007, 2001002, 100, 1, 1), # Griffey | Very Special Sundae
    (2600007, 2040502, 100, 1, 1), # Griffey | Scroll for Overall Armor for DEX 10%
    (2600007, 2510394, 100, 1, 1), # Griffey | Dragonic Nageling Recipe
    (2600007, 2511027, 100, 1, 1), # Griffey | Tenacious Exquisite Belt Recipe
    (2600007, 2510476, 100, 1, 1), # Griffey | Abyss Persona Recipe
    (2600007, 2511052, 100, 1, 1), # Griffey | Pink Wolf Pendant Recipe
    (2600006, 2510247, 100, 1, 1), # Green Hobi | Pure Gold Transistor Recipe
    (2600006, 4007005, 7500, 1, 1), # Green Hobi | Magic Powder (Purple)
    (2600006, 2510272, 100, 1, 1), # Green Hobi | Abyss Taragon Recipe
    (2600006, 4000261, 7500, 1, 1), # Green Hobi | Pin Hov's Charm
    (2600006, 2512209, 100, 1, 1), # Green Hobi | Advanced Luck Pill I Recipe
    (2600006, 4004003, 7500, 1, 1), # Green Hobi | LUK Crystal Ore
    (2600006, 2040824, 100, 1, 1), # Green Hobi | Scroll for Gloves for HP 60%
    (2600006, 2060000, 100, 1, 1), # Green Hobi | Arrow for Bow
    (2600006, 2510396, 100, 1, 1), # Green Hobi | Dragonic Dragon Katara Recipe
    (2600006, 2001001, 100, 1, 1), # Green Hobi | Ice Cream Pop
    (2600006, 2510037, 100, 1, 1), # Green Hobi | Reverse Taragon Recipe
    (2600006, 4020001, 7500, 1, 1), # Green Hobi | Amethyst Ore
    (2600006, 2512135, 100, 1, 1), # Green Hobi | Advanced Magic Attack Potion II Recipe
    (2600006, 2512285, 100, 1, 1), # Green Hobi | Superior Dexterity Boost Potion Recipe
    (2600006, 2040621, 100, 1, 1), # Green Hobi | Scroll for Bottomwear for HP 60%
    (2600006, 4006000, 7500, 1, 1), # Green Hobi | The Magic Rock
    (2600006, 2512287, 100, 1, 1), # Green Hobi | Superior Luck Boost Potion Recipe
    (2600006, 4007007, 7500, 1, 1), # Green Hobi | Magic Powder (Black)
    (2600006, 2330002, 100, 1, 1), # Green Hobi | Mighty Bullet
    (2600006, 2001526, 100, 1, 1), # Green Hobi | Pure Water
    (2600006, 2870292, 100, 1, 1), # Green Hobi | Green Hobi Familiar
    (2600006, 2044802, 100, 1, 1), # Green Hobi | Scroll for Knuckle for ATT 10%
    (2600006, 2512274, 100, 1, 1), # Green Hobi | Exceptional Concentration Potion Recipe
    (2600006, 4020007, 7500, 1, 1), # Green Hobi | Diamond Ore
    (2600006, 2044201, 100, 1, 1), # Green Hobi | Scroll for Two-handed BW for ATT 60%
    (2600006, 2000037, 100, 1, 1), # Green Hobi | Sunrise Dew
    (2600006, 2512276, 100, 1, 1), # Green Hobi | Exceptional Fortitude Potion Recipe
    (2600006, 2061000, 100, 1, 1), # Green Hobi | Arrow for Crossbow
    (2600006, 2510427, 100, 1, 1), # Green Hobi | Reverse Peony Recipe
    (2600006, 2044602, 100, 1, 1), # Green Hobi | Scroll for Crossbow for ATT 10%
    (6400000, 2590004, 100, 1, 1), # Dark Yeti | Prototype Soul Enchanter
    (6400000, 4021032, 7500, 1, 1), # Dark Yeti | Mana Crystal
    (6400000, 4021034, 100, 1, 1), # Dark Yeti | Fine Spell Essence
    (6400000, 2001528, 100, 1, 1), # Dark Yeti | Melting Cheese
    (6400000, 2434021, 100, 1, 1), # Dark Yeti | Medal of Honor
    (6400000, 4021035, 100, 1, 1), # Dark Yeti | Grand Spell Essence
    (6400000, 2000037, 100, 1, 1), # Dark Yeti | Sunrise Dew
    (6400000, 4000056, 7500, 1, 1), # Dark Yeti | Dark Yeti Horn
    (6400000, 2870264, 100, 1, 1), # Dark Yeti | Dark Yeti Familiar
    (6400000, 4021031, 50, 1, 1), # Dark Yeti | Twisted Time
    (6400000, 4021033, 100, 1, 1), # Dark Yeti | Basic Spell Essence
    (2600005, 2512266, 100, 1, 1), # Hobi | Superior Hero Potion Recipe
    (2600005, 2512244, 100, 1, 1), # Hobi | Advanced Attack Pill I Recipe
    (2600005, 4000260, 7500, 1, 1), # Hobi | Hov's Shorts
    (2600005, 2044412, 100, 1, 1), # Hobi | Scroll for Pole-Arm for Accuracy 60%
    (2600005, 2000037, 100, 1, 1), # Hobi | Sunrise Dew
    (2600005, 4010001, 7500, 1, 1), # Hobi | Steel Ore
    (2600005, 2512041, 100, 1, 1), # Hobi | Magic Attack Potion V Recipe
    (2600005, 2512283, 100, 1, 1), # Hobi | Superior Intelligence Boost Potion Recipe
    (2600005, 2043401, 100, 1, 1), # Hobi | Scroll for Katara for ATT 60%
    (2600005, 2512126, 100, 1, 1), # Hobi | Advanced Luck Potion VIII Recipe
    (2600005, 4007000, 7500, 1, 1), # Hobi | Magic Powder (Brown)
    (2600005, 2000005, 100, 1, 1), # Hobi | Power Elixir
    (2600005, 4007006, 7500, 1, 1), # Hobi | Magic Powder (Red)
    (2600005, 2512194, 100, 1, 1), # Hobi | Advanced Intelligence Pill VI Recipe
    (2600005, 2061000, 100, 1, 1), # Hobi | Arrow for Crossbow
    (2600005, 2044601, 100, 1, 1), # Hobi | Scroll for Crossbow for ATT 60%
    (2600005, 2870291, 100, 1, 1), # Hobi | Hobi Familiar
    (2600005, 2000004, 100, 1, 1), # Hobi | Elixir
    (2600005, 2022057, 100, 1, 1), # Hobi | Tofu
    (2600005, 2512280, 100, 1, 1), # Hobi | Legendary Insight Potion Recipe
    (2600005, 4010000, 7500, 1, 1), # Hobi | Bronze Ore
    (2600005, 4004002, 7500, 1, 1), # Hobi | DEX Crystal Ore
    (2600005, 4006001, 7500, 1, 1), # Hobi | The Summoning Rock
    (2600005, 2512276, 100, 1, 1), # Hobi | Exceptional Fortitude Potion Recipe
    (6400003, 2870265, 100, 1, 1), # Cuzco | Cuzco Familiar
    (2600004, 4001402, 7500, 1, 1), # Dark Rash | Dragon's Essence
    (2600004, 2870290, 100, 1, 1), # Dark Rash | Dark Rash Familiar
    (2600004, 4004002, 7500, 1, 1), # Dark Rash | DEX Crystal Ore
    (2600004, 4000229, 7500, 1, 1), # Dark Rash | Dark Rash's Furball
    (2600004, 2043301, 100, 1, 1), # Dark Rash | Scroll for Dagger for ATT 60%
    (2600004, 2001526, 100, 1, 1), # Dark Rash | Pure Water
    (2600004, 4020002, 7500, 1, 1), # Dark Rash | AquaMarine Ore
    (2600004, 4010002, 7500, 1, 1), # Dark Rash | Mithril Ore
    (2600004, 2512214, 100, 1, 1), # Dark Rash | Advanced Luck Pill VI Recipe
    (2600004, 4007002, 7500, 1, 1), # Dark Rash | Magic Powder (Blue)
    (2600004, 2511075, 100, 1, 1), # Dark Rash | Strength Ring VII Recipe
    (2600004, 2510299, 100, 1, 1), # Dark Rash | Abyss Diesra Recipe
    (2600004, 2512149, 100, 1, 1), # Dark Rash | Advanced Strength Pill I Recipe
    (2600004, 2512275, 100, 1, 1), # Dark Rash | Superior Fortitude Potion Recipe
    (2600004, 2060000, 100, 1, 1), # Dark Rash | Arrow for Bow
    (2600004, 2000037, 100, 1, 1), # Dark Rash | Sunrise Dew
    (2600004, 2022057, 100, 1, 1), # Dark Rash | Tofu
    (2600004, 2512288, 100, 1, 1), # Dark Rash | Exceptional Luck Boost Potion Recipe
    (2600004, 2001527, 100, 1, 1), # Dark Rash | Unagi
    (2600004, 4007003, 7500, 1, 1), # Dark Rash | Magic Powder (Green)
    (2600004, 2512287, 100, 1, 1), # Dark Rash | Superior Luck Boost Potion Recipe
    (2600004, 2512278, 100, 1, 1), # Dark Rash | Superior Insight Potion Recipe
    (2600004, 2000005, 100, 1, 1), # Dark Rash | Power Elixir
    (2600004, 4006001, 7500, 1, 1), # Dark Rash | The Summoning Rock
    (2600004, 2043002, 100, 1, 1), # Dark Rash | Scroll for One-Handed Sword for ATT 10%
    (2600004, 2001002, 100, 1, 1), # Dark Rash | Very Special Sundae
    (2600003, 4010001, 7500, 1, 1), # Rash | Steel Ore
    (2600003, 4004000, 7500, 1, 1), # Rash | Power Crystal Ore
    (2600003, 2043701, 100, 1, 1), # Rash | Scroll for Wand for Magic Attack 60%
    (2600003, 2040705, 100, 1, 1), # Rash | Scroll for Shoes for Jump 10%
    (2600003, 4020000, 7500, 1, 1), # Rash | Garnet Ore
    (2600003, 2511018, 100, 1, 1), # Rash | Shiny Blue Pirate Symbol Recipe
    (2600003, 4006001, 7500, 1, 1), # Rash | The Summoning Rock
    (2600003, 2000037, 100, 1, 1), # Rash | Sunrise Dew
    (2600003, 4000227, 7500, 1, 1), # Rash | Tree Fruit
    (2600003, 4007007, 7500, 1, 1), # Rash | Magic Powder (Black)
    (2600003, 2060000, 100, 1, 1), # Rash | Arrow for Bow
    (2600003, 2048003, 100, 1, 1), # Rash | Scroll for Pet Equip. for Jump 100%
    (2600003, 4000228, 7500, 1, 1), # Rash | Anesthetic Powder
    (2600003, 4007000, 7500, 1, 1), # Rash | Magic Powder (Brown)
    (2600003, 2000005, 100, 1, 1), # Rash | Power Elixir
    (2600003, 2870289, 100, 1, 1), # Rash | Rash Familiar
    (2600003, 2044901, 100, 1, 1), # Rash | Scroll for Gun for Attack 60%
    (2600003, 4000226, 7500, 1, 1), # Rash | Rash's Furball
    (2600003, 2001001, 100, 1, 1), # Rash | Ice Cream Pop
    (2600003, 2022057, 100, 1, 1), # Rash | Tofu
    (2600003, 2001528, 100, 1, 1), # Rash | Melting Cheese
    (2220100, 4000009, 7500, 1, 1), # Blue Mushroom | Blue Mushroom Cap
    (2220100, 4010006, 7500, 1, 1), # Blue Mushroom | Gold Ore
    (2220100, 2044002, 100, 1, 1), # Blue Mushroom | Scroll for Two-handed Sword for ATT 10%
    (2220100, 2060000, 100, 1, 1), # Blue Mushroom | Arrow for Bow
    (2220100, 2000003, 100, 1, 1), # Blue Mushroom | Blue Potion
    (2220100, 2870043, 100, 1, 1), # Blue Mushroom | Blue Mushroom Familiar
    (2220100, 2022016, 100, 1, 1), # Blue Mushroom | Maple Special Bento
    (2220100, 4020005, 7500, 1, 1), # Blue Mushroom | Sapphire Ore
    (2220100, 2061000, 100, 1, 1), # Blue Mushroom | Arrow for Crossbow
    (2220100, 4020006, 7500, 1, 1), # Blue Mushroom | Topaz Ore
    (2600002, 4010004, 7500, 1, 1), # Hankie | Silver Ore
    (2600002, 2044101, 100, 1, 1), # Hankie | Scroll for Two-handed Axe for ATT 60%
    (2600002, 4004004, 7500, 1, 1), # Hankie | Dark Crystal Ore
    (2600002, 4007003, 7500, 1, 1), # Hankie | Magic Powder (Green)
    (2600002, 2001527, 100, 1, 1), # Hankie | Unagi
    (2600002, 2510011, 100, 1, 1), # Hankie | Reverse Coral Recipe
    (2600002, 2001002, 100, 1, 1), # Hankie | Very Special Sundae
    (2600002, 2044314, 100, 1, 1), # Hankie | Scroll for Spear for Accuracy 10%
    (2600002, 4020004, 7500, 1, 1), # Hankie | Opal Ore
    (2600002, 2870276, 100, 1, 1), # Hankie | Hankie Familiar
    (2600002, 2049100, 100, 1, 1), # Hankie | Chaos Scroll 60%
    (2600002, 2070010, 100, 1, 1), # Hankie | Icicle
    (2600002, 2510479, 100, 1, 1), # Hankie | Celestial Cane Recipe
    (2600002, 4006000, 7500, 1, 1), # Hankie | The Magic Rock
    (2600002, 2512094, 100, 1, 1), # Hankie | Advanced Strength Potion VI Recipe
    (2600002, 2510403, 100, 1, 1), # Hankie | Dragonic Bellum Spear Recipe
    (2600002, 4000231, 7500, 1, 1), # Hankie | Hankie's Pan Flute
    (2600002, 2044301, 100, 1, 1), # Hankie | Scroll for Spear for ATT 60%
    (2600002, 4007005, 7500, 1, 1), # Hankie | Magic Powder (Purple)
    (2600002, 2512112, 100, 1, 1), # Hankie | Advanced Intelligence Potion IV Recipe
    (2600002, 2048004, 100, 1, 1), # Hankie | Scroll for Pet Equip. for Jump 60%
    (2600002, 2000006, 100, 1, 1), # Hankie | Mana Elixir
    (2600002, 2510268, 100, 1, 1), # Hankie | Abyss Coral Recipe
    (2600002, 2512288, 100, 1, 1), # Hankie | Exceptional Luck Boost Potion Recipe
    (2600002, 2512110, 100, 1, 1), # Hankie | Advanced Intelligence Potion II Recipe
    (2600002, 2061000, 100, 1, 1), # Hankie | Arrow for Crossbow
    (2600002, 2512274, 100, 1, 1), # Hankie | Exceptional Concentration Potion Recipe
    (6400004, 2870266, 100, 1, 1), # Opachu | Opachu Familiar
    (2600001, 2060000, 100, 1, 1), # Dual Beetle | Arrow for Bow
    (2600001, 2512286, 100, 1, 1), # Dual Beetle | Exceptional Dexterity Boost Potion Recipe
    (2600001, 2510038, 100, 1, 1), # Dual Beetle | Reverse Evernew Recipe
    (2600001, 2870275, 100, 1, 1), # Dual Beetle | Dual Beetle Familiar
    (2600001, 4007007, 7500, 1, 1), # Dual Beetle | Magic Powder (Black)
    (2600001, 4001402, 7500, 1, 1), # Dual Beetle | Dragon's Essence
    (2600001, 4004003, 7500, 1, 1), # Dual Beetle | LUK Crystal Ore
    (2600001, 2512198, 100, 1, 1), # Dual Beetle | Advanced Intelligence Pill X Recipe
    (2600001, 2044901, 100, 1, 1), # Dual Beetle | Scroll for Gun for Attack 60%
    (2600001, 2000005, 100, 1, 1), # Dual Beetle | Power Elixir
    (2600001, 4007000, 7500, 1, 1), # Dual Beetle | Magic Powder (Brown)
    (2600001, 2044801, 100, 1, 1), # Dual Beetle | Scroll for Knuckle for Attack 60%
    (2600001, 2022057, 100, 1, 1), # Dual Beetle | Tofu
    (2600001, 2001526, 100, 1, 1), # Dual Beetle | Pure Water
    (2600001, 4020003, 7500, 1, 1), # Dual Beetle | Emerald Ore
    (2600001, 2044803, 100, 1, 1), # Dual Beetle | Scroll for Knuckle for Attack 70%
    (2600001, 4006000, 7500, 1, 1), # Dual Beetle | The Magic Rock
    (2600001, 2000037, 100, 1, 1), # Dual Beetle | Sunrise Dew
    (2600001, 4010003, 7500, 1, 1), # Dual Beetle | Adamantium Ore
    (2600001, 2061000, 100, 1, 1), # Dual Beetle | Arrow for Crossbow
    (2600001, 2510245, 100, 1, 1), # Dual Beetle | Pure Gold Engine Leg Recipe
    (2600001, 2044501, 100, 1, 1), # Dual Beetle | Scroll for Bow for ATT 60%
    (2600001, 2510009, 100, 1, 1), # Dual Beetle | Canopus Hat Recipe
    (2600001, 2510398, 100, 1, 1), # Dual Beetle | Dragonic Machlear Recipe
    (6400007, 2870267, 100, 1, 1), # Baby Balrog | Baby Balrog Familiar
    (2600000, 2330004, 100, 1, 1), # Beetle | Shiny Bullet
    (2600000, 4007007, 7500, 1, 1), # Beetle | Magic Powder (Black)
    (2600000, 2001002, 100, 1, 1), # Beetle | Very Special Sundae
    (2600000, 4004001, 7500, 1, 1), # Beetle | Wisdom Crystal Ore
    (2600000, 2870274, 100, 1, 1), # Beetle | Beetle Familiar
    (2600000, 4000236, 7500, 1, 1), # Beetle | Beetle's Horn
    (2600000, 4010000, 7500, 1, 1), # Beetle | Bronze Ore
    (2600000, 2061000, 100, 1, 1), # Beetle | Arrow for Crossbow
    (2600000, 4007006, 7500, 1, 1), # Beetle | Magic Powder (Red)
    (2600000, 2048003, 100, 1, 1), # Beetle | Scroll for Pet Equip. for Jump 100%
    (2600000, 2022057, 100, 1, 1), # Beetle | Tofu
    (2600000, 2043017, 100, 1, 1), # Beetle | Scroll for One-Handed Sword for Accuracy 60%
    (2600000, 4020001, 7500, 1, 1), # Beetle | Amethyst Ore
    (2600000, 4001402, 7500, 1, 1), # Beetle | Dragon's Essence
    (2600000, 2510141, 100, 1, 1), # Beetle | Reverse Lampion Recipe
    (2600000, 2000037, 100, 1, 1), # Beetle | Sunrise Dew
    (2600000, 2000005, 100, 1, 1), # Beetle | Power Elixir
    (2600000, 2044212, 100, 1, 1), # Beetle | Scroll for Two-Handed BW for Accuracy 60%
    (2600000, 2330002, 100, 1, 1), # Beetle | Mighty Bullet
    (2600000, 4006000, 7500, 1, 1), # Beetle | The Magic Rock
    (2600000, 2044014, 100, 1, 1), # Beetle | Scroll for Two-Handed Sword for Accuracy 10%
    (2600015, 2000006, 100, 1, 1), # Kentaurus King | Mana Elixir
    (2600015, 2871092, 100, 1, 1), # Kentaurus King | Kentaurus King Familiar
    (2600015, 2070005, 100, 1, 1), # Kentaurus King | Steely Throwing-Knives
    (2600015, 4020001, 7500, 1, 1), # Kentaurus King | Amethyst Ore
    (2600015, 2000037, 100, 1, 1), # Kentaurus King | Sunrise Dew
    (2600015, 2070007, 100, 1, 1), # Kentaurus King | Hwabi Throwing-Stars
    (2600015, 2330004, 100, 1, 1), # Kentaurus King | Shiny Bullet
    (2600015, 4020007, 7500, 1, 1), # Kentaurus King | Diamond Ore
    (2600015, 2070010, 100, 1, 1), # Kentaurus King | Icicle
    (2600015, 4010005, 7500, 1, 1), # Kentaurus King | Orihalcon Ore
    (2600015, 2001001, 100, 1, 1), # Kentaurus King | Ice Cream Pop
    (2600015, 2330005, 100, 1, 1), # Kentaurus King | Eternal Bullet
    (2600015, 4000234, 7500, 1, 1), # Kentaurus King | Kentaurus's Skull
    (2600015, 2001526, 100, 1, 1), # Kentaurus King | Pure Water
    (2600015, 2330002, 100, 1, 1), # Kentaurus King | Mighty Bullet
    (2600015, 2330003, 100, 1, 1), # Kentaurus King | Vital Bullet
    (2600015, 2000005, 100, 1, 1), # Kentaurus King | Power Elixir
    (2600015, 4000232, 7500, 1, 1), # Kentaurus King | Kentaurus's Flame
    (2600015, 4020005, 7500, 1, 1), # Kentaurus King | Sapphire Ore
    (2600015, 2070003, 100, 1, 1), # Kentaurus King | Kumbi Throwing-Stars
    (2600015, 4000233, 7500, 1, 1), # Kentaurus King | Kentaurus's Marrow
    (2600015, 2070004, 100, 1, 1), # Kentaurus King | Tobi Throwing-Stars
    (2600015, 2001002, 100, 1, 1), # Kentaurus King | Very Special Sundae
    (2600015, 4020002, 7500, 1, 1), # Kentaurus King | AquaMarine Ore
    (2600014, 2870320, 100, 1, 1), # Dual Birk | Dual Birk Familiar
    (2600014, 2070006, 100, 1, 1), # Dual Birk | Ilbi Throwing-Stars
    (2600014, 4010003, 7500, 1, 1), # Dual Birk | Adamantium Ore
    (2600014, 2040323, 100, 1, 1), # Dual Birk | Scroll for Earring for LUK 10%
    (2600014, 4007000, 7500, 1, 1), # Dual Birk | Magic Powder (Brown)
    (2600014, 4020003, 7500, 1, 1), # Dual Birk | Emerald Ore
    (2600014, 2040610, 100, 1, 1), # Dual Birk | Dark Scroll for Bottomwear for DEX 70%
    (2600014, 2001526, 100, 1, 1), # Dual Birk | Pure Water
    (2600014, 4000242, 7500, 1, 1), # Dual Birk | Dual Birk's Tiny Tail
    (2600014, 2022057, 100, 1, 1), # Dual Birk | Tofu
    (2600014, 2000037, 100, 1, 1), # Dual Birk | Sunrise Dew
    (2600014, 2043214, 100, 1, 1), # Dual Birk | Scroll for One-Handed BW for Accuracy 10%
    (2600014, 2043401, 100, 1, 1), # Dual Birk | Scroll for Katara for ATT 60%
    (2600014, 4007002, 7500, 1, 1), # Dual Birk | Magic Powder (Blue)
    (2600014, 4004003, 7500, 1, 1), # Dual Birk | LUK Crystal Ore
    (2600014, 4006001, 7500, 1, 1), # Dual Birk | The Summoning Rock
    (2600014, 2048000, 100, 1, 1), # Dual Birk | Scroll for Pet Equip. for Speed 100%
    (2600014, 2040801, 100, 1, 1), # Dual Birk | Scroll for Gloves for DEX 60%
    (2600013, 4020008, 7500, 1, 1), # Birk | Black Crystal Ore
    (2600013, 2044702, 100, 1, 1), # Birk | Scroll for Claw for ATT 10%
    (2600013, 4000241, 7500, 1, 1), # Birk | Birk's Chewed Grass
    (2600013, 4004002, 7500, 1, 1), # Birk | DEX Crystal Ore
    (2600013, 4006000, 7500, 1, 1), # Birk | The Magic Rock
    (2600013, 2044601, 100, 1, 1), # Birk | Scroll for Crossbow for ATT 60%
    (2600013, 2043802, 100, 1, 1), # Birk | Scroll for Staff for Magic Attack 10%
    (2600013, 2040924, 100, 1, 1), # Birk | Scroll for Shield for LUK 60%
    (2600013, 2061000, 100, 1, 1), # Birk | Arrow for Crossbow
    (2600013, 4010006, 7500, 1, 1), # Birk | Gold Ore
    (2600013, 4007003, 7500, 1, 1), # Birk | Magic Powder (Green)
    (2600013, 2022057, 100, 1, 1), # Birk | Tofu
    (2600013, 2000005, 100, 1, 1), # Birk | Power Elixir
    (2600013, 2870319, 100, 1, 1), # Birk | Birk Familiar
    (2600013, 2000037, 100, 1, 1), # Birk | Sunrise Dew
    (2600013, 2044101, 100, 1, 1), # Birk | Scroll for Two-handed Axe for ATT 60%
    (2600013, 4007001, 7500, 1, 1), # Birk | Magic Powder (White)
    (2600012, 4007005, 7500, 1, 1), # Blue Kentaurus | Magic Powder (Purple)
    (2600012, 4006000, 7500, 1, 1), # Blue Kentaurus | The Magic Rock
    (2600012, 4010000, 7500, 1, 1), # Blue Kentaurus | Bronze Ore
    (2600012, 2000037, 100, 1, 1), # Blue Kentaurus | Sunrise Dew
    (2600012, 2000005, 100, 1, 1), # Blue Kentaurus | Power Elixir
    (2600012, 2000006, 100, 1, 1), # Blue Kentaurus | Mana Elixir
    (2600012, 2044314, 100, 1, 1), # Blue Kentaurus | Scroll for Spear for Accuracy 10%
    (2600012, 2061000, 100, 1, 1), # Blue Kentaurus | Arrow for Crossbow
    (2600012, 4161016, 7500, 1, 1), # Blue Kentaurus | [Storybook] Frozen Book of Ice
    (2600012, 2870318, 100, 1, 1), # Blue Kentaurus | Blue Kentaurus Familiar
    (2600012, 4020001, 7500, 1, 1), # Blue Kentaurus | Amethyst Ore
    (2600012, 2022057, 100, 1, 1), # Blue Kentaurus | Tofu
    (2600012, 2001001, 100, 1, 1), # Blue Kentaurus | Ice Cream Pop
    (2600012, 4007006, 7500, 1, 1), # Blue Kentaurus | Magic Powder (Red)
    (2600012, 2001002, 100, 1, 1), # Blue Kentaurus | Very Special Sundae
    (2600012, 4000233, 7500, 1, 1), # Blue Kentaurus | Kentaurus's Marrow
    (2600012, 2044012, 100, 1, 1), # Blue Kentaurus | Scroll for Two-Handed Sword for Accuracy 60%
    (2600012, 2043701, 100, 1, 1), # Blue Kentaurus | Scroll for Wand for Magic Attack 60%
    (2600012, 2041058, 100, 1, 1), # Blue Kentaurus | Scroll for Cape for Cold Protection 10%
    (2600012, 2040601, 100, 1, 1), # Blue Kentaurus | Scroll for Bottomwear for DEF 60%
    (2600012, 2332000, 100, 1, 1), # Blue Kentaurus | Glaze Capsule
    (2600012, 4004002, 7500, 1, 1), # Blue Kentaurus | DEX Crystal Ore
    (2600011, 2022057, 100, 1, 1), # Red Kentaurus | Tofu
    (2600011, 4004004, 7500, 1, 1), # Red Kentaurus | Dark Crystal Ore
    (2600011, 2870317, 100, 1, 1), # Red Kentaurus | Red Kentaurus Familiar
    (2600011, 2330002, 100, 1, 1), # Red Kentaurus | Mighty Bullet
    (2600011, 2043101, 100, 1, 1), # Red Kentaurus | Scroll for One-Handed Axe for ATT 60%
    (2600011, 2044501, 100, 1, 1), # Red Kentaurus | Scroll for Bow for ATT 60%
    (2600011, 2060000, 100, 1, 1), # Red Kentaurus | Arrow for Bow
    (2600011, 2001527, 100, 1, 1), # Red Kentaurus | Unagi
    (2600011, 2040805, 100, 1, 1), # Red Kentaurus | Scroll for Gloves for ATT 10%
    (2600011, 4161015, 7500, 1, 1), # Red Kentaurus | [Storybook] Burning Book of Fire
    (2600011, 2001526, 100, 1, 1), # Red Kentaurus | Pure Water
    (2600011, 4006000, 7500, 1, 1), # Red Kentaurus | The Magic Rock
    (2600011, 2331000, 100, 1, 1), # Red Kentaurus | Blaze Capsule
    (2600011, 4000232, 7500, 1, 1), # Red Kentaurus | Kentaurus's Flame
    (2600011, 4020000, 7500, 1, 1), # Red Kentaurus | Garnet Ore
    (2600011, 4007004, 7500, 1, 1), # Red Kentaurus | Magic Powder (Yellow)
    (2600011, 2001002, 100, 1, 1), # Red Kentaurus | Very Special Sundae
    (2600011, 2070004, 100, 1, 1), # Red Kentaurus | Tobi Throwing-Stars
    (2600011, 4007001, 7500, 1, 1), # Red Kentaurus | Magic Powder (White)
    (2600011, 4010001, 7500, 1, 1), # Red Kentaurus | Steel Ore
    (2600011, 2040901, 100, 1, 1), # Red Kentaurus | Scroll for Shield for DEF 60%
    (2600010, 4004003, 7500, 1, 1), # Black Kentaurus | LUK Crystal Ore
    (2600010, 2040825, 100, 1, 1), # Black Kentaurus | Scroll for Gloves for HP 10%
    (2600010, 2001001, 100, 1, 1), # Black Kentaurus | Ice Cream Pop
    (2600010, 2022057, 100, 1, 1), # Black Kentaurus | Tofu
    (2600010, 2060000, 100, 1, 1), # Black Kentaurus | Arrow for Bow
    (2600010, 4007001, 7500, 1, 1), # Black Kentaurus | Magic Powder (White)
    (2600010, 4000234, 7500, 1, 1), # Black Kentaurus | Kentaurus's Skull
    (2600010, 2000006, 100, 1, 1), # Black Kentaurus | Mana Elixir
    (2600010, 4010003, 7500, 1, 1), # Black Kentaurus | Adamantium Ore
    (2600010, 2870316, 100, 1, 1), # Black Kentaurus | Black Kentaurus Familiar
    (2600010, 4020001, 7500, 1, 1), # Black Kentaurus | Amethyst Ore
    (2600010, 2040824, 100, 1, 1), # Black Kentaurus | Scroll for Gloves for HP 60%
    (2600010, 2330002, 100, 1, 1), # Black Kentaurus | Mighty Bullet
    (2600010, 2048000, 100, 1, 1), # Black Kentaurus | Scroll for Pet Equip. for Speed 100%
    (2600010, 2043401, 100, 1, 1), # Black Kentaurus | Scroll for Katara for ATT 60%
    (2600010, 4007007, 7500, 1, 1), # Black Kentaurus | Magic Powder (Black)
    (2600010, 4006001, 7500, 1, 1), # Black Kentaurus | The Summoning Rock
    (2600010, 2070007, 100, 1, 1), # Black Kentaurus | Hwabi Throwing-Stars
    (2600010, 2000005, 100, 1, 1), # Black Kentaurus | Power Elixir
    (2600009, 2044412, 100, 1, 1), # Blood Harp | Scroll for Pole-Arm for Accuracy 60%
    (2600009, 2870314, 100, 1, 1), # Blood Harp | Blood Harp Familiar
    (2600009, 2001528, 100, 1, 1), # Blood Harp | Melting Cheese
    (2600009, 4007003, 7500, 1, 1), # Blood Harp | Magic Powder (Green)
    (2600009, 4007000, 7500, 1, 1), # Blood Harp | Magic Powder (Brown)
    (2600009, 2049000, 100, 1, 1), # Blood Harp | Pure Clean Slate Scroll 1%
    (2600009, 2060000, 100, 1, 1), # Blood Harp | Arrow for Bow
    (2600009, 2000037, 100, 1, 1), # Blood Harp | Sunrise Dew
    (2600009, 4000239, 7500, 1, 1), # Blood Harp | Blood Harp's Crown
    (2600009, 2001002, 100, 1, 1), # Blood Harp | Very Special Sundae
    (2600009, 2022057, 100, 1, 1), # Blood Harp | Tofu
    (2600009, 4020007, 7500, 1, 1), # Blood Harp | Diamond Ore
    (2600009, 2070007, 100, 1, 1), # Blood Harp | Hwabi Throwing-Stars
    (2600009, 2040026, 100, 1, 1), # Blood Harp | Scroll for Helmet for INT 10%
    (2600009, 2001526, 100, 1, 1), # Blood Harp | Pure Water
    (2600009, 4004001, 7500, 1, 1), # Blood Harp | Wisdom Crystal Ore
    (2600009, 2043017, 100, 1, 1), # Blood Harp | Scroll for One-Handed Sword for Accuracy 60%
    (2600009, 4000240, 7500, 1, 1), # Blood Harp | Small Flaming Feather
    (2600009, 2040707, 100, 1, 1), # Blood Harp | Scroll for Shoes for Speed 60%
    (2600009, 4010006, 7500, 1, 1), # Blood Harp | Gold Ore
    (2600009, 4006001, 7500, 1, 1), # Blood Harp | The Summoning Rock
    (2220110, 2044002, 100, 1, 1), # Crying Blue Mushroom | Scroll for Two-handed Sword for ATT 10%
    (2220110, 4020006, 7500, 1, 1), # Crying Blue Mushroom | Topaz Ore
    (2220110, 2070008, 100, 1, 1), # Crying Blue Mushroom | Snowball
    (2220110, 2000003, 100, 1, 1), # Crying Blue Mushroom | Blue Potion
    (2220110, 4010006, 7500, 1, 1), # Crying Blue Mushroom | Gold Ore
    (2220110, 4000009, 7500, 1, 1), # Crying Blue Mushroom | Blue Mushroom Cap
    (2220110, 2040900, 100, 1, 1), # Crying Blue Mushroom | Scroll for Shield for DEF 100%
    (2220110, 2870044, 100, 1, 1), # Crying Blue Mushroom | Crying Blue Mushroom Familiar
    (2220110, 2040403, 100, 1, 1), # Crying Blue Mushroom | Scroll for Topwear for DEF 100%
    (2220110, 2000001, 100, 1, 1), # Crying Blue Mushroom | Orange Potion
    (2220110, 2000002, 100, 1, 1), # Crying Blue Mushroom | White Potion
    (2220110, 2048000, 100, 1, 1), # Crying Blue Mushroom | Scroll for Pet Equip. for Speed 100%
    (2220110, 4020005, 7500, 1, 1), # Crying Blue Mushroom | Sapphire Ore
    (2220110, 2022016, 100, 1, 1), # Crying Blue Mushroom | Maple Special Bento
    (2220110, 2060000, 100, 1, 1), # Crying Blue Mushroom | Arrow for Bow
    (2600008, 4020005, 7500, 1, 1), # Harp | Sapphire Ore
    (2600008, 2000004, 100, 1, 1), # Harp | Elixir
    (2600008, 4007007, 7500, 1, 1), # Harp | Magic Powder (Black)
    (2600008, 4004000, 7500, 1, 1), # Harp | Power Crystal Ore
    (2600008, 4000238, 7500, 1, 1), # Harp | Harp's Tail Feather
    (2600008, 2870313, 100, 1, 1), # Harp | Harp Familiar
    (2600008, 2000006, 100, 1, 1), # Harp | Mana Elixir
    (2600008, 4006000, 7500, 1, 1), # Harp | The Magic Rock
    (2600008, 2044602, 100, 1, 1), # Harp | Scroll for Crossbow for ATT 10%
    (2600008, 2001527, 100, 1, 1), # Harp | Unagi
    (2600008, 2048001, 100, 1, 1), # Harp | Scroll for Pet Equip. for Speed 60%
    (2600008, 2001001, 100, 1, 1), # Harp | Ice Cream Pop
    (2600008, 2060000, 100, 1, 1), # Harp | Arrow for Bow
    (2600008, 2044701, 100, 1, 1), # Harp | Scroll for Claw for ATT 60%
    (2600008, 2040318, 100, 1, 1), # Harp | Scroll for Earring for DEX 10%
    (2600008, 4010005, 7500, 1, 1), # Harp | Orihalcon Ore
    (2600008, 4007005, 7500, 1, 1), # Harp | Magic Powder (Purple)
    (2600008, 2043401, 100, 1, 1), # Harp | Scroll for Katara for ATT 60%
    (2600023, 2001002, 100, 1, 1), # Red Wyvern | Very Special Sundae
    (2600023, 2870340, 100, 1, 1), # Red Wyvern | Red Wyvern Familiar
    (2600023, 4004002, 7500, 1, 1), # Red Wyvern | DEX Crystal Ore
    (2600023, 4020000, 7500, 1, 1), # Red Wyvern | Garnet Ore
    (2600023, 4006001, 7500, 1, 1), # Red Wyvern | The Summoning Rock
    (2600023, 2043114, 100, 1, 1), # Red Wyvern | Scroll for One-Handed Axe for Accuracy 10%
    (2600023, 4007001, 7500, 1, 1), # Red Wyvern | Magic Powder (White)
    (2600023, 2048000, 100, 1, 1), # Red Wyvern | Scroll for Pet Equip. for Speed 100%
    (2600023, 4000245, 7500, 1, 1), # Red Wyvern | Dragon Scale
    (2600023, 2001001, 100, 1, 1), # Red Wyvern | Ice Cream Pop
    (2600023, 4001402, 7500, 1, 1), # Red Wyvern | Dragon's Essence
    (2600023, 4007000, 7500, 1, 1), # Red Wyvern | Magic Powder (Brown)
    (2600023, 4001077, 7500, 1, 1), # Red Wyvern | Tough Dragon Skin
    (2600023, 2070005, 100, 1, 1), # Red Wyvern | Steely Throwing-Knives
    (2600023, 2000006, 100, 1, 1), # Red Wyvern | Mana Elixir
    (2600023, 4000268, 7500, 1, 1), # Red Wyvern | Wyvern Wing
    (2600023, 2043002, 100, 1, 1), # Red Wyvern | Scroll for One-Handed Sword for ATT 10%
    (2600023, 2060000, 100, 1, 1), # Red Wyvern | Arrow for Bow
    (2600023, 4000244, 7500, 1, 1), # Red Wyvern | Dragon Spirit
    (2600023, 2070007, 100, 1, 1), # Red Wyvern | Hwabi Throwing-Stars
    (2600023, 2043212, 100, 1, 1), # Red Wyvern | Scroll for One-Handed BW for Accuracy 60%
    (2600023, 2044301, 100, 1, 1), # Red Wyvern | Scroll for Spear for ATT 60%
    (2600023, 2022057, 100, 1, 1), # Red Wyvern | Tofu
    (2600023, 4010001, 7500, 1, 1), # Red Wyvern | Steel Ore
    (2600022, 2044601, 100, 1, 1), # Manon | Scroll for Crossbow for ATT 60%
    (2600022, 4001402, 7500, 1, 1), # Manon | Dragon's Essence
    (2600022, 2044701, 100, 1, 1), # Manon | Scroll for Claw for ATT 60%
    (2600022, 2044401, 100, 1, 1), # Manon | Scroll for Pole Arm for ATT 60%
    (2600022, 2044501, 100, 1, 1), # Manon | Scroll for Bow for ATT 60%
    (2600022, 2045301, 100, 1, 1), # Manon | Scroll for Hand Cannon for ATT 60%
    (2600022, 2043201, 100, 1, 1), # Manon | Scroll for One-Handed BW for ATT 60%
    (2600022, 2045201, 100, 1, 1), # Manon | Scroll for Dual Bowgun ATT 60%
    (2600022, 2044201, 100, 1, 1), # Manon | Scroll for Two-handed BW for ATT 60%
    (2600022, 2044901, 100, 1, 1), # Manon | Scroll for Gun for Attack 60%
    (2600022, 2043212, 100, 1, 1), # Manon | Scroll for One-Handed BW for Accuracy 60%
    (2600022, 2000037, 100, 1, 1), # Manon | Sunrise Dew
    (2600022, 2044301, 100, 1, 1), # Manon | Scroll for Spear for ATT 60%
    (2600022, 4000245, 7500, 1, 1), # Manon | Dragon Scale
    (2600022, 2042301, 100, 1, 1), # Manon | Scroll for Desperado for Weapon ATT 60%
    (2600022, 4000235, 7500, 1, 1), # Manon | Manon's Tail
    (2600022, 2040621, 100, 1, 1), # Manon | Scroll for Bottomwear for HP 60%
    (2600022, 2043301, 100, 1, 1), # Manon | Scroll for Dagger for ATT 60%
    (2600022, 2043801, 100, 1, 1), # Manon | Scroll for Staff for Magic Attack 60%
    (2600022, 4004002, 7500, 1, 1), # Manon | DEX Crystal Ore
    (2600022, 2040901, 100, 1, 1), # Manon | Scroll for Shield for DEF 60%
    (2600022, 2043001, 100, 1, 1), # Manon | Scroll for One-Handed Sword for ATT 60%
    (2600022, 2044014, 100, 1, 1), # Manon | Scroll for Two-Handed Sword for Accuracy 10%
    (2600022, 2043112, 100, 1, 1), # Manon | Scroll for One-Handed Axe for Accuracy 60%
    (2600022, 2044001, 100, 1, 1), # Manon | Scroll for Two-handed Sword for ATT 60%
    (2600022, 2044101, 100, 1, 1), # Manon | Scroll for Two-handed Axe for ATT 60%
    (2600022, 2040805, 100, 1, 1), # Manon | Scroll for Gloves for ATT 10%
    (2600022, 2870345, 100, 1, 1), # Manon | Manon Familiar
    (2600022, 4004003, 7500, 1, 1), # Manon | LUK Crystal Ore
    (2600022, 4000244, 7500, 1, 1), # Manon | Dragon Spirit
    (2600022, 4004001, 7500, 1, 1), # Manon | Wisdom Crystal Ore
    (2600022, 2044801, 100, 1, 1), # Manon | Scroll for Knuckle for Attack 60%
    (2600022, 2022057, 100, 1, 1), # Manon | Tofu
    (2600022, 2001527, 100, 1, 1), # Manon | Unagi
    (2600022, 2040328, 100, 1, 1), # Manon | Scroll for Earring for HP 10%
    (2600022, 2043701, 100, 1, 1), # Manon | Scroll for Wand for Magic Attack 60%
    (2600022, 2040824, 100, 1, 1), # Manon | Scroll for Gloves for HP 60%
    (2600022, 4001076, 7500, 1, 1), # Manon | Manon's Cry
    (2600022, 4004000, 7500, 1, 1), # Manon | Power Crystal Ore
    (2600022, 2043101, 100, 1, 1), # Manon | Scroll for One-Handed Axe for ATT 60%
    (2600022, 2049000, 100, 1, 1), # Manon | Pure Clean Slate Scroll 1%
    (2600022, 2000004, 100, 1, 1), # Manon | Elixir
    (2600021, 2001001, 100, 1, 1), # Nest Golem | Ice Cream Pop
    (2600021, 4006000, 7500, 1, 1), # Nest Golem | The Magic Rock
    (2600021, 2040320, 100, 1, 1), # Nest Golem | Scroll for Earring for LUK 70%
    (2600021, 4000271, 7500, 1, 1), # Nest Golem | Destroyed Nest
    (2600021, 4004003, 7500, 1, 1), # Nest Golem | LUK Crystal Ore
    (2600021, 2001528, 100, 1, 1), # Nest Golem | Melting Cheese
    (2600021, 2870347, 100, 1, 1), # Nest Golem | Nest Golem Familiar
    (2600021, 4000245, 7500, 1, 1), # Nest Golem | Dragon Scale
    (2600021, 4021020, 10, 1, 1), # Nest Golem | Confusion Fragment
    (2600021, 2044901, 100, 1, 1), # Nest Golem | Scroll for Gun for Attack 60%
    (2600021, 2044104, 100, 1, 1), # Nest Golem | Dark scroll for Two-handed Axe for ATT 70%
    (2600021, 2000037, 100, 1, 1), # Nest Golem | Sunrise Dew
    (2600021, 4007004, 7500, 1, 1), # Nest Golem | Magic Powder (Yellow)
    (2600021, 2043019, 100, 1, 1), # Nest Golem | Scroll for One-Handed Sword for Accuracy 10%
    (2600021, 4001402, 7500, 1, 1), # Nest Golem | Dragon's Essence
    (2600021, 4020007, 7500, 1, 1), # Nest Golem | Diamond Ore
    (2600021, 4010005, 7500, 1, 1), # Nest Golem | Orihalcon Ore
    (2600021, 2044201, 100, 1, 1), # Nest Golem | Scroll for Two-handed BW for ATT 60%
    (2600021, 2040707, 100, 1, 1), # Nest Golem | Scroll for Shoes for Speed 60%
    (2600021, 2061000, 100, 1, 1), # Nest Golem | Arrow for Crossbow
    (2600021, 4000244, 7500, 1, 1), # Nest Golem | Dragon Spirit
    (2600021, 4006001, 7500, 1, 1), # Nest Golem | The Summoning Rock
    (2600020, 2000036, 100, 1, 1), # Jr. Newtie | Reindeer Milk
    (2600020, 4000244, 7500, 1, 1), # Jr. Newtie | Dragon Spirit
    (2600020, 4006001, 7500, 1, 1), # Jr. Newtie | The Summoning Rock
    (2600020, 2049000, 100, 1, 1), # Jr. Newtie | Pure Clean Slate Scroll 1%
    (2600020, 2001002, 100, 1, 1), # Jr. Newtie | Very Special Sundae
    (2600020, 4010000, 7500, 1, 1), # Jr. Newtie | Bronze Ore
    (2600020, 2044702, 100, 1, 1), # Jr. Newtie | Scroll for Claw for ATT 10%
    (2600020, 4007000, 7500, 1, 1), # Jr. Newtie | Magic Powder (Brown)
    (2600020, 4020004, 7500, 1, 1), # Jr. Newtie | Opal Ore
    (2600020, 4000272, 7500, 1, 1), # Jr. Newtie | Egg Shell
    (2600020, 4000245, 7500, 1, 1), # Jr. Newtie | Dragon Scale
    (2600020, 4001402, 7500, 1, 1), # Jr. Newtie | Dragon's Essence
    (2600020, 4021020, 10, 1, 1), # Jr. Newtie | Confusion Fragment
    (2600020, 2061000, 100, 1, 1), # Jr. Newtie | Arrow for Crossbow
    (2600020, 4007005, 7500, 1, 1), # Jr. Newtie | Magic Powder (Purple)
    (2600020, 4004001, 7500, 1, 1), # Jr. Newtie | Wisdom Crystal Ore
    (2600020, 2022057, 100, 1, 1), # Jr. Newtie | Tofu
    (2600020, 2001526, 100, 1, 1), # Jr. Newtie | Pure Water
    (2600020, 2040419, 100, 1, 1), # Jr. Newtie | Scroll for Topwear for STR 10%
    (2600020, 2000037, 100, 1, 1), # Jr. Newtie | Sunrise Dew
    (2600020, 4006000, 7500, 1, 1), # Jr. Newtie | The Magic Rock
    (2600020, 2048003, 100, 1, 1), # Jr. Newtie | Scroll for Pet Equip. for Jump 100%
    (2600019, 4004003, 7500, 1, 1), # Brexton | LUK Crystal Ore
    (2600019, 2001526, 100, 1, 1), # Brexton | Pure Water
    (2600019, 2043201, 100, 1, 1), # Brexton | Scroll for One-Handed BW for ATT 60%
    (2600019, 2040901, 100, 1, 1), # Brexton | Scroll for Shield for DEF 60%
    (2600019, 4000244, 7500, 1, 1), # Brexton | Dragon Spirit
    (2600019, 4007005, 7500, 1, 1), # Brexton | Magic Powder (Purple)
    (2600019, 2000037, 100, 1, 1), # Brexton | Sunrise Dew
    (2600019, 4020008, 7500, 1, 1), # Brexton | Black Crystal Ore
    (2600019, 4000265, 7500, 1, 1), # Brexton | Strange Egg
    (2600019, 4020006, 7500, 1, 1), # Brexton | Topaz Ore
    (2600019, 4000245, 7500, 1, 1), # Brexton | Dragon Scale
    (2600019, 2000005, 100, 1, 1), # Brexton | Power Elixir
    (2600019, 2870328, 100, 1, 1), # Brexton | Brexton Familiar
    (2600019, 4001402, 7500, 1, 1), # Brexton | Dragon's Essence
    (2600019, 4007004, 7500, 1, 1), # Brexton | Magic Powder (Yellow)
    (2600019, 2044012, 100, 1, 1), # Brexton | Scroll for Two-Handed Sword for Accuracy 60%
    (2600019, 2022057, 100, 1, 1), # Brexton | Tofu
    (2600019, 2001002, 100, 1, 1), # Brexton | Very Special Sundae
    (2600018, 4000244, 7500, 1, 1), # Rexton | Dragon Spirit
    (2600018, 2870327, 100, 1, 1), # Rexton | Rexton Familiar
    (2600018, 2061000, 100, 1, 1), # Rexton | Arrow for Crossbow
    (2600018, 4007003, 7500, 1, 1), # Rexton | Magic Powder (Green)
    (2600018, 4004002, 7500, 1, 1), # Rexton | DEX Crystal Ore
    (2600018, 2000037, 100, 1, 1), # Rexton | Sunrise Dew
    (2600018, 2044001, 100, 1, 1), # Rexton | Scroll for Two-handed Sword for ATT 60%
    (2600018, 4000245, 7500, 1, 1), # Rexton | Dragon Scale
    (2600018, 2043101, 100, 1, 1), # Rexton | Scroll for One-Handed Axe for ATT 60%
    (2600018, 4020008, 7500, 1, 1), # Rexton | Black Crystal Ore
    (2600018, 4000264, 7500, 1, 1), # Rexton | Rexton Leather
    (2600018, 4020003, 7500, 1, 1), # Rexton | Emerald Ore
    (2600018, 2043401, 100, 1, 1), # Rexton | Scroll for Katara for ATT 60%
    (2600018, 2045201, 100, 1, 1), # Rexton | Scroll for Dual Bowgun ATT 60%
    (2600018, 2022057, 100, 1, 1), # Rexton | Tofu
    (2600018, 2000005, 100, 1, 1), # Rexton | Power Elixir
    (2600018, 4007000, 7500, 1, 1), # Rexton | Magic Powder (Brown)
    (2600018, 4001402, 7500, 1, 1), # Rexton | Dragon's Essence
    (2600017, 2000037, 100, 1, 1), # Red Dragon Turtle | Sunrise Dew
    (2600017, 2043601, 100, 1, 1), # Red Dragon Turtle | Scroll for Cane for ATT 60%
    (2600017, 2040601, 100, 1, 1), # Red Dragon Turtle | Scroll for Bottomwear for DEF 60%
    (2600017, 4010001, 7500, 1, 1), # Red Dragon Turtle | Steel Ore
    (2600017, 2070005, 100, 1, 1), # Red Dragon Turtle | Steely Throwing-Knives
    (2600017, 2060000, 100, 1, 1), # Red Dragon Turtle | Arrow for Bow
    (2600017, 2061000, 100, 1, 1), # Red Dragon Turtle | Arrow for Crossbow
    (2600017, 4000244, 7500, 1, 1), # Red Dragon Turtle | Dragon Spirit
    (2600017, 4020005, 7500, 1, 1), # Red Dragon Turtle | Sapphire Ore
    (2600017, 4000245, 7500, 1, 1), # Red Dragon Turtle | Dragon Scale
    (2600017, 2001001, 100, 1, 1), # Red Dragon Turtle | Ice Cream Pop
    (2600017, 4000263, 7500, 1, 1), # Red Dragon Turtle | Red Shell
    (2600017, 2001002, 100, 1, 1), # Red Dragon Turtle | Very Special Sundae
    (2600017, 4007002, 7500, 1, 1), # Red Dragon Turtle | Magic Powder (Blue)
    (2600017, 4004001, 7500, 1, 1), # Red Dragon Turtle | Wisdom Crystal Ore
    (2600017, 2043112, 100, 1, 1), # Red Dragon Turtle | Scroll for One-Handed Axe for Accuracy 60%
    (2600017, 2870326, 100, 1, 1), # Red Dragon Turtle | Red Dragon Turtle Familiar
    (2600017, 2044101, 100, 1, 1), # Red Dragon Turtle | Scroll for Two-handed Axe for ATT 60%
    (2600017, 4007000, 7500, 1, 1), # Red Dragon Turtle | Magic Powder (Brown)
    (2600017, 2022057, 100, 1, 1), # Red Dragon Turtle | Tofu
    (2600017, 2001527, 100, 1, 1), # Red Dragon Turtle | Unagi
    (2600017, 4001402, 7500, 1, 1), # Red Dragon Turtle | Dragon's Essence
    (2600016, 2048000, 100, 1, 1), # Blue Dragon Turtle | Scroll for Pet Equip. for Speed 100%
    (2600016, 4020001, 7500, 1, 1), # Blue Dragon Turtle | Amethyst Ore
    (2600016, 2000005, 100, 1, 1), # Blue Dragon Turtle | Power Elixir
    (2600016, 4004000, 7500, 1, 1), # Blue Dragon Turtle | Power Crystal Ore
    (2600016, 2870325, 100, 1, 1), # Blue Dragon Turtle | Blue Dragon Turtle Familiar
    (2600016, 2001527, 100, 1, 1), # Blue Dragon Turtle | Unagi
    (2600016, 4007000, 7500, 1, 1), # Blue Dragon Turtle | Magic Powder (Brown)
    (2600016, 4007003, 7500, 1, 1), # Blue Dragon Turtle | Magic Powder (Green)
    (2600016, 2043017, 100, 1, 1), # Blue Dragon Turtle | Scroll for One-Handed Sword for Accuracy 60%
    (2600016, 2001001, 100, 1, 1), # Blue Dragon Turtle | Ice Cream Pop
    (2600016, 2022057, 100, 1, 1), # Blue Dragon Turtle | Tofu
    (2600016, 2000006, 100, 1, 1), # Blue Dragon Turtle | Mana Elixir
    (2600016, 4001402, 7500, 1, 1), # Blue Dragon Turtle | Dragon's Essence
    (2600016, 4000245, 7500, 1, 1), # Blue Dragon Turtle | Dragon Scale
    (2600016, 2044314, 100, 1, 1), # Blue Dragon Turtle | Scroll for Spear for Accuracy 10%
    (2600016, 2044902, 100, 1, 1), # Blue Dragon Turtle | Scroll for Gun for ATT 10%
    (2600016, 4000262, 7500, 1, 1), # Blue Dragon Turtle | Cracked Shell
    (2600016, 4020007, 7500, 1, 1), # Blue Dragon Turtle | Diamond Ore
    (2600016, 4000244, 7500, 1, 1), # Blue Dragon Turtle | Dragon Spirit
    (4230200, 4031265, 100, 1, 1), # Poopa | Poopa's DNA Sample
    (4230200, 4000158, 7500, 1, 1), # Poopa | Poopa Egg
    (4230200, 2870156, 100, 1, 1), # Poopa | Poopa Familiar
    (2600030, 2870366, 100, 1, 1), # Leviathan | Leviathan Familiar
    (2600030, 2040824, 100, 1, 1), # Leviathan | Scroll for Gloves for HP 60%
    (2600030, 2060000, 100, 1, 1), # Leviathan | Arrow for Bow
    (2600030, 2001527, 100, 1, 1), # Leviathan | Unagi
    (2600030, 2044012, 100, 1, 1), # Leviathan | Scroll for Two-Handed Sword for Accuracy 60%
    (2600030, 4006000, 7500, 1, 1), # Leviathan | The Magic Rock
    (2600030, 2040927, 100, 1, 1), # Leviathan | Scroll for Shield for HP 60%
    (2600030, 2001001, 100, 1, 1), # Leviathan | Ice Cream Pop
    (2600030, 4000269, 7500, 1, 1), # Leviathan | Wyvern Gill
    (2600030, 2040801, 100, 1, 1), # Leviathan | Scroll for Gloves for DEX 60%
    (2600030, 2048000, 100, 1, 1), # Leviathan | Scroll for Pet Equip. for Speed 100%
    (2600030, 2070003, 100, 1, 1), # Leviathan | Kumbi Throwing-Stars
    (2600030, 2044212, 100, 1, 1), # Leviathan | Scroll for Two-Handed BW for Accuracy 60%
    (2600030, 2001002, 100, 1, 1), # Leviathan | Very Special Sundae
    (2600030, 2000004, 100, 1, 1), # Leviathan | Elixir
    (2600030, 2000036, 100, 1, 1), # Leviathan | Reindeer Milk
    (2600030, 2044412, 100, 1, 1), # Leviathan | Scroll for Pole-Arm for Accuracy 60%
    (2600030, 2048002, 100, 1, 1), # Leviathan | Scroll for Pet Equip. for Speed 10%
    (2600030, 2044101, 100, 1, 1), # Leviathan | Scroll for Two-handed Axe for ATT 60%
    (2600030, 2044112, 100, 1, 1), # Leviathan | Scroll for Two-Handed Axe for Accuracy 60%
    (2600030, 2061000, 100, 1, 1), # Leviathan | Arrow for Crossbow
    (2600030, 2001526, 100, 1, 1), # Leviathan | Pure Water
    (2600030, 2001528, 100, 1, 1), # Leviathan | Melting Cheese
    (2600030, 4000268, 7500, 1, 1), # Leviathan | Wyvern Wing
    (2600030, 2049100, 100, 1, 1), # Leviathan | Chaos Scroll 60%
    (2600030, 4000270, 7500, 1, 1), # Leviathan | Wyvern Toenail
    (2600030, 2044312, 100, 1, 1), # Leviathan | Scroll for Spear for Accuracy 60%
    (2600030, 2000005, 100, 1, 1), # Leviathan | Power Elixir
    (2600030, 4006001, 7500, 1, 1), # Leviathan | The Summoning Rock
    (4230201, 2870157, 100, 1, 1), # Poison Poopa | Poison Poopa Familiar
    (4230201, 4000159, 7500, 1, 1), # Poison Poopa | Poison Poopa's Poisonous Spikes
    (8950001, 4001843, 7500, 1, 1), # Lotus | Extraordinary Energy Core (Grade S)
    (8950001, 4310029, 7500, 1, 1), # Lotus | Crusader Coins
    (8950001, 4001877, 7500, 1, 1), # Lotus | Damaged Black Heart
    (8950001, 4310029, 7500, 1, 1), # Lotus | Crusader Coins
    (8950001, 2433593, 3000, 1, 1), # Lotus | Lotus's Soul Shard
    (8950001, 2000005, 100, 1, 1), # Lotus | Power Elixir
    (8950001, 2433103, 100, 1, 1), # Lotus | Boss Medal of Honor
    (2600029, 4004004, 7500, 1, 1), # Skelosaurus | Dark Crystal Ore
    (2600029, 2000006, 100, 1, 1), # Skelosaurus | Mana Elixir
    (2600029, 2043701, 100, 1, 1), # Skelosaurus | Scroll for Wand for Magic Attack 60%
    (2600029, 4020002, 7500, 1, 1), # Skelosaurus | AquaMarine Ore
    (2600029, 4000273, 7500, 1, 1), # Skelosaurus | Old Neck Bone
    (2600029, 4006001, 7500, 1, 1), # Skelosaurus | The Summoning Rock
    (2600029, 4021020, 10, 1, 1), # Skelosaurus | Confusion Fragment
    (2600029, 2070003, 100, 1, 1), # Skelosaurus | Kumbi Throwing-Stars
    (2600029, 2061000, 100, 1, 1), # Skelosaurus | Arrow for Crossbow
    (2600029, 4020006, 7500, 1, 1), # Skelosaurus | Topaz Ore
    (2600029, 4007002, 7500, 1, 1), # Skelosaurus | Magic Powder (Blue)
    (2600029, 2060000, 100, 1, 1), # Skelosaurus | Arrow for Bow
    (2600029, 2048003, 100, 1, 1), # Skelosaurus | Scroll for Pet Equip. for Jump 100%
    (2600029, 4000245, 7500, 1, 1), # Skelosaurus | Dragon Scale
    (2600029, 4001402, 7500, 1, 1), # Skelosaurus | Dragon's Essence
    (2600029, 4007004, 7500, 1, 1), # Skelosaurus | Magic Powder (Yellow)
    (2600029, 2001001, 100, 1, 1), # Skelosaurus | Ice Cream Pop
    (2600029, 4000244, 7500, 1, 1), # Skelosaurus | Dragon Spirit
    (2600029, 4006000, 7500, 1, 1), # Skelosaurus | The Magic Rock
    (2600029, 2022057, 100, 1, 1), # Skelosaurus | Tofu
    (2600029, 2044212, 100, 1, 1), # Skelosaurus | Scroll for Two-Handed BW for Accuracy 60%
    (2600029, 2870349, 100, 1, 1), # Skelosaurus | Skelosaurus Familiar
    (2600029, 2044214, 100, 1, 1), # Skelosaurus | Scroll for Two-Handed BW for Accuracy 10%
    (8950002, 4001877, 7500, 1, 1), # Lotus | Damaged Black Heart
    (8950002, 4310029, 7500, 1, 1), # Lotus | Crusader Coins
    (2600028, 4021020, 10, 1, 1), # Skelegon | Confusion Fragment
    (2600028, 2044701, 100, 1, 1), # Skelegon | Scroll for Claw for ATT 60%
    (2600028, 4006000, 7500, 1, 1), # Skelegon | The Magic Rock
    (2600028, 2044902, 100, 1, 1), # Skelegon | Scroll for Gun for ATT 10%
    (2600028, 2044602, 100, 1, 1), # Skelegon | Scroll for Crossbow for ATT 10%
    (2600028, 4007007, 7500, 1, 1), # Skelegon | Magic Powder (Black)
    (2600028, 4000274, 7500, 1, 1), # Skelegon | Broken Horn
    (2600028, 4006001, 7500, 1, 1), # Skelegon | The Summoning Rock
    (2600028, 2070003, 100, 1, 1), # Skelegon | Kumbi Throwing-Stars
    (2600028, 2022057, 100, 1, 1), # Skelegon | Tofu
    (2600028, 4000244, 7500, 1, 1), # Skelegon | Dragon Spirit
    (2600028, 2040805, 100, 1, 1), # Skelegon | Scroll for Gloves for ATT 10%
    (2600028, 2000005, 100, 1, 1), # Skelegon | Power Elixir
    (2600028, 4001402, 7500, 1, 1), # Skelegon | Dragon's Essence
    (2600028, 4010001, 7500, 1, 1), # Skelegon | Steel Ore
    (2600028, 4020001, 7500, 1, 1), # Skelegon | Amethyst Ore
    (2600028, 2001001, 100, 1, 1), # Skelegon | Ice Cream Pop
    (2600028, 4000245, 7500, 1, 1), # Skelegon | Dragon Scale
    (2600028, 4007003, 7500, 1, 1), # Skelegon | Magic Powder (Green)
    (2600028, 4004001, 7500, 1, 1), # Skelegon | Wisdom Crystal Ore
    (2600028, 2001002, 100, 1, 1), # Skelegon | Very Special Sundae
    (2600028, 2000006, 100, 1, 1), # Skelegon | Mana Elixir
    (2600028, 2870348, 100, 1, 1), # Skelegon | Skelegon Familiar
    (2600028, 2049000, 100, 1, 1), # Skelegon | Pure Clean Slate Scroll 1%
    (2600027, 4001402, 7500, 1, 1), # Dark Cornian | Dragon's Essence
    (2600027, 2049100, 100, 1, 1), # Dark Cornian | Chaos Scroll 60%
    (2600027, 4001075, 7500, 1, 1), # Dark Cornian | Cornian's Marrow
    (2600027, 4007005, 7500, 1, 1), # Dark Cornian | Magic Powder (Purple)
    (2600027, 2870339, 100, 1, 1), # Dark Cornian | Dark Cornian Familiar
    (2600027, 2000036, 100, 1, 1), # Dark Cornian | Reindeer Milk
    (2600027, 2044802, 100, 1, 1), # Dark Cornian | Scroll for Knuckle for ATT 10%
    (2600027, 2000005, 100, 1, 1), # Dark Cornian | Power Elixir
    (2600027, 2000006, 100, 1, 1), # Dark Cornian | Mana Elixir
    (2600027, 4007003, 7500, 1, 1), # Dark Cornian | Magic Powder (Green)
    (2600027, 4004004, 7500, 1, 1), # Dark Cornian | Dark Crystal Ore
    (2600027, 4000267, 7500, 1, 1), # Dark Cornian | Skull Shoulder Pad
    (2600027, 2044601, 100, 1, 1), # Dark Cornian | Scroll for Crossbow for ATT 60%
    (2600027, 2040302, 100, 1, 1), # Dark Cornian | Scroll for Earring for INT 10%
    (2600027, 2040801, 100, 1, 1), # Dark Cornian | Scroll for Gloves for DEX 60%
    (2600027, 4021020, 10, 1, 1), # Dark Cornian | Confusion Fragment
    (2600027, 4010001, 7500, 1, 1), # Dark Cornian | Steel Ore
    (2600027, 2000037, 100, 1, 1), # Dark Cornian | Sunrise Dew
    (2600027, 4000245, 7500, 1, 1), # Dark Cornian | Dragon Scale
    (2600027, 2001002, 100, 1, 1), # Dark Cornian | Very Special Sundae
    (2600027, 4000244, 7500, 1, 1), # Dark Cornian | Dragon Spirit
    (2600027, 2001001, 100, 1, 1), # Dark Cornian | Ice Cream Pop
    (2600027, 2044501, 100, 1, 1), # Dark Cornian | Scroll for Bow for ATT 60%
    (2600027, 2022057, 100, 1, 1), # Dark Cornian | Tofu
    (2600027, 4020002, 7500, 1, 1), # Dark Cornian | AquaMarine Ore
    (2600026, 4000244, 7500, 1, 1), # Green Cornian | Dragon Spirit
    (2600026, 4001075, 7500, 1, 1), # Green Cornian | Cornian's Marrow
    (2600026, 2001526, 100, 1, 1), # Green Cornian | Pure Water
    (2600026, 2048001, 100, 1, 1), # Green Cornian | Scroll for Pet Equip. for Speed 60%
    (2600026, 4007000, 7500, 1, 1), # Green Cornian | Magic Powder (Brown)
    (2600026, 4007007, 7500, 1, 1), # Green Cornian | Magic Powder (Black)
    (2600026, 2044801, 100, 1, 1), # Green Cornian | Scroll for Knuckle for Attack 60%
    (2600026, 2000006, 100, 1, 1), # Green Cornian | Mana Elixir
    (2600026, 4001402, 7500, 1, 1), # Green Cornian | Dragon's Essence
    (2600026, 2043017, 100, 1, 1), # Green Cornian | Scroll for One-Handed Sword for Accuracy 60%
    (2600026, 4010002, 7500, 1, 1), # Green Cornian | Mithril Ore
    (2600026, 4004004, 7500, 1, 1), # Green Cornian | Dark Crystal Ore
    (2600026, 4010005, 7500, 1, 1), # Green Cornian | Orihalcon Ore
    (2600026, 2870338, 100, 1, 1), # Green Cornian | Green Cornian Familiar
    (2600026, 2000005, 100, 1, 1), # Green Cornian | Power Elixir
    (2600026, 4000245, 7500, 1, 1), # Green Cornian | Dragon Scale
    (2600026, 2022057, 100, 1, 1), # Green Cornian | Tofu
    (2600026, 4000266, 7500, 1, 1), # Green Cornian | Wooden Shoulder Pad
    (2600025, 4010005, 7500, 1, 1), # Dark Wyvern | Orihalcon Ore
    (2600025, 4001402, 7500, 1, 1), # Dark Wyvern | Dragon's Essence
    (2600025, 2000005, 100, 1, 1), # Dark Wyvern | Power Elixir
    (2600025, 2040924, 100, 1, 1), # Dark Wyvern | Scroll for Shield for LUK 60%
    (2600025, 4020002, 7500, 1, 1), # Dark Wyvern | AquaMarine Ore
    (2600025, 2045301, 100, 1, 1), # Dark Wyvern | Scroll for Hand Cannon for ATT 60%
    (2600025, 4007001, 7500, 1, 1), # Dark Wyvern | Magic Powder (White)
    (2600025, 2044412, 100, 1, 1), # Dark Wyvern | Scroll for Pole-Arm for Accuracy 60%
    (2600025, 2000006, 100, 1, 1), # Dark Wyvern | Mana Elixir
    (2600025, 2044402, 100, 1, 1), # Dark Wyvern | Scroll for Pole Arm for ATT 10%
    (2600025, 2060000, 100, 1, 1), # Dark Wyvern | Arrow for Bow
    (2600025, 4000244, 7500, 1, 1), # Dark Wyvern | Dragon Spirit
    (2600025, 2001526, 100, 1, 1), # Dark Wyvern | Pure Water
    (2600025, 2040302, 100, 1, 1), # Dark Wyvern | Scroll for Earring for INT 10%
    (2600025, 4000245, 7500, 1, 1), # Dark Wyvern | Dragon Scale
    (2600025, 4007007, 7500, 1, 1), # Dark Wyvern | Magic Powder (Black)
    (2600025, 2870342, 100, 1, 1), # Dark Wyvern | Dark Wyvern Familiar
    (2600025, 4000270, 7500, 1, 1), # Dark Wyvern | Wyvern Toenail
    (2600025, 4001077, 7500, 1, 1), # Dark Wyvern | Tough Dragon Skin
    (2600025, 2022057, 100, 1, 1), # Dark Wyvern | Tofu
    (2600025, 4021020, 10, 1, 1), # Dark Wyvern | Confusion Fragment
    (2600025, 2000036, 100, 1, 1), # Dark Wyvern | Reindeer Milk
    (2600025, 2330005, 100, 1, 1), # Dark Wyvern | Eternal Bullet
    (2600025, 4004000, 7500, 1, 1), # Dark Wyvern | Power Crystal Ore
    (2600025, 4006000, 7500, 1, 1), # Dark Wyvern | The Magic Rock
    (2600025, 2044401, 100, 1, 1), # Dark Wyvern | Scroll for Pole Arm for ATT 60%
    (2600025, 4006001, 7500, 1, 1), # Dark Wyvern | The Summoning Rock
    (2600025, 2061000, 100, 1, 1), # Dark Wyvern | Arrow for Crossbow
    (2600024, 4010004, 7500, 1, 1), # Blue Wyvern | Silver Ore
    (2600024, 2001527, 100, 1, 1), # Blue Wyvern | Unagi
    (2600024, 2044112, 100, 1, 1), # Blue Wyvern | Scroll for Two-Handed Axe for Accuracy 60%
    (2600024, 2022057, 100, 1, 1), # Blue Wyvern | Tofu
    (2600024, 2000005, 100, 1, 1), # Blue Wyvern | Power Elixir
    (2600024, 4004003, 7500, 1, 1), # Blue Wyvern | LUK Crystal Ore
    (2600024, 2000006, 100, 1, 1), # Blue Wyvern | Mana Elixir
    (2600024, 2070009, 100, 1, 1), # Blue Wyvern | Wooden Top
    (2600024, 2870341, 100, 1, 1), # Blue Wyvern | Blue Wyvern Familiar
    (2600024, 4001077, 7500, 1, 1), # Blue Wyvern | Tough Dragon Skin
    (2600024, 2070004, 100, 1, 1), # Blue Wyvern | Tobi Throwing-Stars
    (2600024, 4000245, 7500, 1, 1), # Blue Wyvern | Dragon Scale
    (2600024, 2043112, 100, 1, 1), # Blue Wyvern | Scroll for One-Handed Axe for Accuracy 60%
    (2600024, 4000244, 7500, 1, 1), # Blue Wyvern | Dragon Spirit
    (2600024, 2001002, 100, 1, 1), # Blue Wyvern | Very Special Sundae
    (2600024, 2000037, 100, 1, 1), # Blue Wyvern | Sunrise Dew
    (2600024, 4007007, 7500, 1, 1), # Blue Wyvern | Magic Powder (Black)
    (2600024, 4001402, 7500, 1, 1), # Blue Wyvern | Dragon's Essence
    (2600024, 4007001, 7500, 1, 1), # Blue Wyvern | Magic Powder (White)
    (2600024, 4020005, 7500, 1, 1), # Blue Wyvern | Sapphire Ore
    (2600024, 4006001, 7500, 1, 1), # Blue Wyvern | The Summoning Rock
    (2600024, 2043201, 100, 1, 1), # Blue Wyvern | Scroll for One-Handed BW for ATT 60%
    (2600024, 4000269, 7500, 1, 1), # Blue Wyvern | Wyvern Gill
    (2600103, 2870028, 100, 1, 1), # Cactus | Cactus Familiar
    (2600103, 2512053, 100, 1, 1), # Cactus | Health Boost Potion Recipe
    (2600103, 2001001, 100, 1, 1), # Cactus | Ice Cream Pop
    (2600103, 2070003, 100, 1, 1), # Cactus | Kumbi Throwing-Stars
    (2600103, 4000330, 7500, 1, 1), # Cactus | Cactus Thorn
    (2600103, 2045201, 100, 1, 1), # Cactus | Scroll for Dual Bowgun ATT 60%
    (2600103, 4020004, 7500, 1, 1), # Cactus | Opal Ore
    (2600103, 2044314, 100, 1, 1), # Cactus | Scroll for Spear for Accuracy 10%
    (2600103, 2048002, 100, 1, 1), # Cactus | Scroll for Pet Equip. for Speed 10%
    (2600103, 2044001, 100, 1, 1), # Cactus | Scroll for Two-handed Sword for ATT 60%
    (2600103, 4010007, 7500, 1, 1), # Cactus | Lidium Ore
    (2600103, 2022155, 100, 1, 1), # Cactus | Desert Mist
    (2600103, 2001002, 100, 1, 1), # Cactus | Very Special Sundae
    (2600103, 2043701, 100, 1, 1), # Cactus | Scroll for Wand for Magic Attack 60%
    (2600103, 2070004, 100, 1, 1), # Cactus | Tobi Throwing-Stars
    (2600103, 2061000, 100, 1, 1), # Cactus | Arrow for Crossbow
    (2600103, 4010003, 7500, 1, 1), # Cactus | Adamantium Ore
    (2600103, 2001527, 100, 1, 1), # Cactus | Unagi
    (2600103, 2001526, 100, 1, 1), # Cactus | Pure Water
    (2600102, 2001001, 100, 1, 1), # Jr. Cactus | Ice Cream Pop
    (2600102, 4000329, 7500, 1, 1), # Jr. Cactus | Cactus Stem
    (2600102, 2870027, 100, 1, 1), # Jr. Cactus | Jr. Cactus Familiar
    (2600102, 4010002, 7500, 1, 1), # Jr. Cactus | Mithril Ore
    (2600102, 4010007, 7500, 1, 1), # Jr. Cactus | Lidium Ore
    (2600102, 2044012, 100, 1, 1), # Jr. Cactus | Scroll for Two-Handed Sword for Accuracy 60%
    (2600102, 2000006, 100, 1, 1), # Jr. Cactus | Mana Elixir
    (2600102, 2044312, 100, 1, 1), # Jr. Cactus | Scroll for Spear for Accuracy 60%
    (2600102, 2061000, 100, 1, 1), # Jr. Cactus | Arrow for Crossbow
    (2600102, 2048003, 100, 1, 1), # Jr. Cactus | Scroll for Pet Equip. for Jump 100%
    (2600102, 2040705, 100, 1, 1), # Jr. Cactus | Scroll for Shoes for Jump 10%
    (2600102, 2070004, 100, 1, 1), # Jr. Cactus | Tobi Throwing-Stars
    (2600102, 2022155, 100, 1, 1), # Jr. Cactus | Desert Mist
    (2600102, 4020004, 7500, 1, 1), # Jr. Cactus | Opal Ore
    (2600102, 2044102, 100, 1, 1), # Jr. Cactus | Scroll for Two-handed Axe for ATT 10%
    (2600102, 2001527, 100, 1, 1), # Jr. Cactus | Unagi
    (7120110, 4021033, 100, 1, 1), # Blazing Imp | Basic Spell Essence
    (7120110, 4021032, 7500, 1, 1), # Blazing Imp | Mana Crystal
    (7120110, 4021034, 100, 1, 1), # Blazing Imp | Fine Spell Essence
    (7120110, 4021035, 100, 1, 1), # Blazing Imp | Grand Spell Essence
    (7120110, 4021031, 50, 1, 1), # Blazing Imp | Twisted Time
    (7120110, 4001755, 7500, 1, 1), # Blazing Imp | Burning Tail
    (7120111, 4021033, 100, 1, 1), # Pointy Imp | Basic Spell Essence
    (7120111, 4021031, 50, 1, 1), # Pointy Imp | Twisted Time
    (7120111, 4021032, 7500, 1, 1), # Pointy Imp | Mana Crystal
    (7120111, 4021034, 100, 1, 1), # Pointy Imp | Fine Spell Essence
    (7120111, 4021035, 100, 1, 1), # Pointy Imp | Grand Spell Essence
    (7120111, 4001756, 7500, 1, 1), # Pointy Imp | Sharp Tail
    (3220001, 4000331, 7500, 1, 1), # Deo | Cactus Flower
    (3220001, 2022155, 100, 1, 1), # Deo | Desert Mist
    (3220001, 2040004, 100, 1, 1), # Deo | Scroll for Helmet for HP 60%
    (2600104, 4020005, 7500, 1, 1), # Royal Cactus | Sapphire Ore
    (2600104, 2044412, 100, 1, 1), # Royal Cactus | Scroll for Pole-Arm for Accuracy 60%
    (2600104, 2001001, 100, 1, 1), # Royal Cactus | Ice Cream Pop
    (2600104, 2043214, 100, 1, 1), # Royal Cactus | Scroll for One-Handed BW for Accuracy 10%
    (2600104, 2512059, 100, 1, 1), # Royal Cactus | Luck Boost Potion Recipe
    (2600104, 2043401, 100, 1, 1), # Royal Cactus | Scroll for Katara for ATT 60%
    (2600104, 2044901, 100, 1, 1), # Royal Cactus | Scroll for Gun for Attack 60%
    (2600104, 2000002, 100, 1, 1), # Royal Cactus | White Potion
    (2600104, 2001526, 100, 1, 1), # Royal Cactus | Pure Water
    (2600104, 4000331, 7500, 1, 1), # Royal Cactus | Cactus Flower
    (2600104, 2870029, 100, 1, 1), # Royal Cactus | Royal Cactus Familiar
    (2600104, 4004001, 7500, 1, 1), # Royal Cactus | Wisdom Crystal Ore
    (2600104, 4010007, 7500, 1, 1), # Royal Cactus | Lidium Ore
    (2600104, 4020006, 7500, 1, 1), # Royal Cactus | Topaz Ore
    (2600104, 2022155, 100, 1, 1), # Royal Cactus | Desert Mist
    (2600104, 2000006, 100, 1, 1), # Royal Cactus | Mana Elixir
    (2130100, 2000000, 100, 1, 1), # Dark Axe Stump | Red Potion
    (2130100, 2040503, 100, 1, 1), # Dark Axe Stump | Scroll for Overall Armor for DEF 100%
    (2130100, 2060000, 100, 1, 1), # Dark Axe Stump | Arrow for Bow
    (2130100, 2000001, 100, 1, 1), # Dark Axe Stump | Orange Potion
    (2130100, 2040417, 100, 1, 1), # Dark Axe Stump | Scroll for Topwear for STR 100%
    (2130100, 2044202, 100, 1, 1), # Dark Axe Stump | Scroll for Two-handed BW for ATT 10%
    (2130100, 4010001, 7500, 1, 1), # Dark Axe Stump | Steel Ore
    (2130100, 2040420, 100, 1, 1), # Dark Axe Stump | Scroll for Topwear for HP 100%
    (2130100, 2870036, 100, 1, 1), # Dark Axe Stump | Dark Axe Stump Familiar
    (2130100, 4020007, 7500, 1, 1), # Dark Axe Stump | Diamond Ore
    (2130100, 4000018, 7500, 1, 1), # Dark Axe Stump | Firewood
    (2130100, 4006001, 7500, 1, 1), # Dark Axe Stump | The Summoning Rock
    (2130103, 4020006, 7500, 1, 1), # Jr. Necki | Topaz Ore
    (2130103, 2000003, 100, 1, 1), # Jr. Necki | Blue Potion
    (2130103, 2040926, 100, 1, 1), # Jr. Necki | Scroll for Shield for HP 100%
    (2130103, 2040000, 100, 1, 1), # Jr. Necki | Scroll for Helmet for DEF 100%
    (2130103, 2070000, 100, 1, 1), # Jr. Necki | Subi Throwing-Stars
    (2130103, 2000002, 100, 1, 1), # Jr. Necki | White Potion
    (2130103, 2870037, 100, 1, 1), # Jr. Necki | Jr. Necki Familiar
    (2130103, 4000034, 7500, 1, 1), # Jr. Necki | Jr. Necki Skin
    (2130103, 2040614, 100, 1, 1), # Jr. Necki | Scroll for Bottomwear for DEX 100%
    (2130103, 4010002, 7500, 1, 1), # Jr. Necki | Mithril Ore
    (8140000, 4006001, 7500, 1, 1), # Lycanthrope | The Summoning Rock
    (8140000, 2870312, 100, 1, 1), # Lycanthrope | Lycanthrope Familiar
    (8140000, 2512110, 100, 1, 1), # Lycanthrope | Advanced Intelligence Potion II Recipe
    (8140000, 4021034, 100, 1, 1), # Lycanthrope | Fine Spell Essence
    (8140000, 2512276, 100, 1, 1), # Lycanthrope | Exceptional Fortitude Potion Recipe
    (8140000, 4021035, 100, 1, 1), # Lycanthrope | Grand Spell Essence
    (8140000, 2000037, 100, 1, 1), # Lycanthrope | Sunrise Dew
    (8140000, 2710002, 100, 1, 1), # Lycanthrope | Master Craftsman's Cube
    (8140000, 2001528, 100, 1, 1), # Lycanthrope | Melting Cheese
    (8140000, 2940000, 100, 1, 1), # Lycanthrope | Star Dust
    (8140000, 2512243, 100, 1, 1), # Lycanthrope | Attack Pill V Recipe
    (8140000, 2431174, 100, 1, 1), # Lycanthrope | Medal of Honor
    (8140000, 4003005, 7500, 1, 1), # Lycanthrope | Soft Feather
    (8140000, 2512267, 100, 1, 1), # Lycanthrope | Exceptional Hero Potion Recipe
    (8140000, 2511011, 100, 1, 1), # Lycanthrope | Shiny Red Archer Symbol Recipe
    (8140000, 2040727, 100, 1, 1), # Lycanthrope | Scroll for Spikes on Shoes 10%
    (8140000, 4000054, 7500, 1, 1), # Lycanthrope | Lycanthrope Toenail
    (8140000, 4021031, 50, 1, 1), # Lycanthrope | Twisted Time
    (8140000, 2512106, 100, 1, 1), # Lycanthrope | Advanced Dexterity Potion VIII Recipe
    (8140000, 2049401, 100, 1, 1), # Lycanthrope | Potential Scroll
    (8140000, 2512274, 100, 1, 1), # Lycanthrope | Exceptional Concentration Potion Recipe
    (8140000, 2070006, 100, 1, 1), # Lycanthrope | Ilbi Throwing-Stars
    (8140000, 4021032, 7500, 1, 1), # Lycanthrope | Mana Crystal
    (8140000, 2512269, 100, 1, 1), # Lycanthrope | Superior Blessing Potion Recipe
    (8140000, 4021033, 100, 1, 1), # Lycanthrope | Basic Spell Essence
    (8140000, 2590004, 100, 1, 1), # Lycanthrope | Prototype Soul Enchanter
    (8140000, 2512272, 100, 1, 1), # Lycanthrope | Intermediate Concentration Potion Recipe
    (8880167, 4001879, 7500, 1, 1), # Final Music Box | Butterfly Droplet Stone
    (8880167, 4310029, 7500, 1, 1), # Final Music Box | Crusader Coins
    (9300013, 2430115, 100, 1, 1), # King Block Golem from Another Dimension | Dimensional Pass
    (4300000, 4000530, 7500, 1, 1), # Blue Perfume | Blue Perfume
    (4300000, 2870171, 100, 1, 1), # Blue Perfume | Blue Perfume Familiar
    (8920106, 4310029, 7500, 1, 1), # Crimson Queen's Treasure Chest | Crusader Coins
    (8920106, 2433103, 100, 1, 1), # Crimson Queen's Treasure Chest | Boss Medal of Honor
    (4300002, 4000532, 7500, 1, 1), # Yellow Perfume | Yellow Perfume
    (4300002, 2870172, 100, 1, 1), # Yellow Perfume | Yellow Perfume Familiar
    (9300014, 2430115, 100, 1, 1), # Dark Eye from Another Dimension | Dimensional Pass
    (9001005, 4031013, 9000, 1, 1), # OctoPirate | Dark Marble
    (9300009, 2430115, 100, 1, 1), # Block Golem from Another Dimension | Dimensional Pass
    (4300004, 4000534, 7500, 1, 1), # Pink Perfume | Pink Perfume
    (4300004, 2870173, 100, 1, 1), # Pink Perfume | Pink Perfume Familiar
    (9300008, 2430115, 100, 1, 1), # Shadow Eye from Another Dimension | Dimensional Pass
    (4300007, 4000526, 7500, 1, 1), # Female Mannequin | Female Mannequin's Wig
    (4300007, 2870175, 100, 1, 1), # Female Mannequin | Female Mannequin Familiar
    (4300006, 4000525, 7500, 1, 1), # Kid Mannequin | Kid Mannequin's Bunny Suit
    (4300006, 2870174, 100, 1, 1), # Kid Mannequin | Kid Mannequin Familiar
    (9001006, 4031013, 9000, 1, 1), # OctoPirate | Dark Marble
    (9300010, 2430115, 100, 1, 1), # Rombot from Another Dimension | Dimensional Pass
    (4300009, 2870177, 100, 1, 1), # Latest Hits Compilation | Latest Hits Compilation Familiar
    (4300009, 4000528, 7500, 1, 1), # Latest Hits Compilation | Latest Hits Compilation
    (9300005, 2430115, 10000, 1, 1), # Ratz from Another Dimension | Dimensional Pass
    (4300008, 2870176, 100, 1, 1), # Male Mannequin | Male Mannequin Familiar
    (4300008, 4000527, 7500, 1, 1), # Male Mannequin | Male Mannequin's Fedora
    (4300011, 4000536, 7500, 1, 1), # Cheap Amplifier | Cheap Speakers
    (4300011, 2870179, 100, 1, 1), # Cheap Amplifier | Cheap Amplifier Familiar
    (9300007, 2430115, 100, 1, 1), # Bloctopus from Another Dimension | Dimensional Pass
    (4300010, 4000529, 7500, 1, 1), # Greatest Oldies | Greatest Oldies
    (4300010, 2870178, 100, 1, 1), # Greatest Oldies | Greatest Oldies Familiar
    (9300006, 2430115, 10000, 1, 1), # Black Ratz from Another Dimension | Dimensional Pass
    (4300013, 2870181, 100, 1, 1), # Spirit of Rock | Spirit of Rock Familiar
    (4300013, 2431655, 3000, 1, 1), # Spirit of Rock | Spirit of Rock's Soul Shard
    (4300012, 4000537, 7500, 1, 1), # Fancy Amplifier | Fancy Amplifier Cables
    (4300012, 2870180, 100, 1, 1), # Fancy Amplifier | Fancy Amplifier Familiar
    (4090000, 2870134, 100, 1, 1), # Iron Hog | Iron Hog Familiar
    (4090000, 2431174, 100, 1, 1), # Iron Hog | Medal of Honor
    (4090000, 4000039, 7500, 1, 1), # Iron Hog | Iron Hog's Metal Hoof
    (4090000, 4033055, 7500, 1, 1), # Iron Hog | Excavation Site Blueprint
    (8920103, 2000005, 100, 1, 1), # Crimson Queen | Power Elixir
    (8920103, 3010514, 100, 1, 1), # Crimson Queen | Crimson Queen's Throne
    (8920103, 4310029, 7500, 1, 1), # Crimson Queen | Crusader Coins
    (8920103, 2433103, 100, 1, 1), # Crimson Queen | Boss Medal of Honor
    (8920103, 2433591, 3000, 1, 1), # Crimson Queen | Crimson Queen's Soul Shard
    (8920103, 4310064, 7500, 1, 1), # Crimson Queen | Yggdrasil Rune
    (9001021, 4000589, 7500, 1, 1), # Wooden Training Doll | Wooden Training Doll Remnant
    (9001020, 4000588, 7500, 1, 1), # Straw Training Doll | Straw Training Doll Remnant
    (8880177, 4310029, 7500, 1, 1), # Final Music Box | Crusader Coins
    (9001023, 4000591, 7500, 1, 1), # Training Bellflower | Healthy Bellflower
    (210100, 2022053, 100, 1, 1), # Slime | Jujube
    (210100, 2061000, 100, 1, 1), # Slime | Arrow for Crossbow
    (210100, 4000010, 7500, 1, 1), # Slime | Slime Bubble
    (210100, 2022054, 100, 1, 1), # Slime | Pear
    (210100, 2872038, 100, 1, 1), # Slime | Slime Familiar
    (210100, 4020005, 7500, 1, 1), # Slime | Sapphire Ore
    (210100, 4000004, 7500, 1, 1), # Slime | Squishy Liquid
    (210100, 2000001, 100, 1, 1), # Slime | Orange Potion
    (210100, 2000003, 100, 1, 1), # Slime | Blue Potion
    (210100, 4010005, 7500, 1, 1), # Slime | Orihalcon Ore
    (210100, 2010009, 100, 1, 1), # Slime | Green Apple
    (210100, 2022055, 100, 1, 1), # Slime | Persimmon
    (9001022, 4000590, 7500, 1, 1), # Training Jar | Training Jar Fragment
    (9001009, 4032101, 7500, 1, 1), # Master of Disguise | The Lost Treasure
    (6220000, 4000032, 7500, 1, 1), # Dyle | Ligator Skin
    (6220000, 4000033, 7500, 1, 1), # Dyle | Croco Skin
    (9300018, 4033920, 10000, 1, 1), # Tutorial Jr. Sentinel | Jr. Sentinel Shellpiece
    (9000001, 4031013, 9000, 1, 1), # Zombie Lupin | Dark Marble
    (9001024, 4032326, 7500, 1, 1), # Primitive Thief Boar | Wooden Key
    (9001027, 4000593, 7500, 1, 1), # Decaying Coolie Zombie | Decaying Zombie Molar
    (5200002, 2870216, 100, 1, 1), # Fire Sentinel | Fire Sentinel Familiar
    (5200002, 4000085, 7500, 1, 1), # Fire Sentinel | Fire Sentinel Shellpiece
    (9001028, 4000594, 7500, 1, 1), # Secret Master Soul Teddy | Secret Master Soul Teddy Ghost
    (5200001, 2870215, 100, 1, 1), # Ice Sentinel | Ice Sentinel Familiar
    (5200001, 4000084, 7500, 1, 1), # Ice Sentinel | Ice Sentinel Shellpiece
    (5200000, 4031802, 7500, 1, 1), # Jr. Sentinel | Jr. Sentinel Shellpiece
    (5200000, 2001002, 100, 1, 1), # Jr. Sentinel | Very Special Sundae
    (5200000, 2001001, 100, 1, 1), # Jr. Sentinel | Ice Cream Pop
    (5200000, 2431174, 100, 1, 1), # Jr. Sentinel | Medal of Honor
    (5200000, 4021034, 100, 1, 1), # Jr. Sentinel | Fine Spell Essence
    (5200000, 4021032, 7500, 1, 1), # Jr. Sentinel | Mana Crystal
    (5200000, 2590004, 100, 1, 1), # Jr. Sentinel | Prototype Soul Enchanter
    (5200000, 2870214, 100, 1, 1), # Jr. Sentinel | Jr. Sentinel Familiar
    (5200000, 4021033, 100, 1, 1), # Jr. Sentinel | Basic Spell Essence
    (5200000, 4021035, 100, 1, 1), # Jr. Sentinel | Grand Spell Essence
    (4230300, 4021032, 7500, 1, 1), # Moon Bunny | Mana Crystal
    (4230300, 2000037, 100, 1, 1), # Moon Bunny | Sunrise Dew
    (4230300, 2870158, 100, 1, 1), # Moon Bunny | Moon Bunny Familiar
    (4230300, 4021031, 50, 1, 1), # Moon Bunny | Twisted Time
    (4230300, 2431174, 100, 1, 1), # Moon Bunny | Medal of Honor
    (4230300, 2590004, 100, 1, 1), # Moon Bunny | Prototype Soul Enchanter
    (4230300, 4021034, 100, 1, 1), # Moon Bunny | Fine Spell Essence
    (4230300, 4021035, 100, 1, 1), # Moon Bunny | Grand Spell Essence
    (4230300, 4034629, 7500, 1, 1), # Moon Bunny | Mugwort Juice
    (4230300, 4034633, 7500, 1, 1), # Moon Bunny | Rice Flour
    (4230300, 4021033, 100, 1, 1), # Moon Bunny | Basic Spell Essence
    (4230300, 2001528, 100, 1, 1), # Moon Bunny | Melting Cheese
    (3210450, 4000153, 7500, 1, 1), # Scuba Pepe | Snorkle
    (3210450, 2870088, 100, 1, 1), # Scuba Pepe | Scuba Pepe Familiar
    (2300100, 4020000, 7500, 1, 1), # Stirge | Garnet Ore
    (2300100, 2040024, 100, 1, 1), # Stirge | Scroll for Helmet for INT 100%
    (2300100, 2060000, 100, 1, 1), # Stirge | Arrow for Bow
    (2300100, 4000042, 7500, 1, 1), # Stirge | Stirge Wing
    (2300100, 2001535, 100, 1, 1), # Stirge | Lemon
    (2300100, 2044110, 100, 1, 1), # Stirge | Scroll for Two-Handed Axe for Accuracy 100%
    (2300100, 4003004, 7500, 1, 1), # Stirge | Stiff Feather
    (2300100, 2044010, 100, 1, 1), # Stirge | Scroll for Two-Handed Sword for Accuracy 100%
    (2300100, 2070000, 100, 1, 1), # Stirge | Subi Throwing-Stars
    (2300100, 2040800, 100, 1, 1), # Stirge | Scroll for Gloves for DEX 100%
    (2300100, 2870060, 100, 1, 1), # Stirge | Stirge Familiar
    (2300100, 4010001, 7500, 1, 1), # Stirge | Steel Ore
    (2300100, 2000000, 100, 1, 1), # Stirge | Red Potion
    (6230200, 2001002, 100, 1, 1); # Dark Pepe | Very Special Sundae

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (6230200, 4000057, 7500, 1, 1), # Dark Pepe | Dark Pepe Beak
    (6230200, 2434021, 100, 1, 1), # Dark Pepe | Medal of Honor
    (6230200, 2001001, 100, 1, 1), # Dark Pepe | Ice Cream Pop
    (6230200, 2590004, 100, 1, 1), # Dark Pepe | Prototype Soul Enchanter
    (8870004, 4021035, 100, 1, 1), # Bloodfang | Grand Spell Essence
    (8870004, 4021034, 100, 1, 1), # Bloodfang | Fine Spell Essence
    (8870004, 4021033, 100, 1, 1), # Bloodfang | Basic Spell Essence
    (8870004, 4021032, 7500, 1, 1), # Bloodfang | Mana Crystal
    (8870004, 4021031, 50, 1, 1), # Bloodfang | Twisted Time
    (8144008, 4021032, 7500, 1, 1), # Stone Goblin Guardian | Mana Crystal
    (8144008, 4033176, 7500, 1, 1), # Stone Goblin Guardian | Sunburst
    (8144008, 4021031, 50, 1, 1), # Stone Goblin Guardian | Twisted Time
    (8144008, 4000678, 7500, 1, 1), # Stone Goblin Guardian | Stone Goblin Guardian's Hammer
    (8144008, 4001687, 7500, 1, 1), # Stone Goblin Guardian | Thought-Provoking Keepsake
    (8144008, 4001686, 7500, 1, 1), # Stone Goblin Guardian | Plain Keepsake
    (8144008, 4021035, 100, 1, 1), # Stone Goblin Guardian | Grand Spell Essence
    (8144008, 4021034, 100, 1, 1), # Stone Goblin Guardian | Fine Spell Essence
    (8144008, 4021033, 100, 1, 1), # Stone Goblin Guardian | Basic Spell Essence
    (8144008, 4001685, 7500, 1, 1), # Stone Goblin Guardian | Well-Kept Keepsake
    (8870003, 4021035, 100, 1, 1), # Bloodfang | Grand Spell Essence
    (8870003, 4021032, 7500, 1, 1), # Bloodfang | Mana Crystal
    (8870003, 4021033, 100, 1, 1), # Bloodfang | Basic Spell Essence
    (8870003, 4021031, 50, 1, 1), # Bloodfang | Twisted Time
    (8870003, 4021034, 100, 1, 1), # Bloodfang | Fine Spell Essence
    (8870002, 4021032, 7500, 1, 1), # Elite Pikeman Skeleknight | Mana Crystal
    (8870002, 4021035, 100, 1, 1), # Elite Pikeman Skeleknight | Grand Spell Essence
    (8870002, 4021031, 50, 1, 1), # Elite Pikeman Skeleknight | Twisted Time
    (8870002, 4021033, 100, 1, 1), # Elite Pikeman Skeleknight | Basic Spell Essence
    (8870002, 4021034, 100, 1, 1), # Elite Pikeman Skeleknight | Fine Spell Essence
    (8870001, 4021031, 50, 1, 1), # Elite Pikeman Skeleknight | Twisted Time
    (8870001, 4021032, 7500, 1, 1), # Elite Pikeman Skeleknight | Mana Crystal
    (8870001, 4021034, 100, 1, 1), # Elite Pikeman Skeleknight | Fine Spell Essence
    (8870001, 4021033, 100, 1, 1), # Elite Pikeman Skeleknight | Basic Spell Essence
    (8870001, 4021035, 100, 1, 1), # Elite Pikeman Skeleknight | Grand Spell Essence
    (8144004, 4021031, 50, 1, 1), # Blue Goblin | Twisted Time
    (8144004, 4000564, 7500, 1, 1), # Blue Goblin | Blue Goblin Crown
    (8144004, 4001687, 7500, 1, 1), # Blue Goblin | Thought-Provoking Keepsake
    (8144004, 4021034, 100, 1, 1), # Blue Goblin | Fine Spell Essence
    (8144004, 4001686, 7500, 1, 1), # Blue Goblin | Plain Keepsake
    (8144004, 4021033, 100, 1, 1), # Blue Goblin | Basic Spell Essence
    (8144004, 4001685, 7500, 1, 1), # Blue Goblin | Well-Kept Keepsake
    (8144004, 4021032, 7500, 1, 1), # Blue Goblin | Mana Crystal
    (8144004, 4021035, 100, 1, 1), # Blue Goblin | Grand Spell Essence
    (8144004, 4033176, 7500, 1, 1), # Blue Goblin | Sunburst
    (3110102, 2001002, 100, 1, 1), # Ratz | Very Special Sundae
    (3110102, 4021032, 7500, 1, 1), # Ratz | Mana Crystal
    (3110102, 2870070, 100, 1, 1), # Ratz | Ratz Familiar
    (3110102, 2431174, 100, 1, 1), # Ratz | Medal of Honor
    (3110102, 4021035, 100, 1, 1), # Ratz | Grand Spell Essence
    (3110102, 4021033, 100, 1, 1), # Ratz | Basic Spell Essence
    (3110102, 4021034, 100, 1, 1), # Ratz | Fine Spell Essence
    (3110102, 2001001, 100, 1, 1), # Ratz | Ice Cream Pop
    (3110102, 4000095, 7500, 1, 1), # Ratz | Rat Trap
    (3110102, 2590004, 100, 1, 1), # Ratz | Prototype Soul Enchanter
    (8144005, 4001686, 7500, 1, 1), # Red Goblin | Plain Keepsake
    (8144005, 4000563, 7500, 1, 1), # Red Goblin | Red Goblin Axe
    (8144005, 4021033, 100, 1, 1), # Red Goblin | Basic Spell Essence
    (8144005, 4001687, 7500, 1, 1), # Red Goblin | Thought-Provoking Keepsake
    (8144005, 4021034, 100, 1, 1), # Red Goblin | Fine Spell Essence
    (8144005, 4021035, 100, 1, 1), # Red Goblin | Grand Spell Essence
    (8144005, 4033176, 7500, 1, 1), # Red Goblin | Sunburst
    (8144005, 4021031, 50, 1, 1), # Red Goblin | Twisted Time
    (8144005, 4021032, 7500, 1, 1), # Red Goblin | Mana Crystal
    (8144005, 4001685, 7500, 1, 1), # Red Goblin | Well-Kept Keepsake
    (8144006, 4021032, 7500, 1, 1), # Stone Goblin | Mana Crystal
    (8144006, 4033176, 7500, 1, 1), # Stone Goblin | Sunburst
    (8144006, 4000565, 7500, 1, 1), # Stone Goblin | Stone Goblin's Red Underwear
    (8144006, 4001686, 7500, 1, 1), # Stone Goblin | Plain Keepsake
    (8144006, 4021035, 100, 1, 1), # Stone Goblin | Grand Spell Essence
    (8144006, 4021033, 100, 1, 1), # Stone Goblin | Basic Spell Essence
    (8144006, 4001685, 7500, 1, 1), # Stone Goblin | Well-Kept Keepsake
    (8144006, 4001687, 7500, 1, 1), # Stone Goblin | Thought-Provoking Keepsake
    (8144006, 4021031, 50, 1, 1), # Stone Goblin | Twisted Time
    (8144006, 4021034, 100, 1, 1), # Stone Goblin | Fine Spell Essence
    (3110100, 4032463, 7500, 1, 1), # Ligator | Document with Clue
    (3110100, 2001527, 100, 1, 1), # Ligator | Unagi
    (3110100, 2590004, 100, 1, 1), # Ligator | Prototype Soul Enchanter
    (3110100, 2001526, 100, 1, 1), # Ligator | Pure Water
    (3110100, 4000032, 7500, 1, 1), # Ligator | Ligator Skin
    (3110100, 2431174, 100, 1, 1), # Ligator | Medal of Honor
    (3110100, 2710002, 100, 1, 1), # Ligator | Master Craftsman's Cube
    (3110100, 2870068, 100, 1, 1), # Ligator | Ligator Familiar
    (3110100, 4031164, 7500, 1, 1), # Ligator | Alligator Skin Pouch
    (8144007, 4021035, 100, 1, 1), # Strong Stone Goblin | Grand Spell Essence
    (8144007, 4001685, 7500, 1, 1), # Strong Stone Goblin | Well-Kept Keepsake
    (8144007, 4021032, 7500, 1, 1), # Strong Stone Goblin | Mana Crystal
    (8144007, 4021033, 100, 1, 1), # Strong Stone Goblin | Basic Spell Essence
    (8144007, 4033176, 7500, 1, 1), # Strong Stone Goblin | Sunburst
    (8144007, 4001687, 7500, 1, 1), # Strong Stone Goblin | Thought-Provoking Keepsake
    (8144007, 4000677, 7500, 1, 1), # Strong Stone Goblin | Strong Stone Goblin Crown
    (8144007, 4021034, 100, 1, 1), # Strong Stone Goblin | Fine Spell Essence
    (8144007, 4021031, 50, 1, 1), # Strong Stone Goblin | Twisted Time
    (8144007, 4001686, 7500, 1, 1), # Strong Stone Goblin | Plain Keepsake
    (8144000, 4021031, 50, 1, 1), # Wild Monkey | Twisted Time
    (8144000, 4021034, 100, 1, 1), # Wild Monkey | Fine Spell Essence
    (8144000, 4000561, 7500, 1, 1), # Wild Monkey | Banana Peel
    (8144000, 4021035, 100, 1, 1), # Wild Monkey | Grand Spell Essence
    (8144000, 4021032, 7500, 1, 1), # Wild Monkey | Mana Crystal
    (8144000, 4021033, 100, 1, 1), # Wild Monkey | Basic Spell Essence
    (8144001, 4001686, 7500, 1, 1), # Mama Monkey | Plain Keepsake
    (8144001, 4000562, 7500, 1, 1), # Mama Monkey | Baby Monkey's Bottle
    (8144001, 4021033, 100, 1, 1), # Mama Monkey | Basic Spell Essence
    (8144001, 4021035, 100, 1, 1), # Mama Monkey | Grand Spell Essence
    (8144001, 4001687, 7500, 1, 1), # Mama Monkey | Thought-Provoking Keepsake
    (8144001, 4021034, 100, 1, 1), # Mama Monkey | Fine Spell Essence
    (8144001, 4021031, 50, 1, 1), # Mama Monkey | Twisted Time
    (8144001, 4001685, 7500, 1, 1), # Mama Monkey | Well-Kept Keepsake
    (8144001, 4021032, 7500, 1, 1), # Mama Monkey | Mana Crystal
    (8144001, 4033177, 7500, 1, 1), # Mama Monkey | GPS TourGuide
    (8144002, 4001686, 7500, 1, 1), # Teeny White Monkey | Plain Keepsake
    (8144002, 4021032, 7500, 1, 1), # Teeny White Monkey | Mana Crystal
    (8144002, 4001685, 7500, 1, 1), # Teeny White Monkey | Well-Kept Keepsake
    (8144002, 4021031, 50, 1, 1), # Teeny White Monkey | Twisted Time
    (8144002, 4021033, 100, 1, 1), # Teeny White Monkey | Basic Spell Essence
    (8144002, 4033175, 7500, 1, 1), # Teeny White Monkey | Master Loong's Rosary
    (8144002, 4000559, 7500, 1, 1), # Teeny White Monkey | White Baby Monkey Tail
    (8144002, 4021034, 100, 1, 1), # Teeny White Monkey | Fine Spell Essence
    (8144002, 4001687, 7500, 1, 1), # Teeny White Monkey | Thought-Provoking Keepsake
    (8144002, 4021035, 100, 1, 1), # Teeny White Monkey | Grand Spell Essence
    (8144003, 4001686, 7500, 1, 1), # Mean Mama Monkey | Plain Keepsake
    (8144003, 4021031, 50, 1, 1), # Mean Mama Monkey | Twisted Time
    (8144003, 4000560, 7500, 1, 1), # Mean Mama Monkey | White Mama Monkey Fur
    (8144003, 4001687, 7500, 1, 1), # Mean Mama Monkey | Thought-Provoking Keepsake
    (8144003, 4021032, 7500, 1, 1), # Mean Mama Monkey | Mana Crystal
    (8144003, 4021033, 100, 1, 1), # Mean Mama Monkey | Basic Spell Essence
    (8144003, 4021035, 100, 1, 1), # Mean Mama Monkey | Grand Spell Essence
    (8144003, 4001685, 7500, 1, 1), # Mean Mama Monkey | Well-Kept Keepsake
    (8144003, 4021034, 100, 1, 1), # Mean Mama Monkey | Fine Spell Essence
    (2600231, 2049000, 100, 1, 1), # Jr. Balrog | Pure Clean Slate Scroll 1%
    (2600231, 4020008, 7500, 1, 1), # Jr. Balrog | Black Crystal Ore
    (2600231, 2870311, 100, 1, 1), # Jr. Balrog | Jr. Balrog Familiar
    (2600231, 2512054, 100, 1, 1), # Jr. Balrog | Mana Boost Potion Recipe
    (2600231, 2070007, 100, 1, 1), # Jr. Balrog | Hwabi Throwing-Stars
    (3401011, 4000764, 7500, 1, 1), # Captain Darkgoo | Giant Octopus Tentacle
    (3401011, 4000763, 7500, 1, 1), # Captain Darkgoo | Orange Ink Sack
    (3401011, 4000762, 7500, 1, 1), # Captain Darkgoo | Blue Ink Sack
    (6300000, 4021032, 7500, 1, 1), # Yeti | Mana Crystal
    (6300000, 2590004, 100, 1, 1), # Yeti | Prototype Soul Enchanter
    (6300000, 4021034, 100, 1, 1), # Yeti | Fine Spell Essence
    (6300000, 4021031, 50, 1, 1), # Yeti | Twisted Time
    (6300000, 4021033, 100, 1, 1), # Yeti | Basic Spell Essence
    (6300000, 2870259, 100, 1, 1), # Yeti | Yeti Familiar
    (6300000, 4000049, 7500, 1, 1), # Yeti | Yeti Horn
    (6300000, 2001528, 100, 1, 1), # Yeti | Melting Cheese
    (6300000, 2434021, 100, 1, 1), # Yeti | Medal of Honor
    (6300000, 4021035, 100, 1, 1), # Yeti | Grand Spell Essence
    (6300000, 2000037, 100, 1, 1), # Yeti | Sunrise Dew
    (2600230, 4007001, 7500, 1, 1), # Taurospear | Magic Powder (White)
    (2600230, 4007004, 7500, 1, 1), # Taurospear | Magic Powder (Yellow)
    (2600230, 4000046, 7500, 1, 1), # Taurospear | Taurospear Horn
    (2600230, 2001001, 100, 1, 1), # Taurospear | Ice Cream Pop
    (2600230, 4006001, 7500, 1, 1), # Taurospear | The Summoning Rock
    (2600230, 2060000, 100, 1, 1), # Taurospear | Arrow for Bow
    (2600230, 2048001, 100, 1, 1), # Taurospear | Scroll for Pet Equip. for Speed 60%
    (2600230, 4001112, 7500, 1, 1), # Taurospear | [Storybook] Indecipherable Book
    (2600230, 2048000, 100, 1, 1), # Taurospear | Scroll for Pet Equip. for Speed 100%
    (2600230, 2022057, 100, 1, 1), # Taurospear | Tofu
    (2600230, 2043212, 100, 1, 1), # Taurospear | Scroll for One-Handed BW for Accuracy 60%
    (2600230, 4020008, 7500, 1, 1), # Taurospear | Black Crystal Ore
    (2600230, 2061000, 100, 1, 1), # Taurospear | Arrow for Crossbow
    (2600230, 4010003, 7500, 1, 1), # Taurospear | Adamantium Ore
    (2600230, 2044114, 100, 1, 1), # Taurospear | Scroll for Two-Handed Axe for Accuracy 10%
    (2600230, 2870280, 100, 1, 1), # Taurospear | Taurospear Familiar
    (3401010, 4033627, 7500, 1, 1), # Seashell Octopus Slime | Travel Bag
    (3401010, 4000763, 7500, 1, 1), # Seashell Octopus Slime | Orange Ink Sack
    (2600229, 2870279, 100, 1, 1), # Tauromacis | Tauromacis Familiar
    (2600229, 2512058, 100, 1, 1), # Tauromacis | Intelligence Boost Potion Recipe
    (2600229, 2061000, 100, 1, 1), # Tauromacis | Arrow for Crossbow
    (2600229, 4010006, 7500, 1, 1), # Tauromacis | Gold Ore
    (2600229, 4007004, 7500, 1, 1), # Tauromacis | Magic Powder (Yellow)
    (2600229, 4006000, 7500, 1, 1), # Tauromacis | The Magic Rock
    (2600229, 2022057, 100, 1, 1), # Tauromacis | Tofu
    (2600229, 4000028, 7500, 1, 1), # Tauromacis | Tauromacis Horn
    (2600229, 2000006, 100, 1, 1), # Tauromacis | Mana Elixir
    (2600229, 2001527, 100, 1, 1), # Tauromacis | Unagi
    (2600229, 2048002, 100, 1, 1), # Tauromacis | Scroll for Pet Equip. for Speed 10%
    (2600229, 2001526, 100, 1, 1), # Tauromacis | Pure Water
    (2600229, 4007003, 7500, 1, 1), # Tauromacis | Magic Powder (Green)
    (2600229, 2040801, 100, 1, 1), # Tauromacis | Scroll for Gloves for DEX 60%
    (2600229, 2060000, 100, 1, 1), # Tauromacis | Arrow for Bow
    (2600229, 2040421, 100, 1, 1), # Tauromacis | Scroll for Topwear for HP 60%
    (2600229, 2049000, 100, 1, 1), # Tauromacis | Pure Clean Slate Scroll 1%
    (3401009, 4000762, 7500, 1, 1), # Starfish Octopus Slime | Blue Ink Sack
    (8820100, 4021032, 7500, 1, 1), # Chaos Pink Bean | Mana Crystal
    (8820100, 4021033, 100, 1, 1), # Chaos Pink Bean | Basic Spell Essence
    (8820100, 4021034, 100, 1, 1), # Chaos Pink Bean | Fine Spell Essence
    (8820100, 4021031, 50, 1, 1), # Chaos Pink Bean | Twisted Time
    (8820100, 2431661, 3000, 1, 1), # Chaos Pink Bean | Pink Bean's Soul Shard
    (8820100, 4310029, 7500, 1, 1), # Chaos Pink Bean | Crusader Coins
    (8820100, 2000005, 100, 1, 1), # Chaos Pink Bean | Power Elixir
    (8820100, 2433103, 100, 1, 1), # Chaos Pink Bean | Boss Medal of Honor
    (8820100, 4021020, 10, 1, 1), # Chaos Pink Bean | Confusion Fragment
    (8820100, 4021035, 100, 1, 1), # Chaos Pink Bean | Grand Spell Essence
    (2600228, 2001526, 100, 1, 1), # Dark Drake | Pure Water
    (2600228, 2045201, 100, 1, 1), # Dark Drake | Scroll for Dual Bowgun ATT 60%
    (2600228, 4007007, 7500, 1, 1), # Dark Drake | Magic Powder (Black)
    (2600228, 4001077, 7500, 1, 1), # Dark Drake | Tough Dragon Skin
    (2600228, 2044001, 100, 1, 1), # Dark Drake | Scroll for Two-handed Sword for ATT 60%
    (2600228, 4007003, 7500, 1, 1), # Dark Drake | Magic Powder (Green)
    (2600228, 4010003, 7500, 1, 1), # Dark Drake | Adamantium Ore
    (2600228, 2043301, 100, 1, 1), # Dark Drake | Scroll for Dagger for ATT 60%
    (2600228, 2000002, 100, 1, 1), # Dark Drake | White Potion
    (2600228, 4000186, 7500, 1, 1), # Dark Drake | Dark Drake's Horn
    (2600228, 2043110, 100, 1, 1), # Dark Drake | Scroll for One-Handed Axe for Accuracy 100%
    (2600228, 2000006, 100, 1, 1), # Dark Drake | Mana Elixir
    (2600228, 4010005, 7500, 1, 1), # Dark Drake | Orihalcon Ore
    (2600228, 2044801, 100, 1, 1), # Dark Drake | Scroll for Knuckle for Attack 60%
    (2600228, 2022057, 100, 1, 1), # Dark Drake | Tofu
    (2600228, 2040024, 100, 1, 1), # Dark Drake | Scroll for Helmet for INT 100%
    (2600228, 4000030, 7500, 1, 1), # Dark Drake | Dragon Skin
    (2600228, 2070005, 100, 1, 1), # Dark Drake | Steely Throwing-Knives
    (2600228, 2044501, 100, 1, 1), # Dark Drake | Scroll for Bow for ATT 60%
    (2600228, 4020007, 7500, 1, 1), # Dark Drake | Diamond Ore
    (2600228, 2870257, 100, 1, 1), # Dark Drake | Dark Drake Familiar
    (2600228, 4020008, 7500, 1, 1), # Dark Drake | Black Crystal Ore
    (3401008, 4033622, 7500, 1, 1), # Flying Fish Slime | Willy's Ball
    (3401008, 4000761, 7500, 1, 1), # Flying Fish Slime | Flying Fish
    (6300003, 2870260, 100, 1, 1), # Punco | Punco Familiar
    (2600227, 2332000, 100, 1, 1), # Ice Drake | Glaze Capsule
    (2600227, 2041014, 100, 1, 1), # Ice Drake | Scroll for Cape for STR 10%
    (2600227, 2000006, 100, 1, 1), # Ice Drake | Mana Elixir
    (2600227, 2022057, 100, 1, 1), # Ice Drake | Tofu
    (2600227, 4010004, 7500, 1, 1), # Ice Drake | Silver Ore
    (2600227, 2070003, 100, 1, 1), # Ice Drake | Kumbi Throwing-Stars
    (2600227, 4007000, 7500, 1, 1), # Ice Drake | Magic Powder (Brown)
    (2600227, 4007004, 7500, 1, 1), # Ice Drake | Magic Powder (Yellow)
    (2600227, 4020007, 7500, 1, 1), # Ice Drake | Diamond Ore
    (2600227, 2044414, 100, 1, 1), # Ice Drake | Scroll for Pole-Arm for Accuracy 10%
    (2600227, 2040328, 100, 1, 1), # Ice Drake | Scroll for Earring for HP 10%
    (2600227, 2040301, 100, 1, 1), # Ice Drake | Scroll for Earring for INT 60%
    (2600227, 4000030, 7500, 1, 1), # Ice Drake | Dragon Skin
    (2600227, 4001077, 7500, 1, 1), # Ice Drake | Tough Dragon Skin
    (2600227, 2040002, 100, 1, 1), # Ice Drake | Scroll for Helmet for DEF 10%
    (2600227, 2043015, 100, 1, 1), # Ice Drake | Scroll for One-Handed Sword for Accuracy 100%
    (2600227, 2000002, 100, 1, 1), # Ice Drake | White Potion
    (2600227, 2001527, 100, 1, 1), # Ice Drake | Unagi
    (2600227, 2870256, 100, 1, 1), # Ice Drake | Ice Drake Familiar
    (2600227, 4020005, 7500, 1, 1), # Ice Drake | Sapphire Ore
    (2600227, 4000185, 7500, 1, 1), # Ice Drake | Ice Backbone
    (2600227, 4010006, 7500, 1, 1), # Ice Drake | Gold Ore
    (2600227, 2061000, 100, 1, 1), # Ice Drake | Arrow for Crossbow
    (2600227, 4006000, 7500, 1, 1), # Ice Drake | The Magic Rock
    (6300004, 2870261, 100, 1, 1), # Pachu | Pachu Familiar
    (2600226, 4010006, 7500, 1, 1), # Wild Kargo | Gold Ore
    (2600226, 2040927, 100, 1, 1), # Wild Kargo | Scroll for Shield for HP 60%
    (2600226, 2044801, 100, 1, 1), # Wild Kargo | Scroll for Knuckle for Attack 60%
    (2600226, 2870249, 100, 1, 1), # Wild Kargo | Wild Kargo Familiar
    (2600226, 2060000, 100, 1, 1), # Wild Kargo | Arrow for Bow
    (2600226, 4000027, 7500, 1, 1), # Wild Kargo | Wild Kargo Eye
    (2600226, 2040414, 100, 1, 1), # Wild Kargo | Scroll for Topwear for LUK 100%
    (2600226, 4007001, 7500, 1, 1), # Wild Kargo | Magic Powder (White)
    (2600226, 2049100, 100, 1, 1), # Wild Kargo | Chaos Scroll 60%
    (2600226, 2040824, 100, 1, 1), # Wild Kargo | Scroll for Gloves for HP 60%
    (2600226, 2070005, 100, 1, 1), # Wild Kargo | Steely Throwing-Knives
    (2600226, 2022057, 100, 1, 1), # Wild Kargo | Tofu
    (2600226, 2044809, 100, 1, 1), # Wild Kargo | Scroll for Knuckle for Accuracy 10%
    (2600226, 4020008, 7500, 1, 1), # Wild Kargo | Black Crystal Ore
    (2600226, 4007005, 7500, 1, 1), # Wild Kargo | Magic Powder (Purple)
    (2600226, 2000006, 100, 1, 1), # Wild Kargo | Mana Elixir
    (2600226, 2040323, 100, 1, 1), # Wild Kargo | Scroll for Earring for LUK 10%
    (6300005, 2870262, 100, 1, 1), # Zombie Mushmom | Zombie Mushmom Familiar
    (6300005, 2070006, 100, 1, 1), # Zombie Mushmom | Ilbi Throwing-Stars
    (6300005, 4000176, 7500, 1, 1), # Zombie Mushmom | Poisonous Mushroom
    (2600225, 2044212, 100, 1, 1), # Red Drake | Scroll for Two-Handed BW for Accuracy 60%
    (2600225, 4000030, 7500, 1, 1), # Red Drake | Dragon Skin
    (2600225, 4010005, 7500, 1, 1), # Red Drake | Orihalcon Ore
    (2600225, 4020007, 7500, 1, 1), # Red Drake | Diamond Ore
    (2600225, 4007000, 7500, 1, 1), # Red Drake | Magic Powder (Brown)
    (2600225, 2050099, 100, 1, 1), # Red Drake | Flaming feather
    (2600225, 4000014, 7500, 1, 1), # Red Drake | Drake Skull
    (2600225, 2040612, 100, 1, 1), # Red Drake | Scroll for Bottomwear for DEX 10%
    (2600225, 2040501, 100, 1, 1), # Red Drake | Scroll for Overall Armor for DEX 60%
    (2600225, 2331000, 100, 1, 1), # Red Drake | Blaze Capsule
    (2600225, 2040901, 100, 1, 1), # Red Drake | Scroll for Shield for DEF 60%
    (2600225, 4001077, 7500, 1, 1), # Red Drake | Tough Dragon Skin
    (2600225, 2000002, 100, 1, 1), # Red Drake | White Potion
    (2600225, 2022057, 100, 1, 1), # Red Drake | Tofu
    (2600225, 2043210, 100, 1, 1), # Red Drake | Scroll for One-Handed BW for Accuracy 100%
    (2600225, 4007002, 7500, 1, 1), # Red Drake | Magic Powder (Blue)
    (2600225, 2870235, 100, 1, 1), # Red Drake | Red Drake Familiar
    (2600225, 2001526, 100, 1, 1), # Red Drake | Pure Water
    (2600225, 2000006, 100, 1, 1), # Red Drake | Mana Elixir
    (2600224, 4000014, 7500, 1, 1), # Drake | Drake Skull
    (2600224, 2060000, 100, 1, 1), # Drake | Arrow for Bow
    (2600224, 2041017, 100, 1, 1), # Drake | Scroll for Cape for INT 10%
    (2600224, 4007000, 7500, 1, 1), # Drake | Magic Powder (Brown)
    (2600224, 2040928, 100, 1, 1), # Drake | Scroll for Shield for HP 10%
    (2600224, 4007002, 7500, 1, 1), # Drake | Magic Powder (Blue)
    (2600224, 4000030, 7500, 1, 1), # Drake | Dragon Skin
    (2600224, 2041022, 100, 1, 1), # Drake | Scroll for Cape for LUK 60%
    (2600224, 2001526, 100, 1, 1), # Drake | Pure Water
    (2600224, 2041007, 100, 1, 1), # Drake | Scroll for Cape for HP 60%
    (2600224, 2012003, 500, 1, 1), # Drake | Drake's Meat
    (2600224, 2012000, 500, 1, 1), # Drake | Drake's Blood
    (2600224, 2061000, 100, 1, 1), # Drake | Arrow for Crossbow
    (2600224, 4020007, 7500, 1, 1), # Drake | Diamond Ore
    (2600224, 2000002, 100, 1, 1), # Drake | White Potion
    (2600224, 2022057, 100, 1, 1), # Drake | Tofu
    (2600224, 2000006, 100, 1, 1), # Drake | Mana Elixir
    (2600224, 2870203, 100, 1, 1), # Drake | Drake Familiar
    (2600224, 4001077, 7500, 1, 1), # Drake | Tough Dragon Skin
    (2600224, 4010006, 7500, 1, 1), # Drake | Gold Ore
    (2600224, 2043212, 100, 1, 1), # Drake | Scroll for One-Handed BW for Accuracy 60%
    (4130103, 2001002, 100, 1, 1), # Rombot | Very Special Sundae
    (4130103, 2870129, 100, 1, 1), # Rombot | Rombot Familiar
    (4130103, 4021035, 100, 1, 1), # Rombot | Grand Spell Essence
    (4130103, 4021032, 7500, 1, 1), # Rombot | Mana Crystal
    (4130103, 4021033, 100, 1, 1), # Rombot | Basic Spell Essence
    (4130103, 2001001, 100, 1, 1), # Rombot | Ice Cream Pop
    (4130103, 2590004, 100, 1, 1), # Rombot | Prototype Soul Enchanter
    (4130103, 2434021, 100, 1, 1), # Rombot | Medal of Honor
    (4130103, 4000124, 7500, 1, 1), # Rombot | Rombot's Memory Card
    (4130103, 4000616, 7500, 1, 1), # Rombot | Vent Pipe
    (4130103, 4021034, 100, 1, 1), # Rombot | Fine Spell Essence
    (8220021, 4021034, 100, 1, 1), # Corrupted Time Monk Trainee | Fine Spell Essence
    (8220021, 4021033, 100, 1, 1), # Corrupted Time Monk Trainee | Basic Spell Essence
    (8220021, 4021032, 7500, 1, 1), # Corrupted Time Monk Trainee | Mana Crystal
    (8220021, 4021040, 7500, 1, 1), # Corrupted Time Monk Trainee | Dimension Fragment
    (8220021, 2434021, 100, 1, 1), # Corrupted Time Monk Trainee | Medal of Honor
    (8220021, 4009114, 7500, 1, 1), # Corrupted Time Monk Trainee | Qualm Cloth
    (8220021, 4021035, 100, 1, 1), # Corrupted Time Monk Trainee | Grand Spell Essence
    (8220021, 4310085, 7500, 1, 1), # Corrupted Time Monk Trainee | Time Coin
    (8220021, 2001554, 100, 1, 1), # Corrupted Time Monk Trainee | Sunset Dew
    (8220021, 2000036, 100, 1, 1), # Corrupted Time Monk Trainee | Reindeer Milk
    (8220021, 4021031, 50, 1, 1), # Corrupted Time Monk Trainee | Twisted Time
    (8220021, 2590004, 100, 1, 1), # Corrupted Time Monk Trainee | Prototype Soul Enchanter
    (4130102, 4000062, 7500, 1, 1), # Dark Nependeath | Dark Nependeath Seed
    (8220020, 2000036, 100, 1, 1), # Corrupted Chief Memory Guardian | Reindeer Milk
    (8220020, 4021033, 100, 1, 1), # Corrupted Chief Memory Guardian | Basic Spell Essence
    (8220020, 4021031, 50, 1, 1), # Corrupted Chief Memory Guardian | Twisted Time
    (8220020, 4021035, 100, 1, 1), # Corrupted Chief Memory Guardian | Grand Spell Essence
    (8220020, 4021032, 7500, 1, 1), # Corrupted Chief Memory Guardian | Mana Crystal
    (8220020, 4021020, 10, 1, 1), # Corrupted Chief Memory Guardian | Confusion Fragment
    (8220020, 4021034, 100, 1, 1), # Corrupted Chief Memory Guardian | Fine Spell Essence
    (8220020, 2001554, 100, 1, 1), # Corrupted Chief Memory Guardian | Sunset Dew
    (8220020, 2590004, 100, 1, 1), # Corrupted Chief Memory Guardian | Prototype Soul Enchanter
    (8220020, 2434021, 100, 1, 1), # Corrupted Chief Memory Guardian | Medal of Honor
    (8220017, 4021032, 7500, 1, 1), # Corrupted Cornian | Mana Crystal
    (8220017, 4021033, 100, 1, 1), # Corrupted Cornian | Basic Spell Essence
    (8220017, 4021034, 100, 1, 1), # Corrupted Cornian | Fine Spell Essence
    (8220017, 4021031, 50, 1, 1), # Corrupted Cornian | Twisted Time
    (8220017, 4021035, 100, 1, 1), # Corrupted Cornian | Grand Spell Essence
    (8220016, 4021031, 50, 1, 1), # Corrupted Wyvern | Twisted Time
    (8220016, 4021032, 7500, 1, 1), # Corrupted Wyvern | Mana Crystal
    (8220016, 4021034, 100, 1, 1), # Corrupted Wyvern | Fine Spell Essence
    (8220016, 4021033, 100, 1, 1), # Corrupted Wyvern | Basic Spell Essence
    (8220016, 4021035, 100, 1, 1), # Corrupted Wyvern | Grand Spell Essence
    (8220019, 4021034, 100, 1, 1), # Corrupted Kentaurus | Fine Spell Essence
    (8220019, 4021033, 100, 1, 1), # Corrupted Kentaurus | Basic Spell Essence
    (8220019, 4021035, 100, 1, 1), # Corrupted Kentaurus | Grand Spell Essence
    (8220019, 4021032, 7500, 1, 1), # Corrupted Kentaurus | Mana Crystal
    (8220019, 4021031, 50, 1, 1), # Corrupted Kentaurus | Twisted Time
    (8220018, 4021035, 100, 1, 1), # Corrupted Skelegon | Grand Spell Essence
    (8220018, 4021033, 100, 1, 1), # Corrupted Skelegon | Basic Spell Essence
    (8220018, 4021034, 100, 1, 1), # Corrupted Skelegon | Fine Spell Essence
    (8220018, 4021031, 50, 1, 1), # Corrupted Skelegon | Twisted Time
    (8220018, 4021032, 7500, 1, 1), # Corrupted Skelegon | Mana Crystal
    (3401003, 4033626, 7500, 1, 1), # Violet Clam Slime | Island Drum
    (3401003, 4000756, 7500, 1, 1), # Violet Clam Slime | Violet Clam
    (3401003, 4033631, 7500, 1, 1), # Violet Clam Slime | Lighting Battery
    (3401002, 4000755, 7500, 1, 1), # Emerald Clam Slime | Emerald Clam
    (3401001, 4033623, 7500, 1, 1), # Coconut Slime | Goldychee Fruit
    (3401001, 4033624, 7500, 1, 1), # Coconut Slime | Goldygranate Fruit
    (3401001, 4000754, 7500, 1, 1), # Coconut Slime | Coconut
    (3401001, 4033622, 7500, 1, 1), # Coconut Slime | Willy's Ball
    (3401000, 4000753, 7500, 1, 1), # Palm Tree Slime | Palm Leaf
    (3401000, 4033622, 7500, 1, 1), # Palm Tree Slime | Willy's Ball
    (3401007, 4000760, 7500, 1, 1), # Shrimp Slime | Fried Shrimp Bite
    (8220001, 4310029, 7500, 1, 1), # Snowman | Crusader Coins
    (8220001, 4000049, 7500, 1, 1), # Snowman | Yeti Horn
    (8220001, 4021033, 100, 1, 1), # Snowman | Basic Spell Essence
    (8220001, 4000257, 7500, 1, 1), # Snowman | Yeti Key Chain
    (8220001, 4021032, 7500, 1, 1), # Snowman | Mana Crystal
    (8220001, 4000048, 7500, 1, 1), # Snowman | Jr. Yeti Skin
    (8220001, 4000051, 7500, 1, 1), # Snowman | Hector Tail
    (8220001, 4021035, 100, 1, 1), # Snowman | Grand Spell Essence
    (8220001, 4021034, 100, 1, 1), # Snowman | Fine Spell Essence
    (8220001, 4000021, 7500, 1, 1), # Snowman | Leather
    (8220001, 4021031, 50, 1, 1), # Snowman | Twisted Time
    (3401006, 4000759, 7500, 1, 1), # Blue Tube Slime | Blue Inner Tube
    (8220000, 4000074, 7500, 1, 1), # Eliza | Lucida Tail
    (8220000, 4000073, 7500, 1, 1), # Eliza | Solid Horn
    (3401005, 4000758, 7500, 1, 1), # Red Tube Slime | Red Inner Tube
    (3401004, 4033630, 7500, 1, 1), # Seagull Slime | Super Expensive Ring
    (3401004, 4000757, 7500, 1, 1), # Seagull Slime | Seagull Baggy
    (4230500, 2590004, 100, 1, 1), # Chipmunk | Prototype Soul Enchanter
    (4230500, 2870160, 100, 1, 1), # Chipmunk | Chipmunk Familiar
    (4230500, 4021034, 100, 1, 1), # Chipmunk | Fine Spell Essence
    (4230500, 4021031, 50, 1, 1), # Chipmunk | Twisted Time
    (4230500, 2000037, 100, 1, 1), # Chipmunk | Sunrise Dew
    (4230500, 2431174, 100, 1, 1), # Chipmunk | Medal of Honor
    (4230500, 2511106, 100, 1, 1), # Chipmunk | Angelic Blessing Recipe
    (4230500, 2049401, 100, 1, 1), # Chipmunk | Potential Scroll
    (4230500, 4021035, 100, 1, 1), # Chipmunk | Grand Spell Essence
    (4230500, 4000276, 7500, 1, 1), # Chipmunk | Acorn
    (4230500, 4000021, 7500, 1, 1), # Chipmunk | Leather
    (4230500, 4021033, 100, 1, 1), # Chipmunk | Basic Spell Essence
    (4230500, 2001528, 100, 1, 1), # Chipmunk | Melting Cheese
    (4230500, 4021032, 7500, 1, 1), # Chipmunk | Mana Crystal
    (8900003, 4310029, 7500, 1, 1), # Chaos Pierre | Crusader Coins
    (4230501, 4021035, 100, 1, 1), # Red Porky | Grand Spell Essence
    (4230501, 2870161, 100, 1, 1), # Red Porky | Red Porky Familiar
    (4230501, 2001526, 100, 1, 1), # Red Porky | Pure Water
    (4230501, 2060000, 100, 1, 1), # Red Porky | Arrow for Bow
    (4230501, 2431174, 100, 1, 1), # Red Porky | Medal of Honor
    (4230501, 4000277, 7500, 1, 1), # Red Porky | Thimble
    (4230501, 4021034, 100, 1, 1), # Red Porky | Fine Spell Essence
    (4230501, 2000002, 100, 1, 1), # Red Porky | White Potion
    (4230501, 4021033, 100, 1, 1), # Red Porky | Basic Spell Essence
    (4230501, 4021032, 7500, 1, 1), # Red Porky | Mana Crystal
    (4230501, 2049000, 100, 1, 1), # Red Porky | Pure Clean Slate Scroll 1%
    (4230501, 2590004, 100, 1, 1), # Red Porky | Prototype Soul Enchanter
    (4230501, 2001527, 100, 1, 1), # Red Porky | Unagi
    (4230501, 2512056, 100, 1, 1), # Red Porky | Strength Boost Potion Recipe
    (4230502, 2512057, 100, 1, 1), # Black Porky | Dexterity Boost Potion Recipe
    (4230502, 4021032, 7500, 1, 1), # Black Porky | Mana Crystal
    (4230502, 2590004, 100, 1, 1), # Black Porky | Prototype Soul Enchanter
    (4230502, 2001527, 100, 1, 1), # Black Porky | Unagi
    (4230502, 2870162, 100, 1, 1), # Black Porky | Black Porky Familiar
    (4230502, 2001001, 100, 1, 1), # Black Porky | Ice Cream Pop
    (4230502, 4021034, 100, 1, 1), # Black Porky | Fine Spell Essence
    (4230502, 4021033, 100, 1, 1), # Black Porky | Basic Spell Essence
    (4230502, 2061000, 100, 1, 1), # Black Porky | Arrow for Crossbow
    (4230502, 4021035, 100, 1, 1), # Black Porky | Grand Spell Essence
    (4230502, 2431174, 100, 1, 1), # Black Porky | Medal of Honor
    (4230502, 2000002, 100, 1, 1), # Black Porky | White Potion
    (4230502, 2049401, 100, 1, 1), # Black Porky | Potential Scroll
    (4230502, 2048001, 100, 1, 1), # Black Porky | Scroll for Pet Equip. for Speed 60%
    (4230502, 4000278, 7500, 1, 1), # Black Porky | Needle Pouch
    (4230503, 4021033, 100, 1, 1), # Blue Flower Serpent | Basic Spell Essence
    (4230503, 2870163, 100, 1, 1), # Blue Flower Serpent | Blue Flower Serpent Familiar
    (4230503, 4000280, 7500, 1, 1), # Blue Flower Serpent | Necki Swimming Cap
    (4230503, 4021032, 7500, 1, 1), # Blue Flower Serpent | Mana Crystal
    (4230503, 2001527, 100, 1, 1), # Blue Flower Serpent | Unagi
    (4230503, 2000006, 100, 1, 1), # Blue Flower Serpent | Mana Elixir
    (4230503, 2590004, 100, 1, 1), # Blue Flower Serpent | Prototype Soul Enchanter
    (4230503, 2049401, 100, 1, 1), # Blue Flower Serpent | Potential Scroll
    (4230503, 2060000, 100, 1, 1), # Blue Flower Serpent | Arrow for Bow
    (4230503, 2431174, 100, 1, 1), # Blue Flower Serpent | Medal of Honor
    (4230503, 2001001, 100, 1, 1), # Blue Flower Serpent | Ice Cream Pop
    (4230503, 2001002, 100, 1, 1), # Blue Flower Serpent | Very Special Sundae
    (4230503, 4021034, 100, 1, 1), # Blue Flower Serpent | Fine Spell Essence
    (4230503, 2511106, 100, 1, 1), # Blue Flower Serpent | Angelic Blessing Recipe
    (4230503, 4021035, 100, 1, 1), # Blue Flower Serpent | Grand Spell Essence
    (4230503, 4000281, 7500, 1, 1), # Blue Flower Serpent | Snake Leather
    (4230504, 2512067, 100, 1, 1), # Red Flower Serpent | Basic Hero Potion Recipe
    (4230504, 4021032, 7500, 1, 1), # Red Flower Serpent | Mana Crystal
    (4230504, 2590004, 100, 1, 1), # Red Flower Serpent | Prototype Soul Enchanter
    (4230504, 4021033, 100, 1, 1), # Red Flower Serpent | Basic Spell Essence
    (4230504, 2061000, 100, 1, 1), # Red Flower Serpent | Arrow for Crossbow
    (4230504, 4000281, 7500, 1, 1), # Red Flower Serpent | Snake Leather
    (4230504, 2001526, 100, 1, 1), # Red Flower Serpent | Pure Water
    (4230504, 2060000, 100, 1, 1), # Red Flower Serpent | Arrow for Bow
    (4230504, 2870164, 100, 1, 1), # Red Flower Serpent | Red Flower Serpent Familiar
    (4230504, 2049401, 100, 1, 1), # Red Flower Serpent | Potential Scroll
    (4230504, 4000279, 7500, 1, 1), # Red Flower Serpent | Necki Flower
    (4230504, 4021035, 100, 1, 1), # Red Flower Serpent | Grand Spell Essence
    (4230504, 4021034, 100, 1, 1), # Red Flower Serpent | Fine Spell Essence
    (4230504, 2431174, 100, 1, 1), # Red Flower Serpent | Medal of Honor
    (4230505, 2512287, 100, 1, 1), # Jar | Superior Luck Boost Potion Recipe
    (4230505, 2431174, 100, 1, 1), # Jar | Medal of Honor
    (4230505, 2000037, 100, 1, 1), # Jar | Sunrise Dew
    (4230505, 2512273, 100, 1, 1), # Jar | Superior Concentration Potion Recipe
    (4230505, 4032908, 7500, 1, 1), # Jar | Ripped Scroll Piece
    (4230505, 4021035, 100, 1, 1), # Jar | Grand Spell Essence
    (4230505, 4021033, 100, 1, 1), # Jar | Basic Spell Essence
    (4230505, 2049401, 100, 1, 1), # Jar | Potential Scroll
    (4230505, 2001528, 100, 1, 1), # Jar | Melting Cheese
    (4230505, 4006001, 7500, 1, 1), # Jar | The Summoning Rock
    (4230505, 2048002, 100, 1, 1), # Jar | Scroll for Pet Equip. for Speed 10%
    (4230505, 4021032, 7500, 1, 1), # Jar | Mana Crystal
    (4230505, 4000291, 7500, 1, 1), # Jar | Broken Piece of Pot
    (4230505, 2590004, 100, 1, 1), # Jar | Prototype Soul Enchanter
    (4230505, 4021031, 50, 1, 1), # Jar | Twisted Time
    (4230505, 2870165, 100, 1, 1), # Jar | Jar Familiar
    (4230505, 4021034, 100, 1, 1), # Jar | Fine Spell Essence
    (2600215, 4000753, 7500, 1, 1), # Palm Tree Slime | Palm Leaf
    (2600214, 4000967, 7500, 1, 1), # Metal Golem | Golem Essence
    (2600213, 4000966, 7500, 1, 1), # Flaming Mixed Golem | Fiery Golem Fragment
    (2600212, 4000965, 7500, 1, 1), # Icy Mixed Golem | Icy Golem Fragment
    (2600211, 2040901, 100, 1, 1), # Mixed Golem | Scroll for Shield for DEF 60%
    (2600211, 4007002, 7500, 1, 1), # Mixed Golem | Magic Powder (Blue)
    (2600211, 4000177, 7500, 1, 1), # Mixed Golem | Mixed Block
    (2600211, 2000000, 100, 1, 1), # Mixed Golem | Red Potion
    (2600211, 4007003, 7500, 1, 1), # Mixed Golem | Magic Powder (Green)
    (2600211, 4010006, 7500, 1, 1), # Mixed Golem | Gold Ore
    (2600211, 2040923, 100, 1, 1), # Mixed Golem | Scroll for Shield for LUK 100%
    (2600211, 2000001, 100, 1, 1), # Mixed Golem | Orange Potion
    (2600211, 2043000, 100, 1, 1), # Mixed Golem | Scroll for One-Handed Sword for ATT 100%
    (2600211, 2870212, 100, 1, 1), # Mixed Golem | Mixed Golem Familiar
    (2600211, 2022057, 100, 1, 1), # Mixed Golem | Tofu
    (2600211, 2040926, 100, 1, 1), # Mixed Golem | Scroll for Shield for HP 100%
    (2600211, 2040319, 100, 1, 1), # Mixed Golem | Scroll for Earring for LUK 100%
    (2600210, 2040512, 100, 1, 1), # Dark Stone Golem | Scroll for Overall Armor for INT 100%
    (2600210, 4007002, 7500, 1, 1), # Dark Stone Golem | Magic Powder (Blue)
    (2600210, 2048000, 100, 1, 1), # Dark Stone Golem | Scroll for Pet Equip. for Speed 100%
    (2600210, 2070009, 100, 1, 1), # Dark Stone Golem | Wooden Top
    (2600210, 2870205, 100, 1, 1), # Dark Stone Golem | Dark Stone Golem Familiar
    (2600210, 2044310, 100, 1, 1), # Dark Stone Golem | Scroll for Spear for Accuracy 100%
    (2600210, 4020008, 7500, 1, 1), # Dark Stone Golem | Black Crystal Ore
    (2600210, 2044802, 100, 1, 1), # Dark Stone Golem | Scroll for Knuckle for ATT 10%
    (2600210, 4004004, 7500, 1, 1), # Dark Stone Golem | Dark Crystal Ore
    (2600210, 2044400, 100, 1, 1), # Dark Stone Golem | Scroll for Pole Arm for ATT 100%
    (2600210, 4000025, 7500, 1, 1), # Dark Stone Golem | Dark Stone Golem Rubble
    (2600210, 2060000, 100, 1, 1), # Dark Stone Golem | Arrow for Bow
    (2600210, 2043105, 100, 1, 1), # Dark Stone Golem | Dark scroll for One-Handed Axe for ATT 30%
    (2600210, 2000003, 100, 1, 1), # Dark Stone Golem | Blue Potion
    (2600210, 2044800, 100, 1, 1), # Dark Stone Golem | Scroll for Knuckle for Attack 100%
    (2600210, 4007001, 7500, 1, 1), # Dark Stone Golem | Magic Powder (White)
    (2600209, 2040900, 100, 1, 1), # Stone Golem | Scroll for Shield for DEF 100%
    (2600209, 4000022, 7500, 1, 1), # Stone Golem | Stone Golem Rubble
    (2600209, 4020008, 7500, 1, 1), # Stone Golem | Black Crystal Ore
    (2600209, 2044500, 100, 1, 1), # Stone Golem | Scroll for Bow for ATT 100%
    (2600209, 2061000, 100, 1, 1), # Stone Golem | Arrow for Crossbow
    (2600209, 4007001, 7500, 1, 1), # Stone Golem | Magic Powder (White)
    (2600209, 2022057, 100, 1, 1), # Stone Golem | Tofu
    (2600209, 2040403, 100, 1, 1), # Stone Golem | Scroll for Topwear for DEF 100%
    (2600209, 2000002, 100, 1, 1), # Stone Golem | White Potion
    (2600209, 4007006, 7500, 1, 1), # Stone Golem | Magic Powder (Red)
    (2600209, 2870204, 100, 1, 1), # Stone Golem | Stone Golem Familiar
    (2600209, 2000001, 100, 1, 1), # Stone Golem | Orange Potion
    (2600223, 2000001, 100, 1, 1), # Copper Drake | Orange Potion
    (2600223, 4000030, 7500, 1, 1), # Copper Drake | Dragon Skin
    (2600223, 2040002, 100, 1, 1), # Copper Drake | Scroll for Helmet for DEF 10%
    (2600223, 4010005, 7500, 1, 1), # Copper Drake | Orihalcon Ore
    (2600223, 4006001, 7500, 1, 1), # Copper Drake | The Summoning Rock
    (2600223, 2044014, 100, 1, 1), # Copper Drake | Scroll for Two-Handed Sword for Accuracy 10%
    (2600223, 4001077, 7500, 1, 1), # Copper Drake | Tough Dragon Skin
    (2600223, 4007004, 7500, 1, 1), # Copper Drake | Magic Powder (Yellow)
    (2600223, 2000002, 100, 1, 1), # Copper Drake | White Potion
    (2600223, 4006000, 7500, 1, 1), # Copper Drake | The Magic Rock
    (2600223, 4020008, 7500, 1, 1), # Copper Drake | Black Crystal Ore
    (2600223, 2001527, 100, 1, 1), # Copper Drake | Unagi
    (2600223, 4007001, 7500, 1, 1), # Copper Drake | Magic Powder (White)
    (2600223, 2040824, 100, 1, 1), # Copper Drake | Scroll for Gloves for HP 60%
    (2600223, 2044012, 100, 1, 1), # Copper Drake | Scroll for Two-Handed Sword for Accuracy 60%
    (2600223, 2041021, 100, 1, 1), # Copper Drake | Scroll for Cape for LUK 100%
    (2600223, 2022057, 100, 1, 1), # Copper Drake | Tofu
    (2600223, 2870127, 100, 1, 1), # Copper Drake | Copper Drake Familiar
    (2600223, 2040025, 100, 1, 1), # Copper Drake | Scroll for Helmet for INT 60%
    (2600223, 2070009, 100, 1, 1), # Copper Drake | Wooden Top
    (2600223, 4000014, 7500, 1, 1), # Copper Drake | Drake Skull
    (8643003, 4021034, 100, 1, 1), # Gallina | Fine Spell Essence
    (8643003, 4021035, 100, 1, 1), # Gallina | Grand Spell Essence
    (8643003, 2000036, 100, 1, 1), # Gallina | Reindeer Milk
    (8643003, 4034983, 7500, 1, 1), # Gallina | Dream Fragment
    (8643003, 4034984, 7500, 1, 1), # Gallina | Dream Fragment
    (8643003, 4034985, 7500, 1, 1), # Gallina | Dream Fragment
    (8643003, 4034986, 7500, 1, 1), # Gallina | Dream Fragment
    (8643003, 4034987, 7500, 1, 1), # Gallina | Dream Fragment
    (8643003, 4034988, 7500, 1, 1), # Gallina | Dream Fragment
    (8643003, 4021033, 100, 1, 1), # Gallina | Basic Spell Essence
    (8643003, 2000037, 100, 1, 1), # Gallina | Sunrise Dew
    (8643003, 4021032, 7500, 1, 1), # Gallina | Mana Crystal
    (8643003, 2435719, 100, 1, 1), # Gallina | Nodestone
    (8643003, 4021020, 10, 1, 1), # Gallina | Confusion Fragment
    (8643003, 4021031, 50, 1, 1), # Gallina | Twisted Time
    (2600222, 4000764, 7500, 1, 1), # Captain Darkgoo | Giant Octopus Tentacle
    (8643002, 4021034, 100, 1, 1), # Wood Board Alley Citizen | Fine Spell Essence
    (8643002, 4021035, 100, 1, 1), # Wood Board Alley Citizen | Grand Spell Essence
    (8643002, 4021020, 10, 1, 1), # Wood Board Alley Citizen | Confusion Fragment
    (8643002, 2435719, 100, 1, 1), # Wood Board Alley Citizen | Nodestone
    (8643002, 4021031, 50, 1, 1), # Wood Board Alley Citizen | Twisted Time
    (8643002, 2000036, 100, 1, 1), # Wood Board Alley Citizen | Reindeer Milk
    (8643002, 4034983, 7500, 1, 1), # Wood Board Alley Citizen | Dream Fragment
    (8643002, 4034984, 7500, 1, 1), # Wood Board Alley Citizen | Dream Fragment
    (8643002, 4034985, 7500, 1, 1), # Wood Board Alley Citizen | Dream Fragment
    (8643002, 4034986, 7500, 1, 1), # Wood Board Alley Citizen | Dream Fragment
    (8643002, 4034987, 7500, 1, 1), # Wood Board Alley Citizen | Dream Fragment
    (8643002, 4034988, 7500, 1, 1), # Wood Board Alley Citizen | Dream Fragment
    (8643002, 2000037, 100, 1, 1), # Wood Board Alley Citizen | Sunrise Dew
    (8643002, 4021032, 7500, 1, 1), # Wood Board Alley Citizen | Mana Crystal
    (8643002, 4021033, 100, 1, 1), # Wood Board Alley Citizen | Basic Spell Essence
    (2600221, 4000763, 7500, 1, 1), # Seashell Octopus Slime | Orange Ink Sack
    (8643001, 4021034, 100, 1, 1), # Paper Bag Alley Citizen | Fine Spell Essence
    (8643001, 4034983, 7500, 1, 1), # Paper Bag Alley Citizen | Dream Fragment
    (8643001, 4034984, 7500, 1, 1), # Paper Bag Alley Citizen | Dream Fragment
    (8643001, 4034985, 7500, 1, 1), # Paper Bag Alley Citizen | Dream Fragment
    (8643001, 4034986, 7500, 1, 1), # Paper Bag Alley Citizen | Dream Fragment
    (8643001, 4034987, 7500, 1, 1), # Paper Bag Alley Citizen | Dream Fragment
    (8643001, 4034988, 7500, 1, 1), # Paper Bag Alley Citizen | Dream Fragment
    (8643001, 4021031, 50, 1, 1), # Paper Bag Alley Citizen | Twisted Time
    (8643001, 4021032, 7500, 1, 1), # Paper Bag Alley Citizen | Mana Crystal
    (8643001, 4034979, 7500, 1, 1), # Paper Bag Alley Citizen | Cheap Mask Material
    (8643001, 4021020, 10, 1, 1), # Paper Bag Alley Citizen | Confusion Fragment
    (8643001, 4021033, 100, 1, 1), # Paper Bag Alley Citizen | Basic Spell Essence
    (8643001, 2435719, 100, 1, 1), # Paper Bag Alley Citizen | Nodestone
    (8643001, 2000037, 100, 1, 1), # Paper Bag Alley Citizen | Sunrise Dew
    (8643001, 2000036, 100, 1, 1), # Paper Bag Alley Citizen | Reindeer Milk
    (8643001, 4021035, 100, 1, 1), # Paper Bag Alley Citizen | Grand Spell Essence
    (2600220, 4000762, 7500, 1, 1), # Starfish Octopus Slime | Blue Ink Sack
    (8643000, 4021031, 50, 1, 1), # Dreamkeeper | Twisted Time
    (8643000, 2435719, 100, 1, 1), # Dreamkeeper | Nodestone
    (8643000, 2000036, 100, 1, 1), # Dreamkeeper | Reindeer Milk
    (8643000, 2000037, 100, 1, 1), # Dreamkeeper | Sunrise Dew
    (8643000, 4021035, 100, 1, 1), # Dreamkeeper | Grand Spell Essence
    (8643000, 4021034, 100, 1, 1), # Dreamkeeper | Fine Spell Essence
    (8643000, 4034983, 7500, 1, 1), # Dreamkeeper | Dream Fragment
    (8643000, 4034984, 7500, 1, 1), # Dreamkeeper | Dream Fragment
    (8643000, 4034985, 7500, 1, 1), # Dreamkeeper | Dream Fragment
    (8643000, 4034986, 7500, 1, 1), # Dreamkeeper | Dream Fragment
    (8643000, 4034987, 7500, 1, 1), # Dreamkeeper | Dream Fragment
    (8643000, 4034988, 7500, 1, 1), # Dreamkeeper | Dream Fragment
    (8643000, 4021020, 10, 1, 1), # Dreamkeeper | Confusion Fragment
    (8643000, 4021033, 100, 1, 1), # Dreamkeeper | Basic Spell Essence
    (8643000, 4021032, 7500, 1, 1), # Dreamkeeper | Mana Crystal
    (2600219, 4000761, 7500, 1, 1), # Flying Fish Slime | Flying Fish
    (8643007, 4021035, 100, 1, 1), # Red Dancing Shoes | Grand Spell Essence
    (8643007, 4021020, 10, 1, 1), # Red Dancing Shoes | Confusion Fragment
    (8643007, 4021034, 100, 1, 1), # Red Dancing Shoes | Fine Spell Essence
    (8643007, 4034983, 7500, 1, 1), # Red Dancing Shoes | Dream Fragment
    (8643007, 4034984, 7500, 1, 1), # Red Dancing Shoes | Dream Fragment
    (8643007, 4034985, 7500, 1, 1), # Red Dancing Shoes | Dream Fragment
    (8643007, 4034986, 7500, 1, 1), # Red Dancing Shoes | Dream Fragment
    (8643007, 4034987, 7500, 1, 1), # Red Dancing Shoes | Dream Fragment
    (8643007, 4034988, 7500, 1, 1), # Red Dancing Shoes | Dream Fragment
    (8643007, 4021032, 7500, 1, 1), # Red Dancing Shoes | Mana Crystal
    (8643007, 2000036, 100, 1, 1), # Red Dancing Shoes | Reindeer Milk
    (8643007, 2000037, 100, 1, 1), # Red Dancing Shoes | Sunrise Dew
    (8643007, 2435719, 100, 1, 1), # Red Dancing Shoes | Nodestone
    (8643007, 4021031, 50, 1, 1), # Red Dancing Shoes | Twisted Time
    (8643007, 4021033, 100, 1, 1), # Red Dancing Shoes | Basic Spell Essence
    (2600218, 4000760, 7500, 1, 1), # Shrimp Slime | Fried Shrimp Bite
    (8643006, 2000037, 100, 1, 1), # Crooked Victory Plate | Sunrise Dew
    (8643006, 4021034, 100, 1, 1), # Crooked Victory Plate | Fine Spell Essence
    (8643006, 4021033, 100, 1, 1), # Crooked Victory Plate | Basic Spell Essence
    (8643006, 4034983, 7500, 1, 1), # Crooked Victory Plate | Dream Fragment
    (8643006, 4034984, 7500, 1, 1), # Crooked Victory Plate | Dream Fragment
    (8643006, 4034985, 7500, 1, 1), # Crooked Victory Plate | Dream Fragment
    (8643006, 4034986, 7500, 1, 1), # Crooked Victory Plate | Dream Fragment
    (8643006, 4034987, 7500, 1, 1), # Crooked Victory Plate | Dream Fragment
    (8643006, 4034988, 7500, 1, 1), # Crooked Victory Plate | Dream Fragment
    (8643006, 4021035, 100, 1, 1), # Crooked Victory Plate | Grand Spell Essence
    (8643006, 4021032, 7500, 1, 1), # Crooked Victory Plate | Mana Crystal
    (8643006, 4034982, 7500, 1, 1), # Crooked Victory Plate | Green Plate Piece
    (8643006, 2000036, 100, 1, 1), # Crooked Victory Plate | Reindeer Milk
    (8643006, 4021031, 50, 1, 1), # Crooked Victory Plate | Twisted Time
    (8643006, 2435719, 100, 1, 1), # Crooked Victory Plate | Nodestone
    (8643006, 4021020, 10, 1, 1), # Crooked Victory Plate | Confusion Fragment
    (2600217, 4000759, 7500, 1, 1), # Blue Tube Slime | Blue Inner Tube
    (8643005, 2000036, 100, 1, 1), # Angry Victory Plate | Reindeer Milk
    (8643005, 4034983, 7500, 1, 1), # Angry Victory Plate | Dream Fragment
    (8643005, 4034984, 7500, 1, 1), # Angry Victory Plate | Dream Fragment
    (8643005, 4034985, 7500, 1, 1), # Angry Victory Plate | Dream Fragment
    (8643005, 4034986, 7500, 1, 1), # Angry Victory Plate | Dream Fragment
    (8643005, 4034987, 7500, 1, 1), # Angry Victory Plate | Dream Fragment
    (8643005, 4034988, 7500, 1, 1), # Angry Victory Plate | Dream Fragment
    (8643005, 4021032, 7500, 1, 1), # Angry Victory Plate | Mana Crystal
    (8643005, 4034981, 7500, 1, 1), # Angry Victory Plate | Blue Plate Piece
    (8643005, 4021035, 100, 1, 1), # Angry Victory Plate | Grand Spell Essence
    (8643005, 4021031, 50, 1, 1), # Angry Victory Plate | Twisted Time
    (8643005, 2435719, 100, 1, 1), # Angry Victory Plate | Nodestone
    (8643005, 4021034, 100, 1, 1), # Angry Victory Plate | Fine Spell Essence
    (8643005, 2000037, 100, 1, 1), # Angry Victory Plate | Sunrise Dew
    (8643005, 4021033, 100, 1, 1), # Angry Victory Plate | Basic Spell Essence
    (8643005, 4021020, 10, 1, 1), # Angry Victory Plate | Confusion Fragment
    (2600216, 4000756, 7500, 1, 1), # Violet Clam Slime | Violet Clam
    (8643004, 4021035, 100, 1, 1), # Gallus | Grand Spell Essence
    (8643004, 4021031, 50, 1, 1), # Gallus | Twisted Time
    (8643004, 4021033, 100, 1, 1), # Gallus | Basic Spell Essence
    (8643004, 2000036, 100, 1, 1), # Gallus | Reindeer Milk
    (8643004, 4021020, 10, 1, 1), # Gallus | Confusion Fragment
    (8643004, 4021032, 7500, 1, 1), # Gallus | Mana Crystal
    (8643004, 2000037, 100, 1, 1), # Gallus | Sunrise Dew
    (8643004, 4021034, 100, 1, 1), # Gallus | Fine Spell Essence
    (8643004, 4034983, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 4034984, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 4034985, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 4034986, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 4034987, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 4034988, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 2435719, 100, 1, 1), # Gallus | Nodestone
    (4230400, 2870159, 100, 1, 1), # Iron Boar | Iron Boar Familiar
    (4230400, 2431174, 100, 1, 1), # Iron Boar | Medal of Honor
    (4230400, 4000178, 7500, 1, 1), # Iron Boar | Iron Boar Armor
    (8600003, 2512280, 100, 1, 1), # Mutant Ribbon Pig | Legendary Insight Potion Recipe
    (8600003, 2434021, 100, 1, 1), # Mutant Ribbon Pig | Medal of Honor
    (8600003, 4000645, 7500, 1, 1), # Mutant Ribbon Pig | Mutant Pork
    (8600003, 2512012, 100, 1, 1), # Mutant Ribbon Pig | Dexterity Potion VII Recipe
    (8600003, 4021020, 10, 1, 1), # Mutant Ribbon Pig | Confusion Fragment
    (8600003, 4021032, 7500, 1, 1), # Mutant Ribbon Pig | Mana Crystal
    (8600003, 4021035, 100, 1, 1), # Mutant Ribbon Pig | Grand Spell Essence
    (8600003, 2512284, 100, 1, 1), # Mutant Ribbon Pig | Exceptional Intelligence Boost Potion Recipe
    (8600003, 2590004, 100, 1, 1), # Mutant Ribbon Pig | Prototype Soul Enchanter
    (8600003, 2512278, 100, 1, 1), # Mutant Ribbon Pig | Superior Insight Potion Recipe
    (8600003, 2512286, 100, 1, 1), # Mutant Ribbon Pig | Exceptional Dexterity Boost Potion Recipe
    (8600003, 2512272, 100, 1, 1), # Mutant Ribbon Pig | Intermediate Concentration Potion Recipe
    (8600003, 2512282, 100, 1, 1), # Mutant Ribbon Pig | Exceptional Strength Boost Potion Recipe
    (8600003, 2070010, 100, 1, 1), # Mutant Ribbon Pig | Icicle
    (8600003, 2330005, 100, 1, 1), # Mutant Ribbon Pig | Eternal Bullet
    (8600003, 2512247, 100, 1, 1), # Mutant Ribbon Pig | Advanced Attack Pill IV Recipe
    (8600003, 2000036, 100, 1, 1), # Mutant Ribbon Pig | Reindeer Milk
    (8600003, 2049401, 100, 1, 1), # Mutant Ribbon Pig | Potential Scroll
    (8600003, 2511098, 100, 1, 1), # Mutant Ribbon Pig | HP Hound Earrings Recipe
    (8600003, 4021034, 100, 1, 1), # Mutant Ribbon Pig | Fine Spell Essence
    (8600003, 2001554, 100, 1, 1), # Mutant Ribbon Pig | Sunset Dew
    (8600003, 2870455, 100, 1, 1), # Mutant Ribbon Pig | Mutant Ribbon Pig Familiar
    (8600003, 2512279, 100, 1, 1), # Mutant Ribbon Pig | Exceptional Insight Potion Recipe
    (8600003, 2512270, 100, 1, 1), # Mutant Ribbon Pig | Exceptional Blessing Potion Recipe
    (8600003, 2511099, 100, 1, 1), # Mutant Ribbon Pig | MP Hound Earrings Recipe
    (8600003, 4032940, 7500, 1, 1), # Mutant Ribbon Pig | Pig Oil
    (8600003, 2510261, 100, 1, 1), # Mutant Ribbon Pig | Deimos Darkness Shield Recipe
    (8600003, 4034983, 7500, 1, 1), # Mutant Ribbon Pig | Dream Fragment
    (8600003, 4034984, 7500, 1, 1), # Mutant Ribbon Pig | Dream Fragment
    (8600003, 4034985, 7500, 1, 1), # Mutant Ribbon Pig | Dream Fragment
    (8643004, 4034986, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 4034987, 7500, 1, 1), # Gallus | Dream Fragment
    (8643004, 4034988, 7500, 1, 1), # Gallus | Dream Fragment
    (8600003, 2512107, 100, 1, 1), # Mutant Ribbon Pig | Advanced Dexterity Potion IX Recipe
    (8600003, 4021033, 100, 1, 1), # Mutant Ribbon Pig | Basic Spell Essence
    (8600003, 4021031, 50, 1, 1), # Mutant Ribbon Pig | Twisted Time
    (8600003, 2048002, 100, 1, 1), # Mutant Ribbon Pig | Scroll for Pet Equip. for Speed 10%
    (8643011, 2000036, 100, 1, 1), # Red-eyed Gargoyle | Reindeer Milk
    (8643011, 4034983, 7500, 1, 1), # Red-eyed Gargoyle | Dream Fragment
    (8643011, 4034984, 7500, 1, 1), # Red-eyed Gargoyle | Dream Fragment
    (8643011, 4034985, 7500, 1, 1), # Red-eyed Gargoyle | Dream Fragment
    (8643011, 4034986, 7500, 1, 1), # Red-eyed Gargoyle | Dream Fragment
    (8643011, 4034987, 7500, 1, 1), # Red-eyed Gargoyle | Dream Fragment
    (8643011, 4034988, 7500, 1, 1), # Red-eyed Gargoyle | Dream Fragment
    (8643011, 2000037, 100, 1, 1), # Red-eyed Gargoyle | Sunrise Dew
    (8643011, 2435719, 100, 1, 1), # Red-eyed Gargoyle | Nodestone
    (8643011, 4021033, 100, 1, 1), # Red-eyed Gargoyle | Basic Spell Essence
    (8643011, 4021035, 100, 1, 1), # Red-eyed Gargoyle | Grand Spell Essence
    (8643011, 4021032, 7500, 1, 1), # Red-eyed Gargoyle | Mana Crystal
    (8643011, 4021031, 50, 1, 1), # Red-eyed Gargoyle | Twisted Time
    (8643011, 4021020, 10, 1, 1), # Red-eyed Gargoyle | Confusion Fragment
    (8643011, 4021034, 100, 1, 1), # Red-eyed Gargoyle | Fine Spell Essence
    (8600002, 2512276, 100, 1, 1), # Mutant Slime | Exceptional Fortitude Potion Recipe
    (8600002, 2511035, 100, 1, 1), # Mutant Slime | Sharp Exquisite Belt Recipe
    (8600002, 4021020, 10, 1, 1), # Mutant Slime | Confusion Fragment
    (8600002, 2049401, 100, 1, 1), # Mutant Slime | Potential Scroll
    (8600002, 2870454, 100, 1, 1), # Mutant Slime | Mutant Slime Familiar
    (8600002, 2434021, 100, 1, 1), # Mutant Slime | Medal of Honor
    (8600002, 2512269, 100, 1, 1), # Mutant Slime | Superior Blessing Potion Recipe
    (8600002, 2000036, 100, 1, 1), # Mutant Slime | Reindeer Milk
    (8600002, 2510243, 100, 1, 1), # Mutant Slime | Pure Gold Engine Recipe
    (8600002, 2512286, 100, 1, 1), # Mutant Slime | Exceptional Dexterity Boost Potion Recipe
    (8600002, 4034983, 7500, 1, 1), # Mutant Slime | Dream Fragment
    (8600002, 4034984, 7500, 1, 1), # Mutant Slime | Dream Fragment
    (8600002, 4034985, 7500, 1, 1), # Mutant Slime | Dream Fragment
    (8600002, 4034986, 7500, 1, 1), # Mutant Slime | Dream Fragment
    (8600002, 4034987, 7500, 1, 1), # Mutant Slime | Dream Fragment
    (8600002, 4034988, 7500, 1, 1), # Mutant Slime | Dream Fragment
    (8600002, 4021034, 100, 1, 1), # Mutant Slime | Fine Spell Essence
    (8600002, 2511075, 100, 1, 1), # Mutant Slime | Strength Ring VII Recipe
    (8600002, 2512279, 100, 1, 1), # Mutant Slime | Exceptional Insight Potion Recipe
    (8600002, 4021031, 50, 1, 1), # Mutant Slime | Twisted Time
    (8600002, 2512267, 100, 1, 1), # Mutant Slime | Exceptional Hero Potion Recipe
    (8600002, 2048001, 100, 1, 1), # Mutant Slime | Scroll for Pet Equip. for Speed 60%
    (8600002, 2512271, 100, 1, 1), # Mutant Slime | Legendary Blessing Potion Recipe
    (8600002, 4021035, 100, 1, 1), # Mutant Slime | Grand Spell Essence
    (8600002, 2512195, 100, 1, 1), # Mutant Slime | Advanced Intelligence Pill VII Recipe
    (8600002, 2512023, 100, 1, 1), # Mutant Slime | Intelligence Potion X Recipe
    (8600002, 4000644, 7500, 1, 1), # Mutant Slime | Mutant Slime Bubble
    (8600002, 2590004, 100, 1, 1), # Mutant Slime | Prototype Soul Enchanter
    (8600002, 4021033, 100, 1, 1), # Mutant Slime | Basic Spell Essence
    (8600002, 2512274, 100, 1, 1), # Mutant Slime | Exceptional Concentration Potion Recipe
    (8600002, 2048005, 100, 1, 1), # Mutant Slime | Scroll for Pet Equip. for Jump 10%
    (8600002, 4021032, 7500, 1, 1), # Mutant Slime | Mana Crystal
    (8600002, 2001554, 100, 1, 1), # Mutant Slime | Sunset Dew
    (8600002, 2512126, 100, 1, 1), # Mutant Slime | Advanced Luck Potion VIII Recipe
    (8643010, 2000037, 100, 1, 1), # Blue-eyed Gargoyle | Sunrise Dew
    (8643010, 4021031, 50, 1, 1), # Blue-eyed Gargoyle | Twisted Time
    (8643010, 4021032, 7500, 1, 1), # Blue-eyed Gargoyle | Mana Crystal
    (8643010, 2000036, 100, 1, 1), # Blue-eyed Gargoyle | Reindeer Milk
    (8643010, 4021034, 100, 1, 1), # Blue-eyed Gargoyle | Fine Spell Essence
    (8643010, 4021035, 100, 1, 1), # Blue-eyed Gargoyle | Grand Spell Essence
    (8643010, 4034983, 7500, 1, 1), # Blue-eyed Gargoyle | Dream Fragment
    (8643010, 4034984, 7500, 1, 1), # Blue-eyed Gargoyle | Dream Fragment
    (8643010, 4034985, 7500, 1, 1), # Blue-eyed Gargoyle | Dream Fragment
    (8643010, 4034986, 7500, 1, 1), # Blue-eyed Gargoyle | Dream Fragment
    (8643010, 4034987, 7500, 1, 1), # Blue-eyed Gargoyle | Dream Fragment
    (8643010, 4034988, 7500, 1, 1), # Blue-eyed Gargoyle | Dream Fragment
    (8643010, 4021020, 10, 1, 1), # Blue-eyed Gargoyle | Confusion Fragment
    (8643010, 2435719, 100, 1, 1), # Blue-eyed Gargoyle | Nodestone
    (8643010, 4021033, 100, 1, 1), # Blue-eyed Gargoyle | Basic Spell Essence
    (8600001, 4021034, 100, 1, 1), # Mutant Orange Mushroom | Fine Spell Essence
    (8600001, 2512212, 100, 1, 1), # Mutant Orange Mushroom | Advanced Luck Pill IV Recipe
    (8600001, 2870453, 100, 1, 1), # Mutant Orange Mushroom | Mutant Orange Mushroom Familiar
    (8600001, 2510267, 100, 1, 1), # Mutant Orange Mushroom | Abyss Fennel Recipe
    (8600001, 4021020, 10, 1, 1), # Mutant Orange Mushroom | Confusion Fragment
    (8600001, 2590004, 100, 1, 1), # Mutant Orange Mushroom | Prototype Soul Enchanter
    (8600001, 2512287, 100, 1, 1), # Mutant Orange Mushroom | Superior Luck Boost Potion Recipe
    (8600001, 4000643, 7500, 1, 1), # Mutant Orange Mushroom | Mutant Mushroom Cap
    (8600001, 2512272, 100, 1, 1), # Mutant Orange Mushroom | Intermediate Concentration Potion Recipe
    (8600001, 4032930, 7500, 1, 1), # Mutant Orange Mushroom | Glittering Crystal Marble
    (8600001, 2510430, 100, 1, 1), # Mutant Orange Mushroom | Abyss Peony Recipe
    (8600001, 2000036, 100, 1, 1), # Mutant Orange Mushroom | Reindeer Milk
    (8600001, 4021035, 100, 1, 1), # Mutant Orange Mushroom | Grand Spell Essence
    (8600001, 2049401, 100, 1, 1), # Mutant Orange Mushroom | Potential Scroll
    (8600001, 2512282, 100, 1, 1), # Mutant Orange Mushroom | Exceptional Strength Boost Potion Recipe
    (8600001, 2510271, 100, 1, 1), # Mutant Orange Mushroom | Abyss Conrad Henkel Recipe
    (8600001, 2434021, 100, 1, 1), # Mutant Orange Mushroom | Medal of Honor
    (8600001, 4034983, 7500, 1, 1), # Mutant Orange Mushroom | Dream Fragment
    (8600001, 4034984, 7500, 1, 1), # Mutant Orange Mushroom | Dream Fragment
    (8600001, 4034985, 7500, 1, 1), # Mutant Orange Mushroom | Dream Fragment
    (8600001, 4034986, 7500, 1, 1), # Mutant Orange Mushroom | Dream Fragment
    (8600001, 4034987, 7500, 1, 1), # Mutant Orange Mushroom | Dream Fragment
    (8600001, 4034988, 7500, 1, 1), # Mutant Orange Mushroom | Dream Fragment
    (8600001, 2510261, 100, 1, 1), # Mutant Orange Mushroom | Deimos Darkness Shield Recipe
    (8600001, 4021032, 7500, 1, 1), # Mutant Orange Mushroom | Mana Crystal
    (8600001, 2512279, 100, 1, 1), # Mutant Orange Mushroom | Exceptional Insight Potion Recipe
    (8600001, 2001554, 100, 1, 1), # Mutant Orange Mushroom | Sunset Dew
    (8600001, 2070005, 100, 1, 1), # Mutant Orange Mushroom | Steely Throwing-Knives
    (8600001, 2512156, 100, 1, 1), # Mutant Orange Mushroom | Advanced Strength Pill VIII Recipe
    (8600001, 2512179, 100, 1, 1), # Mutant Orange Mushroom | Intelligence Pill I Recipe
    (8600001, 2070004, 100, 1, 1), # Mutant Orange Mushroom | Tobi Throwing-Stars
    (8600001, 4021031, 50, 1, 1), # Mutant Orange Mushroom | Twisted Time
    (8600001, 2512092, 100, 1, 1), # Mutant Orange Mushroom | Advanced Strength Potion IV Recipe
    (8600001, 4021033, 100, 1, 1), # Mutant Orange Mushroom | Basic Spell Essence
    (8643009, 4021034, 100, 1, 1), # Insane Masquerade Citizen | Fine Spell Essence
    (8643009, 4021033, 100, 1, 1), # Insane Masquerade Citizen | Basic Spell Essence
    (8643009, 4021035, 100, 1, 1), # Insane Masquerade Citizen | Grand Spell Essence
    (8643009, 2435719, 100, 1, 1), # Insane Masquerade Citizen | Nodestone
    (8643009, 2000037, 100, 1, 1), # Insane Masquerade Citizen | Sunrise Dew
    (8643009, 4021032, 7500, 1, 1), # Insane Masquerade Citizen | Mana Crystal
    (8643009, 4034983, 7500, 1, 1), # Insane Masquerade Citizen | Dream Fragment
    (8643009, 4034984, 7500, 1, 1), # Insane Masquerade Citizen | Dream Fragment
    (8643009, 4034985, 7500, 1, 1), # Insane Masquerade Citizen | Dream Fragment
    (8643009, 4034986, 7500, 1, 1), # Insane Masquerade Citizen | Dream Fragment
    (8643009, 4034987, 7500, 1, 1), # Insane Masquerade Citizen | Dream Fragment
    (8643009, 4034988, 7500, 1, 1), # Insane Masquerade Citizen | Dream Fragment
    (8643009, 2000036, 100, 1, 1), # Insane Masquerade Citizen | Reindeer Milk
    (8643009, 4021031, 50, 1, 1), # Insane Masquerade Citizen | Twisted Time
    (8643009, 4021020, 10, 1, 1), # Insane Masquerade Citizen | Confusion Fragment
    (8600000, 2512269, 100, 1, 1), # Mutant Snail | Superior Blessing Potion Recipe
    (8600000, 2512096, 100, 1, 1), # Mutant Snail | Advanced Strength Potion VIII Recipe
    (8600000, 2512216, 100, 1, 1), # Mutant Snail | Advanced Luck Pill VIII Recipe
    (8600000, 4021035, 100, 1, 1), # Mutant Snail | Grand Spell Essence
    (8600000, 2001554, 100, 1, 1), # Mutant Snail | Sunset Dew
    (8600000, 2434021, 100, 1, 1), # Mutant Snail | Medal of Honor
    (8600000, 2000036, 100, 1, 1), # Mutant Snail | Reindeer Milk
    (8600000, 4034983, 7500, 1, 1), # Mutant Snail | Dream Fragment
    (8600000, 4034984, 7500, 1, 1), # Mutant Snail | Dream Fragment
    (8600000, 4034985, 7500, 1, 1), # Mutant Snail | Dream Fragment
    (8600000, 4034986, 7500, 1, 1), # Mutant Snail | Dream Fragment
    (8600000, 4034987, 7500, 1, 1), # Mutant Snail | Dream Fragment
    (8600000, 4034988, 7500, 1, 1), # Mutant Snail | Dream Fragment
    (8600000, 2870452, 100, 1, 1), # Mutant Snail | Mutant Snail Familiar
    (8600000, 4021032, 7500, 1, 1), # Mutant Snail | Mana Crystal
    (8600000, 2512283, 100, 1, 1), # Mutant Snail | Superior Intelligence Boost Potion Recipe
    (8600000, 2512271, 100, 1, 1), # Mutant Snail | Legendary Blessing Potion Recipe
    (8600000, 2512284, 100, 1, 1), # Mutant Snail | Exceptional Intelligence Boost Potion Recipe
    (8600000, 2512270, 100, 1, 1), # Mutant Snail | Exceptional Blessing Potion Recipe
    (8600000, 2512090, 100, 1, 1), # Mutant Snail | Advanced Strength Potion II Recipe
    (8600000, 2512102, 100, 1, 1), # Mutant Snail | Advanced Dexterity Potion IV Recipe
    (8600000, 2512275, 100, 1, 1), # Mutant Snail | Superior Fortitude Potion Recipe
    (8600000, 4000642, 7500, 1, 1), # Mutant Snail | Mutant Snail Shell
    (8600000, 4021033, 100, 1, 1), # Mutant Snail | Basic Spell Essence
    (8600000, 2048003, 100, 1, 1), # Mutant Snail | Scroll for Pet Equip. for Jump 100%
    (8600000, 2590004, 100, 1, 1), # Mutant Snail | Prototype Soul Enchanter
    (8600000, 2510249, 100, 1, 1), # Mutant Snail | Blood Blossom Katara Recipe
    (8600000, 4021020, 10, 1, 1), # Mutant Snail | Confusion Fragment
    (8600000, 2512112, 100, 1, 1), # Mutant Snail | Advanced Intelligence Potion IV Recipe
    (8600000, 4021031, 50, 1, 1), # Mutant Snail | Twisted Time
    (8600000, 4032921, 7500, 1, 1), # Mutant Snail | Snail Meat
    (8600000, 2049401, 100, 1, 1), # Mutant Snail | Potential Scroll
    (8600000, 4021034, 100, 1, 1), # Mutant Snail | Fine Spell Essence
    (8643008, 2000037, 100, 1, 1), # Angry Masquerade Citizen | Sunrise Dew
    (8643008, 4034983, 7500, 1, 1), # Angry Masquerade Citizen | Dream Fragment
    (8643008, 4034984, 7500, 1, 1), # Angry Masquerade Citizen | Dream Fragment
    (8643008, 4034985, 7500, 1, 1), # Angry Masquerade Citizen | Dream Fragment
    (8643008, 4034986, 7500, 1, 1), # Angry Masquerade Citizen | Dream Fragment
    (8643008, 4034987, 7500, 1, 1), # Angry Masquerade Citizen | Dream Fragment
    (8643008, 4034988, 7500, 1, 1), # Angry Masquerade Citizen | Dream Fragment
    (8643008, 4021035, 100, 1, 1), # Angry Masquerade Citizen | Grand Spell Essence
    (8643008, 2000036, 100, 1, 1), # Angry Masquerade Citizen | Reindeer Milk
    (8643008, 4021031, 50, 1, 1), # Angry Masquerade Citizen | Twisted Time
    (8643008, 4021020, 10, 1, 1), # Angry Masquerade Citizen | Confusion Fragment
    (8643008, 4021033, 100, 1, 1), # Angry Masquerade Citizen | Basic Spell Essence
    (8643008, 4034980, 7500, 1, 1), # Angry Masquerade Citizen | Flashy Mask Material
    (8643008, 2435719, 100, 1, 1), # Angry Masquerade Citizen | Nodestone
    (8643008, 4021034, 100, 1, 1), # Angry Masquerade Citizen | Fine Spell Essence
    (8643008, 4021032, 7500, 1, 1), # Angry Masquerade Citizen | Mana Crystal
    (8600006, 2048003, 100, 1, 1), # Mutant Tiguru | Scroll for Pet Equip. for Jump 100%
    (8600006, 2512285, 100, 1, 1), # Mutant Tiguru | Superior Dexterity Boost Potion Recipe
    (8600006, 2512276, 100, 1, 1), # Mutant Tiguru | Exceptional Fortitude Potion Recipe
    (8600006, 2512284, 100, 1, 1), # Mutant Tiguru | Exceptional Intelligence Boost Potion Recipe
    (8600006, 4021034, 100, 1, 1), # Mutant Tiguru | Fine Spell Essence
    (8600006, 2511049, 100, 1, 1), # Mutant Tiguru | Purple Owl Pendant Recipe
    (8600006, 2512109, 100, 1, 1), # Mutant Tiguru | Advanced Intelligence Potion I Recipe
    (8600006, 4021035, 100, 1, 1), # Mutant Tiguru | Grand Spell Essence
    (8600006, 2590004, 100, 1, 1), # Mutant Tiguru | Prototype Soul Enchanter
    (8600006, 2512267, 100, 1, 1), # Mutant Tiguru | Exceptional Hero Potion Recipe
    (8600006, 4021033, 100, 1, 1), # Mutant Tiguru | Basic Spell Essence
    (8600006, 4034983, 7500, 1, 1), # Mutant Tiguru | Dream Fragment
    (8600006, 4034984, 7500, 1, 1), # Mutant Tiguru | Dream Fragment
    (8600006, 4034985, 7500, 1, 1), # Mutant Tiguru | Dream Fragment
    (8600006, 4034986, 7500, 1, 1), # Mutant Tiguru | Dream Fragment
    (8600006, 4034987, 7500, 1, 1), # Mutant Tiguru | Dream Fragment
    (8600006, 4034988, 7500, 1, 1), # Mutant Tiguru | Dream Fragment
    (8600006, 4021020, 10, 1, 1), # Mutant Tiguru | Confusion Fragment
    (8600006, 2512130, 100, 1, 1), # Mutant Tiguru | Advanced Attack Potion II Recipe
    (8600006, 2512029, 100, 1, 1), # Mutant Tiguru | Luck Potion VIII Recipe
    (8600006, 2004095, 100, 1, 1), # Mutant Tiguru | Advanced Defense Potion VI
    (8600006, 2512283, 100, 1, 1), # Mutant Tiguru | Superior Intelligence Boost Potion Recipe
    (8600006, 2000036, 100, 1, 1), # Mutant Tiguru | Reindeer Milk
    (8600006, 2512286, 100, 1, 1), # Mutant Tiguru | Exceptional Dexterity Boost Potion Recipe
    (8600006, 4021032, 7500, 1, 1), # Mutant Tiguru | Mana Crystal
    (8600006, 2434021, 100, 1, 1), # Mutant Tiguru | Medal of Honor
    (8600006, 2870458, 100, 1, 1), # Mutant Tiguru | Mutant Tiguru Familiar
    (8600006, 4021031, 50, 1, 1), # Mutant Tiguru | Twisted Time
    (8600006, 2512281, 100, 1, 1), # Mutant Tiguru | Superior Strength Boost Potion Recipe
    (8600006, 2001554, 100, 1, 1), # Mutant Tiguru | Sunset Dew
    (8600006, 4000648, 7500, 1, 1), # Mutant Tiguru | Mutant Tiguru Feather
    (8600006, 2512272, 100, 1, 1), # Mutant Tiguru | Intermediate Concentration Potion Recipe
    (8600006, 2048000, 100, 1, 1), # Mutant Tiguru | Scroll for Pet Equip. for Speed 100%
    (8600006, 2049401, 100, 1, 1), # Mutant Tiguru | Potential Scroll
    (8600005, 2003544, 100, 1, 1), # Mutant Tiru | Exceptional Dexterity Boost Potion
    (8600005, 2590004, 100, 1, 1), # Mutant Tiru | Prototype Soul Enchanter
    (8600005, 2003541, 100, 1, 1), # Mutant Tiru | Superior Intelligence Boost Potion
    (8600005, 2330004, 100, 1, 1), # Mutant Tiru | Shiny Bullet
    (8600005, 4021031, 50, 1, 1), # Mutant Tiru | Twisted Time
    (8600005, 4021020, 10, 1, 1), # Mutant Tiru | Confusion Fragment
    (8600005, 2070010, 100, 1, 1), # Mutant Tiru | Icicle
    (8600005, 4021034, 100, 1, 1), # Mutant Tiru | Fine Spell Essence
    (8600005, 2387093, 100, 1, 1), # Mutant Tiru | Mutant Tiru Card
    (8600005, 4021032, 7500, 1, 1), # Mutant Tiru | Mana Crystal
    (8600005, 2003543, 100, 1, 1), # Mutant Tiru | Superior Dexterity Boost Potion
    (8600005, 2049401, 100, 1, 1), # Mutant Tiru | Potential Scroll
    (8600005, 4021035, 100, 1, 1), # Mutant Tiru | Grand Spell Essence
    (8600005, 2330002, 100, 1, 1), # Mutant Tiru | Mighty Bullet
    (8600005, 2003532, 100, 1, 1), # Mutant Tiru | Exceptional Concentration Potion
    (8600005, 2003534, 100, 1, 1), # Mutant Tiru | Exceptional Fortitude Potion
    (8600005, 4021033, 100, 1, 1), # Mutant Tiru | Basic Spell Essence
    (8600005, 2434021, 100, 1, 1), # Mutant Tiru | Medal of Honor
    (8600005, 2000036, 100, 1, 1), # Mutant Tiru | Reindeer Milk
    (8600005, 4000647, 7500, 1, 1), # Mutant Tiru | Mutant Tiru Feather
    (8600005, 4034983, 7500, 1, 1), # Mutant Tiru | Dream Fragment
    (8600005, 4034984, 7500, 1, 1), # Mutant Tiru | Dream Fragment
    (8600005, 4034985, 7500, 1, 1), # Mutant Tiru | Dream Fragment
    (8600005, 4034986, 7500, 1, 1), # Mutant Tiru | Dream Fragment
    (8600005, 4034987, 7500, 1, 1), # Mutant Tiru | Dream Fragment
    (8600005, 4034988, 7500, 1, 1), # Mutant Tiru | Dream Fragment
    (8600005, 2001554, 100, 1, 1), # Mutant Tiru | Sunset Dew
    (8600004, 4021031, 50, 1, 1), # Mutant Tino | Twisted Time
    (8600004, 2512013, 100, 1, 1), # Mutant Tino | Dexterity Potion VIII Recipe
    (8600004, 2512288, 100, 1, 1), # Mutant Tino | Exceptional Luck Boost Potion Recipe
    (8600004, 2870456, 100, 1, 1), # Mutant Tino | Mutant Tino Familiar
    (8600004, 4021020, 10, 1, 1), # Mutant Tino | Confusion Fragment
    (8600004, 4021032, 7500, 1, 1), # Mutant Tino | Mana Crystal
    (8600004, 2512271, 100, 1, 1), # Mutant Tino | Legendary Blessing Potion Recipe
    (8600004, 4021035, 100, 1, 1), # Mutant Tino | Grand Spell Essence
    (8600004, 2512268, 100, 1, 1), # Mutant Tino | Legendary Hero Potion Recipe
    (8600004, 2000036, 100, 1, 1), # Mutant Tino | Reindeer Milk
    (8600004, 4034983, 7500, 1, 1), # Mutant Tino | Dream Fragment
    (8600004, 4034984, 7500, 1, 1), # Mutant Tino | Dream Fragment
    (8600004, 4034985, 7500, 1, 1), # Mutant Tino | Dream Fragment
    (8600004, 4034986, 7500, 1, 1), # Mutant Tino | Dream Fragment
    (8600004, 4034987, 7500, 1, 1), # Mutant Tino | Dream Fragment
    (8600004, 4034988, 7500, 1, 1), # Mutant Tino | Dream Fragment
    (8600004, 2512172, 100, 1, 1), # Mutant Tino | Advanced Dexterity Pill IV Recipe
    (8600004, 2512229, 100, 1, 1), # Mutant Tino | Advanced Defense Pill I Recipe
    (8600004, 2590004, 100, 1, 1), # Mutant Tino | Prototype Soul Enchanter
    (8600004, 2434021, 100, 1, 1), # Mutant Tino | Medal of Honor
    (8600004, 2512136, 100, 1, 1), # Mutant Tino | Advanced Magic Attack Potion III Recipe
    (8600004, 2512283, 100, 1, 1), # Mutant Tino | Superior Intelligence Boost Potion Recipe
    (8600004, 2512274, 100, 1, 1), # Mutant Tino | Exceptional Concentration Potion Recipe
    (8600004, 4000646, 7500, 1, 1), # Mutant Tino | Mutant Tino Feather
    (8600004, 4021033, 100, 1, 1), # Mutant Tino | Basic Spell Essence
    (8600004, 2512134, 100, 1, 1), # Mutant Tino | Advanced Magic Attack Potion I Recipe
    (8600004, 2048000, 100, 1, 1), # Mutant Tino | Scroll for Pet Equip. for Speed 100%
    (8600004, 2511035, 100, 1, 1), # Mutant Tino | Sharp Exquisite Belt Recipe
    (8600004, 4021034, 100, 1, 1), # Mutant Tino | Fine Spell Essence
    (8600004, 2001554, 100, 1, 1), # Mutant Tino | Sunset Dew
    (8600004, 2512091, 100, 1, 1), # Mutant Tino | Advanced Strength Potion III Recipe
    (8600004, 2512191, 100, 1, 1), # Mutant Tino | Advanced Intelligence Pill III Recipe
    (8600004, 2511101, 100, 1, 1), # Mutant Tino | Nimble Hound Earrings Recipe
    (8643012, 2000037, 100, 1, 1), # Weakened Dreamkeeper | Sunrise Dew
    (8643012, 2435719, 100, 1, 1), # Weakened Dreamkeeper | Nodestone
    (8643012, 4021020, 10, 1, 1), # Weakened Dreamkeeper | Confusion Fragment
    (8643012, 4021034, 100, 1, 1), # Weakened Dreamkeeper | Fine Spell Essence
    (8643012, 4021031, 50, 1, 1), # Weakened Dreamkeeper | Twisted Time
    (8643012, 2000036, 100, 1, 1), # Weakened Dreamkeeper | Reindeer Milk
    (8643012, 4021035, 100, 1, 1), # Weakened Dreamkeeper | Grand Spell Essence
    (8643012, 4021033, 100, 1, 1), # Weakened Dreamkeeper | Basic Spell Essence
    (8643012, 4021032, 7500, 1, 1), # Weakened Dreamkeeper | Mana Crystal
    (8643012, 4034983, 7500, 1, 1), # Weakened Dreamkeeper | Dream Fragment
    (8643012, 4034984, 7500, 1, 1), # Weakened Dreamkeeper | Dream Fragment
    (8643012, 4034985, 7500, 1, 1), # Weakened Dreamkeeper | Dream Fragment
    (8643012, 4034986, 7500, 1, 1), # Weakened Dreamkeeper | Dream Fragment
    (8643012, 4034987, 7500, 1, 1), # Weakened Dreamkeeper | Dream Fragment
    (8643012, 4034988, 7500, 1, 1), # Weakened Dreamkeeper | Dream Fragment
    (2600303, 2870245, 100, 1, 1), # Sage Cat | Sage Cat Familiar
    (2600303, 2040901, 100, 1, 1), # Sage Cat | Scroll for Shield for DEF 60%
    (2600303, 4003005, 7500, 1, 1), # Sage Cat | Soft Feather
    (2600303, 2000037, 100, 1, 1), # Sage Cat | Sunrise Dew
    (2600303, 2022057, 100, 1, 1), # Sage Cat | Tofu
    (2600303, 2001527, 100, 1, 1), # Sage Cat | Unagi
    (2600303, 2044701, 100, 1, 1), # Sage Cat | Scroll for Claw for ATT 60%
    (2600303, 2510479, 100, 1, 1), # Sage Cat | Celestial Cane Recipe
    (2600303, 4007001, 7500, 1, 1), # Sage Cat | Magic Powder (White)
    (2600303, 2512287, 100, 1, 1), # Sage Cat | Superior Luck Boost Potion Recipe
    (2600303, 2510076, 100, 1, 1), # Sage Cat | Reverse Bergamot Recipe
    (2600303, 4006000, 7500, 1, 1), # Sage Cat | The Magic Rock
    (2600303, 4000289, 7500, 1, 1), # Sage Cat | Cat Doll
    (2600303, 2001001, 100, 1, 1), # Sage Cat | Ice Cream Pop
    (2600303, 2512269, 100, 1, 1), # Sage Cat | Superior Blessing Potion Recipe
    (2600303, 4004003, 7500, 1, 1), # Sage Cat | LUK Crystal Ore
    (2600303, 4020004, 7500, 1, 1); # Sage Cat | Opal Ore

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (2600303, 2001526, 100, 1, 1), # Sage Cat | Pure Water
    (2600303, 4007000, 7500, 1, 1), # Sage Cat | Magic Powder (Brown)
    (2600303, 2512272, 100, 1, 1), # Sage Cat | Intermediate Concentration Potion Recipe
    (2600303, 2330003, 100, 1, 1), # Sage Cat | Vital Bullet
    (2600303, 4020002, 7500, 1, 1), # Sage Cat | AquaMarine Ore
    (2600303, 2070003, 100, 1, 1), # Sage Cat | Kumbi Throwing-Stars
    (2600303, 4000021, 7500, 1, 1), # Sage Cat | Leather
    (2600303, 2044312, 100, 1, 1), # Sage Cat | Scroll for Spear for Accuracy 60%
    (2600302, 2022057, 100, 1, 1), # The Book Ghost | Tofu
    (2600302, 2040601, 100, 1, 1), # The Book Ghost | Scroll for Bottomwear for DEF 60%
    (2600302, 2044412, 100, 1, 1), # The Book Ghost | Scroll for Pole-Arm for Accuracy 60%
    (2600302, 2001001, 100, 1, 1), # The Book Ghost | Ice Cream Pop
    (2600302, 4007002, 7500, 1, 1), # The Book Ghost | Magic Powder (Blue)
    (2600302, 4000299, 7500, 1, 1), # The Book Ghost | The Book Ghost's Sheet of Paper
    (2600302, 2000005, 100, 1, 1), # The Book Ghost | Power Elixir
    (2600302, 2060000, 100, 1, 1), # The Book Ghost | Arrow for Bow
    (2600302, 4010006, 7500, 1, 1), # The Book Ghost | Gold Ore
    (2600302, 2070003, 100, 1, 1), # The Book Ghost | Kumbi Throwing-Stars
    (2600302, 2040825, 100, 1, 1), # The Book Ghost | Scroll for Gloves for HP 10%
    (2600302, 2000006, 100, 1, 1), # The Book Ghost | Mana Elixir
    (2600302, 4000298, 7500, 1, 1), # The Book Ghost | Old Paper
    (2600302, 4007007, 7500, 1, 1), # The Book Ghost | Magic Powder (Black)
    (2600302, 2512055, 100, 1, 1), # The Book Ghost | Blessing Potion Recipe
    (2600302, 4020008, 7500, 1, 1), # The Book Ghost | Black Crystal Ore
    (2600302, 2001526, 100, 1, 1), # The Book Ghost | Pure Water
    (2600302, 2043017, 100, 1, 1), # The Book Ghost | Scroll for One-Handed Sword for Accuracy 60%
    (2600302, 2870202, 100, 1, 1), # The Book Ghost | The Book Ghost Familiar
    (2600301, 4020002, 7500, 1, 1), # Wooden Target Dummy | AquaMarine Ore
    (2600301, 4010006, 7500, 1, 1), # Wooden Target Dummy | Gold Ore
    (2600301, 2512066, 100, 1, 1), # Wooden Target Dummy | Advanced Luck Boost Potion Recipe
    (2600301, 4000287, 7500, 1, 1), # Wooden Target Dummy | Wooden Doll
    (2600301, 2022057, 100, 1, 1), # Wooden Target Dummy | Tofu
    (2600301, 2040621, 100, 1, 1), # Wooden Target Dummy | Scroll for Bottomwear for HP 60%
    (2600301, 2048004, 100, 1, 1), # Wooden Target Dummy | Scroll for Pet Equip. for Jump 60%
    (2600301, 2001526, 100, 1, 1), # Wooden Target Dummy | Pure Water
    (2600301, 2040825, 100, 1, 1), # Wooden Target Dummy | Scroll for Gloves for HP 10%
    (2600301, 2000006, 100, 1, 1), # Wooden Target Dummy | Mana Elixir
    (2600301, 4006001, 7500, 1, 1), # Wooden Target Dummy | The Summoning Rock
    (2600301, 4004000, 7500, 1, 1), # Wooden Target Dummy | Power Crystal Ore
    (2600301, 2000005, 100, 1, 1), # Wooden Target Dummy | Power Elixir
    (2600301, 2870200, 100, 1, 1), # Wooden Target Dummy | Wooden Target Dummy Familiar
    (2600301, 2040619, 100, 1, 1), # Wooden Target Dummy | Scroll for Bottomwear for Jump 10%
    (2600301, 2001001, 100, 1, 1), # Wooden Target Dummy | Ice Cream Pop
    (2600301, 4007006, 7500, 1, 1), # Wooden Target Dummy | Magic Powder (Red)
    (2600301, 4007004, 7500, 1, 1), # Wooden Target Dummy | Magic Powder (Yellow)
    (2600301, 2043301, 100, 1, 1), # Wooden Target Dummy | Scroll for Dagger for ATT 60%
    (2600300, 2040026, 100, 1, 1), # Straw Target Dummy | Scroll for Helmet for INT 10%
    (2600300, 2061000, 100, 1, 1), # Straw Target Dummy | Arrow for Crossbow
    (2600300, 2000005, 100, 1, 1), # Straw Target Dummy | Power Elixir
    (2600300, 4000286, 7500, 1, 1), # Straw Target Dummy | Straw Doll
    (2600300, 2040801, 100, 1, 1), # Straw Target Dummy | Scroll for Gloves for DEX 60%
    (2600300, 2044301, 100, 1, 1), # Straw Target Dummy | Scroll for Spear for ATT 60%
    (2600300, 2070010, 100, 1, 1), # Straw Target Dummy | Icicle
    (2600300, 2044602, 100, 1, 1), # Straw Target Dummy | Scroll for Crossbow for ATT 10%
    (2600300, 4007004, 7500, 1, 1), # Straw Target Dummy | Magic Powder (Yellow)
    (2600300, 4004002, 7500, 1, 1), # Straw Target Dummy | DEX Crystal Ore
    (2600300, 2060000, 100, 1, 1), # Straw Target Dummy | Arrow for Bow
    (2600300, 2512067, 100, 1, 1), # Straw Target Dummy | Basic Hero Potion Recipe
    (2600300, 2043401, 100, 1, 1), # Straw Target Dummy | Scroll for Katara for ATT 60%
    (2600300, 2870199, 100, 1, 1), # Straw Target Dummy | Straw Target Dummy Familiar
    (2600300, 4007000, 7500, 1, 1), # Straw Target Dummy | Magic Powder (Brown)
    (2600300, 2001526, 100, 1, 1), # Straw Target Dummy | Pure Water
    (2600300, 4020005, 7500, 1, 1), # Straw Target Dummy | Sapphire Ore
    (6090000, 2870228, 100, 1, 1), # Lich | Lich Familiar
    (6090000, 4021034, 100, 1, 1), # Lich | Fine Spell Essence
    (6090000, 4021031, 50, 1, 1), # Lich | Twisted Time
    (6090000, 4021033, 100, 1, 1), # Lich | Basic Spell Essence
    (6090000, 4000336, 7500, 1, 1), # Lich | Bible of the Corrupt
    (6090000, 4021035, 100, 1, 1), # Lich | Grand Spell Essence
    (6090000, 4000633, 7500, 1, 1), # Lich | Red Contract Orb
    (6090000, 4021032, 7500, 1, 1), # Lich | Mana Crystal
    (6090001, 4000415, 7500, 1, 1), # Snow Witch | Ice Tear
    (6090001, 2870229, 100, 1, 1), # Snow Witch | Snow Witch Familiar
    (6090002, 4021034, 100, 1, 1), # Bamboo Warrior | Fine Spell Essence
    (6090002, 4021033, 100, 1, 1), # Bamboo Warrior | Basic Spell Essence
    (6090002, 4021031, 50, 1, 1), # Bamboo Warrior | Twisted Time
    (6090002, 4000414, 7500, 1, 1), # Bamboo Warrior | Bamboo Nameplate
    (6090002, 4021032, 7500, 1, 1), # Bamboo Warrior | Mana Crystal
    (6090002, 4021035, 100, 1, 1), # Bamboo Warrior | Grand Spell Essence
    (6090003, 4000416, 7500, 1, 1), # Scholar Ghost | Traditional Scholar Hat
    (6090003, 2870231, 100, 1, 1), # Scholar Ghost | Scholar Ghost Familiar
    (2600311, 2512153, 100, 1, 1), # Giant Centipede | Advanced Strength Pill V Recipe
    (2600311, 2070004, 100, 1, 1), # Giant Centipede | Tobi Throwing-Stars
    (2600311, 2330003, 100, 1, 1), # Giant Centipede | Vital Bullet
    (2600311, 4021031, 50, 1, 1), # Giant Centipede | Twisted Time
    (2600311, 2512236, 100, 1, 1), # Giant Centipede | Advanced Defense Pill VIII Recipe
    (2600311, 2871088, 100, 1, 1), # Giant Centipede | King Centipede Familiar
    (2600311, 2512051, 100, 1, 1), # Giant Centipede | Advanced Mana Potion Recipe
    (2600311, 2060000, 100, 1, 1), # Giant Centipede | Arrow for Bow
    (2600311, 2512270, 100, 1, 1), # Giant Centipede | Exceptional Blessing Potion Recipe
    (2600311, 2512258, 100, 1, 1), # Giant Centipede | Advanced Magic Attack Pill V Recipe
    (8642003, 4034945, 7500, 1, 1), # Ramanana | Sour Peel
    (8642003, 4021020, 10, 1, 1), # Ramanana | Confusion Fragment
    (8642003, 2000037, 100, 1, 1), # Ramanana | Sunrise Dew
    (8642003, 2431174, 100, 1, 1), # Ramanana | Medal of Honor
    (8642003, 4021031, 50, 1, 1), # Ramanana | Twisted Time
    (8642003, 4021035, 100, 1, 1), # Ramanana | Grand Spell Essence
    (8642003, 2435719, 100, 1, 1), # Ramanana | Nodestone
    (8642003, 2000036, 100, 1, 1), # Ramanana | Reindeer Milk
    (8642003, 2590004, 100, 1, 1), # Ramanana | Prototype Soul Enchanter
    (8642003, 4021034, 100, 1, 1), # Ramanana | Fine Spell Essence
    (8642003, 4021032, 7500, 1, 1), # Ramanana | Mana Crystal
    (8642003, 4021033, 100, 1, 1), # Ramanana | Basic Spell Essence
    (2600310, 4007004, 7500, 1, 1), # Red Flower Serpent | Magic Powder (Yellow)
    (2600310, 2001526, 100, 1, 1), # Red Flower Serpent | Pure Water
    (2600310, 2000005, 100, 1, 1), # Red Flower Serpent | Power Elixir
    (2600310, 2044212, 100, 1, 1), # Red Flower Serpent | Scroll for Two-Handed BW for Accuracy 60%
    (2600310, 2061000, 100, 1, 1), # Red Flower Serpent | Arrow for Crossbow
    (2600310, 4004000, 7500, 1, 1), # Red Flower Serpent | Power Crystal Ore
    (2600310, 2060000, 100, 1, 1), # Red Flower Serpent | Arrow for Bow
    (2600310, 4000281, 7500, 1, 1), # Red Flower Serpent | Snake Leather
    (2600310, 2870164, 100, 1, 1), # Red Flower Serpent | Red Flower Serpent Familiar
    (2600310, 4007003, 7500, 1, 1), # Red Flower Serpent | Magic Powder (Green)
    (2600310, 2022057, 100, 1, 1), # Red Flower Serpent | Tofu
    (2600310, 4010002, 7500, 1, 1), # Red Flower Serpent | Mithril Ore
    (2600310, 2044801, 100, 1, 1), # Red Flower Serpent | Scroll for Knuckle for Attack 60%
    (2600310, 4000279, 7500, 1, 1), # Red Flower Serpent | Necki Flower
    (2600310, 2044901, 100, 1, 1), # Red Flower Serpent | Scroll for Gun for Attack 60%
    (2600310, 2044002, 100, 1, 1), # Red Flower Serpent | Scroll for Two-handed Sword for ATT 10%
    (2600310, 4010004, 7500, 1, 1), # Red Flower Serpent | Silver Ore
    (2600310, 2512067, 100, 1, 1), # Red Flower Serpent | Basic Hero Potion Recipe
    (2600310, 2070004, 100, 1, 1), # Red Flower Serpent | Tobi Throwing-Stars
    (8642002, 4021020, 10, 1, 1), # Ewenana | Confusion Fragment
    (8642002, 4021033, 100, 1, 1), # Ewenana | Basic Spell Essence
    (8642002, 2435719, 100, 1, 1), # Ewenana | Nodestone
    (8642002, 4021031, 50, 1, 1), # Ewenana | Twisted Time
    (8642002, 4034944, 7500, 1, 1), # Ewenana | Greasy Peel
    (8642002, 4021034, 100, 1, 1), # Ewenana | Fine Spell Essence
    (8642002, 2431174, 100, 1, 1), # Ewenana | Medal of Honor
    (8642002, 2590004, 100, 1, 1), # Ewenana | Prototype Soul Enchanter
    (8642002, 2000036, 100, 1, 1), # Ewenana | Reindeer Milk
    (8642002, 2000037, 100, 1, 1), # Ewenana | Sunrise Dew
    (8642002, 4021032, 7500, 1, 1), # Ewenana | Mana Crystal
    (8642002, 4021035, 100, 1, 1), # Ewenana | Grand Spell Essence
    (2600309, 4000280, 7500, 1, 1), # Blue Flower Serpent | Necki Swimming Cap
    (2600309, 2040705, 100, 1, 1), # Blue Flower Serpent | Scroll for Shoes for Jump 10%
    (2600309, 2330002, 100, 1, 1), # Blue Flower Serpent | Mighty Bullet
    (2600309, 4000281, 7500, 1, 1), # Blue Flower Serpent | Snake Leather
    (2600309, 4004001, 7500, 1, 1), # Blue Flower Serpent | Wisdom Crystal Ore
    (2600309, 2043301, 100, 1, 1), # Blue Flower Serpent | Scroll for Dagger for ATT 60%
    (2600309, 2000006, 100, 1, 1), # Blue Flower Serpent | Mana Elixir
    (2600309, 4020005, 7500, 1, 1), # Blue Flower Serpent | Sapphire Ore
    (2600309, 4007007, 7500, 1, 1), # Blue Flower Serpent | Magic Powder (Black)
    (2600309, 2048002, 100, 1, 1), # Blue Flower Serpent | Scroll for Pet Equip. for Speed 10%
    (2600309, 2060000, 100, 1, 1), # Blue Flower Serpent | Arrow for Bow
    (2600309, 2511106, 100, 1, 1), # Blue Flower Serpent | Angelic Blessing Recipe
    (2600309, 4010004, 7500, 1, 1), # Blue Flower Serpent | Silver Ore
    (2600309, 2001001, 100, 1, 1), # Blue Flower Serpent | Ice Cream Pop
    (2600309, 2001527, 100, 1, 1), # Blue Flower Serpent | Unagi
    (2600309, 2001002, 100, 1, 1), # Blue Flower Serpent | Very Special Sundae
    (2600309, 4007005, 7500, 1, 1), # Blue Flower Serpent | Magic Powder (Purple)
    (2600309, 2870163, 100, 1, 1), # Blue Flower Serpent | Blue Flower Serpent Familiar
    (2600309, 2044501, 100, 1, 1), # Blue Flower Serpent | Scroll for Bow for ATT 60%
    (8642001, 4021031, 50, 1, 1), # Bighorn Pinedeer | Twisted Time
    (8642001, 4021020, 10, 1, 1), # Bighorn Pinedeer | Confusion Fragment
    (8642001, 2431174, 100, 1, 1), # Bighorn Pinedeer | Medal of Honor
    (8642001, 4021033, 100, 1, 1), # Bighorn Pinedeer | Basic Spell Essence
    (8642001, 2435719, 100, 1, 1), # Bighorn Pinedeer | Nodestone
    (8642001, 4021032, 7500, 1, 1), # Bighorn Pinedeer | Mana Crystal
    (8642001, 4021035, 100, 1, 1), # Bighorn Pinedeer | Grand Spell Essence
    (8642001, 2000037, 100, 1, 1), # Bighorn Pinedeer | Sunrise Dew
    (8642001, 4034943, 7500, 1, 1), # Bighorn Pinedeer | Spicy Hoof
    (8642001, 4021034, 100, 1, 1), # Bighorn Pinedeer | Fine Spell Essence
    (8642001, 2000036, 100, 1, 1), # Bighorn Pinedeer | Reindeer Milk
    (8642001, 2590004, 100, 1, 1), # Bighorn Pinedeer | Prototype Soul Enchanter
    (2600308, 4004003, 7500, 1, 1), # Black Porky | LUK Crystal Ore
    (2600308, 2001527, 100, 1, 1), # Black Porky | Unagi
    (2600308, 4000278, 7500, 1, 1), # Black Porky | Needle Pouch
    (2600308, 2000002, 100, 1, 1), # Black Porky | White Potion
    (2600308, 2870162, 100, 1, 1), # Black Porky | Black Porky Familiar
    (2600308, 2044201, 100, 1, 1), # Black Porky | Scroll for Two-handed BW for ATT 60%
    (2600308, 4020004, 7500, 1, 1), # Black Porky | Opal Ore
    (2600308, 2061000, 100, 1, 1), # Black Porky | Arrow for Crossbow
    (2600308, 2512057, 100, 1, 1), # Black Porky | Dexterity Boost Potion Recipe
    (2600308, 4020008, 7500, 1, 1), # Black Porky | Black Crystal Ore
    (2600308, 2001001, 100, 1, 1), # Black Porky | Ice Cream Pop
    (2600308, 4007002, 7500, 1, 1), # Black Porky | Magic Powder (Blue)
    (2600308, 2048001, 100, 1, 1), # Black Porky | Scroll for Pet Equip. for Speed 60%
    (2600308, 4007007, 7500, 1, 1), # Black Porky | Magic Powder (Black)
    (2600308, 2043114, 100, 1, 1), # Black Porky | Scroll for One-Handed Axe for Accuracy 10%
    (2600308, 2044601, 100, 1, 1), # Black Porky | Scroll for Crossbow for ATT 60%
    (8642000, 2000037, 100, 1, 1), # Pinedeer | Sunrise Dew
    (8642000, 4021035, 100, 1, 1), # Pinedeer | Grand Spell Essence
    (8642000, 4034942, 7500, 1, 1), # Pinedeer | Sweet Hoof
    (8642000, 2000036, 100, 1, 1), # Pinedeer | Reindeer Milk
    (8642000, 2435719, 100, 1, 1), # Pinedeer | Nodestone
    (8642000, 2590004, 100, 1, 1), # Pinedeer | Prototype Soul Enchanter
    (8642000, 4021031, 50, 1, 1), # Pinedeer | Twisted Time
    (8642000, 2431174, 100, 1, 1), # Pinedeer | Medal of Honor
    (8642000, 4021020, 10, 1, 1), # Pinedeer | Confusion Fragment
    (8642000, 4021032, 7500, 1, 1), # Pinedeer | Mana Crystal
    (8642000, 4021033, 100, 1, 1), # Pinedeer | Basic Spell Essence
    (8642000, 4021034, 100, 1, 1), # Pinedeer | Fine Spell Essence
    (2600307, 2040621, 100, 1, 1), # Red Porky | Scroll for Bottomwear for HP 60%
    (2600307, 2070007, 100, 1, 1), # Red Porky | Hwabi Throwing-Stars
    (2600307, 2870161, 100, 1, 1), # Red Porky | Red Porky Familiar
    (2600307, 2044601, 100, 1, 1), # Red Porky | Scroll for Crossbow for ATT 60%
    (2600307, 4000277, 7500, 1, 1), # Red Porky | Thimble
    (2600307, 2049000, 100, 1, 1), # Red Porky | Pure Clean Slate Scroll 1%
    (2600307, 2511106, 100, 1, 1), # Red Porky | Angelic Blessing Recipe
    (2600307, 2001001, 100, 1, 1), # Red Porky | Ice Cream Pop
    (2600307, 2000002, 100, 1, 1), # Red Porky | White Potion
    (2600307, 2044801, 100, 1, 1), # Red Porky | Scroll for Knuckle for Attack 60%
    (2600307, 2001002, 100, 1, 1), # Red Porky | Very Special Sundae
    (2600307, 4010001, 7500, 1, 1), # Red Porky | Steel Ore
    (2600307, 2022057, 100, 1, 1), # Red Porky | Tofu
    (2600307, 4004002, 7500, 1, 1), # Red Porky | DEX Crystal Ore
    (2600307, 2330002, 100, 1, 1), # Red Porky | Mighty Bullet
    (8642007, 4034949, 7500, 1, 1), # Ripe Wolfruit | Chewy Sole
    (8642007, 4021031, 50, 1, 1), # Ripe Wolfruit | Twisted Time
    (8642007, 4021034, 100, 1, 1), # Ripe Wolfruit | Fine Spell Essence
    (8642007, 4021033, 100, 1, 1), # Ripe Wolfruit | Basic Spell Essence
    (8642007, 4021032, 7500, 1, 1), # Ripe Wolfruit | Mana Crystal
    (8642007, 2000037, 100, 1, 1), # Ripe Wolfruit | Sunrise Dew
    (8642007, 2590004, 100, 1, 1), # Ripe Wolfruit | Prototype Soul Enchanter
    (8642007, 2000036, 100, 1, 1), # Ripe Wolfruit | Reindeer Milk
    (8642007, 2431174, 100, 1, 1), # Ripe Wolfruit | Medal of Honor
    (8642007, 4021020, 10, 1, 1), # Ripe Wolfruit | Confusion Fragment
    (8642007, 2435719, 100, 1, 1), # Ripe Wolfruit | Nodestone
    (8642007, 4021035, 100, 1, 1), # Ripe Wolfruit | Grand Spell Essence
    (2600306, 2044601, 100, 1, 1), # Chipmunk | Scroll for Crossbow for ATT 60%
    (2600306, 4007006, 7500, 1, 1), # Chipmunk | Magic Powder (Red)
    (2600306, 2022057, 100, 1, 1), # Chipmunk | Tofu
    (2600306, 2870160, 100, 1, 1), # Chipmunk | Chipmunk Familiar
    (2600306, 2330002, 100, 1, 1), # Chipmunk | Mighty Bullet
    (2600306, 2001527, 100, 1, 1), # Chipmunk | Unagi
    (2600306, 2048005, 100, 1, 1), # Chipmunk | Scroll for Pet Equip. for Jump 10%
    (2600306, 4000021, 7500, 1, 1), # Chipmunk | Leather
    (2600306, 2000002, 100, 1, 1), # Chipmunk | White Potion
    (2600306, 2000006, 100, 1, 1), # Chipmunk | Mana Elixir
    (2600306, 2511106, 100, 1, 1), # Chipmunk | Angelic Blessing Recipe
    (2600306, 4010006, 7500, 1, 1), # Chipmunk | Gold Ore
    (2600306, 2061000, 100, 1, 1), # Chipmunk | Arrow for Crossbow
    (2600306, 2070007, 100, 1, 1), # Chipmunk | Hwabi Throwing-Stars
    (2600306, 2044801, 100, 1, 1), # Chipmunk | Scroll for Knuckle for Attack 60%
    (2600306, 4010001, 7500, 1, 1), # Chipmunk | Steel Ore
    (2600306, 2001001, 100, 1, 1), # Chipmunk | Ice Cream Pop
    (2600306, 4000276, 7500, 1, 1), # Chipmunk | Acorn
    (2600306, 4007005, 7500, 1, 1), # Chipmunk | Magic Powder (Purple)
    (8642006, 4021034, 100, 1, 1), # Unripe Wolfruit | Fine Spell Essence
    (8642006, 4034948, 7500, 1, 1), # Unripe Wolfruit | Soft Sole
    (8642006, 4021020, 10, 1, 1), # Unripe Wolfruit | Confusion Fragment
    (8642006, 4021033, 100, 1, 1), # Unripe Wolfruit | Basic Spell Essence
    (8642006, 2435719, 100, 1, 1), # Unripe Wolfruit | Nodestone
    (8642006, 2431174, 100, 1, 1), # Unripe Wolfruit | Medal of Honor
    (8642006, 4021035, 100, 1, 1), # Unripe Wolfruit | Grand Spell Essence
    (8642006, 4021032, 7500, 1, 1), # Unripe Wolfruit | Mana Crystal
    (8642006, 2000036, 100, 1, 1), # Unripe Wolfruit | Reindeer Milk
    (8642006, 4021031, 50, 1, 1), # Unripe Wolfruit | Twisted Time
    (8642006, 2000037, 100, 1, 1), # Unripe Wolfruit | Sunrise Dew
    (8642006, 2590004, 100, 1, 1), # Unripe Wolfruit | Prototype Soul Enchanter
    (8642005, 4021034, 100, 1, 1), # Angry Flyon | Fine Spell Essence
    (8642005, 2431174, 100, 1, 1), # Angry Flyon | Medal of Honor
    (8642005, 2590004, 100, 1, 1), # Angry Flyon | Prototype Soul Enchanter
    (8642005, 4021020, 10, 1, 1), # Angry Flyon | Confusion Fragment
    (8642005, 2435719, 100, 1, 1), # Angry Flyon | Nodestone
    (8642005, 4021031, 50, 1, 1), # Angry Flyon | Twisted Time
    (8642005, 2000037, 100, 1, 1), # Angry Flyon | Sunrise Dew
    (8642005, 4021032, 7500, 1, 1), # Angry Flyon | Mana Crystal
    (8642005, 4021033, 100, 1, 1), # Angry Flyon | Basic Spell Essence
    (8642005, 4034947, 7500, 1, 1), # Angry Flyon | Zesty Mane
    (8642005, 4021035, 100, 1, 1), # Angry Flyon | Grand Spell Essence
    (8642005, 2000036, 100, 1, 1), # Angry Flyon | Reindeer Milk
    (2600304, 2022149, 100, 1, 1), # Master Dummy | Pill of Intelligence
    (2600304, 2512055, 100, 1, 1), # Master Dummy | Blessing Potion Recipe
    (2600304, 2512153, 100, 1, 1), # Master Dummy | Advanced Strength Pill V Recipe
    (2600304, 2061001, 100, 1, 1), # Master Dummy | Bronze Arrow for Crossbow
    (2600304, 2512115, 100, 1, 1), # Master Dummy | Advanced Intelligence Potion VII Recipe
    (2600304, 2512236, 100, 1, 1), # Master Dummy | Advanced Defense Pill VIII Recipe
    (2600304, 2000003, 100, 1, 1), # Master Dummy | Blue Potion
    (2600304, 4000298, 7500, 1, 1), # Master Dummy | Old Paper
    (2600304, 4009003, 7500, 1, 1), # Master Dummy | Sage Cat Doll
    (2600304, 4010005, 7500, 1, 1), # Master Dummy | Orihalcon Ore
    (2600304, 4004004, 7500, 1, 1), # Master Dummy | Dark Crystal Ore
    (2600304, 2070007, 100, 1, 1), # Master Dummy | Hwabi Throwing-Stars
    (2600304, 2049000, 100, 1, 1), # Master Dummy | Pure Clean Slate Scroll 1%
    (2600304, 4000412, 7500, 1, 1), # Master Dummy | Proof of Training
    (2600304, 2000002, 100, 1, 1), # Master Dummy | White Potion
    (2600304, 2870302, 100, 1, 1), # Master Dummy | King Sage Cat Familiar
    (2600304, 4020008, 7500, 1, 1), # Master Dummy | Black Crystal Ore
    (2600304, 2870183, 100, 1, 1), # Master Dummy | Master Dummy Familiar
    (2600304, 2060001, 100, 1, 1), # Master Dummy | Bronze Arrow for Bow
    (2600304, 2600305, 100, 1, 1), # Master Dummy | ?
    (2600304, 2000005, 100, 1, 1), # Master Dummy | Power Elixir
    (2600304, 2512028, 100, 1, 1), # Master Dummy | Luck Potion VII Recipe
    (8642004, 2431174, 100, 1, 1), # Flyon | Medal of Honor
    (8642004, 4021031, 50, 1, 1), # Flyon | Twisted Time
    (8642004, 4021035, 100, 1, 1), # Flyon | Grand Spell Essence
    (8642004, 4034946, 7500, 1, 1), # Flyon | Fresh Mane
    (8642004, 2000036, 100, 1, 1), # Flyon | Reindeer Milk
    (8642004, 2435719, 100, 1, 1), # Flyon | Nodestone
    (8642004, 4021020, 10, 1, 1), # Flyon | Confusion Fragment
    (8642004, 4021034, 100, 1, 1), # Flyon | Fine Spell Essence
    (8642004, 4021033, 100, 1, 1), # Flyon | Basic Spell Essence
    (8642004, 2000037, 100, 1, 1), # Flyon | Sunrise Dew
    (8642004, 4021032, 7500, 1, 1), # Flyon | Mana Crystal
    (8642004, 2590004, 100, 1, 1), # Flyon | Prototype Soul Enchanter
    (2600319, 4000293, 7500, 1, 1), # Bellflower Root | Bellflower
    (2600319, 2001001, 100, 1, 1), # Bellflower Root | Ice Cream Pop
    (2600319, 2043017, 100, 1, 1), # Bellflower Root | Scroll for One-Handed Sword for Accuracy 60%
    (2600319, 2040801, 100, 1, 1), # Bellflower Root | Scroll for Gloves for DEX 60%
    (2600319, 4004003, 7500, 1, 1), # Bellflower Root | LUK Crystal Ore
    (2600319, 2001526, 100, 1, 1), # Bellflower Root | Pure Water
    (2600319, 2060000, 100, 1, 1), # Bellflower Root | Arrow for Bow
    (2600319, 2000037, 100, 1, 1), # Bellflower Root | Sunrise Dew
    (2600319, 2043201, 100, 1, 1), # Bellflower Root | Scroll for One-Handed BW for ATT 60%
    (2600319, 4006000, 7500, 1, 1), # Bellflower Root | The Magic Rock
    (2600319, 2512281, 100, 1, 1), # Bellflower Root | Superior Strength Boost Potion Recipe
    (2600319, 2511019, 100, 1, 1), # Bellflower Root | Shiny Red Pirate Symbol Recipe
    (2600319, 2870197, 100, 1, 1), # Bellflower Root | Bellflower Root Familiar
    (2600319, 4020005, 7500, 1, 1), # Bellflower Root | Sapphire Ore
    (2600319, 2330002, 100, 1, 1), # Bellflower Root | Mighty Bullet
    (2600319, 2044903, 100, 1, 1), # Bellflower Root | Scroll for Gun for Attack 70%
    (2600319, 2512273, 100, 1, 1), # Bellflower Root | Superior Concentration Potion Recipe
    (2600319, 2512275, 100, 1, 1), # Bellflower Root | Superior Fortitude Potion Recipe
    (2600319, 4007004, 7500, 1, 1), # Bellflower Root | Magic Powder (Yellow)
    (2600319, 2022057, 100, 1, 1), # Bellflower Root | Tofu
    (2600319, 4010001, 7500, 1, 1), # Bellflower Root | Steel Ore
    (2600319, 4007003, 7500, 1, 1), # Bellflower Root | Magic Powder (Green)
    (8642011, 2000037, 100, 1, 1), # Boss Rhyturtle | Sunrise Dew
    (8642011, 2590004, 100, 1, 1), # Boss Rhyturtle | Prototype Soul Enchanter
    (8642011, 4021020, 10, 1, 1), # Boss Rhyturtle | Confusion Fragment
    (8642011, 4034953, 7500, 1, 1), # Boss Rhyturtle | Soft Shell
    (8642011, 4021033, 100, 1, 1), # Boss Rhyturtle | Basic Spell Essence
    (8642011, 4021031, 50, 1, 1), # Boss Rhyturtle | Twisted Time
    (8642011, 2431174, 100, 1, 1), # Boss Rhyturtle | Medal of Honor
    (8642011, 4021034, 100, 1, 1), # Boss Rhyturtle | Fine Spell Essence
    (8642011, 2000036, 100, 1, 1), # Boss Rhyturtle | Reindeer Milk
    (8642011, 2435719, 100, 1, 1), # Boss Rhyturtle | Nodestone
    (8642011, 4021032, 7500, 1, 1), # Boss Rhyturtle | Mana Crystal
    (8642011, 4021035, 100, 1, 1), # Boss Rhyturtle | Grand Spell Essence
    (2600318, 2048005, 100, 1, 1), # Ginseng Jar | Scroll for Pet Equip. for Jump 10%
    (2600318, 2040601, 100, 1, 1), # Ginseng Jar | Scroll for Bottomwear for DEF 60%
    (2600318, 2512273, 100, 1, 1), # Ginseng Jar | Superior Concentration Potion Recipe
    (2600318, 2510053, 100, 1, 1), # Ginseng Jar | Black Garina Shoes Recipe
    (2600318, 2061000, 100, 1, 1), # Ginseng Jar | Arrow for Crossbow
    (2600318, 2000037, 100, 1, 1), # Ginseng Jar | Sunrise Dew
    (2600318, 2512269, 100, 1, 1), # Ginseng Jar | Superior Blessing Potion Recipe
    (2600318, 4000292, 7500, 1, 1), # Ginseng Jar | Ginseng-Boiled Water
    (2600318, 2070009, 100, 1, 1), # Ginseng Jar | Wooden Top
    (2600318, 4007002, 7500, 1, 1), # Ginseng Jar | Magic Powder (Blue)
    (2600318, 2512281, 100, 1, 1), # Ginseng Jar | Superior Strength Boost Potion Recipe
    (2600318, 4010001, 7500, 1, 1), # Ginseng Jar | Steel Ore
    (2600318, 2000005, 100, 1, 1), # Ginseng Jar | Power Elixir
    (2600318, 2048001, 100, 1, 1), # Ginseng Jar | Scroll for Pet Equip. for Speed 60%
    (2600318, 2870166, 100, 1, 1), # Ginseng Jar | Ginseng Jar Familiar
    (2600318, 4020000, 7500, 1, 1), # Ginseng Jar | Garnet Ore
    (2600318, 2001526, 100, 1, 1), # Ginseng Jar | Pure Water
    (2600318, 2049100, 100, 1, 1), # Ginseng Jar | Chaos Scroll 60%
    (2600318, 4006001, 7500, 1, 1), # Ginseng Jar | The Summoning Rock
    (2600318, 2022057, 100, 1, 1), # Ginseng Jar | Tofu
    (2600318, 4007004, 7500, 1, 1), # Ginseng Jar | Magic Powder (Yellow)
    (8642010, 4021034, 100, 1, 1), # Rhyturtle | Fine Spell Essence
    (8642010, 4021020, 10, 1, 1), # Rhyturtle | Confusion Fragment
    (8642010, 4021033, 100, 1, 1), # Rhyturtle | Basic Spell Essence
    (8642010, 2431174, 100, 1, 1), # Rhyturtle | Medal of Honor
    (8642010, 4034952, 7500, 1, 1), # Rhyturtle | Crunchy Shell
    (8642010, 2000037, 100, 1, 1), # Rhyturtle | Sunrise Dew
    (8642010, 2435719, 100, 1, 1), # Rhyturtle | Nodestone
    (8642010, 4021032, 7500, 1, 1), # Rhyturtle | Mana Crystal
    (8642010, 4021031, 50, 1, 1), # Rhyturtle | Twisted Time
    (8642010, 2590004, 100, 1, 1), # Rhyturtle | Prototype Soul Enchanter
    (8642010, 2000036, 100, 1, 1), # Rhyturtle | Reindeer Milk
    (8642010, 4021035, 100, 1, 1), # Rhyturtle | Grand Spell Essence
    (2600317, 4007004, 7500, 1, 1), # Jar | Magic Powder (Yellow)
    (2600317, 2048001, 100, 1, 1), # Jar | Scroll for Pet Equip. for Speed 60%
    (2600317, 4007001, 7500, 1, 1), # Jar | Magic Powder (White)
    (2600317, 2043401, 100, 1, 1), # Jar | Scroll for Katara for ATT 60%
    (2600317, 2061000, 100, 1, 1), # Jar | Arrow for Crossbow
    (2600317, 2001001, 100, 1, 1), # Jar | Ice Cream Pop
    (2600317, 2001002, 100, 1, 1), # Jar | Very Special Sundae
    (2600317, 2070003, 100, 1, 1), # Jar | Kumbi Throwing-Stars
    (2600317, 2512287, 100, 1, 1), # Jar | Superior Luck Boost Potion Recipe
    (2600317, 4000291, 7500, 1, 1), # Jar | Broken Piece of Pot
    (2600317, 4020000, 7500, 1, 1), # Jar | Garnet Ore
    (2600317, 2044102, 100, 1, 1), # Jar | Scroll for Two-handed Axe for ATT 10%
    (2600317, 2044412, 100, 1, 1), # Jar | Scroll for Pole-Arm for Accuracy 60%
    (2600317, 2001527, 100, 1, 1), # Jar | Unagi
    (2600317, 2870165, 100, 1, 1), # Jar | Jar Familiar
    (2600317, 2511026, 100, 1, 1), # Jar | Tenacious Brown Gilded Belt Recipe
    (2600317, 4020006, 7500, 1, 1), # Jar | Topaz Ore
    (2600317, 2022057, 100, 1, 1), # Jar | Tofu
    (2600317, 2512273, 100, 1, 1), # Jar | Superior Concentration Potion Recipe
    (2600317, 4004002, 7500, 1, 1), # Jar | DEX Crystal Ore
    (2600317, 4006001, 7500, 1, 1), # Jar | The Summoning Rock
    (2600317, 2000005, 100, 1, 1), # Jar | Power Elixir
    (8642009, 4021035, 100, 1, 1), # Blue Catfish | Grand Spell Essence
    (8642009, 4021032, 7500, 1, 1), # Blue Catfish | Mana Crystal
    (8642009, 4021034, 100, 1, 1), # Blue Catfish | Fine Spell Essence
    (8642009, 2435719, 100, 1, 1), # Blue Catfish | Nodestone
    (8642009, 2431174, 100, 1, 1), # Blue Catfish | Medal of Honor
    (8642009, 4021020, 10, 1, 1), # Blue Catfish | Confusion Fragment
    (8642009, 4021033, 100, 1, 1), # Blue Catfish | Basic Spell Essence
    (8642009, 2590004, 100, 1, 1), # Blue Catfish | Prototype Soul Enchanter
    (8642009, 4034951, 7500, 1, 1), # Blue Catfish | Tart Fins
    (8642009, 4021031, 50, 1, 1), # Blue Catfish | Twisted Time
    (8642009, 2000036, 100, 1, 1), # Blue Catfish | Reindeer Milk
    (8642009, 2000037, 100, 1, 1), # Blue Catfish | Sunrise Dew
    (2600316, 4000284, 7500, 1, 1), # Tae Roon | Yellow Belt
    (2600316, 2510260, 500, 1, 1), # Tae Roon | Infinite Throwing-Knives Recipe
    (2600316, 2512058, 100, 1, 1), # Tae Roon | Intelligence Boost Potion Recipe
    (2600316, 2002011, 100, 1, 1), # Tae Roon | Pain Reliever
    (2600316, 2512056, 100, 1, 1), # Tae Roon | Strength Boost Potion Recipe
    (2600316, 2000005, 100, 1, 1), # Tae Roon | Power Elixir
    (2600316, 2512266, 100, 1, 1), # Tae Roon | Superior Hero Potion Recipe
    (2600316, 2512281, 100, 1, 1), # Tae Roon | Superior Strength Boost Potion Recipe
    (2600316, 2061001, 100, 1, 1), # Tae Roon | Bronze Arrow for Crossbow
    (2600316, 4000021, 7500, 1, 1), # Tae Roon | Leather
    (2600316, 4020006, 7500, 1, 1), # Tae Roon | Topaz Ore
    (2600316, 2000002, 100, 1, 1), # Tae Roon | White Potion
    (2600316, 2870300, 100, 1, 1), # Tae Roon | Tae Roon Familiar
    (2600316, 4011007, 7500, 1, 1), # Tae Roon | Moon Rock
    (2600316, 2022142, 100, 1, 1), # Tae Roon | Mind & Heart Medicine
    (2600316, 4000285, 7500, 1, 1), # Tae Roon | Red Belt
    (2600316, 4010001, 7500, 1, 1), # Tae Roon | Steel Ore
    (2600316, 2070004, 100, 1, 1), # Tae Roon | Tobi Throwing-Stars
    (2600316, 4004000, 7500, 1, 1), # Tae Roon | Power Crystal Ore
    (2600316, 2512053, 100, 1, 1), # Tae Roon | Health Boost Potion Recipe
    (2600316, 4000283, 7500, 1, 1), # Tae Roon | Bear Foot
    (2600316, 2510172, 100, 1, 1), # Tae Roon | Android (F) Recipe
    (2600316, 2049100, 100, 1, 1), # Tae Roon | Chaos Scroll 60%
    (2600316, 2060001, 100, 1, 1), # Tae Roon | Bronze Arrow for Bow
    (2600316, 2000003, 100, 1, 1), # Tae Roon | Blue Potion
    (2600316, 4021009, 7500, 1, 1), # Tae Roon | Star Rock
    (8642008, 2435719, 100, 1, 1), # Green Catfish | Nodestone
    (8642008, 2590004, 100, 1, 1), # Green Catfish | Prototype Soul Enchanter
    (8642008, 2000037, 100, 1, 1), # Green Catfish | Sunrise Dew
    (8642008, 2431174, 100, 1, 1), # Green Catfish | Medal of Honor
    (8642008, 4021035, 100, 1, 1), # Green Catfish | Grand Spell Essence
    (8642008, 2000036, 100, 1, 1), # Green Catfish | Reindeer Milk
    (8642008, 4021031, 50, 1, 1), # Green Catfish | Twisted Time
    (8642008, 4034950, 7500, 1, 1), # Green Catfish | Savory Fin
    (8642008, 4021034, 100, 1, 1), # Green Catfish | Fine Spell Essence
    (8642008, 4021020, 10, 1, 1), # Green Catfish | Confusion Fragment
    (8642008, 4021032, 7500, 1, 1), # Green Catfish | Mana Crystal
    (8642008, 4021033, 100, 1, 1), # Green Catfish | Basic Spell Essence
    (2600315, 4003005, 7500, 1, 1), # Panda | Soft Feather
    (2600315, 2044014, 100, 1, 1), # Panda | Scroll for Two-Handed Sword for Accuracy 10%
    (2600315, 2000006, 100, 1, 1), # Panda | Mana Elixir
    (2600315, 2001002, 100, 1, 1), # Panda | Very Special Sundae
    (2600315, 2512057, 100, 1, 1), # Panda | Dexterity Boost Potion Recipe
    (2600315, 2044601, 100, 1, 1), # Panda | Scroll for Crossbow for ATT 60%
    (2600315, 2043401, 100, 1, 1), # Panda | Scroll for Katara for ATT 60%
    (2600315, 2001527, 100, 1, 1), # Panda | Unagi
    (2600315, 2870241, 100, 1, 1), # Panda | Panda Familiar
    (2600315, 4007007, 7500, 1, 1), # Panda | Magic Powder (Black)
    (2600315, 4000283, 7500, 1, 1), # Panda | Bear Foot
    (2600315, 2070005, 100, 1, 1), # Panda | Steely Throwing-Knives
    (2600315, 4010005, 7500, 1, 1), # Panda | Orihalcon Ore
    (2600315, 4020007, 7500, 1, 1), # Panda | Diamond Ore
    (2600315, 4007003, 7500, 1, 1), # Panda | Magic Powder (Green)
    (2600315, 2022057, 100, 1, 1), # Panda | Tofu
    (2600315, 2000005, 100, 1, 1), # Panda | Power Elixir
    (2600315, 2061000, 100, 1, 1), # Panda | Arrow for Crossbow
    (2600315, 2044301, 100, 1, 1), # Panda | Scroll for Spear for ATT 60%
    (2600315, 4000285, 7500, 1, 1), # Panda | Red Belt
    (2600315, 4000021, 7500, 1, 1), # Panda | Leather
    (8642015, 2000037, 100, 1, 1), # Patriarch Birdshark | Sunrise Dew
    (8642015, 4034957, 7500, 1, 1), # Patriarch Birdshark | Chewy Talon
    (8642015, 4021032, 7500, 1, 1), # Patriarch Birdshark | Mana Crystal
    (8642015, 2435719, 100, 1, 1), # Patriarch Birdshark | Nodestone
    (8642015, 2431174, 100, 1, 1), # Patriarch Birdshark | Medal of Honor
    (8642015, 4021020, 10, 1, 1), # Patriarch Birdshark | Confusion Fragment
    (8642015, 4021031, 50, 1, 1), # Patriarch Birdshark | Twisted Time
    (8642015, 4021034, 100, 1, 1), # Patriarch Birdshark | Fine Spell Essence
    (8642015, 4021035, 100, 1, 1), # Patriarch Birdshark | Grand Spell Essence
    (8642015, 4021033, 100, 1, 1), # Patriarch Birdshark | Basic Spell Essence
    (8642015, 2590004, 100, 1, 1), # Patriarch Birdshark | Prototype Soul Enchanter
    (8642015, 2000036, 100, 1, 1), # Patriarch Birdshark | Reindeer Milk
    (2600314, 2001527, 100, 1, 1), # Peach Monkey | Unagi
    (2600314, 2510479, 100, 1, 1), # Peach Monkey | Celestial Cane Recipe
    (2600314, 4020004, 7500, 1, 1), # Peach Monkey | Opal Ore
    (2600314, 2040901, 100, 1, 1), # Peach Monkey | Scroll for Shield for DEF 60%
    (2600314, 2043401, 100, 1, 1), # Peach Monkey | Scroll for Katara for ATT 60%
    (2600314, 2061000, 100, 1, 1), # Peach Monkey | Arrow for Crossbow
    (2600314, 2512281, 100, 1, 1), # Peach Monkey | Superior Strength Boost Potion Recipe
    (2600314, 2511044, 100, 1, 1), # Peach Monkey | Pink Bear Pendant Recipe
    (2600314, 2870243, 100, 1, 1), # Peach Monkey | Peach Monkey Familiar
    (2600314, 4000021, 7500, 1, 1), # Peach Monkey | Leather
    (2600314, 2001528, 100, 1, 1), # Peach Monkey | Melting Cheese
    (2600314, 4000282, 7500, 1, 1), # Peach Monkey | Peach Seed
    (2600314, 2040328, 100, 1, 1), # Peach Monkey | Scroll for Earring for HP 10%
    (2600314, 2022057, 100, 1, 1), # Peach Monkey | Tofu
    (2600314, 2048000, 100, 1, 1), # Peach Monkey | Scroll for Pet Equip. for Speed 100%
    (2600314, 4007006, 7500, 1, 1), # Peach Monkey | Magic Powder (Red)
    (2600314, 2512272, 100, 1, 1), # Peach Monkey | Intermediate Concentration Potion Recipe
    (2600314, 4003005, 7500, 1, 1), # Peach Monkey | Soft Feather
    (2600314, 2060000, 100, 1, 1), # Peach Monkey | Arrow for Bow
    (2600314, 4007007, 7500, 1, 1), # Peach Monkey | Magic Powder (Black)
    (2600314, 2001001, 100, 1, 1), # Peach Monkey | Ice Cream Pop
    (8642014, 4021031, 50, 1, 1), # Birdshark | Twisted Time
    (8642014, 2000036, 100, 1, 1), # Birdshark | Reindeer Milk
    (8642014, 2590004, 100, 1, 1), # Birdshark | Prototype Soul Enchanter
    (8642014, 4021032, 7500, 1, 1), # Birdshark | Mana Crystal
    (8642014, 4021033, 100, 1, 1), # Birdshark | Basic Spell Essence
    (8642014, 2000037, 100, 1, 1), # Birdshark | Sunrise Dew
    (8642014, 2435719, 100, 1, 1), # Birdshark | Nodestone
    (8642014, 4021020, 10, 1, 1), # Birdshark | Confusion Fragment
    (8642014, 4021035, 100, 1, 1), # Birdshark | Grand Spell Essence
    (8642014, 4034956, 7500, 1, 1), # Birdshark | Unpleasant Talon
    (8642014, 2431174, 100, 1, 1), # Birdshark | Medal of Honor
    (8642014, 4021034, 100, 1, 1), # Birdshark | Fine Spell Essence
    (2600313, 2512067, 100, 1, 1), # Cute Reindeer | Basic Hero Potion Recipe
    (2600313, 2049000, 100, 1, 1), # Cute Reindeer | Pure Clean Slate Scroll 1%
    (2600313, 4010006, 7500, 1, 1), # Cute Reindeer | Gold Ore
    (2600313, 4007005, 7500, 1, 1), # Cute Reindeer | Magic Powder (Purple)
    (2600313, 2000005, 100, 1, 1), # Cute Reindeer | Power Elixir
    (2600313, 2043801, 100, 1, 1), # Cute Reindeer | Scroll for Staff for Magic Attack 60%
    (2600313, 2870201, 100, 1, 1), # Cute Reindeer | Cute Reindeer Familiar
    (2600313, 2000006, 100, 1, 1), # Cute Reindeer | Mana Elixir
    (2600313, 2040621, 100, 1, 1), # Cute Reindeer | Scroll for Bottomwear for HP 60%
    (2600313, 2001526, 100, 1, 1), # Cute Reindeer | Pure Water
    (2600313, 4007007, 7500, 1, 1), # Cute Reindeer | Magic Powder (Black)
    (2600313, 4003005, 7500, 1, 1), # Cute Reindeer | Soft Feather
    (2600313, 2001002, 100, 1, 1), # Cute Reindeer | Very Special Sundae
    (2600313, 4000021, 7500, 1, 1), # Cute Reindeer | Leather
    (2600313, 2060000, 100, 1, 1), # Cute Reindeer | Arrow for Bow
    (2600313, 2061000, 100, 1, 1), # Cute Reindeer | Arrow for Crossbow
    (2600313, 2043017, 100, 1, 1), # Cute Reindeer | Scroll for One-Handed Sword for Accuracy 60%
    (8642013, 4021034, 100, 1, 1), # Patriarch Crilia | Fine Spell Essence
    (8642013, 2000037, 100, 1, 1), # Patriarch Crilia | Sunrise Dew
    (8642013, 2431174, 100, 1, 1), # Patriarch Crilia | Medal of Honor
    (8642013, 4021033, 100, 1, 1), # Patriarch Crilia | Basic Spell Essence
    (8642013, 4021031, 50, 1, 1), # Patriarch Crilia | Twisted Time
    (8642013, 2590004, 100, 1, 1), # Patriarch Crilia | Prototype Soul Enchanter
    (8642013, 4021035, 100, 1, 1), # Patriarch Crilia | Grand Spell Essence
    (8642013, 4021020, 10, 1, 1), # Patriarch Crilia | Confusion Fragment
    (8642013, 2000036, 100, 1, 1), # Patriarch Crilia | Reindeer Milk
    (8642013, 4034955, 7500, 1, 1), # Patriarch Crilia | Sticky Feather
    (8642013, 2435719, 100, 1, 1), # Patriarch Crilia | Nodestone
    (8642013, 4021032, 7500, 1, 1), # Patriarch Crilia | Mana Crystal
    (2600312, 4004004, 7500, 1, 1), # Grizzly | Dark Crystal Ore
    (2600312, 2512058, 100, 1, 1), # Grizzly | Intelligence Boost Potion Recipe
    (2600312, 2043001, 100, 1, 1), # Grizzly | Scroll for One-Handed Sword for ATT 60%
    (2600312, 4000283, 7500, 1, 1), # Grizzly | Bear Foot
    (2600312, 4007002, 7500, 1, 1), # Grizzly | Magic Powder (Blue)
    (2600312, 4020004, 7500, 1, 1), # Grizzly | Opal Ore
    (2600312, 2000005, 100, 1, 1), # Grizzly | Power Elixir
    (2600312, 2001001, 100, 1, 1), # Grizzly | Ice Cream Pop
    (2600312, 2049100, 100, 1, 1), # Grizzly | Chaos Scroll 60%
    (2600312, 2040601, 100, 1, 1), # Grizzly | Scroll for Bottomwear for DEF 60%
    (2600312, 2001526, 100, 1, 1), # Grizzly | Pure Water
    (2600312, 4004002, 7500, 1, 1), # Grizzly | DEX Crystal Ore
    (2600312, 4000284, 7500, 1, 1), # Grizzly | Yellow Belt
    (2600312, 2040031, 100, 1, 1), # Grizzly | Scroll for Helmet for DEX 10%
    (2600312, 2870196, 100, 1, 1), # Grizzly | Grizzly Familiar
    (2600312, 4000021, 7500, 1, 1), # Grizzly | Leather
    (2600312, 4003005, 7500, 1, 1), # Grizzly | Soft Feather
    (2600312, 4010005, 7500, 1, 1), # Grizzly | Orihalcon Ore
    (2600312, 2000006, 100, 1, 1), # Grizzly | Mana Elixir
    (8642012, 2000037, 100, 1, 1), # Crilia | Sunrise Dew
    (8642012, 4021020, 10, 1, 1), # Crilia | Confusion Fragment
    (8642012, 4021031, 50, 1, 1), # Crilia | Twisted Time
    (8642012, 2000036, 100, 1, 1), # Crilia | Reindeer Milk
    (8642012, 2590004, 100, 1, 1), # Crilia | Prototype Soul Enchanter
    (8642012, 4021035, 100, 1, 1), # Crilia | Grand Spell Essence
    (8642012, 4034954, 7500, 1, 1), # Crilia | Slimy Feather
    (8642012, 2431174, 100, 1, 1), # Crilia | Medal of Honor
    (8642012, 4021032, 7500, 1, 1), # Crilia | Mana Crystal
    (8642012, 2435719, 100, 1, 1), # Crilia | Nodestone
    (8642012, 4021033, 100, 1, 1), # Crilia | Basic Spell Essence
    (8642012, 4021034, 100, 1, 1), # Crilia | Fine Spell Essence
    (8642016, 4034958, 7500, 1, 1), # Slurpy Tree | Slurpy Fruit
    (8142100, 4021035, 100, 1, 1), # Risell Squid | Grand Spell Essence
    (8142100, 4021034, 100, 1, 1), # Risell Squid | Fine Spell Essence
    (8142100, 2590004, 100, 1, 1), # Risell Squid | Prototype Soul Enchanter
    (8142100, 4021033, 100, 1, 1), # Risell Squid | Basic Spell Essence
    (8142100, 2000037, 100, 1, 1), # Risell Squid | Sunrise Dew
    (8142100, 4000184, 7500, 1, 1), # Risell Squid | Butter-Toasted Squid
    (8142100, 4021032, 7500, 1, 1), # Risell Squid | Mana Crystal
    (8142100, 2434021, 100, 1, 1), # Risell Squid | Medal of Honor
    (8142100, 2001528, 100, 1, 1), # Risell Squid | Melting Cheese
    (8142100, 4021031, 50, 1, 1), # Risell Squid | Twisted Time
    (8142100, 2870333, 100, 1, 1), # Risell Squid | Risell Squid Familiar
    (8641003, 4021034, 100, 1, 1), # Joyful Erda | Fine Spell Essence
    (8641003, 4021035, 100, 1, 1), # Joyful Erda | Grand Spell Essence
    (8641003, 4021032, 7500, 1, 1), # Joyful Erda | Mana Crystal
    (8641003, 4034925, 7500, 1, 1), # Joyful Erda | Joyful Erdas Sample
    (8641003, 4021031, 50, 1, 1), # Joyful Erda | Twisted Time
    (8641003, 4034917, 7500, 1, 1), # Joyful Erda | Joyful Memory
    (8641003, 4021033, 100, 1, 1), # Joyful Erda | Basic Spell Essence
    (8641003, 2435719, 100, 1, 1), # Joyful Erda | Nodestone
    (8641003, 2000037, 100, 1, 1), # Joyful Erda | Sunrise Dew
    (8641003, 4034934, 7500, 1, 1), # Joyful Erda | Oblivion Inhibitor
    (8641003, 4021020, 10, 1, 1), # Joyful Erda | Confusion Fragment
    (8641003, 2000036, 100, 1, 1), # Joyful Erda | Reindeer Milk
    (8641002, 4021032, 7500, 1, 1), # Sad Erda | Mana Crystal
    (8641002, 2000037, 100, 1, 1), # Sad Erda | Sunrise Dew
    (8641002, 4021031, 50, 1, 1), # Sad Erda | Twisted Time
    (8641002, 2000036, 100, 1, 1), # Sad Erda | Reindeer Milk
    (8641002, 4034924, 7500, 1, 1), # Sad Erda | Sad Erdas Sample
    (8641002, 4021034, 100, 1, 1), # Sad Erda | Fine Spell Essence
    (8641002, 4021020, 10, 1, 1), # Sad Erda | Confusion Fragment
    (8641002, 2435719, 100, 1, 1), # Sad Erda | Nodestone
    (8641002, 4021033, 100, 1, 1), # Sad Erda | Basic Spell Essence
    (8641002, 4034934, 7500, 1, 1), # Sad Erda | Oblivion Inhibitor
    (8641002, 4021035, 100, 1, 1), # Sad Erda | Grand Spell Essence
    (8641002, 4034916, 7500, 1, 1), # Sad Erda | Sad Memory
    (8641001, 4034934, 7500, 1, 1), # Raging Erda | Oblivion Inhibitor
    (8641001, 4021020, 10, 1, 1), # Raging Erda | Confusion Fragment
    (8641001, 4021035, 100, 1, 1), # Raging Erda | Grand Spell Essence
    (8641001, 4021032, 7500, 1, 1), # Raging Erda | Mana Crystal
    (8641001, 4021031, 50, 1, 1), # Raging Erda | Twisted Time
    (8641001, 4021034, 100, 1, 1), # Raging Erda | Fine Spell Essence
    (8641001, 4034915, 7500, 1, 1), # Raging Erda | Raging Memory
    (8641001, 4021033, 100, 1, 1), # Raging Erda | Basic Spell Essence
    (8641001, 2000036, 100, 1, 1), # Raging Erda | Reindeer Milk
    (8641001, 2435719, 100, 1, 1), # Raging Erda | Nodestone
    (8641001, 4034923, 7500, 1, 1), # Raging Erda | Raging Erdas Sample
    (8641001, 2000037, 100, 1, 1), # Raging Erda | Sunrise Dew
    (8641000, 4021035, 100, 1, 1), # Happy Erda | Grand Spell Essence
    (8641000, 2000037, 100, 1, 1), # Happy Erda | Sunrise Dew
    (8641000, 4021034, 100, 1, 1), # Happy Erda | Fine Spell Essence
    (8641000, 4021020, 10, 1, 1), # Happy Erda | Confusion Fragment
    (8641000, 4034934, 7500, 1, 1), # Happy Erda | Oblivion Inhibitor
    (8641000, 2435719, 100, 1, 1), # Happy Erda | Nodestone
    (8641000, 4021031, 50, 1, 1), # Happy Erda | Twisted Time
    (8641000, 4021033, 100, 1, 1), # Happy Erda | Basic Spell Essence
    (8641000, 4034922, 7500, 1, 1), # Happy Erda | Happy Erdas Sample
    (8641000, 4021032, 7500, 1, 1), # Happy Erda | Mana Crystal
    (8641000, 2000036, 100, 1, 1), # Happy Erda | Reindeer Milk
    (8641007, 4021020, 10, 1, 1), # Tranquil Erda | Confusion Fragment
    (8641007, 4021032, 7500, 1, 1), # Tranquil Erda | Mana Crystal
    (8641007, 4021031, 50, 1, 1), # Tranquil Erda | Twisted Time
    (8641007, 4021033, 100, 1, 1), # Tranquil Erda | Basic Spell Essence
    (8641007, 4034921, 7500, 1, 1), # Tranquil Erda | Tranquil Solvent
    (8641007, 4034929, 7500, 1, 1), # Tranquil Erda | Tranquil Erdas Sample
    (8641007, 2000037, 100, 1, 1), # Tranquil Erda | Sunrise Dew
    (8641007, 2000036, 100, 1, 1), # Tranquil Erda | Reindeer Milk
    (8641007, 4021034, 100, 1, 1), # Tranquil Erda | Fine Spell Essence
    (8641007, 2435719, 100, 1, 1), # Tranquil Erda | Nodestone
    (8641007, 4021035, 100, 1, 1), # Tranquil Erda | Grand Spell Essence
    (8641007, 4034936, 7500, 1, 1), # Tranquil Erda | Repose Inhibitor
    (8641006, 4021032, 7500, 1, 1), # Soulful Erda | Mana Crystal
    (8641006, 4021035, 100, 1, 1), # Soulful Erda | Grand Spell Essence
    (8641006, 4034920, 7500, 1, 1), # Soulful Erda | Hard Shell
    (8641006, 2000037, 100, 1, 1), # Soulful Erda | Sunrise Dew
    (8641006, 4034935, 7500, 1, 1), # Soulful Erda | Extinction Inhibitor
    (8641006, 4034928, 7500, 1, 1), # Soulful Erda | Soulful Erdas Sample
    (8641006, 2000036, 100, 1, 1), # Soulful Erda | Reindeer Milk
    (8641006, 4021033, 100, 1, 1), # Soulful Erda | Basic Spell Essence
    (8641006, 4021031, 50, 1, 1), # Soulful Erda | Twisted Time
    (8641006, 4021034, 100, 1, 1), # Soulful Erda | Fine Spell Essence
    (8641006, 2435719, 100, 1, 1), # Soulful Erda | Nodestone
    (8641006, 4021020, 10, 1, 1), # Soulful Erda | Confusion Fragment
    (8641005, 4021035, 100, 1, 1), # Blazing Erda | Grand Spell Essence
    (8641005, 4034935, 7500, 1, 1), # Blazing Erda | Extinction Inhibitor
    (8641005, 4021032, 7500, 1, 1), # Blazing Erda | Mana Crystal
    (8641005, 4021033, 100, 1, 1), # Blazing Erda | Basic Spell Essence
    (8641005, 4021031, 50, 1, 1), # Blazing Erda | Twisted Time
    (8641005, 4034919, 7500, 1, 1), # Blazing Erda | Sticky Oil
    (8641005, 2000036, 100, 1, 1), # Blazing Erda | Reindeer Milk
    (8641005, 2435719, 100, 1, 1), # Blazing Erda | Nodestone
    (8641005, 4021020, 10, 1, 1), # Blazing Erda | Confusion Fragment
    (8641005, 2000037, 100, 1, 1), # Blazing Erda | Sunrise Dew
    (8641005, 4021034, 100, 1, 1), # Blazing Erda | Fine Spell Essence
    (8641005, 4034927, 7500, 1, 1), # Blazing Erda | Blazing Erdas Sample
    (8641004, 4021031, 50, 1, 1), # Stone Erda | Twisted Time
    (8641004, 4021033, 100, 1, 1), # Stone Erda | Basic Spell Essence
    (8641004, 4021020, 10, 1, 1), # Stone Erda | Confusion Fragment
    (8641004, 4021032, 7500, 1, 1), # Stone Erda | Mana Crystal
    (8641004, 4021035, 100, 1, 1), # Stone Erda | Grand Spell Essence
    (8641004, 2000037, 100, 1, 1), # Stone Erda | Sunrise Dew
    (8641004, 4034918, 7500, 1, 1), # Stone Erda | Solid Claw
    (8641004, 4034935, 7500, 1, 1), # Stone Erda | Extinction Inhibitor
    (8641004, 4021034, 100, 1, 1), # Stone Erda | Fine Spell Essence
    (8641004, 2000036, 100, 1, 1), # Stone Erda | Reindeer Milk
    (8641004, 2435719, 100, 1, 1), # Stone Erda | Nodestone
    (8641004, 4034926, 7500, 1, 1), # Stone Erda | Stone Erdas Sample
    (3400001, 4034452, 7500, 1, 1), # Mango Bubble Tea | Mango Bubble
    (3400001, 4034451, 7500, 1, 1), # Mango Bubble Tea | Melon Bubble
    (3400001, 3400002, 100, 1, 1), # Mango Bubble Tea | ?
    (8641009, 4021020, 10, 1, 1), # Arma's Follower | Confusion Fragment
    (8641009, 4021034, 100, 1, 1), # Arma's Follower | Fine Spell Essence
    (8641009, 4021035, 100, 1, 1), # Arma's Follower | Grand Spell Essence
    (8641009, 2435719, 100, 1, 1), # Arma's Follower | Nodestone
    (8641009, 4021033, 100, 1, 1), # Arma's Follower | Basic Spell Essence
    (8641009, 2000036, 100, 1, 1), # Arma's Follower | Reindeer Milk
    (8641009, 4021031, 50, 1, 1), # Arma's Follower | Twisted Time
    (8641009, 2000037, 100, 1, 1), # Arma's Follower | Sunrise Dew
    (8641009, 4021032, 7500, 1, 1), # Arma's Follower | Mana Crystal
    (3400000, 4034450, 7500, 1, 1), # Cherry Bubble Tea | Cherry Bubble
    (8641008, 4021032, 7500, 1, 1), # Lantern Erda | Mana Crystal
    (8641008, 4021031, 50, 1, 1), # Lantern Erda | Twisted Time
    (8641008, 2000037, 100, 1, 1), # Lantern Erda | Sunrise Dew
    (8641008, 4034930, 7500, 1, 1), # Lantern Erda | Lantern Erda Samples
    (8641008, 4021033, 100, 1, 1), # Lantern Erda | Basic Spell Essence
    (8641008, 2000036, 100, 1, 1), # Lantern Erda | Reindeer Milk
    (8641008, 4021020, 10, 1, 1), # Lantern Erda | Confusion Fragment
    (8641008, 4021035, 100, 1, 1), # Lantern Erda | Grand Spell Essence
    (8641008, 2435719, 100, 1, 1), # Lantern Erda | Nodestone
    (8641008, 4034921, 7500, 1, 1), # Lantern Erda | Tranquil Solvent
    (8641008, 4034936, 7500, 1, 1), # Lantern Erda | Repose Inhibitor
    (8641008, 4021034, 100, 1, 1), # Lantern Erda | Fine Spell Essence
    (3400006, 4000543, 7500, 1, 1), # Jr. Pepe Doll | Jr. Pepe Keychain
    (3400004, 4000257, 7500, 1, 1), # Yeti Doll | Yeti Key Chain
    (5150001, 2870213, 100, 1, 1), # Skeleton Soldier | Skeleton Soldier Familiar
    (5150001, 4000206, 7500, 1, 1), # Skeleton Soldier | Rib
    (5150001, 2434021, 100, 1, 1), # Skeleton Soldier | Medal of Honor
    (9000201, 4031013, 9000, 1, 1), # Stone Golem | Dark Marble
    (4230600, 4000332, 7500, 1, 1), # Desert Giant | Sand
    (4230600, 2870167, 100, 1, 1), # Desert Giant | Desert Giant Familiar
    (8141300, 2070004, 100, 1, 1), # Squid | Tobi Throwing-Stars
    (8141300, 2434021, 100, 1, 1), # Squid | Medal of Honor
    (8141300, 2590004, 100, 1, 1), # Squid | Prototype Soul Enchanter
    (8141300, 4021035, 100, 1, 1), # Squid | Grand Spell Essence
    (8141300, 2001528, 100, 1, 1), # Squid | Melting Cheese
    (8141300, 2000037, 100, 1, 1), # Squid | Sunrise Dew
    (8141300, 4021033, 100, 1, 1), # Squid | Basic Spell Essence
    (8141300, 4000183, 7500, 1, 1), # Squid | Ink Bottle
    (8141300, 4021032, 7500, 1, 1), # Squid | Mana Crystal
    (8141300, 4021031, 50, 1, 1), # Squid | Twisted Time
    (8141300, 2870331, 100, 1, 1), # Squid | Squid Familiar
    (8141300, 4021034, 100, 1, 1), # Squid | Fine Spell Essence
    (5400000, 4000088, 7500, 1, 1), # Jr. Pepe | Jr. Pepe's Fish
    (5400000, 2870227, 100, 1, 1), # Jr. Pepe | Jr. Pepe Familiar
    (8910100, 2433103, 100, 1, 1), # Von Bon | Boss Medal of Honor
    (8910100, 4310029, 7500, 1, 1), # Von Bon | Crusader Coins
    (1140100, 4010004, 7500, 1, 1), # Ghost Stump | Silver Ore
    (1140100, 4000195, 7500, 1, 1), # Ghost Stump | Seedling
    (1140100, 4020007, 7500, 1, 1), # Ghost Stump | Diamond Ore
    (1140100, 4010000, 7500, 1, 1), # Ghost Stump | Bronze Ore
    (1140100, 2040620, 100, 1, 1), # Ghost Stump | Scroll for Bottomwear for HP 100%
    (1140100, 2043100, 100, 1, 1), # Ghost Stump | Scroll for One-Handed Axe for ATT 100%
    (1140100, 2040823, 100, 1, 1), # Ghost Stump | Scroll for Gloves for HP 100%
    (1140100, 2044802, 100, 1, 1), # Ghost Stump | Scroll for Knuckle for ATT 10%
    (1140100, 2870014, 100, 1, 1), # Ghost Stump | Ghost Stump Familiar
    (1140100, 2000001, 100, 1, 1), # Ghost Stump | Orange Potion
    (1140100, 2060000, 100, 1, 1), # Ghost Stump | Arrow for Bow
    (8300006, 4001401, 7500, 1, 1), # Dragonoir | Dragonoir's Horn
    (8300007, 4001545, 7500, 1, 1), # Dragon Rider | Sky Scale
    (8300007, 2431657, 3000, 1, 1), # Dragon Rider | Dragon Rider's Soul Shard
    (2600326, 4021035, 100, 1, 1), # Lord Pirate | Grand Spell Essence
    (2600326, 4021033, 100, 1, 1), # Lord Pirate | Basic Spell Essence
    (2600326, 4021032, 7500, 1, 1), # Lord Pirate | Mana Crystal
    (2600326, 2000036, 100, 1, 1), # Lord Pirate | Reindeer Milk
    (2600326, 4021040, 7500, 1, 1), # Lord Pirate | Dimension Fragment
    (2600326, 2001554, 100, 1, 1), # Lord Pirate | Sunset Dew
    (2600326, 2434021, 100, 1, 1), # Lord Pirate | Medal of Honor
    (2600326, 4021031, 50, 1, 1), # Lord Pirate | Twisted Time
    (2600326, 4009026, 7500, 1, 1), # Lord Pirate | Lord Pirate's Hook
    (2600326, 4021034, 100, 1, 1), # Lord Pirate | Fine Spell Essence
    (2600326, 4310085, 7500, 1, 1), # Lord Pirate | Time Coin
    (2600326, 2590004, 100, 1, 1), # Lord Pirate | Prototype Soul Enchanter
    (2600325, 2000036, 100, 1, 1), # Gold Captain | Reindeer Milk
    (2600325, 4021032, 7500, 1, 1), # Gold Captain | Mana Crystal
    (2600325, 4310085, 7500, 1, 1), # Gold Captain | Time Coin
    (2600325, 4009025, 7500, 1, 1), # Gold Captain | Pirates's Voyage Log
    (2600325, 4021040, 7500, 1, 1), # Gold Captain | Dimension Fragment
    (2600325, 4021034, 100, 1, 1), # Gold Captain | Fine Spell Essence
    (2600325, 4021031, 50, 1, 1), # Gold Captain | Twisted Time
    (2600325, 2434021, 100, 1, 1), # Gold Captain | Medal of Honor
    (2600325, 4021033, 100, 1, 1), # Gold Captain | Basic Spell Essence
    (2600325, 4021035, 100, 1, 1), # Gold Captain | Grand Spell Essence
    (2600325, 2001554, 100, 1, 1), # Gold Captain | Sunset Dew
    (2600325, 2590004, 100, 1, 1), # Gold Captain | Prototype Soul Enchanter
    (2600324, 4021032, 7500, 1, 1), # Captain | Mana Crystal
    (2600324, 4021035, 100, 1, 1), # Captain | Grand Spell Essence
    (2600324, 4021033, 100, 1, 1), # Captain | Basic Spell Essence
    (2600324, 2001554, 100, 1, 1), # Captain | Sunset Dew
    (2600324, 4021031, 50, 1, 1), # Captain | Twisted Time
    (2600324, 4021034, 100, 1, 1), # Captain | Fine Spell Essence
    (2600324, 2870283, 100, 1, 1), # Captain | Captain Familiar
    (2600324, 2000036, 100, 1, 1), # Captain | Reindeer Milk
    (2600324, 2590004, 100, 1, 1), # Captain | Prototype Soul Enchanter
    (2600324, 2434021, 100, 1, 1), # Captain | Medal of Honor
    (2600324, 4000297, 7500, 1, 1), # Captain | Captain's Hat
    (2600323, 4021034, 100, 1, 1), # Kru | Fine Spell Essence
    (2600323, 2000037, 100, 1, 1), # Kru | Sunrise Dew
    (2600323, 2049401, 100, 1, 1), # Kru | Potential Scroll
    (2600323, 4000296, 7500, 1, 1), # Kru | Mark of the Pirate
    (2600323, 4021032, 7500, 1, 1), # Kru | Mana Crystal
    (2600323, 2590004, 100, 1, 1), # Kru | Prototype Soul Enchanter
    (2600323, 4021033, 100, 1, 1), # Kru | Basic Spell Essence
    (2600323, 2434021, 100, 1, 1), # Kru | Medal of Honor
    (2600323, 2001528, 100, 1, 1), # Kru | Melting Cheese
    (2600323, 4021035, 100, 1, 1), # Kru | Grand Spell Essence
    (2600323, 2870244, 100, 1, 1), # Kru | Kru Familiar
    (2600323, 4021031, 50, 1, 1), # Kru | Twisted Time
    (3230101, 2870093, 100, 1, 1), # Jr. Wraith | Jr. Wraith Familiar
    (3230101, 4000035, 7500, 1, 1), # Jr. Wraith | Tablecloth
    (2600322, 4003005, 7500, 1, 1), # Mr. Alli | Soft Feather
    (2600322, 4021034, 100, 1, 1), # Mr. Alli | Fine Spell Essence
    (2600322, 4021031, 50, 1, 1), # Mr. Alli | Twisted Time
    (2600322, 2512127, 100, 1, 1), # Mr. Alli | Advanced Luck Potion IX Recipe
    (2600322, 2512272, 100, 1, 1), # Mr. Alli | Intermediate Concentration Potion Recipe
    (2600322, 2434021, 100, 1, 1), # Mr. Alli | Medal of Honor
    (2600322, 4021032, 7500, 1, 1), # Mr. Alli | Mana Crystal
    (2600322, 4021035, 100, 1, 1), # Mr. Alli | Grand Spell Essence
    (2600322, 2049401, 100, 1, 1), # Mr. Alli | Potential Scroll
    (2600322, 2000037, 100, 1, 1), # Mr. Alli | Sunrise Dew
    (2600322, 2870242, 100, 1, 1), # Mr. Alli | Mr. Alli Familiar
    (2600322, 2049000, 100, 1, 1), # Mr. Alli | Pure Clean Slate Scroll 1%
    (2600322, 4021033, 100, 1, 1), # Mr. Alli | Basic Spell Essence
    (2600322, 2512278, 100, 1, 1), # Mr. Alli | Superior Insight Potion Recipe
    (2600322, 4000021, 7500, 1, 1), # Mr. Alli | Leather
    (2600322, 2590004, 100, 1, 1), # Mr. Alli | Prototype Soul Enchanter
    (2600322, 2001528, 100, 1, 1), # Mr. Alli | Melting Cheese
    (2600322, 2048002, 100, 1, 1), # Mr. Alli | Scroll for Pet Equip. for Speed 10%
    (2600322, 4000295, 7500, 1, 1), # Mr. Alli | Mr. Alli's Leather
    (3230100, 2870092, 100, 1, 1), # Curse Eye | Curse Eye Familiar
    (3230100, 4000013, 7500, 1, 1), # Curse Eye | Curse Eye Tail
    (2600321, 4310085, 7500, 1, 1), # Gigantic Bellflower Root | Time Coin
    (2600321, 4021032, 7500, 1, 1), # Gigantic Bellflower Root | Mana Crystal
    (2600321, 4021031, 50, 1, 1), # Gigantic Bellflower Root | Twisted Time
    (2600321, 4021035, 100, 1, 1), # Gigantic Bellflower Root | Grand Spell Essence
    (2600321, 4021034, 100, 1, 1), # Gigantic Bellflower Root | Fine Spell Essence
    (2600321, 4021040, 7500, 1, 1), # Gigantic Bellflower Root | Dimension Fragment
    (2600321, 4021033, 100, 1, 1), # Gigantic Bellflower Root | Basic Spell Essence
    (3230103, 2870095, 100, 1, 1), # King Bloctopus | King Bloctopus Familiar
    (3230103, 4000100, 7500, 1, 1), # King Bloctopus | Plastic Crown
    (8820000, 2433103, 100, 1, 1), # Pink Bean | Boss Medal of Honor
    (8820000, 4021034, 100, 1, 1), # Pink Bean | Fine Spell Essence
    (8820000, 4021033, 100, 1, 1), # Pink Bean | Basic Spell Essence
    (8820000, 4021032, 7500, 1, 1), # Pink Bean | Mana Crystal
    (8820000, 2431661, 3000, 1, 1), # Pink Bean | Pink Bean's Soul Shard
    (8820000, 2000005, 100, 1, 1), # Pink Bean | Power Elixir
    (8820000, 4310029, 7500, 1, 1), # Pink Bean | Crusader Coins
    (8820000, 4021031, 50, 1, 1), # Pink Bean | Twisted Time
    (8820000, 4021035, 100, 1, 1), # Pink Bean | Grand Spell Essence
    (8820000, 4021020, 10, 1, 1), # Pink Bean | Confusion Fragment
    (3230102, 2870094, 100, 1, 1), # Lorang | Lorang Familiar
    (3230102, 4000043, 7500, 1, 1), # Lorang | Lorang Claw
    (5100005, 2000036, 100, 1, 1), # Hogul | Reindeer Milk
    (5100005, 2001554, 100, 1, 1), # Hogul | Sunset Dew
    (5100005, 4021031, 50, 1, 1), # Hogul | Twisted Time
    (5100005, 4021034, 100, 1, 1), # Hogul | Fine Spell Essence
    (5100005, 2870187, 100, 1, 1), # Hogul | Hogul Familiar
    (5100005, 2434021, 100, 1, 1), # Hogul | Medal of Honor
    (5100005, 4021033, 100, 1, 1), # Hogul | Basic Spell Essence
    (5100005, 4021032, 7500, 1, 1), # Hogul | Mana Crystal
    (5100005, 4034651, 7500, 1, 1), # Hogul | Hogul Fur
    (5100005, 4034653, 7500, 1, 1), # Hogul | Hogul Leather
    (5100005, 4021035, 100, 1, 1), # Hogul | Grand Spell Essence
    (5100005, 2590004, 100, 1, 1), # Hogul | Prototype Soul Enchanter
    (3230104, 2870096, 100, 1, 1), # Mask Fish | Mask Fish Familiar
    (3230104, 4000167, 7500, 1, 1), # Mask Fish | Hard Needle
    (5100004, 2870186, 100, 1, 1), # Samiho | Samiho Familiar
    (5100004, 4000172, 7500, 1, 1), # Samiho | Three-Tailed Foxtail
    (5100004, 4000021, 7500, 1, 1), # Samiho | Leather
    (5100000, 2590004, 100, 1, 1), # Jr. Yeti | Prototype Soul Enchanter
    (5100000, 2431174, 100, 1, 1), # Jr. Yeti | Medal of Honor
    (5100000, 4021034, 100, 1, 1), # Jr. Yeti | Fine Spell Essence
    (5100000, 2870184, 100, 1, 1), # Jr. Yeti | Jr. Yeti Familiar
    (5100000, 4021033, 100, 1, 1), # Jr. Yeti | Basic Spell Essence
    (5100000, 4021032, 7500, 1, 1), # Jr. Yeti | Mana Crystal
    (5100000, 2001001, 100, 1, 1), # Jr. Yeti | Ice Cream Pop
    (5100000, 4021035, 100, 1, 1), # Jr. Yeti | Grand Spell Essence
    (5100000, 2001002, 100, 1, 1), # Jr. Yeti | Very Special Sundae
    (5100000, 4000048, 7500, 1, 1), # Jr. Yeti | Jr. Yeti Skin
    (5100003, 2000036, 100, 1, 1), # Hodori | Reindeer Milk
    (5100003, 4021033, 100, 1, 1), # Hodori | Basic Spell Essence
    (5100003, 4021035, 100, 1, 1), # Hodori | Grand Spell Essence
    (5100003, 4021032, 7500, 1, 1), # Hodori | Mana Crystal
    (5100003, 2870185, 100, 1, 1), # Hodori | Hodori Familiar
    (5100003, 2000037, 100, 1, 1), # Hodori | Sunrise Dew
    (5100003, 2431174, 100, 1, 1), # Hodori | Medal of Honor
    (5100003, 2590004, 100, 1, 1), # Hodori | Prototype Soul Enchanter
    (5100003, 4021034, 100, 1, 1), # Hodori | Fine Spell Essence
    (5100002, 4000081, 7500, 1, 1), # Firebomb | Firebomb Flame
    (3110301, 2870072, 100, 1, 1), # Dark Sand Dwarf | Dark Sand Dwarf Familiar
    (3110301, 4000352, 7500, 1, 1), # Dark Sand Dwarf | Flaming Desert
    (1140130, 2043800, 100, 1, 1), # Smirking Ghost Stump | Scroll for Staff for Magic Attack 100%
    (1140130, 2000000, 100, 1, 1), # Smirking Ghost Stump | Red Potion
    (1140130, 4000195, 7500, 1, 1), # Smirking Ghost Stump | Seedling
    (1140130, 2870015, 100, 1, 1), # Smirking Ghost Stump | Smirking Ghost Stump Familiar
    (1140130, 2044802, 100, 1, 1), # Smirking Ghost Stump | Scroll for Knuckle for ATT 10%
    (1140130, 2044200, 100, 1, 1), # Smirking Ghost Stump | Scroll for Two-handed BW for ATT 100%
    (1140130, 2044400, 100, 1, 1), # Smirking Ghost Stump | Scroll for Pole Arm for ATT 100%
    (1140130, 2060000, 100, 1, 1), # Smirking Ghost Stump | Arrow for Bow
    (5120502, 2590004, 100, 1, 1), # Sr. Bellflower Root | Prototype Soul Enchanter
    (5120502, 2512124, 100, 1, 1), # Sr. Bellflower Root | Advanced Luck Potion VI Recipe
    (5120502, 2001528, 100, 1, 1), # Sr. Bellflower Root | Melting Cheese
    (5120502, 2512281, 100, 1, 1), # Sr. Bellflower Root | Superior Strength Boost Potion Recipe
    (5120502, 2048000, 100, 1, 1), # Sr. Bellflower Root | Scroll for Pet Equip. for Speed 100%
    (5120502, 4006000, 7500, 1, 1), # Sr. Bellflower Root | The Magic Rock
    (5120502, 2431174, 100, 1, 1), # Sr. Bellflower Root | Medal of Honor
    (5120502, 2000037, 100, 1, 1), # Sr. Bellflower Root | Sunrise Dew
    (5120502, 4021034, 100, 1, 1), # Sr. Bellflower Root | Fine Spell Essence
    (5120502, 4021033, 100, 1, 1), # Sr. Bellflower Root | Basic Spell Essence
    (5120502, 4009020, 7500, 1, 1), # Sr. Bellflower Root | 100-Year-Old Bellflower
    (5120502, 4021031, 50, 1, 1), # Sr. Bellflower Root | Twisted Time
    (5120502, 2870198, 100, 1, 1), # Sr. Bellflower Root | Sr. Bellflower Root Familiar
    (5120502, 4021032, 7500, 1, 1), # Sr. Bellflower Root | Mana Crystal
    (5120502, 4021035, 100, 1, 1), # Sr. Bellflower Root | Grand Spell Essence
    (5120502, 2049401, 100, 1, 1), # Sr. Bellflower Root | Potential Scroll
    (5120500, 4003005, 7500, 1, 1), # Grizzly | Soft Feather
    (5120500, 2049100, 100, 1, 1), # Grizzly | Chaos Scroll 60%
    (5120500, 4000284, 7500, 1, 1), # Grizzly | Yellow Belt
    (5120500, 2590004, 100, 1, 1), # Grizzly | Prototype Soul Enchanter
    (5120500, 2001526, 100, 1, 1), # Grizzly | Pure Water
    (5120500, 2512058, 100, 1, 1), # Grizzly | Intelligence Boost Potion Recipe
    (5120500, 4021033, 100, 1, 1), # Grizzly | Basic Spell Essence
    (5120500, 2870196, 100, 1, 1), # Grizzly | Grizzly Familiar
    (5120500, 2000006, 100, 1, 1), # Grizzly | Mana Elixir
    (5120500, 4021032, 7500, 1, 1), # Grizzly | Mana Crystal
    (5120500, 2001001, 100, 1, 1), # Grizzly | Ice Cream Pop
    (5120500, 4021034, 100, 1, 1), # Grizzly | Fine Spell Essence
    (5120500, 4021035, 100, 1, 1), # Grizzly | Grand Spell Essence
    (5120500, 4000283, 7500, 1, 1), # Grizzly | Bear Foot
    (5120500, 2431174, 100, 1, 1), # Grizzly | Medal of Honor
    (5120500, 2001527, 100, 1, 1), # Grizzly | Unagi
    (5120500, 4000021, 7500, 1, 1), # Grizzly | Leather
    (2600423, 4000617, 7500, 1, 1), # AF Android | Android Bulb
    (2600423, 2870307, 100, 1, 1), # AF Android | AF Android Familiar
    (2600422, 2001002, 100, 1, 1), # Enhanced Security System | Very Special Sundae
    (2600422, 4021032, 7500, 1, 1), # Enhanced Security System | Mana Crystal
    (2600422, 2590004, 100, 1, 1), # Enhanced Security System | Prototype Soul Enchanter
    (2600422, 4000616, 7500, 1, 1), # Enhanced Security System | Vent Pipe
    (2600422, 4021033, 100, 1, 1), # Enhanced Security System | Basic Spell Essence
    (2600422, 4021034, 100, 1, 1), # Enhanced Security System | Fine Spell Essence
    (2600422, 2434021, 100, 1, 1), # Enhanced Security System | Medal of Honor
    (2600422, 2001001, 100, 1, 1), # Enhanced Security System | Ice Cream Pop
    (2600422, 4021035, 100, 1, 1), # Enhanced Security System | Grand Spell Essence
    (2600421, 4021033, 100, 1, 1), # Security System | Basic Spell Essence
    (2600421, 4021034, 100, 1, 1), # Security System | Fine Spell Essence
    (2600421, 2001002, 100, 1, 1), # Security System | Very Special Sundae
    (2600421, 2434021, 100, 1, 1), # Security System | Medal of Honor
    (2600421, 2001001, 100, 1, 1), # Security System | Ice Cream Pop
    (2600421, 2590004, 100, 1, 1), # Security System | Prototype Soul Enchanter
    (2600421, 4021032, 7500, 1, 1), # Security System | Mana Crystal
    (2600421, 4021035, 100, 1, 1), # Security System | Grand Spell Essence
    (2600421, 4000615, 7500, 1, 1), # Security System | Outer Handle
    (8520000, 4000175, 7500, 1, 1), # Pianus | Miniature Pianus
    (8520000, 4021033, 100, 1, 1), # Pianus | Basic Spell Essence
    (8520000, 4021035, 100, 1, 1), # Pianus | Grand Spell Essence
    (8520000, 4021034, 100, 1, 1), # Pianus | Fine Spell Essence
    (8520000, 4021032, 7500, 1, 1), # Pianus | Mana Crystal
    (8520000, 4031253, 7500, 1, 1), # Pianus | Pianus's Scream
    (8520000, 4021031, 50, 1, 1), # Pianus | Twisted Time
    (8520000, 2431895, 3000, 1, 1), # Pianus | Pianus's Soul Shard
    (2600420, 4000613, 7500, 1, 1), # Guard Robot L | Guard Robot L's Electric Racket
    (2600419, 4009046, 7500, 1, 1), # Gigantic Rue Monster | Flaming Rue
    (2600418, 2870309, 100, 1, 1), # Ore Muncher | Ore Muncher Familiar
    (2600418, 4032776, 7500, 1, 1), # Ore Muncher | Ore Muncher DNA Sample
    (2600418, 4000619, 7500, 1, 1), # Ore Muncher | Rue Ore
    (2600417, 2870297, 100, 1, 1), # Raco | Racaroni Familiar
    (2600417, 4032771, 7500, 1, 1), # Raco | Racaroni Heart
    (2600417, 4000614, 7500, 1, 1), # Raco | Raco's Safety Hat
    (2600416, 4032771, 7500, 1, 1), # Racaroni | Racaroni Heart
    (2600416, 4021033, 100, 1, 1), # Racaroni | Basic Spell Essence
    (2600416, 4021032, 7500, 1, 1), # Racaroni | Mana Crystal
    (2600416, 4021034, 100, 1, 1), # Racaroni | Fine Spell Essence
    (2600416, 2870297, 100, 1, 1), # Racaroni | Racaroni Familiar
    (2600416, 2434021, 100, 1, 1), # Racaroni | Medal of Honor
    (2600416, 2001002, 100, 1, 1), # Racaroni | Very Special Sundae
    (2600416, 2001001, 100, 1, 1), # Racaroni | Ice Cream Pop
    (2600416, 2590004, 100, 1, 1), # Racaroni | Prototype Soul Enchanter
    (2600416, 4034533, 7500, 1, 1), # Racaroni | Racaroni Tail
    (2600416, 4021035, 100, 1, 1), # Racaroni | Grand Spell Essence
    (3230200, 4000059, 7500, 1, 1), # Star Pixie | Star Pixie's Starpiece
    (3230200, 2870097, 100, 1, 1), # Star Pixie | Star Pixie Familiar
    (3230200, 2431174, 100, 1, 1), # Star Pixie | Medal of Honor
    (3230200, 4021032, 7500, 1, 1), # Star Pixie | Mana Crystal
    (3230200, 4021035, 100, 1, 1), # Star Pixie | Grand Spell Essence
    (3230200, 2001001, 100, 1, 1), # Star Pixie | Ice Cream Pop
    (3230200, 2001002, 100, 1, 1), # Star Pixie | Very Special Sundae
    (3230200, 4021033, 100, 1, 1), # Star Pixie | Basic Spell Essence
    (3230200, 2590004, 100, 1, 1), # Star Pixie | Prototype Soul Enchanter
    (3230200, 4031309, 7500, 1, 1), # Star Pixie | Cloud Piece
    (3230200, 4021034, 100, 1, 1), # Star Pixie | Fine Spell Essence
    (2600424, 4000618, 7500, 1, 1), # Broken DF Android | Broken Parts
    (2600424, 2870308, 100, 1, 1), # Broken DF Android | Broken DF Android Familiar
    (4250000, 4033109, 500, 1, 1), # Mossy Snail | Forest Stone
    (4250000, 4000436, 7500, 1, 1), # Mossy Snail | Moss Snail Shell
    (4250000, 2870169, 100, 1, 1), # Mossy Snail | Mossy Snail Familiar
    (4250001, 2870170, 100, 1, 1), # Tree Rod | Tree Rod Familiar
    (4250001, 4033109, 500, 1, 1), # Tree Rod | Forest Stone
    (4250001, 4000438, 7500, 1, 1), # Tree Rod | Tree Trunk
    (6230401, 2870254, 100, 1, 1), # Jr. Lucida | Jr. Lucida Familiar
    (8860002, 2002058, 100, 1, 1), # Netherworld Monk | Secret Medicine of the Otherverse
    (9000300, 4031013, 9000, 1, 1), # Swamp Monster | Dark Marble
    (8860001, 2433103, 100, 1, 1), # Arkarium | Boss Medal of Honor
    (8860001, 4021033, 100, 1, 1), # Arkarium | Basic Spell Essence
    (8860001, 4021035, 100, 1, 1), # Arkarium | Grand Spell Essence
    (8860001, 4021032, 7500, 1, 1), # Arkarium | Mana Crystal
    (8860001, 2000005, 100, 1, 1), # Arkarium | Power Elixir
    (8860001, 4310029, 7500, 1, 1), # Arkarium | Crusader Coins
    (8860001, 4021031, 50, 1, 1), # Arkarium | Twisted Time
    (8860001, 4021034, 100, 1, 1), # Arkarium | Fine Spell Essence
    (8860001, 2431753, 3000, 1, 1), # Arkarium | Arkarium's Soul Shard
    (6130101, 4000040, 7500, 1, 1), # Mushmom | Mushmom Spore
    (6130101, 2070006, 100, 1, 1), # Mushmom | Ilbi Throwing-Stars
    (3100102, 2870067, 100, 1, 1), # Kiyo | Kiyo Familiar
    (3100102, 4000335, 7500, 1, 1), # Kiyo | Kiyo's Beek
    (3100101, 2870066, 100, 1, 1), # Sand Dwarf | Sand Dwarf Familiar
    (3100101, 4000334, 7500, 1, 1), # Sand Dwarf | Empty Sack
    (6130103, 2001002, 100, 1, 1), # Pepe | Very Special Sundae
    (6130103, 2590004, 100, 1, 1), # Pepe | Prototype Soul Enchanter
    (6130103, 2001001, 100, 1, 1), # Pepe | Ice Cream Pop
    (6130103, 2434021, 100, 1, 1), # Pepe | Medal of Honor
    (6130103, 4000050, 7500, 1, 1), # Pepe | Pepe Beak
    (150002, 2060000, 100, 1, 1), # Grape Juice Bottle | Arrow for Bow
    (150002, 4000597, 7500, 1, 1), # Grape Juice Bottle | Cork Stopper
    (150002, 2870007, 100, 1, 1), # Grape Juice Bottle | Grape Juice Bottle Familiar
    (150002, 4020006, 7500, 1, 1), # Grape Juice Bottle | Topaz Ore
    (150002, 4011004, 7500, 1, 1), # Grape Juice Bottle | Silver
    (150002, 2061000, 100, 1, 1), # Grape Juice Bottle | Arrow for Crossbow
    (150002, 2000003, 100, 1, 1), # Grape Juice Bottle | Blue Potion
    (150002, 2000000, 100, 1, 1); # Grape Juice Bottle | Red Potion

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (2600407, 2000003, 100, 1, 1), # Strange Sign | Blue Potion
    (2600407, 2434021, 100, 1, 1), # Strange Sign | Medal of Honor
    (2600407, 2060000, 100, 1, 1), # Strange Sign | Arrow for Bow
    (2600407, 4034341, 7500, 1, 1), # Strange Sign | Wooden Board
    (2600407, 2000002, 100, 1, 1), # Strange Sign | White Potion
    (2600407, 2870017, 100, 1, 1), # Strange Sign | Strange Sign Familiar
    (150000, 2000001, 100, 1, 1), # Potted Sprout | Orange Potion
    (150000, 2061000, 100, 1, 1), # Potted Sprout | Arrow for Crossbow
    (150000, 2000003, 100, 1, 1), # Potted Sprout | Blue Potion
    (150000, 2000000, 100, 1, 1), # Potted Sprout | Red Potion
    (150000, 4020005, 7500, 1, 1), # Potted Sprout | Sapphire Ore
    (150000, 4010004, 7500, 1, 1), # Potted Sprout | Silver Ore
    (150000, 2870005, 100, 1, 1), # Potted Sprout | Potted Sprout Familiar
    (150000, 2060000, 100, 1, 1), # Potted Sprout | Arrow for Bow
    (150000, 4000595, 7500, 1, 1), # Potted Sprout | Sprout
    (2600405, 2434021, 100, 1, 1), # Guard Robot E | Medal of Honor
    (2600405, 4000765, 7500, 1, 1), # Guard Robot E | Guard E Chip
    (2600405, 2000006, 100, 1, 1), # Guard Robot E | Mana Elixir
    (150001, 4020000, 7500, 1, 1), # Potted Morning Glory | Garnet Ore
    (150001, 2870006, 100, 1, 1), # Potted Morning Glory | Potted Morning Glory Familiar
    (150001, 4020005, 7500, 1, 1), # Potted Morning Glory | Sapphire Ore
    (150001, 2000000, 100, 1, 1), # Potted Morning Glory | Red Potion
    (150001, 4000596, 7500, 1, 1), # Potted Morning Glory | Morning Glory
    (150001, 2060000, 100, 1, 1), # Potted Morning Glory | Arrow for Bow
    (150001, 2000003, 100, 1, 1), # Potted Morning Glory | Blue Potion
    (150001, 2000001, 100, 1, 1), # Potted Morning Glory | Orange Potion
    (150001, 4010003, 7500, 1, 1), # Potted Morning Glory | Adamantium Ore
    (150001, 2061000, 100, 1, 1), # Potted Morning Glory | Arrow for Crossbow
    (2600404, 2000002, 100, 1, 1), # Patrol Robot S | White Potion
    (2600404, 4000604, 7500, 1, 1), # Patrol Robot S | Patrol Robot S Memory Chip
    (2600404, 4033123, 7500, 1, 1), # Patrol Robot S | Battery
    (2600404, 2000003, 100, 1, 1), # Patrol Robot S | Blue Potion
    (2600404, 2434021, 100, 1, 1), # Patrol Robot S | Medal of Honor
    (2600404, 2870041, 100, 1, 1), # Patrol Robot S | Patrol Robot S Familiar
    (2600403, 2000002, 100, 1, 1), # Streetlight | White Potion
    (2600403, 2000003, 100, 1, 1), # Streetlight | Blue Potion
    (2600403, 2434021, 100, 1, 1), # Streetlight | Medal of Honor
    (2600403, 4000603, 7500, 1, 1), # Streetlight | Streetlight Bulb
    (2600403, 2870040, 100, 1, 1), # Streetlight | Streetlight Familiar
    (2600402, 2000003, 100, 1, 1), # Dust Box | Blue Potion
    (2600402, 2870039, 100, 1, 1), # Dust Box | Dust Box Familiar
    (2600402, 2000002, 100, 1, 1), # Dust Box | White Potion
    (2600402, 2434021, 100, 1, 1), # Dust Box | Medal of Honor
    (2600402, 2060000, 100, 1, 1), # Dust Box | Arrow for Bow
    (2600402, 4000602, 7500, 1, 1), # Dust Box | Dust Ball
    (2600401, 4000601, 7500, 1, 1), # Water Thief Monster | Water Thief's Bottle
    (2600401, 2000002, 100, 1, 1), # Water Thief Monster | White Potion
    (2600401, 2870038, 100, 1, 1), # Water Thief Monster | Water Thief Monster Familiar
    (2600401, 2061000, 100, 1, 1), # Water Thief Monster | Arrow for Crossbow
    (2600401, 2000003, 100, 1, 1), # Water Thief Monster | Blue Potion
    (2600401, 2434021, 100, 1, 1), # Water Thief Monster | Medal of Honor
    (2600400, 4000598, 7500, 1, 1), # Patrol Robot | Patrol Robot Memory Chip
    (2600400, 2000003, 100, 1, 1), # Patrol Robot | Blue Potion
    (2600400, 2870016, 100, 1, 1), # Patrol Robot | Patrol Robot Familiar
    (2600400, 4033121, 7500, 1, 1), # Patrol Robot | Garish Ring
    (2600400, 2060000, 100, 1, 1), # Patrol Robot | Arrow for Bow
    (2600400, 2000002, 100, 1, 1), # Patrol Robot | White Potion
    (2600400, 2434021, 100, 1, 1), # Patrol Robot | Medal of Honor
    (2600415, 4032770, 7500, 1, 1), # Cart Bear | Cart Bear Meat
    (2600415, 4000611, 7500, 1, 1), # Cart Bear | Cart Bear's Cart
    (2600415, 2434021, 100, 1, 1), # Cart Bear | Medal of Honor
    (2600415, 2870296, 100, 1, 1), # Cart Bear | Cart Bear Familiar
    (8220037, 4021031, 50, 1, 1), # Elite Monsters | Twisted Time
    (8220037, 4021034, 100, 1, 1), # Elite Monsters | Fine Spell Essence
    (8220037, 2434021, 100, 1, 1), # Elite Monsters | Medal of Honor
    (8220037, 4021032, 7500, 1, 1), # Elite Monsters | Mana Crystal
    (8220037, 2590004, 100, 1, 1), # Elite Monsters | Prototype Soul Enchanter
    (8220037, 4021033, 100, 1, 1), # Elite Monsters | Basic Spell Essence
    (8220037, 2000036, 100, 1, 1), # Elite Monsters | Reindeer Milk
    (8220037, 2001554, 100, 1, 1), # Elite Monsters | Sunset Dew
    (8220037, 4021035, 100, 1, 1), # Elite Monsters | Grand Spell Essence
    (2600414, 4034074, 7500, 1, 1), # Big Spider | Bedrock Fragment
    (2600414, 2870295, 100, 1, 1), # Big Spider | Big Spider Familiar
    (2600414, 4000610, 7500, 1, 1), # Big Spider | Big Spider Antenna
    (2600414, 2434021, 100, 1, 1), # Big Spider | Medal of Honor
    (2600413, 4000609, 7500, 1, 1), # Racoco | Racoco's Shovel
    (2600413, 2870294, 100, 1, 1), # Racoco | Racoco Familiar
    (2600413, 2434021, 100, 1, 1), # Racoco | Medal of Honor
    (2600412, 4009039, 7500, 1, 1), # Big Rock Face | Boulder Shield
    (2600412, 2434021, 100, 1, 1), # Big Rock Face | Medal of Honor
    (2600411, 2000002, 100, 1, 1), # Big Boulder Muncher | White Potion
    (2600411, 2000006, 100, 1, 1), # Big Boulder Muncher | Mana Elixir
    (2600411, 4000607, 7500, 1, 1), # Big Boulder Muncher | Fragmented ore
    (2600411, 2382119, 100, 1, 1), # Big Boulder Muncher | Big Boulder Muncher Card
    (2600411, 2434021, 100, 1, 1), # Big Boulder Muncher | Medal of Honor
    (2600410, 2434021, 100, 1, 1), # Baby Boulder Muncher | Medal of Honor
    (2600410, 4000606, 7500, 1, 1), # Baby Boulder Muncher | Boulder Fragment
    (2600410, 2000006, 100, 1, 1), # Baby Boulder Muncher | Mana Elixir
    (2600410, 2382115, 100, 1, 1), # Baby Boulder Muncher | Baby Boulder Muncher Card
    (2600410, 2000002, 100, 1, 1), # Baby Boulder Muncher | White Potion
    (2600409, 2000002, 100, 1, 1), # Safety First | White Potion
    (2600409, 4000605, 7500, 1, 1), # Safety First | Safety First Sign
    (2600409, 2434021, 100, 1, 1), # Safety First | Medal of Honor
    (2600409, 2000006, 100, 1, 1), # Safety First | Mana Elixir
    (2600409, 2382114, 100, 1, 1), # Safety First | Safety First Card
    (2600408, 2000003, 100, 1, 1), # Serpent | Blue Potion
    (2600408, 4033122, 7500, 1, 1), # Serpent | Snake Leather Bag
    (2600408, 2434021, 100, 1, 1), # Serpent | Medal of Honor
    (2600408, 2870018, 100, 1, 1), # Serpent | Serpent Familiar
    (2600408, 4000600, 7500, 1, 1), # Serpent | Serpent Tail
    (2600408, 2000002, 100, 1, 1), # Serpent | White Potion
    (5100109, 4021035, 100, 1, 1), # Blin | Grand Spell Essence
    (5100109, 2434021, 100, 1, 1), # Blin | Medal of Honor
    (5100109, 4021032, 7500, 1, 1), # Blin | Mana Crystal
    (5100109, 4021033, 100, 1, 1), # Blin | Basic Spell Essence
    (5100109, 4021034, 100, 1, 1), # Blin | Fine Spell Essence
    (5100109, 2000036, 100, 1, 1), # Blin | Reindeer Milk
    (5100109, 4021031, 50, 1, 1), # Blin | Twisted Time
    (5100109, 2590004, 100, 1, 1), # Blin | Prototype Soul Enchanter
    (5100109, 2001554, 100, 1, 1), # Blin | Sunset Dew
    (8810118, 2435367, 3000, 1, 1), # Chaos Horntail | Chaos Horntail Monsterbloom
    (8810118, 4001094, 7500, 1, 1), # Chaos Horntail | Nine Spirit's Egg
    (5100108, 2434021, 100, 1, 1), # Yellow King Goblin | Medal of Honor
    (5100108, 2000036, 100, 1, 1), # Yellow King Goblin | Reindeer Milk
    (5100108, 2590004, 100, 1, 1), # Yellow King Goblin | Prototype Soul Enchanter
    (5100108, 4021033, 100, 1, 1), # Yellow King Goblin | Basic Spell Essence
    (5100108, 2001554, 100, 1, 1), # Yellow King Goblin | Sunset Dew
    (5100108, 2870286, 100, 1, 1), # Yellow King Goblin | Yellow King Goblin Familiar
    (5100108, 4021034, 100, 1, 1), # Yellow King Goblin | Fine Spell Essence
    (5100108, 4021035, 100, 1, 1), # Yellow King Goblin | Grand Spell Essence
    (5100108, 4021031, 50, 1, 1), # Yellow King Goblin | Twisted Time
    (5100108, 4021032, 7500, 1, 1), # Yellow King Goblin | Mana Crystal
    (5100104, 2001554, 100, 1, 1), # Three-Tailed Fox | Sunset Dew
    (5100104, 4021033, 100, 1, 1), # Three-Tailed Fox | Basic Spell Essence
    (5100104, 4021034, 100, 1, 1), # Three-Tailed Fox | Fine Spell Essence
    (5100104, 2434021, 100, 1, 1), # Three-Tailed Fox | Medal of Honor
    (5100104, 2590004, 100, 1, 1), # Three-Tailed Fox | Prototype Soul Enchanter
    (5100104, 4021032, 7500, 1, 1), # Three-Tailed Fox | Mana Crystal
    (5100104, 2000036, 100, 1, 1), # Three-Tailed Fox | Reindeer Milk
    (5100104, 4021031, 50, 1, 1), # Three-Tailed Fox | Twisted Time
    (5100104, 4021035, 100, 1, 1), # Three-Tailed Fox | Grand Spell Essence
    (5100107, 4021035, 100, 1, 1), # Green King Goblin | Grand Spell Essence
    (5100107, 2001554, 100, 1, 1), # Green King Goblin | Sunset Dew
    (5100107, 2000036, 100, 1, 1), # Green King Goblin | Reindeer Milk
    (5100107, 2590004, 100, 1, 1), # Green King Goblin | Prototype Soul Enchanter
    (5100107, 4021031, 50, 1, 1), # Green King Goblin | Twisted Time
    (5100107, 2434021, 100, 1, 1), # Green King Goblin | Medal of Honor
    (5100107, 2870288, 100, 1, 1), # Green King Goblin | Green King Goblin Familiar
    (5100107, 4021032, 7500, 1, 1), # Green King Goblin | Mana Crystal
    (5100107, 4021033, 100, 1, 1), # Green King Goblin | Basic Spell Essence
    (5100107, 4021034, 100, 1, 1), # Green King Goblin | Fine Spell Essence
    (5100106, 4021031, 50, 1, 1), # Blue King Goblin | Twisted Time
    (5100106, 4021032, 7500, 1, 1), # Blue King Goblin | Mana Crystal
    (5100106, 2001554, 100, 1, 1), # Blue King Goblin | Sunset Dew
    (5100106, 4021034, 100, 1, 1), # Blue King Goblin | Fine Spell Essence
    (5100106, 2434021, 100, 1, 1), # Blue King Goblin | Medal of Honor
    (5100106, 2590004, 100, 1, 1), # Blue King Goblin | Prototype Soul Enchanter
    (5100106, 4021033, 100, 1, 1), # Blue King Goblin | Basic Spell Essence
    (5100106, 2000036, 100, 1, 1), # Blue King Goblin | Reindeer Milk
    (5100106, 2870287, 100, 1, 1), # Blue King Goblin | Blue King Goblin Familiar
    (5100106, 4021035, 100, 1, 1), # Blue King Goblin | Grand Spell Essence
    (2600503, 4021034, 100, 1, 1), # Triple Rumo | Fine Spell Essence
    (2600503, 4021035, 100, 1, 1), # Triple Rumo | Grand Spell Essence
    (2600503, 4021033, 100, 1, 1), # Triple Rumo | Basic Spell Essence
    (2600503, 2870074, 100, 1, 1), # Triple Rumo | Triple Rumo Familiar
    (2600503, 4034481, 7500, 1, 1), # Triple Rumo | Flask
    (2600503, 4021032, 7500, 1, 1), # Triple Rumo | Mana Crystal
    (3220001, 2044001, 100, 1, 1), # Deo | Scroll for Two-handed Sword for ATT 60%
    (3220001, 2048004, 100, 1, 1), # Deo | Scroll for Pet Equip. for Jump 60%
    (3220001, 2045301, 100, 1, 1), # Deo | Scroll for Hand Cannon for ATT 60%
    (3220001, 2000001, 100, 1, 1), # Deo | Orange Potion
    (3220001, 2000003, 100, 1, 1), # Deo | Blue Potion
    (3220001, 4010002, 7500, 1, 1), # Deo | Mithril Ore
    (3220001, 2040927, 100, 1, 1), # Deo | Scroll for Shield for HP 60%
    (3220001, 2040504, 100, 1, 1), # Deo | Scroll for Overall Armor for DEF 60%
    (3220001, 4020003, 7500, 1, 1), # Deo | Emerald Ore
    (3220001, 2040801, 100, 1, 1), # Deo | Scroll for Gloves for DEX 60%
    (3220001, 4000331, 7500, 1, 1), # Deo | Cactus Flower
    (3220001, 2044201, 100, 1, 1), # Deo | Scroll for Two-handed BW for ATT 60%
    (3220001, 2043701, 100, 1, 1), # Deo | Scroll for Wand for Magic Attack 60%
    (3220001, 4010007, 7500, 1, 1), # Deo | Lidium Ore
    (3220001, 2041007, 100, 1, 1), # Deo | Scroll for Cape for HP 60%
    (3220001, 2044501, 100, 1, 1), # Deo | Scroll for Bow for ATT 60%
    (3220001, 2044601, 100, 1, 1), # Deo | Scroll for Crossbow for ATT 60%
    (3220001, 2040824, 100, 1, 1), # Deo | Scroll for Gloves for HP 60%
    (3220001, 2045201, 100, 1, 1), # Deo | Scroll for Dual Bowgun ATT 60%
    (3220001, 2048000, 100, 1, 1), # Deo | Scroll for Pet Equip. for Speed 100%
    (3220001, 2001527, 100, 1, 1), # Deo | Unagi
    (3220001, 2040501, 100, 1, 1), # Deo | Scroll for Overall Armor for DEX 60%
    (3220001, 2043801, 100, 1, 1), # Deo | Scroll for Staff for Magic Attack 60%
    (3220001, 2040924, 100, 1, 1), # Deo | Scroll for Shield for LUK 60%
    (3220001, 2044101, 100, 1, 1), # Deo | Scroll for Two-handed Axe for ATT 60%
    (3220001, 2043201, 100, 1, 1), # Deo | Scroll for One-Handed BW for ATT 60%
    (3220001, 2870091, 100, 1, 1), # Deo | Deo Familiar
    (3220001, 4000330, 7500, 1, 1), # Deo | Cactus Thorn
    (3220001, 4000329, 7500, 1, 1), # Deo | Cactus Stem
    (2600502, 4021032, 7500, 1, 1), # Rumo | Mana Crystal
    (2600502, 4021033, 100, 1, 1), # Rumo | Basic Spell Essence
    (2600502, 2870073, 100, 1, 1), # Rumo | Rumo Familiar
    (2600502, 4034481, 7500, 1, 1), # Rumo | Flask
    (2600502, 4021034, 100, 1, 1), # Rumo | Fine Spell Essence
    (2600502, 4021035, 100, 1, 1), # Rumo | Grand Spell Essence
    (3220000, 4000003, 7500, 1, 1), # Stumpy | Tree Branch
    (3220000, 4000195, 7500, 1, 1), # Stumpy | Seedling
    (3220000, 4000005, 7500, 1, 1), # Stumpy | Leaf
    (3220000, 2870090, 100, 1, 1), # Stumpy | Stumpy Familiar
    (3220000, 4000018, 7500, 1, 1), # Stumpy | Firewood
    (2600501, 4021034, 100, 1, 1), # Megran | Fine Spell Essence
    (2600501, 4021035, 100, 1, 1), # Megran | Grand Spell Essence
    (2600501, 4009061, 7500, 1, 1), # Megran | Red Megran Fruit
    (2600501, 4021032, 7500, 1, 1), # Megran | Mana Crystal
    (2600501, 4021033, 100, 1, 1), # Megran | Basic Spell Essence
    (2600500, 2001002, 100, 1, 1), # Cube Slime | Very Special Sundae
    (2600500, 4021032, 7500, 1, 1), # Cube Slime | Mana Crystal
    (2600500, 4021033, 100, 1, 1), # Cube Slime | Basic Spell Essence
    (2600500, 2870071, 100, 1, 1), # Cube Slime | Cube Slime Familiar
    (2600500, 4021035, 100, 1, 1), # Cube Slime | Grand Spell Essence
    (2600500, 2590004, 100, 1, 1), # Cube Slime | Prototype Soul Enchanter
    (2600500, 4021034, 100, 1, 1), # Cube Slime | Fine Spell Essence
    (2600500, 2001001, 100, 1, 1), # Cube Slime | Ice Cream Pop
    (2600500, 2434021, 100, 1, 1), # Cube Slime | Medal of Honor
    (2600500, 4000353, 7500, 1, 1), # Cube Slime | Gelatin
    (6230602, 2434021, 100, 1, 1), # Skeleton Officer | Medal of Honor
    (6230602, 2870258, 100, 1, 1), # Skeleton Officer | Skeleton Officer Familiar
    (6230602, 4000207, 7500, 1, 1), # Skeleton Officer | Pelvic Bone
    (2600511, 2870271, 100, 1, 1), # Homunculus | Homunculus Familiar
    (2600511, 4021034, 100, 1, 1), # Homunculus | Fine Spell Essence
    (2600511, 2001001, 100, 1, 1), # Homunculus | Ice Cream Pop
    (2600511, 4021031, 50, 1, 1), # Homunculus | Twisted Time
    (2600511, 4000361, 7500, 1, 1), # Homunculus | May Mist
    (2600511, 2590004, 100, 1, 1), # Homunculus | Prototype Soul Enchanter
    (2600511, 2434021, 100, 1, 1), # Homunculus | Medal of Honor
    (2600511, 4021032, 7500, 1, 1), # Homunculus | Mana Crystal
    (2600511, 2001002, 100, 1, 1), # Homunculus | Very Special Sundae
    (2600511, 4021035, 100, 1, 1), # Homunculus | Grand Spell Essence
    (2600511, 4021033, 100, 1, 1), # Homunculus | Basic Spell Essence
    (2600510, 2001002, 100, 1, 1), # Homun | Very Special Sundae
    (2600510, 4021035, 100, 1, 1), # Homun | Grand Spell Essence
    (2600510, 4021034, 100, 1, 1), # Homun | Fine Spell Essence
    (2600510, 2870233, 100, 1, 1), # Homun | Homun Familiar
    (2600510, 2434021, 100, 1, 1), # Homun | Medal of Honor
    (2600510, 4021032, 7500, 1, 1), # Homun | Mana Crystal
    (2600510, 4021033, 100, 1, 1), # Homun | Basic Spell Essence
    (2600510, 4034482, 7500, 1, 1), # Homun | Beaker
    (2600510, 2590004, 100, 1, 1), # Homun | Prototype Soul Enchanter
    (2600510, 2001001, 100, 1, 1), # Homun | Ice Cream Pop
    (2600509, 4021032, 7500, 1, 1), # Security Camera | Mana Crystal
    (2600509, 4021035, 100, 1, 1), # Security Camera | Grand Spell Essence
    (2600509, 4000411, 7500, 1, 1), # Security Camera | Blacklist
    (2600509, 4021034, 100, 1, 1), # Security Camera | Fine Spell Essence
    (2600509, 4021033, 100, 1, 1), # Security Camera | Basic Spell Essence
    (2600508, 4021033, 100, 1, 1), # Reinforced Mithril Mutae | Basic Spell Essence
    (2600508, 2590004, 100, 1, 1), # Reinforced Mithril Mutae | Prototype Soul Enchanter
    (2600508, 2001002, 100, 1, 1), # Reinforced Mithril Mutae | Very Special Sundae
    (2600508, 4021035, 100, 1, 1), # Reinforced Mithril Mutae | Grand Spell Essence
    (2600508, 4021034, 100, 1, 1), # Reinforced Mithril Mutae | Fine Spell Essence
    (2600508, 2870188, 100, 1, 1), # Reinforced Mithril Mutae | Reinforced Mithril Mutae Familiar
    (2600508, 2001001, 100, 1, 1), # Reinforced Mithril Mutae | Ice Cream Pop
    (2600508, 4000360, 7500, 1, 1), # Reinforced Mithril Mutae | Hardened Piece of Mithril
    (2600508, 4021032, 7500, 1, 1), # Reinforced Mithril Mutae | Mana Crystal
    (2600508, 2434021, 100, 1, 1), # Reinforced Mithril Mutae | Medal of Honor
    (2600507, 4021033, 100, 1, 1), # Mithril Mutae | Basic Spell Essence
    (2600507, 2001002, 100, 1, 1), # Mithril Mutae | Very Special Sundae
    (2600507, 2434021, 100, 1, 1), # Mithril Mutae | Medal of Honor
    (2600507, 4021032, 7500, 1, 1), # Mithril Mutae | Mana Crystal
    (2600507, 4021035, 100, 1, 1), # Mithril Mutae | Grand Spell Essence
    (2600507, 4000359, 7500, 1, 1), # Mithril Mutae | Piece of Mithril
    (2600507, 2001001, 100, 1, 1), # Mithril Mutae | Ice Cream Pop
    (2600507, 2870126, 100, 1, 1), # Mithril Mutae | Mithril mutae Familiar
    (2600507, 4021034, 100, 1, 1), # Mithril Mutae | Fine Spell Essence
    (2600507, 2590004, 100, 1, 1), # Mithril Mutae | Prototype Soul Enchanter
    (2600506, 4021032, 7500, 1, 1), # Reinforced Iron Mutae | Mana Crystal
    (2600506, 2590004, 100, 1, 1), # Reinforced Iron Mutae | Prototype Soul Enchanter
    (2600506, 4000358, 7500, 1, 1), # Reinforced Iron Mutae | Hardened Piece of Steel
    (2600506, 4021034, 100, 1, 1), # Reinforced Iron Mutae | Fine Spell Essence
    (2600506, 4021035, 100, 1, 1), # Reinforced Iron Mutae | Grand Spell Essence
    (2600506, 2434021, 100, 1, 1), # Reinforced Iron Mutae | Medal of Honor
    (2600506, 2001002, 100, 1, 1), # Reinforced Iron Mutae | Very Special Sundae
    (2600506, 2001001, 100, 1, 1), # Reinforced Iron Mutae | Ice Cream Pop
    (2600506, 4021033, 100, 1, 1), # Reinforced Iron Mutae | Basic Spell Essence
    (2600506, 2870125, 100, 1, 1), # Reinforced Iron Mutae | Reinforced Iron Mutae Familiar
    (2600504, 4021032, 7500, 1, 1), # Rurumo | Mana Crystal
    (2600504, 4021034, 100, 1, 1), # Rurumo | Fine Spell Essence
    (2600504, 4021033, 100, 1, 1), # Rurumo | Basic Spell Essence
    (2600504, 4021035, 100, 1, 1), # Rurumo | Grand Spell Essence
    (2600504, 4034505, 7500, 1, 1), # Rurumo | Purple Liquid
    (8210013, 2431656, 3000, 1, 1), # Prison Guard Ani | Prison Guard Ani's Soul Shard
    (8210005, 4032838, 7500, 1, 1), # Castle Golem | Alliance Badge
    (8210005, 4021031, 50, 1, 1), # Castle Golem | Twisted Time
    (8210005, 2001528, 100, 1, 1), # Castle Golem | Melting Cheese
    (8210005, 4021033, 100, 1, 1), # Castle Golem | Basic Spell Essence
    (8210005, 2512031, 100, 1, 1), # Castle Golem | Luck Potion X Recipe
    (8210005, 2000037, 100, 1, 1), # Castle Golem | Sunrise Dew
    (8210005, 4033113, 7500, 1, 1), # Castle Golem | Smoldering Black Goo
    (8210005, 2434021, 100, 1, 1), # Castle Golem | Medal of Honor
    (8210005, 4021034, 100, 1, 1), # Castle Golem | Fine Spell Essence
    (8210005, 2512007, 100, 1, 1), # Castle Golem | Strength Potion X Recipe
    (8210005, 2512176, 100, 1, 1), # Castle Golem | Advanced Dexterity Pill VIII Recipe
    (8210005, 2512275, 100, 1, 1), # Castle Golem | Superior Fortitude Potion Recipe
    (8210005, 2590004, 100, 1, 1), # Castle Golem | Prototype Soul Enchanter
    (8210005, 4032835, 7500, 1, 1), # Castle Golem | Dirty Magic Scroll of the Knight
    (8210005, 2512170, 100, 1, 1), # Castle Golem | Advanced Dexterity Pill II Recipe
    (8210005, 2512278, 100, 1, 1), # Castle Golem | Superior Insight Potion Recipe
    (8210005, 4021032, 7500, 1, 1), # Castle Golem | Mana Crystal
    (8210005, 4000629, 7500, 1, 1), # Castle Golem | Castle Golem Brick
    (8210005, 4000630, 7500, 1, 1), # Castle Golem | Purification Totem
    (8210005, 2511049, 100, 1, 1), # Castle Golem | Purple Owl Pendant Recipe
    (8210005, 2870448, 100, 1, 1), # Castle Golem | Castle Golem Familiar
    (8210005, 4021035, 100, 1, 1), # Castle Golem | Grand Spell Essence
    (8210004, 2512266, 100, 1, 1), # Grey Vulture | Superior Hero Potion Recipe
    (8210004, 2512275, 100, 1, 1), # Grey Vulture | Superior Fortitude Potion Recipe
    (8210004, 4000627, 7500, 1, 1), # Grey Vulture | Bearwolf's Morning Star
    (8210004, 4021032, 7500, 1, 1), # Grey Vulture | Mana Crystal
    (8210004, 2870447, 100, 1, 1), # Grey Vulture | Grey Vulture Familiar
    (8210004, 4021035, 100, 1, 1), # Grey Vulture | Grand Spell Essence
    (8210004, 2512284, 100, 1, 1), # Grey Vulture | Exceptional Intelligence Boost Potion Recipe
    (8210004, 4021031, 50, 1, 1), # Grey Vulture | Twisted Time
    (8210004, 4021033, 100, 1, 1), # Grey Vulture | Basic Spell Essence
    (8210004, 2434021, 100, 1, 1), # Grey Vulture | Medal of Honor
    (8210004, 2001528, 100, 1, 1), # Grey Vulture | Melting Cheese
    (8210004, 4033113, 7500, 1, 1), # Grey Vulture | Smoldering Black Goo
    (8210004, 2049401, 100, 1, 1), # Grey Vulture | Potential Scroll
    (8210004, 2000037, 100, 1, 1), # Grey Vulture | Sunrise Dew
    (8210004, 4021034, 100, 1, 1), # Grey Vulture | Fine Spell Essence
    (8210004, 2590004, 100, 1, 1), # Grey Vulture | Prototype Soul Enchanter
    (8210004, 4000630, 7500, 1, 1), # Grey Vulture | Purification Totem
    (8210004, 4000628, 7500, 1, 1), # Grey Vulture | Vulture's Shoulder Accessory
    (8210001, 4033113, 7500, 1, 1), # Reindeer | Smoldering Black Goo
    (8210001, 2512176, 100, 1, 1), # Reindeer | Advanced Dexterity Pill VIII Recipe
    (8210001, 4021034, 100, 1, 1), # Reindeer | Fine Spell Essence
    (8210001, 2512117, 100, 1, 1), # Reindeer | Advanced Intelligence Potion IX Recipe
    (8210001, 2511049, 100, 1, 1), # Reindeer | Purple Owl Pendant Recipe
    (8210001, 2512269, 100, 1, 1), # Reindeer | Superior Blessing Potion Recipe
    (8210001, 2512284, 100, 1, 1), # Reindeer | Exceptional Intelligence Boost Potion Recipe
    (8210001, 4021035, 100, 1, 1), # Reindeer | Grand Spell Essence
    (8210001, 4021033, 100, 1, 1), # Reindeer | Basic Spell Essence
    (8210001, 2048004, 100, 1, 1), # Reindeer | Scroll for Pet Equip. for Jump 60%
    (8210001, 2512248, 100, 1, 1), # Reindeer | Advanced Attack Pill V Recipe
    (8210001, 2590004, 100, 1, 1), # Reindeer | Prototype Soul Enchanter
    (8210001, 2070005, 100, 1, 1), # Reindeer | Steely Throwing-Knives
    (8210001, 2048000, 100, 1, 1), # Reindeer | Scroll for Pet Equip. for Speed 100%
    (8210001, 4021032, 7500, 1, 1), # Reindeer | Mana Crystal
    (8210001, 4000626, 7500, 1, 1), # Reindeer | Reindeer's Spear
    (8210001, 2512283, 100, 1, 1), # Reindeer | Superior Intelligence Boost Potion Recipe
    (8210001, 2070003, 100, 1, 1), # Reindeer | Kumbi Throwing-Stars
    (8210001, 2001528, 100, 1, 1), # Reindeer | Melting Cheese
    (8210001, 2870444, 100, 1, 1), # Reindeer | Reindeer Familiar
    (8210001, 2049401, 100, 1, 1), # Reindeer | Potential Scroll
    (8210001, 2512245, 100, 1, 1), # Reindeer | Advanced Attack Pill II Recipe
    (8210001, 4021031, 50, 1, 1), # Reindeer | Twisted Time
    (8210001, 4000630, 7500, 1, 1), # Reindeer | Purification Totem
    (8210001, 2512273, 100, 1, 1), # Reindeer | Superior Concentration Potion Recipe
    (8210001, 2000037, 100, 1, 1), # Reindeer | Sunrise Dew
    (8210001, 2434021, 100, 1, 1), # Reindeer | Medal of Honor
    (8210001, 2512133, 100, 1, 1), # Reindeer | Advanced Attack Potion V Recipe
    (8210000, 2070005, 100, 1, 1), # Crocky the Gatekeeper | Steely Throwing-Knives
    (8210000, 4033113, 7500, 1, 1), # Crocky the Gatekeeper | Smoldering Black Goo
    (8210000, 4021035, 100, 1, 1), # Crocky the Gatekeeper | Grand Spell Essence
    (8210000, 4021033, 100, 1, 1), # Crocky the Gatekeeper | Basic Spell Essence
    (8210000, 4000630, 7500, 1, 1), # Crocky the Gatekeeper | Purification Totem
    (8210000, 4021032, 7500, 1, 1), # Crocky the Gatekeeper | Mana Crystal
    (8210000, 2434021, 100, 1, 1), # Crocky the Gatekeeper | Medal of Honor
    (8210000, 2870443, 100, 1, 1), # Crocky the Gatekeeper | Crocky the Gatekeeper Familiar
    (8210000, 4021034, 100, 1, 1), # Crocky the Gatekeeper | Fine Spell Essence
    (8210000, 4021031, 50, 1, 1), # Crocky the Gatekeeper | Twisted Time
    (8210000, 4000625, 7500, 1, 1), # Crocky the Gatekeeper | Crocky's Helmet
    (8210000, 2001528, 100, 1, 1), # Crocky the Gatekeeper | Melting Cheese
    (8210000, 2000037, 100, 1, 1), # Crocky the Gatekeeper | Sunrise Dew
    (8210003, 4000627, 7500, 1, 1), # Bearwolf | Bearwolf's Morning Star
    (8210003, 4021032, 7500, 1, 1), # Bearwolf | Mana Crystal
    (8210003, 4021031, 50, 1, 1), # Bearwolf | Twisted Time
    (8210003, 2434021, 100, 1, 1), # Bearwolf | Medal of Honor
    (8210003, 2001528, 100, 1, 1), # Bearwolf | Melting Cheese
    (8210003, 4021033, 100, 1, 1), # Bearwolf | Basic Spell Essence
    (8210003, 4033113, 7500, 1, 1), # Bearwolf | Smoldering Black Goo
    (8210003, 4021035, 100, 1, 1), # Bearwolf | Grand Spell Essence
    (8210003, 2000037, 100, 1, 1), # Bearwolf | Sunrise Dew
    (8210003, 2870446, 100, 1, 1), # Bearwolf | Bearwolf Familiar
    (8210003, 2070010, 100, 1, 1), # Bearwolf | Icicle
    (8210003, 2512270, 100, 1, 1), # Bearwolf | Exceptional Blessing Potion Recipe
    (8210003, 2590004, 100, 1, 1), # Bearwolf | Prototype Soul Enchanter
    (8210003, 4000630, 7500, 1, 1), # Bearwolf | Purification Totem
    (8210003, 2512006, 100, 1, 1), # Bearwolf | Strength Potion IX Recipe
    (8210003, 2048004, 100, 1, 1), # Bearwolf | Scroll for Pet Equip. for Jump 60%
    (8210003, 2049401, 100, 1, 1), # Bearwolf | Potential Scroll
    (8210003, 2330004, 100, 1, 1), # Bearwolf | Shiny Bullet
    (8210003, 2512279, 100, 1, 1), # Bearwolf | Exceptional Insight Potion Recipe
    (8210003, 4021034, 100, 1, 1), # Bearwolf | Fine Spell Essence
    (8210002, 2590004, 100, 1, 1), # Blood Reindeer | Prototype Soul Enchanter
    (8210002, 4021033, 100, 1, 1), # Blood Reindeer | Basic Spell Essence
    (8210002, 2870445, 100, 1, 1), # Blood Reindeer | Blood Reindeer Familiar
    (8210002, 4033113, 7500, 1, 1), # Blood Reindeer | Smoldering Black Goo
    (8210002, 4000626, 7500, 1, 1), # Blood Reindeer | Reindeer's Spear
    (8210002, 4021032, 7500, 1, 1), # Blood Reindeer | Mana Crystal
    (8210002, 4021035, 100, 1, 1), # Blood Reindeer | Grand Spell Essence
    (8210002, 2000037, 100, 1, 1), # Blood Reindeer | Sunrise Dew
    (8210002, 4021031, 50, 1, 1), # Blood Reindeer | Twisted Time
    (8210002, 2434021, 100, 1, 1), # Blood Reindeer | Medal of Honor
    (8210002, 4021034, 100, 1, 1), # Blood Reindeer | Fine Spell Essence
    (8210002, 2001528, 100, 1, 1), # Blood Reindeer | Melting Cheese
    (8210002, 4000630, 7500, 1, 1), # Blood Reindeer | Purification Totem
    (8211004, 4021033, 100, 1, 1), # King Castle Golem | Basic Spell Essence
    (8211004, 4021035, 100, 1, 1), # King Castle Golem | Grand Spell Essence
    (8211004, 4000630, 7500, 1, 1), # King Castle Golem | Purification Totem
    (8211004, 4021032, 7500, 1, 1), # King Castle Golem | Mana Crystal
    (8211004, 4009173, 7500, 1, 1), # King Castle Golem | Suspicious Lime Powder
    (8211004, 4021031, 50, 1, 1), # King Castle Golem | Twisted Time
    (8211004, 4021034, 100, 1, 1), # King Castle Golem | Fine Spell Essence
    (8211004, 4033113, 7500, 1, 1), # King Castle Golem | Smoldering Black Goo
    (8211001, 2001528, 100, 1, 1), # Keymaster Rousseau | Melting Cheese
    (8211001, 2590004, 100, 1, 1), # Keymaster Rousseau | Prototype Soul Enchanter
    (8211001, 4021031, 50, 1, 1), # Keymaster Rousseau | Twisted Time
    (8211001, 4009170, 7500, 1, 1), # Keymaster Rousseau | Loose Chain
    (8211001, 4021035, 100, 1, 1), # Keymaster Rousseau | Grand Spell Essence
    (8211001, 4030032, 7500, 1, 1), # Keymaster Rousseau | Rousseau's Key
    (8211001, 4021032, 7500, 1, 1), # Keymaster Rousseau | Mana Crystal
    (8211001, 2000037, 100, 1, 1), # Keymaster Rousseau | Sunrise Dew
    (8211001, 4033113, 7500, 1, 1), # Keymaster Rousseau | Smoldering Black Goo
    (8211001, 4021034, 100, 1, 1), # Keymaster Rousseau | Fine Spell Essence
    (8211001, 2434021, 100, 1, 1), # Keymaster Rousseau | Medal of Honor
    (8211001, 4021033, 100, 1, 1), # Keymaster Rousseau | Basic Spell Essence
    (8211001, 4000630, 7500, 1, 1), # Keymaster Rousseau | Purification Totem
    (8211000, 4000630, 7500, 1, 1), # Frozen Rose | Purification Totem
    (8211000, 2434021, 100, 1, 1), # Frozen Rose | Medal of Honor
    (8211000, 2000037, 100, 1, 1), # Frozen Rose | Sunrise Dew
    (8211000, 4033113, 7500, 1, 1), # Frozen Rose | Smoldering Black Goo
    (8211000, 4021033, 100, 1, 1), # Frozen Rose | Basic Spell Essence
    (8211000, 4021035, 100, 1, 1), # Frozen Rose | Grand Spell Essence
    (8211000, 4009169, 7500, 1, 1), # Frozen Rose | Frozen Rose Petal
    (8211000, 4021031, 50, 1, 1), # Frozen Rose | Twisted Time
    (8211000, 4021032, 7500, 1, 1), # Frozen Rose | Mana Crystal
    (8211000, 4021034, 100, 1, 1), # Frozen Rose | Fine Spell Essence
    (8211000, 2590004, 100, 1, 1), # Frozen Rose | Prototype Soul Enchanter
    (8211000, 4030036, 7500, 1, 1), # Frozen Rose | Rose Seed
    (8211000, 2001528, 100, 1, 1), # Frozen Rose | Melting Cheese
    (8211003, 4009172, 7500, 1, 1), # Golden Bee | Shiny Flower Powder
    (8211003, 4021033, 100, 1, 1), # Golden Bee | Basic Spell Essence
    (8211003, 4021031, 50, 1, 1), # Golden Bee | Twisted Time
    (8211003, 4021032, 7500, 1, 1), # Golden Bee | Mana Crystal
    (8211003, 4021034, 100, 1, 1), # Golden Bee | Fine Spell Essence
    (8211003, 4000630, 7500, 1, 1), # Golden Bee | Purification Totem
    (8211003, 4033113, 7500, 1, 1), # Golden Bee | Smoldering Black Goo
    (8211003, 4021035, 100, 1, 1), # Golden Bee | Grand Spell Essence
    (8211002, 4033113, 7500, 1, 1), # Garden Golem | Smoldering Black Goo
    (8211002, 4009171, 7500, 1, 1), # Garden Golem | Dried Rose Vine
    (8211002, 2434021, 100, 1, 1), # Garden Golem | Medal of Honor
    (8211002, 4021032, 7500, 1, 1), # Garden Golem | Mana Crystal
    (8211002, 4021031, 50, 1, 1), # Garden Golem | Twisted Time
    (8211002, 4021033, 100, 1, 1), # Garden Golem | Basic Spell Essence
    (8211002, 4021034, 100, 1, 1), # Garden Golem | Fine Spell Essence
    (8211002, 4021035, 100, 1, 1), # Garden Golem | Grand Spell Essence
    (8211002, 4000630, 7500, 1, 1), # Garden Golem | Purification Totem
    (8211002, 2001528, 100, 1, 1), # Garden Golem | Melting Cheese
    (8211002, 2590004, 100, 1, 1), # Garden Golem | Prototype Soul Enchanter
    (8211002, 2000037, 100, 1, 1), # Garden Golem | Sunrise Dew
    (2150000, 2043000, 100, 1, 1), # Water Thief Monster | Scroll for One-Handed Sword for ATT 100%
    (2150000, 2061000, 100, 1, 1), # Water Thief Monster | Arrow for Crossbow
    (2150000, 2043700, 100, 1, 1), # Water Thief Monster | Scroll for Wand for Magic Attack 100%
    (2150000, 2044000, 100, 1, 1), # Water Thief Monster | Scroll for Two-handed Sword for ATT 100%
    (2150000, 2000003, 100, 1, 1), # Water Thief Monster | Blue Potion
    (2150000, 4010002, 7500, 1, 1), # Water Thief Monster | Mithril Ore
    (2150000, 2000002, 100, 1, 1), # Water Thief Monster | White Potion
    (2150000, 4020004, 7500, 1, 1), # Water Thief Monster | Opal Ore
    (2150000, 2000003, 100, 1, 1), # Water Thief Monster | Blue Potion
    (2150000, 2000000, 100, 1, 1), # Water Thief Monster | Red Potion
    (2150000, 4000601, 7500, 1, 1), # Water Thief Monster | Water Thief's Bottle
    (2150000, 2000000, 100, 1, 1), # Water Thief Monster | Red Potion
    (2150000, 2000002, 100, 1, 1), # Water Thief Monster | White Potion
    (2150000, 2000006, 100, 1, 1), # Water Thief Monster | Mana Elixir
    (2150000, 4010006, 7500, 1, 1), # Water Thief Monster | Gold Ore
    (2150000, 2043200, 100, 1, 1), # Water Thief Monster | Scroll for One-Handed BW for ATT 100%
    (2150000, 4020002, 7500, 1, 1), # Water Thief Monster | AquaMarine Ore
    (2150000, 2000001, 100, 1, 1), # Water Thief Monster | Orange Potion
    (2150000, 2000001, 100, 1, 1), # Water Thief Monster | Orange Potion
    (2150000, 4000602, 7500, 1, 1), # Water Thief Monster | Dust Ball
    (2150000, 2061000, 100, 1, 1), # Water Thief Monster | Arrow for Crossbow
    (2150000, 2870038, 100, 1, 1), # Water Thief Monster | Water Thief Monster Familiar
    (2150000, 2060000, 100, 1, 1), # Water Thief Monster | Arrow for Bow
    (2150000, 2044600, 100, 1, 1), # Water Thief Monster | Scroll for Crossbow for ATT 100%
    (2150000, 2060000, 100, 1, 1), # Water Thief Monster | Arrow for Bow
    (2150000, 2150001, 100, 1, 1), # Water Thief Monster | ?
    (2150000, 2043800, 100, 1, 1), # Water Thief Monster | Scroll for Staff for Magic Attack 100%
    (3230304, 2870101, 100, 1, 1), # Planey | Planey Familiar
    (3230304, 4000105, 7500, 1, 1), # Planey | Plane Controller
    (2150002, 2060000, 100, 1, 1), # Streetlight | Arrow for Bow
    (2150002, 2870040, 100, 1, 1), # Streetlight | Streetlight Familiar
    (2150002, 4010006, 7500, 1, 1), # Streetlight | Gold Ore
    (2150002, 2040709, 100, 1, 1), # Streetlight | Scroll for Shoes for DEX 100%
    (2150002, 2000000, 100, 1, 1), # Streetlight | Red Potion
    (2150002, 2061000, 100, 1, 1), # Streetlight | Arrow for Crossbow
    (2150002, 2040417, 100, 1, 1), # Streetlight | Scroll for Topwear for STR 100%
    (2150002, 2000006, 100, 1, 1), # Streetlight | Mana Elixir
    (2150002, 2000002, 100, 1, 1), # Streetlight | White Potion
    (2150002, 4010001, 7500, 1, 1), # Streetlight | Steel Ore
    (2150002, 2040926, 100, 1, 1), # Streetlight | Scroll for Shield for HP 100%
    (2150002, 4000603, 7500, 1, 1), # Streetlight | Streetlight Bulb
    (2150002, 2000001, 100, 1, 1), # Streetlight | Orange Potion
    (3230307, 2870104, 100, 1, 1), # Chirppy | Chirppy Familiar
    (3230307, 4000123, 7500, 1, 1), # Chirppy | Worn-Out Goggle
    (2150003, 2870041, 100, 1, 1), # Patrol Robot S | Patrol Robot S Familiar
    (2150003, 4000604, 7500, 1, 1), # Patrol Robot S | Patrol Robot S Memory Chip
    (2150003, 4010001, 7500, 1, 1), # Patrol Robot S | Steel Ore
    (2150003, 4010006, 7500, 1, 1), # Patrol Robot S | Gold Ore
    (2150003, 2000001, 100, 1, 1), # Patrol Robot S | Orange Potion
    (2150003, 2044805, 100, 1, 1), # Patrol Robot S | Scroll for Knuckle for Accuracy 100%
    (2150003, 2040901, 100, 1, 1), # Patrol Robot S | Scroll for Shield for DEF 60%
    (2150003, 2040515, 100, 1, 1), # Patrol Robot S | Scroll for Overall Armor for LUK 100%
    (2150003, 2060000, 100, 1, 1), # Patrol Robot S | Arrow for Bow
    (2150003, 2000000, 100, 1, 1), # Patrol Robot S | Red Potion
    (2150003, 2061000, 100, 1, 1), # Patrol Robot S | Arrow for Crossbow
    (2150003, 2000006, 100, 1, 1), # Patrol Robot S | Mana Elixir
    (2150003, 2000003, 100, 1, 1), # Patrol Robot S | Blue Potion
    (8930000, 4310029, 7500, 1, 1), # Chaos Vellum | Crusader Coins
    (8810214, 4310029, 7500, 1, 1), # Easy Horntail | Crusader Coins
    (8810214, 4001094, 7500, 1, 1), # Easy Horntail | Nine Spirit's Egg
    (8810214, 2433103, 100, 1, 1), # Easy Horntail | Boss Medal of Honor
    (2600518, 4000418, 7500, 1, 1), # Deet and Roi | Useless Mechanical Heart
    (2600518, 4310029, 7500, 1, 1), # Deet and Roi | Crusader Coins
    (2600517, 4000362, 7500, 1, 1), # D. Roy | Broken Mechanical Heart
    (2600517, 2870270, 100, 1, 1), # D. Roy | D. Roy Familiar
    (2600516, 2590004, 100, 1, 1), # Neo Huroid | Prototype Soul Enchanter
    (2600516, 2001001, 100, 1, 1), # Neo Huroid | Ice Cream Pop
    (2600516, 2001002, 100, 1, 1), # Neo Huroid | Very Special Sundae
    (2600516, 2434021, 100, 1, 1), # Neo Huroid | Medal of Honor
    (2600516, 2870190, 100, 1, 1), # Neo Huroid | Neo Huroid Familiar
    (2600515, 4000364, 7500, 1, 1), # Roid | Wires
    (2600515, 2870189, 100, 1, 1), # Roid | Roid Familiar
    (1130100, 2044000, 100, 1, 1), # Axe Stump | Scroll for Two-handed Sword for ATT 100%
    (1130100, 4010005, 7500, 1, 1), # Axe Stump | Orihalcon Ore
    (1130100, 4020004, 7500, 1, 1), # Axe Stump | Opal Ore
    (1130100, 4000018, 7500, 1, 1), # Axe Stump | Firewood
    (1130100, 2040512, 100, 1, 1), # Axe Stump | Scroll for Overall Armor for INT 100%
    (1130100, 2043202, 100, 1, 1), # Axe Stump | Scroll for One-Handed BW for ATT 10%
    (1130100, 2000002, 100, 1, 1), # Axe Stump | White Potion
    (1130100, 2000003, 100, 1, 1), # Axe Stump | Blue Potion
    (1130100, 2048003, 100, 1, 1), # Axe Stump | Scroll for Pet Equip. for Jump 100%
    (1130100, 2870013, 100, 1, 1), # Axe Stump | Axe Stump Familiar
    (2600514, 4000356, 7500, 1, 1), # Chimera | Flask
    (2600514, 4000364, 7500, 1, 1), # Chimera | Wires
    (2600514, 4000365, 7500, 1, 1), # Chimera | Plug
    (2600514, 4310029, 7500, 1, 1), # Chimera | Crusader Coins
    (2600514, 4021032, 7500, 1, 1), # Chimera | Mana Crystal
    (1000041, 4031013, 9000, 1, 1), # ? | Dark Marble
    (2600513, 2386043, 100, 1, 1), # Saitie | Saitie Card
    (2600513, 2001001, 100, 1, 1), # Saitie | Ice Cream Pop
    (2600513, 2001002, 100, 1, 1), # Saitie | Very Special Sundae
    (2600513, 4021033, 100, 1, 1), # Saitie | Basic Spell Essence
    (2600513, 2590004, 100, 1, 1), # Saitie | Prototype Soul Enchanter
    (2600513, 2434021, 100, 1, 1), # Saitie | Medal of Honor
    (2600513, 4021032, 7500, 1, 1), # Saitie | Mana Crystal
    (2600513, 4000363, 7500, 1, 1), # Saitie | Entry Pass
    (2600513, 4021034, 100, 1, 1), # Saitie | Fine Spell Essence
    (2600513, 4021035, 100, 1, 1), # Saitie | Grand Spell Essence
    (2600512, 4021035, 100, 1, 1), # Homunscullo | Grand Spell Essence
    (2600512, 2870310, 100, 1, 1), # Homunscullo | Homunscullo Familiar
    (2600512, 4033637, 7500, 1, 1), # Homunscullo | Homunculus's Blood
    (2600512, 4021032, 7500, 1, 1), # Homunscullo | Mana Crystal
    (2600512, 4021034, 100, 1, 1), # Homunscullo | Fine Spell Essence
    (2600512, 4000355, 7500, 1, 1), # Homunscullo | Homunculus's Sand
    (2600512, 4021033, 100, 1, 1), # Homunscullo | Basic Spell Essence
    (3230301, 2590004, 100, 1, 1), # Jr. Boogie | Prototype Soul Enchanter
    (3230301, 2710002, 100, 1, 1), # Jr. Boogie | Master Craftsman's Cube
    (3230301, 4000067, 7500, 1, 1), # Jr. Boogie | Jr. Boogie Horn
    (3230301, 2000002, 100, 1, 1), # Jr. Boogie | White Potion
    (3230301, 2000006, 100, 1, 1), # Jr. Boogie | Mana Elixir
    (3230301, 2870098, 100, 1, 1), # Jr. Boogie | Jr. Boogie Familiar
    (8170000, 4021031, 50, 1, 1), # [*]Thanatos | Twisted Time
    (8170000, 2001528, 100, 1, 1), # [*]Thanatos | Melting Cheese
    (8170000, 4021033, 100, 1, 1), # [*]Thanatos | Basic Spell Essence
    (8170000, 4021034, 100, 1, 1), # [*]Thanatos | Fine Spell Essence
    (8170000, 2000037, 100, 1, 1), # [*]Thanatos | Sunrise Dew
    (8170000, 2434021, 100, 1, 1), # [*]Thanatos | Medal of Honor
    (8170000, 4021032, 7500, 1, 1), # [*]Thanatos | Mana Crystal
    (8170000, 4034181, 100, 1, 1), # [*]Thanatos | Dispersed Star Fragment
    (8170000, 4021035, 100, 1, 1), # [*]Thanatos | Grand Spell Essence
    (8170000, 4031172, 7500, 1, 1), # [*]Thanatos | Ludibrium Medal
    (8170000, 2870344, 100, 1, 1), # [*]Thanatos | [*]Thanatos Familiar
    (8170000, 2590004, 100, 1, 1), # [*]Thanatos | Prototype Soul Enchanter
    (3230300, 2870098, 100, 1, 1), # Jr. Boogie | Jr. Boogie Familiar
    (3230300, 2710002, 100, 1, 1), # Jr. Boogie | Master Craftsman's Cube
    (3230300, 4000067, 7500, 1, 1), # Jr. Boogie | Jr. Boogie Horn
    (3230300, 2000006, 100, 1, 1), # Jr. Boogie | Mana Elixir
    (3230300, 2000002, 100, 1, 1), # Jr. Boogie | White Potion
    (3230300, 2590004, 100, 1, 1), # Jr. Boogie | Prototype Soul Enchanter
    (3230303, 2870100, 100, 1, 1), # Propelly | Propelly Familiar
    (3230303, 4000104, 7500, 1, 1), # Propelly | Motor
    (5140000, 4032644, 7500, 1, 1), # White Fang | Shammos's Diary Page 7
    (5140000, 4021034, 100, 1, 1), # White Fang | Fine Spell Essence
    (5140000, 4032643, 7500, 1, 1), # White Fang | Shammos's Diary Page 6
    (5140000, 2870211, 100, 1, 1), # White Fang | White Fang Familiar
    (5140000, 4031218, 7500, 1, 1), # White Fang | The Contract of Darkness
    (5140000, 2001001, 100, 1, 1), # White Fang | Ice Cream Pop
    (5140000, 4021035, 100, 1, 1), # White Fang | Grand Spell Essence
    (5140000, 4032642, 7500, 1, 1), # White Fang | Shammos's Diary Page 5
    (5140000, 2001002, 100, 1, 1), # White Fang | Very Special Sundae
    (5140000, 2434021, 100, 1, 1), # White Fang | Medal of Honor
    (5140000, 4021032, 7500, 1, 1), # White Fang | Mana Crystal
    (5140000, 4021033, 100, 1, 1), # White Fang | Basic Spell Essence
    (5140000, 4031054, 7500, 1, 1), # White Fang | Old Piece of Map
    (5140000, 2590004, 100, 1, 1), # White Fang | Prototype Soul Enchanter
    (5140000, 4032645, 7500, 1, 1), # White Fang | Shammos's Diary Page 8
    (5140000, 4000052, 7500, 1, 1), # White Fang | White Fang Tail
    (5140000, 4032646, 7500, 1, 1), # White Fang | Shammos's Diary Page 9
    (3230302, 4031089, 7500, 1, 1), # Bloctopus | Screwdriver
    (3230302, 2870099, 100, 1, 1), # Bloctopus | Bloctopus Familiar
    (3230302, 4000099, 7500, 1, 1), # Bloctopus | Bloctopus Key Chain
    (9100024, 4032603, 7500, 1, 1), # SnowFro the Fruitnificent | Ritaya's Camera
    (8630007, 4021035, 100, 1, 1), # Burning Rage | Grand Spell Essence
    (8630007, 4033930, 7500, 1, 1), # Burning Rage | Burning Weapon Energy
    (8630007, 4009139, 7500, 1, 1), # Burning Rage | Broken Shaft (Red)
    (8630007, 4021033, 100, 1, 1), # Burning Rage | Basic Spell Essence
    (8630007, 2000036, 100, 1, 1), # Burning Rage | Reindeer Milk
    (8630007, 4021031, 50, 1, 1), # Burning Rage | Twisted Time
    (8630007, 4021034, 100, 1, 1), # Burning Rage | Fine Spell Essence
    (8630007, 2431174, 100, 1, 1), # Burning Rage | Medal of Honor
    (8630007, 4021032, 7500, 1, 1), # Burning Rage | Mana Crystal
    (8630007, 4021020, 10, 1, 1), # Burning Rage | Confusion Fragment
    (8630007, 2590004, 100, 1, 1), # Burning Rage | Prototype Soul Enchanter
    (8630007, 2000037, 100, 1, 1), # Burning Rage | Sunrise Dew
    (8630006, 2590004, 100, 1, 1), # Burning Terror | Prototype Soul Enchanter
    (8630006, 4021031, 50, 1, 1), # Burning Terror | Twisted Time
    (8630006, 4021034, 100, 1, 1), # Burning Terror | Fine Spell Essence
    (8630006, 4021032, 7500, 1, 1), # Burning Terror | Mana Crystal
    (8630006, 2000036, 100, 1, 1), # Burning Terror | Reindeer Milk
    (8630006, 2431174, 100, 1, 1), # Burning Terror | Medal of Honor
    (8630006, 4033930, 7500, 1, 1), # Burning Terror | Burning Weapon Energy
    (8630006, 4021035, 100, 1, 1), # Burning Terror | Grand Spell Essence
    (8630006, 4021020, 10, 1, 1), # Burning Terror | Confusion Fragment
    (8630006, 4021033, 100, 1, 1), # Burning Terror | Basic Spell Essence
    (8630006, 2000037, 100, 1, 1), # Burning Terror | Sunrise Dew
    (8630006, 4009138, 7500, 1, 1), # Burning Terror | Broken Hilt (Red)
    (8630005, 2431174, 100, 1, 1), # Burning Solitude | Medal of Honor
    (8630005, 4009137, 7500, 1, 1), # Burning Solitude | Broken Blade (Red)
    (8630005, 2000036, 100, 1, 1), # Burning Solitude | Reindeer Milk
    (8630005, 2000037, 100, 1, 1), # Burning Solitude | Sunrise Dew
    (8630005, 2590004, 100, 1, 1), # Burning Solitude | Prototype Soul Enchanter
    (8630005, 4021020, 10, 1, 1), # Burning Solitude | Confusion Fragment
    (8630005, 4021032, 7500, 1, 1), # Burning Solitude | Mana Crystal
    (8630005, 4021034, 100, 1, 1), # Burning Solitude | Fine Spell Essence
    (8630005, 4033930, 7500, 1, 1), # Burning Solitude | Burning Weapon Energy
    (8630005, 4021035, 100, 1, 1), # Burning Solitude | Grand Spell Essence
    (8630005, 4021033, 100, 1, 1), # Burning Solitude | Basic Spell Essence
    (8630005, 4021031, 50, 1, 1), # Burning Solitude | Twisted Time
    (8630011, 4021035, 100, 1, 1), # Permeating Terror | Grand Spell Essence
    (8630011, 2590004, 100, 1, 1), # Permeating Terror | Prototype Soul Enchanter
    (8630011, 2431174, 100, 1, 1), # Permeating Terror | Medal of Honor
    (8630011, 2000037, 100, 1, 1), # Permeating Terror | Sunrise Dew
    (8630011, 4021033, 100, 1, 1), # Permeating Terror | Basic Spell Essence
    (8630011, 4009143, 7500, 1, 1), # Permeating Terror | Broken Hilt (Black)
    (8630011, 2000036, 100, 1, 1), # Permeating Terror | Reindeer Milk
    (8630011, 4021031, 50, 1, 1), # Permeating Terror | Twisted Time
    (8630011, 4033933, 7500, 1, 1), # Permeating Terror | Permeating Energy
    (8630011, 4021020, 10, 1, 1), # Permeating Terror | Confusion Fragment
    (8630011, 4021032, 7500, 1, 1), # Permeating Terror | Mana Crystal
    (8630011, 4021034, 100, 1, 1), # Permeating Terror | Fine Spell Essence
    (8630010, 2000036, 100, 1, 1), # Permeating Solitude | Reindeer Milk
    (8630010, 2000037, 100, 1, 1), # Permeating Solitude | Sunrise Dew
    (8630010, 4021032, 7500, 1, 1), # Permeating Solitude | Mana Crystal
    (8630010, 4021033, 100, 1, 1), # Permeating Solitude | Basic Spell Essence
    (8630010, 4021031, 50, 1, 1), # Permeating Solitude | Twisted Time
    (8630010, 2431174, 100, 1, 1), # Permeating Solitude | Medal of Honor
    (8630010, 4021020, 10, 1, 1), # Permeating Solitude | Confusion Fragment
    (8630010, 4021034, 100, 1, 1), # Permeating Solitude | Fine Spell Essence
    (8630010, 4033933, 7500, 1, 1), # Permeating Solitude | Permeating Energy
    (8630010, 4021035, 100, 1, 1), # Permeating Solitude | Grand Spell Essence
    (8630010, 2590004, 100, 1, 1), # Permeating Solitude | Prototype Soul Enchanter
    (8630010, 4009142, 7500, 1, 1), # Permeating Solitude | Broken Blade (Black)
    (8630009, 2590004, 100, 1, 1), # Burning Vanity | Prototype Soul Enchanter
    (8630009, 4021020, 10, 1, 1), # Burning Vanity | Confusion Fragment
    (8630009, 4021034, 100, 1, 1), # Burning Vanity | Fine Spell Essence
    (8630009, 4021033, 100, 1, 1), # Burning Vanity | Basic Spell Essence
    (8630009, 2000037, 100, 1, 1), # Burning Vanity | Sunrise Dew
    (8630009, 4009141, 7500, 1, 1), # Burning Vanity | Broken Feather (Red)
    (8630009, 2431174, 100, 1, 1), # Burning Vanity | Medal of Honor
    (8630009, 4033930, 7500, 1, 1), # Burning Vanity | Burning Weapon Energy
    (8630009, 4021031, 50, 1, 1), # Burning Vanity | Twisted Time
    (8630009, 2000036, 100, 1, 1), # Burning Vanity | Reindeer Milk
    (8630009, 4021032, 7500, 1, 1), # Burning Vanity | Mana Crystal
    (8630009, 4021035, 100, 1, 1), # Burning Vanity | Grand Spell Essence
    (8630008, 4021034, 100, 1, 1), # Burning Anxiety | Fine Spell Essence
    (8630008, 4021031, 50, 1, 1), # Burning Anxiety | Twisted Time
    (8630008, 4021035, 100, 1, 1), # Burning Anxiety | Grand Spell Essence
    (8630008, 4033930, 7500, 1, 1), # Burning Anxiety | Burning Weapon Energy
    (8630008, 2590004, 100, 1, 1), # Burning Anxiety | Prototype Soul Enchanter
    (8630008, 4009140, 7500, 1, 1), # Burning Anxiety | Broken Bow (Red)
    (8630008, 2000037, 100, 1, 1), # Burning Anxiety | Sunrise Dew
    (8630008, 4021033, 100, 1, 1), # Burning Anxiety | Basic Spell Essence
    (8630008, 2000036, 100, 1, 1), # Burning Anxiety | Reindeer Milk
    (8630008, 4021032, 7500, 1, 1), # Burning Anxiety | Mana Crystal
    (8630008, 2431174, 100, 1, 1), # Burning Anxiety | Medal of Honor
    (8630008, 4021020, 10, 1, 1), # Burning Anxiety | Confusion Fragment
    (8630015, 2590004, 100, 1, 1), # Corrupted Basic Magician | Prototype Soul Enchanter
    (8630015, 4021035, 100, 1, 1), # Corrupted Basic Magician | Grand Spell Essence
    (8630015, 4021020, 10, 1, 1), # Corrupted Basic Magician | Confusion Fragment
    (8630015, 4021031, 50, 1, 1), # Corrupted Basic Magician | Twisted Time
    (8630015, 2431174, 100, 1, 1), # Corrupted Basic Magician | Medal of Honor
    (8630015, 4021032, 7500, 1, 1), # Corrupted Basic Magician | Mana Crystal
    (8630015, 2000037, 100, 1, 1), # Corrupted Basic Magician | Sunrise Dew
    (8630015, 4009147, 7500, 1, 1), # Corrupted Basic Magician | Basic Magician's Cloth
    (8630015, 2000036, 100, 1, 1), # Corrupted Basic Magician | Reindeer Milk
    (8630015, 4021033, 100, 1, 1), # Corrupted Basic Magician | Basic Spell Essence
    (8630015, 4021034, 100, 1, 1), # Corrupted Basic Magician | Fine Spell Essence
    (8630014, 2000037, 100, 1, 1), # Permeating Vanity | Sunrise Dew
    (8630014, 4009146, 7500, 1, 1), # Permeating Vanity | Broken Feather (Black)
    (8630014, 4021020, 10, 1, 1), # Permeating Vanity | Confusion Fragment
    (8630014, 4021033, 100, 1, 1), # Permeating Vanity | Basic Spell Essence
    (8630014, 4021032, 7500, 1, 1), # Permeating Vanity | Mana Crystal
    (8630014, 2431174, 100, 1, 1), # Permeating Vanity | Medal of Honor
    (8630014, 2590004, 100, 1, 1), # Permeating Vanity | Prototype Soul Enchanter
    (8630014, 4021035, 100, 1, 1), # Permeating Vanity | Grand Spell Essence
    (8630014, 4021031, 50, 1, 1), # Permeating Vanity | Twisted Time
    (8630014, 4033933, 7500, 1, 1), # Permeating Vanity | Permeating Energy
    (8630014, 2000036, 100, 1, 1), # Permeating Vanity | Reindeer Milk
    (8630014, 4021034, 100, 1, 1), # Permeating Vanity | Fine Spell Essence
    (8630013, 4021033, 100, 1, 1), # Permeating Anxiety | Basic Spell Essence
    (8630013, 2000036, 100, 1, 1), # Permeating Anxiety | Reindeer Milk
    (8630013, 4021020, 10, 1, 1), # Permeating Anxiety | Confusion Fragment
    (8630013, 2000037, 100, 1, 1), # Permeating Anxiety | Sunrise Dew
    (8630013, 4021034, 100, 1, 1), # Permeating Anxiety | Fine Spell Essence
    (8630013, 2590004, 100, 1, 1), # Permeating Anxiety | Prototype Soul Enchanter
    (8630013, 4033933, 7500, 1, 1), # Permeating Anxiety | Permeating Energy
    (8630013, 4021031, 50, 1, 1), # Permeating Anxiety | Twisted Time
    (8630013, 4021035, 100, 1, 1), # Permeating Anxiety | Grand Spell Essence
    (8630013, 4021032, 7500, 1, 1), # Permeating Anxiety | Mana Crystal
    (8630013, 2431174, 100, 1, 1), # Permeating Anxiety | Medal of Honor
    (8630013, 4009145, 7500, 1, 1), # Permeating Anxiety | Broken Bow (Black)
    (8630012, 4021032, 7500, 1, 1), # Permeating Rage | Mana Crystal
    (8630012, 4021033, 100, 1, 1), # Permeating Rage | Basic Spell Essence
    (8630012, 4021031, 50, 1, 1), # Permeating Rage | Twisted Time
    (8630012, 4021034, 100, 1, 1), # Permeating Rage | Fine Spell Essence
    (8630012, 4021035, 100, 1, 1), # Permeating Rage | Grand Spell Essence
    (8630012, 4021020, 10, 1, 1), # Permeating Rage | Confusion Fragment
    (8630012, 4009144, 7500, 1, 1), # Permeating Rage | Broken Shaft (Black)
    (8630012, 2000036, 100, 1, 1), # Permeating Rage | Reindeer Milk
    (8630012, 2000037, 100, 1, 1), # Permeating Rage | Sunrise Dew
    (8630012, 4033933, 7500, 1, 1), # Permeating Rage | Permeating Energy
    (8630012, 2590004, 100, 1, 1), # Permeating Rage | Prototype Soul Enchanter
    (8630012, 2431174, 100, 1, 1), # Permeating Rage | Medal of Honor
    (2600615, 4021034, 100, 1, 1), # Master Death Teddy | Fine Spell Essence
    (2600615, 2001528, 100, 1, 1), # Master Death Teddy | Melting Cheese
    (2600615, 4021031, 50, 1, 1), # Master Death Teddy | Twisted Time
    (2600615, 4021033, 100, 1, 1), # Master Death Teddy | Basic Spell Essence
    (2600615, 4000148, 7500, 1, 1), # Master Death Teddy | Binding Bridle
    (2600615, 2434021, 100, 1, 1), # Master Death Teddy | Medal of Honor
    (2600615, 2000037, 100, 1, 1), # Master Death Teddy | Sunrise Dew
    (2600615, 2590004, 100, 1, 1), # Master Death Teddy | Prototype Soul Enchanter
    (2600615, 4021035, 100, 1, 1), # Master Death Teddy | Grand Spell Essence
    (2600615, 4034181, 7500, 1, 1), # Master Death Teddy | Dispersed Star Fragment
    (2600615, 2870285, 100, 1, 1), # Master Death Teddy | [*]Master Death Teddy Familiar
    (2600615, 4021032, 7500, 1, 1), # Master Death Teddy | Mana Crystal
    (8900103, 4310029, 7500, 1, 1), # Pierre's Treasure Chest | Crusader Coins
    (8900103, 2433103, 100, 1, 1), # Pierre's Treasure Chest | Boss Medal of Honor
    (2600614, 4000147, 7500, 1, 1), # Death Teddy | Sealed Teddy Bear
    (2600614, 4034181, 7500, 1, 1), # Death Teddy | Dispersed Star Fragment
    (2600614, 4021032, 7500, 1, 1), # Death Teddy | Mana Crystal
    (2600614, 2870277, 100, 1, 1), # Death Teddy | [*]Death Teddy Familiar
    (2600614, 2590004, 100, 1, 1), # Death Teddy | Prototype Soul Enchanter
    (2600614, 4021034, 100, 1, 1), # Death Teddy | Fine Spell Essence
    (2600614, 4021035, 100, 1, 1), # Death Teddy | Grand Spell Essence
    (2600614, 2434021, 100, 1, 1), # Death Teddy | Medal of Honor
    (2600614, 4021033, 100, 1, 1), # Death Teddy | Basic Spell Essence
    (2600614, 2000037, 100, 1, 1), # Death Teddy | Sunrise Dew
    (2600614, 4021031, 50, 1, 1), # Death Teddy | Twisted Time
    (2600614, 2001528, 100, 1, 1), # Death Teddy | Melting Cheese
    (2600613, 4000113, 7500, 1, 1), # Timer | Clock Spring
    (2600613, 4021035, 100, 1, 1), # Timer | Grand Spell Essence
    (2600613, 4000114, 7500, 1, 1), # Timer | Table Clock
    (2600613, 4021033, 100, 1, 1), # Timer | Basic Spell Essence
    (2600613, 4021032, 7500, 1, 1), # Timer | Mana Crystal
    (2600613, 4000115, 7500, 1, 1), # Timer | Cog
    (2600613, 4021034, 100, 1, 1), # Timer | Fine Spell Essence
    (2600612, 2870145, 100, 1, 1), # Master Chronos | Master Chronos Familiar
    (2600612, 2434021, 100, 1, 1), # Master Chronos | Medal of Honor
    (2600612, 2590004, 100, 1, 1), # Master Chronos | Prototype Soul Enchanter
    (2600612, 4034343, 7500, 1, 1), # Master Chronos | Cog
    (2600612, 2000037, 100, 1, 1), # Master Chronos | Sunrise Dew
    (2600612, 4021034, 100, 1, 1), # Master Chronos | Fine Spell Essence
    (2600612, 4021035, 100, 1, 1), # Master Chronos | Grand Spell Essence
    (2600612, 4021032, 7500, 1, 1), # Master Chronos | Mana Crystal
    (2600612, 2001528, 100, 1, 1), # Master Chronos | Melting Cheese
    (2600612, 4021033, 100, 1, 1), # Master Chronos | Basic Spell Essence
    (2600612, 4021031, 50, 1, 1), # Master Chronos | Twisted Time
    (2600611, 4021031, 50, 1, 1), # Platoon Chronos | Twisted Time
    (2600611, 4021034, 100, 1, 1), # Platoon Chronos | Fine Spell Essence
    (2600611, 2001528, 100, 1, 1), # Platoon Chronos | Melting Cheese
    (2600611, 2590004, 100, 1, 1), # Platoon Chronos | Prototype Soul Enchanter
    (2600611, 4021035, 100, 1, 1), # Platoon Chronos | Grand Spell Essence
    (2600611, 2000037, 100, 1, 1), # Platoon Chronos | Sunrise Dew
    (2600611, 2434021, 100, 1, 1), # Platoon Chronos | Medal of Honor
    (2600611, 4021033, 100, 1, 1), # Platoon Chronos | Basic Spell Essence
    (2600611, 2870144, 100, 1, 1), # Platoon Chronos | Platoon Chronos Familiar
    (2600611, 4021032, 7500, 1, 1), # Platoon Chronos | Mana Crystal
    (2600611, 4034343, 7500, 1, 1), # Platoon Chronos | Cog
    (2600610, 2434021, 100, 1, 1), # Chronos | Medal of Honor
    (2600610, 4021033, 100, 1, 1), # Chronos | Basic Spell Essence
    (2600610, 2870103, 100, 1, 1), # Chronos | Chronos Familiar
    (2600610, 2000037, 100, 1, 1), # Chronos | Sunrise Dew
    (2600610, 4034343, 7500, 1, 1), # Chronos | Cog
    (2600610, 2590004, 100, 1, 1), # Chronos | Prototype Soul Enchanter
    (2600610, 4021032, 7500, 1, 1), # Chronos | Mana Crystal
    (2600610, 2001528, 100, 1, 1), # Chronos | Melting Cheese
    (2600610, 4021034, 100, 1, 1), # Chronos | Fine Spell Essence
    (2600610, 4021035, 100, 1, 1), # Chronos | Grand Spell Essence
    (2600609, 4009087, 7500, 1, 1), # Tick-Tock | Table Clock
    (2600609, 4021032, 7500, 1, 1), # Tick-Tock | Mana Crystal
    (2600609, 2870143, 100, 1, 1), # Tick-Tock | Tick-Tock Familiar
    (2600609, 4021033, 100, 1, 1), # Tick-Tock | Basic Spell Essence
    (2600609, 4021034, 100, 1, 1), # Tick-Tock | Fine Spell Essence
    (2600609, 4021035, 100, 1, 1), # Tick-Tock | Grand Spell Essence
    (2600609, 4031098, 7500, 1, 1), # Tick-Tock | All-purpose Clock Spring
    (2600608, 4021033, 100, 1, 1), # Tick | Basic Spell Essence
    (2600608, 4021034, 100, 1, 1), # Tick | Fine Spell Essence
    (2600608, 2870086, 100, 1, 1), # Tick | Tick Familiar
    (2600608, 4021035, 100, 1, 1), # Tick | Grand Spell Essence
    (2600608, 4021032, 7500, 1, 1), # Tick | Mana Crystal
    (2600608, 4001532, 7500, 1, 1), # Tick | Clock Spring
    (2600623, 4021034, 100, 1, 1), # Ghost Pirate | Fine Spell Essence
    (2600623, 2434021, 100, 1, 1), # Ghost Pirate | Medal of Honor
    (2600623, 4021031, 50, 1, 1), # Ghost Pirate | Twisted Time
    (2600623, 2001528, 100, 1, 1), # Ghost Pirate | Melting Cheese
    (2600623, 4000132, 7500, 1, 1), # Ghost Pirate | Ghost Pirate Key
    (2600623, 4021033, 100, 1, 1), # Ghost Pirate | Basic Spell Essence
    (2600623, 4034181, 7500, 1, 1), # Ghost Pirate | Dispersed Star Fragment
    (2600623, 2000037, 100, 1, 1), # Ghost Pirate | Sunrise Dew
    (2600623, 4021032, 7500, 1, 1), # Ghost Pirate | Mana Crystal
    (2600623, 2590004, 100, 1, 1), # Ghost Pirate | Prototype Soul Enchanter
    (2600623, 4021035, 100, 1, 1), # Ghost Pirate | Grand Spell Essence
    (2600623, 2870293, 100, 1, 1), # Ghost Pirate | [*]Ghost Pirate Familiar
    (2600622, 4001023, 7500, 1, 1), # Alishar | Key of Dimension
    (2600621, 2001528, 100, 1, 1), # Lazy Buffy | Melting Cheese
    (2600621, 2434021, 100, 1, 1), # Lazy Buffy | Medal of Honor
    (2600621, 2000037, 100, 1, 1), # Lazy Buffy | Sunrise Dew
    (2600621, 4021033, 100, 1, 1), # Lazy Buffy | Basic Spell Essence
    (2600621, 4000129, 7500, 1, 1), # Lazy Buffy | Lazy Buffy Marble
    (2600621, 4021035, 100, 1, 1), # Lazy Buffy | Grand Spell Essence
    (2600621, 4021031, 50, 1, 1), # Lazy Buffy | Twisted Time
    (2600621, 4021032, 7500, 1, 1), # Lazy Buffy | Mana Crystal
    (2600621, 2870252, 100, 1, 1), # Lazy Buffy | [*]Lazy Buffy Familiar
    (2600621, 4034181, 7500, 1, 1), # Lazy Buffy | Dispersed Star Fragment
    (2600621, 2590004, 100, 1, 1), # Lazy Buffy | Prototype Soul Enchanter
    (2600621, 4021034, 100, 1, 1), # Lazy Buffy | Fine Spell Essence
    (2600621, 4031189, 7500, 1, 1), # Lazy Buffy | Aurora Marble
    (2600620, 4021031, 50, 1, 1), # Buffy | Twisted Time
    (2600620, 2870239, 100, 1, 1), # Buffy | [*]Buffy Familiar
    (2600620, 4000128, 7500, 1, 1), # Buffy | Buffy Hat
    (2600620, 4021035, 100, 1, 1), # Buffy | Grand Spell Essence
    (2600620, 2590004, 100, 1, 1), # Buffy | Prototype Soul Enchanter
    (2600620, 4021032, 7500, 1, 1), # Buffy | Mana Crystal
    (2600620, 2001528, 100, 1, 1), # Buffy | Melting Cheese
    (2600620, 4034181, 7500, 1, 1), # Buffy | Dispersed Star Fragment
    (2600620, 2000037, 100, 1, 1), # Buffy | Sunrise Dew
    (2600620, 2434021, 100, 1, 1), # Buffy | Medal of Honor
    (2600620, 4021033, 100, 1, 1), # Buffy | Basic Spell Essence
    (2600620, 4021034, 100, 1, 1), # Buffy | Fine Spell Essence
    (2600619, 2001528, 100, 1, 1), # Deep Buffoon | Melting Cheese
    (2600619, 2434021, 100, 1, 1), # Deep Buffoon | Medal of Honor
    (2600619, 4000131, 7500, 1, 1), # Deep Buffoon | Deep Buffoon's Rock Piece
    (2600619, 4021033, 100, 1, 1), # Deep Buffoon | Basic Spell Essence
    (2600619, 2590004, 100, 1, 1), # Deep Buffoon | Prototype Soul Enchanter
    (2600619, 4021034, 100, 1, 1), # Deep Buffoon | Fine Spell Essence
    (2600619, 2000037, 100, 1, 1), # Deep Buffoon | Sunrise Dew
    (2600619, 4021031, 50, 1, 1), # Deep Buffoon | Twisted Time
    (2600619, 4034181, 7500, 1, 1), # Deep Buffoon | Dispersed Star Fragment
    (2600619, 2870268, 100, 1, 1), # Deep Buffoon | [*]Deep Buffoon Familiar
    (2600619, 4021035, 100, 1, 1), # Deep Buffoon | Grand Spell Essence
    (2600619, 4021032, 7500, 1, 1), # Deep Buffoon | Mana Crystal
    (2600618, 4021033, 100, 1, 1), # Buffoon | Basic Spell Essence
    (2600618, 4021032, 7500, 1, 1), # Buffoon | Mana Crystal
    (2600618, 4021031, 50, 1, 1), # Buffoon | Twisted Time
    (2600618, 2000037, 100, 1, 1), # Buffoon | Sunrise Dew
    (2600618, 2590004, 100, 1, 1), # Buffoon | Prototype Soul Enchanter
    (2600618, 4021034, 100, 1, 1), # Buffoon | Fine Spell Essence
    (2600618, 4034181, 7500, 1, 1), # Buffoon | Dispersed Star Fragment
    (2600618, 2001528, 100, 1, 1), # Buffoon | Melting Cheese
    (2600618, 4021035, 100, 1, 1), # Buffoon | Grand Spell Essence
    (2600618, 2870263, 100, 1, 1), # Buffoon | [*]Buffoon Familiar
    (2600618, 4000130, 7500, 1, 1), # Buffoon | Buffoon's Grandpa Clock
    (2600618, 2434021, 100, 1, 1), # Buffoon | Medal of Honor
    (2600617, 4021034, 100, 1, 1), # Master Soul Teddy | Fine Spell Essence
    (2600617, 4021031, 50, 1, 1), # Master Soul Teddy | Twisted Time
    (2600617, 2001528, 100, 1, 1), # Master Soul Teddy | Melting Cheese
    (2600617, 4021035, 100, 1, 1), # Master Soul Teddy | Grand Spell Essence
    (2600617, 2434021, 100, 1, 1), # Master Soul Teddy | Medal of Honor
    (2600617, 2590004, 100, 1, 1), # Master Soul Teddy | Prototype Soul Enchanter
    (2600617, 4021032, 7500, 1, 1), # Master Soul Teddy | Mana Crystal
    (2600617, 4000144, 7500, 1, 1), # Master Soul Teddy | Free Spirit
    (2600617, 2870255, 100, 1, 1), # Master Soul Teddy | [*]Master Soul Teddy Familiar
    (2600617, 4021033, 100, 1, 1), # Master Soul Teddy | Basic Spell Essence
    (2600617, 4034181, 7500, 1, 1), # Master Soul Teddy | Dispersed Star Fragment
    (2600617, 2000037, 100, 1, 1), # Master Soul Teddy | Sunrise Dew
    (2600616, 4021031, 50, 1, 1), # Soul Teddy | Twisted Time
    (2600616, 2870253, 100, 1, 1), # Soul Teddy | [*]Soul Teddy Familiar
    (2600616, 4021032, 7500, 1, 1), # Soul Teddy | Mana Crystal
    (2600616, 2590004, 100, 1, 1), # Soul Teddy | Prototype Soul Enchanter
    (2600616, 2434021, 100, 1, 1), # Soul Teddy | Medal of Honor
    (2600616, 4021033, 100, 1, 1), # Soul Teddy | Basic Spell Essence
    (2600616, 2001528, 100, 1, 1), # Soul Teddy | Melting Cheese
    (2600616, 4034181, 7500, 1, 1), # Soul Teddy | Dispersed Star Fragment
    (2600616, 4021034, 100, 1, 1), # Soul Teddy | Fine Spell Essence
    (2600616, 2000037, 100, 1, 1), # Soul Teddy | Sunrise Dew
    (2600616, 4021035, 100, 1, 1), # Soul Teddy | Grand Spell Essence
    (2600616, 4000143, 7500, 1, 1), # Soul Teddy | Zombie Teddy Bear
    (2600631, 2000005, 100, 1, 1), # Papulatus | Power Elixir
    (2600631, 4310029, 7500, 1, 1), # Papulatus | Crusader Coins
    (2600631, 2433103, 100, 1, 1), # Papulatus | Boss Medal of Honor
    (2600631, 4031196, 7500, 1, 1), # Papulatus | Dark Tachion
    (2600630, 4006001, 7500, 1, 1), # Grim Phantom Watch | The Summoning Rock
    (2600630, 2434021, 100, 1, 1), # Grim Phantom Watch | Medal of Honor
    (2600630, 2870334, 100, 1, 1), # Grim Phantom Watch | [*]Grim Phantom Watch Familiar
    (2600630, 2001528, 100, 1, 1), # Grim Phantom Watch | Melting Cheese
    (2600630, 4021033, 100, 1, 1), # Grim Phantom Watch | Basic Spell Essence
    (2600630, 4034181, 7500, 1, 1), # Grim Phantom Watch | Dispersed Star Fragment
    (2600630, 4021031, 50, 1, 1), # Grim Phantom Watch | Twisted Time
    (2600630, 4021034, 100, 1, 1), # Grim Phantom Watch | Fine Spell Essence
    (2600630, 2000037, 100, 1, 1), # Grim Phantom Watch | Sunrise Dew
    (2600630, 2590004, 100, 1, 1), # Grim Phantom Watch | Prototype Soul Enchanter
    (2600630, 4021035, 100, 1, 1), # Grim Phantom Watch | Grand Spell Essence
    (2600630, 4000150, 7500, 1, 1), # Grim Phantom Watch | Ice Piece
    (2600630, 4021032, 7500, 1, 1), # Grim Phantom Watch | Mana Crystal
    (2600629, 2870332, 100, 1, 1), # Phantom Watch | [*]Phantom Watch Familiar
    (2600629, 4000149, 7500, 1, 1), # Phantom Watch | Sealed Bottle
    (2600629, 2001528, 100, 1, 1), # Phantom Watch | Melting Cheese
    (2600629, 4021031, 50, 1, 1), # Phantom Watch | Twisted Time
    (2600629, 4021032, 7500, 1, 1), # Phantom Watch | Mana Crystal
    (2600629, 4021034, 100, 1, 1), # Phantom Watch | Fine Spell Essence
    (2600629, 4021033, 100, 1, 1), # Phantom Watch | Basic Spell Essence
    (2600629, 2434021, 100, 1, 1), # Phantom Watch | Medal of Honor
    (2600629, 2000037, 100, 1, 1), # Phantom Watch | Sunrise Dew
    (2600629, 2590004, 100, 1, 1), # Phantom Watch | Prototype Soul Enchanter
    (2600629, 4034181, 7500, 1, 1), # Phantom Watch | Dispersed Star Fragment
    (2600629, 4021035, 100, 1, 1), # Phantom Watch | Grand Spell Essence
    (2600628, 2870330, 100, 1, 1), # Giagantic Spirit Viking | [*]Gigantic Spirit Viking Familiar
    (2600628, 2001528, 100, 1, 1), # Giagantic Spirit Viking | Melting Cheese
    (2600628, 4021031, 50, 1, 1), # Giagantic Spirit Viking | Twisted Time
    (2600628, 4021032, 7500, 1, 1), # Giagantic Spirit Viking | Mana Crystal
    (2600628, 2590004, 100, 1, 1), # Giagantic Spirit Viking | Prototype Soul Enchanter
    (2600628, 4021034, 100, 1, 1), # Giagantic Spirit Viking | Fine Spell Essence
    (2600628, 4021033, 100, 1, 1), # Giagantic Spirit Viking | Basic Spell Essence
    (2600628, 2434021, 100, 1, 1), # Giagantic Spirit Viking | Medal of Honor
    (2600628, 4000135, 7500, 1, 1), # Giagantic Spirit Viking | Gigantic Viking Hat
    (2600628, 2000037, 100, 1, 1), # Giagantic Spirit Viking | Sunrise Dew
    (2600628, 4021035, 100, 1, 1), # Giagantic Spirit Viking | Grand Spell Essence
    (2600628, 4034181, 7500, 1, 1), # Giagantic Spirit Viking | Dispersed Star Fragment
    (2600627, 2000037, 100, 1, 1), # Spirit Viking | Sunrise Dew
    (2600627, 2590004, 100, 1, 1), # Spirit Viking | Prototype Soul Enchanter
    (2600627, 4000134, 7500, 1, 1), # Spirit Viking | Viking Sail
    (2600627, 4021031, 50, 1, 1), # Spirit Viking | Twisted Time
    (2600627, 4034181, 7500, 1, 1), # Spirit Viking | Dispersed Star Fragment
    (2600627, 2870329, 100, 1, 1), # Spirit Viking | [*]Spirit Viking Familiar
    (2600627, 4021032, 7500, 1, 1), # Spirit Viking | Mana Crystal
    (2600627, 4021033, 100, 1, 1), # Spirit Viking | Basic Spell Essence
    (2600627, 4021034, 100, 1, 1), # Spirit Viking | Fine Spell Essence
    (2600627, 2434021, 100, 1, 1), # Spirit Viking | Medal of Honor
    (2600627, 4021035, 100, 1, 1), # Spirit Viking | Grand Spell Essence
    (2600627, 2001528, 100, 1, 1), # Spirit Viking | Melting Cheese
    (2600626, 4021034, 100, 1, 1), # Dark Klock | Fine Spell Essence
    (2600626, 4021033, 100, 1, 1), # Dark Klock | Basic Spell Essence
    (2600626, 4000146, 7500, 1, 1), # Dark Klock | Evil Spirit
    (2600626, 4021031, 50, 1, 1), # Dark Klock | Twisted Time
    (2600626, 2000037, 100, 1, 1), # Dark Klock | Sunrise Dew
    (2600626, 2001528, 100, 1, 1), # Dark Klock | Melting Cheese
    (2600626, 2870322, 100, 1, 1), # Dark Klock | [*]Dark Klock Familiar
    (2600626, 4021032, 7500, 1, 1), # Dark Klock | Mana Crystal
    (2600626, 2590004, 100, 1, 1), # Dark Klock | Prototype Soul Enchanter
    (2600626, 2434021, 100, 1, 1), # Dark Klock | Medal of Honor
    (2600626, 4021035, 100, 1, 1), # Dark Klock | Grand Spell Essence
    (2600626, 4034181, 7500, 1, 1), # Dark Klock | Dispersed Star Fragment
    (2600625, 2001528, 100, 1, 1), # Klock | Melting Cheese
    (2600625, 4021031, 50, 1, 1), # Klock | Twisted Time
    (2600625, 4034181, 7500, 1, 1), # Klock | Dispersed Star Fragment
    (2600625, 4000145, 7500, 1, 1), # Klock | Sealed-up Grandpa Clock
    (2600625, 4021033, 100, 1, 1), # Klock | Basic Spell Essence
    (2600625, 2870321, 100, 1, 1), # Klock | [*]Klock Familiar
    (2600625, 2434021, 100, 1, 1), # Klock | Medal of Honor
    (2600625, 2000037, 100, 1, 1), # Klock | Sunrise Dew
    (2600625, 4021034, 100, 1, 1), # Klock | Fine Spell Essence
    (2600625, 4021032, 7500, 1, 1), # Klock | Mana Crystal
    (2600625, 4021035, 100, 1, 1), # Klock | Grand Spell Essence
    (2600625, 2590004, 100, 1, 1), # Klock | Prototype Soul Enchanter
    (2600624, 4000133, 7500, 1, 1), # Dual Ghost Pirate | Dual Pirate's Propeller
    (2600624, 2000037, 100, 1, 1), # Dual Ghost Pirate | Sunrise Dew
    (2600624, 4021034, 100, 1, 1), # Dual Ghost Pirate | Fine Spell Essence
    (2600624, 4021032, 7500, 1, 1), # Dual Ghost Pirate | Mana Crystal
    (2600624, 2001528, 100, 1, 1), # Dual Ghost Pirate | Melting Cheese
    (2600624, 4034181, 7500, 1, 1), # Dual Ghost Pirate | Dispersed Star Fragment
    (2600624, 4021031, 50, 1, 1), # Dual Ghost Pirate | Twisted Time
    (2600624, 4021035, 100, 1, 1), # Dual Ghost Pirate | Grand Spell Essence
    (2600624, 2590004, 100, 1, 1), # Dual Ghost Pirate | Prototype Soul Enchanter
    (2600624, 2434021, 100, 1, 1), # Dual Ghost Pirate | Medal of Honor
    (2600624, 2870299, 100, 1, 1), # Dual Ghost Pirate | [*]Dual Ghost Pirate Familiar
    (2600624, 4021033, 100, 1, 1), # Dual Ghost Pirate | Basic Spell Essence
    (5090001, 4021032, 7500, 1, 1), # Master Dummy | Mana Crystal
    (5090001, 4000412, 7500, 1, 1), # Master Dummy | Proof of Training
    (5090001, 4310029, 7500, 1, 1), # Master Dummy | Crusader Coins
    (5090001, 2870183, 100, 1, 1), # Master Dummy | Master Dummy Familiar
    (5090001, 4021035, 100, 1, 1), # Master Dummy | Grand Spell Essence
    (5090001, 4021034, 100, 1, 1), # Master Dummy | Fine Spell Essence
    (5090001, 4021033, 100, 1, 1), # Master Dummy | Basic Spell Essence
    (5090000, 4033041, 7500, 1, 1), # Shade | Someone's Hat
    (5090000, 4033040, 7500, 1, 1), # Shade | Lost Bag
    (5090000, 2870182, 100, 1, 1), # Shade | Shade Familiar
    (100001, 2010009, 100, 1, 1), # Blue Snail | Green Apple
    (100001, 4000000, 7500, 1, 1), # Blue Snail | Blue Snail Shell
    (100001, 2000003, 100, 1, 1), # Blue Snail | Blue Potion
    (100001, 2061000, 100, 1, 1), # Blue Snail | Arrow for Crossbow
    (100001, 2870001, 100, 1, 1), # Blue Snail | Blue Snail Familiar
    (100000, 2870000, 100, 1, 1), # Snail | Snail Familiar
    (100000, 2060000, 100, 1, 1), # Snail | Arrow for Bow
    (100000, 2000000, 100, 1, 1), # Snail | Red Potion
    (100000, 4000019, 7500, 1, 1); # Snail | Green Snail Shell

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (100000, 2010009, 100, 1, 1), # Snail | Green Apple
    (100003, 2061000, 100, 1, 1), # Spore | Arrow for Crossbow
    (100003, 4000011, 7500, 1, 1), # Spore | Mushroom Spore
    (100003, 2870002, 100, 1, 1), # Spore | Shroom Familiar
    (100003, 2010009, 100, 1, 1), # Spore | Green Apple
    (100003, 2000000, 100, 1, 1), # Spore | Red Potion
    (100002, 2022055, 100, 1, 1), # Red Snail | Persimmon
    (100002, 2022054, 100, 1, 1), # Red Snail | Pear
    (100002, 2022053, 100, 1, 1), # Red Snail | Jujube
    (100002, 4020004, 7500, 1, 1), # Red Snail | Opal Ore
    (100002, 2010009, 100, 1, 1), # Red Snail | Green Apple
    (100002, 2060000, 100, 1, 1), # Red Snail | Arrow for Bow
    (100002, 2000000, 100, 1, 1), # Red Snail | Red Potion
    (100002, 2000003, 100, 1, 1), # Red Snail | Blue Potion
    (100002, 2870004, 100, 1, 1), # Red Snail | Red Snail Familiar
    (100002, 4010004, 7500, 1, 1), # Red Snail | Silver Ore
    (100002, 4000016, 7500, 1, 1), # Red Snail | Red Snail Shell
    (100002, 2870004, 100, 1, 1), # Red Snail | Red Snail Familiar
    (100002, 2061000, 100, 1, 1), # Red Snail | Arrow for Crossbow
    (100005, 2000003, 100, 1, 1), # Stump | Blue Potion
    (100005, 2010009, 100, 1, 1), # Stump | Green Apple
    (100005, 4000003, 7500, 1, 1), # Stump | Tree Branch
    (100005, 2870003, 100, 1, 1), # Stump | Stump Familiar
    (100005, 2000000, 100, 1, 1), # Stump | Red Potion
    (8810018, 4310029, 7500, 1, 1), # Horntail | Crusader Coins
    (8810018, 2433103, 100, 1, 1), # Horntail | Boss Medal of Honor
    (8810018, 4001094, 7500, 1, 1), # Horntail | Nine Spirit's Egg
    (100004, 4000001, 7500, 1, 1), # Orange Mushroom | Orange Mushroom Cap
    (100004, 2000001, 100, 1, 1), # Orange Mushroom | Orange Potion
    (100004, 4020007, 7500, 1, 1), # Orange Mushroom | Diamond Ore
    (100004, 2022055, 100, 1, 1), # Orange Mushroom | Persimmon
    (100004, 2870021, 100, 1, 1), # Orange Mushroom | Orange Mushroom Familiar
    (100004, 2060000, 100, 1, 1), # Orange Mushroom | Arrow for Bow
    (100004, 2040705, 100, 1, 1), # Orange Mushroom | Scroll for Shoes for Jump 10%
    (100004, 2022053, 100, 1, 1), # Orange Mushroom | Jujube
    (100004, 2022054, 100, 1, 1), # Orange Mushroom | Pear
    (100004, 4010000, 7500, 1, 1), # Orange Mushroom | Bronze Ore
    (100007, 4000017, 7500, 1, 1), # Pig | Pig's Head
    (100007, 2000003, 100, 1, 1), # Pig | Blue Potion
    (100007, 2010009, 100, 1, 1), # Pig | Green Apple
    (100007, 2060000, 100, 1, 1), # Pig | Arrow for Bow
    (100007, 4000021, 7500, 1, 1), # Pig | Leather
    (100007, 4010006, 7500, 1, 1), # Pig | Gold Ore
    (100007, 4020006, 7500, 1, 1), # Pig | Topaz Ore
    (100007, 2022055, 100, 1, 1), # Pig | Persimmon
    (100007, 2022054, 100, 1, 1), # Pig | Pear
    (100007, 2022053, 100, 1, 1), # Pig | Jujube
    (100007, 2870019, 100, 1, 1), # Pig | Pig Familiar
    (100006, 4020005, 7500, 1, 1), # Slime | Sapphire Ore
    (100006, 2010009, 100, 1, 1), # Slime | Green Apple
    (100006, 2000001, 100, 1, 1), # Slime | Orange Potion
    (100006, 2022053, 100, 1, 1), # Slime | Jujube
    (100006, 4000004, 7500, 1, 1), # Slime | Squishy Liquid
    (100006, 2022054, 100, 1, 1), # Slime | Pear
    (100006, 4000010, 7500, 1, 1), # Slime | Slime Bubble
    (100006, 2872038, 100, 1, 1), # Slime | Slime Familiar
    (100006, 2022055, 100, 1, 1), # Slime | Persimmon
    (100006, 2000003, 100, 1, 1), # Slime | Blue Potion
    (100006, 2061000, 100, 1, 1), # Slime | Arrow for Crossbow
    (100006, 4010005, 7500, 1, 1), # Slime | Orihalcon Ore
    (8140500, 4021033, 100, 1, 1), # [*]Bain | Basic Spell Essence
    (8140500, 2590004, 100, 1, 1), # [*]Bain | Prototype Soul Enchanter
    (8140500, 2434021, 100, 1, 1), # [*]Bain | Medal of Honor
    (8140500, 4021031, 50, 1, 1), # [*]Bain | Twisted Time
    (8140500, 2000036, 100, 1, 1), # [*]Bain | Reindeer Milk
    (8140500, 4000080, 7500, 1, 1), # [*]Bain | Bain's Spiky Collar
    (8140500, 4021035, 100, 1, 1), # [*]Bain | Grand Spell Essence
    (8140500, 4021032, 7500, 1, 1), # [*]Bain | Mana Crystal
    (8140500, 4021034, 100, 1, 1), # [*]Bain | Fine Spell Essence
    (8140500, 2001554, 100, 1, 1), # [*]Bain | Sunset Dew
    (8140500, 2870323, 100, 1, 1), # [*]Bain | [*]Bain Familiar
    (8510000, 4021035, 100, 1, 1), # Pianus | Grand Spell Essence
    (8510000, 4000175, 7500, 1, 1), # Pianus | Miniature Pianus
    (8510000, 4021031, 50, 1, 1), # Pianus | Twisted Time
    (8510000, 4021034, 100, 1, 1), # Pianus | Fine Spell Essence
    (8510000, 2431895, 3000, 1, 1), # Pianus | Pianus's Soul Shard
    (8510000, 4021032, 7500, 1, 1), # Pianus | Mana Crystal
    (8510000, 4021033, 100, 1, 1), # Pianus | Basic Spell Essence
    (8510000, 4031253, 7500, 1, 1), # Pianus | Pianus's Scream
    (2600607, 4021032, 7500, 1, 1), # Targa | Mana Crystal
    (2600607, 4310085, 7500, 1, 1), # Targa | Time Coin
    (2600607, 4021034, 100, 1, 1), # Targa | Fine Spell Essence
    (2600607, 4000687, 7500, 1, 1), # Targa | Targa Paw
    (2600607, 4021033, 100, 1, 1), # Targa | Basic Spell Essence
    (2600607, 4021040, 7500, 1, 1), # Targa | Dimension Fragment
    (2600607, 4021031, 50, 1, 1), # Targa | Twisted Time
    (2600607, 4021035, 100, 1, 1), # Targa | Grand Spell Essence
    (2600606, 4021034, 100, 1, 1), # Master Robo | Fine Spell Essence
    (2600606, 4000112, 7500, 1, 1), # Master Robo | Mechanical Heart
    (2600606, 4031130, 7500, 1, 1), # Master Robo | Maintenance Manual
    (2600606, 4021032, 7500, 1, 1), # Master Robo | Mana Crystal
    (2600606, 4021035, 100, 1, 1), # Master Robo | Grand Spell Essence
    (2600606, 4021033, 100, 1, 1), # Master Robo | Basic Spell Essence
    (2600606, 2870142, 100, 1, 1), # Master Robo | Master Robo Familiar
    (2600605, 2870141, 100, 1, 1), # Robo | Robo Familiar
    (2600605, 2590004, 100, 1, 1), # Robo | Prototype Soul Enchanter
    (2600605, 4021033, 100, 1, 1), # Robo | Basic Spell Essence
    (2600605, 2001528, 100, 1, 1), # Robo | Melting Cheese
    (2600605, 4021031, 50, 1, 1), # Robo | Twisted Time
    (2600605, 2000037, 100, 1, 1), # Robo | Sunrise Dew
    (2600605, 4021035, 100, 1, 1), # Robo | Grand Spell Essence
    (2600605, 4021032, 7500, 1, 1), # Robo | Mana Crystal
    (2600605, 4000111, 7500, 1, 1), # Robo | Cheap Battery
    (2600605, 2434021, 100, 1, 1), # Robo | Medal of Honor
    (2600605, 4021034, 100, 1, 1), # Robo | Fine Spell Essence
    (2600604, 4000110, 7500, 1, 1), # Toy Trojan | Toy Trojan Sword
    (2600604, 4021032, 7500, 1, 1), # Toy Trojan | Mana Crystal
    (2600604, 4021034, 100, 1, 1), # Toy Trojan | Fine Spell Essence
    (2600604, 4021033, 100, 1, 1), # Toy Trojan | Basic Spell Essence
    (2600604, 4021035, 100, 1, 1), # Toy Trojan | Grand Spell Essence
    (2600603, 2870083, 100, 1, 1), # Roloduck | Roloduck Familiar
    (2600603, 4021034, 100, 1, 1), # Roloduck | Fine Spell Essence
    (2600603, 4021031, 50, 1, 1), # Roloduck | Twisted Time
    (2600603, 4021032, 7500, 1, 1), # Roloduck | Mana Crystal
    (2600603, 4021033, 100, 1, 1), # Roloduck | Basic Spell Essence
    (2600603, 4021035, 100, 1, 1), # Roloduck | Grand Spell Essence
    (2600602, 4021034, 100, 1, 1), # Panda Teddy | Fine Spell Essence
    (2600602, 4021035, 100, 1, 1), # Panda Teddy | Grand Spell Essence
    (2600602, 2870082, 100, 1, 1), # Panda Teddy | Panda Teddy Familiar
    (2600602, 4009080, 7500, 1, 1), # Panda Teddy | Cute Panda Doll
    (2600602, 4021033, 100, 1, 1), # Panda Teddy | Basic Spell Essence
    (2600602, 4021032, 7500, 1, 1), # Panda Teddy | Mana Crystal
    (2600601, 4021035, 100, 1, 1), # Pink Teddy | Grand Spell Essence
    (2600601, 4021031, 50, 1, 1), # Pink Teddy | Twisted Time
    (2600601, 4021034, 100, 1, 1), # Pink Teddy | Fine Spell Essence
    (2600601, 4021032, 7500, 1, 1), # Pink Teddy | Mana Crystal
    (2600601, 2870069, 100, 1, 1), # Pink Teddy | Pink Teddy Familiar
    (2600601, 4000107, 7500, 1, 1), # Pink Teddy | Teddy's Yellow Ribbon
    (2600601, 4021033, 100, 1, 1), # Pink Teddy | Basic Spell Essence
    (2600600, 4021034, 100, 1, 1), # Brown Teddy | Fine Spell Essence
    (2600600, 2870063, 100, 1, 1), # Brown Teddy | Brown Teddy Familiar
    (2600600, 4000106, 7500, 1, 1), # Brown Teddy | Teddy's Cotton
    (2600600, 4021032, 7500, 1, 1), # Brown Teddy | Mana Crystal
    (2600600, 4021033, 100, 1, 1), # Brown Teddy | Basic Spell Essence
    (2600600, 2434021, 100, 1, 1), # Brown Teddy | Medal of Honor
    (2600600, 4021035, 100, 1, 1), # Brown Teddy | Grand Spell Essence
    (2600600, 2001528, 100, 1, 1), # Brown Teddy | Melting Cheese
    (2600600, 2000037, 100, 1, 1), # Brown Teddy | Sunrise Dew
    (2600600, 2590004, 100, 1, 1), # Brown Teddy | Prototype Soul Enchanter
    (4240000, 4000125, 7500, 1, 1), # Chief Gray | Chief Gray's Sign
    (4240000, 2870168, 100, 1, 1), # Chief Gray | Chief Gray Familiar
    (8250028, 4021035, 100, 1, 1), # Salvoroid Blue | Grand Spell Essence
    (8250028, 2000036, 100, 1, 1), # Salvoroid Blue | Reindeer Milk
    (8250028, 4034302, 7500, 1, 1), # Salvoroid Blue | Blue Blind Shell
    (8250028, 4001842, 10000, 1, 1), # Salvoroid Blue | Diffusion-Line Energy Core (Grade A)
    (8250028, 4021034, 100, 1, 1), # Salvoroid Blue | Fine Spell Essence
    (8250028, 4021032, 7500, 1, 1), # Salvoroid Blue | Mana Crystal
    (8250028, 4021020, 10, 1, 1), # Salvoroid Blue | Confusion Fragment
    (8250028, 2434021, 100, 1, 1), # Salvoroid Blue | Medal of Honor
    (8250028, 4021033, 100, 1, 1), # Salvoroid Blue | Basic Spell Essence
    (8250028, 2001554, 100, 1, 1), # Salvoroid Blue | Sunset Dew
    (8250028, 2590004, 100, 1, 1), # Salvoroid Blue | Prototype Soul Enchanter
    (8250028, 4021031, 50, 1, 1), # Salvoroid Blue | Twisted Time
    (2700029, 2431964, 3000, 1, 1), # Magnus | Magnus's Soul Shard
    (2700029, 4021035, 100, 1, 1), # Magnus | Grand Spell Essence
    (2700029, 4021033, 100, 1, 1), # Magnus | Basic Spell Essence
    (2700029, 2000005, 100, 1, 1), # Magnus | Power Elixir
    (2700029, 2433103, 100, 1, 1), # Magnus | Boss Medal of Honor
    (2700029, 4310059, 7500, 1, 1), # Magnus | Shadowdealer Coin
    (2700029, 4021034, 100, 1, 1), # Magnus | Fine Spell Essence
    (2700029, 4021032, 7500, 1, 1), # Magnus | Mana Crystal
    (8250024, 4034300, 7500, 1, 1), # Scrap Xenoroid EX | Scrap ID Plate
    (8250024, 4001842, 10000, 1, 1), # Scrap Xenoroid EX | Diffusion-Line Energy Core (Grade A)
    (8250024, 2590004, 100, 1, 1), # Scrap Xenoroid EX | Prototype Soul Enchanter
    (8250024, 2434021, 100, 1, 1), # Scrap Xenoroid EX | Medal of Honor
    (8250024, 2000036, 100, 1, 1), # Scrap Xenoroid EX | Reindeer Milk
    (8250024, 4021035, 100, 1, 1), # Scrap Xenoroid EX | Grand Spell Essence
    (8250024, 2001554, 100, 1, 1), # Scrap Xenoroid EX | Sunset Dew
    (8250024, 4021031, 50, 1, 1), # Scrap Xenoroid EX | Twisted Time
    (8250024, 4021034, 100, 1, 1), # Scrap Xenoroid EX | Fine Spell Essence
    (8250024, 4021033, 100, 1, 1), # Scrap Xenoroid EX | Basic Spell Essence
    (8250024, 4021020, 10, 1, 1), # Scrap Xenoroid EX | Confusion Fragment
    (8250024, 4021032, 7500, 1, 1), # Scrap Xenoroid EX | Mana Crystal
    (8250026, 4021020, 10, 1, 1), # Modded Megaroid | Confusion Fragment
    (8250026, 4021032, 7500, 1, 1), # Modded Megaroid | Mana Crystal
    (8250026, 4021035, 100, 1, 1), # Modded Megaroid | Grand Spell Essence
    (8250026, 4034301, 7500, 1, 1), # Modded Megaroid | Giant Light Bulb
    (8250026, 2001554, 100, 1, 1), # Modded Megaroid | Sunset Dew
    (8250026, 2000036, 100, 1, 1), # Modded Megaroid | Reindeer Milk
    (8250026, 4001842, 10000, 1, 1), # Modded Megaroid | Diffusion-Line Energy Core (Grade A)
    (8250026, 4021033, 100, 1, 1), # Modded Megaroid | Basic Spell Essence
    (8250026, 2434021, 100, 1, 1), # Modded Megaroid | Medal of Honor
    (8250026, 2590004, 100, 1, 1), # Modded Megaroid | Prototype Soul Enchanter
    (8250026, 4021031, 50, 1, 1), # Modded Megaroid | Twisted Time
    (8250026, 4021034, 100, 1, 1), # Modded Megaroid | Fine Spell Essence
    (2110200, 4020006, 7500, 1, 1), # Horny Mushroom | Topaz Ore
    (2110200, 2040929, 100, 1, 1), # Horny Mushroom | Scroll for Shield for STR 100%
    (2110200, 2043214, 100, 1, 1), # Horny Mushroom | Scroll for One-Handed BW for Accuracy 10%
    (2110200, 2040600, 100, 1, 1), # Horny Mushroom | Scroll for Bottomwear for DEF 100%
    (2110200, 2000001, 100, 1, 1), # Horny Mushroom | Orange Potion
    (2110200, 2044300, 100, 1, 1), # Horny Mushroom | Scroll for Spear for ATT 100%
    (2110200, 2060000, 100, 1, 1), # Horny Mushroom | Arrow for Bow
    (2110200, 2044102, 100, 1, 1), # Horny Mushroom | Scroll for Two-handed Axe for ATT 10%
    (2110200, 4000015, 7500, 1, 1), # Horny Mushroom | Horny Mushroom Cap
    (2110200, 4010000, 7500, 1, 1), # Horny Mushroom | Bronze Ore
    (2110200, 2870034, 100, 1, 1), # Horny Mushroom | Horny Mushroom Familiar
    (2600703, 2512155, 100, 1, 1), # Memory Guardian | Advanced Strength Pill VII Recipe
    (2600703, 4021032, 7500, 1, 1), # Memory Guardian | Mana Crystal
    (2600703, 2511035, 100, 1, 1), # Memory Guardian | Sharp Exquisite Belt Recipe
    (2600703, 2512215, 100, 1, 1), # Memory Guardian | Advanced Luck Pill VII Recipe
    (2600703, 4021035, 100, 1, 1), # Memory Guardian | Grand Spell Essence
    (2600703, 2000036, 100, 1, 1), # Memory Guardian | Reindeer Milk
    (2600703, 2512123, 100, 1, 1), # Memory Guardian | Advanced Luck Potion V Recipe
    (2600703, 4021034, 100, 1, 1), # Memory Guardian | Fine Spell Essence
    (2600703, 2512152, 100, 1, 1), # Memory Guardian | Advanced Strength Pill IV Recipe
    (2600703, 4021020, 10, 1, 1), # Memory Guardian | Confusion Fragment
    (2600703, 4000459, 7500, 1, 1), # Memory Guardian | Black Armor Fragment
    (2600703, 4021033, 100, 1, 1), # Memory Guardian | Basic Spell Essence
    (2600703, 4000447, 7500, 1, 1), # Memory Guardian | Green Helmet
    (2600703, 2000037, 100, 1, 1), # Memory Guardian | Sunrise Dew
    (2600703, 2512283, 100, 1, 1), # Memory Guardian | Superior Intelligence Boost Potion Recipe
    (2600703, 2512278, 100, 1, 1), # Memory Guardian | Superior Insight Potion Recipe
    (2600703, 2330002, 100, 1, 1), # Memory Guardian | Mighty Bullet
    (2600703, 2431174, 100, 1, 1), # Memory Guardian | Medal of Honor
    (2600703, 2512036, 100, 1, 1), # Memory Guardian | Attack Potion V Recipe
    (2600703, 2511057, 100, 1, 1), # Memory Guardian | Purple Peacock Pendant Recipe
    (2600703, 2049401, 100, 1, 1), # Memory Guardian | Potential Scroll
    (2600703, 2590004, 100, 1, 1), # Memory Guardian | Prototype Soul Enchanter
    (2600703, 4021031, 50, 1, 1), # Memory Guardian | Twisted Time
    (2600703, 4009096, 7500, 1, 1), # Memory Guardian | Piece of Time
    (2600703, 2870353, 100, 1, 1), # Memory Guardian | Memory Guardian Familiar
    (2600702, 2590004, 100, 1, 1), # Memory Monk Trainee | Prototype Soul Enchanter
    (2600702, 4021020, 10, 1, 1), # Memory Monk Trainee | Confusion Fragment
    (2600702, 4021031, 50, 1, 1), # Memory Monk Trainee | Twisted Time
    (2600702, 2000036, 100, 1, 1), # Memory Monk Trainee | Reindeer Milk
    (2600702, 2048004, 100, 1, 1), # Memory Monk Trainee | Scroll for Pet Equip. for Jump 60%
    (2600702, 2512112, 100, 1, 1), # Memory Monk Trainee | Advanced Intelligence Potion IV Recipe
    (2600702, 4021033, 100, 1, 1), # Memory Monk Trainee | Basic Spell Essence
    (2600702, 2870352, 100, 1, 1), # Memory Monk Trainee | Memory Monk Trainee Familiar
    (2600702, 2512284, 100, 1, 1), # Memory Monk Trainee | Exceptional Intelligence Boost Potion Recipe
    (2600702, 4021034, 100, 1, 1), # Memory Monk Trainee | Fine Spell Essence
    (2600702, 2000037, 100, 1, 1), # Memory Monk Trainee | Sunrise Dew
    (2600702, 2431174, 100, 1, 1), # Memory Monk Trainee | Medal of Honor
    (2600702, 2049401, 100, 1, 1), # Memory Monk Trainee | Potential Scroll
    (2600702, 4000446, 7500, 1, 1), # Memory Monk Trainee | Smiley Mask
    (2600702, 4021035, 100, 1, 1), # Memory Monk Trainee | Grand Spell Essence
    (2600702, 2510170, 100, 1, 1), # Memory Monk Trainee | Giant Bullet Recipe
    (2600702, 2510244, 100, 1, 1), # Memory Monk Trainee | Pure Gold Engine Arm Recipe
    (2600702, 2512274, 100, 1, 1), # Memory Monk Trainee | Exceptional Concentration Potion Recipe
    (2600702, 4021032, 7500, 1, 1), # Memory Monk Trainee | Mana Crystal
    (2600702, 4000445, 7500, 1, 1), # Memory Monk Trainee | Green Cone Hat
    (2600702, 2512268, 100, 1, 1), # Memory Monk Trainee | Legendary Hero Potion Recipe
    (2600702, 2512276, 100, 1, 1), # Memory Monk Trainee | Exceptional Fortitude Potion Recipe
    (2600702, 4009096, 7500, 1, 1), # Memory Monk Trainee | Piece of Time
    (8250020, 4021034, 100, 1, 1), # Alloy Xenoroid EX | Fine Spell Essence
    (8250020, 4034298, 7500, 1, 1), # Alloy Xenoroid EX | Alloy ID Plate
    (8250020, 4001842, 10000, 1, 1), # Alloy Xenoroid EX | Diffusion-Line Energy Core (Grade A)
    (8250020, 2590004, 100, 1, 1), # Alloy Xenoroid EX | Prototype Soul Enchanter
    (8250020, 2000036, 100, 1, 1), # Alloy Xenoroid EX | Reindeer Milk
    (8250020, 4021035, 100, 1, 1), # Alloy Xenoroid EX | Grand Spell Essence
    (8250020, 2001554, 100, 1, 1), # Alloy Xenoroid EX | Sunset Dew
    (8250020, 2434021, 100, 1, 1), # Alloy Xenoroid EX | Medal of Honor
    (8250020, 4021020, 10, 1, 1), # Alloy Xenoroid EX | Confusion Fragment
    (8250020, 4021031, 50, 1, 1), # Alloy Xenoroid EX | Twisted Time
    (8250020, 4021032, 7500, 1, 1), # Alloy Xenoroid EX | Mana Crystal
    (8250020, 4021033, 100, 1, 1), # Alloy Xenoroid EX | Basic Spell Essence
    (2600701, 2870351, 100, 1, 1), # Memory Monk | Memory Monk Familiar
    (2600701, 4021033, 100, 1, 1), # Memory Monk | Basic Spell Essence
    (2600701, 4033171, 7500, 1, 1), # Memory Monk | Shard of Recollection
    (2600701, 2512266, 100, 1, 1), # Memory Monk | Superior Hero Potion Recipe
    (2600701, 2512274, 100, 1, 1), # Memory Monk | Exceptional Concentration Potion Recipe
    (2600701, 4009096, 7500, 1, 1), # Memory Monk | Piece of Time
    (2600701, 4021032, 7500, 1, 1), # Memory Monk | Mana Crystal
    (2600701, 4021031, 50, 1, 1), # Memory Monk | Twisted Time
    (2600701, 2000037, 100, 1, 1), # Memory Monk | Sunrise Dew
    (2600701, 2512092, 100, 1, 1), # Memory Monk | Advanced Strength Potion IV Recipe
    (2600701, 4000444, 7500, 1, 1), # Memory Monk | Green Cloth
    (2600701, 4021034, 100, 1, 1), # Memory Monk | Fine Spell Essence
    (2600701, 2330003, 100, 1, 1), # Memory Monk | Vital Bullet
    (2600701, 2431174, 100, 1, 1), # Memory Monk | Medal of Honor
    (2600701, 2510310, 100, 1, 1), # Memory Monk | Abyss Tail Recipe
    (2600701, 2049401, 100, 1, 1), # Memory Monk | Potential Scroll
    (2600701, 2590004, 100, 1, 1), # Memory Monk | Prototype Soul Enchanter
    (2600701, 4021020, 10, 1, 1), # Memory Monk | Confusion Fragment
    (2600701, 2512273, 100, 1, 1), # Memory Monk | Superior Concentration Potion Recipe
    (2600701, 2512271, 100, 1, 1), # Memory Monk | Legendary Blessing Potion Recipe
    (2600701, 2512095, 100, 1, 1), # Memory Monk | Advanced Strength Potion VII Recipe
    (2600701, 4021035, 100, 1, 1), # Memory Monk | Grand Spell Essence
    (2600701, 2512286, 100, 1, 1), # Memory Monk | Exceptional Dexterity Boost Potion Recipe
    (2600701, 2511031, 100, 1, 1), # Memory Monk | Wise Exquisite Belt Recipe
    (2600701, 2510307, 100, 1, 1), # Memory Monk | Abyss Mask Recipe
    (2600701, 4000446, 7500, 1, 1), # Memory Monk | Smiley Mask
    (2600701, 2000036, 100, 1, 1), # Memory Monk | Reindeer Milk
    (2600700, 2000036, 100, 1, 1), # Eye of Time | Reindeer Milk
    (2600700, 4021034, 100, 1, 1), # Eye of Time | Fine Spell Essence
    (2600700, 4021033, 100, 1, 1), # Eye of Time | Basic Spell Essence
    (2600700, 4009096, 7500, 1, 1), # Eye of Time | Piece of Time
    (2600700, 4021035, 100, 1, 1), # Eye of Time | Grand Spell Essence
    (2600700, 4032972, 7500, 1, 1), # Eye of Time | Hourglass of Time
    (2600700, 4021020, 10, 1, 1), # Eye of Time | Confusion Fragment
    (2600700, 4021032, 7500, 1, 1), # Eye of Time | Mana Crystal
    (2600700, 4021031, 50, 1, 1), # Eye of Time | Twisted Time
    (8250022, 4021035, 100, 1, 1), # Steel Xenoroid EX | Grand Spell Essence
    (8250022, 4021032, 7500, 1, 1), # Steel Xenoroid EX | Mana Crystal
    (8250022, 4034299, 7500, 1, 1), # Steel Xenoroid EX | Steel ID Plate
    (8250022, 4021020, 10, 1, 1), # Steel Xenoroid EX | Confusion Fragment
    (8250022, 2001554, 100, 1, 1), # Steel Xenoroid EX | Sunset Dew
    (8250022, 2434021, 100, 1, 1), # Steel Xenoroid EX | Medal of Honor
    (8250022, 4001842, 10000, 1, 1), # Steel Xenoroid EX | Diffusion-Line Energy Core (Grade A)
    (8250022, 2590004, 100, 1, 1), # Steel Xenoroid EX | Prototype Soul Enchanter
    (8250022, 4021034, 100, 1, 1), # Steel Xenoroid EX | Fine Spell Essence
    (8250022, 4021033, 100, 1, 1), # Steel Xenoroid EX | Basic Spell Essence
    (8250022, 2000036, 100, 1, 1), # Steel Xenoroid EX | Reindeer Milk
    (8250022, 4021031, 50, 1, 1), # Steel Xenoroid EX | Twisted Time
    (8850012, 4021032, 7500, 1, 1), # Cygnus | Mana Crystal
    (8850012, 2431711, 3000, 1, 1), # Cygnus | Cygnus's Soul Shard
    (8850012, 2000005, 100, 1, 1), # Cygnus | Power Elixir
    (8850012, 4021033, 100, 1, 1), # Cygnus | Basic Spell Essence
    (8850012, 4000659, 7500, 1, 1), # Cygnus | Cygnus Circlet
    (8850012, 2434589, 100, 1, 1), # Cygnus | Piece of Darkness
    (8850012, 2434588, 100, 1, 1), # Cygnus | Piece of Ruin
    (8850012, 4021020, 10, 1, 1), # Cygnus | Confusion Fragment
    (8850012, 4021035, 100, 1, 1), # Cygnus | Grand Spell Essence
    (8850012, 4021034, 100, 1, 1), # Cygnus | Fine Spell Essence
    (8850012, 2433103, 100, 1, 1), # Cygnus | Boss Medal of Honor
    (8850012, 4310029, 7500, 1, 1), # Cygnus | Crusader Coins
    (8850012, 4021031, 50, 1, 1), # Cygnus | Twisted Time
    (8850012, 1113185, 1000, 1, 1), # Cygnus | Blackgate Ring
    (8850011, 4310029, 7500, 1, 1), # Cygnus | Crusader Coins
    (8850011, 1113185, 1250, 1, 1), # Cygnus | Blackgate Ring
    (8250016, 4021031, 50, 1, 1), # Steel Xenoroid DX | Twisted Time
    (8250016, 4021020, 10, 1, 1), # Steel Xenoroid DX | Confusion Fragment
    (8250016, 4021034, 100, 1, 1), # Steel Xenoroid DX | Fine Spell Essence
    (8250016, 2434021, 100, 1, 1), # Steel Xenoroid DX | Medal of Honor
    (8250016, 2590004, 100, 1, 1), # Steel Xenoroid DX | Prototype Soul Enchanter
    (8250016, 2000036, 100, 1, 1), # Steel Xenoroid DX | Reindeer Milk
    (8250016, 2001554, 100, 1, 1), # Steel Xenoroid DX | Sunset Dew
    (8250016, 4021035, 100, 1, 1), # Steel Xenoroid DX | Grand Spell Essence
    (8250016, 4021033, 100, 1, 1), # Steel Xenoroid DX | Basic Spell Essence
    (8250016, 4034296, 7500, 1, 1), # Steel Xenoroid DX | Steel Xenoroid Chipset
    (8250016, 4001842, 10000, 1, 1), # Steel Xenoroid DX | Diffusion-Line Energy Core (Grade A)
    (8250016, 4021032, 7500, 1, 1), # Steel Xenoroid DX | Mana Crystal
    (6160003, 2431709, 3000, 1, 1), # Xerxes | Xerxes's Soul Shard
    (8250018, 4021020, 10, 1, 1), # Scrap Xenoroid DX | Confusion Fragment
    (8250018, 4021031, 50, 1, 1), # Scrap Xenoroid DX | Twisted Time
    (8250018, 4021033, 100, 1, 1), # Scrap Xenoroid DX | Basic Spell Essence
    (8250018, 2001554, 100, 1, 1), # Scrap Xenoroid DX | Sunset Dew
    (8250018, 2000036, 100, 1, 1), # Scrap Xenoroid DX | Reindeer Milk
    (8250018, 4021035, 100, 1, 1), # Scrap Xenoroid DX | Grand Spell Essence
    (8250018, 4001842, 10000, 1, 1), # Scrap Xenoroid DX | Diffusion-Line Energy Core (Grade A)
    (8250018, 2434021, 100, 1, 1), # Scrap Xenoroid DX | Medal of Honor
    (8250018, 4021034, 100, 1, 1), # Scrap Xenoroid DX | Fine Spell Essence
    (8250018, 2590004, 100, 1, 1), # Scrap Xenoroid DX | Prototype Soul Enchanter
    (8250018, 4034297, 7500, 1, 1), # Scrap Xenoroid DX | Scrap Xenoroid Chipset
    (8250018, 4021032, 7500, 1, 1), # Scrap Xenoroid DX | Mana Crystal
    (8250013, 4001842, 10000, 1, 1), # Repairoid | Diffusion-Line Energy Core (Grade A)
    (8250013, 2434021, 100, 1, 1), # Repairoid | Medal of Honor
    (8250013, 2590004, 100, 1, 1), # Repairoid | Prototype Soul Enchanter
    (8250013, 4021033, 100, 1, 1), # Repairoid | Basic Spell Essence
    (8250013, 2001554, 100, 1, 1), # Repairoid | Sunset Dew
    (8250013, 4021031, 50, 1, 1), # Repairoid | Twisted Time
    (8250013, 4034294, 7500, 1, 1), # Repairoid | Pipe Piece
    (8250013, 2000036, 100, 1, 1), # Repairoid | Reindeer Milk
    (8250013, 4021035, 100, 1, 1), # Repairoid | Grand Spell Essence
    (8250013, 4021032, 7500, 1, 1), # Repairoid | Mana Crystal
    (8250013, 4021034, 100, 1, 1), # Repairoid | Fine Spell Essence
    (8250013, 4021020, 10, 1, 1), # Repairoid | Confusion Fragment
    (8250014, 2000036, 100, 1, 1), # Alloy Xenoroid DX | Reindeer Milk
    (8250014, 4021031, 50, 1, 1), # Alloy Xenoroid DX | Twisted Time
    (8250014, 4021033, 100, 1, 1), # Alloy Xenoroid DX | Basic Spell Essence
    (8250014, 4034295, 7500, 1, 1), # Alloy Xenoroid DX | Alloy Xenoroid Chipset
    (8250014, 4021032, 7500, 1, 1), # Alloy Xenoroid DX | Mana Crystal
    (8250014, 4001842, 10000, 1, 1), # Alloy Xenoroid DX | Diffusion-Line Energy Core (Grade A)
    (8250014, 4021020, 10, 1, 1), # Alloy Xenoroid DX | Confusion Fragment
    (8250014, 2434021, 100, 1, 1), # Alloy Xenoroid DX | Medal of Honor
    (8250014, 4021035, 100, 1, 1), # Alloy Xenoroid DX | Grand Spell Essence
    (8250014, 2590004, 100, 1, 1), # Alloy Xenoroid DX | Prototype Soul Enchanter
    (8250014, 4021034, 100, 1, 1), # Alloy Xenoroid DX | Fine Spell Essence
    (8250014, 2001554, 100, 1, 1), # Alloy Xenoroid DX | Sunset Dew
    (1210102, 4020007, 7500, 1, 1), # Orange Mushroom | Diamond Ore
    (1210102, 2022055, 100, 1, 1), # Orange Mushroom | Persimmon
    (1210102, 2000001, 100, 1, 1), # Orange Mushroom | Orange Potion
    (1210102, 2022053, 100, 1, 1), # Orange Mushroom | Jujube
    (1210102, 2870021, 100, 1, 1), # Orange Mushroom | Orange Mushroom Familiar
    (1210102, 2040705, 100, 1, 1), # Orange Mushroom | Scroll for Shoes for Jump 10%
    (1210102, 4010000, 7500, 1, 1), # Orange Mushroom | Bronze Ore
    (1210102, 2022054, 100, 1, 1), # Orange Mushroom | Pear
    (1210102, 2060000, 100, 1, 1), # Orange Mushroom | Arrow for Bow
    (1210102, 4000001, 7500, 1, 1), # Orange Mushroom | Orange Mushroom Cap
    (8250009, 4021020, 10, 1, 1), # Salvoroid Red | Confusion Fragment
    (8250009, 4021034, 100, 1, 1), # Salvoroid Red | Fine Spell Essence
    (8250009, 4021033, 100, 1, 1), # Salvoroid Red | Basic Spell Essence
    (8250009, 2434021, 100, 1, 1), # Salvoroid Red | Medal of Honor
    (8250009, 4021035, 100, 1, 1), # Salvoroid Red | Grand Spell Essence
    (8250009, 4021031, 50, 1, 1), # Salvoroid Red | Twisted Time
    (8250009, 2590004, 100, 1, 1), # Salvoroid Red | Prototype Soul Enchanter
    (8250009, 4001842, 10000, 1, 1), # Salvoroid Red | Diffusion-Line Energy Core (Grade A)
    (8250009, 2000036, 100, 1, 1), # Salvoroid Red | Reindeer Milk
    (8250009, 2001554, 100, 1, 1), # Salvoroid Red | Sunset Dew
    (8250009, 4034290, 7500, 1, 1), # Salvoroid Red | Red Blind Shell
    (8250009, 4021032, 7500, 1, 1), # Salvoroid Red | Mana Crystal
    (1210103, 4020005, 7500, 1, 1), # Bubbling | Sapphire Ore
    (1210103, 2000003, 100, 1, 1), # Bubbling | Blue Potion
    (1210103, 2043302, 100, 1, 1), # Bubbling | Scroll for Dagger for ATT 10%
    (1210103, 2870022, 100, 1, 1), # Bubbling | Bubbling Familiar
    (1210103, 2000000, 100, 1, 1), # Bubbling | Red Potion
    (1210103, 4000037, 7500, 1, 1), # Bubbling | Bubbling's Huge Bubble
    (1210103, 4006000, 7500, 1, 1), # Bubbling | The Magic Rock
    (1210103, 4010002, 7500, 1, 1), # Bubbling | Mithril Ore
    (8250008, 2590004, 100, 1, 1), # Modded Buffroid | Prototype Soul Enchanter
    (8250008, 4021035, 100, 1, 1), # Modded Buffroid | Grand Spell Essence
    (8250008, 4034289, 7500, 1, 1), # Modded Buffroid | Giant Steel Piece
    (8250008, 4021031, 50, 1, 1), # Modded Buffroid | Twisted Time
    (8250008, 2434021, 100, 1, 1), # Modded Buffroid | Medal of Honor
    (8250008, 4021033, 100, 1, 1), # Modded Buffroid | Basic Spell Essence
    (8250008, 4001842, 10000, 1, 1), # Modded Buffroid | Diffusion-Line Energy Core (Grade A)
    (8250008, 4021034, 100, 1, 1), # Modded Buffroid | Fine Spell Essence
    (8250008, 2000036, 100, 1, 1), # Modded Buffroid | Reindeer Milk
    (8250008, 4021032, 7500, 1, 1), # Modded Buffroid | Mana Crystal
    (8250008, 2001554, 100, 1, 1), # Modded Buffroid | Sunset Dew
    (8250008, 4021020, 10, 1, 1), # Modded Buffroid | Confusion Fragment
    (1210100, 2010009, 100, 1, 1), # Pig | Green Apple
    (1210100, 2022054, 100, 1, 1), # Pig | Pear
    (1210100, 2870019, 100, 1, 1), # Pig | Pig Familiar
    (1210100, 4000017, 7500, 1, 1), # Pig | Pig's Head
    (1210100, 2022055, 100, 1, 1), # Pig | Persimmon
    (1210100, 2000003, 100, 1, 1), # Pig | Blue Potion
    (1210100, 4000021, 7500, 1, 1), # Pig | Leather
    (1210100, 2060000, 100, 1, 1), # Pig | Arrow for Bow
    (1210100, 4010006, 7500, 1, 1), # Pig | Gold Ore
    (1210100, 4020006, 7500, 1, 1), # Pig | Topaz Ore
    (1210100, 2022053, 100, 1, 1), # Pig | Jujube
    (8250011, 4021032, 7500, 1, 1), # Inner Guard EX | Mana Crystal
    (8250011, 4021031, 50, 1, 1), # Inner Guard EX | Twisted Time
    (8250011, 2434021, 100, 1, 1), # Inner Guard EX | Medal of Honor
    (8250011, 2590004, 100, 1, 1), # Inner Guard EX | Prototype Soul Enchanter
    (8250011, 2000036, 100, 1, 1), # Inner Guard EX | Reindeer Milk
    (8250011, 4021020, 10, 1, 1), # Inner Guard EX | Confusion Fragment
    (8250011, 4021033, 100, 1, 1), # Inner Guard EX | Basic Spell Essence
    (8250011, 2001554, 100, 1, 1), # Inner Guard EX | Sunset Dew
    (8250011, 4021035, 100, 1, 1), # Inner Guard EX | Grand Spell Essence
    (8250011, 4021034, 100, 1, 1), # Inner Guard EX | Fine Spell Essence
    (8250011, 4001842, 10000, 1, 1), # Inner Guard EX | Diffusion-Line Energy Core (Grade A)
    (8250011, 4034292, 7500, 1, 1), # Inner Guard EX | Internal Siren
    (1210101, 4000002, 7500, 1, 1), # Ribbon Pig | Pig's Ribbon
    (1210101, 2060000, 100, 1, 1), # Ribbon Pig | Arrow for Bow
    (1210101, 4010002, 7500, 1, 1), # Ribbon Pig | Mithril Ore
    (1210101, 4000021, 7500, 1, 1), # Ribbon Pig | Leather
    (1210101, 4003004, 7500, 1, 1), # Ribbon Pig | Stiff Feather
    (1210101, 4020001, 7500, 1, 1), # Ribbon Pig | Amethyst Ore
    (1210101, 2000003, 100, 1, 1), # Ribbon Pig | Blue Potion
    (1210101, 2872039, 100, 1, 1), # Ribbon Pig | Ribbon Pig Familiar
    (1210101, 2000001, 100, 1, 1), # Ribbon Pig | Orange Potion
    (1210101, 4000017, 7500, 1, 1), # Ribbon Pig | Pig's Head
    (8250010, 2590004, 100, 1, 1), # Outer Guard EX | Prototype Soul Enchanter
    (8250010, 2000036, 100, 1, 1), # Outer Guard EX | Reindeer Milk
    (8250010, 4021031, 50, 1, 1), # Outer Guard EX | Twisted Time
    (8250010, 2001554, 100, 1, 1), # Outer Guard EX | Sunset Dew
    (8250010, 4021035, 100, 1, 1), # Outer Guard EX | Grand Spell Essence
    (8250010, 4021020, 10, 1, 1), # Outer Guard EX | Confusion Fragment
    (8250010, 4021032, 7500, 1, 1), # Outer Guard EX | Mana Crystal
    (8250010, 4001842, 10000, 1, 1), # Outer Guard EX | Diffusion-Line Energy Core (Grade A)
    (8250010, 4021033, 100, 1, 1), # Outer Guard EX | Basic Spell Essence
    (8250010, 4034291, 7500, 1, 1), # Outer Guard EX | Field Siren
    (8250010, 2434021, 100, 1, 1), # Outer Guard EX | Medal of Honor
    (8250010, 4021034, 100, 1, 1), # Outer Guard EX | Fine Spell Essence
    (8250005, 2590004, 100, 1, 1), # Hunterizer Red | Prototype Soul Enchanter
    (8250005, 4034286, 7500, 1, 1), # Hunterizer Red | Red Antenna Piece
    (8250005, 2000036, 100, 1, 1), # Hunterizer Red | Reindeer Milk
    (8250005, 4001842, 10000, 1, 1), # Hunterizer Red | Diffusion-Line Energy Core (Grade A)
    (8250005, 4021031, 50, 1, 1), # Hunterizer Red | Twisted Time
    (8250005, 4021035, 100, 1, 1), # Hunterizer Red | Grand Spell Essence
    (8250005, 2434021, 100, 1, 1), # Hunterizer Red | Medal of Honor
    (8250005, 2001554, 100, 1, 1), # Hunterizer Red | Sunset Dew
    (8250005, 4021033, 100, 1, 1), # Hunterizer Red | Basic Spell Essence
    (8250005, 4021020, 10, 1, 1), # Hunterizer Red | Confusion Fragment
    (8250005, 4021032, 7500, 1, 1), # Hunterizer Red | Mana Crystal
    (8250005, 4021034, 100, 1, 1), # Hunterizer Red | Fine Spell Essence
    (8250004, 4021035, 100, 1, 1), # Chaseroid Blue | Grand Spell Essence
    (8250004, 4021020, 10, 1, 1), # Chaseroid Blue | Confusion Fragment
    (8250004, 2001554, 100, 1, 1), # Chaseroid Blue | Sunset Dew
    (8250004, 4021034, 100, 1, 1), # Chaseroid Blue | Fine Spell Essence
    (8250004, 4021032, 7500, 1, 1), # Chaseroid Blue | Mana Crystal
    (8250004, 2434021, 100, 1, 1), # Chaseroid Blue | Medal of Honor
    (8250004, 4001842, 10000, 1, 1), # Chaseroid Blue | Diffusion-Line Energy Core (Grade A)
    (8250004, 4034285, 7500, 1, 1), # Chaseroid Blue | Blue Android Scope
    (8250004, 4021031, 50, 1, 1), # Chaseroid Blue | Twisted Time
    (8250004, 2590004, 100, 1, 1), # Chaseroid Blue | Prototype Soul Enchanter
    (8250004, 2000036, 100, 1, 1), # Chaseroid Blue | Reindeer Milk
    (8250004, 4021033, 100, 1, 1), # Chaseroid Blue | Basic Spell Essence
    (1210104, 2870023, 100, 1, 1), # Blue Ribbon Pig | Blue Ribbon Pig Familiar
    (1210104, 4000622, 7500, 1, 1), # Blue Ribbon Pig | Pig's Blue Ribbon
    (1210104, 2060000, 100, 1, 1), # Blue Ribbon Pig | Arrow for Bow
    (1210104, 2043200, 100, 1, 1), # Blue Ribbon Pig | Scroll for One-Handed BW for ATT 100%
    (1210104, 4000017, 7500, 1, 1), # Blue Ribbon Pig | Pig's Head
    (1210104, 2000001, 100, 1, 1), # Blue Ribbon Pig | Orange Potion
    (1210104, 2061000, 100, 1, 1), # Blue Ribbon Pig | Arrow for Crossbow
    (1210104, 2040901, 100, 1, 1), # Blue Ribbon Pig | Scroll for Shield for DEF 60%
    (1210104, 2000003, 100, 1, 1), # Blue Ribbon Pig | Blue Potion
    (3000007, 2870065, 100, 1, 1), # Royal Fairy | Royal Fairy Familiar
    (3000007, 4000620, 7500, 1, 1), # Royal Fairy | Royal Fairy Wing
    (8250007, 4021033, 100, 1, 1), # Modded Deliverbot | Basic Spell Essence
    (8250007, 2590004, 100, 1, 1), # Modded Deliverbot | Prototype Soul Enchanter
    (8250007, 2000036, 100, 1, 1), # Modded Deliverbot | Reindeer Milk
    (8250007, 2434021, 100, 1, 1), # Modded Deliverbot | Medal of Honor
    (8250007, 4021032, 7500, 1, 1), # Modded Deliverbot | Mana Crystal
    (8250007, 2001554, 100, 1, 1), # Modded Deliverbot | Sunset Dew
    (8250007, 4021034, 100, 1, 1), # Modded Deliverbot | Fine Spell Essence
    (8250007, 4034288, 7500, 1, 1), # Modded Deliverbot | Prison Key Piece
    (8250007, 4021031, 50, 1, 1), # Modded Deliverbot | Twisted Time
    (8250007, 4021020, 10, 1, 1), # Modded Deliverbot | Confusion Fragment
    (8250007, 4021035, 100, 1, 1), # Modded Deliverbot | Grand Spell Essence
    (8250007, 4001842, 10000, 1, 1), # Modded Deliverbot | Diffusion-Line Energy Core (Grade A)
    (3000006, 2870064, 100, 1, 1), # Krip | Krip Familiar
    (3000006, 4000166, 7500, 1, 1), # Krip | Shrimp Meat
    (8250006, 4021032, 7500, 1, 1), # Hunterizer Blue | Mana Crystal
    (8250006, 4021031, 50, 1, 1), # Hunterizer Blue | Twisted Time
    (8250006, 2001554, 100, 1, 1), # Hunterizer Blue | Sunset Dew
    (8250006, 4001842, 10000, 1, 1), # Hunterizer Blue | Diffusion-Line Energy Core (Grade A)
    (8250006, 2000036, 100, 1, 1), # Hunterizer Blue | Reindeer Milk
    (8250006, 4021033, 100, 1, 1), # Hunterizer Blue | Basic Spell Essence
    (8250006, 2434021, 100, 1, 1), # Hunterizer Blue | Medal of Honor
    (8250006, 4021020, 10, 1, 1), # Hunterizer Blue | Confusion Fragment
    (8250006, 4021035, 100, 1, 1), # Hunterizer Blue | Grand Spell Essence
    (8250006, 2590004, 100, 1, 1), # Hunterizer Blue | Prototype Soul Enchanter
    (8250006, 4034287, 7500, 1, 1), # Hunterizer Blue | Blue Antenna Piece
    (8250006, 4021034, 100, 1, 1), # Hunterizer Blue | Fine Spell Essence
    (3000001, 4000068, 7500, 1, 1), # Fairy | Fairy's Antenna
    (8250001, 2434021, 100, 1, 1), # Modded Broken Android | Medal of Honor
    (8250001, 2590004, 100, 1, 1), # Modded Broken Android | Prototype Soul Enchanter
    (8250001, 2000036, 100, 1, 1), # Modded Broken Android | Reindeer Milk
    (8250001, 4034282, 7500, 1, 1), # Modded Broken Android | Green Chipset Piece
    (8250001, 4021033, 100, 1, 1), # Modded Broken Android | Basic Spell Essence
    (8250001, 4001842, 10000, 1, 1), # Modded Broken Android | Diffusion-Line Energy Core (Grade A)
    (8250001, 4021034, 100, 1, 1), # Modded Broken Android | Fine Spell Essence
    (8250001, 4021031, 50, 1, 1), # Modded Broken Android | Twisted Time
    (8250001, 4021020, 10, 1, 1), # Modded Broken Android | Confusion Fragment
    (8250001, 4021032, 7500, 1, 1), # Modded Broken Android | Mana Crystal
    (8250001, 2001554, 100, 1, 1), # Modded Broken Android | Sunset Dew
    (8250001, 4021035, 100, 1, 1), # Modded Broken Android | Grand Spell Essence
    (3000000, 2870061, 100, 1, 1), # Sentinel | Sentinel Familiar
    (3000000, 4000063, 7500, 1, 1), # Sentinel | Sentinel Shellpiece
    (5220002, 2870218, 100, 1, 1), # Faust | Faust Familiar
    (8250000, 2001554, 100, 1, 1), # Modded Scaredroid | Sunset Dew
    (8250000, 4021033, 100, 1, 1), # Modded Scaredroid | Basic Spell Essence
    (8250000, 2434021, 100, 1, 1), # Modded Scaredroid | Medal of Honor
    (8250000, 2000036, 100, 1, 1), # Modded Scaredroid | Reindeer Milk
    (8250000, 4021031, 50, 1, 1), # Modded Scaredroid | Twisted Time
    (8250000, 2590004, 100, 1, 1), # Modded Scaredroid | Prototype Soul Enchanter
    (8250000, 4021032, 7500, 1, 1), # Modded Scaredroid | Mana Crystal
    (8250000, 4021034, 100, 1, 1), # Modded Scaredroid | Fine Spell Essence
    (8250000, 4021035, 100, 1, 1), # Modded Scaredroid | Grand Spell Essence
    (8250000, 4001842, 10000, 1, 1), # Modded Scaredroid | Diffusion-Line Energy Core (Grade A)
    (8250000, 4021020, 10, 1, 1), # Modded Scaredroid | Confusion Fragment
    (8250000, 4034281, 7500, 1, 1), # Modded Scaredroid | Red Chipset Piece
    (8250003, 4034284, 7500, 1, 1), # Chaseroid Red | Red Android Scope
    (8250003, 4021032, 7500, 1, 1), # Chaseroid Red | Mana Crystal
    (8250003, 2001554, 100, 1, 1), # Chaseroid Red | Sunset Dew
    (8250003, 2434021, 100, 1, 1), # Chaseroid Red | Medal of Honor
    (8250003, 4021034, 100, 1, 1), # Chaseroid Red | Fine Spell Essence
    (8250003, 4001842, 10000, 1, 1), # Chaseroid Red | Diffusion-Line Energy Core (Grade A)
    (8250003, 4021033, 100, 1, 1), # Chaseroid Red | Basic Spell Essence
    (8250003, 4021035, 100, 1, 1), # Chaseroid Red | Grand Spell Essence
    (8250003, 4021031, 50, 1, 1), # Chaseroid Red | Twisted Time
    (8250003, 2590004, 100, 1, 1), # Chaseroid Red | Prototype Soul Enchanter
    (8250003, 2000036, 100, 1, 1), # Chaseroid Red | Reindeer Milk
    (8250003, 4021020, 10, 1, 1), # Chaseroid Red | Confusion Fragment
    (5220000, 2870217, 100, 1, 1), # King Clang | King Clang Familiar
    (8250002, 4034283, 7500, 1, 1), # Modded Laseroid | Blue Chipset Piece
    (8250002, 2001554, 100, 1, 1), # Modded Laseroid | Sunset Dew
    (8250002, 4021031, 50, 1, 1), # Modded Laseroid | Twisted Time
    (8250002, 2590004, 100, 1, 1), # Modded Laseroid | Prototype Soul Enchanter
    (8250002, 4021034, 100, 1, 1), # Modded Laseroid | Fine Spell Essence
    (8250002, 2434021, 100, 1, 1), # Modded Laseroid | Medal of Honor
    (8250002, 4021020, 10, 1, 1), # Modded Laseroid | Confusion Fragment
    (8250002, 4021033, 100, 1, 1), # Modded Laseroid | Basic Spell Essence
    (8250002, 2000036, 100, 1, 1), # Modded Laseroid | Reindeer Milk
    (8250002, 4001842, 10000, 1, 1), # Modded Laseroid | Diffusion-Line Energy Core (Grade A)
    (8250002, 4021035, 100, 1, 1), # Modded Laseroid | Grand Spell Essence
    (8250002, 4021032, 7500, 1, 1), # Modded Laseroid | Mana Crystal
    (3230400, 4000127, 7500, 1, 1), # Drumming Bunny | Toy Drum
    (3230400, 2870106, 100, 1, 1), # Drumming Bunny | Drumming Bunny Familiar
    (3230405, 2870107, 100, 1, 1), # Jr. Seal | Jr. Seal Familiar
    (3230405, 4000154, 7500, 1, 1), # Jr. Seal | Toy Baby Seal
    (100101, 4000000, 7500, 1, 1), # Blue Snail | Blue Snail Shell
    (100101, 2010009, 100, 1, 1), # Blue Snail | Green Apple
    (100101, 2870001, 100, 1, 1), # Blue Snail | Blue Snail Familiar
    (100101, 2061000, 100, 1, 1), # Blue Snail | Arrow for Crossbow
    (100101, 2000003, 100, 1, 1), # Blue Snail | Blue Potion
    (100100, 2010009, 100, 1, 1), # Snail | Green Apple
    (100100, 4000019, 7500, 1, 1), # Snail | Green Snail Shell
    (100100, 2060000, 100, 1, 1), # Snail | Arrow for Bow
    (100100, 2000000, 100, 1, 1), # Snail | Red Potion
    (100100, 2870000, 100, 1, 1), # Snail | Snail Familiar
    (2600711, 4021031, 50, 1, 1), # Oblivion Monk | Twisted Time
    (2600711, 4021033, 100, 1, 1), # Oblivion Monk | Basic Spell Essence
    (2600711, 4009096, 7500, 1, 1), # Oblivion Monk | Piece of Time
    (2600711, 4033171, 7500, 1, 1), # Oblivion Monk | Shard of Recollection
    (2600711, 2000037, 100, 1, 1), # Oblivion Monk | Sunrise Dew
    (2600711, 2049401, 100, 1, 1), # Oblivion Monk | Potential Scroll
    (2600711, 2512233, 100, 1, 1), # Oblivion Monk | Advanced Defense Pill V Recipe
    (2600711, 4021020, 10, 1, 1), # Oblivion Monk | Confusion Fragment
    (2600711, 2510244, 100, 1, 1), # Oblivion Monk | Pure Gold Engine Arm Recipe
    (2600711, 2512257, 100, 1, 1), # Oblivion Monk | Advanced Magic Attack Pill IV Recipe
    (2600711, 2512237, 100, 1, 1), # Oblivion Monk | Advanced Defense Pill IX Recipe
    (2600711, 2512288, 100, 1, 1), # Oblivion Monk | Exceptional Luck Boost Potion Recipe
    (2600711, 4000454, 7500, 1, 1), # Oblivion Monk | Red Cloth
    (2600711, 2512151, 100, 1, 1), # Oblivion Monk | Advanced Strength Pill III Recipe
    (2600711, 2590004, 100, 1, 1), # Oblivion Monk | Prototype Soul Enchanter
    (2600711, 2870359, 100, 1, 1), # Oblivion Monk | Oblivion Monk Familiar
    (2600711, 4021035, 100, 1, 1), # Oblivion Monk | Grand Spell Essence
    (2600711, 4000456, 7500, 1, 1), # Oblivion Monk | Frowny Mask
    (2600711, 2512279, 100, 1, 1), # Oblivion Monk | Exceptional Insight Potion Recipe
    (2600711, 2000036, 100, 1, 1), # Oblivion Monk | Reindeer Milk
    (2600711, 2431174, 100, 1, 1), # Oblivion Monk | Medal of Honor
    (2600711, 2512245, 100, 1, 1), # Oblivion Monk | Advanced Attack Pill II Recipe
    (2600711, 4021034, 100, 1, 1), # Oblivion Monk | Fine Spell Essence
    (2600711, 4021032, 7500, 1, 1), # Oblivion Monk | Mana Crystal
    (2600710, 4009096, 7500, 1, 1), # Lilynouch | Piece of Time
    (2600710, 4021020, 10, 1, 1), # Lilynouch | Confusion Fragment
    (2600710, 4021034, 100, 1, 1), # Lilynouch | Fine Spell Essence
    (2600710, 4021033, 100, 1, 1), # Lilynouch | Basic Spell Essence
    (2600710, 4021035, 100, 1, 1), # Lilynouch | Grand Spell Essence
    (2600710, 4021031, 50, 1, 1), # Lilynouch | Twisted Time
    (2600710, 4021032, 7500, 1, 1), # Lilynouch | Mana Crystal
    (2600710, 4000461, 7500, 1, 1), # Lilynouch | Knight's Mask
    (100131, 4010006, 7500, 1, 1), # Murupa | Gold Ore
    (100131, 2010009, 100, 1, 1), # Murupa | Green Apple
    (100131, 2060000, 100, 1, 1), # Murupa | Arrow for Bow
    (100131, 4020006, 7500, 1, 1), # Murupa | Topaz Ore
    (100131, 2000003, 100, 1, 1), # Murupa | Blue Potion
    (100131, 4000494, 7500, 1, 1), # Murupa | Murupa's Furball
    (2600709, 2049401, 100, 1, 1), # Chief Qualm Guardian | Potential Scroll
    (2600709, 2870358, 100, 1, 1), # Chief Qualm Guardian | Chief Qualm Guardian Familiar
    (2600709, 2431174, 100, 1, 1), # Chief Qualm Guardian | Medal of Honor
    (2600709, 2512271, 100, 1, 1), # Chief Qualm Guardian | Legendary Blessing Potion Recipe
    (2600709, 2512278, 100, 1, 1), # Chief Qualm Guardian | Superior Insight Potion Recipe
    (2600709, 4021033, 100, 1, 1), # Chief Qualm Guardian | Basic Spell Essence
    (2600709, 2000037, 100, 1, 1), # Chief Qualm Guardian | Sunrise Dew
    (2600709, 4000459, 7500, 1, 1), # Chief Qualm Guardian | Black Armor Fragment
    (2600709, 4021020, 10, 1, 1), # Chief Qualm Guardian | Confusion Fragment
    (2600709, 2000036, 100, 1, 1), # Chief Qualm Guardian | Reindeer Milk
    (2600709, 2511053, 100, 1, 1), # Chief Qualm Guardian | Purple Wolf Pendant Recipe
    (2600709, 2512142, 100, 1, 1), # Chief Qualm Guardian | Strength Pill IV Recipe
    (2600709, 4031090, 7500, 1, 1), # Chief Qualm Guardian | Piece of Memory
    (2600709, 4000453, 7500, 1, 1), # Chief Qualm Guardian | Blue Heart
    (2600709, 4009096, 7500, 1, 1), # Chief Qualm Guardian | Piece of Time
    (2600709, 2512272, 100, 1, 1), # Chief Qualm Guardian | Intermediate Concentration Potion Recipe
    (2600709, 4021035, 100, 1, 1), # Chief Qualm Guardian | Grand Spell Essence
    (2600709, 2590004, 100, 1, 1), # Chief Qualm Guardian | Prototype Soul Enchanter
    (2600709, 2512236, 100, 1, 1), # Chief Qualm Guardian | Advanced Defense Pill VIII Recipe
    (2600709, 4021031, 50, 1, 1), # Chief Qualm Guardian | Twisted Time
    (2600709, 4021032, 7500, 1, 1), # Chief Qualm Guardian | Mana Crystal
    (2600709, 2512028, 100, 1, 1), # Chief Qualm Guardian | Luck Potion VII Recipe
    (2600709, 4021034, 100, 1, 1), # Chief Qualm Guardian | Fine Spell Essence
    (2600709, 2512286, 100, 1, 1), # Chief Qualm Guardian | Exceptional Dexterity Boost Potion Recipe
    (2600709, 2512197, 100, 1, 1), # Chief Qualm Guardian | Advanced Intelligence Pill IX Recipe
    (100130, 2060000, 100, 1, 1), # Muru | Arrow for Bow
    (100130, 2000003, 100, 1, 1), # Muru | Blue Potion
    (100130, 2000001, 100, 1, 1), # Muru | Orange Potion
    (100130, 4000493, 7500, 1, 1), # Muru | Muru's Furball
    (100130, 2010009, 100, 1, 1), # Muru | Green Apple
    (100130, 2001532, 100, 1, 1), # Muru | Apple
    (100130, 4020005, 7500, 1, 1), # Muru | Sapphire Ore
    (100130, 4010005, 7500, 1, 1), # Muru | Orihalcon Ore
    (2600708, 4021033, 100, 1, 1), # Qualm Guardian | Basic Spell Essence
    (2600708, 4021035, 100, 1, 1), # Qualm Guardian | Grand Spell Essence
    (2600708, 2512100, 100, 1, 1), # Qualm Guardian | Advanced Dexterity Potion II Recipe
    (2600708, 4009096, 7500, 1, 1), # Qualm Guardian | Piece of Time
    (2600708, 2512287, 100, 1, 1), # Qualm Guardian | Superior Luck Boost Potion Recipe
    (2600708, 2512270, 100, 1, 1), # Qualm Guardian | Exceptional Blessing Potion Recipe
    (2600708, 4021031, 50, 1, 1), # Qualm Guardian | Twisted Time
    (2600708, 2048005, 100, 1, 1), # Qualm Guardian | Scroll for Pet Equip. for Jump 10%
    (2600708, 2512273, 100, 1, 1), # Qualm Guardian | Superior Concentration Potion Recipe
    (2600708, 4021034, 100, 1, 1), # Qualm Guardian | Fine Spell Essence
    (2600708, 2431174, 100, 1, 1), # Qualm Guardian | Medal of Honor
    (2600708, 2512124, 100, 1, 1), # Qualm Guardian | Advanced Luck Potion VI Recipe
    (2600708, 2512174, 100, 1, 1), # Qualm Guardian | Advanced Dexterity Pill VI Recipe
    (2600708, 2512274, 100, 1, 1), # Qualm Guardian | Exceptional Concentration Potion Recipe
    (2600708, 4000452, 7500, 1, 1), # Qualm Guardian | Blue Helmet
    (2600708, 2512280, 100, 1, 1), # Qualm Guardian | Legendary Insight Potion Recipe
    (2600708, 4021032, 7500, 1, 1), # Qualm Guardian | Mana Crystal
    (2600708, 4021020, 10, 1, 1), # Qualm Guardian | Confusion Fragment
    (2600708, 2870357, 100, 1, 1), # Qualm Guardian | Qualm Guardian Familiar
    (2600708, 2000036, 100, 1, 1), # Qualm Guardian | Reindeer Milk
    (2600708, 4031090, 7500, 1, 1), # Qualm Guardian | Piece of Memory
    (2600708, 2512090, 100, 1, 1), # Qualm Guardian | Advanced Strength Potion II Recipe
    (2600708, 2510310, 100, 1, 1), # Qualm Guardian | Abyss Tail Recipe
    (2600708, 2000037, 100, 1, 1), # Qualm Guardian | Sunrise Dew
    (2600708, 4000459, 7500, 1, 1), # Qualm Guardian | Black Armor Fragment
    (2600708, 2510240, 100, 1, 1), # Qualm Guardian | Gold Heart Recipe
    (2600708, 2590004, 100, 1, 1), # Qualm Guardian | Prototype Soul Enchanter
    (100133, 4000496, 7500, 1, 1), # Murumuru | Murumuru's Furball
    (100133, 4010001, 7500, 1, 1), # Murumuru | Steel Ore
    (100133, 4020003, 7500, 1, 1), # Murumuru | Emerald Ore
    (100133, 2000000, 100, 1, 1), # Murumuru | Red Potion
    (100133, 2060000, 100, 1, 1), # Murumuru | Arrow for Bow
    (100133, 2010009, 100, 1, 1), # Murumuru | Green Apple
    (2600707, 2512279, 100, 1, 1), # Qualm Monk Trainee | Exceptional Insight Potion Recipe
    (2600707, 4000450, 7500, 1, 1), # Qualm Monk Trainee | Blue Cone Hat
    (2600707, 4021035, 100, 1, 1), # Qualm Monk Trainee | Grand Spell Essence
    (2600707, 2512280, 100, 1, 1), # Qualm Monk Trainee | Legendary Insight Potion Recipe
    (2600707, 2510310, 100, 1, 1), # Qualm Monk Trainee | Abyss Tail Recipe
    (2600707, 4000451, 7500, 1, 1), # Qualm Monk Trainee | Neutral Mask
    (2600707, 2000036, 100, 1, 1), # Qualm Monk Trainee | Reindeer Milk
    (2600707, 2590004, 100, 1, 1), # Qualm Monk Trainee | Prototype Soul Enchanter
    (2600707, 2512273, 100, 1, 1), # Qualm Monk Trainee | Superior Concentration Potion Recipe
    (2600707, 4021034, 100, 1, 1), # Qualm Monk Trainee | Fine Spell Essence
    (2600707, 2512197, 100, 1, 1), # Qualm Monk Trainee | Advanced Intelligence Pill IX Recipe
    (2600707, 2512248, 100, 1, 1), # Qualm Monk Trainee | Advanced Attack Pill V Recipe
    (2600707, 2000037, 100, 1, 1), # Qualm Monk Trainee | Sunrise Dew
    (2600707, 4021031, 50, 1, 1), # Qualm Monk Trainee | Twisted Time
    (2600707, 2511078, 100, 1, 1), # Qualm Monk Trainee | Luck Ring VII Recipe
    (2600707, 2512287, 100, 1, 1), # Qualm Monk Trainee | Superior Luck Boost Potion Recipe
    (2600707, 4021032, 7500, 1, 1), # Qualm Monk Trainee | Mana Crystal
    (2600707, 2431174, 100, 1, 1), # Qualm Monk Trainee | Medal of Honor
    (2600707, 4033171, 7500, 1, 1), # Qualm Monk Trainee | Shard of Recollection
    (2600707, 2512217, 100, 1, 1), # Qualm Monk Trainee | Advanced Luck Pill IX Recipe
    (2600707, 2512176, 100, 1, 1), # Qualm Monk Trainee | Advanced Dexterity Pill VIII Recipe
    (2600707, 4021033, 100, 1, 1), # Qualm Monk Trainee | Basic Spell Essence
    (2600707, 4009096, 7500, 1, 1), # Qualm Monk Trainee | Piece of Time
    (2600707, 2870356, 100, 1, 1), # Qualm Monk Trainee | Qualm Monk Trainee Familiar
    (2600707, 4021020, 10, 1, 1), # Qualm Monk Trainee | Confusion Fragment
    (100132, 2000003, 100, 1, 1), # Murupia | Blue Potion
    (100132, 4000495, 7500, 1, 1), # Murupia | Murupia's Furball
    (100132, 2010009, 100, 1, 1), # Murupia | Green Apple
    (100132, 4010000, 7500, 1, 1), # Murupia | Bronze Ore
    (100132, 2061000, 100, 1, 1), # Murupia | Arrow for Crossbow
    (100132, 4020007, 7500, 1, 1), # Murupia | Diamond Ore
    (2600706, 2512269, 100, 1, 1), # Qualm Monk | Superior Blessing Potion Recipe
    (2600706, 4021020, 10, 1, 1), # Qualm Monk | Confusion Fragment
    (2600706, 4021031, 50, 1, 1), # Qualm Monk | Twisted Time
    (2600706, 4021035, 100, 1, 1), # Qualm Monk | Grand Spell Essence
    (2600706, 2512268, 100, 1, 1), # Qualm Monk | Legendary Hero Potion Recipe
    (2600706, 2512280, 100, 1, 1), # Qualm Monk | Legendary Insight Potion Recipe
    (2600706, 4021034, 100, 1, 1), # Qualm Monk | Fine Spell Essence
    (2600706, 4021033, 100, 1, 1), # Qualm Monk | Basic Spell Essence
    (2600706, 2590004, 100, 1, 1), # Qualm Monk | Prototype Soul Enchanter
    (2600706, 2870355, 100, 1, 1), # Qualm Monk | Qualm Monk Familiar
    (2600706, 4000451, 7500, 1, 1), # Qualm Monk | Neutral Mask
    (2600706, 2000036, 100, 1, 1), # Qualm Monk | Reindeer Milk
    (2600706, 2512274, 100, 1, 1), # Qualm Monk | Exceptional Concentration Potion Recipe
    (2600706, 4009096, 7500, 1, 1), # Qualm Monk | Piece of Time
    (2600706, 4000449, 7500, 1, 1), # Qualm Monk | Blue Cloth
    (2600706, 4021032, 7500, 1, 1), # Qualm Monk | Mana Crystal
    (2600706, 2431174, 100, 1, 1), # Qualm Monk | Medal of Honor
    (2600706, 2004008, 100, 1, 1), # Qualm Monk | Strength Potion IX
    (2600706, 4033171, 7500, 1, 1), # Qualm Monk | Shard of Recollection
    (2600706, 2049401, 100, 1, 1), # Qualm Monk | Potential Scroll
    (2600706, 2000037, 100, 1, 1), # Qualm Monk | Sunrise Dew
    (2600706, 2512277, 100, 1, 1), # Qualm Monk | Legendary Fortitude Potion Recipe
    (2600705, 4021033, 100, 1, 1), # Dodo | Basic Spell Essence
    (2600705, 4021032, 7500, 1, 1), # Dodo | Mana Crystal
    (2600705, 4000460, 7500, 1, 1), # Dodo | Whale's Helmet
    (2600705, 4021020, 10, 1, 1), # Dodo | Confusion Fragment
    (2600705, 4009096, 7500, 1, 1), # Dodo | Piece of Time
    (2600705, 4021031, 50, 1, 1), # Dodo | Twisted Time
    (2600705, 4021035, 100, 1, 1), # Dodo | Grand Spell Essence
    (2600705, 4021034, 100, 1, 1), # Dodo | Fine Spell Essence
    (100134, 4000497, 7500, 1, 1), # Murukun | Murukun's Furball
    (100134, 2010009, 100, 1, 1), # Murukun | Green Apple
    (100134, 2000001, 100, 1, 1), # Murukun | Orange Potion
    (100134, 4010002, 7500, 1, 1), # Murukun | Mithril Ore
    (100134, 2060000, 100, 1, 1), # Murukun | Arrow for Bow
    (100134, 2000000, 100, 1, 1), # Murukun | Red Potion
    (100134, 4020002, 7500, 1, 1), # Murukun | AquaMarine Ore
    (2600704, 4000459, 7500, 1, 1), # Chief Memory Guardian | Black Armor Fragment
    (2600704, 2870354, 100, 1, 1), # Chief Memory Guardian | Chief Memory Guardian Familiar
    (2600704, 4021020, 10, 1, 1), # Chief Memory Guardian | Confusion Fragment
    (2600704, 2070009, 100, 1, 1), # Chief Memory Guardian | Wooden Top
    (2600704, 2000036, 100, 1, 1), # Chief Memory Guardian | Reindeer Milk
    (2600704, 2049401, 100, 1, 1), # Chief Memory Guardian | Potential Scroll
    (2600704, 4009096, 7500, 1, 1), # Chief Memory Guardian | Piece of Time
    (2600704, 2000037, 100, 1, 1), # Chief Memory Guardian | Sunrise Dew
    (2600704, 2512279, 100, 1, 1), # Chief Memory Guardian | Exceptional Insight Potion Recipe
    (2600704, 4000448, 7500, 1, 1), # Chief Memory Guardian | Green Heart
    (2600704, 2512286, 100, 1, 1), # Chief Memory Guardian | Exceptional Dexterity Boost Potion Recipe
    (2600704, 2512241, 100, 1, 1), # Chief Memory Guardian | Attack Pill III Recipe
    (2600704, 4021034, 100, 1, 1), # Chief Memory Guardian | Fine Spell Essence
    (2600704, 4021032, 7500, 1, 1), # Chief Memory Guardian | Mana Crystal
    (2600704, 4021035, 100, 1, 1), # Chief Memory Guardian | Grand Spell Essence
    (2600704, 2590004, 100, 1, 1), # Chief Memory Guardian | Prototype Soul Enchanter
    (2600704, 2431174, 100, 1, 1), # Chief Memory Guardian | Medal of Honor
    (2600704, 4021033, 100, 1, 1), # Chief Memory Guardian | Basic Spell Essence
    (2600704, 4021031, 50, 1, 1), # Chief Memory Guardian | Twisted Time
    (2600715, 4021020, 10, 1, 1), # Lyka | Confusion Fragment
    (2600715, 4310029, 7500, 1, 1), # Lyka | Crusader Coins
    (2600715, 4021031, 50, 1, 1), # Lyka | Twisted Time
    (2600715, 4000462, 7500, 1, 1), # Lyka | Guardian's Horn
    (2600715, 4021035, 100, 1, 1), # Lyka | Grand Spell Essence
    (2600715, 4021033, 100, 1, 1), # Lyka | Basic Spell Essence
    (2600715, 4021034, 100, 1, 1), # Lyka | Fine Spell Essence
    (2600715, 4021032, 7500, 1, 1), # Lyka | Mana Crystal
    (2600715, 4009096, 7500, 1, 1), # Lyka | Piece of Time
    (2600714, 4021032, 7500, 1, 1), # Chief Oblivion Guardian | Mana Crystal
    (2600714, 4021035, 100, 1, 1), # Chief Oblivion Guardian | Grand Spell Essence
    (2600714, 2512280, 100, 1, 1), # Chief Oblivion Guardian | Legendary Insight Potion Recipe
    (2600714, 4021020, 10, 1, 1), # Chief Oblivion Guardian | Confusion Fragment
    (2600714, 2512246, 100, 1, 1), # Chief Oblivion Guardian | Advanced Attack Pill III Recipe
    (2600714, 2512274, 100, 1, 1), # Chief Oblivion Guardian | Exceptional Concentration Potion Recipe
    (2600714, 2512273, 100, 1, 1), # Chief Oblivion Guardian | Superior Concentration Potion Recipe
    (2600714, 2431174, 100, 1, 1), # Chief Oblivion Guardian | Medal of Honor
    (2600714, 2511077, 100, 1, 1), # Chief Oblivion Guardian | Dexterity Ring VII Recipe
    (2600714, 2512288, 100, 1, 1), # Chief Oblivion Guardian | Exceptional Luck Boost Potion Recipe
    (2600714, 2870362, 100, 1, 1), # Chief Oblivion Guardian | Chief Oblivion Guardian Familiar
    (2600714, 4021033, 100, 1, 1), # Chief Oblivion Guardian | Basic Spell Essence
    (2600714, 2511015, 100, 1, 1), # Chief Oblivion Guardian | Shiny Red Thief Symbol Recipe
    (2600714, 4021031, 50, 1, 1), # Chief Oblivion Guardian | Twisted Time
    (2600714, 4009096, 7500, 1, 1), # Chief Oblivion Guardian | Piece of Time
    (2600714, 2000037, 100, 1, 1), # Chief Oblivion Guardian | Sunrise Dew
    (2600714, 2049401, 100, 1, 1), # Chief Oblivion Guardian | Potential Scroll
    (2600714, 2590004, 100, 1, 1), # Chief Oblivion Guardian | Prototype Soul Enchanter
    (2600714, 2512235, 100, 1, 1), # Chief Oblivion Guardian | Advanced Defense Pill VII Recipe
    (2600714, 2512036, 100, 1, 1), # Chief Oblivion Guardian | Attack Potion V Recipe
    (2600714, 2000036, 100, 1, 1), # Chief Oblivion Guardian | Reindeer Milk
    (2600714, 2512286, 100, 1, 1), # Chief Oblivion Guardian | Exceptional Dexterity Boost Potion Recipe
    (2600714, 2512198, 100, 1, 1), # Chief Oblivion Guardian | Advanced Intelligence Pill X Recipe
    (2600714, 4021034, 100, 1, 1), # Chief Oblivion Guardian | Fine Spell Essence
    (2600714, 4000459, 7500, 1, 1), # Chief Oblivion Guardian | Black Armor Fragment
    (2600714, 4000458, 7500, 1, 1), # Chief Oblivion Guardian | Red Heart
    (6150000, 2870246, 100, 1, 1), # Guard Robot | Guard Robot Familiar
    (6150000, 4000608, 7500, 1, 1), # Guard Robot | Guard Robot Baton
    (2600713, 4021031, 50, 1, 1), # Oblivion Guard | Twisted Time
    (2600713, 4021033, 100, 1, 1), # Oblivion Guard | Basic Spell Essence
    (2600713, 2590004, 100, 1, 1), # Oblivion Guard | Prototype Soul Enchanter
    (2600713, 2049401, 100, 1, 1), # Oblivion Guard | Potential Scroll
    (2600713, 2000036, 100, 1, 1), # Oblivion Guard | Reindeer Milk
    (2600713, 4009096, 7500, 1, 1), # Oblivion Guard | Piece of Time
    (2600713, 2512035, 100, 1, 1), # Oblivion Guard | Attack Potion IV Recipe
    (2600713, 2512214, 100, 1, 1), # Oblivion Guard | Advanced Luck Pill VI Recipe
    (2600713, 2512283, 100, 1, 1), # Oblivion Guard | Superior Intelligence Boost Potion Recipe
    (2600713, 4021020, 10, 1, 1), # Oblivion Guard | Confusion Fragment
    (2600713, 2512278, 100, 1, 1), # Oblivion Guard | Superior Insight Potion Recipe
    (2600713, 4021034, 100, 1, 1), # Oblivion Guard | Fine Spell Essence
    (2600713, 2512020, 100, 1, 1), # Oblivion Guard | Intelligence Potion VII Recipe
    (2600713, 2048005, 100, 1, 1), # Oblivion Guard | Scroll for Pet Equip. for Jump 10%
    (2600713, 4021032, 7500, 1, 1), # Oblivion Guard | Mana Crystal
    (2600713, 2512268, 100, 1, 1), # Oblivion Guard | Legendary Hero Potion Recipe
    (2600713, 2512281, 100, 1, 1), # Oblivion Guard | Superior Strength Boost Potion Recipe
    (2600713, 2512105, 100, 1, 1), # Oblivion Guard | Advanced Dexterity Potion VII Recipe
    (2600713, 4021035, 100, 1, 1), # Oblivion Guard | Grand Spell Essence
    (2600713, 2512176, 100, 1, 1), # Oblivion Guard | Advanced Dexterity Pill VIII Recipe
    (2600713, 4000457, 7500, 1, 1), # Oblivion Guard | Red Helmet
    (2600713, 2512113, 100, 1, 1), # Oblivion Guard | Advanced Intelligence Potion V Recipe
    (2600713, 2870361, 100, 1, 1), # Oblivion Guard | Oblivion Guardian Familiar
    (2600713, 4000459, 7500, 1, 1), # Oblivion Guard | Black Armor Fragment
    (2600713, 2431174, 100, 1, 1), # Oblivion Guard | Medal of Honor
    (2600713, 2000037, 100, 1, 1), # Oblivion Guard | Sunrise Dew
    (2600712, 2590004, 100, 1, 1), # Oblivion Monk Trainee | Prototype Soul Enchanter
    (2600712, 4021035, 100, 1, 1), # Oblivion Monk Trainee | Grand Spell Essence
    (2600712, 2000036, 100, 1, 1), # Oblivion Monk Trainee | Reindeer Milk
    (2600712, 2048003, 100, 1, 1), # Oblivion Monk Trainee | Scroll for Pet Equip. for Jump 100%
    (2600712, 2510310, 100, 1, 1), # Oblivion Monk Trainee | Abyss Tail Recipe
    (2600712, 4021031, 50, 1, 1), # Oblivion Monk Trainee | Twisted Time
    (2600712, 2510168, 100, 1, 1), # Oblivion Monk Trainee | Titanium Arrow for Crossbow Recipe
    (2600712, 4000455, 7500, 1, 1), # Oblivion Monk Trainee | Red Cone Hat
    (2600712, 4000456, 7500, 1, 1), # Oblivion Monk Trainee | Frowny Mask
    (2600712, 2049401, 100, 1, 1), # Oblivion Monk Trainee | Potential Scroll
    (2600712, 4021032, 7500, 1, 1), # Oblivion Monk Trainee | Mana Crystal
    (2600712, 2431174, 100, 1, 1), # Oblivion Monk Trainee | Medal of Honor
    (2600712, 4021020, 10, 1, 1), # Oblivion Monk Trainee | Confusion Fragment
    (2600712, 2512283, 100, 1, 1), # Oblivion Monk Trainee | Superior Intelligence Boost Potion Recipe
    (2600712, 2512267, 100, 1, 1), # Oblivion Monk Trainee | Exceptional Hero Potion Recipe
    (2600712, 2512288, 100, 1, 1), # Oblivion Monk Trainee | Exceptional Luck Boost Potion Recipe
    (2600712, 4009096, 7500, 1, 1), # Oblivion Monk Trainee | Piece of Time
    (2600712, 2000037, 100, 1, 1), # Oblivion Monk Trainee | Sunrise Dew
    (2600712, 2512281, 100, 1, 1), # Oblivion Monk Trainee | Superior Strength Boost Potion Recipe
    (2600712, 2870360, 100, 1, 1), # Oblivion Monk Trainee | Oblivion Monk Trainee Familiar
    (2600712, 4021033, 100, 1, 1), # Oblivion Monk Trainee | Basic Spell Essence
    (2600712, 4021034, 100, 1, 1), # Oblivion Monk Trainee | Fine Spell Essence
    (2600712, 2070009, 100, 1, 1), # Oblivion Monk Trainee | Wooden Top
    (2600712, 2512270, 100, 1, 1), # Oblivion Monk Trainee | Exceptional Blessing Potion Recipe
    (5130108, 4021033, 100, 1, 1), # [*]Miner Zombie | Basic Spell Essence
    (5130108, 2512193, 100, 1, 1), # [*]Miner Zombie | Advanced Intelligence Pill V Recipe
    (5130108, 4000069, 7500, 1, 1), # [*]Miner Zombie | Zombie's Lost Tooth
    (5130108, 4021032, 7500, 1, 1), # [*]Miner Zombie | Mana Crystal
    (5130108, 2512115, 100, 1, 1), # [*]Miner Zombie | Advanced Intelligence Potion VII Recipe
    (5130108, 2512269, 100, 1, 1), # [*]Miner Zombie | Superior Blessing Potion Recipe
    (5130108, 2001528, 100, 1, 1), # [*]Miner Zombie | Melting Cheese
    (5130108, 2431174, 100, 1, 1), # [*]Miner Zombie | Medal of Honor
    (5130108, 2512198, 100, 1, 1), # [*]Miner Zombie | Advanced Intelligence Pill X Recipe
    (5130108, 2000037, 100, 1, 1), # [*]Miner Zombie | Sunrise Dew
    (5130108, 2512154, 100, 1, 1), # [*]Miner Zombie | Advanced Strength Pill VI Recipe
    (5130108, 4021031, 50, 1, 1), # [*]Miner Zombie | Twisted Time
    (5130108, 2512286, 100, 1, 1), # [*]Miner Zombie | Exceptional Dexterity Boost Potion Recipe
    (5130108, 2870210, 100, 1, 1), # [*]Miner Zombie | [*]Miner Zombie Familiar
    (5130108, 2512041, 100, 1, 1), # [*]Miner Zombie | Magic Attack Potion V Recipe
    (5130108, 2512132, 100, 1, 1), # [*]Miner Zombie | Advanced Attack Potion IV Recipe
    (5130108, 2590004, 100, 1, 1), # [*]Miner Zombie | Prototype Soul Enchanter
    (5130108, 4034183, 7500, 1, 1), # [*]Miner Zombie | Broken Star Fragment
    (5130108, 2512271, 100, 1, 1), # [*]Miner Zombie | Legendary Blessing Potion Recipe
    (5130108, 2512276, 100, 1, 1), # [*]Miner Zombie | Exceptional Fortitude Potion Recipe
    (5130108, 4021034, 100, 1, 1), # [*]Miner Zombie | Fine Spell Essence
    (5130108, 4000082, 7500, 1, 1), # [*]Miner Zombie | Zombie's Lost Gold Tooth
    (5130108, 4021035, 100, 1, 1), # [*]Miner Zombie | Grand Spell Essence
    (5130107, 4000069, 7500, 1, 1), # Coolie Zombie | Zombie's Lost Tooth
    (5130107, 4021031, 50, 1, 1), # Coolie Zombie | Twisted Time
    (5130107, 4021032, 7500, 1, 1), # Coolie Zombie | Mana Crystal
    (5130107, 4021034, 100, 1, 1), # Coolie Zombie | Fine Spell Essence
    (5130107, 4021033, 100, 1, 1), # Coolie Zombie | Basic Spell Essence
    (5130107, 4021035, 100, 1, 1), # Coolie Zombie | Grand Spell Essence
    (5130104, 4032646, 7500, 1, 1), # Hector | Shammos's Diary Page 9
    (5130104, 2870207, 100, 1, 1), # Hector | Hector Familiar
    (5130104, 4031054, 7500, 1, 1), # Hector | Old Piece of Map
    (5130104, 4031218, 7500, 1, 1), # Hector | The Contract of Darkness
    (5130104, 4000051, 7500, 1, 1), # Hector | Hector Tail
    (5130104, 4032643, 7500, 1, 1), # Hector | Shammos's Diary Page 6
    (5130104, 4032645, 7500, 1, 1), # Hector | Shammos's Diary Page 8
    (5130104, 4032644, 7500, 1, 1), # Hector | Shammos's Diary Page 7
    (5130104, 4032642, 7500, 1, 1), # Hector | Shammos's Diary Page 5
    (5130105, 2870208, 100, 1, 1), # Dark Jr. Yeti | Dark Jr. Yeti Familiar
    (5130105, 4000055, 7500, 1, 1), # Dark Jr. Yeti | Dark Jr. Yeti Skin
    (5130103, 2870206, 100, 1, 1), # Croco | Croco Familiar
    (5130103, 4000033, 7500, 1, 1), # Croco | Croco Skin
    (3300104, 4034110, 7500, 1, 1), # Warm Viking | Viking's Helm
    (2230114, 2000001, 100, 1, 1), # Muddy Swamp Monster | Orange Potion
    (2230114, 2040614, 100, 1, 1), # Muddy Swamp Monster | Scroll for Bottomwear for DEX 100%
    (2230114, 2000003, 100, 1, 1), # Muddy Swamp Monster | Blue Potion
    (2230114, 2870057, 100, 1, 1), # Muddy Swamp Monster | Muddy Swamp Monster Familiar
    (2230114, 2060000, 100, 1, 1), # Muddy Swamp Monster | Arrow for Bow
    (2230114, 2000006, 100, 1, 1), # Muddy Swamp Monster | Mana Elixir
    (2230114, 2048003, 100, 1, 1), # Muddy Swamp Monster | Scroll for Pet Equip. for Jump 100%
    (2230114, 4000623, 7500, 1, 1), # Muddy Swamp Monster | Mud Clump
    (2230114, 2043600, 100, 1, 1), # Muddy Swamp Monster | Scroll for Cane for ATT 100%
    (2230114, 2043300, 100, 1, 1), # Muddy Swamp Monster | Scroll for Dagger for ATT 100%
    (2230114, 2000002, 100, 1, 1), # Muddy Swamp Monster | White Potion
    (2230114, 2040900, 100, 1, 1), # Muddy Swamp Monster | Scroll for Shield for DEF 100%
    (2230112, 2000000, 100, 1, 1), # Terrified Wild Boar | Red Potion
    (2230112, 2000002, 100, 1, 1), # Terrified Wild Boar | White Potion
    (2230112, 2040417, 100, 1, 1), # Terrified Wild Boar | Scroll for Topwear for STR 100%
    (2230112, 2000003, 100, 1, 1), # Terrified Wild Boar | Blue Potion
    (2230112, 2043200, 100, 1, 1), # Terrified Wild Boar | Scroll for One-Handed BW for ATT 100%
    (2230112, 2044700, 100, 1, 1), # Terrified Wild Boar | Scroll for Claw for ATT 100%
    (2230112, 4020004, 7500, 1, 1), # Terrified Wild Boar | Opal Ore
    (2230112, 4010003, 7500, 1, 1), # Terrified Wild Boar | Adamantium Ore
    (2230112, 2870055, 100, 1, 1), # Terrified Wild Boar | Terrified Wild Boar Familiar
    (2230112, 4000020, 7500, 1, 1), # Terrified Wild Boar | Wild Boar Tooth
    (2230112, 4003004, 7500, 1, 1), # Terrified Wild Boar | Stiff Feather
    (2230113, 2870056, 100, 1, 1), # Surgeon Eye | Surgeon Eye Familiar
    (2230113, 2000001, 100, 1, 1), # Surgeon Eye | Orange Potion
    (2230113, 2044200, 100, 1, 1), # Surgeon Eye | Scroll for Two-handed BW for ATT 100%
    (2230113, 2060000, 100, 1, 1), # Surgeon Eye | Arrow for Bow
    (2230113, 2000006, 100, 1, 1), # Surgeon Eye | Mana Elixir
    (2230113, 2044210, 100, 1, 1), # Surgeon Eye | Scroll for Two-Handed BW for Accuracy 100%
    (2230113, 2061000, 100, 1, 1), # Surgeon Eye | Arrow for Crossbow
    (2230113, 2040926, 100, 1, 1), # Surgeon Eye | Scroll for Shield for HP 100%
    (2230113, 2070010, 100, 1, 1), # Surgeon Eye | Icicle
    (2230113, 2330000, 100, 1, 1), # Surgeon Eye | Bullet
    (2230113, 2000002, 100, 1, 1), # Surgeon Eye | White Potion
    (2230113, 4000621, 7500, 1, 1), # Surgeon Eye | Surgeon Eye Tail
    (2230113, 2040929, 100, 1, 1), # Surgeon Eye | Scroll for Shield for STR 100%
    (3210100, 2431174, 100, 1, 1), # Fire Boar | Medal of Honor
    (3210100, 2870078, 100, 1, 1), # Fire Boar | Fire Boar Familiar
    (3210100, 2001527, 100, 1, 1), # Fire Boar | Unagi
    (3210100, 2001526, 100, 1, 1), # Fire Boar | Pure Water
    (3210100, 4000024, 7500, 1, 1), # Fire Boar | Fire Boar's Tooth
    (8140600, 2870324, 100, 1, 1), # Bone Fish | Bone Fish Familiar
    (8140600, 4021034, 100, 1, 1), # Bone Fish | Fine Spell Essence
    (8140600, 2434021, 100, 1, 1), # Bone Fish | Medal of Honor
    (8140600, 2000037, 100, 1, 1), # Bone Fish | Sunrise Dew
    (8140600, 4021032, 7500, 1, 1), # Bone Fish | Mana Crystal
    (8140600, 4000182, 7500, 1, 1), # Bone Fish | Lime Powder Bottle
    (8140600, 2590004, 100, 1, 1), # Bone Fish | Prototype Soul Enchanter
    (8140600, 4021035, 100, 1, 1), # Bone Fish | Grand Spell Essence
    (8140600, 4031256, 7500, 1, 1), # Bone Fish | Cold Tear
    (8140600, 4021033, 100, 1, 1), # Bone Fish | Basic Spell Essence
    (8140600, 2001528, 100, 1, 1), # Bone Fish | Melting Cheese
    (8140600, 4021031, 50, 1, 1), # Bone Fish | Twisted Time
    (8920003, 4310065, 7500, 1, 1), # Chaos Crimson Queen | Chaos Yggdrasil Rune
    (8920003, 4310029, 7500, 1, 1), # Chaos Crimson Queen | Crusader Coins
    (8920003, 4310064, 7500, 1, 1), # Chaos Crimson Queen | Yggdrasil Rune
    (8920003, 2434586, 100, 1, 1), # Chaos Crimson Queen | Piece of Anguish
    (8920003, 2000005, 100, 1, 1), # Chaos Crimson Queen | Power Elixir
    (8920003, 2433103, 100, 1, 1), # Chaos Crimson Queen | Boss Medal of Honor
    (8920003, 2433591, 3000, 1, 1), # Chaos Crimson Queen | Crimson Queen's Soul Shard
    (8920003, 3010514, 100, 1, 1), # Chaos Crimson Queen | Crimson Queen's Throne
    (8800202, 4001685, 7500, 1, 1), # Red Goblin | Well-Kept Keepsake
    (8800202, 4021031, 50, 1, 1), # Red Goblin | Twisted Time
    (8800202, 4021035, 100, 1, 1), # Red Goblin | Grand Spell Essence
    (8800202, 4021034, 100, 1, 1), # Red Goblin | Fine Spell Essence
    (8800202, 4001687, 7500, 1, 1), # Red Goblin | Thought-Provoking Keepsake
    (8800202, 4000563, 7500, 1, 1), # Red Goblin | Red Goblin Axe
    (8800202, 4021033, 100, 1, 1), # Red Goblin | Basic Spell Essence
    (8800202, 4021032, 7500, 1, 1), # Red Goblin | Mana Crystal
    (8800202, 4033176, 7500, 1, 1), # Red Goblin | Sunburst
    (8800202, 4001686, 7500, 1, 1), # Red Goblin | Plain Keepsake
    (8290099, 4001877, 7500, 1, 1), # ? | Damaged Black Heart
    (8290099, 4310029, 7500, 1, 1), # ? | Crusader Coins
    (8800203, 4001686, 7500, 1, 1), # Stone Goblin | Plain Keepsake
    (8800203, 4021032, 7500, 1, 1), # Stone Goblin | Mana Crystal
    (8800203, 4021034, 100, 1, 1), # Stone Goblin | Fine Spell Essence
    (8800203, 4021033, 100, 1, 1), # Stone Goblin | Basic Spell Essence
    (8800203, 4033176, 7500, 1, 1), # Stone Goblin | Sunburst
    (8800203, 4000565, 7500, 1, 1), # Stone Goblin | Stone Goblin's Red Underwear
    (8800203, 4001685, 7500, 1, 1), # Stone Goblin | Well-Kept Keepsake
    (8800203, 4001687, 7500, 1, 1), # Stone Goblin | Thought-Provoking Keepsake
    (8800203, 4021035, 100, 1, 1), # Stone Goblin | Grand Spell Essence
    (8800203, 4021031, 50, 1, 1), # Stone Goblin | Twisted Time
    (8800201, 4021035, 100, 1, 1), # Blue Goblin | Grand Spell Essence
    (8800201, 4000564, 7500, 1, 1), # Blue Goblin | Blue Goblin Crown
    (8800201, 4001685, 7500, 1, 1), # Blue Goblin | Well-Kept Keepsake
    (8800201, 4021034, 100, 1, 1), # Blue Goblin | Fine Spell Essence
    (8800201, 4001686, 7500, 1, 1), # Blue Goblin | Plain Keepsake
    (8800201, 4021032, 7500, 1, 1), # Blue Goblin | Mana Crystal
    (8800201, 4001687, 7500, 1, 1), # Blue Goblin | Thought-Provoking Keepsake
    (8800201, 4021031, 50, 1, 1), # Blue Goblin | Twisted Time
    (8800201, 4021033, 100, 1, 1), # Blue Goblin | Basic Spell Essence
    (8800201, 4033176, 7500, 1, 1), # Blue Goblin | Sunburst
    (2230131, 4010003, 7500, 1, 1), # Annoyed Zombie Mushroom | Adamantium Ore
    (2230131, 2000000, 100, 1, 1); # Annoyed Zombie Mushroom | Red Potion

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (2230131, 4020003, 7500, 1, 1), # Annoyed Zombie Mushroom | Emerald Ore
    (2230131, 2045302, 100, 1, 1), # Annoyed Zombie Mushroom | Scroll for Hand Cannon for ATT 10%
    (2230131, 2040538, 100, 1, 1), # Annoyed Zombie Mushroom | Scroll for Overall Armor for DEX 100%
    (2230131, 2044302, 100, 1, 1), # Annoyed Zombie Mushroom | Scroll for Spear for ATT 10%
    (2230131, 4010002, 7500, 1, 1), # Annoyed Zombie Mushroom | Mithril Ore
    (2230131, 2049100, 100, 1, 1), # Annoyed Zombie Mushroom | Chaos Scroll 60%
    (2230131, 2040530, 100, 1, 1), # Annoyed Zombie Mushroom | Scroll for Overall for STR 100%
    (2230131, 4000047, 7500, 1, 1), # Annoyed Zombie Mushroom | Mouse
    (2230131, 2870058, 100, 1, 1), # Annoyed Zombie Mushroom | Annoyed Zombie Mushroom Familiar
    (2230131, 2000001, 100, 1, 1), # Annoyed Zombie Mushroom | Orange Potion
    (2230131, 4000008, 7500, 1, 1), # Annoyed Zombie Mushroom | Charm of the Undead
    (8620003, 4021034, 100, 1, 1), # Pillaging Wild Boar | Fine Spell Essence
    (8620003, 4021033, 100, 1, 1), # Pillaging Wild Boar | Basic Spell Essence
    (8620003, 4021035, 100, 1, 1), # Pillaging Wild Boar | Grand Spell Essence
    (8620003, 4033750, 7500, 1, 1), # Pillaging Wild Boar | Strong Wild Boar Tooth
    (8620003, 2590004, 100, 1, 1), # Pillaging Wild Boar | Prototype Soul Enchanter
    (8620003, 4033733, 7500, 1, 1), # Pillaging Wild Boar | Slumbering Fragment
    (8620003, 2000036, 100, 1, 1), # Pillaging Wild Boar | Reindeer Milk
    (8620003, 4033727, 7500, 1, 1), # Pillaging Wild Boar | Giant Porkchop
    (8620003, 4021020, 10, 1, 1), # Pillaging Wild Boar | Confusion Fragment
    (8620003, 4000829, 7500, 1, 1), # Pillaging Wild Boar | Mutated Tooth
    (8620003, 4021032, 7500, 1, 1), # Pillaging Wild Boar | Mana Crystal
    (8620003, 4021031, 50, 1, 1), # Pillaging Wild Boar | Twisted Time
    (8620003, 2434021, 100, 1, 1), # Pillaging Wild Boar | Medal of Honor
    (8620003, 4033732, 7500, 1, 1), # Pillaging Wild Boar | Lucid Matter
    (8620003, 2001554, 100, 1, 1), # Pillaging Wild Boar | Sunset Dew
    (8620002, 4000826, 7500, 1, 1), # Swollen Axe Stump | Giant Tree Branch
    (8620002, 4021034, 100, 1, 1), # Swollen Axe Stump | Fine Spell Essence
    (8620002, 4021031, 50, 1, 1), # Swollen Axe Stump | Twisted Time
    (8620002, 4033733, 7500, 1, 1), # Swollen Axe Stump | Slumbering Fragment
    (8620002, 2590004, 100, 1, 1), # Swollen Axe Stump | Prototype Soul Enchanter
    (8620002, 2001554, 100, 1, 1), # Swollen Axe Stump | Sunset Dew
    (8620002, 4021020, 10, 1, 1), # Swollen Axe Stump | Confusion Fragment
    (8620002, 2000036, 100, 1, 1), # Swollen Axe Stump | Reindeer Milk
    (8620002, 4021033, 100, 1, 1), # Swollen Axe Stump | Basic Spell Essence
    (8620002, 2434021, 100, 1, 1), # Swollen Axe Stump | Medal of Honor
    (8620002, 4021032, 7500, 1, 1), # Swollen Axe Stump | Mana Crystal
    (8620002, 4021035, 100, 1, 1), # Swollen Axe Stump | Grand Spell Essence
    (8620002, 4033726, 7500, 1, 1), # Swollen Axe Stump | Fresh Sap
    (8620002, 4033732, 7500, 1, 1), # Swollen Axe Stump | Lucid Matter
    (8620001, 2590004, 100, 1, 1), # Swollen Dark Stump | Prototype Soul Enchanter
    (8620001, 4021034, 100, 1, 1), # Swollen Dark Stump | Fine Spell Essence
    (8620001, 2001554, 100, 1, 1), # Swollen Dark Stump | Sunset Dew
    (8620001, 4000827, 7500, 1, 1), # Swollen Dark Stump | Giant Leaf
    (8620001, 2000036, 100, 1, 1), # Swollen Dark Stump | Reindeer Milk
    (8620001, 4021032, 7500, 1, 1), # Swollen Dark Stump | Mana Crystal
    (8620001, 4033732, 7500, 1, 1), # Swollen Dark Stump | Lucid Matter
    (8620001, 4021035, 100, 1, 1), # Swollen Dark Stump | Grand Spell Essence
    (8620001, 4021033, 100, 1, 1), # Swollen Dark Stump | Basic Spell Essence
    (8620001, 4033733, 7500, 1, 1), # Swollen Dark Stump | Slumbering Fragment
    (8620001, 4021020, 10, 1, 1), # Swollen Dark Stump | Confusion Fragment
    (8620001, 4033726, 7500, 1, 1), # Swollen Dark Stump | Fresh Sap
    (8620001, 2434021, 100, 1, 1), # Swollen Dark Stump | Medal of Honor
    (8620001, 4021031, 50, 1, 1), # Swollen Dark Stump | Twisted Time
    (8880101, 4310029, 7500, 1, 1), # Damien | Crusader Coins
    (8880101, 2435369, 3000, 1, 1), # Damien | Damien's Soul Shard
    (8880101, 4001869, 7500, 1, 1), # Damien | Twisted Stigma Spirit Stone
    (8620000, 4021033, 100, 1, 1), # Swollen Stump | Basic Spell Essence
    (8620000, 4021031, 50, 1, 1), # Swollen Stump | Twisted Time
    (8620000, 2590004, 100, 1, 1), # Swollen Stump | Prototype Soul Enchanter
    (8620000, 4000826, 7500, 1, 1), # Swollen Stump | Giant Tree Branch
    (8620000, 4021034, 100, 1, 1), # Swollen Stump | Fine Spell Essence
    (8620000, 4021032, 7500, 1, 1), # Swollen Stump | Mana Crystal
    (8620000, 4021035, 100, 1, 1), # Swollen Stump | Grand Spell Essence
    (8620000, 4021020, 10, 1, 1), # Swollen Stump | Confusion Fragment
    (8620000, 4033732, 7500, 1, 1), # Swollen Stump | Lucid Matter
    (8620000, 2001554, 100, 1, 1), # Swollen Stump | Sunset Dew
    (8620000, 4033726, 7500, 1, 1), # Swollen Stump | Fresh Sap
    (8620000, 4033733, 7500, 1, 1), # Swollen Stump | Slumbering Fragment
    (8620000, 2434021, 100, 1, 1), # Swollen Stump | Medal of Honor
    (8620000, 2000036, 100, 1, 1), # Swollen Stump | Reindeer Milk
    (8620000, 4034983, 7500, 1, 1), # Swollen Stump | Dream Fragment
    (8620000, 4034984, 7500, 1, 1), # Swollen Stump | Dream Fragment
    (8620000, 4034985, 7500, 1, 1), # Swollen Stump | Dream Fragment
    (8620000, 4034986, 7500, 1, 1), # Swollen Stump | Dream Fragment
    (8620000, 4034987, 7500, 1, 1), # Swollen Stump | Dream Fragment
    (8620000, 4034988, 7500, 1, 1), # Swollen Stump | Dream Fragment
    (8620007, 2001554, 100, 1, 1), # Sinister Rocky Mask | Sunset Dew
    (8620007, 4000833, 7500, 1, 1), # Sinister Rocky Mask | Cursed Slate
    (8620007, 4033733, 7500, 1, 1), # Sinister Rocky Mask | Slumbering Fragment
    (8620007, 4033732, 7500, 1, 1), # Sinister Rocky Mask | Lucid Matter
    (8620007, 4021032, 7500, 1, 1), # Sinister Rocky Mask | Mana Crystal
    (8620007, 2590004, 100, 1, 1), # Sinister Rocky Mask | Prototype Soul Enchanter
    (8620007, 2000036, 100, 1, 1), # Sinister Rocky Mask | Reindeer Milk
    (8620007, 4021031, 50, 1, 1), # Sinister Rocky Mask | Twisted Time
    (8620007, 4021034, 100, 1, 1), # Sinister Rocky Mask | Fine Spell Essence
    (8620007, 4021035, 100, 1, 1), # Sinister Rocky Mask | Grand Spell Essence
    (8620007, 4021033, 100, 1, 1), # Sinister Rocky Mask | Basic Spell Essence
    (8620007, 4033730, 7500, 1, 1), # Sinister Rocky Mask | War Journal
    (8620007, 4021020, 10, 1, 1), # Sinister Rocky Mask | Confusion Fragment
    (8620007, 2434021, 100, 1, 1), # Sinister Rocky Mask | Medal of Honor
    (8620006, 4033732, 7500, 1, 1), # Sinister Wooden Mask | Lucid Matter
    (8620006, 4033733, 7500, 1, 1), # Sinister Wooden Mask | Slumbering Fragment
    (8620006, 4021020, 10, 1, 1), # Sinister Wooden Mask | Confusion Fragment
    (8620006, 4021032, 7500, 1, 1), # Sinister Wooden Mask | Mana Crystal
    (8620006, 4033730, 7500, 1, 1), # Sinister Wooden Mask | War Journal
    (8620006, 2590004, 100, 1, 1), # Sinister Wooden Mask | Prototype Soul Enchanter
    (8620006, 4021034, 100, 1, 1), # Sinister Wooden Mask | Fine Spell Essence
    (8620006, 2434021, 100, 1, 1), # Sinister Wooden Mask | Medal of Honor
    (8620006, 4021033, 100, 1, 1), # Sinister Wooden Mask | Basic Spell Essence
    (8620006, 4021035, 100, 1, 1), # Sinister Wooden Mask | Grand Spell Essence
    (8620006, 2001554, 100, 1, 1), # Sinister Wooden Mask | Sunset Dew
    (8620006, 2000036, 100, 1, 1), # Sinister Wooden Mask | Reindeer Milk
    (8620006, 4000832, 7500, 1, 1), # Sinister Wooden Mask | Cursed Plank
    (8620006, 4021031, 50, 1, 1), # Sinister Wooden Mask | Twisted Time
    (8620005, 2001554, 100, 1, 1), # Pillaging Fire Boars | Sunset Dew
    (8620005, 2434021, 100, 1, 1), # Pillaging Fire Boars | Medal of Honor
    (8620005, 4021033, 100, 1, 1), # Pillaging Fire Boars | Basic Spell Essence
    (8620005, 4021034, 100, 1, 1), # Pillaging Fire Boars | Fine Spell Essence
    (8620005, 4033727, 7500, 1, 1), # Pillaging Fire Boars | Giant Porkchop
    (8620005, 4021031, 50, 1, 1), # Pillaging Fire Boars | Twisted Time
    (8620005, 4033733, 7500, 1, 1), # Pillaging Fire Boars | Slumbering Fragment
    (8620005, 2000036, 100, 1, 1), # Pillaging Fire Boars | Reindeer Milk
    (8620005, 4021035, 100, 1, 1), # Pillaging Fire Boars | Grand Spell Essence
    (8620005, 2590004, 100, 1, 1), # Pillaging Fire Boars | Prototype Soul Enchanter
    (8620005, 4021020, 10, 1, 1), # Pillaging Fire Boars | Confusion Fragment
    (8620005, 4033732, 7500, 1, 1), # Pillaging Fire Boars | Lucid Matter
    (8620005, 4000831, 7500, 1, 1), # Pillaging Fire Boars | Flaming Mane
    (8620005, 4021032, 7500, 1, 1), # Pillaging Fire Boars | Mana Crystal
    (8620004, 2001554, 100, 1, 1), # Iron Boar Raider | Sunset Dew
    (8620004, 4021032, 7500, 1, 1), # Iron Boar Raider | Mana Crystal
    (8620004, 4021034, 100, 1, 1), # Iron Boar Raider | Fine Spell Essence
    (8620004, 4021031, 50, 1, 1), # Iron Boar Raider | Twisted Time
    (8620004, 2000036, 100, 1, 1), # Iron Boar Raider | Reindeer Milk
    (8620004, 4033732, 7500, 1, 1), # Iron Boar Raider | Lucid Matter
    (8620004, 4033727, 7500, 1, 1), # Iron Boar Raider | Giant Porkchop
    (8620004, 2434021, 100, 1, 1), # Iron Boar Raider | Medal of Honor
    (8620004, 4021033, 100, 1, 1), # Iron Boar Raider | Basic Spell Essence
    (8620004, 4000830, 7500, 1, 1), # Iron Boar Raider | Heavy Armor Fragment
    (8620004, 4033733, 7500, 1, 1), # Iron Boar Raider | Slumbering Fragment
    (8620004, 4021035, 100, 1, 1), # Iron Boar Raider | Grand Spell Essence
    (8620004, 4021020, 10, 1, 1), # Iron Boar Raider | Confusion Fragment
    (8620004, 4033753, 7500, 1, 1), # Iron Boar Raider | Strong Armor Fragment
    (8620004, 2590004, 100, 1, 1), # Iron Boar Raider | Prototype Soul Enchanter
    (8620011, 4000835, 7500, 1, 1), # Ancient Mixed Golem | Ancient Rubble
    (8620011, 2434021, 100, 1, 1), # Ancient Mixed Golem | Medal of Honor
    (8620011, 2000036, 100, 1, 1), # Ancient Mixed Golem | Reindeer Milk
    (8620011, 2001554, 100, 1, 1), # Ancient Mixed Golem | Sunset Dew
    (8620011, 4021033, 100, 1, 1), # Ancient Mixed Golem | Basic Spell Essence
    (8620011, 4033733, 7500, 1, 1), # Ancient Mixed Golem | Slumbering Fragment
    (8620011, 4000836, 7500, 1, 1), # Ancient Mixed Golem | Dark Ancient Rubble
    (8620011, 4033731, 7500, 1, 1), # Ancient Mixed Golem | Warrior Heart
    (8620011, 4033732, 7500, 1, 1), # Ancient Mixed Golem | Lucid Matter
    (8620011, 4021035, 100, 1, 1), # Ancient Mixed Golem | Grand Spell Essence
    (8620011, 4021034, 100, 1, 1), # Ancient Mixed Golem | Fine Spell Essence
    (8620011, 4021032, 7500, 1, 1), # Ancient Mixed Golem | Mana Crystal
    (8620011, 4021031, 50, 1, 1), # Ancient Mixed Golem | Twisted Time
    (8620011, 2590004, 100, 1, 1), # Ancient Mixed Golem | Prototype Soul Enchanter
    (8620011, 4021020, 10, 1, 1), # Ancient Mixed Golem | Confusion Fragment
    (8620010, 4021033, 100, 1, 1), # Ancient Dark Golem | Basic Spell Essence
    (8620010, 4021035, 100, 1, 1), # Ancient Dark Golem | Grand Spell Essence
    (8620010, 2590004, 100, 1, 1), # Ancient Dark Golem | Prototype Soul Enchanter
    (8620010, 4033752, 7500, 1, 1), # Ancient Dark Golem | Radiant Dark Rubble
    (8620010, 2001554, 100, 1, 1), # Ancient Dark Golem | Sunset Dew
    (8620010, 4033731, 7500, 1, 1), # Ancient Dark Golem | Warrior Heart
    (8620010, 4033733, 7500, 1, 1), # Ancient Dark Golem | Slumbering Fragment
    (8620010, 4021020, 10, 1, 1), # Ancient Dark Golem | Confusion Fragment
    (8620010, 4021031, 50, 1, 1), # Ancient Dark Golem | Twisted Time
    (8620010, 4033732, 7500, 1, 1), # Ancient Dark Golem | Lucid Matter
    (8620010, 2000036, 100, 1, 1), # Ancient Dark Golem | Reindeer Milk
    (8620010, 4000836, 7500, 1, 1), # Ancient Dark Golem | Dark Ancient Rubble
    (8620010, 4021032, 7500, 1, 1), # Ancient Dark Golem | Mana Crystal
    (8620010, 2434021, 100, 1, 1), # Ancient Dark Golem | Medal of Honor
    (8620010, 4021034, 100, 1, 1), # Ancient Dark Golem | Fine Spell Essence
    (8880110, 4001869, 7500, 1, 1), # Normal Damien | Twisted Stigma Spirit Stone
    (8880110, 2000005, 100, 1, 1), # Normal Damien | Power Elixir
    (8880110, 2435369, 3000, 1, 1), # Normal Damien | Damien's Soul Shard
    (8880110, 4310029, 7500, 1, 1), # Normal Damien | Crusader Coins
    (8880110, 2433103, 100, 1, 1), # Normal Damien | Boss Medal of Honor
    (8620009, 2000036, 100, 1, 1), # Ancient Golem | Reindeer Milk
    (8620009, 4033731, 7500, 1, 1), # Ancient Golem | Warrior Heart
    (8620009, 4000835, 7500, 1, 1), # Ancient Golem | Ancient Rubble
    (8620009, 4021035, 100, 1, 1), # Ancient Golem | Grand Spell Essence
    (8620009, 4021031, 50, 1, 1), # Ancient Golem | Twisted Time
    (8620009, 4021033, 100, 1, 1), # Ancient Golem | Basic Spell Essence
    (8620009, 4033751, 7500, 1, 1), # Ancient Golem | Radiant Ancient Rubble
    (8620009, 4021034, 100, 1, 1), # Ancient Golem | Fine Spell Essence
    (8620009, 4033733, 7500, 1, 1), # Ancient Golem | Slumbering Fragment
    (8620009, 4033732, 7500, 1, 1), # Ancient Golem | Lucid Matter
    (8620009, 4021032, 7500, 1, 1), # Ancient Golem | Mana Crystal
    (8620009, 2001554, 100, 1, 1), # Ancient Golem | Sunset Dew
    (8620009, 2590004, 100, 1, 1), # Ancient Golem | Prototype Soul Enchanter
    (8620009, 4021020, 10, 1, 1), # Ancient Golem | Confusion Fragment
    (8620009, 2434021, 100, 1, 1), # Ancient Golem | Medal of Honor
    (8620008, 4021020, 10, 1, 1), # Sinister Steel Mask | Confusion Fragment
    (8620008, 4033732, 7500, 1, 1), # Sinister Steel Mask | Lucid Matter
    (8620008, 2590004, 100, 1, 1), # Sinister Steel Mask | Prototype Soul Enchanter
    (8620008, 4021034, 100, 1, 1), # Sinister Steel Mask | Fine Spell Essence
    (8620008, 2000036, 100, 1, 1), # Sinister Steel Mask | Reindeer Milk
    (8620008, 2434021, 100, 1, 1), # Sinister Steel Mask | Medal of Honor
    (8620008, 4021033, 100, 1, 1), # Sinister Steel Mask | Basic Spell Essence
    (8620008, 2001554, 100, 1, 1), # Sinister Steel Mask | Sunset Dew
    (8620008, 4021035, 100, 1, 1), # Sinister Steel Mask | Grand Spell Essence
    (8620008, 4000834, 7500, 1, 1), # Sinister Steel Mask | Cursed Plate
    (8620008, 4021031, 50, 1, 1), # Sinister Steel Mask | Twisted Time
    (8620008, 4021032, 7500, 1, 1), # Sinister Steel Mask | Mana Crystal
    (8620008, 4033754, 7500, 1, 1), # Sinister Steel Mask | Thick Cursed Plate
    (8620008, 4033733, 7500, 1, 1), # Sinister Steel Mask | Slumbering Fragment
    (8620015, 2870466, 100, 1, 1), # [*] Official Knight C | Official Knight C Familiar
    (8620015, 2590004, 100, 1, 1), # [*] Official Knight C | Prototype Soul Enchanter
    (8620015, 2070004, 100, 1, 1), # [*] Official Knight C | Tobi Throwing-Stars
    (8620015, 2000036, 100, 1, 1), # [*] Official Knight C | Reindeer Milk
    (8620015, 2001554, 100, 1, 1), # [*] Official Knight C | Sunset Dew
    (8620015, 2434021, 100, 1, 1), # [*] Official Knight C | Medal of Honor
    (8620015, 4021035, 100, 1, 1), # [*] Official Knight C | Grand Spell Essence
    (8620015, 4021020, 10, 1, 1), # [*] Official Knight C | Confusion Fragment
    (8620015, 4021034, 100, 1, 1), # [*] Official Knight C | Fine Spell Essence
    (8620015, 4034983, 7500, 1, 1), # [*] Official Knight C | Dream Fragment
    (8620015, 4034984, 7500, 1, 1), # [*] Official Knight C | Dream Fragment
    (8620015, 4034985, 7500, 1, 1), # [*] Official Knight C | Dream Fragment
    (8620015, 4034986, 7500, 1, 1), # [*] Official Knight C | Dream Fragment
    (8620015, 4034987, 7500, 1, 1), # [*] Official Knight C | Dream Fragment
    (8620015, 4034988, 7500, 1, 1), # [*] Official Knight C | Dream Fragment
    (8620015, 4032926, 7500, 1, 1), # [*] Official Knight C | Stan's Letter
    (8620015, 4021032, 7500, 1, 1), # [*] Official Knight C | Mana Crystal
    (8620015, 4021033, 100, 1, 1), # [*] Official Knight C | Basic Spell Essence
    (8620015, 4000651, 7500, 1, 1), # [*] Official Knight C | Knight Bandana
    (8620015, 4021031, 50, 1, 1), # [*] Official Knight C | Twisted Time
    (8620014, 4021034, 100, 1, 1), # [*] Official Knight B | Fine Spell Essence
    (8620014, 4021032, 7500, 1, 1), # [*] Official Knight B | Mana Crystal
    (8620014, 2512266, 100, 1, 1), # [*] Official Knight B | Superior Hero Potion Recipe
    (8620014, 2512270, 100, 1, 1), # [*] Official Knight B | Exceptional Blessing Potion Recipe
    (8620014, 4021035, 100, 1, 1), # [*] Official Knight B | Grand Spell Essence
    (8620014, 2512175, 100, 1, 1), # [*] Official Knight B | Advanced Dexterity Pill VII Recipe
    (8620014, 2049401, 100, 1, 1), # [*] Official Knight B | Potential Scroll
    (8620014, 2000036, 100, 1, 1), # [*] Official Knight B | Reindeer Milk
    (8620014, 2512154, 100, 1, 1), # [*] Official Knight B | Advanced Strength Pill VI Recipe
    (8620014, 4021020, 10, 1, 1), # [*] Official Knight B | Confusion Fragment
    (8620014, 2512128, 100, 1, 1), # [*] Official Knight B | Advanced Luck Potion X Recipe
    (8620014, 2512285, 100, 1, 1), # [*] Official Knight B | Superior Dexterity Boost Potion Recipe
    (8620014, 2512283, 100, 1, 1), # [*] Official Knight B | Superior Intelligence Boost Potion Recipe
    (8620014, 4021031, 50, 1, 1), # [*] Official Knight B | Twisted Time
    (8620014, 2001554, 100, 1, 1), # [*] Official Knight B | Sunset Dew
    (8620014, 4000650, 7500, 1, 1), # [*] Official Knight B | Knight Robe
    (8620014, 4021033, 100, 1, 1), # [*] Official Knight B | Basic Spell Essence
    (8620014, 2434021, 100, 1, 1), # [*] Official Knight B | Medal of Honor
    (8620014, 2590004, 100, 1, 1), # [*] Official Knight B | Prototype Soul Enchanter
    (8620014, 2512271, 100, 1, 1), # [*] Official Knight B | Legendary Blessing Potion Recipe
    (8620014, 2512287, 100, 1, 1), # [*] Official Knight B | Superior Luck Boost Potion Recipe
    (8620014, 2512099, 100, 1, 1), # [*] Official Knight B | Advanced Dexterity Potion I Recipe
    (8620014, 2070009, 100, 1, 1), # [*] Official Knight B | Wooden Top
    (8620014, 2870465, 100, 1, 1), # [*] Official Knight B | Official Knight B Familiar
    (8620014, 4032941, 7500, 1, 1), # [*] Official Knight B | Cheery Book
    (8620014, 2512137, 100, 1, 1), # [*] Official Knight B | Advanced Magic Attack Potion IV Recipe
    (8620014, 4034983, 7500, 1, 1), # [*] Official Knight B | Dream Fragment
    (8620014, 4034984, 7500, 1, 1), # [*] Official Knight B | Dream Fragment
    (8620014, 4034985, 7500, 1, 1), # [*] Official Knight B | Dream Fragment
    (8620014, 4034986, 7500, 1, 1), # [*] Official Knight B | Dream Fragment
    (8620014, 4034987, 7500, 1, 1), # [*] Official Knight B | Dream Fragment
    (8620014, 4034988, 7500, 1, 1), # [*] Official Knight B | Dream Fragment
    (8620013, 4021034, 100, 1, 1), # [*] Official Knight A | Fine Spell Essence
    (8620013, 4021032, 7500, 1, 1), # [*] Official Knight A | Mana Crystal
    (8620013, 2512154, 100, 1, 1), # [*] Official Knight A | Advanced Strength Pill VI Recipe
    (8620013, 2512137, 100, 1, 1), # [*] Official Knight A | Advanced Magic Attack Potion IV Recipe
    (8620013, 2512266, 100, 1, 1), # [*] Official Knight A | Superior Hero Potion Recipe
    (8620013, 2512285, 100, 1, 1), # [*] Official Knight A | Superior Dexterity Boost Potion Recipe
    (8620013, 4021020, 10, 1, 1), # [*] Official Knight A | Confusion Fragment
    (8620013, 2049401, 100, 1, 1), # [*] Official Knight A | Potential Scroll
    (8620013, 2330003, 100, 1, 1), # [*] Official Knight A | Vital Bullet
    (8620013, 2590004, 100, 1, 1), # [*] Official Knight A | Prototype Soul Enchanter
    (8620013, 2512287, 100, 1, 1), # [*] Official Knight A | Superior Luck Boost Potion Recipe
    (8620013, 2512128, 100, 1, 1), # [*] Official Knight A | Advanced Luck Potion X Recipe
    (8620013, 2000036, 100, 1, 1), # [*] Official Knight A | Reindeer Milk
    (8620013, 4034983, 7500, 1, 1), # [*] Official Knight A | Dream Fragment
    (8620013, 4034984, 7500, 1, 1), # [*] Official Knight A | Dream Fragment
    (8620013, 4034985, 7500, 1, 1), # [*] Official Knight A | Dream Fragment
    (8620013, 4034986, 7500, 1, 1), # [*] Official Knight A | Dream Fragment
    (8620013, 4034987, 7500, 1, 1), # [*] Official Knight A | Dream Fragment
    (8620013, 4034988, 7500, 1, 1), # [*] Official Knight A | Dream Fragment
    (8620013, 2330002, 100, 1, 1), # [*] Official Knight A | Mighty Bullet
    (8620013, 2512271, 100, 1, 1), # [*] Official Knight A | Legendary Blessing Potion Recipe
    (8620013, 2434021, 100, 1, 1), # [*] Official Knight A | Medal of Honor
    (8620013, 2048003, 100, 1, 1), # [*] Official Knight A | Scroll for Pet Equip. for Jump 100%
    (8620013, 2070007, 100, 1, 1), # [*] Official Knight A | Hwabi Throwing-Stars
    (8620013, 4021033, 100, 1, 1), # [*] Official Knight A | Basic Spell Essence
    (8620013, 2870464, 100, 1, 1), # [*] Official Knight A | Official Knight A Familiar
    (8620013, 4021031, 50, 1, 1), # [*] Official Knight A | Twisted Time
    (8620013, 4021035, 100, 1, 1), # [*] Official Knight A | Grand Spell Essence
    (8620013, 2512099, 100, 1, 1), # [*] Official Knight A | Advanced Dexterity Potion I Recipe
    (8620013, 4000649, 7500, 1, 1), # [*] Official Knight A | Knight Mask
    (8620013, 2512175, 100, 1, 1), # [*] Official Knight A | Advanced Dexterity Pill VII Recipe
    (8620013, 2512270, 100, 1, 1), # [*] Official Knight A | Exceptional Blessing Potion Recipe
    (8620013, 2001554, 100, 1, 1), # [*] Official Knight A | Sunset Dew
    (8620013, 2512283, 100, 1, 1), # [*] Official Knight A | Superior Intelligence Boost Potion Recipe
    (8620012, 4033732, 7500, 1, 1), # Ghostwood Stumpy | Lucid Matter
    (8620012, 2001554, 100, 1, 1), # Ghostwood Stumpy | Sunset Dew
    (8620012, 4021020, 10, 1, 1), # Ghostwood Stumpy | Confusion Fragment
    (8620012, 2000036, 100, 1, 1), # Ghostwood Stumpy | Reindeer Milk
    (8620012, 4021031, 50, 1, 1), # Ghostwood Stumpy | Twisted Time
    (8620012, 4021032, 7500, 1, 1), # Ghostwood Stumpy | Mana Crystal
    (8620012, 4021034, 100, 1, 1), # Ghostwood Stumpy | Fine Spell Essence
    (8620012, 4033729, 7500, 1, 1), # Ghostwood Stumpy | Pachamama's Quena
    (8620012, 4033733, 7500, 1, 1), # Ghostwood Stumpy | Slumbering Fragment
    (8620012, 2434021, 100, 1, 1), # Ghostwood Stumpy | Medal of Honor
    (8620012, 4000826, 7500, 1, 1), # Ghostwood Stumpy | Giant Tree Branch
    (8620012, 4021035, 100, 1, 1), # Ghostwood Stumpy | Grand Spell Essence
    (8620012, 4000827, 7500, 1, 1), # Ghostwood Stumpy | Giant Leaf
    (8620012, 2590004, 100, 1, 1), # Ghostwood Stumpy | Prototype Soul Enchanter
    (8620012, 4021033, 100, 1, 1), # Ghostwood Stumpy | Basic Spell Essence
    (8620012, 4000828, 7500, 1, 1), # Ghostwood Stumpy | Giant Firewood
    (5300000, 2870224, 100, 1, 1), # Leatty | Leatty Familiar
    (5300000, 4000086, 7500, 1, 1), # Leatty | Leatty Furball
    (5300001, 2870225, 100, 1, 1), # Dark Leatty | Dark Leatty Familiar
    (5300001, 4000087, 7500, 1, 1), # Dark Leatty | Dark Leatty Furball
    (8620017, 2870468, 100, 1, 1), # [*] Official Knight E | Official Knight E Familiar
    (8620017, 4034983, 7500, 1, 1), # [*] Official Knight E | Dream Fragment
    (8620017, 4034984, 7500, 1, 1), # [*] Official Knight E | Dream Fragment
    (8620017, 4034985, 7500, 1, 1), # [*] Official Knight E | Dream Fragment
    (8620017, 4034986, 7500, 1, 1), # [*] Official Knight E | Dream Fragment
    (8620017, 4034987, 7500, 1, 1), # [*] Official Knight E | Dream Fragment
    (8620017, 4034988, 7500, 1, 1), # [*] Official Knight E | Dream Fragment
    (8620017, 2590004, 100, 1, 1), # [*] Official Knight E | Prototype Soul Enchanter
    (8620017, 4021034, 100, 1, 1), # [*] Official Knight E | Fine Spell Essence
    (8620017, 4021035, 100, 1, 1), # [*] Official Knight E | Grand Spell Essence
    (8620017, 4021020, 10, 1, 1), # [*] Official Knight E | Confusion Fragment
    (8620017, 2001554, 100, 1, 1), # [*] Official Knight E | Sunset Dew
    (8620017, 4000653, 7500, 1, 1), # [*] Official Knight E | Knight Pauldron
    (8620017, 4021032, 7500, 1, 1), # [*] Official Knight E | Mana Crystal
    (8620017, 4021031, 50, 1, 1), # [*] Official Knight E | Twisted Time
    (8620017, 2434021, 100, 1, 1), # [*] Official Knight E | Medal of Honor
    (8620017, 4021033, 100, 1, 1), # [*] Official Knight E | Basic Spell Essence
    (8620017, 2000036, 100, 1, 1), # [*] Official Knight E | Reindeer Milk
    (8620017, 2070005, 100, 1, 1), # [*] Official Knight E | Steely Throwing-Knives
    (8620016, 4000652, 7500, 1, 1), # [*] Official Knight D | Knight Gloves
    (8620016, 4034983, 7500, 1, 1), # [*] Official Knight D | Dream Fragment
    (8620016, 4034984, 7500, 1, 1), # [*] Official Knight D | Dream Fragment
    (8620016, 4034985, 7500, 1, 1), # [*] Official Knight D | Dream Fragment
    (8620016, 4034986, 7500, 1, 1), # [*] Official Knight D | Dream Fragment
    (8620016, 4034987, 7500, 1, 1), # [*] Official Knight D | Dream Fragment
    (8620016, 4034988, 7500, 1, 1), # [*] Official Knight D | Dream Fragment
    (8620016, 2001554, 100, 1, 1), # [*] Official Knight D | Sunset Dew
    (8620016, 4021034, 100, 1, 1), # [*] Official Knight D | Fine Spell Essence
    (8620016, 4021020, 10, 1, 1), # [*] Official Knight D | Confusion Fragment
    (8620016, 2870467, 100, 1, 1), # [*] Official Knight D | Official Knight D Familiar
    (8620016, 4021035, 100, 1, 1), # [*] Official Knight D | Grand Spell Essence
    (8620016, 4021031, 50, 1, 1), # [*] Official Knight D | Twisted Time
    (8620016, 2434021, 100, 1, 1), # [*] Official Knight D | Medal of Honor
    (8620016, 4021032, 7500, 1, 1), # [*] Official Knight D | Mana Crystal
    (8620016, 4021033, 100, 1, 1), # [*] Official Knight D | Basic Spell Essence
    (8620016, 2000036, 100, 1, 1), # [*] Official Knight D | Reindeer Milk
    (8620016, 2590004, 100, 1, 1), # [*] Official Knight D | Prototype Soul Enchanter
    (2230102, 2060000, 100, 1, 1), # Wild Boar | Arrow for Bow
    (2230102, 2870047, 100, 1, 1), # Wild Boar | Wild Boar Familiar
    (2230102, 2000000, 100, 1, 1), # Wild Boar | Red Potion
    (2230102, 2044310, 100, 1, 1), # Wild Boar | Scroll for Spear for Accuracy 100%
    (2230102, 2040500, 100, 1, 1), # Wild Boar | Scroll for Overall Armor for DEX 100%
    (2230102, 2044700, 100, 1, 1), # Wild Boar | Scroll for Claw for ATT 100%
    (2230102, 4003004, 7500, 1, 1), # Wild Boar | Stiff Feather
    (2230102, 4010003, 7500, 1, 1), # Wild Boar | Adamantium Ore
    (2230102, 4000021, 7500, 1, 1), # Wild Boar | Leather
    (2230102, 2000002, 100, 1, 1), # Wild Boar | White Potion
    (2230102, 4020004, 7500, 1, 1), # Wild Boar | Opal Ore
    (2230102, 4000020, 7500, 1, 1), # Wild Boar | Wild Boar Tooth
    (1120100, 2022054, 100, 1, 1), # Octopus | Pear
    (1120100, 2043002, 100, 1, 1), # Octopus | Scroll for One-Handed Sword for ATT 10%
    (1120100, 2022053, 100, 1, 1), # Octopus | Jujube
    (1120100, 2022014, 100, 1, 1), # Octopus | Dango
    (1120100, 4020002, 7500, 1, 1), # Octopus | AquaMarine Ore
    (1120100, 4010003, 7500, 1, 1), # Octopus | Adamantium Ore
    (1120100, 2000003, 100, 1, 1), # Octopus | Blue Potion
    (1120100, 2870012, 100, 1, 1), # Octopus | Octopus Familiar
    (1120100, 2022055, 100, 1, 1), # Octopus | Persimmon
    (1120100, 2000002, 100, 1, 1), # Octopus | White Potion
    (2230103, 2040417, 100, 1, 1), # Trixter | Scroll for Topwear for STR 100%
    (2230103, 2070008, 100, 1, 1), # Trixter | Snowball
    (2230103, 2044112, 100, 1, 1), # Trixter | Scroll for Two-Handed Axe for Accuracy 60%
    (2230103, 2044802, 100, 1, 1), # Trixter | Scroll for Knuckle for ATT 10%
    (2230103, 2000001, 100, 1, 1), # Trixter | Orange Potion
    (2230103, 4000097, 7500, 1, 1), # Trixter | Spiderweb
    (2230103, 4004003, 7500, 1, 1), # Trixter | LUK Crystal Ore
    (2230103, 2040002, 100, 1, 1), # Trixter | Scroll for Helmet for DEF 10%
    (2230103, 2001521, 100, 1, 1), # Trixter | Warrior Pill
    (2230103, 2000006, 100, 1, 1), # Trixter | Mana Elixir
    (2230103, 2043201, 100, 1, 1), # Trixter | Scroll for One-Handed BW for ATT 60%
    (2230103, 4010003, 7500, 1, 1), # Trixter | Adamantium Ore
    (2230103, 2043601, 100, 1, 1), # Trixter | Scroll for Cane for ATT 60%
    (2230103, 2044114, 100, 1, 1), # Trixter | Scroll for Two-Handed Axe for Accuracy 10%
    (2230103, 2061000, 100, 1, 1), # Trixter | Arrow for Crossbow
    (2230100, 2044800, 100, 1, 1), # Evil Eye | Scroll for Knuckle for Attack 100%
    (2230100, 2070001, 100, 1, 1), # Evil Eye | Wolbi Throwing-Stars
    (2230100, 2040515, 100, 1, 1), # Evil Eye | Scroll for Overall Armor for LUK 100%
    (2230100, 4020005, 7500, 1, 1), # Evil Eye | Sapphire Ore
    (2230100, 4010004, 7500, 1, 1), # Evil Eye | Silver Ore
    (2230100, 2870045, 100, 1, 1), # Evil Eye | Evil Eye Familiar
    (2230100, 2044402, 100, 1, 1), # Evil Eye | Scroll for Pole Arm for ATT 10%
    (2230100, 2048000, 100, 1, 1), # Evil Eye | Scroll for Pet Equip. for Speed 100%
    (2230100, 2000001, 100, 1, 1), # Evil Eye | Orange Potion
    (2230100, 4000007, 7500, 1, 1), # Evil Eye | Evil Eye Tail
    (2230100, 2061000, 100, 1, 1), # Evil Eye | Arrow for Crossbow
    (2230101, 4020003, 7500, 1, 1), # Zombie Mushroom | Emerald Ore
    (2230101, 4010002, 7500, 1, 1), # Zombie Mushroom | Mithril Ore
    (2230101, 2040003, 100, 1, 1), # Zombie Mushroom | Scroll for Helmet for HP 100%
    (2230101, 2000003, 100, 1, 1), # Zombie Mushroom | Blue Potion
    (2230101, 2044210, 100, 1, 1), # Zombie Mushroom | Scroll for Two-Handed BW for Accuracy 100%
    (2230101, 2044700, 100, 1, 1), # Zombie Mushroom | Scroll for Claw for ATT 100%
    (2230101, 4000008, 7500, 1, 1), # Zombie Mushroom | Charm of the Undead
    (2230101, 2044302, 100, 1, 1), # Zombie Mushroom | Scroll for Spear for ATT 10%
    (2230101, 2872040, 100, 1, 1), # Zombie Mushroom | Zombie Mushroom Familiar
    (2230101, 2061000, 100, 1, 1), # Zombie Mushroom | Arrow for Crossbow
    (2230101, 2045302, 100, 1, 1), # Zombie Mushroom | Scroll for Hand Cannon for ATT 10%
    (2230106, 2000002, 100, 1, 1), # Cico | White Potion
    (2230106, 2040004, 100, 1, 1), # Cico | Scroll for Helmet for HP 60%
    (2230106, 4000163, 7500, 1, 1), # Cico | Sea Horse Horn
    (2230106, 2044412, 100, 1, 1), # Cico | Scroll for Pole-Arm for Accuracy 60%
    (2230106, 2000004, 100, 1, 1), # Cico | Elixir
    (2230106, 2001527, 100, 1, 1), # Cico | Unagi
    (2230106, 2001526, 100, 1, 1), # Cico | Pure Water
    (2230106, 2048004, 100, 1, 1), # Cico | Scroll for Pet Equip. for Jump 60%
    (2230106, 2040514, 100, 1, 1), # Cico | Scroll for Overall Armor for INT 10%
    (2230106, 2040602, 100, 1, 1), # Cico | Scroll for Bottomwear for DEF 10%
    (2230106, 2060000, 100, 1, 1), # Cico | Arrow for Bow
    (2230106, 2870049, 100, 1, 1), # Cico | Cico Familiar
    (2230107, 2070008, 100, 1, 1), # Krappy | Snowball
    (2230107, 4020005, 7500, 1, 1), # Krappy | Sapphire Ore
    (2230107, 4010002, 7500, 1, 1), # Krappy | Mithril Ore
    (2230107, 2044010, 100, 1, 1), # Krappy | Scroll for Two-Handed Sword for Accuracy 100%
    (2230107, 2040613, 100, 1, 1), # Krappy | Scroll for Bottomwear for DEX 60%
    (2230107, 2045302, 100, 1, 1), # Krappy | Scroll for Hand Cannon for ATT 10%
    (2230107, 2044302, 100, 1, 1), # Krappy | Scroll for Spear for ATT 10%
    (2230107, 2040902, 100, 1, 1), # Krappy | Scroll for Shield for DEF 10%
    (2230107, 2040326, 100, 1, 1), # Krappy | Scroll for Earring for HP 60%
    (2230107, 2040321, 100, 1, 1), # Krappy | Scroll for Earring for LUK 60%
    (2230107, 2001527, 100, 1, 1), # Krappy | Unagi
    (2230107, 2060000, 100, 1, 1), # Krappy | Arrow for Bow
    (2230107, 2870050, 100, 1, 1), # Krappy | Krappy Familiar
    (2230107, 2000002, 100, 1, 1), # Krappy | White Potion
    (2230107, 4000162, 7500, 1, 1), # Krappy | Flamboyant Scale Skin
    (2230104, 2040924, 100, 1, 1), # Green Trixter | Scroll for Shield for LUK 60%
    (2230104, 2041009, 100, 1, 1), # Green Trixter | Scroll for Cape for MP 100%
    (2230104, 2001527, 100, 1, 1), # Green Trixter | Unagi
    (2230104, 4000098, 7500, 1, 1), # Green Trixter | Sticky Spiderweb
    (2230104, 4010002, 7500, 1, 1), # Green Trixter | Mithril Ore
    (2230104, 4010001, 7500, 1, 1), # Green Trixter | Steel Ore
    (2230104, 4004002, 7500, 1, 1), # Green Trixter | DEX Crystal Ore
    (2230104, 2044112, 100, 1, 1), # Green Trixter | Scroll for Two-Handed Axe for Accuracy 60%
    (2230104, 2040516, 100, 1, 1), # Green Trixter | Scroll for Overall Armor for LUK 60%
    (2230104, 2001522, 100, 1, 1), # Green Trixter | Magic Pill
    (2230105, 2040620, 100, 1, 1), # Seacle | Scroll for Bottomwear for HP 100%
    (2230105, 2070001, 100, 1, 1), # Seacle | Wolbi Throwing-Stars
    (2230105, 2040824, 100, 1, 1), # Seacle | Scroll for Gloves for HP 60%
    (2230105, 4004003, 7500, 1, 1), # Seacle | LUK Crystal Ore
    (2230105, 2001526, 100, 1, 1), # Seacle | Pure Water
    (2230105, 2870048, 100, 1, 1), # Seacle | Seacle Familiar
    (2230105, 4000161, 7500, 1, 1), # Seacle | Sea Horse Tail
    (2230105, 2040505, 100, 1, 1), # Seacle | Scroll for Overall Armor for DEF 10%
    (2230105, 2041010, 100, 1, 1), # Seacle | Scroll for Cape for MP 60%
    (2230105, 2044601, 100, 1, 1), # Seacle | Scroll for Crossbow for ATT 60%
    (2230105, 2070009, 100, 1, 1), # Seacle | Wooden Top
    (2230105, 2000002, 100, 1, 1), # Seacle | White Potion
    (2230105, 4010001, 7500, 1, 1), # Seacle | Steel Ore
    (2230105, 2001527, 100, 1, 1), # Seacle | Unagi
    (2230105, 2060000, 100, 1, 1), # Seacle | Arrow for Bow
    (2230105, 2061000, 100, 1, 1), # Seacle | Arrow for Crossbow
    (2110300, 4020007, 7500, 1, 1), # Sand Rat | Diamond Ore
    (2110300, 2000004, 100, 1, 1), # Sand Rat | Elixir
    (2110300, 4020000, 7500, 1, 1), # Sand Rat | Garnet Ore
    (2110300, 4010007, 7500, 1, 1), # Sand Rat | Lidium Ore
    (2110300, 2043214, 100, 1, 1), # Sand Rat | Scroll for One-Handed BW for Accuracy 10%
    (2110300, 2040924, 100, 1, 1), # Sand Rat | Scroll for Shield for LUK 60%
    (2110300, 2070003, 100, 1, 1), # Sand Rat | Kumbi Throwing-Stars
    (2110300, 4000350, 7500, 1, 1), # Sand Rat | Wooden Hammer
    (2110300, 2001527, 100, 1, 1), # Sand Rat | Unagi
    (2110300, 2048000, 100, 1, 1), # Sand Rat | Scroll for Pet Equip. for Speed 100%
    (2110300, 2000002, 100, 1, 1), # Sand Rat | White Potion
    (2110300, 2000006, 100, 1, 1), # Sand Rat | Mana Elixir
    (2230110, 4020001, 7500, 1, 1), # Wooden Mask | Amethyst Ore
    (2230110, 2040420, 100, 1, 1), # Wooden Mask | Scroll for Topwear for HP 100%
    (2230110, 2044805, 100, 1, 1), # Wooden Mask | Scroll for Knuckle for Accuracy 100%
    (2230110, 2000001, 100, 1, 1), # Wooden Mask | Orange Potion
    (2230110, 4010000, 7500, 1, 1), # Wooden Mask | Bronze Ore
    (2230110, 2870053, 100, 1, 1), # Wooden Mask | Wooden Mask Familiar
    (2230110, 4020002, 7500, 1, 1), # Wooden Mask | AquaMarine Ore
    (2230110, 2040316, 100, 1, 1), # Wooden Mask | Scroll for Earring for DEX 100%
    (2230110, 2061000, 100, 1, 1), # Wooden Mask | Arrow for Crossbow
    (2230110, 2044010, 100, 1, 1), # Wooden Mask | Scroll for Two-Handed Sword for Accuracy 100%
    (2230110, 4000196, 7500, 1, 1), # Wooden Mask | Wooden Board
    (2230110, 2040530, 100, 1, 1), # Wooden Mask | Scroll for Overall for STR 100%
    (2230110, 2000006, 100, 1, 1), # Wooden Mask | Mana Elixir
    (8160000, 2000037, 100, 1, 1), # [*]Gatekeeper | Sunrise Dew
    (8160000, 2434021, 100, 1, 1), # [*]Gatekeeper | Medal of Honor
    (8160000, 2870343, 100, 1, 1), # [*]Gatekeeper | [*]Gatekeeper Familiar
    (8160000, 4034181, 100, 1, 1), # [*]Gatekeeper | Dispersed Star Fragment
    (8160000, 4021031, 50, 1, 1), # [*]Gatekeeper | Twisted Time
    (8160000, 4021032, 7500, 1, 1), # [*]Gatekeeper | Mana Crystal
    (8160000, 4000151, 7500, 1, 1), # [*]Gatekeeper | Gatekeeper Armband
    (8160000, 2590004, 100, 1, 1), # [*]Gatekeeper | Prototype Soul Enchanter
    (8160000, 4021034, 100, 1, 1), # [*]Gatekeeper | Fine Spell Essence
    (8160000, 4031172, 7500, 1, 1), # [*]Gatekeeper | Ludibrium Medal
    (8160000, 4021033, 100, 1, 1), # [*]Gatekeeper | Basic Spell Essence
    (8160000, 4021035, 100, 1, 1), # [*]Gatekeeper | Grand Spell Essence
    (8160000, 2001528, 100, 1, 1), # [*]Gatekeeper | Melting Cheese
    (2110301, 2048002, 100, 1, 1), # Scorpion | Scroll for Pet Equip. for Speed 10%
    (2110301, 2001002, 100, 1, 1), # Scorpion | Very Special Sundae
    (2110301, 4010001, 7500, 1, 1), # Scorpion | Steel Ore
    (2110301, 2044901, 100, 1, 1), # Scorpion | Scroll for Gun for Attack 60%
    (2110301, 2512055, 100, 1, 1), # Scorpion | Blessing Potion Recipe
    (2110301, 2043002, 100, 1, 1), # Scorpion | Scroll for One-Handed Sword for ATT 10%
    (2110301, 4010002, 7500, 1, 1), # Scorpion | Mithril Ore
    (2110301, 2043114, 100, 1, 1), # Scorpion | Scroll for One-Handed Axe for Accuracy 10%
    (2110301, 2001526, 100, 1, 1), # Scorpion | Pure Water
    (2110301, 4000351, 7500, 1, 1), # Scorpion | Scorpion Sting
    (2110301, 2060000, 100, 1, 1), # Scorpion | Arrow for Bow
    (2110301, 4010007, 7500, 1, 1), # Scorpion | Lidium Ore
    (2110301, 2000005, 100, 1, 1), # Scorpion | Power Elixir
    (2110301, 4004002, 7500, 1, 1), # Scorpion | DEX Crystal Ore
    (2110301, 2001001, 100, 1, 1), # Scorpion | Ice Cream Pop
    (2110301, 2061000, 100, 1, 1), # Scorpion | Arrow for Crossbow
    (2110301, 2870035, 100, 1, 1), # Scorpion | Scorpion Familiar
    (2110301, 2048004, 100, 1, 1), # Scorpion | Scroll for Pet Equip. for Jump 60%
    (2230111, 2000006, 100, 1, 1), # Rocky Mask | Mana Elixir
    (2230111, 2000000, 100, 1, 1), # Rocky Mask | Red Potion
    (2230111, 4010001, 7500, 1, 1), # Rocky Mask | Steel Ore
    (2230111, 2044210, 100, 1, 1), # Rocky Mask | Scroll for Two-Handed BW for Accuracy 100%
    (2230111, 2070008, 100, 1, 1), # Rocky Mask | Snowball
    (2230111, 2044110, 100, 1, 1), # Rocky Mask | Scroll for Two-Handed Axe for Accuracy 100%
    (2230111, 4020003, 7500, 1, 1), # Rocky Mask | Emerald Ore
    (2230111, 2040534, 100, 1, 1), # Rocky Mask | Scroll for Overall for STR 10%
    (2230111, 2870054, 100, 1, 1), # Rocky Mask | Rocky Mask Familiar
    (2230111, 2060000, 100, 1, 1), # Rocky Mask | Arrow for Bow
    (2230111, 4000197, 7500, 1, 1), # Rocky Mask | Slate
    (2230111, 2049000, 100, 1, 1), # Rocky Mask | Pure Clean Slate Scroll 1%
    (2230111, 2043800, 100, 1, 1), # Rocky Mask | Scroll for Staff for Magic Attack 100%
    (2230111, 4010005, 7500, 1, 1), # Rocky Mask | Orihalcon Ore
    (2230108, 2043017, 100, 1, 1), # Pinboom | Scroll for One-Handed Sword for Accuracy 60%
    (2230108, 2043201, 100, 1, 1), # Pinboom | Scroll for One-Handed BW for ATT 60%
    (2230108, 2001527, 100, 1, 1), # Pinboom | Unagi
    (2230108, 2870051, 100, 1, 1), # Pinboom | Pinboom Familiar
    (2230108, 4000160, 7500, 1, 1), # Pinboom | Needle
    (2230108, 4010000, 7500, 1, 1), # Pinboom | Bronze Ore
    (2230108, 2040620, 100, 1, 1), # Pinboom | Scroll for Bottomwear for HP 100%
    (2230108, 4006001, 7500, 1, 1), # Pinboom | The Summoning Rock
    (2230108, 2044807, 100, 1, 1), # Pinboom | Scroll for Knuckle for Accuracy 60%
    (2230108, 2044202, 100, 1, 1), # Pinboom | Scroll for Two-handed BW for ATT 10%
    (2230108, 2044012, 100, 1, 1), # Pinboom | Scroll for Two-Handed Sword for Accuracy 60%
    (2230108, 2000002, 100, 1, 1), # Pinboom | White Potion
    (2230108, 4020007, 7500, 1, 1), # Pinboom | Diamond Ore
    (2230109, 4020003, 7500, 1, 1), # Bubble Fish | Emerald Ore
    (2230109, 2070002, 100, 1, 1), # Bubble Fish | Mokbi Throwing-Stars
    (2230109, 2000002, 100, 1, 1), # Bubble Fish | White Potion
    (2230109, 4010004, 7500, 1, 1), # Bubble Fish | Silver Ore
    (2230109, 2000006, 100, 1, 1), # Bubble Fish | Mana Elixir
    (2230109, 2001526, 100, 1, 1), # Bubble Fish | Pure Water
    (2230109, 2040534, 100, 1, 1), # Bubble Fish | Scroll for Overall for STR 10%
    (2230109, 2060000, 100, 1, 1), # Bubble Fish | Arrow for Bow
    (2230109, 2040513, 100, 1, 1), # Bubble Fish | Scroll for Overall Armor for INT 60%
    (2230109, 2049210, 100, 1, 1), # Bubble Fish | Dark Scroll for Accessory for MP 70%
    (2230109, 4000164, 7500, 1, 1), # Bubble Fish | Bubble Fish's Thoughts
    (2230109, 2040925, 100, 1, 1), # Bubble Fish | Scroll for Shield for LUK 10%
    (2230109, 4004002, 7500, 1, 1), # Bubble Fish | DEX Crystal Ore
    (2230109, 2870052, 100, 1, 1), # Bubble Fish | Bubble Fish Familiar
    (2230109, 2040902, 100, 1, 1), # Bubble Fish | Scroll for Shield for DEF 10%
    (3502005, 4001806, 7500, 1, 1), # Possibly-Evil Walrus | Pure White Tusk
    (3502005, 2431174, 100, 1, 1), # Possibly-Evil Walrus | Medal of Honor
    (3502005, 4030025, 7500, 1, 1), # Possibly-Evil Walrus | Garbled Command
    (5250000, 4033109, 500, 1, 1), # Mossy Mushroom | Forest Stone
    (5250000, 2870221, 100, 1, 1), # Mossy Mushroom | Mossy Mushroom Familiar
    (5250000, 4000437, 7500, 1, 1), # Mossy Mushroom | Black Mushroom Spore
    (3502004, 4001805, 7500, 1, 1), # Possibly-Evil Seal | Soft Seal Pelt
    (3502004, 2431174, 100, 1, 1), # Possibly-Evil Seal | Medal of Honor
    (5250001, 2870222, 100, 1, 1), # Stone Bug | Stone Bug Familiar
    (5250001, 4000439, 7500, 1, 1), # Stone Bug | Rubble
    (5250001, 4033109, 500, 1, 1), # Stone Bug | Forest Stone
    (3502007, 2431174, 100, 1, 1), # Shaver Bot | Medal of Honor
    (3502007, 4030030, 7500, 1, 1), # Shaver Bot | Glacier Core Fragment
    (3502007, 4001808, 7500, 1, 1), # Shaver Bot | Worn Point
    (5250002, 2001001, 100, 1, 1), # Primitive Boar | Ice Cream Pop
    (5250002, 2590004, 100, 1, 1), # Primitive Boar | Prototype Soul Enchanter
    (5250002, 2870223, 100, 1, 1), # Primitive Boar | Primitive Boar Familiar
    (5250002, 2434021, 100, 1, 1), # Primitive Boar | Medal of Honor
    (5250002, 4033109, 500, 1, 1), # Primitive Boar | Forest Stone
    (5250002, 4000440, 7500, 1, 1), # Primitive Boar | Tough Leather
    (5250002, 4021034, 100, 1, 1), # Primitive Boar | Fine Spell Essence
    (5250002, 4021031, 50, 1, 1), # Primitive Boar | Twisted Time
    (5250002, 2001002, 100, 1, 1), # Primitive Boar | Very Special Sundae
    (5250002, 4021033, 100, 1, 1), # Primitive Boar | Basic Spell Essence
    (5250002, 4021035, 100, 1, 1), # Primitive Boar | Grand Spell Essence
    (5250002, 4021032, 7500, 1, 1), # Primitive Boar | Mana Crystal
    (8630017, 4021035, 100, 1, 1), # Corrupted Advanced Magician | Grand Spell Essence
    (8630017, 4021034, 100, 1, 1), # Corrupted Advanced Magician | Fine Spell Essence
    (8630017, 4021020, 10, 1, 1), # Corrupted Advanced Magician | Confusion Fragment
    (8630017, 4021031, 50, 1, 1), # Corrupted Advanced Magician | Twisted Time
    (8630017, 4021033, 100, 1, 1), # Corrupted Advanced Magician | Basic Spell Essence
    (8630017, 2431174, 100, 1, 1), # Corrupted Advanced Magician | Medal of Honor
    (8630017, 2590004, 100, 1, 1), # Corrupted Advanced Magician | Prototype Soul Enchanter
    (8630017, 2000036, 100, 1, 1), # Corrupted Advanced Magician | Reindeer Milk
    (8630017, 4021032, 7500, 1, 1), # Corrupted Advanced Magician | Mana Crystal
    (8630017, 4009149, 7500, 1, 1), # Corrupted Advanced Magician | Advanced Magician's Cloth
    (8630017, 2000037, 100, 1, 1), # Corrupted Advanced Magician | Sunrise Dew
    (3502006, 2431174, 100, 1, 1), # Warmer Bot | Medal of Honor
    (3502006, 4001807, 7500, 1, 1), # Warmer Bot | Burnt Material
    (8630016, 4021020, 10, 1, 1), # Corrupted Intermediate Magician | Confusion Fragment
    (8630016, 4021034, 100, 1, 1), # Corrupted Intermediate Magician | Fine Spell Essence
    (8630016, 4021035, 100, 1, 1), # Corrupted Intermediate Magician | Grand Spell Essence
    (8630016, 4021033, 100, 1, 1), # Corrupted Intermediate Magician | Basic Spell Essence
    (8630016, 2000037, 100, 1, 1), # Corrupted Intermediate Magician | Sunrise Dew
    (8630016, 4021032, 7500, 1, 1), # Corrupted Intermediate Magician | Mana Crystal
    (8630016, 2000036, 100, 1, 1), # Corrupted Intermediate Magician | Reindeer Milk
    (8630016, 2431174, 100, 1, 1), # Corrupted Intermediate Magician | Medal of Honor
    (8630016, 2590004, 100, 1, 1), # Corrupted Intermediate Magician | Prototype Soul Enchanter
    (8630016, 4009148, 7500, 1, 1), # Corrupted Intermediate Magician | Intermediate Magician's Cloth
    (8630016, 4021031, 50, 1, 1), # Corrupted Intermediate Magician | Twisted Time
    (3502001, 4001802, 7500, 1, 1), # Fish Grumpil | Boulder Chunk
    (3502001, 2431174, 100, 1, 1), # Fish Grumpil | Medal of Honor
    (5250004, 4000674, 7500, 1, 1), # Chao | Chao's Tusk
    (5250004, 4033109, 500, 1, 1), # Chao | Forest Stone
    (8800002, 4001083, 7500, 1, 1), # Zakum | Zakum Certificate
    (8800002, 4310029, 7500, 1, 1), # Zakum | Crusader Coins
    (8800002, 2433103, 100, 1, 1), # Zakum | Boss Medal of Honor
    (3502000, 4001801, 7500, 1, 1), # Ammonite Grumpil | Rubble Fragment
    (3502000, 2431174, 100, 1, 1), # Ammonite Grumpil | Medal of Honor
    (5250005, 4033109, 500, 1, 1), # Ancient Fairy | Forest Stone
    (5250005, 4000675, 7500, 1, 1), # Ancient Fairy | Ancient Fairy's Wings
    (3502003, 2431174, 100, 1, 1), # Polluter Barrel | Medal of Honor
    (3502003, 4001804, 7500, 1, 1), # Polluter Barrel | Stinky Oil
    (5250006, 4033109, 500, 1, 1), # Shining Fairy | Forest Stone
    (5250006, 4000676, 7500, 1, 1), # Shining Fairy | Shining Fairy's Wings
    (3502002, 4001803, 7500, 1, 1), # Corrupter Barrels | Filthy Gel
    (3502002, 2431174, 100, 1, 1), # Corrupter Barrels | Medal of Honor
    (5250007, 2431752, 3000, 1, 1), # Ephenia | Ephenia's Soul Shard
    (5250007, 4033109, 500, 1, 1), # Ephenia | Forest Stone
    (3502008, 4034229, 7500, 1, 1), # Demolishizer | Dented Steel Plate
    (3502008, 2431174, 100, 1, 1), # Demolishizer | Medal of Honor
    (8800022, 2433103, 100, 1, 1), # Crumbling Zakum | Boss Medal of Honor
    (3503009, 4021032, 7500, 1, 1), # Dark Demon Wolfmaster | Mana Crystal
    (3503009, 4001868, 7500, 1, 1), # Dark Demon Wolfmaster | Faint Stigma Spirit Stone
    (3503009, 2590004, 100, 1, 1), # Dark Demon Wolfmaster | Prototype Soul Enchanter
    (3503009, 2000036, 100, 1, 1), # Dark Demon Wolfmaster | Reindeer Milk
    (3503009, 2000037, 100, 1, 1), # Dark Demon Wolfmaster | Sunrise Dew
    (3503009, 4021034, 100, 1, 1), # Dark Demon Wolfmaster | Fine Spell Essence
    (3503009, 4021033, 100, 1, 1), # Dark Demon Wolfmaster | Basic Spell Essence
    (3503009, 4021020, 10, 1, 1), # Dark Demon Wolfmaster | Confusion Fragment
    (3503009, 4034878, 7500, 1, 1), # Dark Demon Wolfmaster | Demon Wolf Fang
    (3503009, 4021031, 50, 1, 1), # Dark Demon Wolfmaster | Twisted Time
    (3503009, 2431174, 100, 1, 1), # Dark Demon Wolfmaster | Medal of Honor
    (3503009, 4021035, 100, 1, 1), # Dark Demon Wolfmaster | Grand Spell Essence
    (4230100, 4000023, 7500, 1, 1), # Cold Eye | Cold Eye Tail
    (4230100, 4034068, 7500, 1, 1), # Cold Eye | Cold Eye Cell Sample
    (4230100, 2870131, 100, 1, 1), # Cold Eye | Cold Eye Familiar
    (3503008, 2431174, 100, 1, 1), # Dark Demon Wolfrider | Medal of Honor
    (3503008, 2000036, 100, 1, 1), # Dark Demon Wolfrider | Reindeer Milk
    (3503008, 4021034, 100, 1, 1), # Dark Demon Wolfrider | Fine Spell Essence
    (3503008, 4001868, 7500, 1, 1), # Dark Demon Wolfrider | Faint Stigma Spirit Stone
    (3503008, 4021031, 50, 1, 1), # Dark Demon Wolfrider | Twisted Time
    (3503008, 4021035, 100, 1, 1), # Dark Demon Wolfrider | Grand Spell Essence
    (3503008, 4034878, 7500, 1, 1), # Dark Demon Wolfrider | Demon Wolf Fang
    (3503008, 2590004, 100, 1, 1), # Dark Demon Wolfrider | Prototype Soul Enchanter
    (3503008, 4021032, 7500, 1, 1), # Dark Demon Wolfrider | Mana Crystal
    (3503008, 4021033, 100, 1, 1), # Dark Demon Wolfrider | Basic Spell Essence
    (3503008, 2000037, 100, 1, 1), # Dark Demon Wolfrider | Sunrise Dew
    (3503008, 4021020, 10, 1, 1), # Dark Demon Wolfrider | Confusion Fragment
    (4230101, 4031013, 9000, 1, 1), # Zombie Lupin | Dark Marble
    (4230102, 2870133, 100, 1, 1), # Wraith | Wraith Familiar
    (4230102, 4031155, 7500, 1, 1), # Wraith | Broken Mirror Glass
    (4230102, 4000036, 7500, 1, 1), # Wraith | Strange Medicine
    (4230103, 4033055, 7500, 1, 1), # Iron Hog | Excavation Site Blueprint
    (4230103, 2870134, 100, 1, 1), # Iron Hog | Iron Hog Familiar
    (4230103, 4000039, 7500, 1, 1), # Iron Hog | Iron Hog's Metal Hoof
    (4230103, 2431174, 100, 1, 1), # Iron Hog | Medal of Honor
    (4230105, 4031200, 7500, 1, 1), # Nependeath | Sap of Nependeath
    (4230105, 2590004, 100, 1, 1), # Nependeath | Prototype Soul Enchanter
    (4230105, 2434021, 100, 1, 1), # Nependeath | Medal of Honor
    (4230105, 2001002, 100, 1, 1), # Nependeath | Very Special Sundae
    (4230105, 2022039, 100, 1, 1), # Nependeath | Nependeath's Honey
    (4230105, 2001001, 100, 1, 1), # Nependeath | Ice Cream Pop
    (4230105, 4000058, 7500, 1, 1), # Nependeath | Nependeath Seed
    (2230200, 4004000, 7500, 1, 1), # Flower Fish | Power Crystal Ore
    (2230200, 4010006, 7500, 1, 1), # Flower Fish | Gold Ore
    (2230200, 2070002, 100, 1, 1), # Flower Fish | Mokbi Throwing-Stars
    (2230200, 2040621, 100, 1, 1), # Flower Fish | Scroll for Bottomwear for HP 60%
    (2230200, 4000165, 7500, 1, 1), # Flower Fish | Flamboyant Petal
    (2230200, 2060000, 100, 1, 1), # Flower Fish | Arrow for Bow
    (2230200, 2512059, 100, 1, 1), # Flower Fish | Luck Boost Potion Recipe
    (2230200, 2048001, 100, 1, 1), # Flower Fish | Scroll for Pet Equip. for Speed 60%
    (2230200, 2001001, 100, 1, 1), # Flower Fish | Ice Cream Pop
    (2230200, 2000004, 100, 1, 1), # Flower Fish | Elixir
    (2230200, 2070005, 100, 1, 1), # Flower Fish | Steely Throwing-Knives
    (2230200, 2043212, 100, 1, 1), # Flower Fish | Scroll for One-Handed BW for Accuracy 60%
    (2230200, 2001527, 100, 1, 1), # Flower Fish | Unagi
    (2230200, 2870059, 100, 1, 1), # Flower Fish | Flower Fish Familiar
    (2230200, 2001526, 100, 1, 1), # Flower Fish | Pure Water
    (2230200, 4020002, 7500, 1, 1), # Flower Fish | AquaMarine Ore
    (4230106, 2434021, 100, 1, 1), # Lunar Pixie | Medal of Honor
    (4230106, 4021035, 100, 1, 1), # Lunar Pixie | Grand Spell Essence
    (4230106, 4031309, 7500, 1, 1), # Lunar Pixie | Cloud Piece
    (4230106, 4021033, 100, 1, 1), # Lunar Pixie | Basic Spell Essence
    (4230106, 2001002, 100, 1, 1), # Lunar Pixie | Very Special Sundae
    (4230106, 4000060, 7500, 1, 1), # Lunar Pixie | Lunar Pixie's Moonpiece
    (4230106, 4021032, 7500, 1, 1), # Lunar Pixie | Mana Crystal
    (4230106, 2590004, 100, 1, 1), # Lunar Pixie | Prototype Soul Enchanter
    (4230106, 2870136, 100, 1, 1), # Lunar Pixie | Lunar Pixie Familiar
    (4230106, 2001001, 100, 1, 1), # Lunar Pixie | Ice Cream Pop
    (4230106, 4021034, 100, 1, 1), # Lunar Pixie | Fine Spell Essence
    (4230107, 4021034, 100, 1, 1), # Flyeye | Fine Spell Essence
    (4230107, 2431174, 100, 1, 1), # Flyeye | Medal of Honor
    (4230107, 2870137, 100, 1, 1), # Flyeye | Flyeye Familiar
    (4230107, 4021032, 7500, 1, 1), # Flyeye | Mana Crystal
    (4230107, 4031309, 7500, 1, 1), # Flyeye | Cloud Piece
    (4230107, 4021035, 100, 1, 1), # Flyeye | Grand Spell Essence
    (4230107, 2000037, 100, 1, 1), # Flyeye | Sunrise Dew
    (4230107, 2001528, 100, 1, 1), # Flyeye | Melting Cheese
    (4230107, 2590004, 100, 1, 1), # Flyeye | Prototype Soul Enchanter
    (4230107, 4021033, 100, 1, 1), # Flyeye | Basic Spell Essence
    (4230107, 4000076, 7500, 1, 1), # Flyeye | Fly-Eye Wing
    (7130103, 2870282, 100, 1, 1), # Skeleton Commander | Skeleton Commander Familiar
    (7130103, 2434021, 100, 1, 1), # Skeleton Commander | Medal of Honor
    (7130103, 4000208, 7500, 1, 1), # Skeleton Commander | Horse Skull
    (4230108, 4000078, 7500, 1, 1), # [*]Jr. Cerebes | Jr. Cerebes Tooth
    (4230108, 2590004, 100, 1, 1), # [*]Jr. Cerebes | Prototype Soul Enchanter
    (4230108, 4021035, 100, 1, 1), # [*]Jr. Cerebes | Grand Spell Essence
    (4230108, 4021033, 100, 1, 1), # [*]Jr. Cerebes | Basic Spell Essence
    (4230108, 2870138, 100, 1, 1), # [*]Jr. Cerebes | [*]Jr. Cerebes Familiar
    (4230108, 2431174, 100, 1, 1), # [*]Jr. Cerebes | Medal of Honor
    (4230108, 4021032, 7500, 1, 1), # [*]Jr. Cerebes | Mana Crystal
    (4230108, 4021034, 100, 1, 1), # [*]Jr. Cerebes | Fine Spell Essence
    (4230108, 4021031, 50, 1, 1), # [*]Jr. Cerebes | Twisted Time
    (4230108, 2000036, 100, 1, 1), # [*]Jr. Cerebes | Reindeer Milk
    (4230108, 2000037, 100, 1, 1), # [*]Jr. Cerebes | Sunrise Dew
    (4230109, 4000101, 7500, 1, 1), # Block Golem | Yellow Toy Block
    (4230109, 2001002, 100, 1, 1), # Block Golem | Very Special Sundae
    (4230109, 4021035, 100, 1, 1), # Block Golem | Grand Spell Essence
    (4230109, 4021033, 100, 1, 1), # Block Golem | Basic Spell Essence
    (4230109, 4021032, 7500, 1, 1), # Block Golem | Mana Crystal
    (4230109, 2590004, 100, 1, 1), # Block Golem | Prototype Soul Enchanter
    (4230109, 4021034, 100, 1, 1), # Block Golem | Fine Spell Essence
    (4230109, 2870139, 100, 1, 1), # Block Golem | Block Golem Familiar
    (4230109, 2434021, 100, 1, 1), # Block Golem | Medal of Honor
    (4230109, 2001001, 100, 1, 1), # Block Golem | Ice Cream Pop
    (4230110, 2870140, 100, 1, 1), # King Block Golem | King Block Golem Familiar
    (4230110, 4000102, 7500, 1, 1), # King Block Golem | Blue Toy Block
    (4230110, 2870140, 100, 1, 1), # King Block Golem | King Block Golem Familiar
    (8240098, 4310029, 7500, 1, 1), # Lotus | Crusader Coins
    (8240098, 4001843, 7500, 1, 1), # Lotus | Extraordinary Energy Core (Grade S)
    (8240098, 2000005, 100, 1, 1), # Lotus | Power Elixir
    (8240098, 2433103, 100, 1, 1), # Lotus | Boss Medal of Honor
    (8240098, 2433593, 3000, 1, 1), # Lotus | Lotus's Soul Shard
    (8500002, 4001084, 7500, 1, 1), # Papulatus | Papulatus Certificate
    (8500002, 4031196, 7500, 1, 1), # Papulatus | Dark Tachion
    (2100100, 2000006, 100, 1, 1), # White Hare | Mana Elixir
    (2100100, 4000324, 7500, 1, 1), # White Hare | Clover
    (2100100, 2044601, 100, 1, 1), # White Hare | Scroll for Crossbow for ATT 60%
    (2100100, 4003004, 7500, 1, 1), # White Hare | Stiff Feather
    (2100100, 4020006, 7500, 1, 1), # White Hare | Topaz Ore
    (2100100, 2001001, 100, 1, 1), # White Hare | Ice Cream Pop
    (2100100, 2512055, 100, 1, 1), # White Hare | Blessing Potion Recipe
    (2100100, 2043112, 100, 1, 1), # White Hare | Scroll for One-Handed Axe for Accuracy 60%
    (2100100, 2870025, 100, 1, 1), # White Hare | White Hare Familiar
    (2100100, 2044801, 100, 1, 1), # White Hare | Scroll for Knuckle for Attack 60%
    (2100100, 2001002, 100, 1, 1), # White Hare | Very Special Sundae
    (2100100, 2001527, 100, 1, 1), # White Hare | Unagi
    (2100100, 4010007, 7500, 1, 1), # White Hare | Lidium Ore
    (2100100, 2049000, 100, 1, 1), # White Hare | Pure Clean Slate Scroll 1%
    (2100100, 2061000, 100, 1, 1), # White Hare | Arrow for Crossbow
    (2100100, 4010001, 7500, 1, 1), # White Hare | Steel Ore
    (4230116, 2431174, 100, 1, 1), # Barnard Gray | Medal of Honor
    (4230116, 2000037, 100, 1, 1), # Barnard Gray | Sunrise Dew
    (4230116, 4021033, 100, 1, 1), # Barnard Gray | Basic Spell Essence
    (4230116, 2512154, 100, 1, 1), # Barnard Gray | Advanced Strength Pill VI Recipe
    (4230116, 4021032, 7500, 1, 1), # Barnard Gray | Mana Crystal
    (4230116, 4021034, 100, 1, 1), # Barnard Gray | Fine Spell Essence
    (4230116, 4021031, 50, 1, 1), # Barnard Gray | Twisted Time
    (4230116, 2000036, 100, 1, 1), # Barnard Gray | Reindeer Milk
    (4230116, 2870146, 100, 1, 1), # Barnard Gray | Barnard Gray Familiar
    (4230116, 2590004, 100, 1, 1), # Barnard Gray | Prototype Soul Enchanter
    (4230116, 4021020, 10, 1, 1), # Barnard Gray | Confusion Fragment
    (4230116, 4021035, 100, 1, 1), # Barnard Gray | Grand Spell Essence
    (4230116, 4000117, 7500, 1, 1), # Barnard Gray | Space Food
    (2100101, 2061000, 100, 1, 1), # Brown Hare | Arrow for Crossbow
    (2100101, 4020001, 7500, 1, 1), # Brown Hare | Amethyst Ore
    (2100101, 2512054, 100, 1, 1), # Brown Hare | Mana Boost Potion Recipe
    (2100101, 2049000, 100, 1, 1), # Brown Hare | Pure Clean Slate Scroll 1%
    (2100101, 2044312, 100, 1, 1), # Brown Hare | Scroll for Spear for Accuracy 60%
    (2100101, 2000002, 100, 1, 1), # Brown Hare | White Potion
    (2100101, 2040621, 100, 1, 1), # Brown Hare | Scroll for Bottomwear for HP 60%
    (2100101, 2044602, 100, 1, 1), # Brown Hare | Scroll for Crossbow for ATT 10%
    (2100101, 2048004, 100, 1, 1), # Brown Hare | Scroll for Pet Equip. for Jump 60%
    (2100101, 2001001, 100, 1, 1), # Brown Hare | Ice Cream Pop
    (2100101, 2870026, 100, 1, 1), # Brown Hare | Brown Hare Card
    (2100101, 4000325, 7500, 1, 1), # Brown Hare | Carrot
    (2100101, 2001002, 100, 1, 1), # Brown Hare | Very Special Sundae
    (2100101, 4003004, 7500, 1, 1), # Brown Hare | Stiff Feather
    (2100101, 4010006, 7500, 1, 1), # Brown Hare | Gold Ore
    (2100101, 4010007, 7500, 1, 1), # Brown Hare | Lidium Ore
    (4230117, 2000036, 100, 1, 1), # Zeta Gray | Reindeer Milk
    (4230117, 2870147, 100, 1, 1), # Zeta Gray | Zeta Gray Familiar
    (4230117, 2590004, 100, 1, 1), # Zeta Gray | Prototype Soul Enchanter
    (4230117, 4021033, 100, 1, 1), # Zeta Gray | Basic Spell Essence
    (4230117, 2431174, 100, 1, 1), # Zeta Gray | Medal of Honor
    (4230117, 4021034, 100, 1, 1), # Zeta Gray | Fine Spell Essence
    (4230117, 4021032, 7500, 1, 1), # Zeta Gray | Mana Crystal
    (4230117, 4021035, 100, 1, 1), # Zeta Gray | Grand Spell Essence
    (4230117, 4021031, 50, 1, 1), # Zeta Gray | Twisted Time
    (4230117, 2000037, 100, 1, 1), # Zeta Gray | Sunrise Dew
    (4230117, 4021020, 10, 1, 1), # Zeta Gray | Confusion Fragment
    (2100102, 4010002, 7500, 1, 1), # Jr. Cactus | Mithril Ore
    (2100102, 2044012, 100, 1, 1), # Jr. Cactus | Scroll for Two-Handed Sword for Accuracy 60%
    (2100102, 2048003, 100, 1, 1), # Jr. Cactus | Scroll for Pet Equip. for Jump 100%
    (2100102, 2040705, 100, 1, 1), # Jr. Cactus | Scroll for Shoes for Jump 10%
    (2100102, 2000006, 100, 1, 1), # Jr. Cactus | Mana Elixir
    (2100102, 2001527, 100, 1, 1), # Jr. Cactus | Unagi
    (2100102, 4000329, 7500, 1, 1), # Jr. Cactus | Cactus Stem
    (2100102, 2022155, 100, 1, 1), # Jr. Cactus | Desert Mist
    (2100102, 2061000, 100, 1, 1), # Jr. Cactus | Arrow for Crossbow
    (2100102, 4020004, 7500, 1, 1), # Jr. Cactus | Opal Ore
    (2100102, 4010007, 7500, 1, 1), # Jr. Cactus | Lidium Ore
    (2100102, 2001001, 100, 1, 1), # Jr. Cactus | Ice Cream Pop
    (2100102, 2044312, 100, 1, 1), # Jr. Cactus | Scroll for Spear for Accuracy 60%
    (2100102, 2870027, 100, 1, 1), # Jr. Cactus | Jr. Cactus Familiar
    (2100102, 2070004, 100, 1, 1), # Jr. Cactus | Tobi Throwing-Stars
    (2100102, 2044102, 100, 1, 1), # Jr. Cactus | Scroll for Two-handed Axe for ATT 10%
    (4230118, 4021035, 100, 1, 1), # Ultra Gray | Grand Spell Essence
    (4230118, 4021033, 100, 1, 1), # Ultra Gray | Basic Spell Essence
    (4230118, 2431174, 100, 1, 1), # Ultra Gray | Medal of Honor
    (4230118, 4021032, 7500, 1, 1), # Ultra Gray | Mana Crystal
    (4230118, 2000036, 100, 1, 1), # Ultra Gray | Reindeer Milk
    (4230118, 4021031, 50, 1, 1), # Ultra Gray | Twisted Time
    (4230118, 2870148, 100, 1, 1), # Ultra Gray | Ultra Gray Familiar
    (4230118, 4021034, 100, 1, 1), # Ultra Gray | Fine Spell Essence
    (4230118, 4021020, 10, 1, 1), # Ultra Gray | Confusion Fragment
    (4230118, 2590004, 100, 1, 1), # Ultra Gray | Prototype Soul Enchanter
    (4230118, 2000037, 100, 1, 1), # Ultra Gray | Sunrise Dew
    (2100103, 4000330, 7500, 1, 1), # Cactus | Cactus Thorn
    (2100103, 2044001, 100, 1, 1), # Cactus | Scroll for Two-handed Sword for ATT 60%
    (2100103, 2022155, 100, 1, 1), # Cactus | Desert Mist
    (2100103, 2048002, 100, 1, 1), # Cactus | Scroll for Pet Equip. for Speed 10%
    (2100103, 2061000, 100, 1, 1), # Cactus | Arrow for Crossbow
    (2100103, 2001526, 100, 1, 1), # Cactus | Pure Water
    (2100103, 2512053, 100, 1, 1), # Cactus | Health Boost Potion Recipe
    (2100103, 4010003, 7500, 1, 1), # Cactus | Adamantium Ore
    (2100103, 2045201, 100, 1, 1), # Cactus | Scroll for Dual Bowgun ATT 60%
    (2100103, 2001527, 100, 1, 1), # Cactus | Unagi
    (2100103, 4010007, 7500, 1, 1), # Cactus | Lidium Ore
    (2100103, 2870028, 100, 1, 1), # Cactus | Cactus Familiar
    (2100103, 2044314, 100, 1, 1), # Cactus | Scroll for Spear for Accuracy 10%
    (2100103, 2070004, 100, 1, 1), # Cactus | Tobi Throwing-Stars
    (2100103, 2001001, 100, 1, 1), # Cactus | Ice Cream Pop
    (2100103, 4020004, 7500, 1, 1), # Cactus | Opal Ore
    (2100103, 2070003, 100, 1, 1), # Cactus | Kumbi Throwing-Stars
    (2100103, 2001002, 100, 1, 1), # Cactus | Very Special Sundae
    (2100103, 2043701, 100, 1, 1), # Cactus | Scroll for Wand for Magic Attack 60%
    (4230119, 4021035, 100, 1, 1), # Mateon | Grand Spell Essence
    (4230119, 2431174, 100, 1, 1), # Mateon | Medal of Honor
    (4230119, 2870149, 100, 1, 1), # Mateon | Mateon Familiar
    (4230119, 2590004, 100, 1, 1), # Mateon | Prototype Soul Enchanter
    (4230119, 4021031, 50, 1, 1), # Mateon | Twisted Time
    (4230119, 2000036, 100, 1, 1), # Mateon | Reindeer Milk
    (4230119, 4021032, 7500, 1, 1), # Mateon | Mana Crystal
    (4230119, 4021020, 10, 1, 1), # Mateon | Confusion Fragment
    (4230119, 4000120, 7500, 1, 1), # Mateon | Mateon's Tentacle
    (4230119, 4021033, 100, 1, 1), # Mateon | Basic Spell Essence
    (4230119, 4021034, 100, 1, 1), # Mateon | Fine Spell Essence
    (4230119, 2000037, 100, 1, 1), # Mateon | Sunrise Dew
    (2100104, 2001001, 100, 1, 1), # Royal Cactus | Ice Cream Pop
    (2100104, 2870029, 100, 1, 1), # Royal Cactus | Royal Cactus Familiar
    (2100104, 2043214, 100, 1, 1), # Royal Cactus | Scroll for One-Handed BW for Accuracy 10%
    (2100104, 2044412, 100, 1, 1), # Royal Cactus | Scroll for Pole-Arm for Accuracy 60%
    (2100104, 2512059, 100, 1, 1), # Royal Cactus | Luck Boost Potion Recipe
    (2100104, 2000002, 100, 1, 1), # Royal Cactus | White Potion
    (2100104, 4020005, 7500, 1, 1), # Royal Cactus | Sapphire Ore
    (2100104, 2044901, 100, 1, 1), # Royal Cactus | Scroll for Gun for Attack 60%
    (2100104, 4004001, 7500, 1, 1), # Royal Cactus | Wisdom Crystal Ore
    (2100104, 2001526, 100, 1, 1), # Royal Cactus | Pure Water
    (2100104, 4020006, 7500, 1, 1), # Royal Cactus | Topaz Ore
    (2100104, 2022155, 100, 1, 1), # Royal Cactus | Desert Mist
    (2100104, 4000331, 7500, 1, 1), # Royal Cactus | Cactus Flower
    (2100104, 2000006, 100, 1, 1), # Royal Cactus | Mana Elixir
    (2100104, 4010007, 7500, 1, 1), # Royal Cactus | Lidium Ore
    (2100104, 2043401, 100, 1, 1), # Royal Cactus | Scroll for Katara for ATT 60%
    (3503005, 4021020, 10, 1, 1), # Dark Demon Axemaster | Confusion Fragment
    (3503005, 4021035, 100, 1, 1), # Dark Demon Axemaster | Grand Spell Essence
    (3503005, 4001868, 7500, 1, 1), # Dark Demon Axemaster | Faint Stigma Spirit Stone
    (3503005, 4021033, 100, 1, 1), # Dark Demon Axemaster | Basic Spell Essence
    (3503005, 4034876, 7500, 1, 1), # Dark Demon Axemaster | Demon Axe
    (3503005, 2000037, 100, 1, 1), # Dark Demon Axemaster | Sunrise Dew
    (3503005, 4021034, 100, 1, 1), # Dark Demon Axemaster | Fine Spell Essence
    (3503005, 4021031, 50, 1, 1), # Dark Demon Axemaster | Twisted Time
    (3503005, 2431174, 100, 1, 1), # Dark Demon Axemaster | Medal of Honor
    (3503005, 4021032, 7500, 1, 1), # Dark Demon Axemaster | Mana Crystal
    (3503005, 2000036, 100, 1, 1), # Dark Demon Axemaster | Reindeer Milk
    (3503005, 2590004, 100, 1, 1), # Dark Demon Axemaster | Prototype Soul Enchanter
    (4230120, 4021034, 100, 1, 1), # Plateon | Fine Spell Essence
    (4230120, 4021032, 7500, 1, 1), # Plateon | Mana Crystal
    (4230120, 2000036, 100, 1, 1), # Plateon | Reindeer Milk
    (4230120, 4021020, 10, 1, 1), # Plateon | Confusion Fragment
    (4230120, 2000037, 100, 1, 1), # Plateon | Sunrise Dew
    (4230120, 4021031, 50, 1, 1), # Plateon | Twisted Time
    (4230120, 2590004, 100, 1, 1), # Plateon | Prototype Soul Enchanter
    (4230120, 2870150, 100, 1, 1), # Plateon | Plateon Familiar
    (4230120, 2431174, 100, 1, 1), # Plateon | Medal of Honor
    (4230120, 4021035, 100, 1, 1), # Plateon | Grand Spell Essence
    (4230120, 4000121, 7500, 1, 1), # Plateon | Plateon's Helmet
    (4230120, 4021033, 100, 1, 1), # Plateon | Basic Spell Essence
    (2100105, 4000328, 7500, 1, 1), # Bellamoa | Snake Rattle
    (2100105, 2043212, 100, 1, 1), # Bellamoa | Scroll for One-Handed BW for Accuracy 60%
    (2100105, 4006001, 7500, 1, 1), # Bellamoa | The Summoning Rock
    (2100105, 2043114, 100, 1, 1), # Bellamoa | Scroll for One-Handed Axe for Accuracy 10%
    (2100105, 2000006, 100, 1, 1), # Bellamoa | Mana Elixir
    (2100105, 2001001, 100, 1, 1), # Bellamoa | Ice Cream Pop
    (2100105, 2001002, 100, 1, 1), # Bellamoa | Very Special Sundae
    (2100105, 2044201, 100, 1, 1), # Bellamoa | Scroll for Two-handed BW for ATT 60%
    (2100105, 2870030, 100, 1, 1), # Bellamoa | Bellamoa Familiar
    (2100105, 4010004, 7500, 1, 1), # Bellamoa | Silver Ore
    (2100105, 2001527, 100, 1, 1), # Bellamoa | Unagi
    (2100105, 2044002, 100, 1, 1), # Bellamoa | Scroll for Two-handed Sword for ATT 10%
    (2100105, 2001526, 100, 1, 1), # Bellamoa | Pure Water
    (3503004, 2431174, 100, 1, 1), # Dark Demon Axeman | Medal of Honor
    (3503004, 4034876, 7500, 1, 1), # Dark Demon Axeman | Demon Axe
    (3503004, 2590004, 100, 1, 1), # Dark Demon Axeman | Prototype Soul Enchanter
    (3503004, 4021035, 100, 1, 1), # Dark Demon Axeman | Grand Spell Essence
    (3503004, 4021034, 100, 1, 1), # Dark Demon Axeman | Fine Spell Essence
    (3503004, 4021033, 100, 1, 1), # Dark Demon Axeman | Basic Spell Essence
    (3503004, 4021020, 10, 1, 1), # Dark Demon Axeman | Confusion Fragment
    (3503004, 2000037, 100, 1, 1), # Dark Demon Axeman | Sunrise Dew
    (3503004, 4021032, 7500, 1, 1), # Dark Demon Axeman | Mana Crystal
    (3503004, 4001868, 7500, 1, 1), # Dark Demon Axeman | Faint Stigma Spirit Stone
    (3503004, 2000036, 100, 1, 1), # Dark Demon Axeman | Reindeer Milk
    (3503004, 4021031, 50, 1, 1), # Dark Demon Axeman | Twisted Time
    (4230121, 4021033, 100, 1, 1), # Mecateon | Basic Spell Essence
    (4230121, 4021034, 100, 1, 1), # Mecateon | Fine Spell Essence
    (4230121, 2512158, 100, 1, 1), # Mecateon | Advanced Strength Pill X Recipe
    (4230121, 4021035, 100, 1, 1), # Mecateon | Grand Spell Essence
    (4230121, 2000036, 100, 1, 1), # Mecateon | Reindeer Milk
    (4230121, 2431174, 100, 1, 1), # Mecateon | Medal of Honor
    (4230121, 2000037, 100, 1, 1), # Mecateon | Sunrise Dew
    (4230121, 4021020, 10, 1, 1), # Mecateon | Confusion Fragment
    (4230121, 4021031, 50, 1, 1), # Mecateon | Twisted Time
    (4230121, 4021032, 7500, 1, 1), # Mecateon | Mana Crystal
    (4230121, 4000122, 7500, 1, 1), # Mecateon | Mecateon's Laser Gun
    (4230121, 2590004, 100, 1, 1), # Mecateon | Prototype Soul Enchanter
    (4230121, 2870151, 100, 1, 1), # Mecateon | Mecateon Familiar
    (2100106, 4020007, 7500, 1, 1), # Ear Plug Plead | Diamond Ore
    (2100106, 2048005, 100, 1, 1), # Ear Plug Plead | Scroll for Pet Equip. for Jump 10%
    (2100106, 4000326, 7500, 1, 1), # Ear Plug Plead | Earmuff
    (2100106, 2870031, 100, 1, 1), # Ear Plug Plead | Ear Plug Plead Familiar
    (2100106, 2000006, 100, 1, 1), # Ear Plug Plead | Mana Elixir
    (2100106, 2512057, 100, 1, 1), # Ear Plug Plead | Dexterity Boost Potion Recipe
    (2100106, 2000004, 100, 1, 1), # Ear Plug Plead | Elixir
    (2100106, 4010007, 7500, 1, 1), # Ear Plug Plead | Lidium Ore
    (2100106, 2044101, 100, 1, 1), # Ear Plug Plead | Scroll for Two-handed Axe for ATT 60%
    (2100106, 2001527, 100, 1, 1), # Ear Plug Plead | Unagi
    (2100106, 4020000, 7500, 1, 1), # Ear Plug Plead | Garnet Ore
    (2100106, 2061000, 100, 1, 1), # Ear Plug Plead | Arrow for Crossbow
    (2100106, 2048004, 100, 1, 1), # Ear Plug Plead | Scroll for Pet Equip. for Jump 60%
    (3503007, 4021020, 10, 1, 1), # Dark Demon Shieldmaster | Confusion Fragment
    (3503007, 4021033, 100, 1, 1), # Dark Demon Shieldmaster | Basic Spell Essence
    (3503007, 4021031, 50, 1, 1), # Dark Demon Shieldmaster | Twisted Time
    (3503007, 4001868, 7500, 1, 1), # Dark Demon Shieldmaster | Faint Stigma Spirit Stone
    (3503007, 2431174, 100, 1, 1), # Dark Demon Shieldmaster | Medal of Honor
    (3503007, 2590004, 100, 1, 1), # Dark Demon Shieldmaster | Prototype Soul Enchanter
    (3503007, 4021032, 7500, 1, 1), # Dark Demon Shieldmaster | Mana Crystal
    (3503007, 4034877, 7500, 1, 1), # Dark Demon Shieldmaster | Demon Shield
    (3503007, 2000036, 100, 1, 1), # Dark Demon Shieldmaster | Reindeer Milk
    (3503007, 2000037, 100, 1, 1), # Dark Demon Shieldmaster | Sunrise Dew
    (3503007, 4021034, 100, 1, 1), # Dark Demon Shieldmaster | Fine Spell Essence
    (3503007, 4021035, 100, 1, 1), # Dark Demon Shieldmaster | Grand Spell Essence
    (2100107, 2001001, 100, 1, 1), # Scarf Plead | Ice Cream Pop
    (2100107, 4010007, 7500, 1, 1), # Scarf Plead | Lidium Ore
    (2100107, 2070002, 100, 1, 1), # Scarf Plead | Mokbi Throwing-Stars
    (2100107, 4020003, 7500, 1, 1), # Scarf Plead | Emerald Ore
    (2100107, 4000327, 7500, 1, 1), # Scarf Plead | Ragged Scarf
    (2100107, 2001002, 100, 1, 1), # Scarf Plead | Very Special Sundae
    (2100107, 2870032, 100, 1, 1), # Scarf Plead | Scarf Plead Familiar
    (2100107, 2043001, 100, 1, 1), # Scarf Plead | Scroll for One-Handed Sword for ATT 60%
    (2100107, 2001527, 100, 1, 1), # Scarf Plead | Unagi
    (2100107, 2043112, 100, 1, 1), # Scarf Plead | Scroll for One-Handed Axe for Accuracy 60%
    (2100107, 2061000, 100, 1, 1), # Scarf Plead | Arrow for Crossbow
    (2100107, 2048002, 100, 1, 1), # Scarf Plead | Scroll for Pet Equip. for Speed 10%
    (2100107, 2001526, 100, 1, 1), # Scarf Plead | Pure Water
    (2100107, 4020002, 7500, 1, 1), # Scarf Plead | AquaMarine Ore
    (3503006, 4034877, 7500, 1, 1), # Dark Demon Shieldbearer | Demon Shield
    (3503006, 2431174, 100, 1, 1), # Dark Demon Shieldbearer | Medal of Honor
    (3503006, 4021032, 7500, 1, 1), # Dark Demon Shieldbearer | Mana Crystal
    (3503006, 4021033, 100, 1, 1), # Dark Demon Shieldbearer | Basic Spell Essence
    (3503006, 4001868, 7500, 1, 1), # Dark Demon Shieldbearer | Faint Stigma Spirit Stone
    (3503006, 2000037, 100, 1, 1), # Dark Demon Shieldbearer | Sunrise Dew
    (3503006, 4021020, 10, 1, 1), # Dark Demon Shieldbearer | Confusion Fragment
    (3503006, 4021035, 100, 1, 1), # Dark Demon Shieldbearer | Grand Spell Essence
    (3503006, 4021034, 100, 1, 1), # Dark Demon Shieldbearer | Fine Spell Essence
    (3503006, 2590004, 100, 1, 1), # Dark Demon Shieldbearer | Prototype Soul Enchanter
    (3503006, 2000036, 100, 1, 1), # Dark Demon Shieldbearer | Reindeer Milk
    (3503006, 4021031, 50, 1, 1), # Dark Demon Shieldbearer | Twisted Time
    (4230123, 2870152, 100, 1, 1), # Sparker | Sparker Familiar
    (4230123, 4031266, 100, 1, 1), # Sparker | Sparker's DNA Sample
    (4230123, 4000156, 7500, 1, 1), # Sparker | Seal Tooth
    (4230123, 4000157, 7500, 1, 1), # Sparker | Seal Meat
    (2100108, 4010007, 7500, 1, 1), # Meerkat | Lidium Ore
    (2100108, 4010001, 7500, 1, 1), # Meerkat | Steel Ore
    (2100108, 2040801, 100, 1, 1), # Meerkat | Scroll for Gloves for DEX 60%
    (2100108, 4003004, 7500, 1, 1), # Meerkat | Stiff Feather
    (2100108, 2044214, 100, 1, 1), # Meerkat | Scroll for Two-Handed BW for Accuracy 10%
    (2100108, 2060000, 100, 1, 1), # Meerkat | Arrow for Bow
    (2100108, 4010002, 7500, 1, 1), # Meerkat | Mithril Ore
    (2100108, 4004002, 7500, 1, 1), # Meerkat | DEX Crystal Ore
    (2100108, 2001002, 100, 1, 1), # Meerkat | Very Special Sundae
    (2100108, 4000333, 7500, 1, 1), # Meerkat | Telescope
    (2100108, 2000002, 100, 1, 1), # Meerkat | White Potion
    (2100108, 2512260, 100, 1, 1), # Meerkat | Giant Potion Recipe
    (2100108, 2043002, 100, 1, 1), # Meerkat | Scroll for One-Handed Sword for ATT 10%
    (2100108, 2044014, 100, 1, 1), # Meerkat | Scroll for Two-Handed Sword for Accuracy 10%
    (2100108, 2330003, 100, 1, 1), # Meerkat | Vital Bullet
    (2100108, 2044212, 100, 1, 1); # Meerkat | Scroll for Two-Handed BW for Accuracy 60%

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (2100108, 2001526, 100, 1, 1), # Meerkat | Pure Water
    (2100108, 2048004, 100, 1, 1), # Meerkat | Scroll for Pet Equip. for Jump 60%
    (2100108, 2870033, 100, 1, 1), # Meerkat | Meerkat Familiar
    (3503001, 4021033, 100, 1, 1), # Defiled Tree Sap | Basic Spell Essence
    (3503001, 2000037, 100, 1, 1), # Defiled Tree Sap | Sunrise Dew
    (3503001, 4021034, 100, 1, 1), # Defiled Tree Sap | Fine Spell Essence
    (3503001, 4021031, 50, 1, 1), # Defiled Tree Sap | Twisted Time
    (3503001, 4021035, 100, 1, 1), # Defiled Tree Sap | Grand Spell Essence
    (3503001, 2590004, 100, 1, 1), # Defiled Tree Sap | Prototype Soul Enchanter
    (3503001, 2431174, 100, 1, 1), # Defiled Tree Sap | Medal of Honor
    (3503001, 4021032, 7500, 1, 1), # Defiled Tree Sap | Mana Crystal
    (3503001, 4001868, 7500, 1, 1), # Defiled Tree Sap | Faint Stigma Spirit Stone
    (3503001, 2000036, 100, 1, 1), # Defiled Tree Sap | Reindeer Milk
    (3503001, 4021020, 10, 1, 1), # Defiled Tree Sap | Confusion Fragment
    (4230124, 4031267, 100, 1, 1), # Freezer | Freezer's DNA Sample
    (4230124, 2870153, 100, 1, 1), # Freezer | Freezer Familiar
    (4230124, 4000157, 7500, 1, 1), # Freezer | Seal Meat
    (4230124, 4000156, 7500, 1, 1), # Freezer | Seal Tooth
    (4230124, 4000155, 7500, 1, 1), # Freezer | Seal Skin
    (3503000, 4021035, 100, 1, 1), # Polluted Tree Sap | Grand Spell Essence
    (3503000, 2431174, 100, 1, 1), # Polluted Tree Sap | Medal of Honor
    (3503000, 2590004, 100, 1, 1), # Polluted Tree Sap | Prototype Soul Enchanter
    (3503000, 2000037, 100, 1, 1), # Polluted Tree Sap | Sunrise Dew
    (3503000, 4021020, 10, 1, 1), # Polluted Tree Sap | Confusion Fragment
    (3503000, 4021031, 50, 1, 1), # Polluted Tree Sap | Twisted Time
    (3503000, 4001868, 7500, 1, 1), # Polluted Tree Sap | Faint Stigma Spirit Stone
    (3503000, 2000036, 100, 1, 1), # Polluted Tree Sap | Reindeer Milk
    (3503000, 4021033, 100, 1, 1), # Polluted Tree Sap | Basic Spell Essence
    (3503000, 4021032, 7500, 1, 1), # Polluted Tree Sap | Mana Crystal
    (3503000, 4021034, 100, 1, 1), # Polluted Tree Sap | Fine Spell Essence
    (4230125, 2870154, 100, 1, 1), # Skeledog | Skeledog Familiar
    (4230125, 4000204, 7500, 1, 1), # Skeledog | Skeledog's Bone
    (4230125, 2431174, 100, 1, 1), # Skeledog | Medal of Honor
    (3503003, 4034875, 7500, 1, 1), # Dark Demon Swordmaster | Demon Sword
    (3503003, 2431174, 100, 1, 1), # Dark Demon Swordmaster | Medal of Honor
    (3503003, 4021033, 100, 1, 1), # Dark Demon Swordmaster | Basic Spell Essence
    (3503003, 4001868, 7500, 1, 1), # Dark Demon Swordmaster | Faint Stigma Spirit Stone
    (3503003, 2590004, 100, 1, 1), # Dark Demon Swordmaster | Prototype Soul Enchanter
    (3503003, 4021034, 100, 1, 1), # Dark Demon Swordmaster | Fine Spell Essence
    (3503003, 4021031, 50, 1, 1), # Dark Demon Swordmaster | Twisted Time
    (3503003, 4021032, 7500, 1, 1), # Dark Demon Swordmaster | Mana Crystal
    (3503003, 4021035, 100, 1, 1), # Dark Demon Swordmaster | Grand Spell Essence
    (3503003, 4021020, 10, 1, 1), # Dark Demon Swordmaster | Confusion Fragment
    (3503003, 2000036, 100, 1, 1), # Dark Demon Swordmaster | Reindeer Milk
    (3503003, 2000037, 100, 1, 1), # Dark Demon Swordmaster | Sunrise Dew
    (4230126, 2001002, 100, 1, 1), # Mummydog | Very Special Sundae
    (4230126, 4000205, 7500, 1, 1), # Mummydog | Dirty Bandage
    (4230126, 2001001, 100, 1, 1), # Mummydog | Ice Cream Pop
    (4230126, 2870155, 100, 1, 1), # Mummydog | Mummydog Familiar
    (4230126, 2431174, 100, 1, 1), # Mummydog | Medal of Honor
    (4230126, 2590004, 100, 1, 1), # Mummydog | Prototype Soul Enchanter
    (3503002, 4021031, 50, 1, 1), # Dark Demon Swordsman | Twisted Time
    (3503002, 4021035, 100, 1, 1), # Dark Demon Swordsman | Grand Spell Essence
    (3503002, 2431174, 100, 1, 1), # Dark Demon Swordsman | Medal of Honor
    (3503002, 2000036, 100, 1, 1), # Dark Demon Swordsman | Reindeer Milk
    (3503002, 2590004, 100, 1, 1), # Dark Demon Swordsman | Prototype Soul Enchanter
    (3503002, 4001868, 7500, 1, 1), # Dark Demon Swordsman | Faint Stigma Spirit Stone
    (3503002, 4034875, 7500, 1, 1), # Dark Demon Swordsman | Demon Sword
    (3503002, 4021020, 10, 1, 1), # Dark Demon Swordsman | Confusion Fragment
    (3503002, 4021032, 7500, 1, 1), # Dark Demon Swordsman | Mana Crystal
    (3503002, 4021034, 100, 1, 1), # Dark Demon Swordsman | Fine Spell Essence
    (3503002, 2000037, 100, 1, 1), # Dark Demon Swordsman | Sunrise Dew
    (3503002, 4021033, 100, 1, 1), # Dark Demon Swordsman | Basic Spell Essence
    (8930100, 2433103, 100, 1, 1), # Vellum | Boss Medal of Honor
    (8930100, 4310029, 7500, 1, 1), # Vellum | Crusader Coins
    (8840006, 4310010, 7500, 1, 1), # Von Leon | Royal Lion King Medal
    (8840006, 4021032, 7500, 1, 1), # Von Leon | Mana Crystal
    (8840006, 2512264, 100, 1, 1), # Von Leon | Special Von Leon Transform Potion Recipe
    (8840006, 2431662, 3000, 1, 1), # Von Leon | Von Leon Soul Shard
    (8840006, 4021033, 100, 1, 1), # Von Leon | Basic Spell Essence
    (8840006, 2000005, 100, 1, 1), # Von Leon | Power Elixir
    (8840006, 2433103, 100, 1, 1), # Von Leon | Boss Medal of Honor
    (8840006, 4021034, 100, 1, 1), # Von Leon | Fine Spell Essence
    (8840006, 4021035, 100, 1, 1), # Von Leon | Grand Spell Essence
    (8840006, 4021031, 50, 1, 1), # Von Leon | Twisted Time
    (8840006, 2070005, 100, 1, 1), # Von Leon | Steely Throwing-Knives
    (8840007, 2433103, 100, 1, 1), # Von Leon | Boss Medal of Honor
    (7130020, 4021033, 100, 1, 1), # Goby | Basic Spell Essence
    (7130020, 4021032, 7500, 1, 1), # Goby | Mana Crystal
    (7130020, 4021034, 100, 1, 1), # Goby | Fine Spell Essence
    (7130020, 4021031, 50, 1, 1), # Goby | Twisted Time
    (7130020, 4000179, 7500, 1, 1), # Goby | A Bundle of Goby
    (7130020, 4021035, 100, 1, 1), # Goby | Grand Spell Essence
    (3210208, 4000095, 7500, 1, 1), # Retz | Rat Trap
    (3210208, 2870087, 100, 1, 1), # Retz | Retz Familiar
    (2600903, 2590004, 100, 1, 1), # Frozen Vanity | Prototype Soul Enchanter
    (2600903, 2590004, 100, 1, 1), # Frozen Vanity | Prototype Soul Enchanter
    (2600903, 4021032, 7500, 1, 1), # Frozen Vanity | Mana Crystal
    (2600903, 2431174, 100, 1, 1), # Frozen Vanity | Medal of Honor
    (2600903, 4021020, 10, 1, 1), # Frozen Vanity | Confusion Fragment
    (2600903, 4021035, 100, 1, 1), # Frozen Vanity | Grand Spell Essence
    (2600903, 4021032, 7500, 1, 1), # Frozen Vanity | Mana Crystal
    (2600903, 4021033, 100, 1, 1), # Frozen Vanity | Basic Spell Essence
    (2600903, 4021034, 100, 1, 1), # Frozen Vanity | Fine Spell Essence
    (2600903, 4021020, 10, 1, 1), # Frozen Vanity | Confusion Fragment
    (2600903, 4033927, 7500, 1, 1), # Frozen Vanity | Frozen Energy
    (2600903, 2000036, 100, 1, 1), # Frozen Vanity | Reindeer Milk
    (2600903, 2000037, 100, 1, 1), # Frozen Vanity | Sunrise Dew
    (2600903, 4009136, 7500, 1, 1), # Frozen Vanity | Broken Feather (Blue)
    (2600903, 4021031, 50, 1, 1), # Frozen Vanity | Twisted Time
    (2600903, 2000036, 100, 1, 1), # Frozen Vanity | Reindeer Milk
    (2600903, 4021033, 100, 1, 1), # Frozen Vanity | Basic Spell Essence
    (2600903, 2431174, 100, 1, 1), # Frozen Vanity | Medal of Honor
    (2600903, 4021035, 100, 1, 1), # Frozen Vanity | Grand Spell Essence
    (2600903, 4021031, 50, 1, 1), # Frozen Vanity | Twisted Time
    (2600903, 2000037, 100, 1, 1), # Frozen Vanity | Sunrise Dew
    (2600903, 4021034, 100, 1, 1), # Frozen Vanity | Fine Spell Essence
    (8800102, 4310029, 7500, 1, 1), # Chaos Zakum | Crusader Coins
    (8800102, 4001083, 7500, 1, 1), # Chaos Zakum | Zakum Certificate
    (2600902, 4021031, 50, 1, 1), # Frozen Anxiety | Twisted Time
    (2600902, 4021035, 100, 1, 1), # Frozen Anxiety | Grand Spell Essence
    (2600902, 2431174, 100, 1, 1), # Frozen Anxiety | Medal of Honor
    (2600902, 4021033, 100, 1, 1), # Frozen Anxiety | Basic Spell Essence
    (2600902, 4021020, 10, 1, 1), # Frozen Anxiety | Confusion Fragment
    (2600902, 4021020, 10, 1, 1), # Frozen Anxiety | Confusion Fragment
    (2600902, 2000036, 100, 1, 1), # Frozen Anxiety | Reindeer Milk
    (2600902, 4021032, 7500, 1, 1), # Frozen Anxiety | Mana Crystal
    (2600902, 4021033, 100, 1, 1), # Frozen Anxiety | Basic Spell Essence
    (2600902, 2590004, 100, 1, 1), # Frozen Anxiety | Prototype Soul Enchanter
    (2600902, 4021035, 100, 1, 1), # Frozen Anxiety | Grand Spell Essence
    (2600902, 2000037, 100, 1, 1), # Frozen Anxiety | Sunrise Dew
    (2600902, 4009135, 7500, 1, 1), # Frozen Anxiety | Broken Bow (Blue)
    (2600902, 4021034, 100, 1, 1), # Frozen Anxiety | Fine Spell Essence
    (2600902, 4021032, 7500, 1, 1), # Frozen Anxiety | Mana Crystal
    (2600902, 2590004, 100, 1, 1), # Frozen Anxiety | Prototype Soul Enchanter
    (2600902, 2431174, 100, 1, 1), # Frozen Anxiety | Medal of Honor
    (2600902, 2000036, 100, 1, 1), # Frozen Anxiety | Reindeer Milk
    (2600902, 2000037, 100, 1, 1), # Frozen Anxiety | Sunrise Dew
    (2600902, 4033927, 7500, 1, 1), # Frozen Anxiety | Frozen Energy
    (2600902, 4021031, 50, 1, 1), # Frozen Anxiety | Twisted Time
    (2600902, 4021034, 100, 1, 1), # Frozen Anxiety | Fine Spell Essence
    (2600902, 4033927, 7500, 1, 1), # Frozen Anxiety | Frozen Energy
    (4034527, 4021035, 100, 1, 1), # ? | Grand Spell Essence
    (4034527, 4021032, 7500, 1, 1), # ? | Mana Crystal
    (4034527, 4021034, 100, 1, 1), # ? | Fine Spell Essence
    (4034527, 4021033, 100, 1, 1), # ? | Basic Spell Essence
    (2600901, 2590004, 100, 1, 1), # Frozen Rage | Prototype Soul Enchanter
    (2600901, 4021034, 100, 1, 1), # Frozen Rage | Fine Spell Essence
    (2600901, 2000037, 100, 1, 1), # Frozen Rage | Sunrise Dew
    (2600901, 4021035, 100, 1, 1), # Frozen Rage | Grand Spell Essence
    (2600901, 4021031, 50, 1, 1), # Frozen Rage | Twisted Time
    (2600901, 4021031, 50, 1, 1), # Frozen Rage | Twisted Time
    (2600901, 4021033, 100, 1, 1), # Frozen Rage | Basic Spell Essence
    (2600901, 2431174, 100, 1, 1), # Frozen Rage | Medal of Honor
    (2600901, 2431174, 100, 1, 1), # Frozen Rage | Medal of Honor
    (2600901, 4033927, 7500, 1, 1), # Frozen Rage | Frozen Energy
    (2600901, 2590004, 100, 1, 1), # Frozen Rage | Prototype Soul Enchanter
    (2600901, 4021020, 10, 1, 1), # Frozen Rage | Confusion Fragment
    (2600901, 4021020, 10, 1, 1), # Frozen Rage | Confusion Fragment
    (2600901, 4021034, 100, 1, 1), # Frozen Rage | Fine Spell Essence
    (2600901, 4021035, 100, 1, 1), # Frozen Rage | Grand Spell Essence
    (2600901, 2000036, 100, 1, 1), # Frozen Rage | Reindeer Milk
    (2600901, 2000036, 100, 1, 1), # Frozen Rage | Reindeer Milk
    (2600901, 2000037, 100, 1, 1), # Frozen Rage | Sunrise Dew
    (2600901, 4021033, 100, 1, 1), # Frozen Rage | Basic Spell Essence
    (2600901, 4033927, 7500, 1, 1), # Frozen Rage | Frozen Energy
    (2600901, 4021032, 7500, 1, 1), # Frozen Rage | Mana Crystal
    (2600901, 4009134, 7500, 1, 1), # Frozen Rage | Broken Shaft (Blue)
    (2600901, 4021032, 7500, 1, 1), # Frozen Rage | Mana Crystal
    (2600900, 2590004, 100, 1, 1), # Frozen Terror | Prototype Soul Enchanter
    (2600900, 4021034, 100, 1, 1), # Frozen Terror | Fine Spell Essence
    (2600900, 4033927, 7500, 1, 1), # Frozen Terror | Frozen Energy
    (2600900, 4021035, 100, 1, 1), # Frozen Terror | Grand Spell Essence
    (2600900, 4021020, 10, 1, 1), # Frozen Terror | Confusion Fragment
    (2600900, 4021033, 100, 1, 1), # Frozen Terror | Basic Spell Essence
    (2600900, 4021031, 50, 1, 1), # Frozen Terror | Twisted Time
    (2600900, 2000037, 100, 1, 1), # Frozen Terror | Sunrise Dew
    (2600900, 2431174, 100, 1, 1), # Frozen Terror | Medal of Honor
    (2600900, 4009133, 7500, 1, 1), # Frozen Terror | Broken Hilt (Blue)
    (2600900, 4021032, 7500, 1, 1), # Frozen Terror | Mana Crystal
    (2600900, 2000036, 100, 1, 1), # Frozen Terror | Reindeer Milk
    (3501008, 4000997, 7500, 1, 1), # Mole King | Turnip Sprout
    (3501008, 4000998, 7500, 1, 1), # Mole King | Mole King's Claw
    (3501008, 4000992, 7500, 1, 1), # Mole King | Lake Ellinel Muck
    (3501008, 4000993, 7500, 1, 1), # Mole King | Wisp's Desire
    (3501008, 4000995, 7500, 1, 1), # Mole King | Fairy Bookmark
    (3501008, 4034562, 7500, 1, 1), # Mole King | Fairy Dust
    (3501008, 4000991, 7500, 1, 1), # Mole King | Sparkling Wing
    (3501008, 4000996, 7500, 1, 1), # Mole King | Fresh Onion
    (3501008, 4000990, 7500, 1, 1), # Mole King | Alluring Antenna
    (3210200, 2870079, 100, 1, 1), # Jr. Cellion | Jr. Cellion Familiar
    (3210200, 4000073, 7500, 1, 1), # Jr. Cellion | Solid Horn
    (3210201, 2870080, 100, 1, 1), # Jr. Lioner | Jr. Lioner Familiar
    (3210201, 4000073, 7500, 1, 1), # Jr. Lioner | Solid Horn
    (3210202, 2870081, 100, 1, 1), # Jr. Grupin | Jr. Grupin Familiar
    (3210202, 4000073, 7500, 1, 1), # Jr. Grupin | Solid Horn
    (3210205, 4000096, 7500, 1, 1), # Black Ratz | Hard Walnut
    (3210205, 4021033, 100, 1, 1), # Black Ratz | Basic Spell Essence
    (3210205, 2001002, 100, 1, 1), # Black Ratz | Very Special Sundae
    (3210205, 4021034, 100, 1, 1), # Black Ratz | Fine Spell Essence
    (3210205, 4031093, 7500, 1, 1), # Black Ratz | Tasty Walnut
    (3210205, 2431174, 100, 1, 1), # Black Ratz | Medal of Honor
    (3210205, 2870084, 100, 1, 1), # Black Ratz | Black Ratz Familiar
    (3210205, 2001001, 100, 1, 1), # Black Ratz | Ice Cream Pop
    (3210205, 4021032, 7500, 1, 1), # Black Ratz | Mana Crystal
    (3210205, 2590004, 100, 1, 1), # Black Ratz | Prototype Soul Enchanter
    (3210205, 4021035, 100, 1, 1), # Black Ratz | Grand Spell Essence
    (3210205, 4032734, 7500, 1, 1), # Black Ratz | Lost Egg
    (2600905, 4021034, 100, 1, 1), # Frozen Terror | Fine Spell Essence
    (2600905, 2000036, 100, 1, 1), # Frozen Terror | Reindeer Milk
    (2600905, 2431174, 100, 1, 1), # Frozen Terror | Medal of Honor
    (2600905, 4021035, 100, 1, 1), # Frozen Terror | Grand Spell Essence
    (2600905, 4021032, 7500, 1, 1), # Frozen Terror | Mana Crystal
    (2600905, 2000037, 100, 1, 1), # Frozen Terror | Sunrise Dew
    (2600905, 4009133, 7500, 1, 1), # Frozen Terror | Broken Hilt (Blue)
    (2600905, 4021031, 50, 1, 1), # Frozen Terror | Twisted Time
    (2600905, 4021020, 10, 1, 1), # Frozen Terror | Confusion Fragment
    (2600905, 4021033, 100, 1, 1), # Frozen Terror | Basic Spell Essence
    (2600905, 4033927, 7500, 1, 1), # Frozen Terror | Frozen Energy
    (2600905, 2590004, 100, 1, 1), # Frozen Terror | Prototype Soul Enchanter
    (3210206, 2870085, 100, 1, 1), # Helly | Helly Familiar
    (3210206, 4000103, 7500, 1, 1), # Helly | Propeller
    (2600904, 4021031, 50, 1, 1), # Frozen Solitude | Twisted Time
    (2600904, 2000037, 100, 1, 1), # Frozen Solitude | Sunrise Dew
    (2600904, 4021020, 10, 1, 1), # Frozen Solitude | Confusion Fragment
    (2600904, 2431174, 100, 1, 1), # Frozen Solitude | Medal of Honor
    (2600904, 4021034, 100, 1, 1), # Frozen Solitude | Fine Spell Essence
    (2600904, 2590004, 100, 1, 1), # Frozen Solitude | Prototype Soul Enchanter
    (2600904, 4021034, 100, 1, 1), # Frozen Solitude | Fine Spell Essence
    (2600904, 4033927, 7500, 1, 1), # Frozen Solitude | Frozen Energy
    (2600904, 2000036, 100, 1, 1), # Frozen Solitude | Reindeer Milk
    (2600904, 4021033, 100, 1, 1), # Frozen Solitude | Basic Spell Essence
    (2600904, 4021032, 7500, 1, 1), # Frozen Solitude | Mana Crystal
    (2600904, 4021035, 100, 1, 1), # Frozen Solitude | Grand Spell Essence
    (2600904, 4021032, 7500, 1, 1), # Frozen Solitude | Mana Crystal
    (2600904, 4021031, 50, 1, 1), # Frozen Solitude | Twisted Time
    (2600904, 2590004, 100, 1, 1), # Frozen Solitude | Prototype Soul Enchanter
    (2600904, 2431174, 100, 1, 1), # Frozen Solitude | Medal of Honor
    (2600904, 2000036, 100, 1, 1), # Frozen Solitude | Reindeer Milk
    (2600904, 4021035, 100, 1, 1), # Frozen Solitude | Grand Spell Essence
    (2600904, 2000037, 100, 1, 1), # Frozen Solitude | Sunrise Dew
    (2600904, 4021020, 10, 1, 1), # Frozen Solitude | Confusion Fragment
    (2600904, 4009132, 7500, 1, 1), # Frozen Solitude | Broken Blade (Blue)
    (2600904, 4033927, 7500, 1, 1), # Frozen Solitude | Frozen Energy
    (2600904, 4021033, 100, 1, 1), # Frozen Solitude | Basic Spell Essence
    (3501005, 4000995, 7500, 1, 1), # Raging Tome | Fairy Bookmark
    (3501005, 2431769, 100, 1, 1), # Raging Tome | Schoolgirls' Note
    (3501004, 2431768, 100, 1, 1), # Grumpy Tome | Schoolboys' Note
    (3501004, 2000002, 100, 1, 1), # Grumpy Tome | White Potion
    (3501004, 2000006, 100, 1, 1), # Grumpy Tome | Mana Elixir
    (3501004, 4034562, 7500, 1, 1), # Grumpy Tome | Fairy Dust
    (3501007, 4000997, 7500, 1, 1), # Turnipy | Turnip Sprout
    (3501006, 4000996, 7500, 1, 1), # Oniony | Fresh Onion
    (3501001, 4000991, 7500, 1, 1), # Fairy Slime | Sparkling Wing
    (3501001, 4033825, 7500, 1, 1), # Fairy Slime | Starlight Crystal
    (7130000, 2870272, 100, 1, 1), # Lucida | Lucida Familiar
    (7130000, 4000074, 7500, 1, 1), # Lucida | Lucida Tail
    (3501000, 4033824, 7500, 1, 1), # Firefly Slime | Sparitum Leaf
    (3501000, 4000990, 7500, 1, 1), # Firefly Slime | Alluring Antenna
    (7130001, 4021035, 100, 1, 1), # [*]Cerebes | Grand Spell Essence
    (7130001, 2434021, 100, 1, 1), # [*]Cerebes | Medal of Honor
    (7130001, 4021032, 7500, 1, 1), # [*]Cerebes | Mana Crystal
    (7130001, 4021031, 50, 1, 1), # [*]Cerebes | Twisted Time
    (7130001, 4000079, 7500, 1, 1), # [*]Cerebes | Cerebes Tooth
    (7130001, 2590004, 100, 1, 1), # [*]Cerebes | Prototype Soul Enchanter
    (7130001, 4021033, 100, 1, 1), # [*]Cerebes | Basic Spell Essence
    (7130001, 4021034, 100, 1, 1), # [*]Cerebes | Fine Spell Essence
    (7130001, 2000036, 100, 1, 1), # [*]Cerebes | Reindeer Milk
    (7130001, 2001554, 100, 1, 1), # [*]Cerebes | Sunset Dew
    (7130001, 2870273, 100, 1, 1), # [*]Cerebes | [*]Cerebes Familiar
    (3501003, 2000002, 100, 1, 1), # Forest Sprite | White Potion
    (3501003, 2000003, 100, 1, 1), # Forest Sprite | Blue Potion
    (3501003, 4033826, 7500, 1, 1), # Forest Sprite | Cootie's Plant Encyclopedia
    (3501003, 4000992, 7500, 1, 1), # Forest Sprite | Lake Ellinel Muck
    (3501002, 4000992, 7500, 1, 1), # Water Sprite | Lake Ellinel Muck
    (3501002, 2000002, 100, 1, 1), # Water Sprite | White Potion
    (3501002, 2000003, 100, 1, 1), # Water Sprite | Blue Potion
    (3501002, 4033826, 7500, 1, 1), # Water Sprite | Cootie's Plant Encyclopedia
    (3501002, 4033827, 7500, 1, 1), # Water Sprite | Cootie's Report Page
    (1110100, 4033021, 7500, 1, 1), # Green Mushroom | Yellowish Green Mushroom Cap
    (2110200, 4033026, 7500, 1, 1), # Horny Mushroom | Red Horny Mushroom Cap
    (2110200, 4033025, 7500, 1, 1), # Horny Mushroom | Pie Honey
    (2110200, 4033024, 7500, 1, 1), # Horny Mushroom | Pie Flour
    (2220100, 4033029, 7500, 1, 1), # Blue Mushroom | Blue Mushroom Spore
    (5130101, 4033031, 7500, 1, 1), # Stone Golem | Cracked Stone Golem Rubble
    (5130102, 4000025, 7500, 1, 1), # Dark Stone Golem | Dark Stone Golem Rubble
    (5150000, 4000177, 7500, 1, 1), # Mixed Golem | Mixed Block
    (5150002, 4000671, 7500, 1, 1), # Icy Mixed Golem | Frozen Stone
    (5150003, 4000672, 7500, 1, 1), # Flaming Mixed Golem | Burning Stone
    (9600015, 4034635, 7500, 1, 1), # Male Thief | Intercepted Message
    (9600016, 4034635, 7500, 1, 1), # Female Thief | Intercepted Message
    (9600017, 4034636, 7500, 1, 1), # Censer | Venerable Abbot's Shiny Prayer Beads
    (9600024, 4034724, 7500, 1, 1), # Silver Spearman | Soft Leather
    (9600022, 4034724, 7500, 1, 1), # Silver Giant | Soft Leather
    (3401001, 4033625, 7500, 1, 1), # Coconut Slime | Willy's Ball
    (9390008, 4000870, 7500, 1, 1), # Savage Cold Shark | Ominous Onyx
    (9390008, 4000869, 7500, 1, 1), # Savage Cold Shark | Suspicious Onyx
    (9390007, 4000870, 7500, 1, 1), # Savage Shark | Ominous Onyx
    (9390007, 4000869, 7500, 1, 1), # Savage Shark | Suspicious Onyx
    (9390014, 4000870, 7500, 1, 1), # Savage Phantom Watch | Ominous Onyx
    (9390014, 4000869, 7500, 1, 1), # Savage Phantom Watch | Suspicious Onyx
    (9390015, 4000870, 7500, 1, 1), # Savage Grim Phantom Watch | Ominous Onyx
    (9390015, 4000869, 7500, 1, 1), # Savage Grim Phantom Watch | Suspicious Onyx
    (9390009, 4000870, 7500, 1, 1), # Savage Seaman Recruit Ghost | Ominous Onyx
    (9390009, 4000869, 7500, 1, 1), # Savage Seaman Recruit Ghost | Suspicious Onyx
    (9390010, 4000870, 7500, 1, 1), # Savage Lieutenant Commander Ghost | Ominous Onyx
    (9390010, 4000869, 7500, 1, 1), # Savage Lieutenant Commander Ghost | Suspicious Onyx
    (2400200, 4009219, 2000, 1, 1), # Caterpillar | Dried Caterpillar
    (2400201, 4009220, 2000, 1, 1), # Lady Bug | Spotted Wings
    (2400202, 4009221, 2000, 1, 1), # Sparrow | Sparrow Egg
    (2400203, 4009222, 2000, 1, 1), # Balloon Mouse | Mouse Tail
    (2400204, 4009223, 2000, 1, 1), # Red Poison Frog | Red Frog Legs
    (2400205, 4009224, 2000, 1, 1), # Green Poison Frog | Green Frog Legs
    (2400206, 4009225, 2000, 1, 1), # Delinquent Chick | Mohawk Feather
    (2400207, 4009226, 2000, 1, 1), # Punk Chick | Rocker Feather
    (2400208, 4009227, 2000, 1, 1), # Mane Fur Hyena | Mane Fur
    (2400209, 4009228, 2000, 1, 1), # Fang Hyena | Fang
    (100200, 4033072, 2000, 1, 1), # Starfish | Yellow Starfish Leg
    (9001012, 4032311, 10000, 1, 1), # Scarred Bear | Sign of Acceptance
    (9001013, 4032339, 10000, 1, 1), # Thief Crow | Red Jade
    (8641000, 4034914, 5000, 1, 1), # Happy Erda | Happy Memory
    (8641014, 4034920, 8000, 1, 1), # Soulful Erda | Hard Shell
    (8641007, 4034921, 8000, 1, 1), # Tranquil Erda | Tranquil Solvent
    (8641008, 4034937, 8000, 1, 1), # Lantern Erda | Tranquil Solvent
    (8641007, 4034937, 8000, 1, 1), # Tranquil Erda | Tranquil Solvent
    (8641008, 4034938, 8000, 1, 1), # Lantern Erda | Tranquil Solvent
    (8641007, 4034938, 8000, 1, 1), # Tranquil Erda | Tranquil Solvent
    (9001017, 4031013, 10000, 1, 1), # Shadow Blade Lord | Dark Marble
    (9001003, 4031059, 10000, 1, 1), # Dark Lord's Clone | Black Charm
    (9300730, 4000489, 10000, 1, 1), # Training Tino | Tutorial Tino's Feather
    (9300731, 4000489, 10000, 1, 1), # Training Tino | Tutorial Tino's Feather
    (9001000, 4031059, 10000, 1, 1), # Dances with Balrog's Clone | Black Charm
    (9001001, 4031059, 10000, 1, 1), # Grendel the Really Old's Clone | Black Charm
    (9001002, 4031059, 10000, 1, 1), # Athena Pierce's Clone | Black Charm
    (9001004, 4031059, 10000, 1, 1), # Shadow Kyrin | Black Charm
    (9001031, 4032737, 10000, 1, 1), # Schiller | Black Wings Report
    (9001031, 4032738, 10000, 1, 1), # Schiller | Black Wings Report
    (9001031, 4032739, 10000, 1, 1), # Schiller | Black Wings Report
    (9001031, 4034787, 10000, 1, 1), # Schiller | Black Wings Report
    (9001043, 4031343, 10000, 1, 1), # Manon, Herald of Heroes | The Heroic Pentagon
    (9001043, 4031514, 10000, 1, 1), # Manon, Herald of Heroes | Heroic Pentagon
    (9001043, 4031511, 10000, 1, 1), # Manon, Herald of Heroes | Heroic Pentagon
    (9001043, 4031517, 10000, 1, 1), # Manon, Herald of Heroes | Heroic Pentagon
    (9001043, 4031860, 10000, 1, 1), # Manon, Herald of Heroes | Heroic Pentagon
    (9001044, 4031344, 10000, 1, 1), # Griffey Herald of Heroes | The Heroic Star
    (9001044, 4031515, 10000, 1, 1), # Griffey Herald of Heroes | Heroic Star
    (9001044, 4031512, 10000, 1, 1), # Griffey Herald of Heroes | Heroic Star
    (9001044, 4031518, 10000, 1, 1), # Griffey Herald of Heroes | Heroic Star
    (9001044, 4031861, 10000, 1, 1), # Griffey Herald of Heroes | Heroic Star
    (9300732, 4033670, 10000, 1, 1), # Training Timu | Proof of Exam
    (8644000, 4036096, 7500, 1, 1), # Water Spirit | Essence of Water
    (8644000, 4036101, 7500, 1, 1), # Water Spirit | Essence of Water
    (8644001, 4036097, 7500, 1, 1), # Sun Spirit | Essence of Sunlight
    (8644001, 4036102, 7500, 1, 1), # Sun Spirit | Essence of Sunlight
    (8644002, 4036098, 7500, 1, 1), # Earth Spirit | Essence of Earth
    (8644002, 4036103, 7500, 1, 1), # Earth Spirit | Essence of Earth
    (8644005, 4036099, 7500, 1, 1), # Toxic Spirit | Deathcry
    (8644006, 4036100, 7500, 1, 1), # Volatile Spirit | Volatile Shriek
    (2400350, 4036344, 10000, 1, 1), # Desert Mush | Dried Tree Husk
    (2400351, 4036345, 10000, 1, 1), # Ferocious Desert Mush | Tough Meat
    (2400352, 4036346, 10000, 1, 1), # Sand Tortoise | Crumbled Rock
    (2400353, 4036347, 10000, 1, 1), # Crystal Sand Tortoise | Crushed Crystal
    (2400354, 4036348, 10000, 1, 1), # Crystal Hermit Crab | Decent Crystal
    (2400355, 4036349, 10000, 1, 1), # Bomber Hermit Crab | Exploding Eyeball
    (2400413, 4036162, 10000, 1, 1), # Mutant Hedgehog | Mechanical Parts
    (8880010, 2431964, 10000, 1, 1), # Magnus | Magnus's Soul Shard
    (8880010, 1152170, 2500, 1, 1), # Magnus | Royal Black Metal Shoulder
    (8880010, 1182087, 2500, 1, 1), # Magnus | Crystal Ventus Badge
    (8880010, 4310058, 10000, 5, 15), # Magnus | Magnus Coin
    (8880002, 1132164, 400, 1, 1), # Magnus | Elite Heliseum Warrior Belt
    (8880002, 1132165, 400, 1, 1), # Magnus | Elite Heliseum Magician Belt
    (8880002, 1132166, 400, 1, 1), # Magnus | Elite Heliseum Bowman Belt
    (8880002, 1132167, 400, 1, 1), # Magnus | Elite Heliseum Thief Belt
    (8880002, 1132168, 400, 1, 1), # Magnus | Elite Heliseum Pirate Belt
    (8880002, 2048703, 2500, 1, 1), # Magnus | Rebirth Flame Lv. 140
    (8880002, 4310058, 10000, 15, 35), # Magnus | Magnus Coin
    (8880000, 1072743, 250, 1, 1), # Magnus | Tyrant Hyades Boots
    (8880000, 1072744, 250, 1, 1), # Magnus | Tyrant Hermes Boots
    (8880000, 1072745, 250, 1, 1), # Magnus | Tyrant Charon Boots
    (8880000, 1072746, 250, 1, 1), # Magnus | Tyrant Lycaon Boots
    (8880000, 1072747, 250, 1, 1), # Magnus | Tyrant Altair Boots
    (8880000, 1082543, 250, 1, 1), # Magnus | Tyrant Hyades Gloves
    (8880000, 1082544, 250, 1, 1), # Magnus | Tyrant Hermes Gloves
    (8880000, 1082545, 250, 1, 1), # Magnus | Tyrant Charon Gloves
    (8880000, 1082546, 250, 1, 1), # Magnus | Tyrant Lycaon Gloves
    (8880000, 1082547, 250, 1, 1), # Magnus | Tyrant Altair Gloves
    (8880000, 1102481, 250, 1, 1), # Magnus | Tyrant Hyades Cloak
    (8880000, 1102482, 250, 1, 1), # Magnus | Tyrant Hermes Cloak
    (8880000, 1102483, 250, 1, 1), # Magnus | Tyrant Charon Cloak
    (8880000, 1102484, 250, 1, 1), # Magnus | Tyrant Lycaon Cloak
    (8880000, 1102485, 250, 1, 1), # Magnus | Tyrant Altair Cloak
    (8880000, 1132175, 250, 1, 1), # Magnus | Tyrant Hermes Belt
    (8880000, 1132176, 250, 1, 1), # Magnus | Tyrant Charon Belt
    (8880000, 1132177, 250, 1, 1), # Magnus | Tyrant Lycaon Belt
    (8880000, 1132178, 250, 1, 1), # Magnus | Tyrant Altair Belt
    (8880000, 1112951, 100, 1, 1), # Magnus | Magnus's Rage
    (8880000, 1402179, 100, 1, 1), # Magnus | Cursed Kaiserium
    (8880000, 4036105, 10000, 1, 1), # Magnus | Accursed Horn Energy
    (8880000, 4033767, 10000, 1, 1), # Magnus | Cursed Horn Energy
    (8870000, 2431896, 5000, 1, 3), # Hilla | Hilla's Soul Shard
    (8870000, 2430690, 100, 1, 1), # Hilla | [Pet Box] Blackheart
    (8870000, 1032136, 500, 1, 1), # Hilla | Will o' the Wisps
    (8870000, 1162009, 2000, 1, 1), # Hilla | Stone of Eternal Life
    (8870000, 1004214, 2000, 1, 1), # Hilla | Necromancer Warrior Hat
    (8870000, 1004215, 2000, 1, 1), # Hilla | Necromancer Magician Hat
    (8870000, 1004216, 2000, 1, 1), # Hilla | Necromancer Sentinel Hat
    (8870000, 1004217, 2000, 1, 1), # Hilla | Necromancer Chaser Hat
    (8870000, 1004218, 2000, 1, 1), # Hilla | Necromancer Skipper Hat
    (8870000, 1052784, 2000, 1, 1), # Hilla | Necromancer Warrior Suit
    (8870000, 1052785, 2000, 1, 1), # Hilla | Necromancer Magician Suit
    (8870000, 1052786, 2000, 1, 1), # Hilla | Necromancer Sentinel Suit
    (8870000, 1052787, 2000, 1, 1), # Hilla | Necromancer Chaser Suit
    (8870000, 1052788, 2000, 1, 1), # Hilla | Necromancer Skipper Suit
    (8870000, 1072952, 2000, 1, 1), # Hilla | Necromancer Warrior Shoes
    (8870000, 1072953, 2000, 1, 1), # Hilla | Necromancer Magician Shoes
    (8870000, 1072954, 2000, 1, 1), # Hilla | Necromancer Sentinel Shoes
    (8870000, 1072955, 2000, 1, 1), # Hilla | Necromancer Chaser Shoes
    (8870000, 1072956, 2000, 1, 1), # Hilla | Necromancer Skipper Shoes
    (8870000, 1082593, 2000, 1, 1), # Hilla | Necromancer Warrior Gloves
    (8870000, 1082594, 2000, 1, 1), # Hilla | Necromancer Magician Gloves
    (8870000, 1082595, 2000, 1, 1), # Hilla | Necromancer Sentinel Gloves
    (8870000, 1082596, 2000, 1, 1), # Hilla | Necromancer Chaser Gloves
    (8870000, 1082597, 2000, 1, 1), # Hilla | Necromancer Skipper Gloves
    (8870000, 1212035, 2000, 1, 1), # Hilla | Necro Shining Rod
    (8870000, 1222035, 2000, 1, 1), # Hilla | Necro Purple Dragon
    (8870000, 1232035, 2000, 1, 1), # Hilla | Necro Grim Seeker
    (8870000, 1242035, 2000, 1, 1), # Hilla | Necro Hefty Head
    (8870000, 1242131, 2000, 1, 1), # Hilla | Necro Stabber
    (8870000, 1252020, 2000, 1, 1), # Hilla | Necro Kitten Soul Scepter
    (8870000, 1262013, 2000, 1, 1), # Hilla | Necromancer Psy-limiter
    (8870000, 1272010, 2000, 1, 1), # Hilla | Necro Chain
    (8870000, 1282010, 2000, 1, 1), # Hilla | Necromancer Lucent Gauntlet
    (8870000, 1302213, 2000, 1, 1), # Hilla | Necro Terror
    (8870000, 1312112, 2000, 1, 1), # Hilla | Necro Headsplitter
    (8870000, 1322151, 2000, 1, 1), # Hilla | Necro Hammer
    (8870000, 1332187, 2000, 1, 1), # Hilla | Necro Slayer
    (8870000, 1332188, 2000, 1, 1), # Hilla | Necro Pescas
    (8870000, 1342066, 2000, 1, 1), # Hilla | Necro Katara
    (8870000, 1362061, 2000, 1, 1), # Hilla | Necro Jester
    (8870000, 1372132, 2000, 1, 1), # Hilla | Necro Wand
    (8870000, 1382159, 2000, 1, 1), # Hilla | Necro Rod
    (8870000, 1402143, 2000, 1, 1), # Hilla | Necro Zweihander
    (8870000, 1412100, 2000, 1, 1), # Hilla | Necro Giant Axe
    (8870000, 1422103, 2000, 1, 1), # Hilla | Necro Maul
    (8870000, 1432133, 2000, 1, 1), # Hilla | Necro Spear
    (8870000, 1442171, 2000, 1, 1), # Hilla | Necro Polearm
    (8870000, 1452163, 2000, 1, 1), # Hilla | Necro Longbow
    (8870000, 1462153, 2000, 1, 1), # Hilla | Necro Crossbow
    (8870000, 1472175, 2000, 1, 1), # Hilla | Necro Bloody Mist
    (8870000, 1482136, 2000, 1, 1), # Hilla | Necro Wolf
    (8870000, 1492136, 2000, 1, 1), # Hilla | Necro Eagle
    (8870000, 1522067, 2000, 1, 1), # Hilla | Necro Dual Bowguns
    (8870000, 1532071, 2000, 1, 1), # Hilla | Necro Oblivion
    (8870000, 1542040, 2000, 1, 1), # Hilla | Necromancer's Blade
    (8870000, 1582013, 2000, 1, 1), # Hilla | Necromancer Gigas
    (8870000, 1592011, 2000, 1, 1), # Hilla | Necromancer Ancient Bow
    (8870000, 1213011, 2000, 1, 1), # Hilla | Necro Trust
    (8870100, 4310029, 8000, 10, 15), # Silver-Haired Hilla | Crusader Coins
    (8870100, 2431896, 5000, 3, 5), # Silver-Haired Hilla | Hilla's Soul Shard
    (8870100, 2430690, 100, 1, 1), # Silver-Haired Hilla | [Pet Box] Blackheart
    (8870100, 1032136, 1000, 1, 1), # Silver-Haired Hilla | Will o' the Wisps
    (8870100, 1162009, 3000, 1, 1), # Silver-Haired Hilla | Stone of Eternal Life
    (8870100, 1112952, 100, 1, 1), # Silver-Haired Hilla | Hilla's Rage
    (8870100, 2433103, 10000, 1, 1), # Silver-Haired Hilla | Boss Medal of Honor
    (8870100, 2048701, 7000, 1, 1), # Silver-Haired Hilla | Rebirth Flame Lv. 120
    (8870100, 1004214, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Warrior Hat
    (8870100, 1004215, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Magician Hat
    (8870100, 1004216, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Sentinel Hat
    (8870100, 1004217, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Chaser Hat
    (8870100, 1004218, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Skipper Hat
    (8870100, 1052784, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Warrior Suit
    (8870100, 1052785, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Magician Suit
    (8870100, 1052786, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Sentinel Suit
    (8870100, 1052787, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Chaser Suit
    (8870100, 1052788, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Skipper Suit
    (8870100, 1072952, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Warrior Shoes
    (8870100, 1072953, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Magician Shoes
    (8870100, 1072954, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Sentinel Shoes
    (8870100, 1072955, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Chaser Shoes
    (8870100, 1072956, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Skipper Shoes
    (8870100, 1082593, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Warrior Gloves
    (8870100, 1082594, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Magician Gloves
    (8870100, 1082595, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Sentinel Gloves
    (8870100, 1082596, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Chaser Gloves
    (8870100, 1082597, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Skipper Gloves
    (8870100, 1212035, 2000, 1, 1), # Silver-Haired Hilla | Necro Shining Rod
    (8870100, 1222035, 2000, 1, 1), # Silver-Haired Hilla | Necro Purple Dragon
    (8870100, 1232035, 2000, 1, 1), # Silver-Haired Hilla | Necro Grim Seeker
    (8870100, 1242035, 2000, 1, 1), # Silver-Haired Hilla | Necro Hefty Head
    (8870100, 1242131, 2000, 1, 1), # Silver-Haired Hilla | Necro Stabber
    (8870100, 1252020, 2000, 1, 1), # Silver-Haired Hilla | Necro Kitten Soul Scepter
    (8870100, 1262013, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Psy-limiter
    (8870100, 1272010, 2000, 1, 1), # Silver-Haired Hilla | Necro Chain
    (8870100, 1282010, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Lucent Gauntlet
    (8870100, 1302213, 2000, 1, 1), # Silver-Haired Hilla | Necro Terror
    (8870100, 1312112, 2000, 1, 1), # Silver-Haired Hilla | Necro Headsplitter
    (8870100, 1322151, 2000, 1, 1), # Silver-Haired Hilla | Necro Hammer
    (8870100, 1332187, 2000, 1, 1), # Silver-Haired Hilla | Necro Slayer
    (8870100, 1332188, 2000, 1, 1), # Silver-Haired Hilla | Necro Pescas
    (8870100, 1342066, 2000, 1, 1), # Silver-Haired Hilla | Necro Katara
    (8870100, 1362061, 2000, 1, 1), # Silver-Haired Hilla | Necro Jester
    (8870100, 1372132, 2000, 1, 1), # Silver-Haired Hilla | Necro Wand
    (8870100, 1382159, 2000, 1, 1), # Silver-Haired Hilla | Necro Rod
    (8870100, 1402143, 2000, 1, 1), # Silver-Haired Hilla | Necro Zweihander
    (8870100, 1412100, 2000, 1, 1), # Silver-Haired Hilla | Necro Giant Axe
    (8870100, 1422103, 2000, 1, 1), # Silver-Haired Hilla | Necro Maul
    (8870100, 1432133, 2000, 1, 1), # Silver-Haired Hilla | Necro Spear
    (8870100, 1442171, 2000, 1, 1), # Silver-Haired Hilla | Necro Polearm
    (8870100, 1452163, 2000, 1, 1), # Silver-Haired Hilla | Necro Longbow
    (8870100, 1462153, 2000, 1, 1), # Silver-Haired Hilla | Necro Crossbow
    (8870100, 1472175, 2000, 1, 1), # Silver-Haired Hilla | Necro Bloody Mist
    (8870100, 1482136, 2000, 1, 1), # Silver-Haired Hilla | Necro Wolf
    (8870100, 1492136, 2000, 1, 1), # Silver-Haired Hilla | Necro Eagle
    (8870100, 1522067, 2000, 1, 1), # Silver-Haired Hilla | Necro Dual Bowguns
    (8870100, 1532071, 2000, 1, 1), # Silver-Haired Hilla | Necro Oblivion
    (8870100, 1542040, 2000, 1, 1), # Silver-Haired Hilla | Necromancer's Blade
    (8870100, 1582013, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Gigas
    (8870100, 1592011, 2000, 1, 1), # Silver-Haired Hilla | Necromancer Ancient Bow
    (8870100, 1213011, 2000, 1, 1), # Silver-Haired Hilla | Necro Trust
    (8850111, 2020013, 10000, 10, 25), # Cygnus | Reindeer Milk
    (8850111, 2433103, 10000, 1, 1), # Cygnus | Boss Medal of Honor
    (8850111, 4310018, 6000, 2, 2), # Cygnus | Crusader Coin
    (8850111, 1942001, 2500, 1, 1), # Cygnus | Gold Mask
    (8850111, 1952001, 2500, 1, 1), # Cygnus | Gold Pendant
    (8850111, 1962001, 2500, 1, 1), # Cygnus | Gold Wings
    (8850111, 1972001, 2500, 1, 1), # Cygnus | Gold Tail
    (8850111, 1612004, 2500, 1, 1), # Cygnus | Pure Gold Engine
    (8850111, 1622004, 2500, 1, 1), # Cygnus | Pure Gold Machine Arm
    (8850111, 1632003, 2500, 1, 1), # Cygnus | Pure Gold Machine Leg
    (8850111, 1642002, 2500, 1, 1), # Cygnus | Gold Body Frame
    (8850111, 1652004, 2500, 1, 1), # Cygnus | Pure Gold Transistor
    (8850111, 4021035, 10000, 5, 15), # Cygnus | Grand Spell Essence
    (8850111, 4021034, 10000, 5, 15), # Cygnus | Fine Spell Essence
    (8850111, 4021033, 10000, 5, 15), # Cygnus | Basic Spell Essence
    (8850111, 4021032, 10000, 5, 15), # Cygnus | Mana Crystal
    (8850111, 4021031, 10000, 5, 15), # Cygnus | Twisted Time
    (8850111, 4021020, 10000, 5, 15), # Cygnus | Confusion Fragment
    (8850111, 4000659, 10000, 1, 1), # Cygnus | Cygnus Circlet
    (8850111, 3010406, 3500, 1, 1), # Cygnus | Mini Shinsoo Chair
    (8850111, 2434588, 3500, 1, 1), # Cygnus | Piece of Ruin
    (8850111, 2434589, 3500, 1, 1), # Cygnus | Piece of Darkness
    (8850011, 2020013, 10000, 10, 25), # Cygnus | Reindeer Milk
    (8850011, 2433103, 10000, 1, 1), # Cygnus | Boss Medal of Honor
    (8850011, 4310018, 6000, 2, 2), # Cygnus | Crusader Coin
    (8850011, 2048701, 5000, 1, 1), # Cygnus | Rebirth Flame Lv. 120
    (8850011, 1942001, 2500, 1, 1), # Cygnus | Gold Mask
    (8850011, 1952001, 2500, 1, 1), # Cygnus | Gold Pendant
    (8850011, 1962001, 2500, 1, 1), # Cygnus | Gold Wings
    (8850011, 1972001, 2500, 1, 1), # Cygnus | Gold Tail
    (8850011, 1612004, 2500, 1, 1), # Cygnus | Pure Gold Engine
    (8850011, 1622004, 2500, 1, 1), # Cygnus | Pure Gold Machine Arm
    (8850011, 1632003, 2500, 1, 1), # Cygnus | Pure Gold Machine Leg
    (8850011, 1642002, 2500, 1, 1), # Cygnus | Gold Body Frame
    (8850011, 1652004, 2500, 1, 1), # Cygnus | Pure Gold Transistor
    (8850011, 4021035, 10000, 5, 15), # Cygnus | Grand Spell Essence
    (8850011, 4021034, 10000, 5, 15), # Cygnus | Fine Spell Essence
    (8850011, 4021033, 10000, 5, 15), # Cygnus | Basic Spell Essence
    (8850011, 4021032, 10000, 5, 15), # Cygnus | Mana Crystal
    (8850011, 4021031, 10000, 5, 15), # Cygnus | Twisted Time
    (8850011, 4021020, 10000, 5, 15), # Cygnus | Confusion Fragment
    (8850011, 4000659, 10000, 1, 1), # Cygnus | Cygnus Circlet
    (8850011, 3010406, 3500, 1, 1), # Cygnus | Mini Shinsoo Chair
    (8850011, 2431711, 7000, 1, 1), # Cygnus | Cygnus's Soul Shard
    (8850011, 2434588, 3500, 1, 1), # Cygnus | Piece of Ruin
    (8850011, 2434589, 3500, 1, 1), # Cygnus | Piece of Darkness
    (8860005, 2046374, 5000, 1, 1), # Arkarium | Fragment of Distorted Time
    (8860005, 1122254, 1000, 1, 1), # Arkarium | Mechanator Pendant
    (8860005, 2431753, 7000, 1, 1), # Arkarium | Arkarium's Soul Shard
    (8860005, 4021035, 10000, 5, 15), # Arkarium | Grand Spell Essence
    (8860005, 4021034, 10000, 5, 15), # Arkarium | Fine Spell Essence
    (8860005, 4021033, 10000, 5, 15), # Arkarium | Basic Spell Essence
    (8860005, 4021032, 10000, 5, 15), # Arkarium | Mana Crystal
    (8860005, 4021031, 10000, 5, 15), # Arkarium | Twisted Time
    (8860005, 4021020, 10000, 5, 15), # Arkarium | Confusion Fragment
    (8860005, 3010321, 2000, 1, 1), # Arkarium | Arkarium Storytime Chair
    (8860005, 4021010, 10000, 1, 2), # Arkarium | Rock of Time
    (8860000, 1122150, 1500, 1, 1), # Arkarium | Dominator Pendant
    (8860000, 2046374, 6000, 1, 1), # Arkarium | Fragment of Distorted Time
    (8860000, 1122254, 1500, 1, 1), # Arkarium | Mechanator Pendant
    (8860000, 2431753, 8000, 1, 1), # Arkarium | Arkarium's Soul Shard
    (8860000, 2433103, 10000, 1, 1), # Arkarium | Boss Medal of Honor
    (8860000, 4021031, 10000, 5, 15), # Arkarium | Twisted Time
    (8860000, 4021020, 10000, 5, 15), # Arkarium | Confusion Fragment
    (8860000, 4001832, 10000, 20, 60), # Arkarium | Spell Trace
    (8860000, 3010321, 2000, 1, 1), # Arkarium | Arkarium Storytime Chair
    (8860000, 4021010, 10000, 2, 3), # Arkarium | Rock of Time
    (8920106, 4310064, 10000, 1, 1), # Crimson Queen's Treasure Chest | Yggdrasil Rune
    (8920106, 1003717, 7000, 1, 1), # Crimson Queen's Treasure Chest | Queen's Tiara
    (8920106, 3010514, 750, 1, 1), # Crimson Queen's Treasure Chest | Crimson Queen's Throne
    (8920106, 2433591, 7000, 1, 1), # Crimson Queen's Treasure Chest | Crimson Queen's Soul Shard
    (8920006, 4310064, 10000, 1, 1), # Chaos Crimson Queen | Yggdrasil Rune
    (8920006, 4310065, 10000, 1, 1), # Chaos Crimson Queen | Chaos Yggdrasil Rune
    (8920006, 1003717, 7000, 1, 1), # Chaos Crimson Queen | Queen's Tiara
    (8920006, 1003721, 5000, 1, 1), # Chaos Crimson Queen | Chaos Queen's Tiara
    (8920006, 3010514, 1000, 1, 1), # Chaos Crimson Queen | Crimson Queen's Throne
    (8920006, 2048708, 5000, 1, 1), # Chaos Crimson Queen | Rebirth Flame Lv. 150
    (8920006, 2433103, 10000, 1, 1), # Chaos Crimson Queen | Boss Medal of Honor
    (8920006, 4310018, 6000, 2, 2), # Chaos Crimson Queen | Crusader Coin
    (8920006, 2433591, 8000, 1, 1), # Chaos Crimson Queen | Crimson Queen's Soul Shard
    (8920006, 2434586, 3500, 1, 1), # Chaos Crimson Queen | Piece of Anguish
    (8920006, 2049129, 1500, 1, 1), # Chaos Crimson Queen | Chaos Scroll of Goodness 50%
    (8920006, 2049133, 1500, 1, 1), # Chaos Crimson Queen | Miraculous Chaos Scroll 50%
    (8910100, 4310064, 10000, 1, 1), # Von Bon | Yggdrasil Rune
    (8910100, 1003716, 7000, 1, 1), # Von Bon | Von Bon Helmet
    (8910100, 3010512, 750, 1, 1), # Von Bon | Von Bon's Von Chair
    (8910100, 2433515, 7000, 1, 1), # Von Bon | Von Bon's Soul Shard
    (8910000, 4310064, 10000, 1, 1), # Chaos Von Bon | Yggdrasil Rune
    (8910000, 4310065, 10000, 1, 1), # Chaos Von Bon | Chaos Yggdrasil Rune
    (8910000, 1003716, 7000, 1, 1), # Chaos Von Bon | Von Bon Helmet
    (8910000, 1003720, 5000, 1, 1), # Chaos Von Bon | Chaos Von Bon Helmet
    (8910000, 3010512, 1000, 1, 1), # Chaos Von Bon | Von Bon's Von Chair
    (8910000, 2048708, 5000, 1, 1), # Chaos Von Bon | Rebirth Flame Lv. 150
    (8910000, 2433103, 10000, 1, 1), # Chaos Von Bon | Boss Medal of Honor
    (8910000, 4310018, 6000, 2, 2), # Chaos Von Bon | Crusader Coin
    (8910000, 2433515, 8000, 1, 1), # Chaos Von Bon | Von Bon's Soul Shard
    (8910000, 2434584, 3500, 1, 1), # Chaos Von Bon | Piece of Time
    (8910000, 2049129, 1500, 1, 1), # Chaos Von Bon | Chaos Scroll of Goodness 50%
    (8910000, 2049133, 1500, 1, 1), # Chaos Von Bon | Miraculous Chaos Scroll 50%
    (8900103, 4310064, 10000, 1, 1), # Pierre's Treasure Chest | Yggdrasil Rune
    (8900103, 1003715, 7000, 1, 1), # Pierre's Treasure Chest | Pierre Hat
    (8900103, 3010513, 750, 1, 1), # Pierre's Treasure Chest | Happy Pierre Chair
    (8900103, 2433446, 7000, 1, 1), # Pierre's Treasure Chest | Pierre's Soul Shard
    (8900003, 4310064, 10000, 1, 1), # Chaos Pierre | Yggdrasil Rune
    (8900003, 4310065, 10000, 1, 1), # Chaos Pierre | Chaos Yggdrasil Rune
    (8900003, 1003715, 7000, 1, 1), # Chaos Pierre | Pierre Hat
    (8900003, 1003719, 5000, 1, 1), # Chaos Pierre | Chaos Pierre Hat
    (8900003, 3010513, 1000, 1, 1), # Chaos Pierre | Happy Pierre Chair
    (8900003, 2048708, 5000, 1, 1), # Chaos Pierre | Rebirth Flame Lv. 150
    (8900003, 2433103, 10000, 1, 1), # Chaos Pierre | Boss Medal of Honor
    (8900003, 4310018, 6000, 2, 2), # Chaos Pierre | Crusader Coin
    (8900003, 2433446, 8000, 1, 1), # Chaos Pierre | Pierre's Soul Shard
    (8900003, 2434585, 3500, 1, 1), # Chaos Pierre | Piece of Mockery
    (8930100, 4310064, 10000, 1, 1), # Vellum | Yggdrasil Rune
    (8930100, 1003718, 7000, 1, 1), # Vellum | Vellum's Helm
    (8930100, 3010515, 750, 1, 1), # Vellum | Vellum Rock Chair
    (8930100, 2433592, 7000, 1, 1), # Vellum | Vellum's Soul Shard
    (8930000, 4310064, 10000, 1, 1), # Chaos Vellum | Yggdrasil Rune
    (8930000, 4310065, 10000, 1, 1), # Chaos Vellum | Chaos Yggdrasil Rune
    (8930000, 1003718, 7000, 1, 1), # Chaos Vellum | Vellum's Helm
    (8930000, 1003722, 5000, 1, 1), # Chaos Vellum | Chaos Vellum's Helm
    (8930000, 3010515, 1000, 1, 1), # Chaos Vellum | Vellum Rock Chair
    (8930000, 2048708, 5000, 1, 1), # Chaos Vellum | Rebirth Flame Lv. 150
    (8930000, 2433103, 10000, 1, 1), # Chaos Vellum | Boss Medal of Honor
    (8930000, 4310018, 6000, 2, 2), # Chaos Vellum | Crusader Coin
    (8930000, 2433592, 8000, 1, 1), # Chaos Vellum | Vellum's Soul Shard
    (8930000, 2432295, 500, 1, 1), # Chaos Vellum | Permanent Vellum Mount Coupon
    (8930000, 2434587, 3500, 1, 1), # Chaos Vellum | Piece of Destruction
    (9421589, 1232072, 300, 1, 1), # Mori Ranmaru | Amaterasu's Death Bringer
    (9421589, 1302229, 300, 1, 1), # Mori Ranmaru | Amaterasu's Nimbus Sword
    (9421589, 1312118, 300, 1, 1), # Mori Ranmaru | Amaterasu's Axe of a Thousand Blades
    (9421589, 1322164, 300, 1, 1), # Mori Ranmaru | Amaterasu's Golden Slice
    (9421589, 1402153, 300, 1, 1), # Mori Ranmaru | Amaterasu's Weeping Spirit Sword
    (9421589, 1412106, 300, 1, 1), # Mori Ranmaru | Amaterasu's Smoldering Spirit Axe
    (9421589, 1422109, 300, 1, 1), # Mori Ranmaru | Amaterasu's Mace of Ruin
    (9421589, 1432140, 300, 1, 1), # Mori Ranmaru | Amaterasu's Final Wind
    (9421589, 1442184, 300, 1, 1), # Mori Ranmaru | Amaterasu's Pelting Rain
    (9421589, 1542045, 300, 1, 1), # Mori Ranmaru | Amaterasu's Muramasa
    (9421589, 1213031, 300, 1, 1), # Mori Ranmaru | Amaterasu Bladecaster
    (9421589, 1452172, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Fire Spirit Bow
    (9421589, 1462161, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Mind Expansion Bow
    (9421589, 1522073, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Dragon Bow
    (9421589, 1212057, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Shining Rod
    (9421589, 1282027, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Lucent Gauntlet
    (9421589, 1252056, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Spirit Pole
    (9421589, 1372141, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Crooked Soul Staff
    (9421589, 1382170, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Sinister Spirit Hand
    (9421589, 1552045, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Red Flower
    (9421589, 1222052, 300, 1, 1), # Mori Ranmaru | Susano-o's Rainbow Shooter
    (9421589, 1482142, 300, 1, 1), # Mori Ranmaru | Susano-o's Bloodstained Sword
    (9421589, 1492154, 300, 1, 1), # Mori Ranmaru | Susano-o's Baleful Blaster
    (9421589, 1532076, 300, 1, 1), # Mori Ranmaru | Susano-o's Solar Cannon
    (9421589, 1242077, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Energy Chain
    (9421589, 1242078, 300, 1, 1), # Mori Ranmaru | Susano-o's Energy Chain
    (9421589, 1272027, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Chain
    (9421589, 1332195, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Execution Sword
    (9421589, 1342071, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Sword of Sorrow
    (9421589, 1362069, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Wraith Hand
    (9421589, 1472181, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Fist of Malice
    (9421589, 1262028, 300, 1, 1), # Mori Ranmaru | Sengoku Era Psy-limiter
    (9421589, 1003601, 300, 1, 1), # Mori Ranmaru | Amaterasu's Helm
    (9421589, 1052509, 300, 1, 1), # Mori Ranmaru | Amaterasu's Armor
    (9421589, 1072711, 300, 1, 1), # Mori Ranmaru | Amaterasu's Shoes
    (9421589, 1082472, 300, 1, 1), # Mori Ranmaru | Amaterasu's Gloves
    (9421589, 1102456, 300, 1, 1), # Mori Ranmaru | Amaterasu's Cape
    (9421589, 1132156, 300, 1, 1), # Mori Ranmaru | Amaterasu's Belt
    (9421589, 1152094, 300, 1, 1), # Mori Ranmaru | Amaterasu's Epaulet
    (9421589, 1003602, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Helm
    (9421589, 1052510, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Armor
    (9421589, 1072712, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Shoes
    (9421589, 1082473, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Gloves
    (9421589, 1102457, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Cape
    (9421589, 1132157, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Belt
    (9421589, 1152095, 300, 1, 1), # Mori Ranmaru | Oyamatsumi's Epaulet
    (9421589, 1052511, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Armor
    (9421589, 1003603, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Helm
    (9421589, 1072713, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Shoes
    (9421589, 1082474, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Gloves
    (9421589, 1102458, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Cape
    (9421589, 1132158, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Belt
    (9421589, 1152096, 300, 1, 1), # Mori Ranmaru | Ame-no-Uzume's Epaulet
    (9421589, 1003604, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Helm
    (9421589, 1052512, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Armor
    (9421589, 1072714, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Shoes
    (9421589, 1082475, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Gloves
    (9421589, 1102459, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Cape
    (9421589, 1132159, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Belt
    (9421589, 1152097, 300, 1, 1), # Mori Ranmaru | Tsukuyomi's Epaulet
    (9421589, 1003605, 300, 1, 1), # Mori Ranmaru | Susano-o's Helm
    (9421589, 1052513, 300, 1, 1), # Mori Ranmaru | Susano-o's Armor
    (9421589, 1072715, 300, 1, 1), # Mori Ranmaru | Susano-o's Shoes
    (9421589, 1082476, 300, 1, 1), # Mori Ranmaru | Susano-o's Gloves
    (9421589, 1102460, 300, 1, 1), # Mori Ranmaru | Susano-o's Cape
    (9421589, 1132160, 300, 1, 1), # Mori Ranmaru | Susano-o's Belt
    (9421589, 1152098, 300, 1, 1), # Mori Ranmaru | Susano-o's Epaulet
    (9421589, 2433103, 10000, 1, 1), # Mori Ranmaru | Boss Medal of Honor
    (9421589, 4021030, 10000, 1, 1), # Mori Ranmaru | Spirit Stone of the Sixth Heaven
    (9421589, 4021031, 10000, 125, 250), # Mori Ranmaru | Twisted Time
    (9421589, 3015195, 700, 1, 1), # Mori Ranmaru | Ghost Fox Chair
    (9421589, 4001832, 10000, 20, 60), # Mori Ranmaru | Spell Trace
    (9421589, 2870528, 5000, 1, 1), # Mori Ranmaru | Mori Ranmaru Familiar
    (9421581, 2870528, 5000, 1, 1), # Mori Ranmaru | Mori Ranmaru Familiar
    (9421581, 4021029, 10000, 10, 25), # Mori Ranmaru | Condensed Spirit
    (9421581, 3015195, 100, 1, 1), # Mori Ranmaru | Ghost Fox Chair
    (8880111, 4310216, 200, 1, 1), # Damien | AbsoLab Essence
    (8880111, 1082636, 200, 1, 1), # Damien | AbsoLab Knight Gloves
    (8880111, 1082637, 200, 1, 1), # Damien | AbsoLab Mage Gloves
    (8880111, 1082638, 200, 1, 1), # Damien | AbsoLab Archer Gloves
    (8880111, 1082639, 200, 1, 1), # Damien | AbsoLab Bandit Gloves
    (8880111, 1082640, 200, 1, 1), # Damien | AbsoLab Pirate Gloves
    (8880111, 1102775, 200, 1, 1), # Damien | AbsoLab Knight Cape
    (8880111, 1102794, 200, 1, 1), # Damien | AbsoLab Mage Cape
    (8880111, 1102795, 200, 1, 1), # Damien | AbsoLab Archer Cape
    (8880111, 1102796, 200, 1, 1), # Damien | AbsoLab Bandit Cape
    (8880111, 1102797, 200, 1, 1), # Damien | AbsoLab Pirate Cape
    (8880111, 1662088, 150, 1, 1), # Damien | Damienroid
    (8880111, 2432009, 1050, 3, 6), # Damien | Black Mage's Token
    (8880111, 1099015, 2000, 1, 1), # Damien | Ruin Force Shield
    (8880111, 1022278, 400, 1, 1), # Damien | Magic Eyepatch
    (8880111, 1212115, 225, 1, 1), # Damien | AbsoLab Shining Rod
    (8880111, 1222109, 225, 1, 1), # Damien | AbsoLab Soul Shooter
    (8880111, 1232109, 225, 1, 1), # Damien | AbsoLab Desperado
    (8880111, 1242116, 225, 1, 1), # Damien | AbsoLab Whip Blade
    (8880111, 1252093, 225, 1, 1), # Damien | AbsoLab Scepter
    (8880111, 1262017, 225, 1, 1), # Damien | AbsoLab Psy-limiter
    (8880111, 1272016, 225, 1, 1), # Damien | AbsoLab Chain
    (8880111, 1282016, 225, 1, 1), # Damien | AbsoLab Lucent Gauntlet
    (8880111, 1302333, 225, 1, 1), # Damien | AbsoLab Saber
    (8880111, 1312199, 225, 1, 1), # Damien | AbsoLab Axe
    (8880111, 1322250, 225, 1, 1), # Damien | AbsoLab Bit Hammer
    (8880111, 1332274, 225, 1, 1), # Damien | AbsoLab Blade Lord
    (8880111, 1342101, 225, 1, 1), # Damien | AbsoLab Katara
    (8880111, 1362135, 225, 1, 1), # Damien | AbsoLab Forked Cane
    (8880111, 1372222, 225, 1, 1), # Damien | AbsoLab Spellsong Wand
    (8880111, 1382259, 225, 1, 1), # Damien | AbsoLab Spellsong Staff
    (8880111, 1402251, 225, 1, 1), # Damien | AbsoLab Broad Saber
    (8880111, 1412177, 225, 1, 1), # Damien | AbsoLab Broad Axe
    (8880111, 1422184, 225, 1, 1), # Damien | AbsoLab Broad Hammer
    (8880111, 1432214, 225, 1, 1), # Damien | AbsoLab Piercing Spear
    (8880111, 1442268, 225, 1, 1), # Damien | AbsoLab Hellslayer
    (8880111, 1452252, 225, 1, 1), # Damien | AbsoLab Sureshot Bow
    (8880111, 1462239, 225, 1, 1), # Damien | AbsoLab Crossbow
    (8880111, 1472261, 225, 1, 1), # Damien | AbsoLab Revenge Guard
    (8880111, 1482216, 225, 1, 1), # Damien | AbsoLab Blast Knuckle
    (8880111, 1492231, 225, 1, 1), # Damien | AbsoLab Point Gun
    (8880111, 1522138, 225, 1, 1), # Damien | AbsoLab Dual Bowguns
    (8880111, 1532144, 225, 1, 1), # Damien | AbsoLab Blast Cannon
    (8880111, 1542108, 225, 1, 1), # Damien | AbsoLab Katana
    (8880111, 1552110, 225, 1, 1), # Damien | AbsoLab Summoner
    (8880111, 1582017, 225, 1, 1), # Damien | AbsoLab Pile God
    (8880111, 1213017, 225, 1, 1), # Damien | AbsoLab Bladecaster
    (8880111, 1214017, 225, 1, 1), # Damien | AbsoLab Whispershot
    (8880111, 4310018, 6000, 2, 2), # Damien | Crusader Coin
    (8880111, 2048708, 5000, 1, 1), # Damien | Rebirth Flame Lv. 150
    (8880111, 2433103, 10000, 1, 1), # Damien | Boss Medal of Honor
    (8880111, 2020013, 10000, 10, 25), # Damien | Reindeer Milk
    (8880111, 2435369, 8000, 1, 1), # Damien | Damien's Soul Shard
    (8880111, 4001869, 10000, 1, 1), # Damien | Twisted Stigma Spirit Stone
    (8880111, 2434157, 100, 1, 1), # Damien | Damien's Band Damage Skin
    (8880111, 2049136, 2000, 1, 1), # Damien | Incredible Chaos Scroll of Goodness 20%
    (8880111, 2049165, 1000, 1, 1), # Damien | Incredible Chaos Scroll of Goodness 50%
    (8880111, 2049129, 4000, 1, 1), # Damien | Chaos Scroll of Goodness 50%
    (8880111, 2049133, 4000, 1, 1), # Damien | Miraculous Chaos Scroll 50%
    (8880101, 4310216, 200, 1, 1), # Damien | AbsoLab Essence
    (8880101, 1082636, 150, 1, 1), # Damien | AbsoLab Knight Gloves
    (8880101, 1082637, 150, 1, 1), # Damien | AbsoLab Mage Gloves
    (8880101, 1082638, 150, 1, 1), # Damien | AbsoLab Archer Gloves
    (8880101, 1082639, 150, 1, 1), # Damien | AbsoLab Bandit Gloves
    (8880101, 1082640, 150, 1, 1), # Damien | AbsoLab Pirate Gloves
    (8880101, 1102775, 150, 1, 1), # Damien | AbsoLab Knight Cape
    (8880101, 1102794, 150, 1, 1), # Damien | AbsoLab Mage Cape
    (8880101, 1102795, 150, 1, 1), # Damien | AbsoLab Archer Cape
    (8880101, 1102796, 150, 1, 1), # Damien | AbsoLab Bandit Cape
    (8880101, 1102797, 150, 1, 1), # Damien | AbsoLab Pirate Cape
    (8880101, 1099015, 2000, 1, 1), # Damien | Ruin Force Shield
    (8880101, 4310018, 6000, 2, 2), # Damien | Crusader Coin
    (8880101, 2048708, 5000, 1, 1), # Damien | Rebirth Flame Lv. 150
    (8880101, 2048716, 4000, 1, 1), # Damien | Powerful Rebirth Flame
    (8880101, 2432009, 600, 2, 4), # Damien | Black Mage's Token
    (8880101, 2433103, 10000, 1, 1), # Damien | Boss Medal of Honor
    (8880101, 2020013, 10000, 10, 25), # Damien | Reindeer Milk
    (8880101, 2049129, 2000, 1, 1), # Damien | Chaos Scroll of Goodness 50%
    (8880101, 2049133, 2000, 1, 1), # Damien | Miraculous Chaos Scroll 50%
    (8810118, 1122076, 10000, 1, 1), # Chaos Horntail | Chaos Horntail Necklace
    (8810118, 1113149, 1000, 1, 1), # Chaos Horntail | Silver Blossom Ring
    (8810118, 1032241, 2000, 1, 1), # Chaos Horntail | Dea Sidus Earring
    (8810118, 2433103, 10000, 1, 1), # Chaos Horntail | Boss Medal of Honor
    (8810118, 3010128, 2000, 1, 1), # Chaos Horntail | Horntail Chair
    (8810118, 1042243, 4000, 1, 1), # Chaos Horntail | Horntail's Ab T-Shirt
    (8810018, 1122000, 10000, 1, 1), # Horntail | Horntail Necklace
    (8810018, 3010128, 2000, 1, 1), # Horntail | Horntail Chair
    (8880405, 4001894, 10000, 1, 1), # Verus Hilla | Shadow of Annihilation
    (8880405, 2439567, 10000, 1, 1), # Verus Hilla | Verus Hilla's Soul Shard
    (8880405, 2433103, 10000, 1, 1), # Verus Hilla | Boss Medal of Honor
    (8880405, 4310018, 6000, 2, 2), # Verus Hilla | Crusader Coin
    (8880405, 2048708, 5000, 1, 1), # Verus Hilla | Rebirth Flame Lv. 150
    (8880405, 2048716, 5000, 1, 1), # Verus Hilla | Powerful Rebirth Flame
    (8880405, 2049136, 4000, 1, 1), # Verus Hilla | Incredible Chaos Scroll of Goodness 20%
    (8880405, 2049165, 3000, 1, 1), # Verus Hilla | Incredible Chaos Scroll of Goodness 50%
    (8880405, 2049129, 6000, 1, 1), # Verus Hilla | Chaos Scroll of Goodness 50%
    (8880405, 2049133, 6000, 1, 1), # Verus Hilla | Miraculous Chaos Scroll 50%
    (8950102, 4310216, 200, 1, 1), # Lotus | AbsoLab Essence
    (8950102, 4001877, 250, 1, 1), # Lotus | Damaged Black Heart
    (8950102, 1004422, 270, 1, 1), # Lotus | AbsoLab Knight Helm
    (8950102, 1004423, 270, 1, 1), # Lotus | AbsoLab Mage Crown
    (8950102, 1004424, 270, 1, 1), # Lotus | AbsoLab Archer Hood
    (8950102, 1004425, 270, 1, 1), # Lotus | AbsoLab Bandit Cap
    (8950102, 1004426, 270, 1, 1), # Lotus | AbsoLab Pirate Fedora
    (8950102, 1052882, 270, 1, 1), # Lotus | AbsoLab Knight Suit
    (8950102, 1052887, 270, 1, 1), # Lotus | AbsoLab Mage Suit
    (8950102, 1052888, 270, 1, 1), # Lotus | AbsoLab Archer Suit
    (8950102, 1052889, 270, 1, 1), # Lotus | AbsoLab Bandit Suit
    (8950102, 1052890, 270, 1, 1), # Lotus | AbsoLab Pirate Suit
    (8950102, 1073030, 270, 1, 1), # Lotus | AbsoLab Knight Shoes
    (8950102, 1073032, 270, 1, 1), # Lotus | AbsoLab Mage Shoes
    (8950102, 1073033, 270, 1, 1), # Lotus | AbsoLab Archer Shoes
    (8950102, 1073034, 270, 1, 1), # Lotus | AbsoLab Bandit Shoes
    (8950102, 1073035, 270, 1, 1), # Lotus | AbsoLab Pirate Shoes
    (8950102, 1152174, 270, 1, 1), # Lotus | AbsoLab Knight Shoulder
    (8950102, 1152176, 270, 1, 1), # Lotus | AbsoLab Mage Shoulder
    (8950102, 1152177, 270, 1, 1), # Lotus | AbsoLab Archer Shoulder
    (8950102, 1152178, 270, 1, 1), # Lotus | AbsoLab Thief Shoulder
    (8950102, 1152179, 270, 1, 1), # Lotus | AbsoLab Pirate Shoulder
    (8950102, 1212115, 200, 1, 1), # Lotus | AbsoLab Shining Rod
    (8950102, 1222109, 200, 1, 1), # Lotus | AbsoLab Soul Shooter
    (8950102, 1232109, 200, 1, 1), # Lotus | AbsoLab Desperado
    (8950102, 1242116, 200, 1, 1), # Lotus | AbsoLab Whip Blade
    (8950102, 1252093, 200, 1, 1), # Lotus | AbsoLab Scepter
    (8950102, 1262017, 200, 1, 1), # Lotus | AbsoLab Psy-limiter
    (8950102, 1272016, 200, 1, 1), # Lotus | AbsoLab Chain
    (8950102, 1282016, 200, 1, 1), # Lotus | AbsoLab Lucent Gauntlet
    (8950102, 1302333, 200, 1, 1), # Lotus | AbsoLab Saber
    (8950102, 1312199, 200, 1, 1), # Lotus | AbsoLab Axe
    (8950102, 1322250, 200, 1, 1), # Lotus | AbsoLab Bit Hammer
    (8950102, 1332274, 200, 1, 1), # Lotus | AbsoLab Blade Lord
    (8950102, 1342101, 200, 1, 1), # Lotus | AbsoLab Katara
    (8950102, 1362135, 200, 1, 1), # Lotus | AbsoLab Forked Cane
    (8950102, 1372222, 200, 1, 1), # Lotus | AbsoLab Spellsong Wand
    (8950102, 1382259, 200, 1, 1), # Lotus | AbsoLab Spellsong Staff
    (8950102, 1402251, 200, 1, 1), # Lotus | AbsoLab Broad Saber
    (8950102, 1412177, 200, 1, 1), # Lotus | AbsoLab Broad Axe
    (8950102, 1422184, 200, 1, 1), # Lotus | AbsoLab Broad Hammer
    (8950102, 1432214, 200, 1, 1), # Lotus | AbsoLab Piercing Spear
    (8950102, 1442268, 200, 1, 1), # Lotus | AbsoLab Hellslayer
    (8950102, 1452252, 200, 1, 1), # Lotus | AbsoLab Sureshot Bow
    (8950102, 1462239, 200, 1, 1), # Lotus | AbsoLab Crossbow
    (8950102, 1472261, 200, 1, 1), # Lotus | AbsoLab Revenge Guard
    (8950102, 1482216, 200, 1, 1), # Lotus | AbsoLab Blast Knuckle
    (8950102, 1492231, 200, 1, 1), # Lotus | AbsoLab Point Gun
    (8950102, 1522138, 200, 1, 1), # Lotus | AbsoLab Dual Bowguns
    (8950102, 1532144, 200, 1, 1), # Lotus | AbsoLab Blast Cannon
    (8950102, 1542108, 200, 1, 1), # Lotus | AbsoLab Katana
    (8950102, 1552110, 200, 1, 1), # Lotus | AbsoLab Summoner
    (8950102, 1582017, 200, 1, 1), # Lotus | AbsoLab Pile God
    (8950102, 1292017, 200, 1, 1), # Lotus | AbsoLab Monster Ritual Fan
    (8950102, 1592019, 200, 1, 1), # Lotus | AbsoLab Ancient Bow
    (8950102, 4001843, 9000, 1, 1), # Lotus | Extraordinary Energy Core (Grade S)
    (8950102, 2433103, 10000, 1, 1), # Lotus | Boss Medal of Honor
    (8950102, 2434795, 300, 1, 1), # Lotus | Lotus Damage Skin
    (8950102, 2434794, 300, 1, 1), # Lotus | Orchid Damage Skin
    (8950102, 2434796, 100, 1, 1), # Lotus | Black Heaven Damage Skin
    (8950102, 2433593, 7000, 1, 1), # Lotus | Lotus's Soul Shard
    (8950102, 2880714, 5000, 1, 1), # Lotus | Lotus Familiar
    (8950102, 1012632, 500, 1, 1), # Lotus | Berserked
    (8950102, 2049517, 5000, 1, 1), # Lotus | Gold Potential Stamp
    (8950102, 2048708, 5000, 1, 1), # Lotus | Rebirth Flame Lv. 150
    (8950102, 2048716, 5000, 1, 1), # Lotus | Powerful Rebirth Flame
    (8950102, 4310018, 6000, 2, 2), # Lotus | Crusader Coin
    (8950102, 3015514, 750, 1, 1), # Lotus | Blazing Lotus Chair
    (8950102, 3015003, 750, 1, 1), # Lotus | Forever Lotus Pillow Chair
    (8950102, 2049136, 2500, 1, 1), # Lotus | Incredible Chaos Scroll of Goodness 20%
    (8950102, 2049165, 800, 1, 1), # Lotus | Incredible Chaos Scroll of Goodness 50%
    (8950102, 2049129, 5000, 1, 1), # Lotus | Chaos Scroll of Goodness 50%
    (8950102, 2049133, 5000, 1, 1), # Lotus | Miraculous Chaos Scroll 50%
    (8950002, 4310216, 150, 1, 1), # Lotus | AbsoLab Essence
    (8950002, 1004422, 170, 1, 1), # Lotus | AbsoLab Knight Helm
    (8950002, 1004423, 170, 1, 1), # Lotus | AbsoLab Mage Crown
    (8950002, 1004424, 170, 1, 1), # Lotus | AbsoLab Archer Hood
    (8950002, 1004425, 170, 1, 1), # Lotus | AbsoLab Bandit Cap
    (8950002, 1004426, 170, 1, 1), # Lotus | AbsoLab Pirate Fedora
    (8950002, 1052882, 170, 1, 1), # Lotus | AbsoLab Knight Suit
    (8950002, 1052887, 170, 1, 1), # Lotus | AbsoLab Mage Suit
    (8950002, 1052888, 170, 1, 1), # Lotus | AbsoLab Archer Suit
    (8950002, 1052889, 170, 1, 1), # Lotus | AbsoLab Bandit Suit
    (8950002, 1052890, 170, 1, 1), # Lotus | AbsoLab Pirate Suit
    (8950002, 1073030, 170, 1, 1), # Lotus | AbsoLab Knight Shoes
    (8950002, 1073032, 170, 1, 1), # Lotus | AbsoLab Mage Shoes
    (8950002, 1073033, 170, 1, 1), # Lotus | AbsoLab Archer Shoes
    (8950002, 1073034, 170, 1, 1), # Lotus | AbsoLab Bandit Shoes
    (8950002, 1073035, 170, 1, 1), # Lotus | AbsoLab Pirate Shoes
    (8950002, 1152174, 170, 1, 1), # Lotus | AbsoLab Knight Shoulder
    (8950002, 1152176, 170, 1, 1), # Lotus | AbsoLab Mage Shoulder
    (8950002, 1152177, 170, 1, 1), # Lotus | AbsoLab Archer Shoulder
    (8950002, 1152178, 170, 1, 1), # Lotus | AbsoLab Thief Shoulder
    (8950002, 1152179, 170, 1, 1), # Lotus | AbsoLab Pirate Shoulder
    (8950002, 1212115, 100, 1, 1), # Lotus | AbsoLab Shining Rod
    (8950002, 1222109, 100, 1, 1), # Lotus | AbsoLab Soul Shooter
    (8950002, 1232109, 100, 1, 1), # Lotus | AbsoLab Desperado
    (8950002, 1242116, 100, 1, 1), # Lotus | AbsoLab Whip Blade
    (8950002, 1252093, 100, 1, 1), # Lotus | AbsoLab Scepter
    (8950002, 1262017, 100, 1, 1), # Lotus | AbsoLab Psy-limiter
    (8950002, 1272016, 100, 1, 1), # Lotus | AbsoLab Chain
    (8950002, 1282016, 100, 1, 1), # Lotus | AbsoLab Lucent Gauntlet
    (8950002, 1302333, 100, 1, 1), # Lotus | AbsoLab Saber
    (8950002, 1312199, 100, 1, 1), # Lotus | AbsoLab Axe
    (8950002, 1322250, 100, 1, 1), # Lotus | AbsoLab Bit Hammer
    (8950002, 1332274, 100, 1, 1), # Lotus | AbsoLab Blade Lord
    (8950002, 1342101, 100, 1, 1), # Lotus | AbsoLab Katara
    (8950002, 1362135, 100, 1, 1), # Lotus | AbsoLab Forked Cane
    (8950002, 1372222, 100, 1, 1), # Lotus | AbsoLab Spellsong Wand
    (8950002, 1382259, 100, 1, 1), # Lotus | AbsoLab Spellsong Staff
    (8950002, 1402251, 100, 1, 1), # Lotus | AbsoLab Broad Saber
    (8950002, 1412177, 100, 1, 1), # Lotus | AbsoLab Broad Axe
    (8950002, 1422184, 100, 1, 1), # Lotus | AbsoLab Broad Hammer
    (8950002, 1432214, 100, 1, 1), # Lotus | AbsoLab Piercing Spear
    (8950002, 1442268, 100, 1, 1), # Lotus | AbsoLab Hellslayer
    (8950002, 1452252, 100, 1, 1), # Lotus | AbsoLab Sureshot Bow
    (8950002, 1462239, 100, 1, 1), # Lotus | AbsoLab Crossbow
    (8950002, 1472261, 100, 1, 1), # Lotus | AbsoLab Revenge Guard
    (8950002, 1482216, 100, 1, 1), # Lotus | AbsoLab Blast Knuckle
    (8950002, 1492231, 100, 1, 1), # Lotus | AbsoLab Point Gun
    (8950002, 1522138, 100, 1, 1), # Lotus | AbsoLab Dual Bowguns
    (8950002, 1532144, 100, 1, 1), # Lotus | AbsoLab Blast Cannon
    (8950002, 1542108, 100, 1, 1), # Lotus | AbsoLab Katana
    (8950002, 1552110, 100, 1, 1), # Lotus | AbsoLab Summoner
    (8950002, 1582017, 100, 1, 1), # Lotus | AbsoLab Pile God
    (8950002, 1292017, 100, 1, 1), # Lotus | AbsoLab Monster Ritual Fan
    (8950002, 1592019, 100, 1, 1), # Lotus | AbsoLab Ancient Bow
    (8950002, 4310018, 6000, 2, 2), # Lotus | Crusader Coin
    (8950002, 2433593, 5000, 1, 1), # Lotus | Lotus's Soul Shard
    (8950002, 2434795, 100, 1, 1), # Lotus | Lotus Damage Skin
    (8950002, 2880713, 5000, 1, 1), # Lotus | Lotus Familiar
    (8950002, 2048708, 5000, 1, 1), # Lotus | Rebirth Flame Lv. 150
    (8950002, 2048716, 4000, 1, 1), # Lotus | Powerful Rebirth Flame
    (8950002, 4001843, 10000, 1, 1), # Lotus | Extraordinary Energy Core (Grade S)
    (8950002, 2433103, 10000, 1, 1), # Lotus | Boss Medal of Honor
    (8950002, 2049129, 2500, 1, 1), # Lotus | Chaos Scroll of Goodness 50%
    (8950002, 2049133, 2500, 1, 1), # Lotus | Miraculous Chaos Scroll 50%
    (8880177, 1132308, 250, 1, 1), # Final Music Box | Dreamy Belt
    (8880177, 1152196, 200, 1, 1), # Final Music Box | Arcane Umbra Knight Shoulder
    (8880177, 1152197, 200, 1, 1), # Final Music Box | Arcane Umbra Mage Shoulder
    (8880177, 1152198, 200, 1, 1), # Final Music Box | Arcane Umbra Archer Shoulder
    (8880177, 1152199, 200, 1, 1), # Final Music Box | Arcane Umbra Thief Shoulder
    (8880177, 1152200, 200, 1, 1), # Final Music Box | Arcane Umbra Pirate Shoulder
    (8880177, 1073158, 200, 1, 1), # Final Music Box | Arcane Umbra Knight Shoes
    (8880177, 1073159, 200, 1, 1), # Final Music Box | Arcane Umbra Mage Shoes
    (8880177, 1073160, 200, 1, 1), # Final Music Box | Arcane Umbra Archer Shoes
    (8880177, 1073161, 200, 1, 1), # Final Music Box | Arcane Umbra Thief Shoes
    (8880177, 1073162, 200, 1, 1), # Final Music Box | Arcane Umbra Pirate Shoes
    (8880177, 1102940, 200, 1, 1), # Final Music Box | Arcane Umbra Knight Cape
    (8880177, 1102941, 200, 1, 1), # Final Music Box | Arcane Umbra Mage Cape
    (8880177, 1102942, 200, 1, 1), # Final Music Box | Arcane Umbra Archer Cape
    (8880177, 1102943, 200, 1, 1), # Final Music Box | Arcane Umbra Thief Cape
    (8880177, 1102944, 200, 1, 1), # Final Music Box | Arcane Umbra Pirate Cape
    (8880177, 4001879, 9000, 1, 1), # Final Music Box | Butterfly Droplet Stone
    (8880177, 2436039, 10000, 1, 1), # Final Music Box | Lucid's Soul Shard
    (8880177, 1662111, 1000, 1, 1), # Final Music Box | Lucidroid
    (8880177, 4310018, 6000, 2, 2), # Final Music Box | Crusader Coin
    (8880177, 2048708, 5000, 1, 1), # Final Music Box | Rebirth Flame Lv. 150
    (8880177, 2048716, 4000, 1, 1), # Final Music Box | Powerful Rebirth Flame
    (8880177, 2433103, 10000, 1, 1), # Final Music Box | Boss Medal of Honor
    (8880177, 2435719, 3500, 1, 1), # Final Music Box | Nodestone
    (8880177, 2436228, 100, 1, 1), # Final Music Box | Lucid Butterfly Damage Skin
    (8880177, 2870698, 1000, 1, 1), # Final Music Box | Dreaming Lucid Familiar
    (8880177, 2870861, 1000, 1, 1), # Final Music Box | Lucid Familiar
    (8880177, 3015712, 3500, 1, 1), # Final Music Box | Lucid's Nightmare Bed
    (8880177, 3015637, 3000, 1, 1), # Final Music Box | Bloom of Lucid
    (8880177, 2049136, 3500, 1, 1), # Final Music Box | Incredible Chaos Scroll of Goodness 20%
    (8880177, 2049165, 2500, 1, 1), # Final Music Box | Incredible Chaos Scroll of Goodness 50%
    (8880177, 2049129, 6000, 1, 1), # Final Music Box | Chaos Scroll of Goodness 50%
    (8880167, 2436039, 10000, 1, 1), # Final Music Box | Lucid's Soul Shard
    (8880167, 4310018, 6000, 2, 2), # Final Music Box | Crusader Coin
    (8880167, 2048708, 5000, 1, 1), # Final Music Box | Rebirth Flame Lv. 150
    (8880167, 2048716, 4000, 1, 1), # Final Music Box | Powerful Rebirth Flame
    (8880167, 2433103, 10000, 1, 1), # Final Music Box | Boss Medal of Honor
    (8880167, 2435719, 3500, 1, 1), # Final Music Box | Nodestone
    (8880167, 2870861, 1000, 1, 1), # Final Music Box | Lucid Familiar
    (8880167, 3015712, 2000, 1, 1), # Final Music Box | Lucid's Nightmare Bed
    (8880167, 3015637, 1500, 1, 1), # Final Music Box | Bloom of Lucid
    (8880167, 2049136, 800, 1, 1), # Final Music Box | Incredible Chaos Scroll of Goodness 20%
    (8880167, 2049129, 3000, 1, 1), # Final Music Box | Chaos Scroll of Goodness 50%
    (8880302, 1004808, 200, 1, 1), # Will | Arcane Umbra Knight Hat
    (8880302, 1004809, 200, 1, 1), # Will | Arcane Umbra Mage Hat
    (8880302, 1004810, 200, 1, 1), # Will | Arcane Umbra Archer Hat
    (8880302, 1004811, 200, 1, 1), # Will | Arcane Umbra Thief Hat
    (8880302, 1004812, 200, 1, 1), # Will | Arcane Umbra Pirate Hat
    (8880302, 1053063, 200, 1, 1), # Will | Arcane Umbra Knight Suit
    (8880302, 1053064, 200, 1, 1), # Will | Arcane Umbra Mage Suit
    (8880302, 1053065, 200, 1, 1), # Will | Arcane Umbra Archer Suit
    (8880302, 1053066, 200, 1, 1), # Will | Arcane Umbra Thief Suit
    (8880302, 1053067, 200, 1, 1); # Will | Arcane Umbra Pirate Suit

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (8880302, 1082695, 200, 1, 1), # Will | Arcane Umbra Knight Gloves
    (8880302, 1082696, 200, 1, 1), # Will | Arcane Umbra Mage Gloves
    (8880302, 1082697, 200, 1, 1), # Will | Arcane Umbra Archer Gloves
    (8880302, 1082698, 200, 1, 1), # Will | Arcane Umbra Thief Gloves
    (8880302, 1082699, 200, 1, 1), # Will | Arcane Umbra Pirate Gloves
    (8880302, 4001890, 9000, 1, 1), # Will | Stone Cobweb Droplet
    (8880302, 2438396, 10000, 1, 1), # Will | Will's Soul Shard
    (8880302, 4310018, 6000, 2, 2), # Will | Crusader Coin
    (8880302, 2048708, 5000, 1, 1), # Will | Rebirth Flame Lv. 150
    (8880302, 2048716, 4000, 1, 1), # Will | Powerful Rebirth Flame
    (8880302, 2433103, 10000, 1, 1), # Will | Boss Medal of Honor
    (8880302, 2435719, 3500, 1, 1), # Will | Nodestone
    (8880302, 2870897, 1000, 1, 1), # Will | Will Familiar
    (8880302, 2049136, 3500, 1, 1), # Will | Incredible Chaos Scroll of Goodness 20%
    (8880302, 2049165, 2500, 1, 1), # Will | Incredible Chaos Scroll of Goodness 50%
    (8880302, 2046876, 1500, 1, 3), # Will | 9th Anniversary Prime Scroll for Accessory
    (8880302, 2046995, 1500, 1, 3), # Will | 9th Anniversary Prime Scroll for One-Handed Weapon
    (8880302, 2047817, 1500, 1, 3), # Will | 9th Anniversary Prime Scroll for Two-Handed Weapon
    (8880302, 2047950, 1500, 1, 3), # Will | 9th Anniversary Prime Scroll for Armor
    (8880302, 1162080, 500, 1, 1), # Will | Cursed Red Spellbook
    (8880302, 1162081, 500, 1, 1), # Will | Cursed Blue Spellbook
    (8880302, 1162082, 500, 1, 1), # Will | Cursed Green Spellbook
    (8880302, 1162083, 500, 1, 1), # Will | Cursed Yellow Spellbook
    (8880302, 1143105, 500, 1, 1), # Will | The True Abyss
    (8880302, 2049129, 6000, 1, 1), # Will | Chaos Scroll of Goodness 50%
    (8880302, 2438411, 10000, 1, 5), # Will | Mirror World Nodestone
    (8880342, 1004808, 100, 1, 1), # Will | Arcane Umbra Knight Hat
    (8880342, 1004809, 100, 1, 1), # Will | Arcane Umbra Mage Hat
    (8880342, 1004810, 100, 1, 1), # Will | Arcane Umbra Archer Hat
    (8880342, 1004811, 100, 1, 1), # Will | Arcane Umbra Thief Hat
    (8880342, 1004812, 100, 1, 1), # Will | Arcane Umbra Pirate Hat
    (8880342, 1053063, 100, 1, 1), # Will | Arcane Umbra Knight Suit
    (8880342, 1053064, 100, 1, 1), # Will | Arcane Umbra Mage Suit
    (8880342, 1053065, 100, 1, 1), # Will | Arcane Umbra Archer Suit
    (8880342, 1053066, 100, 1, 1), # Will | Arcane Umbra Thief Suit
    (8880342, 1053067, 100, 1, 1), # Will | Arcane Umbra Pirate Suit
    (8880342, 1082695, 100, 1, 1), # Will | Arcane Umbra Knight Gloves
    (8880342, 1082696, 100, 1, 1), # Will | Arcane Umbra Mage Gloves
    (8880342, 1082697, 100, 1, 1), # Will | Arcane Umbra Archer Gloves
    (8880342, 1082698, 100, 1, 1), # Will | Arcane Umbra Thief Gloves
    (8880342, 1082699, 100, 1, 1), # Will | Arcane Umbra Pirate Gloves
    (8880342, 4001890, 6000, 1, 1), # Will | Stone Cobweb Droplet
    (8880342, 2438396, 10000, 1, 1), # Will | Will's Soul Shard
    (8880342, 4310018, 6000, 2, 2), # Will | Crusader Coin
    (8880342, 2048708, 5000, 1, 1), # Will | Rebirth Flame Lv. 150
    (8880342, 2048716, 4000, 1, 1), # Will | Powerful Rebirth Flame
    (8880342, 2433103, 10000, 1, 1), # Will | Boss Medal of Honor
    (8880342, 2435719, 3500, 1, 1), # Will | Nodestone
    (8880342, 2049136, 800, 1, 1), # Will | Incredible Chaos Scroll of Goodness 20%
    (8880342, 2049129, 3000, 1, 1), # Will | Chaos Scroll of Goodness 50%
    (8880342, 2438411, 1000, 1, 2), # Will | Mirror World Nodestone
    (8500007, 2437606, 10000, 1, 1), # Papulatus's Tick-Tock | Extend Buff Duration (x2)
    (8500008, 2437607, 10000, 1, 1), # Papulatus's Chronos | Reduce Buff Duration (/2)
    (8500002, 2437478, 5000, 1, 1), # Papulatus | Papulatus Soul Shard
    (8500012, 3016206, 1000, 1, 1), # Papulatus | Papulatus Clock Chair
    (8500012, 2437478, 8000, 1, 1), # Papulatus | Papulatus Soul Shard
    (8500012, 2433103, 10000, 1, 1), # Papulatus | Boss Medal of Honor
    (8500012, 4031196, 6000, 2, 2), # Papulatus | Dark Tachion
    (8500022, 1022277, 300, 1, 1), # Papulatus | Papulatus Mark
    (8500022, 3016206, 2000, 1, 1), # Papulatus | Papulatus Clock Chair
    (8500022, 2437478, 8000, 1, 1), # Papulatus | Papulatus Soul Shard
    (8500022, 1142247, 90, 1, 1), # Papulatus | Time Lord
    (8500022, 1672078, 500, 1, 1), # Papulatus | Wondroid Heart
    (8500022, 1662132, 500, 1, 1), # Papulatus | Adam Wondroid
    (8500022, 1662133, 500, 1, 1), # Papulatus | Eve Wondroid
    (8500022, 2433103, 10000, 1, 1), # Papulatus | Boss Medal of Honor
    (8500022, 4310018, 6000, 2, 2), # Papulatus | Crusader Coin
    (8500022, 4031196, 6000, 2, 2), # Papulatus | Dark Tachion
    (8820001, 1162025, 4000, 1, 1), # Pink Bean | Pink Holy Cup
    (8820001, 1132272, 4000, 1, 1), # Pink Bean | Golden Clover Belt
    (8820001, 1212012, 2000, 1, 1), # Pink Bean | Reverse Dead End
    (8820001, 1222012, 2000, 1, 1), # Pink Bean | Reverse Purple Dragon
    (8820001, 1232012, 2000, 1, 1), # Pink Bean | Reverse Grim Seeker
    (8820001, 1242012, 2000, 1, 1), # Pink Bean | Reverse Hefty Head
    (8820001, 1252012, 2000, 1, 1), # Pink Bean | Reverse Kitty Pride Scepter
    (8820001, 1302086, 2000, 1, 1), # Pink Bean | Reverse Executioner
    (8820001, 1312038, 2000, 1, 1), # Pink Bean | Reverse Bardiche
    (8820001, 1322061, 2000, 1, 1), # Pink Bean | Reverse Allargando
    (8820001, 1332075, 2000, 1, 1), # Pink Bean | Reverse Pescas
    (8820001, 1332076, 2000, 1, 1), # Pink Bean | Reverse Killic
    (8820001, 1342012, 2000, 1, 1), # Pink Bean | Reverse Katara
    (8820001, 1362017, 2000, 1, 1), # Pink Bean | Reverse Persona
    (8820001, 1372045, 2000, 1, 1), # Pink Bean | Reverse Enlil Tear
    (8820001, 1382059, 2000, 1, 1), # Pink Bean | Reverse Aeas Hand
    (8820001, 1402047, 2000, 1, 1), # Pink Bean | Reverse Nibleheim
    (8820001, 1412034, 2000, 1, 1), # Pink Bean | Reverse Tabarzin
    (8820001, 1422038, 2000, 1, 1), # Pink Bean | Reverse Bellocce
    (8820001, 1432049, 2000, 1, 1), # Pink Bean | Reverse Alchupiz
    (8820001, 1442067, 2000, 1, 1), # Pink Bean | Reverse Diesra
    (8820001, 1452059, 2000, 1, 1), # Pink Bean | Reverse Engaw
    (8820001, 1462051, 2000, 1, 1), # Pink Bean | Reverse Black Beauty
    (8820001, 1472071, 2000, 1, 1), # Pink Bean | Reverse Lampion
    (8820001, 1482024, 2000, 1, 1), # Pink Bean | Reverse Equinox
    (8820001, 1492025, 2000, 1, 1), # Pink Bean | Reverse Blindness
    (8820001, 1522016, 2000, 1, 1), # Pink Bean | Reverse Blooms
    (8820001, 1532016, 2000, 1, 1), # Pink Bean | Reverse Eradicator
    (8820001, 1542012, 2000, 1, 1), # Pink Bean | Reverse Great Sword of Creation
    (8820001, 1552012, 2000, 1, 1), # Pink Bean | Reverse Fan of Altruism
    (8820001, 2020013, 10000, 10, 25), # Pink Bean | Reindeer Milk
    (8820001, 2433103, 10000, 1, 1), # Pink Bean | Boss Medal of Honor
    (8820001, 4310018, 6000, 2, 2), # Pink Bean | Crusader Coin
    (8820001, 2048701, 5000, 1, 1), # Pink Bean | Rebirth Flame Lv. 120
    (8820001, 1942001, 2500, 1, 1), # Pink Bean | Gold Mask
    (8820001, 1952001, 2500, 1, 1), # Pink Bean | Gold Pendant
    (8820001, 1962001, 2500, 1, 1), # Pink Bean | Gold Wings
    (8820001, 1972001, 2500, 1, 1), # Pink Bean | Gold Tail
    (8820001, 1612004, 2500, 1, 1), # Pink Bean | Pure Gold Engine
    (8820001, 1622004, 2500, 1, 1), # Pink Bean | Pure Gold Machine Arm
    (8820001, 1632003, 2500, 1, 1), # Pink Bean | Pure Gold Machine Leg
    (8820001, 1642002, 2500, 1, 1), # Pink Bean | Gold Body Frame
    (8820001, 1652004, 2500, 1, 1), # Pink Bean | Pure Gold Transistor
    (8820001, 4021035, 10000, 5, 15), # Pink Bean | Grand Spell Essence
    (8820001, 4021034, 10000, 5, 15), # Pink Bean | Fine Spell Essence
    (8820001, 4021033, 10000, 5, 15), # Pink Bean | Basic Spell Essence
    (8820001, 4021032, 10000, 5, 15), # Pink Bean | Mana Crystal
    (8820001, 4021031, 10000, 5, 15), # Pink Bean | Twisted Time
    (8820001, 4021020, 10000, 5, 15), # Pink Bean | Confusion Fragment
    (8820001, 2431661, 7000, 1, 1), # Pink Bean | Pink Bean's Soul Shard
    (8820001, 3010323, 1000, 1, 1), # Pink Bean | Pink Bean Chair
    (8820001, 2870379, 1000, 1, 1), # Pink Bean | Pink Bean Familiar
    (8820001, 4021010, 10000, 3, 4), # Pink Bean | Rock of Time
    (8820212, 1003621, 2000, 1, 1), # Chaos Pink Bean | Chaos Pink Bean Hat
    (8820212, 1003622, 2000, 1, 1), # Chaos Pink Bean | Black Bean Hat
    (8820212, 1052526, 2000, 1, 1), # Chaos Pink Bean | Chaos Pink Bean Suit
    (8820212, 1052527, 2000, 1, 1), # Chaos Pink Bean | Black Bean Suit
    (8820212, 1022232, 3500, 1, 1), # Chaos Pink Bean | Black Bean Mark
    (8820212, 1162025, 6000, 1, 1), # Chaos Pink Bean | Pink Holy Cup
    (8820212, 1132272, 6000, 1, 1), # Chaos Pink Bean | Golden Clover Belt
    (8820212, 1212012, 2000, 1, 1), # Chaos Pink Bean | Reverse Dead End
    (8820212, 1222012, 2000, 1, 1), # Chaos Pink Bean | Reverse Purple Dragon
    (8820212, 1232012, 2000, 1, 1), # Chaos Pink Bean | Reverse Grim Seeker
    (8820212, 1242012, 2000, 1, 1), # Chaos Pink Bean | Reverse Hefty Head
    (8820212, 1252012, 2000, 1, 1), # Chaos Pink Bean | Reverse Kitty Pride Scepter
    (8820212, 1302086, 2000, 1, 1), # Chaos Pink Bean | Reverse Executioner
    (8820212, 1312038, 2000, 1, 1), # Chaos Pink Bean | Reverse Bardiche
    (8820212, 1322061, 2000, 1, 1), # Chaos Pink Bean | Reverse Allargando
    (8820212, 1332075, 2000, 1, 1), # Chaos Pink Bean | Reverse Pescas
    (8820212, 1332076, 2000, 1, 1), # Chaos Pink Bean | Reverse Killic
    (8820212, 1342012, 2000, 1, 1), # Chaos Pink Bean | Reverse Katara
    (8820212, 1362017, 2000, 1, 1), # Chaos Pink Bean | Reverse Persona
    (8820212, 1372045, 2000, 1, 1), # Chaos Pink Bean | Reverse Enlil Tear
    (8820212, 1382059, 2000, 1, 1), # Chaos Pink Bean | Reverse Aeas Hand
    (8820212, 1402047, 2000, 1, 1), # Chaos Pink Bean | Reverse Nibleheim
    (8820212, 1412034, 2000, 1, 1), # Chaos Pink Bean | Reverse Tabarzin
    (8820212, 1422038, 2000, 1, 1), # Chaos Pink Bean | Reverse Bellocce
    (8820212, 1432049, 2000, 1, 1), # Chaos Pink Bean | Reverse Alchupiz
    (8820212, 1442067, 2000, 1, 1), # Chaos Pink Bean | Reverse Diesra
    (8820212, 1452059, 2000, 1, 1), # Chaos Pink Bean | Reverse Engaw
    (8820212, 1462051, 2000, 1, 1), # Chaos Pink Bean | Reverse Black Beauty
    (8820212, 1472071, 2000, 1, 1), # Chaos Pink Bean | Reverse Lampion
    (8820212, 1482024, 2000, 1, 1), # Chaos Pink Bean | Reverse Equinox
    (8820212, 1492025, 2000, 1, 1), # Chaos Pink Bean | Reverse Blindness
    (8820212, 1522016, 2000, 1, 1), # Chaos Pink Bean | Reverse Blooms
    (8820212, 1532016, 2000, 1, 1), # Chaos Pink Bean | Reverse Eradicator
    (8820212, 1542012, 2000, 1, 1), # Chaos Pink Bean | Reverse Great Sword of Creation
    (8820212, 1552012, 2000, 1, 1), # Chaos Pink Bean | Reverse Fan of Altruism
    (8820212, 2020013, 10000, 10, 25), # Chaos Pink Bean | Reindeer Milk
    (8820212, 2433103, 10000, 1, 1), # Chaos Pink Bean | Boss Medal of Honor
    (8820212, 4310018, 6000, 2, 2), # Chaos Pink Bean | Crusader Coin
    (8820212, 2048702, 5000, 1, 1), # Chaos Pink Bean | Rebirth Flame Lv. 130
    (8820212, 1942001, 2500, 1, 1), # Chaos Pink Bean | Gold Mask
    (8820212, 1952001, 2500, 1, 1), # Chaos Pink Bean | Gold Pendant
    (8820212, 1962001, 2500, 1, 1), # Chaos Pink Bean | Gold Wings
    (8820212, 1972001, 2500, 1, 1), # Chaos Pink Bean | Gold Tail
    (8820212, 1612004, 2500, 1, 1), # Chaos Pink Bean | Pure Gold Engine
    (8820212, 1622004, 2500, 1, 1), # Chaos Pink Bean | Pure Gold Machine Arm
    (8820212, 1632003, 2500, 1, 1), # Chaos Pink Bean | Pure Gold Machine Leg
    (8820212, 1642002, 2500, 1, 1), # Chaos Pink Bean | Gold Body Frame
    (8820212, 1652004, 2500, 1, 1), # Chaos Pink Bean | Pure Gold Transistor
    (8820212, 4021035, 10000, 5, 15), # Chaos Pink Bean | Grand Spell Essence
    (8820212, 4021034, 10000, 5, 15), # Chaos Pink Bean | Fine Spell Essence
    (8820212, 4021033, 10000, 5, 15), # Chaos Pink Bean | Basic Spell Essence
    (8820212, 4021032, 10000, 5, 15), # Chaos Pink Bean | Mana Crystal
    (8820212, 4021031, 10000, 5, 15), # Chaos Pink Bean | Twisted Time
    (8820212, 4021020, 10000, 5, 15), # Chaos Pink Bean | Confusion Fragment
    (8820212, 2431661, 8000, 1, 1), # Chaos Pink Bean | Pink Bean's Soul Shard
    (8820212, 3010323, 2000, 1, 1), # Chaos Pink Bean | Pink Bean Chair
    (8820212, 1022182, 1000, 1, 1), # Chaos Pink Bean | Chaos Pink Bean Mark
    (8820212, 2433981, 200, 1, 1), # Chaos Pink Bean | Pink Bean Damage Skin
    (8820212, 2870379, 1000, 1, 1), # Chaos Pink Bean | Pink Bean Familiar
    (8820212, 2049129, 2000, 1, 1), # Chaos Pink Bean | Chaos Scroll of Goodness 50%
    (8820212, 2049133, 2000, 1, 1), # Chaos Pink Bean | Miraculous Chaos Scroll 50%
    (8820212, 4021010, 10000, 4, 5), # Chaos Pink Bean | Rock of Time
    (8840007, 4021031, 10000, 5, 15), # Von Leon | Twisted Time
    (8840007, 4021020, 10000, 5, 15), # Von Leon | Confusion Fragment
    (8840007, 4310010, 8000, 1, 1), # Von Leon | Royal Lion King Medal
    (8840007, 3010188, 1000, 1, 1), # Von Leon | Von Leon Chair
    (8840007, 2431662, 6000, 1, 1), # Von Leon | Von Leon Soul Shard
    (8840000, 4021031, 10000, 5, 15), # Von Leon | Twisted Time
    (8840000, 4310010, 8000, 1, 1), # Von Leon | Royal Lion King Medal
    (8840000, 4001832, 10000, 20, 60), # Von Leon | Spell Trace
    (8840000, 2433103, 10000, 1, 1), # Von Leon | Boss Medal of Honor
    (8840000, 3010188, 2000, 1, 1), # Von Leon | Von Leon Chair
    (8840000, 2431662, 7000, 1, 1), # Von Leon | Von Leon Soul Shard
    (8800022, 1372049, 10000, 1, 1), # Crumbling Zakum | Zakum's Tree Branch
    (8800022, 1002357, 10000, 1, 1), # Crumbling Zakum | Zakum Helmet
    (8800022, 2431710, 5000, 1, 1), # Crumbling Zakum | Zakum's Soul Shard
    (8800022, 4001083, 10000, 1, 1), # Crumbling Zakum | Zakum Certificate
    (8800002, 1372049, 10000, 1, 1), # Zakum | Zakum's Tree Branch
    (8800002, 1002357, 10000, 1, 1), # Zakum | Zakum Helmet
    (8800002, 1022231, 4000, 1, 1), # Zakum | Aquatic Letter Eye Accessory
    (8800002, 1012478, 4000, 1, 1), # Zakum | Condensed Power Crystal
    (8800002, 1212098, 1000, 1, 1), # Zakum | Zakum's Poisonic Shining Rod
    (8800002, 1222092, 1000, 1, 1), # Zakum | Zakum's Poisonic Soul Shooter
    (8800002, 1232092, 1000, 1, 1), # Zakum | Zakum's Poisonic Desperado
    (8800002, 1242099, 1000, 1, 1), # Zakum | Zakum's Poisonic Whip Blade
    (8800002, 1252083, 1000, 1, 1), # Zakum | Zakum's Poisonic Shining Stick
    (8800002, 1262012, 1000, 1, 1), # Zakum | Zakum's Poisonic Psy-limiter
    (8800002, 1272020, 1000, 1, 1), # Zakum | Zakum's Poisonic Chain
    (8800002, 1282020, 1000, 1, 1), # Zakum | Zakum's Poisonic Lucent Gauntlet
    (8800002, 1302312, 1000, 1, 1), # Zakum | Zakum's Poisonic Sword
    (8800002, 1312182, 1000, 1, 1), # Zakum | Zakum's Poisonic Axe
    (8800002, 1322233, 1000, 1, 1), # Zakum | Zakum's Poisonic Hammer
    (8800002, 1332257, 1000, 1, 1), # Zakum | Zakum's Poisonic Dagger
    (8800002, 1342097, 1000, 1, 1), # Zakum | Zakum's Poisonic Katara
    (8800002, 1362118, 1000, 1, 1), # Zakum | Zakum's Poisonic Cane
    (8800002, 1372204, 1000, 1, 1), # Zakum | Zakum's Poisonic Wand
    (8800002, 1382242, 1000, 1, 1), # Zakum | Zakum's Poisonic Staff
    (8800002, 1402233, 1000, 1, 1), # Zakum | Zakum's Poisonic Two-handed Sword
    (8800002, 1412161, 1000, 1, 1), # Zakum | Zakum's Poisonic Two-handed Axe
    (8800002, 1422168, 1000, 1, 1), # Zakum | Zakum's Poisonic Sledgehammer
    (8800002, 1432197, 1000, 1, 1), # Zakum | Zakum's Poisonic Spear
    (8800002, 1442251, 1000, 1, 1), # Zakum | Zakum's Poisonic Polearm
    (8800002, 1452235, 1000, 1, 1), # Zakum | Zakum's Poisonic Bow
    (8800002, 1462222, 1000, 1, 1), # Zakum | Zakum's Poisonic Crossbow
    (8800002, 1472244, 1000, 1, 1), # Zakum | Zakum's Poisonic Guards
    (8800002, 1482199, 1000, 1, 1), # Zakum | Zakum's Poisonic Knuckle
    (8800002, 1492209, 1000, 1, 1), # Zakum | Zakum's Poisonic Gun
    (8800002, 1522121, 1000, 1, 1), # Zakum | Zakum's Poisonic Dual Bowguns
    (8800002, 1532127, 1000, 1, 1), # Zakum | Zakum's Poisonic Hand Cannon
    (8800002, 1542098, 1000, 1, 1), # Zakum | Zakum's Poisonic Katana
    (8800002, 1552099, 1000, 1, 1), # Zakum | Zakum's Poisonic Fan
    (8800002, 1582012, 1000, 1, 1), # Zakum | Zakum's Poisonic Arm Cannon
    (8800002, 1213009, 1500, 1, 1), # Zakum | Zakum's Poisonic Vile Regard
    (8800002, 3010333, 2500, 1, 1), # Zakum | Zakum Chair
    (8800002, 2431710, 8000, 1, 1), # Zakum | Zakum's Soul Shard
    (8800102, 1372073, 10000, 1, 1), # Chaos Zakum | Chaos Zakum's Tree Branch
    (8800102, 1003112, 10000, 1, 1), # Chaos Zakum | Chaos Zakum Helmet
    (8800102, 1004637, 500, 1, 1), # Chaos Zakum | Enraged Zakum Helmet
    (8800102, 1102871, 500, 1, 1), # Chaos Zakum | Enraged Zakum Cape
    (8800102, 1132296, 500, 1, 1), # Chaos Zakum | Enraged Zakum Belt
    (8800102, 1022231, 5000, 1, 1), # Chaos Zakum | Aquatic Letter Eye Accessory
    (8800102, 1012478, 5000, 1, 1), # Chaos Zakum | Condensed Power Crystal
    (8800102, 1212098, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Shining Rod
    (8800102, 1222092, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Soul Shooter
    (8800102, 1232092, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Desperado
    (8800102, 1242099, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Whip Blade
    (8800102, 1252083, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Shining Stick
    (8800102, 1262012, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Psy-limiter
    (8800102, 1272020, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Chain
    (8800102, 1282020, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Lucent Gauntlet
    (8800102, 1302312, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Sword
    (8800102, 1312182, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Axe
    (8800102, 1322233, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Hammer
    (8800102, 1332257, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Dagger
    (8800102, 1342097, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Katara
    (8800102, 1362118, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Cane
    (8800102, 1372204, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Wand
    (8800102, 1382242, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Staff
    (8800102, 1402233, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Two-handed Sword
    (8800102, 1412161, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Two-handed Axe
    (8800102, 1422168, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Sledgehammer
    (8800102, 1432197, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Spear
    (8800102, 1442251, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Polearm
    (8800102, 1452235, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Bow
    (8800102, 1462222, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Crossbow
    (8800102, 1472244, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Guards
    (8800102, 1482199, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Knuckle
    (8800102, 1492209, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Gun
    (8800102, 1522121, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Dual Bowguns
    (8800102, 1532127, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Hand Cannon
    (8800102, 1542098, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Katana
    (8800102, 1552099, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Fan
    (8800102, 1582012, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Arm Cannon
    (8800102, 1213009, 1500, 1, 1), # Chaos Zakum | Zakum's Poisonic Vile Regard
    (8800102, 3010333, 3500, 1, 1), # Chaos Zakum | Zakum Chair
    (8800102, 2431710, 8000, 1, 1), # Chaos Zakum | Zakum's Soul Shard
    (8800102, 2433103, 10000, 1, 1), # Chaos Zakum | Boss Medal of Honor
    (8800102, 4310018, 6000, 2, 2), # Chaos Zakum | Crusader Coin
    (9450022, 2432755, 8000, 1, 1), # Princess No | Princess No's Gold Jewel Box
    (9450022, 2432755, 4000, 1, 1), # Princess No | Princess No's Gold Jewel Box
    (9450022, 2432755, 2000, 1, 1), # Princess No | Princess No's Gold Jewel Box
    (9450022, 2432754, 10000, 1, 1), # Princess No | Princess No's Silver Jewel Box
    (9450022, 2432754, 5000, 1, 1), # Princess No | Princess No's Silver Jewel Box
    (9450022, 2432754, 2500, 1, 1), # Princess No | Princess No's Silver Jewel Box
    (9450022, 2433103, 10000, 1, 1), # Princess No | Boss Medal of Honor
    (9450022, 2433103, 10000, 1, 1), # Princess No | Boss Medal of Honor
    (9450022, 2433103, 10000, 1, 1), # Princess No | Boss Medal of Honor
    (9450022, 2433103, 10000, 1, 1), # Princess No | Boss Medal of Honor
    (9450022, 2433103, 10000, 1, 1), # Princess No | Boss Medal of Honor
    (9450022, 2433103, 10000, 1, 1), # Princess No | Boss Medal of Honor
    (9450022, 4001832, 10000, 20, 40), # Princess No | Spell Trace
    (9450022, 4001832, 10000, 20, 40), # Princess No | Spell Trace
    (9450022, 4001832, 10000, 20, 40), # Princess No | Spell Trace
    (8520000, 1942001, 2500, 1, 1), # Pianus | Gold Mask
    (8520000, 1952001, 2500, 1, 1), # Pianus | Gold Pendant
    (8520000, 1962001, 2500, 1, 1), # Pianus | Gold Wings
    (8520000, 1972001, 2500, 1, 1), # Pianus | Gold Tail
    (8520000, 1612004, 2500, 1, 1), # Pianus | Pure Gold Engine
    (8520000, 1622004, 2500, 1, 1), # Pianus | Pure Gold Machine Arm
    (8520000, 1632003, 2500, 1, 1), # Pianus | Pure Gold Machine Leg
    (8520000, 1642002, 2500, 1, 1), # Pianus | Gold Body Frame
    (8520000, 1652004, 2500, 1, 1), # Pianus | Pure Gold Transistor
    (8520000, 4021035, 10000, 5, 15), # Pianus | Grand Spell Essence
    (8520000, 4021034, 10000, 5, 15), # Pianus | Fine Spell Essence
    (8520000, 4021033, 10000, 5, 15), # Pianus | Basic Spell Essence
    (8520000, 4021032, 10000, 5, 15), # Pianus | Mana Crystal
    (8520000, 4001832, 10000, 2, 10), # Pianus | Spell Trace
    (8520000, 4001085, 2500, 1, 1), # Pianus | Pianus Certificate
    (8520000, 2431895, 8000, 1, 1), # Pianus | Pianus's Soul Shard
    (8510000, 1942001, 2500, 1, 1), # Pianus | Gold Mask
    (8510000, 1952001, 2500, 1, 1), # Pianus | Gold Pendant
    (8510000, 1962001, 2500, 1, 1), # Pianus | Gold Wings
    (8510000, 1972001, 2500, 1, 1), # Pianus | Gold Tail
    (8510000, 1612004, 2500, 1, 1), # Pianus | Pure Gold Engine
    (8510000, 1622004, 2500, 1, 1), # Pianus | Pure Gold Machine Arm
    (8510000, 1632003, 2500, 1, 1), # Pianus | Pure Gold Machine Leg
    (8510000, 1642002, 2500, 1, 1), # Pianus | Gold Body Frame
    (8510000, 1652004, 2500, 1, 1), # Pianus | Pure Gold Transistor
    (8510000, 4021035, 10000, 5, 15), # Pianus | Grand Spell Essence
    (8510000, 4021034, 10000, 5, 15), # Pianus | Fine Spell Essence
    (8510000, 4021033, 10000, 5, 15), # Pianus | Basic Spell Essence
    (8510000, 4021032, 10000, 5, 15), # Pianus | Mana Crystal
    (8510000, 4001832, 10000, 2, 10), # Pianus | Spell Trace
    (8510000, 4001085, 2500, 1, 1), # Pianus | Pianus Certificate
    (8510000, 2431895, 7000, 1, 1), # Pianus | Pianus's Soul Shard
    (5250007, 2431752, 10000, 1, 1), # Ephenia | Ephenia's Soul Shard
    (5250007, 1112683, 5000, 1, 1), # Ephenia | Ephenia's Ring
    (5250007, 2022176, 10000, 10, 10), # Ephenia | Power Elixir
    (5250007, 2020013, 10000, 10, 25), # Ephenia | Reindeer Milk
    (5250007, 2000004, 10000, 10, 25), # Ephenia | Elixir
    (9309201, 1142687, 5000, 1, 1), # Giant Spider | Webmaster
    (9309201, 2433840, 10000, 2, 5), # Giant Spider | Tower of Oz Medal of Honor
    (9309201, 2433840, 5000, 2, 5), # Giant Spider | Tower of Oz Medal of Honor
    (9309201, 2433840, 3000, 1, 3), # Giant Spider | Tower of Oz Medal of Honor
    (9309201, 2432464, 4000, 1, 2), # Giant Spider | Oz Point Pouch
    (9309201, 2432464, 4000, 1, 2), # Giant Spider | Oz Point Pouch
    (9309201, 2432464, 2000, 1, 2), # Giant Spider | Oz Point Pouch
    (9309201, 2028272, 1000, 1, 1), # Giant Spider | Alicia's Ring Box (10th Rank)
    (9309201, 2028272, 1000, 1, 1), # Giant Spider | Alicia's Ring Box (10th Rank)
    (9309201, 2028271, 1000, 1, 1), # Giant Spider | Alicia's Ring Box (9th Rank)
    (9309205, 1142689, 5000, 1, 1), # Tin Woodsman | Have a Heart
    (9309205, 2433840, 10000, 2, 5), # Tin Woodsman | Tower of Oz Medal of Honor
    (9309205, 2433840, 5000, 2, 5), # Tin Woodsman | Tower of Oz Medal of Honor
    (9309205, 2433840, 3000, 1, 3), # Tin Woodsman | Tower of Oz Medal of Honor
    (9309205, 2432464, 6000, 1, 2), # Tin Woodsman | Oz Point Pouch
    (9309205, 2432464, 5000, 1, 2), # Tin Woodsman | Oz Point Pouch
    (9309205, 2432464, 2000, 1, 3), # Tin Woodsman | Oz Point Pouch
    (9309205, 2432464, 2000, 1, 3), # Tin Woodsman | Oz Point Pouch
    (9309205, 2028270, 2000, 1, 1), # Tin Woodsman | Alicia's Ring Box (8th Rank)
    (9309205, 2028270, 1000, 1, 1), # Tin Woodsman | Alicia's Ring Box (8th Rank)
    (9309205, 2028269, 1000, 1, 1), # Tin Woodsman | Alicia's Ring Box (7th Rank)
    (9309200, 1142691, 5000, 1, 1), # Cowardly Lion | Courageous
    (9309200, 2433840, 10000, 2, 5), # Cowardly Lion | Tower of Oz Medal of Honor
    (9309200, 2433840, 5000, 2, 5), # Cowardly Lion | Tower of Oz Medal of Honor
    (9309200, 2433840, 3000, 1, 3), # Cowardly Lion | Tower of Oz Medal of Honor
    (9309200, 2432464, 10000, 1, 2), # Cowardly Lion | Oz Point Pouch
    (9309200, 2432464, 9000, 1, 2), # Cowardly Lion | Oz Point Pouch
    (9309200, 2432464, 4000, 1, 3), # Cowardly Lion | Oz Point Pouch
    (9309200, 2432464, 2000, 1, 3), # Cowardly Lion | Oz Point Pouch
    (9309200, 2028268, 2000, 1, 1), # Cowardly Lion | Alicia's Ring Box (6th Rank)
    (9309200, 2028268, 1000, 1, 1), # Cowardly Lion | Alicia's Ring Box (6th Rank)
    (9309200, 2028267, 1000, 1, 1), # Cowardly Lion | Alicia's Ring Box (5th Rank)
    (9309203, 1142693, 5000, 1, 1), # Scarecrow | Brainy
    (9309203, 2433840, 10000, 2, 5), # Scarecrow | Tower of Oz Medal of Honor
    (9309203, 2433840, 5000, 2, 5), # Scarecrow | Tower of Oz Medal of Honor
    (9309203, 2433840, 3000, 1, 3), # Scarecrow | Tower of Oz Medal of Honor
    (9309203, 2432464, 10000, 1, 2), # Scarecrow | Oz Point Pouch
    (9309203, 2432464, 10000, 1, 2), # Scarecrow | Oz Point Pouch
    (9309203, 2432464, 5000, 1, 3), # Scarecrow | Oz Point Pouch
    (9309203, 2432464, 2000, 1, 3), # Scarecrow | Oz Point Pouch
    (9309203, 2432464, 2000, 1, 3), # Scarecrow | Oz Point Pouch
    (9309203, 2028266, 2000, 1, 1), # Scarecrow | Alicia's Ring Box (4th Rank)
    (9309203, 2028266, 1000, 1, 1), # Scarecrow | Alicia's Ring Box (4th Rank)
    (9309203, 2028265, 500, 1, 1), # Scarecrow | Alicia's Ring Box (3rd Rank)
    (9309207, 1142695, 5000, 1, 1), # Dorothy | No Place Like Home
    (9309207, 2433840, 10000, 2, 5), # Dorothy | Tower of Oz Medal of Honor
    (9309207, 2433840, 5000, 2, 5), # Dorothy | Tower of Oz Medal of Honor
    (9309207, 2433840, 3000, 1, 3), # Dorothy | Tower of Oz Medal of Honor
    (9309207, 2432464, 10000, 1, 2), # Dorothy | Oz Point Pouch
    (9309207, 2432464, 10000, 1, 2), # Dorothy | Oz Point Pouch
    (9309207, 2432464, 5000, 2, 4), # Dorothy | Oz Point Pouch
    (9309207, 2432464, 2000, 2, 4), # Dorothy | Oz Point Pouch
    (9309207, 2432464, 2000, 2, 4), # Dorothy | Oz Point Pouch
    (9309207, 2028264, 2000, 1, 1), # Dorothy | Alicia's Ring Box (2nd Rank)
    (9309207, 2028264, 1000, 1, 1), # Dorothy | Alicia's Ring Box (2nd Rank)
    (9309207, 2028263, 200, 1, 1), # Dorothy | Alicia's Ring Box (1st Rank)
    (8220022, 2433834, 10000, 1, 1), # Black Knight | Rare Treasure Chest
    (8220022, 2433834, 7000, 1, 1), # Black Knight | Rare Treasure Chest
    (8220022, 2433834, 3500, 1, 1), # Black Knight | Rare Treasure Chest
    (8220022, 2433834, 1500, 1, 1), # Black Knight | Rare Treasure Chest
    (8220023, 2433834, 10000, 1, 1), # Mad Mage | Rare Treasure Chest
    (8220023, 2433834, 7000, 1, 1), # Mad Mage | Rare Treasure Chest
    (8220023, 2433834, 3500, 1, 1), # Mad Mage | Rare Treasure Chest
    (8220023, 2433834, 1500, 1, 1), # Mad Mage | Rare Treasure Chest
    (8220024, 2433834, 10000, 1, 1), # Rampant Cyborg | Rare Treasure Chest
    (8220024, 2433834, 7000, 1, 1), # Rampant Cyborg | Rare Treasure Chest
    (8220024, 2433834, 3500, 1, 1), # Rampant Cyborg | Rare Treasure Chest
    (8220024, 2433834, 1500, 1, 1), # Rampant Cyborg | Rare Treasure Chest
    (8220025, 2433834, 10000, 1, 1), # Vicious Hunter | Rare Treasure Chest
    (8220025, 2433834, 7000, 1, 1), # Vicious Hunter | Rare Treasure Chest
    (8220025, 2433834, 3500, 1, 1), # Vicious Hunter | Rare Treasure Chest
    (8220025, 2433834, 1500, 1, 1), # Vicious Hunter | Rare Treasure Chest
    (8220026, 2433834, 10000, 1, 1), # Bad Brawler | Rare Treasure Chest
    (8220026, 2433834, 7000, 1, 1), # Bad Brawler | Rare Treasure Chest
    (8220026, 2433834, 3500, 1, 1), # Bad Brawler | Rare Treasure Chest
    (8220026, 2433834, 1500, 1, 1), # Bad Brawler | Rare Treasure Chest
    (9390624, 4310098, 5000, 3, 3), # Conjured Veilprey | Gollux Penny
    (9390624, 2432091, 250, 1, 1), # Conjured Veilprey | Gollux's Head Teleport Rock
    (9390624, 2432092, 250, 1, 1), # Conjured Veilprey | Gollux's Left Shoulder Teleport Rock
    (9390624, 2432093, 250, 1, 1), # Conjured Veilprey | Gollux's Right Shoulder Teleport Rock
    (9390625, 4310098, 5000, 3, 3), # Conjured Veilprey | Gollux Penny
    (9390625, 2432091, 250, 1, 1), # Conjured Veilprey | Gollux's Head Teleport Rock
    (9390625, 2432092, 250, 1, 1), # Conjured Veilprey | Gollux's Left Shoulder Teleport Rock
    (9390625, 2432093, 250, 1, 1), # Conjured Veilprey | Gollux's Right Shoulder Teleport Rock
    (9390634, 4310098, 5000, 3, 3), # Mutated Wardlock | Gollux Penny
    (9390634, 2432091, 250, 1, 1), # Mutated Wardlock | Gollux's Head Teleport Rock
    (9390634, 2432092, 250, 1, 1), # Mutated Wardlock | Gollux's Left Shoulder Teleport Rock
    (9390634, 2432093, 250, 1, 1), # Mutated Wardlock | Gollux's Right Shoulder Teleport Rock
    (9390637, 4310098, 5000, 3, 3), # Gollux's Nutrient Flower | Gollux Penny
    (9390637, 2432091, 250, 1, 1), # Gollux's Nutrient Flower | Gollux's Head Teleport Rock
    (9390637, 2432092, 250, 1, 1), # Gollux's Nutrient Flower | Gollux's Left Shoulder Teleport Rock
    (9390637, 2432093, 250, 1, 1), # Gollux's Nutrient Flower | Gollux's Right Shoulder Teleport Rock
    (9390643, 4310098, 5000, 3, 3), # Perdit Seed | Gollux Penny
    (9390643, 2432091, 250, 1, 1), # Perdit Seed | Gollux's Head Teleport Rock
    (9390643, 2432092, 250, 1, 1), # Perdit Seed | Gollux's Left Shoulder Teleport Rock
    (9390643, 2432093, 250, 1, 1), # Perdit Seed | Gollux's Right Shoulder Teleport Rock
    (9390640, 4310098, 5000, 3, 3), # Veiler Boom | Gollux Penny
    (9390640, 2432091, 250, 1, 1), # Veiler Boom | Gollux's Head Teleport Rock
    (9390640, 2432092, 250, 1, 1), # Veiler Boom | Gollux's Left Shoulder Teleport Rock
    (9390640, 2432093, 250, 1, 1), # Veiler Boom | Gollux's Right Shoulder Teleport Rock
    (9390612, 4310097, 10000, 5, 5), # Gollux: Abdomen | Gollux Coin
    (9390610, 4310098, 10000, 3, 3), # Gollux: Right Shoulder | Gollux Penny
    (9390610, 4310098, 10000, 3, 3), # Gollux: Right Shoulder | Gollux Penny
    (9390610, 4310098, 10000, 3, 3), # Gollux: Right Shoulder | Gollux Penny
    (9390610, 4310098, 10000, 3, 3), # Gollux: Right Shoulder | Gollux Penny
    (9390610, 4310098, 10000, 3, 3), # Gollux: Right Shoulder | Gollux Penny
    (9390610, 4310098, 10000, 3, 3), # Gollux: Right Shoulder | Gollux Penny
    (9390610, 4001832, 10000, 10, 30), # Gollux: Right Shoulder | Spell Trace
    (9390610, 2022176, 10000, 10, 20), # Gollux: Right Shoulder | Power Elixir
    (9390610, 2022176, 10000, 10, 20), # Gollux: Right Shoulder | Power Elixir
    (9390610, 2022176, 10000, 10, 20), # Gollux: Right Shoulder | Power Elixir
    (9390610, 2432091, 250, 1, 1), # Gollux: Right Shoulder | Gollux's Head Teleport Rock
    (9390610, 2432092, 250, 1, 1), # Gollux: Right Shoulder | Gollux's Left Shoulder Teleport Rock
    (9390610, 2432093, 250, 1, 1), # Gollux: Right Shoulder | Gollux's Right Shoulder Teleport Rock
    (9390612, 4310097, 10000, 5, 5), # Gollux: Abdomen | Gollux Coin
    (9390611, 4310098, 10000, 3, 3), # Gollux: Left Shoulder | Gollux Penny
    (9390611, 4310098, 10000, 3, 3), # Gollux: Left Shoulder | Gollux Penny
    (9390611, 4310098, 10000, 3, 3), # Gollux: Left Shoulder | Gollux Penny
    (9390611, 4310098, 10000, 3, 3), # Gollux: Left Shoulder | Gollux Penny
    (9390611, 4310098, 10000, 3, 3), # Gollux: Left Shoulder | Gollux Penny
    (9390611, 4310098, 10000, 3, 3), # Gollux: Left Shoulder | Gollux Penny
    (9390611, 4001832, 10000, 10, 30), # Gollux: Left Shoulder | Spell Trace
    (9390611, 2022176, 10000, 10, 20), # Gollux: Left Shoulder | Power Elixir
    (9390611, 2022176, 10000, 10, 20), # Gollux: Left Shoulder | Power Elixir
    (9390611, 2022176, 10000, 10, 20), # Gollux: Left Shoulder | Power Elixir
    (9390611, 2432091, 250, 1, 1), # Gollux: Left Shoulder | Gollux's Head Teleport Rock
    (9390611, 2432092, 250, 1, 1), # Gollux: Left Shoulder | Gollux's Left Shoulder Teleport Rock
    (9390611, 2432093, 250, 1, 1), # Gollux: Left Shoulder | Gollux's Right Shoulder Teleport Rock
    (9390612, 4310097, 10000, 5, 5), # Gollux: Abdomen | Gollux Coin
    (9390612, 4310098, 10000, 3, 3), # Gollux: Abdomen | Gollux Penny
    (9390612, 4310098, 10000, 3, 3), # Gollux: Abdomen | Gollux Penny
    (9390612, 4310098, 10000, 3, 3), # Gollux: Abdomen | Gollux Penny
    (9390612, 4310098, 10000, 3, 3), # Gollux: Abdomen | Gollux Penny
    (9390612, 4310098, 10000, 3, 3), # Gollux: Abdomen | Gollux Penny
    (9390612, 4310098, 10000, 3, 3), # Gollux: Abdomen | Gollux Penny
    (9390612, 4001832, 10000, 10, 30), # Gollux: Abdomen | Spell Trace
    (9390612, 2022176, 10000, 10, 20), # Gollux: Abdomen | Power Elixir
    (9390612, 2022176, 10000, 10, 20), # Gollux: Abdomen | Power Elixir
    (9390612, 2022176, 10000, 10, 20), # Gollux: Abdomen | Power Elixir
    (9390612, 2432091, 250, 1, 1), # Gollux: Abdomen | Gollux's Head Teleport Rock
    (9390612, 2432092, 250, 1, 1), # Gollux: Abdomen | Gollux's Left Shoulder Teleport Rock
    (9390612, 2432093, 250, 1, 1), # Gollux: Abdomen | Gollux's Right Shoulder Teleport Rock
    (9601459, 1132243, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Belt
    (9601459, 1122264, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Pendant
    (9601459, 4310098, 10000, 15, 30), # Gollux's Corrupted Heart | Gollux Penny
    (9601459, 4310097, 10000, 15, 35), # Gollux's Corrupted Heart | Gollux Coin
    (9601460, 1132243, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Belt
    (9601460, 1122264, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Pendant
    (9601460, 1132244, 600, 1, 1), # Gollux's Corrupted Heart | Solid Engraved Gollux Belt
    (9601460, 1122265, 600, 1, 1), # Gollux's Corrupted Heart | Solid Engraved Gollux Pendant
    (9601460, 4310098, 10000, 30, 60), # Gollux's Corrupted Heart | Gollux Penny
    (9601460, 4310097, 10000, 35, 75), # Gollux's Corrupted Heart | Gollux Coin
    (9601461, 1132243, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Belt
    (9601461, 1122264, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Pendant
    (9601461, 1132244, 600, 1, 1), # Gollux's Corrupted Heart | Solid Engraved Gollux Belt
    (9601461, 1122265, 600, 1, 1), # Gollux's Corrupted Heart | Solid Engraved Gollux Pendant
    (9601461, 1132245, 400, 1, 1), # Gollux's Corrupted Heart | Reinforced Engraved Gollux Belt
    (9601461, 1122266, 400, 1, 1), # Gollux's Corrupted Heart | Reinforced Engraved Gollux Pendant
    (9601461, 4310098, 10000, 60, 120), # Gollux's Corrupted Heart | Gollux Penny
    (9601461, 4310097, 10000, 75, 125), # Gollux's Corrupted Heart | Gollux Coin
    (9601462, 1132243, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Belt
    (9601462, 1122264, 1000, 1, 1), # Gollux's Corrupted Heart | Cracked Engraved Gollux Pendant
    (9601462, 1132244, 600, 1, 1), # Gollux's Corrupted Heart | Solid Engraved Gollux Belt
    (9601462, 1122265, 600, 1, 1), # Gollux's Corrupted Heart | Solid Engraved Gollux Pendant
    (9601462, 1132245, 400, 1, 1), # Gollux's Corrupted Heart | Reinforced Engraved Gollux Belt
    (9601462, 1122266, 400, 1, 1), # Gollux's Corrupted Heart | Reinforced Engraved Gollux Pendant
    (9601462, 1132246, 150, 1, 1), # Gollux's Corrupted Heart | Superior Engraved Gollux Belt
    (9601462, 1122267, 150, 1, 1), # Gollux's Corrupted Heart | Superior Engraved Gollux Pendant
    (9601462, 4310098, 10000, 120, 180), # Gollux's Corrupted Heart | Gollux Penny
    (9601462, 4310097, 10000, 125, 200), # Gollux's Corrupted Heart | Gollux Coin
    (8644650, 4001893, 10000, 10, 20), # Giant Monster Gloom | Spark of Determination
    (8644650, 2048708, 5000, 1, 1), # Giant Monster Gloom | Rebirth Flame Lv. 150
    (8644650, 2433103, 10000, 1, 1), # Giant Monster Gloom | Boss Medal of Honor
    (8644650, 4310018, 6000, 2, 2), # Giant Monster Gloom | Crusader Coin
    (8644650, 4021031, 10000, 125, 250), # Giant Monster Gloom | Twisted Time
    (8645009, 4001893, 10000, 10, 20), # Guard Captain Darknell | Spark of Determination
    (8645009, 2048708, 5000, 1, 1), # Guard Captain Darknell | Rebirth Flame Lv. 150
    (8645009, 2439568, 8000, 1, 1), # Guard Captain Darknell | Darknell's Soul Shard
    (8645009, 2433103, 10000, 1, 1), # Guard Captain Darknell | Boss Medal of Honor
    (8645009, 4310018, 6000, 2, 2), # Guard Captain Darknell | Crusader Coin
    (8645009, 4021031, 10000, 125, 250), # Guard Captain Darknell | Twisted Time
    (8645009, 2048702, 5000, 1, 1), # Guard Captain Darknell | Rebirth Flame Lv. 130
    (8645009, 2048701, 5000, 1, 1), # Guard Captain Darknell | Rebirth Flame Lv. 120
    (8645009, 2048700, 5000, 1, 1), # Guard Captain Darknell | Rebirth Flame Lv. 110
    (8645009, 2439500, 3500, 1, 1), # Guard Captain Darknell | Eyeful's Equipment Coupon (Special)
    (2300200, 4036523, 5000, 1, 1), # Ruins Guardian | Pillar Offering
    (2300201, 4036524, 5000, 1, 1), # Pudgy Flower | Pudgy Flower Seed Oil
    (2300201, 4036525, 5000, 1, 1), # Pudgy Flower | Pudgy Flower Stems
    (2300202, 4036526, 5000, 1, 1), # Toxiblossom | Toxiblossom Leaf
    (2300203, 4036527, 5000, 1, 1), # Hoppin' Sprout | Pottery Shards
    (2300204, 4036528, 5000, 1, 1), # Chargin' Sprout | Censer Ashes
    (2400103, 4033573, 10000, 1, 1), # Dinogoth | Dinogoth Shoulder Meat
    (2400104, 4033575, 10000, 1, 1), # Dinoram | Dinor Sirloin
    (2400105, 4033579, 10000, 1, 1), # Dinodon | Dinodon Sharp Fang
    (9420568, 4033251, 10000, 1, 1), # Burly Swashbuckler Mack | Blue Ore
    (9420569, 4033252, 10000, 1, 1), # Furnace Ferret | Fire Ore
    (5110301, 4000364, 10000, 1, 1), # Roid | Wires
    (4110300, 4000357, 10000, 1, 1), # Iron Mutae | Piece of Steel
    (4110301, 4000358, 10000, 1, 1), # Reinforced Iron Mutae | Hardened Piece of Steel
    (9420514, 4000429, 10000, 1, 1), # Berserkie | Sweat Bead
    (9420515, 4000430, 10000, 1, 1), # Veetron | Veetron Horn
    (9420516, 4000431, 10000, 1, 1), # Slygie | Slygie Tail
    (9420517, 4000432, 10000, 1, 1), # Petrifighter | Moss Rock
    (9420518, 4000433, 10000, 1, 1), # Montrecer | Oil Canister
    (9420518, 4000434, 10000, 1, 1), # Montrecer | Rafflesia
    (9420513, 1182060, 10000, 1, 1), # Capt. Latanica | Ghost Ship Exorcist
    (9420513, 4000384, 10000, 1, 1), # Capt. Latanica | Black Essence
    (9420513, 4000385, 10000, 1, 1), # Capt. Latanica | Soul Lantern
    (1000044, 4033920, 10000, 1, 1), # ? | Jr. Sentinel Shellpiece
    (9270030, 4000383, 10000, 1, 1), # ? | Red Essence
    (9420512, 4000383, 10000, 1, 1), # Mr. Anchor | Red Essence
    (8641000, 1712001, 450, 1, 1), # Happy Erda | Arcane Symbol: Vanishing Journey
    (8641001, 1712001, 450, 1, 1), # Raging Erda | Arcane Symbol: Vanishing Journey
    (8641002, 1712001, 450, 1, 1), # Sad Erda | Arcane Symbol: Vanishing Journey
    (8641003, 1712001, 450, 1, 1), # Joyful Erda | Arcane Symbol: Vanishing Journey
    (8641004, 1712001, 450, 1, 1), # Stone Erda | Arcane Symbol: Vanishing Journey
    (8641005, 1712001, 450, 1, 1), # Blazing Erda | Arcane Symbol: Vanishing Journey
    (8641006, 1712001, 450, 1, 1), # Soulful Erda | Arcane Symbol: Vanishing Journey
    (8641007, 1712001, 450, 1, 1), # Tranquil Erda | Arcane Symbol: Vanishing Journey
    (8641008, 1712001, 450, 1, 1), # Lantern Erda | Arcane Symbol: Vanishing Journey
    (8641009, 1712001, 450, 1, 1), # Arma's Follower | Arcane Symbol: Vanishing Journey
    (8641010, 1712001, 450, 1, 1), # Arma | Arcane Symbol: Vanishing Journey
    (8641011, 1712001, 450, 1, 1), # Arma | Arcane Symbol: Vanishing Journey
    (8641012, 1712001, 450, 1, 1), # Arma's Follower | Arcane Symbol: Vanishing Journey
    (8641051, 1712001, 450, 1, 1), # Erda Rat | Arcane Symbol: Vanishing Journey
    (8641052, 1712001, 450, 1, 1), # Monto | Arcane Symbol: Vanishing Journey
    (8641053, 1712001, 450, 1, 1), # Seeker T-Drone Model A | Arcane Symbol: Vanishing Journey
    (8641054, 1712001, 450, 1, 1), # Seeker T-Drone Model B | Arcane Symbol: Vanishing Journey
    (8641055, 1712001, 450, 1, 1), # Combat T-Drone Model A | Arcane Symbol: Vanishing Journey
    (8641056, 1712001, 450, 1, 1), # Combat T-Drone Model B | Arcane Symbol: Vanishing Journey
    (8641070, 1712001, 450, 1, 1), # Strong Exterminator T-Drone Model A | Arcane Symbol: Vanishing Journey
    (8641071, 1712001, 450, 1, 1), # Strong Exterminator T-Drone Model B | Arcane Symbol: Vanishing Journey
    (8641066, 1712001, 450, 1, 1), # Strong Seeker T-Drone Model A | Arcane Symbol: Vanishing Journey
    (8641067, 1712001, 450, 1, 1), # Strong Seeker T-Drone Model B | Arcane Symbol: Vanishing Journey
    (8641068, 1712001, 450, 1, 1), # Strong Combat T-Drone Model A | Arcane Symbol: Vanishing Journey
    (8641069, 1712001, 450, 1, 1), # Strong Combat T-Drone Model B | Arcane Symbol: Vanishing Journey
    (8642000, 1712002, 495, 1, 1), # Pinedeer | Arcane Symbol: Chu Chu Island
    (8642001, 1712002, 495, 1, 1), # Bighorn Pinedeer | Arcane Symbol: Chu Chu Island
    (8642002, 1712002, 495, 1, 1), # Ewenana | Arcane Symbol: Chu Chu Island
    (8642003, 1712002, 495, 1, 1), # Ramanana | Arcane Symbol: Chu Chu Island
    (8642004, 1712002, 495, 1, 1), # Flyon | Arcane Symbol: Chu Chu Island
    (8642005, 1712002, 495, 1, 1), # Angry Flyon | Arcane Symbol: Chu Chu Island
    (8642006, 1712002, 495, 1, 1), # Unripe Wolfruit | Arcane Symbol: Chu Chu Island
    (8642007, 1712002, 495, 1, 1), # Ripe Wolfruit | Arcane Symbol: Chu Chu Island
    (8642008, 1712002, 495, 1, 1), # Green Catfish | Arcane Symbol: Chu Chu Island
    (8642009, 1712002, 495, 1, 1), # Blue Catfish | Arcane Symbol: Chu Chu Island
    (8642010, 1712002, 495, 1, 1), # Rhyturtle | Arcane Symbol: Chu Chu Island
    (8642011, 1712002, 495, 1, 1), # Boss Rhyturtle | Arcane Symbol: Chu Chu Island
    (8642012, 1712002, 495, 1, 1), # Crilia | Arcane Symbol: Chu Chu Island
    (8642013, 1712002, 495, 1, 1), # Patriarch Crilia | Arcane Symbol: Chu Chu Island
    (8642014, 1712002, 495, 1, 1), # Birdshark | Arcane Symbol: Chu Chu Island
    (8642015, 1712002, 495, 1, 1), # Patriarch Birdshark | Arcane Symbol: Chu Chu Island
    (8642050, 1712002, 495, 1, 1), # Monkeyrog | Arcane Symbol: Chu Chu Island
    (8642051, 1712002, 495, 1, 1), # Bunshroom | Arcane Symbol: Chu Chu Island
    (8642052, 1712002, 495, 1, 1), # Lytone | Arcane Symbol: Chu Chu Island
    (8642053, 1712002, 495, 1, 1), # Stonepy | Arcane Symbol: Chu Chu Island
    (8642054, 1712002, 495, 1, 1), # Kumpider | Arcane Symbol: Chu Chu Island
    (8642055, 1712002, 495, 1, 1), # Squirrelnon | Arcane Symbol: Chu Chu Island
    (8642060, 1712002, 495, 1, 1), # Potent Monkeyrog | Arcane Symbol: Chu Chu Island
    (8642061, 1712002, 495, 1, 1), # Potent Bunshroom | Arcane Symbol: Chu Chu Island
    (8642062, 1712002, 495, 1, 1), # Potent Lytone | Arcane Symbol: Chu Chu Island
    (8642063, 1712002, 495, 1, 1), # Potent Stonepy | Arcane Symbol: Chu Chu Island
    (8642064, 1712002, 495, 1, 1), # Potent Kumpider | Arcane Symbol: Chu Chu Island
    (8642065, 1712002, 495, 1, 1), # Potent Squirrelnon | Arcane Symbol: Chu Chu Island
    (8643001, 1712003, 495, 1, 1), # Paper Bag Alley Citizen | Arcane Symbol: Lachelein
    (8643002, 1712003, 495, 1, 1), # Wood Board Alley Citizen | Arcane Symbol: Lachelein
    (8643003, 1712003, 495, 1, 1), # Gallina | Arcane Symbol: Lachelein
    (8643004, 1712003, 495, 1, 1), # Gallus | Arcane Symbol: Lachelein
    (8643005, 1712003, 495, 1, 1), # Angry Victory Plate | Arcane Symbol: Lachelein
    (8643006, 1712003, 495, 1, 1), # Crooked Victory Plate | Arcane Symbol: Lachelein
    (8643007, 1712003, 495, 1, 1), # Red Dancing Shoes | Arcane Symbol: Lachelein
    (8643008, 1712003, 495, 1, 1), # Angry Masquerade Citizen | Arcane Symbol: Lachelein
    (8643009, 1712003, 495, 1, 1), # Insane Masquerade Citizen | Arcane Symbol: Lachelein
    (8643010, 1712003, 495, 1, 1), # Blue-eyed Gargoyle | Arcane Symbol: Lachelein
    (8643011, 1712003, 495, 1, 1), # Red-eyed Gargoyle | Arcane Symbol: Lachelein
    (8643012, 1712003, 495, 1, 1), # Weakened Dreamkeeper | Arcane Symbol: Lachelein
    (8644402, 4036309, 7500, 1, 1), # Nameless Cat | Kritias Coin
    (8644403, 4036310, 7500, 1, 1), # Powerful Gangster | Ripe Apple
    (8644405, 2437666, 7500, 1, 1), # Blue Shadow | Kritias Research Journal
    (8644406, 4036311, 7500, 1, 1), # Red Shadow | Red Reagent
    (8644407, 4036312, 1000, 1, 1), # Experiment Gone Wrong | Jean's Gift
    (8644000, 1712004, 495, 1, 1), # Water Spirit | Arcane Symbol: Arcana
    (8644001, 1712004, 495, 1, 1), # Sun Spirit | Arcane Symbol: Arcana
    (8644002, 1712004, 495, 1, 1), # Earth Spirit | Arcane Symbol: Arcana
    (8644003, 1712004, 495, 1, 1), # Snow Cloud Spirit | Arcane Symbol: Arcana
    (8644004, 1712004, 495, 1, 1), # Thunder Cloud Spirit | Arcane Symbol: Arcana
    (8644005, 1712004, 495, 1, 1), # Toxic Spirit | Arcane Symbol: Arcana
    (8644006, 1712004, 495, 1, 1), # Volatile Spirit | Arcane Symbol: Arcana
    (8644007, 1712004, 495, 1, 1), # Befuddled Spirit | Arcane Symbol: Arcana
    (8644008, 1712004, 495, 1, 1), # Anguished Spirit | Arcane Symbol: Arcana
    (8644009, 1712004, 495, 1, 1), # Mournful Spirit | Arcane Symbol: Arcana
    (8644010, 1712004, 495, 1, 1), # Discordant Spirit | Arcane Symbol: Arcana
    (8644400, 1712005, 495, 1, 1), # Xenoroid Echo Type A | Arcane Symbol: Morass
    (8644401, 1712005, 495, 1, 1), # Xenoroid Echo Type B | Arcane Symbol: Morass
    (8644402, 1712005, 495, 1, 1), # Nameless Cat | Arcane Symbol: Morass
    (8644403, 1712005, 495, 1, 1), # Powerful Gangster | Arcane Symbol: Morass
    (8644404, 1712005, 495, 1, 1), # Strong Gangster | Arcane Symbol: Morass
    (8644405, 1712005, 495, 1, 1), # Blue Shadow | Arcane Symbol: Morass
    (8644406, 1712005, 495, 1, 1), # Red Shadow | Arcane Symbol: Morass
    (8644407, 1712005, 495, 1, 1), # Experiment Gone Wrong | Arcane Symbol: Morass
    (8644408, 1712005, 495, 1, 1), # Big Experiment Gone Wrong | Arcane Symbol: Morass
    (8644409, 1712005, 495, 1, 1), # Thralled Guard | Arcane Symbol: Morass
    (8644410, 1712005, 495, 1, 1), # Thralled Warhammer Knight | Arcane Symbol: Morass
    (8644411, 1712005, 495, 1, 1), # Thralled Wizard | Arcane Symbol: Morass
    (8644412, 1712005, 495, 1, 1), # Thralled Archer | Arcane Symbol: Morass
    (8644500, 1712006, 495, 1, 1), # Ahtuin | Arcane Symbol: Esfera
    (8644501, 1712006, 495, 1, 1), # Atus | Arcane Symbol: Esfera
    (8644502, 1712006, 495, 1, 1), # Bellalion | Arcane Symbol: Esfera
    (8644503, 1712006, 495, 1, 1), # Bellalis | Arcane Symbol: Esfera
    (8644504, 1712006, 495, 1, 1), # Aranya | Arcane Symbol: Esfera
    (8644505, 1712006, 495, 1, 1), # Aranea | Arcane Symbol: Esfera
    (8644506, 1712006, 495, 1, 1), # Keeper of Light | Arcane Symbol: Esfera
    (8644507, 1712006, 495, 1, 1), # Keeper of Darkness | Arcane Symbol: Esfera
    (8644508, 1712006, 495, 1, 1), # Light Executor | Arcane Symbol: Esfera
    (8644509, 1712006, 495, 1, 1), # Dark Executor | Arcane Symbol: Esfera
    (8642100, 1712006, 495, 1, 1), # Veritate | Arcane Symbol: Esfera
    (8642101, 1712006, 495, 1, 1), # Volar | Arcane Symbol: Esfera
    (8642102, 1712006, 495, 1, 1), # Oceanleli | Arcane Symbol: Esfera
    (8642103, 1712006, 495, 1, 1), # Hyades | Arcane Symbol: Esfera
    (8642104, 1712006, 495, 1, 1), # Denebola | Arcane Symbol: Esfera
    (8642105, 1712006, 495, 1, 1), # Lilli Borea | Arcane Symbol: Esfera
    (8642106, 1712006, 495, 1, 1), # Angelus | Arcane Symbol: Esfera
    (8641013, 1712001, 450, 1, 1), # Joyful Erda | Arcane Symbol: Vanishing Journey
    (8641014, 1712001, 450, 1, 1), # Soulful Erda | Arcane Symbol: Vanishing Journey
    (8643000, 1712003, 495, 1, 1), # Dreamkeeper | Arcane Symbol: Lachelein
    (8645123, 1713000, 495, 1, 1), # Ebonstar Foot Soldier | Sacred Symbol: Cernium
    (8645124, 1713000, 495, 1, 1), # Ebonstar Archer | Sacred Symbol: Cernium
    (8645125, 1713000, 495, 1, 1), # Monster Gull | Sacred Symbol: Cernium
    (8645126, 1713000, 495, 1, 1), # Fire Spirit | Sacred Symbol: Cernium
    (8645127, 1713000, 495, 1, 1), # Wandering Scholar Ghost | Sacred Symbol: Cernium
    (8645128, 1713000, 495, 1, 1), # Curious Scholar Ghost | Sacred Symbol: Cernium
    (8645135, 1713000, 495, 1, 1), # Chief Monster Gull | Sacred Symbol: Cernium
    (8645149, 1713000, 495, 1, 1), # Haunted Tome | Sacred Symbol: Cernium
    (8645129, 1713000, 495, 1, 1), # Ebonstar Bombardier | Sacred Symbol: Cernium
    (8645130, 1713000, 495, 1, 1), # Ebonstar Magician | Sacred Symbol: Cernium
    (8645131, 1713000, 495, 1, 1), # Flora Foot Soldier | Sacred Symbol: Cernium
    (8645132, 1713000, 495, 1, 1), # Flora Magician | Sacred Symbol: Cernium
    (8645133, 1713000, 495, 1, 1), # Flora Assassin | Sacred Symbol: Cernium
    (8645134, 1713000, 495, 1, 1), # Flora Heavy Infantry | Sacred Symbol: Cernium
    (8645137, 1713000, 495, 1, 1), # Enraged Fire Spirit | Sacred Symbol: Cernium
    (8645151, 1713000, 495, 1, 1), # Burning Tome | Sacred Symbol: Cernium
    (8645200, 1713001, 495, 1, 1), # Sandblade Marauder | Sacred Symbol: Arcus
    (8645201, 1713001, 495, 1, 1), # Ironshot Desperado | Sacred Symbol: Arcus
    (8645202, 1713001, 495, 1, 1), # Puxillian Rover | Sacred Symbol: Arcus
    (8645203, 1713001, 495, 1, 1), # Puxillian Scrounger | Sacred Symbol: Arcus
    (8645204, 1713001, 495, 1, 1), # Conductorbot | Sacred Symbol: Arcus
    (8645205, 1713001, 495, 1, 1), # Houndbot | Sacred Symbol: Arcus
    (8645206, 1713001, 495, 1, 1), # Stone Kiwi | Sacred Symbol: Arcus
    (8644000, 2435719, 100, 1, 1), # Water Spirit | Nodestone
    (8644001, 2435719, 100, 1, 1), # Sun Spirit | Nodestone
    (8644002, 2435719, 100, 1, 1), # Earth Spirit | Nodestone
    (8644003, 2435719, 100, 1, 1), # Snow Cloud Spirit | Nodestone
    (8644004, 2435719, 100, 1, 1), # Thunder Cloud Spirit | Nodestone
    (8644005, 2435719, 100, 1, 1), # Toxic Spirit | Nodestone
    (8644006, 2435719, 100, 1, 1), # Volatile Spirit | Nodestone
    (8644007, 2435719, 100, 1, 1), # Befuddled Spirit | Nodestone
    (8644008, 2435719, 100, 1, 1), # Anguished Spirit | Nodestone
    (8644009, 2435719, 100, 1, 1), # Mournful Spirit | Nodestone
    (8644010, 2435719, 100, 1, 1), # Discordant Spirit | Nodestone
    (8642050, 2435719, 100, 1, 1), # Monkeyrog | Nodestone
    (8642051, 2435719, 100, 1, 1), # Bunshroom | Nodestone
    (8642052, 2435719, 100, 1, 1), # Lytone | Nodestone
    (8642053, 2435719, 100, 1, 1), # Stonepy | Nodestone
    (8642054, 2435719, 100, 1, 1), # Kumpider | Nodestone
    (8642055, 2435719, 100, 1, 1), # Squirrelnon | Nodestone
    (8642060, 2435719, 100, 1, 1), # Potent Monkeyrog | Nodestone
    (8642061, 2435719, 100, 1, 1), # Potent Bunshroom | Nodestone
    (8642062, 2435719, 100, 1, 1), # Potent Lytone | Nodestone
    (8642063, 2435719, 100, 1, 1), # Potent Stonepy | Nodestone
    (8642064, 2435719, 100, 1, 1), # Potent Kumpider | Nodestone
    (8642065, 2435719, 100, 1, 1), # Potent Squirrelnon | Nodestone
    (8644400, 2435719, 100, 1, 1), # Xenoroid Echo Type A | Nodestone
    (8644401, 2435719, 100, 1, 1), # Xenoroid Echo Type B | Nodestone
    (8644402, 2435719, 100, 1, 1), # Nameless Cat | Nodestone
    (8644403, 2435719, 100, 1, 1), # Powerful Gangster | Nodestone
    (8644404, 2435719, 100, 1, 1), # Strong Gangster | Nodestone
    (8644405, 2435719, 100, 1, 1), # Blue Shadow | Nodestone
    (8644406, 2435719, 100, 1, 1), # Red Shadow | Nodestone
    (8644407, 2435719, 100, 1, 1), # Experiment Gone Wrong | Nodestone
    (8644408, 2435719, 100, 1, 1), # Big Experiment Gone Wrong | Nodestone
    (8644409, 2435719, 100, 1, 1), # Thralled Guard | Nodestone
    (8644410, 2435719, 100, 1, 1), # Thralled Warhammer Knight | Nodestone
    (8644411, 2435719, 100, 1, 1), # Thralled Wizard | Nodestone
    (8644412, 2435719, 100, 1, 1), # Thralled Archer | Nodestone
    (8644500, 2435719, 100, 1, 1), # Ahtuin | Nodestone
    (8644501, 2435719, 100, 1, 1), # Atus | Nodestone
    (8644502, 2435719, 100, 1, 1), # Bellalion | Nodestone
    (8644503, 2435719, 100, 1, 1), # Bellalis | Nodestone
    (8644504, 2435719, 100, 1, 1), # Aranya | Nodestone
    (8644505, 2435719, 100, 1, 1), # Aranea | Nodestone
    (8644506, 2435719, 100, 1, 1), # Keeper of Light | Nodestone
    (8644507, 2435719, 100, 1, 1), # Keeper of Darkness | Nodestone
    (8644508, 2435719, 100, 1, 1), # Light Executor | Nodestone
    (8644509, 2435719, 100, 1, 1), # Dark Executor | Nodestone
    (8642100, 2435719, 100, 1, 1), # Veritate | Nodestone
    (8642101, 2435719, 100, 1, 1), # Volar | Nodestone
    (8642102, 2435719, 100, 1, 1), # Oceanleli | Nodestone
    (8642103, 2435719, 100, 1, 1), # Hyades | Nodestone
    (8642104, 2435719, 100, 1, 1), # Denebola | Nodestone
    (8642105, 2435719, 100, 1, 1), # Lilli Borea | Nodestone
    (8642106, 2435719, 100, 1, 1), # Angelus | Nodestone
    (8641012, 2435719, 100, 1, 1), # Arma's Follower | Nodestone
    (8641013, 2435719, 100, 1, 1), # Joyful Erda | Nodestone
    (8641014, 2435719, 100, 1, 1), # Soulful Erda | Nodestone
    (8641051, 2435719, 100, 1, 1), # Erda Rat | Nodestone
    (8641052, 2435719, 100, 1, 1), # Monto | Nodestone
    (8641053, 2435719, 100, 1, 1), # Seeker T-Drone Model A | Nodestone
    (8641054, 2435719, 100, 1, 1), # Seeker T-Drone Model B | Nodestone
    (8641055, 2435719, 100, 1, 1), # Combat T-Drone Model A | Nodestone
    (8641056, 2435719, 100, 1, 1), # Combat T-Drone Model B | Nodestone
    (8641070, 2435719, 100, 1, 1), # Strong Exterminator T-Drone Model A | Nodestone
    (8641071, 2435719, 100, 1, 1), # Strong Exterminator T-Drone Model B | Nodestone
    (8641066, 2435719, 100, 1, 1), # Strong Seeker T-Drone Model A | Nodestone
    (8641067, 2435719, 100, 1, 1), # Strong Seeker T-Drone Model B | Nodestone
    (8641068, 2435719, 100, 1, 1), # Strong Combat T-Drone Model A | Nodestone
    (8641069, 2435719, 100, 1, 1), # Strong Combat T-Drone Model B | Nodestone
    (8644614, 2435719, 100, 1, 1), # Soot Beast | Nodestone
    (8644615, 2435719, 100, 1, 1), # Soot Talon | Nodestone
    (8644616, 2435719, 100, 1, 1), # Soot Slug | Nodestone
    (8644617, 2435719, 100, 1, 1), # Soot Core | Nodestone
    (8644618, 2435719, 100, 1, 1), # Crushing Glare | Nodestone
    (8644619, 2435719, 100, 1, 1), # Burst Glare | Nodestone
    (8644700, 2435719, 100, 1, 1), # Entangled Fragment | Nodestone
    (8644701, 2435719, 100, 1, 1), # Faith Fragment | Nodestone
    (8644702, 2435719, 100, 1, 1), # Dark Miscreation | Nodestone
    (8644703, 2435719, 100, 1, 1), # Dark Construct | Nodestone
    (8644704, 2435719, 100, 1, 1), # Despairing Wing | Nodestone
    (8644705, 2435719, 100, 1, 1), # Despairing Blade | Nodestone
    (8644706, 2435719, 100, 1, 1), # Silent Knight | Nodestone
    (8644707, 2435719, 100, 1, 1), # Silent Scout | Nodestone
    (8644708, 2435719, 100, 1, 1), # Silent Rogue | Nodestone
    (8644709, 2435719, 100, 1, 1), # Silent Watchman | Nodestone
    (8644710, 2435719, 100, 1, 1), # Silent Assassin | Nodestone
    (8644711, 2435719, 100, 1, 1), # Despairing Soul | Nodestone
    (8644712, 2435719, 100, 1, 1), # Despairing Prisoner | Nodestone
    (8645010, 2435719, 100, 1, 1), # Ancestion | Nodestone
    (8645011, 2435719, 100, 1, 1), # Ascendion | Nodestone
    (8645012, 2435719, 100, 1, 1), # Transcendion | Nodestone
    (8645022, 2435719, 100, 1, 1), # Embrion | Nodestone
    (8645023, 2435719, 100, 1, 1), # Foreberion | Nodestone
    (8644500, 4036449, 1000, 1, 1), # Ahtuin | Flare
    (8644501, 4036450, 7500, 1, 1), # Atus | Fresh Firewood
    (8644503, 4036451, 7500, 1, 1), # Bellalis | Animal Fat
    (9309042, 4009237, 10000, 5, 5), # Ancient Blue Turtle | Ancient Blue Turtle Egg
    (9309043, 4009238, 10000, 5, 5), # Ancient Orange Turtle | Ancient Orange Turtle Egg
    (9101078, 4310096, 10000, 2, 4), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 10000, 2, 4), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 10000, 1, 4), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 10000, 2, 5), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 10000, 1, 5), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 9000, 1, 3), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 4000, 2, 3), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 3000, 2, 4), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 3000, 2, 5), # Inferno Wolf | Swordie Coin
    (9101078, 4310096, 3000, 2, 4), # Inferno Wolf | Swordie Coin
    (6400100, 2870268, 100, 1, 1), # [*]Deep Buffoon | [*]Deep Buffoon Familiar
    (6230101, 2870250, 100, 1, 1), # Puco | Puco Familiar
    (6230100, 2870249, 100, 1, 1), # Wild Kargo | Wild Kargo Familiar
    (8145008, 2871190, 100, 1, 1), # Bearic the White | Bearic the White Familiar
    (8145004, 2870999, 100, 1, 1), # Clowning Scarlion | Clowning Scarlion Familiar
    (8145006, 2871188, 100, 1, 1), # Soaring Scarlion | Soaring Scarlion Familiar
    (8145007, 2871189, 100, 1, 1), # Jellybus | Jellybus Familiar
    (8145000, 2870996, 100, 1, 1), # Creeper Scarlion | Creeper Scarlion Familiar
    (9601159, 2870955, 100, 1, 1), # Lil Luminous (Light) | Lil Luminous (Light) Familiar
    (8145001, 2870997, 100, 1, 1), # Ratacula | Ratacula Familiar
    (8800400, 2870640, 100, 1, 1), # Tarantulus | Tarantulus Familiar
    (8145003, 2870998, 100, 1, 1), # Canterbear | Canterbear Familiar
    (9601162, 2870958, 100, 1, 1), # Lil Mercedes | Lil Mercedes Familiar
    (2400317, 2870872, 100, 1, 1), # Hazard's Vile Crony | Hazard's Vile Crony Familiar
    (9601163, 2870959, 100, 1, 1), # Lil Phantom | Lil Phantom Familiar
    (9300119, 2870409, 100, 1, 1), # Lord Pirate | Lord Pirate Familiar
    (2400318, 2870873, 100, 1, 1), # Hazard's Rotten Crony | Hazard's Rotten Crony Familiar
    (9601160, 2870956, 100, 1, 1), # Lil Luminous (Dark) | Lil Luminous (Dark) Familiar
    (9601161, 2870957, 100, 1, 1), # Lil Luminous (Equilibrium) | Lil Luminous (Equilibrium) Familiar
    (9601166, 2870962, 100, 1, 1), # Petite Lania | Petite Lania Familiar
    (9601164, 2870960, 100, 1, 1), # Lil Shade | Lil Shade Familiar
    (2400315, 2870871, 100, 1, 1), # Roving Hoodlum | Roving Hoodlum Familiar
    (9601165, 2870961, 100, 1, 1), # Lil Moonbeam | Lil Moonbeam Familiar
    (9300140, 2870411, 100, 1, 1), # Angry Frankenroid | Angry Frankenroid Familiar
    (2400256, 2870966, 100, 1, 1), # Snowy Stjartmes | Snowy Stjartmes Familiar
    (2400257, 2870967, 100, 1, 1), # Stormy Stjartmes | Stormy Stjartmes Familiar
    (9300139, 2870410, 100, 1, 1), # Frankenroid | Frankenroid Familiar
    (2400258, 2870882, 100, 1, 1), # Cloud Guardian | Cloud Guardian Familiar
    (8880302, 2870898, 100, 1, 1), # Will | Will Familiar
    (5120100, 2870195, 100, 1, 1), # MT-09 | MT-09 Familiar
    (8880301, 2870897, 100, 1, 1), # Will | Will Familiar
    (2400265, 2870881, 100, 1, 1), # Wind Guardian | Wind Guardian Familiar
    (9400507, 2871061, 100, 1, 1), # 1st Anniversary Cake Monster | 1st Anniversary Cake Monster Familiar
    (9400506, 2871060, 100, 1, 1), # Candle Monster | Candle Monster Familiar
    (8147008, 2870636, 100, 1, 1), # Polluted Tree Spirit | Polluted Tree Spirit Familiar
    (8147009, 2870637, 100, 1, 1), # Corrupted Tree Spirit | Corrupted Tree Spirit Familiar
    (8147010, 2870638, 100, 1, 1), # Polluted Rock Spirit | Polluted Rock Spirit Familiar
    (8147011, 2870639, 100, 1, 1), # Corrupted Rock Spirit | Corrupted Rock Spirit Familiar
    (9300173, 2870414, 100, 1, 1), # Poisoned Stone Bug | Poisoned Stone Bug Familiar
    (8147004, 2870632, 100, 1, 1), # Dark Flora | Dark Flora Familiar
    (9300172, 2870413, 100, 1, 1), # Poisoned Lord Tree | Poisoned Lord Tree Familiar
    (8147005, 2870633, 100, 1, 1), # Hornet | Hornet Familiar
    (9300175, 2870415, 100, 1, 1), # Poisoned Spright | Poisoned Spright Familiar
    (8147006, 2870634, 100, 1, 1), # Poison Hornet | Poison Hornet Familiar
    (8147007, 2870635, 100, 1, 1), # General Hornet | General Hornet Familiar
    (1110100, 2870009, 100, 1, 1), # Green Mushroom | Green Mushroom Familiar
    (8147000, 2870628, 100, 1, 1), # Mantis | Mantis Familiar
    (1110101, 2870010, 100, 1, 1), # Dark Stump | Dark Stump Familiar
    (8147001, 2870629, 100, 1, 1), # Blood Mantis | Blood Mantis Familiar
    (8147002, 2870630, 100, 1, 1), # Poison Mantis | Poison Mantis Familiar
    (8147003, 2870631, 100, 1, 1), # Flora | Flora Familiar
    (8190004, 2870349, 100, 1, 1), # Skelosaurus | Skelosaurus Familiar
    (5120002, 2870193, 100, 1, 1), # Lioner | Lioner Familiar
    (5120003, 2870194, 100, 1, 1), # Grupin | Grupin Familiar
    (5120000, 2870191, 100, 1, 1), # Luster Pixie | Luster Pixie Familiar
    (8190002, 2870347, 100, 1, 1), # Nest Golem | Nest Golem Familiar
    (9400513, 2871062, 100, 1, 1), # Deluxe Candle Monster | Deluxe Candle Monster Familiar
    (5120001, 2870192, 100, 1, 1), # Cellion | Cellion Familiar
    (8190003, 2870348, 100, 1, 1), # Skelegon | Skelegon Familiar
    (9300162, 2870412, 100, 1, 1), # Willi | Willi Familiar
    (8610003, 2870462, 100, 1, 1), # Night | Night Familiar
    (8610002, 2870461, 100, 1, 1), # Wind | Wind Familiar
    (8610001, 2870460, 100, 1, 1), # Blaze | Blaze Familiar
    (8610000, 2870459, 100, 1, 1), # Dawn | Dawn Familiar
    (8610007, 2870466, 100, 1, 1), # Official Knight C | Official Knight C Familiar
    (8610006, 2870465, 100, 1, 1), # Official Knight B | Official Knight B Familiar
    (8610005, 2870464, 100, 1, 1), # Official Knight A | Official Knight A Familiar
    (8610004, 2870463, 100, 1, 1), # Thunder | Thunder Familiar
    (9300181, 2870418, 100, 1, 1), # Charged Poison Golem | Charged Poison Golem Familiar
    (8610011, 2870470, 100, 1, 1), # Advanced Knight B | Advanced Knight B Familiar
    (9300180, 2870417, 100, 1, 1), # Poison Golem | Poison Golem Familiar
    (8610010, 2870469, 100, 1, 1), # Advanced Knight A | Advanced Knight A Familiar
    (8610009, 2870468, 100, 1, 1), # Official Knight E | Official Knight E Familiar
    (9300182, 2870419, 100, 1, 1), # Super-Charged Poison Golem | Super-Charged Poison Golem Familiar
    (8610008, 2870467, 100, 1, 1), # Official Knight D | Official Knight D Familiar
    (9300176, 2870416, 100, 1, 1), # Poison Golem | Poison Golem Familiar
    (8610014, 2870473, 100, 1, 1), # Advanced Knight E | Advanced Knight E Familiar
    (8610013, 2870472, 100, 1, 1), # Advanced Knight D | Advanced Knight D Familiar
    (8610012, 2870471, 100, 1, 1), # Advanced Knight C | Advanced Knight C Familiar
    (6400000, 2870264, 100, 1, 1), # Dark Yeti | Dark Yeti Familiar
    (2400325, 2870870, 100, 1, 1), # Mr. Hazard | Mr. Hazard Familiar
    (9601267, 2871226, 100, 1, 1), # Corrupted Master | Corrupted Master Familiar
    (1110130, 2870011, 100, 1, 1), # Dejected Green Mushroom | Dejected Green Mushroom Familiar
    (6400003, 2870265, 100, 1, 1), # Cuzco | Cuzco Familiar
    (9601265, 2871225, 100, 1, 1), # Elderwraith | Elderwraith Familiar
    (9601270, 2871229, 100, 1, 1), # Corrupted Shadowknight | Corrupted Shadowknight Familiar
    (2220100, 2870043, 100, 1, 1), # Blue Mushroom | Blue Mushroom Familiar
    (6400004, 2870266, 100, 1, 1), # Opachu | Opachu Familiar
    (9601271, 2871230, 100, 1, 1), # Corrupted Stormcaster | Corrupted Stormcaster Familiar
    (6400007, 2870267, 100, 1, 1), # Baby Balrog | Baby Balrog Familiar
    (9601268, 2871227, 100, 1, 1), # Corrupted Windreaver | Corrupted Windreaver Familiar
    (9601269, 2871228, 100, 1, 1), # Corrupted Flamekeeper | Corrupted Flamekeeper Familiar
    (9601274, 2871233, 100, 1, 1), # Shadowknight Dehiti | Shadowknight Dehiti Familiar
    (9601275, 2871234, 100, 1, 1), # Stormcaster Caeneus | Stormcaster Caeneus Familiar
    (9400549, 2871063, 100, 1, 1), # Headless Horseman | Headless Horseman Familiar
    (9601272, 2871231, 100, 1, 1), # Windreaver Atlante | Windreaver Atlante Familiar
    (9601273, 2871232, 100, 1, 1), # Flamekeeper Siana | Flamekeeper Siana Familiar
    (2220110, 2870044, 100, 1, 1), # Crying Blue Mushroom | Crying Blue Mushroom Familiar
    (9400575, 2871065, 100, 1, 1), # Bigfoot | Bigfoot Familiar
    (9400574, 2871064, 100, 1, 1), # Typhon | Typhon Familiar
    (9601254, 2871219, 100, 1, 1), # Sinister Jungle Monkey | Sinister Jungle Monkey Familiar
    (9601255, 2871220, 100, 1, 1), # Violent Jungle Monkey | Violent Jungle Monkey Familiar
    (9601253, 2871218, 100, 1, 1), # Krakia Jungle Bug | Krakia Jungle Bug Familiar
    (9601258, 2871223, 100, 1, 1), # Gryphon Warrior | Gryphon Warrior Familiar
    (9601256, 2871221, 100, 1, 1), # Jungle Bear Warrior | Jungle Bear Warrior Familiar
    (9601257, 2871222, 100, 1, 1), # Jungle Monkey Warrior | Jungle Monkey Warrior Familiar
    (9601263, 2871224, 100, 1, 1), # Krakian Spirit | Krakian Spirit Familiar
    (9410801, 2880727, 100, 1, 1), # Omega Tutu | Omega Tutu Familiar
    (3150000, 2870075, 100, 1, 1), # Safety First | Safety First Familiar
    (3150001, 2870076, 100, 1, 1), # Baby Boulder Muncher | Baby Boulder Muncher Familiar
    (3150002, 2870077, 100, 1, 1), # Big Boulder Muncher | Big Boulder Muncher Familiar
    (8644619, 2870982, 100, 1, 1), # Burst Glare | Burst Glare Familiar
    (8880140, 2870698, 100, 1, 1), # Dreaming Lucid | Dreaming Lucid Familiar
    (9400323, 2870750, 100, 1, 1), # Giant Snowman (Lvl 2) - Easy | Giant Snowman (Lvl 2) - Easy Familiar
    (8880150, 2870861, 100, 1, 1), # Lucid | Lucid Familiar
    (9402397, 2870933, 100, 1, 1), # Demon King | Demon King Familiar
    (2130100, 2870036, 100, 1, 1), # Dark Axe Stump | Dark Axe Stump Familiar
    (2130103, 2870037, 100, 1, 1), # Jr. Necki | Jr. Necki Familiar
    (9001001, 2870382, 100, 1, 1), # Grendel the Really Old's Clone | Grendel the Really Old's Clone Familiar
    (9001000, 2870381, 100, 1, 1), # Dances with Balrog's Clone | Dances with Balrog's Clone Familiar
    (9300012, 2870394, 100, 1, 1), # Alishar | Alishar Familiar
    (9001003, 2870384, 100, 1, 1), # Dark Lord's Clone | Dark Lord's Clone Familiar
    (9001002, 2870383, 100, 1, 1), # Athena Pierce's Clone | Athena Pierce's Clone Familiar
    (9001004, 2870385, 100, 1, 1), # Shadow Kyrin | Shadow Kyrin Familiar
    (8110300, 2870310, 100, 1, 1), # Homunscullo | Homunscullo Familiar
    (8644644, 2870981, 100, 1, 1), # Crushing Glare | Crushing Glare Familiar
    (9300004, 2870393, 100, 1, 1), # Mimic | Mimic Familiar
    (7160000, 2870299, 100, 1, 1), # [*]Dual Ghost Pirate | [*]Dual Ghost Pirate Familiar
    (9300003, 2870392, 100, 1, 1), # King Slime | King Slime Familiar
    (9402400, 2870935, 100, 1, 1), # Goblin Archer | Goblin Archer Familiar
    (9300028, 2870404, 100, 1, 1), # Ergoth | Ergoth Familiar
    (8141000, 2870329, 100, 1, 1), # [*]Spirit Viking | [*]Spirit Viking Familiar
    (9300025, 2870401, 100, 1, 1), # Gargoyle | Gargoyle Familiar
    (9300024, 2870400, 100, 1, 1), # Puppet Golem | Puppet Golem Familiar
    (9300027, 2870403, 100, 1, 1), # Devil Slime | Devil Slime Familiar
    (9300026, 2870402, 100, 1, 1), # Jr. Gargoyle | Jr. Gargoyle Familiar
    (9001009, 2870386, 100, 1, 1), # Master of Disguise | Master of Disguise Familiar
    (9300021, 2870397, 100, 1, 1), # Dark Muscle Stone | Dark Muscle Stone Familiar
    (9300020, 2870396, 100, 1, 1), # Muscle Stone | Muscle Stone Familiar
    (9300023, 2870399, 100, 1, 1), # Myst Knight | Myst Knight Familiar
    (9001010, 2870387, 100, 1, 1), # Black Witch | Black Witch Familiar
    (9300022, 2870398, 100, 1, 1), # Black Knight | Black Knight Familiar
    (9001013, 2870389, 100, 1, 1), # Thief Crow | Thief Crow Familiar
    (9001012, 2870388, 100, 1, 1), # Scarred Bear | Scarred Bear Familiar
    (6220000, 2870247, 100, 1, 1), # Dyle | Dyle Familiar
    (9300019, 2870395, 100, 1, 1), # Master Muscle Stone | Master Muscle Stone Familiar
    (6220001, 2870248, 100, 1, 1), # Zeno | Zeno Familiar
    (9001014, 2870880, 100, 1, 1), # Uncontrollable Maha | Uncontrollable Maha Familiar
    (9001037, 2870974, 100, 1, 1), # Mastema | Mastema Familiar
    (9001036, 2870976, 100, 1, 1), # Strange Monster | Mastema Familiar
    (9001038, 2870975, 100, 1, 1), # Mastema | Mastema Familiar
    (9001024, 2870390, 100, 1, 1), # Primitive Thief Boar | Primitive Thief Boar Familiar
    (9300039, 2870406, 100, 1, 1), # Papa Pixie | Papa Pixie Familiar
    (9300038, 2870405, 100, 1, 1), # Ghost Pixie | Ghost Pixie Familiar
    (5200002, 2870216, 100, 1, 1), # Fire Sentinel | Fire Sentinel Familiar
    (5200001, 2870215, 100, 1, 1), # Ice Sentinel | Ice Sentinel Familiar
    (9001031, 2870391, 100, 1, 1), # Schiller | Schiller Familiar
    (5200000, 2870214, 100, 1, 1), # Jr. Sentinel | Jr. Sentinel Familiar
    (9400408, 2871052, 100, 1, 1), # False Daimyo | False Daimyo Familiar
    (9400407, 2871051, 100, 1, 1), # Kaede Toad | Kaede Toad Familiar
    (9400406, 2871050, 100, 1, 1), # Kaede Kunoichi | Kaede Kunoichi Familiar
    (9400405, 2871049, 100, 1, 1), # Kacchuu Musha | Kacchuu Musha Familiar
    (9400404, 2871048, 100, 1, 1), # Kaede Ashigaru | Kaede Ashigaru Familiar
    (9400402, 2871046, 100, 1, 1), # Sakura Jonin | Kaede Jonin Familiar
    (9400401, 2871045, 100, 1, 1), # Sakura Chunin | Kaede Chunin Familiar
    (9400400, 2871044, 100, 1, 1), # Sakura Genin | Kaede Genin Familiar
    (8143000, 2870334, 100, 1, 1), # [*]Grim Phantom Watch | [*]Grim Phantom Watch Familiar
    (2300100, 2870060, 100, 1, 1), # Stirge | Stirge Familiar
    (6230200, 2870251, 100, 1, 1), # Dark Pepe | Dark Pepe Familiar
    (9001058, 2870944, 100, 1, 1), # Inner Rage | Inner Rage Familiar
    (8644719, 2870980, 100, 1, 1), # Blackheart Negative | Blackheart Negative Familiar
    (9300089, 2870407, 100, 1, 1), # Phoenix | Phoenix Familiar
    (8644727, 2870939, 100, 1, 1), # Despairing Wing | Despairing Wing Familiar
    (8644726, 2870938, 100, 1, 1), # Silent Rogue | Silent Rogue Familiar
    (8644725, 2870937, 100, 1, 1), # Silent Scout | Silent Scout Familiar
    (9300090, 2870408, 100, 1, 1), # Frostprey | Freezer Familiar
    (8870000, 2870574, 100, 1, 1), # Hilla | Hilla Familiar
    (8644724, 2870936, 100, 1, 1), # Silent Knight | Silent Knight Familiar
    (8644728, 2870940, 100, 1, 1), # Despairing Blade | Despairing Blade Familiar
    (6300000, 2870259, 100, 1, 1), # Yeti | Yeti Familiar
    (2400550, 2871196, 100, 1, 1), # Pine Nut | Pine Nut Familiar
    (6300003, 2870260, 100, 1, 1), # Punco | Punco Familiar
    (2400551, 2871197, 100, 1, 1), # Sikhye Crock | Sikhye Crock Familiar
    (6300004, 2870261, 100, 1, 1), # Pachu | Pachu Familiar
    (6300005, 2870262, 100, 1, 1), # Zombie Mushmom | Zombie Mushmom Familiar
    (2400556, 2871202, 100, 1, 1), # Sewer Rat | Sewer Rat Familiar
    (2400557, 2871203, 100, 1, 1), # Vicious Sewer Rat | Vicious Sewer Rat Familiar
    (2400558, 2871204, 100, 1, 1), # Handsome Monk | Handsome Monk Familiar
    (2400559, 2871205, 100, 1, 1), # Monster Rat | Monster Rat Familiar
    (2400552, 2871198, 100, 1, 1), # Broken Sikhye Crock | Broken Sikhye Crock Familiar
    (2400553, 2871199, 100, 1, 1), # Jar of Stagnant Water | Jar of Stagnant Water Familiar
    (2400554, 2871200, 100, 1, 1), # Rolling Gourd | Rolling Gourd Familiar
    (9300354, 2870430, 100, 1, 1), # Wolf Underling | Wolf Underling Familiar
    (2400555, 2871201, 100, 1, 1), # Chunky Striped Gourd | Chunky Striped Gourd Familiar
    (8645011, 2870953, 100, 1, 1), # Ascendion | Ascendion Familiar
    (9400735, 2871094, 100, 1, 1), # Starling | Starling Familiar
    (8645010, 2870952, 100, 1, 1), # Ancestion | Ancestion Familiar
    (9400734, 2871093, 100, 1, 1), # Coco | Coco Familiar
    (8645009, 2870950, 100, 1, 1), # Guard Captain Darknell | Guard Captain Darknell Familiar
    (9400733, 2871092, 100, 1, 1), # Kentaurus King | Kentaurus King Familiar
    (9400732, 2871091, 100, 1, 1), # Master Birk | Master Birk Familiar
    (9400731, 2871090, 100, 1, 1), # Master Harp | Master Harp Familiar
    (9400730, 2871089, 100, 1, 1), # Master Hoblin | Master Hoblin Familiar
    (9400729, 2871088, 100, 1, 1), # King Centipede | King Centipede Familiar
    (8645012, 2870951, 100, 1, 1), # Transcendion | Transcendion Familiar
    (9400728, 2871087, 100, 1, 1), # Toy Black Knight | Toy Black Knight Familiar
    (9400727, 2871086, 100, 1, 1), # Queen Pepe | Queen Pepe Familiar
    (3220001, 2870091, 100, 1, 1), # Deo | Deo Familiar
    (9400726, 2871085, 100, 1, 1), # Pixiemom | Pixiemom Familiar
    (2400574, 2870517, 100, 1, 1), # Snowman | Snowman Familiar
    (9400725, 2871084, 100, 1, 1), # Lord Skeleton | Lord Skeleton Familiar
    (2400575, 2870520, 100, 1, 1), # Leviathan | Leviathan Familiar
    (8645022, 2870954, 100, 1, 1), # Embrion | Embrion Familiar
    (2400570, 2870649, 100, 1, 1); # Mole King | Mole King Familiar

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (2400571, 2870488, 100, 1, 1), # Dyle | Dyle Familiar
    (9400750, 2870580, 100, 1, 1), # Pie monster | Pie monster Familiar
    (9400748, 2870512, 100, 1, 1), # MV | ?
    (9300393, 2870437, 100, 1, 1), # Gentleman | Gentleman Familiar
    (9300392, 2870436, 100, 1, 1), # Black Wing Henchman | Black Wing Henchman Familiar
    (9300389, 2870434, 100, 1, 1), # Safe Guard | Safe Guard Familiar
    (9400743, 2870526, 100, 1, 1), # Angry Guard Dog | Angry Guard Dog Familiar
    (9400741, 2871096, 100, 1, 1), # Corrupted Bastille | Corrupted Bastille Familiar
    (9300390, 2870435, 100, 1, 1), # Door Block | Door Block Familiar
    (9300385, 2870431, 100, 1, 1), # Treacherous Fox | Treacherous Fox Familiar
    (9400738, 2871095, 100, 1, 1), # Crow | Crow Familiar
    (9300387, 2870433, 100, 1, 1), # Enraged Golem | Enraged Golem Familiar
    (9300386, 2870432, 100, 1, 1), # Trainee Spore | Trainee Spore Familiar
    (9300414, 2870439, 100, 1, 1), # Suspicious Thief | Suspicious Thief Familiar
    (9300408, 2870438, 100, 1, 1), # Gold Captain | Gold Captain Familiar
    (8643003, 2870696, 100, 1, 1), # Gallina | Gallina Familiar
    (8643001, 2870695, 100, 1, 1), # Paper Bag Alley Citizen | Paper Bag Alley Citizen Familiar
    (8643000, 2870694, 100, 1, 1), # Dreamkeeper | Dreamkeeper Familiar
    (8600003, 2870455, 100, 1, 1), # Mutant Ribbon Pig | Mutant Ribbon Pig Familiar
    (8643011, 2870697, 100, 1, 1), # Red-eyed Gargoyle | Red-eyed Gargoyle Familiar
    (8600002, 2870454, 100, 1, 1), # Mutant Slime | Mutant Slime Familiar
    (8600001, 2870453, 100, 1, 1), # Mutant Orange Mushroom | Mutant Orange Mushroom Familiar
    (6230300, 2870252, 100, 1, 1), # [*]Lazy Buffy | [*]Lazy Buffy Familiar
    (8600000, 2870452, 100, 1, 1), # Mutant Snail | Mutant Snail Familiar
    (2300263, 2870991, 100, 1, 1), # Blazin' Sparkinstone Bugs | Blazin' Sparkinstone Bugs Familiar
    (8600006, 2870458, 100, 1, 1), # Mutant Tiguru | Mutant Tiguru Familiar
    (2300262, 2870990, 100, 1, 1), # Blazin' Log-a-Rhythmic Bugs | Blazin' Log-a-Rhythmic Bugs Familiar
    (8600005, 2870457, 100, 1, 1), # Mutant Tiru | Mutant Tiru Familiar
    (2300261, 2870989, 100, 1, 1), # Flared Chargin' Sprout | Flared Chargin' Sprout Familiar
    (8600004, 2870456, 100, 1, 1), # Mutant Tino | Mutant Tino Familiar
    (9300417, 2870441, 100, 1, 1), # Eleanor's Cockatrice | Eleanor's Cockatrice Familiar
    (2300271, 2870988, 100, 1, 1), # Flared Hoppin' Sprout | Flared Hoppin' Sprout Familiar
    (8145200, 2871192, 100, 1, 1), # Scarboss | Scarboss Familiar
    (9300416, 2870440, 100, 1, 1), # Rowdy Puppet | Rowdy Puppet Familiar
    (9300418, 2870442, 100, 1, 1), # Fabio's Firebomb | Fabio's Firebomb Familiar
    (2300268, 2870992, 100, 1, 1), # Blazin' Brazier | Blazin' Brazier Familiar
    (8141100, 2870330, 100, 1, 1), # [*]Gigantic Spirit Viking | [*]Gigantic Spirit Viking Familiar
    (2600022, 2870345, 100, 1, 1), # Manon | Manon Familiar
    (8180000, 2870345, 100, 1, 1), # Manon | Manon Familiar
    (2600007, 2870346, 100, 1, 1), # Griffey | Griffey Familiar
    (8180001, 2870346, 100, 1, 1), # Griffey | Griffey Familiar
    (9300435, 2870853, 100, 1, 1), # Orchid | Orchid Familiar
    (8641001, 2870672, 100, 1, 1), # Raging Erda | Raging Erda Familiar
    (8641000, 2870664, 100, 1, 1), # Happy Erda | Happy Erda Familiar
    (8641007, 2870689, 100, 1, 1), # Tranquil Erda | Tranquil Erda Familiar
    (8641005, 2870673, 100, 1, 1), # Blazing Erda | Blazing Erda Familiar
    (3400003, 2870120, 100, 1, 1), # Yeti Doll Claw Game | Yeti Doll Claw Game Familiar
    (9300479, 2870507, 100, 1, 1), # Master Hoblin | Master Hoblin Familiar
    (9300478, 2870505, 100, 1, 1), # King Centipede | King Centipede Familiar
    (3400007, 2870122, 100, 1, 1), # Transformed Doll Claw Game | Transformed Doll Claw Game Familiar
    (8645111, 2871206, 100, 1, 1), # Ebonstar Foot Soldier | Ebonstar Foot Soldier Familiar
    (9300472, 2870493, 100, 1, 1), # Pixiemom | Pixiemom Familiar
    (3400005, 2870121, 100, 1, 1), # Jr. Pepe Doll Claw Game | Jr. Pepe Doll Claw Game Familiar
    (9300474, 2870511, 100, 1, 1), # Coco | Coco Familiar
    (8645115, 2871212, 100, 1, 1), # Ancient Spirit | Ancient Spirit Familiar
    (8645114, 2871211, 100, 1, 1), # Spirit of Fire | Spirit of Fire Familiar
    (8645113, 2871210, 100, 1, 1), # Monster Gull | Monster Gull Familiar
    (3400008, 2870123, 100, 1, 1), # Transformed Doll Claw Game | Transformed Doll Claw Game Familiar
    (8645112, 2871207, 100, 1, 1), # Ebonstar Archer | Ebonstar Archer Familiar
    (8645118, 2871214, 100, 1, 1), # Flora Battle Wizard | Flora Battle Wizard Familiar
    (8645116, 2871213, 100, 1, 1), # Flora Foot Soldier | Flora Foot Soldier Familiar
    (9400591, 2870515, 100, 1, 1), # Red Nirg | ?
    (9400590, 2870513, 100, 1, 1), # Margana | ?
    (9400589, 2871074, 100, 1, 1), # Nagrom | Nagrom Familiar
    (8141300, 2870331, 100, 1, 1), # Squid | Squid Familiar
    (9400583, 2871073, 100, 1, 1), # Leprechaun | Leprechaun Familiar
    (9400582, 2871072, 100, 1, 1), # Crimson Guardian | Crimson Guardian Familiar
    (9400581, 2871071, 100, 1, 1), # Stormbreaker | Stormbreaker Familiar
    (9410821, 2880726, 100, 1, 1), # Omega Nenne | Omega Nenne Familiar
    (9400580, 2871070, 100, 1, 1), # Elderwraith | Elderwraith Familiar
    (9400579, 2871069, 100, 1, 1), # Nightshadow | Nightshadow Familiar
    (9400578, 2871068, 100, 1, 1), # Firebrand | Firebrand Familiar
    (9400577, 2871067, 100, 1, 1), # Firebrand | Firebrand Familiar
    (9400576, 2871066, 100, 1, 1), # Windraider | Windraider Familiar
    (9400607, 2870579, 100, 1, 1), # Cake Monster | Cake monster Familiar
    (8880405, 2870943, 100, 1, 1), # Verus Hilla | Verus Hilla Familiar
    (8880404, 2870942, 100, 1, 1), # Necro Damien | Necro Damien Familiar
    (8880403, 2870941, 100, 1, 1), # Necro Lotus | Necro Lotus Familiar
    (9400599, 2871083, 100, 1, 1), # Black Bird | Black Bird Familiar
    (9400597, 2871082, 100, 1, 1), # Azure Ocelot | Azure Ocelot Familiar
    (9400596, 2871081, 100, 1, 1), # Scarlet Phoenix | Scarlet Phoenix Familiar
    (9400595, 2871080, 100, 1, 1), # Blood Stirge | Blood Stirge Familiar
    (9400594, 2871079, 100, 1, 1), # Master Guardian | Master Guardian Familiar
    (9400593, 2870516, 100, 1, 1), # Hsalf | ?
    (9400592, 2871077, 100, 1, 1), # Rellik | Rellik Familiar
    (9300270, 2870420, 100, 1, 1), # Mingu | Mingu Familiar
    (3230101, 2870093, 100, 1, 1), # Jr. Wraith | Jr. Wraith Familiar
    (6300100, 2870263, 100, 1, 1), # [*]Buffoon | [*]Buffoon Familiar
    (3230100, 2870092, 100, 1, 1), # Curse Eye | Curse Eye Familiar
    (3230103, 2870095, 100, 1, 1), # King Bloctopus | King Bloctopus Familiar
    (3230102, 2870094, 100, 1, 1), # Lorang | Lorang Familiar
    (5100005, 2870187, 100, 1, 1), # Hogul | Hogul Familiar
    (9410855, 2880728, 100, 1, 1), # Alpha Lingling | Alpha Lingling Familiar
    (3230104, 2870096, 100, 1, 1), # Mask Fish | Mask Fish Familiar
    (5100004, 2870186, 100, 1, 1), # Samiho | Samiho Familiar
    (5100000, 2870184, 100, 1, 1), # Jr. Yeti | Jr. Yeti Familiar
    (5100003, 2870185, 100, 1, 1), # Hodori | Hodori Familiar
    (5120506, 2870202, 100, 1, 1), # The Book Ghost | The Book Ghost Familiar
    (9300281, 2870427, 100, 1, 1), # Rex | Rex Familiar
    (9300280, 2870426, 100, 1, 1), # Ferocious Hoblin | Ferocious Hoblin Familiar
    (5120504, 2870200, 100, 1, 1), # Wooden Target Dummy | Wooden Target Dummy Familiar
    (5120505, 2870201, 100, 1, 1), # Cute Reindeer | Cute Reindeer Familiar
    (9300282, 2870428, 100, 1, 1), # Shammos | Shammos Familiar
    (5120502, 2870198, 100, 1, 1), # Sr. Bellflower Root | Sr. Bellflower Root Familiar
    (9300277, 2870423, 100, 1, 1), # Elite Hoblin | Elite Hoblin Familiar
    (5120503, 2870199, 100, 1, 1), # Straw Target Dummy | Straw Target Dummy Familiar
    (9300276, 2870422, 100, 1, 1), # Hoblin Hector | Hoblin Hector Familiar
    (5120500, 2870196, 100, 1, 1), # Grizzly | Grizzly Familiar
    (9300279, 2870425, 100, 1, 1), # Combat Hoblin | Combat Hoblin Familiar
    (5120501, 2870197, 100, 1, 1), # Bellflower Root | Bellflower Root Familiar
    (6230500, 2870255, 100, 1, 1), # [*]Master Soul Teddy | [*]Master Soul Teddy Familiar
    (9300278, 2870424, 100, 1, 1), # Elite Green Hoblin | Elite Green Hoblin Familiar
    (9300274, 2870421, 100, 1, 1), # Cynical Orange Mushroom | Cynical Orange Mushroom Familiar
    (8870200, 2870879, 100, 1, 1), # Hilla (Gray Maiden) | Hilla (Gray Maiden) Familiar
    (9300300, 2870497, 100, 1, 1), # Toy Black Knight | Toy Black Knight Familiar
    (3230200, 2870097, 100, 1, 1), # Star Pixie | Star Pixie Familiar
    (9300295, 2870429, 100, 1, 1), # Ifia | Ifia Familiar
    (4250000, 2870169, 100, 1, 1), # Mossy Snail | Mossy Snail Familiar
    (4250001, 2870170, 100, 1, 1), # Tree Rod | Tree Rod Familiar
    (7150004, 2870298, 100, 1, 1), # Guard Robot L | Guard Robot L Familiar
    (7150001, 2870295, 100, 1, 1), # Big Spider | Big Spider Familiar
    (9400659, 2880564, 100, 1, 1), # Dunas Type D | ?
    (7150000, 2870294, 100, 1, 1), # Racoco | Racoco Familiar
    (6230401, 2870254, 100, 1, 1), # Jr. Lucida | Jr. Lucida Familiar
    (7150003, 2870297, 100, 1, 1), # Racaroni | Racaroni Familiar
    (6230400, 2870253, 100, 1, 1), # [*]Soul Teddy | [*]Soul Teddy Familiar
    (7150002, 2870296, 100, 1, 1), # Cart Bear | Cart Bear Familiar
    (6130104, 2870238, 100, 1, 1), # Boogie | Boogie Familiar
    (6130101, 2870236, 100, 1, 1), # Mushmom | Mushmom Familiar
    (6130100, 2870235, 100, 1, 1), # Red Drake | Red Drake Familiar
    (6130102, 2870237, 100, 1, 1), # Separated Pepe | Separated Pepe Familiar
    (8090000, 2870303, 100, 1, 1), # Deet and Roi | Deet and Roi Familiar
    (150002, 2870007, 100, 1, 1), # Grape Juice Bottle | Grape Juice Bottle Familiar
    (150000, 2870005, 100, 1, 1), # Potted Sprout | Potted Sprout Familiar
    (9300351, 2870604, 100, 1, 1), # Shadow Knight | Shadow Knight Familiar
    (150001, 2870006, 100, 1, 1), # Potted Morning Glory | Potted Morning Glory Familiar
    (3220001, 2870091, 100, 1, 1), # Deo | Deo Familiar
    (9500338, 2870090, 100, 1, 1), # Stumpy | Stumpy Familiar
    (6230602, 2870258, 100, 1, 1), # Skeleton Officer | Skeleton Officer Familiar
    (8510100, 2870378, 100, 1, 1), # Bloody Boom | Bloody Boom Familiar
    (6230601, 2870257, 100, 1, 1), # Dark Drake | Dark Drake Familiar
    (6230600, 2870256, 100, 1, 1), # Ice Drake | Ice Drake Familiar
    (9401013, 2870585, 100, 1, 1), # The Fatal Scythe | The Fatal Scythe Familiar
    (9401012, 2870584, 100, 1, 1), # The Skull Reaper | The Skull Reaper Familiar
    (9401011, 2870583, 100, 1, 1), # The Gleam Eyes | The Gleam Eyes Familiar
    (9300685, 2870848, 100, 1, 1), # Lil Von Leon | Lil Von Leon Familiar
    (6130204, 2870242, 100, 1, 1), # Mr. Alli | Mr. Alli Familiar
    (9300684, 2870844, 100, 1, 1), # Lil Magnus | Lil Magnus Familiar
    (6130207, 2870243, 100, 1, 1), # Peach Monkey | Peach Monkey Familiar
    (9300687, 2870874, 100, 1, 1), # Lil Cygnus | Lil Cygnus Familiar
    (9300686, 2870846, 100, 1, 1), # Lil Arkarium | Lil Arkarium Familiar
    (6130200, 2870239, 100, 1, 1), # [*]Buffy | [*]Buffy Familiar
    (9421514, 2870531, 100, 1, 1), # Oda Warrior | Oda Warrior Familiar
    (6130203, 2870241, 100, 1, 1), # Panda | Panda Familiar
    (9421513, 2870538, 100, 1, 1), # Oda Guardsman | Oda Guardsman Familiar
    (6130202, 2870240, 100, 1, 1), # Morphed Blin | Morphed Blin Familiar
    (9421512, 2870532, 100, 1, 1), # Oda Advanced Scout | Oda Advanced Scout Familiar
    (9421511, 2870537, 100, 1, 1), # Oda Scout | Oda Scout Familiar
    (9421510, 2870533, 100, 1, 1), # Conjureye | Conjureye Familiar
    (9421509, 2870536, 100, 1, 1), # Onieye | Onieye Familiar
    (9421508, 2870535, 100, 1, 1), # Floateye | Floateye Familiar
    (3230305, 2870102, 100, 1, 1), # Toy Trojan | Toy Trojan Familiar
    (5110301, 2870189, 100, 1, 1), # Roid | Roid Familiar
    (9390815, 2870712, 100, 1, 1), # Blood Pirate Swabby | Blood Pirate Swabby Familiar
    (3230304, 2870101, 100, 1, 1), # Planey | Planey Familiar
    (5110300, 2870188, 100, 1, 1), # Reinforced Mithril Mutae | Reinforced Mithril Mutae Familiar
    (3230307, 2870104, 100, 1, 1), # Chirppy | Chirppy Familiar
    (9390813, 2870594, 100, 1, 1), # Dirty Lupo | Dirty Lupo Familiar
    (3230306, 2870103, 100, 1, 1), # Chronos | Chronos Familiar
    (5110302, 2870190, 100, 1, 1), # Neo Huroid | Neo Huroid Familiar
    (9390812, 2870599, 100, 1, 1), # Riverson | Riverson Familiar
    (9390811, 2870591, 100, 1, 1), # Ripclaw Delfino | Ripclaw Delfino Familiar
    (3230308, 2870105, 100, 1, 1), # Tweeter | Tweeter Familiar
    (9390810, 2870590, 100, 1, 1), # Voodoo Delfino | Voodoo Delfino Familiar
    (9390809, 2870589, 100, 1, 1), # Sureshot Delfino | Sureshot Delfino Familiar
    (9390808, 2870588, 100, 1, 1), # Cutthroat Delfino | Cutthroat Delfino Familiar
    (9390807, 2870587, 100, 1, 1), # Gray Tomcat | Gray Tomcat Familiar
    (9390806, 2870586, 100, 1, 1), # Calico Timcat | Calico Timcat Familiar
    (9300689, 2870875, 100, 1, 1), # Lil Zakum | Lil Zakum Familiar
    (9300688, 2870847, 100, 1, 1), # Lil Orchid | Lil Orchid Familiar
    (9300691, 2870845, 100, 1, 1), # Lil Hilla | Lil Hilla Familiar
    (9390800, 2870592, 100, 1, 1), # Captain Blood Pirate | Captain Blood Pirate Familiar
    (9300690, 2870876, 100, 1, 1), # Lil Horntail | Lil Horntail Familiar
    (9390822, 2870600, 100, 1, 1), # Il Capo | Il Capo Familiar
    (9300711, 2870968, 100, 1, 1), # Ephenia | Ephenia Familiar
    (9390820, 2870598, 100, 1, 1), # Nightpriest Assassin | Nightpriest Assassin Familiar
    (8170000, 2870344, 100, 1, 1), # [*]Thanatos | [*]Thanatos Familiar
    (9390819, 2870597, 100, 1, 1), # Nightwhip Assassin | Nightwhip Assassin Familiar
    (3230300, 2870098, 100, 1, 1), # Jr. Boogie | Jr. Boogie Familiar
    (9390818, 2870596, 100, 1, 1), # Nightcutter Assassin | Nightcutter Assassin Familiar
    (3230303, 2870100, 100, 1, 1), # Propelly | Propelly Familiar
    (9390817, 2870601, 100, 1, 1), # Captain Blood the Pirate King | Captain Blood the Pirate King Familiar
    (3230302, 2870099, 100, 1, 1), # Bloctopus | Bloctopus Familiar
    (9390816, 2870595, 100, 1, 1), # Blood Pirate Lookout | Blood Pirate Lookout Familiar
    (9421565, 2870534, 100, 1, 1), # Oda Spirit Walker | Oda Spirit Walker Familiar
    (9421563, 2870530, 100, 1, 1), # Oda Samurai Captain | Oda Samurai Captain Familiar
    (9421562, 2870527, 100, 1, 1), # Takigawa Kazumasu | Takigawa Kazumasu Familiar
    (6130209, 2870245, 100, 1, 1), # Sage Cat | Sage Cat Familiar
    (6130208, 2870244, 100, 1, 1), # Kru | Kru Familiar
    (8645122, 2871217, 100, 1, 1), # Seren | Seren Familiar
    (8645121, 2871216, 100, 1, 1), # Templar | Templar Familiar
    (8645120, 2871215, 100, 1, 1), # Court Knight | Court Knight Familiar
    (9450004, 2870764, 100, 1, 1), # Mikagami | Mikagami Familiar
    (9450005, 2870766, 100, 1, 1), # Oda Swordsman | Oda Swordsman Familiar
    (9450006, 2870782, 100, 1, 1), # Oda Swordsman | Oda Swordsman Familiar
    (9450007, 2870783, 100, 1, 1), # Oda Swordsman | Oda Swordsman Familiar
    (9450008, 2870784, 100, 1, 1), # Oda Swordsman | Oda Swordsman Familiar
    (9450009, 2870785, 100, 1, 1), # Oda Swordsman | Oda Swordsman Familiar
    (9450010, 2870763, 100, 1, 1), # Renka | Renka Familiar
    (9300481, 2870509, 100, 1, 1), # Master Birk | Master Birk Familiar
    (9300480, 2870508, 100, 1, 1), # Master Harp | Master Harp Familiar
    (9450013, 2870762, 100, 1, 1), # Oura Shogun | Oura Shogun Familiar
    (9300483, 2870494, 100, 1, 1), # Queen Pepe | Queen Pepe Familiar
    (9450014, 2870777, 100, 1, 1), # Oura Shogun | Oura Shogun Familiar
    (3300002, 2870110, 100, 1, 1), # Intoxicated Pig | Intoxicated Pig Familiar
    (3300003, 2870111, 100, 1, 1), # Helmet Pepe | Helmet Pepe Familiar
    (3300000, 2870108, 100, 1, 1), # Renegade Spores | Renegade Spores Familiar
    (3300001, 2870109, 100, 1, 1), # Poison Mushroom | Poison Mushroom Familiar
    (9390620, 2870619, 100, 1, 1), # Corrupted Veilstone Alpha | Corrupted Veilstone Alpha Familiar
    (3300006, 2870114, 100, 1, 1), # Gold Yeti and King Pepe | Gold Yeti and King Pepe Familiar
    (3300007, 2870115, 100, 1, 1), # White Yeti and King Pepe | White Yeti and King Pepe Familiar
    (3300004, 2870112, 100, 1, 1), # Royal Guard Pepe | Royal Guard Pepe Familiar
    (3300005, 2870113, 100, 1, 1), # Grey Yeti and King Pepe | Grey Yeti and King Pepe Familiar
    (3300008, 2870116, 100, 1, 1), # Prime Minister | Prime Minister Familiar
    (5090001, 2870183, 100, 1, 1), # Master Dummy | Master Dummy Familiar
    (5090000, 2870182, 100, 1, 1), # Shade | Shade Familiar
    (9390635, 2870627, 100, 1, 1), # Wardlock | Wardlock Familiar
    (9390634, 2870626, 100, 1, 1), # Mutated Wardlock | Mutated Wardlock Familiar
    (9390633, 2870625, 100, 1, 1), # Corrupted Wardlock | Corrupted Wardlock Familiar
    (9833008, 2870687, 100, 1, 1), # Devil Mukuru | Devil Mukuru Familiar
    (9390624, 2870623, 100, 1, 1), # Conjured Veilprey | Conjured Veilprey Familiar
    (9450047, 2870787, 100, 1, 1), # Oda Spirit Walker | Oda Spirit Walker Familiar
    (9450020, 2870765, 100, 1, 1), # Miroku | Miroku Familiar
    (9450021, 2870786, 100, 1, 1), # Oda Spirit Walker | Oda Spirit Walker Familiar
    (9450022, 2870761, 100, 1, 1), # Princess No | Princess No Familiar
    (9300534, 2870862, 100, 1, 1), # Lania | Lania Familiar
    (4240000, 2870168, 100, 1, 1), # Chief Gray | Chief Gray Familiar
    (7140000, 2870293, 100, 1, 1), # [*]Ghost Pirate | [*]Ghost Pirate Familiar
    (8850007, 2870481, 100, 1, 1), # Irena | Irena Familiar
    (9450048, 2870788, 100, 1, 1), # Oda Spirit Walker | Oda Spirit Walker Familiar
    (8850006, 2870480, 100, 1, 1), # Oz | Oz Familiar
    (9450049, 2870789, 100, 1, 1), # Oda Spirit Walker | Oda Spirit Walker Familiar
    (8250028, 2880688, 100, 1, 1), # Salvoroid Blue | Salvoroid Blue Familiar
    (8850005, 2870479, 100, 1, 1), # Mihile | Mihile Familiar
    (9450050, 2870790, 100, 1, 1), # Oda Spirit Walker | Oda Spirit Walker Familiar
    (2700029, 2870571, 100, 1, 1), # Magnus | Magnus Familiar
    (8250024, 2880686, 100, 1, 1), # Scrap Xenoroid EX | Scrap Xenoroid EX Familiar
    (8250026, 2880687, 100, 1, 1), # Modded Megaroid | Modded Megaroid Familiar
    (2110200, 2870034, 100, 1, 1), # Horny Mushroom | Horny Mushroom Familiar
    (8250020, 2880684, 100, 1, 1), # Alloy Xenoroid EX | Alloy Xenoroid EX Familiar
    (8250022, 2880685, 100, 1, 1), # Steel Xenoroid EX | Steel Xenoroid EX Familiar
    (8850011, 2870485, 100, 1, 1), # Cygnus | Cygnus Familiar
    (8850010, 2870484, 100, 1, 1), # Shinsoo | Shinsoo Familiar
    (8250016, 2880682, 100, 1, 1), # Steel Xenoroid DX | Steel Xenoroid DX Familiar
    (8850009, 2870483, 100, 1, 1), # Hawkeye | Hawkeye Familiar
    (8850008, 2870482, 100, 1, 1), # Eckhart | Eckhart Familiar
    (8250018, 2880683, 100, 1, 1), # Scrap Xenoroid DX | Scrap Xenoroid DX Familiar
    (8250014, 2880681, 100, 1, 1), # Alloy Xenoroid DX | Alloy Xenoroid DX Familiar
    (8250009, 2880676, 100, 1, 1), # Salvoroid Red | Salvoroid Red Familiar
    (8250008, 2880675, 100, 1, 1), # Modded Buffroid | Modded Buffroid Familiar
    (8250011, 2880678, 100, 1, 1), # Inner Guard EX | Inner Guard EX Familiar
    (8250010, 2880677, 100, 1, 1), # Outer Guard EX | Outer Guard EX Familiar
    (8250005, 2880673, 100, 1, 1), # Hunterizer Red | Hunterizer Red Familiar
    (8250004, 2880672, 100, 1, 1), # Chaseroid Blue | Chaseroid Blue Familiar
    (8250006, 2880674, 100, 1, 1), # Hunterizer Blue | Hunterizer Blue Familiar
    (8250001, 2880669, 100, 1, 1), # Modded Broken Android | Modded Broken Android Familiar
    (8250000, 2880668, 100, 1, 1), # Modded Scaredroid | Modded Scaredroid Familiar
    (8250003, 2880671, 100, 1, 1), # Chaseroid Red | Chaseroid Red Familiar
    (8250002, 2880670, 100, 1, 1), # Modded Laseroid | Modded Laseroid Familiar
    (3230400, 2870106, 100, 1, 1), # Drumming Bunny | Drumming Bunny Familiar
    (3230405, 2870107, 100, 1, 1), # Jr. Seal | Jr. Seal Familiar
    (9833382, 2870993, 100, 1, 1), # Medusa | Medusa Familiar
    (9833380, 2870994, 100, 1, 1), # Stheno | Stheno Familiar
    (9833381, 2870995, 100, 1, 1), # Euryale | Euryale Familiar
    (3300102, 2880691, 100, 1, 1), # Exhausted Viking | Exhausted Viking Familiar
    (3300103, 2880692, 100, 1, 1), # Tired Viking | Tired Viking Familiar
    (3300100, 2880689, 100, 1, 1), # Mushroom Chandelier | Mushroom Chandelier Familiar
    (3300101, 2880690, 100, 1, 1), # Mushroom Knight Armor | Mushroom Knight Armor Familiar
    (3300106, 2880695, 100, 1, 1), # Solemn Viking | Solemn Viking Familiar
    (3300107, 2880696, 100, 1, 1), # Serious Viking | Serious Viking Familiar
    (3300104, 2880693, 100, 1, 1), # Warm Viking | Warm Viking Familiar
    (3300105, 2880694, 100, 1, 1), # Generous Viking | Generous Viking Familiar
    (3300110, 2880699, 100, 1, 1), # Black Viking | Black Viking Familiar
    (3300108, 2880697, 100, 1, 1), # Master Squid | Master Squid Familiar
    (3300109, 2880698, 100, 1, 1), # Viking Squad | Viking Squad Familiar
    (3210100, 2870078, 100, 1, 1), # Fire Boar | Fire Boar Familiar
    (7220002, 2870302, 100, 1, 1), # King Sage Cat | King Sage Cat Familiar
    (7220000, 2870300, 100, 1, 1), # Tae Roon | Tae Roon Familiar
    (7220001, 2870301, 100, 1, 1), # Nine-Tailed Fox | Nine-Tailed Fox Familiar
    (2700111, 2871180, 100, 1, 1), # Greedy Troublemaker | Greedy Troublemaker Familiar
    (2700113, 2871181, 100, 1, 1), # Born Single Troublemaker | Born Single Troublemaker Familiar
    (2700115, 2871182, 100, 1, 1), # Squid Troublemaker | Squid Troublemaker Familiar
    (8160000, 2870343, 100, 1, 1), # [*]Gatekeeper | [*]Gatekeeper Familiar
    (2110301, 2870035, 100, 1, 1), # Scorpion | Scorpion Familiar
    (2700116, 2871183, 100, 1, 1), # Proud Troublemaker | Proud Troublemaker Familiar
    (5250000, 2870221, 100, 1, 1), # Mossy Mushroom | Mossy Mushroom Familiar
    (5250001, 2870222, 100, 1, 1), # Stone Bug | Stone Bug Familiar
    (5250002, 2870223, 100, 1, 1), # Primitive Boar | Primitive Boar Familiar
    (5250005, 2870965, 100, 1, 1), # Ancient Fairy | Ancient Fairy Familiar
    (9300747, 2870606, 100, 1, 1), # Kyson | Kyson Familiar
    (5250006, 2870964, 100, 1, 1), # Shining Fairy | Shining Fairy Familiar
    (8150000, 2870335, 100, 1, 1), # Crimson Balrog | Crimson Balrog Familiar
    (9300765, 2870970, 100, 1, 1), # Shadow Knight | Shadow Familiar
    (9300764, 2870608, 100, 1, 1), # Lyra | Lyra Familiar
    (4230100, 2870131, 100, 1, 1), # Cold Eye | Cold Eye Familiar
    (7130104, 2870283, 100, 1, 1), # Captain | Captain Familiar
    (3503009, 2870684, 100, 1, 1), # Dark Demon Wolfmaster | Dark Demon Wolfmaster Familiar
    (4230101, 2870132, 100, 1, 1), # Zombie Lupin | Zombie Lupin Familiar
    (4230102, 2870133, 100, 1, 1), # Wraith | Wraith Familiar
    (4230103, 2870134, 100, 1, 1), # Iron Hog | Iron Hog Familiar
    (8240106, 2880667, 100, 1, 1), # Gelimer | Gelimer Familiar
    (4230104, 2870135, 100, 1, 1), # Clang | Clang Familiar
    (7130100, 2870279, 100, 1, 1), # Tauromacis | Tauromacis Familiar
    (8240101, 2880659, 100, 1, 1), # Self-destructive Blue | Self-destructive Blue Familiar
    (7130101, 2870280, 100, 1, 1), # Taurospear | Taurospear Familiar
    (8240100, 2880658, 100, 1, 1), # Self-destructive Red | Self-destructive Red Familiar
    (4230106, 2870136, 100, 1, 1), # Lunar Pixie | Lunar Pixie Familiar
    (7130102, 2870281, 100, 1, 1), # Yeti and Pepe | Yeti and Pepe Familiar
    (9300759, 2870603, 100, 1, 1), # Lazuli | Lazuli Familiar
    (4230107, 2870137, 100, 1, 1), # Flyeye | Flyeye Familiar
    (7130103, 2870282, 100, 1, 1), # Skeleton Commander | Skeleton Commander Familiar
    (9300758, 2870602, 100, 1, 1), # Lapis | Lapis Familiar
    (8240102, 2880660, 100, 1, 1), # Self-destructive Yellow | Self-destructive Yellow Familiar
    (4230108, 2870138, 100, 1, 1), # [*]Jr. Cerebes | [*]Jr. Cerebes Familiar
    (4230109, 2870139, 100, 1, 1), # Block Golem | Block Golem Familiar
    (4230110, 2870140, 100, 1, 1), # King Block Golem | King Block Golem Familiar
    (4230111, 2870141, 100, 1, 1), # Robo | Robo Familiar
    (8240098, 2880665, 100, 1, 1), # Lotus | Lotus Familiar
    (4230112, 2870142, 100, 1, 1), # Master Robo | Master Robo Familiar
    (8500001, 2870376, 100, 1, 1), # Papulatus Clock | Papulatus Clock Familiar
    (4230113, 2870143, 100, 1, 1), # Tick-Tock | Tick-Tock Familiar
    (4230114, 2870144, 100, 1, 1), # Platoon Chronos | Platoon Chronos Familiar
    (4230115, 2870145, 100, 1, 1), # Master Chronos | Master Chronos Familiar
    (8500002, 2870377, 100, 1, 1), # Papulatus | Papulatus Familiar
    (2100100, 2870025, 100, 1, 1), # White Hare | White Hare Familiar
    (4230116, 2870146, 100, 1, 1), # Barnard Gray | Barnard Gray Familiar
    (2100101, 2870026, 100, 1, 1), # Brown Hare | Brown Hare Card
    (4230117, 2870147, 100, 1, 1), # Zeta Gray | Zeta Gray Familiar
    (8240088, 2880661, 100, 1, 1), # Scaredroid | Scaredroid Familiar
    (2100102, 2870027, 100, 1, 1), # Jr. Cactus | Jr. Cactus Familiar
    (4230118, 2870148, 100, 1, 1), # Ultra Gray | Ultra Gray Familiar
    (2100103, 2870028, 100, 1, 1), # Cactus | Cactus Familiar
    (4230119, 2870149, 100, 1, 1), # Mateon | Mateon Familiar
    (2100104, 2870029, 100, 1, 1), # Royal Cactus | Royal Cactus Familiar
    (4230120, 2870150, 100, 1, 1), # Plateon | Plateon Familiar
    (8240085, 2880663, 100, 1, 1), # Black Wings Security | Black Wings Security Familiar
    (2100105, 2870030, 100, 1, 1), # Bellamoa | Bellamoa Familiar
    (4230121, 2870151, 100, 1, 1), # Mecateon | Mecateon Familiar
    (3503004, 2870680, 100, 1, 1), # Dark Demon Axeman | Dark Demon Axeman Familiar
    (8240084, 2880680, 100, 1, 1), # Repairoid | Repairoid Familiar
    (2100106, 2870031, 100, 1, 1), # Ear Plug Plead | Ear Plug Plead Familiar
    (3503007, 2870683, 100, 1, 1), # Dark Demon Shieldmaster | Dark Demon Shieldmaster Familiar
    (8240087, 2880662, 100, 1, 1), # Buffroid | Buffroid Familiar
    (2100107, 2870032, 100, 1, 1), # Scarf Plead | Scarf Plead Familiar
    (4230123, 2870152, 100, 1, 1), # Sparker | Sparker Familiar
    (3503006, 2870681, 100, 1, 1), # Dark Demon Shieldbearer | Dark Demon Shieldbearer Familiar
    (2100108, 2870033, 100, 1, 1), # Meerkat | Meerkat Familiar
    (4230124, 2870153, 100, 1, 1), # Freezer | Freezer Familiar
    (4230125, 2870154, 100, 1, 1), # Skeledog | Skeledog Familiar
    (9300768, 2870609, 100, 1, 1), # Milo | Milo Familiar
    (4230126, 2870155, 100, 1, 1), # Mummydog | Mummydog Familiar
    (3503003, 2870682, 100, 1, 1), # Dark Demon Swordmaster | Dark Demon Swordmaster Familiar
    (4230139, 2870699, 100, 1, 1), # OMNI-CLN | OMNI-CLN Familiar
    (8840004, 2870477, 100, 1, 1), # Prison Guard Rhino | Prison Guard Rhino Familiar
    (8840005, 2870478, 100, 1, 1), # Mini Castle Golem | Mini Castle Golem Familiar
    (8840002, 2870475, 100, 1, 1), # Red Crocky | Red Crocky Familiar
    (8240057, 2880657, 100, 1, 1), # Laseroid JEK | Laseroid JEK Familiar
    (8840003, 2870476, 100, 1, 1), # Prison Guard Boar | Prison Guard Boar Familiar
    (8840001, 2870474, 100, 1, 1), # Demon Gargoyle | Demon Gargoyle Familiar
    (7130020, 2870278, 100, 1, 1), # Goby | Goby Familiar
    (8240048, 2880654, 100, 1, 1), # Dargoth | Dargoth Familiar
    (3210208, 2870087, 100, 1, 1), # Retz | Retz Familiar
    (9300823, 2870883, 100, 1, 1), # Master Specter | Master Specter Familiar
    (8240031, 2880653, 100, 1, 1), # Scrounging Sparrow | Scrounging Sparrow Familiar
    (3501009, 2870650, 100, 1, 1), # Mystic Wisp | Mystic Wisp Familiar
    (8240025, 2880652, 100, 1, 1), # Yellow Pop Jelly | Yellow Pop Jelly Familiar
    (8240024, 2880651, 100, 1, 1), # Blue Pop Jelly | Blue Pop Jelly Familiar
    (3210200, 2870079, 100, 1, 1), # Jr. Cellion | Jr. Cellion Familiar
    (3210201, 2870080, 100, 1, 1), # Jr. Lioner | Jr. Lioner Familiar
    (3210202, 2870081, 100, 1, 1), # Jr. Grupin | Jr. Grupin Familiar
    (8240023, 2880650, 100, 1, 1), # Red Pop Jelly | Red Pop Jelly Familiar
    (3210203, 2870082, 100, 1, 1), # Panda Teddy | Panda Teddy Familiar
    (3210204, 2870083, 100, 1, 1), # Roloduck | Roloduck Familiar
    (8240017, 2880649, 100, 1, 1), # Flying Master of Disguise | Flying Master of Disguise Familiar
    (3210205, 2870084, 100, 1, 1), # Black Ratz | Black Ratz Familiar
    (3210206, 2870085, 100, 1, 1), # Helly | Helly Familiar
    (3210207, 2870086, 100, 1, 1), # Tick | Tick Familiar
    (8240012, 2871033, 100, 1, 1), # Bomber Android B | Bomber Android B Familiar
    (8240015, 2871036, 100, 1, 1), # Laseroid A | Laseroid A Familiar
    (7130010, 2870277, 100, 1, 1), # [*]Death Teddy | [*]Death Teddy Familiar
    (7130004, 2870276, 100, 1, 1), # Hankie | Hankie Familiar
    (3501005, 2870646, 100, 1, 1), # Raging Tome | Raging Tome Familiar
    (3501004, 2870645, 100, 1, 1), # Grumpy Tome | Grumpy Tome Familiar
    (8240004, 2880664, 100, 1, 1), # Pulverizer | ?
    (3501007, 2870648, 100, 1, 1), # Turnipy | Turnipy Familiar
    (3501006, 2870647, 100, 1, 1), # Oniony | Oniony Familiar
    (7130000, 2870272, 100, 1, 1), # Lucida | Lucida Familiar
    (3501001, 2870642, 100, 1, 1), # Fairy Slime | Fairy Slime Familiar
    (7130001, 2870273, 100, 1, 1), # [*]Cerebes | [*]Cerebes Familiar
    (3501000, 2870641, 100, 1, 1), # Firefly Slime | Firefly Slime Familiar
    (7130002, 2870274, 100, 1, 1), # Beetle | Beetle Familiar
    (3501003, 2870644, 100, 1, 1), # Forest Sprite | Forest Sprite Familiar
    (7130003, 2870275, 100, 1, 1), # Dual Beetle | Dual Beetle Familiar
    (3501002, 2870643, 100, 1, 1), # Water Sprite | Water Sprite Familiar
    (6110301, 2870234, 100, 1, 1), # Saitie | Saitie Familiar
    (3501109, 2870987, 100, 1, 1), # Deep Spring Sprite | Deep Spring Sprite Familiar
    (6110300, 2870233, 100, 1, 1), # Homun | Homun Familiar
    (3501108, 2870986, 100, 1, 1), # Blue Spring Sprite | Blue Spring Sprite Familiar
    (3501104, 2870983, 100, 1, 1), # Dust Bundle | Dust Bundle Familiar
    (3501107, 2870985, 100, 1, 1), # Night Glimmerfox | Night Glimmerfox Familiar
    (3501106, 2870984, 100, 1, 1), # Forest Glimmerfox | Forest Glimmerfox Familiar
    (5160004, 2870671, 100, 1, 1), # Ferret | Ferret Familiar
    (5160002, 2870669, 100, 1, 1), # Scorpie | Scorpie Familiar
    (5160003, 2870670, 100, 1, 1), # Golden Scorpie | Golden Scorpie Familiar
    (5160000, 2870667, 100, 1, 1), # Rabbit | Rabbit Familiar
    (5160001, 2870668, 100, 1, 1), # Golden Rabbit | Golden Rabbit Familiar
    (1150001, 2870017, 100, 1, 1), # Strange Sign | Strange Sign Familiar
    (4220000, 2870130, 100, 1, 1), # Seruf | Seruf Familiar
    (1150000, 2870016, 100, 1, 1), # Patrol Robot | Patrol Robot Familiar
    (1150002, 2870018, 100, 1, 1), # Serpent | Serpent Familiar
    (9833653, 2871208, 100, 1, 1), # Ebonstar Bombardier | Ebonstar Bombardier Familiar
    (8150100, 2870336, 100, 1, 1), # Shark | Shark Familiar
    (8150101, 2870337, 100, 1, 1), # Cold Shark | Cold Shark Familiar
    (9833660, 2871209, 100, 1, 1), # Ebonstar Wizard | Ebonstar Wizard Familiar
    (2700313, 2870948, 100, 1, 1), # Omen | Omen Familiar
    (2700314, 2870949, 100, 1, 1), # Master Omen | Master Omen Familiar
    (130101, 2870004, 100, 1, 1), # Red Snail | Red Snail Familiar
    (7130200, 2870284, 100, 1, 1), # Werewolf | Werewolf Familiar
    (130100, 2870003, 100, 1, 1), # Stump | Stump Familiar
    (8910000, 2870577, 100, 1, 1), # Chaos Von Bon | Chaos Von Bon Familiar
    (2700307, 2871178, 100, 1, 1), # Forever Alone Troublemaker | Forever Alone Troublemaker Familiar
    (2700306, 2871177, 100, 1, 1), # Fistfight Troublemaker | Fistfight Troublemaker Familiar
    (2700308, 2871179, 100, 1, 1), # Two-faced Troublemaker | Two-faced Troublemaker Familiar
    (8105003, 2870307, 100, 1, 1), # AF Android | AF Android Familiar
    (8105002, 2870306, 100, 1, 1), # Enhanced Security System | Enhanced Security System Familiar
    (8105001, 2870305, 100, 1, 1), # Security System | Security System Familiar
    (8105000, 2870304, 100, 1, 1), # Raco | Raco Familiar
    (8105005, 2870309, 100, 1, 1), # Ore Muncher | Ore Muncher Familiar
    (8105004, 2870308, 100, 1, 1), # Broken DF Android | Broken DF Android Familiar
    (8240164, 2870679, 100, 1, 1), # Demon Soldier | Demon Soldier Familiar
    (8240162, 2870685, 100, 1, 1), # Ruins Scorpion | Ruins Scorpion Familiar
    (9420015, 2871108, 100, 1, 1), # NooNoo | NooNoo Familiar
    (2600015, 2870510, 100, 1, 1), # Kentaurus King | Kentaurus King Familiar
    (9420005, 2871107, 100, 1, 1), # White Rooster | White Rooster Familiar
    (9420004, 2871106, 100, 1, 1), # Yellow Lizard | Yellow Lizard Familiar
    (9420003, 2871105, 100, 1, 1), # Red Lizard | Red Lizard Familiar
    (9420002, 2871104, 100, 1, 1), # Python | Python Familiar
    (9420001, 2871103, 100, 1, 1), # Frog | Frog Familiar
    (9420000, 2871102, 100, 1, 1), # Toad | Toad Familiar
    (4230200, 2870156, 100, 1, 1), # Poopa | Poopa Familiar
    (8240133, 2870686, 100, 1, 1), # Devil Murukun | Devil Murukun Familiar
    (9420023, 2871114, 100, 1, 1), # Unidentified Alien | Spirit Visitor Familiar
    (4230201, 2870157, 100, 1, 1), # Poison Poopa | Poison Poopa Familiar
    (9420022, 2871113, 100, 1, 1), # Unidentified Alien | Assassin Visitor Familiar
    (8240132, 2880736, 100, 1, 1), # Devil Murukun | Devil Murukun Familiar
    (9420021, 2871112, 100, 1, 1), # Unidentified Alien | Gunner Visitor Familiar
    (9420020, 2871111, 100, 1, 1), # Unidentified Alien | Sniper Visitor Familiar
    (8240134, 2880729, 100, 1, 1), # Demon Soldier | Demon Soldier Familiar
    (9420019, 2871110, 100, 1, 1), # Unidentified Alien | Cleric Visitor Familiar
    (9420018, 2871109, 100, 1, 1), # Unidentified Alien | Axe Visitor Familiar
    (7120109, 2880558, 100, 1, 1), # Iruvata | ?
    (9600017, 2880625, 100, 1, 1), # Censer | Censer Familiar
    (2600119, 2870662, 100, 1, 1), # Pyramid Skeleton | Pyramid Skeleton Familiar
    (9600002, 2871129, 100, 1, 1), # Duck | Duck Familiar
    (9600003, 2871130, 100, 1, 1), # Sheep | Sheep Familiar
    (9600001, 2871128, 100, 1, 1), # Rooster | Rooster Familiar
    (9600006, 2871133, 100, 1, 1), # Cow | Cow Familiar
    (9600007, 2871134, 100, 1, 1), # Plow Ox | Plow Ox Familiar
    (9309208, 2870864, 100, 1, 1), # Dorothy's Illusion | Dorothy's Illusion Familiar
    (9600004, 2871131, 100, 1, 1), # Goat | Goat Familiar
    (9600005, 2871132, 100, 1, 1), # Black Goat | Black Goat Familiar
    (9309206, 2871187, 100, 1, 1), # Toto | Toto Familiar
    (9309207, 2870863, 100, 1, 1), # Dorothy | Dorothy Familiar
    (9600008, 2871135, 100, 1, 1), # Black Sheep | Black Sheep Familiar
    (9309205, 2871185, 100, 1, 1), # Tin Woodsman | Tin Woodsman Familiar
    (8140000, 2870312, 100, 1, 1), # Lycanthrope | Lycanthrope Familiar
    (2600123, 2870666, 100, 1, 1), # Advisor Mummy | Advisor Mummy Familiar
    (8140001, 2870313, 100, 1, 1), # Harp | Harp Familiar
    (2600122, 2870665, 100, 1, 1), # Horus | Horus Familiar
    (9309203, 2871186, 100, 1, 1), # Scarecrow | Scarecrow Familiar
    (8140002, 2870314, 100, 1, 1), # Blood Harp | Blood Harp Familiar
    (9309200, 2871184, 100, 1, 1), # Cowardly Lion | Cowardly Lion Familiar
    (2600120, 2870663, 100, 1, 1), # Mummy | Mummy Familiar
    (4300001, 2870171, 100, 1, 1), # Blue Perfume | Blue Perfume Familiar
    (4300003, 2870172, 100, 1, 1), # Yellow Perfume | Yellow Perfume Familiar
    (4300005, 2870173, 100, 1, 1), # Pink Perfume | Pink Perfume Familiar
    (4300007, 2870175, 100, 1, 1), # Female Mannequin | Female Mannequin Familiar
    (4300006, 2870174, 100, 1, 1), # Kid Mannequin | Kid Mannequin Familiar
    (4300009, 2870177, 100, 1, 1), # Latest Hits Compilation | Latest Hits Compilation Familiar
    (4300008, 2870176, 100, 1, 1), # Male Mannequin | Male Mannequin Familiar
    (4300011, 2870179, 100, 1, 1), # Cheap Amplifier | Cheap Amplifier Familiar
    (4300010, 2870178, 100, 1, 1), # Greatest Oldies | Greatest Oldies Familiar
    (4300013, 2870181, 100, 1, 1), # Spirit of Rock | Spirit of Rock Familiar
    (4300012, 2870180, 100, 1, 1), # Fancy Amplifier | Fancy Amplifier Familiar
    (9303103, 2870855, 100, 1, 1), # Magnificent Murgoth | Magnificent Murgoth Familiar
    (210100, 2870008, 100, 1, 1), # Slime | Slime Familiar
    (8150200, 2870338, 100, 1, 1), # Green Cornian | Green Cornian Familiar
    (8150201, 2870339, 100, 1, 1), # Dark Cornian | Dark Cornian Familiar
    (8230069, 2870607, 100, 1, 1), # Seamus | Seamus Familiar
    (9303108, 2870860, 100, 1, 1), # Pirate Lil Murgoth | Pirate Lil Murgoth Familiar
    (8230071, 2870973, 100, 1, 1), # Will | Will Familiar
    (8230070, 2870605, 100, 1, 1), # Keene | Keene Familiar
    (8142000, 2870332, 100, 1, 1), # [*]Phantom Watch | [*]Phantom Watch Familiar
    (9303105, 2870857, 100, 1, 1), # Mage Lil Murgoth | Mage Lil Murgoth Familiar
    (9303104, 2870856, 100, 1, 1), # Warrior Lil Murgoth | Warrior Lil Murgoth Familiar
    (9303107, 2870859, 100, 1, 1), # Thief Lil Murgoth | Thief Lil Murgoth Familiar
    (9303106, 2870858, 100, 1, 1), # Bowman Lil Murgoth | Bowman Lil Murgoth Familiar
    (7130300, 2870285, 100, 1, 1), # [*]Master Death Teddy | [*]Master Death Teddy Familiar
    (9303135, 2870923, 100, 1, 1), # Black Knight | Black Knight Familiar
    (4230300, 2870158, 100, 1, 1), # Moon Bunny | Moon Bunny Familiar
    (3210450, 2870088, 100, 1, 1), # Scuba Pepe | Scuba Pepe Familiar
    (8230039, 2880655, 100, 1, 1), # Black Wing Henchman | Black Wing Henchman Familiar
    (9303137, 2870925, 100, 1, 1), # Rampant Cyborg | Rampant Cyborg Familiar
    (9303136, 2870924, 100, 1, 1), # Mad Mage | Mad Mage Familiar
    (9303139, 2870927, 100, 1, 1), # Bad Brawler | Bad Brawler Familiar
    (9303138, 2870926, 100, 1, 1), # Vicious Hunter | Vicious Hunter Familiar
    (3110102, 2870070, 100, 1, 1), # Ratz | Ratz Familiar
    (3110101, 2870069, 100, 1, 1), # Pink Teddy | Pink Teddy Familiar
    (3110100, 2870068, 100, 1, 1), # Ligator | Ligator Familiar
    (4130103, 2870129, 100, 1, 1), # Rombot | Rombot Familiar
    (4130101, 2870128, 100, 1, 1), # Tortie | Tortie Familiar
    (4130100, 2870127, 100, 1, 1), # Copper Drake | Copper Drake Familiar
    (8220009, 2870371, 100, 1, 1), # Snack Bar | Snack Bar Familiar
    (8220011, 2870523, 100, 1, 1), # Aufheben | ?
    (8220010, 2880560, 100, 1, 1), # Dunas | ?
    (2600710, 2870368, 100, 1, 1), # Lilynouch | Lilynouch Familiar
    (2600705, 2870367, 100, 1, 1), # Dodo | Dodo Familiar
    (8220007, 2870370, 100, 1, 1), # Blue Mushmom | Blue Mushmom Familiar
    (2600715, 2870369, 100, 1, 1), # Lyka | Lyka Familiar
    (8220001, 2870364, 100, 1, 1), # Snowman | Snowman Familiar
    (8220000, 2870363, 100, 1, 1), # Eliza | Eliza Familiar
    (8220003, 2870366, 100, 1, 1), # Leviathan | Leviathan Familiar
    (8220002, 2870365, 100, 1, 1), # Chimera | Chimera Familiar
    (8644001, 2870838, 100, 1, 1), # Sun Spirit | Sun Spirit Familiar
    (8644000, 2870837, 100, 1, 1), # Water Spirit | Water Spirit Familiar
    (4230500, 2870160, 100, 1, 1), # Chipmunk | Chipmunk Familiar
    (120100, 2870002, 100, 1, 1), # Shroom | Shroom Familiar
    (4230501, 2870161, 100, 1, 1), # Red Porky | Red Porky Familiar
    (4230502, 2870162, 100, 1, 1), # Black Porky | Black Porky Familiar
    (8644005, 2870840, 100, 1, 1), # Toxic Spirit | Toxic Spirit Familiar
    (4230503, 2870163, 100, 1, 1), # Blue Flower Serpent | Blue Flower Serpent Familiar
    (8644004, 2870839, 100, 1, 1), # Thunder Cloud Spirit | Thunder Cloud Spirit Familiar
    (4230504, 2870164, 100, 1, 1), # Red Flower Serpent | Red Flower Serpent Familiar
    (4230505, 2870165, 100, 1, 1), # Jar | Jar Familiar
    (8644010, 2870841, 100, 1, 1), # Discordant Spirit | Discordant Spirit Familiar
    (4230506, 2870166, 100, 1, 1), # Ginseng Jar | Ginseng Jar Familiar
    (8644012, 2870843, 100, 1, 1), # Spirit Debris | Spirit Debris Familiar
    (8140110, 2870319, 100, 1, 1), # Birk | Birk Familiar
    (8140111, 2870320, 100, 1, 1), # Dual Birk | Dual Birk Familiar
    (8140100, 2870315, 100, 1, 1), # Dark Yeti and Pepe | Dark Yeti and Pepe Familiar
    (8140101, 2870316, 100, 1, 1), # Black Kentaurus | Black Kentaurus Familiar
    (8140102, 2870317, 100, 1, 1), # Red Kentaurus | Red Kentaurus Familiar
    (8140103, 2870318, 100, 1, 1), # Blue Kentaurus | Blue Kentaurus Familiar
    (4230400, 2870159, 100, 1, 1), # Iron Boar | Iron Boar Familiar
    (6090004, 2870232, 100, 1, 1), # Rurumo | Rurumo Familiar
    (6090000, 2870228, 100, 1, 1), # Lich | Lich Familiar
    (7130400, 2870286, 100, 1, 1), # Yellow King Goblin | Yellow King Goblin Familiar
    (6090001, 2870229, 100, 1, 1), # Snow Witch | Snow Witch Familiar
    (7130401, 2870287, 100, 1, 1), # Blue King Goblin | Blue King Goblin Familiar
    (6090002, 2870230, 100, 1, 1), # Bamboo Warrior | Bamboo Warrior Familiar
    (7130402, 2870288, 100, 1, 1), # Green King Goblin | Green King Goblin Familiar
    (6090003, 2870231, 100, 1, 1), # Scholar Ghost | Scholar Ghost Familiar
    (2600311, 2880619, 100, 1, 1), # Giant Centipede | Giant Centipede Familiar
    (8642002, 2870690, 100, 1, 1), # Ewenana | Ewenana Familiar
    (8642006, 2870691, 100, 1, 1), # Unripe Wolfruit | Unripe Wolfruit Familiar
    (2600305, 2870504, 100, 1, 1), # King Sage Cat | King Sage Cat Familiar
    (9410007, 2870119, 100, 1, 1), # Green Bubble Tea | Melon Bubble Tea Familiar
    (9410006, 2870118, 100, 1, 1), # Yellow Bubble Tea | Mango Bubble Tea Familiar
    (9410005, 2870117, 100, 1, 1), # Red Bubble Tea | Cherry Bubble Tea Familiar
    (9410004, 2871101, 100, 1, 1), # Biker Monkey | Biker Monkey Familiar
    (9410003, 2871100, 100, 1, 1), # Clown Monkey | Clown Monkey Familiar
    (9410002, 2871099, 100, 1, 1), # Angry Stray Dog | Angry Stray Dog Familiar
    (9410001, 2871098, 100, 1, 1), # Stylish Stray Dog | Stylish Stray Dog Familiar
    (8642012, 2870692, 100, 1, 1), # Crilia | Crilia Familiar
    (9410000, 2871097, 100, 1, 1), # Stray Dog | Stray Dog Familiar
    (8150300, 2870340, 100, 1, 1), # Red Wyvern | Red Wyvern Familiar
    (9500144, 2872002, 100, 1, 1), # Coke Snail | Coke Snail Familiar
    (8150301, 2870341, 100, 1, 1), # Blue Wyvern | Blue Wyvern Familiar
    (9500145, 2872003, 100, 1, 1), # Coke Seal | Coke Seal Familiar
    (8150302, 2870342, 100, 1, 1), # Dark Wyvern | Dark Wyvern Familiar
    (9500146, 2872004, 100, 1, 1), # Play Seal | Play Seal Familiar
    (9500147, 2872005, 100, 1, 1), # Yeti and Coketump | Yeti and Coketump Familiar
    (9500148, 2872006, 100, 1, 1), # Igloo Turtle | Igloo Turtle Familiar
    (8642022, 2870693, 100, 1, 1), # Patriarch Birdshark | Patriarch Birdshark Familiar
    (9500149, 2872007, 100, 1, 1), # Coke Golem | Coke Golem Familiar
    (9500150, 2872008, 100, 1, 1), # Ice Golem | Ice Golem Familiar
    (9500151, 2872009, 100, 1, 1), # Coke Slime | Coke Slime Familiar
    (8142100, 2870333, 100, 1, 1), # Risell Squid | Risell Squid Familiar
    (9500152, 2872010, 100, 1, 1), # Coke Mushroom | Coke Mushroom Familiar
    (9500153, 2872011, 100, 1, 1), # Coketump | Coketump Familiar
    (9500154, 2872012, 100, 1, 1), # Coketump Lite | Coketump Lite Familiar
    (5150000, 2870212, 100, 1, 1), # Mixed Golem | Mixed Golem Familiar
    (5150001, 2870213, 100, 1, 1), # Skeleton Soldier | Skeleton Soldier Familiar
    (9500143, 2872001, 100, 1, 1), # Coke Pig | Coke Pig Familiar
    (7130600, 2870291, 100, 1, 1), # Hobi | Hobi Familiar
    (7130601, 2870292, 100, 1, 1), # Green Hobi | Green Hobi Familiar
    (9420042, 2871115, 100, 1, 1), # Bully Visitor | Bully Visitor Familiar
    (4230600, 2870167, 100, 1, 1), # Desert Giant | Desert Giant Familiar
    (5400000, 2870227, 100, 1, 1), # Jr. Pepe | Jr. Pepe Familiar
    (9420060, 2871119, 100, 1, 1), # Commander | Commander Familiar
    (1140100, 2870014, 100, 1, 1), # Ghost Stump | Ghost Stump Familiar
    (9420057, 2871118, 100, 1, 1), # Destroyer | Destroyer Familiar
    (8300006, 2870374, 100, 1, 1), # Dragonoir | Dragonoir Familiar
    (8300007, 2870375, 100, 1, 1), # Dragon Rider | Dragon Rider Familiar
    (9420054, 2871117, 100, 1, 1), # Speedy Visitor | Speedy Visitor Familiar
    (9420051, 2871116, 100, 1, 1), # Unjust Visitor | Unjust Visitor Familiar
    (8300000, 2870372, 100, 1, 1), # Soaring Hawk | Soaring Hawk Familiar
    (8300001, 2870373, 100, 1, 1), # Soaring Eagle | Soaring Eagle Familiar
    (8820007, 2870380, 100, 1, 1), # Mini Bean | Mini Bean Familiar
    (9420075, 2871127, 100, 1, 1), # Researcher H | Researcher H Familiar
    (9420074, 2871126, 100, 1, 1), # Randall Machine | Randall Machine Familiar
    (9420073, 2871125, 100, 1, 1), # OS4 Delivery Ship | OS4 Delivery Ship Familiar
    (9420072, 2871124, 100, 1, 1), # OS4 Shuttle | OS4 Shuttle Familiar
    (9420071, 2871123, 100, 1, 1), # OS3A Machine | OS3A Machine Familiar
    (9420070, 2871122, 100, 1, 1), # OS3M Miner | OS3M Miner Familiar
    (9420069, 2871121, 100, 1, 1), # OS3D Diver | OS3D Diver Familiar
    (9420065, 2871120, 100, 1, 1), # Ultimate Visitor | Ultimate Visitor Familiar
    (3110303, 2870074, 100, 1, 1), # Triple Rumo | Triple Rumo Familiar
    (3110302, 2870073, 100, 1, 1), # Rumo | Rumo Familiar
    (3110301, 2870072, 100, 1, 1), # Dark Sand Dwarf | Dark Sand Dwarf Familiar
    (1140130, 2870015, 100, 1, 1), # Smirking Ghost Stump | Smirking Ghost Stump Familiar
    (3110300, 2870071, 100, 1, 1), # Cube Slime | Cube Slime Familiar
    (8140200, 2870321, 100, 1, 1), # [*]Klock | [*]Klock Familiar
    (7130500, 2870289, 100, 1, 1), # Rash | Rash Familiar
    (3100102, 2870067, 100, 1, 1), # Kiyo | Kiyo Familiar
    (7130501, 2870290, 100, 1, 1), # Dark Rash | Dark Rash Familiar
    (3100101, 2870066, 100, 1, 1), # Sand Dwarf | Sand Dwarf Familiar
    (3210800, 2870089, 100, 1, 1), # Lupin | Lupin Familiar
    (9500317, 2872024, 100, 1, 1), # Kid Snowman | Kid Snowman Familiar
    (9500318, 2872025, 100, 1, 1), # Angry Snowman | Angry Snowman Familiar
    (9500319, 2872026, 100, 1, 1), # Giant Snowman | Giant Snowman Familiar
    (9500300, 2872022, 100, 1, 1), # Busted Doll | Busted Doll Familiar
    (9500301, 2872023, 100, 1, 1), # Destroyed Doll | Destroyed Doll Familiar
    (2600504, 2870502, 100, 1, 1), # Rurumo | Rurumo Familiar
    (8210013, 2870451, 100, 1, 1), # Prison Guard Ani | Prison Guard Ani Familiar
    (8210005, 2870448, 100, 1, 1), # Castle Golem | Castle Golem Familiar
    (8210004, 2870447, 100, 1, 1), # Grey Vulture | Grey Vulture Familiar
    (8210007, 2870450, 100, 1, 1), # Prison Guard Rhino | Prison Guard Rhino Familiar
    (8210006, 2870449, 100, 1, 1), # Prison Guard Boar | Prison Guard Boar Familiar
    (8210001, 2870444, 100, 1, 1), # Reindeer | Reindeer Familiar
    (8210000, 2870443, 100, 1, 1), # Crocky the Gatekeeper | Crocky the Gatekeeper Familiar
    (8210003, 2870446, 100, 1, 1), # Bearwolf | Bearwolf Familiar
    (8210002, 2870445, 100, 1, 1), # Blood Reindeer | Blood Reindeer Familiar
    (9500320, 2872027, 100, 1, 1), # Lost Rudolph | Lost Rudolph Familiar
    (9500323, 2872028, 100, 1, 1), # Kitty from Cheese Storage | Kitty from Cheese Storage Familiar
    (9400014, 2871012, 100, 1, 1), # Black Crow | Black Crow Familiar
    (9400013, 2871011, 100, 1, 1), # Sleepy Ghost | Dreamy Ghost Familiar
    (9400012, 2871010, 100, 1, 1), # Water Goblin | Water Goblin Familiar
    (9400011, 2871009, 100, 1, 1), # Paper Lantern Ghost | Paper Lantern Ghost Familiar
    (9400008, 2871008, 100, 1, 1), # Black Boogie | Black Boogie Familiar
    (8130100, 2870311, 100, 1, 1), # Jr. Balrog | Jr. Balrog Familiar
    (9400007, 2871007, 100, 1, 1), # Green Boogie | Green Boogie Familiar
    (9400006, 2871006, 100, 1, 1), # Blue Boogie | Blue Boogie Familiar
    (9400005, 2871005, 100, 1, 1), # Red Boogie | Red Boogie Familiar
    (9400004, 2871004, 100, 1, 1), # Big Cloud Fox | Big Cloud Fox Familiar
    (9400003, 2871003, 100, 1, 1), # Nightghost | Nightghost Familiar
    (9400002, 2871002, 100, 1, 1), # Cloud Fox | Cloud Fox Familiar
    (9400001, 2871001, 100, 1, 1), # Fire Raccoon | Fire Raccoon Familiar
    (9400000, 2871000, 100, 1, 1), # Crow | Crow Familiar
    (9500383, 2872029, 100, 1, 1), # Wild Monkey | Wild Monkey Familiar
    (2150000, 2870038, 100, 1, 1), # Water Thief Monster | Water Thief Monster Familiar
    (2150001, 2870039, 100, 1, 1), # Dust Box | Dust Box Familiar
    (2150002, 2870040, 100, 1, 1), # Streetlight | Streetlight Familiar
    (2150003, 2870041, 100, 1, 1), # Patrol Robot S | Patrol Robot S Familiar
    (2600517, 2870503, 100, 1, 1), # D. Roy | D. Roy Familiar
    (1130100, 2870013, 100, 1, 1), # Axe Stump | Axe Stump Familiar
    (5140000, 2870211, 100, 1, 1), # White Fang | White Fang Familiar
    (8140300, 2870322, 100, 1, 1), # [*]Dark Klock | [*]Dark Klock Familiar
    (9500384, 2872030, 100, 1, 1), # Mama Monkey | Mama Monkey Familiar
    (9500385, 2872031, 100, 1, 1), # White Baby Monkey | Teeny White Monkey Familiar
    (9500386, 2872032, 100, 1, 1), # White Mama Monkey | Mean Mama Monkey Familiar
    (9500387, 2872033, 100, 1, 1), # Blue Goblin | Blue Goblin Familiar
    (8630007, 2870798, 100, 1, 1), # Burning Rage | Burning Rage Familiar
    (9101054, 2870934, 100, 1, 1), # Golem | Golem Familiar
    (9500388, 2872034, 100, 1, 1), # Red Goblin | Red Goblin Familiar
    (8630006, 2870797, 100, 1, 1), # Burning Terror | Burning Terror Familiar
    (9500389, 2872035, 100, 1, 1), # Strong Stone Goblin | Stone Goblin Familiar
    (8630005, 2870796, 100, 1, 1), # Burning Solitude | Burning Solitude Familiar
    (9500390, 2872036, 100, 1, 1), # Ravana | Ravana Familiar
    (8630011, 2870802, 100, 1, 1), # Permeating Terror | Permeating Terror Familiar
    (8630010, 2870801, 100, 1, 1), # Permeating Solitude | Permeating Solitude Familiar
    (8630009, 2870800, 100, 1, 1), # Burning Vanity | Burning Vanity Familiar
    (8630008, 2870799, 100, 1, 1), # Burning Anxiety | Burning Anxiety Familiar
    (8630015, 2870806, 100, 1, 1), # Corrupted Basic Magician | Corrupted Basic Magician Familiar
    (8630014, 2870805, 100, 1, 1), # Permeating Vanity | Permeating Vanity Familiar
    (8630013, 2870804, 100, 1, 1), # Permeating Anxiety | Permeating Anxiety Familiar
    (8630012, 2870803, 100, 1, 1), # Permeating Rage | Permeating Rage Familiar
    (2600613, 2870219, 100, 1, 1), # Timer | Timer Familiar
    (9500184, 2872014, 100, 1, 1), # Rideword P | Rideword P Familiar
    (2600622, 2870491, 100, 1, 1), # Alishar | Alishar Familiar
    (9500185, 2872015, 100, 1, 1), # Rideword Y | Rideword Y Familiar
    (9500186, 2872016, 100, 1, 1), # Rideword B | Rideword B Familiar
    (9500190, 2872017, 100, 1, 1), # Toy Clown | Toy Clown Familiar
    (9500191, 2872018, 100, 1, 1), # Green Phantom | Green Phantom Familiar
    (2600631, 2870518, 100, 1, 1), # Papulatus | Papulatus Familiar
    (9500167, 2872013, 100, 1, 1), # Golden Pig | Golden Pig Familiar
    (8140512, 2880562, 100, 1, 1), # Royal Guard | ?
    (8140511, 2880561, 100, 1, 1), # Imperial Guard | ?
    (8140500, 2870323, 100, 1, 1), # [*]Bain | [*]Bain Familiar
    (9500193, 2872019, 100, 1, 1), # Fire Steed | Fire Steed Familiar
    (9500195, 2872020, 100, 1, 1), # Jack-o-Lantern | Jack-o-Lantern Familiar
    (9500196, 2872021, 100, 1, 1), # Ghost | Ghost Familiar
    (2600607, 2871191, 100, 1, 1), # Targa | Targa Familiar
    (9305677, 2880723, 100, 1, 1), # Red Tiger | Red Tiger Familiar
    (9305676, 2880722, 100, 1, 1), # Gold Dragon | Gold Dragon Familiar
    (9305673, 2880719, 100, 1, 1), # Bonsun | Bonsun Familiar
    (9305672, 2880718, 100, 1, 1), # Alang | Alang Familiar
    (9305675, 2880721, 100, 1, 1), # Sondal | Sondal Familiar
    (9305674, 2880720, 100, 1, 1), # Wukong | Wukong Familiar
    (6160001, 2870675, 100, 1, 1), # Mammoth | Mammoth Familiar
    (6160000, 2870674, 100, 1, 1), # Golden Ferret | Golden Ferret Familiar
    (6160003, 2870677, 100, 1, 1), # Xerxes | Xerxes Familiar
    (6160002, 2870676, 100, 1, 1), # Golden Mammoth | Golden Mammoth Familiar
    (1210102, 2870021, 100, 1, 1), # Orange Mushroom | Orange Mushroom Familiar
    (1210103, 2870022, 100, 1, 1), # Bubbling | Bubbling Familiar
    (1210100, 2870019, 100, 1, 1), # Pig | Pig Familiar
    (1210101, 2870020, 100, 1, 1), # Ribbon Pig | Ribbon Pig Familiar
    (3000005, 2870063, 100, 1, 1), # Brown Teddy | Brown Teddy Familiar
    (1210104, 2870023, 100, 1, 1), # Blue Ribbon Pig | Blue Ribbon Pig Familiar
    (3000007, 2870065, 100, 1, 1), # Royal Fairy | Royal Fairy Familiar
    (3000006, 2870064, 100, 1, 1), # Krip | Krip Familiar
    (3000001, 2870062, 100, 1, 1), # Fairy | Fairy Familiar
    (5220003, 2870219, 100, 1, 1), # Timer | Timer Familiar
    (1210111, 2870024, 100, 1, 1), # Strange Pig | Strange Pig Familiar
    (3000000, 2870061, 100, 1, 1), # Sentinel | Sentinel Familiar
    (5220002, 2870218, 100, 1, 1), # Faust | Faust Familiar
    (5220000, 2870217, 100, 1, 1), # King Clang | King Clang Familiar
    (7110300, 2870270, 100, 1, 1), # D. Roy | D. Roy Familiar
    (7110301, 2870271, 100, 1, 1), # Homunculus | Homunculus Familiar
    (8880006, 2870575, 100, 1, 1), # Victor | Victor Familiar
    (8880004, 2870570, 100, 1, 1), # Velderoth | Velderoth Familiar
    (9400204, 2871025, 100, 1, 1), # Silver Slime | Silver Slime
    (100101, 2870001, 100, 1, 1), # Blue Snail | Blue Snail Familiar
    (9402251, 2870911, 100, 1, 1), # Actinops | Actinops Familiar
    (9400203, 2871024, 100, 1, 1), # Silver Slime | Silver Slime Familiar
    (9402250, 2870910, 100, 1, 1), # Frostflail Yeti | Frostflail Yeti Familiar
    (9400202, 2871023, 100, 1, 1), # Golden Slime | Golden Slime Familiar
    (9402249, 2870909, 100, 1, 1), # Fembris | Fembris Familiar
    (9402248, 2870908, 100, 1, 1), # Raging Leatty | Raging Leatty Familiar
    (9402246, 2870907, 100, 1, 1), # Frost Hellfang | Frost Hellfang Familiar
    (9402244, 2870905, 100, 1, 1), # Grimgaze Werebeast | Grimgaze Werebeast Familiar
    (9402243, 2870904, 100, 1, 1), # Frostfang Werebeast | Frostfang Werebeast Familiar
    (9402241, 2870902, 100, 1, 1), # Raging Shrelephant | Raging Shrelephant Familiar
    (8880008, 2870576, 100, 1, 1), # Treglow | Treglow Familiar
    (9402240, 2870901, 100, 1, 1), # Petreefied Eyeful | Petreefied Eyeful Familiar
    (8644503, 2870889, 100, 1, 1), # Bellalis | Bellalis Familiar
    (8644502, 2870888, 100, 1, 1), # Bellalion | Bellalion Familiar
    (8644507, 2870893, 100, 1, 1), # Keeper of Darkness | Keeper of Darkness Familiar
    (8644506, 2870892, 100, 1, 1), # Keeper of Light | Keeper of Light Familiar
    (8644505, 2870891, 100, 1, 1), # Aranea | Aranea Familiar
    (8644504, 2870890, 100, 1, 1), # Aranya | Aranya Familiar
    (8644509, 2870895, 100, 1, 1), # Dark Executor | Dark Executor Familiar
    (8644508, 2870894, 100, 1, 1), # Light Executor | Light Executor Familiar
    (2400004, 2870543, 100, 1, 1), # Amethyst Tokka | Amethyst Tokka Familiar
    (2400005, 2870544, 100, 1, 1), # Laloong | Laloong Familiar
    (2400006, 2870545, 100, 1, 1), # Kaloong | Kaloong Familiar
    (100131, 2880643, 100, 1, 1), # Murupa | Murupa Familiar
    (2400007, 2870546, 100, 1, 1), # Nefarious Monk Initiate | Nefarious Monk Initiate Familiar
    (100130, 2880642, 100, 1, 1), # Muru | Muru Familiar
    (2400000, 2870539, 100, 1, 1), # Sleepy Grobbler | Sleepy Grobbler Familiar
    (100133, 2880645, 100, 1, 1), # Murumuru | Murumuru Familiar
    (2400001, 2870540, 100, 1, 1), # Alert Grobbler | Alert Grobbler Familiar
    (100132, 2880644, 100, 1, 1), # Murupia | Murupia Familiar
    (2400002, 2870541, 100, 1, 1), # Grumpy Grobbler | Grumpy Grobbler Familiar
    (2400003, 2870542, 100, 1, 1), # Limestone Tokka | Limestone Tokka Familiar
    (100134, 2880646, 100, 1, 1), # Murukun | Murukun Familiar
    (2400012, 2870551, 100, 1, 1), # Heretic Leader | Heretic Leader Familiar
    (2400013, 2870552, 100, 1, 1), # Onyx Stonegar | Onyx Stonegar Familiar
    (2400014, 2870553, 100, 1, 1), # Gravi Stonegar | Gravi Stonegar Familiar
    (8644520, 2870896, 100, 1, 1), # Origin Guardian | Origin Guardian Familiar
    (2400008, 2870547, 100, 1, 1), # Nefarious Monk Apprentice | Nefarious Monk Apprentice Familiar
    (6150000, 2870246, 100, 1, 1), # Guard Robot | Guard Robot Familiar
    (2400009, 2870548, 100, 1, 1), # Nefarious Monk Master | Nefarious Monk Master Familiar
    (2400010, 2870549, 100, 1, 1), # Heretic Monk Initiate | Heretic Monk Initiate Familiar
    (2400011, 2870550, 100, 1, 1), # Heretic Monk Apprentice | Heretic Monk Apprentice Familiar
    (8200012, 2870362, 100, 1, 1), # Chief Oblivion Guardian | Chief Oblivion Guardian Familiar
    (5130108, 2870210, 100, 1, 1), # [*]Miner Zombie | [*]Miner Zombie Familiar
    (9500579, 2870931, 100, 1, 1), # Whipped Cream Wight | Whipped Cream Wight Familiar
    (8200009, 2870359, 100, 1, 1), # Oblivion Monk | Oblivion Monk Familiar
    (9500580, 2870932, 100, 1, 1), # Sweet Choco Cream | Sweet Choco Cream Familiar
    (5130107, 2870209, 100, 1, 1), # Coolie Zombie | Coolie Zombie Familiar
    (8200008, 2870358, 100, 1, 1), # Chief Qualm Guardian | Chief Qualm Guardian Familiar
    (5130104, 2870207, 100, 1, 1), # Hector | Hector Familiar
    (8200011, 2870361, 100, 1, 1), # Oblivion Guardian | Oblivion Guardian Familiar
    (5130105, 2870208, 100, 1, 1), # Dark Jr. Yeti | Dark Jr. Yeti Familiar
    (8200010, 2870360, 100, 1, 1), # Oblivion Monk Trainee | Oblivion Monk Trainee Familiar
    (5130102, 2870205, 100, 1, 1), # Dark Stone Golem | Dark Stone Golem Familiar
    (8200005, 2870355, 100, 1, 1), # Qualm Monk | Qualm Monk Familiar
    (5130103, 2870206, 100, 1, 1), # Croco | Croco Familiar
    (8200004, 2870354, 100, 1, 1), # Chief Memory Guardian | Chief Memory Guardian Familiar
    (5130100, 2870203, 100, 1, 1), # Drake | Drake Familiar
    (8200007, 2870357, 100, 1, 1), # Qualm Guardian | Qualm Guardian Familiar
    (5130101, 2870204, 100, 1, 1), # Stone Golem | Stone Golem Familiar
    (8200006, 2870356, 100, 1, 1), # Qualm Monk Trainee | Qualm Monk Trainee Familiar
    (7090000, 2870269, 100, 1, 1), # Security Camera | Security Camera Familiar
    (8200001, 2870351, 100, 1, 1), # Memory Monk | Memory Monk Familiar
    (8200000, 2870350, 100, 1, 1), # Eye of Time | Eye of Time Familiar
    (8200003, 2870353, 100, 1, 1), # Memory Guardian | Memory Guardian Familiar
    (8200002, 2870352, 100, 1, 1), # Memory Monk Trainee | Memory Monk Trainee Familiar
    (2230114, 2870057, 100, 1, 1), # Muddy Swamp Monster | Muddy Swamp Monster Familiar
    (2400100, 2870554, 100, 1, 1), # Blue Speeyor | Blue Speeyor Familiar
    (2400101, 2870555, 100, 1, 1), # Red Speeyor | Red Speeyor Familiar
    (2230112, 2870055, 100, 1, 1), # Terrified Wild Boar | Terrified Wild Boar Familiar
    (2400102, 2870556, 100, 1, 1), # Yellow Speeyor | Yellow Speeyor Familiar
    (9402317, 2870915, 100, 1, 1), # Skuas Miner | Skuas Miner Familiar
    (2230113, 2870056, 100, 1, 1), # Surgeon Eye | Surgeon Eye Familiar
    (2400103, 2870557, 100, 1, 1), # Dinogoth | Dinogoth Familiar
    (9402316, 2870914, 100, 1, 1), # Skuas Guard | Skuas Guard Familiar
    (8140600, 2870324, 100, 1, 1), # Bone Fish | Bone Fish Familiar
    (9402315, 2870913, 100, 1, 1), # Svarti Harpooner | Svarti Harpooner Familiar
    (9402313, 2870912, 100, 1, 1), # Kaptafel Snowfield Archer | Kaptafel Snowfield Archer Familiar
    (9402312, 2870922, 100, 1, 1), # Ullan | Ullan Familiar
    (2400108, 2870562, 100, 1, 1), # Specter Miner | Specter Miner Familiar
    (9402311, 2870916, 100, 1, 1), # Kan | Kan Familiar
    (2400109, 2870563, 100, 1, 1), # Specter Shieldbearer | Specter Shieldbearer Familiar
    (9402310, 2870920, 100, 1, 1), # Einar | Einar Familiar
    (2400110, 2870564, 100, 1, 1), # Guerrilla Specter | Guerrilla Specter Familiar
    (9402309, 2870921, 100, 1, 1), # Aruhi | Aruhi Familiar
    (2400111, 2870565, 100, 1, 1), # Power Specter | Power Specter Familiar
    (9402308, 2870918, 100, 1, 1), # Slaka | Slaka Familiar
    (2400104, 2870558, 100, 1, 1), # Dinoram | Dinoram Familiar
    (2400105, 2870559, 100, 1, 1), # Dinodon | Dinodon Familiar
    (9402306, 2870917, 100, 1, 1), # Peytour | Peytour Familiar
    (2400106, 2870560, 100, 1, 1), # Specter Battle Hound | Specter Battle Hound Familiar
    (9402305, 2870919, 100, 1, 1), # Gurnardson | Gurnardson Familiar
    (2400107, 2870561, 100, 1, 1), # Specter Tamer | Specter Tamer Familiar
    (2230131, 2870058, 100, 1, 1), # Annoyed Zombie Mushroom | Annoyed Zombie Mushroom Familiar
    (8120104, 2880555, 100, 1, 1), # Maverick Type A | ?
    (2400112, 2870566, 100, 1, 1), # Specter Engineer | Specter Engineer Familiar
    (2400113, 2870567, 100, 1, 1), # Warrior Specter | Warrior Specter Familiar
    (2400114, 2870568, 100, 1, 1), # Magician Specter | Magician Specter Familiar
    (2400115, 2870569, 100, 1, 1), # Reaper Specter | Reaper Specter Familiar
    (8120103, 2880554, 100, 1, 1), # Prototype Lord | ?
    (8120102, 2871026, 100, 1, 1), # Afterlord | ?
    (8620003, 2870654, 100, 1, 1), # Pillaging Wild Boar | Pillaging Wild Boar Familiar
    (8620002, 2870653, 100, 1, 1), # Swollen Axe Stump | Swollen Axe Stump Familiar
    (8620001, 2870652, 100, 1, 1), # Swollen Dark Stump | Swollen Dark Stump Familiar
    (8880101, 2870688, 100, 1, 1), # Damien | Damien Familiar
    (8620000, 2870651, 100, 1, 1), # Swollen Stump | Swollen Stump Familiar
    (9400300, 2871043, 100, 1, 1), # Yakuza Boss | Yakuza Boss Familiar
    (8880100, 2880738, 100, 1, 1), # Damien | Damien Familiar
    (8620007, 2870658, 100, 1, 1), # Sinister Rocky Mask | Sinister Rocky Mask Familiar
    (8620006, 2870657, 100, 1, 1), # Sinister Wooden Mask | Sinister Wooden Mask Familiar
    (8620005, 2870656, 100, 1, 1), # Pillaging Fire Boars | Pillaging Fire Boars Familiar
    (8620004, 2870655, 100, 1, 1), # Iron Boar Raider | Iron Boar Raider Familiar
    (8620011, 2870572, 100, 1, 1), # Ancient Mixed Golem | Ancient Mixed Golem Familiar
    (8620010, 2870661, 100, 1, 1), # Ancient Dark Golem | Ancient Dark Golem Familiar
    (8880110, 2870878, 100, 1, 1), # Normal Damien | Normal Damien Familiar
    (8620009, 2870660, 100, 1, 1), # Ancient Golem | Ancient Golem Familiar
    (8620008, 2870659, 100, 1, 1), # Sinister Steel Mask | Sinister Steel Mask Familiar
    (8620012, 2870573, 100, 1, 1), # Ghostwood Stumpy | Ghostwood Stumpy Familiar
    (5300000, 2870224, 100, 1, 1), # Leatty | Leatty Familiar
    (9400319, 2870751, 100, 1, 1), # Cross (Easy) | Cross (Easy) Familiar
    (5300001, 2870225, 100, 1, 1), # Dark Leatty | Dark Leatty Familiar
    (9400317, 2870735, 100, 1, 1), # Tac (Hard) | Tac (Hard) Familiar
    (9400316, 2870734, 100, 1, 1), # Tic (Hard) | Tic (Hard) Familiar
    (2230102, 2870047, 100, 1, 1), # Wild Boar | Wild Boar Familiar
    (9400315, 2870733, 100, 1, 1), # Toe (Medium) | Toe (Medium) Familiar
    (1120100, 2870012, 100, 1, 1), # Octopus | Octopus Familiar
    (9400314, 2870732, 100, 1, 1), # Tac (Medium) | Tac (Medium) Familiar
    (2230100, 2870045, 100, 1, 1), # Evil Eye | Evil Eye Familiar
    (9400313, 2870731, 100, 1, 1), # Tic (Medium) | Tic (Medium) Familiar
    (9302011, 2872000, 100, 1, 1), # Lupin Pig | Lupin Pig Familiar
    (2230101, 2870046, 100, 1, 1), # Zombie Mushroom | Zombie Mushroom Familiar
    (9400312, 2870730, 100, 1, 1), # Toe (Easy) | Toe (Easy) Familiar
    (2230106, 2870049, 100, 1, 1), # Cico | Cico Familiar
    (9400311, 2870729, 100, 1, 1), # Tac (Easy) | Tac (Easy) Familiar
    (2230107, 2870050, 100, 1, 1), # Krappy | Krappy Familiar
    (9400310, 2870728, 100, 1, 1), # Tic (Easy) | Tic (Easy) Familiar
    (2230105, 2870048, 100, 1, 1), # Seacle | Seacle Familiar
    (2230110, 2870053, 100, 1, 1), # Wooden Mask | Wooden Mask Familiar
    (2230111, 2870054, 100, 1, 1), # Rocky Mask | Rocky Mask Familiar
    (2230108, 2870051, 100, 1, 1), # Pinboom | Pinboom Familiar
    (2230109, 2870052, 100, 1, 1), # Bubble Fish | Bubble Fish Familiar
    (3502005, 2870615, 100, 1, 1), # Possibly-Evil Walrus | Possibly-Evil Walrus Familiar
    (3502004, 2870614, 100, 1, 1), # Possibly-Evil Seal | Possibly-Evil Seal Familiar
    (3502007, 2870617, 100, 1, 1), # Shaver Bot | Shaver Bot Familiar
    (8630017, 2870808, 100, 1, 1), # Corrupted Advanced Magician | Corrupted Advanced Magician Familiar
    (3502006, 2870616, 100, 1, 1), # Warmer Bot | Warmer Bot Familiar
    (8630016, 2870807, 100, 1, 1), # Corrupted Intermediate Magician | Corrupted Intermediate Magician Familiar
    (3502001, 2870611, 100, 1, 1), # Fish Grumpil | Fish Grumpil Familiar
    (3502000, 2870610, 100, 1, 1), # Ammonite Grumpil | Ammonite Grumpil Familiar
    (3502003, 2870613, 100, 1, 1), # Polluter Barrel | Polluter Barrel Familiar
    (3502002, 2870612, 100, 1, 1), # Corrupter Barrels | Corrupter Barrels Familiar
    (3502008, 2870618, 100, 1, 1), # Demolishizer | Demolishizer Familiar
    (2230200, 2870059, 100, 1, 1), # Flower Fish | Flower Fish Familiar
    (9400111, 2871018, 100, 1, 1), # Yakuza Shotcaller | Yakuza Shotcaller Familiar
    (9400110, 2871017, 100, 1, 1), # Yakuza Lieutenant | Yakuza Lieutenant Familiar
    (9400103, 2871016, 100, 1, 1), # Yakuza Enforcer | Yakuza Enforcer Familiar
    (9400102, 2871015, 100, 1, 1), # Yakuza Bruiser | Yakuza Bruiser Familiar
    (9400101, 2871014, 100, 1, 1), # Yakuza Grunt | Yakuza Grunt Familiar
    (9400100, 2871013, 100, 1, 1), # Yakuza Soldier | Yakuza Soldier Familiar
    (8644402, 2870977, 100, 1, 1), # Nameless Cat | Nameless Cat Familiar
    (8644406, 2870979, 100, 1, 1), # Red Shadow | Red Shadow Familiar
    (8644405, 2870978, 100, 1, 1), # Blue Shadow | Blue Shadow Familiar
    (9400121, 2871022, 100, 1, 1), # Yakuza Underboss | Yakuza Underboss Familiar
    (9400120, 2871021, 100, 1, 1), # Yakuza Executive | Yakuza Officer Familiar
    (8644411, 2870885, 100, 1, 1), # Thralled Wizard | Thralled Wizard Familiar
    (8644410, 2870887, 100, 1, 1), # Thralled Warhammer Knight | Thralled Warhammer Knight Familiar
    (8644409, 2870886, 100, 1, 1), # Thralled Guard | Thralled Guard Familiar
    (9400113, 2871020, 100, 1, 1), # Yakuza Protector | Yakuza Protector Familiar
    (8644412, 2870884, 100, 1, 1), # Thralled Archer | Thralled Archer Familiar
    (9400112, 2871019, 100, 1, 1), # Yakuza Bodyguard | Yakuza Bodyguard Familiar
    (9402191, 2870850, 100, 1, 1), # Beefy Churmble | Beefy Churmble Familiar
    (2220000, 2870042, 100, 1, 1), # Mano | Mano Familiar
    (9402190, 2870849, 100, 1, 1), # Cuddly Churmble | Cuddly Churmble Familiar
    (9101128, 2870906, 100, 1, 1), # Raging Hellfang | Raging Hellfang Familiar
    (9480019, 2880620, 100, 1, 1), # Black Bear Swordsman | Black Bear Swordsman Familiar
    (9480020, 2880621, 100, 1, 1), # White Tiger Swordsman | White Tiger Swordsman Familiar
    (9480021, 2871139, 100, 1, 1), # Eagle Swordsman | Eagle Swordsman Familiar
    (9480022, 2880623, 100, 1, 1), # Male Thief | Male Thief Familiar
    (9480023, 2871141, 100, 1, 1), # Female Thief | Female Thief Familiar
    (9480024, 2880626, 100, 1, 1), # Mini Gold Martial Artist | Mini Gold Martial Artist Familiar
    (9480025, 2880627, 100, 1, 1), # Mini Bronze Martial Artist | Mini Bronze Martial Artist Familiar
    (9480026, 2871145, 100, 1, 1), # Golden Giant | Golden Giant Familiar
    (9480027, 2880629, 100, 1, 1), # Silver Giant | Silver Giant Familiar
    (9480028, 2880630, 100, 1, 1), # Bronze Staffman | Bronze Staffman Familiar
    (9101127, 2870903, 100, 1, 1), # Raging Werebeast | Raging Werebeast Familiar
    (9480029, 2871148, 100, 1, 1), # Silver Spearman | Silver Spearman Familiar
    (9480030, 2871149, 100, 1, 1), # Martial Artist | Martial Artist Familiar
    (5300100, 2870226, 100, 1, 1), # Malady | Malady Familiar
    (9402193, 2870852, 100, 1, 1), # Toddling Churmble | Toddling Churmble Familiar
    (9402192, 2870851, 100, 1, 1), # Flappy Churmble | Flappy Churmble Familiar
    (2400251, 2870963, 100, 1, 1), # Flutterbuzz | Flutterbuzz Familiar
    (4110302, 2870126, 100, 1, 1), # Mithril Mutae | Mithril mutae Familiar
    (8140700, 2870325, 100, 1, 1), # Blue Dragon Turtle | Blue Dragon Turtle Familiar
    (2600903, 2870795, 100, 1, 1), # Frozen Vanity | Frozen Vanity Familiar
    (9500528, 2870930, 100, 1, 1), # Dracula Bear | Dracula Bear Familiar
    (8140701, 2870326, 100, 1, 1), # Red Dragon Turtle | Red Dragon Turtle Familiar
    (2600902, 2870794, 100, 1, 1), # Frozen Anxiety | Frozen Anxiety Familiar
    (4110300, 2870124, 100, 1, 1), # Iron Mutae | Iron Mutae Familiar
    (8140702, 2870327, 100, 1, 1), # Rexton | Rexton Familiar
    (2600901, 2870793, 100, 1, 1), # Frozen Rage | Frozen Rage Familiar
    (4110301, 2870125, 100, 1, 1), # Reinforced Iron Mutae | Reinforced Iron Mutae Familiar
    (8140703, 2870328, 100, 1, 1), # Brexton | Brexton Familiar
    (2600900, 2870792, 100, 1, 1), # Frozen Terror | Frozen Terror Familiar
    (2600911, 2870928, 100, 1, 1), # Giant Blackheart | Giant Blackheart Familiar
    (2600910, 2870678, 100, 1, 1), # Giant Blackheart | Giant Blackheart Familiar
    (2600904, 2870791, 100, 1, 1), # Frozen Solitude | Frozen Solitude Familiar
    (9402238, 2870900, 100, 1, 1), # Eyeful | Eyeful Familiar
    (9402237, 2870899, 100, 1, 1), # Shrelephant | Shrelephant Familiar
    (9402225, 2870702, 100, 1, 1), # Combat Mushroom | Combat Mushroom Familiar
    (9500526, 2870929, 100, 1, 1), # Halloween Pumpkin | Halloween Pumpkin Familiar
    (9300900, 4000884, 1000, 1, 1), # Pig | Primrose Seed
    (9300901, 4000884, 1000, 1, 1), # Ribbon Pig | Primrose Seed
    (8644400, 2881153, 1000, 1, 1), # Xenoroid Echo Type A | Xenoroid Echo Type A Familiar
    (8644401, 2881154, 1000, 1, 1), # Xenoroid Echo Type B | Xenoroid Echo Type B Familiar
    (8644402, 2881155, 1000, 1, 1), # Nameless Cat | Nameless Cat Familiar
    (8644403, 2881156, 1000, 1, 1), # Powerful Gangster | Powerful Gangster Familiar
    (8644404, 2881157, 1000, 1, 1), # Strong Gangster | Strong Gangster Familiar
    (8644405, 2881158, 1000, 1, 1), # Blue Shadow | Blue Shadow Familiar
    (8644406, 2881159, 1000, 1, 1), # Red Shadow | Red Shadow Familiar
    (8644407, 2881160, 1000, 1, 1), # Experiment Gone Wrong | Experiment Gone Wrong Familiar
    (8644408, 2881161, 1000, 1, 1), # Big Experiment Gone Wrong | Big Experiment Gone Wrong Familiar
    (8644409, 2881162, 1000, 1, 1), # Thralled Guard | Thralled Guard Familiar
    (8644410, 2881163, 1000, 1, 1), # Thralled Warhammer Knight | Thralled Warhammer Knight Familiar
    (8644411, 2881164, 1000, 1, 1), # Thralled Wizard | Thralled Wizard Familiar
    (8644412, 2881165, 1000, 1, 1), # Thralled Archer | Thralled Archer Familiar
    (8644500, 2881143, 1000, 1, 1), # Ahtuin | Ahtuin Familiar
    (8644501, 2881144, 1000, 1, 1), # Atus | Atus Familiar
    (8644502, 2881145, 1000, 1, 1), # Bellalion | Bellalion Familiar
    (8644503, 2881146, 1000, 1, 1), # Bellalis | Bellalis Familiar
    (8644504, 2881147, 1000, 1, 1), # Aranya | Aranya Familiar
    (8644505, 2881148, 1000, 1, 1), # Aranea | Aranea Familiar
    (8644506, 2881149, 1000, 1, 1), # Keeper of Light | Keeper of Light Familiar
    (8644507, 2881150, 1000, 1, 1), # Keeper of Darkness | Keeper of Darkness Familiar
    (8644508, 2881151, 1000, 1, 1), # Light Executor | Light Executor Familiar
    (8644509, 2881152, 1000, 1, 1), # Dark Executor | Dark Executor Familiar
    (8644614, 2881135, 1000, 1, 1), # Soot Beast | Soot Beast Familiar
    (8644615, 2881136, 1000, 1, 1), # Soot Talon | Soot Talon Familiar
    (8644616, 2881137, 1000, 1, 1), # Soot Slug | Soot Slug Familiar
    (8644617, 2881138, 1000, 1, 1), # Soot Core | Soot Core Familiar
    (8644618, 2881139, 1000, 1, 1), # Crushing Glare | Crushing Glare Familiar
    (8644619, 2881140, 1000, 1, 1), # Burst Glare | Burst Glare Familiar
    (8644701, 2881123, 1000, 1, 1), # Faith Fragment | Faith Fragment Familiar
    (8644700, 2881122, 1000, 1, 1), # Entangled Fragment | Entangled Fragment Familiar
    (8644702, 2881100, 1000, 1, 1), # Dark Miscreation | Dark Miscreation Familiar
    (8644703, 2881101, 1000, 1, 1), # Dark Construct | Dark Construct Familiar
    (8644704, 2881102, 1000, 1, 1), # Despairing Wing | Despairing Wing Familiar
    (8644705, 2881103, 1000, 1, 1), # Despairing Blade | Despairing Blade Familiar
    (8644706, 2881104, 1000, 1, 1), # Silent Knight | Silent Knight Familiar
    (8644707, 2881105, 1000, 1, 1), # Silent Scout | Silent Scout Familiar
    (8644708, 2881106, 1000, 1, 1), # Silent Rogue | Silent Rogue Familiar
    (8644709, 2881107, 1000, 1, 1), # Silent Watchman | Silent Watchman Familiar
    (8644710, 2881108, 1000, 1, 1), # Silent Assassin | Silent Assassin Familiar
    (8644711, 2881109, 1000, 1, 1), # Despairing Soul | Despairing Soul Familiar
    (8644712, 2881110, 1000, 1, 1), # Despairing Prisoner | Despairing Prisoner Familiar
    (8645010, 2881117, 1000, 1, 1), # Ancestion | Ancestion Familiar
    (8645012, 2881118, 1000, 1, 1), # Transcendion | Transcendion Familiar
    (8645011, 2881119, 1000, 1, 1), # Ascendion | Ascendion Familiar
    (8645023, 2881120, 1000, 1, 1), # Foreberion | Foreberion Familiar
    (8645022, 2881121, 1000, 1, 1), # Embrion | Embrion Familiar
    (9400518, 4031596, 10000, 1, 1), # Magik Fierry B | Wing Hammer
    (8600005, 2510262, 100, 1, 1), # Mutant Tiru | Deimos Sage Shield Recipe
    (8610005, 2510262, 100, 1, 1), # Official Knight A | Deimos Sage Shield Recipe
    (8620013, 2510262, 100, 1, 1), # [*] Official Knight A | Deimos Sage Shield Recipe
    (8610002, 2510262, 100, 1, 1), # Wind | Deimos Sage Shield Recipe
    (8610004, 2510262, 100, 1, 1), # Thunder | Deimos Sage Shield Recipe
    (5130101, 4000022, 5000, 1, 1), # Stone Golem | Stone Golem Rubble
    (4230105, 4032934, 5000, 1, 1); # Nependeath | Nependeath Fruit

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (4130102, 4032934, 5000, 1, 1), # Dark Nependeath | Nependeath Fruit
    (4130104, 4032934, 5000, 1, 1), # Dark Nependeath | Nependeath Fruit
    (4230122, 4032934, 5000, 1, 1), # Nependeath | Nependeath Fruit
    (9300274, 4032315, 5000, 1, 1), # Cynical Orange Mushroom | Cynical Orange Mushroom Puppet
    (9300274, 4033719, 5000, 1, 1), # Cynical Orange Mushroom | Cynical Orange Mushroom Puppet
    (9300274, 4034062, 5000, 1, 1), # Cynical Orange Mushroom | Cynical Orange Mushroom Puppet
    (2230115, 4000673, 5000, 1, 1), # Muddy Sprout Monster | Grassy Mud Clump
    (4230102, 4033039, 1000, 1, 1), # Wraith | Broken Mirror Glass
    (8800400, 4033785, 5000, 1, 1), # Tarantulus | Demonic Rune
    (9600310, 4034654, 5000, 1, 1), # Bicycle Ghost | Tomo's Lost Backpack
    (9600311, 4034654, 5000, 1, 1), # Sign Ghost | Tomo's Lost Backpack
    (6230100, 4033148, 100, 1, 1), # Wild Kargo | Gwin's Bag
    (7130100, 4033149, 100, 1, 1), # Tauromacis | Key to the Forgotten Shrine
    (7130101, 4033149, 100, 1, 1), # Taurospear | Key to the Forgotten Shrine
    (5130100, 4033174, 100, 1, 1), # Drake | Ronnie's Marble
    (6130100, 4033174, 100, 1, 1), # Red Drake | Ronnie's Marble
    (6230600, 4033174, 100, 1, 1), # Ice Drake | Ronnie's Marble
    (6230601, 4033174, 100, 1, 1), # Dark Drake | Ronnie's Marble
    (6230600, 4031212, 1000, 1, 1), # Ice Drake | Cold Steam
    (6230100, 4031213, 500, 1, 1), # Wild Kargo | Wild Kargo's Spirit Rock
    (7130100, 4031214, 500, 1, 1), # Tauromacis | Tauromacis's Spirit Rock
    (7130101, 4031215, 500, 1, 1), # Taurospear | Taurospear's Spirit Rock
    (4130100, 4031015, 100, 1, 1), # Copper Drake | Fresh Milk
    (6130100, 4001006, 100, 1, 1), # Red Drake | Flaming Feather
    (3210200, 4001006, 100, 1, 1), # Jr. Cellion | Flaming Feather
    (6230600, 4003002, 100, 1, 1), # Ice Drake | Piece of Ice
    (6130103, 4003002, 100, 1, 1), # Pepe | Piece of Ice
    (7130100, 4001005, 100, 1, 1), # Tauromacis | Ancient Scroll
    (9400016, 4000066, 5000, 1, 1), # Field Fox | Fox Tail
    (2400104, 4033576, 5000, 1, 1), # Dinoram | Dinoram Tenderloin
    (9400548, 4036626, 5000, 1, 1), # Mighty Maple Eater | Flora Sample
    (2100100, 4031577, 5000, 1, 1), # White Hare | Fairy's Tea Leaf
    (2100101, 4031577, 5000, 1, 1), # Brown Hare | Fairy's Tea Leaf
    (6130209, 4000289, 1000, 1, 1), # Sage Cat | Cat Doll
    (7220002, 4000289, 5000, 1, 1), # King Sage Cat | Cat Doll
    (9300880, 4000289, 5000, 1, 1), # King Sage Cat | Cat Doll
    (7220002, 4031789, 10000, 1, 1), # King Sage Cat | Antidote Marble
    (9300880, 4031789, 10000, 1, 1), # King Sage Cat | Antidote Marble
    (5120506, 4000299, 500, 1, 1), # The Book Ghost | The Book Ghost's Sheet of Paper
    (5120506, 4000298, 1000, 1, 1), # The Book Ghost | Old Paper
    (6130209, 4031433, 100, 1, 1), # Sage Cat | Kitty Spirit
    (6130207, 4000282, 5000, 1, 1), # Peach Monkey | Peach Seed
    (5120505, 4000288, 1000, 1, 1), # Cute Reindeer | Broken Deer Horn
    (9450024, 4034126, 5000, 1, 1), # Seasoned Oda Warrior | 100 Spells for the Serious Soldier
    (9450025, 4034126, 5000, 1, 1), # Seasoned Oda Scout | 100 Spells for the Serious Soldier
    (8620001, 4000828, 5000, 1, 1), # Swollen Dark Stump | Giant Firewood
    (8620002, 4000828, 5000, 1, 1), # Swollen Axe Stump | Giant Firewood
    (2230105, 4031259, 5000, 1, 1), # Seacle | Seacle's DNA Sample
    (2230106, 4031260, 5000, 1, 1), # Cico | Cico's DNA Sample
    (2230108, 4031261, 5000, 1, 1), # Pinboom | Pin Boom's DNA Sample
    (2230200, 4031262, 5000, 1, 1), # Flower Fish | Flower Fish's DNA Sample
    (3230104, 4031263, 5000, 1, 1), # Mask Fish | Masked Fish's DNA Sample
    (2230109, 4031264, 5000, 1, 1), # Bubble Fish | Bubble Fish's DNA Sample
    (8150100, 4031254, 100, 1, 1), # Shark | Broken Flashlight
    (8150101, 4031254, 100, 1, 1), # Cold Shark | Broken Flashlight
    (7130003, 4000237, 5000, 1, 1), # Dual Beetle | Dual Beetle's Horn
    (7130501, 4031412, 1000, 1, 1), # Dark Rash | Black Soul of Dark Rash
    (8150201, 4031414, 100, 1, 1), # Dark Cornian | Black Soul of Dark Cornian
    (8140111, 4031413, 100, 1, 1), # Dual Birk | Black Soul of Dual Birk
    (8140112, 4031413, 100, 1, 1), # [*]Dual Birk | Black Soul of Dual Birk
    (8150302, 4031415, 100, 1, 1), # Dark Wyvern | Black Soul of Dark Wyvern
    (8150303, 4031415, 100, 1, 1), # [*]Dark Wyvern | Black Soul of Dark Wyvern
    (9400023, 4000984, 5000, 1, 1), # Sleepy Ghost | Kimono Fragment
    (9400100, 3996002, 10000, 1, 1), # Yakuza Soldier | Yakuza Soldier's Shoes
    (9400101, 3996003, 10000, 1, 1), # Yakuza Grunt | Yakuza Grunt's Girdle
    (9400102, 3996004, 10000, 1, 1), # Yakuza Bruiser | Yakuza Bruiser's Baseball Bat
    (9400103, 3996005, 10000, 1, 1), # Yakuza Enforcer | Yakuza Enforcer's Jacket
    (9400300, 3996006, 10000, 1, 1), # Yakuza Boss | Fire Raccoon Finance's Secret Ledger
    (9400103, 3994111, 10000, 1, 1), # Yakuza Enforcer | Fire Raccoon Map Fragment
    (9400102, 3994112, 10000, 1, 1), # Yakuza Bruiser | Fire Raccoon Map Fragment
    (9400110, 3994113, 10000, 1, 1), # Yakuza Lieutenant | Fire Raccoon Map Fragment
    (9400100, 1002418, 200, 1, 1), # Yakuza Soldier | Newspaper Hat
    (9400110, 1002418, 200, 1, 1), # Yakuza Lieutenant | Newspaper Hat
    (9400121, 4000138, 10000, 1, 1), # Yakuza Underboss | Yakuza Underboss's Comb
    (9400121, 1072239, 300, 1, 1), # Yakuza Underboss | Yellow Snowshoes
    (9400112, 4000139, 10000, 1, 1), # Yakuza Bodyguard | Yakuza Bodyguard's Tie Clip
    (9400113, 2880549, 1000, 1, 1), # Yakuza Protector | Yakuza Protector Familiar
    (9400113, 4000140, 10000, 1, 1), # Yakuza Protector | Yakuza Protector's Bullet Shell
    (9400300, 4000141, 10000, 1, 1), # Yakuza Boss | Yakuza Boss's Lantern
    (9400300, 1072238, 1000, 1, 1), # Yakuza Boss | Violet Snowshoes
    (9400300, 2631423, 500, 1, 1), # Yakuza Boss | Showa Town Damage Skin
    (9400300, 2631513, 500, 1, 1), # Yakuza Boss | Fire Raccoon Damage Skin
    (9400019, 4000224, 10000, 1, 1), # Tengu's Shadow | Kimono Shoes
    (9300103, 4031507, 1000, 1, 1), # Barnard Gray | Pheromone
    (9300104, 4031507, 1000, 1, 1), # Zeta Gray | Pheromone
    (7130601, 4000261, 1000, 1, 1), # Green Hobi | Pin Hov's Charm
    (8140700, 4000262, 1000, 1, 1), # Blue Dragon Turtle | Cracked Shell
    (2100108, 4031568, 100, 1, 1), # Meerkat | Cat's Eye
    (2150001, 4000602, 1000, 1, 1), # Dust Box | Dust Ball
    (2150001, 4032754, 100, 1, 1), # Dust Box | Black Plastic Bag
    (3000005, 4000106, 1000, 1, 1), # Brown Teddy | Teddy's Cotton
    (3110101, 4000107, 1000, 1, 1), # Pink Teddy | Teddy's Yellow Ribbon
    (3110300, 4000353, 1000, 1, 1), # Cube Slime | Gelatin
    (3110302, 4000356, 1000, 1, 1), # Rumo | Flask
    (3110302, 4031694, 500, 1, 1), # Rumo | Lumo's Leaf
    (3110303, 4000356, 1000, 1, 1), # Triple Rumo | Flask
    (3110303, 4031694, 1000, 1, 1), # Triple Rumo | Lumo's Leaf
    (3150000, 4000605, 1000, 1, 1), # Safety First | Safety First Sign
    (3150000, 4032762, 100, 1, 1), # Safety First | Ulrika's Photo
    (3150000, 4032764, 1000, 1, 1), # Safety First | Rue Battery
    (3150000, 4033676, 1000, 1, 1), # Safety First | Working Rue Battery
    (3150001, 4000606, 1000, 1, 1), # Baby Boulder Muncher | Boulder Fragment
    (3150001, 4032765, 500, 1, 1), # Baby Boulder Muncher | Rock Shield
    (3150002, 4000607, 1000, 1, 1), # Big Boulder Muncher | Fragmented ore
    (3210203, 4000108, 1000, 1, 1), # Panda Teddy | Panda Doll
    (3210204, 4000109, 1000, 1, 1), # Roloduck | Toy Duckling
    (3210207, 2022354, 100, 1, 1), # Tick | Tick-Tock's Egg
    (3210207, 4000113, 1000, 1, 1), # Tick | Clock Spring
    (3210207, 4000114, 1000, 1, 1), # Tick | Table Clock
    (3210207, 4031992, 500, 1, 1), # Tick | Springy Worm
    (3230305, 4000110, 1000, 1, 1), # Toy Trojan | Toy Trojan Sword
    (3230306, 2022355, 100, 1, 1), # Chronos | Chronos's Egg
    (3230306, 4000115, 1000, 1, 1), # Chronos | Cog
    (3230306, 4031992, 100, 1, 1), # Chronos | Springy Worm
    (3230308, 4000116, 1000, 1, 1), # Tweeter | Small Egg
    (4110302, 4000359, 1000, 1, 1), # Mithril Mutae | Piece of Mithril
    (4130100, 4000014, 1000, 1, 1), # Copper Drake | Drake Skull
    (4130100, 4000030, 500, 1, 1), # Copper Drake | Dragon Skin
    (4230111, 4000111, 1000, 1, 1), # Robo | Cheap Battery
    (4230112, 4000112, 1000, 1, 1), # Master Robo | Mechanical Heart
    (4230112, 4031130, 100, 1, 1), # Master Robo | Maintenance Manual
    (4230113, 2022354, 100, 1, 1), # Tick-Tock | Tick-Tock's Egg
    (4230113, 4000114, 1000, 1, 1), # Tick-Tock | Table Clock
    (4230113, 4031098, 100, 1, 1), # Tick-Tock | All-purpose Clock Spring
    (4230113, 4031992, 500, 1, 1), # Tick-Tock | Springy Worm
    (4230114, 2022355, 100, 1, 1), # Platoon Chronos | Chronos's Egg
    (4230114, 4000115, 1000, 1, 1), # Platoon Chronos | Cog
    (4230114, 4031992, 100, 1, 1), # Platoon Chronos | Springy Worm
    (4230115, 2022355, 100, 1, 1), # Master Chronos | Chronos's Egg
    (4230115, 4000115, 1000, 1, 1), # Master Chronos | Cog
    (4230115, 4031992, 100, 1, 1), # Master Chronos | Springy Worm
    (4230506, 4000292, 1000, 1, 1), # Ginseng Jar | Ginseng-Boiled Water
    (4230506, 4032908, 500, 1, 1), # Ginseng Jar | Ripped Scroll Piece
    (5110300, 4000360, 1000, 1, 1), # Reinforced Mithril Mutae | Hardened Piece of Mithril
    (5110302, 4000365, 1000, 1, 1), # Neo Huroid | Plug
    (5120001, 4031050, 100, 1, 1), # Cellion | Cracked Black Crystal
    (5120002, 4031050, 100, 1, 1), # Lioner | Cracked Black Crystal
    (5120003, 4031050, 100, 1, 1), # Grupin | Cracked Black Crystal
    (5120501, 4000293, 100, 1, 1), # Bellflower Root | Bellflower
    (5120503, 4000286, 1000, 1, 1), # Straw Target Dummy | Straw Doll
    (5120504, 4000287, 1000, 1, 1), # Wooden Target Dummy | Wooden Doll
    (5120505, 4000021, 500, 1, 1), # Cute Reindeer | Leather
    (5120506, 4031432, 100, 1, 1), # The Book Ghost | The Legendary Being's Scroll <Advanced>
    (5130100, 2012000, 500, 1, 1), # Drake | Drake's Blood
    (5130100, 2012003, 500, 1, 1), # Drake | Drake's Meat
    (5130100, 4000014, 1000, 1, 1), # Drake | Drake Skull
    (5130100, 4000030, 500, 1, 1), # Drake | Dragon Skin
    (6090004, 4000419, 10000, 1, 1), # Rurumo | Purple Liquid
    (6110301, 4000363, 1000, 1, 1), # Saitie | Entry Pass
    (6110301, 4031745, 100, 1, 1), # Saitie | Parwen's Entry Pass
    (6130100, 4000014, 1000, 1, 1), # Red Drake | Drake Skull
    (6130100, 4000030, 500, 1, 1), # Red Drake | Dragon Skin
    (6130203, 4000021, 500, 1, 1), # Panda | Leather
    (6130203, 4000283, 500, 1, 1), # Panda | Bear Foot
    (6130203, 4000285, 1000, 1, 1), # Panda | Red Belt
    (6130204, 4000021, 500, 1, 1), # Mr. Alli | Leather
    (6130204, 4000295, 1000, 1, 1), # Mr. Alli | Mr. Alli's Leather
    (6130207, 4000021, 500, 1, 1), # Peach Monkey | Leather
    (6130208, 4000296, 1000, 1, 1), # Kru | Mark of the Pirate
    (6110300, 4000354, 1000, 1, 1), # Homun | Beaker
    (6130200, 4000128, 1000, 1, 1), # [*]Buffy | Buffy Hat
    (6130200, 4034181, 100, 1, 1), # [*]Buffy | Dispersed Star Fragment
    (6230300, 4000129, 1000, 1, 1), # [*]Lazy Buffy | Lazy Buffy Marble
    (6230300, 4031195, 100, 1, 1), # [*]Lazy Buffy | Aurora Marble
    (6230300, 4034181, 100, 1, 1), # [*]Lazy Buffy | Dispersed Star Fragment
    (6230400, 4000143, 1000, 1, 1), # [*]Soul Teddy | Zombie Teddy Bear
    (6230400, 4034181, 100, 1, 1), # [*]Soul Teddy | Dispersed Star Fragment
    (6230500, 4000144, 1000, 1, 1), # [*]Master Soul Teddy | Free Spirit
    (6230500, 4034181, 100, 1, 1), # [*]Master Soul Teddy | Dispersed Star Fragment
    (6230600, 4000030, 500, 1, 1), # Ice Drake | Dragon Skin
    (6230600, 4000185, 1000, 1, 1), # Ice Drake | Ice Backbone
    (6230601, 4000030, 500, 1, 1), # Dark Drake | Dragon Skin
    (6230601, 4000186, 1000, 1, 1), # Dark Drake | Dark Drake's Horn
    (6230100, 4000027, 1000, 1, 1), # Wild Kargo | Wild Kargo Eye
    (6300100, 4000130, 1000, 1, 1), # [*]Buffoon | Buffoon's Grandpa Clock
    (6300100, 4034181, 100, 1, 1), # [*]Buffoon | Dispersed Star Fragment
    (6400100, 4000131, 1000, 1, 1), # [*]Deep Buffoon | Deep Buffoon's Rock Piece
    (6400100, 4034181, 100, 1, 1), # [*]Deep Buffoon | Dispersed Star Fragment
    (7090000, 4000411, 10000, 1, 1), # Security Camera | Blacklist
    (7110300, 4000362, 10000, 1, 1), # D. Roy | Broken Mechanical Heart
    (7110300, 4031741, 10000, 1, 1), # D. Roy | Magic Stone of Trust
    (7130002, 4000236, 1000, 1, 1), # Beetle | Beetle's Horn
    (7130003, 4000237, 1000, 1, 1), # Dual Beetle | Dual Beetle's Horn
    (7130004, 4000231, 1000, 1, 1), # Hankie | Hankie's Pan Flute
    (7130010, 4000147, 1000, 1, 1), # [*]Death Teddy | Sealed Teddy Bear
    (7130010, 4034181, 100, 1, 1), # [*]Death Teddy | Dispersed Star Fragment
    (7130100, 4000028, 1000, 1, 1), # Tauromacis | Tauromacis Horn
    (7130100, 4033149, 100, 1, 1), # Tauromacis | Key to the Forgotten Shrine
    (7130101, 4000046, 1000, 1, 1), # Taurospear | Taurospear Horn
    (7130101, 4033149, 100, 1, 1), # Taurospear | Key to the Forgotten Shrine
    (7130104, 4000297, 1000, 1, 1), # Captain | Captain's Hat
    (7110301, 4000361, 1000, 1, 1), # Homunculus | May Mist
    (7130300, 4000148, 1000, 1, 1), # [*]Master Death Teddy | Binding Bridle
    (7130300, 4034181, 100, 1, 1), # [*]Master Death Teddy | Dispersed Star Fragment
    (7130500, 4000226, 1000, 1, 1), # Rash | Rash's Furball
    (7130500, 4000227, 1000, 1, 1), # Rash | Tree Fruit
    (7130500, 4000228, 1000, 1, 1), # Rash | Anesthetic Powder
    (7130501, 4000229, 1000, 1, 1), # Dark Rash | Dark Rash's Furball
    (7130501, 4000230, 1000, 1, 1), # Dark Rash | Curse Powder
    (7130600, 4000260, 1000, 1, 1), # Hobi | Hov's Shorts
    (7140000, 4000132, 1000, 1, 1), # [*]Ghost Pirate | Ghost Pirate Key
    (7140000, 4034181, 100, 1, 1), # [*]Ghost Pirate | Dispersed Star Fragment
    (7150000, 4000609, 1000, 1, 1), # Racoco | Racoco's Shovel
    (7150001, 4000610, 1000, 1, 1), # Big Spider | Big Spider Antenna
    (7150001, 4034074, 1000, 1, 1), # Big Spider | Bedrock Fragment
    (7150002, 4000611, 1000, 1, 1), # Cart Bear | Cart Bear's Cart
    (7150002, 4032770, 1000, 1, 1), # Cart Bear | Cart Bear Meat
    (7150003, 4000612, 1000, 1, 1), # Racaroni | Racaroni Tail
    (7150003, 4032771, 500, 1, 1), # Racaroni | Racaroni Heart
    (7150004, 4000613, 1000, 1, 1), # Guard Robot L | Guard Robot L's Electric Racket
    (7160000, 4000133, 1000, 1, 1), # [*]Dual Ghost Pirate | Dual Pirate's Propeller
    (7160000, 4034181, 100, 1, 1), # [*]Dual Ghost Pirate | Dispersed Star Fragment
    (7220000, 4000283, 10000, 1, 1), # Tae Roon | Bear Foot
    (7220000, 4000284, 10000, 1, 1), # Tae Roon | Yellow Belt
    (7220000, 4000285, 10000, 1, 1), # Tae Roon | Red Belt
    (7220000, 2510260, 100, 1, 1), # Tae Roon | Infinite Throwing-Knives Recipe
    (7220002, 4000298, 10000, 1, 1), # King Sage Cat | Old Paper
    (7220002, 4310029, 10000, 1, 1), # King Sage Cat | Crusader Coins
    (8090000, 4000418, 10000, 1, 1), # Deet and Roi | Useless Mechanical Heart
    (8090000, 4310029, 10000, 1, 1), # Deet and Roi | Crusader Coins
    (8105000, 4000614, 1000, 1, 1), # Raco | Raco's Safety Hat
    (8105000, 4032772, 500, 1, 1), # Raco | Raco Heart
    (8105001, 4000615, 1000, 1, 1), # Security System | Outer Handle
    (8105002, 4000616, 1000, 1, 1), # Enhanced Security System | Vent Pipe
    (8105003, 4000617, 1000, 1, 1), # AF Android | Android Bulb
    (8105004, 4000618, 1000, 1, 1), # Broken DF Android | Broken Parts
    (8105005, 4000619, 1000, 1, 1), # Ore Muncher | Rue Ore
    (8105005, 4032776, 500, 1, 1), # Ore Muncher | Ore Muncher DNA Sample
    (8110300, 4000355, 10000, 1, 1), # Homunscullo | Homunculus's Sand
    (8110300, 4031737, 10000, 1, 1), # Homunscullo | Homunculus's Blood
    (8110300, 4031740, 10000, 1, 1), # Homunscullo | Magic Stone of Honesty
    (8140001, 4000238, 5000, 1, 1), # Harp | Harp's Tail Feather
    (8140002, 4000239, 1000, 1, 1), # Blood Harp | Blood Harp's Crown
    (8140002, 4000240, 500, 1, 1), # Blood Harp | Small Flaming Feather
    (8140003, 2870314, 100, 1, 1), # [*]Blood Harp | Blood Harp Familiar
    (8140003, 4000239, 1000, 1, 1), # [*]Blood Harp | Blood Harp's Crown
    (8140003, 4000240, 500, 1, 1), # [*]Blood Harp | Small Flaming Feather
    (8140003, 4034180, 100, 1, 1), # [*]Blood Harp | Vanished Star Fragment
    (8140101, 4000234, 1000, 1, 1), # Black Kentaurus | Kentaurus's Skull
    (8140102, 4000232, 1000, 1, 1), # Red Kentaurus | Kentaurus's Flame
    (8140103, 4000233, 1000, 1, 1), # Blue Kentaurus | Kentaurus's Marrow
    (8140104, 4000234, 1000, 1, 1), # [*]Black Kentaurus | Kentaurus's Skull
    (8140104, 4034180, 100, 1, 1), # [*]Black Kentaurus | Vanished Star Fragment
    (8140105, 4000232, 1000, 1, 1), # [*]Red Kentaurus | Kentaurus's Flame
    (8140105, 4034180, 100, 1, 1), # [*]Red Kentaurus | Vanished Star Fragment
    (8140106, 4000233, 100, 1, 1), # [*]Blue Kentaurus | Kentaurus's Marrow
    (8140106, 4034180, 100, 1, 1), # [*]Blue Kentaurus | Vanished Star Fragment
    (8140110, 4000241, 1000, 1, 1), # Birk | Birk's Chewed Grass
    (8140111, 4000242, 1000, 1, 1), # Dual Birk | Dual Birk's Tiny Tail
    (8140112, 4000242, 100, 1, 1), # [*]Dual Birk | Dual Birk's Tiny Tail
    (8140112, 4034180, 100, 1, 1), # [*]Dual Birk | Vanished Star Fragment
    (8140200, 4000145, 100, 1, 1), # [*]Klock | Sealed-up Grandpa Clock
    (8140200, 4034181, 100, 1, 1), # [*]Klock | Dispersed Star Fragment
    (8140300, 4000146, 100, 1, 1), # [*]Dark Klock | Evil Spirit
    (8140300, 4034181, 100, 1, 1), # [*]Dark Klock | Dispersed Star Fragment
    (8140700, 4000244, 500, 1, 1), # Blue Dragon Turtle | Dragon Spirit
    (8140700, 4000245, 500, 1, 1), # Blue Dragon Turtle | Dragon Scale
    (8140701, 4000244, 500, 1, 1), # Red Dragon Turtle | Dragon Spirit
    (8140701, 4000245, 500, 1, 1), # Red Dragon Turtle | Dragon Scale
    (8140701, 4000263, 1000, 1, 1), # Red Dragon Turtle | Red Shell
    (8140702, 4000244, 500, 1, 1), # Rexton | Dragon Spirit
    (8140702, 4000245, 500, 1, 1), # Rexton | Dragon Scale
    (8140702, 4000264, 1000, 1, 1), # Rexton | Rexton Leather
    (8140703, 4000244, 500, 1, 1), # Brexton | Dragon Spirit
    (8140703, 4000245, 500, 1, 1), # Brexton | Dragon Scale
    (8140703, 4000265, 1000, 1, 1), # Brexton | Strange Egg
    (8141000, 4000134, 1000, 1, 1), # [*]Spirit Viking | Viking Sail
    (8141000, 4034181, 100, 1, 1), # [*]Spirit Viking | Dispersed Star Fragment
    (8141100, 4000135, 1000, 1, 1), # [*]Gigantic Spirit Viking | Gigantic Viking Hat
    (8141100, 4034181, 100, 1, 1), # [*]Gigantic Spirit Viking | Dispersed Star Fragment
    (8142000, 4000149, 1000, 1, 1), # [*]Phantom Watch | Sealed Bottle
    (8142000, 4034181, 100, 1, 1), # [*]Phantom Watch | Dispersed Star Fragment
    (8143000, 4000150, 1000, 1, 1), # [*]Grim Phantom Watch | Ice Piece
    (8143000, 4034181, 100, 1, 1), # [*]Grim Phantom Watch | Dispersed Star Fragment
    (8150000, 2512047, 100, 1, 1), # Crimson Balrog | Special Crimson Balrog Transform Potion Recipe
    (8150000, 2870335, 100, 1, 1), # Crimson Balrog | Crimson Balrog Familiar
    (8150000, 4032935, 10000, 1, 1), # Crimson Balrog | Sunny's Gloves
    (8150200, 4000244, 500, 1, 1), # Green Cornian | Dragon Spirit
    (8150200, 4000245, 500, 1, 1), # Green Cornian | Dragon Scale
    (8150200, 4000266, 1000, 1, 1), # Green Cornian | Wooden Shoulder Pad
    (8150201, 4000244, 500, 1, 1), # Dark Cornian | Dragon Spirit
    (8150201, 4000245, 500, 1, 1), # Dark Cornian | Dragon Scale
    (8150201, 4000267, 1000, 1, 1), # Dark Cornian | Skull Shoulder Pad
    (8150300, 4000244, 500, 1, 1), # Red Wyvern | Dragon Spirit
    (8150300, 4000245, 500, 1, 1), # Red Wyvern | Dragon Scale
    (8150300, 4000268, 1000, 1, 1), # Red Wyvern | Wyvern Wing
    (8150301, 4000244, 500, 1, 1), # Blue Wyvern | Dragon Spirit
    (8150301, 4000245, 500, 1, 1), # Blue Wyvern | Dragon Scale
    (8150301, 4000269, 1000, 1, 1), # Blue Wyvern | Wyvern Gill
    (8150302, 4000244, 500, 1, 1), # Dark Wyvern | Dragon Spirit
    (8150302, 4000245, 500, 1, 1), # Dark Wyvern | Dragon Scale
    (8150302, 4000270, 1000, 1, 1), # Dark Wyvern | Wyvern Toenail
    (8150303, 4000244, 500, 1, 1), # [*]Dark Wyvern | Dragon Spirit
    (8150303, 4000245, 500, 1, 1), # [*]Dark Wyvern | Dragon Scale
    (8150303, 4000270, 1000, 1, 1), # [*]Dark Wyvern | Wyvern Toenail
    (8150303, 4034182, 100, 1, 1), # [*]Dark Wyvern | Smashed Star Fragment
    (8170000, 4034181, 100, 1, 1), # [*]Thanatos | Dispersed Star Fragment
    (8180000, 4000235, 10000, 1, 1), # Manon | Manon's Tail
    (8180000, 4000244, 10000, 1, 1), # Manon | Dragon Spirit
    (8180000, 4000245, 10000, 1, 1), # Manon | Dragon Scale
    (8180000, 4310029, 10000, 1, 1), # Manon | Crusader Coins
    (8180001, 4000243, 10000, 1, 1), # Griffey | Griffey Horn
    (8180001, 4000244, 10000, 1, 1), # Griffey | Dragon Spirit
    (8180001, 4000245, 10000, 1, 1), # Griffey | Dragon Scale
    (8180001, 4310029, 10000, 1, 1), # Griffey | Crusader Coins
    (8190000, 4000244, 500, 1, 1), # Jr. Newtie | Dragon Spirit
    (8190000, 4000245, 500, 1, 1), # Jr. Newtie | Dragon Scale
    (8190000, 4000272, 1000, 1, 1), # Jr. Newtie | Egg Shell
    (8190002, 4000244, 500, 1, 1), # Nest Golem | Dragon Spirit
    (8190002, 4000245, 500, 1, 1), # Nest Golem | Dragon Scale
    (8190002, 4000271, 1000, 1, 1), # Nest Golem | Destroyed Nest
    (8190003, 4000244, 500, 1, 1), # Skelegon | Dragon Spirit
    (8190003, 4000245, 500, 1, 1), # Skelegon | Dragon Scale
    (8190003, 4000274, 1000, 1, 1), # Skelegon | Broken Horn
    (8190004, 4000244, 500, 1, 1), # Skelosaurus | Dragon Spirit
    (8190004, 4000245, 500, 1, 1), # Skelosaurus | Dragon Scale
    (8190004, 4000273, 1000, 1, 1), # Skelosaurus | Old Neck Bone
    (8190005, 4000244, 500, 1, 1), # Nest Golem | Dragon Spirit
    (8190005, 4000245, 500, 1, 1), # Nest Golem | Dragon Scale
    (8190005, 4000271, 1000, 1, 1), # Nest Golem | Destroyed Nest
    (8190006, 4000244, 500, 1, 1), # [*]Skelegon | Dragon Spirit
    (8190006, 4000245, 500, 1, 1), # [*]Skelegon | Dragon Scale
    (8190006, 4000274, 1000, 1, 1), # [*]Skelegon | Broken Horn
    (8190006, 4034182, 100, 1, 1), # [*]Skelegon | Smashed Star Fragment
    (8190007, 4000244, 500, 1, 1), # [*]Skelosaurus | Dragon Spirit
    (8190007, 4000245, 500, 1, 1), # [*]Skelosaurus | Dragon Scale
    (8190007, 4000273, 1000, 1, 1), # [*]Skelosaurus | Old Neck Bone
    (8190007, 4034182, 100, 1, 1), # [*]Skelosaurus | Smashed Star Fragment
    (8200000, 4000443, 1000, 1, 1), # Eye of Time | Cracked Hourglass
    (8200000, 4009096, 100, 1, 1), # Eye of Time | Piece of Time
    (8200000, 4032972, 100, 1, 1), # Eye of Time | Hourglass of Time
    (8200001, 4000444, 1000, 1, 1), # Memory Monk | Green Cloth
    (8200001, 4000446, 500, 1, 1), # Memory Monk | Smiley Mask
    (8200001, 4009096, 100, 1, 1), # Memory Monk | Piece of Time
    (8200001, 4033171, 500, 1, 1), # Memory Monk | Shard of Recollection
    (8200002, 4000445, 1000, 1, 1), # Memory Monk Trainee | Green Cone Hat
    (8200002, 4000446, 500, 1, 1), # Memory Monk Trainee | Smiley Mask
    (8200002, 4009096, 100, 1, 1), # Memory Monk Trainee | Piece of Time
    (8200002, 4033171, 500, 1, 1), # Memory Monk Trainee | Shard of Recollection
    (8200003, 4000447, 1000, 1, 1), # Memory Guardian | Green Helmet
    (8200003, 4000459, 500, 1, 1), # Memory Guardian | Black Armor Fragment
    (8200003, 4009096, 100, 1, 1), # Memory Guardian | Piece of Time
    (8200003, 4033172, 500, 1, 1), # Memory Guardian | Piece of Memory
    (8200004, 4000448, 1000, 1, 1), # Chief Memory Guardian | Green Heart
    (8200004, 4000459, 500, 1, 1), # Chief Memory Guardian | Black Armor Fragment
    (8200004, 4009096, 100, 1, 1), # Chief Memory Guardian | Piece of Time
    (8200004, 4033172, 100, 1, 1), # Chief Memory Guardian | Piece of Memory
    (8200005, 4000449, 1000, 1, 1), # Qualm Monk | Blue Cloth
    (8200005, 4000451, 500, 1, 1), # Qualm Monk | Neutral Mask
    (8200005, 4009096, 100, 1, 1), # Qualm Monk | Piece of Time
    (8200005, 4033171, 100, 1, 1), # Qualm Monk | Shard of Recollection
    (8200006, 4000450, 1000, 1, 1), # Qualm Monk Trainee | Blue Cone Hat
    (8200006, 4000451, 500, 1, 1), # Qualm Monk Trainee | Neutral Mask
    (8200006, 4009096, 100, 1, 1), # Qualm Monk Trainee | Piece of Time
    (8200006, 4033171, 100, 1, 1), # Qualm Monk Trainee | Shard of Recollection
    (8200007, 4000452, 1000, 1, 1), # Qualm Guardian | Blue Helmet
    (8200007, 4000459, 500, 1, 1), # Qualm Guardian | Black Armor Fragment
    (8200007, 4009096, 100, 1, 1), # Qualm Guardian | Piece of Time
    (8200007, 4033172, 100, 1, 1), # Qualm Guardian | Piece of Memory
    (8200008, 4000453, 1000, 1, 1), # Chief Qualm Guardian | Blue Heart
    (8200008, 4000459, 500, 1, 1), # Chief Qualm Guardian | Black Armor Fragment
    (8200008, 4009096, 100, 1, 1), # Chief Qualm Guardian | Piece of Time
    (8200008, 4033172, 100, 1, 1), # Chief Qualm Guardian | Piece of Memory
    (8200009, 4000454, 1000, 1, 1), # Oblivion Monk | Red Cloth
    (8200009, 4000456, 500, 1, 1), # Oblivion Monk | Frowny Mask
    (8200009, 4009096, 100, 1, 1), # Oblivion Monk | Piece of Time
    (8200009, 4033171, 100, 1, 1), # Oblivion Monk | Shard of Recollection
    (8200010, 4000455, 1000, 1, 1), # Oblivion Monk Trainee | Red Cone Hat
    (8200010, 4000456, 500, 1, 1), # Oblivion Monk Trainee | Frowny Mask
    (8200010, 4009096, 100, 1, 1), # Oblivion Monk Trainee | Piece of Time
    (8200010, 4033171, 100, 1, 1), # Oblivion Monk Trainee | Shard of Recollection
    (8200011, 4000457, 1000, 1, 1), # Oblivion Guardian | Red Helmet
    (8200011, 4000459, 500, 1, 1), # Oblivion Guardian | Black Armor Fragment
    (8200011, 4009096, 100, 1, 1), # Oblivion Guardian | Piece of Time
    (8200011, 4033172, 100, 1, 1), # Oblivion Guardian | Piece of Memory
    (8200012, 4000458, 1000, 1, 1), # Chief Oblivion Guardian | Red Heart
    (8200012, 4000459, 500, 1, 1), # Chief Oblivion Guardian | Black Armor Fragment
    (8200012, 4009096, 100, 1, 1), # Chief Oblivion Guardian | Piece of Time
    (8200012, 4033172, 100, 1, 1), # Chief Oblivion Guardian | Piece of Memory
    (8220002, 2870365, 100, 1, 1), # Chimera | Chimera Familiar
    (8220002, 4000356, 10000, 1, 1), # Chimera | Flask
    (8220002, 4000364, 10000, 1, 1), # Chimera | Wires
    (8220002, 4000365, 10000, 1, 1), # Chimera | Plug
    (8220004, 4009096, 10000, 1, 1), # Dodo | Piece of Time
    (8220005, 4009096, 100, 1, 1), # Lilynouch | Piece of Time
    (8220006, 4009096, 100, 1, 1), # Lyka | Piece of Time
    (8220038, 4000443, 1000, 1, 1), # [*] Eye of Time | Cracked Hourglass
    (8220038, 4009096, 100, 1, 1), # [*] Eye of Time | Piece of Time
    (8220038, 4032972, 100, 1, 1), # [*] Eye of Time | Hourglass of Time
    (8220039, 4000454, 1000, 1, 1), # [*] Oblivion Monk | Red Cloth
    (8220039, 4000456, 500, 1, 1), # [*] Oblivion Monk | Frowny Mask
    (8220039, 4009096, 100, 1, 1), # [*] Oblivion Monk | Piece of Time
    (8220039, 4033171, 100, 1, 1), # [*] Oblivion Monk | Shard of Recollection
    (8220040, 4000455, 1000, 1, 1), # [*] Oblivion Monk Trainee | Red Cone Hat
    (8220040, 4000456, 500, 1, 1), # [*] Oblivion Monk Trainee | Frowny Mask
    (8220040, 4009096, 100, 1, 1), # [*] Oblivion Monk Trainee | Piece of Time
    (8220040, 4033171, 100, 1, 1), # [*] Oblivion Monk Trainee | Shard of Recollection
    (8220041, 4000457, 1000, 1, 1), # [*] Oblivion Guardian | Red Helmet
    (8220041, 4000459, 500, 1, 1), # [*] Oblivion Guardian | Black Armor Fragment
    (8220041, 4009096, 100, 1, 1), # [*] Oblivion Guardian | Piece of Time
    (8220041, 4033172, 100, 1, 1), # [*] Oblivion Guardian | Piece of Memory
    (8220042, 4000458, 1000, 1, 1), # [*] Chief Oblivion Guardian | Red Heart
    (8220042, 4000459, 100, 1, 1), # [*] Chief Oblivion Guardian | Black Armor Fragment
    (8220042, 4009096, 100, 1, 1), # [*] Chief Oblivion Guardian | Piece of Time
    (8220042, 4033172, 100, 1, 1), # [*] Chief Oblivion Guardian | Piece of Memory
    (9601255, 4036628, 2000, 1, 1), # Violent Jungle Monkey | Soil Sample
    (9601294, 4036627, 2000, 1, 1), # Aggressive Gryphon | Ecological Sample
    (9400805, 4009481, 100, 1, 1), # Jr. Alien Cog | Refined Lianium
    (9400807, 4009481, 100, 1, 1), # Jr. Alien Miner | Refined Lianium
    (9309207, 1382234, 500, 1, 1), # Dorothy | Alicia's Mutated Staff
    (9309207, 1004075, 500, 1, 1), # Dorothy | Aquarius Crown
    (9309200, 1004075, 100, 1, 1), # Cowardly Lion | Aquarius Crown
    (9309203, 1004075, 100, 1, 1), # Scarecrow | Aquarius Crown
    (8850111, 4021019, 10000, 3, 3), # Cygnus | Dream Stone
    (8850011, 4021019, 10000, 10, 10), # Cygnus | Dream Stone
    (8600000, 4020013, 100, 1, 1), # Mutant Snail | Dream Fragment
    (8600004, 4020013, 100, 1, 1), # Mutant Tino | Dream Fragment
    (8600001, 4020013, 100, 1, 1), # Mutant Orange Mushroom | Dream Fragment
    (8600005, 4020013, 100, 1, 1), # Mutant Tiru | Dream Fragment
    (8600002, 4020013, 100, 1, 1), # Mutant Slime | Dream Fragment
    (8600006, 4020013, 100, 1, 1), # Mutant Tiguru | Dream Fragment
    (8600003, 4020013, 100, 1, 1), # Mutant Ribbon Pig | Dream Fragment
    (8610005, 4020013, 100, 1, 1), # Official Knight A | Dream Fragment
    (8620013, 4020013, 100, 1, 1), # [*] Official Knight A | Dream Fragment
    (8610000, 4020013, 100, 1, 1), # Dawn | Dream Fragment
    (8610006, 4020013, 100, 1, 1), # Official Knight B | Dream Fragment
    (8620014, 4020013, 100, 1, 1), # [*] Official Knight B | Dream Fragment
    (8610001, 4020013, 100, 1, 1), # Blaze | Dream Fragment
    (8610007, 4020013, 100, 1, 1), # Official Knight C | Dream Fragment
    (8620015, 4020013, 100, 1, 1), # [*] Official Knight C | Dream Fragment
    (8610002, 4020013, 100, 1, 1), # Wind | Dream Fragment
    (8610008, 4020013, 100, 1, 1), # Official Knight D | Dream Fragment
    (8620016, 4020013, 100, 1, 1), # [*] Official Knight D | Dream Fragment
    (8610003, 4020013, 100, 1, 1), # Night | Dream Fragment
    (8610009, 4020013, 100, 1, 1), # Official Knight E | Dream Fragment
    (8620017, 4020013, 100, 1, 1), # [*] Official Knight E | Dream Fragment
    (8610004, 4020013, 100, 1, 1), # Thunder | Dream Fragment
    (8610010, 4020013, 100, 1, 1), # Advanced Knight A | Dream Fragment
    (8610011, 4020013, 100, 1, 1), # Advanced Knight B | Dream Fragment
    (8610012, 4020013, 100, 1, 1), # Advanced Knight C | Dream Fragment
    (8610013, 4020013, 100, 1, 1), # Advanced Knight D | Dream Fragment
    (8610014, 4020013, 100, 1, 1), # Advanced Knight E | Dream Fragment
    (8860006, 2002058, 10000, 1, 1), # Netherworld Monk | Secret Medicine of the Otherverse
    (8860002, 2002058, 10000, 1, 1), # Netherworld Monk | Secret Medicine of the Otherverse
    (9601259, 4310278, 10, 1, 1), # Phantom Tree | Raven Ninja Coin
    (9601260, 4310278, 10, 1, 1), # Phantom Tree | Raven Ninja Coin
    (9601261, 4310278, 10, 1, 1), # Phantom Tree | Raven Ninja Coin
    (9601331, 4310278, 10, 1, 1), # Krakian Spirit | Raven Ninja Coin
    (9601332, 4310278, 10, 1, 1), # Krakian Spirit | Raven Ninja Coin
    (9601265, 4310278, 10, 1, 1), # Elderwraith | Raven Ninja Coin
    (9601266, 4310278, 10, 1, 1), # Corrupted Master | Raven Ninja Coin
    (9601268, 4310278, 10, 1, 1), # Corrupted Windreaver | Raven Ninja Coin
    (9601269, 4310278, 10, 1, 1), # Corrupted Flamekeeper | Raven Ninja Coin
    (9601270, 4310278, 10, 1, 1), # Corrupted Shadowknight | Raven Ninja Coin
    (9601271, 4310278, 10, 1, 1), # Corrupted Stormcaster | Raven Ninja Coin
    (9400017, 4000077, 10000, 1, 1), # Night Fox | Big Cloud Foxtail
    (9400022, 4000075, 10000, 1, 1), # Dark Specter | Dark Specter Bandana
    (9400100, 4000089, 10000, 1, 1), # Yakuza Soldier | Yakuza Soldier's Pin
    (9400101, 4000090, 10000, 1, 1), # Yakuza Grunt | Yakuza Grunt's Name Badge
    (9400102, 4000091, 10000, 1, 1), # Yakuza Bruiser | Yakuza Bruiser's Necklace
    (9400110, 4000092, 10000, 1, 1), # Yakuza Lieutenant | Yakuza Lieutenant's Shades
    (9400111, 4000093, 10000, 1, 1), # Yakuza Shotcaller | Yakuza Shotcaller's Charm
    (9400111, 4031139, 10000, 1, 1), # Yakuza Shotcaller | Mariwaka's Bag
    (9400120, 4000094, 10000, 1, 1), # Yakuza Executive | Bigshot Pomade
    (9400122, 4000094, 10000, 1, 1), # Yakuza Officer | Bigshot Pomade
    (2100100, 4032862, 500, 1, 1), # White Hare | Lunar Dew
    (2100101, 4032862, 500, 1, 1), # Brown Hare | Lunar Dew
    (9601696, 4001907, 10000, 1, 1), # Goo's Scattered Soul Fragment | Goo Blue Flame
    (9601697, 4001907, 10000, 1, 1), # Goo's Scattered Soul Fragment | Goo Blue Flame
    (9601698, 4001907, 10000, 1, 1), # Goo's Scattered Soul Fragment | Goo Blue Flame
    (9601699, 4001907, 10000, 1, 1), # Goo's Scattered Soul Fragment | Goo Blue Flame
    (9601700, 2633344, 10000, 1, 1), # Goo Ruins Gold Chest | Goo Ruins Gold Chest
    (9601701, 2633345, 10000, 1, 1), # Goo Ruins Silver Chest | Goo Ruins Silver Chest
    (9601702, 2633346, 10000, 1, 1), # Goo Ruins Bronze Chest | Goo Ruins Bronze Chest
    (9300141, 4031698, 100, 1, 1), # Homun of Closed Laboratory | Magic Device
    (8644400, 4036327, 2500, 1, 1), # Xenoroid Echo Type A | Memory Sliver
    (8644401, 4036327, 2500, 1, 1), # Xenoroid Echo Type B | Memory Sliver
    (8644402, 4036327, 2500, 1, 1), # Nameless Cat | Memory Sliver
    (8644403, 4036327, 2500, 1, 1), # Powerful Gangster | Memory Sliver
    (8644404, 4036327, 2500, 1, 1), # Strong Gangster | Memory Sliver
    (8644405, 4036327, 2500, 1, 1), # Blue Shadow | Memory Sliver
    (8644406, 4036327, 2500, 1, 1), # Red Shadow | Memory Sliver
    (8644407, 4036327, 2500, 1, 1), # Experiment Gone Wrong | Memory Sliver
    (8644408, 4036327, 2500, 1, 1), # Big Experiment Gone Wrong | Memory Sliver
    (8644409, 4036327, 2500, 1, 1), # Thralled Guard | Memory Sliver
    (8644410, 4036327, 2500, 1, 1), # Thralled Warhammer Knight | Memory Sliver
    (8644411, 4036327, 2500, 1, 1), # Thralled Wizard | Memory Sliver
    (8644412, 4036327, 2500, 1, 1), # Thralled Archer | Memory Sliver
    (8644409, 4036328, 2500, 1, 1), # Thralled Guard | Memory Glass
    (8644410, 4036328, 2500, 1, 1), # Thralled Warhammer Knight | Memory Glass
    (8644411, 4036328, 2500, 1, 1), # Thralled Wizard | Memory Glass
    (8644412, 4036328, 2500, 1, 1), # Thralled Archer | Memory Glass
    (8644402, 4036329, 2500, 1, 1), # Nameless Cat | Glittering Powder
    (8644402, 4036330, 1000, 1, 1), # Nameless Cat | Anti-magic Stone Fragment
    (8644403, 4036330, 1000, 1, 1), # Powerful Gangster | Anti-magic Stone Fragment
    (8644404, 4036330, 1000, 1, 1), # Strong Gangster | Anti-magic Stone Fragment
    (8644403, 4036331, 2500, 1, 1), # Powerful Gangster | Stolen Fruit
    (8644405, 4036332, 2500, 1, 1), # Blue Shadow | Shadow's Core
    (8644406, 4036332, 2500, 1, 1), # Red Shadow | Shadow's Core
    (8644400, 4036333, 2500, 1, 1), # Xenoroid Echo Type A | Tasty Seafood
    (8644401, 4036333, 2500, 1, 1), # Xenoroid Echo Type B | Tasty Seafood
    (8644407, 4036334, 2500, 1, 1), # Experiment Gone Wrong | Experiment Remnants
    (8644408, 4036334, 2500, 1, 1), # Big Experiment Gone Wrong | Experiment Remnants
    (8644410, 4036335, 2500, 1, 1), # Thralled Warhammer Knight | Broken Shaft
    (8644412, 4036336, 2500, 1, 1), # Thralled Archer | Broken Bow
    (8644500, 4036398, 2500, 1, 1), # Ahtuin | Ahtuin Shell
    (8644501, 4036399, 2500, 1, 1), # Atus | Atus Shell
    (8644502, 4036400, 2500, 1, 1), # Bellalion | Bellalion Scale
    (8644503, 4036401, 2500, 1, 1), # Bellalis | Bellalis Scale
    (8644504, 4036402, 2500, 1, 1), # Aranya | Aranya Claws
    (8644505, 4036403, 2500, 1, 1), # Aranea | Aranea Claws
    (8644506, 4036404, 2500, 1, 1), # Keeper of Light | Keeper of Light Ring
    (8644507, 4036405, 2500, 1, 1), # Keeper of Darkness | Keeper of Darkness Ring
    (8644508, 4036406, 2500, 1, 1), # Light Executor | Light Executor Ring
    (8644509, 4036407, 2500, 1, 1), # Dark Executor | Dark Executor Ring
    (0, 0, 0, 0, 0), # ? | ?
    (8641051, 4036631, 7500, 1, 1), # Erda Rat | Blue Rat Meat
    (8641052, 4036632, 7500, 1, 1), # Monto | Purple Rat Meat
    (8641054, 4036633, 7500, 1, 1), # Seeker T-Drone Model B | T-Boy's Research Notes
    (8641055, 4036634, 7500, 1, 1), # Combat T-Drone Model A | Drone Part
    (8641055, 4036635, 7500, 1, 1), # Combat T-Drone Model A | Drone Part
    (4310000, 4036027, 7500, 1, 1), # Greatest Oldies Album | Blake's New Album
    (4310001, 4036027, 7500, 1, 1), # Latest Hits Album | Blake's New Album
    (4310002, 4036019, 7500, 1, 1), # Next-gen Speaker | Audio Cable
    (4310004, 4036020, 7500, 1, 1), # Blue Raspberry Jelly Juice | Fruit Jelly Container
    (4310005, 4036020, 7500, 1, 1), # Grape Jelly Juice | Fruit Jelly Container
    (4310006, 4036020, 7500, 1, 1), # Enraged Espresso Machine | Fruit Jelly Container
    (4310007, 4036021, 7500, 1, 1), # Green Notebook | Note Paper
    (4310008, 4036021, 7500, 1, 1), # Blue Notebook | Note Paper
    (4310009, 4036023, 7500, 1, 1), # Yellow Headphones | Amy's Recorder
    (4310010, 4036023, 7500, 1, 1), # Red Headphones | Amy's Recorder
    (4310011, 4036024, 7500, 1, 1), # Killer Cosmetics | Super Expensive Makeup Kit
    (4310012, 4036024, 7500, 1, 1), # Deadly Dressing Table | Super Expensive Makeup Kit
    (4310013, 4036025, 7500, 1, 1), # Harried Hair Roller | High-End Perm Agent
    (4310014, 4036025, 7500, 1, 1), # Peeved Perm Machine | High-End Perm Agent
    (2400300, 4036140, 7500, 1, 1), # Hedgehog | Cast Iron Plate
    (2400301, 4036141, 7500, 1, 1), # Mutant Hedgehog | Big Nail
    (2400301, 4036142, 7500, 1, 1), # Mutant Hedgehog | Mutant Hedgehog Fur
    (2400302, 4036143, 7500, 1, 1), # Nutria | Nutria Hind Leg
    (2400304, 4036144, 7500, 1, 1), # Ooze Waste | Still Fluid
    (2400305, 4036145, 7500, 1, 1), # Flame Waste | Unburning Flame
    (2400306, 4036146, 7500, 1, 1), # Scrap Iron Waste | Soft Scrap Iron
    (2400307, 4036147, 7500, 1, 1), # Specter Stray Dog | Wiggling Tail
    (2400308, 4036151, 7500, 1, 1), # Mutant Nutria | Mutant Nutria Blood
    (2400312, 4036152, 7500, 1, 1), # Hazard's Mean Crony | Poison Remover
    (2400013, 4036153, 7500, 1, 1), # Onyx Stonegar | Onyx Stonegar Magic Stone
    (2400251, 4036315, 7500, 1, 1), # Flutterbuzz | Flutterbuzz Powder
    (2400252, 4036316, 7500, 1, 1), # Pom Puff Bush | Pom Puff Leaf
    (2400254, 4036317, 7500, 1, 1), # Nettled Squirrel | Nettled Squirrel Tree Nut
    (2400255, 4036319, 7500, 1, 1), # Vicious Squirrel | Vicious Squirrel Tree Nut
    (2400400, 4036163, 7500, 1, 1), # Green Grossular | Grossular Shard
    (2400401, 4036164, 7500, 1, 1), # Purple Amethyst | Amethyst Shard
    (2400409, 4036165, 7500, 1, 1), # Tainted Practice Robot | Dean's Bag
    (2400409, 4036166, 7500, 1, 1), # Tainted Practice Robot | Dean's Notebook
    (2400402, 4036167, 7500, 1, 1), # Fire Pyrope | Pyrope Shard
    (2400403, 4036168, 7500, 1, 1), # Dark Obsidian | Obsidian Shard
    (2400404, 4036169, 7500, 1, 1), # Shining Gold | Gold Shard
    (2400405, 4036170, 7500, 1, 1), # Shining Vinegold | Vinegold Shard
    (2400014, 4036172, 7500, 1, 1), # Gravi Stonegar | Gravi Stonegar Magic Stone
    (2400306, 4036350, 7500, 1, 1), # Scrap Iron Waste | Soft Scrap Iron
    (2400308, 4036351, 7500, 1, 1), # Mutant Nutria | Wiggling Tail
    (8644622, 4036446, 7500, 1, 1), # Soot Beast | Wicked Soul Remnants
    (8644623, 4036446, 7500, 1, 1), # Soot Talon | Wicked Soul Remnants
    (8644626, 4036456, 7500, 1, 1), # Crushing Glare | Void Fragment
    (8644627, 4036456, 7500, 1, 1), # Burst Glare | Void Fragment
    (8644700, 4036452, 7500, 1, 1), # Entangled Fragment | Decent Flint
    (8644701, 4036452, 7500, 1, 1), # Faith Fragment | Decent Flint
    (8644700, 4036453, 7500, 1, 1), # Entangled Fragment | Decent Tree Branch
    (8644701, 4036453, 7500, 1, 1), # Faith Fragment | Decent Tree Branch
    (8644704, 4036454, 7500, 1, 1), # Despairing Wing | Burning Black Soul
    (8644705, 4036454, 7500, 1, 1), # Despairing Blade | Burning Black Soul
    (2300205, 4036529, 7500, 1, 1), # Log-a-Rhythmic Bugs | Extra Dry Firewood
    (2300206, 4036529, 7500, 1, 1), # Sparkinstone Bugs | Extra Dry Firewood
    (2300205, 4036530, 7500, 1, 1), # Log-a-Rhythmic Bugs | Smoky Flint
    (2300206, 4036530, 7500, 1, 1), # Sparkinstone Bugs | Smoky Flint
    (2300208, 4036531, 7500, 1, 1), # Ruins Sentinel | Glowpod
    (2300266, 4036533, 7500, 1, 1), # Frenzied Ruins Sentinel | Sentsuma
    (2300267, 4036534, 7500, 1, 1), # Searing Ruins Guardian | Guardian's Soul Flame
    (8644431, 4036338, 7500, 1, 1), # Thralled Guard | Memory Glass
    (8644432, 4036338, 7500, 1, 1), # Thralled Warhammer Knight | Memory Glass
    (8644433, 4036338, 7500, 1, 1), # Thralled Wizard | Memory Glass
    (8644434, 4036338, 7500, 1, 1), # Thralled Archer | Memory Glass
    (8643001, 4036572, 3000, 1, 1), # Paper Bag Alley Citizen | Sleep Powder
    (8643002, 4036572, 3000, 1, 1), # Wood Board Alley Citizen | Sleep Powder
    (8643003, 4036572, 3000, 1, 1), # Gallina | Sleep Powder
    (8643004, 4036572, 3000, 1, 1), # Gallus | Sleep Powder
    (8643005, 4036572, 3000, 1, 1), # Angry Victory Plate | Sleep Powder
    (8643006, 4036572, 3000, 1, 1), # Crooked Victory Plate | Sleep Powder
    (8643007, 4036572, 3000, 1, 1), # Red Dancing Shoes | Sleep Powder
    (8643008, 4036572, 3000, 1, 1), # Angry Masquerade Citizen | Sleep Powder
    (8643009, 4036572, 3000, 1, 1), # Insane Masquerade Citizen | Sleep Powder
    (8643010, 4036572, 3000, 1, 1), # Blue-eyed Gargoyle | Sleep Powder
    (8643011, 4036572, 3000, 1, 1), # Red-eyed Gargoyle | Sleep Powder
    (8643015, 4036572, 3000, 1, 1), # Red Dancing Shoes | Sleep Powder
    (9421511, 4033306, 7500, 1, 1), # Oda Scout | Familiar Seal Case
    (9421514, 4033307, 7500, 1, 1), # Oda Warrior | Mysterious Foxtail
    (6130207, 4033309, 7500, 1, 1), # Peach Monkey | Oda Clan Seal
    (5120505, 4033309, 7500, 1, 1), # Cute Reindeer | Oda Clan Seal
    (6130209, 4033310, 7500, 1, 1), # Sage Cat | Volatile Oda Clan Seal
    (9300344, 4032322, 10000, 1, 1), # Puppeteer | Puppeteer Document
    (9001056, 4033222, 7500, 1, 1), # Unidentified Assailant | Cloth Scrap
    (2700303, 4034602, 7500, 1, 1), # Shooter Mote | Twisted Dust Ball
    (2700304, 4034602, 7500, 1, 1), # Flyer Mote | Twisted Dust Ball
    (2700305, 4034602, 7500, 1, 1), # Roller Mote | Twisted Dust Ball
    (9300804, 4033891, 10000, 1, 1), # Master of Disguise | Gem of Nihal Desert
    (5130101, 4033124, 7500, 1, 1), # Stone Golem | Golem Core
    (5130102, 4033124, 7500, 1, 1), # Dark Stone Golem | Golem Core
    (5150000, 4033124, 7500, 1, 1), # Mixed Golem | Golem Core
    (5150002, 4033124, 7500, 1, 1), # Icy Mixed Golem | Golem Core
    (5150003, 4033124, 7500, 1, 1), # Flaming Mixed Golem | Golem Core
    (4230109, 4033137, 7500, 1, 1), # Block Golem | Golem Core
    (4230110, 4033137, 7500, 1, 1), # King Block Golem | Golem Core
    (7130100, 4033126, 7500, 1, 1), # Tauromacis | Tauromacis Blood
    (8645225, 4036781, 7500, 1, 1), # Sprout Silkworm | Lost Shoe
    (8645227, 4036778, 1500, 1, 1), # Flower Field Bunny | Carrot Brooch
    (8645226, 4036779, 1000, 1, 1), # Flower Wing | Wrapped Toy
    (8645227, 4036782, 7500, 1, 1), # Flower Field Bunny | Pink Petal
    (8645228, 4036783, 7500, 1, 1), # Flower Drift Bunny | Yellow Petal
    (8645221, 4036784, 5000, 1, 1), # Sunlight-Seeking Sphere | Worn-out Bottle
    (8645223, 4036780, 5000, 1, 1), # Gluey Leaf Fly | Worn Paper
    (100124, 4033671, 5000, 1, 1), # Tiguru | Ereve Investigation Permit
    (9300743, 4033672, 10000, 1, 1), # Master of Disguise | The Lost Treasure
    (8230026, 4033972, 6000, 1, 1), # Neon Bat | Faint Glowing Substance
    (8230008, 4033973, 6000, 1, 1), # Glowing Primary Rock | Glassium
    (8230036, 4033975, 1000, 1, 1), # Ore Muncher | Lovely Daughter's Photograph
    (8230037, 4033976, 6000, 1, 1), # AF Android | Security Memory Chip
    (8230038, 4033977, 6000, 1, 1), # Broken DF Android | Detour Operator Device
    (2600010, 4033798, 3000, 1, 1), # Black Kentaurus | Harp Feather
    (2600011, 4033798, 3000, 1, 1), # Red Kentaurus | Harp Feather
    (2600012, 4033798, 3000, 1, 1), # Blue Kentaurus | Harp Feather
    (2600008, 4033799, 3000, 1, 1), # Harp | Blood Harp Feather
    (2600009, 4033799, 3000, 1, 1), # Blood Harp | Blood Harp Feather
    (2600013, 4033805, 2000, 1, 1), # Birk | Kentaurus King Horn
    (2600014, 4033805, 2000, 1, 1), # Dual Birk | Kentaurus King Horn
    (2600018, 4033806, 2000, 1, 1), # Rexton | Manon Feather
    (2600019, 4033806, 2000, 1, 1), # Brexton | Manon Feather
    (2600104, 4033811, 2000, 1, 1), # Royal Cactus | Deo's Fruit
    (2600111, 4033812, 10000, 1, 1), # Thief Crow | Thief Crow's Key
    (2600206, 4033820, 2000, 1, 1), # Zombie Mushroom | Mushmom Cap
    (2600207, 4033820, 2000, 1, 1), # Annoyed Zombie Mushroom | Mushmom Cap
    (2600220, 4033821, 2000, 1, 1), # Starfish Octopus Slime | Pearl
    (2600221, 4033821, 2000, 1, 1), # Seashell Octopus Slime | Pearl
    (9300766, 4033832, 3000, 1, 1), # Straw Training Doll | Proof of Training
    (2600318, 4033922, 2000, 1, 1), # Ginseng Jar | Thousand-year-old Bellflower
    (2600416, 4033858, 2000, 1, 1), # Racaroni | Shining Rue Ore
    (9300792, 4033859, 10000, 1, 1), # Shadow Knight | Shadow Knights Report
    (2600425, 4033860, 10000, 1, 1), # Poison Gas Generator | Poison Concentrate
    (2600705, 2431836, 10000, 1, 1), # Dodo | Shard of Recollection
    (2600710, 2431837, 10000, 1, 1), # Lilynouch | Shard of Regret
    (2600715, 2431838, 10000, 1, 1), # Lyka | Shard of Oblivion
    (2600023, 4033795, 3000, 1, 1), # Red Wyvern | Wyvern Wing Snippet
    (2600024, 4033796, 3000, 1, 1), # Blue Wyvern | Wyvern Gill Sample
    (2600025, 4033797, 3000, 1, 1), # Dark Wyvern | Wyvern Claw Chip
    (2600026, 4033803, 4000, 1, 1), # Green Cornian | Cornian Marrow Sample
    (2600027, 4033803, 4000, 1, 1), # Dark Cornian | Cornian Marrow Sample
    (2600030, 4033793, 10000, 1, 1), # Leviathan | Leviathan Tear Droplet
    (2600010, 4033794, 4000, 1, 1), # Black Kentaurus | Kentaurus Bone
    (2600011, 4033794, 4000, 1, 1), # Red Kentaurus | Kentaurus Bone
    (2600012, 4033794, 4000, 1, 1), # Blue Kentaurus | Kentaurus Bone
    (2600006, 4033790, 1500, 1, 1), # Green Hobi | Signed Peace Treaty
    (2600016, 4033787, 4000, 1, 1), # Blue Dragon Turtle | Shell Dragon Meat
    (2600017, 4033787, 4000, 1, 1), # Red Dragon Turtle | Shell Dragon Meat
    (2600022, 4033792, 10000, 1, 1), # Manon | Manon's Tail Meat
    (2600113, 4033907, 1000, 1, 1), # Sand Dwarf | Gold Sand
    (2600106, 4033906, 1500, 1, 1), # Bellamoa | Blue Feather
    (2600200, 4033819, 2000, 1, 1), # Green Mushroom | Hay
    (2600231, 4033786, 10000, 1, 1), # Jr. Balrog | Jr. Balrog Essence
    (2600321, 4033908, 10000, 1, 1), # Gigantic Bellflower Root | Thousand-year Bellflower Fruit
    (2600326, 4033791, 10000, 1, 1), # Lord Pirate | Master Goblin Essence
    (2600418, 4033836, 5000, 1, 1), # Ore Muncher | Rue Ore Fragment
    (2600411, 4033836, 5000, 1, 1), # Big Boulder Muncher | Rue Ore Fragment
    (2600505, 4033841, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 1
    (2600505, 4033842, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 2
    (2600505, 4033843, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 3
    (2600505, 4033844, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 4
    (2600505, 4033845, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 5
    (2600505, 4033846, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 6
    (2600505, 4033847, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 7
    (2600505, 4033848, 800, 1, 1), # Iron Mutae | Alcadno Research Diary Page 8
    (2600506, 4033841, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 1
    (2600506, 4033842, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 2
    (2600506, 4033843, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 3
    (2600506, 4033844, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 4
    (2600506, 4033845, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 5
    (2600506, 4033846, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 6
    (2600506, 4033847, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 7
    (2600506, 4033848, 800, 1, 1), # Reinforced Iron Mutae | Alcadno Research Diary Page 8
    (2600507, 4033841, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 1
    (2600507, 4033842, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 2
    (2600507, 4033843, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 3
    (2600507, 4033844, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 4
    (2600507, 4033845, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 5
    (2600507, 4033846, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 6
    (2600507, 4033847, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 7
    (2600507, 4033848, 800, 1, 1), # Mithril Mutae | Alcadno Research Diary Page 8
    (2600508, 4033841, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 1
    (2600508, 4033842, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 2
    (2600508, 4033843, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 3
    (2600508, 4033844, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 4
    (2600508, 4033845, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 5
    (2600508, 4033846, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 6
    (2600508, 4033847, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 7
    (2600508, 4033848, 800, 1, 1), # Reinforced Mithril Mutae | Alcadno Research Diary Page 8
    (2600509, 4033850, 10000, 1, 1), # Security Camera | Alcadno Lab Pass
    (2600516, 4033909, 1500, 1, 1), # Neo Huroid | Mana Supply Device
    (2600518, 4033910, 10000, 1, 1), # Deet and Roi | Useless Iron Heart
    (2600613, 4033862, 10000, 1, 1), # Timer | Time Sphere
    (2600608, 4033863, 5000, 1, 1), # Tick | Time Egg
    (2600609, 4033863, 5000, 1, 1), # Tick-Tock | Time Egg
    (2600610, 4033863, 5000, 1, 1), # Chronos | Time Egg
    (2600611, 4033863, 5000, 1, 1), # Platoon Chronos | Time Egg
    (2600612, 4033863, 5000, 1, 1), # Master Chronos | Time Egg
    (2600631, 4033864, 10000, 1, 1), # Papulatus | Clocktower Key
    (2600700, 4033872, 5000, 1, 1), # Eye of Time | Memory Crumbs
    (2600701, 4033872, 5000, 1, 1), # Memory Monk | Memory Crumbs
    (2600702, 4033872, 5000, 1, 1), # Memory Monk Trainee | Memory Crumbs
    (2600703, 4033872, 5000, 1, 1), # Memory Guardian | Memory Crumbs
    (2600704, 4033872, 5000, 1, 1), # Chief Memory Guardian | Memory Crumbs
    (2600706, 4033874, 5000, 1, 1), # Qualm Monk | Emotion Crumbs
    (2600707, 4033874, 5000, 1, 1), # Qualm Monk Trainee | Emotion Crumbs
    (2600708, 4033874, 5000, 1, 1), # Qualm Guardian | Emotion Crumbs
    (2600709, 4033874, 5000, 1, 1), # Chief Qualm Guardian | Emotion Crumbs
    (2600711, 4033876, 5000, 1, 1), # Oblivion Monk | Oblivion Crumbs
    (2600712, 4033876, 5000, 1, 1), # Oblivion Monk Trainee | Oblivion Crumbs
    (2600713, 4033876, 5000, 1, 1), # Oblivion Guard | Oblivion Crumbs
    (2600714, 4033876, 5000, 1, 1), # Chief Oblivion Guardian | Oblivion Crumbs
    (3300106, 4034112, 4000, 1, 1), # Solemn Viking | Iron Chain Key
    (3300107, 4034112, 4000, 1, 1), # Serious Viking | Iron Chain Key
    (3300108, 4034112, 4000, 1, 1), # Master Squid | Iron Chain Key
    (3300106, 4034114, 3000, 1, 1), # Solemn Viking | Secret Puzzle Piece
    (3300107, 4034114, 3000, 1, 1), # Serious Viking | Secret Puzzle Piece
    (3300108, 4034114, 3000, 1, 1), # Master Squid | Secret Puzzle Piece
    (9400037, 4034856, 6000, 1, 1), # Lost Specter | Lost Specter's Handkerchief
    (9400037, 4034858, 300, 1, 1), # Lost Specter | The Gospel of Nue
    (9400039, 4034859, 10000, 1, 2), # Dusk Viper | Dusk Viper Venom
    (9400074, 4034873, 6000, 1, 1), # Stone Golem | Stompin' Rock
    (9400031, 4034872, 1500, 1, 1), # Crazed Oni | Sakura's Treasure
    (9400032, 4034872, 1500, 1, 1), # Berserk Oni | Sakura's Treasure
    (9400033, 4034872, 1500, 1, 1), # Suspicious Oni | Sakura's Treasure
    (2700020, 4030038, 4000, 1, 1), # Mote | Bizarre Dust Ball
    (2700021, 4030038, 4000, 1, 1), # Shooting Mote | Bizarre Dust Ball
    (2700022, 4030038, 4000, 1, 1), # Flying Mote | Bizarre Dust Ball
    (2700023, 4030038, 4000, 1, 1), # Rolling Mote | Bizarre Dust Ball
    (2700024, 4030038, 4000, 1, 1), # Poison Mote | Bizarre Dust Ball
    (2700025, 4030038, 4000, 1, 1), # Hallow Mote | Bizarre Dust Ball
    (2700020, 4030039, 4000, 1, 1), # Mote | Student Records Page
    (2700021, 4030039, 4000, 1, 1), # Shooting Mote | Student Records Page
    (2700022, 4030039, 4000, 1, 1), # Flying Mote | Student Records Page
    (2700023, 4030039, 4000, 1, 1), # Rolling Mote | Student Records Page
    (2700024, 4030039, 4000, 1, 1), # Poison Mote | Student Records Page
    (2700025, 4030039, 4000, 1, 1), # Hallow Mote | Student Records Page
    (2700020, 4034165, 4000, 1, 1), # Mote | Aluminum Powder
    (2700021, 4034165, 4000, 1, 1), # Shooting Mote | Aluminum Powder
    (2700022, 4034165, 4000, 1, 1), # Flying Mote | Aluminum Powder
    (2700023, 4034165, 4000, 1, 1), # Rolling Mote | Aluminum Powder
    (2700024, 4034165, 4000, 1, 1), # Poison Mote | Aluminum Powder
    (2700025, 4034165, 4000, 1, 1), # Hallow Mote | Aluminum Powder
    (2700020, 4034172, 4000, 1, 1), # Mote | Laxative
    (2700021, 4034172, 4000, 1, 1), # Shooting Mote | Laxative
    (2700022, 4034172, 4000, 1, 1), # Flying Mote | Laxative
    (2700023, 4034172, 4000, 1, 1), # Rolling Mote | Laxative
    (2700024, 4034172, 4000, 1, 1), # Poison Mote | Laxative
    (2700025, 4034172, 4000, 1, 1), # Hallow Mote | Laxative
    (2700020, 4034150, 4000, 1, 1), # Mote | Flu Virus
    (2700021, 4034150, 4000, 1, 1), # Shooting Mote | Flu Virus
    (2700022, 4034150, 4000, 1, 1), # Flying Mote | Flu Virus
    (2700023, 4034150, 4000, 1, 1), # Rolling Mote | Flu Virus
    (2700024, 4034150, 4000, 1, 1), # Poison Mote | Flu Virus
    (2700025, 4034150, 4000, 1, 1), # Hallow Mote | Flu Virus
    (2700020, 4034151, 4000, 1, 1), # Mote | Chalk
    (2700021, 4034151, 4000, 1, 1), # Shooting Mote | Chalk
    (2700022, 4034151, 4000, 1, 1), # Flying Mote | Chalk
    (2700023, 4034151, 4000, 1, 1), # Rolling Mote | Chalk
    (2700024, 4034151, 4000, 1, 1), # Poison Mote | Chalk
    (2700025, 4034151, 4000, 1, 1), # Hallow Mote | Chalk
    (2700020, 4034173, 4000, 1, 1), # Mote | Suspicious Recorder
    (2700021, 4034173, 4000, 1, 1), # Shooting Mote | Suspicious Recorder
    (2700022, 4034173, 4000, 1, 1), # Flying Mote | Suspicious Recorder
    (2700023, 4034173, 4000, 1, 1), # Rolling Mote | Suspicious Recorder
    (2700024, 4034173, 4000, 1, 1), # Poison Mote | Suspicious Recorder
    (2700025, 4034173, 4000, 1, 1), # Hallow Mote | Suspicious Recorder
    (2700020, 4034174, 4000, 1, 1), # Mote | Big Ketchup Bottle
    (2700021, 4034174, 4000, 1, 1), # Shooting Mote | Big Ketchup Bottle
    (2700022, 4034174, 4000, 1, 1), # Flying Mote | Big Ketchup Bottle
    (2700023, 4034174, 4000, 1, 1), # Rolling Mote | Big Ketchup Bottle
    (2700024, 4034174, 4000, 1, 1), # Poison Mote | Big Ketchup Bottle
    (2700025, 4034174, 4000, 1, 1), # Hallow Mote | Big Ketchup Bottle
    (2700020, 4034346, 4000, 1, 1), # Mote | Practice Music Sheet
    (2700021, 4034346, 4000, 1, 1), # Shooting Mote | Practice Music Sheet
    (2700022, 4034346, 4000, 1, 1), # Flying Mote | Practice Music Sheet
    (2700023, 4034346, 4000, 1, 1), # Rolling Mote | Practice Music Sheet
    (2700024, 4034346, 4000, 1, 1), # Poison Mote | Practice Music Sheet
    (2700025, 4034346, 4000, 1, 1), # Hallow Mote | Practice Music Sheet
    (2700020, 4034348, 4000, 1, 1), # Mote | Fever Medicine
    (2700021, 4034348, 4000, 1, 1), # Shooting Mote | Fever Medicine
    (2700022, 4034348, 4000, 1, 1), # Flying Mote | Fever Medicine
    (2700023, 4034348, 4000, 1, 1), # Rolling Mote | Fever Medicine
    (2700024, 4034348, 4000, 1, 1), # Poison Mote | Fever Medicine
    (2700025, 4034348, 4000, 1, 1), # Hallow Mote | Fever Medicine
    (2700020, 4034347, 4000, 1, 1), # Mote | Pamphlet
    (2700021, 4034347, 4000, 1, 1), # Shooting Mote | Pamphlet
    (2700022, 4034347, 4000, 1, 1), # Flying Mote | Pamphlet
    (2700023, 4034347, 4000, 1, 1), # Rolling Mote | Pamphlet
    (2700024, 4034347, 4000, 1, 1), # Poison Mote | Pamphlet
    (2700025, 4034347, 4000, 1, 1), # Hallow Mote | Pamphlet
    (8230019, 4033962, 5000, 1, 1), # Sparker | Aqua Road White Pearl
    (8230020, 4033962, 5000, 1, 1), # Freezer | Aqua Road White Pearl
    (8230022, 4033963, 5000, 1, 1), # Seacle | Aqua Road Green Pearl
    (8230023, 4033963, 5000, 1, 1), # Cico | Aqua Road Green Pearl
    (8230024, 4033964, 5000, 1, 1), # Bone Fish | Aqua Road Black Pearl
    (8230025, 4033964, 5000, 1, 1), # Bombing Fish House | Aqua Road Black Pearl
    (8230027, 4033966, 5000, 1, 1), # Cute Reindeer | Glowing Antler
    (8230029, 4033967, 5000, 1, 1), # Peach Monkey | Golden Peach
    (8230028, 4033968, 5000, 1, 1), # Bellflower Root | Ginseng Concentrate
    (8140000, 4033970, 2000, 1, 1), # Lycanthrope | Flower of Fate
    (8230047, 4033971, 10000, 1, 1), # Omen | Omen Piece
    (8230050, 4036137, 5000, 1, 1), # Volcano Bug | Dry Firewood
    (8230051, 4036138, 5000, 1, 1), # Giant Beetle | Resin
    (8230059, 4036139, 5000, 1, 1), # Mountain Turtle | Mountain Turtle Egg
    (9500195, 4034711, 4000, 1, 1), # Jack-o-Lantern | Jack-O-Lantern
    (9500195, 4034709, 3000, 1, 1), # Jack-o-Lantern | Soul Flower
    (9500407, 4034710, 4000, 1, 1), # Graveyard Stirge | Black Bow
    (9500407, 4034709, 3000, 1, 1), # Graveyard Stirge | Soul Flower
    (9500191, 4009379, 1500, 1, 1), # Green Phantom | Candle Chest Key
    (9500196, 4009379, 1500, 1, 1), # Ghost | Candle Chest Key
    (9500188, 4034712, 1000, 1, 1), # Destroyed Doll | Tuning Hammer
    (9500192, 4034716, 1500, 1, 1), # Pumpkin Knight | Pumpkin Knight's Charm
    (9500193, 4034718, 3000, 1, 1), # Fire Steed | Invisible Cloth Piece
    (9010102, 4001863, 10000, 1, 2), # Elite Pumpkin Zombie | Kaleido-Spinner Event Ticket
    (9010102, 2550029, 10000, 1, 1), # Elite Pumpkin Zombie | Halloween Surprise Badge Box
    (5100100, 4034634, 7500, 1, 1), # Moon Bunny | Rice Flour
    (5100101, 4034639, 7500, 1, 1), # Dark Axe Stump | Rose
    (5100101, 4034650, 7500, 1, 1), # Dark Axe Stump | Very Thin Bark
    (9810102, 4033202, 7500, 1, 1), # Water Thief Monster | Dapper Drip
    (9810100, 4033200, 7500, 1, 1), # Baby Boulder Muncher | Suaveness Comb
    (9810103, 4033203, 7500, 1, 1), # Cart Bear | Channeling Jewel
    (9810200, 4033212, 7500, 1, 1), # White Hare | Desert Rabbit Fur
    (9810202, 4033214, 7500, 1, 1), # Desert Giant | Palm Tree Sap
    (9810300, 4033204, 7500, 1, 1), # Harp | Warm Feather
    (9810305, 4033207, 7500, 1, 1), # Red Dragon Turtle | Delicious Grass
    (9810305, 4033208, 7500, 1, 1), # Red Dragon Turtle | Dragon Meat
    (9810402, 4033216, 7500, 1, 1), # Jr. Yeti | Jr. Yeti Meat
    (9810403, 4033217, 7500, 1, 1), # Werewolf | Werewolf Tail
    (9306101, 4033457, 7500, 1, 1), # System Monster G6 | Accelerator
    (9306001, 4033451, 7500, 1, 1), # System Monster N2 | Memory Chip
    (9306007, 4033458, 7500, 1, 1), # System Monster F1 | Power Supply
    (210100, 4031273, 7500, 1, 1), # Slime | Red Ball of Yarn
    (4230201, 4032863, 7500, 1, 1), # Poison Poopa | Soul Light
    (3110300, 4032864, 7500, 1, 1), # Cube Slime | Soul Light
    (4230503, 4032864, 7500, 1, 1), # Blue Flower Serpent | Soul Light
    (4110301, 4032865, 7500, 1, 1), # Reinforced Iron Mutae | Soul Light
    (5120500, 4032865, 7500, 1, 1), # Grizzly | Soul Light
    (3100101, 4032866, 7500, 1, 1), # Sand Dwarf | Soul Light
    (6130203, 4032866, 7500, 1, 1), # Panda | Soul Light
    (6130200, 4032867, 7500, 1, 1), # [*]Buffy | Soul Light
    (6130207, 4032867, 7500, 1, 1), # Peach Monkey | Soul Light
    (4230201, 4032868, 7500, 1, 1), # Poison Poopa | Burning Flare
    (3110300, 4032869, 7500, 1, 1), # Cube Slime | Burning Flare
    (4230503, 4032869, 7500, 1, 1), # Blue Flower Serpent | Burning Flare
    (4110301, 4032870, 7500, 1, 1), # Reinforced Iron Mutae | Burning Flare
    (5120500, 4032870, 7500, 1, 1), # Grizzly | Burning Flare
    (3100101, 4032871, 7500, 1, 1), # Sand Dwarf | Burning Flare
    (6130203, 4032871, 7500, 1, 1), # Panda | Burning Flare
    (6130200, 4032872, 7500, 1, 1), # [*]Buffy | Burning Flare
    (6130207, 4032872, 7500, 1, 1), # Peach Monkey | Burning Flare
    (4230201, 4032873, 7500, 1, 1), # Poison Poopa | Unstoppable Wind
    (4110301, 4032875, 7500, 1, 1), # Reinforced Iron Mutae | Unstoppable Wind
    (5120500, 4032875, 7500, 1, 1), # Grizzly | Unstoppable Wind
    (3100101, 4032876, 7500, 1, 1), # Sand Dwarf | Unstoppable Wind
    (6130203, 4032876, 7500, 1, 1), # Panda | Unstoppable Wind
    (6130200, 4032877, 7500, 1, 1), # [*]Buffy | Unstoppable Wind
    (6130207, 4032877, 7500, 1, 1), # Peach Monkey | Unstoppable Wind
    (4230201, 4032878, 7500, 1, 1), # Poison Poopa | Nameless Darkness
    (3110300, 4032879, 7500, 1, 1), # Cube Slime | Nameless Darkness
    (4230503, 4032879, 7500, 1, 1), # Blue Flower Serpent | Nameless Darkness
    (4110301, 4032880, 7500, 1, 1), # Reinforced Iron Mutae | Nameless Darkness
    (5120500, 4032880, 7500, 1, 1), # Grizzly | Nameless Darkness
    (3100101, 4032881, 7500, 1, 1), # Sand Dwarf | Nameless Darkness
    (6130203, 4032881, 7500, 1, 1), # Panda | Nameless Darkness
    (6130200, 4032882, 7500, 1, 1), # [*]Buffy | Nameless Darkness
    (6130207, 4032882, 7500, 1, 1), # Peach Monkey | Nameless Darkness
    (4230201, 4032883, 7500, 1, 1), # Poison Poopa | Earth-Cleaving Lightning
    (3110300, 4032884, 7500, 1, 1), # Cube Slime | Earth-Cleaving Lightning
    (4230503, 4032884, 7500, 1, 1), # Blue Flower Serpent | Earth-Cleaving Lightning
    (4110301, 4032885, 7500, 1, 1), # Reinforced Iron Mutae | Earth-Cleaving Lightning
    (5120500, 4032885, 7500, 1, 1), # Grizzly | Earth-Cleaving Lightning
    (3100101, 4032886, 7500, 1, 1), # Sand Dwarf | Earth-Cleaving Lightning
    (6130203, 4032886, 7500, 1, 1), # Panda | Earth-Cleaving Lightning
    (6130200, 4032887, 7500, 1, 1), # [*]Buffy | Earth-Cleaving Lightning
    (6130207, 4032887, 7500, 1, 1), # Peach Monkey | Earth-Cleaving Lightning
    (2130103, 4034076, 7500, 1, 1), # Jr. Necki | Thin Jr. Necki Skin
    (9300000, 4034076, 7500, 1, 1), # Jr. Necki | Thin Jr. Necki Skin
    (1140100, 4033181, 7500, 1, 1), # Ghost Stump | Lost in Transit Box
    (1140130, 4033181, 7500, 1, 1), # Smirking Ghost Stump | Lost in Transit Box
    (3110100, 4033182, 7500, 1, 1), # Ligator | Secret Document
    (2130100, 4000215, 7500, 1, 1), # Dark Axe Stump | Axe
    (130100, 4033059, 7500, 1, 1), # Stump | Dry Tree Branch
    (1140100, 4033059, 7500, 1, 1), # Ghost Stump | Dry Tree Branch
    (2230114, 4033045, 7500, 1, 1), # Muddy Swamp Monster | First-Aid Box
    (5200000, 4000083, 7500, 1, 1), # Jr. Sentinel | Jr. Sentinel Shellpiece
    (9300281, 4000584, 7500, 1, 1), # Rex | Essence of the Hoblin King
    (8210004, 4032836, 7500, 1, 1), # Grey Vulture | Key to the Rose Garden
    (4230128, 4036070, 7500, 1, 1), # Plateon | Luggage Ticket
    (4230137, 4036071, 7500, 1, 1), # White Robosaucer | Signal Jammer
    (4230138, 4036071, 7500, 1, 1), # Black Robosaucer | Signal Jammer
    (5120502, 4000294, 7500, 1, 1), # Sr. Bellflower Root | 100-Year-Old Bellflower
    (7130104, 4031436, 7500, 1, 1), # Captain | Hook
    (9300116, 4031436, 7500, 1, 1), # Lord Pirate's Enraged Captain | Hook
    (9300118, 4031436, 7500, 1, 1), # Lord Pirate's Devoted Captain | Hook
    (9300122, 4031436, 7500, 1, 1), # Lord Pirate's Furious Captain | Hook
    (9400011, 4000222, 7500, 1, 1), # Paper Lantern Ghost | Worn Paper Lantern
    (9400021, 4000222, 7500, 1, 1), # Paper Lantern Ghost | Worn Paper Lantern
    (9400012, 4000223, 7500, 1, 1), # Water Goblin | Cucumber
    (9400020, 4000223, 7500, 1, 1), # Kappa | Cucumber
    (9400408, 4000345, 7500, 1, 1), # False Daimyo | Daimyo's Stolen Armrest
    (9400409, 4000345, 7500, 1, 1), # Gigatoad | Daimyo's Stolen Armrest
    (9400409, 4000346, 7500, 1, 1), # Gigatoad | Gigatoad's Coin Wallet
    (9400006, 4000188, 7500, 1, 1), # Blue Boogie | Duck Egg
    (9400024, 4000188, 7500, 1, 1), # Black Crow | Duck Egg
    (9400401, 4000338, 7500, 1, 1), # Sakura Chunin | Chunin Poison Dart
    (9400406, 4000344, 7500, 1, 1), # Kaede Kunoichi | Kunoichi Dagger
    (9400403, 4000340, 7500, 1, 1), # Kaede Ninto | Ninto Scroll
    (9400405, 4000340, 7500, 1, 1), # Kacchuu Musha | Ninto Scroll
    (9400403, 4000342, 7500, 1, 1), # Kaede Ninto | Kacchuu Musha Fragment
    (9400405, 4000342, 7500, 1, 1), # Kacchuu Musha | Kacchuu Musha Fragment
    (9400408, 4000346, 7500, 1, 1), # False Daimyo | Gigatoad's Coin Wallet
    (130100, 4001038, 7500, 1, 1), # Stump | Stump Eraser
    (210100, 4001042, 7500, 1, 1), # Slime | Slime Eraser
    (9390857, 4033991, 7500, 1, 1), # Grosso Polpo | Unknown Cube Fragment
    (9390818, 4033996, 7500, 1, 1), # Nightcutter Assassin | Confidential Document
    (9390819, 4033996, 7500, 1, 1), # Nightwhip Assassin | Confidential Document
    (9390820, 4033996, 7500, 1, 1), # Nightpriest Assassin | Confidential Document
    (3230300, 4033220, 7500, 1, 1), # Jr. Boogie | Informant Badge
    (3230300, 4033221, 7500, 1, 1), # Jr. Boogie | Winston's Album
    (1110101, 4033223, 7500, 1, 1), # Dark Stump | Chrome's Old Diary
    (1110101, 4033224, 7500, 1, 1), # Dark Stump | Diary Page
    (9833457, 4033224, 7500, 1, 1), # Dark Stump | Diary Page
    (1130100, 4033225, 7500, 1, 1), # Axe Stump | Diary Page
    (9833458, 4033225, 7500, 1, 1), # Axe Stump | Diary Page
    (2130100, 4033226, 7500, 1, 1), # Dark Axe Stump | Diary Page
    (5100101, 4033226, 7500, 1, 1), # Dark Axe Stump | Diary Page
    (9833459, 4033226, 7500, 1, 1), # Dark Axe Stump | Diary Page
    (1140100, 4033228, 7500, 1, 1), # Ghost Stump | Empress Aria's Keepsake
    (9300736, 4033717, 7500, 1, 1), # Berserk Horny Mushroom | Berzerk Horny Mushroom Puppet
    (9300737, 4033718, 7500, 1, 1), # Weirdo Horny Mushroom | Weirdo Horny Mushroom Puppet
    (8190003, 4032334, 7500, 1, 1), # Skelegon | Life Water for Wolf
    (1210102, 4034063, 7500, 1, 1), # Orange Mushroom | Berserk Horny Mushroom Puppet
    (2110200, 4034063, 7500, 1, 1), # Horny Mushroom | Berserk Horny Mushroom Puppet
    (9300736, 4034063, 7500, 1, 1), # Berserk Horny Mushroom | Berserk Horny Mushroom Puppet
    (2220110, 4034064, 7500, 1, 1), # Crying Blue Mushroom | Crying Blue Mushroom Doll
    (5130101, 4034065, 7500, 1, 1), # Stone Golem | Golem Doll
    (1140130, 4032319, 7500, 1, 1), # Smirking Ghost Stump | Smirking Ghost Stump Puppet
    (9300347, 4032324, 7500, 1, 1), # Giant Nependeath | Rapid Growth Stimulator
    (130100, 4032498, 7500, 1, 1), # Stump | Thick Branch
    (1210100, 4032498, 7500, 1, 1), # Pig | Thick Branch
    (1210100, 4032453, 7500, 1, 1), # Pig | Pork
    (1210111, 4032455, 7500, 1, 1), # Strange Pig | Letter from Gustav
    (5130101, 4034073, 7500, 1, 1), # Stone Golem | Golem Doll
    (5130102, 4034073, 7500, 1, 1), # Dark Stone Golem | Golem Doll
    (5150000, 4034073, 7500, 1, 1), # Mixed Golem | Golem Doll
    (5150002, 4034073, 7500, 1, 1), # Icy Mixed Golem | Golem Doll
    (5150003, 4034073, 7500, 1, 1), # Flaming Mixed Golem | Golem Doll
    (130100, 4032460, 7500, 1, 1), # Stump | Refreshing Stump Sap
    (1140100, 4034066, 7500, 1, 1), # Ghost Stump | Smirking Ghost Stump Puppet
    (1140130, 4034066, 7500, 1, 1), # Smirking Ghost Stump | Smirking Ghost Stump Puppet
    (2230113, 4034069, 7500, 1, 1), # Surgeon Eye | Surgeon Eye Cell Sample
    (4230100, 4034069, 7500, 1, 1), # Cold Eye | Surgeon Eye Cell Sample
    (2220100, 4034067, 7500, 1, 1), # Blue Mushroom | Blue Mushroom Doll
    (9300387, 4032466, 7500, 1, 1), # Enraged Golem | Golem Doll
    (2150002, 4032746, 7500, 1, 1), # Streetlight | Streetlight Tendril
    (4230110, 4033651, 7500, 1, 1), # King Block Golem | MR Receiver
    (5130101, 4033656, 7500, 1, 1), # Stone Golem | Delivery Box
    (5130102, 4033656, 7500, 1, 1), # Dark Stone Golem | Delivery Box
    (5150000, 4033656, 7500, 1, 1), # Mixed Golem | Delivery Box
    (5150002, 4033656, 7500, 1, 1), # Icy Mixed Golem | Delivery Box
    (5150003, 4033656, 7500, 1, 1), # Flaming Mixed Golem | Delivery Box
    (2400013, 4000711, 7500, 1, 1), # Onyx Stonegar | Onyx Stonegar Piece
    (2400014, 4000711, 7500, 1, 1), # Gravi Stonegar | Onyx Stonegar Piece
    (2400013, 4000712, 7500, 1, 1), # Onyx Stonegar | Gravi Stonegar Guard
    (2400014, 4000712, 7500, 1, 1), # Gravi Stonegar | Gravi Stonegar Guard
    (9300640, 4033643, 7500, 1, 1), # Garbage Pile | Delivery Delay Notice
    (9300644, 4033644, 7500, 1, 1), # Messy Book Pile | Book Pile
    (9300645, 4033664, 7500, 1, 1), # Guard Robot E | Mechanical Circuit
    (2150002, 4032756, 7500, 1, 1), # Streetlight | First-Aid Kit
    (7150004, 4032768, 7500, 1, 1), # Guard Robot L | Guard Robot L's Chip
    (8105001, 4032775, 7500, 1, 1), # Security System | Baby Ore Muncher
    (210100, 4032959, 7500, 1, 1), # Slime | Evil Eye Cell Sample
    (2230100, 4032959, 7500, 1, 1), # Evil Eye | Evil Eye Cell Sample
    (2230100, 4032960, 7500, 1, 1), # Evil Eye | Curse Eye Cell Sample
    (3230100, 4032960, 7500, 1, 1), # Curse Eye | Curse Eye Cell Sample
    (2230100, 4032961, 7500, 1, 1), # Evil Eye | Plant Cell Sample
    (3230100, 4032961, 7500, 1, 1), # Curse Eye | Plant Cell Sample
    (1110101, 4032963, 7500, 1, 1), # Dark Stump | Monster Picture Book
    (5130101, 4032964, 7500, 1, 1), # Stone Golem | Delivery Box
    (5130102, 4032964, 7500, 1, 1), # Dark Stone Golem | Delivery Box
    (5150000, 4032964, 7500, 1, 1), # Mixed Golem | Delivery Box
    (5150002, 4032964, 7500, 1, 1), # Icy Mixed Golem | Delivery Box
    (5150003, 4032964, 7500, 1, 1), # Flaming Mixed Golem | Delivery Box
    (5130101, 4034071, 7500, 1, 1), # Stone Golem | Dejected Green Mushroom Puppet
    (5130102, 4034071, 7500, 1, 1); # Dark Stone Golem | Dejected Green Mushroom Puppet

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (5150000, 4034071, 7500, 1, 1), # Mixed Golem | Dejected Green Mushroom Puppet
    (5150002, 4034071, 7500, 1, 1), # Icy Mixed Golem | Dejected Green Mushroom Puppet
    (5150003, 4034071, 7500, 1, 1), # Flaming Mixed Golem | Dejected Green Mushroom Puppet
    (2230102, 4032968, 7500, 1, 1), # Wild Boar | Tongue Twister Scroll
    (3000007, 4033323, 7500, 1, 1), # Royal Fairy | Lolo's Mop
    (2230100, 4033325, 7500, 1, 1), # Evil Eye | Polishing Sap
    (3230100, 4033326, 7500, 1, 1), # Curse Eye | Anne's Teddy Bear
    (4230100, 4033327, 7500, 1, 1), # Cold Eye | Herb Seed
    (7130100, 4034072, 7500, 1, 1), # Tauromacis | Tauromacis Blood
    (2400003, 4033342, 7500, 1, 1), # Limestone Tokka | Shine Shine Stone
    (2400001, 4033341, 7500, 1, 1), # Alert Grobbler | Alert Grobbler Flower
    (2400005, 4000709, 7500, 1, 1), # Laloong | Laloong Tail
    (2400001, 4000705, 7500, 1, 1), # Alert Grobbler | Alert Grobbler Stalk
    (2400002, 4000706, 7500, 1, 1), # Grumpy Grobbler | Grumpy Grobbler Stalk
    (2400008, 4033350, 7500, 1, 1), # Nefarious Monk Apprentice | West Sanctum Relic
    (2400009, 4033350, 7500, 1, 1), # Nefarious Monk Master | West Sanctum Relic
    (2400013, 4033346, 7500, 1, 1), # Onyx Stonegar | Onyx Stonegar Magic Pebble
    (2400014, 4033345, 7500, 1, 1), # Gravi Stonegar | Gravi Stonegar Magic Pebble
    (6230100, 4033353, 7500, 1, 1), # Wild Kargo | Evil Essence
    (7130100, 4033353, 7500, 1, 1), # Tauromacis | Evil Essence
    (7130101, 4033353, 7500, 1, 1), # Taurospear | Evil Essence
    (8130100, 4033353, 7500, 1, 1), # Jr. Balrog | Evil Essence
    (3230200, 4033354, 7500, 1, 1), # Star Pixie | Pixie Cloud Piece
    (4230106, 4033354, 7500, 1, 1), # Lunar Pixie | Pixie Cloud Piece
    (5120000, 4033354, 7500, 1, 1), # Luster Pixie | Pixie Cloud Piece
    (2100107, 4033380, 7500, 1, 1), # Scarf Plead | Desert Scarf
    (2400003, 4033429, 7500, 1, 1), # Limestone Tokka | King Shine Shine Stone
    (2400001, 4033431, 7500, 1, 1), # Alert Grobbler | Alert Grobbler Dewdrop
    (2400005, 4033344, 7500, 1, 1), # Laloong | Laloong Horn
    (2400004, 4033343, 7500, 1, 1), # Amethyst Tokka | Glow Glow Stone
    (2400004, 4033432, 7500, 1, 1), # Amethyst Tokka | King Glow Glow Stone
    (2400006, 4000710, 7500, 1, 1), # Kaloong | Kaloong Wing
    (2400010, 4033348, 7500, 1, 1), # Heretic Monk Initiate | Lean Food Pouch
    (2400011, 4033348, 7500, 1, 1), # Heretic Monk Apprentice | Lean Food Pouch
    (2400012, 4033348, 7500, 1, 1), # Heretic Leader | Lean Food Pouch
    (5130100, 4033436, 7500, 1, 1), # Drake | Basic Green Essence Stone
    (6230100, 4033438, 7500, 1, 1), # Wild Kargo | Basic Purple Essence Stone
    (3230200, 4033433, 7500, 1, 1), # Star Pixie | Basic Red Essence Stone
    (5140000, 4033440, 7500, 1, 1), # White Fang | Basic White Essence Stone
    (5120002, 4033434, 7500, 1, 1), # Lioner | Basic Orange Essence Stone
    (3110303, 4033449, 7500, 1, 1), # Triple Rumo | White Essence Stone
    (5110302, 4033446, 7500, 1, 1), # Neo Huroid | Blue Essence Stone
    (5250004, 4033450, 7500, 1, 1), # Chao | Black Essence Stone
    (5250007, 4033447, 7500, 1, 1), # Ephenia | Purple Essence Stone
    (3300100, 4034104, 7500, 1, 1), # Mushroom Chandelier | Mushroom Bulb
    (8148007, 4030020, 7500, 1, 1), # Elite Windraider | Wind Mark
    (8148009, 4030021, 7500, 1, 1), # Elite Nightshadow | Night Mark
    (2400106, 4000727, 7500, 1, 1), # Specter Battle Hound | Battle Hound Broken Horn
    (2400109, 4033361, 7500, 1, 1), # Specter Shieldbearer | Specter Commander Badge
    (2400103, 4033410, 7500, 1, 1), # Dinogoth | Dinogoth Shoulder Meat
    (2400100, 4033407, 7500, 1, 1), # Blue Speeyor | Blue Speeyor Fur Coat
    (2400101, 4033408, 7500, 1, 1), # Red Speeyor | Animal Oil
    (2400103, 4000742, 7500, 1, 1), # Dinogoth | Dinogoth Tail
    (2400100, 4000739, 7500, 1, 1), # Blue Speeyor | Blue Speeyor Torn Fur Coat
    (2400104, 4033468, 7500, 1, 1), # Dinoram | Lost Supplies
    (2400101, 4033400, 7500, 1, 1), # Red Speeyor | Fine Firewood Pile
    (2400104, 4033411, 7500, 1, 1), # Dinoram | Dinoram Tenderloin
    (2400104, 4033469, 7500, 1, 1), # Dinoram | Dinoram Tenderloin
    (2400100, 4033470, 7500, 1, 1), # Blue Speeyor | Blue Speeyor Fur Coat
    (2400105, 4033412, 7500, 1, 1), # Dinodon | Dinodon Sharp Fang
    (2400104, 4000743, 7500, 1, 1), # Dinoram | Dinoram Claw
    (2400108, 4000729, 7500, 1, 1), # Specter Miner | Sparkling Specter Stone
    (2400100, 4033483, 7500, 1, 1), # Blue Speeyor | Blue Speeyor Fur Coat
    (2400103, 4033483, 7500, 1, 1), # Dinogoth | Blue Speeyor Fur Coat
    (2400100, 4033484, 7500, 1, 1), # Blue Speeyor | Dinogoth Shoulder Meat
    (2400103, 4033484, 7500, 1, 1), # Dinogoth | Dinogoth Shoulder Meat
    (2400104, 4033474, 7500, 1, 1), # Dinoram | Dinoram Tenderloin
    (2400102, 4033475, 7500, 1, 1), # Yellow Speeyor | Yellow Speeyor Fur Coat
    (2400105, 4033475, 7500, 1, 1), # Dinodon | Yellow Speeyor Fur Coat
    (2400102, 4033476, 7500, 1, 1), # Yellow Speeyor | Dinodon Sharp Fang
    (2400105, 4033476, 7500, 1, 1), # Dinodon | Dinodon Sharp Fang
    (2400101, 4000740, 7500, 1, 1), # Red Speeyor | Red Speeyor Stalk
    (2400110, 4000731, 7500, 1, 1), # Guerrilla Specter | Specter's Deadly Poison
    (2400110, 4033395, 7500, 1, 1), # Guerrilla Specter | Morrow Tree Sap
    (2400110, 4033360, 7500, 1, 1), # Guerrilla Specter | Specter Candy
    (2400107, 4000727, 7500, 1, 1), # Specter Tamer | Battle Hound Broken Horn
    (2400106, 4033413, 7500, 1, 1), # Specter Battle Hound | Tamer's Whip
    (2400107, 4033413, 7500, 1, 1), # Specter Tamer | Tamer's Whip
    (2400110, 4000727, 7500, 1, 1), # Guerrilla Specter | Battle Hound Broken Horn
    (2400106, 4033477, 7500, 1, 1), # Specter Battle Hound | Tamer's Whip
    (2400107, 4033477, 7500, 1, 1), # Specter Tamer | Tamer's Whip
    (2400110, 4033477, 7500, 1, 1), # Guerrilla Specter | Tamer's Whip
    (2400106, 4033478, 7500, 1, 1), # Specter Battle Hound | Specter Candy
    (2400107, 4033478, 7500, 1, 1), # Specter Tamer | Specter Candy
    (2400110, 4033478, 7500, 1, 1), # Guerrilla Specter | Specter Candy
    (2400107, 4000728, 7500, 1, 1), # Specter Tamer | Tamer Hardhat
    (2400108, 4000728, 7500, 1, 1), # Specter Miner | Tamer Hardhat
    (2400112, 4000728, 7500, 1, 1), # Specter Engineer | Tamer Hardhat
    (2400107, 4000729, 7500, 1, 1), # Specter Tamer | Sparkling Specter Stone
    (2400112, 4000729, 7500, 1, 1), # Specter Engineer | Sparkling Specter Stone
    (2400107, 4000733, 7500, 1, 1), # Specter Tamer | Welding Goggles
    (2400108, 4000733, 7500, 1, 1), # Specter Miner | Welding Goggles
    (2400112, 4000733, 7500, 1, 1), # Specter Engineer | Welding Goggles
    (2400111, 4000732, 7500, 1, 1), # Power Specter | Hard Back Horn
    (2400114, 4000735, 7500, 1, 1), # Magician Specter | Specter Cube
    (2400113, 4000734, 7500, 1, 1), # Warrior Specter | Specter's Sword Piece
    (2400114, 4000734, 7500, 1, 1), # Magician Specter | Specter's Sword Piece
    (2400113, 4000735, 7500, 1, 1), # Warrior Specter | Specter Cube
    (2400118, 4000737, 7500, 1, 1), # Purple Reagent Rock | Broken Flask
    (2400116, 4000738, 7500, 1, 1), # Red Totem Staff | Ragged Cloth
    (2400116, 4033364, 7500, 1, 1), # Red Totem Staff | Creation Essence
    (2400114, 4033578, 7500, 1, 1), # Magician Specter | Magnus's Letter
    (2400109, 4000730, 7500, 1, 1), # Specter Shieldbearer | Specter Wooden Shield
    (2400109, 4033424, 7500, 1, 1), # Specter Shieldbearer | Fine Wooden Shield
    (2400105, 4000744, 7500, 1, 1), # Dinodon | Dinodon Fang
    (2400102, 4000741, 7500, 1, 1), # Yellow Speeyor | Yellow Speeyor Torn Fur Coat
    (2400112, 4000730, 7500, 1, 1), # Specter Engineer | Specter Wooden Shield
    (2400100, 4033574, 7500, 1, 1), # Blue Speeyor | Blue Speeyor Fur Coat
    (8630017, 4033939, 7500, 1, 1), # Corrupted Advanced Magician | Black Spellbook
    (8630037, 4033939, 7500, 1, 1), # Corrupted Advanced Magician | Black Spellbook
    (8630017, 4033943, 7500, 1, 1), # Corrupted Advanced Magician | Magic Orb
    (8630037, 4033943, 7500, 1, 1), # Corrupted Advanced Magician | Magic Orb
    (8630010, 4033944, 7500, 1, 1), # Permeating Solitude | Tower Fragment
    (8630011, 4033944, 7500, 1, 1), # Permeating Terror | Tower Fragment
    (8630013, 4033944, 7500, 1, 1), # Permeating Anxiety | Tower Fragment
    (8630014, 4033944, 7500, 1, 1), # Permeating Vanity | Tower Fragment
    (8630030, 4033944, 7500, 1, 1), # Permeating Solitude | Tower Fragment
    (8630031, 4033944, 7500, 1, 1), # Permeating Terror | Tower Fragment
    (8630033, 4033944, 7500, 1, 1), # Permeating Anxiety | Tower Fragment
    (2700021, 4034166, 7500, 1, 1), # Shooting Mote | Brush
    (2700022, 4034166, 7500, 1, 1), # Flying Mote | Brush
    (2700023, 4034166, 7500, 1, 1), # Rolling Mote | Brush
    (2700024, 4034166, 7500, 1, 1), # Poison Mote | Brush
    (2700025, 4034166, 7500, 1, 1), # Hallow Mote | Brush
    (2700021, 4034167, 7500, 1, 1), # Shooting Mote | The Handsome Man
    (2700022, 4034167, 7500, 1, 1), # Flying Mote | The Handsome Man
    (2700023, 4034167, 7500, 1, 1), # Rolling Mote | The Handsome Man
    (2700024, 4034167, 7500, 1, 1), # Poison Mote | The Handsome Man
    (2700025, 4034167, 7500, 1, 1), # Hallow Mote | The Handsome Man
    (2700021, 4034168, 7500, 1, 1), # Shooting Mote | Howl of Love
    (2700022, 4034168, 7500, 1, 1), # Flying Mote | Howl of Love
    (2700023, 4034168, 7500, 1, 1), # Rolling Mote | Howl of Love
    (2700024, 4034168, 7500, 1, 1), # Poison Mote | Howl of Love
    (2700025, 4034168, 7500, 1, 1), # Hallow Mote | Howl of Love
    (2700021, 4034169, 7500, 1, 1), # Shooting Mote | Ancient Hearts
    (2700022, 4034169, 7500, 1, 1), # Flying Mote | Ancient Hearts
    (2700023, 4034169, 7500, 1, 1), # Rolling Mote | Ancient Hearts
    (2700024, 4034169, 7500, 1, 1), # Poison Mote | Ancient Hearts
    (2700025, 4034169, 7500, 1, 1), # Hallow Mote | Ancient Hearts
    (2700021, 4034170, 7500, 1, 1), # Shooting Mote | War of the Heart
    (2700022, 4034170, 7500, 1, 1), # Flying Mote | War of the Heart
    (2700023, 4034170, 7500, 1, 1), # Rolling Mote | War of the Heart
    (2700024, 4034170, 7500, 1, 1), # Poison Mote | War of the Heart
    (2700025, 4034170, 7500, 1, 1), # Hallow Mote | War of the Heart
    (2700021, 4034171, 7500, 1, 1), # Shooting Mote | Love Strike
    (2700022, 4034171, 7500, 1, 1), # Flying Mote | Love Strike
    (2700023, 4034171, 7500, 1, 1), # Rolling Mote | Love Strike
    (2700024, 4034171, 7500, 1, 1), # Poison Mote | Love Strike
    (2700025, 4034171, 7500, 1, 1), # Hallow Mote | Love Strike
    (8230062, 4036341, 7500, 1, 1), # Darkling Stump | Moonlight Mushroom Powder
    (8230065, 4036342, 7500, 1, 1), # Darkling Blob | Peanut
    (2400406, 4036171, 7500, 1, 1), # Blue Larimar | Larimar Shard
    (2400330, 4036490, 7500, 1, 1), # Prickly Hedgehog | Big Nail
    (2400332, 4036491, 7500, 1, 1), # Sketchy Hooligan | Mysterious Part
    (2400302, 4036492, 7500, 1, 1), # Nutria | Scratched Cast Iron Plate
    (2400307, 4036493, 7500, 1, 1), # Specter Stray Dog | Wiggling Tail
    (3401000, 4034061, 7500, 1, 1), # Palm Tree Slime | Glass Bottle
    (3401001, 4034061, 7500, 1, 1), # Coconut Slime | Glass Bottle
    (3401002, 4034061, 7500, 1, 1), # Emerald Clam Slime | Glass Bottle
    (3401003, 4034061, 7500, 1, 1), # Violet Clam Slime | Glass Bottle
    (3401004, 4034061, 7500, 1, 1), # Seagull Slime | Glass Bottle
    (3401005, 4034061, 7500, 1, 1), # Red Tube Slime | Glass Bottle
    (3401006, 4034061, 7500, 1, 1), # Blue Tube Slime | Glass Bottle
    (3401007, 4034061, 7500, 1, 1), # Shrimp Slime | Glass Bottle
    (3401008, 4034061, 7500, 1, 1), # Flying Fish Slime | Glass Bottle
    (3401009, 4034061, 7500, 1, 1), # Starfish Octopus Slime | Glass Bottle
    (3401010, 4034061, 7500, 1, 1), # Seashell Octopus Slime | Glass Bottle
    (3401011, 4034061, 7500, 1, 1), # Captain Darkgoo | Glass Bottle
    (2400550, 4036576, 7500, 1, 1), # Pine Nut | Stiff Wooden Block
    (2400551, 4036577, 7500, 1, 1), # Sikhye Crock | Jar of Distilled Spirits
    (2400552, 4036578, 7500, 1, 1), # Broken Sikhye Crock | Jar Fragment
    (2400554, 4036579, 7500, 1, 1), # Rolling Gourd | Discarded Gourd
    (2400556, 4036584, 7500, 1, 1), # Sewer Rat | Black Feather
    (2400556, 4036588, 7500, 1, 1), # Sewer Rat | Ring-handled Wooden Board
    (2400556, 4036589, 7500, 1, 1), # Sewer Rat | Crumpled Note
    (2400555, 4036580, 7500, 1, 1), # Chunky Striped Gourd | Gourd Vine
    (2400556, 4036581, 7500, 1, 1), # Sewer Rat | Silver Key
    (2400557, 4036582, 7500, 1, 1), # Vicious Sewer Rat | Gold Key
    (2400553, 4036583, 7500, 1, 1), # Jar of Stagnant Water | Bowl of Mineral Water
    (2400601, 4036657, 7500, 1, 1), # Town Square Pot | Ristonian Silver
    (2400602, 4036658, 7500, 1, 1), # Monstrous News Machine | Ristonian Dispatch
    (2400603, 4036659, 7500, 1, 1), # Torchlighter | Small Ember
    (2600009, 4000914, 7500, 1, 1), # Blood Harp | Blood Harp Crest Feather
    (2600019, 4000921, 7500, 1, 1), # Brexton | Brexton's Egg
    (2600000, 4000906, 7500, 1, 1), # Beetle | Beetle Horn
    (2600001, 4000906, 7500, 1, 1), # Dual Beetle | Beetle Horn
    (2600102, 4000930, 7500, 1, 1), # Jr. Cactus | Weird Cactus
    (2600103, 4000930, 7500, 1, 1), # Cactus | Weird Cactus
    (2600104, 4000930, 7500, 1, 1), # Royal Cactus | Weird Cactus
    (2600102, 4000932, 7500, 1, 1), # Jr. Cactus | Desert Flower
    (2600103, 4000932, 7500, 1, 1), # Cactus | Desert Flower
    (2600104, 4000932, 7500, 1, 1), # Royal Cactus | Desert Flower
    (2600109, 4000936, 7500, 1, 1), # Meerkat | Meerkat's Telescope
    (2600200, 4000953, 7500, 1, 1), # Green Mushroom | Green Mushroom Hat
    (2600202, 4000955, 7500, 1, 1), # Orange Mushroom | Orange Mushroom Hat
    (2600204, 4000957, 7500, 1, 1), # Blue Mushroom | Blue Mushroom Hat
    (2600206, 4000959, 7500, 1, 1), # Zombie Mushroom | Zombie Mushroom Ticket
    (2600307, 4009007, 7500, 1, 1), # Red Porky | Red Porky Thorn
    (2600308, 4009008, 7500, 1, 1), # Black Porky | Black Porky Thorn
    (2600320, 4009019, 7500, 1, 1), # Sr. Bellflower Root | Bellflower
    (2600400, 4009027, 7500, 1, 1), # Patrol Robot | Patrol Robot Memory Card
    (2600400, 4009031, 7500, 1, 1), # Patrol Robot | Patrol Robot S Memory Card
    (2600411, 4009038, 7500, 1, 1), # Big Boulder Muncher | Ore Fragment
    (2600413, 4009040, 7500, 1, 1), # Racoco | Shovel
    (2600415, 4009042, 7500, 1, 1), # Cart Bear | Cart
    (2600417, 4009044, 7500, 1, 1), # Raco | Safety Hat
    (2600414, 4009041, 7500, 1, 1), # Big Spider | Big Spider Antenna
    (2600414, 4009045, 7500, 1, 1), # Big Spider | Ore Muncher Sample
    (2600502, 4009062, 7500, 1, 1), # Rumo | Rumo Leaf
    (2600505, 4009065, 7500, 1, 1), # Iron Mutae | Steel Fragment
    (2600507, 4009067, 7500, 1, 1), # Mithril Mutae | Mithril Fragment
    (2600510, 4009071, 7500, 1, 1), # Homun | Clean Dew
    (2600511, 4009071, 7500, 1, 1), # Homunculus | Clean Dew
    (2600512, 4009071, 7500, 1, 1), # Homunscullo | Clean Dew
    (2600513, 4009071, 7500, 1, 1), # Saitie | Clean Dew
    (2600516, 4009073, 7500, 1, 1), # Neo Huroid | Alchemy Stone
    (2600516, 4009075, 7500, 1, 1), # Neo Huroid | Tangled Wires
    (2600516, 4009076, 7500, 1, 1), # Neo Huroid | Plug
    (2600516, 4009077, 7500, 1, 1), # Neo Huroid | Broken Iron Heart
    (2600603, 4009081, 7500, 1, 1), # Roloduck | Youngest Duckie
    (2600604, 4009081, 7500, 1, 1), # Toy Trojan | Youngest Duckie
    (2600603, 4009082, 7500, 1, 1), # Roloduck | Plastic Toy Sword
    (2600604, 4009082, 7500, 1, 1), # Toy Trojan | Plastic Toy Sword
    (2600606, 4009084, 7500, 1, 1), # Master Robo | Master Robo Battery
    (2600701, 4009109, 7500, 1, 1), # Memory Monk | Memory Cloth
    (2600702, 4009110, 7500, 1, 1), # Memory Monk Trainee | Memory Cone Hat
    (2600703, 4009111, 7500, 1, 1), # Memory Guardian | Memory Helm
    (2600704, 4009112, 7500, 1, 1), # Chief Memory Guardian | Memory Heart
    (2600707, 4009115, 7500, 1, 1), # Qualm Monk Trainee | Qualm Cone Hat
    (2600708, 4009116, 7500, 1, 1), # Qualm Guardian | Qualm Helm
    (2600709, 4009117, 7500, 1, 1), # Chief Qualm Guardian | Qualm Heart
    (2600711, 4009119, 7500, 1, 1), # Oblivion Monk | Oblivion Cloth
    (2600712, 4009120, 7500, 1, 1), # Oblivion Monk Trainee | Oblivion Cone Hat
    (2600714, 4009122, 7500, 1, 1), # Chief Oblivion Guardian | Oblivion Heart
    (5120503, 4034216, 7500, 1, 1), # Straw Target Dummy | Dry Straw Bundle
    (5120503, 4034217, 7500, 1, 1), # Straw Target Dummy | Dry Straw Bundle
    (9601054, 4034364, 7500, 1, 1), # Wu Tien Eagle | Wu Tien Eagle Meat
    (9601055, 4034364, 7500, 1, 1), # Wu Tien Eagle | Wu Tien Eagle Meat
    (9601056, 4034364, 7500, 1, 1), # Wu Tien Eagle | Wu Tien Eagle Meat
    (9601054, 4034365, 7500, 1, 1), # Wu Tien Eagle | Wu Tien Eagle Meat
    (9601055, 4034365, 7500, 1, 1), # Wu Tien Eagle | Wu Tien Eagle Meat
    (9601056, 4034365, 7500, 1, 1), # Wu Tien Eagle | Wu Tien Eagle Meat
    (9421511, 4033272, 7500, 1, 1), # Oda Scout | Scout's Report
    (9421514, 4033274, 7500, 1, 1), # Oda Warrior | Mysterious Foxtail
    (5120505, 4033275, 7500, 1, 1), # Cute Reindeer | Oda Clan Seal
    (6130207, 4033275, 7500, 1, 1), # Peach Monkey | Oda Clan Seal
    (6130209, 4033276, 7500, 1, 1), # Sage Cat | Volatile Oda Clan Seal
    (9450030, 4034132, 7500, 1, 1), # Oda Spirit Walker | Princess No's Orders
    (9450030, 4034133, 7500, 1, 1), # Oda Spirit Walker | Red Jar
    (9450034, 4034137, 7500, 1, 1), # Mammoth Moon Bunny | Lunar Sand
    (9390939, 4034050, 7500, 1, 1), # Kobold | Kobold's Wooden Club
    (9390939, 4034000, 7500, 1, 1), # Kobold | Kobold's Sword
    (9390914, 4034003, 7500, 1, 1), # Kobold Shooter | Red Pouch
    (9390933, 4034004, 7500, 1, 1), # Kobold Zapper | Blue Pouch
    (9390943, 4034001, 7500, 1, 1), # Disgusting Kobold | Spear Kobold's Claw
    (9600013, 4034726, 7500, 1, 1), # White Tiger Swordsman | White Tiger Swordsman Demon Orb
    (9600014, 4034727, 7500, 1, 1), # Eagle Swordsman | Eagle Swordsman Demon Orb
    (9600015, 4034728, 7500, 1, 1), # Male Thief | Male Thief Demon Orb
    (9600017, 4034729, 7500, 1, 1), # Censer | Censer Demon Orb
    (9600019, 4034730, 7500, 1, 1), # Mini Gold Martial Artist | Mini Gold Martial Artist Demon Orb
    (9600021, 4034731, 7500, 1, 1), # Golden Giant | Golden Giant Demon Orb
    (9600024, 4034732, 7500, 1, 1), # Silver Spearman | Silver Spearman Demon Orb
    (9601062, 4034733, 7500, 1, 1), # Haunted Graystone | Haunted Graystone Demon Orb
    (9601065, 4034734, 7500, 1, 1), # Red Zhanshi Spirit | Red Zhanshi Spirit Demon Orb
    (9601066, 4034735, 7500, 1, 1), # Haunted Helmet | Haunted Helmet Demon Orb
    (9600310, 4009368, 7500, 1, 1), # Bicycle Ghost | Bike Tire
    (9600302, 4009358, 7500, 1, 1), # Deranged Duck | Duck Egg
    (9600303, 4009361, 7500, 1, 1), # Nutty Sheep | Mutton
    (9600304, 4009361, 7500, 1, 1), # Loony Sheep | Mutton
    (9600301, 4034644, 7500, 1, 1), # Cuckoo Chicken | Rooster Cell Sample
    (9600303, 4034644, 7500, 1, 1), # Nutty Sheep | Rooster Cell Sample
    (9600307, 4034644, 7500, 1, 1), # Bonkers Cow | Rooster Cell Sample
    (9600301, 4034645, 7500, 1, 1), # Cuckoo Chicken | Sheep Cell Sample
    (9600303, 4034645, 7500, 1, 1), # Nutty Sheep | Sheep Cell Sample
    (9600307, 4034645, 7500, 1, 1), # Bonkers Cow | Sheep Cell Sample
    (9600301, 4034646, 7500, 1, 1), # Cuckoo Chicken | Cow Cell Sample
    (9600303, 4034646, 7500, 1, 1), # Nutty Sheep | Cow Cell Sample
    (9600307, 4034646, 7500, 1, 1), # Bonkers Cow | Cow Cell Sample
    (9600311, 4009367, 7500, 1, 1), # Sign Ghost | Broken Sign
    (9600312, 4009369, 7500, 1, 1), # Stoplight Ghost | LED Light
    (9600313, 4009370, 7500, 1, 1), # Shopper Jiangshi | Shopping Bag
    (9600314, 4009371, 7500, 1, 1), # Traveler Jiangshi | Broken Sunglasses
    (9600317, 4009372, 7500, 1, 1), # Poison Jiangshi | Jiangshi Poison
    (9402237, 4036372, 7500, 1, 1), # Shrelephant | Shrelephant Meat
    (9402245, 4036374, 7500, 1, 1), # Raging Hellfang | Raging Hellfang Meat
    (9402245, 4036383, 7500, 1, 1), # Raging Hellfang | Raging Hellfang Meat
    (9402237, 4036384, 7500, 1, 1), # Shrelephant | Shrelephant Meat
    (9402242, 4036385, 7500, 1, 1), # Raging Werebeast | Raging Werebeast Fur
    (9402237, 4036378, 7500, 1, 1), # Shrelephant | Shrelephant Meat
    (9402328, 2439168, 7500, 1, 1), # Jellyrash | Jellyrash Rope
    (9402238, 4036389, 7500, 1, 1), # Eyeful | Solid Piece of Wood
    (9402328, 4036379, 7500, 1, 1), # Jellyrash | Jellyrash Rope
    (2600318, 4009018, 7500, 1, 1), # Ginseng Jar | Ginseng Water
    (9402240, 4036373, 7500, 1, 1), # Petreefied Eyeful | Firewood
    (5100100, 4034633, 7500, 1, 1), # Moon Bunny | Rice Flour
    (2110200, 4033197, 7500, 1, 1), # Horny Mushroom | Lost Document
    (1210104, 4033197, 7500, 1, 1), # Blue Ribbon Pig | Lost Document
    (2110200, 4033198, 7500, 1, 1), # Horny Mushroom | Lost Confidential Document
    (1210104, 4033198, 7500, 1, 1), # Blue Ribbon Pig | Lost Confidential Document
    (2110200, 4033218, 7500, 1, 1), # Horny Mushroom | Lost Top Secret Document
    (1210104, 4033218, 7500, 1, 1), # Blue Ribbon Pig | Lost Top Secret Document
    (2100100, 4032888, 7500, 1, 1), # White Hare | Lunar Dew
    (2100101, 4032888, 7500, 1, 1), # Brown Hare | Lunar Dew
    (2100100, 4032889, 7500, 1, 1), # White Hare | Lunar Dew
    (2100101, 4032889, 7500, 1, 1), # Brown Hare | Lunar Dew
    (2100100, 4032890, 7500, 1, 1), # White Hare | Lunar Dew
    (2100101, 4032890, 7500, 1, 1), # Brown Hare | Lunar Dew
    (2100100, 4032891, 7500, 1, 1), # White Hare | Lunar Dew
    (2100101, 4032891, 7500, 1, 1), # Brown Hare | Lunar Dew
    (9390808, 4033993, 7500, 1, 1), # Cutthroat Delfino | Delfinos Hideout Map Fragment
    (9390809, 4033993, 7500, 1, 1), # Sureshot Delfino | Delfinos Hideout Map Fragment
    (9390810, 4033993, 7500, 1, 1), # Voodoo Delfino | Delfinos Hideout Map Fragment
    (9390811, 4033993, 7500, 1, 1), # Ripclaw Delfino | Delfinos Hideout Map Fragment
    (9390866, 4033995, 7500, 1, 1), # Il Capo | Il Capo File
    (2600209, 4000962, 7500, 1, 1), # Stone Golem | Weird Golem Fragment
    (2600210, 4000962, 7500, 1, 1), # Dark Stone Golem | Weird Golem Fragment
    (2600211, 4000962, 7500, 1, 1), # Mixed Golem | Weird Golem Fragment
    (2600212, 4000962, 7500, 1, 1), # Icy Mixed Golem | Weird Golem Fragment
    (2600213, 4000962, 7500, 1, 1), # Flaming Mixed Golem | Weird Golem Fragment
    (8630005, 4033938, 7500, 1, 1), # Burning Solitude | Worn Wooden Comb
    (8630006, 4033938, 7500, 1, 1), # Burning Terror | Worn Wooden Comb
    (8630007, 4033938, 7500, 1, 1), # Burning Rage | Worn Wooden Comb
    (8630008, 4033938, 7500, 1, 1), # Burning Anxiety | Worn Wooden Comb
    (8630009, 4033938, 7500, 1, 1), # Burning Vanity | Worn Wooden Comb
    (8630025, 4033938, 7500, 1, 1), # Burning Solitude | Worn Wooden Comb
    (8630026, 4033938, 7500, 1, 1), # Burning Terror | Worn Wooden Comb
    (8630027, 4033938, 7500, 1, 1), # Burning Anger | Worn Wooden Comb
    (8630028, 4033938, 7500, 1, 1), # Burning Anxiety | Worn Wooden Comb
    (8630029, 4033938, 7500, 1, 1), # Burning Vanity | Worn Wooden Comb
    (4230129, 4036073, 7500, 1, 1), # Barnard Gray | Gray Popstar Collectible Figurine
    (4230130, 4036072, 7500, 1, 1), # Ultra Gray | SpaceCoin
    (8220019, 4033080, 7500, 1, 1), # Corrupted Kentaurus | Broken Polearm Piece
    (3300101, 4034105, 7500, 1, 1), # Mushroom Knight Armor | Mushroom Armor
    (3110102, 4031129, 7500, 1, 1), # Ratz | Savory Cheese
    (7130500, 4033232, 7500, 1, 1), # Rash | Peridot
    (2400009, 4033347, 7500, 1, 1), # Nefarious Monk Master | Magnus's Letter
    (2400012, 4033465, 7500, 1, 1), # Heretic Leader | Magnus's Letter
    (2400010, 4033466, 7500, 1, 1), # Heretic Monk Initiate | West Sanctum Relic
    (2400011, 4033466, 7500, 1, 1), # Heretic Monk Apprentice | West Sanctum Relic
    (2400012, 4033466, 7500, 1, 1), # Heretic Leader | West Sanctum Relic
    (9402257, 4036382, 7500, 1, 1), # Raging Leatty | Leatty Crystal
    (9300550, 4033460, 7500, 1, 1), # Nefarious Priest | Nefarious Pouch
    (9300551, 4033460, 7500, 1, 1), # Nefarious Priest | Nefarious Pouch
    (9300552, 4033460, 7500, 1, 1), # Nefarious Priest | Nefarious Pouch
    (8610011, 4032925, 7500, 1, 1), # Advanced Knight B | Superior Magic Wand
    (4310002, 4036018, 7500, 1, 1), # Next-gen Speaker | Moral Support
    (4310004, 4036018, 7500, 1, 1), # Blue Raspberry Jelly Juice | Moral Support
    (4310005, 4036018, 7500, 1, 1), # Grape Jelly Juice | Moral Support
    (4310006, 4036018, 7500, 1, 1), # Enraged Espresso Machine | Moral Support
    (4310007, 4036018, 7500, 1, 1), # Green Notebook | Moral Support
    (4310008, 4036018, 7500, 1, 1), # Blue Notebook | Moral Support
    (4310009, 4036018, 7500, 1, 1), # Yellow Headphones | Moral Support
    (4310010, 4036018, 7500, 1, 1), # Red Headphones | Moral Support
    (4310011, 4036018, 7500, 1, 1), # Killer Cosmetics | Moral Support
    (4310012, 4036018, 7500, 1, 1), # Deadly Dressing Table | Moral Support
    (4310013, 4036018, 7500, 1, 1), # Harried Hair Roller | Moral Support
    (4310014, 4036018, 7500, 1, 1), # Peeved Perm Machine | Moral Support
    (4310015, 4036018, 7500, 1, 1), # Spirit of Rock | Moral Support
    (4310016, 4036018, 7500, 1, 1), # Frenzied Peri Dotts | Moral Support
    (4310017, 4036018, 7500, 1, 1), # [*] Enraged Espresso Machine | Moral Support
    (4310018, 4036018, 7500, 1, 1), # [*] Blue Notebook | Moral Support
    (4310019, 4036018, 7500, 1, 1), # [*] Red Headphones | Moral Support
    (4310020, 4036018, 7500, 1, 1), # [*] Deadly Dressing Table | Moral Support
    (4310021, 4036018, 7500, 1, 1), # [*] Harried Hair Roller | Moral Support
    (5130101, 4033639, 7500, 1, 1), # Stone Golem | Rugged Bottle
    (5130102, 4033639, 7500, 1, 1), # Dark Stone Golem | Rugged Bottle
    (5150000, 4033639, 7500, 1, 1), # Mixed Golem | Rugged Bottle
    (5150002, 4033639, 7500, 1, 1), # Icy Mixed Golem | Rugged Bottle
    (5150003, 4033639, 7500, 1, 1), # Flaming Mixed Golem | Rugged Bottle
    (2400336, 4036494, 7500, 1, 1), # Blob Glooger | Device Button
    (2400337, 4036495, 7500, 1, 1), # Spine Glooger | Device Part
    (9600305, 4009362, 7500, 1, 1), # Unhinged Goat | Goat Milk
    (9600306, 4009362, 7500, 1, 1), # Batty Goat | Goat Milk
    (9600313, 4009373, 7500, 1, 1), # Shopper Jiangshi | Tattered Jiangshi Talisman
    (9600314, 4009373, 7500, 1, 1), # Traveler Jiangshi | Tattered Jiangshi Talisman
    (9600315, 4009373, 7500, 1, 1), # Pale Jiangshi | Tattered Jiangshi Talisman
    (9600316, 4009373, 7500, 1, 1), # Blood Jiangshi | Tattered Jiangshi Talisman
    (9600317, 4009373, 7500, 1, 1), # Poison Jiangshi | Tattered Jiangshi Talisman
    (9600318, 4009373, 7500, 1, 1), # Diabolic Jiangshi | Tattered Jiangshi Talisman
    (2400107, 4033423, 7500, 1, 1), # Specter Tamer | Tamer's Whip
    (8140700, 4033118, 7500, 1, 1), # Blue Dragon Turtle | Dragon Warrant
    (8140701, 4033118, 7500, 1, 1), # Red Dragon Turtle | Dragon Warrant
    (8140702, 4033118, 7500, 1, 1), # Rexton | Dragon Warrant
    (8140703, 4033118, 7500, 1, 1), # Brexton | Dragon Warrant
    (8150300, 4033118, 7500, 1, 1), # Red Wyvern | Dragon Warrant
    (8150301, 4033118, 7500, 1, 1), # Blue Wyvern | Dragon Warrant
    (8150302, 4033118, 7500, 1, 1), # Dark Wyvern | Dragon Warrant
    (8150303, 4033118, 7500, 1, 1), # [*]Dark Wyvern | Dragon Warrant
    (4230503, 4032874, 7500, 1, 1), # Blue Flower Serpent | Unstoppable Wind
    (3110300, 4032874, 7500, 1, 1), # Cube Slime | Unstoppable Wind
    (9600300, 4009359, 7500, 1, 1), # Raving Chicken | Chicken Meat
    (9600301, 4009359, 7500, 1, 1), # Cuckoo Chicken | Chicken Meat
    (9600307, 4009363, 7500, 1, 1), # Bonkers Cow | Prime Beef
    (9600308, 4009363, 7500, 1, 1), # Mad Plow Ox | Prime Beef
    (9600307, 4009364, 7500, 1, 1), # Bonkers Cow | Fresh Milk
    (9600308, 4009364, 7500, 1, 1), # Mad Plow Ox | Fresh Milk
    (9600308, 4009365, 7500, 1, 1), # Mad Plow Ox | Rusty Plow
    (9600309, 4009366, 7500, 1, 1), # Giant Centipede | Giant Centipede Leg
    (9600318, 4009374, 7500, 1, 1), # Diabolic Jiangshi | Diabolic Jiangshi Talisman
    (9810101, 4033201, 7500, 1, 1), # Dust Box | Delivery Box
    (8230062, 4036343, 7500, 1, 1), # Darkling Stump | Thorn Tree Sap
    (8230063, 4036343, 7500, 1, 1), # Darkling Axe Stump | Thorn Tree Sap
    (9300496, 4033098, 7500, 1, 1), # Black Wing Henchmen | Black Wing Emblem
    (2600003, 4000909, 7500, 1, 1), # Rash | Rash's Fuzzball
    (2600004, 4000909, 7500, 1, 1), # Dark Rash | Rash's Fuzzball
    (2600008, 4000913, 7500, 1, 1), # Harp | Harp Wing Feather
    (2600102, 4000931, 7500, 1, 1), # Jr. Cactus | Nasty Thorn
    (2600103, 4000931, 7500, 1, 1), # Cactus | Nasty Thorn
    (2600104, 4000931, 7500, 1, 1), # Royal Cactus | Nasty Thorn
    (2600115, 4000941, 7500, 1, 1), # Dark Sand Dwarf | Red Sand Crystal
    (2600116, 4000942, 7500, 1, 1), # Intruder | Intruder's Pouch
    (2600608, 4009086, 7500, 1, 1), # Tick | Small Spring
    (2600609, 4009086, 7500, 1, 1), # Tick-Tock | Small Spring
    (2600610, 4009088, 7500, 1, 1), # Chronos | Small Cog
    (2600611, 4009088, 7500, 1, 1), # Platoon Chronos | Small Cog
    (2600612, 4009088, 7500, 1, 1), # Master Chronos | Small Cog
    (2600510, 4009070, 7500, 1, 1), # Homun | Test Beaker
    (2600511, 4009070, 7500, 1, 1), # Homunculus | Test Beaker
    (2600512, 4009070, 7500, 1, 1), # Homunscullo | Test Beaker
    (2600513, 4009070, 7500, 1, 1), # Saitie | Test Beaker
    (2600500, 4009064, 7500, 1, 1), # Cube Slime | Purple Liquid
    (2600501, 4009064, 7500, 1, 1), # Megran | Purple Liquid
    (2600502, 4009064, 7500, 1, 1), # Rumo | Purple Liquid
    (2600503, 4009064, 7500, 1, 1), # Triple Rumo | Purple Liquid
    (3210800, 4001040, 7500, 1, 1), # Lupin | Lupin Eraser
    (4230102, 4001041, 7500, 1, 1), # Wraith | Wraith Eraser
    (1120100, 4001043, 7500, 1, 1), # Octopus | Octopus Eraser
    (-1, 4034895, 3000, 1, 1), # Global | Litter
    (-1, 4034894, 3000, 1, 1), # Global | Mana Particles
    (-1, 4034897, 3000, 1, 1), # Global | Valuable Loot
    (-1, 4034898, 3000, 1, 1), # Global | Awesome Monster Loot
    (-1, 4034893, 3000, 1, 1), # Global | Strange Animal Fur
    (4230122, 4000058, 7500, 1, 1), # Nependeath | Nependeath Seed
    (9833483, 4000058, 7500, 1, 1), # Nependeath | Nependeath Seed
    (4230122, 2022039, 100, 1, 1), # Nependeath | Nependeath's Honey
    (9833483, 2022039, 100, 1, 1), # Nependeath | Nependeath's Honey
    (4230122, 4031200, 7500, 1, 1), # Nependeath | Sap of Nependeath
    (9833483, 4031200, 7500, 1, 1), # Nependeath | Sap of Nependeath
    (5100100, 4034629, 7500, 1, 1), # Moon Bunny | Mugwort Juice
    (8210013, 4033114, 10000, 1, 1), # Prison Guard Ani | Congealed Evil
    (9500400, 4031223, 10000, 1, 1), # Gourd | Box of Jewelry
    (9500400, 4031224, 10000, 1, 1), # Gourd | Warrant of Attachment
    (9300514, 4000633, 10000, 1, 1), # Lich | Red Contract Orb
    (9300514, 4000336, 10000, 1, 1), # Lich | Bible of the Corrupt
    (3501103, 2630159, 3000, 1, 1), # Hay Bundle | Hay
    (3501108, 4036503, 7500, 1, 1), # Blue Spring Sprite | Pure Water
    (3501109, 4036503, 7500, 1, 1), # Deep Spring Sprite | Pure Water
    (3501108, 4036505, 7500, 1, 1), # Blue Spring Sprite | Pure Water
    (3501109, 4036505, 7500, 1, 1), # Deep Spring Sprite | Pure Water
    (3501108, 2630243, 7500, 1, 1), # Blue Spring Sprite | Pure Water
    (3501109, 2630243, 7500, 1, 1), # Deep Spring Sprite | Pure Water
    (8610022, 4032922, 7500, 1, 1), # Official Knight A | Cygnus Knight Emblem
    (8145000, 4033235, 7500, 1, 1), # Creeper Scarlion | Entry Ticket
    (8145000, 4036092, 7500, 1, 1), # Creeper Scarlion | Creeper Scarlion's Ribbon
    (8145003, 4033237, 7500, 1, 1), # Canterbear | Lost Walkie Talkie
    (8145005, 4033239, 7500, 1, 1), # Colabear | Fantastic Combo Set
    (8145008, 4033244, 7500, 1, 1), # Bearic the White | Cotton Candy
    (8145007, 4033245, 7500, 1, 1), # Jellybus | Sparkling Cleaner
    (8145007, 4036093, 7500, 1, 1), # Jellybus | Tool Bag
    (9420533, 4000471, 7500, 1, 1), # Rodeo | Rodeo Rider
    (9100045, 4032498, 7500, 1, 1), # Stump | Thick Branch
    (8145101, 1003534, 500, 1, 1), # Angry Targa | Ludi Targa Hat
    (8145102, 1003534, 500, 1, 1), # Angry Targa | Ludi Targa Hat
    (8145201, 1003535, 500, 1, 1), # Furious Scarboss | Ludi Scarlion Hat
    (8145202, 1003535, 500, 1, 1), # Furious Scarboss | Ludi Scarlion Hat
    (8145101, 1082719, 500, 1, 1), # Angry Targa | Heart Bracelet
    (8145102, 1082719, 500, 1, 1), # Angry Targa | Heart Bracelet
    (8145201, 1082719, 500, 1, 1), # Furious Scarboss | Heart Bracelet
    (8145202, 1082719, 500, 1, 1), # Furious Scarboss | Heart Bracelet
    (8145101, 1004219, 40, 1, 1), # Angry Targa | Eclectic Fennel
    (8145101, 1004220, 40, 1, 1), # Angry Targa | Eclectic Coral
    (8145101, 1004221, 40, 1, 1), # Angry Targa | Eclectic Rapid
    (8145101, 1004222, 40, 1, 1), # Angry Targa | Eclectic Chive
    (8145101, 1004223, 40, 1, 1), # Angry Targa | Eclectic Conrad Henkel
    (8145101, 1004224, 40, 1, 1), # Angry Targa | Muspell Warrior Hat
    (8145101, 1004225, 40, 1, 1), # Angry Targa | Muspell Magician Hat
    (8145101, 1004226, 40, 1, 1), # Angry Targa | Muspell Bowman Hat
    (8145101, 1004227, 40, 1, 1), # Angry Targa | Muspell Thief Hat
    (8145101, 1004228, 40, 1, 1), # Angry Targa | Muspell Pirate Hat
    (8145101, 1052789, 40, 1, 1), # Angry Targa | Eclectic Taragon
    (8145101, 1052790, 40, 1, 1), # Angry Targa | Eclectic Myst Blue
    (8145101, 1052791, 40, 1, 1), # Angry Targa | Eclectic Evernew
    (8145101, 1052792, 40, 1, 1), # Angry Targa | Eclectic Prinsid
    (8145101, 1052793, 40, 1, 1), # Angry Targa | Eclectic Burgunt
    (8145101, 1052794, 40, 1, 1), # Angry Targa | Muspell Warrior Suit
    (8145101, 1052795, 40, 1, 1), # Angry Targa | Muspell Magician Suit
    (8145101, 1052796, 40, 1, 1), # Angry Targa | Muspell Bowman Suit
    (8145101, 1052797, 40, 1, 1), # Angry Targa | Muspell Thief Suit
    (8145101, 1052798, 40, 1, 1), # Angry Targa | Muspell Pirate Suit
    (8145101, 1072957, 40, 1, 1), # Angry Targa | Eclectic Grabbe
    (8145101, 1072958, 40, 1, 1), # Angry Targa | Eclectic Cabatina
    (8145101, 1072959, 40, 1, 1), # Angry Targa | Eclectic Rontano
    (8145101, 1072960, 40, 1, 1), # Angry Targa | Eclectic Moonsteed
    (8145101, 1072961, 40, 1, 1), # Angry Targa | Eclectic Faraon
    (8145101, 1072962, 40, 1, 1), # Angry Targa | Muspell Warrior Shoes
    (8145101, 1072963, 40, 1, 1), # Angry Targa | Muspell Magician Shoes
    (8145101, 1072964, 40, 1, 1), # Angry Targa | Muspell Bowman Shoes
    (8145101, 1072965, 40, 1, 1), # Angry Targa | Muspell Thief Shoes
    (8145101, 1072966, 40, 1, 1), # Angry Targa | Muspell Pirate Shoes
    (8145101, 1082598, 40, 1, 1), # Angry Targa | Eclectic Bergamot
    (8145101, 1082599, 40, 1, 1), # Angry Targa | Eclectic Hermosa
    (8145101, 1082600, 40, 1, 1), # Angry Targa | Eclectic Presto
    (8145101, 1082601, 40, 1, 1), # Angry Targa | Eclectic Lubav
    (8145101, 1082602, 40, 1, 1), # Angry Targa | Eclectic Charlston
    (8145101, 1082603, 40, 1, 1), # Angry Targa | Muspell Warrior Gloves
    (8145101, 1082604, 40, 1, 1), # Angry Targa | Muspell Magician Gloves
    (8145101, 1082605, 40, 1, 1), # Angry Targa | Muspell Bowman Gloves
    (8145101, 1082606, 40, 1, 1), # Angry Targa | Muspell Thief Gloves
    (8145101, 1082607, 40, 1, 1), # Angry Targa | Muspell Pirate Gloves
    (8145101, 1212099, 40, 1, 1), # Angry Targa | Briser Shining Rod
    (8145101, 1212100, 40, 1, 1), # Angry Targa | Jaihin Shining Rod
    (8145101, 1213010, 40, 1, 1), # Angry Targa | Briser Trust
    (8145101, 1213012, 40, 1, 1), # Angry Targa | Jaihin Nobility
    (8145101, 1214010, 40, 1, 1), # Angry Targa | Briser Kalhygge
    (8145101, 1214012, 40, 1, 1), # Angry Targa | Jaihin Whispershot
    (8145101, 1222093, 40, 1, 1), # Angry Targa | Briser Dragon Soul
    (8145101, 1222094, 40, 1, 1), # Angry Targa | Jaihin Dragon Soul
    (8145101, 1232093, 40, 1, 1), # Angry Targa | Briser Grim Seeker
    (8145101, 1232094, 40, 1, 1), # Angry Targa | Jaihin Desperado
    (8145101, 1242100, 40, 1, 1), # Angry Targa | Briser Hefty Head
    (8145101, 1242101, 40, 1, 1), # Angry Targa | Jaihin Energy Chain
    (8145101, 1242132, 40, 1, 1), # Angry Targa | Jaihin Energy Chain
    (8145101, 1252084, 40, 1, 1), # Angry Targa | Briser Shining Stick
    (8145101, 1252085, 40, 1, 1), # Angry Targa | Jaihin Shining Stick
    (8145101, 1262009, 40, 1, 1), # Angry Targa | Briser Psy-limiter
    (8145101, 1262010, 40, 1, 1), # Angry Targa | Jaihin Psy-limiter
    (8145101, 1272009, 40, 1, 1), # Angry Targa | Briser Chain
    (8145101, 1272011, 40, 1, 1), # Angry Targa | Jaihin Chain
    (8145101, 1282009, 40, 1, 1), # Angry Targa | Briser Lucent Gauntlet
    (8145101, 1282011, 40, 1, 1), # Angry Targa | Jaihin Lucent Gauntlet
    (8145101, 1292010, 40, 1, 1), # Angry Targa | Briser Destroyer Ritual Fan
    (8145101, 1292012, 40, 1, 1), # Angry Targa | Jaihin Great Ritual Fan
    (8145101, 1302313, 40, 1, 1), # Angry Targa | Briser Terror
    (8145101, 1302314, 40, 1, 1), # Angry Targa | Jaihin Saber
    (8145101, 1312183, 40, 1, 1), # Angry Targa | Briser Headsplitter
    (8145101, 1312184, 40, 1, 1), # Angry Targa | Jaihin Axe
    (8145101, 1322234, 40, 1, 1), # Angry Targa | Briser Hammer
    (8145101, 1322235, 40, 1, 1), # Angry Targa | Jaihin Hair
    (8145101, 1332258, 40, 1, 1), # Angry Targa | Briser Slayer
    (8145101, 1332259, 40, 1, 1), # Angry Targa | Jaihin Dagger
    (8145101, 1342098, 40, 1, 1), # Angry Targa | Briser Katara
    (8145101, 1342099, 40, 1, 1), # Angry Targa | Jaihin Katara
    (8145101, 1362119, 40, 1, 1), # Angry Targa | Briser Jester
    (8145101, 1362120, 40, 1, 1), # Angry Targa | Jaihin Cane
    (8145101, 1372205, 40, 1, 1), # Angry Targa | Briser Wand
    (8145101, 1372206, 40, 1, 1), # Angry Targa | Jaihin Wand
    (8145101, 1382243, 40, 1, 1), # Angry Targa | Briser Rod
    (8145101, 1382244, 40, 1, 1), # Angry Targa | Jaihin Staff
    (8145101, 1402234, 40, 1, 1), # Angry Targa | Briser Zweihander
    (8145101, 1402235, 40, 1, 1), # Angry Targa | Jaihin Two-handed Sword
    (8145101, 1412162, 40, 1, 1), # Angry Targa | Briser Giant Axe
    (8145101, 1412163, 40, 1, 1), # Angry Targa | Jaihin Two-handed Axe
    (8145101, 1422169, 40, 1, 1), # Angry Targa | Briser Maul
    (8145101, 1422170, 40, 1, 1), # Angry Targa | Jaihin Two-handed Hammer
    (8145101, 1432198, 40, 1, 1), # Angry Targa | Briser Spear
    (8145101, 1432199, 40, 1, 1), # Angry Targa | Jaihin Spear
    (8145101, 1442252, 40, 1, 1), # Angry Targa | Briser Polearm
    (8145101, 1442253, 40, 1, 1), # Angry Targa | Jaihin Hellslayer
    (8145101, 1452236, 40, 1, 1), # Angry Targa | Briser Longbow
    (8145101, 1452237, 40, 1, 1), # Angry Targa | Jaihin Bow
    (8145101, 1462223, 40, 1, 1), # Angry Targa | Briser Crossbow
    (8145101, 1462224, 40, 1, 1), # Angry Targa | Jaihin Crossbow
    (8145101, 1472245, 40, 1, 1), # Angry Targa | Briser Bloody Mist
    (8145101, 1472246, 40, 1, 1), # Angry Targa | Jaihin Guards
    (8145101, 1482200, 40, 1, 1), # Angry Targa | Briser Knuckle
    (8145101, 1482201, 40, 1, 1), # Angry Targa | Jaihin Claw
    (8145101, 1492210, 40, 1, 1), # Angry Targa | Briser Gun
    (8145101, 1492211, 40, 1, 1), # Angry Targa | Jaihin Pistol
    (8145101, 1522122, 40, 1, 1), # Angry Targa | Briser Dual Bowguns
    (8145101, 1522123, 40, 1, 1), # Angry Targa | Jaihin Dual Bowguns
    (8145101, 1532128, 40, 1, 1), # Angry Targa | Briser Hand Cannon
    (8145101, 1532129, 40, 1, 1), # Angry Targa | Jaihin Siege Gun
    (8145101, 1542099, 40, 1, 1), # Angry Targa | Briser Katana
    (8145101, 1542100, 40, 1, 1), # Angry Targa | Jaihin Katana
    (8145101, 1552100, 40, 1, 1), # Angry Targa | Briser Fan
    (8145101, 1552101, 40, 1, 1), # Angry Targa | Jaihin Fan
    (8145101, 1582009, 40, 1, 1), # Angry Targa | Briser Surtr
    (8145101, 1582010, 40, 1, 1), # Angry Targa | Jaihin Jager
    (8145101, 1592012, 40, 1, 1), # Angry Targa | Briser Ancient Bow
    (8145101, 1592014, 40, 1, 1), # Angry Targa | Jaihin Ancient Bow
    (8145102, 1004219, 40, 1, 1), # Angry Targa | Eclectic Fennel
    (8145102, 1004220, 40, 1, 1), # Angry Targa | Eclectic Coral
    (8145102, 1004221, 40, 1, 1), # Angry Targa | Eclectic Rapid
    (8145102, 1004222, 40, 1, 1), # Angry Targa | Eclectic Chive
    (8145102, 1004223, 40, 1, 1), # Angry Targa | Eclectic Conrad Henkel
    (8145102, 1004224, 40, 1, 1), # Angry Targa | Muspell Warrior Hat
    (8145102, 1004225, 40, 1, 1), # Angry Targa | Muspell Magician Hat
    (8145102, 1004226, 40, 1, 1), # Angry Targa | Muspell Bowman Hat
    (8145102, 1004227, 40, 1, 1), # Angry Targa | Muspell Thief Hat
    (8145102, 1004228, 40, 1, 1), # Angry Targa | Muspell Pirate Hat
    (8145102, 1052789, 40, 1, 1), # Angry Targa | Eclectic Taragon
    (8145102, 1052790, 40, 1, 1), # Angry Targa | Eclectic Myst Blue
    (8145102, 1052791, 40, 1, 1), # Angry Targa | Eclectic Evernew
    (8145102, 1052792, 40, 1, 1), # Angry Targa | Eclectic Prinsid
    (8145102, 1052793, 40, 1, 1), # Angry Targa | Eclectic Burgunt
    (8145102, 1052794, 40, 1, 1), # Angry Targa | Muspell Warrior Suit
    (8145102, 1052795, 40, 1, 1), # Angry Targa | Muspell Magician Suit
    (8145102, 1052796, 40, 1, 1), # Angry Targa | Muspell Bowman Suit
    (8145102, 1052797, 40, 1, 1), # Angry Targa | Muspell Thief Suit
    (8145102, 1052798, 40, 1, 1), # Angry Targa | Muspell Pirate Suit
    (8145102, 1072957, 40, 1, 1), # Angry Targa | Eclectic Grabbe
    (8145102, 1072958, 40, 1, 1), # Angry Targa | Eclectic Cabatina
    (8145102, 1072959, 40, 1, 1), # Angry Targa | Eclectic Rontano
    (8145102, 1072960, 40, 1, 1), # Angry Targa | Eclectic Moonsteed
    (8145102, 1072961, 40, 1, 1), # Angry Targa | Eclectic Faraon
    (8145102, 1072962, 40, 1, 1), # Angry Targa | Muspell Warrior Shoes
    (8145102, 1072963, 40, 1, 1), # Angry Targa | Muspell Magician Shoes
    (8145102, 1072964, 40, 1, 1), # Angry Targa | Muspell Bowman Shoes
    (8145102, 1072965, 40, 1, 1), # Angry Targa | Muspell Thief Shoes
    (8145102, 1072966, 40, 1, 1), # Angry Targa | Muspell Pirate Shoes
    (8145102, 1082598, 40, 1, 1), # Angry Targa | Eclectic Bergamot
    (8145102, 1082599, 40, 1, 1), # Angry Targa | Eclectic Hermosa
    (8145102, 1082600, 40, 1, 1), # Angry Targa | Eclectic Presto
    (8145102, 1082601, 40, 1, 1), # Angry Targa | Eclectic Lubav
    (8145102, 1082602, 40, 1, 1), # Angry Targa | Eclectic Charlston
    (8145102, 1082603, 40, 1, 1), # Angry Targa | Muspell Warrior Gloves
    (8145102, 1082604, 40, 1, 1), # Angry Targa | Muspell Magician Gloves
    (8145102, 1082605, 40, 1, 1), # Angry Targa | Muspell Bowman Gloves
    (8145102, 1082606, 40, 1, 1), # Angry Targa | Muspell Thief Gloves
    (8145102, 1082607, 40, 1, 1), # Angry Targa | Muspell Pirate Gloves
    (8145102, 1212099, 40, 1, 1), # Angry Targa | Briser Shining Rod
    (8145102, 1212100, 40, 1, 1), # Angry Targa | Jaihin Shining Rod
    (8145102, 1213010, 40, 1, 1), # Angry Targa | Briser Trust
    (8145102, 1213012, 40, 1, 1), # Angry Targa | Jaihin Nobility
    (8145102, 1214010, 40, 1, 1), # Angry Targa | Briser Kalhygge
    (8145102, 1214012, 40, 1, 1), # Angry Targa | Jaihin Whispershot
    (8145102, 1222093, 40, 1, 1), # Angry Targa | Briser Dragon Soul
    (8145102, 1222094, 40, 1, 1), # Angry Targa | Jaihin Dragon Soul
    (8145102, 1232093, 40, 1, 1), # Angry Targa | Briser Grim Seeker
    (8145102, 1232094, 40, 1, 1), # Angry Targa | Jaihin Desperado
    (8145102, 1242100, 40, 1, 1), # Angry Targa | Briser Hefty Head
    (8145102, 1242101, 40, 1, 1), # Angry Targa | Jaihin Energy Chain
    (8145102, 1242132, 40, 1, 1), # Angry Targa | Jaihin Energy Chain
    (8145102, 1252084, 40, 1, 1), # Angry Targa | Briser Shining Stick
    (8145102, 1252085, 40, 1, 1), # Angry Targa | Jaihin Shining Stick
    (8145102, 1262009, 40, 1, 1), # Angry Targa | Briser Psy-limiter
    (8145102, 1262010, 40, 1, 1), # Angry Targa | Jaihin Psy-limiter
    (8145102, 1272009, 40, 1, 1), # Angry Targa | Briser Chain
    (8145102, 1272011, 40, 1, 1), # Angry Targa | Jaihin Chain
    (8145102, 1282009, 40, 1, 1), # Angry Targa | Briser Lucent Gauntlet
    (8145102, 1282011, 40, 1, 1), # Angry Targa | Jaihin Lucent Gauntlet
    (8145102, 1292010, 40, 1, 1), # Angry Targa | Briser Destroyer Ritual Fan
    (8145102, 1292012, 40, 1, 1), # Angry Targa | Jaihin Great Ritual Fan
    (8145102, 1302313, 40, 1, 1), # Angry Targa | Briser Terror
    (8145102, 1302314, 40, 1, 1), # Angry Targa | Jaihin Saber
    (8145102, 1312183, 40, 1, 1), # Angry Targa | Briser Headsplitter
    (8145102, 1312184, 40, 1, 1), # Angry Targa | Jaihin Axe
    (8145102, 1322234, 40, 1, 1), # Angry Targa | Briser Hammer
    (8145102, 1322235, 40, 1, 1), # Angry Targa | Jaihin Hair
    (8145102, 1332258, 40, 1, 1), # Angry Targa | Briser Slayer
    (8145102, 1332259, 40, 1, 1), # Angry Targa | Jaihin Dagger
    (8145102, 1342098, 40, 1, 1), # Angry Targa | Briser Katara
    (8145102, 1342099, 40, 1, 1), # Angry Targa | Jaihin Katara
    (8145102, 1362119, 40, 1, 1), # Angry Targa | Briser Jester
    (8145102, 1362120, 40, 1, 1), # Angry Targa | Jaihin Cane
    (8145102, 1372205, 40, 1, 1), # Angry Targa | Briser Wand
    (8145102, 1372206, 40, 1, 1), # Angry Targa | Jaihin Wand
    (8145102, 1382243, 40, 1, 1), # Angry Targa | Briser Rod
    (8145102, 1382244, 40, 1, 1), # Angry Targa | Jaihin Staff
    (8145102, 1402234, 40, 1, 1), # Angry Targa | Briser Zweihander
    (8145102, 1402235, 40, 1, 1), # Angry Targa | Jaihin Two-handed Sword
    (8145102, 1412162, 40, 1, 1), # Angry Targa | Briser Giant Axe
    (8145102, 1412163, 40, 1, 1), # Angry Targa | Jaihin Two-handed Axe
    (8145102, 1422169, 40, 1, 1), # Angry Targa | Briser Maul
    (8145102, 1422170, 40, 1, 1), # Angry Targa | Jaihin Two-handed Hammer
    (8145102, 1432198, 40, 1, 1), # Angry Targa | Briser Spear
    (8145102, 1432199, 40, 1, 1), # Angry Targa | Jaihin Spear
    (8145102, 1442252, 40, 1, 1), # Angry Targa | Briser Polearm
    (8145102, 1442253, 40, 1, 1), # Angry Targa | Jaihin Hellslayer
    (8145102, 1452236, 40, 1, 1), # Angry Targa | Briser Longbow
    (8145102, 1452237, 40, 1, 1), # Angry Targa | Jaihin Bow
    (8145102, 1462223, 40, 1, 1), # Angry Targa | Briser Crossbow
    (8145102, 1462224, 40, 1, 1), # Angry Targa | Jaihin Crossbow
    (8145102, 1472245, 40, 1, 1), # Angry Targa | Briser Bloody Mist
    (8145102, 1472246, 40, 1, 1), # Angry Targa | Jaihin Guards
    (8145102, 1482200, 40, 1, 1), # Angry Targa | Briser Knuckle
    (8145102, 1482201, 40, 1, 1), # Angry Targa | Jaihin Claw
    (8145102, 1492210, 40, 1, 1), # Angry Targa | Briser Gun
    (8145102, 1492211, 40, 1, 1), # Angry Targa | Jaihin Pistol
    (8145102, 1522122, 40, 1, 1), # Angry Targa | Briser Dual Bowguns
    (8145102, 1522123, 40, 1, 1), # Angry Targa | Jaihin Dual Bowguns
    (8145102, 1532128, 40, 1, 1), # Angry Targa | Briser Hand Cannon
    (8145102, 1532129, 40, 1, 1), # Angry Targa | Jaihin Siege Gun
    (8145102, 1542099, 40, 1, 1), # Angry Targa | Briser Katana
    (8145102, 1542100, 40, 1, 1), # Angry Targa | Jaihin Katana
    (8145102, 1552100, 40, 1, 1), # Angry Targa | Briser Fan
    (8145102, 1552101, 40, 1, 1), # Angry Targa | Jaihin Fan
    (8145102, 1582009, 40, 1, 1), # Angry Targa | Briser Surtr
    (8145102, 1582010, 40, 1, 1), # Angry Targa | Jaihin Jager
    (8145102, 1592012, 40, 1, 1), # Angry Targa | Briser Ancient Bow
    (8145102, 1592014, 40, 1, 1), # Angry Targa | Jaihin Ancient Bow
    (8145201, 1004219, 40, 1, 1), # Furious Scarboss | Eclectic Fennel
    (8145201, 1004220, 40, 1, 1), # Furious Scarboss | Eclectic Coral
    (8145201, 1004221, 40, 1, 1), # Furious Scarboss | Eclectic Rapid
    (8145201, 1004222, 40, 1, 1), # Furious Scarboss | Eclectic Chive
    (8145201, 1004223, 40, 1, 1), # Furious Scarboss | Eclectic Conrad Henkel
    (8145201, 1004224, 40, 1, 1), # Furious Scarboss | Muspell Warrior Hat
    (8145201, 1004225, 40, 1, 1), # Furious Scarboss | Muspell Magician Hat
    (8145201, 1004226, 40, 1, 1), # Furious Scarboss | Muspell Bowman Hat
    (8145201, 1004227, 40, 1, 1), # Furious Scarboss | Muspell Thief Hat
    (8145201, 1004228, 40, 1, 1), # Furious Scarboss | Muspell Pirate Hat
    (8145201, 1052789, 40, 1, 1), # Furious Scarboss | Eclectic Taragon
    (8145201, 1052790, 40, 1, 1), # Furious Scarboss | Eclectic Myst Blue
    (8145201, 1052791, 40, 1, 1), # Furious Scarboss | Eclectic Evernew
    (8145201, 1052792, 40, 1, 1), # Furious Scarboss | Eclectic Prinsid
    (8145201, 1052793, 40, 1, 1), # Furious Scarboss | Eclectic Burgunt
    (8145201, 1052794, 40, 1, 1), # Furious Scarboss | Muspell Warrior Suit
    (8145201, 1052795, 40, 1, 1), # Furious Scarboss | Muspell Magician Suit
    (8145201, 1052796, 40, 1, 1), # Furious Scarboss | Muspell Bowman Suit
    (8145201, 1052797, 40, 1, 1), # Furious Scarboss | Muspell Thief Suit
    (8145201, 1052798, 40, 1, 1), # Furious Scarboss | Muspell Pirate Suit
    (8145201, 1072957, 40, 1, 1), # Furious Scarboss | Eclectic Grabbe
    (8145201, 1072958, 40, 1, 1), # Furious Scarboss | Eclectic Cabatina
    (8145201, 1072959, 40, 1, 1), # Furious Scarboss | Eclectic Rontano
    (8145201, 1072960, 40, 1, 1), # Furious Scarboss | Eclectic Moonsteed
    (8145201, 1072961, 40, 1, 1), # Furious Scarboss | Eclectic Faraon
    (8145201, 1072962, 40, 1, 1), # Furious Scarboss | Muspell Warrior Shoes
    (8145201, 1072963, 40, 1, 1), # Furious Scarboss | Muspell Magician Shoes
    (8145201, 1072964, 40, 1, 1), # Furious Scarboss | Muspell Bowman Shoes
    (8145201, 1072965, 40, 1, 1), # Furious Scarboss | Muspell Thief Shoes
    (8145201, 1072966, 40, 1, 1), # Furious Scarboss | Muspell Pirate Shoes
    (8145201, 1082598, 40, 1, 1), # Furious Scarboss | Eclectic Bergamot
    (8145201, 1082599, 40, 1, 1), # Furious Scarboss | Eclectic Hermosa
    (8145201, 1082600, 40, 1, 1), # Furious Scarboss | Eclectic Presto
    (8145201, 1082601, 40, 1, 1), # Furious Scarboss | Eclectic Lubav
    (8145201, 1082602, 40, 1, 1), # Furious Scarboss | Eclectic Charlston
    (8145201, 1082603, 40, 1, 1), # Furious Scarboss | Muspell Warrior Gloves
    (8145201, 1082604, 40, 1, 1), # Furious Scarboss | Muspell Magician Gloves
    (8145201, 1082605, 40, 1, 1), # Furious Scarboss | Muspell Bowman Gloves
    (8145201, 1082606, 40, 1, 1), # Furious Scarboss | Muspell Thief Gloves
    (8145201, 1082607, 40, 1, 1), # Furious Scarboss | Muspell Pirate Gloves
    (8145201, 1212099, 40, 1, 1), # Furious Scarboss | Briser Shining Rod
    (8145201, 1212100, 40, 1, 1), # Furious Scarboss | Jaihin Shining Rod
    (8145201, 1213010, 40, 1, 1), # Furious Scarboss | Briser Trust
    (8145201, 1213012, 40, 1, 1), # Furious Scarboss | Jaihin Nobility
    (8145201, 1214010, 40, 1, 1), # Furious Scarboss | Briser Kalhygge
    (8145201, 1214012, 40, 1, 1), # Furious Scarboss | Jaihin Whispershot
    (8145201, 1222093, 40, 1, 1), # Furious Scarboss | Briser Dragon Soul
    (8145201, 1222094, 40, 1, 1), # Furious Scarboss | Jaihin Dragon Soul
    (8145201, 1232093, 40, 1, 1), # Furious Scarboss | Briser Grim Seeker
    (8145201, 1232094, 40, 1, 1), # Furious Scarboss | Jaihin Desperado
    (8145201, 1242100, 40, 1, 1), # Furious Scarboss | Briser Hefty Head
    (8145201, 1242101, 40, 1, 1), # Furious Scarboss | Jaihin Energy Chain
    (8145201, 1242132, 40, 1, 1), # Furious Scarboss | Jaihin Energy Chain
    (8145201, 1252084, 40, 1, 1), # Furious Scarboss | Briser Shining Stick
    (8145201, 1252085, 40, 1, 1), # Furious Scarboss | Jaihin Shining Stick
    (8145201, 1262009, 40, 1, 1), # Furious Scarboss | Briser Psy-limiter
    (8145201, 1262010, 40, 1, 1), # Furious Scarboss | Jaihin Psy-limiter
    (8145201, 1272009, 40, 1, 1), # Furious Scarboss | Briser Chain
    (8145201, 1272011, 40, 1, 1), # Furious Scarboss | Jaihin Chain
    (8145201, 1282009, 40, 1, 1), # Furious Scarboss | Briser Lucent Gauntlet
    (8145201, 1282011, 40, 1, 1), # Furious Scarboss | Jaihin Lucent Gauntlet
    (8145201, 1292010, 40, 1, 1), # Furious Scarboss | Briser Destroyer Ritual Fan
    (8145201, 1292012, 40, 1, 1), # Furious Scarboss | Jaihin Great Ritual Fan
    (8145201, 1302313, 40, 1, 1), # Furious Scarboss | Briser Terror
    (8145201, 1302314, 40, 1, 1), # Furious Scarboss | Jaihin Saber
    (8145201, 1312183, 40, 1, 1), # Furious Scarboss | Briser Headsplitter
    (8145201, 1312184, 40, 1, 1), # Furious Scarboss | Jaihin Axe
    (8145201, 1322234, 40, 1, 1), # Furious Scarboss | Briser Hammer
    (8145201, 1322235, 40, 1, 1), # Furious Scarboss | Jaihin Hair
    (8145201, 1332258, 40, 1, 1), # Furious Scarboss | Briser Slayer
    (8145201, 1332259, 40, 1, 1), # Furious Scarboss | Jaihin Dagger
    (8145201, 1342098, 40, 1, 1), # Furious Scarboss | Briser Katara
    (8145201, 1342099, 40, 1, 1), # Furious Scarboss | Jaihin Katara
    (8145201, 1362119, 40, 1, 1), # Furious Scarboss | Briser Jester
    (8145201, 1362120, 40, 1, 1), # Furious Scarboss | Jaihin Cane
    (8145201, 1372205, 40, 1, 1), # Furious Scarboss | Briser Wand
    (8145201, 1372206, 40, 1, 1), # Furious Scarboss | Jaihin Wand
    (8145201, 1382243, 40, 1, 1), # Furious Scarboss | Briser Rod
    (8145201, 1382244, 40, 1, 1), # Furious Scarboss | Jaihin Staff
    (8145201, 1402234, 40, 1, 1), # Furious Scarboss | Briser Zweihander
    (8145201, 1402235, 40, 1, 1), # Furious Scarboss | Jaihin Two-handed Sword
    (8145201, 1412162, 40, 1, 1), # Furious Scarboss | Briser Giant Axe
    (8145201, 1412163, 40, 1, 1), # Furious Scarboss | Jaihin Two-handed Axe
    (8145201, 1422169, 40, 1, 1), # Furious Scarboss | Briser Maul
    (8145201, 1422170, 40, 1, 1), # Furious Scarboss | Jaihin Two-handed Hammer
    (8145201, 1432198, 40, 1, 1), # Furious Scarboss | Briser Spear
    (8145201, 1432199, 40, 1, 1), # Furious Scarboss | Jaihin Spear
    (8145201, 1442252, 40, 1, 1), # Furious Scarboss | Briser Polearm
    (8145201, 1442253, 40, 1, 1), # Furious Scarboss | Jaihin Hellslayer
    (8145201, 1452236, 40, 1, 1), # Furious Scarboss | Briser Longbow
    (8145201, 1452237, 40, 1, 1), # Furious Scarboss | Jaihin Bow
    (8145201, 1462223, 40, 1, 1), # Furious Scarboss | Briser Crossbow
    (8145201, 1462224, 40, 1, 1), # Furious Scarboss | Jaihin Crossbow
    (8145201, 1472245, 40, 1, 1), # Furious Scarboss | Briser Bloody Mist
    (8145201, 1472246, 40, 1, 1), # Furious Scarboss | Jaihin Guards
    (8145201, 1482200, 40, 1, 1), # Furious Scarboss | Briser Knuckle
    (8145201, 1482201, 40, 1, 1), # Furious Scarboss | Jaihin Claw
    (8145201, 1492210, 40, 1, 1), # Furious Scarboss | Briser Gun
    (8145201, 1492211, 40, 1, 1), # Furious Scarboss | Jaihin Pistol
    (8145201, 1522122, 40, 1, 1), # Furious Scarboss | Briser Dual Bowguns
    (8145201, 1522123, 40, 1, 1), # Furious Scarboss | Jaihin Dual Bowguns
    (8145201, 1532128, 40, 1, 1), # Furious Scarboss | Briser Hand Cannon
    (8145201, 1532129, 40, 1, 1), # Furious Scarboss | Jaihin Siege Gun
    (8145201, 1542099, 40, 1, 1), # Furious Scarboss | Briser Katana
    (8145201, 1542100, 40, 1, 1), # Furious Scarboss | Jaihin Katana
    (8145201, 1552100, 40, 1, 1), # Furious Scarboss | Briser Fan
    (8145201, 1552101, 40, 1, 1), # Furious Scarboss | Jaihin Fan
    (8145201, 1582009, 40, 1, 1), # Furious Scarboss | Briser Surtr
    (8145201, 1582010, 40, 1, 1), # Furious Scarboss | Jaihin Jager
    (8145201, 1592012, 40, 1, 1), # Furious Scarboss | Briser Ancient Bow
    (8145201, 1592014, 40, 1, 1), # Furious Scarboss | Jaihin Ancient Bow
    (8145202, 1004219, 40, 1, 1), # Furious Scarboss | Eclectic Fennel
    (8145202, 1004220, 40, 1, 1), # Furious Scarboss | Eclectic Coral
    (8145202, 1004221, 40, 1, 1), # Furious Scarboss | Eclectic Rapid
    (8145202, 1004222, 40, 1, 1), # Furious Scarboss | Eclectic Chive
    (8145202, 1004223, 40, 1, 1), # Furious Scarboss | Eclectic Conrad Henkel
    (8145202, 1004224, 40, 1, 1), # Furious Scarboss | Muspell Warrior Hat
    (8145202, 1004225, 40, 1, 1), # Furious Scarboss | Muspell Magician Hat
    (8145202, 1004226, 40, 1, 1), # Furious Scarboss | Muspell Bowman Hat
    (8145202, 1004227, 40, 1, 1), # Furious Scarboss | Muspell Thief Hat
    (8145202, 1004228, 40, 1, 1), # Furious Scarboss | Muspell Pirate Hat
    (8145202, 1052789, 40, 1, 1), # Furious Scarboss | Eclectic Taragon
    (8145202, 1052790, 40, 1, 1), # Furious Scarboss | Eclectic Myst Blue
    (8145202, 1052791, 40, 1, 1), # Furious Scarboss | Eclectic Evernew
    (8145202, 1052792, 40, 1, 1), # Furious Scarboss | Eclectic Prinsid
    (8145202, 1052793, 40, 1, 1), # Furious Scarboss | Eclectic Burgunt
    (8145202, 1052794, 40, 1, 1), # Furious Scarboss | Muspell Warrior Suit
    (8145202, 1052795, 40, 1, 1), # Furious Scarboss | Muspell Magician Suit
    (8145202, 1052796, 40, 1, 1), # Furious Scarboss | Muspell Bowman Suit
    (8145202, 1052797, 40, 1, 1), # Furious Scarboss | Muspell Thief Suit
    (8145202, 1052798, 40, 1, 1), # Furious Scarboss | Muspell Pirate Suit
    (8145202, 1072957, 40, 1, 1), # Furious Scarboss | Eclectic Grabbe
    (8145202, 1072958, 40, 1, 1), # Furious Scarboss | Eclectic Cabatina
    (8145202, 1072959, 40, 1, 1), # Furious Scarboss | Eclectic Rontano
    (8145202, 1072960, 40, 1, 1), # Furious Scarboss | Eclectic Moonsteed
    (8145202, 1072961, 40, 1, 1), # Furious Scarboss | Eclectic Faraon
    (8145202, 1072962, 40, 1, 1), # Furious Scarboss | Muspell Warrior Shoes
    (8145202, 1072963, 40, 1, 1), # Furious Scarboss | Muspell Magician Shoes
    (8145202, 1072964, 40, 1, 1), # Furious Scarboss | Muspell Bowman Shoes
    (8145202, 1072965, 40, 1, 1), # Furious Scarboss | Muspell Thief Shoes
    (8145202, 1072966, 40, 1, 1), # Furious Scarboss | Muspell Pirate Shoes
    (8145202, 1082598, 40, 1, 1), # Furious Scarboss | Eclectic Bergamot
    (8145202, 1082599, 40, 1, 1), # Furious Scarboss | Eclectic Hermosa
    (8145202, 1082600, 40, 1, 1), # Furious Scarboss | Eclectic Presto
    (8145202, 1082601, 40, 1, 1), # Furious Scarboss | Eclectic Lubav
    (8145202, 1082602, 40, 1, 1), # Furious Scarboss | Eclectic Charlston
    (8145202, 1082603, 40, 1, 1), # Furious Scarboss | Muspell Warrior Gloves
    (8145202, 1082604, 40, 1, 1), # Furious Scarboss | Muspell Magician Gloves
    (8145202, 1082605, 40, 1, 1), # Furious Scarboss | Muspell Bowman Gloves
    (8145202, 1082606, 40, 1, 1), # Furious Scarboss | Muspell Thief Gloves
    (8145202, 1082607, 40, 1, 1), # Furious Scarboss | Muspell Pirate Gloves
    (8145202, 1212099, 40, 1, 1), # Furious Scarboss | Briser Shining Rod
    (8145202, 1212100, 40, 1, 1), # Furious Scarboss | Jaihin Shining Rod
    (8145202, 1213010, 40, 1, 1), # Furious Scarboss | Briser Trust
    (8145202, 1213012, 40, 1, 1), # Furious Scarboss | Jaihin Nobility
    (8145202, 1214010, 40, 1, 1), # Furious Scarboss | Briser Kalhygge
    (8145202, 1214012, 40, 1, 1), # Furious Scarboss | Jaihin Whispershot
    (8145202, 1222093, 40, 1, 1), # Furious Scarboss | Briser Dragon Soul
    (8145202, 1222094, 40, 1, 1), # Furious Scarboss | Jaihin Dragon Soul
    (8145202, 1232093, 40, 1, 1), # Furious Scarboss | Briser Grim Seeker
    (8145202, 1232094, 40, 1, 1), # Furious Scarboss | Jaihin Desperado
    (8145202, 1242100, 40, 1, 1), # Furious Scarboss | Briser Hefty Head
    (8145202, 1242101, 40, 1, 1), # Furious Scarboss | Jaihin Energy Chain
    (8145202, 1242132, 40, 1, 1), # Furious Scarboss | Jaihin Energy Chain
    (8145202, 1252084, 40, 1, 1), # Furious Scarboss | Briser Shining Stick
    (8145202, 1252085, 40, 1, 1), # Furious Scarboss | Jaihin Shining Stick
    (8145202, 1262009, 40, 1, 1), # Furious Scarboss | Briser Psy-limiter
    (8145202, 1262010, 40, 1, 1), # Furious Scarboss | Jaihin Psy-limiter
    (8145202, 1272009, 40, 1, 1), # Furious Scarboss | Briser Chain
    (8145202, 1272011, 40, 1, 1), # Furious Scarboss | Jaihin Chain
    (8145202, 1282009, 40, 1, 1), # Furious Scarboss | Briser Lucent Gauntlet
    (8145202, 1282011, 40, 1, 1), # Furious Scarboss | Jaihin Lucent Gauntlet
    (8145202, 1292010, 40, 1, 1), # Furious Scarboss | Briser Destroyer Ritual Fan
    (8145202, 1292012, 40, 1, 1), # Furious Scarboss | Jaihin Great Ritual Fan
    (8145202, 1302313, 40, 1, 1), # Furious Scarboss | Briser Terror
    (8145202, 1302314, 40, 1, 1), # Furious Scarboss | Jaihin Saber
    (8145202, 1312183, 40, 1, 1), # Furious Scarboss | Briser Headsplitter
    (8145202, 1312184, 40, 1, 1), # Furious Scarboss | Jaihin Axe
    (8145202, 1322234, 40, 1, 1), # Furious Scarboss | Briser Hammer
    (8145202, 1322235, 40, 1, 1), # Furious Scarboss | Jaihin Hair
    (8145202, 1332258, 40, 1, 1), # Furious Scarboss | Briser Slayer
    (8145202, 1332259, 40, 1, 1), # Furious Scarboss | Jaihin Dagger
    (8145202, 1342098, 40, 1, 1), # Furious Scarboss | Briser Katara
    (8145202, 1342099, 40, 1, 1), # Furious Scarboss | Jaihin Katara
    (8145202, 1362119, 40, 1, 1), # Furious Scarboss | Briser Jester
    (8145202, 1362120, 40, 1, 1), # Furious Scarboss | Jaihin Cane
    (8145202, 1372205, 40, 1, 1), # Furious Scarboss | Briser Wand
    (8145202, 1372206, 40, 1, 1), # Furious Scarboss | Jaihin Wand
    (8145202, 1382243, 40, 1, 1), # Furious Scarboss | Briser Rod
    (8145202, 1382244, 40, 1, 1), # Furious Scarboss | Jaihin Staff
    (8145202, 1402234, 40, 1, 1), # Furious Scarboss | Briser Zweihander
    (8145202, 1402235, 40, 1, 1), # Furious Scarboss | Jaihin Two-handed Sword
    (8145202, 1412162, 40, 1, 1), # Furious Scarboss | Briser Giant Axe
    (8145202, 1412163, 40, 1, 1), # Furious Scarboss | Jaihin Two-handed Axe
    (8145202, 1422169, 40, 1, 1), # Furious Scarboss | Briser Maul
    (8145202, 1422170, 40, 1, 1), # Furious Scarboss | Jaihin Two-handed Hammer
    (8145202, 1432198, 40, 1, 1), # Furious Scarboss | Briser Spear
    (8145202, 1432199, 40, 1, 1), # Furious Scarboss | Jaihin Spear
    (8145202, 1442252, 40, 1, 1), # Furious Scarboss | Briser Polearm
    (8145202, 1442253, 40, 1, 1), # Furious Scarboss | Jaihin Hellslayer
    (8145202, 1452236, 40, 1, 1), # Furious Scarboss | Briser Longbow
    (8145202, 1452237, 40, 1, 1), # Furious Scarboss | Jaihin Bow
    (8145202, 1462223, 40, 1, 1), # Furious Scarboss | Briser Crossbow
    (8145202, 1462224, 40, 1, 1), # Furious Scarboss | Jaihin Crossbow
    (8145202, 1472245, 40, 1, 1), # Furious Scarboss | Briser Bloody Mist
    (8145202, 1472246, 40, 1, 1), # Furious Scarboss | Jaihin Guards
    (8145202, 1482200, 40, 1, 1), # Furious Scarboss | Briser Knuckle
    (8145202, 1482201, 40, 1, 1), # Furious Scarboss | Jaihin Claw
    (8145202, 1492210, 40, 1, 1), # Furious Scarboss | Briser Gun
    (8145202, 1492211, 40, 1, 1), # Furious Scarboss | Jaihin Pistol
    (8145202, 1522122, 40, 1, 1), # Furious Scarboss | Briser Dual Bowguns
    (8145202, 1522123, 40, 1, 1), # Furious Scarboss | Jaihin Dual Bowguns
    (8145202, 1532128, 40, 1, 1), # Furious Scarboss | Briser Hand Cannon
    (8145202, 1532129, 40, 1, 1), # Furious Scarboss | Jaihin Siege Gun
    (8145202, 1542099, 40, 1, 1), # Furious Scarboss | Briser Katana
    (8145202, 1542100, 40, 1, 1), # Furious Scarboss | Jaihin Katana
    (8145202, 1552100, 40, 1, 1), # Furious Scarboss | Briser Fan
    (8145202, 1552101, 40, 1, 1), # Furious Scarboss | Jaihin Fan
    (8145202, 1582009, 40, 1, 1), # Furious Scarboss | Briser Surtr
    (8145202, 1582010, 40, 1, 1), # Furious Scarboss | Jaihin Jager
    (8145202, 1592012, 40, 1, 1), # Furious Scarboss | Briser Ancient Bow
    (8145202, 1592014, 40, 1, 1), # Furious Scarboss | Jaihin Ancient Bow
    (8643000, 4001878, 200, 1, 1), # Dreamkeeper | Arcane River Droplet Stone
    (8643001, 4001878, 200, 1, 1), # Paper Bag Alley Citizen | Arcane River Droplet Stone
    (8643002, 4001878, 200, 1, 1), # Wood Board Alley Citizen | Arcane River Droplet Stone
    (8643003, 4001878, 200, 1, 1), # Gallina | Arcane River Droplet Stone
    (8643004, 4001878, 200, 1, 1), # Gallus | Arcane River Droplet Stone
    (8643005, 4001878, 200, 1, 1), # Angry Victory Plate | Arcane River Droplet Stone
    (8643006, 4001878, 200, 1, 1), # Crooked Victory Plate | Arcane River Droplet Stone
    (8643007, 4001878, 200, 1, 1), # Red Dancing Shoes | Arcane River Droplet Stone
    (8643008, 4001878, 200, 1, 1), # Angry Masquerade Citizen | Arcane River Droplet Stone
    (8643009, 4001878, 200, 1, 1), # Insane Masquerade Citizen | Arcane River Droplet Stone
    (8643010, 4001878, 200, 1, 1), # Blue-eyed Gargoyle | Arcane River Droplet Stone
    (8643011, 4001878, 200, 1, 1), # Red-eyed Gargoyle | Arcane River Droplet Stone
    (8643012, 4001878, 200, 1, 1), # Weakened Dreamkeeper | Arcane River Droplet Stone
    (8880101, 2003586, 3000, 3, 5), # Damien | Sparkling Blue Star Potion
    (8880101, 2003587, 3000, 3, 5), # Damien | Sparkling Red Star Potion
    (8880101, 2590006, 500, 1, 1), # Damien | Production Soul Enchanter
    (8880101, 2633892, 10000, 1, 1), # Damien | Large Boss Medal of Honor
    (8880101, 2633920, 1000, 1, 1), # Damien | 50% Bonus EXP Coupon Voucher
    (8880101, 4021020, 3000, 1, 3), # Damien | Confusion Fragment
    (8880101, 4021031, 2000, 1, 2), # Damien | Twisted Time
    (8880101, 4001832, 10000, 160, 160), # Damien | Spell Trace
    (9601623, 1212134, 200, 1, 1), # Akechi Mitsuhide | Ame-no-Uzume's Shining Rod of the Wolf
    (9601623, 1213038, 200, 1, 1), # Akechi Mitsuhide | Amaterasu Bladecaster of the Wolf
    (9601623, 1214034, 200, 1, 1), # Akechi Mitsuhide | Oyamatsumi's Whispershot of the Wolf
    (9601623, 1222127, 200, 1, 1), # Akechi Mitsuhide | Susano-o's Rainbow Shooter of the Wolf
    (9601623, 1232127, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Death Bringer of the Wolf
    (9601623, 1242150, 200, 1, 1), # Akechi Mitsuhide | Tsukuyomi's Energy Chain of the Wolf (Thief)
    (9601623, 1242151, 200, 1, 1), # Akechi Mitsuhide | Susano-o's Energy Chain of the Wolf (Pirate)
    (9601623, 1252115, 200, 1, 1), # Akechi Mitsuhide | Ame-no-Uzume's Spirit Pole of the Wolf
    (9601623, 1262057, 200, 1, 1), # Akechi Mitsuhide | Sengoku Era Psy-limiter of the Wolf
    (9601623, 1272047, 200, 1, 1), # Akechi Mitsuhide | Tsukuyomi's Chain of the Wolf
    (9601623, 1282047, 200, 1, 1), # Akechi Mitsuhide | Ame-no-Uzume's Lucent Gauntlet of the Wolf
    (9601623, 1292042, 200, 1, 1), # Akechi Mitsuhide | Tsukuyomi Ritual Fan of the Wolf
    (9601623, 1302364, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Nimbus Sword of the Wolf
    (9601623, 1312219, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Axe of a Thousand Blades of the Wolf
    (9601623, 1322271, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Golden Slice of the Wolf
    (9601623, 1332294, 200, 1, 1), # Akechi Mitsuhide | Tsukuyomi's Execution Sword of the Wolf
    (9601623, 1342112, 200, 1, 1), # Akechi Mitsuhide | Tsukuyomi's Mournful Blade of the Wolf
    (9601623, 1362154, 200, 1, 1), # Akechi Mitsuhide | Tsukuyomi's Wraith Hand of the Wolf
    (9601623, 1372242, 200, 1, 1), # Akechi Mitsuhide | Ame-no-Uzume's Crooked Soul Staff of the Wolf
    (9601623, 1382279, 200, 1, 1), # Akechi Mitsuhide | Ame-no-Uzume's Sinister Spirit Hand of the Wolf
    (9601623, 1402274, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Weeping Spirit Sword of the Wolf
    (9601623, 1412194, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Smoldering Spirit Axe of the Wolf
    (9601623, 1422204, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Mace of Ruin of the Wolf
    (9601623, 1432232, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Final Wind of the Wolf
    (9601623, 1442290, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Pelting Rain of the Wolf
    (9601623, 1452273, 200, 1, 1), # Akechi Mitsuhide | Oyamatsumi's Fire Spirit Bow of the Wolf
    (9601623, 1462257, 200, 1, 1), # Akechi Mitsuhide | Oyamatsumi's Mind Expansion Bow of the Wolf
    (9601623, 1472280, 200, 1, 1), # Akechi Mitsuhide | Tsukuyomi's Fist of Malice of the Wolf
    (9601623, 1482237, 200, 1, 1), # Akechi Mitsuhide | Susano-o's Bloodstained Sword of the Wolf
    (9601623, 1492251, 200, 1, 1), # Akechi Mitsuhide | Susano-o's Baleful Blaster of the Wolf
    (9601623, 1522157, 200, 1, 1), # Akechi Mitsuhide | Oyamatsumi's Dragon Bow of the Wolf
    (9601623, 1532163, 200, 1, 1), # Akechi Mitsuhide | Susano-o's Solar Cannon of the Wolf
    (9601623, 1542136, 200, 1, 1), # Akechi Mitsuhide | Amaterasu's Muramasa of the Wolf
    (9601623, 1552139, 200, 1, 1), # Akechi Mitsuhide | Ame-no-Uzume's Red Flower of the Wolf
    (9601623, 1582050, 200, 1, 1), # Akechi Mitsuhide | Amaterasu Bunker Buster of the Wolf
    (9601623, 1592041, 200, 1, 1), # Akechi Mitsuhide | Oyamatsumi's Ancient Bow of the Wolf
    (9601623, 2003586, 3000, 3, 5), # Akechi Mitsuhide | Sparkling Blue Star Potion
    (9601623, 2003587, 3000, 3, 5), # Akechi Mitsuhide | Sparkling Red Star Potion
    (9601623, 2003607, 2000, 1, 1), # Akechi Mitsuhide | Small EXP Accumulation Potion
    (9601623, 2024084, 2000, 1, 1), # Akechi Mitsuhide | 50% Bonus EXP Coupon
    (9601623, 2633892, 10000, 1, 1), # Akechi Mitsuhide | Large Boss Medal of Honor
    (9601623, 4021020, 3000, 1, 2), # Akechi Mitsuhide | Confusion Fragment
    (9601623, 4021031, 3000, 1, 2), # Akechi Mitsuhide | Twisted Time
    (9601623, 4001832, 10000, 160, 160), # Akechi Mitsuhide | Spell Trace
    (9601623, 4310295, 3000, 1, 2), # Akechi Mitsuhide | Mark of the Silver Wolf
    (9601623, 4001886, 3000, 1, 2), # Akechi Mitsuhide | Intense Power Crystal
    (9601623, 2590006, 500, 1, 1), # Akechi Mitsuhide | Production Soul Enchanter
    (8860000, 1072776, 400, 1, 1), # Arkarium | Proton Boots
    (8860000, 1050253, 400, 1, 1), # Arkarium | Proton Suit
    (8860000, 2590006, 500, 1, 1), # Arkarium | Production Soul Enchanter
    (8860000, 4021022, 3000, 1, 2), # Arkarium | Primal Essence
    (8860005, 2590006, 500, 1, 1), # Arkarium | Production Soul Enchanter
    (8860005, 2433103, 2000, 1, 1), # Arkarium | Boss Medal of Honor
    (8860005, 4021022, 3000, 1, 2), # Arkarium | Primal Essence
    (8860005, 4001832, 10000, 50, 50), # Arkarium | Spell Trace
    (8860005, 4001886, 3000, 1, 2), # Arkarium | Intense Power Crystal
    (8850011, 4001832, 10000, 160, 160), # Cygnus | Spell Trace
    (8850011, 2590006, 500, 1, 1), # Cygnus | Production Soul Enchanter
    (8850011, 1050253, 400, 1, 1), # Cygnus | Proton Suit
    (8850011, 1051309, 400, 1, 1), # Cygnus | Proton Suit
    (8850011, 1072776, 400, 1, 1), # Cygnus | Proton Boots
    (8850011, 1152108, 400, 1, 1), # Cygnus | Lionheart Battle Shoulder
    (8850011, 1152110, 400, 1, 1), # Cygnus | Dragon Tail Mage Shoulder
    (8850011, 1152111, 400, 1, 1), # Cygnus | Falcon Wing Sentinel Shoulder
    (8850011, 1152112, 400, 1, 1), # Cygnus | Raven Horn Chaser Shoulder
    (8850011, 1152113, 400, 1, 1); # Cygnus | Shark Tooth Skipper Shoulder

insert into `mob_drops` (`mobid`, `itemid`, `chance`, `minquant`, `maxquant`)
values
    (8850011, 4001886, 3000, 1, 2), # Cygnus | Intense Power Crystal
    (8850111, 4001832, 10000, 140, 140), # Cygnus | Spell Trace
    (8850111, 2590006, 500, 1, 1), # Cygnus | Production Soul Enchanter
    (8850111, 1050253, 400, 1, 1), # Cygnus | Proton Suit
    (8850111, 1051309, 400, 1, 1), # Cygnus | Proton Suit
    (8850111, 1072776, 400, 1, 1), # Cygnus | Proton Boots
    (8850111, 4001886, 3000, 1, 2), # Cygnus | Intense Power Crystal
    (8850111, 2431711, 3000, 1, 1), # Cygnus | Cygnus's Soul Shard
    (8880111, 2003586, 3000, 3, 5), # Damien | Sparkling Blue Star Potion
    (8880111, 2003587, 3000, 3, 5), # Damien | Sparkling Red Star Potion
    (8880111, 2003607, 2000, 1, 1), # Damien | Small EXP Accumulation Potion
    (8880111, 2633920, 1000, 1, 1), # Damien | 50% Bonus EXP Coupon Voucher
    (8880111, 2590006, 500, 1, 1), # Damien | Production Soul Enchanter
    (8880111, 2633919, 1000, 1, 1), # Damien | Scroll - Accessory Coupon
    (8880111, 2633912, 300, 1, 1), # Damien | AbsoLab Weapon Box
    (8880111, 2633913, 300, 1, 1), # Damien | AbsoLab Armor Box
    (8880111, 4021020, 3000, 1, 2), # Damien | Confusion Fragment
    (8880111, 4021031, 3000, 1, 2), # Damien | Twisted Time
    (8880111, 4001832, 10000, 240, 240), # Damien | Spell Trace
    (8645009, 2003586, 3000, 3, 5), # Guard Captain Darknell | Sparkling Blue Star Potion
    (8645009, 2003587, 3000, 3, 5), # Guard Captain Darknell | Sparkling Red Star Potion
    (8645009, 2003607, 2000, 1, 1), # Guard Captain Darknell | Small EXP Accumulation Potion
    (8645009, 2633920, 1000, 1, 1), # Guard Captain Darknell | 50% Bonus EXP Coupon Voucher
    (8645009, 2590006, 500, 1, 1), # Guard Captain Darknell | Production Soul Enchanter
    (8645009, 4001832, 10000, 160, 160), # Guard Captain Darknell | Spell Trace
    (8645009, 4001886, 3000, 1, 2), # Guard Captain Darknell | Intense Power Crystal
    (8645066, 1032316, 50, 1, 1), # Guard Captain Darknell | Commanding Force Earring
    (8645066, 2003586, 3000, 3, 5), # Guard Captain Darknell | Sparkling Blue Star Potion
    (8645066, 2003587, 3000, 3, 5), # Guard Captain Darknell | Sparkling Red Star Potion
    (8645066, 2003607, 2000, 1, 1), # Guard Captain Darknell | Small EXP Accumulation Potion
    (8645066, 2633920, 1000, 1, 1), # Guard Captain Darknell | 50% Bonus EXP Coupon Voucher
    (8645066, 2590006, 500, 1, 1), # Guard Captain Darknell | Production Soul Enchanter
    (8645066, 2633892, 10000, 1, 1), # Guard Captain Darknell | Large Boss Medal of Honor
    (8645066, 2439568, 3000, 1, 1), # Guard Captain Darknell | Darknell's Soul Shard
    (8645066, 2633916, 1000, 1, 1), # Guard Captain Darknell | Premium Scroll - Accessory Coupon
    (8645066, 2633917, 1000, 1, 1), # Guard Captain Darknell | Premium Scroll - Pet Equipment Coupon
    (8645066, 2633918, 1000, 1, 1), # Guard Captain Darknell | Magical Scroll - Weapon Coupon
    (8645066, 2633914, 300, 1, 1), # Guard Captain Darknell | Arcane Umbra Weapon Box
    (8645066, 2633915, 300, 1, 1), # Guard Captain Darknell | Arcane Umbra Armor Box
    (8645066, 4021022, 3000, 1, 2), # Guard Captain Darknell | Primal Essence
    (8645066, 4021031, 3000, 1, 2), # Guard Captain Darknell | Twisted Time
    (8645066, 4001832, 10000, 240, 240), # Guard Captain Darknell | Spell Trace
    (8645066, 4001886, 3000, 1, 2), # Guard Captain Darknell | Intense Power Crystal
    (8644650, 2003586, 3000, 3, 5), # Giant Monster Gloom | Sparkling Blue Star Potion
    (8644650, 2003587, 3000, 3, 5), # Giant Monster Gloom | Sparkling Red Star Potion
    (8644650, 2003607, 2000, 1, 1), # Giant Monster Gloom | Small EXP Accumulation Potion
    (8644650, 2633920, 1000, 1, 1), # Giant Monster Gloom | 50% Bonus EXP Coupon Voucher
    (8644650, 2590006, 500, 1, 1), # Giant Monster Gloom | Production Soul Enchanter
    (8644650, 4001832, 10000, 160, 160), # Giant Monster Gloom | Spell Trace
    (8644650, 4001886, 3000, 1, 2), # Giant Monster Gloom | Intense Power Crystal
    (9601459, 1032220, 400, 1, 1), # Gollux's Corrupted Heart | Cracked Gollux Earrings
    (9601460, 1032220, 400, 1, 1), # Gollux's Corrupted Heart | Cracked Gollux Earrings
    (9601461, 1032220, 400, 1, 1), # Gollux's Corrupted Heart | Cracked Gollux Earrings
    (9601462, 1032220, 400, 1, 1), # Gollux's Corrupted Heart | Cracked Gollux Earrings
    (9601459, 1113072, 400, 1, 1), # Gollux's Corrupted Heart | Cracked Gollux Ring
    (9601460, 1113072, 400, 1, 1), # Gollux's Corrupted Heart | Cracked Gollux Ring
    (9601459, 2590006, 500, 1, 1), # Gollux's Corrupted Heart | Production Soul Enchanter
    (9601460, 2590006, 500, 1, 1), # Gollux's Corrupted Heart | Production Soul Enchanter
    (9601461, 2590006, 500, 1, 1), # Gollux's Corrupted Heart | Production Soul Enchanter
    (9601462, 2590006, 500, 1, 1), # Gollux's Corrupted Heart | Production Soul Enchanter
    (9601459, 2433103, 2000, 1, 1), # Gollux's Corrupted Heart | Boss Medal of Honor
    (9601460, 2433103, 2000, 1, 1), # Gollux's Corrupted Heart | Boss Medal of Honor
    (9601461, 2433103, 2000, 1, 1), # Gollux's Corrupted Heart | Boss Medal of Honor
    (9601462, 2433103, 2000, 1, 1), # Gollux's Corrupted Heart | Boss Medal of Honor
    (9601459, 4021031, 3000, 1, 2), # Gollux's Corrupted Heart | Twisted Time
    (9601460, 4021031, 3000, 1, 2), # Gollux's Corrupted Heart | Twisted Time
    (9601461, 4021031, 3000, 1, 2), # Gollux's Corrupted Heart | Twisted Time
    (9601462, 4021031, 3000, 1, 2), # Gollux's Corrupted Heart | Twisted Time
    (9601459, 4001832, 10000, 50, 50), # Gollux's Corrupted Heart | Spell Trace
    (9601460, 4001832, 10000, 50, 50), # Gollux's Corrupted Heart | Spell Trace
    (9601461, 4001832, 10000, 50, 50), # Gollux's Corrupted Heart | Spell Trace
    (9601462, 4001832, 10000, 50, 50), # Gollux's Corrupted Heart | Spell Trace
    (9601460, 1032221, 400, 1, 1), # Gollux's Corrupted Heart | Solid Gollux Earrings
    (9601461, 1032221, 400, 1, 1), # Gollux's Corrupted Heart | Solid Gollux Earrings
    (9601462, 1032221, 400, 1, 1), # Gollux's Corrupted Heart | Solid Gollux Earrings
    (9601460, 1113073, 400, 1, 1), # Gollux's Corrupted Heart | Solid Gollux Ring
    (9601461, 1032222, 400, 1, 1), # Gollux's Corrupted Heart | Reinforced Gollux Earrings
    (9601462, 1032222, 400, 1, 1), # Gollux's Corrupted Heart | Reinforced Gollux Earrings
    (9601462, 1032223, 400, 1, 1), # Gollux's Corrupted Heart | Superior Gollux Earrings
    (9601462, 4033982, 3000, 1, 2), # Gollux's Corrupted Heart | Fallen Heart Tree Key
    (8880726, 1113313, 50, 1, 1), # Slime Treasure Chest | Guardian Angel Ring
    (8880726, 2003586, 3000, 3, 5), # Slime Treasure Chest | Sparkling Blue Star Potion
    (8880726, 2003587, 3000, 3, 5), # Slime Treasure Chest | Sparkling Red Star Potion
    (8880726, 2003607, 2000, 1, 1), # Slime Treasure Chest | Small EXP Accumulation Potion
    (8880726, 2633920, 1000, 1, 1), # Slime Treasure Chest | 50% Bonus EXP Coupon Voucher
    (8880726, 2633892, 10000, 1, 1), # Slime Treasure Chest | Large Boss Medal of Honor
    (8880726, 4021020, 3000, 1, 2), # Slime Treasure Chest | Confusion Fragment
    (8880726, 4021031, 3000, 1, 2), # Slime Treasure Chest | Twisted Time
    (8880726, 4001832, 10000, 240, 240), # Slime Treasure Chest | Spell Trace
    (8880726, 4001886, 3000, 1, 2), # Slime Treasure Chest | Intense Power Crystal
    (8880726, 2590006, 500, 1, 1), # Slime Treasure Chest | Production Soul Enchanter
    (8880725, 1113313, 50, 1, 1), # Slime Treasure Chest | Guardian Angel Ring
    (8880725, 2003586, 3000, 3, 5), # Slime Treasure Chest | Sparkling Blue Star Potion
    (8880725, 2003587, 3000, 3, 5), # Slime Treasure Chest | Sparkling Red Star Potion
    (8880725, 2003607, 2000, 1, 1), # Slime Treasure Chest | Small EXP Accumulation Potion
    (8880725, 2633920, 1000, 1, 1), # Slime Treasure Chest | 50% Bonus EXP Coupon Voucher
    (8880725, 2590006, 500, 1, 1), # Slime Treasure Chest | Production Soul Enchanter
    (8880725, 2633892, 10000, 1, 1), # Slime Treasure Chest | Large Boss Medal of Honor
    (8880725, 2633916, 1000, 1, 1), # Slime Treasure Chest | Premium Scroll - Accessory Coupon
    (8880725, 2633917, 1000, 1, 1), # Slime Treasure Chest | Premium Scroll - Pet Equipment Coupon
    (8880725, 2633918, 1000, 1, 1), # Slime Treasure Chest | Magical Scroll - Weapon Coupon
    (8880725, 4021022, 3000, 1, 2), # Slime Treasure Chest | Primal Essence
    (8880725, 4021020, 3000, 1, 2), # Slime Treasure Chest | Confusion Fragment
    (8880725, 4021031, 3000, 1, 2), # Slime Treasure Chest | Twisted Time
    (8880725, 4001832, 10000, 240, 240), # Slime Treasure Chest | Spell Trace
    (8880725, 4001886, 3000, 1, 2), # Slime Treasure Chest | Intense Power Crystal
    (8870000, 1214011, 200, 1, 1), # Hilla | Necromancer Whispershot
    (8870000, 1292011, 200, 1, 1), # Hilla | Necro Apparition Ritual Fan
    (8870000, 1552040, 200, 1, 1), # Hilla | Necromancer's Fan
    (8870000, 2590006, 500, 1, 1), # Hilla | Production Soul Enchanter
    (8870000, 2433103, 2000, 1, 1), # Hilla | Boss Medal of Honor
    (8870000, 4021031, 3000, 1, 2), # Hilla | Twisted Time
    (8870000, 4001832, 10000, 20, 20), # Hilla | Spell Trace
    (8870000, 4001886, 3000, 1, 2), # Hilla | Intense Power Crystal
    (8870100, 1214011, 200, 1, 1), # Silver-Haired Hilla | Necromancer Whispershot
    (8870100, 1292011, 200, 1, 1), # Silver-Haired Hilla | Necro Apparition Ritual Fan
    (8870100, 1552040, 200, 1, 1), # Silver-Haired Hilla | Necromancer's Fan
    (8870100, 1072776, 400, 1, 1), # Silver-Haired Hilla | Proton Boots
    (8870100, 1050253, 400, 1, 1), # Silver-Haired Hilla | Proton Suit
    (8870100, 2590006, 500, 1, 1), # Silver-Haired Hilla | Production Soul Enchanter
    (8870100, 4021031, 3000, 1, 2), # Silver-Haired Hilla | Twisted Time
    (8870100, 4001832, 10000, 140, 140), # Silver-Haired Hilla | Spell Trace
    (8810018, 1113149, 400, 1, 1), # Horntail | Silver Blossom Ring
    (8810018, 1032241, 400, 1, 1), # Horntail | Dea Sidus Earring
    (8810018, 2590006, 500, 1, 1), # Horntail | Production Soul Enchanter
    (8810018, 2435366, 3000, 1, 1), # Horntail | Horntail Monsterbloom
    (8810018, 4021031, 3000, 1, 2), # Horntail | Twisted Time
    (8810018, 4001832, 10000, 40, 40), # Horntail | Spell Trace
    (8810018, 4001886, 3000, 1, 2), # Horntail | Intense Power Crystal
    (8810214, 1122000, 400, 1, 1), # Easy Horntail | Horntail Necklace
    (8810214, 1113149, 400, 1, 1), # Easy Horntail | Silver Blossom Ring
    (8810214, 1032241, 400, 1, 1), # Easy Horntail | Dea Sidus Earring
    (8810214, 2590006, 500, 1, 1), # Easy Horntail | Production Soul Enchanter
    (8810214, 4021031, 3000, 1, 2), # Easy Horntail | Twisted Time
    (8810214, 4001832, 10000, 20, 20), # Easy Horntail | Spell Trace
    (8810214, 4001886, 3000, 1, 2), # Easy Horntail | Intense Power Crystal
    (8810118, 2590006, 500, 1, 1), # Chaos Horntail | Production Soul Enchanter
    (8810118, 4021031, 3000, 1, 2), # Chaos Horntail | Twisted Time
    (8810118, 4001832, 10000, 60, 60), # Chaos Horntail | Spell Trace
    (8810118, 4001886, 3000, 1, 2), # Chaos Horntail | Intense Power Crystal
    (8950002, 2003586, 3000, 3, 5), # Lotus | Sparkling Blue Star Potion
    (8950002, 2003587, 3000, 3, 5), # Lotus | Sparkling Red Star Potion
    (8950002, 2003607, 2000, 1, 1), # Lotus | Small EXP Accumulation Potion
    (8950002, 2633920, 1000, 1, 1), # Lotus | 50% Bonus EXP Coupon Voucher
    (8950002, 2590006, 500, 1, 1), # Lotus | Production Soul Enchanter
    (8950002, 4021020, 3000, 1, 2), # Lotus | Confusion Fragment
    (8950002, 4021031, 3000, 1, 2), # Lotus | Twisted Time
    (8950002, 4001832, 10000, 160, 160), # Lotus | Spell Trace
    (8950002, 4001886, 3000, 1, 2), # Lotus | Intense Power Crystal
    (8950102, 1662053, 50, 1, 1), # Lotus | Lotusroid
    (8950102, 2003586, 3000, 3, 5), # Lotus | Sparkling Blue Star Potion
    (8950102, 2003587, 3000, 3, 5), # Lotus | Sparkling Red Star Potion
    (8950102, 2003607, 2000, 1, 1), # Lotus | Small EXP Accumulation Potion
    (8950102, 2633920, 1000, 1, 1), # Lotus | 50% Bonus EXP Coupon Voucher
    (8950102, 2590006, 500, 1, 1), # Lotus | Production Soul Enchanter
    (8950102, 2633919, 1000, 1, 1), # Lotus | Scroll - Accessory Coupon
    (8950102, 2633912, 300, 1, 1), # Lotus | AbsoLab Weapon Box
    (8950102, 2633913, 300, 1, 1), # Lotus | AbsoLab Armor Box
    (8950102, 4021020, 3000, 1, 2), # Lotus | Confusion Fragment
    (8950102, 4021031, 3000, 1, 2), # Lotus | Twisted Time
    (8950102, 4001832, 10000, 240, 240), # Lotus | Spell Trace
    (8950102, 4001886, 3000, 1, 2), # Lotus | Intense Power Crystal
    (8880167, 2003586, 3000, 3, 5), # Final Music Box | Sparkling Blue Star Potion
    (8880167, 2003587, 3000, 3, 5), # Final Music Box | Sparkling Red Star Potion
    (8880167, 2003607, 2000, 1, 1), # Final Music Box | Small EXP Accumulation Potion
    (8880167, 2633920, 1000, 1, 1), # Final Music Box | 50% Bonus EXP Coupon Voucher
    (8880167, 2590006, 500, 1, 1), # Final Music Box | Production Soul Enchanter
    (8880167, 4021020, 3000, 1, 2), # Final Music Box | Confusion Fragment
    (8880167, 4021031, 3000, 1, 2), # Final Music Box | Twisted Time
    (8880167, 4001832, 10000, 160, 160), # Final Music Box | Spell Trace
    (8880167, 4001886, 3000, 1, 2), # Final Music Box | Intense Power Crystal
    (8880156, 2003586, 3000, 3, 5), # Final Music Box | Sparkling Blue Star Potion
    (8880156, 2003587, 3000, 3, 5), # Final Music Box | Sparkling Red Star Potion
    (8880156, 2003607, 2000, 1, 1), # Final Music Box | Small EXP Accumulation Potion
    (8880156, 2633920, 1000, 1, 1), # Final Music Box | 50% Bonus EXP Coupon Voucher
    (8880156, 2590006, 500, 1, 1), # Final Music Box | Production Soul Enchanter
    (8880156, 2633892, 10000, 1, 1), # Final Music Box | Large Boss Medal of Honor
    (8880156, 2436039, 3000, 1, 1), # Final Music Box | Lucid's Soul Shard
    (8880156, 4021020, 3000, 1, 2), # Final Music Box | Confusion Fragment
    (8880156, 4021031, 3000, 1, 2), # Final Music Box | Twisted Time
    (8880156, 4001832, 10000, 160, 160), # Final Music Box | Spell Trace
    (8880156, 4001886, 3000, 1, 2), # Final Music Box | Intense Power Crystal
    (8880177, 2003586, 3000, 3, 5), # Final Music Box | Sparkling Blue Star Potion
    (8880177, 2003587, 3000, 3, 5), # Final Music Box | Sparkling Red Star Potion
    (8880177, 2003607, 2000, 1, 1), # Final Music Box | Small EXP Accumulation Potion
    (8880177, 2633920, 1000, 1, 1), # Final Music Box | 50% Bonus EXP Coupon Voucher
    (8880177, 2590006, 500, 1, 1), # Final Music Box | Production Soul Enchanter
    (8880177, 2633919, 1000, 1, 1), # Final Music Box | Scroll - Accessory Coupon
    (8880177, 2633914, 300, 1, 1), # Final Music Box | Arcane Umbra Weapon Box
    (8880177, 2633915, 300, 1, 1), # Final Music Box | Arcane Umbra Armor Box
    (8880177, 4021020, 3000, 1, 2), # Final Music Box | Confusion Fragment
    (8880177, 4021031, 3000, 1, 2), # Final Music Box | Twisted Time
    (8880177, 4001832, 10000, 240, 240), # Final Music Box | Spell Trace
    (8880177, 4001886, 3000, 1, 2), # Final Music Box | Intense Power Crystal
    (8880002, 1102476, 400, 1, 1), # Magnus | Nova Hyades Cloak
    (8880002, 1102477, 400, 1, 1), # Magnus | Nova Hermes Cloak
    (8880002, 1102478, 400, 1, 1), # Magnus | Nova Charon Cloak
    (8880002, 1102479, 400, 1, 1), # Magnus | Nova Lycaon Cloak
    (8880002, 1102480, 400, 1, 1), # Magnus | Nova Altair Cloak
    (8880002, 1402205, 200, 1, 1), # Magnus | Kaiserion
    (8880002, 1152170, 400, 1, 1), # Magnus | Royal Black Metal Shoulder
    (8880002, 1182087, 400, 1, 1), # Magnus | Crystal Ventus Badge
    (8880002, 2590006, 500, 1, 1), # Magnus | Production Soul Enchanter
    (8880002, 2433103, 2000, 1, 1), # Magnus | Boss Medal of Honor
    (8880002, 2431964, 3000, 1, 1), # Magnus | Magnus's Soul Shard
    (8880002, 4021031, 3000, 1, 2), # Magnus | Twisted Time
    (8880002, 4001832, 10000, 72, 72), # Magnus | Spell Trace
    (8880002, 4310059, 3000, 1, 2), # Magnus | Shadowdealer Coin
    (8880002, 4001886, 3000, 1, 2), # Magnus | Intense Power Crystal
    (8880010, 1132164, 400, 1, 1), # Magnus | Elite Heliseum Warrior Belt
    (8880010, 1132165, 400, 1, 1), # Magnus | Elite Heliseum Magician Belt
    (8880010, 1132166, 400, 1, 1), # Magnus | Elite Heliseum Bowman Belt
    (8880010, 1132167, 400, 1, 1), # Magnus | Elite Heliseum Thief Belt
    (8880010, 1132168, 400, 1, 1), # Magnus | Elite Heliseum Pirate Belt
    (8880010, 1102476, 400, 1, 1), # Magnus | Nova Hyades Cloak
    (8880010, 1102477, 400, 1, 1), # Magnus | Nova Hermes Cloak
    (8880010, 1102478, 400, 1, 1), # Magnus | Nova Charon Cloak
    (8880010, 1102479, 400, 1, 1), # Magnus | Nova Lycaon Cloak
    (8880010, 1102480, 400, 1, 1), # Magnus | Nova Altair Cloak
    (8880010, 1402205, 200, 1, 1), # Magnus | Kaiserion
    (8880010, 2590006, 500, 1, 1), # Magnus | Production Soul Enchanter
    (8880010, 2433103, 2000, 1, 1), # Magnus | Boss Medal of Honor
    (8880010, 4021031, 3000, 1, 2), # Magnus | Twisted Time
    (8880010, 4001832, 10000, 20, 20), # Magnus | Spell Trace
    (8880010, 4310059, 3000, 1, 2), # Magnus | Shadowdealer Coin
    (8880010, 4001886, 3000, 1, 2), # Magnus | Intense Power Crystal
    (8880000, 1132164, 400, 1, 1), # Magnus | Elite Heliseum Warrior Belt
    (8880000, 1132165, 400, 1, 1), # Magnus | Elite Heliseum Magician Belt
    (8880000, 1132166, 400, 1, 1), # Magnus | Elite Heliseum Bowman Belt
    (8880000, 1132167, 400, 1, 1), # Magnus | Elite Heliseum Thief Belt
    (8880000, 1132168, 400, 1, 1), # Magnus | Elite Heliseum Pirate Belt
    (8880000, 1102476, 400, 1, 1), # Magnus | Nova Hyades Cloak
    (8880000, 1102477, 400, 1, 1), # Magnus | Nova Hermes Cloak
    (8880000, 1102478, 400, 1, 1), # Magnus | Nova Charon Cloak
    (8880000, 1102479, 400, 1, 1), # Magnus | Nova Lycaon Cloak
    (8880000, 1102480, 400, 1, 1), # Magnus | Nova Altair Cloak
    (8880000, 1072737, 400, 1, 1), # Magnus | Nova Hyades Boots
    (8880000, 1072738, 400, 1, 1), # Magnus | Nova Hermes Boots
    (8880000, 1072739, 400, 1, 1), # Magnus | Nova Charon Boots
    (8880000, 1072740, 400, 1, 1), # Magnus | Nova Lycaon Boots
    (8880000, 1072741, 400, 1, 1), # Magnus | Nova Altair Boots
    (8880000, 1132169, 400, 1, 1), # Magnus | Nova Hyades Belt
    (8880000, 1132170, 400, 1, 1), # Magnus | Nova Hermes Belt
    (8880000, 1132171, 400, 1, 1), # Magnus | Nova Charon Belt
    (8880000, 1132172, 400, 1, 1), # Magnus | Nova Lycaon Belt
    (8880000, 1132173, 400, 1, 1), # Magnus | Nova Altair Belt
    (8880000, 1072776, 400, 1, 1), # Magnus | Proton Boots
    (8880000, 1050253, 400, 1, 1), # Magnus | Proton Suit
    (8880000, 1152170, 400, 1, 1), # Magnus | Royal Black Metal Shoulder
    (8880000, 1182087, 400, 1, 1), # Magnus | Crystal Ventus Badge
    (8880000, 2590006, 500, 1, 1), # Magnus | Production Soul Enchanter
    (8880000, 2433103, 2000, 1, 1), # Magnus | Boss Medal of Honor
    (8880000, 2431964, 3000, 1, 1), # Magnus | Magnus's Soul Shard
    (8880000, 4021031, 3000, 1, 2), # Magnus | Twisted Time
    (8880000, 4001832, 10000, 160, 160), # Magnus | Spell Trace
    (8880000, 4310059, 3000, 1, 2), # Magnus | Shadowdealer Coin
    (8880000, 4310058, 3000, 1, 2), # Magnus | Magnus Coin
    (8880000, 4001886, 3000, 1, 2), # Magnus | Intense Power Crystal
    (8500012, 2870377, 2000, 1, 1), # Papulatus | Papulatus Familiar
    (8500012, 4021031, 3000, 1, 2), # Papulatus | Twisted Time
    (8500012, 4001832, 10000, 72, 72), # Papulatus | Spell Trace
    (8500012, 4001886, 3000, 1, 2), # Papulatus | Intense Power Crystal
    (8500002, 2433103, 2000, 1, 1), # Papulatus | Boss Medal of Honor
    (8500002, 4021031, 3000, 1, 2), # Papulatus | Twisted Time
    (8500002, 4001832, 10000, 20, 20), # Papulatus | Spell Trace
    (8500002, 4001886, 3000, 1, 2), # Papulatus | Intense Power Crystal
    (8500022, 2870377, 2000, 1, 1), # Papulatus | Papulatus Familiar
    (8500022, 4021031, 3000, 1, 2), # Papulatus | Twisted Time
    (8500022, 4001832, 10000, 160, 160), # Papulatus | Spell Trace
    (8500022, 4001886, 3000, 1, 2), # Papulatus | Intense Power Crystal
    (8900103, 2590006, 500, 1, 1), # Pierre's Treasure Chest | Production Soul Enchanter
    (8900103, 4021031, 3000, 1, 2), # Pierre's Treasure Chest | Twisted Time
    (8900103, 4001832, 10000, 20, 20), # Pierre's Treasure Chest | Spell Trace
    (8900103, 4001886, 3000, 1, 2), # Pierre's Treasure Chest | Intense Power Crystal
    (8900003, 4021031, 3000, 1, 2), # Chaos Pierre | Twisted Time
    (8900003, 4001832, 10000, 160, 160), # Chaos Pierre | Spell Trace
    (8900003, 4001886, 3000, 1, 2), # Chaos Pierre | Intense Power Crystal
    (8900003, 2590006, 500, 1, 1), # Chaos Pierre | Production Soul Enchanter
    (8820001, 1022232, 400, 1, 1), # Pink Bean | Black Bean Mark
    (8820001, 2590006, 500, 1, 1), # Pink Bean | Production Soul Enchanter
    (8820001, 4001832, 10000, 60, 60), # Pink Bean | Spell Trace
    (8820001, 4001115, 3000, 1, 2), # Pink Bean | Undine's Cloth
    (8820001, 4001886, 3000, 1, 2), # Pink Bean | Intense Power Crystal
    (8820212, 1050253, 400, 1, 1), # Chaos Pink Bean | Proton Suit
    (8820212, 1072776, 400, 1, 1), # Chaos Pink Bean | Proton Boots
    (8820212, 2590006, 500, 1, 1), # Chaos Pink Bean | Production Soul Enchanter
    (8820212, 4001832, 10000, 140, 140), # Chaos Pink Bean | Spell Trace
    (8820212, 4001886, 3000, 1, 2), # Chaos Pink Bean | Intense Power Crystal
    (8920106, 4021031, 3000, 1, 2), # Crimson Queen's Treasure Chest | Twisted Time
    (8920106, 4001832, 10000, 20, 20), # Crimson Queen's Treasure Chest | Spell Trace
    (8920106, 2590006, 500, 1, 1), # Crimson Queen's Treasure Chest | Production Soul Enchanter
    (8920006, 2590006, 500, 1, 1), # Chaos Crimson Queen | Production Soul Enchanter
    (8920006, 4021031, 3000, 1, 2), # Chaos Crimson Queen | Twisted Time
    (8920006, 4001832, 10000, 160, 160), # Chaos Crimson Queen | Spell Trace
    (8920006, 4001886, 3000, 1, 2), # Chaos Crimson Queen | Intense Power Crystal
    (9421581, 2433103, 2000, 1, 1), # Mori Ranmaru | Boss Medal of Honor
    (9421581, 4021030, 3000, 1, 2), # Mori Ranmaru | Spirit Stone of the Sixth Heaven
    (9421581, 4001832, 10000, 20, 20), # Mori Ranmaru | Spell Trace
    (9421581, 2590006, 500, 1, 1), # Mori Ranmaru | Production Soul Enchanter
    (9421581, 4001886, 3000, 1, 2), # Mori Ranmaru | Intense Power Crystal
    (9421589, 2003550, 2000, 1, 1), # Mori Ranmaru | EXP Accumulation Potion
    (9421589, 2590006, 500, 1, 1), # Mori Ranmaru | Production Soul Enchanter
    (9421589, 4021020, 3000, 1, 2), # Mori Ranmaru | Confusion Fragment
    (9421589, 4021023, 3000, 1, 2), # Mori Ranmaru | Legendary Weapon Shard (Mouri Clan)
    (9421589, 4021024, 3000, 1, 2), # Mori Ranmaru | Legendary Armor Shard (Mouri Clan)
    (9421589, 4021025, 3000, 1, 2), # Mori Ranmaru | Legendary Weapon Shard (Takeda Clan)
    (9421589, 4021026, 3000, 1, 2), # Mori Ranmaru | Legendary Armor Shard (Takeda Clan)
    (9421589, 4021027, 3000, 1, 2), # Mori Ranmaru | Legendary Weapon Shard (Uesugi Clan)
    (9421589, 4021028, 3000, 1, 2), # Mori Ranmaru | Legendary Armor Shard (Uesugi Clan)
    (9421589, 1214033, 200, 1, 1), # Mori Ranmaru | Oyamatsumi's Whispershot
    (9421589, 1292033, 200, 1, 1), # Mori Ranmaru | Tsukuyomi Ritual Fan
    (9421589, 1582030, 200, 1, 1), # Mori Ranmaru | Amaterasu Bunker Buster
    (9421589, 1592034, 200, 1, 1), # Mori Ranmaru | Oyamatsumi's Ancient Bow
    (9421589, 4001886, 3000, 1, 2), # Mori Ranmaru | Intense Power Crystal
    (8880614, 2003586, 3000, 3, 5), # Chosen Seren's Treasure Chest | Sparkling Blue Star Potion
    (8880615, 2003586, 3000, 3, 5), # Chosen Seren | Sparkling Blue Star Potion
    (8880614, 2003587, 3000, 3, 5), # Chosen Seren's Treasure Chest | Sparkling Red Star Potion
    (8880615, 2003587, 3000, 3, 5), # Chosen Seren | Sparkling Red Star Potion
    (8880614, 2003607, 2000, 1, 1), # Chosen Seren's Treasure Chest | Small EXP Accumulation Potion
    (8880615, 2003607, 2000, 1, 1), # Chosen Seren | Small EXP Accumulation Potion
    (8880614, 2633920, 1000, 1, 1), # Chosen Seren's Treasure Chest | 50% Bonus EXP Coupon Voucher
    (8880615, 2633920, 1000, 1, 1), # Chosen Seren | 50% Bonus EXP Coupon Voucher
    (8880614, 2590006, 500, 1, 1), # Chosen Seren's Treasure Chest | Production Soul Enchanter
    (8880615, 2590006, 500, 1, 1), # Chosen Seren | Production Soul Enchanter
    (8880614, 2633892, 10000, 1, 1), # Chosen Seren's Treasure Chest | Large Boss Medal of Honor
    (8880615, 2633892, 10000, 1, 1), # Chosen Seren | Large Boss Medal of Honor
    (8880614, 2633916, 1000, 1, 1), # Chosen Seren's Treasure Chest | Premium Scroll - Accessory Coupon
    (8880615, 2633916, 1000, 1, 1), # Chosen Seren | Premium Scroll - Accessory Coupon
    (8880614, 2633917, 1000, 1, 1), # Chosen Seren's Treasure Chest | Premium Scroll - Pet Equipment Coupon
    (8880615, 2633917, 1000, 1, 1), # Chosen Seren | Premium Scroll - Pet Equipment Coupon
    (8880614, 2633918, 1000, 1, 1), # Chosen Seren's Treasure Chest | Magical Scroll - Weapon Coupon
    (8880615, 2633918, 1000, 1, 1), # Chosen Seren | Magical Scroll - Weapon Coupon
    (8880614, 4021022, 3000, 1, 2), # Chosen Seren's Treasure Chest | Primal Essence
    (8880615, 4021022, 3000, 1, 2), # Chosen Seren | Primal Essence
    (8880614, 4021031, 3000, 1, 2), # Chosen Seren's Treasure Chest | Twisted Time
    (8880615, 4021031, 3000, 1, 2), # Chosen Seren | Twisted Time
    (8880614, 4001832, 10000, 240, 240), # Chosen Seren's Treasure Chest | Spell Trace
    (8880615, 4001832, 10000, 240, 240), # Chosen Seren | Spell Trace
    (8880614, 4001886, 3000, 1, 2), # Chosen Seren's Treasure Chest | Intense Power Crystal
    (8880615, 4001886, 3000, 1, 2), # Chosen Seren | Intense Power Crystal
    (8930100, 2590006, 500, 1, 1), # Vellum | Production Soul Enchanter
    (8930100, 4021031, 3000, 1, 2), # Vellum | Twisted Time
    (8930100, 4001832, 10000, 20, 20), # Vellum | Spell Trace
    (8930100, 4001886, 3000, 1, 2), # Vellum | Intense Power Crystal
    (8930000, 2590006, 500, 1, 1), # Chaos Vellum | Production Soul Enchanter
    (8930000, 4021031, 3000, 1, 2), # Chaos Vellum | Twisted Time
    (8930000, 4001832, 10000, 160, 160), # Chaos Vellum | Spell Trace
    (8930000, 4001886, 3000, 1, 2), # Chaos Vellum | Intense Power Crystal
    (8880405, 2003586, 3000, 3, 5), # Verus Hilla | Sparkling Blue Star Potion
    (8880405, 2003587, 3000, 3, 5), # Verus Hilla | Sparkling Red Star Potion
    (8880405, 2003607, 2000, 1, 1), # Verus Hilla | Small EXP Accumulation Potion
    (8880405, 2633920, 1000, 1, 1), # Verus Hilla | 50% Bonus EXP Coupon Voucher
    (8880405, 2633919, 1000, 1, 1), # Verus Hilla | Scroll - Accessory Coupon
    (8880405, 2633914, 300, 1, 1), # Verus Hilla | Arcane Umbra Weapon Box
    (8880405, 2633915, 300, 1, 1), # Verus Hilla | Arcane Umbra Armor Box
    (8880405, 4021022, 3000, 1, 2), # Verus Hilla | Primal Essence
    (8880405, 4021031, 3000, 1, 2), # Verus Hilla | Twisted Time
    (8880405, 4001832, 10000, 240, 240), # Verus Hilla | Spell Trace
    (8880405, 2590006, 500, 1, 1), # Verus Hilla | Production Soul Enchanter
    (8880405, 4001886, 3000, 1, 2), # Verus Hilla | Intense Power Crystal
    (8880410, 1122430, 50, 1, 1), # Verus Hilla | Source of Suffering
    (8880410, 2003586, 3000, 3, 5), # Verus Hilla | Sparkling Blue Star Potion
    (8880410, 2003587, 3000, 3, 5), # Verus Hilla | Sparkling Red Star Potion
    (8880410, 2003607, 2000, 1, 1), # Verus Hilla | Small EXP Accumulation Potion
    (8880410, 2633920, 1000, 1, 1), # Verus Hilla | 50% Bonus EXP Coupon Voucher
    (8880410, 2590006, 500, 1, 1), # Verus Hilla | Production Soul Enchanter
    (8880410, 2633892, 10000, 1, 1), # Verus Hilla | Large Boss Medal of Honor
    (8880410, 2439567, 3000, 1, 1), # Verus Hilla | Verus Hilla's Soul Shard
    (8880410, 2633916, 1000, 1, 1), # Verus Hilla | Premium Scroll - Accessory Coupon
    (8880410, 2633917, 1000, 1, 1), # Verus Hilla | Premium Scroll - Pet Equipment Coupon
    (8880410, 2633918, 1000, 1, 1), # Verus Hilla | Magical Scroll - Weapon Coupon
    (8880410, 2633914, 300, 1, 1), # Verus Hilla | Arcane Umbra Weapon Box
    (8880410, 2633915, 300, 1, 1), # Verus Hilla | Arcane Umbra Armor Box
    (8880410, 4021022, 3000, 1, 2), # Verus Hilla | Primal Essence
    (8880410, 4021031, 3000, 1, 2), # Verus Hilla | Twisted Time
    (8880410, 4001832, 10000, 240, 240), # Verus Hilla | Spell Trace
    (8880410, 4001886, 3000, 1, 2), # Verus Hilla | Intense Power Crystal
    (8910100, 2590006, 500, 1, 1), # Von Bon | Production Soul Enchanter
    (8910100, 4021031, 3000, 1, 2), # Von Bon | Twisted Time
    (8910100, 4001832, 10000, 20, 20), # Von Bon | Spell Trace
    (8910100, 4001886, 3000, 1, 2), # Von Bon | Intense Power Crystal
    (8910000, 4021031, 3000, 1, 2), # Chaos Von Bon | Twisted Time
    (8910000, 4001832, 10000, 160, 160), # Chaos Von Bon | Spell Trace
    (8910000, 2590006, 500, 1, 1), # Chaos Von Bon | Production Soul Enchanter
    (8910000, 4001886, 3000, 1, 2), # Chaos Von Bon | Intense Power Crystal
    (8840000, 2590006, 500, 1, 1), # Von Leon | Production Soul Enchanter
    (8840000, 4001886, 3000, 1, 2), # Von Leon | Intense Power Crystal
    (8840007, 2590006, 500, 1, 1), # Von Leon | Production Soul Enchanter
    (8840007, 4001832, 10000, 40, 40), # Von Leon | Spell Trace
    (8840007, 4001886, 3000, 1, 2), # Von Leon | Intense Power Crystal
    (8840014, 1032227, 400, 1, 1), # Von Leon | Ifia's Earrings
    (8840014, 1113089, 400, 1, 1), # Von Leon | Ifia's Ring
    (8840014, 1122274, 400, 1, 1), # Von Leon | Ifia's Necklace
    (8840014, 1004234, 400, 1, 1), # Von Leon | Royal Von Leon Warrior Helm
    (8840014, 1004235, 400, 1, 1), # Von Leon | Royal Von Leon Magician Helm
    (8840014, 1004236, 400, 1, 1), # Von Leon | Royal Von Leon Sentinel Helm
    (8840014, 1004237, 400, 1, 1), # Von Leon | Royal Von Leon Chaser Helm
    (8840014, 1004238, 400, 1, 1), # Von Leon | Royal Von Leon Skipper Helm
    (8840014, 1052804, 400, 1, 1), # Von Leon | Royal Von Leon Warrior Suit
    (8840014, 1052805, 400, 1, 1), # Von Leon | Royal Von Leon Mage Suit
    (8840014, 1052806, 400, 1, 1), # Von Leon | Royal Von Leon Sentinel Suit
    (8840014, 1052807, 400, 1, 1), # Von Leon | Royal Von Leon Chaser Suit
    (8840014, 1052808, 400, 1, 1), # Von Leon | Royal Von Leon Skipper Suit
    (8840014, 1072972, 400, 1, 1), # Von Leon | Royal Von Leon Warrior Boots
    (8840014, 1072973, 400, 1, 1), # Von Leon | Royal Von Leon Mage Boots
    (8840014, 1072974, 400, 1, 1), # Von Leon | Royal Von Leon Sentinel Boots
    (8840014, 1072975, 400, 1, 1), # Von Leon | Royal Von Leon Chaser Boots
    (8840014, 1072976, 400, 1, 1), # Von Leon | Royal Von Leon Skipper Boots
    (8840014, 1082613, 400, 1, 1), # Von Leon | Royal Von Leon Warrior Hands
    (8840014, 1082614, 400, 1, 1), # Von Leon | Royal Von Leon Mage Hands
    (8840014, 1082615, 400, 1, 1), # Von Leon | Royal Von Leon Sentinel Hands
    (8840014, 1082616, 400, 1, 1), # Von Leon | Royal Von Leon Chaser Hands
    (8840014, 1082617, 400, 1, 1), # Von Leon | Royal Von Leon Skipper Hands
    (8840014, 1102713, 400, 1, 1), # Von Leon | Royal Von Leon Warrior Cape
    (8840014, 1102714, 400, 1, 1), # Von Leon | Royal Von Leon Mage Cape
    (8840014, 1102715, 400, 1, 1), # Von Leon | Royal Von Leon Sentinel Cape
    (8840014, 1102716, 400, 1, 1), # Von Leon | Royal Von Leon Chaser Cape
    (8840014, 1102717, 400, 1, 1), # Von Leon | Royal Von Leon Skipper Cape
    (8840014, 1212102, 200, 1, 1), # Von Leon | Royal Von Leon Glorier
    (8840014, 1213013, 200, 1, 1), # Von Leon | Royal Von Leon Nobility
    (8840014, 1214013, 200, 1, 1), # Von Leon | Royal Von Leon Whispershot
    (8840014, 1222096, 200, 1, 1), # Von Leon | Royal Von Leon White Worm
    (8840014, 1232096, 200, 1, 1), # Von Leon | Royal Von Leon Blood Fury
    (8840014, 1242103, 200, 1, 1), # Von Leon | Royal Von Leon Energy Chain
    (8840014, 1252087, 200, 1, 1), # Von Leon | Royal Von Leon Stick
    (8840014, 1262014, 200, 1, 1), # Von Leon | Royal Von Leon Psy-limiter
    (8840014, 1272012, 200, 1, 1), # Von Leon | Royal Von Leon Chain
    (8840014, 1282012, 200, 1, 1), # Von Leon | Royal Von Leon Lucent Gauntlet
    (8840014, 1292013, 200, 1, 1), # Von Leon | Royal Von Leon Lion Ritual Fan
    (8840014, 1302316, 200, 1, 1), # Von Leon | Royal Von Leon Saber
    (8840014, 1312186, 200, 1, 1), # Von Leon | Royal Von Leon Axe
    (8840014, 1322237, 200, 1, 1), # Von Leon | Royal Von Leon Hammer
    (8840014, 1332261, 200, 1, 1), # Von Leon | Royal Von Leon Dagger
    (8840014, 1362122, 200, 1, 1), # Von Leon | Royal Von Leon Cane
    (8840014, 1372208, 200, 1, 1), # Von Leon | Royal Von Leon Wand
    (8840014, 1382246, 200, 1, 1), # Von Leon | Royal Von Leon Staff
    (8840014, 1402237, 200, 1, 1), # Von Leon | Royal Von Leon Sword
    (8840014, 1412179, 200, 1, 1), # Von Leon | Royal Von Leon Two-Handed Axe
    (8840014, 1422186, 200, 1, 1), # Von Leon | Royal Von Leon Two-Handed Hammer
    (8840014, 1432201, 200, 1, 1), # Von Leon | Royal Von Leon Spear
    (8840014, 1442255, 200, 1, 1), # Von Leon | Royal Von Leon Hellslayer
    (8840014, 1452239, 200, 1, 1), # Von Leon | Royal Von Leon Bow
    (8840014, 1462226, 200, 1, 1), # Von Leon | Royal Von Leon Crossbow
    (8840014, 1472248, 200, 1, 1), # Von Leon | Royal Von Leon Guards
    (8840014, 1482203, 200, 1, 1), # Von Leon | Royal Von Leon Claw
    (8840014, 1492213, 200, 1, 1), # Von Leon | Royal Von Leon Pistol
    (8840014, 1522125, 200, 1, 1), # Von Leon | Royal Von Leon Dual Bowguns
    (8840014, 1532131, 200, 1, 1), # Von Leon | Royal Von Leon Siege Gun
    (8840014, 1542102, 200, 1, 1), # Von Leon | Royal Von Leon Katana
    (8840014, 1552103, 200, 1, 1), # Von Leon | Royal Von Leon Fan
    (8840014, 1582014, 200, 1, 1), # Von Leon | Royal Von Leon Ymir
    (8840014, 1592013, 200, 1, 1), # Von Leon | Royal Von Leon Ancient Bow
    (8840014, 2590006, 500, 1, 1), # Von Leon | Production Soul Enchanter
    (8840014, 2433103, 2000, 1, 1), # Von Leon | Boss Medal of Honor
    (8840014, 2431662, 3000, 1, 1), # Von Leon | Von Leon Soul Shard
    (8840014, 4021031, 3000, 1, 2), # Von Leon | Twisted Time
    (8840014, 4001832, 10000, 72, 72), # Von Leon | Spell Trace
    (8840014, 4001886, 3000, 1, 2), # Von Leon | Intense Power Crystal
    (8880342, 2003586, 3000, 3, 5), # Will | Sparkling Blue Star Potion
    (8880342, 2003587, 3000, 3, 5), # Will | Sparkling Red Star Potion
    (8880342, 2003607, 2000, 1, 1), # Will | Small EXP Accumulation Potion
    (8880342, 2633920, 1000, 1, 1), # Will | 50% Bonus EXP Coupon Voucher
    (8880342, 2590006, 500, 1, 1), # Will | Production Soul Enchanter
    (8880342, 4021020, 3000, 1, 2), # Will | Confusion Fragment
    (8880342, 4021031, 3000, 1, 2), # Will | Twisted Time
    (8880342, 4001832, 10000, 160, 160), # Will | Spell Trace
    (8880342, 4001886, 3000, 1, 2), # Will | Intense Power Crystal
    (8880302, 2003586, 3000, 3, 5), # Will | Sparkling Blue Star Potion
    (8880302, 2003587, 3000, 3, 5), # Will | Sparkling Red Star Potion
    (8880302, 2003607, 2000, 1, 1), # Will | Small EXP Accumulation Potion
    (8880302, 2633920, 1000, 1, 1), # Will | 50% Bonus EXP Coupon Voucher
    (8880302, 2590006, 500, 1, 1), # Will | Production Soul Enchanter
    (8880302, 2633914, 300, 1, 1), # Will | Arcane Umbra Weapon Box
    (8880302, 2633915, 300, 1, 1), # Will | Arcane Umbra Armor Box
    (8880302, 2633926, 500, 1, 1), # Will | Will's Cursed Spellbook Selection Box
    (8880302, 4021020, 3000, 1, 2), # Will | Confusion Fragment
    (8880302, 4021031, 3000, 1, 2), # Will | Twisted Time
    (8880302, 4001832, 10000, 240, 240), # Will | Spell Trace
    (8880302, 4001886, 3000, 1, 2), # Will | Intense Power Crystal
    (8880302, 2633919, 1000, 1, 1), # Will | Scroll - Accessory Coupon
    (8800002, 1214009, 200, 1, 1), # Zakum | Zakum's Poisonic Whispershot
    (8800002, 1292009, 200, 1, 1), # Zakum | Zakum's Poisonic Ritual Fan
    (8800002, 1592010, 200, 1, 1), # Zakum | Zakum's Poisonic Ancient Bow
    (8800002, 2590006, 500, 1, 1), # Zakum | Production Soul Enchanter
    (8800002, 4021031, 3000, 1, 2), # Zakum | Twisted Time
    (8800002, 4001832, 10000, 20, 20), # Zakum | Spell Trace
    (8800002, 4001886, 3000, 1, 2), # Zakum | Intense Power Crystal
    (8800022, 2590006, 500, 1, 1), # Crumbling Zakum | Production Soul Enchanter
    (8800022, 4001886, 3000, 1, 2), # Crumbling Zakum | Intense Power Crystal
    (8800102, 1214009, 200, 1, 1), # Chaos Zakum | Zakum's Poisonic Whispershot
    (8800102, 1292009, 200, 1, 1), # Chaos Zakum | Zakum's Poisonic Ritual Fan
    (8800102, 1592010, 200, 1, 1), # Chaos Zakum | Zakum's Poisonic Ancient Bow
    (8800102, 2590006, 500, 1, 1), # Chaos Zakum | Production Soul Enchanter
    (8800102, 4021031, 3000, 1, 2), # Chaos Zakum | Twisted Time
    (8800102, 4001832, 10000, 160, 160), # Chaos Zakum | Spell Trace
    (8800102, 4001886, 3000, 1, 2); # Chaos Zakum | Intense Power Crystal

insert into `equip_drops` (`id`, `job`, `level`)
values
    (1002010, 'BOWMAN', 10),
    (1002017, 'MAGICIAN', 10),
    (1002023, 'WARRIOR', 30),
    (1002025, 'WARRIOR', 40),
    (1002028, 'WARRIOR', 50),
    (1002029, 'WARRIOR', 60),
    (1002030, 'WARRIOR', 70),
    (1002036, 'MAGICIAN', 30),
    (1002043, 'WARRIOR', 10),
    (1002059, 'WARRIOR', 20),
    (1002073, 'MAGICIAN', 20),
    (1002119, 'BOWMAN', 20),
    (1002125, 'THIEF', 10),
    (1002130, 'THIEF', 20),
    (1002155, 'MAGICIAN', 40),
    (1002163, 'BOWMAN', 30),
    (1002168, 'BOWMAN', 40),
    (1002175, 'THIEF', 30),
    (1002185, 'THIEF', 40),
    (1002207, 'THIEF', 50),
    (1002214, 'BOWMAN', 50),
    (1002217, 'MAGICIAN', 50),
    (1002246, 'MAGICIAN', 60),
    (1002249, 'THIEF', 60),
    (1002254, 'MAGICIAN', 70),
    (1002270, 'BOWMAN', 60),
    (1002274, 'MAGICIAN', 80),
    (1002278, 'BOWMAN', 80),
    (1002284, 'THIEF', 70),
    (1002289, 'BOWMAN', 70),
    (1002326, 'THIEF', 90),
    (1002330, 'THIEF', 80),
    (1002340, 'WARRIOR', 80),
    (1002366, 'MAGICIAN', 90),
    (1002379, 'WARRIOR', 100),
    (1002383, 'THIEF', 100),
    (1002401, 'MAGICIAN', 100),
    (1002405, 'BOWMAN', 90),
    (1002407, 'BOWMAN', 100),
    (1002532, 'WARRIOR', 90),
    (1002547, 'BOWMAN', 110),
    (1002550, 'THIEF', 110),
    (1002551, 'WARRIOR', 110),
    (1002610, 'PIRATE', 10),
    (1002616, 'PIRATE', 20),
    (1002622, 'PIRATE', 30),
    (1002628, 'PIRATE', 40),
    (1002631, 'PIRATE', 50),
    (1002634, 'PIRATE', 60),
    (1002637, 'PIRATE', 70),
    (1002640, 'PIRATE', 80),
    (1002643, 'PIRATE', 90),
    (1002646, 'PIRATE', 100),
    (1002649, 'PIRATE', 110),
    (1002773, 'MAGICIAN', 110),
    (1004219, 'WARRIOR', 120),
    (1004220, 'MAGICIAN', 120),
    (1004221, 'BOWMAN', 120),
    (1004222, 'THIEF', 120),
    (1004223, 'PIRATE', 120),
    (1004224, 'WARRIOR', 130),
    (1004225, 'MAGICIAN', 130),
    (1004226, 'BOWMAN', 130),
    (1004227, 'THIEF', 130),
    (1004228, 'PIRATE', 130),
    (1004229, 'WARRIOR', 140),
    (1004230, 'MAGICIAN', 140),
    (1004231, 'BOWMAN', 140),
    (1004232, 'THIEF', 140),
    (1004233, 'PIRATE', 140),
    (1004383, 'BOWMAN', 70),
    (1040003, 'BOWMAN', 20),
    (1040008, 'BOWMAN', 10),
    (1040012, 'WARRIOR', 20),
    (1040015, 'WARRIOR', 10),
    (1040020, 'MAGICIAN', 10),
    (1040021, 'WARRIOR', 30),
    (1040024, 'BOWMAN', 20),
    (1040033, 'THIEF', 10),
    (1040034, 'THIEF', 10),
    (1040044, 'THIEF', 20),
    (1040048, 'THIEF', 20),
    (1040057, 'THIEF', 30),
    (1040061, 'THIEF', 30),
    (1040067, 'BOWMAN', 30),
    (1040075, 'BOWMAN', 30),
    (1040079, 'BOWMAN', 40),
    (1040083, 'THIEF', 40),
    (1040085, 'WARRIOR', 40),
    (1040087, 'WARRIOR', 50),
    (1040092, 'WARRIOR', 60),
    (1040094, 'THIEF', 50),
    (1040099, 'THIEF', 60),
    (1040103, 'WARRIOR', 70),
    (1040107, 'THIEF', 70),
    (1040109, 'THIEF', 80),
    (1040112, 'WARRIOR', 90),
    (1040116, 'THIEF', 90),
    (1040121, 'WARRIOR', 100),
    (1040146, 'BOWMAN', 30),
    (1040160, 'MAGICIAN', 0),
    (1040178, 'BOWMAN', 40),
    (1041003, 'THIEF', 20),
    (1041008, 'BOWMAN', 10),
    (1041014, 'WARRIOR', 10),
    (1041015, 'MAGICIAN', 0),
    (1041021, 'WARRIOR', 20),
    (1041027, 'BOWMAN', 20),
    (1041034, 'BOWMAN', 20),
    (1041035, 'BOWMAN', 20),
    (1041041, 'MAGICIAN', 20),
    (1041052, 'MAGICIAN', 30),
    (1041054, 'BOWMAN', 30),
    (1041067, 'BOWMAN', 30),
    (1041081, 'BOWMAN', 40),
    (1041085, 'WARRIOR', 40),
    (1041092, 'WARRIOR', 60),
    (1041095, 'THIEF', 60),
    (1041097, 'WARRIOR', 70),
    (1041101, 'THIEF', 70),
    (1041106, 'THIEF', 80),
    (1041116, 'THIEF', 90),
    (1041120, 'WARRIOR', 90),
    (1041123, 'WARRIOR', 100),
    (1041176, 'BOWMAN', 30),
    (1050008, 'MAGICIAN', 10),
    (1050027, 'MAGICIAN', 20),
    (1050039, 'MAGICIAN', 30),
    (1050052, 'BOWMAN', 50),
    (1050053, 'MAGICIAN', 50),
    (1050059, 'BOWMAN', 60),
    (1050061, 'BOWMAN', 70),
    (1050067, 'MAGICIAN', 60),
    (1050073, 'MAGICIAN', 70),
    (1050075, 'BOWMAN', 80),
    (1050081, 'WARRIOR', 80),
    (1050088, 'BOWMAN', 90),
    (1050092, 'MAGICIAN', 80),
    (1050097, 'THIEF', 100),
    (1050102, 'MAGICIAN', 90),
    (1050103, 'MAGICIAN', 90),
    (1050107, 'BOWMAN', 100),
    (1050194, 'MAGICIAN', 10),
    (1050197, 'MAGICIAN', 20),
    (1050200, 'MAGICIAN', 30),
    (1050202, 'BOWMAN', 50),
    (1050313, 'PIRATE', 60),
    (1051034, 'MAGICIAN', 40),
    (1051037, 'BOWMAN', 50),
    (1051041, 'BOWMAN', 60),
    (1051044, 'MAGICIAN', 50),
    (1051052, 'MAGICIAN', 60),
    (1051057, 'MAGICIAN', 70),
    (1051062, 'BOWMAN', 70),
    (1051066, 'BOWMAN', 80),
    (1051078, 'WARRIOR', 80),
    (1051082, 'BOWMAN', 90),
    (1051091, 'THIEF', 100),
    (1051094, 'MAGICIAN', 80),
    (1051102, 'MAGICIAN', 90),
    (1051103, 'MAGICIAN', 90),
    (1051106, 'BOWMAN', 100),
    (1052071, 'BOWMAN', 110),
    (1052072, 'THIEF', 110),
    (1052075, 'WARRIOR', 110),
    (1052076, 'MAGICIAN', 100),
    (1052095, 'PIRATE', 10),
    (1052098, 'PIRATE', 10),
    (1052101, 'PIRATE', 20),
    (1052107, 'PIRATE', 30),
    (1052113, 'PIRATE', 40),
    (1052116, 'PIRATE', 50),
    (1052122, 'PIRATE', 70),
    (1052125, 'PIRATE', 80),
    (1052128, 'PIRATE', 90),
    (1052131, 'PIRATE', 100),
    (1052134, 'PIRATE', 110),
    (1052789, 'WARRIOR', 120),
    (1052790, 'MAGICIAN', 120),
    (1052791, 'BOWMAN', 120),
    (1052792, 'THIEF', 120),
    (1052793, 'PIRATE', 120),
    (1052794, 'WARRIOR', 130),
    (1052795, 'MAGICIAN', 130),
    (1052796, 'BOWMAN', 130),
    (1052797, 'THIEF', 130),
    (1052798, 'PIRATE', 130),
    (1052799, 'WARRIOR', 140),
    (1052800, 'MAGICIAN', 140),
    (1052801, 'BOWMAN', 140),
    (1052802, 'THIEF', 140),
    (1052803, 'PIRATE', 140),
    (1060008, 'WARRIOR', 10),
    (1060018, 'WARRIOR', 30),
    (1060024, 'THIEF', 10),
    (1060028, 'WARRIOR', 10),
    (1060029, 'WARRIOR', 20),
    (1060033, 'THIEF', 20),
    (1060037, 'THIEF', 20),
    (1060038, 'THIEF', 20),
    (1060043, 'THIEF', 30),
    (1060056, 'BOWMAN', 30),
    (1060065, 'BOWMAN', 30),
    (1060069, 'BOWMAN', 40),
    (1060075, 'WARRIOR', 40),
    (1060076, 'WARRIOR', 50),
    (1060080, 'WARRIOR', 60),
    (1060083, 'THIEF', 50),
    (1060088, 'THIEF', 60),
    (1060091, 'WARRIOR', 70),
    (1060101, 'WARRIOR', 90),
    (1060111, 'WARRIOR', 100),
    (1060133, 'BOWMAN', 140),
    (1060133, 'MAGICIAN', 140),
    (1060133, 'PIRATE', 140),
    (1060133, 'THIEF', 140),
    (1060133, 'WARRIOR', 140),
    (1060155, 'WARRIOR', 20),
    (1060160, 'THIEF', 20),
    (1061003, 'THIEF', 20),
    (1061006, 'BOWMAN', 10),
    (1061010, 'MAGICIAN', 0),
    (1061025, 'BOWMAN', 20),
    (1061040, 'THIEF', 20),
    (1061062, 'BOWMAN', 30),
    (1061080, 'BOWMAN', 40),
    (1061091, 'WARRIOR', 60),
    (1061094, 'THIEF', 60),
    (1061100, 'THIEF', 70),
    (1061118, 'WARRIOR', 90),
    (1061123, 'WARRIOR', 100),
    (1061155, 'BOWMAN', 140),
    (1061155, 'MAGICIAN', 140),
    (1061155, 'PIRATE', 140),
    (1061155, 'THIEF', 140),
    (1061155, 'WARRIOR', 140),
    (1072000, 'WARRIOR', 40),
    (1072007, 'WARRIOR', 20),
    (1072019, 'MAGICIAN', 20),
    (1072026, 'BOWMAN', 20),
    (1072065, 'THIEF', 20),
    (1072107, 'THIEF', 40),
    (1072115, 'MAGICIAN', 40),
    (1072120, 'BOWMAN', 40),
    (1072139, 'MAGICIAN', 60),
    (1072144, 'BOWMAN', 60),
    (1072149, 'WARRIOR', 60),
    (1072151, 'THIEF', 60),
    (1072162, 'THIEF', 70),
    (1072174, 'THIEF', 80),
    (1072178, 'MAGICIAN', 80),
    (1072184, 'BOWMAN', 80),
    (1072203, 'BOWMAN', 90),
    (1072204, 'BOWMAN', 90),
    (1072210, 'WARRIOR', 80),
    (1072216, 'THIEF', 100),
    (1072222, 'WARRIOR', 100),
    (1072224, 'MAGICIAN', 100),
    (1072228, 'BOWMAN', 100),
    (1072268, 'MAGICIAN', 110),
    (1072269, 'BOWMAN', 110),
    (1072272, 'THIEF', 110),
    (1072273, 'WARRIOR', 110),
    (1072288, 'PIRATE', 20),
    (1072300, 'PIRATE', 40),
    (1072306, 'PIRATE', 60),
    (1072312, 'PIRATE', 80),
    (1072318, 'PIRATE', 100),
    (1072321, 'PIRATE', 110),
    (1072592, 'THIEF', 40),
    (1072957, 'WARRIOR', 120),
    (1072958, 'MAGICIAN', 120),
    (1072959, 'BOWMAN', 120),
    (1072960, 'THIEF', 120),
    (1072961, 'PIRATE', 120),
    (1072962, 'WARRIOR', 130),
    (1072963, 'MAGICIAN', 130),
    (1072964, 'BOWMAN', 130),
    (1072965, 'THIEF', 130),
    (1072966, 'PIRATE', 130),
    (1072967, 'WARRIOR', 140),
    (1072968, 'MAGICIAN', 140),
    (1072969, 'BOWMAN', 140),
    (1072970, 'THIEF', 140),
    (1072971, 'PIRATE', 140),
    (1082004, 'WARRIOR', 20),
    (1082025, 'WARRIOR', 40),
    (1082029, 'THIEF', 10),
    (1082059, 'WARRIOR', 60),
    (1082064, 'MAGICIAN', 40),
    (1082086, 'MAGICIAN', 60),
    (1082090, 'BOWMAN', 60),
    (1082093, 'THIEF', 60),
    (1082111, 'BOWMAN', 80),
    (1082116, 'WARRIOR', 80),
    (1082120, 'THIEF', 80),
    (1082122, 'MAGICIAN', 80),
    (1082127, 'BOWMAN', 90),
    (1082138, 'THIEF', 100),
    (1082141, 'WARRIOR', 100),
    (1082152, 'MAGICIAN', 100),
    (1082160, 'BOWMAN', 100),
    (1082163, 'BOWMAN', 110),
    (1082164, 'MAGICIAN', 110),
    (1082167, 'THIEF', 110),
    (1082168, 'WARRIOR', 110),
    (1082195, 'PIRATE', 40),
    (1082201, 'PIRATE', 60),
    (1082207, 'PIRATE', 80),
    (1082213, 'PIRATE', 100),
    (1082216, 'PIRATE', 110),
    (1082355, 'PIRATE', 20),
    (1082598, 'WARRIOR', 120),
    (1082599, 'MAGICIAN', 120),
    (1082600, 'BOWMAN', 120),
    (1082601, 'THIEF', 120),
    (1082602, 'PIRATE', 120),
    (1082603, 'WARRIOR', 130),
    (1082604, 'MAGICIAN', 130),
    (1082605, 'BOWMAN', 130),
    (1082606, 'THIEF', 130),
    (1082607, 'PIRATE', 130),
    (1082608, 'WARRIOR', 140),
    (1082609, 'MAGICIAN', 140),
    (1082610, 'BOWMAN', 140),
    (1082611, 'THIEF', 140),
    (1082612, 'PIRATE', 140),
    (1102003, 'BOWMAN', 50),
    (1102003, 'MAGICIAN', 50),
    (1102003, 'PIRATE', 50),
    (1102003, 'THIEF', 50),
    (1102003, 'WARRIOR', 50),
    (1102004, 'BOWMAN', 50),
    (1102004, 'MAGICIAN', 50),
    (1102004, 'PIRATE', 50),
    (1102004, 'THIEF', 50),
    (1102004, 'WARRIOR', 50),
    (1102017, 'BOWMAN', 60),
    (1102017, 'MAGICIAN', 60),
    (1102017, 'PIRATE', 60),
    (1102017, 'THIEF', 60),
    (1102017, 'WARRIOR', 60),
    (1102018, 'BOWMAN', 60),
    (1102018, 'MAGICIAN', 60),
    (1102018, 'PIRATE', 60),
    (1102018, 'THIEF', 60),
    (1102018, 'WARRIOR', 60),
    (1102029, 'BOWMAN', 70),
    (1102029, 'MAGICIAN', 70),
    (1102029, 'PIRATE', 70),
    (1102029, 'THIEF', 70),
    (1102029, 'WARRIOR', 70),
    (1102030, 'BOWMAN', 70),
    (1102030, 'MAGICIAN', 70),
    (1102030, 'PIRATE', 70),
    (1102030, 'THIEF', 70),
    (1102030, 'WARRIOR', 70),
    (1102033, 'BOWMAN', 80),
    (1102033, 'MAGICIAN', 80),
    (1102033, 'PIRATE', 80),
    (1102033, 'THIEF', 80),
    (1102033, 'WARRIOR', 80),
    (1102034, 'BOWMAN', 80),
    (1102034, 'MAGICIAN', 80),
    (1102034, 'PIRATE', 80),
    (1102034, 'THIEF', 80),
    (1102034, 'WARRIOR', 80),
    (1102718, 'WARRIOR', 140),
    (1102719, 'MAGICIAN', 140),
    (1102720, 'BOWMAN', 140),
    (1102721, 'THIEF', 140),
    (1102722, 'PIRATE', 140),
    (1212001, 'MAGICIAN', 10),
    (1212002, 'MAGICIAN', 30),
    (1212003, 'MAGICIAN', 50),
    (1212004, 'MAGICIAN', 60),
    (1212005, 'MAGICIAN', 70),
    (1212006, 'MAGICIAN', 80),
    (1212007, 'MAGICIAN', 90),
    (1212010, 'MAGICIAN', 110),
    (1212099, 'MAGICIAN', 120),
    (1212100, 'MAGICIAN', 130),
    (1212101, 'MAGICIAN', 140),
    (1213000, 'WARRIOR', 10),
    (1213001, 'WARRIOR', 30),
    (1213002, 'WARRIOR', 50),
    (1213003, 'WARRIOR', 60),
    (1213004, 'WARRIOR', 70),
    (1213005, 'WARRIOR', 80),
    (1213006, 'WARRIOR', 90),
    (1213007, 'WARRIOR', 100),
    (1213008, 'WARRIOR', 110),
    (1213010, 'WARRIOR', 120),
    (1213012, 'WARRIOR', 130),
    (1213014, 'WARRIOR', 140),
    (1214000, 'BOWMAN', 10),
    (1214001, 'BOWMAN', 30),
    (1214002, 'BOWMAN', 50),
    (1214003, 'BOWMAN', 60),
    (1214004, 'BOWMAN', 70),
    (1214005, 'BOWMAN', 80),
    (1214006, 'BOWMAN', 90),
    (1214007, 'BOWMAN', 100),
    (1214008, 'BOWMAN', 110),
    (1214010, 'BOWMAN', 120),
    (1214012, 'BOWMAN', 130),
    (1214014, 'BOWMAN', 140),
    (1222001, 'PIRATE', 10),
    (1222002, 'PIRATE', 30),
    (1222003, 'PIRATE', 50),
    (1222004, 'PIRATE', 60),
    (1222005, 'PIRATE', 70),
    (1222006, 'PIRATE', 80),
    (1222007, 'PIRATE', 90),
    (1222008, 'PIRATE', 100),
    (1222009, 'PIRATE', 100),
    (1222010, 'PIRATE', 110),
    (1222093, 'PIRATE', 120),
    (1222094, 'PIRATE', 130),
    (1222095, 'PIRATE', 140),
    (1232000, 'WARRIOR', 10),
    (1232002, 'WARRIOR', 30),
    (1232003, 'WARRIOR', 50),
    (1232004, 'WARRIOR', 60),
    (1232005, 'WARRIOR', 70),
    (1232006, 'WARRIOR', 80),
    (1232007, 'WARRIOR', 90),
    (1232008, 'WARRIOR', 100),
    (1232010, 'WARRIOR', 110),
    (1232093, 'WARRIOR', 120),
    (1232094, 'WARRIOR', 130),
    (1232095, 'WARRIOR', 140),
    (1242001, 'PIRATE', 10),
    (1242001, 'THIEF', 10),
    (1242002, 'PIRATE', 30),
    (1242002, 'THIEF', 30),
    (1242003, 'PIRATE', 50),
    (1242003, 'THIEF', 50),
    (1242004, 'PIRATE', 60),
    (1242004, 'THIEF', 60),
    (1242005, 'PIRATE', 70),
    (1242005, 'THIEF', 70),
    (1242006, 'PIRATE', 80),
    (1242006, 'THIEF', 80),
    (1242007, 'PIRATE', 90),
    (1242007, 'THIEF', 90),
    (1242008, 'PIRATE', 100),
    (1242008, 'THIEF', 100),
    (1242009, 'PIRATE', 100),
    (1242009, 'THIEF', 100),
    (1242010, 'PIRATE', 110),
    (1242010, 'THIEF', 110),
    (1242100, 'PIRATE', 120),
    (1242100, 'THIEF', 120),
    (1242101, 'PIRATE', 130),
    (1242101, 'THIEF', 130),
    (1242102, 'PIRATE', 140),
    (1242102, 'THIEF', 140),
    (1252001, 'MAGICIAN', 10),
    (1252006, 'MAGICIAN', 80),
    (1252007, 'MAGICIAN', 90),
    (1252008, 'MAGICIAN', 100),
    (1252009, 'MAGICIAN', 100),
    (1252010, 'MAGICIAN', 110),
    (1252048, 'MAGICIAN', 10),
    (1252049, 'MAGICIAN', 20),
    (1252050, 'MAGICIAN', 30),
    (1252051, 'MAGICIAN', 40),
    (1252052, 'MAGICIAN', 50),
    (1252053, 'MAGICIAN', 60),
    (1252054, 'MAGICIAN', 70),
    (1252084, 'MAGICIAN', 120),
    (1252085, 'MAGICIAN', 130),
    (1252086, 'MAGICIAN', 140),
    (1262000, 'MAGICIAN', 10),
    (1262001, 'MAGICIAN', 30),
    (1262002, 'MAGICIAN', 50),
    (1262003, 'MAGICIAN', 60),
    (1262004, 'MAGICIAN', 70),
    (1262005, 'MAGICIAN', 80),
    (1262006, 'MAGICIAN', 90),
    (1262007, 'MAGICIAN', 100),
    (1262008, 'MAGICIAN', 110),
    (1262009, 'MAGICIAN', 120),
    (1262010, 'MAGICIAN', 130),
    (1262011, 'MAGICIAN', 140),
    (1272000, 'THIEF', 10),
    (1272001, 'THIEF', 30),
    (1272002, 'THIEF', 50),
    (1272003, 'THIEF', 60),
    (1272004, 'THIEF', 70),
    (1272005, 'THIEF', 80),
    (1272006, 'THIEF', 90),
    (1272007, 'THIEF', 100),
    (1272008, 'THIEF', 110),
    (1272009, 'THIEF', 120),
    (1272011, 'THIEF', 130),
    (1272013, 'THIEF', 140),
    (1282000, 'MAGICIAN', 10),
    (1282001, 'MAGICIAN', 30),
    (1282002, 'MAGICIAN', 50),
    (1282003, 'MAGICIAN', 60),
    (1282004, 'MAGICIAN', 70),
    (1282005, 'MAGICIAN', 80),
    (1282006, 'MAGICIAN', 90),
    (1282007, 'MAGICIAN', 100),
    (1282008, 'MAGICIAN', 110),
    (1282010, 'MAGICIAN', 120),
    (1282011, 'MAGICIAN', 130),
    (1282013, 'MAGICIAN', 140),
    (1292000, 'THIEF', 10),
    (1292001, 'THIEF', 30),
    (1292002, 'THIEF', 50),
    (1292003, 'THIEF', 40),
    (1292004, 'THIEF', 70),
    (1292005, 'THIEF', 80),
    (1292006, 'THIEF', 90),
    (1292010, 'THIEF', 110),
    (1292012, 'THIEF', 130),
    (1292014, 'THIEF', 140),
    (1302002, 'WARRIOR', 20),
    (1302004, 'WARRIOR', 30),
    (1302005, 'WARRIOR', 10),
    (1302006, 'WARRIOR', 20),
    (1302008, 'WARRIOR', 30),
    (1302009, 'WARRIOR', 40),
    (1302010, 'WARRIOR', 50),
    (1302011, 'WARRIOR', 60),
    (1302012, 'WARRIOR', 70),
    (1302018, 'WARRIOR', 80),
    (1302023, 'WARRIOR', 90),
    (1302056, 'WARRIOR', 100),
    (1302059, 'WARRIOR', 110),
    (1302313, 'WARRIOR', 120),
    (1302314, 'WARRIOR', 130),
    (1302315, 'WARRIOR', 140),
    (1312000, 'WARRIOR', 10),
    (1312001, 'WARRIOR', 10),
    (1312003, 'WARRIOR', 20),
    (1312005, 'WARRIOR', 30),
    (1312006, 'WARRIOR', 30),
    (1312007, 'WARRIOR', 40),
    (1312008, 'WARRIOR', 50),
    (1312009, 'WARRIOR', 60),
    (1312010, 'WARRIOR', 70),
    (1312011, 'WARRIOR', 80),
    (1312015, 'WARRIOR', 90),
    (1312030, 'WARRIOR', 100),
    (1312031, 'WARRIOR', 110),
    (1312183, 'WARRIOR', 120),
    (1312184, 'WARRIOR', 130),
    (1312185, 'WARRIOR', 140),
    (1322000, 'WARRIOR', 10),
    (1322002, 'WARRIOR', 20),
    (1322004, 'WARRIOR', 20),
    (1322014, 'WARRIOR', 30),
    (1322016, 'WARRIOR', 40),
    (1322017, 'WARRIOR', 50),
    (1322018, 'WARRIOR', 60),
    (1322019, 'WARRIOR', 70),
    (1322020, 'WARRIOR', 70),
    (1322028, 'WARRIOR', 80),
    (1322029, 'WARRIOR', 90),
    (1322045, 'WARRIOR', 100),
    (1322052, 'WARRIOR', 110),
    (1322234, 'WARRIOR', 120),
    (1322235, 'WARRIOR', 130),
    (1322236, 'WARRIOR', 140),
    (1332000, 'THIEF', 10),
    (1332001, 'THIEF', 30),
    (1332002, 'THIEF', 10),
    (1332003, 'THIEF', 50),
    (1332004, 'THIEF', 20),
    (1332006, 'THIEF', 10),
    (1332007, 'THIEF', 0),
    (1332008, 'THIEF', 20),
    (1332009, 'THIEF', 30),
    (1332010, 'THIEF', 20),
    (1332011, 'THIEF', 40),
    (1332012, 'THIEF', 30),
    (1332013, 'THIEF', 20),
    (1332014, 'THIEF', 30),
    (1332015, 'THIEF', 60),
    (1332016, 'THIEF', 50),
    (1332017, 'THIEF', 60),
    (1332018, 'THIEF', 70),
    (1332019, 'THIEF', 70),
    (1332022, 'THIEF', 80),
    (1332023, 'THIEF', 80),
    (1332024, 'THIEF', 50),
    (1332026, 'THIEF', 90),
    (1332027, 'THIEF', 90),
    (1332030, 'THIEF', 60),
    (1332031, 'THIEF', 40),
    (1332049, 'THIEF', 110),
    (1332050, 'THIEF', 110),
    (1332052, 'THIEF', 100),
    (1332258, 'THIEF', 120),
    (1332259, 'THIEF', 130),
    (1332260, 'THIEF', 140),
    (1342000, 'THIEF', 20),
    (1342001, 'THIEF', 30),
    (1342002, 'THIEF', 40),
    (1342003, 'THIEF', 50),
    (1342004, 'THIEF', 60),
    (1342005, 'THIEF', 70),
    (1342006, 'THIEF', 80),
    (1342007, 'THIEF', 90),
    (1342008, 'THIEF', 100),
    (1342010, 'THIEF', 110),
    (1342098, 'THIEF', 120),
    (1342099, 'THIEF', 130),
    (1342100, 'THIEF', 140),
    (1362002, 'THIEF', 10),
    (1362003, 'THIEF', 20),
    (1362004, 'THIEF', 20),
    (1362005, 'THIEF', 30),
    (1362006, 'THIEF', 30),
    (1362007, 'THIEF', 40),
    (1362008, 'THIEF', 50),
    (1362009, 'THIEF', 60),
    (1362010, 'THIEF', 70),
    (1362011, 'THIEF', 70),
    (1362012, 'THIEF', 90),
    (1362013, 'THIEF', 100),
    (1362015, 'THIEF', 110),
    (1362119, 'THIEF', 120),
    (1362120, 'THIEF', 130),
    (1362121, 'THIEF', 140),
    (1372000, 'MAGICIAN', 30),
    (1372001, 'MAGICIAN', 30),
    (1372002, 'MAGICIAN', 10),
    (1372003, 'MAGICIAN', 20),
    (1372004, 'MAGICIAN', 20),
    (1372005, 'MAGICIAN', 0),
    (1372007, 'MAGICIAN', 40),
    (1372008, 'MAGICIAN', 60),
    (1372009, 'MAGICIAN', 80),
    (1372010, 'MAGICIAN', 90),
    (1372012, 'MAGICIAN', 30),
    (1372014, 'MAGICIAN', 50),
    (1372015, 'MAGICIAN', 60),
    (1372016, 'MAGICIAN', 70),
    (1372032, 'MAGICIAN', 100),
    (1372205, 'MAGICIAN', 120),
    (1372206, 'MAGICIAN', 130),
    (1372207, 'MAGICIAN', 140),
    (1382000, 'MAGICIAN', 10),
    (1382002, 'MAGICIAN', 20),
    (1382003, 'MAGICIAN', 10),
    (1382004, 'MAGICIAN', 20),
    (1382005, 'MAGICIAN', 10),
    (1382009, 'MAGICIAN', 30),
    (1382011, 'MAGICIAN', 50),
    (1382017, 'MAGICIAN', 30),
    (1382018, 'MAGICIAN', 30),
    (1382019, 'MAGICIAN', 40),
    (1382020, 'MAGICIAN', 40),
    (1382023, 'MAGICIAN', 70),
    (1382024, 'MAGICIAN', 80),
    (1382026, 'MAGICIAN', 50),
    (1382027, 'MAGICIAN', 60),
    (1382035, 'MAGICIAN', 90),
    (1382036, 'MAGICIAN', 110),
    (1382037, 'MAGICIAN', 100),
    (1382243, 'MAGICIAN', 120),
    (1382244, 'MAGICIAN', 130),
    (1382245, 'MAGICIAN', 140),
    (1402000, 'WARRIOR', 20),
    (1402001, 'WARRIOR', 10),
    (1402002, 'WARRIOR', 30),
    (1402003, 'WARRIOR', 50),
    (1402004, 'WARRIOR', 80),
    (1402005, 'WARRIOR', 90),
    (1402006, 'WARRIOR', 30),
    (1402007, 'WARRIOR', 40),
    (1402008, 'WARRIOR', 20),
    (1402011, 'WARRIOR', 60),
    (1402012, 'WARRIOR', 70),
    (1402013, 'WARRIOR', 50),
    (1402015, 'WARRIOR', 80),
    (1402016, 'WARRIOR', 90),
    (1402035, 'WARRIOR', 100),
    (1402036, 'WARRIOR', 110),
    (1402037, 'WARRIOR', 100),
    (1402234, 'WARRIOR', 120),
    (1402235, 'WARRIOR', 130),
    (1402236, 'WARRIOR', 140),
    (1412000, 'WARRIOR', 20),
    (1412001, 'WARRIOR', 10),
    (1412003, 'WARRIOR', 50),
    (1412004, 'WARRIOR', 30),
    (1412005, 'WARRIOR', 40),
    (1412006, 'WARRIOR', 30),
    (1412007, 'WARRIOR', 60),
    (1412008, 'WARRIOR', 70),
    (1412009, 'WARRIOR', 80),
    (1412010, 'WARRIOR', 90),
    (1412012, 'WARRIOR', 10),
    (1412021, 'WARRIOR', 100),
    (1412026, 'WARRIOR', 110),
    (1412162, 'WARRIOR', 120),
    (1412163, 'WARRIOR', 130),
    (1412164, 'WARRIOR', 140),
    (1422000, 'WARRIOR', 10),
    (1422001, 'WARRIOR', 30),
    (1422003, 'WARRIOR', 20),
    (1422005, 'WARRIOR', 50),
    (1422007, 'WARRIOR', 40),
    (1422008, 'WARRIOR', 30),
    (1422009, 'WARRIOR', 60),
    (1422010, 'WARRIOR', 70),
    (1422013, 'WARRIOR', 90),
    (1422018, 'WARRIOR', 80),
    (1422027, 'WARRIOR', 100),
    (1422028, 'WARRIOR', 110),
    (1422169, 'WARRIOR', 120),
    (1422170, 'WARRIOR', 130),
    (1422171, 'WARRIOR', 140),
    (1432000, 'WARRIOR', 10),
    (1432001, 'WARRIOR', 10),
    (1432002, 'WARRIOR', 30),
    (1432004, 'WARRIOR', 50),
    (1432005, 'WARRIOR', 40),
    (1432006, 'WARRIOR', 60),
    (1432007, 'WARRIOR', 70),
    (1432010, 'WARRIOR', 80),
    (1432011, 'WARRIOR', 90),
    (1432030, 'WARRIOR', 100),
    (1432038, 'WARRIOR', 110),
    (1432198, 'WARRIOR', 120),
    (1432199, 'WARRIOR', 130),
    (1432200, 'WARRIOR', 140),
    (1442000, 'WARRIOR', 10),
    (1442001, 'WARRIOR', 30),
    (1442005, 'WARRIOR', 50),
    (1442006, 'WARRIOR', 10),
    (1442007, 'WARRIOR', 20),
    (1442008, 'WARRIOR', 70),
    (1442009, 'WARRIOR', 40),
    (1442010, 'WARRIOR', 60),
    (1442019, 'WARRIOR', 80),
    (1442020, 'WARRIOR', 90),
    (1442044, 'WARRIOR', 100),
    (1442045, 'WARRIOR', 110),
    (1442169, 'WARRIOR', 120),
    (1442252, 'WARRIOR', 120),
    (1442253, 'WARRIOR', 130),
    (1442254, 'WARRIOR', 140),
    (1452001, 'BOWMAN', 20),
    (1452002, 'BOWMAN', 10),
    (1452003, 'BOWMAN', 10),
    (1452004, 'BOWMAN', 60),
    (1452005, 'BOWMAN', 30),
    (1452007, 'BOWMAN', 40),
    (1452008, 'BOWMAN', 50),
    (1452009, 'BOWMAN', 70),
    (1452010, 'BOWMAN', 70),
    (1452011, 'BOWMAN', 70),
    (1452012, 'BOWMAN', 80),
    (1452013, 'BOWMAN', 80),
    (1452014, 'BOWMAN', 80),
    (1452017, 'BOWMAN', 90),
    (1452019, 'BOWMAN', 100),
    (1452020, 'BOWMAN', 100),
    (1452021, 'BOWMAN', 100),
    (1452025, 'BOWMAN', 90),
    (1452026, 'BOWMAN', 90),
    (1452044, 'BOWMAN', 110),
    (1452236, 'BOWMAN', 120),
    (1452237, 'BOWMAN', 130),
    (1452238, 'BOWMAN', 140),
    (1462000, 'BOWMAN', 20),
    (1462001, 'BOWMAN', 10),
    (1462002, 'BOWMAN', 10),
    (1462003, 'BOWMAN', 20),
    (1462004, 'BOWMAN', 30),
    (1462005, 'BOWMAN', 30),
    (1462006, 'BOWMAN', 40),
    (1462007, 'BOWMAN', 50),
    (1462008, 'BOWMAN', 60),
    (1462009, 'BOWMAN', 70),
    (1462010, 'BOWMAN', 80),
    (1462011, 'BOWMAN', 80),
    (1462012, 'BOWMAN', 80),
    (1462013, 'BOWMAN', 80),
    (1462016, 'BOWMAN', 100),
    (1462017, 'BOWMAN', 100),
    (1462018, 'BOWMAN', 90),
    (1462021, 'BOWMAN', 90),
    (1462022, 'BOWMAN', 90),
    (1462039, 'BOWMAN', 110),
    (1462223, 'BOWMAN', 120),
    (1462224, 'BOWMAN', 130),
    (1462225, 'BOWMAN', 140),
    (1472000, 'THIEF', 10),
    (1472001, 'THIEF', 10),
    (1472002, 'THIEF', 10),
    (1472003, 'THIEF', 10),
    (1472004, 'THIEF', 20),
    (1472005, 'THIEF', 20),
    (1472006, 'THIEF', 20),
    (1472007, 'THIEF', 30),
    (1472008, 'THIEF', 30),
    (1472009, 'THIEF', 30),
    (1472010, 'THIEF', 30),
    (1472011, 'THIEF', 30),
    (1472012, 'THIEF', 30),
    (1472013, 'THIEF', 30),
    (1472014, 'THIEF', 40),
    (1472015, 'THIEF', 40),
    (1472016, 'THIEF', 40),
    (1472017, 'THIEF', 40),
    (1472018, 'THIEF', 50),
    (1472019, 'THIEF', 50),
    (1472020, 'THIEF', 50),
    (1472021, 'THIEF', 50),
    (1472022, 'THIEF', 60),
    (1472023, 'THIEF', 60),
    (1472024, 'THIEF', 60),
    (1472025, 'THIEF', 60),
    (1472026, 'THIEF', 70),
    (1472027, 'THIEF', 70),
    (1472028, 'THIEF', 70),
    (1472029, 'THIEF', 70),
    (1472031, 'THIEF', 80),
    (1472033, 'THIEF', 90),
    (1472051, 'THIEF', 110),
    (1472052, 'THIEF', 110),
    (1472053, 'THIEF', 100),
    (1472055, 'THIEF', 60),
    (1472245, 'THIEF', 120),
    (1472246, 'THIEF', 130),
    (1472247, 'THIEF', 140),
    (1482000, 'PIRATE', 10),
    (1482001, 'PIRATE', 10),
    (1482002, 'PIRATE', 20),
    (1482003, 'PIRATE', 20),
    (1482004, 'PIRATE', 30),
    (1482005, 'PIRATE', 30),
    (1482006, 'PIRATE', 40),
    (1482007, 'PIRATE', 50),
    (1482008, 'PIRATE', 60),
    (1482009, 'PIRATE', 70),
    (1482010, 'PIRATE', 80),
    (1482011, 'PIRATE', 90),
    (1482012, 'PIRATE', 100),
    (1482013, 'PIRATE', 110),
    (1482020, 'PIRATE', 30),
    (1482022, 'PIRATE', 60),
    (1482200, 'PIRATE', 120),
    (1482201, 'PIRATE', 130),
    (1482202, 'PIRATE', 140),
    (1492000, 'PIRATE', 10),
    (1492001, 'PIRATE', 10),
    (1492002, 'PIRATE', 20),
    (1492003, 'PIRATE', 20),
    (1492004, 'PIRATE', 30),
    (1492005, 'PIRATE', 30),
    (1492006, 'PIRATE', 40),
    (1492007, 'PIRATE', 50),
    (1492008, 'PIRATE', 60),
    (1492009, 'PIRATE', 70),
    (1492010, 'PIRATE', 80),
    (1492011, 'PIRATE', 90),
    (1492012, 'PIRATE', 100),
    (1492013, 'PIRATE', 110),
    (1492210, 'PIRATE', 120),
    (1492211, 'PIRATE', 130),
    (1492212, 'PIRATE', 140),
    (1522000, 'BOWMAN', 10),
    (1522002, 'BOWMAN', 20),
    (1522003, 'BOWMAN', 20),
    (1522004, 'BOWMAN', 30),
    (1522005, 'BOWMAN', 30),
    (1522006, 'BOWMAN', 40),
    (1522007, 'BOWMAN', 50),
    (1522008, 'BOWMAN', 60),
    (1522009, 'BOWMAN', 70),
    (1522010, 'BOWMAN', 80),
    (1522011, 'BOWMAN', 90),
    (1522012, 'BOWMAN', 100),
    (1522014, 'BOWMAN', 110),
    (1522086, 'BOWMAN', 70),
    (1522122, 'BOWMAN', 120),
    (1522123, 'BOWMAN', 130),
    (1522124, 'BOWMAN', 140),
    (1532001, 'PIRATE', 10),
    (1532002, 'PIRATE', 20),
    (1532003, 'PIRATE', 20),
    (1532004, 'PIRATE', 30),
    (1532005, 'PIRATE', 30),
    (1532006, 'PIRATE', 40),
    (1532007, 'PIRATE', 50),
    (1532008, 'PIRATE', 60),
    (1532009, 'PIRATE', 70),
    (1532010, 'PIRATE', 80),
    (1532011, 'PIRATE', 90),
    (1532012, 'PIRATE', 100),
    (1532014, 'PIRATE', 110),
    (1532128, 'PIRATE', 120),
    (1532129, 'PIRATE', 130),
    (1532130, 'PIRATE', 140),
    (1542000, 'WARRIOR', 10),
    (1542001, 'WARRIOR', 20),
    (1542002, 'WARRIOR', 30),
    (1542003, 'WARRIOR', 40),
    (1542004, 'WARRIOR', 50),
    (1542006, 'WARRIOR', 60),
    (1542007, 'WARRIOR', 80),
    (1542008, 'WARRIOR', 90),
    (1542009, 'WARRIOR', 100),
    (1542010, 'WARRIOR', 110),
    (1542011, 'WARRIOR', 100),
    (1542099, 'WARRIOR', 120),
    (1542100, 'WARRIOR', 130),
    (1542101, 'WARRIOR', 140),
    (1552000, 'MAGICIAN', 10),
    (1552001, 'MAGICIAN', 20),
    (1552002, 'MAGICIAN', 30),
    (1552003, 'MAGICIAN', 40),
    (1552004, 'MAGICIAN', 50),
    (1552005, 'MAGICIAN', 60),
    (1552006, 'MAGICIAN', 70),
    (1552007, 'MAGICIAN', 80),
    (1552008, 'MAGICIAN', 90),
    (1552009, 'MAGICIAN', 100),
    (1552010, 'MAGICIAN', 110),
    (1552011, 'MAGICIAN', 100),
    (1552100, 'MAGICIAN', 120),
    (1552101, 'MAGICIAN', 130),
    (1552102, 'MAGICIAN', 140),
    (1582000, 'WARRIOR', 10),
    (1582001, 'WARRIOR', 30),
    (1582002, 'WARRIOR', 50),
    (1582003, 'WARRIOR', 60),
    (1582004, 'WARRIOR', 70),
    (1582005, 'WARRIOR', 80),
    (1582006, 'WARRIOR', 90),
    (1582007, 'WARRIOR', 100),
    (1582008, 'WARRIOR', 110),
    (1582009, 'WARRIOR', 120),
    (1582010, 'WARRIOR', 130),
    (1582011, 'WARRIOR', 140),
    (1592000, 'BOWMAN', 20),
    (1592001, 'BOWMAN', 30),
    (1592002, 'BOWMAN', 50),
    (1592003, 'BOWMAN', 60),
    (1592004, 'BOWMAN', 70),
    (1592005, 'BOWMAN', 80),
    (1592006, 'BOWMAN', 90),
    (1592007, 'BOWMAN', 100),
    (1592009, 'BOWMAN', 110),
    (1592012, 'BOWMAN', 120),
    (1592014, 'BOWMAN', 130),
    (1592016, 'BOWMAN', 140);

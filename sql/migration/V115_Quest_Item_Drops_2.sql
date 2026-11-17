-- Quest items that nothing gave (no drop, shop, quest or script), so their quests couldn't be finished.
-- Monsters come from official data: String.wz MonsterBook.img (the monster's listed rewards), the quest's own
-- text in Quest.wz (QuestInfo.img, #o<monster># tags or the monster named in words), or the item being named
-- after the monster; only monsters placed in the quest's area count for the last two. Quest items only show up
-- for characters that have the quest. Each insert is skipped if the drop already exists.
-- Keep comments on their own lines: the migration runner only ends a statement at a line ending in ';'.
-- Water Thief Monster | Dapper Drip (1711 The Shimmering Bristles of Victory) [dname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810102, 4033202, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810102 AND itemid = 4033202);
-- Baby Boulder Muncher | Suaveness Comb (1715 Every Whisker in its Right Place) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810100, 4033200, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810100 AND itemid = 4033200);
-- Cart Bear | Channeling Jewel (1716 This Moustache Comes Fully Loaded) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810103, 4033203, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810103 AND itemid = 4033203);
-- White Hare | Desert Rabbit Fur (1723 Weaving for the Queen) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810200, 4033212, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810200 AND itemid = 4033212);
-- Desert Giant | Palm Tree Sap (1726 Audience with the Queen) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810202, 4033214, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810202 AND itemid = 4033214);
-- Harp | Warm Feather (1732 Hatchenheit) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810300, 4033204, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810300 AND itemid = 4033204);
-- Red Dragon Turtle | Delicious Grass (1735 Top Hats and Tigers 2) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810305, 4033207, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810305 AND itemid = 4033207);
-- Red Dragon Turtle | Dragon Meat (1735 Top Hats and Tigers 2) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810305, 4033208, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810305 AND itemid = 4033208);
-- Jr. Yeti | Jr. Yeti Meat (1742 Frosty Meal) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810402, 4033216, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810402 AND itemid = 4033216);
-- Werewolf | Werewolf Tail (1745 The Worst Rope Ever) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810403, 4033217, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810403 AND itemid = 4033217);
-- System Monster G6 | Accelerator (1815 [System Revamp] Accelerator) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9306101, 4033457, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9306101 AND itemid = 4033457);
-- System Monster N2 | Memory Chip (1821 [Training Course] Disguise) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9306001, 4033451, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9306001 AND itemid = 4033451);
-- System Monster F1 | Power Supply (1836 [Selectable Program] Energy Distribution) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9306007, 4033458, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9306007 AND itemid = 4033458);
-- Slime | Red Ball of Yarn (2104 Fanzy's Red Furball) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 210100, 4031273, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 210100 AND itemid = 4031273);
-- Poison Poopa | Soul Light (2512 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230201, 4032863, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230201 AND itemid = 4032863);
-- Cube Slime | Soul Light (2513 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110300, 4032864, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110300 AND itemid = 4032864);
-- Blue Flower Serpent | Soul Light (2513 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230503, 4032864, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230503 AND itemid = 4032864);
-- Reinforced Iron Mutae | Soul Light (2514 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4110301, 4032865, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4110301 AND itemid = 4032865);
-- Grizzly | Soul Light (2514 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120500, 4032865, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120500 AND itemid = 4032865);
-- Sand Dwarf | Soul Light (2515 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3100101, 4032866, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3100101 AND itemid = 4032866);
-- Panda | Soul Light (2515 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130203, 4032866, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130203 AND itemid = 4032866);
-- [*]Buffy | Soul Light (2516 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130200, 4032867, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130200 AND itemid = 4032867);
-- Peach Monkey | Soul Light (2516 Power of the Spirit of Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130207, 4032867, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130207 AND itemid = 4032867);
-- Poison Poopa | Burning Flare (2517 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230201, 4032868, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230201 AND itemid = 4032868);
-- Cube Slime | Burning Flare (2518 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110300, 4032869, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110300 AND itemid = 4032869);
-- Blue Flower Serpent | Burning Flare (2518 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230503, 4032869, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230503 AND itemid = 4032869);
-- Reinforced Iron Mutae | Burning Flare (2519 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4110301, 4032870, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4110301 AND itemid = 4032870);
-- Grizzly | Burning Flare (2519 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120500, 4032870, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120500 AND itemid = 4032870);
-- Sand Dwarf | Burning Flare (2520 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3100101, 4032871, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3100101 AND itemid = 4032871);
-- Panda | Burning Flare (2520 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130203, 4032871, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130203 AND itemid = 4032871);
-- [*]Buffy | Burning Flare (2521 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130200, 4032872, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130200 AND itemid = 4032872);
-- Peach Monkey | Burning Flare (2521 Power of the Spirit of Fire) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130207, 4032872, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130207 AND itemid = 4032872);
-- Poison Poopa | Unstoppable Wind (2522 Power of the Spirit of Wind) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230201, 4032873, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230201 AND itemid = 4032873);
-- Reinforced Iron Mutae | Unstoppable Wind (2524 Power of the Spirit of Wind) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4110301, 4032875, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4110301 AND itemid = 4032875);
-- Grizzly | Unstoppable Wind (2524 Power of the Spirit of Wind) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120500, 4032875, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120500 AND itemid = 4032875);
-- Sand Dwarf | Unstoppable Wind (2525 Power of the Spirit of Wind) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3100101, 4032876, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3100101 AND itemid = 4032876);
-- Panda | Unstoppable Wind (2525 Power of the Spirit of Wind) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130203, 4032876, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130203 AND itemid = 4032876);
-- [*]Buffy | Unstoppable Wind (2526 Power of the Spirit of Wind) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130200, 4032877, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130200 AND itemid = 4032877);
-- Peach Monkey | Unstoppable Wind (2526 Power of the Spirit of Wind) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130207, 4032877, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130207 AND itemid = 4032877);
-- Poison Poopa | Nameless Darkness (2527 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230201, 4032878, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230201 AND itemid = 4032878);
-- Cube Slime | Nameless Darkness (2528 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110300, 4032879, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110300 AND itemid = 4032879);
-- Blue Flower Serpent | Nameless Darkness (2528 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230503, 4032879, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230503 AND itemid = 4032879);
-- Reinforced Iron Mutae | Nameless Darkness (2529 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4110301, 4032880, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4110301 AND itemid = 4032880);
-- Grizzly | Nameless Darkness (2529 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120500, 4032880, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120500 AND itemid = 4032880);
-- Sand Dwarf | Nameless Darkness (2530 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3100101, 4032881, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3100101 AND itemid = 4032881);
-- Panda | Nameless Darkness (2530 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130203, 4032881, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130203 AND itemid = 4032881);
-- [*]Buffy | Nameless Darkness (2531 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130200, 4032882, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130200 AND itemid = 4032882);
-- Peach Monkey | Nameless Darkness (2531 Power of the Spirit of Darkness) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130207, 4032882, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130207 AND itemid = 4032882);
-- Poison Poopa | Earth-Cleaving Lightning (2532 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230201, 4032883, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230201 AND itemid = 4032883);
-- Cube Slime | Earth-Cleaving Lightning (2533 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110300, 4032884, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110300 AND itemid = 4032884);
-- Blue Flower Serpent | Earth-Cleaving Lightning (2533 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230503, 4032884, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230503 AND itemid = 4032884);
-- Reinforced Iron Mutae | Earth-Cleaving Lightning (2534 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4110301, 4032885, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4110301 AND itemid = 4032885);
-- Grizzly | Earth-Cleaving Lightning (2534 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120500, 4032885, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120500 AND itemid = 4032885);
-- Sand Dwarf | Earth-Cleaving Lightning (2535 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3100101, 4032886, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3100101 AND itemid = 4032886);
-- Panda | Earth-Cleaving Lightning (2535 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130203, 4032886, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130203 AND itemid = 4032886);
-- [*]Buffy | Earth-Cleaving Lightning (2536 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130200, 4032887, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130200 AND itemid = 4032887);
-- Peach Monkey | Earth-Cleaving Lightning (2536 Power of the Spirit of Lightning) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130207, 4032887, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130207 AND itemid = 4032887);
-- Jr. Necki | Thin Jr. Necki Skin (2616 Helping JM with His Problem) [dname+iname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2130103, 4034076, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2130103 AND itemid = 4034076);
-- Jr. Necki | Thin Jr. Necki Skin (2616 Helping JM with His Problem) [dname+iname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300000, 4034076, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300000 AND itemid = 4034076);
-- Ghost Stump | Lost in Transit Box (2619 Stealing the Goods) [oname+tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1140100, 4033181, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4033181);
-- Smirking Ghost Stump | Lost in Transit Box (2619 Stealing the Goods) [tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1140130, 4033181, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140130 AND itemid = 4033181);
-- Ligator | Secret Document (2620 The Hut in the Swamp) [tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110100, 4033182, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110100 AND itemid = 4033182);
-- Dark Axe Stump | Axe (2806 Help the Excavation Site) [mbook+oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2130100, 4000215, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2130100 AND itemid = 4000215);
-- Stump | Dry Tree Branch (2814 Dehydrated Tree Branches) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 130100, 4033059, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 130100 AND itemid = 4033059);
-- Ghost Stump | Dry Tree Branch (2814 Dehydrated Tree Branches) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1140100, 4033059, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4033059);
-- Muddy Swamp Monster | First-Aid Box (2883 Nella's Request) [oname+tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230114, 4033045, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230114 AND itemid = 4033045);
-- Jr. Sentinel | Jr. Sentinel Shellpiece (3038 Collecting Huckle's Magic Ingredients) [dname+iname+mbook+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5200000, 4000083, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5200000 AND itemid = 4000083);
-- Rex | Essence of the Hoblin King (3125 Evidence of the Seal) [mbook]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300281, 4000584, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300281 AND itemid = 4000584);
-- Grey Vulture | Key to the Rose Garden (3174 [Lion King's Castle] Key to the Rose Garden) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8210004, 4032836, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8210004 AND itemid = 4032836);
-- Plateon | Luggage Ticket (3480 [Omega Sector] May I See Your Boarding Pass?) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230128, 4036070, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230128 AND itemid = 4036070);
-- White Robosaucer | Signal Jammer (3493 [Omega Sector] Hurt Feelings (2)) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230137, 4036071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230137 AND itemid = 4036071);
-- Black Robosaucer | Signal Jammer (3493 [Omega Sector] Hurt Feelings (2)) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230138, 4036071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230138 AND itemid = 4036071);
-- Sr. Bellflower Root | 100-Year-Old Bellflower (3820 Making Mastery Medicine) [mbook]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120502, 4000294, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120502 AND itemid = 4000294);
-- Captain | Hook (3828 An Evidence of Slaying the Captain) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 7130104, 4031436, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7130104 AND itemid = 4031436);
-- Lord Pirate's Enraged Captain | Hook (3828 An Evidence of Slaying the Captain) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300116, 4031436, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300116 AND itemid = 4031436);
-- Lord Pirate's Devoted Captain | Hook (3828 An Evidence of Slaying the Captain) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300118, 4031436, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300118 AND itemid = 4031436);
-- Lord Pirate's Furious Captain | Hook (3828 An Evidence of Slaying the Captain) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300122, 4031436, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300122 AND itemid = 4031436);
-- Paper Lantern Ghost | Worn Paper Lantern (4306 The Lanterns at Mushroom Shrine) [mbook+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400011, 4000222, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400011 AND itemid = 4000222);
-- Paper Lantern Ghost | Worn Paper Lantern (4306 The Lanterns at Mushroom Shrine) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400021, 4000222, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400021 AND itemid = 4000222);
-- Water Goblin | Cucumber (4308 Cucumber Shuffle) [mbook]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400012, 4000223, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400012 AND itemid = 4000223);
-- Kappa | Cucumber (4308 Cucumber Shuffle) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400020, 4000223, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400020 AND itemid = 4000223);
-- False Daimyo | Daimyo's Stolen Armrest (5649 A Vigilant Eye) [mbook]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400408, 4000345, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400408 AND itemid = 4000345);
-- Gigatoad | Daimyo's Stolen Armrest (5649 A Vigilant Eye) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400409, 4000345, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400409 AND itemid = 4000345);
-- Gigatoad | Gigatoad's Coin Wallet (5649 A Vigilant Eye) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400409, 4000346, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400409 AND itemid = 4000346);
-- Blue Boogie | Duck Egg (8152 Breakfast of Champions) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400006, 4000188, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400006 AND itemid = 4000188);
-- Black Crow | Duck Egg (8152 Breakfast of Champions) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400024, 4000188, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400024 AND itemid = 4000188);
-- Sakura Chunin | Chunin Poison Dart (8166 A Little Mischief) [mbook+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400401, 4000338, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400401 AND itemid = 4000338);
-- Kaede Kunoichi | Kunoichi Dagger (8168 Cooperation) [mbook+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400406, 4000344, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400406 AND itemid = 4000344);
-- Kaede Ninto | Ninto Scroll (8169 Collecting Info) [mbook+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400403, 4000340, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400403 AND itemid = 4000340);
-- Kacchuu Musha | Ninto Scroll (8169 Collecting Info) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400405, 4000340, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400405 AND itemid = 4000340);
-- Kaede Ninto | Kacchuu Musha Fragment (8169 Collecting Info) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400403, 4000342, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400403 AND itemid = 4000342);
-- Kacchuu Musha | Kacchuu Musha Fragment (8169 Collecting Info) [dname+iname+mbook+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400405, 4000342, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400405 AND itemid = 4000342);
-- False Daimyo | Gigatoad's Coin Wallet (8170 Defeat the Great Offender) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9400408, 4000346, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9400408 AND itemid = 4000346);
-- Stump | Stump Eraser (9414 An Immovable Sword) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 130100, 4001038, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 130100 AND itemid = 4001038);
-- Slime | Slime Eraser (9414 An Immovable Sword) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 210100, 4001042, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 210100 AND itemid = 4001042);
-- Grosso Polpo | Unknown Cube Fragment (17021 [Commerci Trade] Cubist Mercantilism) [oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390857, 4033991, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390857 AND itemid = 4033991);
-- Nightcutter Assassin | Confidential Document (17708 [Commerci Republic] Letters from an Assassin) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390818, 4033996, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390818 AND itemid = 4033996);
-- Nightwhip Assassin | Confidential Document (17708 [Commerci Republic] Letters from an Assassin) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390819, 4033996, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390819 AND itemid = 4033996);
-- Nightpriest Assassin | Confidential Document (17708 [Commerci Republic] Letters from an Assassin) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390820, 4033996, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390820 AND itemid = 4033996);
-- Jr. Boogie | Informant Badge (20780 Ugh, 10 Boogies!) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230300, 4033220, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230300 AND itemid = 4033220);
-- Jr. Boogie | Winston's Album (20782 Temperamental Old Man) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230300, 4033221, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230300 AND itemid = 4033221);
-- Dark Stump | Chrome's Old Diary (20791 The True Culprit?) [oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1110101, 4033223, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1110101 AND itemid = 4033223);
-- Dark Stump | Diary Page (20792 Chromile's Diary - Part 1) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1110101, 4033224, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1110101 AND itemid = 4033224);
-- Dark Stump | Diary Page (20792 Chromile's Diary - Part 1) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9833457, 4033224, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9833457 AND itemid = 4033224);
-- Axe Stump | Diary Page (20793 Chromile's Diary - Part 2) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1130100, 4033225, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1130100 AND itemid = 4033225);
-- Axe Stump | Diary Page (20793 Chromile's Diary - Part 2) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9833458, 4033225, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9833458 AND itemid = 4033225);
-- Dark Axe Stump | Diary Page (20794 Chromile's Diary - Part 3) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2130100, 4033226, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2130100 AND itemid = 4033226);
-- Dark Axe Stump | Diary Page (20794 Chromile's Diary - Part 3) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5100101, 4033226, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5100101 AND itemid = 4033226);
-- Dark Axe Stump | Diary Page (20794 Chromile's Diary - Part 3) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9833459, 4033226, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9833459 AND itemid = 4033226);
-- Ghost Stump | Empress Aria's Keepsake (20800 A Chance to Redeem Yourself) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1140100, 4033228, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4033228);
-- Berserk Horny Mushroom | Berzerk Horny Mushroom Puppet (20902 The Puppeteer Returns) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300736, 4033717, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300736 AND itemid = 4033717);
-- Weirdo Horny Mushroom | Weirdo Horny Mushroom Puppet (20904 Pia's Testimony) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300737, 4033718, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300737 AND itemid = 4033718);
-- Skelegon | Life Water for Wolf (21614 A Wolf's Heart) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8190003, 4032334, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8190003 AND itemid = 4032334);
-- Orange Mushroom | Berserk Horny Mushroom Puppet (21710 Raging Horny Mushrooms) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1210102, 4034063, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210102 AND itemid = 4034063);
-- Horny Mushroom | Berserk Horny Mushroom Puppet (21710 Raging Horny Mushrooms) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2110200, 4034063, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2110200 AND itemid = 4034063);
-- Berserk Horny Mushroom | Berserk Horny Mushroom Puppet (21710 Raging Horny Mushrooms) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300736, 4034063, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300736 AND itemid = 4034063);
-- Crying Blue Mushroom | Crying Blue Mushroom Doll (21711 When Mushrooms Cry) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2220110, 4034064, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2220110 AND itemid = 4034064);
-- Stone Golem | Golem Doll (21714 Golems Amok) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130101, 4034065, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130101 AND itemid = 4034065);
-- Smirking Ghost Stump | Smirking Ghost Stump Puppet (21723 Find the Puppet) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1140130, 4032319, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140130 AND itemid = 4032319);
-- Giant Nependeath | Rapid Growth Stimulator (21737 The Giant Nependeaths Appear) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300347, 4032324, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300347 AND itemid = 4032324);
-- Stump | Thick Branch (22004 Fixing the Fence) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 130100, 4032498, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 130100 AND itemid = 4032498);
-- Pig | Thick Branch (22004 Fixing the Fence) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1210100, 4032498, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210100 AND itemid = 4032498);
-- Pig | Pork (22503 A Bite of Pork) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1210100, 4032453, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210100 AND itemid = 4032453);
-- Strange Pig | Letter from Gustav (22510 Letter Delivery) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1210111, 4032455, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210111 AND itemid = 4032455);
-- Stone Golem | Golem Doll (22524 Strange Puppet) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130101, 4034073, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130101 AND itemid = 4034073);
-- Dark Stone Golem | Golem Doll (22524 Strange Puppet) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130102, 4034073, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130102 AND itemid = 4034073);
-- Mixed Golem | Golem Doll (22524 Strange Puppet) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150000, 4034073, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150000 AND itemid = 4034073);
-- Icy Mixed Golem | Golem Doll (22524 Strange Puppet) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150002, 4034073, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150002 AND itemid = 4034073);
-- Flaming Mixed Golem | Golem Doll (22524 Strange Puppet) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150003, 4034073, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150003 AND itemid = 4034073);
-- Stump | Refreshing Stump Sap (22529 Helping Beginner Adventurer Christopher) [dname+iname+oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 130100, 4032460, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 130100 AND itemid = 4032460);
-- Ghost Stump | Smirking Ghost Stump Puppet (22532 Strange Ghost Stumps) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1140100, 4034066, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140100 AND itemid = 4034066);
-- Smirking Ghost Stump | Smirking Ghost Stump Puppet (22532 Strange Ghost Stumps) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1140130, 4034066, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1140130 AND itemid = 4034066);
-- Surgeon Eye | Surgeon Eye Cell Sample (22544 Where's the Book? 4) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230113, 4034069, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230113 AND itemid = 4034069);
-- Cold Eye | Surgeon Eye Cell Sample (22544 Where's the Book? 4) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230100, 4034069, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230100 AND itemid = 4034069);
-- Blue Mushroom | Blue Mushroom Doll (22555 Chief Stan's Test) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2220100, 4034067, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2220100 AND itemid = 4034067);
-- Enraged Golem | Golem Doll (22559 Eliminate the Golems) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300387, 4032466, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300387 AND itemid = 4032466);
-- Streetlight | Streetlight Tendril (23123 A Gift for the Test Subject 1) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2150002, 4032746, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2150002 AND itemid = 4032746);
-- King Block Golem | MR Receiver (23287 Conspiracy Theory) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230110, 4033651, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230110 AND itemid = 4033651);
-- Stone Golem | Delivery Box (23645 Lunchtime 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130101, 4033656, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130101 AND itemid = 4033656);
-- Dark Stone Golem | Delivery Box (23645 Lunchtime 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130102, 4033656, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130102 AND itemid = 4033656);
-- Mixed Golem | Delivery Box (23645 Lunchtime 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150000, 4033656, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150000 AND itemid = 4033656);
-- Icy Mixed Golem | Delivery Box (23645 Lunchtime 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150002, 4033656, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150002 AND itemid = 4033656);
-- Flaming Mixed Golem | Delivery Box (23645 Lunchtime 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150003, 4033656, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150003 AND itemid = 4033656);
-- Onyx Stonegar | Onyx Stonegar Piece (23659 An Engineer's Dream) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400013, 4000711, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400013 AND itemid = 4000711);
-- Gravi Stonegar | Onyx Stonegar Piece (23659 An Engineer's Dream) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400014, 4000711, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400014 AND itemid = 4000711);
-- Onyx Stonegar | Gravi Stonegar Guard (23659 An Engineer's Dream) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400013, 4000712, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400013 AND itemid = 4000712);
-- Gravi Stonegar | Gravi Stonegar Guard (23659 An Engineer's Dream) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400014, 4000712, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400014 AND itemid = 4000712);
-- Garbage Pile | Delivery Delay Notice (23661 An Alchemist's Desire 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300640, 4033643, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300640 AND itemid = 4033643);
-- Messy Book Pile | Book Pile (23681 A Gift Most Manly 1) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300644, 4033644, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300644 AND itemid = 4033644);
-- Guard Robot E | Mechanical Circuit (23693 Data Mining 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300645, 4033664, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300645 AND itemid = 4033664);
-- Streetlight | First-Aid Kit (23924 Please Find the Missing Item) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2150002, 4032756, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2150002 AND itemid = 4032756);
-- Guard Robot L | Guard Robot L's Chip (23953 Gabrielle's Request) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 7150004, 4032768, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7150004 AND itemid = 4032768);
-- Security System | Baby Ore Muncher (23971 Escape Plans) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8105001, 4032775, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8105001 AND itemid = 4032775);
-- Slime | Evil Eye Cell Sample (24053 Betty's Request 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 210100, 4032959, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 210100 AND itemid = 4032959);
-- Evil Eye | Evil Eye Cell Sample (24053 Betty's Request 1) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230100, 4032959, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230100 AND itemid = 4032959);
-- Evil Eye | Curse Eye Cell Sample (24054 Betty's Request 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230100, 4032960, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230100 AND itemid = 4032960);
-- Curse Eye | Curse Eye Cell Sample (24054 Betty's Request 2) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230100, 4032960, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230100 AND itemid = 4032960);
-- Evil Eye | Plant Cell Sample (24055 Betty's Request 3) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230100, 4032961, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230100 AND itemid = 4032961);
-- Curse Eye | Plant Cell Sample (24055 Betty's Request 3) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230100, 4032961, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230100 AND itemid = 4032961);
-- Dark Stump | Monster Picture Book (24058 Winston's Request 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1110101, 4032963, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1110101 AND itemid = 4032963);
-- Stone Golem | Delivery Box (24060 Bruce's Request 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130101, 4032964, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130101 AND itemid = 4032964);
-- Dark Stone Golem | Delivery Box (24060 Bruce's Request 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130102, 4032964, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130102 AND itemid = 4032964);
-- Mixed Golem | Delivery Box (24060 Bruce's Request 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150000, 4032964, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150000 AND itemid = 4032964);
-- Icy Mixed Golem | Delivery Box (24060 Bruce's Request 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150002, 4032964, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150002 AND itemid = 4032964);
-- Flaming Mixed Golem | Delivery Box (24060 Bruce's Request 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150003, 4032964, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150003 AND itemid = 4032964);
-- Stone Golem | Dejected Green Mushroom Puppet (24061 Bruce's Request 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130101, 4034071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130101 AND itemid = 4034071);
-- Dark Stone Golem | Dejected Green Mushroom Puppet (24061 Bruce's Request 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130102, 4034071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130102 AND itemid = 4034071);
-- Mixed Golem | Dejected Green Mushroom Puppet (24061 Bruce's Request 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150000, 4034071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150000 AND itemid = 4034071);
-- Icy Mixed Golem | Dejected Green Mushroom Puppet (24061 Bruce's Request 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150002, 4034071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150002 AND itemid = 4034071);
-- Flaming Mixed Golem | Dejected Green Mushroom Puppet (24061 Bruce's Request 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150003, 4034071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150003 AND itemid = 4034071);
-- Wild Boar | Tongue Twister Scroll (24081 Wild Goose Chase) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230102, 4032968, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230102 AND itemid = 4032968);
-- Royal Fairy | Lolo's Mop (25538 Cleaned Out) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3000007, 4033323, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3000007 AND itemid = 4033323);
-- Evil Eye | Polishing Sap (25541 Lolo's Sap) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2230100, 4033325, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2230100 AND itemid = 4033325);
-- Curse Eye | Anne's Teddy Bear (25544 The Lone Girl) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230100, 4033326, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230100 AND itemid = 4033326);
-- Cold Eye | Herb Seed (25545 Garden of Vengeance) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230100, 4033327, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230100 AND itemid = 4033327);
-- Tauromacis | Tauromacis Blood (25634 The Sleeping Temple) [dname+iname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 7130100, 4034072, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7130100 AND itemid = 4034072);
-- Limestone Tokka | Shine Shine Stone (25727 Consoling Tear 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400003, 4033342, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400003 AND itemid = 4033342);
-- Alert Grobbler | Alert Grobbler Flower (25728 Consoling Tear 2) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400001, 4033341, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400001 AND itemid = 4033341);
-- Laloong | Laloong Tail (25729 Competing for Resources) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400005, 4000709, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400005 AND itemid = 4000709);
-- Alert Grobbler | Alert Grobbler Stalk (25731 Assisting Kylan) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400001, 4000705, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400001 AND itemid = 4000705);
-- Grumpy Grobbler | Grumpy Grobbler Stalk (25738 Anor's Request) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400002, 4000706, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400002 AND itemid = 4000706);
-- Nefarious Monk Apprentice | West Sanctum Relic (25746 Reclaiming the Relic) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400008, 4033350, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400008 AND itemid = 4033350);
-- Nefarious Monk Master | West Sanctum Relic (25746 Reclaiming the Relic) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400009, 4033350, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400009 AND itemid = 4033350);
-- Onyx Stonegar | Onyx Stonegar Magic Pebble (25764 Seal Breaking) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400013, 4033346, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400013 AND itemid = 4033346);
-- Gravi Stonegar | Gravi Stonegar Magic Pebble (25765 A Guide to Portal Operation) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400014, 4033345, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400014 AND itemid = 4033345);
-- Wild Kargo | Evil Essence (25774 Straight to the Source) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6230100, 4033353, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6230100 AND itemid = 4033353);
-- Tauromacis | Evil Essence (25774 Straight to the Source) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 7130100, 4033353, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7130100 AND itemid = 4033353);
-- Taurospear | Evil Essence (25774 Straight to the Source) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 7130101, 4033353, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7130101 AND itemid = 4033353);
-- Jr. Balrog | Evil Essence (25774 Straight to the Source) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8130100, 4033353, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8130100 AND itemid = 4033353);
-- Star Pixie | Pixie Cloud Piece (25776 Orbis Cloud Piece) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230200, 4033354, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230200 AND itemid = 4033354);
-- Lunar Pixie | Pixie Cloud Piece (25776 Orbis Cloud Piece) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230106, 4033354, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230106 AND itemid = 4033354);
-- Luster Pixie | Pixie Cloud Piece (25776 Orbis Cloud Piece) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120000, 4033354, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120000 AND itemid = 4033354);
-- Scarf Plead | Desert Scarf (25783 Desert Scarf) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100107, 4033380, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100107 AND itemid = 4033380);
-- Limestone Tokka | King Shine Shine Stone (25842 Pebble Collector 1) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400003, 4033429, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400003 AND itemid = 4033429);
-- Alert Grobbler | Alert Grobbler Dewdrop (25846 Voice Soother) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400001, 4033431, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400001 AND itemid = 4033431);
-- Laloong | Laloong Horn (25847 Assisting Kylan) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400005, 4033344, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400005 AND itemid = 4033344);
-- Amethyst Tokka | Glow Glow Stone (25848 Fenelle's Catalyst) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400004, 4033343, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400004 AND itemid = 4033343);
-- Amethyst Tokka | King Glow Glow Stone (25849 Pebble Collector 2) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400004, 4033432, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400004 AND itemid = 4033432);
-- Kaloong | Kaloong Wing (25855 Anor's Request) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400006, 4000710, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400006 AND itemid = 4000710);
-- Heretic Monk Initiate | Lean Food Pouch (25877 Get Our Grub Back 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400010, 4033348, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400010 AND itemid = 4033348);
-- Heretic Monk Apprentice | Lean Food Pouch (25877 Get Our Grub Back 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400011, 4033348, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400011 AND itemid = 4033348);
-- Heretic Leader | Lean Food Pouch (25877 Get Our Grub Back 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400012, 4033348, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400012 AND itemid = 4033348);
-- Drake | Basic Green Essence Stone (25882 Drake Stone) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130100, 4033436, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130100 AND itemid = 4033436);
-- Wild Kargo | Basic Purple Essence Stone (25883 Wild Kargo Stone) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6230100, 4033438, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6230100 AND itemid = 4033438);
-- Star Pixie | Basic Red Essence Stone (25884 Star Pixie Stone) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3230200, 4033433, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3230200 AND itemid = 4033433);
-- White Fang | Basic White Essence Stone (25885 White Fang Stone) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5140000, 4033440, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5140000 AND itemid = 4033440);
-- Lioner | Basic Orange Essence Stone (25886 Lioner Stone) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120002, 4033434, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120002 AND itemid = 4033434);
-- Triple Rumo | White Essence Stone (25897 Artificial Organism Stone 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110303, 4033449, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110303 AND itemid = 4033449);
-- Neo Huroid | Blue Essence Stone (25898 Artificial Organism Stone 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5110302, 4033446, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5110302 AND itemid = 4033446);
-- Chao | Black Essence Stone (25902 Chao Stone) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5250004, 4033450, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5250004 AND itemid = 4033450);
-- Ephenia | Purple Essence Stone (25903 Ephenia Stone) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5250007, 4033447, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5250007 AND itemid = 4033447);
-- Mushroom Chandelier | Mushroom Bulb (30052 [Mushroom Castle] For Her Smile 1) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300100, 4034104, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300100 AND itemid = 4034104);
-- Elite Windraider | Wind Mark (31251 [Crimsonheart] Wind Mark) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8148007, 4030020, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8148007 AND itemid = 4030020);
-- Elite Nightshadow | Night Mark (31254 [Crimsonheart] Night Marks) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8148009, 4030021, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8148009 AND itemid = 4030021);
-- Specter Battle Hound | Battle Hound Broken Horn (31405 Out With the Scout) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400106, 4000727, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4000727);
-- Specter Shieldbearer | Specter Commander Badge (31406 Push the Line) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400109, 4033361, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4033361);
-- Dinogoth | Dinogoth Shoulder Meat (31409 Finding Emergency Supplies) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400103, 4033410, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400103 AND itemid = 4033410);
-- Blue Speeyor | Blue Speeyor Fur Coat (31410 More Supplies!) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4033407, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4033407);
-- Red Speeyor | Animal Oil (31413 Arrested Development) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400101, 4033408, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4033408);
-- Dinogoth | Dinogoth Tail (31415 Selene's Request) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400103, 4000742, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400103 AND itemid = 4000742);
-- Blue Speeyor | Blue Speeyor Torn Fur Coat (31416 Christina's Request) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4000739, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4000739);
-- Dinoram | Lost Supplies (31417 Yet More Supplies) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400104, 4033468, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4033468);
-- Red Speeyor | Fine Firewood Pile (31424 Great Firewood) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400101, 4033400, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4033400);
-- Dinoram | Dinoram Tenderloin (31425 High-Quality Meat) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400104, 4033411, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4033411);
-- Dinoram | Dinoram Tenderloin (31427 Eurenth's Favorite Seasoning) [iname+tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400104, 4033469, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4033469);
-- Blue Speeyor | Blue Speeyor Fur Coat (31427 Eurenth's Favorite Seasoning) [iname+tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4033470, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4033470);
-- Dinodon | Dinodon Sharp Fang (31430 No Free Trade) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400105, 4033412, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033412);
-- Dinoram | Dinoram Claw (31432 Freeloading Trade King) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400104, 4000743, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4000743);
-- Specter Miner | Sparkling Specter Stone (31437 Seizing the Ores) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400108, 4000729, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400108 AND itemid = 4000729);
-- Blue Speeyor | Blue Speeyor Fur Coat (31438 Continuous Supply) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4033483, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4033483);
-- Dinogoth | Blue Speeyor Fur Coat (31438 Continuous Supply) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400103, 4033483, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400103 AND itemid = 4033483);
-- Blue Speeyor | Dinogoth Shoulder Meat (31438 Continuous Supply) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4033484, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4033484);
-- Dinogoth | Dinogoth Shoulder Meat (31438 Continuous Supply) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400103, 4033484, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400103 AND itemid = 4033484);
-- Dinoram | Dinoram Tenderloin (31451 Hungry Games) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400104, 4033474, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400104 AND itemid = 4033474);
-- Yellow Speeyor | Yellow Speeyor Fur Coat (31452 Eurenth the Needy) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400102, 4033475, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4033475);
-- Dinodon | Yellow Speeyor Fur Coat (31452 Eurenth the Needy) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400105, 4033475, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033475);
-- Yellow Speeyor | Dinodon Sharp Fang (31452 Eurenth the Needy) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400102, 4033476, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4033476);
-- Dinodon | Dinodon Sharp Fang (31452 Eurenth the Needy) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400105, 4033476, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4033476);
-- Red Speeyor | Red Speeyor Stalk (31457 What Treglow Needs) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400101, 4000740, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400101 AND itemid = 4000740);
-- Guerrilla Specter | Specter's Deadly Poison (31459 That Guy is Poison) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4000731, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4000731);
-- Guerrilla Specter | Morrow Tree Sap (31477 A Game of Shadows) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4033395, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033395);
-- Guerrilla Specter | Specter Candy (31478 Specter Materials) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4033360, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033360);
-- Specter Tamer | Battle Hound Broken Horn (31479 Healthy Competition) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4000727, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4000727);
-- Specter Battle Hound | Tamer's Whip (31479 Healthy Competition) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400106, 4033413, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4033413);
-- Specter Tamer | Tamer's Whip (31479 Healthy Competition) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4033413, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033413);
-- Guerrilla Specter | Battle Hound Broken Horn (31481 Sabotagonero) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4000727, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4000727);
-- Specter Battle Hound | Tamer's Whip (31481 Sabotagonero) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400106, 4033477, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4033477);
-- Specter Tamer | Tamer's Whip (31481 Sabotagonero) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4033477, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033477);
-- Guerrilla Specter | Tamer's Whip (31481 Sabotagonero) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4033477, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033477);
-- Specter Battle Hound | Specter Candy (31481 Sabotagonero) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400106, 4033478, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400106 AND itemid = 4033478);
-- Specter Tamer | Specter Candy (31481 Sabotagonero) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4033478, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033478);
-- Guerrilla Specter | Specter Candy (31481 Sabotagonero) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400110, 4033478, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400110 AND itemid = 4033478);
-- Specter Tamer | Tamer Hardhat (31488 Seal Ingredients) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4000728, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4000728);
-- Specter Miner | Tamer Hardhat (31488 Seal Ingredients) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400108, 4000728, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400108 AND itemid = 4000728);
-- Specter Engineer | Tamer Hardhat (31488 Seal Ingredients) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 4000728, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 4000728);
-- Specter Tamer | Sparkling Specter Stone (31488 Seal Ingredients) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4000729, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4000729);
-- Specter Engineer | Sparkling Specter Stone (31488 Seal Ingredients) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 4000729, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 4000729);
-- Specter Tamer | Welding Goggles (31488 Seal Ingredients) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4000733, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4000733);
-- Specter Miner | Welding Goggles (31488 Seal Ingredients) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400108, 4000733, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400108 AND itemid = 4000733);
-- Specter Engineer | Welding Goggles (31488 Seal Ingredients) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 4000733, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 4000733);
-- Power Specter | Hard Back Horn (31489 A New Enemy) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400111, 4000732, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400111 AND itemid = 4000732);
-- Magician Specter | Specter Cube (31494 Stealthy Encore) [dname+oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400114, 4000735, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400114 AND itemid = 4000735);
-- Warrior Specter | Specter's Sword Piece (31502 General's Final Test) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400113, 4000734, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400113 AND itemid = 4000734);
-- Magician Specter | Specter's Sword Piece (31502 General's Final Test) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400114, 4000734, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400114 AND itemid = 4000734);
-- Warrior Specter | Specter Cube (31502 General's Final Test) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400113, 4000735, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400113 AND itemid = 4000735);
-- Purple Reagent Rock | Broken Flask (31504 Road to Treglow) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400118, 4000737, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400118 AND itemid = 4000737);
-- Red Totem Staff | Ragged Cloth (31512 Road to Victor) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400116, 4000738, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400116 AND itemid = 4000738);
-- Red Totem Staff | Creation Essence (31515 Collecting Light) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400116, 4033364, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400116 AND itemid = 4033364);
-- Magician Specter | Magnus's Letter (31524 Specter Informant) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400114, 4033578, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400114 AND itemid = 4033578);
-- Specter Shieldbearer | Specter Wooden Shield (31610 Shields are Hard) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400109, 4000730, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4000730);
-- Specter Shieldbearer | Fine Wooden Shield (31613 Hard to Build, Easy to Destroy) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400109, 4033424, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400109 AND itemid = 4033424);
-- Dinodon | Dinodon Fang (31658 Lacking Resources) [iname+oname+tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400105, 4000744, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400105 AND itemid = 4000744);
-- Yellow Speeyor | Yellow Speeyor Torn Fur Coat (31658 Lacking Resources) [iname+oname+tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400102, 4000741, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400102 AND itemid = 4000741);
-- Specter Engineer | Specter Wooden Shield (31680 Setting the Stage) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400112, 4000730, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400112 AND itemid = 4000730);
-- Blue Speeyor | Blue Speeyor Fur Coat (31810 More Supplies!) [iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400100, 4033574, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400100 AND itemid = 4033574);
-- Corrupted Advanced Magician | Black Spellbook (32511 [Kritias] Hungry for Words) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630017, 4033939, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630017 AND itemid = 4033939);
-- Corrupted Advanced Magician | Black Spellbook (32511 [Kritias] Hungry for Words) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630037, 4033939, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630037 AND itemid = 4033939);
-- Corrupted Advanced Magician | Magic Orb (32521 [Kritias] Spell-Breaking) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630017, 4033943, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630017 AND itemid = 4033943);
-- Corrupted Advanced Magician | Magic Orb (32521 [Kritias] Spell-Breaking) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630037, 4033943, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630037 AND itemid = 4033943);
-- Permeating Solitude | Tower Fragment (32522 [Kritias] The Absent Pentacle) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630010, 4033944, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630010 AND itemid = 4033944);
-- Permeating Terror | Tower Fragment (32522 [Kritias] The Absent Pentacle) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630011, 4033944, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630011 AND itemid = 4033944);
-- Permeating Anxiety | Tower Fragment (32522 [Kritias] The Absent Pentacle) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630013, 4033944, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630013 AND itemid = 4033944);
-- Permeating Vanity | Tower Fragment (32522 [Kritias] The Absent Pentacle) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630014, 4033944, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630014 AND itemid = 4033944);
-- Permeating Solitude | Tower Fragment (32522 [Kritias] The Absent Pentacle) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630030, 4033944, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630030 AND itemid = 4033944);
-- Permeating Terror | Tower Fragment (32522 [Kritias] The Absent Pentacle) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630031, 4033944, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630031 AND itemid = 4033944);
-- Permeating Anxiety | Tower Fragment (32522 [Kritias] The Absent Pentacle) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630033, 4033944, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630033 AND itemid = 4033944);
-- Shooting Mote | Brush (32766 [FriendStory] Oz's Hunt 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034166, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034166);
-- Flying Mote | Brush (32766 [FriendStory] Oz's Hunt 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034166, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034166);
-- Rolling Mote | Brush (32766 [FriendStory] Oz's Hunt 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034166, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034166);
-- Poison Mote | Brush (32766 [FriendStory] Oz's Hunt 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034166, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034166);
-- Hallow Mote | Brush (32766 [FriendStory] Oz's Hunt 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034166, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034166);
-- Shooting Mote | The Handsome Man (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034167, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034167);
-- Flying Mote | The Handsome Man (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034167, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034167);
-- Rolling Mote | The Handsome Man (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034167, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034167);
-- Poison Mote | The Handsome Man (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034167, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034167);
-- Hallow Mote | The Handsome Man (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034167, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034167);
-- Shooting Mote | Howl of Love (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034168, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034168);
-- Flying Mote | Howl of Love (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034168, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034168);
-- Rolling Mote | Howl of Love (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034168, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034168);
-- Poison Mote | Howl of Love (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034168, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034168);
-- Hallow Mote | Howl of Love (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034168, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034168);
-- Shooting Mote | Ancient Hearts (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034169, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034169);
-- Flying Mote | Ancient Hearts (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034169, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034169);
-- Rolling Mote | Ancient Hearts (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034169, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034169);
-- Poison Mote | Ancient Hearts (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034169, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034169);
-- Hallow Mote | Ancient Hearts (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034169, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034169);
-- Shooting Mote | War of the Heart (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034170, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034170);
-- Flying Mote | War of the Heart (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034170, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034170);
-- Rolling Mote | War of the Heart (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034170, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034170);
-- Poison Mote | War of the Heart (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034170, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034170);
-- Hallow Mote | War of the Heart (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034170, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034170);
-- Shooting Mote | Love Strike (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700021, 4034171, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700021 AND itemid = 4034171);
-- Flying Mote | Love Strike (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700022, 4034171, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700022 AND itemid = 4034171);
-- Rolling Mote | Love Strike (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700023, 4034171, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700023 AND itemid = 4034171);
-- Poison Mote | Love Strike (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700024, 4034171, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700024 AND itemid = 4034171);
-- Hallow Mote | Love Strike (32770 [FriendStory] Dating For Dummies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2700025, 4034171, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2700025 AND itemid = 4034171);
-- Darkling Stump | Moonlight Mushroom Powder (32970 Mirror Worlds and Shadow Knights) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230062, 4036341, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230062 AND itemid = 4036341);
-- Darkling Blob | Peanut (32974 Peanuts, Books, and Secrets) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230065, 4036342, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230065 AND itemid = 4036342);
-- Blue Larimar | Larimar Shard (34816 A Divine Festival 4) [dname+tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400406, 4036171, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400406 AND itemid = 4036171);
-- Prickly Hedgehog | Big Nail (37104 [Detective Rave's Case Notes] Scrappy Hero) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400330, 4036490, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400330 AND itemid = 4036490);
-- Sketchy Hooligan | Mysterious Part (37109 [Detective Rave's Case Notes] Go, Watson!) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400332, 4036491, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400332 AND itemid = 4036491);
-- Nutria | Scratched Cast Iron Plate (37111 [Detective Rave's Case Notes] Order Overload) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400302, 4036492, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400302 AND itemid = 4036492);
-- Specter Stray Dog | Wiggling Tail (37115 [Detective Rave's Case Notes] Get It On) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400307, 4036493, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400307 AND itemid = 4036493);
-- Palm Tree Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401000, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401000 AND itemid = 4034061);
-- Coconut Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401001, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401001 AND itemid = 4034061);
-- Emerald Clam Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401002, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401002 AND itemid = 4034061);
-- Violet Clam Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401003, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401003 AND itemid = 4034061);
-- Seagull Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401004, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401004 AND itemid = 4034061);
-- Red Tube Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401005, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401005 AND itemid = 4034061);
-- Blue Tube Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401006, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401006 AND itemid = 4034061);
-- Shrimp Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401007, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401007 AND itemid = 4034061);
-- Flying Fish Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401008, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401008 AND itemid = 4034061);
-- Starfish Octopus Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401009, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401009 AND itemid = 4034061);
-- Seashell Octopus Slime | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401010, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401010 AND itemid = 4034061);
-- Captain Darkgoo | Glass Bottle (38039 A Letter Across Ages) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3401011, 4034061, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3401011 AND itemid = 4034061);
-- Pine Nut | Stiff Wooden Block (39511 The Way of Three Paths) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400550, 4036576, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400550 AND itemid = 4036576);
-- Sikhye Crock | Jar of Distilled Spirits (39515 The Rest of the Story) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400551, 4036577, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400551 AND itemid = 4036577);
-- Broken Sikhye Crock | Jar Fragment (39518 A Mundane Mission) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400552, 4036578, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400552 AND itemid = 4036578);
-- Rolling Gourd | Discarded Gourd (39519 Another Mundane Mission) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400554, 4036579, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400554 AND itemid = 4036579);
-- Sewer Rat | Black Feather (39521 The Missing Trainee 2) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400556, 4036584, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400556 AND itemid = 4036584);
-- Sewer Rat | Ring-handled Wooden Board (39521 The Missing Trainee 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400556, 4036588, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400556 AND itemid = 4036588);
-- Sewer Rat | Crumpled Note (39521 The Missing Trainee 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400556, 4036589, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400556 AND itemid = 4036589);
-- Chunky Striped Gourd | Gourd Vine (39526 The Man With No Face 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400555, 4036580, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400555 AND itemid = 4036580);
-- Sewer Rat | Silver Key (39529 The Temple Investigation 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400556, 4036581, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400556 AND itemid = 4036581);
-- Vicious Sewer Rat | Gold Key (39530 The Temple Investigation 4) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400557, 4036582, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400557 AND itemid = 4036582);
-- Jar of Stagnant Water | Bowl of Mineral Water (39534 This Old Man) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400553, 4036583, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400553 AND itemid = 4036583);
-- Town Square Pot | Ristonian Silver (39606 An Unfamiliar Kingdom) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400601, 4036657, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400601 AND itemid = 4036657);
-- Monstrous News Machine | Ristonian Dispatch (39607 A Very Unfamiliar Kingdom) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400602, 4036658, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400602 AND itemid = 4036658);
-- Torchlighter | Small Ember (39615 Breaking and Entering 3) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400603, 4036659, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400603 AND itemid = 4036659);
-- Blood Harp | Blood Harp Crest Feather (41007 Bone-chilling) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600009, 4000914, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600009 AND itemid = 4000914);
-- Brexton | Brexton's Egg (41013 Om  Noms for Shammos?) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600019, 4000921, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600019 AND itemid = 4000921);
-- Beetle | Beetle Horn (41014 The Miracle of Life 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600000, 4000906, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600000 AND itemid = 4000906);
-- Dual Beetle | Beetle Horn (41014 The Miracle of Life 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600001, 4000906, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600001 AND itemid = 4000906);
-- Jr. Cactus | Weird Cactus (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600102, 4000930, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600102 AND itemid = 4000930);
-- Cactus | Weird Cactus (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600103, 4000930, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600103 AND itemid = 4000930);
-- Royal Cactus | Weird Cactus (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600104, 4000930, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600104 AND itemid = 4000930);
-- Jr. Cactus | Desert Flower (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600102, 4000932, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600102 AND itemid = 4000932);
-- Cactus | Desert Flower (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600103, 4000932, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600103 AND itemid = 4000932);
-- Royal Cactus | Desert Flower (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600104, 4000932, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600104 AND itemid = 4000932);
-- Meerkat | Meerkat's Telescope (41058 Siren Sirin) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600109, 4000936, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600109 AND itemid = 4000936);
-- Green Mushroom | Green Mushroom Hat (41100 Raging Mrs. Ming Ming) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600200, 4000953, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600200 AND itemid = 4000953);
-- Orange Mushroom | Orange Mushroom Hat (41100 Raging Mrs. Ming Ming) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600202, 4000955, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600202 AND itemid = 4000955);
-- Blue Mushroom | Blue Mushroom Hat (41100 Raging Mrs. Ming Ming) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600204, 4000957, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600204 AND itemid = 4000957);
-- Zombie Mushroom | Zombie Mushroom Ticket (41110 Good Luck Charm) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600206, 4000959, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600206 AND itemid = 4000959);
-- Red Porky | Red Porky Thorn (41151 Self Sufficiency) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600307, 4009007, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600307 AND itemid = 4009007);
-- Black Porky | Black Porky Thorn (41151 Self Sufficiency) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600308, 4009008, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600308 AND itemid = 4009008);
-- Sr. Bellflower Root | Bellflower (41156 Mr. Gong's Request) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600320, 4009019, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600320 AND itemid = 4009019);
-- Patrol Robot | Patrol Robot Memory Card (41200 Broken Patrol Robot) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600400, 4009027, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600400 AND itemid = 4009027);
-- Patrol Robot | Patrol Robot S Memory Card (41200 Broken Patrol Robot) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600400, 4009031, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600400 AND itemid = 4009031);
-- Big Boulder Muncher | Ore Fragment (41204 Love Songs of the Poor) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600411, 4009038, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600411 AND itemid = 4009038);
-- Racoco | Shovel (41210 Conquerers of the Mine) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600413, 4009040, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600413 AND itemid = 4009040);
-- Cart Bear | Cart (41210 Conquerers of the Mine) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600415, 4009042, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600415 AND itemid = 4009042);
-- Raco | Safety Hat (41210 Conquerers of the Mine) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600417, 4009044, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600417 AND itemid = 4009044);
-- Big Spider | Big Spider Antenna (41211 Rue Ore's Secret) [dname+iname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600414, 4009041, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600414 AND itemid = 4009041);
-- Big Spider | Ore Muncher Sample (41212 Rue Ore's Secret 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600414, 4009045, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600414 AND itemid = 4009045);
-- Rumo | Rumo Leaf (41252 What's in this Thing?) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600502, 4009062, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600502 AND itemid = 4009062);
-- Iron Mutae | Steel Fragment (41256 Constructing Neo Huroid 1) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600505, 4009065, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600505 AND itemid = 4009065);
-- Mithril Mutae | Mithril Fragment (41256 Constructing Neo Huroid 1) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600507, 4009067, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600507 AND itemid = 4009067);
-- Homun | Clean Dew (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600510, 4009071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600510 AND itemid = 4009071);
-- Homunculus | Clean Dew (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600511, 4009071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600511 AND itemid = 4009071);
-- Homunscullo | Clean Dew (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600512, 4009071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600512 AND itemid = 4009071);
-- Saitie | Clean Dew (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600513, 4009071, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600513 AND itemid = 4009071);
-- Neo Huroid | Alchemy Stone (41262 Constructing Neo Huroid 2) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600516, 4009073, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600516 AND itemid = 4009073);
-- Neo Huroid | Tangled Wires (41265 Constructing Neo Huroid 3) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600516, 4009075, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600516 AND itemid = 4009075);
-- Neo Huroid | Plug (41265 Constructing Neo Huroid 3) [dname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600516, 4009076, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600516 AND itemid = 4009076);
-- Neo Huroid | Broken Iron Heart (41268 New Heart) [oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600516, 4009077, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600516 AND itemid = 4009077);
-- Roloduck | Youngest Duckie (41301 Reducing Production Costs 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600603, 4009081, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600603 AND itemid = 4009081);
-- Toy Trojan | Youngest Duckie (41301 Reducing Production Costs 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600604, 4009081, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600604 AND itemid = 4009081);
-- Roloduck | Plastic Toy Sword (41301 Reducing Production Costs 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600603, 4009082, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600603 AND itemid = 4009082);
-- Toy Trojan | Plastic Toy Sword (41301 Reducing Production Costs 1) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600604, 4009082, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600604 AND itemid = 4009082);
-- Master Robo | Master Robo Battery (41303 Reducing Production Costs 2) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600606, 4009084, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600606 AND itemid = 4009084);
-- Memory Monk | Memory Cloth (41352 Meaning of Memories 1) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600701, 4009109, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600701 AND itemid = 4009109);
-- Memory Monk Trainee | Memory Cone Hat (41352 Meaning of Memories 1) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600702, 4009110, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600702 AND itemid = 4009110);
-- Memory Guardian | Memory Helm (41353 Meaning of Memories 2) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600703, 4009111, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600703 AND itemid = 4009111);
-- Chief Memory Guardian | Memory Heart (41353 Meaning of Memories 2) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600704, 4009112, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600704 AND itemid = 4009112);
-- Qualm Monk Trainee | Qualm Cone Hat (41359 Meaning of Regret) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600707, 4009115, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600707 AND itemid = 4009115);
-- Qualm Guardian | Qualm Helm (41360 Meaning of Regret 2) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600708, 4009116, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600708 AND itemid = 4009116);
-- Chief Qualm Guardian | Qualm Heart (41360 Meaning of Regret 2) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600709, 4009117, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600709 AND itemid = 4009117);
-- Oblivion Monk | Oblivion Cloth (41365 Meaning of Oblivion) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600711, 4009119, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600711 AND itemid = 4009119);
-- Oblivion Monk Trainee | Oblivion Cone Hat (41365 Meaning of Oblivion) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600712, 4009120, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600712 AND itemid = 4009120);
-- Chief Oblivion Guardian | Oblivion Heart (41366 Meaning of Oblivion 2) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600714, 4009122, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600714 AND itemid = 4009122);
-- Straw Target Dummy | Dry Straw Bundle (56577 [Beasts of Fury] Banquet Preparations) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120503, 4034216, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120503 AND itemid = 4034216);
-- Straw Target Dummy | Dry Straw Bundle (56604 [Beasts of Fury] Banquet Preparations) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120503, 4034217, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120503 AND itemid = 4034217);
-- Wu Tien Eagle | Wu Tien Eagle Meat (56737 [Beasts of Fury] Needs Food Badly) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601054, 4034364, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601054 AND itemid = 4034364);
-- Wu Tien Eagle | Wu Tien Eagle Meat (56737 [Beasts of Fury] Needs Food Badly) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601055, 4034364, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601055 AND itemid = 4034364);
-- Wu Tien Eagle | Wu Tien Eagle Meat (56737 [Beasts of Fury] Needs Food Badly) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601056, 4034364, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601056 AND itemid = 4034364);
-- Wu Tien Eagle | Wu Tien Eagle Meat (56750 [Beasts of Fury] Needs Food Badly) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601054, 4034365, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601054 AND itemid = 4034365);
-- Wu Tien Eagle | Wu Tien Eagle Meat (56750 [Beasts of Fury] Needs Food Badly) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601055, 4034365, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601055 AND itemid = 4034365);
-- Wu Tien Eagle | Wu Tien Eagle Meat (56750 [Beasts of Fury] Needs Food Badly) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601056, 4034365, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601056 AND itemid = 4034365);
-- Oda Scout | Scout's Report (57408 Blind and Battered 1) [oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421511, 4033272, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421511 AND itemid = 4033272);
-- Oda Warrior | Mysterious Foxtail (57415 Aura's Origin 2) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9421514, 4033274, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9421514 AND itemid = 4033274);
-- Cute Reindeer | Oda Clan Seal (57442 Tae Soo and You) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5120505, 4033275, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5120505 AND itemid = 4033275);
-- Peach Monkey | Oda Clan Seal (57442 Tae Soo and You) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130207, 4033275, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130207 AND itemid = 4033275);
-- Sage Cat | Volatile Oda Clan Seal (57444 Mu Lung Mojo) [oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 6130209, 4033276, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 6130209 AND itemid = 4033276);
-- Oda Spirit Walker | Princess No's Orders (58916 [Hieizan Temple] The Lost Alliance 1) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9450030, 4034132, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9450030 AND itemid = 4034132);
-- Oda Spirit Walker | Red Jar (58917 [Hieizan Temple] The Lost Alliance 2) [dname+oname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9450030, 4034133, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9450030 AND itemid = 4034133);
-- Mammoth Moon Bunny | Lunar Sand (58951 Will of the Five Planets 2) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9450034, 4034137, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9450034 AND itemid = 4034137);
-- Kobold | Kobold's Wooden Club (59036 The Boy Who Cried Kobold) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390939, 4034050, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390939 AND itemid = 4034050);
-- Kobold | Kobold's Sword (59037 Bluffing Tom) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390939, 4034000, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390939 AND itemid = 4034000);
-- Kobold Shooter | Red Pouch (59045 Den Expedition 1) [tmap]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390914, 4034003, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390914 AND itemid = 4034003);
-- Kobold Zapper | Blue Pouch (59046 Den Expedition 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390933, 4034004, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390933 AND itemid = 4034004);
-- Disgusting Kobold | Spear Kobold's Claw (59081 Vicky's Standards) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390943, 4034001, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390943 AND itemid = 4034001);
-- White Tiger Swordsman | White Tiger Swordsman Demon Orb (62024 Collecting the White Tiger Swordsman Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600013, 4034726, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600013 AND itemid = 4034726);
-- Eagle Swordsman | Eagle Swordsman Demon Orb (62025 Collecting the Eagle Swordsman Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600014, 4034727, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600014 AND itemid = 4034727);
-- Male Thief | Male Thief Demon Orb (62026 Collecting the Male Thief Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600015, 4034728, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600015 AND itemid = 4034728);
-- Censer | Censer Demon Orb (62027 Collecting the Censer Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600017, 4034729, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600017 AND itemid = 4034729);
-- Mini Gold Martial Artist | Mini Gold Martial Artist Demon Orb (62028 Collecting the Mini Gold Martial Artist Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600019, 4034730, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600019 AND itemid = 4034730);
-- Golden Giant | Golden Giant Demon Orb (62029 Collecting the Golden Giant Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600021, 4034731, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600021 AND itemid = 4034731);
-- Silver Spearman | Silver Spearman Demon Orb (62030 Collecting the Silver Spearman Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600024, 4034732, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600024 AND itemid = 4034732);
-- Haunted Graystone | Haunted Graystone Demon Orb (62031 Collecting the Haunted Graystone Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601062, 4034733, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601062 AND itemid = 4034733);
-- Red Zhanshi Spirit | Red Zhanshi Spirit Demon Orb (62032 Collecting the Red Zhanshi Spirit Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601065, 4034734, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601065 AND itemid = 4034734);
-- Haunted Helmet | Haunted Helmet Demon Orb (62033 Collecting the Haunted Helmet Demon Orb) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9601066, 4034735, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9601066 AND itemid = 4034735);
-- Bicycle Ghost | Bike Tire (62117 We've Got a Flat!) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600310, 4009368, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600310 AND itemid = 4009368);
-- Deranged Duck | Duck Egg (62121 They're Getting Hungry!) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600302, 4009358, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600302 AND itemid = 4009358);
-- Nutty Sheep | Mutton (62122 A Special Meal for the Residents) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600303, 4009361, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600303 AND itemid = 4009361);
-- Loony Sheep | Mutton (62122 A Special Meal for the Residents) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600304, 4009361, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600304 AND itemid = 4009361);
-- Cuckoo Chicken | Rooster Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600301, 4034644, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600301 AND itemid = 4034644);
-- Nutty Sheep | Rooster Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600303, 4034644, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600303 AND itemid = 4034644);
-- Bonkers Cow | Rooster Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600307, 4034644, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600307 AND itemid = 4034644);
-- Cuckoo Chicken | Sheep Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600301, 4034645, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600301 AND itemid = 4034645);
-- Nutty Sheep | Sheep Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600303, 4034645, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600303 AND itemid = 4034645);
-- Bonkers Cow | Sheep Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600307, 4034645, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600307 AND itemid = 4034645);
-- Cuckoo Chicken | Cow Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600301, 4034646, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600301 AND itemid = 4034646);
-- Nutty Sheep | Cow Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600303, 4034646, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600303 AND itemid = 4034646);
-- Bonkers Cow | Cow Cell Sample (62134 Where the Strangeness Began) [otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600307, 4034646, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600307 AND itemid = 4034646);
-- Sign Ghost | Broken Sign (62176 Broken Junk Signs) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600311, 4009367, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600311 AND itemid = 4009367);
-- Stoplight Ghost | LED Light (62178 Flickering LED Lights) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600312, 4009369, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600312 AND itemid = 4009369);
-- Shopper Jiangshi | Shopping Bag (62189 Mystery Shopper) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600313, 4009370, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600313 AND itemid = 4009370);
-- Traveler Jiangshi | Broken Sunglasses (62190 Cool Shades, Bro) [dname+otag]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600314, 4009371, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600314 AND itemid = 4009371);
-- Poison Jiangshi | Jiangshi Poison (62199 Medicinal Jiangshi Poison) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600317, 4009372, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600317 AND itemid = 4009372);
-- Shrelephant | Shrelephant Meat (64030 [MONAD: The First Omen] Food Shortage) [dname+iname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402237, 4036372, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402237 AND itemid = 4036372);
-- Raging Hellfang | Raging Hellfang Meat (64037 [MONAD: The First Omen] Dinner Preparations) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402245, 4036374, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402245 AND itemid = 4036374);
-- Raging Hellfang | Raging Hellfang Meat (64044 [MONAD: The First Omen] Proving Your Worth) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402245, 4036383, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402245 AND itemid = 4036383);
-- Shrelephant | Shrelephant Meat (64045 [MONAD: The First Omen] High Quality Meat) [dname+iname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402237, 4036384, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402237 AND itemid = 4036384);
-- Raging Werebeast | Raging Werebeast Fur (64047 [MONAD: The First Omen] Endless Demands) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402242, 4036385, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402242 AND itemid = 4036385);
-- Shrelephant | Shrelephant Meat (64080 [MONAD: The First Omen] Night in the Forest) [dname+iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402237, 4036378, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402237 AND itemid = 4036378);
-- Jellyrash | Jellyrash Rope (64084 [MONAD: The First Omen] Local Villagers) [iname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402328, 2439168, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402328 AND itemid = 2439168);
-- Eyeful | Solid Piece of Wood (64107 [MONAD: The First Omen] Setting Up Barriers) [dname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402238, 4036389, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402238 AND itemid = 4036389);
-- Jellyrash | Jellyrash Rope (64133 [MONAD: The First Omen] Building a Catapult) [iname+oname]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402328, 4036379, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402328 AND itemid = 4036379);
-- Ginseng Jar | 4009018 (40305 Convincing Wu Gong 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600318, 4009018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600318 AND itemid = 4009018);
-- Petreefied Eyeful | 4036373 (64037 [MONAD: The First Omen] Dinner Preparations) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402240, 4036373, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402240 AND itemid = 4036373);
-- Moon Bunny | 4034633 (30416 [Korean Folk Town] Enough Gluttony) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5100100, 4034633, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5100100 AND itemid = 4034633);
-- Horny Mushroom | 4033197 (20774 Retrieving Documents) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2110200, 4033197, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2110200 AND itemid = 4033197);
-- Blue Ribbon Pig | 4033197 (20774 Retrieving Documents) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1210104, 4033197, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210104 AND itemid = 4033197);
-- Horny Mushroom | 4033198 (20775 More Missing Documents) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2110200, 4033198, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2110200 AND itemid = 4033198);
-- Blue Ribbon Pig | 4033198 (20775 More Missing Documents) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1210104, 4033198, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210104 AND itemid = 4033198);
-- Horny Mushroom | 4033218 (20776 Disappeared Documents 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2110200, 4033218, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2110200 AND itemid = 4033218);
-- Blue Ribbon Pig | 4033218 (20776 Disappeared Documents 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1210104, 4033218, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1210104 AND itemid = 4033218);
-- White Hare | 4032888 (2508 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100100, 4032888, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100100 AND itemid = 4032888);
-- Brown Hare | 4032888 (2508 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100101, 4032888, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100101 AND itemid = 4032888);
-- White Hare | 4032889 (2509 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100100, 4032889, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100100 AND itemid = 4032889);
-- Brown Hare | 4032889 (2509 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100101, 4032889, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100101 AND itemid = 4032889);
-- White Hare | 4032890 (2510 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100100, 4032890, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100100 AND itemid = 4032890);
-- Brown Hare | 4032890 (2510 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100101, 4032890, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100101 AND itemid = 4032890);
-- White Hare | 4032891 (2511 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100100, 4032891, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100100 AND itemid = 4032891);
-- Brown Hare | 4032891 (2511 Empress's Might) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2100101, 4032891, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2100101 AND itemid = 4032891);
-- Cutthroat Delfino | 4033993 (17702 [Commerci Republic] Hide and Seek) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390808, 4033993, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390808 AND itemid = 4033993);
-- Sureshot Delfino | 4033993 (17702 [Commerci Republic] Hide and Seek) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390809, 4033993, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390809 AND itemid = 4033993);
-- Voodoo Delfino | 4033993 (17702 [Commerci Republic] Hide and Seek) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390810, 4033993, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390810 AND itemid = 4033993);
-- Ripclaw Delfino | 4033993 (17702 [Commerci Republic] Hide and Seek) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390811, 4033993, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390811 AND itemid = 4033993);
-- Il Capo | 4033995 (17707 [Commerci Republic] Who's the Boss?) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9390866, 4033995, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9390866 AND itemid = 4033995);
-- Stone Golem | 4000962 (41107 Mrs. Ming Ming's Cleaning Tips 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600209, 4000962, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600209 AND itemid = 4000962);
-- Dark Stone Golem | 4000962 (41107 Mrs. Ming Ming's Cleaning Tips 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600210, 4000962, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600210 AND itemid = 4000962);
-- Mixed Golem | 4000962 (41107 Mrs. Ming Ming's Cleaning Tips 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600211, 4000962, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600211 AND itemid = 4000962);
-- Icy Mixed Golem | 4000962 (41107 Mrs. Ming Ming's Cleaning Tips 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600212, 4000962, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600212 AND itemid = 4000962);
-- Flaming Mixed Golem | 4000962 (41107 Mrs. Ming Ming's Cleaning Tips 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600213, 4000962, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600213 AND itemid = 4000962);
-- Burning Solitude | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630005, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630005 AND itemid = 4033938);
-- Burning Terror | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630006, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630006 AND itemid = 4033938);
-- Burning Rage | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630007, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630007 AND itemid = 4033938);
-- Burning Anxiety | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630008, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630008 AND itemid = 4033938);
-- Burning Vanity | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630009, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630009 AND itemid = 4033938);
-- Burning Solitude | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630025, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630025 AND itemid = 4033938);
-- Burning Terror | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630026, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630026 AND itemid = 4033938);
-- Burning Anger | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630027, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630027 AND itemid = 4033938);
-- Burning Anxiety | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630028, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630028 AND itemid = 4033938);
-- Burning Vanity | 4033938 (32507 [Kritias] The Request of a Princess) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8630029, 4033938, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8630029 AND itemid = 4033938);
-- Barnard Gray | 4036073 (3482 [Omega Sector] For Nolyfe) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230129, 4036073, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230129 AND itemid = 4036073);
-- Ultra Gray | 4036072 (3483 [Omega Sector] Nolyfe's Second Request) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230130, 4036072, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230130 AND itemid = 4036072);
-- Corrupted Kentaurus | 4033080 (31171 [Arkarium] Maha's Request) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8220019, 4033080, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8220019 AND itemid = 4033080);
-- Mushroom Knight Armor | 4034105 (30053 [Mushroom Castle] For Her Smile 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3300101, 4034105, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3300101 AND itemid = 4034105);
-- Ratz | 4031129 (3207 Nemi's Flavorful Ingredients) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110102, 4031129, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110102 AND itemid = 4031129);
-- Rash | 4033232 (20617 Empress's Grace) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 7130500, 4033232, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 7130500 AND itemid = 4033232);
-- Nefarious Monk Master | 4033347 (25748 Enemy Mastermind) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400009, 4033347, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400009 AND itemid = 4033347);
-- Heretic Leader | 4033465 (25860 Meet the Mastermind) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400012, 4033465, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400012 AND itemid = 4033465);
-- Heretic Monk Initiate | 4033466 (25859 Relic Reclamation Rumble) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400010, 4033466, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400010 AND itemid = 4033466);
-- Heretic Monk Apprentice | 4033466 (25859 Relic Reclamation Rumble) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400011, 4033466, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400011 AND itemid = 4033466);
-- Heretic Leader | 4033466 (25859 Relic Reclamation Rumble) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400012, 4033466, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400012 AND itemid = 4033466);
-- Raging Leatty | 4036382 (64067 [MONAD: The First Omen] Treatment Necessities) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9402257, 4036382, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9402257 AND itemid = 4036382);
-- Nefarious Priest | 4033460 (25852 Ingredients for the Recipe for Disaster) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300550, 4033460, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300550 AND itemid = 4033460);
-- Nefarious Priest | 4033460 (25852 Ingredients for the Recipe for Disaster) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300551, 4033460, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300551 AND itemid = 4033460);
-- Nefarious Priest | 4033460 (25852 Ingredients for the Recipe for Disaster) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300552, 4033460, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300552 AND itemid = 4033460);
-- Advanced Knight B | 4032925 (31141 [Knight Stronghold] Superior Implements) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8610011, 4032925, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8610011 AND itemid = 4032925);
-- Next-gen Speaker | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310002, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310002 AND itemid = 4036018);
-- Blue Raspberry Jelly Juice | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310004, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310004 AND itemid = 4036018);
-- Grape Jelly Juice | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310005, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310005 AND itemid = 4036018);
-- Enraged Espresso Machine | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310006, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310006 AND itemid = 4036018);
-- Green Notebook | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310007, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310007 AND itemid = 4036018);
-- Blue Notebook | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310008, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310008 AND itemid = 4036018);
-- Yellow Headphones | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310009, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310009 AND itemid = 4036018);
-- Red Headphones | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310010, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310010 AND itemid = 4036018);
-- Killer Cosmetics | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310011, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310011 AND itemid = 4036018);
-- Deadly Dressing Table | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310012, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310012 AND itemid = 4036018);
-- Harried Hair Roller | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310013, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310013 AND itemid = 4036018);
-- Peeved Perm Machine | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310014, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310014 AND itemid = 4036018);
-- Spirit of Rock | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310015, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310015 AND itemid = 4036018);
-- Frenzied Peri Dotts | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310016, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310016 AND itemid = 4036018);
-- [*] Enraged Espresso Machine | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310017, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310017 AND itemid = 4036018);
-- [*] Blue Notebook | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310018, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310018 AND itemid = 4036018);
-- [*] Red Headphones | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310019, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310019 AND itemid = 4036018);
-- [*] Deadly Dressing Table | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310020, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310020 AND itemid = 4036018);
-- [*] Harried Hair Roller | 4036018 (34405 [Kerning Tower] A Show of Support) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4310021, 4036018, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4310021 AND itemid = 4036018);
-- Stone Golem | 4033639 (23648 A Refreshing Beverage) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130101, 4033639, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130101 AND itemid = 4033639);
-- Dark Stone Golem | 4033639 (23648 A Refreshing Beverage) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5130102, 4033639, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5130102 AND itemid = 4033639);
-- Mixed Golem | 4033639 (23648 A Refreshing Beverage) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150000, 4033639, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150000 AND itemid = 4033639);
-- Icy Mixed Golem | 4033639 (23648 A Refreshing Beverage) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150002, 4033639, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150002 AND itemid = 4033639);
-- Flaming Mixed Golem | 4033639 (23648 A Refreshing Beverage) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5150003, 4033639, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5150003 AND itemid = 4033639);
-- Blob Glooger | 4036494 (37118 [Detective Rave's Case Notes] Tea with Miss Raven) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400336, 4036494, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400336 AND itemid = 4036494);
-- Spine Glooger | 4036495 (37119 [Detective Rave's Case Notes] Not Helping) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400337, 4036495, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400337 AND itemid = 4036495);
-- Unhinged Goat | 4009362 (62124 Fresh Goat Milk) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600305, 4009362, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600305 AND itemid = 4009362);
-- Batty Goat | 4009362 (62124 Fresh Goat Milk) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600306, 4009362, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600306 AND itemid = 4009362);
-- Shopper Jiangshi | 4009373 (62145 Jiangshi Versus Talismans 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600313, 4009373, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600313 AND itemid = 4009373);
-- Traveler Jiangshi | 4009373 (62145 Jiangshi Versus Talismans 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600314, 4009373, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600314 AND itemid = 4009373);
-- Pale Jiangshi | 4009373 (62145 Jiangshi Versus Talismans 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600315, 4009373, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600315 AND itemid = 4009373);
-- Blood Jiangshi | 4009373 (62145 Jiangshi Versus Talismans 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600316, 4009373, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600316 AND itemid = 4009373);
-- Poison Jiangshi | 4009373 (62145 Jiangshi Versus Talismans 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600317, 4009373, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600317 AND itemid = 4009373);
-- Diabolic Jiangshi | 4009373 (62145 Jiangshi Versus Talismans 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600318, 4009373, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600318 AND itemid = 4009373);
-- Specter Tamer | 4033423 (31631 [Communication] Romero's Beginner Test) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2400107, 4033423, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2400107 AND itemid = 4033423);
-- Blue Dragon Turtle | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8140700, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8140700 AND itemid = 4033118);
-- Red Dragon Turtle | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8140701, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8140701 AND itemid = 4033118);
-- Rexton | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8140702, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8140702 AND itemid = 4033118);
-- Brexton | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8140703, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8140703 AND itemid = 4033118);
-- Red Wyvern | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8150300, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8150300 AND itemid = 4033118);
-- Blue Wyvern | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8150301, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8150301 AND itemid = 4033118);
-- Dark Wyvern | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8150302, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8150302 AND itemid = 4033118);
-- [*]Dark Wyvern | 4033118 (20753 Arkarium's Barrier) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8150303, 4033118, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8150303 AND itemid = 4033118);
-- Blue Flower Serpent | 4032874 (2523 Power of the Spirit of Wind) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230503, 4032874, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230503 AND itemid = 4032874);
-- Cube Slime | 4032874 (2523 Power of the Spirit of Wind) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3110300, 4032874, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3110300 AND itemid = 4032874);
-- Raving Chicken | 4009359 (62173 Yu Garden Food Supplies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600300, 4009359, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600300 AND itemid = 4009359);
-- Cuckoo Chicken | 4009359 (62173 Yu Garden Food Supplies 2) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600301, 4009359, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600301 AND itemid = 4009359);
-- Bonkers Cow | 4009363 (62184 Prime Beef) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600307, 4009363, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600307 AND itemid = 4009363);
-- Mad Plow Ox | 4009363 (62184 Prime Beef) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600308, 4009363, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600308 AND itemid = 4009363);
-- Bonkers Cow | 4009364 (62185 Fresh Milk) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600307, 4009364, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600307 AND itemid = 4009364);
-- Mad Plow Ox | 4009364 (62185 Fresh Milk) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600308, 4009364, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600308 AND itemid = 4009364);
-- Mad Plow Ox | 4009365 (62191 Recyclable Junk) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600308, 4009365, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600308 AND itemid = 4009365);
-- Giant Centipede | 4009366 (62196 So Good, You Can't Resist) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600309, 4009366, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600309 AND itemid = 4009366);
-- Diabolic Jiangshi | 4009374 (62198 Diabolic Jiangshi Talismans) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9600318, 4009374, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9600318 AND itemid = 4009374);
-- Dust Box | 4033201 (1712 The Great Moustachiolanche) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9810101, 4033201, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9810101 AND itemid = 4033201);
-- Darkling Stump | 4036343 (32978 Forgotten Memory) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230062, 4036343, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230062 AND itemid = 4036343);
-- Darkling Axe Stump | 4036343 (32978 Forgotten Memory) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8230063, 4036343, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8230063 AND itemid = 4036343);
-- Black Wing Henchmen | 4033098 (22613 IF we really do have the same enemy.) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9300496, 4033098, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9300496 AND itemid = 4033098);
-- Rash | 4000909 (41014 The Miracle of Life 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600003, 4000909, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600003 AND itemid = 4000909);
-- Dark Rash | 4000909 (41014 The Miracle of Life 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600004, 4000909, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600004 AND itemid = 4000909);
-- Harp | 4000913 (41007 Bone-chilling) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600008, 4000913, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600008 AND itemid = 4000913);
-- Jr. Cactus | 4000931 (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600102, 4000931, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600102 AND itemid = 4000931);
-- Cactus | 4000931 (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600103, 4000931, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600103 AND itemid = 4000931);
-- Royal Cactus | 4000931 (41053 Cold Desert Nights 1) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600104, 4000931, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600104 AND itemid = 4000931);
-- Dark Sand Dwarf | 4000941 (41051 For the Queen) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600115, 4000941, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600115 AND itemid = 4000941);
-- Intruder | 4000942 (41051 For the Queen) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600116, 4000942, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600116 AND itemid = 4000942);
-- Tick | 4009086 (41305 Wake up Grandpa Clock) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600608, 4009086, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600608 AND itemid = 4009086);
-- Tick-Tock | 4009086 (41305 Wake up Grandpa Clock) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600609, 4009086, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600609 AND itemid = 4009086);
-- Chronos | 4009088 (41306 Get Well Grandpa Clock) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600610, 4009088, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600610 AND itemid = 4009088);
-- Platoon Chronos | 4009088 (41306 Get Well Grandpa Clock) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600611, 4009088, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600611 AND itemid = 4009088);
-- Master Chronos | 4009088 (41306 Get Well Grandpa Clock) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600612, 4009088, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600612 AND itemid = 4009088);
-- Homun | 4009070 (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600510, 4009070, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600510 AND itemid = 4009070);
-- Homunculus | 4009070 (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600511, 4009070, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600511 AND itemid = 4009070);
-- Homunscullo | 4009070 (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600512, 4009070, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600512 AND itemid = 4009070);
-- Saitie | 4009070 (41259 Dreaming of Alchemy) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600513, 4009070, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600513 AND itemid = 4009070);
-- Cube Slime | 4009064 (41250 Spy on the Zenumist) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600500, 4009064, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600500 AND itemid = 4009064);
-- Megran | 4009064 (41250 Spy on the Zenumist) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600501, 4009064, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600501 AND itemid = 4009064);
-- Rumo | 4009064 (41250 Spy on the Zenumist) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600502, 4009064, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600502 AND itemid = 4009064);
-- Triple Rumo | 4009064 (41250 Spy on the Zenumist) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 2600503, 4009064, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 2600503 AND itemid = 4009064);
-- Lupin | 4001040 (9412 Hughes Needs the Eraser) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 3210800, 4001040, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 3210800 AND itemid = 4001040);
-- Wraith | 4001041 (9412 Hughes Needs the Eraser) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230102, 4001041, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230102 AND itemid = 4001041);
-- Octopus | 4001043 (9412 Hughes Needs the Eraser) [review]
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 1120100, 4001043, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 1120100 AND itemid = 4001043);
-- [Stellar Detectives]: the quests say to collect these by defeating monsters around your level (Tiger Trackers: "follow
-- its trail", which has no map of its own in v232), so any monster
-- drops them (30%) for characters on the quest.
-- any monster | Litter (59708)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT -1, 4034895, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = -1 AND itemid = 4034895);
-- any monster | Mana Particles (59714)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT -1, 4034894, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = -1 AND itemid = 4034894);
-- any monster | Valuable Loot (59718)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT -1, 4034897, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = -1 AND itemid = 4034897);
-- any monster | Awesome Monster Loot (59719)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT -1, 4034898, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = -1 AND itemid = 4034898);
-- any monster | Strange Animal Fur (59723)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT -1, 4034893, 3000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = -1 AND itemid = 4034893);
-- Quest items whose only drop belonged to an old monster id that v232 no longer places: the same monster under
-- its v232 id gets the same drop (same chance).
-- Nependeath 4230105 -> 4230122 | item 4000058 (Ericsson's Reward)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230122, 4000058, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230122 AND itemid = 4000058);
-- Nependeath 4230105 -> 9833483 | item 4000058 (Ericsson's Reward)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9833483, 4000058, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9833483 AND itemid = 4000058);
-- Nependeath 4230105 -> 4230122 | item 2022039 (Elma's Nependeath Juice)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230122, 2022039, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230122 AND itemid = 2022039);
-- Nependeath 4230105 -> 9833483 | item 2022039 (Elma's Nependeath Juice)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9833483, 2022039, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9833483 AND itemid = 2022039);
-- Nependeath 4230105 -> 4230122 | item 4031200 (Elma's Nependeath Juice)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 4230122, 4031200, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 4230122 AND itemid = 4031200);
-- Nependeath 4230105 -> 9833483 | item 4031200 (Elma's Nependeath Juice)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 9833483, 4031200, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 9833483 AND itemid = 4031200);
-- Moon Bunny 4230300 -> 5100100 | item 4034629 ([Korean Folk Town] Strange Korean Folk Town)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 5100100, 4034629, 7500, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 5100100 AND itemid = 4034629);

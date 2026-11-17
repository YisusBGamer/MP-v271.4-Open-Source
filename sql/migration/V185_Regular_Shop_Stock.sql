-- @shop Regular Shop (Collector, 9001212): mid- and late-game basics. Elixir, the status cures (Antidote, Eyedrop,
-- Tonic, Holy Water, All Cure Potion), the 60% HP/MP potions (Honster, Mana Bull), Melting Cheese (4000 HP),
-- Sunset Dew (5000 MP) and higher ammo (Diamond arrows, Hwabi Throwing-Stars, Eternal Bullets). No buff or EXP items.
-- Prices are in mesos; ammo is sold in bundles of 1000. Existing rows are unchanged.
insert into shopitems (shopid, itemid, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, questexid, questexkey, questexvalue, itemperiod, levellimited, showlevmin, showlevmax, questid, sellstart, sellend, tabindex, worldblock, potentialgrade, buylimit, quantity, unitprice, maxperslot, discountperc) values
(9001212, 2000004, 2000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050000, 300, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050001, 300, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050002, 400, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050003, 400, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2050004, 800, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2002021, 3000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2002020, 3000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2020012, 3000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2020015, 4000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1, 0, 3000, 0),
(9001212, 2060004, 4000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0),
(9001212, 2061004, 4000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0),
(9001212, 2070007, 12000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0),
(9001212, 2330005, 5000, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, NULL, NULL, 0, 0, 0, 2000000000, 1000, 0, 3000, 0);

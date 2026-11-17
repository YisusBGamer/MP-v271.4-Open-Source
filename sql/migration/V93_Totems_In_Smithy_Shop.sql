-- Totems (the three totem slots) can now be bought: Smithy's Swordie Plus shop (@qm > Shops) sells the Hunter totem
-- line for mesos, weakest to strongest. Every one gives ATT, Magic ATT and all stats; up to three can be worn.
insert into shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) values
(9062231, 1202181, 0, 0, 0, 10000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 1, 0, 1),
(9062231, 1202182, 0, 0, 0, 30000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 1, 0, 1),
(9062231, 1202183, 0, 0, 0, 75000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 1, 0, 1),
(9062231, 1202184, 0, 0, 0, 150000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 1, 0, 1),
(9062231, 1202185, 0, 0, 0, 300000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 1, 0, 1),
(9062231, 1202186, 0, 0, 0, 500000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 1, 0, 1);

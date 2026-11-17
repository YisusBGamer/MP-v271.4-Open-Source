-- Christmas (Missing Santa): Roodolph sends you to Kerning City for a glass of Warm Milk for Icarus ("you should
-- be able to purchase a glass of warm milk from the store there"). Dr. Faymus at the Kerning City Pharmacy sells it;
-- the client only shows it while Warm Milk Delivery (4488) is in progress.
insert into shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) values
(1051002, 4031884, 100000, 0, 0, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 4488, '', 0, 1, 0, 1);

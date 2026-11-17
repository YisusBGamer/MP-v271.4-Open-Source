-- Kerning City, "Helping Don Giovanni with His Problem" (2617): "Don Giovanni wants you to buy a gift from the Secret
-- Garden for his son... the Toy Plane. Tip: You can get the item required from Taeha in The Secret Garden Basement"
-- (Quest.wz). Taeha (1055002) is a shopkeeper in the game data but had no stock, so the quest couldn't be finished
-- (the Kerning City chain from 2614 stopped there). She now sells the Toy Plane (4033179, "sold in Kerning Square");
-- the price is arbitrary (the game data has none). Skipped if the row exists.
insert into shopitems (shopid, itemid, buylimit, tabindex, itemperiod, price, tokenitemid, tokenprice, pointquestid, pointprice, starcoin, levellimited, showlevmin, showlevmax, questid, questexkey, questexvalue, quantity, unitprice, maxperslot) select 1055002, 4033179, 0, 0, 0, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 1, 0, 1 from dual where not exists (select 1 from shopitems where shopid = 1055002 and itemid = 4033179);

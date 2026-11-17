-- The totems in the Swordie Plus shop (Smithy, 9062231; @shop) cost Swordie Coins (4310096, the shop's
-- coin) instead of mesos: Rabbit 120 up to Hunter 260, evenly spaced. They were 10M-500M mesos (V93).
UPDATE shopitems SET price = 0, tokenitemid = 4310096, tokenprice = 120 WHERE shopid = 9062231 AND itemid = 1202181;
UPDATE shopitems SET price = 0, tokenitemid = 4310096, tokenprice = 148 WHERE shopid = 9062231 AND itemid = 1202182;
UPDATE shopitems SET price = 0, tokenitemid = 4310096, tokenprice = 176 WHERE shopid = 9062231 AND itemid = 1202183;
UPDATE shopitems SET price = 0, tokenitemid = 4310096, tokenprice = 204 WHERE shopid = 9062231 AND itemid = 1202184;
UPDATE shopitems SET price = 0, tokenitemid = 4310096, tokenprice = 232 WHERE shopid = 9062231 AND itemid = 1202185;
UPDATE shopitems SET price = 0, tokenitemid = 4310096, tokenprice = 260 WHERE shopid = 9062231 AND itemid = 1202186;

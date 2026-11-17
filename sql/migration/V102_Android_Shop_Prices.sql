-- Swordie Plus: Android Shop (Lauren, 1012123) prices x10 - mesos drop at 20x on this server, and the permanent hearts
-- were far too cheap for what they unlock.
UPDATE shopitems SET price = 50000000 WHERE shopid = 1012123 AND itemid IN (1662000, 1662001); -- Android (M)/(F)
UPDATE shopitems SET price = 20000000 WHERE shopid = 1012123 AND itemid = 1672012; -- Iron Heart (Permanent)
UPDATE shopitems SET price = 50000000 WHERE shopid = 1012123 AND itemid = 1672013; -- Adamantium Heart (Permanent)
UPDATE shopitems SET price = 100000000 WHERE shopid = 1012123 AND itemid = 1672014; -- Gold Heart (Permanent)
UPDATE shopitems SET price = 200000000 WHERE shopid = 1012123 AND itemid = 1672015; -- Garnet Heart (Permanent)
UPDATE shopitems SET price = 350000000 WHERE shopid = 1012123 AND itemid = 1672016; -- Crystal Heart (Permanent)
UPDATE shopitems SET price = 600000000 WHERE shopid = 1012123 AND itemid = 1672053; -- Lidium Heart (Permanent)
UPDATE shopitems SET price = 10000000 WHERE shopid = 1012123 AND itemid IN (1672034, 1672035, 1672036, 1672037, 1672038); -- Electronic Hearts (14 days)

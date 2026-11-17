-- The Familiar shop (Roro, 9133404) sells for mesos instead of Familiar Points (quest 65427):
-- the meso price is the Familiar Point price x 1000.
UPDATE shopitems SET price = pointprice * 1000, pointquestid = 0, pointprice = 0 WHERE shopid = 9133404 AND pointquestid = 65427;

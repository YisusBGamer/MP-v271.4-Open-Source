-- Sid's shop (11000, added in V103) listed the White Scroll (2340000), which v232's Item.wz no longer has
-- (Consume/0234.img is empty), so the client can't show it and the server can't create it when bought.
delete from shopitems where shopid = 11000 and itemid = 2340000;

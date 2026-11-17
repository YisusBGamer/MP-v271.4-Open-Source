# Midnight Chaser Exit (921171100), on entering: game over unless the run was cleared (the prize waits at Mr. Flopsy)
if sm.getQRValue(34341, "reward") != "1":
    sm.showEffect("Map/Effect2.img/event/gameover")

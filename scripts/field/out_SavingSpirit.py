# Spirit Savior Exit (921172400), on entering: game over unless the run was cleared (the reward waits at the NPC)
if sm.getQRValue(16215, "reward") != "1":
    sm.showEffect("Map/Effect2.img/event/gameover")

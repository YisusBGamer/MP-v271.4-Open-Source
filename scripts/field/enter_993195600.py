# Esfera Guardian Exit (993195600), on entering: game over unless the run was cleared (the reward waits at Shubert)
if sm.getQRValue(34598, "symbol") != "1":
    sm.showEffect("Map/Effect2.img/event/gameover")

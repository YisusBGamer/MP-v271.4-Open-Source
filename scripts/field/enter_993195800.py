# Ranheim Defense Exit (993195800), on entering: the Mysterious Researcher sees to the reward or the way back
if sm.getQRValue(39077, "clear") == "1":
    sm.setQRValue(39077, "clear", "2")
else:
    sm.showEffect("Map/Effect2.img/event/gameover")
sm.openNpc(3003490)

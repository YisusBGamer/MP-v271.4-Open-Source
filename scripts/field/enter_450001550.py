# Erda Spectrum base (450001550), on entering: a cleared run's reward waits at Nina; otherwise it's over
if sm.getQRValue(34170, "clear") != "1":
    sm.showOffFieldEffect("Map/Effect.img/killing/fail")
    sm.setSpeakerID(3003145)
    sm.sendNext("Unfortunately, you didn't complete the investigation. Better luck next time... Goodbye!")
    sm.warp(450001000)

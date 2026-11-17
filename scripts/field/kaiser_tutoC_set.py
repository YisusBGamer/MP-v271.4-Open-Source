# 940002040 Decades Later - Kaiser's tutorial, last scene: on to Pantheon
sm.lockInGameUI(True, True)
sm.sendDelay(1000)
sm.setPlayerAsSpeaker()
sm.removeEscapeButton()
sm.sendNext("(The war ended and the Black Mage was sealed away... but the Kaiser's work is never done. Pantheon "
            "calls again.)")
sm.lockInGameUI(False)
sm.warpInstanceOut(400000000, 0)

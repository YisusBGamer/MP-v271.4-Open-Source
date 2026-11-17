# 321000300 Field 1-B - Zero's tutorial: the Darklings were weak... too weak
MILO = 2410003
sm.lockInGameUI(True, False)
sm.sendDelay(800)
sm.setSpeakerID(MILO)
sm.removeEscapeButton()
sm.sendNext("That's all of them? They barely fought back. These things are nothing like what the commanders keep "
            "telling us.")
sm.sendSay("Wait - there's a signal from the Umbra Temple. The commanders are sending the whole squad to raid it!")
sm.setPlayerAsSpeaker()
sm.sendSay("(The temple... the girl sleeping there. If they reach her first, she's finished. I have to get there "
           "before them.)")
sm.lockInGameUI(False)
sm.warp(321000600)

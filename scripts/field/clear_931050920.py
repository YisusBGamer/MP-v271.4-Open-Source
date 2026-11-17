# 931050920 Classified Lab - Xenon's tutorial: the test is over; Roo-D has a warning
GELIMER = 2159376
ROO_D = 2159380
sm.lockInGameUI(True, False)
sm.sendDelay(800)
sm.setSpeakerID(GELIMER)
sm.removeEscapeButton()
sm.sendNext("Excellent results. Prepare X for memory reprogramming tonight - I want no trace of the old personality "
            "left.")
sm.setSpeakerID(ROO_D)
sm.sendSay("*beep* X! X! It's me, Roo-D. They're going to wipe your memories! I scrambled the door locks. Run while "
           "the guards are changing shifts! *beep boop*")
sm.setPlayerAsSpeaker()
sm.sendSay("(My memories are the only thing they haven't taken. I won't let them.)")
sm.lockInGameUI(False)
sm.warp(931050970)

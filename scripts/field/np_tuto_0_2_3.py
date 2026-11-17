# Jett tutorial | 552000022 Space Station Hectare 2: Jett brings the master key back to Burke
BURKE = 9270083

sm.spawnNpc(BURKE, -300, -120)
sm.lockInGameUI(True, False)
sm.setSpeakerID(BURKE)
sm.removeEscapeButton()
sm.sendNext("You got it! The ship's ready. Let's go before the royal guard boards the station.")
sm.lockInGameUI(False)
sm.removeNpc(BURKE)
sm.warp(552000020)

# 940001010 East Pantheon - Kaiser's tutorial: the wraiths are gone; Velderoth brings terrible news
VELDEROTH = 3000007
TEAR = 3000013
sm.lockInGameUI(True, False)
sm.sendDelay(800)
sm.setSpeakerID(VELDEROTH)
sm.removeEscapeButton()
sm.sendNext("There you are! A messenger just came from Heliseum. The army was ambushed... and the Kaiser has fallen.")
sm.setPlayerAsSpeaker()
sm.sendSay("No... That can't be. Nobody could stand against the Kaiser!")
sm.setSpeakerID(VELDEROTH)
sm.sendSay("Tear is already at the outskirts. Come on - if anyone survived, they'll need us.")
sm.lockInGameUI(False)
sm.warp(940001100)

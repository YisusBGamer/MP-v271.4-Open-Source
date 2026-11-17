# 940001100 Heliseum Outskirts - Kaiser's tutorial, scene 3: the Kaiser's power chooses a successor
TEAR = 3000013
VELDEROTH = 3000007
MAGNUS = 3000131
sm.lockInGameUI(True, False)
sm.spawnNpc(TEAR, -1700, 178)
sm.spawnNpc(VELDEROTH, -1900, 178)
sm.sendDelay(800)
sm.setSpeakerID(TEAR)
sm.removeEscapeButton()
sm.sendNext("The Kaiser gave everything to hold them back. Before the end, his power left him - it's searching for a "
            "new vessel.")
sm.sendSay("Can you feel it? It's coming to you.")
sm.setPlayerAsSpeaker()
sm.sendSay("(A warmth surges through my body... The power of the Kaiser?)")
sm.setSpeakerID(MAGNUS)
sm.sendSay("YOU? That power should have been mine! I am the heir of the royal line, not some soldier from the "
           "barracks!")
sm.setSpeakerID(VELDEROTH)
sm.sendSay("Magnus, this isn't the time! More of them are coming!")
sm.lockInGameUI(False)
sm.warp(940001110)

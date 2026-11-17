# 931050980 Classified Lab Silo - Xenon's tutorial, scene 4: Beryl blocks the way out, and the Resistance arrives
BERYL = 2159378
CLAUDINE = 2159382
if sm.getFieldID() == 931050980:
    sm.lockInGameUI(True, False)
    sm.spawnNpc(BERYL, -400, 43)
    sm.sendDelay(800)
    sm.setSpeakerID(BERYL)
    sm.removeEscapeButton()
    sm.sendNext("Going somewhere, X? Gelimer's newest toy, running away on its first day. How disappointing.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("I'm not a toy. Get out of my way, Beryl.")
    sm.spawnNpc(CLAUDINE, -900, 43)
    sm.setSpeakerID(CLAUDINE)
    sm.sendSay("Over here! We're the Resistance - we've been watching this lab for months. Come with us!")
    sm.setSpeakerID(BERYL)
    sm.sendSay("Tch. The Resistance. Enjoy your little freedom, X. We'll meet again.")
    sm.removeNpc(BERYL)
    sm.sendDelay(500)
    sm.lockInGameUI(False)
    sm.warp(931060070)

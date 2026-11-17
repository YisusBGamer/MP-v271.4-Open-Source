# 931060070 Secret Square - Xenon's tutorial, last scene: welcome to the Resistance
CLAUDINE = 2159384
if sm.getFieldID() == 931060070:
    sm.lockInGameUI(True, False)
    sm.sendDelay(800)
    sm.setSpeakerID(CLAUDINE)
    sm.removeEscapeButton()
    sm.sendNext("You're safe now. This is the Resistance's hideout, right under the Black Wings' noses.")
    sm.sendSay("Whatever Gelimer did to you, you're still you. Help us take Edelstein back, and we'll help you find out "
               "who you were.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Then I'm with the Resistance. Call me Xenon.")
    sm.lockInGameUI(False)
    sm.warpInstanceOut(310010000, 0)

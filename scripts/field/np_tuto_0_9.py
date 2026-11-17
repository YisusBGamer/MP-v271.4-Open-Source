# Jett tutorial | 552000050 Nautilus Bunkroom: Jett wakes aboard the Nautilus and takes Kyrin and Black Bark for the
# royal guard; Black Bark gives her the piece of the Core he found, and Kyrin, curious about her ship, takes her in as a
# pirate. The tutorial ends with her 1st job (Jett, level 10).
KYRIN = 9270089
BLACK_BARK = 9270088

if chr.getLevel() < 10:
    sm.lockInGameUI(True, False)
    sm.setPlayerAsSpeaker()
    sm.removeEscapeButton()
    sm.sendNext("Royal guard! Stay back! I didn't kill the king!")
    sm.setSpeakerID(KYRIN)
    sm.sendSay("Easy there. We're not anybody's guard. You're aboard the Nautilus. We pulled you out of that wreck.")
    sm.setSpeakerID(BLACK_BARK)
    sm.sendSay("Found this next to you. Looks like a piece broke off of something.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("(A piece of the Core! Burke didn't get all of it.)")
    sm.setSpeakerID(KYRIN)
    sm.sendSay("That ship of yours is like nothing I've ever seen. Stay with us and fight as a pirate. When you're "
               "ready, come talk to me.")
    sm.lockInGameUI(False)
    if chr.getJob() != 508:
        sm.jobAdvance(508)
    sm.levelUntil(10)
    sm.setSTR(4)
    sm.setINT(4)
    sm.setDEX(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)

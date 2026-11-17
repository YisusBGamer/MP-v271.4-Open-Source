# 993070076: the story fight is won (35844; 35806 before)
if sm.hasQuest(35844):
    sm.setQRValue(35844, "dir", "1")
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Guard Captain Darknell is down. Orchid tells you Neinheart's waiting to speak to you aboard the White "
                "Spear Ship Deck in #bEnd of the World 1-1#k.)")
    if sm.completeQuest(35844):
        sm.warpInstanceOut(450012300, 0)
elif sm.hasQuest(35806):
    sm.setQRValue(35839, "dummy", "1")
    done = sm.questEndDialogue(35806)
    if done:
        sm.warpInstanceOut(450012300, 0)

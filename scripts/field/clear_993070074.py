# 993070074: the story fight is won (35843; 35805 before)
if sm.hasQuest(35843):
    sm.setQRValue(35843, "dir", "1")
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You reached the top of the waterfall, and fought off the Black Mage's Elites.)")
    if sm.completeQuest(35843):
        sm.warpInstanceOut(450012200, 0)
elif sm.hasQuest(35805):
    sm.setQRValue(35839, "dummy", "1")
    done = sm.questEndDialogue(35805)
    if done:
        sm.warpInstanceOut(450012200, 0)

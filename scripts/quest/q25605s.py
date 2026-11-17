# Finding the Fourth Augury (Luminous) - Vieren
# Vieren says the quest's own summary. Players who already finished Riena Strait's story (quest 32192) get the
# Augury at once; otherwise done_32192.py gives it when that story ends.
VIEREN = 1032209
AUGURY = 4033332

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(VIEREN)
    sm.sendNext("The fourth Augury is located in a freezing area called #bRiena Strait#k. See if you can find out what happened to it.")
    sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.hasQuestCompleted(32192) and not sm.hasItem(AUGURY) and sm.canHold(AUGURY):
    sm.giveItem(AUGURY)
    sm.chat("You already know Riena Strait well, and the Augury wasn't hard to find.")
sm.dispose()

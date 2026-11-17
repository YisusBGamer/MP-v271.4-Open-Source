# Finding the Second Augury (Luminous) - Vieren
# Vieren says the quest's own summary. Players who already finished Gold Beach's story (quest 2975) get the Augury
# at once; otherwise done_2975.py gives it when that story ends.
VIEREN = 1032209
AUGURY = 4033329

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(VIEREN)
    sm.sendNext("The second Augury is in the #bdeepest end of Gold Beach#k.")
    sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.hasQuestCompleted(2975) and not sm.hasItem(AUGURY) and sm.canHold(AUGURY):
    sm.giveItem(AUGURY)
    sm.chat("You already know Gold Beach well, and the Augury wasn't hard to find.")
sm.dispose()

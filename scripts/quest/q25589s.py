# Finding the First Augury (Luminous) - Vieren
# Vieren says the quest's own summary. Players who already finished the Ellinel Fairy Academy's story (quest 32129)
# get the Augury at once; otherwise done_32129.py gives it when that story ends.
VIEREN = 1032209
AUGURY = 4033328

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(VIEREN)
    sm.sendNext("The first Augury is hidden in the #bEllinel Fairy Academy#k. Travel there and retrieve the Augury.")
    sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.hasQuestCompleted(32129) and not sm.hasItem(AUGURY) and sm.canHold(AUGURY):
    sm.giveItem(AUGURY)
    sm.chat("You already know the Ellinel Fairy Academy well, and the Augury wasn't hard to find.")
sm.dispose()

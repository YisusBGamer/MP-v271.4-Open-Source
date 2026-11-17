# [Fox Valley] Climb, Tree, Climb! (34756), start - Maru
# The start script is missing (no Say.img lines for the start either). Maru hands over the Grow-a-Buncha-Bunch
# Fertilizer the quest asks for; the quest's own lines say to give up the quest and accept it again for another.
# Written from the quest's own Quest.wz summaries.
MARU = 3002023
FERTILIZER = 4036320  # Grow-a-Buncha-Bunch Fertilizer

if not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(MARU)
    if not sm.canHold(FERTILIZER):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    elif sm.hasQuest(parentID) or sm.sendAskAccept('Behold! The Grow-a-Buncha-Bunch Fertilizer, twice the nuts and twice the stink! One more dose and that tree will be big enough to shelter the whole village. Use it on the #bFrombrella Tree#k!'):
        if not sm.hasQuest(parentID):
            sm.startQuest(parentID)
        if sm.hasQuest(parentID) and not sm.hasItem(FERTILIZER):
            sm.giveItem(FERTILIZER)
sm.dispose()

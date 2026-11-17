# [Fox Valley] Grow, Tree, Grow! (34754), start - Maru
# The start script is missing (no Say.img lines for the start either). Maru hands over the Grow-a-Bunch Fertilizer the
# quest asks for; the quest's own lines say to give up the quest and accept it again for another. Written from the
# quest's own Quest.wz summaries.
MARU = 3002023
FERTILIZER = 4036318  # Grow-a-Bunch Fertilizer

if not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(MARU)
    if not sm.canHold(FERTILIZER):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    elif sm.hasQuest(parentID) or sm.sendAskAccept("Ta-da! My Grow-a-Bunch Fertilizer, made from the finest Nettled Squirrel nuts! Yes, yes, it smells. That's how you know it's working. Pour it on the #bFrombrella Tree#k and watch the magic happen!"):
        if not sm.hasQuest(parentID):
            sm.startQuest(parentID)
        if sm.hasQuest(parentID) and not sm.hasItem(FERTILIZER):
            sm.giveItem(FERTILIZER)
sm.dispose()

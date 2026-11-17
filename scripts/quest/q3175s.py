# [Lion King's Castle] The Deluded King (3175) - Queen Ifia
# Ifia's official Say.img lines are played (questStartDialogue) and she hands over the Flower Book the quest asks for
# (nothing else gives it).
BOOK = 4032837  # Flower Book

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if not sm.canHold(BOOK):
        sm.setSpeakerID(2161001)
        sm.sendSayOkay("Please make some room in your Etc inventory first.")
    elif sm.questStartDialogue(parentID, 2161001) and not sm.hasItem(BOOK):
        sm.giveItem(BOOK)
sm.dispose()

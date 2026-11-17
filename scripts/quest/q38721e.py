# 38721 Discoverer of the Source - end (Hinya): the Discoverer of the Source title medal.
MEDAL = 1143369

if sm.hasQuest(parentID) and sm.isComplete(parentID):
    if not sm.canHold(MEDAL):
        sm.sendSayOkay("Make room in your Equip inventory first.")
    elif sm.completeQuest(parentID):
        sm.giveItem(MEDAL)
        sm.sendSayOkay("You obtained the <Discoverer of the Source> title.")
sm.dispose()

# 38418 Seasons Anew - end (Master Tai Yu): the Seasons Anew title medal.
MEDAL = 1143368

if sm.hasQuest(parentID) and sm.isComplete(parentID):
    if not sm.canHold(MEDAL):
        sm.sendSayOkay("Make room in your Equip inventory first.")
    elif sm.completeQuest(parentID):
        sm.giveItem(MEDAL)
        sm.sendSayOkay("You obtained the <Seasons Anew> title.")
sm.dispose()

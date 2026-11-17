# 38252 Liberator of the Guardian - end (Statue): the Liberator of the Guardian medal.
MEDAL = 1143367

if sm.hasQuest(parentID) and sm.isComplete(parentID):
    if not sm.canHold(MEDAL):
        sm.sendSayOkay("Make room in your Equip inventory first.")
    elif sm.completeQuest(parentID):
        sm.giveItem(MEDAL)
        sm.sendSayOkay("#b(You obtained the Liberator of the Guardian Medal.)#k")
sm.dispose()

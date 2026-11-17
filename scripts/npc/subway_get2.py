# Treasure Chest | Subway 1  JQ
if sm.getFieldID() == 910360102:
    if sm.sendAskYesNo("You have reached the end of this jumpquest! I will send you back now"):
        if sm.hasQuest(9998):
            sm.setQRValue(9998, "sub2", "1")
        else:
            sm.createQuestWithQRValue(9998, "sub2=1")
        # Shumi's quest item is in this chest (quest 2056 asks for it and nothing else gives it)
        if sm.hasQuest(2056) and not sm.hasItem(4031040) and sm.canHold(4031040):
            sm.giveItem(4031040)
        sm.warp(103020000)
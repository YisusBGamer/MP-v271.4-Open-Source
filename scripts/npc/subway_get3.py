# Treasure Chest | Subway 1  JQ
if sm.getFieldID() == 910360203:
    if sm.sendAskYesNo("You have reached the end of this jumpquest! I will send you back now"):
        if sm.hasQuest(9998):
            sm.setQRValue(9998, "sub3", "1")
        else:
            sm.createQuestWithQRValue(9998, "sub3=1")
        # Shumi's quest item is in this chest (quest 2057 asks for it and nothing else gives it)
        if sm.hasQuest(2057) and not sm.hasItem(4031041) and sm.canHold(4031041):
            sm.giveItem(4031041)
        sm.warp(103020000)
# Flowers | Sleepywood  JQ
# Hands out the quest item for 2054 "John's Last Present": 30 White Violas.
if sm.hasQuest(2054) and sm.getQuantityOfItem(4031028) < 30:
    if sm.sendAskYesNo("Are you sure you want to take #b30 White Violas#k with you?"):
        sm.giveItem(4031028, 30 - sm.getQuantityOfItem(4031028))
if sm.getFieldID() == 910530202:
    if sm.sendAskYesNo("You have reached the end of this jumpquest! I will send you back now"):
        if sm.hasQuest(9998):
            sm.setQRValue(9998, "sleepy", "1")
        else:
            sm.createQuestWithQRValue(9998, "sleepy=1")
        sm.warp(105000000)

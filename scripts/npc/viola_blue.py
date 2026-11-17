# Flowers | Sleepywood  JQ
# Hands out the quest item for 2053 "John's Present": 20 Blue Violas.
if sm.hasQuest(2053) and sm.getQuantityOfItem(4031026) < 20:
    if sm.sendAskYesNo("Are you sure you want to take #b20 Blue Violas#k with you?"):
        sm.giveItem(4031026, 20 - sm.getQuantityOfItem(4031026))
if sm.getFieldID() == 910530101:
    sm.warp(910530200)

#if sm.getFieldID() == 910530001:
#    if sm.sendAskYesNo("You have reached the end of this jumpquest! I will send you back now"):
#        if sm.hasQuest(9998):
#            sm.setQRValue(9998, "sleepy", "1")
#        else:
 #           sm.createQuestWithQRValue(9998, "sleepy=1")
#    sm.warp(105000000)

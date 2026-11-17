# Flowers | Sleepywood  JQ
# Hands out the quest item for 2052 "John's Pink Flower Basket": 10 Pink Violas.
if sm.hasQuest(2052) and sm.getQuantityOfItem(4031025) < 10:
    if sm.sendAskYesNo("Are you sure you want to take #b10 Pink Violas#k with you?"):
        sm.giveItem(4031025, 10 - sm.getQuantityOfItem(4031025))
if sm.getFieldID() == 910530001:
    sm.warp(910530100)

#if sm.getFieldID() == 910530001:
#    if sm.sendAskYesNo("You have reached the end of this jumpquest! I will send you back now"):
#        if sm.hasQuest(9998):
#            sm.setQRValue(9998, "sleepy", "1")
#        else:
 #           sm.createQuestWithQRValue(9998, "sleepy=1")
#    sm.warp(105000000)

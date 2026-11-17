# [Daily Quest] Arcana Area Expedition
from net.swordie.ms.enums import InvType
TREE_SPIRITS = 3003322
COUPON = 2635512

sm.setSpeakerID(TREE_SPIRITS)
sm.sendNext("#b[Daily Quest] Peace in Arcana#k You completed the quest successfully. It will help us a lot!")
if sm.sendAskYesNo("You haven't claimed today's Arcana daily quest EXP yet.\r\nDo you want to request it now?\r\n\r\n#r#n* Arcana daily quest completion EXP can only be claimed once per day per world."):
    if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
        sm.completeQuest(parentID)
        sm.giveItem(COUPON, 20)
        sm.setSpeakerID(TREE_SPIRITS)
        sm.sendSayOkay("I gave you #b#i"+str(COUPON)+"# #z"+str(COUPON)+"##k x 20 Please check your USE inventory.\r\n\r\n#r#e* You received daily quest completion EXP in the Arcana area.*#n Daily quest completion EXP in the Arcana area can only be claimed once per day per world.")
    else:
        sm.setSpeakerID(TREE_SPIRITS)
        sm.sendNext("You need at least 01 free slot in your USE inventory.")
else:
    sm.setSpeakerID(TREE_SPIRITS)
    sm.sendNext("Let me know when you're ready to claim your reward.")

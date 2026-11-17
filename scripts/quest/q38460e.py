# [Daily Quest] Lachelein Area Purification
from net.swordie.ms.enums import InvType
GRAY_MASK = 3003209
COUPON = 2635511

sm.setSpeakerID(GRAY_MASK)
sm.sendNext("#b[Daily Quest] A Night's Peace in Lachelein#k You completed the quest successfully. It will help our research a lot!")
if sm.sendAskYesNo("You haven't claimed today's Lachelein daily quest EXP yet.\r\nDo you want to request it now?\r\n\r\n#r#n* Lachelein daily quest completion EXP can only be claimed once per day per world."):
    if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
        sm.completeQuest(parentID)
        sm.giveItem(COUPON, 20)
        sm.setSpeakerID(GRAY_MASK)
        sm.sendSayOkay("I gave you #b#i"+str(COUPON)+"# #z"+str(COUPON)+"##k x 20 Please check your USE inventory.\r\n\r\n#r#e* You received daily quest completion EXP in the Lachelein area.*#n Daily quest completion EXP in the Lachelein area can only be claimed once per day per world.")
    else:
        sm.setSpeakerID(GRAY_MASK)
        sm.sendNext("You need at least 01 free slot in your USE inventory.")
else:
    sm.setSpeakerID(GRAY_MASK)
    sm.sendNext("Let me know when you're ready to claim your reward.")

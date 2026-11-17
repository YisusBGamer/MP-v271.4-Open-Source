# [Daily Quest] Esfera Research
from net.swordie.ms.enums import InvType
OLLIE = 3003530
COUPON = 2635514

sm.setSpeakerID(OLLIE)
sm.sendNext("#b[Daily Quest] Esfera Research#k You completed the quest successfully. It will help our research a lot!")
if sm.sendAskYesNo("You haven't claimed today's Esfera daily quest EXP yet.\r\nDo you want to request it now?\r\n\r\n#r#n* Esfera daily quest completion EXP can only be claimed once per day per world."):
    if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
        sm.completeQuest(parentID)
        sm.giveItem(COUPON, 20)
        sm.setSpeakerID(OLLIE)
        sm.sendSayOkay("I gave you #b#i"+str(COUPON)+"# #z"+str(COUPON)+"##k x 20 Please check your USE inventory.\r\n\r\n#r#e* You received daily quest completion EXP in the Esfera area.*#n Daily quest completion EXP in the Esfera area can only be claimed once per day per world.")
    else:
        sm.setSpeakerID(OLLIE)
        sm.sendNext("You need at least 01 free slot in your USE inventory.")
else:
    sm.setSpeakerID(OLLIE)
    sm.sendNext("Let me know when you're ready to claim your reward.")

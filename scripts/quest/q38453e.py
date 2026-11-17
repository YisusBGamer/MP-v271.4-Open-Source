# [Daily Quest] Vanishing Journey Research
from net.swordie.ms.enums import InvType
RONA = 3003104
COUPON = 2635509

sm.setSpeakerID(RONA)
sm.sendNext("#b[Daily Quest] Vanishing Journey Research#k You completed the quest successfully. It will help a lot!")
if sm.sendAskYesNo("You haven't claimed today's Vanishing Journey daily quest EXP yet.\r\nDo you want to request it now?\r\n\r\n#r#n* Vanishing Journey daily quest completion EXP can only be claimed once per day per world."):
    if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
        sm.completeQuest(parentID)
        if sm.hasQuestCompleted(37620): # [Reverse City] Surviving in the Reversed City
            sm.giveItem(COUPON, 20)
            sm.setSpeakerID(RONA)
            sm.sendSayOkay("I gave you #b#i"+str(COUPON)+"# #z"+str(COUPON)+"##k x 20 (+ 10 after completing #b[Reverse City] Surviving in the Reversed City#k) Please check your USE inventory.\r\n\r\n#r#e* You received daily quest completion EXP in the Vanishing Journey area.*#n Daily quest completion EXP in the Vanishing Journey area can only be claimed once per day per world.")
        else:
            sm.giveItem(COUPON, 10)
            sm.setSpeakerID(RONA)
            sm.sendSayOkay("I gave you #b#i"+str(COUPON)+"# #z"+str(COUPON)+"##k x 10 Please check your USE inventory.\r\n\r\n#r#e* You received daily quest completion EXP in the Vanishing Journey area.*#n Daily quest completion EXP in the Vanishing Journey area can only be claimed once per day per world.")
    else:
        sm.setSpeakerID(RONA)
        sm.sendNext("You need at least 01 free slot in your USE inventory.")
else:
    sm.setSpeakerID(RONA)
    sm.sendNext("Let me know when you're ready to claim your reward.")

# [Daily Quest] Chu Chu Island Enemy Elimination
from net.swordie.ms.enums import InvType
MASTER_LYCK = 3003152
COUPON = 2635510

sm.setSpeakerID(MASTER_LYCK)
sm.sendNext("#b[Daily Quest] Chu Chu's Finest Cuisine#k You completed the quest successfully. It will help a lot for your feast!")
if sm.sendAskYesNo("You haven't claimed today's Chu Chu daily quest EXP yet.\r\nDo you want to request it now?\r\n\r\n#r#n* Chu Chu daily quest completion EXP can only be claimed once per day per world."):
    if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
        sm.completeQuest(parentID)
        if sm.hasQuestCompleted(37726): # [Yum Yum] The Traveler and the Follower
            sm.giveItem(COUPON, 20)
            sm.setSpeakerID(MASTER_LYCK)
            sm.sendSayOkay("I gave you #b#i"+str(COUPON)+"# #z"+str(COUPON)+"##k x 20 (+ 10 after completing #b[Yum Yum] The Traveler and the Follower#k) Please check your USE inventory.\r\n\r\n#r#e* You received daily quest completion EXP in the Chu Chu area.*#n Daily quest completion EXP in the Chu Chu area can only be claimed once per day per world.")
        else:
            sm.giveItem(COUPON, 10)
            sm.setSpeakerID(MASTER_LYCK)
            sm.sendSayOkay("I gave you #b#i"+str(COUPON)+"# #z"+str(COUPON)+"##k x 10 Please check your USE inventory.\r\n\r\n#r#e* You received daily quest completion EXP in the Chu Chu area.*#n Daily quest completion EXP in the Chu Chu area can only be claimed once per day per world.")
    else:
        sm.setSpeakerID(MASTER_LYCK)
        sm.sendNext("You need at least 01 free slot in your USE inventory.")
else:
    sm.setSpeakerID(MASTER_LYCK)
    sm.sendNext("Let me know when you're ready to claim your reward.")

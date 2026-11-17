# [Daily Quest] Investigate the Tallahart Ancient God's Power
from net.swordie.ms.enums import InvType
NEINHEART = 3006843
COUPON = 2638105

sm.setSpeakerID(NEINHEART)
if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
    if sm.completeQuest(parentID):
        sm.giveItem(COUPON, 15)
        sm.sendSayOkay("Good work. Here are #b#i" + str(COUPON) + "# #z" + str(COUPON) + "# x 15#k.")
else:
    sm.sendNext("You need at least 1 free slot in your USE inventory.")

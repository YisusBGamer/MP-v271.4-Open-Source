# [Daily Quest] Shangri-La Contamination Purification
from net.swordie.ms.enums import InvType
TAI_YU = 3006163
COUPON = 2636321

sm.setSpeakerID(TAI_YU)
if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
    if sm.completeQuest(parentID):
        sm.giveItem(COUPON, 15)
        sm.sendSayOkay("Good work. Here are #b#i" + str(COUPON) + "# #z" + str(COUPON) + "# x 15#k.")
else:
    sm.sendNext("You need at least 1 free slot in your USE inventory.")

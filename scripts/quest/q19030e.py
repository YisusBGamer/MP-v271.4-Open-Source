# [Daily Quest] Odium Area Expedition
from net.swordie.ms.enums import InvType
SYASYA = 3005532
COUPON = 2636320

sm.setSpeakerID(SYASYA)
if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
    if sm.completeQuest(parentID):
        sm.giveItem(COUPON, 15)
        sm.sendSayOkay("Good work. Here are #b#i" + str(COUPON) + "# #z" + str(COUPON) + "# x 15#k.")
else:
    sm.sendNext("You need at least 1 free slot in your USE inventory.")

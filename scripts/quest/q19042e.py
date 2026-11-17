# [Daily Quest] Carcion Recovery Support
from net.swordie.ms.enums import InvType
DUHAN = 3006551
COUPON = 2636553

sm.setSpeakerID(DUHAN)
if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
    if sm.completeQuest(parentID):
        sm.giveItem(COUPON, 15)
        sm.sendSayOkay("Good work. Here are #b#i" + str(COUPON) + "# #z" + str(COUPON) + "# x 15#k.")
else:
    sm.sendNext("You need at least 1 free slot in your USE inventory.")

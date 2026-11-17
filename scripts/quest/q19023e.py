# [Daily Quest] Cernium Research
from net.swordie.ms.enums import InvType
FLAME_PRIEST = 3004540
COUPON = 2636137

sm.setSpeakerID(FLAME_PRIEST)
if sm.getEmptyInventorySlots(InvType.CONSUME) >= 1:
    if sm.completeQuest(parentID):
        sm.giveItem(COUPON, 30)
        sm.sendSayOkay("Good work. Here are #b#i" + str(COUPON) + "# #z" + str(COUPON) + "# x 30#k.")
else:
    sm.sendNext("You need at least 1 free slot in your USE inventory.")

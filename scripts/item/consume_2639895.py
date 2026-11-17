# Burning Flame Wings Coupon
WINGS = 1103893

sm.setSpeakerID(9010000)
if sm.canHold(WINGS):
    sm.consumeItem(parentID)
    sm.giveItemWithExpiry(WINGS, 90 * 24)
else:
    sm.sendNext("You don't have enough free slots in your EQUIP inventory to receive the reward.")

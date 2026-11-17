# Beyond Burning Title Coupon
TITLE = 3700807

sm.setSpeakerID(9010000)
if sm.canHold(TITLE):
    sm.consumeItem(parentID)
    sm.giveItemWithExpiry(TITLE, 90 * 24)
else:
    sm.sendNext("You don't have enough free slots in your SETUP inventory to receive the reward.")

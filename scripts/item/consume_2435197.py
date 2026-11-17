# Permanent Hyper Teleport Rock Coupon: the Hyper Teleport Rock with no expiry (never used up; also the world map rock)
from net.swordie.ms.constants import ItemConstants

ROCK = ItemConstants.HYPER_TELEPORT_ROCK
if sm.canHold(ROCK):
    sm.giveItem(ROCK)
    sm.consumeItem()
else:
    sm.sendSayOkay("Please make room in your Cash inventory.")

# The Haunted Mansion's exit: back to the town the player came from (Henesys if none).
from net.swordie.ms.world import HalloweenEvent
sm.warp(HalloweenEvent.returnTown(chr))
sm.dispose()

# Scrubby Garden (271040300), the room Cygnus sends players to: back to her chamber while the fight goes on,
# out to the garden when it is over
from net.swordie.ms.constants import BossConstants

CYGNUS_ENTRANCE = 271040000
if sm.getAmountOfMobsInField(BossConstants.CYGNUS_NORMAL_MAP) > 0:
    sm.warp(BossConstants.CYGNUS_NORMAL_MAP)
else:
    sm.warpInstanceOut(CYGNUS_ENTRANCE, 1)

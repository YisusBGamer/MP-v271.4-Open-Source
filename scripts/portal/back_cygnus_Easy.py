# Easy Scrubby Garden (271041300), the room Cygnus sends players to: back to her chamber while the fight goes on,
# out to the garden when it is over
from net.swordie.ms.constants import BossConstants

CYGNUS_ENTRANCE = 271040000
if sm.getAmountOfMobsInField(BossConstants.CYGNUS_EASY_MAP) > 0:
    sm.warp(BossConstants.CYGNUS_EASY_MAP)
else:
    sm.warpInstanceOut(CYGNUS_ENTRANCE, 1)

# Shammos (2022005) | Ice Ravine Entrance
from net.swordie.ms.world.field.instance.instancehelper.pq import HoblinKingInstanceHelper as HK
sm.setSpeakerID(2022005)
if sm.sendAskYesNo("Shall we go back to my room?"):
    sm.warp(HK.SOLITARY_ROOM, 0)

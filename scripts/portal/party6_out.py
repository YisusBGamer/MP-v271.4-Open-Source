# Forest of Poison Haze, Forest of Poison: to Ellin's Forest once the Poison Golem is defeated
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PH):
    helper = None
if helper is not None and helper.isFinished():
    sm.warp(PH.ELLINS_FOREST, 0)
else:
    chr.chatMessage("Defeat the Poison Golem first!")
sm.dispose()

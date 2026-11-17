# Forest of Poison Haze, Forest of Haze: a maze portal (where it leads is new every run)
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PH):
    helper = None
if helper is not None:
    target = helper.mazeMove(chr, parentID)
    if target >= 0:
        sm.teleportToPortal(target)
sm.dispose()

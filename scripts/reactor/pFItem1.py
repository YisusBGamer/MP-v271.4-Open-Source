# Forest of Poison Haze, Forest's Empty Lot: a box that may hold the Purple Stone of Magic
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PH):
    helper = None
if helper is not None:
    helper.openBox(chr, reactor)
sm.dispose()

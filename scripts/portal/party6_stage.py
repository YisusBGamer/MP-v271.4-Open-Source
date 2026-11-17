# Forest of Poison Haze: on to the next area (once it's purified)
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PH):
    helper = None
if helper is not None:
    helper.next(chr)
sm.dispose()

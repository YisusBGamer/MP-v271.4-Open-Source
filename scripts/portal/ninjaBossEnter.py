# Boudoir 1: the door to the Tower Room
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None
if helper is None:
    sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)  # no run going on: out the back
else:
    helper.enterTower(chr)
sm.dispose()

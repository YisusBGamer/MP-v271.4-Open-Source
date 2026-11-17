# Kenta in Danger, Dangerous Sea 1: the shipwreck Kenta is stuck under
from net.swordie.ms.world.field.instance.instancehelper.pq import KentaInstanceHelper as KH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, KH):
    helper = None
sm.removeReactor()
if helper is not None:
    helper.wreckBroken(chr.getField())
sm.dispose()

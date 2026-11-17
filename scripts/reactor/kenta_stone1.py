# Kenta in Danger, Dangerous Sea 2: a rock to seal a cave with
from net.swordie.ms.world.field.instance.instancehelper.pq import KentaInstanceHelper as KH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, KH):
    helper = None
sm.removeReactor()
if helper is not None:
    helper.rockPlaced(chr.getField())
sm.dispose()

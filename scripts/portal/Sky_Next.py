# Dragon Rider: on to the next area (once this one is cleared)
from net.swordie.ms.world.field.instance.instancehelper.pq import DragonRiderInstanceHelper as DR
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, DR):
    helper = None
if helper is not None:
    helper.next(chr)
sm.dispose()

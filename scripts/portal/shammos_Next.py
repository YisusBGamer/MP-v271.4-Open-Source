# Resurrection of the Hoblin King: on with Shammos (once the Hoblins are beaten back)
from net.swordie.ms.world.field.instance.instancehelper.pq import HoblinKingInstanceHelper as HK
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, HK):
    helper = None
if helper is not None:
    helper.next(chr)
sm.dispose()

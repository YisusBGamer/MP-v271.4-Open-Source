# The Ice Knight's Curse: on through the Frosty Pass (once the monsters are defeated)
from net.swordie.ms.world.field.instance.instancehelper.pq import IceKnightInstanceHelper as IK
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, IK):
    helper = None
if helper is not None:
    helper.next(chr)
sm.dispose()

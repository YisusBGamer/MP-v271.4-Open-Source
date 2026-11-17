# Romeo and Juliet party quest: on to the next stage (once this one is cleared)
from net.swordie.ms.world.field.instance.instancehelper.pq import RomeoJulietInstanceHelper as RJ
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, RJ):
    helper = None
if helper is not None:
    helper.next(chr)
sm.dispose()

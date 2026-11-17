# Romeo and Juliet party quest, Special Lab: a locked side door (a Card Key opens it)
from net.swordie.ms.world.field.instance.instancehelper.pq import RomeoJulietInstanceHelper as RJ
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, RJ):
    helper = None
if helper is not None and helper.openDoor(chr, 0):
    sm.removeReactor()
sm.dispose()

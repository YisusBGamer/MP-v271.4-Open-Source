# Romeo and Juliet party quest, Lab Tunnel: door 3
from net.swordie.ms.world.field.instance.instancehelper.pq import RomeoJulietInstanceHelper as RJ
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, RJ):
    helper = None
if helper is not None:
    helper.enterUnit(chr, 3)
sm.dispose()

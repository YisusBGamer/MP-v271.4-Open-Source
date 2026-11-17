# Romeo and Juliet party quest, Lab Tunnel unit: a platform (one per floor leads up)
from net.swordie.ms.world.field.instance.instancehelper.pq import RomeoJulietInstanceHelper as RJ
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, RJ):
    helper = None
if helper is not None:
    sm.teleportToPortal(helper.platform(chr, parentID))
sm.dispose()

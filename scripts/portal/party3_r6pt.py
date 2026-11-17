# Remnant of the Goddess, On the Way Up: a platform (one per floor leads up)
from net.swordie.ms.world.field.instance.instancehelper.pq import GoddessInstanceHelper as GH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, GH):
    helper = None
if helper is not None:
    sm.teleportToPortal(helper.wayUp(chr, parentID))
sm.dispose()

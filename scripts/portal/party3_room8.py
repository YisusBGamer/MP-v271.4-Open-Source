# Remnant of the Goddess, Center Tower: a door
from net.swordie.ms.world.field.instance.instancehelper.pq import GoddessInstanceHelper as GH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, GH):
    helper = None
if helper is not None:
    helper.enterRoom(chr, 8)
sm.dispose()

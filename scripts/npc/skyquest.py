# Crimson Sky Doorway (2085002) | Crimson Sky Nest
from net.swordie.ms.world.field.instance.instancehelper.pq import DragonRiderInstanceHelper as DR
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, DR):
    helper = None
if sm.sendAskYesNo("Would you like to return to the Crimson Sky Dock?"):
    sm.warpInstanceOut(DR.DOCK, 0)

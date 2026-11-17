# Ninja Castle corridors: gate1-4 lead into the chambers, gate0 (upper) on to the next corridor
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None
if helper is None:
    sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)  # no run going on: out the back
else:
    p = sm.getField().getInfo().getPortalByID(parentID)
    if p is not None and p.getName().startswith("gate"):
        helper.corridorGate(chr, int(p.getName()[4:]))
sm.dispose()

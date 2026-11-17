# Lord Pirate stage 3: a door the pirates come out of. An Old Metal Key closes it.
from net.swordie.ms.world.field.instance.instancehelper.pq import LordPirateInstanceHelper as LP
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, LP):
    helper = None
if helper is not None and helper.closeDoor(chr, objectID):
    sm.removeReactor()
sm.dispose()

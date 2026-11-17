# Escape party quest, Aerial Prison: a prison gate. A Prison Key opens it.
from net.swordie.ms.world.field.instance.instancehelper.pq import EscapeInstanceHelper as EH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, EH):
    helper = None
if helper is not None and helper.openGate(chr):
    sm.removeReactor()
sm.dispose()

# Escape party quest: a stage's way forward (opens once the stage is cleared)
from net.swordie.ms.world.field.instance.instancehelper.pq import EscapeInstanceHelper as EH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, EH):
    helper = None
if helper is not None:
    helper.next(chr)
sm.dispose()

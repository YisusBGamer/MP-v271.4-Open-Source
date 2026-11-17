# Escape party quest: the goal of the jump quest. Reaching it takes the whole party on.
from net.swordie.ms.world.field.instance.instancehelper.pq import EscapeInstanceHelper as EH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, EH):
    helper = None
if helper is not None:
    helper.jumpGoal(chr)
sm.dispose()

# Ninja Castle <Chamber of Traps>: give up and go back to the corridor
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None
if helper is None:
    sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)  # no run going on: out the back
else:
    sm.setSpeakerID(9100037)
    if helper.isChamberCleared(chr) or sm.sendAskYesNo("Give up on the traps and go back to the corridor?"):
        helper.leaveChamber(chr)
sm.dispose()

# Ninja Castle <Chamber of Battle>: the way back to the corridor
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None
if helper is None:
    sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)  # no run going on: out the back
elif helper.isChamberCleared(chr):
    helper.leaveChamber(chr)
else:
    sm.setSpeakerID(9100037)
    if sm.sendAskYesNo("Defeat every soldier here to clear this chamber.\r\nLeave the chamber anyway?"):
        helper.leaveChamber(chr)
sm.dispose()

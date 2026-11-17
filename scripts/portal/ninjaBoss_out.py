# Tower Room: out of the castle
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None

if helper is None or helper.isFinished():
    sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)
else:
    sm.setSpeakerID(9100037)
    if sm.sendAskYesNo("The Daimyo still stands. Leave the castle anyway?"):
        sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)
sm.dispose()

# Princess No's Temple: leave once Princess No is defeated
from net.swordie.ms.world.field.instance.instancehelper.pq import PrincessNoInstanceHelper as PN
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PN):
    helper = None
if helper is not None and helper.isFinished():
    sm.warpInstanceOut(PN.PLAZA, 0)
elif sm.getFieldID() == PN.FLOOR_5:
    chr.chatMessage("Talk to the Gate to face Princess No.")
else:
    chr.chatMessage("Defeat Princess No first.")
sm.dispose()

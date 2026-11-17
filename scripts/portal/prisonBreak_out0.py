# Escape party quest: leaving
from net.swordie.ms.world.field.instance.instancehelper.pq import EscapeInstanceHelper as EH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, EH):
    helper = None
if helper is not None and helper.isFinished() and sm.getFieldID() == EH.STAGE_BOSS:
    sm.warpInstanceOut(EH.ENTRANCE, 0)
elif sm.sendAskYesNo("Are you sure you want to leave? You won't be able to return."):
    sm.warpInstanceOut(EH.ENTRANCE, 0)

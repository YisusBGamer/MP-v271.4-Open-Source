# Captured Explorer (9020008) | Escape party quest
from net.swordie.ms.world.field.instance.instancehelper.pq import EscapeInstanceHelper as EH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, EH):
    helper = None
if helper is not None and helper.isFinished():
    if sm.sendAskYesNo("Thank you for freeing us! Shall we go back to Jenn?"):
        sm.warpInstanceOut(EH.ENTRANCE, 0)
elif sm.sendAskYesNo("Please, get us out of here! ...Or do you want to give up and leave?"):
    sm.warpInstanceOut(EH.ENTRANCE, 0)

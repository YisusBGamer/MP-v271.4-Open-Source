# Door (9401486) | Hieizan Temple 5th Floor (Npc.wz script out00_100): leave for the Temple Plaza
from net.swordie.ms.world.field.instance.instancehelper.pq import PrincessNoInstanceHelper as PN
sm.setSpeakerID(9401486)
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, PN) and helper.isFinished():
    question = "Princess No is defeated. Return to the Temple Plaza?"
else:
    question = "Leave the Temple? You'll have to start over."
if sm.sendAskYesNo(question):
    sm.warpInstanceOut(PN.PLAZA, 0)
sm.dispose()

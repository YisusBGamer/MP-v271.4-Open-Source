# Princess No's Temple: leave for the Temple Plaza (asks first, like the Door)
from net.swordie.ms.world.field.instance.instancehelper.pq import PrincessNoInstanceHelper as PN

inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
others = [c for c in sm.getField().getChars() if c.getId() != chr.getId()]
if isinstance(helper, PN) and helper.isFinished():
    question = "Princess No is defeated. Return to the Temple Plaza?"
elif others:
    question = "Would you like to leave the fight? Your party will continue without you."
else:
    question = "Would you like to leave the fight? The fight will be lost."
if sm.sendAskYesNo(question):
    sm.warpInstanceOut(PN.PLAZA, 0)
sm.dispose()

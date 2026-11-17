# Kenta (9020007) | Kenta in Danger
from net.swordie.ms.world.field.instance.instancehelper.pq import KentaInstanceHelper as KH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, KH):
    helper = None
sm.setSpeakerID(9020007)
if helper is None:
    sm.dispose()
elif sm.getFieldID() == KH.STAGE_1:
    sm.sendSayOkay(helper.giveBubbles(chr))
elif sm.sendAskYesNo("Thanks for coming to save me! ...Do you want to give up and leave?"):
    sm.warpInstanceOut(KH.ENTRANCE, 0)

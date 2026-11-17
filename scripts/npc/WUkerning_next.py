# Cloto (9076111) | First Time Together (Cross World)
from net.swordie.ms.world.field.instance.instancehelper.pq import FirstTimeTogetherInstanceHelper as FT
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, FT):
    helper = None
sm.setSpeakerID(parentID)
if helper is not None:
    msg = helper.talkToCloto(chr)
    if msg is not None:
        sm.sendSayOkay(msg)
sm.dispose()

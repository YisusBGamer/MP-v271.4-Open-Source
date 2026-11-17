# Sprite (2133004) | Forest of Poison Haze, Forest's Empty Lot
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PH):
    helper = None
sm.setSpeakerID(2133004)
if helper is not None:
    msg = helper.showStone(chr)
    if msg is not None:
        sm.sendSayOkay(msg)
sm.dispose()

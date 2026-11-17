# Gate (9401486) | Temple 5th Floor: on to Princess No
from net.swordie.ms.world.field.instance.instancehelper.pq import PrincessNoInstanceHelper as PN
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PN):
    helper = None
sm.setSpeakerID(9401486)
if helper is not None and sm.sendAskYesNo("Beyond this gate waits Princess No. Are you ready?"):
    sm.warp(PN.BOSS_ROOM, 0)
sm.dispose()

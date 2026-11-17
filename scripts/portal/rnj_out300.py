# Romeo and Juliet party quest: from a Lab Tunnel unit back to the tunnel
from net.swordie.ms.world.field.instance.instancehelper.pq import RomeoJulietInstanceHelper as RJ
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, RJ):
    helper = None
if helper is not None:
    sm.warp(helper.stage6() if not helper.isStageCleared(6) else helper.centerLabEntrance(), 0)
sm.dispose()

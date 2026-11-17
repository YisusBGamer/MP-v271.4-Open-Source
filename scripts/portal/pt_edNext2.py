# Nightmare Paradise: the way to the next stage, open once the stage is cleared (NightmareParadiseInstanceHelper)
from net.swordie.ms.world.field.instance.instancehelper.epic import NightmareParadiseInstanceHelper as NP
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, NP):
    helper.nextStage(chr)
elif inst is not None:
    sm.warpInstanceOut(ED.NP_ENTRANCE, 0)
else:
    sm.warp(ED.NP_ENTRANCE, 0)
sm.dispose()

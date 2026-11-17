# High Mountain: the way to the next stage, open once the stage is cleared (HighMountainInstanceHelper)
from net.swordie.ms.world.field.instance.instancehelper.epic import HighMountainInstanceHelper as HM

inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, HM):
    helper.nextStage(chr)
elif inst is not None:
    sm.warpInstanceOut(410000500, 0)
else:
    sm.warp(410000500, 0)
sm.dispose()

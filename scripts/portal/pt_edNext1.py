# Angler Company: the way to the next stage, open once the stage is cleared (AnglerCompanyInstanceHelper)
from net.swordie.ms.world.field.instance.instancehelper.epic import AnglerCompanyInstanceHelper as AC

inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, AC):
    helper.nextStage(chr)
elif inst is not None:
    sm.warpInstanceOut(410007000, 0)
else:
    sm.warp(410007000, 0)
sm.dispose()

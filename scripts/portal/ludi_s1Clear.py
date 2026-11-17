# Dimensional Crack stage 1 -> stage 2 (opens once the 20 passes are collected)
from net.swordie.ms.world.field.instance.instancehelper.pq import DimensionalCrackInstanceHelper as DC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, DC) and helper.isStageCleared(1):
    sm.warp(DC.STAGE_2, 1)
else:
    sm.chat("Collect all 20 Dimensional Passes to open the portal.")
sm.dispose()

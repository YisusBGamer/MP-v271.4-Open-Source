# Dimensional Crack stage 3 (boxes) -> stage 4 (Rombots)
from net.swordie.ms.world.field.instance.instancehelper.pq import DimensionalCrackInstanceHelper as DC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, DC) and helper.isStageCleared(3):
    sm.warp(DC.STAGE_4, 1)
else:
    sm.chat("Reach the top of the boxes first.")
sm.dispose()

# Dimensional Crack stage 2 -> stage 3 (opens once every eye in the dark rooms is defeated)
from net.swordie.ms.world.field.instance.instancehelper.pq import DimensionalCrackInstanceHelper as DC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, DC) and helper.isStageCleared(2):
    sm.warp(DC.STAGE_3, 0)
else:
    sm.chat("Defeat all the eyes in the five dark rooms to open the portal.")
sm.dispose()

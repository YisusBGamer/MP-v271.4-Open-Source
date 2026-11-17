# Kaling, phase 1: go on to another Peril once this season's Peril has fallen.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.world.field.instance.instancehelper.boss import KalingInstanceHelper

st = BossConstants.getKalingStage(sm.getFieldID())
inst = sm.getInstance()
if st is not None and inst is not None:
    if not inst.hasProperty(KalingInstanceHelper.DEAD_PERIL_KEY + "0"):
        sm.chat("Defeat this season's Peril first.")
    elif inst.hasProperty(KalingInstanceHelper.DEAD_PERIL_KEY + "1"):
        sm.chat("That Peril has already fallen.")
    else:
        sm.warp(BossConstants.KALING_FIELDS[st[0]][1], 0, True)
sm.dispose()

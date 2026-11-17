# Kaling's treasure room: Peace Blooms Again holds the rewards.
from net.swordie.ms.constants import BossConstants

st = BossConstants.getKalingStage(sm.getFieldID())
if st is not None:
    sm.spawnMob(BossConstants.KALING_BASE[st[0]] + BossConstants.KALING_CHEST, 0, 105, False)

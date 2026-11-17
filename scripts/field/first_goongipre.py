# Kaling fight (HP from maplestorywiki.net, GMS); the rest of the fight: KalingInstanceHelper.
from net.swordie.ms.constants import BossConstants

st = BossConstants.getKalingStage(sm.getFieldID())
if st is not None:
    m = st[0]
    sm.spawnMob(BossConstants.KALING_BASE[m] + 0, 0, 106, False, BossConstants.KALING_PERIL_HP[m])

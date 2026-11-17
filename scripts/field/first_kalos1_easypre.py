# Kalos the Guardian, phase 1 (HP from maplestorywiki.net, GMS). Phase 2 and the treasure room: KalosInstanceHelper.
from net.swordie.ms.constants import BossConstants

mode = BossConstants.getKalosMode(sm.getFieldID())
if mode >= 0:
    sm.spawnMob(BossConstants.KALOS_P1[mode], 0, 398, False, BossConstants.KALOS_P1_HP[mode])

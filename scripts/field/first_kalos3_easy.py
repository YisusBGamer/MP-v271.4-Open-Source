# Kalos the Guardian's treasure room: the Guardian Kalos Treasure Chest holds the rewards.
from net.swordie.ms.constants import BossConstants

mode = BossConstants.getKalosMode(sm.getFieldID())
if mode >= 0:
    sm.spawnMob(BossConstants.KALOS_CHEST[mode], 0, 399, False)

# Altar of Desire (450010500): Verus Hilla. Normal and Hard share the map; Maze3_dungeon.py marks a Hard instance.
from net.swordie.ms.constants import BossConstants

if sm.hasInstanceProperty(BossConstants.HARD_MODE_KEY):
    sm.spawnMob(BossConstants.VERUS_HILLA_HARD, 370, 266, BossConstants.VERUS_HILLA_HARD_HP)
else:
    sm.spawnMob(BossConstants.VERUS_HILLA, 370, 266, BossConstants.VERUS_HILLA_HP)

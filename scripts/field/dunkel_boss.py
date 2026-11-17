# Limina : Final Horizon
# 450012210 (Normal), 450012600 (Hard)
# Boss: Darknell
from net.swordie.ms.constants import BossConstants

# Normal and Hard share this map: the entry (dunkel_enter.py) marks a Hard instance
chaos = sm.hasInstanceProperty(BossConstants.HARD_MODE_KEY)

sm.spawnMob(BossConstants.DARKNELL_HARD if chaos else BossConstants.DARKNELL_NORMAL, -45, -157, BossConstants.DARKNELL_HARD_HP if chaos else BossConstants.DARKNELL_HP)
sm.spawnMob(BossConstants.DARKNELL_BEAM if chaos else BossConstants.DARKNELL_BEAM - 1)  # GMS Normal: 8645067
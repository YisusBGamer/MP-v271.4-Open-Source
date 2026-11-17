# Ramuramu Temple (160020000): Guardian Angel Slime (Chaos) and its field mechanics
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.world.field.instance.instancehelper.boss import GuardianAngelSlimeInstanceHelper as GAS

sm.spawnMob(BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_MOB, GAS.BOSS_X, GAS.BOSS_Y, BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_HP)
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, GAS):
    helper.startFight(sm.getField())

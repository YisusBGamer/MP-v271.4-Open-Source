# Boss Lucid, phase 2 (Crumbling Clocktower): Normal 450004250, Hard 450004550, Easy 450004850.
# Hard spawns Hard Lucid with Hard's health; a third phase follows (die_lucidP2)
from net.swordie.ms.constants import BossConstants

if BossConstants.isLucidHardField(sm.getFieldID()):
    sm.spawnMob(BossConstants.LUCID_HARD_TEMPLATE_ID_2, 600, -500, False, BossConstants.LUCID_HARD_HP_2)
elif BossConstants.isLucidEasyField(sm.getFieldID()):
    sm.spawnMob(BossConstants.LUCID_EASY_TEMPLATE_ID_2, 600, -500, False, BossConstants.LUCID_EASY_HP_2)
else:
    sm.spawnMob(BossConstants.LUCID_TEMPLATE_ID_2, 600, -500, False, BossConstants.LUCID_HP_2)

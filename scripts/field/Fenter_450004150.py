# Boss Lucid, phase 1 (Dreaming Forest): Normal 450004150, Hard 450004450, Easy 450004750.
# Hard spawns Hard Lucid (her own template and skills) with Hard's health
from net.swordie.ms.constants import BossConstants

TREE_STEM = 8880166

if BossConstants.isLucidHardField(sm.getFieldID()):
    sm.spawnMob(BossConstants.LUCID_HARD_TEMPLATE_ID_1, 1015, 48, False, BossConstants.LUCID_HARD_HP_1)
elif BossConstants.isLucidEasyField(sm.getFieldID()):
    sm.spawnMob(BossConstants.LUCID_EASY_TEMPLATE_ID_1, 1015, 48, False, BossConstants.LUCID_EASY_HP_1)
else:
    sm.spawnMob(BossConstants.LUCID_TEMPLATE_ID_1, 1015, 48, False, BossConstants.LUCID_HP_1)
sm.spawnMob(TREE_STEM, 1015, 48, False, 1)  # Tree Stem

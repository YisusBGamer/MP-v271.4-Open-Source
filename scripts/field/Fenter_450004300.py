# Boss Lucid reward room (Nightmare Clocktower): Normal 450004300, Hard 450004600, Easy 450004900.
# each difficulty gets its own Final Music Box (and its own rewards)
from net.swordie.ms.constants import BossConstants

if BossConstants.isLucidHardField(sm.getFieldID()):
    box = BossConstants.LUCID_REWARD_BOX_HARD
elif BossConstants.isLucidEasyField(sm.getFieldID()):
    box = BossConstants.LUCID_REWARD_BOX_EASY
else:
    box = BossConstants.LUCID_REWARD_BOX_NORMAL
# data HP (100,000 at 1,000 fixed damage per hit = 100 hits)
sm.spawnMob(box, -15, 36, False)

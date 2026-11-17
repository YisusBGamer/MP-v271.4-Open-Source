# Moonbridge: Eye of the Void
# 450009400 (Gloom), 450009450 (Chaos Gloom), 450009500 (the story version: [Moonbridge] Unleashing the Alliance)
# Boss: Gloom
from net.swordie.ms.constants import BossConstants

if sm.getFieldID() == 450009500:
    # [Moonbridge] Unleashing the Alliance (35630): Weakened Gloom
    if sm.hasQuest(35630):
        sm.spawnMob(8644611, -45, -157, False, 21000000000)  # maplestorywiki.net: Story (Weakened Gloom) 21b
else:
    chaos = sm.getFieldID() == BossConstants.GLOOM_HARD_FIELD
    sm.spawnMob(BossConstants.GLOOM_HARD if chaos else BossConstants.GLOOM, -45, -157, BossConstants.GLOOM_HARD_HP if chaos else BossConstants.GLOOM_HP)
    # the tentacle holder the field skills (tentacles, Terror Flame) are worked out from; GMS spawns it with Gloom
    sm.spawnMob(BossConstants.GLOOM_TENTACLE + (1 if chaos else 0), -45, -157, False, 1000000000000000000)

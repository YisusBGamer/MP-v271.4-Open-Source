# [Tallahart] The Battle to Come (36996) done: the 'Destiny's Adversary' title medal (1143382), the end of the
# Grandis story so far. Its title quest (36997) only completes while the medal is held, and nothing else gives it.
MEDAL = 1143382

if not sm.hasQuestCompleted(36997):
    if not sm.hasItem(MEDAL) and sm.canHold(MEDAL):
        sm.giveItem(MEDAL)
    sm.completeQuestNoRewards(36997)
sm.dispose()

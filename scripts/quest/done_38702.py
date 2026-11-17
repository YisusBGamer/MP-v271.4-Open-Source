# [Carcion] A Message from Across the Continent (38702) done: the 'The Eternal Tree's Guardian' title medal (1143318).
# Its title quest (38703) is what Tallahart's first quest (36971) asks for; it only completes while the medal is held,
# and nothing else gives the medal.
MEDAL = 1143318

if not sm.hasQuestCompleted(38703):
    if not sm.hasItem(MEDAL) and sm.canHold(MEDAL):
        sm.giveItem(MEDAL)
    sm.completeQuestNoRewards(38703)
sm.dispose()

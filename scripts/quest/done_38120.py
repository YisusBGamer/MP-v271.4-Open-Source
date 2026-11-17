# [Hotel Arcus] 'Til We Meet Again (38120) done: the 'Slow and Steady' title medal (1143244). Its title quest (38121)
# is what Karote's A New Mission (38201) and Odium's Contact from Edelstein (38301) ask for; it only completes while
# the medal is held, and nothing else gives the medal.
MEDAL = 1143244

if not sm.hasQuestCompleted(38121):
    if not sm.hasItem(MEDAL) and sm.canHold(MEDAL):
        sm.giveItem(MEDAL)
    sm.completeQuestNoRewards(38121)
sm.dispose()

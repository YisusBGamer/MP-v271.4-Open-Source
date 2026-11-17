# [Shangri-La] One Who Arrived in Paradise (38404) done: the 'One Who Arrived in Paradise' title medal (1143305). Its
# title quest (38405) is what Arteria's To Maple World (38631) asks for; it only completes while the medal is held,
# and nothing else gives the medal.
MEDAL = 1143305

if not sm.hasQuestCompleted(38405):
    if not sm.hasItem(MEDAL) and sm.canHold(MEDAL):
        sm.giveItem(MEDAL)
    sm.completeQuestNoRewards(38405)
sm.dispose()

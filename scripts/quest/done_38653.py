# [Arteria] The Battle's Aftermath (38653) done: the 'Promise to Meet Again' title medal (1143310). Its title quest
# (38654) is what Carcion's Without the Empress (38681) asks for; it only completes while the medal is held, and
# nothing else gives the medal.
MEDAL = 1143310

if not sm.hasQuestCompleted(38654):
    if not sm.hasItem(MEDAL) and sm.canHold(MEDAL):
        sm.giveItem(MEDAL)
    sm.completeQuestNoRewards(38654)
sm.dispose()

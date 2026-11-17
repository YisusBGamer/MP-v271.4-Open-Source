# [Labyrinth of Suffering] Source of Suffering (36772) handed in: the Eternal Light medal (1143139). Its title quest
# Eternal Light (35205) is what Limina's Toward the Black Mage (35841) asks for; nothing else in the data gives it.
MEDAL = 1143139

if not sm.hasQuestCompleted(35205):
    if not sm.hasItem(MEDAL) and sm.canHold(MEDAL):
        sm.giveItem(MEDAL)
    sm.completeQuestNoRewards(35205)
sm.dispose()

# [Odium] Hundun Laid to Rest (38319) done: the 'Hundun's Pallbearer' title medal (1143284). Its title quest (38320)
# is what Shangri-La's A Paradise Blooming with Peach Blossoms (38391) asks for; it only completes while the medal is
# held, and nothing else gives the medal.
MEDAL = 1143284

if not sm.hasQuestCompleted(38320):
    if not sm.hasItem(MEDAL) and sm.canHold(MEDAL):
        sm.giveItem(MEDAL)
    sm.completeQuestNoRewards(38320)
sm.dispose()

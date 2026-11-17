# Deep Within the Ruins (100051043): the black pool. Lighting the Way (35927) dives into the Deathly Abyss;
# once it's done the pool leads to the Soul Chamber (100051044), where Soulful Discourse (35928) starts.
if sm.hasQuest(35927):
    sm.warp(910090312)
elif sm.hasQuestCompleted(35927):
    sm.warp(100051044, 1)
else:
    sm.chat("It's pitch-dark down there. Better not dive in without some light.")
    sm.warp(100051043, 1)
sm.dispose()

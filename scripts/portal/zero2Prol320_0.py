# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 321190320 Glowcave 3, in00: Horntail's cave - the fight, Freud, or (later) the other Freud
if not sm.hasQuestCompleted(40055):
    sm.warp(321190330, 0)
elif sm.hasQuest(40061) or (sm.hasQuestCompleted(40060) and not sm.hasQuestCompleted(40061)):
    sm.warp(321190350, 0)
else:
    sm.warp(321190340, 0)
sm.dispose()

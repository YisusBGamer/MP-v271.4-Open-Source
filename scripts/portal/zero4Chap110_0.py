# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 325090110 Secret Path, in00: the power plant (Arsen's, while his part of the story goes on)
if sm.hasQuest(40403) or sm.hasQuest(40405) or (sm.hasQuestCompleted(40402) and not sm.hasQuestCompleted(40405)):
    sm.warp(325090140, 0)
else:
    sm.warp(325090120, 0)
sm.dispose()

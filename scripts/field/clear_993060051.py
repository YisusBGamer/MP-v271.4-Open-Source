# 993060051: the story fight is won (35621)
if sm.hasQuest(35621):
    done = sm.questEndDialogue(35621)
    if done:
        sm.warpInstanceOut(993060055, 0)

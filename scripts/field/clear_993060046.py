# 993060046: the story fight is won (35619)
if sm.hasQuest(35619):
    done = sm.questEndDialogue(35619)
    if done:
        sm.warpInstanceOut(993060048, 0)

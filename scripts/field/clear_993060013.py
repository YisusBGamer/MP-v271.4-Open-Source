# 993060013: the story fight is won (35606)
if sm.hasQuest(35606):
    done = sm.questEndDialogue(35606)
    if done:
        sm.warp(993060017, 0)

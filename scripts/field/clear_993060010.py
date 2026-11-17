# 993060010: the story fight is won (35604)
if sm.hasQuest(35604):
    done = sm.questEndDialogue(35604)
    if done:
        sm.warp(993060011, 0)

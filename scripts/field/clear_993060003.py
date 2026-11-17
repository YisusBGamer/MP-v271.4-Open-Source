# 993060003: the story fight is won (35602)
if sm.hasQuest(35602):
    done = sm.questEndDialogue(35602)
    if done:
        sm.warp(993060002, 0)

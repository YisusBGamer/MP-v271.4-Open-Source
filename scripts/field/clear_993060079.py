# 993060079: the story fight is won (35625)
if sm.hasQuest(35625):
    done = sm.questEndDialogue(35625)
    if done:
        sm.warpInstanceOut(993060058, 0)

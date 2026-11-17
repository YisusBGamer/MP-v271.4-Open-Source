# 993060081: the story fight is won (35631)
if sm.hasQuest(35631):
    done = sm.questEndDialogue(35631)
    if done:
        sm.warpInstanceOut(450009300, 0)

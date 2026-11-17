# 993060011: the story fight is won (35605)
if sm.hasQuest(35605):
    done = sm.questEndDialogue(35605)
    if done:
        sm.warp(993060020, 0)  # the Outpost: Maple World Right Now (35607)

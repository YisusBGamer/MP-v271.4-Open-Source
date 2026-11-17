# A Ghost's Perspective
# 2868
# Jane Doe (1052105)
# Official lines (Quest.wz Say.img); accepting takes you to where the hat was found.

lineTwo = 103020420

if sm.questStartDialogue(parentID, 1052105):
    sm.warp(lineTwo)
sm.dispose()

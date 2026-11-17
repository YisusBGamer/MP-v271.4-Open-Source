# A Ghost's Perspective
# 2868
# Jane Doe (1052133)
# Official lines (Quest.wz Say.img), then back to Naora Hospital.

naoraHospital = 103000004

if sm.questEndDialogue(parentID, 1052133):
    sm.warp(naoraHospital)
sm.dispose()

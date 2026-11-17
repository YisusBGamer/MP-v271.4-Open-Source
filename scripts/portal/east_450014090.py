# Barr's Abode
# 450014090
# Warp from Barr's Abode to T-Boy's Research Train 1.
# [Reverse City] Abandoned Train (37607): finding the train (going through here) is the quest's objective.

if sm.hasQuest(37607) and sm.getQRValue(37607, "dummy") != "1":
    sm.setQRValue(37607, "dummy", "1")
    sm.chatScript("You found T-Boy's abandoned research train. Talk to Rasol.")
sm.warp(450014100, 1)

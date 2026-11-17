# 102010000 Perion Southern Ridge, tent00: Chromile's tent (Mihile story).
#  The Forgotten Tent (20783): finding it is the quest's record ("Discovery").
#  Suspicious Movement on the Path (20784): the Worn Tent, where the Unidentified Assailants are watching.
#  A Truth So Cruel (20785): the tent where an Unidentified Assailant waits.
if sm.hasQuest(20783) and sm.getQRValue(20783) != "Discovery":
    sm.setQRValue(20783, "Discovery", False)
if sm.hasQuest(20785) or (sm.hasQuestCompleted(20784) and not sm.hasHadQuest(20785)):
    sm.warpInstanceIn(913070400, 0)
elif sm.hasQuest(20783) or sm.hasQuest(20784) or (sm.hasQuestCompleted(20783) and not sm.hasHadQuest(20784)):
    sm.warpInstanceIn(913070410, 0)
else:
    sm.warp(913070410, 0)
sm.dispose()

# 106030201
# Maple Road 4000012-4000014: v265 blocks the old Maple Island quests (32202-32215), so the way on isn't gated by them
fieldID = sm.getFieldID()
if fieldID < 4000014:
    sm.warp(fieldID + 1)
elif fieldID == 4000014:
    sm.warp(4000020, 0)
sm.dispose()

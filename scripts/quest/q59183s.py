# Where You Came From [Beast Tamer]
# 59183
# Bluffing Tom (9390313)
# Plays the quest's official lines (Quest.wz Say).

recoveredMemory = 7081

bluffingTom = 9390313

if sm.questStartDialogue(parentID, bluffingTom):
    sm.completeQuest(parentID)
    sm.startQuest(recoveredMemory)
    sm.setQRValue(recoveredMemory, "1", False)
sm.dispose()
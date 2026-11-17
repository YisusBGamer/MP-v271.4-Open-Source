# Karote: the way up from 410005003. While [Karote] The Second Statue (38209) is in progress (the statue's trial left
# unfinished), the statue on this floor pulls you back into its Void instead.
QUEST = 38209
VOID = 993165900
FLOOR = 410005003

if sm.getFieldID() == FLOOR and sm.hasQuest(QUEST):
    if sm.sendAskYesNo("The statue is still glowing. Return to the Void and finish its trial?"):
        sm.warpInstanceIn(VOID, 0)
        sm.setInstanceTime(15 * 60, FLOOR)
elif sm.getFieldID() == FLOOR:
    sm.warp(410005004, 1)
sm.dispose()

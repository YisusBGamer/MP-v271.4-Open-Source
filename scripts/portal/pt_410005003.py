# Karote: the way up from 410005002. While [Karote] The First Statue (38206) is in progress (the statue's trial left
# unfinished), the statue on this floor pulls you back into its Void instead.
QUEST = 38206
VOID = 993165850
FLOOR = 410005002

if sm.getFieldID() == FLOOR and sm.hasQuest(QUEST):
    if sm.sendAskYesNo("The statue is still glowing. Return to the Void and finish its trial?"):
        sm.warpInstanceIn(VOID, 0)
        sm.setInstanceTime(15 * 60, FLOOR)
elif sm.getFieldID() == FLOOR:
    sm.warp(410005003, 1)
sm.dispose()

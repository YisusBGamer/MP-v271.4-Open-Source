# Karote: the way up from 410005004. While [Karote] The Third Statue (38211) is in progress (the statue's trial left
# unfinished), the statue on this floor pulls you back into its Void instead.
QUEST = 38211
VOID = 993165950
FLOOR = 410005004

if sm.getFieldID() == FLOOR and sm.hasQuest(QUEST):
    if sm.sendAskYesNo("The statue is still glowing. Return to the Void and finish its trial?"):
        sm.warpInstanceIn(VOID, 0)
        sm.setInstanceTime(15 * 60, FLOOR)
elif sm.getFieldID() == FLOOR:
    sm.warp(410005005, 3)
sm.dispose()

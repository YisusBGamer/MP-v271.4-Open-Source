# 200060001 Neglected Strolling Path (see CustomFieldScripts) - To the Sealed Garden (21739, Aran): the Nependeath
# stem takes you into the Sealed Garden (a copy of your own; sealGarden spawns the Giant)
if sm.hasQuest(21739) and not sm.isComplete(21739):
    sm.chatScript("The Nependeath stem pulls you into the Sealed Garden!")
    sm.warpInstanceIn(920030001, 0)

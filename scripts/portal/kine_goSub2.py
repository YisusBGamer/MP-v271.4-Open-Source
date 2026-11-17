# go_next in Subway Car #1 (331003000)
if sm.hasQuest(22731) or sm.hasQuestCompleted(22731) and not sm.hasQuestCompleted(22733):
    sm.warp(331003100, 0)
else:
    sm.chatScript("The door to the next car won't open.")
    sm.dispose()

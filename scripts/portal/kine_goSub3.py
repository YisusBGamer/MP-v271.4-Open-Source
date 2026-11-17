# go_next in Subway Car #2 (331003100)
if sm.hasQuest(22732) or sm.hasQuestCompleted(22732) and not sm.hasQuestCompleted(22733):
    sm.warp(331003200, 0)
else:
    sm.chatScript("The door to the next car won't open.")
    sm.dispose()

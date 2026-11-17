# go_next in Subway Car #3 (331003200)
if sm.hasQuest(22733):
    sm.warp(331003300, 0)
else:
    sm.chatScript("The door to the next car won't open.")
    sm.dispose()

# Remote House (993201057) -> Front Yard (993201058), once Father's lesson is over (20036).
if sm.hasQuestCompleted(20036):
    sm.warp(993201058, 1)
else:
    sm.chatScript("Talk to Father before you go out.")
sm.dispose()

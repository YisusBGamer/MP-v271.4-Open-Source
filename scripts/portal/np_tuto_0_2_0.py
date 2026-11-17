# 552000010 out00 - Jett tutorial: to the control room (Key Keeper's Room) once Burke sent you after the key
if sm.hasQuest(53247) or sm.hasQuestCompleted(53247):
    sm.warp(552000021)
elif sm.hasMobsInField():
    sm.chatScript("Eliminate all guards.")
else:
    sm.chatScript("Return to Burke.")
sm.dispose()

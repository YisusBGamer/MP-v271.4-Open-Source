# [Daily] Part-Time Gardener Stage 1 (31044, start) | Cecilia (2162000)
# The day's score (record 31035 "score") starts over with Stage 1; Stage 2 and 3 open again once it's done ("x").
SCORE_RECORD = 31035
RULES = "#eRose Garden: Daily Quest#n\r\n - Eliminate regular monsters: 3 points\n - Grow roses/Free souls: 10 points\n - Eliminate Golden Bees: 25 points\n - Eliminate King Castle Golem: 50 points\r\n#rGoal: 300 points#k"

sm.setSpeakerID(2162000)
if sm.sendAskAccept(RULES):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(SCORE_RECORD, "score", "0")
        sm.setQRValue(SCORE_RECORD, "x", "0")
        sm.setQRValue(SCORE_RECORD, "d", sm.getCurrentDateAsString())
sm.dispose()

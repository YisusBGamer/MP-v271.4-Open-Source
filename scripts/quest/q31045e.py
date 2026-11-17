# [Daily] Part-Time Gardener Stage 2 (31045, end) | Cecilia (2162000)
# Done at 500 points on record 31035 "score". Act.img gives nothing: the EXP below is not in the game data. Finishing
# counts for Rose Knight (31047, 31035 "total").
SCORE_RECORD = 31035
ROSE_KNIGHT = 31047
ROSE_KNIGHT_GOAL = 15
EXP = 250000
RULES = "#eRose Garden: Daily Quest#n\r\n - Eliminate regular monsters: 3 points\n - Grow roses/Free souls: 10 points\n - Eliminate Golden Bees: 25 points\n - Eliminate King Castle Golem: 50 points\r\n#rGoal: 500 points#k"

sm.setSpeakerID(2162000)
# the score belongs to the day Stage 1 started (q31044s stamps "d"): yesterday's points don't count today
if sm.getQRValue(SCORE_RECORD, "d") != sm.getCurrentDateAsString():
    sm.setQRValue(SCORE_RECORD, "score", "0")
    sm.setQRValue(SCORE_RECORD, "d", sm.getCurrentDateAsString())
if sm.questEndDialogue(parentID):
    sm.giveExp(EXP)
    if sm.hasQuest(ROSE_KNIGHT):
        v = sm.getQRValue(SCORE_RECORD, "total")
        total = int(v) if v and v.isdigit() else 0
        if total < ROSE_KNIGHT_GOAL:
            sm.setQRValue(SCORE_RECORD, "total", str(total + 1))
elif sm.hasQuest(parentID):
    sm.sendSayOkay(RULES)
sm.dispose()

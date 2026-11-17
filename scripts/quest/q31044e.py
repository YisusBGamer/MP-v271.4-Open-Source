# [Daily] Part-Time Gardener Stage 1 (31044, end) | Cecilia (2162000)
# Done at 300 points on record 31035 "score". Act.img gives nothing: the EXP below is not in the game data. Finishing
# opens Stage 2 and 3 for the day (their start check: 31035 "x" = 1) and counts for Rose Knight (31047, 31035 "total").
SCORE_RECORD = 31035
ROSE_KNIGHT = 31047
ROSE_KNIGHT_GOAL = 15
EXP = 150000
RULES = "#eRose Garden: Daily Quest#n\r\n - Eliminate regular monsters: 3 points\n - Grow roses/Free souls: 10 points\n - Eliminate Golden Bees: 25 points\n - Eliminate King Castle Golem: 50 points\r\n#rGoal: 300 points#k"

sm.setSpeakerID(2162000)
if sm.questEndDialogue(parentID):
    sm.setQRValue(SCORE_RECORD, "x", "1")
    sm.giveExp(EXP)
    if sm.hasQuest(ROSE_KNIGHT):
        v = sm.getQRValue(SCORE_RECORD, "total")
        total = int(v) if v and v.isdigit() else 0
        if total < ROSE_KNIGHT_GOAL:
            sm.setQRValue(SCORE_RECORD, "total", str(total + 1))
elif sm.hasQuest(parentID):
    sm.sendSayOkay(RULES)
sm.dispose()

# Rose Flower Garden (2119100), Rose Garden: Abandoned Garden. Reactor.wz: "You can make the flowers bloom using Rose
# Seeds." [Rose Garden] Fresh Roses (31038): "Click on empty flower pots with the seeds in your inventory to grow and
# collect roses" - record 31038 "count" up to 5. The Rose Garden dailies score 10 points a rose (31035 "score").
ITEM = 4030036
QUEST = 31038
GOAL = 5
SCORE_RECORD = 31035
DAILIES = (31044, 31045, 31046)
DAILY_POINTS = 10


def held_daily():
    return any(sm.hasQuest(q) for q in DAILIES)


if not sm.hasItem(ITEM):
    sm.chatScript("You can make the flowers bloom using Rose Seeds.")
else:
    counted = False
    if sm.hasQuest(QUEST):
        v = sm.getQRValue(QUEST, "count")
        n = int(v) if v and v.isdigit() else 0
        if n < GOAL:
            n += 1
            sm.setQRValue(QUEST, "count", str(n))
            sm.chatScript("A rose bloomed in the flower pot. (%d/%d)" % (n, GOAL))
            counted = True
    if held_daily():
        v = sm.getQRValue(SCORE_RECORD, "score")
        score = int(v) if v and v.isdigit() else 0
        sm.setQRValue(SCORE_RECORD, "score", str(score + DAILY_POINTS))
        if not counted:
            sm.chatScript("A rose bloomed in the flower pot.")
        counted = True
    if counted:
        sm.consumeItem(ITEM, 1)
sm.dispose()

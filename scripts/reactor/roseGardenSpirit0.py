# Rose Flower Garden (2119101/2119102), Rose Garden: Soul's Rest. Reactor.wz: "You can free souls with Rousseau's Key."
# [Rose Garden] Imprisoned Souls (31041): "With the key in your inventory, you can click the imprisoned souls to free
# them" - record 31041 "count" up to 5. The Rose Garden dailies score 10 points a soul (31035 "score").
ITEM = 4030032
QUEST = 31041
GOAL = 5
SCORE_RECORD = 31035
DAILIES = (31044, 31045, 31046)
DAILY_POINTS = 10


def held_daily():
    return any(sm.hasQuest(q) for q in DAILIES)


if not sm.hasItem(ITEM):
    sm.chatScript("You can free souls with Rousseau's Key.")
else:
    counted = False
    if sm.hasQuest(QUEST):
        v = sm.getQRValue(QUEST, "count")
        n = int(v) if v and v.isdigit() else 0
        if n < GOAL:
            n += 1
            sm.setQRValue(QUEST, "count", str(n))
            sm.chatScript("You freed an imprisoned soul. (%d/%d)" % (n, GOAL))
            counted = True
    if held_daily():
        v = sm.getQRValue(SCORE_RECORD, "score")
        score = int(v) if v and v.isdigit() else 0
        sm.setQRValue(SCORE_RECORD, "score", str(score + DAILY_POINTS))
        if not counted:
            sm.chatScript("You freed an imprisoned soul.")
        counted = True
    if counted:
        sm.consumeItem(ITEM, 1)
sm.dispose()

# Gallero (3001106), Heliseum 401000000: today's commissions. The [Daily] quests 31700-31733 need their key on
# Gallero's records (31791 keys 0-17, 31792 keys 18-31, 31793 the bosses 32-33) set to 1. Each day (UTC) yesterday's
# are cleared (finished ones can be taken again) and three new ones open, plus the two Magnus bosses from Lv. 140.
import random
import time

FIRST = 31700
NORMAL = range(0, 32)
BOSSES = (32, 33)
PICKS = 3
DAY_RECORD = 31790


def record(key):
    return 31791 if key <= 17 else 31792 if key <= 31 else 31793


today = sm.getResetDay("yyyyMMdd")
sm.setSpeakerID(3001106)
if sm.getQRValue(DAY_RECORD, "day") != today:
    for key in list(NORMAL) + list(BOSSES):
        q = FIRST + key
        if sm.getQRValue(record(key), str(key)) == "1":
            sm.setQRValue(record(key), str(key), "0")
        if sm.hasQuestCompleted(q):
            sm.deleteQuest(q)  # a new day: it can be taken again
    for key in random.sample(list(NORMAL), PICKS):
        sm.setQRValue(record(key), str(key), "1")
    if chr.getLevel() >= 140:
        for key in BOSSES:
            sm.setQRValue(record(key), str(key), "1")
    sm.setQRValue(DAY_RECORD, "day", today)
    sm.sendSayOkay("I'm Gallero! I've got today's commissions for you. Check your quest list and get to it!")
else:
    sm.sendSayOkay("I'm Gallero! You've already got today's commissions. Come back tomorrow for more.")
sm.dispose()

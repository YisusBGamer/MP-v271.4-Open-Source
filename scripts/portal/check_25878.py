# Portal script check_25878.
# Kaiser story: Heretic Temple 3 -> Heretical Cleric Chambers, for Magnus's Plan (25878).
if sm.hasQuest(25878) or sm.hasQuestCompleted(25878):
    sm.warp(400030610, 1)
else:
    chr.chatMessage("Something blocks the way deeper into the temple.")
sm.dispose()

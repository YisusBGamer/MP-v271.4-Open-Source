# Portal script getOffBus.
# Shinsoo International School (FriendStory): get off the bus - choose the stop.
SUNSET_ALT_QUESTS = (33513, 33514)
SUNSET_ALT_STARTS = ((33514, 33513),)
STOPS = (("City Center", 330000000, 0), ("Fountain Park", 330001700, 3), ("Drizzling Strolling Path", 330001800, 1),
         ("Sunset Lot", 330001900, 3))


def sunset_alt():
    if any(sm.hasQuest(q) for q in SUNSET_ALT_QUESTS):
        return True
    for q, pre in SUNSET_ALT_STARTS:
        if not sm.hasQuest(q) and not sm.hasQuestCompleted(q) and sm.hasQuestCompleted(pre):
            return True
    return False


text = "Where would you like to get off?\r\n\r\n#b"
for i in range(len(STOPS)):
    text += "#L{}#{}#l\r\n".format(i, STOPS[i][0])
sel = sm.sendNext(text)
if 0 <= sel < len(STOPS):
    name, field, portal = STOPS[sel]
    if field == 330001900 and sunset_alt():
        sm.warp(330000630, 1)
    else:
        sm.warp(field, portal)
sm.dispose()

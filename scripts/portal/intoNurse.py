# Portal script intoNurse.
# Shinsoo International School (FriendStory): hallway -> School Infirmary.
# Later episodes take place in another copy of the room; you're sent there while one of its quests is open.
ALT_ROOM = 330000900
ALT_QUESTS = (33516, 33517)
ALT_STARTS = ((33517, 33516),)
def use_alt():
    if any(sm.hasQuest(q) for q in ALT_QUESTS):
        return True
    for q, pre in ALT_STARTS:
        if not sm.hasQuest(q) and not sm.hasQuestCompleted(q) and sm.hasQuestCompleted(pre):
            return True
    return False


if use_alt():
    sm.warp(ALT_ROOM, 2)
else:
    sm.warp(330000400, 2)
sm.dispose()

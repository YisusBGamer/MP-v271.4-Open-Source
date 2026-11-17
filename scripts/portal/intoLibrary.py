# Portal script intoLibrary.
# Shinsoo International School (FriendStory): hallway -> Library.
# Later episodes take place in another copy of the room; you're sent there while one of its quests is open.
ALT_ROOM = 330000700
ALT_QUESTS = (33519, 33520)
ALT_STARTS = ((33520, 33519),)
def use_alt():
    if any(sm.hasQuest(q) for q in ALT_QUESTS):
        return True
    for q, pre in ALT_STARTS:
        if not sm.hasQuest(q) and not sm.hasQuestCompleted(q) and sm.hasQuestCompleted(pre):
            return True
    return False


if use_alt():
    sm.warp(ALT_ROOM, 1)
else:
    sm.warp(330001200, 1)
sm.dispose()

# Portal script inClass_3F.
# Shinsoo International School (FriendStory): hallway -> Third Floor Classroom.
# Later episodes take place in another copy of the room; you're sent there while one of its quests is open.
ALT_ROOM = 330000600
ALT_QUESTS = (33503,)
ALT_STARTS = ((33504, 33503),)
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
    sm.warp(330000300, 1)
sm.dispose()

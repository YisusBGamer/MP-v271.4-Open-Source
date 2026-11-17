# Portal script intoMusic.
# Shinsoo International School (FriendStory): hallway -> Music Room.
# Later episodes take place in another copy of the room; you're sent there while one of its quests is open.
ALT_ROOM = 330000800
ALT_QUESTS = (33509, 33510)
ALT_STARTS = ((33510, 33509), (33511, 33510))
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
    sm.warp(330001400, 1)
sm.dispose()

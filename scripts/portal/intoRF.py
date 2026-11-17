# Portal script intoRF.
# Shinsoo International School (FriendStory): hallway -> School Roof.
ALT_ROOM = 330000620
ALT_QUESTS = (33508, 33511)
ALT_STARTS = ((33509, 33508), (33512, 33511), (33515, 33514))
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
    sm.warp(330000500, 1)
sm.dispose()

# Portal script oldSchool_Pt.
# Shinsoo International School (FriendStory): City Center -> Old Shinsoo School Building.
ALT_ROOM = 330004000
ALT_QUESTS = (32859, 32860, 32865)
ALT_STARTS = ((32860, 32859), (32861, 32860), (32866, 32865))
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
    sm.warp(330003000, 2)
sm.dispose()

# Portal script pt_sp1hidden: Sunset Lot (330001900), far right.
# Hidden Story: The Big Moment (33046) plays in the hidden map 330002130 (Hidden Street: The Big Moment).
QUEST = 33046

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "ok") != "1":
    sm.warp(330002130, 0)
sm.dispose()

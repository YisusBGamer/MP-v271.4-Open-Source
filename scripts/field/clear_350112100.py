# Heroes of Maple, Act 1: Slumbering Dragon Island (33910) - stage 2/2 cleared (short version of the story). The story's
# own scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33910
MILESTONES = (33920, 33921, 33922, 33923, 33924, 33925, 33926)
if sm.hasQuest(ACT):
    ret = sm.getQRValue(ACT, "ret")
    for q in MILESTONES:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.completeQuestNoCheck(ACT)
    chr.chatMessage('[Heroes of Maple] Act 1: Slumbering Dragon Island complete! Talk to Neinheart in Ereve to continue.')
    try:
        back = int(ret)
    except (TypeError, ValueError):
        back = 130000000
    sm.warpInstanceOut(back if back > 0 else 130000000, 0)

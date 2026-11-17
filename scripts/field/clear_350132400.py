# Heroes of Maple, Act 3: Unfamiliar World (33913) - stage 2/2 cleared (short version of the story). The story's own
# scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33913
MILESTONES = (34001, 34002, 34003, 34004, 34005, 34006, 34007)
if sm.hasQuest(ACT):
    ret = sm.getQRValue(ACT, "ret")
    for q in MILESTONES:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.completeQuestNoCheck(ACT)
    chr.chatMessage('[Heroes of Maple] Act 3: Unfamiliar World complete! Talk to Neinheart in Ereve to continue.')
    try:
        back = int(ret)
    except (TypeError, ValueError):
        back = 130000000
    sm.warpInstanceOut(back if back > 0 else 130000000, 0)

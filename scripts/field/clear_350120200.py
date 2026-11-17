# Heroes of Maple, Act 1.5: Reunion (33911) - stage 1/1 cleared (short version of the story). The story's own
# scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33911
MILESTONES = (33947, 33948, 33949, 33950, 33951)
if sm.hasQuest(ACT):
    ret = sm.getQRValue(ACT, "ret")
    for q in MILESTONES:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.completeQuestNoCheck(ACT)
    chr.chatMessage('[Heroes of Maple] Act 1.5: Reunion complete! Talk to Neinheart in Ereve to continue.')
    try:
        back = int(ret)
    except (TypeError, ValueError):
        back = 130000000
    sm.warpInstanceOut(back if back > 0 else 130000000, 0)

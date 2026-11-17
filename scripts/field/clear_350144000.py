# Heroes of Maple, Final Act: The World Tree (33914) - stage 2/2 cleared (short version of the story). The story's own
# scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33914
MILESTONES = (34011, 34012, 34013, 34014, 34015)
if sm.hasQuest(ACT):
    ret = sm.getQRValue(ACT, "ret")
    for q in MILESTONES:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.completeQuestNoCheck(ACT)
    sm.setQRValue(500664, "HOM_check", "clear")  # what the boss window checks for Damien
    chr.chatMessage('[Heroes of Maple] The World Tree is safe for now. The story is complete!')
    try:
        back = int(ret)
    except (TypeError, ValueError):
        back = 130000000
    sm.warpInstanceOut(back if back > 0 else 130000000, 0)

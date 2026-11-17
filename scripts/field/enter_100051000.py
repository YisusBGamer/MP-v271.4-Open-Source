# Karuppa Town (100051000), Partem
# [Partem Ruins] Missing Children (35952) has you follow the triplets' footsteps into Karuppa Town. Quest.wz: its
# "Missing Kids" record (dummy=1) is the only thing it asks for and no NPC turns it in, so walking in sets the record
# and finishes the quest.
MISSING_CHILDREN = 35952
if sm.hasQuest(MISSING_CHILDREN):
    if sm.getQRValue(MISSING_CHILDREN, "dummy") != "1":
        sm.setQRValue(MISSING_CHILDREN, "dummy", "1")
    sm.completeQuest(MISSING_CHILDREN)

# Pathfinders whose tutorial skip completed none of the tutorial's quests (Research and Rescue needs 35903)
if chr.getJob() in (301, 330, 331, 332):
    for quest in (35900, 35901, 35902, 35903):
        if not sm.hasQuestCompleted(quest):
            sm.completeQuestNoRewards(quest)

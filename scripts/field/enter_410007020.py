# Shangri-La: Sinners' Paradise (410007020), on entering.
# The map holds the story's people for several points of the Shangri-La story (two Gaons, two Zigangs, two Weios). As
# in Cernium Square (enter_410000500), a story copy shows only while the player needs it: it hands in a quest under
# way or starts one the player can take now. Everyone else (Master Tai Yu's dailies, the merchants, the storage box,
# the townspeople) always stays.
qm = chr.getQuestManager()

# story NPC -> (quests it hands in, quests it starts), from Quest.wz
STORY = {
    3006173: ([], [38392, 38394]),  # Gaon
    3006175: ([], [38393]),         # Zigang
    3006180: ([38400], [38400, 38401]),  # Weio
    3006342: ([38409], []),         # Gaon
}


def under_way(q):
    return sm.hasQuest(q) and not sm.hasQuestCompleted(q)


def needed(npc):
    ends, starts = STORY[npc]
    return any(under_way(q) for q in ends) or any(under_way(q) or qm.canStartQuestQuietly(q) for q in starts)


for npc in sm.getField().getNpcs():
    tid = npc.getTemplateId()
    if tid in STORY and not needed(tid):
        sm.invokeAfterDelay(500, "hideNpcInField", sm.getFieldID(), npc.getObjectId(), True)
sm.dispose()

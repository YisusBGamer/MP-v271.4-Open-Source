# [Lion King's Castle] Is There Any Hope? (3178) - Queen Ifia
# Ifia's official Say.img lines are played (questStartDialogue); she then takes you along the Secret Corridor
# (921140000) to confront the Lion King, as her last line says ("Please, come with me to the Audience Room"). See
# npc/ipia_teleport.py, portal/EpiaNext.py and npc/2161009.py.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.questStartDialogue(parentID, 2161001)
if sm.hasQuest(parentID) and not sm.hasItem(4032839):
    sm.warpInstanceIn(921140000, 0)
sm.dispose()

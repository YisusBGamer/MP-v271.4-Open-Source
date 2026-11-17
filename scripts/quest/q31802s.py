# 31802 - Heliseum Reclamation HQ in Danger (Tiron, 3001007, Transitional Dimensional Door)
# Says its official lines (Quest.wz Say.img). Declining used to start the quest and warp anyway.
if sm.questStartDialogue(parentID, 3001007):
    sm.warpInstanceIn(401070000)  # the occupied Reclamation HQ battlefield
sm.dispose()

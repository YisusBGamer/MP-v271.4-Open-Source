# A Truth So Cruel (Mihile 20785): started at the Unidentified Assailant (npc/1106007), who attacks.
if sm.questStartDialogue(parentID, 1106007) and sm.getFieldID() == 913070400 and not sm.hasMobsInField(913070400, 9001056):
    pos = chr.getPosition()
    for i in range(8):
        sm.spawnMob(9001056, pos.getX() - 350 + i * 100, pos.getY(), False)
sm.dispose()

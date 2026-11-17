# [Arkarium] Traces of Arkarium (31176): Bastille, under Arkarium's control, attacks (Corrupted Bastille 9300487).
if sm.questStartDialogue(parentID, 2144008) and sm.getFieldID() == 272000410 and not sm.hasMobsInField(272000410, 9300487):
    npc = sm.getField().getLifeByTemplateId(2144008)
    pos = npc.getPosition() if npc is not None else chr.getPosition()
    sm.removeNpc(2144008)
    sm.spawnMob(9300487, pos.getX(), pos.getY(), False)
sm.dispose()

# 272000410-272000419 Leafre in Flames 4 (copy): [Arkarium] Traces of Arkarium (31176) - back after leaving: Bastille
# attacks again.
if sm.hasQuest(31176) and not sm.isComplete(31176) and not sm.hasMobsInField(sm.getFieldID(), 9300487):
    npc = sm.getField().getLifeByTemplateId(2144008)
    pos = npc.getPosition() if npc is not None else chr.getPosition()
    sm.removeNpc(2144008)
    sm.spawnMob(9300487, pos.getX(), pos.getY(), False)

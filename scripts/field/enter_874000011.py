# id 874000011 (Fort Asura : Ritual Altar), field 874000011
# AkechiInstanceHelper spawns Akechi and runs the fight
# The map has no exit portal: put out the Gate NPC (Npc.wz script AkechiOut) at the west end of the altar
GATE = 9401487
sm.spawnNpcIfAbsent(GATE, -450, 306)
npc = sm.getField().getLifeByTemplateId(GATE)
if npc is not None:
    npc.getScripts().put(0, "AkechiOut")
sm.dispose()

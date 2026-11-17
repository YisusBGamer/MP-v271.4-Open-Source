# Fort Asura Entrance has only a spawn point in the client data and no exit portal or NPC.
# Give its boss lobby a visible guide; the same script also supplies the @menu exit option.
NPC = 9130021  # Princess Sakuno
sm.spawnNpcIfAbsent(NPC, 500, 249)
npc = sm.getField().getLifeByTemplateId(NPC)
if npc is not None:
    npc.getScripts().put(0, "akechi_enter")
sm.dispose()

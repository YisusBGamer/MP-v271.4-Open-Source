# NPC script: npc_9063837
# NPCs: 9063837 (Monster Park Shuttle)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Monster Park Shuttle.")

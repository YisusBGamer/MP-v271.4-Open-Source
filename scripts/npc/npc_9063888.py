# NPC script: npc_9063888
# NPCs: 9063888 (Phantasmal Priestess)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Phantasmal Priestess.")

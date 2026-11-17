# NPC script: npc_9063347
# NPCs: 9063347 (Diver Box), 9063351 (Diver Box)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Diver Box.")

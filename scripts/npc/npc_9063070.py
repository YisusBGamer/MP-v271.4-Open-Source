# NPC script: npc_9063070
# NPCs: 9063070 (Creepy Coat Rack)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Creepy Coat Rack.")

# NPC script: npc_9063013
# NPCs: 9063013 (Oran and Toran)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Oran and Toran.")

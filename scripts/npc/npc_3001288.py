# NPC script: npc_3001288
# NPCs: 3001288 (Oran and Toran)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Oran and Toran.")

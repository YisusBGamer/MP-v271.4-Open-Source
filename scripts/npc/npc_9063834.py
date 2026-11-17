# NPC script: npc_9063834
# NPCs: 9063834 (Spiegelette)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Spiegelette.")

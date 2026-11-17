# NPC script: npc_9063316
# NPCs: 3006791 (Cledd), 9063316 (Cledd)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Cledd.")

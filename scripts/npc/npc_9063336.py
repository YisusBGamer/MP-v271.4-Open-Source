# NPC script: npc_9063336
# NPCs: 9063336 (Cobra), 9063396 (Cobra)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Cobra.")

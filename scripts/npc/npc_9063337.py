# NPC script: npc_9063337
# NPCs: 9063337 (Dave), 9063350 (Dave), 9063395 (Dave)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Dave.")

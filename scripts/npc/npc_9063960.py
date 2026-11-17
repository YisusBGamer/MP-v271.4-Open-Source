# NPC script: npc_9063960
# NPCs: 9063960 (Oz), 9063964 (Oz)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Oz.")

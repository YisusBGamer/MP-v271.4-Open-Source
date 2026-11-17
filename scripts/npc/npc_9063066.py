# NPC script: npc_9063066
# NPCs: 9063066 (Busy Honey Bee)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Busy Honey Bee.")

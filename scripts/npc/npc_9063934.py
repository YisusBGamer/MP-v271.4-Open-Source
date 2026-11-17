# NPC script: npc_9063934
# NPCs: 9063934 (Churro Stall)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Churro Stall.")

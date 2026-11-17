# NPC script: npc_9063723
# NPCs: 9063723 (Ranking Board)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Ranking Board.")

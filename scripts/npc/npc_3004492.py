# NPC script: npc_3004492
# NPCs: 3004492 (Scorched Scripture)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Scorched Scripture.")

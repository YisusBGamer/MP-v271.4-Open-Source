# NPC script: npc_3006883
# NPCs: 3006883 (Priest's Chest)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Priest's Chest.")

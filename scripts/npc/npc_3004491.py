# NPC script: npc_3004491
# NPCs: 3004491 (Teddy Bear)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Teddy Bear.")

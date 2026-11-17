# NPC script: npc_9062856
# NPCs: 9062856 (Neon Spiegelmann)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Neon Spiegelmann.")

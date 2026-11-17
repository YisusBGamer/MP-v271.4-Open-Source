# NPC script: npc_9062702
# NPCs: 9062702 (Mr. Lim)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Mr. Lim.")

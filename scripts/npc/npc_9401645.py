# NPC script: npc_9401645
# NPCs: 9401645 (Craftsman Statue)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Craftsman Statue.")

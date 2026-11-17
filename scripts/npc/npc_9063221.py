# NPC script: npc_9063221
# NPCs: 9063221 (Wal-Sook)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Wal-Sook.")

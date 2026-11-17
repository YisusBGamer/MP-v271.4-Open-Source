# NPC script: talk2153007
# NPCs: 2153007 (Unmarked Grave)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Unmarked Grave.")

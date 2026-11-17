# NPC script: npc_3006894
# NPCs: 3006894 (Phantom Crystal Ball)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Phantom Crystal Ball.")

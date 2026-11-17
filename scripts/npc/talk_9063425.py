# NPC script: talk_9063425
# NPCs: 9063425 (Optimistic Shamaness)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Optimistic Shamaness.")

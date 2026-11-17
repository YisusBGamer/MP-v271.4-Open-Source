# NPC script: talk_9063446
# NPCs: 9063446 (Daring Priest)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Daring Priest.")

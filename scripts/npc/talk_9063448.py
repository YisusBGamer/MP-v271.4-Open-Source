# NPC script: talk_9063448
# NPCs: 9063448 (Nostalgic Priest)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Nostalgic Priest.")

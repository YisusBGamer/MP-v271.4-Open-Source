# NPC script: talk_9063431
# NPCs: 9063431 (Gloomy Child)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Gloomy Child.")

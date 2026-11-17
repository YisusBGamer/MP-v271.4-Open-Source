# NPC script: custom_illustration_help
# NPCs: 9070104 (Michelavinci)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Michelavinci.")

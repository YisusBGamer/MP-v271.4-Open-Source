# NPC script: erion_open
# NPCs: 9076145 (Erion), 9076151 (Erion)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Erion.")

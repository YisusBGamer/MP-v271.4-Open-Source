# NPC script: npc_3006235
# NPCs: 3006235 (Anan's Document Pile)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Anan's Document Pile.")

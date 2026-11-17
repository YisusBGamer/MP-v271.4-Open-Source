# NPC script: npc_3006893
# NPCs: 3006893 (Altar of Eternity)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Altar of Eternity.")

# NPC script: npc_3006891
# NPCs: 3006891 (Altar of Providence)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Altar of Providence.")

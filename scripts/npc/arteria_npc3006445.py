# NPC script: arteria_npc3006445
# NPCs: 3006445 (Suspicious Place)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Suspicious Place.")

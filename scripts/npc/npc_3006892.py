# NPC script: npc_3006892
# NPCs: 3006892 (Altar of Judgment)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Altar of Judgment.")

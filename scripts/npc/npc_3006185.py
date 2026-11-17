# NPC script: npc_3006185
# NPCs: 3006185 (Winter Scroll)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Winter Scroll.")

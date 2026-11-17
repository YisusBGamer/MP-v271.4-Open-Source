# NPC script: npc_9402003
# NPCs: 9402003 (Mr. Do)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Mr. Do.")

# NPC script: npc_3005033
# NPCs: 3005033 (Kelly's Letter)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Kelly's Letter.")

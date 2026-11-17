# NPC script: npc_3007102
# NPCs: 3007102 (Securitron Z)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Securitron Z.")

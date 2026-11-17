# NPC script: npc_9401610
# NPCs: 9401610 (Pit-a-Pat Gift Box)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Pit-a-Pat Gift Box.")

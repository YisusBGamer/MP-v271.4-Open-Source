# NPC script: npc_3006483
# NPCs: 3006483 (Third Barrier Stone)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Third Barrier Stone.")

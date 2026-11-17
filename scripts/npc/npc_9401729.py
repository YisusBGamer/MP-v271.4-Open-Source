# NPC script: npc_9401729
# NPCs: 9401729 (Viewfinder)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Viewfinder.")

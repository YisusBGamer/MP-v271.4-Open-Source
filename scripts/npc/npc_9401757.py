# NPC script: npc_9401757
# NPCs: 9401757 (Spiegelette)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Spiegelette.")

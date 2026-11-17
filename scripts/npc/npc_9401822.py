# NPC script: npc_9401822
# NPCs: 9401822 (Earth Guardian), 9401860 (Earth Guardian)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Earth Guardian.")

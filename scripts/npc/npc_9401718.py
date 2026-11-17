# NPC script: npc_9401718
# NPCs: 9401718 (Tigun the Advisor)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Tigun the Advisor.")

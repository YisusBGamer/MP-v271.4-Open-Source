# NPC script: npc_3003582
# NPCs: 3003582 (Wind Statue)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Wind Statue.")

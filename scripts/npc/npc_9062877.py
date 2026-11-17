# NPC script: npc_9062877
# NPCs: 9062877 (Pink Bean Statue)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Pink Bean Statue.")

# NPC script: npc_9062939
# NPCs: 9062939 (Growing Baby Food)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Growing Baby Food.")

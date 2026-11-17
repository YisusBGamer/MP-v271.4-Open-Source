# NPC script: npc_9062661
# NPCs: 9062661 (Growing Baby Food)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Growing Baby Food.")

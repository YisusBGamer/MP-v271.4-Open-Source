# NPC script: npc_9063928
# NPCs: 9063928 (Gift Ticket Booth)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Gift Ticket Booth.")

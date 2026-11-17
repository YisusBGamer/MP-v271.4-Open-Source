# NPC script: npc_9063699
# NPCs: 9063699 (Jr. Yeti)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Jr. Yeti.")

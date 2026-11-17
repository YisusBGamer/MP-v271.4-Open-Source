# NPC script: npc_9063700
# NPCs: 9063700 (Dark Jr. Yeti), 9063702 (Muru Yeti)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Dark Jr. Yeti.")

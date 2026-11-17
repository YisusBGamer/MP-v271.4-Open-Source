# NPC script: npc_9063830
# NPCs: 9063830 (Mrs. Ming Ming and Jay)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Mrs. Ming Ming and Jay.")

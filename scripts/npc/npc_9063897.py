# NPC script: npc_9063897
# NPCs: 9063897 (Small Snowman)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Small Snowman.")

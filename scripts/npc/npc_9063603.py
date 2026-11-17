# NPC script: npc_9063603
# NPCs: 9063603 (Metal Bucket Snowman)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Metal Bucket Snowman.")

# NPC script: npc_3006783
# NPCs: 3006783 (Kelly), 3006795 (Kelly), 9063490 (Kelly)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Kelly.")

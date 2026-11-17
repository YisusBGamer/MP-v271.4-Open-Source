# NPC script: npc_9063288
# NPCs: 9063288 (Stack of Magic Books)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Stack of Magic Books.")

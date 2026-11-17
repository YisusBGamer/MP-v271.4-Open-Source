# NPC script: npc_9063689
# NPCs: 9063689 (Next-gen Speaker)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Next-gen Speaker.")

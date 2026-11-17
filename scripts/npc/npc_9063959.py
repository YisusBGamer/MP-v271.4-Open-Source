# NPC script: npc_9063959
# NPCs: 9063959 (Cygnus), 9063963 (Cygnus)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Cygnus.")

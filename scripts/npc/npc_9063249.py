# NPC script: npc_9063249
# NPCs: 9063249 (Dances with Balrog)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Dances with Balrog.")

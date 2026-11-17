# NPC script: npc_3007023
# NPCs: 3007023 (Wandering Peddler)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Wandering Peddler.")

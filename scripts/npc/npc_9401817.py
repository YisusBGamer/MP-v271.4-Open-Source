# NPC script: npc_9401817
# NPCs: 9401817 (Forest Master), 9401840 (Forest Master)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Forest Master.")

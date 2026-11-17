# NPC script: rita_library2022
# NPCs: 2500008 (Skylark Rita)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Skylark Rita.")

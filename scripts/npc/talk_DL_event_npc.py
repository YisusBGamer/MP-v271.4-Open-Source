# NPC script: talk_DL_event_npc
# NPCs: 2500019 (Skylark Romi)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Skylark Romi.")

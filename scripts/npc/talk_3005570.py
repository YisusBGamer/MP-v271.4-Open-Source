# NPC script: talk_3005570
# NPCs: 3005570 (Godsphere)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Godsphere.")
